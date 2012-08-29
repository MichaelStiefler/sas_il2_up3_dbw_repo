package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.game.Mission;
import com.maddox.rts.Time;
import com.maddox.sound.Sample;
import com.maddox.sound.SoundFX;

public class CockpitMosquito6 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  public Vector3f w = new Vector3f();
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictBrake = 0.0F;
  private float pictFlap = 0.0F;
  private float pictGear = 0.0F;
  private float pictBbay = 0.0F;
  private float pictSupc = 0.0F;
  private float pictLlit = 0.0F;
  private float pictManf1 = 1.0F;
  private float pictManf2 = 1.0F;
  private boolean bNeedSetUp = true;
  private int iWiper = 0;

  private static final float[] speedometerScale = { 0.0F, 16.5F, 31.0F, 60.5F, 90.0F, 120.5F, 151.5F, 182.0F, 213.5F, 244.0F, 274.0F, 303.0F, 333.5F, 369.5F, 399.0F, 434.5F, 465.5F, 496.5F, 527.5F, 558.5F, 588.5F, 626.5F };

  private static final float[] radScale = { 0.0F, 0.1F, 0.2F, 0.3F, 3.5F, 11.0F, 22.0F, 37.5F, 58.5F, 82.5F, 112.5F, 147.0F, 187.0F, 236.0F, 298.5F };

  private static final float[] boostScale = { 0.0F, 21.0F, 39.0F, 56.0F, 90.5F, 109.5F, 129.0F, 146.5F, 163.0F, 179.5F, 196.0F, 212.5F, 231.5F, 250.5F };

  private static final float[] variometerScale = { -158.0F, -111.0F, -65.5F, -32.5F, 0.0F, 32.5F, 65.5F, 111.0F, 158.0F };

  private Point3d tmpP = new Point3d();
  private Vector3d tmpV = new Vector3d();

  private SoundFX fxw = null;
  private Sample wiStart = new Sample("wip_002_s.wav", 256, 65535);
  private Sample wiRun = new Sample("wip_002.wav", 256, 65535);
  private int wiState = 0;

  protected float waypointAzimuth()
  {
    return super.waypointAzimuthInvertMinus(10.0F);
  }

  public CockpitMosquito6()
  {
    super("3DO/Cockpit/Mosquito_FB_MkVI/hier.him", "he111");

    this.cockpitNightMats = new String[] { "01", "02", "03", "04", "05", "12", "20", "23", "24", "26", "27", "01_damage", "03_damage", "04_damage", "24_damage" };

    setNightMats(false);

    interpPut(new Interpolater(), null, Time.current(), null);
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    if (this.bNeedSetUp) {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    this.mesh.chunkSetAngles("Z_Wiper1", cvt(interp(this.setNew.wiper, this.setOld.wiper, paramFloat), -1.0F, 1.0F, -61.0F, 61.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("XLampGearUpL", this.fm.CT.getGear() < 0.01F);
    this.mesh.chunkVisible("XLampGearDownL", this.fm.CT.getGear() > 0.99F);
    this.mesh.chunkVisible("XLampFuel1", this.fm.EI.engines[0].getRPM() < 300.0F);
    this.mesh.chunkVisible("XLampFuel2", this.fm.EI.engines[1].getRPM() < 300.0F);

    this.mesh.chunkSetAngles("Z_Columnbase", 12.0F * (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Column", 45.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_ColumnSwitch", 20.0F * (this.pictBrake = 0.91F * this.pictBrake + 0.09F * this.fm.CT.BrakeControl), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle1", 62.720001F * interp(this.setNew.throttle1, this.setOld.throttle1, paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Throtle2", 62.720001F * interp(this.setNew.throttle2, this.setOld.throttle2, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_LeftPedal", -20.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RightPedal", 20.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Gear1", -55.0F * (this.pictGear = 0.9F * this.pictGear + 0.1F * this.fm.CT.GearControl), 0.0F, 0.0F);
    if (Math.abs(this.fm.CT.FlapsControl - this.fm.CT.getFlap()) > 0.02F) {
      if (this.fm.CT.FlapsControl - this.fm.CT.getFlap() > 0.0F)
        f1 = -0.0299F;
      else
        f1 = -0.0F;
    }
    else f1 = -0.0144F;

    this.pictFlap = (0.8F * this.pictFlap + 0.2F * f1);
    resetYPRmodifier();
    xyz[2] = this.pictFlap;
    this.mesh.chunkSetLocate("Z_Flaps1", xyz, ypr);
    this.mesh.chunkSetAngles("Z_Trim1", -1000.0F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trim2", 1000.0F * this.fm.CT.getTrimAileronControl(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trim3", 90.0F * this.fm.CT.getTrimRudderControl(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_Prop1", 90.0F * this.setNew.prop1, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Prop2", 90.0F * this.setNew.prop2, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_BombBay1", 70.0F * (this.pictBbay = 0.9F * this.pictBbay + 0.1F * this.fm.CT.BayDoorControl), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("COMPASS_M", 90.0F + this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("SHKALA_DIRECTOR", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass1", this.setNew.azimuth.getDeg(paramFloat), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Compass2", this.setNew.waypointAzimuth.getDeg(paramFloat * 0.1F) + 90.0F, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STREL_ALT_LONG", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 10800.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("STREL_ALT_SHORT", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 9144.0F, 0.0F, 1080.0F), 0.0F, 0.0F);

    this.pictManf1 = (0.91F * this.pictManf1 + 0.09F * this.fm.EI.engines[0].getManifoldPressure());
    float f1 = this.pictManf1 - 1.0F;
    float f2 = 1.0F;
    if (f1 <= 0.0F) {
      f2 = -1.0F;
    }
    f1 = Math.abs(f1);
    this.mesh.chunkSetAngles("STRELKA_BOOST1", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13.0F), boostScale), 0.0F, 0.0F);

    this.pictManf2 = (0.91F * this.pictManf2 + 0.09F * this.fm.EI.engines[1].getManifoldPressure());
    f1 = this.pictManf2 - 1.0F;
    f2 = 1.0F;
    if (f1 <= 0.0F) {
      f2 = -1.0F;
    }
    f1 = Math.abs(f1);
    this.mesh.chunkSetAngles("STRELKA_BOOST2", f2 * floatindex(cvt(f1, 0.0F, 1.792637F, 0.0F, 13.0F), boostScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELKA_FUEL1", cvt(this.fm.M.fuel, 0.0F, 763.0F, 0.0F, 301.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("STRELKA_FUEL2", cvt(this.fm.M.fuel, 0.0F, 763.0F, 0.0F, 301.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("STRELKA_FUEL3", cvt((float)Math.sqrt(this.fm.M.fuel), 0.0F, (float)Math.sqrt(88.379997253417969D), 0.0F, 301.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("STRELKA_FUEL4", cvt(this.fm.M.fuel, 1022.0F, 1200.0F, 0.0F, 301.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("STRELKA_FUEL5", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("STRELKA_FUEL6", 0.0F, 0.0F, 0.0F);
    this.mesh.chunkSetAngles("STRELKA_FUEL7", cvt(this.fm.M.fuel, 851.0F, 1123.0F, 0.0F, 301.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("STRELKA_FUEL8", cvt(this.fm.M.fuel, 851.0F, 1123.0F, 0.0F, 301.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_RPM_SHORT1", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM_LONG1", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM_SHORT2", cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 10000.0F, 0.0F, 360.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_RPM_LONG2", cvt(this.fm.EI.engines[1].getRPM(), 0.0F, 10000.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Z_TEMP_OIL1", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 100.0F, 0.0F, 266.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TEMP_OIL2", cvt(this.fm.EI.engines[1].tOilOut, 0.0F, 100.0F, 0.0F, 266.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TEMP_RAD1", floatindex(cvt(this.fm.EI.engines[0].tWaterOut, 0.0F, 140.0F, 0.0F, 14.0F), radScale), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_TEMP_RAD2", floatindex(cvt(this.fm.EI.engines[1].tWaterOut, 0.0F, 140.0F, 0.0F, 14.0F), radScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STR_OIL_LB1", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 10.0F, 0.0F, -31.0F), 0.0F);
    this.mesh.chunkSetAngles("STR_OIL_LB2", 0.0F, cvt(1.0F + 0.05F * this.fm.EI.engines[1].tOilOut, 0.0F, 10.0F, 0.0F, -31.0F), 0.0F);
    this.mesh.chunkSetAngles("STRELK_TURN_UP", cvt(getBall(8.0D), -8.0F, 8.0F, 31.0F, -31.0F), 0.0F, 0.0F);
    this.w.set(this.fm.getW());
    this.fm.Or.transform(this.w);
    this.mesh.chunkSetAngles("STREL_TURN_DOWN", cvt(this.w.z, -0.23562F, 0.23562F, -50.0F, 50.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("STRELK_V_LONG", floatindex(cvt(Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeed()), 26.822403F, 214.57922F, 0.0F, 21.0F), speedometerScale), 0.0F, 0.0F);
    this.mesh.chunkVisible("STRELK_V_SHORT", false);

    this.mesh.chunkSetAngles("STRELKA_GOS", -this.fm.Or.getKren(), 0.0F, 0.0F);
    resetYPRmodifier();
    xyz[1] = cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 0.02355F, -0.02355F);
    this.mesh.chunkSetLocate("STRELKA_GOR", xyz, ypr);
    if ((this.fm.AS.astateCockpitState & 0x40) == 0) {
      this.mesh.chunkSetAngles("STR_CLIMB", floatindex(cvt(this.setNew.vspeed, -20.32F, 20.32F, 0.0F, 8.0F), variometerScale), 0.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Z_FlapPos", cvt(this.fm.CT.getFlap(), 0.0F, 1.0F, 0.0F, 125.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trim1Pos", -104.0F * this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Z_Trim2Pos", 208.0F * this.fm.CT.getTrimAileronControl(), 0.0F, 0.0F);
  }

  public void reflectCockpitState()
  {
    if (((this.fm.AS.astateCockpitState & 0x80) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x4) != 0)) {
      this.mesh.chunkVisible("HullDamage3", true);
      this.mesh.chunkVisible("XGlassDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("HullDamage4", true);
      this.mesh.chunkVisible("XGlassDamage3", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x10) != 0) {
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("XGlassDamage4", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("XGlassDamage4", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("HullDamage1", true);
      this.mesh.chunkVisible("HullDamage2", true);
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("Panel_D0", false);
      this.mesh.chunkVisible("Panel_D1", true);
      this.mesh.chunkVisible("STRELKA_FUEL2", false);
      this.mesh.chunkVisible("STRELKA_FUEL3", false);
      this.mesh.chunkVisible("STRELKA_FUEL4", false);
      this.mesh.chunkVisible("STRELKA_FUEL5", false);
      this.mesh.chunkVisible("STRELKA_FUEL6", false);
      this.mesh.chunkVisible("STRELKA_FUEL7", false);

      this.mesh.chunkVisible("STRELKA_BOOST1", false);
      this.mesh.chunkVisible("Z_TEMP_OIL1", false);
      this.mesh.chunkVisible("Z_TEMP_OIL2", false);
      this.mesh.chunkVisible("Z_TEMP_RAD1", false);
      this.mesh.chunkVisible("STRELK_V_LONG", false);
      this.mesh.chunkVisible("STRELK_V_SHORT", false);
      this.mesh.chunkVisible("STRELKA_GOR", false);
      this.mesh.chunkVisible("STRELKA_GOS", false);
      this.mesh.chunkVisible("STREL_ALT_LONG", false);
      this.mesh.chunkVisible("STREL_ALT_SHORT", false);
      this.mesh.chunkVisible("STRELK_TURN_UP", false);
      this.mesh.chunkVisible("Z_FlapPos", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlassDamage1", true);
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("XGlassDamage4", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("XGlassDamage2", true);
      this.mesh.chunkVisible("XGlassDamage3", true);
      this.mesh.chunkVisible("XGlassDamage4", true);
    }
    retoggleLight();
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
  }

  public void toggleLight()
  {
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

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitMosquito6.this.fm != null) {
        CockpitMosquito6.access$102(CockpitMosquito6.this, CockpitMosquito6.this.setOld); CockpitMosquito6.access$202(CockpitMosquito6.this, CockpitMosquito6.this.setNew); CockpitMosquito6.access$302(CockpitMosquito6.this, CockpitMosquito6.this.setTmp);

        CockpitMosquito6.this.setNew.throttle1 = (0.85F * CockpitMosquito6.this.setOld.throttle1 + CockpitMosquito6.this.fm.EI.engines[0].getControlThrottle() * 0.15F);
        CockpitMosquito6.this.setNew.prop1 = (0.85F * CockpitMosquito6.this.setOld.prop1 + CockpitMosquito6.this.fm.EI.engines[0].getControlProp() * 0.15F);

        CockpitMosquito6.this.setNew.throttle2 = (0.85F * CockpitMosquito6.this.setOld.throttle2 + CockpitMosquito6.this.fm.EI.engines[1].getControlThrottle() * 0.15F);
        CockpitMosquito6.this.setNew.prop2 = (0.85F * CockpitMosquito6.this.setOld.prop2 + CockpitMosquito6.this.fm.EI.engines[1].getControlProp() * 0.15F);

        CockpitMosquito6.this.setNew.altimeter = CockpitMosquito6.this.fm.getAltitude();

        float f = CockpitMosquito6.this.waypointAzimuth();

        if (CockpitMosquito6.this.useRealisticNavigationInstruments())
        {
          CockpitMosquito6.this.setNew.waypointAzimuth.setDeg(f - 90.0F);
          CockpitMosquito6.this.setOld.waypointAzimuth.setDeg(f - 90.0F);
        }
        else
        {
          CockpitMosquito6.this.setNew.waypointAzimuth.setDeg(CockpitMosquito6.this.setOld.waypointAzimuth.getDeg(0.1F), f);
        }

        CockpitMosquito6.this.setNew.azimuth.setDeg(CockpitMosquito6.this.setOld.azimuth.getDeg(1.0F), CockpitMosquito6.this.fm.Or.azimut());
        CockpitMosquito6.this.setNew.vspeed = (0.99F * CockpitMosquito6.this.setOld.vspeed + 0.01F * CockpitMosquito6.this.fm.getVertSpeed());

        switch (CockpitMosquito6.this.iWiper) {
        case 0:
          if ((Mission.curCloudsType() <= 4) || 
            (CockpitMosquito6.this.fm.getSpeedKMH() >= 220.0F) || (CockpitMosquito6.this.fm.getAltitude() >= Mission.curCloudsHeight() + 300.0F)) break;
          CockpitMosquito6.access$402(CockpitMosquito6.this, 1); break;
        case 1:
          CockpitMosquito6.this.setNew.wiper = (CockpitMosquito6.this.setOld.wiper - 0.05F);
          if (CockpitMosquito6.this.setNew.wiper < -1.03F) {
            CockpitMosquito6.access$408(CockpitMosquito6.this);
          }
          if (CockpitMosquito6.this.wiState >= 2) break;
          if (CockpitMosquito6.this.wiState == 0) {
            if (CockpitMosquito6.this.fxw == null) {
              CockpitMosquito6.access$602(CockpitMosquito6.this, CockpitMosquito6.this.aircraft().newSound("aircraft.wiper", false));
              if (CockpitMosquito6.this.fxw != null) {
                CockpitMosquito6.this.fxw.setParent(CockpitMosquito6.this.aircraft().getRootFX());
                CockpitMosquito6.this.fxw.setPosition(CockpitMosquito6.this.sfxPos);
              }
            }
            if (CockpitMosquito6.this.fxw != null) CockpitMosquito6.this.fxw.play(CockpitMosquito6.this.wiStart);
          }
          if (CockpitMosquito6.this.fxw == null) break;
          CockpitMosquito6.this.fxw.play(CockpitMosquito6.this.wiRun);
          CockpitMosquito6.access$502(CockpitMosquito6.this, 2); break;
        case 2:
          CockpitMosquito6.this.setNew.wiper = (CockpitMosquito6.this.setOld.wiper + 0.05F);
          if (CockpitMosquito6.this.setNew.wiper > 1.03F) {
            CockpitMosquito6.access$408(CockpitMosquito6.this);
          }
          if (CockpitMosquito6.this.wiState <= 1) break; CockpitMosquito6.access$502(CockpitMosquito6.this, 1); break;
        case 3:
          CockpitMosquito6.this.setNew.wiper = (CockpitMosquito6.this.setOld.wiper - 0.05F);
          if (CockpitMosquito6.this.setNew.wiper >= 0.02F) break;
          if ((CockpitMosquito6.this.fm.getSpeedKMH() > 250.0F) || (CockpitMosquito6.this.fm.getAltitude() > Mission.curCloudsHeight() + 400.0F))
            CockpitMosquito6.access$408(CockpitMosquito6.this);
          else
            CockpitMosquito6.access$402(CockpitMosquito6.this, 1); break;
        case 4:
          CockpitMosquito6.this.setNew.wiper = CockpitMosquito6.this.setOld.wiper;
          CockpitMosquito6.access$402(CockpitMosquito6.this, 0);
          CockpitMosquito6.access$502(CockpitMosquito6.this, 0);
          if (CockpitMosquito6.this.fxw == null) break; CockpitMosquito6.this.fxw.cancel();
        }
      }

      return true;
    }
  }

  private class Variables
  {
    float throttle1;
    float throttle2;
    float prop1;
    float prop2;
    float altimeter;
    float vspeed;
    AnglesFork azimuth;
    AnglesFork waypointAzimuth;
    float wiper;
    private final CockpitMosquito6 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.azimuth = new AnglesFork();
      this.waypointAzimuth = new AnglesFork();
    }

    Variables(CockpitMosquito6.1 arg2)
    {
      this();
    }
  }
}