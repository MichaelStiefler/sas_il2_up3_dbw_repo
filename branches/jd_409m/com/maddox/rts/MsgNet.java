package com.maddox.rts;

public class MsgNet extends Message
{
  public boolean bDestroy = false;

  private static MessageCache cache = new MessageCache(MsgNet.class);

  public static void postGame(long paramLong, Object paramObject, NetMsgInput paramNetMsgInput)
  {
    MsgNet localMsgNet = (MsgNet)cache.get();
    localMsgNet.jdField__sender_of_type_JavaLangObject = paramNetMsgInput;
    localMsgNet.post(0, paramObject, paramLong);
  }

  public static void postReal(long paramLong, Object paramObject, NetMsgInput paramNetMsgInput)
  {
    MsgNet localMsgNet = (MsgNet)cache.get();
    localMsgNet.jdField__sender_of_type_JavaLangObject = paramNetMsgInput;
    localMsgNet.post(64, paramObject, paramLong);
  }

  public static void postRealNewChannel(Object paramObject, NetChannel paramNetChannel)
  {
    MsgNet localMsgNet = (MsgNet)cache.get();
    localMsgNet.jdField__sender_of_type_JavaLangObject = paramNetChannel;
    localMsgNet.bDestroy = false;
    localMsgNet.post(64, paramObject, Time.currentReal());
  }

  public static void postRealDelChannel(Object paramObject, NetChannel paramNetChannel)
  {
    MsgNet localMsgNet = (MsgNet)cache.get();
    localMsgNet.jdField__sender_of_type_JavaLangObject = paramNetChannel;
    localMsgNet.bDestroy = true;
    localMsgNet.post(64, paramObject, Time.currentReal());
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgNetListener)) {
      if ((this.jdField__sender_of_type_JavaLangObject == null) || ((this.jdField__sender_of_type_JavaLangObject instanceof NetChannel))) {
        if (this.bDestroy)
          ((MsgNetListener)paramObject).msgNetDelChannel((NetChannel)this.jdField__sender_of_type_JavaLangObject);
        else
          ((MsgNetListener)paramObject).msgNetNewChannel((NetChannel)this.jdField__sender_of_type_JavaLangObject);
      }
      else ((MsgNetListener)paramObject).msgNet((NetMsgInput)this.jdField__sender_of_type_JavaLangObject);

      return true;
    }
    return false;
  }
}