// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Point3d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple3d, Point4d, Point3f, Tuple3f

public class Point3d extends com.maddox.JGP.Tuple3d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Point3d(double d, double d1, double d2)
    {
        super(d, d1, d2);
    }

    public Point3d(double ad[])
    {
        super(ad);
    }

    public Point3d(com.maddox.JGP.Point3d point3d)
    {
        super(point3d);
    }

    public Point3d(com.maddox.JGP.Point3f point3f)
    {
        super(point3f);
    }

    public Point3d(com.maddox.JGP.Tuple3d tuple3d)
    {
        super(tuple3d);
    }

    public Point3d(com.maddox.JGP.Tuple3f tuple3f)
    {
        super(tuple3f);
    }

    public Point3d()
    {
    }

    public final double distanceSquared(com.maddox.JGP.Point3d point3d)
    {
        double d = x - point3d.x;
        double d1 = y - point3d.y;
        double d2 = z - point3d.z;
        return d * d + d1 * d1 + d2 * d2;
    }

    public final double distance(com.maddox.JGP.Point3d point3d)
    {
        return java.lang.Math.sqrt(distanceSquared(point3d));
    }

    public final double distanceL1(com.maddox.JGP.Point3d point3d)
    {
        return java.lang.Math.abs(x - point3d.x) + java.lang.Math.abs(y - point3d.y) + java.lang.Math.abs(z - point3d.z);
    }

    public final double distanceLinf(com.maddox.JGP.Point3d point3d)
    {
        return java.lang.Math.max(java.lang.Math.max(java.lang.Math.abs(x - point3d.x), java.lang.Math.abs(y - point3d.y)), java.lang.Math.abs(z - point3d.z));
    }

    public final void project(com.maddox.JGP.Point4d point4d)
    {
        x = point4d.x / point4d.w;
        y = point4d.y / point4d.w;
        z = point4d.z / point4d.w;
    }
}
