package space.glowberry.homeworkcollectbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import space.glowberry.homeworkcollectbackend.DataAccess.DataSheetOperator;

@SpringBootApplication
public class HomeworkCollectBackendApplication {


    public static MiniOManager manager;
    public static ConfigurableApplicationContext context;

    @Autowired
    public void setMiniOManager(MiniOManager manager1){
        manager = manager1;
    }

    public static void main(String[] args) throws Exception{
        context = SpringApplication.run(HomeworkCollectBackendApplication.class, args);
        context.getBean(DataSheetOperator.class).init();

        manager.connect();
        manager.createBucket();
//        manager.putFile();
//        System.out.println(manager.getURL("Dockerfile.txt"));




    }


}
