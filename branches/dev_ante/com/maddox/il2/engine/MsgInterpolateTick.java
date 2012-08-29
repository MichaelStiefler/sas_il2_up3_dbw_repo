package com.maddox.il2.engine;

import com.maddox.rts.Message;

public class MsgInterpolateTick extends Message
{
  private static MsgInterpolateTick msg = new MsgInterpolateTick();

  protected static void send(Actor paramActor)
  {
    msg.send(paramActor);
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgInterpolateTickListener)) {
      ((MsgInterpolateTickListener)paramObject).msgInterpolateTick();
      return true;
    }
    return false;
  }
}