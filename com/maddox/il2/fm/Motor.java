package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.BI_1;
import com.maddox.il2.objects.air.BI_6;
import com.maddox.il2.objects.air.GLADIATOR;
import com.maddox.il2.objects.air.MXY_7;
import com.maddox.il2.objects.air.P_38;
import com.maddox.il2.objects.air.P_51;
import com.maddox.il2.objects.air.P_51Mustang;
import com.maddox.il2.objects.air.P_63C;
import com.maddox.il2.objects.air.SPITFIRE12;
import com.maddox.il2.objects.air.SPITFIRE14C;
import com.maddox.il2.objects.air.SPITFIRE8;
import com.maddox.il2.objects.air.SPITFIRE8CLP;
import com.maddox.il2.objects.air.SPITFIRE8CLPFB;
import com.maddox.il2.objects.air.SPITFIRE8FB;
import com.maddox.il2.objects.air.SPITFIRE9;
import com.maddox.il2.objects.air.SPITFIRELF14E;
import com.maddox.il2.objects.air.SPITFIREXIIclpearly;
import com.maddox.il2.objects.air.YAK_3;
import com.maddox.il2.objects.air.YAK_3P;
import com.maddox.il2.objects.air.YAK_9M;
import com.maddox.il2.objects.air.YAK_9U;
import com.maddox.il2.objects.air.YAK_9UT;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import java.io.IOException;

public class Motor extends FMMath
{
  private static final boolean ___debug___ = false;
  public static final int _E_TYPE_INLINE = 0;
  public static final int _E_TYPE_RADIAL = 1;
  public static final int _E_TYPE_JET = 2;
  public static final int _E_TYPE_ROCKET = 3;
  public static final int _E_TYPE_ROCKETBOOST = 4;
  public static final int _E_TYPE_TOW = 5;
  public static final int _E_TYPE_PVRD = 6;
  public static final int _E_TYPE_HELO_INLINE = 7;
  public static final int _E_TYPE_UNIDENTIFIED = 8;
  public static final int _E_PROP_DIR_LEFT = 0;
  public static final int _E_PROP_DIR_RIGHT = 1;
  public static final int _E_STAGE_NULL = 0;
  public static final int _E_STAGE_WAKE_UP = 1;
  public static final int _E_STAGE_STARTER_ROLL = 2;
  public static final int _E_STAGE_CATCH_UP = 3;
  public static final int _E_STAGE_CATCH_ROLL = 4;
  public static final int _E_STAGE_CATCH_FIRE = 5;
  public static final int _E_STAGE_NOMINAL = 6;
  public static final int _E_STAGE_DEAD = 7;
  public static final int _E_STAGE_STUCK = 8;
  public static final int _E_PROP_FIXED = 0;
  public static final int _E_PROP_RETAIN_RPM_1 = 1;
  public static final int _E_PROP_RETAIN_RPM_2 = 2;
  public static final int _E_PROP_RETAIN_AOA_1 = 3;
  public static final int _E_PROP_RETAIN_AOA_2 = 4;
  public static final int _E_PROP_FRICTION = 5;
  public static final int _E_PROP_MANUALDRIVEN = 6;
  public static final int _E_PROP_WM_KOMANDGERAT = 7;
  public static final int _E_PROP_FW_KOMANDGERAT = 8;
  public static final int _E_CARB_SUCTION = 0;
  public static final int _E_CARB_CARBURETOR = 1;
  public static final int _E_CARB_INJECTOR = 2;
  public static final int _E_CARB_FLOAT = 3;
  public static final int _E_COMPRESSOR_NONE = 0;
  public static final int _E_COMPRESSOR_MANUALSTEP = 1;
  public static final int _E_COMPRESSOR_WM_KOMANDGERAT = 2;
  public static final int _E_COMPRESSOR_TURBO = 3;
  public static final int _E_MIXER_GENERIC = 0;
  public static final int _E_MIXER_BRIT_FULLAUTO = 1;
  public static final int _E_MIXER_LIMITED_PRESSURE = 2;
  public static final int _E_AFTERBURNER_GENERIC = 0;
  public static final int _E_AFTERBURNER_MW50 = 1;
  public static final int _E_AFTERBURNER_GM1 = 2;
  public static final int _E_AFTERBURNER_FIRECHAMBER = 3;
  public static final int _E_AFTERBURNER_WATER = 4;
  public static final int _E_AFTERBURNER_NO2 = 5;
  public static final int _E_AFTERBURNER_FUEL_INJECTION = 6;
  public static final int _E_AFTERBURNER_FUEL_ILA5 = 7;
  public static final int _E_AFTERBURNER_FUEL_ILA5AUTO = 8;
  public static final int _E_AFTERBURNER_WATERMETHANOL = 9;
  public static final int _E_AFTERBURNER_P51 = 10;
  public static final int _E_AFTERBURNER_SPIT = 11;
  public static final int _E_AFTERBURNER_START_U_NOTLEISTUNG = 12;
  public static final int _E_AFTERBURNER_DECOLLO_ED_EMERGENZA = 13;
  private static int heatStringID = -1;
  public FmSounds isnd;
  private FlightModel reference;
  private static boolean bTFirst;
  public String soundName;
  public String startStopName;
  public String propName;
  private int number;
  private int type;
  private int cylinders;
  private float engineMass;
  private float wMin;
  private float wNom;
  private float wMax;
  private float wWEP;
  private float wMaxAllowed;
  public int wNetPrev;
  private float engineMoment;
  private float engineMomentMax;
  private float engineBoostFactor;
  private float engineAfterburnerBoostFactor;
  private float engineDistAM;
  private float engineDistBM;
  private float engineDistCM;
  private float producedDistabilisation;
  private boolean bRan;
  private Point3f enginePos;
  private Vector3f engineVector;
  private Vector3f engineForce;
  private Vector3f engineTorque;
  private float engineDamageAccum;
  private float _1_wMaxAllowed;
  private float _1_wMax;
  private float RPMMin;
  private float RPMNom;
  private float RPMMax;
  private float Vopt;
  private float pressureExtBar;
  private double momForFuel;
  public double addVflow;
  public double addVside;
  private Point3f propPos;
  private float propReductor;
  private int propAngleDeviceType;
  private float propAngleDeviceMinParam;
  private float propAngleDeviceMaxParam;
  private float propAngleDeviceAfterburnerParam;
  private int propDirection;
  private float propDiameter;
  private float propMass;
  private float propI;
  public Vector3d propIW;
  private float propSEquivalent;
  private float propr;
  private float propPhiMin;
  private float propPhiMax;
  private float propPhi;
  private float propPhiW;
  private float propAoA;
  private float propAoA0;
  private float propAoACrit;
  private float propAngleChangeSpeed;
  private float propForce;
  private float propMoment;
  private float propTarget;
  private int mixerType;
  private float mixerLowPressureBar;
  private float horsePowers;
  private float thrustMax;
  private int cylindersOperable;
  private float engineI;
  private float engineAcceleration;
  private boolean[] bMagnetos = { true, true };
  private boolean bIsAutonomous;
  private boolean bIsMaster;
  private boolean bIsStuck;
  private boolean bIsInoperable;
  private boolean bIsAngleDeviceOperational;
  private boolean isPropAngleDeviceHydroOperable;
  private int engineCarburetorType;
  private float FuelConsumptionP0;
  private float FuelConsumptionP05;
  private float FuelConsumptionP1;
  private float FuelConsumptionPMAX;
  private int compressorType;
  public int compressorMaxStep;
  private float compressorPMax;
  private float compressorManifoldPressure;
  public float[] compressorAltitudes;
  private float[] compressorPressure;
  private float[] compressorAltMultipliers;
  private float compressorRPMtoP0;
  private float compressorRPMtoCurvature;
  private float compressorRPMtoPMax;
  private float compressorRPMtoWMaxATA;
  private float compressorSpeedManifold;
  private float[] compressorRPM;
  private float[] compressorATA;
  private int nOfCompPoints;
  private boolean compressorStepFound;
  private float compressorManifoldThreshold;
  private float afterburnerCompressorFactor;
  private float _1_P0;
  private float compressor1stThrottle;
  private float compressor2ndThrottle;
  private float compressorPAt0;
  private int afterburnerType;
  private boolean afterburnerChangeW;
  private int stage;
  private int oldStage;
  private long timer;
  private long given;
  private float rpm;
  private float w;
  private float aw;
  private float oldW;
  private float readyness;
  private float oldReadyness;
  private float radiatorReadyness;
  private float rearRush;
  public float tOilIn;
  public float tOilOut;
  public float tWaterOut;
  public float tCylinders;
  private float tWaterCritMin;
  public float tWaterCritMax;
  private float tOilCritMin;
  public float tOilCritMax;
  private float tWaterMaxRPM;
  public float tOilOutMaxRPM;
  private float tOilInMaxRPM;
  private float tChangeSpeed;
  private float timeOverheat;
  private float timeUnderheat;
  private float timeCounter;
  private float oilMass;
  private float waterMass;
  private float Ptermo;
  private float R_air;
  private float R_oil;
  private float R_water;
  private float R_cyl_oil;
  private float R_cyl_water;
  private float C_eng;
  private float C_oil;
  private float C_water;
  private boolean bHasThrottleControl;
  private boolean bHasAfterburnerControl;
  private boolean bHasPropControl;
  private boolean bHasRadiatorControl;
  private boolean bHasMixControl;
  private boolean bHasMagnetoControl;
  private boolean bHasExtinguisherControl;
  public boolean bHasCompressorControl;
  private boolean bHasFeatherControl;
  private int extinguishers;
  private float controlThrottle;
  private float controlRadiator;
  private boolean controlAfterburner;
  private float controlProp;
  private boolean bControlPropAuto;
  private float controlMix;
  private int controlMagneto;
  private int controlCompressor;
  private int controlFeather;
  public boolean fastATA;
  private Vector3f old_engineForce;
  private Vector3f old_engineTorque;
  private float updateStep;
  private float updateLast;
  private float fricCoeffT;
  private static Vector3f tmpV3f = new Vector3f();
  private static Vector3d tmpV3d1 = new Vector3d();
  private static Vector3d tmpV3d2 = new Vector3d();
  private static Point3f safeloc = new Point3f();
  private static Point3d safeLoc = new Point3d();
  private static Vector3f safeVwld = new Vector3f();
  private static Vector3f safeVflow = new Vector3f();
  private static boolean tmpB;
  private static float tmpF;
  private int engineNoFuelHUDLogId;

  public Motor()
  {
    this.isnd = null;
    this.reference = null;
    this.soundName = null;
    this.startStopName = null;
    this.propName = null;
    this.number = 0;
    this.type = 0;
    this.cylinders = 12;
    this.engineMass = 900.0F;
    this.wMin = 20.0F;
    this.wNom = 180.0F;
    this.wMax = 200.0F;
    this.wWEP = 220.0F;
    this.wMaxAllowed = 250.0F;
    this.wNetPrev = 0;
    this.engineMoment = 0.0F;
    this.engineMomentMax = 0.0F;
    this.engineBoostFactor = 1.0F;
    this.engineAfterburnerBoostFactor = 1.0F;
    this.engineDistAM = 0.0F;
    this.engineDistBM = 0.0F;
    this.engineDistCM = 0.0F;
    this.bRan = false;
    this.enginePos = new Point3f();
    this.engineVector = new Vector3f();
    this.engineForce = new Vector3f();
    this.engineTorque = new Vector3f();
    this.engineDamageAccum = 0.0F;
    this._1_wMaxAllowed = (1.0F / this.wMaxAllowed);
    this._1_wMax = (1.0F / this.wMax);
    this.RPMMin = 200.0F;
    this.RPMNom = 2000.0F;
    this.RPMMax = 2200.0F;
    this.Vopt = 90.0F;
    this.momForFuel = 0.0D;
    this.addVflow = 0.0D;
    this.addVside = 0.0D;
    this.propPos = new Point3f();
    this.propReductor = 1.0F;
    this.propAngleDeviceType = 0;
    this.propAngleDeviceMinParam = 0.0F;
    this.propAngleDeviceMaxParam = 0.0F;
    this.propAngleDeviceAfterburnerParam = -999.90002F;
    this.propDirection = 0;
    this.propDiameter = 3.0F;
    this.propMass = 30.0F;
    this.propI = 1.0F;
    this.propIW = new Vector3d();
    this.propSEquivalent = 1.0F;
    this.propr = 1.125F;
    this.propPhiMin = (float)Math.toRadians(10.0D);
    this.propPhiMax = (float)Math.toRadians(29.0D);
    this.propPhi = (float)Math.toRadians(11.0D);
    this.propAoA0 = (float)Math.toRadians(11.0D);
    this.propAoACrit = (float)Math.toRadians(16.0D);
    this.propAngleChangeSpeed = 0.1F;
    this.propForce = 0.0F;
    this.propMoment = 0.0F;
    this.propTarget = 0.0F;
    this.mixerType = 0;
    this.mixerLowPressureBar = 0.0F;
    this.horsePowers = 1200.0F;
    this.thrustMax = 10.7F;
    this.cylindersOperable = 12;
    this.engineI = 1.0F;
    this.engineAcceleration = 1.0F;
    this.bIsAutonomous = true;
    this.bIsMaster = true;
    this.bIsStuck = false;
    this.bIsInoperable = false;
    this.bIsAngleDeviceOperational = true;
    this.isPropAngleDeviceHydroOperable = true;
    this.engineCarburetorType = 0;
    this.FuelConsumptionP0 = 0.4F;
    this.FuelConsumptionP05 = 0.24F;
    this.FuelConsumptionP1 = 0.28F;
    this.FuelConsumptionPMAX = 0.3F;
    this.compressorType = 0;
    this.compressorMaxStep = 0;
    this.compressorPMax = 1.0F;
    this.compressorManifoldPressure = 1.0F;
    this.compressorAltitudes = null;
    this.compressorPressure = null;
    this.compressorAltMultipliers = null;
    this.compressorRPMtoP0 = 1500.0F;
    this.compressorRPMtoCurvature = -30.0F;
    this.compressorRPMtoPMax = 2600.0F;
    this.compressorRPMtoWMaxATA = 1.45F;
    this.compressorSpeedManifold = 0.2F;
    this.compressorRPM = new float[16];
    this.compressorATA = new float[16];
    this.nOfCompPoints = 0;
    this.compressorStepFound = false;
    this.compressorManifoldThreshold = 1.0F;
    this.afterburnerCompressorFactor = 1.0F;
    this._1_P0 = (1.0F / Atmosphere.P0());
    this.compressor1stThrottle = 1.0F;
    this.compressor2ndThrottle = 1.0F;
    this.compressorPAt0 = 0.3F;
    this.afterburnerType = 0;
    this.afterburnerChangeW = false;
    this.stage = 0;
    this.oldStage = 0;
    this.timer = 0L;
    this.given = 4611686018427387903L;
    this.rpm = 0.0F;
    this.w = 0.0F;
    this.aw = 0.0F;
    this.oldW = 0.0F;
    this.readyness = 1.0F;
    this.oldReadyness = 1.0F;
    this.radiatorReadyness = 1.0F;
    this.tOilIn = 0.0F;
    this.tOilOut = 0.0F;
    this.tWaterOut = 0.0F;
    this.tCylinders = 0.0F;
    this.oilMass = 90.0F;
    this.waterMass = 90.0F;
    this.bHasThrottleControl = true;
    this.bHasAfterburnerControl = true;
    this.bHasPropControl = true;
    this.bHasRadiatorControl = true;
    this.bHasMixControl = true;
    this.bHasMagnetoControl = true;
    this.bHasExtinguisherControl = false;
    this.bHasCompressorControl = false;
    this.bHasFeatherControl = false;
    this.extinguishers = 0;
    this.controlThrottle = 0.0F;
    this.controlRadiator = 0.0F;
    this.controlAfterburner = false;
    this.controlProp = 1.0F;
    this.bControlPropAuto = true;
    this.controlMix = 1.0F;
    this.controlMagneto = 0;
    this.controlCompressor = 0;
    this.controlFeather = 0;
    this.fastATA = false;
    this.old_engineForce = new Vector3f();
    this.old_engineTorque = new Vector3f();
    this.updateStep = 0.12F;
    this.updateLast = 0.0F;
    this.fricCoeffT = 1.0F;
    this.engineNoFuelHUDLogId = -1;
  }

  public void load(FlightModel paramFlightModel, String paramString1, String paramString2, int paramInt)
  {
    this.reference = paramFlightModel;
    this.number = paramInt;
    SectFile localSectFile = FlightModelMain.sectFile(paramString1);
    resolveFromFile(localSectFile, "Generic");
    resolveFromFile(localSectFile, paramString2);
    calcAfterburnerCompressorFactor();
    if ((this.type == 0) || (this.type == 1) || (this.type == 7))
      initializeInline(paramFlightModel.jdField_Vmax_of_type_Float);
    if (this.type == 2)
      initializeJet(paramFlightModel.jdField_Vmax_of_type_Float);
  }

