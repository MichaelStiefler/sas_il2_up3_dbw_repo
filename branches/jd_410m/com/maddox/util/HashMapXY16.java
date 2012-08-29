package com.maddox.util;

public class HashMapXY16 extends HashMapInt
{
  public HashMapXY16()
  {
  }

  public HashMapXY16(int paramInt)
  {
    super(paramInt);
  }

  public HashMapXY16(int paramInt, float paramFloat) {
    super(paramInt, paramFloat);
  }

  public HashMapXY16(HashMapXY16 paramHashMapXY16) {
    super(paramHashMapXY16);
  }

  public HashMapIntEntry getEntry(int paramInt1, int paramInt2) {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;
    return getEntry(i);
  }

  public boolean containsKey(int paramInt1, int paramInt2) {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;
    return containsKey(i);
  }

  public Object get(int paramInt1, int paramInt2) {
    return get(paramInt2 & 0xFFFF | paramInt1 << 16);
  }

  public Object put(int paramInt1, int paramInt2, Object paramObject) {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;
    return put(i, paramObject);
  }

  public Object remove(int paramInt1, int paramInt2) {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;
    return remove(i);
  }

  public boolean equals(Object paramObject)
  {
    return this == paramObject;
  }
}