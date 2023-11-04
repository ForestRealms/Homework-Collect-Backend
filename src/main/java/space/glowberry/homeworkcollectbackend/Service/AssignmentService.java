package space.glowberry.homeworkcollectbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.glowberry.homeworkcollectbackend.DataAccess.AssignmentDataAccess;
import space.glowberry.homeworkcollectbackend.DataAccess.HomeworkDataAccess;
import space.glowberry.homeworkcollectbackend.Entity.Assignment;
import space.glowberry.homeworkcollectbackend.Entity.Homework;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService {

    private HomeworkDataAccess homeworkDataAccess;
    private AssignmentDataAccess assignmentDataAccess;

    @Autowired
    private void setHomeworkDataAccess(HomeworkDataAccess homeworkDataAccess) {
        this.homeworkDataAccess = homeworkDataAccess;
    }

    @Autowired
    private void setAssignmentDataAccess(AssignmentDataAccess assignmentDataAccess) {
        this.assignmentDataAccess = assignmentDataAccess;
    }

    /**
     * 取出某一用户还未完成的作业
     * @param user_id 用户的ID
     * @return 该用户未完成的作业的列表
     */
    public List<Homework> getUnfinishedHomeworkByUser(int user_id){
        List<Homework> res = new ArrayList<>();
        for (Assignment assignment : this.assignmentDataAccess.getByAssigneeId(user_id)) {
            if (!assignment.isCompleted()){
                int homework_id = assignment.getHomework_id();
                res.add(this.homeworkDataAccess.getById(homework_id));
            }
        }
        return res;
    }

    /**
     * 取出某一用户已经完成的作业
     * @param user_id 用户的ID
     * @return 该用户已经完成的作业的列表
     */
    public List<Homework> getFinishedHomeworkByUser(int user_id){
        List<Homework> res = new ArrayList<>();
        for (Assignment assignment : this.assignmentDataAccess.getByAssigneeId(user_id)) {
            if (assignment.isCompleted()){
                int homework_id = assignment.getHomework_id();
                res.add(this.homeworkDataAccess.getById(homework_id));
            }
        }
        return res;
    }
}
