package yk.yfor;

import java.util.function.Predicate;

/**
 * 13.12.2024
 */
public class YForWhilst<T> extends YForAbstractPrev<T> {
    private final Predicate<T> predicate;

    public YForWhilst(YFor<T> prev, Predicate<T> predicate) {
        super(prev);
        this.predicate = predicate;
    }

    @Override
    public void next(YForResult<T> result) {
        callPrev(result);
        if (!result.isPresent()) return;
        if (!predicate.test(result.getResult())) result.setAbsent();
    }
}
