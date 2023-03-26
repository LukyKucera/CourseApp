package com.javacorner.service;

import com.javacorner.entity.Student;
import java.util.List;

public interface StudentService {

    Student loadStudentById(Long studentId);

    List<Student> findStudentsByName(String name);

    Student loadStudentByEmail(String email);

    Student createStudent(String firstName, String lastName, String level, String email, String password);

    Student updateStudent(Student student);

    List<Student> fetchStudents();

    void removeStudent(Long studentId);
}
