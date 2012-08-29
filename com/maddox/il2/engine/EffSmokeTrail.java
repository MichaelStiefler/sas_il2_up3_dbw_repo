// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   EffSmokeTrail.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Eff3D, EffSmokeTrailActor, GObj, Loc, 
//            Eff3DActor

public class EffSmokeTrail extends com.maddox.il2.engine.Eff3D
{

    protected com.maddox.il2.engine.Eff3DActor NewActor(com.maddox.il2.engine.Loc loc)
    {
        return new EffSmokeTrailActor(this, loc);
    }

    protected EffSmokeTrail()
    {
        cppObj = cNew();
    }

    private native int cNew();

    public EffSmokeTrail(int i)
    {
        cppObj = i;
    }

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
