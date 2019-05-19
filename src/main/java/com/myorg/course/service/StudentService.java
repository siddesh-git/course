package com.myorg.course.service;

import com.myorg.course.model.Course;
import com.myorg.course.model.Student;
import com.myorg.course.repository.CourseRepository;
import com.myorg.course.repository.StudentRepository;
import com.myorg.course.transferobjects.RegisterRequest;
import com.myorg.course.transferobjects.UnregisterRequest;
import com.myorg.course.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    public List<Student> get(String email){
        return studentRepository.getByEmail(email);
    }

    public Map<Integer, String> register(RegisterRequest registerRequest){

        Integer[] courseList = registerRequest.getCourseList();
        Map<Integer, String> result = new HashMap<>();
        Student studentObj = registerRequest.getStudent();
        for(Integer courseId: courseList){
            Student student = new Student(studentObj.getFname(),studentObj.getLname(),
                    studentObj.getEmail(),studentObj.getPhone());
            Student student1 = studentRepository.getStudentByEmailAndCourseId(studentObj.getEmail(), courseId);
            if(student1 != null && student1.getStatus().equals(Constants.REGISTERED)){
                result.put(courseId, Constants.ALREADY_REGISTERED);
            }else{
                Course course = courseRepository.getOne(courseId);
                if(course.getStatus().equalsIgnoreCase(Constants.AVAILABLE)
                                || course.getStatus().equalsIgnoreCase(Constants.UPCOMING)){
                    student.setStatus(Constants.REGISTERED);
                    student.setCourseId(courseId);
                    studentRepository.save(student);
                    course.setAvailableSeats(course.getAvailableSeats()-1);
                    if(course.getAvailableSeats() == 0){
                        course.setStatus(Constants.FULL);
                    }
                    courseRepository.save(course);
                    result.put(courseId, Constants.REGISTERED);
                }else{
                    result.put(courseId, course.getStatus());
                }
            }
        }
        return result;
    }

    public Map<Integer, String> unregister(UnregisterRequest unregisterRequest){
        Map<Integer, String> result = new HashMap<>();
        for(Integer courseId: unregisterRequest.getCourseId()){
            Student student1 = studentRepository.getStudentByEmailAndCourseId(unregisterRequest.getEmail(), courseId);
            Course course = courseRepository.getOne(courseId);
            if(student1 != null)
                if(student1.getStatus().equals(Constants.REGISTERED)) {
                    if(course.getDueDate().before(new Date())){
                        result.put(courseId, Constants.CANCEL_FAILED);
                    }else{
                        student1.setStatus(Constants.CANCELLED);
                        studentRepository.save(student1);
                        course.setStatus(Constants.AVAILABLE);
                        course.setAvailableSeats(course.getAvailableSeats()+1);
                        courseRepository.save(course);
                    }

                }else{
                    result.put(courseId, student1.getStatus());
            }else{
                result.put(courseId, Constants.NOTFOUND);
            }
        }
        return result;
    }
}
