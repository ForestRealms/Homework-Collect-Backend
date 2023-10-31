package space.glowberry.homeworkcollectbackend.DataAccess;

import java.lang.reflect.Field;
import java.util.List;

public interface EntityGetter<T> {
    List<T> get();
//    T getBy(Field field);
}
