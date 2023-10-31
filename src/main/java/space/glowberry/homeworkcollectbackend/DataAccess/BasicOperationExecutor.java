package space.glowberry.homeworkcollectbackend.DataAccess;

import org.jetbrains.annotations.NotNull;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import space.glowberry.homeworkcollectbackend.Utils.Condition;

import java.util.List;

@Repository
public class BasicOperationExecutor implements DataAccess{

    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    /**
     * 更改某指定数据库中的符合某一条件的数据的值（更改字符串）
     * @param datasheetName 表的名称
     * @param after 替换后的值
     * @param before 替换前的值
     */
    public void updateString(String datasheetName, @NotNull Condition<String> after, @NotNull Condition<String> before){
        this.template.update("update ? set ?=? where ?=?",
                datasheetName, after.getKey(), after.getValue(),
                before.getKey(), before.getValue());
    }

    /**
     * 更改某指定数据库中的符合某一条件的数据的值（更改整数）
     * @param datasheetName 表的名称
     * @param after 替换后的值
     * @param before 替换前的值
     */
    public void updateInteger(String datasheetName, @NotNull Condition<Integer> after, @NotNull Condition<Integer> before){
        this.template.update("update ? set ?=? where ?=?",
                datasheetName, after.getKey(), after.getValue(),
                before.getKey(), before.getValue());
    }


}
