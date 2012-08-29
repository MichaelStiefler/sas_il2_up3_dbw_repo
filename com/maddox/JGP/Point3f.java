// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Point3f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple3f, Point4f, Point3d, Tuple3d

public class Point3f extends com.maddox.JGP.Tuple3f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Point3f(float f, float f1, float f2)
    {
        super(f, f1, f2);
    }

    public Point3f(float af[])
    {
        super(af);
    }

    public Point3f(com.maddox.JGP.Point3f point3f)
    {
        super(point3f);
    }

    public Point3f(com.maddox.JGP.Point3d point3d)
    {
        super(point3d);
    }

    public Point3f(com.maddox.JGP.Tuple3f tuple3f)
    {
        super(tuple3f);
    }

    public Point3f(com.maddox.JGP.Tuple3d tuple3d)
    {
        super(tuple3d);
    }

    public Point3f()
    {
    }

    public final float distanceSquared(com.maddox.JGP.Point3f point3f)
    {
        double d = x - point3f.x;
        double d1 = y - point3f.y;
        double d2 = z - point3f.z;
        return (float)(d * d + d1 * d1 + d2 * d2);
    }

    public final float distance(com.maddox.JGP.Point3f point3f)
    {
        return (float)java.lang.Math.sqrt(distanceSquared(point3f));
    }

    public final float distanceL1(com.maddox.JGP.Point3f point3f)
    {
        return java.lang.Math.abs(x - point3f.x) + java.lang.Math.abs(y - point3f.y) + java.lang.Math.abs(z - point3f.z);
    }

    public final float distanceLinf(com.maddox.JGP.Point3f point3f)
    {
        return java.lang.Math.max(java.lang.Math.max(java.lang.Math.abs(x - point3f.x), java.lang.Math.abs(y - point3f.y)), java.lang.Math.abs(z - point3f.z));
    }

    public final void project(com.maddox.JGP.Point4f point4f)
    {
        x = point4f.x / point4f.w;
        y = point4f.y / point4f.w;
        z = point4f.z / point4f.w;
    }
}
