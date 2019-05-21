package  com.myorg.course.controller;

import com.myorg.course.ApplicationRunner;
import com.myorg.course.dao.Course;
import com.myorg.course.integration.AbstractTest;
import com.myorg.course.service.CourseService;
import com.myorg.course.utils.Constants;
import com.sun.org.apache.xerces.internal.util.PropertyState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
    public void getCoursesTest()
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
        course.setDueDate(new Date());
        course.setStartDate(new Date());
        String inputJson = super.mapToJson(course);
        String uri = "/course/add";
        List<Course> courseList = Arrays.asList(course);

        given(courseService.add(course)).willReturn(Constants.COURSE_ADD_SUCCESS);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 200);
        Assert.assertTrue(result.getResponse().getContentAsString().equals(Constants.COURSE_ADD_SUCCESS));
    }
}
