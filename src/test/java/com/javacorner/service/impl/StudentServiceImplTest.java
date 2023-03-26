package com.javacorner.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.javacorner.dao.StudentDao;
import com.javacorner.entity.Course;
import com.javacorner.entity.Student;
import com.javacorner.entity.User;
import com.javacorner.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    StudentDao studentDao;

    @Mock
    UserService userService;

    @InjectMocks
    StudentServiceImpl studentService;

    @Test
    void testLoadStudentById() {
        Student student = new Student();
        student.setStudentId(1L);

        when(studentDao.findById(any())).thenReturn(Optional.of(student));

        Student actualStudent = studentService.loadStudentById(1L);

        assertEquals(student, actualStudent);

    }

    @Test
    void testExceptionForNotFoundUserById() {
        assertThrows(EntityNotFoundException.class, () -> studentService.loadStudentById(any()));
    }

    @Test
    void testFindStudentsByName() {
        studentService.findStudentsByName("name");
        verify(studentDao).findStudentsByName(any());
    }

    @Test
    void testLoadStudentByEmail() {
        String email = "test@gmail.com";
        studentService.loadStudentByEmail(email);
        verify(studentDao).findStudentByEmail(email);
    }

    @Test
    void testCreateStudent() {
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setPassword("pass");

        when(userService.createUser(any(), any())).thenReturn(user);
        studentService.createStudent("FN", "LN", "master", "user@gmail.com", "pass");
        verify(studentDao).save(any());
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student();
        student.setStudentId(1L);
        studentService.updateStudent(student);
        verify(studentDao).save(student);
    }

    @Test
    void testFetchStudents() {
        studentService.fetchStudents();
        verify(studentDao, times(1)).findAll();
    }

    @Test
    void testRemoveStudent() {
        Student student = new Student();
        student.setStudentId(1L);
        Course course = new Course();
        course.setCourseId(1L);
        student.getCourses().add(course);

        when(studentDao.findById(any())).thenReturn(Optional.of(student));

        studentService.removeStudent(1L);

        verify(studentDao).deleteById(any());
    }
}