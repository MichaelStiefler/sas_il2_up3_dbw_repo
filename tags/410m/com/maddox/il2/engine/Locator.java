// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Locator.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;

// Referenced classes of package com.maddox.il2.engine:
//            Loc, Orientation, Orient

public class Locator extends com.maddox.il2.engine.Loc
{

    public Locator()
    {
        P = new Point3d();
        O = new Orientation();
    }

    public Locator(double d, double d1, double d2, float f, 
            float f1, float f2)
    {
        this();
        set(d, d1, d2, f, f1, f2);
    }

    public Locator(com.maddox.il2.engine.Loc loc)
    {
        this();
        set(loc);
    }

    public Locator(com.maddox.JGP.Tuple3d tuple3d, com.maddox.il2.engine.Orient orient)
    {
        this();
        set(tuple3d, orient);
    }

    public Locator(com.maddox.JGP.Tuple3d tuple3d)
    {
        this();
        set(tuple3d);
    }

    public Locator(com.maddox.il2.engine.Orient orient)
    {
        this();
        set(orient);
    }

    public Locator(double ad[])
    {
        this();
        set(ad);
    }
}
