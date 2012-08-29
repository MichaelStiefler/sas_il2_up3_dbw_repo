// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HookRender.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Hook, ActorException, Actor, Loc

public abstract class HookRender extends com.maddox.il2.engine.Hook
{

    public HookRender()
    {
    }

    public boolean computeRenderPos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        throw new ActorException("computeRenderPos for hook not implemented");
    }
}
