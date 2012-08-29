// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Tuple4d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple4f

public abstract class Tuple4d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Tuple4d(double d, double d1, double d2, double d3)
    {
        set(d, d1, d2, d3);
    }

    public Tuple4d(double ad[])
    {
        set(ad);
    }

    public Tuple4d(com.maddox.JGP.Tuple4d tuple4d)
    {
        set(tuple4d);
    }

    public Tuple4d(com.maddox.JGP.Tuple4f tuple4f)
    {
        set(tuple4f);
    }

    public Tuple4d()
    {
        x = 0.0D;
        y = 0.0D;
        z = 0.0D;
        w = 0.0D;
    }

    public final void set(double d, double d1, double d2, double d3)
    {
        x = d;
        y = d1;
        z = d2;
        w = d3;
    }

    public final void set(double ad[])
    {
        x = ad[0];
        y = ad[1];
        z = ad[2];
        w = ad[3];
    }

    public final void set(com.maddox.JGP.Tuple4d tuple4d)
    {
        x = tuple4d.x;
        y = tuple4d.y;
        z = tuple4d.z;
        w = tuple4d.w;
    }

    public final void set(com.maddox.JGP.Tuple4f tuple4f)
    {
        x = tuple4f.x;
        y = tuple4f.y;
        z = tuple4f.z;
        w = tuple4f.w;
    }

    public final void get(double ad[])
    {
        ad[0] = x;
        ad[1] = y;
        ad[2] = z;
        ad[3] = w;
    }

    public final void get(com.maddox.JGP.Tuple4d tuple4d)
    {
        tuple4d.x = x;
        tuple4d.y = y;
        tuple4d.z = z;
        tuple4d.w = w;
    }

    public final void add(com.maddox.JGP.Tuple4d tuple4d, com.maddox.JGP.Tuple4d tuple4d1)
    {
        x = tuple4d.x + tuple4d1.x;
        y = tuple4d.y + tuple4d1.y;
        z = tuple4d.z + tuple4d1.z;
        w = tuple4d.w + tuple4d1.w;
    }

    public final void add(com.maddox.JGP.Tuple4d tuple4d)
    {
        x += tuple4d.x;
        y += tuple4d.y;
        z += tuple4d.z;
        w += tuple4d.w;
    }

    public final void sub(com.maddox.JGP.Tuple4d tuple4d, com.maddox.JGP.Tuple4d tuple4d1)
    {
        x = tuple4d.x - tuple4d1.x;
        y = tuple4d.y - tuple4d1.y;
        z = tuple4d.z - tuple4d1.z;
        w = tuple4d.z - tuple4d1.w;
    }

    public final void sub(com.maddox.JGP.Tuple4d tuple4d)
    {
        x -= tuple4d.x;
        y -= tuple4d.y;
        z -= tuple4d.z;
        w -= tuple4d.w;
    }

    public final void negate(com.maddox.JGP.Tuple4d tuple4d)
    {
        x = -tuple4d.x;
        y = -tuple4d.y;
        z = -tuple4d.z;
        w = -tuple4d.w;
    }

    public final void negate()
    {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
    }

    public final void scale(double d, com.maddox.JGP.Tuple4d tuple4d)
    {
        x = d * tuple4d.x;
        y = d * tuple4d.y;
        z = d * tuple4d.z;
        w = d * tuple4d.w;
    }

    public final void scale(double d)
    {
        x *= d;
        y *= d;
        z *= d;
        w *= d;
    }

    public final void scaleAdd(double d, com.maddox.JGP.Tuple4d tuple4d, com.maddox.JGP.Tuple4d tuple4d1)
    {
        x = d * tuple4d.x + tuple4d1.x;
        y = d * tuple4d.y + tuple4d1.y;
        z = d * tuple4d.z + tuple4d1.z;
        w = d * tuple4d.w + tuple4d1.w;
    }

    public final void scaleAdd(double d, com.maddox.JGP.Tuple4d tuple4d)
    {
        x = d * x + tuple4d.x;
        y = d * y + tuple4d.y;
        z = d * z + tuple4d.z;
        w = d * z + tuple4d.w;
    }

    public int hashCode()
    {
        long l = java.lang.Double.doubleToLongBits(x);
        long l1 = java.lang.Double.doubleToLongBits(y);
        long l2 = java.lang.Double.doubleToLongBits(z);
        long l3 = java.lang.Double.doubleToLongBits(w);
        return (int)(l ^ l >> 32 ^ l1 ^ l1 >> 32 ^ l2 ^ l2 >> 32 ^ l3 ^ l3 >> 32);
    }

    public boolean equals(com.maddox.JGP.Tuple4d tuple4d)
    {
        return tuple4d != null && x == tuple4d.x && y == tuple4d.y && z == tuple4d.z && w == tuple4d.w;
    }

    public boolean epsilonEquals(com.maddox.JGP.Tuple4d tuple4d, double d)
    {
        return java.lang.Math.abs(tuple4d.x - x) <= d && java.lang.Math.abs(tuple4d.y - y) <= d && java.lang.Math.abs(tuple4d.z - z) <= d && java.lang.Math.abs(tuple4d.w - w) <= d;
    }

    public java.lang.String toString()
    {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }

    public final void clamp(double d, double d1, com.maddox.JGP.Tuple4d tuple4d)
    {
        set(tuple4d);
        clamp(d, d1);
    }

    public final void clampMin(double d, com.maddox.JGP.Tuple4d tuple4d)
    {
        set(tuple4d);
        clampMin(d);
    }

    public final void clampMax(double d, com.maddox.JGP.Tuple4d tuple4d)
    {
        set(tuple4d);
        clampMax(d);
    }

    public final void absolute(com.maddox.JGP.Tuple4d tuple4d)
    {
        set(tuple4d);
        absolute();
    }

    public final void clamp(double d, double d1)
    {
        clampMin(d);
        clampMax(d1);
    }

    public final void clampMin(double d)
    {
        if(x < d)
            x = d;
        if(y < d)
            y = d;
        if(z < d)
            z = d;
        if(w < d)
            w = d;
    }

    public final void clampMax(double d)
    {
        if(x > d)
            x = d;
        if(y > d)
            y = d;
        if(z > d)
            z = d;
        if(w > d)
            w = d;
    }

    public final void absolute()
    {
        if(x < 0.0D)
            x = -x;
        if(y < 0.0D)
            y = -y;
        if(z < 0.0D)
            z = -z;
        if(w < 0.0D)
            w = -w;
    }

    public final void interpolate(com.maddox.JGP.Tuple4d tuple4d, com.maddox.JGP.Tuple4d tuple4d1, double d)
    {
        x = tuple4d.x + (tuple4d1.x - tuple4d.x) * d;
        y = tuple4d.y + (tuple4d1.y - tuple4d.y) * d;
        z = tuple4d.z + (tuple4d1.z - tuple4d.z) * d;
        w = tuple4d.w + (tuple4d1.w - tuple4d.w) * d;
    }

    public final void interpolate(com.maddox.JGP.Tuple4d tuple4d, double d)
    {
        x += d * (tuple4d.x - x);
        y += d * (tuple4d.y - y);
        z += d * (tuple4d.z - z);
        w += d * (tuple4d.w - w);
    }

    public double x;
    public double y;
    public double z;
    public double w;
}
