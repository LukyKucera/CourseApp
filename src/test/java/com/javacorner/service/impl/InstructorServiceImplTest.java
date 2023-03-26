package com.javacorner.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.javacorner.dao.InstructorDao;
import com.javacorner.entity.Course;
import com.javacorner.entity.Instructor;
import com.javacorner.entity.User;
import com.javacorner.service.CourseService;
import com.javacorner.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InstructorServiceImplTest {

    @Mock
    private InstructorDao instructorDao;

    @Mock
    private UserService userService;

    @Mock
    private CourseService courseService;


    @InjectMocks
    private InstructorServiceImpl instructorService;

    @Test
    void testLoadInstructorById() {
        Instructor instructor = new Instructor();
        instructor.setInstructorId(1L);

        when(instructorDao.findById(1L)).thenReturn(Optional.of(instructor));

        Instructor actualInstructor = instructorService.loadInstructorById(1L);

        assertEquals(instructor, actualInstructor);

    }

    @Test
    void testFindInstructorsByName() {
        String instructorName = "instructorFN";
        instructorService.findInstructorsByName(instructorName);
        verify(instructorDao).findInstructorsByName(instructorName);
    }

    @Test
    void testLoadInstructorByEmail() {
        String instructorEmail = "instructor01@gmail.com";
        instructorService.loadInstructorByEmail(instructorEmail);
        verify(instructorDao).findInstructorByEmail(instructorEmail);
    }

    @Test
    void testCreateInstructor() {
        User user = new User("user1@gmail.com", "test");
        when(userService.createUser(any(), any())).thenReturn(user);
        instructorService.createInstructor("fName", "lName", "summary", "user@gmail.com", "test");
        verify(instructorDao).save(any());
    }

    @Test
    void testUpdateInstructor() {
        Instructor instructor = new Instructor();
        instructor.setInstructorId(1L);

        instructorService.updateInstructor(instructor);
        verify(instructorDao).save(instructor);
    }

    @Test
    void testFetchInstructors() {
        instructorService.fetchInstructors();
        verify(instructorDao).findAll();
    }

    @Test
    void testRemoveInstructor() {
        Instructor instructor = new Instructor();
        instructor.setInstructorId(1L);
        Course course = new Course();
        course.setCourseId(1L);
        instructor.getCourses().add(course);

        when(instructorDao.findById(1L)).thenReturn(Optional.of(instructor));

        instructorService.removeInstructor(1L);

        verify(courseService, times(1)).removeCourse(any());
        verify(instructorDao).deleteById(any());

    }
}