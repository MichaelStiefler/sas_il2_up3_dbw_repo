package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.CellAirPlane;
import com.maddox.il2.ai.air.CellObject;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.FObj;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Squares;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.TimeSkip;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetWing;
import com.maddox.il2.objects.ActorCrater;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.Wreck;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.artillery.RocketryRocket;
import com.maddox.il2.objects.weapons.BallisticProjectile;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.il2.objects.weapons.MGunAircraftGeneric;
import com.maddox.il2.objects.weapons.Pylon;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.KryptoInputFilter;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.MsgEndAction;
import com.maddox.rts.MsgEndActionListener;
import com.maddox.rts.NetChannel;
import com.maddox.rts.ObjIO;
import com.maddox.rts.ObjState;
import com.maddox.rts.Property;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.NumberTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public abstract class Aircraft extends NetAircraft
  implements MsgCollisionListener, MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener, MsgEndActionListener, Predator
{
  private static RangeRandom dec_rnd = new RangeRandom();
  public long tmSearchlighted;
  public static final float MINI_HIT = 5.000001E-007F;
  public static final float defaultUnitHit = 0.01F;
  public static final float powerPerMM = 1700.0F;
  public static final int HIT_COLLISION = 0;
  public static final int HIT_EXPLOSION = 1;
  public static final int HIT_SHOT = 2;
  protected static float[] ypr = { 0.0F, 0.0F, 0.0F };
  protected static float[] xyz = { 0.0F, 0.0F, 0.0F };
  public static final int _AILERON_L = 0;
  public static final int _AILERON_R = 1;
  public static final int _FUSELAGE = 2;
  public static final int _ENGINE_1 = 3;
  public static final int _ENGINE_2 = 4;
  public static final int _ENGINE_3 = 5;
  public static final int _ENGINE_4 = 6;
  public static final int _GEAR_C = 7;
  public static final int _FLAP_R = 8;
  public static final int _GEAR_L = 9;
  public static final int _GEAR_R = 10;
  public static final int _VER_STAB_1 = 11;
  public static final int _VER_STAB_2 = 12;
  public static final int _NOSE = 13;
  public static final int _OIL = 14;
  public static final int _RUDDER_1 = 15;
  public static final int _RUDDER_2 = 16;
  public static final int _HOR_STAB_L = 17;
  public static final int _HOR_STAB_R = 18;
  public static final int _TAIL_1 = 19;
  public static final int _TAIL_2 = 20;
  public static final int _TANK_1 = 21;
  public static final int _TANK_2 = 22;
  public static final int _TANK_3 = 23;
  public static final int _TANK_4 = 24;
  public static final int _TURRET_1 = 25;
  public static final int _TURRET_2 = 26;
  public static final int _TURRET_3 = 27;
  public static final int _TURRET_4 = 28;
  public static final int _TURRET_5 = 29;
  public static final int _TURRET_6 = 30;
  public static final int _ELEVATOR_L = 31;
  public static final int _ELEVATOR_R = 32;
  public static final int _WING_ROOT_L = 33;
  public static final int _WING_MIDDLE_L = 34;
  public static final int _WING_END_L = 35;
  public static final int _WING_ROOT_R = 36;
  public static final int _WING_MIDDLE_R = 37;
  public static final int _WING_END_R = 38;
  public static final int _FLAP_01 = 39;
  public static final int _FLAP_02 = 40;
  public static final int _FLAP_03 = 41;
  public static final int _FLAP_04 = 42;
  public static final int _NULLPART = 43;
  public static final int _NOMOREPARTS = 44;
  private static final String[] partNames = { "AroneL", "AroneR", "CF", "Engine1", "Engine2", "Engine3", "Engine4", "GearC2", "FlapR", "GearL2", "GearR2", "Keel1", "Keel2", "Nose", "Oil", "Rudder1", "Rudder2", "StabL", "StabR", "Tail1", "Tail2", "Tank1", "Tank2", "Tank3", "Tank4", "Turret1B", "Turret2B", "Turret3B", "Turret4B", "Turret5B", "Turret6B", "VatorL", "VatorR", "WingLIn", "WingLMid", "WingLOut", "WingRIn", "WingRMid", "WingROut", "Flap01", "Flap02", "Flap03", "Flap04", "NullPart", "EXPIRED" };

  private static final String[] partNamesForAll = { "AroneL", "AroneR", "CF", "GearL2", "GearR2", "Keel1", "Oil", "Rudder1", "StabL", "StabR", "Tail1", "VatorL", "VatorR", "WingLIn", "WingLMid", "WingLOut", "WingRIn", "WingRMid", "WingROut" };
  private static final long _HIT = 17592186044416L;
  private static final long _TOMASTER = 35184372088832L;
  public static final int END_EXPLODE = 2;
  public static final int END_FM_DESTROY = 3;
  public static final int END_DISAPPEAR = 4;
  private long timePostEndAction = -1L;

  public boolean buried = false;

  private float EpsCoarse_ = 0.03F;
  private float EpsSmooth_ = 0.003F;
  private float EpsVerySmooth_ = 0.0005F;
  private float Gear_;
  private float Rudder_;
  private float Elevator_;
  private float Aileron_;
  private float Flap_;
  private float BayDoor_ = 0.0F; private float AirBrake_ = 0.0F; private float Steering_ = 0.0F;
  public float wingfold_ = 0.0F; public float cockpitDoor_ = 0.0F; public float arrestor_ = 0.0F;

  protected float[] propPos = { 0.0F, 21.6F, 45.900002F, 66.900002F, 45.0F, 9.2F };
  protected int[] oldProp = { 0, 0, 0, 0, 0, 0 };
  protected static final String[][] Props = { { "Prop1_D0", "PropRot1_D0", "Prop1_D1" }, { "Prop2_D0", "PropRot2_D0", "Prop2_D1" }, { "Prop3_D0", "PropRot3_D0", "Prop3_D1" }, { "Prop4_D0", "PropRot4_D0", "Prop4_D1" }, { "Prop5_D0", "PropRot5_D0", "Prop5_D1" }, { "Prop6_D0", "PropRot6_D0", "Prop6_D1" } };
  private LightPointWorld[] lLight;
  private Hook[] lLightHook = { null, null, null, null };
  private static Loc lLightLoc1 = new Loc();
  private static Point3d lLightP1 = new Point3d();
  private static Point3d lLightP2 = new Point3d();
  private static Point3d lLightPL = new Point3d();
  private String _loadingCountry;
  private String typedName = "UNKNOWN";

  private static String[] _skinMat = { "Gloss1D0o", "Gloss1D1o", "Gloss1D2o", "Gloss2D0o", "Gloss2D1o", "Gloss2D2o", "Gloss1D0p", "Gloss1D1p", "Gloss1D2p", "Gloss2D0p", "Gloss2D1p", "Gloss2D2p", "Gloss1D0q", "Gloss1D1q", "Gloss1D2q", "Gloss2D0q", "Gloss2D1q", "Gloss2D2q", "Matt1D0o", "Matt1D1o", "Matt1D2o", "Matt2D0o", "Matt2D1o", "Matt2D2o", "Matt1D0p", "Matt1D1p", "Matt1D2p", "Matt2D0p", "Matt2D1p", "Matt2D2p", "Matt1D0q", "Matt1D1q", "Matt1D2q", "Matt2D0q", "Matt2D1q", "Matt2D2q" };

  private static final String[] _curSkin = { "skin1o.tga", "skin1p.tga", "skin1q.tga" };

  private static HashMapExt meshCache = new HashMapExt();
  private static HashMapExt airCache = new HashMapExt();

  protected static Loc tmpLocCell = new Loc();

  protected static Vector3d v1 = new Vector3d();
  private static Vector3f v2 = new Vector3f();
  private static Vector3d Vd = new Vector3d();
  protected static Point3d Pd = new Point3d();

  protected static Point3d tmpP1 = new Point3d();
  protected static Point3d tmpP2 = new Point3d();
  public static Loc tmpLoc1 = new Loc();
  protected static Loc tmpLoc2 = new Loc();
  protected static Loc tmpLoc3 = new Loc();
  protected static Loc tmpLocExp = new Loc();
  public static Orient tmpOr = new Orient();
  private static int tmpBonesHit;
  private static boolean bWasAlive = true;

  public static String[] partNames()
  {
    return partNames;
  }

  public int part(String paramString)
  {
    if (paramString == null) return 43;
    int i = 0; for (long l = 1L; i < 44; l <<= 1) {
      if (paramString.startsWith(partNames[i])) return i;
      i++;
    }

    return 43;
  }

  public boolean cut(String paramString)
  {
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.dryFriction = 1.0F;
    debugprintln("" + paramString + " goes off..");

    if (World.Rnd().nextFloat() < bailProbabilityOnCut(paramString)) {
      debugprintln("BAILING OUT - " + paramString + " gone, can't keep on..");
      hitDaSilk();
    }

    if (!isChunkAnyDamageVisible(paramString)) {
      debugprintln("" + paramString + " is already cut off - operation rejected..");
      return false;
    }

    int[] arrayOfInt = hideSubTrees(paramString + "_D");

    if (arrayOfInt == null) return false;

    for (int i = 0; i < arrayOfInt.length; i++)
    {
      localObject = new Wreckage(this, arrayOfInt[i]);

      for (int j = 0; j < 4; j++)
      {
        if (hierMesh().chunkName().startsWith(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[(j + 0)])) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.changeTankEffectBase(j, (Actor)localObject);
        }
      }
      for (j = 0; j < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); j++)
      {
        if (hierMesh().chunkName().startsWith(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[(j + 4)])) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.changeEngineEffectBase(j, (Actor)localObject);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.changeSootEffectBase(j, (Actor)localObject);
        }
      }
      for (j = 0; j < 6; j++)
      {
        if (hierMesh().chunkName().startsWith(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[(j + 12)])) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.changeNavLightEffectBase(j, (Actor)localObject);
        }
      }
      for (j = 0; j < 4; j++)
      {
        if (hierMesh().chunkName().startsWith(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[(j + 18)])) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.changeLandingLightEffectBase(j, (Actor)localObject);
        }
      }
      for (j = 0; j < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); j++)
      {
        if (hierMesh().chunkName().startsWith(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[(j + 22)])) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.changeOilEffectBase(j, (Actor)localObject);
        }

      }

      if ((hierMesh().chunkName().startsWith(paramString)) && 
        (World.Rnd().nextInt(0, 99) < 50)) {
        Eff3DActor.New((Actor)localObject, null, null, 1.0F, Wreckage.SMOKE, 3.0F);
      }

      Vd.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d); ((Wreckage)localObject).setSpeed(Vd);
    }
    arrayOfInt = hierMesh().getSubTrees(paramString + "_D");

    for (i = 0; i < arrayOfInt.length; i++) detachGun(arrayOfInt[i]);
    Object localObject = paramString + "_CAP";
    if (hierMesh().chunkFindCheck((String)localObject) >= 0) hierMesh().chunkVisible((String)localObject, true);

    for (i = 0; i < arrayOfInt.length; i++) {
      for (int k = 3; k < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.pnti.length; k++) {
        try {
          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.pnti[k] != -1) && (arrayOfInt[i] == hierMesh().chunkByHookNamed(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.pnti[k])))
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.pnti[k] = -1;
        } catch (Exception localException) {
          System.out.println("FATAL ERROR: Gear pnti[] cut failed on tt[] = " + k + " - " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.pnti.length);
        }
      }
    }

    hierMesh().setCurChunk(arrayOfInt[0]);
    hierMesh().getChunkLocObj(tmpLoc1);
    sfxCrash(tmpLoc1.getPoint());

    return true;
  }

  public boolean cut_Subtrees(String paramString)
  {
    debugprintln("" + paramString + " goes off..");

    if (World.Rnd().nextFloat() < bailProbabilityOnCut(paramString)) {
      debugprintln("BAILING OUT - " + paramString + " gone, can't keep on..");
      hitDaSilk();
    }

    if (!isChunkAnyDamageVisible(paramString)) {
      debugprintln("" + paramString + " is already cut off - operation rejected..");
      return false;
    }

    int i = hierMesh().chunkFindCheck(paramString + "_D0");
    if (i >= 0)
    {
      for (int j = 0; j <= 9; j++) {
        int k = hierMesh().chunkFindCheck(paramString + "_D" + j);
        if (k >= 0) {
          hierMesh().setCurChunk(k);
          if (hierMesh().isChunkVisible()) {
            break;
          }
        }
      }
      if (j > 9)
      {
        i = -1;
      }

    }

    Object localObject = null;
    if (i >= 0) {
      localObject = Wreckage.makeWreck(this, i);
      ((Actor)localObject).setOwner(this, false, false, false);
    }

    int[] arrayOfInt = hideSubTrees(paramString + "_D");

    if (arrayOfInt == null) {
      return false;
    }

    for (int m = 0; m < arrayOfInt.length; m++)
    {
      if (i < 0)
        localObject = new Wreckage(this, arrayOfInt[m]);
      else {
        hierMesh().setCurChunk(arrayOfInt[m]);
      }

      for (int n = 0; n < 4; n++)
      {
        if (hierMesh().chunkName().startsWith(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[(n + 0)])) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.changeTankEffectBase(n, (Actor)localObject);
        }
      }
      for (n = 0; n < 4; n++)
      {
        if (hierMesh().chunkName().startsWith(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEffectChunks[(n + 4)])) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.changeEngineEffectBase(n, (Actor)localObject);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.changeSootEffectBase(n, (Actor)localObject);
        }

      }

      if ((hierMesh().chunkName().startsWith(paramString)) && 
        (World.Rnd().nextInt(0, 99) < 50)) {
        Eff3DActor.New((Actor)localObject, null, null, 1.0F, Wreckage.SMOKE, 3.0F);
      }

      Vd.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      if (i < 0)
        ((Wreckage)localObject).setSpeed(Vd);
      else {
        ((Wreck)localObject).setSpeed(Vd);
      }
    }

    arrayOfInt = hierMesh().getSubTrees(paramString + "_D");

    for (m = 0; m < arrayOfInt.length; m++) detachGun(arrayOfInt[m]);
    String str = paramString + "_CAP";
    if (hierMesh().chunkFindCheck(str) >= 0) hierMesh().chunkVisible(str, true);

    for (m = 0; m < arrayOfInt.length; m++) {
      for (int i1 = 3; i1 < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.pnti.length; i1++) {
        try {
          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.pnti[i1] != -1) && (arrayOfInt[m] == hierMesh().chunkByHookNamed(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.pnti[i1])))
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.pnti[i1] = -1;
        } catch (Exception localException) {
          System.out.println("FATAL ERROR: Gear pnti[] cut failed on tt[] = " + i1 + " - " + this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.pnti.length);
        }
      }
    }

    hierMesh().setCurChunk(arrayOfInt[0]);
    hierMesh().getChunkLocObj(tmpLoc1);
    sfxCrash(tmpLoc1.getPoint());

    return true;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor) {
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.dryFriction = 1.0F;
    switch (paramInt1) {
    case 2:
      if (isEnablePostEndAction(0.0D))
        postEndAction(0.0D, paramActor, 2, null);
      return false;
    case 3:
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines.length <= 0) break;
      hitProp(0, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].setEngineStuck(paramActor); break;
    case 4:
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines.length <= 1) break;
      hitProp(1, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[1].setEngineStuck(paramActor); break;
    case 5:
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines.length <= 2) break;
      hitProp(2, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[2].setEngineStuck(paramActor); break;
    case 6:
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines.length <= 3) break;
      hitProp(3, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[3].setEngineStuck(paramActor);
    }

    return cut(partNames[paramInt1]);
  }

  protected int curDMGLevel(int paramInt) {
    return curDMGLevel(partNames[paramInt] + "_D0");
  }

  private int curDMGLevel(String paramString) {
    int i = paramString.length() - 1;
    if (i < 2) return 0;
    int j = (paramString.charAt(i - 2) == '_') && (Character.toUpperCase(paramString.charAt(i - 1)) == 'D') && (Character.isDigit(paramString.charAt(i))) ? 1 : 0;

    if (j == 0)
      return 0;
    HierMesh localHierMesh = hierMesh();
    String str1 = paramString.substring(0, i);
    int k = 0;
    while (k < 10) {
      String str2 = str1 + k;
      if (localHierMesh.chunkFindCheck(str2) < 0) return 0;
      if (localHierMesh.isChunkVisible(str2)) break;
      k++;
    }
    if (k == 10) return 0;
    return k;
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    int i = paramString.length() - 1;
    HierMesh localHierMesh = hierMesh();
    String str2 = paramString;
    int j = (paramString.charAt(i - 2) == '_') && (Character.toUpperCase(paramString.charAt(i - 1)) == 'D') && (Character.isDigit(paramString.charAt(i))) ? 1 : 0;

    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.dryFriction = 1.0F;
    String str1;
    String str3;
    if (j != 0) {
      k = paramString.charAt(i) - '0';
      str1 = paramString.substring(0, i);

      while (!localHierMesh.isChunkVisible(str2)) {
        if (k < 9) k++; else return;
        str2 = str1 + k;
        if (localHierMesh.chunkFindCheck(str2) < 0) return;
      }
      if (k < 9) {
        k++; str3 = str1 + k;
        if (localHierMesh.chunkFindCheck(str3) < 0) str3 = null; 
      }
      else {
        str3 = null;
      }str1 = paramString.substring(0, i - 2);
    } else {
      if (!localHierMesh.isChunkVisible(str2)) return;
      str3 = null;
      str1 = paramString;
    }

    if (str3 == null) {
      if ((!isNet()) || (isNetMaster()))
        nextCUTLevel(paramString, paramInt, paramActor);
      return;
    }
    int k = part(paramString);
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.hit(k);

    localHierMesh.chunkVisible(str2, false);
    localHierMesh.chunkVisible(str3, true);
  }

  protected void nextDMGLevels(int paramInt1, int paramInt2, String paramString, Actor paramActor)
  {
    if (paramInt1 <= 0) return;
    if (paramInt1 > 4)
      paramInt1 = 4;
    if ((this == World.getPlayerAircraft()) && (!World.cur().diffCur.Vulnerability)) return;
    if (isNet()) {
      if ((isNetPlayer()) && (!World.cur().diffCur.Vulnerability)) return;
      if (!Actor.isValid(paramActor)) return;
      int i = part(paramString);
      if (!isNetMaster())
        netPutHits(true, null, paramInt1, paramInt2, i, paramActor);
      netPutHits(false, null, paramInt1, paramInt2, i, paramActor);
      if ((paramActor != this) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && ((paramActor instanceof Aircraft)) && (((Aircraft)paramActor).isNetPlayer()) && (paramInt2 != 0) && (paramInt1 > 3))
      {
        if (paramString.startsWith("Wing")) {
          if (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isSentBuryNote())
            Chat.sendLogRnd(3, "gore_blowwing", (Aircraft)paramActor, this);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setSentWingNote(true);
        }
        else if ((paramString.startsWith("Tail")) && 
          (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isSentBuryNote())) {
          Chat.sendLogRnd(3, "gore_blowtail", (Aircraft)paramActor, this);
        }
      }
    }
    while (paramInt1-- > 0) nextDMGLevel(paramString, paramInt2, paramActor); 
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor)
  {
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.dryFriction = 1.0F;
    debugprintln("Detected NCL in " + paramString + "..");
    if ((this == World.getPlayerAircraft()) && (!World.cur().diffCur.Vulnerability)) return;

    int i = paramString.length() - 1;
    HierMesh localHierMesh = hierMesh();
    String str2 = paramString;
    int j = (paramString.charAt(i - 2) == '_') && (Character.toUpperCase(paramString.charAt(i - 1)) == 'D') && (Character.isDigit(paramString.charAt(i))) ? 1 : 0;
    String str1;
    if (j != 0) {
      str1 = paramString.substring(0, i - 2);
    } else {
      if (!localHierMesh.isChunkVisible(str2)) return;
      str1 = paramString;
    }

    int k = part(paramString);
    if (cutFM(k, paramInt, paramActor)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(k, paramInt, paramActor);
      netPutCut(k, paramInt, paramActor);

      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (this != paramActor) && ((paramActor instanceof Aircraft)) && (((Aircraft)paramActor).isNetPlayer()) && (paramInt == 2) && (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isSentWingNote()) && (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isSentBuryNote()) && ((k == 34) || (k == 37) || (k == 33) || (k == 36)))
      {
        Chat.sendLogRnd(3, "gore_sawwing", (Aircraft)paramActor, this);
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setSentWingNote(true);
      }
    }
  }

  public boolean isEnablePostEndAction(double paramDouble)
  {
    if (this.timePostEndAction < 0L) return true;
    long l = Time.current() + (int)(paramDouble * 1000.0D);
    return l < this.timePostEndAction;
  }

  public void postEndAction(double paramDouble, Actor paramActor, int paramInt, Eff3DActor paramEff3DActor)
  {
    if (!isEnablePostEndAction(paramDouble)) return;

    this.timePostEndAction = (Time.current() + (int)(paramDouble * 1000.0D));
    MsgEndAction.post(0, paramDouble, this, new EndActionParam(paramActor, paramEff3DActor), paramInt);
  }

  public void msgEndAction(Object paramObject, int paramInt) {
    EndActionParam localEndActionParam = (EndActionParam)paramObject;

    if (isAlive()) {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isSentBuryNote())) {
        switch (paramInt) {
        case 2:
          if ((!Actor.isAlive(localEndActionParam.initiator)) || (!(localEndActionParam.initiator instanceof Aircraft)) || (!((Aircraft)localEndActionParam.initiator).isNetPlayer()) || (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) <= 100.0D))
          {
            break;
          }
          Chat.sendLogRnd(1, "gore_blowup", (Aircraft)localEndActionParam.initiator, this); break;
        }

      }

      switch (paramInt) {
      case 2:
        netExplode();
        if (localEndActionParam.smoke != null) {
          Eff3DActor.finish(localEndActionParam.smoke);
          sfxSmokeState(0, 0, false);
        }
        doExplosion();
        for (int i = 0; i < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateEngineStates.length; i++) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(this, i, 1000);
        }
        for (int j = 0; j < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates.length; j++) {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, j, 1000);
        }
        float f1 = 50.0F;
        Actor localActor = null;
        String str = null;
        if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vrel_of_type_ComMaddoxJGPVector3d.lengthSquared() < 70.0D)) {
          f1 = 0.0F;
        } else {
          Point3d localPoint3d1 = new Point3d(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
          Point3d localPoint3d2 = new Point3d(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
          Point3d localPoint3d3 = new Point3d();
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vrel_of_type_ComMaddoxJGPVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vrel_of_type_ComMaddoxJGPVector3d.normalize();
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vrel_of_type_ComMaddoxJGPVector3d.scale(20.0D);
          localPoint3d2.add(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vrel_of_type_ComMaddoxJGPVector3d);
          localActor = Engine.collideEnv().getLine(localPoint3d1, localPoint3d2, false, this, localPoint3d3);
          if ((Actor.isAlive(localActor)) && ((localActor instanceof ActorHMesh))) {
            Mesh localMesh = ((ActorMesh)localActor).mesh();
            Loc localLoc = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs();
            float f4 = localMesh.detectCollisionLine(localLoc, localPoint3d1, localPoint3d2);
            if (f4 >= 0.0F) str = Mesh.collisionChunk(0);
            if (((localActor instanceof BigshipGeneric)) || ((localActor instanceof ShipGeneric))) {
              float f5 = 0.018F * (float)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length();
              if (f5 > 1.0F) f5 = 1.0F;
              if (f5 < 0.1F) f5 = 0.1F;
              float f6 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel;
              if (f6 > 300.0F) f6 = 300.0F;
              f1 = f5 * (50.0F + 0.7F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getWeaponMass() + 0.3F * f6);
            }
          }
        }
        float f2 = 0.5F * f1;
        if (f2 < 50.0F) f2 = 50.0F;
        if (f2 > 300.0F) f2 = 300.0F;
        float f3 = 0.7F * f1;
        if (f3 < 70.0F) f3 = 70.0F;
        if (f3 > 350.0F) f3 = 350.0F;

        MsgExplosion.send(localActor, str, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this, f1, 0.9F * f1, 0, f2);
        MsgExplosion.send(localActor, str, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d, this, 0.5F * f1, 0.25F * f1, 1, f3);
      case 3:
        explode();
      }

      for (int k = 0; k < Math.min(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.crew, 9); k++) {
        if (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotDead(k))
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor, k, 100);
      }
      setDamager(localEndActionParam.initiator, 4);
      World.onActorDied(this, getDamager());
    }
    MsgDestroy.Post(Time.current(), this);
  }

  protected void doExplosion()
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double < Engine.cur.land.HQ_Air(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) + 3.0D) {
      World.cur(); if (World.land().isWater(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double)) {
        Explosions.AirDrop_Water(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
      } else {
        Explosions.AirDrop_Land(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        Loc localLoc = new Loc(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
        World.cur(); localLoc.getPoint().jdField_z_of_type_Double = World.land().HQ(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double);
        Eff3DActor.New(localLoc, 1.0F, "EFFECTS/Smokes/SmokeBoiling.eff", 1200.0F);
        Eff3DActor.New(localLoc, 1.0F, "3DO/Effects/Aircraft/FireGND.eff", 1200.0F);
        Eff3DActor.New(localLoc, 1.0F, "3DO/Effects/Aircraft/BlackHeavyGND.eff", 1200.0F);
      }
    }
    else {
      Explosions.ExplodeFuel(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);
    }
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    int i = (Engine.collideEnv().isDoCollision()) && (World.getPlayerAircraft() != this) ? 1 : 0;

    if ((paramActor instanceof BigshipGeneric)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.bFlatTopGearCheck = true;
      if ((i != 0) && ((Time.tickCounter() + hashCode() & 0xF) != 0))
        paramArrayOfBoolean[0] = false;
    } else if ((i != 0) && ((Time.tickCounter() & 0xF) != 0) && ((paramActor instanceof Aircraft)) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.isUnderDeck())) {
      paramArrayOfBoolean[0] = (!((Aircraft)paramActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.isUnderDeck() ? 1 : false);
    }

    if ((Engine.collideEnv().isDoCollision()) && ((paramActor instanceof Aircraft)) && (Mission.isCoop()) && (paramActor.isNetMirror()))
    {
      if ((isMirrorUnderDeck()) || (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.isUnderDeck()) || (Time.tickCounter() <= 2))
        paramArrayOfBoolean[0] = false;
    }
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if ((isNet()) && (isNetMirror())) return;

    if ((paramActor instanceof ActorCrater)) {
      if (!paramString1.startsWith("Gear"))
        return;
      if ((netUser() != null) && (netUser() == ((ActorCrater)paramActor).netOwner)) return;
    }

    if (this == World.getPlayerAircraft()) {
      TimeSkip.airAction(1);
    }
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.dryFriction = 1.0F;
    int i;
    if (paramString1.startsWith("Pilot")) {
      if ((this == World.getPlayerAircraft()) && (!World.cur().diffCur.Vulnerability)) return;
      i = paramString1.charAt(5) - '1';
      killPilot(this, i);
      return;
    }
    if (paramString1.startsWith("Head")) {
      if ((this == World.getPlayerAircraft()) && (!World.cur().diffCur.Vulnerability)) return;
      i = paramString1.charAt(4) - '1';
      killPilot(this, i);
      return;
    }
    if ((paramActor instanceof Wreckage)) {
      if (paramString1.startsWith("CF_")) return;
      if (paramActor.getOwner() == this) return;
      if ((netUser() != null) && (netUser() == ((Wreckage)paramActor).netOwner)) return;

      paramActor.collide(false);

      nextDMGLevels(3, 0, paramString1, this);
      return;
    }if ((paramActor instanceof Paratrooper)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed(v1); paramActor.getSpeed(Vd);
      Vd.jdField_x_of_type_Double -= v1.jdField_x_of_type_Double; Vd.jdField_y_of_type_Double -= v1.jdField_y_of_type_Double; Vd.jdField_z_of_type_Double -= v1.jdField_z_of_type_Double;
      if (Vd.length() > 30.0D) {
        setDamager(paramActor, 4);
        nextDMGLevels(4, 0, paramString1, paramActor);
      }
      return;
    }
    Object localObject;
    if (((paramActor instanceof RocketryRocket)) && (paramString2.startsWith("Wing")))
    {
      RocketryRocket localRocketryRocket = (RocketryRocket)paramActor;
      Loc localLoc = new Loc();
      localObject = new Point3d();
      Vector3d localVector3d1 = new Vector3d();
      Vector3d localVector3d2 = new Vector3d();

      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(localLoc);
      ((Point3d)localObject).set(paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
      localLoc.transformInv((Point3d)localObject);
      int j = ((Point3d)localObject).jdField_y_of_type_Double > 0.0D ? 1 : 0;

      localVector3d1.set(0.0D, j != 0 ? hierMesh().collisionR() : -hierMesh().collisionR(), 0.0D);

      localLoc.transform(localVector3d1);

      ((Point3d)localObject).set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d);

      ((Point3d)localObject).add(localVector3d1);

      paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(localLoc);
      localLoc.transformInv((Point3d)localObject);

      localVector3d1.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);

      paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.speed(localVector3d2);

      localVector3d1.sub(localVector3d2);

      localLoc.transformInv(localVector3d1);

      localVector3d1.jdField_z_of_type_Double += (j != 0 ? 1.0D : -1.0D) * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().jdField_x_of_type_Double * hierMesh().collisionR();

      if (localVector3d1.jdField_x_of_type_Double * localVector3d1.jdField_x_of_type_Double + localVector3d1.jdField_y_of_type_Double * localVector3d1.jdField_y_of_type_Double < 4.0D) {
        if (((Point3d)localObject).jdField_y_of_type_Double * localVector3d1.jdField_z_of_type_Double > 0.0D)
        {
          localRocketryRocket.sendRocketStateChange('a', this);
        }
        else {
          localRocketryRocket.sendRocketStateChange('b', this);
        }
        return;
      }
      localRocketryRocket.sendRocketStateChange(j != 0 ? 'l' : 'r', this);
    }

    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turnOffCollisions) && (
      (paramString1.startsWith("Wing")) || (paramString1.startsWith("Arone")) || (paramString1.startsWith("Keel")) || (paramString1.startsWith("Rudder")) || (paramString1.startsWith("Stab")) || (paramString1.startsWith("Vator")) || (paramString1.startsWith("Nose")) || (paramString1.startsWith("Tail"))))
    {
      return;
    }

    if (((paramActor instanceof Aircraft)) && (Actor.isValid(paramActor)) && (getArmy() == paramActor.getArmy())) {
      double d = Engine.cur.land.HQ(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double);
      localObject = (Aircraft)paramActor;
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - 2.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.H < d) && (((Aircraft)localObject).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - 2.0F * ((Aircraft)localObject).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.H < d)) {
        setDamagerExclude(paramActor);
      }
    }
    if ((paramString1 != null) && 
      (hierMesh().chunkFindCheck(paramString1) != -1)) {
      hierMesh().setCurChunk(paramString1);
      hierMesh().getChunkLocObj(tmpLoc1);
      Vd.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(Vd);

      Vd.normalize();
      Vd.negate();
      Vd.scale(2000.0F / this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.mass);
      Vd.cross(tmpLoc1.getPoint(), Vd);

      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().jdField_x_of_type_Double += (float)Vd.jdField_x_of_type_Double;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().jdField_y_of_type_Double += (float)Vd.jdField_y_of_type_Double;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().jdField_z_of_type_Double += (float)Vd.jdField_z_of_type_Double;
    }

    setDamager(paramActor, 4);
    nextDMGLevels(4, 0, paramString1, paramActor);
  }

  private void splintersHit(Explosion paramExplosion)
  {
    float[] arrayOfFloat = new float[2];
    float f1 = mesh().collisionR();

    float f2 = 1.0F;

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getTime(Time.current(), tmpLocExp);
    tmpLocExp.get(Pd);
    paramExplosion.computeSplintersHit(Pd, f1, 1.0F, arrayOfFloat);

    Shot localShot = new Shot();
    localShot.chunkName = "CF_D0";
    localShot.initiator = paramExplosion.initiator;
    localShot.tickOffset = Time.tickOffset();

    int i = (int)(arrayOfFloat[0] * 2.0F + 0.5F);

    if (i <= 0)
      return;
    do
    {
      i = (int)(i * 0.5F);
      f2 *= 2.0F;
    }
    while (i > 192);

    for (int k = 0; k < i; k++) {
      tmpP1.set(paramExplosion.p);
      tmpLocExp.get(tmpP2);
      double d = tmpP1.distance(tmpP2);
      tmpP2.add(World.Rnd().nextDouble(-f1, f1), World.Rnd().nextDouble(-f1, f1), World.Rnd().nextDouble(-f1, f1));
      if (d > f1) {
        tmpP1.interpolate(tmpP1, tmpP2, 1.0D - f1 / d);
      }
      tmpP2.interpolate(tmpP1, tmpP2, 2.0D);
      int j = hierMesh().detectCollisionLineMulti(tmpLocExp, tmpP1, tmpP2);
      if (j > 0) {
        localShot.mass = (0.015F * World.Rnd().nextFloat(0.25F, 1.75F) * f2);
        if (World.Rnd().nextFloat() < 0.1F) {
          localShot.mass = (0.015F * World.Rnd().nextFloat(0.1F, 10.0F) * f2);
        }
        float f3 = paramExplosion.power * 10.0F;
        if (localShot.mass > f3) {
          localShot.mass = f3;
        }
        hierMesh(); localShot.p.interpolate(tmpP1, tmpP2, HierMesh.collisionDistMulti(0));

        if (World.Rnd().nextFloat() < 0.333333F)
          localShot.powerType = 2;
        else if (World.Rnd().nextFloat() < 0.5F)
          localShot.powerType = 3;
        else {
          localShot.powerType = 0;
        }
        localShot.v.jdField_x_of_type_Double = (float)(tmpP2.jdField_x_of_type_Double - tmpP1.jdField_x_of_type_Double);
        localShot.v.jdField_y_of_type_Double = (float)(tmpP2.jdField_y_of_type_Double - tmpP1.jdField_y_of_type_Double);
        localShot.v.jdField_z_of_type_Double = (float)(tmpP2.jdField_z_of_type_Double - tmpP1.jdField_z_of_type_Double);
        localShot.v.normalize();
        if (World.Rnd().nextFloat() < 0.02F)
          localShot.v.scale(arrayOfFloat[1] * World.Rnd().nextFloat(0.1F, 10.0F));
        else {
          localShot.v.scale(arrayOfFloat[1] * World.Rnd().nextFloat(0.9F, 1.1F));
        }

        msgShot(localShot);
      }
    }
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (this == World.getPlayerAircraft())
      TimeSkip.airAction(3);
    setExplosion(paramExplosion);
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.dryFriction = 1.0F;
    if ((paramExplosion.power <= 0.0F) || ((paramExplosion.chunkName != null) && (paramExplosion.chunkName.equals(partNames[43]))))
    {
      debugprintln("Splash hit from " + ((paramExplosion.initiator instanceof Aircraft) ? ((Aircraft)paramExplosion.initiator).typedName() : paramExplosion.initiator.name()) + " in " + paramExplosion.chunkName + " is Nill..");

      return;
    }

    if (paramExplosion.powerType == 1) {
      splintersHit(paramExplosion);
      return;
    }

    float f1 = paramExplosion.power;
    float f2 = 0.0F;
    if (paramExplosion.powerType == 0) {
      f1 *= 0.5F;
      f2 = f1;
    }

    if (paramExplosion.chunkName != null) {
      if ((paramExplosion.chunkName.startsWith("Wing")) && (paramExplosion.chunkName.endsWith("_D3"))) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfACM(false);
      }
      if ((paramExplosion.chunkName.startsWith("Wing")) && (paramExplosion.power > 0.017F)) {
        if (paramExplosion.chunkName.startsWith("WingL")) {
          debugprintln("Large Shockwave Hits the Left Wing - Wing Stalls.");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setFMSFX(paramExplosion.initiator, 2, 20);
        }
        if (paramExplosion.chunkName.startsWith("WingR")) {
          debugprintln("Large Shockwave Hits the Right Wing - Wing Stalls.");
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setFMSFX(paramExplosion.initiator, 3, 20);
        }
      }
    }
    float f3;
    if (paramExplosion.chunkName == null)
    {
      f3 = paramExplosion.receivedTNT_1meter(this);
    }
    else {
      f3 = f1;
    }
    if (f3 <= 5.000001E-007F) return;

    debugprintln("Splash hit from " + ((paramExplosion.initiator instanceof Aircraft) ? ((Aircraft)paramExplosion.initiator).typedName() : paramExplosion.initiator.name()) + " in " + paramExplosion.chunkName + " for " + (int)(100.0F * f3 / (0.01F + 3.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.getToughness(part(paramExplosion.chunkName)))) + " % ( " + f3 + " kg)..");

    if (paramExplosion.chunkName == null) {
      f3 /= 0.01F;
    } else {
      if ((paramExplosion.chunkName.endsWith("_D0")) && (!paramExplosion.chunkName.startsWith("Gear"))) {
        if (f3 > 0.01F)
          f3 = 1.0F + (f3 - 0.01F) / this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.getToughness(part(paramExplosion.chunkName));
        else
          f3 /= 0.01F;
      }
      else {
        f3 /= this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.getToughness(part(paramExplosion.chunkName));
      }
      f3 += this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.eAbsorber[part(paramExplosion.chunkName)];
    }
    if (f3 >= 1.0F) setDamager(paramExplosion.initiator, (int)f3);
    int i;
    int j;
    if (paramExplosion.chunkName != null) {
      if ((int)f3 > 0) {
        setDamager(paramExplosion.initiator, 1);
        if (paramExplosion.chunkName.startsWith("Pilot")) {
          killPilot(paramExplosion.initiator, paramExplosion.chunkName.charAt(5) - '1');
          return;
        }
        if (paramExplosion.chunkName.startsWith("Head")) {
          killPilot(paramExplosion.initiator, paramExplosion.chunkName.charAt(4) - '1');
          return;
        }
      }
      nextDMGLevels((int)f3, 1, paramExplosion.chunkName, paramExplosion.initiator);
    }
    else
    {
      for (i = 0; i < partNamesForAll.length; i++) {
        j = World.Rnd().nextInt(partNamesForAll.length);
        if (isChunkAnyDamageVisible(partNamesForAll[j])) {
          nextDMGLevels((int)f3, 1, partNamesForAll[j] + "_D0", paramExplosion.initiator);
          break;
        }
      }
    }
    if (paramExplosion.chunkName != null) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.eAbsorber[part(paramExplosion.chunkName)] = (f3 - (int)f3);
    }

    if (f3 > 8.0F) {
      if (f3 / partNamesForAll.length > 1.5F) {
        for (i = 0; i < partNamesForAll.length; i++)
          if (isChunkAnyDamageVisible(partNamesForAll[i]))
            nextDMGLevels(3, 1, partNamesForAll[i] + "_D0", paramExplosion.initiator);
      }
      else {
        i = (int)f3 / 3 - 1;
        if (i > partNamesForAll.length * 2) i = partNamesForAll.length * 2;
        for (j = 0; j < i; j++) {
          int k = World.Rnd().nextInt(partNamesForAll.length);
          if (isChunkAnyDamageVisible(partNamesForAll[k])) {
            nextDMGLevels(3, 1, partNamesForAll[k] + "_D0", paramExplosion.initiator);
          }
        }
      }

    }

    if ((bWasAlive) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTakenMortalDamage()) && ((getDamager() instanceof Aircraft)) && 
      (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() != getDamager().getArmy()) && (World.Rnd().nextInt(0, 99) < 66)) {
      if (!this.buried) Voice.speakNiceKill((Aircraft)getDamager());
      this.buried = true;
    }

    bWasAlive = true;

    if (f2 > 0.0F)
    {
      MsgExplosionPostVarSet localMsgExplosionPostVarSet = new MsgExplosionPostVarSet(null);
      localMsgExplosionPostVarSet.THIS = this;
      localMsgExplosionPostVarSet.chunkName = paramExplosion.chunkName;
      localMsgExplosionPostVarSet.p.set(paramExplosion.p);
      localMsgExplosionPostVarSet.initiator = paramExplosion.initiator;
      localMsgExplosionPostVarSet.power = f2;
      localMsgExplosionPostVarSet.radius = paramExplosion.radius;
      new MsgAction(false, localMsgExplosionPostVarSet) {
        public void doAction(Object paramObject) { Aircraft.MsgExplosionPostVarSet localMsgExplosionPostVarSet = (Aircraft.MsgExplosionPostVarSet)paramObject;
          if (!Actor.isValid(localMsgExplosionPostVarSet.THIS)) return;

          MsgExplosion.send(localMsgExplosionPostVarSet.THIS, localMsgExplosionPostVarSet.chunkName, localMsgExplosionPostVarSet.p, localMsgExplosionPostVarSet.initiator, 48.0F * localMsgExplosionPostVarSet.power, localMsgExplosionPostVarSet.power, 1, Math.max(localMsgExplosionPostVarSet.radius, 30.0F));
        }
      };
    }
  }

  protected void doRicochet(Shot paramShot)
  {
    v1.jdField_x_of_type_Double *= World.Rnd().nextFloat(0.25F, 1.0F);
    v1.jdField_y_of_type_Double *= World.Rnd().nextFloat(-1.0F, -0.25F);
    v1.jdField_z_of_type_Double *= World.Rnd().nextFloat(-1.0F, -0.25F);
    v1.normalize();
    v1.scale(World.Rnd().nextFloat(10.0F, 600.0F));
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(v1);
    doRicochet(paramShot.p, v1);
    paramShot.power = 0.0F;
  }
  protected void doRicochetBack(Shot paramShot) {
    v1.jdField_x_of_type_Double *= -1.0D;
    v1.jdField_y_of_type_Double *= -1.0D;
    v1.jdField_z_of_type_Double *= -1.0D;
    v1.scale(World.Rnd().nextFloat(0.25F, 1.0F));
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transform(v1);
    doRicochet(paramShot.p, v1);
  }
  protected void doRicochet(Point3d paramPoint3d, Vector3d paramVector3d) {
    BallisticProjectile localBallisticProjectile = new BallisticProjectile(paramPoint3d, paramVector3d, 1.0F);
    Eff3DActor.New(localBallisticProjectile, null, null, 4.0F, "3DO/Effects/Tracers/TrailRicochet.eff", 1.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(tmpLoc1);
    tmpLoc1.transformInv(paramPoint3d);
    Eff3DActor.New(this, null, new Loc(paramPoint3d), 1.0F, "3DO/Effects/Fireworks/12mmRicochet.eff", 0.2F);
    Eff3DActor.New(this, null, new Loc(paramPoint3d), 0.5F, "3DO/Effects/Fireworks/20_Sparks.eff", -1.0F);
  }

  protected void setShot(Shot paramShot)
  {
    if (((this == World.getPlayerAircraft()) || (isNetPlayer())) && (!World.cur().diffCur.Vulnerability))
    {
      paramShot.chunkName = partNames[43];
      paramShot.power = 0.0F;
      paramShot.mass = 0.0F;
    }

    if (bWasAlive) bWasAlive = !this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTakenMortalDamage();

    v1.sub(paramShot.v, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    double d = v1.length();
    paramShot.power = ((float)(paramShot.mass * d * d) * 0.5F);
    if (paramShot.powerType == 0) paramShot.power *= 0.666F;
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.transformInv(v1);
    v1.normalize();

    tmpLoc1.set(paramShot.p);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(tmpLoc2); this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent(tmpLoc3); tmpLoc3.interpolate(tmpLoc2, paramShot.tickOffset);
    tmpLoc1.sub(tmpLoc3);
    tmpLoc1.get(Pd);

    Vd.set(paramShot.v);
    Vd.normalize();
    Vd.scale(0.1000000014901161D);
    tmpP1.set(paramShot.p); tmpP1.sub(Vd);
    Vd.normalize();
    Vd.scale(48.900001525878906D);
    tmpP2.set(paramShot.p); tmpP2.add(Vd);

    tmpBonesHit = hierMesh().detectCollisionLineMulti(tmpLoc3, tmpP1, tmpP2);

    if ((Config.isUSE_RENDER()) && (World.cur().isArcade())) {
      ActorSimpleMesh localActorSimpleMesh = new ActorSimpleMesh("3DO/Arms/MatrixXX/mono.sim");
      localActorSimpleMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(this, null, false);
      tmpOr.setAT0(v1);
      localActorSimpleMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setRel(Pd, tmpOr);
      float f = (float)Math.sqrt(Math.sqrt(paramShot.mass));
      localActorSimpleMesh.mesh().setScaleXYZ(0.75F * f, f, f);
      localActorSimpleMesh.drawing(true);
      localActorSimpleMesh.postDestroy(Time.current() + 30000L);
    }
  }

  protected void setExplosion(Explosion paramExplosion)
  {
    if (((this == World.getPlayerAircraft()) || (isNetPlayer())) && (!World.cur().diffCur.Vulnerability))
    {
      paramExplosion.chunkName = partNames[43];
    }

    if ((paramExplosion.chunkName == null) && (!isChunkAnyDamageVisible("CF"))) {
      paramExplosion.chunkName = partNames[43];
    }

    if (bWasAlive) bWasAlive = !this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTakenMortalDamage();
  }

  protected void msgSndShot(float paramFloat, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (!Config.isUSE_RENDER()) return;
    Actor._tmpPoint.set(paramDouble1, paramDouble2, paramDouble3);
    sfxHit(paramFloat, Actor._tmpPoint);

    if ((isNet()) && 
      (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof RealFlightModel))) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.dryFriction = 1.0F;
      ((RealFlightModel)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).producedShakeLevel = 1.0F;
      float f = 2000.0F * paramFloat / this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.mass;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().add(World.Rnd().nextFloat(-f, f), World.Rnd().nextFloat(-f, f), World.Rnd().nextFloat(-f, f));
    }
  }

  public void msgShot(Shot paramShot)
  {
    if (this == World.getPlayerAircraft()) {
      TimeSkip.airAction(2);
    }

    setShot(paramShot);

    if (!isNet()) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.dryFriction = 1.0F;
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof RealFlightModel))) {
        ((RealFlightModel)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).producedShakeLevel = 1.0F;
      }
      float f1 = 2000.0F * paramShot.mass / this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.mass;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getW().add(World.Rnd().nextFloat(-f1, f1), World.Rnd().nextFloat(-f1, f1), World.Rnd().nextFloat(-f1, f1));
    }

    if (paramShot.chunkName == null) return;
    if (paramShot.chunkName == partNames[43]) {
      if (World.Rnd().nextFloat() < 0.25F) doRicochet(paramShot);
      return;
    }

    if ((paramShot.chunkName.startsWith("Wing")) && ((paramShot.chunkName.endsWith("_D3")) || ((paramShot.chunkName.endsWith("_D2")) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Skill_of_type_Int >= 2))))
    {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setCapableOfACM(false);
    }
    if (((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) && (World.Rnd().nextInt(-1, 8) < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Skill_of_type_Int)) ((Pilot)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).setAsDanger(paramShot.initiator);

    if ((Config.isUSE_RENDER()) && 
      ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof RealFlightModel)))
    {
      Actor._tmpPoint.set(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
      Actor._tmpPoint.sub(paramShot.p);
      msgSndShot(paramShot.mass, Actor._tmpPoint.jdField_x_of_type_Double, Actor._tmpPoint.jdField_y_of_type_Double, Actor._tmpPoint.jdField_z_of_type_Double);
    }

    paramShot.bodyMaterial = 2;
    if (isNetPlayer()) {
      sendMsgSndShot(paramShot);
    }

    if (tmpBonesHit > 0)
    {
      debuggunnery("");
      debuggunnery("New Bullet: E = " + (int)paramShot.power + " [J], M = " + (int)(1000.0F * paramShot.mass) + " [g], Type = (" + sttp(paramShot.powerType) + ")");
      if (paramShot.powerType == 1) tmpBonesHit = Math.min(tmpBonesHit, 2);
      for (j = 0; j < tmpBonesHit; j++) {
        hierMesh(); String str = HierMesh.collisionNameMulti(j, 1);
        if (str.length() == 0) { hierMesh(); str = HierMesh.collisionNameMulti(j, 0); }
        if (paramShot.power > 0.0F) {
          hierMesh(); Pd.interpolate(tmpP1, tmpP2, HierMesh.collisionDistMulti(j));
          tmpLoc3.transformInv(Pd);

          debuggunnery("Hit Bone [" + str + "], E = " + (int)paramShot.power);
          hitBone(str, paramShot, Pd);
          if (!str.startsWith("xx")) {
            hierMesh(); hierMesh(); getEnergyPastArmor(33.333F * (j == tmpBonesHit - 1 ? 0.02F : HierMesh.collisionDistMulti(j + 1) - HierMesh.collisionDistMulti(j)), paramShot);
            if (World.Rnd().nextFloat() < 0.05F) {
              paramShot.power = 0.0F;
              debuggunnery("Inner Ricochet");
            }

          }

        }

      }

    }

    int i = 0;
    for (int j = 0; j < tmpBonesHit; j++)
    {
      hierMesh(); if (HierMesh.collisionNameMulti(j, 1) != null) { hierMesh(); hierMesh(); if (!HierMesh.collisionNameMulti(j, 1).equals(HierMesh.collisionNameMulti(j, 0)))
          continue; }
      i = 1;
    }

    if (i != 0) {
      debuggunnery("[+++ PROCESS OLD +++]");
      hierMesh(); paramShot.chunkName = HierMesh.collisionNameMulti(0, 0);

      if ((paramShot.chunkName.startsWith("WingLOut")) && (World.Rnd().nextInt(0, 99) < 20)) {
        paramShot.chunkName = "AroneL_D0";
      }
      if ((paramShot.chunkName.startsWith("WingROut")) && (World.Rnd().nextInt(0, 99) < 20)) {
        paramShot.chunkName = "AroneR_D0";
      }
      if ((paramShot.chunkName.startsWith("StabL")) && (World.Rnd().nextInt(0, 99) < 45)) {
        paramShot.chunkName = "VatorL_D0";
      }
      if ((paramShot.chunkName.startsWith("StabR")) && (World.Rnd().nextInt(0, 99) < 45)) {
        paramShot.chunkName = "VatorR_D0";
      }
      if ((paramShot.chunkName.startsWith("Keel1")) && (World.Rnd().nextInt(0, 99) < 33)) {
        paramShot.chunkName = "Rudder1_D0";
      }
      if ((paramShot.chunkName.startsWith("Keel2")) && (World.Rnd().nextInt(0, 99) < 33)) {
        paramShot.chunkName = "Rudder2_D0";
      }

      float f2 = paramShot.powerToTNT();
      debugprintln("Bullet hit from " + ((paramShot.initiator instanceof Aircraft) ? ((Aircraft)paramShot.initiator).typedName() : paramShot.initiator.name()) + " in " + paramShot.chunkName + " for " + (int)(100.0F * f2 / (0.01F + 3.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.getToughness(part(paramShot.chunkName)))) + " %..");

      paramShot.bodyMaterial = 2;
      if (((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Pilot)) && (World.Rnd().nextInt(-1, 8) < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Skill_of_type_Int)) ((Pilot)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel).setAsDanger(paramShot.initiator);

      if (f2 <= 5.000001E-007F) return;

      if ((paramShot.chunkName.endsWith("_D0")) && (!paramShot.chunkName.startsWith("Gear"))) {
        if (f2 > 0.01F)
          f2 = 1.0F + (f2 - 0.01F) / this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.getToughness(part(paramShot.chunkName));
        else
          f2 /= 0.01F;
      }
      else {
        f2 /= this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.getToughness(part(paramShot.chunkName));
      }
      f2 += this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.eAbsorber[part(paramShot.chunkName)];
      int k = (int)f2;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.eAbsorber[part(paramShot.chunkName)] = (f2 - k);
      if (k > 0) {
        setDamager(paramShot.initiator, k);
        if (paramShot.chunkName.startsWith("Pilot")) {
          killPilot(paramShot.initiator, paramShot.chunkName.charAt(5) - '1');
          return;
        }
        if (paramShot.chunkName.startsWith("Head")) {
          killPilot(paramShot.initiator, paramShot.chunkName.charAt(4) - '1');
          return;
        }
      }

      nextDMGLevels(k, 2, paramShot.chunkName, paramShot.initiator);
    }

    if ((bWasAlive) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTakenMortalDamage()) && ((getDamager() instanceof Aircraft)) && 
      (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor.getArmy() != getDamager().getArmy()) && (World.Rnd().nextInt(0, 99) < 66)) {
      if (!this.buried) Voice.speakNiceKill((Aircraft)getDamager());
      this.buried = true;
    }

    bWasAlive = true;
  }

  private String sttp(int paramInt) {
    switch (paramInt) { case 2:
      return "AP";
    case 3:
      return "API/APIT";
    case 1:
      return "CUMULATIVE";
    case 0:
      return "HE";
    }
    return null;
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
  }

  protected void hitChunk(String paramString, Shot paramShot)
  {
    if (paramString.lastIndexOf("_") == -1) paramString = paramString + "_D" + chunkDamageVisible(paramString);

    float f = paramShot.powerToTNT();

    if ((paramString.endsWith("_D0")) && (!paramString.startsWith("Gear"))) {
      if (f > 0.01F)
        f = 1.0F + (f - 0.01F) / this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.getToughness(part(paramString));
      else
        f /= 0.01F;
    }
    else {
      f /= this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.getToughness(part(paramString));
    }
    f += this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.eAbsorber[part(paramString)];

    int i = (int)f;
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.eAbsorber[part(paramString)] = (f - i);
    if (i > 0) setDamager(paramShot.initiator, i);
    nextDMGLevels(i, 2, paramString, paramShot.initiator);
  }
  protected void hitFlesh(int paramInt1, Shot paramShot, int paramInt2) {
    int i = (int)(paramShot.power * 0.0035F * World.Rnd().nextFloat(0.5F, 1.5F));
    switch (paramInt2) {
    case 0:
      if (World.Rnd().nextFloat() < 0.05F) return;
      if ((paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T");
      i = (int)(i * 30.0F);
      break;
    case 1:
      break;
    case 2:
      i = (int)(i / 3.0F);
    }

    debuggunnery("*** Pilot " + paramInt1 + " hit for " + i + "% (" + (int)paramShot.power + " J)");
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, paramInt1, i);
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astatePilotStates[paramInt1] > 95) && (paramInt2 == 0))
      debuggunnery("*** Headshot!.");
  }

  protected float getEnergyPastArmor(float paramFloat1, float paramFloat2, Shot paramShot)
  {
    Shot tmp1_0 = paramShot; tmp1_0.power = (float)(tmp1_0.power - (paramShot.powerType == 0 ? 2.0F : 1.0F) * (paramFloat1 * 1700.0F * Math.cos(paramFloat2)));
    return paramShot.power;
  }
  protected float getEnergyPastArmor(float paramFloat, Shot paramShot) {
    paramShot.power -= (paramShot.powerType == 0 ? 2.0F : 1.0F) * (paramFloat * 1700.0F);
    return paramShot.power;
  }
  public static boolean isArmorPenetrated(float paramFloat, Shot paramShot) {
    return paramShot.power > (paramShot.powerType == 0 ? 2.0F : 1.0F) * (paramFloat * 1700.0F);
  }

  protected float getEnergyPastArmor(double paramDouble, float paramFloat, Shot paramShot)
  {
    Shot tmp2_0 = paramShot; tmp2_0.power = (float)(tmp2_0.power - (tmp2_0.powerType == 0 ? 2.0F : 1.0F) * (paramDouble * 1700.0D * Math.cos(paramFloat)));
    return tmp2_0.power;
  }

  protected float getEnergyPastArmor(double paramDouble, Shot paramShot)
  {
    Shot tmp1_0 = paramShot; tmp1_0.power = (float)(tmp1_0.power - (tmp1_0.powerType == 0 ? 2.0F : 1.0F) * (paramDouble * 1700.0D));
    return tmp1_0.power;
  }
  public static boolean isArmorPenetrated(double paramDouble, Shot paramShot) {
    return paramShot.power > (paramShot.powerType == 0 ? 2.0F : 1.0F) * (paramDouble * 1700.0D);
  }

  protected void netHits(int paramInt1, int paramInt2, int paramInt3, Actor paramActor)
  {
    if (isNetMaster()) {
      setDamager(paramActor, paramInt1);
    }
    while (paramInt1-- > 0)
      nextDMGLevel(partNames[paramInt3] + "_D0", paramInt2, paramActor);
  }

  public int curDMGProp(int paramInt) {
    String str = "Prop" + (paramInt + 1) + "_D1";
    HierMesh localHierMesh = hierMesh();
    if (localHierMesh.chunkFindCheck(str) < 0) return 0;
    if (localHierMesh.isChunkVisible(str)) return 1;
    return 0;
  }

  protected void addGun(BulletEmitter paramBulletEmitter, int paramInt)
  {
    if ((this == World.getPlayerAircraft()) && (!World.cur().diffCur.Limited_Ammo)) {
      paramBulletEmitter.loadBullets(-1);
    }
    String str = paramBulletEmitter.getHookName();
    if (str == null) return;

    BulletEmitter[] arrayOfBulletEmitter1 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[paramInt];
    int i;
    if (arrayOfBulletEmitter1 == null) i = 0; else i = arrayOfBulletEmitter1.length;
    BulletEmitter[] arrayOfBulletEmitter2 = new BulletEmitter[i + 1];
    for (int j = 0; j < i; j++) arrayOfBulletEmitter2[j] = arrayOfBulletEmitter1[j];
    arrayOfBulletEmitter2[j] = paramBulletEmitter;
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[paramInt] = arrayOfBulletEmitter2;
    if (paramBulletEmitter.isEnablePause()) this.bGunPodsExist = true; 
  }

  public void detachGun(int paramInt)
  {
    for (int i = 0; i < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length; i++) {
      BulletEmitter[] arrayOfBulletEmitter = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i];
      if (arrayOfBulletEmitter != null)
        for (int j = 0; j < arrayOfBulletEmitter.length; j++)
          arrayOfBulletEmitter[j] = arrayOfBulletEmitter[j].detach(hierMesh(), paramInt);
    }
  }

  public Gun getGunByHookName(String paramString) {
    for (int i = 0; i < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length; i++) {
      BulletEmitter[] arrayOfBulletEmitter = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i];
      if (arrayOfBulletEmitter != null) {
        for (int j = 0; j < arrayOfBulletEmitter.length; j++) {
          if ((arrayOfBulletEmitter[j] instanceof Gun)) {
            Gun localGun = (Gun)arrayOfBulletEmitter[j];
            if (paramString.equals(localGun.getHookName())) {
              return localGun;
            }

          }

        }

      }

    }

    return GunEmpty.get();
  }
  public BulletEmitter getBulletEmitterByHookName(String paramString) {
    for (int i = 0; i < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length; i++) {
      BulletEmitter[] arrayOfBulletEmitter = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i];
      if (arrayOfBulletEmitter != null) {
        for (int j = 0; j < arrayOfBulletEmitter.length; j++)
          if (paramString.equals(arrayOfBulletEmitter[j].getHookName()))
            return arrayOfBulletEmitter[j];
      }
    }
    return GunEmpty.get();
  }
  public static void moveGear(HierMesh paramHierMesh, float paramFloat) {
  }

  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat); } 
  public void forceGear(float paramFloat) { moveGear(paramFloat); } 
  public static void forceGear(Class paramClass, HierMesh paramHierMesh, float paramFloat) {
    try {
      Method localMethod = paramClass.getMethod("moveGear", new Class[] { HierMesh.class, Float.TYPE });

      localMethod.invoke(null, new Object[] { paramHierMesh, new Float(paramFloat) });
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }
  public void moveArrestorHook(float paramFloat) {
  }

  protected void moveWingFold(HierMesh paramHierMesh, float paramFloat) {
  }

  public void moveWingFold(float paramFloat) {
  }

  public void moveCockpitDoor(float paramFloat) {
  }

  protected void moveRudder(float paramFloat) {
  }

  protected void moveElevator(float paramFloat) {
  }

  protected void moveAileron(float paramFloat) {
  }

  protected void moveFlap(float paramFloat) {
  }

  protected void moveBayDoor(float paramFloat) {
  }

  protected void moveAirBrake(float paramFloat) {
  }

  public void moveSteering(float paramFloat) {
  }

  public void moveWheelSink() {
  }

  public void rareAction(float paramFloat, boolean paramBoolean) {
  }

  protected void moveFan(float paramFloat) {
    int i = 0;

    for (int j = 0; j < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum(); j++) {
      if (this.oldProp[j] < 2)
      {
        i = Math.abs((int)(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getw() * 0.06F));
        if (i >= 1) i = 1;
        if ((i != this.oldProp[j]) && 
          (hierMesh().isChunkVisible(Props[j][this.oldProp[j]]))) {
          hierMesh().chunkVisible(Props[j][this.oldProp[j]], false);
          this.oldProp[j] = i;
          hierMesh().chunkVisible(Props[j][i], true);
        }

      }

      if (i == 0) {
        this.propPos[j] = ((this.propPos[j] + 57.299999F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getw() * paramFloat) % 360.0F);
      } else {
        float f = 57.299999F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[j].getw();
        f %= 2880.0F;
        f /= 2880.0F;
        if (f <= 0.5F)
          f *= 2.0F;
        else {
          f = f * 2.0F - 2.0F;
        }
        f *= 1200.0F;
        this.propPos[j] = ((this.propPos[j] + f * paramFloat) % 360.0F);
      }
      hierMesh().chunkSetAngles(Props[j][0], 0.0F, -this.propPos[j], 0.0F);
    }
  }

  public void hitProp(int paramInt1, int paramInt2, Actor paramActor) {
    if ((paramInt1 > this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.getNum() - 1) || (this.oldProp[paramInt1] == 2)) return;
    super.hitProp(paramInt1, paramInt2, paramActor);
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(part("Engine" + (paramInt1 + 1)), paramInt2, paramActor);
    if ((isChunkAnyDamageVisible("Prop" + (paramInt1 + 1))) || (isChunkAnyDamageVisible("PropRot" + (paramInt1 + 1)))) {
      hierMesh().chunkVisible(Props[paramInt1][0], false);
      hierMesh().chunkVisible(Props[paramInt1][1], false);
      hierMesh().chunkVisible(Props[paramInt1][2], true);
    }
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[paramInt1].setFricCoeffT(1.0F);
    this.oldProp[paramInt1] = 2;
  }

  public void updateLLights()
  {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(Actor._tmpLoc);
    if (this.lLight == null) {
      if (Actor._tmpLoc.getX() < 1.0D) return;
      this.lLight = new LightPointWorld[] { null, null, null, null };
      for (i = 0; i < 4; i++) {
        this.lLight[i] = new LightPointWorld();
        this.lLight[i].setColor(0.4941177F, 0.9098039F, 0.9607843F);
        this.lLight[i].setEmit(0.0F, 0.0F);
        try {
          this.lLightHook[i] = new HookNamed(this, "_LandingLight0" + i); } catch (Exception localException) {
        }
      }
      return;
    }

    for (int i = 0; i < 4; i++)
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateLandingLightEffects[i] != null) {
        lLightLoc1.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        this.lLightHook[i].computePos(this, Actor._tmpLoc, lLightLoc1);
        lLightLoc1.get(lLightP1);
        lLightLoc1.set(1000.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        this.lLightHook[i].computePos(this, Actor._tmpLoc, lLightLoc1);
        lLightLoc1.get(lLightP2);
        Engine.land(); if (Landscape.rayHitHQ(lLightP1, lLightP2, lLightPL)) {
          lLightPL.jdField_z_of_type_Double += 1.0D;
          lLightP2.interpolate(lLightP1, lLightPL, 0.95F);
          this.lLight[i].setPos(lLightP2);
          float f1 = (float)lLightP1.distance(lLightPL);
          float f2 = f1 * 0.5F + 30.0F;
          float f3 = 0.5F - 0.5F * f1 / 1000.0F;
          this.lLight[i].setEmit(f3, f2);
        } else {
          this.lLight[i].setEmit(0.0F, 0.0F);
        }
      }
      else if (this.lLight[i].getR() != 0.0F) {
        this.lLight[i].setEmit(0.0F, 0.0F);
      }
  }

  public boolean isUnderWater()
  {
    Point3d localPoint3d = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    if (!Engine.land().isWater(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double)) return false;
    return localPoint3d.jdField_z_of_type_Double < 0.0D;
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);

    if (this == World.getPlayerAircraft()) {
      if (isUnderWater())
        World.doPlayerUnderWater();
      EventLog.flyPlayer(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
      if ((this instanceof TypeBomber)) {
        ((TypeBomber)this).typeBomberUpdate(paramFloat);
      }
    }
    Controls localControls = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls;
    moveFan(paramFloat);

    if (localControls.bHasGearControl) {
      f = localControls.getGear();
      if (Math.abs(this.Gear_ - f) > this.EpsSmooth_) {
        if (!(this instanceof I_16)) {
          if (Math.abs(f - localControls.GearControl) <= this.EpsSmooth_)
            sfxGear(false);
          else {
            sfxGear(true);
          }
        }
        moveGear(this.Gear_ = f);
      }
    }

    if (localControls.bHasArrestorControl) {
      f = localControls.getArrestor();
      if (Math.abs(this.arrestor_ - f) > this.EpsSmooth_) {
        moveArrestorHook(this.arrestor_ = f);
      }
    }
    if (localControls.bHasWingControl) {
      f = localControls.getWing();
      if (Math.abs(this.wingfold_ - f) > this.EpsVerySmooth_) {
        moveWingFold(this.wingfold_ = f);
      }
    }
    if (localControls.bHasCockpitDoorControl) {
      f = localControls.getCockpitDoor();
      if (Math.abs(this.cockpitDoor_ - f) > this.EpsVerySmooth_) {
        moveCockpitDoor(this.cockpitDoor_ = f);
      }
    }
    if (localControls.bHasFlapsControl) {
      f = localControls.getFlap();
      if (Math.abs(this.Flap_ - f) > this.EpsSmooth_) {
        if (Math.abs(f - localControls.FlapsControl) <= this.EpsSmooth_)
          sfxFlaps(false);
        else {
          sfxFlaps(true);
        }
        moveFlap(this.Flap_ = f);
      }
    }

    float f = localControls.getRudder(); if (Math.abs(this.Rudder_ - f) > this.EpsCoarse_) moveRudder(this.Rudder_ = f);
    f = localControls.getElevator(); if (Math.abs(this.Elevator_ - f) > this.EpsCoarse_) moveElevator(this.Elevator_ = f);
    f = localControls.getAileron(); if (Math.abs(this.Aileron_ - f) > this.EpsCoarse_) moveAileron(this.Aileron_ = f);
    f = localControls.getBayDoor(); if (Math.abs(this.BayDoor_ - f) > 0.025F) {
      this.BayDoor_ += 0.025F * (f > this.BayDoor_ ? 2.0F : -1.0F);
      moveBayDoor(this.BayDoor_);
    }

    f = localControls.getAirBrake();
    if (Math.abs(this.AirBrake_ - f) > this.EpsSmooth_) {
      moveAirBrake(this.AirBrake_ = f);
      if (Math.abs(this.AirBrake_ - 0.5F) >= 0.48F) {
        sfxAirBrake();
      }
    }

    f = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.getSteeringAngle(); if (Math.abs(this.Steering_ - f) > this.EpsSmooth_) moveSteering(this.Steering_ = f);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.nearGround())
      moveWheelSink();
  }

  public void setFM(int paramInt, boolean paramBoolean)
  {
    setFM(Property.stringValue(getClass(), "FlightModel", null), paramInt, paramBoolean);
  }

  public void setFM(String paramString, int paramInt, boolean paramBoolean) {
    if ((this instanceof JU_88MSTL)) {
      paramInt = 1;
    }
    switch (paramInt) { case 0:
    default:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel = new Pilot(paramString); break;
    case 1:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel = new RealFlightModel(paramString); break;
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel = new FlightModel(paramString);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AP = new Autopilotage();
    }

    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_actor_of_type_ComMaddoxIl2EngineActor = this;

    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.set(this, (paramBoolean) && (!NetMissionTrack.isPlaying()));
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.setNotMirror((paramBoolean) && (!NetMissionTrack.isPlaying()));

    SectFile localSectFile = FlightModelMain.sectFile(paramString);
    int i = 0;
    String str = localSectFile.get("SOUND", "FeedType", "PNEUMATIC");
    if (str.compareToIgnoreCase("PNEUMATIC") == 0) i = 0;
    else if (str.compareToIgnoreCase("ELECTRIC") == 0) i = 1;
    else if (str.compareToIgnoreCase("HYDRAULIC") == 0) i = 2;
    else
      System.out.println("ERROR: Invalid feed type" + str);
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.set(hierMesh());
    forceGear(getClass(), hierMesh(), 1.0F);
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.computePlaneLandPose(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel);
    forceGear(getClass(), hierMesh(), 0.0F);
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.set(this);
    initSound(localSectFile);
    sfxInit(i);
    interpPut(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel, "FlightModel", Time.current(), null);
  }

  public void checkTurretSkill()
  {
  }

  public void destroy()
  {
    if ((isAlive()) && (Mission.isPlaying()) && (name().charAt(0) != ' ')) {
      Front.checkAircraftCaptured(this);
      World.onActorDied(this, World.remover);
    }

    if (this.lLight != null) for (int i = 0; i < 4; i++) {
        ObjState.destroy(this.lLight[i]);
      }
    if (World.getPlayerAircraft() == this)
      deleteCockpits();
    Wing localWing = getWing();
    if ((Actor.isValid(localWing)) && ((localWing instanceof NetWing)))
      localWing.destroy();
    detachGun(-1);
    super.destroy();
    if (World.getPlayerAircraft() == this)
      World.setPlayerAircraft(null);
    _removeMesh();
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public Aircraft() {
    checkLoadingCountry();

    if (this._loadingCountry == null)
      _setMesh(Property.stringValue(getClass(), "meshName", null));
    else {
      _setMesh(Property.stringValue(getClass(), "meshName_" + this._loadingCountry, null));
    }

    collide(true);
    drawing(true);
    dreamFire(true);
  }

  private void checkLoadingCountry()
  {
    this._loadingCountry = null;
    if (NetAircraft.loadingCountry == null) return;
    Class localClass = getClass();
    if ((Property.value(localClass, "PaintScheme_" + NetAircraft.loadingCountry) != null) && (Property.stringValue(localClass, "meshName_" + NetAircraft.loadingCountry, null) != null))
    {
      this._loadingCountry = NetAircraft.loadingCountry;
    }
  }

  public static String getPropertyMeshDemo(Class paramClass, String paramString) {
    String str1 = "meshNameDemo";
    String str2 = Property.stringValue(paramClass, str1, (String)null);
    if (str2 != null)
      return str2;
    return getPropertyMesh(paramClass, paramString);
  }
  public static String getPropertyMesh(Class paramClass, String paramString) {
    String str1 = "meshName";
    String str2 = null;
    if (paramString != null)
      str2 = Property.stringValue(paramClass, str1 + "_" + paramString, null);
    if (str2 == null)
      str2 = Property.stringValue(paramClass, str1);
    return str2;
  }
  public static PaintScheme getPropertyPaintScheme(Class paramClass, String paramString) {
    String str = "PaintScheme";
    PaintScheme localPaintScheme = null;
    if (paramString != null)
      localPaintScheme = (PaintScheme)Property.value(paramClass, str + "_" + paramString, null);
    if (localPaintScheme == null)
      localPaintScheme = (PaintScheme)Property.value(paramClass, str);
    return localPaintScheme;
  }
  public String typedName() {
    return this.typedName;
  }

  private void correctTypedName() {
    if ((this.typedName != null) && 
      (this.typedName.indexOf('_') >= 0)) {
      StringBuffer localStringBuffer = new StringBuffer();
      int i = this.typedName.length();
      for (int j = 0; j < i; j++) {
        char c = this.typedName.charAt(j);
        if (c != '_')
          localStringBuffer.append(c);
      }
      this.typedName = localStringBuffer.toString();
    }
  }

  public void preparePaintScheme()
  {
    PaintScheme localPaintScheme = getPropertyPaintScheme(getClass(), this._loadingCountry);
    if (localPaintScheme != null) {
      localPaintScheme.prepare(this, this.jdField_bPaintShemeNumberOn_of_type_Boolean);
      this.typedName = localPaintScheme.typedName(this);
      correctTypedName();
    }
  }

  public void preparePaintScheme(int paramInt) {
    PaintScheme localPaintScheme = getPropertyPaintScheme(getClass(), this._loadingCountry);
    if (localPaintScheme != null) {
      localPaintScheme.prepareNum(this, paramInt, this.jdField_bPaintShemeNumberOn_of_type_Boolean);
      this.typedName = localPaintScheme.typedNameNum(this, paramInt);
      correctTypedName();
    }
  }

  public void prepareCamouflage()
  {
    String str = getPropertyMesh(getClass(), this._loadingCountry);
    prepareMeshCamouflage(str, hierMesh());
  }

  public static void prepareMeshCamouflage(String paramString, HierMesh paramHierMesh) {
    prepareMeshCamouflage(paramString, paramHierMesh, null);
  }

  public static void prepareMeshCamouflage(String paramString1, HierMesh paramHierMesh, String paramString2)
  {
    prepareMeshCamouflage(paramString1, paramHierMesh, paramString2, null);
  }

  public static void prepareMeshCamouflage(String paramString1, HierMesh paramHierMesh, String paramString2, Mat[] paramArrayOfMat)
  {
    if (!Config.isUSE_RENDER()) return;
    String str1 = paramString1.substring(0, paramString1.lastIndexOf('/') + 1);
    if (paramString2 == null)
    {
      switch (World.cur().camouflage) { case 0:
        localObject = "summer"; break;
      case 1:
        localObject = "winter"; break;
      case 2:
        localObject = "desert"; break;
      default:
        localObject = "summer";
      }
      if (!existSFSFile(str1 + (String)localObject + "/skin1o.tga")) {
        localObject = "summer";
        if (!existSFSFile(str1 + (String)localObject + "/skin1o.tga"))
          return;
      }
      paramString2 = str1 + (String)localObject;
    }

    Object localObject = { paramString2 + "/skin1o.tga", paramString2 + "/skin1p.tga", paramString2 + "/skin1q.tga" };

    int[] arrayOfInt = new int[4];
    for (int i = 0; i < _skinMat.length; i++) {
      int j = paramHierMesh.materialFind(_skinMat[i]);
      if (j < 0)
        continue;
      Mat localMat1 = paramHierMesh.material(j);
      int k = 0;
      for (int m = 0; m < 4; m++) {
        arrayOfInt[m] = -1;
        if (localMat1.isValidLayer(m)) {
          localMat1.setLayer(m);
          str2 = localMat1.get('\000');
          for (int n = 0; n < 3; n++) {
            if (str2.regionMatches(true, str2.length() - 10, _curSkin[n], 0, 10)) {
              arrayOfInt[m] = n;
              k = 1;
              break;
            }
          }
        }
      }
      if (k == 0)
        continue;
      String str2 = paramString2 + "/" + _skinMat[i] + ".mat";
      Mat localMat2;
      int i1;
      if (FObj.Exist(str2)) {
        localMat2 = (Mat)FObj.Get(str2);
      } else {
        localMat2 = (Mat)localMat1.Clone();
        localMat2.Rename(str2);
        for (i1 = 0; i1 < 4; i1++) {
          if (arrayOfInt[i1] >= 0) {
            localMat2.setLayer(i1);
            localMat2.set('\000', localObject[arrayOfInt[i1]]);
          }
        }
      }
      if (paramArrayOfMat != null) {
        for (i1 = 0; i1 < 4; i1++)
          if (arrayOfInt[i1] >= 0)
            paramArrayOfMat[arrayOfInt[i1]] = localMat2;
      }
      paramHierMesh.materialReplace(_skinMat[i], localMat2);
    }
  }

  public static void prepareMeshSkin(String paramString1, HierMesh paramHierMesh, String paramString2, String paramString3)
  {
    String str = paramString1;
    int i = str.lastIndexOf('/');
    if (i >= 0)
      str = str.substring(0, i + 1) + "summer";
    else
      str = str + "summer";
    try {
      File localFile = new File(HomePath.toFileSystemName(paramString3, 0));
      if (!localFile.isDirectory())
        localFile.mkdir();
    } catch (Exception localException) {
      return;
    }
    if (!BmpUtils.bmp8PalTo4TGA4(paramString2, str, paramString3))
      return;
    if (paramString3 == null)
      return;
    prepareMeshCamouflage(paramString1, paramHierMesh, paramString3, null);
  }

  public static void prepareMeshPilot(HierMesh paramHierMesh, int paramInt, String paramString1, String paramString2)
  {
    prepareMeshPilot(paramHierMesh, paramInt, paramString1, paramString2, null);
  }

  public static void prepareMeshPilot(HierMesh paramHierMesh, int paramInt, String paramString1, String paramString2, Mat[] paramArrayOfMat)
  {
    if (!Config.isUSE_RENDER()) return;
    String str = "Pilot" + (1 + paramInt);
    int i = paramHierMesh.materialFind(str);

    if (i < 0)
      return;
    Mat localMat1;
    if (FObj.Exist(paramString1)) {
      localMat1 = (Mat)FObj.Get(paramString1);
    } else {
      Mat localMat2 = paramHierMesh.material(i);
      localMat1 = (Mat)localMat2.Clone();
      localMat1.Rename(paramString1);
      localMat1.setLayer(0);
      localMat1.set('\000', paramString2);
    }
    if (paramArrayOfMat != null)
      paramArrayOfMat[0] = localMat1;
    paramHierMesh.materialReplace(str, localMat1);
  }

  public static void prepareMeshNoseart(HierMesh paramHierMesh, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    prepareMeshNoseart(paramHierMesh, paramString1, paramString2, paramString3, paramString4, null);
  }

  public static void prepareMeshNoseart(HierMesh paramHierMesh, String paramString1, String paramString2, String paramString3, String paramString4, Mat[] paramArrayOfMat)
  {
    if (!Config.isUSE_RENDER()) return;
    String str = "Overlay9";
    int i = paramHierMesh.materialFind(str);
    if (i < 0)
      return;
    Mat localMat1;
    Mat localMat2;
    if (FObj.Exist(paramString1)) {
      localMat1 = (Mat)FObj.Get(paramString1);
    } else {
      localMat2 = paramHierMesh.material(i);
      localMat1 = (Mat)localMat2.Clone();
      localMat1.Rename(paramString1);
      localMat1.setLayer(0);
      localMat1.set('\000', paramString3);
    }
    if (paramArrayOfMat != null)
      paramArrayOfMat[0] = localMat1;
    paramHierMesh.materialReplace(str, localMat1);

    str = "OverlayA";
    i = paramHierMesh.materialFind(str);
    if (i < 0)
      return;
    if (FObj.Exist(paramString2)) {
      localMat1 = (Mat)FObj.Get(paramString2);
    } else {
      localMat2 = paramHierMesh.material(i);
      localMat1 = (Mat)localMat2.Clone();
      localMat1.Rename(paramString2);
      localMat1.setLayer(0);
      localMat1.set('\000', paramString4);
    }
    if (paramArrayOfMat != null)
      paramArrayOfMat[1] = localMat1;
    paramHierMesh.materialReplace(str, localMat1);
  }

  private static boolean existSFSFile(String paramString) {
    try {
      SFSInputStream localSFSInputStream = new SFSInputStream(paramString);
      localSFSInputStream.close();
      return true; } catch (Exception localException) {
    }
    return false;
  }

  public double getSpeed(Vector3d paramVector3d)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel == null) {
      if (paramVector3d != null) paramVector3d.set(0.0D, 0.0D, 0.0D);
      return 0.0D;
    }
    if (paramVector3d != null) paramVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
    return this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length();
  }

  public void setSpeed(Vector3d paramVector3d)
  {
    super.setSpeed(paramVector3d);

    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.set(paramVector3d);
  }

  public void setOnGround(Point3d paramPoint3d, Orient paramOrient, Vector3d paramVector3d) {
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.setLanded();
    forceGear(getClass(), hierMesh(), this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear());
    if ((paramPoint3d != null) && (paramOrient != null)) {
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(paramPoint3d, paramOrient);
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
    }
    if (paramVector3d != null)
      setSpeed(paramVector3d);
  }

  public void load(SectFile paramSectFile, String paramString, int paramInt1, NetChannel paramNetChannel, int paramInt2)
    throws Exception
  {
    if (this == World.getPlayerAircraft()) {
      setFM(1, true);
      World.setPlayerFM();
    } else if (paramNetChannel != null) {
      setFM(2, false);
    } else {
      setFM(0, true);
    }

    if (paramSectFile.exist(paramString, "Skill" + paramInt1))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setSkill(paramSectFile.get(paramString, "Skill" + paramInt1, 1));
    else {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setSkill(paramSectFile.get(paramString, "Skill", 1));
    }
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.fuel = (paramSectFile.get(paramString, "Fuel", 100.0F, 0.0F, 100.0F) * 0.01F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_M_of_type_ComMaddoxIl2FmMass.maxFuel);

    if (paramSectFile.exist(paramString, "numberOn" + paramInt1)) {
      this.jdField_bPaintShemeNumberOn_of_type_Boolean = (paramSectFile.get(paramString, "numberOn" + paramInt1, 1, 0, 1) == 1);
    }
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsEnableToBailout = (paramSectFile.get(paramString, "Parachute", 1, 0, 1) == 1);

    if (Mission.isServer()) {
      createNetObject(null, 0);
    }
    else if (paramNetChannel != null) {
      createNetObject(paramNetChannel, paramInt2);
    }

    if (this.jdField_net_of_type_ComMaddoxIl2EngineActorNet != null) {
      ((NetAircraft.AircraftNet)this.jdField_net_of_type_ComMaddoxIl2EngineActorNet).netName = name();
      ((NetAircraft.AircraftNet)this.jdField_net_of_type_ComMaddoxIl2EngineActorNet).netUser = null;
    }

    String str1 = paramString + "_weapons";
    int i = paramSectFile.sectionIndex(str1);
    if (i >= 0) {
      int j = paramSectFile.vars(i);

      for (int k = 0; k < j; k++) {
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
        int n = localNumberTokenizer.next(9, 0, 19);
        String str2 = localNumberTokenizer.next();
        String str3 = localNumberTokenizer.next();
        Class localClass = ObjIO.classForName("weapons." + str3);
        Object localObject = localClass.newInstance();
        if ((localObject instanceof BulletEmitter)) {
          BulletEmitter localBulletEmitter = (BulletEmitter)localObject;
          localBulletEmitter.set(this, str2, dumpName(str2));
          int m = localNumberTokenizer.next(-12345);
          if (m == -12345) localBulletEmitter.loadBullets(); else localBulletEmitter._loadBullets(m);
          addGun(localBulletEmitter, n);
        }
      }
    } else {
      this.jdField_thisWeaponsName_of_type_JavaLangString = paramSectFile.get(paramString, "weapons", (String)null);
      if (this.jdField_thisWeaponsName_of_type_JavaLangString != null) {
        weaponsLoad(this, this.jdField_thisWeaponsName_of_type_JavaLangString);
      }
    }

    if (this == World.getPlayerAircraft())
      createCockpits();
    onAircraftLoaded();
  }

  private static String dumpName(String paramString) {
    int i = paramString.length() - 1;
    for (; i >= 0; i--) if (!Character.isDigit(paramString.charAt(i)))
        break; i++;
    return paramString.substring(0, i) + "Dump" + paramString.substring(i);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    for (int i = 0; i < 2; i++) {
      paramArrayOfFloat[i] = ((paramArrayOfFloat[i] + 3600.0F) % 360.0F);
      if (paramArrayOfFloat[i] <= 180.0F) continue; paramArrayOfFloat[i] -= 360.0F;
    }
    paramArrayOfFloat[2] = 0.0F;
    return true;
  }

  public int WeaponsMask() {
    return -1;
  }
  public int HitbyMask() { return this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() < 2.0D ? -1 : -25;
  }

  public int chooseBulletType(BulletProperties[] paramArrayOfBulletProperties)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTakenMortalDamage()) {
      return -1;
    }

    if (paramArrayOfBulletProperties.length == 1) {
      return 0;
    }

    if (paramArrayOfBulletProperties.length <= 0) {
      return -1;
    }

    if (paramArrayOfBulletProperties[0].power <= 0.0F)
    {
      return 1;
    }
    if (paramArrayOfBulletProperties[1].power <= 0.0F)
    {
      return 0;
    }

    if (paramArrayOfBulletProperties[0].powerType == 1)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].powerType == 1)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 0)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].powerType == 0)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 2)
    {
      return 1;
    }

    return 0;
  }

  public int chooseShotpoint(BulletProperties paramBulletProperties) {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTakenMortalDamage()) {
      return -1;
    }
    return 0;
  }

  public boolean getShotpointOffset(int paramInt, Point3d paramPoint3d) {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isTakenMortalDamage()) {
      return false;
    }

    if (paramInt != 0) {
      return false;
    }

    if (paramPoint3d != null) {
      paramPoint3d.set(0.0D, 0.0D, 0.0D);
    }
    return true;
  }

  public float AttackMaxDistance() {
    return 1500.0F;
  }

  private static int[] getSwTbl(int paramInt)
  {
    if (paramInt < 0) paramInt = -paramInt;
    int i = paramInt % 16 + 11;
    int j = paramInt % Finger.kTable.length;
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

  public static void weapons(Class paramClass)
  {
    try {
      int i = Finger.Int("ce" + paramClass.getName() + "vd");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(new KryptoInputFilter(new SFSInputStream(Finger.LongFN(0L, "cod/" + Finger.incInt(i, "adt"))), getSwTbl(i))));

      ArrayList localArrayList = weaponsListProperty(paramClass);
      HashMapInt localHashMapInt = weaponsMapProperty(paramClass);
      while (true) {
        String str1 = localBufferedReader.readLine();
        if (str1 == null)
          break;
        StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
        int j = localStringTokenizer.countTokens() - 1;
        String str2 = localStringTokenizer.nextToken();
        _WeaponSlot[] arrayOf_WeaponSlot = new _WeaponSlot[j];
        for (int k = 0; k < j; k++) {
          String str3 = localStringTokenizer.nextToken();
          if ((str3 != null) && (str3.length() > 3)) {
            NumberTokenizer localNumberTokenizer = new NumberTokenizer(str3);
            arrayOf_WeaponSlot[k] = new _WeaponSlot(localNumberTokenizer.next(0), localNumberTokenizer.next(null), localNumberTokenizer.next(-12345));
          }
        }
        localArrayList.add(str2);
        localHashMapInt.put(Finger.Int(str2), arrayOf_WeaponSlot);
      }
      localBufferedReader.close();
    }
    catch (Exception localException)
    {
    }
  }

  public long finger(long paramLong)
  {
    Class localClass = getClass();
    paramLong = FlightModelMain.finger(paramLong, Property.stringValue(localClass, "FlightModel", null));
    paramLong = Finger.incLong(paramLong, Property.stringValue(localClass, "meshName", null));
    Object localObject1 = Property.value(localClass, "cockpitClass", null);
    if (localObject1 != null) {
      if ((localObject1 instanceof Class)) {
        paramLong = Finger.incLong(paramLong, ((Class)localObject1).getName());
      } else {
        Class[] arrayOfClass = (Class[])localObject1;
        for (int j = 0; j < arrayOfClass.length; j++) {
          paramLong = Finger.incLong(paramLong, arrayOfClass[j].getName());
        }
      }
    }
    for (int i = 0; i < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length; i++) {
      BulletEmitter[] arrayOfBulletEmitter = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i];
      if (arrayOfBulletEmitter != null) {
        for (int k = 0; k < arrayOfBulletEmitter.length; k++) {
          BulletEmitter localBulletEmitter = arrayOfBulletEmitter[k];
          paramLong = Finger.incLong(paramLong, Property.intValue(localBulletEmitter, "_count", 0));
          Object localObject2;
          Object localObject3;
          if ((localBulletEmitter instanceof Gun)) {
            localObject2 = ((Gun)localBulletEmitter).prop;
            paramLong = Finger.incLong(paramLong, ((GunProperties)localObject2).shotFreq);
            paramLong = Finger.incLong(paramLong, ((GunProperties)localObject2).shotFreqDeviation);
            paramLong = Finger.incLong(paramLong, ((GunProperties)localObject2).maxDeltaAngle);
            paramLong = Finger.incLong(paramLong, ((GunProperties)localObject2).bullets);
            localObject3 = ((GunProperties)localObject2).bullet;
            if (localObject3 != null) {
              for (int m = 0; m < localObject3.length; m++) {
                paramLong = Finger.incLong(paramLong, localObject3[m].massa);
                paramLong = Finger.incLong(paramLong, localObject3[m].kalibr);
                paramLong = Finger.incLong(paramLong, localObject3[m].speed);
                paramLong = Finger.incLong(paramLong, localObject3[m].cumulativePower);
                paramLong = Finger.incLong(paramLong, localObject3[m].power);
                paramLong = Finger.incLong(paramLong, localObject3[m].powerType);
                paramLong = Finger.incLong(paramLong, localObject3[m].powerRadius);
                paramLong = Finger.incLong(paramLong, localObject3[m].timeLife);
              }
            }
          }
          else if ((localBulletEmitter instanceof RocketGun)) {
            localObject2 = (RocketGun)localBulletEmitter;
            localObject3 = (Class)Property.value(localObject2.getClass(), "bulletClass", null);
            paramLong = Finger.incLong(paramLong, Property.intValue(localObject2.getClass(), "bullets", 1));
            paramLong = Finger.incLong(paramLong, Property.floatValue(localObject2.getClass(), "shotFreq", 0.5F));
            if (localObject3 != null) {
              paramLong = Finger.incLong(paramLong, Property.floatValue((Class)localObject3, "radius", 1.0F));
              paramLong = Finger.incLong(paramLong, Property.floatValue((Class)localObject3, "timeLife", 1.0F));
              paramLong = Finger.incLong(paramLong, Property.floatValue((Class)localObject3, "timeFire", 1.0F));
              paramLong = Finger.incLong(paramLong, Property.floatValue((Class)localObject3, "force", 1.0F));
              paramLong = Finger.incLong(paramLong, Property.floatValue((Class)localObject3, "power", 1.0F));
              paramLong = Finger.incLong(paramLong, Property.intValue((Class)localObject3, "powerType", 1));
              paramLong = Finger.incLong(paramLong, Property.floatValue((Class)localObject3, "kalibr", 1.0F));
              paramLong = Finger.incLong(paramLong, Property.floatValue((Class)localObject3, "massa", 1.0F));
              paramLong = Finger.incLong(paramLong, Property.floatValue((Class)localObject3, "massaEnd", 1.0F));
            }
          } else if ((localBulletEmitter instanceof BombGun)) {
            localObject2 = (BombGun)localBulletEmitter;
            localObject3 = (Class)Property.value(localObject2.getClass(), "bulletClass", null);
            paramLong = Finger.incLong(paramLong, Property.intValue(localObject2.getClass(), "bullets", 1));
            paramLong = Finger.incLong(paramLong, Property.floatValue(localObject2.getClass(), "shotFreq", 0.5F));
            if (localObject3 != null) {
              paramLong = Finger.incLong(paramLong, Property.floatValue((Class)localObject3, "radius", 1.0F));
              paramLong = Finger.incLong(paramLong, Property.floatValue((Class)localObject3, "power", 1.0F));
              paramLong = Finger.incLong(paramLong, Property.intValue((Class)localObject3, "powerType", 1));
              paramLong = Finger.incLong(paramLong, Property.floatValue((Class)localObject3, "kalibr", 1.0F));
              paramLong = Finger.incLong(paramLong, Property.floatValue((Class)localObject3, "massa", 1.0F));
            }
          }
        }
      }
    }
    return paramLong;
  }

  protected static void weaponTriggersRegister(Class paramClass, int[] paramArrayOfInt) {
    Property.set(paramClass, "weaponTriggers", paramArrayOfInt);
  }
  public static int[] getWeaponTriggersRegistered(Class paramClass) {
    return (int[])Property.value(paramClass, "weaponTriggers", null);
  }
  protected static void weaponHooksRegister(Class paramClass, String[] paramArrayOfString) {
    if (paramArrayOfString.length != getWeaponTriggersRegistered(paramClass).length)
      throw new RuntimeException("Sizeof 'weaponHooks' != sizeof 'weaponTriggers'");
    Property.set(paramClass, "weaponHooks", paramArrayOfString);
  }
  public static String[] getWeaponHooksRegistered(Class paramClass) {
    return (String[])Property.value(paramClass, "weaponHooks", null);
  }

  protected static void weaponsRegister(Class paramClass, String paramString, String[] paramArrayOfString)
  {
  }

  protected static void weaponsUnRegister(Class paramClass, String paramString)
  {
    ArrayList localArrayList = weaponsListProperty(paramClass);
    HashMapInt localHashMapInt = weaponsMapProperty(paramClass);
    int i = localArrayList.indexOf(paramString);
    if (i < 0)
      return;
    localArrayList.remove(i);
    localHashMapInt.remove(Finger.Int(paramString));
  }
  public static String[] getWeaponsRegistered(Class paramClass) {
    ArrayList localArrayList = weaponsListProperty(paramClass);
    HashMapInt localHashMapInt = weaponsMapProperty(paramClass);
    String[] arrayOfString = new String[localArrayList.size()];
    for (int i = 0; i < arrayOfString.length; i++)
      arrayOfString[i] = ((String)localArrayList.get(i));
    return arrayOfString;
  }
  public static _WeaponSlot[] getWeaponSlotsRegistered(Class paramClass, String paramString) {
    HashMapInt localHashMapInt = weaponsMapProperty(paramClass);
    return (_WeaponSlot[])localHashMapInt.get(Finger.Int(paramString));
  }
  public static boolean weaponsExist(Class paramClass, String paramString) {
    Object localObject = Property.value(paramClass, "weaponsMap", null);
    if (localObject == null)
      return false;
    HashMapInt localHashMapInt = (HashMapInt)localObject;
    int i = Finger.Int(paramString);
    return localHashMapInt.containsKey(i);
  }
  protected void weaponsLoad(String paramString) throws Exception {
    weaponsLoad(this, paramString);
  }
  protected static void weaponsLoad(Aircraft paramAircraft, String paramString) throws Exception {
    Class localClass = paramAircraft.getClass();
    HashMapInt localHashMapInt = weaponsMapProperty(localClass);
    int i = Finger.Int(paramString);
    if (!localHashMapInt.containsKey(i))
      throw new RuntimeException("Weapon set '" + paramString + "' not registered in " + ObjIO.classGetName(localClass));
    weaponsLoad(paramAircraft, i, localHashMapInt);
  }
  protected static void weaponsLoad(Aircraft paramAircraft, int paramInt) throws Exception {
    HashMapInt localHashMapInt = weaponsMapProperty(paramAircraft.getClass());
    if (!localHashMapInt.containsKey(paramInt))
      throw new RuntimeException("Weapon set '" + paramInt + "' not registered in " + ObjIO.classGetName(paramAircraft.getClass()));
    weaponsLoad(paramAircraft, paramInt, localHashMapInt);
  }
  protected static void weaponsLoad(Aircraft paramAircraft, int paramInt, HashMapInt paramHashMapInt) throws Exception {
    String[] arrayOfString = getWeaponHooksRegistered(paramAircraft.getClass());
    _WeaponSlot[] arrayOf_WeaponSlot = (_WeaponSlot[])paramHashMapInt.get(paramInt);
    for (int i = 0; i < arrayOfString.length; i++)
      if (arrayOf_WeaponSlot[i] != null)
        if (paramAircraft.mesh().hookFind(arrayOfString[i]) != -1) {
          BulletEmitter localBulletEmitter = (BulletEmitter)arrayOf_WeaponSlot[i].clazz.newInstance();
          localBulletEmitter.set(paramAircraft, arrayOfString[i], dumpName(arrayOfString[i]));
          if ((paramAircraft.isNet()) && (paramAircraft.isNetMirror())) {
            if (!World.cur().diffCur.Limited_Ammo)
              localBulletEmitter.loadBullets(-1);
            else if ((arrayOf_WeaponSlot[i].trigger == 2) || (arrayOf_WeaponSlot[i].trigger == 3) || (arrayOf_WeaponSlot[i].trigger >= 10)) {
              if (arrayOf_WeaponSlot[i].bullets == -12345) localBulletEmitter.loadBullets(); else
                localBulletEmitter._loadBullets(arrayOf_WeaponSlot[i].bullets);
            }
            else localBulletEmitter.loadBullets(-1);

          }
          else if (arrayOf_WeaponSlot[i].bullets == -12345) localBulletEmitter.loadBullets(); else {
            localBulletEmitter.loadBullets(arrayOf_WeaponSlot[i].bullets);
          }
          paramAircraft.addGun(localBulletEmitter, arrayOf_WeaponSlot[i].trigger);
          Property.set(localBulletEmitter, "_count", arrayOf_WeaponSlot[i].bullets);

          switch (arrayOf_WeaponSlot[i].trigger) {
          case 0:
            if (!(localBulletEmitter instanceof MGunAircraftGeneric)) continue;
            if (World.getPlayerAircraft() == paramAircraft) {
              ((MGunAircraftGeneric)localBulletEmitter).setConvDistance(World.cur().userCoverMashineGun, Property.floatValue(paramAircraft.getClass(), "LOSElevation", 0.75F)); continue;
            }

            if ((paramAircraft.isNet()) && (paramAircraft.isNetPlayer())) {
              ((MGunAircraftGeneric)localBulletEmitter).setConvDistance(400.0F, Property.floatValue(paramAircraft.getClass(), "LOSElevation", 0.75F)); continue;
            }

            ((MGunAircraftGeneric)localBulletEmitter).setConvDistance(400.0F, 0.0F);

            break;
          case 1:
            if (!(localBulletEmitter instanceof MGunAircraftGeneric)) continue;
            if (World.getPlayerAircraft() == paramAircraft) {
              ((MGunAircraftGeneric)localBulletEmitter).setConvDistance(World.cur().userCoverCannon, Property.floatValue(paramAircraft.getClass(), "LOSElevation", 0.75F)); continue;
            }

            if ((paramAircraft.isNet()) && (paramAircraft.isNetPlayer())) {
              ((MGunAircraftGeneric)localBulletEmitter).setConvDistance(400.0F, Property.floatValue(paramAircraft.getClass(), "LOSElevation", 0.75F)); continue;
            }

            ((MGunAircraftGeneric)localBulletEmitter).setConvDistance(400.0F, 0.0F);

            break;
          case 2:
            if (!(localBulletEmitter instanceof RocketGun)) continue;
            if (World.getPlayerAircraft() == paramAircraft) {
              ((RocketGun)localBulletEmitter).setRocketTimeLife(World.cur().userRocketDelay);
              ((RocketGun)localBulletEmitter).setConvDistance(World.cur().userCoverRocket, Property.floatValue(paramAircraft.getClass(), "LOSElevation", 0.75F) - 2.81F); continue;
            }

            if ((paramAircraft.isNet()) && (paramAircraft.isNetPlayer())) {
              ((RocketGun)localBulletEmitter).setConvDistance(400.0F, Property.floatValue(paramAircraft.getClass(), "LOSElevation", 0.75F) - 2.81F); continue;
            }

            if ((paramAircraft instanceof TypeFighter)) {
              ((RocketGun)localBulletEmitter).setConvDistance(400.0F, -1.8F); continue;
            }
            if (((RocketGun)localBulletEmitter).bulletMassa() > 10.0F) {
              if ((paramAircraft instanceof IL_2)) { ((RocketGun)localBulletEmitter).setConvDistance(400.0F, -2.0F); continue; }
              ((RocketGun)localBulletEmitter).setConvDistance(400.0F, -1.65F); continue;
            }

            if ((paramAircraft instanceof IL_2)) { ((RocketGun)localBulletEmitter).setConvDistance(400.0F, -2.1F); continue; }
            ((RocketGun)localBulletEmitter).setConvDistance(400.0F, -1.9F);

            break;
          case 3:
            if ((!(localBulletEmitter instanceof BombGun)) || 
              (World.getPlayerAircraft() != paramAircraft)) continue;
            ((BombGun)localBulletEmitter).setBombDelay(World.cur().userBombDelay); break;
          default:
            break;
          }
        }
        else {
          System.err.println("Hook '" + arrayOfString[i] + "' NOT found in mesh of " + paramAircraft.getClass());
        }
  }

  private static ArrayList weaponsListProperty(Class paramClass)
  {
    Object localObject = Property.value(paramClass, "weaponsList", null);
    if (localObject != null)
      return (ArrayList)localObject;
    localObject = new ArrayList();
    Property.set(paramClass, "weaponsList", localObject);
    return (ArrayList)(ArrayList)localObject;
  }
  private static HashMapInt weaponsMapProperty(Class paramClass) {
    Object localObject = Property.value(paramClass, "weaponsMap", null);
    if (localObject != null)
      return (HashMapInt)localObject;
    localObject = new HashMapInt();
    Property.set(paramClass, "weaponsMap", localObject);
    return (HashMapInt)(HashMapInt)localObject;
  }

  public void hideWingWeapons(boolean paramBoolean) {
    for (int i = 0; i < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length; i++) {
      BulletEmitter[] arrayOfBulletEmitter = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i];
      if (arrayOfBulletEmitter != null)
        for (int j = 0; j < arrayOfBulletEmitter.length; j++)
          if ((arrayOfBulletEmitter[j] instanceof BombGun))
            ((BombGun)arrayOfBulletEmitter[j]).hide(paramBoolean);
          else if ((arrayOfBulletEmitter[j] instanceof RocketGun))
            ((RocketGun)arrayOfBulletEmitter[j]).hide(paramBoolean);
          else if ((arrayOfBulletEmitter[j] instanceof Pylon))
            ((Pylon)arrayOfBulletEmitter[j]).drawing(!paramBoolean);
    }
  }

  public void createCockpits()
  {
    if (!Config.isUSE_RENDER()) return;
    deleteCockpits();
    Object localObject1 = Property.value(getClass(), "cockpitClass");
    if (localObject1 == null) return;
    Cockpit._newAircraft = this;
    Object localObject2;
    if ((localObject1 instanceof Class)) {
      localObject2 = (Class)localObject1;
      try {
        Main3D.cur3D().cockpits = new Cockpit[1];
        Main3D.cur3D().cockpits[0] = ((Cockpit)((Class)localObject2).newInstance());
        Main3D.cur3D().cockpitCur = Main3D.cur3D().cockpits[0];
      } catch (Exception localException1) {
        System.out.println(localException1.getMessage());
        localException1.printStackTrace();
      }
    } else {
      localObject2 = (Class[])localObject1;
      try {
        Main3D.cur3D().cockpits = new Cockpit[localObject2.length];
        for (int i = 0; i < localObject2.length; i++)
          Main3D.cur3D().cockpits[i] = ((Cockpit)localObject2[i].newInstance());
        Main3D.cur3D().cockpitCur = Main3D.cur3D().cockpits[0];
      } catch (Exception localException2) {
        System.out.println(localException2.getMessage());
        localException2.printStackTrace();
      }
    }
    Cockpit._newAircraft = null;
  }

  protected void deleteCockpits() {
    if (!Config.isUSE_RENDER()) return;
    Cockpit[] arrayOfCockpit = Main3D.cur3D().cockpits;
    if (arrayOfCockpit == null) return;
    for (int i = 0; i < arrayOfCockpit.length; i++) {
      arrayOfCockpit[i].destroy();
      arrayOfCockpit[i] = null;
    }
    Main3D.cur3D().cockpits = null;
    Main3D.cur3D().cockpitCur = null;
  }

  private void explode()
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel != null) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel;
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel != null) this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel;
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Wingman_of_type_ComMaddoxIl2FmFlightModel = null; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Leader_of_type_ComMaddoxIl2FmFlightModel = null;
    HierMesh localHierMesh = hierMesh();
    int j = -1;
    float f = 30.0F;
    for (int i = 9; i >= 0; i--)
      if ((j = localHierMesh.chunkFindCheck("CF_D" + i)) >= 0)
        break;
    int[] arrayOfInt2 = hideSubTrees("");
    if (arrayOfInt2 == null) return;
    int[] arrayOfInt1 = arrayOfInt2;
    arrayOfInt2 = new int[arrayOfInt1.length + 1];
    for (i = 0; i < arrayOfInt1.length; i++) arrayOfInt2[i] = arrayOfInt1[i];
    arrayOfInt2[i] = j;

    for (i = 0; i < arrayOfInt2.length; i++) {
      Wreckage localWreckage = new Wreckage(this, arrayOfInt2[i]);
      if (World.Rnd().nextInt(0, 99) < 20) {
        Eff3DActor.New(localWreckage, null, null, 1.0F, Wreckage.FIRE, 2.5F);
        if (World.Rnd().nextInt(0, 99) < 50) {
          Eff3DActor.New(localWreckage, null, null, 1.0F, Wreckage.SMOKE_EXPLODE, 3.0F);
        }
      }
      getSpeed(Vd);

      Vd.jdField_x_of_type_Double += f * (World.Rnd().nextDouble(0.0D, 1.0D) - 0.5D);
      Vd.jdField_y_of_type_Double += f * (World.Rnd().nextDouble(0.0D, 1.0D) - 0.5D);
      Vd.jdField_z_of_type_Double += f * (World.Rnd().nextDouble(0.0D, 1.0D) - 0.5D);

      localWreckage.setSpeed(Vd);
    }
  }

  public int aircNumber()
  {
    Wing localWing = (Wing)getOwner();
    if (localWing == null) return -1;
    return localWing.aircReady();
  }

  public int aircIndex()
  {
    Wing localWing = (Wing)getOwner();
    if (localWing == null) return -1;
    return localWing.aircIndex(this);
  }

  public boolean isInPlayerWing()
  {
    if (!Actor.isValid(World.getPlayerAircraft())) return false;
    return getWing() == World.getPlayerAircraft().getWing();
  }

  public boolean isInPlayerSquadron()
  {
    if (!Actor.isValid(World.getPlayerAircraft())) return false;
    return getSquadron() == World.getPlayerAircraft().getSquadron();
  }

  public boolean isInPlayerRegiment()
  {
    return getRegiment() == World.getPlayerRegiment();
  }

  public boolean isChunkAnyDamageVisible(String paramString)
  {
    if (paramString.lastIndexOf("_") == -1) paramString = paramString + "_D";
    for (int i = 0; i < 4; i++) {
      if ((hierMesh().chunkFindCheck(paramString + i) != -1) && 
        (hierMesh().isChunkVisible(paramString + i))) return true;
    }
    return false;
  }

  protected int chunkDamageVisible(String paramString) {
    if (paramString.lastIndexOf("_") == -1) paramString = paramString + "_D";
    for (int i = 0; i < 4; i++) {
      if ((hierMesh().chunkFindCheck(paramString + i) != -1) && 
        (hierMesh().isChunkVisible(paramString + i))) return i;
    }
    return 0;
  }

  public Wing getWing()
  {
    return (Wing)getOwner();
  }

  public Squadron getSquadron()
  {
    Wing localWing = getWing();
    if (localWing == null) return null;
    return localWing.squadron();
  }

  public Regiment getRegiment()
  {
    Wing localWing = getWing();
    if (localWing == null) return null;
    return localWing.regiment();
  }

  public void hitDaSilk() {
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitDaSilk();
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setReadyToDie(true);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_z_of_type_Double - Engine.land().HQ_Air(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_x_of_type_Double, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Loc_of_type_ComMaddoxJGPPoint3d.jdField_y_of_type_Double) > 20.0D) Voice.speakBailOut(this);
  }

  protected void killPilot(Actor paramActor, int paramInt)
  {
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramActor, paramInt, 100);
  }
  public void doKillPilot(int paramInt) {
  }
  public void doMurderPilot(int paramInt) {
  }
  public void doRemoveBodyFromPlane(int paramInt) { doRemoveBodyChunkFromPlane("Pilot" + paramInt);
    doRemoveBodyChunkFromPlane("Head" + paramInt);
    doRemoveBodyChunkFromPlane("HMask" + paramInt);
    doRemoveBodyChunkFromPlane("Pilot" + paramInt + "a");
    doRemoveBodyChunkFromPlane("Head" + paramInt + "a");
    doRemoveBodyChunkFromPlane("Pilot" + paramInt + "FAK");
    doRemoveBodyChunkFromPlane("Head" + paramInt + "FAK");
    doRemoveBodyChunkFromPlane("Pilot" + paramInt + "FAL");
    doRemoveBodyChunkFromPlane("Head" + paramInt + "FAL"); }

  protected void doRemoveBodyChunkFromPlane(String paramString) {
    if (hierMesh().chunkFindCheck(paramString + "_D0") != -1) {
      hierMesh().chunkVisible(paramString + "_D0", false);
    }
    if (hierMesh().chunkFindCheck(paramString + "_D1") != -1)
      hierMesh().chunkVisible(paramString + "_D1", false);
  }

  public void doSetSootState(int paramInt1, int paramInt2)
  {
    for (int i = 0; i < 2; i++) {
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateSootEffects[paramInt1][i] != null) {
        Eff3DActor.finish(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateSootEffects[paramInt1][i]);
      }
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateSootEffects[paramInt1][i] = null;
    }
    switch (paramInt2) {
    case 0:
      break;
    case 1:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateSootEffects[paramInt1][0] = Eff3DActor.New(this, findHook("_Engine" + (paramInt1 + 1) + "ES_01"), null, 1.0F, "3DO/Effects/Aircraft/BlackSmallTSPD.eff", -1.0F);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateSootEffects[paramInt1][1] = Eff3DActor.New(this, findHook("_Engine" + (paramInt1 + 1) + "ES_02"), null, 1.0F, "3DO/Effects/Aircraft/BlackSmallTSPD.eff", -1.0F);
      break;
    case 3:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateSootEffects[paramInt1][1] = Eff3DActor.New(this, findHook("_Engine" + (paramInt1 + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/BlackMediumTSPD.eff", -1.0F);
    case 2:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateSootEffects[paramInt1][0] = Eff3DActor.New(this, findHook("_Engine" + (paramInt1 + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/TurboZippo.eff", -1.0F);
      break;
    case 5:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateSootEffects[paramInt1][0] = Eff3DActor.New(this, findHook("_Engine" + (paramInt1 + 1) + "EF_01"), null, 3.0F, "3DO/Effects/Aircraft/TurboJRD1100F.eff", -1.0F);
    case 4:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateSootEffects[paramInt1][1] = Eff3DActor.New(this, findHook("_Engine" + (paramInt1 + 1) + "EF_01"), null, 1.0F, "3DO/Effects/Aircraft/BlackMediumTSPD.eff", -1.0F);
    }
  }

  public void onAircraftLoaded()
  {
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof Maneuver)) {
      Maneuver localManeuver = (Maneuver)this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel;
      localManeuver.takeIntoAccount[0] = 1.0F;
      localManeuver.takeIntoAccount[1] = 1.0F;
      localManeuver.takeIntoAccount[2] = 0.7F;
      if ((this instanceof TypeFighter)) {
        if (aircIndex() % 2 == 0) {
          localManeuver.takeIntoAccount[3] = 0.0F;
          localManeuver.takeIntoAccount[4] = 1.0F;
        } else {
          localManeuver.takeIntoAccount[2] = 0.1F;
          localManeuver.takeIntoAccount[3] = 1.0F;
          localManeuver.takeIntoAccount[4] = 0.0F;
        }
        localManeuver.takeIntoAccount[5] = 0.3F;
        localManeuver.takeIntoAccount[6] = 0.3F;
        localManeuver.takeIntoAccount[7] = 0.1F;
      }
      else if ((this instanceof TypeStormovik)) {
        if (aircIndex() != 0) localManeuver.takeIntoAccount[2] = 0.5F;
        localManeuver.takeIntoAccount[3] = 0.4F;
        localManeuver.takeIntoAccount[4] = 0.2F;
        localManeuver.takeIntoAccount[5] = 0.1F;
        localManeuver.takeIntoAccount[6] = 0.1F;
        localManeuver.takeIntoAccount[7] = 0.6F;
      } else {
        if (aircIndex() != 0) localManeuver.takeIntoAccount[2] = 0.4F;
        localManeuver.takeIntoAccount[3] = 0.2F;
        localManeuver.takeIntoAccount[4] = 0.0F;
        localManeuver.takeIntoAccount[5] = 0.0F;
        localManeuver.takeIntoAccount[6] = 0.0F;
        localManeuver.takeIntoAccount[7] = 1.0F;
      }
      for (int i = 0; i < 7 + 1; i++) localManeuver.AccountCoeff[i] = 0.0F;
    }
  }

  public static float cvt(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    paramFloat1 = Math.min(Math.max(paramFloat1, paramFloat2), paramFloat3);
    return paramFloat4 + (paramFloat5 - paramFloat4) * (paramFloat1 - paramFloat2) / (paramFloat3 - paramFloat2);
  }

  protected void debugprintln(String paramString)
  {
    if (World.cur().isDebugFM())
      System.out.println("<" + name() + "> (" + typedName() + ") " + paramString);
  }

  public static void debugprintln(Actor paramActor, String paramString) {
    if (World.cur().isDebugFM()) {
      if (Actor.isValid(paramActor)) {
        System.out.print("<" + paramActor.name() + ">");
        if ((paramActor instanceof Aircraft)) System.out.print(" (" + ((Aircraft)paramActor).typedName() + ")"); 
      }
      else {
        System.out.print("<INVALIDACTOR>");
      }
      System.out.println(" " + paramString);
    }
  }

  public void debuggunnery(String paramString) {
    if (World.cur().isDebugFM())
      System.out.println("<" + name() + "> (" + typedName() + ") *** BULLET *** : " + paramString);
  }

  protected float bailProbabilityOnCut(String paramString)
  {
    if (paramString.startsWith("Nose")) return 0.5F;
    if (paramString.startsWith("Wing")) return 0.99F;
    if (paramString.startsWith("Aroone")) return 0.05F;
    if (paramString.startsWith("Tail")) return 0.99F;
    if ((paramString.startsWith("StabL")) && (!isChunkAnyDamageVisible("VatorR"))) return 0.99F;
    if ((paramString.startsWith("StabR")) && (!isChunkAnyDamageVisible("VatorL"))) return 0.99F;
    if (paramString.startsWith("Stab")) return 0.33F;
    if ((paramString.startsWith("VatorL")) && (!isChunkAnyDamageVisible("VatorR"))) return 0.99F;
    if ((paramString.startsWith("VatorR")) && (!isChunkAnyDamageVisible("VatorL"))) return 0.99F;
    if (paramString.startsWith("Vator")) return 0.01F;
    if (paramString.startsWith("Keel")) return 0.5F;
    if (paramString.startsWith("Rudder")) return 0.05F;
    if (paramString.startsWith("Engine")) return 0.12F;
    return -0.0F;
  }

  private void _setMesh(String paramString)
  {
    setMesh(paramString);
    CacheItem localCacheItem = (CacheItem)meshCache.get(paramString);
    if (localCacheItem == null) {
      localCacheItem = new CacheItem();
      localCacheItem.mesh = new HierMesh(paramString);
      prepareMeshCamouflage(paramString, localCacheItem.mesh);
      localCacheItem.bExistTextures = true;
      localCacheItem.loaded = 1;
      meshCache.put(paramString, localCacheItem);
    } else {
      localCacheItem.loaded += 1;
      if (!localCacheItem.bExistTextures) {
        localCacheItem.mesh.destroy();
        localCacheItem.mesh = new HierMesh(paramString);
        prepareMeshCamouflage(paramString, localCacheItem.mesh);
        localCacheItem.bExistTextures = true;
      }
    }
    airCache.put(this, localCacheItem);

    checkMeshCache();
  }

  private void _removeMesh() {
    CacheItem localCacheItem = (CacheItem)airCache.get(this);
    if (localCacheItem == null) return;
    airCache.remove(this);
    localCacheItem.loaded -= 1;
    if (localCacheItem.loaded == 0) {
      localCacheItem.time = Time.real();
    }
    checkMeshCache();
  }

  public static void checkMeshCache() {
    if (!Config.isUSE_RENDER()) return;
    long l = Time.real();

    Map.Entry localEntry = meshCache.nextEntry(null);
    while (localEntry != null) {
      CacheItem localCacheItem = (CacheItem)localEntry.getValue();
      if ((localCacheItem.loaded == 0) && (localCacheItem.bExistTextures) && (l - localCacheItem.time > 180000L))
      {
        HierMesh localHierMesh = localCacheItem.mesh;
        int i = localHierMesh.materials();
        Mat localMat = Mat.New("3do/textures/clear.mat");
        for (int j = 0; j < i; j++)
          localHierMesh.materialReplace(j, localMat);
        localCacheItem.bExistTextures = false;
      }
      localEntry = meshCache.nextEntry(localEntry);
    }
  }

  public static void resetGameClear() {
    meshCache.clear();
    airCache.clear();
  }

  public void setCockpitState(int paramInt)
  {
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (World.cur().diffCur.Vulnerability) && 
      (Actor.isValid(Main3D.cur3D().cockpitCur)))
      Main3D.cur3D().cockpitCur.doReflectCockitState();
  }

  protected void resetYPRmodifier()
  {
    float tmp33_32 = (ypr[2] = xyz[0] = xyz[1] = xyz[2] = 0.0F); ypr[1] = tmp33_32; ypr[0] = tmp33_32;
  }

  public CellAirPlane getCellAirPlane()
  {
    CellAirPlane localCellAirPlane = (CellAirPlane)Property.value(this, "CellAirPlane", (Object)null);
    if (localCellAirPlane != null)
      return localCellAirPlane;
    localCellAirPlane = (CellAirPlane)Property.value(getClass(), "CellObject", (Object)null);
    if (localCellAirPlane == null) {
      tmpLocCell.set(0.0D, 0.0D, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.H, 0.0F, this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.Pitch, 0.0F);
      localCellAirPlane = new CellAirPlane(new CellObject[1][1], hierMesh(), tmpLocCell, 1.0D);
      localCellAirPlane.blurSiluet8x();
      localCellAirPlane.clampCells();
      Property.set(getClass(), "CellObject", localCellAirPlane);
    }
    localCellAirPlane = (CellAirPlane)localCellAirPlane.getClone();
    Property.set(this, "CellObject", localCellAirPlane);
    return localCellAirPlane;
  }
  public static CellAirPlane getCellAirPlane(Class paramClass) {
    CellAirPlane localCellAirPlane = null;
    tmpLocCell.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    HierMesh localHierMesh = new HierMesh(getPropertyMesh(paramClass, null));
    localCellAirPlane = new CellAirPlane(new CellObject[1][1], localHierMesh, tmpLocCell, 1.0D);
    localCellAirPlane.blurSiluet8x();
    localCellAirPlane.clampCells();
    return localCellAirPlane;
  }

  static class CacheItem
  {
    HierMesh mesh;
    boolean bExistTextures;
    int loaded;
    long time;
  }

  public static class _WeaponSlot
  {
    public int trigger;
    public Class clazz;
    public int bullets;

    public _WeaponSlot(int paramInt1, String paramString, int paramInt2)
      throws Exception
    {
      this.trigger = paramInt1;
      this.clazz = ObjIO.classForName("weapons." + paramString);
      this.bullets = paramInt2;
    }
  }

  private static class MsgExplosionPostVarSet
  {
    Actor THIS;
    String chunkName;
    Point3d p = new Point3d();
    Actor initiator;
    float power;
    float radius;

    private MsgExplosionPostVarSet()
    {
    }

    MsgExplosionPostVarSet(Aircraft.1 param1)
    {
      this();
    }
  }

  static class EndActionParam
  {
    Actor initiator;
    Eff3DActor smoke;

    public EndActionParam(Actor paramActor, Eff3DActor paramEff3DActor)
    {
      this.initiator = paramActor;
      this.smoke = paramEff3DActor;
    }
  }
}