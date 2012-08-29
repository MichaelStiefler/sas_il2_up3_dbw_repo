// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HashMapXY16.java

package com.maddox.util;


// Referenced classes of package com.maddox.util:
//            HashMapInt, HashMapIntEntry

public class HashMapXY16 extends com.maddox.util.HashMapInt
{

    public HashMapXY16()
    {
    }

    public HashMapXY16(int i)
    {
        super(i);
    }

    public HashMapXY16(int i, float f)
    {
        super(i, f);
    }

    public HashMapXY16(com.maddox.util.HashMapXY16 hashmapxy16)
    {
        super(hashmapxy16);
    }

    public com.maddox.util.HashMapIntEntry getEntry(int i, int j)
    {
        int k = j & 0xffff | i << 16;
        return getEntry(k);
    }

    public boolean containsKey(int i, int j)
    {
        int k = j & 0xffff | i << 16;
        return containsKey(k);
    }

    public java.lang.Object get(int i, int j)
    {
        return get(j & 0xffff | i << 16);
    }

    public java.lang.Object put(int i, int j, java.lang.Object obj)
    {
        int k = j & 0xffff | i << 16;
        return put(k, obj);
    }

    public java.lang.Object remove(int i, int j)
    {
        int k = j & 0xffff | i << 16;
        return remove(k);
    }

    public boolean equals(java.lang.Object obj)
    {
        return this == obj;
    }
}
