package space.glowberry.homeworkcollectbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import space.glowberry.homeworkcollectbackend.DataAccess.HomeworkDataAccess;
import space.glowberry.homeworkcollectbackend.Entity.Homework;

import java.util.Date;

@Service
public class HomeworkService {

    private HomeworkDataAccess homeworkDataAccess;

    @Autowired
    public void setHomeworkDataAccess(HomeworkDataAccess homeworkDataAccess) {
        this.homeworkDataAccess = homeworkDataAccess;
    }

    public void addHomework(int id,
                            String title,
                            String description,
                            Date due){
        this.homeworkDataAccess.addHomework(new Homework(id, title, description, due));
    }
}
