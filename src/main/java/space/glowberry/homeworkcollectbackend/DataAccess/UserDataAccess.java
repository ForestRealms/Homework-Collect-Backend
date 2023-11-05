package space.glowberry.homeworkcollectbackend.DataAccess;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import space.glowberry.homeworkcollectbackend.Entity.User;
import space.glowberry.homeworkcollectbackend.Entity.RowMappers.UserRowMapper;

import java.util.List;

@Repository
public class UserDataAccess implements DataAccess{
    private JdbcTemplate template;

    @PostConstruct
    private void init(){
        template.execute("CREATE TABLE if not exists `users` ( " +
                "`id` INT DEFAULT NULL, " +
                "`username` VARCHAR ( 255 ) DEFAULT NULL, " +
                "`password` VARCHAR ( 255 ) DEFAULT NULL, " +
                "`privileged` INT DEFAULT NULL ) " +
                "ENGINE = INNODB DEFAULT CHARSET = " +
                "utf8mb4 COLLATE = utf8mb4_0900_ai_ci;");
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public User getUser(String username){
        try {
            return this.template.queryForObject("SELECT * FROM users WHERE username=?",
                    new UserRowMapper(), username);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public List<User> getUsers(){
        return this.template.query("SELECT * FROM users", new UserRowMapper());

    }

    public void addUser(User user){
        this.template.update("insert into users (username, password, privileged, id) VALUES " +
                "(?, ?, ?, ?)", user.getUsername(), user.getPassword(), user.isPrivileged(), user.getId());
    }
}
