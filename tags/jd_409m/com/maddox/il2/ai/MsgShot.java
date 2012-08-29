package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.ActorLand;
import com.maddox.rts.Message;
import com.maddox.rts.NetObj;

public class MsgShot extends Message
{
  private static Shot shot = new Shot();
  private static MsgShot msg = new MsgShot();

  public static Shot send(Actor paramActor1, String paramString, Point3d paramPoint3d, Vector3f paramVector3f, float paramFloat1, Actor paramActor2, float paramFloat2, int paramInt, double paramDouble)
  {
    shot.chunkName = paramString;
    shot.p.set(paramPoint3d);
    shot.v.set(paramVector3f);
    shot.mass = paramFloat1;
    shot.initiator = paramActor2;
    shot.power = paramFloat2;
    shot.powerType = paramInt;
    shot.tickOffset = paramDouble;

    if ((paramActor1 instanceof ActorLand))
    {
      if (Engine.land().isWater(paramPoint3d.x, paramPoint3d.y)) shot.bodyMaterial = 1; else
        shot.bodyMaterial = 0;
    }
    else shot.bodyMaterial = 2;

    shot.bodyNormal.negate(paramVector3f);

    if ((!Actor.isValid(paramActor2)) && (Mission.isSingle()) && ((Mission.cur().netObj() == null) || (Mission.cur().netObj().isMaster())))
    {
      shot.initiator = (paramActor2 = Engine.actorLand());
    }
    if ((Actor.isValid(paramActor2)) && (Actor.isValid(paramActor1)) && ((!paramActor2.isNet()) || (!paramActor2.net.isMirror())))
    {
      msg.send(paramActor1);
    }return shot;
  }

  public static void resetGame() {
    shot.chunkName = null;
    shot.initiator = null;
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgShotListener)) {
      ((MsgShotListener)paramObject).msgShot(shot);
      return true;
    }
    return false;
  }
}