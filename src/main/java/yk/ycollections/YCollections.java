package yk.ycollections;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static yk.ycollections.YArrayList.al;
import static yk.ycollections.YArrayList.toYList;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/12/14
 * Time: 5:48 PM
 */
public class YCollections {
    public static <T, CT extends Collection<T>> CT filterList(CT result, List<T> l,
                                                              Predicate<? super T> predicate) {
        for (int i = 0, lSize = l.size(); i < lSize; i++) {
            T t = l.get(i);
            if (predicate.test(t)) result.add(t);
        }
        return result;
    }

    public static <T, CT extends Collection<T>> CT filterCollection(CT result, Collection<T> l,
                                                                    Predicate<? super T> predicate) {
        for (T t : l) if (predicate.test(t)) result.add(t);
        return result;
    }

    static <T, R, CR extends Collection<R>> CR mapList(CR result, List<T> source,
                                                                  Function<? super T, ? extends R> mapper) {
        for (int i = 0, sourceSize = source.size(); i < sourceSize; i++) {
            result.add(mapper.apply(source.get(i)));
        }
        return result;
    }

    static <T, R, CR extends Collection<R>> CR mapCollection(CR result, Collection<T> source,
                                                             Function<? super T, ? extends R> mapper) {
        for (T t : source) result.add(mapper.apply(t));
        return result;
    }

    static <T, R, CR extends Collection<R>> CR mapWithIndex(CR result, List<T> source,
                                            BiFunction<Integer, ? super T, ? extends R> mapper) {
        for (int i = 0; i < source.size(); i++) result.add(mapper.apply(i, source.get(i)));
        return result;
    }

    static <T, R, CR extends Collection<R>> CR mapWithIndex(CR result, Collection<T> source,
                                            BiFunction<Integer, ? super T, ? extends R> mapper) {
        int i = 0;
        for (T t : source) result.add(mapper.apply(i++, t));
        return result;
    }

    static <T, R, CR extends Collection<R>> CR flatMap(CR result, Collection<T> source, Function<? super T, ? extends Collection<? extends R>> mapper) {
        for (T s : source) {
            Collection<? extends R> rr = mapper.apply(s);
            if (rr != null) result.addAll(rr);
        }
        return result;
    }

    static <T, R, CR extends Collection<R>> CR yAdj(CR result, Collection<T> source, boolean cycle, BiFunction<T, T, Collection<R>> f) {
        T first = null;
        T last = null;
        boolean isFirst = true;
        boolean more1 = false;
        for (T t : source) {
            if (isFirst) {
                first = t;

            }
            if (!isFirst) {
                Collection<R> r = f.apply(last, t);
                if (r != null) result.addAll(r);
                more1 = true;
            }
            last = t;
            isFirst = false;
        }
        if (cycle && more1) {
            Collection<R> r = f.apply(last, first);
            if (r != null) result.addAll(r);
        }
        return result;
    }
    static <T, T2, R, CR extends Collection<R>> CR yZip(CR result, Collection<T> source, Collection<T2> b, BiFunction<T, T2, Collection<R>> f) {
        Iterator<T> it1 = source.iterator();
        Iterator<T2> it2 = b.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            T t1 = it1.next();
            T2 t2 = it2.next();
            Collection<R> r = f.apply(t1, t2);
            if (r != null) result.addAll(r);
        }

