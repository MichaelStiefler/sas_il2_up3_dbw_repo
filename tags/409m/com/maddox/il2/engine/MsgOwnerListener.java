// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgOwnerListener.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Actor

public interface MsgOwnerListener
{

    public abstract void msgOwnerAttach(com.maddox.il2.engine.Actor actor);

    public abstract void msgOwnerDetach(com.maddox.il2.engine.Actor actor);

    public abstract void msgOwnerDied(com.maddox.il2.engine.Actor actor);

    public abstract void msgOwnerTaskComplete(com.maddox.il2.engine.Actor actor);

    public abstract void msgOwnerChange(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1);
}
