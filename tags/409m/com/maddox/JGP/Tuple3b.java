// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Tuple3b.java

package com.maddox.JGP;

import java.io.Serializable;

public abstract class Tuple3b
    implements java.io.Serializable, java.lang.Cloneable
{

    public Tuple3b(byte byte0, byte byte1, byte byte2)
    {
        x = byte0;
        y = byte1;
        z = byte2;
    }

    public Tuple3b(byte abyte0[])
    {
        x = abyte0[0];
        y = abyte0[1];
        z = abyte0[2];
    }

    public Tuple3b(com.maddox.JGP.Tuple3b tuple3b)
    {
        x = tuple3b.x;
        y = tuple3b.y;
        z = tuple3b.z;
    }

    public Tuple3b()
    {
        x = 0;
        y = 0;
        z = 0;
    }

    public final void set(com.maddox.JGP.Tuple3b tuple3b)
    {
        x = tuple3b.x;
        y = tuple3b.y;
        z = tuple3b.z;
    }

    public final void set(byte abyte0[])
    {
        x = abyte0[0];
        y = abyte0[1];
        z = abyte0[2];
    }

    public final void get(byte abyte0[])
    {
        abyte0[0] = x;
        abyte0[1] = y;
        abyte0[2] = z;
    }

    public final void get(com.maddox.JGP.Tuple3b tuple3b)
    {
        tuple3b.x = x;
        tuple3b.y = y;
        tuple3b.z = z;
    }

    public int hashCode()
    {
        return x | y << 8 | z << 16;
    }

    public boolean equals(com.maddox.JGP.Tuple3b tuple3b)
    {
        return tuple3b != null && x == tuple3b.x && y == tuple3b.y && z == tuple3b.z;
    }

    public boolean equals(java.lang.Object obj)
    {
        return obj != null && (obj instanceof com.maddox.JGP.Tuple3b) && equals((com.maddox.JGP.Tuple3b)obj);
    }

    public java.lang.String toString()
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public byte x;
    public byte y;
    public byte z;
}
