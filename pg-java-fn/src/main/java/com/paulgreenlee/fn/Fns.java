package com.paulgreenlee.fn;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Extensions for {@link java.util.function.Function}.
 * 
 * @author Paul Greenlee
 */
public class Fns {

  private Fns() {

  }

  /**
   * A function that accepts three arguments and returns a result.
   * 
   * @author Paul Greenlee
   *
   * @param <A> the type of the first argument to the function
   * @param <B> the type of the second argument to the function
   * @param <C> the type of the third argument to the function
   * @param <R> the type of the return value of the function
   */
  @FunctionalInterface
  public interface TriFunction<A, B, C, R> {

    /**
     * Apply the function to the arguments
     * 
     * @param a the first argument
     * @param b the second argument
     * @param c the third argument
     * @return the result of the function
     */
    R apply(A a, B b, C c);
  }

  /**
   * A function that accepts four arguments and returns a result.
   * 
   * @author Paul Greenlee
   *
   * @param <A> the type of the first argument to the function
   * @param <B> the type of the second argument to the function
   * @param <C> the type of the third argument to the function
   * @param <D> the type of the fourth argument to the function
   * @param <R> the type of the return value of the function
   */
  @FunctionalInterface
  public interface TetraFunction<A, B, C, D, R> {

    /**
     * Apply the function to the arguments
     * 
     * @param a the first argument
     * @param b the second argument
     * @param c the third argument
     * @param d the fourth argument
     * @return the result of the function
     */
    R apply(A a, B b, C c, D d);
  }

  /**
   * A function that accepts five arguments and returns a result.
   * 
   * @author Paul Greenlee
   *
   * @param <A> the type of the first argument to the function
   * @param <B> the type of the second argument to the function
   * @param <C> the type of the third argument to the function
   * @param <D> the type of the fourth argument to the function
   * @param <E> the type of the fifth argument to the function
   * @param <R> the type of the return value of the function
   */
  @FunctionalInterface
  public interface PentaFunction<A, B, C, D, E, R> {

    /**
     * Apply the function to the arguments
     * 
     * @param a the first argument
     * @param b the second argument
     * @param c the third argument
     * @param d the fourth argument
     * @param e the fifth argument
     * @return the result of the function
     */
    R apply(A a, B b, C c, D d, E e);
  }

  /**
   * A function that accepts six arguments and returns a result. Wowee!
   * 
   * @author Paul Greenlee
   *
   * @param <A> the type of the first argument to the function
   * @param <B> the type of the second argument to the function
   * @param <C> the type of the third argument to the function
   * @param <D> the type of the fourth argument to the function
   * @param <E> the type of the fifth argument to the function
   * @param <F> the type of the sixth argument to the function
   * @param <R> the type of the return value of the function
   */
  @FunctionalInterface
  public interface HexaFunction<A, B, C, D, E, F, R> {

    /**
     * Apply the function to the arguments
     * 
     * @param a the first argument
     * @param b the second argument
     * @param c the third argument
     * @param d the fourth argument
     * @param e the fifth argument
     * @param f the sixth argument
     * @return the result of the function
     */
    R apply(A a, B b, C c, D d, E e, F f);
  }

  /**
   * Restructure a function that takes two arguments as a series of two nested
   * functions that each take a single argument. This allows arguments to
   * partially applied in stages.
   * 
   * @param <A> the type of the first argument to the function
   * @param <B> the type of the second argument to the function
   * @param <R> the return type of the function
   * @param fn  the two-argument function
   * @return a series of nested functions that take one argument each
   */
  public static <A, B, R> Function<A, Function<B, R>> curry(
    BiFunction<A, B, R> fn
  ) {
    return (A a) -> (B b) -> fn.apply(a, b);
  }

  /**
   * Restructure a function that takes three arguments as a series of three nested
   * functions that each take a single argument. This allows arguments to
   * partially applied in stages.
   * 
   * @param <A> the type of the first argument to the function
   * @param <B> the type of the second argument to the function
   * @param <C> the type of the third argument to the function
   * @param <R> the return type of the function
   * @param fn  the three-argument function
   * @return a series of nested functions that take one argument each
   */
  public static <A, B, C, R> Function<A, Function<B, Function<C, R>>> curry(
    TriFunction<A, B, C, R> fn
  ) {
    return (A a) -> (B b) -> (C c) -> fn.apply(a, b, c);
  }

  /**
   * Restructure a function that takes four arguments as a series of four nested
   * functions that each take a single argument. This allows arguments to
   * partially applied in stages.
   * 
   * @param <A> the type of the first argument to the function
   * @param <B> the type of the second argument to the function
   * @param <C> the type of the third argument to the function
   * @param <D> the type of the fourth argument to the function
   * @param <R> the return type of the function
   * @param fn  the four-argument function
   * @return a series of nested functions that take one argument each
   */
  public static <A, B, C, D, R> Function<A, Function<B, Function<C, Function<D, R>>>> curry(
    TetraFunction<A, B, C, D, R> fn
  ) {
    return (A a) -> (B b) -> (C c) -> (D d) -> fn.apply(a, b, c, d);
  }

  /**
   * Restructure a function that takes five arguments as a series of five nested
   * functions that each take a single argument. This allows arguments to
   * partially applied in stages.
   * 
   * @param <A> the type of the first argument to the function
   * @param <B> the type of the second argument to the function
   * @param <C> the type of the third argument to the function
   * @param <D> the type of the fourth argument to the function
   * @param <E> the type of the fifth argument to the function
   * @param <R> the return type of the function
   * @param fn  the five-argument function
   * @return a series of nested functions that take one argument each
   */
  public static <A, B, C, D, E, R> Function<A, Function<B, Function<C, Function<D, Function<E, R>>>>> curry(
    PentaFunction<A, B, C, D, E, R> fn
  ) {
    return (
      A a
    ) -> (B b) -> (C c) -> (D d) -> (E e) -> fn.apply(a, b, c, d, e);
  }

  /**
   * Restructure a function that takes six arguments as a series of six nested
   * functions that each take a single argument. This allows arguments to
   * partially applied in stages.
   * 
   * @param <A> the type of the first argument to the function
   * @param <B> the type of the second argument to the function
   * @param <C> the type of the third argument to the function
   * @param <D> the type of the fourth argument to the function
   * @param <E> the type of the fifth argument to the function
   * @param <F> the type of the sixth argument to the function
   * @param <R> the return type of the function
   * @param fn  the six-argument function
   * @return a series of nested functions that take one argument each
   */
  public static <A, B, C, D, E, F, R> Function<A, Function<B, Function<C, Function<D, Function<E, Function<F, R>>>>>> curry(
    HexaFunction<A, B, C, D, E, F, R> fn
  ) {
    return (A a) -> (B b) -> (
      C c
    ) -> (D d) -> (E e) -> (F f) -> fn.apply(a, b, c, d, e, f);
  }

}
