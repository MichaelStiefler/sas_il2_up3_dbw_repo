package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.InterpolateRef;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.rts.Time;
import com.maddox.sound.ReverbFXRoom;

public class CockpitAVIA_B534 extends CockpitPilot
{
  private Variables setOld = new Variables(null);
  private Variables setNew = new Variables(null);
  private Variables setTmp;
  private Vector3f w = new Vector3f();
  private boolean bNeedSetUp = true;
  private float pictAiler = 0.0F;
  private float pictElev = 0.0F;
  private float pictSupc = 0.0F;
  private float pictManifold;
  private float tailWheelLock = 1.0F;
  private AVIA_B534 ac = null;
  private float rpmGeneratedPressure = 0.0F;
  private float oilPressure = 0.0F;
  private float hIncrement = 0.009600001F;
  private float vIncrement = 0.0F;
  private float hIncrementSize = 0.0024F;
  private float vIncrementSize = 0.0005F;
  private int previousCycle = 0;
  private int cycleCount = 5;
  private boolean turningCycle = false;
  private int cycleMultiplier = -1;
  private boolean gunLOK = true;
  private boolean gunROK = true;

  private static double compassZ = -0.2D;
  private double segLen1;
  private double segLen2;
  private double compassLimit;
  private static double compassLimitAngle = 25.0D;
  private Vector3d[] compassSpeed;
  int compassFirst = 0;
  private Vector3d accel;
  private Vector3d compassNorth;
  private Vector3d compassSouth;
  private double compassAcc;
  private double compassSc;

  public CockpitAVIA_B534()
  {
    super("3DO/Cockpit/AviaB-534/hier.him", "bf109");
    this.ac = ((AVIA_B534)aircraft());
    this.ac.registerPit(this);

    this.cockpitNightMats = new String[] { "Compass", "gauge1", "gauge2", "gauge3", "gauge4", "gauge5", "DM_gauge1", "DM_gauge2" };

    setNightMats(false);
    interpPut(new Interpolater(), null, Time.current(), null);

    if (this.acoustics != null)
      this.acoustics.globFX = new ReverbFXRoom(0.45F);
  }

  private void initCompass()
  {
    this.accel = new Vector3d();
    this.compassSpeed = new Vector3d[4];
    this.compassSpeed[0] = new Vector3d(0.0D, 0.0D, 0.0D);
    this.compassSpeed[1] = new Vector3d(0.0D, 0.0D, 0.0D);
    this.compassSpeed[2] = new Vector3d(0.0D, 0.0D, 0.0D);
    this.compassSpeed[3] = new Vector3d(0.0D, 0.0D, 0.0D);

    float[] arrayOfFloat1 = { 87.0F, 77.5F, 65.300003F, 41.5F, -0.3F, -43.5F, -62.900002F, -64.0F, -66.300003F, -75.800003F };

    float[] arrayOfFloat2 = { 55.799999F, 51.5F, 47.0F, 40.099998F, 33.799999F, 33.700001F, 32.700001F, 35.099998F, 46.599998F, 61.0F };

    float f1 = cvt(Engine.land().config.declin, -90.0F, 90.0F, 9.0F, 0.0F);

    float f2 = floatindex(f1, arrayOfFloat1);
    this.compassNorth = new Vector3d(0.0D, Math.cos(0.01745277777777778D * f2), -Math.sin(0.01745277777777778D * f2));
    this.compassSouth = new Vector3d(0.0D, -Math.cos(0.01745277777777778D * f2), Math.sin(0.01745277777777778D * f2));
    float f3 = floatindex(f1, arrayOfFloat2);

    this.compassNorth.scale(f3 / 600.0F * Time.tickLenFs());
    this.compassSouth.scale(f3 / 600.0F * Time.tickLenFs());

    this.segLen1 = (2.0D * Math.sqrt(1.0D - compassZ * compassZ));
    this.segLen2 = (this.segLen1 / Math.sqrt(2.0D));
    this.compassLimit = (-1.0D * Math.sin(0.0174532888888889D * compassLimitAngle));
    this.compassLimit *= this.compassLimit;

    this.compassAcc = (4.66666666D * Time.tickLenFs());
    this.compassSc = (0.101936799D / Time.tickLenFs() / Time.tickLenFs());
  }

