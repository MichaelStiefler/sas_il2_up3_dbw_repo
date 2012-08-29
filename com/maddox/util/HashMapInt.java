// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HashMapInt.java

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

// Referenced classes of package com.maddox.util:
//            HashMapIntEntry

public class HashMapInt
    implements java.lang.Cloneable, java.io.Serializable
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
                com.maddox.util.HashMapInt.Entry entry1 = lastReturned = entry;
                entry = entry1.next;
                return type != 1 ? entry1 : entry1.value;
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
            com.maddox.util.HashMapInt.Entry aentry[] = _fld0.table;
            int i = (lastReturned.key & 0x7fffffff) % aentry.length;
            com.maddox.util.HashMapInt.Entry entry1 = aentry[i];
            com.maddox.util.HashMapInt.Entry entry2 = null;
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
                    freeEntry(entry1);
                    return;
                }
                entry2 = entry1;
            }

            throw new ConcurrentModificationException();
        }

        com.maddox.util.HashMapInt.Entry table[];
        int index;
        com.maddox.util.HashMapInt.Entry entry;
        com.maddox.util.HashMapInt.Entry lastReturned;
        int type;
        private int expectedModCount;

        HashIterator(int i)
        {
            table = _fld0.table;
            index = table.length;
            entry = null;
            lastReturned = null;
            expectedModCount = modCount;
            type = i;
        }
    }

    private static class Entry
        implements com.maddox.util.HashMapIntEntry
    {

        protected java.lang.Object clone()
        {
            return new Entry(key, value, next != null ? (com.maddox.util.Entry)next.clone() : null);
        }

        public int getKey()
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
            if(!(obj instanceof com.maddox.util.HashMapIntEntry))
            {
                return false;
            } else
            {
                com.maddox.util.HashMapIntEntry hashmapintentry = (com.maddox.util.HashMapIntEntry)obj;
                return key == hashmapintentry.getKey() && (value != null ? value.equals(hashmapintentry.getValue()) : hashmapintentry.getValue() == null);
            }
        }

        public int hashCode()
        {
            return key ^ (value != null ? value.hashCode() : 0);
        }

        public java.lang.String toString()
        {
            return key + "=" + value.toString();
        }

        int key;
        java.lang.Object value;
        com.maddox.util.Entry next;

        Entry(int i, java.lang.Object obj, com.maddox.util.Entry entry)
        {
            key = i;
            value = obj;
            next = entry;
        }
    }


    public HashMapInt(int i, float f)
    {
        modCount = 0;
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
    }

    public HashMapInt(int i)
    {
        this(i, 0.75F);
    }

    public HashMapInt()
    {
        this(101, 0.75F);
    }

    public HashMapInt(com.maddox.util.HashMapInt hashmapint)
    {
        this(java.lang.Math.max(2 * hashmapint.size(), 11), 0.75F);
        putAll(hashmapint);
    }

    public int size()
    {
        return count;
    }

    public boolean isEmpty()
    {
        return count == 0;
    }

    public boolean equals(java.lang.Object obj)
    {
        return this == obj;
    }

    public com.maddox.util.HashMapIntEntry nextEntry(com.maddox.util.HashMapIntEntry hashmapintentry)
    {
        if(hashmapintentry == null)
            nextEntryLock = modCount;
        else
        if(modCount != nextEntryLock)
            throw new ConcurrentModificationException();
        com.maddox.util.Entry entry = (com.maddox.util.Entry)hashmapintentry;
        int i = 0;
        if(entry != null)
        {
            if(entry.next != null)
                return entry.next;
            i = (entry.getKey() & 0x7fffffff) % table.length - 1;
        } else
        {
            i = table.length - 1;
        }
        for(; i >= 0; i--)
            if(table[i] != null)
                return table[i];

        return null;
    }

    public com.maddox.util.HashMapIntEntry getEntry(int i)
    {
        com.maddox.util.Entry aentry[] = table;
        int j = i;
        int k = (j & 0x7fffffff) % aentry.length;
        for(com.maddox.util.Entry entry = aentry[k]; entry != null; entry = entry.next)
            if(entry.key == i)
                return entry;

        return null;
    }

    public boolean containsValue(java.lang.Object obj)
    {
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
                    entry = entry.next;
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
                    entry1 = entry1.next;
                }
            }

        }
        return false;
    }

    public boolean containsKey(int i)
    {
        com.maddox.util.Entry aentry[] = table;
        int j = i;
        int k = (j & 0x7fffffff) % aentry.length;
        for(com.maddox.util.Entry entry = aentry[k]; entry != null; entry = entry.next)
            if(entry.key == i)
                return true;

        return false;
    }

    public int findNotUsedKey(int i, int j, int k)
    {
        com.maddox.util.Entry aentry[] = table;
        if(i < k)
        {
            if(j < i || j >= k)
                return k;
            for(int l = j; l < k; l++)
            {
                com.maddox.util.Entry entry;
                for(entry = aentry[(l & 0x7fffffff) % aentry.length]; entry != null && entry.key != l; entry = entry.next);
                if(entry == null)
                    return l;
            }

            for(int i1 = i; i1 < j; i1++)
            {
                com.maddox.util.Entry entry1;
                for(entry1 = aentry[(i1 & 0x7fffffff) % aentry.length]; entry1 != null && entry1.key != i1; entry1 = entry1.next);
                if(entry1 == null)
                    return i1;
            }

        } else
        if(i > k)
        {
            if(j > i || j <= k)
                return k;
            for(int j1 = j; j1 > k; j1--)
            {
                com.maddox.util.Entry entry2;
                for(entry2 = aentry[(j1 & 0x7fffffff) % aentry.length]; entry2 != null && entry2.key != j1; entry2 = entry2.next);
                if(entry2 == null)
                    return j1;
            }

            for(int k1 = i; k1 > j; k1--)
            {
                com.maddox.util.Entry entry3;
                for(entry3 = aentry[(k1 & 0x7fffffff) % aentry.length]; entry3 != null && entry3.key != k1; entry3 = entry3.next);
                if(entry3 == null)
                    return k1;
            }

        }
        return k;
    }

    public java.lang.Object get(int i)
    {
        com.maddox.util.Entry aentry[] = table;
        int j = i;
        int k = (j & 0x7fffffff) % aentry.length;
        for(com.maddox.util.Entry entry = aentry[k]; entry != null; entry = entry.next)
            if(entry.key == i)
                return entry.value;

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
                entry = entry.next;
                int l = (entry1.key & 0x7fffffff) % j;
                entry1.next = aentry1[l];
                aentry1[l] = entry1;
            }
        }

    }

    public java.lang.Object put(int i, java.lang.Object obj)
    {
        com.maddox.util.Entry aentry[] = table;
        int j = i;
        int k = 0;
        k = (j & 0x7fffffff) % aentry.length;
        for(com.maddox.util.Entry entry = aentry[k]; entry != null; entry = entry.next)
            if(entry.key == i)
            {
                java.lang.Object obj1 = entry.value;
                entry.value = obj;
                return obj1;
            }

        modCount++;
        if(count >= threshold)
        {
            rehash();
            aentry = table;
            k = (j & 0x7fffffff) % aentry.length;
        }
        com.maddox.util.Entry entry1 = allocEntry(i, obj, aentry[k]);
        aentry[k] = entry1;
        count++;
        return null;
    }

    public java.lang.Object remove(int i)
    {
        com.maddox.util.Entry aentry[] = table;
        int j = i;
        int k = (j & 0x7fffffff) % aentry.length;
        com.maddox.util.Entry entry = aentry[k];
        com.maddox.util.Entry entry1 = null;
        for(; entry != null; entry = entry.next)
        {
            if(entry.key == i)
            {
                modCount++;
                if(entry1 != null)
                    entry1.next = entry.next;
                else
                    aentry[k] = entry.next;
                count--;
                java.lang.Object obj = entry.value;
                freeEntry(entry);
                return obj;
            }
            entry1 = entry;
        }

        return null;
    }

    public void putAll(com.maddox.util.HashMapInt hashmapint)
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = hashmapint.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint.nextEntry(hashmapintentry))
            put(hashmapintentry.getKey(), hashmapintentry.getValue());

    }

    public void clear()
    {
        com.maddox.util.Entry aentry[] = table;
        modCount++;
        for(int i = aentry.length; --i >= 0;)
        {
            com.maddox.util.Entry entry1;
            for(com.maddox.util.Entry entry = aentry[i]; entry != null; entry = entry1)
            {
                entry1 = entry.next;
                freeEntry(entry);
            }

            aentry[i] = null;
        }

        count = 0;
    }

    public java.lang.Object clone()
    {
        com.maddox.util.HashMapInt hashmapint;
        hashmapint = (com.maddox.util.HashMapInt)super.clone();
        hashmapint.table = new com.maddox.util.Entry[table.length];
        for(int i = table.length; i-- > 0;)
            hashmapint.table[i] = table[i] == null ? null : (com.maddox.util.Entry)table[i].clone();

        hashmapint.entrySet = null;
        hashmapint.values = null;
        hashmapint.modCount = 0;
        return hashmapint;
        java.lang.CloneNotSupportedException clonenotsupportedexception;
        clonenotsupportedexception;
        throw new InternalError();
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
                    com.maddox.util.HashMapInt.this.clear();
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
                    if(!(obj instanceof com.maddox.util.HashMapIntEntry))
                        return false;
                    com.maddox.util.HashMapIntEntry hashmapintentry = (com.maddox.util.HashMapIntEntry)obj;
                    int i = hashmapintentry.getKey();
                    com.maddox.util.HashMapInt.Entry aentry[] = table;
                    int j = i;
                    int k = (j & 0x7fffffff) % aentry.length;
                    for(com.maddox.util.HashMapInt.Entry entry = aentry[k]; entry != null; entry = entry.next)
                        if(entry.key == i && entry.equals(hashmapintentry))
                            return true;

                    return false;
                }

                public boolean remove(java.lang.Object obj)
                {
                    if(!(obj instanceof com.maddox.util.HashMapIntEntry))
                        return false;
                    com.maddox.util.HashMapIntEntry hashmapintentry = (com.maddox.util.HashMapIntEntry)obj;
                    int i = hashmapintentry.getKey();
                    com.maddox.util.HashMapInt.Entry aentry[] = table;
                    int j = i;
                    int k = (j & 0x7fffffff) % aentry.length;
                    com.maddox.util.HashMapInt.Entry entry = aentry[k];
                    com.maddox.util.HashMapInt.Entry entry1 = null;
                    for(; entry != null; entry = entry.next)
                    {
                        if(entry.key == i && entry.equals(hashmapintentry))
                        {
                            modCount++;
                            if(entry1 != null)
                                entry1.next = entry.next;
                            else
                                aentry[k] = entry.next;
                            count--;
                            freeEntry(entry);
                            return true;
                        }
                        entry1 = entry;
                    }

                    return false;
                }

                public int size()
                {
                    return count;
                }

                public void clear()
                {
                    com.maddox.util.HashMapInt.this.clear();
                }

            }
;
        return entrySet;
    }

    private com.maddox.util.Entry allocEntry(int i, java.lang.Object obj, com.maddox.util.Entry entry)
    {
        if(freeEntryList == null)
        {
            return new Entry(i, obj, entry);
        } else
        {
            com.maddox.util.Entry entry1 = freeEntryList;
            freeEntryList = freeEntryList.next;
            entry1.key = i;
            entry1.value = obj;
            entry1.next = entry;
            return entry1;
        }
    }

    private void freeEntry(com.maddox.util.Entry entry)
    {
        entry.value = null;
        entry.next = freeEntryList;
        freeEntryList = entry;
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
                objectoutputstream.writeInt(entry.key);
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
            int l = objectinputstream.readInt();
            java.lang.Object obj = objectinputstream.readObject();
            put(l, obj);
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
    private transient java.util.Set entrySet;
    private transient java.util.Collection values;
    private com.maddox.util.Entry freeEntryList;
    private static final int VALUES = 1;
    private static final int ENTRIES = 2;






}