  private void resolveFromFile(SectFile paramSectFile, String paramString) {
    this.soundName = paramSectFile.get(paramString, "SoundName", this.soundName);
    this.propName = paramSectFile.get(paramString, "PropName", this.propName);
    this.startStopName = paramSectFile.get(paramString, "StartStopName", this.startStopName);
    Aircraft.debugprintln(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, "Resolving submodel " + paramString + " from file '" + paramSectFile.toString() + "'....");

    String str = paramSectFile.get(paramString, "Type");
    if (str != null) {
      if (str.endsWith("Inline"))
        this.type = 0;
      else if (str.endsWith("Radial"))
        this.type = 1;
      else if (str.endsWith("Jet"))
        this.type = 2;
      else if (str.endsWith("RocketBoost"))
        this.type = 4;
      else if (str.endsWith("Rocket"))
        this.type = 3;
      else if (str.endsWith("Tow"))
        this.type = 5;
      else if (str.endsWith("PVRD"))
        this.type = 6;
      else if (str.endsWith("Unknown"))
        this.type = 8;
      else if (str.endsWith("Azure"))
        this.type = 8;
      else if (str.endsWith("HeloI"))
        this.type = 7;
    }
    if ((this.type == 0) || (this.type == 1) || (this.type == 7)) {
      int i = paramSectFile.get(paramString, "Cylinders", -99999);
      if (i != -99999) {
        this.cylinders = i;
        this.cylindersOperable = this.cylinders;
      }
    }
    str = paramSectFile.get(paramString, "Direction");
    if (str != null) {
      if (str.endsWith("Left"))
        this.propDirection = 0;
      else if (str.endsWith("Right"))
        this.propDirection = 1;
    }
    float f1 = paramSectFile.get(paramString, "RPMMin", -99999.0F);
    if (f1 != -99999.0F) {
      this.RPMMin = f1;
      this.wMin = toRadianPerSecond(this.RPMMin);
    }
    f1 = paramSectFile.get(paramString, "RPMNom", -99999.0F);
    if (f1 != -99999.0F) {
      this.RPMNom = f1;
      this.wNom = toRadianPerSecond(this.RPMNom);
    }
    f1 = paramSectFile.get(paramString, "RPMMax", -99999.0F);
    if (f1 != -99999.0F) {
      this.RPMMax = f1;
      this.wMax = toRadianPerSecond(this.RPMMax);
      this._1_wMax = (1.0F / this.wMax);
    }
    f1 = paramSectFile.get(paramString, "RPMMaxAllowed", -99999.0F);
    if (f1 != -99999.0F) {
      this.wMaxAllowed = toRadianPerSecond(f1);
      this._1_wMaxAllowed = (1.0F / this.wMaxAllowed);
    }
    f1 = paramSectFile.get(paramString, "Reductor", -99999.0F);
    if (f1 != -99999.0F)
      this.propReductor = f1;
    if ((this.type == 0) || (this.type == 1) || (this.type == 7)) {
      f1 = paramSectFile.get(paramString, "HorsePowers", -99999.0F);
      if (f1 != -99999.0F)
        this.horsePowers = f1;
      j = paramSectFile.get(paramString, "Carburetor", -99999);
      if (j != -99999)
        this.engineCarburetorType = j;
      f1 = paramSectFile.get(paramString, "Mass", -99999.0F);
      if (f1 != -99999.0F)
        this.engineMass = f1;
      else
        this.engineMass = (this.horsePowers * 0.6F);
    } else {
      f1 = paramSectFile.get(paramString, "Thrust", -99999.0F);
      if (f1 != -99999.0F)
        this.thrustMax = (f1 * 9.81F);
    }
    f1 = paramSectFile.get(paramString, "BoostFactor", -99999.0F);
    if (f1 != -99999.0F)
      this.engineBoostFactor = f1;
    f1 = paramSectFile.get(paramString, "WEPBoostFactor", -99999.0F);
    if (f1 != -99999.0F)
      this.engineAfterburnerBoostFactor = f1;
    if (this.type == 2) {
      this.FuelConsumptionP0 = 0.075F;
      this.FuelConsumptionP05 = 0.075F;
      this.FuelConsumptionP1 = 0.1F;
      this.FuelConsumptionPMAX = 0.11F;
    }
    if (this.type == 6) {
      this.FuelConsumptionP0 = 0.835F;
      this.FuelConsumptionP05 = 0.835F;
      this.FuelConsumptionP1 = 0.835F;
      this.FuelConsumptionPMAX = 0.835F;
    }
    f1 = paramSectFile.get(paramString, "FuelConsumptionP0", -99999.0F);
    if (f1 != -99999.0F)
      this.FuelConsumptionP0 = f1;
    f1 = paramSectFile.get(paramString, "FuelConsumptionP05", -99999.0F);
    if (f1 != -99999.0F)
      this.FuelConsumptionP05 = f1;
    f1 = paramSectFile.get(paramString, "FuelConsumptionP1", -99999.0F);
    if (f1 != -99999.0F)
      this.FuelConsumptionP1 = f1;
    f1 = paramSectFile.get(paramString, "FuelConsumptionPMAX", -99999.0F);
    if (f1 != -99999.0F)
      this.FuelConsumptionPMAX = f1;
    int j = paramSectFile.get(paramString, "Autonomous", -99999);
    if (j != -99999) {
      if (j == 0)
        this.bIsAutonomous = false;
      else if (j == 1)
        this.bIsAutonomous = true;
    }
    j = paramSectFile.get(paramString, "cThrottle", -99999);
    if (j != -99999) {
      if (j == 0)
        this.bHasThrottleControl = false;
      else if (j == 1)
        this.bHasThrottleControl = true;
    }
    j = paramSectFile.get(paramString, "cAfterburner", -99999);
    if (j != -99999) {
      if (j == 0)
        this.bHasAfterburnerControl = false;
      else if (j == 1)
        this.bHasAfterburnerControl = true;
    }
    j = paramSectFile.get(paramString, "cProp", -99999);
    if (j != -99999) {
      if (j == 0)
        this.bHasPropControl = false;
      else if (j == 1)
        this.bHasPropControl = true;
    }
    j = paramSectFile.get(paramString, "cMix", -99999);
    if (j != -99999) {
      if (j == 0)
        this.bHasMixControl = false;
      else if (j == 1)
        this.bHasMixControl = true;
    }
    j = paramSectFile.get(paramString, "cMagneto", -99999);
    if (j != -99999) {
      if (j == 0)
        this.bHasMagnetoControl = false;
      else if (j == 1)
        this.bHasMagnetoControl = true;
    }
    j = paramSectFile.get(paramString, "cCompressor", -99999);
    if (j != -99999) {
      if (j == 0)
        this.bHasCompressorControl = false;
      else if (j == 1)
        this.bHasCompressorControl = true;
    }
    j = paramSectFile.get(paramString, "cFeather", -99999);
    if (j != -99999) {
      if (j == 0)
        this.bHasFeatherControl = false;
      else if (j == 1)
        this.bHasFeatherControl = true;
    }
    j = paramSectFile.get(paramString, "cRadiator", -99999);
    if (j != -99999) {
      if (j == 0)
        this.bHasRadiatorControl = false;
      else if (j == 1)
        this.bHasRadiatorControl = true;
    }
    j = paramSectFile.get(paramString, "Extinguishers", -99999);
    if (j != -99999) {
      this.extinguishers = j;
      if (j != 0)
        this.bHasExtinguisherControl = true;
      else
        this.bHasExtinguisherControl = false;
    }
    f1 = paramSectFile.get(paramString, "PropDiameter", -99999.0F);
    if (f1 != -99999.0F)
      this.propDiameter = f1;
    this.propr = (0.5F * this.propDiameter * 0.75F);
    f1 = paramSectFile.get(paramString, "PropMass", -99999.0F);
    if (f1 != -99999.0F)
      this.propMass = f1;
    this.propI = (this.propMass * this.propDiameter * this.propDiameter * 0.083F);
    j = paramSectFile.get(paramString, "PropAnglerType", -99999);
    if (j != -99999)
      this.propAngleDeviceType = j;
    f1 = paramSectFile.get(paramString, "PropAnglerSpeed", -99999.0F);
    if (f1 != -99999.0F)
      this.propAngleChangeSpeed = f1;
    f1 = paramSectFile.get(paramString, "PropAnglerMinParam", -99999.0F);
    if (f1 != -99999.0F) {
      this.propAngleDeviceMinParam = f1;
      if ((this.propAngleDeviceType == 6) || (this.propAngleDeviceType == 5)) {
        this.propAngleDeviceMinParam = (float)Math.toRadians(this.propAngleDeviceMinParam);
      }
      if ((this.propAngleDeviceType == 1) || (this.propAngleDeviceType == 2) || (this.propAngleDeviceType == 7) || (this.propAngleDeviceType == 8))
      {
        this.propAngleDeviceMinParam = toRadianPerSecond(this.propAngleDeviceMinParam);
      }
    }
    f1 = paramSectFile.get(paramString, "PropAnglerMaxParam", -99999.0F);
    if (f1 != -99999.0F) {
      this.propAngleDeviceMaxParam = f1;
      if ((this.propAngleDeviceType == 6) || (this.propAngleDeviceType == 5)) {
        this.propAngleDeviceMaxParam = (float)Math.toRadians(this.propAngleDeviceMaxParam);
      }
      if ((this.propAngleDeviceType == 1) || (this.propAngleDeviceType == 2) || (this.propAngleDeviceType == 7) || (this.propAngleDeviceType == 8))
      {
        this.propAngleDeviceMaxParam = toRadianPerSecond(this.propAngleDeviceMaxParam);
      }
      if (this.propAngleDeviceAfterburnerParam == -999.90002F)
        this.propAngleDeviceAfterburnerParam = this.propAngleDeviceMaxParam;
    }
    f1 = paramSectFile.get(paramString, "PropAnglerAfterburnerParam", -99999.0F);
    if (f1 != -99999.0F) {
      this.propAngleDeviceAfterburnerParam = f1;
      this.wWEP = toRadianPerSecond(this.propAngleDeviceAfterburnerParam);
      if (this.wWEP != this.wMax)
        this.afterburnerChangeW = true;
      if ((this.propAngleDeviceType == 6) || (this.propAngleDeviceType == 5)) {
        this.propAngleDeviceAfterburnerParam = (float)Math.toRadians(this.propAngleDeviceAfterburnerParam);
      }

      if ((this.propAngleDeviceType == 1) || (this.propAngleDeviceType == 2) || (this.propAngleDeviceType == 7) || (this.propAngleDeviceType == 8))
      {
        this.propAngleDeviceAfterburnerParam = toRadianPerSecond(this.propAngleDeviceAfterburnerParam);
      }
    } else {
      this.wWEP = this.wMax;
    }f1 = paramSectFile.get(paramString, "PropPhiMin", -99999.0F);
    if (f1 != -99999.0F) {
      this.propPhiMin = (float)Math.toRadians(f1);
      if (this.propPhi < this.propPhiMin)
        this.propPhi = this.propPhiMin;
      if (this.propTarget < this.propPhiMin)
        this.propTarget = this.propPhiMin;
    }
    f1 = paramSectFile.get(paramString, "PropPhiMax", -99999.0F);
    if (f1 != -99999.0F) {
      this.propPhiMax = (float)Math.toRadians(f1);
      if (this.propPhi > this.propPhiMax)
        this.propPhi = this.propPhiMax;
      if (this.propTarget > this.propPhiMax)
        this.propTarget = this.propPhiMax;
    }
    f1 = paramSectFile.get(paramString, "PropAoA0", -99999.0F);
    if (f1 != -99999.0F)
      this.propAoA0 = (float)Math.toRadians(f1);
    j = paramSectFile.get(paramString, "CompressorType", -99999);
    if (j != -99999)
      this.compressorType = j;
    f1 = paramSectFile.get(paramString, "CompressorPMax", -99999.0F);
    if (f1 != -99999.0F)
      this.compressorPMax = f1;
    j = paramSectFile.get(paramString, "CompressorSteps", -99999);
    if (j != -99999) {
      this.compressorMaxStep = (j - 1);
      if (this.compressorMaxStep < 0)
        this.compressorMaxStep = 0;
    }
    if ((this.compressorAltitudes != null) && (this.compressorAltitudes.length != this.compressorMaxStep + 1));
    this.compressorAltitudes = new float[this.compressorMaxStep + 1];
    this.compressorPressure = new float[this.compressorMaxStep + 1];
    this.compressorAltMultipliers = new float[this.compressorMaxStep + 1];
    if (this.compressorAltitudes.length > 0) {
      for (k = 0; k < this.compressorAltitudes.length; k++) {
        f1 = paramSectFile.get(paramString, "CompressorAltitude" + k, -99999.0F);

        if (f1 != -99999.0F) {
          this.compressorAltitudes[k] = f1;
          this.compressorPressure[k] = (Atmosphere.pressure(this.compressorAltitudes[k]) * this._1_P0);
        }

        f1 = paramSectFile.get(paramString, "CompressorMultiplier" + k, -99999.0F);

        if (f1 != -99999.0F)
          this.compressorAltMultipliers[k] = f1;
      }
    }
    f1 = paramSectFile.get(paramString, "CompressorRPMP0", -99999.0F);
    if (f1 != -99999.0F) {
      this.compressorRPMtoP0 = f1;
      insetrPoiInCompressorPoly(this.compressorRPMtoP0, 1.0F);
    }
    f1 = paramSectFile.get(paramString, "CompressorRPMCurvature", -99999.0F);
    if (f1 != -99999.0F)
      this.compressorRPMtoCurvature = f1;
    f1 = paramSectFile.get(paramString, "CompressorMaxATARPM", -99999.0F);
    if (f1 != -99999.0F) {
      this.compressorRPMtoWMaxATA = f1;
      insetrPoiInCompressorPoly(this.RPMMax, this.compressorRPMtoWMaxATA);
    }
    f1 = paramSectFile.get(paramString, "CompressorRPMPMax", -99999.0F);
    if (f1 != -99999.0F) {
      this.compressorRPMtoPMax = f1;
      insetrPoiInCompressorPoly(this.compressorRPMtoPMax, this.compressorPMax);
    }
    f1 = paramSectFile.get(paramString, "CompressorSpeedManifold", -99999.0F);
    if (f1 != -99999.0F)
      this.compressorSpeedManifold = f1;
    f1 = paramSectFile.get(paramString, "CompressorPAt0", -99999.0F);
    if (f1 != -99999.0F)
      this.compressorPAt0 = f1;
    f1 = paramSectFile.get(paramString, "Voptimal", -99999.0F);
    if (f1 != -99999.0F)
      this.Vopt = (f1 * 0.277778F);
    int k = 1;
    float f2 = 2000.0F;
    float f3 = 1.0F;
    int m = 0;
    while (k != 0) {
      f1 = paramSectFile.get(paramString, "CompressorRPM" + m, -99999.0F);
      if (f1 != -99999.0F)
        f2 = f1;
      else
        k = 0;
      f1 = paramSectFile.get(paramString, "CompressorATA" + m, -99999.0F);
      if (f1 != -99999.0F)
        f3 = f1;
      else
        k = 0;
      if (k != 0)
        insetrPoiInCompressorPoly(f2, f3);
      m++;
      if ((this.nOfCompPoints > 15) || (m > 15))
        k = 0;
    }
    j = paramSectFile.get(paramString, "AfterburnerType", -99999);
    if (j != -99999)
      this.afterburnerType = j;
    j = paramSectFile.get(paramString, "MixerType", -99999);
    if (j != -99999)
      this.mixerType = j;
    f1 = paramSectFile.get(paramString, "MixerAltitude", -99999.0F);
    if (f1 != -99999.0F)
      this.mixerLowPressureBar = (Atmosphere.pressure(f1) / Atmosphere.P0());
    f1 = paramSectFile.get(paramString, "EngineI", -99999.0F);
    if (f1 != -99999.0F)
      this.engineI = f1;
    f1 = paramSectFile.get(paramString, "EngineAcceleration", -99999.0F);
    if (f1 != -99999.0F)
      this.engineAcceleration = f1;
    f1 = paramSectFile.get(paramString, "DisP0x", -99999.0F);
    if (f1 != -99999.0F) {
      float f4 = paramSectFile.get(paramString, "DisP0x", -99999.0F);
      f4 = toRadianPerSecond(f4);
      float f5 = paramSectFile.get(paramString, "DisP0y", -99999.0F);
      f5 *= 0.01F;
      float f6 = paramSectFile.get(paramString, "DisP1x", -99999.0F);
      f6 = toRadianPerSecond(f6);
      float f7 = paramSectFile.get(paramString, "DisP1y", -99999.0F);
      f7 *= 0.01F;
      float f8 = f4;
      float f9 = f5;
      float f10 = (f6 - f4) * (f6 - f4);
      float f11 = f7 - f5;
      this.engineDistAM = (f11 / f10);
      this.engineDistBM = (-2.0F * f11 * f8 / f10);
      this.engineDistCM = (f9 + f11 * f8 * f8 / f10);
    }
    this.timeCounter = 0.0F;
    f1 = paramSectFile.get(paramString, "TESPEED", -99999.0F);
    if (f1 != -99999.0F)
      this.tChangeSpeed = f1;
    f1 = paramSectFile.get(paramString, "TWATERMAXRPM", -99999.0F);
    if (f1 != -99999.0F)
      this.tWaterMaxRPM = f1;
    f1 = paramSectFile.get(paramString, "TOILINMAXRPM", -99999.0F);
    if (f1 != -99999.0F)
      this.tOilInMaxRPM = f1;
    f1 = paramSectFile.get(paramString, "TOILOUTMAXRPM", -99999.0F);
    if (f1 != -99999.0F)
      this.tOilOutMaxRPM = f1;
    f1 = paramSectFile.get(paramString, "MAXRPMTIME", -99999.0F);
    if (f1 != -99999.0F)
      this.timeOverheat = f1;
    f1 = paramSectFile.get(paramString, "MINRPMTIME", -99999.0F);
    if (f1 != -99999.0F)
      this.timeUnderheat = f1;
    f1 = paramSectFile.get(paramString, "TWATERMAX", -99999.0F);
    if (f1 != -99999.0F)
      this.tWaterCritMax = f1;
    f1 = paramSectFile.get(paramString, "TWATERMIN", -99999.0F);
    if (f1 != -99999.0F)
      this.tWaterCritMin = f1;
    f1 = paramSectFile.get(paramString, "TOILMAX", -99999.0F);
    if (f1 != -99999.0F)
      this.tOilCritMax = f1;
    f1 = paramSectFile.get(paramString, "TOILMIN", -99999.0F);
    if (f1 != -99999.0F)
      this.tOilCritMin = f1;
  }

