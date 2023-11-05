package space.glowberry.homeworkcollectbackend.Controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import space.glowberry.homeworkcollectbackend.Entity.Homework;
import space.glowberry.homeworkcollectbackend.Service.AssignmentService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AssignmentController {

    private AssignmentService assignmentService;

    @Autowired
    public void setAssignmentService(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping(value = "/getUnfinishedAssignment")
    public List<JSONObject> getUnfinishedAssignment(@RequestBody JSONObject params){
        System.out.println(params);
        List<Homework> homework =
                this.assignmentService.getUnfinishedHomeworkByUser(params.getInteger("user_id"));
        List<JSONObject> list = new ArrayList<>();
        for (Homework h : homework) {
            JSONObject res = new JSONObject();
            res.put("value", h.getId());
            res.put("label", h.getTitle());
            res.put("disabled", false);
            list.add(res);
        }
        return list;


    }
}
