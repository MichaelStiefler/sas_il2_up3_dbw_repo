// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Vector2d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple2d, Tuple2f

public class Vector2d extends com.maddox.JGP.Tuple2d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Vector2d(double d, double d1)
    {
        super(d, d1);
    }

    public Vector2d(double ad[])
    {
        super(ad);
    }

    public Vector2d(com.maddox.JGP.Tuple2d tuple2d)
    {
        super(tuple2d);
    }

    public Vector2d(com.maddox.JGP.Tuple2f tuple2f)
    {
        super(tuple2f);
    }

    public Vector2d()
    {
    }

    public final double dot(com.maddox.JGP.Tuple2d tuple2d)
    {
        return x * tuple2d.x + y * tuple2d.y;
    }

    public final double length()
    {
        return java.lang.Math.sqrt(x * x + y * y);
    }

    public final double lengthSquared()
    {
        return x * x + y * y;
    }

    public final void normalize()
    {
        double d = length();
        x /= d;
        y /= d;
    }

    public final void normalize(com.maddox.JGP.Tuple2d tuple2d)
    {
        set(tuple2d);
        normalize();
    }

    public final double angle(com.maddox.JGP.Vector2d vector2d)
    {
        return java.lang.Math.abs(java.lang.Math.atan2(x * vector2d.y - y * vector2d.x, dot(vector2d)));
    }

    public final double direction()
    {
        double d = java.lang.Math.atan2(y, x);
        if(d < 0.0D)
            d += 6.2831853071795862D;
        return d;
    }
}
