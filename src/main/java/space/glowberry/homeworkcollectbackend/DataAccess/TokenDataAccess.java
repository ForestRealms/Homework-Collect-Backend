package space.glowberry.homeworkcollectbackend.DataAccess;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import space.glowberry.homeworkcollectbackend.Entity.RowMappers.TokenRowMapper;
import space.glowberry.homeworkcollectbackend.Entity.Token;

import java.util.Date;
import java.util.List;

@Repository
public class TokenDataAccess implements DataAccess, EntityGetter<Token> {
    private JdbcTemplate template;

    @PostConstruct
    private void init(){
        template.execute("CREATE TABLE if not exists `token` (\n" +
                "  `token` varchar(255) DEFAULT NULL,\n" +
                "  `expired_at` date DEFAULT NULL,\n" +
                "  `owner` varchar(255) DEFAULT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }


    @Override
    public List<Token> get() {
        return this.template.query("SELECT * FROM token", new TokenRowMapper());
    }

    /**
     * 通过令牌值找令牌，返回令牌实体
     * @param tokenValue 令牌值
     * @return 令牌实体
     */
    public Token getByTokenValue(String tokenValue){
        try {
            Token token = this.template.queryForObject("SELECT * FROM token WHERE token=?",
                    new TokenRowMapper(), tokenValue);
            if (token != null && token.isExpired()) {
                return null;
            }
            return token;
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    /**
     * 注册新的令牌
     * @param value 令牌值
     * @param expired 到期时间
     * @param username 令牌持有者
     */
    public void registerToken(String value, Date expired, String username){
        this.template.update("INSERT INTO token (`token`, `expired_at`, `owner`) VALUES (?, ?, ?)"
        , value, expired, username);
    }

    public int removeToken(String value){
        return this.template.update("DELETE FROM token WHERE token=?", value);
    }
}
