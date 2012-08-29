package com.maddox.il2.engine;

public abstract interface MsgBaseListener
{
  public abstract void msgBaseAttach(Actor paramActor);

  public abstract void msgBaseDetach(Actor paramActor);

  public abstract void msgBaseChange(Actor paramActor1, Hook paramHook1, Actor paramActor2, Hook paramHook2);
}