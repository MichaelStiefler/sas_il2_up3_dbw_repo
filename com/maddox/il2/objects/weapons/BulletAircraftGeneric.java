package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.BulletGeneric;
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
  private static final int[] fv = { 1, 1, 5, 15, 52, 87, 123, 160, 196, 233, 269, 333 };

  public void move(float paramFloat)
  {
    if (this.jdField_gun_of_type_ComMaddoxIl2EngineGunGeneric == null) return;

    Point3d localPoint3d = this.jdField_p1_of_type_ComMaddoxJGPPoint3d; this.jdField_p1_of_type_ComMaddoxJGPPoint3d = this.jdField_p0_of_type_ComMaddoxJGPPoint3d; this.jdField_p0_of_type_ComMaddoxJGPPoint3d = localPoint3d;
    BulletGeneric.dspeed.scale(this.jdField_gun_of_type_ComMaddoxIl2EngineGunGeneric.bulletKV[indx()] * paramFloat * 1.0F * fv(this.jdField_speed_of_type_ComMaddoxJGPVector3d.length()) / this.jdField_speed_of_type_ComMaddoxJGPVector3d.length(), this.jdField_speed_of_type_ComMaddoxJGPVector3d);

    BulletGeneric.dspeed.z += this.jdField_gun_of_type_ComMaddoxIl2EngineGunGeneric.bulletAG[indx()] * paramFloat;
    this.jdField_speed_of_type_ComMaddoxJGPVector3d.add(BulletGeneric.dspeed);
    this.jdField_p1_of_type_ComMaddoxJGPPoint3d.scaleAdd(paramFloat, this.jdField_speed_of_type_ComMaddoxJGPVector3d, this.jdField_p0_of_type_ComMaddoxJGPPoint3d);
  }

  public void timeOut()
  {
    if (gun() != null)
      Explosions.generateExplosion(null, this.jdField_p1_of_type_ComMaddoxJGPPoint3d, gun().prop.bullet[indx()].power, gun().prop.bullet[indx()].powerType, gun().prop.bullet[indx()].powerRadius, 0.0D);
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

  public BulletAircraftGeneric(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    super(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong);

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