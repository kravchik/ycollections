package yk.jcommon.collections;

import yk.jcommon.utils.BadException;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import static yk.jcommon.collections.YArrayList.*;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 10/1/14
 * Time: 11:50 PM
 */
public class YHashMap<K, V> extends LinkedHashMap<K, V> implements YMap<K, V> {


    //public static YHashMap map(Object... oo) {
    //    YHashMap result = new YHashMap();
    //    for (int i = 0; i < oo.length; i+=2) result.put(oo[i], oo[i+1]);
    //    return result;
    //}

    public static <K, V> YHashMap<K, V> hm(K k, V v, Object... oo) {//TODO assert repeating keys
        YHashMap result = new YHashMap();
        result.put(k, v);
        for (int i = 0; i < oo.length; i+=2) result.put(oo[i], oo[i+1]);
        return result;
    }

    public static <K, V> YHashMap<K, V> toYMap(Map<K, V> source) {
        YHashMap result = new YHashMap();
        result.putAll(source);
        return result;
    }

    public static <K, V> YHashMap<K, V> hm() {
        return new YHashMap<>();
    }

    @Override
    public Map<K, V> filter(BiPredicate<? super K, ? super V> predicate) {
        Map<K, V> result = hm();
        for (Map.Entry<K, V> entry : this.entrySet()) if (predicate.test(entry.getKey(), entry.getValue())) result.put(entry.getKey(), entry.getValue());
        return result;
    }

    @Override
    public <V2> YList<V2> mapToList(BiFunction<? super K, ? super V, V2> mapper) {
        YList<V2> result = al();
        for (Map.Entry<K, V> entry : this.entrySet()) {
            result.add(mapper.apply(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    @Override
    public <V2> YMap<K, V2> map(BiFunction<? super K, ? super V, V2> mapper) {
        YMap<K, V2> result = hm();
        for (Map.Entry<K, V> entry : this.entrySet()) {
            result.put(entry.getKey(), mapper.apply(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    //@Override
    //public <K2, V2> YMap<K2, V2> map(BiFunction<? super K, ? super V, Tuple<? extends K2, ? extends V2>> mapper) {
    //    YMap<K2, V2> result = hm();
    //    for (Entry<K, V> e : entrySet()) {
    //        Tuple<? extends K2, ? extends V2> t = mapper.apply(e.getKey(), e.getValue());
    //        //TODO assert repeating keys?
    //        result.put(t.a, t.b);
    //    }
    //    return result;
    //}
    //
    //@Override
    //public <K2, V2> YMap<K2, V2> flatMap(BiFunction<? super K, ? super V, ? extends List<Tuple<? extends K2, ? extends V2>>> mapper) {
    //    return null;
    //}

    @Override
    public Tuple<K, V> car() {
        Map.Entry<K, V> next = entrySet().iterator().next();
        return new Tuple<>(next.getKey(), next.getValue());
    }

    @Override
    public Map<K, V> cdr() {
        return without(car().a);
    }

    @Override
    public Tuple<K, V> first() {
        return car();
    }

    @Override
    public Tuple<K, V> last() {
        throw BadException.notImplemented();
    }

    @Override
    public Tuple<K, V> max() {
        throw BadException.notImplemented();
    }

    @Override
    public Tuple<K, V> min() {
        throw BadException.notImplemented();
    }

    @Override
    public V getOr(K key, V cur) {
        V result = get(key);
        return result == null ? cur : result;
    }

    @Override
    public YSet<K> keySet() {
        return new YHashSetWrapper<>(super.keySet());
    }

    @Override
    public YList<V> values() {
        //TODO wrapper
        //return new YListWrapper(super.values());
        return toYList(super.values());
    }

    @Override
    public YMap<K, V> with(K k, V v) {
        YMap<K, V> result = hm();
        result.putAll(this);
        result.put(k, v);
        return result;
    }

    @Override
    public YMap<K, V> with(K k, V v, Object... other) {
        YMap<K, V> result = with(k, v);
        for (int i = 0; i < other.length; i += 2) result.put((K)other[i], (V)other[i+1]);
        return result;
    }

    @Override
    public YMap<K, V> with(Map<K, V> kv) {
        YMap<K, V> result = hm();
        result.putAll(this);
        result.putAll(kv);
        return result;
    }

    @Override
    public YMap<K, V> without(K pKey) {
        YMap<K, V> result = new YHashMap<>();
        result.putAll(this);
        result.remove(pKey);
        return result;
    }

    @Override
    public YMap<K, V> without(Collection<K> keys) {
        YMap<K, V> result = toYMap(this);
        for (K key : keys) result.remove(key);
        return result;
    }
}
