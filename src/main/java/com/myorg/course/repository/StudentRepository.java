package com.myorg.course.repository;

import com.myorg.course.dao.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface StudentRepository  extends JpaRepository<Student, Integer> {

    public Student findByEmail(String email);

    @Query(value = "SELECT * FROM student S INNER JOIN student_course cs ON S.student_id = cs.student_id where cs.course_id= :courseId", nativeQuery = true)
    Set<Student> findStudents(@Param("courseId") int courseId);
}
