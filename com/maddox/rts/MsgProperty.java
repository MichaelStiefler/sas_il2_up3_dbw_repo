package com.maddox.rts;

public class MsgProperty extends Message
{
  protected int _action;
  private static MessageCache cache = new MessageCache(MsgProperty.class);

  public static void post(boolean paramBoolean1, boolean paramBoolean2, Object paramObject, Property paramProperty, int paramInt)
  {
    MsgProperty localMsgProperty = (MsgProperty)cache.get();
    localMsgProperty._action = paramInt;
    if (paramBoolean1) {
      localMsgProperty.setTime(Time.currentReal());
      localMsgProperty.setFlags(64);
      if (paramBoolean2) localMsgProperty.send(paramObject, paramProperty); else
        localMsgProperty.post(paramObject, paramProperty);
    } else {
      localMsgProperty.setTime(Time.current());
      localMsgProperty.setFlags(0);
      if (paramBoolean2) localMsgProperty.send(paramObject, paramProperty); else
        localMsgProperty.post(paramObject, paramProperty);
    }
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgPropertyListener)) {
      switch (this._action) { case 1:
        ((MsgPropertyListener)paramObject).msgPropertyAdded((Property)this._sender); break;
      case 2:
        ((MsgPropertyListener)paramObject).msgPropertyRemoved((Property)this._sender); break;
      case 3:
        ((MsgPropertyListener)paramObject).msgPropertyChanged((Property)this._sender); break;
      }

      return true;
    }
    return false;
  }
}