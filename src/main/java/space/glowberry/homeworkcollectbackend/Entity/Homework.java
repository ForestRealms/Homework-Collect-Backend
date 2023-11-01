package space.glowberry.homeworkcollectbackend.Entity;

import java.sql.Time;
import java.util.Date;

public class Homework {
    private int id;
    private String title;
    private String description;
    private Date due;

    public Homework(int id, String title, String description, Date due) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.due = due;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }
}
