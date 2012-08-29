package com.maddox.il2.ai.air;

import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector2f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.engine.TextScr;
import com.maddox.il2.fm.AIFlightModel;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FMMath;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Polares;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.air.AR_234;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.BI_1;
import com.maddox.il2.objects.air.BI_6;
import com.maddox.il2.objects.air.G4M2E;
import com.maddox.il2.objects.air.HE_LERCHE3;
import com.maddox.il2.objects.air.KI_46_OTSUHEI;
import com.maddox.il2.objects.air.ME_163B1A;
import com.maddox.il2.objects.air.MXY_7;
import com.maddox.il2.objects.air.Scheme4;
import com.maddox.il2.objects.air.TA_152C;
import com.maddox.il2.objects.air.TA_183;
import com.maddox.il2.objects.air.TypeAcePlane;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeDockable;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeGlider;
import com.maddox.il2.objects.air.TypeSailPlane;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.air.TypeTransport;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.BombGunPara;
import com.maddox.il2.objects.weapons.TorpedoGun;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class Maneuver extends AIFlightModel
{
  private int task;
  private int maneuver = 26;
  private int curman;
  protected float mn_time;
  private int[] program = new int[8];
  private boolean bBusy = true;
  public boolean wasBusy = true;
  public boolean dontSwitch = false;
  public boolean aggressiveWingman = false;
  public boolean kamikaze = false;
  public boolean silence = true;
  public boolean bombsOut;
  private int bombsOutCounter = 0;
  public float direction;
  public Loc rwLoc;
  private boolean first = true;
  private int rocketsDelay = 0;
  private int bulletDelay = 0;
  private int submanDelay = 0;
  private static final int volleyL = 120;
  private float maxG;
  private float maxAOA;
  private float LandHQ;
  public float Alt = 0.0F;
  private float oldAOA;
  private float corrCoeff = 0.0F;
  private float corrElev = 0.0F;
  private float corrAile = 0.0F;

  private boolean checkGround = false;
  private boolean checkStrike = false;
  private boolean frequentControl = false;
  private boolean stallable = false;

  public FlightModel airClient = null;

  public FlightModel target = null;

  public FlightModel danger = null;

  private float dangerAggressiveness = 0.0F;
  private float oldDanCoeff = 0.0F;
  private int shotAtFriend = 0;
  private float distToFriend = 0.0F;

  public Actor target_ground = null;
  public AirGroup Group;
  protected boolean TaxiMode = false;
  protected boolean finished = false;
  protected boolean canTakeoff = false;
  public Point_Any wayCurPos;
  protected Point_Any wayPrevPos;
  protected Point_Any[] airdromeWay;
  protected int curAirdromePoi = 0;

  public long actionTimerStart = 0L;
  public long actionTimerStop = 0L;
  protected int gattackCounter = 0;
  private int beNearOffsPhase = 0;

  public int submaneuver = 0;
  private boolean dont_change_subm = false;
  private int tmpi = 0;
  private int sub_Man_Count = 0;
  private float dA;
  private float dist = 0.0F;

  private float man1Dist = 50.0F;
  private float bullTime = 0.0015F;
  protected static final int STAY_ON_THE_TAIL = 1;
  protected static final int NOT_TOO_FAST = 2;
  protected static final int FROM_WAYPOINT = 3;
  protected static final int CONST_SPEED = 4;
  protected static final int MIN_SPEED = 5;
  protected static final int MAX_SPEED = 6;
  protected static final int CONST_POWER = 7;
  protected static final int ZERO_POWER = 8;
  protected static final int BOOST_ON = 9;
  protected static final int FOLLOW_WITHOUT_FLAPS = 10;
  protected int speedMode = 3;
  protected float smConstSpeed = 90.0F;
  protected float smConstPower = 0.7F;
  protected FlightModel tailForStaying = null;
  public Vector3d tailOffset = new Vector3d();
  protected int Speak5minutes;
  protected int Speak1minute;
  protected int SpeakBeginGattack;
  protected boolean WeWereInGAttack = false;
  protected boolean WeWereInAttack = false;
  protected boolean SpeakMissionAccomplished = false;
  public static final int ROOKIE = 0;
  public static final int NORMAL = 1;
  public static final int VETERAN = 2;
  public static final int ACE = 3;
  public static final int NO_TASK = 0;
  public static final int WAIT = 1;
  public static final int STAY_FORMATION = 2;
  public static final int FLY_WAYPOINT = 3;
  public static final int DEFENCE = 4;
  public static final int DEFENDING = 5;
  public static final int ATTACK_AIR = 6;
  public static final int ATTACK_GROUND = 7;
  public static final int NONE = 0;
  public static final int HOLD = 1;
  public static final int PULL_UP = 2;
  public static final int LEVEL_PLANE = 3;
  public static final int ROLL = 4;
  public static final int ROLL_90 = 5;
  public static final int ROLL_180 = 6;
  public static final int SPIRAL_BRAKE = 7;
  public static final int SPIRAL_UP = 8;
  public static final int SPIRAL_DOWN = 9;
  public static final int CLIMB = 10;
  public static final int DIVING_0_RPM = 11;
  public static final int DIVING_30_DEG = 12;
  public static final int DIVING_45_DEG = 13;
  public static final int TURN = 14;
  public static final int MIL_TURN = 15;
  public static final int LOOP = 16;
  public static final int LOOP_DOWN = 17;
  public static final int HALF_LOOP_UP = 18;
  public static final int HALF_LOOP_DOWN = 19;
  public static final int STALL = 20;
  public static final int WAYPOINT = 21;
  public static final int SPEEDUP = 22;
  public static final int BELL = 23;
  public static final int FOLLOW = 24;
  public static final int LANDING = 25;
  public static final int TAKEOFF = 26;
  public static final int ATTACK = 27;
  public static final int WAVEOUT = 28;
  public static final int SINUS = 29;
  public static final int ZIGZAG_UP = 30;
  public static final int ZIGZAG_DOWN = 31;
  public static final int ZIGZAG_SPIT = 32;
  public static final int HALF_LOOP_DOWN_135 = 33;
  public static final int HARTMANN_REDOUT = 34;
  public static final int ROLL_360 = 35;
  public static final int STALL_POKRYSHKIN = 36;
  public static final int BARREL_POKRYSHKIN = 37;
  public static final int SLIDE_LEVEL = 38;
  public static final int SLIDE_DESCENT = 39;
  public static final int RANVERSMAN = 40;
  public static final int CUBAN = 41;
  public static final int CUBAN_INVERT = 42;
  public static final int GATTACK = 43;
  public static final int PILOT_DEAD = 44;
  public static final int HANG_ON = 45;
  public static final int DELAY = 48;
  public static final int EMERGENCY_LANDING = 49;
  public static final int GATTACK_DIVE = 50;
  public static final int GATTACK_TORPEDO = 51;
  public static final int GATTACK_CASSETTE = 52;
  public static final int GATTACK_KAMIKAZE = 46;
  public static final int GATTACK_TINYTIM = 47;
  public static final int FAR_FOLLOW = 53;
  public static final int SPIRAL_DOWN_SLOW = 54;
  public static final int FOLLOW_SPIRAL_UP = 55;
  public static final int SINUS_SHALLOW = 56;
  public static final int GAIN = 57;
  public static final int SEPARATE = 58;
  public static final int BE_NEAR = 59;
  public static final int EVADE_UP = 60;
  public static final int EVADE_DN = 61;
  public static final int ENERGY_ATTACK = 62;
  public static final int ATTACK_BOMBER = 63;
  public static final int PARKED_STARTUP = 64;
  public static final int COVER = 65;
  public static final int TAXI = 66;
  public static final int RUN_AWAY = 67;
  public static final int FAR_COVER = 68;
  public static final int TAKEOFF_VTOL_A = 69;
  public static final int LANDING_VTOL_A = 70;
  private static final int SUB_MAN0 = 0;
  private static final int SUB_MAN1 = 1;
  private static final int SUB_MAN2 = 2;
  private static final int SUB_MAN3 = 3;
  private static final int SUB_MAN4 = 4;
  public static final int LIVE = 0;
  public static final int RETURN = 1;
  public static final int TASK = 2;
  public static final int PROTECT_LEADER = 3;
  public static final int PROTECT_WINGMAN = 4;
  public static final int PROTECT_FRIENDS = 5;
  public static final int DESTROY_ENEMIES = 6;
  public static final int KEEP_ORDER = 7;
  public float[] takeIntoAccount = new float[8];
  public float[] AccountCoeff = new float[8];
  public static final int FVSB_BOOM_ZOOM = 0;
  public static final int FVSB_BOOM_ZOOM_TO_ENGINE = 1;
  public static final int FVSB_SHALLOW_DIVE_TO_ENGINE = 2;
  public static final int FVSB_FROM_AHEAD = 3;
  public static final int FVSB_FROM_BELOW = 4;
  public static final int FVSB_AS_IT_IS = 5;
  public static final int FVSB_FROM_SIDE = 6;
  public static final int FVSB_FROM_TAIL_TO_ENGINE = 7;
  public static final int FVSB_FROM_TAIL = 8;
  public static final int FVSB_SHALLOW_DIVE = 9;
  public static final int FVSB_FROM_BOTTOM = 10;
  private static final int demultiplier = 4;
  private Vector3d ApproachV = new Vector3d();
  private Vector3d TargV = new Vector3d();
  private Vector3d TargDevV = new Vector3d(0.0D, 0.0D, 0.0D);
  private Vector3d TargDevVnew = new Vector3d(0.0D, 0.0D, 0.0D);
  private Vector3d scaledApproachV = new Vector3d();
  private float ApproachR;
  private float TargY;
  private float TargZ;
  private float TargYS;
  private float TargZS;
  private float RandomVal;
  public Vector3d followOffset = new Vector3d();
  private Vector3d followTargShift = new Vector3d(0.0D, 0.0D, 0.0D);
  private Vector3d followCurShift = new Vector3d(0.0D, 0.0D, 0.0D);
  private float raAilShift = 0.0F;
  private float raElevShift = 0.0F;
  private float raRudShift = 0.0F;
  private float sinKren = 0.0F;

  private boolean strikeEmer = false;
  private FlightModel strikeTarg = null;
  private Vector3d strikeV = new Vector3d();

  private boolean direc = false;

  float Kmax = 10.0F;
  float rmin;
  float rmax;
  double phase = 0.0D;
  double radius = 50.0D;
  int pointQuality = -1;
  int curPointQuality = 50;

  private static Vector3d tmpV3d = new Vector3d();
  private static Vector3d tmpV3f = new Vector3d();
  public static Orient tmpOr = new Orient();
  public Orient saveOr = new Orient();
  private static Point3d Po = new Point3d();
  private static Point3d Pc = new Point3d();
  private static Point3d Pd = new Point3d();
  private static Vector3d Ve = new Vector3d();
  private Vector3d oldVe = new Vector3d();
  private Vector3d Vtarg = new Vector3d();
  private Vector3d constVtarg = new Vector3d();
  private Vector3d constVtarg1 = new Vector3d();
  private static Vector3d Vf = new Vector3d();
  private Vector3d Vxy = new Vector3d();
  private static Vector3d Vpl = new Vector3d();
  private AnglesFork AFo = new AnglesFork();
  private static Vector3f LandingOffset = new Vector3f(220.0F, 4.0F, 0.0F);
  private float[] headPos = new float[3];
  private float[] headOr = new float[3];

  private static Point3d P = new Point3d();
  private static Point2f Pcur = new Point2f();
  private static Vector2d Vcur = new Vector2d();
  private static Vector2f V_to = new Vector2f();
  private static Vector2d Vdiff = new Vector2d();
  private static Loc elLoc = new Loc();
  public static boolean showFM;
  private float pilotHeadT = 0.0F; private float pilotHeadY = 0.0F;

  public void set_task(int paramInt)
  {
    this.task = paramInt;
  }
  public int get_task() {
    return this.task;
  }
  public int get_maneuver() { return this.maneuver;
  }

  public void set_maneuver(int paramInt)
  {
    if (this.maneuver == 44) return;

    if ((paramInt != 44) && ((this.maneuver == 26) || (this.maneuver == 69) || (this.maneuver == 66) || (this.maneuver == 46))) {
      return;
    }
    int i = this.maneuver;
    this.maneuver = paramInt;
    if (i != this.maneuver)
      set_flags();
  }

  public void pop()
  {
    if (this.maneuver == 44) return;

    if ((this.program[0] != 44) && ((this.maneuver == 26) || (this.maneuver == 69) || (this.maneuver == 66) || (this.maneuver == 46))) {
      return;
    }
    int i = this.maneuver;
    this.maneuver = this.program[0];
    for (int j = 0; j < this.program.length - 1; j++) this.program[j] = this.program[(j + 1)];
    this.program[(this.program.length - 1)] = 0;
    if (i != this.maneuver)
      set_flags();
  }

  public void unblock()
  {
    this.maneuver = 0;
  }

  public void safe_set_maneuver(int paramInt) {
    this.dont_change_subm = true;
    set_maneuver(paramInt);
    this.dont_change_subm = true;
  }

  public void safe_pop() {
    this.dont_change_subm = true;
    pop();
    this.dont_change_subm = true;
  }

  public void clear_stack() {
    for (int i = 0; i < this.program.length; i++) this.program[i] = 0;
  }

  public void push(int paramInt)
  {
    for (int i = this.program.length - 2; i >= 0; i--) this.program[(i + 1)] = this.program[i];
    this.program[0] = paramInt;
  }

  public void push() {
    push(this.maneuver);
  }
  public void accurate_set_task_maneuver(int paramInt1, int paramInt2) {
    if ((this.maneuver == 44) || (this.maneuver == 26) || (this.maneuver == 69) || (this.maneuver == 64))
    {
      return;
    }
    set_task(paramInt1);
    if (this.maneuver != paramInt2) {
      clear_stack();
      set_maneuver(paramInt2);
    }
  }

  public void accurate_set_FOLLOW() {
    if ((this.maneuver == 44) || (this.maneuver == 26) || (this.maneuver == 69) || (this.maneuver == 64))
    {
      return;
    }
    set_task(2);
    if ((this.maneuver != 24) && (this.maneuver != 53)) {
      clear_stack();
      set_maneuver(24);
    }
  }

  public void setBusy(boolean paramBoolean) {
    this.bBusy = paramBoolean; } 
  public boolean isBusy() { return this.bBusy; } 
  public void setSpeedMode(int paramInt) { this.speedMode = paramInt; }

  private boolean isStallable()
  {
    if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik)) return false;
    return !(this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeTransport);
  }

  private void resetControls()
  {
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F);

    this.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl = 0.0F;
  }

  private void set_flags() {
    if (World.cur().isDebugFM()) printDebugFM();
    this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
    this.mn_time = 0.0F;
    this.jdField_minElevCoeff_of_type_Float = 4.0F;

    if (!this.dont_change_subm) {
      setSpeedMode(3);
      this.first = true;
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }
    this.dont_change_subm = false;

    if ((this.maneuver != 48) && (this.maneuver != 0) && (this.maneuver != 26) && (this.maneuver != 64) && (this.maneuver != 44))
    {
      resetControls();
    }
    if ((this.maneuver == 20) || (this.maneuver == 25) || (this.maneuver == 66) || (this.maneuver == 1) || (this.maneuver == 26) || (this.maneuver == 69) || (this.maneuver == 44) || (this.maneuver == 49) || (this.maneuver == 43) || (this.maneuver == 50) || (this.maneuver == 51) || (this.maneuver == 46) || (this.maneuver == 2) || (this.maneuver == 10) || (this.maneuver == 57) || (this.maneuver == 64))
    {
      setCheckGround(false);
    }
    else setCheckGround(true);

    if ((this.maneuver == 24) || (this.maneuver == 53) || (this.maneuver == 68) || (this.maneuver == 59) || (this.maneuver == 8) || (this.maneuver == 55) || (this.maneuver == 27) || (this.maneuver == 62) || (this.maneuver == 63) || (this.maneuver == 25) || (this.maneuver == 66) || (this.maneuver == 43) || (this.maneuver == 50) || (this.maneuver == 65) || (this.maneuver == 44) || (this.maneuver == 21) || (this.maneuver == 64) || (this.maneuver == 69))
    {
      this.frequentControl = true;
    }
    else this.frequentControl = false;

    if ((this.maneuver == 25) || (this.maneuver == 26) || (this.maneuver == 69))
    {
      turnOnChristmasTree(true);
    }
    else turnOnChristmasTree(false);

    if (this.maneuver == 25)
    {
      turnOnCloudShine(true);
    }
    else turnOnCloudShine(false);

    if ((this.maneuver == 60) || (this.maneuver == 61) || (this.maneuver == 66) || (this.maneuver == 1) || (this.maneuver == 24) || (this.maneuver == 26) || (this.maneuver == 69) || (this.maneuver == 64) || (this.maneuver == 44))
    {
      this.checkStrike = false;
    }
    else this.checkStrike = true;

    if ((this.maneuver == 44) || (this.maneuver == 1) || (this.maneuver == 48) || (this.maneuver == 0) || (this.maneuver == 26) || (this.maneuver == 69) || (this.maneuver == 64) || (this.maneuver == 43) || (this.maneuver == 50) || (this.maneuver == 51) || (this.maneuver == 52) || (this.maneuver == 47))
    {
      this.stallable = false;
    }
    else this.stallable = true;

    if ((this.maneuver == 44) || (this.maneuver == 1) || (this.maneuver == 26) || (this.maneuver == 69) || (this.maneuver == 64) || (this.maneuver == 2) || (this.maneuver == 57) || (this.maneuver == 60) || (this.maneuver == 61) || (this.maneuver == 43) || (this.maneuver == 50) || (this.maneuver == 51) || (this.maneuver == 52) || (this.maneuver == 47) || (this.maneuver == 29))
    {
      setBusy(true);
    }
  }

  public void setCheckStrike(boolean paramBoolean) {
    this.checkStrike = paramBoolean;
  }

  private void setCheckGround(boolean paramBoolean) {
    this.checkGround = paramBoolean;
  }

  public Maneuver(String paramString)
  {
    super(paramString);
    this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage = new AutopilotAI(this);
  }

  public void decDangerAggressiveness() {
    this.dangerAggressiveness -= 0.01F;
    if (this.dangerAggressiveness < 0.0F) this.dangerAggressiveness = 0.0F;
    this.oldDanCoeff -= 0.005F;
    if (this.oldDanCoeff < 0.0F) this.oldDanCoeff = 0.0F;
  }

  public void incDangerAggressiveness(int paramInt, float paramFloat1, float paramFloat2, FlightModel paramFlightModel)
  {
    paramFloat1 -= 0.7F;
    if (paramFloat1 < 0.0F) paramFloat1 = 0.0F;
    paramFloat2 = 1000.0F - paramFloat2;
    if (paramFloat2 < 0.0F) paramFloat2 = 0.0F;
    double d1 = (paramFlightModel.jdField_Energy_of_type_Float - this.jdField_Energy_of_type_Float) * 0.1019D;
    double d2 = 1.0D + d1 * 0.00125D;
    if (d2 > 1.2D) d2 = 1.2D;
    if (d2 < 0.8D) d2 = 0.8D;

    float f = (float)d2 * (7.E-005F * paramFloat1 * paramFloat2);
    if ((this.danger == null) || (f > this.oldDanCoeff)) {
      this.danger = paramFlightModel;
    }
    this.oldDanCoeff = f;
    this.dangerAggressiveness += f * paramInt;
    if (this.dangerAggressiveness > 1.0F) this.dangerAggressiveness = 1.0F; 
  }

  public float getDangerAggressiveness()
  {
    return this.dangerAggressiveness;
  }

  public float getMaxHeightSpeed(float paramFloat) {
    if (paramFloat < this.jdField_HofVmax_of_type_Float) {
      return this.jdField_Vmax_of_type_Float + (this.jdField_VmaxH_of_type_Float - this.jdField_Vmax_of_type_Float) * (paramFloat / this.jdField_HofVmax_of_type_Float);
    }
    float f = (paramFloat - this.jdField_HofVmax_of_type_Float) / this.jdField_HofVmax_of_type_Float;
    f = 1.0F - 1.5F * f;
    if (f < 0.0F) f = 0.0F;
    return this.jdField_VmaxH_of_type_Float * f;
  }

  public float getMinHeightTurn(float paramFloat)
  {
    return this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.T_turn;
  }

  public void setShotAtFriend(float paramFloat) {
    this.distToFriend = paramFloat;
    this.shotAtFriend = 30;
  }

  public boolean hasCourseWeaponBullets() {
    if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof KI_46_OTSUHEI)) {
      return (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0].countBullets() != 0);
    }
    if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof AR_234)) {
      return false;
    }
    return ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0].countBullets() != 0)) || ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0].countBullets() != 0));
  }

  public boolean hasBombs()
  {
    if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) {
      for (int i = 0; i < this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length; i++) {
        if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][i] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][i].countBullets() != 0)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean hasRockets() {
    if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2] != null) {
      for (int i = 0; i < this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2].length; i++) {
        if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2][i] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2][i].countBullets() != 0)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean canAttack() {
    return ((!this.Group.isWingman(this.Group.numInGroup((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor))) || (this.aggressiveWingman)) && (isOk()) && (hasCourseWeaponBullets());
  }

  public void update(float paramFloat)
  {
    if (Config.isUSE_RENDER()) headTurn(paramFloat);
    if (showFM) OutCT(20);

    super.update(paramFloat);
    this.jdField_callSuperUpdate_of_type_Boolean = true;
    decDangerAggressiveness();
    if (this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double < -20.0D) ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).postEndAction(0.0D, this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 4, null);

    this.LandHQ = (float)Engine.land().HQ_Air(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double);
    Po.set(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    Po.scale(3.0D);
    Po.add(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    this.LandHQ = (float)Math.max(this.LandHQ, Engine.land().HQ_Air(Po.jdField_x_of_type_Double, Po.jdField_y_of_type_Double));
    this.Alt = ((float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - this.LandHQ);
    this.jdField_indSpeed_of_type_Float = (getSpeed() * (float)Math.sqrt(this.Density / 1.225F));
    if ((!this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) && (isOk()) && (this.Alt > 8.0F)) {
      if (this.jdField_AOA_of_type_Float > this.jdField_AOA_Crit_of_type_Float - 2.0F) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, 0.05F * (this.jdField_AOA_Crit_of_type_Float - 2.0F - this.jdField_AOA_of_type_Float), 0.0F);
      if (this.jdField_AOA_of_type_Float < -5.0F) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, 0.05F * (-5.0F - this.jdField_AOA_of_type_Float), 0.0F);
    }
    if ((!this.frequentControl) && ((Time.tickCounter() + this.jdField_actor_of_type_ComMaddoxIl2EngineActor.hashCode()) % 4 != 0)) return;

    turnOffTheWeapon();
    this.maxG = (3.5F + this.jdField_Skill_of_type_Int * 0.5F);
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.wrap();

    if ((this.mn_time > 10.0F) && (this.jdField_AOA_of_type_Float > this.jdField_AOA_Crit_of_type_Float + 5.0F) && (isStallable()) && (this.stallable)) safe_set_maneuver(20);
    float f1;
    float f2;
    float f3;
    Object localObject;
    Maneuver localManeuver;
    float f4;
    float f5;
    float f10;
    float f13;
    double d5;
    float f12;
    float f7;
    float f9;
    switch (this.maneuver)
    {
    case 0:
      this.target_ground = null;
      break;
    case 1:
      this.dryFriction = 8.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 1.0F;
      break;
    case 48:
      if (this.mn_time >= 1.0F) pop(); break;
    case 44:
      if ((this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) && (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() < 0.300000011920929D) && (World.Rnd().nextInt(0, 99) < 4)) {
        if (this.Group != null) this.Group.delAircraft((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeGlider)) || ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeSailPlane))) break label23427; World.cur(); if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor != World.getPlayerAircraft()) {
          if (Airport.distToNearestAirport(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d) > 900.0D)
            ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).postEndAction(60.0D, this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 3, null);
          else
            MsgDestroy.Post(Time.current() + 30000L, this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        }
      }
      if (this.first) {
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = World.Rnd().nextFloat(-0.07F, 0.4F);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = World.Rnd().nextFloat(-0.15F, 0.15F); } break;
    case 7:
      wingman(false);
      setSpeedMode(9);
      if (getW().jdField_x_of_type_Double <= 0.0D) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = -1.0F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 1.0F;
      }
      else {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = 1.0F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = -1.0F;
      }
      f1 = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();

      if ((f1 > -90.0F) && (f1 < 90.0F)) {
        f2 = 0.01111F * (90.0F - Math.abs(f1));
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (-0.08F * f2 * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - 3.0F));
      }
      else {
        f2 = 0.01111F * (Math.abs(f1) - 90.0F);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (0.08F * f2 * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - 3.0F));
      }

      if (getSpeed() < 1.7F * this.jdField_Vmin_of_type_Float)
        pop();
      if (this.mn_time > 2.2F)
        pop();
      if ((this.danger != null) && ((this.danger instanceof Pilot)) && (((Maneuver)this.danger).target == this) && (this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.distance(this.danger.jdField_Loc_of_type_ComMaddoxJGPPoint3d) > 400.0D))
      {
        pop(); } break;
    case 8:
      if ((this.first) && 
        (!isCapableOfACM())) {
        if (this.jdField_Skill_of_type_Int > 0) pop();
        if (this.jdField_Skill_of_type_Int > 1) setReadyToReturn(true);
      }

      setSpeedMode(6);
      tmpOr.setYPR(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw(), 0.0F, 0.0F);
      if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() > 0.0F) Ve.set(100.0D, -6.0D, 10.0D); else
        Ve.set(100.0D, 6.0D, 10.0D);
      tmpOr.transform(Ve);
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      farTurnToDirection();
      if (((this.Alt > 250.0F) && (this.mn_time > 8.0F)) || (this.mn_time > 120.0F)) pop(); break;
    case 55:
      if ((this.first) && 
        (!isCapableOfACM())) {
        if (this.jdField_Skill_of_type_Int > 0) pop();
        if (this.jdField_Skill_of_type_Int > 1) setReadyToReturn(true);
      }

      setSpeedMode(6);
      tmpOr.setYPR(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw(), 0.0F, 0.0F);
      if ((this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel != null) && (Actor.isAlive(this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor)) && (this.mn_time < 2.5F)) {
        if (this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() > 0.0F) Ve.set(100.0D, -6.0D, 10.0D); else
          Ve.set(100.0D, 6.0D, 10.0D);
      }
      else if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() > 0.0F) Ve.set(100.0D, -6.0D, 10.0D); else {
        Ve.set(100.0D, 6.0D, 10.0D);
      }
      tmpOr.transform(Ve);
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      farTurnToDirection();
      if (((this.Alt > 250.0F) && (this.mn_time > 8.0F)) || (this.mn_time > 120.0F)) pop(); break;
    case 45:
      setSpeedMode(7);
      this.smConstPower = 0.8F;
      this.dA = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();
      if (this.dA > 0.0F) this.dA -= 25.0F; else this.dA -= 335.0F;
      if (this.dA < -180.0F) this.dA += 360.0F;
      this.dA = (-0.01F * this.dA);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = this.dA;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (-0.04F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - 1.0F) + 0.002F * (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().z() - (float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double + 250.0F));
      if (this.mn_time > 60.0F) {
        this.mn_time = 0.0F;
        pop(); } break;
    case 54:
      if (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() <= this.jdField_VminFLAPS_of_type_Float * 2.0F) break; this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.scale(0.9950000047683716D);
    case 9:
      if ((this.first) && 
        (!isCapableOfACM())) {
        if (this.jdField_Skill_of_type_Int > 0) pop();
        if (this.jdField_Skill_of_type_Int > 1) setReadyToReturn(true);
      }

      setSpeedMode(4);
      this.smConstSpeed = 100.0F;
      this.dA = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();
      if (this.dA > 0.0F) this.dA -= 50.0F; else this.dA -= 310.0F;
      if (this.dA < -180.0F) this.dA += 360.0F;
      this.dA = (-0.01F * this.dA);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = this.dA;

      this.dA = ((-10.0F - this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage()) * 0.05F);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = this.dA;
      if (getOverload() < 1.0D / Math.abs(Math.cos(this.dA)))
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 1.0F * paramFloat;
      else {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 1.0F * paramFloat;
      }

      if (this.Alt < 100.0F) {
        push(3);
        pop();
      }
      if (this.mn_time > 5.0F) pop(); break;
    case 14:
      setSpeedMode(6);
      if (this.first) this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAltitude(true);
      this.dA = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();
      if (getOverload() < 1.0D / Math.abs(Math.cos(this.dA)))
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 1.0F * paramFloat;
      else {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 1.0F * paramFloat;
      }
      if (this.dA > 0.0F) this.dA -= 50.0F; else this.dA -= 310.0F;
      if (this.dA < -180.0F) this.dA += 360.0F;
      this.dA = (-0.01F * this.dA);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = this.dA;
      if (this.mn_time > 5.0F) pop(); break;
    case 4:
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (getW().jdField_x_of_type_Double <= 0.0D ? -1.0F : 1.0F);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (0.1F * (float)Math.cos(FMMath.DEG2RAD(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren())));
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
      if (getSpeedKMH() < 220.0F) {
        push(3);
        pop();
      }
      if (this.mn_time > 7.0F) pop(); break;
    case 2:
      this.jdField_minElevCoeff_of_type_Float = 20.0F;
      if (this.first) {
        wingman(false);
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
        if ((World.Rnd().nextInt(0, 99) < 10) && (this.Alt < 80.0F)) Voice.speakPullUp((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      }
      setSpeedMode(9);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 0.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl = 0.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * (this.dA = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()));
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (1.0F + 0.3F * (float)getW().jdField_y_of_type_Double);

      if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl < 0.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.0F;
      if (this.jdField_AOA_of_type_Float > 15.0F) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, (15.0F - this.jdField_AOA_of_type_Float) * 0.5F * paramFloat, 0.0F);
      if ((this.Alt < 10.0F) && (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < 0.0D)) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.8999999761581421D;
      if (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 1.0D) {
        if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeGlider)) push(49); else
          push(57);
        pop();
      }
      if (this.mn_time > 25.0F) {
        push(49);
        pop(); } break;
    case 60:
      setSpeedMode(9);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 1.0F;
      if (this.mn_time > 0.15F) pop(); break;
    case 61:
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = -0.4F;
      if (this.mn_time > 0.2F) pop(); break;
    case 3:
      if ((this.first) && 
        (this.program[0] == 49)) pop();

      setSpeedMode(6);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      this.dA = ((getSpeedKMH() - 180.0F - this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() * 10.0F - getVertSpeed() * 5.0F) * 0.004F);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = this.dA;
      if ((getSpeed() > this.jdField_Vmin_of_type_Float * 1.2F) && (getVertSpeed() > 0.0F)) {
        setSpeedMode(7);
        this.smConstPower = 0.7F;
        pop(); } break;
    case 10:
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
      setSpeedMode(6);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      this.dA = this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl;
      if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > 15.0F) this.dA -= (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - 15.0F) * 0.1F * paramFloat;
      else
        this.dA = (((float)this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() / this.jdField_VminFLAPS_of_type_Float * 140.0F - 50.0F - this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() * 20.0F) * 0.004F);
      this.dA = (float)(this.dA + 0.5D * getW().jdField_y_of_type_Double);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = this.dA;
      if (((this.Alt > 250.0F) && (this.mn_time > 6.0F)) || (this.mn_time > 20.0F)) pop(); break;
    case 57:
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
      setSpeedMode(9);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      this.dA = this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl;
      if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > 25.0F) this.dA -= (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - 25.0F) * 0.1F * paramFloat; else
        this.dA = (((float)this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() / this.jdField_VminFLAPS_of_type_Float * 140.0F - 50.0F - this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() * 20.0F) * 0.004F);
      this.dA = (float)(this.dA + 0.5D * getW().jdField_y_of_type_Double);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = this.dA;
      if ((this.Alt > 150.0F) || ((this.Alt > 100.0F) && (this.mn_time > 2.0F)) || (this.mn_time > 3.0F)) pop(); break;
    case 11:
      setSpeedMode(8);
      if (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) < 90.0F) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
        if ((this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D) || (getSpeedKMH() < 270.0F)) this.dA = -0.04F; else
          this.dA = 0.04F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl * 0.9F + this.dA * 0.1F);
      } else {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (0.04F * (180.0F - Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren())));
        if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > -25.0F) this.dA = 0.33F;
        else if ((this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D) || (getSpeedKMH() < 270.0F)) this.dA = 0.04F; else
          this.dA = -0.04F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl * 0.9F + this.dA * 0.1F);
      }
      if ((this.Alt < 120.0F) || (this.mn_time > 4.0F)) pop(); break;
    case 12:
      setSpeedMode(4);
      this.smConstSpeed = 80.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      if (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() > this.jdField_VminFLAPS_of_type_Float * 2.0F) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.scale(0.9950000047683716D);
      this.dA = (-((float)this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double / (Math.abs(getSpeed()) + 1.0F) + 0.5F));
      if (this.dA < -0.1F) this.dA = -0.1F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl * 0.9F + this.dA * 0.1F + 0.3F * (float)getW().jdField_y_of_type_Double);
      if ((this.mn_time > 5.0F) || (this.Alt < 200.0F)) pop(); break;
    case 13:
      if (this.first) {
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
        this.submaneuver = ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter) ? 0 : 2);
      }
      switch (this.submaneuver) {
      case 0:
        this.dA = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - 180.0F);
        if (this.dA < -180.0F) this.dA += 360.0F;
        this.dA = (-0.04F * this.dA);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = this.dA;
        if ((this.mn_time <= 3.0F) && (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) <= 175.0F - 5.0F * this.jdField_Skill_of_type_Int)) break; this.submaneuver += 1; break;
      case 1:
        this.dA = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - 180.0F);
        if (this.dA < -180.0F) this.dA += 360.0F;
        this.dA = (-0.04F * this.dA);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = this.dA;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
        setSpeedMode(8);

        if ((this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > -45.0F) && (getOverload() < this.maxG))
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 1.5F * paramFloat;
        else {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.5F * paramFloat;
        }
        if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < -44.0F) this.submaneuver += 1;
        if ((this.Alt >= -5.0D * this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double) && (this.mn_time <= 5.0F)) break; pop(); break;
      case 2:
        setSpeedMode(8);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
        this.dA = (-((float)this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double / (Math.abs(getSpeed()) + 1.0F) + 0.707F));
        if (this.dA < -0.75F) this.dA = -0.75F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl * 0.9F + this.dA * 0.1F + 0.5F * (float)getW().jdField_y_of_type_Double);
        if ((this.Alt >= -5.0D * this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double) && (this.mn_time <= 5.0F)) break; pop();
      }

      if (this.Alt < 200.0F) pop(); break;
    case 5:
      this.dA = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();
      if (this.dA < 0.0F) this.dA -= 270.0F; else this.dA -= 90.0F;
      if (this.dA < -180.0F) this.dA += 360.0F;
      this.dA = (-0.02F * this.dA);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = this.dA;
      if ((this.mn_time > 5.0F) || (Math.abs(Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) - 90.0F) < 1.0F)) pop(); break;
    case 6:
      this.dA = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - 180.0F);
      if (this.dA < -180.0F) this.dA += 360.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (float)(-0.04F * this.dA - 0.5D * getW().jdField_x_of_type_Double);
      if ((this.mn_time > 4.0F) || (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) > 178.0F)) {
        this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double = 0.0D;
        pop(); } break;
    case 35:
      if (this.first) {
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
        this.direction = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();
        this.submaneuver = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() > 0.0F ? 1 : -1);
        this.tmpi = 0;
        setSpeedMode(9);
      }
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (1.0F * this.submaneuver);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (0.0F * this.submaneuver);
      f1 = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();
      if ((f1 > -90.0F) && (f1 < 90.0F)) {
        f2 = 0.01111F * (90.0F - Math.abs(f1));
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (-0.08F * f2 * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - 3.0F));
      }
      else {
        f2 = 0.01111F * (90.0F - Math.abs(f1));
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (0.08F * f2 * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - 3.0F));
      }
      if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() * this.direction < 0.0F)
        this.tmpi = 1;
      if ((this.tmpi != 1) || (this.submaneuver > 0 ? this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() <= this.direction : this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() >= this.direction)) { if (this.mn_time <= 17.5F);
      } else
        pop(); break;
    case 22:
      setSpeedMode(9);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (-0.04F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() + 5.0F));
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
      if ((getSpeed() > this.jdField_Vmax_of_type_Float) || (this.mn_time > 30.0F)) pop(); break;
    case 67:
      this.jdField_minElevCoeff_of_type_Float = 18.0F;
      if (this.first) {
        this.sub_Man_Count = 0;
        setSpeedMode(9);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 1.1F;
      }
      if (this.danger != null) {
        f3 = 700.0F - (float)this.danger.jdField_Loc_of_type_ComMaddoxJGPPoint3d.distance(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        if (f3 < 0.0F) f3 = 0.0F;
        f3 *= 0.00143F;

        f1 = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();
        if ((this.sub_Man_Count == 0) || (this.first)) {
          if (this.raAilShift < 0.0F)
            this.raAilShift = (f3 * World.Rnd().nextFloat(0.6F, 1.0F));
          else {
            this.raAilShift = (f3 * World.Rnd().nextFloat(-1.0F, -0.6F));
          }
          this.raRudShift = (f3 * World.Rnd().nextFloat(-0.5F, 0.5F));
          this.raElevShift = (f3 * World.Rnd().nextFloat(-0.8F, 0.8F));
        }

        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (0.9F * this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl + 0.1F * this.raAilShift);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (0.95F * this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl + 0.05F * this.raRudShift);
        if ((f1 > -90.0F) && (f1 < 90.0F))
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (-0.04F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() + 5.0F));
        else {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (0.05F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() + 5.0F));
        }
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.1F * this.raElevShift;

        this.sub_Man_Count += 1;
        if ((this.sub_Man_Count >= 80.0F * (1.5F - f3)) && (f1 > -70.0F) && (f1 < 70.0F))
          this.sub_Man_Count = 0;
        if (this.mn_time > 30.0F)
          pop();
      } else {
        pop();
      }
      break;
    case 16:
      if (this.first) {
        if (!isCapableOfACM()) pop();
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
        setSpeedMode(6);
        if (getSpeed() < 0.75F * this.jdField_Vmax_of_type_Float) {
          push();
          push(22);
          pop();
        }
        else {
          this.submaneuver = 0;
        }
      } else {
        this.maxAOA = (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D ? 7.0F : 12.0F);
        switch (this.submaneuver) {
        case 0:
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.05F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          if (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) >= 2.0F) break; this.submaneuver += 1; break;
        case 1:
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          this.dA = 0.5F;
          if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.4F * paramFloat;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.4F * paramFloat;
          }
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > 80.0F) this.submaneuver += 1;
          if (getSpeed() >= this.jdField_Vmin_of_type_Float * 1.5F) break;
          pop(); break;
        case 2:
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS() * (getSpeed() > 300.0F ? 1.0F : 0.0F));
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.4F * paramFloat;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.4F * paramFloat;
          }
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() >= 0.0F) break; this.submaneuver += 1; break;
        case 3:
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS() * (getSpeed() > 300.0F ? 1.0F : 0.0F));
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.2F * paramFloat;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.2F * paramFloat;
          }
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() >= -60.0F) break; this.submaneuver += 1; break;
        case 4:
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > -45.0F) {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
            this.maxAOA = 3.5F;
          }
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          this.dA = 0.5F;
          if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 1.0F * paramFloat;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.4F * paramFloat;
          }
          if ((this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() <= 3.0F) && (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double <= 0.0D)) break; pop();
        }
      }
      break;
    case 17:
      if (this.first) {
        if (this.Alt < 1000.0F) {
          pop();
        }
        else if (getSpeed() < this.jdField_Vmin_of_type_Float * 1.2F) {
          push();
          push(22);
          pop();
        }
        else {
          push(18);
          push(19);
          pop();
        }
      }
      else pop();
      break;
    case 19:
      if (this.first) {
        if (this.Alt < 1000.0F) {
          pop();
        }
        else {
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
          this.submaneuver = 0;
        }
      } else {
        this.maxAOA = (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D ? 7.0F : 12.0F);
        switch (this.submaneuver) {
        case 0:
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.05F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (0.04F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() > 0.0F ? 180.0F - this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() : -180.0F + this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()));
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          if (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) <= 178.0F) break; this.submaneuver += 1; break;
        case 1:
          setSpeedMode(7);
          this.smConstPower = 0.5F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = 0.0F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.2F * paramFloat;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 1.2F * paramFloat;
          }
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() >= -60.0F) break; this.submaneuver += 1; break;
        case 2:
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > -45.0F) {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
            setSpeedMode(9);
            this.maxAOA = 7.0F;
          }
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          this.dA = 0.5F;
          if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.8F * paramFloat;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.4F * paramFloat;
          }
          if ((this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() <= this.jdField_AOA_of_type_Float - 1.0F) && (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double <= 0.0D)) break; pop();
        }
      }
      break;
    case 18:
      if (this.first) {
        if (!isCapableOfACM()) pop();
        if (getSpeed() < this.jdField_Vmax_of_type_Float * 0.75F) {
          push();
          push(22);
          pop();
        }
        else {
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
          this.submaneuver = 0;
          setSpeedMode(6);
        }
      } else {
        this.maxAOA = (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D ? 7.0F : 12.0F);
        switch (this.submaneuver)
        {
        case 0:
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          if (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) >= 2.0F) break; this.submaneuver += 1; break;
        case 1:
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.4F * paramFloat;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.8F * paramFloat;
          }
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > 80.0F) this.submaneuver += 1;
          if (getSpeed() >= this.jdField_Vmin_of_type_Float * 1.5F) break;
          pop(); break;
        case 2:
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS() * (getSpeed() > 300.0F ? 1.0F : 0.0F));
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.4F * paramFloat;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.4F * paramFloat;
          }
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() >= 12.0F) break; this.submaneuver += 1; break;
        case 3:
          if (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) < 60.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.05F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          if (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) >= 30.0F) break; this.submaneuver += 1; break;
        case 4:
          pop();
        }
      }
      break;
    case 15:
      if ((this.first) && (getSpeed() < 0.35F * (this.jdField_Vmin_of_type_Float + this.jdField_Vmax_of_type_Float))) { pop(); } else {
        push(8); pop();
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
        if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() < 0.0F)
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() + 30.0F));
        else {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - 30.0F));
        }

        if (this.mn_time > 7.5F) pop(); 
      }break;
    case 20:
      if (this.first) {
        wingman(false);
        setSpeedMode(6);
      }
      if (!isCapableOfBMP()) {
        setReadyToDie(true);
        pop();
      }
      if (getW().jdField_z_of_type_Double > 0.0D)
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 1.0F;
      else {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = -1.0F;
      }
      if (this.jdField_AOA_of_type_Float > this.jdField_AOA_Crit_of_type_Float - 4.0F) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, 0.01F * (this.jdField_AOA_Crit_of_type_Float - 4.0F - this.jdField_AOA_of_type_Float), 0.0F);
      if (this.jdField_AOA_of_type_Float < -5.0F) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, 0.01F * (-5.0F - this.jdField_AOA_of_type_Float), 0.0F);

      if (this.jdField_AOA_of_type_Float < this.jdField_AOA_Crit_of_type_Float - 1.0F) pop();
      if ((this.mn_time > 14.0F - this.jdField_Skill_of_type_Int * 4.0F) || (this.Alt < 50.0F)) pop(); break;
    case 21:
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setWayPoint(true);
      if (this.mn_time > 300.0F) pop();

      if ((isTick(256, 0)) && (!this.jdField_actor_of_type_ComMaddoxIl2EngineActor.isTaskComplete()) && (((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLast()) && (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.getWayPointDistance() < 1500.0F)) || (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding())))
      {
        World.onTaskComplete(this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      }
      if ((((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).aircIndex() == 0) && (!isReadyToReturn())) { World.cur(); if (World.getPlayerAircraft() != null) { World.cur(); if (((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).getRegiment() == World.getPlayerAircraft().getRegiment())
          {
            f3 = 1.0E+012F;
            int i;
            if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 3) {
              f3 = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.getWayPointDistance();
            } else {
              i = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur();
              this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
              if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 3)
                f3 = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.getWayPointDistance();
              this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(i);
            }
            if ((this.Speak5minutes == 0) && (22000.0F < f3) && (f3 < 30000.0F)) {
              Voice.speak5minutes((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
              this.Speak5minutes = 1;
            }
            if ((this.Speak1minute == 0) && (f3 < 10000.0F)) {
              Voice.speak1minute((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
              this.Speak1minute = 1; this.Speak5minutes = 1;
            }

            if (((this.WeWereInGAttack) || (this.WeWereInAttack)) && (this.mn_time > 5.0F)) {
              if (!this.SpeakMissionAccomplished) {
                i = 0;
                int j = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur();
                if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 3) break label23427; if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTarget() != null) break label23427; do
                {
                  this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
                  if ((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 3) || (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTarget() != null))
                    i = 1;
                }
                while (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur() < this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.size() - 1);

                this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(j);
                if (i == 0) {
                  Voice.speakMissionAccomplished((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
                  this.SpeakMissionAccomplished = true;
                }
              }
              if (!this.SpeakMissionAccomplished) {
                this.Speak5minutes = 0; this.Speak1minute = 0; this.SpeakBeginGattack = 0;
              }
              this.WeWereInGAttack = false;
              this.WeWereInAttack = false;
            }
          } }
      }
      if ((((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeBomber)) || ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeTransport))) && (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr() != null) && (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 3) && ((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getTarget() == null) || ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof Scheme4))))
      {
        double d1 = this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - World.land().HQ(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double);
        if (d1 < 0.0D) d1 = 0.0D;
        if ((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.getWayPointDistance() < getSpeed() * Math.sqrt(d1 * 0.2038699984550476D)) && 
          (!this.bombsOut)) {
          if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].countBullets() != 0) && (!(this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] instanceof BombGunPara)))
          {
            Voice.airSpeaks((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 85, 1);
          }
          this.bombsOut = true;
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action = 0;
          if (this.Group != null) this.Group.dropBombs();
        }
      }

      setSpeedMode(3);

      if ((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLandingOnShip()) && 
        (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding())) {
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.landingAirport.rebuildLandWay(this);
        if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasCockpitDoorControl) this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 1); 
      }break;
    case 23:
      if (this.first) {
        wingman(false);
        if (getSpeedKMH() < this.jdField_Vmin_of_type_Float * 1.25F) {
          push();
          push(22);
          pop();
          break label23427;
        }
      }
      setSpeedMode(6);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
      if ((this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < 70.0F) && (getOverload() < this.maxG) && (this.jdField_AOA_of_type_Float < 14.0F)) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.5F * paramFloat; else
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.5F * paramFloat;
      if (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < 1.0D) pop(); break;
    case 24:
      if ((this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel == null) || (!Actor.isAlive(this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor)) || (!((Maneuver)this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel).isOk()) || ((((Maneuver)this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel).isBusy()) && ((!(this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel instanceof RealFlightModel)) || (!((RealFlightModel)this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel).isRealMode()))))
      {
        set_maneuver(0);
      }
      else {
        if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeGlider)) {
          if ((this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action != 0) && (this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action != 1))
            set_maneuver(49);
        }
        else if ((this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.getSpeed() < 30.0F) || (this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) < 50.0D)) {
          this.airClient = this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel;
          push();
          push(59);
          pop();
          break label23427;
        }
        if (!this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding()) {
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur());
          if (this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != this) {
            if ((!this.bombsOut) && (((Maneuver)this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel).bombsOut)) {
              this.bombsOut = true;
              localObject = this;
              while (((Maneuver)localObject).jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != null) {
                localObject = (Maneuver)((Maneuver)localObject).jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel;
                ((Maneuver)localObject).bombsOut = true;
              }
            }
            if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl != this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl) {
              this.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl;
              localObject = (Pilot)this;
              while (((Pilot)localObject).jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != null) {
                localObject = (Pilot)((Pilot)localObject).jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel;
                ((Pilot)localObject).jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = this.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl;
              }
            }
          }
        }
        else {
          if (this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != this) {
            push(8);
            push(8);
            push(World.Rnd().nextBoolean() ? 8 : 48);
            push(World.Rnd().nextBoolean() ? 8 : 48);
            pop();
          }
          this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = null; pop(); break label23427;
        }

        this.airClient = this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel;
        tmpOr.setAT0(this.airClient.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        tmpOr.increment(0.0F, this.airClient.getAOA(), 0.0F);

        Ve.set(this.followOffset);
        Ve.jdField_x_of_type_Double -= 300.0D;

        tmpV3f.sub(this.followTargShift, this.followCurShift);
        if (tmpV3f.lengthSquared() < 0.5D) {
          this.followTargShift.set(World.cur().rnd.nextFloat(-0.0F, 10.0F), World.cur().rnd.nextFloat(-5.0F, 5.0F), World.cur().rnd.nextFloat(-3.5F, 3.5F));
        }

        tmpV3f.normalize();
        tmpV3f.scale(2.0F * paramFloat);
        this.followCurShift.add(tmpV3f);
        Ve.add(this.followCurShift);

        tmpOr.transform(Ve, Po);
        Po.scale(-1.0D);
        Po.add(this.airClient.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        Ve.sub(Po, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
        this.dist = (float)Ve.length();
        if (this.followOffset.jdField_x_of_type_Double > 600.0D) {
          Ve.set(this.followOffset);
          Ve.jdField_x_of_type_Double -= 0.5D * this.followOffset.jdField_x_of_type_Double;
          tmpOr.transform(Ve, Po);
          Po.scale(-1.0D);
          Po.add(this.airClient.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
          Ve.sub(Po, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
        }

        Ve.normalize();
        if (this.dist > 600.0D + Ve.jdField_x_of_type_Double * 400.0D) {
          push();
          push(53);
          pop();
        }
        else
        {
          if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeTransport)) && (this.jdField_Vmax_of_type_Float < 70.0D))
            farTurnToDirection(6.2F);
          else {
            attackTurnToDirection(this.dist, paramFloat, 10.0F);
          }

          setSpeedMode(10);

          this.tailForStaying = this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel;
          this.tailOffset.set(this.followOffset);
          this.tailOffset.scale(-1.0D);
        }
      }
      break;
    case 65:
      if ((!(this instanceof RealFlightModel)) || (!((RealFlightModel)this).isRealMode())) {
        this.bombsOut = true;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.dropFuelTanks();
      }
      if ((this.airClient == null) || (!Actor.isAlive(this.airClient.jdField_actor_of_type_ComMaddoxIl2EngineActor)) || (!isOk())) {
        set_maneuver(0);
      }
      else {
        localObject = (Maneuver)this.airClient;
        localManeuver = (Maneuver)((Maneuver)this.airClient).danger;
        if ((((Maneuver)localObject).getDangerAggressiveness() >= 1.0F - this.jdField_Skill_of_type_Int * 0.2F) && (localManeuver != null) && (hasCourseWeaponBullets())) {
          Voice.speakCheckYour6((Aircraft)((Maneuver)localObject).jdField_actor_of_type_ComMaddoxIl2EngineActor, (Aircraft)localManeuver.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          Voice.speakHelpFromAir((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 1);
          set_task(6);
          this.target = localManeuver;
          set_maneuver(27);
        }

        Ve.sub(this.airClient.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
        this.dist = (float)Ve.length();
        Ve.normalize();
        attackTurnToDirection(this.dist, paramFloat, 10.0F + this.jdField_Skill_of_type_Int * 8.0F);
        if (this.Alt > 50.0F) setSpeedMode(1); else
          setSpeedMode(6);
        this.tailForStaying = this.airClient;
        this.tailOffset.set(this.followOffset);
        this.tailOffset.scale(-1.0D);

        if ((this.dist > 600.0D + Ve.jdField_x_of_type_Double * 400.0D) && (get_maneuver() != 27)) {
          push();
          push(53);
          pop();
        }
      }break;
    case 53:
      if ((this.airClient == null) || (!Actor.isAlive(this.airClient.jdField_actor_of_type_ComMaddoxIl2EngineActor)) || (!isOk())) {
        this.airClient = null;
        set_maneuver(0);
      }
      else {
        this.maxAOA = 5.0F;

        Ve.set(this.followOffset);
        Ve.jdField_x_of_type_Double -= 300.0D;
        tmpOr.setAT0(this.airClient.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        tmpOr.increment(0.0F, 4.0F, 0.0F);
        tmpOr.transform(Ve, Po);
        Po.scale(-1.0D);
        Po.add(this.airClient.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        Ve.sub(Po, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
        this.dist = (float)Ve.length();
        Ve.normalize();
        if (this.jdField_Vmax_of_type_Float < 83.0F) farTurnToDirection(8.5F); else {
          farTurnToDirection(7.0F);
        }
        f4 = (this.jdField_Energy_of_type_Float - this.airClient.jdField_Energy_of_type_Float) * 0.1019F;
        if (f4 < -50.0D + this.followOffset.jdField_z_of_type_Double) setSpeedMode(9); else
          setSpeedMode(10);
        this.tailForStaying = this.airClient;
        this.tailOffset.set(this.followOffset);
        this.tailOffset.scale(-1.0D);

        if (this.dist < 500.0D + Ve.jdField_x_of_type_Double * 200.0D) {
          pop();
        }
        else {
          if ((this.jdField_AOA_of_type_Float > 12.0F) && (this.Alt > 15.0F)) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, 12.0F - this.jdField_AOA_of_type_Float, 0.0F);
          if ((this.mn_time > 30.0F) && ((Ve.jdField_x_of_type_Double < 0.0D) || (this.dist > 10000.0F))) pop(); 
        }
      }break;
    case 68:
      if ((this.airClient == null) || (!Actor.isAlive(this.airClient.jdField_actor_of_type_ComMaddoxIl2EngineActor)) || (!isOk())) {
        set_maneuver(0);
      }
      else {
        localObject = (Maneuver)this.airClient;
        localManeuver = (Maneuver)((Maneuver)this.airClient).danger;
        if ((((Maneuver)localObject).getDangerAggressiveness() >= 1.0F - this.jdField_Skill_of_type_Int * 0.3F) && (localManeuver != null) && (hasCourseWeaponBullets())) {
          tmpV3d.sub(((Maneuver)localObject).jdField_Vwld_of_type_ComMaddoxJGPVector3d, localManeuver.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
          if (tmpV3d.length() < 170.0D) {
            set_task(6);
            this.target = localManeuver;
            set_maneuver(27);
          }
        }
        this.maxAOA = 5.0F;

        Ve.set(this.followOffset);
        Ve.jdField_x_of_type_Double -= 300.0D;
        tmpOr.setAT0(this.airClient.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        tmpOr.increment(0.0F, 4.0F, 0.0F);
        tmpOr.transform(Ve, Po);
        Po.scale(-1.0D);
        Po.add(this.airClient.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        Ve.sub(Po, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
        this.dist = (float)Ve.length();
        Ve.normalize();
        if (this.jdField_Vmax_of_type_Float < 83.0F) farTurnToDirection(8.5F); else {
          farTurnToDirection(7.0F);
        }

        setSpeedMode(10);

        this.tailForStaying = this.airClient;
        this.tailOffset.set(this.followOffset);
        this.tailOffset.scale(-1.0D);

        if (this.dist < 500.0D + Ve.jdField_x_of_type_Double * 200.0D) {
          pop();
        }
        else {
          if ((this.jdField_AOA_of_type_Float > 12.0F) && (this.Alt > 15.0F)) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, 12.0F - this.jdField_AOA_of_type_Float, 0.0F);
          if ((this.mn_time > 30.0F) && ((Ve.jdField_x_of_type_Double < 0.0D) || (this.dist > 10000.0F))) pop(); 
        }
      }break;
    case 59:
      if ((this.airClient == null) || (!Actor.isValid(this.airClient.jdField_actor_of_type_ComMaddoxIl2EngineActor)) || (!isOk())) {
        this.airClient = null;
        set_maneuver(0);
      }
      else {
        this.maxAOA = 5.0F;
        if (this.first) {
          this.followOffset.set(1000.0F * (float)Math.sin(this.beNearOffsPhase * 0.7854F), 1000.0F * (float)Math.cos(this.beNearOffsPhase * 0.7854F), -400.0D);
        }

        Ve.set(this.followOffset);
        Ve.jdField_x_of_type_Double -= 300.0D;
        tmpOr.setAT0(this.airClient.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        tmpOr.increment(0.0F, 4.0F, 0.0F);
        tmpOr.transform(Ve, Po);
        Po.scale(-1.0D);
        Po.add(this.airClient.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        Ve.sub(Po, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
        this.dist = (float)Ve.length();
        Ve.normalize();
        farTurnToDirection();

        setSpeedMode(2);
        this.tailForStaying = this.airClient;
        this.tailOffset.set(this.followOffset);
        this.tailOffset.scale(-1.0D);

        if (this.dist < 300.0F) {
          this.beNearOffsPhase += 1; if (this.beNearOffsPhase > 3) this.beNearOffsPhase = 0;
          f5 = (float)Math.sqrt(this.followOffset.jdField_x_of_type_Double * this.followOffset.jdField_x_of_type_Double + this.followOffset.jdField_y_of_type_Double * this.followOffset.jdField_y_of_type_Double);
          this.followOffset.set(f5 * (float)Math.sin(this.beNearOffsPhase * 1.5708F), f5 * (float)Math.cos(this.beNearOffsPhase * 1.5708F), this.followOffset.jdField_z_of_type_Double);
        }

        if ((this.jdField_AOA_of_type_Float > 12.0F) && (this.Alt > 15.0F)) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, 12.0F - this.jdField_AOA_of_type_Float, 0.0F);
        if ((this.mn_time > 15.0F) && ((Ve.jdField_x_of_type_Double < 0.0D) || (this.dist > 3000.0F))) pop();
        if (this.mn_time > 30.0F) pop(); 
      }break;
    case 63:
      if ((!(this instanceof RealFlightModel)) || (!((RealFlightModel)this).isRealMode())) {
        this.bombsOut = true;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.dropFuelTanks();
      }
      if ((this.target == null) || (!Actor.isValid(this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor)) || (this.target.isTakenMortalDamage()) || (!hasCourseWeaponBullets()))
      {
        this.target = null;
        clear_stack();
        set_maneuver(3);
      }
      else if ((this.target.getSpeedKMH() < 45.0F) && (this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) < 50.0D) && (this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() != this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()))
      {
        this.target_ground = this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor;
        set_task(7);
        clear_stack();
        set_maneuver(43);
      }
      else {
        if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof HE_LERCHE3)) && 
          (((HE_LERCHE3)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).bToFire)) {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[2] = true;
          ((HE_LERCHE3)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).bToFire = false;
        }

        if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TA_183)) && 
          (((TA_183)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).bToFire)) {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[2] = true;
          ((TA_183)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).bToFire = false;
        }

        if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TA_152C)) && 
          (((TA_152C)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).bToFire)) {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[2] = true;
          ((TA_152C)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).bToFire = false;
        }

        if (this.TargV.jdField_z_of_type_Double == -400.0D) fighterUnderBomber(paramFloat); else
          fighterVsBomber(paramFloat);
        if ((this.jdField_AOA_of_type_Float > this.jdField_AOA_Crit_of_type_Float - 2.0F) && (this.Alt > 15.0F)) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, 0.01F * (this.jdField_AOA_Crit_of_type_Float - 2.0F - this.jdField_AOA_of_type_Float), 0.0F); 
      }break;
    case 27:
      if ((!(this instanceof RealFlightModel)) || (!((RealFlightModel)this).isRealMode())) {
        this.bombsOut = true;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.dropFuelTanks();
      }
      if ((this.target == null) || (!Actor.isValid(this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor)) || (this.target.isTakenMortalDamage()) || (this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() == this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()) || (!hasCourseWeaponBullets()))
      {
        this.target = null;
        clear_stack();
        set_maneuver(0);
      }
      else if ((this.target.getSpeedKMH() < 45.0F) && (this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) < 50.0D) && (this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() != this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()))
      {
        this.target_ground = this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor;
        set_task(7);
        clear_stack();
        set_maneuver(43);
      }
      else {
        if ((this.first) && 
          ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeAcePlane))) {
          if ((this.target != null) && (this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor != null) && (this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() != this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()))
          {
            this.target.jdField_Skill_of_type_Int = 0;
          }
          if ((this.danger != null) && (this.danger.jdField_actor_of_type_ComMaddoxIl2EngineActor != null) && (this.danger.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() != this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()))
          {
            this.danger.jdField_Skill_of_type_Int = 0;
          }

        }

        if (this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() != this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()) ((Maneuver)this.target).danger = this;

        if (isTick(64, 0))
        {
          f4 = (this.target.jdField_Energy_of_type_Float - this.jdField_Energy_of_type_Float) * 0.1019F;
          Ve.sub(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
          Ve.normalize();
          f5 = 470.0F + (float)Ve.jdField_x_of_type_Double * 120.0F - f4;
          if (f5 < 0.0F) {
            clear_stack();
            set_maneuver(62);
          }
        }

        fighterVsFighter(paramFloat);
        setSpeedMode(9);

        if ((this.jdField_AOA_of_type_Float > this.jdField_AOA_Crit_of_type_Float - 1.0F) && (this.Alt > 15.0F)) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, 0.01F * (this.jdField_AOA_Crit_of_type_Float - 1.0F - this.jdField_AOA_of_type_Float), 0.0F);
        if (this.mn_time > 100.0F) {
          this.target = null;
          pop(); } 
      }break;
    case 62:
      if ((this.target == null) || (!Actor.isValid(this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor)) || (this.target.isTakenMortalDamage()) || (this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() == this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()) || (!hasCourseWeaponBullets()))
      {
        this.target = null;
        clear_stack();
        set_maneuver(0);
      }
      else if ((this.target.getSpeedKMH() < 45.0F) && (this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) < 50.0D) && (this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() != this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()))
      {
        this.target_ground = this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor;
        set_task(7);
        clear_stack();
        set_maneuver(43);
      }
      else {
        if ((this.first) && 
          ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeAcePlane))) {
          if ((this.target != null) && (this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor != null) && (this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() != this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()))
          {
            this.target.jdField_Skill_of_type_Int = 0;
          }
          if ((this.danger != null) && (this.danger.jdField_actor_of_type_ComMaddoxIl2EngineActor != null) && (this.danger.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() != this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()))
          {
            this.danger.jdField_Skill_of_type_Int = 0;
          }

        }

        if (this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() != this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy()) ((Maneuver)this.target).danger = this;
        goodFighterVsFighter(paramFloat);
      }break;
    case 70:
      this.checkGround = false;
      this.checkStrike = false;
      this.frequentControl = true;
      this.stallable = false;
      if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof HE_LERCHE3))
        switch (this.submaneuver) {
        case 0:
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
          this.submaneuver += 1;
          this.sub_Man_Count = 0;
        case 1:
          if (this.sub_Man_Count == 0) {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = World.cur().rnd.nextFloat(-0.15F, 0.15F);
          }
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl = 1.0F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 1.0F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = Aircraft.cvt(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), 0.0F, 60.0F, 1.0F, 0.0F);
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > 30.0F) {
            this.submaneuver += 1;
            this.sub_Man_Count = 0;
          }
          this.sub_Man_Count += 1;
          break;
        case 2:
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = 0.0F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.0F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 0.1F;
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, (float)(paramFloat * 0.1D * this.sub_Man_Count * (90.0D - this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage())), 0.0F);
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > 89.0F) {
            this.saveOr.set(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation);
            this.submaneuver += 1;
          }
          this.sub_Man_Count += 1;
          break;
        case 3:
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.0F;
          if (this.Alt > 10.0F)
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 0.33F;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 0.0F;
          }
          if (this.Alt < 20.0F) {
            if (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < -4.0D) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.95D;
            if (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.lengthSquared() < 1.0D) {
              this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setEngineStops();
            }
          }
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.set(this.saveOr);
          if (this.mn_time <= 100.0F) break;
          this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
          MsgDestroy.Post(Time.current() + 12000L, this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        }
      break;
    case 25:
      wingman(false);
      if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding()) {
        if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLandingOnShip()) {
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.landingAirport.rebuildLandWay(this);
          if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl == 1.0F) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.arrestorControl < 1.0F) && (!this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround())) this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setArrestor(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 1);
        }
        if (this.first) {
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setWayPoint(true);
          doDumpBombsPassively();
          this.submaneuver = 0;
        }
        if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof HE_LERCHE3)) && 
          (this.Alt < 50.0F)) {
          this.maneuver = 70;
        }

        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getP(Po);

        int k = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur();
        float f8 = (float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.last().z();
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(k);
        this.Alt = Math.min(this.Alt, f8);

        Po.add(0.0D, 0.0D, -3.0D);
        Ve.sub(Po, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        f10 = (float)Ve.length();
        int i1 = (this.Alt > 4.5F + this.jdField_Gears_of_type_ComMaddoxIl2FmGear.H) && (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur() < 8) ? 1 : 0;
        if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLandingOnShip()) i1 = (this.Alt > 4.5F + this.jdField_Gears_of_type_ComMaddoxIl2FmGear.H) && (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur() < 8) ? 1 : 0;
        if (i1 != 0) {
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.prev();
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getP(Pc);
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
          tmpV3f.sub(Po, Pc);
          tmpV3f.normalize();

          if ((tmpV3f.dot(Ve) < 0.0D) && (f10 > 1000.0F) && (!this.TaxiMode)) {
            this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.first();
            push(10);
            pop();
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = 0.0F;
          }

          f13 = (float)tmpV3f.dot(Ve);
          tmpV3f.scale(-f13);
          tmpV3f.add(Po, tmpV3f);
          tmpV3f.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);

          float f15 = 0.0005F * (3000.0F - f10);
          if (f15 > 1.0F) f15 = 1.0F;
          if (f15 < 0.1F) f15 = 0.1F;
          float f17 = (float)tmpV3f.length();
          if (f17 > 40.0F * f15) {
            f17 = 40.0F * f15;
            tmpV3f.normalize();
            tmpV3f.scale(f17);
          }

          float f19 = this.jdField_VminFLAPS_of_type_Float;
          if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur() >= 6) {
            if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLandingOnShip()) {
              if ((Actor.isAlive(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.landingAirport)) && 
                ((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.landingAirport instanceof AirportCarrier))) {
                float f20 = (float)((AirportCarrier)this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.landingAirport).speedLen();
                if (this.jdField_VminFLAPS_of_type_Float < f20 + 10.0F) f19 = f20 + 10.0F;
              }
            }
            else
              f19 = this.jdField_VminFLAPS_of_type_Float * 1.2F;
            if (f19 < 14.0F) f19 = 14.0F; 
          }
          else {
            f19 = this.jdField_VminFLAPS_of_type_Float * 2.0F;
          }
          d5 = this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length();
          double d6 = f19 - d5;
          float f21 = 2.0F * paramFloat;
          if (d6 > f21) d6 = f21;
          if (d6 < -f21) d6 = -f21;

          Ve.normalize();
          Ve.scale(d5);
          Ve.add(tmpV3f);
          Ve.sub(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);

          float f22 = (50.0F * f15 - f17) * paramFloat;
          if (Ve.length() > f22) {
            Ve.normalize();
            Ve.scale(f22);
          }
          this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.add(Ve);
          this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.normalize();
          this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.scale(d5 + d6);

          this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double += this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double * paramFloat;
          this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double += this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double * paramFloat;
          this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double += this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double * paramFloat;

          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(tmpV3f);
          tmpOr.setAT0(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);

          float f23 = 0.0F;
          if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLandingOnShip()) f23 = 0.9F * (45.0F - this.Alt); else
            f23 = 0.7F * (20.0F - this.Alt);
          if (f23 < 0.0F) f23 = 0.0F;
          tmpOr.increment(0.0F, 4.0F + f23, (float)(tmpV3f.jdField_y_of_type_Double * 0.5D));

          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.interpolate(tmpOr, 0.5F * paramFloat);
          this.jdField_callSuperUpdate_of_type_Boolean = false;
          this.jdField_W_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (0.05F + 0.3F * f23);
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (float)(-tmpV3f.jdField_y_of_type_Double * 0.02D);
          this.direction = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getAzimut();
        }
        else {
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabDirection(true);
        }
      } else {
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabDirection(true);
      }
      this.dA = this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl;
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.update(paramFloat);
      setSpeedControl(paramFloat);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = this.dA;
      if (this.maneuver != 25) return;

      if (this.Alt > 60.0F) {
        if (this.Alt < 160.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.32F; else
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.0F;
        setSpeedMode(7);
        this.smConstPower = 0.2F;
        this.dA = Math.min(130.0F + this.Alt, 270.0F);
        if ((this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D) || (getSpeedKMH() < this.dA)) this.dA = (-1.2F * paramFloat); else
          this.dA = (1.2F * paramFloat);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl * 0.9F + this.dA * 0.1F);
      } else {
        this.jdField_minElevCoeff_of_type_Float = 15.0F;
        if ((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur() >= 6) || (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur() == 0)) {
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < -5.0F) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, -this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - 5.0F, 0.0F);
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > this.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch + 10.0F)
            this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, -(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch + 10.0F)), 0.0F);
        }
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 1.0F;
        if (this.jdField_Vrel_of_type_ComMaddoxJGPVector3d.length() < 1.0D) {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = (this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 0.0F);
          if (!this.TaxiMode) {
            setSpeedMode(8);
            if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLandingOnShip()) {
              if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getFlap() < 0.001F) this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setWingFold(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 1);
              this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 1.0F;
              if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.arrestorControl == 1.0F) && (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround())) this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setArrestor(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 0);
              MsgDestroy.Post(Time.current() + 25000L, this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            } else {
              this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setEngineStops();
              if (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getPropw() < 1.0F) { World.cur(); if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor != World.getPlayerAircraft())
                  MsgDestroy.Post(Time.current() + 12000L, this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
              }
            }
          }
        }
        if (getSpeed() < this.jdField_VmaxFLAPS_of_type_Float * 0.21F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.0F;
        if ((this.jdField_Vrel_of_type_ComMaddoxJGPVector3d.length() < this.jdField_VmaxFLAPS_of_type_Float * 0.25F) && 
          (this.wayCurPos == null) && (!this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLandingOnShip())) {
          this.TaxiMode = true;
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(0);
          return;
        }

        if ((getSpeed() > this.jdField_VminFLAPS_of_type_Float * 0.6F) && (this.Alt < 10.0F)) {
          setSpeedMode(8);
          if ((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLandingOnShip()) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasArrestorControl)) {
            if (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < -5.5D) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.949999988079071D;
            if (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.910000026226044D; 
          }
          else {
            if (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < -0.6000000238418579D) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.9399999976158142D;
            if (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < -2.5D) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double = -2.5D;
            if (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.910000026226044D;
          }
        }
        float f6 = this.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch - 2.0F;
        if (f6 < 5.0F) f6 = 5.0F;
        if (((this.Alt < 7.0F) && (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < f6)) || (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLandingOnShip())) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 1.5F * paramFloat;
        Controls tmp14959_14956 = this.jdField_CT_of_type_ComMaddoxIl2FmControls; tmp14959_14956.ElevatorControl = (float)(tmp14959_14956.ElevatorControl + 0.0500000007450581D * getW().jdField_y_of_type_Double);
        if (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) {
          if (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch > 5.0F) {
            if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < this.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 1.5F * paramFloat;
            if (!this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLandingOnShip())
            {
              Controls tmp15055_15052 = this.jdField_CT_of_type_ComMaddoxIl2FmControls; tmp15055_15052.ElevatorControl = (float)(tmp15055_15052.ElevatorControl + 0.300000011920929D * getW().jdField_y_of_type_Double);
            }
          } else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.0F;
          }
          if (!this.TaxiMode) {
            this.AFo.setDeg(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getAzimut(), this.direction);
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (8.0F * this.AFo.getDiffRad() + 0.5F * (float)getW().jdField_z_of_type_Double); } else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
          }
        }
      }
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getP(Po);

      return;
    case 66:
      if ((!isCapableOfTaxiing()) || (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getThrustOutput() < 0.01F)) {
        set_task(3);
        this.maneuver = 0;
        set_maneuver(49);
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
      }
      else {
        if (this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotDead(0)) {
          set_task(3);
          this.maneuver = 0;
          set_maneuver(44);
          setSpeedMode(8);
          this.smConstPower = 0.0F;
          if (Airport.distToNearestAirport(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d) > 900.0D)
            ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).postEndAction(6000.0D, this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 3, null);
          else
            MsgDestroy.Post(Time.current() + 300000L, this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          return;
        }

        P.jdField_x_of_type_Double = this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double;
        P.jdField_y_of_type_Double = this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double;
        P.jdField_z_of_type_Double = this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double;
        Vcur.set(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);

        if (this.wayCurPos == null) {
          World.cur().airdrome.findTheWay((Pilot)this);
          this.wayPrevPos = (this.wayCurPos = getNextAirdromeWayPoint());
        }
        if (this.wayCurPos != null) {
          Point_Any localPoint_Any1 = this.wayCurPos;
          Point_Any localPoint_Any2 = this.wayPrevPos;
          Pcur.set((float)P.jdField_x_of_type_Double, (float)P.jdField_y_of_type_Double);
          f10 = Pcur.distance(localPoint_Any1);
          f12 = Pcur.distance(localPoint_Any2);

          V_to.sub(localPoint_Any1, Pcur);
          V_to.normalize();

          f13 = 5.0F + 0.1F * f10;
          if (f13 > 12.0F) f13 = 12.0F;
          if (f13 > 0.9F * this.jdField_VminFLAPS_of_type_Float) f13 = 0.9F * this.jdField_VminFLAPS_of_type_Float;
          if (((this.curAirdromePoi < this.airdromeWay.length) && (f10 < 15.0F)) || (f10 < 4.0F))
          {
            f13 = 0.0F;
            Point_Any localPoint_Any3 = getNextAirdromeWayPoint();
            if (localPoint_Any3 == null) {
              this.jdField_CT_of_type_ComMaddoxIl2FmControls.setPowerControl(0.0F);
              this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(P);
              this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
              if (this.finished) return;
              this.finished = true;

              int i3 = 1000;
              if (this.wayCurPos != null) i3 = 2400000;
              this.jdField_actor_of_type_ComMaddoxIl2EngineActor.collide(true);
              this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.set(0.0D, 0.0D, 0.0D);
              this.jdField_CT_of_type_ComMaddoxIl2FmControls.setPowerControl(0.0F);
              this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setCurControlAll(true);
              this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setEngineStops();
              World.cur(); if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor != World.getPlayerAircraft()) {
                MsgDestroy.Post(Time.current() + i3, this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
                setStationedOnGround(true);
                this.maneuver = 0;
                set_maneuver(44);
              }
              return;
            }
            this.wayPrevPos = this.wayCurPos;
            this.wayCurPos = localPoint_Any3;
          }
          V_to.scale(f13);

          float f16 = 2.0F * paramFloat;
          Vdiff.set(V_to);
          Vdiff.sub(Vcur);
          float f18 = (float)Vdiff.length();
          if (f18 > f16) {
            Vdiff.normalize();
            Vdiff.scale(f16);
          }
          Vcur.add(Vdiff);

          tmpOr.setYPR(FMMath.RAD2DEG((float)Vcur.direction()), this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getPitch(), 0.0F);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.interpolate(tmpOr, 0.2F);
          this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double = Vcur.jdField_x_of_type_Double;
          this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double = Vcur.jdField_y_of_type_Double;
          P.jdField_x_of_type_Double += Vcur.jdField_x_of_type_Double * paramFloat;
          P.jdField_y_of_type_Double += Vcur.jdField_y_of_type_Double * paramFloat;
        } else {
          this.wayPrevPos = (this.wayCurPos = new Point_Null((float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, (float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double));
          World.cur(); if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor != World.getPlayerAircraft()) {
            MsgDestroy.Post(Time.current() + 30000L, this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            setStationedOnGround(true);
          }
        }

        this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(P);
        this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        return;
      }

    case 49:
      emergencyLanding(paramFloat);
      break;
    case 64:
      if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeGlider)) { pop(); } else {
        if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof HE_LERCHE3)) {
          int m = (Actor.isAlive(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport)) && ((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport instanceof AirportCarrier)) ? 1 : 0;
          if (m == 0) this.jdField_callSuperUpdate_of_type_Boolean = false;
        }
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 1.0F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.5F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.setPowerControl(0.0F);
        this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setCurControlAll(false);
        setSpeedMode(0);
        if (World.Rnd().nextFloat() < 0.05F) {
          this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setCurControl(this.submaneuver, true);
          if (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[this.submaneuver].getStage() == 0) {
            setSpeedMode(0);
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.setPowerControl(0.05F);
            this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[this.submaneuver].setControlThrottle(0.2F);
            this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.toggle();
            if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof BI_1)) || ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof BI_6))) { pop(); break label23427; }
          }
        }
        if (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[this.submaneuver].getStage() == 6) {
          setSpeedMode(0);
          this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[this.submaneuver].setControlThrottle(0.0F);
          this.submaneuver += 1;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.setPowerControl(0.0F);
          if (this.submaneuver > this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum() - 1) {
            this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setCurControlAll(true);
            pop(); } 
        }
      }break;
    case 26:
      f7 = this.Alt;
      f9 = 0.4F;
      if ((Actor.isAlive(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport)) && 
        ((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport instanceof AirportCarrier))) {
        f7 -= ((AirportCarrier)this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport).height();
        f9 = 0.95F;
        if ((this.Alt < 9.0F) && (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < 0.0D)) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.85D;
        if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasCockpitDoorControl) this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 1);
      }

      if (this.first) {
        setCheckGround(false);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 1.0F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = 1.0F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 0.0F;
        if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasArrestorControl) this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setArrestor(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 0);
        setSpeedMode(8);
        if ((f7 > 7.21F) && (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur() == 0)) {
          this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setEngineRunning();
          Aircraft.debugprintln(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, "in the air - engines running!.");
        }
        if (!Actor.isAlive(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport)) {
          this.direction = this.jdField_actor_of_type_ComMaddoxIl2EngineActor.pos.getAbsOrient().getAzimut();
        }
        if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof HE_LERCHE3)) {
          this.maneuver = 69;
          break label23427;
        }
      }

      if (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) {
        if (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() == 0) {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 0.0F;
          setSpeedMode(8);
          if (((World.Rnd().nextFloat() < 0.05F) && (this.mn_time > 1.0F)) || (this.mn_time > 8.0F)) {
            push();
            push(64);
            this.submaneuver = 0;
            this.maneuver = 0;
            safe_pop();
            break label23427;
          }
        } else {
          Po.set(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
          Vpl.set(60.0D, 0.0D, 0.0D);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(Vpl);
          Po.add(Vpl);
          Pd.set(Po);
          if (this.canTakeoff) {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 1.1F;
            setSpeedMode(9);
          } else {
            setSpeedMode(8);
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 1.0F;
            int n = 1;
            if (this.mn_time < 8.0F) n = 0;
            if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor != War.getNearestFriendAtPoint(Pd, (Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 70.0F)) {
              if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof G4M2E)) {
                if (War.getNearestFriendAtPoint(Pd, (Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 70.0F) != ((G4M2E)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).typeDockableGetDrone())
                  n = 0;
              }
              else {
                n = 0;
              }
            }
            if ((Actor.isAlive(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport)) && 
              (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport.takeoffRequest > 0)) n = 0;

            if (n != 0) {
              this.canTakeoff = true;
              if (Actor.isAlive(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport)) {
                this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport.takeoffRequest = 300;
              }
            }
          }
        }

        if ((this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() == 6) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasWingControl) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getWing() > 0.001F))
        {
          this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setWingFold(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 0);
        }
      }
      else if (this.canTakeoff) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 1.1F;
        setSpeedMode(9);
      }

      if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl == 0.0F) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getWing() < 0.001F)) this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.4F;

      if ((this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() == 6) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getPower() > f9)) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 0.0F;
        this.jdField_brakeShoe_of_type_Boolean = false;
        float f11 = this.jdField_Vmin_of_type_Float * this.jdField_M_of_type_ComMaddoxIl2FmMass.getFullMass() / this.jdField_M_of_type_ComMaddoxIl2FmMass.massEmpty;
        f12 = 12.0F * (getSpeed() / f11 - 0.2F);
        if (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.bIsSail) f12 *= 2.0F;
        if (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.bFrontWheel) {
          f12 = this.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch + 4.0F;
        }

        if (f12 < 1.0F) f12 = 1.0F;
        if (f12 > 10.0F) f12 = 10.0F;
        f13 = 1.5F;
        if ((Actor.isAlive(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport)) && 
          ((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport instanceof AirportCarrier)) && 
          (!this.jdField_Gears_of_type_ComMaddoxIl2FmGear.isUnderDeck())) {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = 0.0F;
          if (f7 < 0.0F) {
            f12 = 18.0F;
            f13 = 0.05F;
          } else {
            f12 = 14.0F;
            f13 = 0.3F;
          }

        }

        if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < f12)
          this.dA = (-0.7F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - f12) + f13 * (float)getW().jdField_y_of_type_Double + 0.5F * (float)getAW().jdField_y_of_type_Double);
        else {
          this.dA = (-0.1F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - f12) + f13 * (float)getW().jdField_y_of_type_Double + 0.5F * (float)getAW().jdField_y_of_type_Double);
        }
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += (this.dA - this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl) * 3.0F * paramFloat; } else {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 1.0F;
      }
      this.AFo.setDeg(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getAzimut(), this.direction);
      double d2 = this.AFo.getDiffRad();
      if (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() == 6) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (8.0F * (float)d2);
        if ((d2 > -1.0D) && (d2 < 1.0D)) {
          if ((Actor.isAlive(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport)) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getPower() > 0.3F)) {
            double d3 = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport.ShiftFromLine(this);
            double d4 = 3.0D - 3.0D * Math.abs(d2);
            if (d4 > 1.0D) d4 = 1.0D;
            d5 = 0.25D * d3 * d4;
            if (d5 > 1.5D) d5 = 1.5D;
            if (d5 < -1.5D) d5 = -1.5D;
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl += (float)d5;
          }
        }
        else this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 1.0F;

      }

      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.05F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() + 0.3F * (float)getW().jdField_y_of_type_Double);

      if ((f7 > 5.0F) && (!this.jdField_Gears_of_type_ComMaddoxIl2FmGear.isUnderDeck())) this.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = 0.0F;
      float f14 = 1.0F;
      if (hasBombs()) f14 *= 1.7F;
      if ((f7 > 120.0F * f14) || (getSpeed() > this.jdField_Vmin_of_type_Float * 1.8F * f14) || ((f7 > 80.0F * f14) && (getSpeed() > this.jdField_Vmin_of_type_Float * 1.6F * f14)) || ((f7 > 40.0F * f14) && (getSpeed() > this.jdField_Vmin_of_type_Float * 1.3F * f14) && (this.mn_time > 60.0F + ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).aircIndex() * 3.0F)))
      {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.0F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = 0.0F;
        this.rwLoc = null;
        if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeGlider)) push(24);
        this.maneuver = 0;
        this.jdField_brakeShoe_of_type_Boolean = false;
        this.jdField_turnOffCollisions_of_type_Boolean = false;
        if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasCockpitDoorControl) this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 0);
        pop();
      }
      if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeGlider)) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 0.0F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.05F;
        this.canTakeoff = true; } break;
    case 69:
      f7 = this.Alt;
      f9 = 0.4F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 1.0F;
      this.jdField_W_of_type_ComMaddoxJGPVector3d.scale(0.0D);
      int i2 = (Actor.isAlive(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport)) && ((this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport instanceof AirportCarrier)) ? 1 : 0;
      if (i2 != 0) {
        f7 -= ((AirportCarrier)this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport).height();
        f9 = 0.8F;
        if ((this.Alt < 9.0F) && (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < 0.0D))
          this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.85D;
        if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasCockpitDoorControl) {
          this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 1);
        }
      }
      if ((this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double != 0.0D) && (((HE_LERCHE3)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).suka.getPoint().jdField_z_of_type_Double == 0.0D)) {
        ((HE_LERCHE3)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).suka.set(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation);
      }
      if (this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double != 0.0D) {
        if ((this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getPowerOutput() < f9) && (i2 == 0)) {
          this.jdField_callSuperUpdate_of_type_Boolean = false;
          this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(((HE_LERCHE3)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).suka.getPoint());
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.set(((HE_LERCHE3)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).suka.getOrient());
        } else if (f7 < 100.0F) {
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.set(((HE_LERCHE3)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).suka.getOrient());
        }

      }

      if ((this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) && 
        (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() == 0)) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 0.0F;
        setSpeedMode(8);
        if (((World.Rnd().nextFloat() < 0.05F) && (this.mn_time > 1.0F)) || (this.mn_time > 8.0F)) {
          push();
          push(64);
          this.submaneuver = 0;
          this.maneuver = 0;
          safe_pop();
          break label23427;
        }

      }

      if (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getStage() == 6) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 0.0F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl = 1.0F;
        this.jdField_brakeShoe_of_type_Boolean = false;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 1.1F;
        setSpeedMode(9);
      }

      if (f7 > 200.0F) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = 0.0F;
        this.rwLoc = null;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = -1.0F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl = 0.0F;
        if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < 25.0F) {
          this.maneuver = 0;
        }
        this.jdField_brakeShoe_of_type_Boolean = false;
        this.jdField_turnOffCollisions_of_type_Boolean = false;
        if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasCockpitDoorControl) this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 0);
        pop(); } break;
    case 28:
      if (this.first) {
        this.direction = World.Rnd().nextFloat(-25.0F, 25.0F);
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
        setSpeedMode(6);

        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = World.Rnd().nextFloat(-0.22F, 0.22F);
        this.submaneuver = 0;
        if (getSpeed() < this.jdField_Vmin_of_type_Float * 1.5F) pop();
      }
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - this.direction));
      switch (this.submaneuver) {
      case 0:
        this.dA = 1.0F;
        this.maxAOA = (12.0F + 1.0F * this.jdField_Skill_of_type_Int);
        if ((this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.6F * paramFloat;
        else {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 3.3F * paramFloat;
        }
        if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() <= 40.0F + 5.1F * this.jdField_Skill_of_type_Int) break; this.submaneuver += 1; break;
      case 1:
        this.direction = World.Rnd().nextFloat(-25.0F, 25.0F);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = World.Rnd().nextFloat(-0.75F, 0.75F);
        this.submaneuver += 1;
      case 2:
        this.dA = -1.0F;
        this.maxAOA = (12.0F + 5.0F * this.jdField_Skill_of_type_Int);
        if ((this.jdField_AOA_of_type_Float < -this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl < this.dA))
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.6F * paramFloat;
        else {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 3.3F * paramFloat;
        }
        if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() >= -45.0F) break; pop();
      }

      if (this.mn_time > 3.0F) pop(); break;
    case 29:
      this.jdField_minElevCoeff_of_type_Float = 17.0F;
      if (this.first) {
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
        setSpeedMode(9);
        this.sub_Man_Count = 0;
      }
      if (this.danger != null) {
        if (this.sub_Man_Count == 0) {
          tmpV3d.set(this.danger.getW());
          this.danger.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(tmpV3d);
          if (tmpV3d.jdField_z_of_type_Double > 0.0D) {
            this.sinKren = (-World.Rnd().nextFloat(60.0F, 90.0F));
          }
          else {
            this.sinKren = World.Rnd().nextFloat(60.0F, 90.0F);
          }

        }

        if (this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.distanceSquared(this.danger.jdField_Loc_of_type_ComMaddoxJGPPoint3d) < 5000.0D) {
          setSpeedMode(8);
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 0.0F;
        } else {
          setSpeedMode(9);
        }
      } else {
        pop();
      }

      this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.2F;
      if (getSpeed() < 120.0D) this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.33F;
      if (getSpeed() < 80.0D) this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 1.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.08F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() + this.sinKren));
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.9F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
      this.sub_Man_Count += 1;
      if (this.sub_Man_Count > 15) this.sub_Man_Count = 0;

      if (this.mn_time > 10.0F) pop(); break;
    case 56:
      if (this.first) {
        this.submaneuver = World.Rnd().nextInt(0, 1);
        this.direction = World.Rnd().nextFloat(-20.0F, -10.0F);
      }
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.08F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - this.direction));
      switch (this.submaneuver) {
      case 0:
        this.dA = 1.0F;
        this.maxAOA = (10.0F + 2.0F * this.jdField_Skill_of_type_Int);
        if ((getOverload() > 1.0D / Math.abs(Math.cos(Math.toRadians(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren())))) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.2F * paramFloat;
        else {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.4F * paramFloat;
        }
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-1.0F * (float)Math.sin(Math.toRadians(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() + 55.0F)));
        if (this.mn_time <= 1.5F) break; this.submaneuver += 1; break;
      case 1:
        this.direction = World.Rnd().nextFloat(10.0F, 20.0F);
        this.submaneuver += 1;
      case 2:
        this.dA = 1.0F;
        this.maxAOA = (10.0F + 2.0F * this.jdField_Skill_of_type_Int);
        if ((getOverload() > 1.0D / Math.abs(Math.cos(Math.toRadians(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren())))) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.2F * paramFloat;
        else {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.4F * paramFloat;
        }
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (1.0F * (float)Math.sin(Math.toRadians(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - 55.0F)));
        if (this.mn_time <= 4.5F) break; this.submaneuver += 1; break;
      case 3:
        this.direction = World.Rnd().nextFloat(-20.0F, -10.0F);
        this.submaneuver += 1;
      case 4:
        this.dA = 1.0F;
        this.maxAOA = (10.0F + 2.0F * this.jdField_Skill_of_type_Int);
        if ((getOverload() > 1.0D / Math.abs(Math.cos(Math.toRadians(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren())))) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.2F * paramFloat;
        else {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.4F * paramFloat;
        }
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-1.0F * (float)Math.sin(Math.toRadians(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() + 55.0F)));
        if (this.mn_time <= 6.0F) break; pop();
      }

      break;
    case 30:
      push(14);
      push(18);
      pop();
      break;
    case 31:
      push(14);
      push(19);
      pop();
      break;
    case 32:
      if ((!isCapableOfACM()) && (World.Rnd().nextInt(-2, 9) < this.jdField_Skill_of_type_Int)) {
        ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).hitDaSilk();
      }
      if (this.first) {
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
        setSpeedMode(6);

        this.submaneuver = 0;
        this.direction = (178.0F - World.Rnd().nextFloat(0.0F, 8.0F) * this.jdField_Skill_of_type_Int);
      }
      this.maxAOA = (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D ? 14.0F : 24.0F);
      if (this.danger != null) {
        Ve.sub(this.danger.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        this.dist = (float)Ve.length();
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
        Vpl.set(this.danger.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Vpl);
        Vpl.normalize();
      }
      switch (this.submaneuver) {
      case 0:
        this.dA = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - 180.0F);
        if (this.dA < -180.0F) this.dA += 360.0F;
        this.dA = (-0.08F * this.dA);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = this.dA;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (this.dA > 0.0F ? 1.0F : -1.0F);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (0.01111111F * Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()));
        if ((this.mn_time <= 2.0F) && (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) <= this.direction)) break;
        this.submaneuver += 1;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = World.Rnd().nextFloat(-0.5F, 0.5F);
        this.direction = World.Rnd().nextFloat(-60.0F, -30.0F);
        this.mn_time = 0.5F; break;
      case 1:
        this.dA = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - 180.0F);
        if (this.dA < -180.0F) this.dA += 360.0F;
        this.dA = (-0.04F * this.dA);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = this.dA;
        if ((this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > this.direction + 5.0F) && (getOverload() < this.maxG) && (this.jdField_AOA_of_type_Float < this.maxAOA)) {
          if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl < 0.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.0F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 1.0F * paramFloat;
        } else {
          if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > 0.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.0F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 1.0F * paramFloat;
        }
        if ((this.mn_time > 2.0F) && (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < this.direction + 20.0F)) {
          this.submaneuver += 1;
        }
        if (this.danger == null) break;
        if ((this.jdField_Skill_of_type_Int >= 2) && (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < -30.0F) && (Vpl.jdField_x_of_type_Double > 0.9986295104026794D)) this.submaneuver += 1;
        if ((this.jdField_Skill_of_type_Int < 3) || (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage()) <= 145.0F) || (Math.abs(this.danger.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage()) <= 135.0F) || (this.dist >= 400.0F)) break;
        this.submaneuver += 1; break;
      case 2:
        this.direction = World.Rnd().nextFloat(-60.0F, 60.0F);
        setSpeedMode(6);

        this.submaneuver += 1;
      case 3:
        this.dA = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - this.direction);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.dA);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (this.dA > 0.0F ? 1.0F : -1.0F);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.5F;
        if (Math.abs(this.dA) >= 4.0F + 3.0F * this.jdField_Skill_of_type_Int) break; this.submaneuver += 1; break;
      case 4:
        this.direction *= World.Rnd().nextFloat(0.5F, 1.0F);
        setSpeedMode(6);

        this.submaneuver += 1;
      case 5:
        this.dA = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - this.direction);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.dA);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
        this.dA = 1.0F;
        if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA) || (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > 40.0F))
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.8F * paramFloat;
        else {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 1.6F * paramFloat;
        }
        if ((this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > 80.0F) || (this.mn_time > 4.0F) || (getSpeed() < this.jdField_Vmin_of_type_Float * 1.5F)) pop();
        if ((this.danger == null) || (
          (Ve.jdField_z_of_type_Double >= -1.0D) && (this.dist <= 600.0F) && (Vpl.jdField_x_of_type_Double >= 0.7880100011825562D))) break; pop();
      }

      if (this.Alt < -7.0D * this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double) pop(); break;
    case 33:
      if (this.first) {
        if (this.Alt < 1000.0F) {
          pop();
        }
        else {
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
          this.submaneuver = 0;
          this.direction = ((World.Rnd().nextBoolean() ? 1.0F : -1.0F) * World.Rnd().nextFloat(107.0F, 160.0F));
        }
      } else {
        this.maxAOA = (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D ? 14.0F : 24.0F);
        switch (this.submaneuver) {
        case 0:
          if (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) < 45.0F) {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (0.005555556F * Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()));
          }
          else if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > 1.0F))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.2F * paramFloat;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 1.2F * paramFloat;
          }

          this.dA = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - this.direction);
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.dA);
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          if (Math.abs(this.dA) >= 4.0F + 1.0F * this.jdField_Skill_of_type_Int) break; this.submaneuver += 1; break;
        case 1:
          setSpeedMode(7);
          this.smConstPower = 0.5F;

          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = 0.0F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.2F * paramFloat;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 1.2F * paramFloat;
          }
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() >= -60.0F) break; this.submaneuver += 1; break;
        case 2:
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > -45.0F) {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
            setSpeedMode(9);

            this.maxAOA = 7.0F;
          }
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.8F * paramFloat;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.4F * paramFloat;
          }
          if ((this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() <= this.jdField_AOA_of_type_Float - 10.0F) && (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double <= -1.0D)) break; pop();
        }

        if (this.Alt < -7.0D * this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double) pop(); 
      }break;
    case 34:
      if (this.first) {
        if (this.Alt < 500.0F) {
          pop();
        }
        else {
          this.direction = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage();
          setSpeedMode(9);
        }
      } else {
        this.dA = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() - (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() > 0.0F ? 35.0F : -35.0F));

        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.dA);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() > 0.0F ? 1.0F : -1.0F);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = -1.0F;
        if ((this.direction > this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() + 45.0F) || (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < -60.0F) || (this.mn_time > 4.0F))
        {
          pop(); } 
      }break;
    case 36:
    case 37:
      if (this.first) {
        if (!isCapableOfACM()) pop();
        if (getSpeed() < this.jdField_Vmax_of_type_Float * 0.5F) {
          pop();
        }
        else {
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
          this.submaneuver = 0;
          this.direction = World.Rnd().nextFloat(-30.0F, 80.0F);
          setSpeedMode(9);
        }
      } else {
        this.maxAOA = (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D ? 14.0F : 24.0F);
        switch (this.submaneuver) {
        case 0:
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-0.1F * getAOS());
          if (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) >= 45.0F) break; this.submaneuver += 1; break;
        case 1:
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = 0.0F;
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > this.dA))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.4F * paramFloat;
          else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.8F * paramFloat;
          }
          if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > this.direction) this.submaneuver += 1;
          if (getSpeed() >= this.jdField_Vmin_of_type_Float * 1.25F) break;
          pop(); break;
        case 2:
          push(this.maneuver == 36 ? 7 : 35);
          pop();
        }
      }
      break;
    case 38:
      if (this.first) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() > 0.0F ? 1.0F : -1.0F);
      }
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl += -0.02F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();
      if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl > 0.1F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = 0.1F;
      if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl < -0.1F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = -0.1F;
      this.dA = ((getSpeedKMH() - 180.0F - this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() * 10.0F - getVertSpeed() * 5.0F) * 0.004F);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = this.dA;
      if (this.mn_time > 3.5F) pop(); break;
    case 39:
      setSpeedMode(6);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (-0.04F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() + 10.0F));
      if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl > 0.1F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.8F;
      else if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl < -0.1F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = -0.8F; else
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() > 0.0F ? 1.0F : -1.0F);
      if ((getSpeed() > this.jdField_Vmax_of_type_Float) || (this.mn_time > 7.0F)) pop(); break;
    case 40:
      push(39);
      push(57);
      pop();
      break;
    case 41:
      push(13);
      push(18);
      pop();
      break;
    case 42:
      push(19);
      push(57);
      pop();
      break;
    case 46:
      if ((this.target_ground == null) || (this.target_ground.pos == null)) {
        if (this.Group != null) {
          this.dont_change_subm = true;
          boolean bool1 = isBusy();
          int i5 = this.Group.grTask;
          this.Group.grTask = 4;
          setBusy(false);
          this.Group.setTaskAndManeuver(this.Group.numInGroup((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor));
          setBusy(bool1);
          this.Group.grTask = i5;
        }
        if ((this.target_ground == null) || (this.target_ground.pos == null)) {
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.first();

          Airport localAirport = Airport.nearest(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getP(), this.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy(), 7);
          WayPoint localWayPoint;
          if (localAirport != null) localWayPoint = new WayPoint(localAirport.pos.getAbsPoint()); else
            localWayPoint = new WayPoint(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.first().getP());
          localWayPoint.set(0.6F * this.jdField_Vmax_of_type_Float);
          localWayPoint.Action = 2;
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.add(localWayPoint);
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.last();
          set_task(3);
          clear_stack();
          this.maneuver = 21;
          set_maneuver(21);
          break label23427;
        }
      }
      groundAttackKamikaze(this.target_ground, paramFloat);
      break;
    case 43:
    case 47:
    case 50:
    case 51:
    case 52:
      if ((this.first) && 
        (!isCapableOfACM())) {
        this.bombsOut = true;
        setReadyToReturn(true);
        if (this.Group != null) { this.Group.waitGroup(this.Group.numInGroup((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor));
        } else {
          this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
          set_task(3);
          clear_stack();
          set_maneuver(21);
        }
      }
      else
      {
        if ((this.target_ground == null) || (this.target_ground.pos == null) || (!Actor.isAlive(this.target_ground))) {
          int i4 = this.maneuver;
          if ((this.Group != null) && (this.Group.grTask == 4)) {
            this.dont_change_subm = true;
            boolean bool2 = isBusy();
            setBusy(false);
            this.Group.setTaskAndManeuver(this.Group.numInGroup((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor));
            setBusy(bool2);
          }
          if ((this.target_ground == null) || (this.target_ground.pos == null) || (!Actor.isAlive(this.target_ground))) {
            if (i4 == 50) this.bombsOut = true;
            if (this.Group != null) { this.Group.waitGroup(this.Group.numInGroup((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor));
            } else {
              this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
              set_task(3);
              clear_stack();
              set_maneuver(21);
            }
            push(2);
            pop();
            break label23427;
          }
        }

        switch (this.maneuver) {
        case 43:
          groundAttack(this.target_ground, paramFloat);
          if (this.mn_time <= 120.0F) break;
          set_maneuver(0); break;
        case 50:
          groundAttackDiveBomber(this.target_ground, paramFloat);
          if (this.mn_time <= 120.0F) break;
          setSpeedMode(6);
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 0.0F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl = 0.0F;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.0F;
          pop();
          this.sub_Man_Count = 0; break;
        case 51:
          groundAttackTorpedo(this.target_ground, paramFloat);
          break;
        case 52:
          groundAttackCassette(this.target_ground, paramFloat);
          break;
        case 46:
          groundAttackKamikaze(this.target_ground, paramFloat);
          break;
        case 47:
          groundAttackTinyTim(this.target_ground, paramFloat);
        case 44:
        case 45:
        case 48:
        case 49:
        }
      }case 58:
    }label23427: if (this.checkGround) doCheckGround(paramFloat);
    if ((this.checkStrike) && (this.strikeEmer)) doCheckStrike();
    this.strikeEmer = false;
    setSpeedControl(paramFloat);

    this.first = false;
    this.mn_time += paramFloat;
    if (this.frequentControl) this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.update(paramFloat); else
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.update(paramFloat * 4.0F);
    if (this.bBusy) this.wasBusy = true; else this.wasBusy = false;
    if (this.shotAtFriend > 0) this.shotAtFriend -= 1;
  }

  void OutCT(int paramInt)
  {
    if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor != Main3D.cur3D().viewActor()) return;

    TextScr.output(paramInt + 5, 45, "Alt(MSL)  " + (int)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double + "    " + (this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl > 0.0F ? "BRAKE" : ""));
    TextScr.output(paramInt + 5, 65, "Alt(AGL)  " + (int)(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double)));

    int i = 0;
    TextScr.output(paramInt + 225, 140, "---ENGINES (" + this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum() + ")---" + this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getStage());
    TextScr.output(paramInt + 245, 120, "THTL " + (int)(100.0F * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getControlThrottle()) + "%" + (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getControlAfterburner() ? " (NITROS)" : ""));
    TextScr.output(paramInt + 245, 100, "PROP " + (int)(100.0F * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getControlProp()) + "%" + (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getStepControlAuto() ? " (AUTO)" : ""));
    TextScr.output(paramInt + 245, 80, "MIX " + (int)(100.0F * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getControlMix()) + "%");
    TextScr.output(paramInt + 245, 60, "RAD " + (int)(100.0F * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getControlRadiator()) + "%" + (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getRadiatorControlAuto() ? " (AUTO)" : ""));
    TextScr.output(paramInt + 245, 40, "SUPC " + this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getControlCompressor() + "x");
    TextScr.output(245, 20, "PropAoA :" + (int)Math.toDegrees(this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getPropAoA()));

    TextScr.output(paramInt + 455, 120, "Cyls/Cams " + this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getCylindersOperable() + "/" + this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCylinders());
    TextScr.output(paramInt + 455, 100, "Readyness " + (int)(100.0F * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getReadyness()) + "%");
    TextScr.output(paramInt + 455, 80, "PRM " + (int)((int)(this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getRPM() * 0.02F) * 50.0F) + " rpm");
    TextScr.output(paramInt + 455, 60, "Thrust " + (int)this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getEngineForce().x + " N");
    TextScr.output(paramInt + 455, 40, "Fuel " + (int)(100.0F * this.jdField_M_of_type_ComMaddoxIl2FmMass.fuel / this.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel) + "% Nitro " + (int)(100.0F * this.jdField_M_of_type_ComMaddoxIl2FmMass.nitro / this.jdField_M_of_type_ComMaddoxIl2FmMass.maxNitro) + "%");
    TextScr.output(paramInt + 455, 20, "MPrs " + (int)(1000.0F * this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].getManifoldPressure()) + " mBar");

    TextScr.output(paramInt + 640, 140, "---Controls---");
    TextScr.output(paramInt + 640, 120, "A/C: " + (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasAileronControl ? "" : "AIL ") + (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasElevatorControl ? "" : "ELEV ") + (this.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasRudderControl ? "" : "RUD ") + (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.bIsHydroOperable ? "" : "GEAR "));
    TextScr.output(paramInt + 640, 100, "ENG: " + (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].isHasControlThrottle() ? "" : "THTL ") + (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].isHasControlProp() ? "" : "PROP ") + (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].isHasControlMix() ? "" : "MIX ") + (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].isHasControlCompressor() ? "" : "SUPC ") + (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[i].isPropAngleDeviceOperational() ? "" : "GVRNR "));
    TextScr.output(paramInt + 640, 80, "PIL: (" + (int)(this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.getPilotHealth(0) * 100.0F) + "%)");

    TextScr.output(paramInt + 5, 105, "V   " + (int)getSpeedKMH());
    TextScr.output(paramInt + 5, 125, "AOA " + (int)(getAOA() * 1000.0F) / 1000.0F);
    TextScr.output(paramInt + 5, 145, "AOS " + (int)(getAOS() * 1000.0F) / 1000.0F);
    TextScr.output(paramInt + 5, 165, "Kr  " + (int)this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
    TextScr.output(paramInt + 5, 185, "Ta  " + (int)this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage());
    TextScr.output(paramInt + 250, 185, "way.speed  " + this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().getV() * 3.6F + "way.z " + this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().z() + "  mn_time = " + this.mn_time);

    TextScr.output(paramInt + 5, 205, "<" + this.jdField_actor_of_type_ComMaddoxIl2EngineActor.name() + ">: " + ManString.tname(this.task) + ":" + ManString.name(this.maneuver) + " , WP=" + this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur() + "(" + (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.size() - 1) + ")-" + ManString.wpname(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action));

    TextScr.output(paramInt + 7, 225, "======= " + ManString.name(this.program[0]) + "  Sub = " + this.submaneuver + " follOffs.x = " + this.followOffset.jdField_x_of_type_Double + "  " + (((AutopilotAI)this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage).bWayPoint ? "Stab WPoint " : "") + (((AutopilotAI)this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage).bStabAltitude ? "Stab ALT " : "") + (((AutopilotAI)this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage).bStabDirection ? "Stab DIR " : "") + (((AutopilotAI)this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage).bStabSpeed ? "Stab SPD " : "   ") + (((Pilot)((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).isDumb() ? "DUMB " : " ") + (((Pilot)((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_Gears_of_type_ComMaddoxIl2FmGear.lgear ? "L " : " ") + (((Pilot)((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).jdField_Gears_of_type_ComMaddoxIl2FmGear.rgear ? "R " : " ") + (((Pilot)((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).TaxiMode ? "TaxiMode" : ""));

    TextScr.output(paramInt + 7, 245, " ====== " + ManString.name(this.program[1]));
    TextScr.output(paramInt + 7, 265, "  ===== " + ManString.name(this.program[2]));
    TextScr.output(paramInt + 7, 285, "   ==== " + ManString.name(this.program[3]));
    TextScr.output(paramInt + 7, 305, "    === " + ManString.name(this.program[4]));
    TextScr.output(paramInt + 7, 325, "     == " + ManString.name(this.program[5]));
    TextScr.output(paramInt + 7, 345, "      = " + ManString.name(this.program[6]) + "  " + (this.target == null ? "" : "TARGET  ") + (this.target_ground == null ? "" : "GROUND  ") + (this.danger == null ? "" : "DANGER  "));

    if ((this.target != null) && (Actor.isValid(this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor))) {
      TextScr.output(paramInt + 1, 365, " AT: (" + this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.name() + ") " + ((this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof Aircraft) ? "" : this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.getClass().getName()));
    }
    if ((this.target_ground != null) && (Actor.isValid(this.target_ground))) {
      TextScr.output(paramInt + 1, 385, " GT: (" + this.target_ground.name() + ") ..." + this.target_ground.getClass().getName());
    }

    TextScr.output(400, 500, "+");
    TextScr.output(400, 400, "|");
    TextScr.output((int)(400.0F + 200.0F * this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl), (int)(500.0F - 200.0F * this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl), "+");
    TextScr.output((int)(400.0F + 200.0F * this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl), 400, "|");
    TextScr.output(250, 780, "followOffset  " + this.followOffset.jdField_x_of_type_Double + "  " + this.followOffset.jdField_y_of_type_Double + "  " + this.followOffset.jdField_z_of_type_Double + "  ");
    if ((this.Group != null) && (this.Group.enemies != null)) TextScr.output(250, 760, "Enemies   " + AirGroupList.length(this.Group.enemies[0]));
    TextScr.output(700, 780, "speedMode   " + this.speedMode);
    if (this.Group != null) TextScr.output(700, 760, "Group task  " + this.Group.grTaskName());
    if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLandingOnShip()) TextScr.output(5, 460, "Landing On Carrier");
    TextScr.output(700, 740, "gattackCounter  " + this.gattackCounter);
    TextScr.output(5, 360, "silence = " + this.silence);
  }

  private void groundAttackGuns(Actor paramActor, float paramFloat)
  {
    float f1;
    if ((this.submaneuver == 0) && (this.sub_Man_Count == 0) && 
      (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1] != null)) {
      f1 = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0]).bulletSpeed();
      if (f1 > 0.01F) this.bullTime = (1.0F / f1);

    }

    this.maxAOA = 15.0F;
    this.jdField_minElevCoeff_of_type_Float = 20.0F;

    switch (this.submaneuver) {
    case 0:
      setCheckGround(true);
      this.rocketsDelay = 0;
      if (this.sub_Man_Count == 0) {
        this.Vtarg.set(paramActor.pos.getAbsPoint());
        paramActor.getSpeed(tmpV3d);
        tmpV3f.jdField_x_of_type_Double = (float)tmpV3d.jdField_x_of_type_Double; tmpV3f.jdField_y_of_type_Double = (float)tmpV3d.jdField_y_of_type_Double; tmpV3f.jdField_z_of_type_Double = (float)tmpV3d.jdField_z_of_type_Double;
        tmpV3f.jdField_z_of_type_Double = 0.0D;
        if (tmpV3f.length() < 9.999999747378752E-005D) {
          tmpV3f.sub(this.Vtarg, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
          tmpV3f.jdField_z_of_type_Double = 0.0D;
        }
        tmpV3f.normalize();
        this.Vtarg.jdField_x_of_type_Double -= tmpV3f.jdField_x_of_type_Double * 1500.0D;
        this.Vtarg.jdField_y_of_type_Double -= tmpV3f.jdField_y_of_type_Double * 1500.0D;
        this.Vtarg.jdField_z_of_type_Double += 400.0D;
        this.constVtarg.set(this.Vtarg);

        Ve.sub(this.constVtarg, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        Ve.normalize();
        this.Vxy.cross(Ve, tmpV3f);
        Ve.sub(tmpV3f);
        this.Vtarg.jdField_z_of_type_Double += 100.0D;
        if (this.Vxy.jdField_z_of_type_Double > 0.0D) {
          this.Vtarg.jdField_x_of_type_Double += Ve.jdField_y_of_type_Double * 1000.0D;
          this.Vtarg.jdField_y_of_type_Double -= Ve.jdField_x_of_type_Double * 1000.0D;
        } else {
          this.Vtarg.jdField_x_of_type_Double -= Ve.jdField_y_of_type_Double * 1000.0D;
          this.Vtarg.jdField_y_of_type_Double += Ve.jdField_x_of_type_Double * 1000.0D;
        }
        this.constVtarg1.set(this.Vtarg);
      }

      Ve.set(this.constVtarg1);
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      f1 = (float)Ve.length();
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      if (Ve.jdField_x_of_type_Double < 0.0D) {
        push(0);
        push(8);
        pop();
        this.dontSwitch = true;
      }
      else {
        Ve.normalize();

        setSpeedMode(4);
        this.smConstSpeed = 100.0F;
        farTurnToDirection();

        this.sub_Man_Count += 1;
        if (f1 >= 300.0F) break;
        this.submaneuver += 1;
        this.gattackCounter += 1;
        this.sub_Man_Count = 0; } break;
    case 1:
      Ve.set(this.constVtarg);
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      f1 = (float)Ve.length();
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();

      setSpeedMode(4);
      this.smConstSpeed = 100.0F;
      farTurnToDirection();

      this.sub_Man_Count += 1;
      if (f1 >= 300.0F) break;
      this.submaneuver += 1;
      this.gattackCounter += 1;
      this.sub_Man_Count = 0; break;
    case 2:
      if (this.rocketsDelay > 0) this.rocketsDelay -= 1;
      if (this.sub_Man_Count > 100) setCheckGround(false);
      P.set(paramActor.pos.getAbsPoint());
      P.jdField_z_of_type_Double += 4.0D;
      Engine.land(); if (Landscape.rayHitHQ(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d, P, P)) {
        push(0);
        push(38);
        pop();
        this.gattackCounter -= 1;
        if (this.gattackCounter < 0) this.gattackCounter = 0;
      }
      Ve.sub(paramActor.pos.getAbsPoint(), this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);

      this.Vtarg.set(Ve);
      f1 = (float)Ve.length();
      paramActor.getSpeed(tmpV3d);
      tmpV3f.jdField_x_of_type_Double = (float)tmpV3d.jdField_x_of_type_Double; tmpV3f.jdField_y_of_type_Double = (float)tmpV3d.jdField_y_of_type_Double; tmpV3f.jdField_z_of_type_Double = (float)tmpV3d.jdField_z_of_type_Double;
      tmpV3f.sub(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      tmpV3f.scale(f1 * this.bullTime * 0.3333F * this.jdField_Skill_of_type_Int);
      Ve.add(tmpV3f);
      float f2 = 0.3F * (f1 - 1000.0F);
      if (f2 < 0.0F) f2 = 0.0F;
      if (f2 > 300.0F) f2 = 300.0F;
      Ve.jdField_z_of_type_Double += f2 + World.cur().rnd.nextFloat(-3.0F, 3.0F) * (3 - this.jdField_Skill_of_type_Int);

      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      if ((f1 < 800.0F) && ((this.shotAtFriend <= 0) || (this.distToFriend > f1)) && 
        (Math.abs(Ve.jdField_y_of_type_Double) < 15.0D) && (Math.abs(Ve.jdField_z_of_type_Double) < 10.0D)) {
        if (f1 < 550.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = true;
        if (f1 < 450.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[1] = true;
        if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2][0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2][(this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2].length - 1)].countBullets() != 0) && (this.rocketsDelay < 1) && (f1 < 500.0F))
        {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[2] = true;
          Voice.speakAttackByRockets((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          this.rocketsDelay += 30;
        }
      }

      if (((this.sub_Man_Count > 200) && (Ve.jdField_x_of_type_Double < 200.0D)) || (this.Alt < 40.0F)) {
        if ((this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel == null) || (!Actor.isAlive(this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor))) {
          Voice.speakEndGattack((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        }
        this.rocketsDelay = 0;
        push(0);
        push(55);
        push(10);
        pop();
        this.dontSwitch = true;
        return;
      }

      Ve.normalize();

      attackTurnToDirection(f1, paramFloat, 4.0F + this.jdField_Skill_of_type_Int * 2.0F);
      setSpeedMode(4);
      this.smConstSpeed = 100.0F;

      break;
    default:
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }
  }

  private void groundAttack(Actor paramActor, float paramFloat)
  {
    setSpeedMode(4);
    this.smConstSpeed = 120.0F;
    float f3 = this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D ? 3.0F : 4.0F;

    int i = 0;
    if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].countBullets() != 0)) {
      i = 1;
    }
    else if ((!(this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik)) && (!(this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) && (!(this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeDiveBomber)))
    {
      set_maneuver(0);
      return;
    }

    Ve.set(paramActor.pos.getAbsPoint());
    float f4 = (float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - (float)Ve.jdField_z_of_type_Double;
    if (f4 < 0.0F) f4 = 0.0F;
    float f5 = (float)Math.sqrt(2.0F * f4 * 0.1019F) + 0.0017F * f4;

    paramActor.getSpeed(tmpV3d);
    if (((paramActor instanceof Aircraft)) && 
      (tmpV3d.length() > 20.0D)) {
      this.target = ((Aircraft)paramActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      set_task(6);
      clear_stack();
      set_maneuver(27);
      this.dontSwitch = true;
      return;
    }

    float f6 = 0.5F;
    if (i != 0) f6 = (f5 + 5.0F) * 0.33333F;
    this.Vtarg.jdField_x_of_type_Double = ((float)tmpV3d.jdField_x_of_type_Double * f6 * this.jdField_Skill_of_type_Int);
    this.Vtarg.jdField_y_of_type_Double = ((float)tmpV3d.jdField_y_of_type_Double * f6 * this.jdField_Skill_of_type_Int);
    this.Vtarg.jdField_z_of_type_Double = ((float)tmpV3d.jdField_z_of_type_Double * f6 * this.jdField_Skill_of_type_Int);
    Ve.add(this.Vtarg);

    Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    Ve.add(0.0D, 0.0D, -0.5F + World.Rnd().nextFloat(-2.0F, 0.8F));
    Vf.set(Ve);

    float f1 = (float)Math.sqrt(Ve.jdField_x_of_type_Double * Ve.jdField_x_of_type_Double + Ve.jdField_y_of_type_Double * Ve.jdField_y_of_type_Double);
    if (i != 0) {
      float f7 = getSpeed() * f5 + 500.0F;
      if (f1 > f7) Ve.jdField_z_of_type_Double += 200.0D; else
        Ve.jdField_z_of_type_Double = 0.0D;
    }
    this.Vtarg.set(Ve);
    this.Vtarg.normalize();
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);

    if (i == 0) {
      groundAttackGuns(paramActor, paramFloat);
      return;
    }

    if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) || ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeStormovik))) {
      groundAttackShallowDive(paramActor, paramFloat);
      return;
    }

    Ve.normalize();
    Vpl.set(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    Vpl.normalize();
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 1.0F;
    if ((f1 + 4.0F * f5 < getSpeed() * f5) && (Ve.jdField_x_of_type_Double > 0.0D) && (Vpl.dot(this.Vtarg) > 0.9800000190734863D))
    {
      if (!this.bombsOut) {
        this.bombsOut = true;
        if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0].countBullets() != 0) && (!(this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] instanceof BombGunPara)))
        {
          Voice.speakAttackByBombs((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        }
      }
      push(0);
      push(55);
      push(48);
      pop();
      Voice.speakEndGattack((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 0.0F;
    }
    if (Ve.jdField_x_of_type_Double < 0.0D) {
      push(0);
      push(55);
      push(10);
      pop();
    }

    if (Math.abs(Ve.jdField_y_of_type_Double) > 0.1000000014901161D)
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-(float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_z_of_type_Double) - 0.016F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
    else {
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-(float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double) - 0.016F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
    }
    if (Math.abs(Ve.jdField_y_of_type_Double) > 0.001000000047497451D)
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-98.0F * (float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double));
    else
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
    if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D) this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double = 0.0D; else this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 1.039999961853027D;

    float f2 = (float)Math.atan2(Ve.jdField_z_of_type_Double, Ve.jdField_x_of_type_Double);

    if ((Math.abs(Ve.jdField_y_of_type_Double) < 0.002000000094994903D) && (Math.abs(Ve.jdField_z_of_type_Double) < 0.002000000094994903D)) f2 = 0.0F;

    if (Ve.jdField_x_of_type_Double < 0.0D) { f2 = 1.0F;
    } else {
      if (f2 * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double > 0.0D) this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double = 0.0D;
      if (f2 < 0.0F) {
        if (getOverload() < 0.1F) f2 = 0.0F;
        if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > 0.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.0F;
      }
      else if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl < 0.0F) { this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.0F; }
    }
    if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > f3) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > f2)) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.2F * paramFloat; else
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.2F * paramFloat;
  }

  private void groundAttackKamikaze(Actor paramActor, float paramFloat)
  {
    float f1;
    if ((this.submaneuver == 0) && (this.sub_Man_Count == 0) && 
      (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1] != null)) {
      f1 = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0]).bulletSpeed();
      if (f1 > 0.01F) this.bullTime = (1.0F / f1);

    }

    this.maxAOA = 15.0F;
    this.jdField_minElevCoeff_of_type_Float = 20.0F;

    switch (this.submaneuver) {
    case 0:
      setCheckGround(true);
      this.rocketsDelay = 0;
      if (this.sub_Man_Count == 0) {
        this.Vtarg.set(paramActor.pos.getAbsPoint());
        tmpV3f.set(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        tmpV3f.jdField_x_of_type_Double += World.Rnd().nextFloat(-100.0F, 100.0F);
        tmpV3f.jdField_y_of_type_Double += World.Rnd().nextFloat(-100.0F, 100.0F);
        tmpV3f.jdField_z_of_type_Double = 0.0D;
        if (tmpV3f.length() < 9.999999747378752E-005D) {
          tmpV3f.sub(this.Vtarg, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
          tmpV3f.jdField_z_of_type_Double = 0.0D;
        }
        tmpV3f.normalize();
        this.Vtarg.jdField_x_of_type_Double -= tmpV3f.jdField_x_of_type_Double * 1500.0D;
        this.Vtarg.jdField_y_of_type_Double -= tmpV3f.jdField_y_of_type_Double * 1500.0D;
        this.Vtarg.jdField_z_of_type_Double += 400.0D;
        this.constVtarg.set(this.Vtarg);

        Ve.sub(this.constVtarg, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        Ve.normalize();
        this.Vxy.cross(Ve, tmpV3f);
        Ve.sub(tmpV3f);
        this.Vtarg.jdField_z_of_type_Double += 100.0D;
        if (this.Vxy.jdField_z_of_type_Double > 0.0D) {
          this.Vtarg.jdField_x_of_type_Double += Ve.jdField_y_of_type_Double * 1000.0D;
          this.Vtarg.jdField_y_of_type_Double -= Ve.jdField_x_of_type_Double * 1000.0D;
        } else {
          this.Vtarg.jdField_x_of_type_Double -= Ve.jdField_y_of_type_Double * 1000.0D;
          this.Vtarg.jdField_y_of_type_Double += Ve.jdField_x_of_type_Double * 1000.0D;
        }
        this.constVtarg1.set(this.Vtarg);
      }

      Ve.set(this.constVtarg1);
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      f1 = (float)Ve.length();
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      if (Ve.jdField_x_of_type_Double < 0.0D) {
        push(0);
        push(8);
        pop();
        this.dontSwitch = true;
      }
      else {
        Ve.normalize();
        setSpeedMode(6);
        farTurnToDirection();

        this.sub_Man_Count += 1;
        if (f1 < 300.0F) {
          this.submaneuver += 1;
          this.gattackCounter += 1;
          this.sub_Man_Count = 0;
        }
        if (this.sub_Man_Count <= 1000) break;
        this.sub_Man_Count = 0; } break;
    case 1:
      setCheckGround(true);
      Ve.set(this.constVtarg);
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      f1 = (float)Ve.length();
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      setSpeedMode(6);
      farTurnToDirection();

      this.sub_Man_Count += 1;
      if (f1 < 300.0F) {
        this.submaneuver += 1;
        this.gattackCounter += 1;
        this.sub_Man_Count = 0;
      }
      if (this.sub_Man_Count <= 700) break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 2:
      setCheckGround(false);
      if (this.rocketsDelay > 0) this.rocketsDelay -= 1;
      if (this.sub_Man_Count > 100) setCheckGround(false);
      Ve.set(paramActor.pos.getAbsPoint());
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      this.Vtarg.set(Ve);
      f1 = (float)Ve.length();
      if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof MXY_7)) {
        Ve.jdField_z_of_type_Double += 0.01D * f1;
        this.Vtarg.jdField_z_of_type_Double += 0.01D * f1;
      }
      paramActor.getSpeed(tmpV3d);
      tmpV3f.jdField_x_of_type_Double = (float)tmpV3d.jdField_x_of_type_Double; tmpV3f.jdField_y_of_type_Double = (float)tmpV3d.jdField_y_of_type_Double; tmpV3f.jdField_z_of_type_Double = (float)tmpV3d.jdField_z_of_type_Double;
      tmpV3f.sub(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      tmpV3f.scale(f1 * this.bullTime * 0.3333F * this.jdField_Skill_of_type_Int);
      Ve.add(tmpV3f);
      float f2 = 0.3F * (f1 - 1000.0F);
      if (f2 < 0.0F) f2 = 0.0F;
      if (f2 > 300.0F) f2 = 300.0F;
      Ve.jdField_z_of_type_Double += f2 + World.cur().rnd.nextFloat(-3.0F, 3.0F) * (3 - this.jdField_Skill_of_type_Int);

      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      if ((f1 < 50.0F) && 
        (Math.abs(Ve.jdField_y_of_type_Double) < 40.0D) && (Math.abs(Ve.jdField_z_of_type_Double) < 30.0D)) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = true;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[1] = true;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[2] = true;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = true;
      }

      if (Ve.jdField_x_of_type_Double < -50.0D) {
        this.rocketsDelay = 0;
        push(0);
        push(55);
        push(10);
        pop();
        this.dontSwitch = true;
        return;
      }

      Ve.normalize();

      attackTurnToDirection(f1, paramFloat, 4.0F + this.jdField_Skill_of_type_Int * 2.0F);
      setSpeedMode(4);
      this.smConstSpeed = 130.0F;

      break;
    default:
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }
  }

  private void groundAttackTinyTim(Actor paramActor, float paramFloat)
  {
    this.maxG = 5.0F; this.maxAOA = 8.0F;
    setSpeedMode(4);
    this.smConstSpeed = 120.0F;
    this.jdField_minElevCoeff_of_type_Float = 20.0F;
    double d;
    switch (this.submaneuver) {
    case 0:
      if (this.sub_Man_Count == 0) {
        this.Vtarg.set(paramActor.pos.getAbsPoint());
        paramActor.getSpeed(tmpV3d);
        if (tmpV3d.length() < 0.5D) tmpV3d.sub(this.Vtarg, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        tmpV3d.normalize();
        this.Vtarg.jdField_x_of_type_Double -= tmpV3d.jdField_x_of_type_Double * 3000.0D;
        this.Vtarg.jdField_y_of_type_Double -= tmpV3d.jdField_y_of_type_Double * 3000.0D;
        this.Vtarg.jdField_z_of_type_Double += 500.0D;
      }
      Ve.sub(this.Vtarg, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      d = Ve.length();
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);

      this.sub_Man_Count += 1;
      if (d < 1000.0D) {
        this.submaneuver += 1;
        this.sub_Man_Count = 0;
      }

      Ve.normalize();
      farTurnToDirection();
      break;
    case 1:
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      this.Vtarg.jdField_z_of_type_Double += 80.0D;
      Ve.sub(this.Vtarg, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      d = Ve.length();
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);

      this.sub_Man_Count += 1;
      if (d < 1500.0D) {
        if ((Math.abs(Ve.jdField_y_of_type_Double) < 40.0D) && (Math.abs(Ve.jdField_z_of_type_Double) < 30.0D)) {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[2] = true;
        }
        push(0);
        push(10);
        push(48);
        pop();
        this.dontSwitch = true;
      }
      if ((d < 500.0D) && (Ve.jdField_x_of_type_Double < 0.0D)) {
        push(0);
        push(10);
        pop();
      }

      Ve.normalize();
      if (Ve.jdField_x_of_type_Double < 0.800000011920929D) turnToDirection(paramFloat); else
        attackTurnToDirection((float)d, paramFloat, 2.0F + this.jdField_Skill_of_type_Int * 1.5F);
      break;
    default:
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }
  }

  private void groundAttackShallowDive(Actor paramActor, float paramFloat)
  {
    this.maxAOA = 10.0F;
    if (!hasBombs()) {
      set_maneuver(0);
      wingman(true);
      return;
    }

    if (this.first) this.RandomVal = (50.0F - World.cur().rnd.nextFloat(0.0F, 100.0F));
    setSpeedMode(4);
    this.smConstSpeed = 120.0F;

    Ve.set(paramActor.pos.getAbsPoint());
    Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    float f1 = (float)(-Ve.jdField_z_of_type_Double);
    if (f1 < 0.0F) f1 = 0.0F;
    Ve.jdField_z_of_type_Double += 250.0D;
    float f2 = (float)Math.sqrt(Ve.jdField_x_of_type_Double * Ve.jdField_x_of_type_Double + Ve.jdField_y_of_type_Double * Ve.jdField_y_of_type_Double) + this.RandomVal * (3 - this.jdField_Skill_of_type_Int);
    if (Ve.jdField_z_of_type_Double < -0.1F * f2) Ve.jdField_z_of_type_Double = (-0.1F * f2);
    if (this.Alt + Ve.jdField_z_of_type_Double < 250.0D) Ve.jdField_z_of_type_Double = (250.0F - this.Alt);
    if (this.Alt < 50.0F) {
      push(10);
      pop();
    }

    Vf.set(Ve);
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 1.0F;

    float f3 = (float)this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double * 0.1019F;
    f3 += (float)Math.sqrt(f3 * f3 + 2.0F * f1 * 0.1019F);
    float f4 = (float)Math.sqrt(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double * this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double + this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double * this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double);
    float f5 = f4 * f3 + 10.0F;
    paramActor.getSpeed(tmpV3d);
    tmpV3d.scale(f3 * 0.35D * this.jdField_Skill_of_type_Int);
    Ve.jdField_x_of_type_Double += (float)tmpV3d.jdField_x_of_type_Double;
    Ve.jdField_y_of_type_Double += (float)tmpV3d.jdField_y_of_type_Double;
    Ve.jdField_z_of_type_Double += (float)tmpV3d.jdField_z_of_type_Double;

    if (f5 >= f2) {
      this.bombsOut = true;
      Voice.speakAttackByBombs((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      setSpeedMode(6);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 0.0F;
      push(0);
      push(55);
      push(48);
      pop();
      this.sub_Man_Count = 0;
    }

    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
    Ve.normalize();
    turnToDirection(paramFloat);
  }

  private void groundAttackDiveBomber(Actor paramActor, float paramFloat)
  {
    this.maxG = 5.0F; this.maxAOA = 10.0F;
    setSpeedMode(6);
    this.maxAOA = 4.0F;
    this.jdField_minElevCoeff_of_type_Float = 20.0F;

    if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] == null) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.getWeaponCount(3) == 0)) {
      if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action == 3) this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
      set_maneuver(0);
      wingman(true);
      return;
    }
    if (this.Alt < 350.0F) {
      this.bombsOut = true;
      Voice.speakAttackByBombs((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      setSpeedMode(6);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 0.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl = 0.0F;
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
      push(0);
      push(8);
      push(2);
      pop();
      this.sub_Man_Count = 0;
    }

    Ve.set(paramActor.pos.getAbsPoint());
    Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    float f5 = (float)(-Ve.jdField_z_of_type_Double);
    float f2 = (float)Math.sqrt(Ve.jdField_x_of_type_Double * Ve.jdField_x_of_type_Double + Ve.jdField_y_of_type_Double * Ve.jdField_y_of_type_Double + Ve.jdField_z_of_type_Double * Ve.jdField_z_of_type_Double);
    float f1 = (float)Math.sqrt(Ve.jdField_x_of_type_Double * Ve.jdField_x_of_type_Double + Ve.jdField_y_of_type_Double * Ve.jdField_y_of_type_Double);
    float f6;
    if ((f1 > 1000.0F) || ((this.submaneuver == 3) && (this.sub_Man_Count > 100))) {
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      paramActor.getSpeed(tmpV3d);
      f6 = 0.0F;
      switch (this.submaneuver) { case 0:
        f6 = f1 / 40.0F + 4.0F + this.Alt / 48.0F;
      case 1:
        f6 = (f1 - this.man1Dist) / (float)this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() + 4.0F + this.Alt / 48.0F;
      case 2:
        f6 = this.Alt / 60.0F;
      case 3:
        f6 = this.Alt / 120.0F;
      }
      f6 *= 0.33333F;
      this.Vtarg.jdField_x_of_type_Double += (float)tmpV3d.jdField_x_of_type_Double * f6 * this.jdField_Skill_of_type_Int;
      this.Vtarg.jdField_y_of_type_Double += (float)tmpV3d.jdField_y_of_type_Double * f6 * this.jdField_Skill_of_type_Int;
      this.Vtarg.jdField_z_of_type_Double += (float)tmpV3d.jdField_z_of_type_Double * f6 * this.jdField_Skill_of_type_Int;
    }

    Ve.set(this.Vtarg);
    Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    float f4 = (float)(-Ve.jdField_z_of_type_Double);
    if (f4 < 0.0F) f4 = 0.0F;
    Ve.add(this.Vxy);
    f5 = (float)(-Ve.jdField_z_of_type_Double);
    f2 = (float)Math.sqrt(Ve.jdField_x_of_type_Double * Ve.jdField_x_of_type_Double + Ve.jdField_y_of_type_Double * Ve.jdField_y_of_type_Double + Ve.jdField_z_of_type_Double * Ve.jdField_z_of_type_Double);
    f1 = (float)Math.sqrt(Ve.jdField_x_of_type_Double * Ve.jdField_x_of_type_Double + Ve.jdField_y_of_type_Double * Ve.jdField_y_of_type_Double);

    if (this.submaneuver < 2) Ve.jdField_z_of_type_Double = 0.0D;
    Vf.set(Ve);
    Ve.normalize();
    Vpl.set(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    Vpl.normalize();

    switch (this.submaneuver) {
    case 0:
      push();
      pop();
      if (f5 < 1200.0F) this.man1Dist = 400.0F;
      else if (f5 > 4500.0F) this.man1Dist = 50.0F; else
        this.man1Dist = (50.0F + 350.0F * (4500.0F - f5) / 3300.0F);
      f6 = 0.01F * f5;
      if (f6 < 10.0F) f6 = 10.0F;
      this.Vxy.set(World.Rnd().nextFloat(-10.0F, 10.0F), World.Rnd().nextFloat(-10.0F, 10.0F), World.Rnd().nextFloat(5.0F, f6));
      this.Vxy.scale(4.0F - this.jdField_Skill_of_type_Int);
      float f7 = 2.0E-005F * f5 * f5;
      if (f7 < 60.0F) f7 = 60.0F;
      if (f7 > 350.0F) f7 = 350.0F;
      this.Vxy.jdField_z_of_type_Double += f7;
      this.submaneuver += 1;
      break;
    case 1:
      setSpeedMode(4);
      this.smConstSpeed = 110.0F;
      if (f1 >= this.man1Dist) break;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl = 1.0F;
      if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 1.0F;
      push();
      push(6);
      safe_pop();
      this.submaneuver += 1; break;
    case 2:
      setSpeedMode(4);
      this.smConstSpeed = 110.0F;
      this.sub_Man_Count += 1;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl = 1.0F;
      if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 1.0F;

      float f3 = this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren();
      if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > -90.0F) {
        f3 -= 180.0F;
        if (f3 < -180.0F) f3 += 360.0F;
      }
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (float)(-0.04F * f3 - 0.5D * getW().jdField_x_of_type_Double);

      if (getOverload() < 4.0F)
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.3F * paramFloat;
      else {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.3F * paramFloat;
      }
      if (((this.sub_Man_Count <= 30) || (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() >= -90.0F)) && (this.sub_Man_Count <= 150)) break;
      this.sub_Man_Count = 0;
      this.submaneuver += 1; break;
    case 3:
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl = 1.0F;
      if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeFighter)) this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 1.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 1.0F;
      setSpeedMode(4);
      this.smConstSpeed = 110.0F;
      this.sub_Man_Count += 1;
      if (this.sub_Man_Count > 80) {
        float f8 = (float)this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double * 0.1019F;
        f8 = f8 + (float)Math.sqrt(f8 * f8 + 2.0F * f4 * 0.1019F) + 0.00035F * f4;
        float f9 = (float)Math.sqrt(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double * this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double + this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double * this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double);
        float f10 = f9 * f8;
        float f11 = 0.2F * (f1 - f10);
        this.Vxy.jdField_z_of_type_Double += f11;
        if (this.Vxy.jdField_z_of_type_Double > 0.7F * f4) this.Vxy.jdField_z_of_type_Double = (0.7F * f4);
      }
      if (((this.sub_Man_Count <= 100) || (this.Alt >= 1000.0F) || (Vpl.dot(Ve) <= 0.9900000095367432D)) && (this.Alt >= 600.0F))
        break;
      this.bombsOut = true;
      Voice.speakAttackByBombs((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.BayDoorControl = 0.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AirBrakeControl = 0.0F;
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
      push(0);
      push(8);
      push(2);
      pop();
    }

    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
    Ve.normalize();
    if (this.submaneuver == 3) attackTurnToDirection(1000.0F, paramFloat, 30.0F);
    else if (this.submaneuver != 2) turnToDirection(paramFloat);
  }

  private void groundAttackTorpedo(Actor paramActor, float paramFloat)
  {
    this.maxG = 5.0F; this.maxAOA = 8.0F;
    setSpeedMode(6);
    this.jdField_minElevCoeff_of_type_Float = 20.0F;

    Ve.set(paramActor.pos.getAbsPoint());
    Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    double d1 = Ve.jdField_x_of_type_Double * Ve.jdField_x_of_type_Double + Ve.jdField_y_of_type_Double * Ve.jdField_y_of_type_Double;
    float f1 = (float)Math.sqrt(d1);

    if (this.first) {
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      this.submaneuver = 0;
    }
    if ((this.submaneuver == 1) && (this.sub_Man_Count == 0)) {
      tmpV3f.set(paramActor.pos.getAbsPoint());
      tmpV3f.sub(this.Vtarg);
      paramActor.getSpeed(tmpV3d);
      if (tmpV3f.length() > 9.999999747378752E-005D) tmpV3f.normalize();
      this.Vxy.set(tmpV3f.jdField_y_of_type_Double * 3000.0D, -tmpV3f.jdField_x_of_type_Double * 3000.0D, 0.0D);
      this.direc = (this.Vxy.dot(Ve) > 0.0D);
      if (this.direc) this.Vxy.scale(-1.0D);
      this.Vtarg.add(this.Vxy);
      this.Vtarg.jdField_x_of_type_Double += tmpV3d.jdField_x_of_type_Double * 80.0D;
      this.Vtarg.jdField_y_of_type_Double += tmpV3d.jdField_y_of_type_Double * 80.0D;
      this.Vtarg.jdField_z_of_type_Double = 80.0D;
      this.TargDevV.set(World.cur().rnd.nextFloat(-10.0F, 10.0F) * (3.8D - this.jdField_Skill_of_type_Int), World.cur().rnd.nextFloat(-10.0F, 10.0F) * (3.8D - this.jdField_Skill_of_type_Int), 0.0D);
    }

    if (this.submaneuver == 2) {
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      paramActor.getSpeed(tmpV3d);
      double d2 = tmpV3d.lengthSquared();

      float f4 = 20.0F;
      if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] instanceof TorpedoGun)) {
        TorpedoGun localTorpedoGun = (TorpedoGun)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0];
        Class localClass = (Class)Property.value(localTorpedoGun.getClass(), "bulletClass", null);
        if (localClass != null)
          f4 = Property.floatValue(localClass, "velocity", 1.0F);
      }
      double d3 = Math.sqrt(0.204D * this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double);
      double d4 = 1.0D * d3 * getSpeed();
      double d5 = (780.0D - d4) / f4;
      this.Vtarg.jdField_x_of_type_Double += (float)(tmpV3d.jdField_x_of_type_Double * d5);
      this.Vtarg.jdField_y_of_type_Double += (float)(tmpV3d.jdField_y_of_type_Double * d5);
      this.Vtarg.jdField_z_of_type_Double = 20.0D;
      if (this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double < 30.0D) this.Vtarg.jdField_z_of_type_Double += 3.0D * (30.0D - this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double);
      this.Vtarg.add(this.TargDevV);
    }

    Ve.set(this.Vtarg);
    Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    float f2 = (float)Math.sqrt(Ve.jdField_x_of_type_Double * Ve.jdField_x_of_type_Double + Ve.jdField_y_of_type_Double * Ve.jdField_y_of_type_Double + Ve.jdField_z_of_type_Double * Ve.jdField_z_of_type_Double);

    Vf.set(Ve);
    float f3 = (float)(-Ve.jdField_z_of_type_Double);
    Vpl.set(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    Vpl.normalize();

    if (this.Alt < 40.0F) {
      if (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < 0.0D) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= (-0.2D + 0.025D * this.Alt);
      if (this.Alt < 8.0F) set_maneuver(2);
    }

    switch (this.submaneuver) {
    case 0:
      this.sub_Man_Count += 1;
      if (this.sub_Man_Count <= 60) break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 1:
      setSpeedMode(6);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = 1.0F;
      this.sub_Man_Count += 1;
      if (f2 >= 1000.0F) break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 2:
      setSpeedMode(6);
      if (f2 >= 800.0F) break;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = true;
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.next();
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 3:
      setSpeedMode(9);
      this.sub_Man_Count += 1;
      if (this.sub_Man_Count <= 90) break;
      this.task = 3;
      push(0);
      push(8);
      pop();
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }

    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
    if (this.submaneuver == 3) {
      if (this.sub_Man_Count < 20)
        Ve.set(1.0D, 0.09000000357627869D, 0.02999999932944775D);
      else {
        Ve.set(1.0D, 0.09000000357627869D, 0.009999999776482582D);
      }
      if (!this.direc) Ve.jdField_y_of_type_Double *= -1.0D;
    }
    Ve.normalize();
    turnToDirection(paramFloat);
  }

  private void groundAttackCassette(Actor paramActor, float paramFloat)
  {
    this.maxG = 5.0F; this.maxAOA = 8.0F;
    setSpeedMode(4);
    this.smConstSpeed = 120.0F;
    this.jdField_minElevCoeff_of_type_Float = 20.0F;

    Ve.set(paramActor.pos.getAbsPoint());
    Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    float f1 = (float)Math.sqrt(Ve.jdField_x_of_type_Double * Ve.jdField_x_of_type_Double + Ve.jdField_y_of_type_Double * Ve.jdField_y_of_type_Double);
    float f4;
    if ((this.submaneuver == 3) && (this.sub_Man_Count > 0) && (this.sub_Man_Count < 45) && (f1 > 200.0F)) {
      tmpV3f.set(this.Vxy);
      f4 = (float)tmpV3f.dot(Ve);
      tmpV3f.scale(-f4);
      tmpV3f.add(Ve);

      float f6 = (float)tmpV3f.length();
      float f5;
      if (f6 > 150.0F) f5 = 7.5F / f6;
      else
        f5 = 0.05F;
      tmpV3f.scale(f5);
      tmpV3f.jdField_z_of_type_Double = 0.0D;
      this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.add(tmpV3f);
    }
    if (f1 <= 200.0F) this.sub_Man_Count = 50;

    if (this.first) {
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      this.submaneuver = 0;
    }

    if ((this.submaneuver == 1) && (this.sub_Man_Count == 0)) {
      tmpV3f.set(paramActor.pos.getAbsPoint());
      paramActor.getSpeed(tmpV3d);
      if (tmpV3d.length() < 0.5D) tmpV3d.set(Ve);
      tmpV3d.normalize();
      this.Vxy.set((float)tmpV3d.jdField_x_of_type_Double, (float)tmpV3d.jdField_y_of_type_Double, (float)tmpV3d.jdField_z_of_type_Double);
      this.Vtarg.jdField_x_of_type_Double -= tmpV3d.jdField_x_of_type_Double * 3000.0D;
      this.Vtarg.jdField_y_of_type_Double -= tmpV3d.jdField_y_of_type_Double * 3000.0D;
      this.Vtarg.jdField_z_of_type_Double += 250.0D;
    }
    if ((this.submaneuver == 2) && (this.sub_Man_Count == 0)) {
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      this.Vtarg.jdField_x_of_type_Double -= this.Vxy.jdField_x_of_type_Double * 1000.0D;
      this.Vtarg.jdField_y_of_type_Double -= this.Vxy.jdField_y_of_type_Double * 1000.0D;
      this.Vtarg.jdField_z_of_type_Double += 50.0D;
    }
    if ((this.submaneuver == 3) && (this.sub_Man_Count == 0)) {
      this.checkGround = false;
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      this.Vtarg.jdField_x_of_type_Double += this.Vxy.jdField_x_of_type_Double * 1000.0D;
      this.Vtarg.jdField_y_of_type_Double += this.Vxy.jdField_y_of_type_Double * 1000.0D;
      this.Vtarg.jdField_z_of_type_Double += 50.0D;
    }

    Ve.set(this.Vtarg);
    Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);

    float f2 = (float)Math.sqrt(Ve.jdField_x_of_type_Double * Ve.jdField_x_of_type_Double + Ve.jdField_y_of_type_Double * Ve.jdField_y_of_type_Double + Ve.jdField_z_of_type_Double * Ve.jdField_z_of_type_Double);

    Vf.set(Ve);
    float f3 = (float)(-Ve.jdField_z_of_type_Double);
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
    Ve.normalize();
    Vpl.set(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    Vpl.normalize();

    if (this.Alt < 10.0F) {
      push(0);
      push(2);
      pop();
    }

    switch (this.submaneuver) {
    case 0:
      setSpeedMode(9);
      this.sub_Man_Count += 1;
      if (this.sub_Man_Count <= 60) break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 1:
      setSpeedMode(9);
      this.sub_Man_Count += 1;
      if (f2 >= 1000.0F) break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 2:
      setSpeedMode(6);
      this.sub_Man_Count += 1;
      if (f2 < 155.0F) {
        this.submaneuver += 1;
        this.sub_Man_Count = 0;
      }
      if (this.sub_Man_Count <= 320) break;
      push(0);
      push(10);
      pop(); break;
    case 3:
      setSpeedMode(6);
      this.sub_Man_Count += 1;
      this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.800000011920929D;
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double *= 0.8999999761581421D;
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      f4 = this.sub_Man_Count;
      if (f4 < 100.0F) f4 = 100.0F;
      if (this.Alt > 45.0F) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double -= 0.002F * (this.Alt - 45.0F) * f4; else
        this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double -= 0.005F * (this.Alt - 45.0F) * f4;
      if (this.Alt < 0.0F) this.Alt = 0.0F;
      if (f2 < 1080.0F + getSpeed() * (float)Math.sqrt(2.0F * this.Alt / 9.81F)) this.bombsOut = true;
      if ((Ve.jdField_x_of_type_Double >= 0.0D) && (f2 >= 350.0F) && (this.sub_Man_Count <= 160)) break;
      push(0);
      push(10);
      push(10);
      pop();
    }

    if (this.submaneuver == 0) {
      Ve.set(1.0D, 0.0D, 0.0D);
    }
    turnToDirection(paramFloat);
  }

  public void goodFighterVsFighter(float paramFloat)
  {
    Ve.sub(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    float f1 = (float)Math.sqrt(Ve.jdField_x_of_type_Double * Ve.jdField_x_of_type_Double + Ve.jdField_y_of_type_Double * Ve.jdField_y_of_type_Double);
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
    float f2 = (float)Ve.length();
    float f3 = 1.0F / f2;
    this.Vtarg.set(Ve);
    this.Vtarg.scale(f3);
    float f4 = (this.jdField_Energy_of_type_Float - this.target.jdField_Energy_of_type_Float) * 0.1019F;
    tmpV3f.sub(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    float f5;
    if (this.sub_Man_Count == 0) {
      f5 = 0.0F;
      if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0].countBullets() > 0))
        f5 = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0]).bulletSpeed();
      else if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0] != null) {
        f5 = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0]).bulletSpeed();
      }
      if (f5 > 0.01F) this.bullTime = (1.0F / f5);
      this.submanDelay = 0;
    }
    float f6;
    float f7;
    if (f1 < 1500.0F) {
      f5 = 0.0F;
      f6 = 0.0F;
      if (this.Vtarg.jdField_x_of_type_Double > -0.2000000029802322D) {
        f5 = 3.0F * ((float)this.Vtarg.jdField_x_of_type_Double + 0.2F);
        this.Vxy.set(tmpV3f);
        this.Vxy.scale(1.0D);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.Vxy);
        this.Vxy.add(Ve);
        this.Vxy.normalize();
        f6 = 10.0F * (float)(this.Vxy.jdField_x_of_type_Double - this.Vtarg.jdField_x_of_type_Double);
        if (f6 < -1.0F) f6 = -1.0F;
        if (f6 > 1.0F) f6 = 1.0F; 
      }
      else {
        f5 = 3.0F * ((float)this.Vtarg.jdField_x_of_type_Double + 0.2F);
      }

      if ((this.submaneuver == 4) && (this.Vtarg.jdField_x_of_type_Double < 0.6000000238418579D) && (f2 < 300.0D)) {
        this.submaneuver = 1;
        this.submanDelay = 30;
      }
      if ((this.submaneuver != 4) && (f4 > 300.0D) && (this.Vtarg.jdField_x_of_type_Double > 0.75D)) {
        this.submaneuver = 4;
        this.submanDelay = 240;
      }

      f7 = 0.0015F * f4 + 0.0006F * f1 + f5 + 0.5F * f6;
      if ((f7 > 0.9F) && (this.submanDelay == 0)) {
        if ((this.Vtarg.jdField_x_of_type_Double > 0.5D) || (f1 * 2.0D < f2)) {
          this.submaneuver = 4;
          this.submanDelay = 240;
        } else {
          this.submaneuver = 3;
          this.submanDelay = 120;
        }

      }
      else if (((f4 > 800.0F) && (this.submaneuver == 0)) || (f4 > 1000.0F)) {
        Ve.set(0.0D, 0.0D, 800.0D);
        if (this.submanDelay == 0) { this.submaneuver = 0; this.submanDelay = 30; }
      }
      else if (((f2 > 450.0F) && (this.submaneuver == 2)) || (f2 > 600.0F)) {
        if (this.submanDelay == 0) { this.submaneuver = 2; this.submanDelay = 60; }
      }
      else if (this.submanDelay == 0) { this.submaneuver = 1; this.submanDelay = 30;
      }

    }
    else if (f1 < 3000.0F) {
      if (this.Vtarg.jdField_x_of_type_Double < 0.5D) {
        if (this.submanDelay == 0) { this.submaneuver = 3; this.submanDelay = 120; }
      }
      else if (((f4 > 600.0F) && (this.submaneuver == 0)) || (f4 > 800.0F)) {
        Ve.set(0.0D, 0.0D, 800.0D);
        if (this.submanDelay == 0) {
          this.submaneuver = 0;
          this.submanDelay = 120;
        }
      }
      else if (((f4 > -200.0F) && (this.submaneuver >= 4)) || (f4 > -100.0F))
      {
        if (this.submanDelay == 0) {
          this.submaneuver = 4;
          this.submanDelay = 120;
        }
      }
      else {
        Ve.set(0.0D, 0.0D, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double);
        if (this.submanDelay == 0) {
          this.submaneuver = 0;
          this.submanDelay = 120;
        }
      }
    }
    else {
      Ve.set(0.0D, 0.0D, 1000.0D);
      if (this.submanDelay == 0) { this.submaneuver = 0; this.submanDelay = 180;
      }
    }

    switch (this.submaneuver) {
    case 0:
      this.target.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(Ve);
      Ve.add(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      tmpV3f.set(Ve);
      tmpV3f.jdField_z_of_type_Double = 0.0D;
      f2 = (float)tmpV3f.length();
      tmpV3f.normalize();
      this.Vtarg.set(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      this.Vtarg.jdField_z_of_type_Double = 0.0D;
      f5 = (float)tmpV3f.dot(this.Vtarg);
      f6 = getSpeed() - f5;
      if (f6 < 10.0F) f6 = 10.0F;
      f7 = f2 / f6;
      if (f7 < 0.0F) f7 = 0.0F;
      tmpV3f.scale(this.Vtarg, f7);
      Ve.add(tmpV3f);

      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      setSpeedMode(9);
      farTurnToDirection();
      this.sub_Man_Count += 1;
      break;
    case 1:
      setSpeedMode(9);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (-0.04F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() + 5.0F));
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
      break;
    case 2:
      setSpeedMode(9);
      tmpOr.setYPR(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw(), 0.0F, 0.0F);
      float f8 = 6.0F;

      if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() > 0.0F) Ve.set(100.0D, -f8, 10.0D); else
        Ve.set(100.0D, f8, 10.0D);
      tmpOr.transform(Ve);
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      farTurnToDirection();
      break;
    case 3:
      this.jdField_minElevCoeff_of_type_Float = 20.0F;
      setSpeedMode(9);
      tmpOr.setYPR(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw(), 0.0F, 0.0F);
      Ve.sub(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      Ve.jdField_z_of_type_Double = 0.0D;
      Ve.normalize();
      Ve.jdField_z_of_type_Double = 0.4D;

      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      attackTurnToDirection(1000.0F, paramFloat, 15.0F);

      break;
    case 4:
      this.jdField_minElevCoeff_of_type_Float = 20.0F;
      boomAttack(paramFloat);
      setSpeedMode(9);
      break;
    default:
      this.jdField_minElevCoeff_of_type_Float = 20.0F;
      fighterVsFighter(paramFloat);
    }
    if (this.submanDelay > 0) this.submanDelay -= 1;
  }

  public void bNZFighterVsFighter(float paramFloat)
  {
    Ve.sub(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
    this.dist = (float)Ve.length();
    float f1 = (float)Math.sqrt(Ve.jdField_x_of_type_Double * Ve.jdField_x_of_type_Double + Ve.jdField_y_of_type_Double * Ve.jdField_y_of_type_Double);
    float f2 = 1.0F / this.dist;
    this.Vtarg.set(Ve);
    this.Vtarg.scale(f2);
    float f3 = (this.jdField_Energy_of_type_Float - this.target.jdField_Energy_of_type_Float) * 0.1019F;
    tmpV3f.sub(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    float f4;
    if (this.sub_Man_Count == 0) {
      f4 = 0.0F;
      if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0].countBullets() > 0))
        f4 = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0]).bulletSpeed();
      else if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0] != null) {
        f4 = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0]).bulletSpeed();
      }
      if (f4 > 0.01F) this.bullTime = (1.0F / f4);
      this.submanDelay = 0;
    }
    float f5;
    float f6;
    if (f1 < 1500.0F) {
      f4 = 0.0F;
      f5 = 0.0F;
      if (this.Vtarg.jdField_x_of_type_Double > -0.2000000029802322D) {
        f4 = 3.0F * ((float)this.Vtarg.jdField_x_of_type_Double + 0.2F);
        this.Vxy.set(tmpV3f);
        this.Vxy.scale(1.0D);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.Vxy);
        this.Vxy.add(Ve);
        this.Vxy.normalize();
        f5 = 20.0F * (float)(this.Vxy.jdField_x_of_type_Double - this.Vtarg.jdField_x_of_type_Double);
        if (f5 < -1.0F) f5 = -1.0F;
        if (f5 > 1.0F) f5 = 1.0F;
      }
      f6 = f3 * 0.0015F + f1 * 0.0006F + f4 + f5;
      if (((f6 > 0.8F) && (this.submaneuver >= 3)) || (f6 > 1.2F)) {
        if (tmpV3f.length() < 100.0D) {
          if (this.submanDelay == 0) { this.submaneuver = 4; this.submanDelay = 120; }
        }
        else if (this.submanDelay == 0) { this.submaneuver = 3; this.submanDelay = 120;
        }
      }
      else if (((f3 > 800.0F) && (this.submaneuver == 0)) || (f3 > 1000.0F)) {
        Ve.set(0.0D, 0.0D, 800.0D);
        if (this.submanDelay == 0) { this.submaneuver = 0; this.submanDelay = 30; }
      }
      else if (((this.dist > 450.0F) && (this.submaneuver == 2)) || (this.dist > 600.0F)) {
        if (this.submanDelay == 0) { this.submaneuver = 2; this.submanDelay = 60; }
      }
      else if (this.submanDelay == 0) { this.submaneuver = 1; this.submanDelay = 30;
      }

    }
    else if (f1 < 3000.0F) {
      if (((f3 > 600.0F) && (this.submaneuver == 0)) || (f3 > 800.0F)) {
        Ve.set(0.0D, 0.0D, 800.0D);
        if (this.submanDelay == 0) { this.submaneuver = 0; this.submanDelay = 120; }
      } else if (((f3 > -200.0F) && (this.submaneuver >= 3)) || (f3 > -100.0F)) {
        if (this.submanDelay == 0) { this.submaneuver = 3; this.submanDelay = 120; }
      } else {
        Ve.set(0.0D, 0.0D, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double);
        if (this.submanDelay == 0) { this.submaneuver = 0; this.submanDelay = 120; }
      }
    } else {
      Ve.set(0.0D, 0.0D, 1000.0D);
      if (this.submanDelay == 0) { this.submaneuver = 0; this.submanDelay = 180;
      }
    }

    switch (this.submaneuver) {
    case 0:
      this.target.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(Ve);
      Ve.add(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      tmpV3f.set(Ve);
      tmpV3f.jdField_z_of_type_Double = 0.0D;
      this.dist = (float)tmpV3f.length();
      tmpV3f.normalize();
      this.Vtarg.set(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      this.Vtarg.jdField_z_of_type_Double = 0.0D;
      f4 = (float)tmpV3f.dot(this.Vtarg);
      f5 = getSpeed() - f4;
      if (f5 < 10.0F) f5 = 10.0F;
      f6 = this.dist / f5;
      if (f6 < 0.0F) f6 = 0.0F;
      tmpV3f.scale(this.Vtarg, f6);
      Ve.add(tmpV3f);

      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      setSpeedMode(9);
      farTurnToDirection();
      this.sub_Man_Count += 1;
      break;
    case 1:
      setSpeedMode(9);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (-0.04F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() + 5.0F));
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
      break;
    case 2:
      setSpeedMode(9);
      tmpOr.setYPR(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getYaw(), 0.0F, 0.0F);
      if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() > 0.0F) Ve.set(100.0D, -6.0D, 10.0D); else
        Ve.set(100.0D, 6.0D, 10.0D);
      tmpOr.transform(Ve);
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      farTurnToDirection();
      break;
    case 3:
      this.jdField_minElevCoeff_of_type_Float = 20.0F;
      fighterVsFighter(paramFloat);
      setSpeedMode(6);
      break;
    default:
      this.jdField_minElevCoeff_of_type_Float = 20.0F;
      fighterVsFighter(paramFloat);
    }
    if (this.submanDelay > 0) this.submanDelay -= 1;
  }

  public void setBomberAttackType(int paramInt)
  {
    float f1;
    if (Actor.isValid(this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor)) f1 = this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.collisionR(); else
      f1 = 15.0F;
    setRandomTargDeviation(0.8F);
    switch (paramInt) {
    case 0:
      this.ApproachV.set(-300.0F + World.Rnd().nextFloat(-100.0F, 100.0F), 0.0D, 600.0F + World.Rnd().nextFloat(-100.0F, 100.0F));
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 150.0F;
      this.TargY = (2.1F * f1); this.TargZ = (3.9F * f1);
      this.TargYS = (0.43F * f1); this.TargZS = (0.43F * f1);
      break;
    case 1:
      this.ApproachV.set(-300.0F + World.Rnd().nextFloat(-100.0F, 100.0F), 0.0D, 600.0F + World.Rnd().nextFloat(-100.0F, 100.0F));
      this.TargV.set(this.target.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[World.Rnd().nextInt(0, this.target.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum() - 1)].getEnginePos());
      this.TargV.jdField_x_of_type_Double -= 1.0D;
      this.ApproachR = 150.0F;
      this.TargY = (2.1F * f1); this.TargZ = (3.9F * f1);
      this.TargYS = (0.43F * f1); this.TargZS = (0.43F * f1);
      break;
    case 2:
      this.ApproachV.set(-600.0F + World.Rnd().nextFloat(-100.0F, 100.0F), 0.0D, 600.0F + World.Rnd().nextFloat(-100.0F, 100.0F));
      this.TargV.set(this.target.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[World.Rnd().nextInt(0, this.target.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum() - 1)].getEnginePos());
      this.TargV.jdField_x_of_type_Double -= 1.0D;
      this.ApproachR = 300.0F;
      this.TargY = (2.1F * f1); this.TargZ = (3.9F * f1);
      this.TargYS = (0.43F * f1); this.TargZS = (0.43F * f1);
      break;
    case 3:
      this.ApproachV.set(2600.0D, 0.0D, 300.0F + World.Rnd().nextFloat(-100.0F, 100.0F));
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 800.0F;
      this.TargY = 25.0F; this.TargZ = 15.0F;
      this.TargYS = 3.0F; this.TargZS = 3.0F;
      break;
    case 4:
      this.ApproachV.set(-400.0F + World.Rnd().nextFloat(-100.0F, 100.0F), 0.0D, -200.0F + World.Rnd().nextFloat(-50.0F, 50.0F));
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 300.0F;
      this.TargY = (2.1F * f1); this.TargZ = (1.3F * f1);
      this.TargYS = (0.26F * f1); this.TargZS = (0.26F * f1);
      break;
    case 5:
      this.ApproachV.set(0.0D, 0.0D, 0.0D);
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 600.0F;
      this.TargY = 25.0F; this.TargZ = 12.0F;
      this.TargYS = (0.26F * f1); this.TargZS = (0.26F * f1);
      break;
    case 6:
      this.ApproachV.set(600.0D, 600 - World.Rnd().nextInt(0, 1) * 1200 + World.Rnd().nextFloat(-100.0F, 100.0F), 300.0D);
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 300.0F;
      this.TargY = (2.1F * f1); this.TargZ = (1.2F * f1);
      this.TargYS = (0.26F * f1); this.TargZS = (0.26F * f1);
      break;
    case 7:
      this.ApproachV.set(-1000.0F + World.Rnd().nextFloat(-200.0F, 200.0F), 0.0D, 100.0F + World.Rnd().nextFloat(-50.0F, 50.0F));
      this.TargV.set(this.target.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[World.Rnd().nextInt(0, this.target.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum() - 1)].getEnginePos());
      this.ApproachR = 200.0F;
      this.TargY = 10.0F; this.TargZ = 10.0F;
      this.TargYS = 2.0F; this.TargZS = 2.0F;
      break;
    case 8:
      this.ApproachV.set(-1000.0F + World.Rnd().nextFloat(-200.0F, 200.0F), 0.0D, 100.0F + World.Rnd().nextFloat(-50.0F, 50.0F));
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 200.0F;
      this.TargY = (2.1F * f1); this.TargZ = (3.9F * f1);
      this.TargYS = (0.2F * f1); this.TargZS = (0.2F * f1);
      break;
    case 9:
      this.ApproachV.set(-600.0F + World.Rnd().nextFloat(-100.0F, 100.0F), 0.0D, 600.0F + World.Rnd().nextFloat(-100.0F, 100.0F));
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 300.0F;
      this.TargY = (2.1F * f1); this.TargZ = (3.9F * f1);
      this.TargYS = (0.2F * f1); this.TargZS = (0.2F * f1);
      break;
    case 10:
      this.ApproachV.set(-600.0F + World.Rnd().nextFloat(-100.0F, 100.0F), 0.0D, -300.0F + World.Rnd().nextFloat(-100.0F, 100.0F));
      this.TargV.set(100.0D, 0.0D, -400.0D);
      this.ApproachR = 300.0F;
      this.TargY = (2.1F * f1); this.TargZ = (3.9F * f1);
      this.TargYS = (0.43F * f1); this.TargZS = (0.43F * f1);
      break;
    default:
      this.ApproachV.set(-1000.0F + World.Rnd().nextFloat(-200.0F, 200.0F), 0.0D, 100.0F + World.Rnd().nextFloat(-50.0F, 50.0F));
      this.TargV.set(this.target.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[World.Rnd().nextInt(0, this.target.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum() - 1)].getEnginePos());
      this.ApproachR = 200.0F;
      this.TargY = 10.0F; this.TargZ = 10.0F;
      this.TargYS = 2.0F; this.TargZS = 2.0F;
    }

    float f2 = 0.0F;
    if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0].countBullets() > 0))
      f2 = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0]).bulletSpeed();
    else if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0] != null) {
      f2 = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0]).bulletSpeed();
    }
    if (f2 > 0.01F) this.bullTime = (1.0F / f2);
  }

  public void fighterVsBomber(float paramFloat)
  {
    this.maxAOA = 15.0F;
    tmpOr.setAT0(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    float f1;
    float f2;
    switch (this.submaneuver) {
    case 0:
      this.jdField_minElevCoeff_of_type_Float = 4.0F;
      this.rocketsDelay = 0; this.bulletDelay = 0;
      double d1 = 0.0001D * this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double;

      Ve.sub(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      tmpOr.transformInv(Ve);
      this.scaledApproachV.set(this.ApproachV);
      this.scaledApproachV.jdField_x_of_type_Double -= 700.0D * d1;
      this.scaledApproachV.jdField_z_of_type_Double += 500.0D * d1;
      Ve.add(this.scaledApproachV);
      Ve.normalize();
      tmpV3f.scale(this.scaledApproachV, -1.0D);
      tmpV3f.normalize();
      double d2 = Ve.dot(tmpV3f);
      Ve.set(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      Ve.normalize();
      tmpV3f.set(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      tmpV3f.normalize();
      double d3 = Ve.dot(tmpV3f);

      Ve.set(this.scaledApproachV);
      Ve.jdField_x_of_type_Double += 60.0D * (2.0D * (1.0D - d2) + 4.0D * (1.0D - d3));

      tmpOr.transform(Ve);
      Ve.add(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      double d4 = Ve.jdField_z_of_type_Double;
      tmpV3f.set(Ve);
      tmpV3f.jdField_z_of_type_Double = 0.0D;
      f1 = (float)tmpV3f.length();
      f2 = 0.55F + 0.15F * this.jdField_Skill_of_type_Int;
      tmpV3f.normalize();
      this.Vtarg.set(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      this.Vtarg.jdField_z_of_type_Double = 0.0D;
      float f3 = (float)tmpV3f.dot(this.Vtarg);
      float f4 = getSpeed() - f3;
      if (f4 < 10.0F) f4 = 10.0F;
      float f5 = f1 / f4;
      if (f5 < 0.0F) f5 = 0.0F;
      tmpV3f.scale(this.Vtarg, f5 * f2);
      Ve.add(tmpV3f);

      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      if (d4 > -200.0D) {
        setSpeedMode(9);
      } else {
        setSpeedMode(4);
        this.smConstSpeed = 180.0F;
      }

      attackTurnToDirection(f1, paramFloat, 10.0F * (float)(1.0D + d1));

      this.sub_Man_Count += 1;
      if ((f1 >= this.ApproachR * (1.0D + d1)) || (d4 >= 200.0D)) break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 1:
      this.jdField_minElevCoeff_of_type_Float = 20.0F;
      Ve.set(this.TargV);
      this.target.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(Ve);
      Ve.add(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      f1 = (float)Ve.length();
      f2 = 0.55F + 0.15F * this.jdField_Skill_of_type_Int;
      tmpV3f.sub(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      float f6 = f1 * this.bullTime * 0.0025F;
      if (f6 > 0.05F)
        f6 = 0.05F;
      tmpV3f.scale(f1 * f6 * f2);
      Ve.add(tmpV3f);

      this.Vtarg.set(Ve);
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      ((Maneuver)this.target).incDangerAggressiveness(1, (float)Ve.jdField_x_of_type_Double, f1, this);

      if ((f1 > 3200.0F) || (this.Vtarg.jdField_z_of_type_Double > 1500.0D)) {
        this.submaneuver = 0;
        this.sub_Man_Count = 0;
        set_maneuver(0);
      }
      else {
        if (Ve.jdField_x_of_type_Double < 0.300000011920929D) {
          this.Vtarg.jdField_z_of_type_Double += 0.012F * this.jdField_Skill_of_type_Int * (800.0F + f1) * (0.300000011920929D - Ve.jdField_x_of_type_Double);
          Ve.set(this.Vtarg);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
          Ve.normalize();
        }

        attackTurnToDirection(f1, paramFloat, 10.0F + this.jdField_Skill_of_type_Int * 8.0F);
        setSpeedMode(4);
        this.smConstSpeed = 180.0F;

        if (f1 >= 400.0F) break;
        this.submaneuver += 1;
        this.sub_Man_Count = 0; } break;
    case 2:
      this.jdField_minElevCoeff_of_type_Float = 20.0F;
      if (this.rocketsDelay > 0) this.rocketsDelay -= 1;
      if (this.bulletDelay > 0) this.bulletDelay -= 1;
      if (this.bulletDelay == 120) this.bulletDelay = 0;
      setRandomTargDeviation(0.8F);
      Ve.set(this.TargV);
      Ve.add(this.TargDevV);
      this.target.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(Ve);
      Ve.add(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      this.Vtarg.set(Ve);
      f1 = (float)Ve.length();
      f2 = 0.55F + 0.15F * this.jdField_Skill_of_type_Int;
      float f7 = 0.0002F * this.jdField_Skill_of_type_Int;
      tmpV3f.sub(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      Vpl.set(tmpV3f);
      tmpV3f.scale(f1 * (this.bullTime + f7) * f2);
      Ve.add(tmpV3f);
      tmpV3f.set(Vpl);
      tmpV3f.scale(f1 * this.bullTime * f2);
      this.Vtarg.add(tmpV3f);

      if ((f1 > 4000.0F) || (this.Vtarg.jdField_z_of_type_Double > 2000.0D)) {
        this.submaneuver = 0;
        this.sub_Man_Count = 0;
        set_maneuver(0);
      }
      else
      {
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.Vtarg);
        if ((this.Vtarg.jdField_x_of_type_Double > 0.0D) && (f1 < 500.0F) && ((this.shotAtFriend <= 0) || (this.distToFriend > f1)) && 
          (Math.abs(this.Vtarg.jdField_y_of_type_Double) < this.TargY - this.TargYS * this.jdField_Skill_of_type_Int) && (Math.abs(this.Vtarg.jdField_z_of_type_Double) < this.TargZ - this.TargZS * this.jdField_Skill_of_type_Int) && (this.bulletDelay < 120))
        {
          if (!(this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof KI_46_OTSUHEI))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = true;
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[1] = true;
          this.bulletDelay += 2;
          if (this.bulletDelay >= 118) {
            int i = (int)(this.target.getW().length() * 150.0D);
            if (i > 60)
              i = 60;
            this.bulletDelay += World.Rnd().nextInt(i * this.jdField_Skill_of_type_Int, 2 * i * this.jdField_Skill_of_type_Int);
          }

          if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2][0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2][(this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2].length - 1)].countBullets() != 0) && (this.rocketsDelay < 1))
          {
            tmpV3f.sub(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
            this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(tmpV3f);
            if ((Math.abs(tmpV3f.jdField_y_of_type_Double) < this.TargY - this.TargYS * this.jdField_Skill_of_type_Int) && (Math.abs(tmpV3f.jdField_z_of_type_Double) < this.TargZ - this.TargZS * this.jdField_Skill_of_type_Int))
            {
              this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[2] = true;
            }Voice.speakAttackByRockets((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            this.rocketsDelay += 120;
          }
          ((Maneuver)this.target).incDangerAggressiveness(1, 0.8F, f1, this);

          ((Pilot)this.target).setAsDanger(this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        }

        this.Vtarg.set(Ve);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
        Ve.normalize();
        ((Maneuver)this.target).incDangerAggressiveness(1, (float)Ve.jdField_x_of_type_Double, f1, this);

        if (Ve.jdField_x_of_type_Double < 0.300000011920929D) {
          this.Vtarg.jdField_z_of_type_Double += 0.01F * this.jdField_Skill_of_type_Int * (800.0F + f1) * (0.300000011920929D - Ve.jdField_x_of_type_Double);
          Ve.set(this.Vtarg);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
          Ve.normalize();
        }

        attackTurnToDirection(f1, paramFloat, 10.0F + this.jdField_Skill_of_type_Int * 8.0F);
        if (this.target.getSpeed() > 70.0F) {
          setSpeedMode(2);
          this.tailForStaying = this.target;
          this.tailOffset.set(-20.0D, 0.0D, 0.0D);
        }
        else {
          setSpeedMode(4);
          this.smConstSpeed = 70.0F;
        }

        if (this.strikeEmer) {
          Vpl.sub(this.strikeTarg.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
          tmpV3f.set(Vpl);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(tmpV3f);
          if (tmpV3f.jdField_x_of_type_Double < 0.0D)
            return;
          tmpV3f.sub(this.strikeTarg.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
          tmpV3f.scale(0.699999988079071D);
          Vpl.add(tmpV3f);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Vpl);
          push();
          if (Vpl.jdField_z_of_type_Double < 0.0D) {
            push(0);
            push(8);
            push(60);
          }
          else {
            push(0);
            push(8);
            push(61);
          }
          pop();
          this.strikeEmer = false;
          this.submaneuver = 0;
          this.sub_Man_Count = 0;
        }
        if (this.sub_Man_Count <= 600) break;
        push(8);
        pop(); } break;
    default:
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
      set_maneuver(0);
    }
  }

  public void fighterUnderBomber(float paramFloat)
  {
    this.maxAOA = 15.0F;
    float f1;
    float f2;
    switch (this.submaneuver) {
    case 0:
      this.rocketsDelay = 0; this.bulletDelay = 0;
      Ve.set(this.ApproachV);
      this.target.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(Ve);
      Ve.add(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      tmpV3f.set(Ve);
      tmpV3f.jdField_z_of_type_Double = 0.0D;
      f1 = (float)tmpV3f.length();
      f2 = 0.55F + 0.15F * this.jdField_Skill_of_type_Int;
      tmpV3f.normalize();
      this.Vtarg.set(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      this.Vtarg.jdField_z_of_type_Double = 0.0D;
      float f3 = (float)tmpV3f.dot(this.Vtarg);
      float f4 = getSpeed() - f3;
      if (f4 < 10.0F) f4 = 10.0F;
      float f5 = f1 / f4;
      if (f5 < 0.0F) f5 = 0.0F;
      tmpV3f.scale(this.Vtarg, f5 * f2);
      Ve.add(tmpV3f);

      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      setSpeedMode(4);
      this.smConstSpeed = 140.0F;
      farTurnToDirection();

      this.sub_Man_Count += 1;
      if (f1 >= this.ApproachR) break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 1:
      Ve.set(this.TargV);
      this.target.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(Ve);
      Ve.add(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      f1 = (float)Ve.length();
      f2 = 0.55F + 0.15F * this.jdField_Skill_of_type_Int;
      tmpV3f.sub(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      float f6 = f1 * this.bullTime * 0.0025F;
      if (f6 > 0.02F) f6 = 0.02F;
      tmpV3f.scale(f1 * f6 * f2);
      Ve.add(tmpV3f);

      this.Vtarg.set(Ve);
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      ((Maneuver)this.target).incDangerAggressiveness(1, (float)Ve.jdField_x_of_type_Double, f1, this);

      if ((f1 > 3200.0F) || (this.Vtarg.jdField_z_of_type_Double > 1500.0D)) {
        this.submaneuver = 0;
        this.sub_Man_Count = 0;
      }
      else {
        if (Ve.jdField_x_of_type_Double < 0.300000011920929D) {
          this.Vtarg.jdField_z_of_type_Double += 0.01F * this.jdField_Skill_of_type_Int * (800.0F + f1) * (0.300000011920929D - Ve.jdField_x_of_type_Double);
          Ve.set(this.Vtarg);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
          Ve.normalize();
        }

        attackTurnToDirection(f1, paramFloat, 10.0F);
        if (this.target.getSpeed() > 120.0F) {
          setSpeedMode(2);
          this.tailForStaying = this.target;
        } else {
          setSpeedMode(4);
          this.smConstSpeed = 120.0F;
        }

        if (f1 >= 400.0F) break;
        this.submaneuver += 1;
        this.sub_Man_Count = 0; } break;
    case 2:
      setCheckStrike(false);
      if (this.rocketsDelay > 0) this.rocketsDelay -= 1;
      if (this.bulletDelay > 0) this.bulletDelay -= 1;
      if (this.bulletDelay == 120) this.bulletDelay = 0;
      setRandomTargDeviation(0.8F);
      Ve.set(this.TargV);
      Ve.add(this.TargDevV);
      this.target.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(Ve);
      Ve.add(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      f1 = (float)Ve.length();
      f2 = 0.55F + 0.15F * this.jdField_Skill_of_type_Int;
      tmpV3f.sub(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      tmpV3f.scale(f1 * this.bullTime * f2);
      Ve.add(tmpV3f);
      this.Vtarg.set(Ve);
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);

      if ((f1 > 4000.0F) || (this.Vtarg.jdField_z_of_type_Double > 2000.0D)) {
        this.submaneuver = 0;
        this.sub_Man_Count = 0;
      }
      else {
        if ((this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[1] < 100) && (f1 < 330.0F) && (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) < 3.0F)) {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = true;
        }

        Ve.normalize();

        if (Ve.jdField_x_of_type_Double < 0.300000011920929D) {
          this.Vtarg.jdField_z_of_type_Double += 0.01F * this.jdField_Skill_of_type_Int * (800.0F + f1) * (0.300000011920929D - Ve.jdField_x_of_type_Double);
          Ve.set(this.Vtarg);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
          Ve.normalize();
        }

        attackTurnToDirection(f1, paramFloat, 6.0F + this.jdField_Skill_of_type_Int * 3.0F);

        setSpeedMode(1);
        this.tailForStaying = this.target;
        this.tailOffset.set(-190.0D, 0.0D, 0.0D);

        if ((this.sub_Man_Count <= 10000) && (f1 >= 240.0F)) break;
        push(9);
        pop(); } break;
    default:
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }
  }

  public void fighterVsFighter(float paramFloat) {
    if (this.sub_Man_Count == 0) {
      f1 = 0.0F;
      if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0].countBullets() > 0))
        f1 = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0]).bulletSpeed();
      else if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0] != null) {
        f1 = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0]).bulletSpeed();
      }
      if (f1 > 0.01F) this.bullTime = (1.0F / f1);
    }
    this.maxAOA = 15.0F;
    if (this.rocketsDelay > 0) this.rocketsDelay -= 1;
    if (this.bulletDelay > 0) this.bulletDelay -= 1;
    if (this.bulletDelay == 122) this.bulletDelay = 0;

    Ve.sub(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    setRandomTargDeviation(0.3F);
    Ve.add(this.TargDevV);
    this.Vtarg.set(Ve);
    float f1 = (float)Ve.length();
    float f2 = 0.55F + 0.15F * this.jdField_Skill_of_type_Int;
    float f3 = 0.0002F * this.jdField_Skill_of_type_Int;
    tmpV3f.sub(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    Vpl.set(tmpV3f);
    tmpV3f.scale(f1 * (this.bullTime + f3) * f2);
    Ve.add(tmpV3f);
    tmpV3f.set(Vpl);
    tmpV3f.scale(f1 * this.bullTime * f2);
    this.Vtarg.add(tmpV3f);

    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Vpl);
    float f4 = (float)(-Vpl.jdField_x_of_type_Double);
    if (f4 < 0.001F) f4 = 0.001F;
    Vpl.normalize();
    if ((Vpl.jdField_x_of_type_Double < -0.9399999976158142D) && (f1 / f4 < 1.5F * (3 - this.jdField_Skill_of_type_Int))) {
      push();
      set_maneuver(14);
      return;
    }

    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.Vtarg);
    if ((this.Vtarg.jdField_x_of_type_Double > 0.0D) && (f1 < 500.0F) && ((this.shotAtFriend <= 0) || (this.distToFriend > f1))) {
      if ((Math.abs(this.Vtarg.jdField_y_of_type_Double) < 13.0D) && (Math.abs(this.Vtarg.jdField_z_of_type_Double) < 13.0D)) {
        ((Maneuver)this.target).incDangerAggressiveness(1, 0.95F, f1, this);
      }
      if ((Math.abs(this.Vtarg.jdField_y_of_type_Double) < 12.5F - 3.5F * this.jdField_Skill_of_type_Int) && (Math.abs(this.Vtarg.jdField_z_of_type_Double) < 12.5D - 3.5D * this.jdField_Skill_of_type_Int) && (this.bulletDelay < 120)) {
        if (!(this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof KI_46_OTSUHEI)) this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = true;
        this.bulletDelay += 2;
        if (this.bulletDelay >= 118) {
          int i = (int)(this.target.getW().length() * 150.0D);
          if (i > 60) i = 60;
          this.bulletDelay += World.Rnd().nextInt(i * this.jdField_Skill_of_type_Int, 2 * i * this.jdField_Skill_of_type_Int);
        }
        if (f1 < 400.0F) {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[1] = true;
          if ((f1 < 100.0F) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2][0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2][(this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2].length - 1)].countBullets() != 0) && (this.rocketsDelay < 1))
          {
            tmpV3f.sub(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
            this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(tmpV3f);
            if ((Math.abs(tmpV3f.jdField_y_of_type_Double) < 4.0D) && (Math.abs(tmpV3f.jdField_z_of_type_Double) < 4.0D))
              this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[2] = true;
            Voice.speakAttackByRockets((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
            this.rocketsDelay += 120;
          }
        }
        ((Pilot)this.target).setAsDanger(this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      }
    }

    this.Vtarg.set(Ve);
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
    Ve.normalize();
    ((Maneuver)this.target).incDangerAggressiveness(1, (float)Ve.jdField_x_of_type_Double, f1, this);

    if (Ve.jdField_x_of_type_Double < 0.300000011920929D) {
      this.Vtarg.jdField_z_of_type_Double += 0.01F * this.jdField_Skill_of_type_Int * (800.0F + f1) * (0.300000011920929D - Ve.jdField_x_of_type_Double);
      Ve.set(this.Vtarg);
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
    }

    attackTurnToDirection(f1, paramFloat, 10.0F + this.jdField_Skill_of_type_Int * 8.0F);
    if ((this.Alt > 150.0F) && (Ve.jdField_x_of_type_Double > 0.6D) && (f1 < 70.0D)) {
      setSpeedMode(1);
      this.tailForStaying = this.target;
      this.tailOffset.set(-20.0D, 0.0D, 0.0D);
    } else {
      setSpeedMode(9);
    }
  }

  public void boomAttack(float paramFloat)
  {
    if (this.sub_Man_Count == 0) {
      f1 = 0.0F;
      if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0].countBullets() > 0))
        f1 = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[1][0]).bulletSpeed();
      else if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0] != null) {
        f1 = ((GunGeneric)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[0][0]).bulletSpeed();
      }
      if (f1 > 0.01F) this.bullTime = (1.0F / f1);
    }
    this.maxAOA = 15.0F;
    if (this.rocketsDelay > 0) this.rocketsDelay -= 1;
    if (this.bulletDelay > 0) this.bulletDelay -= 1;
    if (this.bulletDelay == 122) this.bulletDelay = 0;

    Ve.sub(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    setRandomTargDeviation(0.3F);
    Ve.add(this.TargDevV);
    this.Vtarg.set(Ve);
    float f1 = (float)Ve.length();
    float f2 = 0.55F + 0.15F * this.jdField_Skill_of_type_Int;
    float f3 = 0.000333F * this.jdField_Skill_of_type_Int;
    tmpV3f.sub(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    Vpl.set(tmpV3f);
    tmpV3f.scale(f1 * (this.bullTime + f3) * f2);
    Ve.add(tmpV3f);
    tmpV3f.set(Vpl);
    tmpV3f.scale(f1 * this.bullTime * f2);
    this.Vtarg.add(tmpV3f);

    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Vpl);
    float f4 = (float)(-Vpl.jdField_x_of_type_Double);
    if (f4 < 0.001F) f4 = 0.001F;
    Vpl.normalize();
    if ((Vpl.jdField_x_of_type_Double < -0.9399999976158142D) && (f1 / f4 < 1.5F * (3 - this.jdField_Skill_of_type_Int))) {
      push();
      set_maneuver(14);
      return;
    }

    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(this.Vtarg);
    if ((this.Vtarg.jdField_x_of_type_Double > 0.0D) && (f1 < 700.0F) && ((this.shotAtFriend <= 0) || (this.distToFriend > f1)) && 
      (Math.abs(this.Vtarg.jdField_y_of_type_Double) < 15.5F - 3.5F * this.jdField_Skill_of_type_Int) && (Math.abs(this.Vtarg.jdField_z_of_type_Double) < 15.5D - 3.5D * this.jdField_Skill_of_type_Int) && (this.bulletDelay < 120)) {
      ((Maneuver)this.target).incDangerAggressiveness(1, 0.8F, f1, this);
      if (!(this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof KI_46_OTSUHEI)) this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = true;
      this.bulletDelay += 2;
      if (this.bulletDelay >= 118) {
        int i = (int)(this.target.getW().length() * 150.0D);
        if (i > 60) i = 60;
        this.bulletDelay += World.Rnd().nextInt(i * this.jdField_Skill_of_type_Int, 2 * i * this.jdField_Skill_of_type_Int);
      }
      if (f1 < 500.0F) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[1] = true;
        if ((f1 < 100.0F) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2][0] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2][(this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[2].length - 1)].countBullets() != 0) && (this.rocketsDelay < 1))
        {
          tmpV3f.sub(this.target.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
          this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(tmpV3f);
          if ((Math.abs(tmpV3f.jdField_y_of_type_Double) < 4.0D) && (Math.abs(tmpV3f.jdField_z_of_type_Double) < 4.0D))
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[2] = true;
          Voice.speakAttackByRockets((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          this.rocketsDelay += 120;
        }
      }
      ((Pilot)this.target).setAsDanger(this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    }

    this.Vtarg.set(Ve);
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
    Ve.normalize();
    ((Maneuver)this.target).incDangerAggressiveness(1, (float)Ve.jdField_x_of_type_Double, f1, this);

    if (Ve.jdField_x_of_type_Double < 0.8999999761581421D) {
      this.Vtarg.jdField_z_of_type_Double += 0.03F * this.jdField_Skill_of_type_Int * (800.0F + f1) * (0.8999999761581421D - Ve.jdField_x_of_type_Double);
      Ve.set(this.Vtarg);
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
    }

    attackTurnToDirection(f1, paramFloat, 10.0F + this.jdField_Skill_of_type_Int * 8.0F);
  }

  private void turnToDirection(float paramFloat)
  {
    if (Math.abs(Ve.jdField_y_of_type_Double) > 0.1000000014901161D)
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-(float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_z_of_type_Double) - 0.016F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
    else {
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-(float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double) - 0.016F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
    }
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-10.0F * (float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double));
    if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D) this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double = 0.0D; else this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 1.039999961853027D;

    float f = (float)Math.atan2(Ve.jdField_z_of_type_Double, Ve.jdField_x_of_type_Double);

    if ((Math.abs(Ve.jdField_y_of_type_Double) < 0.002000000094994903D) && (Math.abs(Ve.jdField_z_of_type_Double) < 0.002000000094994903D)) f = 0.0F;

    if (Ve.jdField_x_of_type_Double < 0.0D) { f = 1.0F;
    } else {
      if (f * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double > 0.0D) this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double = 0.0D;
      if (f < 0.0F) {
        if (getOverload() < 0.1F) f = 0.0F;
        if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > 0.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.0F;
      }
      else if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl < 0.0F) { this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 0.0F; }
    }
    if ((getOverload() > this.maxG) || (this.jdField_AOA_of_type_Float > this.maxAOA) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > f)) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.3F * paramFloat; else
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.3F * paramFloat;
  }

  private void farTurnToDirection() {
    farTurnToDirection(4.0F);
  }
  private void farTurnToDirection(float paramFloat) {
    Vpl.set(1.0D, 0.0D, 0.0D);
    tmpV3f.cross(Vpl, Ve);
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(tmpV3f);

    this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-10.0F * (float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double) + 1.0F * (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double);

    float f7 = getSpeed() / this.jdField_Vmax_of_type_Float * 45.0F;
    if (f7 > 85.0F) f7 = 85.0F;
    float f8 = (float)Ve.jdField_x_of_type_Double;
    if (f8 < 0.0F) f8 = 0.0F;
    float f1;
    if (tmpV3f.jdField_z_of_type_Double >= 0.0D)
      f1 = -0.02F * (f7 + this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) * (1.0F - f8) - 0.05F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() + 1.0F * (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double;
    else {
      f1 = -0.02F * (-f7 + this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) * (1.0F - f8) + 0.05F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() + 1.0F * (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double;
    }
    float f2 = -(float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double) - 0.016F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() + 1.0F * (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double;

    float f4 = -0.1F * (getAOA() - 10.0F) + 0.5F * (float)getW().jdField_y_of_type_Double;
    float f5;
    if (Ve.jdField_z_of_type_Double > 0.0D)
      f5 = -0.1F * (getAOA() - paramFloat) + 0.5F * (float)getW().jdField_y_of_type_Double;
    else {
      f5 = 1.0F * (float)Ve.jdField_z_of_type_Double + 0.5F * (float)getW().jdField_y_of_type_Double;
    }
    if (Math.abs(Ve.jdField_y_of_type_Double) < 0.002000000094994903D) {
      f2 = 0.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
    }

    float f9 = 1.0F - Math.abs((float)Ve.jdField_y_of_type_Double) * 2.0F;
    if ((f9 < 0.0F) || (Ve.jdField_x_of_type_Double < 0.0D)) f9 = 0.0F;
    float f3 = f9 * f2 + (1.0F - f9) * f1;
    float f6 = f9 * f5 + (1.0F - f9) * f4;

    this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = f3;
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = f6;
  }

  private void attackTurnToDirection(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (Ve.jdField_x_of_type_Double < 0.009999999776482582D) Ve.jdField_x_of_type_Double = 0.009999999776482582D;
    if (this.sub_Man_Count == 0) this.oldVe.set(Ve);
    float f1;
    if (Ve.jdField_x_of_type_Double > 0.949999988079071D)
    {
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (float)(-30.0D * Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double) + 1.5D * (Ve.jdField_y_of_type_Double - this.oldVe.jdField_y_of_type_Double));

      if ((Ve.jdField_z_of_type_Double > 0.0D) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl > 0.9F)) {
        f1 = (float)(10.0D * Math.atan2(Ve.jdField_z_of_type_Double, Ve.jdField_x_of_type_Double) + 6.0D * (Ve.jdField_z_of_type_Double - this.oldVe.jdField_z_of_type_Double));

        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-30.0F * (float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double) - 0.02F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() + 5.0F * (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double);
      } else {
        f1 = (float)(5.0D * Math.atan2(Ve.jdField_z_of_type_Double, Ve.jdField_x_of_type_Double) + 6.0D * (Ve.jdField_z_of_type_Double - this.oldVe.jdField_z_of_type_Double));
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-5.0F * (float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double) - 0.02F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() + 5.0F * (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double);
      }

      if (Ve.jdField_x_of_type_Double > 1.0F - 0.005F * this.jdField_Skill_of_type_Int) {
        tmpOr.set(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation);
        tmpOr.increment((float)Math.toDegrees(Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double)), (float)Math.toDegrees(Math.atan2(Ve.jdField_z_of_type_Double, Ve.jdField_x_of_type_Double)), 0.0F);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.interpolate(tmpOr, 0.1F);
      }

      if (Math.abs(this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl - f1) < paramFloat3 * paramFloat2) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = f1;
      else if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl < f1)
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += paramFloat3 * paramFloat2;
      else {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= paramFloat3 * paramFloat2;
      }

    }
    else
    {
      if ((this.jdField_Skill_of_type_Int >= 2) && (Ve.jdField_z_of_type_Double > 0.5D) && (paramFloat1 < 600.0F)) this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.1F; else {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.0F;
      }
      f2 = 0.6F - (float)Ve.jdField_z_of_type_Double;
      if (f2 < 0.0F) f2 = 0.0F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (float)(-30.0D * Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double) * f2 + 1.0D * (Ve.jdField_y_of_type_Double - this.oldVe.jdField_y_of_type_Double) * Ve.jdField_x_of_type_Double + 0.5D * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double);

      if (Ve.jdField_z_of_type_Double > 0.0D) {
        f1 = (float)(10.0D * Math.atan2(Ve.jdField_z_of_type_Double, Ve.jdField_x_of_type_Double) + 6.0D * (Ve.jdField_z_of_type_Double - this.oldVe.jdField_z_of_type_Double) + 0.5D * (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double);
        if (f1 < 0.0F) f1 = 0.0F;
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (float)(-20.0D * Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_z_of_type_Double) - 0.05D * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() + 5.0D * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double);
      } else {
        f1 = (float)(-5.0D * Math.atan2(Ve.jdField_z_of_type_Double, Ve.jdField_x_of_type_Double) + 6.0D * (Ve.jdField_z_of_type_Double - this.oldVe.jdField_z_of_type_Double) + 0.5D * (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double);
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (float)(-20.0D * Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_z_of_type_Double) - 0.05D * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() + 5.0D * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double);
      }
      if (f1 < 0.0F) f1 = 0.0F;

      if (Math.abs(this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl - f1) < paramFloat3 * paramFloat2) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = f1;
      else if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl < f1)
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl += 0.3F * paramFloat2;
      else {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl -= 0.3F * paramFloat2;
      }
    }

    float f2 = 0.054F * (600.0F - paramFloat1);
    if (f2 < 4.0F) f2 = 4.0F;
    if (f2 > this.jdField_AOA_Crit_of_type_Float) f2 = this.jdField_AOA_Crit_of_type_Float;
    if (this.jdField_AOA_of_type_Float > f2 - 1.5F) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, 0.16F * (f2 - 1.5F - this.jdField_AOA_of_type_Float), 0.0F);
    if (this.jdField_AOA_of_type_Float < -5.0F) this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.increment(0.0F, 0.12F * (-5.0F - this.jdField_AOA_of_type_Float), 0.0F);
    this.oldVe.set(Ve);
    this.oldAOA = this.jdField_AOA_of_type_Float;
    this.sub_Man_Count += 1;

    this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double *= 0.95D;
  }

  private void doCheckStrike() {
    if ((this.jdField_M_of_type_ComMaddoxIl2FmMass.massEmpty > 5000.0F) && (!this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding())) return;
    if ((this.maneuver == 24) && (this.strikeTarg == this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel)) return;
    if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeDockable)) && (((TypeDockable)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).typeDockableIsDocked())) {
      return;
    }
    Vpl.sub(this.strikeTarg.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    tmpV3f.set(Vpl);
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(tmpV3f);
    if (tmpV3f.jdField_x_of_type_Double < 0.0D) return;
    tmpV3f.sub(this.strikeTarg.jdField_Vwld_of_type_ComMaddoxJGPVector3d, this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    tmpV3f.scale(0.699999988079071D);
    Vpl.add(tmpV3f);
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Vpl);
    push();
    if (Vpl.jdField_z_of_type_Double > 0.0D) {
      push(61);
    }
    else {
      push(60);
    }
    safe_pop();
  }

  public void setStrikeEmer(FlightModel paramFlightModel) {
    this.strikeEmer = true;
    this.strikeTarg = paramFlightModel;
  }

  private float bombDist(float paramFloat) {
    float f = FMMath.positiveSquareEquation(-0.5F * World.g(), (float)this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double, paramFloat);
    if (f < 0.0F) return -1000000.0F;
    return f * (float)Math.sqrt(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double * this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double + this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double * this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double);
  }

  protected void wingman(boolean paramBoolean) {
    if (this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel == null) return;
    this.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = (paramBoolean ? this : null);
  }

  public float getMnTime() {
    return this.mn_time;
  }

  public void doDumpBombsPassively()
  {
    int k = 0;
    for (int i = 0; i < this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length; i++) {
      if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i] == null) || (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i].length <= 0)) continue; for (int j = 0; j < this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i].length; j++) {
        if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i][j] instanceof BombGun)) {
          if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i][j] instanceof BombGunPara)) { k = 1;
          } else {
            ((BombGun)this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i][j]).setBombDelay(33000000.0F);
            if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor == World.getPlayerAircraft()) && (!World.cur().diffCur.Limited_Ammo)) {
              this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i][j].loadBullets(0);
            }
          }
        }
      }
    }
    if (k == 0) this.bombsOut = true;
  }

  protected void printDebugFM()
  {
    System.out.print("<" + this.jdField_actor_of_type_ComMaddoxIl2EngineActor.name() + "> " + ManString.tname(this.task) + ":" + ManString.name(this.maneuver) + (this.target != null ? "T" : "t") + (this.danger != null ? "D" : "d") + (this.target_ground != null ? "G " : "g "));

    switch (this.maneuver) {
    case 21:
      System.out.println(": WP=" + this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur() + "(" + (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.size() - 1) + ")-" + ManString.wpname(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Action));

      if (this.target_ground == null) break;
      System.out.println(" GT=" + this.target_ground.getClass().getName() + "(" + this.target_ground.name() + ")");

      break;
    case 27:
      if ((this.target == null) || (!Actor.isValid(this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor))) System.out.println(" T=null"); else {
        System.out.println(" T=" + this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.getClass().getName() + "(" + this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.name() + ")");
      }
      break;
    case 43:
    case 50:
    case 51:
    case 52:
      if ((this.target_ground == null) || (!Actor.isValid(this.target_ground))) System.out.println(" GT=null"); else {
        System.out.println(" GT=" + this.target_ground.getClass().getName() + "(" + this.target_ground.name() + ")");
      }
      break;
    default:
      System.out.println("");
      if ((this.target == null) || (!Actor.isValid(this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor))) { System.out.println(" T=null"); } else {
        System.out.println(" T=" + this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.getClass().getName() + "(" + this.target.jdField_actor_of_type_ComMaddoxIl2EngineActor.name() + ")");

        if ((this.target_ground == null) || (!Actor.isValid(this.target_ground))) System.out.println(" GT=null"); else
          System.out.println(" GT=" + this.target_ground.getClass().getName() + "(" + this.target_ground.name() + ")");
      }
    }
  }

  protected void headTurn(float paramFloat)
  {
    if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor == Main3D.cur3D().viewActor()) && (this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[0] < 90))
    {
      int i = 0;
      switch (get_task()) {
      case 2:
        if (this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel == null) break; Ve.set(this.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d); i = 1; break;
      case 6:
        if (this.target == null) break; Ve.set(this.target.jdField_Loc_of_type_ComMaddoxJGPPoint3d); i = 1; break;
      case 5:
        if (this.airClient == null) break; Ve.set(this.airClient.jdField_Loc_of_type_ComMaddoxJGPPoint3d); i = 1; break;
      case 4:
        if (this.danger == null) break; Ve.set(this.danger.jdField_Loc_of_type_ComMaddoxJGPPoint3d); i = 1; break;
      case 7:
        if (this.target_ground == null) break; Ve.set(this.target_ground.pos.getAbsPoint()); i = 1;
      case 3:
      }
      float f1;
      float f2;
      if (i != 0) {
        Ve.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
        tmpOr.setAT0(Ve);
        f1 = tmpOr.getTangage();
        f2 = tmpOr.getYaw();
        if (f2 > 75.0F) f2 = 75.0F;
        if (f2 < -75.0F) f2 = -75.0F;
        if (f1 < -15.0F) f1 = -15.0F;
        if (f1 > 40.0F) f1 = 40.0F;
      }
      else if (get_maneuver() != 44) {
        f1 = 0.0F;
        f2 = 0.0F;
      } else {
        f2 = -15.0F;
        f1 = -15.0F;
      }

      if (Math.abs(this.pilotHeadT - f1) > 3.0F) this.pilotHeadT += 90.0F * (this.pilotHeadT > f1 ? -1.0F : 1.0F) * paramFloat; else
        this.pilotHeadT = f1;
      if (Math.abs(this.pilotHeadY - f2) > 2.0F) this.pilotHeadY += 60.0F * (this.pilotHeadY > f2 ? -1.0F : 1.0F) * paramFloat; else
        this.pilotHeadY = f2;
      tmpOr.setYPR(0.0F, 0.0F, 0.0F);
      tmpOr.increment(0.0F, this.pilotHeadY, 0.0F);
      tmpOr.increment(this.pilotHeadT, 0.0F, 0.0F);
      tmpOr.increment(0.0F, 0.0F, -0.2F * this.pilotHeadT + 0.05F * this.pilotHeadY);
      this.headOr[0] = tmpOr.getYaw();
      this.headOr[1] = tmpOr.getPitch();
      this.headOr[2] = tmpOr.getRoll();
      this.headPos[0] = (0.0005F * Math.abs(this.pilotHeadY));
      this.headPos[1] = (-1.0E-004F * Math.abs(this.pilotHeadY));
      this.headPos[2] = 0.0F;
      ((ActorHMesh)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).hierMesh().chunkSetLocate("Head1_D0", this.headPos, this.headOr);
    }
  }

  protected void turnOffTheWeapon() {
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[0] = false;
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[1] = false;
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[2] = false;
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = false;
    if (this.bombsOut) {
      this.bombsOutCounter += 1;
      if (this.bombsOutCounter > 128) { this.bombsOutCounter = 0; this.bombsOut = false; }
      if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) this.jdField_CT_of_type_ComMaddoxIl2FmControls.WeaponControl[3] = true; else this.bombsOut = false;
      if ((this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3] != null) && (this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][0] != null)) {
        int i = 0;
        for (int j = 0; j < this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length; j++) {
          i += this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][j].countBullets();
        }
        if (i == 0) {
          this.bombsOut = false;
          for (int k = 0; k < this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3].length; k++)
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[3][k].loadBullets(0);
        }
      }
    }
  }

  protected void turnOnChristmasTree(boolean paramBoolean)
  {
    if (paramBoolean) {
      if (World.Sun().ToSun.jdField_z_of_type_Float < -0.22F)
        this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setNavLightsState(true);
    }
    else
      this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setNavLightsState(false);
  }

  protected void turnOnCloudShine(boolean paramBoolean)
  {
    if (paramBoolean) {
      if (World.Sun().ToSun.jdField_z_of_type_Float < -0.22F)
        this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setLandingLightState(true);
    }
    else
      this.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setLandingLightState(false);
  }

  protected void doCheckGround(float paramFloat)
  {
    if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl > 1.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = 1.0F;
    if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl < -1.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = -1.0F;
    if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl > 1.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = 1.0F;
    if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl < -1.0F) this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = -1.0F;
    float f4 = 0.0003F * this.jdField_M_of_type_ComMaddoxIl2FmMass.massEmpty;
    float f5 = 10.0F;
    float f6 = 80.0F;
    if (this.maneuver == 24) {
      f5 = 15.0F;
      f6 = 120.0F;
    }
    float f7 = (float)(-this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double) * f5 * f4;
    float f8 = 1.0F + 7.0F * ((this.jdField_indSpeed_of_type_Float - this.jdField_VmaxAllowed_of_type_Float) / this.jdField_VmaxAllowed_of_type_Float);
    if (f8 > 1.0F) f8 = 1.0F;
    if (f8 < 0.0F) f8 = 0.0F;
    float f1;
    float f2;
    float f3;
    if ((f7 > this.Alt) || (this.Alt < f6) || (f8 > 0.0F))
    {
      if (this.Alt < 0.001F) this.Alt = 0.001F;
      f1 = (f7 - this.Alt) / this.Alt;
      Math.max(0.01667D * (f6 - this.Alt), f1);

      if (f1 > 1.0F) f1 = 1.0F;
      if (f1 < 0.0F) f1 = 0.0F;
      if (f1 < f8) f1 = f8;
      f2 = -0.12F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - 5.0F) + 3.0F * (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_y_of_type_Double;
      f3 = -0.07F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren() + 3.0F * (float)this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double;
      if (f3 > 2.0F) f3 = 2.0F;
      if (f3 < -2.0F) f3 = -2.0F;
      if (f2 > 2.0F) f2 = 2.0F;
      if (f2 < -2.0F) f2 = -2.0F; 
    }
    else
    {
      f1 = 0.0F;
      f2 = 0.0F;
      f3 = 0.0F;
    }

    float f9 = 0.2F;

    if (this.corrCoeff < f1) this.corrCoeff = f1;
    if (this.corrCoeff > f1) this.corrCoeff = (float)(this.corrCoeff * (1.0D - f9 * paramFloat));
    if (f2 < 0.0F) {
      if (this.corrElev > f2) this.corrElev = f2;
      if (this.corrElev < f2) this.corrElev = (float)(this.corrElev * (1.0D - f9 * paramFloat)); 
    }
    else {
      if (this.corrElev < f2) this.corrElev = f2;
      if (this.corrElev > f2) this.corrElev = (float)(this.corrElev * (1.0D - f9 * paramFloat));
    }
    if (f3 < 0.0F) {
      if (this.corrAile > f3) this.corrAile = f3;
      if (this.corrAile < f3) this.corrAile = (float)(this.corrAile * (1.0D - f9 * paramFloat)); 
    }
    else {
      if (this.corrAile < f3) this.corrAile = f3;
      if (this.corrAile > f3) this.corrAile = (float)(this.corrAile * (1.0D - f9 * paramFloat));
    }

    this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (this.corrCoeff * this.corrAile + (1.0F - this.corrCoeff) * this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl);
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl = (this.corrCoeff * this.corrElev + (1.0F - this.corrCoeff) * this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl);
    if ((this.Alt < 15.0F) && (this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double < 0.0D)) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.8500000238418579D;

    if ((-this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double * 1.5D > this.Alt) || (this.Alt < 10.0F)) {
      if ((this.maneuver == 27) || (this.maneuver == 43) || (this.maneuver == 21) || (this.maneuver == 24) || (this.maneuver == 68) || (this.maneuver == 53))
      {
        push();
      }push(2);
      pop();
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }

    this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double *= 0.95D;
  }

  protected void setSpeedControl(float paramFloat) {
    float f1 = 0.8F;
    float f2 = 0.02F;
    float f3 = 1.5F;

    this.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(false);
    float f8;
    float f4;
    float f6;
    float f5;
    float f9;
    float f7;
    float f11;
    float f12;
    switch (this.speedMode) {
    case 1:
      if (this.tailForStaying == null) { f1 = 1.0F; } else {
        Ve.sub(this.tailForStaying.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        this.tailForStaying.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.tailOffset, tmpV3f);
        Ve.add(tmpV3f);
        f8 = (float)Ve.jdField_z_of_type_Double;
        f4 = 0.005F * (200.0F - (float)Ve.length());
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
        f6 = (float)Ve.jdField_x_of_type_Double;
        Ve.normalize();
        f4 = Math.max(f4, (float)Ve.jdField_x_of_type_Double);
        if (f4 < 0.0F) f4 = 0.0F;
        Ve.set(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        Ve.normalize();
        tmpV3f.set(this.tailForStaying.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        tmpV3f.normalize();
        f5 = (float)tmpV3f.dot(Ve);
        if (f5 < 0.0F) f5 = 0.0F;
        f4 *= f5;
        if ((f4 > 0.0F) && (f6 < 1000.0F)) {
          if (f6 > 300.0F) f6 = 300.0F;
          f9 = 0.6F;
          if (this.tailForStaying.jdField_VmaxH_of_type_Float == this.jdField_VmaxH_of_type_Float) f9 = this.tailForStaying.jdField_CT_of_type_ComMaddoxIl2FmControls.getPower();
          if (this.jdField_Vmax_of_type_Float < 83.0F) {
            f10 = f8;
            if (f10 > 0.0F) {
              if (f10 > 20.0F) f10 = 20.0F;
              this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double += 0.01F * f10;
            }
          }
          if (f6 > 0.0F) f7 = 0.007F * f6 + 0.005F * f8; else
            f7 = 0.03F * f6 + 0.001F * f8;
          f10 = getSpeed() - this.tailForStaying.getSpeed();
          f11 = -0.3F * f10;
          f12 = -3.0F * (getForwAccel() - this.tailForStaying.getForwAccel());
          if (f10 > 27.0F) {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 1.0F;
            f1 = 0.0F;
          } else {
            this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = (0.02F * f10 + 0.02F * -f6);
            f1 = f9 + f7 + f11 + f12;
          }
        } else {
          f1 = 1.1F;
        }
      }break;
    case 2:
      f1 = (float)(1.0D - 8.000000000000001E-005D * (0.5D * this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.lengthSquared() - 9.800000000000001D * Ve.jdField_z_of_type_Double - 0.5D * this.tailForStaying.jdField_Vwld_of_type_ComMaddoxJGPVector3d.lengthSquared()));
      break;
    case 3:
      f1 = this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl;
      if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Speed < 10.0F) this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().set(1.7F * this.jdField_Vmin_of_type_Float);
      f9 = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Speed / this.jdField_VmaxH_of_type_Float;
      f1 = 0.2F + 0.8F * (float)Math.pow(f9, 1.5D);
      f1 += 0.1F * (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().Speed - Pitot.Indicator((float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, getSpeed())) - 3.0F * getForwAccel();
      if (getAltitude() < this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().z() - 70.0F) f1 += this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.curr().z() - 70.0F - getAltitude();
      if (f1 > 0.93F) f1 = 0.93F;
      if ((f1 >= 0.35F) || (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.isLanding())) break; f1 = 0.35F; break;
    case 4:
      f1 = this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl;
      f1 += (f2 * (this.smConstSpeed - Pitot.Indicator((float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, getSpeed())) - f3 * getForwAccel()) * paramFloat;
      if (f1 <= 1.0F) break; f1 = 1.0F; break;
    case 5:
      f1 = this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 1.0F;
      f1 += (f2 * (1.3F * this.jdField_VminFLAPS_of_type_Float - Pitot.Indicator((float)this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, getSpeed())) - f3 * getForwAccel()) * paramFloat;
      break;
    case 8:
      f1 = 0.0F;
      break;
    case 6:
      f1 = 1.0F;
      break;
    case 9:
      f1 = 1.1F;
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(true);
      break;
    case 7:
      f1 = this.smConstPower;
      break;
    case 10:
      if (this.tailForStaying == null) { f1 = 1.0F; } else {
        Ve.sub(this.tailForStaying.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        this.tailForStaying.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(this.tailOffset, tmpV3f);
        Ve.add(tmpV3f);
        f8 = (float)Ve.jdField_z_of_type_Double;
        f4 = 0.005F * (200.0F - (float)Ve.length());
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
        f6 = (float)Ve.jdField_x_of_type_Double;
        Ve.normalize();
        f4 = Math.max(f4, (float)Ve.jdField_x_of_type_Double);
        if (f4 < 0.0F) f4 = 0.0F;
        Ve.set(this.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        Ve.normalize();
        tmpV3f.set(this.tailForStaying.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        tmpV3f.normalize();
        f5 = (float)tmpV3f.dot(Ve);
        if (f5 < 0.0F) f5 = 0.0F;
        f4 *= f5;
        if ((f4 > 0.0F) && (f6 < 1000.0F)) {
          if (f6 > 300.0F) f6 = 300.0F;
          f10 = 0.6F;
          if (this.tailForStaying.jdField_VmaxH_of_type_Float == this.jdField_VmaxH_of_type_Float) f10 = this.tailForStaying.jdField_CT_of_type_ComMaddoxIl2FmControls.getPower();
          if (this.jdField_Vmax_of_type_Float < 83.0F) {
            f11 = f8;
            if (f11 > 0.0F) {
              if (f11 > 20.0F) f11 = 20.0F;
              this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double += 0.01F * f11;
            }
          }
          if (f6 > 0.0F) f7 = 0.007F * f6 + 0.005F * f8; else
            f7 = 0.03F * f6 + 0.001F * f8;
          f11 = getSpeed() - this.tailForStaying.getSpeed();
          f12 = -0.3F * f11;
          float f13 = -3.0F * (getForwAccel() - this.tailForStaying.getForwAccel());
          if (f11 > 27.0F) {
            this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.scale(0.9800000190734863D);
            f1 = 0.0F;
          } else {
            float f14 = 1.0F - 0.02F * (0.02F * f11 + 0.02F * -f6);
            if (f14 < 0.98F) f14 = 0.98F;
            if (f14 > 1.0F) f14 = 1.0F;
            this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.scale(f14);
            f1 = f10 + f7 + f12 + f13;
          }
        } else {
          f1 = 1.1F;
        }
      }break;
    default:
      return;
    }
    if (f1 > 1.1F) f1 = 1.1F;
    else if (f1 < 0.0F) f1 = 0.0F;

    if (Math.abs(this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl - f1) < 0.5D * paramFloat) this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = f1;
    else if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl < f1)
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl += 0.5F * paramFloat;
    else {
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl -= 0.5F * paramFloat;
    }

    float f10 = this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getCriticalW();
    if (this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getw() > 0.9F * f10) {
      f1 = 10.0F * (f10 - this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getw()) / f10;
      if (f1 < this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl) this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = f1;
    }

    if (this.jdField_indSpeed_of_type_Float > 0.8F * this.jdField_VmaxAllowed_of_type_Float) {
      f1 = 1.0F * (this.jdField_VmaxAllowed_of_type_Float - this.jdField_indSpeed_of_type_Float) / this.jdField_VmaxAllowed_of_type_Float;
      if (f1 < this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl) this.jdField_CT_of_type_ComMaddoxIl2FmControls.PowerControl = f1;
    }
  }

  private void setRandomTargDeviation(float paramFloat)
  {
    if (isTick(16, 0)) {
      float f = paramFloat * (8.0F - 1.5F * this.jdField_Skill_of_type_Int);
      this.TargDevVnew.set(World.Rnd().nextFloat(-f, f), World.Rnd().nextFloat(-f, f), World.Rnd().nextFloat(-f, f));
    }

    this.TargDevV.interpolate(this.TargDevVnew, 0.01F);
  }

  private Point_Any getNextAirdromeWayPoint()
  {
    if (this.airdromeWay == null) return null;
    if (this.airdromeWay.length == 0) return null;
    if (this.curAirdromePoi >= this.airdromeWay.length) return null;
    return this.airdromeWay[(this.curAirdromePoi++)];
  }

  private void findPointForEmLanding(float paramFloat)
  {
    Point3d localPoint3d = elLoc.getPoint();

    if (this.radius > 2.0D * this.rmax) {
      if (this.submaneuver != 69) initTargPoint(paramFloat); else
        return;
    }
    for (int i = 0; i < 32; i++) {
      Po.set(this.Vtarg.jdField_x_of_type_Double + this.radius * Math.sin(this.phase), this.Vtarg.jdField_y_of_type_Double + this.radius * Math.cos(this.phase), this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double);

      this.radius += 0.01D * this.rmax;
      this.phase += 0.3D;
      Ve.sub(Po, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      double d = Ve.length();
      this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
      Ve.normalize();
      float f1 = 0.9F - 0.005F * this.Alt;
      if (f1 < -1.0F) f1 = -1.0F;
      if (f1 > 0.8F) f1 = 0.8F;
      float f2 = 1.5F - 0.0005F * this.Alt;
      if (f2 < 1.0F) f2 = 1.0F;
      if (f2 > 1.5F) f2 = 1.5F;
      if ((this.rmax <= d) || (d <= this.rmin * f2) || (Ve.jdField_x_of_type_Double <= f1) || 
        (!isConvenientPoint())) continue;
      this.submaneuver = 69;
      localPoint3d.set(Po);
      this.pointQuality = this.curPointQuality;

      return;
    }
  }

  private boolean isConvenientPoint()
  {
    Po.jdField_z_of_type_Double = Engine.cur.land.HQ_Air(Po.jdField_x_of_type_Double, Po.jdField_y_of_type_Double);
    this.curPointQuality = 50;
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        if (Engine.land().isWater(Po.jdField_x_of_type_Double + 500.0D * i, Po.jdField_y_of_type_Double + 500.0D * j)) {
          if (!(this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeSailPlane)) this.curPointQuality -= 1;
        }
        else if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeSailPlane)) this.curPointQuality -= 1;

        if (Engine.cur.land.HQ_ForestHeightHere(Po.jdField_x_of_type_Double + 500.0D * i, Po.jdField_y_of_type_Double + 500.0D * j) > 1.0D) this.curPointQuality -= 2;
        if (Engine.cur.land.EQN(Po.jdField_x_of_type_Double + 500.0D * i, Po.jdField_y_of_type_Double + 500.0D * j, Ve) <= 1110.949951171875D) continue; this.curPointQuality -= 2;
      }
    }
    if (this.curPointQuality < 0) this.curPointQuality = 0;
    return this.curPointQuality > this.pointQuality;
  }

  private void emergencyTurnToDirection(float paramFloat)
  {
    if (Math.abs(Ve.jdField_y_of_type_Double) > 0.1000000014901161D)
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-(float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_z_of_type_Double) - 0.016F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
    else {
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-(float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double) - 0.016F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
    }
    this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = (-10.0F * (float)Math.atan2(Ve.jdField_y_of_type_Double, Ve.jdField_x_of_type_Double));
    if (this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl * this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double > 0.0D) this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double = 0.0D; else this.jdField_W_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 1.039999961853027D; 
  }

  private void initTargPoint(float paramFloat)
  {
    int i = this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.Cur();
    this.Vtarg.set(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.last().x(), this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.last().y(), 0.0D);
    this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.setCur(i);
    this.Vtarg.sub(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    this.Vtarg.jdField_z_of_type_Double = 0.0D;
    if (this.Vtarg.length() > this.rmax) {
      this.Vtarg.normalize();
      this.Vtarg.scale(this.rmax);
    }
    this.Vtarg.add(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    this.phase = 0.0D;
    this.radius = 50.0D;
    this.pointQuality = -1;
  }

  private void emergencyLanding(float paramFloat)
  {
    if (this.first) {
      this.Kmax = (this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.new_Cya(5.0F) / this.jdField_Wing_of_type_ComMaddoxIl2FmPolares.new_Cxa(5.0F));
      if (this.Kmax > 14.0F) this.Kmax = 14.0F;
      this.Kmax *= 0.8F;
      this.rmax = (1.2F * this.Kmax * this.Alt);
      this.rmin = (0.6F * this.Kmax * this.Alt);
      initTargPoint(paramFloat);

      setReadyToDieSoftly(true);
      this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.setStabAll(false);
      if (this.TaxiMode) {
        setSpeedMode(8);
        this.smConstPower = 0.0F;
        push(44);
        pop();
      }
      this.dist = this.Alt;

      if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeSailPlane)) {
        this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setEngineStops();
      }
    }

    setSpeedMode(4);
    this.smConstSpeed = (this.jdField_VminFLAPS_of_type_Float * 1.25F);
    if ((this.Alt < 500.0F) && (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeGlider)) || ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeSailPlane)))) this.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = 1.0F;
    if (this.Alt < 10.0F) {
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      setSpeedMode(4);
      this.smConstSpeed = (this.jdField_VminFLAPS_of_type_Float * 1.1F);
      if (this.Alt < 5.0F) setSpeedMode(8);
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.BrakeControl = 0.2F;
      if ((this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() < 0.300000011920929D) && (World.Rnd().nextInt(0, 99) < 4)) {
        setStationedOnGround(true);
        World.cur(); if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor != World.getPlayerAircraft()) {
          push(44);
          safe_pop();
          if ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeSailPlane)) {
            this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setCurControlAll(true);
            this.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setEngineStops();
            if (Engine.land().isWater(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double)) {
              return;
            }
          }
          ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).hitDaSilk();
        }
        if (this.Group != null) this.Group.delAircraft((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeGlider)) || ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeSailPlane))) return;
        World.cur(); if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor != World.getPlayerAircraft()) {
          if (Airport.distToNearestAirport(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d) > 900.0D) {
            ((Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor).postEndAction(60.0D, this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 4, null);
          }
          else {
            MsgDestroy.Post(Time.current() + 30000L, this.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          }
        }
      }
    }
    this.dA = (0.2F * (getSpeed() - this.jdField_Vmin_of_type_Float * 1.3F) - 0.8F * (getAOA() - 5.0F));

    if (this.Alt < 40.0F) {
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
      if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof BI_1)) || ((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof ME_163B1A))) this.jdField_CT_of_type_ComMaddoxIl2FmControls.GearControl = 1.0F;
      float f1;
      if (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch < 10.0F) {
        f1 = (40.0F * (getSpeed() - this.jdField_VminFLAPS_of_type_Float * 1.15F) - 60.0F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() + 3.0F) - 240.0F * (getVertSpeed() + 1.0F) - 120.0F * ((float)getAccel().jdField_z_of_type_Double - 1.0F)) * 0.004F;
      }
      else {
        f1 = (40.0F * (getSpeed() - this.jdField_VminFLAPS_of_type_Float * 1.15F) - 60.0F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - this.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch + 10.0F) - 240.0F * (getVertSpeed() + 1.0F) - 120.0F * ((float)getAccel().jdField_z_of_type_Double - 1.0F)) * 0.004F;
      }

      if (this.Alt > 0.0F) {
        float f2 = 0.01666F * this.Alt;
        this.dA = (this.dA * f2 + f1 * (1.0F - f2)); } else {
        this.dA = f1;
      }
      this.jdField_CT_of_type_ComMaddoxIl2FmControls.FlapsControl = 0.33F;
      if ((this.Alt < 9.0F) && (Math.abs(this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren()) < 5.0F) && (getVertSpeed() < -0.7F)) this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.jdField_z_of_type_Double *= 0.8700000047683716D;
    }
    else
    {
      this.rmax = (1.2F * this.Kmax * this.Alt);
      this.rmin = (0.6F * this.Kmax * this.Alt);

      if (((this.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof TypeGlider)) && (this.Alt > 200.0F)) {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.RudderControl = 0.0F;
      }
      else if ((this.pointQuality < 50) && (this.mn_time > 0.5F)) {
        findPointForEmLanding(paramFloat);
      }

      if (this.submaneuver == 69) {
        Ve.sub(elLoc.getPoint(), this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        d = Ve.length();
        this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Ve);
        Ve.normalize();
        float f3 = 0.9F - 0.005F * this.Alt;
        if (f3 < -1.0F) f3 = -1.0F;
        if (f3 > 0.8F) f3 = 0.8F;
        if ((this.rmax * 2.0F < d) || (d < this.rmin) || (Ve.jdField_x_of_type_Double < f3)) {
          this.submaneuver = 0;
          initTargPoint(paramFloat);
        }
        if (d > 88.0D) {
          emergencyTurnToDirection(paramFloat);
          if (this.Alt > d) this.submaneuver = 0; 
        }
        else {
          this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
        }
      } else {
        this.jdField_CT_of_type_ComMaddoxIl2FmControls.AileronControl = (-0.04F * this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getKren());
      }
      if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() > -1.0F) this.dA -= 0.1F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() + 1.0F);
      if (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() < -10.0F) this.dA -= 0.1F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() + 10.0F);
    }

    if ((this.Alt < 2.0F) || (this.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround())) {
      this.dA = (-2.0F * (this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage() - this.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch));
    }
    double d = this.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() / this.jdField_Vmin_of_type_Float;
    if (d > 1.0D) d = 1.0D;
    Controls tmp1312_1309 = this.jdField_CT_of_type_ComMaddoxIl2FmControls; tmp1312_1309.ElevatorControl = (float)(tmp1312_1309.ElevatorControl + ((this.dA - this.jdField_CT_of_type_ComMaddoxIl2FmControls.ElevatorControl) * 3.33F * paramFloat + 1.5D * getW().jdField_y_of_type_Double * d + 0.5D * getAW().jdField_y_of_type_Double * d));
  }

  public boolean canITakeoff()
  {
    Po.set(this.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    Vpl.set(69.0D, 0.0D, 0.0D);
    this.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(Vpl);
    Po.add(Vpl);
    Pd.set(Po);
    if (this.jdField_actor_of_type_ComMaddoxIl2EngineActor != War.getNearestFriendAtPoint(Pd, (Aircraft)this.jdField_actor_of_type_ComMaddoxIl2EngineActor, 70.0F)) {
      return false;
    }
    if (this.canTakeoff) {
      return true;
    }
    if (Actor.isAlive(this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport)) {
      if (this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport.takeoffRequest <= 0) {
        this.jdField_AP_of_type_ComMaddoxIl2FmAutopilotage.way.takeoffAirport.takeoffRequest = 2000;
        this.canTakeoff = true;
        return true;
      }
    } else return true;
    return false;
  }

  public void set_maneuver_imm(int paramInt)
  {
    int i = this.maneuver;
    this.maneuver = paramInt;
    if (i != this.maneuver)
      set_flags();
  }
}