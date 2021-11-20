package com.paulgreenlee.fn;

import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.paulgreenlee.fn.Tuples.Two;

public class StreamUtils {

  private StreamUtils() {

  }

  public static <E> Stream<E> append(Stream<E> stream, E elem) {
    Objects.requireNonNull(stream);
    return Stream.concat(stream, Stream.of(elem));
  }

  public static <E> Stream<E> prepend(E elem, Stream<E> stream) {
    Objects.requireNonNull(stream);
    return Stream.concat(Stream.of(elem), stream);
  }

  /**
   * Take two streams and produces a new stream whose elements are pairs of the
   * elements from the two streams. For example, if the streams are represented as
   * {@code [a1, a2, a3, ..., an]} and {@code [b1, b2, b3, ..., bn]}, then the
   * resulting stream will be [(a1, b1), (a2, b2), ...]. The stream will be as
   * long as the shorter of the two streams: min (an, bn), in the example.
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

  public static Stream<Integer> sequence(Integer start) {
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
   * @see {@link StreamUtils#zip} for a description of the operation
   */
  public static class Zipperator<A, B>
    extends AbstractSpliterator<Tuples.Two<A, B>> {

    private Spliterator<A> as;
    private Spliterator<B> bs;

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

  public static void main(String[] args) {
    StreamUtils
      .zip(
        Stream.of("A", "B", "C", "D"),
        Stream.of("0", "1", "2", "3", "4")
      )
      .peek(two -> System.out.println(two))
      .collect(Collectors.toList());
  }
}
