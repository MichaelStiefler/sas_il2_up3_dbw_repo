// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Tuple2d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple2f, Tuple3d

public abstract class Tuple2d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Tuple2d(double d, double d1)
    {
        x = d;
        y = d1;
    }

    public Tuple2d(double ad[])
    {
        x = ad[0];
        y = ad[1];
    }

    public Tuple2d(com.maddox.JGP.Tuple2d tuple2d)
    {
        x = tuple2d.x;
        y = tuple2d.y;
    }

    public Tuple2d(com.maddox.JGP.Tuple2f tuple2f)
    {
        x = tuple2f.x;
        y = tuple2f.y;
    }

    public Tuple2d()
    {
        x = 0.0D;
        y = 0.0D;
    }

    public final void set(double d, double d1)
    {
        x = d;
        y = d1;
    }

    public final void set(double ad[])
    {
        x = ad[0];
        y = ad[1];
    }

    public final void set(com.maddox.JGP.Tuple2d tuple2d)
    {
        x = tuple2d.x;
        y = tuple2d.y;
    }

    public final void set(com.maddox.JGP.Tuple2f tuple2f)
    {
        x = tuple2f.x;
        y = tuple2f.y;
    }

    public final void set(com.maddox.JGP.Tuple3d tuple3d)
    {
        x = tuple3d.x;
        y = tuple3d.y;
    }

    public final void get(double ad[])
    {
        ad[0] = x;
        ad[1] = y;
    }

    public final void add(com.maddox.JGP.Tuple2d tuple2d, com.maddox.JGP.Tuple2d tuple2d1)
    {
        x = tuple2d.x + tuple2d1.x;
        y = tuple2d.y + tuple2d1.y;
    }

    public final void add(double d, double d1)
    {
        x += d;
        y += d1;
    }

    public final void add(com.maddox.JGP.Tuple2d tuple2d)
    {
        x += tuple2d.x;
        y += tuple2d.y;
    }

    public final void sub(com.maddox.JGP.Tuple2d tuple2d, com.maddox.JGP.Tuple2d tuple2d1)
    {
        x = tuple2d.x - tuple2d1.x;
        y = tuple2d.y - tuple2d1.y;
    }

    public final void sub(double d, double d1)
    {
        x -= d;
        y -= d1;
    }

    public final void sub(com.maddox.JGP.Tuple2d tuple2d)
    {
        x -= tuple2d.x;
        y -= tuple2d.y;
    }

    public final void negate(com.maddox.JGP.Tuple2d tuple2d)
    {
        x = -tuple2d.x;
        y = -tuple2d.y;
    }

    public final void negate()
    {
        x = -x;
        y = -y;
    }

    public final void scale(double d, com.maddox.JGP.Tuple2d tuple2d)
    {
        x = d * tuple2d.x;
        y = d * tuple2d.y;
    }

    public final void scale(double d)
    {
        x *= d;
        y *= d;
    }

    public final void scaleAdd(double d, com.maddox.JGP.Tuple2d tuple2d, com.maddox.JGP.Tuple2d tuple2d1)
    {
        x = d * tuple2d.x + tuple2d1.x;
        y = d * tuple2d.y + tuple2d1.y;
    }

    public final void scaleAdd(double d, com.maddox.JGP.Tuple2d tuple2d)
    {
        x = d * x + tuple2d.x;
        y = d * y + tuple2d.y;
    }

    public int hashCode()
    {
        long l = java.lang.Double.doubleToLongBits(x);
        long l1 = java.lang.Double.doubleToLongBits(y);
        return (int)(l ^ l >> 32 ^ l1 ^ l1 >> 32);
    }

    public boolean equals(com.maddox.JGP.Tuple2d tuple2d)
    {
        return tuple2d != null && x == tuple2d.x && y == tuple2d.y;
    }

    public boolean equals(java.lang.Object obj)
    {
        return obj != null && (obj instanceof com.maddox.JGP.Tuple2d) && equals((com.maddox.JGP.Tuple2d)obj);
    }

    public boolean epsilonEquals(com.maddox.JGP.Tuple2d tuple2d, double d)
    {
        return java.lang.Math.abs(tuple2d.x - x) <= d && java.lang.Math.abs(tuple2d.y - y) <= d;
    }

    public java.lang.String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    public final void clamp(double d, double d1, com.maddox.JGP.Tuple2d tuple2d)
    {
        set(tuple2d);
        clamp(d, d1);
    }

    public final void clampMin(double d, com.maddox.JGP.Tuple2d tuple2d)
    {
        set(tuple2d);
        clampMin(d);
    }

    public final void clampMax(double d, com.maddox.JGP.Tuple2d tuple2d)
    {
        set(tuple2d);
        clampMax(d);
    }

    public final void absolute(com.maddox.JGP.Tuple2d tuple2d)
    {
        set(tuple2d);
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
    }

    public final void clampMax(double d)
    {
        if(x > d)
            x = d;
        if(y > d)
            y = d;
    }

    public final void absolute()
    {
        if(x < 0.0D)
            x = -x;
        if(y < 0.0D)
            y = -y;
    }

    public final void interpolate(com.maddox.JGP.Tuple2d tuple2d, com.maddox.JGP.Tuple2d tuple2d1, double d)
    {
        x = tuple2d.x + (tuple2d1.x - tuple2d.x) * d;
        y = tuple2d.y + (tuple2d1.y - tuple2d.y) * d;
    }

    public final void interpolate(com.maddox.JGP.Tuple2d tuple2d, double d)
    {
        x += d * (tuple2d.x - x);
        y += d * (tuple2d.y - y);
    }

    public double x;
    public double y;
}
