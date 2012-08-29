package com.maddox.rts;

public class MsgTimeOut extends Message
{
  protected Object _param;
  private static MessageCache cache = new MessageCache(MsgTimeOut.class);

  public MsgTimeOut()
  {
    this._param = null;
  }

  public MsgTimeOut(Object paramObject)
  {
    this._param = paramObject;
  }

  public static void post(int paramInt, long paramLong, Object paramObject1, Object paramObject2)
  {
    MsgTimeOut localMsgTimeOut = (MsgTimeOut)cache.get();
    localMsgTimeOut._param = paramObject2;
    localMsgTimeOut.post(paramInt, paramObject1, paramLong);
  }

  public static void post(int paramInt, double paramDouble, Object paramObject1, Object paramObject2)
  {
    MsgTimeOut localMsgTimeOut = (MsgTimeOut)cache.get();
    localMsgTimeOut._param = paramObject2;
    localMsgTimeOut.post(paramInt, paramObject1, paramDouble);
  }

  public static void post(long paramLong, Object paramObject1, Object paramObject2)
  {
    MsgTimeOut localMsgTimeOut = (MsgTimeOut)cache.get();
    localMsgTimeOut._param = paramObject2;
    localMsgTimeOut.post(0, paramObject1, paramLong);
  }

  public static void post(long paramLong, int paramInt, Object paramObject1, Object paramObject2)
  {
    MsgTimeOut localMsgTimeOut = (MsgTimeOut)cache.get();
    localMsgTimeOut._param = paramObject2;
    localMsgTimeOut.post(0, paramObject1, paramLong, paramInt);
  }

  public static void post(double paramDouble, Object paramObject1, Object paramObject2)
  {
    MsgTimeOut localMsgTimeOut = (MsgTimeOut)cache.get();
    localMsgTimeOut._param = paramObject2;
    localMsgTimeOut.post(0, paramObject1, paramDouble);
  }

  public void clean() {
    super.clean();
    this._param = null;
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgTimeOutListener)) {
      ((MsgTimeOutListener)paramObject).msgTimeOut(this._param);
      return true;
    }
    return false;
  }
}