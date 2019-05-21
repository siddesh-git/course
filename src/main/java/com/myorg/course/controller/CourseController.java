package com.myorg.course.controller;


import com.myorg.course.dao.Course;
import com.myorg.course.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Api(value = "Course information", description = "Api related to course information")
@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @ApiOperation(value="view all courses")
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ResponseEntity<List<Course>> get(){
        List<Course> couseList = courseService.get();
        return ResponseEntity.ok(couseList);
    }

    @ApiOperation(value="api to add course")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Course course){
        courseService.add(course);
        return ResponseEntity.ok("Course added successfully");
    }


}
