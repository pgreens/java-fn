package com.paulgreenlee.fn;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A collection of functions for working with {@link ImList}.
 * 
 * @author Paul Greenlee
 */
public class ImListFns {

  private ImListFns() {
  }

  /**
   * Create a list from a set of elements.
   * 
   * @param <E>      the type of the elements
   * @param elements the elements for ths list
   * @return an immutable list containing the specified elements
   */
  @SafeVarargs
  public static <E> ImList<E> listOf(E... elements) {
    return ImListImpl.of(elements);
  }

  /**
   * Add an element to the end of the list. This does not modify the input
   * {@code list}.
   * 
   * @param <E>  the type of the elements in the list
   * @param list a list
   * @param elem the element to add
   * @return a new list that has {@code elem} as the last element.
   */
  public static <E> ImList<E> add(ImList<E> list, E elem) {
    Objects.requireNonNull(list);
    return new ImListImpl<>(
      () -> Stream.concat(list.stream(), Stream.of(elem)),
      list.size() + 1
    );
  }

  /**
   * Add an element to the start of the list. This does not modify the input
   * {@code list}.
   * 
   * @param <E>  the type of the elements in the list
   * @param elem the element to add
   * @param list a list
   * @return a new list that has {@code elem} as the first element.
   */
  public static <E> ImList<E> addFirst(E elem, ImList<E> list) {
    Objects.requireNonNull(list);
    return new ImListImpl<>(
      () -> Stream.concat(Stream.of(elem), list.stream()),
      list.size() + 1
    );
  }

  /**
   * Take the first element from the list. This does not modify the input
   * {@code list}.
   * 
   * @param <E>  the type of the elements in the list
   * @param list a list
   * @return the first element in the list, if the list is not empty
   * @throws NoSuchElementException if the list is empty
   */
  public static <E> E first(ImList<E> list) {
    Objects.requireNonNull(list);
    return list
      .stream()
      .findFirst()
      .orElseThrow(
        () -> new NoSuchElementException("The list is empty")
      );
  }

  /**
   * Take the first element from the list. This does not modify the input
   * {@code list}.
   * 
   * @param <E>  the type of the elements in the list
   * @param list a list
   * @return the last element in the list, if the list is not empty
   * @throws NoSuchElementException if the list is empty
   */
  public static <E> E last(ImList<E> list) {
    Objects.requireNonNull(list);
    if (list.isEmpty())
      throw new NoSuchElementException("The list is empty");

    return list
      .stream()
      .skip(list.size() - 1)
      .findFirst()
      .orElseThrow(
        () -> new NoSuchElementException("The list is empty")
      );
  }

  /**
   * Take the first element out of the list, and return a new list containing the
   * rest of the elements. This does not modify the input {@code list}.
   * 
   * @param <E>  the type of the elements in the list
   * @param list a list
   * @return a new list containing all but the first element of {@code list}
   */
  public static <E> ImList<E> dropFirst(ImList<E> list) {
    Objects.requireNonNull(list);
    if (list.isEmpty()) {
      return ImListImpl.emptyList();
    }
    return new ImListImpl<>(
      () -> list.stream().skip(1),
      list.size() - 1
    );
  }

  /**
   * Take the last element out of the list, and return a new list containing the
   * rest of the elements. This does not modify the input {@code list}.
   * 
   * @param <E>  the type of the elements in the list
   * @param list a list
   * @return a new list containing all but the last element of {@code list}
   */
  public static <E> ImList<E> dropLast(ImList<E> list) {
    Objects.requireNonNull(list);
    if (list.size() == 0) {
      return ImListImpl.emptyList();
    }
    return new ImListImpl<>(
      () -> list.stream().limit(list.size() - 1),
      list.size() - 1
    );
  }

  /**
   * Join two lists together. All of the elements of {@code a} will be first,
   * followed by all the elements of {@code b}. This does not modify either of the
   * input lists.
   * 
   * @param <E> the type of the elements in the lists
   * @param a   the first list
   * @param b   the second list
   * @return a new list containing all the elements of {@code a} and {@code b}
   */
  public static <E> ImList<E> concat(ImList<E> a, ImList<E> b) {
    Objects.requireNonNull(a);
    Objects.requireNonNull(b);
    return new ImListImpl<E>(
      () -> Stream.concat(a.stream(), b.stream()),
      a.size() + b.size()
    );
  }
}