  private void updateCompass()
  {
    if (this.compassFirst == 0)
    {
      initCompass();
      this.fm.getLoc(this.setOld.planeLoc);
    }

    this.fm.getLoc(this.setNew.planeLoc);

    this.setNew.planeMove.set(this.setNew.planeLoc);
    this.setNew.planeMove.sub(this.setOld.planeLoc);

    this.accel.set(this.setNew.planeMove);
    this.accel.sub(this.setOld.planeMove);

    this.accel.scale(this.compassSc);

    this.accel.x = (-this.accel.x);
    this.accel.y = (-this.accel.y);
    this.accel.z = (-this.accel.z - 1.0D);
    this.accel.scale(this.compassAcc);

    if (this.accel.length() > -compassZ * 0.7D) {
      this.accel.scale(-compassZ * 0.7D / this.accel.length());
    }

    for (int i = 0; i < 4; i++)
    {
      this.compassSpeed[i].set(this.setOld.compassPoint[i]);
      this.compassSpeed[i].sub(this.setNew.compassPoint[i]);
    }

    for (i = 0; i < 4; i++)
    {
      double d1 = this.compassSpeed[i].length();
      d1 = 0.985D / (1.0D + d1 * d1 * 15.0D);

      this.compassSpeed[i].scale(d1);
    }

    Vector3d localVector3d1 = new Vector3d();
    localVector3d1.set(this.setOld.compassPoint[0]);
    localVector3d1.add(this.setOld.compassPoint[1]);
    localVector3d1.add(this.setOld.compassPoint[2]);
    localVector3d1.add(this.setOld.compassPoint[3]);
    localVector3d1.normalize();

    for (int j = 0; j < 4; j++) {
      Vector3d localVector3d3 = new Vector3d();
      double d2 = localVector3d1.dot(this.compassSpeed[j]);
      localVector3d3.set(localVector3d1);
      d2 *= 0.28D;

      localVector3d3.scale(-d2);

      this.compassSpeed[j].add(localVector3d3);
    }

    for (j = 0; j < 4; j++)
      this.compassSpeed[j].add(this.accel);
    this.compassSpeed[0].add(this.compassNorth);
    this.compassSpeed[2].add(this.compassSouth);

    for (j = 0; j < 4; j++) {
      this.setNew.compassPoint[j].set(this.setOld.compassPoint[j]);
      this.setNew.compassPoint[j].add(this.compassSpeed[j]);
    }

    localVector3d1.set(this.setNew.compassPoint[0]);
    localVector3d1.add(this.setNew.compassPoint[1]);
    localVector3d1.add(this.setNew.compassPoint[2]);
    localVector3d1.add(this.setNew.compassPoint[3]);
    localVector3d1.scale(0.25D);
    Vector3d localVector3d2 = new Vector3d(localVector3d1);
    localVector3d2.normalize();
    localVector3d2.scale(-compassZ);
    localVector3d2.sub(localVector3d1);

    for (int k = 0; k < 4; k++) {
      this.setNew.compassPoint[k].add(localVector3d2);
    }

    for (k = 0; k < 4; k++)
      this.setNew.compassPoint[k].normalize();
    for (k = 0; k < 2; k++)
    {
      compassDist(this.setNew.compassPoint[0], this.setNew.compassPoint[2], this.segLen1);
      compassDist(this.setNew.compassPoint[1], this.setNew.compassPoint[3], this.segLen1);

      compassDist(this.setNew.compassPoint[0], this.setNew.compassPoint[1], this.segLen2);
      compassDist(this.setNew.compassPoint[2], this.setNew.compassPoint[3], this.segLen2);
      compassDist(this.setNew.compassPoint[1], this.setNew.compassPoint[2], this.segLen2);
      compassDist(this.setNew.compassPoint[3], this.setNew.compassPoint[0], this.segLen2);

      for (m = 0; m < 4; m++) {
        this.setNew.compassPoint[m].normalize();
      }
      compassDist(this.setNew.compassPoint[3], this.setNew.compassPoint[0], this.segLen2);
      compassDist(this.setNew.compassPoint[1], this.setNew.compassPoint[2], this.segLen2);
      compassDist(this.setNew.compassPoint[2], this.setNew.compassPoint[3], this.segLen2);
      compassDist(this.setNew.compassPoint[0], this.setNew.compassPoint[1], this.segLen2);

      compassDist(this.setNew.compassPoint[1], this.setNew.compassPoint[3], this.segLen1);
      compassDist(this.setNew.compassPoint[0], this.setNew.compassPoint[2], this.segLen1);
      for (m = 0; m < 4; m++) {
        this.setNew.compassPoint[m].normalize();
      }

    }

    Orientation localOrientation = new Orientation();
    this.fm.getOrient(localOrientation);
    for (int m = 0; m < 4; m++) {
      this.setNew.cP[m].set(this.setNew.compassPoint[m]);
      localOrientation.transformInv(this.setNew.cP[m]);
    }

    Vector3d localVector3d4 = new Vector3d();
    localVector3d4.set(this.setNew.cP[0]);
    localVector3d4.add(this.setNew.cP[1]);
    localVector3d4.add(this.setNew.cP[2]);
    localVector3d4.add(this.setNew.cP[3]);
    localVector3d4.scale(0.25D);
    Vector3d localVector3d5 = new Vector3d();
    localVector3d5.set(localVector3d4);
    localVector3d5.normalize();

    float f = (float)(localVector3d5.x * localVector3d5.x + localVector3d5.y * localVector3d5.y);
    if ((f > this.compassLimit) || (localVector3d4.z > 0.0D))
    {
      for (int n = 0; n < 4; n++)
      {
        this.setNew.cP[n].set(this.setOld.cP[n]);
        this.setNew.compassPoint[n].set(this.setOld.cP[n]);

        localOrientation.transform(this.setNew.compassPoint[n]);
      }

      localVector3d4.set(this.setNew.cP[0]);
      localVector3d4.add(this.setNew.cP[1]);
      localVector3d4.add(this.setNew.cP[2]);
      localVector3d4.add(this.setNew.cP[3]);
      localVector3d4.scale(0.25D);
    }

    localVector3d5.set(this.setNew.cP[0]);
    localVector3d5.sub(localVector3d4);

    double d3 = -Math.atan2(localVector3d4.y, -localVector3d4.z);
    vectorRot2(localVector3d4, d3);
    vectorRot2(localVector3d5, d3);

    double d4 = Math.atan2(localVector3d4.x, -localVector3d4.z);

    vectorRot1(localVector3d5, -d4);

    double d5 = Math.atan2(localVector3d5.y, localVector3d5.x);

    this.mesh.chunkSetAngles("NeedCompass_A", -(float)(d3 * 180.0D / 3.1415926D), -(float)(d4 * 180.0D / 3.1415926D), 0.0F);

    this.mesh.chunkSetAngles("NeedCompass_B", 0.0F, (float)(90.0D - d5 * 180.0D / 3.1415926D), 0.0F);

    this.compassFirst += 1;
  }

  private void vectorRot1(Vector3d paramVector3d, double paramDouble)
  {
    double d1 = Math.sin(paramDouble);
    double d2 = Math.cos(paramDouble);
    double d3 = paramVector3d.x * d2 - paramVector3d.z * d1;
    paramVector3d.z = (paramVector3d.x * d1 + paramVector3d.z * d2);
    paramVector3d.x = d3;
  }

  private void vectorRot2(Vector3d paramVector3d, double paramDouble) {
    double d1 = Math.sin(paramDouble);
    double d2 = Math.cos(paramDouble);
    double d3 = paramVector3d.y * d2 - paramVector3d.z * d1;
    paramVector3d.z = (paramVector3d.y * d1 + paramVector3d.z * d2);
    paramVector3d.y = d3;
  }

