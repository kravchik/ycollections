package yk.yfor;

import java.util.function.Predicate;

public class YForSkipByPredicate<T> extends YForAbstractPrev<T> {
    private final Predicate<T> predicate;
    private boolean skipped = false;

    public YForSkipByPredicate(YFor<T> prev, Predicate<T> predicate) {
        super(prev);
        this.predicate = predicate;
    }

    @Override
    public void next(YForResult<T> result) {
        if (!skipped) {
            skipped = true;
            while(true) {
                callPrev(result);
                if (!result.isPresent()) return;
                if (!predicate.test(result.getResult())) return;
            }
        } else {
            callPrev(result);
        }
    }
}
