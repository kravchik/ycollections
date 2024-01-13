package yk.ycollections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static yk.ycollections.YHashSet.hs;

public class TestYHashSet {
    @Test
    public void testConstructors() {
        YHashSet<Object> orig = hs();
        YHashSet<Object> copy = orig.copy();
        orig.add("a");
        copy.add("b");
        assertEquals(hs("a"), orig);
        assertEquals(hs("b"), copy);

    }
}
