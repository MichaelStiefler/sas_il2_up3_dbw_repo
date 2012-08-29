package com.maddox.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class HashMapXY
  implements Cloneable, Serializable
{
  private transient HashMapInt mapY;
  private int initialCapacity = 101;
  private float loadFactor = 0.75F;

  public HashMapXY() {
    this.mapY = new HashMapInt(this.initialCapacity, this.loadFactor);
  }

  public HashMapXY(int paramInt) {
    this.initialCapacity = paramInt;
    this.mapY = new HashMapInt(paramInt, this.loadFactor);
  }

  public HashMapXY(int paramInt, float paramFloat) {
    this.initialCapacity = paramInt;
    this.loadFactor = paramFloat;
    this.mapY = new HashMapInt(paramInt, paramFloat);
  }

  public int size() {
    int i = 0;
    HashMapIntEntry localHashMapIntEntry = this.mapY.nextEntry(null);
    while (localHashMapIntEntry != null) {
      i += ((HashMapInt)(HashMapInt)localHashMapIntEntry.getValue()).size();
      localHashMapIntEntry = this.mapY.nextEntry(localHashMapIntEntry);
    }
    return i;
  }

  public int sizeY() {
    return this.mapY.size();
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public boolean isEmptyY() {
    return sizeY() == 0;
  }

  public HashMapInt mapY() {
    return this.mapY;
  }

  public HashMapInt get(int paramInt) {
    return (HashMapInt)this.mapY.get(paramInt);
  }

  public Object get(int paramInt1, int paramInt2) {
    HashMapInt localHashMapInt = (HashMapInt)this.mapY.get(paramInt1);
    if (localHashMapInt != null)
      return localHashMapInt.get(paramInt2);
    return null;
  }

  public boolean containsKey(int paramInt) {
    return this.mapY.containsKey(paramInt);
  }

  public boolean containsKey(int paramInt1, int paramInt2) {
    HashMapInt localHashMapInt = (HashMapInt)this.mapY.get(paramInt1);
    if (localHashMapInt != null)
      return localHashMapInt.containsKey(paramInt2);
    return false;
  }

  public boolean containsValue(Object paramObject) {
    HashMapIntEntry localHashMapIntEntry = this.mapY.nextEntry(null);
    while (localHashMapIntEntry != null) {
      if (((HashMapInt)(HashMapInt)localHashMapIntEntry.getValue()).containsValue(paramObject))
        return true;
      localHashMapIntEntry = this.mapY.nextEntry(localHashMapIntEntry);
    }
    return false;
  }

  public boolean containsValue(int paramInt, Object paramObject) {
    HashMapInt localHashMapInt = (HashMapInt)this.mapY.get(paramInt);
    if (localHashMapInt != null)
      return localHashMapInt.containsValue(paramObject);
    return false;
  }

  public Object put(int paramInt1, int paramInt2, Object paramObject) {
    HashMapInt localHashMapInt = (HashMapInt)this.mapY.get(paramInt1);
    if (localHashMapInt == null) {
      localHashMapInt = new HashMapInt(this.initialCapacity, this.loadFactor);
      this.mapY.put(paramInt1, localHashMapInt);
    }
    return localHashMapInt.put(paramInt2, paramObject);
  }

  public void clear() {
    HashMapIntEntry localHashMapIntEntry = this.mapY.nextEntry(null);
    while (localHashMapIntEntry != null) {
      ((HashMapInt)(HashMapInt)localHashMapIntEntry.getValue()).clear();
      localHashMapIntEntry = this.mapY.nextEntry(localHashMapIntEntry);
    }
    this.mapY.clear();
  }

  public void clear(int paramInt) {
    HashMapInt localHashMapInt = (HashMapInt)this.mapY.get(paramInt);
    if (localHashMapInt != null) {
      localHashMapInt.clear();
      this.mapY.remove(paramInt);
    }
  }

  public Object remove(int paramInt1, int paramInt2) {
    HashMapInt localHashMapInt = (HashMapInt)this.mapY.get(paramInt1);

    if (localHashMapInt != null) {
      Object localObject = localHashMapInt.remove(paramInt2);
      if (localHashMapInt.isEmpty())
        this.mapY.remove(paramInt1);
      return localObject;
    }
    return null;
  }

  public boolean equals(Object paramObject)
  {
    return this == paramObject;
  }

  public Object clone()
  {
    try
    {
      HashMapXY localHashMapXY = (HashMapXY)super.clone();
      localHashMapXY.mapY = new HashMapInt(this.initialCapacity, this.loadFactor);
      HashMapIntEntry localHashMapIntEntry = this.mapY.nextEntry(null);
      while (localHashMapIntEntry != null) {
        int i = localHashMapIntEntry.getKey();
        HashMapInt localHashMapInt = (HashMapInt)localHashMapIntEntry.getValue();
        localHashMapInt = (HashMapInt)localHashMapInt.clone();
        localHashMapXY.mapY.put(i, localHashMapInt);
        localHashMapIntEntry = this.mapY.nextEntry(localHashMapIntEntry);
      }
      return localHashMapXY;
    } catch (CloneNotSupportedException localCloneNotSupportedException) {
    }
    throw new InternalError();
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
    paramObjectOutputStream.writeObject(this.mapY);
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    this.mapY = ((HashMapInt)paramObjectInputStream.readObject());
  }
}