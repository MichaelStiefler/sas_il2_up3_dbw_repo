package com.maddox.il2.engine;

import com.maddox.rts.Message;

public class MsgCollisionRequest extends Message
{
  private static MsgCollisionRequest msg = new MsgCollisionRequest();
  private static boolean[] res = new boolean[1];

  protected static boolean on(Actor paramActor1, Actor paramActor2)
  {
    msg._sender = paramActor2;
    res[0] = true;
    msg.send(paramActor1);
    msg._sender = null;
    if (res[0] == 0)
      return false;
    msg._sender = paramActor1;
    res[0] = true;
    msg.send(paramActor2);
    msg._sender = null;
    return res[0];
  }

  public boolean invokeListener(Object paramObject)
  {
    if ((paramObject instanceof MsgCollisionRequestListener)) {
      ((MsgCollisionRequestListener)paramObject).msgCollisionRequest((Actor)this._sender, res);
      return true;
    }
    return false;
  }
}