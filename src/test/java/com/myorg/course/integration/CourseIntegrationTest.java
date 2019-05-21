package com.myorg.course.integration;


import com.myorg.course.ApplicationRunner;
import com.myorg.course.dao.Course;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationRunner.class, webEnvironment  = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CourseIntegrationTest extends AbstractTest{

    @Before
    public void setup(){
        super.setUp();
    }

    @Test
    public void addCourseTest() throws Exception{
        Course course = new Course();
        course.setTitle("Course Testing");
        course.setCharges(1000);
        course.setDueDate(new Date());
        course.setStartDate(new Date());
        String inputJson = super.mapToJson(course);
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
    public void updateCourseTest() throws Exception{
        Course course = new Course();
        course.setTitle("Course Testing");
        course.setCharges(1000);
        course.setDueDate(new Date());
        course.setStartDate(new Date());
        String inputJson = super.mapToJson(course);
        List<Course> courseList = Arrays.asList(course);
        String uri = "/course/update";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, Constants.COURSE_UPDATE_SUCCESS);
    }

    @Test
    public void deleteCoursesTest() throws Exception{
        Course course = new Course();
        course.setTitle("Course Testing");
        course.setCharges(1000);
        course.setDueDate(new Date());
        course.setStartDate(new Date());
        String inputJson = super.mapToJson(course);
        List<Course> courseList = Arrays.asList(course);
        String uri = "/course/add";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        uri = "/course/delete/1";
        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult2.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult2.getResponse().getContentAsString();
        assertTrue(content.length()>0);
    }

    @Test
    public void viewCoursesTest() throws Exception{
        Course course = new Course();
        course.setTitle("Course Testing");
        course.setCharges(1000);
        course.setDueDate(new Date());
        course.setStartDate(new Date());
        String inputJson = super.mapToJson(course);
        List<Course> courseList = Arrays.asList(course);
        String uri = "/course/add";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        uri = "/course/view";
        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult2.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult2.getResponse().getContentAsString();
        assertTrue(content.length()>0);
    }
}