  private void initializeInline(float paramFloat) {
    this.propSEquivalent = (0.26F * this.propr * this.propr);
    this.engineMomentMax = (this.horsePowers * 746.0F * 1.2F / this.wMax);
  }

  private void initializeJet(float paramFloat) {
    this.propSEquivalent = (this.cylinders * this.cylinders * (2.0F * this.thrustMax) / (getFanCy(this.propAoA0) * Atmosphere.ro0() * this.wMax * this.wMax * this.propr * this.propr));

    computePropForces(this.wMax, 0.0F, 0.0F, this.propAoA0, 0.0F);
    this.engineMomentMax = this.propMoment;
  }

  public void initializeTowString(float paramFloat) {
    this.propForce = paramFloat;
  }

  public void setMaster(boolean paramBoolean) {
    this.bIsMaster = paramBoolean;
  }

  private void insetrPoiInCompressorPoly(float paramFloat1, float paramFloat2)
  {
    for (int i = 0; i < this.nOfCompPoints; i++) {
      if (this.compressorRPM[i] >= paramFloat1) {
        if (this.compressorRPM[i] != paramFloat1)
          break;
        return;
      }
    }
    for (int j = this.nOfCompPoints - 1; j >= i; j--) {
      this.compressorRPM[(j + 1)] = this.compressorRPM[j];
      this.compressorATA[(j + 1)] = this.compressorATA[j];
    }
    this.nOfCompPoints += 1;
    this.compressorRPM[i] = paramFloat1;
    this.compressorATA[i] = paramFloat2;
  }

  private void calcAfterburnerCompressorFactor() {
    if ((this.afterburnerType == 1) || (this.afterburnerType == 7) || (this.afterburnerType == 8) || (this.afterburnerType == 10) || (this.afterburnerType == 11) || (this.afterburnerType == 6) || (this.afterburnerType == 5) || (this.afterburnerType == 9) || (this.afterburnerType == 4) || (this.afterburnerType == 12) || (this.afterburnerType == 13))
    {
      float f1 = this.compressorRPM[(this.nOfCompPoints - 1)];
      float f2 = this.compressorATA[(this.nOfCompPoints - 1)];
      this.nOfCompPoints -= 1;
      int i = 0;
      int j = 1;
      float f3 = 1.0F;
      float f4 = f1;
      if (this.nOfCompPoints < 2) {
        this.afterburnerCompressorFactor = 1.0F;
      } else {
        if (f4 < 0.1D) {
          f3 = Atmosphere.pressure((float)this.reference.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double) * this._1_P0;
        }
        else if (f4 >= this.compressorRPM[(this.nOfCompPoints - 1)]) {
          f3 = this.compressorATA[(this.nOfCompPoints - 1)];
        } else {
          if (f4 < this.compressorRPM[0]) {
            i = 0;
            j = 1;
          } else {
            for (int k = 0; k < this.nOfCompPoints - 1; )
            {
              if ((this.compressorRPM[k] <= f4) && (f4 < this.compressorRPM[(k + 1)]))
              {
                i = k;
                j = k + 1;
                break;
              }
              k++;
            }

          }

          float f5 = this.compressorRPM[j] - this.compressorRPM[i];
          if (f5 < 0.001F)
            f5 = 0.001F;
          f3 = this.compressorATA[i] + (f4 - this.compressorRPM[i]) * (this.compressorATA[j] - this.compressorATA[i]) / f5;
        }

        this.afterburnerCompressorFactor = (f2 / f3);
      }
    } else {
      this.afterburnerCompressorFactor = 1.0F;
    }
  }

  public float getATA(float paramFloat) {
    int i = 0;
    int j = 1;
    float f1 = 1.0F;
    if (this.nOfCompPoints < 2)
      return 1.0F;
    if (paramFloat < 0.1D) {
      f1 = Atmosphere.pressure((float)this.reference.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double) * this._1_P0;
    } else if (paramFloat >= this.compressorRPM[(this.nOfCompPoints - 1)]) {
      f1 = this.compressorATA[(this.nOfCompPoints - 1)];
    } else {
      if (paramFloat < this.compressorRPM[0]) {
        i = 0;
        j = 1;
      } else {
        for (int k = 0; k < this.nOfCompPoints - 1; k++) {
          if ((this.compressorRPM[k] > paramFloat) || (paramFloat >= this.compressorRPM[(k + 1)]))
            continue;
          i = k;
          j = k + 1;
          break;
        }
      }

      float f2 = this.compressorRPM[j] - this.compressorRPM[i];
      if (f2 < 0.001F)
        f2 = 0.001F;
      f1 = this.compressorATA[i] + (paramFloat - this.compressorRPM[i]) * (this.compressorATA[j] - this.compressorATA[i]) / f2;
    }

    return f1;
  }

  public void update(float paramFloat) {
    if ((!(this.reference instanceof RealFlightModel)) && (Time.tickCounter() > 200))
    {
      this.updateLast += paramFloat;
      if (this.updateLast >= this.updateStep) {
        paramFloat = this.updateStep;
      } else {
        this.engineForce.set(this.old_engineForce);
        this.engineTorque.set(this.old_engineTorque);
        return;
      }
    }
    this.producedDistabilisation = 0.0F;
    this.pressureExtBar = (Atmosphere.pressure(this.reference.getAltitude()) + this.compressorSpeedManifold * 0.5F * Atmosphere.density(this.reference.getAltitude()) * this.reference.getSpeed() * this.reference.getSpeed());

    this.pressureExtBar /= Atmosphere.P0();
    if ((this.controlThrottle > 1.0F) && (this.engineBoostFactor == 1.0F)) {
      this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setPowerControl(1.0F);
      if ((this.reference.isPlayers()) && ((this.reference instanceof RealFlightModel)) && (((RealFlightModel)this.reference).isRealMode()))
      {
        HUD.log(AircraftHotKeys.hudLogPowerId, "Power", new Object[] { new Integer(100) });
      }
    }
    computeForces(paramFloat);
    computeStage(paramFloat);
    if ((this.stage > 0) && (this.stage < 6))
      this.engineForce.set(0.0F, 0.0F, 0.0F);
    else if (this.stage == 8)
      this.rpm = (this.w = 0.0F);
    if (this.reference.isPlayers()) {
      if ((this.bIsMaster) && ((this.reference instanceof RealFlightModel)) && (((RealFlightModel)this.reference).isRealMode()))
      {
        computeTemperature(paramFloat);
      }if (World.cur().diffCur.Limited_Fuel)
        computeFuel(paramFloat);
    } else {
      computeFuel(paramFloat);
    }this.old_engineForce.set(this.engineForce);
    this.old_engineTorque.set(this.engineTorque);
    this.updateLast = 0.0F;
    float f = 0.5F / (Math.abs(this.aw) + 1.0F) - 0.1F;
    if (f < 0.025F)
      f = 0.025F;
    if (f > 0.4F)
      f = 0.4F;
    if (f < this.updateStep)
      this.updateStep = (0.9F * this.updateStep + 0.1F * f);
    else
      this.updateStep = (0.99F * this.updateStep + 0.01F * f);
  }

  public void netupdate(float paramFloat, boolean paramBoolean) {
    computeStage(paramFloat);
    if (Math.abs(this.w) < 1.E-005D)
      this.propPhiW = 1.570796F;
    else {
      this.propPhiW = (float)Math.atan(this.reference.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double / (this.w * this.propReductor * this.propr));
    }

    this.propAoA = (this.propPhi - this.propPhiW);
    computePropForces(this.w * this.propReductor, (float)this.reference.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double, this.propPhi, this.propAoA, this.reference.getAltitude());

    float f1 = this.w;
    float f2 = this.propPhi;
    float f3 = this.compressorManifoldPressure;
    computeForces(paramFloat);
    if (paramBoolean)
      this.compressorManifoldPressure = f3;
    this.w = f1;
    this.propPhi = f2;
    this.rpm = toRPM(this.w);
  }

  public void setReadyness(Actor paramActor, float paramFloat) {
    if (paramFloat > 1.0F)
      paramFloat = 1.0F;
    if (paramFloat < 0.0F)
      paramFloat = 0.0F;
    if (Actor.isAlive(paramActor)) {
      if (this.bIsMaster) {
        if ((this.readyness > 0.0F) && (paramFloat == 0.0F)) {
          this.readyness = 0.0F;
          setEngineDies(paramActor);
          return;
        }
        doSetReadyness(paramFloat);
      }
      if (Math.abs(this.oldReadyness - this.readyness) > 0.1F) {
        this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineReadyness(paramActor, this.number, (int)(paramFloat * 100.0F));

        this.oldReadyness = this.readyness;
      }
    }
  }

  private void setReadyness(float paramFloat) {
    setReadyness(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, paramFloat);
  }

  public void doSetReadyness(float paramFloat) {
    this.readyness = paramFloat;
  }

  public void setStage(Actor paramActor, int paramInt) {
    if (Actor.isAlive(paramActor)) {
      if (this.bIsMaster)
        doSetStage(paramInt);
      this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStage(paramActor, this.number, paramInt);
    }
  }

  public void doSetStage(int paramInt) {
    this.stage = paramInt;
  }

  public void setEngineStarts(Actor paramActor) {
    if ((this.bIsMaster) && (Actor.isAlive(paramActor)) && ((!isHasControlMagnetos()) || (getMagnetoMultiplier() >= 0.1F)))
    {
      this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStarts(this.number);
    }
  }

  public void doSetEngineStarts() {
    if ((Airport.distToNearestAirport(this.reference.jdField_Loc_of_type_ComMaddoxJGPPoint3d) < 1050.0D) && (this.reference.isStationedOnGround()))
    {
      this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setMagnetoControl(3);
      setControlMagneto(3);
      this.stage = 1;
      this.bRan = false;
      this.timer = Time.current();
    } else if (this.stage == 0) {
      this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setMagnetoControl(3);
      setControlMagneto(3);
      this.stage = 1;
      this.timer = Time.current();
    }
  }

  public void setEngineStops(Actor paramActor) {
    if ((Actor.isAlive(paramActor)) && (this.stage >= 1) && (this.stage <= 6))
      this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStops(this.number);
  }

  public void doSetEngineStops() {
    if (this.stage != 0) {
      this.stage = 0;
      setControlMagneto(0);
      this.timer = Time.current();
    }
  }

  public void setEngineDies(Actor paramActor) {
    if (this.stage <= 6)
      this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineDies(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, this.number);
  }

  public void doSetEngineDies() {
    if (this.stage < 7) {
      this.bIsInoperable = true;
      this.reference.setCapableOfTaxiing(false);
      this.reference.setCapableOfACM(false);
      doSetReadyness(0.0F);
      float f = 0.0F;
      int i = this.reference.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum();
      if (i != 0) {
        for (int j = 0; j < i; j++) {
          f += this.reference.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getReadyness() / i;
        }
        if (f < 0.7F)
          this.reference.setReadyToReturn(true);
        if (f < 0.3F)
          this.reference.setReadyToDie(true);
      }
      this.stage = 7;
      if (this.reference.isPlayers())
        HUD.log("FailedEngine");
      this.timer = Time.current();
    }
  }

  public void setEngineRunning(Actor paramActor) {
    if ((this.bIsMaster) && (Actor.isAlive(paramActor)))
      this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineRunning(this.number);
  }

  public void doSetEngineRunning() {
    if (this.stage < 6) {
      this.stage = 6;
      this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setMagnetoControl(3);
      setControlMagneto(3);
      if (this.reference.isPlayers())
        HUD.log("EngineI1");
      this.w = (this.wMax * 0.75F);
      this.tWaterOut = (0.5F * (this.tWaterCritMin + this.tWaterMaxRPM));
      this.tOilOut = (0.5F * (this.tOilCritMin + this.tOilOutMaxRPM));
      this.tOilIn = (0.5F * (this.tOilCritMin + this.tOilInMaxRPM));
      this.propPhi = (0.5F * (this.propPhiMin + this.propPhiMax));
      this.propTarget = this.propPhi;
      if (this.isnd != null)
        this.isnd.onEngineState(this.stage);
    }
  }

