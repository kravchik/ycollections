package yk.ycollections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static yk.ycollections.YHashSet.hs;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/13/14
 * Time: 8:53 PM
 */
public interface YSet<T> extends YCollection<T>, Set<T> {
    @Override
    default YSet<T> copy() {
        throw new RuntimeException("Not implemented, use toList/toSet");
    }
    @Override
    default YSet<T> filter(Predicate<? super T> predicate) {
        return YCollections.filterCollection(hs(), this, predicate);
    }
    @Override
    default <R> YSet<R> map(Function<? super T, ? extends R> mapper) {
        return YCollections.mapCollection(hs(), this, mapper);
    }
    @Override
    default <R> YSet<R> mapWithIndex(BiFunction<Integer, ? super T, ? extends R> mapper) {
        return YCollections.mapWithIndex(hs(), this, mapper);
    }
    @Override
    default <R> YSet<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper) {
        return YCollections.flatMap(hs(), this, mapper);
    }
    @Override
    default <R> YSet<R> y(Function<? super T, ? extends Collection<? extends R>> mapper) {
        return flatMap(mapper);
    }
    @Override
    default <R> YSet<R> yAdj(boolean cycle, BiFunction<T, T, Collection<R>> f) {
        return YCollections.yAdj(hs(), this, cycle, f);
    }

    @Override
    default <T2, R> YCollection<R> yZip(Collection<T2> b, BiFunction<T, T2, Collection<R>> f) {
        return YCollections.yZip(hs(), this, b, f);
    }

    @Override
    YSet<T> cdr();

    @Override
    default YSet<T> toSet() {
        return this;
    }

    @Override
    YSet<T> withAll(Collection<T> c);
    @Override
    YSet<T> with(T t);
    @Override
    @SuppressWarnings("unchecked")
    YSet<T> with(T... tt);
    @Override
    YSet<T> withoutAll(Collection<T> tt);
    @Override
    YSet<T> without(T t);
    @Override
    @SuppressWarnings("unchecked")
    default YSet<T> without(T... tt) {
        return withoutAll(hs(tt));
    }

    default YSet<T> intersection(YSet<T> other) {
        YSet<T> result = hs();
        for (T t : this) if (other.contains(t))result.add(t);
        return result;
    }

    @Override
    default YSet<T> take(int count) {
        YSet result = hs();
        Iterator<T> it = iterator();
        for (int i = 0; i < count && it.hasNext(); i++) result.add(it.next());
        return result;
    }

    @Override
    default YSet<T> assertSize(int s) {
        if (size() != s) {
            throw new RuntimeException("Expected size " + s + " but was " + size());
        }
        return this;
    }
}
