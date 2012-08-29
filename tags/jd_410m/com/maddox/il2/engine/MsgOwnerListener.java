package com.maddox.il2.engine;

public abstract interface MsgOwnerListener
{
  public abstract void msgOwnerAttach(Actor paramActor);

  public abstract void msgOwnerDetach(Actor paramActor);

  public abstract void msgOwnerDied(Actor paramActor);

  public abstract void msgOwnerTaskComplete(Actor paramActor);

  public abstract void msgOwnerChange(Actor paramActor1, Actor paramActor2);
}