package com.maddox.il2.ai.air;

import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple2d;
import com.maddox.JGP.Tuple2f;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Tuple3f;
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
import com.maddox.il2.engine.Interpolate;
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
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Polares;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
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
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.BombGunPara;
import com.maddox.il2.objects.weapons.TorpedoGun;
import com.maddox.rts.IniFile;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.Random;

public class Maneuver extends AIFlightModel
{
  private int task;
  private int maneuver;
  private int curman;
  protected float mn_time;
  private int[] program;
  private boolean bBusy;
  public boolean wasBusy;
  public boolean dontSwitch;
  public boolean aggressiveWingman;
  public boolean kamikaze;
  public boolean silence;
  public boolean bombsOut;
  private int bombsOutCounter;
  public float direction;
  public Loc rwLoc;
  private boolean first;
  private int rocketsDelay;
  private int bulletDelay;
  private int submanDelay;
  private static final int volleyL = 120;
  private float maxG;
  private float maxAOA;
  private float LandHQ;
  public float Alt;
  private float oldAOA;
  private float corrCoeff;
  private float corrElev;
  private float corrAile;
  private boolean checkGround;
  private boolean checkStrike;
  private boolean frequentControl;
  private boolean stallable;
  public FlightModel airClient;
  public FlightModel target;
  public FlightModel danger;
  private float dangerAggressiveness;
  private float oldDanCoeff;
  private int shotAtFriend;
  private float distToFriend;
  public Actor target_ground;
  public AirGroup Group;
  protected boolean TaxiMode;
  protected boolean finished;
  protected boolean canTakeoff;
  public Point_Any wayCurPos;
  protected Point_Any wayPrevPos;
  protected Point_Any[] airdromeWay;
  protected int curAirdromePoi;
  public long actionTimerStart;
  public long actionTimerStop;
  protected int gattackCounter;
  private int beNearOffsPhase;
  public int submaneuver;
  private boolean dont_change_subm;
  private int tmpi;
  private int sub_Man_Count;
  private float dA;
  private float dist;
  private float man1Dist;
  private float bullTime;
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
  protected int speedMode;
  protected float smConstSpeed;
  protected float smConstPower;
  protected FlightModel tailForStaying;
  public Vector3d tailOffset;
  protected int Speak5minutes;
  protected int Speak1minute;
  protected int SpeakBeginGattack;
  protected boolean WeWereInGAttack;
  protected boolean WeWereInAttack;
  protected boolean SpeakMissionAccomplished;
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
  public static final int CLIMBFOR_ADVANTAGE = 71;
  public static final int MOD_ATTACK = 72;
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
  public float[] takeIntoAccount;
  public float[] AccountCoeff;
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
  private Vector3d ApproachV;
  private Vector3d TargV;
  private Vector3d TargDevV;
  private Vector3d TargDevVnew;
  private Vector3d scaledApproachV;
  private float ApproachR;
  private float TargY;
  private float TargZ;
  private float TargYS;
  private float TargZS;
  private float RandomVal;
  public Vector3d followOffset;
  private Vector3d followTargShift;
  private Vector3d followCurShift;
  private float raAilShift;
  private float raElevShift;
  private float raRudShift;
  private float sinKren;
  private boolean strikeEmer;
  private FlightModel strikeTarg;
  private Vector3d strikeV;
  private boolean direc;
  float Kmax;
  float rmin;
  float rmax;
  double phase;
  double radius;
  int pointQuality;
  int curPointQuality;
  private static Vector3d tmpV3d = new Vector3d();
  private static Vector3d tmpV3f = new Vector3d();
  public static Orient tmpOr = new Orient();
  public Orient saveOr;
  private static Point3d Po = new Point3d();
  private static Point3d Pc = new Point3d();
  private static Point3d Pd = new Point3d();
  private static Vector3d Ve = new Vector3d();
  private Vector3d oldVe;
  private Vector3d Vtarg;
  private Vector3d constVtarg;
  private Vector3d constVtarg1;
  private static Vector3d Vf = new Vector3d();
  private Vector3d Vxy;
  private static Vector3d Vpl = new Vector3d();
  private AnglesFork AFo;
  private static Vector3f LandingOffset = new Vector3f(220.0F, 4.0F, 0.0F);
  private float[] headPos;
  private float[] headOr;
  private static Point3d P = new Point3d();
  private static Point2f Pcur = new Point2f();
  private static Vector2d Vcur = new Vector2d();
  private static Vector2f V_to = new Vector2f();
  private static Vector2d Vdiff = new Vector2d();
  private static Loc elLoc = new Loc();
  public static boolean showFM;
  private float pilotHeadT;
  private float pilotHeadY;
  private boolean bStage1 = false;
  private boolean bStage2 = false;
  private boolean bStage3 = false;
  private boolean bStage4 = false;
  private boolean bStage5 = false;
  private boolean bStage6 = false;
  private boolean bStage7 = false;
  private long lStageStartTime;
  private long lStage6Delay;
  private boolean bAlreadyCheckedStage7 = false;
  private boolean bHasCatapult = false;
  private float fNearestDistance;
  private boolean bCatapultAI;
  private boolean bNoNavLightsAI = false;
  private boolean bFastLaunchAI = false;

  public void set_task(int paramInt)
  {
    this.task = paramInt;
  }

  public int get_task()
  {
    return this.task;
  }

  public int get_maneuver()
  {
    return this.maneuver;
  }

  public void set_maneuver(int paramInt)
  {
    if ((this.maneuver != 44) && ((paramInt == 44) || ((this.maneuver != 26) && (this.maneuver != 69) && (this.maneuver != 66) && (this.maneuver != 46))))
    {
      int i = this.maneuver;
      this.maneuver = paramInt;
      if (i != this.maneuver)
        set_flags();
    }
  }

  public void pop()
  {
    if ((this.maneuver != 44) && ((this.program[0] == 44) || ((this.maneuver != 26) && (this.maneuver != 69) && (this.maneuver != 66) && (this.maneuver != 46))))
    {
      int i = this.maneuver;
      this.maneuver = this.program[0];
      for (int j = 0; j < this.program.length - 1; j++)
        this.program[j] = this.program[(j + 1)];
      this.program[(this.program.length - 1)] = 0;
      if (i != this.maneuver)
        set_flags();
    }
  }

  public void unblock()
  {
    this.maneuver = 0;
  }

  public void safe_set_maneuver(int paramInt)
  {
    this.dont_change_subm = true;
    set_maneuver(paramInt);
    this.dont_change_subm = true;
  }

  public void safe_pop()
  {
    this.dont_change_subm = true;
    pop();
    this.dont_change_subm = true;
  }

  public void clear_stack()
  {
    for (int i = 0; i < this.program.length; i++)
      this.program[i] = 0;
  }

  public void push(int paramInt)
  {
    for (int i = this.program.length - 2; i >= 0; i--)
      this.program[(i + 1)] = this.program[i];
    this.program[0] = paramInt;
  }

  public void push()
  {
    push(this.maneuver);
  }

  public void accurate_set_task_maneuver(int paramInt1, int paramInt2)
  {
    if ((this.maneuver != 44) && (this.maneuver != 26) && (this.maneuver != 69) && (this.maneuver != 64))
    {
      set_task(paramInt1);
      if (this.maneuver != paramInt2)
      {
        clear_stack();
        set_maneuver(paramInt2);
      }
    }
  }

  public void accurate_set_FOLLOW()
  {
    if ((this.maneuver != 44) && (this.maneuver != 26) && (this.maneuver != 69) && (this.maneuver != 64))
    {
      set_task(2);
      if ((this.maneuver != 24) && (this.maneuver != 53))
      {
        clear_stack();
        set_maneuver(24);
      }
    }
  }

  public void setBusy(boolean paramBoolean)
  {
    this.bBusy = paramBoolean;
  }

  public boolean isBusy()
  {
    return this.bBusy;
  }

  public void setSpeedMode(int paramInt)
  {
    this.speedMode = paramInt;
  }

  private boolean isStallable()
  {
    if ((this.actor instanceof TypeStormovik))
      return false;
    return !(this.actor instanceof TypeTransport);
  }

  private void resetControls()
  {
    this.CT.AileronControl = (this.CT.BrakeControl = this.CT.ElevatorControl = this.CT.FlapsControl = this.CT.RudderControl = 0.0F);
    this.CT.AirBrakeControl = 0.0F;
  }

