package com.javacorner.dao;

import com.javacorner.entity.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {
    @Query(value = "select s from Student as s where s.firstName like %:name% or s.lastName like %:name%")
    List<Student> findStudentsByName(@Param("name") String name);

    @Query(value = "select s from Student as s where s.user.email=:email")
    Student findStudentByEmail(@Param("email") String email);

}
