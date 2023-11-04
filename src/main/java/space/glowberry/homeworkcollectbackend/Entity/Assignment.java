package space.glowberry.homeworkcollectbackend.Entity;

public class Assignment {
    private int id;
    private int homework_id;
    private int user_id;
    private boolean completed;

    public Assignment(int id, int homework_id, int user_id, boolean completed) {
        this.id = id;
        this.homework_id = homework_id;
        this.user_id = user_id;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(int homework_id) {
        this.homework_id = homework_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", homework_id=" + homework_id +
                ", user_id=" + user_id +
                ", completed=" + completed +
                '}';
    }
}
