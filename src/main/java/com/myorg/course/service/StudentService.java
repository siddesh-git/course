package com.myorg.course.service;

import com.myorg.course.dao.Course;
import com.myorg.course.dao.Student;
import com.myorg.course.repository.CourseRepository;
import com.myorg.course.repository.StudentRepository;
import com.myorg.course.dto.RegisterRequest;
import com.myorg.course.dto.UnregisterRequest;
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

    public Set<Course> get(String email){
        Set<Course> courses = new HashSet<>();
        Student student = studentRepository.findByEmail(email);
        if(student!=null) {
            courses = student.getCourses();
            Iterator<Course> coursesIt = courses.iterator();
            while (coursesIt.hasNext()) {
                coursesIt.next().setStatus(Constants.REGISTERED);
            }
        }
        return courses;
    }

    public Set<Student> findStudents(int courseId){
        Set<Student> students = studentRepository.findStudents(courseId);
        Iterator<Student> studentIterator = students.iterator();
        while(studentIterator.hasNext()){
            studentIterator.next().getCourses().clear();
        }
        return students;
    }

    public Map<Integer, String> register(RegisterRequest registerRequest){

        Integer[] courseList = registerRequest.getCourseList();
        Map<Integer, String> result = new HashMap<>();
        Student studentObj = registerRequest.getStudent();
        if(courseList == null || courseList.length == 0){
            updateStudentInfo(studentObj);
            studentRepository.save(studentObj);
            result.put(0, Constants.STUDENT_UPDATE);
        }else{
            for(Integer courseId: courseList){
                Student student = new Student(studentObj.getFname(),studentObj.getLname(),
                        studentObj.getEmail(),studentObj.getPhone());
                Student student1 = studentRepository.findByEmail(studentObj.getEmail());
                Course course = courseRepository.getOne(courseId);
                if(student1 != null){
                    boolean flag = false;
                    Iterator<Course> courseIterator = student1.getCourses().iterator();
                    while(courseIterator.hasNext()){
                        if(courseIterator.next().getCourseId() == courseId){
                            result.put(courseId, Constants.ALREADY_REGISTERED);
                            flag=true;
                            break;
                        }
                    }
                    if(!flag){
                        registerCourse(result, student1, course);
                    }

                }else{
                    registerCourse(result, student, course);
                }
            }
        }

        return result;
    }

    private void updateStudentInfo(Student studentObj) {
        studentObj.setLastModifiedTs(new Date());
        studentObj.setCreateBy(studentObj.getEmail());
        studentObj.setUpdateBy(studentObj.getEmail());
    }

    private void registerCourse(Map<Integer, String> result, Student student, Course course) {
        if(course.getStatus().equalsIgnoreCase(Constants.AVAILABLE)
                        || course.getStatus().equalsIgnoreCase(Constants.UPCOMING)){
            student.getCourses().add(course);
            updateStudentInfo(student);
            studentRepository.save(student);
            course.setAvailableSeats(course.getAvailableSeats()-1);
            course.setLastModifiedTs(new Date());
            courseRepository.save(course);
            result.put(course.getCourseId(), Constants.REGISTERED);
        }else{
            result.put(course.getCourseId(), course.getStatus());
        }
    }

    public Map<Integer, String> unregister(UnregisterRequest unregisterRequest){
        Map<Integer, String> result = new HashMap<>();
        for(Integer courseId: unregisterRequest.getCourseId()){
            Student student1 = studentRepository.findByEmail(unregisterRequest.getEmail());
            Course course = courseRepository.getOne(courseId);
            if(student1 != null && course != null){
                if(course.getDueDate().before(new Date())){
                    result.put(courseId, Constants.CANCEL_FAILED);
                }else{
                    student1.getCourses().remove(course);
                    updateStudentInfo(student1);
                    studentRepository.save(student1);
                    course.setLastModifiedTs(new Date());
                    course.setAvailableSeats(course.getAvailableSeats()+1);
                    courseRepository.save(course);
                    result.put(courseId, Constants.UNREGISTERED);
                }
            }else{
                result.put(courseId, Constants.NOTFOUND);
            }
        }
        return result;
    }
}
