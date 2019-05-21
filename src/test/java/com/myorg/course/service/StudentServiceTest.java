package com.myorg.course.service;


import com.myorg.course.dao.Course;
import com.myorg.course.dao.Student;
import com.myorg.course.dto.RegisterRequest;
import com.myorg.course.dto.UnregisterRequest;
import com.myorg.course.repository.CourseRepository;
import com.myorg.course.repository.StudentRepository;
import com.myorg.course.utils.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private StudentService studentService;


    List<Course> list = new ArrayList<>();
    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerTest(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setCourseList(new Integer[]{1});
        Student student = new Student("", "", "", "");
        registerRequest.setStudent(student);
        Course course = new Course();
        course.setTitle("Course Testing");
        course.setStatus(Constants.AVAILABLE);
        course.setStartDate(new Date());
        course.setDueDate(new Date());
        Mockito.when(courseRepository.getOne(1)).thenReturn(course);
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        Map<Integer, String> res1 = studentService.register(registerRequest);
        Assert.assertEquals(res1.size(), 1);

    }

    @Test
    public void unregisterTest(){
        UnregisterRequest unregisterRequest = new UnregisterRequest();
        unregisterRequest.setEmail("john.snow@org.com");
        unregisterRequest.setCourseId(new Integer[]{1});
        Course course = new Course();
        course.setTitle("Course Testing");
        course.setStatus(Constants.AVAILABLE);
        course.setStartDate(new Date());
        course.setDueDate(new Date());
        Student student = new Student("","","","");
        Mockito.when(courseRepository.getOne(1)).thenReturn(course);
        Mockito.when(studentRepository.findByEmail("john.snow@org.com")).thenReturn(student);
        Mockito.when(courseRepository.save(course)).thenReturn(course);
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        Map<Integer, String> res1 = studentService.unregister(unregisterRequest);
        Assert.assertEquals(res1.size(), 1);

    }

    @Test
    public void getCoursesTest(){
        Student student = new Student("","john.snow@org.com","","");
        Course course = new Course();
        course.setTitle("Course Testing");
        course.setStatus(Constants.AVAILABLE);
        course.setStartDate(new Date());
        course.setDueDate(new Date());
        Set<Course> courses = new HashSet<>();
        courses.add(course);
        student.setCourses(courses);
        Mockito.when(studentRepository.findByEmail("john.snow@org.com")).thenReturn(student);
        Set<Course> courseList = studentService.get("john.snow@org.com");
        Assert.assertEquals(courseList.size(), 1);

    }

    @Test
    public void findStudentsTest(){
        Student student = new Student("","john.snow@org.com","","");
        Course course = new Course();
        course.setTitle("Course Testing");
        course.setStatus(Constants.AVAILABLE);
        course.setStartDate(new Date());
        course.setDueDate(new Date());
        Set<Course> courses = new HashSet<>();
        courses.add(course);
        student.setCourses(courses);
        Set<Student> students = new HashSet<>();
        students.add(student);
        Mockito.when(studentRepository.findStudents(1)).thenReturn(students);
        Set<Student> studentsList = studentService.findStudents(1);
        Assert.assertEquals(studentsList.size(), 1);

    }
}