  private void set_flags()
  {
    if (World.cur().isDebugFM())
      printDebugFM();
    this.AP.setStabAll(false);
    this.mn_time = 0.0F;
    this.minElevCoeff = 4.0F;
    if (!this.dont_change_subm)
    {
      setSpeedMode(3);
      this.first = true;
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }
    this.dont_change_subm = false;
    if ((this.maneuver != 48) && (this.maneuver != 0) && (this.maneuver != 26) && (this.maneuver != 64) && (this.maneuver != 44))
      resetControls();
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

    if ((World.Sun().ToSun.z < -0.22F) && (this.AP.way.isLanding()))
      turnOnChristmasTree(true);
    else
      turnOnChristmasTree(false);
    if ((this.maneuver == 25) && (World.Sun().ToSun.z < -0.22F) && (this.Alt < 50.0F)) {
      turnOnCloudShine(true);
    }
    if (this.maneuver == 25)
      turnOnCloudShine(true);
    else
      turnOnCloudShine(false);
    if ((this.maneuver == 60) || (this.maneuver == 61) || (this.maneuver == 66) || (this.maneuver == 1) || (this.maneuver == 24) || (this.maneuver == 26) || (this.maneuver == 69) || (this.maneuver == 64) || (this.maneuver == 44))
      this.checkStrike = false;
    else
      this.checkStrike = true;
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

  private void setCheckGround(boolean paramBoolean)
  {
    this.checkGround = paramBoolean;
  }

  public Maneuver(String paramString)
  {
    super(paramString);
    this.maneuver = 26;
    this.program = new int[8];
    this.bBusy = true;
    this.wasBusy = true;
    this.dontSwitch = false;
    this.aggressiveWingman = false;
    this.kamikaze = false;
    this.silence = true;
    this.bombsOutCounter = 0;
    this.first = true;
    this.rocketsDelay = 0;
    this.bulletDelay = 0;
    this.submanDelay = 0;
    this.Alt = 0.0F;
    this.corrCoeff = 0.0F;
    this.corrElev = 0.0F;
    this.corrAile = 0.0F;
    this.checkGround = false;
    this.checkStrike = false;
    this.frequentControl = false;
    this.stallable = false;
    this.airClient = null;
    this.target = null;
    this.danger = null;
    this.dangerAggressiveness = 0.0F;
    this.oldDanCoeff = 0.0F;
    this.shotAtFriend = 0;
    this.distToFriend = 0.0F;
    this.target_ground = null;
    this.TaxiMode = false;
    this.finished = false;
    this.canTakeoff = false;
    this.curAirdromePoi = 0;
    this.actionTimerStart = 0L;
    this.actionTimerStop = 0L;
    this.gattackCounter = 0;
    this.beNearOffsPhase = 0;
    this.submaneuver = 0;
    this.dont_change_subm = false;
    this.tmpi = 0;
    this.sub_Man_Count = 0;
    this.dist = 0.0F;
    this.man1Dist = 50.0F;
    this.bullTime = 0.0015F;
    this.speedMode = 3;
    this.smConstSpeed = 90.0F;
    this.smConstPower = 0.7F;
    this.tailForStaying = null;
    this.tailOffset = new Vector3d();
    this.WeWereInGAttack = false;
    this.WeWereInAttack = false;
    this.SpeakMissionAccomplished = false;
    this.takeIntoAccount = new float[8];
    this.AccountCoeff = new float[8];
    this.ApproachV = new Vector3d();
    this.TargV = new Vector3d();
    this.TargDevV = new Vector3d(0.0D, 0.0D, 0.0D);
    this.TargDevVnew = new Vector3d(0.0D, 0.0D, 0.0D);
    this.scaledApproachV = new Vector3d();
    this.followOffset = new Vector3d();
    this.followTargShift = new Vector3d(0.0D, 0.0D, 0.0D);
    this.followCurShift = new Vector3d(0.0D, 0.0D, 0.0D);
    this.raAilShift = 0.0F;
    this.raElevShift = 0.0F;
    this.raRudShift = 0.0F;
    this.sinKren = 0.0F;
    this.strikeEmer = false;
    this.strikeTarg = null;
    this.strikeV = new Vector3d();
    this.direc = false;
    this.Kmax = 10.0F;
    this.phase = 0.0D;
    this.radius = 50.0D;
    this.pointQuality = -1;
    this.curPointQuality = 50;
    this.saveOr = new Orient();
    this.oldVe = new Vector3d();
    this.Vtarg = new Vector3d();
    this.constVtarg = new Vector3d();
    this.constVtarg1 = new Vector3d();
    this.Vxy = new Vector3d();
    this.AFo = new AnglesFork();
    this.headPos = new float[3];
    this.headOr = new float[3];
    this.pilotHeadT = 0.0F;
    this.pilotHeadY = 0.0F;
    this.AP = new AutopilotAI(this);

    if (Config.cur.ini.get("Mods", "NoNavLightsAI", 0) == 1)
      this.bNoNavLightsAI = true;
    if (Mission.cur().sectFile().get("Mods", "NoNavLightsAI", 0) == 1)
      this.bNoNavLightsAI = true;
    if (Config.cur.ini.get("Mods", "FastLaunchAI", 0) == 1)
      this.bFastLaunchAI = true;
    if (Mission.cur().sectFile().get("Mods", "FastLaunchAI", 0) == 1)
      this.bFastLaunchAI = true;
  }

  public void decDangerAggressiveness()
  {
    this.dangerAggressiveness -= 0.01F;
    if (this.dangerAggressiveness < 0.0F)
      this.dangerAggressiveness = 0.0F;
    this.oldDanCoeff -= 0.005F;
    if (this.oldDanCoeff < 0.0F)
      this.oldDanCoeff = 0.0F;
  }

  public void incDangerAggressiveness(int paramInt, float paramFloat1, float paramFloat2, FlightModel paramFlightModel)
  {
    paramFloat1 -= 0.7F;
    if (paramFloat1 < 0.0F)
      paramFloat1 = 0.0F;
    paramFloat2 = 1000.0F - paramFloat2;
    if (paramFloat2 < 0.0F)
      paramFloat2 = 0.0F;
    double d1 = (paramFlightModel.Energy - this.Energy) * 0.1019D;
    double d2 = 1.0D + d1 * 0.00125D;
    if (d2 > 1.2D)
      d2 = 1.2D;
    if (d2 < 0.8D)
      d2 = 0.8D;
    float f = (float)d2 * (7.E-005F * paramFloat1 * paramFloat2);
    if ((this.danger == null) || (f > this.oldDanCoeff))
      this.danger = paramFlightModel;
    this.oldDanCoeff = f;
    this.dangerAggressiveness += f * paramInt;
    if (this.dangerAggressiveness > 1.0F)
      this.dangerAggressiveness = 1.0F;
  }

  public float getDangerAggressiveness()
  {
    return this.dangerAggressiveness;
  }

  public float getMaxHeightSpeed(float paramFloat)
  {
    if (paramFloat < this.HofVmax)
      return this.Vmax + (this.VmaxH - this.Vmax) * (paramFloat / this.HofVmax);
    float f = (paramFloat - this.HofVmax) / this.HofVmax;
    f = 1.0F - 1.5F * f;
    if (f < 0.0F)
      f = 0.0F;
    return this.VmaxH * f;
  }

  public float getMinHeightTurn(float paramFloat)
  {
    return this.Wing.T_turn;
  }

  public void setShotAtFriend(float paramFloat)
  {
    this.distToFriend = paramFloat;
    this.shotAtFriend = 30;
  }

  public boolean hasCourseWeaponBullets()
  {
    if ((this.actor instanceof KI_46_OTSUHEI))
      return (this.CT.Weapons[1] != null) && (this.CT.Weapons[1][0] != null) && (this.CT.Weapons[1][0].countBullets() != 0);
    if ((this.actor instanceof AR_234))
      return false;
    return ((this.CT.Weapons[0] != null) && (this.CT.Weapons[0][0] != null) && (this.CT.Weapons[0][0].countBullets() != 0)) || ((this.CT.Weapons[1] != null) && (this.CT.Weapons[1][0] != null) && (this.CT.Weapons[1][0].countBullets() != 0));
  }

  public boolean hasBombs()
  {
    if (this.CT.Weapons[3] != null)
    {
      for (int i = 0; i < this.CT.Weapons[3].length; i++)
      {
        if ((this.CT.Weapons[3][i] != null) && (this.CT.Weapons[3][i].countBullets() != 0))
          return true;
      }
    }
    return false;
  }

  public boolean hasRockets()
  {
    if (this.CT.Weapons[2] != null)
    {
      for (int i = 0; i < this.CT.Weapons[2].length; i++)
      {
        if ((this.CT.Weapons[2][i] != null) && (this.CT.Weapons[2][i].countBullets() != 0))
          return true;
      }
    }
    return false;
  }

  public boolean canAttack()
  {
    return ((!this.Group.isWingman(this.Group.numInGroup((Aircraft)this.actor))) || (this.aggressiveWingman)) && (isOk()) && (hasCourseWeaponBullets());
  }

  public void update(float paramFloat)
  {
    if (Config.isUSE_RENDER())
      headTurn(paramFloat);
    if (showFM)
      OutCT(20);
    super.update(paramFloat);
    this.callSuperUpdate = true;
    decDangerAggressiveness();
    if (this.Loc.z < -20.0D)
      ((Aircraft)this.actor).postEndAction(0.0D, this.actor, 4, null);
    this.LandHQ = (float)Engine.land().HQ_Air(this.Loc.x, this.Loc.y);
    Po.set(this.Vwld);
    Po.scale(3.0D);
    Po.add(this.Loc);
    this.LandHQ = (float)Math.max(this.LandHQ, Engine.land().HQ_Air(Po.x, Po.y));
    this.Alt = ((float)this.Loc.z - this.LandHQ);
    this.indSpeed = (getSpeed() * (float)Math.sqrt(this.Density / 1.225F));
    if ((!this.Gears.onGround()) && (isOk()) && (this.Alt > 8.0F))
    {
      if (this.AOA > this.AOA_Crit - 2.0F)
        this.Or.increment(0.0F, 0.05F * (this.AOA_Crit - 2.0F - this.AOA), 0.0F);
      if (this.AOA < -5.0F)
        this.Or.increment(0.0F, 0.05F * (-5.0F - this.AOA), 0.0F);
    }
    if ((this.frequentControl) || ((Time.tickCounter() + this.actor.hashCode()) % 4 == 0))
    {
      turnOffTheWeapon();
      this.maxG = (3.5F + this.Skill * 0.5F);
      this.Or.wrap();
      if ((this.mn_time > 10.0F) && (this.AOA > this.AOA_Crit + 5.0F) && (isStallable()) && (this.stallable))
        safe_set_maneuver(20);
      float f1;
      float f3;
      Object localObject1;
      Maneuver localManeuver4;
      float f5;
      float f8;
      float f10;
      float f14;
      double d5;
      float f7;
      float f9;
      Object localObject2;
      switch (this.maneuver) {
      case 58:
      default:
        break;
      case 0:
        this.target_ground = null;
        break;
      case 1:
        this.dryFriction = 8.0F;
        this.CT.FlapsControl = 0.0F;
        this.CT.BrakeControl = 1.0F;
        break;
      case 48:
        if (this.mn_time < 1.0F) break label24670; pop(); break;
      case 44:
        if ((this.Gears.onGround()) && (this.Vwld.length() < 0.300000011920929D) && (World.Rnd().nextInt(0, 99) < 4))
        {
          if (this.Group != null)
            this.Group.delAircraft((Aircraft)this.actor);
          if (((this.actor instanceof TypeGlider)) || ((this.actor instanceof TypeSailPlane))) break label24670;
          World.cur();
          if (this.actor != World.getPlayerAircraft())
          {
            if (Airport.distToNearestAirport(this.Loc) > 900.0D)
              ((Aircraft)this.actor).postEndAction(60.0D, this.actor, 3, null);
            else
              MsgDestroy.Post(Time.current() + 30000L, this.actor);
          }
        }
        if (!this.first) break label24670;
        this.AP.setStabAll(false);
        this.CT.ElevatorControl = World.Rnd().nextFloat(-0.07F, 0.4F);
        this.CT.AileronControl = World.Rnd().nextFloat(-0.15F, 0.15F); break;
      case 7:
        wingman(false);
        setSpeedMode(9);
        if (getW().x <= 0.0D)
        {
          this.CT.AileronControl = -1.0F;
          this.CT.RudderControl = 1.0F;
        }
        else
        {
          this.CT.AileronControl = 1.0F;
          this.CT.RudderControl = -1.0F;
        }
        f1 = this.Or.getKren();
        float f2;
        if ((f1 > -90.0F) && (f1 < 90.0F))
        {
          f2 = 0.01111F * (90.0F - Math.abs(f1));
          this.CT.ElevatorControl = (-0.08F * f2 * (this.Or.getTangage() - 3.0F));
        }
        else
        {
          f2 = 0.01111F * (Math.abs(f1) - 90.0F);
          this.CT.ElevatorControl = (0.08F * f2 * (this.Or.getTangage() - 3.0F));
        }
        if (getSpeed() < 1.7F * this.Vmin)
          pop();
        if (this.mn_time > 2.2F) {
          pop();
        }

        if ((this.danger != null) && ((this.danger instanceof Pilot)))
        {
          Maneuver localManeuver2 = zutiParse2Maneuver(this.danger);
          if (localManeuver2 == null) break label24670;
          if ((localManeuver2.target != this) || (this.Loc.distance(this.danger.Loc) <= 400.0D)) break label24670; pop(); break label24670;
        }

        if ((this.danger == null) || (this.Loc.distance(this.danger.Loc) <= 400.0D)) break label24670;
        pop(); break;
      case 8:
        if ((this.first) && (!isCapableOfACM()))
        {
          if (this.Skill > 0)
            pop();
          if (this.Skill > 1)
            setReadyToReturn(true);
        }
        setSpeedMode(6);
        tmpOr.setYPR(this.Or.getYaw(), 0.0F, 0.0F);
        if (this.Or.getKren() > 0.0F)
          Ve.set(100.0D, -6.0D, 10.0D);
        else
          Ve.set(100.0D, 6.0D, 10.0D);
        tmpOr.transform(Ve);
        this.Or.transformInv(Ve);
        Ve.normalize();
        farTurnToDirection();
        if (((this.Alt <= 250.0F) || (this.mn_time <= 8.0F)) && (this.mn_time <= 120.0F)) break label24670; pop(); break;
      case 55:
        if ((this.first) && (!isCapableOfACM()))
        {
          if (this.Skill > 0)
            pop();
          if (this.Skill > 1)
            setReadyToReturn(true);
        }
        setSpeedMode(6);
        tmpOr.setYPR(this.Or.getYaw(), 0.0F, 0.0F);
        if ((this.Leader != null) && (Actor.isAlive(this.Leader.actor)) && (this.mn_time < 2.5F))
        {
          if (this.Leader.Or.getKren() > 0.0F)
            Ve.set(100.0D, -6.0D, 10.0D);
          else
            Ve.set(100.0D, 6.0D, 10.0D);
        }
        else if (this.Or.getKren() > 0.0F)
          Ve.set(100.0D, -6.0D, 10.0D);
        else
          Ve.set(100.0D, 6.0D, 10.0D);
        tmpOr.transform(Ve);
        this.Or.transformInv(Ve);
        Ve.normalize();
        farTurnToDirection();
        if (((this.Alt <= 250.0F) || (this.mn_time <= 8.0F)) && (this.mn_time <= 120.0F)) break label24670; pop(); break;
      case 45:
        setSpeedMode(7);
        this.smConstPower = 0.8F;
        this.dA = this.Or.getKren();
        if (this.dA > 0.0F)
          this.dA -= 25.0F;
        else
          this.dA -= 335.0F;
        if (this.dA < -180.0F)
          this.dA += 360.0F;
        this.dA = (-0.01F * this.dA);
        this.CT.AileronControl = this.dA;
        this.CT.ElevatorControl = (-0.04F * (this.Or.getTangage() - 1.0F) + 0.002F * (this.AP.way.curr().z() - (float)this.Loc.z + 250.0F));
        if (this.mn_time <= 60.0F) break label24670;
        this.mn_time = 0.0F;
        pop(); break;
      case 54:
        if (this.Vwld.length() <= this.VminFLAPS * 2.0F) break;
        this.Vwld.scale(0.9950000047683716D);
      case 9:
        if ((this.first) && (!isCapableOfACM()))
        {
          if (this.Skill > 0)
            pop();
          if (this.Skill > 1)
            setReadyToReturn(true);
        }
        setSpeedMode(4);
        this.smConstSpeed = 100.0F;
        this.dA = this.Or.getKren();
        if (this.dA > 0.0F)
          this.dA -= 50.0F;
        else
          this.dA -= 310.0F;
        if (this.dA < -180.0F)
          this.dA += 360.0F;
        this.dA = (-0.01F * this.dA);
        this.CT.AileronControl = this.dA;
        this.dA = ((-10.0F - this.Or.getTangage()) * 0.05F);
        this.CT.ElevatorControl = this.dA;
        if (getOverload() < 1.0D / Math.abs(Math.cos(this.dA)))
          this.CT.ElevatorControl += 1.0F * paramFloat;
        else
          this.CT.ElevatorControl -= 1.0F * paramFloat;
        if (this.Alt < 100.0F)
        {
          push(3);
          pop();
        }
        if (this.mn_time <= 5.0F) break label24670; pop(); break;
      case 14:
        setSpeedMode(6);
        if (this.first)
          this.AP.setStabAltitude(true);
        this.dA = this.Or.getKren();
        if (getOverload() < 1.0D / Math.abs(Math.cos(this.dA)))
          this.CT.ElevatorControl += 1.0F * paramFloat;
        else
          this.CT.ElevatorControl -= 1.0F * paramFloat;
        if (this.dA > 0.0F)
          this.dA -= 50.0F;
        else
          this.dA -= 310.0F;
        if (this.dA < -180.0F)
          this.dA += 360.0F;
        this.dA = (-0.01F * this.dA);
        this.CT.AileronControl = this.dA;
        if (this.mn_time <= 5.0F) break label24670; pop(); break;
      case 4:
        this.CT.AileronControl = (getW().x > 0.0D ? 1.0F : -1.0F);
        this.CT.ElevatorControl = (0.1F * (float)Math.cos(FMMath.DEG2RAD(this.Or.getKren())));
        this.CT.RudderControl = 0.0F;
        if (getSpeedKMH() < 220.0F)
        {
          push(3);
          pop();
        }
        if (this.mn_time <= 7.0F) break label24670; pop(); break;
      case 2:
        this.minElevCoeff = 20.0F;
        if (this.first)
        {
          wingman(false);
          this.AP.setStabAll(false);
          this.CT.RudderControl = 0.0F;
          if ((World.Rnd().nextInt(0, 99) < 10) && (this.Alt < 80.0F))
            Voice.speakPullUp((Aircraft)this.actor);
        }
        setSpeedMode(9);
        this.CT.BayDoorControl = 0.0F;
        this.CT.AirBrakeControl = 0.0F;
        this.CT.AileronControl = (-0.04F * (this.dA = this.Or.getKren()));
        this.CT.ElevatorControl = (1.0F + 0.3F * (float)getW().y);
        if (this.CT.ElevatorControl < 0.0F)
          this.CT.ElevatorControl = 0.0F;
        if (this.AOA > 15.0F)
          this.Or.increment(0.0F, (15.0F - this.AOA) * 0.5F * paramFloat, 0.0F);
        if ((this.Alt < 10.0F) && (this.Vwld.z < 0.0D))
          this.Vwld.z *= 0.8999999761581421D;
        if (this.Vwld.z > 1.0D)
        {
          if ((this.actor instanceof TypeGlider))
            push(49);
          else
            push(57);
          pop();
        }
        if (this.mn_time <= 25.0F) break label24670;
        push(49);
        pop(); break;
      case 60:
        setSpeedMode(9);
        this.CT.RudderControl = 0.0F;
        this.CT.ElevatorControl = 1.0F;
        if (this.mn_time <= 0.15F) break label24670; pop(); break;
      case 61:
        this.CT.RudderControl = 0.0F;
        this.CT.ElevatorControl = -0.4F;
        if (this.mn_time <= 0.2F) break label24670; pop(); break;
      case 3:
        if ((this.first) && (this.program[0] == 49))
          pop();
        setSpeedMode(6);
        this.CT.AileronControl = (-0.04F * this.Or.getKren());
        this.dA = ((getSpeedKMH() - 180.0F - this.Or.getTangage() * 10.0F - getVertSpeed() * 5.0F) * 0.004F);
        this.CT.ElevatorControl = this.dA;
        if ((getSpeed() <= this.Vmin * 1.2F) || (getVertSpeed() <= 0.0F)) break label24670;
        setSpeedMode(7);
        this.smConstPower = 0.7F;
        pop(); break;
      case 10:
        this.AP.setStabAll(false);
        setSpeedMode(6);
        this.CT.AileronControl = (-0.04F * this.Or.getKren());
        this.dA = this.CT.ElevatorControl;
        if (this.Or.getTangage() > 15.0F)
          this.dA -= (this.Or.getTangage() - 15.0F) * 0.1F * paramFloat;
        else
          this.dA = (((float)this.Vwld.length() / this.VminFLAPS * 140.0F - 50.0F - this.Or.getTangage() * 20.0F) * 0.004F);
        this.dA = (float)(this.dA + 0.5D * getW().y);
        this.CT.ElevatorControl = this.dA;
        if (((this.Alt <= 250.0F) || (this.mn_time <= 6.0F)) && (this.mn_time <= 20.0F)) break label24670; pop(); break;
      case 71:
        this.AP.setStabAll(false);
        setSpeedMode(6);
        this.CT.AileronControl = (-0.04F * this.Or.getKren());
        this.dA = this.CT.ElevatorControl;
        if (this.Or.getTangage() > 15.0F)
          this.dA -= (this.Or.getTangage() - 15.0F) * 0.1F * paramFloat;
        else
          this.dA = (((float)this.Vwld.length() / this.VminFLAPS * 140.0F - 50.0F - this.Or.getTangage() * 20.0F) * 0.004F);
        this.dA = (float)(this.dA + 0.5D * getW().y);
        this.CT.ElevatorControl = this.dA;
        if (this.mn_time <= 30.0F) break label24670; pop(); break;
      case 57:
        this.AP.setStabAll(false);
        setSpeedMode(9);
        this.CT.AileronControl = (-0.04F * this.Or.getKren());
        this.dA = this.CT.ElevatorControl;
        if (this.Or.getTangage() > 25.0F)
          this.dA -= (this.Or.getTangage() - 25.0F) * 0.1F * paramFloat;
        else
          this.dA = (((float)this.Vwld.length() / this.VminFLAPS * 140.0F - 50.0F - this.Or.getTangage() * 20.0F) * 0.004F);
        this.dA = (float)(this.dA + 0.5D * getW().y);
        this.CT.ElevatorControl = this.dA;
        if ((this.Alt <= 150.0F) && ((this.Alt <= 100.0F) || (this.mn_time <= 2.0F)) && (this.mn_time <= 3.0F)) break label24670; pop(); break;
      case 11:
        setSpeedMode(8);
        if (Math.abs(this.Or.getKren()) < 90.0F)
        {
          this.CT.AileronControl = (-0.04F * this.Or.getKren());
          if ((this.Vwld.z > 0.0D) || (getSpeedKMH() < 270.0F))
            this.dA = -0.04F;
          else
            this.dA = 0.04F;
          this.CT.ElevatorControl = (this.CT.ElevatorControl * 0.9F + this.dA * 0.1F);
        }
        else
        {
          this.CT.AileronControl = (0.04F * (180.0F - Math.abs(this.Or.getKren())));
          if (this.Or.getTangage() > -25.0F)
            this.dA = 0.33F;
          else if ((this.Vwld.z > 0.0D) || (getSpeedKMH() < 270.0F))
            this.dA = 0.04F;
          else
            this.dA = -0.04F;
          this.CT.ElevatorControl = (this.CT.ElevatorControl * 0.9F + this.dA * 0.1F);
        }
        if ((this.Alt >= 120.0F) && (this.mn_time <= 4.0F)) break label24670; pop(); break;
      case 12:
        setSpeedMode(4);
        this.smConstSpeed = 80.0F;
        this.CT.AileronControl = (-0.04F * this.Or.getKren());
        if (this.Vwld.length() > this.VminFLAPS * 2.0F)
          this.Vwld.scale(0.9950000047683716D);
        this.dA = (-((float)this.Vwld.z / (Math.abs(getSpeed()) + 1.0F) + 0.5F));
        if (this.dA < -0.1F)
          this.dA = -0.1F;
        this.CT.ElevatorControl = (this.CT.ElevatorControl * 0.9F + this.dA * 0.1F + 0.3F * (float)getW().y);
        if ((this.mn_time <= 5.0F) && (this.Alt >= 200.0F)) break label24670; pop(); break;
      case 13:
        if (this.first)
        {
          this.AP.setStabAll(false);
          this.submaneuver = ((this.actor instanceof TypeFighter) ? 0 : 2);
        }
        switch (this.submaneuver)
        {
        case 0:
          this.dA = (this.Or.getKren() - 180.0F);
          if (this.dA < -180.0F)
            this.dA += 360.0F;
          this.dA = (-0.04F * this.dA);
          this.CT.AileronControl = this.dA;
          if ((this.mn_time <= 3.0F) && (Math.abs(this.Or.getKren()) <= 175.0F - 5.0F * this.Skill)) break;
          this.submaneuver += 1; break;
        case 1:
          this.dA = (this.Or.getKren() - 180.0F);
          if (this.dA < -180.0F)
            this.dA += 360.0F;
          this.dA = (-0.04F * this.dA);
          this.CT.AileronControl = this.dA;
          this.CT.RudderControl = (-0.1F * getAOS());
          setSpeedMode(8);
          if ((this.Or.getTangage() > -45.0F) && (getOverload() < this.maxG))
            this.CT.ElevatorControl += 1.5F * paramFloat;
          else
            this.CT.ElevatorControl -= 0.5F * paramFloat;
          if (this.Or.getTangage() < -44.0F)
            this.submaneuver += 1;
          if ((this.Alt >= -5.0D * this.Vwld.z) && (this.mn_time <= 5.0F)) break;
          pop(); break;
        case 2:
          setSpeedMode(8);
          this.CT.AileronControl = (-0.04F * this.Or.getKren());
          this.dA = (-((float)this.Vwld.z / (Math.abs(getSpeed()) + 1.0F) + 0.707F));
          if (this.dA < -0.75F)
            this.dA = -0.75F;
          this.CT.ElevatorControl = (this.CT.ElevatorControl * 0.9F + this.dA * 0.1F + 0.5F * (float)getW().y);
          if ((this.Alt >= -5.0D * this.Vwld.z) && (this.mn_time <= 5.0F)) break;
          pop();
        }

        if (this.Alt >= 200.0F) break label24670; pop(); break;
      case 5:
        this.dA = this.Or.getKren();
        if (this.dA < 0.0F)
          this.dA -= 270.0F;
        else
          this.dA -= 90.0F;
        if (this.dA < -180.0F)
          this.dA += 360.0F;
        this.dA = (-0.02F * this.dA);
        this.CT.AileronControl = this.dA;
        if ((this.mn_time <= 5.0F) && (Math.abs(Math.abs(this.Or.getKren()) - 90.0F) >= 1.0F)) break label24670; pop(); break;
      case 6:
        this.dA = (this.Or.getKren() - 180.0F);
        if (this.dA < -180.0F)
          this.dA += 360.0F;
        this.CT.AileronControl = (float)(-0.04F * this.dA - 0.5D * getW().x);
        if ((this.mn_time <= 4.0F) && (Math.abs(this.Or.getKren()) <= 178.0F)) break label24670;
        this.W.x = 0.0D;
        pop(); break;
      case 35:
        if (this.first)
        {
          this.AP.setStabAll(false);
          this.direction = this.Or.getKren();
          this.submaneuver = (this.Or.getKren() <= 0.0F ? -1 : 1);
          this.tmpi = 0;
          setSpeedMode(9);
        }
        this.CT.AileronControl = (1.0F * this.submaneuver);
        this.CT.RudderControl = (0.0F * this.submaneuver);
        f1 = this.Or.getKren();
        if ((f1 > -90.0F) && (f1 < 90.0F))
        {
          f3 = 0.01111F * (90.0F - Math.abs(f1));
          this.CT.ElevatorControl = (-0.08F * f3 * (this.Or.getTangage() - 3.0F));
        }
        else
        {
          f3 = 0.01111F * (90.0F - Math.abs(f1));
          this.CT.ElevatorControl = (0.08F * f3 * (this.Or.getTangage() - 3.0F));
        }
        if (this.Or.getKren() * this.direction < 0.0F)
          this.tmpi = 1;
        if (this.tmpi == 1) { if ((this.Or.getKren() > this.direction ? 1 : this.submaneuver <= 0 ? 0 : this.Or.getKren() < this.direction ? 1 : 0) != 0); } else if (this.mn_time <= 17.5F) break label24670; pop(); break;
      case 22:
        setSpeedMode(9);
        this.CT.AileronControl = (-0.04F * this.Or.getKren());
        this.CT.ElevatorControl = (-0.04F * (this.Or.getTangage() + 5.0F));
        this.CT.RudderControl = 0.0F;
        if ((getSpeed() <= this.Vmax) && (this.mn_time <= 30.0F)) break label24670; pop(); break;
      case 67:
        this.minElevCoeff = 18.0F;
        if (this.first)
        {
          this.sub_Man_Count = 0;
          setSpeedMode(9);
          this.CT.PowerControl = 1.1F;
        }
        if (this.danger != null)
        {
          f1 = 700.0F - (float)this.danger.Loc.distance(this.Loc);
          if (f1 < 0.0F)
            f1 = 0.0F;
          f1 *= 0.00143F;
          f3 = this.Or.getKren();
          if ((this.sub_Man_Count == 0) || (this.first))
          {
            if (this.raAilShift < 0.0F)
              this.raAilShift = (f1 * World.Rnd().nextFloat(0.6F, 1.0F));
            else
              this.raAilShift = (f1 * World.Rnd().nextFloat(-1.0F, -0.6F));
            this.raRudShift = (f1 * World.Rnd().nextFloat(-0.5F, 0.5F));
            this.raElevShift = (f1 * World.Rnd().nextFloat(-0.8F, 0.8F));
          }
          this.CT.AileronControl = (0.9F * this.CT.AileronControl + 0.1F * this.raAilShift);
          this.CT.RudderControl = (0.95F * this.CT.RudderControl + 0.05F * this.raRudShift);
          if ((f3 > -90.0F) && (f3 < 90.0F))
            this.CT.ElevatorControl = (-0.04F * (this.Or.getTangage() + 5.0F));
          else
            this.CT.ElevatorControl = (0.05F * (this.Or.getTangage() + 5.0F));
          this.CT.ElevatorControl += 0.1F * this.raElevShift;
          this.sub_Man_Count += 1;
          if ((this.sub_Man_Count >= 80.0F * (1.5F - f1)) && (f3 > -70.0F) && (f3 < 70.0F))
            this.sub_Man_Count = 0;
          if (this.mn_time <= 30.0F) break label24670; pop(); break label24670;
        }

        pop();
        break;
      case 16:
        if (this.first)
        {
          if (!isCapableOfACM())
            pop();
          this.AP.setStabAll(false);
          setSpeedMode(6);
          if (getSpeed() < 0.75F * this.Vmax)
          {
            push();
            push(22);
            pop();
            break label24670;
          }
          this.submaneuver = 0;
        }
        this.maxAOA = (this.Vwld.z <= 0.0D ? 12.0F : 7.0F);
        switch (this.submaneuver)
        {
        case 0:
          this.CT.ElevatorControl = 0.05F;
          this.CT.AileronControl = (-0.04F * this.Or.getKren());
          this.CT.RudderControl = (-0.1F * getAOS());
          if (Math.abs(this.Or.getKren()) >= 2.0F) break;
          this.submaneuver += 1; break;
        case 1:
          this.CT.AileronControl = (-0.04F * this.Or.getKren());
          this.CT.RudderControl = (-0.1F * getAOS());
          this.dA = 0.5F;
          if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.4F * paramFloat;
          else
            this.CT.ElevatorControl += 0.4F * paramFloat;
          if (this.Or.getTangage() > 80.0F)
            this.submaneuver += 1;
          if (getSpeed() >= this.Vmin * 1.5F) break;
          pop(); break;
        case 2:
          this.CT.RudderControl = (-0.1F * getAOS() * (getSpeed() <= 300.0F ? 0.0F : 1.0F));
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.4F * paramFloat;
          else
            this.CT.ElevatorControl += 0.4F * paramFloat;
          if (this.Or.getTangage() >= 0.0F) break;
          this.submaneuver += 1; break;
        case 3:
          this.CT.RudderControl = (-0.1F * getAOS() * (getSpeed() <= 300.0F ? 0.0F : 1.0F));
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.2F * paramFloat;
          else
            this.CT.ElevatorControl += 0.2F * paramFloat;
          if (this.Or.getTangage() >= -60.0F) break;
          this.submaneuver += 1; break;
        case 4:
          if (this.Or.getTangage() > -45.0F)
          {
            this.CT.AileronControl = (-0.04F * this.Or.getKren());
            this.maxAOA = 3.5F;
          }
          this.CT.RudderControl = (-0.1F * getAOS());
          this.dA = 0.5F;
          if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 1.0F * paramFloat;
          else
            this.CT.ElevatorControl += 0.4F * paramFloat;
          if ((this.Or.getTangage() <= 3.0F) && (this.Vwld.z <= 0.0D)) break;
          pop();
        }

        break;
      case 17:
        if (this.first)
        {
          if (this.Alt < 1000.0F) {
            pop(); break label24670;
          }if (getSpeed() < this.Vmin * 1.2F)
          {
            push();
            push(22);
            pop(); break label24670;
          }

          push(18);
          push(19);
          pop(); break label24670;
        }

        pop();
        break;
      case 19:
        if (this.first)
        {
          if (this.Alt < 1000.0F)
          {
            pop();
            break label24670;
          }
          this.AP.setStabAll(false);
          this.submaneuver = 0;
        }
        this.maxAOA = (this.Vwld.z <= 0.0D ? 12.0F : 7.0F);
        switch (this.submaneuver)
        {
        case 0:
          this.CT.ElevatorControl = 0.05F;
          this.CT.AileronControl = (0.04F * (this.Or.getKren() <= 0.0F ? -180.0F + this.Or.getKren() : 180.0F - this.Or.getKren()));
          this.CT.RudderControl = (-0.1F * getAOS());
          if (Math.abs(this.Or.getKren()) <= 178.0F) break;
          this.submaneuver += 1; break;
        case 1:
          setSpeedMode(7);
          this.smConstPower = 0.5F;
          this.CT.AileronControl = 0.0F;
          this.CT.RudderControl = (-0.1F * getAOS());
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.2F * paramFloat;
          else
            this.CT.ElevatorControl += 1.2F * paramFloat;
          if (this.Or.getTangage() >= -60.0F) break;
          this.submaneuver += 1; break;
        case 2:
          if (this.Or.getTangage() > -45.0F)
          {
            this.CT.AileronControl = (-0.04F * this.Or.getKren());
            setSpeedMode(9);
            this.maxAOA = 7.0F;
          }
          this.CT.RudderControl = (-0.1F * getAOS());
          this.dA = 0.5F;
          if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.8F * paramFloat;
          else
            this.CT.ElevatorControl += 0.4F * paramFloat;
          if ((this.Or.getTangage() <= this.AOA - 1.0F) && (this.Vwld.z <= 0.0D)) break;
          pop();
        }

        break;
      case 18:
        if (this.first)
        {
          if (!isCapableOfACM())
            pop();
          if (getSpeed() < this.Vmax * 0.75F)
          {
            push();
            push(22);
            pop();
            break label24670;
          }
          this.AP.setStabAll(false);
          this.submaneuver = 0;
          setSpeedMode(6);
        }
        this.maxAOA = (this.Vwld.z <= 0.0D ? 12.0F : 7.0F);
        switch (this.submaneuver)
        {
        case 0:
          this.CT.AileronControl = (-0.04F * this.Or.getKren());
          this.CT.RudderControl = (-0.1F * getAOS());
          if (Math.abs(this.Or.getKren()) >= 2.0F) break;
          this.submaneuver += 1; break;
        case 1:
          this.CT.AileronControl = (-0.04F * this.Or.getKren());
          this.CT.RudderControl = (-0.1F * getAOS());
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.4F * paramFloat;
          else
            this.CT.ElevatorControl += 0.8F * paramFloat;
          if (this.Or.getTangage() > 80.0F)
            this.submaneuver += 1;
          if (getSpeed() >= this.Vmin * 1.5F) break;
          pop(); break;
        case 2:
          this.CT.RudderControl = (-0.1F * getAOS() * (getSpeed() <= 300.0F ? 0.0F : 1.0F));
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.4F * paramFloat;
          else
            this.CT.ElevatorControl += 0.4F * paramFloat;
          if (this.Or.getTangage() >= 12.0F) break;
          this.submaneuver += 1; break;
        case 3:
          if (Math.abs(this.Or.getKren()) < 60.0F)
            this.CT.ElevatorControl = 0.05F;
          this.CT.AileronControl = (-0.04F * this.Or.getKren());
          this.CT.RudderControl = (-0.1F * getAOS());
          if (Math.abs(this.Or.getKren()) >= 30.0F) break;
          this.submaneuver += 1; break;
        case 4:
          pop();
        }

        break;
      case 15:
        if ((this.first) && (getSpeed() < 0.35F * (this.Vmin + this.Vmax))) {
          pop(); break label24670;
        }

        push(8);
        pop();
        this.CT.RudderControl = (-0.1F * getAOS());
        if (this.Or.getKren() < 0.0F)
          this.CT.AileronControl = (-0.04F * (this.Or.getKren() + 30.0F));
        else
          this.CT.AileronControl = (-0.04F * (this.Or.getKren() - 30.0F));
        if (this.mn_time <= 7.5F) break label24670; pop(); break;
      case 20:
        if (this.first)
        {
          wingman(false);
          setSpeedMode(6);
        }
        if (!isCapableOfBMP())
        {
          setReadyToDie(true);
          pop();
        }
        if (getW().z > 0.0D)
          this.CT.RudderControl = 1.0F;
        else
          this.CT.RudderControl = -1.0F;
        if (this.AOA > this.AOA_Crit - 4.0F)
          this.Or.increment(0.0F, 0.01F * (this.AOA_Crit - 4.0F - this.AOA), 0.0F);
        if (this.AOA < -5.0F)
          this.Or.increment(0.0F, 0.01F * (-5.0F - this.AOA), 0.0F);
        if (this.AOA < this.AOA_Crit - 1.0F)
          pop();
        if ((this.mn_time <= 14.0F - this.Skill * 4.0F) && (this.Alt >= 50.0F)) break label24670; pop(); break;
      case 21:
        this.AP.setWayPoint(true);
        if (this.mn_time > 300.0F)
          pop();
        if ((isTick(256, 0)) && (!this.actor.isTaskComplete()) && (((this.AP.way.isLast()) && (this.AP.getWayPointDistance() < 1500.0F)) || (this.AP.way.isLanding())))
          World.onTaskComplete(this.actor);
        if ((((Aircraft)this.actor).aircNumber() == 0) && (!isReadyToReturn()))
        {
          World.cur();
          if (World.getPlayerAircraft() != null)
          {
            if (((Aircraft)this.actor).getRegiment() == World.getPlayerAircraft().getRegiment())
            {
              f1 = 1.0E+012F;
              int i;
              if (this.AP.way.curr().Action == 3) {
                f1 = this.AP.getWayPointDistance();
              }
              else {
                i = this.AP.way.Cur();
                this.AP.way.next();
                if (this.AP.way.curr().Action == 3)
                  f1 = this.AP.getWayPointDistance();
                this.AP.way.setCur(i);
              }
              if ((this.Speak5minutes == 0) && (22000.0F < f1) && (f1 < 30000.0F))
              {
                Voice.speak5minutes((Aircraft)this.actor);
                this.Speak5minutes = 1;
              }
              if ((this.Speak1minute == 0) && (f1 < 10000.0F))
              {
                Voice.speak1minute((Aircraft)this.actor);
                this.Speak1minute = 1;
                this.Speak5minutes = 1;
              }
              if (((this.WeWereInGAttack) || (this.WeWereInAttack)) && (this.mn_time > 5.0F))
              {
                if (!this.SpeakMissionAccomplished)
                {
                  i = 0;
                  int n = this.AP.way.Cur();
                  if (this.AP.way.curr().Action == 3) break label24670; if (this.AP.way.curr().getTarget() != null)
                    break label24670;
                  do
                  {
                    this.AP.way.next();
                    if ((this.AP.way.curr().Action == 3) || (this.AP.way.curr().getTarget() != null))
                      i = 1;
                  }
                  while (this.AP.way.Cur() < this.AP.way.size() - 1);

                  this.AP.way.setCur(n);
                  if (i == 0)
                  {
                    Voice.speakMissionAccomplished((Aircraft)this.actor);
                    this.SpeakMissionAccomplished = true;
                  }
                }
                if (!this.SpeakMissionAccomplished)
                {
                  this.Speak5minutes = 0;
                  this.Speak1minute = 0;
                  this.SpeakBeginGattack = 0;
                }
                this.WeWereInGAttack = false;
                this.WeWereInAttack = false;
              }
            }
          }
        }
        if ((((this.actor instanceof TypeBomber)) || ((this.actor instanceof TypeTransport))) && (this.AP.way.curr() != null) && (this.AP.way.curr().Action == 3) && ((this.AP.way.curr().getTarget() == null) || ((this.actor instanceof Scheme4))))
        {
          double d1 = this.Loc.z - World.land().HQ(this.Loc.x, this.Loc.y);
          if (d1 < 0.0D)
            d1 = 0.0D;
          if ((this.AP.getWayPointDistance() < getSpeed() * Math.sqrt(d1 * 0.2038699984550476D)) && (!this.bombsOut))
          {
            if ((this.CT.Weapons[3] != null) && (this.CT.Weapons[3][0] != null) && (this.CT.Weapons[3][0].countBullets() != 0) && (!(this.CT.Weapons[3][0] instanceof BombGunPara)))
              Voice.airSpeaks((Aircraft)this.actor, 85, 1);
            this.bombsOut = true;
            this.AP.way.curr().Action = 0;
            if (this.Group != null)
              this.Group.dropBombs();
          }
        }
        setSpeedMode(3);
        if ((!this.AP.way.isLandingOnShip()) || (!this.AP.way.isLanding())) break label24670;
        this.AP.way.landingAirport.rebuildLandWay(this);
        if (!this.CT.bHasCockpitDoorControl) break label24670; this.AS.setCockpitDoor(this.actor, 1); break;
      case 23:
        if (this.first)
        {
          wingman(false);
          if (getSpeedKMH() < this.Vmin * 1.25F)
          {
            push();
            push(22);
            pop();
            break label24670;
          }
        }
        setSpeedMode(6);
        this.CT.AileronControl = (-0.04F * this.Or.getKren());
        this.CT.RudderControl = (-0.1F * getAOS());
        if ((this.Or.getTangage() < 70.0F) && (getOverload() < this.maxG) && (this.AOA < 14.0F))
          this.CT.ElevatorControl += 0.5F * paramFloat;
        else
          this.CT.ElevatorControl -= 0.5F * paramFloat;
        if (this.Vwld.z >= 1.0D) break label24670; pop(); break;
      case 24:
        Maneuver localManeuver1 = zutiParse2Maneuver(this.Leader);
        if ((this.Leader == null) || (!Actor.isAlive(this.Leader.actor))) {
          set_maneuver(0); break label24670;
        }if ((localManeuver1 != null) && ((!localManeuver1.isOk()) || ((localManeuver1.isBusy()) && ((!(this.Leader instanceof RealFlightModel)) || (!((RealFlightModel)this.Leader).isRealMode()))))) {
          set_maneuver(0); break label24670;
        }

        if ((this.actor instanceof TypeGlider))
        {
          if ((this.Leader.AP.way.curr().Action != 0) && (this.Leader.AP.way.curr().Action != 1))
            set_maneuver(49);
        }
        else if ((this.Leader.getSpeed() < 30.0F) || (this.Leader.Loc.z - Engine.land().HQ_Air(this.Leader.Loc.x, this.Leader.Loc.y) < 50.0D))
        {
          this.airClient = this.Leader;

          push(71);
          push(71);

          pop();
          break label24670;
        }
        if (!this.Leader.AP.way.isLanding())
        {
          this.AP.way.setCur(this.Leader.AP.way.Cur());
          if (this.Leader.Wingman != this)
          {
            if (localManeuver1 != null)
            {
              if ((!this.bombsOut) && (localManeuver1.bombsOut))
              {
                this.bombsOut = true;
                localObject1 = this;
                while (((FlightModelMain)localObject1).Wingman != null)
                {
                  if (zutiParse2Maneuver(((FlightModelMain)localObject1).Wingman) == null)
                    continue;
                  localObject1 = (Maneuver)((FlightModelMain)localObject1).Wingman;
                  ((Maneuver)localObject1).bombsOut = true;
                }
              }
            }

            if (this.CT.BayDoorControl != this.Leader.CT.BayDoorControl)
            {
              this.CT.BayDoorControl = this.Leader.CT.BayDoorControl;
              localObject1 = (Pilot)this;
              while (((FlightModelMain)localObject1).Wingman != null)
              {
                localObject1 = (Pilot)((FlightModelMain)localObject1).Wingman;
                ((FlightModelMain)localObject1).CT.BayDoorControl = this.CT.BayDoorControl;
              }
            }
          }
        }
        else
        {
          if (this.Leader.Wingman != this)
          {
            push(8);
            push(8);
            push(World.Rnd().nextBoolean() ? 8 : 48);
            push(World.Rnd().nextBoolean() ? 8 : 48);
            pop();
          }
          this.Leader = null;
          pop();
          break label24670;
        }
        this.airClient = this.Leader;
        tmpOr.setAT0(this.airClient.Vwld);
        tmpOr.increment(0.0F, this.airClient.getAOA(), 0.0F);
        Ve.set(this.followOffset);
        Ve.x -= 300.0D;
        tmpV3f.sub(this.followTargShift, this.followCurShift);
        if (tmpV3f.lengthSquared() < 0.5D)
          this.followTargShift.set(World.cur().rnd.nextFloat(-0.0F, 10.0F), World.cur().rnd.nextFloat(-5.0F, 5.0F), World.cur().rnd.nextFloat(-3.5F, 3.5F));
        tmpV3f.normalize();
        tmpV3f.scale(2.0F * paramFloat);
        this.followCurShift.add(tmpV3f);
        Ve.add(this.followCurShift);
        tmpOr.transform(Ve, Po);
        Po.scale(-1.0D);
        Po.add(this.airClient.Loc);
        Ve.sub(Po, this.Loc);
        this.Or.transformInv(Ve);
        this.dist = (float)Ve.length();
        if (this.followOffset.x > 600.0D)
        {
          Ve.set(this.followOffset);
          Ve.x -= 0.5D * this.followOffset.x;
          tmpOr.transform(Ve, Po);
          Po.scale(-1.0D);
          Po.add(this.airClient.Loc);
          Ve.sub(Po, this.Loc);
          this.Or.transformInv(Ve);
        }
        Ve.normalize();
        if (this.dist > 600.0D + Ve.x * 400.0D)
        {
          push();
          push(53);
          pop(); break label24670;
        }

        if (((this.actor instanceof TypeTransport)) && (this.Vmax < 70.0D))
          farTurnToDirection(6.2F);
        else
          attackTurnToDirection(this.dist, paramFloat, 10.0F);
        setSpeedMode(10);
        this.tailForStaying = this.Leader;
        this.tailOffset.set(this.followOffset);
        this.tailOffset.scale(-1.0D);

        break;
      case 65:
        if ((!(this instanceof RealFlightModel)) || (!((RealFlightModel)this).isRealMode()))
        {
          this.bombsOut = true;
          this.CT.dropFuelTanks();
        }
        if ((this.airClient == null) || (!Actor.isAlive(this.airClient.actor)) || (!isOk())) {
          set_maneuver(0); break label24670;
        }

        localObject1 = zutiParse2Maneuver(this.airClient);
        localManeuver4 = zutiParse2Maneuver(((Maneuver)this.airClient).danger);
        if ((localObject1 != null) && (localManeuver4 != null))
        {
          if ((((Maneuver)localObject1).getDangerAggressiveness() >= 1.0F - this.Skill * 0.35F) && (localManeuver4 != null) && (hasCourseWeaponBullets()))
          {
            Voice.speakCheckYour6((Aircraft)((Interpolate)localObject1).actor, (Aircraft)localManeuver4.actor);
            Voice.speakHelpFromAir((Aircraft)this.actor, 1);
            set_task(6);
            this.target = localManeuver4;
            set_maneuver(27);
          }

        }
        else if ((localObject1 != null) && (((Maneuver)localObject1).danger != null) && (hasCourseWeaponBullets()))
        {
          Voice.speakCheckYour6((Aircraft)((Interpolate)localObject1).actor, (Aircraft)((Maneuver)localObject1).danger.actor);
          Voice.speakHelpFromAir((Aircraft)this.actor, 1);
          set_task(6);
          this.target = ((Maneuver)localObject1).danger;
          set_maneuver(27);
        }

        Ve.sub(this.airClient.Loc, this.Loc);
        this.Or.transformInv(Ve);
        this.dist = (float)Ve.length();
        Ve.normalize();
        attackTurnToDirection(this.dist, paramFloat, 10.0F + this.Skill * 8.0F);
        if (this.Alt > 50.0F)
          setSpeedMode(1);
        else
          setSpeedMode(6);
        this.tailForStaying = this.airClient;
        this.tailOffset.set(this.followOffset);
        this.tailOffset.scale(-1.0D);
        if ((this.dist <= 600.0D + Ve.x * 400.0D) || (get_maneuver() == 27)) break label24670;
        push();
        push(53);
        pop(); break;
      case 53:
        if ((this.airClient == null) || (!Actor.isAlive(this.airClient.actor)) || (!isOk()))
        {
          this.airClient = null;
          set_maneuver(0); break label24670;
        }

        this.maxAOA = 5.0F;
        Ve.set(this.followOffset);
        Ve.x -= 300.0D;
        tmpOr.setAT0(this.airClient.Vwld);
        tmpOr.increment(0.0F, 4.0F, 0.0F);
        tmpOr.transform(Ve, Po);
        Po.scale(-1.0D);
        Po.add(this.airClient.Loc);
        Ve.sub(Po, this.Loc);
        this.Or.transformInv(Ve);
        this.dist = (float)Ve.length();
        Ve.normalize();
        if (this.Vmax < 83.0F)
          farTurnToDirection(8.5F);
        else
          farTurnToDirection(7.0F);
        float f4 = (this.Energy - this.airClient.Energy) * 0.1019F;
        if (f4 < -50.0D + this.followOffset.z)
          setSpeedMode(9);
        else
          setSpeedMode(10);
        this.tailForStaying = this.airClient;
        this.tailOffset.set(this.followOffset);
        this.tailOffset.scale(-1.0D);
        if (this.dist < 500.0D + Ve.x * 200.0D) {
          pop(); break label24670;
        }

        if ((this.AOA > 12.0F) && (this.Alt > 15.0F))
          this.Or.increment(0.0F, 12.0F - this.AOA, 0.0F);
        if ((this.mn_time <= 30.0F) || ((Ve.x >= 0.0D) && (this.dist <= 10000.0F))) break label24670; pop(); break;
      case 68:
        if ((this.airClient == null) || (!Actor.isAlive(this.airClient.actor)) || (!isOk())) {
          set_maneuver(0); break label24670;
        }

        Maneuver localManeuver3 = zutiParse2Maneuver(this.airClient);
        if (localManeuver3 != null)
        {
          localManeuver4 = zutiParse2Maneuver(((Maneuver)this.airClient).danger);
          if (localManeuver4 != null)
          {
            if ((localManeuver3.getDangerAggressiveness() >= 1.0F - this.Skill * 0.3F) && (hasCourseWeaponBullets()))
            {
              tmpV3d.sub(localManeuver3.Vwld, localManeuver4.Vwld);
              if (tmpV3d.length() < 170.0D)
              {
                set_task(6);
                this.target = localManeuver4;
                set_maneuver(27);
              }
            }

          }
          else if ((localManeuver3.danger != null) && (hasCourseWeaponBullets()))
          {
            tmpV3d.sub(localManeuver3.Vwld, localManeuver3.danger.Vwld);
            if (tmpV3d.length() < 170.0D)
            {
              set_task(6);
              this.target = localManeuver3.danger;
              set_maneuver(27);
            }
          }
        }

        this.maxAOA = 5.0F;
        Ve.set(this.followOffset);
        Ve.x -= 300.0D;
        tmpOr.setAT0(this.airClient.Vwld);
        tmpOr.increment(0.0F, 4.0F, 0.0F);
        tmpOr.transform(Ve, Po);
        Po.scale(-1.0D);
        Po.add(this.airClient.Loc);
        Ve.sub(Po, this.Loc);
        this.Or.transformInv(Ve);
        this.dist = (float)Ve.length();
        Ve.normalize();
        if (this.Vmax < 83.0F)
          farTurnToDirection(8.5F);
        else
          farTurnToDirection(7.0F);
        setSpeedMode(10);
        this.tailForStaying = this.airClient;
        this.tailOffset.set(this.followOffset);
        this.tailOffset.scale(-1.0D);
        if (this.dist < 500.0D + Ve.x * 200.0D) {
          pop(); break label24670;
        }

        if ((this.AOA > 12.0F) && (this.Alt > 15.0F))
          this.Or.increment(0.0F, 12.0F - this.AOA, 0.0F);
        if ((this.mn_time <= 30.0F) || ((Ve.x >= 0.0D) && (this.dist <= 10000.0F))) break label24670; pop(); break;
      case 59:
        if ((this.airClient == null) || (!Actor.isValid(this.airClient.actor)) || (!isOk()))
        {
          this.airClient = null;
          set_maneuver(0); break label24670;
        }

        this.maxAOA = 5.0F;
        if (this.first)
          this.followOffset.set(1000.0F * (float)Math.sin(this.beNearOffsPhase * 0.7854F), 1000.0F * (float)Math.cos(this.beNearOffsPhase * 0.7854F), -400.0D);
        Ve.set(this.followOffset);
        Ve.x -= 300.0D;
        tmpOr.setAT0(this.airClient.Vwld);
        tmpOr.increment(0.0F, 4.0F, 0.0F);
        tmpOr.transform(Ve, Po);
        Po.scale(-1.0D);
        Po.add(this.airClient.Loc);
        Ve.sub(Po, this.Loc);
        this.Or.transformInv(Ve);
        this.dist = (float)Ve.length();
        Ve.normalize();
        farTurnToDirection();
        setSpeedMode(2);
        this.tailForStaying = this.airClient;
        this.tailOffset.set(this.followOffset);
        this.tailOffset.scale(-1.0D);
        if (this.dist < 300.0F)
        {
          this.beNearOffsPhase += 1;
          if (this.beNearOffsPhase > 3)
            this.beNearOffsPhase = 0;
          f5 = (float)Math.sqrt(this.followOffset.x * this.followOffset.x + this.followOffset.y * this.followOffset.y);
          this.followOffset.set(f5 * (float)Math.sin(this.beNearOffsPhase * 1.5708F), f5 * (float)Math.cos(this.beNearOffsPhase * 1.5708F), this.followOffset.z);
        }
        if ((this.AOA > 12.0F) && (this.Alt > 15.0F))
          this.Or.increment(0.0F, 12.0F - this.AOA, 0.0F);
        if ((this.mn_time > 15.0F) && ((Ve.x < 0.0D) || (this.dist > 3000.0F)))
          pop();
        if (this.mn_time <= 30.0F) break label24670; pop(); break;
      case 63:
        if ((!(this instanceof RealFlightModel)) || (!((RealFlightModel)this).isRealMode()))
        {
          this.bombsOut = true;
          this.CT.dropFuelTanks();
        }
        if ((this.target == null) || (!Actor.isValid(this.target.actor)) || (this.target.isTakenMortalDamage()) || (!hasCourseWeaponBullets()))
        {
          this.target = null;
          clear_stack();
          set_maneuver(3); break label24670;
        }
        if ((this.target.getSpeedKMH() < 45.0F) && (this.target.Loc.z - Engine.land().HQ_Air(this.target.Loc.x, this.target.Loc.y) < 50.0D) && (this.target.actor.getArmy() != this.actor.getArmy()))
        {
          this.target_ground = this.target.actor;
          set_task(7);
          clear_stack();
          set_maneuver(43); break label24670;
        }

        if (((this.actor instanceof HE_LERCHE3)) && (((HE_LERCHE3)this.actor).bToFire))
        {
          this.CT.WeaponControl[2] = true;
          ((HE_LERCHE3)this.actor).bToFire = false;
        }
        if (((this.actor instanceof TA_183)) && (((TA_183)this.actor).bToFire))
        {
          this.CT.WeaponControl[2] = true;
          ((TA_183)this.actor).bToFire = false;
        }
        if (((this.actor instanceof TA_152C)) && (((TA_152C)this.actor).bToFire))
        {
          this.CT.WeaponControl[2] = true;
          ((TA_152C)this.actor).bToFire = false;
        }
        if (this.TargV.z == -400.0D)
          fighterUnderBomber(paramFloat);
        else
          fighterVsBomber(paramFloat);
        if ((this.AOA <= this.AOA_Crit - 2.0F) || (this.Alt <= 15.0F)) break label24670; this.Or.increment(0.0F, 0.01F * (this.AOA_Crit - 2.0F - this.AOA), 0.0F); break;
      case 27:
        if ((!(this instanceof RealFlightModel)) || (!((RealFlightModel)this).isRealMode()))
        {
          this.bombsOut = true;
          this.CT.dropFuelTanks();
        }
        if ((this.target == null) || (!Actor.isValid(this.target.actor)) || (this.target.isTakenMortalDamage()) || (this.target.actor.getArmy() == this.actor.getArmy()) || (!hasCourseWeaponBullets()))
        {
          this.target = null;
          clear_stack();
          set_maneuver(0); break label24670;
        }
        if ((this.target.getSpeedKMH() < 45.0F) && (this.target.Loc.z - Engine.land().HQ_Air(this.target.Loc.x, this.target.Loc.y) < 50.0D) && (this.target.actor.getArmy() != this.actor.getArmy()))
        {
          this.target_ground = this.target.actor;
          set_task(7);
          clear_stack();
          set_maneuver(43); break label24670;
        }

        if ((this.first) && ((this.actor instanceof TypeAcePlane)))
        {
          if ((this.target != null) && (this.target.actor != null) && (this.target.actor.getArmy() != this.actor.getArmy()))
            this.target.Skill = 0;
          if ((this.danger != null) && (this.danger.actor != null) && (this.danger.actor.getArmy() != this.actor.getArmy()))
            this.danger.Skill = 0;
        }
        if (this.target.actor.getArmy() != this.actor.getArmy())
        {
          if (zutiParse2Maneuver(this.target) != null) {
            ((Maneuver)this.target).danger = this;
          }
        }

        if ((isTick(64, 0)) && (this.target != null))
        {
          f5 = (this.target.Energy - this.Energy) * 0.1019F;
          Ve.sub(this.target.Loc, this.Loc);
          this.Or.transformInv(Ve);
          Ve.normalize();
          f8 = 470.0F + (float)Ve.x * 120.0F - f5;
          if (f8 < 0.0F)
          {
            clear_stack();
            set_maneuver(62);
          }
        }
        fighterVsFighter(paramFloat);
        setSpeedMode(9);
        if ((this.AOA > this.AOA_Crit - 1.0F) && (this.Alt > 15.0F))
          this.Or.increment(0.0F, 0.01F * (this.AOA_Crit - 1.0F - this.AOA), 0.0F);
        if (this.mn_time <= 100.0F) break label24670;
        this.target = null;
        pop(); break;
      case 62:
        if ((this.target == null) || (!Actor.isValid(this.target.actor)) || (this.target.isTakenMortalDamage()) || (this.target.actor.getArmy() == this.actor.getArmy()) || (!hasCourseWeaponBullets()))
        {
          this.target = null;
          clear_stack();
          set_maneuver(0); break label24670;
        }
        if ((this.target.getSpeedKMH() < 45.0F) && (this.target.Loc.z - Engine.land().HQ_Air(this.target.Loc.x, this.target.Loc.y) < 50.0D) && (this.target.actor.getArmy() != this.actor.getArmy()))
        {
          this.target_ground = this.target.actor;
          set_task(7);
          clear_stack();
          set_maneuver(43); break label24670;
        }

        if ((this.first) && ((this.actor instanceof TypeAcePlane)))
        {
          if ((this.target != null) && (this.target.actor != null) && (this.target.actor.getArmy() != this.actor.getArmy()))
            this.target.Skill = 0;
          if ((this.danger != null) && (this.danger.actor != null) && (this.danger.actor.getArmy() != this.actor.getArmy())) {
            this.danger.Skill = 0;
          }
        }

        if ((zutiParse2Maneuver(this.target) != null) && (this.target.actor.getArmy() != this.actor.getArmy())) {
          ((Maneuver)this.target).danger = this;
        }
        goodFighterVsFighter(paramFloat);

        break;
      case 72:
        if ((this.target == null) || (!Actor.isValid(this.target.actor)) || (this.target.isTakenMortalDamage()) || (this.target.actor.getArmy() == this.actor.getArmy()) || (!hasCourseWeaponBullets()))
        {
          this.target = null;
          clear_stack();
          set_maneuver(0); break label24670;
        }
        if ((this.target.getSpeedKMH() < 45.0F) && (this.target.Loc.z - Engine.land().HQ_Air(this.target.Loc.x, this.target.Loc.y) < 50.0D) && (this.target.actor.getArmy() != this.actor.getArmy()))
        {
          this.target_ground = this.target.actor;
          set_task(7);
          clear_stack();
          set_maneuver(43); break label24670;
        }

        if ((this.first) && ((this.actor instanceof TypeAcePlane)))
        {
          if ((this.target != null) && (this.target.actor != null) && (this.target.actor.getArmy() != this.actor.getArmy()))
            this.target.Skill = 0;
          if ((this.danger != null) && (this.danger.actor != null) && (this.danger.actor.getArmy() != this.actor.getArmy())) {
            this.danger.Skill = 0;
          }
        }
        if ((zutiParse2Maneuver(this.target) != null) && (this.target.actor.getArmy() != this.actor.getArmy())) {
          ((Maneuver)this.target).danger = this;
        }
        modFighterVsFighter(paramFloat);

        break;
      case 70:
        this.checkGround = false;
        this.checkStrike = false;
        this.frequentControl = true;
        this.stallable = false;
        if (!(this.actor instanceof HE_LERCHE3)) break label24670;
        switch (this.submaneuver)
        {
        case 0:
          this.AP.setStabAll(false);
          this.submaneuver += 1;
          this.sub_Man_Count = 0;
        case 1:
          if (this.sub_Man_Count == 0)
            this.CT.AileronControl = World.cur().rnd.nextFloat(-0.15F, 0.15F);
          this.CT.AirBrakeControl = 1.0F;
          this.CT.PowerControl = 1.0F;
          this.CT.ElevatorControl = Aircraft.cvt(this.Or.getTangage(), 0.0F, 60.0F, 1.0F, 0.0F);
          if (this.Or.getTangage() > 30.0F)
          {
            this.submaneuver += 1;
            this.sub_Man_Count = 0;
          }
          this.sub_Man_Count += 1;
          break;
        case 2:
          this.CT.AileronControl = 0.0F;
          this.CT.ElevatorControl = 0.0F;
          this.CT.PowerControl = 0.1F;
          this.Or.increment(0.0F, (float)(paramFloat * 0.1D * this.sub_Man_Count * (90.0D - this.Or.getTangage())), 0.0F);
          if (this.Or.getTangage() > 89.0F)
          {
            this.saveOr.set(this.Or);
            this.submaneuver += 1;
          }
          this.sub_Man_Count += 1;
          break;
        case 3:
          this.CT.ElevatorControl = 0.0F;
          if (this.Alt > 10.0F)
            this.CT.PowerControl = 0.33F;
          else
            this.CT.PowerControl = 0.0F;
          if (this.Alt < 20.0F)
          {
            if (this.Vwld.z < -4.0D)
              this.Vwld.z *= 0.95D;
            if (this.Vwld.lengthSquared() < 1.0D)
              this.EI.setEngineStops();
          }
          this.Or.set(this.saveOr);
          if (this.mn_time <= 100.0F)
            break;
          this.Vwld.set(0.0D, 0.0D, 0.0D);
          MsgDestroy.Post(Time.current() + 12000L, this.actor);
        }
        break;
      case 25:
        wingman(false);
        if (this.AP.way.isLanding())
        {
          if (this.AP.way.isLandingOnShip())
          {
            this.AP.way.landingAirport.rebuildLandWay(this);
            if ((this.CT.GearControl == 1.0F) && (this.CT.arrestorControl < 1.0F) && (!this.Gears.onGround()))
              this.AS.setArrestor(this.actor, 1);
          }
          if (this.first)
          {
            this.AP.setWayPoint(true);
            doDumpBombsPassively();
            this.submaneuver = 0;
          }
          if (((this.actor instanceof HE_LERCHE3)) && (this.Alt < 50.0F)) {
            this.maneuver = 70;
          }

          if (this.Alt < 50.0F)
            this.AS.setNavLightsState(false);
          if (this.Alt < 50.0F) {
            this.AS.setLandingLightState(true);
          }
          this.AP.way.curr().getP(Po);
          int j = this.AP.way.Cur();
          f8 = (float)this.Loc.z - this.AP.way.last().z();
          this.AP.way.setCur(j);
          this.Alt = Math.min(this.Alt, f8);
          Po.add(0.0D, 0.0D, -3.0D);
          Ve.sub(Po, this.Loc);
          f10 = (float)Ve.length();
          int i4 = (this.Alt > 4.5F + this.Gears.H) && (this.AP.way.Cur() < 8) ? 1 : 0;
          if (this.AP.way.isLandingOnShip())
            i4 = (this.Alt > 4.5F + this.Gears.H) && (this.AP.way.Cur() < 8) ? 1 : 0;
          if (i4 != 0)
          {
            this.AP.way.prev();
            this.AP.way.curr().getP(Pc);
            this.AP.way.next();
            tmpV3f.sub(Po, Pc);
            tmpV3f.normalize();
            if ((tmpV3f.dot(Ve) < 0.0D) && (f10 > 1000.0F) && (!this.TaxiMode))
            {
              this.AP.way.first();
              push(10);
              pop();
              this.CT.GearControl = 0.0F;
            }
            f14 = (float)tmpV3f.dot(Ve);
            tmpV3f.scale(-f14);
            tmpV3f.add(Po, tmpV3f);
            tmpV3f.sub(this.Loc);
            float f17 = 0.0005F * (3000.0F - f10);
            if (f17 > 1.0F)
              f17 = 1.0F;
            if (f17 < 0.1F)
              f17 = 0.1F;
            float f19 = (float)tmpV3f.length();
            if (f19 > 40.0F * f17)
            {
              f19 = 40.0F * f17;
              tmpV3f.normalize();
              tmpV3f.scale(f19);
            }
            float f21 = this.VminFLAPS;
            if (this.AP.way.Cur() >= 6)
            {
              if (this.AP.way.isLandingOnShip())
              {
                if ((Actor.isAlive(this.AP.way.landingAirport)) && ((this.AP.way.landingAirport instanceof AirportCarrier)))
                {
                  float f22 = (float)((AirportCarrier)this.AP.way.landingAirport).speedLen();
                  if (this.VminFLAPS < f22 + 10.0F)
                    f21 = f22 + 10.0F;
                }
              }
              else
                f21 = this.VminFLAPS * 1.2F;
              if (f21 < 14.0F)
                f21 = 14.0F;
            }
            else {
              f21 = this.VminFLAPS * 2.0F;
            }d5 = this.Vwld.length();
            double d6 = f21 - d5;
            float f23 = 2.0F * paramFloat;
            if (d6 > f23)
              d6 = f23;
            if (d6 < -f23)
              d6 = -f23;
            Ve.normalize();
            Ve.scale(d5);
            Ve.add(tmpV3f);
            Ve.sub(this.Vwld);
            float f24 = (50.0F * f17 - f19) * paramFloat;
            if (Ve.length() > f24)
            {
              Ve.normalize();
              Ve.scale(f24);
            }
            this.Vwld.add(Ve);
            this.Vwld.normalize();
            this.Vwld.scale(d5 + d6);
            this.Loc.x += this.Vwld.x * paramFloat;
            this.Loc.y += this.Vwld.y * paramFloat;
            this.Loc.z += this.Vwld.z * paramFloat;
            this.Or.transformInv(tmpV3f);
            tmpOr.setAT0(this.Vwld);
            float f25 = 0.0F;
            if (this.AP.way.isLandingOnShip())
              f25 = 0.9F * (45.0F - this.Alt);
            else
              f25 = 0.7F * (20.0F - this.Alt);
            if (f25 < 0.0F)
              f25 = 0.0F;
            tmpOr.increment(0.0F, 4.0F + f25, (float)(tmpV3f.y * 0.5D));
            this.Or.interpolate(tmpOr, 0.5F * paramFloat);
            this.callSuperUpdate = false;
            this.W.set(0.0D, 0.0D, 0.0D);
            this.CT.ElevatorControl = (0.05F + 0.3F * f25);
            this.CT.RudderControl = (float)(-tmpV3f.y * 0.02D);
            this.direction = this.Or.getAzimut();
          }
          else {
            this.AP.setStabDirection(true);
          }
        } else {
          this.AP.setStabDirection(true);
        }this.dA = this.CT.ElevatorControl;
        this.AP.update(paramFloat);
        setSpeedControl(paramFloat);
        this.CT.ElevatorControl = this.dA;
        if (this.maneuver == 25)
        {
          if (this.Alt > 60.0F)
          {
            if (this.Alt < 160.0F)
              this.CT.FlapsControl = 0.32F;
            else
              this.CT.FlapsControl = 0.0F;
            setSpeedMode(7);
            this.smConstPower = 0.2F;
            this.dA = Math.min(130.0F + this.Alt, 270.0F);
            if ((this.Vwld.z > 0.0D) || (getSpeedKMH() < this.dA))
              this.dA = (-1.2F * paramFloat);
            else
              this.dA = (1.2F * paramFloat);
            this.CT.ElevatorControl = (this.CT.ElevatorControl * 0.9F + this.dA * 0.1F);
          }
          else
          {
            this.minElevCoeff = 15.0F;
            if ((this.AP.way.Cur() >= 6) || (this.AP.way.Cur() == 0))
            {
              if (this.Or.getTangage() < -5.0F)
                this.Or.increment(0.0F, -this.Or.getTangage() - 5.0F, 0.0F);
              if (this.Or.getTangage() > this.Gears.Pitch + 10.0F)
                this.Or.increment(0.0F, -(this.Or.getTangage() - (this.Gears.Pitch + 10.0F)), 0.0F);
            }
            this.CT.FlapsControl = 1.0F;
            if (this.Vrel.length() < 1.0D)
            {
              this.CT.FlapsControl = (this.CT.BrakeControl = 0.0F);
              if (!this.TaxiMode)
              {
                setSpeedMode(8);
                if (this.AP.way.isLandingOnShip())
                {
                  if (this.CT.getFlap() < 0.001F)
                    this.AS.setWingFold(this.actor, 1);
                  this.CT.BrakeControl = 1.0F;
                  if ((this.CT.arrestorControl == 1.0F) && (this.Gears.onGround()))
                    this.AS.setArrestor(this.actor, 0);
                  MsgDestroy.Post(Time.current() + 25000L, this.actor);
                }
                else
                {
                  this.EI.setEngineStops();
                  if (this.EI.engines[0].getPropw() < 1.0F)
                  {
                    World.cur();
                    if (this.actor != World.getPlayerAircraft())
                      MsgDestroy.Post(Time.current() + 12000L, this.actor);
                  }
                }
              }
            }
            if (getSpeed() < this.VmaxFLAPS * 0.21F)
              this.CT.FlapsControl = 0.0F;
            if ((this.Vrel.length() < this.VmaxFLAPS * 0.25F) && (this.wayCurPos == null) && (!this.AP.way.isLandingOnShip()))
            {
              this.TaxiMode = true;
              this.AP.way.setCur(0);
              return;
            }
            if ((getSpeed() > this.VminFLAPS * 0.6F) && (this.Alt < 10.0F))
            {
              setSpeedMode(8);
              if ((this.AP.way.isLandingOnShip()) && (this.CT.bHasArrestorControl))
              {
                if (this.Vwld.z < -5.5D)
                  this.Vwld.z *= 0.949999988079071D;
                if (this.Vwld.z > 0.0D)
                  this.Vwld.z *= 0.910000026226044D;
              }
              else
              {
                if (this.Vwld.z < -0.6000000238418579D)
                  this.Vwld.z *= 0.9399999976158142D;
                if (this.Vwld.z < -2.5D)
                  this.Vwld.z = -2.5D;
                if (this.Vwld.z > 0.0D)
                  this.Vwld.z *= 0.910000026226044D;
              }
            }
            float f6 = this.Gears.Pitch - 2.0F;
            if (f6 < 5.0F)
              f6 = 5.0F;
            if (((this.Alt < 7.0F) && (this.Or.getTangage() < f6)) || (this.AP.way.isLandingOnShip()))
              this.CT.ElevatorControl += 1.5F * paramFloat;
            Controls tmp15680_15677 = this.CT; tmp15680_15677.ElevatorControl = (float)(tmp15680_15677.ElevatorControl + 0.0500000007450581D * getW().y);
            if (this.Gears.onGround())
            {
              if (this.Gears.Pitch > 5.0F)
              {
                if (this.Or.getTangage() < this.Gears.Pitch)
                  this.CT.ElevatorControl += 1.5F * paramFloat;
                if (!this.AP.way.isLandingOnShip())
                {
                  Controls tmp15775_15772 = this.CT; tmp15775_15772.ElevatorControl = (float)(tmp15775_15772.ElevatorControl + 0.300000011920929D * getW().y);
                }
              } else {
                this.CT.ElevatorControl = 0.0F;
              }if (!this.TaxiMode)
              {
                this.AFo.setDeg(this.Or.getAzimut(), this.direction);
                this.CT.RudderControl = (8.0F * this.AFo.getDiffRad() + 0.5F * (float)getW().z);
              }
              else {
                this.CT.RudderControl = 0.0F;
              }
            }
          }
          this.AP.way.curr().getP(Po);
          return;
        }
        return;
      case 66:
        if ((!isCapableOfTaxiing()) || (this.EI.getThrustOutput() < 0.01F))
        {
          set_task(3);
          this.maneuver = 0;
          set_maneuver(49);
          this.AP.setStabAll(false); break label24670;
        }

        if (this.AS.isPilotDead(0))
        {
          set_task(3);
          this.maneuver = 0;
          set_maneuver(44);
          setSpeedMode(8);
          this.smConstPower = 0.0F;
          if (Airport.distToNearestAirport(this.Loc) > 900.0D)
            ((Aircraft)this.actor).postEndAction(6000.0D, this.actor, 3, null);
          else
            MsgDestroy.Post(Time.current() + 300000L, this.actor);
        }
        else
        {
          P.x = this.Loc.x;
          P.y = this.Loc.y;
          P.z = this.Loc.z;
          Vcur.set(this.Vwld);
          if (this.wayCurPos == null)
          {
            World.cur().airdrome.findTheWay((Pilot)this);
            this.wayPrevPos = (this.wayCurPos = getNextAirdromeWayPoint());
          }
          if (this.wayCurPos != null)
          {
            Point_Any localPoint_Any1 = this.wayCurPos;
            Point_Any localPoint_Any2 = this.wayPrevPos;
            Pcur.set((float)P.x, (float)P.y);
            f10 = Pcur.distance(localPoint_Any1);
            float f12 = Pcur.distance(localPoint_Any2);
            V_to.sub(localPoint_Any1, Pcur);
            V_to.normalize();
            f14 = 5.0F + 0.1F * f10;
            if (f14 > 12.0F)
              f14 = 12.0F;
            if (f14 > 0.9F * this.VminFLAPS)
              f14 = 0.9F * this.VminFLAPS;
            if (((this.curAirdromePoi < this.airdromeWay.length) && (f10 < 15.0F)) || (f10 < 4.0F))
            {
              f14 = 0.0F;
              Point_Any localPoint_Any3 = getNextAirdromeWayPoint();
              if (localPoint_Any3 == null)
              {
                this.CT.setPowerControl(0.0F);
                this.Loc.set(P);
                this.Loc.set(this.Loc);
                if (!this.finished)
                {
                  this.finished = true;
                  int i5 = 1000;
                  if (this.wayCurPos != null)
                    i5 = 2400000;
                  this.actor.collide(true);
                  this.Vwld.set(0.0D, 0.0D, 0.0D);
                  this.CT.setPowerControl(0.0F);
                  this.EI.setCurControlAll(true);
                  this.EI.setEngineStops();
                  World.cur();
                  if (this.actor != World.getPlayerAircraft())
                  {
                    MsgDestroy.Post(Time.current() + i5, this.actor);
                    setStationedOnGround(true);
                    this.maneuver = 0;
                    set_maneuver(44);
                  }
                  return;
                }
                return;
              }
              this.wayPrevPos = this.wayCurPos;
              this.wayCurPos = localPoint_Any3;
            }
            V_to.scale(f14);
            float f18 = 2.0F * paramFloat;
            Vdiff.set(V_to);
            Vdiff.sub(Vcur);
            float f20 = (float)Vdiff.length();
            if (f20 > f18)
            {
              Vdiff.normalize();
              Vdiff.scale(f18);
            }
            Vcur.add(Vdiff);
            tmpOr.setYPR(FMMath.RAD2DEG((float)Vcur.direction()), this.Or.getPitch(), 0.0F);
            this.Or.interpolate(tmpOr, 0.2F);
            this.Vwld.x = Vcur.x;
            this.Vwld.y = Vcur.y;
            P.x += Vcur.x * paramFloat;
            P.y += Vcur.y * paramFloat;
          }
          else
          {
            this.wayPrevPos = (this.wayCurPos = new Point_Null((float)this.Loc.x, (float)this.Loc.y));
            World.cur();
            if (this.actor != World.getPlayerAircraft())
            {
              MsgDestroy.Post(Time.current() + 30000L, this.actor);
              setStationedOnGround(true);
            }
          }
          this.Loc.set(P);
          this.Loc.set(this.Loc);
          return;
        }
        return;
      case 49:
        emergencyLanding(paramFloat);
        break;
      case 64:
        if ((this.actor instanceof TypeGlider)) {
          pop(); break label24670;
        }

        if ((this.actor instanceof HE_LERCHE3))
        {
          int k = (Actor.isAlive(this.AP.way.takeoffAirport)) && ((this.AP.way.takeoffAirport instanceof AirportCarrier)) ? 1 : 0;
          if (k == 0)
            this.callSuperUpdate = false;
        }
        this.CT.BrakeControl = 1.0F;
        this.CT.ElevatorControl = 0.5F;
        this.CT.setPowerControl(0.0F);
        this.EI.setCurControlAll(false);
        setSpeedMode(0);
        if (World.Rnd().nextFloat() < 0.05F)
        {
          this.EI.setCurControl(this.submaneuver, true);
          if (this.EI.engines[this.submaneuver].getStage() == 0)
          {
            setSpeedMode(0);
            this.CT.setPowerControl(0.05F);
            this.EI.engines[this.submaneuver].setControlThrottle(0.2F);
            this.EI.toggle();
            if (((this.actor instanceof BI_1)) || ((this.actor instanceof BI_6)))
            {
              pop();
              break label24670;
            }
          }
        }
        if (this.EI.engines[this.submaneuver].getStage() != 6) break label24670;
        setSpeedMode(0);
        this.EI.engines[this.submaneuver].setControlThrottle(0.0F);
        this.submaneuver += 1;
        this.CT.setPowerControl(0.0F);
        if (this.submaneuver <= this.EI.getNum() - 1) break label24670;
        this.EI.setCurControlAll(true);
        pop(); break;
      case 26:
        f7 = this.Alt;
        f9 = 0.4F;
        if ((Actor.isAlive(this.AP.way.takeoffAirport)) && ((this.AP.way.takeoffAirport instanceof AirportCarrier)))
        {
          f7 -= ((AirportCarrier)this.AP.way.takeoffAirport).height();
          f9 = 0.95F;
          if ((this.Alt < 9.0F) && (this.Vwld.z < 0.0D))
            this.Vwld.z *= 0.85D;
          if (this.CT.bHasCockpitDoorControl)
            this.AS.setCockpitDoor(this.actor, 1);
        }
        if (this.first)
        {
          setCheckGround(false);
          this.CT.BrakeControl = 1.0F;
          this.CT.GearControl = 1.0F;
          this.CT.PowerControl = 0.0F;
          if (this.CT.bHasArrestorControl)
            this.AS.setArrestor(this.actor, 0);
          setSpeedMode(8);
          if ((f7 > 7.21F) && (this.AP.way.Cur() == 0))
          {
            this.EI.setEngineRunning();
            Aircraft.debugprintln(this.actor, "in the air - engines running!.");
          }
          if (!Actor.isAlive(this.AP.way.takeoffAirport))
            this.direction = this.actor.pos.getAbsOrient().getAzimut();
          if ((this.actor instanceof HE_LERCHE3))
          {
            this.maneuver = 69;
            break label24670;
          }
        }
        double d4;
        if (this.Gears.onGround())
        {
          if (this.EI.engines[0].getStage() == 0)
          {
            this.CT.PowerControl = 0.0F;
            setSpeedMode(8);
            if (((World.Rnd().nextFloat() < 0.05F) && (this.mn_time > 1.0F)) || (this.mn_time > 8.0F))
            {
              push();
              push(64);
              this.submaneuver = 0;
              this.maneuver = 0;
              safe_pop();
              break label24670;
            }
          }
          else
          {
            Object localObject3;
            BigshipGeneric localBigshipGeneric;
            if (!this.bAlreadyCheckedStage7)
            {
              if ((this.AP.way.takeoffAirport instanceof AirportCarrier))
              {
                localObject3 = (AirportCarrier)this.AP.way.takeoffAirport;
                localBigshipGeneric = ((AirportCarrier)localObject3).ship();
                this.bHasCatapult = this.Gears.setCatapultOffset(localBigshipGeneric);
                this.bCatapultAI = this.Gears.getCatapultAI();
              }
              else {
                this.bStage7 = true;
              }this.bAlreadyCheckedStage7 = true;
            }
            if (!this.bCatapultAI)
            {
              Po.set(this.Loc);
              Vpl.set(60.0D, 0.0D, 0.0D);
              this.fNearestDistance = 70.0F;
              this.Or.transform(Vpl);
              Po.add(Vpl);
              Pd.set(Po);
            }
            else
            {
              Po.set(this.Loc);
              Vpl.set(200.0D, 0.0D, 0.0D);
              this.fNearestDistance = 210.0F;
              this.Or.transform(Vpl);
              Po.add(Vpl);
              Pd.set(Po);
            }
            if (this.canTakeoff)
            {
              if (!this.bStage7)
              {
                if (this.bStage6)
                {
                  if ((this.bFastLaunchAI) || (!this.CT.bHasWingControl) || ((this.CT.bHasWingControl) && (this.CT.getWing() < 0.5F)))
                    this.bStage7 = true;
                }
                else if (this.bStage4)
                {
                  if ((this.CT.bHasWingControl) && (this.CT.getWing() > 0.001F))
                  {
                    if (this.bFastLaunchAI)
                      this.CT.forceWing(0.0F);
                    this.AS.setWingFold(this.actor, 0);
                  }
                  this.bStage6 = true;
                }
                else if (this.bStage3)
                {
                  localObject3 = new Loc();
                  localBigshipGeneric = (BigshipGeneric)this.brakeShoeLastCarrier;
                  Aircraft localAircraft = (Aircraft)this.actor;
                  CellAirField localCellAirField = localBigshipGeneric.getCellTO();

                  if (this.bCatapultAI)
                  {
                    d4 = -localCellAirField.leftUpperCorner().x - this.Gears.getCatapultOffsetX();
                    d5 = localCellAirField.leftUpperCorner().y - this.Gears.getCatapultOffsetY();
                  }
                  else
                  {
                    d4 = -localCellAirField.leftUpperCorner().x - (localCellAirField.getWidth() / 2 - 3);
                    d5 = this.brakeShoeLoc.getX() + localAircraft.getCellAirPlane().getHeight() + 4.0D;
                  }
                  ((Loc)localObject3).set(d5, d4, this.brakeShoeLoc.getZ(), this.brakeShoeLoc.getAzimut(), this.brakeShoeLoc.getTangage(), this.brakeShoeLoc.getKren());
                  ((Loc)localObject3).add(this.brakeShoeLastCarrier.pos.getAbs());
                  this.actor.pos.setAbs(((Loc)localObject3).getPoint());
                  this.brakeShoeLoc.set(this.actor.pos.getAbs());
                  this.brakeShoeLoc.sub(this.brakeShoeLastCarrier.pos.getAbs());

                  this.bStage4 = true;
                }
                else
                {
                  this.CT.PowerControl = 1.0F;
                  this.bStage3 = true;
                }
              }
              else
              {
                this.CT.PowerControl = 1.1F;

                setSpeedMode(11);
              }
            }
            else
            {
              setSpeedMode(8);
              this.CT.BrakeControl = 1.0F;
              int i2 = 1;
              if (this.mn_time < 8.0F)
                i2 = 0;
              if (this.actor != War.getNearestFriendAtPoint(Pd, (Aircraft)this.actor, this.fNearestDistance))
              {
                if ((this.actor instanceof G4M2E))
                {
                  if (War.getNearestFriendAtPoint(Pd, (Aircraft)this.actor, this.fNearestDistance) != ((G4M2E)this.actor).typeDockableGetDrone())
                    i2 = 0;
                }
                else
                  i2 = 0;
              }
              if ((Actor.isAlive(this.AP.way.takeoffAirport)) && (this.AP.way.takeoffAirport.takeoffRequest > 0))
                i2 = 0;
              if (i2 != 0)
              {
                this.canTakeoff = true;
                if (Actor.isAlive(this.AP.way.takeoffAirport))
                  this.AP.way.takeoffAirport.takeoffRequest = 300;
              }
            }
          }
          if ((this.EI.engines[0].getStage() == 6) && (this.CT.bHasWingControl) && (this.CT.getWing() > 0.001F) && (!(this.AP.way.takeoffAirport instanceof AirportCarrier)))
            this.AS.setWingFold(this.actor, 0);
        }
        else if (this.canTakeoff)
        {
          this.CT.PowerControl = 1.1F;

          setSpeedMode(11);
        }
        if ((this.CT.FlapsControl == 0.0F) && (this.CT.getWing() < 0.001F))
          this.CT.FlapsControl = 0.4F;
        if ((this.EI.engines[0].getStage() == 6) && (this.CT.getPower() > f9))
        {
          this.CT.BrakeControl = 0.0F;
          this.brakeShoe = false;
          float f11 = this.Vmin * this.M.getFullMass() / this.M.massEmpty;
          float f13 = 12.0F * (getSpeed() / f11 - 0.2F);
          if (this.Gears.bIsSail)
            f13 *= 2.0F;
          if (this.Gears.bFrontWheel)
            f13 = this.Gears.Pitch + 4.0F;
          if (f13 < 1.0F)
            f13 = 1.0F;
          if (f13 > 10.0F)
            f13 = 10.0F;
          float f15 = 1.5F;
          if ((Actor.isAlive(this.AP.way.takeoffAirport)) && ((this.AP.way.takeoffAirport instanceof AirportCarrier)) && (!this.Gears.isUnderDeck()))
          {
            this.CT.GearControl = 0.0F;
            if (f7 < 0.0F)
            {
              f13 = 18.0F;
              f15 = 0.05F;
            }
            else
            {
              f13 = 14.0F;
              f15 = 0.3F;
            }
          }
          if (this.Or.getTangage() < f13)
            this.dA = (-0.7F * (this.Or.getTangage() - f13) + f15 * (float)getW().y + 0.5F * (float)getAW().y);
          else
            this.dA = (-0.1F * (this.Or.getTangage() - f13) + f15 * (float)getW().y + 0.5F * (float)getAW().y);
          this.CT.ElevatorControl += (this.dA - this.CT.ElevatorControl) * 3.0F * paramFloat;
        }
        else {
          this.CT.ElevatorControl = 1.0F;
        }this.AFo.setDeg(this.Or.getAzimut(), this.direction);
        double d2 = this.AFo.getDiffRad();
        if (this.EI.engines[0].getStage() == 6)
        {
          this.CT.RudderControl = (8.0F * (float)d2);
          if ((d2 > -1.0D) && (d2 < 1.0D))
          {
            if ((Actor.isAlive(this.AP.way.takeoffAirport)) && (this.CT.getPower() > 0.3F))
            {
              double d3 = this.AP.way.takeoffAirport.ShiftFromLine(this);
              d4 = 3.0D - 3.0D * Math.abs(d2);
              if (d4 > 1.0D)
                d4 = 1.0D;
              d5 = 0.25D * d3 * d4;
              if (d5 > 1.5D)
                d5 = 1.5D;
              if (d5 < -1.5D)
                d5 = -1.5D;
              this.CT.RudderControl += (float)d5;
            }
          }
          else
            this.CT.BrakeControl = 1.0F;
        }
        this.CT.AileronControl = (-0.05F * this.Or.getKren() + 0.3F * (float)getW().y);
        if ((f7 > 5.0F) && (!this.Gears.isUnderDeck()))
          this.CT.GearControl = 0.0F;
        float f16 = 1.0F;
        if (hasBombs())
          f16 *= 1.7F;
        if ((f7 > 120.0F * f16) || (getSpeed() > this.Vmin * 1.8F * f16) || ((f7 > 80.0F * f16) && (getSpeed() > this.Vmin * 1.6F * f16)) || ((f7 > 40.0F * f16) && (getSpeed() > this.Vmin * 1.3F * f16) && (this.mn_time > 60.0F + ((Aircraft)this.actor).aircIndex() * 3.0F)))
        {
          this.CT.FlapsControl = 0.0F;
          this.CT.GearControl = 0.0F;
          this.rwLoc = null;
          if ((this.actor instanceof TypeGlider))
            push(24);
          this.maneuver = 0;
          this.brakeShoe = false;
          this.turnOffCollisions = false;
          if (this.CT.bHasCockpitDoorControl)
            this.AS.setCockpitDoor(this.actor, 0);
          pop();
        }
        if (!(this.actor instanceof TypeGlider)) break label24670;
        this.CT.BrakeControl = 0.0F;
        this.CT.ElevatorControl = 0.05F;
        this.canTakeoff = true; break;
      case 69:
        f7 = this.Alt;
        f9 = 0.4F;
        this.CT.BrakeControl = 1.0F;
        this.W.scale(0.0D);
        int i3 = (Actor.isAlive(this.AP.way.takeoffAirport)) && ((this.AP.way.takeoffAirport instanceof AirportCarrier)) ? 1 : 0;
        if (i3 != 0)
        {
          f7 -= ((AirportCarrier)this.AP.way.takeoffAirport).height();
          f9 = 0.8F;
          if ((this.Alt < 9.0F) && (this.Vwld.z < 0.0D))
            this.Vwld.z *= 0.85D;
          if (this.CT.bHasCockpitDoorControl)
            this.AS.setCockpitDoor(this.actor, 1);
        }
        if ((this.Loc.z != 0.0D) && (((HE_LERCHE3)this.actor).suka.getPoint().z == 0.0D))
          ((HE_LERCHE3)this.actor).suka.set(this.Loc, this.Or);
        if (this.Loc.z != 0.0D)
        {
          if ((this.EI.getPowerOutput() < f9) && (i3 == 0))
          {
            this.callSuperUpdate = false;
            this.Loc.set(((HE_LERCHE3)this.actor).suka.getPoint());
            this.Or.set(((HE_LERCHE3)this.actor).suka.getOrient());
          }
          else if (f7 < 100.0F) {
            this.Or.set(((HE_LERCHE3)this.actor).suka.getOrient());
          }
        }
        if ((this.Gears.onGround()) && (this.EI.engines[0].getStage() == 0))
        {
          this.CT.PowerControl = 0.0F;
          setSpeedMode(8);
          if (((World.Rnd().nextFloat() < 0.05F) && (this.mn_time > 1.0F)) || (this.mn_time > 8.0F))
          {
            push();
            push(64);
            this.submaneuver = 0;
            this.maneuver = 0;
            safe_pop();
            break label24670;
          }
        }
        if (this.EI.engines[0].getStage() == 6)
        {
          this.CT.BrakeControl = 0.0F;
          this.CT.AirBrakeControl = 1.0F;
          this.brakeShoe = false;
          this.CT.PowerControl = 1.1F;

          setSpeedMode(11);
        }
        if (f7 <= 200.0F) break label24670;
        this.CT.GearControl = 0.0F;
        this.rwLoc = null;
        this.CT.ElevatorControl = -1.0F;
        this.CT.AirBrakeControl = 0.0F;
        if (this.Or.getTangage() < 25.0F)
          this.maneuver = 0;
        this.brakeShoe = false;
        this.turnOffCollisions = false;
        if (this.CT.bHasCockpitDoorControl)
          this.AS.setCockpitDoor(this.actor, 0);
        pop(); break;
      case 28:
        if (this.first)
        {
          this.direction = World.Rnd().nextFloat(-25.0F, 25.0F);
          this.AP.setStabAll(false);
          setSpeedMode(6);
          this.CT.RudderControl = World.Rnd().nextFloat(-0.22F, 0.22F);
          this.submaneuver = 0;
          if (getSpeed() < this.Vmin * 1.5F)
            pop();
        }
        this.CT.AileronControl = (-0.04F * (this.Or.getKren() - this.direction));
        switch (this.submaneuver)
        {
        case 0:
          this.dA = 1.0F;
          this.maxAOA = (12.0F + 1.0F * this.Skill);
          if ((this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.6F * paramFloat;
          else
            this.CT.ElevatorControl += 3.3F * paramFloat;
          if (this.Or.getTangage() <= 40.0F + 5.1F * this.Skill) break;
          this.submaneuver += 1; break;
        case 1:
          this.direction = World.Rnd().nextFloat(-25.0F, 25.0F);
          this.CT.RudderControl = World.Rnd().nextFloat(-0.75F, 0.75F);
          this.submaneuver += 1;
        case 2:
          this.dA = -1.0F;
          this.maxAOA = (12.0F + 5.0F * this.Skill);
          if ((this.AOA < -this.maxAOA) || (this.CT.ElevatorControl < this.dA))
            this.CT.ElevatorControl += 0.6F * paramFloat;
          else
            this.CT.ElevatorControl -= 3.3F * paramFloat;
          if (this.Or.getTangage() >= -45.0F) break;
          pop();
        }

        if (this.mn_time <= 3.0F) break label24670; pop(); break;
      case 29:
        this.minElevCoeff = 17.0F;
        if (this.first)
        {
          this.AP.setStabAll(false);
          setSpeedMode(9);
          this.sub_Man_Count = 0;
        }
        if (this.danger != null)
        {
          if (this.sub_Man_Count == 0)
          {
            tmpV3d.set(this.danger.getW());
            this.danger.Or.transform(tmpV3d);
            if (tmpV3d.z > 0.0D)
              this.sinKren = (-World.Rnd().nextFloat(60.0F, 90.0F));
            else
              this.sinKren = World.Rnd().nextFloat(60.0F, 90.0F);
          }
          if (this.Loc.distanceSquared(this.danger.Loc) < 5000.0D)
          {
            setSpeedMode(8);
            this.CT.PowerControl = 0.0F;
          }
          else {
            setSpeedMode(9);
          }
        } else {
          pop();
        }this.CT.FlapsControl = 0.2F;
        if (getSpeed() < 120.0D)
          this.CT.FlapsControl = 0.33F;
        if (getSpeed() < 80.0D)
          this.CT.FlapsControl = 1.0F;
        this.CT.AileronControl = (-0.08F * (this.Or.getKren() + this.sinKren));
        this.CT.ElevatorControl = 0.9F;
        this.CT.RudderControl = 0.0F;
        this.sub_Man_Count += 1;
        if (this.sub_Man_Count > 15)
          this.sub_Man_Count = 0;
        if (this.mn_time <= 10.0F) break label24670; pop(); break;
      case 56:
        if (this.first)
        {
          this.submaneuver = World.Rnd().nextInt(0, 1);
          this.direction = World.Rnd().nextFloat(-20.0F, -10.0F);
        }
        this.CT.AileronControl = (-0.08F * (this.Or.getKren() - this.direction));
        switch (this.submaneuver)
        {
        case 0:
          this.dA = 1.0F;
          this.maxAOA = (10.0F + 2.0F * this.Skill);
          if ((getOverload() > 1.0D / Math.abs(Math.cos(Math.toRadians(this.Or.getKren())))) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.2F * paramFloat;
          else
            this.CT.ElevatorControl += 0.4F * paramFloat;
          this.CT.RudderControl = (-1.0F * (float)Math.sin(Math.toRadians(this.Or.getKren() + 55.0F)));
          if (this.mn_time <= 1.5F) break;
          this.submaneuver += 1; break;
        case 1:
          this.direction = World.Rnd().nextFloat(10.0F, 20.0F);
          this.submaneuver += 1;
        case 2:
          this.dA = 1.0F;
          this.maxAOA = (10.0F + 2.0F * this.Skill);
          if ((getOverload() > 1.0D / Math.abs(Math.cos(Math.toRadians(this.Or.getKren())))) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.2F * paramFloat;
          else
            this.CT.ElevatorControl += 0.4F * paramFloat;
          this.CT.RudderControl = (1.0F * (float)Math.sin(Math.toRadians(this.Or.getKren() - 55.0F)));
          if (this.mn_time <= 4.5F) break;
          this.submaneuver += 1; break;
        case 3:
          this.direction = World.Rnd().nextFloat(-20.0F, -10.0F);
          this.submaneuver += 1;
        case 4:
          this.dA = 1.0F;
          this.maxAOA = (10.0F + 2.0F * this.Skill);
          if ((getOverload() > 1.0D / Math.abs(Math.cos(Math.toRadians(this.Or.getKren())))) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.2F * paramFloat;
          else
            this.CT.ElevatorControl += 0.4F * paramFloat;
          this.CT.RudderControl = (-1.0F * (float)Math.sin(Math.toRadians(this.Or.getKren() + 55.0F)));
          if (this.mn_time <= 6.0F) break;
          pop();
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
        if ((!isCapableOfACM()) && (World.Rnd().nextInt(-2, 9) < this.Skill))
          ((Aircraft)this.actor).hitDaSilk();
        if (this.first)
        {
          this.AP.setStabAll(false);
          setSpeedMode(6);
          this.submaneuver = 0;
          this.direction = (178.0F - World.Rnd().nextFloat(0.0F, 8.0F) * this.Skill);
        }
        this.maxAOA = (this.Vwld.z <= 0.0D ? 24.0F : 14.0F);
        if (this.danger != null)
        {
          Ve.sub(this.danger.Loc, this.Loc);
          this.dist = (float)Ve.length();
          this.Or.transformInv(Ve);
          Vpl.set(this.danger.Vwld);
          this.Or.transformInv(Vpl);
          Vpl.normalize();
        }
        switch (this.submaneuver)
        {
        case 0:
          this.dA = (this.Or.getKren() - 180.0F);
          if (this.dA < -180.0F)
            this.dA += 360.0F;
          this.dA = (-0.08F * this.dA);
          this.CT.AileronControl = this.dA;
          this.CT.RudderControl = (this.dA <= 0.0F ? -1.0F : 1.0F);
          this.CT.ElevatorControl = (0.0111111F * Math.abs(this.Or.getKren()));
          if ((this.mn_time <= 2.0F) && (Math.abs(this.Or.getKren()) <= this.direction))
            break;
          this.submaneuver += 1;
          this.CT.RudderControl = World.Rnd().nextFloat(-0.5F, 0.5F);
          this.direction = World.Rnd().nextFloat(-60.0F, -30.0F);
          this.mn_time = 0.5F; break;
        case 1:
          this.dA = (this.Or.getKren() - 180.0F);
          if (this.dA < -180.0F)
            this.dA += 360.0F;
          this.dA = (-0.04F * this.dA);
          this.CT.AileronControl = this.dA;
          if ((this.Or.getTangage() > this.direction + 5.0F) && (getOverload() < this.maxG) && (this.AOA < this.maxAOA))
          {
            if (this.CT.ElevatorControl < 0.0F)
              this.CT.ElevatorControl = 0.0F;
            this.CT.ElevatorControl += 1.0F * paramFloat;
          }
          else
          {
            if (this.CT.ElevatorControl > 0.0F)
              this.CT.ElevatorControl = 0.0F;
            this.CT.ElevatorControl -= 1.0F * paramFloat;
          }
          if ((this.mn_time > 2.0F) && (this.Or.getTangage() < this.direction + 20.0F))
            this.submaneuver += 1;
          if (this.danger == null)
            break;
          if ((this.Skill >= 2) && (this.Or.getTangage() < -30.0F) && (Vpl.x > 0.9986295104026794D))
            this.submaneuver += 1;
          if ((this.Skill < 3) || (Math.abs(this.Or.getTangage()) <= 145.0F) || (Math.abs(this.danger.Or.getTangage()) <= 135.0F) || (this.dist >= 400.0F)) break;
          this.submaneuver += 1; break;
        case 2:
          this.direction = World.Rnd().nextFloat(-60.0F, 60.0F);
          setSpeedMode(6);
          this.submaneuver += 1;
        case 3:
          this.dA = (this.Or.getKren() - this.direction);
          this.CT.AileronControl = (-0.04F * this.dA);
          this.CT.RudderControl = (this.dA <= 0.0F ? -1.0F : 1.0F);
          this.CT.ElevatorControl = 0.5F;
          if (Math.abs(this.dA) >= 4.0F + 3.0F * this.Skill) break;
          this.submaneuver += 1; break;
        case 4:
          this.direction *= World.Rnd().nextFloat(0.5F, 1.0F);
          setSpeedMode(6);
          this.submaneuver += 1;
        case 5:
          this.dA = (this.Or.getKren() - this.direction);
          this.CT.AileronControl = (-0.04F * this.dA);
          this.CT.RudderControl = (-0.1F * getAOS());
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA) || (this.Or.getTangage() > 40.0F))
            this.CT.ElevatorControl -= 0.8F * paramFloat;
          else
            this.CT.ElevatorControl += 1.6F * paramFloat;
          if ((this.Or.getTangage() > 80.0F) || (this.mn_time > 4.0F) || (getSpeed() < this.Vmin * 1.5F))
            pop();
          if ((this.danger == null) || ((Ve.z >= -1.0D) && (this.dist <= 600.0F) && (Vpl.x >= 0.7880100011825562D))) break;
          pop();
        }

        if (this.Alt >= -7.0D * this.Vwld.z) break label24670; pop(); break;
      case 33:
        if (this.first)
        {
          if (this.Alt < 1000.0F)
          {
            pop();
            break label24670;
          }
          this.AP.setStabAll(false);
          this.submaneuver = 0;
          this.direction = ((World.Rnd().nextBoolean() ? 1.0F : -1.0F) * World.Rnd().nextFloat(107.0F, 160.0F));
        }
        this.maxAOA = (this.Vwld.z <= 0.0D ? 24.0F : 14.0F);
        switch (this.submaneuver)
        {
        case 0:
          if (Math.abs(this.Or.getKren()) < 45.0F)
            this.CT.ElevatorControl = (0.005555556F * Math.abs(this.Or.getKren()));
          else if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > 1.0F))
            this.CT.ElevatorControl -= 0.2F * paramFloat;
          else
            this.CT.ElevatorControl += 1.2F * paramFloat;
          this.dA = (this.Or.getKren() - this.direction);
          this.CT.AileronControl = (-0.04F * this.dA);
          this.CT.RudderControl = (-0.1F * getAOS());
          if (Math.abs(this.dA) >= 4.0F + 1.0F * this.Skill) break;
          this.submaneuver += 1; break;
        case 1:
          setSpeedMode(7);
          this.smConstPower = 0.5F;
          this.CT.AileronControl = 0.0F;
          this.CT.RudderControl = (-0.1F * getAOS());
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.2F * paramFloat;
          else
            this.CT.ElevatorControl += 1.2F * paramFloat;
          if (this.Or.getTangage() >= -60.0F) break;
          this.submaneuver += 1; break;
        case 2:
          if (this.Or.getTangage() > -45.0F)
          {
            this.CT.AileronControl = (-0.04F * this.Or.getKren());
            setSpeedMode(9);
            this.maxAOA = 7.0F;
          }
          this.CT.RudderControl = (-0.1F * getAOS());
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.8F * paramFloat;
          else
            this.CT.ElevatorControl += 0.4F * paramFloat;
          if ((this.Or.getTangage() <= this.AOA - 10.0F) && (this.Vwld.z <= -1.0D)) break;
          pop();
        }

        if (this.Alt >= -7.0D * this.Vwld.z) break label24670; pop(); break;
      case 34:
        if (this.first)
        {
          if (this.Alt < 500.0F)
          {
            pop();
            break label24670;
          }
          this.direction = this.Or.getTangage();
          setSpeedMode(9);
        }
        this.dA = (this.Or.getKren() - (this.Or.getKren() <= 0.0F ? -35.0F : 35.0F));
        this.CT.AileronControl = (-0.04F * this.dA);
        this.CT.RudderControl = (this.Or.getKren() <= 0.0F ? -1.0F : 1.0F);
        this.CT.ElevatorControl = -1.0F;
        if ((this.direction <= this.Or.getTangage() + 45.0F) && (this.Or.getTangage() >= -60.0F) && (this.mn_time <= 4.0F)) break label24670; pop(); break;
      case 36:
      case 37:
        if (this.first)
        {
          if (!isCapableOfACM())
            pop();
          if (getSpeed() < this.Vmax * 0.5F)
          {
            pop();
            break label24670;
          }
          this.AP.setStabAll(false);
          this.submaneuver = 0;
          this.direction = World.Rnd().nextFloat(-30.0F, 80.0F);
          setSpeedMode(9);
        }
        this.maxAOA = (this.Vwld.z <= 0.0D ? 24.0F : 14.0F);
        switch (this.submaneuver)
        {
        case 0:
          this.CT.AileronControl = (-0.04F * this.Or.getKren());
          this.CT.RudderControl = (-0.1F * getAOS());
          if (Math.abs(this.Or.getKren()) >= 45.0F) break;
          this.submaneuver += 1; break;
        case 1:
          this.CT.AileronControl = 0.0F;
          this.dA = 1.0F;
          if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > this.dA))
            this.CT.ElevatorControl -= 0.4F * paramFloat;
          else
            this.CT.ElevatorControl += 0.8F * paramFloat;
          if (this.Or.getTangage() > this.direction)
            this.submaneuver += 1;
          if (getSpeed() >= this.Vmin * 1.25F) break;
          pop(); break;
        case 2:
          push(this.maneuver != 36 ? 35 : 7);
          pop();
        }

        break;
      case 38:
        if (this.first)
          this.CT.RudderControl = (this.Or.getKren() <= 0.0F ? -1.0F : 1.0F);
        this.CT.AileronControl += -0.02F * this.Or.getKren();
        if (this.CT.AileronControl > 0.1F)
          this.CT.AileronControl = 0.1F;
        if (this.CT.AileronControl < -0.1F)
          this.CT.AileronControl = -0.1F;
        this.dA = ((getSpeedKMH() - 180.0F - this.Or.getTangage() * 10.0F - getVertSpeed() * 5.0F) * 0.004F);
        this.CT.ElevatorControl = this.dA;
        if (this.mn_time <= 3.5F) break label24670; pop(); break;
      case 39:
        setSpeedMode(6);
        this.CT.AileronControl = (-0.04F * this.Or.getKren());
        this.CT.ElevatorControl = (-0.04F * (this.Or.getTangage() + 10.0F));
        if (this.CT.RudderControl > 0.1F)
          this.CT.RudderControl = 0.8F;
        else if (this.CT.RudderControl < -0.1F)
          this.CT.RudderControl = -0.8F;
        else
          this.CT.RudderControl = (this.Or.getKren() <= 0.0F ? -1.0F : 1.0F);
        if ((getSpeed() <= this.Vmax) && (this.mn_time <= 7.0F)) break label24670; pop(); break;
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
        if ((this.target_ground == null) || (this.target_ground.pos == null))
        {
          if (this.Group != null)
          {
            this.dont_change_subm = true;
            boolean bool1 = isBusy();
            int i1 = this.Group.grTask;
            AirGroup localAirGroup = this.Group;
            this.Group.grTask = 4;
            setBusy(false);
            this.Group.setTaskAndManeuver(this.Group.numInGroup((Aircraft)this.actor));
            setBusy(bool1);
            this.Group.grTask = i1;
          }
          if ((this.target_ground == null) || (this.target_ground.pos == null))
          {
            this.AP.way.first();
            Airport localAirport = Airport.nearest(this.AP.way.curr().getP(), this.actor.getArmy(), 7);

            if (localAirport != null)
              localObject2 = new WayPoint(localAirport.pos.getAbsPoint());
            else
              localObject2 = new WayPoint(this.AP.way.first().getP());
            ((WayPoint)localObject2).set(0.6F * this.Vmax);
            ((WayPoint)localObject2).Action = 2;
            this.AP.way.add((WayPoint)localObject2);
            this.AP.way.last();
            set_task(3);
            clear_stack();
            this.maneuver = 21;
            set_maneuver(21);
            break label24670;
          }
        }
        groundAttackKamikaze(this.target_ground, paramFloat);
        break;
      case 43:
      case 47:
      case 50:
      case 51:
      case 52:
      }if ((this.first) && (!isCapableOfACM()))
      {
        this.bombsOut = true;
        setReadyToReturn(true);
        if (this.Group != null) {
          this.Group.waitGroup(this.Group.numInGroup((Aircraft)this.actor));
        }
        else {
          this.AP.way.next();
          set_task(3);
          clear_stack();
          set_maneuver(21);
        }
      }
      else
      {
        if ((this.target_ground == null) || (this.target_ground.pos == null) || (!Actor.isAlive(this.target_ground)))
        {
          int m = this.maneuver;
          if (this.Group != null)
          {
            localObject2 = this.Group;
            if (this.Group.grTask == 4)
            {
              this.dont_change_subm = true;
              boolean bool2 = isBusy();
              setBusy(false);
              this.Group.setTaskAndManeuver(this.Group.numInGroup((Aircraft)this.actor));
              setBusy(bool2);
            }
          }
          if ((this.target_ground == null) || (this.target_ground.pos == null) || (!Actor.isAlive(this.target_ground)))
          {
            if (m == 50)
              this.bombsOut = true;
            if (this.Group != null) {
              this.Group.waitGroup(this.Group.numInGroup((Aircraft)this.actor));
            }
            else {
              this.AP.way.next();
              set_task(3);
              clear_stack();
              set_maneuver(21);
            }
            push(2);
            pop();
            break label24670;
          }
        }
        switch (this.maneuver) { case 44:
        case 45:
        case 48:
        case 49:
        default:
          break;
        case 43:
          groundAttack(this.target_ground, paramFloat);
          if (this.mn_time <= 120.0F) break;
          set_maneuver(0); break;
        case 50:
          groundAttackDiveBomber(this.target_ground, paramFloat);
          if (this.mn_time <= 120.0F)
            break;
          setSpeedMode(6);
          this.CT.BayDoorControl = 0.0F;
          this.CT.AirBrakeControl = 0.0F;
          this.CT.FlapsControl = 0.0F;
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
        }
      }

      label24670: if (this.checkGround)
        doCheckGround(paramFloat);
      if ((this.checkStrike) && (this.strikeEmer))
        doCheckStrike();
      this.strikeEmer = false;
      setSpeedControl(paramFloat);
      this.first = false;
      this.mn_time += paramFloat;
      if (this.frequentControl)
        this.AP.update(paramFloat);
      else
        this.AP.update(paramFloat * 4.0F);
      if (this.bBusy)
        this.wasBusy = true;
      else
        this.wasBusy = false;
      if (this.shotAtFriend > 0)
        this.shotAtFriend -= 1;
    }
  }

  void OutCT(int paramInt)
  {
    if (this.actor == Main3D.cur3D().viewActor())
    {
      TextScr.output(paramInt + 5, 45, "Alt(MSL)  " + (int)this.Loc.z + "    " + (this.CT.BrakeControl <= 0.0F ? "" : "BRAKE"));
      TextScr.output(paramInt + 5, 65, "Alt(AGL)  " + (int)(this.Loc.z - Engine.land().HQ_Air(this.Loc.x, this.Loc.y)));
      int i = 0;
      TextScr.output(paramInt + 225, 140, "---ENGINES (" + this.EI.getNum() + ")---" + this.EI.engines[i].getStage());
      TextScr.output(paramInt + 245, 120, "THTL " + (int)(100.0F * this.EI.engines[i].getControlThrottle()) + "%" + (this.EI.engines[i].getControlAfterburner() ? " (NITROS)" : ""));
      TextScr.output(paramInt + 245, 100, "PROP " + (int)(100.0F * this.EI.engines[i].getControlProp()) + "%" + (this.CT.getStepControlAuto() ? " (AUTO)" : ""));
      TextScr.output(paramInt + 245, 80, "MIX " + (int)(100.0F * this.EI.engines[i].getControlMix()) + "%");
      TextScr.output(paramInt + 245, 60, "RAD " + (int)(100.0F * this.EI.engines[i].getControlRadiator()) + "%" + (this.CT.getRadiatorControlAuto() ? " (AUTO)" : ""));
      TextScr.output(paramInt + 245, 40, "SUPC " + this.EI.engines[i].getControlCompressor() + "x");
      TextScr.output(245, 20, "PropAoA :" + (int)Math.toDegrees(this.EI.engines[i].getPropAoA()));
      TextScr.output(paramInt + 455, 120, "Cyls/Cams " + this.EI.engines[i].getCylindersOperable() + "/" + this.EI.engines[0].getCylinders());
      TextScr.output(paramInt + 455, 100, "Readyness " + (int)(100.0F * this.EI.engines[i].getReadyness()) + "%");
      TextScr.output(paramInt + 455, 80, "PRM " + (int)((int)(this.EI.engines[i].getRPM() * 0.02F) * 50.0F) + " rpm");
      TextScr.output(paramInt + 455, 60, "Thrust " + (int)this.EI.engines[i].getEngineForce().x + " N");
      TextScr.output(paramInt + 455, 40, "Fuel " + (int)(100.0F * this.M.fuel / this.M.maxFuel) + "% Nitro " + (int)(100.0F * this.M.nitro / this.M.maxNitro) + "%");
      TextScr.output(paramInt + 455, 20, "MPrs " + (int)(1000.0F * this.EI.engines[i].getManifoldPressure()) + " mBar");
      TextScr.output(paramInt + 640, 140, "---Controls---");
      TextScr.output(paramInt + 640, 120, "A/C: " + (this.CT.bHasAileronControl ? "" : "AIL ") + (this.CT.bHasElevatorControl ? "" : "ELEV ") + (this.CT.bHasRudderControl ? "" : "RUD ") + (this.Gears.bIsHydroOperable ? "" : "GEAR "));
      TextScr.output(paramInt + 640, 100, "ENG: " + (this.EI.engines[i].isHasControlThrottle() ? "" : "THTL ") + (this.EI.engines[i].isHasControlProp() ? "" : "PROP ") + (this.EI.engines[i].isHasControlMix() ? "" : "MIX ") + (this.EI.engines[i].isHasControlCompressor() ? "" : "SUPC ") + (this.EI.engines[i].isPropAngleDeviceOperational() ? "" : "GVRNR "));

      TextScr.output(paramInt + 640, 80, "PIL: (" + (int)(this.AS.getPilotHealth(0) * 100.0F) + "%)");
      TextScr.output(paramInt + 5, 105, "V   " + (int)getSpeedKMH());
      TextScr.output(paramInt + 5, 125, "AOA " + (int)(getAOA() * 1000.0F) / 1000.0F);
      TextScr.output(paramInt + 5, 145, "AOS " + (int)(getAOS() * 1000.0F) / 1000.0F);
      TextScr.output(paramInt + 5, 165, "Kr  " + (int)this.Or.getKren());
      TextScr.output(paramInt + 5, 185, "Ta  " + (int)this.Or.getTangage());
      TextScr.output(paramInt + 250, 185, "way.speed  " + this.AP.way.curr().getV() * 3.6F + "way.z " + this.AP.way.curr().z() + "  mn_time = " + this.mn_time);
      TextScr.output(paramInt + 5, 205, "<" + this.actor.name() + ">: " + ManString.tname(this.task) + ":" + ManString.name(this.maneuver) + " , WP=" + this.AP.way.Cur() + "(" + (this.AP.way.size() - 1) + ")-" + ManString.wpname(this.AP.way.curr().Action));
      TextScr.output(paramInt + 7, 225, "======= " + ManString.name(this.program[0]) + "  Sub = " + this.submaneuver + " follOffs.x = " + this.followOffset.x + "  " + (((AutopilotAI)this.AP).bWayPoint ? "Stab WPoint " : "") + (((AutopilotAI)this.AP).bStabAltitude ? "Stab ALT " : "") + (((AutopilotAI)this.AP).bStabDirection ? "Stab DIR " : "") + (((AutopilotAI)this.AP).bStabSpeed ? "Stab SPD " : "   ") + (((Pilot)((Aircraft)this.actor).FM).isDumb() ? "DUMB " : " ") + (((Pilot)((Aircraft)this.actor).FM).Gears.lgear ? "L " : " ") + (((Pilot)((Aircraft)this.actor).FM).Gears.rgear ? "R " : " ") + (((Pilot)((Aircraft)this.actor).FM).TaxiMode ? "TaxiMode" : ""));

      TextScr.output(paramInt + 7, 245, " ====== " + ManString.name(this.program[1]));
      TextScr.output(paramInt + 7, 265, "  ===== " + ManString.name(this.program[2]));
      TextScr.output(paramInt + 7, 285, "   ==== " + ManString.name(this.program[3]));
      TextScr.output(paramInt + 7, 305, "    === " + ManString.name(this.program[4]));
      TextScr.output(paramInt + 7, 325, "     == " + ManString.name(this.program[5]));
      TextScr.output(paramInt + 7, 345, "      = " + ManString.name(this.program[6]) + "  " + (this.target != null ? "TARGET  " : "") + (this.target_ground != null ? "GROUND  " : "") + (this.danger != null ? "DANGER  " : ""));
      if ((this.target != null) && (Actor.isValid(this.target.actor)))
        TextScr.output(paramInt + 1, 365, " AT: (" + this.target.actor.name() + ") " + ((this.target.actor instanceof Aircraft) ? "" : this.target.actor.getClass().getName()));
      if ((this.target_ground != null) && (Actor.isValid(this.target_ground)))
        TextScr.output(paramInt + 1, 385, " GT: (" + this.target_ground.name() + ") ..." + this.target_ground.getClass().getName());
      TextScr.output(400, 500, "+");
      TextScr.output(400, 400, "|");
      TextScr.output((int)(400.0F + 200.0F * this.CT.AileronControl), (int)(500.0F - 200.0F * this.CT.ElevatorControl), "+");
      TextScr.output((int)(400.0F + 200.0F * this.CT.RudderControl), 400, "|");
      TextScr.output(250, 780, "followOffset  " + this.followOffset.x + "  " + this.followOffset.y + "  " + this.followOffset.z + "  ");
      if ((this.Group != null) && (this.Group.enemies != null))
        TextScr.output(250, 760, "Enemies   " + AirGroupList.length(this.Group.enemies[0]));
      TextScr.output(700, 780, "speedMode   " + this.speedMode);
      if (this.Group != null)
        TextScr.output(700, 760, "Group task  " + this.Group.grTaskName());
      if (this.AP.way.isLandingOnShip())
        TextScr.output(5, 460, "Landing On Carrier");
      TextScr.output(700, 740, "gattackCounter  " + this.gattackCounter);
      TextScr.output(5, 360, "silence = " + this.silence);
    }
  }

  private void groundAttackGuns(Actor paramActor, float paramFloat)
  {
    float f1;
    if ((this.submaneuver == 0) && (this.sub_Man_Count == 0) && (this.CT.Weapons[1] != null))
    {
      f1 = ((GunGeneric)this.CT.Weapons[1][0]).bulletSpeed();
      if (f1 > 0.01F)
        this.bullTime = (1.0F / f1);
    }
    this.maxAOA = 15.0F;
    this.minElevCoeff = 20.0F;
    switch (this.submaneuver)
    {
    case 0:
      setCheckGround(true);
      this.rocketsDelay = 0;
      if (this.sub_Man_Count == 0)
      {
        this.Vtarg.set(paramActor.pos.getAbsPoint());
        paramActor.getSpeed(tmpV3d);
        tmpV3f.x = (float)tmpV3d.x;
        tmpV3f.y = (float)tmpV3d.y;
        tmpV3f.z = (float)tmpV3d.z;
        tmpV3f.z = 0.0D;
        if (tmpV3f.length() < 9.999999747378752E-005D)
        {
          tmpV3f.sub(this.Vtarg, this.Loc);
          tmpV3f.z = 0.0D;
        }
        tmpV3f.normalize();
        this.Vtarg.x -= tmpV3f.x * 1500.0D;
        this.Vtarg.y -= tmpV3f.y * 1500.0D;
        this.Vtarg.z += 400.0D;
        this.constVtarg.set(this.Vtarg);
        Ve.sub(this.constVtarg, this.Loc);
        Ve.normalize();
        this.Vxy.cross(Ve, tmpV3f);
        Ve.sub(tmpV3f);
        this.Vtarg.z += 100.0D;
        if (this.Vxy.z > 0.0D)
        {
          this.Vtarg.x += Ve.y * 1000.0D;
          this.Vtarg.y -= Ve.x * 1000.0D;
        }
        else
        {
          this.Vtarg.x -= Ve.y * 1000.0D;
          this.Vtarg.y += Ve.x * 1000.0D;
        }
        this.constVtarg1.set(this.Vtarg);
      }
      Ve.set(this.constVtarg1);
      Ve.sub(this.Loc);
      f1 = (float)Ve.length();
      this.Or.transformInv(Ve);
      if (Ve.x < 0.0D)
      {
        push(0);
        push(8);
        pop();
        this.dontSwitch = true;
      }
      else
      {
        Ve.normalize();
        setSpeedMode(4);
        this.smConstSpeed = 100.0F;
        farTurnToDirection();
        this.sub_Man_Count += 1;
        if (f1 >= 300.0F)
          break;
        this.submaneuver += 1;
        this.gattackCounter += 1;
        this.sub_Man_Count = 0; } break;
    case 1:
      Ve.set(this.constVtarg);
      Ve.sub(this.Loc);
      f1 = (float)Ve.length();
      this.Or.transformInv(Ve);
      Ve.normalize();
      setSpeedMode(4);
      this.smConstSpeed = 100.0F;
      farTurnToDirection();
      this.sub_Man_Count += 1;
      if (f1 >= 300.0F)
        break;
      this.submaneuver += 1;
      this.gattackCounter += 1;
      this.sub_Man_Count = 0; break;
    case 2:
      if (this.rocketsDelay > 0)
        this.rocketsDelay -= 1;
      if (this.sub_Man_Count > 100)
        setCheckGround(false);
      P.set(paramActor.pos.getAbsPoint());
      P.z += 4.0D;
      Engine.land();
      if (Landscape.rayHitHQ(this.Loc, P, P))
      {
        push(0);
        push(38);
        pop();
        this.gattackCounter -= 1;
        if (this.gattackCounter < 0)
          this.gattackCounter = 0;
      }
      Ve.sub(paramActor.pos.getAbsPoint(), this.Loc);
      this.Vtarg.set(Ve);
      f1 = (float)Ve.length();
      paramActor.getSpeed(tmpV3d);
      tmpV3f.x = (float)tmpV3d.x;
      tmpV3f.y = (float)tmpV3d.y;
      tmpV3f.z = (float)tmpV3d.z;
      tmpV3f.sub(this.Vwld);
      tmpV3f.scale(f1 * this.bullTime * 0.3333F * this.Skill);
      Ve.add(tmpV3f);
      float f2 = 0.3F * (f1 - 1000.0F);
      if (f2 < 0.0F)
        f2 = 0.0F;
      if (f2 > 300.0F)
        f2 = 300.0F;
      Ve.z += f2 + World.cur().rnd.nextFloat(-3.0F, 3.0F) * (3 - this.Skill);
      this.Or.transformInv(Ve);
      if ((f1 < 800.0F) && ((this.shotAtFriend <= 0) || (this.distToFriend > f1)) && (Math.abs(Ve.y) < 15.0D) && (Math.abs(Ve.z) < 10.0D))
      {
        if (f1 < 550.0F)
          this.CT.WeaponControl[0] = true;
        if (f1 < 450.0F)
          this.CT.WeaponControl[1] = true;
        if ((this.CT.Weapons[2] != null) && (this.CT.Weapons[2][0] != null) && (this.CT.Weapons[2][(this.CT.Weapons[2].length - 1)].countBullets() != 0) && (this.rocketsDelay < 1) && (f1 < 500.0F))
        {
          this.CT.WeaponControl[2] = true;
          Voice.speakAttackByRockets((Aircraft)this.actor);
          this.rocketsDelay += 30;
        }
      }
      if (((this.sub_Man_Count > 200) && (Ve.x < 200.0D)) || (this.Alt < 40.0F))
      {
        if ((this.Leader == null) || (!Actor.isAlive(this.Leader.actor)))
          Voice.speakEndGattack((Aircraft)this.actor);
        this.rocketsDelay = 0;
        push(0);
        push(55);
        push(10);
        pop();
        this.dontSwitch = true;
      }
      else
      {
        Ve.normalize();
        attackTurnToDirection(f1, paramFloat, 4.0F + this.Skill * 2.0F);
        setSpeedMode(4);
        this.smConstSpeed = 100.0F;
      }break;
    default:
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }
  }

  private void groundAttack(Actor paramActor, float paramFloat)
  {
    setSpeedMode(4);
    this.smConstSpeed = 120.0F;
    float f1 = this.Vwld.z <= 0.0D ? 4.0F : 3.0F;
    int i = 0;
    if ((this.CT.Weapons[3] != null) && (this.CT.Weapons[3][0] != null) && (this.CT.Weapons[3][0].countBullets() != 0)) {
      i = 1;
    } else if ((!(this.actor instanceof TypeStormovik)) && (!(this.actor instanceof TypeFighter)) && (!(this.actor instanceof TypeDiveBomber)))
    {
      set_maneuver(0);
      return;
    }
    Ve.set(paramActor.pos.getAbsPoint());
    float f2 = (float)this.Loc.z - (float)Ve.z;
    if (f2 < 0.0F)
      f2 = 0.0F;
    float f3 = (float)Math.sqrt(2.0F * f2 * 0.1019F) + 0.0017F * f2;
    paramActor.getSpeed(tmpV3d);
    if (((paramActor instanceof Aircraft)) && (tmpV3d.length() > 20.0D))
    {
      this.target = ((Aircraft)paramActor).FM;
      set_task(6);
      clear_stack();
      set_maneuver(27);
      this.dontSwitch = true;
    }
    else
    {
      float f4 = 0.5F;
      if (i != 0)
        f4 = (f3 + 5.0F) * 0.33333F;
      this.Vtarg.x = ((float)tmpV3d.x * f4 * this.Skill);
      this.Vtarg.y = ((float)tmpV3d.y * f4 * this.Skill);
      this.Vtarg.z = ((float)tmpV3d.z * f4 * this.Skill);
      Ve.add(this.Vtarg);
      Ve.sub(this.Loc);
      Ve.add(0.0D, 0.0D, -0.5F + World.Rnd().nextFloat(-2.0F, 0.8F));
      Vf.set(Ve);
      float f5 = (float)Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
      float f6;
      if (i != 0)
      {
        f6 = getSpeed() * f3 + 500.0F;
        if (f5 > f6)
          Ve.z += 200.0D;
        else
          Ve.z = 0.0D;
      }
      this.Vtarg.set(Ve);
      this.Vtarg.normalize();
      this.Or.transformInv(Ve);
      if (i == 0) {
        groundAttackGuns(paramActor, paramFloat);
      } else if (((this.actor instanceof TypeFighter)) || ((this.actor instanceof TypeStormovik))) {
        groundAttackShallowDive(paramActor, paramFloat);
      }
      else {
        Ve.normalize();
        Vpl.set(this.Vwld);
        Vpl.normalize();
        this.CT.BayDoorControl = 1.0F;
        if ((f5 + 4.0F * f3 < getSpeed() * f3) && (Ve.x > 0.0D) && (Vpl.dot(this.Vtarg) > 0.9800000190734863D))
        {
          if (!this.bombsOut)
          {
            this.bombsOut = true;
            if ((this.CT.Weapons[3] != null) && (this.CT.Weapons[3][0] != null) && (this.CT.Weapons[3][0].countBullets() != 0) && (!(this.CT.Weapons[3][0] instanceof BombGunPara)))
              Voice.speakAttackByBombs((Aircraft)this.actor);
          }
          push(0);
          push(55);
          push(48);
          pop();
          Voice.speakEndGattack((Aircraft)this.actor);
          this.CT.BayDoorControl = 0.0F;
        }
        if (Ve.x < 0.0D)
        {
          push(0);
          push(55);
          push(10);
          pop();
        }
        if (Math.abs(Ve.y) > 0.1000000014901161D)
          this.CT.AileronControl = (-(float)Math.atan2(Ve.y, Ve.z) - 0.016F * this.Or.getKren());
        else
          this.CT.AileronControl = (-(float)Math.atan2(Ve.y, Ve.x) - 0.016F * this.Or.getKren());
        if (Math.abs(Ve.y) > 0.001000000047497451D)
          this.CT.RudderControl = (-98.0F * (float)Math.atan2(Ve.y, Ve.x));
        else
          this.CT.RudderControl = 0.0F;
        if (this.CT.RudderControl * this.W.z > 0.0D)
          this.W.z = 0.0D;
        else
          this.W.z *= 1.039999961853027D;
        f6 = (float)Math.atan2(Ve.z, Ve.x);
        if ((Math.abs(Ve.y) < 0.002000000094994903D) && (Math.abs(Ve.z) < 0.002000000094994903D))
          f6 = 0.0F;
        if (Ve.x < 0.0D) {
          f6 = 1.0F;
        }
        else {
          if (f6 * this.W.y > 0.0D)
            this.W.y = 0.0D;
          if (f6 < 0.0F)
          {
            if (getOverload() < 0.1F)
              f6 = 0.0F;
            if (this.CT.ElevatorControl > 0.0F)
              this.CT.ElevatorControl = 0.0F;
          }
          else if (this.CT.ElevatorControl < 0.0F) {
            this.CT.ElevatorControl = 0.0F;
          }
        }
        if ((getOverload() > this.maxG) || (this.AOA > f1) || (this.CT.ElevatorControl > f6))
          this.CT.ElevatorControl -= 0.2F * paramFloat;
        else
          this.CT.ElevatorControl += 0.2F * paramFloat;
      }
    }
  }

  private void groundAttackKamikaze(Actor paramActor, float paramFloat)
  {
    float f1;
    if ((this.submaneuver == 0) && (this.sub_Man_Count == 0) && (this.CT.Weapons[1] != null))
    {
      f1 = ((GunGeneric)this.CT.Weapons[1][0]).bulletSpeed();
      if (f1 > 0.01F)
        this.bullTime = (1.0F / f1);
    }
    this.maxAOA = 15.0F;
    this.minElevCoeff = 20.0F;
    switch (this.submaneuver)
    {
    case 0:
      setCheckGround(true);
      this.rocketsDelay = 0;
      if (this.sub_Man_Count == 0)
      {
        this.Vtarg.set(paramActor.pos.getAbsPoint());
        tmpV3f.set(this.Vwld);
        tmpV3f.x += World.Rnd().nextFloat(-100.0F, 100.0F);
        tmpV3f.y += World.Rnd().nextFloat(-100.0F, 100.0F);
        tmpV3f.z = 0.0D;
        if (tmpV3f.length() < 9.999999747378752E-005D)
        {
          tmpV3f.sub(this.Vtarg, this.Loc);
          tmpV3f.z = 0.0D;
        }
        tmpV3f.normalize();
        this.Vtarg.x -= tmpV3f.x * 1500.0D;
        this.Vtarg.y -= tmpV3f.y * 1500.0D;
        this.Vtarg.z += 400.0D;
        this.constVtarg.set(this.Vtarg);
        Ve.sub(this.constVtarg, this.Loc);
        Ve.normalize();
        this.Vxy.cross(Ve, tmpV3f);
        Ve.sub(tmpV3f);
        this.Vtarg.z += 100.0D;
        if (this.Vxy.z > 0.0D)
        {
          this.Vtarg.x += Ve.y * 1000.0D;
          this.Vtarg.y -= Ve.x * 1000.0D;
        }
        else
        {
          this.Vtarg.x -= Ve.y * 1000.0D;
          this.Vtarg.y += Ve.x * 1000.0D;
        }
        this.constVtarg1.set(this.Vtarg);
      }
      Ve.set(this.constVtarg1);
      Ve.sub(this.Loc);
      f1 = (float)Ve.length();
      this.Or.transformInv(Ve);
      if (Ve.x < 0.0D)
      {
        push(0);
        push(8);
        pop();
        this.dontSwitch = true;
      }
      else
      {
        Ve.normalize();
        setSpeedMode(6);
        farTurnToDirection();
        this.sub_Man_Count += 1;
        if (f1 < 300.0F)
        {
          this.submaneuver += 1;
          this.gattackCounter += 1;
          this.sub_Man_Count = 0;
        }
        if (this.sub_Man_Count <= 1000) break;
        this.sub_Man_Count = 0; } break;
    case 1:
      setCheckGround(true);
      Ve.set(this.constVtarg);
      Ve.sub(this.Loc);
      f1 = (float)Ve.length();
      this.Or.transformInv(Ve);
      Ve.normalize();
      setSpeedMode(6);
      farTurnToDirection();
      this.sub_Man_Count += 1;
      if (f1 < 300.0F)
      {
        this.submaneuver += 1;
        this.gattackCounter += 1;
        this.sub_Man_Count = 0;
      }
      if (this.sub_Man_Count <= 700)
        break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 2:
      setCheckGround(false);
      if (this.rocketsDelay > 0)
        this.rocketsDelay -= 1;
      if (this.sub_Man_Count > 100)
        setCheckGround(false);
      Ve.set(paramActor.pos.getAbsPoint());
      Ve.sub(this.Loc);
      this.Vtarg.set(Ve);
      f1 = (float)Ve.length();
      if ((this.actor instanceof MXY_7))
      {
        Ve.z += 0.01D * f1;
        this.Vtarg.z += 0.01D * f1;
      }
      paramActor.getSpeed(tmpV3d);
      tmpV3f.x = (float)tmpV3d.x;
      tmpV3f.y = (float)tmpV3d.y;
      tmpV3f.z = (float)tmpV3d.z;
      tmpV3f.sub(this.Vwld);
      tmpV3f.scale(f1 * this.bullTime * 0.3333F * this.Skill);
      Ve.add(tmpV3f);
      float f2 = 0.3F * (f1 - 1000.0F);
      if (f2 < 0.0F)
        f2 = 0.0F;
      if (f2 > 300.0F)
        f2 = 300.0F;
      Ve.z += f2 + World.cur().rnd.nextFloat(-3.0F, 3.0F) * (3 - this.Skill);
      this.Or.transformInv(Ve);
      if ((f1 < 50.0F) && (Math.abs(Ve.y) < 40.0D) && (Math.abs(Ve.z) < 30.0D))
      {
        this.CT.WeaponControl[0] = true;
        this.CT.WeaponControl[1] = true;
        this.CT.WeaponControl[2] = true;
        this.CT.WeaponControl[3] = true;
      }
      if (Ve.x < -50.0D)
      {
        this.rocketsDelay = 0;
        push(0);
        push(55);
        push(10);
        pop();
        this.dontSwitch = true;
      }
      else
      {
        Ve.normalize();
        attackTurnToDirection(f1, paramFloat, 4.0F + this.Skill * 2.0F);
        setSpeedMode(4);
        this.smConstSpeed = 130.0F;
      }break;
    default:
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }
  }

  private void groundAttackTinyTim(Actor paramActor, float paramFloat)
  {
    this.maxG = 5.0F;
    this.maxAOA = 8.0F;
    setSpeedMode(4);
    this.smConstSpeed = 120.0F;
    this.minElevCoeff = 20.0F;
    double d;
    switch (this.submaneuver)
    {
    case 0:
      if (this.sub_Man_Count == 0)
      {
        this.Vtarg.set(paramActor.pos.getAbsPoint());
        paramActor.getSpeed(tmpV3d);
        if (tmpV3d.length() < 0.5D)
          tmpV3d.sub(this.Vtarg, this.Loc);
        tmpV3d.normalize();
        this.Vtarg.x -= tmpV3d.x * 3000.0D;
        this.Vtarg.y -= tmpV3d.y * 3000.0D;
        this.Vtarg.z += 500.0D;
      }
      Ve.sub(this.Vtarg, this.Loc);
      d = Ve.length();
      this.Or.transformInv(Ve);
      this.sub_Man_Count += 1;
      if (d < 1000.0D)
      {
        this.submaneuver += 1;
        this.sub_Man_Count = 0;
      }
      Ve.normalize();
      farTurnToDirection();
      break;
    case 1:
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      this.Vtarg.z += 80.0D;
      Ve.sub(this.Vtarg, this.Loc);
      d = Ve.length();
      this.Or.transformInv(Ve);
      this.sub_Man_Count += 1;
      if (d < 1500.0D)
      {
        if ((Math.abs(Ve.y) < 40.0D) && (Math.abs(Ve.z) < 30.0D))
          this.CT.WeaponControl[2] = true;
        push(0);
        push(10);
        push(48);
        pop();
        this.dontSwitch = true;
      }
      if ((d < 500.0D) && (Ve.x < 0.0D))
      {
        push(0);
        push(10);
        pop();
      }
      Ve.normalize();
      if (Ve.x < 0.800000011920929D)
        turnToDirection(paramFloat);
      else
        attackTurnToDirection((float)d, paramFloat, 2.0F + this.Skill * 1.5F);
      break;
    default:
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }
  }

  private void groundAttackShallowDive(Actor paramActor, float paramFloat)
  {
    this.maxAOA = 8.0F;

    if (!hasBombs())
    {
      set_maneuver(0);
      wingman(true);
    }
    else
    {
      if (this.first)
        this.RandomVal = (50.0F - World.cur().rnd.nextFloat(0.0F, 100.0F));
      setSpeedMode(4);
      this.smConstSpeed = 120.0F;
      Ve.set(paramActor.pos.getAbsPoint());
      Ve.sub(this.Loc);
      float f1 = (float)(-Ve.z);
      if (f1 < 0.0F)
        f1 = 0.0F;
      Ve.z += 250.0D;

      float f2 = (float)Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y) + this.RandomVal * (float)(2.75D - this.Skill);

      if (Ve.z < -0.1F * f2)
        Ve.z = (-0.1F * f2);
      if (this.Alt + Ve.z < 250.0D)
        Ve.z = (250.0F - this.Alt);
      if (this.Alt < 50.0F)
      {
        push(10);
        pop();
      }
      Vf.set(Ve);
      this.CT.BayDoorControl = 1.0F;
      float f3 = (float)this.Vwld.z * 0.1019F;
      f3 += (float)Math.sqrt(f3 * f3 + 2.0F * f1 * 0.1019F);
      float f4 = (float)Math.sqrt(this.Vwld.x * this.Vwld.x + this.Vwld.y * this.Vwld.y);
      float f5 = f4 * f3 + 10.0F;
      paramActor.getSpeed(tmpV3d);

      tmpV3d.scale(f3 * 0.45D * this.Skill);

      Ve.x += (float)tmpV3d.x;
      Ve.y += (float)tmpV3d.y;
      Ve.z += (float)tmpV3d.z;
      if (f5 >= f2)
      {
        this.bombsOut = true;
        Voice.speakAttackByBombs((Aircraft)this.actor);
        setSpeedMode(6);
        this.CT.BayDoorControl = 0.0F;
        push(0);
        push(55);
        push(48);
        pop();
        this.sub_Man_Count = 0;
      }
      this.Or.transformInv(Ve);
      Ve.normalize();
      turnToDirection(paramFloat);
    }
  }

