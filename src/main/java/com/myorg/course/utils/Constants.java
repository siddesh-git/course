package com.myorg.course.utils;

public interface Constants {
    String AVAILABLE = "available";
    String FULL = "full";
    String REGISTERED = "registered";
    String UNREGISTERED = "unregistered";
    String UPCOMING = "upcoming";
    String WAITING = "waiting";
    String NOTAVIALBLE = "notavailable";
    String COMPLETED = "completed";
    String CANCELLED = "cancelled";
    String NOTFOUND = "notfound";
    String CANCEL_FAILED = "Unable to cancel registration as it crossed the cancellation date";
    String ALREADY_REGISTERED = "You are already registered";

    String COURSE_DUE_DATE_AFTER_START_DATE_ERROR = "Due of the course cannot be after start date";
    String COURSE_ADD_SUCCESS = "Course added successfully";
    String COURSE_DELETE_SUCCESS = "Course deleted successfully";
    String COURSE_UPDATE_SUCCESS = "Course updated successfully";
    String STUDENT_UPDATE = "Student details added/updated successfully";
}
