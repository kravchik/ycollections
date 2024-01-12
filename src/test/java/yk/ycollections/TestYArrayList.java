package yk.ycollections;

import org.junit.Test;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static yk.ycollections.YArrayList.al;
import static yk.ycollections.YArrayList.times;

/**
 * Created by Yuri Kravchik on 17.11.2019
 */
public class TestYArrayList {

    @Test
    public void testGetOr() {
        assertEquals("hello", al().getOr(0, "hello"));
        assertEquals("world", al("world").getOr(0, "hello"));
        assertEquals("hello", al("world").getOr(-1, "hello"));
        assertEquals("hello", al("world").getOr(1, "hello"));
    }

    @Test
    public void testTimes() {
        assertEquals(al(), times(-1, i -> "_" + i));//maybe it is better to throw an exception
        assertEquals(al(), times(0, i -> "_" + i));
        assertEquals(al("_0", "_1", "_2"), times(3, i -> "_" + i));
    }

    @Test
    public void testRemoveFast() {
        assertEquals(al(), rf(al("a"), 0));
        assertEquals(al("b"), rf(al("a", "b"), 0));
        assertEquals(al("c", "b"), rf(al("a", "b", "c"), 0));
        assertEquals(al("a", "c"), rf(al("a", "b", "c"), 1));
        assertEquals(al("a", "b"), rf(al("a", "b", "c"), 2));
    }

    @Test
    public void testZipWith() {
        assertEquals(al(), al().zipWith(al(), (a, b) -> null));
        assertEquals(al(), al("a").zipWith(al(), (a, b) -> a + b));
        assertEquals(al("ab"), al("a").zipWith(al("b"), (a, b) -> a + b));
        assertEquals(al(), al().zipWith(al("b"), (a, b) -> a + b));
    }

    @Test
    public void testForAdj() {
        assertEquals(al(), al().forAdj(false, (a, b) -> System.out.println()));
        assertEquals(al(), al().forAdj(false, (a, b, c) -> System.out.println()));
        assertEquals(al(), al().forAdj(false, (a, b, c, d) -> System.out.println()));

        assertEquals(al(), sideEffect(se -> al("a").forAdj(false, (a, b) -> se.accept(a + b))));
        assertEquals(al(), sideEffect(se -> al("a").forAdj(false, (a, b, c) -> se.accept(a + b))));
        assertEquals(al(), sideEffect(se -> al("a").forAdj(false, (a, b, c, d) -> se.accept(a + b))));

        assertEquals(al("ab"), sideEffect(se -> al("a", "b").forAdj(false, (a, b) -> se.accept(a + b))));
        assertEquals(al("ab", "bc"), sideEffect(se -> al("a", "b", "c").forAdj(false, (a, b) -> se.accept(a + b))));
        assertEquals(al("abc", "bcd"), sideEffect(se -> al("a", "b", "c", "d").forAdj(false, (a, b, c) -> se.accept(a + b + c))));
        assertEquals(al("abcd", "bcde"), sideEffect(se -> al("a", "b", "c", "d", "e").forAdj(false, (a, b, c, d) -> se.accept(a + b + c + d))));

        assertEquals(al(), sideEffect(se -> al("a").forAdj(true, (a, b) -> se.accept(a + b))));
        assertEquals(al("ab", "ba"), sideEffect(se -> al("a", "b").forAdj(true, (a, b) -> se.accept(a + b))));
        assertEquals(al("ab", "bc", "ca"), sideEffect(se -> al("a", "b", "c").forAdj(true, (a, b) -> se.accept(a + b))));
        assertEquals(al("abc", "bcd", "cda", "dab"), sideEffect(se -> al("a", "b", "c", "d").forAdj(true, (a, b, c) -> se.accept(a + b + c))));
        assertEquals(al("abcd", "bcde", "cdea", "deab", "eabc"), sideEffect(se -> al("a", "b", "c", "d", "e").forAdj(true, (a, b, c, d) -> se.accept(a + b + c + d))));
    }

    @Test
    public void testMapAdj() {
        assertEquals(al(), YArrayList.<String>al().mapAdj(false, (a, b) -> a + b));
        assertEquals(al(), al("a").mapAdj(false, (a, b) -> a + b));
        assertEquals(al("ab"), al("a", "b").mapAdj(false, (a, b) -> a + b));
        assertEquals(al("ab", "bc"), al("a", "b", "c").mapAdj(false, (a, b) -> a + b));

        assertEquals(al(), YArrayList.<String>al().mapAdj(true, (a, b) -> a + b));
        assertEquals(al(), al("a").mapAdj(true, (a, b) -> a + b));
        assertEquals(al("ab", "ba"), al("a", "b").mapAdj(true, (a, b) -> a + b));
        assertEquals(al("ab", "bc", "ca"), al("a", "b", "c").mapAdj(true, (a, b) -> a + b));
    }

    private static YList sideEffect(Consumer<Consumer<String>> cc) {
        YList<String> result = al();
        cc.accept(s -> result.add(s));
        return result;
    }

    private static <T> YArrayList<T> rf(YArrayList<T> src, int i) {
        T old = src.get(i);
        T t = src.removeFast(i);
        assertEquals(old, t);
        return src;
    }
}
