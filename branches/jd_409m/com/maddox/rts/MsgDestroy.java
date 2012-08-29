package com.maddox.rts;

public class MsgDestroy extends Message
{
  private static MessageCache cache = new MessageCache(MsgDestroy.class);

  public static void Post(int paramInt, long paramLong, Object paramObject)
  {
    MsgDestroy localMsgDestroy = (MsgDestroy)cache.get();
    localMsgDestroy.post(paramInt, paramObject, paramLong);
  }

  public static void Post(int paramInt, double paramDouble, Object paramObject)
  {
    MsgDestroy localMsgDestroy = (MsgDestroy)cache.get();
    localMsgDestroy.post(paramInt, paramObject, paramDouble);
  }

  public static void Post(long paramLong, Object paramObject)
  {
    MsgDestroy localMsgDestroy = (MsgDestroy)cache.get();
    localMsgDestroy.post(0, paramObject, paramLong);
  }

  public static void Post(double paramDouble, Object paramObject)
  {
    MsgDestroy localMsgDestroy = (MsgDestroy)cache.get();
    localMsgDestroy.post(0, paramObject, paramDouble);
  }

  public static void Post(Object paramObject)
  {
    MsgDestroy localMsgDestroy = (MsgDestroy)cache.get();
    localMsgDestroy.post(0, paramObject, Time.current());
  }

  public static void PostReal(long paramLong, Object paramObject)
  {
    MsgDestroy localMsgDestroy = (MsgDestroy)cache.get();
    localMsgDestroy.post(64, paramObject, paramLong);
  }

  public static void PostReal(Object paramObject)
  {
    MsgDestroy localMsgDestroy = (MsgDestroy)cache.get();
    localMsgDestroy.post(64, paramObject, Time.currentReal());
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof Destroy)) {
      ((Destroy)paramObject).destroy();
      return true;
    }
    return false;
  }
}