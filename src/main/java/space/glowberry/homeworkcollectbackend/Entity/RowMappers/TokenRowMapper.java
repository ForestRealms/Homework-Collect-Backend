package space.glowberry.homeworkcollectbackend.Entity.RowMappers;

import org.springframework.jdbc.core.RowMapper;
import space.glowberry.homeworkcollectbackend.Entity.Token;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenRowMapper implements RowMapper<Token> {
    @Override
    public Token mapRow(ResultSet rs, int rowNum) throws SQLException {
        String value = rs.getString("token");
        Date expired = rs.getDate("expired_at");
        String owner = rs.getString("owner");
        return new Token(value, expired, owner);
    }
}
