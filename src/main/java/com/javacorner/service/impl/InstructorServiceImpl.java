package com.javacorner.service.impl;

import com.javacorner.dao.InstructorDao;
import com.javacorner.entity.Course;
import com.javacorner.entity.Instructor;
import com.javacorner.entity.User;
import com.javacorner.service.CourseService;
import com.javacorner.service.InstructorService;
import com.javacorner.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private InstructorDao instructorDao;

    private CourseService courseService;

    private UserService userService;

    public InstructorServiceImpl(InstructorDao instructorDao, CourseService courseService, UserService userService) {
        this.instructorDao = instructorDao;
        this.courseService = courseService;
        this.userService = userService;
    }

    @Override
    public Instructor loadInstructorById(Long instructorId) {
        return instructorDao.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instructor with id " + instructorId + " Not Found"));
    }

    @Override
    public List<Instructor> findInstructorsByName(String name) {
        return instructorDao.findInstructorsByName(name);
    }

    @Override
    public Instructor loadInstructorByEmail(String email) {
        return instructorDao.findInstructorByEmail(email);
    }

    @Override
    public Instructor createInstructor(String firstName, String lastName, String summary, String email, String password) {
        User user = userService.createUser(email, password);
        userService.assignRoleToUser(email, "Instructor");
        return instructorDao.save(new Instructor(firstName, lastName, summary, user));
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        return instructorDao.save(instructor);
    }

    @Override
    public List<Instructor> fetchInstructors() {
        return instructorDao.findAll();
    }

    @Override
    public void removeInstructor(Long instructorId) {
        Instructor instructor = loadInstructorById(instructorId);
        for(Course course : instructor.getCourses()) {
            courseService.removeCourse(course.getCourseId());
        }
        instructorDao.deleteById(instructorId);
    }
}
