package yk.ycollections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/13/14
 * Time: 8:53 PM
 */
public class YHashSet<T> extends LinkedHashSet<T> implements YSet<T> {

    @Override
    public YCollection<T> emptyInstance() {
        return hs();
    }

    @Override
    public YHashSet<T> copy() {
        return toYSet(this);
    }

    public static <T> YHashSet<T> toYSet(Collection<T> source) {
        YHashSet<T> result = new YHashSet<>();
        result.addAll(source);
        return result;
    }

    @SafeVarargs
    public static <T> YHashSet<T> hs(T... tt) {
        YHashSet<T> result = new YHashSet<>();
        for (int i = 0; i < tt.length; i++) result.add(tt[i]);
        return result;
    }

    public static <T> YHashSet<T> hs() {//to avoid creation of empty array if calling hs(T... tt)
        return new YHashSet<>();
    }

    @Override
    public YSet<T> cdr() {
        YSet<T> result = new YHashSet<>();
        Iterator<T> iterator = this.iterator();
        iterator.next();
        for (; iterator.hasNext(); ) result.add(iterator.next());
        return result;
    }

    @Override
    public YSet<T> without(T t) {
        return YCollections.subSet(this, t);
    }

    @Override
    public YSet<T> withoutAll(Collection<T> tt) {
        return YCollections.subSet(this, tt);
    }

    @Override
    public YSet<T> withAll(Collection<T> c) {
        return YCollections.appendSet(this, c);
    }

    @Override
    public YSet<T> with(T t) {
        return YCollections.appendSet(this, t);
    }

    @SafeVarargs
    @Override
    public final YSet<T> with(T... tt) {
        YHashSet<T> result = new YHashSet<>();
        result.addAll(this);
        for (int i = 0; i < tt.length; i++) result.add(tt[i]);
        return result;
    }
}
