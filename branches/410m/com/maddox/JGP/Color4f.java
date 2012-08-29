// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Color4f.java

package com.maddox.JGP;

import java.io.Serializable;

// Referenced classes of package com.maddox.JGP:
//            Tuple4f, Tuple4d

public class Color4f extends com.maddox.JGP.Tuple4f
    implements java.io.Serializable, java.lang.Cloneable
{

    public Color4f(float f, float f1, float f2, float f3)
    {
        super(f, f1, f2, f3);
    }

    public Color4f(float af[])
    {
        super(af);
    }

    public Color4f(com.maddox.JGP.Color4f color4f)
    {
        super(color4f);
    }

    public Color4f(com.maddox.JGP.Tuple4d tuple4d)
    {
        super(tuple4d);
    }

    public Color4f(com.maddox.JGP.Tuple4f tuple4f)
    {
        super(tuple4f);
    }

    public Color4f()
    {
    }
}
