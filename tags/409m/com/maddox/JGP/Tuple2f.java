// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Tuple2f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple2d, Tuple3f

public abstract class Tuple2f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Tuple2f(float f, float f1)
    {
        x = f;
        y = f1;
    }

    public Tuple2f(float af[])
    {
        x = af[0];
        y = af[1];
    }

    public Tuple2f(com.maddox.JGP.Tuple2f tuple2f)
    {
        x = tuple2f.x;
        y = tuple2f.y;
    }

    public Tuple2f(com.maddox.JGP.Tuple2d tuple2d)
    {
        x = (float)tuple2d.x;
        y = (float)tuple2d.y;
    }

    public Tuple2f()
    {
        x = 0.0F;
        y = 0.0F;
    }

    public final void set(float f, float f1)
    {
        x = f;
        y = f1;
    }

    public final void set(float af[])
    {
        x = af[0];
        y = af[1];
    }

    public final void set(com.maddox.JGP.Tuple2f tuple2f)
    {
        x = tuple2f.x;
        y = tuple2f.y;
    }

    public final void set(com.maddox.JGP.Tuple3f tuple3f)
    {
        x = tuple3f.x;
        y = tuple3f.y;
    }

    public final void set(com.maddox.JGP.Tuple2d tuple2d)
    {
        x = (float)tuple2d.x;
        y = (float)tuple2d.y;
    }

    public final void get(float af[])
    {
        af[0] = x;
        af[1] = y;
    }

    public final void add(com.maddox.JGP.Tuple2f tuple2f, com.maddox.JGP.Tuple2f tuple2f1)
    {
        x = tuple2f.x + tuple2f1.x;
        y = tuple2f.y + tuple2f1.y;
    }

    public final void add(float f, float f1)
    {
        x += f;
        y += f1;
    }

    public final void add(com.maddox.JGP.Tuple2f tuple2f)
    {
        x += tuple2f.x;
        y += tuple2f.y;
    }

    public final void sub(com.maddox.JGP.Tuple2f tuple2f, com.maddox.JGP.Tuple2f tuple2f1)
    {
        x = tuple2f.x - tuple2f1.x;
        y = tuple2f.y - tuple2f1.y;
    }

    public final void sub(float f, float f1)
    {
        x -= f;
        y -= f1;
    }

    public final void sub(com.maddox.JGP.Tuple2f tuple2f)
    {
        x -= tuple2f.x;
        y -= tuple2f.y;
    }

    public final void negate(com.maddox.JGP.Tuple2f tuple2f)
    {
        x = -tuple2f.x;
        y = -tuple2f.y;
    }

    public final void negate()
    {
        x = -x;
        y = -y;
    }

    public final void scale(float f, com.maddox.JGP.Tuple2f tuple2f)
    {
        x = f * tuple2f.x;
        y = f * tuple2f.y;
    }

    public final void scale(float f)
    {
        x *= f;
        y *= f;
    }

    public final void scaleAdd(float f, com.maddox.JGP.Tuple2f tuple2f, com.maddox.JGP.Tuple2f tuple2f1)
    {
        x = f * tuple2f.x + tuple2f1.x;
        y = f * tuple2f.y + tuple2f1.y;
    }

    public final void scaleAdd(float f, com.maddox.JGP.Tuple2f tuple2f)
    {
        x = f * x + tuple2f.x;
        y = f * y + tuple2f.y;
    }

    public int hashCode()
    {
        int i = java.lang.Float.floatToIntBits(x);
        int j = java.lang.Float.floatToIntBits(y);
        return i ^ j;
    }

    public boolean equals(com.maddox.JGP.Tuple2f tuple2f)
    {
        return tuple2f != null && x == tuple2f.x && y == tuple2f.y;
    }

    public boolean equals(java.lang.Object obj)
    {
        return obj != null && (obj instanceof com.maddox.JGP.Tuple2f) && equals((com.maddox.JGP.Tuple2f)obj);
    }

    public boolean epsilonEquals(com.maddox.JGP.Tuple2f tuple2f, float f)
    {
        return java.lang.Math.abs(tuple2f.x - x) <= f && java.lang.Math.abs(tuple2f.y - y) <= f;
    }

    public java.lang.String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    public final void clamp(float f, float f1, com.maddox.JGP.Tuple2f tuple2f)
    {
        set(tuple2f);
        clamp(f, f1);
    }

    public final void clampMin(float f, com.maddox.JGP.Tuple2f tuple2f)
    {
        set(tuple2f);
        clampMin(f);
    }

    public final void clampMax(float f, com.maddox.JGP.Tuple2f tuple2f)
    {
        set(tuple2f);
        clampMax(f);
    }

    public final void absolute(com.maddox.JGP.Tuple2f tuple2f)
    {
        set(tuple2f);
        absolute();
    }

    public final void clamp(float f, float f1)
    {
        clampMin(f);
        clampMax(f1);
    }

    public final void clampMin(float f)
    {
        if(x < f)
            x = f;
        if(y < f)
            y = f;
    }

    public final void clampMax(float f)
    {
        if(x > f)
            x = f;
        if(y > f)
            y = f;
    }

    public final void absolute()
    {
        if((double)x < 0.0D)
            x = -x;
        if((double)y < 0.0D)
            y = -y;
    }

    public final void interpolate(com.maddox.JGP.Tuple2f tuple2f, com.maddox.JGP.Tuple2f tuple2f1, float f)
    {
        x = tuple2f.x + (tuple2f1.x - tuple2f.x) * f;
        y = tuple2f.y + (tuple2f1.y - tuple2f.y) * f;
    }

    public final void interpolate(com.maddox.JGP.Tuple2f tuple2f, float f)
    {
        x += f * (tuple2f.x - x);
        y += f * (tuple2f.y - y);
    }

    public float x;
    public float y;
}
