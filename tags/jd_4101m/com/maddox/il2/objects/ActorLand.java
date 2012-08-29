package com.maddox.il2.objects;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPosStatic;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;

public class ActorLand extends Actor
  implements MsgShotListener, MsgExplosionListener
{
  public void msgShot(Shot paramShot)
  {
    if (Engine.land().isWater(paramShot.p.x, paramShot.p.y)) paramShot.bodyMaterial = 1; else
      paramShot.bodyMaterial = 0; 
  }

  public void msgExplosion(Explosion paramExplosion) {
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public ActorLand() {
    this.pos = new ActorPosStatic(this, new Loc());
    this.flags = 16;
    setName("landscape");
    this.net = new ActorNet(this, 250) {
      public void msgNetNewChannel(NetChannel paramNetChannel) {
      } } ;
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }
}