package space.glowberry.homeworkcollectbackend.DataAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import space.glowberry.homeworkcollectbackend.Entity.Assignment;
import space.glowberry.homeworkcollectbackend.Entity.RowMappers.AssignmentRowMapper;
import space.glowberry.homeworkcollectbackend.Entity.RowMappers.HomeworkRowMapper;

import java.util.List;
@Repository
public class AssignmentDataAccess implements EntityGetter<Assignment>{

    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Assignment> get() {
        return this.template.query("select * from assignment", new AssignmentRowMapper());
    }

    public Assignment getById(int id){
        return this.template.queryForObject("select * from assignment where id=?", new AssignmentRowMapper(), id);
    }

    public List<Assignment> getByHomeworkId(int homework_id){
        return this.template.query("select * from assignment where homework_id=?",
                new AssignmentRowMapper(), homework_id);
    }

    public List<Assignment> getByAssigneeId(int user_id){
        return this.template.query("select * from assignment where user_id=?",
                new AssignmentRowMapper(), user_id);
    }

    public List<Assignment> getByCompletionStatus(boolean is_completed){
        int c = 0;
        if (is_completed) c = 1;
        return this.template.query("select * from assignment where completion_status=?",
                new AssignmentRowMapper(), c);
    }
}