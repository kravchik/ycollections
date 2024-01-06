package yk.ycollections;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 2/18/14
 * Time: 10:49 AM
 */
public class Tuple<A, B> {
    public A a;
    public B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public static <A, B> Tuple<A, B> tuple(A a, B b) {
        return new Tuple<>(a, b);
    }

    public <R> R mapThis(Function<Tuple<A, B>, R> f) {
        return f.apply(this);
    }

    public Tuple<A, B> forThis(Consumer<Tuple<A, B>> c) {
        c.accept(this);
        return this;
    }

    @Override
    public String toString() {
        return "tuple(" + a + " " + b + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(a, tuple.a) && Objects.equals(b, tuple.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
