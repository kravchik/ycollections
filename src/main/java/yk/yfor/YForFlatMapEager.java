package yk.yfor;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

/**
 * 31.05.2024
 */
public class YForFlatMapEager<T, T2> implements YFor<T2> {
    private final YFor<T> prev;
    private final YForResult<T> prevRes = new YForResult<>();
    private final Function<T, Collection<T2>> f;
    private Iterator<T2> it;

    public YForFlatMapEager(YFor<T> prev, Function<T, Collection<T2>> f) {
        this.prev = prev;
        this.f = f;
    }

    @Override
    public void next(YForResult<T2> result) {
        while(it == null || !it.hasNext()) {
            it = null;
            prev.next(prevRes);
            if (!prevRes.isPresent()) {
                result.setAbsent();
                return;
            }
            Collection<T2> applied = f.apply(prevRes.getResult());
            if (applied == null || applied.isEmpty()) continue;
            it = applied.iterator();
        }
        result.setPresent(it.next());
    }
}
