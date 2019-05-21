package com.myorg.course.controller;


import com.myorg.course.dao.Course;
import com.myorg.course.service.CourseService;
import com.myorg.course.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
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
    public ResponseEntity add(@RequestBody  @Valid Course course){
        return ResponseEntity.ok(courseService.add(course));
    }

    @ApiOperation(value="api to update course")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody  @Valid Course course){
        courseService.update(course);
        return ResponseEntity.ok(Constants.COURSE_UPDATE_SUCCESS);
    }

    @ApiOperation(value="api to delete course")
    @RequestMapping(value = "/delete/{courseId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("courseId") Integer courseId){
        return ResponseEntity.ok(courseService.delete(courseId));
    }


}
