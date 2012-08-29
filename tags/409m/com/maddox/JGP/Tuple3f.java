// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Tuple3f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple3d, Tuple2f

public abstract class Tuple3f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Tuple3f(float f, float f1, float f2)
    {
        x = f;
        y = f1;
        z = f2;
    }

    public Tuple3f(float af[])
    {
        x = af[0];
        y = af[1];
        z = af[2];
    }

    public Tuple3f(com.maddox.JGP.Tuple3f tuple3f)
    {
        x = tuple3f.x;
        y = tuple3f.y;
        z = tuple3f.z;
    }

    public Tuple3f(com.maddox.JGP.Tuple3d tuple3d)
    {
        x = (float)tuple3d.x;
        y = (float)tuple3d.y;
        z = (float)tuple3d.z;
    }

    public Tuple3f()
    {
        x = 0.0F;
        y = 0.0F;
        z = 0.0F;
    }

    public final void set(float f, float f1, float f2)
    {
        x = f;
        y = f1;
        z = f2;
    }

    public final void set(float af[])
    {
        x = af[0];
        y = af[1];
        z = af[2];
    }

    public final void set(com.maddox.JGP.Tuple3f tuple3f)
    {
        x = tuple3f.x;
        y = tuple3f.y;
        z = tuple3f.z;
    }

    public final void set(com.maddox.JGP.Tuple3d tuple3d)
    {
        x = (float)tuple3d.x;
        y = (float)tuple3d.y;
        z = (float)tuple3d.z;
    }

    public final void set(com.maddox.JGP.Tuple2f tuple2f)
    {
        x = tuple2f.x;
        y = tuple2f.y;
        z = 0.0F;
    }

    public final void set2(com.maddox.JGP.Tuple2f tuple2f)
    {
        x = tuple2f.x;
        y = tuple2f.y;
    }

    public final void get(float af[])
    {
        af[0] = x;
        af[1] = y;
        af[2] = z;
    }

    public final void get(com.maddox.JGP.Tuple3f tuple3f)
    {
        tuple3f.x = x;
        tuple3f.y = y;
        tuple3f.z = z;
    }

    public final void add(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        x = tuple3f.x + tuple3f1.x;
        y = tuple3f.y + tuple3f1.y;
        z = tuple3f.z + tuple3f1.z;
    }

    public final void add(com.maddox.JGP.Tuple3f tuple3f)
    {
        x += tuple3f.x;
        y += tuple3f.y;
        z += tuple3f.z;
    }

    public final void add(float f, float f1, float f2)
    {
        x += f;
        y += f1;
        z += f2;
    }

    public final void sub(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        x = tuple3f.x - tuple3f1.x;
        y = tuple3f.y - tuple3f1.y;
        z = tuple3f.z - tuple3f1.z;
    }

    public final void sub(com.maddox.JGP.Tuple3f tuple3f)
    {
        x -= tuple3f.x;
        y -= tuple3f.y;
        z -= tuple3f.z;
    }

    public final void sub(float f, float f1, float f2)
    {
        x -= f;
        y -= f1;
        z -= f2;
    }

    public final void negate(com.maddox.JGP.Tuple3f tuple3f)
    {
        x = -tuple3f.x;
        y = -tuple3f.y;
        z = -tuple3f.z;
    }

    public final void negate()
    {
        x = -x;
        y = -y;
        z = -z;
    }

    public final void scale(float f, com.maddox.JGP.Tuple3f tuple3f)
    {
        x = f * tuple3f.x;
        y = f * tuple3f.y;
        z = f * tuple3f.z;
    }

    public final void scale(com.maddox.JGP.Tuple3f tuple3f, float f)
    {
        x = f * tuple3f.x;
        y = f * tuple3f.y;
        z = f * tuple3f.z;
    }

    public final void scale(float f)
    {
        x *= f;
        y *= f;
        z *= f;
    }

    public final void scaleAdd(float f, com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        x = f * tuple3f.x + tuple3f1.x;
        y = f * tuple3f.y + tuple3f1.y;
        z = f * tuple3f.z + tuple3f1.z;
    }

    public final void scaleAdd(float f, com.maddox.JGP.Tuple3f tuple3f)
    {
        x = f * x + tuple3f.x;
        y = f * y + tuple3f.y;
        z = f * z + tuple3f.z;
    }

    public int hashCode()
    {
        int i = java.lang.Float.floatToIntBits(x);
        int j = java.lang.Float.floatToIntBits(y);
        int k = java.lang.Float.floatToIntBits(z);
        return i ^ j ^ k;
    }

    public boolean equals(com.maddox.JGP.Tuple3f tuple3f)
    {
        return tuple3f != null && x == tuple3f.x && y == tuple3f.y && z == tuple3f.z;
    }

    public boolean epsilonEquals(com.maddox.JGP.Tuple3f tuple3f, float f)
    {
        return java.lang.Math.abs(tuple3f.x - x) <= f && java.lang.Math.abs(tuple3f.y - y) <= f && java.lang.Math.abs(tuple3f.z - z) <= f;
    }

    public java.lang.String toString()
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public final void clamp(float f, float f1, com.maddox.JGP.Tuple3f tuple3f)
    {
        set(tuple3f);
        clamp(f, f1);
    }

    public final void clampMin(float f, com.maddox.JGP.Tuple3f tuple3f)
    {
        set(tuple3f);
        clampMin(f);
    }

    public final void clampMax(float f, com.maddox.JGP.Tuple3f tuple3f)
    {
        set(tuple3f);
        clampMax(f);
    }

    public final void absolute(com.maddox.JGP.Tuple3f tuple3f)
    {
        set(tuple3f);
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
    }

    public final void clampMax(float f)
    {
        if(x > f)
            x = f;
        if(y > f)
            y = f;
        if(z > f)
            z = f;
    }

    public final void absolute()
    {
        if((double)x < 0.0D)
            x = -x;
        if((double)y < 0.0D)
            y = -y;
        if((double)z < 0.0D)
            z = -z;
    }

    public final void interpolate(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1, float f)
    {
        x = tuple3f.x + (tuple3f1.x - tuple3f.x) * f;
        y = tuple3f.y + (tuple3f1.y - tuple3f.y) * f;
        z = tuple3f.z + (tuple3f1.z - tuple3f.z) * f;
    }

    public final void interpolate(com.maddox.JGP.Tuple3f tuple3f, float f)
    {
        x += (tuple3f.x - x) * f;
        y += (tuple3f.y - y) * f;
        z += (tuple3f.z - z) * f;
    }

    public float x;
    public float y;
    public float z;
}
