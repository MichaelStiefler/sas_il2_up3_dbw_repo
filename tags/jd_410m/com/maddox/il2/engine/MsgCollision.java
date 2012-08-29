package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.MessageCache;

public class MsgCollision extends Message
{
  public String thisChunk = null;
  public String otherChunk = null;

  private static MessageCache cache = new MessageCache(MsgCollision.class);

  public static void post(long paramLong, Actor paramActor1, Actor paramActor2, String paramString1, String paramString2)
  {
    MsgCollision localMsgCollision = (MsgCollision)cache.get();
    localMsgCollision.thisChunk = paramString1;
    localMsgCollision.otherChunk = paramString2;
    localMsgCollision.post(paramActor1, paramLong, paramActor2);
  }

  public static void post2(long paramLong, Actor paramActor1, Actor paramActor2, String paramString1, String paramString2)
  {
    MsgCollision localMsgCollision = null;
    int i = (paramActor1.flags & 0x80) != 0 ? 1 : 0;
    int j = (paramActor2.flags & 0x80) != 0 ? 1 : 0;
    if ((i == 0) && (j == 0)) {
      localMsgCollision = (MsgCollision)cache.get();
      localMsgCollision.thisChunk = paramString1;
      localMsgCollision.otherChunk = paramString2;
      localMsgCollision.post(paramActor1, paramLong, paramActor2);
      localMsgCollision = (MsgCollision)cache.get();
      localMsgCollision.thisChunk = paramString2;
      localMsgCollision.otherChunk = paramString1;
      localMsgCollision.post(paramActor2, paramLong, paramActor1);
      return;
    }
    if ((i != 0) && (j != 0)) {
      if ((paramActor2.flags & 0x20) != 0) i = 0; else
        j = 0;
    }
    if (i != 0) {
      localMsgCollision = (MsgCollision)cache.get();
      localMsgCollision.thisChunk = paramString1;
      localMsgCollision.otherChunk = paramString2;
      localMsgCollision.post(paramActor1, paramLong, paramActor2);
    }
    if (j != 0) {
      localMsgCollision = (MsgCollision)cache.get();
      localMsgCollision.thisChunk = paramString2;
      localMsgCollision.otherChunk = paramString1;
      localMsgCollision.post(paramActor2, paramLong, paramActor1);
    }
  }

  public void clean() {
    this.thisChunk = null;
    this.otherChunk = null;
    super.clean();
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgCollisionListener)) {
      ((MsgCollisionListener)paramObject).msgCollision((Actor)this._sender, this.thisChunk, this.otherChunk);
      return true;
    }
    return false;
  }
}