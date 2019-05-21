package com.myorg.course.controller;

import com.myorg.course.ApplicationRunner;
import com.myorg.course.dao.Course;
import com.myorg.course.dao.Student;
import com.myorg.course.dto.RegisterRequest;
import com.myorg.course.dto.UnregisterRequest;
import com.myorg.course.integration.AbstractTest;
import com.myorg.course.service.StudentService;
import com.myorg.course.utils.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationRunner.class)
@ActiveProfiles("test")
@WebAppConfiguration
public class StudentControllerTest extends AbstractTest {

    @MockBean
    StudentService studentService;

    @Before
    public void setup(){
        super.setUp();
    }

    @Test
    public void viewRegisteredCoursesTest()
            throws Exception {

        Course course = new Course();
        course.setTitle("Course Testing");

        Set<Course> courseList = new HashSet<>();
        courseList.add(course);
        String email = "test@org.com";
        given(studentService.get(email)).willReturn(courseList);

        mvc.perform(get("/student/view/"+email)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void registerTest()
            throws Exception {

        String uri = "/student/register";
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setCourseList(new Integer[]{1,2});
        registerRequest.setStudent(new Student("","","",""));
        Map<Integer, String> res = new HashMap<>();
        String inputJson = super.mapToJson(registerRequest);
        res.put(1, Constants.REGISTERED);
        given(studentService.register(registerRequest)).willReturn(res);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 200);
        Assert.assertTrue(result.getResponse().getContentAsString().length()>0);
    }

    @Test
    public void unregisterTest()
            throws Exception {

        String uri = "/student/unregister";
        UnregisterRequest unregisterRequest = new UnregisterRequest();
        unregisterRequest.setCourseId(new Integer[]{1,2});
        unregisterRequest.setEmail("test@org.com");
        Map<Integer, String> res = new HashMap<>();
        String inputJson = super.mapToJson(unregisterRequest);
        res.put(1, Constants.UNREGISTERED);
        given(studentService.unregister(unregisterRequest)).willReturn(res);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 200);
        System.out.println(result.getResponse().getContentAsString());
        Assert.assertTrue(result.getResponse().getContentAsString().length()>0);
    }


    @Test
    public void findAllStudentsTest()
            throws Exception {

        Student student = new Student("","","","");

        Set<Student> studentList = new HashSet<>();
        studentList.add(student);
        String email = "test@org.com";
        given(studentService.findStudents(1)).willReturn(studentList);

        mvc.perform(get("/student/findall/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }


    @Test
    public void viewAllStudentsTest()
            throws Exception {

        Student student = new Student("","","","");

        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        given(studentService.get()).willReturn(studentList);

        mvc.perform(get("/student/view")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
