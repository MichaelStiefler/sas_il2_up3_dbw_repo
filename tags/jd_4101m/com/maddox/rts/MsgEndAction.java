package com.maddox.rts;

public class MsgEndAction extends Message
{
  protected int _action;
  private static MessageCache cache = new MessageCache(MsgEndAction.class);

  public static void post(int paramInt1, long paramLong, Object paramObject1, Object paramObject2, int paramInt2)
  {
    MsgEndAction localMsgEndAction = (MsgEndAction)cache.get();
    localMsgEndAction._action = paramInt2;
    localMsgEndAction.post(paramInt1, paramObject1, paramLong, paramObject2);
  }

  public static void post(int paramInt1, double paramDouble, Object paramObject1, Object paramObject2, int paramInt2)
  {
    MsgEndAction localMsgEndAction = (MsgEndAction)cache.get();
    localMsgEndAction._action = paramInt2;
    localMsgEndAction.post(paramInt1, paramObject1, paramDouble, paramObject2);
  }

  public static void post(int paramInt1, Object paramObject1, Object paramObject2, int paramInt2)
  {
    MsgEndAction localMsgEndAction = (MsgEndAction)cache.get();
    localMsgEndAction._action = paramInt2;
    if ((paramInt1 & 0x40) != 0) localMsgEndAction.post(paramInt1, paramObject1, Time.currentReal(), paramObject2); else
      localMsgEndAction.post(paramInt1, paramObject1, Time.current(), paramObject2);
  }

  public static void postReal(Object paramObject1, Object paramObject2, int paramInt)
  {
    MsgEndAction localMsgEndAction = (MsgEndAction)cache.get();
    localMsgEndAction._action = paramInt;
    localMsgEndAction.post(64, paramObject1, Time.currentReal(), paramObject2);
  }

  public static void post(Object paramObject1, Object paramObject2, int paramInt)
  {
    MsgEndAction localMsgEndAction = (MsgEndAction)cache.get();
    localMsgEndAction._action = paramInt;
    localMsgEndAction.post(0, paramObject1, Time.current(), paramObject2);
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgEndActionListener)) {
      ((MsgEndActionListener)paramObject).msgEndAction(this._sender, this._action);
      return true;
    }
    return false;
  }
}