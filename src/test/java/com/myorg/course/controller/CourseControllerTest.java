package  com.myorg.course.controller;

import com.myorg.course.ApplicationRunner;
import com.myorg.course.dao.Course;
import com.myorg.course.integration.AbstractTest;
import com.myorg.course.service.CourseService;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationRunner.class)
@ActiveProfiles("test")
@WebAppConfiguration
public class CourseControllerTest extends AbstractTest {

    @MockBean
    CourseService courseService;

    @Before
    public void setup(){
        super.setUp();
    }

    @Test
    public void viewCoursesTest()
            throws Exception {

        Course course = new Course();
        course.setTitle("Course Testing");

        List<Course> courseList = Arrays.asList(course);

        given(courseService.get()).willReturn(courseList);

        mvc.perform(get("/course/view")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void addCoursesTest()
            throws Exception {

        Course course = new Course();
        course.setTitle("Course Testing");
        course.setCharges(1000);
        course.setDueDate((new Date(new Date().getTime() + 2 * 3600*1000)));
        course.setStartDate((new Date(new Date().getTime() + 2 * 3600*1000)));
        String inputJson = super.mapToJson(course);
        String uri = "/course/add";

        given(courseService.add(course)).willReturn(Constants.COURSE_ADD_SUCCESS);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 200);
    }


    @Test
    public void updateCourseTest()
            throws Exception {

        Course course = new Course();
        course.setTitle("Course Testing");
        course.setCharges(1000);
        course.setDueDate((new Date(new Date().getTime() + 2 * 3600*1000)));
        course.setStartDate((new Date(new Date().getTime() + 2 * 3600*1000)));
        String inputJson = super.mapToJson(course);
        String uri = "/course/update";
        given(courseService.update(course)).willReturn(Constants.COURSE_UPDATE_SUCCESS);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 200);
        Assert.assertEquals(result.getResponse().getContentAsString(), Constants.COURSE_UPDATE_SUCCESS);
    }


    @Test
    public void deleteCourseTest()
            throws Exception {
        String uri = "/course/delete/1";
        given(courseService.delete(1)).willReturn(Constants.COURSE_DELETE_SUCCESS);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 200);
        Assert.assertEquals(result.getResponse().getContentAsString(), Constants.COURSE_DELETE_SUCCESS);
    }
}
