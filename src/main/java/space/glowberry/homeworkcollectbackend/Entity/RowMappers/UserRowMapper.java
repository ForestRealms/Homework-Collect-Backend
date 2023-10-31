package space.glowberry.homeworkcollectbackend.Entity.RowMappers;

import org.springframework.jdbc.core.RowMapper;
import space.glowberry.homeworkcollectbackend.Entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getString("username"),
                rs.getString("password"),
                rs.getInt("id"),
                rs.getInt("privileged") == 1);
    }
}
