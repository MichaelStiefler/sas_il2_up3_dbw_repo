// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Vector2f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple2f, Tuple2d

public class Vector2f extends com.maddox.JGP.Tuple2f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Vector2f(float f, float f1)
    {
        super(f, f1);
    }

    public Vector2f(float af[])
    {
        super(af);
    }

    public Vector2f(com.maddox.JGP.Tuple2f tuple2f)
    {
        super(tuple2f);
    }

    public Vector2f(com.maddox.JGP.Tuple2d tuple2d)
    {
        super(tuple2d);
    }

    public Vector2f()
    {
    }

    public final float dot(com.maddox.JGP.Tuple2f tuple2f)
    {
        return x * tuple2f.x + y * tuple2f.y;
    }

    public final float length()
    {
        return (float)java.lang.Math.sqrt(x * x + y * y);
    }

    public final float lengthSquared()
    {
        return x * x + y * y;
    }

    public final void normalize()
    {
        double d = length();
        x /= d;
        y /= d;
    }

    public final void normalize(com.maddox.JGP.Tuple2f tuple2f)
    {
        set(tuple2f);
        normalize();
    }

    public final float angle(com.maddox.JGP.Tuple2f tuple2f)
    {
        return (float)java.lang.Math.abs(java.lang.Math.atan2(x * tuple2f.y - y * tuple2f.x, dot(tuple2f)));
    }

    public final float direction()
    {
        float f = (float)java.lang.Math.atan2(y, x);
        if(f < 0.0F)
            f += 6.283185F;
        return f;
    }
}
