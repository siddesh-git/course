package com.myorg.course.repository;

import com.myorg.course.dao.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
