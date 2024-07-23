package com.boubyan.studentmanagement.student;

import com.boubyan.studentmanagement.common.BaseRepository;
import com.boubyan.studentmanagement.student.Student;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends BaseRepository<Student, Integer> {
    Optional<Student> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
