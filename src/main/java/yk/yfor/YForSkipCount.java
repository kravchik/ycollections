package yk.yfor;

public class YForSkipCount<T> extends YForAbstractPrev<T> {
    private final int count;
    private boolean skipped = false;

    public YForSkipCount(YFor<T> prev, int count) {
        super(prev);
        this.count = count;
    }

    @Override
    public void next(YForResult<T> result) {
        if (!skipped) {
            skipped = true;
            for (int i = 0; i <= count; i++) {
                callPrev(result);
                if (!result.isPresent()) return;
            }
        } else {
            callPrev(result);
        }
    }
}
