package space.glowberry.homeworkcollectbackend.Entity.RowMappers;

import org.springframework.jdbc.core.RowMapper;
import space.glowberry.homeworkcollectbackend.Entity.Assignment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AssignmentRowMapper implements RowMapper<Assignment> {
    @Override
    public Assignment mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        int homework_id = rs.getInt("homework_id");
        int user_id = rs.getInt("user_id");
        boolean completion_status = rs.getBoolean("completion_status");
        return new Assignment(id, homework_id, user_id, completion_status);
    }
}
