package com.paulgreenlee.fn;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

public class ImListFns {

  private ImListFns() {
  }

  @SafeVarargs
  public static <E> ImList<E> listOf(E... elements) {
    return ImListImpl.of(elements);
  }

  public static <E> ImList<E> add(ImList<E> list, E elem) {
    Objects.requireNonNull(list);
    return new ImListImpl<>(
      () -> Stream.concat(list.stream(), Stream.of(elem)),
      list.size() + 1
    );
  }

  public static <E> ImList<E> addFirst(E elem, ImList<E> list) {
    Objects.requireNonNull(list);
    return new ImListImpl<>(
      () -> Stream.concat(Stream.of(elem), list.stream()),
      list.size() + 1
    );
  }

  public static <E> E first(ImList<E> list) {
    Objects.requireNonNull(list);
    return list
      .stream()
      .findFirst()
      .orElseThrow(
        () -> new NoSuchElementException("The list is empty")
      );
  }

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

  public static <E> ImList<E> concat(ImList<E> a, ImList<E> b) {
    Objects.requireNonNull(a);
    Objects.requireNonNull(b);
    return new ImListImpl<E>(
      () -> Stream.concat(a.stream(), b.stream()),
      a.size() + b.size()
    );
  }
}
