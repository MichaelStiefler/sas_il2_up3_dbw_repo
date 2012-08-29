package com.maddox.il2.engine;

import com.maddox.rts.Message;

public class MsgDream extends Message
{
  private boolean _bWakeup;

  protected void send(boolean paramBoolean, Actor paramActor)
  {
    this._bWakeup = paramBoolean;
    send(paramActor);
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgDreamListener)) {
      ((MsgDreamListener)paramObject).msgDream(this._bWakeup);
      return true;
    }
    return false;
  }
}