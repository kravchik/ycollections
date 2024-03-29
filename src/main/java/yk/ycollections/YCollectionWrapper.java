package yk.ycollections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static yk.ycollections.YArrayList.al;

public class YCollectionWrapper<T> implements YCollection<T> {
    private Collection<T> collection;

    public YCollectionWrapper(Collection<T> collection) {
        this.collection = collection;
    }

    @Override
    public YList<T> cdr() {
        YList<T> result = al();
        Iterator<T> iterator = this.iterator();
        iterator.next();
        for (; iterator.hasNext(); ) result.add(iterator.next());
        return result;
    }

    @Override
    public YList<T> withAll(Collection<T> c) {
        YList<T> result = al();
        result.addAll(this);
        result.addAll(c);
        return result;
    }

    @Override
    public YList<T> with(T t) {
        YList<T> result = al();
        result.addAll(this);
        result.add(t);
        return result;
    }

    @Override
    public YCollection<T> with(T... tt) {
        YList<T> result = al();
        result.addAll(this);
        for (int i = 0; i < tt.length; i++) result.add(tt[i]);
        return result;
    }

    @Override
    public YList<T> withoutAll(Collection<T> c) {
        YList<T> result = al();
        for (T t : this) if (!c.contains(t)) result.add(t);
        return result;
    }

    @Override
    public YList<T> without(T t) {
        YList<T> result = al();
        for (T tt : this) if (tt != t) result.add(tt);
        return result;
    }

    @SafeVarargs
    @Override
    public final YList<T> without(T... tt) {
        return withoutAll(Arrays.asList(tt));
    }

    @Override
    public YList<T> take(int count) {
        YList result = al();
        Iterator<T> it = iterator();
        for (int i = 0; i < count && it.hasNext(); i++) result.add(it.next());
        return result;
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return collection.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return collection.iterator();
    }

    @Override
    public Object[] toArray() {
        return collection.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return collection.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return add(t);
    }

    @Override
    public boolean remove(Object o) {
        return collection.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return collection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return collection.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return collection.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return collection.retainAll(c);
    }

    @Override
    public void clear() {
        collection.clear();
    }
}
