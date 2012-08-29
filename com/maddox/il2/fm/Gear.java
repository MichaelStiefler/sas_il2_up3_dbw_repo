package com.maddox.il2.fm;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Line2f;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollision;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.Scheme1;
import com.maddox.il2.objects.buildings.Plate;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.BigshipGeneric.AirportProperties;
import com.maddox.il2.objects.ships.BigshipGeneric.ShipProperties;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import java.util.AbstractCollection;

public class Gear
{
  public int[] pnti;
  boolean onGround;
  boolean nearGround;
  public float H;
  public float Pitch;
  public float sinkFactor;
  public float springsStiffness;
  public float tailStiffness;
  public boolean bIsSail;
  public int nOfGearsOnGr;
  public int nOfPoiOnGr;
  private int oldNOfGearsOnGr;
  private int oldNOfPoiOnGr;
  private int nP;
  public boolean gearsChanged;
  private static final double landSlot = 0.1D;
  HierMesh HM;
  public boolean bFlatTopGearCheck;
  public static final int MP = 64;
  public static final double maxVf_gr = 65.0D;
  public static final double maxVn_gr = 7.0D;
  public static final double maxVf_wt = 50.0D;
  public static final double maxVn_wt = 30.0D;
  public static final double maxVf_wl = 110.0D;
  public static final double maxVn_wl = 7.0D;
  public static final double _1_maxVf_gr2 = 0.0002366863905325444D;
  public static final double _1_maxVn_gr2 = 0.02040816326530612D;
  public static final double _1_maxVf_wt2 = 0.0004D;
  public static final double _1_maxVn_wt2 = 0.001111111111111111D;
  public static final double _1_maxVf_wl2 = 8.264462809917356E-005D;
  public static final double _1_maxVn_wl2 = 0.02040816326530612D;
  private static Point3f[] Pnt = new Point3f[64];
  private static boolean[] clp = new boolean[64];
  private Eff3DActor[] clpEff;
  public Eff3DActor[][] clpGearEff;
  public Eff3DActor[][] clpEngineEff;
  private String effectName;
  private boolean bTheBabysGonnaBlow;
  public boolean lgear;
  public boolean rgear;
  public boolean cgear;
  public boolean bIsHydroOperable;
  private boolean bIsOperable;
  public boolean bTailwheelLocked;
  public double[] gVelocity;
  public float[] gWheelAngles;
  public float[] gWheelSinking;
  public float steerAngle;
  public double roughness;
  public float arrestorVAngle;
  public float arrestorVSink;
  public HookNamed arrestorHook;
  public int[] waterList;
  private boolean isGearColl;
  private double MassCoeff;
  public boolean bFrontWheel;
  private static AnglesFork steerAngleFork = new AnglesFork();
  private double d;
  private double poiDrag;
  private double D0;
  private double D;
  private double Vnorm;
  private boolean isWater;
  private boolean bUnderDeck;
  private boolean bIsGear;
  private FlightModel FM;
  private boolean bIsMaster;
  private int[] fatigue;
  private Point3d p0;
  private Point3d p1;
  private Loc l0;
  private Vector3d v0;
  private Vector3d tmpV;
  private Vector3d tmpV1;
  private double fric;
  private double fricF;
  private double fricR;
  private double maxFric;
  public double screenHQ;
  static ClipFilter clipFilter = new ClipFilter();
  private boolean canDoEffect;
  private static Vector3d Normal;
  private static Vector3d Forward;
  private static Vector3d Right;
  private static Vector3d nwForward;
  private static Vector3d nwRight;
  private static double NormalVPrj;
  private static double ForwardVPrj;
  private static double RightVPrj;
  private static Vector3d Vnf;
  private static Vector3d Fd;
  private static Vector3d Fx;
  private static Vector3d Vship;
  private static Vector3d Fv;
  private static Vector3d Tn;
  private static Point3d Pn;
  private static Point3d PnT;
  private static Point3d Pship;
  private static Vector3d Vs;
  private static Vector3d normal;
  private static Matrix4d M4;
  private static PlateFilter plateFilter;
  private static Point3d corn;
  private static Point3d corn1;
  private static Loc L;
  private static float[] plateBox;
  private static boolean bPlateExist;
  private static boolean bPlateGround;

  public Gear()
  {
    this.onGround = false;
    this.nearGround = false;

    this.nOfGearsOnGr = 0;
    this.nOfPoiOnGr = 0;
    this.oldNOfGearsOnGr = 0;
    this.oldNOfPoiOnGr = 0;
    this.nP = 0;
    this.gearsChanged = false;

    this.clpEff = new Eff3DActor[64];
    this.clpGearEff = new Eff3DActor[][] { { null, null }, { null, null }, { null, null } };
    this.clpEngineEff = new Eff3DActor[8][2];
    this.effectName = new String();

    this.bTheBabysGonnaBlow = false;
    this.lgear = true; this.rgear = true; this.cgear = true;

    this.bIsHydroOperable = true;
    this.bIsOperable = true;
    this.bTailwheelLocked = false;

    this.gVelocity = new double[] { 0.0D, 0.0D, 0.0D };
    this.gWheelAngles = new float[] { 0.0F, 0.0F, 0.0F };
    this.gWheelSinking = new float[] { 0.0F, 0.0F, 0.0F };
    this.steerAngle = 0.0F;
    this.roughness = 0.5D;
    this.arrestorVAngle = 0.0F;
    this.arrestorVSink = 0.0F;
    this.arrestorHook = null;
    this.waterList = null;
    this.isGearColl = false;
    this.MassCoeff = 1.0D;
    this.bFrontWheel = false;

    this.bUnderDeck = false;

    this.bIsMaster = true;
    this.fatigue = new int[2];

    this.p0 = new Point3d();
    this.p1 = new Point3d();
    this.l0 = new Loc();
    this.v0 = new Vector3d();
    this.tmpV = new Vector3d();
    this.tmpV1 = new Vector3d();
    this.fric = 0.0D; this.fricF = 0.0D; this.fricR = 0.0D; this.maxFric = 0.0D;
    this.screenHQ = 0.0D;

    this.canDoEffect = true;
  }

  public boolean onGround()
  {
    return this.onGround; } 
  public boolean nearGround() { return this.nearGround; } 
  public boolean isHydroOperable() {
    return this.bIsHydroOperable; } 
  public void setHydroOperable(boolean paramBoolean) { this.bIsHydroOperable = paramBoolean; } 
  public boolean isOperable() { return this.bIsOperable; } 
  public void setOperable(boolean paramBoolean) { this.bIsOperable = paramBoolean; } 
  public float getSteeringAngle() { return this.steerAngle; } 
  public boolean isUnderDeck() { return this.bUnderDeck; } 
  public boolean getWheelsOnGround() {
    boolean bool = this.isGearColl;
    this.isGearColl = false;
    return bool;
  }

