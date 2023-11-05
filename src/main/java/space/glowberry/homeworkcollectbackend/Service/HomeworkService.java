package space.glowberry.homeworkcollectbackend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.glowberry.homeworkcollectbackend.DataAccess.HomeworkDataAccess;
import space.glowberry.homeworkcollectbackend.Entity.Exception.HomeworkAlreadyExists;
import space.glowberry.homeworkcollectbackend.Entity.Homework;

import java.util.Date;

@Service
public class HomeworkService {

    private HomeworkDataAccess homeworkDataAccess;

    @Autowired
    public void setHomeworkDataAccess(HomeworkDataAccess homeworkDataAccess) {
        this.homeworkDataAccess = homeworkDataAccess;
    }

    public Homework get(int id){
        return this.homeworkDataAccess.getById(id);
    }

    public Homework addHomework(String title,
                            String description,
                            Date due){
        int id = this.homeworkDataAccess.getMaximumId() + 1;
        Homework homework = new Homework(id, title, description, due);
        try {
            this.homeworkDataAccess.addHomework(homework);
        } catch (HomeworkAlreadyExists ignored) {

        }
        return homework;

    }

    public void updateHomework(int id,
                               String title,
                               String description,
                               Date due){
        this.homeworkDataAccess.updateHomework(new Homework(id, title, description, due));
    }
}
