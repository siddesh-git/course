package com.myorg.course.repository;

import com.myorg.course.dao.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query(value = "SELECT * FROM course c INNER JOIN student_course cs ON c.course_id = cs.course_id " +
            "INNER JOIN student s ON s.student_id = cs.student_id where s.email= :email and cs.course_id= :courseId", nativeQuery = true)
    Course courseStatus(@Param("email") String email, @Param("courseId") int courseId);
}
