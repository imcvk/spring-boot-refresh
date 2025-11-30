package org.hibernate.commonutil.dummydatagenerator;

import com.github.javafaker.Faker;
import org.hibernate.Chapter1.pojo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DummyData {
    // Use a ThreadLocal Faker to avoid sharing mutable Random across threads
    private static final ThreadLocal<Faker> FAKER = ThreadLocal.withInitial(Faker::new);


    /**
     * Return a randomly generated Student (transient) with fake names and email.
     */
    public Student getDummyStudent() {
        return getDummyStudentWithDomain(null);
    }

    /**
     * Return a randomly generated Student (transient) with an email forced to the provided domain
     * if domain is non-null/blank.
     *
     * @param domain optional email domain (example: "example.com"). If null or blank, faker will produce an email.
     * @return generated transient Student (rollNo will be null until persisted)
     */
    public Student getDummyStudentWithDomain(String domain) {
        Faker faker = FAKER.get();

        String fname = safeCapitalize(faker.name().firstName());
        String lname = safeCapitalize(faker.name().lastName());

        String email;
        if (domain != null && !domain.isBlank()) {
            // build a simple email using names and the provided domain
            String local = (fname + "." + lname).replaceAll("[^A-Za-z0-9._-]", "").toLowerCase();
            if (local.isBlank()) local = "user" + Math.abs(faker.hashCode());
            email = local + "@" + domain;
        } else {
            email = faker.internet().emailAddress();
        }

        // Normalize email and names to avoid nulls
        email = Objects.requireNonNullElse(email, "unknown@example.com");
        Address address = getDummyAddress();
        Machine machine = getDummyMachine();
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            books.add(new Book(faker.book().title(), faker.book().author()));
        }
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            teachers.add(getDummyTeacher());
        }
        Student s = new Student(fname, lname, email, address, machine, books, teachers);
//        teachers.forEach(teacher -> {
//            teacher.getStudents().add(s);
//        });
        return s;
    }

    private String safeCapitalize(String s) {
        if (s == null || s.isBlank()) return "";
        s = s.trim();
        if (s.length() == 1) return s.toUpperCase();
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public Address getDummyAddress() {
        Faker faker = FAKER.get();

        String city = safeCapitalize(faker.address().city());
        String state = safeCapitalize(faker.address().state());
        String country = safeCapitalize(faker.address().country());

        return new Address(city, state, country);
    }

    public Teacher getDummyTeacher() {
        Faker faker = FAKER.get();
        String fname = safeCapitalize(faker.name().firstName());
        String lname = safeCapitalize(faker.name().lastName());
        String email = faker.internet().emailAddress();
        List<Student> students = new ArrayList<>();
        return new Teacher(fname, lname, email,students);
    }

    public Machine getDummyMachine() {
        Faker faker = FAKER.get();
        String machineName = safeCapitalize("MCN" + faker.number().numberBetween(00001, 99999));
        String machineType = safeCapitalize("LAPTOP");
        return new Machine(machineName, machineType);
    }
}
