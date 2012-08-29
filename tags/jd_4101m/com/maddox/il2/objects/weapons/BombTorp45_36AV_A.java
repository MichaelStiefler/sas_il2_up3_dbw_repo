package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Chute;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombTorp45_36AV_A extends BombParaTorp
{
  private Chute chute = null;
  private boolean bOnChute = false;
  private static Vector3d v3d = new Vector3d();
  private float ttcurTM;
  private float ttexpTM;
  private float openHeight;

  public void start()
  {
    super.start();
    this.ttcurTM = World.Rnd().nextFloat(0.5F, 1.75F);
    this.ttexpTM = World.Rnd().nextFloat(11.2F, 17.75F);
    this.openHeight = 10000.0F;
  }

  public void destroy() {
    if (this.chute != null) {
      this.chute.destroy();
    }
    super.destroy();
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2) {
    if (((paramActor instanceof ActorLand)) && (this.chute != null))
      this.bOnChute = false;
    this.ttcurTM = 100000.0F;
    if (this.chute != null) {
      this.chute.landing();
    }
    super.msgCollision(paramActor, paramString1, paramString2);
  }

  public void interpolateTick()
  {
    this.curTm += Time.tickLenFs();

    super.interpolateTick();
    if (this.bOnChute)
    {
      getSpeed(v3d);
      v3d.scale(0.99D);
      if (v3d.z < -10.0D)
        v3d.z += 1.1F * Time.tickConstLenFs();
      setSpeed(v3d);
      this.pos.getAbs(P, Or);
    } else if ((this.curTm > this.ttcurTM) && (P.z < this.openHeight))
    {
      this.bOnChute = true;
      this.chute = new Chute(this);
      this.chute.collide(false);
      this.chute.mesh().setScale(2.5F);
      this.chute.pos.setRel(new Point3d(1.0D, 0.0D, 0.0D), new Orient(0.0F, 90.0F, 0.0F));
    }
  }

  protected void mydebug(String paramString)
  {
  }

  static
  {
    Class localClass = BombTorp45_36AV_A.class;
    Property.set(localClass, "mesh", "3do/arms/45-12/mono.sim");
    Property.set(localClass, "radius", 20.0F);
    Property.set(localClass, "power", 220.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.45F);
    Property.set(localClass, "massa", 960.0F);
    Property.set(localClass, "sound", "weapon.torpedo");
    Property.set(localClass, "velocity", 20.0F);
    Property.set(localClass, "traveltime", 200.0F);
    Property.set(localClass, "startingspeed", 0.0F);
    Property.set(localClass, "impactAngleMin", 0.0F);
    Property.set(localClass, "impactAngleMax", 90.5F);
    Property.set(localClass, "impactSpeed", 115.0F);
    Property.set(localClass, "armingTime", 3.5F);
  }
}