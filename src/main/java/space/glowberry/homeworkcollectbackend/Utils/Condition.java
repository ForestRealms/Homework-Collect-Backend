package space.glowberry.homeworkcollectbackend.Utils;

public class Condition<T> {
    private final String key;
    private final T value;

    public Condition(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }
}
