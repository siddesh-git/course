package com.myorg.course.controller;


import com.myorg.course.model.Student;
import com.myorg.course.service.StudentService;
import com.myorg.course.transferobjects.RegisterRequest;
import com.myorg.course.transferobjects.UnregisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping(path="/view/{email}")
    public ResponseEntity<List<Student>> get(@PathVariable("email") String email){
        return ResponseEntity.ok(studentService.get(email));
    }

    @RequestMapping(path="/register")
    public ResponseEntity<Map> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(studentService.register(registerRequest));
    }

    @RequestMapping(path="/unregister")
    public ResponseEntity<Map> unregister(@RequestBody UnregisterRequest unregisterRequest){
        return ResponseEntity.ok( studentService.unregister(unregisterRequest));
    }
}
