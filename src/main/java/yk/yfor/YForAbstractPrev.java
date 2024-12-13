package yk.yfor;

/**
 * 31.05.2024
 */
public abstract class YForAbstractPrev<T> implements YFor<T> {
    private final YFor<T> prev;

    protected YForAbstractPrev(YFor<T> prev) {
        this.prev = prev;
    }

    public void callPrev(YForResult<T> result) {
        prev.next(result);
    }
}
