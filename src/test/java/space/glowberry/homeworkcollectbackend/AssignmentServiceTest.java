package space.glowberry.homeworkcollectbackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.glowberry.homeworkcollectbackend.Service.AssignmentService;

@SpringBootTest
public class AssignmentServiceTest {

    private AssignmentService assignmentService;

    @Autowired
    public void setAssignmentService(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @Test
    public void getUnfinishedHomework(){
        System.out.println(this.assignmentService.getUnfinishedHomeworkByUser(1));
    }

    @Test
    public void getFinishedHomework(){
        System.out.println(this.assignmentService.getFinishedHomeworkByUser(1));
    }
}
