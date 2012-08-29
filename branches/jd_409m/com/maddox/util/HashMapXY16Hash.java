package com.maddox.util;

import java.util.List;

public class HashMapXY16Hash
{
  private transient HashMapInt mapXY;
  private int initialCapacity = 101;
  private float loadFactor = 0.75F;

  public HashMapXY16Hash() {
    this.mapXY = new HashMapInt(this.initialCapacity, this.loadFactor);
  }

  public HashMapXY16Hash(int paramInt) {
    this.initialCapacity = paramInt;
    this.mapXY = new HashMapInt(paramInt, this.loadFactor);
  }

  public HashMapXY16Hash(int paramInt, float paramFloat) {
    this.initialCapacity = paramInt;
    this.loadFactor = paramFloat;
    this.mapXY = new HashMapInt(paramInt, paramFloat);
  }

  public int capacity() {
    int i = 0;
    HashMapIntEntry localHashMapIntEntry = this.mapXY.nextEntry(null);
    while (localHashMapIntEntry != null) {
      HashMapExt localHashMapExt = (HashMapExt)localHashMapIntEntry.getValue();
      i += localHashMapExt.capacity();
      localHashMapIntEntry = this.mapXY.nextEntry(localHashMapIntEntry);
    }
    return i;
  }

  public int size() {
    int i = 0;
    HashMapIntEntry localHashMapIntEntry = this.mapXY.nextEntry(null);
    while (localHashMapIntEntry != null) {
      HashMapExt localHashMapExt = (HashMapExt)localHashMapIntEntry.getValue();
      i += localHashMapExt.size();
      localHashMapIntEntry = this.mapXY.nextEntry(localHashMapIntEntry);
    }
    return i;
  }

  public void allValues(List paramList) {
    HashMapIntEntry localHashMapIntEntry = this.mapXY.nextEntry(null);
    while (localHashMapIntEntry != null) {
      HashMapExt localHashMapExt = (HashMapExt)localHashMapIntEntry.getValue();
      paramList.add(localHashMapExt);
      localHashMapIntEntry = this.mapXY.nextEntry(localHashMapIntEntry);
    }
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public boolean containsKey(int paramInt1, int paramInt2, Object paramObject) {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;

    HashMapExt localHashMapExt = (HashMapExt)this.mapXY.get(i);
    if (localHashMapExt == null)
      return false;
    return localHashMapExt.containsKey(paramObject);
  }

  public HashMapExt get(int paramInt1, int paramInt2) {
    return (HashMapExt)this.mapXY.get(paramInt2 & 0xFFFF | paramInt1 << 16);
  }

  public Object put(int paramInt1, int paramInt2, Object paramObject1, Object paramObject2)
  {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;

    HashMapExt localHashMapExt = (HashMapExt)this.mapXY.get(i);
    if (localHashMapExt == null) {
      localHashMapExt = new HashMapExt(this.initialCapacity, this.loadFactor);
      this.mapXY.put(i, localHashMapExt);
    }
    return localHashMapExt.put(paramObject1, paramObject2);
  }

  public void clear() {
    HashMapIntEntry localHashMapIntEntry = this.mapXY.nextEntry(null);
    while (localHashMapIntEntry != null) {
      HashMapExt localHashMapExt = (HashMapExt)localHashMapIntEntry.getValue();
      localHashMapExt.clear();
      localHashMapIntEntry = this.mapXY.nextEntry(localHashMapIntEntry);
    }
    this.mapXY.clear();
  }

  public Object remove(int paramInt1, int paramInt2, Object paramObject) {
    int i = paramInt2 & 0xFFFF | paramInt1 << 16;

    HashMapExt localHashMapExt = (HashMapExt)this.mapXY.get(i);
    if (localHashMapExt != null) {
      Object localObject = localHashMapExt.remove(paramObject);
      if ((i != 0) && (localHashMapExt.isEmpty()))
        this.mapXY.remove(i);
      return localObject;
    }
    return null;
  }

  public boolean equals(Object paramObject)
  {
    return this == paramObject;
  }
}