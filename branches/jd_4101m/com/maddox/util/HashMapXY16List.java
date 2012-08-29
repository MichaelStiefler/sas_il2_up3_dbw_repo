package com.maddox.util;

import java.util.ArrayList;
import java.util.List;

public class HashMapXY16List
{
  private transient HashMapInt mapXY;
  private int initialCapacity = 101;
  private float loadFactor = 0.75F;

  public HashMapXY16List() {
    this.mapXY = new HashMapInt(this.initialCapacity, this.loadFactor);
  }

  public HashMapXY16List(int paramInt) {
    this.initialCapacity = paramInt;
    this.mapXY = new HashMapInt(paramInt, this.loadFactor);
  }

  public HashMapXY16List(int paramInt, float paramFloat) {
    this.initialCapacity = paramInt;
    this.loadFactor = paramFloat;
    this.mapXY = new HashMapInt(paramInt, paramFloat);
  }

  public int size() {
    int i = 0;
    HashMapIntEntry localHashMapIntEntry = this.mapXY.nextEntry(null);
    while (localHashMapIntEntry != null) {
      ArrayList localArrayList = (ArrayList)(ArrayList)localHashMapIntEntry.getValue();
      i += localArrayList.size();
      localHashMapIntEntry = this.mapXY.nextEntry(localHashMapIntEntry);
    }
    return i;
  }

  public int[] allKeys() {
    if (this.mapXY.size() == 0) return null;
    int[] arrayOfInt = new int[this.mapXY.size()];
    HashMapIntEntry localHashMapIntEntry = this.mapXY.nextEntry(null);
    int i = 0;
    while (localHashMapIntEntry != null) {
      arrayOfInt[(i++)] = localHashMapIntEntry.getKey();
      localHashMapIntEntry = this.mapXY.nextEntry(localHashMapIntEntry);
    }
    return arrayOfInt;
  }

  public int key2x(int paramInt) {
    return paramInt & 0xFFFF;
  }
  public int key2y(int paramInt) {
    return paramInt >> 16 & 0xFFFF;
  }

  public void allValues(List paramList)
  {
    HashMapIntEntry localHashMapIntEntry = this.mapXY.nextEntry(null);
    while (localHashMapIntEntry != null) {
      ArrayList localArrayList = (ArrayList)(ArrayList)localHashMapIntEntry.getValue();
      paramList.add(localArrayList);
      localHashMapIntEntry = this.mapXY.nextEntry(localHashMapIntEntry);
    }
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public boolean containsKey(int paramInt1, int paramInt2, Object paramObject) {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;

    ArrayList localArrayList = (ArrayList)this.mapXY.get(i);
    if (localArrayList == null)
      return false;
    return localArrayList.contains(paramObject);
  }

  public List get(int paramInt1, int paramInt2) {
    return (List)this.mapXY.get(paramInt2 & 0xFFFF | paramInt1 << 16);
  }

  public void put(int paramInt1, int paramInt2, Object paramObject)
  {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;

    ArrayList localArrayList = (ArrayList)this.mapXY.get(i);
    if (localArrayList == null) {
      localArrayList = new ArrayList(this.initialCapacity);
      this.mapXY.put(i, localArrayList);
    }
    localArrayList.add(paramObject);
  }

  public void put(int paramInt1, int paramInt2, Object paramObject, int paramInt3) {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;

    ArrayList localArrayList = (ArrayList)this.mapXY.get(i);
    if (localArrayList == null) {
      localArrayList = new ArrayList(paramInt3);
      this.mapXY.put(i, localArrayList);
    }
    localArrayList.add(paramObject);
  }

  public void allValuesTrimToSize() {
    HashMapIntEntry localHashMapIntEntry = this.mapXY.nextEntry(null);
    while (localHashMapIntEntry != null) {
      ArrayList localArrayList = (ArrayList)(ArrayList)localHashMapIntEntry.getValue();
      localArrayList.trimToSize();
      localHashMapIntEntry = this.mapXY.nextEntry(localHashMapIntEntry);
    }
  }

  public void valueTrimToSize(int paramInt1, int paramInt2) {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;

    ArrayList localArrayList = (ArrayList)this.mapXY.get(i);
    if (localArrayList != null)
      localArrayList.trimToSize();
  }

  public void clear() {
    HashMapIntEntry localHashMapIntEntry = this.mapXY.nextEntry(null);
    while (localHashMapIntEntry != null) {
      ArrayList localArrayList = (ArrayList)(ArrayList)localHashMapIntEntry.getValue();
      localArrayList.clear();
      localHashMapIntEntry = this.mapXY.nextEntry(localHashMapIntEntry);
    }
    this.mapXY.clear();
  }

  public void remove(int paramInt1, int paramInt2, Object paramObject) {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;

    ArrayList localArrayList = (ArrayList)this.mapXY.get(i);
    if (localArrayList != null)
    {
      int j = localArrayList.indexOf(paramObject);
      if (j >= 0) {
        localArrayList.remove(j);
      }
      if ((i != 0) && (localArrayList.isEmpty()))
        this.mapXY.remove(i);
    }
  }

  public void remove(int paramInt1, int paramInt2) {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;

    ArrayList localArrayList = (ArrayList)this.mapXY.get(i);
    if ((localArrayList != null) && (i != 0) && (localArrayList.isEmpty()))
      this.mapXY.remove(i);
  }

  public boolean equals(Object paramObject)
  {
    return this == paramObject;
  }
}