  public void set(HierMesh paramHierMesh) {
    this.HM = paramHierMesh;
    if (this.pnti == null)
    {
      for (i = 0; i < 61; i++)
        if (this.HM.hookFind("_Clip" + s(i)) < 0)
          break;
      this.pnti = new int[i + 3];
      this.pnti[0] = this.HM.hookFind("_ClipLGear");
      this.pnti[1] = this.HM.hookFind("_ClipRGear");
      this.pnti[2] = this.HM.hookFind("_ClipCGear");
      for (i = 3; i < this.pnti.length; i++) {
        this.pnti[i] = this.HM.hookFind("_Clip" + s(i - 3));
      }
    }
    if ((this.arrestorHook == null) && 
      (paramHierMesh.hookFind("_ClipAGear") != -1)) {
      this.arrestorHook = new HookNamed(paramHierMesh, "_ClipAGear");
    }

    int i = this.pnti[2];
    if (i > 0) {
      this.HM.hookMatrix(i, M4);
      if (M4.m03 > -1.0D)
        this.bFrontWheel = true;
    }
  }

  public void computePlaneLandPose(FlightModel paramFlightModel)
  {
    this.FM = paramFlightModel;
    if ((this.H != 0.0F) && (this.Pitch != 0.0F))
      return;
    for (int i = 0; i < 3; i++)
      if (this.pnti[i] < 0)
        return;
    this.HM.hookMatrix(this.pnti[2], M4);
    double d1 = M4.m03;
    double d2 = M4.m23;
    this.HM.hookMatrix(this.pnti[0], M4);
    double d3 = M4.m03;
    double d4 = M4.m23;
    this.HM.hookMatrix(this.pnti[1], M4);
    d3 = (d3 + M4.m03) * 0.5D;
    d4 = (d4 + M4.m23) * 0.5D;

    double d5 = d3 - d1;
    double d6 = d4 - d2;
    this.Pitch = (-Geom.RAD2DEG((float)Math.atan2(d6, d5)));
    if (d5 < 0.0D) this.Pitch += 180.0F;
    Line2f localLine2f = new Line2f();
    localLine2f.set(new Point2f((float)d3, (float)d4), new Point2f((float)d1, (float)d2));
    this.H = localLine2f.distance(new Point2f(0.0F, 0.0F));
    this.H = (float)(this.H - (this.FM.jdField_M_of_type_ComMaddoxIl2FmMass.massEmpty + this.FM.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel + this.FM.jdField_M_of_type_ComMaddoxIl2FmMass.maxNitro) * Atmosphere.g() / 2700000.0D);
  }

  public void set(Gear paramGear)
  {
    if (paramGear.pnti == null) return;
    this.pnti = new int[paramGear.pnti.length];
    if (paramGear.waterList != null) {
      this.waterList = new int[paramGear.waterList.length];
      for (i = 0; i < this.waterList.length; i++) {
        this.waterList[i] = paramGear.waterList[i];
      }
    }
    for (int i = 0; i < this.pnti.length; i++) {
      this.pnti[i] = paramGear.pnti[i];
    }
    this.bIsSail = paramGear.bIsSail;
    this.sinkFactor = paramGear.sinkFactor;
    this.springsStiffness = paramGear.springsStiffness;
    this.H = paramGear.H;
    this.Pitch = paramGear.Pitch;
    this.bFrontWheel = paramGear.bFrontWheel;
  }

  public void ground(FlightModel paramFlightModel, boolean paramBoolean) {
    ground(paramFlightModel, paramBoolean, false);
  }

