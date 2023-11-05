package space.glowberry.homeworkcollectbackend;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.glowberry.homeworkcollectbackend.Entity.Exception.HomeworkAlreadyExists;
import space.glowberry.homeworkcollectbackend.Entity.Exception.HomeworkNotFoundException;
import space.glowberry.homeworkcollectbackend.Entity.Exception.UserNotFoundException;
import space.glowberry.homeworkcollectbackend.Entity.Homework;
import space.glowberry.homeworkcollectbackend.Service.AssignmentService;
import space.glowberry.homeworkcollectbackend.Service.HomeworkService;
import space.glowberry.homeworkcollectbackend.Service.UserService;

import java.util.Calendar;

@SpringBootTest
public class HomeworkPublishTest {

    private HomeworkService homeworkService;
    private UserService userService;
    private AssignmentService assignmentService;

    @Autowired
    public void setHomeworkService(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAssignmentService(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @Test
    public void publish() throws UserNotFoundException, HomeworkNotFoundException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 12, 12, 11, 12);
        Homework homework = this.homeworkService.addHomework("测试作业0102", "新的作业描述",
                calendar.getTime());
        this.assignmentService.createAssignment(1, homework.getId());
    }
}
