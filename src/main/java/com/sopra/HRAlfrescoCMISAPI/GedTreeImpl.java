package com.sopra.HRAlfrescoCMISAPI;

import java.util.List;

public class GedTreeImpl<T> implements GedTree<T>
{
  private T item;
  private final List<GedTree<T>> children;
  
  public GedTreeImpl(T item, List<GedTree<T>> children)
  {
    if (this.item == null) {
      throw new IllegalArgumentException("item must be set");
    }
    this.item = item;
    this.children = children;
  }
  

  public T getItem()
  {
    return item;
  }
  
  public List<GedTree<T>> getChildren() {
    return children;
  }
}
