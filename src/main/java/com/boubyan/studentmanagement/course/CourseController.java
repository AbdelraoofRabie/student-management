package com.boubyan.studentmanagement.course;

import com.boubyan.studentmanagement.common.GenericResponse;
import com.boubyan.studentmanagement.courseregisteration.CourseRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CourseController {
    private final CourseRepository courseRepository;
    private final CourseRegistrationService courseRegistrationService;

    public CourseController(CourseRepository courseRepository, CourseRegistrationService courseRegistrationService) {

        this.courseRepository = courseRepository;
        this.courseRegistrationService = courseRegistrationService;
    }

    @GetMapping("/courses")
    public ResponseEntity<GenericResponse<List<Course>>> viewCourses() {

        return ResponseEntity.ok(new GenericResponse<>(courseRepository.findAll(), "Ok"));
    }

    @PostMapping("/courses")
    public ResponseEntity<GenericResponse<Course>> createCourse(@RequestBody CreateCourseRequest createCourseRequest) {

        String title = createCourseRequest.getTitle();
        String description = createCourseRequest.getDescription();
        java.sql.Date startDate = new java.sql.Date(createCourseRequest.getStartDate().getTime());
        Course savedCourse = courseRepository.save(new Course(title, description, startDate));
        return ResponseEntity.ok(new GenericResponse<>(savedCourse, "Ok"));
    }

    @PostMapping("/courses/{courseId}/register")
    public ResponseEntity<?> registerForCourse(@PathVariable int courseId) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        courseRegistrationService.registerStudentToCourse(username, courseId);
        return ResponseEntity.ok("Registered successfully");
    }

    @PostMapping("/courses/{courseId}/unregister")
    public ResponseEntity<?> unregisterForCourse(@PathVariable int courseId) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        courseRegistrationService.unregisterStudentToCourse(username, courseId);
        return ResponseEntity.ok("cancelled Registration successfully");
    }

}