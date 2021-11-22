package com.paulgreenlee.fn;

import static com.paulgreenlee.fn.Fns.*;

public class Demo {

  public static void main(String[] args) {
    Integer sum = curry(Demo::sum)
      .apply(3)
      .apply(4)
      .apply(5)
      .apply(6);
  }

  public static Integer sum(
    Integer a,
    Integer b,
    Integer c,
    Integer d
  ) {
    return a + b + c + d;
  }

}
