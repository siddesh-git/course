package com.myorg.course.service;


import com.myorg.course.dao.Course;
import com.myorg.course.dao.Student;
import com.myorg.course.repository.CourseRepository;
import com.myorg.course.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public List<Course> get(){
        return courseRepository.findAll();
    }

    public String add(Course c){
        if(c.getDueDate().after(c.getStartDate())){
            return Constants.COURSE_DUE_DATE_AFTER_START_DATE_ERROR;
        }else{
            courseRepository.save(c);
            return Constants.COURSE_ADD_SUCCESS;
        }
    }

    public String delete(int courseId){
        Optional<Course> course =  courseRepository.findById(courseId);
        Course courseObj =  course.get();
        courseObj.setStatus(Constants.NOTAVIALBLE);
        courseRepository.save(courseObj);
        return Constants.COURSE_DELETE_SUCCESS;
    }

    public String update(Course course){
        courseRepository.save(course);
        return Constants.COURSE_UPDATE_SUCCESS;
    }


    public Course courseStatus(String email, Integer courseId){
        return courseRepository.courseStatus(email, courseId);
    }

}
