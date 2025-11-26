package org.hibernate.Chapter1.dummydatagenerator;

import com.github.javafaker.Faker;
import org.hibernate.Chapter1.pojo.Student;

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

        return new Student(fname, lname, email);
    }

    private String safeCapitalize(String s) {
        if (s == null || s.isBlank()) return "";
        s = s.trim();
        if (s.length() == 1) return s.toUpperCase();
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
