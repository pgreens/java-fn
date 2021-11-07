package com.paulgreenlee.fn;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import com.paulgreenlee.fn.Tuples.Two;

public class TuplesTest {

	@Test
	void two() {
		Two<String,String> two = Tuples.of("Hello", "World");
		assertThat(two.getA(), equalTo("Hello"));
		assertThat(two.getB(), equalTo("World"));
	}
}
