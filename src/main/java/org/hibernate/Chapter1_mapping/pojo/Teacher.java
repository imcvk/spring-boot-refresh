package org.hibernate.Chapter1_mapping.pojo;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String mentorName;
    String mentorEmail;
    String mentorExpertise;
    @ManyToMany(cascade = CascadeType.ALL)
    List<Student> students;

    public Teacher() {
    }

    public Teacher(String mentorName, String mentorEmail, String mentorExpertise, List<Student> students) {
        this.mentorName = mentorName;
        this.mentorEmail = mentorEmail;
        this.mentorExpertise = mentorExpertise;
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Teacher(String mentorName, String mentorEmail, String mentorExpertise) {
        this.mentorName = mentorName;
        this.mentorEmail = mentorEmail;
        this.mentorExpertise = mentorExpertise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public String getMentorExpertise() {
        return mentorExpertise;
    }

    public void setMentorExpertise(String mentorExpertise) {
        this.mentorExpertise = mentorExpertise;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", mentorName='" + mentorName + '\'' +
                ", mentorEmail='" + mentorEmail + '\'' +
                ", mentorExpertise='" + mentorExpertise + '\'' +
                ", students=" + students +
                '}';
    }
}
