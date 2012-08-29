// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Vector3f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple3f, Tuple3d

public class Vector3f extends com.maddox.JGP.Tuple3f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Vector3f(float f, float f1, float f2)
    {
        super(f, f1, f2);
    }

    public Vector3f(float af[])
    {
        super(af);
    }

    public Vector3f(com.maddox.JGP.Tuple3d tuple3d)
    {
        super(tuple3d);
    }

    public Vector3f(com.maddox.JGP.Tuple3f tuple3f)
    {
        super(tuple3f);
    }

    public Vector3f()
    {
    }

    public final float lengthSquared()
    {
        return x * x + y * y + z * z;
    }

    public final float length()
    {
        return (float)java.lang.Math.sqrt(lengthSquared());
    }

    public final void cross(com.maddox.JGP.Tuple3f tuple3f, com.maddox.JGP.Tuple3f tuple3f1)
    {
        set(tuple3f.y * tuple3f1.z - tuple3f.z * tuple3f1.y, tuple3f.z * tuple3f1.x - tuple3f.x * tuple3f1.z, tuple3f.x * tuple3f1.y - tuple3f.y * tuple3f1.x);
    }

    public final float dot(com.maddox.JGP.Tuple3f tuple3f)
    {
        return x * tuple3f.x + y * tuple3f.y + z * tuple3f.z;
    }

    public final float normalize(com.maddox.JGP.Tuple3f tuple3f)
    {
        set(tuple3f);
        return normalize();
    }

    public final float normalize()
    {
        float f = java.lang.Math.max(length(), 1E-035F);
        x /= f;
        y /= f;
        z /= f;
        return f;
    }

    public final float angle(com.maddox.JGP.Vector3f vector3f)
    {
        double d = y * vector3f.z - z * vector3f.y;
        double d1 = z * vector3f.x - x * vector3f.z;
        double d2 = x * vector3f.y - y * vector3f.x;
        double d3 = java.lang.Math.sqrt(d * d + d1 * d1 + d2 * d2);
        return (float)java.lang.Math.abs(java.lang.Math.atan2(d3, dot(vector3f)));
    }
}
