package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.rts.Message;
import com.maddox.rts.Time;

public class BallisticProjectile extends Actor
{
  private Vector3d v;
  private long ttl;
  private static Point3d tmpp = new Point3d();

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public BallisticProjectile(Point3d paramPoint3d, Vector3d paramVector3d, float paramFloat)
  {
    this.pos = new ActorPosMove(this);
    this.pos.setAbs(paramPoint3d);

    this.pos.reset();
    this.v = new Vector3d(paramVector3d);
    this.v.scale(Time.tickConstLenFs());
    this.ttl = (Time.current() + ()(paramFloat * 1000.0F));
    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void interpolateStep()
  {
    this.pos.getAbs(tmpp);
    tmpp.add(this.v);
    this.pos.setAbs(tmpp);

    this.v.z -= Atmosphere.g() * Time.tickLenFs() * Time.tickLenFs();
    if (Time.current() > this.ttl) postDestroy();
  }

  class Interpolater extends Interpolate
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      BallisticProjectile.this.interpolateStep();
      return true;
    }
  }
}