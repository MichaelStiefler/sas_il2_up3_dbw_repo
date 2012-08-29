// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Tuple4b.java

package com.maddox.JGP;

import java.io.Serializable;

public abstract class Tuple4b
    implements java.io.Serializable, java.lang.Cloneable
{

    public Tuple4b(byte byte0, byte byte1, byte byte2, byte byte3)
    {
        x = byte0;
        y = byte1;
        z = byte2;
        w = byte3;
    }

    public Tuple4b(byte abyte0[])
    {
        x = abyte0[0];
        y = abyte0[1];
        z = abyte0[2];
        w = abyte0[3];
    }

    public Tuple4b(com.maddox.JGP.Tuple4b tuple4b)
    {
        x = tuple4b.x;
        y = tuple4b.y;
        z = tuple4b.z;
        w = tuple4b.w;
    }

    public Tuple4b()
    {
        x = 0;
        y = 0;
        z = 0;
        w = 0;
    }

    public final void set(com.maddox.JGP.Tuple4b tuple4b)
    {
        x = tuple4b.x;
        y = tuple4b.y;
        z = tuple4b.z;
        w = tuple4b.w;
    }

    public final void set(byte abyte0[])
    {
        x = abyte0[0];
        y = abyte0[1];
        z = abyte0[2];
        w = abyte0[3];
    }

    public final void get(byte abyte0[])
    {
        abyte0[0] = x;
        abyte0[1] = y;
        abyte0[2] = z;
        abyte0[3] = w;
    }

    public final void get(com.maddox.JGP.Tuple4b tuple4b)
    {
        tuple4b.x = x;
        tuple4b.y = y;
        tuple4b.z = z;
        tuple4b.w = w;
    }

    public int hashCode()
    {
        return x | y << 8 | z << 16 | w << 24;
    }

    public boolean equals(com.maddox.JGP.Tuple4b tuple4b)
    {
        return tuple4b != null && x == tuple4b.x && y == tuple4b.y && z == tuple4b.z && w == tuple4b.w;
    }

    public java.lang.String toString()
    {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }

    public byte x;
    public byte y;
    public byte z;
    public byte w;
}
