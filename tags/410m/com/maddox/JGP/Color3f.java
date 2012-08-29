// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Color3f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple3f, Tuple3d

public class Color3f extends com.maddox.JGP.Tuple3f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Color3f(float f, float f1, float f2)
    {
        super(f, f1, f2);
    }

    public Color3f(float af[])
    {
        super(af);
    }

    public Color3f(com.maddox.JGP.Color3f color3f)
    {
        super(color3f);
    }

    public Color3f(com.maddox.JGP.Tuple3d tuple3d)
    {
        super(tuple3d);
    }

    public Color3f(com.maddox.JGP.Tuple3f tuple3f)
    {
        super(tuple3f);
    }

    public Color3f()
    {
    }
}
