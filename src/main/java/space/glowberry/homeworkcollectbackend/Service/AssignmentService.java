package space.glowberry.homeworkcollectbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.glowberry.homeworkcollectbackend.DataAccess.AssignmentDataAccess;
import space.glowberry.homeworkcollectbackend.DataAccess.HomeworkDataAccess;
import space.glowberry.homeworkcollectbackend.DataAccess.UserDataAccess;
import space.glowberry.homeworkcollectbackend.Entity.Assignment;
import space.glowberry.homeworkcollectbackend.Entity.Exception.AssignmentAlreadyExists;
import space.glowberry.homeworkcollectbackend.Entity.Exception.HomeworkNotFoundException;
import space.glowberry.homeworkcollectbackend.Entity.Exception.UserNotFoundException;
import space.glowberry.homeworkcollectbackend.Entity.Homework;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService {

    private HomeworkDataAccess homeworkDataAccess;
    private AssignmentDataAccess assignmentDataAccess;
    private UserDataAccess userDataAccess;

    @Autowired
    private void setHomeworkDataAccess(HomeworkDataAccess homeworkDataAccess) {
        this.homeworkDataAccess = homeworkDataAccess;
    }

    @Autowired
    private void setAssignmentDataAccess(AssignmentDataAccess assignmentDataAccess) {
        this.assignmentDataAccess = assignmentDataAccess;
    }

    @Autowired
    public void setUserDataAccess(UserDataAccess userDataAccess) {
        this.userDataAccess = userDataAccess;
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

    /**
     * 添加一个新的作业分配实体（记录）
     * @param user_id 被分配的用户的ID
     * @param homework_id 分配到的作业的ID
     * @throws UserNotFoundException 如果指定的用户ID对应的用户不存在，则抛出此异常
     * @throws HomeworkNotFoundException 如果指定的作业ID对应的作业不存在，则抛出磁异常
     * @throws AssignmentAlreadyExists 分配实体（记录）已存在
     */
    public void createAssignment(int user_id, int homework_id)
            throws UserNotFoundException,
            HomeworkNotFoundException{
        if (!this.homeworkDataAccess.isExists(homework_id)){
            throw new HomeworkNotFoundException();
        }
        if (!this.userDataAccess.isExists(user_id)){
            throw new UserNotFoundException();
        }
        int assignment_id = this.assignmentDataAccess.getMaximumId() + 1;
        Assignment assignment = new Assignment(assignment_id, homework_id, user_id, false);
        try {
            this.assignmentDataAccess.addAssignment(assignment);
        } catch (AssignmentAlreadyExists ignored) {

        }
    }
}
