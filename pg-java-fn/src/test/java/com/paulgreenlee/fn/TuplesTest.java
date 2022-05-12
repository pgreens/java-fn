package com.paulgreenlee.fn;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.paulgreenlee.fn.Tuples.Three;
import com.paulgreenlee.fn.Tuples.Tuple;
import com.paulgreenlee.fn.Tuples.Two;

public class TuplesTest {

  private static final Tuple INSTANCE = Tuples.of("A", "B");

  enum EqualTestCase {
    SAME_INSTANCE(INSTANCE, INSTANCE),
    BOTH_NULL(null, null),
    MATCHING_VALUES(
      Tuples.of("A", "B", "C", "D"),
      Tuples.of("A", "B", "C", "D")
    );

    private Tuple a;
    private Tuple b;

    EqualTestCase(Tuple a, Tuple b) {
      this.a = a;
      this.b = b;
    }
  }

  enum NotEqualTestCase {
    A_NULL(null, INSTANCE),
    B_NULL(INSTANCE, null),
    DIFFERENT_CARDINALITY(
      Tuples.of("A", "B", "C"),
      Tuples.of("A", "B", "C", "D")
    ),
    DIFFERENT_VALUES(
      Tuples.of("A", "B", "C", "D"),
      Tuples.of("A", "B", "C", "Whoa!")
    );

    private Tuple a;
    private Tuple b;

    NotEqualTestCase(Tuple a, Tuple b) {
      this.a = a;
      this.b = b;
    }
  }

  @ParameterizedTest
  @EnumSource(EqualTestCase.class)
  void equals(EqualTestCase testCase) {
    assertThat(Tuples.equal(testCase.a, testCase.b), equalTo(true));
  }

  @ParameterizedTest
  @EnumSource(NotEqualTestCase.class)
  void notEqual(NotEqualTestCase testCase) {
    assertThat(Tuples.equal(testCase.a, testCase.b), equalTo(false));
  }

  @Nested
  class TwoTests {

    @Test
    void checkVals() {
      Two<String, String> two = Tuples.of("A", "B");
      assertThat(two.getA(), equalTo("A"));
      assertThat(two.getB(), equalTo("B"));
    }

    @Test
    void equals() {
      Two<String, String> a = Tuples.of("A", "B");
      Two<String, String> b = Tuples.of("A", "B");
      assertThat(a, equalTo(b));
    }

  }

  @Nested
  class ThreeTests {

    @Test
    void checkVals() {
      Three<String, Integer, Boolean> two = Tuples
        .of("A", 1, Boolean.TRUE);
      assertThat(two.getA(), equalTo("A"));
      assertThat(two.getB(), equalTo(1));
      assertThat(two.getC(), equalTo(Boolean.TRUE));
    }

    @Test
    void equals() {
      Three<String, Integer, Boolean> a = Tuples
        .of("A", 1, Boolean.TRUE);
      Three<String, Integer, Boolean> b = Tuples
        .of("A", 1, Boolean.TRUE);
      assertThat(a, equalTo(b));
    }

  }

}
