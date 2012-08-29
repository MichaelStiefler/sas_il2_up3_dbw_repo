package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

public class CockpitP_39Q10 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private LightPointActor light1;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;

  private static final float[] speedometerScale = { 0.0F, 17.0F, 80.5F, 143.5F, 205.0F, 227.0F, 249.0F, 271.5F, 294.0F, 317.0F, 339.5F, 341.5F };

  private static final float[] variometerScale = { 0.0F, 25.0F, 49.5F, 64.0F, 78.5F, 89.5F, 101.0F, 112.0F, 123.0F, 134.5F, 145.5F, 157.0F, 168.0F, 168.0F };

  public CockpitP_39Q10()
  {
    super("3do/cockpit/P-39Q-10/hier.him", "p39");

    this.light1 = new LightPointActor(new LightPoint(), new Point3d(0.6D, 0.0D, 0.8D));
    this.light1.light.setColor(232.0F, 75.0F, 44.0F);
    this.light1.light.setEmit(0.0F, 0.0F);
    this.pos.base().draw.lightMap().put("LAMPHOOK1", this.light1);

    interpPut(new Interpolater(), null, Time.current(), null);
    AircraftLH.printCompassHeading = true;
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    this.mesh.chunkSetAngles("Stick", 0.0F, (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl) * 15.0F, (this.pictElev = 0.85F * this.pictElev + 0.15F * this.fm.CT.ElevatorControl) * 10.0F);

    this.mesh.chunkSetAngles("PedalBaseLeft", 0.0F, 0.0F, -20.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("PedalBaseRight", 0.0F, 0.0F, 20.0F * this.fm.CT.getRudder());
    this.mesh.chunkSetAngles("PedalLeft", 20.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("PedalRight", -20.0F * this.fm.CT.getRudder(), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Throttle", 0.0F, 0.0F, 25.0F - interp(this.setNew.throttle, this.setOld.throttle, paramFloat) * 52.0F * 0.91F);
    this.mesh.chunkSetAngles("Propeller", 0.0F, 0.0F, 29.0F - (this.fm.CT.getStepControl() >= 0.0F ? 1.0F - this.fm.CT.getStepControl() : interp(this.setNew.throttle, this.setOld.throttle, paramFloat)) * 59.0F * 0.91F);

    this.mesh.chunkSetAngles("Alt1", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 30480.0F, 0.0F, 36000.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Alt2", cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 30480.0F, 0.0F, 3600.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("TurnInd", interp(this.setNew.azimuthMag, this.setOld.azimuthMag, paramFloat), 0.0F, 0.0F);

    if ((this.fm.AS.astateCockpitState & 0x2) == 0) {
      this.mesh.chunkSetAngles("FltInd", this.fm.Or.getKren(), 0.0F, 0.0F);
      this.mesh.chunkSetAngles("FltIndMesh", 0.0F, 0.0F, cvt(this.fm.Or.getTangage(), -45.0F, 45.0F, 1.5F, -1.5F));
    }

    this.mesh.chunkSetAngles("SettingStrelki", interp(this.setNew.waypointAzimuth, this.setOld.waypointAzimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("CompassStrelka", interp(this.setNew.azimuth, this.setOld.azimuth, paramFloat), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("AirSpeed", floatindex(cvt(0.6213711F * Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH()), 0.0F, 500.0F, 0.0F, 10.0F), speedometerScale), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Climb", this.setNew.vspeed >= 0.0F ? floatindex(cvt(this.setNew.vspeed / 5.08F, 0.0F, 6.0F, 0.0F, 12.0F), variometerScale) : -floatindex(cvt(-this.setNew.vspeed / 5.08F, 0.0F, 6.0F, 0.0F, 12.0F), variometerScale), 0.0F, 0.0F);
    float f;
    if (aircraft().isFMTrackMirror()) {
      f = aircraft().fmTrack().getCockpitAzimuthSpeed();
    } else {
      f = cvt((this.setNew.azimuth - this.setOld.azimuth) / Time.tickLenFs(), -3.0F, 3.0F, -30.0F, 30.0F);
      if (aircraft().fmTrack() != null)
        aircraft().fmTrack().setCockpitAzimuthSpeed(f);
    }
    this.mesh.chunkSetAngles("TurnStrelka", f, 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Hodilka", cvt(getBall(7.0D), -7.0F, 7.0F, -15.0F, 15.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("ManPress", cvt(this.fm.EI.engines[0].getManifoldPressure(), 0.3386F, 2.5398F, 0.0F, 345.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("CoolantTemp", cvt(this.fm.EI.engines[0].tOilIn, 40.0F, 160.0F, 0.0F, 116.5F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Engine", cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 140.0F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Oil", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 17.236897F, 0.0F, 180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Fuel", cvt(this.fm.M.fuel > 1.0F ? 0.26F : 0.0F, 0.0F, 1.72369F, 0.0F, -180.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Liquid1", cvt(this.fm.M.fuel / 0.72F, 0.0F, 454.24933F, 0.0F, 97.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Liquid2", cvt(this.fm.M.fuel / 0.72F, 0.0F, 454.24933F, 0.0F, 97.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("RPMStrelka", cvt(this.fm.EI.engines[0].getRPM(), 0.0F, 3500.0F, 0.0F, 280.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Hour", cvt(World.getTimeofDay(), 0.0F, 24.0F, 0.0F, 720.0F), 0.0F, 0.0F);
    this.mesh.chunkSetAngles("Min", cvt(World.getTimeofDay() % 1.0F, 0.0F, 1.0F, 0.0F, 360.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("OilPress", cvt(1.0F + 0.05F * this.fm.EI.engines[0].tOilOut, 0.0F, 20.684277F, 0.0F, 300.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("CarbAir", cvt((Atmosphere.temperature((float)this.fm.Loc.z - 278.0F) + this.fm.EI.engines[0].tOilIn) / 2.0F, -50.0F, 50.0F, -52.0F, 52.0F), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("SuctStrelka", cvt(this.fm.EI.engines[0].getw() / 57.0F * Atmosphere.density((float)this.fm.Loc.z), 0.0F, 5.0F, 0.0F, 300.0F), 0.0F, 0.0F);

    this.mesh.chunkVisible("XFlareRed", (this.fm.CT.PowerControl < 0.333F) && (this.fm.CT.getGear() != 1.0F));
    this.mesh.chunkVisible("XFlareBlue", false);
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("Kollimator", false);
      this.mesh.chunkVisible("Z_Z_MASK", false);
      this.mesh.chunkVisible("Z_Z_RETICLE", false);
      this.mesh.chunkVisible("DKollimator", true);
      this.mesh.chunkVisible("Z_Z_MASK_D", true);
      this.mesh.chunkVisible("Z_Z_RETICLE_D", true);
      this.mesh.chunkVisible("XHullHoles1", true);
      this.mesh.chunkVisible("XHullHoles4", true);
      this.mesh.materialReplace("APanelUp", "DPanelUp");
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0) {
      this.mesh.chunkVisible("XGlAssHoles3", true);
      this.mesh.chunkVisible("XGlAssHoles4", true);
      this.mesh.chunkVisible("XGlAssHoles5", true);
      this.mesh.chunkVisible("XHullHoles1", true);
      this.mesh.materialReplace("APanelUp", "DPanelUp");
    }
    if ((this.fm.AS.astateCockpitState & 0x40) != 0) {
      this.mesh.chunkVisible("XHullHoles1", true);
      this.mesh.chunkVisible("XHullHoles6", true);
      this.mesh.materialReplace("GagePanel1", "DGagePanel1");
      this.mesh.chunkVisible("Climb", false);
      this.mesh.chunkVisible("ManPress", false);
      this.mesh.chunkVisible("Engine", false);
      this.mesh.chunkVisible("Oil", false);
      this.mesh.chunkVisible("Fuel", false);
      this.mesh.chunkVisible("RPMStrelka", false);
      this.mesh.materialReplace("GagePanel2", "DGagePanel2");
      this.mesh.chunkVisible("AirSpeed", false);
      this.mesh.chunkVisible("TurnStrelka", false);
      this.mesh.chunkVisible("Hodilka", false);
      this.mesh.chunkVisible("OilPress", false);
      this.mesh.chunkVisible("CarbAir", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x4) != 0) {
      this.mesh.chunkVisible("XGlAssHoles1", true);
      this.mesh.chunkVisible("XHullHoles1", true);
      this.mesh.chunkVisible("XHullHoles2", true);
      this.mesh.chunkVisible("XHullHoles4", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x8) != 0) {
      this.mesh.chunkVisible("XGlAssHoles3", true);
      this.mesh.chunkVisible("XHullHoles1", true);
      this.mesh.chunkVisible("XHullHoles3", true);
      this.mesh.materialReplace("GagePanel3", "DGagePanel3");
      this.mesh.chunkVisible("Alt1", false);
      this.mesh.chunkVisible("Alt2", false);
      this.mesh.chunkVisible("CoolantTemp", false);
      this.mesh.chunkVisible("Liquid1", false);
      this.mesh.chunkVisible("Liquid2", false);
      this.mesh.chunkVisible("SuctStrelka", false);
    }
    if (((this.fm.AS.astateCockpitState & 0x80) == 0) || 
      ((this.fm.AS.astateCockpitState & 0x10) != 0)) {
      this.mesh.chunkVisible("XGlAssHoles2", true);
      this.mesh.chunkVisible("XHullHoles1", true);
      this.mesh.chunkVisible("XHullHoles2", true);
      this.mesh.chunkVisible("XHullHoles6", true);
      this.mesh.chunkVisible("RDoorHandle", false);
    }
    if ((this.fm.AS.astateCockpitState & 0x20) != 0) {
      this.mesh.chunkVisible("XGlAssHoles3", true);
      this.mesh.chunkVisible("XHullHoles1", true);
      this.mesh.chunkVisible("XHullHoles4", true);
      this.mesh.chunkVisible("XHullHoles5", true);
      this.mesh.materialReplace("GagePanel4", "DGagePanel4");
      this.mesh.chunkVisible("TurnInd", false);
      this.mesh.chunkVisible("CompassStrelka", false);
      this.mesh.chunkVisible("SettingStrelki", false);
      this.mesh.chunkVisible("Hour", false);
      this.mesh.chunkVisible("Min", false);
    }
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      this.light1.light.setEmit(0.005F, 1.0F);
    else
      this.light1.light.setEmit(0.0F, 0.0F);
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitP_39Q10.this.fm != null) {
        CockpitP_39Q10.access$102(CockpitP_39Q10.this, CockpitP_39Q10.this.setOld); CockpitP_39Q10.access$202(CockpitP_39Q10.this, CockpitP_39Q10.this.setNew); CockpitP_39Q10.access$302(CockpitP_39Q10.this, CockpitP_39Q10.this.setTmp);

        CockpitP_39Q10.this.setNew.throttle = ((10.0F * CockpitP_39Q10.this.setOld.throttle + CockpitP_39Q10.this.fm.CT.PowerControl) / 11.0F);
        CockpitP_39Q10.this.setNew.altimeter = CockpitP_39Q10.this.fm.getAltitude();

        if (Math.abs(CockpitP_39Q10.this.fm.Or.getKren()) < 45.0F) {
          CockpitP_39Q10.this.setNew.azimuthMag = ((349.0F * CockpitP_39Q10.this.setOld.azimuthMag + CockpitP_39Q10.this.fm.Or.azimut()) / 350.0F);
        }
        if ((CockpitP_39Q10.this.setOld.azimuthMag > 270.0F) && (CockpitP_39Q10.this.setNew.azimuthMag < 90.0F)) CockpitP_39Q10.this.setOld.azimuthMag -= 360.0F;
        if ((CockpitP_39Q10.this.setOld.azimuthMag < 90.0F) && (CockpitP_39Q10.this.setNew.azimuthMag > 270.0F)) CockpitP_39Q10.this.setOld.azimuthMag += 360.0F;

        CockpitP_39Q10.this.setNew.azimuth = CockpitP_39Q10.this.fm.Or.azimut();
        if ((CockpitP_39Q10.this.setOld.azimuth > 270.0F) && (CockpitP_39Q10.this.setNew.azimuth < 90.0F)) CockpitP_39Q10.this.setOld.azimuth -= 360.0F;
        if ((CockpitP_39Q10.this.setOld.azimuth < 90.0F) && (CockpitP_39Q10.this.setNew.azimuth > 270.0F)) CockpitP_39Q10.this.setOld.azimuth += 360.0F;

        CockpitP_39Q10.this.setNew.vspeed = ((499.0F * CockpitP_39Q10.this.setOld.vspeed + CockpitP_39Q10.this.fm.getVertSpeed()) / 500.0F);
        CockpitP_39Q10.this.setNew.waypointAzimuth = ((10.0F * CockpitP_39Q10.this.setOld.waypointAzimuth + CockpitP_39Q10.this.waypointAzimuth(30.0F)) / 11.0F);
      }

      return true;
    }
  }

  private class Variables
  {
    float throttle;
    float altimeter;
    float azimuth;
    float azimuthMag;
    float vspeed;
    float waypointAzimuth;
    private final CockpitP_39Q10 this$0;

    private Variables()
    {
      this.this$0 = this$1; } 
    Variables(CockpitP_39Q10.1 arg2) { this();
    }
  }
}