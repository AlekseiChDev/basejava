package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.Month;

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

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("Team organization", "Web Application Development"));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish", "DB: PostgreSQL"));
        resume.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("Company11", "http://Company11.ru",
                                new Company.Period(2005, Month.JANUARY, "position1", "content1"),
                                new Company.Period(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        resume.addSection(SectionType.EDUCATION,
                new CompanySection(
                        new Company("Universitet1", "http://Universitet.ru",
                                new Company.Period(1996, Month.JANUARY, "student", "content1"),
                                new Company.Period(2001, Month.MARCH, 2005, Month.JANUARY, "aspirant", "content2"))));

        return resume;








    }
}
