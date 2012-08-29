// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Point2d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple2d, Point2f, Tuple2f

public class Point2d extends com.maddox.JGP.Tuple2d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Point2d(double d, double d1)
    {
        super(d, d1);
    }

    public Point2d(double ad[])
    {
        super(ad);
    }

    public Point2d(com.maddox.JGP.Point2d point2d)
    {
        super(point2d);
    }

    public Point2d(com.maddox.JGP.Point2f point2f)
    {
        super(point2f);
    }

    public Point2d(com.maddox.JGP.Tuple2d tuple2d)
    {
        super(tuple2d);
    }

    public Point2d(com.maddox.JGP.Tuple2f tuple2f)
    {
        super(tuple2f);
    }

    public Point2d()
    {
    }

    public final double distanceSquared(com.maddox.JGP.Point2d point2d)
    {
        double d = x - point2d.x;
        double d1 = y - point2d.y;
        return d * d + d1 * d1;
    }

    public final double distance(com.maddox.JGP.Point2d point2d)
    {
        return java.lang.Math.sqrt(distanceSquared(point2d));
    }

    public final double distanceL1(com.maddox.JGP.Point2d point2d)
    {
        return java.lang.Math.abs(x - point2d.x) + java.lang.Math.abs(y - point2d.y);
    }

    public final double distanceLinf(com.maddox.JGP.Point2d point2d)
    {
        return java.lang.Math.max(java.lang.Math.abs(x - point2d.x), java.lang.Math.abs(y - point2d.y));
    }
}
