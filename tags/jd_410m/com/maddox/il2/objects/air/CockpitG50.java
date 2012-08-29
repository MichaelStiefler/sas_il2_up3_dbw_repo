package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Time;

public class CockpitG50 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private float pictAiler;
  private float pictElev;
  private float pictFlap;
  private float pictGear;
  private float pictRadiator;
  private float pictManifold;
  private Gun[] gun = { null, null };
  private int delay = 80;
  private boolean sightDamaged = false;

  private static final float[] speedometerScale = { 0.0F, 6.0F, 27.5F, 66.0F, 108.0F, 146.5F, 183.10001F, 217.5F, 251.0F, 281.5F, 310.5F, 388.0F, 394.0F };

  private static final float[] manifoldScale = { 0.0F, 56.0F, 111.0F, 166.5F, 220.5F, 276.0F, 327.5F };

  public CockpitG50()
  {
    super("3DO/Cockpit/G50/hier.him", "bf109");

    this.cockpitNightMats = new String[] { "texture07", "texture08", "texture09", "texture10", "texture11", "texture12", "texture15" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.gun[0] == null) {
      this.gun[0] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN01");
      this.gun[1] = ((Aircraft)this.fm.actor).getGunByHookName("_MGUN02");
    }

    this.mesh.chunkVisible("Z_GearRedL", (this.fm.CT.getGear() == 0.0F) || (this.fm.Gears.isAnyDamaged()));
    this.mesh.chunkVisible("Z_GearRedR", (this.fm.CT.getGear() == 0.0F) || (this.fm.Gears.isAnyDamaged()));
    this.mesh.chunkVisible("Z_GearGreenL", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.lgear));
    this.mesh.chunkVisible("Z_GearGreenR", (this.fm.CT.getGear() == 1.0F) && (this.fm.Gears.rgear));

    this.mesh.chunkSetAngles("Z_Columnbase", 0.0F, 0.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);
    this.mesh.chunkSetAngles("Z_Column", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Pedal1", 15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Pedal2", 15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Pedal3", 15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Pedal4", 15.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Gear", 50.0F * (this.pictGear = 0.85F * this.pictGear + 0.15F * this.fm.CT.GearControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Flap", 50.0F * (this.pictFlap = 0.85F * this.pictFlap + 0.15F * this.fm.CT.FlapsControl), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Altimeter1", -cvt(this.fm.getAltitude(), 0.0F, 20000.0F, 0.0F, 7200.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Altimeter2", -cvt(this.fm.getAltitude(), 0.0F, 20000.0F, 0.0F, 720.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Compass1", -60.0F - interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Hour1", 0.0F, 0.0F, cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F));
    this.mesh.chunkSetAngles("Z_Minute1", 0.0F, 0.0F, cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F));
    this.mesh.chunkSetAngles("Z_Secund1", 0.0F, 0.0F, cvt(World.getTimeofDay() % 1.0F * 60.0F % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F));

    this.mesh.chunkSetAngles("Z_Speedometer1", -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 600.0F, 0.0F, 12.0F), speedometerScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Speedometer2", -floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 600.0F, 0.0F, 12.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Throtle1", 70.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Prop1", 70.0F * interp(this.setNew.prop, this.setOld.prop, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Mixture1", 70.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM1", -cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3000.0F, 0.0F, 316.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_TurnBank1", cvt(getBall(8.0D), -8.0F, 8.0F, 9.0F, -9.0F), 0.0F, 0.0F);
    float f;
    if (aircraft().isFMTrackMirror()) {
      f = aircraft().fmTrack().getCockpitAzimuthSpeed();
    } else {
      f = cvt((this.setNew.azimuth - this.setOld.azimuth) / Time.tickLenFs(), -9.0F, 9.0F, -24.0F, 24.0F);
      if (aircraft().fmTrack() != null)
        aircraft().fmTrack().setCockpitAzimuthSpeed(f);
    }
    this.mesh.chunkSetAngles("Z_TurnBank2", f, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Climb1", cvt(this.setNew.vspeed, -25.0F, 25.0F, 180.0F, -180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Fuel1", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 0.6F, 0.0F, -270.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oil1", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 15.0F, 0.0F, -300.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Oiltemp1", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 150.0F, 0.0F, -300.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Temp1", cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 350.0F, 0.0F, -74.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Prescockp1", -floatindex(cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.533288F, 1.33322F, 0.0F, 6.0F), manifoldScale), 0.0F, 0.0F);

    resetYPRmodifier();
    xyz[2] = cvt(this.gun[0].countBullets(), 0.0F, 150.0F, 0.0F, 0.06025F);
    this.mesh.chunkSetLocate("Z_AmmoCounter1", xyz, ypr);
    xyz[2] = cvt(this.gun[1].countBullets(), 0.0F, 150.0F, 0.0F, 0.06025F);
    this.mesh.chunkSetLocate("Z_AmmoCounter2", xyz, ypr);

    this.mesh.chunkSetAngles("Z_gunsight_rim", 50.0F * this.setNew.stbyPosition, 0.0F, 0.0F);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  public void doToggleAim(boolean paramBoolean)
  {
    super.doToggleAim(paramBoolean);
    if ((paramBoolean) && (this.sightDamaged))
    {
      HookPilot localHookPilot = HookPilot.current;
      localHookPilot.doAim(true);
      localHookPilot.setAim(new Point3d(-1.399999976158142D, 0.0D, 0.958320021629334D));
    }
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x80) != 0) {
      this.mesh.chunkVisible("Front", false);
      this.mesh.chunkVisible("Front_D1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("gunsight_lense", false);
      this.mesh.chunkVisible("D_gunsight_lense", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x40) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x4) != 0)) {
      this.mesh.chunkVisible("XHullDamage1", true);
    }
    if (((this.fm.AS.astateCockpitState & 0x8) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x10) != 0)) {
      this.mesh.chunkVisible("XHullDamage2", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      CockpitG50.access$102(CockpitG50.this, CockpitG50.this.setOld); CockpitG50.access$202(CockpitG50.this, CockpitG50.this.setNew); CockpitG50.access$302(CockpitG50.this, CockpitG50.this.setTmp);

      if (((CockpitG50.this.fm.AS.astateCockpitState & 0x2) != 0) && (CockpitG50.this.setNew.stbyPosition < 1.0F))
      {
        CockpitG50.access$410(CockpitG50.this);
        if (CockpitG50.this.delay <= 0)
        {
          CockpitG50.this.setNew.stbyPosition = (CockpitG50.this.setOld.stbyPosition + 0.03F);
          CockpitG50.this.setOld.stbyPosition = CockpitG50.this.setNew.stbyPosition;
          CockpitG50.access$502(CockpitG50.this, true);
        }
      }

      CockpitG50.this.setNew.throttle = ((10.0F * CockpitG50.this.setOld.throttle + CockpitG50.this.fm.CT.PowerControl) / 11.0F);
      CockpitG50.this.setNew.prop = ((10.0F * CockpitG50.this.setOld.prop + CockpitG50.this.fm.EI.engines[0].getControlProp()) / 11.0F);
      CockpitG50.this.setNew.mix = ((10.0F * CockpitG50.this.setOld.mix + CockpitG50.this.fm.EI.engines[0].getControlMix()) / 11.0F);
      CockpitG50.this.setNew.vspeed = ((199.0F * CockpitG50.this.setOld.vspeed + CockpitG50.this.fm.getVertSpeed()) / 200.0F);
      CockpitG50.this.setNew.azimuth = CockpitG50.this.fm.Or.getYaw();
      if ((CockpitG50.this.setOld.azimuth > 270.0F) && (CockpitG50.this.setNew.azimuth < 90.0F)) CockpitG50.this.setOld.azimuth -= 360.0F;
      if ((CockpitG50.this.setOld.azimuth < 90.0F) && (CockpitG50.this.setNew.azimuth > 270.0F)) CockpitG50.this.setOld.azimuth += 360.0F;
      CockpitG50.this.setNew.waypointAzimuth = ((10.0F * CockpitG50.this.setOld.waypointAzimuth + (CockpitG50.this.waypointAzimuth(30.0F) - CockpitG50.this.setOld.azimuth)) / 11.0F);
      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float prop;
    float mix;
    float azimuth;
    float waypointAzimuth;
    float vspeed;
    float stbyPosition;
    private final CockpitG50 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitG50.1 arg2) { this();
    }
  }
}