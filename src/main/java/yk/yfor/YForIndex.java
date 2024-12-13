package yk.yfor;

/**
 * 10.12.2024
 */
public class YForIndex<T> extends YForAbstractPrev<T> {
    public int index = -1;

    public YForIndex(YFor<T> prev) {
        super(prev);
    }

    @Override
    public void next(YForResult<T> result) {
        callPrev(result);
        index++;
    }
}
