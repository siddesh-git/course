package com.myorg.course.dto;

import com.myorg.course.dao.Student;

public class RegisterRequest {

    private Student student;
    private Integer[] courseList;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer[] getCourseList() {
        return courseList;
    }

    public void setCourseList(Integer[] courseList) {
        this.courseList = courseList;
    }
}
