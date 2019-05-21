package com.myorg.course.service;

import com.myorg.course.dao.Course;
import com.myorg.course.repository.CourseRepository;
import com.myorg.course.utils.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private CourseService courseService;


    List<Course> list = new ArrayList<>();
    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void viewCourseTest() {
        Course course = new Course();
        course.setTitle("Course Testing");
        list.add(course);
        Mockito.when(courseRepository.findAll()).thenReturn(list);
        List<Course> returnList = courseService.get();
        Assert.assertEquals(returnList.get(0).getTitle(), "Course Testing");
    }


    @Test
    public void addCourseTest() {
        Course course = new Course();
        course.setTitle("Course Testing");
        course.setStartDate(new Date());
        course.setDueDate(new Date());
        Mockito.when(courseRepository.save(course)).thenReturn(course);

        Assert.assertEquals(courseService.add(course), Constants.COURSE_ADD_SUCCESS);
    }

}
