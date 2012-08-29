// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Vector4f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple4f, Tuple4d

public class Vector4f extends com.maddox.JGP.Tuple4f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Vector4f(float f, float f1, float f2, float f3)
    {
        super(f, f1, f2, f3);
    }

    public Vector4f(float af[])
    {
        super(af);
    }

    public Vector4f(com.maddox.JGP.Tuple4d tuple4d)
    {
        super(tuple4d);
    }

    public Vector4f(com.maddox.JGP.Tuple4f tuple4f)
    {
        super(tuple4f);
    }

    public Vector4f()
    {
    }

    public final float lengthSquared()
    {
        return x * x + y * y + z * z + w * w;
    }

    public final float length()
    {
        return (float)java.lang.Math.sqrt(lengthSquared());
    }

    public final float dot(com.maddox.JGP.Tuple4f tuple4f)
    {
        return x * tuple4f.x + y * tuple4f.y + z * tuple4f.z + w * tuple4f.w;
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

    public final float angle(com.maddox.JGP.Vector4f vector4f)
    {
        double d = dot(vector4f);
        double d1 = vector4f.length();
        double d2 = length();
        return (float)java.lang.Math.acos(d / d1 / d2);
    }
}
