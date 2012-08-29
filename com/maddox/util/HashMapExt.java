// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HashMapExt.java

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
import java.util.NoSuchElementException;
import java.util.Set;

public class HashMapExt extends java.util.AbstractMap
    implements java.util.Map, java.lang.Cloneable, java.io.Serializable
{
    private class HashIterator
        implements java.util.Iterator
    {

        public boolean hasNext()
        {
            return entry.lNext != tail;
        }

        public java.lang.Object next()
        {
            if(modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if(entry.lNext == tail)
            {
                throw new NoSuchElementException();
            } else
            {
                lastReturned = entry = entry.lNext;
                return type != 0 ? type != 1 ? ((java.lang.Object) (entry)) : entry.value : entry.key;
            }
        }

        public void remove()
        {
            if(lastReturned == null)
                throw new IllegalStateException();
            if(modCount != expectedModCount)
                throw new ConcurrentModificationException();
            com.maddox.util.HashMapExt.Entry aentry[] = table;
            int i = (lastReturned.hash & 0x7fffffff) % aentry.length;
            com.maddox.util.HashMapExt.Entry entry1 = aentry[i];
            com.maddox.util.HashMapExt.Entry entry2 = null;
            for(; entry1 != null; entry1 = entry1.next)
            {
                if(entry1 == lastReturned)
                {
                    modCount++;
                    expectedModCount++;
                    if(entry2 == null)
                        aentry[i] = entry1.next;
                    else
                        entry2.next = entry1.next;
                    count--;
                    lastReturned = null;
                    entry = entry1.lPrev;
                    freeEntry(entry1);
                    return;
                }
                entry2 = entry1;
            }

            throw new ConcurrentModificationException();
        }

        com.maddox.util.HashMapExt.Entry entry;
        com.maddox.util.HashMapExt.Entry lastReturned;
        int type;
        private int expectedModCount;

        HashIterator(int i)
        {
            entry = head;
            lastReturned = null;
            expectedModCount = modCount;
            type = i;
        }
    }

    private static class Entry
        implements java.util.Map.Entry
    {

        protected java.lang.Object clone()
        {
            return new Entry(hash, key, value, next != null ? (com.maddox.util.Entry)next.clone() : null);
        }

        public java.lang.Object getKey()
        {
            return key;
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
            {
                return false;
            } else
            {
                java.util.Map.Entry entry = (java.util.Map.Entry)obj;
                return (key != null ? key.equals(entry.getKey()) : entry.getKey() == null) && (value != null ? value.equals(entry.getValue()) : entry.getValue() == null);
            }
        }

        public int hashCode()
        {
            return hash ^ (value != null ? value.hashCode() : 0);
        }

        public java.lang.String toString()
        {
            return key.toString() + "=" + value.toString();
        }

        int hash;
        java.lang.Object key;
        java.lang.Object value;
        com.maddox.util.Entry next;
        com.maddox.util.Entry lNext;
        com.maddox.util.Entry lPrev;

        Entry(int i, java.lang.Object obj, java.lang.Object obj1, com.maddox.util.Entry entry)
        {
            hash = i;
            key = obj;
            value = obj1;
            next = entry;
        }
    }


    public HashMapExt(int i, float f)
    {
        modCount = 0;
        keySet = null;
        entrySet = null;
        values = null;
        freeEntryList = null;
        if(i < 0)
            throw new IllegalArgumentException("Illegal Initial Capacity: " + i);
        if(f <= 0.0F)
            throw new IllegalArgumentException("Illegal Load factor: " + f);
        if(i == 0)
            i = 1;
        loadFactor = f;
        table = new com.maddox.util.Entry[i];
        threshold = (int)((float)i * f);
        head = allocEntry(_entryHash++, null, null, null);
        tail = allocEntry(0, null, null, null);
        head.lNext = tail;
        tail.lPrev = head;
    }

    public HashMapExt(int i)
    {
        this(i, 0.75F);
    }

    public HashMapExt()
    {
        this(101, 0.75F);
    }

    public HashMapExt(java.util.Map map)
    {
        this(java.lang.Math.max(2 * map.size(), 11), 0.75F);
        putAll(map);
    }

    public int size()
    {
        return count;
    }

    public boolean isEmpty()
    {
        return count == 0;
    }

    public int hashCode()
    {
        return head.hashCode();
    }

    public boolean equals(java.lang.Object obj)
    {
        return this == obj;
    }

    public java.util.Map.Entry nextEntry(java.util.Map.Entry entry)
    {
        if(count == 0)
            return null;
        com.maddox.util.Entry entry1 = (com.maddox.util.Entry)entry;
        if(entry1 == null)
        {
            nextEntryLock = modCount;
            entry1 = head.lNext;
        } else
        {
            if(modCount != nextEntryLock)
                throw new ConcurrentModificationException();
            entry1 = entry1.lNext;
        }
        if(entry1 == tail)
            return null;
        else
            return entry1;
    }

    public java.util.Map.Entry getEntry(java.lang.Object obj)
    {
        if(obj != null)
        {
            com.maddox.util.Entry aentry[] = table;
            int i = obj.hashCode();
            int j = (i & 0x7fffffff) % aentry.length;
            for(com.maddox.util.Entry entry1 = aentry[j]; entry1 != null; entry1 = entry1.next)
                if(entry1.hash == i && obj.equals(entry1.key))
                    return entry1;

        } else
        {
            for(com.maddox.util.Entry entry = head.lNext; entry != tail; entry = entry.lNext)
                if(entry.key == null)
                    return entry;

        }
        return null;
    }

    public boolean containsValue(java.lang.Object obj)
    {
        com.maddox.util.Entry entry = head.lNext;
        if(obj == null)
            for(; entry != tail; entry = entry.lNext)
                if(entry.value == null)
                    return true;

        else
            for(; entry != tail; entry = entry.lNext)
                if(obj.equals(entry.value))
                    return true;

        return false;
    }

    public boolean containsKey(java.lang.Object obj)
    {
        if(obj != null)
        {
            com.maddox.util.Entry aentry[] = table;
            int i = obj.hashCode();
            int j = (i & 0x7fffffff) % aentry.length;
            for(com.maddox.util.Entry entry1 = aentry[j]; entry1 != null; entry1 = entry1.next)
                if(entry1.hash == i && obj.equals(entry1.key))
                    return true;

        } else
        {
            for(com.maddox.util.Entry entry = head.lNext; entry != tail; entry = entry.lNext)
                if(entry.key == null)
                    return true;

        }
        return false;
    }

    public java.lang.Object get(java.lang.Object obj)
    {
        if(obj != null)
        {
            com.maddox.util.Entry aentry[] = table;
            int i = obj.hashCode();
            int j = (i & 0x7fffffff) % aentry.length;
            for(com.maddox.util.Entry entry1 = aentry[j]; entry1 != null; entry1 = entry1.next)
                if(entry1.hash == i && obj.equals(entry1.key))
                    return entry1.value;

        } else
        {
            for(com.maddox.util.Entry entry = head.lNext; entry != tail; entry = entry.lNext)
                if(entry.key == null)
                    return entry.value;

        }
        return null;
    }

    private void rehash()
    {
        int i = table.length * 2 + 1;
        com.maddox.util.Entry aentry[] = new com.maddox.util.Entry[i];
        modCount++;
        threshold = (int)((float)i * loadFactor);
        table = aentry;
        for(com.maddox.util.Entry entry = head.lNext; entry != tail; entry = entry.lNext)
        {
            int j = (entry.hash & 0x7fffffff) % i;
            entry.next = aentry[j];
            aentry[j] = entry;
        }

    }

    public java.lang.Object put(java.lang.Object obj, java.lang.Object obj1)
    {
        com.maddox.util.Entry aentry[] = table;
        int i = 0;
        int j = 0;
        if(obj != null)
        {
            i = obj.hashCode();
            j = (i & 0x7fffffff) % aentry.length;
            for(com.maddox.util.Entry entry = aentry[j]; entry != null; entry = entry.next)
                if(entry.hash == i && obj.equals(entry.key))
                {
                    java.lang.Object obj2 = entry.value;
                    entry.value = obj1;
                    return obj2;
                }

        } else
        {
            for(com.maddox.util.Entry entry1 = head.lNext; entry1 != tail; entry1 = entry1.lNext)
                if(entry1.key == null)
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
        com.maddox.util.Entry entry2 = allocEntry(i, obj, obj1, aentry[j]);
        entry2.lPrev = head;
        entry2.lNext = head.lNext;
        entry2.lNext.lPrev = entry2;
        head.lNext = entry2;
        aentry[j] = entry2;
        count++;
        return null;
    }

    public java.lang.Object remove(java.lang.Object obj)
    {
        com.maddox.util.Entry aentry[] = table;
        if(obj != null)
        {
            int i = obj.hashCode();
            int j = (i & 0x7fffffff) % aentry.length;
            com.maddox.util.Entry entry2 = aentry[j];
            com.maddox.util.Entry entry3 = null;
            for(; entry2 != null; entry2 = entry2.next)
            {
                if(entry2.hash == i && obj.equals(entry2.key))
                {
                    modCount++;
                    if(entry3 != null)
                        entry3.next = entry2.next;
                    else
                        aentry[j] = entry2.next;
                    count--;
                    java.lang.Object obj2 = entry2.value;
                    freeEntry(entry2);
                    return obj2;
                }
                entry3 = entry2;
            }

        } else
        {
            com.maddox.util.Entry entry = aentry[0];
            com.maddox.util.Entry entry1 = null;
            for(; entry != null; entry = entry.next)
            {
                if(entry.key == null)
                {
                    modCount++;
                    if(entry1 != null)
                        entry1.next = entry.next;
                    else
                        aentry[0] = entry.next;
                    count--;
                    java.lang.Object obj1 = entry.value;
                    freeEntry(entry);
                    return obj1;
                }
                entry1 = entry;
            }

        }
        return null;
    }

    public void putAll(java.util.Map map)
    {
        java.util.Map.Entry entry;
        for(java.util.Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); put(entry.getKey(), entry.getValue()))
            entry = (java.util.Map.Entry)iterator.next();

    }

    public void clear()
    {
        if(count == 0)
            return;
        modCount++;
        for(com.maddox.util.Entry entry = head.lNext; entry != tail;)
        {
            com.maddox.util.Entry entry1 = entry;
            entry = entry1.lNext;
            freeEntry(entry1);
        }

        com.maddox.util.Entry aentry[] = table;
        for(int i = aentry.length; --i >= 0;)
            aentry[i] = null;

        count = 0;
    }

    public java.lang.Object clone()
    {
        com.maddox.util.HashMapExt hashmapext;
        hashmapext = (com.maddox.util.HashMapExt)super.clone();
        hashmapext.table = new com.maddox.util.Entry[table.length];
        for(int i = table.length; i-- > 0;)
        {
            for(com.maddox.util.Entry entry = hashmapext.table[i] = table[i] == null ? null : (com.maddox.util.Entry)table[i].clone(); entry != null; entry = entry.next)
            {
                entry.lPrev = hashmapext.head;
                entry.lNext = hashmapext.head.lNext;
                entry.lNext.lPrev = entry;
                hashmapext.head.lNext = entry;
            }

        }

        hashmapext.keySet = null;
        hashmapext.entrySet = null;
        hashmapext.values = null;
        hashmapext.modCount = 0;
        return hashmapext;
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
                    return count;
                }

                public boolean contains(java.lang.Object obj)
                {
                    return containsKey(obj);
                }

                public boolean remove(java.lang.Object obj)
                {
                    return com.maddox.util.HashMapExt.this.remove(obj) != null;
                }

                public void clear()
                {
                    com.maddox.util.HashMapExt.this.clear();
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
                    return count;
                }

                public boolean contains(java.lang.Object obj)
                {
                    return containsValue(obj);
                }

                public void clear()
                {
                    com.maddox.util.HashMapExt.this.clear();
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
                    java.util.Map.Entry entry = (java.util.Map.Entry)obj;
                    java.lang.Object obj1 = entry.getKey();
                    com.maddox.util.HashMapExt.Entry aentry[] = table;
                    int i = obj1 != null ? obj1.hashCode() : 0;
                    int j = (i & 0x7fffffff) % aentry.length;
                    for(com.maddox.util.HashMapExt.Entry entry1 = aentry[j]; entry1 != null; entry1 = entry1.next)
                        if(entry1.hash == i && entry1.equals(entry))
                            return true;

                    return false;
                }

                public boolean remove(java.lang.Object obj)
                {
                    if(!(obj instanceof java.util.Map.Entry))
                        return false;
                    java.util.Map.Entry entry = (java.util.Map.Entry)obj;
                    java.lang.Object obj1 = entry.getKey();
                    com.maddox.util.HashMapExt.Entry aentry[] = table;
                    int i = obj1 != null ? obj1.hashCode() : 0;
                    int j = (i & 0x7fffffff) % aentry.length;
                    com.maddox.util.HashMapExt.Entry entry1 = aentry[j];
                    com.maddox.util.HashMapExt.Entry entry2 = null;
                    for(; entry1 != null; entry1 = entry1.next)
                    {
                        if(entry1.hash == i && entry1.equals(entry))
                        {
                            modCount++;
                            if(entry2 != null)
                                entry2.next = entry1.next;
                            else
                                aentry[j] = entry1.next;
                            count--;
                            freeEntry(entry1);
                            return true;
                        }
                        entry2 = entry1;
                    }

                    return false;
                }

                public int size()
                {
                    return count;
                }

                public void clear()
                {
                    com.maddox.util.HashMapExt.this.clear();
                }

            }
;
        return entrySet;
    }

    private com.maddox.util.Entry allocEntry(int i, java.lang.Object obj, java.lang.Object obj1, com.maddox.util.Entry entry)
    {
        if(freeEntryList == null)
        {
            return new Entry(i, obj, obj1, entry);
        } else
        {
            com.maddox.util.Entry entry1 = freeEntryList;
            freeEntryList = freeEntryList.next;
            entry1.hash = i;
            entry1.key = obj;
            entry1.value = obj1;
            entry1.next = entry;
            return entry1;
        }
    }

    private void freeEntry(com.maddox.util.Entry entry)
    {
        entry.key = null;
        entry.value = null;
        entry.next = freeEntryList;
        freeEntryList = entry;
        entry.lPrev.lNext = entry.lNext;
        entry.lNext.lPrev = entry.lPrev;
        entry.lPrev = null;
        entry.lNext = null;
    }

    public void freeCachedMem()
    {
        freeEntryList = null;
    }

    private void writeObject(java.io.ObjectOutputStream objectoutputstream)
        throws java.io.IOException
    {
        objectoutputstream.defaultWriteObject();
        objectoutputstream.writeInt(table.length);
        objectoutputstream.writeInt(count);
        for(int i = table.length - 1; i >= 0; i--)
        {
            for(com.maddox.util.Entry entry = table[i]; entry != null; entry = entry.next)
            {
                objectoutputstream.writeObject(entry.key);
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

    public int capacity()
    {
        return table.length;
    }

    public float loadFactor()
    {
        return loadFactor;
    }

    private transient com.maddox.util.Entry table[];
    private transient int count;
    private int threshold;
    private float loadFactor;
    private transient int modCount;
    private static int _entryHash = 0;
    private transient com.maddox.util.Entry head;
    private transient com.maddox.util.Entry tail;
    private transient int nextEntryLock;
    private transient java.util.Set keySet;
    private transient java.util.Set entrySet;
    private transient java.util.Collection values;
    private com.maddox.util.Entry freeEntryList;
    private static final int KEYS = 0;
    private static final int VALUES = 1;
    private static final int ENTRIES = 2;









}
