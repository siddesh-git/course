package com.myorg.course.repository;

import com.myorg.course.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository  extends JpaRepository<Student, Integer> {

    public List<Student> getByEmail(String email);

    public Student getStudentByEmailAndCourseId(String email, Integer courseId);
}
