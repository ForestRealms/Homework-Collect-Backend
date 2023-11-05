package space.glowberry.homeworkcollectbackend.DataAccess;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import space.glowberry.homeworkcollectbackend.Entity.Assignment;
import space.glowberry.homeworkcollectbackend.Entity.Exception.AssignmentAlreadyExists;
import space.glowberry.homeworkcollectbackend.Entity.RowMappers.AssignmentRowMapper;
import space.glowberry.homeworkcollectbackend.Entity.RowMappers.HomeworkRowMapper;
import space.glowberry.homeworkcollectbackend.Service.AssignmentService;

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

    @PostConstruct
    private void init(){
        this.template.execute("CREATE TABLE if not exists `assignment` (\n" +
                "  `id` int NOT NULL,\n" +
                "  `homework_id` int DEFAULT NULL,\n" +
                "  `user_id` int DEFAULT NULL,\n" +
                "  `completion_status` tinyint unsigned DEFAULT '0',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
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

    /**
     * 获取当前Assignment表中所具有的最大的ID值
     * @return 最大Assignment的ID
     */
    public int getMaximumId(){
        int res = 0;
        for (Assignment assignment : get()) {
            if (assignment.getId() > res) res = assignment.getId();
        }
        return res;
    }

    /**
     * 新增作业分配记录（根据ID区分）
     * @param assignment 分配实体
     * @throws AssignmentAlreadyExists 如果某个分配记录已经存在，则抛出此异常
     */
    public void addAssignment(Assignment assignment) throws AssignmentAlreadyExists {
        for (Assignment assignment1 : this.get()) {
            if (assignment1.getId() == assignment.getId()){
                throw new AssignmentAlreadyExists();
            }
        }
        int c = 0;
        if (assignment.isCompleted()) c = 1;
        this.template.update("insert into assignment (id, homework_id, user_id, completion_status) VALUES (?, ?, ?, ?)",
                assignment.getId(),
                assignment.getHomework_id(),
                assignment.getUser_id(),
                c);
    }
}