  public void ground(FlightModel paramFlightModel, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.FM = paramFlightModel; this.bIsMaster = paramBoolean1; this.onGround = paramBoolean2;
    this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double = (-this.FM.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double);
    this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double = (-this.FM.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double);
    this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double = (-this.FM.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double);
    for (int i = 0; i < 2; i++) {
      if (this.fatigue[i] <= 0) continue; this.fatigue[i] -= 1;
    }
    Pn.set(this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    Pn.jdField_z_of_type_Double = Engine.cur.land.HQ(Pn.jdField_x_of_type_Double, Pn.jdField_y_of_type_Double);
    double d1 = Pn.jdField_z_of_type_Double;
    this.screenHQ = d1;
    if ((this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - d1 > 50.0D) && (!this.bFlatTopGearCheck)) {
      turnOffEffects();
      this.arrestorVSink = -50.0F;
      return;
    }
    this.isWater = Engine.cur.land.isWater(Pn.jdField_x_of_type_Double, Pn.jdField_y_of_type_Double);
    if (this.isWater) this.roughness = 0.5D;
    this.D0 = Engine.cur.land.EQN(Pn.jdField_x_of_type_Double, Pn.jdField_y_of_type_Double, Normal);

    this.bUnderDeck = false;
    BigshipGeneric localBigshipGeneric = null;
    Object localObject1;
    Object localObject2;
    if (this.bFlatTopGearCheck) {
      corn.set(this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      corn1.set(this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      corn1.jdField_z_of_type_Double -= 20.0D;
      localObject1 = Engine.collideEnv().getLine(corn, corn1, false, clipFilter, Pship);
      if ((localObject1 instanceof BigshipGeneric)) {
        Pn.jdField_z_of_type_Double = (Pship.jdField_z_of_type_Double + 0.5D);
        d1 = Pn.jdField_z_of_type_Double;
        this.isWater = false;
        this.bUnderDeck = true;
        ((Actor)localObject1).getSpeed(Vship);
        this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.add(Vship);
        localBigshipGeneric = (BigshipGeneric)localObject1;
        localBigshipGeneric.addRockingSpeed(this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d, Normal, this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        if ((paramFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isMaster()) && (localBigshipGeneric.getAirport() != null) && (paramFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasArrestorControl)) {
          if ((!localBigshipGeneric.isTowAircraft((Aircraft)paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor)) && (this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.lengthSquared() > 10.0D) && (paramFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getArrestor() > 0.1F)) {
            localBigshipGeneric.requestTowAircraft((Aircraft)paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            if (localBigshipGeneric.isTowAircraft((Aircraft)paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor)) {
              paramFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setFlatTopString(localBigshipGeneric, localBigshipGeneric.towPortNum);
              if (((this.FM instanceof RealFlightModel)) && (this.bIsMaster) && (((RealFlightModel)this.FM).isRealMode())) {
                ((RealFlightModel)this.FM).producedShakeLevel = 5.0F;
              }
              ((Aircraft)paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor).sfxTow();
            }
          }
          if ((localBigshipGeneric.isTowAircraft((Aircraft)paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor)) && (this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.lengthSquared() < 1.0D) && (World.Rnd().nextFloat() < 0.008F)) {
            localBigshipGeneric.requestDetowAircraft((Aircraft)paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            paramFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setFlatTopString(localBigshipGeneric, -1);
          }
        }
        if (localBigshipGeneric.isTowAircraft((Aircraft)paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor)) {
          k = localBigshipGeneric.towPortNum;
          localObject2 = localBigshipGeneric.getShipProp().propAirport.towPRel;
          localBigshipGeneric.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.l0);
          this.l0.transform(localObject2[(k * 2)], this.p0);
          this.l0.transform(localObject2[(k * 2 + 1)], this.p1);
          this.p0.jdField_x_of_type_Double = (0.5D * (this.p0.jdField_x_of_type_Double + this.p1.jdField_x_of_type_Double));
          this.p0.jdField_y_of_type_Double = (0.5D * (this.p0.jdField_y_of_type_Double + this.p1.jdField_y_of_type_Double));
          this.p0.jdField_z_of_type_Double = (0.5D * (this.p0.jdField_z_of_type_Double + this.p1.jdField_z_of_type_Double));
          paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.l0);
          this.l0.transformInv(this.p0);
          this.l0.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
          localBigshipGeneric.towHook.computePos(paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor, new Loc(this.l0), this.l0);
          this.v0.sub(this.p0, this.l0.getPoint());
          if (this.v0.jdField_x_of_type_Double > 0.0D) {
            if (localBigshipGeneric.isTowAircraft((Aircraft)paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor)) {
              localBigshipGeneric.requestDetowAircraft((Aircraft)paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor);
              paramFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setFlatTopString(localBigshipGeneric, -1);
            }
          } else {
            this.tmpV.set(this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d);
            paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient().transformInv(this.tmpV);
            if (this.tmpV.jdField_x_of_type_Double < 0.0D) {
              double d3 = this.v0.length();
              this.v0.normalize();
              this.arrestorVAngle = (float)Math.toDegrees(Math.asin(this.v0.jdField_z_of_type_Double));

              this.v0.scale(1000.0D * d3);
              paramFlightModel.jdField_GF_of_type_ComMaddoxJGPVector3d.add(this.v0);
              this.v0.scale(0.3D);
              this.v0.cross(this.l0.getPoint(), this.v0);
              paramFlightModel.jdField_GM_of_type_ComMaddoxJGPVector3d.add(this.v0);
            }
          }
        }
        else {
          this.arrestorVAngle = 0.0F;
        }
      }
    }

    if (paramFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasArrestorControl) {
      paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(Aircraft.tmpLoc1);
      localObject1 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      this.arrestorHook.computePos(paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor, Aircraft.tmpLoc1, (Loc)localObject1);
      this.arrestorVSink = (float)(Pn.jdField_z_of_type_Double - ((Loc)localObject1).getPoint().jdField_z_of_type_Double);
    }

    Fd.set(this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d);
    Vnf.set(Normal);

    this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Normal);
    this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Fd);
    Fd.normalize();

    Pn.jdField_x_of_type_Double = 0.0D; Pn.jdField_y_of_type_Double = 0.0D;
    Pn.jdField_z_of_type_Double -= this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double;

    this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Pn);
    this.D = (-Normal.dot(Pn));

    if (!this.bIsMaster) this.D -= 0.015D;
    if (this.D > 50.0D) {
      this.nearGround = false;
      return;
    }
    this.nearGround = true;
    float tmp1369_1368 = (this.gWheelSinking[2] = 0.0F); this.gWheelSinking[1] = tmp1369_1368; this.gWheelSinking[0] = tmp1369_1368;

    for (int j = 0; j < this.pnti.length; j++) {
      k = this.pnti[j];
      if (k <= 0) { Pnt[j].set(0.0F, 0.0F, 0.0F); } else {
        this.HM.hookMatrix(k, M4);
        Pnt[j].set((float)M4.m03, (float)M4.m13, (float)M4.m23);
      }
    }
    this.nP = 0; this.nOfGearsOnGr = 0; this.nOfPoiOnGr = 0;

    this.tmpV.set(0.0D, 1.0D, 0.0D);
    Forward.cross(this.tmpV, Normal);
    Forward.normalize();
    Right.cross(Normal, Forward);

    for (int k = 0; k < this.pnti.length; k++) {
      clp[k] = false;
      if (k <= 2) {
        this.bIsGear = true;
        if (((k == 0) && ((!this.lgear) || (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.01F))) || (
          (k == 1) && ((!this.rgear) || (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.01F)))) continue;
        if ((k == 2) && (!this.cgear)) continue; 
      } else {
        this.bIsGear = false;
      }
      PnT.set(Pnt[k]);
      this.d = (Normal.dot(PnT) + this.D);
      Fx.set(Fd);
      this.MassCoeff = (0.0004D * this.FM.jdField_M_of_type_ComMaddoxIl2FmMass.getFullMass());
      if (this.MassCoeff > 1.0D) this.MassCoeff = 1.0D;

      if (this.d < 0.0D) {
        if (!testPropellorCollision(k)) continue;
        if (this.isWater) {
          if (!testWaterCollision(k)) continue;
        }
        else {
          if (this.bIsGear ? 
            !testGearCollision(k) : 
            !testNonGearCollision(k))
            continue;
        }
        clp[k] = true; this.nP += 1;
      } else {
        if ((this.d >= 0.1D) || (this.isWater) || (this.bIsGear) || 
          (!testNonGearCollision(k))) continue;
        clp[k] = true; this.nP += 1;
      }

      PnT.jdField_x_of_type_Double += this.FM.jdField_Arms_of_type_ComMaddoxIl2FmArm.GC_GEAR_SHIFT;
      Fx.cross(PnT, Tn);
      Fv.set(Fx);
      if ((this.bIsSail) && (bInWaterList(k))) {
        this.tmpV.scale(this.fricF * 0.5D, Forward);
        Tn.add(this.tmpV);
        this.tmpV.scale(this.fricR * 0.5D, Right);
        Tn.add(this.tmpV);
      }
      if (this.bIsMaster) {
        this.FM.jdField_GF_of_type_ComMaddoxJGPVector3d.add(Tn);
        this.FM.jdField_GM_of_type_ComMaddoxJGPVector3d.add(Fx);
      }

    }

    if ((this.oldNOfGearsOnGr != this.nOfGearsOnGr) || (this.oldNOfPoiOnGr != this.nOfPoiOnGr)) this.gearsChanged = true; else
      this.gearsChanged = false;
    this.oldNOfGearsOnGr = this.nOfGearsOnGr;
    this.oldNOfPoiOnGr = this.nOfPoiOnGr;
    this.onGround = (this.nP > 0);

    if (Config.isUSE_RENDER()) drawEffects();

    if (this.bIsMaster) {
      this.FM.canChangeBrakeShoe = false;
      localObject2 = localBigshipGeneric;
      if (localObject2 != null) {
        this.FM.brakeShoeLastCarrier = ((Actor)localObject2);
      }
      else if ((Mission.isCoop()) && (!Mission.isServer()) && (Actor.isAlive(this.FM.brakeShoeLastCarrier)) && (Time.current() < 60000L))
      {
        localObject2 = (BigshipGeneric)this.FM.brakeShoeLastCarrier;
      }
      if (localObject2 != null) {
        if (this.FM.brakeShoe) {
          if (!isAnyDamaged()) {
            L.set(this.FM.brakeShoeLoc);
            L.add(this.FM.brakeShoeLastCarrier.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
            this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(L.getPoint());
            this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.set(L.getOrient());
            this.FM.brakeShoeLastCarrier.getSpeed(this.FM.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
            this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
            for (int m = 0; m < 3; m++) this.gVelocity[m] = 0.0D;
            this.onGround = true;
            this.FM.canChangeBrakeShoe = true;
          } else {
            this.FM.brakeShoe = false;
          }
        }
        else if ((this.nOfGearsOnGr == 3) && (this.nP == 3) && (this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.lengthSquared() < 1.0D)) {
          this.FM.brakeShoeLoc.set(this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent());
          this.FM.brakeShoeLoc.sub(this.FM.brakeShoeLastCarrier.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent());
          this.FM.canChangeBrakeShoe = true;
        }

      }

    }

    if (!this.bIsMaster) return;
    if ((this.onGround) && (!this.isWater)) processingCollisionEffect();

    double d2 = Engine.cur.land.HQ_ForestHeightHere(this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double);
    if ((d2 > 0.0D) && 
      (this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double <= d1 + d2) && 
      (((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).isEnablePostEndAction(0.0D)))
      ((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).postEndAction(0.0D, Engine.actorLand(), 2, null);
  }

  private boolean testNonGearCollision(int paramInt)
  {
    this.nOfPoiOnGr += 1;

    Vs.set(this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d);
    Vs.scale(-1.0D);
    this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Vs);
    this.tmpV.set(Pnt[paramInt]);
    this.tmpV.cross(this.FM.getW(), this.tmpV);
    Vs.add(this.tmpV);
    ForwardVPrj = Forward.dot(Vs);
    NormalVPrj = Normal.dot(Vs);
    RightVPrj = Right.dot(Vs);
    if (NormalVPrj > 0.0D) {
      NormalVPrj -= 3.0D;
      if (NormalVPrj < 0.0D) NormalVPrj = 0.0D;
    }

    double d1 = 1.0D;
    double d2 = this.d - 0.06D;
    double d3 = this.d + 0.04000000000000001D;
    if (d2 > 0.0D) d2 = 0.0D;
    if (d2 < -2.0D) d2 = -2.0D;
    if (d3 > 0.0D) d3 = 0.0D;
    if (d3 < -0.25D) d3 = -0.25D;
    d1 = Math.max(-120000.0D * d2, -360000.0D * d3);
    d1 *= this.MassCoeff;
    Tn.scale(d1, Normal);

    this.fric = (-40000.0D * NormalVPrj);
    if (this.fric > 100000.0D) this.fric = 100000.0D;
    if (this.fric < -100000.0D) this.fric = -100000.0D;
    this.tmpV.scale(this.fric, Normal);
    Tn.add(this.tmpV);

    double d4 = 1.0D - 0.5D * Math.abs(Pnt[paramInt].y) / this.FM.jdField_Arms_of_type_ComMaddoxIl2FmArm.WING_END;
    this.fricF = (-8000.0D * ForwardVPrj);
    this.fricR = (-50000.0D * RightVPrj);
    this.fric = Math.sqrt(this.fricF * this.fricF + this.fricR * this.fricR);
    if (this.fric > 20000.0D * d4) {
      this.fric = (20000.0D * d4 / this.fric);
      this.fricF *= this.fric;
      this.fricR *= this.fric;
    }
    this.tmpV.scale(this.fricF, Forward);
    Tn.add(this.tmpV);
    this.tmpV.scale(this.fricR, Right);
    Tn.add(this.tmpV);

    if ((paramInt > 6) && (this.bIsMaster)) { World.cur(); if ((this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor == World.getPlayerAircraft()) && (this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.FM.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) < 2.0D) && (!this.bTheBabysGonnaBlow))
      {
        for (int i = 0; i < this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length; i++) {
          if ((this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i] == null) || (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i].length <= 0)) continue; for (int j = 0; j < this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i].length; j++) {
            if (((!(this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i][j] instanceof BombGun)) && (!(this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i][j] instanceof RocketGun))) || (!this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i][j].haveBullets()) || (this.FM.getSpeed() <= 38.0F))
              continue;
            if (!this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i][j].getHookName().startsWith("_External")) {
              continue;
            }
            this.bTheBabysGonnaBlow = true;
          }

        }

        if ((this.bTheBabysGonnaBlow) && 
          ((!this.FM.isPlayers()) || (World.cur().diffCur.Vulnerability)) && 
          (((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).isEnablePostEndAction(0.0D))) {
          ((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).postEndAction(0.0D, Engine.actorLand(), 2, null);
          if (this.FM.isPlayers()) HUD.log("FailedBombsDetonate");
        }
      }
    }

    if ((this.bIsMaster) && (NormalVPrj < 0.0D)) {
      double d5 = (ForwardVPrj * ForwardVPrj + RightVPrj * RightVPrj) * 0.0002366863905325444D + NormalVPrj * NormalVPrj * 0.02040816326530612D;
      if (d5 > 1.0D) {
        landHit(paramInt, (float)d5);
      }
    }
    return true;
  }

  private boolean testGearCollision(int paramInt) {
    if (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.01D) return false;
    double d1 = 1.0D;
    this.gWheelSinking[paramInt] = (float)(-this.d);

    Vs.set(this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d);
    Vs.scale(-1.0D);
    this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Vs);
    this.tmpV.set(Pnt[paramInt]);
    this.tmpV.cross(this.FM.getW(), this.tmpV);
    Vs.add(this.tmpV);
    ForwardVPrj = Forward.dot(Vs);
    NormalVPrj = Normal.dot(Vs);
    RightVPrj = Right.dot(Vs);
    if (NormalVPrj > 0.0D) NormalVPrj = 0.0D;

    double d2 = this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double * this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double + this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double * this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double - 2.0D;
    if (d2 < 0.0D) d2 = 0.0D;
    double d3 = 0.01D * d2;
    if (d3 < 0.0D) d3 = 0.0D;
    if (d3 > 4.5D) d3 = 4.5D;
    double d4 = 0.4000000059604645D * Math.max(this.roughness * this.roughness, this.roughness);
    if (d3 > d4) d3 = d4;
    if (this.roughness > d3) this.roughness = d3;
    if (this.roughness < 0.2000000029802322D) this.roughness = 0.2000000029802322D;
    if (paramInt < 2) {
      this.d += World.Rnd().nextFloat(-2.0F, 1.0F) * 0.04D * d3 * this.MassCoeff;
      d1 = Math.max(-9500.0D * (this.d - 0.1D), -950000.0D * this.d);
      d1 *= this.springsStiffness;
    } else {
      this.d += World.Rnd().nextFloat(-2.0F, 1.0F) * 0.04F * d3 * this.MassCoeff;
      d1 = Math.max(-9500.0D * (this.d - 0.1D), -950000.0D * this.d);
      if ((Pnt[paramInt].jdField_x_of_type_Float > 0.0F) && (Fd.dot(Normal) >= 0.0D)) d1 *= 0.449999988079071D; else {
        d1 *= this.tailStiffness;
      }
    }
    d1 -= 40000.0D * NormalVPrj;
    Tn.scale(d1, Normal);

    double d5 = 0.0001D * d1;
    double d6 = this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.getBrake();
    double d7 = this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder();
    double d9;
    double d10;
    double d17;
    switch (paramInt) {
    case 0:
    case 1:
      double d8 = 1.2D;
      if (paramInt == 0) d8 = -1.2D;

      this.nOfGearsOnGr += 1;
      this.isGearColl = true;
      this.gVelocity[paramInt] = ForwardVPrj;
      if (d6 > 0.1D) {
        if (d7 > 0.1D) {
          d6 += d8 * d6 * (d7 - 0.1D);
        }
        if (d7 < -0.1D) {
          d6 += d8 * d6 * (d7 + 0.1D);
        }
        if (d6 > 1.0D) d6 = 1.0D;
        if (d6 < 0.0D) d6 = 0.0D;
      }

      d9 = Math.sqrt(ForwardVPrj * ForwardVPrj + RightVPrj * RightVPrj);
      if (d9 < 0.01D) d9 = 0.01D;
      d10 = 1.0D / d9;
      double d11 = ForwardVPrj * d10;
      if (d11 < 0.0D) d11 *= -1.0D;
      double d12 = RightVPrj * d10;
      if (d12 < 0.0D) d12 *= -1.0D;

      double d13 = 5.0D;
      if (PnT.jdField_y_of_type_Double * RightVPrj > 0.0D) {
        if (PnT.jdField_y_of_type_Double > 0.0D) d13 += 7.0D * RightVPrj; else
          d13 -= 7.0D * RightVPrj;
        if (d13 > 20.0D) d13 = 20.0D;
      }

      double d14 = 15000.0D;
      if (d9 < 3.0D) {
        d15 = -0.333333D * (d9 - 3.0D);
        d14 += 3000.0D * d15 * d15;
      }

      this.fricR = (-d13 * 100000.0D * RightVPrj * d5);

      this.maxFric = (d14 * d5 * d12);
      if (this.fricR > this.maxFric) this.fricR = this.maxFric;
      if (this.fricR < -this.maxFric) this.fricR = (-this.maxFric);

      this.fricF = (-d13 * 600.0D * ForwardVPrj * d5);
      this.maxFric = (d13 * Math.max(200.0D * (1.0D - 0.04D * d9), 5.0D) * d5 * d11);

      if (this.fricF > this.maxFric) this.fricF = this.maxFric;
      if (this.fricF < -this.maxFric) this.fricF = (-this.maxFric);

      double d15 = 0.03D;
      if (Pnt[2].jdField_x_of_type_Float > 0.0F) d15 = 0.06D;
      double d16 = Math.abs(ForwardVPrj);
      if (d16 < 1.0D) d15 += 3.0D * (1.0D - d16);
      d15 *= 0.03D * d6;

      this.fricF += -300000.0D * d15 * ForwardVPrj * d5;

      this.maxFric = (d14 * d5 * d11);
      if (this.fricF > this.maxFric) this.fricF = this.maxFric;
      if (this.fricF < -this.maxFric) this.fricF = (-this.maxFric);

      this.fric = Math.sqrt(this.fricF * this.fricF + this.fricR * this.fricR);
      if (this.fric > this.maxFric) {
        this.fric = (this.maxFric / this.fric);
        this.fricF *= this.fric;
        this.fricR *= this.fric;
      }

      this.tmpV.scale(this.fricF, Forward);
      Tn.add(this.tmpV);
      this.tmpV.scale(this.fricR, Right);
      Tn.add(this.tmpV);

      if ((!this.bIsMaster) || (NormalVPrj >= 0.0D)) break;
      d17 = ForwardVPrj * ForwardVPrj * 8.000000000000001E-005D + RightVPrj * RightVPrj * 0.0068D;

      if (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasArrestorControl) {
        d17 += NormalVPrj * NormalVPrj * 0.025D;
      }
      else {
        d17 += NormalVPrj * NormalVPrj * 0.07000000000000001D;
      }

      if (d17 <= 1.0D) break;
      this.fatigue[paramInt] += 10;
      double d18 = 38000.0D + this.FM.jdField_M_of_type_ComMaddoxIl2FmMass.massEmpty * 6.0D;
      double d19 = (Tn.jdField_x_of_type_Double * Tn.jdField_x_of_type_Double * 0.15D + Tn.jdField_y_of_type_Double * Tn.jdField_y_of_type_Double * 0.15D + Tn.jdField_z_of_type_Double * Tn.jdField_z_of_type_Double * 0.08D) / (d18 * d18);

      if ((this.fatigue[paramInt] <= 100) && (d19 <= 1.0D)) break;
      landHit(paramInt, (float)d17);
      Aircraft localAircraft2 = (Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor;
      if (paramInt == 0) localAircraft2.msgCollision(localAircraft2, "GearL2_D0", "GearL2_D0");
      if (paramInt != 1) break; localAircraft2.msgCollision(localAircraft2, "GearR2_D0", "GearR2_D0"); break;
    case 2:
      this.nOfGearsOnGr += 1;

      if ((this.bTailwheelLocked) && (this.steerAngle > -5.0F) && (this.steerAngle < 5.0F)) {
        this.gVelocity[paramInt] = ForwardVPrj;
        this.steerAngle = 0.0F;
        this.fric = (-400.0D * ForwardVPrj);
        this.maxFric = 400.0D;
        if (this.fric > this.maxFric) this.fric = this.maxFric;
        if (this.fric < -this.maxFric) this.fric = (-this.maxFric);
        this.tmpV.scale(this.fric, Forward);
        Tn.add(this.tmpV);

        this.fric = (-10000.0D * RightVPrj);
        this.maxFric = 40000.0D;
        if (this.fric > this.maxFric) this.fric = this.maxFric;
        if (this.fric < -this.maxFric) this.fric = (-this.maxFric);
        this.tmpV.scale(this.fric, Right);
        Tn.add(this.tmpV);
      }
      else if (this.bFrontWheel) {
        this.gVelocity[paramInt] = ForwardVPrj;

        this.tmpV.set(1.0D, -0.5D * this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.getRudder(), 0.0D);
        steerAngleFork.setDeg(this.steerAngle, (float)Math.toDegrees(Math.atan2(this.tmpV.jdField_y_of_type_Double, this.tmpV.jdField_x_of_type_Double)));
        this.steerAngle = steerAngleFork.getDeg(0.115F);
        nwRight.cross(Normal, this.tmpV);
        nwRight.normalize();
        nwForward.cross(nwRight, Normal);

        ForwardVPrj = nwForward.dot(Vs);
        RightVPrj = nwRight.dot(Vs);

        d9 = Math.sqrt(ForwardVPrj * ForwardVPrj + RightVPrj * RightVPrj);
        if (d9 < 0.01D) d9 = 0.01D;
        d10 = 1.0D / d9;

        this.fricF = (-100.0D * ForwardVPrj);
        this.maxFric = 4000.0D;
        if (this.fricF > this.maxFric) this.fricF = this.maxFric;
        if (this.fricF < -this.maxFric) this.fricF = (-this.maxFric);

        this.fricR = (-500.0D * RightVPrj);
        this.maxFric = 4000.0D;
        if (this.fricR > this.maxFric) this.fricR = this.maxFric;
        if (this.fricR < -this.maxFric) this.fricR = (-this.maxFric);

        this.maxFric = (1.0D - 0.02D * d9);
        if (this.maxFric < 0.1D) this.maxFric = 0.1D;
        this.maxFric = (5000.0D * this.maxFric);
        this.fric = Math.sqrt(this.fricF * this.fricF + this.fricR * this.fricR);
        if (this.fric > this.maxFric) {
          this.fric = (this.maxFric / this.fric);
          this.fricF *= this.fric;
          this.fricR *= this.fric;
        }

        this.tmpV.scale(this.fricF, Forward);
        Tn.add(this.tmpV);
        this.tmpV.scale(this.fricR, Right);
        Tn.add(this.tmpV);
      }
      else {
        this.gVelocity[paramInt] = Vs.length();
        if (Vs.lengthSquared() > 0.04D) {
          steerAngleFork.setDeg(this.steerAngle, (float)Math.toDegrees(Math.atan2(Vs.jdField_y_of_type_Double, Vs.jdField_x_of_type_Double)));
          this.steerAngle = steerAngleFork.getDeg(0.115F);
        }
        this.fricF = (-1000.0D * ForwardVPrj);
        this.fricR = (-1000.0D * RightVPrj);
        this.fric = Math.sqrt(this.fricF * this.fricF + this.fricR * this.fricR);
        this.maxFric = 1500.0D;
        if (this.fric > this.maxFric) {
          this.fric = (this.maxFric / this.fric);
          this.fricF *= this.fric;
          this.fricR *= this.fric;
        }
        this.tmpV.scale(this.fricF, Forward);
        Tn.add(this.tmpV);
        this.tmpV.scale(this.fricR, Right);
        Tn.add(this.tmpV);
      }

      if ((!this.bIsMaster) || (NormalVPrj >= 0.0D)) break;
      d17 = (ForwardVPrj * ForwardVPrj + RightVPrj * RightVPrj) * 0.0001D;
      if (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasArrestorControl) {
        d17 += NormalVPrj * NormalVPrj * 0.004D;
      }
      else {
        d17 += NormalVPrj * NormalVPrj * 0.02D;
      }
      if (d17 <= 1.0D) break;
      landHit(paramInt, (float)d17);

      Aircraft localAircraft1 = (Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor;
      localAircraft1.msgCollision(localAircraft1, "GearC2_D0", "GearC2_D0"); break;
    default:
      this.fricF = (-4000.0D * ForwardVPrj);
      this.fricR = (-4000.0D * RightVPrj);
      this.fric = Math.sqrt(this.fricF * this.fricF + this.fricR * this.fricR);
      if (this.fric > 10000.0D) {
        this.fric = (10000.0D / this.fric);
        this.fricF *= this.fric;
        this.fricR *= this.fric;
      }
      this.tmpV.scale(this.fricF, Forward);
      Tn.add(this.tmpV);
      this.tmpV.scale(this.fricR, Right);
      Tn.add(this.tmpV);
    }

    Tn.scale(this.MassCoeff);

    return true;
  }

  private boolean testWaterCollision(int paramInt) {
    Vs.set(this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d);
    Vs.scale(-1.0D);
    this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Vs);
    this.tmpV.set(Pnt[paramInt]);
    this.tmpV.cross(this.FM.getW(), this.tmpV);
    Vs.add(this.tmpV);
    ForwardVPrj = Forward.dot(Vs);
    NormalVPrj = Normal.dot(Vs);
    RightVPrj = Right.dot(Vs);

    double d1 = ForwardVPrj;
    if (d1 < 0.0D) d1 = 0.0D;
    if (((!this.bIsSail) || (!bInWaterList(paramInt))) && (this.d < -2.0D)) this.d = -2.0D;
    double d2 = -(1.0D + 0.3D * d1) * this.sinkFactor * this.d * Math.abs(this.d) * (1.0D + 0.3D * Math.sin((1 + paramInt % 3) * Time.current() * 0.001D));
    double d3 = 0.0001D * d2;

    if ((this.bIsSail) && (bInWaterList(paramInt))) {
      if (NormalVPrj > 0.0D) NormalVPrj = 0.0D;
      Tn.scale(d2, Normal);

      this.fric = (-1000.0D * NormalVPrj);
      if (this.fric > 4000.0D)
        this.fric = 4000.0D;
      if (this.fric < -4000.0D)
        this.fric = -4000.0D;
      this.tmpV.scale(this.fric, Normal);
      Tn.add(this.tmpV);

      this.fricF = (-40.0D * ForwardVPrj);
      this.fricR = (-300.0D * RightVPrj);
      this.fric = Math.sqrt(this.fricF * this.fricF + this.fricR * this.fricR);
      if (this.fric > 50000.0D) {
        this.fric = (50000.0D / this.fric);
        this.fricF *= this.fric;
        this.fricR *= this.fric;
      }

      this.tmpV.scale(this.fricF * 0.5D, Forward);
      Tn.add(this.tmpV);
      this.tmpV.scale(this.fricR * 0.5D, Right);
      Tn.add(this.tmpV);
    }
    else {
      Tn.scale(d2, Normal);

      this.fric = (-1000.0D * NormalVPrj);
      if (this.fric > 4000.0D)
        this.fric = 4000.0D;
      if (this.fric < -4000.0D)
        this.fric = -4000.0D;
      this.tmpV.scale(this.fric, Normal);
      Tn.add(this.tmpV);

      this.fricF = (-500.0D * ForwardVPrj);
      this.fricR = (-800.0D * RightVPrj);
      this.fric = Math.sqrt(this.fricF * this.fricF + this.fricR * this.fricR);
      if (this.fric > 50000.0D) {
        this.fric = (50000.0D / this.fric);
        this.fricF *= this.fric;
        this.fricR *= this.fric;
      }

      this.tmpV.scale(this.fricF, Forward);
      Tn.add(this.tmpV);
      this.tmpV.scale(this.fricR, Right);
      Tn.add(this.tmpV);

      if ((this.sinkFactor > 1.0F) && (!this.bIsSail)) {
        this.sinkFactor -= 0.4F * Time.tickLenFs();
        if (this.sinkFactor < 1.0F) {
          this.sinkFactor = 1.0F;
        }
      }
    }
    if ((this.bIsMaster) && (NormalVPrj < 0.0D)) {
      double d4 = (ForwardVPrj * ForwardVPrj + RightVPrj * RightVPrj) * 0.0004D + NormalVPrj * NormalVPrj * 0.001111111111111111D;
      if (d4 > 1.0D) {
        landHit(paramInt, (float)d4);
      }
    }
    return true;
  }

  private boolean testPropellorCollision(int paramInt) {
    if ((this.bIsMaster) && (paramInt >= 3) && 
      (paramInt <= 6)) {
      if ((this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor == World.getPlayerAircraft()) && (!World.cur().diffCur.Realistic_Landings))
        return false;
      this.FM.setCapableOfTaxiing(false);
      switch (this.FM.Scheme) {
      default:
        break;
      case 1:
        ((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).hitProp(0, 0, Engine.actorLand());
        break;
      case 2:
      case 3:
        if (paramInt < 5) ((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).hitProp(0, 0, Engine.actorLand()); else
          ((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).hitProp(1, 0, Engine.actorLand());
        break;
      case 4:
      case 5:
        ((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).hitProp(paramInt - 3, 0, Engine.actorLand());
        break;
      case 6:
        switch (paramInt) {
        case 3:
          ((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).hitProp(0, 0, Engine.actorLand());
          break;
        case 4:
        case 5:
          ((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).hitProp(1, 0, Engine.actorLand());
          break;
        case 6:
          ((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).hitProp(2, 0, Engine.actorLand());
        }

      }

      return false;
    }

    return true;
  }

  private void landHit(int paramInt, double paramDouble) {
    if ((this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.length() < 13.0D) || (this.pnti[paramInt] < 0)) return;
    if ((this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor == World.getPlayerAircraft()) && (!World.cur().diffCur.Realistic_Landings)) return;
    ActorHMesh localActorHMesh = (ActorHMesh)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor;
    if (!Actor.isValid(localActorHMesh)) return;
    Mesh localMesh = localActorHMesh.mesh();
    long l = Time.tick();
    String str = localActorHMesh.findHook(localMesh.hookName(this.pnti[paramInt])).chunkName();
    if (str.compareTo("CF_D0") == 0) {
      if (paramDouble > 2.0D) MsgCollision.post(l, localActorHMesh, Engine.actorLand(), str, "Body"); 
    }
    else if (str.compareTo("Tail1_D0") == 0) {
      if (paramDouble > 1.3D) MsgCollision.post(l, localActorHMesh, Engine.actorLand(), str, "Body"); 
    }
    else if (((this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof Scheme1)) && (str.compareTo("Engine1_D0") == 0)) {
      MsgCollision.post(l, localActorHMesh, Engine.actorLand(), str, "Body");
      if (paramDouble > 5.0D) MsgCollision.post(l, localActorHMesh, Engine.actorLand(), "CF_D0", "Body"); 
    } else {
      MsgCollision.post(l, localActorHMesh, Engine.actorLand(), str, "Body");
    }
  }
  public void hitLeftGear() {
    this.lgear = false; this.FM.brakeShoe = false; } 
  public void hitRightGear() { this.rgear = false; this.FM.brakeShoe = false; } 
  public void hitCentreGear() { this.cgear = false; this.FM.brakeShoe = false; }

  public boolean isAnyDamaged() {
    return (!this.lgear) || (!this.rgear) || (!this.cgear);
  }

  private void drawEffects()
  {
    boolean bool = this.FM.isTick(16, 0);

    for (int i = 0; i < 3; i++) {
      if ((this.bIsSail) && (bool) && (this.isWater) && (clp[i] != 0) && (this.FM.getSpeedKMH() > 10.0F)) {
        if (this.clpGearEff[i][0] != null)
          continue;
        this.clpGearEff[i][0] = Eff3DActor.New(this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor, null, new Loc(new Point3d(Pnt[i]), new Orient(0.0F, 0.0F, 0.0F)), 1.0F, "3DO/Effects/Tracers/ShipTrail/WakeSmaller.eff", -1.0F);

        this.clpGearEff[i][1] = Eff3DActor.New(this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor, null, new Loc(new Point3d(Pnt[i]), new Orient(0.0F, 0.0F, 0.0F)), 1.0F, "3DO/Effects/Tracers/ShipTrail/WaveSmaller.eff", -1.0F);
      }
      else
      {
        if ((!bool) || (this.clpGearEff[i][0] == null))
          continue;
        Eff3DActor.finish(this.clpGearEff[i][0]);
        Eff3DActor.finish(this.clpGearEff[i][1]);
        this.clpGearEff[i][0] = null;
        this.clpGearEff[i][1] = null;
      }
    }

    for (i = 0; i < this.pnti.length; i++) {
      if ((clp[i] != 0) && (this.FM.jdField_Vrel_of_type_ComMaddoxJGPVector3d.length() > 16.666666670000001D) && (!isUnderDeck())) {
        if (this.clpEff[i] != null)
          continue;
        if (this.isWater) {
          this.effectName = "EFFECTS/Smokes/SmokeAirSplat.eff";
        }
        else if (World.cur().camouflage == 1)
          this.effectName = "EFFECTS/Smokes/SmokeAirTouchW.eff";
        else {
          this.effectName = "EFFECTS/Smokes/SmokeAirTouch.eff";
        }

        this.clpEff[i] = Eff3DActor.New(this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor, null, new Loc(new Point3d(Pnt[i]), new Orient(0.0F, 90.0F, 0.0F)), 1.0F, this.effectName, -1.0F);
      }
      else {
        if ((!bool) || (this.clpEff[i] == null))
          continue;
        Eff3DActor.finish(this.clpEff[i]);
        this.clpEff[i] = null;
      }
    }
    if (this.FM.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum() > 0)
    {
      for (i = 0; i < this.FM.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); i++) {
        this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(Aircraft.tmpLoc1);
        Pn.set(this.FM.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getPropPos());
        Aircraft.tmpLoc1.transform(Pn, PnT);
        float f = (float)(PnT.jdField_z_of_type_Double - Engine.cur.land.HQ(PnT.jdField_x_of_type_Double, PnT.jdField_y_of_type_Double));
        if ((f < 16.200001F) && (this.FM.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getThrustOutput() > 0.5F)) {
          Pn.jdField_x_of_type_Double -= f * Aircraft.cvt(this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -30.0F, 30.0F, 8.0F, 2.0F);
          Aircraft.tmpLoc1.transform(Pn, PnT);
          PnT.jdField_z_of_type_Double = Engine.cur.land.HQ(PnT.jdField_x_of_type_Double, PnT.jdField_y_of_type_Double);
          if (this.clpEngineEff[i][0] == null) {
            Aircraft.tmpLoc1.transformInv(PnT);
            if (this.isWater) {
              this.clpEngineEff[i][0] = Eff3DActor.New(this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor, null, new Loc(PnT), 1.0F, "3DO/Effects/Aircraft/GrayGroundDust2.eff", -1.0F);
              this.clpEngineEff[i][1] = Eff3DActor.New(new Loc(PnT), 1.0F, "3DO/Effects/Aircraft/WhiteEngineWaveTSPD.eff", -1.0F);
            } else {
              this.clpEngineEff[i][0] = Eff3DActor.New(this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor, null, new Loc(PnT), 1.0F, "3DO/Effects/Aircraft/GrayGroundDust" + (World.cur().camouflage == 1 ? "2" : "1") + ".eff", -1.0F);
            }
          } else {
            if (this.isWater) {
              if (this.clpEngineEff[i][1] == null) {
                Eff3DActor.finish(this.clpEngineEff[i][0]);
                this.clpEngineEff[i][0] = null;
                continue;
              }
            }
            else if (this.clpEngineEff[i][1] != null) {
              Eff3DActor.finish(this.clpEngineEff[i][0]);
              this.clpEngineEff[i][0] = null;
              Eff3DActor.finish(this.clpEngineEff[i][1]);
              this.clpEngineEff[i][1] = null;
              continue;
            }

            Aircraft.tmpOr.set(this.FM.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getAzimut() + 180.0F, 0.0F, 0.0F);
            this.clpEngineEff[i][0].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(PnT);
            this.clpEngineEff[i][0].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(Aircraft.tmpOr);
            this.clpEngineEff[i][0].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.resetAsBase();
            if (this.clpEngineEff[i][1] != null) {
              PnT.jdField_z_of_type_Double = 0.0D;
              this.clpEngineEff[i][1].jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(PnT);
            }
          }
        } else {
          if (this.clpEngineEff[i][0] != null) {
            Eff3DActor.finish(this.clpEngineEff[i][0]);
            this.clpEngineEff[i][0] = null;
          }
          if (this.clpEngineEff[i][1] != null) {
            Eff3DActor.finish(this.clpEngineEff[i][1]);
            this.clpEngineEff[i][1] = null;
          }
        }
      }
    }
  }

  private void turnOffEffects() {
    if (this.FM.isTick(69, 0)) {
      for (int i = 0; i < this.pnti.length; i++) {
        if (this.clpEff[i] != null) {
          Eff3DActor.finish(this.clpEff[i]);
          this.clpEff[i] = null;
        }
      }
      for (int j = 0; j < this.FM.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); j++) {
        if (this.clpEngineEff[j][0] != null) {
          Eff3DActor.finish(this.clpEngineEff[j][0]);
          this.clpEngineEff[j][0] = null;
        }
        if (this.clpEngineEff[j][1] != null) {
          Eff3DActor.finish(this.clpEngineEff[j][1]);
          this.clpEngineEff[j][1] = null;
        }
      }
    }
  }

  private void processingCollisionEffect()
  {
    if (!this.canDoEffect) return;
    this.Vnorm = this.FM.jdField_Vwld_of_type_ComMaddoxJGPVector3d.dot(Normal);
    if ((this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor == World.getPlayerAircraft()) && (World.cur().diffCur.Realistic_Landings) && (this.Vnorm < -20.0D) && (World.Rnd().nextFloat() < 0.02D)) {
      this.canDoEffect = false;
      int i = 20 + (int)(30.0F * World.Rnd().nextFloat());
      if ((this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] != null) && (this.FM.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].countBullets() != 0))
      {
        i = 0;
      }
      if (((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).isEnablePostEndAction(i)) {
        Eff3DActor localEff3DActor = null;
        if (i > 0) {
          Eff3DActor.New(this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor, null, new Loc(new Point3d(0.0D, 0.0D, 0.0D), new Orient(0.0F, 90.0F, 0.0F)), 1.0F, "3DO/Effects/Aircraft/FireGND.eff", i);

          localEff3DActor = Eff3DActor.New(this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor, null, new Loc(new Point3d(0.0D, 0.0D, 0.0D), new Orient(0.0F, 90.0F, 0.0F)), 1.0F, "3DO/Effects/Aircraft/BlackHeavyGND.eff", i + 10);

          ((NetAircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).sfxSmokeState(0, 0, true);
        }
        ((Aircraft)this.FM.jdField_actor_of_type_ComMaddoxIl2EngineActor).postEndAction(i, Engine.actorLand(), 2, localEff3DActor);
      }
    }
  }

  public void load(SectFile paramSectFile)
  {
    this.bIsSail = (paramSectFile.get("Aircraft", "Seaplane", 0) != 0);
    this.sinkFactor = paramSectFile.get("Gear", "SinkFactor", 1.0F);
    this.springsStiffness = paramSectFile.get("Gear", "SpringsStiffness", 1.0F);
    this.tailStiffness = paramSectFile.get("Gear", "TailStiffness", 0.6F);

    if (paramSectFile.get("Gear", "FromIni", 0) == 1) {
      this.H = paramSectFile.get("Gear", "H", 2.0F);
      this.Pitch = paramSectFile.get("Gear", "Pitch", 10.0F);
    } else {
      this.H = (this.Pitch = 0.0F);
    }
    String str = paramSectFile.get("Gear", "WaterClipList", "-");
    if (!str.startsWith("-")) {
      this.waterList = new int[3 + str.length() / 2];
      this.waterList[0] = 0;
      this.waterList[1] = 1;
      this.waterList[2] = 2;
      for (int i = 0; i < this.waterList.length - 3; i++) {
        this.waterList[(3 + i)] = (10 * (str.charAt(i + i) - '0') + 1 * (str.charAt(i + i + 1) - '0'));
        this.waterList[(3 + i)] += 3;
      }
    }
  }

  public float getLandingState() {
    if (!this.onGround) return 0.0F;

    float f = 0.4F + ((float)this.roughness - 0.2F) * 0.5F;
    if (f > 1.0F) f = 1.0F;
    return f;
  }

  public double plateFriction(FlightModel paramFlightModel)
  {
    Actor localActor = paramFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor;
    if (this.bUnderDeck) return 0.0D;
    if (!Actor.isValid(localActor)) return 0.2D;
    if (!World.cur().diffCur.Realistic_Landings) return 0.2D;
    float f = 200.0F;
    localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(corn);
    bPlateExist = false;
    bPlateGround = false;
    Engine.drawEnv().getFiltered((AbstractCollection)null, corn.jdField_x_of_type_Double - f, corn.jdField_y_of_type_Double - f, corn.jdField_x_of_type_Double + f, corn.jdField_y_of_type_Double + f, 1, plateFilter);

    if (bPlateExist) {
      if (bPlateGround) return 0.8D;
      return 0.0D;
    }

    int i = Engine.cur.land.HQ_RoadTypeHere(paramFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, paramFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double);
    switch (i) { case 1:
      return 0.8D;
    case 2:
      return 0.0D;
    case 3:
      return 5.0D; }
    return 3.8D;
  }

  private String s(int paramInt)
  {
    return "" + paramInt;
  }
  private boolean bInWaterList(int paramInt) { if (this.waterList != null) {
      for (int i = 0; i < this.waterList.length; i++) {
        if (this.waterList[i] == paramInt) {
          return true;
        }
      }
    }
    return false;
  }

  static
  {
    for (int i = 0; i < Pnt.length; i++) Pnt[i] = new Point3f();

    Normal = new Vector3d();
    Forward = new Vector3d();
    Right = new Vector3d();
    nwForward = new Vector3d();
    nwRight = new Vector3d();

    Vnf = new Vector3d();
    Fd = new Vector3d();
    Fx = new Vector3d();
    Vship = new Vector3d();
    Fv = new Vector3d();
    Tn = new Vector3d();
    Pn = new Point3d();
    PnT = new Point3d();
    Pship = new Point3d();
    Vs = new Vector3d();
    normal = new Vector3d();

    M4 = new Matrix4d();

    plateFilter = new PlateFilter(null);
    corn = new Point3d();
    corn1 = new Point3d();
    L = new Loc();
    plateBox = new float[6];
    bPlateExist = false;
    bPlateGround = false;
  }

  private static class PlateFilter
    implements ActorFilter
  {
    private PlateFilter()
    {
    }

    public boolean isUse(Actor paramActor, double paramDouble)
    {
      if (!(paramActor instanceof Plate)) return true;
      Mesh localMesh = ((ActorMesh)paramActor).mesh();
      localMesh.getBoundBox(Gear.plateBox);
      Gear.corn1.set(Gear.corn);
      Loc localLoc = paramActor.pos.getAbs();
      localLoc.transformInv(Gear.corn1);
      if ((Gear.plateBox[0] - 2.5F < Gear.corn1.jdField_x_of_type_Double) && (Gear.corn1.jdField_x_of_type_Double < Gear.plateBox[3] + 2.5F) && (Gear.plateBox[1] - 2.5F < Gear.corn1.jdField_y_of_type_Double) && (Gear.corn1.jdField_y_of_type_Double < Gear.plateBox[4] + 2.5F))
      {
        Gear.access$302(true);
        Gear.access$402(((Plate)paramActor).isGround());
      }
      return true;
    }

    PlateFilter(Gear.1 param1)
    {
      this();
    }
  }

  static class ClipFilter
    implements ActorFilter
  {
    public boolean isUse(Actor paramActor, double paramDouble)
    {
      return paramActor instanceof BigshipGeneric;
    }
  }
}