  public void setKillCompressor(Actor paramActor) {
    this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramActor, this.number, 0);
  }

  public void doSetKillCompressor() {
    switch (this.compressorType) {
    default:
      break;
    case 2:
      this.compressorAltitudes[0] = 50.0F;
      this.compressorAltMultipliers[0] = 1.0F;
      break;
    case 1:
      for (int i = 0; i < this.compressorMaxStep; i++) {
        this.compressorAltitudes[i] = 50.0F;
        this.compressorAltMultipliers[i] = 1.0F;
      }
    }
  }

  public void setKillPropAngleDevice(Actor paramActor) {
    this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramActor, this.number, 3);
  }

  public void doSetKillPropAngleDevice() {
    this.bIsAngleDeviceOperational = false;
  }

  public void setKillPropAngleDeviceSpeeds(Actor paramActor) {
    this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(paramActor, this.number, 4);
  }

  public void doSetKillPropAngleDeviceSpeeds() {
    this.isPropAngleDeviceHydroOperable = false;
  }

  public void setCyliderKnockOut(Actor paramActor, int paramInt) {
    this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineCylinderKnockOut(paramActor, this.number, paramInt);
  }

  public void doSetCyliderKnockOut(int paramInt) {
    this.cylindersOperable -= paramInt;
    if (this.cylindersOperable < 0)
      this.cylindersOperable = 0;
    if (this.bIsMaster)
      if (getCylindersRatio() < 0.12F)
        setEngineDies(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      else if (getCylindersRatio() < getReadyness())
        setReadyness(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, getCylindersRatio());
  }

  public void setMagnetoKnockOut(Actor paramActor, int paramInt)
  {
    this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineMagnetoKnockOut(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, this.number, paramInt);
  }

  public void doSetMagnetoKnockOut(int paramInt) {
    this.bMagnetos[paramInt] = false;
    if (paramInt == this.controlMagneto)
      setEngineStops(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
  }

  public void setEngineStuck(Actor paramActor) {
    this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStuck(paramActor, this.number);
  }

  public void doSetEngineStuck() {
    this.bIsInoperable = true;
    this.reference.setCapableOfTaxiing(false);
    this.reference.setCapableOfACM(false);
    if (this.stage != 8) {
      setReadyness(0.0F);
      if ((this.reference.isPlayers()) && (this.stage != 7))
        HUD.log("FailedEngine");
      this.stage = 8;
      this.timer = Time.current();
    }
  }

  public void setw(float paramFloat) {
    this.w = paramFloat;
    this.rpm = toRPM(this.w);
  }

  public void setPropPhi(float paramFloat) {
    this.propPhi = paramFloat;
  }

  public void setEngineMomentMax(float paramFloat) {
    this.engineMomentMax = paramFloat;
  }

  public void setPos(Point3d paramPoint3d) {
    this.enginePos.set(paramPoint3d);
  }

  public void setPropPos(Point3d paramPoint3d) {
    this.propPos.set(paramPoint3d);
  }

  public void setVector(Vector3f paramVector3f) {
    this.engineVector.set(paramVector3f);
    this.engineVector.normalize();
  }

  public void setControlThrottle(float paramFloat) {
    if (this.bHasThrottleControl) {
      if (this.afterburnerType == 4) {
        if ((paramFloat > 1.0F) && (this.controlThrottle <= 1.0F) && (this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.requestNitro(1.0E-004F)))
        {
          this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(true);
          setControlAfterburner(true);
          if (this.reference.isPlayers()) {
            Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(true);

            HUD.logRightBottom("BoostWepTP4");
          }
        }
        if ((paramFloat < 1.0F) && (this.controlThrottle >= 1.0F)) {
          this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(false);
          setControlAfterburner(false);
          if (this.reference.isPlayers()) {
            Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(false);

            HUD.logRightBottom(null);
          }
        }
      } else if (this.afterburnerType == 8) {
        if ((paramFloat > 1.0F) && (this.controlThrottle <= 1.0F)) {
          this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(true);
          setControlAfterburner(true);
          if (this.reference.isPlayers()) {
            Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(true);

            HUD.logRightBottom("BoostWepTP7");
          }
        }
        if ((paramFloat < 1.0F) && (this.controlThrottle >= 1.0F)) {
          this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(false);
          setControlAfterburner(false);
          if (this.reference.isPlayers()) {
            Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(false);

            HUD.logRightBottom(null);
          }
        }
      } else if (this.afterburnerType == 10) {
        if ((paramFloat > 1.0F) && (this.controlThrottle <= 1.0F)) {
          this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(true);
          setControlAfterburner(true);
          if (this.reference.isPlayers()) {
            Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(true);

            HUD.logRightBottom("BoostWepTP0");
          }
        }
        if ((paramFloat < 1.0F) && (this.controlThrottle >= 1.0F)) {
          this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(false);
          setControlAfterburner(false);
          if (this.reference.isPlayers()) {
            Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(false);

            HUD.logRightBottom(null);
          }
        }
      } else if (this.afterburnerType == 12) {
        if ((paramFloat > 1.0F) && (this.controlThrottle <= 1.0F)) {
          this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(true);
          setControlAfterburner(true);
          if (this.reference.isPlayers()) {
            Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(true);

            HUD.logRightBottom("BoostWepTP12");
          }
        }
        if ((paramFloat < 1.0F) && (this.controlThrottle >= 1.0F)) {
          this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(false);
          setControlAfterburner(false);
          if (this.reference.isPlayers()) {
            Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(false);

            HUD.logRightBottom(null);
          }
        }
      } else if (this.afterburnerType == 13) {
        if ((paramFloat > 1.0F) && (this.controlThrottle <= 1.0F)) {
          this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(true);
          setControlAfterburner(true);
          if (this.reference.isPlayers()) {
            Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(true);

            HUD.logRightBottom("BoostWepTP13");
          }
        }
        if ((paramFloat < 1.0F) && (this.controlThrottle >= 1.0F)) {
          this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(false);
          setControlAfterburner(false);
          if (this.reference.isPlayers()) {
            Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(false);

            HUD.logRightBottom(null);
          }
        }
      }
      this.controlThrottle = paramFloat;
    }
  }

  public void setControlAfterburner(boolean paramBoolean) {
    if (this.bHasAfterburnerControl) {
      if ((this.afterburnerType == 1) && (!this.controlAfterburner) && (paramBoolean) && (this.controlThrottle > 1.0F) && (World.Rnd().nextFloat() < 0.5F) && (this.reference.isPlayers()) && ((this.reference instanceof RealFlightModel)) && (((RealFlightModel)this.reference).isRealMode()) && (World.cur().diffCur.Vulnerability))
      {
        setCyliderKnockOut(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, World.Rnd().nextInt(0, 3));
      }this.controlAfterburner = paramBoolean;
    }
    if ((this.afterburnerType == 4) || (this.afterburnerType == 8) || (this.afterburnerType == 10) || (this.afterburnerType == 12) || (this.afterburnerType == 13))
    {
      this.controlAfterburner = paramBoolean;
    }
  }

  public void doSetKillControlThrottle() {
    this.bHasThrottleControl = false;
  }

  public void doSetKillControlAfterburner() {
    this.bHasAfterburnerControl = false;
  }

  public void setControlProp(float paramFloat) {
    if (this.bHasPropControl)
      this.controlProp = paramFloat;
  }

  public void setControlPropAuto(boolean paramBoolean) {
    if (this.bHasPropControl)
      this.bControlPropAuto = ((paramBoolean) && (isAllowsAutoProp()));
  }

  public void doSetKillControlProp() {
    this.bHasPropControl = false;
  }

  public void setControlMix(float paramFloat) {
    if (this.bHasMixControl)
      switch (this.mixerType) {
      case 0:
        this.controlMix = paramFloat;
        break;
      case 1:
        this.controlMix = paramFloat;
        if (this.controlMix >= 1.0F) break;
        this.controlMix = 1.0F; break;
      default:
        this.controlMix = paramFloat;
      }
  }

  public void doSetKillControlMix()
  {
    this.bHasMixControl = false;
  }

  public void setControlMagneto(int paramInt) {
    if (this.bHasMagnetoControl) {
      this.controlMagneto = paramInt;
      if (paramInt == 0)
        setEngineStops(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    }
  }

  public void setControlCompressor(int paramInt) {
    if (this.bHasCompressorControl)
      this.controlCompressor = paramInt;
  }

  public void setControlFeather(int paramInt) {
    if (this.bHasFeatherControl) {
      this.controlFeather = paramInt;
      if (this.reference.isPlayers())
        HUD.log("EngineFeather" + this.controlFeather);
    }
  }

  public void setControlRadiator(float paramFloat) {
    if (this.bHasRadiatorControl)
      this.controlRadiator = paramFloat;
  }

  public void setExtinguisherFire() {
    if ((this.bIsMaster) && 
      (this.bHasExtinguisherControl)) {
      this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineSpecificDamage(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, this.number, 5);

      if (this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[this.number] > 2) {
        this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineState(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, this.number, World.Rnd().nextInt(1, 2));
      }
      else if (this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[this.number] > 0)
        this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineState(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, this.number, 0);
    }
  }

  public void doSetExtinguisherFire()
  {
    if (this.bHasExtinguisherControl) {
      this.extinguishers -= 1;
      if (this.extinguishers == 0)
        this.bHasExtinguisherControl = false;
      this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.doSetEngineExtinguisherVisuals(this.number);
      if (this.bIsMaster) {
        if ((this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[this.number] > 1) && (World.Rnd().nextFloat() < 0.56F))
        {
          this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.repairEngine(this.number);
        }if ((this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates[this.number] > 3) && (World.Rnd().nextFloat() < 0.21F))
        {
          this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.repairEngine(this.number);
          this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.repairEngine(this.number);
        }
        this.tWaterOut -= 4.0F;
        this.tOilIn -= 4.0F;
        this.tOilOut -= 4.0F;
      }
      if (this.reference.isPlayers())
        HUD.log("ExtinguishersFired");
    }
  }

  private void computeStage(float paramFloat) {
    if (this.stage != 6) {
      bTFirst = false;
      float f = 20.0F;
      long l = Time.current() - this.timer;
      if ((this.stage > 0) && (this.stage < 6) && (l > this.given)) {
        this.stage += 1;
        this.timer = Time.current();
        l = 0L;
      }
      if (this.oldStage != this.stage) {
        bTFirst = true;
        this.oldStage = this.stage;
      }
      if ((this.stage > 0) && (this.stage < 6))
        setControlThrottle(0.2F);
      switch (this.stage) {
      case 0:
        if (bTFirst) {
          this.given = 4611686018427387903L;
          this.timer = Time.current();
        }
        if (this.isnd == null) break;
        this.isnd.onEngineState(this.stage); break;
      case 1:
        if (bTFirst) {
          if (this.bIsStuck) {
            this.stage = 8;
          }
          else if ((this.type == 3) || (this.type == 4) || (this.type == 6)) {
            this.stage = 5;
            if (!this.reference.isPlayers()) break;
            HUD.log("Starting_Engine");
          }
          else {
            if ((this.type == 0) || (this.type == 1) || (this.type == 7)) {
              if (this.w > this.wMin) {
                this.stage = 3;
                if (!this.reference.isPlayers()) break;
                HUD.log("Starting_Engine"); break;
              }

              if (!this.bIsAutonomous) {
                if ((Airport.distToNearestAirport(this.reference.jdField_Loc_of_type_ComMaddoxJGPPoint3d) < 1050.0D) && (this.reference.isStationedOnGround()))
                {
                  setControlMagneto(3);
                  if (this.reference.isPlayers())
                    HUD.log("Starting_Engine");
                } else {
                  doSetEngineStops();
                  if (!this.reference.isPlayers()) break;
                  HUD.log("EngineI0"); break;
                }
              }
              else if (this.reference.isPlayers())
                HUD.log("Starting_Engine");
            } else if (!this.bIsAutonomous) {
              if ((Airport.distToNearestAirport(this.reference.jdField_Loc_of_type_ComMaddoxJGPPoint3d) < 1050.0D) && (this.reference.isStationedOnGround()))
              {
                setControlMagneto(3);
                if (this.reference.isPlayers())
                  HUD.log("Starting_Engine");
              } else {
                if (this.reference.getSpeedKMH() < 350.0F) {
                  doSetEngineStops();
                  if (!this.reference.isPlayers()) break;
                  HUD.log("EngineI0"); break;
                }

                if (this.reference.isPlayers())
                  HUD.log("Starting_Engine");
              }
            } else if (this.reference.isPlayers()) {
              HUD.log("Starting_Engine");
            }this.given = ()(500.0F * World.Rnd().nextFloat(1.0F, 2.0F));
          }
        } else {
          if (this.isnd != null)
            this.isnd.onEngineState(this.stage);
          this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setMagnetoControl(3);
          setControlMagneto(3);
          this.w = (0.1047F * (20.0F * (float)l / (float)this.given));
          setControlThrottle(0.0F);
        }break;
      case 2:
        if (bTFirst) {
          this.given = ()(4000.0F * World.Rnd().nextFloat(1.0F, 2.0F));

          if (this.bRan) {
            this.given = ()(100.0F + (this.tOilOutMaxRPM - this.tOilOut) / (this.tOilOutMaxRPM - f) * 7900.0F * World.Rnd().nextFloat(2.0F, 4.2F));

            if (this.given > 9000L)
              this.given = World.Rnd().nextLong(7800L, 9600L);
            if ((this.bIsMaster) && (World.Rnd().nextFloat() < 0.5F)) {
              this.stage = 0;
              this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStops(this.number);
            }
          }
        }
        this.w = (0.1047F * (20.0F + 15.0F * (float)l / (float)this.given));
        setControlThrottle(0.0F);
        if (this.isnd == null) break;
        this.isnd.onEngineState(this.stage); break;
      case 3:
        if (bTFirst) {
          if (this.isnd != null)
            this.isnd.onEngineState(this.stage);
          if (this.bIsInoperable) {
            this.stage = 0;
            doSetEngineDies();
            break;
          }
          this.given = ()(50.0F * World.Rnd().nextFloat(1.0F, 2.0F));
          if ((this.bIsMaster) && (World.Rnd().nextFloat() < 0.12F) && ((this.tOilOutMaxRPM - this.tOilOut) / (this.tOilOutMaxRPM - f) < 0.75F))
          {
            this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStops(this.number);
          }
        }
        this.w = (0.1047F * (60.0F + 60.0F * (float)l / (float)this.given));
        setControlThrottle(0.0F);
        if ((this.reference == null) || (this.type == 2) || (this.type == 3) || (this.type == 4) || (this.type == 6) || (this.type == 5))
          break;
        for (int i = 1; i < 32; i++)
          try {
            Hook localHook = this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor.findHook("_Engine" + (this.number + 1) + "EF_" + (i >= 10 ? "" + i : new StringBuffer().append("0").append(i).toString()));

            if (localHook != null)
              Eff3DActor.New(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, localHook, null, 1.0F, "3DO/Effects/Aircraft/EngineStart" + World.Rnd().nextInt(1, 3) + ".eff", -1.0F);
          }
          catch (Exception localException)
          {
          }
        break;
      case 4:
        if (bTFirst) {
          this.given = ()(500.0F * World.Rnd().nextFloat(1.0F, 2.0F));
        }
        this.w = 12.564F;
        setControlThrottle(0.0F);
        if (this.isnd == null) break;
        this.isnd.onEngineState(this.stage); break;
      case 5:
        if (bTFirst) {
          this.given = ()(500.0F * World.Rnd().nextFloat(1.0F, 2.0F));

          if ((this.bRan) && ((this.type == 0) || (this.type == 1) || (this.type == 7))) {
            if ((this.tOilOutMaxRPM - this.tOilOut) / (this.tOilOutMaxRPM - f) > 0.75F)
            {
              if ((this.type == 0) || (this.type == 7)) {
                if ((this.bIsMaster) && (getReadyness() > 0.75F) && (World.Rnd().nextFloat() < 0.25F))
                {
                  setReadyness(getReadyness() - 0.05F);
                }
              } else if ((this.type == 1) && (this.bIsMaster) && (World.Rnd().nextFloat() < 0.1F))
              {
                this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineDies(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, this.number);
              }
            }
            if ((this.bIsMaster) && (World.Rnd().nextFloat() < 0.1F))
              this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineStops(this.number);
          }
          this.bRan = true;
        }
        this.w = (0.1047F * (120.0F + 120.0F * (float)l / (float)this.given));
        setControlThrottle(0.2F);
        if (this.isnd == null) break;
        this.isnd.onEngineState(this.stage); break;
      case 6:
        if (bTFirst) {
          this.given = -1L;
          this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setEngineRunning(this.number);
        }
        if (this.isnd == null) break;
        this.isnd.onEngineState(this.stage); break;
      case 7:
      case 8:
        if (bTFirst)
          this.given = -1L;
        setReadyness(0.0F);
        setControlMagneto(0);
        if (this.isnd == null) break;
        this.isnd.onEngineState(this.stage);
      }
    }
  }

  private void computeFuel(float paramFloat)
  {
    tmpF = 0.0F;
    float f1 = this.w * this._1_wMax;
    if (this.stage == 6)
    {
      double d1;
      float f2;
      switch (this.type) {
      case 0:
      case 1:
      case 7:
        d1 = this.momForFuel * this.w * 0.00105D;
        f2 = (float)d1 / this.horsePowers;
        if (d1 >= this.horsePowers * 0.05D) break;
        d1 = this.horsePowers * 0.05D; break;
      default:
        d1 = this.thrustMax * (f2 = getPowerOutput());
        if (d1 >= this.thrustMax * 0.05D) break;
        d1 = this.thrustMax * 0.05D;
      }
      if (f2 < 0.0F)
        f2 = 0.0F;
      double d2;
      float f3;
      if (f2 <= 0.5F) {
        d2 = this.FuelConsumptionP0 + (this.FuelConsumptionP05 - this.FuelConsumptionP0) * (2.0D * f2);
      }
      else if (f2 <= 1.0D) {
        d2 = this.FuelConsumptionP05 + (this.FuelConsumptionP1 - this.FuelConsumptionP05) * (2.0D * (f2 - 0.5D));
      }
      else
      {
        f3 = f2 - 1.0F;
        if (f3 > 0.1F)
          f3 = 0.1F;
        f3 *= 10.0F;
        d2 = this.FuelConsumptionP1 + (this.FuelConsumptionPMAX - this.FuelConsumptionP1) * f3;
      }

      d2 /= 3600.0D;
      switch (this.type) { case 5:
      default:
        break;
      case 0:
      case 1:
      case 7:
        f3 = (float)(d2 * d1);
        tmpF = f3 * paramFloat;
        double d3 = f3 * 44000000.0F;
        double d4 = f3 * 15.7F;
        double d5 = 1010.0D * d4 * 700.0D;
        d1 *= 746.0D;
        this.Ptermo = (float)(d3 - d1 - d5);
        break;
      case 2:
        tmpF = (float)(d2 * d1 * paramFloat);
        break;
      case 3:
        if (((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof BI_1)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof BI_6)))
        {
          tmpF = 1.8F * getPowerOutput() * paramFloat;
        } else if ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof MXY_7))
          tmpF = 0.5F * getPowerOutput() * paramFloat;
        else
          tmpF = 2.5777F * getPowerOutput() * paramFloat;
        break;
      case 4:
        tmpF = 1.432056F * getPowerOutput() * paramFloat;
        tmpB = this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.requestNitro(tmpF);
        tmpF = 0.0F;
        if ((tmpB) || (!this.bIsMaster)) break;
        setEngineStops(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        if ((this.reference.isPlayers()) && (this.engineNoFuelHUDLogId == -1)) {
          this.engineNoFuelHUDLogId = HUD.makeIdLog();
          HUD.log(this.engineNoFuelHUDLogId, "EngineNoFuel");
        }

        return;
      case 6:
        tmpF = (float)(d2 * d1 * paramFloat);
        tmpB = this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.requestNitro(tmpF);
        tmpF = 0.0F;
        if ((tmpB) || (!this.bIsMaster)) break;
        setEngineStops(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        if ((this.reference.isPlayers()) && (this.engineNoFuelHUDLogId == -1)) {
          this.engineNoFuelHUDLogId = HUD.makeIdLog();
          HUD.log(this.engineNoFuelHUDLogId, "EngineNoFuel");
        }
        return;
      }
    }

    tmpB = this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.requestFuel(tmpF);
    if ((!tmpB) && (this.bIsMaster)) {
      setEngineStops(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      this.reference.setCapableOfACM(false);
      this.reference.setCapableOfTaxiing(false);
      if ((this.reference.isPlayers()) && (this.engineNoFuelHUDLogId == -1)) {
        this.engineNoFuelHUDLogId = HUD.makeIdLog();
        HUD.log(this.engineNoFuelHUDLogId, "EngineNoFuel");
      }
    }
    if (this.controlAfterburner)
      switch (this.afterburnerType) { case 3:
      case 6:
      case 7:
      case 8:
      default:
        break;
      case 1:
        if ((this.controlThrottle <= 1.0F) || (this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.requestNitro(0.044872F * paramFloat)) || (!this.reference.isPlayers()) || (!(this.reference instanceof RealFlightModel)) || (!((RealFlightModel)this.reference).isRealMode()) || (!World.cur().diffCur.Vulnerability))
        {
          break;
        }

        setReadyness(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, getReadyness() - 0.01F * paramFloat); break;
      case 2:
      case 5:
        if ((!this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.requestNitro(0.044872F * paramFloat)) || (goto 1063) || 
          (!this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.requestNitro(0.044872F * paramFloat))) break; break;
      case 9:
      case 4:
        if ((!this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.requestNitro(0.044872F * paramFloat)) || (goto 1063) || 
          (this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.requestNitro(0.044872F * paramFloat))) break;
        this.reference.jdField_CT_of_type_ComMaddoxIl2FmControls.setAfterburnerControl(false);
        if (!this.reference.isPlayers()) break;
        Main3D.cur3D().aircraftHotKeys.setAfterburnerForAutoActivation(false);

        HUD.logRightBottom(null);
      }
  }

  private void computeTemperature(float paramFloat)
  {
    float f1 = Pitot.Indicator((float)this.reference.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double, this.reference.getSpeedKMH());

    float f2 = Atmosphere.temperature((float)this.reference.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double) - 273.14999F;

    if (this.stage == 6) {
      f3 = 1.05F * (float)Math.sqrt(Math.sqrt(getPowerOutput() + this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateOilStates[this.number] * 0.33F)) * (float)Math.sqrt(this.w / this.wMax) * this.tOilOutMaxRPM * (1.0F - 0.11F * this.controlRadiator) * (1.0F - f1 * 0.0002F) + 22.0F;

      if (getPowerOutput() > 1.0F)
        f3 *= getPowerOutput();
      this.tOilOut += (f3 - this.tOilOut) * paramFloat * this.tChangeSpeed;
    } else {
      f3 = this.w / this.wMax * this.tOilOutMaxRPM * (1.0F - 0.2F * this.controlRadiator) + f2;

      Motor localMotor1 = this;
      localMotor1.tOilOut += (f3 - this.tOilOut) * paramFloat * this.tChangeSpeed * (this.type != 0 ? 1.07F : 0.42F);
    }

    float f3 = 0.8F - 0.05F * this.controlRadiator;
    float f4 = this.tOilOut * (f3 - f1 * 0.0005F) + f2 * (1.0F - f3 + f1 * 0.0005F);

    this.tOilIn += (f4 - this.tOilIn) * paramFloat * this.tChangeSpeed * 0.5F;
    f4 = 1.05F * (float)Math.sqrt(getPowerOutput()) * (1.0F - f1 * 0.0002F) * this.tWaterMaxRPM * (this.controlAfterburner ? 1.1F : 1.0F) + f2;

    Motor localMotor2 = this;
    localMotor2.tWaterOut += (f4 - this.tWaterOut) * paramFloat * this.tChangeSpeed * (this.tWaterOut >= 50.0F ? 1.0F : 0.4F) * (1.0F - f1 * 0.0006F);

    if (this.tOilOut < f2)
      this.tOilOut = f2;
    if (this.tOilIn < f2)
      this.tOilIn = f2;
    if (this.tWaterOut < f2)
      this.tWaterOut = f2;
    if ((World.cur().diffCur.Engine_Overheat) && ((this.tWaterOut > this.tWaterCritMax) || (this.tOilOut > this.tOilCritMax)))
    {
      if (heatStringID == -1)
        heatStringID = HUD.makeIdLog();
      if (this.reference.isPlayers())
        HUD.log(heatStringID, "EngineOverheat");
      this.timeCounter += paramFloat;
      if (this.timeCounter > this.timeOverheat)
        if (this.readyness > 0.32F) {
          setReadyness(this.readyness - 0.00666F * paramFloat);
          this.tOilCritMax -= 0.00666F * paramFloat * (this.tOilCritMax - this.tOilOutMaxRPM);
        }
        else {
          setEngineDies(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        }
    } else if (this.timeCounter > 0.0F) {
      this.timeCounter = 0.0F;
      if (heatStringID == -1)
        heatStringID = HUD.makeIdLog();
      if (this.reference.isPlayers())
        HUD.log(heatStringID, "EngineRestored");
    }
  }

  public void updateRadiator(float paramFloat) {
    if ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof GLADIATOR)) {
      this.controlRadiator = 0.0F;
    } else if (((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof P_51)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof P_51Mustang)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof P_38)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof YAK_3)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof YAK_3P)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof YAK_9M)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof YAK_9U)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof YAK_9UT)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof P_63C)))
    {
      if (this.tOilOut > this.tOilOutMaxRPM) {
        this.controlRadiator += 0.1F * paramFloat;
        if (this.controlRadiator > 1.0F)
          this.controlRadiator = 1.0F;
      } else {
        this.controlRadiator = (1.0F - this.reference.getSpeed() / this.reference.jdField_VmaxH_of_type_Float);

        if (this.controlRadiator < 0.0F)
          this.controlRadiator = 0.0F;
      }
    } else if (((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE9)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRELF14E)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE14C)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE8)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE8FB)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE12)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIREXIIclpearly)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE8CLPFB)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE8CLP)))
    {
      float f1 = 0.0F;
      if (this.tOilOut > this.tOilCritMin) {
        f2 = this.tOilCritMax - this.tOilCritMin;
        f1 = 1.4F * (this.tOilOut - this.tOilCritMin) / f2;
        if (f1 > 1.4F)
          f1 = 1.4F;
      }
      float f2 = 0.0F;
      if (this.tWaterOut > this.tWaterCritMin) {
        f3 = this.tWaterCritMax - this.tWaterCritMin;
        f2 = 1.4F * (this.tWaterOut - this.tWaterCritMin) / f3;
        if (f2 > 1.4F)
          f2 = 1.4F;
      }
      float f3 = Math.max(f1, f2);
      float f4 = 1.0F;
      float f5 = this.reference.getSpeed();
      if (f5 > this.reference.jdField_Vmin_of_type_Float * 1.5F) {
        float f6 = this.reference.jdField_Vmax_of_type_Float - this.reference.jdField_Vmin_of_type_Float * 1.5F;
        f4 = 1.0F - 1.65F * (f5 - this.reference.jdField_Vmin_of_type_Float * 1.5F) / f6;
        if (f4 < -1.0F)
          f4 = -1.0F;
      }
      this.controlRadiator = (0.5F * (f3 + f4));
      if ((this.tWaterOut > this.tWaterCritMax) || (this.tOilOut > this.tOilCritMax))
        this.controlRadiator += 0.05F * this.timeCounter;
      if (this.controlRadiator < 0.0F)
        this.controlRadiator = 0.0F;
      if (this.controlRadiator > 1.0F)
        this.controlRadiator = 1.0F;
    } else if ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof GLADIATOR)) {
      this.controlRadiator = 0.0F;
    } else {
      switch (this.propAngleDeviceType) { case 3:
      case 4:
      default:
        this.controlRadiator = (1.0F - getPowerOutput());
        break;
      case 5:
      case 6:
        this.controlRadiator = (1.0F - this.reference.getSpeed() / this.reference.jdField_VmaxH_of_type_Float);

        if (this.controlRadiator >= 0.0F) break;
        this.controlRadiator = 0.0F; break;
      case 1:
      case 2:
        if (this.controlRadiator > 1.0F - getPowerOutput()) {
          this.controlRadiator -= 0.15F * paramFloat;
          if (this.controlRadiator >= 0.0F) break;
          this.controlRadiator = 0.0F;
        } else {
          this.controlRadiator += 0.15F * paramFloat;
        }break;
      case 8:
        if (this.type == 0) {
          if (this.tOilOut > this.tOilOutMaxRPM) {
            this.controlRadiator += 0.1F * paramFloat;
            if (this.controlRadiator <= 1.0F) break;
            this.controlRadiator = 1.0F; } else {
            if (this.tOilOut >= this.tOilOutMaxRPM - 10.0F) break;
            this.controlRadiator -= 0.1F * paramFloat;
            if (this.controlRadiator >= 0.0F) break;
            this.controlRadiator = 0.0F;
          }
        } else if (this.controlRadiator > 1.0F - getPowerOutput()) {
          this.controlRadiator -= 0.15F * paramFloat;
          if (this.controlRadiator >= 0.0F) break;
          this.controlRadiator = 0.0F;
        } else {
          this.controlRadiator += 0.15F * paramFloat;
        }break;
      case 7:
        if (this.tOilOut > this.tOilOutMaxRPM) {
          this.controlRadiator += 0.1F * paramFloat;
          if (this.controlRadiator <= 1.0F) break;
          this.controlRadiator = 1.0F;
        } else {
          this.controlRadiator = (1.0F - this.reference.getSpeed() / this.reference.jdField_VmaxH_of_type_Float);

          if (this.controlRadiator >= 0.0F) break;
          this.controlRadiator = 0.0F;
        }
      }
    }
  }

  private void computeForces(float paramFloat)
  {
    float f1;
    float f2;
    float f5;
    float f8;
    switch (this.type) {
    case 0:
    case 1:
    case 7:
      if (Math.abs(this.w) < 1.0E-005F)
        this.propPhiW = 1.570796F;
      else if (this.type == 7) {
        this.propPhiW = (float)Math.atan(Math.abs(this.reference.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double) / (this.w * this.propReductor * this.propr));
      }
      else
      {
        this.propPhiW = (float)Math.atan(this.reference.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double / (this.w * this.propReductor * this.propr));
      }

      this.propAoA = (this.propPhi - this.propPhiW);
      if (this.type == 7) {
        computePropForces(this.w * this.propReductor, (float)Math.abs(this.reference.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double), this.propPhi, this.propAoA, this.reference.getAltitude());
      }
      else
      {
        computePropForces(this.w * this.propReductor, (float)this.reference.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double, this.propPhi, this.propAoA, this.reference.getAltitude());
      }
      float f3;
      switch (this.propAngleDeviceType) {
      case 3:
      case 4:
        f1 = this.controlThrottle;
        if (f1 > 1.0F)
          f1 = 1.0F;
        this.compressorManifoldThreshold = (0.5F + (this.compressorRPMtoWMaxATA - 0.5F) * f1);

        if (isPropAngleDeviceOperational()) {
          if (this.bControlPropAuto)
            this.propTarget = (this.propPhiW + this.propAoA0);
          else
            this.propTarget = (this.propPhiMax - this.controlProp * (this.propPhiMax - this.propPhiMin));
        }
        else if (this.propAngleDeviceType == 3)
          this.propTarget = 0.0F;
        else
          this.propTarget = 3.141593F;
        break;
      case 1:
      case 2:
        if (this.bControlPropAuto) {
          if (this.engineBoostFactor > 1.0F)
            this.controlProp = (0.75F + 0.227272F * this.controlThrottle);
          else
            this.controlProp = (0.75F + 0.25F * this.controlThrottle);
        }
        f1 = this.propAngleDeviceMaxParam;
        if (this.controlAfterburner)
          f1 = this.propAngleDeviceAfterburnerParam;
        f2 = this.propAngleDeviceMinParam + (f1 - this.propAngleDeviceMinParam) * this.controlProp;

        f3 = this.controlThrottle;
        if (f3 > 1.0F)
          f3 = 1.0F;
        this.compressorManifoldThreshold = getATA(toRPM(this.propAngleDeviceMinParam + (this.propAngleDeviceMaxParam - this.propAngleDeviceMinParam) * f3));

        if (isPropAngleDeviceOperational()) {
          if (this.w < f2) {
            f2 = Math.min(1.0F, 0.01F * (f2 - this.w) - 0.012F * this.aw);

            this.propTarget -= f2 * getPropAngleDeviceSpeed() * paramFloat;
          } else {
            f2 = Math.min(1.0F, 0.01F * (this.w - f2) + 0.012F * this.aw);

            this.propTarget += f2 * getPropAngleDeviceSpeed() * paramFloat;
          }
          if ((this.stage != 6) || (this.propTarget >= this.propPhiW - 0.12F)) break;
          this.propTarget = (this.propPhiW - 0.12F);
          if (this.propPhi >= this.propTarget) break;
          this.propPhi += 0.2F * paramFloat;
        }
        else if (this.propAngleDeviceType == 1) {
          this.propTarget = 0.0F;
        } else {
          this.propTarget = 1.5708F;
        }break;
      case 7:
        f1 = this.controlThrottle;
        if (this.engineBoostFactor > 1.0F)
          f1 = 0.9090909F * this.controlThrottle;
        f2 = this.propAngleDeviceMaxParam;
        if (this.controlAfterburner)
          if ((this.afterburnerType == 1) || (this.afterburnerType == 12) || (this.afterburnerType == 13))
          {
            if (this.controlThrottle > 1.0F) {
              f2 = this.propAngleDeviceMaxParam + 10.0F * (this.controlThrottle - 1.0F) * (this.propAngleDeviceAfterburnerParam - this.propAngleDeviceMaxParam);
            }
          }
          else
          {
            f2 = this.propAngleDeviceAfterburnerParam;
          }
        f3 = this.propAngleDeviceMinParam + (f2 - this.propAngleDeviceMinParam) * f1;

        f5 = this.controlThrottle;
        if (f5 > 1.0F)
          f5 = 1.0F;
        this.compressorManifoldThreshold = getATA(toRPM(this.propAngleDeviceMinParam + (f2 - this.propAngleDeviceMinParam) * f5));

        if (!isPropAngleDeviceOperational()) break;
        if (this.bControlPropAuto) {
          if (this.w < f3) {
            f3 = Math.min(1.0F, 0.01F * (f3 - this.w) - 0.012F * this.aw);

            this.propTarget -= f3 * getPropAngleDeviceSpeed() * paramFloat;
          }
          else {
            f3 = Math.min(1.0F, 0.01F * (this.w - f3) + 0.012F * this.aw);

            this.propTarget += f3 * getPropAngleDeviceSpeed() * paramFloat;
          }

          if ((this.stage == 6) && (this.propTarget < this.propPhiW - 0.12F)) {
            this.propTarget = (this.propPhiW - 0.12F);
            if (this.propPhi < this.propTarget)
              this.propPhi += 0.2F * paramFloat;
          }
          if (this.propTarget >= this.propPhiMin + (float)Math.toRadians(3.0D))
            break;
          this.propTarget = (this.propPhiMin + (float)Math.toRadians(3.0D));
        }
        else {
          this.propTarget = ((1.0F - paramFloat * 0.1F) * this.propTarget + paramFloat * 0.1F * (this.propPhiMax - this.controlProp * (this.propPhiMax - this.propPhiMin)));

          if (this.w > 1.02F * this.wMax) {
            this.wMaxAllowed = ((1.0F - 4.0E-007F * (this.w - 1.02F * this.wMax)) * this.wMaxAllowed);
          }

          if (this.w <= this.wMax) break;
          float f6 = this.w - this.wMax;
          f6 *= f6;
          f8 = 1.0F - 0.001F * f6;
          if (f8 < 0.0F)
            f8 = 0.0F;
          this.propForce *= f8; } break;
      case 8:
        f1 = this.controlThrottle;
        if (this.engineBoostFactor > 1.0F)
          f1 = 0.9090909F * this.controlThrottle;
        f2 = this.propAngleDeviceMaxParam;
        if (this.controlAfterburner)
          if ((this.afterburnerType == 1) || (this.afterburnerType == 12) || (this.afterburnerType == 13))
          {
            if (this.controlThrottle > 1.0F) {
              f2 = this.propAngleDeviceMaxParam + 10.0F * (this.controlThrottle - 1.0F) * (this.propAngleDeviceAfterburnerParam - this.propAngleDeviceMaxParam);
            }
          }
          else
          {
            f2 = this.propAngleDeviceAfterburnerParam;
          }
        f3 = this.propAngleDeviceMinParam + (f2 - this.propAngleDeviceMinParam) * f1 + (this.bControlPropAuto ? 0.0F : -25.0F + 50.0F * this.controlProp);

        f5 = this.controlThrottle;
        if (f5 > 1.0F)
          f5 = 1.0F;
        this.compressorManifoldThreshold = getATA(toRPM(this.propAngleDeviceMinParam + (f2 - this.propAngleDeviceMinParam) * f5));

        if (!isPropAngleDeviceOperational()) break;
        if (this.w < f3) {
          f3 = Math.min(1.0F, 0.01F * (f3 - this.w) - 0.012F * this.aw);

          this.propTarget -= f3 * getPropAngleDeviceSpeed() * paramFloat;
        } else {
          f3 = Math.min(1.0F, 0.01F * (this.w - f3) + 0.012F * this.aw);

          this.propTarget += f3 * getPropAngleDeviceSpeed() * paramFloat;
        }
        if ((this.stage == 6) && (this.propTarget < this.propPhiW - 0.12F)) {
          this.propTarget = (this.propPhiW - 0.12F);
          if (this.propPhi < this.propTarget)
            this.propPhi += 0.2F * paramFloat;
        }
        if (this.propTarget >= this.propPhiMin + (float)Math.toRadians(3.0D)) break;
        this.propTarget = (this.propPhiMin + (float)Math.toRadians(3.0D)); break;
      case 6:
        f1 = this.controlThrottle;
        if (f1 > 1.0F)
          f1 = 1.0F;
        this.compressorManifoldThreshold = (0.5F + (this.compressorRPMtoWMaxATA - 0.5F) * f1);

        if (!isPropAngleDeviceOperational()) break;
        if (this.bControlPropAuto) {
          f2 = 25.0F + (this.wMax - 25.0F) * (0.25F + 0.75F * this.controlThrottle);

          if (this.w < f2) {
            f2 = Math.min(1.0F, 0.01F * (f2 - this.w) - 0.012F * this.aw);

            this.propTarget -= f2 * getPropAngleDeviceSpeed() * paramFloat;
          }
          else {
            f2 = Math.min(1.0F, 0.01F * (this.w - f2) + 0.012F * this.aw);

            this.propTarget += f2 * getPropAngleDeviceSpeed() * paramFloat;
          }

          if ((this.stage == 6) && (this.propTarget < this.propPhiW - 0.12F)) {
            this.propTarget = (this.propPhiW - 0.12F);
            if (this.propPhi < this.propTarget)
              this.propPhi += 0.2F * paramFloat;
          }
          this.controlProp = ((this.propAngleDeviceMaxParam - this.propTarget) / (this.propAngleDeviceMaxParam - this.propAngleDeviceMinParam));

          if (this.controlProp < 0.0F)
            this.controlProp = 0.0F;
          if (this.controlProp <= 1.0F) break;
          this.controlProp = 1.0F;
        } else {
          this.propTarget = (this.propAngleDeviceMaxParam - this.controlProp * (this.propAngleDeviceMaxParam - this.propAngleDeviceMinParam)); } break;
      case 5:
        f1 = this.controlThrottle;
        if (f1 > 1.0F)
          f1 = 1.0F;
        this.compressorManifoldThreshold = (0.5F + (this.compressorRPMtoWMaxATA - 0.5F) * f1);

        if (this.bControlPropAuto) {
          if ((this.reference.isPlayers()) && ((this.reference instanceof RealFlightModel)) && (((RealFlightModel)this.reference).isRealMode()))
          {
            if (World.cur().diffCur.ComplexEManagement)
              this.controlProp = (-this.controlThrottle);
            else {
              this.controlProp = (-Aircraft.cvt(this.reference.getSpeed(), this.reference.jdField_Vmin_of_type_Float, this.reference.jdField_Vmax_of_type_Float, 0.0F, 1.0F));
            }
          }
          else
          {
            this.controlProp = (-Aircraft.cvt(this.reference.getSpeed(), this.reference.jdField_Vmin_of_type_Float, this.reference.jdField_Vmax_of_type_Float, 0.0F, 1.0F));
          }

        }

        this.propTarget = (this.propAngleDeviceMaxParam - this.controlProp * (this.propAngleDeviceMaxParam - this.propAngleDeviceMinParam));

        this.propPhi = this.propTarget;
      }

      if ((this.controlFeather == 1) && (this.bHasFeatherControl) && (isPropAngleDeviceOperational()))
      {
        this.propTarget = 1.55F;
      }if (this.propPhi > this.propTarget) {
        f1 = Math.min(1.0F, 157.29581F * (this.propPhi - this.propTarget));

        this.propPhi -= f1 * getPropAngleDeviceSpeed() * paramFloat;
      } else if (this.propPhi < this.propTarget) {
        f1 = Math.min(1.0F, 157.29581F * (this.propTarget - this.propPhi));

        this.propPhi += f1 * getPropAngleDeviceSpeed() * paramFloat;
      }
      if (this.propTarget > this.propPhiMax)
        this.propTarget = this.propPhiMax;
      else if (this.propTarget < this.propPhiMin)
        this.propTarget = this.propPhiMin;
      if ((this.propPhi > this.propPhiMax) && (this.controlFeather == 0))
        this.propPhi = this.propPhiMax;
      else if (this.propPhi < this.propPhiMin)
        this.propPhi = this.propPhiMin;
      this.engineMoment = getN();
      f1 = getCompressorMultiplier(paramFloat);
      this.engineMoment *= f1;
      this.momForFuel = this.engineMoment;
      this.engineMoment *= getReadyness();
      this.engineMoment *= getMagnetoMultiplier();
      this.engineMoment *= getMixMultiplier();
      this.engineMoment *= getStageMultiplier();
      this.engineMoment *= getDistabilisationMultiplier();
      this.engineMoment += getFrictionMoment(paramFloat);
      f2 = this.engineMoment - this.propMoment;
      this.aw = (f2 / (this.propI + this.engineI));
      if (this.aw > 0.0F)
        this.aw *= this.engineAcceleration;
      this.oldW = this.w;
      this.w += this.aw * paramFloat;
      if (this.w < 0.0F)
        this.w = 0.0F;
      if (this.w > this.wMaxAllowed + this.wMaxAllowed)
        this.w = (this.wMaxAllowed + this.wMaxAllowed);
      if (this.oldW == 0.0F) {
        if (this.w < 10.0F * this.fricCoeffT)
          this.w = 0.0F;
      } else if (this.w < 2.0F * this.fricCoeffT)
        this.w = 0.0F;
      if ((this.reference.isPlayers()) && (World.cur().diffCur.Torque_N_Gyro_Effects) && ((this.reference instanceof RealFlightModel)) && (((RealFlightModel)this.reference).isRealMode()))
      {
        this.propIW.set(this.propI * this.w * this.propReductor, 0.0D, 0.0D);
        if (this.propDirection == 1)
          this.propIW.jdField_x_of_type_Double = (-this.propIW.jdField_x_of_type_Double);
        this.engineTorque.set(0.0F, 0.0F, 0.0F);
        f3 = this.propI * this.aw * this.propReductor;
        if (this.propDirection == 0) {
          this.engineTorque.jdField_x_of_type_Float += this.propMoment;
          this.engineTorque.jdField_x_of_type_Float += f3;
        } else {
          this.engineTorque.jdField_x_of_type_Float -= this.propMoment;
          this.engineTorque.jdField_x_of_type_Float -= f3;
        }
      } else {
        this.engineTorque.set(0.0F, 0.0F, 0.0F);
      }this.engineForce.set(this.engineVector);
      this.engineForce.scale(this.propForce);
      tmpV3f.cross(this.propPos, this.engineForce);
      this.engineTorque.add(tmpV3f);
      this.rearRush = 0.0F;
      this.rpm = toRPM(this.w);
      double d1 = this.reference.jdField_Vflow_of_type_ComMaddoxJGPVector3d.jdField_x_of_type_Double + this.addVflow;
      if (d1 < 1.0D)
        d1 = 1.0D;
      double d2 = 1.0D / (Atmosphere.density(this.reference.getAltitude()) * 6.0F * d1);

      this.addVflow = (0.95D * this.addVflow + 0.05D * this.propForce * d2);
      this.addVside = (0.95D * this.addVside + 0.05D * (this.propMoment / this.propr) * d2);

      if (this.addVside >= 0.0D) break;
      this.addVside = 0.0D; break;
    case 2:
      f1 = this.pressureExtBar;
      this.engineMoment = (this.propAngleDeviceMinParam + getControlThrottle() * (this.propAngleDeviceMaxParam - this.propAngleDeviceMinParam));

      this.engineMoment /= this.propAngleDeviceMaxParam;
      this.engineMoment *= this.engineMomentMax;
      this.engineMoment *= getReadyness();
      this.engineMoment *= getDistabilisationMultiplier();
      this.engineMoment *= getStageMultiplier();
      this.engineMoment += getJetFrictionMoment(paramFloat);
      computePropForces(this.w, 0.0F, 0.0F, this.propAoA0, 0.0F);
      f2 = this.w * this._1_wMax;
      float f4 = f2 * this.pressureExtBar;
      f5 = f2 * f2;
      float f7 = 1.0F - 0.006F * (Atmosphere.temperature((float)this.reference.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double) - 290.0F);

      f8 = 1.0F - 0.0011F * this.reference.getSpeed();
      this.propForce = (this.thrustMax * f4 * f5 * f7 * f8 * getStageMultiplier());

      float f9 = this.engineMoment - this.propMoment;
      this.aw = (f9 / (this.propI + this.engineI) * 1.0F);
      if (this.aw > 0.0F)
        this.aw *= this.engineAcceleration;
      this.w += this.aw * paramFloat;
      if (this.w < -this.wMaxAllowed)
        this.w = (-this.wMaxAllowed);
      if (this.w > this.wMaxAllowed + this.wMaxAllowed)
        this.w = (this.wMaxAllowed + this.wMaxAllowed);
      this.engineForce.set(this.engineVector);
      this.engineForce.scale(this.propForce);
      this.engineTorque.cross(this.enginePos, this.engineForce);
      this.rpm = toRPM(this.w);
      break;
    case 3:
    case 4:
      this.w = (this.wMin + (this.wMax - this.wMin) * this.controlThrottle);
      if ((this.w < this.wMin) || (this.w < 0.0F) || (this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.fuel == 0.0F) || (this.stage != 6))
        this.w = 0.0F;
      this.propForce = (this.w / this.wMax * this.thrustMax);
      this.propForce *= getStageMultiplier();
      f1 = (float)this.reference.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length();
      if (f1 > 208.33299F) {
        if (f1 > 291.66599F)
          this.propForce = 0.0F;
        else {
          this.propForce *= (float)Math.sqrt((291.66599F - f1) / 83.332993F);
        }
      }
      this.engineForce.set(this.engineVector);
      this.engineForce.scale(this.propForce);
      this.engineTorque.cross(this.enginePos, this.engineForce);
      this.rpm = toRPM(this.w);
      break;
    case 6:
      this.w = (this.wMin + (this.wMax - this.wMin) * this.controlThrottle);
      if ((this.w < this.wMin) || (this.w < 0.0F) || (this.stage != 6))
        this.w = 0.0F;
      f1 = this.reference.getSpeed() / 94.0F;
      if (f1 < 1.0F)
        this.w = 0.0F;
      else
        f1 = (float)Math.sqrt(f1);
      this.propForce = (this.w / this.wMax * this.thrustMax * f1);
      this.propForce *= getStageMultiplier();
      f2 = (float)this.reference.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length();
      if (f2 > 208.33299F) {
        if (f2 > 291.66599F)
          this.propForce = 0.0F;
        else {
          this.propForce *= (float)Math.sqrt((291.66599F - f2) / 83.332993F);
        }
      }
      this.engineForce.set(this.engineVector);
      this.engineForce.scale(this.propForce);
      this.engineTorque.cross(this.enginePos, this.engineForce);
      this.rpm = toRPM(this.w);
      if (!(this.reference instanceof RealFlightModel)) break;
      RealFlightModel localRealFlightModel = (RealFlightModel)this.reference;
      f2 = Aircraft.cvt(this.propForce, 0.0F, this.thrustMax, 0.0F, 0.21F);
      if (localRealFlightModel.producedShakeLevel >= f2) break;
      localRealFlightModel.producedShakeLevel = f2; break;
    case 5:
      this.engineForce.set(this.engineVector);
      this.engineForce.scale(this.propForce);
      this.engineTorque.cross(this.enginePos, this.engineForce);
    }
  }

  private void computePropForces(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    float f1 = paramFloat1 * this.propr;
    float f2 = paramFloat2 * paramFloat2 + f1 * f1;
    float f3 = (float)Math.sqrt(f2);
    float f4 = 0.5F * getFanCy((float)Math.toDegrees(paramFloat4)) * Atmosphere.density(paramFloat5) * f2 * this.propSEquivalent;

    float f5 = 0.5F * getFanCx((float)Math.toDegrees(paramFloat4)) * Atmosphere.density(paramFloat5) * f2 * this.propSEquivalent;

    if (f3 > 300.0F) {
      f6 = 1.0F + 0.02F * (f3 - 300.0F);
      if (f6 > 2.0F)
        f6 = 2.0F;
      f5 *= f6;
    }
    if (f3 < 0.001F)
      f3 = 0.001F;
    float f6 = 1.0F / f3;
    float f7 = paramFloat2 * f6;
    float f8 = f1 * f6;
    float f9 = 1.0F;
    if (paramFloat2 < this.Vopt) {
      float f10 = this.Vopt - paramFloat2;
      f9 = 1.0F - 5.0E-005F * f10 * f10;
    }
    this.propForce = (f9 * (f4 * f8 - f5 * f7));
    this.propMoment = ((f5 * f8 + f4 * f7) * this.propr);
  }

  public void toggle() {
    if (this.stage == 0) {
      setEngineStarts(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    } else if (this.stage < 7) {
      setEngineStops(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      if (this.reference.isPlayers())
        HUD.log("EngineI0");
    }
  }

  public float getPowerOutput() {
    if ((this.stage == 0) || (this.stage > 6))
      return 0.0F;
    return this.controlThrottle * this.readyness;
  }

  public float getThrustOutput() {
    if ((this.stage == 0) || (this.stage > 6))
      return 0.0F;
    float f = this.w * this._1_wMax * this.readyness;
    if (f > 1.1F)
      f = 1.1F;
    return f;
  }

  public float getReadyness() {
    return this.readyness;
  }

  public float getPropPhi() {
    return this.propPhi;
  }

  private float getPropAngleDeviceSpeed() {
    if (this.isPropAngleDeviceHydroOperable)
      return this.propAngleChangeSpeed;
    return this.propAngleChangeSpeed * 10.0F;
  }

  public int getPropDir() {
    return this.propDirection;
  }

  public float getPropAoA() {
    return this.propAoA;
  }

  public Vector3f getForce() {
    return this.engineForce;
  }

  public float getRearRush() {
    return this.rearRush;
  }

  public float getw() {
    return this.w;
  }

  public float getRPM() {
    return this.rpm;
  }

  public float getPropw() {
    return this.w * this.propReductor;
  }

  public float getPropRPM() {
    return this.rpm * this.propReductor;
  }

  public int getType() {
    return this.type;
  }

  public float getControlThrottle() {
    return this.controlThrottle;
  }

  public boolean getControlAfterburner() {
    return this.controlAfterburner;
  }

  public boolean isHasControlThrottle() {
    return this.bHasThrottleControl;
  }

  public boolean isHasControlAfterburner() {
    return this.bHasAfterburnerControl;
  }

  public float getControlProp() {
    return this.controlProp;
  }

  public boolean isHasControlProp() {
    return this.bHasPropControl;
  }

  public boolean isAllowsAutoProp() {
    if ((this.reference.isPlayers()) && ((this.reference instanceof RealFlightModel)) && (((RealFlightModel)this.reference).isRealMode()))
    {
      if (World.cur().diffCur.ComplexEManagement) {
        switch (this.propAngleDeviceType) {
        case 0:
          return false;
        case 5:
          return true;
        case 6:
          return false;
        case 3:
        case 4:
          return false;
        case 1:
        case 2:
          return ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE9)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRELF14E)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE14C)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE8)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE8FB)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE8CLPFB)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE8CLP)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE12)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIREXIIclpearly));
        case 7:
        case 8:
          return true;
        }
      }
      else
      {
        return this.bHasPropControl;
      }
    }
    return true;
  }

  public float getControlMix() {
    return this.controlMix;
  }

  public boolean isHasControlMix() {
    return this.bHasMixControl;
  }

  public int getControlMagnetos() {
    return this.controlMagneto;
  }

  public int getControlCompressor() {
    return this.controlCompressor;
  }

  public boolean isHasControlMagnetos() {
    return this.bHasMagnetoControl;
  }

  public boolean isHasControlCompressor() {
    return this.bHasCompressorControl;
  }

  public int getControlFeather() {
    return this.controlFeather;
  }

  public boolean isHasControlFeather() {
    return this.bHasFeatherControl;
  }

  public boolean isAllowsAutoRadiator() {
    if (World.cur().diffCur.ComplexEManagement) {
      if (((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof P_51)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof P_51Mustang)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof P_38)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof YAK_3)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof YAK_3P)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof YAK_9M)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof YAK_9U)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof YAK_9UT)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE8)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE8CLP)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE8CLPFB)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE8FB)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE9)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRELF14E)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE14C)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIRE12)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof SPITFIREXIIclpearly)) || ((this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor instanceof P_63C)))
      {
        return true;
      }switch (this.propAngleDeviceType) {
      case 7:
        return true;
      case 8:
        return this.type == 0;
      }
      return false;
    }

    return true;
  }

  public boolean isHasControlRadiator() {
    return this.bHasRadiatorControl;
  }

  public float getControlRadiator() {
    return this.controlRadiator;
  }

  public int getExtinguishers() {
    return this.extinguishers;
  }

  private float getFanCy(float paramFloat) {
    if (paramFloat > 34.0F)
      paramFloat = 34.0F;
    if (paramFloat < -8.0F)
      paramFloat = -8.0F;
    if (paramFloat < 16.0F)
      return -0.004688F * paramFloat * paramFloat + 0.15F * paramFloat + 0.4F;
    float f = 0.0F;
    if (paramFloat > 22.0F) {
      f = 0.01F * (paramFloat - 22.0F);
      paramFloat = 22.0F;
    }
    return 0.00097222F * paramFloat * paramFloat - 0.070833F * paramFloat + 2.4844F + f;
  }

  private float getFanCx(float paramFloat) {
    if (paramFloat < -4.0F)
      paramFloat = -8.0F - paramFloat;
    if (paramFloat > 34.0F)
      paramFloat = 34.0F;
    if (paramFloat < 16.0D)
      return 0.00035F * paramFloat * paramFloat + 0.0028F * paramFloat + 0.0256F;
    float f = 0.0F;
    if (paramFloat > 22.0F) {
      f = 0.04F * (paramFloat - 22.0F);
      paramFloat = 22.0F;
    }
    return -0.00555F * paramFloat * paramFloat + 0.24444F * paramFloat - 2.32888F + f;
  }

  public int getCylinders() {
    return this.cylinders;
  }

  public int getCylindersOperable() {
    return this.cylindersOperable;
  }

  public float getCylindersRatio() {
    return this.cylindersOperable / this.cylinders;
  }

  public int getStage() {
    return this.stage;
  }

  public float getBoostFactor() {
    return this.engineBoostFactor;
  }

  public float getManifoldPressure() {
    return this.compressorManifoldPressure;
  }

  public void setManifoldPressure(float paramFloat) {
    this.compressorManifoldPressure = paramFloat;
  }

  public boolean getSootState() {
    return false;
  }

  public Point3f getEnginePos() {
    return this.enginePos;
  }

  public Point3f getPropPos() {
    return this.propPos;
  }

  public Vector3f getEngineVector() {
    return this.engineVector;
  }

  public float forcePropAOA(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean)
  {
    float f1;
    switch (this.type) {
    default:
      return -1.0F;
    case 0:
    case 1:
    case 7:
      f1 = this.controlThrottle;
      boolean bool = this.controlAfterburner;
      int i = this.stage;
      safeLoc.set(this.reference.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      safeVwld.set(this.reference.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      safeVflow.set(this.reference.jdField_Vflow_of_type_ComMaddoxJGPVector3d);
      if (paramBoolean)
        this.w = this.wWEP;
      else
        this.w = this.wMax;
      this.controlThrottle = paramFloat3;
      if ((this.engineBoostFactor <= 1.0D) && (this.controlThrottle > 1.0F))
        this.controlThrottle = 1.0F;
      if ((this.afterburnerType > 0) && (paramBoolean))
        this.controlAfterburner = true; this.stage = 6;
      this.fastATA = true;
      this.reference.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(0.0D, 0.0D, paramFloat2);
      this.reference.jdField_Vwld_of_type_ComMaddoxJGPVector3d.set(paramFloat1, 0.0D, 0.0D);
      this.reference.jdField_Vflow_of_type_ComMaddoxJGPVector3d.set(paramFloat1, 0.0D, 0.0D);
      this.pressureExtBar = (Atmosphere.pressure(this.reference.getAltitude()) + this.compressorSpeedManifold * 0.5F * Atmosphere.density(this.reference.getAltitude()) * paramFloat1 * paramFloat1);

      this.pressureExtBar /= Atmosphere.P0();
      float f4 = getCompressorMultiplier(0.033F);
      f4 *= getN();
      this.controlThrottle = f1;
      this.controlAfterburner = bool;
      this.stage = i;
      this.reference.jdField_Loc_of_type_ComMaddoxJGPPoint3d.set(safeLoc);
      this.reference.jdField_Vwld_of_type_ComMaddoxJGPVector3d.set(safeVwld);
      this.reference.jdField_Vflow_of_type_ComMaddoxJGPVector3d.set(safeVflow);
      this.fastATA = false;
      this.w = 0.0F;
      float f5 = 1.5F;
      float f6 = -0.06F;
      float f7 = 0.5F * (f5 + f6);
      int j = 0;
      float f8;
      while (true) { f8 = 0.5F * (f5 + f6);
        if (paramBoolean) {
          computePropForces(this.wWEP * this.propReductor, paramFloat1, 0.0F, f8, paramFloat2);
        }
        else {
          computePropForces(this.wMax * this.propReductor, paramFloat1, 0.0F, f8, paramFloat2);
        }
        if (((this.propForce > 0.0F) && (Math.abs(this.propMoment - f4) < 1.0E-005F)) || (j > 32))
        {
          break;
        }
        if ((this.propForce > 0.0F) && (this.propMoment > f4))
          f5 = f8;
        else
          f6 = f8;
        j++;
      }
      float f9 = (float)Math.toDegrees(Math.atan(paramFloat1 / (this.wMax * this.propReductor * this.propr)) + f8);

      return this.propForce;
    case 2:
      this.pressureExtBar = (Atmosphere.pressure(paramFloat2) + this.compressorSpeedManifold * 0.5F * Atmosphere.density(paramFloat2) * paramFloat1 * paramFloat1);

      this.pressureExtBar /= Atmosphere.P0();
      f1 = this.pressureExtBar;
      float f2 = 1.0F - 0.006F * (Atmosphere.temperature(paramFloat2) - 290.0F);

      float f3 = 1.0F - 0.0011F * paramFloat1;
      this.propForce = (this.thrustMax * f1 * f2 * f3);
      return this.propForce;
    case 3:
    case 4:
    case 6:
      this.propForce = this.thrustMax;
      if (paramFloat1 > 208.33299F) {
        if (paramFloat1 > 291.66599F)
          this.propForce = 0.0F;
        else {
          this.propForce *= (float)Math.sqrt((291.66599F - paramFloat1) / 83.332993F);
        }
      }
      return this.propForce;
    case 5:
      return this.thrustMax;
    case 8:
    }return -1.0F;
  }

  public float getEngineLoad()
  {
    float f1 = 0.1F + getControlThrottle() * 0.8181818F;
    float f2 = getw() / this.wMax;
    return f2 / f1;
  }

  private void overrevving() {
    if (((this.reference instanceof RealFlightModel)) && (((RealFlightModel)this.reference).isRealMode()) && (World.cur().diffCur.ComplexEManagement) && (World.cur().diffCur.Engine_Overheat) && (this.w > this.wMaxAllowed) && (this.bIsMaster))
    {
      this.wMaxAllowed = (0.999965F * this.wMaxAllowed);
      this._1_wMaxAllowed = (1.0F / this.wMaxAllowed);
      tmpF *= (1.0F - (this.wMaxAllowed - this.w) * 0.01F);
      this.engineDamageAccum += 0.01F + 0.05F * (this.w - this.wMaxAllowed) * this._1_wMaxAllowed;

      if (this.engineDamageAccum > 1.0F) {
        if (heatStringID == -1)
          heatStringID = HUD.makeIdLog();
        if (this.reference.isPlayers())
          HUD.log(heatStringID, "EngineOverheat");
        setReadyness(getReadyness() - (this.engineDamageAccum - 1.0F) * 0.005F);
      }

      if (getReadyness() < 0.2F)
        setEngineDies(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
    }
  }

  public float getN() {
    if (this.stage == 6)
    {
      float f1;
      float f2;
      switch (this.engineCarburetorType) {
      case 0:
        f1 = 0.05F + 0.95F * getControlThrottle();
        f2 = this.w / this.wMax;
        tmpF = this.engineMomentMax * (-1.0F / f1 * f2 * f2 + 2.0F * f2);

        if (getControlThrottle() > 1.0F)
          tmpF *= this.engineBoostFactor;
        overrevving();
        break;
      case 3:
        f1 = 0.05F + 0.95F * getControlThrottle();
        f2 = this.w / this.wNom;
        tmpF = this.engineMomentMax * (-1.0F / f1 * f2 * f2 + 2.0F * f2);

        if (getControlThrottle() > 1.0F)
          tmpF *= this.engineBoostFactor;
        if (this.reference.getOverload() < 0.0F) {
          this.producedDistabilisation += 16.0F;
          tmpF *= 0.5F;
          if ((this.reference.isPlayers()) && ((this.reference instanceof RealFlightModel)) && (((RealFlightModel)this.reference).isRealMode()) && (this.bIsMaster) && (World.Rnd().nextFloat() < 0.001F))
          {
            setEngineStops(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          }
        }
        overrevving();
        break;
      case 1:
      case 2:
        f1 = 0.1F + 0.9F * getControlThrottle();
        if (f1 > 1.0F)
          f1 = 1.0F;
        f2 = this.engineMomentMax * (-0.5F * f1 * f1 + 1.0F * f1 + 0.5F);
        float f3;
        if (this.controlAfterburner)
          f3 = this.w / (this.wWEP * f1);
        else
          f3 = this.w / (this.wNom * f1);
        tmpF = f2 * (2.0F * f3 - 1.0F * f3 * f3);
        if (getControlThrottle() > 1.0F) {
          tmpF *= (1.0F + (getControlThrottle() - 1.0F) * 10.0F * (this.engineBoostFactor - 1.0F));
        }
        overrevving();
      }

      if (this.controlAfterburner) {
        if (this.afterburnerType == 1) {
          if ((this.controlThrottle > 1.0F) && (this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.nitro > 0.0F))
            tmpF *= this.engineAfterburnerBoostFactor;
        } else if ((this.afterburnerType == 8) || (this.afterburnerType == 7)) {
          if (this.controlCompressor < this.compressorMaxStep)
            tmpF *= this.engineAfterburnerBoostFactor;
        }
        else tmpF *= this.engineAfterburnerBoostFactor;
      }
      if (this.engineDamageAccum > 0.0F)
        this.engineDamageAccum -= 0.01F;
      if (this.engineDamageAccum < 0.0F)
        this.engineDamageAccum = 0.0F;
      if (tmpF < 0.0F)
        tmpF = Math.max(tmpF, -0.8F * this.w * this._1_wMax * this.engineMomentMax);
      return tmpF;
    }
    tmpF = -1500.0F * this.w * this._1_wMax * this.engineMomentMax;
    if (this.stage == 8)
      this.w = 0.0F;
    return tmpF;
  }

  private float getDistabilisationMultiplier() {
    if (this.engineMoment < 0.0F)
      return 1.0F;
    float f = 1.0F + World.Rnd().nextFloat(-1.0F, 0.1F) * getDistabilisationAmplitude();

    if ((f < 0.0F) && (this.w < 0.5F * (this.wMax + this.wMin)))
      return 0.0F;
    return f;
  }

  public float getDistabilisationAmplitude() {
    if (getCylindersOperable() > 2) {
      float f = 1.0F - getCylindersRatio();
      return this.engineDistAM * this.w * this.w + this.engineDistBM * this.w + this.engineDistCM + 9.25F * f * f + this.producedDistabilisation;
    }

    return 11.25F;
  }

  private float getCompressorMultiplier(float paramFloat) {
    float f1 = this.controlThrottle;
    if (f1 > 1.0F)
      f1 = 1.0F;
    float f2;
    switch (this.propAngleDeviceType) {
    case 1:
    case 2:
    case 7:
    case 8:
      f2 = getATA(toRPM(this.propAngleDeviceMinParam + (this.propAngleDeviceMaxParam - this.propAngleDeviceMinParam) * f1));

      break;
    case 3:
    case 4:
    case 5:
    case 6:
    default:
      f2 = this.compressorRPMtoWMaxATA * (0.55F + 0.45F * f1);
    }
    this.compressorManifoldThreshold = f2;
    float f3;
    float f4;
    float f5;
    float f8;
    float f9;
    float f10;
    float f11;
    float f7;
    float f6;
    switch (this.compressorType) {
    case 0:
      f3 = Atmosphere.pressure(this.reference.getAltitude()) + 0.5F * Atmosphere.density(this.reference.getAltitude()) * this.reference.getSpeed() * this.reference.getSpeed();

      f4 = f3 / Atmosphere.P0();
      return f4;
    case 1:
      f3 = this.pressureExtBar;
      if (((!this.bHasCompressorControl) || (!this.reference.isPlayers()) || (!(this.reference instanceof RealFlightModel)) || (!((RealFlightModel)this.reference).isRealMode()) || (!World.cur().diffCur.ComplexEManagement) || (this.fastATA)) && ((this.reference.isTick(128, 0)) || (this.fastATA)))
      {
        this.compressorStepFound = false;
        this.controlCompressor = 0;
      }
      f4 = -1.0F;
      f5 = -1.0F;
      int i = -1;
      float f12;
      float f13;
      float f14;
      if (this.fastATA) {
        this.controlCompressor = 0;
        while (this.controlCompressor <= this.compressorMaxStep)
        {
          this.compressorManifoldThreshold = f2;
          f8 = this.compressorPressure[this.controlCompressor];
          f9 = this.compressorRPMtoWMaxATA / f8;
          f10 = 1.0F;
          f11 = 1.0F;
          if (f3 > f8) {
            f12 = 1.0F - f8;
            if (f12 < 1.0E-004F)
              f12 = 1.0E-004F;
            f13 = 1.0F - f3;
            if (f13 < 0.0F)
              f13 = 0.0F;
            f14 = 1.0F;
            for (int j = 1; j <= this.controlCompressor; )
            {
              if (this.compressorAltMultipliers[this.controlCompressor] >= 1.0F)
              {
                f14 *= 0.8F;
              }
              else f14 *= 0.8F * this.compressorAltMultipliers[this.controlCompressor];
              j++;
            }

            f10 = f14 + f13 / f12 * (this.compressorAltMultipliers[this.controlCompressor] - f14);
          }
          else
          {
            f10 = this.compressorAltMultipliers[this.controlCompressor];
          }this.compressorManifoldPressure = ((this.compressorPAt0 + (1.0F - this.compressorPAt0) * this.w * this._1_wMax) * f3 * f9);

          f12 = this.compressorRPMtoWMaxATA / this.compressorManifoldPressure;

          if ((this.controlAfterburner) && (((this.afterburnerType != 8) && (this.afterburnerType != 7)) || ((this.controlCompressor != this.compressorMaxStep) && ((this.afterburnerType != 1) || (this.controlThrottle <= 1.0F) || (this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.nitro > 0.0F)))))
          {
            f12 *= this.afterburnerCompressorFactor;
            this.compressorManifoldThreshold *= this.afterburnerCompressorFactor;
          }

          this.compressor2ndThrottle = f12;
          if (this.compressor2ndThrottle > 1.0F)
            this.compressor2ndThrottle = 1.0F;
          this.compressorManifoldPressure *= this.compressor2ndThrottle;
          this.compressor1stThrottle = (f2 / this.compressorRPMtoWMaxATA);
          if (this.compressor1stThrottle > 1.0F)
            this.compressor1stThrottle = 1.0F;
          this.compressorManifoldPressure *= this.compressor1stThrottle;
          f11 = f10 * this.compressorManifoldPressure / this.compressorManifoldThreshold;

          if ((this.controlAfterburner) && ((this.afterburnerType == 8) || (this.afterburnerType == 7)) && (this.controlCompressor == this.compressorMaxStep))
          {
            if (f11 / this.engineAfterburnerBoostFactor > f4) {
              f4 = f11;
              i = this.controlCompressor;
            }
          } else if (f11 > f4) {
            f4 = f11;
            i = this.controlCompressor;
          }
          this.controlCompressor += 1;
        }

        f7 = f4;
        this.controlCompressor = i;
      } else {
        f8 = f2;
        if (this.controlAfterburner)
          f8 *= this.afterburnerCompressorFactor;
        do {
          f9 = this.compressorPressure[this.controlCompressor];
          f10 = this.compressorRPMtoWMaxATA / f9;
          f11 = 1.0F;
          f12 = 1.0F;
          if (f3 > f9) {
            f13 = 1.0F - f9;
            if (f13 < 1.0E-004F)
              f13 = 1.0E-004F;
            f14 = 1.0F - f3;
            if (f14 < 0.0F)
              f14 = 0.0F;
            float f15 = 1.0F;
            for (int k = 1; k <= this.controlCompressor; )
            {
              if (this.compressorAltMultipliers[this.controlCompressor] >= 1.0F)
              {
                f15 *= 0.8F;
              }
              else f15 *= 0.8F * this.compressorAltMultipliers[this.controlCompressor];
              k++;
            }

            f11 = f15 + f14 / f13 * (this.compressorAltMultipliers[this.controlCompressor] - f15);

            f12 = f11;
          } else {
            f11 = this.compressorAltMultipliers[this.controlCompressor];
            f12 = f11 * f3 * f10 / f8;
          }
          if (f12 > f4) {
            f4 = f12;
            f5 = f11;
            i = this.controlCompressor;
          }
          if (!this.compressorStepFound) {
            this.controlCompressor += 1;
            if (this.controlCompressor == this.compressorMaxStep + 1)
              this.compressorStepFound = true; 
          }
        }
        while (!this.compressorStepFound);
        if (i < 0)
          i = 0;
        this.controlCompressor = i;
        f9 = this.compressorPressure[this.controlCompressor];
        f10 = this.compressorRPMtoWMaxATA / f9;
        this.compressorManifoldPressure = ((this.compressorPAt0 + (1.0F - this.compressorPAt0) * this.w * this._1_wMax) * f3 * f10);

        f11 = this.compressorRPMtoWMaxATA / this.compressorManifoldPressure;

        if ((this.controlAfterburner) && (((this.afterburnerType != 8) && (this.afterburnerType != 7)) || ((this.controlCompressor != this.compressorMaxStep) && ((this.afterburnerType != 1) || (this.controlThrottle <= 1.0F) || (this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.nitro > 0.0F)))))
        {
          f11 *= this.afterburnerCompressorFactor;
          this.compressorManifoldThreshold *= this.afterburnerCompressorFactor;
        }
        if (this.fastATA)
          this.compressor2ndThrottle = f11;
        else {
          this.compressor2ndThrottle -= 3.0F * paramFloat * (this.compressor2ndThrottle - f11);
        }
        if (this.compressor2ndThrottle > 1.0F)
          this.compressor2ndThrottle = 1.0F;
        this.compressorManifoldPressure *= this.compressor2ndThrottle;
        this.compressor1stThrottle = (f2 / this.compressorRPMtoWMaxATA);
        if (this.compressor1stThrottle > 1.0F)
          this.compressor1stThrottle = 1.0F;
        this.compressorManifoldPressure *= this.compressor1stThrottle;
        f7 = this.compressorManifoldPressure / this.compressorManifoldThreshold;

        f7 *= f5;
      }
      if ((this.w <= 20.0F) && (this.w < 150.0F)) {
        this.compressorManifoldPressure = Math.min(this.compressorManifoldPressure, f3 * (0.4F + (this.w - 20.0F) * 0.04F));
      }

      if (this.w < 20.0F)
        this.compressorManifoldPressure = (f3 * (1.0F - this.w * 0.03F));
      if ((this.mixerType == 1) && (this.stage == 6))
        this.compressorManifoldPressure *= getMixMultiplier();
      return f7;
    case 2:
      f3 = this.pressureExtBar;
      f4 = this.compressorPressure[this.controlCompressor];
      f5 = this.compressorRPMtoWMaxATA / f4;
      this.compressorManifoldPressure = ((this.compressorPAt0 + (1.0F - this.compressorPAt0) * this.w * this._1_wMax) * f3 * f5);

      f6 = this.compressorRPMtoWMaxATA / this.compressorManifoldPressure;
      if ((this.controlAfterburner) && ((this.afterburnerType != 1) || (this.controlThrottle <= 1.0F) || (this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.nitro > 0.0F)))
      {
        f6 *= this.afterburnerCompressorFactor;
        this.compressorManifoldThreshold *= this.afterburnerCompressorFactor;
      }
      if (this.fastATA)
        this.compressor2ndThrottle = f6;
      else {
        this.compressor2ndThrottle -= 0.1F * (this.compressor2ndThrottle - f6);
      }
      if (this.compressor2ndThrottle > 1.0F)
        this.compressor2ndThrottle = 1.0F;
      this.compressorManifoldPressure *= this.compressor2ndThrottle;
      this.compressor1stThrottle = (f2 / this.compressorRPMtoWMaxATA);
      if (this.compressor1stThrottle > 1.0F)
        this.compressor1stThrottle = 1.0F;
      this.compressorManifoldPressure *= this.compressor1stThrottle;

      if ((this.controlAfterburner) && (this.afterburnerType == 2) && (this.reference.isPlayers()) && ((this.reference instanceof RealFlightModel)) && (((RealFlightModel)this.reference).isRealMode()) && (this.reference.jdField_M_of_type_ComMaddoxIl2FmMass.nitro > 0.0F))
      {
        f8 = this.compressorManifoldPressure + 0.2F;
        if ((f8 > this.compressorRPMtoWMaxATA + 0.199F) && (!this.fastATA) && (World.Rnd().nextFloat() < 0.001F))
        {
          this.readyness = 0.0F;
          setEngineDies(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
        }
        if (f8 > this.compressorManifoldThreshold)
          f8 = this.compressorManifoldThreshold;
        f7 = f8 / this.compressorManifoldThreshold;
      } else {
        f7 = this.compressorManifoldPressure / this.compressorManifoldThreshold;
      }
      if ((this.w <= 20.0F) && (this.w < 150.0F)) {
        this.compressorManifoldPressure = Math.min(this.compressorManifoldPressure, f3 * (0.4F + (this.w - 20.0F) * 0.04F));
      }

      if (this.w < 20.0F)
        this.compressorManifoldPressure = (f3 * (1.0F - this.w * 0.03F));
      if (f3 > f4) {
        f8 = 1.0F - f4;
        if (f8 < 1.0E-004F)
          f8 = 1.0E-004F;
        f9 = 1.0F - f3;
        if (f9 < 0.0F)
          f9 = 0.0F;
        f10 = 1.0F;
        f11 = f10 + f9 / f8 * (this.compressorAltMultipliers[this.controlCompressor] - f10);

        f7 *= f11;
      } else {
        f7 *= this.compressorAltMultipliers[this.controlCompressor];
      }return f7;
    case 3:
      f3 = this.pressureExtBar;
      this.controlCompressor = 0;
      f4 = -1.0F;
      f5 = this.compressorPressure[this.controlCompressor];
      f6 = this.compressorRPMtoWMaxATA / f5;
      f7 = 1.0F;
      f8 = 1.0F;
      if (f3 > f5) {
        f9 = 1.0F - f5;
        if (f9 < 1.0E-004F)
          f9 = 1.0E-004F;
        f10 = 1.0F - f3;
        if (f10 < 0.0F)
          f10 = 0.0F;
        f11 = 1.0F;
        f7 = f11 + f10 / f9 * (this.compressorAltMultipliers[this.controlCompressor] - f11);
      }
      else
      {
        f7 = this.compressorAltMultipliers[this.controlCompressor];
      }f4 = f7;
      f6 = this.compressorRPMtoWMaxATA / f5;
      if (f3 < f5)
        f3 = 0.1F * f3 + 0.9F * f5;
      f9 = f3 * f6;
      this.compressorManifoldPressure = ((this.compressorPAt0 + (1.0F - this.compressorPAt0) * this.w * this._1_wMax) * f9);

      f10 = this.compressorRPMtoWMaxATA / this.compressorManifoldPressure;
      if (this.fastATA)
        this.compressor2ndThrottle = f10;
      else {
        this.compressor2ndThrottle -= 3.0F * paramFloat * (this.compressor2ndThrottle - f10);
      }
      if (this.compressor2ndThrottle > 1.0F)
        this.compressor2ndThrottle = 1.0F;
      this.compressorManifoldPressure *= this.compressor2ndThrottle;
      this.compressor1stThrottle = (f2 / this.compressorRPMtoWMaxATA);
      if (this.compressor1stThrottle > 1.0F)
        this.compressor1stThrottle = 1.0F;
      this.compressorManifoldPressure *= this.compressor1stThrottle;
      f11 = this.compressorManifoldPressure / this.compressorManifoldThreshold;

      f11 *= f4;
      if ((this.w <= 20.0F) && (this.w < 150.0F)) {
        this.compressorManifoldPressure = Math.min(this.compressorManifoldPressure, f3 * (0.4F + (this.w - 20.0F) * 0.04F));
      }

      if (this.w < 20.0F)
        this.compressorManifoldPressure = (f3 * (1.0F - this.w * 0.03F));
      return f11;
    }

    return 1.0F;
  }

  private float getMagnetoMultiplier()
  {
    switch (this.controlMagneto) {
    case 0:
      return 0.0F;
    case 1:
      return this.bMagnetos[0] != 0 ? 0.87F : 0.0F;
    case 2:
      return this.bMagnetos[1] != 0 ? 0.87F : 0.0F;
    case 3:
      float f = 0.0F;
      f += (this.bMagnetos[0] != 0 ? 0.87F : 0.0F);
      f += (this.bMagnetos[1] != 0 ? 0.87F : 0.0F);
      if (f > 1.0F)
        f = 1.0F;
      return f;
    }

    return 1.0F;
  }

  private float getMixMultiplier()
  {
    float f1 = 0.0F;
    switch (this.mixerType) {
    case 0:
      return 1.0F;
    case 1:
      if (this.controlMix != 1.0F) break;
      this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setSootState(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, this.number, 0);
      return 1.0F;
    case 2:
      if ((this.reference.isPlayers()) && ((this.reference instanceof RealFlightModel)) && (((RealFlightModel)this.reference).isRealMode()))
      {
        if (!World.cur().diffCur.ComplexEManagement)
          return 1.0F;
        f2 = this.mixerLowPressureBar * this.controlMix;
        float f3;
        if (f2 < this.pressureExtBar - f1) {
          if (f2 < 0.03F)
            setEngineStops(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
          this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setSootState(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, this.number, 0);
          if (f2 > (this.pressureExtBar - f1) * 0.25F)
            return 1.0F;
          f3 = f2 / ((this.pressureExtBar - f1) * 0.25F);
          return f3;
        }
        if (f2 > this.pressureExtBar) {
          this.producedDistabilisation += 0.0F + 35.0F * (1.0F - (this.pressureExtBar + f1) / (f2 + 1.0E-004F));

          this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setSootState(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, this.number, 1);
          f3 = (this.pressureExtBar + f1) / (f2 + 1.0E-004F);
          return f3;
        }
        this.reference.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setSootState(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor, this.number, 0);
        return 1.0F;
      }
      float f2 = this.mixerLowPressureBar * this.controlMix;
      if ((f2 < this.pressureExtBar - f1) && (f2 < 0.03F))
        setEngineStops(this.reference.jdField_actor_of_type_ComMaddoxIl2EngineActor);
      return 1.0F;
    }

    return 1.0F;
  }

  private float getStageMultiplier()
  {
    return this.stage != 6 ? 0.0F : 1.0F;
  }

  public void setFricCoeffT(float paramFloat) {
    this.fricCoeffT = paramFloat;
  }

  private float getFrictionMoment(float paramFloat) {
    float f1 = 0.0F;
    if ((this.bIsInoperable) || (this.stage == 0) || (this.controlMagneto == 0)) {
      this.fricCoeffT += 0.1F * paramFloat;
      if (this.fricCoeffT > 1.0F)
        this.fricCoeffT = 1.0F;
      float f2 = this.w * this._1_wMax;
      f1 = -this.fricCoeffT * (6.0F + 3.8F * f2) * (this.propI + this.engineI) / paramFloat;

      float f3 = -0.99F * this.w * (this.propI + this.engineI) / paramFloat;
      if (f1 < f3)
        f1 = f3;
    } else {
      this.fricCoeffT = 0.0F;
    }return f1;
  }

  private float getJetFrictionMoment(float paramFloat) {
    float f = 0.0F;
    if ((this.bIsInoperable) || (this.stage == 0))
      f = -0.002F * this.w * (this.propI + this.engineI) / paramFloat;
    return f;
  }

  public Vector3f getEngineForce() {
    return this.engineForce;
  }

  public Vector3f getEngineTorque() {
    return this.engineTorque;
  }

  public Vector3d getEngineGyro() {
    tmpV3d1.cross(this.reference.getW(), this.propIW);
    return tmpV3d1;
  }

  public float getEngineMomentRatio() {
    return this.engineMoment / this.engineMomentMax;
  }

  public boolean isPropAngleDeviceOperational() {
    return this.bIsAngleDeviceOperational;
  }

  public float getCriticalW() {
    return this.wMaxAllowed;
  }

  public float getPropPhiMin() {
    return this.propPhiMin;
  }

  public int getAfterburnerType() {
    return this.afterburnerType;
  }

  private float toRadianPerSecond(float paramFloat) {
    return paramFloat * 3.141593F * 2.0F / 60.0F;
  }

  private float toRPM(float paramFloat) {
    return paramFloat * 60.0F / 2.0F / 3.141593F;
  }

  private float getKforH(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = Atmosphere.density(paramFloat3) * (paramFloat2 * paramFloat2) / (Atmosphere.density(0.0F) * (paramFloat1 * paramFloat1));

    if (this.type != 2)
      f = f * kV(paramFloat1) / kV(paramFloat2);
    return f;
  }

  private float kV(float paramFloat) {
    return 1.0F - 0.0032F * paramFloat;
  }

  public final void setAfterburnerType(int paramInt) {
    this.afterburnerType = paramInt;
  }

  public final void setPropReductorValue(float paramFloat) {
    this.propReductor = paramFloat;
  }

  public void replicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException
  {
    paramNetMsgGuaranted.writeByte(this.controlMagneto | this.stage << 4);
    paramNetMsgGuaranted.writeByte(this.cylinders);
    paramNetMsgGuaranted.writeByte(this.cylindersOperable);
    paramNetMsgGuaranted.writeByte((int)(255.0F * this.readyness));
    paramNetMsgGuaranted.writeByte((int)(255.0F * ((this.propPhi - this.propPhiMin) / (this.propPhiMax - this.propPhiMin))));

    paramNetMsgGuaranted.writeFloat(this.w);
  }

  public void replicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
    int i = paramNetMsgInput.readUnsignedByte();
    this.stage = ((i & 0xF0) >> 4);
    this.controlMagneto = (i & 0xF);
    this.cylinders = paramNetMsgInput.readUnsignedByte();
    this.cylindersOperable = paramNetMsgInput.readUnsignedByte();
    this.readyness = (paramNetMsgInput.readUnsignedByte() / 255.0F);
    this.propPhi = (paramNetMsgInput.readUnsignedByte() / 255.0F * (this.propPhiMax - this.propPhiMin) + this.propPhiMin);

    this.w = paramNetMsgInput.readFloat();
  }
}