  private void compassDist(Vector3d paramVector3d1, Vector3d paramVector3d2, double paramDouble)
  {
    Vector3d localVector3d = new Vector3d();
    localVector3d.set(paramVector3d1);
    localVector3d.sub(paramVector3d2);
    double d = localVector3d.length();

    if (d < 1.0E-006D)
      d = 1.0E-006D;
    d = (paramDouble - d) / d / 2.0D;

    localVector3d.scale(d);
    paramVector3d1.add(localVector3d);
    paramVector3d2.sub(localVector3d);
  }

  public void reflectWorldToInstruments(float paramFloat)
  {
    float f1 = 0.0F;

    if (this.bNeedSetUp)
    {
      reflectPlaneMats();
      this.bNeedSetUp = false;
    }

    if (((AVIA_B534)aircraft()).bChangedPit)
    {
      reflectPlaneToModel();
      ((AVIA_B534)aircraft()).bChangedPit = false;
    }

    this.mesh.chunkSetAngles("NeedManPress", 0.0F, this.pictManifold = 0.85F * this.pictManifold + 0.15F * cvt(this.fm.EI.engines[0].getManifoldPressure() * 760.0F, 260.0F, 1200.0F, 33.299999F, 360.0F), 0.0F);

    f1 = -15.0F * (this.pictAiler = 0.85F * this.pictAiler + 0.15F * this.fm.CT.AileronControl);

    float f2 = 14.0F * (this.pictElev = 0.85F * this.pictElev + 0.2F * this.fm.CT.ElevatorControl);

    this.mesh.chunkSetAngles("StickB", 0.0F, -f1, f2);

    f1 = this.fm.CT.getRudder();

    this.mesh.chunkSetAngles("Pedal_L", 0.0F, 18.0F * f1, 0.0F);
    this.mesh.chunkSetAngles("Pedal_R", 0.0F, 18.0F * f1, 0.0F);

    this.mesh.chunkSetAngles("TQHandle", 0.0F, 0.0F, 75.0F * interp(this.setNew.throttle, this.setOld.throttle, paramFloat));

    this.mesh.chunkSetAngles("MixLvr", 0.0F, 0.0F, 65.0F * interp(this.setNew.mix, this.setOld.mix, paramFloat));

    this.mesh.chunkSetAngles("CowlFlapLvr", 0.0F, -70.0F * interp(this.setNew.radiator, this.setOld.radiator, paramFloat), 0.0F);

    this.mesh.chunkSetAngles("NeedAlt_Km", 0.0F, cvt(interp(this.setNew.altimeter, this.setOld.altimeter, paramFloat), 0.0F, 10000.0F, 0.0F, 450.0F), 0.0F);

    float f3 = Pitot.Indicator((float)this.fm.Loc.z, this.fm.getSpeedKMH());

    if (f3 < 100.0F) {
      this.mesh.chunkSetAngles("NeedSpeed", 0.0F, -cvt(f3, 0.0F, 100.0F, 0.0F, -28.4F), 0.0F);
    }
    else if (f3 < 200.0F) {
      this.mesh.chunkSetAngles("NeedSpeed", 0.0F, -cvt(f3, 100.0F, 200.0F, -28.4F, -102.0F), 0.0F);
    }
    else if (f3 < 300.0F) {
      this.mesh.chunkSetAngles("NeedSpeed", 0.0F, -cvt(f3, 200.0F, 300.0F, -102.0F, -191.5F), 0.0F);
    }
    else {
      this.mesh.chunkSetAngles("NeedSpeed", 0.0F, -cvt(f3, 300.0F, 450.0F, -191.5F, -326.0F), 0.0F);
    }

    this.mesh.chunkSetAngles("NeedFuel", 0.0F, cvt(this.fm.M.fuel, 0.0F, 250.0F, 0.0F, 255.5F), 0.0F);

    f1 = this.fm.EI.engines[0].tOilOut;

    if (f1 < 20.0F) {
      this.mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 0.0F, 20.0F, 0.0F, 15.0F), 0.0F);
    }
    else if (f1 < 40.0F) {
      this.mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 20.0F, 40.0F, 15.0F, 50.0F), 0.0F);
    }
    else if (f1 < 60.0F) {
      this.mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 40.0F, 60.0F, 50.0F, 102.5F), 0.0F);
    }
    else if (f1 < 80.0F) {
      this.mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 60.0F, 80.0F, 102.5F, 186.0F), 0.0F);
    }
    else if (f1 < 100.0F) {
      this.mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 80.0F, 100.0F, 186.0F, 283.0F), 0.0F);
    }
    else if (f1 < 120.0F) {
      this.mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 100.0F, 120.0F, 283.0F, 314.0F), 0.0F);
    }
    else {
      this.mesh.chunkSetAngles("NeedOilTemp", 0.0F, cvt(f1, 120.0F, 140.0F, 314.0F, 345.0F), 0.0F);
    }

    f1 = this.fm.EI.engines[0].getRPM();

    this.mesh.chunkSetAngles("NeedRPM", 0.0F, cvt(f1, 0.0F, 3000.0F, 0.0F, 310.0F), 0.0F);

    if ((this.fm.Or.getKren() < -110.0F) || (this.fm.Or.getKren() > 110.0F))
      this.rpmGeneratedPressure -= 0.5F;
    else if (f1 < this.rpmGeneratedPressure) {
      this.rpmGeneratedPressure -= (this.rpmGeneratedPressure - f1) * 0.01F;
    }
    else {
      this.rpmGeneratedPressure += (f1 - this.rpmGeneratedPressure) * 0.001F;
    }

    if (this.rpmGeneratedPressure < 600.0F)
      this.oilPressure = cvt(this.rpmGeneratedPressure, 0.0F, 600.0F, 0.0F, 4.0F);
    else if (this.rpmGeneratedPressure < 900.0F)
      this.oilPressure = cvt(this.rpmGeneratedPressure, 600.0F, 900.0F, 4.0F, 7.0F);
    else {
      this.oilPressure = cvt(this.rpmGeneratedPressure, 900.0F, 1200.0F, 7.0F, 10.0F);
    }
    float f4 = 0.0F;
    if (this.fm.EI.engines[0].tOilOut > 90.0F)
      f4 = cvt(this.fm.EI.engines[0].tOilOut, 90.0F, 120.0F, 1.1F, 1.5F);
    else if (this.fm.EI.engines[0].tOilOut < 50.0F)
      f4 = cvt(this.fm.EI.engines[0].tOilOut, 0.0F, 50.0F, 1.5F, 0.9F);
    else {
      f4 = cvt(this.fm.EI.engines[0].tOilOut, 50.0F, 90.0F, 0.9F, 1.1F);
    }
    float f5 = f4 * this.fm.EI.engines[0].getReadyness() * this.oilPressure;

    if (f5 < 12.0F) {
      this.mesh.chunkSetAngles("NeedOilPress", 0.0F, cvt(f5, 0.0F, 12.0F, 0.0F, 230.0F), 0.0F);
    }
    else if (f5 < 16.0F) {
      this.mesh.chunkSetAngles("NeedOilPress", 0.0F, cvt(f5, 12.0F, 16.0F, 230.0F, 285.0F), 0.0F);
    }
    else {
      this.mesh.chunkSetAngles("NeedOilPress", 0.0F, cvt(f5, 16.0F, 32.0F, 285.0F, 320.0F), 0.0F);
    }

    f1 = this.fm.EI.engines[0].tWaterOut;

    if (f1 < 20.0F) {
      this.mesh.chunkSetAngles("NeedWatTemp", 0.0F, cvt(f1, 0.0F, 20.0F, 0.0F, 15.0F), 0.0F);
    }
    else if (f1 < 40.0F) {
      this.mesh.chunkSetAngles("NeedWatTemp", 0.0F, cvt(f1, 20.0F, 40.0F, 15.0F, 50.0F), 0.0F);
    }
    else if (f1 < 60.0F) {
      this.mesh.chunkSetAngles("NeedWatTemp", 0.0F, cvt(f1, 40.0F, 60.0F, 50.0F, 109.0F), 0.0F);
    }
    else if (f1 < 80.0F) {
      this.mesh.chunkSetAngles("NeedWatTemp", 0.0F, cvt(f1, 60.0F, 80.0F, 109.0F, 192.0F), 0.0F);
    }
    else if (f1 < 100.0F) {
      this.mesh.chunkSetAngles("NeedWatTemp", 0.0F, cvt(f1, 80.0F, 100.0F, 192.0F, 294.0F), 0.0F);
    }
    else {
      this.mesh.chunkSetAngles("NeedWatTemp", 0.0F, cvt(f1, 100.0F, 111.5F, 294.0F, 356.10001F), 0.0F);
    }

    this.mesh.chunkSetAngles("NeedFuelPress", 0.0F, cvt(this.rpmGeneratedPressure, 0.0F, 1200.0F, 0.0F, 260.0F), 0.0F);

    this.mesh.chunkSetAngles("NeedBank", 0.0F, cvt(this.setNew.turn, -0.2F, 0.2F, 22.5F, -22.5F), 0.0F);

    this.mesh.chunkSetAngles("NeedTurn", 0.0F, -cvt(getBall(8.0D), -8.0F, 8.0F, 9.5F, -9.5F), 0.0F);

    this.mesh.chunkSetAngles("NeedClimb", 0.0F, -cvt(this.setNew.vspeed, -10.0F, 10.0F, 180.0F, -180.0F), 0.0F);

    this.mesh.chunkSetAngles("TrimIndicator", 120.0F * -this.fm.CT.getTrimElevatorControl(), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("ElevTrim", 0.0F, 600.0F * this.fm.CT.getTrimElevatorControl(), 0.0F);

    this.mesh.chunkSetAngles("IgnitionSwitch", 0.0F, 90.0F * (1 + this.fm.EI.engines[0].getControlMagnetos()), 0.0F);

    if (this.fm.CT.bHasBrakeControl)
    {
      f1 = this.fm.CT.getBrake();

      this.mesh.chunkSetAngles("BrakeLever", f1 * 20.0F, 0.0F, 0.0F);

      this.mesh.chunkSetAngles("NeedAirPR", 0.0F, -cvt(f1 + f1 * this.fm.CT.getRudder(), 0.0F, 1.5F, 0.0F, 148.0F), 0.0F);

      this.mesh.chunkSetAngles("NeedAirPL", 0.0F, cvt(f1 - f1 * this.fm.CT.getRudder(), 0.0F, 1.5F, 0.0F, 148.0F), 0.0F);

      this.mesh.chunkSetAngles("NeedAirP", 0.0F, 110.0F - f1 * 20.0F, 0.0F);
    }

    this.mesh.chunkSetAngles("Trigger1", 10.0F * (this.fm.CT.saveWeaponControl[0] != 0 ? 1 : 0), 0.0F, 0.0F);

    this.mesh.chunkSetAngles("Trigger2", 10.0F * (this.fm.CT.saveWeaponControl[1] != 0 ? 1 : 0), 0.0F, 0.0F);

    if ((this.fm.CT.Weapons[0] == null) || ((this.fm.CT.Weapons[0][0].countBullets() < 1) && (this.fm.CT.Weapons[1][0].countBullets() < 1)))
    {
      this.mesh.chunkVisible("MGLightL", false);
    }
    if ((this.fm.CT.Weapons[0] == null) || ((this.fm.CT.Weapons[0][1].countBullets() < 1) && (this.fm.CT.Weapons[1][1].countBullets() < 1)))
    {
      this.mesh.chunkVisible("MGLightR", false);
    }
    Aircraft.xyz[0] = 0.0F;
    Aircraft.xyz[2] = 0.0F;
    Aircraft.ypr[0] = 0.0F;
    Aircraft.ypr[1] = 0.0F;
    Aircraft.ypr[2] = 0.0F;
    Aircraft.xyz[1] = (this.fm.CT.getCockpitDoor() * 0.62F);
    this.mesh.chunkSetLocate("Canopy", Aircraft.xyz, Aircraft.ypr);

    if (this.fm.CT.saveWeaponControl[1] != 0)
      updateBullets();
  }

  public void reflectCockpitState()
  {
    if ((this.fm.AS.astateCockpitState & 0x2) != 0) {
      this.mesh.chunkVisible("DamageGlass1", true);
    }
    if ((this.fm.AS.astateCockpitState & 0x1) != 0)
    {
      this.mesh.chunkVisible("Gages1_d0", false);
      this.mesh.chunkVisible("Gages1_d1", true);
      this.mesh.chunkVisible("NeedOilTemp", false);
      this.mesh.chunkVisible("NeedSpeed", false);
      this.mesh.chunkVisible("NeedWatTemp", false);
      this.mesh.chunkVisible("NeedAlt_Km", false);
      this.mesh.chunkVisible("DamageHull1", true);
    }

    if ((this.fm.AS.astateCockpitState & 0x40) != 0)
    {
      this.mesh.chunkVisible("Gages2_d0", false);
      this.mesh.chunkVisible("Gages2_d1", true);
      this.mesh.chunkVisible("NeedClimb", false);
      this.mesh.chunkVisible("NeedBank", false);
      this.mesh.chunkVisible("NeedTurn", false);
      this.mesh.chunkVisible("DamageHull1", true);
    }

    if ((this.fm.AS.astateCockpitState & 0x4) != 0)
    {
      this.mesh.chunkVisible("DamageHull2", true);
      this.mesh.chunkVisible("DamageGlass3", true);
    }

    if ((this.fm.AS.astateCockpitState & 0x8) != 0)
    {
      this.mesh.chunkVisible("DamageHull3", true);
      this.mesh.chunkVisible("DamageGlass3", true);
    }

    if ((this.fm.AS.astateCockpitState & 0x10) != 0)
    {
      this.mesh.chunkVisible("DamageGlass2", true);
      this.mesh.chunkVisible("DamageHull4", true);
    }

    if ((this.fm.AS.astateCockpitState & 0x80) != 0)
      this.mesh.chunkVisible("OilSplats", true);
  }

  protected void reflectPlaneToModel()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    this.mesh.chunkVisible("WingLMid_D0", localHierMesh.isChunkVisible("WingLMid_D0"));
    this.mesh.chunkVisible("WingRMid_D0", localHierMesh.isChunkVisible("WingRMid_D0"));
  }

  public void doToggleUp(boolean paramBoolean)
  {
    super.doToggleUp(paramBoolean);
  }

  public void destroy()
  {
    super.destroy();
  }

  public void toggleDim()
  {
    this.cockpitDimControl = (!this.cockpitDimControl);
  }

  protected void reflectPlaneMats()
  {
    HierMesh localHierMesh = aircraft().hierMesh();
    Mat localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D0o"));
    this.mesh.materialReplace("Gloss1D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D1o"));
    this.mesh.materialReplace("Gloss1D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss1D2o"));
    this.mesh.materialReplace("Gloss1D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D0o"));
    this.mesh.materialReplace("Gloss2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D1o"));
    this.mesh.materialReplace("Gloss2D1o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Gloss2D2o"));
    this.mesh.materialReplace("Gloss2D2o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D0o"));
    this.mesh.materialReplace("Matt2D0o", localMat);
    localMat = localHierMesh.material(localHierMesh.materialFind("Matt2D2o"));
    this.mesh.materialReplace("Matt2D2o", localMat);
  }

  public void toggleLight()
  {
    this.cockpitLightControl = (!this.cockpitLightControl);
    if (this.cockpitLightControl)
      setNightMats(true);
    else
      setNightMats(false);
  }

  protected boolean doFocusEnter()
  {
    this.previousCycle = -1;
    this.cycleCount = 5;
    updateBullets();
    return super.doFocusEnter();
  }

  private void updateBullets()
  {
    int i = 0;
    Aircraft.xyz[0] = 0.0F;
    Aircraft.xyz[1] = 0.0F;
    Aircraft.xyz[2] = 0.0F;
    Aircraft.ypr[0] = 0.0F;
    Aircraft.ypr[1] = 0.0F;
    Aircraft.ypr[2] = 0.0F;

    BulletEmitter[] arrayOfBulletEmitter = this.fm.CT.Weapons[1];
    if ((arrayOfBulletEmitter != null) && ((this.gunROK) || (this.gunLOK)))
    {
      int j = 300;

      if (this.gunROK)
        j = 300 - arrayOfBulletEmitter[1].countBullets();
      else if (this.gunLOK) {
        j = 300 - arrayOfBulletEmitter[0].countBullets();
      }
      int k = j % 5 + 1;
      if (k != this.previousCycle)
      {
        this.hIncrementSize = (0.012F / this.cycleCount);
        this.vIncrementSize = (0.004F / (this.cycleCount * 2));
        this.hIncrement = (this.hIncrementSize * (this.cycleCount - 1));
        this.vIncrement = (this.vIncrementSize * (this.cycleCount - 1));
        this.cycleCount = 1;
        this.vIncrement = 0.0F;
      }
      else
      {
        this.cycleCount += 1;
        this.hIncrement -= this.hIncrementSize;
        if (this.vIncrement >= 0.0025F)
          this.vIncrement -= this.vIncrementSize;
        else {
          this.vIncrement += this.vIncrementSize;
        }
      }
      if (this.hIncrement < 0.0F) {
        return;
      }
      int m = 0;
      if (j >= 80)
      {
        if (j < 95) {
          this.turningCycle = true;
        } else if (j < 120)
        {
          hideBullets(1);
          m = 2;
          this.turningCycle = false;
          this.cycleMultiplier = -1;
        }
        else if (j < 135)
        {
          m = 2;
          this.turningCycle = true;
        }
        else if (j < 160)
        {
          hideBullets(1);
          hideBullets(2);
          hideBullets(3);
          m = 4;
          this.turningCycle = false;
          this.cycleMultiplier = -1;
        }
        else if (j < 175)
        {
          m = 4;
          this.turningCycle = true;
        }
        else if (j < 200)
        {
          hideBullets(1);
          hideBullets(2);
          hideBullets(3);
          hideBullets(4);
          hideBullets(5);
          m = 6;
          this.turningCycle = false;
          this.cycleMultiplier = -1;
          this.mesh.chunkVisible("RBullet21", true);
          this.mesh.chunkVisible("LBullet21", true);
        }
        else if (j < 215)
        {
          m = 6;
          this.turningCycle = true;
          if (this.gunROK) {
            this.mesh.chunkVisible("RBullet21", false);
          }
          if (this.gunLOK)
            this.mesh.chunkVisible("LBullet21", false);
        }
        else if (j < 240)
        {
          hideBullets(1);
          hideBullets(2);
          hideBullets(3);
          hideBullets(4);
          hideBullets(5);
          hideBullets(6);
          hideBullets(7);
          m = 8;
          this.turningCycle = false;
          this.cycleMultiplier = -1;
          this.mesh.chunkVisible("RBullet21", true);
          this.mesh.chunkVisible("LBullet21", true);
        }
        else if (j < 255)
        {
          m = 8;
          this.turningCycle = true;
          if (this.gunROK) {
            this.mesh.chunkVisible("RBullet21", false);
          }
          if (this.gunLOK)
            this.mesh.chunkVisible("LBullet21", false);
        }
        else
        {
          hideBullets(1);
          hideBullets(2);
          hideBullets(3);
          hideBullets(4);
          hideBullets(5);
          hideBullets(6);
          hideBullets(7);
          hideBullets(8);
          hideBullets(9);
          m = 10;
          this.turningCycle = false;

          this.cycleMultiplier = -1;

          if (j >= 280)
          {
            i = 1;
            if (k == 5)
            {
              if (this.gunROK) {
                this.mesh.chunkVisible("RBullet21", false);
              }
              if (this.gunLOK) {
                this.mesh.chunkVisible("LBullet21", false);
              }
              hideBullets(10);
            }

            if (j >= 291)
            {
              hideBullets(10);
              if (this.gunROK)
              {
                for (n = 1; n <= j - 290; n++) {
                  this.mesh.chunkVisible("RBullet0" + n, false);
                }
              }
              if (this.gunLOK)
              {
                for (n = 1; n <= j - 290; n++) {
                  this.mesh.chunkVisible("LBullet0" + n, false);
                }
              }
            }
            if (j >= 300)
              hideAllBullets();
          }
          else
          {
            this.mesh.chunkVisible("RBullet21", true);
            this.mesh.chunkVisible("LBullet21", true);
          }
        }
      }

      for (int n = 5; n >= 1; n--)
      {
        Aircraft.xyz[2] = this.hIncrement;
        Aircraft.xyz[0] = 0.0F;
        if (this.gunROK) {
          this.mesh.chunkSetLocate("RBullet0" + n, Aircraft.xyz, Aircraft.ypr);
        }
        if (this.gunLOK) {
          this.mesh.chunkSetLocate("LBullet0" + n, Aircraft.xyz, Aircraft.ypr);
        }
      }
      for (n = 9; n >= 6; n--)
      {
        Aircraft.xyz[0] = this.hIncrement;
        Aircraft.xyz[2] = 0.0F;
        if (this.gunROK) {
          this.mesh.chunkSetLocate("RBullet0" + n, Aircraft.xyz, Aircraft.ypr);
        }
        if (this.gunLOK)
          this.mesh.chunkSetLocate("LBullet0" + n, Aircraft.xyz, Aircraft.ypr);
      }
      int i1;
      if (this.turningCycle)
      {
        if ((k != this.previousCycle) && (k == 1)) {
          this.cycleMultiplier += 1;
        }
        n = k + this.cycleMultiplier * 5;

        if (n > 10)
        {
          hideBullets(m + 1);
          this.previousCycle = k;
          return;
        }

        i1 = 0;
        if (n % 2 != 0) {
          i1 = n / 2 + 1;
        }
        int i2 = n / 2;

        for (int i3 = 1; i3 <= 6; i3++)
        {
          if (i3 <= i2)
          {
            int i4 = n - (i3 + (i3 - 1));
            Aircraft.xyz[0] = (i4 * -0.012F + this.hIncrement - 0.006F);
            Aircraft.xyz[2] = (0.01F + this.vIncrement);
            if (this.gunROK) {
              this.mesh.chunkSetLocate("RBullet" + (m + 1) + i3, Aircraft.xyz, Aircraft.ypr);
            }
            if (this.gunLOK) {
              this.mesh.chunkSetLocate("LBullet" + (m + 1) + i3, Aircraft.xyz, Aircraft.ypr);
            }
          }
          if (i3 == i1)
          {
            Aircraft.xyz[0] = (-0.006F + this.hIncrement / 2.0F);
            Aircraft.xyz[2] = (0.01F - this.hIncrement);
            if (this.gunROK) {
              this.mesh.chunkSetLocate("RBullet" + (m + 1) + i1, Aircraft.xyz, Aircraft.ypr);
            }

            if (this.gunLOK) {
              this.mesh.chunkSetLocate("LBullet" + (m + 1) + i1, Aircraft.xyz, Aircraft.ypr);
            }
          }

          if ((n >= 6) || (i3 > 6) || (m == 0))
            continue;
          Aircraft.xyz[0] = ((n + 1) * -0.012F + this.hIncrement);
          Aircraft.xyz[2] = (0.0F + this.vIncrement);
          if (this.gunROK) {
            this.mesh.chunkSetLocate("RBullet" + m + i3, Aircraft.xyz, Aircraft.ypr);
          }
          if (this.gunLOK) {
            this.mesh.chunkSetLocate("LBullet" + m + i3, Aircraft.xyz, Aircraft.ypr);
          }
        }

        if ((n >= 5) && (m != 0))
          hideBullets(m);
      }
      else if (m != 0)
      {
        for (n = 6; n >= 1; n--)
        {
          if ((k + n >= 7) && (i == 0))
          {
            i1 = 6 - n - k;
            if (i1 == 0)
            {
              Aircraft.xyz[0] = ((n + i1) * 0.012F);
              Aircraft.xyz[2] = 0.0F;
              if (this.gunROK) {
                this.mesh.chunkSetLocate("RBullet" + m + n, Aircraft.xyz, Aircraft.ypr);
              }
              if (this.gunLOK)
                this.mesh.chunkSetLocate("LBullet" + m + n, Aircraft.xyz, Aircraft.ypr);
            }
            else
            {
              Aircraft.xyz[0] = ((n + i1) * 0.012F + this.hIncrement);
              Aircraft.xyz[2] = (0.0F + this.vIncrement);
              if (this.gunROK) {
                this.mesh.chunkSetLocate("RBullet" + m + n, Aircraft.xyz, Aircraft.ypr);
              }
              if (this.gunLOK)
                this.mesh.chunkSetLocate("LBullet" + m + n, Aircraft.xyz, Aircraft.ypr);
            }
          }
          else
          {
            Aircraft.xyz[0] = (k * -0.012F + this.hIncrement);
            Aircraft.xyz[2] = (0.0F + this.vIncrement);
            if (this.gunROK) {
              this.mesh.chunkSetLocate("RBullet" + m + n, Aircraft.xyz, Aircraft.ypr);
            }
            if (this.gunLOK) {
              this.mesh.chunkSetLocate("LBullet" + m + n, Aircraft.xyz, Aircraft.ypr);
            }
          }
        }
        if (m >= 6)
        {
          Aircraft.xyz[0] = (-0.072F + this.hIncrement);
          Aircraft.xyz[2] = (-0.010742F * (m - 2) + this.vIncrement);

          if (this.gunROK) {
            this.mesh.chunkSetLocate("RBullet21", Aircraft.xyz, Aircraft.ypr);
          }
          if (this.gunLOK) {
            this.mesh.chunkSetLocate("LBullet21", Aircraft.xyz, Aircraft.ypr);
          }
        }
      }
      this.previousCycle = k;
    }
  }

  private void hideBullets(int paramInt)
  {
    if (this.gunLOK)
    {
      this.mesh.chunkVisible("LBullet" + paramInt + 1, false);
      this.mesh.chunkVisible("LBullet" + paramInt + 2, false);
      this.mesh.chunkVisible("LBullet" + paramInt + 3, false);
      this.mesh.chunkVisible("LBullet" + paramInt + 4, false);
      this.mesh.chunkVisible("LBullet" + paramInt + 5, false);
      this.mesh.chunkVisible("LBullet" + paramInt + 6, false);
    }

    if (this.gunROK)
    {
      this.mesh.chunkVisible("RBullet" + paramInt + 1, false);
      this.mesh.chunkVisible("RBullet" + paramInt + 2, false);
      this.mesh.chunkVisible("RBullet" + paramInt + 3, false);
      this.mesh.chunkVisible("RBullet" + paramInt + 4, false);
      this.mesh.chunkVisible("RBullet" + paramInt + 5, false);
      this.mesh.chunkVisible("RBullet" + paramInt + 6, false);
    }
  }

  public void jamLeftGun()
  {
    this.gunLOK = false;
  }

  public void jamRightGun()
  {
    this.gunROK = false;
  }

  public void hideAllBullets()
  {
    for (int i = 1; i <= 10; i++)
    {
      if (this.gunLOK)
      {
        this.mesh.chunkVisible("LBullet" + i + 1, false);
        this.mesh.chunkVisible("LBullet" + i + 2, false);
        this.mesh.chunkVisible("LBullet" + i + 3, false);
        this.mesh.chunkVisible("LBullet" + i + 4, false);
        this.mesh.chunkVisible("LBullet" + i + 5, false);
        this.mesh.chunkVisible("LBullet" + i + 6, false);
      }
      if (!this.gunROK)
        continue;
      this.mesh.chunkVisible("RBullet" + i + 1, false);
      this.mesh.chunkVisible("RBullet" + i + 2, false);
      this.mesh.chunkVisible("RBullet" + i + 3, false);
      this.mesh.chunkVisible("RBullet" + i + 4, false);
      this.mesh.chunkVisible("RBullet" + i + 5, false);
      this.mesh.chunkVisible("RBullet" + i + 6, false);
    }

    for (i = 1; i <= 9; i++)
    {
      if (this.gunLOK)
        this.mesh.chunkVisible("LBullet0" + i, false);
      if (this.gunROK)
        this.mesh.chunkVisible("RBullet0" + i, false);
    }
  }

  private class Variables
  {
    float altimeter;
    float throttle;
    float mix;
    float radiator;
    float prop;
    float turn;
    float vspeed;
    float stbyPosition;
    float dimPos;
    Point3d planeLoc;
    Point3d planeMove;
    Vector3d[] compassPoint;
    Vector3d[] cP;
    private final CockpitAVIA_B534 this$0;

    private Variables()
    {
      this.this$0 = this$1;

      this.planeLoc = new Point3d();
      this.planeMove = new Point3d();
      this.compassPoint = new Vector3d[4];
      this.cP = new Vector3d[4];

      this.compassPoint[0] = new Vector3d(0.0D, Math.sqrt(1.0D - CockpitAVIA_B534.compassZ * CockpitAVIA_B534.compassZ), CockpitAVIA_B534.compassZ);

      this.compassPoint[1] = new Vector3d(-Math.sqrt(1.0D - CockpitAVIA_B534.compassZ * CockpitAVIA_B534.compassZ), 0.0D, CockpitAVIA_B534.compassZ);
      this.compassPoint[2] = new Vector3d(0.0D, -Math.sqrt(1.0D - CockpitAVIA_B534.compassZ * CockpitAVIA_B534.compassZ), CockpitAVIA_B534.compassZ);
      this.compassPoint[3] = new Vector3d(Math.sqrt(1.0D - CockpitAVIA_B534.compassZ * CockpitAVIA_B534.compassZ), 0.0D, CockpitAVIA_B534.compassZ);

      this.cP[0] = new Vector3d(0.0D, Math.sqrt(1.0D - CockpitAVIA_B534.compassZ * CockpitAVIA_B534.compassZ), CockpitAVIA_B534.compassZ);

      this.cP[1] = new Vector3d(-Math.sqrt(1.0D - CockpitAVIA_B534.compassZ * CockpitAVIA_B534.compassZ), 0.0D, CockpitAVIA_B534.compassZ);
      this.cP[2] = new Vector3d(0.0D, -Math.sqrt(1.0D - CockpitAVIA_B534.compassZ * CockpitAVIA_B534.compassZ), CockpitAVIA_B534.compassZ);
      this.cP[3] = new Vector3d(Math.sqrt(1.0D - CockpitAVIA_B534.compassZ * CockpitAVIA_B534.compassZ), 0.0D, CockpitAVIA_B534.compassZ);
    }

    Variables(CockpitAVIA_B534.1 arg2)
    {
      this();
    }
  }

  class Interpolater extends InterpolateRef
  {
    Interpolater()
    {
    }

    public boolean tick()
    {
      if (CockpitAVIA_B534.this.bNeedSetUp)
      {
        CockpitAVIA_B534.this.reflectPlaneMats();
        CockpitAVIA_B534.access$102(CockpitAVIA_B534.this, false);
      }

      if ((CockpitAVIA_B534.this.ac != null) && (CockpitAVIA_B534.this.ac.bChangedPit))
      {
        CockpitAVIA_B534.this.reflectPlaneToModel();
        CockpitAVIA_B534.this.ac.bChangedPit = false;
      }

      CockpitAVIA_B534.access$302(CockpitAVIA_B534.this, CockpitAVIA_B534.this.setOld);
      CockpitAVIA_B534.access$402(CockpitAVIA_B534.this, CockpitAVIA_B534.this.setNew);
      CockpitAVIA_B534.access$502(CockpitAVIA_B534.this, CockpitAVIA_B534.this.setTmp);
      if (((CockpitAVIA_B534.this.fm.AS.astateCockpitState & 0x2) != 0) && (CockpitAVIA_B534.this.setNew.stbyPosition < 1.0F))
      {
        CockpitAVIA_B534.this.setNew.stbyPosition = (CockpitAVIA_B534.this.setOld.stbyPosition + 0.0125F);
        CockpitAVIA_B534.this.setOld.stbyPosition = CockpitAVIA_B534.this.setNew.stbyPosition;
      }

      CockpitAVIA_B534.this.setNew.altimeter = CockpitAVIA_B534.this.fm.getAltitude();

      CockpitAVIA_B534.this.setNew.throttle = ((10.0F * CockpitAVIA_B534.this.setOld.throttle + CockpitAVIA_B534.this.fm.EI.engines[0].getControlThrottle()) / 11.0F);

      CockpitAVIA_B534.this.setNew.mix = ((10.0F * CockpitAVIA_B534.this.setOld.mix + CockpitAVIA_B534.this.fm.EI.engines[0].getControlMix()) / 11.0F);
      CockpitAVIA_B534.this.setNew.radiator = ((10.0F * CockpitAVIA_B534.this.setOld.radiator + CockpitAVIA_B534.this.fm.EI.engines[0].getControlRadiator()) / 11.0F);

      CockpitAVIA_B534.this.setNew.prop = CockpitAVIA_B534.this.setOld.prop;
      if (CockpitAVIA_B534.this.setNew.prop < CockpitAVIA_B534.this.fm.EI.engines[0].getControlProp() - 0.01F) {
        CockpitAVIA_B534.this.setNew.prop += 0.0025F;
      }
      if (CockpitAVIA_B534.this.setNew.prop > CockpitAVIA_B534.this.fm.EI.engines[0].getControlProp() + 0.01F) {
        CockpitAVIA_B534.this.setNew.prop -= 0.0025F;
      }
      CockpitAVIA_B534.this.w.set(CockpitAVIA_B534.this.fm.getW());
      CockpitAVIA_B534.this.fm.Or.transform(CockpitAVIA_B534.this.w);
      CockpitAVIA_B534.this.setNew.turn = ((12.0F * CockpitAVIA_B534.this.setOld.turn + CockpitAVIA_B534.this.w.z) / 13.0F);
      CockpitAVIA_B534.this.setNew.vspeed = ((299.0F * CockpitAVIA_B534.this.setOld.vspeed + CockpitAVIA_B534.this.fm.getVertSpeed()) / 300.0F);
      CockpitAVIA_B534.access$702(CockpitAVIA_B534.this, 0.8F * CockpitAVIA_B534.this.pictSupc + 0.2F * CockpitAVIA_B534.this.fm.EI.engines[0].getControlCompressor());

      if (CockpitAVIA_B534.this.cockpitDimControl)
      {
        if (CockpitAVIA_B534.this.setNew.dimPos < 1.0F)
          CockpitAVIA_B534.this.setNew.dimPos = (CockpitAVIA_B534.this.setOld.dimPos + 0.03F);
      }
      else if (CockpitAVIA_B534.this.setNew.dimPos > 0.0F) {
        CockpitAVIA_B534.this.setNew.dimPos = (CockpitAVIA_B534.this.setOld.dimPos - 0.03F);
      }
      if ((!CockpitAVIA_B534.this.fm.Gears.bTailwheelLocked) && (CockpitAVIA_B534.this.tailWheelLock < 1.0F))
        CockpitAVIA_B534.access$802(CockpitAVIA_B534.this, CockpitAVIA_B534.this.tailWheelLock + 0.05F);
      else if ((CockpitAVIA_B534.this.fm.Gears.bTailwheelLocked) && (CockpitAVIA_B534.this.tailWheelLock > 0.0F)) {
        CockpitAVIA_B534.access$802(CockpitAVIA_B534.this, CockpitAVIA_B534.this.tailWheelLock - 0.05F);
      }
      CockpitAVIA_B534.this.updateCompass();
      return true;
    }
  }
}