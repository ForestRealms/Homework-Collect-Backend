package space.glowberry.homeworkcollectbackend.DataAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DataSheetOperator implements DataAccess{



    private JdbcTemplate template;
    private final String[] datasheets = {
            "users"
    };

    public DataSheetOperator() {
    }

    @Autowired
    private void setTemplate(JdbcTemplate template){
        this.template = template;
    }


    public void init(){
        this.template.execute(
                "CREATE TABLE if not exists `users` (\n" +
                        "  `username` varchar(255) DEFAULT NULL,\n" +
                        "  `password` varchar(255) DEFAULT NULL,\n" +
                        "  `id` int DEFAULT NULL\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;"
        );
    }
}
