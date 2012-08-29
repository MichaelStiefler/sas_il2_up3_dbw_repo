package com.maddox.rts;

public class MsgNet extends Message
{
  public boolean bDestroy = false;

  private static MessageCache cache = new MessageCache(MsgNet.class);

  public static void postGame(long paramLong, Object paramObject, NetMsgInput paramNetMsgInput)
  {
    MsgNet localMsgNet = (MsgNet)cache.get();
    localMsgNet._sender = paramNetMsgInput;
    localMsgNet.post(0, paramObject, paramLong);
  }

  public static void postReal(long paramLong, Object paramObject, NetMsgInput paramNetMsgInput)
  {
    MsgNet localMsgNet = (MsgNet)cache.get();
    localMsgNet._sender = paramNetMsgInput;
    localMsgNet.post(64, paramObject, paramLong);
  }

  public static void postRealNewChannel(Object paramObject, NetChannel paramNetChannel)
  {
    MsgNet localMsgNet = (MsgNet)cache.get();
    localMsgNet._sender = paramNetChannel;
    localMsgNet.bDestroy = false;
    localMsgNet.post(64, paramObject, Time.currentReal());
  }

  public static void postRealDelChannel(Object paramObject, NetChannel paramNetChannel)
  {
    MsgNet localMsgNet = (MsgNet)cache.get();
    localMsgNet._sender = paramNetChannel;
    localMsgNet.bDestroy = true;
    localMsgNet.post(64, paramObject, Time.currentReal());
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgNetListener)) {
      if ((this._sender == null) || ((this._sender instanceof NetChannel))) {
        if (this.bDestroy)
          ((MsgNetListener)paramObject).msgNetDelChannel((NetChannel)this._sender);
        else
          ((MsgNetListener)paramObject).msgNetNewChannel((NetChannel)this._sender);
      }
      else ((MsgNetListener)paramObject).msgNet((NetMsgInput)this._sender);

      return true;
    }
    return false;
  }
}