package com.paulgreenlee.fn;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Simple container types. There is no facility for this in Java. If you want to
 * return two values from a function, you have to create a new type to do so.
 * The Record type in Java 15 reduces the overhead to creating a new simple
 * type, but this may still be easier.
 * 
 * @author Paul Greenlee
 */
public class Tuples {

  private Tuples() {

  }

  /**
   * This common interface for tuples is convenient for writing utilities or code
   * that needs to handle all tuples uniformly. This type of usage is rare, since
   * you lose the generic typing and access to the individual values in the tuple.
   * 
   * @author Paul Greenlee
   */
  public interface Tuple {
    /**
     * The number of elements contained in the Tuple
     * 
     * @return the number of elements in the Tuple
     */
    int cardinality();

    /**
     * A stream of the values contained in the Tuple. The purpose of tuples is to
     * store arbitrary pairs of values (or more), and the types of the values are
     * likely to be different. Therefore, this treats them all as Objects. Usage of
     * this method should be rare, as a Collection would usually be a better fit.
     * 
     * @return a Stream containing all values in the tuple.
     */
    Stream<Object> values();
  }

  /**
   * A container for two arbitrary values.
   * 
   * @author Paul Greenlee
   *
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @implSpec Implementations should be immutable
   */
  public interface Two<A, B> extends Tuple {
    A getA();

    B getB();

    default int cardinality() {
      return 2;
    }

    default Stream<Object> values() {
      return Stream.of(getA(), getB());
    }
  }

  public interface Three<A, B, C> extends Two<A, B> {
    C getC();

    default int cardinality() {
      return 3;
    }

    default Stream<Object> values() {
      return Stream.of(getA(), getB(), getC());
    }
  }

  public interface Four<A, B, C, D> extends Three<A, B, C> {
    D getD();

    default int cardinality() {
      return 4;
    }

    default Stream<Object> values() {
      return Stream.of(getA(), getB(), getC(), getD());
    }
  }

  public interface Five<A, B, C, D, E> extends Four<A, B, C, D> {
    E getE();

    default int cardinality() {
      return 5;
    }

    default Stream<Object> values() {
      return Stream.of(getA(), getB(), getC(), getD(), getE());
    }
  }

  public interface Six<A, B, C, D, E, F> extends Five<A, B, C, D, E> {
    F getF();

    default int cardinality() {
      return 6;
    }

    default Stream<Object> values() {
      return Stream
        .of(getA(), getB(), getC(), getD(), getE(), getF());
    }
  }

  /**
   * Construct a tuple with two values.
   * 
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param a   the first value
   * @param b   the second value
   * @return a tuple containing both values
   */
  public static <A, B> Two<A, B> of(A a, B b) {
    return new TwoImpl<>(a, b);
  }

  public static <A, B, C> Three<A, B, C> of(A a, B b, C c) {
    return new ThreeImpl<>(a, b, c);
  }

  public static <A, B, C, D> Four<A, B, C, D> of(A a, B b, C c, D d) {
    return new FourImpl<>(a, b, c, d);
  }

  public static <A, B, C, D, E> Five<A, B, C, D, E> of(
    A a,
    B b,
    C c,
    D d,
    E e
  ) {
    return new FiveImpl<>(a, b, c, d, e);
  }

  public static <A, B, C, D, E, F> Six<A, B, C, D, E, F> of(
    A a,
    B b,
    C c,
    D d,
    E e,
    F f
  ) {
    return new SixImpl<>(a, b, c, d, e, f);
  }

  public static String toString(Tuple tuple) {
    return tuple
      .values()
      .map(Object::toString)
      .collect(Collectors.joining(", ", "(", ")"));
  }

  public static boolean equal(Tuple a, Tuple b) {
    if (a == b)
      return true;
    if (a == null && b == null)
      return true;
    if (a == null)
      return false;
    if (b == null)
      return false;
    if (a.cardinality() != b.cardinality())
      return false;
    return StreamUtils
      .zip(a.values(), b.values())
      .allMatch(valPair -> valPair.getA().equals(valPair.getB()));
  }

  private static boolean internalEqual(Tuple a, Object o) {
    if (a == o)
      return true;
    if (a == null && o == null)
      return true;
    if (a == null)
      return false;
    if (o == null)
      return false;
    if (!(o instanceof Tuple))
      return false;
    Tuple b = (Tuple) o;
    if (a.cardinality() != b.cardinality())
      return false;
    return StreamUtils
      .zip(a.values(), b.values())
      .allMatch(valPair -> valPair.getA().equals(valPair.getB()));
  }

