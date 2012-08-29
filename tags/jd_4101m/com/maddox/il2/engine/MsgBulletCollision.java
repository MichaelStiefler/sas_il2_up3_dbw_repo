package com.maddox.il2.engine;

import com.maddox.rts.Message;
import com.maddox.rts.MessageCache;

public class MsgBulletCollision extends Message
{
  public Actor collided = null;
  public String collidedChunk = null;
  public double tickOffset;
  private static MessageCache cache = new MessageCache(MsgBulletCollision.class);

  public static void post(long paramLong, double paramDouble, Actor paramActor, String paramString, BulletGeneric paramBulletGeneric)
  {
    MsgBulletCollision localMsgBulletCollision = (MsgBulletCollision)cache.get();
    localMsgBulletCollision.collided = paramActor;
    localMsgBulletCollision.collidedChunk = paramString;
    localMsgBulletCollision.tickOffset = paramDouble;
    localMsgBulletCollision.post(localMsgBulletCollision, paramLong, paramBulletGeneric);
  }

  public void clean() {
    this.collided = null;
    this.collidedChunk = null;
    super.clean();
  }

  public boolean invokeListener(Object paramObject) {
    BulletGeneric localBulletGeneric = (BulletGeneric)this._sender;
    if ((!localBulletGeneric.isDestroyed()) && (Actor.isValid(this.collided))) {
      if (localBulletGeneric.collided(this.collided, this.collidedChunk, this.tickOffset)) {
        localBulletGeneric.destroy();
      } else {
        localBulletGeneric.nextBullet = Engine.cur.bulletList;
        Engine.cur.bulletList = localBulletGeneric;
      }
    }
    return true;
  }
}