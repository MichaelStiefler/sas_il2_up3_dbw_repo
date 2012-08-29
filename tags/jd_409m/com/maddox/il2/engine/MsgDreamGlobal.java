package com.maddox.il2.engine;

import com.maddox.rts.Message;

public class MsgDreamGlobal extends Message
{
  private boolean _bWakeup;
  private int[] _xIndx;
  private int[] _yIndx;
  private int _count;
  private int updateTicks;
  private int updateTickCounter;

  protected void sendTick(Actor paramActor, int paramInt1, int paramInt2)
  {
    this.updateTicks = paramInt1;
    this.updateTickCounter = paramInt2;
    send(paramActor);
    this.updateTicks = 0;
  }

  protected void send(Actor paramActor, boolean paramBoolean, int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2) {
    this._bWakeup = paramBoolean;
    this._count = paramInt;
    this._xIndx = paramArrayOfInt1;
    this._yIndx = paramArrayOfInt2;
    send(paramActor);
    this._count = 0;
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgDreamGlobalListener)) {
      if (this.updateTicks != 0)
        ((MsgDreamGlobalListener)paramObject).msgDreamGlobalTick(this.updateTicks, this.updateTickCounter);
      else if (this._count != 0) {
        ((MsgDreamGlobalListener)paramObject).msgDreamGlobal(this._bWakeup, this._count, this._xIndx, this._yIndx);
      }
      return true;
    }
    return false;
  }
}