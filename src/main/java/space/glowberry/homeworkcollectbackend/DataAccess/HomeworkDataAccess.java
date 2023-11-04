package space.glowberry.homeworkcollectbackend.DataAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import space.glowberry.homeworkcollectbackend.Entity.Exception.HomeworkAlreadyExists;
import space.glowberry.homeworkcollectbackend.Entity.Homework;
import space.glowberry.homeworkcollectbackend.Entity.RowMappers.HomeworkRowMapper;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@Repository
public class HomeworkDataAccess implements DataAccess, EntityGetter<Homework> {
    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Homework> get() {
        return this.template.query("select * from `homework`", new HomeworkRowMapper());
    }

    public Homework getById(int id){
        return this.template.queryForObject("select * from homework where id=?",
                new HomeworkRowMapper(), id);
    }

    public void addHomework(Homework homework) throws HomeworkAlreadyExists {
        int id = homework.getId();
        for (Homework homework1 : get()) {
            if (homework1.getId() == id) throw new HomeworkAlreadyExists();
        }
        String title = homework.getTitle();
        String description = homework.getDescription();
        Date due = homework.getDue();
        this.template.update("INSERT INTO homework (`id`, `title`, `description`, `due`) VALUES (?, ?, ?, ?)",
                id, title, description, due);

    }

    public void updateHomework(Homework homework){
        for (Homework h : get()) {
            if(h.getId() == homework.getId()){
                this.template.update("update homework set title = ?, description = ?, due = ? where id=?;",
                        homework.getTitle(), homework.getDescription(), homework.getDue(), homework.getId());
            }
        }
    }
}
