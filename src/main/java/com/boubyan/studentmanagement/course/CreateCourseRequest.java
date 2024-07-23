package com.boubyan.studentmanagement.course;

import java.io.Serializable;
import java.util.Date;

public class CreateCourseRequest implements Serializable {
    private static final long serialVersionUID = -7725078085228066666L;

    private String title;
    private String description;

    private Date startDate;

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
}
