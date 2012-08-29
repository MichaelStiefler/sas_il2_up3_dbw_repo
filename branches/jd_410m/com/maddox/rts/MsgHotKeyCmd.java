package com.maddox.rts;

public class MsgHotKeyCmd extends Message
{
  protected boolean bStart;
  protected boolean bBefore;

  public boolean invokeListener(Object paramObject)
  {
    if ((paramObject instanceof MsgHotKeyCmdListener)) {
      ((MsgHotKeyCmdListener)paramObject).msgHotKeyCmd((HotKeyCmd)this._sender, this.bStart, this.bBefore);
      return true;
    }
    return false;
  }
}