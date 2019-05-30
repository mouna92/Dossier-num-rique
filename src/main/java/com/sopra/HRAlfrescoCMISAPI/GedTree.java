package com.sopra.HRAlfrescoCMISAPI;

import java.util.List;

public abstract interface GedTree<T>
{
  public abstract T getItem();
  
  public abstract List<GedTree<T>> getChildren();
}