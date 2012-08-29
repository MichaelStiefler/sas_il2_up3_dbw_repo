package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Scheme1;
import com.maddox.il2.objects.air.Scheme2a;
import com.maddox.il2.objects.air.Scheme5;
import com.maddox.il2.objects.air.Scheme7;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.RocketBombGun;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.InOutStreams;
import com.maddox.rts.KryptoInputFilter;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;

public class FlightModelMain extends FMMath
{
  public static final int __DEBUG__IL2C_DUMP_LEVEL__ = 0;
  public static final int ROOKIE = 0;
  public static final int NORMAL = 1;
  public static final int VETERAN = 2;
  public static final int ACE = 3;
  private static final long FMFLAGS_READYTORETURN = 2L;
  private static final long FMFLAGS_READYTODIE = 4L;
  private static final long FMFLAGS_TAKENMORTALDAMAGE = 8L;
  private static final long FMFLAGS_CAPABLEAIRWORTHY = 16L;
  private static final long FMFLAGS_CAPABLEACM = 32L;
  private static final long FMFLAGS_CAPABLETAXI = 64L;
  private static final long FMFLAGS_STATIONEDONGROUND = 128L;
  private static final long FMFLAGS_CRASHEDONGROUND = 256L;
  private static final long FMFLAGS_NEARAIRDROME = 512L;
  private static final long FMFLAGS_ISCROSSCOUNTRY = 1024L;
  private static final long FMFLAGS_WASAIRBORNE = 2048L;
  private static final long FMFLAGS_NETSENTWINGNOTE = 4096L;
  private static final long FMFLAGS_NETSENTBURYNOTE = 16384L;
  private static final long FMFLAGS_NETSENTCTRLSDMG = 32768L;
  private static final long FMFLAGS_NETSENT4 = 65536L;
  private static final long FMFLAGS_NETSENT5 = 131072L;
  private static final long FMFLAGS_NETSENT6 = 262144L;
  private static final long FMFLAGS_NETSENT7 = 524288L;
  public static final int FMSFX_NOOP = 0;
  public static final int FMSFX_DROP_WINGFOLDED = 1;
  public static final int FMSFX_DROP_LEFTWING = 2;
  public static final int FMSFX_DROP_RIGHTWING = 3;
  public static float outEngineAOA = 0.0F;

  private long flags0 = 240L;
  private boolean bDamagedGround = false;
  private boolean bDamaged = false;
  private Actor damagedInitiator = null;
  public int Skill;
  public int crew;
  public int turretSkill;
  public Autopilotage AP;
  public Controls CT = new Controls(this);

  public float SensYaw = 0.3F;
  public float SensPitch = 0.5F;
  public float SensRoll = 0.4F;

  public float GearCX = 0.035F;
  public float radiatorCX = 0.003F;

  public Point3d Loc = new Point3d();

  public Orientation Or = new Orientation();
  public FlightModel Leader;
  public FlightModel Wingman;
  public Vector3d Offset = new Vector3d(25.0D, 25.0D, 0.0D);

  public byte formationType = 0;
  public float formationScale = 1.0F;
  public float minElevCoeff = 4.0F;
  protected float AOA;
  protected float AOS;
  protected float V;
  protected float V2;
  protected float q_;
  protected float Gravity;
  protected float Mach;
  public float Energy;
  public float BarometerZ = 0.0F;
  public float WingDiff = 0.0F;
  public float WingLoss = 0.0F;
  public float turbCoeff = 1.0F;
  public float FuelConsumption = 0.06F;
  public Vector3d producedAM = new Vector3d(0.0D, 0.0D, 0.0D);
  public Vector3d producedAF = new Vector3d(0.0D, 0.0D, 0.0D);

  protected int fmsfxCurrentType = -1;
  protected float fmsfxPrevValue = 0.0F;
  protected long fmsfxTimeDisable = -1L;

  protected Vector3d AF = new Vector3d();
  protected Vector3d AM = new Vector3d();
  protected Vector3d GF = new Vector3d();
  protected Vector3d GM = new Vector3d();
  protected Vector3d SummF = new Vector3d();
  protected Vector3d SummM = new Vector3d();
  private static Vector3d TmpA = new Vector3d();
  private static Vector3d TmpV = new Vector3d();

  protected Vector3d ACmeter = new Vector3d();
  protected Vector3d Accel = new Vector3d();
  protected Vector3d LocalAccel = new Vector3d();
  protected Vector3d BallAccel = new Vector3d();
  public Vector3d Vwld = new Vector3d();
  public Vector3d Vrel = new Vector3d();
  protected Vector3d Vair = new Vector3d();
  protected Vector3d Vflow = new Vector3d();
  protected Vector3d Vwind = new Vector3d();
  protected Vector3d J0 = new Vector3d();
  protected Vector3d J = new Vector3d();
  protected Vector3d W = new Vector3d();
  protected Vector3d AW = new Vector3d();

  public EnginesInterface EI = new EnginesInterface();

  public Mass M = new Mass();
  public AircraftState AS = new AircraftState();
  public Squares Sq = new Squares();
  protected Arm Arms = new Arm();
  public Gear Gears = new Gear();
  public boolean AIRBRAKE;
  protected Polares Wing = new Polares();
  protected Polares Tail = new Polares();
  protected Polares Fusel = new Polares();
  public int Scheme;
  public float Wingspan;
  public float Length;
  public float Vmax = 116.66667F;
  public float VmaxH = 122.22223F;
  public float Vmin = 43.333336F;
  public float HofVmax = 277.7778F;
  public float VmaxFLAPS = 72.222229F;
  public float VminFLAPS = 39.722225F;
  public float VmaxAllowed = 208.33334F;
  public float indSpeed = 0.0F;
  public float AOA_Crit = 15.5F;
  public float Range = 800.0F;
  public float CruiseSpeed = 370.0F;

  public int[] damagedParts = new int[7];
  public int maxDamage = 0;
  public int cutPart = -1;
  private boolean bReal;
  public float UltimateLoad = 12.0F;
  public float Negative_G_Limit = -4.0F;
  public float Negative_G_Ultimate = -6.0F;
  public float LimitLoad = 8.0F;
  public float G_ClassCoeff;
  public float refM;
  public float SafetyFactor = 1.5F;
  public float ReferenceForce;
  private String aircrName;
  String turnFile = new String();
  String speedFile = new String();

  private static Orient actOr = new Orient();
  private static Vector3d actVwld = new Vector3d();

  public long Operate = 17592186044415L;

  private static Vector3d GPulse = new Vector3d();

  public static boolean bCY_CRIT04 = true;
  private static InOutStreams fmDir;

  public float getSpeedKMH()
  {
    return (float)(this.Vflow.x * 3.6D); } 
  public float getSpeed() { return (float)this.Vflow.x; } 
  public void getSpeed(Vector3d paramVector3d) { paramVector3d.set(this.Vair); } 
  public float getVertSpeed() { return (float)this.Vair.z; } 
  public float getAltitude() { return (float)this.Loc.z; } 
  public float getAOA() { return this.AOA; } 
  public float getAOS() { return this.AOS; } 
  public void getLoc(Point3f paramPoint3f) { paramPoint3f.set(this.Loc); } 
  public void getLoc(Point3d paramPoint3d) { paramPoint3d.set(this.Loc); } 
  public void getOrient(Orientation paramOrientation) { paramOrientation.set(this.Or); } 
  public Vector3d getW() { return this.W; } 
  public Vector3d getAW() { return this.AW; } 
  public Vector3d getAccel() { return this.Accel; } 
  public Vector3d getLocalAccel() { return this.LocalAccel; } 
  public Vector3d getBallAccel() {
    TmpV.set(this.LocalAccel);
    if (this.Vwld.lengthSquared() < 10.0D) {
      double d = this.Vwld.lengthSquared() - 5.0D;
      if (d < 0.0D) d = 0.0D;
      TmpV.scale(0.2D * d);
    }

    TmpA.set(0.0D, 0.0D, -Atmosphere.g());
    this.Or.transformInv(TmpA);

    TmpA.sub(TmpV);
    return TmpA;
  }
  public Vector3d getAM() { return this.AM; } 
  public Vector3d getVflow() { return this.Vflow;
  }

