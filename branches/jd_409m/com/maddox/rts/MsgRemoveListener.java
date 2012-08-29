package com.maddox.rts;

public class MsgRemoveListener extends Message
{
  protected Object _listener;
  protected Object _param;
  private static MessageCache cache = new MessageCache(MsgRemoveListener.class);

  public MsgRemoveListener()
  {
    this._listener = null; this._param = null;
  }

  public MsgRemoveListener(Object paramObject1, Object paramObject2)
  {
    this._listener = paramObject1;
    this._param = paramObject2;
  }

  public static void post(int paramInt, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    MsgRemoveListener localMsgRemoveListener = (MsgRemoveListener)cache.get();
    localMsgRemoveListener._listener = paramObject2;
    localMsgRemoveListener._param = paramObject3;
    if ((paramInt & 0x40) != 0) localMsgRemoveListener.jdField__time_of_type_Long = Time.currentReal(); else
      localMsgRemoveListener.jdField__time_of_type_Long = Time.current();
    localMsgRemoveListener.post(paramInt, paramObject1);
  }

  public static void post(int paramInt, Object paramObject1, long paramLong, Object paramObject2, Object paramObject3)
  {
    MsgRemoveListener localMsgRemoveListener = (MsgRemoveListener)cache.get();
    localMsgRemoveListener._listener = paramObject2;
    localMsgRemoveListener._param = paramObject3;
    localMsgRemoveListener.jdField__time_of_type_Long = paramLong;
    localMsgRemoveListener.post(paramInt, paramObject1);
  }

  public void clean() {
    super.clean();
    this._listener = null;
    this._param = null;
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgRemoveListenerListener)) {
      ((MsgRemoveListenerListener)paramObject).msgRemoveListener(this._listener, this._param);
      return true;
    }
    return false;
  }
}