  /**
   * A container for two arbitrary values. It is immutable (as long as the values
   * contained in it are also immutable), and computes hashcode and equals based
   * on the hashcodes and equals methods of the underlying values.
   * 
   * @author Paul Greenlee
   *
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   */
  public static class TwoImpl<A, B> implements Two<A, B> {
    private final A a;
    private final B b;

    private TwoImpl(A a, B b) {
      super();
      this.a = a;
      this.b = b;
    }

    /**
     * Get the first value in the tuple.
     * 
     * @return the first value
     */
    public A getA() {
      return a;
    }

    /**
     * Get the second value in the tuple.
     * 
     * @return the second value
     */
    public B getB() {
      return b;
    }

    @Override
    public int hashCode() {
      return Objects.hash(a, b);
    }

    @Override
    public boolean equals(Object obj) {
      return internalEqual(this, obj);
    }

    @Override
    public String toString() {
      return Tuples.toString(this);
    }
  }

  public static class ThreeImpl<A, B, C> implements Three<A, B, C> {

    private final A a;
    private final B b;
    private final C c;

    public ThreeImpl(A a, B b, C c) {
      this.a = a;
      this.b = b;
      this.c = c;
    }

    @Override
    public A getA() {
      return a;
    }

    @Override
    public B getB() {
      return b;
    }

    @Override
    public C getC() {
      return c;
    }

    @Override
    public int hashCode() {
      return Objects.hash(a, b, c);
    }

    @Override
    public boolean equals(Object obj) {
      return internalEqual(this, obj);
    }

    @Override
    public String toString() {
      return Tuples.toString(this);
    }
  }

  public static class FourImpl<A, B, C, D>
    implements Four<A, B, C, D> {

    private final A a;
    private final B b;
    private final C c;
    private final D d;

    public FourImpl(A a, B b, C c, D d) {
      this.a = a;
      this.b = b;
      this.c = c;
      this.d = d;
    }

    @Override
    public A getA() {
      return a;
    }

    @Override
    public B getB() {
      return b;
    }

    @Override
    public C getC() {
      return c;
    }

    @Override
    public D getD() {
      return d;
    }

    @Override
    public int hashCode() {
      return Objects.hash(a, b, c, d);
    }

    @Override
    public boolean equals(Object obj) {
      return internalEqual(this, obj);
    }

    @Override
    public String toString() {
      return Tuples.toString(this);
    }

  }

  public static class FiveImpl<A, B, C, D, E>
    implements Five<A, B, C, D, E> {

    private final A a;
    private final B b;
    private final C c;
    private final D d;
    private final E e;

    public FiveImpl(A a, B b, C c, D d, E e) {
      this.a = a;
      this.b = b;
      this.c = c;
      this.d = d;
      this.e = e;
    }

    @Override
    public A getA() {
      return a;
    }

    @Override
    public B getB() {
      return b;
    }

    @Override
    public C getC() {
      return c;
    }

    @Override
    public D getD() {
      return d;
    }

    @Override
    public E getE() {
      return e;
    }

    @Override
    public int hashCode() {
      return Objects.hash(a, b, c, d, e);
    }

    @Override
    public boolean equals(Object obj) {
      return internalEqual(this, obj);
    }

    @Override
    public String toString() {
      return Tuples.toString(this);
    }

  }

  public static class SixImpl<A, B, C, D, E, F>
    implements Six<A, B, C, D, E, F> {

    private final A a;
    private final B b;
    private final C c;
    private final D d;
    private final E e;
    private final F f;

    public SixImpl(A a, B b, C c, D d, E e, F f) {
      this.a = a;
      this.b = b;
      this.c = c;
      this.d = d;
      this.e = e;
      this.f = f;
    }

    @Override
    public A getA() {
      return a;
    }

    @Override
    public B getB() {
      return b;
    }

    @Override
    public C getC() {
      return c;
    }

    @Override
    public D getD() {
      return d;
    }

    @Override
    public E getE() {
      return e;
    }

    @Override
    public F getF() {
      return f;
    }

    @Override
    public int hashCode() {
      return Objects.hash(a, b, c, d, e, f);
    }

    @Override
    public boolean equals(Object obj) {
      return internalEqual(this, obj);
    }

    @Override
    public String toString() {
      return Tuples.toString(this);
    }

  }
}
