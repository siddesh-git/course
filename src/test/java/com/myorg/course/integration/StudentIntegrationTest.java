package com.myorg.course.integration;

import com.myorg.course.ApplicationRunner;
import com.myorg.course.dao.Course;
import com.myorg.course.dao.Student;
import com.myorg.course.dto.RegisterRequest;
import com.myorg.course.dto.UnregisterRequest;
import com.myorg.course.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationRunner.class, webEnvironment  = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StudentIntegrationTest extends  AbstractTest{


    @Before
    public void setup()throws Exception{
        super.setUp();
        addCourse();
    }

    private void addCourse() throws Exception{
        Course course = new Course();
        course.setTitle("Course Testing");
        course.setStatus(Constants.AVAILABLE);
        course.setCharges(1000);
        course.setAvailableSeats(20);
        course.setDueDate(new Date(new Date().getTime() + 2 * 3600*1000));
        course.setStartDate(new Date(new Date().getTime() + 2 * 3600*1000));
        String inputJson = mapToJson(course);
        List<Course> courseList = Arrays.asList(course);
        String uri = "/course/add";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, Constants.COURSE_ADD_SUCCESS);
    }

    @Test
    public void registerTest() throws Exception{
        Course course = new Course();
        course.setDueDate(new Date(new Date().getTime() + 2 * 3600*1000));
        course.setStartDate(new Date(new Date().getTime() + 2 * 3600*1000));
        course.setStatus(Constants.AVAILABLE);
        course.setAvailableSeats(20);
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setCourseList(new Integer[]{1});
        registerRequest.setStudent(new Student("john","snow","john.snow@org.com","123"));
        Map<Integer, String> res = new HashMap<>();
        String inputJson = mapToJson(registerRequest);
        res.put(1, Constants.REGISTERED);

        String uri = "/student/register";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void getCoursesTest() throws Exception{
        String email="john.snow@org.com";
        String uri = "/student/view/"+email;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void findStudentsTest() throws Exception{
        String uri = "/student/findall/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void viewAllStudentsTest() throws Exception{
        String uri = "/student/view";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }


    @Test
    public void unregisterTest() throws Exception{
        registerTest();
        Course course = new Course();
        course.setDueDate(new Date());
        course.setStartDate(new Date());
        course.setStatus(Constants.AVAILABLE);
        course.setAvailableSeats(20);
        UnregisterRequest unregisterRequest = new UnregisterRequest();
        unregisterRequest.setCourseId(new Integer[]{1});
        unregisterRequest.setEmail("john.snow@org.com");

        Map<Integer, String> res = new HashMap<>();
        String inputJson = mapToJson(unregisterRequest);
        res.put(1, Constants.UNREGISTERED);

        String uri = "/student/unregister";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}
