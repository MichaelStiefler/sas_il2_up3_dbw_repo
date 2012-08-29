package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class CockpitB17G_FGunner extends CockpitGunner
{
  private static final float[] speedometerScale = { 0.0F, 2.5F, 54.0F, 104.0F, 154.5F, 205.5F, 224.0F, 242.0F, 259.5F, 277.5F, 296.25F, 314.0F, 334.0F, 344.5F };

  private static Point3d P1 = new Point3d();
  private static Vector3d V = new Vector3d();

  public void moveGun(Orient paramOrient)
  {
    super.moveGun(paramOrient);
    this.mesh.chunkSetAngles("TurretA", 0.0F, -paramOrient.getYaw(), 0.0F);
    this.mesh.chunkSetAngles("TurretB", 0.0F, 15.0F + paramOrient.getTangage(), 0.0F);
    this.mesh.chunkSetAngles("TurretC", 0.0F, -cvt(paramOrient.getYaw(), -17.0F, 17.0F, -17.0F, 17.0F), 0.0F);

    this.mesh.chunkSetAngles("TurretD", 0.0F, cvt(paramOrient.getTangage(), -10.0F, 15.0F, -10.0F, 15.0F), 0.0F);
  }

  public void clipAnglesGun(Orient paramOrient)
  {
    if (isRealMode())
      if (!aiTurret().bIsOperable) {
        paramOrient.setYPR(0.0F, 0.0F, 0.0F);
      } else {
        float f1 = paramOrient.getYaw();
        float f2 = paramOrient.getTangage();
        if (f1 < -43.0F)
          f1 = -43.0F;
        if (f1 > 43.0F)
          f1 = 43.0F;
        if (f2 > 30.0F)
          f2 = 30.0F;
        if (f2 < -45.0F)
          f2 = -45.0F;
        paramOrient.setYPR(f1, f2, 0.0F);
        paramOrient.wrap();
      }
  }

  protected void interpTick()
  {
    if (isRealMode()) {
      if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      {
        this.bGunFire = false;
      }this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
    }
  }

  public void doGunFire(boolean paramBoolean) {
    if (isRealMode()) {
      if ((this.emitter == null) || (!this.emitter.haveBullets()) || (!aiTurret().bIsOperable))
      {
        this.bGunFire = false;
      }
      else this.bGunFire = paramBoolean;
      this.fm.CT.WeaponControl[weaponControlNum()] = this.bGunFire;
    }
  }

  public CockpitB17G_FGunner() {
    super("3DO/Cockpit/B-25J-FGun/FGunnerB17G.him", "bf109");
    this.cockpitNightMats = new String[] { "textrbm9", "texture25" };
    setNightMats(false);
  }

  public void reflectCockpitState() {
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
      this.mesh.chunkVisible("XGlassDamage1", true);
    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
      this.mesh.chunkVisible("XGlassDamage2", true);
    if ((this.fm.AS.astateCockpitState & 0x20) != 0)
      this.mesh.chunkVisible("XGlassDamage2", true);
    if ((this.fm.AS.astateCockpitState & 0x2) != 0)
      this.mesh.chunkVisible("XGlassDamage3", true);
    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
      this.mesh.chunkVisible("XHullDamage1", true);
    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
      this.mesh.chunkVisible("XHullDamage2", true);
  }

  public void reflectWorldToInstruments(float paramFloat) {
    this.mesh.chunkSetAngles("zSpeed", 0.0F, floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 836.85901F, 0.0F, 13.0F), speedometerScale), 0.0F);

    this.mesh.chunkSetAngles("zSpeed1", 0.0F, floatindex(cvt(this.fm.getSpeedKMH(), 0.0F, 836.85901F, 0.0F, 13.0F), speedometerScale), 0.0F);

    this.mesh.chunkSetAngles("zAlt1", 0.0F, cvt((float)this.fm.Loc.z, 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F);

    this.mesh.chunkSetAngles("zAlt2", 0.0F, cvt((float)this.fm.Loc.z, 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F);

    this.mesh.chunkSetAngles("zHour", 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F);

    this.mesh.chunkSetAngles("zMinute", 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zSecond", 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F);

    this.mesh.chunkSetAngles("zCompass1", 0.0F, this.fm.Or.getAzimut(), 0.0F);
    float f = 0.0F;
    WayPoint localWayPoint = this.fm.AP.way.curr();
    if (localWayPoint != null) {
      localWayPoint.getP(P1);
      V.sub(P1, this.fm.Loc);
      f = (float)(57.295779513082323D * Math.atan2(V.x, V.y));
      this.mesh.chunkSetAngles("zCompass2", 0.0F, 90.0F + f, 0.0F);
    }
  }

  public void toggleLight() {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  private void retoggleLight() {
    if (this.cockpitLightControl) {
      setNightMats(false);
      setNightMats(true);
    } else {
      setNightMats(true);
      setNightMats(false);
    }
  }

  static
  {
    Property.set(CockpitB17G_FGunner.class, "aiTuretNum", 0);

    Property.set(CockpitB17G_FGunner.class, "weaponControlNum", 10);

    Property.set(CockpitB17G_FGunner.class, "astatePilotIndx", 2);
  }
}