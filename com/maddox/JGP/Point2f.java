// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Point2f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple2f, Point2d, Tuple2d

public class Point2f extends com.maddox.JGP.Tuple2f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Point2f(float f, float f1)
    {
        super(f, f1);
    }

    public Point2f(float af[])
    {
        super(af);
    }

    public Point2f(com.maddox.JGP.Point2f point2f)
    {
        super(point2f);
    }

    public Point2f(com.maddox.JGP.Point2d point2d)
    {
        super(point2d);
    }

    public Point2f(com.maddox.JGP.Tuple2f tuple2f)
    {
        super(tuple2f);
    }

    public Point2f(com.maddox.JGP.Tuple2d tuple2d)
    {
        super(tuple2d);
    }

    public Point2f()
    {
    }

    public final float distanceSquared(com.maddox.JGP.Point2f point2f)
    {
        double d = x - point2f.x;
        double d1 = y - point2f.y;
        return (float)(d * d + d1 * d1);
    }

    public final float distance(com.maddox.JGP.Point2f point2f)
    {
        return (float)java.lang.Math.sqrt(distanceSquared(point2f));
    }

    public final float distanceL1(com.maddox.JGP.Point2f point2f)
    {
        return java.lang.Math.abs(x - point2f.x) + java.lang.Math.abs(y - point2f.y);
    }

    public final float distanceLinf(com.maddox.JGP.Point2f point2f)
    {
        return java.lang.Math.max(java.lang.Math.abs(x - point2f.x), java.lang.Math.abs(y - point2f.y));
    }
}
