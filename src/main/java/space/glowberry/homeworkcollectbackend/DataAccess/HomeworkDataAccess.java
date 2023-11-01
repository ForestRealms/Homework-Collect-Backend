package space.glowberry.homeworkcollectbackend.DataAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import space.glowberry.homeworkcollectbackend.Entity.Homework;
import space.glowberry.homeworkcollectbackend.Entity.RowMappers.HomeworkRowMapper;

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
        for (Homework homework : get()) {
            if (homework.getId() == id) return homework;
        }
        return null;
    }

    public void addHomework(Homework homework){
        int id = homework.getId();
        String title = homework.getTitle();
        String description = homework.getDescription();
        Date due = homework.getDue();
        this.template.update("INSERT INTO homework (`id`, `title`, `description`, `due`) VALUES (?, ?, ?, ?)",
                id, title, description, due);
    }
}
