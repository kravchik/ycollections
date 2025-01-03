package yk.ycollections;

import org.junit.Test;

import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import static yk.ycollections.YArrayList.al;
import static yk.ycollections.YHashMap.hm;
import static yk.ycollections.YHashSet.hs;

public class TestCollections {

    public static void main1(String[] args) {
        YList<String> all = al("shift", "ctrl", "alt", "super");
        System.out.println(all.eachToEach(all)     //take pares of each to each
                .map(p -> p                        //rework each pare
                        .toSet()                   //  convert to set to remove "alt alt" and similar
                        .sorted()                  //  sort (yes, it is a LinkedHashSet inside)
                        .toString(", ")            //  make a string
                )
                .toSet()                           //convert to set to remove duplicates ("alt shift", "shift alt")
                .sorted()                          //sort
                .toString("\n"));                  //make a result string
    }

    @Test
    public void testEachToEach() {
        YList<String> all = al("shift", "ctrl", "alt", "super");
        assertEquals("[[alt, ctrl], [alt, shift], [alt, super], [alt], [ctrl, shift], [ctrl, super], [ctrl], [shift, super], [shift], [super]]"
                , all.eachToEach(all).map(p -> p.toSet().toList().sorted().toString()).toSet().toList().sorted().toString());

        assertEquals(al(al("a", "c"), al("a", "d"), al("b", "c"), al("b", "d")), al("a", "b").eachToEach(al("c", "d")));

        assertEquals(al("ac", "ad", "bc", "bd"), al("a", "b").eachToEach(al("c", "d"), (a, b) -> a + b));
        assertEquals(al(), al("a", "b").eachToEach(al(), (a, b) -> a + b));
        assertEquals(al(), al().eachToEach(al("c", "d"), (a, b) -> a + b));
        assertEquals(al("ac", "ad", "bc", "bd"), al("a", "b").eachToEach((Collection<String>)al("c", "d"), (a, b) -> a + b));
        assertEquals(al("ac", "ad", "bc", "bd"), al("a", "b").eachToEach(al("c", "d"), (a, b) -> a + b));
    }

    @Test
    public void testReduce() {
        //System.out.println(al(2, 3, 4).fold(1, (a, b) -> a * b)); ahaha - internal compilator error
        assertEquals(9, (int)al(2, 3, 4).reduce(0, (a, b) -> a + b));
        assertEquals(24, (int)al(2, 3, 4).reduce(1, (a, b) -> a * b));
        YArrayList<Integer> l = al();
        assertEquals(1, (int) l.reduce(1, (a, b) -> a * b));
    }

    @Test
    public void testIfEmpty() {
        assertEquals("isEmpty", al().ifEmpty("isEmpty", (l) -> (String)l.first()));
        assertEquals("a", al("a", "b").ifEmpty("isEmpty", (l) -> l.first()));
    }

    private static void testSort(YCollection<Integer> c) {
        assertEquals(al(1), c.with(1).sorted(v -> -v).toList());
        assertEquals(al(4, 3, 2, 1), c.with(1, 2, 3, 4).sorted(v -> -v).toList());
        assertEquals(al(), c.with().sorted(v -> -v).toList());

        assertEquals(al(1), c.with(1).sorted((o1, o2) -> -o1.compareTo(o2)).toList());
        assertEquals(al(4, 3, 2, 1), c.with(1, 2, 3, 4).sorted((o1, o2) -> -o1.compareTo(o2)).toList());
        assertEquals(al(), c.with().sorted((o1, o2) -> -o1.compareTo(o2)).toList());

        assertEquals(al(1), c.with(1).sorted().toList());
        assertEquals(al(1, 2, 3, 4), c.with(4, 3, 2, 1).sorted().toList());
        assertEquals(al(), c.with().sorted().toList());
    }

    private static void testMax(YCollection<Integer> c) {
        try {
            c.max();
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 4, c.with(1, 2, 3, 4).max());
        assertEquals((Integer) 4, c.with(1, 2, 4, 3).max());
        assertEquals((Integer) 4, c.with(4, 1, 2, 3).max());
        assertEquals((Integer) 4, c.with(4).max());

        try {
            c.max((o1, o2) -> o1.compareTo(o2));
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 1, c.with(1, 2, 3, 4).max((o1, o2) -> -o1.compareTo(o2)));
        assertEquals((Integer) 1, c.with(2, 1, 3, 4).max((o1, o2) -> -o1.compareTo(o2)));
        assertEquals((Integer) 1, c.with(2, 3, 4, 1).max((o1, o2) -> -o1.compareTo(o2)));
        assertEquals((Integer) 1, c.with(1).max((o1, o2) -> -o1.compareTo(o2)));

        try {
            c.max(i -> -i);
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 1, c.with(1, 2, 3, 4).max(i -> -i));
        assertEquals((Integer) 1, c.with(2, 1, 3, 4).max(i -> -i));
        assertEquals((Integer) 1, c.with(2, 3, 4, 1).max(i -> -i));
        assertEquals((Integer) 1, c.with(1, 3, 4, 2).max(i -> -i));
        assertEquals((Integer) 1, c.with(1).max(i -> -i));

        try {
            c.maxByFloat(i -> -i);
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 1, c.with(1, 2, 3, 4).maxByFloat(i -> -i));
        assertEquals((Integer) 1, c.with(2, 1, 3, 4).maxByFloat(i -> -i));
        assertEquals((Integer) 1, c.with(2, 3, 4, 1).maxByFloat(i -> -i));
        assertEquals((Integer) 1, c.with(1, 3, 4, 2).maxByFloat(i -> -i));
        assertEquals((Integer) 1, c.with(1).maxByFloat(i -> -i));

    }

