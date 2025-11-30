package org.hibernate.Chapter1.pojo;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    private Address address;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private Machine machine;

    @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<Teacher> teachers;

    // Required by JPA
    protected Student() {
    }

    public Student(String fName, String lName, String email, Address address, Machine machine, List<Book> books) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.address = address;
        this.machine = machine;
        this.books = books;
    }

    // getters / setters; omit setRollNo or make it protected if id is generated
    public Integer getRollNo() {
        return rollNo;
    }

    protected void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public Address getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Machine getMachine() {
        return machine;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Student(List<Book> books, Machine machine, Address address, String email, String lName, String fName) {
        this.books = books;
        this.machine = machine;
        this.address = address;
        this.email = email;
        this.lName = lName;
        this.fName = fName;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public Student(String fName, String lName, String email, Address address, Machine machine, List<Book> books, List<Teacher> teachers) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.address = address;
        this.machine = machine;
        this.books = books;
        this.teachers = teachers;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

//    public String toString() {
//        String s="";
//        for(Teacher t: teachers){
//            s+=t.getMentorName()+", ";
//        }
//        return "Student{" +
//                "rollNo=" + rollNo +
//                ", fName='" + fName + '\'' +
//                ", lName='" + lName + '\'' +
//                ", email='" + email + '\'' +
//                ", address=" + address +
//                ", machine=" + machine +
//                ", books=" + books +
//                ", teachers=" + s+
//                '}';
//    }

//    @Override
//    public String toString() {
//        String s = "Student{" +
//                "rollNo=" + rollNo +
//                ", fName='" + fName + '\'' +
//                ", lName='" + lName + '\'' +
//                ", email='" + email + '\'' +
//                ", address=" + address +
//                ", books=" + books +
//                ", machine=" + machine +
//                '}';
//        return s;
//    }
}
