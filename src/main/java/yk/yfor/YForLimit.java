package yk.yfor;

/**
 * 26.12.2024
 */
public class YForLimit<T> extends YForAbstractPrev<T> {
    private final int times;
    private int count;

    public YForLimit(YFor prev, int times) {
        super(prev);
        this.times = times;
        count = 0;
    }

    @Override
    public void next(YForResult<T> result) {
        if (count >= times) result.setAbsent();
        else {
            callPrev(result);
            count++;
        }
    }
}
