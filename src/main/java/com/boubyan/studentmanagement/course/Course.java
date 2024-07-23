package com.boubyan.studentmanagement.course;

import com.boubyan.studentmanagement.common.BaseEntity;
import com.boubyan.studentmanagement.student.Student;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="t_courses")
public class Course  extends BaseEntity<Integer> {

    private static final long serialVersionUID = -7725078085228066679L;

    private String title;
    private String description;

    private Date startDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    private Set<Student> students = new HashSet<>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Course(String title, String description, Date startDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
    }

    public Course() {
    }
}
