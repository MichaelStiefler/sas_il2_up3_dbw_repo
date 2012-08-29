// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Tuple3d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple3f, Tuple2d

public abstract class Tuple3d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Tuple3d(double d, double d1, double d2)
    {
        x = d;
        y = d1;
        z = d2;
    }

    public Tuple3d(double ad[])
    {
        x = ad[0];
        y = ad[1];
        z = ad[2];
    }

    public Tuple3d(com.maddox.JGP.Tuple3d tuple3d)
    {
        x = tuple3d.x;
        y = tuple3d.y;
        z = tuple3d.z;
    }

    public Tuple3d(com.maddox.JGP.Tuple3f tuple3f)
    {
        x = tuple3f.x;
        y = tuple3f.y;
        z = tuple3f.z;
    }

    public Tuple3d()
    {
        x = 0.0D;
        y = 0.0D;
        z = 0.0D;
    }

    public final void set(double d, double d1, double d2)
    {
        x = d;
        y = d1;
        z = d2;
    }

    public final void set(double ad[])
    {
        x = ad[0];
        y = ad[1];
        z = ad[2];
    }

    public final void set(com.maddox.JGP.Tuple3d tuple3d)
    {
        x = tuple3d.x;
        y = tuple3d.y;
        z = tuple3d.z;
    }

    public final void set(com.maddox.JGP.Tuple3f tuple3f)
    {
        x = tuple3f.x;
        y = tuple3f.y;
        z = tuple3f.z;
    }

    public final void set(com.maddox.JGP.Tuple2d tuple2d)
    {
        x = tuple2d.x;
        y = tuple2d.y;
        z = 0.0D;
    }

    public final void set2(com.maddox.JGP.Tuple2d tuple2d)
    {
        x = tuple2d.x;
        y = tuple2d.y;
    }

    public final void get(double ad[])
    {
        ad[0] = x;
        ad[1] = y;
        ad[2] = z;
    }

    public final void get(com.maddox.JGP.Tuple3d tuple3d)
    {
        tuple3d.x = x;
        tuple3d.y = y;
        tuple3d.z = z;
    }

    public final void add(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1)
    {
        x = tuple3d.x + tuple3d1.x;
        y = tuple3d.y + tuple3d1.y;
        z = tuple3d.z + tuple3d1.z;
    }

    public final void add(com.maddox.JGP.Tuple3d tuple3d)
    {
        x += tuple3d.x;
        y += tuple3d.y;
        z += tuple3d.z;
    }

    public final void add(double d, double d1, double d2)
    {
        x += d;
        y += d1;
        z += d2;
    }

    public final void sub(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1)
    {
        x = tuple3d.x - tuple3d1.x;
        y = tuple3d.y - tuple3d1.y;
        z = tuple3d.z - tuple3d1.z;
    }

    public final void sub(com.maddox.JGP.Tuple3d tuple3d)
    {
        x -= tuple3d.x;
        y -= tuple3d.y;
        z -= tuple3d.z;
    }

    public final void sub(double d, double d1, double d2)
    {
        x -= d;
        y -= d1;
        z -= d2;
    }

    public final void negate(com.maddox.JGP.Tuple3d tuple3d)
    {
        x = -tuple3d.x;
        y = -tuple3d.y;
        z = -tuple3d.z;
    }

    public final void negate()
    {
        x = -x;
        y = -y;
        z = -z;
    }

    public final void scale(double d, com.maddox.JGP.Tuple3d tuple3d)
    {
        x = d * tuple3d.x;
        y = d * tuple3d.y;
        z = d * tuple3d.z;
    }

    public final void scale(com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        x = d * tuple3d.x;
        y = d * tuple3d.y;
        z = d * tuple3d.z;
    }

    public final void scale(double d)
    {
        x *= d;
        y *= d;
        z *= d;
    }

    public final void scaleAdd(double d, com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1)
    {
        x = d * tuple3d.x + tuple3d1.x;
        y = d * tuple3d.y + tuple3d1.y;
        z = d * tuple3d.z + tuple3d1.z;
    }

    public final void scaleAdd(double d, com.maddox.JGP.Tuple3d tuple3d)
    {
        x = d * x + tuple3d.x;
        y = d * y + tuple3d.y;
        z = d * z + tuple3d.z;
    }

    public int hashCode()
    {
        long l = java.lang.Double.doubleToLongBits(x);
        long l1 = java.lang.Double.doubleToLongBits(y);
        long l2 = java.lang.Double.doubleToLongBits(z);
        return (int)(l ^ l >> 32 ^ l1 ^ l1 >> 32 ^ l2 ^ l2 >> 32);
    }

    public boolean equals(com.maddox.JGP.Tuple3d tuple3d)
    {
        return tuple3d != null && x == tuple3d.x && y == tuple3d.y && z == tuple3d.z;
    }

    public boolean epsilonEquals(com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        return java.lang.Math.abs(tuple3d.x - x) <= d && java.lang.Math.abs(tuple3d.y - y) <= d && java.lang.Math.abs(tuple3d.z - z) <= d;
    }

    public java.lang.String toString()
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public final void absolute(com.maddox.JGP.Tuple3d tuple3d)
    {
        set(tuple3d);
        absolute();
    }

    public final void absolute()
    {
        if(x < 0.0D)
            x = -x;
        if(y < 0.0D)
            y = -y;
        if(z < 0.0D)
            z = -z;
    }

    /**
     * @deprecated Method clamp is deprecated
     */

    public final void clamp(float f, float f1)
    {
        clampMin(f);
        clampMax(f1);
    }

    /**
     * @deprecated Method clamp is deprecated
     */

    public final void clamp(float f, float f1, com.maddox.JGP.Tuple3d tuple3d)
    {
        set(tuple3d);
        clamp(f, f1);
    }

    /**
     * @deprecated Method clampMin is deprecated
     */

    public final void clampMin(float f)
    {
        if(x < (double)f)
            x = f;
        if(y < (double)f)
            y = f;
        if(z < (double)f)
            z = f;
    }

    /**
     * @deprecated Method clampMin is deprecated
     */

    public final void clampMin(float f, com.maddox.JGP.Tuple3d tuple3d)
    {
        set(tuple3d);
        clampMin(f);
    }

    /**
     * @deprecated Method clampMax is deprecated
     */

    public final void clampMax(float f, com.maddox.JGP.Tuple3d tuple3d)
    {
        set(tuple3d);
        clampMax(f);
    }

    /**
     * @deprecated Method clampMax is deprecated
     */

    public final void clampMax(float f)
    {
        if(x > (double)f)
            x = f;
        if(y > (double)f)
            y = f;
        if(z > (double)f)
            z = f;
    }

    public final void clamp(double d, double d1)
    {
        clampMin(d);
        clampMax(d1);
    }

    public final void clamp(double d, double d1, com.maddox.JGP.Tuple3d tuple3d)
    {
        set(tuple3d);
        clamp(d, d1);
    }

    public final void clampMin(double d)
    {
        if(x < d)
            x = d;
        if(y < d)
            y = d;
        if(z < d)
            z = d;
    }

    public final void clampMin(double d, com.maddox.JGP.Tuple3d tuple3d)
    {
        set(tuple3d);
        clampMin(d);
    }

    public final void clampMax(double d, com.maddox.JGP.Tuple3d tuple3d)
    {
        set(tuple3d);
        clampMax(d);
    }

    public final void clampMax(double d)
    {
        if(x > d)
            x = d;
        if(y > d)
            y = d;
        if(z > d)
            z = d;
    }

    public final void interpolate(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1, float f)
    {
        set(tuple3d);
        interpolate(tuple3d1, f);
    }

    public final void interpolate(com.maddox.JGP.Tuple3d tuple3d, float f)
    {
        x += (double)f * (tuple3d.x - x);
        y += (double)f * (tuple3d.y - y);
        z += (double)f * (tuple3d.z - z);
    }

    public final void interpolate(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1, double d)
    {
        x = tuple3d.x + (tuple3d1.x - tuple3d.x) * d;
        y = tuple3d.y + (tuple3d1.y - tuple3d.y) * d;
        z = tuple3d.z + (tuple3d1.z - tuple3d.z) * d;
    }

    public final void interpolate(com.maddox.JGP.Tuple3d tuple3d, double d)
    {
        x += d * (tuple3d.x - x);
        y += d * (tuple3d.y - y);
        z += d * (tuple3d.z - z);
    }

    public double x;
    public double y;
    public double z;
}
