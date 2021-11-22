package com.paulgreenlee.fn;

import java.util.stream.Stream;

/**
 * A simple list interface. This does not inherit from any native List or
 * Collection class, because their interfaces are designed in a way that assumes
 * they are mutable. This interface assumes that implementations are immutable,
 * so it works as a signal in the code that uses it that this list is not
 * mutable. Therefore, there is no need to defensively copy it or wrap it in an
 * unmodifiable wrapper.
 * 
 * @author Paul Greenlee
 *
 * @param <E> the type of the elements of the list
 * @implSpec implementations of this interface must be immutable
 * @see ImListFns
 */
public interface ImList<E> {

  /**
   * The number of elements in the list.
   * 
   * @return the number of elements in the list
   */
  int size();

  /**
   * Is the list empty?
   * 
   * @return true if the list has no elements
   */
  boolean isEmpty();

  /**
   * Stream the elements of the list. See {@link ImListFns} for helper functions
   * to work with lists.
   * 
   * @return a stream of the elements.
   */
  Stream<E> stream();

}
