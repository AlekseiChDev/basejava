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
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section).getText());
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            writeForEach(dos, ((ListSection) section).getItems(), dos::writeUTF);
                    case EXPERIENCE, EDUCATION ->
                            writeForEach(dos, ((CompanySection) section).getCompanies(), company -> {
                                dos.writeUTF(company.getHomePage().getName());
                                dos.writeUTF(company.getHomePage().getUrl());
                                writeForEach(dos, company.getPeriods(), period -> {
                                    writeLocalDate(dos, period.getStartDate());
                                    writeLocalDate(dos, period.getEndDate());
                                    dos.writeUTF(period.getTitle());
                                    dos.writeUTF(period.getDescription());
                                });
                            });
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
            readItems(dis,()-> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis,()->{
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(sectionType, dis));
            });
            return resume;
        }
    }

    private interface ReaderItem {
        void read() throws IOException;
    }

    private void readItems(DataInputStream dis, ReaderItem reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private interface ReaderItemList<T> {
        T read() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, ReaderItemList<T> reader) throws IOException {
        List<T> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
    }

    private Section readSection(SectionType type, DataInputStream dis) throws IOException {
        return switch (type) {
            case PERSONAL, OBJECTIVE -> new TextSection(dis.readUTF());
            case ACHIEVEMENT, QUALIFICATIONS -> new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE, EDUCATION -> new CompanySection(
                    readList(dis, () -> new Company(new Link(dis.readUTF(), dis.readUTF()),
                            readList(dis, () -> new Company.Period(readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF())))));
        };
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }
 }
