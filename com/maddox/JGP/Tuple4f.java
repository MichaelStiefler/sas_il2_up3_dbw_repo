// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Tuple4f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple4d

public abstract class Tuple4f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Tuple4f(float f, float f1, float f2, float f3)
    {
        set(f, f1, f2, f3);
    }

    public Tuple4f(float af[])
    {
        set(af);
    }

    public Tuple4f(com.maddox.JGP.Tuple4f tuple4f)
    {
        set(tuple4f);
    }

    public Tuple4f(com.maddox.JGP.Tuple4d tuple4d)
    {
        set(tuple4d);
    }

    public Tuple4f()
    {
        x = 0.0F;
        y = 0.0F;
        z = 0.0F;
        w = 0.0F;
    }

    public final void set(float f, float f1, float f2, float f3)
    {
        x = f;
        y = f1;
        z = f2;
        w = f3;
    }

    public final void set(float af[])
    {
        x = af[0];
        y = af[1];
        z = af[2];
        w = af[3];
    }

    public final void set(com.maddox.JGP.Tuple4f tuple4f)
    {
        x = tuple4f.x;
        y = tuple4f.y;
        z = tuple4f.z;
        w = tuple4f.w;
    }

    public final void set(com.maddox.JGP.Tuple4d tuple4d)
    {
        x = (float)tuple4d.x;
        y = (float)tuple4d.y;
        z = (float)tuple4d.z;
        w = (float)tuple4d.w;
    }

    public final void get(float af[])
    {
        af[0] = x;
        af[1] = y;
        af[2] = z;
        af[3] = w;
    }

    public final void get(com.maddox.JGP.Tuple4f tuple4f)
    {
        tuple4f.x = x;
        tuple4f.y = y;
        tuple4f.z = z;
        tuple4f.w = w;
    }

    public final void add(com.maddox.JGP.Tuple4f tuple4f, com.maddox.JGP.Tuple4f tuple4f1)
    {
        x = tuple4f.x + tuple4f1.x;
        y = tuple4f.y + tuple4f1.y;
        z = tuple4f.z + tuple4f1.z;
        w = tuple4f.w + tuple4f1.w;
    }

    public final void add(com.maddox.JGP.Tuple4f tuple4f)
    {
        x += tuple4f.x;
        y += tuple4f.y;
        z += tuple4f.z;
        w += tuple4f.w;
    }

    public final void sub(com.maddox.JGP.Tuple4f tuple4f, com.maddox.JGP.Tuple4f tuple4f1)
    {
        x = tuple4f.x - tuple4f1.x;
        y = tuple4f.y - tuple4f1.y;
        z = tuple4f.z - tuple4f1.z;
        w = tuple4f.z - tuple4f1.w;
    }

    public final void sub(com.maddox.JGP.Tuple4f tuple4f)
    {
        x -= tuple4f.x;
        y -= tuple4f.y;
        z -= tuple4f.z;
        w -= tuple4f.w;
    }

    public final void negate(com.maddox.JGP.Tuple4f tuple4f)
    {
        x = -tuple4f.x;
        y = -tuple4f.y;
        z = -tuple4f.z;
        w = -tuple4f.w;
    }

    public final void negate()
    {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
    }

    public final void scale(float f, com.maddox.JGP.Tuple4f tuple4f)
    {
        x = f * tuple4f.x;
        y = f * tuple4f.y;
        z = f * tuple4f.z;
        w = f * tuple4f.w;
    }

    public final void scale(float f)
    {
        x *= f;
        y *= f;
        z *= f;
        w *= f;
    }

    public final void scaleAdd(float f, com.maddox.JGP.Tuple4f tuple4f, com.maddox.JGP.Tuple4f tuple4f1)
    {
        x = f * tuple4f.x + tuple4f1.x;
        y = f * tuple4f.y + tuple4f1.y;
        z = f * tuple4f.z + tuple4f1.z;
        w = f * tuple4f.w + tuple4f1.w;
    }

    public final void scaleAdd(float f, com.maddox.JGP.Tuple4f tuple4f)
    {
        x = f * x + tuple4f.x;
        y = f * y + tuple4f.y;
        z = f * z + tuple4f.z;
        w = f * z + tuple4f.w;
    }

    public int hashCode()
    {
        return java.lang.Float.floatToIntBits(x) ^ java.lang.Float.floatToIntBits(y) ^ java.lang.Float.floatToIntBits(z) ^ java.lang.Float.floatToIntBits(w);
    }

    public boolean equals(com.maddox.JGP.Tuple4f tuple4f)
    {
        return tuple4f != null && x == tuple4f.x && y == tuple4f.y && z == tuple4f.z && w == tuple4f.w;
    }

    public boolean epsilonEquals(com.maddox.JGP.Tuple4f tuple4f, float f)
    {
        return java.lang.Math.abs(tuple4f.x - x) <= f && java.lang.Math.abs(tuple4f.y - y) <= f && java.lang.Math.abs(tuple4f.z - z) <= f && java.lang.Math.abs(tuple4f.w - w) <= f;
    }

    public java.lang.String toString()
    {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }

    public final void clamp(float f, float f1, com.maddox.JGP.Tuple4f tuple4f)
    {
        set(tuple4f);
        clamp(f, f1);
    }

    public final void clampMin(float f, com.maddox.JGP.Tuple4f tuple4f)
    {
        set(tuple4f);
        clampMin(f);
    }

    public final void clampMax(float f, com.maddox.JGP.Tuple4f tuple4f)
    {
        set(tuple4f);
        clampMax(f);
    }

    public final void absolute(com.maddox.JGP.Tuple4f tuple4f)
    {
        set(tuple4f);
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
        if(z < f)
            z = f;
        if(w < f)
            w = f;
    }

    public final void clampMax(float f)
    {
        if(x > f)
            x = f;
        if(y > f)
            y = f;
        if(z > f)
            z = f;
        if(w > f)
            w = f;
    }

    public final void absolute()
    {
        if((double)x < 0.0D)
            x = -x;
        if((double)y < 0.0D)
            y = -y;
        if((double)z < 0.0D)
            z = -z;
        if((double)w < 0.0D)
            w = -w;
    }

    public final void interpolate(com.maddox.JGP.Tuple4f tuple4f, com.maddox.JGP.Tuple4f tuple4f1, float f)
    {
        x = tuple4f.x + (tuple4f1.x - tuple4f.x) * f;
        y = tuple4f.y + (tuple4f1.y - tuple4f.y) * f;
        z = tuple4f.z + (tuple4f1.z - tuple4f.z) * f;
        w = tuple4f.w + (tuple4f1.w - tuple4f.w) * f;
    }

    public final void interpolate(com.maddox.JGP.Tuple4f tuple4f, float f)
    {
        x += f * (tuple4f.x - x);
        y += f * (tuple4f.y - y);
        z += f * (tuple4f.z - z);
        w += f * (tuple4f.w - w);
    }

    public float x;
    public float y;
    public float z;
    public float w;
}
