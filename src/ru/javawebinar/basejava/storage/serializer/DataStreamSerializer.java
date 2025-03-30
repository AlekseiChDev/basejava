package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeForEach(dos, r.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }) ;

            writeForEach(dos, r.getSections().entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType){
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeForEach(dos, ((ListSection) section).getItems(), item -> {
                            dos.writeUTF( item );
                        }) ;
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeForEach(dos, ((CompanySection) section).getCompanies(), company -> {
                            dos.writeUTF( company.getHomePage().getName() );
                            dos.writeUTF( company.getHomePage().getUrl() );
                            writeForEach(dos, company.getPeriods(), period -> {
                                writeLocalDate(dos, period.getStartDate());
                                writeLocalDate(dos, period.getEndDate());
                                dos.writeUTF( period.getTitle() );
                                dos.writeUTF( period.getDescription() );
                            }) ;
                        }) ;
                        break;
                }
            }) ;
        }
    }

    private interface WriterItem<T> {
        void write(T t) throws IOException;
    }
    private <T> void writeForEach(DataOutputStream dos, Collection<T> items, WriterItem<T> writer) throws IOException {
        dos.writeInt(items.size());
        for (T  item : items) {
            writer.write(item);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(sectionType, dis));
            }
            return resume;
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
    }

    private Section readSection(SectionType type, DataInputStream dis) throws IOException {
        switch (type){
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                int sizeItems = dis.readInt();
                List<String> items = new ArrayList<>();;
                for (int j = 0; j < sizeItems; j++) {
                    items.add(dis.readUTF());
                }
                return new ListSection(items);
            case EXPERIENCE:
            case EDUCATION:
                int sizeCompanies = dis.readInt();
                List<Company> companies = new ArrayList<>();
                for (int j = 0; j < sizeCompanies; j++) {
                    companies.add(readCompany(dis));
                }
                return new CompanySection(companies);
        }
        throw new IllegalStateException();
    }

    private Company readCompany(DataInputStream dis) throws IOException {
        return new Company(new Link(dis.readUTF(), dis.readUTF()), readPeriods(dis));
    }

    private List<Company.Period> readPeriods(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Company.Period> periods = new ArrayList<>();;
        for (int j = 0; j < size; j++) {
            periods.add(new Company.Period(readLocalDate(dis),readLocalDate(dis), dis.readUTF(), dis.readUTF() ));
        }
        return periods;
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }
 }
