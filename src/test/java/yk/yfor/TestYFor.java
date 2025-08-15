package yk.yfor;

import org.junit.Test;

import static org.junit.Assert.*;
import static yk.ycollections.YArrayList.al;
import static yk.yfor.YFor.yfor;

/**
 * 10.12.2024
 */
public class TestYFor {

    @Test
    public void testCollection() {
        assertEquals(al(), yfor(al()).map(c -> c + "").toList());
        assertEquals(al("1"), yfor(al(1)).map(c -> c + "").toList());
        assertEquals(al("1", "2"), yfor(al(1, 2)).map(c -> c + "").toList());
    }

    @Test
    public void testFirst() {
        assertNull(al().yfor().first());
        assertNull(yfor(al(1, 2)).filter(c -> false).first());
        assertEquals((Integer)1, al(1).first());
        assertEquals("1", yfor(al(1)).map(c -> c + "").first());
    }

    @Test
    public void testFilter() {
        assertEquals(al(), yfor(al()).filter(c -> c != null).toList());
        assertEquals(al("a"), yfor(al("a")).filter(c -> c != null).toList());
        assertEquals(al("a"), yfor(al("a", null)).filter(c -> c != null).toList());
        assertEquals(al("a"), yfor(al(null, "a", null)).filter(c -> c != null).toList());
        assertEquals(al(), yfor(al(null, null)).filter(c -> c != null).toList());
        assertEquals(al(), yfor(al((Object) null)).filter(c -> c != null).toList());
    }

    @Test
    public void testMap() {
        assertEquals(al(), yfor(al()).map(c -> c + "").toList());
        assertEquals(al("1"), yfor(al(1)).map(c -> c + "").toList());
        assertEquals(al("1", "2"), yfor(al(1, 2)).map(c -> c + "").toList());
    }

    @Test
    public void testFlatMapEager() {
        assertEquals(al(), yfor(al()).flatMapEager(c -> al(c, "")).toList());
        assertEquals(al("1", ""), yfor(al(1)).flatMapEager(c -> al(c+"", "")).toList());
        assertEquals(al("1", "", "2", ""), yfor(al(1, 2)).flatMapEager(c -> al(c+"", "")).toList());
        assertEquals(al(2, 2), yfor(al(1, 2)).flatMapEager(c -> c == 1 ? al() : al(c, c)).toList());
        assertEquals(al(2, 2, 4, 4, 6, 6), yfor(1, 7).flatMapEager(c -> (c % 2 == 1) ? al() : al(c, c)).toList());
        assertEquals(al(2, 2, 4, 4, 6, 6), yfor(1, 7).flatMapEager(c -> (c % 2 == 1) ? null : al(c, c)).toList());
        assertEquals(al(2, 4, 6), yfor(1, 7).flatMapEager(c -> (c % 2 == 1) ? null : al(c)).toList());
    }

    @Test
    public void testFlatMap() {
        assertEquals(al(), yfor(al()).flatMap(c -> al(c+"", "").yfor()).toList());
        assertEquals(al("1", "", "2", ""), yfor(al(1, 2)).flatMap(c -> al(c+"", "").yfor()).toList());
        assertEquals(al(2, 3, 3, 4), yfor(1, 3).flatMap(c -> yfor(1, 3).map(i -> i + c)).toList());
    }

        @Test
    public void testIndex() {
        assertEquals("[]", yfor(5, 5).indexed(i -> i.map(v -> i.index + ": " + v)).toList().toString());
        assertEquals("[0: 1, 1: 0, 2: -1]", (yfor(1, -2).indexed(i -> i.map(v -> i.index + ": " + v)).toList()).toString());
        assertEquals("[0: 5, 1: 6, 2: 7]", yfor(5, 8).indexed(i -> i.map(v -> i.index + ": " + v)).toList().toString());
    }

    @Test
    public void testIntRange() {
        assertEquals(al(), yfor(5, 5).map(i -> ":" + i).toList());
        assertEquals(al(":5"), yfor(5, 6).map(i -> ":" + i).toList());
        assertEquals(al(":5", ":6"), yfor(5, 7).map(i -> ":" + i).toList());
        assertEquals(al(":5"), yfor(5, 4).map(i -> ":" + i).toList());

    }

