// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   EffParticles.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Eff3D, EffParticlesActor, GObj, Loc, 
//            Eff3DActor, ActorPos

public class EffParticles extends com.maddox.il2.engine.Eff3D
{

    protected com.maddox.il2.engine.Eff3DActor NewActor(com.maddox.il2.engine.Loc loc)
    {
        return new EffParticlesActor(this, loc);
    }

    protected com.maddox.il2.engine.Eff3DActor NewActor(com.maddox.il2.engine.ActorPos actorpos)
    {
        return new EffParticlesActor(this, actorpos);
    }

    protected EffParticles()
    {
        cppObj = cNew();
    }

    private native int cNew();

    public EffParticles(int i)
    {
        cppObj = i;
    }

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
