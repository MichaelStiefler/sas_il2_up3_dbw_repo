// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LightEnv.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

// Referenced classes of package com.maddox.il2.engine:
//            Sun, LightPoint

public abstract class LightEnv
{

    public com.maddox.il2.engine.Sun sun()
    {
        return sun;
    }

    public void setSun(com.maddox.il2.engine.Sun sun1)
    {
        sun = sun1;
    }

    public int prepareForRender(com.maddox.JGP.Point3d point3d, float f)
    {
        return 0;
    }

    protected void changedPos(com.maddox.il2.engine.LightPoint lightpoint, double d, double d1, double d2)
    {
    }

    protected void changedEmit(com.maddox.il2.engine.LightPoint lightpoint, float f, float f1)
    {
    }

    protected void add(com.maddox.il2.engine.LightPoint lightpoint)
    {
    }

    protected void remove(com.maddox.il2.engine.LightPoint lightpoint)
    {
    }

    public void clear()
    {
    }

    public LightEnv()
    {
        sun = new Sun();
    }

    public LightEnv(com.maddox.il2.engine.Sun sun1)
    {
        sun = sun1;
    }

    protected void activate()
    {
        sun.activate();
    }

    private com.maddox.il2.engine.Sun sun;
}
