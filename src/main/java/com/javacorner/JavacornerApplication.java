package com.javacorner;

import com.javacorner.entity.Course;
import com.javacorner.entity.Instructor;
import com.javacorner.entity.Student;
import com.javacorner.entity.User;
import com.javacorner.service.CourseService;
import com.javacorner.service.InstructorService;
import com.javacorner.service.RoleService;
import com.javacorner.service.StudentService;
import com.javacorner.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true)
public class JavacornerApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static final String ADMIN = "Admin";
    public static final String INSTRUCTOR = "Instructor";
    public static final String STUDENT = "Student";


    public static void main(String[] args) {
        SpringApplication.run(JavacornerApplication.class, args);

    }

    @Bean
    CommandLineRunner start(UserService userService, RoleService roleService,
                            StudentService studentService, InstructorService instructorService,
                            CourseService courseService) {
        return args -> {

            User user1 = userService.createUser("user1@gmail.com", "pass1");
            User user2 = userService.createUser("user2@gmail.com", "pass2");

            roleService.createRole(ADMIN);
            roleService.createRole(INSTRUCTOR);
            roleService.createRole(STUDENT);

            userService.assignRoleToUser(user1.getEmail(), ADMIN);
            //  userService.assignRoleToUser(user1.getEmail(), INSTRUCTOR);
            userService.assignRoleToUser(user2.getEmail(), STUDENT);

            Instructor instructor1 =
                instructorService.createInstructor("instructor1FN", "instructor1LN",
                    "Experienced Instructor", "instructorUser1@gmail.com", "pass1");
            Instructor instructor2 =
                instructorService.createInstructor("instructor2FN", "instructor2LN",
                    "Senior Instructor", "instructorUser2@gmail.com", "pass2");

            Student student1 =
                studentService.createStudent("std1FN", "std1LN", "beginner", "stdUser1@gmail.com",
                    "pass1");
            Student student2 =
                studentService.createStudent("std2FN", "std2LN", "master degree", "stdUser2@gmail.com",
                    "pass2");

            Course course1 =
                courseService.createCourse("Spring Service", "2 Hours", "Master Spring Service",
                    instructor1.getInstructorId());
            Course course2 =
                courseService.createCourse("Spring Data JPA", "4 Hours", "Introduction to JPA",
                    instructor2.getInstructorId());

            courseService.assignStudentToCourse(course1.getCourseId(), student1.getStudentId());
            courseService.assignStudentToCourse(course2.getCourseId(), student2.getStudentId());

        };
    }
}
