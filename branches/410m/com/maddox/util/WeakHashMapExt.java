// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   WeakHashMapExt.java

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
import java.util.NoSuchElementException;
import java.util.Set;

public class WeakHashMapExt extends java.util.AbstractMap
    implements java.util.Map, java.lang.Cloneable, java.io.Serializable
{
    private class HashIterator
        implements java.util.Iterator
    {

        public boolean hasNext()
        {
            for(; entry == null && index > 0; entry = table[--index]);
            return entry != null;
        }

        public java.lang.Object next()
        {
            if(modCount != expectedModCount)
                throw new ConcurrentModificationException();
            for(; entry == null && index > 0; entry = table[--index]);
            if(entry != null)
            {
                com.maddox.util.WeakHashMapExt.Entry entry1 = lastReturned = entry;
                entry = entry1.nextEntry;
                return type != 0 ? type != 1 ? ((java.lang.Object) (entry1)) : entry1.value : entry1.getKey();
            } else
            {
                throw new NoSuchElementException();
            }
        }

        public void remove()
        {
            if(lastReturned == null)
                throw new IllegalStateException();
            if(modCount != expectedModCount)
                throw new ConcurrentModificationException();
            com.maddox.util.WeakHashMapExt.Entry aentry[] = _fld0.table;
            int i = (lastReturned.hash & 0x7fffffff) % aentry.length;
            com.maddox.util.WeakHashMapExt.Entry entry1 = aentry[i];
            com.maddox.util.WeakHashMapExt.Entry entry2 = null;
            for(; entry1 != null; entry1 = entry1.nextEntry)
            {
                if(entry1 == lastReturned)
                {
                    modCount++;
                    expectedModCount++;
                    if(entry2 == null)
                        aentry[i] = entry1.nextEntry;
                    else
                        entry2.nextEntry = entry1.nextEntry;
                    count--;
                    lastReturned = null;
                    entry1.clear();
                    return;
                }
                entry2 = entry1;
            }

            throw new ConcurrentModificationException();
        }

        com.maddox.util.WeakHashMapExt.Entry table[];
        int index;
        com.maddox.util.WeakHashMapExt.Entry entry;
        com.maddox.util.WeakHashMapExt.Entry lastReturned;
        int type;
        private int expectedModCount;

        HashIterator(int i)
        {
            entry = null;
            lastReturned = null;
            processQueue();
            table = com.maddox.util.WeakHashMapExt.this.table;
            index = table.length;
            expectedModCount = modCount;
            type = i;
        }
    }

    private class Entry extends java.lang.ref.WeakReference
        implements java.util.Map.Entry
    {

        public void clear()
        {
            super.clear();
            value = null;
            nextEntry = null;
        }

        protected java.lang.Object clone()
        {
            return new Entry(hash, get(), value, nextEntry != null ? (com.maddox.util.Entry)nextEntry.clone() : null);
        }

        public java.lang.Object getKey()
        {
            return get();
        }

        public java.lang.Object getValue()
        {
            return value;
        }

        public java.lang.Object setValue(java.lang.Object obj)
        {
            java.lang.Object obj1 = value;
            value = obj;
            return obj1;
        }

        public boolean equals(java.lang.Object obj)
        {
            if(!(obj instanceof java.util.Map.Entry))
                return false;
            java.util.Map.Entry entry = (java.util.Map.Entry)obj;
            java.lang.Object obj1 = get();
            java.lang.Object obj2 = entry.getKey();
            if(obj1 == null || obj2 == null)
                return false;
            else
                return obj1.equals(obj2) && (value != null ? value.equals(entry.getValue()) : entry.getValue() == null);
        }

        public int hashCode()
        {
            return hash ^ (value != null ? value.hashCode() : 0);
        }

        public java.lang.String toString()
        {
            java.lang.Object obj = get();
            return (obj != null ? obj.toString() : "NULL") + "=" + value.toString();
        }

        int hash;
        java.lang.Object value;
        com.maddox.util.Entry nextEntry;

        Entry(int i, java.lang.Object obj, java.lang.Object obj1, com.maddox.util.Entry entry)
        {
            super(obj, queue);
            hash = i;
            value = obj1;
            nextEntry = entry;
        }
    }


    public WeakHashMapExt(int i, float f)
    {
        modCount = 0;
        keySet = null;
        entrySet = null;
        values = null;
        queue = new ReferenceQueue();
        if(i < 0)
            throw new IllegalArgumentException("Illegal Initial Capacity: " + i);
        if(f <= 0.0F)
            throw new IllegalArgumentException("Illegal Load factor: " + f);
        if(i == 0)
            i = 1;
        loadFactor = f;
        table = new com.maddox.util.Entry[i];
        threshold = (int)((float)i * f);
    }

    public WeakHashMapExt(int i)
    {
        this(i, 0.75F);
    }

    public WeakHashMapExt()
    {
        this(101, 0.75F);
    }

    public WeakHashMapExt(java.util.Map map)
    {
        this(java.lang.Math.max(2 * map.size(), 11), 0.75F);
        putAll(map);
    }

    public int size()
    {
        processQueue();
        return count;
    }

    public boolean isEmpty()
    {
        processQueue();
        return count == 0;
    }

    public int hashCode()
    {
        return hashCode();
    }

    public boolean equals(java.lang.Object obj)
    {
        return this == obj;
    }

    public java.util.Map.Entry nextEntry(java.util.Map.Entry entry)
    {
        if(entry == null)
            nextEntryLock = modCount;
        else
        if(modCount != nextEntryLock)
            throw new ConcurrentModificationException();
        com.maddox.util.Entry entry1 = (com.maddox.util.Entry)entry;
        int i = 0;
        if(entry1 != null)
        {
            if(entry1.nextEntry != null)
                return entry1.nextEntry;
            java.lang.Object obj = entry1.getKey();
            if(obj != null)
                i = (obj.hashCode() & 0x7fffffff) % table.length - 1;
        } else
        {
            i = table.length - 1;
        }
        for(; i >= 0; i--)
            if(table[i] != null)
                return table[i];

        return null;
    }

    public java.util.Map.Entry getEntry(java.lang.Object obj)
    {
        com.maddox.util.Entry aentry[] = table;
        if(obj != null)
        {
            int i = obj.hashCode();
            int j = (i & 0x7fffffff) % aentry.length;
            for(com.maddox.util.Entry entry1 = aentry[j]; entry1 != null; entry1 = entry1.nextEntry)
                if(entry1.hash == i && obj.equals(entry1.getKey()))
                    return entry1;

        } else
        {
            for(com.maddox.util.Entry entry = aentry[0]; entry != null; entry = entry.nextEntry)
                if(entry.getKey() == null)
                    return entry;

        }
        return null;
    }

    public boolean containsValue(java.lang.Object obj)
    {
        processQueue();
        com.maddox.util.Entry aentry[] = table;
        if(obj == null)
        {
            for(int i = aentry.length; i-- > 0;)
            {
                com.maddox.util.Entry entry = aentry[i];
                while(entry != null) 
                {
                    if(entry.value == null)
                        return true;
                    entry = entry.nextEntry;
                }
            }

        } else
        {
            for(int j = aentry.length; j-- > 0;)
            {
                com.maddox.util.Entry entry1 = aentry[j];
                while(entry1 != null) 
                {
                    if(obj.equals(entry1.value))
                        return true;
                    entry1 = entry1.nextEntry;
                }
            }

        }
        return false;
    }

    public boolean containsKey(java.lang.Object obj)
    {
        processQueue();
        com.maddox.util.Entry aentry[] = table;
        if(obj != null)
        {
            int i = obj.hashCode();
            int j = (i & 0x7fffffff) % aentry.length;
            for(com.maddox.util.Entry entry1 = aentry[j]; entry1 != null; entry1 = entry1.nextEntry)
                if(entry1.hash == i && obj.equals(entry1.getKey()))
                    return true;

        } else
        {
            for(com.maddox.util.Entry entry = aentry[0]; entry != null; entry = entry.nextEntry)
                if(entry.getKey() == null)
                    return true;

        }
        return false;
    }

    public java.lang.Object get(java.lang.Object obj)
    {
        processQueue();
        com.maddox.util.Entry aentry[] = table;
        if(obj != null)
        {
            int i = obj.hashCode();
            int j = (i & 0x7fffffff) % aentry.length;
            for(com.maddox.util.Entry entry1 = aentry[j]; entry1 != null; entry1 = entry1.nextEntry)
                if(entry1.hash == i && obj.equals(entry1.getKey()))
                    return entry1.value;

        } else
        {
            for(com.maddox.util.Entry entry = aentry[0]; entry != null; entry = entry.nextEntry)
                if(entry.getKey() == null)
                    return entry.value;

        }
        return null;
    }

    private void rehash()
    {
        int i = table.length;
        com.maddox.util.Entry aentry[] = table;
        int j = i * 2 + 1;
        com.maddox.util.Entry aentry1[] = new com.maddox.util.Entry[j];
        modCount++;
        threshold = (int)((float)j * loadFactor);
        table = aentry1;
        for(int k = i; k-- > 0;)
        {
            com.maddox.util.Entry entry = aentry[k];
            while(entry != null) 
            {
                com.maddox.util.Entry entry1 = entry;
                entry = entry.nextEntry;
                int l = (entry1.hash & 0x7fffffff) % j;
                entry1.nextEntry = aentry1[l];
                aentry1[l] = entry1;
            }
        }

    }

    public java.lang.Object put(java.lang.Object obj, java.lang.Object obj1)
    {
        processQueue();
        com.maddox.util.Entry aentry[] = table;
        int i = 0;
        int j = 0;
        if(obj != null)
        {
            i = obj.hashCode();
            j = (i & 0x7fffffff) % aentry.length;
            for(com.maddox.util.Entry entry = aentry[j]; entry != null; entry = entry.nextEntry)
                if(entry.hash == i && obj.equals(entry.getKey()))
                {
                    java.lang.Object obj2 = entry.value;
                    entry.value = obj1;
                    return obj2;
                }

        } else
        {
            for(com.maddox.util.Entry entry1 = aentry[0]; entry1 != null; entry1 = entry1.nextEntry)
                if(entry1.getKey() == null)
                {
                    java.lang.Object obj3 = entry1.value;
                    entry1.value = obj1;
                    return obj3;
                }

        }
        modCount++;
        if(count >= threshold)
        {
            rehash();
            aentry = table;
            j = (i & 0x7fffffff) % aentry.length;
        }
        com.maddox.util.Entry entry2 = new Entry(i, obj, obj1, aentry[j]);
        aentry[j] = entry2;
        count++;
        return null;
    }

    public java.lang.Object remove(java.lang.Object obj)
    {
        processQueue();
        com.maddox.util.Entry aentry[] = table;
        if(obj != null)
        {
            int i = obj.hashCode();
            int j = (i & 0x7fffffff) % aentry.length;
            com.maddox.util.Entry entry2 = aentry[j];
            com.maddox.util.Entry entry3 = null;
            for(; entry2 != null; entry2 = entry2.nextEntry)
            {
                if(entry2.hash == i && obj.equals(entry2.getKey()))
                {
                    modCount++;
                    if(entry3 != null)
                        entry3.nextEntry = entry2.nextEntry;
                    else
                        aentry[j] = entry2.nextEntry;
                    count--;
                    java.lang.Object obj2 = entry2.value;
                    entry2.clear();
                    return obj2;
                }
                entry3 = entry2;
            }

        } else
        {
            com.maddox.util.Entry entry = aentry[0];
            com.maddox.util.Entry entry1 = null;
            for(; entry != null; entry = entry.nextEntry)
            {
                if(entry.getKey() == null)
                {
                    modCount++;
                    if(entry1 != null)
                        entry1.nextEntry = entry.nextEntry;
                    else
                        aentry[0] = entry.nextEntry;
                    count--;
                    java.lang.Object obj1 = entry.value;
                    entry.clear();
                    return obj1;
                }
                entry1 = entry;
            }

        }
        return null;
    }

    private void removeEntry(com.maddox.util.Entry entry)
    {
        com.maddox.util.Entry aentry[] = table;
        int i = entry.hash;
        int j = (i & 0x7fffffff) % aentry.length;
        com.maddox.util.Entry entry1 = aentry[j];
        com.maddox.util.Entry entry2 = null;
        for(; entry1 != null; entry1 = entry1.nextEntry)
        {
            if(entry1 == entry)
            {
                modCount++;
                if(entry2 != null)
                    entry2.nextEntry = entry1.nextEntry;
                else
                    aentry[j] = entry1.nextEntry;
                count--;
                entry.clear();
                return;
            }
            entry2 = entry1;
        }

    }

    public void putAll(java.util.Map map)
    {
        processQueue();
        java.util.Map.Entry entry;
        for(java.util.Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); put(entry.getKey(), entry.getValue()))
            entry = (java.util.Map.Entry)iterator.next();

    }

    public void clear()
    {
        processQueue();
        com.maddox.util.Entry aentry[] = table;
        modCount++;
        for(int i = aentry.length; --i >= 0;)
        {
            com.maddox.util.Entry entry1;
            for(com.maddox.util.Entry entry = aentry[i]; entry != null; entry = entry1)
            {
                entry1 = entry.nextEntry;
                entry.clear();
            }

            aentry[i] = null;
        }

        count = 0;
    }

    public java.lang.Object clone()
    {
        processQueue();
        com.maddox.util.WeakHashMapExt weakhashmapext;
        weakhashmapext = (com.maddox.util.WeakHashMapExt)super.clone();
        weakhashmapext.table = new com.maddox.util.Entry[table.length];
        for(int i = table.length; i-- > 0;)
            weakhashmapext.table[i] = table[i] == null ? null : (com.maddox.util.Entry)table[i].clone();

        weakhashmapext.keySet = null;
        weakhashmapext.entrySet = null;
        weakhashmapext.values = null;
        weakhashmapext.modCount = 0;
        return weakhashmapext;
        java.lang.CloneNotSupportedException clonenotsupportedexception;
        clonenotsupportedexception;
        throw new InternalError();
    }

    public java.util.Set keySet()
    {
        if(keySet == null)
            keySet = new java.util.AbstractSet() {

                public java.util.Iterator iterator()
                {
                    return new HashIterator(0);
                }

                public int size()
                {
                    processQueue();
                    return count;
                }

                public boolean contains(java.lang.Object obj)
                {
                    return containsKey(obj);
                }

                public boolean remove(java.lang.Object obj)
                {
                    return com.maddox.util.WeakHashMapExt.this.remove(obj) != null;
                }

                public void clear()
                {
                    com.maddox.util.WeakHashMapExt.this.clear();
                }

            }
;
        return keySet;
    }

    public java.util.Collection values()
    {
        if(values == null)
            values = new java.util.AbstractCollection() {

                public java.util.Iterator iterator()
                {
                    return new HashIterator(1);
                }

                public int size()
                {
                    processQueue();
                    return count;
                }

                public boolean contains(java.lang.Object obj)
                {
                    return containsValue(obj);
                }

                public void clear()
                {
                    com.maddox.util.WeakHashMapExt.this.clear();
                }

            }
;
        return values;
    }

    public java.util.Set entrySet()
    {
        if(entrySet == null)
            entrySet = new java.util.AbstractSet() {

                public java.util.Iterator iterator()
                {
                    return new HashIterator(2);
                }

                public boolean contains(java.lang.Object obj)
                {
                    if(!(obj instanceof java.util.Map.Entry))
                        return false;
                    processQueue();
                    java.util.Map.Entry entry = (java.util.Map.Entry)obj;
                    java.lang.Object obj1 = entry.getKey();
                    com.maddox.util.WeakHashMapExt.Entry aentry[] = table;
                    int i = obj1 != null ? obj1.hashCode() : 0;
                    int j = (i & 0x7fffffff) % aentry.length;
                    for(com.maddox.util.WeakHashMapExt.Entry entry1 = aentry[j]; entry1 != null; entry1 = entry1.nextEntry)
                        if(entry1.hash == i && entry1.equals(entry))
                            return true;

                    return false;
                }

                public boolean remove(java.lang.Object obj)
                {
                    if(!(obj instanceof java.util.Map.Entry))
                        return false;
                    processQueue();
                    java.util.Map.Entry entry = (java.util.Map.Entry)obj;
                    java.lang.Object obj1 = entry.getKey();
                    com.maddox.util.WeakHashMapExt.Entry aentry[] = table;
                    int i = obj1 != null ? obj1.hashCode() : 0;
                    int j = (i & 0x7fffffff) % aentry.length;
                    com.maddox.util.WeakHashMapExt.Entry entry1 = aentry[j];
                    com.maddox.util.WeakHashMapExt.Entry entry2 = null;
                    for(; entry1 != null; entry1 = entry1.nextEntry)
                    {
                        if(entry1.hash == i && entry1.equals(entry))
                        {
                            modCount++;
                            if(entry2 != null)
                                entry2.nextEntry = entry1.nextEntry;
                            else
                                aentry[j] = entry1.nextEntry;
                            count--;
                            entry1.clear();
                            return true;
                        }
                        entry2 = entry1;
                    }

                    return false;
                }

                public int size()
                {
                    processQueue();
                    return count;
                }

                public void clear()
                {
                    com.maddox.util.WeakHashMapExt.this.clear();
                }

            }
;
        return entrySet;
    }

    private void processQueue()
    {
        com.maddox.util.Entry entry;
        while((entry = (com.maddox.util.Entry)queue.poll()) != null) 
            removeEntry(entry);
    }

    private void writeObject(java.io.ObjectOutputStream objectoutputstream)
        throws java.io.IOException
    {
        processQueue();
        objectoutputstream.defaultWriteObject();
        objectoutputstream.writeInt(table.length);
        objectoutputstream.writeInt(count);
        for(int i = table.length - 1; i >= 0; i--)
        {
            for(com.maddox.util.Entry entry = table[i]; entry != null; entry = entry.nextEntry)
            {
                objectoutputstream.writeObject(entry.getKey());
                objectoutputstream.writeObject(entry.value);
            }

        }

    }

    private void readObject(java.io.ObjectInputStream objectinputstream)
        throws java.io.IOException, java.lang.ClassNotFoundException
    {
        objectinputstream.defaultReadObject();
        int i = objectinputstream.readInt();
        table = new com.maddox.util.Entry[i];
        int j = objectinputstream.readInt();
        for(int k = 0; k < j; k++)
        {
            java.lang.Object obj = objectinputstream.readObject();
            java.lang.Object obj1 = objectinputstream.readObject();
            put(obj, obj1);
        }

    }

    int capacity()
    {
        return table.length;
    }

    float loadFactor()
    {
        return loadFactor;
    }

    private transient com.maddox.util.Entry table[];
    private transient int count;
    private int threshold;
    private float loadFactor;
    private transient int modCount;
    private transient int nextEntryLock;
    private transient java.util.Set keySet;
    private transient java.util.Set entrySet;
    private transient java.util.Collection values;
    private java.lang.ref.ReferenceQueue queue;
    private static final int KEYS = 0;
    private static final int VALUES = 1;
    private static final int ENTRIES = 2;







}
