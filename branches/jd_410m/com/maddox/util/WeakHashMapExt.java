package com.maddox.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
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

public class WeakHashMapExt extends AbstractMap
  implements Map, Cloneable, Serializable
{
  private transient Entry[] table;
  private transient int count;
  private int threshold;
  private float loadFactor;
  private transient int modCount = 0;
  private transient int nextEntryLock;
  private transient Set keySet = null;
  private transient Set entrySet = null;
  private transient Collection values = null;

  private ReferenceQueue queue = new ReferenceQueue();
  private static final int KEYS = 0;
  private static final int VALUES = 1;
  private static final int ENTRIES = 2;

  public WeakHashMapExt(int paramInt, float paramFloat)
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

  public WeakHashMapExt(int paramInt)
  {
    this(paramInt, 0.75F);
  }

  public WeakHashMapExt()
  {
    this(101, 0.75F);
  }

  public WeakHashMapExt(Map paramMap)
  {
    this(Math.max(2 * paramMap.size(), 11), 0.75F);
    putAll(paramMap);
  }

  public int size()
  {
    processQueue();
    return this.count;
  }

  public boolean isEmpty()
  {
    processQueue();
    return this.count == 0;
  }

  public int hashCode()
  {
    return hashCode();
  }

  public boolean equals(Object paramObject)
  {
    return this == paramObject;
  }

  public Map.Entry nextEntry(Map.Entry paramEntry)
  {
    if (paramEntry == null) {
      this.nextEntryLock = this.modCount;
    }
    else if (this.modCount != this.nextEntryLock) {
      throw new ConcurrentModificationException();
    }
    Entry localEntry = (Entry)paramEntry;
    int i = 0;
    if (localEntry != null) {
      if (localEntry.nextEntry != null)
        return localEntry.nextEntry;
      Object localObject = localEntry.getKey();
      if (localObject != null)
        i = (localObject.hashCode() & 0x7FFFFFFF) % this.table.length - 1;
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

  public Map.Entry getEntry(Object paramObject)
  {
    Entry[] arrayOfEntry = this.table;

    if (paramObject != null) {
      int i = paramObject.hashCode();
      int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
      for (Entry localEntry2 = arrayOfEntry[j]; localEntry2 != null; localEntry2 = localEntry2.nextEntry)
        if ((localEntry2.hash == i) && (paramObject.equals(localEntry2.getKey())))
          return localEntry2;
    } else {
      for (Entry localEntry1 = arrayOfEntry[0]; localEntry1 != null; localEntry1 = localEntry1.nextEntry) {
        if (localEntry1.getKey() == null)
          return localEntry1;
      }
    }
    return null;
  }

  public boolean containsValue(Object paramObject)
  {
    processQueue();
    Entry[] arrayOfEntry = this.table;
    int i;
    Entry localEntry;
    if (paramObject == null)
      for (i = arrayOfEntry.length; i-- > 0; )
        for (localEntry = arrayOfEntry[i]; localEntry != null; localEntry = localEntry.nextEntry)
          if (localEntry.value == null)
            return true;
    else {
      for (i = arrayOfEntry.length; i-- > 0; ) {
        for (localEntry = arrayOfEntry[i]; localEntry != null; localEntry = localEntry.nextEntry)
          if (paramObject.equals(localEntry.value))
            return true;
      }
    }
    return false;
  }

  public boolean containsKey(Object paramObject)
  {
    processQueue();
    Entry[] arrayOfEntry = this.table;
    if (paramObject != null) {
      int i = paramObject.hashCode();
      int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
      for (Entry localEntry2 = arrayOfEntry[j]; localEntry2 != null; localEntry2 = localEntry2.nextEntry)
        if ((localEntry2.hash == i) && (paramObject.equals(localEntry2.getKey())))
          return true;
    } else {
      for (Entry localEntry1 = arrayOfEntry[0]; localEntry1 != null; localEntry1 = localEntry1.nextEntry) {
        if (localEntry1.getKey() == null)
          return true;
      }
    }
    return false;
  }

  public Object get(Object paramObject)
  {
    processQueue();
    Entry[] arrayOfEntry = this.table;

    if (paramObject != null) {
      int i = paramObject.hashCode();
      int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
      for (Entry localEntry2 = arrayOfEntry[j]; localEntry2 != null; localEntry2 = localEntry2.nextEntry)
        if ((localEntry2.hash == i) && (paramObject.equals(localEntry2.getKey())))
          return localEntry2.value;
    } else {
      for (Entry localEntry1 = arrayOfEntry[0]; localEntry1 != null; localEntry1 = localEntry1.nextEntry) {
        if (localEntry1.getKey() == null)
          return localEntry1.value;
      }
    }
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
      for (localEntry1 = arrayOfEntry1[k]; localEntry1 != null; ) {
        Entry localEntry2 = localEntry1;
        localEntry1 = localEntry1.nextEntry;

        int m = (localEntry2.hash & 0x7FFFFFFF) % j;
        localEntry2.nextEntry = arrayOfEntry2[m];
        arrayOfEntry2[m] = localEntry2;
      }
    Entry localEntry1;
  }

  public Object put(Object paramObject1, Object paramObject2)
  {
    processQueue();

    Entry[] arrayOfEntry = this.table;
    int i = 0;
    int j = 0;
    Object localObject;
    if (paramObject1 != null) {
      i = paramObject1.hashCode();
      j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
      for (localEntry = arrayOfEntry[j]; localEntry != null; localEntry = localEntry.nextEntry)
        if ((localEntry.hash == i) && (paramObject1.equals(localEntry.getKey()))) {
          localObject = localEntry.value;
          localEntry.value = paramObject2;
          return localObject;
        }
    }
    else {
      for (localEntry = arrayOfEntry[0]; localEntry != null; localEntry = localEntry.nextEntry) {
        if (localEntry.getKey() == null) {
          localObject = localEntry.value;
          localEntry.value = paramObject2;
          return localObject;
        }
      }
    }

    this.modCount += 1;
    if (this.count >= this.threshold)
    {
      rehash();

      arrayOfEntry = this.table;
      j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
    }

    Entry localEntry = new Entry(i, paramObject1, paramObject2, arrayOfEntry[j]);
    arrayOfEntry[j] = localEntry;
    this.count += 1;
    return null;
  }

  public Object remove(Object paramObject)
  {
    processQueue();
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
        if ((((Entry)localObject1).hash == i) && (paramObject.equals(((Entry)localObject1).getKey()))) {
          this.modCount += 1;
          if (localObject2 != null)
            localObject2.nextEntry = ((Entry)localObject1).nextEntry;
          else {
            arrayOfEntry[j] = ((Entry)localObject1).nextEntry;
          }
          this.count -= 1;
          Object localObject3 = ((Entry)localObject1).value;
          ((Entry)localObject1).clear();
          return localObject3;
        }
        localObject2 = localObject1; localObject1 = ((Entry)localObject1).nextEntry;
      }

    }
    else
    {
      localEntry1 = arrayOfEntry[0]; for (localEntry2 = null; localEntry1 != null; )
      {
        if (localEntry1.getKey() == null) {
          this.modCount += 1;
          if (localEntry2 != null)
            localEntry2.nextEntry = localEntry1.nextEntry;
          else {
            arrayOfEntry[0] = localEntry1.nextEntry;
          }
          this.count -= 1;
          localObject1 = localEntry1.value;
          localEntry1.clear();
          return localObject1;
        }
        localEntry2 = localEntry1; localEntry1 = localEntry1.nextEntry;
      }

    }

    return null;
  }

  private void removeEntry(Entry paramEntry) {
    Entry[] arrayOfEntry = this.table;
    int i = paramEntry.hash;
    int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;
    Entry localEntry1 = arrayOfEntry[j]; for (Entry localEntry2 = null; localEntry1 != null; )
    {
      if (localEntry1 == paramEntry) {
        this.modCount += 1;
        if (localEntry2 != null) localEntry2.nextEntry = localEntry1.nextEntry; else
          arrayOfEntry[j] = localEntry1.nextEntry;
        this.count -= 1;
        paramEntry.clear();
        return;
      }
      localEntry2 = localEntry1; localEntry1 = localEntry1.nextEntry;
    }
  }

  public void putAll(Map paramMap)
  {
    processQueue();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext()) {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      put(localEntry.getKey(), localEntry.getValue());
    }
  }

  public void clear()
  {
    processQueue();
    Entry[] arrayOfEntry = this.table;
    this.modCount += 1;
    int i = arrayOfEntry.length;
    while (true) { i--; if (i < 0) break;
      Object localObject = arrayOfEntry[i];
      while (localObject != null) {
        Entry localEntry = ((Entry)localObject).nextEntry;
        ((Entry)localObject).clear();
        localObject = localEntry;
      }
      arrayOfEntry[i] = null;
    }
    this.count = 0;
  }

  public Object clone()
  {
    processQueue();
    try {
      WeakHashMapExt localWeakHashMapExt = (WeakHashMapExt)super.clone();
      localWeakHashMapExt.table = new Entry[this.table.length];
      for (int i = this.table.length; i-- > 0; ) {
        localWeakHashMapExt.table[i] = (this.table[i] != null ? (Entry)this.table[i].clone() : null);
      }

      localWeakHashMapExt.keySet = null;
      localWeakHashMapExt.entrySet = null;
      localWeakHashMapExt.values = null;
      localWeakHashMapExt.modCount = 0;
      return localWeakHashMapExt;
    } catch (CloneNotSupportedException localCloneNotSupportedException) {
    }
    throw new InternalError();
  }

  public Set keySet()
  {
    if (this.keySet == null)
      this.keySet = new AbstractSet() {
        public Iterator iterator() {
          return new WeakHashMapExt.HashIterator(WeakHashMapExt.this, 0);
        }
        public int size() {
          WeakHashMapExt.this.processQueue();
          return WeakHashMapExt.this.count;
        }
        public boolean contains(Object paramObject) {
          return WeakHashMapExt.this.containsKey(paramObject);
        }
        public boolean remove(Object paramObject) {
          return WeakHashMapExt.this.remove(paramObject) != null;
        }
        public void clear() {
          WeakHashMapExt.this.clear();
        }
      };
    return this.keySet;
  }

  public Collection values()
  {
    if (this.values == null)
      this.values = new AbstractCollection() {
        public Iterator iterator() {
          return new WeakHashMapExt.HashIterator(WeakHashMapExt.this, 1);
        }
        public int size() {
          WeakHashMapExt.this.processQueue();
          return WeakHashMapExt.this.count;
        }
        public boolean contains(Object paramObject) {
          return WeakHashMapExt.this.containsValue(paramObject);
        }
        public void clear() {
          WeakHashMapExt.this.clear();
        }
      };
    return this.values;
  }

  public Set entrySet()
  {
    if (this.entrySet == null) {
      this.entrySet = new AbstractSet() {
        public Iterator iterator() {
          return new WeakHashMapExt.HashIterator(WeakHashMapExt.this, 2);
        }

        public boolean contains(Object paramObject) {
          if (!(paramObject instanceof Map.Entry))
            return false;
          WeakHashMapExt.this.processQueue();
          Map.Entry localEntry = (Map.Entry)paramObject;
          Object localObject = localEntry.getKey();
          WeakHashMapExt.Entry[] arrayOfEntry = WeakHashMapExt.this.table;
          int i = localObject == null ? 0 : localObject.hashCode();
          int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;

          for (WeakHashMapExt.Entry localEntry1 = arrayOfEntry[j]; localEntry1 != null; localEntry1 = localEntry1.nextEntry)
            if ((localEntry1.hash == i) && (localEntry1.equals(localEntry)))
              return true;
          return false;
        }

        public boolean remove(Object paramObject) {
          if (!(paramObject instanceof Map.Entry))
            return false;
          WeakHashMapExt.this.processQueue();
          Map.Entry localEntry = (Map.Entry)paramObject;
          Object localObject = localEntry.getKey();
          WeakHashMapExt.Entry[] arrayOfEntry = WeakHashMapExt.this.table;
          int i = localObject == null ? 0 : localObject.hashCode();
          int j = (i & 0x7FFFFFFF) % arrayOfEntry.length;

          WeakHashMapExt.Entry localEntry1 = arrayOfEntry[j]; for (WeakHashMapExt.Entry localEntry2 = null; localEntry1 != null; )
          {
            if ((localEntry1.hash == i) && (localEntry1.equals(localEntry))) {
              WeakHashMapExt.access$308(WeakHashMapExt.this);
              if (localEntry2 != null)
                localEntry2.nextEntry = localEntry1.nextEntry;
              else {
                arrayOfEntry[j] = localEntry1.nextEntry;
              }
              WeakHashMapExt.access$110(WeakHashMapExt.this);
              localEntry1.clear();
              return true;
            }
            localEntry2 = localEntry1; localEntry1 = localEntry1.nextEntry;
          }

          return false;
        }

        public int size() {
          WeakHashMapExt.this.processQueue();
          return WeakHashMapExt.this.count;
        }

        public void clear() {
          WeakHashMapExt.this.clear();
        }
      };
    }
    return this.entrySet;
  }

  private void processQueue()
  {
    Entry localEntry;
    while ((localEntry = (Entry)this.queue.poll()) != null)
      removeEntry(localEntry);
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    processQueue();

    paramObjectOutputStream.defaultWriteObject();

    paramObjectOutputStream.writeInt(this.table.length);

    paramObjectOutputStream.writeInt(this.count);

    for (int i = this.table.length - 1; i >= 0; i--) {
      Entry localEntry = this.table[i];

      while (localEntry != null) {
        paramObjectOutputStream.writeObject(localEntry.getKey());
        paramObjectOutputStream.writeObject(localEntry.value);
        localEntry = localEntry.nextEntry;
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

  int capacity() {
    return this.table.length;
  }

  float loadFactor() {
    return this.loadFactor;
  }

  private class HashIterator
    implements Iterator
  {
    WeakHashMapExt.Entry[] table;
    int index;
    WeakHashMapExt.Entry entry = null;
    WeakHashMapExt.Entry lastReturned = null;
    int type;
    private int expectedModCount;

    HashIterator(int arg2)
    {
      WeakHashMapExt.this.processQueue();
      this.table = WeakHashMapExt.this.table;
      this.index = this.table.length;
      this.expectedModCount = WeakHashMapExt.this.modCount;
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
      if (WeakHashMapExt.this.modCount != this.expectedModCount) {
        throw new ConcurrentModificationException();
      }
      while ((this.entry == null) && (this.index > 0)) {
        this.entry = this.table[(--this.index)];
      }
      if (this.entry != null) {
        WeakHashMapExt.Entry localEntry = this.lastReturned = this.entry;
        this.entry = localEntry.nextEntry;
        return this.type == 1 ? localEntry.value : this.type == 0 ? localEntry.getKey() : localEntry;
      }
      throw new NoSuchElementException();
    }

    public void remove() {
      if (this.lastReturned == null)
        throw new IllegalStateException();
      if (WeakHashMapExt.this.modCount != this.expectedModCount) {
        throw new ConcurrentModificationException();
      }
      WeakHashMapExt.Entry[] arrayOfEntry = WeakHashMapExt.this.table;
      int i = (this.lastReturned.hash & 0x7FFFFFFF) % arrayOfEntry.length;

      WeakHashMapExt.Entry localEntry1 = arrayOfEntry[i]; for (WeakHashMapExt.Entry localEntry2 = null; localEntry1 != null; )
      {
        if (localEntry1 == this.lastReturned) {
          WeakHashMapExt.access$308(WeakHashMapExt.this);
          this.expectedModCount += 1;
          if (localEntry2 == null)
            arrayOfEntry[i] = localEntry1.nextEntry;
          else
            localEntry2.nextEntry = localEntry1.nextEntry;
          WeakHashMapExt.access$110(WeakHashMapExt.this);
          this.lastReturned = null;
          localEntry1.clear();
          return;
        }
        localEntry2 = localEntry1; localEntry1 = localEntry1.nextEntry;
      }

      throw new ConcurrentModificationException();
    }
  }

  private class Entry extends WeakReference
    implements Map.Entry
  {
    int hash;
    Object value;
    Entry nextEntry;

    Entry(int paramObject1, Object paramObject2, Object paramEntry, Entry arg5)
    {
      super(WeakHashMapExt.this.queue);
      this.hash = paramObject1;
      this.value = paramEntry;
      Object localObject;
      this.nextEntry = localObject;
    }

    public void clear() {
      super.clear();
      this.value = null;
      this.nextEntry = null;
    }

    protected Object clone() {
      return new Entry(WeakHashMapExt.this, this.hash, get(), this.value, this.nextEntry == null ? null : (Entry)this.nextEntry.clone());
    }

    public Object getKey()
    {
      return get();
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
      Object localObject1 = get();
      Object localObject2 = localEntry.getKey();
      if ((localObject1 == null) || (localObject2 == null)) return false;
      return (localObject1.equals(localObject2)) && (this.value == null ? localEntry.getValue() == null : this.value.equals(localEntry.getValue()));
    }

    public int hashCode()
    {
      return this.hash ^ (this.value == null ? 0 : this.value.hashCode());
    }

    public String toString() {
      Object localObject = get();
      return (localObject == null ? "NULL" : localObject.toString()) + "=" + this.value.toString();
    }
  }
}