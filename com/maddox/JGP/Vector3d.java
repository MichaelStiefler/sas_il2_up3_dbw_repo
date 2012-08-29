// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Vector3d.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple3d, Tuple3f

public class Vector3d extends com.maddox.JGP.Tuple3d
    implements java.io.Serializable, java.lang.Cloneable
{

    public Vector3d(double d, double d1, double d2)
    {
        super(d, d1, d2);
    }

    public Vector3d(double ad[])
    {
        super(ad);
    }

    public Vector3d(com.maddox.JGP.Tuple3d tuple3d)
    {
        super(tuple3d);
    }

    public Vector3d(com.maddox.JGP.Tuple3f tuple3f)
    {
        super(tuple3f);
    }

    public Vector3d()
    {
    }

    public final void cross(com.maddox.JGP.Tuple3d tuple3d, com.maddox.JGP.Tuple3d tuple3d1)
    {
        set(tuple3d.y * tuple3d1.z - tuple3d.z * tuple3d1.y, tuple3d.z * tuple3d1.x - tuple3d.x * tuple3d1.z, tuple3d.x * tuple3d1.y - tuple3d.y * tuple3d1.x);
    }

    public final double normalize(com.maddox.JGP.Tuple3d tuple3d)
    {
        set(tuple3d);
        return normalize();
    }

    public final double normalize()
    {
        double d = java.lang.Math.max(length(), 9.9999999999999996E-076D);
        x /= d;
        y /= d;
        z /= d;
        return d;
    }

    public final double dot(com.maddox.JGP.Tuple3d tuple3d)
    {
        return x * tuple3d.x + y * tuple3d.y + z * tuple3d.z;
    }

    public final double lengthSquared()
    {
        return x * x + y * y + z * z;
    }

    public final double length()
    {
        return java.lang.Math.sqrt(lengthSquared());
    }

    public final double angle(com.maddox.JGP.Vector3d vector3d)
    {
        double d = y * vector3d.z - z * vector3d.y;
        double d1 = z * vector3d.x - x * vector3d.z;
        double d2 = x * vector3d.y - y * vector3d.x;
        double d3 = java.lang.Math.sqrt(d * d + d1 * d1 + d2 * d2);
        return java.lang.Math.abs(java.lang.Math.atan2(d3, dot(vector3d)));
    }
}
