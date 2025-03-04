package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    private static final String UUID_1 = "uuid1";

    public static void main(String[] args) {

        Resume resume = fillResume(UUID_1, "Name1");

        System.out.println(resume);
        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType + ":" + resume.getContact(contactType));
        }
        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType + "\n" + resume.getSection(sectionType));
        }
    }

    public static Resume fillResume(String uuid, String fullName ) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "");
        resume.addContact(ContactType.GITHUB, "");
        resume.addContact(ContactType.STATCKOVERFLOW, "");
        resume.addContact(ContactType.HOME_PAGE, "");
        resume.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Position"));

        List<String> achivments = new ArrayList<>();
        achivments.add("Team organization");
        achivments.add("Web Application Development");
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achivments));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish ");
        qualifications.add("DB: PostgreSQL");
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));

        List<Period> periods = new ArrayList<>();
        periods.add(new Period((LocalDate.of(2020, 1, 8)), (LocalDate.of(2022, 1, 8)), "Post", "Description"));
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("Company1", "URL1", periods ));
        resume.addSection(SectionType.EXPERIENCE, new CompanySection(companies));

        periods.clear();
        periods.add(new Period((LocalDate.of(2018, 1, 8)), (LocalDate.of(2019, 1, 8)), "", "Description"));
        companies.clear();
        companies.add(new Company("Universitet1", "URL2", periods ));
        resume.addSection(SectionType.EXPERIENCE, new CompanySection(companies));
        resume.addSection(SectionType.EDUCATION, new CompanySection(companies));

        return resume;
    }
}
