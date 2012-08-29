// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TypeDockable.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public interface TypeDockable
{

    public abstract boolean typeDockableIsDocked();

    public abstract void typeDockableAttemptAttach();

    public abstract void typeDockableAttemptDetach();

    public abstract void typeDockableRequestAttach(com.maddox.il2.engine.Actor actor);

    public abstract void typeDockableRequestDetach(com.maddox.il2.engine.Actor actor);

    public abstract void typeDockableRequestAttach(com.maddox.il2.engine.Actor actor, int i, boolean flag);

    public abstract void typeDockableRequestDetach(com.maddox.il2.engine.Actor actor, int i, boolean flag);

    public abstract void typeDockableDoAttachToDrone(com.maddox.il2.engine.Actor actor, int i);

    public abstract void typeDockableDoDetachFromDrone(int i);

    public abstract void typeDockableDoAttachToQueen(com.maddox.il2.engine.Actor actor, int i);

    public abstract void typeDockableDoDetachFromQueen(int i);

    public abstract void typeDockableReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException;

    public abstract void typeDockableReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException;
}
