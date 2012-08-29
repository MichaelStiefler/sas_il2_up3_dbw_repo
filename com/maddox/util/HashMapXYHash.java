// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HashMapXYHash.java

package com.maddox.util;

import java.util.List;

// Referenced classes of package com.maddox.util:
//            HashMapInt, HashMapExt, HashMapIntEntry

public class HashMapXYHash
{

    public HashMapXYHash()
    {
        initialCapacity = 101;
        loadFactor = 0.75F;
        mapY = new HashMapInt(initialCapacity, loadFactor);
    }

    public HashMapXYHash(int i)
    {
        initialCapacity = 101;
        loadFactor = 0.75F;
        initialCapacity = i;
        mapY = new HashMapInt(i, loadFactor);
    }

    public HashMapXYHash(int i, float f)
    {
        initialCapacity = 101;
        loadFactor = 0.75F;
        initialCapacity = i;
        loadFactor = f;
        mapY = new HashMapInt(i, f);
    }

    public int capacity()
    {
        int i = 0;
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapY.nextEntry(hashmapintentry))
        {
            com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)hashmapintentry.getValue();
            for(com.maddox.util.HashMapIntEntry hashmapintentry1 = hashmapint.nextEntry(null); hashmapintentry1 != null; hashmapintentry1 = hashmapint.nextEntry(hashmapintentry1))
            {
                com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)hashmapintentry1.getValue();
                i += hashmapext.capacity();
            }

        }

        return i;
    }

    public int size()
    {
        int i = 0;
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapY.nextEntry(hashmapintentry))
        {
            com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)hashmapintentry.getValue();
            for(com.maddox.util.HashMapIntEntry hashmapintentry1 = hashmapint.nextEntry(null); hashmapintentry1 != null; hashmapintentry1 = hashmapint.nextEntry(hashmapintentry1))
            {
                com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)hashmapintentry1.getValue();
                i += hashmapext.size();
            }

        }

        return i;
    }

    public void allValues(java.util.List list)
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapY.nextEntry(hashmapintentry))
        {
            com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)hashmapintentry.getValue();
            for(com.maddox.util.HashMapIntEntry hashmapintentry1 = hashmapint.nextEntry(null); hashmapintentry1 != null; hashmapintentry1 = hashmapint.nextEntry(hashmapintentry1))
            {
                com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)hashmapintentry1.getValue();
                list.add(hashmapext);
            }

        }

    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public com.maddox.util.HashMapExt get(int i, int j)
    {
        com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)mapY.get(i);
        if(hashmapint != null)
            return (com.maddox.util.HashMapExt)hashmapint.get(j);
        else
            return null;
    }

    public boolean containsKey(int i, int j, java.lang.Object obj)
    {
        com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)mapY.get(i);
        if(hashmapint == null)
            return false;
        com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)hashmapint.get(j);
        if(hashmapext == null)
            return false;
        else
            return hashmapext.containsKey(obj);
    }

    public java.lang.Object put(int i, int j, java.lang.Object obj, java.lang.Object obj1)
    {
        com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)mapY.get(i);
        if(hashmapint == null)
        {
            hashmapint = new HashMapInt(initialCapacity, loadFactor);
            mapY.put(i, hashmapint);
        }
        com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)hashmapint.get(j);
        if(hashmapext == null)
        {
            hashmapext = new HashMapExt(initialCapacity, loadFactor);
            hashmapint.put(j, hashmapext);
        }
        return hashmapext.put(obj, obj1);
    }

    public void clear()
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapY.nextEntry(hashmapintentry))
        {
            com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)hashmapintentry.getValue();
            for(com.maddox.util.HashMapIntEntry hashmapintentry1 = hashmapint.nextEntry(null); hashmapintentry1 != null; hashmapintentry1 = hashmapint.nextEntry(hashmapintentry1))
            {
                com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)hashmapintentry1.getValue();
                hashmapext.clear();
            }

            hashmapint.clear();
        }

        mapY.clear();
    }

    public java.lang.Object remove(int i, int j, java.lang.Object obj)
    {
        com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)mapY.get(i);
        if(hashmapint != null)
        {
            com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)hashmapint.get(j);
            if(hashmapext != null)
            {
                java.lang.Object obj1 = hashmapext.remove(obj);
                if((j | i) != 0 && hashmapext.isEmpty())
                    hashmapint.remove(j);
                return obj1;
            }
        }
        return null;
    }

    public boolean equals(java.lang.Object obj)
    {
        return this == obj;
    }

    private transient com.maddox.util.HashMapInt mapY;
    private int initialCapacity;
    private float loadFactor;
}
