// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Hook.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            ActorException, Actor, Loc

public abstract class Hook
{

    public Hook()
    {
    }

    public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        throw new ActorException("computePos for hook not implemented");
    }

    public java.lang.String chunkName()
    {
        return "Body";
    }

    public int chunkNum()
    {
        return -1;
    }

    public void baseChanged(com.maddox.il2.engine.Actor actor)
    {
    }
}
