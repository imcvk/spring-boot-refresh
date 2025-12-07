package org.hibernate.Chapter1_mapping.main;

import org.hibernate.commonutil.crud.DB_Operations;
import org.hibernate.commonutil.dummydatagenerator.DummyData;
import org.hibernate.Chapter1_mapping.pojo.Book;
import org.hibernate.Chapter1_mapping.pojo.Student;
import org.hibernate.Chapter1_mapping.pojo.Teacher;

import java.util.ArrayList;

public class MainApp {
    public static void main(String[] args) {
        DummyData dummyData = new DummyData();
        Student s;
        DB_Operations crud = new DB_Operations();

        try {
//            for (int i = 0; i < 50; i++) {
            s = dummyData.getDummyStudent();
            s.getMachine().setStudent(s);
            Student finalS = s;
            s.getBooks().forEach(book -> book.setStudent(finalS));
            s.getTeachers().forEach(teacher -> {
                teacher.getStudents().add(finalS);
            });
            crud.createStudent(s); // after this, s.getRollNo() should be populated
            System.out.println("Persisted student: " + s);
            // Demonstrate retrieval by id
            Integer id = s.getRollNo();
            if (id != null) {
                Student fetched = crud.getStudentById(id);
                printStudent(fetched);
            }
//            }
//             Example update (uncomment to test)
            Student s2 = dummyData.getDummyStudent();
            s.setfName(s2.getfName());
            s.setlName(s2.getlName());
            s.setEmail(s2.getEmail());
            crud.updateStudent(s);
            System.out.println("Updated student: " + s);
            // Delete operation can be added similarly
            crud.deleteStudent(s);
            System.out.println("Deleted student with id: " + s.getRollNo());
            ArrayList<Student> students = new ArrayList<>(crud.getAllStudents());
            System.out.println("All students in DB:");
            for (Student student : students) {
                printStudent(student);
            }
        } finally {
            // Ensure we close the SessionFactory when the app exits
            crud.closeSessionFactory();
        }
    }

    public static void printStudent(Student student) {
        System.out.println("-------------------------------");
        System.out.println("Student Details:");
        System.out.println("Roll No: " + student.getRollNo() + "Name: " + student.getfName() + " " + student.getlName() + "Email: " + student.getEmail() + " " + "Address: " + student.getAddress() + "Machine: " + student.getMachine()+",");
        System.out.println(printBookDetails(student)+",");
        System.out.println(printBookDetails(student));
        System.out.println("-------------------------------");
    }

    public static String printBookDetails(Student student) {
        Book studentBooks = student.getBooks().get(0);
        return "Book ID: " + studentBooks.getBookId() + ", Title: " + studentBooks.getTitle() + ", Author: " + studentBooks.getAuthor();

    }

    public static String printTeacherDetails(Student student) {
        Teacher teacher = student.getTeachers().get(0);
        return "Teacher ID: " + teacher.getId() + ", Name: " + teacher.getMentorName() + ", Subject: " + teacher.getMentorExpertise();

    }
}
