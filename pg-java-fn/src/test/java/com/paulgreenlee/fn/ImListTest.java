package com.paulgreenlee.fn;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class ImListTest {

  enum Example {
    EMPTY(ImListImpl.emptyList()),
    ONE(ImListImpl.of("A")),
    TWO(ImListImpl.of("A", "B")),
    THREE(ImListImpl.of("A", "B", "C")),
    TEN(
      ImListImpl.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")
    );

    private ImListImpl<Object> list;

    Example(ImListImpl<Object> list) {
      this.list = list;
    }
  }

  @ParameterizedTest
  @EnumSource(Example.class)
  public void equal(Example eg) {
    ImList<Object> other = ImListImpl.<Object>of(eg.list);
    assertThat(eg.list.equals(eg.list), equalTo(true));
    assertThat(eg.list.equals(other), equalTo(true));
    assertThat(other.equals(eg.list), equalTo(true));
  }

  @ParameterizedTest
  @EnumSource(Example.class)
  public void notEqual(Example eg) {
    Arrays
      .stream(Example.values())
      .filter(other -> other != eg)
      .forEach(other -> {
        assertThat(eg.list.equals(other.list), equalTo(false));
        assertThat(other.list.equals(eg.list), equalTo(false));
      });
  }

  @ParameterizedTest
  @EnumSource(Example.class)
  public void add(Example eg) {
    String elem = "X";
    ImList<Object> before = eg.list;
    ImList<Object> after = ImListFns.add(before, elem);
    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(arr(after)[after.size() - 1], equalTo(elem));
    assertThat(before, equalTo(ImListFns.dropLast(after)));
  }

  @ParameterizedTest
  @EnumSource(Example.class)
  public void addFirst(Example eg) {
    String elem = "X";
    ImList<Object> before = eg.list;
    ImList<Object> after = ImListFns.addFirst(elem, before);
    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(arr(after)[0], equalTo(elem));
    assertThat(before, equalTo(ImListFns.dropFirst(after)));
  }

  @ParameterizedTest
  @EnumSource(Example.class)
  public void dropFirst(Example eg) {
    ImList<Object> before = eg.list;
    ImList<Object> after = ImListFns.dropFirst(before);
    assertThat(after.size(), equalTo(Math.max(before.size() - 1, 0)));
    assertThat(ImListFns.dropFirst(before), equalTo(after));
  }

  @ParameterizedTest
  @EnumSource(Example.class)
  public void first(Example eg) {
    ImList<?> list = eg.list;
    if (list.isEmpty()) {
      assertThrows(
        NoSuchElementException.class,
        () -> ImListFns.first(list)
      );
    } else {
      Object actual = ImListFns.first(list);
      assertThat(actual, equalTo(arr(list)[0]));
    }
  }

  @ParameterizedTest
  @EnumSource(Example.class)
  public void last(Example eg) {
    ImList<?> list = eg.list;
    if (list.isEmpty()) {
      assertThrows(
        NoSuchElementException.class,
        () -> ImListFns.last(list)
      );
    } else {
      Object actual = ImListFns.last(list);
      assertThat(actual, equalTo(arr(list)[list.size() - 1]));
    }
  }

  private static Object[] arr(ImList<?> list) {
    return list.stream().toArray();
  }
}
