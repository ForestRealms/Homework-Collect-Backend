package space.glowberry.homeworkcollectbackend.Entity.RowMappers;

import org.springframework.jdbc.core.RowMapper;
import space.glowberry.homeworkcollectbackend.Entity.Homework;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeworkRowMapper implements RowMapper<Homework> {
    @Override
    public Homework mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Homework(rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getTimestamp("due"));
    }
}
