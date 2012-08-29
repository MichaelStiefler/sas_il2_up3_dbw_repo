// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Point4d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple4d, Point4f, Tuple4f

public class Point4d extends com.maddox.JGP.Tuple4d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Point4d(double d, double d1, double d2, double d3)
    {
        super(d, d1, d2, d3);
    }

    public Point4d(double ad[])
    {
        super(ad);
    }

    public Point4d(com.maddox.JGP.Point4f point4f)
    {
        super(point4f);
    }

    public Point4d(com.maddox.JGP.Point4d point4d)
    {
        super(point4d);
    }

    public Point4d(com.maddox.JGP.Tuple4d tuple4d)
    {
        super(tuple4d);
    }

    public Point4d(com.maddox.JGP.Tuple4f tuple4f)
    {
        super(tuple4f);
    }

    public Point4d()
    {
    }

    public final double distanceSquared(com.maddox.JGP.Point4d point4d)
    {
        double d = x - point4d.x;
        double d1 = y - point4d.y;
        double d2 = z - point4d.z;
        double d3 = z - point4d.w;
        return (double)(float)(d * d + d1 * d1 + d2 * d2 + d3 * d3);
    }

    public final double distance(com.maddox.JGP.Point4d point4d)
    {
        return java.lang.Math.sqrt(distanceSquared(point4d));
    }

    public final double distanceL1(com.maddox.JGP.Point4d point4d)
    {
        return java.lang.Math.abs(x - point4d.x) + java.lang.Math.abs(y - point4d.y) + java.lang.Math.abs(z - point4d.z) + java.lang.Math.abs(w - point4d.w);
    }

    public final double distanceLinf(com.maddox.JGP.Point4d point4d)
    {
        return java.lang.Math.max(java.lang.Math.max(java.lang.Math.abs(x - point4d.x), java.lang.Math.abs(y - point4d.y)), java.lang.Math.max(java.lang.Math.abs(z - point4d.z), java.lang.Math.abs(w - point4d.w)));
    }

    public final void project(com.maddox.JGP.Point4d point4d)
    {
        x = point4d.x / point4d.w;
        y = point4d.y / point4d.w;
        z = point4d.z / point4d.w;
        w = 1.0D;
    }
}
