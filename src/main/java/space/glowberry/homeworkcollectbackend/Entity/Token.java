package space.glowberry.homeworkcollectbackend.Entity;

import java.util.Date;

public class Token {
    private final String value;
    private final Date expired;
    private final String owner;

    public Token(String value, Date expired, String owner) {
        this.value = value;
        this.expired = expired;
        this.owner = owner;
    }

    public String getValue() {
        return value;
    }

    public Date getExpired() {
        return expired;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isExpired(){
        return getExpired().before(new Date());
    }
}
