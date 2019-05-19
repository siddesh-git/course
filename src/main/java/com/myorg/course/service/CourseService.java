package com.myorg.course.service;


import com.myorg.course.model.Course;
import com.myorg.course.repository.CourseRepository;
import com.myorg.course.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}
