package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.game.Mission;
import com.maddox.rts.Message;
import com.maddox.rts.NetObj;
import java.util.ArrayList;

public class MsgExplosion extends Message
{
  private static MsgExplosion msg = new MsgExplosion();
  private static Explosion explosion = new Explosion();
  private static ArrayList lst = new ArrayList();

  public static void send(Actor paramActor1, String paramString, Point3d paramPoint3d, Actor paramActor2, float paramFloat1, float paramFloat2, int paramInt, float paramFloat3)
  {
    explosion.chunkName = paramString;
    explosion.p.set(paramPoint3d);
    explosion.radius = paramFloat3;
    explosion.initiator = paramActor2;
    explosion.power = paramFloat2;
    explosion.powerType = paramInt;

    if (paramInt == 1) {
      explosion.computeSplinterParams(paramFloat1);
    }

    if ((!Actor.isValid(paramActor2)) && (Mission.isSingle()) && ((Mission.cur().netObj() == null) || (Mission.cur().netObj().isMaster())))
    {
      explosion.initiator = (paramActor2 = Engine.actorLand());
    }
    if (!Actor.isValid(paramActor2)) return;
    if ((paramActor2.isNet()) && (paramActor2.net.isMirror())) return;

    if (Actor.isValid(paramActor1)) {
      msg.setListener(paramActor1);
      msg.send();
    }
    if (paramFloat3 <= 0.0F) return;
    Engine.collideEnv().getSphere(lst, paramPoint3d, paramFloat3);
    int i = lst.size();
    if (i <= 0) return;
    explosion.chunkName = null;
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)lst.get(j);
      if ((Actor.isValid(localActor)) && (paramActor1 != localActor)) {
        msg.setListener(localActor);
        msg.send();
      }
    }
    lst.clear();
  }

  public static void resetGame() {
    explosion.chunkName = null;
    explosion.initiator = null;
  }

  public boolean invokeListener(Object paramObject) {
    if ((paramObject instanceof MsgExplosionListener)) {
      ((MsgExplosionListener)paramObject).msgExplosion(explosion);
      return true;
    }
    return false;
  }
}