package com.maddox.il2.engine;

import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;

public abstract class ActorNet extends NetObj
{
  public Actor actor()
  {
    return (Actor)this.superObj;
  }

  public void msgNetNewChannel(NetChannel paramNetChannel)
  {
    if (!Actor.isValid(actor()))
      return;
    if (actor().isSpawnFromMission())
      return;
    try {
      if (paramNetChannel.isMirrored(this))
        return;
      if (paramNetChannel.userState == 0) {
        NetMsgSpawn localNetMsgSpawn = actor().netReplicate(paramNetChannel);
        if (localNetMsgSpawn != null) {
          postTo(paramNetChannel, localNetMsgSpawn);
          actor().netFirstUpdate(paramNetChannel);
        }
      }
    } catch (Exception localException) {
      NetObj.printDebug(localException);
    }
  }

  public ActorNet(Actor paramActor, int paramInt) {
    super(paramActor, paramInt);
  }

  public ActorNet(Actor paramActor)
  {
    super(paramActor);
  }

  public ActorNet(Actor paramActor, NetChannel paramNetChannel, int paramInt)
  {
    super(paramActor, paramNetChannel, paramInt);
  }
}