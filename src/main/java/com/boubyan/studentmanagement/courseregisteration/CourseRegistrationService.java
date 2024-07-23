package com.boubyan.studentmanagement.courseregisteration;

import com.boubyan.studentmanagement.course.Course;
import com.boubyan.studentmanagement.course.CourseRepository;
import com.boubyan.studentmanagement.student.Student;
import com.boubyan.studentmanagement.student.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseRegistrationService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseRegistrationService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public void registerStudentToCourse(String studentUsername, Integer courseId) {
        Student student = studentRepository.findByUsername(studentUsername).orElseThrow(IllegalArgumentException::new);
        Course course = courseRepository.findById(courseId).orElseThrow(IllegalArgumentException::new);
        student.getCourses().add(course);
        course.getStudents().add(student);
        studentRepository.save(student);
        courseRepository.save(course);
    }

    @Transactional
    public void unregisterStudentToCourse(String studentUsername, Integer courseId) {
        Student student = studentRepository.findByUsername(studentUsername).orElseThrow(IllegalArgumentException::new);
        Course course = courseRepository.findById(courseId).orElseThrow(IllegalArgumentException::new);
        student.getCourses().remove(course);
        course.getStudents().remove(student);
        studentRepository.save(student);
        courseRepository.save(course);
    }

}
