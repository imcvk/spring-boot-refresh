package org.hibernate.Chapter1.main;

import org.hibernate.Chapter1.crud.DB_Operations;
import org.hibernate.Chapter1.dummydatagenerator.DummyData;
import org.hibernate.Chapter1.pojo.Student;

import java.util.ArrayList;

public class MainApp {
    public static void main(String[] args) {
        DummyData dummyData = new DummyData();
        Student s;
        DB_Operations crud = new DB_Operations();

        try {
            s = dummyData.getDummyStudent();
            crud.createStudent(s); // after this, s.getRollNo() should be populated
            System.out.println("Persisted student: " + s);

            // Demonstrate retrieval by id
            Integer id = s.getRollNo();
            if (id != null) {
                Student fetched = crud.getStudentById(id);
                System.out.println("Fetched student by id: " + fetched);
            }

            // Example update (uncomment to test)
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
                System.out.println(student);
            }
        } finally {
            // Ensure we close the SessionFactory when the app exits
            crud.closeSessionFactory();
        }
    }
}
