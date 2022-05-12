package com.paulgreenlee.fn;

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.paulgreenlee.fn.Tuples.Two;

/**
 * Helper functions for working with Streams.
 * 
 * @author Paul Greenlees
 *
 */
public class StreamUtils {

  private StreamUtils() {

  }

  /**
   * Put a single element at the beginning of a stream.
   * 
   * @param <E>    the type of elements in the stream
   * @param stream a stream
   * @param elem   the element to be placed at the start
   * @return a new stream that contains the new element followed by the elements
   *         of the other stream
   */
  public static <E> Stream<E> append(Stream<E> stream, E elem) {
    Objects.requireNonNull(stream);
    return Stream.concat(stream, Stream.of(elem));
  }

  /**
   * Put a single element at the end of a stream.
   * 
   * @param <E>    the type of elements in the stream
   * @param stream a stream
   * @param elem   the element to be placed at the end
   * @return a new stream that contains the elements of the exiting stream
   *         followed by the new elements
   */
  public static <E> Stream<E> prepend(E elem, Stream<E> stream) {
    Objects.requireNonNull(stream);
    return Stream.concat(Stream.of(elem), stream);
  }

  /**
   * <p>
   * Take two streams and produces a new stream whose elements are pairs of the
   * elements from the two streams. For example, if the streams are represented as
   * {@code [a1, a2, a3, ..., an]} and {@code [b1, b2, b3, ..., bn]}, then the
   * resulting stream will be {@code [(a1, b1), (a2, b2), ...]}. The stream will
   * be as long as the shorter of the two streams.
   * </p>
   * 
   * @param <A> the type of the first value
   * @param <B> the type of the second value
   * @param as  the stream of A values
   * @param bs  the stream of B values
   * @return a stream of tuples of pairs of elements from each stream
   */
  public static <A, B> Stream<Tuples.Two<A, B>> zip(
    Stream<A> as,
    Stream<B> bs
  ) {
    return StreamSupport.stream(Zipperator.of(as, bs), false);
  }

  /**
   * Generate an infinite stream of Integers, starting at a specified value.
   * 
   * @param start the value to begin counting from (inclusive)
   * @return an infinite stream of Integers counting up from the start value
   */
  public static Stream<Integer> integers(Integer start) {
    Objects.requireNonNull(start);
    return Stream.iterate(start, StreamUtils::inc);
  }

  private static Integer inc(Integer a) {
    return a + 1;
  }

  /**
   * A spliterator to facilitate the zip function on streams.
   * 
   * @author Paul Greenlee
   *
   * @param <A> the type of the first stream
   * @param <B> the type of the second stream
   * 
   * @see StreamUtils#zip
   */
  public static class Zipperator<A, B>
    extends AbstractSpliterator<Tuples.Two<A, B>> {

    private Spliterator<A> as;
    private Spliterator<B> bs;

    /**
     * Create a Zipperator from two Spliterators
     * 
     * @param est                       the estimated size of the zipped stream
     * @param additionalCharacteristics the characteristics of the Spliterator
     * @param as                        a Spliterator
     * @param bs                        another Spliterator
     */
    protected Zipperator(
      long est,
      int additionalCharacteristics,
      Spliterator<A> as,
      Spliterator<B> bs
    ) {
      super(est, additionalCharacteristics);
      this.as = as;
      this.bs = bs;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Two<A, B>> action) {
      if (action == null)
        throw new NullPointerException();

      Holder<A> aHold = new Holder<>();
      Holder<B> bHold = new Holder<>();
      if (as.tryAdvance(aHold) && bs.tryAdvance(bHold)) {
        action.accept(Tuples.of(aHold.consumed, bHold.consumed));
        return true;
      }

      return false;
    }

    static final class Holder<T> implements Consumer<T> {
      T consumed;

      @Override
      public void accept(T value) {
        this.consumed = value;
      }
    }

    /**
     * Create a Zipperator from two streams
     * 
     * @param <A> the type of the first stream
     * @param <B> the type of the second stream
     * @param as  the first stream
     * @param bs  the second stream
     * @return a Zipperator for the two streams
     */
    public static <A, B> Zipperator<A, B> of(
      Stream<A> as,
      Stream<B> bs
    ) {
      Spliterator<A> aSplit = as.spliterator();
      Spliterator<B> bSplit = bs.spliterator();
      int characteristics = aSplit.characteristics()
        & bSplit.characteristics();
      return new Zipperator<A, B>(
        Math.min(aSplit.estimateSize(), bSplit.estimateSize()),
        characteristics,
        aSplit,
        bSplit
      );
    }
  }

}
