package space.glowberry.homeworkcollectbackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.glowberry.homeworkcollectbackend.DataAccess.AssignmentDataAccess;

@SpringBootTest
class AssignmentDataAccessTest {

    private AssignmentDataAccess assignmentDataAccess;

    @Autowired
    public void setAssignmentDataAccess(AssignmentDataAccess assignmentDataAccess) {
        this.assignmentDataAccess = assignmentDataAccess;
    }

    @Test
    public void getAllAssignments(){
        System.out.println(this.assignmentDataAccess.get());
    }

    @Test
    public void getAssignmentById(){
        System.out.println(this.assignmentDataAccess.getByHomeworkId(1));
    }

    @Test
    public void getAssignmentByHomeworkId(){
        System.out.println(this.assignmentDataAccess.getByHomeworkId(1));
    }

    @Test
    public void getAssignmentByUserId(){
        System.out.println(this.assignmentDataAccess.getByAssigneeId(1));
    }

    @Test
    public void getAssignmentByCompletionStatus(){
        System.out.println(this.assignmentDataAccess.getByCompletionStatus(true));
    }

}
