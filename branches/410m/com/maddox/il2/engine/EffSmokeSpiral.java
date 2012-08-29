// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   EffSmokeSpiral.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Eff3D, EffSmokeSpiralActor, GObj, Loc, 
//            Eff3DActor, ActorPos

public class EffSmokeSpiral extends com.maddox.il2.engine.Eff3D
{

    protected com.maddox.il2.engine.Eff3DActor NewActor(com.maddox.il2.engine.Loc loc)
    {
        return new EffSmokeSpiralActor(this, loc);
    }

    protected com.maddox.il2.engine.Eff3DActor NewActor(com.maddox.il2.engine.ActorPos actorpos)
    {
        return new EffSmokeSpiralActor(this, actorpos);
    }

    protected EffSmokeSpiral()
    {
        cppObj = cNew();
    }

    private native int cNew();

    public EffSmokeSpiral(int i)
    {
        cppObj = i;
    }

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
