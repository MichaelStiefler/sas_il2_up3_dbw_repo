package com.maddox.util;

import java.util.List;

public class HashMapXYHash
{
  private transient HashMapInt mapY;
  private int initialCapacity = 101;
  private float loadFactor = 0.75F;

  public HashMapXYHash() {
    this.mapY = new HashMapInt(this.initialCapacity, this.loadFactor);
  }

  public HashMapXYHash(int paramInt) {
    this.initialCapacity = paramInt;
    this.mapY = new HashMapInt(paramInt, this.loadFactor);
  }

  public HashMapXYHash(int paramInt, float paramFloat) {
    this.initialCapacity = paramInt;
    this.loadFactor = paramFloat;
    this.mapY = new HashMapInt(paramInt, paramFloat);
  }

  public int capacity() {
    int i = 0;
    HashMapIntEntry localHashMapIntEntry1 = this.mapY.nextEntry(null);
    while (localHashMapIntEntry1 != null) {
      HashMapInt localHashMapInt = (HashMapInt)localHashMapIntEntry1.getValue();
      HashMapIntEntry localHashMapIntEntry2 = localHashMapInt.nextEntry(null);
      while (localHashMapIntEntry2 != null) {
        HashMapExt localHashMapExt = (HashMapExt)localHashMapIntEntry2.getValue();
        i += localHashMapExt.capacity();
        localHashMapIntEntry2 = localHashMapInt.nextEntry(localHashMapIntEntry2);
      }
      localHashMapIntEntry1 = this.mapY.nextEntry(localHashMapIntEntry1);
    }
    return i;
  }

  public int size() {
    int i = 0;
    HashMapIntEntry localHashMapIntEntry1 = this.mapY.nextEntry(null);
    while (localHashMapIntEntry1 != null) {
      HashMapInt localHashMapInt = (HashMapInt)localHashMapIntEntry1.getValue();
      HashMapIntEntry localHashMapIntEntry2 = localHashMapInt.nextEntry(null);
      while (localHashMapIntEntry2 != null) {
        HashMapExt localHashMapExt = (HashMapExt)localHashMapIntEntry2.getValue();
        i += localHashMapExt.size();
        localHashMapIntEntry2 = localHashMapInt.nextEntry(localHashMapIntEntry2);
      }
      localHashMapIntEntry1 = this.mapY.nextEntry(localHashMapIntEntry1);
    }
    return i;
  }

  public void allValues(List paramList) {
    HashMapIntEntry localHashMapIntEntry1 = this.mapY.nextEntry(null);
    while (localHashMapIntEntry1 != null) {
      HashMapInt localHashMapInt = (HashMapInt)localHashMapIntEntry1.getValue();
      HashMapIntEntry localHashMapIntEntry2 = localHashMapInt.nextEntry(null);
      while (localHashMapIntEntry2 != null) {
        HashMapExt localHashMapExt = (HashMapExt)localHashMapIntEntry2.getValue();
        paramList.add(localHashMapExt);
        localHashMapIntEntry2 = localHashMapInt.nextEntry(localHashMapIntEntry2);
      }
      localHashMapIntEntry1 = this.mapY.nextEntry(localHashMapIntEntry1);
    }
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public HashMapExt get(int paramInt1, int paramInt2) {
    HashMapInt localHashMapInt = (HashMapInt)this.mapY.get(paramInt1);
    if (localHashMapInt != null)
      return (HashMapExt)localHashMapInt.get(paramInt2);
    return null;
  }

  public boolean containsKey(int paramInt1, int paramInt2, Object paramObject) {
    HashMapInt localHashMapInt = (HashMapInt)this.mapY.get(paramInt1);
    if (localHashMapInt == null)
      return false;
    HashMapExt localHashMapExt = (HashMapExt)localHashMapInt.get(paramInt2);
    if (localHashMapExt == null)
      return false;
    return localHashMapExt.containsKey(paramObject);
  }

  public Object put(int paramInt1, int paramInt2, Object paramObject1, Object paramObject2) {
    HashMapInt localHashMapInt = (HashMapInt)this.mapY.get(paramInt1);
    if (localHashMapInt == null) {
      localHashMapInt = new HashMapInt(this.initialCapacity, this.loadFactor);
      this.mapY.put(paramInt1, localHashMapInt);
    }
    HashMapExt localHashMapExt = (HashMapExt)localHashMapInt.get(paramInt2);
    if (localHashMapExt == null) {
      localHashMapExt = new HashMapExt(this.initialCapacity, this.loadFactor);
      localHashMapInt.put(paramInt2, localHashMapExt);
    }
    return localHashMapExt.put(paramObject1, paramObject2);
  }

  public void clear() {
    HashMapIntEntry localHashMapIntEntry1 = this.mapY.nextEntry(null);
    while (localHashMapIntEntry1 != null) {
      HashMapInt localHashMapInt = (HashMapInt)localHashMapIntEntry1.getValue();
      HashMapIntEntry localHashMapIntEntry2 = localHashMapInt.nextEntry(null);
      while (localHashMapIntEntry2 != null) {
        HashMapExt localHashMapExt = (HashMapExt)localHashMapIntEntry2.getValue();
        localHashMapExt.clear();
        localHashMapIntEntry2 = localHashMapInt.nextEntry(localHashMapIntEntry2);
      }
      localHashMapInt.clear();
      localHashMapIntEntry1 = this.mapY.nextEntry(localHashMapIntEntry1);
    }
    this.mapY.clear();
  }

  public Object remove(int paramInt1, int paramInt2, Object paramObject) {
    HashMapInt localHashMapInt = (HashMapInt)this.mapY.get(paramInt1);
    if (localHashMapInt != null) {
      HashMapExt localHashMapExt = (HashMapExt)localHashMapInt.get(paramInt2);
      if (localHashMapExt != null) {
        Object localObject = localHashMapExt.remove(paramObject);
        if (((paramInt2 | paramInt1) != 0) && (localHashMapExt.isEmpty()))
          localHashMapInt.remove(paramInt2);
        return localObject;
      }
    }
    return null;
  }

  public boolean equals(Object paramObject)
  {
    return this == paramObject;
  }
}