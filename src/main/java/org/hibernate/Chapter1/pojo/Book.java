package org.hibernate.Chapter1.pojo;

import jakarta.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    int bookId;
    String title;
    String author;

    @ManyToOne
    @JoinColumn(name = "student_id") // owning side
    private Student student;
    public Book() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Book(String title, String author, Student student) {
        this.title = title;
        this.author = author;
        this.student = student;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

//    @Override
//    public String toString() {
//        return "Book{" +
//                "bookId=" + bookId +
//                ", title='" + title + '\'' +
//                ", author='" + author + '\'' +
//                ", student=" + student +
//                '}';
//    }
}