  public void load(String paramString)
  {
    String str1 = "Error loading params from " + paramString;
    SectFile localSectFile = sectFile(paramString);

    String str2 = "Aircraft";

    this.Wingspan = localSectFile.get(str2, "Wingspan", 0.0F); if (this.Wingspan == 0.0F) throw new RuntimeException(str1);
    this.Length = localSectFile.get(str2, "Length", 0.0F); if (this.Length == 0.0F) throw new RuntimeException(str1);
    this.Scheme = localSectFile.get(str2, "Type", -1); if (this.Scheme == -1) throw new RuntimeException(str1);

    this.crew = localSectFile.get(str2, "Crew", 0, 0, 9); if (this.crew == 0) throw new RuntimeException(str1);
    for (int j = 0; j < this.AS.astatePilotFunctions.length; j++) {
      i = localSectFile.get(str2, "CrewFunction" + j, -1);
      if (i != -1) {
        this.AS.astatePilotFunctions[j] = (byte)i;
      }
    }
    int i = localSectFile.get("Controls", "CDiveBrake", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.AIRBRAKE = (i == 1);

    str2 = "Controls";

    i = localSectFile.get(str2, "CAileron", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasAileronControl = (i == 1);
    i = localSectFile.get(str2, "CAileronTrim", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasAileronTrim = (i == 1);
    i = localSectFile.get(str2, "CElevator", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasElevatorControl = (i == 1);
    i = localSectFile.get(str2, "CElevatorTrim", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasElevatorTrim = (i == 1);
    i = localSectFile.get(str2, "CRudder", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasRudderControl = (i == 1);
    i = localSectFile.get(str2, "CRudderTrim", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasRudderTrim = (i == 1);
    i = localSectFile.get(str2, "CFlap", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasFlapsControl = (i == 1);
    i = localSectFile.get(str2, "CFlapPos", -1); if ((i < 0) || (i > 3)) throw new RuntimeException(str1);
    this.CT.bHasFlapsControlRed = (i < 3);
    i = localSectFile.get(str2, "CDiveBrake", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasAirBrakeControl = (i == 1);

    i = localSectFile.get(str2, "CUndercarriage", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasGearControl = (i == 1);
    i = localSectFile.get(str2, "CArrestorHook", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasArrestorControl = (i == 1);
    i = localSectFile.get(str2, "CWingFold", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasWingControl = (i == 1);
    i = localSectFile.get(str2, "CCockpitDoor", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasCockpitDoorControl = (i == 1);
    i = localSectFile.get(str2, "CWheelBrakes", 1);
    this.CT.bHasBrakeControl = (i == 1);
    i = localSectFile.get(str2, "CLockTailwheel", 0); if ((i != 0) && (i != 1)) throw new RuntimeException(str1);
    this.CT.bHasLockGearControl = (i == 1);
    this.CT.AilThr = (0.27778F * localSectFile.get(str2, "CAileronThreshold", 360.0F));
    this.CT.RudThr = (0.27778F * localSectFile.get(str2, "CRudderThreshold", 360.0F));
    this.CT.ElevThr = (0.27778F * localSectFile.get(str2, "CElevatorThreshold", 403.20001F));
    this.CT.CalcTresholds();

    this.CT.setTrimAileronControl(localSectFile.get(str2, "DefaultAileronTrim", -999.0F));
    if (this.CT.getTrimAileronControl() == -999.0F) throw new RuntimeException(str1);

    if (!this.CT.bHasElevatorTrim) {
      this.CT.setTrimElevatorControl(localSectFile.get(str2, "DefaultElevatorTrim", -999.0F));
      if (this.CT.getTrimElevatorControl() == -999.0F) throw new RuntimeException(str1);
    }

    this.CT.setTrimRudderControl(localSectFile.get(str2, "DefaultRudderTrim", -999.0F));
    if (this.CT.getTrimRudderControl() == -999.0F) throw new RuntimeException(str1);

    if (!this.CT.bHasGearControl) {
      this.GearCX = 0.0F;
      this.CT.GearControl = 1.0F;
      this.CT.setFixedGear(true);
    }
    if (!this.CT.bHasFlapsControl) {
      this.CT.FlapsControl = 0.0F;
    }

    i = localSectFile.get(str2, "cElectricProp", 0);
    this.CT.bUseElectricProp = (i == 1);

    float f1 = localSectFile.get(str2, "GearPeriod", -999.0F);
    if (f1 != -999.0F) {
      this.CT.dvGear = (1.0F / f1);
    }
    f1 = localSectFile.get(str2, "WingPeriod", -999.0F);
    if (f1 != -999.0F) {
      this.CT.dvWing = (1.0F / f1);
    }
    f1 = localSectFile.get(str2, "CockpitDoorPeriod", -999.0F);
    if (f1 != -999.0F)
      this.CT.dvCockpitDoor = (1.0F / f1);
    float f2;
    float f4;
    float f3;
    float f5;
    switch (this.Scheme) { default:
      throw new RuntimeException("Invalid Plane Scheme (Can't Get There!)..");
    case 0:
    case 1:
      f2 = this.Length * 0.35F;
      f2 *= f2;
      f4 = this.Length * 0.125F;
      f4 *= f4;
      f3 = this.Wingspan * 0.2F;
      f3 *= f3;
      f5 = this.Length * 0.07F;
      f5 *= f5;
      this.J0.z = (f2 * 0.2F + f4 * 0.4F + f3 * 0.4F);
      this.J0.y = (f2 * 0.2F + f4 * 0.4F + f5 * 0.4F);
      this.J0.x = (f5 * 0.6F + f3 * 0.4F);

      break;
    case 2:
      f2 = this.Length * 0.35F;
      f2 *= f2;
      f4 = this.Length * 0.125F;
      f4 *= f4;
      f3 = this.Wingspan * 0.2F;
      f3 *= f3;
      f5 = this.Length * 0.07F;
      f5 *= f5;
      this.J0.z = (f2 * 0.2F + f4 * 0.1F + f3 * 0.7F);
      this.J0.y = (f2 * 0.2F + f4 * 0.1F + f5 * 0.7F);
      this.J0.x = (f5 * 0.3F + f3 * 0.7F);

      break;
    case 3:
      f2 = this.Length * 0.35F;
      f2 *= f2;
      f4 = this.Length * 0.125F;
      f4 *= f4;
      f3 = this.Wingspan * 0.2F;
      f3 *= f3;
      f5 = this.Length * 0.07F;
      f5 *= f5;
      this.J0.z = (f2 * 0.2F + f4 * 0.2F + f3 * 0.6F);
      this.J0.y = (f2 * 0.2F + f4 * 0.2F + f5 * 0.6F);
      this.J0.x = (f5 * 0.2F + f3 * 0.8F);

      break;
    case 4:
    case 5:
    case 7:
      f2 = this.Length * 0.35F;
      f2 *= f2;
      f4 = this.Length * 0.125F;
      f4 *= f4;
      f3 = this.Wingspan * 0.2F;
      f3 *= f3;
      f5 = this.Length * 0.07F;
      f5 *= f5;
      this.J0.z = (f2 * 0.25F + f4 * 0.15F + f3 * 0.6F);
      this.J0.y = (f2 * 0.25F + f4 * 0.15F + f5 * 0.6F);
      this.J0.x = (f5 * 0.4F + f3 * 0.6F);

      break;
    case 6:
      f2 = this.Length * 0.35F;
      f2 *= f2;
      f4 = this.Length * 0.125F;
      f4 *= f4;
      f3 = this.Wingspan * 0.2F;
      f3 *= f3;
      f5 = this.Length * 0.07F;
      f5 *= f5;
      this.J0.z = (f2 * 0.25F + f4 * 0.15F + f3 * 0.6F);
      this.J0.y = (f2 * 0.25F + f4 * 0.15F + f5 * 0.6F);
      this.J0.x = (f5 * 0.4F + f3 * 0.6F);
    }

    str2 = "Params";
    if (localSectFile.exist(str2, "ReferenceWeight"))
      this.refM = localSectFile.get(str2, "ReferenceWeight", 0.0F, -2000.0F, 2000.0F);
    else {
      this.refM = 0.0F;
    }

    this.M.load(localSectFile, this);

    this.Sq.load(localSectFile);

    this.Arms.load(localSectFile);

    Aircraft.debugprintln(this.actor, "Calling engines interface to resolve file '" + localSectFile.toString() + "'....");
    this.EI.load((FlightModel)this, localSectFile);

    this.Gears.load(localSectFile);

    if (localSectFile.exist(str2, "G_CLASS")) {
      this.LimitLoad = localSectFile.get(str2, "G_CLASS", 12.0F, 0.0F, 15.0F);
      this.LimitLoad /= 1.5F;
    } else {
      this.LimitLoad = 12.0F;
    }
    if (localSectFile.exist(str2, "G_CLASS_COEFF"))
      this.G_ClassCoeff = localSectFile.get(str2, "G_CLASS_COEFF", 20.0F, -30.0F, 50.0F);
    else {
      this.G_ClassCoeff = 20.0F;
    }

    float f6 = this.M.maxWeight * Atmosphere.g();

    float f7 = this.Sq.squareWing;

    this.Vmax = localSectFile.get(str2, "Vmax", 1.0F);
    this.VmaxH = localSectFile.get(str2, "VmaxH", 1.0F);
    this.Vmin = localSectFile.get(str2, "Vmin", 1.0F);
    this.HofVmax = localSectFile.get(str2, "HofVmax", 1.0F);
    this.VmaxFLAPS = localSectFile.get(str2, "VmaxFLAPS", 1.0F);
    this.VminFLAPS = localSectFile.get(str2, "VminFLAPS", 1.0F);
    this.SensYaw = localSectFile.get(str2, "SensYaw", 1.0F);
    this.SensPitch = localSectFile.get(str2, "SensPitch", 1.0F);
    this.SensRoll = localSectFile.get(str2, "SensRoll", 1.0F);
    this.VmaxAllowed = localSectFile.get(str2, "VmaxAllowed", this.VmaxH * 1.3F);
    this.Range = localSectFile.get(str2, "Range", 800.0F);
    this.CruiseSpeed = localSectFile.get(str2, "CruiseSpeed", 0.7F * this.Vmax);
    this.FuelConsumption = (this.M.maxFuel / (0.64F * (this.Range / this.CruiseSpeed * 3600.0F) * this.EI.getNum()));

    this.Vmax *= 0.2777778F;
    this.VmaxH *= 0.2777778F;
    this.Vmin *= 0.2777778F;
    this.VmaxFLAPS *= 0.2777778F;
    this.VminFLAPS *= 0.2416667F;
    this.VmaxAllowed *= 0.2777778F;

    float f8 = Atmosphere.density(0.0F);

    this.Fusel.lineCyCoeff = 0.02F;
    this.Fusel.AOAMinCx_Shift = 0.0F;
    this.Fusel.Cy0_0 = 0.0F;
    this.Fusel.AOACritH_0 = 17.0F;
    this.Fusel.AOACritL_0 = -17.0F;
    this.Fusel.CyCritH_0 = 0.2F;
    this.Fusel.CyCritL_0 = -0.2F;
    this.Fusel.parabCxCoeff_0 = 0.0006F;
    this.Fusel.CxMin_0 = 0.0F;
    this.Fusel.Cy0_1 = 0.0F;
    this.Fusel.AOACritH_1 = 17.0F;
    this.Fusel.AOACritL_1 = -17.0F;
    this.Fusel.CyCritH_1 = 0.2F;
    this.Fusel.CyCritL_1 = -0.2F;
    this.Fusel.CxMin_1 = 0.0F;
    this.Fusel.parabCxCoeff_1 = 0.0006F;
    this.Fusel.declineCoeff = 0.007F;
    this.Fusel.maxDistAng = 30.0F;
    this.Fusel.parabAngle = 5.0F;
    this.Fusel.setFlaps(0.0F);

    this.Tail.lineCyCoeff = 0.085F;
    this.Tail.AOAMinCx_Shift = 0.0F;
    this.Tail.Cy0_0 = 0.0F;
    this.Tail.AOACritH_0 = 17.0F;
    this.Tail.AOACritL_0 = -17.0F;
    this.Tail.CyCritH_0 = 1.1F;
    this.Tail.CyCritL_0 = -1.1F;
    this.Tail.parabCxCoeff_0 = 0.0006F;
    this.Tail.CxMin_0 = 0.02F;
    this.Tail.Cy0_1 = 0.0F;
    this.Tail.AOACritH_1 = 17.0F;
    this.Tail.AOACritL_1 = -17.0F;
    this.Tail.CyCritH_1 = 1.1F;
    this.Tail.CyCritL_1 = -1.1F;
    this.Tail.CxMin_1 = 0.02F;
    this.Tail.parabCxCoeff_1 = 0.0006F;
    this.Tail.declineCoeff = 0.007F;
    this.Tail.maxDistAng = 30.0F;
    this.Tail.parabAngle = 5.0F;
    this.Tail.setFlaps(0.0F);

    str2 = "Params";
    this.Wing.AOA_crit = localSectFile.get(str2, "CriticalAOA", 16.0F);
    this.Wing.V_max = (0.27778F * localSectFile.get(str2, "Vmax", 500.0F));
    this.Wing.V_min = (0.27778F * localSectFile.get(str2, "Vmin", 160.0F));
    this.Wing.V_maxFlaps = (0.27778F * localSectFile.get(str2, "VmaxFLAPS", 270.0F));
    this.Wing.V_land = (0.27778F * localSectFile.get(str2, "VminFLAPS", 140.0F));
    this.Wing.T_turn = localSectFile.get(str2, "T_turn", 20.0F);
    this.Wing.V_turn = (0.27778F * localSectFile.get(str2, "V_turn", 300.0F));
    this.Wing.Vz_climb = localSectFile.get(str2, "Vz_climb", 18.0F);
    this.Wing.V_climb = (0.27778F * localSectFile.get(str2, "V_climb", 270.0F));
    this.Wing.K_max = localSectFile.get(str2, "K_max", 14.0F);
    this.Wing.Cy0_max = localSectFile.get(str2, "Cy0_max", 0.15F);
    this.Wing.FlapsMult = localSectFile.get(str2, "FlapsMult", 0.16F);
    this.Wing.FlapsAngSh = localSectFile.get(str2, "FlapsAngSh", 4.0F);

    this.Wing.P_Vmax = this.EI.forcePropAOA(this.Wing.V_max, 0.0F, 1.1F, true);
    this.Wing.S = f7;
    this.Wing.G = f6;

    str2 = "Polares";
    f1 = localSectFile.get(str2, "lineCyCoeff", -999.0F);
    if (f1 != -999.0F) {
      this.Wing.lineCyCoeff = f1;
      this.Wing.AOAMinCx_Shift = localSectFile.get(str2, "AOAMinCx_Shift", 0.0F);
      this.Wing.Cy0_0 = localSectFile.get(str2, "Cy0_0", 0.15F);
      this.Wing.AOACritH_0 = localSectFile.get(str2, "AOACritH_0", 16.0F);
      this.Wing.AOACritL_0 = localSectFile.get(str2, "AOACritL_0", -16.0F);
      this.Wing.CyCritH_0 = localSectFile.get(str2, "CyCritH_0", 1.1F);
      this.Wing.CyCritL_0 = localSectFile.get(str2, "CyCritL_0", -0.8F);
      this.Wing.parabCxCoeff_0 = localSectFile.get(str2, "parabCxCoeff_0", 0.0008F);
      this.Wing.CxMin_0 = localSectFile.get(str2, "CxMin_0", 0.026F);
      this.Wing.Cy0_1 = localSectFile.get(str2, "Cy0_1", 0.65F);
      this.Wing.AOACritH_1 = localSectFile.get(str2, "AOACritH_1", 15.0F);
      this.Wing.AOACritL_1 = localSectFile.get(str2, "AOACritL_1", -18.0F);
      this.Wing.CyCritH_1 = localSectFile.get(str2, "CyCritH_1", 1.6F);
      this.Wing.CyCritL_1 = localSectFile.get(str2, "CyCritL_1", -0.75F);
      this.Wing.CxMin_1 = localSectFile.get(str2, "CxMin_1", 0.09F);
      this.Wing.parabCxCoeff_1 = localSectFile.get(str2, "parabCxCoeff_1", 0.0025F);
      this.Wing.parabAngle = localSectFile.get(str2, "parabAngle", 5.0F);
      this.Wing.declineCoeff = localSectFile.get(str2, "Decline", 0.007F);
      this.Wing.maxDistAng = localSectFile.get(str2, "maxDistAng", 30.0F);

      this.Wing.setFlaps(0.0F);
    } else {
      throw new RuntimeException(str1);
    }
  }

  public void drawData()
  {
    for (int i = 0; i < 250; i++) {
      this.Wing.normP[i] = this.EI.forcePropAOA(i, 0.0F, 1.0F, false);
      this.Wing.maxP[i] = this.EI.forcePropAOA(i, 1000.0F, 1.1F, true);
    }
    this.Wing.drawGraphs(this.turnFile);
    this.Wing.setFlaps(0.0F);

    drawSpeed(this.speedFile);
    this.Wing.setFlaps(0.0F);
  }

  public void drawSpeed(String paramString)
  {
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(HomePath.toFileSystemName(paramString, 0))));

      for (int i = 0; i <= 10000; i += 100) {
        localPrintWriter.print(i + "\t");
        float f1 = -1000.0F;
        float f2 = -1000.0F;
        float f3;
        float f4;
        for (int j = 50; j < 1500; j++) {
          f3 = this.EI.forcePropAOA(j * 0.27778F, i, 1.1F, true);
          f4 = this.Wing.getClimb(j * 0.27778F, i, f3);
          if (f4 > f1) f1 = f4;
          if ((f4 < 0.0F) && (f4 < f1)) {
            f2 = j;
            break;
          }
        }
        if (f2 < 0.0F) localPrintWriter.print("\t"); else
          localPrintWriter.print(f2 + "\t");
        localPrintWriter.print(f1 * this.Wing.Vyfac + "\t");
        f1 = -1000.0F;
        f2 = -1000.0F;
        for (j = 50; j < 1500; j++) {
          f3 = this.EI.forcePropAOA(j * 0.27778F, i, 1.0F, false);
          f4 = this.Wing.getClimb(j * 0.27778F, i, f3);
          if (f4 > f1) f1 = f4;
          if ((f4 < 0.0F) && (f4 < f1)) {
            f2 = j;
            break;
          }
        }
        if (f2 < 0.0F) localPrintWriter.print("\t"); else
          localPrintWriter.print(f2 + "\t");
        localPrintWriter.print(f1 * this.Wing.Vyfac + "\t");

        localPrintWriter.println();
      }

      localPrintWriter.close();
    } catch (IOException localIOException) {
      System.out.println("File save failed: " + localIOException.getMessage());
      localIOException.printStackTrace();
    }
  }

  public FlightModelMain(String paramString)
  {
    load(paramString);
    init_G_Limits();
  }

  public void set(Loc paramLoc, Vector3f paramVector3f)
  {
    this.actor.pos.setAbs(paramLoc); this.Vwld.set(paramVector3f);
    paramLoc.get(this.Loc, this.Or);
  }

  public void set(Point3d paramPoint3d, Orient paramOrient, Vector3f paramVector3f)
  {
    this.actor.pos.setAbs(paramPoint3d, paramOrient); this.Vwld.set(paramVector3f);
    this.Loc.set(paramPoint3d);
    this.Or.set(paramOrient);
  }

  public void update(float paramFloat)
  {
    ((Aircraft)this.actor).update(paramFloat);
  }

  public boolean tick()
  {
    float f1 = Time.tickLenFs();

    float f2 = (float)(this.Loc.z - Engine.land().HQ_Air(this.Loc.x, this.Loc.y));

    this.actor.pos.getAbs(this.Loc, this.Or);

    int i = (int)(f1 / 0.05F) + 1;
    float f3 = f1 / i;
    for (int j = 0; j < i; j++) update(f3);
    this.Gears.bFlatTopGearCheck = false;

    if (this.actor.pos.base() == null)
    {
      this.actor.pos.setAbs(this.Loc, this.Or);
    }
    else
    {
      if ((this.actor.pos.base() instanceof Aircraft)) {
        this.Vwld.set(((Aircraft)this.actor.pos.base()).FM.Vwld);
      } else {
        this.actor.pos.speed(actVwld);
        this.Vwld.x = (float)actVwld.x;
        this.Vwld.y = (float)actVwld.y;
        this.Vwld.z = (float)actVwld.z;
      }
      this.actor.pos.getAbs(this.Or);
      this.producedAF.z += 9.81F * this.M.mass;
    }

    this.Energy = (Atmosphere.g() * (float)this.Loc.z + this.V2 * 0.5F);

    return true;
  }

  public float getOverload()
  {
    return (float)this.ACmeter.z;
  }

  public float getForwAccel() {
    return (float)this.ACmeter.x;
  }

  public float getRollAcceleration() {
    return (float)this.AM.x / this.Gravity;
  }

  public void gunPulse(Vector3d paramVector3d)
  {
    GPulse.set(paramVector3d);
    GPulse.scale(1.0F / this.M.mass);
    this.Vwld.sub(GPulse);
  }

  private void cutOp(int paramInt)
  {
    this.Operate &= (1L << paramInt ^ 0xFFFFFFFF);
  }
  protected boolean getOp(int paramInt) { return (this.Operate & 1L << paramInt) != 0L; } 
  private float Op(int paramInt) {
    return getOp(paramInt) ? 1.0F : 0.0F;
  }

  public final boolean isPlayers()
  {
    return (this.actor != null) && (this.actor == World.getPlayerAircraft());
  }
  public final boolean isCapableOfACM() {
    return (this.flags0 & 0x20) != 0L;
  }
  public final boolean isCapableOfBMP() {
    return (this.flags0 & 0x10) != 0L;
  }
  public final boolean isCapableOfTaxiing() {
    return (this.flags0 & 0x40) != 0L;
  }
  public final boolean isReadyToDie() {
    return (this.flags0 & 0x4) != 0L;
  }
  public final boolean isReadyToReturn() {
    return (this.flags0 & 0x2) != 0L;
  }
  public final boolean isTakenMortalDamage() {
    return (this.flags0 & 0x8) != 0L;
  }
  public final boolean isStationedOnGround() {
    return (this.flags0 & 0x80) != 0L;
  }
  public final boolean isCrashedOnGround() {
    return (this.flags0 & 0x100) != 0L;
  }
  public final boolean isNearAirdrome() {
    return (this.flags0 & 0x200) != 0L;
  }
  public final boolean isCrossCountry() {
    return (this.flags0 & 0x400) != 0L;
  }
  public final boolean isWasAirborne() {
    return (this.flags0 & 0x800) != 0L;
  }
  public final boolean isSentWingNote() {
    return (this.flags0 & 0x1000) != 0L;
  }
  public final boolean isSentBuryNote() {
    return (this.flags0 & 0x4000) != 0L;
  }
  public final boolean isSentControlsOutNote() {
    return (this.flags0 & 0x8000) != 0L;
  }

  public boolean isOk() {
    return (isCapableOfBMP()) && (!isReadyToDie()) && (!isTakenMortalDamage());
  }

  private void checkDamaged()
  {
    if ((!this.bDamaged) && 
      (Actor.isValid(this.damagedInitiator)) && (
      (!isCapableOfBMP()) || (isTakenMortalDamage()))) {
      this.bDamaged = true;
      if (this.actor != this.damagedInitiator)
        EventLog.onDamaged(this.actor, this.damagedInitiator);
      this.damagedInitiator = null;
    }

    if ((!this.bDamagedGround) && 
      (isStationedOnGround()) && (
      (!isCapableOfBMP()) || (!isCapableOfTaxiing()) || (isReadyToDie()) || (isTakenMortalDamage()) || (isSentControlsOutNote())))
    {
      this.bDamagedGround = true;
      EventLog.onDamagedGround(this.actor);
    }
  }

  public final void setCapableOfACM(boolean paramBoolean)
  {
    if (isCapableOfACM() == paramBoolean) return;
    if (paramBoolean)
      this.flags0 |= 32L;
    else
      this.flags0 &= -33L;
  }

  public final void setCapableOfBMP(boolean paramBoolean, Actor paramActor)
  {
    if (isCapableOfBMP() == paramBoolean) return;
    if ((isCapableOfBMP()) && (World.Rnd().nextInt(0, 99) < 25)) {
      Voice.speakMayday((Aircraft)this.actor);
    }
    if (paramBoolean) {
      this.flags0 |= 16L;
    } else {
      this.flags0 &= -17L;
      if (!this.bDamaged)
        this.damagedInitiator = paramActor;
      checkDamaged();
    }
  }

  public final void setCapableOfTaxiing(boolean paramBoolean) {
    if (isCapableOfTaxiing() == paramBoolean) return;
    if (paramBoolean) {
      this.flags0 |= 64L;
    } else {
      this.flags0 &= -65L;
      checkDamaged();
    }
  }

  public final void setReadyToDie(boolean paramBoolean)
  {
    if (isReadyToDie() == paramBoolean) return;
    if (!isReadyToDie()) {
      if (World.Rnd().nextInt(0, 99) < 75) Voice.speakMayday((Aircraft)this.actor);
      Explosions.generateComicBulb(this.actor, "OnFire", 9.0F);
    }
    if (paramBoolean) {
      this.flags0 |= 4L;
      checkDamaged();
    } else {
      this.flags0 &= -5L;
    }
  }

  public final void setReadyToDieSoftly(boolean paramBoolean)
  {
    if (isReadyToDie() == paramBoolean) return;
    if (paramBoolean) {
      this.flags0 |= 4L;
      checkDamaged();
    } else {
      this.flags0 &= -5L;
    }
  }

  public final void setReadyToReturn(boolean paramBoolean)
  {
    if (isReadyToReturn() == paramBoolean) return;
    if (!isReadyToReturn()) {
      Explosions.generateComicBulb(this.actor, "RTB", 9.0F);
    }
    if (paramBoolean)
      this.flags0 |= 2L;
    else {
      this.flags0 &= -3L;
    }
    Voice.speakToReturn((Aircraft)this.actor);
  }

  public final void setReadyToReturnSoftly(boolean paramBoolean) {
    if (isReadyToReturn() == paramBoolean) return;
    if (paramBoolean)
      this.flags0 |= 2L;
    else
      this.flags0 &= -3L;
  }

  public final void setTakenMortalDamage(boolean paramBoolean, Actor paramActor)
  {
    if (isTakenMortalDamage() == paramBoolean) return;
    if (paramBoolean) {
      this.flags0 |= 8L;
      if ((!this.bDamaged) && (!Actor.isValid(this.damagedInitiator)))
        this.damagedInitiator = paramActor;
      checkDamaged();
    } else {
      this.flags0 &= -9L;
    }
    if ((paramBoolean) && 
      (this.actor != World.getPlayerAircraft()) && (((Aircraft)this.actor).FM.turret.length > 0))
      for (int i = 0; i < ((Aircraft)this.actor).FM.turret.length; i++)
        ((Aircraft)this.actor).FM.turret[i].bIsOperable = false;
  }

  public final void setStationedOnGround(boolean paramBoolean)
  {
    if (isStationedOnGround() == paramBoolean) return;
    if (paramBoolean) {
      this.flags0 |= 128L;
      EventLog.onAirLanded((Aircraft)this.actor);
      checkDamaged();
    } else {
      this.flags0 &= -129L;
    }
  }

  public final void setCrashedOnGround(boolean paramBoolean) {
    if (isCrashedOnGround() == paramBoolean) return;
    if (paramBoolean) {
      this.flags0 |= 256L;
      checkDamaged();
    } else {
      this.flags0 &= -257L;
    }
  }

  public final void setNearAirdrome(boolean paramBoolean) {
    if (isNearAirdrome() == paramBoolean) return;
    if (paramBoolean)
      this.flags0 |= 512L;
    else
      this.flags0 &= -513L;
  }

  public final void setCrossCountry(boolean paramBoolean)
  {
    if (isCrossCountry() == paramBoolean) return;
    if (paramBoolean)
      this.flags0 |= 1024L;
    else
      this.flags0 &= -1025L;
  }

  public final void setWasAirborne(boolean paramBoolean)
  {
    if (isWasAirborne() == paramBoolean) return;
    if (paramBoolean)
      this.flags0 |= 2048L;
    else
      this.flags0 &= -2049L;
  }

  public final void setSentWingNote(boolean paramBoolean)
  {
    if (isSentWingNote() == paramBoolean) return;
    if (paramBoolean)
      this.flags0 |= 4096L;
    else
      this.flags0 &= -4097L;
  }

  public final void setSentBuryNote(boolean paramBoolean)
  {
    if (isSentBuryNote() == paramBoolean) return;
    if (paramBoolean)
      this.flags0 |= 16384L;
    else
      this.flags0 &= -16385L;
  }

  public final void setSentControlsOutNote(boolean paramBoolean)
  {
    if (isSentControlsOutNote() == paramBoolean) return;
    if (paramBoolean) {
      this.flags0 |= 32768L;
      checkDamaged();
    } else {
      this.flags0 &= -32769L;
    }
  }

  public void hit(int paramInt)
  {
    Aircraft.debugprintln(this.actor, "Detected NDL in " + Aircraft.partNames()[paramInt] + "..");
    if ((paramInt < 0) || (paramInt >= 44)) return;

    if ((this instanceof RealFlightModel))
      this.bReal = true;
    else {
      this.bReal = false;
    }

    switch (paramInt) {
    case 13:
      this.Sq.dragFuselageCx = (this.Sq.dragFuselageCx > 0.08F ? this.Sq.dragFuselageCx * 2.0F : 0.08F);
      break;
    case 0:
      this.Sq.liftWingLOut *= 0.95F;
      this.SensRoll *= 0.68F;
      break;
    case 1:
      this.Sq.liftWingROut *= 0.95F;
      this.SensRoll *= 0.68F;
      break;
    case 9:
      break;
    case 10:
      break;
    case 17:
      this.Sq.liftStab *= 0.68F;

      this.Sq.getClass(); this.Sq.dragProducedCx += 0.12F;
      break;
    case 18:
      this.Sq.liftStab *= 0.68F;

      this.Sq.getClass(); this.Sq.dragProducedCx += 0.12F;
      break;
    case 31:
      this.Sq.squareElevators *= 0.68F;
      this.SensPitch *= 0.68F;
      break;
    case 32:
      this.Sq.squareElevators *= 0.68F;
      this.SensPitch *= 0.68F;
      break;
    case 11:
      this.Sq.liftKeel *= 0.68F;

      this.Sq.getClass(); this.Sq.dragProducedCx += 0.12F;
      break;
    case 12:
      this.Sq.liftKeel *= 0.68F;

      this.Sq.getClass(); this.Sq.dragProducedCx += 0.12F;
      break;
    case 15:
      this.Sq.squareRudders *= 0.5F;
      this.SensYaw *= 0.68F;
      break;
    case 16:
      this.Sq.squareRudders *= 0.5F;
      this.SensYaw *= 0.68F;
      break;
    case 33:
      if (this.bReal)
        setDmgLoadLimit(0.6F, 2);
      this.Sq.getClass(); this.Sq.liftWingLIn -= 0.8F;

      this.Sq.getClass(); this.Sq.dragProducedCx += 0.12F;
      if (World.Rnd().nextFloat() < 0.09F) setReadyToReturn(true);
      if (World.Rnd().nextFloat() >= 0.09F) break; setCapableOfACM(false); break;
    case 36:
      if (this.bReal)
        setDmgLoadLimit(0.6F, 3);
      this.Sq.getClass(); this.Sq.liftWingRIn -= 0.8F;

      this.Sq.getClass(); this.Sq.dragProducedCx += 0.12F;
      if (World.Rnd().nextFloat() < 0.09F) setReadyToReturn(true);
      if (World.Rnd().nextFloat() >= 0.09F) break; setCapableOfACM(false); break;
    case 34:
      if (this.bReal)
        setDmgLoadLimit(0.7F, 1);
      this.Sq.getClass(); this.Sq.liftWingLMid -= 0.8F;

      this.Sq.getClass(); this.Sq.dragProducedCx += 0.12F;
      if (World.Rnd().nextFloat() < 0.09F) setReadyToReturn(true);
      if (World.Rnd().nextFloat() >= 0.09F) break; setCapableOfACM(false); break;
    case 37:
      if (this.bReal)
        setDmgLoadLimit(0.7F, 4);
      this.Sq.getClass(); this.Sq.liftWingRMid -= 0.8F;

      this.Sq.getClass(); this.Sq.dragProducedCx += 0.12F;
      if (World.Rnd().nextFloat() < 0.09F) setReadyToReturn(true);
      if (World.Rnd().nextFloat() >= 0.09F) break; setCapableOfACM(false); break;
    case 35:
      if (this.bReal)
        setDmgLoadLimit(0.8F, 0);
      this.Sq.getClass(); this.Sq.liftWingLOut -= 0.8F;

      this.Sq.getClass(); this.Sq.dragProducedCx += 0.12F;
      if (World.Rnd().nextFloat() < 0.12F) setReadyToReturn(true);
      if (World.Rnd().nextFloat() >= 0.12F) break; setCapableOfACM(false); break;
    case 38:
      if (this.bReal)
        setDmgLoadLimit(0.8F, 5);
      this.Sq.getClass(); this.Sq.liftWingROut -= 0.8F;

      this.Sq.getClass(); this.Sq.dragProducedCx += 0.12F;
      if (World.Rnd().nextFloat() < 0.12F) setReadyToReturn(true);
      if (World.Rnd().nextFloat() >= 0.12F) break; setCapableOfACM(false); break;
    case 3:
      if (this.Sq.dragEngineCx[0] < 0.15F)
      {
        int tmp1240_1239 = 0;
        float[] tmp1240_1236 = this.Sq.dragEngineCx; tmp1240_1236[tmp1240_1239] = (float)(tmp1240_1236[tmp1240_1239] + 0.05D);
      }if (World.Rnd().nextFloat() >= 0.12F) break; setReadyToReturn(true); break;
    case 4:
      if (this.Sq.dragEngineCx[1] < 0.15F) this.Sq.dragEngineCx[1] += 0.05F;
      if (World.Rnd().nextFloat() >= 0.12F) break; setReadyToReturn(true); break;
    case 5:
      if (this.Sq.dragEngineCx[2] < 0.15F) this.Sq.dragEngineCx[2] += 0.05F;
      if (World.Rnd().nextFloat() >= 0.12F) break; setReadyToReturn(true); break;
    case 6:
      if (this.Sq.dragEngineCx[3] < 0.15F) this.Sq.dragEngineCx[3] += 0.05F;
      if (World.Rnd().nextFloat() >= 0.12F) break; setReadyToReturn(true); break;
    case 19:
    case 20:
      if (!this.bReal) break;
      setDmgLoadLimit(0.5F, 6);
    case 2:
    case 7:
    case 8:
    case 14:
    case 21:
    case 22:
    case 23:
    case 24:
    case 25:
    case 26:
    case 27:
    case 28:
    case 29:
    case 30: }  } 
  public void cut(int paramInt1, int paramInt2, Actor paramActor) { if ((paramInt1 < 0) || (paramInt1 >= 44)) return;
    Aircraft.debugprintln(this.actor, "cutting part #" + paramInt1 + " (" + Aircraft.partNames()[paramInt1] + ")");
    if (!getOp(paramInt1)) {
      Aircraft.debugprintln(this.actor, "part #" + paramInt1 + " (" + Aircraft.partNames()[paramInt1] + ") already cut off");
      return;
    }cutOp(paramInt1);

    switch (paramInt1)
    {
    case 13:
      setCapableOfBMP(false, paramActor);
      setCapableOfACM(false);
      this.Sq.dragFuselageCx = (this.Sq.dragFuselageCx > 0.24F ? this.Sq.dragFuselageCx * 2.0F : 0.24F);
      break;
    case 9:
      setCapableOfTaxiing(false);
      this.Gears.hitLeftGear();
      break;
    case 10:
      setCapableOfTaxiing(false);
      this.Gears.hitRightGear();
      break;
    case 7:
      setCapableOfTaxiing(false);
      this.Gears.hitCentreGear();
      break;
    case 2:
      setCapableOfACM(false);
      setCapableOfBMP(false, paramActor);
      setCapableOfTaxiing(false);
      setTakenMortalDamage(true, paramActor);

      for (int i = 0; i < this.EI.getNum(); i++) {
        this.EI.engines[i].setReadyness(this.actor, 0.0F);
      }

      cutOp(19);
      cutOp(20);
    case 19:
    case 20:
      setCapableOfACM(false);
      setCapableOfBMP(false, paramActor);
      setReadyToDie(true);
      setTakenMortalDamage(true, paramActor);
      this.Arms.GCENTER = 0.0F;
      this.W.y += World.Rnd().nextFloat(-0.1F, 0.1F);
      this.W.z += World.Rnd().nextFloat(-0.1F, 0.1F);
      this.J0.y *= 0.5D;

      cut(17, paramInt2, paramActor);
      cut(18, paramInt2, paramActor);

      cut(11, paramInt2, paramActor);
      cut(12, paramInt2, paramActor);
      break;
    case 17:
      if (World.Rnd().nextInt(-1, 8) < this.Skill) setReadyToReturn(true);
      if (World.Rnd().nextInt(-1, 16) < this.Skill) setReadyToDie(true);
      this.Sq.liftStab *= (0.5F * Op(18) + 0.1F);

      if (isPlayers()) {
        this.Sq.getClass(); this.Sq.dragProducedCx += 0.12F;
      } else {
        this.Sq.getClass(); this.Sq.dragProducedCx += 0.06F;
      }
      this.Sq.liftWingRMid *= 0.9F;
      this.Sq.liftWingLMid *= 1.1F;
      this.Sq.liftWingROut *= 0.9F;
      this.Sq.liftWingLOut *= 1.1F;
      if (Op(18) == 0.0F) {
        this.CT.setTrimAileronControl(this.CT.getTrimAileronControl() - 0.25F);
        if (World.Rnd().nextBoolean()) {
          this.Sq.liftWingLOut *= 0.95F;
          this.Sq.liftWingLMid *= 0.95F;
          this.Sq.liftWingLIn *= 0.95F;
          this.Sq.liftWingRIn *= 0.75F;
          this.Sq.liftWingRMid *= 0.75F;
          this.Sq.liftWingROut *= 0.75F;
        } else {
          this.Sq.liftWingROut *= 0.75F;
          this.Sq.liftWingRMid *= 0.75F;
          this.Sq.liftWingRIn *= 0.75F;
          this.Sq.liftWingLIn *= 0.95F;
          this.Sq.liftWingLMid *= 0.95F;
          this.Sq.liftWingLOut *= 0.95F;
        }
      }
      cutOp(31);
    case 31:
      setCapableOfACM(false);
      if (Op(32) == 0.0F) setReadyToDie(true);
      this.Sq.squareElevators *= 0.5F * Op(32);
      this.SensPitch *= 0.5F * Op(32);
      break;
    case 18:
      if (World.Rnd().nextInt(-1, 8) < this.Skill) setReadyToReturn(true);
      if (World.Rnd().nextInt(-1, 16) < this.Skill) setReadyToDie(true);
      this.Sq.liftStab *= (0.5F * Op(17) + 0.1F);

      if (isPlayers()) {
        this.Sq.getClass(); this.Sq.dragProducedCx += 0.12F;
      } else {
        this.Sq.getClass(); this.Sq.dragProducedCx += 0.06F;
      }
      this.Sq.liftWingLMid *= 0.9F;
      this.Sq.liftWingRMid *= 1.1F;
      this.Sq.liftWingLOut *= 0.9F;
      this.Sq.liftWingROut *= 1.1F;
      if (Op(17) == 0.0F) {
        this.CT.setTrimAileronControl(this.CT.getTrimAileronControl() + 0.25F);
        if (World.Rnd().nextBoolean()) {
          this.Sq.liftWingLOut *= 0.95F;
          this.Sq.liftWingLMid *= 0.95F;
          this.Sq.liftWingLIn *= 0.95F;
          this.Sq.liftWingRIn *= 0.75F;
          this.Sq.liftWingRMid *= 0.75F;
          this.Sq.liftWingROut *= 0.75F;
        } else {
          this.Sq.liftWingROut *= 0.75F;
          this.Sq.liftWingRMid *= 0.75F;
          this.Sq.liftWingRIn *= 0.75F;
          this.Sq.liftWingLIn *= 0.95F;
          this.Sq.liftWingLMid *= 0.95F;
          this.Sq.liftWingLOut *= 0.95F;
        }
      }
      cutOp(32);
    case 32:
      setCapableOfACM(false);
      if (Op(31) == 0.0F) setReadyToDie(true);
      this.Sq.squareElevators *= 0.5F * Op(31);
      this.SensPitch *= 0.5F * Op(31);
      break;
    case 11:
      if (World.Rnd().nextInt(-1, 8) < this.Skill) setReadyToReturn(true);
      if (World.Rnd().nextInt(-1, 16) < this.Skill) setReadyToDie(true);
      if (((this.actor instanceof Scheme2a)) || ((this.actor instanceof Scheme5)) || ((this.actor instanceof Scheme7))) {
        this.Sq.liftKeel *= 0.25F * Op(12);
      }
      else {
        this.Sq.liftKeel *= 0.0F;
      }

      cutOp(15);
    case 15:
      setCapableOfACM(false);
      if (World.Rnd().nextInt(-1, 8) < this.Skill) setReadyToReturn(true);
      if (((this.actor instanceof Scheme2a)) || ((this.actor instanceof Scheme5)) || ((this.actor instanceof Scheme7))) {
        this.Sq.squareRudders *= 0.5F;
        this.SensYaw *= 0.25F;
      } else {
        this.Sq.squareRudders *= 0.0F;
        this.SensYaw *= 0.0F;
      }
      break;
    case 12:
      if (World.Rnd().nextInt(-1, 8) < this.Skill) setReadyToReturn(true);
      if (World.Rnd().nextInt(-1, 16) < this.Skill) setReadyToDie(true);
      if (((this.actor instanceof Scheme2a)) || ((this.actor instanceof Scheme5)) || ((this.actor instanceof Scheme7))) {
        this.Sq.liftKeel *= 0.25F * Op(12);
      }
      else {
        this.Sq.liftKeel *= 0.0F;
      }

      cutOp(16);
    case 16:
      setCapableOfACM(false);
      if (World.Rnd().nextInt(-1, 8) < this.Skill) setReadyToReturn(true);
      if (((this.actor instanceof Scheme2a)) || ((this.actor instanceof Scheme5)) || ((this.actor instanceof Scheme7))) {
        this.Sq.squareRudders *= 0.5F;
        this.SensYaw *= 0.25F;
      } else {
        this.Sq.squareRudders *= 0.0F;
        this.SensYaw *= 0.0F;
      }
      break;
    case 33:
      this.Sq.liftWingLIn *= 0.25F;

      ((ActorHMesh)this.actor).destroyChildFiltered(BombGun.class);
      ((ActorHMesh)this.actor).destroyChildFiltered(RocketBombGun.class);
      cut(9, paramInt2, paramActor);
      cutOp(34);
    case 34:
      setTakenMortalDamage(true, paramActor);
      setReadyToDie(true);
      this.Sq.liftWingLMid *= 0.0F;

      this.Sq.liftWingLIn *= 0.9F;
      ((ActorHMesh)this.actor).destroyChildFiltered(RocketGun.class);
      cutOp(35);
    case 35:
      setCapableOfBMP(false, paramActor);
      setCapableOfACM(false);
      this.AS.bWingTipLExists = false;
      this.AS.setStallState(false);
      this.AS.setAirShowState(false);
      this.Sq.liftWingLOut *= 0.0F;

      this.Sq.liftWingLMid *= 0.5F;

      this.Sq.liftWingLOut = 0.0F;
      this.Sq.liftWingLMid = 0.0F;
      this.Sq.liftWingLIn = 0.0F;
      this.CT.bHasAileronControl = false;

      cutOp(0);
    case 0:
      if (Op(1) == 0.0F) setCapableOfACM(false);
      this.Sq.squareAilerons *= 0.5F;
      this.SensRoll *= 0.5F * Op(1);
      break;
    case 36:
      this.Sq.liftWingRIn *= 0.25F;

      ((ActorHMesh)this.actor).destroyChildFiltered(BombGun.class);
      ((ActorHMesh)this.actor).destroyChildFiltered(RocketBombGun.class);
      cut(10, paramInt2, paramActor);
      cutOp(37);
    case 37:
      setTakenMortalDamage(true, paramActor);
      setReadyToDie(true);
      this.Sq.liftWingRMid *= 0.0F;

      this.Sq.liftWingRIn *= 0.9F;
      ((ActorHMesh)this.actor).destroyChildFiltered(RocketGun.class);
      cutOp(38);
    case 38:
      setCapableOfBMP(false, paramActor);
      setCapableOfACM(false);
      this.AS.bWingTipRExists = false;
      this.AS.setStallState(false);
      this.AS.setAirShowState(false);
      this.Sq.liftWingROut *= 0.0F;

      this.Sq.liftWingRMid *= 0.5F;

      this.Sq.liftWingROut = 0.0F;
      this.Sq.liftWingRMid = 0.0F;
      this.Sq.liftWingRIn = 0.0F;
      this.CT.bHasAileronControl = false;

      cutOp(1);
    case 1:
      if (Op(0) == 0.0F) setCapableOfACM(false);
      this.Sq.squareAilerons *= 0.5F;
      this.SensRoll *= 0.5F * Op(0);
      break;
    case 3:
      if (this.EI.engines.length <= 0) break;
      setCapableOfTaxiing(false);
      if (!(this.actor instanceof Scheme1)) setReadyToDie(true);
      this.EI.engines[0].setEngineDies(this.actor);
      this.Sq.dragEngineCx[0] = 0.15F; break;
    case 4:
      if (this.EI.engines.length <= 1) break;
      setCapableOfTaxiing(false);
      setReadyToDie(true);
      this.EI.engines[1].setEngineDies(this.actor);
      this.Sq.dragEngineCx[1] = 0.15F; break;
    case 5:
      if (this.EI.engines.length <= 2) break;
      setCapableOfTaxiing(false);
      setReadyToDie(true);
      this.EI.engines[2].setEngineDies(this.actor);
      this.Sq.dragEngineCx[2] = 0.15F; break;
    case 6:
      if (this.EI.engines.length <= 3) break;
      setCapableOfTaxiing(false);
      setReadyToDie(true);
      this.EI.engines[3].setEngineDies(this.actor);
      this.Sq.dragEngineCx[3] = 0.15F;
    case 8:
    case 14:
    case 21:
    case 22:
    case 23:
    case 24:
    case 25:
    case 26:
    case 27:
    case 28:
    case 29:
    case 30: }  } 
  private void init_G_Limits() { this.UltimateLoad = (this.LimitLoad * 1.5F);
    this.ReferenceForce = (this.LimitLoad * (this.M.referenceWeight + this.refM));
    this.Negative_G_Limit = (-0.5F * this.LimitLoad);
    this.Negative_G_Ultimate = (this.Negative_G_Limit * 1.5F);
  }

  public void setDmgLoadLimit(float paramFloat, int paramInt)
  {
    setLimitLoad(this.LimitLoad -= paramFloat);
    this.damagedParts[paramInt] += 1;

    for (paramInt = 0; paramInt < this.damagedParts.length; paramInt++)
      if (this.damagedParts[paramInt] > this.maxDamage)
        this.cutPart = paramInt;
  }

  public void setUltimateLoad(float paramFloat)
  {
    this.UltimateLoad = (paramFloat * this.SafetyFactor);
    this.Negative_G_Ultimate = (this.UltimateLoad * -0.5F);
  }

  public float getUltimateLoad() {
    return this.UltimateLoad;
  }

  public void setLimitLoad(float paramFloat) {
    this.LimitLoad = paramFloat;
    this.Negative_G_Limit = (-paramFloat * 0.5F);
    setUltimateLoad(paramFloat);
  }

  public float getLimitLoad() {
    return this.LimitLoad;
  }

  public void setSafetyFactor(float paramFloat) {
    this.SafetyFactor -= paramFloat;
  }

  public float getSafetyFactor() {
    return this.SafetyFactor;
  }

  public float getLoadDiff() {
    return getLimitLoad() - getOverload();
  }

  public void doRequestFMSFX(int paramInt1, int paramInt2)
  {
    if ((this.fmsfxCurrentType == 1) && 
      (paramInt1 != 1)) {
      return;
    }

    switch (paramInt1) {
    case 0:
      this.fmsfxCurrentType = paramInt1;
      break;
    case 1:
      this.fmsfxCurrentType = paramInt1;
      this.fmsfxPrevValue = (paramInt2 * 0.01F);
      if (this.fmsfxPrevValue >= 0.05F) break;
      this.fmsfxCurrentType = 0; break;
    case 2:
    case 3:
      this.fmsfxCurrentType = paramInt1;
      this.fmsfxTimeDisable = (Time.current() + 100 * paramInt2);
    }
  }

  public void setGCenter(float paramFloat)
  {
    this.Arms.GCENTER = paramFloat;
  }

  public void setGC_Gear_Shift(float paramFloat) {
    this.Arms.GC_GEAR_SHIFT = paramFloat;
  }

  public void setFlapsShift(float paramFloat) {
    this.Wing.setFlaps(paramFloat);
  }

  public static long finger(long paramLong, String paramString)
  {
    SectFile localSectFile1 = sectFile(paramString);
    paramLong = localSectFile1.finger(paramLong);
    for (int i = 0; i < 10; i++) {
      String str1 = "Engine" + i + "Family";
      String str2 = localSectFile1.get("Engine", str1);
      if (str2 == null)
        break;
      SectFile localSectFile2 = sectFile("FlightModels/" + str2 + ".emd");
      paramLong = localSectFile2.finger(paramLong);
    }
    return paramLong;
  }

  public static SectFile sectFile(String paramString)
  {
    SectFile localSectFile = null;
    String str = paramString.toLowerCase();
    try
    {
      Object localObject = Property.value(paramString, "stream", null);
      InputStream localInputStream;
      if (localObject != null) {
        localInputStream = (InputStream)localObject;
      } else {
        if (fmDir == null) {
          fmDir = new InOutStreams();
          fmDir.open(Finger.LongFN(0L, "gui/game/buttons"));
        }
        localInputStream = fmDir.openStream("" + Finger.Int(new StringBuffer().append(str).append("d2wO").toString()));
      }
      localInputStream.mark(0);
      localSectFile = new SectFile(new InputStreamReader(new KryptoInputFilter(localInputStream, getSwTbl(Finger.Int(str + "ogh9"), localInputStream.available())), "Cp1252"));

      localInputStream.reset();
      if (localObject == null)
        Property.set(paramString, "stream", localInputStream);
    } catch (Exception localException) {
    }
    return localSectFile;
  }

  private static int[] getSwTbl(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0) paramInt1 = -paramInt1;
    if (paramInt2 < 0) paramInt2 = -paramInt2;
    int i = (paramInt2 + paramInt1 / 5) % 16 + 14;
    int j = (paramInt2 + paramInt1 / 19) % Finger.kTable.length;
    if (i < 0)
      i = -i % 16;
    if (i < 10)
      i = 10;
    if (j < 0)
      j = -j % Finger.kTable.length;
    int[] arrayOfInt = new int[i];
    for (int k = 0; k < i; k++)
      arrayOfInt[k] = Finger.kTable[((j + k) % Finger.kTable.length)];
    return arrayOfInt;
  }
}