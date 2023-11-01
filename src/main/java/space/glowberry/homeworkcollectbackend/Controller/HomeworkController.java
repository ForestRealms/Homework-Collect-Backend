package space.glowberry.homeworkcollectbackend.Controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.glowberry.homeworkcollectbackend.DataAccess.HomeworkDataAccess;
import space.glowberry.homeworkcollectbackend.Entity.Homework;
import space.glowberry.homeworkcollectbackend.Service.HomeworkService;

import java.util.Calendar;
import java.util.Date;

@RestController
public class HomeworkController {

//    @PostMapping("/upload")
//    public JSONObject uploadHomework(@RequestParam("file")MultipartFile multipartFile){
//
//    }

    private HomeworkDataAccess homeworkDataAccess;
    private HomeworkService homeworkService;

    @Autowired
    public void setHomeworkDataAccess(HomeworkDataAccess homeworkDataAccess) {
        this.homeworkDataAccess = homeworkDataAccess;
    }

    @Autowired
    public void setHomeworkService(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    @GetMapping("/homework/{id}")
    public Homework getHomework(@PathVariable int id){
        return this.homeworkDataAccess.getById(id);
    }

    @PostMapping("/addHomework")
    public int addHomework(@RequestParam("id") int id,
                           @RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("year") int year,
                           @RequestParam("month") int month,
                           @RequestParam("date") int date,
                           @RequestParam("hour") int hour,
                           @RequestParam("minute") int minute,
                           @RequestParam("second") int second){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, date, hour, minute, second);
        Date d = calendar.getTime();
        this.homeworkService.addHomework(id, title, description, d);
        return 1;
    }
}
