package com.paulgreenlee.fn;

import java.util.stream.Stream;

/**
 * A simple list interface.
 * 
 * @author Paul Greenlee
 *
 * @param <E> the type of the elements of the list
 * @implSpec implementations of this interface must be immutable
 */
public interface ImList<E> {

  int size();
  
  boolean isEmpty();

  Stream<E> stream();

}
