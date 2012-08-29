package com.maddox.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public class HashMapExt extends AbstractMap
  implements Map, Cloneable, Serializable
{
  private transient Entry[] table;
  private transient int count;
  private int threshold;
  private float loadFactor;
  private transient int modCount = 0;

  private static int _entryHash = 0;
  private transient Entry head;
  private transient Entry tail;
  private transient int nextEntryLock;
  private transient Set keySet = null;
  private transient Set entrySet = null;
  private transient Collection values = null;

  private Entry freeEntryList = null;
  private static final int KEYS = 0;
  private static final int VALUES = 1;
  private static final int ENTRIES = 2;

  public HashMapExt(int paramInt, float paramFloat)
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
    this.head = allocEntry(_entryHash++, null, null, null);
    this.tail = allocEntry(0, null, null, null);
    this.head.lNext = this.tail;
    this.tail.lPrev = this.head;
  }

  public HashMapExt(int paramInt)
  {
    this(paramInt, 0.75F);
  }

  public HashMapExt()
  {
    this(101, 0.75F);
  }

  public HashMapExt(Map paramMap)
  {
    this(Math.max(2 * paramMap.size(), 11), 0.75F);
    putAll(paramMap);
  }

  public int size()
  {
    return this.count;
  }

  public boolean isEmpty()
  {
    return this.count == 0;
  }

  public int hashCode()
  {
    return this.head.hashCode();
  }

  public boolean equals(Object paramObject)
  {
    return this == paramObject;
  }

  public Map.Entry nextEntry(Map.Entry paramEntry)
  {
    if (this.count == 0)
      return null;
    Entry localEntry = (Entry)paramEntry;
    if (localEntry == null) {
      this.nextEntryLock = this.modCount;
      localEntry = this.head.lNext;
    } else {
      if (this.modCount != this.nextEntryLock)
        throw new ConcurrentModificationException();
      localEntry = localEntry.lNext;
    }
    if (localEntry == this.tail) return null;
    return localEntry;
  }

  public Map.Entry getEntry(Object paramObject)
  {
    Object localObject;
    if (paramObject != null) {
      localObject = this.table;
      int i = paramObject.hashCode();
      int j = (i & 0x7FFFFFFF) % localObject.length;
      for (Entry localEntry = localObject[j]; localEntry != null; localEntry = localEntry.next)
        if ((localEntry.hash == i) && (paramObject.equals(localEntry.key)))
          return localEntry;
    } else {
      localObject = this.head.lNext;
      while (localObject != this.tail) {
        if (((Entry)localObject).key == null)
          return localObject;
        localObject = ((Entry)localObject).lNext;
      }
    }

    return (Map.Entry)null;
  }

  public boolean containsValue(Object paramObject)
  {
    Entry localEntry = this.head.lNext;

    if (paramObject == null) {
      while (localEntry != this.tail) {
        if (localEntry.value == null)
          return true;
        localEntry = localEntry.lNext;
      }
    }
    while (localEntry != this.tail) {
      if (paramObject.equals(localEntry.value))
        return true;
      localEntry = localEntry.lNext;
    }

    return false;
  }

  public boolean containsKey(Object paramObject)
  {
    Object localObject;
    if (paramObject != null) {
      localObject = this.table;
      int i = paramObject.hashCode();
      int j = (i & 0x7FFFFFFF) % localObject.length;
      for (Entry localEntry = localObject[j]; localEntry != null; localEntry = localEntry.next)
        if ((localEntry.hash == i) && (paramObject.equals(localEntry.key)))
          return true;
    } else {
      localObject = this.head.lNext;
      while (localObject != this.tail) {
        if (((Entry)localObject).key == null)
          return true;
        localObject = ((Entry)localObject).lNext;
      }
    }

    return false;
  }

  public Object get(Object paramObject)
  {
    Object localObject;
    if (paramObject != null) {
      localObject = this.table;
      int i = paramObject.hashCode();
      int j = (i & 0x7FFFFFFF) % localObject.length;
      for (Entry localEntry = localObject[j]; localEntry != null; localEntry = localEntry.next)
        if ((localEntry.hash == i) && (paramObject.equals(localEntry.key)))
          return localEntry.value;
    } else {
      localObject = this.head.lNext;
      while (localObject != this.tail) {
        if (((Entry)localObject).key == null)
          return ((Entry)localObject).value;
        localObject = ((Entry)localObject).lNext;
      }
    }

    return null;
  }

  private void rehash()
  {
    int i = this.table.length * 2 + 1;
    Entry[] arrayOfEntry = new Entry[i];

    this.modCount += 1;
    this.threshold = (int)(i * this.loadFactor);
    this.table = arrayOfEntry;

    Entry localEntry = this.head.lNext;
    while (localEntry != this.tail) {
      int j = (localEntry.hash & 0x7FFFFFFF) % i;
      localEntry.next = arrayOfEntry[j];
      arrayOfEntry[j] = localEntry;

      localEntry = localEntry.lNext;
    }
  }

  public Object put(Object paramObject1, Object paramObject2)
  {
    Entry[] arrayOfEntry = this.table;
    int i = 0;
    int j = 0;
    Object localObject;
    if (paramObject1 != null) {
      i = paramObject1.hashCode();
      j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
      for (localEntry = arrayOfEntry[j]; localEntry != null; localEntry = localEntry.next)
        if ((localEntry.hash == i) && (paramObject1.equals(localEntry.key))) {
          localObject = localEntry.value;
          localEntry.value = paramObject2;
          return localObject;
        }
    }
    else {
      localEntry = this.head.lNext;
      while (localEntry != this.tail) {
        if (localEntry.key == null) {
          localObject = localEntry.value;
          localEntry.value = paramObject2;
          return localObject;
        }
        localEntry = localEntry.lNext;
      }
    }

    this.modCount += 1;
    if (this.count >= this.threshold)
    {
      rehash();

      arrayOfEntry = this.table;
      j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
    }

    Entry localEntry = allocEntry(i, paramObject1, paramObject2, arrayOfEntry[j]);
    localEntry.lPrev = this.head; localEntry.lNext = this.head.lNext;
    localEntry.lNext.lPrev = localEntry; this.head.lNext = localEntry;
    arrayOfEntry[j] = localEntry;
    this.count += 1;
    return null;
  }

  public Object remove(Object paramObject)
  {
    Entry[] arrayOfEntry = this.table;
    int i;
    int j;
    Object localObject1;
    Object localObject2;
    Entry localEntry1;
    Entry localEntry2;
    if (paramObject != null) {
      i = paramObject.hashCode();
      j = (i & 0x7FFFFFFF) % arrayOfEntry.length;

      localObject1 = arrayOfEntry[j]; for (localObject2 = null; localObject1 != null; )
      {
        if ((((Entry)localObject1).hash == i) && (paramObject.equals(((Entry)localObject1).key))) {
          this.modCount += 1;
          if (localObject2 != null)
            localObject2.next = ((Entry)localObject1).next;
          else {
            arrayOfEntry[j] = ((Entry)localObject1).next;
          }
          this.count -= 1;
          Object localObject3 = ((Entry)localObject1).value;
          freeEntry((Entry)localObject1);
          return localObject3;
        }
        localObject2 = localObject1; localObject1 = ((Entry)localObject1).next;
      }

    }
    else
    {
      localEntry1 = arrayOfEntry[0]; for (localEntry2 = null; localEntry1 != null; )
      {
        if (localEntry1.key == null) {
          this.modCount += 1;
          if (localEntry2 != null)
            localEntry2.next = localEntry1.next;
          else {
            arrayOfEntry[0] = localEntry1.next;
          }
          this.count -= 1;
          localObject1 = localEntry1.value;
          freeEntry(localEntry1);
          return localObject1;
        }
        localEntry2 = localEntry1; localEntry1 = localEntry1.next;
      }

    }

    return null;
  }

  public void putAll(Map paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext()) {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      put(localEntry.getKey(), localEntry.getValue());
    }
  }

  public void clear()
  {
    if (this.count == 0)
      return;
    this.modCount += 1;
    Entry localEntry = this.head.lNext;
    while (localEntry != this.tail) {
      localObject = localEntry;
      localEntry = ((Entry)localObject).lNext;
      freeEntry((Entry)localObject);
    }

    Object localObject = this.table;
    int i = localObject.length;
    while (true) { i--; if (i < 0) break;
      localObject[i] = null; }
    this.count = 0;
  }

  public Object clone()
  {
    try
    {
      HashMapExt localHashMapExt = (HashMapExt)super.clone();
      localHashMapExt.table = new Entry[this.table.length];
      for (int i = this.table.length; i-- > 0; ) {
        Entry localEntry = localHashMapExt.table[i] =  = this.table[i] != null ? (Entry)this.table[i].clone() : null;

        while (localEntry != null) {
          localEntry.lPrev = localHashMapExt.head; localEntry.lNext = localHashMapExt.head.lNext;
          localEntry.lNext.lPrev = localEntry; localHashMapExt.head.lNext = localEntry;
          localEntry = localEntry.next;
        }
      }
      localHashMapExt.keySet = null;
      localHashMapExt.entrySet = null;
      localHashMapExt.values = null;
      localHashMapExt.modCount = 0;
      return localHashMapExt;
    } catch (CloneNotSupportedException localCloneNotSupportedException) {
    }
    throw new InternalError();
  }

  public Set keySet()
  {
    if (this.keySet == null)
      this.keySet = new AbstractSet() {
        public Iterator iterator() {
          return new HashMapExt.HashIterator(HashMapExt.this, 0);
        }
        public int size() {
          return HashMapExt.this.count;
        }
        public boolean contains(Object paramObject) {
          return HashMapExt.this.containsKey(paramObject);
        }
        public boolean remove(Object paramObject) {
          return HashMapExt.this.remove(paramObject) != null;
        }
        public void clear() {
          HashMapExt.this.clear();
        }
      };
    return this.keySet;
  }

  public Collection values()
  {
    if (this.values == null)
      this.values = new AbstractCollection() {
        public Iterator iterator() {
          return new HashMapExt.HashIterator(HashMapExt.this, 1);
        }
        public int size() {
          return HashMapExt.this.count;
        }
        public boolean contains(Object paramObject) {
          return HashMapExt.this.containsValue(paramObject);
        }
        public void clear() {
          HashMapExt.this.clear();
        }
      };
    return this.values;
  }

  public Set entrySet()
  {
    if (this.entrySet == null) {
      this.entrySet = new AbstractSet() {
        public Iterator iterator() {
          return new HashMapExt.HashIterator(HashMapExt.this, 2);
        }

        public boolean contains(Object paramObject) {
          if (!(paramObject instanceof Map.Entry))
            return false;
          Map.Entry localEntry = (Map.Entry)paramObject;
          Object localObject = localEntry.getKey();
          HashMapExt.Entry[] arrayOfEntry = HashMapExt.this.table;
          int i = localObject == null ? 0 : localObject.hashCode();
          int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;

          for (HashMapExt.Entry localEntry1 = arrayOfEntry[j]; localEntry1 != null; localEntry1 = localEntry1.next)
            if ((localEntry1.hash == i) && (localEntry1.equals(localEntry)))
              return true;
          return false;
        }

        public boolean remove(Object paramObject) {
          if (!(paramObject instanceof Map.Entry))
            return false;
          Map.Entry localEntry = (Map.Entry)paramObject;
          Object localObject = localEntry.getKey();
          HashMapExt.Entry[] arrayOfEntry = HashMapExt.this.table;
          int i = localObject == null ? 0 : localObject.hashCode();
          int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;

          HashMapExt.Entry localEntry1 = arrayOfEntry[j]; for (HashMapExt.Entry localEntry2 = null; localEntry1 != null; )
          {
            if ((localEntry1.hash == i) && (localEntry1.equals(localEntry))) {
              HashMapExt.access$208(HashMapExt.this);
              if (localEntry2 != null)
                localEntry2.next = localEntry1.next;
              else {
                arrayOfEntry[j] = localEntry1.next;
              }
              HashMapExt.access$010(HashMapExt.this);
              HashMapExt.this.freeEntry(localEntry1);
              return true;
            }
            localEntry2 = localEntry1; localEntry1 = localEntry1.next;
          }

          return false;
        }

        public int size() {
          return HashMapExt.this.count;
        }

        public void clear() {
          HashMapExt.this.clear();
        }
      };
    }
    return this.entrySet;
  }

  private Entry allocEntry(int paramInt, Object paramObject1, Object paramObject2, Entry paramEntry)
  {
    if (this.freeEntryList == null)
      return new Entry(paramInt, paramObject1, paramObject2, paramEntry);
    Entry localEntry = this.freeEntryList;
    this.freeEntryList = this.freeEntryList.next;
    localEntry.hash = paramInt;
    localEntry.key = paramObject1;
    localEntry.value = paramObject2;
    localEntry.next = paramEntry;
    return localEntry;
  }

  private void freeEntry(Entry paramEntry) {
    paramEntry.key = null;
    paramEntry.value = null;
    paramEntry.next = this.freeEntryList;
    this.freeEntryList = paramEntry;
    paramEntry.lPrev.lNext = paramEntry.lNext;
    paramEntry.lNext.lPrev = paramEntry.lPrev;
    paramEntry.lPrev = null;
    paramEntry.lNext = null;
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
        paramObjectOutputStream.writeObject(localEntry.key);
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
      Object localObject1 = paramObjectInputStream.readObject();
      Object localObject2 = paramObjectInputStream.readObject();
      put(localObject1, localObject2);
    }
  }

  public int capacity() {
    return this.table.length;
  }

  public float loadFactor() {
    return this.loadFactor;
  }

  private class HashIterator
    implements Iterator
  {
    HashMapExt.Entry entry = HashMapExt.this.head;
    HashMapExt.Entry lastReturned = null;
    int type;
    private int expectedModCount = HashMapExt.this.modCount;

    HashIterator(int arg2)
    {
      int i;
      this.type = i;
    }

    public boolean hasNext() {
      return this.entry.lNext != HashMapExt.this.tail;
    }

    public Object next() {
      if (HashMapExt.this.modCount != this.expectedModCount)
        throw new ConcurrentModificationException();
      if (this.entry.lNext == HashMapExt.this.tail)
        throw new NoSuchElementException();
      this.lastReturned = (this.entry = this.entry.lNext);
      return this.type == 1 ? this.entry.value : this.type == 0 ? this.entry.key : this.entry;
    }

    public void remove() {
      if (this.lastReturned == null)
        throw new IllegalStateException();
      if (HashMapExt.this.modCount != this.expectedModCount) {
        throw new ConcurrentModificationException();
      }
      HashMapExt.Entry[] arrayOfEntry = HashMapExt.this.table;
      int i = (this.lastReturned.hash & 0x7FFFFFFF) % arrayOfEntry.length;

      HashMapExt.Entry localEntry1 = arrayOfEntry[i]; for (HashMapExt.Entry localEntry2 = null; localEntry1 != null; )
      {
        if (localEntry1 == this.lastReturned) {
          HashMapExt.access$208(HashMapExt.this);
          this.expectedModCount += 1;
          if (localEntry2 == null)
            arrayOfEntry[i] = localEntry1.next;
          else
            localEntry2.next = localEntry1.next;
          HashMapExt.access$010(HashMapExt.this);
          this.lastReturned = null;
          this.entry = localEntry1.lPrev;
          HashMapExt.this.freeEntry(localEntry1);
          return;
        }
        localEntry2 = localEntry1; localEntry1 = localEntry1.next;
      }

      throw new ConcurrentModificationException();
    }
  }

  private static class Entry
    implements Map.Entry
  {
    int hash;
    Object key;
    Object value;
    Entry next;
    Entry lNext;
    Entry lPrev;

    Entry(int paramInt, Object paramObject1, Object paramObject2, Entry paramEntry)
    {
      this.hash = paramInt;
      this.key = paramObject1;
      this.value = paramObject2;
      this.next = paramEntry;
    }

    protected Object clone() {
      return new Entry(this.hash, this.key, this.value, this.next == null ? null : (Entry)this.next.clone());
    }

    public Object getKey()
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
      if (!(paramObject instanceof Map.Entry))
        return false;
      Map.Entry localEntry = (Map.Entry)paramObject;

      return (this.key == null ? localEntry.getKey() == null : this.key.equals(localEntry.getKey())) && (this.value == null ? localEntry.getValue() == null : this.value.equals(localEntry.getValue()));
    }

    public int hashCode()
    {
      return this.hash ^ (this.value == null ? 0 : this.value.hashCode());
    }

    public String toString() {
      return this.key.toString() + "=" + this.value.toString();
    }
  }
}