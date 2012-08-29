package com.maddox.il2.objects;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;

public class ActorViewPoint extends Actor
{
  private HookUpdate hook;

  public void setViewActor(Actor paramActor)
  {
    if (!Actor.isValid(paramActor)) return;
    this.pos.setBase(paramActor, this.hook, true);
  }

  public void createNetObject(NetChannel paramNetChannel, int paramInt)
  {
    if (paramNetChannel == null)
    {
      this.net = new Master(this);
    }
    else
      this.net = new Mirror(this, paramNetChannel, paramInt);
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }
  public ActorViewPoint() {
    this.pos = new ActorPosMove(this, new Loc());
    this.hook = new HookUpdate();
    this.acoustics = Engine.worldAcoustics();
  }

  class Mirror extends ActorNet
  {
    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4)
    {
      super(paramInt, i);
    }
  }

  class Master extends ActorNet
  {
    public Master(Actor arg2)
    {
      super();
    }
  }

  static class HookUpdate extends Hook
  {
    public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2)
    {
    }
  }
}