package com.maddox.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class HashMapInt
  implements Cloneable, Serializable
{
  private transient Entry[] table;
  private transient int count;
  private int threshold;
  private float loadFactor;
  private transient int modCount = 0;
  private transient int nextEntryLock;
  private transient Set entrySet = null;
  private transient Collection values = null;

  private Entry freeEntryList = null;
  private static final int VALUES = 1;
  private static final int ENTRIES = 2;

  public HashMapInt(int paramInt, float paramFloat)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Illegal Initial Capacity: " + paramInt);
    }
    if (paramFloat <= 0.0F) {
      throw new IllegalArgumentException("Illegal Load factor: " + paramFloat);
    }
    if (paramInt == 0)
      paramInt = 1;
    this.loadFactor = paramFloat;
    this.table = new Entry[paramInt];
    this.threshold = (int)(paramInt * paramFloat);
  }

  public HashMapInt(int paramInt)
  {
    this(paramInt, 0.75F);
  }

  public HashMapInt()
  {
    this(101, 0.75F);
  }

  public HashMapInt(HashMapInt paramHashMapInt)
  {
    this(Math.max(2 * paramHashMapInt.size(), 11), 0.75F);
    putAll(paramHashMapInt);
  }

  public int size()
  {
    return this.count;
  }

  public boolean isEmpty()
  {
    return this.count == 0;
  }

  public boolean equals(Object paramObject)
  {
    return this == paramObject;
  }

  public HashMapIntEntry nextEntry(HashMapIntEntry paramHashMapIntEntry)
  {
    if (paramHashMapIntEntry == null) {
      this.nextEntryLock = this.modCount;
    }
    else if (this.modCount != this.nextEntryLock) {
      throw new ConcurrentModificationException();
    }
    Entry localEntry = (Entry)paramHashMapIntEntry;
    int i = 0;
    if (localEntry != null) {
      if (localEntry.next != null)
        return localEntry.next;
      i = (localEntry.getKey() & 0x7FFFFFFF) % this.table.length - 1;
    } else {
      i = this.table.length - 1;
    }

    while (i >= 0) {
      if (this.table[i] != null)
        return this.table[i];
      i--;
    }
    return null;
  }

  public HashMapIntEntry getEntry(int paramInt)
  {
    Entry[] arrayOfEntry = this.table;

    int i = paramInt;
    int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
    for (Entry localEntry = arrayOfEntry[j]; localEntry != null; localEntry = localEntry.next) {
      if (localEntry.key == paramInt)
        return localEntry;
    }
    return null;
  }

  public boolean containsValue(Object paramObject)
  {
    Entry[] arrayOfEntry = this.table;
    int i;
    Entry localEntry;
    if (paramObject == null)
      for (i = arrayOfEntry.length; i-- > 0; )
        for (localEntry = arrayOfEntry[i]; localEntry != null; localEntry = localEntry.next)
          if (localEntry.value == null)
            return true;
    else {
      for (i = arrayOfEntry.length; i-- > 0; ) {
        for (localEntry = arrayOfEntry[i]; localEntry != null; localEntry = localEntry.next)
          if (paramObject.equals(localEntry.value))
            return true;
      }
    }
    return false;
  }

  public boolean containsKey(int paramInt)
  {
    Entry[] arrayOfEntry = this.table;
    int i = paramInt;
    int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
    for (Entry localEntry = arrayOfEntry[j]; localEntry != null; localEntry = localEntry.next)
      if (localEntry.key == paramInt)
        return true;
    return false;
  }

  public int findNotUsedKey(int paramInt1, int paramInt2, int paramInt3)
  {
    Entry[] arrayOfEntry = this.table;
    int i;
    Entry localEntry3;
    if (paramInt1 < paramInt3) {
      if ((paramInt2 < paramInt1) || (paramInt2 >= paramInt3))
        return paramInt3;
      for (i = paramInt2; i < paramInt3; i++) {
        Entry localEntry1 = arrayOfEntry[((i & 0x7FFFFFFF) % arrayOfEntry.length)];
        for (; localEntry1 != null; localEntry1 = localEntry1.next)
          if (localEntry1.key == i)
            break;
        if (localEntry1 == null)
          return i;
      }
      for (int j = paramInt1; j < paramInt2; j++) {
        localEntry3 = arrayOfEntry[((j & 0x7FFFFFFF) % arrayOfEntry.length)];
        for (; localEntry3 != null; localEntry3 = localEntry3.next)
          if (localEntry3.key == j)
            break;
        if (localEntry3 == null)
          return j;
      }
    } else if (paramInt1 > paramInt3) {
      if ((paramInt2 > paramInt1) || (paramInt2 <= paramInt3))
        return paramInt3;
      for (i = paramInt2; i > paramInt3; i--) {
        Entry localEntry2 = arrayOfEntry[((i & 0x7FFFFFFF) % arrayOfEntry.length)];
        for (; localEntry2 != null; localEntry2 = localEntry2.next)
          if (localEntry2.key == i)
            break;
        if (localEntry2 == null)
          return i;
      }
      for (int k = paramInt1; k > paramInt2; k--) {
        localEntry3 = arrayOfEntry[((k & 0x7FFFFFFF) % arrayOfEntry.length)];
        for (; localEntry3 != null; localEntry3 = localEntry3.next)
          if (localEntry3.key == k)
            break;
        if (localEntry3 == null)
          return k;
      }
    }
    return paramInt3;
  }

  public Object get(int paramInt)
  {
    Entry[] arrayOfEntry = this.table;

    int i = paramInt;
    int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
    for (Entry localEntry = arrayOfEntry[j]; localEntry != null; localEntry = localEntry.next)
      if (localEntry.key == paramInt)
        return localEntry.value;
    return null;
  }

  private void rehash()
  {
    int i = this.table.length;
    Entry[] arrayOfEntry1 = this.table;

    int j = i * 2 + 1;
    Entry[] arrayOfEntry2 = new Entry[j];

    this.modCount += 1;
    this.threshold = (int)(j * this.loadFactor);
    this.table = arrayOfEntry2;

    for (int k = i; k-- > 0; )
      for (Entry localEntry1 = arrayOfEntry1[k]; localEntry1 != null; ) {
        Entry localEntry2 = localEntry1;
        localEntry1 = localEntry1.next;

        int m = (localEntry2.key & 0x7FFFFFFF) % j;
        localEntry2.next = arrayOfEntry2[m];
        arrayOfEntry2[m] = localEntry2;
      }
  }

  public Object put(int paramInt, Object paramObject)
  {
    Entry[] arrayOfEntry = this.table;
    int i = paramInt;
    int j = 0;

    j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
    for (Entry localEntry = arrayOfEntry[j]; localEntry != null; localEntry = localEntry.next) {
      if (localEntry.key == paramInt) {
        localObject = localEntry.value;
        localEntry.value = paramObject;
        return localObject;
      }
    }

    this.modCount += 1;
    if (this.count >= this.threshold)
    {
      rehash();

      arrayOfEntry = this.table;
      j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
    }

    Object localObject = allocEntry(paramInt, paramObject, arrayOfEntry[j]);
    arrayOfEntry[j] = localObject;
    this.count += 1;
    return null;
  }

  public Object remove(int paramInt)
  {
    Entry[] arrayOfEntry = this.table;

    int i = paramInt;
    int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;

    Entry localEntry1 = arrayOfEntry[j]; for (Entry localEntry2 = null; localEntry1 != null; )
    {
      if (localEntry1.key == paramInt) {
        this.modCount += 1;
        if (localEntry2 != null)
          localEntry2.next = localEntry1.next;
        else {
          arrayOfEntry[j] = localEntry1.next;
        }
        this.count -= 1;
        Object localObject = localEntry1.value;
        freeEntry(localEntry1);
        return localObject;
      }
      localEntry2 = localEntry1; localEntry1 = localEntry1.next;
    }

    return null;
  }

  public void putAll(HashMapInt paramHashMapInt)
  {
    HashMapIntEntry localHashMapIntEntry = paramHashMapInt.nextEntry(null);
    while (localHashMapIntEntry != null) {
      put(localHashMapIntEntry.getKey(), localHashMapIntEntry.getValue());
      localHashMapIntEntry = paramHashMapInt.nextEntry(localHashMapIntEntry);
    }
  }

  public void clear()
  {
    Entry[] arrayOfEntry = this.table;
    this.modCount += 1;
    int i = arrayOfEntry.length;
    do { Object localObject = arrayOfEntry[i];
      while (localObject != null) {
        Entry localEntry = ((Entry)localObject).next;
        freeEntry((Entry)localObject);
        localObject = localEntry;
      }
      arrayOfEntry[i] = null;

      i--; } while (i >= 0);

    this.count = 0;
  }

  public Object clone()
  {
    try
    {
      HashMapInt localHashMapInt = (HashMapInt)super.clone();
      localHashMapInt.table = new Entry[this.table.length];
      for (int i = this.table.length; i-- > 0; ) {
        localHashMapInt.table[i] = (this.table[i] != null ? (Entry)this.table[i].clone() : null);
      }

      localHashMapInt.entrySet = null;
      localHashMapInt.values = null;
      localHashMapInt.modCount = 0;
      return localHashMapInt;
    } catch (CloneNotSupportedException localCloneNotSupportedException) {
    }
    throw new InternalError();
  }

  public Collection values()
  {
    if (this.values == null)
      this.values = new AbstractCollection() {
        public Iterator iterator() {
          return new HashMapInt.HashIterator(HashMapInt.this, 1);
        }
        public int size() {
          return HashMapInt.this.count;
        }
        public boolean contains(Object paramObject) {
          return HashMapInt.this.containsValue(paramObject);
        }
        public void clear() {
          HashMapInt.this.clear();
        }
      };
    return this.values;
  }

  public Set entrySet()
  {
    if (this.entrySet == null) {
      this.entrySet = new AbstractSet() {
        public Iterator iterator() {
          return new HashMapInt.HashIterator(HashMapInt.this, 2);
        }

        public boolean contains(Object paramObject) {
          if (!(paramObject instanceof HashMapIntEntry))
            return false;
          HashMapIntEntry localHashMapIntEntry = (HashMapIntEntry)paramObject;
          int i = localHashMapIntEntry.getKey();
          HashMapInt.Entry[] arrayOfEntry = HashMapInt.this.table;
          int j = i;
          int k = (j & 0x7FFFFFFF) % arrayOfEntry.length;

          for (HashMapInt.Entry localEntry = arrayOfEntry[k]; localEntry != null; localEntry = localEntry.next)
            if ((localEntry.key == i) && (localEntry.equals(localHashMapIntEntry)))
              return true;
          return false;
        }

        public boolean remove(Object paramObject) {
          if (!(paramObject instanceof HashMapIntEntry))
            return false;
          HashMapIntEntry localHashMapIntEntry = (HashMapIntEntry)paramObject;
          int i = localHashMapIntEntry.getKey();
          HashMapInt.Entry[] arrayOfEntry = HashMapInt.this.table;
          int j = i;
          int k = (j & 0x7FFFFFFF) % arrayOfEntry.length;

          HashMapInt.Entry localEntry1 = arrayOfEntry[k]; for (HashMapInt.Entry localEntry2 = null; localEntry1 != null; )
          {
            if ((localEntry1.key == i) && (localEntry1.equals(localHashMapIntEntry))) {
              HashMapInt.access$208(HashMapInt.this);
              if (localEntry2 != null)
                localEntry2.next = localEntry1.next;
              else {
                arrayOfEntry[k] = localEntry1.next;
              }
              HashMapInt.access$010(HashMapInt.this);
              HashMapInt.this.freeEntry(localEntry1);
              return true;
            }
            localEntry2 = localEntry1; localEntry1 = localEntry1.next;
          }

          return false;
        }

        public int size() {
          return HashMapInt.this.count;
        }

        public void clear() {
          HashMapInt.this.clear();
        }
      };
    }
    return this.entrySet;
  }

  private Entry allocEntry(int paramInt, Object paramObject, Entry paramEntry)
  {
    if (this.freeEntryList == null)
      return new Entry(paramInt, paramObject, paramEntry);
    Entry localEntry = this.freeEntryList;
    this.freeEntryList = this.freeEntryList.next;
    localEntry.key = paramInt;
    localEntry.value = paramObject;
    localEntry.next = paramEntry;
    return localEntry;
  }

  private void freeEntry(Entry paramEntry) {
    paramEntry.value = null;
    paramEntry.next = this.freeEntryList;
    this.freeEntryList = paramEntry;
  }

  public void freeCachedMem() {
    this.freeEntryList = null;
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();

    paramObjectOutputStream.writeInt(this.table.length);

    paramObjectOutputStream.writeInt(this.count);

    for (int i = this.table.length - 1; i >= 0; i--) {
      Entry localEntry = this.table[i];

      while (localEntry != null) {
        paramObjectOutputStream.writeInt(localEntry.key);
        paramObjectOutputStream.writeObject(localEntry.value);
        localEntry = localEntry.next;
      }
    }
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();

    int i = paramObjectInputStream.readInt();
    this.table = new Entry[i];

    int j = paramObjectInputStream.readInt();

    for (int k = 0; k < j; k++) {
      int m = paramObjectInputStream.readInt();
      Object localObject = paramObjectInputStream.readObject();
      put(m, localObject);
    }
  }

  int capacity() {
    return this.table.length;
  }

  float loadFactor() {
    return this.loadFactor;
  }

  private class HashIterator
    implements Iterator
  {
    HashMapInt.Entry[] table = HashMapInt.this.table;
    int index = this.table.length;
    HashMapInt.Entry entry = null;
    HashMapInt.Entry lastReturned = null;
    int type;
    private int expectedModCount = HashMapInt.this.modCount;

    HashIterator(int arg2)
    {
      int i;
      this.type = i;
    }

    public boolean hasNext() {
      while ((this.entry == null) && (this.index > 0)) {
        this.entry = this.table[(--this.index)];
      }
      return this.entry != null;
    }

    public Object next() {
      if (HashMapInt.this.modCount != this.expectedModCount) {
        throw new ConcurrentModificationException();
      }
      do
        this.entry = this.table[(--this.index)];
      while ((this.entry == null) && (this.index > 0));

      if (this.entry != null) {
        HashMapInt.Entry localEntry = this.lastReturned = this.entry;
        this.entry = localEntry.next;
        return this.type == 1 ? localEntry.value : localEntry;
      }
      throw new NoSuchElementException();
    }

    public void remove() {
      if (this.lastReturned == null)
        throw new IllegalStateException();
      if (HashMapInt.this.modCount != this.expectedModCount) {
        throw new ConcurrentModificationException();
      }
      HashMapInt.Entry[] arrayOfEntry = HashMapInt.this.table;
      int i = (this.lastReturned.key & 0x7FFFFFFF) % arrayOfEntry.length;

      HashMapInt.Entry localEntry1 = arrayOfEntry[i]; for (HashMapInt.Entry localEntry2 = null; localEntry1 != null; )
      {
        if (localEntry1 == this.lastReturned) {
          HashMapInt.access$208(HashMapInt.this);
          this.expectedModCount += 1;
          if (localEntry2 == null)
            arrayOfEntry[i] = localEntry1.next;
          else
            localEntry2.next = localEntry1.next;
          HashMapInt.access$010(HashMapInt.this);
          this.lastReturned = null;
          HashMapInt.this.freeEntry(localEntry1);
          return;
        }
        localEntry2 = localEntry1; localEntry1 = localEntry1.next;
      }

      throw new ConcurrentModificationException();
    }
  }

  private static class Entry
    implements HashMapIntEntry
  {
    int key;
    Object value;
    Entry next;

    Entry(int paramInt, Object paramObject, Entry paramEntry)
    {
      this.key = paramInt;
      this.value = paramObject;
      this.next = paramEntry;
    }

    protected Object clone() {
      return new Entry(this.key, this.value, this.next == null ? null : (Entry)this.next.clone());
    }

    public int getKey()
    {
      return this.key;
    }

    public Object getValue() {
      return this.value;
    }

    public Object setValue(Object paramObject) {
      Object localObject = this.value;
      this.value = paramObject;
      return localObject;
    }

    public boolean equals(Object paramObject) {
      if (!(paramObject instanceof HashMapIntEntry))
        return false;
      HashMapIntEntry localHashMapIntEntry = (HashMapIntEntry)paramObject;

      return (this.key == localHashMapIntEntry.getKey()) && (this.value == null ? localHashMapIntEntry.getValue() == null : this.value.equals(localHashMapIntEntry.getValue()));
    }

    public int hashCode()
    {
      return this.key ^ (this.value == null ? 0 : this.value.hashCode());
    }

    public String toString() {
      return this.key + "=" + this.value.toString();
    }
  }
}