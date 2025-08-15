package yk.yfor;

import yk.ycollections.YList;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static yk.ycollections.YArrayList.al;

/**
 * 31.05.2024
 */
public interface YFor<T> {

    static YForIntRange yfor(int to) {
        return new YForIntRange(0, to);
    }

    static YForIntRange yfor(int from, int to) {
        return new YForIntRange(from, to);
    }

    static YForIntRange yfor(int from, int to, int inc) {
        return new YForIntRange(from, to, inc);
    }

    static <T> YForCollection<T> yfor(Collection<T> c) {
        return new YForCollection<>(c);
    }

    static <T> YForRecursiveGenerator<T> yfor(T initial, Function<T, T> next) {
        return new YForRecursiveGenerator<T>(initial, next);
    }

    void next(YForResult<T> result);

    default YFor<T> filter(Predicate<T> predicate) {
        return new YForFilter<>(this, predicate);
    }

    default <T2> YFor<T2> map(Function<T, T2> f) {
        return new YForMap<>(this, f);
    }

    default <T2> YFor<T2> flatMap(Function<T, YFor<T2>> f) {
        return new YForFlatMap<>(this, f);
    }

    default <T2> YFor<T2> flatMapEager(Function<T, Collection<T2>> f) {
        return new YForFlatMapEager<>(this, f);
    }

    default YFor<T> peek(Consumer<T> consumer) {
        return new YForPeek<>(this, consumer, null);
    }

    default YFor<T> peek(Consumer<T> consumer, Runnable onEnd) {
        return new YForPeek<>(this, consumer, onEnd);
    }

    default <T2> YFor<T2> indexed(Function<YForIndex<T>, YFor<T2>> indexed) {
        YForIndex<T> ind = new YForIndex<>(this);
        return indexed.apply(ind);
    }

    default YFor<T> limit(int times) {
        return new YForLimit(this, times);
    }

    default YFor<T> whilst(Predicate<T> predicate) {
        return new YForWhilst<>(this, predicate);
    }

    default YFor<T> skip(int count) {
        return new YForSkipCount<>(this, count);
    }

    default YFor<T> skip(Predicate<T> predicate) {
        return new YForSkipByPredicate<>(this, predicate);
    }

    default <T2, T3> YFor<T3> withVal(T2 value, BiFunction<YFor<T>, T2, YFor<T3>> f) {
        return f.apply(this, value);
    }

    default int count() {
        int result = 0;
        YForResult<T> r = new YForResult<>();
        while(true) {
            next(r);
            if (!r.isPresent()) break;
            result++;
        }
        return result;
    }

    default YList<T> toList() {
        YList<T> result = al();
        YForResult<T> r = new YForResult<>();
        while(true) {
            next(r);
            if (r.isPresent()) result.add(r.getResult());
            else break;
        }
        return result;
    }

    default T first() {
        YForResult<T> r = new YForResult<>();
        next(r);
        return r.getResult();
    }

    default T reduce(BiFunction<T, T, T> reducer) {
        YForResult<T> r = new YForResult<>();
        next(r);
        if (!r.isPresent()) throw new RuntimeException("Expected at least one value");
        T cur = r.getResult();
        while(true) {
            next(r);
            if (!r.isPresent()) break;
            cur = reducer.apply(cur, r.getResult());
        }
        return cur;
    }

    default <T2> T2 reduce(T2 initial, BiFunction<T2, T, T2> reducer) {
        T2 cur = initial;
        YForResult<T> r = new YForResult<>();
        while(true) {
            next(r);
            if (!r.isPresent()) break;
            cur = reducer.apply(cur, r.getResult());
        }
        return cur;
    }

}
