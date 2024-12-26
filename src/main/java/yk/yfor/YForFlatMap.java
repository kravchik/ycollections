package yk.yfor;

import java.util.function.Function;

/**
 * 31.05.2024
 */
public class YForFlatMap<T, T2> implements YFor<T2> {
    private final YFor<T> prev;
    private final YForResult<T> prevRes = new YForResult<>();
    private final Function<T, YFor<T2>> f;
    private YFor<T2> generator;

    public YForFlatMap(YFor<T> prev, Function<T, YFor<T2>> f) {
        this.prev = prev;
        this.f = f;
    }


    @Override
    public void next(YForResult<T2> result) {
        while(true) {
            if (generator != null) {
                generator.next(result);
                if (result.isPresent()) return;
                generator = null;
            }
            prev.next(prevRes);
            if (!prevRes.isPresent()) {
                result.setAbsent();
                return;
            }
            generator = f.apply(prevRes.getResult());
        }

    }
}
