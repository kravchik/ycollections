package yk.yfor;

import java.util.function.Function;

/**
 * 31.05.2024
 */
public class YForMap<T, T2> implements YFor<T2> {
    YFor<T> prev;
    YForResult<T> hp = new YForResult<>();
    Function<T, T2> f;

    public YForMap(YFor<T> prev, Function<T, T2> f) {
        this.prev = prev;
        this.f = f;
    }

    @Override
    public void next(YForResult<T2> result) {
        prev.next(hp);
        if (!hp.isPresent()) {
            result.setAbsent();
            return;
        }
        result.setPresent(f.apply(hp.getResult()));
    }
}
