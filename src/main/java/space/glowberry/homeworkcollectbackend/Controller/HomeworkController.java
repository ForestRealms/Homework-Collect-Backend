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
    public JSONObject addHomework(@RequestParam("id") int id,
                           @RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("year") int year,
                           @RequestParam("month") int month,
                           @RequestParam("date") int date,
                           @RequestParam("hour") int hour,
                           @RequestParam("minute") int minute,
                           @RequestParam("second") int second){
        JSONObject res = new JSONObject();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, date, hour, minute, second);
        Date d = calendar.getTime();
        if (this.homeworkService.addHomework(id, title, description, d) == 1) {
            res.put("code", HomeworkControllerResponseCode.HOMEWORK_ADD_SUCCESSFULLY);
            res.put("message", "添加成功");
            return res;
        }else {
            res.put("code", HomeworkControllerResponseCode.HOMEWORK_ALREADY_EXISTS);
            res.put("message", "作业已经存在，无需重复添加");
        }
        return res;
    }

    @PostMapping("/updateHomework")
    public JSONObject updateHomework(@RequestParam("id") int id,
                              @RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("year") int year,
                              @RequestParam("month") int month,
                              @RequestParam("date") int date,
                              @RequestParam("hour") int hour,
                              @RequestParam("minute") int minute,
                              @RequestParam("second") int second){
        JSONObject res = new JSONObject();
        for (Homework homework : this.homeworkDataAccess.get()) {
            if (homework.getId() == id){
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month-1, date, hour, minute, second);
                Date d = calendar.getTime();
                homework.setDue(d);
                this.homeworkService.updateHomework(id, title, description, d);
                res.put("code", HomeworkControllerResponseCode.HOMEWORK_UPDATE_SUCCESSFULLY);
                res.put("message", "更改成功");
                return res;
            }
        }
        res.put("code", HomeworkControllerResponseCode.HOMEWORK_NOT_EXISTS);
        res.put("message", "作业不存在");
        return res;
    }
}
