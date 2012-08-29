// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   InterpolateRef.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Interpolate, Actor, ActorPos, InterpolateAdapter

public abstract class InterpolateRef extends com.maddox.il2.engine.Interpolate
{

    public void invokeRef()
    {
        if(actor.pos != null && actor.pos.base() != null)
            com.maddox.il2.engine.InterpolateAdapter.forceInterpolate(actor.pos.base());
    }

    public InterpolateRef()
    {
    }
}
