package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import java.io.IOException;

public abstract interface TypeDockable
{
  public abstract boolean typeDockableIsDocked();

  public abstract void typeDockableAttemptAttach();

  public abstract void typeDockableAttemptDetach();

  public abstract void typeDockableRequestAttach(Actor paramActor);

  public abstract void typeDockableRequestDetach(Actor paramActor);

  public abstract void typeDockableRequestAttach(Actor paramActor, int paramInt, boolean paramBoolean);

  public abstract void typeDockableRequestDetach(Actor paramActor, int paramInt, boolean paramBoolean);

  public abstract void typeDockableDoAttachToDrone(Actor paramActor, int paramInt);

  public abstract void typeDockableDoDetachFromDrone(int paramInt);

  public abstract void typeDockableDoAttachToQueen(Actor paramActor, int paramInt);

  public abstract void typeDockableDoDetachFromQueen(int paramInt);

  public abstract void typeDockableReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException;

  public abstract void typeDockableReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException;
}