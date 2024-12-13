package yk.yfor;

/**
 * 10.12.2024
 */
public class YForResult<T> {
    private boolean present;
    private T result;

    public YForResult() {
    }

    public boolean isPresent() {
        return present;
    }

    public T getResult() {
        return result;
    }

    public YForResult<T> setPresent(T value) {
        present = true;
        this.result = value;
        return this;
    }

    public YForResult<T> setAbsent() {
        present = false;
        this.result = null;
        return this;
    }
}
