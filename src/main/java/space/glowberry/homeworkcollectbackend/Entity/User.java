package space.glowberry.homeworkcollectbackend.Entity;



public class User {
    private final String username;
    private final String password;
    private final int id;
    private final boolean privileged;

    public User(String username, String password, int id, boolean privileged) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.privileged = privileged;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public boolean isPrivileged() {
        return privileged;
    }
}
