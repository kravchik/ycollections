package yk.ycollections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static yk.ycollections.YArrayList.al;
import static yk.ycollections.YHashMap.hm;
import static yk.ycollections.YHashMap.toYMap;

/**
 * Created by Yuri Kravchik on 20.12.2018
 */
public class TestYHashMap {

    @Test
    public void testConstructors() {
        assertEquals(hm(), new YHashMap());
        assertEquals(hm(), new YHashMap(hm()));
        assertEquals(hm("1", "2"), new YHashMap(hm("1", "2")));

        assertEquals(hm(), toYMap(hm()));
        assertEquals(hm("1", "2"), toYMap(hm("1", "2")));

        assertEquals(hm(), hm().copy());
        assertEquals(hm("1", "2"), hm("1", "2").copy());
    }

    @Test
    public void testFilter() {
        assertEquals(hm(), hm().filter((k, v) -> false));
        assertEquals(hm(), hm(1, 2).filter((k, v) -> false));
        assertEquals(hm(1, 2), hm(1, 2).filter((k, v) -> true));
        assertEquals(hm(1, 2), hm(1, 2, 3, 4).filter((k, v) -> k == 1));
        assertEquals(hm(1, 2), hm(1, 2, 3, 4).filter((k, v) -> v == 2));
    }

    @Test
    public void testMapToList() {
        assertEquals(al(), hm().mapToList((k, v) -> k));
        assertEquals(al(1), hm(1, 2).mapToList((k, v) -> k));
        assertEquals(al(2), hm(1, 2).mapToList((k, v) -> v));
        assertEquals(al(1, 3), hm(1, 2, 3, 4).mapToList((k, v) -> k));
    }

    @Test
    public void testMap() {
        assertEquals(hm(), hm().map((k, v) -> k+"", (k, v) -> v+""));
        assertEquals(hm(), hm().map(k -> k+"", v -> v));
        assertEquals(hm("1", "2"), hm(1, 2).map((k, v) -> k+"", (k, v) -> v+""));
        assertEquals(hm("1", "2"), hm(1, 2).map(k -> k+"", v -> v+""));
    }

    @Test
    public void testMapValues() {
        assertEquals(hm(), hm().mapValues((k, v) -> k+""));
        assertEquals(hm(), hm().mapValues(v -> v+""));

        assertEquals(hm(1, "2"), hm(1, 2).mapValues((k, v) -> v+""));
        assertEquals(hm(1, "2"), hm(1, 2).mapValues(v -> v+""));
    }

    @Test
    public void testMapKeys() {
        assertEquals(hm(), hm().mapKeys((k, v) -> k+""));
        assertEquals(hm(), hm().mapKeys(v -> v+""));

        assertEquals(hm("2", 2), hm(1, 2).mapKeys((k, v) -> v+""));
        assertEquals(hm("1", 2), hm(1, 2).mapKeys(k -> k+""));
    }

    @Test
    public void testCar() {
        assertNull(hm().car());
        assertEquals(new Tuple(1, 2), hm(1, 2).car());
        assertEquals(new Tuple(1, 2), hm(1, 2).first());
        assertEquals(new Tuple(1, 2), hm(1, 2).last());
        assertEquals(new Tuple(3, 4), hm(1, 2, 3, 4).last());
        assertNull(hm().last());
        assertEquals(hm(), hm(1, 2).cdr());
        assertEquals(hm(3, 4), hm(1, 2, 3, 4).cdr());
    }

    @Test
    public void testWith() {
        assertEquals(hm(1, 2, 3, 4), hm(1, 2).with(3, 4));
        assertEquals(hm(1, 2, 3, 4, 5, 6), hm(1, 2).with(3, 4, 5, 6));
        assertEquals(hm(1, 2, 3, 4), hm(1, 2).with(hm(3, 4)));

        assertEquals(hm(0, 1, 2, 4, 4, 5), hm(0, 1, 2, 3, 4, 5).with(2, 4));
        assertEquals(hm(0, 1, 2, 4, 4, 6), hm(0, 1, 2, 3, 4, 5).with(2, 4, 4, 6));
        assertEquals(hm(0, 1, 2, 4, 4, 5), hm(0, 1, 2, 3, 4, 5).with(hm(2, 4)));
    }

    @Test
    public void testRemoveAll() {
        YHashMap<Integer, Integer> m = hm(1, 2, 3, 4, 5, 6);
        m.removeAll(al());
        assertEquals(hm(1, 2, 3, 4, 5, 6), m);
        m.removeAll(al(3, 7));
        assertEquals(hm(1, 2, 5, 6), m);
        m.removeAll(al(1, 5));
        assertEquals(hm(), m);
    }

    @Test
    public void testGetAll() {
        assertEquals(al(), hm(1, 2, 3, 4, 5, 6).getAll(al()));
        assertEquals(al(), hm(1, 2, 3, 4, 5, 6).getAll(al(10)));
        assertEquals(al(2), hm(1, 2, 3, 4, 5, 6).getAll(al(1)));
        assertEquals(al(2, 4), hm(1, 2, 3, 4, 5, 6).getAll(al(1, 3)));
        assertEquals(al(2, 4), hm(1, 2, 3, 4, 5, 6).getAll(al(1, 3, 10)));
        assertEquals(al(null, 4), hm(1, null, 3, 4, 5, 6).getAll(al(1, 3, 10)));
    }
}