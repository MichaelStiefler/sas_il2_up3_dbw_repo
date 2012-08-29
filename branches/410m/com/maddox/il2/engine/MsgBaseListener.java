// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MsgBaseListener.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Actor, Hook

public interface MsgBaseListener
{

    public abstract void msgBaseAttach(com.maddox.il2.engine.Actor actor);

    public abstract void msgBaseDetach(com.maddox.il2.engine.Actor actor);

    public abstract void msgBaseChange(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Hook hook, com.maddox.il2.engine.Actor actor1, com.maddox.il2.engine.Hook hook1);
}
