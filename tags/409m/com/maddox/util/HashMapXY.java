// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HashMapXY.java

package com.maddox.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// Referenced classes of package com.maddox.util:
//            HashMapInt, HashMapIntEntry

public class HashMapXY
    implements java.lang.Cloneable, java.io.Serializable
{

    public HashMapXY()
    {
        initialCapacity = 101;
        loadFactor = 0.75F;
        mapY = new HashMapInt(initialCapacity, loadFactor);
    }

    public HashMapXY(int i)
    {
        initialCapacity = 101;
        loadFactor = 0.75F;
        initialCapacity = i;
        mapY = new HashMapInt(i, loadFactor);
    }

    public HashMapXY(int i, float f)
    {
        initialCapacity = 101;
        loadFactor = 0.75F;
        initialCapacity = i;
        loadFactor = f;
        mapY = new HashMapInt(i, f);
    }

    public int size()
    {
        int i = 0;
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapY.nextEntry(hashmapintentry))
            i += ((com.maddox.util.HashMapInt)hashmapintentry.getValue()).size();

        return i;
    }

    public int sizeY()
    {
        return mapY.size();
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public boolean isEmptyY()
    {
        return sizeY() == 0;
    }

    public com.maddox.util.HashMapInt mapY()
    {
        return mapY;
    }

    public com.maddox.util.HashMapInt get(int i)
    {
        return (com.maddox.util.HashMapInt)mapY.get(i);
    }

    public java.lang.Object get(int i, int j)
    {
        com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)mapY.get(i);
        if(hashmapint != null)
            return hashmapint.get(j);
        else
            return null;
    }

    public boolean containsKey(int i)
    {
        return mapY.containsKey(i);
    }

    public boolean containsKey(int i, int j)
    {
        com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)mapY.get(i);
        if(hashmapint != null)
            return hashmapint.containsKey(j);
        else
            return false;
    }

    public boolean containsValue(java.lang.Object obj)
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapY.nextEntry(hashmapintentry))
            if(((com.maddox.util.HashMapInt)hashmapintentry.getValue()).containsValue(obj))
                return true;

        return false;
    }

    public boolean containsValue(int i, java.lang.Object obj)
    {
        com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)mapY.get(i);
        if(hashmapint != null)
            return hashmapint.containsValue(obj);
        else
            return false;
    }

    public java.lang.Object put(int i, int j, java.lang.Object obj)
    {
        com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)mapY.get(i);
        if(hashmapint == null)
        {
            hashmapint = new HashMapInt(initialCapacity, loadFactor);
            mapY.put(i, hashmapint);
        }
        return hashmapint.put(j, obj);
    }

    public void clear()
    {
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapY.nextEntry(hashmapintentry))
            ((com.maddox.util.HashMapInt)hashmapintentry.getValue()).clear();

        mapY.clear();
    }

    public void clear(int i)
    {
        com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)mapY.get(i);
        if(hashmapint != null)
        {
            hashmapint.clear();
            mapY.remove(i);
        }
    }

    public java.lang.Object remove(int i, int j)
    {
        com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)mapY.get(i);
        if(hashmapint != null)
        {
            java.lang.Object obj = hashmapint.remove(j);
            if(hashmapint.isEmpty())
                mapY.remove(i);
            return obj;
        } else
        {
            return null;
        }
    }

    public boolean equals(java.lang.Object obj)
    {
        return this == obj;
    }

    public java.lang.Object clone()
    {
        com.maddox.util.HashMapXY hashmapxy;
        hashmapxy = (com.maddox.util.HashMapXY)super.clone();
        hashmapxy.mapY = new HashMapInt(initialCapacity, loadFactor);
        for(com.maddox.util.HashMapIntEntry hashmapintentry = mapY.nextEntry(null); hashmapintentry != null; hashmapintentry = mapY.nextEntry(hashmapintentry))
        {
            int i = hashmapintentry.getKey();
            com.maddox.util.HashMapInt hashmapint = (com.maddox.util.HashMapInt)hashmapintentry.getValue();
            hashmapint = (com.maddox.util.HashMapInt)hashmapint.clone();
            hashmapxy.mapY.put(i, hashmapint);
        }

        return hashmapxy;
        java.lang.CloneNotSupportedException clonenotsupportedexception;
        clonenotsupportedexception;
        throw new InternalError();
    }

    private void writeObject(java.io.ObjectOutputStream objectoutputstream)
        throws java.io.IOException
    {
        objectoutputstream.defaultWriteObject();
        objectoutputstream.writeObject(mapY);
    }

    private void readObject(java.io.ObjectInputStream objectinputstream)
        throws java.io.IOException, java.lang.ClassNotFoundException
    {
        objectinputstream.defaultReadObject();
        mapY = (com.maddox.util.HashMapInt)objectinputstream.readObject();
    }

    private transient com.maddox.util.HashMapInt mapY;
    private int initialCapacity;
    private float loadFactor;
}
