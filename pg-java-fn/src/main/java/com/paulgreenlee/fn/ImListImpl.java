package com.paulgreenlee.fn;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * A simple implementation of {@link ImList} that uses a Stream supplier
 * function to store elements.
 * </p>
 * <p>
 * Along with {@code ImList} this class also implements {@link java.util.List}.
 * The intention is that you would code against the {@code ImList} interface and
 * ignore the extra methods defined here. However, by also implementing the
 * standard List interface, it allows this class to be recognized as a
 * {@code List} at runtime. This should improve interoperability with existing
 * code and libraries.
 * </p>
 * 
 * @author Paul Greenlee
 *
 * @param <E> the type of the elements in the list
 */
public class ImListImpl<E> implements ImList<E>, List<E> {

  @SuppressWarnings("rawtypes")
  private static final ImListImpl EMPTY_LIST = new ImListImpl<>(
    () -> Stream.empty(),
    0
  );

  private final int size;
  private final Supplier<Stream<E>> streamGen;

  ImListImpl(Supplier<Stream<E>> streamGen, int size) {
    this.size = size;
    this.streamGen = Objects
      .requireNonNull(streamGen, "stream generator required");
  }

  public int size() {
    return size;
  }

  public Stream<E> stream() {
    return streamGen.get();
  }

  public static <E> ImListImpl<E> of(ImList<E> other) {
    Objects.requireNonNull(other, "Non-null list required");
    return new ImListImpl<>(() -> other.stream(), other.size());
  }

  @SafeVarargs
  public static <E> ImListImpl<E> of(E... elements) {
    Objects.requireNonNull(elements, "Non-null array required");
    return new ImListImpl<>(
      () -> Arrays.stream(elements),
      elements.length
    );
  }

  @SuppressWarnings("unchecked")
  public static <E> ImListImpl<E> emptyList() {
    return (ImListImpl<E>) EMPTY_LIST;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @SuppressWarnings("unchecked")
  public boolean equals(Object other) {
    if (other == null)
      return false;
    if (!(other instanceof ImList))
      return false;
    @SuppressWarnings("rawtypes")
    ImList oList = (ImList) other;
    if (size != oList.size())
      return false;
    return StreamUtils
      .<E, Object>zip(streamGen.get(), oList.stream())
      .allMatch(pair -> Objects.equals(pair.getA(), pair.getB()));
  }

  // The following are simple implementations for the java.util.List interface for
  // easier interoperability with other code.

  @Override
  public boolean contains(Object o) {
    return streamGen.get().anyMatch(el -> el.equals(o));
  }

  @Override
  public Iterator<E> iterator() {
    return streamGen.get().iterator();
  }

  @Override
  public Object[] toArray() {
    return streamGen.get().toArray();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T[] toArray(final T[] a) {
    return streamGen
      .get()
      .toArray(size -> (T[]) Arrays.copyOf(a, size, a.getClass()));
  }

  @Override
  public E get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(index);
    }
    return streamGen.get().skip(index).findFirst().orElseThrow();
  }

  @Override
  public int indexOf(Object o) {
    return StreamUtils
      .zip(StreamUtils.sequence(0), streamGen.get())
      .filter(item -> item.getB().equals(o))
      .findFirst()
      .map(Tuples.Two::getA)
      .orElse(0);
  }

  @Override
  public int lastIndexOf(Object o) {
    return StreamUtils
      .zip(StreamUtils.sequence(0), streamGen.get())
      .filter(item -> item.getB().equals(o))
      .map(Tuples.Two::getA)
      .reduce(Math::max)
      .orElse(0);
  }

  @Override
  public ListIterator<E> listIterator() {
    return streamGen
      .get()
      .collect(Collectors.toList())
      .listIterator();
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    return streamGen
      .get()
      .collect(Collectors.toList())
      .listIterator(index);
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    if (fromIndex < 0 || toIndex > size)
      throw new IndexOutOfBoundsException(
        "range [" + fromIndex + ", " + toIndex
          + ") is invalid for list of size " + size
      );
    if (fromIndex > toIndex)
      throw new IllegalArgumentException(
        "fromIndex is greater than toIndex for range [" + fromIndex
          + ", " + toIndex + ")"
      );
    return StreamUtils
      .zip(StreamUtils.sequence(0), streamGen.get())
      .dropWhile(two -> two.getA() < fromIndex)
      .takeWhile(two -> two.getA() < toIndex)
      .map(Tuples.Two::getB)
      .collect(Collectors.toList());
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return c
      .stream()
      .allMatch(
        otherEl -> streamGen
          .get()
          .anyMatch(item -> item.equals(otherEl))
      );
  }

  @Override
  public boolean add(E e) {
    throw new UnsupportedOperationException(
      "add not allowed on an immutable list"
    );
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException(
      "remove not allowed on an immutable list"
    );
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    throw new UnsupportedOperationException(
      "addAll not allowed on an immutable list"
    );
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    throw new UnsupportedOperationException(
      "addAll not allowed on an immutable list"
    );
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException(
      "removeAll not allowed on an immutable list"
    );
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException(
      "retainAll not allowed on an immutable list"
    );
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException(
      "clear not allowed on an immutable list"
    );
  }

  @Override
  public E set(int index, E element) {
    throw new UnsupportedOperationException(
      "set not allowed on an immutable list"
    );
  }

  @Override
  public void add(int index, E element) {
    throw new UnsupportedOperationException(
      "add not allowed on an immutable list"
    );
  }

  @Override
  public E remove(int index) {
    throw new UnsupportedOperationException(
      "remove not allowed on an immutable list"
    );
  }

}
