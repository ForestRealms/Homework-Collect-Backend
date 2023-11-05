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

    @PostMapping("/addHomework")
    public JSONObject addHomework(@RequestParam("title") String title,
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
        try {
            this.homeworkService.addHomework(title, description, d);
            res.put("code", HomeworkControllerResponseCode.HOMEWORK_ADD_SUCCESSFULLY);
            res.put("message", "作业新增成功");
        } catch (Exception e){
            res.put("code", HomeworkControllerResponseCode.HOMEWORK_ADD_UNSUCCESSFULLY);
            res.put("message", "添加失败，原因未知");
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

    @PostMapping("/getHomework")
    public JSONObject getHomework(@RequestBody JSONObject params){
        Integer id = params.getInteger("id");
        Homework homework = this.homeworkService.get(id);
        JSONObject res = new JSONObject();
        if (homework == null) {
            res.put("code", 0);
            res.put("message", "作业不存在");
            return res;
        }
        res.put("code", 1);
        res.put("title", homework.getTitle());
        res.put("description", homework.getDescription());
        res.put("due", homework.getDue().toString());
        return res;
    }
}
