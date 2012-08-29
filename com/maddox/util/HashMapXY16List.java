// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HashMapXY16List.java

package com.maddox.util;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.util:
//            HashMapInt, HashMapIntEntry

public class HashMapXY16List
{

    public HashMapXY16List()
    {
        initialCapacity = 101;
        loadFactor = 0.75F;
        mapXY = new HashMapInt(initialCapacity, loadFactor);
    }

    public HashMapXY16List(int i)
    {
        initialCapacity = 101;
        loadFactor = 0.75F;
        initialCapacity = i;
        mapXY = new HashMapInt(i, loadFactor);
    }

    public HashMapXY16List(int i, float f)
    {
        initialCapacity = 101;
        loadFactor = 0.75F;
        initialCapacity = i;
        loadFactor = f;
        mapXY = new HashMapInt(i, f);
    }

    public int size()
    {
        int i = 0;
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapXY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapXY.nextEntry(hashmapintentry))
        {
            java.util.ArrayList arraylist = (java.util.ArrayList)(java.util.ArrayList)hashmapintentry.getValue();
            i += arraylist.size();
        }

        return i;
    }

    public int[] allKeys()
    {
        if(mapXY.size() == 0)
            return null;
        int ai[] = new int[mapXY.size()];
        com.maddox.util.HashMapIntEntry hashmapintentry = mapXY.nextEntry(null);
        int i = 0;
        for(; hashmapintentry != null; hashmapintentry = mapXY.nextEntry(hashmapintentry))
            ai[i++] = hashmapintentry.getKey();

        return ai;
    }

    public int key2x(int i)
    {
        return i & 0xffff;
    }

    public int key2y(int i)
    {
        return i >> 16 & 0xffff;
    }

    public void allValues(java.util.List list)
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapXY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapXY.nextEntry(hashmapintentry))
        {
            java.util.ArrayList arraylist = (java.util.ArrayList)(java.util.ArrayList)hashmapintentry.getValue();
            list.add(arraylist);
        }

    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public boolean containsKey(int i, int j, java.lang.Object obj)
    {
        int k = j & 0xffff | i << 16;
        java.util.ArrayList arraylist = (java.util.ArrayList)mapXY.get(k);
        if(arraylist == null)
            return false;
        else
            return arraylist.contains(obj);
    }

    public java.util.List get(int i, int j)
    {
        return (java.util.List)mapXY.get(j & 0xffff | i << 16);
    }

    public void put(int i, int j, java.lang.Object obj)
    {
        int k = j & 0xffff | i << 16;
        java.util.ArrayList arraylist = (java.util.ArrayList)mapXY.get(k);
        if(arraylist == null)
        {
            arraylist = new ArrayList(initialCapacity);
            mapXY.put(k, arraylist);
        }
        arraylist.add(obj);
    }

    public void put(int i, int j, java.lang.Object obj, int k)
    {
        int l = j & 0xffff | i << 16;
        java.util.ArrayList arraylist = (java.util.ArrayList)mapXY.get(l);
        if(arraylist == null)
        {
            arraylist = new ArrayList(k);
            mapXY.put(l, arraylist);
        }
        arraylist.add(obj);
    }

    public void allValuesTrimToSize()
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapXY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapXY.nextEntry(hashmapintentry))
        {
            java.util.ArrayList arraylist = (java.util.ArrayList)(java.util.ArrayList)hashmapintentry.getValue();
            arraylist.trimToSize();
        }

    }

    public void valueTrimToSize(int i, int j)
    {
        int k = j & 0xffff | i << 16;
        java.util.ArrayList arraylist = (java.util.ArrayList)mapXY.get(k);
        if(arraylist != null)
            arraylist.trimToSize();
    }

    public void clear()
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapXY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapXY.nextEntry(hashmapintentry))
        {
            java.util.ArrayList arraylist = (java.util.ArrayList)(java.util.ArrayList)hashmapintentry.getValue();
            arraylist.clear();
        }

        mapXY.clear();
    }

    public void remove(int i, int j, java.lang.Object obj)
    {
        int k = j & 0xffff | i << 16;
        java.util.ArrayList arraylist = (java.util.ArrayList)mapXY.get(k);
        if(arraylist != null)
        {
            int l = arraylist.indexOf(obj);
            if(l >= 0)
                arraylist.remove(l);
            if(k != 0 && arraylist.isEmpty())
                mapXY.remove(k);
        }
    }

    public void remove(int i, int j)
    {
        int k = j & 0xffff | i << 16;
        java.util.ArrayList arraylist = (java.util.ArrayList)mapXY.get(k);
        if(arraylist != null && k != 0 && arraylist.isEmpty())
            mapXY.remove(k);
    }

    public boolean equals(java.lang.Object obj)
    {
        return this == obj;
    }

    private transient com.maddox.util.HashMapInt mapXY;
    private int initialCapacity;
    private float loadFactor;
}
