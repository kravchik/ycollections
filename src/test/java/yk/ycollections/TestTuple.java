package yk.ycollections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static yk.ycollections.Tuple.tuple;

public class TestTuple {

    @Test
    public void testSmoke() {
        assertEquals(tuple(1, "2"), tuple(1, "2").forThis(t -> t.toString()));
        assertEquals(tuple("2", 1), tuple(1, "2").mapThis(t -> tuple(t.b, t.a)));
        assertEquals(":12", tuple(1, "2").mapThis(t -> ":" + t.a + t.b));

        assertEquals(tuple(null, null), tuple(null, null).forThis(t -> t.toString()));
        assertEquals(tuple(null, null), tuple(null, null).mapThis(t -> tuple(t.b, t.a)));
        assertEquals("tuple(null null)", tuple(null, null).mapThis(t -> t.toString()));
    }
}