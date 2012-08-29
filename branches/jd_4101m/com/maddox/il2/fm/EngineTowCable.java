package com.maddox.il2.fm;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Orientation;
import com.maddox.rts.Time;

public class EngineTowCable extends Engines
{
  public FlightModel traktor = null;
  public FlightModel plough = null;
  public float stringLength = 88.800003F;
  public float stringKx = 88.0F;
  public float boostThrust = 0.0F;
  public long fireOutTime = 0L;
  private static float a;
  private static Vector3d tmpv = new Vector3d();

  public float force(float paramFloat1, float paramFloat2)
  {
    a = 0.0F;
    if (Time.current() < this.fireOutTime) a += this.boostThrust;
    if (this.traktor == null) return this.plough.M.mass * (this.plough.Gears.onGround ? 0.0F : 1.0E-018F);
    tmpv.sub(this.traktor.Loc, this.plough.Loc);
    this.plough.Or.transformInv(tmpv);
    if (tmpv.x > this.stringLength) {
      a += ((float)tmpv.x - this.stringLength) * ((float)tmpv.x - this.stringLength) * this.stringKx;
    }
    return a;
  }

  public float getPower()
  {
    return 1.0F;
  }

  public int getEngineNumber()
  {
    return 0;
  }
}