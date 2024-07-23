package com.boubyan.studentmanagement.course;

import com.boubyan.studentmanagement.common.BaseRepository;
import com.boubyan.studentmanagement.course.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends BaseRepository<Course, Integer> {

}