    @Test
    public void testPeek() {
        String[] holder = new String[]{""};
        assertEquals(al(), yfor(1, 1).peek(i -> holder[0] += i).toList());
        assertEquals("", holder[0]);
        assertEquals(al(1), yfor(1, 2).peek(i -> holder[0] += i).toList());
        assertEquals("1", holder[0]);
        assertEquals(al(), yfor(1, 1).peek(i -> holder[0] += i, () -> holder[0] += "end").toList());
        assertEquals("1end", holder[0]);
        assertEquals(al(1), yfor(1, 2).peek(i -> holder[0] += i, () -> holder[0] += "end").toList());
        assertEquals("1end1end", holder[0]);
        assertEquals(al(1), yfor(1, 2).peek(null, () -> holder[0] += "end").toList());
        assertEquals("1end1endend", holder[0]);

    }

    @Test
    public void testReduce() {
        assertException(() -> yfor(1, 1).reduce((i11, i21) -> i11 + i21));
        assertEquals((Integer)1, yfor(1, 2).reduce((i1, i2) -> i1 + i2));
        assertEquals((Integer)3, yfor(1, 3).reduce((i1, i2) -> i1 + i2));

        assertEquals("", yfor(1, 1).reduce("", (i1, i2) -> i1 + i2));
        assertEquals("1", yfor(1, 2).reduce("", (i1, i2) -> i1 + i2));
        assertEquals("12", yfor(1, 3).reduce("", (i1, i2) -> i1 + i2));
    }

    private static void assertException(Runnable callable) {
        try {
            callable.run();
            fail();
        } catch (RuntimeException ignore){}
    }

    @Test
    public void testRecursiveGenerator() {
        assertEquals(al(), yfor("a", a -> a + "b").limit(0).toList());
        assertEquals(al(), yfor("a", a -> a + "b").whilst(a -> a.length() < 1).toList());
        assertEquals(al("a"), yfor("a", a -> a + "b").whilst(a -> a.length() < 2).toList());
        assertEquals(al("a", "ab"), yfor("a", a -> a + "b").whilst(a -> a.length() < 3).toList());
    }

    @Test
    public void testWhilst() {
        assertEquals(al(), yfor(5).whilst(a -> a < 0).toList());
        assertEquals(al(0, 1, 2), yfor(5).whilst(a -> a < 3).toList());
    }

    @Test
    public void testSkip() {
        assertEquals(al(), yfor(5).skip(100).toList());
        assertEquals(al(0, 1, 2, 3, 4), yfor(5).skip(0).toList());
        assertEquals(al(1, 2, 3, 4), yfor(5).skip(1).toList());
        assertEquals(al(2, 3, 4), yfor(5).skip(2).toList());
    }

    @Test
    public void testSkipWhile() {
        assertEquals(al(), yfor(5).skip(i -> i > -1).toList());
        assertEquals(al(0, 1, 2, 3, 4), yfor(5).skip(i -> i > 100).toList());
        assertEquals(al(1, 2, 3, 4), yfor(5).skip(i -> i < 1).toList());
        assertEquals(al(2, 3, 4), yfor(5).skip(i -> i < 2).toList());
    }

    @Test
    public void testCount() {
        assertEquals(0, yfor(5).whilst(a -> a < 0).count());
        assertEquals(2, yfor("a", a -> a + "b").whilst(a -> a.length() < 3).count());
    }

    @Test
    public void testWithVal() {
        assertEquals("[7, 8, 9, 10]",
            yfor(1, 5).withVal(6, (y, val) -> y.map(i -> val + i)).toList().toString());

        assertEquals("[hello1, hello2, hello3, hello4]",
            yfor(1, 5).withVal("hello", (y, val) -> y.map(i -> val + i)).toList().toString());

        assertEquals("[hello1>, hello2>, hello3>, hello4>]",
            yfor(1, 5).withVal("hello", (y, val) -> y.map(i -> val + i))
                .map(s -> s + ">").toList().toString());
    }

    @Test
    public void testYCollectionYFor() {
        assertEquals(al(), yfor(al()).toList());
        assertEquals(al(), yfor(al()).map(a -> a + "").toList());
        assertEquals(al("1"), yfor(al(1)).map(a -> a + "").toList());
        assertEquals(al("1", "2"), yfor(al(1, 2)).map(a -> a + "").toList());
    }

    @Test
    public void testLimit() {
        assertEquals(al(), yfor(1, 5).limit(0).toList());
        assertEquals(al(1), yfor(1, 5).limit(1).toList());
        assertEquals(al(1, 2), yfor(1, 5).limit(2).toList());

        assertEquals(al(), yfor(al()).limit(0).toList());
        assertEquals(al(), yfor(al()).limit(1).toList());
        assertEquals(al(), yfor(al()).limit(2).toList());
    }

}
