package com.myorg.course.controller;


import com.myorg.course.model.Course;
import com.myorg.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @RequestMapping("/view")
    public ResponseEntity<List<Course>> get(){
        List<Course> couseList = courseService.get();
        couseList.forEach(System.out::println);
        return ResponseEntity.ok(couseList);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Course course){
        courseService.add(course);
        return ResponseEntity.ok("Course added successfully");
    }


}
