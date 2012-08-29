package com.maddox.rts;

public class MsgAddListener extends Message
{
  protected Object _listener;
  protected Object _param;
  private static MessageCache cache = new MessageCache(MsgAddListener.class);

  public MsgAddListener()
  {
    this._listener = null; this._param = null;
  }

  public MsgAddListener(Object paramObject1, Object paramObject2)
  {
    this._listener = paramObject1;
    this._param = paramObject2;
  }

  public MsgAddListener(Object paramObject)
  {
    this._listener = paramObject;
    this._param = null;
  }

  public static void post(int paramInt, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    MsgAddListener localMsgAddListener = (MsgAddListener)cache.get();
    localMsgAddListener._listener = paramObject2;
    localMsgAddListener._param = paramObject3;
    if ((paramInt & 0x40) != 0) localMsgAddListener.jdField__time_of_type_Long = Time.currentReal(); else
      localMsgAddListener.jdField__time_of_type_Long = Time.current();
    localMsgAddListener.post(paramInt, paramObject1);
  }

  public static void post(int paramInt, Object paramObject1, long paramLong, Object paramObject2, Object paramObject3)
  {
    MsgAddListener localMsgAddListener = (MsgAddListener)cache.get();
    localMsgAddListener._listener = paramObject2;
    localMsgAddListener._param = paramObject3;
    localMsgAddListener.jdField__time_of_type_Long = paramLong;
    localMsgAddListener.post(paramInt, paramObject1);
  }

  public static void post(int paramInt1, Object paramObject1, long paramLong, int paramInt2, Object paramObject2, Object paramObject3)
  {
    MsgAddListener localMsgAddListener = (MsgAddListener)cache.get();
    localMsgAddListener._listener = paramObject2;
    localMsgAddListener._param = paramObject3;
    localMsgAddListener.jdField__time_of_type_Long = paramLong;
    localMsgAddListener._tickPos = paramInt2;
    localMsgAddListener.post(paramInt1, paramObject1);
  }

  public void clean() {
    super.clean();
    this._listener = null;
    this._param = null;
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgAddListenerListener)) {
      ((MsgAddListenerListener)paramObject).msgAddListener(this._listener, this._param);
      return true;
    }
    return false;
  }
}