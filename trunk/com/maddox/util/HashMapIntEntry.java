package com.maddox.util;

public abstract interface HashMapIntEntry
{
  public abstract int getKey();

  public abstract Object getValue();

  public abstract Object setValue(Object paramObject);

  public abstract boolean equals(Object paramObject);

  public abstract int hashCode();
}