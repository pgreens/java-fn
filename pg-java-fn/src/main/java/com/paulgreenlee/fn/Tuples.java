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
    /**
     * Get the first value
     * 
     * @return the first value
     */
    A getA();

    /**
     * Get the second value
     * 
     * @return the second value
     */
    B getB();

    default int cardinality() {
      return 2;
    }

    default Stream<Object> values() {
      return Stream.of(getA(), getB());
    }
  }

  /**
   * A container for three arbitrary values.
   * 
   * @author Paul Greenlee
   *
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param <C> the type of the third value
   * @implSpec Implementations should be immutable
   */
  public interface Three<A, B, C> extends Two<A, B> {
    /**
     * Get the third value
     * 
     * @return the third value
     */
    C getC();

    default int cardinality() {
      return 3;
    }

    default Stream<Object> values() {
      return Stream.of(getA(), getB(), getC());
    }
  }

  /**
   * A container for four arbitrary values.
   * 
   * @author Paul Greenlee
   *
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param <C> the type of the third value
   * @param <D> the type of the fourth value
   * @implSpec Implementations should be immutable
   */
  public interface Four<A, B, C, D> extends Three<A, B, C> {
    /**
     * Get the fourth value
     * 
     * @return the fourth value
     */
    D getD();

    default int cardinality() {
      return 4;
    }

    default Stream<Object> values() {
      return Stream.of(getA(), getB(), getC(), getD());
    }
  }

  /**
   * A container for five arbitrary values.
   * 
   * @author Paul Greenlee
   *
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param <C> the type of the third value
   * @param <D> the type of the fourth value
   * @param <E> the type of the fifth value
   * @implSpec Implementations should be immutable
   */
  public interface Five<A, B, C, D, E> extends Four<A, B, C, D> {
    /**
     * Get the fifth value
     * 
     * @return the fifth value
     */
    E getE();

    default int cardinality() {
      return 5;
    }

    default Stream<Object> values() {
      return Stream.of(getA(), getB(), getC(), getD(), getE());
    }
  }

  /**
   * A container for six arbitrary values.
   * 
   * @author Paul Greenlee
   *
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param <C> the type of the third value
   * @param <D> the type of the fourth value
   * @param <E> the type of the fifth value
   * @param <F> the type of the sixth value
   * @implSpec Implementations should be immutable
   */
  public interface Six<A, B, C, D, E, F> extends Five<A, B, C, D, E> {
    /**
     * Get the sixth value
     * 
     * @return the sixth value
     */
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

  /**
   * Construct a tuple with three values.
   * 
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param <C> the type of the third value
   * @param a   the first value
   * @param b   the second value
   * @param c   the third value
   * @return a tuple containing the values
   */
  public static <A, B, C> Three<A, B, C> of(A a, B b, C c) {
    return new ThreeImpl<>(a, b, c);
  }

  /**
   * Construct a tuple with four values.
   * 
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param <C> the type of the third value
   * @param <D> the type of the fourth value
   * @param a   the first value
   * @param b   the second value
   * @param c   the third value
   * @param d   the fourth value
   * @return a tuple containing the values
   */
  public static <A, B, C, D> Four<A, B, C, D> of(A a, B b, C c, D d) {
    return new FourImpl<>(a, b, c, d);
  }

  /**
   * Construct a tuple with five values.
   * 
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param <C> the type of the third value
   * @param <D> the type of the fourth value
   * @param <E> the type of the fifth value
   * @param a   the first value
   * @param b   the second value
   * @param c   the third value
   * @param d   the fourth value
   * @param e   the fifth value
   * @return a tuple containing the values
   */
  public static <A, B, C, D, E> Five<A, B, C, D, E> of(
    A a,
    B b,
    C c,
    D d,
    E e
  ) {
    return new FiveImpl<>(a, b, c, d, e);
  }

  /**
   * Construct a tuple with six values. Wowee!
   * 
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param <C> the type of the third value
   * @param <D> the type of the fourth value
   * @param <E> the type of the fifth value
   * @param <F> the type of the sixth value
   * @param a   the first value
   * @param b   the second value
   * @param c   the third value
   * @param d   the fourth value
   * @param e   the fifth value
   * @param f   the sixth value
   * @return a tuple containing the values
   */
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

  /**
   * Write the contents of the tuples as a String. {@code toString} will be called
   * on each value in the tuple.
   * 
   * @param tuple a tuple
   * @return a String in the format "(Hello, Word, 123)"
   */
  public static String toString(Tuple tuple) {
    return tuple
      .values()
      .map(Object::toString)
      .collect(Collectors.joining(", ", "(", ")"));
  }

  /**
   * Compare two Tuples for equality. They must have the same cardinality and
   * order to be considered equal. The types of the values are not explicitly
   * compared. {@code Objects.equals(...)} is used to compare each pair of values
   * in the two Tuples.
   * 
   * @param a a tuple
   * @param b another tuple
   * @return true if the two Tuples are the same
   */
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
      .allMatch(
        valPair -> Objects.equals(valPair.getA(), valPair.getB())
      );
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
   * A simple implementation of Two
   * 
   * @author Paul Greenlee
   * 
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   */
  public static class TwoImpl<A, B> implements Two<A, B> {
    private final A a;
    private final B b;

    /**
     * Create an instance with two values
     * 
     * @param a the first value
     * @param b the second value
     */
    private TwoImpl(A a, B b) {
      super();
      this.a = a;
      this.b = b;
    }

    public A getA() {
      return a;
    }

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

  /**
   * A simple implementation of Three
   * 
   * @author Paul Greenlee
   * 
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param <C> the type of the third value
   */
  public static class ThreeImpl<A, B, C> implements Three<A, B, C> {

    private final A a;
    private final B b;
    private final C c;

    /**
     * Create an instance with three values
     * 
     * @param a the first value
     * @param b the second value
     * @param c the third value
     */
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

  /**
   * A simple implementation of Four
   * 
   * @author Paul Greenlee
   * 
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param <C> the type of the third value
   * @param <D> the type of the fourth value
   */
  public static class FourImpl<A, B, C, D>
    implements Four<A, B, C, D> {

    private final A a;
    private final B b;
    private final C c;
    private final D d;

    /**
     * Create an instance with four values
     * 
     * @param a the first value
     * @param b the second value
     * @param c the third value
     * @param d the fourth value
     */
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

  /**
   * A simple implementation of Five
   * 
   * @author Paul Greenlee
   * 
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param <C> the type of the third value
   * @param <D> the type of the fourth value
   * @param <E> the type of the fifth value
   */
  public static class FiveImpl<A, B, C, D, E>
    implements Five<A, B, C, D, E> {

    private final A a;
    private final B b;
    private final C c;
    private final D d;
    private final E e;

    /**
     * Create an instance with five values
     * 
     * @param a the first value
     * @param b the second value
     * @param c the third value
     * @param d the fourth value
     * @param e the fifth value
     */
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

  /**
   * A simple implementation of Six
   * 
   * @author Paul Greenlee
   * 
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param <C> the type of the third value
   * @param <D> the type of the fourth value
   * @param <E> the type of the fifth value
   * @param <F> the type of the sixth value
   */
  public static class SixImpl<A, B, C, D, E, F>
    implements Six<A, B, C, D, E, F> {

    private final A a;
    private final B b;
    private final C c;
    private final D d;
    private final E e;
    private final F f;

    /**
     * Create an instance with six values
     * 
     * @param a the first value
     * @param b the second value
     * @param c the third value
     * @param d the fourth value
     * @param e the fifth value
     * @param f the sixth value
     */
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
