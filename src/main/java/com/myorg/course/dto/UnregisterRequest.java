package com.myorg.course.dto;

public class UnregisterRequest {
    private String email;
    private Integer[] courseId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer[] getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer[] courseId) {
        this.courseId = courseId;
    }
}