  private void groundAttackDiveBomber(Actor paramActor, float paramFloat)
  {
    this.maxG = 5.0F;
    this.maxAOA = 10.0F;
    setSpeedMode(6);
    this.maxAOA = 4.0F;
    this.minElevCoeff = 20.0F;
    if ((this.CT.Weapons[3] == null) || (this.CT.getWeaponCount(3) == 0))
    {
      if (this.AP.way.curr().Action == 3)
        this.AP.way.next();
      set_maneuver(0);
      wingman(true);
    }
    else
    {
      if (this.Alt < 350.0F)
      {
        this.bombsOut = true;
        Voice.speakAttackByBombs((Aircraft)this.actor);
        setSpeedMode(6);
        this.CT.BayDoorControl = 0.0F;
        this.CT.AirBrakeControl = 0.0F;
        this.AP.way.next();
        push(0);
        push(8);
        push(2);
        pop();
        this.sub_Man_Count = 0;
      }
      Ve.set(paramActor.pos.getAbsPoint());
      Ve.sub(this.Loc);
      float f1 = (float)(-Ve.z);
      float f2 = (float)Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y + Ve.z * Ve.z);
      float f3 = (float)Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
      if ((f3 > 1000.0F) || ((this.submaneuver == 3) && (this.sub_Man_Count > 100)))
      {
        this.Vtarg.set(paramActor.pos.getAbsPoint());
        paramActor.getSpeed(tmpV3d);
        f4 = 0.0F;
        switch (this.submaneuver)
        {
        case 0:
          f4 = f3 / 40.0F + 4.0F + this.Alt / 48.0F;
        case 1:
          f4 = (f3 - this.man1Dist) / (float)this.Vwld.length() + 4.0F + this.Alt / 48.0F;
        case 2:
          f4 = this.Alt / 60.0F;
        case 3:
          f4 = this.Alt / 120.0F;
        }

        f4 *= 0.33333F;
        this.Vtarg.x += (float)tmpV3d.x * f4 * this.Skill;
        this.Vtarg.y += (float)tmpV3d.y * f4 * this.Skill;
        this.Vtarg.z += (float)tmpV3d.z * f4 * this.Skill;
      }

      Ve.set(this.Vtarg);
      Ve.sub(this.Loc);
      float f4 = (float)(-Ve.z);
      if (f4 < 0.0F)
        f4 = 0.0F;
      Ve.add(this.Vxy);
      f1 = (float)(-Ve.z);
      f2 = (float)Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y + Ve.z * Ve.z);
      f3 = (float)Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
      if (this.submaneuver < 2)
        Ve.z = 0.0D;
      Vf.set(Ve);
      Ve.normalize();
      Vpl.set(this.Vwld);
      Vpl.normalize();
      float f5;
      float f6;
      switch (this.submaneuver)
      {
      default:
        break;
      case 0:
        push();
        pop();
        if (f1 < 1200.0F)
          this.man1Dist = 400.0F;
        else if (f1 > 4500.0F)
          this.man1Dist = 50.0F;
        else
          this.man1Dist = (50.0F + 350.0F * (4500.0F - f1) / 3300.0F);
        f5 = 0.01F * f1;
        if (f5 < 10.0F)
          f5 = 10.0F;
        this.Vxy.set(World.Rnd().nextFloat(-10.0F, 10.0F), World.Rnd().nextFloat(-10.0F, 10.0F), World.Rnd().nextFloat(5.0F, f5));
        this.Vxy.scale(4.0F - this.Skill);
        f6 = 2.0E-005F * f1 * f1;
        if (f6 < 60.0F)
          f6 = 60.0F;
        if (f6 > 350.0F)
          f6 = 350.0F;
        this.Vxy.z += f6;
        this.submaneuver += 1;
        break;
      case 1:
        setSpeedMode(4);
        this.smConstSpeed = 110.0F;
        if (f3 >= this.man1Dist)
          break;
        this.CT.AirBrakeControl = 1.0F;
        if ((this.actor instanceof TypeFighter))
          this.CT.FlapsControl = 1.0F;
        push();
        push(6);
        safe_pop();
        this.submaneuver += 1; break;
      case 2:
        setSpeedMode(4);
        this.smConstSpeed = 110.0F;
        this.sub_Man_Count += 1;
        this.CT.AirBrakeControl = 1.0F;
        if ((this.actor instanceof TypeFighter))
          this.CT.FlapsControl = 1.0F;
        f5 = this.Or.getKren();
        if (this.Or.getTangage() > -90.0F)
        {
          f5 -= 180.0F;
          if (f5 < -180.0F)
            f5 += 360.0F;
        }
        this.CT.AileronControl = (float)(-0.04F * f5 - 0.5D * getW().x);
        if (getOverload() < 4.0F)
          this.CT.ElevatorControl += 0.3F * paramFloat;
        else
          this.CT.ElevatorControl -= 0.3F * paramFloat;
        if (((this.sub_Man_Count <= 30) || (this.Or.getTangage() >= -90.0F)) && (this.sub_Man_Count <= 150))
          break;
        this.sub_Man_Count = 0;
        this.submaneuver += 1; break;
      case 3:
        this.CT.AirBrakeControl = 1.0F;
        if ((this.actor instanceof TypeFighter))
          this.CT.FlapsControl = 1.0F;
        this.CT.BayDoorControl = 1.0F;
        setSpeedMode(4);
        this.smConstSpeed = 110.0F;
        this.sub_Man_Count += 1;
        if (this.sub_Man_Count > 80)
        {
          f5 = (float)this.Vwld.z * 0.1019F;
          f5 = f5 + (float)Math.sqrt(f5 * f5 + 2.0F * f4 * 0.1019F) + 0.00035F * f4;
          f6 = (float)Math.sqrt(this.Vwld.x * this.Vwld.x + this.Vwld.y * this.Vwld.y);
          float f7 = f6 * f5;
          float f8 = 0.2F * (f3 - f7);
          this.Vxy.z += f8;
          if (this.Vxy.z > 0.7F * f4)
            this.Vxy.z = (0.7F * f4);
        }
        if (((this.sub_Man_Count <= 100) || (this.Alt >= 1000.0F) || (Vpl.dot(Ve) <= 0.9900000095367432D)) && (this.Alt >= 600.0F))
          break;
        this.bombsOut = true;
        Voice.speakAttackByBombs((Aircraft)this.actor);
        this.CT.BayDoorControl = 0.0F;
        this.CT.AirBrakeControl = 0.0F;
        this.AP.way.next();
        push(0);
        push(8);
        push(2);
        pop();
      }

