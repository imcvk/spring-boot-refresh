package org.hibernate.Chapter1.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

@Entity
@Table(name = "student")
@Access(AccessType.FIELD) // optional: explicit field access
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // choose IDENTITY/SEQUENCE per your DB
    private Integer rollNo;

    @Column(name = "first_name") // optional: match DB column names
    private String fName;

    @Column(name = "last_name")
    private String lName;

    private String email;

    // Required by JPA
    protected Student() { }

    // Use a constructor without rollNo for new transient instances
    public Student(String fName, String lName, String email) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
    }

    // getters / setters; omit setRollNo or make it protected if id is generated
    public Integer getRollNo() { return rollNo; }
    protected void setRollNo(Integer rollNo) { this.rollNo = rollNo; }

    public String getfName() { return fName; }
    public void setfName(String fName) { this.fName = fName; }

    public String getlName() { return lName; }
    public void setlName(String lName) { this.lName = lName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Student{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", email='" + email + '\'' +
                ", rollNo=" + rollNo +
                '}';
    }
}
