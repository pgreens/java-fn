package com.paulgreenlee.fn;

import java.util.Objects;

public class Tuples {

	private Tuples() {

	}

	public static <A, B> Two<A, B> of(A a, B b) {
		return new Two<A, B>(a, b);
	}

	public static class Two<A, B> {
		private final A a;
		private final B b;

		public Two(A a, B b) {
			super();
			this.a = a;
			this.b = b;
		}

		public A getA() {
			return a;
		}

		public B getB() {
			return b;
		}

		@Override
		public int hashCode() {
			return Objects.hash(a, b);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			@SuppressWarnings("rawtypes")
			Two other = (Two) obj;
			return Objects.equals(a, other.a) && Objects.equals(b, other.b);
		}

		@Override
		public String toString() {
			return "(" + a + ", " + b + ")";
		}

	}

}
