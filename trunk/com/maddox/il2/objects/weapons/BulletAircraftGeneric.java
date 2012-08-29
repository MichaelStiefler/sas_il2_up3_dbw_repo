package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.MsgAction;

public class BulletAircraftGeneric extends Bullet
{
  protected int bulletss;
  Vector3d Wind;
  private static final int[] fv = { 1, 1, 5, 15, 52, 87, 123, 160, 196, 233, 269, 333 };

  public void move(float paramFloat)
  {
    if (this.gun == null) return;

    Point3d localPoint3d = this.p1; this.p1 = this.p0; this.p0 = localPoint3d;
    dspeed.scale(this.gun.bulletKV[indx()] * paramFloat * 1.0F * fv(this.speed.length()) / this.speed.length(), this.speed);

    dspeed.z += this.gun.bulletAG[indx()] * paramFloat;
    this.speed.add(dspeed);
    this.p1.scaleAdd(paramFloat, this.speed, this.p0);

    this.p1.x += this.Wind.x * paramFloat;
    this.p1.y += this.Wind.y * paramFloat;
  }

  public void timeOut()
  {
    if (gun() != null)
      Explosions.generateExplosion(null, this.p1, gun().prop.bullet[indx()].power, gun().prop.bullet[indx()].powerType, gun().prop.bullet[indx()].powerRadius, 0.0D);
  }

  public void destroy()
  {
    if ((Mission.isPlaying()) && (!NetMissionTrack.isPlaying()) && (Actor.isValid(owner())) && (Actor.isValid(gun())) && ((gun() instanceof MGunAircraftGeneric)) && (owner() == World.getPlayerAircraft()) && (World.cur().diffCur.Limited_Ammo))
    {
      int i = this.bulletss - hashCode();
      if ((i != 0) && (i <= gun().countBullets()) && (
        (i != -1) || (!World.isPlayerGunner()))) {
        postRemove(owner());
      }
    }
    super.destroy();
  }
  private void postRemove(Actor paramActor) {
    new MsgAction(false, paramActor) {
      public void doAction(Object paramObject) {
        if ((paramObject instanceof Aircraft)) {
          Aircraft localAircraft = (Aircraft)paramObject;
          if ((Actor.isValid(localAircraft)) && (Mission.isPlaying()))
            localAircraft.detachGun(-1);
        }
      }
    };
  }

  public BulletAircraftGeneric(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    super(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong);

    this.Wind = new Vector3d();
    this.Wind = paramVector3d1;

    if ((Mission.isPlaying()) && (!NetMissionTrack.isPlaying()) && (Actor.isValid(owner())) && (Actor.isValid(gun())) && ((gun() instanceof MGunAircraftGeneric)) && (owner() == World.getPlayerAircraft()) && (World.cur().diffCur.Limited_Ammo))
    {
      int i = gun().countBullets();
      this.bulletss = (i + hashCode());
      MGunAircraftGeneric localMGunAircraftGeneric = (MGunAircraftGeneric)gun();
      if ((localMGunAircraftGeneric.guardBullet != null) && 
        (i >= localMGunAircraftGeneric.guardBullet.bulletss - localMGunAircraftGeneric.guardBullet.hashCode()) && (
        (i != -1) || (!World.isPlayerGunner()))) {
        postRemove(owner());
      }

      localMGunAircraftGeneric.guardBullet = this;
    }
  }

  static float fv(double paramDouble)
  {
    return paramDouble > 1090.0D ? 333.0F : (fv[((int)paramDouble / 100)] + fv[((int)paramDouble / 100 + 1)]) / 2.0F;
  }
}