    private static void testMin(YCollection<Integer> c) {
        try {
            c.min();
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 1, c.with(1, 2, 3, 4).min());
        assertEquals((Integer) 1, c.with(2, 1, 3, 4).min());
        assertEquals((Integer) 1, c.with(2, 3, 4, 1).min());
        assertEquals((Integer) 1, c.with(1).min());

        try {
            c.min((o1, o2) -> o1.compareTo(o2));
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 4, c.with(1, 2, 3, 4).min((o1, o2) -> -o1.compareTo(o2)));
        assertEquals((Integer) 4, c.with(1, 2, 4, 3).min((o1, o2) -> -o1.compareTo(o2)));
        assertEquals((Integer) 4, c.with(4, 1, 2, 3).min((o1, o2) -> -o1.compareTo(o2)));
        assertEquals((Integer) 4, c.with(4).min((o1, o2) -> -o1.compareTo(o2)));

        try {
            c.min(i -> i);
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 4, c.with(1, 2, 3, 4).min(i -> -i));
        assertEquals((Integer) 4, c.with(1, 2, 4, 3).min(i -> -i));
        assertEquals((Integer) 4, c.with(4, 1, 2, 3).min(i -> -i));
        assertEquals((Integer) 4, c.with(4).min(i -> -i));

        try {
            c.minByFloat(i -> i);
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 4, c.with(1, 2, 3, 4).minByFloat(i -> -i));
        assertEquals((Integer) 4, c.with(1, 2, 4, 3).minByFloat(i -> -i));
        assertEquals((Integer) 4, c.with(4, 1, 2, 3).minByFloat(i -> -i));
        assertEquals((Integer) 4, c.with(4).minByFloat(i -> -i));
    }