      this.Or.transformInv(Ve);
      Ve.normalize();
      if (this.submaneuver == 3)
        attackTurnToDirection(1000.0F, paramFloat, 30.0F);
      else if (this.submaneuver != 2)
        turnToDirection(paramFloat);
    }
  }

  private void groundAttackTorpedo(Actor paramActor, float paramFloat)
  {
    this.maxG = 5.0F;
    this.maxAOA = 8.0F;
    setSpeedMode(6);
    this.minElevCoeff = 20.0F;
    Ve.set(paramActor.pos.getAbsPoint());
    Ve.sub(this.Loc);
    double d1 = Ve.x * Ve.x + Ve.y * Ve.y;
    float f1 = (float)Math.sqrt(d1);
    if (this.first)
    {
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      this.submaneuver = 0;
    }
    if ((this.submaneuver == 1) && (this.sub_Man_Count == 0))
    {
      tmpV3f.set(paramActor.pos.getAbsPoint());
      tmpV3f.sub(this.Vtarg);
      paramActor.getSpeed(tmpV3d);
      if (tmpV3f.length() > 9.999999747378752E-005D)
        tmpV3f.normalize();
      this.Vxy.set(tmpV3f.y * 3000.0D, -tmpV3f.x * 3000.0D, 0.0D);
      this.direc = (this.Vxy.dot(Ve) > 0.0D);
      if (this.direc)
        this.Vxy.scale(-1.0D);
      this.Vtarg.add(this.Vxy);
      this.Vtarg.x += tmpV3d.x * 80.0D;
      this.Vtarg.y += tmpV3d.y * 80.0D;
      this.Vtarg.z = 80.0D;
      this.TargDevV.set(World.cur().rnd.nextFloat(-10.0F, 10.0F) * (3.8D - this.Skill), World.cur().rnd.nextFloat(-10.0F, 10.0F) * (3.8D - this.Skill), 0.0D);
    }
    if (this.submaneuver == 2)
    {
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      paramActor.getSpeed(tmpV3d);
      double d2 = tmpV3d.lengthSquared();
      float f4 = 20.0F;
      if ((this.CT.Weapons[3][0] instanceof TorpedoGun))
      {
        TorpedoGun localTorpedoGun = (TorpedoGun)this.CT.Weapons[3][0];
        Class localClass = (Class)Property.value(localTorpedoGun.getClass(), "bulletClass", null);
        if (localClass != null)
          f4 = Property.floatValue(localClass, "velocity", 1.0F);
      }
      double d3 = Math.sqrt(0.204D * this.Loc.z);
      double d4 = 1.0D * d3 * getSpeed();
      double d5 = (780.0D - d4) / f4;
      this.Vtarg.x += (float)(tmpV3d.x * d5);
      this.Vtarg.y += (float)(tmpV3d.y * d5);
      this.Vtarg.z = 20.0D;
      if (this.Loc.z < 30.0D)
        this.Vtarg.z += 3.0D * (30.0D - this.Loc.z);
      this.Vtarg.add(this.TargDevV);
    }
    Ve.set(this.Vtarg);
    Ve.sub(this.Loc);
    float f2 = (float)Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y + Ve.z * Ve.z);
    Vf.set(Ve);
    float f3 = (float)(-Ve.z);
    Vpl.set(this.Vwld);
    Vpl.normalize();
    if (this.Alt < 40.0F)
    {
      if (this.Vwld.z < 0.0D)
        this.Vwld.z *= (-0.2D + 0.025D * this.Alt);
      if (this.Alt < 8.0F)
        set_maneuver(2);
    }
    switch (this.submaneuver)
    {
    default:
      break;
    case 0:
      this.sub_Man_Count += 1;
      if (this.sub_Man_Count <= 60)
        break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 1:
      setSpeedMode(6);
      this.CT.PowerControl = 1.0F;
      this.sub_Man_Count += 1;
      if (f2 >= 1000.0F)
        break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 2:
      setSpeedMode(6);
      if (f2 >= 800.0F)
        break;
      this.CT.WeaponControl[3] = true;
      this.AP.way.next();
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 3:
      setSpeedMode(9);
      this.sub_Man_Count += 1;
      if (this.sub_Man_Count <= 90)
        break;
      this.task = 3;
      push(0);
      push(8);
      pop();
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }

    this.Or.transformInv(Ve);
    if (this.submaneuver == 3)
    {
      if (this.sub_Man_Count < 20)
        Ve.set(1.0D, 0.09000000357627869D, 0.02999999932944775D);
      else
        Ve.set(1.0D, 0.09000000357627869D, 0.009999999776482582D);
      if (!this.direc)
        Ve.y *= -1.0D;
    }
    Ve.normalize();
    turnToDirection(paramFloat);
  }

  private void groundAttackCassette(Actor paramActor, float paramFloat)
  {
    this.maxG = 5.0F;
    this.maxAOA = 8.0F;
    setSpeedMode(4);
    this.smConstSpeed = 120.0F;
    this.minElevCoeff = 20.0F;
    Ve.set(paramActor.pos.getAbsPoint());
    Ve.sub(this.Loc);
    float f1 = (float)Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
    float f4;
    if ((this.submaneuver == 3) && (this.sub_Man_Count > 0) && (this.sub_Man_Count < 45) && (f1 > 200.0F))
    {
      tmpV3f.set(this.Vxy);
      f2 = (float)tmpV3f.dot(Ve);
      tmpV3f.scale(-f2);
      tmpV3f.add(Ve);
      f3 = (float)tmpV3f.length();

      if (f3 > 150.0F)
        f4 = 7.5F / f3;
      else
        f4 = 0.05F;
      tmpV3f.scale(f4);
      tmpV3f.z = 0.0D;
      this.Vwld.add(tmpV3f);
    }
    if (f1 <= 200.0F)
      this.sub_Man_Count = 50;
    if (this.first)
    {
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      this.submaneuver = 0;
    }
    if ((this.submaneuver == 1) && (this.sub_Man_Count == 0))
    {
      tmpV3f.set(paramActor.pos.getAbsPoint());
      paramActor.getSpeed(tmpV3d);
      if (tmpV3d.length() < 0.5D)
        tmpV3d.set(Ve);
      tmpV3d.normalize();
      this.Vxy.set((float)tmpV3d.x, (float)tmpV3d.y, (float)tmpV3d.z);
      this.Vtarg.x -= tmpV3d.x * 3000.0D;
      this.Vtarg.y -= tmpV3d.y * 3000.0D;
      this.Vtarg.z += 250.0D;
    }
    if ((this.submaneuver == 2) && (this.sub_Man_Count == 0))
    {
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      this.Vtarg.x -= this.Vxy.x * 1000.0D;
      this.Vtarg.y -= this.Vxy.y * 1000.0D;
      this.Vtarg.z += 50.0D;
    }
    if ((this.submaneuver == 3) && (this.sub_Man_Count == 0))
    {
      this.checkGround = false;
      this.Vtarg.set(paramActor.pos.getAbsPoint());
      this.Vtarg.x += this.Vxy.x * 1000.0D;
      this.Vtarg.y += this.Vxy.y * 1000.0D;
      this.Vtarg.z += 50.0D;
    }
    Ve.set(this.Vtarg);
    Ve.sub(this.Loc);
    float f2 = (float)Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y + Ve.z * Ve.z);
    Vf.set(Ve);
    float f3 = (float)(-Ve.z);
    this.Or.transformInv(Ve);
    Ve.normalize();
    Vpl.set(this.Vwld);
    Vpl.normalize();
    if (this.Alt < 10.0F)
    {
      push(0);
      push(2);
      pop();
    }
    switch (this.submaneuver)
    {
    case 0:
      setSpeedMode(9);
      this.sub_Man_Count += 1;
      if (this.sub_Man_Count <= 60)
        break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 1:
      setSpeedMode(9);
      this.sub_Man_Count += 1;
      if (f2 >= 1000.0F)
        break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 2:
      setSpeedMode(6);
      this.sub_Man_Count += 1;
      if (f2 < 155.0F)
      {
        this.submaneuver += 1;
        this.sub_Man_Count = 0;
      }
      if (this.sub_Man_Count <= 320)
        break;
      push(0);
      push(10);
      pop(); break;
    case 3:
      setSpeedMode(6);
      this.sub_Man_Count += 1;
      this.Vwld.z *= 0.800000011920929D;
      this.Or.transformInv(this.Vwld);
      this.Vwld.y *= 0.8999999761581421D;
      this.Or.transform(this.Vwld);
      f4 = this.sub_Man_Count;
      if (f4 < 100.0F)
        f4 = 100.0F;
      if (this.Alt > 45.0F)
        this.Vwld.z -= 0.002F * (this.Alt - 45.0F) * f4;
      else
        this.Vwld.z -= 0.005F * (this.Alt - 45.0F) * f4;
      if (this.Alt < 0.0F)
        this.Alt = 0.0F;
      if (f2 < 1080.0F + getSpeed() * (float)Math.sqrt(2.0F * this.Alt / 9.81F))
        this.bombsOut = true;
      if ((Ve.x >= 0.0D) && (f2 >= 350.0F) && (this.sub_Man_Count <= 160))
        break;
      push(0);
      push(10);
      push(10);
      pop();
    }

    if (this.submaneuver == 0)
      Ve.set(1.0D, 0.0D, 0.0D);
    turnToDirection(paramFloat);
  }

  public void modFighterVsFighter(float paramFloat)
  {
    Ve.sub(this.target.Loc, this.Loc);
    float f1 = (float)Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
    this.Or.transformInv(Ve);
    float f2 = (float)Ve.length();
    float f3 = 1.0F / f2;
    this.Vtarg.set(Ve);
    this.Vtarg.scale(f3);
    float f4 = (this.Energy - this.target.Energy) * 0.1019F;
    tmpV3f.sub(this.target.Vwld, this.Vwld);
    float f5;
    if (this.sub_Man_Count == 0)
    {
      f5 = 0.0F;
      if ((this.CT.Weapons[1] != null) && (this.CT.Weapons[1][0].countBullets() > 0))
        f5 = ((GunGeneric)this.CT.Weapons[1][0]).bulletSpeed();
      else if (this.CT.Weapons[0] != null)
        f5 = ((GunGeneric)this.CT.Weapons[0][0]).bulletSpeed();
      if (f5 > 0.01F)
        this.bullTime = (1.0F / f5);
      this.submanDelay = 0;
    }
    float f6;
    float f7;
    if (f1 < 1500.0F)
    {
      f5 = 0.0F;
      f6 = 0.0F;
      if (this.Vtarg.x > -0.2000000029802322D)
      {
        f5 = 3.0F * ((float)this.Vtarg.x + 0.2F);
        this.Vxy.set(tmpV3f);
        this.Vxy.scale(1.0D);
        this.Or.transformInv(this.Vxy);
        this.Vxy.add(Ve);
        this.Vxy.normalize();
        f6 = 10.0F * (float)(this.Vxy.x - this.Vtarg.x);
        if (f6 < -1.0F)
          f6 = -1.0F;
        if (f6 > 1.0F)
          f6 = 1.0F;
      }
      else {
        f5 = 3.0F * ((float)this.Vtarg.x + 0.2F);
      }if ((this.submaneuver == 4) && (this.Vtarg.x < 0.6000000238418579D) && (f2 < 300.0D))
      {
        this.submaneuver = 1;
        this.submanDelay = 30;
      }
      if ((this.submaneuver != 4) && (f4 > 300.0D) && (this.Vtarg.x > 0.75D))
      {
        this.submaneuver = 4;
        this.submanDelay = 240;
      }
      f7 = 0.0015F * f4 + 0.0006F * f1 + f5 + 0.5F * f6;
      if ((f7 > 0.9F) && (this.submanDelay == 0))
      {
        if ((this.Vtarg.x > 0.5D) || (f1 * 2.0D < f2))
        {
          this.submaneuver = 4;
          this.submanDelay = 240;
        }
        else
        {
          this.submaneuver = 3;
          this.submanDelay = 120;
        }
      }
      else if (((f4 > 800.0F) && (this.submaneuver == 0)) || (f4 > 1000.0F))
      {
        Ve.set(0.0D, 0.0D, 800.0D);
        if (this.submanDelay == 0)
        {
          this.submaneuver = 0;
          this.submanDelay = 30;
        }
      }
      else if (((f2 > 450.0F) && (this.submaneuver == 2)) || (f2 > 600.0F))
      {
        if (this.submanDelay == 0)
        {
          this.submaneuver = 2;
          this.submanDelay = 60;
        }
      }
      else if (this.submanDelay == 0)
      {
        this.submaneuver = 1;
        this.submanDelay = 30;
      }
    }
    else if (f1 < 3000.0F)
    {
      if (this.Vtarg.x < 0.5D)
      {
        if (this.submanDelay == 0)
        {
          this.submaneuver = 3;
          this.submanDelay = 120;
        }
      }
      else if (((f4 > 600.0F) && (this.submaneuver == 0)) || (f4 > 800.0F))
      {
        Ve.set(0.0D, 0.0D, 800.0D);
        if (this.submanDelay == 0)
        {
          this.submaneuver = 0;
          this.submanDelay = 120;
        }
      }
      else if (((f4 > -200.0F) && (this.submaneuver >= 4)) || (f4 > -100.0F))
      {
        if (this.submanDelay == 0)
        {
          this.submaneuver = 4;
          this.submanDelay = 120;
        }
      }
      else
      {
        Ve.set(0.0D, 0.0D, this.Loc.z);
        if (this.submanDelay == 0)
        {
          this.submaneuver = 0;
          this.submanDelay = 120;
        }
      }
    }
    else
    {
      Ve.set(0.0D, 0.0D, 1000.0D);
      if (this.submanDelay == 0)
      {
        this.submaneuver = 0;
        this.submanDelay = 180;
      }
    }
    switch (this.submaneuver)
    {
    case 0:
      this.target.Or.transform(Ve);
      Ve.add(this.target.Loc);
      Ve.sub(this.Loc);
      tmpV3f.set(Ve);
      tmpV3f.z = 0.0D;
      f2 = (float)tmpV3f.length();
      tmpV3f.normalize();
      this.Vtarg.set(this.target.Vwld);
      this.Vtarg.z = 0.0D;
      f5 = (float)tmpV3f.dot(this.Vtarg);
      f6 = getSpeed() - f5;
      if (f6 < 10.0F)
        f6 = 10.0F;
      f7 = f2 / f6;
      if (f7 < 0.0F)
        f7 = 0.0F;
      tmpV3f.scale(this.Vtarg, f7);
      Ve.add(tmpV3f);
      this.Or.transformInv(Ve);
      Ve.normalize();
      setSpeedMode(9);
      farTurnToDirection();
      this.sub_Man_Count += 1;
      break;
    case 1:
      setSpeedMode(9);
      this.CT.AileronControl = (-0.04F * this.Or.getKren());
      this.CT.ElevatorControl = (-0.04F * (this.Or.getTangage() + 5.0F));
      this.CT.RudderControl = 0.0F;
      break;
    case 2:
      setSpeedMode(9);
      tmpOr.setYPR(this.Or.getYaw(), 0.0F, 0.0F);
      f5 = 6.0F;
      if (this.Or.getKren() > 0.0F)
        Ve.set(100.0D, -f5, 10.0D);
      else
        Ve.set(100.0D, f5, 10.0D);
      tmpOr.transform(Ve);
      this.Or.transformInv(Ve);
      Ve.normalize();
      farTurnToDirection();
      break;
    case 3:
      this.minElevCoeff = 20.0F;
      setSpeedMode(9);
      tmpOr.setYPR(this.Or.getYaw(), 0.0F, 0.0F);
      Ve.sub(this.target.Loc, this.Loc);
      Ve.z = 0.0D;
      Ve.normalize();
      Ve.z = 0.4D;
      this.Or.transformInv(Ve);
      Ve.normalize();
      attackTurnToDirection(1000.0F, paramFloat, 15.0F);
      break;
    case 4:
      this.minElevCoeff = 20.0F;
      boomAttack(paramFloat);
      setSpeedMode(9);
      break;
    default:
      this.minElevCoeff = 20.0F;
      fighterVsFighter(paramFloat);
    }
    if (this.submanDelay > 0)
      this.submanDelay -= 1;
  }

  public void goodFighterVsFighter(float paramFloat)
  {
    Ve.sub(this.target.Loc, this.Loc);
    float f1 = (float)Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
    this.Or.transformInv(Ve);
    float f2 = (float)Ve.length();
    float f3 = 1.0F / f2;
    this.Vtarg.set(Ve);
    this.Vtarg.scale(f3);
    float f4 = (this.Energy - this.target.Energy) * 0.1019F;
    tmpV3f.sub(this.target.Vwld, this.Vwld);
    float f5;
    if (this.sub_Man_Count == 0)
    {
      f5 = 0.0F;
      if ((this.CT.Weapons[1] != null) && (this.CT.Weapons[1][0].countBullets() > 0))
        f5 = ((GunGeneric)this.CT.Weapons[1][0]).bulletSpeed();
      else if (this.CT.Weapons[0] != null)
        f5 = ((GunGeneric)this.CT.Weapons[0][0]).bulletSpeed();
      if (f5 > 0.01F)
        this.bullTime = (1.0F / f5);
      this.submanDelay = 0;
    }
    float f6;
    float f7;
    if (f1 < 1500.0F)
    {
      f5 = 0.0F;
      f6 = 0.0F;
      if (this.Vtarg.x > -0.2000000029802322D)
      {
        f5 = 3.0F * ((float)this.Vtarg.x + 0.2F);
        this.Vxy.set(tmpV3f);
        this.Vxy.scale(1.0D);
        this.Or.transformInv(this.Vxy);
        this.Vxy.add(Ve);
        this.Vxy.normalize();
        f6 = 10.0F * (float)(this.Vxy.x - this.Vtarg.x);
        if (f6 < -1.0F)
          f6 = -1.0F;
        if (f6 > 1.0F)
          f6 = 1.0F;
      }
      else {
        f5 = 3.0F * ((float)this.Vtarg.x + 0.2F);
      }if ((this.submaneuver == 4) && (this.Vtarg.x < 0.6000000238418579D) && (f2 < 300.0D))
      {
        this.submaneuver = 1;
        this.submanDelay = 30;
      }
      if ((this.submaneuver != 4) && (f4 > 300.0D) && (this.Vtarg.x > 0.75D))
      {
        this.submaneuver = 4;
        this.submanDelay = 240;
      }
      f7 = 0.0015F * f4 + 0.0006F * f1 + f5 + 0.5F * f6;
      if ((f7 > 0.9F) && (this.submanDelay == 0))
      {
        if ((this.Vtarg.x > 0.5D) || (f1 * 2.0D < f2))
        {
          this.submaneuver = 4;
          this.submanDelay = 240;
        }
        else
        {
          this.submaneuver = 3;
          this.submanDelay = 120;
        }
      }
      else if (((f4 > 800.0F) && (this.submaneuver == 0)) || (f4 > 1000.0F))
      {
        Ve.set(0.0D, 0.0D, 800.0D);
        if (this.submanDelay == 0)
        {
          this.submaneuver = 0;
          this.submanDelay = 30;
        }
      }
      else if (((f2 > 450.0F) && (this.submaneuver == 2)) || (f2 > 600.0F))
      {
        if (this.submanDelay == 0)
        {
          this.submaneuver = 2;
          this.submanDelay = 60;
        }
      }
      else if (this.submanDelay == 0)
      {
        this.submaneuver = 1;
        this.submanDelay = 30;
      }
    }
    else if (f1 < 3000.0F)
    {
      if (this.Vtarg.x < 0.5D)
      {
        if (this.submanDelay == 0)
        {
          this.submaneuver = 3;
          this.submanDelay = 120;
        }
      }
      else if (((f4 > 600.0F) && (this.submaneuver == 0)) || (f4 > 800.0F))
      {
        Ve.set(0.0D, 0.0D, 800.0D);
        if (this.submanDelay == 0)
        {
          this.submaneuver = 0;
          this.submanDelay = 120;
        }
      }
      else if (((f4 > -200.0F) && (this.submaneuver >= 4)) || (f4 > -100.0F))
      {
        if (this.submanDelay == 0)
        {
          this.submaneuver = 4;
          this.submanDelay = 120;
        }
      }
      else
      {
        Ve.set(0.0D, 0.0D, this.Loc.z);
        if (this.submanDelay == 0)
        {
          this.submaneuver = 0;
          this.submanDelay = 120;
        }
      }
    }
    else
    {
      Ve.set(0.0D, 0.0D, 1000.0D);
      if (this.submanDelay == 0)
      {
        this.submaneuver = 0;
        this.submanDelay = 180;
      }
    }
    switch (this.submaneuver)
    {
    case 0:
      this.target.Or.transform(Ve);
      Ve.add(this.target.Loc);
      Ve.sub(this.Loc);
      tmpV3f.set(Ve);
      tmpV3f.z = 0.0D;
      f5 = (float)tmpV3f.length();
      tmpV3f.normalize();
      this.Vtarg.set(this.target.Vwld);
      this.Vtarg.z = 0.0D;
      f6 = (float)tmpV3f.dot(this.Vtarg);
      f7 = getSpeed() - f6;
      if (f7 < 10.0F)
        f7 = 10.0F;
      float f8 = f5 / f7;
      if (f8 < 0.0F)
        f8 = 0.0F;
      tmpV3f.scale(this.Vtarg, f8);
      Ve.add(tmpV3f);
      this.Or.transformInv(Ve);
      Ve.normalize();
      setSpeedMode(9);
      farTurnToDirection();
      this.sub_Man_Count += 1;
      break;
    case 1:
      setSpeedMode(9);
      this.CT.AileronControl = (-0.04F * this.Or.getKren());
      this.CT.ElevatorControl = (-0.04F * (this.Or.getTangage() + 5.0F));
      this.CT.RudderControl = 0.0F;
      break;
    case 2:
      setSpeedMode(9);
      tmpOr.setYPR(this.Or.getYaw(), 0.0F, 0.0F);
      f5 = 6.0F;
      if (this.Or.getKren() > 0.0F)
        Ve.set(100.0D, -f5, 10.0D);
      else
        Ve.set(100.0D, f5, 10.0D);
      tmpOr.transform(Ve);
      this.Or.transformInv(Ve);
      Ve.normalize();
      farTurnToDirection();
      break;
    case 3:
      this.minElevCoeff = 20.0F;
      setSpeedMode(9);
      tmpOr.setYPR(this.Or.getYaw(), 0.0F, 0.0F);
      Ve.sub(this.target.Loc, this.Loc);
      Ve.z = 0.0D;
      Ve.normalize();
      Ve.z = 0.4D;
      this.Or.transformInv(Ve);
      Ve.normalize();
      attackTurnToDirection(1000.0F, paramFloat, 15.0F);
      break;
    case 4:
      this.minElevCoeff = 20.0F;
      boomAttack(paramFloat);
      setSpeedMode(9);
      break;
    default:
      this.minElevCoeff = 20.0F;
      fighterVsFighter(paramFloat);
    }
    if (this.submanDelay > 0)
      this.submanDelay -= 1;
  }

  public void bNZFighterVsFighter(float paramFloat)
  {
    Ve.sub(this.target.Loc, this.Loc);
    this.Or.transformInv(Ve);
    this.dist = (float)Ve.length();
    float f1 = (float)Math.sqrt(Ve.x * Ve.x + Ve.y * Ve.y);
    float f2 = 1.0F / this.dist;
    this.Vtarg.set(Ve);
    this.Vtarg.scale(f2);
    float f3 = (this.Energy - this.target.Energy) * 0.1019F;
    tmpV3f.sub(this.target.Vwld, this.Vwld);
    float f4;
    if (this.sub_Man_Count == 0)
    {
      f4 = 0.0F;
      if ((this.CT.Weapons[1] != null) && (this.CT.Weapons[1][0].countBullets() > 0))
        f4 = ((GunGeneric)this.CT.Weapons[1][0]).bulletSpeed();
      else if (this.CT.Weapons[0] != null)
        f4 = ((GunGeneric)this.CT.Weapons[0][0]).bulletSpeed();
      if (f4 > 0.01F)
        this.bullTime = (1.0F / f4);
      this.submanDelay = 0;
    }
    float f5;
    float f6;
    if (f1 < 1500.0F)
    {
      f4 = 0.0F;
      f5 = 0.0F;
      if (this.Vtarg.x > -0.2000000029802322D)
      {
        f4 = 3.0F * ((float)this.Vtarg.x + 0.2F);
        this.Vxy.set(tmpV3f);
        this.Vxy.scale(1.0D);
        this.Or.transformInv(this.Vxy);
        this.Vxy.add(Ve);
        this.Vxy.normalize();
        f5 = 20.0F * (float)(this.Vxy.x - this.Vtarg.x);
        if (f5 < -1.0F)
          f5 = -1.0F;
        if (f5 > 1.0F)
          f5 = 1.0F;
      }
      f6 = f3 * 0.0015F + f1 * 0.0006F + f4 + f5;
      if (((f6 > 0.8F) && (this.submaneuver >= 3)) || (f6 > 1.2F))
      {
        if (tmpV3f.length() < 100.0D)
        {
          if (this.submanDelay == 0)
          {
            this.submaneuver = 4;
            this.submanDelay = 120;
          }
        }
        else if (this.submanDelay == 0)
        {
          this.submaneuver = 3;
          this.submanDelay = 120;
        }
      }
      else if (((f3 > 800.0F) && (this.submaneuver == 0)) || (f3 > 1000.0F))
      {
        Ve.set(0.0D, 0.0D, 800.0D);
        if (this.submanDelay == 0)
        {
          this.submaneuver = 0;
          this.submanDelay = 30;
        }
      }
      else if (((this.dist > 450.0F) && (this.submaneuver == 2)) || (this.dist > 600.0F))
      {
        if (this.submanDelay == 0)
        {
          this.submaneuver = 2;
          this.submanDelay = 60;
        }
      }
      else if (this.submanDelay == 0)
      {
        this.submaneuver = 1;
        this.submanDelay = 30;
      }
    }
    else if (f1 < 3000.0F)
    {
      if (((f3 > 600.0F) && (this.submaneuver == 0)) || (f3 > 800.0F))
      {
        Ve.set(0.0D, 0.0D, 800.0D);
        if (this.submanDelay == 0)
        {
          this.submaneuver = 0;
          this.submanDelay = 120;
        }
      }
      else if (((f3 > -200.0F) && (this.submaneuver >= 3)) || (f3 > -100.0F))
      {
        if (this.submanDelay == 0)
        {
          this.submaneuver = 3;
          this.submanDelay = 120;
        }
      }
      else
      {
        Ve.set(0.0D, 0.0D, this.Loc.z);
        if (this.submanDelay == 0)
        {
          this.submaneuver = 0;
          this.submanDelay = 120;
        }
      }
    }
    else
    {
      Ve.set(0.0D, 0.0D, 1000.0D);
      if (this.submanDelay == 0)
      {
        this.submaneuver = 0;
        this.submanDelay = 180;
      }
    }
    switch (this.submaneuver)
    {
    case 0:
      this.target.Or.transform(Ve);
      Ve.add(this.target.Loc);
      Ve.sub(this.Loc);
      tmpV3f.set(Ve);
      tmpV3f.z = 0.0D;
      this.dist = (float)tmpV3f.length();
      tmpV3f.normalize();
      this.Vtarg.set(this.target.Vwld);
      this.Vtarg.z = 0.0D;
      f4 = (float)tmpV3f.dot(this.Vtarg);
      f5 = getSpeed() - f4;
      if (f5 < 10.0F)
        f5 = 10.0F;
      f6 = this.dist / f5;
      if (f6 < 0.0F)
        f6 = 0.0F;
      tmpV3f.scale(this.Vtarg, f6);
      Ve.add(tmpV3f);
      this.Or.transformInv(Ve);
      Ve.normalize();
      setSpeedMode(9);
      farTurnToDirection();
      this.sub_Man_Count += 1;
      break;
    case 1:
      setSpeedMode(9);
      this.CT.AileronControl = (-0.04F * this.Or.getKren());
      this.CT.ElevatorControl = (-0.04F * (this.Or.getTangage() + 5.0F));
      this.CT.RudderControl = 0.0F;
      break;
    case 2:
      setSpeedMode(9);
      tmpOr.setYPR(this.Or.getYaw(), 0.0F, 0.0F);
      if (this.Or.getKren() > 0.0F)
        Ve.set(100.0D, -6.0D, 10.0D);
      else
        Ve.set(100.0D, 6.0D, 10.0D);
      tmpOr.transform(Ve);
      this.Or.transformInv(Ve);
      Ve.normalize();
      farTurnToDirection();
      break;
    case 3:
      this.minElevCoeff = 20.0F;
      fighterVsFighter(paramFloat);
      setSpeedMode(6);
      break;
    default:
      this.minElevCoeff = 20.0F;
      fighterVsFighter(paramFloat);
    }
    if (this.submanDelay > 0)
      this.submanDelay -= 1;
  }

  public void setBomberAttackType(int paramInt)
  {
    float f1;
    if (Actor.isValid(this.target.actor))
      f1 = this.target.actor.collisionR();
    else {
      f1 = 15.0F;
    }

    setRandomTargDeviation(1.5F);

    switch (paramInt)
    {
    case 0:
      this.ApproachV.set(-300.0F + World.Rnd().nextFloat(-100.0F, 100.0F), 0.0D, 600.0F + World.Rnd().nextFloat(-100.0F, 100.0F));
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 150.0F;
      this.TargY = (2.1F * f1);
      this.TargZ = (3.9F * f1);
      this.TargYS = (0.43F * f1);
      this.TargZS = (0.43F * f1);
      break;
    case 1:
      this.ApproachV.set(-300.0F + World.Rnd().nextFloat(-100.0F, 100.0F), 0.0D, 600.0F + World.Rnd().nextFloat(-100.0F, 100.0F));
      this.TargV.set(this.target.EI.engines[World.Rnd().nextInt(0, this.target.EI.getNum() - 1)].getEnginePos());
      this.TargV.x -= 1.0D;
      this.ApproachR = 150.0F;
      this.TargY = (2.1F * f1);
      this.TargZ = (3.9F * f1);
      this.TargYS = (0.43F * f1);
      this.TargZS = (0.43F * f1);
      break;
    case 2:
      this.ApproachV.set(-600.0F + World.Rnd().nextFloat(-100.0F, 100.0F), 0.0D, 600.0F + World.Rnd().nextFloat(-100.0F, 100.0F));
      this.TargV.set(this.target.EI.engines[World.Rnd().nextInt(0, this.target.EI.getNum() - 1)].getEnginePos());
      this.TargV.x -= 1.0D;
      this.ApproachR = 300.0F;
      this.TargY = (2.1F * f1);
      this.TargZ = (3.9F * f1);
      this.TargYS = (0.43F * f1);
      this.TargZS = (0.43F * f1);
      break;
    case 3:
      this.ApproachV.set(2600.0D, 0.0D, 300.0F + World.Rnd().nextFloat(-100.0F, 100.0F));
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 800.0F;
      this.TargY = 25.0F;
      this.TargZ = 15.0F;
      this.TargYS = 3.0F;
      this.TargZS = 3.0F;
      break;
    case 4:
      this.ApproachV.set(-400.0F + World.Rnd().nextFloat(-100.0F, 100.0F), 0.0D, -200.0F + World.Rnd().nextFloat(-50.0F, 50.0F));
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 300.0F;
      this.TargY = (2.1F * f1);
      this.TargZ = (1.3F * f1);
      this.TargYS = (0.26F * f1);
      this.TargZS = (0.26F * f1);
      break;
    case 5:
      this.ApproachV.set(0.0D, 0.0D, 0.0D);
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 600.0F;
      this.TargY = 25.0F;
      this.TargZ = 12.0F;
      this.TargYS = (0.26F * f1);
      this.TargZS = (0.26F * f1);
      break;
    case 6:
      this.ApproachV.set(600.0D, 600 - World.Rnd().nextInt(0, 1) * 1200 + World.Rnd().nextFloat(-100.0F, 100.0F), 300.0D);
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 300.0F;
      this.TargY = (2.1F * f1);
      this.TargZ = (1.2F * f1);
      this.TargYS = (0.26F * f1);
      this.TargZS = (0.26F * f1);
      break;
    case 7:
      this.ApproachV.set(-1000.0F + World.Rnd().nextFloat(-200.0F, 200.0F), 0.0D, 100.0F + World.Rnd().nextFloat(-50.0F, 50.0F));
      this.TargV.set(this.target.EI.engines[World.Rnd().nextInt(0, this.target.EI.getNum() - 1)].getEnginePos());
      this.ApproachR = 200.0F;
      this.TargY = 10.0F;
      this.TargZ = 10.0F;
      this.TargYS = 2.0F;
      this.TargZS = 2.0F;
      break;
    case 8:
      this.ApproachV.set(-1000.0F + World.Rnd().nextFloat(-200.0F, 200.0F), 0.0D, 100.0F + World.Rnd().nextFloat(-50.0F, 50.0F));
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 200.0F;
      this.TargY = (2.1F * f1);
      this.TargZ = (3.9F * f1);
      this.TargYS = (0.2F * f1);
      this.TargZS = (0.2F * f1);
      break;
    case 9:
      this.ApproachV.set(-600.0F + World.Rnd().nextFloat(-100.0F, 100.0F), 0.0D, 600.0F + World.Rnd().nextFloat(-100.0F, 100.0F));
      this.TargV.set(0.0D, 0.0D, 0.0D);
      this.ApproachR = 300.0F;
      this.TargY = (2.1F * f1);
      this.TargZ = (3.9F * f1);
      this.TargYS = (0.2F * f1);
      this.TargZS = (0.2F * f1);
      break;
    case 10:
      this.ApproachV.set(-600.0F + World.Rnd().nextFloat(-100.0F, 100.0F), 0.0D, -300.0F + World.Rnd().nextFloat(-100.0F, 100.0F));
      this.TargV.set(100.0D, 0.0D, -400.0D);
      this.ApproachR = 300.0F;
      this.TargY = (2.1F * f1);
      this.TargZ = (3.9F * f1);
      this.TargYS = (0.43F * f1);
      this.TargZS = (0.43F * f1);
      break;
    default:
      this.ApproachV.set(-1000.0F + World.Rnd().nextFloat(-200.0F, 200.0F), 0.0D, 100.0F + World.Rnd().nextFloat(-50.0F, 50.0F));
      this.TargV.set(this.target.EI.engines[World.Rnd().nextInt(0, this.target.EI.getNum() - 1)].getEnginePos());
      this.ApproachR = 200.0F;
      this.TargY = 10.0F;
      this.TargZ = 10.0F;
      this.TargYS = 2.0F;
      this.TargZS = 2.0F;
    }
    float f2 = 0.0F;
    if ((this.CT.Weapons[1] != null) && (this.CT.Weapons[1][0].countBullets() > 0))
      f2 = ((GunGeneric)this.CT.Weapons[1][0]).bulletSpeed();
    else if (this.CT.Weapons[0] != null)
      f2 = ((GunGeneric)this.CT.Weapons[0][0]).bulletSpeed();
    if (f2 > 0.01F)
      this.bullTime = (1.0F / f2);
  }

  public void fighterVsBomber(float paramFloat)
  {
    this.maxAOA = 15.0F;
    tmpOr.setAT0(this.target.Vwld);
    float f1;
    float f2;
    float f3;
    switch (this.submaneuver)
    {
    case 0:
      this.minElevCoeff = 4.0F;
      this.rocketsDelay = 0;
      this.bulletDelay = 0;
      double d1 = 0.0001D * this.target.Loc.z;
      Ve.sub(this.target.Loc, this.Loc);
      tmpOr.transformInv(Ve);
      this.scaledApproachV.set(this.ApproachV);
      this.scaledApproachV.x -= 700.0D * d1;
      this.scaledApproachV.z += 500.0D * d1;
      Ve.add(this.scaledApproachV);
      Ve.normalize();
      tmpV3f.scale(this.scaledApproachV, -1.0D);
      tmpV3f.normalize();
      double d2 = Ve.dot(tmpV3f);
      Ve.set(this.Vwld);
      Ve.normalize();
      tmpV3f.set(this.target.Vwld);
      tmpV3f.normalize();
      double d3 = Ve.dot(tmpV3f);
      Ve.set(this.scaledApproachV);
      Ve.x += 60.0D * (2.0D * (1.0D - d2) + 4.0D * (1.0D - d3));
      tmpOr.transform(Ve);
      Ve.add(this.target.Loc);
      Ve.sub(this.Loc);
      double d4 = Ve.z;
      tmpV3f.set(Ve);
      tmpV3f.z = 0.0D;
      float f4 = (float)tmpV3f.length();
      float f5 = 0.55F + 0.15F * this.Skill;
      tmpV3f.normalize();
      this.Vtarg.set(this.target.Vwld);
      this.Vtarg.z = 0.0D;
      float f6 = (float)tmpV3f.dot(this.Vtarg);
      float f7 = getSpeed() - f6;
      if (f7 < 10.0F)
        f7 = 10.0F;
      float f8 = f4 / f7;
      if (f8 < 0.0F)
        f8 = 0.0F;
      tmpV3f.scale(this.Vtarg, f8 * f5);
      Ve.add(tmpV3f);
      this.Or.transformInv(Ve);
      Ve.normalize();
      if (d4 > -200.0D) {
        setSpeedMode(9);
      }
      else {
        setSpeedMode(4);
        this.smConstSpeed = 180.0F;
      }
      attackTurnToDirection(f4, paramFloat, 10.0F * (float)(1.0D + d1));
      this.sub_Man_Count += 1;
      if ((f4 >= this.ApproachR * (1.0D + d1)) || (d4 >= 200.0D))
        break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 1:
      this.minElevCoeff = 20.0F;
      Ve.set(this.TargV);
      this.target.Or.transform(Ve);
      Ve.add(this.target.Loc);
      Ve.sub(this.Loc);
      f1 = (float)Ve.length();
      f2 = 0.55F + 0.15F * this.Skill;
      tmpV3f.sub(this.target.Vwld, this.Vwld);
      f3 = f1 * this.bullTime * 0.0025F;
      if (f3 > 0.05F)
        f3 = 0.05F;
      tmpV3f.scale(f1 * f3 * f2);
      Ve.add(tmpV3f);
      this.Vtarg.set(Ve);
      this.Or.transformInv(Ve);
      Ve.normalize();

      if (zutiParse2Maneuver(this.target) != null) {
        ((Maneuver)this.target).incDangerAggressiveness(1, (float)Ve.x, f1, this);
      }
      if ((f1 > 3200.0F) || (this.Vtarg.z > 1500.0D))
      {
        this.submaneuver = 0;
        this.sub_Man_Count = 0;
        set_maneuver(0);
      }
      else
      {
        if (Ve.x < 0.300000011920929D)
        {
          this.Vtarg.z += 0.012F * this.Skill * (800.0F + f1) * (0.300000011920929D - Ve.x);
          Ve.set(this.Vtarg);
          this.Or.transformInv(Ve);
          Ve.normalize();
        }
        attackTurnToDirection(f1, paramFloat, 10.0F + this.Skill * 8.0F);
        setSpeedMode(4);
        this.smConstSpeed = 180.0F;
        if (f1 >= 400.0F)
          break;
        this.submaneuver += 1;
        this.sub_Man_Count = 0; } break;
    case 2:
      this.minElevCoeff = 20.0F;
      if (this.rocketsDelay > 0)
        this.rocketsDelay -= 1;
      if (this.bulletDelay > 0)
        this.bulletDelay -= 1;
      if (this.bulletDelay == 120)
        this.bulletDelay = 0;
      setRandomTargDeviation(0.8F);
      Ve.set(this.TargV);
      Ve.add(this.TargDevV);
      this.target.Or.transform(Ve);
      Ve.add(this.target.Loc);
      Ve.sub(this.Loc);
      this.Vtarg.set(Ve);
      f1 = (float)Ve.length();
      f2 = 0.55F + 0.15F * this.Skill;
      f3 = 0.0002F * this.Skill;
      tmpV3f.sub(this.target.Vwld, this.Vwld);
      Vpl.set(tmpV3f);
      tmpV3f.scale(f1 * (this.bullTime + f3) * f2);
      Ve.add(tmpV3f);
      tmpV3f.set(Vpl);
      tmpV3f.scale(f1 * this.bullTime * f2);
      this.Vtarg.add(tmpV3f);
      if ((f1 > 4000.0F) || (this.Vtarg.z > 2000.0D))
      {
        this.submaneuver = 0;
        this.sub_Man_Count = 0;
        set_maneuver(0);
      }
      else
      {
        this.Or.transformInv(this.Vtarg);
        if ((this.Vtarg.x > 0.0D) && (f1 < 500.0F) && ((this.shotAtFriend <= 0) || (this.distToFriend > f1)) && (Math.abs(this.Vtarg.y) < this.TargY - this.TargYS * this.Skill) && (Math.abs(this.Vtarg.z) < this.TargZ - this.TargZS * this.Skill) && (this.bulletDelay < 120))
        {
          if (!(this.actor instanceof KI_46_OTSUHEI))
            this.CT.WeaponControl[0] = true;
          this.CT.WeaponControl[1] = true;
          this.bulletDelay += 2;
          if (this.bulletDelay >= 118)
          {
            int i = (int)(this.target.getW().length() * 150.0D);
            if (i > 60)
              i = 60;
            this.bulletDelay += World.Rnd().nextInt(i * this.Skill, 2 * i * this.Skill);
          }
          if ((this.CT.Weapons[2] != null) && (this.CT.Weapons[2][0] != null) && (this.CT.Weapons[2][(this.CT.Weapons[2].length - 1)].countBullets() != 0) && (this.rocketsDelay < 1))
          {
            tmpV3f.sub(this.target.Vwld, this.Vwld);
            this.Or.transformInv(tmpV3f);
            if ((Math.abs(tmpV3f.y) < this.TargY - this.TargYS * this.Skill) && (Math.abs(tmpV3f.z) < this.TargZ - this.TargZS * this.Skill))
              this.CT.WeaponControl[2] = true;
            Voice.speakAttackByRockets((Aircraft)this.actor);
            this.rocketsDelay += 120;
          }

          if (zutiParse2Maneuver(this.target) != null)
            ((Maneuver)this.target).incDangerAggressiveness(1, 0.8F, f1, this);
          if (zutiParse2Pilot(this.target) != null)
            ((Pilot)this.target).setAsDanger(this.actor);
        }
        this.Vtarg.set(Ve);
        this.Or.transformInv(Ve);
        Ve.normalize();

        if (zutiParse2Maneuver(this.target) != null)
          ((Maneuver)this.target).incDangerAggressiveness(1, (float)Ve.x, f1, this);
        if (Ve.x < 0.300000011920929D)
        {
          this.Vtarg.z += 0.01F * this.Skill * (800.0F + f1) * (0.300000011920929D - Ve.x);
          Ve.set(this.Vtarg);
          this.Or.transformInv(Ve);
          Ve.normalize();
        }
        attackTurnToDirection(f1, paramFloat, 10.0F + this.Skill * 8.0F);
        if (this.target.getSpeed() > 70.0F)
        {
          setSpeedMode(2);
          this.tailForStaying = this.target;
          this.tailOffset.set(-20.0D, 0.0D, 0.0D);
        }
        else
        {
          setSpeedMode(4);
          this.smConstSpeed = 70.0F;
        }
        if (this.strikeEmer)
        {
          Vpl.sub(this.strikeTarg.Loc, this.Loc);
          tmpV3f.set(Vpl);
          this.Or.transformInv(tmpV3f);
          if (tmpV3f.x < 0.0D)
            break;
          tmpV3f.sub(this.strikeTarg.Vwld, this.Vwld);
          tmpV3f.scale(0.699999988079071D);
          Vpl.add(tmpV3f);
          this.Or.transformInv(Vpl);
          push();
          if (Vpl.z < 0.0D)
          {
            push(0);
            push(8);
            push(60);
          }
          else
          {
            push(0);
            push(8);
            push(61);
          }
          pop();
          this.strikeEmer = false;
          this.submaneuver = 0;
          this.sub_Man_Count = 0;
        } else {
          if (this.sub_Man_Count <= 600)
            break;
          push(8);
          pop(); } 
      }break;
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
    float f3;
    switch (this.submaneuver)
    {
    case 0:
      this.rocketsDelay = 0;
      this.bulletDelay = 0;
      Ve.set(this.ApproachV);
      this.target.Or.transform(Ve);
      Ve.add(this.target.Loc);
      Ve.sub(this.Loc);
      tmpV3f.set(Ve);
      tmpV3f.z = 0.0D;
      f1 = (float)tmpV3f.length();
      f2 = 0.55F + 0.15F * this.Skill;
      tmpV3f.normalize();
      this.Vtarg.set(this.target.Vwld);
      this.Vtarg.z = 0.0D;
      f3 = (float)tmpV3f.dot(this.Vtarg);
      float f4 = getSpeed() - f3;
      if (f4 < 10.0F)
        f4 = 10.0F;
      float f5 = f1 / f4;
      if (f5 < 0.0F)
        f5 = 0.0F;
      tmpV3f.scale(this.Vtarg, f5 * f2);
      Ve.add(tmpV3f);
      this.Or.transformInv(Ve);
      Ve.normalize();
      setSpeedMode(4);
      this.smConstSpeed = 140.0F;
      farTurnToDirection();
      this.sub_Man_Count += 1;
      if (f1 >= this.ApproachR)
        break;
      this.submaneuver += 1;
      this.sub_Man_Count = 0; break;
    case 1:
      Ve.set(this.TargV);
      this.target.Or.transform(Ve);
      Ve.add(this.target.Loc);
      Ve.sub(this.Loc);
      f1 = (float)Ve.length();
      f2 = 0.55F + 0.15F * this.Skill;
      tmpV3f.sub(this.target.Vwld, this.Vwld);
      f3 = f1 * this.bullTime * 0.0025F;
      if (f3 > 0.02F)
        f3 = 0.02F;
      tmpV3f.scale(f1 * f3 * f2);
      Ve.add(tmpV3f);
      this.Vtarg.set(Ve);
      this.Or.transformInv(Ve);
      Ve.normalize();

      if (zutiParse2Maneuver(this.target) != null) {
        ((Maneuver)this.target).incDangerAggressiveness(1, (float)Ve.x, f1, this);
      }
      if ((f1 > 3200.0F) || (this.Vtarg.z > 1500.0D))
      {
        this.submaneuver = 0;
        this.sub_Man_Count = 0;
      }
      else
      {
        if (Ve.x < 0.300000011920929D)
        {
          this.Vtarg.z += 0.01F * this.Skill * (800.0F + f1) * (0.300000011920929D - Ve.x);
          Ve.set(this.Vtarg);
          this.Or.transformInv(Ve);
          Ve.normalize();
        }
        attackTurnToDirection(f1, paramFloat, 10.0F);
        if (this.target.getSpeed() > 120.0F)
        {
          setSpeedMode(2);
          this.tailForStaying = this.target;
        }
        else
        {
          setSpeedMode(4);
          this.smConstSpeed = 120.0F;
        }
        if (f1 >= 400.0F)
          break;
        this.submaneuver += 1;
        this.sub_Man_Count = 0; } break;
    case 2:
      setCheckStrike(false);
      if (this.rocketsDelay > 0)
        this.rocketsDelay -= 1;
      if (this.bulletDelay > 0)
        this.bulletDelay -= 1;
      if (this.bulletDelay == 120)
        this.bulletDelay = 0;
      setRandomTargDeviation(0.8F);
      Ve.set(this.TargV);
      Ve.add(this.TargDevV);
      this.target.Or.transform(Ve);
      Ve.add(this.target.Loc);
      Ve.sub(this.Loc);
      f1 = (float)Ve.length();
      f2 = 0.55F + 0.15F * this.Skill;
      tmpV3f.sub(this.target.Vwld, this.Vwld);
      tmpV3f.scale(f1 * this.bullTime * f2);
      Ve.add(tmpV3f);
      this.Vtarg.set(Ve);
      this.Or.transformInv(Ve);
      if ((f1 > 4000.0F) || (this.Vtarg.z > 2000.0D))
      {
        this.submaneuver = 0;
        this.sub_Man_Count = 0;
      }
      else
      {
        if ((this.AS.astatePilotStates[1] < 100) && (f1 < 330.0F) && (Math.abs(this.Or.getKren()) < 3.0F))
          this.CT.WeaponControl[0] = true;
        Ve.normalize();
        if (Ve.x < 0.300000011920929D)
        {
          this.Vtarg.z += 0.01F * this.Skill * (800.0F + f1) * (0.300000011920929D - Ve.x);
          Ve.set(this.Vtarg);
          this.Or.transformInv(Ve);
          Ve.normalize();
        }
        attackTurnToDirection(f1, paramFloat, 6.0F + this.Skill * 3.0F);
        setSpeedMode(1);
        this.tailForStaying = this.target;
        this.tailOffset.set(-190.0D, 0.0D, 0.0D);
        if ((this.sub_Man_Count <= 10000) && (f1 >= 240.0F))
          break;
        push(9);
        pop(); } break;
    default:
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }
  }

  public void fighterVsFighter(float paramFloat)
  {
    if (this.sub_Man_Count == 0)
    {
      f1 = 0.0F;
      if ((this.CT.Weapons[1] != null) && (this.CT.Weapons[1][0].countBullets() > 0))
        f1 = ((GunGeneric)this.CT.Weapons[1][0]).bulletSpeed();
      else if (this.CT.Weapons[0] != null)
        f1 = ((GunGeneric)this.CT.Weapons[0][0]).bulletSpeed();
      if (f1 > 0.01F)
        this.bullTime = (1.0F / f1);
    }
    this.maxAOA = 15.0F;
    if (this.rocketsDelay > 0)
      this.rocketsDelay -= 1;
    if (this.bulletDelay > 0)
      this.bulletDelay -= 1;
    if (this.bulletDelay == 122)
      this.bulletDelay = 0;
    Ve.sub(this.target.Loc, this.Loc);
    setRandomTargDeviation(0.3F);
    Ve.add(this.TargDevV);
    this.Vtarg.set(Ve);
    float f1 = (float)Ve.length();
    float f2 = 0.55F + 0.15F * this.Skill;
    float f3 = 0.0002F * this.Skill;
    tmpV3f.sub(this.target.Vwld, this.Vwld);
    Vpl.set(tmpV3f);
    tmpV3f.scale(f1 * (this.bullTime + f3) * f2);
    Ve.add(tmpV3f);
    tmpV3f.set(Vpl);
    tmpV3f.scale(f1 * this.bullTime * f2);
    this.Vtarg.add(tmpV3f);
    this.Or.transformInv(Vpl);
    float f4 = (float)(-Vpl.x);
    if (f4 < 0.001F)
      f4 = 0.001F;
    Vpl.normalize();
    if ((Vpl.x < -0.9399999976158142D) && (f1 / f4 < 1.5F * (3 - this.Skill)))
    {
      push();
      set_maneuver(14);
    }
    else
    {
      this.Or.transformInv(this.Vtarg);
      if ((this.Vtarg.x > 0.0D) && (f1 < 500.0F) && ((this.shotAtFriend <= 0) || (this.distToFriend > f1)))
      {
        if ((zutiParse2Maneuver(this.target) != null) && (Math.abs(this.Vtarg.y) < 13.0D) && (Math.abs(this.Vtarg.z) < 13.0D))
          ((Maneuver)this.target).incDangerAggressiveness(1, 0.95F, f1, this);
        if ((Math.abs(this.Vtarg.y) < 12.5F - 3.5F * this.Skill) && (Math.abs(this.Vtarg.z) < 12.5D - 3.5D * this.Skill) && (this.bulletDelay < 120))
        {
          if (!(this.actor instanceof KI_46_OTSUHEI))
            this.CT.WeaponControl[0] = true;
          this.bulletDelay += 2;
          if (this.bulletDelay >= 118)
          {
            int i = (int)(this.target.getW().length() * 150.0D);
            if (i > 60)
              i = 60;
            this.bulletDelay += World.Rnd().nextInt(i * this.Skill, 2 * i * this.Skill);
          }
          if (f1 < 400.0F)
          {
            this.CT.WeaponControl[1] = true;
            if ((f1 < 100.0F) && (this.CT.Weapons[2] != null) && (this.CT.Weapons[2][0] != null) && (this.CT.Weapons[2][(this.CT.Weapons[2].length - 1)].countBullets() != 0) && (this.rocketsDelay < 1))
            {
              tmpV3f.sub(this.target.Vwld, this.Vwld);
              this.Or.transformInv(tmpV3f);
              if ((Math.abs(tmpV3f.y) < 4.0D) && (Math.abs(tmpV3f.z) < 4.0D))
                this.CT.WeaponControl[2] = true;
              Voice.speakAttackByRockets((Aircraft)this.actor);
              this.rocketsDelay += 120;
            }
          }

          if (zutiParse2Pilot(this.target) != null)
            ((Pilot)this.target).setAsDanger(this.actor);
        }
      }
      this.Vtarg.set(Ve);
      this.Or.transformInv(Ve);
      Ve.normalize();

      if (zutiParse2Maneuver(this.target) != null) {
        ((Maneuver)this.target).incDangerAggressiveness(1, (float)Ve.x, f1, this);
      }
      if (Ve.x < 0.300000011920929D)
      {
        this.Vtarg.z += 0.01F * this.Skill * (800.0F + f1) * (0.300000011920929D - Ve.x);
        Ve.set(this.Vtarg);
        this.Or.transformInv(Ve);
        Ve.normalize();
      }
      attackTurnToDirection(f1, paramFloat, 10.0F + this.Skill * 8.0F);
      if ((this.Alt > 150.0F) && (Ve.x > 0.6D) && (f1 < 70.0D))
      {
        setSpeedMode(1);
        this.tailForStaying = this.target;
        this.tailOffset.set(-20.0D, 0.0D, 0.0D);
      }
      else {
        setSpeedMode(9);
      }
    }
  }

  public void boomAttack(float paramFloat) {
    if (this.sub_Man_Count == 0)
    {
      f1 = 0.0F;
      if ((this.CT.Weapons[1] != null) && (this.CT.Weapons[1][0].countBullets() > 0))
        f1 = ((GunGeneric)this.CT.Weapons[1][0]).bulletSpeed();
      else if (this.CT.Weapons[0] != null)
        f1 = ((GunGeneric)this.CT.Weapons[0][0]).bulletSpeed();
      if (f1 > 0.01F)
        this.bullTime = (1.0F / f1);
    }
    this.maxAOA = 15.0F;
    if (this.rocketsDelay > 0)
      this.rocketsDelay -= 1;
    if (this.bulletDelay > 0)
      this.bulletDelay -= 1;
    if (this.bulletDelay == 122)
      this.bulletDelay = 0;
    Ve.sub(this.target.Loc, this.Loc);
    setRandomTargDeviation(0.3F);
    Ve.add(this.TargDevV);
    this.Vtarg.set(Ve);
    float f1 = (float)Ve.length();
    float f2 = 0.55F + 0.15F * this.Skill;
    float f3 = 0.000333F * this.Skill;
    tmpV3f.sub(this.target.Vwld, this.Vwld);
    Vpl.set(tmpV3f);
    tmpV3f.scale(f1 * (this.bullTime + f3) * f2);
    Ve.add(tmpV3f);
    tmpV3f.set(Vpl);
    tmpV3f.scale(f1 * this.bullTime * f2);
    this.Vtarg.add(tmpV3f);
    this.Or.transformInv(Vpl);
    float f4 = (float)(-Vpl.x);
    if (f4 < 0.001F)
      f4 = 0.001F;
    Vpl.normalize();
    if ((Vpl.x < -0.9399999976158142D) && (f1 / f4 < 1.5F * (3 - this.Skill)))
    {
      push();
      set_maneuver(14);
    }
    else
    {
      this.Or.transformInv(this.Vtarg);
      if ((this.Vtarg.x > 0.0D) && (f1 < 700.0F) && ((this.shotAtFriend <= 0) || (this.distToFriend > f1)) && (Math.abs(this.Vtarg.y) < 15.5F - 3.5F * this.Skill) && (Math.abs(this.Vtarg.z) < 15.5D - 3.5D * this.Skill) && (this.bulletDelay < 120))
      {
        if (zutiParse2Maneuver(this.target) != null) {
          ((Maneuver)this.target).incDangerAggressiveness(1, 0.8F, f1, this);
        }
        if (!(this.actor instanceof KI_46_OTSUHEI))
          this.CT.WeaponControl[0] = true;
        this.bulletDelay += 2;
        if (this.bulletDelay >= 118)
        {
          int i = (int)(this.target.getW().length() * 150.0D);
          if (i > 60)
            i = 60;
          this.bulletDelay += World.Rnd().nextInt(i * this.Skill, 2 * i * this.Skill);
        }
        if (f1 < 500.0F)
        {
          this.CT.WeaponControl[1] = true;
          if ((f1 < 100.0F) && (this.CT.Weapons[2] != null) && (this.CT.Weapons[2][0] != null) && (this.CT.Weapons[2][(this.CT.Weapons[2].length - 1)].countBullets() != 0) && (this.rocketsDelay < 1))
          {
            tmpV3f.sub(this.target.Vwld, this.Vwld);
            this.Or.transformInv(tmpV3f);
            if ((Math.abs(tmpV3f.y) < 4.0D) && (Math.abs(tmpV3f.z) < 4.0D))
              this.CT.WeaponControl[2] = true;
            Voice.speakAttackByRockets((Aircraft)this.actor);
            this.rocketsDelay += 120;
          }
        }

        if (zutiParse2Pilot(this.target) != null)
          ((Pilot)this.target).setAsDanger(this.actor);
      }
      this.Vtarg.set(Ve);
      this.Or.transformInv(Ve);
      Ve.normalize();

      if (zutiParse2Maneuver(this.target) != null) {
        ((Maneuver)this.target).incDangerAggressiveness(1, (float)Ve.x, f1, this);
      }
      if (Ve.x < 0.8999999761581421D)
      {
        this.Vtarg.z += 0.03F * this.Skill * (800.0F + f1) * (0.8999999761581421D - Ve.x);
        Ve.set(this.Vtarg);
        this.Or.transformInv(Ve);
        Ve.normalize();
      }
      attackTurnToDirection(f1, paramFloat, 10.0F + this.Skill * 8.0F);
    }
  }

  private void turnToDirection(float paramFloat)
  {
    if (Math.abs(Ve.y) > 0.1000000014901161D)
      this.CT.AileronControl = (-(float)Math.atan2(Ve.y, Ve.z) - 0.016F * this.Or.getKren());
    else
      this.CT.AileronControl = (-(float)Math.atan2(Ve.y, Ve.x) - 0.016F * this.Or.getKren());
    this.CT.RudderControl = (-10.0F * (float)Math.atan2(Ve.y, Ve.x));
    if (this.CT.RudderControl * this.W.z > 0.0D)
      this.W.z = 0.0D;
    else
      this.W.z *= 1.039999961853027D;
    float f = (float)Math.atan2(Ve.z, Ve.x);
    if ((Math.abs(Ve.y) < 0.002000000094994903D) && (Math.abs(Ve.z) < 0.002000000094994903D))
      f = 0.0F;
    if (Ve.x < 0.0D) {
      f = 1.0F;
    }
    else {
      if (f * this.W.y > 0.0D)
        this.W.y = 0.0D;
      if (f < 0.0F)
      {
        if (getOverload() < 0.1F)
          f = 0.0F;
        if (this.CT.ElevatorControl > 0.0F)
          this.CT.ElevatorControl = 0.0F;
      }
      else if (this.CT.ElevatorControl < 0.0F) {
        this.CT.ElevatorControl = 0.0F;
      }
    }
    if ((getOverload() > this.maxG) || (this.AOA > this.maxAOA) || (this.CT.ElevatorControl > f))
      this.CT.ElevatorControl -= 0.3F * paramFloat;
    else
      this.CT.ElevatorControl += 0.3F * paramFloat;
  }

  private void farTurnToDirection()
  {
    farTurnToDirection(4.0F);
  }

  private void farTurnToDirection(float paramFloat)
  {
    Vpl.set(1.0D, 0.0D, 0.0D);
    tmpV3f.cross(Vpl, Ve);
    this.Or.transform(tmpV3f);
    this.CT.RudderControl = (-10.0F * (float)Math.atan2(Ve.y, Ve.x) + 1.0F * (float)this.W.y);
    float f1 = getSpeed() / this.Vmax * 45.0F;
    if (f1 > 85.0F)
      f1 = 85.0F;
    float f2 = (float)Ve.x;
    if (f2 < 0.0F)
      f2 = 0.0F;
    float f3;
    if (tmpV3f.z >= 0.0D)
      f3 = -0.02F * (f1 + this.Or.getKren()) * (1.0F - f2) - 0.05F * this.Or.getTangage() + 1.0F * (float)this.W.x;
    else
      f3 = -0.02F * (-f1 + this.Or.getKren()) * (1.0F - f2) + 0.05F * this.Or.getTangage() + 1.0F * (float)this.W.x;
    float f4 = -(float)Math.atan2(Ve.y, Ve.x) - 0.016F * this.Or.getKren() + 1.0F * (float)this.W.x;
    float f5 = -0.1F * (getAOA() - 10.0F) + 0.5F * (float)getW().y;
    float f6;
    if (Ve.z > 0.0D)
      f6 = -0.1F * (getAOA() - paramFloat) + 0.5F * (float)getW().y;
    else
      f6 = 1.0F * (float)Ve.z + 0.5F * (float)getW().y;
    if (Math.abs(Ve.y) < 0.002000000094994903D)
    {
      f4 = 0.0F;
      this.CT.RudderControl = 0.0F;
    }
    float f7 = 1.0F - Math.abs((float)Ve.y) * 2.0F;
    if ((f7 < 0.0F) || (Ve.x < 0.0D))
      f7 = 0.0F;
    float f8 = f7 * f4 + (1.0F - f7) * f3;
    float f9 = f7 * f6 + (1.0F - f7) * f5;
    this.CT.AileronControl = f8;
    this.CT.ElevatorControl = f9;
  }

  private void attackTurnToDirection(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (Ve.x < 0.009999999776482582D)
      Ve.x = 0.009999999776482582D;
    if (this.sub_Man_Count == 0)
      this.oldVe.set(Ve);
    if (Ve.x > 0.949999988079071D)
    {
      this.CT.RudderControl = (float)(-30.0D * Math.atan2(Ve.y, Ve.x) + 1.5D * (Ve.y - this.oldVe.y));

      if ((Ve.z > 0.0D) || (this.CT.RudderControl > 0.9F))
      {
        f1 = (float)(10.0D * Math.atan2(Ve.z, Ve.x) + 6.0D * (Ve.z - this.oldVe.z));
        this.CT.AileronControl = (-30.0F * (float)Math.atan2(Ve.y, Ve.x) - 0.02F * this.Or.getKren() + 5.0F * (float)this.W.x);
      }
      else
      {
        f1 = (float)(5.0D * Math.atan2(Ve.z, Ve.x) + 6.0D * (Ve.z - this.oldVe.z));
        this.CT.AileronControl = (-5.0F * (float)Math.atan2(Ve.y, Ve.x) - 0.02F * this.Or.getKren() + 5.0F * (float)this.W.x);
      }
      if (Ve.x > 1.0F - 0.005F * this.Skill)
      {
        tmpOr.set(this.Or);
        tmpOr.increment((float)Math.toDegrees(Math.atan2(Ve.y, Ve.x)), (float)Math.toDegrees(Math.atan2(Ve.z, Ve.x)), 0.0F);
        this.Or.interpolate(tmpOr, 0.1F);
      }
      if (Math.abs(this.CT.ElevatorControl - f1) < paramFloat3 * paramFloat2)
        this.CT.ElevatorControl = f1;
      else if (this.CT.ElevatorControl < f1)
        this.CT.ElevatorControl += paramFloat3 * paramFloat2;
      else
        this.CT.ElevatorControl -= paramFloat3 * paramFloat2;
    }
    else
    {
      if ((this.Skill >= 2) && (Ve.z > 0.5D) && (paramFloat1 < 600.0F))
        this.CT.FlapsControl = 0.1F;
      else
        this.CT.FlapsControl = 0.0F;
      f1 = 0.6F - (float)Ve.z;
      if (f1 < 0.0F)
        f1 = 0.0F;
      this.CT.RudderControl = (float)(-30.0D * Math.atan2(Ve.y, Ve.x) * f1 + 1.0D * (Ve.y - this.oldVe.y) * Ve.x + 0.5D * this.W.z);
      float f2;
      if (Ve.z > 0.0D)
      {
        f2 = (float)(10.0D * Math.atan2(Ve.z, Ve.x) + 6.0D * (Ve.z - this.oldVe.z) + 0.5D * (float)this.W.y);
        if (f2 < 0.0F)
          f2 = 0.0F;
        this.CT.AileronControl = (float)(-20.0D * Math.atan2(Ve.y, Ve.z) - 0.05D * this.Or.getKren() + 5.0D * this.W.x);
      }
      else
      {
        f2 = (float)(-5.0D * Math.atan2(Ve.z, Ve.x) + 6.0D * (Ve.z - this.oldVe.z) + 0.5D * (float)this.W.y);
        this.CT.AileronControl = (float)(-20.0D * Math.atan2(Ve.y, Ve.z) - 0.05D * this.Or.getKren() + 5.0D * this.W.x);
      }
      if (f2 < 0.0F)
        f2 = 0.0F;
      if (Math.abs(this.CT.ElevatorControl - f2) < paramFloat3 * paramFloat2)
        this.CT.ElevatorControl = f2;
      else if (this.CT.ElevatorControl < f2)
        this.CT.ElevatorControl += 0.3F * paramFloat2;
      else
        this.CT.ElevatorControl -= 0.3F * paramFloat2;
    }
    float f1 = 0.054F * (600.0F - paramFloat1);
    if (f1 < 4.0F)
      f1 = 4.0F;
    if (f1 > this.AOA_Crit)
      f1 = this.AOA_Crit;
    if (this.AOA > f1 - 1.5F)
      this.Or.increment(0.0F, 0.16F * (f1 - 1.5F - this.AOA), 0.0F);
    if (this.AOA < -5.0F)
      this.Or.increment(0.0F, 0.12F * (-5.0F - this.AOA), 0.0F);
    this.oldVe.set(Ve);
    this.oldAOA = this.AOA;
    this.sub_Man_Count += 1;
    this.W.x *= 0.95D;
  }

  private void doCheckStrike()
  {
    if (((this.M.massEmpty <= 5000.0F) || (this.AP.way.isLanding())) && ((this.maneuver != 24) || (this.strikeTarg != this.Leader)) && ((!(this.actor instanceof TypeDockable)) || (!((TypeDockable)this.actor).typeDockableIsDocked())))
    {
      Vpl.sub(this.strikeTarg.Loc, this.Loc);
      tmpV3f.set(Vpl);
      this.Or.transformInv(tmpV3f);
      if (tmpV3f.x >= 0.0D)
      {
        tmpV3f.sub(this.strikeTarg.Vwld, this.Vwld);
        tmpV3f.scale(0.699999988079071D);
        Vpl.add(tmpV3f);
        this.Or.transformInv(Vpl);
        push();
        if (Vpl.z > 0.0D)
          push(61);
        else
          push(60);
        safe_pop();
      }
    }
  }

  public void setStrikeEmer(FlightModel paramFlightModel)
  {
    this.strikeEmer = true;
    this.strikeTarg = paramFlightModel;
  }

  private float bombDist(float paramFloat)
  {
    float f = FMMath.positiveSquareEquation(-0.5F * World.g(), (float)this.Vwld.z, paramFloat);
    if (f < 0.0F)
      return -1000000.0F;
    return f * (float)Math.sqrt(this.Vwld.x * this.Vwld.x + this.Vwld.y * this.Vwld.y);
  }

  protected void wingman(boolean paramBoolean)
  {
    if (this.Wingman != null)
      this.Wingman.Leader = (paramBoolean ? this : null);
  }

  public float getMnTime()
  {
    return this.mn_time;
  }

  public void doDumpBombsPassively()
  {
    int i = 0;
    for (int j = 0; j < this.CT.Weapons.length; j++)
    {
      if ((this.CT.Weapons[j] == null) || (this.CT.Weapons[j].length <= 0))
        continue;
      for (int k = 0; k < this.CT.Weapons[j].length; k++)
      {
        if (!(this.CT.Weapons[j][k] instanceof BombGun))
          continue;
        if ((this.CT.Weapons[j][k] instanceof BombGunPara)) {
          i = 1;
        }
        else {
          ((BombGun)this.CT.Weapons[j][k]).setBombDelay(33000000.0F);
          if ((this.actor == World.getPlayerAircraft()) && (!World.cur().diffCur.Limited_Ammo)) {
            this.CT.Weapons[j][k].loadBullets(0);
          }
        }
      }
    }

    if (i == 0)
      this.bombsOut = true;
  }

  protected void printDebugFM()
  {
    System.out.print("<" + this.actor.name() + "> " + ManString.tname(this.task) + ":" + ManString.name(this.maneuver) + (this.target == null ? "t" : "T") + (this.danger == null ? "d" : "D") + (this.target_ground == null ? "g " : "G "));
    switch (this.maneuver)
    {
    case 21:
      System.out.println(": WP=" + this.AP.way.Cur() + "(" + (this.AP.way.size() - 1) + ")-" + ManString.wpname(this.AP.way.curr().Action));
      if (this.target_ground == null) break;
      System.out.println(" GT=" + this.target_ground.getClass().getName() + "(" + this.target_ground.name() + ")"); break;
    case 27:
      if ((this.target == null) || (!Actor.isValid(this.target.actor)))
        System.out.println(" T=null");
      else
        System.out.println(" T=" + this.target.actor.getClass().getName() + "(" + this.target.actor.name() + ")");
      break;
    case 43:
    case 50:
    case 51:
    case 52:
      if ((this.target_ground == null) || (!Actor.isValid(this.target_ground)))
        System.out.println(" GT=null");
      else
        System.out.println(" GT=" + this.target_ground.getClass().getName() + "(" + this.target_ground.name() + ")");
      break;
    default:
      System.out.println("");
      if ((this.target == null) || (!Actor.isValid(this.target.actor))) {
        System.out.println(" T=null");
      }
      else {
        System.out.println(" T=" + this.target.actor.getClass().getName() + "(" + this.target.actor.name() + ")");
        if ((this.target_ground == null) || (!Actor.isValid(this.target_ground)))
          System.out.println(" GT=null");
        else
          System.out.println(" GT=" + this.target_ground.getClass().getName() + "(" + this.target_ground.name() + ")");
      }
    }
  }

  protected void headTurn(float paramFloat)
  {
    if ((this.actor == Main3D.cur3D().viewActor()) && (this.AS.astatePilotStates[0] < 90))
    {
      int i = 0;
      switch (get_task())
      {
      case 2:
        if (this.Leader == null)
          break;
        Ve.set(this.Leader.Loc);
        i = 1; break;
      case 6:
        if (this.target == null)
          break;
        Ve.set(this.target.Loc);
        i = 1; break;
      case 5:
        if (this.airClient == null)
          break;
        Ve.set(this.airClient.Loc);
        i = 1; break;
      case 4:
        if (this.danger == null)
          break;
        Ve.set(this.danger.Loc);
        i = 1; break;
      case 7:
        if (this.target_ground == null)
          break;
        Ve.set(this.target_ground.pos.getAbsPoint());
        i = 1;
      case 3:
      }
      float f1;
      float f2;
      if (i != 0)
      {
        Ve.sub(this.Loc);
        this.Or.transformInv(Ve);
        tmpOr.setAT0(Ve);
        f1 = tmpOr.getTangage();
        f2 = tmpOr.getYaw();
        if (f2 > 75.0F)
          f2 = 75.0F;
        if (f2 < -75.0F)
          f2 = -75.0F;
        if (f1 < -15.0F)
          f1 = -15.0F;
        if (f1 > 40.0F)
          f1 = 40.0F;
      }
      else if (get_maneuver() != 44)
      {
        f1 = 0.0F;
        f2 = 0.0F;
      }
      else
      {
        f2 = -15.0F;
        f1 = -15.0F;
      }
      Maneuver localManeuver;
      if (Math.abs(this.pilotHeadT - f1) > 3.0F)
      {
        localManeuver = this;
        localManeuver.pilotHeadT += 90.0F * (this.pilotHeadT <= f1 ? 1.0F : -1.0F) * paramFloat;
      }
      else {
        this.pilotHeadT = f1;
      }if (Math.abs(this.pilotHeadY - f2) > 2.0F)
      {
        localManeuver = this;
        localManeuver.pilotHeadY += 60.0F * (this.pilotHeadY <= f2 ? 1.0F : -1.0F) * paramFloat;
      }
      else {
        this.pilotHeadY = f2;
      }tmpOr.setYPR(0.0F, 0.0F, 0.0F);
      tmpOr.increment(0.0F, this.pilotHeadY, 0.0F);
      tmpOr.increment(this.pilotHeadT, 0.0F, 0.0F);
      tmpOr.increment(0.0F, 0.0F, -0.2F * this.pilotHeadT + 0.05F * this.pilotHeadY);
      this.headOr[0] = tmpOr.getYaw();
      this.headOr[1] = tmpOr.getPitch();
      this.headOr[2] = tmpOr.getRoll();
      this.headPos[0] = (0.0005F * Math.abs(this.pilotHeadY));
      this.headPos[1] = (-1.0E-004F * Math.abs(this.pilotHeadY));
      this.headPos[2] = 0.0F;
      ((ActorHMesh)this.actor).hierMesh().chunkSetLocate("Head1_D0", this.headPos, this.headOr);
    }
  }

  protected void turnOffTheWeapon()
  {
    this.CT.WeaponControl[0] = false;
    this.CT.WeaponControl[1] = false;
    this.CT.WeaponControl[2] = false;
    this.CT.WeaponControl[3] = false;
    if (this.bombsOut)
    {
      this.bombsOutCounter += 1;
      if (this.bombsOutCounter > 128)
      {
        this.bombsOutCounter = 0;
        this.bombsOut = false;
      }
      if (this.CT.Weapons[3] != null)
        this.CT.WeaponControl[3] = true;
      else
        this.bombsOut = false;
      if ((this.CT.Weapons[3] != null) && (this.CT.Weapons[3][0] != null))
      {
        int i = 0;
        for (int j = 0; j < this.CT.Weapons[3].length; j++)
          i += this.CT.Weapons[3][j].countBullets();
        if (i == 0)
        {
          this.bombsOut = false;
          for (int k = 0; k < this.CT.Weapons[3].length; k++)
            this.CT.Weapons[3][k].loadBullets(0);
        }
      }
    }
  }

  protected void turnOnChristmasTree(boolean paramBoolean)
  {
    if ((paramBoolean) && (!this.bNoNavLightsAI))
      this.AS.setNavLightsState(true);
    else
      this.AS.setNavLightsState(false);
  }

  protected void turnOnCloudShine(boolean paramBoolean)
  {
    if (paramBoolean)
      this.AS.setLandingLightState(true);
    else
      this.AS.setLandingLightState(false);
  }

  protected void doCheckGround(float paramFloat)
  {
    if (this.CT.AileronControl > 1.0F)
      this.CT.AileronControl = 1.0F;
    if (this.CT.AileronControl < -1.0F)
      this.CT.AileronControl = -1.0F;
    if (this.CT.ElevatorControl > 1.0F)
      this.CT.ElevatorControl = 1.0F;
    if (this.CT.ElevatorControl < -1.0F)
      this.CT.ElevatorControl = -1.0F;
    float f1 = 0.0003F * this.M.massEmpty;
    float f2 = 10.0F;
    float f3 = 80.0F;
    if (this.maneuver == 24)
    {
      f2 = 15.0F;
      f3 = 120.0F;
    }
    float f4 = (float)(-this.Vwld.z) * f2 * f1;
    float f5 = 1.0F + 7.0F * ((this.indSpeed - this.VmaxAllowed) / this.VmaxAllowed);
    if (f5 > 1.0F)
      f5 = 1.0F;
    if (f5 < 0.0F)
      f5 = 0.0F;
    float f6;
    float f7;
    float f8;
    if ((f4 > this.Alt) || (this.Alt < f3) || (f5 > 0.0F))
    {
      if (this.Alt < 0.001F)
        this.Alt = 0.001F;
      f6 = (f4 - this.Alt) / this.Alt;
      Math.max(0.01667D * (f3 - this.Alt), f6);
      if (f6 > 1.0F)
        f6 = 1.0F;
      if (f6 < 0.0F)
        f6 = 0.0F;
      if (f6 < f5)
        f6 = f5;
      f7 = -0.12F * (this.Or.getTangage() - 5.0F) + 3.0F * (float)this.W.y;
      f8 = -0.07F * this.Or.getKren() + 3.0F * (float)this.W.x;
      if (f8 > 2.0F)
        f8 = 2.0F;
      if (f8 < -2.0F)
        f8 = -2.0F;
      if (f7 > 2.0F)
        f7 = 2.0F;
      if (f7 < -2.0F)
        f7 = -2.0F;
    }
    else
    {
      f6 = 0.0F;
      f7 = 0.0F;
      f8 = 0.0F;
    }
    float f9 = 0.2F;
    if (this.corrCoeff < f6)
      this.corrCoeff = f6;
    if (this.corrCoeff > f6)
      this.corrCoeff = (float)(this.corrCoeff * (1.0D - f9 * paramFloat));
    if (f7 < 0.0F)
    {
      if (this.corrElev > f7)
        this.corrElev = f7;
      if (this.corrElev < f7)
        this.corrElev = (float)(this.corrElev * (1.0D - f9 * paramFloat));
    }
    else
    {
      if (this.corrElev < f7)
        this.corrElev = f7;
      if (this.corrElev > f7)
        this.corrElev = (float)(this.corrElev * (1.0D - f9 * paramFloat));
    }
    if (f8 < 0.0F)
    {
      if (this.corrAile > f8)
        this.corrAile = f8;
      if (this.corrAile < f8)
        this.corrAile = (float)(this.corrAile * (1.0D - f9 * paramFloat));
    }
    else
    {
      if (this.corrAile < f8)
        this.corrAile = f8;
      if (this.corrAile > f8)
        this.corrAile = (float)(this.corrAile * (1.0D - f9 * paramFloat));
    }
    this.CT.AileronControl = (this.corrCoeff * this.corrAile + (1.0F - this.corrCoeff) * this.CT.AileronControl);
    this.CT.ElevatorControl = (this.corrCoeff * this.corrElev + (1.0F - this.corrCoeff) * this.CT.ElevatorControl);
    if ((this.Alt < 15.0F) && (this.Vwld.z < 0.0D))
      this.Vwld.z *= 0.8500000238418579D;
    if ((-this.Vwld.z * 1.5D > this.Alt) || (this.Alt < 10.0F))
    {
      if ((this.maneuver == 27) || (this.maneuver == 43) || (this.maneuver == 21) || (this.maneuver == 24) || (this.maneuver == 68) || (this.maneuver == 53))
        push();
      push(2);
      pop();
      this.submaneuver = 0;
      this.sub_Man_Count = 0;
    }
    this.W.x *= 0.95D;
  }

  protected void setSpeedControl(float paramFloat)
  {
    float f1 = 0.8F;
    float f2 = 0.02F;
    float f3 = 1.5F;
    this.CT.setAfterburnerControl(false);
    float f5;
    float f6;
    float f7;
    float f8;
    float f9;
    float f10;
    float f11;
    float f12;
    switch (this.speedMode)
    {
    case 1:
      if (this.tailForStaying == null) {
        f1 = 1.0F;
      }
      else {
        Ve.sub(this.tailForStaying.Loc, this.Loc);
        this.tailForStaying.Or.transform(this.tailOffset, tmpV3f);
        Ve.add(tmpV3f);
        f4 = (float)Ve.z;
        f5 = 0.005F * (200.0F - (float)Ve.length());
        this.Or.transformInv(Ve);
        f6 = (float)Ve.x;
        Ve.normalize();
        f5 = Math.max(f5, (float)Ve.x);
        if (f5 < 0.0F)
          f5 = 0.0F;
        Ve.set(this.Vwld);
        Ve.normalize();
        tmpV3f.set(this.tailForStaying.Vwld);
        tmpV3f.normalize();
        f7 = (float)tmpV3f.dot(Ve);
        if (f7 < 0.0F)
          f7 = 0.0F;
        f5 *= f7;
        if ((f5 > 0.0F) && (f6 < 1000.0F))
        {
          if (f6 > 300.0F)
            f6 = 300.0F;
          f8 = 0.6F;
          if (this.tailForStaying.VmaxH == this.VmaxH)
            f8 = this.tailForStaying.CT.getPower();
          if (this.Vmax < 83.0F)
          {
            f9 = f4;
            if (f9 > 0.0F)
            {
              if (f9 > 20.0F)
                f9 = 20.0F;
              this.Vwld.z += 0.01F * f9;
            }
          }

          if (f6 > 0.0F)
            f9 = 0.007F * f6 + 0.005F * f4;
          else
            f9 = 0.03F * f6 + 0.001F * f4;
          f10 = getSpeed() - this.tailForStaying.getSpeed();
          f11 = -0.3F * f10;
          f12 = -3.0F * (getForwAccel() - this.tailForStaying.getForwAccel());
          if (f10 > 27.0F)
          {
            this.CT.FlapsControl = 1.0F;
            f1 = 0.0F;
          }
          else
          {
            this.CT.FlapsControl = (0.02F * f10 + 0.02F * -f6);
            f1 = f8 + f9 + f11 + f12;
          }
        }
        else {
          f1 = 1.1F;
        }
      }
      break;
    case 2:
      f1 = (float)(1.0D - 8.000000000000001E-005D * (0.5D * this.Vwld.lengthSquared() - 9.800000000000001D * Ve.z - 0.5D * this.tailForStaying.Vwld.lengthSquared()));
      break;
    case 3:
      f1 = this.CT.PowerControl;
      if (this.AP.way.curr().Speed < 10.0F)
        this.AP.way.curr().set(1.7F * this.Vmin);
      f4 = this.AP.way.curr().Speed / this.VmaxH;
      f1 = 0.2F + 0.8F * (float)Math.pow(f4, 1.5D);
      f1 += 0.1F * (this.AP.way.curr().Speed - Pitot.Indicator((float)this.Loc.z, getSpeed())) - 3.0F * getForwAccel();
      if (getAltitude() < this.AP.way.curr().z() - 70.0F)
        f1 += this.AP.way.curr().z() - 70.0F - getAltitude();
      if (f1 > 0.93F)
        f1 = 0.93F;
      if ((f1 >= 0.35F) || (this.AP.way.isLanding())) break;
      f1 = 0.35F; break;
    case 4:
      f1 = this.CT.PowerControl;
      f1 += (f2 * (this.smConstSpeed - Pitot.Indicator((float)this.Loc.z, getSpeed())) - f3 * getForwAccel()) * paramFloat;
      if (f1 <= 1.0F) break;
      f1 = 1.0F; break;
    case 5:
      f1 = this.CT.PowerControl;
      this.CT.FlapsControl = 1.0F;
      f1 += (f2 * (1.3F * this.VminFLAPS - Pitot.Indicator((float)this.Loc.z, getSpeed())) - f3 * getForwAccel()) * paramFloat;
      break;
    case 8:
      f1 = 0.0F;
      break;
    case 6:
      if (this.Skill == 0)
        f1 = 0.95F;
      else if (this.Skill == 1)
        f1 = 0.97F;
      else if (this.Skill == 2)
        f1 = 0.99F;
      else
        f1 = 1.0F;
      break;
    case 9:
      if (this.Skill == 0)
        f1 = 1.05F;
      else if (this.Skill == 1)
        f1 = 1.07F;
      else if (this.Skill == 2)
        f1 = 1.09F;
      else
        f1 = 1.1F;
      this.CT.setAfterburnerControl(true);
      break;
    case 7:
      f1 = this.smConstPower;
      break;
    case 11:
      f1 = 1.1F;
      this.CT.setAfterburnerControl(true);
      break;
    case 10:
      if (this.tailForStaying == null) {
        f1 = 1.0F;
      }
      else {
        Ve.sub(this.tailForStaying.Loc, this.Loc);
        this.tailForStaying.Or.transform(this.tailOffset, tmpV3f);
        Ve.add(tmpV3f);
        f4 = (float)Ve.z;
        f5 = 0.005F * (200.0F - (float)Ve.length());
        this.Or.transformInv(Ve);
        f6 = (float)Ve.x;
        Ve.normalize();
        f5 = Math.max(f5, (float)Ve.x);
        if (f5 < 0.0F)
          f5 = 0.0F;
        Ve.set(this.Vwld);
        Ve.normalize();
        tmpV3f.set(this.tailForStaying.Vwld);
        tmpV3f.normalize();
        f7 = (float)tmpV3f.dot(Ve);
        if (f7 < 0.0F)
          f7 = 0.0F;
        f5 *= f7;
        if ((f5 > 0.0F) && (f6 < 1000.0F))
        {
          if (f6 > 300.0F)
            f6 = 300.0F;
          f8 = 0.6F;
          if (this.tailForStaying.VmaxH == this.VmaxH)
            f8 = this.tailForStaying.CT.getPower();
          if (this.Vmax < 83.0F)
          {
            f9 = f4;
            if (f9 > 0.0F)
            {
              if (f9 > 20.0F)
                f9 = 20.0F;
              this.Vwld.z += 0.01F * f9;
            }
          }

          if (f6 > 0.0F)
            f9 = 0.007F * f6 + 0.005F * f4;
          else
            f9 = 0.03F * f6 + 0.001F * f4;
          f10 = getSpeed() - this.tailForStaying.getSpeed();
          f11 = -0.3F * f10;
          f12 = -3.0F * (getForwAccel() - this.tailForStaying.getForwAccel());
          if (f10 > 27.0F)
          {
            this.Vwld.scale(0.9800000190734863D);
            f1 = 0.0F;
          }
          else
          {
            float f13 = 1.0F - 0.02F * (0.02F * f10 + 0.02F * -f6);
            if (f13 < 0.98F)
              f13 = 0.98F;
            if (f13 > 1.0F)
              f13 = 1.0F;
            this.Vwld.scale(f13);
            f1 = f8 + f9 + f11 + f12;
          }
        }
        else {
          f1 = 1.1F;
        }
      }
      break;
    default:
      return;
    }
    if (f1 > 1.1F)
      f1 = 1.1F;
    else if (f1 < 0.0F)
      f1 = 0.0F;
    if (Math.abs(this.CT.PowerControl - f1) < 0.5D * paramFloat)
      this.CT.PowerControl = f1;
    else if (this.CT.PowerControl < f1)
      this.CT.PowerControl += 0.5F * paramFloat;
    else
      this.CT.PowerControl -= 0.5F * paramFloat;
    float f4 = this.EI.engines[0].getCriticalW();
    if (this.EI.engines[0].getw() > 0.9F * f4)
    {
      f5 = 10.0F * (f4 - this.EI.engines[0].getw()) / f4;
      if (f5 < this.CT.PowerControl)
        this.CT.PowerControl = f5;
    }
    if (this.indSpeed > 0.8F * this.VmaxAllowed)
    {
      f5 = 1.0F * (this.VmaxAllowed - this.indSpeed) / this.VmaxAllowed;
      if (f5 < this.CT.PowerControl)
        this.CT.PowerControl = f5;
    }
  }

  private void setRandomTargDeviation(float paramFloat)
  {
    if (isTick(16, 0))
    {
      float f = paramFloat * (8.0F - 1.5F * this.Skill);
      this.TargDevVnew.set(World.Rnd().nextFloat(-f, f), World.Rnd().nextFloat(-f, f), World.Rnd().nextFloat(-f, f));
    }
    this.TargDevV.interpolate(this.TargDevVnew, 0.01F);
  }

  private Point_Any getNextAirdromeWayPoint()
  {
    if (this.airdromeWay == null)
      return null;
    if (this.airdromeWay.length == 0)
      return null;
    if (this.curAirdromePoi >= this.airdromeWay.length)
      return null;
    return this.airdromeWay[(this.curAirdromePoi++)];
  }

  private void findPointForEmLanding(float paramFloat)
  {
    Point3d localPoint3d = elLoc.getPoint();
    if (this.radius > 2.0D * this.rmax)
    {
      if (this.submaneuver != 69)
        initTargPoint(paramFloat);
      else
        return;
    }
    for (int i = 0; i < 32; i++)
    {
      Po.set(this.Vtarg.x + this.radius * Math.sin(this.phase), this.Vtarg.y + this.radius * Math.cos(this.phase), this.Loc.z);
      this.radius += 0.01D * this.rmax;
      this.phase += 0.3D;
      Ve.sub(Po, this.Loc);
      double d = Ve.length();
      this.Or.transformInv(Ve);
      Ve.normalize();
      float f1 = 0.9F - 0.005F * this.Alt;
      if (f1 < -1.0F)
        f1 = -1.0F;
      if (f1 > 0.8F)
        f1 = 0.8F;
      float f2 = 1.5F - 0.0005F * this.Alt;
      if (f2 < 1.0F)
        f2 = 1.0F;
      if (f2 > 1.5F)
        f2 = 1.5F;
      if ((this.rmax <= d) || (d <= this.rmin * f2) || (Ve.x <= f1) || (!isConvenientPoint()))
        continue;
      this.submaneuver = 69;
      localPoint3d.set(Po);
      this.pointQuality = this.curPointQuality;
      break;
    }
  }

  private boolean isConvenientPoint()
  {
    Po.z = Engine.cur.land.HQ_Air(Po.x, Po.y);
    this.curPointQuality = 50;
    for (int i = -1; i < 2; i++)
    {
      for (int j = -1; j < 2; j++)
      {
        Engine localEngine = Engine.cur;
        if (Engine.land().isWater(Po.x + 500.0D * i, Po.y + 500.0D * j))
        {
          if (!(this.actor instanceof TypeSailPlane))
            this.curPointQuality -= 1;
        }
        else if ((this.actor instanceof TypeSailPlane))
          this.curPointQuality -= 1;
        if (Engine.cur.land.HQ_ForestHeightHere(Po.x + 500.0D * i, Po.y + 500.0D * j) > 1.0D)
          this.curPointQuality -= 2;
        if (Engine.cur.land.EQN(Po.x + 500.0D * i, Po.y + 500.0D * j, Ve) > 1110.949951171875D)
          this.curPointQuality -= 2;
      }
    }
    if (this.curPointQuality < 0)
      this.curPointQuality = 0;
    return this.curPointQuality > this.pointQuality;
  }

  private void emergencyTurnToDirection(float paramFloat)
  {
    if (Math.abs(Ve.y) > 0.1000000014901161D)
      this.CT.AileronControl = (-(float)Math.atan2(Ve.y, Ve.z) - 0.016F * this.Or.getKren());
    else
      this.CT.AileronControl = (-(float)Math.atan2(Ve.y, Ve.x) - 0.016F * this.Or.getKren());
    this.CT.RudderControl = (-10.0F * (float)Math.atan2(Ve.y, Ve.x));
    if (this.CT.RudderControl * this.W.z > 0.0D)
      this.W.z = 0.0D;
    else
      this.W.z *= 1.039999961853027D;
  }

  private void initTargPoint(float paramFloat)
  {
    int i = this.AP.way.Cur();
    this.Vtarg.set(this.AP.way.last().x(), this.AP.way.last().y(), 0.0D);
    this.AP.way.setCur(i);
    this.Vtarg.sub(this.Loc);
    this.Vtarg.z = 0.0D;
    if (this.Vtarg.length() > this.rmax)
    {
      this.Vtarg.normalize();
      this.Vtarg.scale(this.rmax);
    }
    this.Vtarg.add(this.Loc);
    this.phase = 0.0D;
    this.radius = 50.0D;
    this.pointQuality = -1;
  }

  private void emergencyLanding(float paramFloat)
  {
    if (this.first)
    {
      this.Kmax = (this.Wing.new_Cya(5.0F) / this.Wing.new_Cxa(5.0F));
      if (this.Kmax > 14.0F)
        this.Kmax = 14.0F;
      this.Kmax *= 0.8F;
      this.rmax = (1.2F * this.Kmax * this.Alt);
      this.rmin = (0.6F * this.Kmax * this.Alt);
      initTargPoint(paramFloat);
      setReadyToDieSoftly(true);
      this.AP.setStabAll(false);
      if (this.TaxiMode)
      {
        setSpeedMode(8);
        this.smConstPower = 0.0F;
        push(44);
        pop();
      }
      this.dist = this.Alt;
      if ((this.actor instanceof TypeSailPlane))
        this.EI.setEngineStops();
    }
    setSpeedMode(4);
    this.smConstSpeed = (this.VminFLAPS * 1.25F);
    if ((this.Alt < 500.0F) && (((this.actor instanceof TypeGlider)) || ((this.actor instanceof TypeSailPlane))))
      this.CT.GearControl = 1.0F;
    if (this.Alt < 10.0F)
    {
      this.CT.AileronControl = (-0.04F * this.Or.getKren());
      setSpeedMode(4);
      this.smConstSpeed = (this.VminFLAPS * 1.1F);
      if (this.Alt < 5.0F)
        setSpeedMode(8);
      this.CT.BrakeControl = 0.2F;
      if ((this.Vwld.length() < 0.300000011920929D) && (World.Rnd().nextInt(0, 99) < 4))
      {
        setStationedOnGround(true);
        World.cur();
        if (this.actor != World.getPlayerAircraft())
        {
          push(44);
          safe_pop();
          if ((this.actor instanceof TypeSailPlane))
          {
            this.EI.setCurControlAll(true);
            this.EI.setEngineStops();
            if (Engine.land().isWater(this.Loc.x, this.Loc.y))
              return;
          }
          ((Aircraft)this.actor).hitDaSilk();
        }
        if (this.Group != null)
          this.Group.delAircraft((Aircraft)this.actor);
        if (((this.actor instanceof TypeGlider)) || ((this.actor instanceof TypeSailPlane)))
          return;
        World.cur();
        if (this.actor != World.getPlayerAircraft())
        {
          if (Airport.distToNearestAirport(this.Loc) > 900.0D)
            ((Aircraft)this.actor).postEndAction(60.0D, this.actor, 4, null);
          else
            MsgDestroy.Post(Time.current() + 30000L, this.actor);
        }
      }
    }
    this.dA = (0.2F * (getSpeed() - this.Vmin * 1.3F) - 0.8F * (getAOA() - 5.0F));
    if (this.Alt < 40.0F)
    {
      this.CT.AileronControl = (-0.04F * this.Or.getKren());
      this.CT.RudderControl = 0.0F;
      if (((this.actor instanceof BI_1)) || ((this.actor instanceof ME_163B1A)))
        this.CT.GearControl = 1.0F;
      float f1;
      if (this.Gears.Pitch < 10.0F)
        f1 = (40.0F * (getSpeed() - this.VminFLAPS * 1.15F) - 60.0F * (this.Or.getTangage() + 3.0F) - 240.0F * (getVertSpeed() + 1.0F) - 120.0F * ((float)getAccel().z - 1.0F)) * 0.004F;
      else
        f1 = (40.0F * (getSpeed() - this.VminFLAPS * 1.15F) - 60.0F * (this.Or.getTangage() - this.Gears.Pitch + 10.0F) - 240.0F * (getVertSpeed() + 1.0F) - 120.0F * ((float)getAccel().z - 1.0F)) * 0.004F;
      if (this.Alt > 0.0F)
      {
        float f2 = 0.01666F * this.Alt;
        this.dA = (this.dA * f2 + f1 * (1.0F - f2));
      }
      else {
        this.dA = f1;
      }this.CT.FlapsControl = 0.33F;
      if ((this.Alt < 9.0F) && (Math.abs(this.Or.getKren()) < 5.0F) && (getVertSpeed() < -0.7F))
        this.Vwld.z *= 0.8700000047683716D;
    }
    else
    {
      this.rmax = (1.2F * this.Kmax * this.Alt);
      this.rmin = (0.6F * this.Kmax * this.Alt);
      if (((this.actor instanceof TypeGlider)) && (this.Alt > 200.0F))
        this.CT.RudderControl = 0.0F;
      else if ((this.pointQuality < 50) && (this.mn_time > 0.5F))
        findPointForEmLanding(paramFloat);
      if (this.submaneuver == 69)
      {
        Ve.sub(elLoc.getPoint(), this.Loc);
        d = Ve.length();
        this.Or.transformInv(Ve);
        Ve.normalize();
        float f3 = 0.9F - 0.005F * this.Alt;
        if (f3 < -1.0F)
          f3 = -1.0F;
        if (f3 > 0.8F)
          f3 = 0.8F;
        if ((this.rmax * 2.0F < d) || (d < this.rmin) || (Ve.x < f3))
        {
          this.submaneuver = 0;
          initTargPoint(paramFloat);
        }
        if (d > 88.0D)
        {
          emergencyTurnToDirection(paramFloat);
          if (this.Alt > d)
            this.submaneuver = 0;
        }
        else {
          this.CT.AileronControl = (-0.04F * this.Or.getKren());
        }
      } else {
        this.CT.AileronControl = (-0.04F * this.Or.getKren());
      }if (this.Or.getTangage() > -1.0F)
        this.dA -= 0.1F * (this.Or.getTangage() + 1.0F);
      if (this.Or.getTangage() < -10.0F)
        this.dA -= 0.1F * (this.Or.getTangage() + 10.0F);
    }
    if ((this.Alt < 2.0F) || (this.Gears.onGround()))
      this.dA = (-2.0F * (this.Or.getTangage() - this.Gears.Pitch));
    double d = this.Vwld.length() / this.Vmin;
    if (d > 1.0D)
      d = 1.0D;
    Controls tmp1306_1303 = this.CT; tmp1306_1303.ElevatorControl = (float)(tmp1306_1303.ElevatorControl + ((this.dA - this.CT.ElevatorControl) * 3.33F * paramFloat + 1.5D * getW().y * d + 0.5D * getAW().y * d));
  }

  public boolean canITakeoff()
  {
    Po.set(this.Loc);
    Vpl.set(69.0D, 0.0D, 0.0D);
    this.Or.transform(Vpl);
    Po.add(Vpl);
    Pd.set(Po);
    if (this.actor != War.getNearestFriendAtPoint(Pd, (Aircraft)this.actor, 70.0F))
      return false;
    if (this.canTakeoff)
      return true;
    if (Actor.isAlive(this.AP.way.takeoffAirport))
    {
      if (this.AP.way.takeoffAirport.takeoffRequest <= 0)
      {
        this.AP.way.takeoffAirport.takeoffRequest = 2000;
        this.canTakeoff = true;
        return true;
      }
      return false;
    }
    return true;
  }

  public void set_maneuver_imm(int paramInt)
  {
    int i = this.maneuver;
    this.maneuver = paramInt;
    if (i != this.maneuver)
      set_flags();
  }

  private Maneuver zutiParse2Maneuver(FlightModel paramFlightModel)
  {
    Maneuver localManeuver = null;
    try
    {
      localManeuver = (Maneuver)paramFlightModel;
    }
    catch (Exception localException)
    {
      return null;
    }
    return localManeuver;
  }

  private Pilot zutiParse2Pilot(FlightModel paramFlightModel) {
    Pilot localPilot = null;
    try
    {
      localPilot = (Pilot)paramFlightModel;
    }
    catch (Exception localException)
    {
      return null;
    }
    return localPilot;
  }
}