        return result;
    }

    static <T> YList<T> sortedCollection(Collection<T> source) {
        YList<T> result = toYList(source);
        Collections.sort((List)result);
        return result;
    }

    static <T> YList<T> sortedCollection(Collection<T> source, Comparator<? super T> comparator) {
        YList<T> result = toYList(source);
        Collections.sort(result, comparator);
        return result;
    }

    static <T> T maxFromList(List<T> source) {
        if (source.isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T result = null;
        for (int i = 0, sourceSize = source.size(); i < sourceSize; i++) {
            T t = source.get(i);
            if (result == null || ((Comparable<T>)t).compareTo(result) > 0) result = t;
        }

        return result;
    }

    static <T> T maxFromList(List<T> source, Comparator<? super T> comparator) {
        if (source.isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T result = null;
        for (int i = 0, sourceSize = source.size(); i < sourceSize; i++) {
            T t = source.get(i);
            if (result == null || comparator.compare(t, result) > 0) result = t;
        }

        return result;
    }

    static <T> T maxFromCollection(Collection<T> source, Comparator<? super T> comparator) {
        if (source.isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T result = null;
        for (T t : source) {
            if (result == null || comparator.compare(t, result) > 0) result = t;
        }
        return result;
    }

    static <T> T minFromList(List<T> source, Comparator<? super T> comparator) {
        if (source.isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T result = null;
        for (int i = 0, sourceSize = source.size(); i < sourceSize; i++) {
            T t = source.get(i);
            if (result == null || comparator.compare(t, result) < 0) result = t;
        }

        return result;
    }

    static <T> T minFromCollection(Collection<T> source, Comparator<? super T> comparator) {
        if (source.isEmpty()) throw new RuntimeException("can't get min on empty collection");
        T result = null;
        for (T t : source) {
            if (result == null || comparator.compare(t, result) < 0) result = t;
        }
        return result;
    }

    static <T> T maxFromCollection(Collection<T> source) {
        if (source.isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T result = null;
        for (T t : source) if (result == null || ((Comparable<T>)t).compareTo(result) > 0) result = t;
        return result;
    }

    static <T> T minFromList(List<T> source) {
        if (source.isEmpty()) throw new RuntimeException("can't get min on empty collection");
        T result = null;
        for (int i = 0, sourceSize = source.size(); i < sourceSize; i++) {
            T t = source.get(i);
            if (result == null || ((Comparable<T>)t).compareTo(result) < 0) result = t;
        }

        return result;
    }

    static <T> T minFromCollection(Collection<T> source) {
        if (source.isEmpty()) throw new RuntimeException("can't get min on empty collection");
        T result = null;
        for (T t : source) if (result == null || ((Comparable<T>)t).compareTo(result) < 0) result = t;
        return result;
    }

    public static <T> YArrayList<T> cdr(List<T> source) {
        YArrayList<T> result = new YArrayList();
        for (int i = 1, sourceSize = source.size(); i < sourceSize; i++) {
            result.add(source.get(i));
        }
        return result;
    }

    static <T> YArrayList<T> subListFromList(List<T> source, int fromIndex, int toIndex) {
        YArrayList<T> result = YArrayList.al();
        for (int i = fromIndex; i < toIndex; i++) result.add(source.get(i));
        return result;
    }

    public static <A, B, R> YArrayList<R> eachToEach(Collection<A> aa, Collection<B> bb, BiFunction<A, B, R> combinator) {
        YArrayList<R> result = new YArrayList<>();
        for (A a : aa) {
            for (B b : bb) {
                result.add(combinator.apply(a, b));
            }
        }
        return result;
    }

    public static <T> YSet subSet(Collection<T> c, T t) {
        YHashSet<T> result = new YHashSet<>();
        for (T t1 : c) if (!t1.equals(t)) result.add(t1);
        return result;
    }

    public static <T> YSet subSet(Collection<T> c, Collection<T> t) {
        YHashSet<T> result = new YHashSet<>();
        for (T t1 : c) if (!t.contains(t1)) result.add(t1);
        return result;
    }

    public static <T> YSet<T> appendSet(Collection<T> ts, Collection<T> tt) {
        YHashSet<T> result = new YHashSet<>();
        result.addAll(ts);
        result.addAll(tt);
        return result;
    }

    public static <T> YSet<T> appendSet(Collection<T> ts, T t) {
        YHashSet<T> result = new YHashSet<>();
        result.addAll(ts);
        result.add(t);
        return result;
    }

    public static <T> YSet<YMap<T, T>> scramble(YSet<T> aa, YSet<T> bb) {
        return scramble(aa, bb, YHashMap.hm());
    }

    public static <T> YSet<YMap<T, T>> scramble(YSet<T> aa, YSet<T> bb, YMap<T, T> prev) {
        if (aa.isEmpty() || bb.isEmpty()) return YHashSet.hs(prev);
        YSet<YMap<T, T>> result = YHashSet.hs();
        T car = aa.car();
        YSet<T> cdr = aa.cdr();
        for (T b : bb) result.addAll(scramble(cdr, bb.without(b), prev.with(car, b)));
        return result;
    }

    public static <T1, T2, T3> YList<T3> zip(YList<T1> a, YList<T2> b, BiFunction<T1, T2, T3> f) {
        YList<T3> result = al();
        for (int i = 0; i < a.size(); i++) {
            if (b.size() <= i) break;
            result.add(f.apply(a.get(i), b.get(i)));
        }
        return result;
    }
}
