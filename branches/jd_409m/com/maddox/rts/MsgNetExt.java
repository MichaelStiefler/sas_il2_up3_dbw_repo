package com.maddox.rts;

public class MsgNetExt extends Message
{
  protected byte[] buf;
  protected NetSocket socket;
  protected NetAddress address;
  protected int port;
  private static MessageCache cache = new MessageCache(MsgNetExt.class);

  public static void postReal(long paramLong, Object paramObject, byte[] paramArrayOfByte, NetSocket paramNetSocket, NetAddress paramNetAddress, int paramInt)
  {
    MsgNetExt localMsgNetExt = (MsgNetExt)cache.get();
    localMsgNetExt.buf = paramArrayOfByte;
    localMsgNetExt.socket = paramNetSocket;
    localMsgNetExt.address = paramNetAddress;
    localMsgNetExt.port = paramInt;
    localMsgNetExt.post(64, paramObject, paramLong);
  }

  public void clean() {
    super.clean();
    this.buf = null;
    this.socket = null;
    this.address = null;
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgNetExtListener)) {
      ((MsgNetExtListener)paramObject).msgNetExt(this.buf, this.socket, this.address, this.port);
      return true;
    }
    return false;
  }
}