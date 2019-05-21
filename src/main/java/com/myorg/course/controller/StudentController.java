package com.myorg.course.controller;


import com.myorg.course.dao.Course;
import com.myorg.course.dao.Student;
import com.myorg.course.service.StudentService;
import com.myorg.course.dto.RegisterRequest;
import com.myorg.course.dto.UnregisterRequest;
import com.myorg.course.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Api(value = "Student information", description = "Api related to student information")
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @ApiOperation(value="Returns courses registered by user")
    @RequestMapping(path="/view/{email}", method = RequestMethod.GET)
    public ResponseEntity<Set<Course>> get(@PathVariable("email") String email){
        return ResponseEntity.ok(studentService.get(email));
    }

    @ApiOperation(value="api for registering to course")
    @RequestMapping(path="/register", method = RequestMethod.POST)
    public ResponseEntity<Map> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(studentService.register(registerRequest));
    }

    @ApiOperation(value="api for deregistering to course")
    @RequestMapping(path="/unregister", method = RequestMethod.POST)
    public ResponseEntity<Map> unregister(@RequestBody UnregisterRequest unregisterRequest){
        return ResponseEntity.ok( studentService.unregister(unregisterRequest));
    }

    @ApiOperation(value="api for getting all students registerd per course")
    @RequestMapping(path="/findall/{courseId}", method = RequestMethod.GET)
    public ResponseEntity<Set<Student>> findStudents(@PathVariable("courseId") Integer courseId){
        return ResponseEntity.ok( studentService.findStudents(courseId));
    }
}
