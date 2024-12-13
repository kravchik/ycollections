package yk.yfor;

/**
 * 10.12.2024
 */
public class YForIntRange implements YFor<Integer> {
    private final int from;
    private int cur;
    private final int inc;
    private final int to;

    public YForIntRange(int from, int to) {
        this(from, to, to > from ? 1 : -1);
    }

    public YForIntRange(int from, int to, int inc) {
        this.from = from;
        this.to = to;
        this.inc = inc;
        this.cur = from - inc;
    }

    @Override
    public void next(YForResult<Integer> result) {
        cur += inc;
        if (to > from && cur < to || to < from && cur > to) result.setPresent(cur);
        else result.setAbsent();
    }
}
