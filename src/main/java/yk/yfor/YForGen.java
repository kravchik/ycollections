package yk.yfor;

import java.util.function.Function;

/**
 * 13.12.2024
 */
//TODO rename
public class YForGen<T> implements YFor<T> {
    private boolean started = false;
    private T cur;
    private final Function<T, T> inc;

    public YForGen(T initial, Function<T, T> inc) {
        this.cur = initial;
        this.inc = inc;
    }

    @Override
    public void next(YForResult result) {
        if (started) cur = inc.apply(cur);
        else started = true;
        result.setPresent(cur);
    }

    public static <T> YForGen<T> yfor(T initial, Function<T, T> inc) {
        return new YForGen<T>(initial, inc);
    }
}
