package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Chute;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombTorpFFF extends BombParaTorp
{
  private Chute chute1 = null;
  private Chute chute2 = null;
  public boolean bOnChute1 = false;
  public boolean bOnChute2 = false;
  private static Vector3d v3d = new Vector3d();
  private float ttcurTM;
  private float ttexpTM;
  private float openHeight;

  public void start()
  {
    super.start();

    this.ttcurTM = 1.5F;
    this.ttexpTM = 15.0F;

    if (!(this instanceof BombTorpFFF1))
    {
      this.ttcurTM += 1.2F;
      this.openHeight = 210.0F;
    }
    else {
      this.openHeight = 180.0F;
    }
  }

  public void destroy() {
    if (this.chute1 != null)
      this.chute1.destroy();
    if (this.chute2 != null)
      this.chute2.destroy();
    super.destroy();
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2) {
    if (((paramActor instanceof ActorLand)) && ((this.chute1 != null) || (this.chute2 != null))) {
      this.bOnChute1 = false;
    }
    this.ttcurTM = 100000.0F;
    if (this.chute1 != null)
      this.chute1.landing();
    if (this.chute2 != null) {
      this.chute2.landing();
    }
    super.msgCollision(paramActor, paramString1, paramString2);
  }

  public void interpolateTick()
  {
    this.curTm += Time.tickLenFs();

    super.interpolateTick();
    if (this.bOnChute2)
    {
      getSpeed(v3d);
      v3d.scale(0.99D);
      if (v3d.z < -40.0D)
        v3d.z += 8.0F * Time.tickConstLenFs();
      setSpeed(v3d);
      this.pos.getAbs(P, Or);
    }
    else if (this.bOnChute1)
    {
      getSpeed(v3d);
      v3d.scale(0.99D);
      if (v3d.z < -90.0D)
        v3d.z += 1.1F * Time.tickConstLenFs();
      setSpeed(v3d);
      this.pos.getAbs(P, Or);
      if (P.z < this.openHeight)
      {
        this.bOnChute2 = true;
        this.bOnChute1 = false;
        this.chute2 = new Chute(this);
        this.chute2.collide(false);
        this.chute2.mesh().setScale(2.5F);
        this.chute2.pos.setRel(new Point3d(3.0D, 0.0D, 0.0D), new Orient(0.0F, 90.0F, 0.0F));

        if (this.chute1 != null)
          this.chute1.destroy();
      }
    } else if (this.curTm > this.ttcurTM)
    {
      this.bOnChute1 = true;
      this.chute1 = new Chute(this);
      this.chute1.collide(false);
      this.chute1.mesh().setScale(0.4F);
      this.chute1.pos.setRel(new Point3d(0.5D, 0.0D, 0.0D), new Orient(0.0F, 90.0F, 0.0F));
    }
  }

  static
  {
    Class localClass = BombTorpFFF.class;
    Property.set(localClass, "mesh", "3DO/Arms/MotobombaFFF/mono.sim");
    Property.set(localClass, "radius", 70.0F);
    Property.set(localClass, "power", 120.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.5F);
    Property.set(localClass, "massa", 360.0F);
    Property.set(localClass, "sound", "weapon.torpedo");
    Property.set(localClass, "velocity", 6.1F);
    Property.set(localClass, "traveltime", 2100.0F);
    Property.set(localClass, "startingspeed", 0.0F);
    Property.set(localClass, "impactAngleMin", 0.0F);
    Property.set(localClass, "impactAngleMax", 90.5F);
    Property.set(localClass, "impactSpeed", 115.0F);
    Property.set(localClass, "armingTime", 3.5F);
  }
}