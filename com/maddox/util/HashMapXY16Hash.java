// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HashMapXY16Hash.java

package com.maddox.util;

import java.util.List;

// Referenced classes of package com.maddox.util:
//            HashMapInt, HashMapExt, HashMapIntEntry

public class HashMapXY16Hash
{

    public HashMapXY16Hash()
    {
        initialCapacity = 101;
        loadFactor = 0.75F;
        mapXY = new HashMapInt(initialCapacity, loadFactor);
    }

    public HashMapXY16Hash(int i)
    {
        initialCapacity = 101;
        loadFactor = 0.75F;
        initialCapacity = i;
        mapXY = new HashMapInt(i, loadFactor);
    }

    public HashMapXY16Hash(int i, float f)
    {
        initialCapacity = 101;
        loadFactor = 0.75F;
        initialCapacity = i;
        loadFactor = f;
        mapXY = new HashMapInt(i, f);
    }

    public int capacity()
    {
        int i = 0;
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapXY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapXY.nextEntry(hashmapintentry))
        {
            com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)hashmapintentry.getValue();
            i += hashmapext.capacity();
        }

        return i;
    }

    public int size()
    {
        int i = 0;
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapXY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapXY.nextEntry(hashmapintentry))
        {
            com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)hashmapintentry.getValue();
            i += hashmapext.size();
        }

        return i;
    }

    public void allValues(java.util.List list)
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapXY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapXY.nextEntry(hashmapintentry))
        {
            com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)hashmapintentry.getValue();
            list.add(hashmapext);
        }

    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public boolean containsKey(int i, int j, java.lang.Object obj)
    {
        int k = j & 0xffff | i << 16;
        com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)mapXY.get(k);
        if(hashmapext == null)
            return false;
        else
            return hashmapext.containsKey(obj);
    }

    public com.maddox.util.HashMapExt get(int i, int j)
    {
        return (com.maddox.util.HashMapExt)mapXY.get(j & 0xffff | i << 16);
    }

    public java.lang.Object put(int i, int j, java.lang.Object obj, java.lang.Object obj1)
    {
        int k = j & 0xffff | i << 16;
        com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)mapXY.get(k);
        if(hashmapext == null)
        {
            hashmapext = new HashMapExt(initialCapacity, loadFactor);
            mapXY.put(k, hashmapext);
        }
        return hashmapext.put(obj, obj1);
    }

    public void clear()
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapXY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapXY.nextEntry(hashmapintentry))
        {
            com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)hashmapintentry.getValue();
            hashmapext.clear();
        }

        mapXY.clear();
    }

    public java.lang.Object remove(int i, int j, java.lang.Object obj)
    {
        int k = j & 0xffff | i << 16;
        com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)mapXY.get(k);
        if(hashmapext != null)
        {
            java.lang.Object obj1 = hashmapext.remove(obj);
            if(k != 0 && hashmapext.isEmpty())
                mapXY.remove(k);
            return obj1;
        } else
        {
            return null;
        }
    }

    public boolean equals(java.lang.Object obj)
    {
        return this == obj;
    }

    private transient com.maddox.util.HashMapInt mapXY;
    private int initialCapacity;
    private float loadFactor;
}
