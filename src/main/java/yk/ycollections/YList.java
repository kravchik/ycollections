package yk.ycollections;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

import static yk.ycollections.YArrayList.al;
import static yk.ycollections.YHashMap.hm;


/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/12/14
 * Time: 3:27 PM
 */
@SuppressWarnings("ForLoopReplaceableByForEach")
public interface YList<T> extends YCollection<T>, List<T> {
    //TODO nTimes

    @Override
    YList<T> copy();

    @Override//TODO remove (currently needed for cs translator)
    T get(int index);

    default T getOr(int index, T or) {
        return index >= size() || index < 0 ? or : get(index);
    }

    @Override
    default YList<T> filter(Predicate<? super T> predicate) {
        return YCollections.filterList(al(), this, predicate);
    }

    @Override
    default <R> YList<R> map(Function<? super T, ? extends R> mapper) {
        return YCollections.mapList(al(), this, mapper);
    }

    @Override
    default <R> YList<R> mapWithIndex(BiFunction<Integer, ? super T, ? extends R> mapper) {
        return YCollections.mapWithIndex(al(), this, mapper);
    }

    @Override
    default <R> YList<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper) {
        return YCollections.flatMap(al(), this, mapper);
    }

    /**
     * The same as 'forEach', but returns 'this' so can continue using the instance.
     */
    @Override
    default YList<T> peek(Consumer<T> consumer) {
        for (T t : this) consumer.accept(t);
        return this;
    }

    //TODO separate utility iterator
    default <T2> YList<T> forZip(YList<T2> b, BiConsumer<T, T2> f) {
        if (size() != b.size()) throw new RuntimeException("Expected the same size");
        for (int i = 0; i < this.size(); i++) f.accept(this.get(i), b.get(i));
        return this;
    }

    @Override
    default YList<T> forWithIndex(BiConsumer<Integer, ? super T> consumer) {
        for (int i = 0; i < size(); i++) consumer.accept(i, get(i));
        return this;
    }

    @Override
    default T cadr() {
        return get(1);
    }

    @Override
    default YList<T> toList() {
        return this;
    }


    @Override
    YList<T> cdr();
    @Override
    YList<T> withAll(Collection<T> c);
    @Override
    YList<T> with(T t);
    YList<T> withAt(int index, T t);
    @Override
    @SuppressWarnings("unchecked")
    YList<T> with(T... t);
    @Override
    YList<T> withoutAll(Collection<T> c);
    @Override
    YList<T> without(T t);
    @Override
    @SuppressWarnings("unchecked")
    YList<T> without(T... t);
    @Override
    YList<T> take(int count);

    @Override
    YList<T> subList(int fromIndex, int toIndex);
    T last();

    default T lastOr(T t) {
        if (isEmpty()) return t;
        return last();
    }

    default T lastOrCalc(Supplier<T> supplier) {
        if (isEmpty()) return supplier.get();
        return last();
    }

    YList<T> allMin(Comparator<? super T> comparator);

    YList<YList<T>> eachToEach(YList<T> other);

    <O, R> YList<R> eachToEach(Collection<O> other, BiFunction<T, O, R> combinator);

    <O, R> YList<R> eachToEach(List<O> other, BiFunction<T, O, R> combinator);

    default void forUniquePares(BiConsumer<T, T> bc) {
        for (int i = 0; i < size()-1; i++) for (int j = i+1; j < size(); j++) bc.accept(get(i), get(j));
    }

    <R> YList<R> mapUniquePares(BiFunction<T, T, R> bf);

    default YList<? extends YList<T>> split(Object eq) {
        return split(e -> e == null ? eq == null : e.equals(eq));
    }

    YList<? extends YList<T>> split(Predicate<T> isSplitter);

    YList<T> reversed();

    default <R> YList<R> mapAdj(boolean cycle, BiFunction<T, T, R> f) {
        YList<R> result = al();
        if (size() < 2) return result;
        int till = this.size() - (cycle ? 0 : 1);
        for (int i = 0; i < till; i++) {
            T t1 = this.get((i + 0) % this.size());
            T t2 = this.get((i + 1) % this.size());
            result.add(f.apply(t1, t2));
        }
        return result;
    }

    default YList<T> forAdj(boolean cycle, BiConsumer<T, T> consumer) {
        if (size() < 2) return this;
        int till = this.size() - (cycle ? 0 : 1);
        for (int i = 0; i < till; i++) {
            T t1 = this.get((i + 0) % this.size());
            T t2 = this.get((i + 1) % this.size());
            consumer.accept(t1, t2);
        }
        return this;
    }

    default YList<T> forAdj(boolean cycle, Consumer3<T, T, T> consumer) {
        if (size() < 3) return this;
        int till = this.size() - (cycle ? 0 : 2);
        for (int i = 0; i < till; i++) {
            T t1 = this.get((i + 0) % this.size());
            T t2 = this.get((i + 1) % this.size());
            T t3 = this.get((i + 2) % this.size());
            consumer.accept(t1, t2, t3);
        }
        return this;
    }

    default YList<T> forAdj(boolean cycle, Consumer4<T, T, T, T> consumer) {
        if (size() < 4) return this;
        int till = this.size() - (cycle ? 0 : 3);
        for (int i = 0; i < till; i++) {
            T t1 = this.get((i + 0) % this.size());
            T t2 = this.get((i + 1) % this.size());
            T t3 = this.get((i + 2) % this.size());
            T t4 = this.get((i + 3) % this.size());
            consumer.accept(t1, t2, t3, t4);
        }
        return this;
    }

    @Override
    default T findLast(Predicate<? super T> predicate) {
        T result = null;
        for (int i = 0, thisSize = this.size(); i < thisSize; i++) {
            T t = this.get(i);
            if (predicate.test(t)) result = t;
        }
        return result;
    }

    @Override
    default <CMP extends Comparable<CMP>> T max(Function<T, CMP> evaluator) {
        if (isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T max = null;
        CMP maxComparable = null;
        for (int i = 0; i < this.size(); i++) {
            T t = this.get(i);
            CMP nextComparable = evaluator.apply(t);
            if (nextComparable == null) throw new RuntimeException("evaluator shouldn't return null values");
            if (maxComparable == null || maxComparable.compareTo(nextComparable) < 0) {
                max = t;
                maxComparable = nextComparable;
            }
        }
        return max;
    }

    @Override
    default T maxByFloat(Function_float_T<T> evaluator) {
        if (isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T max = null;
        float maxComparable = 0;
        boolean found = false;
        for (int i = 0; i < this.size(); i++) {
            T t = this.get(i);
            float nextComparable = evaluator.apply(t);
            if (!found || nextComparable > maxComparable) {
                max = t;
                maxComparable = nextComparable;
            }
            found = true;
        }
        return max;
    }

    @Override
    default <CMP extends Comparable<CMP>> T min(Function<T, CMP> evaluator) {
        if (isEmpty()) throw new RuntimeException("can't get min on empty collection");
        T min = null;
        CMP minComparable = null;
        for (int i = 0; i < this.size(); i++) {
            T t = this.get(i);
            CMP nextComparable = evaluator.apply(t);
            if (nextComparable == null) throw new RuntimeException("evaluator shouldn't return null values");
            if (minComparable == null || minComparable.compareTo(nextComparable) >= 0) {
                min = t;
                minComparable = nextComparable;
            }
        }
        return min;
    }

    @Override
    default T minByFloat(Function_float_T<T> evaluator) {
        if (isEmpty()) throw new RuntimeException("can't get min on empty collection");
        T min = null;
        float minComparable = 0;
        boolean found = false;
        for (int i = 0; i < this.size(); i++) {
            T t = this.get(i);
            float nextComparable = evaluator.apply(t);
            if (!found || nextComparable < minComparable) {
                min = t;
                minComparable = nextComparable;
            }
            found = true;
        }
        return min;
    }

    default YMap<T, Integer> countByGroup() {
        YMap<T, Integer> result = hm();
        for (T t : this) result.put(t, result.getOr(t, 0) + 1);
        return result;
    }

    default <T2, T3> YList<T3> zipWith(YList<T2> b, BiFunction<T, T2, T3> f) {
        return YCollections.zip(this, b, f);
    }

    @Override
    default YList<T> assertSize(int s) {
        if (size() != s) throw new RuntimeException("Expected size " + s + " but was " + size());
        return this;
    }

    //TODO test
    default int indexOf(Predicate<T> p) {
        for (int i = 0; i < size(); i++) {
            if (p.test(get(i))) return i;
        }
        return -1;
    }

    //TODO test
    default int indexOfMax() {
        int resultIndex = -1;
        T result = null;
        for (int i = 0; i < this.size(); i++) {
            T t = this.get(i);
            if (result == null || ((Comparable<T>) t).compareTo(result) > 0) {
                resultIndex = i;
                result = t;
            }
        }
        return resultIndex;
    }

    //TODO test
    default int indexOfMin() {
        int resultIndex = -1;
        T result = null;
        for (int i = 0; i < this.size(); i++) {
            T t = this.get(i);
            if (result == null || ((Comparable<T>) t).compareTo(result) < 0) {
                resultIndex = i;
                result = t;
            }
        }
        return resultIndex;
    }

    default YList<T> forThis(Consumer<YList<T>> c) {
        c.accept(this);
        return this;
    }

    default <T2> T2 mapThis(Function<YList<T>, T2> f) {
        return f.apply(this);
    }

    @Override
    default <R> YList<R> y(Function<? super T, ? extends Collection<? extends R>> mapper) {
        return flatMap(mapper);
    }
    @Override
    default <R> YList<R> yAdj(boolean cycle, BiFunction<T, T, Collection<R>> f) {
        YList<R> result = al();
        if (size() < 2) return result;
        int till = this.size() - (cycle ? 0 : 1);
        for (int i = 0; i < till; i++) {
            T t1 = this.get((i + 0) % this.size());
            T t2 = this.get((i + 1) % this.size());
            Collection<R> r = f.apply(t1, t2);
            if (r != null) result.addAll(r);
        }
        return result;
    }
    @Override
    default <T2, R> YList<R> yZip(Collection<T2> b, BiFunction<T, T2, Collection<R>> f) {
        return YCollections.yZip(al(), this, b, f);
    }
}
