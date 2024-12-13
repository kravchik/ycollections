package yk.yfor;

import java.util.function.Predicate;

/**
 * 31.05.2024
 */
public class YForFilter<T> extends YForAbstractPrev<T> {
    Predicate<T> predicate;

    protected YForFilter(YFor<T> prev, Predicate<T> predicate) {
        super(prev);
        this.predicate = predicate;
    }

    @Override
    public void next(YForResult<T> result) {
        while(true) {
            callPrev(result);
            if (!result.isPresent()) break;
            if (predicate.test(result.getResult())) break;
        }
    }
}