    private static void testCommon(YCollection<String> col) {
        //filter
        assertEquals(col, col.filter(x -> true));
        assertEquals(col, col.with("2").filter(x -> !x.equals("2")));
        assertEquals(col.with("1"), col.with("1", "2").filter(x -> x.equals("1")));

        //find
        assertEquals(null, col.find(s -> s.length() == 2));
        assertEquals("aa", col.with("a", "aa", "aaa").find(s -> s.length() == 2));
        assertEquals(null, col.with("a", "aaa").find(s -> s.length() == 2));

        //mapWithIndex
        assertEquals(col, col.mapWithIndex((i, s) -> s + i));
        assertEquals(col.with("a0"), col.with("a").mapWithIndex((i, s) -> s + i));
        assertEquals(col.with("a0", "b1"), col.with("a", "b").mapWithIndex((i, s) -> s + i));

        //first or
        assertEquals("a", col.with("a", "b").firstOr(null));
        assertEquals("a", col.with("a", "b").first());
        assertEquals("b", col.with("b").firstOr("c"));
        assertEquals("b", col.with("b").first());
        assertEquals("c", col.with().firstOr("c"));
        assertEquals(null, col.with().firstOr(null));
        assertEquals("d", col.with().firstOrCalc(() -> "d"));
        assertEquals("a", col.with("a").firstOrCalc(() -> "d"));
        try {
            col.with().first();
            fail();
        } catch (Exception ignore) {}

        //isAny
        assertTrue(col.with("a", "b").isAny(s -> s.equals("a")));
        assertFalse(col.with("a", "b").isAny(s -> s.equals("c")));
        assertFalse(col.isAny(s -> s.equals("c")));

        //isAll
        assertTrue(col.with("a", "b").isAll(s -> s.length() == 1));
        assertFalse(col.with("a", "b", "cc").isAll(s -> s.length() == 1));
        assertTrue(col.isAll(s -> s.length() == 1));

        //toMapKeys
        assertEquals(hm(), col.toMapKeys(v -> Integer.parseInt(v)));
        assertEquals(hm("1", 1), col.with("1").toMapKeys(v -> Integer.parseInt(v)));
        assertEquals(hm("1", 1, "2", 2), col.with("1", "2").toMapKeys(v -> Integer.parseInt(v)));

        //toMap
        assertEquals(hm(), col.toMap(v -> v, v -> Integer.parseInt(v)));
        assertEquals(hm("k1", 1), col.with("1").toMap(v -> "k" + v, v -> Integer.parseInt(v)));
        assertEquals(hm("k1", 1, "k2", 2), col.with("1", "2").toMap(v -> "k" + v, v -> Integer.parseInt(v)));

        //toMapMultiKeys
        assertEquals(hm(), col.toMapMultiKeys(v -> al(v), v -> Integer.parseInt(v)));
        assertEquals(hm("a", "ab", "b", "ab"), col.with("ab").toMapMultiKeys(s -> al(s.split("")), s -> s));
        assertEquals(hm(), col.with("ab").toMapMultiKeys(s -> al(), s -> s));

        //notEmpty
        assertFalse(col.notEmpty());
        assertTrue(col.with("a").notEmpty());

        //y
        assertEquals(col, col.y(s -> al(s, s)));
        assertEquals(col.with("a", "a"), col.with("a").y(s -> al(s, s)));
        assertEquals(col.with("a", "a", "b", "b"), col.with("a", "b").y(s -> al(s, s)));
        assertEquals(col.with("a"), col.with("a", "b").y(s -> s.equals("a") ? al(s) : al()));
        assertEquals(col.with("a"), col.with("a", "b").y(s -> s.equals("a") ? al(s) : null));

        //yAdj
        assertEquals(col, col.yAdj(false, (a, b) -> al()));
        assertEquals(col, col.with("a").yAdj(false, (a, b) -> al(a, b)));
        assertEquals(col.with("ab"), col.with("a", "b").yAdj(false, (a, b) -> al(a + b)));
        assertEquals(col.with("ab", "bc"), col.with("a", "b", "c").yAdj(false, (a, b) -> al(a + b)));
        assertEquals(col, col.with("a").yAdj(true, (a, b) -> al(a + b)));
        assertEquals(col.with("ab", "ba"), col.with("a", "b").yAdj(true, (a, b) -> al(a + b)));
        assertEquals(col.with("ab", "bc", "ca"), col.with("a", "b", "c").yAdj(true, (a, b) -> al(a + b)));

        //yZip
        assertEquals(col, col.yZip(al(), (a, b) -> al()));
        assertEquals(col, col.yZip(al("1"), (a, b) -> al()));
        assertEquals(col, col.with("1").yZip(al(), (a, b) -> al()));
        assertEquals(col, col.yZip(al(), (a, b) -> null));
        assertEquals(col, col.yZip(al("1"), (a, b) -> null));
        assertEquals(col, col.with("1").yZip(al(), (a, b) -> null));
        assertEquals(col.with("ab"), col.with("a").yZip(al("b"), (a, b) -> al(a + b)));
        assertEquals(col.with("ab"), col.with("a").yZip(al("b", "c"), (a, b) -> al(a + b)));
        assertEquals(col.with("ab"), col.with("a", "c").yZip(al("b"), (a, b) -> al(a + b)));

        //countByGroup
        assertEquals(hm(), col.countByGroup(s -> s));
        assertEquals(hm("s", 1), col.with("s").countByGroup(s -> s));
        assertEquals(hm(1, 2), col.with("s", "d").countByGroup(s -> s.length()));
        assertEquals(hm(1, 2, 2, 1), col.with("s", "k", "s2").countByGroup(s -> s.length()));

        assertEquals(col, col.without(""));
        //.toCharArray is to guarantee different object
        assertEquals(col, col.with("a").without(new String("a".toCharArray())));
    }

    private static void testForZip(YCollection<String> c) {
        {
            YList<String> sideEffects = al();
            YCollection<String> aa = c.with("a1", "a2");
            YCollection<String> bb = c.with("b1", "b2");
            assertSame(aa, aa.forZip(bb, (a, b) -> sideEffects.addAll(al(a, b))));
            assertEquals(al("a1", "b1", "a2", "b2"), sideEffects);
        }
        {
            YList<String> sideEffects = al();
            YCollection<String> aa = c.emptyInstance();
            YCollection<String> bb = c.emptyInstance();
            assertSame(aa, aa.forZip(bb, (a, b) -> sideEffects.addAll(al(a, b))));
            assertEquals(al(), sideEffects);
        }
        {
            YList<String> sideEffects = al();
            YCollection<String> aa = c.with("a1");
            YCollection<String> bb = c.emptyInstance();
            try {
                aa.forZip(bb, (a, b) -> sideEffects.addAll(al(a, b)));
                fail();
            } catch (RuntimeException ignore){}
        }
    }

    @Test
    public void testList() {
        testSort(al());
        testMax(al());
        testMin(al());
        testCommon(al());
        testForZip(al());

        assertEquals("b", al("a", "b").lastOr("c"));
        assertEquals("b", al("a", "b").last());
        assertEquals("b", al("b").lastOr("c"));
        assertEquals("b", al("b").last());
        assertEquals("c", al().lastOr("c"));
        assertEquals(null, al().lastOr(null));

        assertEquals("b", al("a", "b").lastOrCalc(() -> "c"));
        assertEquals("c", al().lastOrCalc(() -> "c"));
        try {
            al().last();
            fail();
        } catch (Exception ignore) {}
    }

    @Test
    public void testSet() {
        testSort(hs());
        testMax(hs());
        testMin(hs());
        testCommon(hs());
        testForZip(hs());
    }

}
