// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Point4f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple4f, Point4d, Tuple4d

public class Point4f extends com.maddox.JGP.Tuple4f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Point4f(float f, float f1, float f2, float f3)
    {
        super(f, f1, f2, f3);
    }

    public Point4f(float af[])
    {
        super(af);
    }

    public Point4f(com.maddox.JGP.Point4f point4f)
    {
        super(point4f);
    }

    public Point4f(com.maddox.JGP.Point4d point4d)
    {
        super(point4d);
    }

    public Point4f(com.maddox.JGP.Tuple4d tuple4d)
    {
        super(tuple4d);
    }

    public Point4f(com.maddox.JGP.Tuple4f tuple4f)
    {
        super(tuple4f);
    }

    public Point4f()
    {
    }

    public final float distanceSquared(com.maddox.JGP.Point4f point4f)
    {
        double d = x - point4f.x;
        double d1 = y - point4f.y;
        double d2 = z - point4f.z;
        double d3 = z - point4f.w;
        return (float)(d * d + d1 * d1 + d2 * d2 + d3 * d3);
    }

    public final float distance(com.maddox.JGP.Point4f point4f)
    {
        return (float)java.lang.Math.sqrt(distanceSquared(point4f));
    }

    public final float distanceL1(com.maddox.JGP.Point4f point4f)
    {
        return java.lang.Math.abs(x - point4f.x) + java.lang.Math.abs(y - point4f.y) + java.lang.Math.abs(z - point4f.z) + java.lang.Math.abs(w - point4f.w);
    }

    public final float distanceLinf(com.maddox.JGP.Point4f point4f)
    {
        return java.lang.Math.max(java.lang.Math.max(java.lang.Math.abs(x - point4f.x), java.lang.Math.abs(y - point4f.y)), java.lang.Math.max(java.lang.Math.abs(z - point4f.z), java.lang.Math.abs(w - point4f.w)));
    }

    public final void project(com.maddox.JGP.Point4f point4f)
    {
        x = point4f.x / point4f.w;
        y = point4f.y / point4f.w;
        z = point4f.z / point4f.w;
        w = 1.0F;
    }
}
