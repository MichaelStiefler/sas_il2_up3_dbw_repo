// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Vector4d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple4d, Tuple4f

public class Vector4d extends com.maddox.JGP.Tuple4d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Vector4d(double d, double d1, double d2, double d3)
    {
        super(d, d1, d2, d3);
    }

    public Vector4d(double ad[])
    {
        super(ad);
    }

    public Vector4d(com.maddox.JGP.Tuple4d tuple4d)
    {
        super(tuple4d);
    }

    public Vector4d(com.maddox.JGP.Tuple4f tuple4f)
    {
        super(tuple4f);
    }

    public Vector4d()
    {
    }

    public final double lengthSquared()
    {
        return x * x + y * y + z * z + w * w;
    }

    public final double length()
    {
        return java.lang.Math.sqrt(lengthSquared());
    }

    public final double dot(com.maddox.JGP.Tuple4d tuple4d)
    {
        return x * tuple4d.x + y * tuple4d.y + z * tuple4d.z + w * tuple4d.w;
    }

    public final void normalize(com.maddox.JGP.Tuple4d tuple4d)
    {
        set(tuple4d);
        normalize();
    }

    public final void normalize()
    {
        double d = length();
        x /= d;
        y /= d;
        z /= d;
        w /= d;
    }

    public final double angle(com.maddox.JGP.Vector4d vector4d)
    {
        double d = dot(vector4d);
        double d1 = vector4d.length();
        double d2 = length();
        return java.lang.Math.acos(d / d1 / d2);
    }
}
