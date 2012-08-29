package com.maddox.il2.objects.ships;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Line2d;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Aimer;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.AnglesForkExtended;
import com.maddox.il2.ai.AnglesRange;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.StrengthProperties;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.CellAirField;
import com.maddox.il2.ai.air.CellObject;
import com.maddox.il2.ai.ground.Aim;
import com.maddox.il2.ai.ground.HunterInterface;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.InterpolateAdapter;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.engine.VisibilityLong;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.ObjState;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BigshipGeneric extends ActorHMesh
  implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener, Predator, ActorAlign, HunterInterface, VisibilityLong
{
  private static final int MAX_PARTS = 255;
  private static final int MAX_GUNS = 255;
  private static final int MAX_USER_ADDITIONAL_COLLISION_CHUNKS = 4;
  private ShipProperties prop;
  private static final int NETSEND_MIN_DELAY_MS_PARTSSTATE = 650;
  private static final int NETSEND_MAX_DELAY_MS_PARTSSTATE = 1100;
  private long netsendPartsState_lasttimeMS;
  private boolean netsendPartsState_needtosend;
  private static float netsendDrown_pitch = 0.0F;
  private static float netsendDrown_roll = 0.0F;
  private static float netsendDrown_depth = 0.0F;
  private static float netsendDrown_timeS = 0.0F;
  private static int netsendDrown_nparts = 0;
  private static final int NETSEND_MIN_DELAY_MS_FIRE = 40;
  private static final int NETSEND_MAX_DELAY_MS_FIRE = 85;
  private static final long NETSEND_MIN_BYTECODEDDELTATIME_MS_FIRE = -2000L;
  private static final long NETSEND_MAX_BYTECODEDDELTATIME_MS_FIRE = 5000L;
  private static final int NETSEND_ABSLIMIT_NUMITEMS_FIRE = 31;
  private static final int NETSEND_MAX_NUMITEMS_FIRE = 15;
  private long netsendFire_lasttimeMS;
  private int netsendFire_armindex;
  private static TmpTrackOrFireInfo[] netsendFire_tmpbuff;
  private FiringDevice[] arms;
  private static final int STPART_LIVE = 0;
  private static final int STPART_BLACK = 1;
  private static final int STPART_DEAD = 2;
  private Part[] parts;
  private int[] shotpoints;
  int numshotpoints;
  private static final int NETSEND_MIN_DELAY_MS_DMG = 65;
  private static final int NETSEND_MAX_DELAY_MS_DMG = 115;
  private static final int NETSEND_ABSLIMIT_NUMITEMS_DMG = 256;
  private static final int NETSEND_MAX_NUMITEMS_DMG = 14;
  private long netsendDmg_lasttimeMS;
  private int netsendDmg_partindex;
  private ArrayList path;
  private int cachedSeg;
  private float bodyDepth;
  private float bodyPitch;
  private float bodyRoll;
  private float shipYaw;
  private long tmInterpoStart;
  private long tmInterpoEnd;
  private float bodyDepth0;
  private float bodyPitch0;
  private float bodyRoll0;
  private float bodyDepth1;
  private float bodyPitch1;
  private float bodyRoll1;
  private long timeOfDeath;
  private long sink2timeWhenStop;
  private float sink2Depth;
  private float sink2Pitch;
  private float sink2Roll;
  private int dying;
  static final int DYING_NONE = 0;
  static final int DYING_SINK1 = 1;
  static final int DYING_SINK2 = 2;
  static final int DYING_DEAD = 3;
  private long respawnDelay;
  private long wakeupTmr;
  public float DELAY_WAKEUP;
  public int SKILL_IDX;
  public float SLOWFIRE_K;
  private Pipe[] pipes;
  private Pipe[] dsmoks;
  private Eff3DActor[] wake = { null, null, null };
  private Eff3DActor noseW;
  private Eff3DActor nose;
  private Eff3DActor tail;
  private static ShipProperties constr_arg1 = null;
  private static ActorSpawnArg constr_arg2 = null;
  private static Point3d p = new Point3d();
  private static Point3d p1 = new Point3d();
  private static Point3d p2 = new Point3d();
  private Orient o;
  private static Vector3f tmpvf = new Vector3f();
  private static Vector3d tmpvd = new Vector3d();
  private static float[] tmpYPR = new float[3];
  private static float[] tmpf6 = new float[6];
  private static Loc tmpL = new Loc();
  private static byte[] tmpBitsState = new byte[32];
  private float rollAmp;
  private int rollPeriod;
  private double rollWAmp;
  private float pitchAmp;
  private int pitchPeriod;
  private double pitchWAmp;
  private Vector3d W;
  private Vector3d N;
  private Vector3d tmpV;
  public Orient initOr;
  public Loc initLoc;
  private AirportCarrier airport;
  private CellAirField cellTO;
  private CellAirField cellLDG;
  public Aircraft towAircraft;
  public int towPortNum;
  public HookNamed towHook;
  private static Vector3d tmpDir = new Vector3d();

  public ShipProperties getShipProp()
  {
    return this.prop;
  }

  public static final double Rnd(double paramDouble1, double paramDouble2)
  {
    return World.Rnd().nextDouble(paramDouble1, paramDouble2);
  }

  public static final float Rnd(float paramFloat1, float paramFloat2)
  {
    return World.Rnd().nextFloat(paramFloat1, paramFloat2);
  }

  public static final int Rnd(int paramInt1, int paramInt2)
  {
    return World.Rnd().nextInt(paramInt1, paramInt2);
  }

  private boolean RndB(float paramFloat)
  {
    return World.Rnd().nextFloat(0.0F, 1.0F) < paramFloat;
  }

  public static final float KmHourToMSec(float paramFloat)
  {
    return paramFloat / 3.6F;
  }

  private static final long SecsToTicks(float paramFloat)
  {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }

  protected final boolean Head360(FiringDevice paramFiringDevice)
  {
    return this.parts[paramFiringDevice.part_idx].pro.HEAD_YAW_RANGE.fullcircle();
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if ((paramActor instanceof BridgeSegment))
    {
      if (this.dying != 0)
        paramArrayOfBoolean[0] = false;
      return;
    }
    if ((this.path == null) && ((paramActor instanceof ActorMesh)) && (((ActorMesh)paramActor).isStaticPos()))
    {
      paramArrayOfBoolean[0] = false;
      return;
    }
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (this.dying != 0)
      return;
    if (isNetMirror())
      return;
    if ((paramActor instanceof WeakBody))
      return;
    if (((paramActor instanceof ShipGeneric)) || ((paramActor instanceof BigshipGeneric)) || ((paramActor instanceof BridgeSegment)))
      Die(null, -1L, true, true);
  }

  private int findNotDeadPartByShotChunk(String paramString)
  {
    if ((paramString == null) || (paramString == ""))
      return -2;
    int i = hierMesh().chunkFindCheck(paramString);
    if (i < 0)
      return -2;
    for (int j = 0; j < this.parts.length; j++) {
      if (this.parts[j].state == 2)
        continue;
      if (i == this.parts[j].pro.baseChunkIdx)
        return j;
      for (int k = 0; k < this.parts[j].pro.additCollisChunkIdx.length; k++) {
        if (i == this.parts[j].pro.additCollisChunkIdx[k]) {
          return j;
        }
      }
    }
    return -1;
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = 2;
    if (this.dying != 0)
      return;
    if (paramShot.power <= 0.0F)
      return;
    if ((isNetMirror()) && (paramShot.isMirage()))
      return;
    if (this.wakeupTmr < 0L)
      this.wakeupTmr = SecsToTicks(Rnd(this.DELAY_WAKEUP, this.DELAY_WAKEUP * 1.2F));
    int i = findNotDeadPartByShotChunk(paramShot.chunkName);
    if (i < 0)
      return;
    float f1;
    float f2;
    if (paramShot.powerType == 1)
    {
      f1 = this.parts[i].pro.stre.EXPLHIT_MAX_TNT;
      f2 = this.parts[i].pro.stre.EXPLHIT_MAX_TNT;
    }
    else {
      f1 = this.parts[i].pro.stre.SHOT_MIN_ENERGY;
      f2 = this.parts[i].pro.stre.SHOT_MAX_ENERGY;
    }
    float f3 = paramShot.power * Rnd(1.0F, 1.1F);
    if (f3 < f1)
      return;
    tmpvd.set(paramShot.v);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().transformInv(tmpvd);
    Part.access$4402(this.parts[i], tmpvd.jdField_y_of_type_Double > 0.0D);
    float f4 = f3 / f2;
    Part.access$4316(this.parts[i], f4);
    if ((isNetMirror()) && (paramShot.initiator != null))
      Part.access$4702(this.parts[i], paramShot.initiator);
    InjurePart(i, paramShot.initiator, true);

    BigshipGeneric localBigshipGeneric = this;
    if (paramShot.power > 3000.0F)
    {
      String str = "BallisticDamage " + localBigshipGeneric.name() + " " + paramShot.chunkName + " " + this.parts[i].damage + "  damaged by " + paramShot.initiator.name() + " at ";
      EventLog.onParaLanded(localBigshipGeneric, str, 0);
    }
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (this.dying != 0)
      return;
    if ((isNetMirror()) && (paramExplosion.isMirage()))
      return;
    if (this.wakeupTmr < 0L)
      this.wakeupTmr = SecsToTicks(Rnd(this.DELAY_WAKEUP, this.DELAY_WAKEUP * 1.2F));
    float f1 = paramExplosion.power;
    Explosion localExplosion = paramExplosion;
    if ((paramExplosion.powerType == 2) && (paramExplosion.chunkName != null))
      f1 *= 0.45F;
    int i = -1;
    if (paramExplosion.chunkName != null)
    {
      int j = findNotDeadPartByShotChunk(paramExplosion.chunkName);
      if (j >= 0)
      {
        float f2 = f1;
        f2 *= Rnd(1.0F, 1.1F);
        if (f2 >= this.parts[j].pro.stre.EXPLHIT_MIN_TNT)
        {
          i = j;
          p1.set(paramExplosion.p);
          this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().transformInv(p1);
          Part.access$4402(this.parts[j], p1.jdField_y_of_type_Double < 0.0D);
          float f3 = f2 / this.parts[j].pro.stre.EXPLHIT_MAX_TNT;
          Part.access$4316(this.parts[j], f3);
          if ((isNetMirror()) && (paramExplosion.initiator != null))
            Part.access$4702(this.parts[j], paramExplosion.initiator);
          InjurePart(j, paramExplosion.initiator, true);

          BigshipGeneric localBigshipGeneric = this;
          if (paramExplosion.power > 10.0F)
          {
            String str = "ExplosiveDamage " + localBigshipGeneric.name() + " " + paramExplosion.chunkName + " " + this.parts[j].damage + "  damaged by " + paramExplosion.initiator.name() + " at ";
            EventLog.onParaLanded(localBigshipGeneric, str, 0);
          }
        }
      }

    }

    Loc localLoc = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs();
    p1.set(paramExplosion.p);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().transformInv(p1);
    boolean bool = p1.jdField_y_of_type_Double < 0.0D;
    for (int k = 0; k < this.parts.length; k++) {
      if ((k == i) || (this.parts[k].state == 2))
        continue;
      p1.set(this.parts[k].pro.partOffs);
      localLoc.transform(p1);
      float f4 = this.parts[k].pro.partR;
      float f5 = paramExplosion.receivedTNT_1meter(p1, f4);
      f5 *= Rnd(1.0F, 1.1F);
      if (f5 < this.parts[k].pro.stre.EXPLNEAR_MIN_TNT)
        continue;
      Part.access$4402(this.parts[k], bool);
      float f6 = f5 / this.parts[k].pro.stre.EXPLNEAR_MAX_TNT;
      Part.access$4316(this.parts[k], f6);
      if ((isNetMirror()) && (paramExplosion.initiator != null))
        Part.access$4702(this.parts[k], paramExplosion.initiator);
      InjurePart(k, paramExplosion.initiator, true);
    }
  }

  private void recomputeShotpoints()
  {
    if ((this.shotpoints == null) || (this.shotpoints.length < 1 + this.parts.length))
      this.shotpoints = new int[1 + this.parts.length];
    this.numshotpoints = 0;
    if (this.dying != 0)
      return;
    this.numshotpoints = 1;
    this.shotpoints[0] = 0;
    for (int i = 0; i < this.parts.length; i++)
    {
      if (this.parts[i].state == 2)
        continue;
      int j;
      if (this.parts[i].pro.isItLifeKeeper())
      {
        j = this.parts[i].pro.baseChunkIdx;
      }
      else {
        if (!this.parts[i].pro.haveGun())
          continue;
        j = this.parts[i].pro.gunChunkIdx;
      }
      this.shotpoints[this.numshotpoints] = (i + 1);
      hierMesh().setCurChunk(j);
      hierMesh().getChunkLocObj(tmpL);
      this.parts[i].shotpointOffs.set(tmpL.getPoint());
      this.numshotpoints += 1;
    }
  }

  private boolean visualsInjurePart(int paramInt, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      if (this.parts[paramInt].state == 2)
      {
        Part.access$4302(this.parts[paramInt], 1.0F);
        return false;
      }
      if (this.parts[paramInt].damage < this.parts[paramInt].pro.BLACK_DAMAGE)
        return false;
      netsendDrown_nparts = 0;
      netsendDrown_depth = 0.0F;
      netsendDrown_pitch = 0.0F;
      netsendDrown_roll = 0.0F;
      netsendDrown_timeS = 0.0F;
      if (this.parts[paramInt].damage < 1.0F)
      {
        if (this.parts[paramInt].state == 1)
          return false;
        Part.access$1102(this.parts[paramInt], 1);
      }
      else {
        Part.access$4302(this.parts[paramInt], 1.0F);
        Part.access$1102(this.parts[paramInt], 2);
      }
      if (this.parts[paramInt].pro.isItLifeKeeper())
      {
        netsendDrown_nparts += 1;
        netsendDrown_depth += Rnd(0.8F, 1.0F) * this.parts[paramInt].pro.dmgDepth;
        netsendDrown_pitch += Rnd(0.8F, 1.0F) * this.parts[paramInt].pro.dmgPitch;
        netsendDrown_roll = (float)(netsendDrown_roll + Rnd(0.8F, 1.0F) * this.parts[paramInt].pro.dmgRoll * (this.parts[paramInt].damageIsFromRight ? -1.0D : 1.0D));
        netsendDrown_timeS += Rnd(0.7F, 1.3F) * this.parts[paramInt].pro.dmgTime;
      }
    }
    if (this.parts[paramInt].pro.haveGun())
    {
      this.arms[this.parts[paramInt].pro.gun_idx].aime.forgetAiming();
      FiringDevice.access$5402(this.arms[this.parts[paramInt].pro.gun_idx], null);
    }
    int[] arrayOfInt = hierMesh().getSubTreesSpec(this.parts[paramInt].pro.baseChunkName);
    int j;
    int m;
    for (int i = 0; i < arrayOfInt.length; i++)
    {
      hierMesh().setCurChunk(arrayOfInt[i]);
      if (!hierMesh().isChunkVisible())
        continue;
      for (j = 0; j < this.parts.length; j++) {
        if ((j == paramInt) || (this.parts[j].state == 2) || (arrayOfInt[i] != this.parts[j].pro.baseChunkIdx))
          continue;
        if ((!paramBoolean) && (this.parts[j].state == 0) && (this.parts[j].pro.isItLifeKeeper()))
        {
          netsendDrown_nparts += 1;
          netsendDrown_depth += Rnd(0.8F, 1.0F) * this.parts[j].pro.dmgDepth;
          netsendDrown_pitch += Rnd(0.8F, 1.0F) * this.parts[j].pro.dmgPitch;
          netsendDrown_roll = (float)(netsendDrown_roll + Rnd(0.8F, 1.0F) * this.parts[j].pro.dmgRoll * (this.parts[j].damageIsFromRight ? -1.0D : 1.0D));
          netsendDrown_timeS += Rnd(0.7F, 1.3F) * this.parts[j].pro.dmgTime;
        }
        Part.access$4302(this.parts[j], paramBoolean ? 0.0F : 1.0F);
        Part.access$4702(this.parts[j], null);
        Part.access$1102(this.parts[j], 2);
        if (!this.parts[j].pro.haveGun())
          continue;
        this.arms[this.parts[j].pro.gun_idx].aime.forgetAiming();
        FiringDevice.access$5402(this.arms[this.parts[j].pro.gun_idx], null);
      }

      if ((hierMesh().chunkName().endsWith("_x")) || (hierMesh().chunkName().endsWith("_X")))
      {
        hierMesh().chunkVisible(false);
      }
      else {
        String str1 = hierMesh().chunkName() + "_dmg";
        m = hierMesh().chunkFindCheck(str1);
        if (m < 0)
          continue;
        hierMesh().chunkVisible(false);
        hierMesh().chunkVisible(str1, true);
      }
    }
    int k;
    if (this.pipes != null)
    {
      j = 0;
      for (k = 0; k < this.pipes.length; k++) {
        if (this.pipes[k] != null)
          if (this.pipes[k].pipe == null)
          {
            this.pipes[k] = null;
          }
          else {
            m = this.pipes[k].part_idx;
            if (this.parts[m].state == 0)
            {
              j = 1;
            }
            else {
              this.pipes[k].pipe._finish();
              Pipe.access$4102(this.pipes[k], null);
              this.pipes[k] = null;
            }
          }
      }
      if (j == 0)
      {
        for (m = 0; m < this.pipes.length; m++) {
          if (this.pipes[m] != null)
            this.pipes[m] = null;
        }
        this.pipes = null;
      }
    }
    if (this.dsmoks != null)
    {
      for (j = 0; j < this.dsmoks.length; j++) {
        if ((this.dsmoks[j] == null) || (this.dsmoks[j].pipe != null))
          continue;
        k = this.dsmoks[j].part_idx;
        if (this.parts[k].state == 0)
          continue;
        String str2 = this.parts[k].pro.baseChunkName;
        Loc localLoc = new Loc();
        hierMesh().setCurChunk(str2);
        hierMesh().getChunkLocObj(localLoc);
        float f = this.parts[k].pro.stre.EXPLNEAR_MIN_TNT;
        String str3 = "Effects/Smokes/Smoke";
        if (this.parts[k].pro.haveGun())
        {
          str3 = str3 + "Gun";
          if (f < 4.0F) {
            str3 = str3 + "Tiny";
          }
          else if (f < 24.0F) {
            str3 = str3 + "Small";
          }
          else if (f < 32.0F) {
            str3 = str3 + "Medium";
          }
          else if (f < 45.0F)
            str3 = str3 + "Large";
          else
            str3 = str3 + "Huge";
          Pipe.access$4102(this.dsmoks[j], Eff3DActor.New(this, null, localLoc, 1.0F, str3 + ".eff", 600.0F));
          Eff3DActor.New(this, null, localLoc, 1.0F, str3 + "Fire.eff", 120.0F);
        }
        else {
          str3 = str3 + "Ship";
          if (f < 24.0F) {
            str3 = str3 + "Tiny";
          }
          else if (f < 49.0F) {
            str3 = str3 + "Small";
          }
          else if (f < 70.0F) {
            str3 = str3 + "Medium";
          }
          else if (f == 70.0F) {
            str3 = str3 + "Large";
          }
          else if (f < 130.0F) {
            str3 = str3 + "Huge";
          }
          else if (f < 3260.0F)
            str3 = str3 + "Enormous";
          else
            str3 = str3 + "Invulnerable";
          Pipe.access$4102(this.dsmoks[j], Eff3DActor.New(this, null, localLoc, 1.1F, str3 + ".eff", -1.0F));
        }
      }

    }

    recomputeShotpoints();
    return true;
  }

  void master_sendDrown(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (!this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored())
      return;
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try
    {
      localNetMsgGuaranted.writeByte(100);
      float f = paramFloat1 / 1000.0F;
      if (f <= 0.0F)
        f = 0.0F;
      if (f >= 1.0F)
        f = 1.0F;
      int i = (int)(f * 32767.0F);
      if (i > 32767)
        i = 32767;
      if (i < 0)
        i = 0;
      localNetMsgGuaranted.writeShort(i);
      f = paramFloat2 / 90.0F;
      if (f <= -1.0F)
        f = -1.0F;
      if (f >= 1.0F)
        f = 1.0F;
      i = (int)(f * 32767.0F);
      if (i > 32767)
        i = 32767;
      if (i < -32767)
        i = -32767;
      localNetMsgGuaranted.writeShort(i);
      f = paramFloat3 / 90.0F;
      if (f <= -1.0F)
        f = -1.0F;
      if (f >= 1.0F)
        f = 1.0F;
      i = (int)(f * 32767.0F);
      if (i > 32767)
        i = 32767;
      if (i < -32767)
        i = -32767;
      localNetMsgGuaranted.writeShort(i);
      f = paramFloat4 / 1200.0F;
      if (f <= 0.0F)
        f = 0.0F;
      if (f >= 1.0F)
        f = 1.0F;
      i = (int)(f * 32767.0F);
      if (i > 32767)
        i = 32767;
      if (i < 0)
        i = 0;
      localNetMsgGuaranted.writeShort(i);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void InjurePart(int paramInt, Actor paramActor, boolean paramBoolean)
  {
    if (isNetMirror())
      return;
    if (!visualsInjurePart(paramInt, false))
      return;
    if (this.dying != 0)
      return;
    int i = 0;
    for (int j = 0; j < this.parts.length; j++)
    {
      if ((!this.parts[j].pro.isItLifeKeeper()) || (this.parts[j].state != 2))
        continue;
      i = 1;
      break;
    }

    this.netsendPartsState_needtosend = true;
    if (netsendDrown_nparts > 0)
    {
      netsendDrown_depth += this.bodyDepth1;
      netsendDrown_pitch += this.bodyPitch1;
      netsendDrown_roll += this.bodyRoll1;
      netsendDrown_timeS /= netsendDrown_nparts;
      if (netsendDrown_timeS >= 1200.0F)
        netsendDrown_timeS = 1200.0F;
      this.tmInterpoStart = NetServerParams.getServerTime();
      this.tmInterpoEnd = (this.tmInterpoStart + ()(netsendDrown_timeS * 1000.0F));
      this.bodyDepth0 = this.bodyDepth;
      this.bodyPitch0 = this.bodyPitch;
      this.bodyRoll0 = this.bodyRoll;
      this.bodyDepth1 = netsendDrown_depth;
      this.bodyPitch1 = netsendDrown_pitch;
      this.bodyRoll1 = netsendDrown_roll;
      master_sendDrown(netsendDrown_depth, netsendDrown_pitch, netsendDrown_roll, netsendDrown_timeS);
    }
    if (i == 0)
    {
      return;
    }

    Die(paramActor, -1L, paramBoolean, true);
  }

  private float computeSeaDepth(Point3d paramPoint3d)
  {
    for (float f1 = 5.0F; f1 <= 355.0F; f1 += 10.0F)
    {
      for (float f2 = 0.0F; f2 < 360.0F; f2 += 30.0F)
      {
        float f3 = f1 * Geom.cosDeg(f2);
        float f4 = f1 * Geom.sinDeg(f2);
        f3 += (float)paramPoint3d.jdField_x_of_type_Double;
        f4 += (float)paramPoint3d.jdField_y_of_type_Double;
        if (!World.land().isWater(f3, f4)) {
          return 150.0F * (f1 / 355.0F);
        }
      }
    }

    return 1000.0F;
  }

  private void computeSinkingParams(long paramLong)
  {
    if (this.path != null)
      setMovablePosition(paramLong);
    else
      setPosition();
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
    float f1 = computeSeaDepth(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint()) * Rnd(1.0F, 1.25F);
    if (f1 >= 400.0F)
      f1 = 400.0F;
    float f2 = Rnd(0.2F, 0.25F);
    float f3;
    float f4;
    float f5;
    float f6;
    if (f1 >= 200.0F)
    {
      f3 = Rnd(90.0F, 110.0F);
      f4 = f3 * f2;
      f5 = 50.0F - Rnd(0.0F, 20.0F);
      f6 = Rnd(15.0F, 32.0F);
      f2 *= 1.6F;
    }
    else {
      f3 = Rnd(30.0F, 40.0F);
      f4 = f3 * f2;
      f5 = 4.5F - Rnd(0.0F, 2.5F);
      f6 = Rnd(6.0F, 13.0F);
    }
    float f7 = (f1 - f4) / f2;
    if (f7 < 1.0F)
      f7 = 1.0F;
    float f8 = f7 * f2;
    computeInterpolatedDPR(paramLong);
    this.bodyDepth0 = this.bodyDepth;
    this.bodyPitch0 = this.bodyPitch;
    this.bodyRoll0 = this.bodyRoll;
    this.bodyDepth1 += f4;
    this.bodyPitch1 += (this.bodyPitch1 > 0.0D ? 1.0F : -1.0F) * f5;
    this.bodyRoll1 += (this.bodyRoll1 > 0.0D ? 1.0F : -1.0F) * f6;
    if (this.bodyPitch1 > 80.0F)
      this.bodyPitch1 = 80.0F;
    if (this.bodyPitch1 < -80.0F)
      this.bodyPitch1 = -80.0F;
    if (this.bodyRoll1 > 80.0F)
      this.bodyRoll1 = 80.0F;
    if (this.bodyRoll1 < -80.0F)
      this.bodyRoll1 = -80.0F;
    this.tmInterpoStart = paramLong;
    this.tmInterpoEnd = (this.tmInterpoStart + ()(f3 * 1000.0F));
    this.sink2Depth = (this.bodyDepth1 + f8);
    this.sink2Pitch = this.bodyPitch1;
    this.sink2Roll = this.bodyRoll1;
    this.sink2timeWhenStop = (this.tmInterpoEnd + ()(f7 * 1000.0F));
  }

  private void showExplode()
  {
    Explosions.Antiaircraft_Explode(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
  }

  private void Die(Actor paramActor, long paramLong, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.dying != 0)
      return;
    if (paramLong < 0L)
    {
      if (isNetMirror())
      {
        System.out.println("** bigship InternalError: mirror death");
        return;
      }
      paramLong = NetServerParams.getServerTime();
    }
    this.dying = 1;
    World.onActorDied(this, paramActor);
    recomputeShotpoints();
    forgetAllAiming();
    SetEffectsIntens(-1.0F);
    if (paramBoolean2)
      computeSinkingParams(paramLong);
    computeInterpolatedDPR(paramLong);
    if (this.path != null)
      setMovablePosition(paramLong);
    else
      setPosition();
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
    this.timeOfDeath = paramLong;
    if (paramBoolean1)
      showExplode();
    if ((paramBoolean1) && (isNetMaster()))
      send_DeathCommand(paramActor, null);
  }

  public void destroy()
  {
    if (isDestroyed())
      return;
    eraseGuns();
    if (this.parts != null)
    {
      for (int i = 0; i < this.parts.length; i++)
      {
        Part.access$4702(this.parts[i], null);
        this.parts[i] = null;
      }

      this.parts = null;
    }
    super.destroy();
  }

  private boolean isAnyEnemyNear()
  {
    NearestEnemies.set(WeaponsMask());
    Actor localActor = NearestEnemies.getAFoundEnemy(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), 2000.0D, getArmy());
    return localActor != null;
  }

  private final FiringDevice GetFiringDevice(Aim paramAim)
  {
    for (int i = 0; i < this.prop.nGuns; i++) {
      if ((this.arms[i] != null) && (this.arms[i].aime == paramAim))
        return this.arms[i];
    }
    System.out.println("Internal error 1: Can't find ship gun.");
    return null;
  }

  private final ShipPartProperties GetGunProperties(Aim paramAim)
  {
    for (int i = 0; i < this.prop.nGuns; i++) {
      if (this.arms[i].aime == paramAim)
        return this.parts[this.arms[i].part_idx].pro;
    }
    System.out.println("Internal error 2: Can't find ship gun.");
    return null;
  }

  private void setGunAngles(FiringDevice paramFiringDevice, float paramFloat1, float paramFloat2)
  {
    FiringDevice.access$5602(paramFiringDevice, paramFloat1);
    FiringDevice.access$5702(paramFiringDevice, paramFloat2);
    ShipPartProperties localShipPartProperties = this.parts[paramFiringDevice.part_idx].pro;
    tmpYPR[1] = 0.0F;
    tmpYPR[2] = 0.0F;
    hierMesh().setCurChunk(localShipPartProperties.headChunkIdx);
    tmpYPR[0] = FiringDevice.access$5600(paramFiringDevice);
    hierMesh().chunkSetAngles(tmpYPR);
    hierMesh().setCurChunk(localShipPartProperties.gunChunkIdx);
    tmpYPR[0] = (-(FiringDevice.access$5700(paramFiringDevice) - localShipPartProperties.GUN_STD_PITCH));
    hierMesh().chunkSetAngles(tmpYPR);
  }

  private void eraseGuns()
  {
    if (this.arms != null)
    {
      for (int i = 0; i < this.prop.nGuns; i++) {
        if (this.arms[i] == null)
          continue;
        if (this.arms[i].aime != null)
        {
          this.arms[i].aime.forgetAll();
          FiringDevice.access$1202(this.arms[i], null);
        }
        if (this.arms[i].gun != null)
        {
          ObjState.destroy(this.arms[i].gun);
          FiringDevice.access$5802(this.arms[i], null);
        }
        FiringDevice.access$5402(this.arms[i], null);
        this.arms[i] = null;
      }

      this.arms = null;
    }
  }

  private void forgetAllAiming()
  {
    if (this.arms != null)
    {
      for (int i = 0; i < this.prop.nGuns; i++) {
        if ((this.arms[i] == null) || (this.arms[i].aime == null))
          continue;
        this.arms[i].aime.forgetAiming();
        FiringDevice.access$5402(this.arms[i], null);
      }
    }
  }

  private void CreateGuns()
  {
    this.arms = new FiringDevice[this.prop.nGuns];
    for (int i = 0; i < this.parts.length; i++) {
      if (!this.parts[i].pro.haveGun())
        continue;
      ShipPartProperties localShipPartProperties = this.parts[i].pro;
      int j = localShipPartProperties.gun_idx;
      this.arms[j] = new FiringDevice();
      FiringDevice.access$5902(this.arms[j], j);
      FiringDevice.access$1002(this.arms[j], i);
      FiringDevice.access$5802(this.arms[j], null);
      try
      {
        FiringDevice.access$5802(this.arms[j], (Gun)localShipPartProperties.gunClass.newInstance());
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("BigShip: Can't create gun '" + localShipPartProperties.gunClass.getName() + "'");
      }
      this.arms[j].gun.set(this, localShipPartProperties.gunShellStartHookName);
      this.arms[j].gun.loadBullets(-1);
      FiringDevice.access$1202(this.arms[j], new Aim(this, isNetMirror(), this.SLOWFIRE_K * localShipPartProperties.DELAY_AFTER_SHOOT));
      FiringDevice.access$5402(this.arms[j], null);
    }
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  private void initMeshBasedProperties()
  {
    for (int i = 0; i < this.prop.propparts.length; i++)
    {
      ShipPartProperties localShipPartProperties = this.prop.propparts[i];
      if (localShipPartProperties.baseChunkIdx >= 0)
        continue;
      localShipPartProperties.baseChunkIdx = hierMesh().chunkFind(localShipPartProperties.baseChunkName);
      hierMesh().setCurChunk(localShipPartProperties.baseChunkIdx);
      hierMesh().getChunkLocObj(tmpL);
      tmpL.get(p1);
      localShipPartProperties.partOffs = new Point3f();
      localShipPartProperties.partOffs.set(p1);
      localShipPartProperties.partR = hierMesh().getChunkVisibilityR();
      int j = localShipPartProperties.additCollisChunkName.length;
      for (int k = 0; k < localShipPartProperties.additCollisChunkName.length; k++) {
        if (hierMesh().chunkFindCheck(localShipPartProperties.additCollisChunkName[k] + "_dmg") >= 0)
          j++;
      }
      if (hierMesh().chunkFindCheck(localShipPartProperties.baseChunkName + "_dmg") >= 0)
        j++;
      localShipPartProperties.additCollisChunkIdx = new int[j];
      j = 0;
      for (int m = 0; m < localShipPartProperties.additCollisChunkName.length; m++)
      {
        localShipPartProperties.additCollisChunkIdx[(j++)] = hierMesh().chunkFind(localShipPartProperties.additCollisChunkName[m]);
        n = hierMesh().chunkFindCheck(localShipPartProperties.additCollisChunkName[m] + "_dmg");
        if (n >= 0) {
          localShipPartProperties.additCollisChunkIdx[(j++)] = n;
        }
      }
      int n = hierMesh().chunkFindCheck(localShipPartProperties.baseChunkName + "_dmg");
      if (n >= 0)
        localShipPartProperties.additCollisChunkIdx[(j++)] = n;
      if (j != localShipPartProperties.additCollisChunkIdx.length)
        System.out.println("*** bigship: collis internal error");
      if (!localShipPartProperties.haveGun())
        continue;
      localShipPartProperties.headChunkIdx = hierMesh().chunkFind(localShipPartProperties.headChunkName);
      localShipPartProperties.gunChunkIdx = hierMesh().chunkFind(localShipPartProperties.gunChunkName);
      hierMesh().setCurChunk(localShipPartProperties.headChunkIdx);
      hierMesh().getChunkLocObj(tmpL);
      localShipPartProperties.fireOffset = new Point3d();
      tmpL.get(localShipPartProperties.fireOffset);
      localShipPartProperties.fireOrient = new Orient();
      tmpL.get(localShipPartProperties.fireOrient);
      Vector3d localVector3d1 = new Vector3d();
      Vector3d localVector3d2 = new Vector3d();
      localVector3d1.set(1.0D, 0.0D, 0.0D);
      localVector3d2.set(1.0D, 0.0D, 0.0D);
      tmpL.transform(localVector3d1);
      hierMesh().setCurChunk(localShipPartProperties.gunChunkIdx);
      hierMesh().getChunkLocObj(tmpL);
      tmpL.transform(localVector3d2);
      localShipPartProperties.GUN_STD_PITCH = Geom.RAD2DEG((float)localVector3d1.angle(localVector3d2));
    }

    initMeshMats();
  }

  private void initMeshMats()
  {
    if (Config.cur.b3dgunners)
    {
      return;
    }

    hierMesh().materialReplaceToNull("Sailor");
    hierMesh().materialReplaceToNull("Sailor1o");
    hierMesh().materialReplaceToNull("Sailor2p");
  }

  private void makeLive()
  {
    this.dying = 0;
    for (int i = 0; i < this.parts.length; i++)
    {
      Part.access$4302(this.parts[i], 0.0F);
      Part.access$1102(this.parts[i], 0);
      this.parts[i].pro = this.prop.propparts[i];
    }

    for (int j = 0; j < hierMesh().chunks(); j++)
    {
      hierMesh().setCurChunk(j);
      boolean bool = !hierMesh().chunkName().endsWith("_dmg");
      if (hierMesh().chunkName().startsWith("ShdwRcv"))
        bool = false;
      hierMesh().chunkVisible(bool);
    }

    recomputeShotpoints();
  }

  private void setDefaultLivePose()
  {
    int i = hierMesh().hookFind("Ground_Level");
    if (i != -1)
    {
      Matrix4d localMatrix4d = new Matrix4d();
      hierMesh().hookMatrix(i, localMatrix4d);
    }
    for (int j = 0; j < this.arms.length; j++)
    {
      int k = this.arms[j].part_idx;
      setGunAngles(this.arms[j], this.parts[k].pro.HEAD_STD_YAW, this.parts[k].pro.GUN_STD_PITCH);
    }

    this.bodyDepth = 0.0F;
    align();
  }

  protected BigshipGeneric()
  {
    this(constr_arg1, constr_arg2);
  }

  private BigshipGeneric(ShipProperties paramShipProperties, ActorSpawnArg paramActorSpawnArg)
  {
    super(paramShipProperties.meshName);
    this.prop = null;
    this.netsendPartsState_lasttimeMS = 0L;
    this.netsendPartsState_needtosend = false;
    this.netsendFire_lasttimeMS = 0L;
    this.netsendFire_armindex = 0;
    this.arms = null;
    this.parts = null;
    this.shotpoints = null;
    this.netsendDmg_lasttimeMS = 0L;
    this.netsendDmg_partindex = 0;
    this.cachedSeg = 0;
    this.timeOfDeath = 0L;
    this.dying = 0;
    this.respawnDelay = 0L;
    this.wakeupTmr = 0L;
    this.DELAY_WAKEUP = 0.0F;
    this.SKILL_IDX = 2;
    this.SLOWFIRE_K = 1.0F;
    this.pipes = null;
    this.dsmoks = null;
    this.noseW = null;
    this.nose = null;
    this.tail = null;
    this.o = new Orient();
    this.rollAmp = (0.7F * Mission.curCloudsType());
    this.rollPeriod = 12345;
    this.rollWAmp = (this.rollAmp * 19739.208802178713D / (180 * this.rollPeriod));
    this.pitchAmp = (0.1F * Mission.curCloudsType());
    this.pitchPeriod = 23456;
    this.pitchWAmp = (this.pitchAmp * 19739.208802178713D / (180 * this.pitchPeriod));
    this.W = new Vector3d(0.0D, 0.0D, 0.0D);
    this.N = new Vector3d(0.0D, 0.0D, 1.0D);
    this.tmpV = new Vector3d();
    this.initOr = new Orient();
    this.initLoc = new Loc();
    this.airport = null;
    this.towPortNum = -1;
    this.prop = paramShipProperties;
    initMeshBasedProperties();
    paramActorSpawnArg.setStationary(this);
    this.path = null;
    collide(true);
    drawing(true);
    this.tmInterpoStart = (this.tmInterpoEnd = 0L);
    this.bodyDepth = (this.bodyPitch = this.bodyRoll = 0.0F);
    this.bodyDepth0 = (this.bodyPitch0 = this.bodyRoll0 = 0.0F);
    this.bodyDepth1 = (this.bodyPitch1 = this.bodyRoll1 = 0.0F);
    this.shipYaw = paramActorSpawnArg.orient.getYaw();
    setPosition();
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
    this.parts = new Part[this.prop.propparts.length];
    for (int i = 0; i < this.parts.length; i++) {
      this.parts[i] = new Part();
    }
    makeLive();
    createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);
    this.SKILL_IDX = Chief.new_SKILL_IDX;
    this.SLOWFIRE_K = Chief.new_SLOWFIRE_K;
    this.DELAY_WAKEUP = Chief.new_DELAY_WAKEUP;
    this.wakeupTmr = 0L;
    CreateGuns();
    int j = 0;
    for (int k = 0; k < this.parts.length; k++) {
      if ((this.parts[k].pro.isItLifeKeeper()) || (this.parts[k].pro.haveGun()))
        j++;
    }
    if (j <= 0)
    {
      this.dsmoks = null;
    }
    else {
      this.dsmoks = new Pipe[j];
      j = 0;
      for (int m = 0; m < this.parts.length; m++) {
        if ((!this.parts[m].pro.isItLifeKeeper()) && (!this.parts[m].pro.haveGun()))
          continue;
        this.dsmoks[j] = new Pipe();
        Pipe.access$5502(this.dsmoks[j], m);
        Pipe.access$4102(this.dsmoks[j], null);
        j++;
      }
    }

    setDefaultLivePose();
    if ((!isNetMirror()) && (this.prop.nGuns > 0) && (this.DELAY_WAKEUP > 0.0F))
      this.wakeupTmr = (-SecsToTicks(Rnd(2.0F, 7.0F)));
    createAirport();
    if (!interpEnd("move"))
    {
      interpPut(new Move(), "move", Time.current(), null);
      InterpolateAdapter.forceListener(this);
    }
  }

  public BigshipGeneric(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
  {
    this.prop = null;
    this.netsendPartsState_lasttimeMS = 0L;
    this.netsendPartsState_needtosend = false;
    this.netsendFire_lasttimeMS = 0L;
    this.netsendFire_armindex = 0;
    this.arms = null;
    this.parts = null;
    this.shotpoints = null;
    this.netsendDmg_lasttimeMS = 0L;
    this.netsendDmg_partindex = 0;
    this.cachedSeg = 0;
    this.timeOfDeath = 0L;
    this.dying = 0;
    this.respawnDelay = 0L;
    this.wakeupTmr = 0L;
    this.DELAY_WAKEUP = 0.0F;
    this.SKILL_IDX = 2;
    this.SLOWFIRE_K = 1.0F;
    this.pipes = null;
    this.dsmoks = null;
    this.noseW = null;
    this.nose = null;
    this.tail = null;
    this.o = new Orient();
    this.rollAmp = (0.7F * Mission.curCloudsType());
    this.rollPeriod = 12345;
    this.rollWAmp = (this.rollAmp * 19739.208802178713D / (180 * this.rollPeriod));
    this.pitchAmp = (0.1F * Mission.curCloudsType());
    this.pitchPeriod = 23456;
    this.pitchWAmp = (this.pitchAmp * 19739.208802178713D / (180 * this.pitchPeriod));
    this.W = new Vector3d(0.0D, 0.0D, 0.0D);
    this.N = new Vector3d(0.0D, 0.0D, 1.0D);
    this.tmpV = new Vector3d();
    this.initOr = new Orient();
    this.initLoc = new Loc();
    this.airport = null;
    this.towPortNum = -1;
    try
    {
      int i = paramSectFile1.sectionIndex(paramString2);
      String str1 = paramSectFile1.var(i, 0);
      Object localObject1 = Spawn.get(str1);
      if (localObject1 == null)
        throw new ActorException("Ship: Unknown class of ship (" + str1 + ")");
      this.prop = ((SPAWN)localObject1).proper;
      try
      {
        setMesh(this.prop.meshName);
      }
      catch (RuntimeException localRuntimeException)
      {
        super.destroy();
        throw localRuntimeException;
      }
      initMeshBasedProperties();
      if (this.prop.soundName != null)
        newSound(this.prop.soundName, true);
      setName(paramString1);
      setArmy(paramInt);
      LoadPath(paramSectFile2, paramString3);
      this.cachedSeg = 0;
      this.tmInterpoStart = (this.tmInterpoEnd = 0L);
      this.bodyDepth = (this.bodyPitch = this.bodyRoll = 0.0F);
      this.bodyDepth0 = (this.bodyPitch0 = this.bodyRoll0 = 0.0F);
      this.bodyDepth1 = (this.bodyPitch1 = this.bodyRoll1 = 0.0F);
      setMovablePosition(NetServerParams.getServerTime());
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
      collide(true);
      drawing(true);
      this.parts = new Part[this.prop.propparts.length];
      for (int j = 0; j < this.parts.length; j++) {
        this.parts[j] = new Part();
      }
      makeLive();
      int k = 0;
      for (int m = 0; m <= 10; m++)
      {
        String str2 = "Vapor";
        if (m > 0)
          str2 = str2 + (m - 1);
        if (mesh().hookFind(str2) >= 0)
          k++;
      }
      Object localObject2;
      HookNamed localHookNamed2;
      if (k <= 0)
      {
        this.pipes = null;
      }
      else {
        this.pipes = new Pipe[k];
        k = 0;
        for (n = 0; n <= 10; n++)
        {
          localObject2 = "Vapor";
          if (n > 0)
            localObject2 = (String)localObject2 + (n - 1);
          if (mesh().hookFind((String)localObject2) < 0)
            continue;
          this.pipes[k] = new Pipe();
          int i2 = hierMesh().hookParentChunk((String)localObject2);
          if (i2 < 0)
          {
            System.out.println(" *** Bigship: unexpected error in vapor hook " + (String)localObject2);
            this.pipes = null;
            break;
          }

          for (int i3 = 0; i3 < this.parts.length; i3++) {
            if (this.parts[i3].pro.baseChunkIdx == i2)
              break;
          }
          if (i3 >= this.parts.length)
          {
            System.out.println(" *** Bigship: vapor hook '" + (String)localObject2 + "' MUST be linked to baseChunk");
            this.pipes = null;
            break;
          }
          Pipe.access$5502(this.pipes[k], i3);
          localHookNamed2 = new HookNamed(this, (String)localObject2);
          Pipe.access$4102(this.pipes[k], Eff3DActor.New(this, localHookNamed2, null, 1.0F, "Effects/Smokes/SmokePipeShip.eff", -1.0F));
          k++;
        }
      }
       tmp968_967 = (this.wake[0] =  = null); this.wake[1] = tmp968_967; this.wake[2] = tmp968_967;
      this.tail = null;
      this.noseW = null;
      this.nose = null;
      int n = this.prop.SLIDER_DIST / 2.5F < 90.0F ? 1 : 0;
      if (mesh().hookFind("_Prop") >= 0)
      {
        localObject2 = new HookNamedZ0(this, "_Prop");
        this.tail = Eff3DActor.New(this, (Hook)localObject2, null, 1.0F, n != 0 ? "3DO/Effects/Tracers/ShipTrail/PropWakeBoat.eff" : "3DO/Effects/Tracers/ShipTrail/PropWake.eff", -1.0F);
      }
      if (mesh().hookFind("_Centre") >= 0)
      {
        localObject2 = new Loc();
        localObject3 = new Loc();
        HookNamed localHookNamed1 = new HookNamed(this, "_Left");
        localHookNamed1.computePos(this, new Loc(), (Loc)localObject2);
        localHookNamed2 = new HookNamed(this, "_Right");
        localHookNamed2.computePos(this, new Loc(), (Loc)localObject3);
        float f1 = (float)((Loc)localObject2).getPoint().distance(((Loc)localObject3).getPoint());
        HookNamedZ0 localHookNamedZ01 = new HookNamedZ0(this, "_Centre");
        if (mesh().hookFind("_Prop") >= 0)
        {
          HookNamedZ0 localHookNamedZ02 = new HookNamedZ0(this, "_Prop");
          Loc localLoc1 = new Loc();
          localHookNamedZ01.computePos(this, new Loc(), localLoc1);
          Loc localLoc2 = new Loc();
          localHookNamedZ02.computePos(this, new Loc(), localLoc2);
          float f2 = (float)localLoc1.getPoint().distance(localLoc2.getPoint());
          this.wake[0] = Eff3DActor.New(this, localHookNamedZ02, new Loc(-f2 * 0.33D, 0.0D, 0.0D, 0.0F, 30.0F, 0.0F), f1, n != 0 ? "3DO/Effects/Tracers/ShipTrail/WakeBoat.eff" : "3DO/Effects/Tracers/ShipTrail/Wake.eff", -1.0F);
          this.wake[1] = Eff3DActor.New(this, localHookNamedZ01, new Loc(f2 * 0.15D, 0.0D, 0.0D, 0.0F, 30.0F, 0.0F), f1, n != 0 ? "3DO/Effects/Tracers/ShipTrail/WakeBoatS.eff" : "3DO/Effects/Tracers/ShipTrail/WakeS.eff", -1.0F);
          this.wake[2] = Eff3DActor.New(this, localHookNamedZ01, new Loc(-f2 * 0.15D, 0.0D, 0.0D, 0.0F, 30.0F, 0.0F), f1, n != 0 ? "3DO/Effects/Tracers/ShipTrail/WakeBoatS.eff" : "3DO/Effects/Tracers/ShipTrail/WakeS.eff", -1.0F);
        }
        else {
          this.wake[0] = Eff3DActor.New(this, localHookNamedZ01, new Loc(-f1 * 0.3D, 0.0D, 0.0D, 0.0F, 30.0F, 0.0F), f1, this.prop.SLIDER_DIST / 2.5D < 50.0D ? "3DO/Effects/Tracers/ShipTrail/WakeBoat.eff" : "3DO/Effects/Tracers/ShipTrail/Wake.eff", -1.0F);
        }
      }
      if (mesh().hookFind("_Nose") >= 0)
      {
        localObject2 = new HookNamedZ0(this, "_Nose");
        this.noseW = Eff3DActor.New(this, (Hook)localObject2, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 30.0F, 0.0F), 1.0F, "3DO/Effects/Tracers/ShipTrail/SideWave.eff", -1.0F);
        this.nose = Eff3DActor.New(this, (Hook)localObject2, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 30.0F, 0.0F), 1.0F, n != 0 ? "3DO/Effects/Tracers/ShipTrail/FrontPuffBoat.eff" : "3DO/Effects/Tracers/ShipTrail/FrontPuff.eff", -1.0F);
      }
      SetEffectsIntens(0.0F);
      int i1 = Mission.cur().getUnitNetIdRemote(this);
      Object localObject3 = Mission.cur().getNetMasterChannel();
      if (localObject3 == null) {
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new Master(this);
      }
      else if (i1 != 0)
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new Mirror(this, (NetChannel)localObject3, i1);
      this.SKILL_IDX = Chief.new_SKILL_IDX;
      this.SLOWFIRE_K = Chief.new_SLOWFIRE_K;
      this.DELAY_WAKEUP = Chief.new_DELAY_WAKEUP;
      this.wakeupTmr = 0L;
      CreateGuns();
      i1 = 0;
      for (int i4 = 0; i4 < this.parts.length; i4++) {
        if ((this.parts[i4].pro.isItLifeKeeper()) || (this.parts[i4].pro.haveGun()))
          i1++;
      }
      if (i1 <= 0)
      {
        this.dsmoks = null;
      }
      else {
        this.dsmoks = new Pipe[i1];
        i1 = 0;
        for (int i5 = 0; i5 < this.parts.length; i5++) {
          if ((!this.parts[i5].pro.isItLifeKeeper()) && (!this.parts[i5].pro.haveGun()))
            continue;
          this.dsmoks[i1] = new Pipe();
          Pipe.access$5502(this.dsmoks[i1], i5);
          Pipe.access$4102(this.dsmoks[i1], null);
          i1++;
        }
      }

      setDefaultLivePose();
      if ((!isNetMirror()) && (this.prop.nGuns > 0) && (this.DELAY_WAKEUP > 0.0F))
        this.wakeupTmr = (-SecsToTicks(Rnd(2.0F, 7.0F)));
      createAirport();
      if (!interpEnd("move"))
      {
        interpPut(new Move(), "move", Time.current(), null);
        InterpolateAdapter.forceListener(this);
      }
    }
    catch (Exception localException)
    {
      System.out.println("Ship creation failure:");
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      throw new ActorException();
    }
  }

  private void SetEffectsIntens(float paramFloat)
  {
    if (this.dying != 0)
      paramFloat = -1.0F;
    if (this.pipes != null)
    {
      i = 0;
      for (int j = 0; j < this.pipes.length; j++) {
        if (this.pipes[j] != null)
          if (this.pipes[j].pipe == null) {
            this.pipes[j] = null;
          }
          else if (paramFloat >= 0.0F)
          {
            this.pipes[j].pipe._setIntesity(paramFloat);
            i = 1;
          }
          else {
            this.pipes[j].pipe._finish();
            Pipe.access$4102(this.pipes[j], null);
            this.pipes[j] = null;
          }
      }
      if (i == 0)
      {
        for (int k = 0; k < this.pipes.length; k++) {
          if (this.pipes[k] != null)
            this.pipes[k] = null;
        }
        this.pipes = null;
      }
    }
    for (int i = 0; i < 3; i++) {
      if (this.wake[i] != null)
        if (paramFloat >= 0.0F)
        {
          this.wake[i]._setIntesity(paramFloat);
        }
        else {
          this.wake[i]._finish();
          this.wake[i] = null;
        }
    }
    if (this.noseW != null)
      if (paramFloat >= 0.0F)
      {
        this.noseW._setIntesity(paramFloat);
      }
      else {
        this.noseW._finish();
        this.noseW = null;
      }
    if (this.nose != null)
      if (paramFloat >= 0.0F)
      {
        this.nose._setIntesity(paramFloat);
      }
      else {
        this.nose._finish();
        this.nose = null;
      }
    if (this.tail != null)
      if (paramFloat >= 0.0F)
      {
        this.tail._setIntesity(paramFloat);
      }
      else {
        this.tail._finish();
        this.tail = null;
      }
  }

  private void LoadPath(SectFile paramSectFile, String paramString)
  {
    int i = paramSectFile.sectionIndex(paramString);
    if (i < 0)
      throw new ActorException("Ship path: Section [" + paramString + "] not found");
    int j = paramSectFile.vars(i);
    if (j < 1)
      throw new ActorException("Ship path must contain at least 2 nodes");
    this.path = new ArrayList();
    float f7;
    for (int k = 0; k < j; k++)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramSectFile.line(i, k));
      float f1 = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
      float f2 = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
      float f4 = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
      double d = 0.0D;
      f7 = 0.0F;
      if (localStringTokenizer.hasMoreTokens())
      {
        d = Double.valueOf(localStringTokenizer.nextToken()).doubleValue();
        if (localStringTokenizer.hasMoreTokens())
        {
          Double.valueOf(localStringTokenizer.nextToken()).doubleValue();
          if (localStringTokenizer.hasMoreTokens())
          {
            f7 = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
            if (f7 <= 0.0F)
              f7 = this.prop.SPEED;
          }
        }
      }
      if ((f7 <= 0.0F) && ((k == 0) || (k == j - 1)))
        f7 = this.prop.SPEED;
      if (k >= j - 1)
        d = -1.0D;
      Segment localSegment6 = new Segment(null);
      localSegment6.posIn = new Point3d(f1, f2, 0.0D);
      if (Math.abs(d) < 0.1D)
      {
        localSegment6.timeIn = 0L;
      }
      else {
        localSegment6.timeIn = ()(d * 60.0D * 1000.0D + (d > 0.0D ? 0.5D : -0.5D));
        if ((k == 0) && (localSegment6.timeIn < 0L))
          localSegment6.timeIn = (-localSegment6.timeIn);
      }
      localSegment6.speedIn = f7;
      this.path.add(localSegment6);
    }

    for (int m = 0; m < this.path.size() - 1; m++)
    {
      Segment localSegment1 = (Segment)this.path.get(m);
      Segment localSegment2 = (Segment)this.path.get(m + 1);
      localSegment1.length = (float)localSegment1.posIn.distance(localSegment2.posIn);
    }

    int n = 0;
    float f3 = ((Segment)this.path.get(n)).length;
    int i1;
    for (; n < this.path.size() - 1; n = i1)
    {
      i1 = n + 1;
      while (true)
      {
        Segment localSegment3 = (Segment)this.path.get(i1);
        if (localSegment3.speedIn > 0.0F)
          break;
        f3 += localSegment3.length;
        i1++;
      }
      if (i1 - n <= 1)
        continue;
      float f5 = ((Segment)this.path.get(n)).length;
      float f6 = ((Segment)this.path.get(n)).speedIn;
      f7 = ((Segment)this.path.get(i1)).speedIn;
      for (int i4 = n + 1; i4 < i1; i4++)
      {
        Segment localSegment8 = (Segment)this.path.get(i4);
        float f8 = f5 / f3;
        localSegment8.speedIn = (f6 * (1.0F - f8) + f7 * f8);
        f3 += localSegment8.length;
      }
    }
    Segment localSegment7;
    for (int i2 = 0; i2 < this.path.size() - 1; i2++)
    {
      Segment localSegment4 = (Segment)this.path.get(i2);
      localSegment5 = (Segment)this.path.get(i2 + 1);
      if ((localSegment4.timeIn <= 0L) || (localSegment5.timeIn <= 0L))
        continue;
      localSegment7 = new Segment(null);
      localSegment7.posIn = new Point3d(localSegment4.posIn);
      localSegment7.posIn.add(localSegment5.posIn);
      localSegment7.posIn.scale(0.5D);
      localSegment7.timeIn = 0L;
      localSegment7.speedIn = ((localSegment4.speedIn + localSegment5.speedIn) * 0.5F);
      this.path.add(i2 + 1, localSegment7);
    }

    for (int i3 = 0; i3 < this.path.size() - 1; i3++)
    {
      localSegment5 = (Segment)this.path.get(i3);
      localSegment7 = (Segment)this.path.get(i3 + 1);
      localSegment5.length = (float)localSegment5.posIn.distance(localSegment7.posIn);
    }

    Segment localSegment5 = (Segment)this.path.get(0);
    int i5 = localSegment5.timeIn != 0L ? 1 : 0;
    long l1 = localSegment5.timeIn;
    for (int i6 = 0; i6 < this.path.size() - 1; i6++)
    {
      Segment localSegment9 = (Segment)this.path.get(i6);
      Segment localSegment10 = (Segment)this.path.get(i6 + 1);
      localSegment9.posOut = new Point3d(localSegment10.posIn);
      localSegment10.posIn = localSegment9.posOut;
      float f9 = localSegment9.speedIn;
      float f10 = localSegment10.speedIn;
      float f11 = (f9 + f10) * 0.5F;
      float f12;
      if (i5 != 0)
      {
        localSegment9.speedIn = 0.0F;
        localSegment9.speedOut = f10;
        f12 = 2.0F * localSegment9.length / f10 * 1000.0F + 0.5F;
        localSegment9.timeIn = l1;
        localSegment9.timeOut = (localSegment9.timeIn + (int)f12);
        l1 = localSegment9.timeOut;
        i5 = 0;
      }
      else if (localSegment10.timeIn == 0L)
      {
        localSegment9.speedIn = f9;
        localSegment9.speedOut = f10;
        f12 = localSegment9.length / f11 * 1000.0F + 0.5F;
        localSegment9.timeIn = l1;
        localSegment9.timeOut = (localSegment9.timeIn + (int)f12);
        l1 = localSegment9.timeOut;
        i5 = 0;
      }
      else {
        if (localSegment10.timeIn > 0L)
        {
          f12 = localSegment9.length / f11 * 1000.0F + 0.5F;
          long l2 = l1 + (int)f12;
          if (l2 >= localSegment10.timeIn)
          {
            localSegment10.timeIn = 0L;
          }
          else {
            localSegment9.speedIn = f9;
            localSegment9.speedOut = 0.0F;
            float f13 = 2.0F * localSegment9.length / f9 * 1000.0F + 0.5F;
            localSegment9.timeIn = l1;
            localSegment9.timeOut = (localSegment9.timeIn + (int)f13);
            l1 = localSegment10.timeIn;
            i5 = 1;
            continue;
          }
        }
        if (localSegment10.timeIn == 0L)
        {
          localSegment9.speedIn = f9;
          localSegment9.speedOut = f10;
          f12 = localSegment9.length / f11 * 1000.0F + 0.5F;
          localSegment9.timeIn = l1;
          localSegment9.timeOut = (localSegment9.timeIn + (int)f12);
          l1 = localSegment9.timeOut;
          i5 = 0;
        }
        else {
          localSegment9.speedIn = f9;
          localSegment9.speedOut = 0.0F;
          f12 = 2.0F * localSegment9.length / f9 * 1000.0F + 0.5F;
          localSegment9.timeIn = l1;
          localSegment9.timeOut = (localSegment9.timeIn + (int)f12);
          l1 = localSegment9.timeOut + -localSegment10.timeIn;
          i5 = 1;
        }
      }
    }
    this.path.remove(this.path.size() - 1);
  }

  private void printPath(String paramString)
  {
    System.out.println("------------ Path: " + paramString + "  #:" + this.path.size());
    for (int i = 0; i < this.path.size(); i++)
    {
      Segment localSegment = (Segment)this.path.get(i);
      System.out.println(" " + i + ":  len=" + localSegment.length + " spdIn=" + localSegment.speedIn + " spdOut=" + localSegment.speedOut + " tmIn=" + localSegment.timeIn + " tmOut=" + localSegment.timeOut);
    }

    System.out.println("------------");
  }

  public void align()
  {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(p);
    p.jdField_z_of_type_Double = (Engine.land().HQ(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double) - this.bodyDepth);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(p);
  }

  private boolean computeInterpolatedDPR(long paramLong)
  {
    if ((this.tmInterpoStart >= this.tmInterpoEnd) || (paramLong >= this.tmInterpoEnd))
    {
      this.bodyDepth = this.bodyDepth1;
      this.bodyPitch = this.bodyPitch1;
      this.bodyRoll = this.bodyRoll1;
      return false;
    }
    if (paramLong <= this.tmInterpoStart)
    {
      this.bodyDepth = this.bodyDepth0;
      this.bodyPitch = this.bodyPitch0;
      this.bodyRoll = this.bodyRoll0;
      return true;
    }

    float f = (float)(paramLong - this.tmInterpoStart) / (float)(this.tmInterpoEnd - this.tmInterpoStart);
    this.bodyDepth = (this.bodyDepth0 + (this.bodyDepth1 - this.bodyDepth0) * f);
    this.bodyPitch = (this.bodyPitch0 + (this.bodyPitch1 - this.bodyPitch0) * f);
    this.bodyRoll = (this.bodyRoll0 + (this.bodyRoll1 - this.bodyRoll0) * f);
    return true;
  }

  private void setMovablePosition(long paramLong)
  {
    if (this.cachedSeg < 0) {
      this.cachedSeg = 0;
    }
    else if (this.cachedSeg >= this.path.size())
      this.cachedSeg = (this.path.size() - 1);
    Segment localSegment1 = (Segment)this.path.get(this.cachedSeg);
    if ((localSegment1.timeIn <= paramLong) && (paramLong <= localSegment1.timeOut))
    {
      SetEffectsIntens(1.0F);
      setMovablePosition((float)(paramLong - localSegment1.timeIn) / (float)(localSegment1.timeOut - localSegment1.timeIn));
      return;
    }
    Segment localSegment2;
    if (paramLong > localSegment1.timeOut)
    {
      while (this.cachedSeg + 1 < this.path.size())
      {
        localSegment2 = (Segment)this.path.get(++this.cachedSeg);
        if (paramLong <= localSegment2.timeIn)
        {
          SetEffectsIntens(0.0F);
          setMovablePosition(0.0F);
          return;
        }
        if (paramLong > localSegment2.timeOut)
          continue;
        SetEffectsIntens(1.0F);
        setMovablePosition((float)(paramLong - localSegment2.timeIn) / (float)(localSegment2.timeOut - localSegment2.timeIn));
        return;
      }

      SetEffectsIntens(-1.0F);
      setMovablePosition(1.0F);
      return;
    }
    do
    {
      localSegment2 = (Segment)this.path.get(--this.cachedSeg);
      if (paramLong >= localSegment2.timeOut)
      {
        SetEffectsIntens(0.0F);
        setMovablePosition(1.0F);
        return;
      }
      if (paramLong < localSegment2.timeIn)
        continue;
      SetEffectsIntens(1.0F);
      setMovablePosition((float)(paramLong - localSegment2.timeIn) / (float)(localSegment2.timeOut - localSegment2.timeIn));
      return;
    }
    while (this.cachedSeg > 0);

    SetEffectsIntens(0.0F);
    setMovablePosition(0.0F);
  }

  private void setMovablePosition(float paramFloat)
  {
    Segment localSegment1 = (Segment)this.path.get(this.cachedSeg);
    float f1 = (float)(localSegment1.timeOut - localSegment1.timeIn) * 0.001F;
    float f2 = localSegment1.speedIn;
    float f3 = localSegment1.speedOut;
    float f4 = (f3 - f2) / f1;
    paramFloat *= f1;
    float f5 = f2 * paramFloat + f4 * paramFloat * paramFloat * 0.5F;
    int i = this.cachedSeg;
    float f6 = this.prop.SLIDER_DIST - (localSegment1.length - f5);
    if (f6 <= 0.0F)
      p1.interpolate(localSegment1.posIn, localSegment1.posOut, (f5 + this.prop.SLIDER_DIST) / localSegment1.length);
    else
      while (true)
      {
        if (i + 1 >= this.path.size())
        {
          p1.interpolate(localSegment1.posIn, localSegment1.posOut, 1.0F + f6 / localSegment1.length);
          break;
        }
        i++; localSegment1 = (Segment)this.path.get(i);
        if (f6 <= localSegment1.length)
        {
          p1.interpolate(localSegment1.posIn, localSegment1.posOut, f6 / localSegment1.length);
          break;
        }
        f6 -= localSegment1.length;
      }
    i = this.cachedSeg;
    localSegment1 = (Segment)this.path.get(i);
    f6 = this.prop.SLIDER_DIST - f5;
    if (f6 <= 0.0F)
      p2.interpolate(localSegment1.posIn, localSegment1.posOut, (f5 - this.prop.SLIDER_DIST) / localSegment1.length);
    else
      while (true)
      {
        if (i <= 0)
        {
          p2.interpolate(localSegment1.posIn, localSegment1.posOut, 0.0F - f6 / localSegment1.length);
          break;
        }
        i--; localSegment1 = (Segment)this.path.get(i);
        if (f6 <= localSegment1.length)
        {
          p2.interpolate(localSegment1.posIn, localSegment1.posOut, 1.0F - f6 / localSegment1.length);
          break;
        }
        f6 -= localSegment1.length;
      }
    p.interpolate(p1, p2, 0.5F);
    tmpvd.sub(p1, p2);
    if (tmpvd.lengthSquared() < 0.001000000047497451D)
    {
      Segment localSegment2 = (Segment)this.path.get(this.cachedSeg);
      tmpvd.sub(localSegment2.posOut, localSegment2.posIn);
    }
    float f7 = (float)(Math.atan2(tmpvd.jdField_y_of_type_Double, tmpvd.jdField_x_of_type_Double) * 57.295779513082323D);
    setPosition(p, f7);
  }

  public void addRockingSpeed(Vector3d paramVector3d1, Vector3d paramVector3d2, Point3d paramPoint3d)
  {
    this.tmpV.sub(paramPoint3d, this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
    this.o.transformInv(this.tmpV);
    this.tmpV.cross(this.W, this.tmpV);
    this.o.transform(this.tmpV);
    paramVector3d1.add(this.tmpV);
    paramVector3d2.set(this.N);
  }

  private void setPosition(Point3d paramPoint3d, float paramFloat)
  {
    this.shipYaw = paramFloat;
    float f1 = (float)(NetServerParams.getServerTime() % this.rollPeriod) / this.rollPeriod;
    float f2 = 0.05F * (20.0F - Math.abs(this.bodyPitch));
    if (f2 < 0.0F)
      f2 = 0.0F;
    float f3 = this.rollAmp * f2 * (float)Math.sin(f1 * 2.0F * 3.141592653589793D);
    this.W.jdField_x_of_type_Double = (-this.rollWAmp * f2 * Math.cos(f1 * 2.0F * 3.141592653589793D));
    f1 = (float)(NetServerParams.getServerTime() % this.pitchPeriod) / this.pitchPeriod;
    float f4 = this.pitchAmp * f2 * (float)Math.sin(f1 * 2.0F * 3.141592653589793D);
    this.W.jdField_y_of_type_Double = (-this.pitchWAmp * f2 * Math.cos(f1 * 2.0F * 3.141592653589793D));
    this.o.setYPR(this.shipYaw, this.bodyPitch + f4, this.bodyRoll + f3);
    this.N.set(0.0D, 0.0D, 1.0D);
    this.o.transform(this.N);
    this.initOr.setYPR(this.shipYaw, this.bodyPitch, this.bodyRoll);
    paramPoint3d.jdField_z_of_type_Double = (-this.bodyDepth);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(paramPoint3d, this.o);
    this.initLoc.set(paramPoint3d, this.initOr);
  }

  private void setPosition()
  {
    this.o.setYPR(this.shipYaw, this.bodyPitch, this.bodyRoll);
    this.N.set(0.0D, 0.0D, 1.0D);
    this.o.transform(this.N);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.o);
    align();
    this.initLoc.set(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs());
  }

  public int WeaponsMask()
  {
    return this.prop.WEAPONS_MASK;
  }

  public int HitbyMask()
  {
    return this.prop.HITBY_MASK;
  }

  public int chooseBulletType(BulletProperties[] paramArrayOfBulletProperties)
  {
    if (this.dying != 0)
      return -1;
    if (paramArrayOfBulletProperties.length == 1)
      return 0;
    if (paramArrayOfBulletProperties.length <= 0)
      return -1;
    if (paramArrayOfBulletProperties[0].power <= 0.0F)
      return 0;
    if (paramArrayOfBulletProperties[1].power <= 0.0F)
      return 1;
    if (paramArrayOfBulletProperties[0].cumulativePower > 0.0F)
      return 0;
    if (paramArrayOfBulletProperties[1].cumulativePower > 0.0F)
      return 1;
    if (paramArrayOfBulletProperties[0].powerType == 0)
      return 0;
    if (paramArrayOfBulletProperties[1].powerType == 0) {
      return 1;
    }
    return paramArrayOfBulletProperties[0].powerType == 1 ? 1 : 0;
  }

  public int chooseShotpoint(BulletProperties paramBulletProperties)
  {
    if (this.dying != 0)
      return -1;
    if (this.numshotpoints <= 0) {
      return -1;
    }
    return this.shotpoints[Rnd(0, this.numshotpoints - 1)];
  }

  public boolean getShotpointOffset(int paramInt, Point3d paramPoint3d)
  {
    if (this.dying != 0)
      return false;
    if (this.numshotpoints <= 0)
      return false;
    if (paramInt == 0)
    {
      if (paramPoint3d != null)
        paramPoint3d.set(0.0D, 0.0D, 0.0D);
      return true;
    }
    int i = paramInt - 1;
    if ((i >= this.parts.length) || (i < 0))
      return false;
    if (this.parts[i].state == 2)
      return false;
    if ((!this.parts[i].pro.isItLifeKeeper()) && (!this.parts[i].pro.haveGun()))
      return false;
    if (paramPoint3d != null)
      paramPoint3d.set(this.parts[i].shotpointOffs);
    return true;
  }

  public float AttackMaxDistance()
  {
    return this.prop.ATTACK_MAX_DISTANCE;
  }

  private void send_DeathCommand(Actor paramActor, NetChannel paramNetChannel)
  {
    if (!isNetMaster())
      return;
    if (paramNetChannel == null)
      if (Mission.isDeathmatch())
      {
        float f = Mission.respawnTime("Bigship");
        this.respawnDelay = SecsToTicks(Rnd(f, f * 1.2F));
      }
      else {
        this.respawnDelay = 0L;
      }
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try
    {
      localNetMsgGuaranted.writeByte(68);
      localNetMsgGuaranted.writeLong(this.timeOfDeath);
      localNetMsgGuaranted.writeNetObj(paramActor == null ? null : paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
      long l1 = Time.tickNext();
      long l2 = 0L;
      int i = this.dying == 1 ? 1 : 0;
      double d = this.bodyDepth0 / 1000.0D;
      if (d <= 0.0D)
        d = 0.0D;
      if (d >= 1.0D)
        d = 1.0D;
      int j = (int)(d * 32767.0D);
      if (j > 32767)
        j = 32767;
      if (j < 0)
        j = 0;
      localNetMsgGuaranted.writeShort(j);
      d = this.bodyPitch0 / 90.0D;
      if (d <= -1.0D)
        d = -1.0D;
      if (d >= 1.0D)
        d = 1.0D;
      j = (int)(d * 32767.0D);
      if (j > 32767)
        j = 32767;
      if (j < -32767)
        j = -32767;
      localNetMsgGuaranted.writeShort(j);
      d = this.bodyRoll0 / 90.0D;
      if (d <= -1.0D)
        d = -1.0D;
      if (d >= 1.0D)
        d = 1.0D;
      j = (int)(d * 32767.0D);
      if (j > 32767)
        j = 32767;
      if (j < -32767)
        j = -32767;
      localNetMsgGuaranted.writeShort(j);
      d = (this.tmInterpoEnd - this.tmInterpoStart) / 1000.0D / 1200.0D;
      if (i != 0)
        l2 = l1 - this.tmInterpoStart;
      else
        d = 0.0D;
      if (d <= 0.0D)
        d = 0.0D;
      if (d >= 1.0D)
        d = 1.0D;
      j = (int)(d * 32767.0D);
      if (j > 32767)
        j = 32767;
      if (j < 0)
        j = 0;
      localNetMsgGuaranted.writeShort(j);
      d = this.sink2Depth / 1000.0D;
      if (d <= 0.0D)
        d = 0.0D;
      if (d >= 1.0D)
        d = 1.0D;
      j = (int)(d * 32767.0D);
      if (j > 32767)
        j = 32767;
      if (j < 0)
        j = 0;
      localNetMsgGuaranted.writeShort(j);
      d = this.sink2Pitch / 90.0D;
      if (d <= -1.0D)
        d = -1.0D;
      if (d >= 1.0D)
        d = 1.0D;
      j = (int)(d * 32767.0D);
      if (j > 32767)
        j = 32767;
      if (j < -32767)
        j = -32767;
      localNetMsgGuaranted.writeShort(j);
      d = this.sink2Roll / 90.0D;
      if (d <= -1.0D)
        d = -1.0D;
      if (d >= 1.0D)
        d = 1.0D;
      j = (int)(d * 32767.0D);
      if (j > 32767)
        j = 32767;
      if (j < -32767)
        j = -32767;
      localNetMsgGuaranted.writeShort(j);
      d = (this.sink2timeWhenStop - this.tmInterpoEnd) / 1000.0D / 1200.0D;
      if (i == 0)
      {
        d = (this.tmInterpoEnd - this.tmInterpoStart) / 1000.0D / 1200.0D;
        l2 = l1 - this.tmInterpoStart;
      }
      if (d <= 0.0D)
        d = 0.0D;
      if (d >= 1.0D)
        d = 1.0D;
      j = (int)(d * 32767.0D);
      if (j > 32767)
        j = 32767;
      if (j < 0)
        j = 0;
      localNetMsgGuaranted.writeShort(j);
      if (paramNetChannel != null)
        localNetMsgGuaranted.writeLong(l2);
      if (paramNetChannel == null)
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted);
      else
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(paramNetChannel, localNetMsgGuaranted);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void send_RespawnCommand()
  {
    if ((!isNetMaster()) || (!Mission.isDeathmatch()))
      return;
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try
    {
      localNetMsgGuaranted.writeByte(82);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    this.netsendPartsState_needtosend = false;
  }

  private void send_bufferized_FireCommand()
  {
    if (!isNetMaster())
      return;
    long l1 = NetServerParams.getServerTime();
    long l2 = Rnd(40, 85);
    if (Math.abs(l1 - this.netsendFire_lasttimeMS) < l2)
      return;
    this.netsendFire_lasttimeMS = l1;
    if (!this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored())
    {
      for (i = 0; i < this.arms.length; i++) {
        FiringDevice.access$5402(this.arms[i], null);
      }
      this.netsendFire_armindex = 0;
      return;
    }
    int i = 0;
    int j = 0;

    for (int k = 0; k < this.arms.length; k++)
    {
      int m = this.netsendFire_armindex + k;
      if (m >= this.arms.length)
        m -= this.arms.length;
      if (this.arms[m].enemy == null)
        continue;
      if (this.parts[FiringDevice.access$1000(this.arms[m])].state != 0)
      {
        System.out.println("*** BigShip internal error #0");
        FiringDevice.access$5402(this.arms[m], null);
      }
      else if ((!Actor.isValid(this.arms[m].enemy)) || (!this.arms[m].enemy.isNet()))
      {
        FiringDevice.access$5402(this.arms[m], null);
      }
      else {
        if (i >= 15)
          break;
        TmpTrackOrFireInfo.access$6102(netsendFire_tmpbuff[i], m);
        TmpTrackOrFireInfo.access$6202(netsendFire_tmpbuff[i], this.arms[m].enemy);
        TmpTrackOrFireInfo.access$6302(netsendFire_tmpbuff[i], this.arms[m].timeWhenFireS);
        TmpTrackOrFireInfo.access$6502(netsendFire_tmpbuff[i], this.arms[m].shotpointIdx);
        FiringDevice.access$5402(this.arms[m], null);
        if (this.arms[m].timeWhenFireS < 0.0D)
          j++;
        i++;
      }
    }
    for (this.netsendFire_armindex += k; this.netsendFire_armindex >= this.arms.length; this.netsendFire_armindex -= this.arms.length);
    if (i <= 0)
      return; NetMsgFiltered localNetMsgFiltered = new NetMsgFiltered();
    double d1;
    try { localNetMsgFiltered.writeByte(224 + j);
      for (int n = 0; n < i; n++)
      {
        d1 = netsendFire_tmpbuff[n].timeWhenFireS;
        if (d1 >= 0.0D)
          continue;
        localNetMsgFiltered.writeByte(netsendFire_tmpbuff[n].gun_idx);
        localNetMsgFiltered.writeNetObj(netsendFire_tmpbuff[n].enemy.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
        localNetMsgFiltered.writeByte(netsendFire_tmpbuff[n].shotpointIdx);
        j--;
      }
    }
    catch (IOException localIOException)
    {
    }
    if (j != 0)
    {
      System.out.println("*** BigShip internal error #5");
      return;
    }
    try
    {
      for (int i1 = 0; i1 < i; i1++)
      {
        d1 = netsendFire_tmpbuff[i1].timeWhenFireS;
        if (d1 < 0.0D)
          continue;
        double d2 = l1 * 0.001D;
        double d3 = (d1 - d2) * 1000.0D;
        if (d3 <= -2000.0D)
          d3 = -2000.0D;
        if (d3 >= 5000.0D)
          d3 = 5000.0D;
        d3 = (d3 - -2000.0D) / 7000.0D;
        int i2 = (int)(d3 * 255.0D);
        if (i2 < 0)
          i2 = 0;
        if (i2 > 255)
          i2 = 255;
        localNetMsgFiltered.writeByte(i2);
        localNetMsgFiltered.writeByte(netsendFire_tmpbuff[i1].gun_idx);
        localNetMsgFiltered.writeNetObj(netsendFire_tmpbuff[i1].enemy.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
        localNetMsgFiltered.writeByte(netsendFire_tmpbuff[i1].shotpointIdx);
        TmpTrackOrFireInfo.access$6202(netsendFire_tmpbuff[i1], null);
      }

      localNetMsgFiltered.setIncludeTime(true);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(l1, localNetMsgFiltered);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void send_bufferized_PartsState()
  {
    if (!isNetMaster())
      return;
    if (!this.netsendPartsState_needtosend)
      return;
    long l1 = NetServerParams.getServerTime();
    long l2 = Rnd(650, 1100);
    if (Math.abs(l1 - this.netsendPartsState_lasttimeMS) < l2)
      return;
    this.netsendPartsState_lasttimeMS = l1;
    this.netsendPartsState_needtosend = false;
    if (!this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored())
      return;
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try
    {
      localNetMsgGuaranted.writeByte(83);
      int i = (this.parts.length + 3) / 4;
      int j = 0;
      for (int k = 0; k < i; k++)
      {
        int m = 0;
        for (int n = 0; n < 4; n++)
        {
          if (j < this.parts.length)
          {
            int i1 = this.parts[j].state;
            m |= i1 << n * 2;
          }
          j++;
        }

        localNetMsgGuaranted.writeByte(m);
      }

      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted);
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void bufferize_FireCommand(int paramInt1, Actor paramActor, int paramInt2, float paramFloat)
  {
    if (!isNetMaster())
      return;
    if (!this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored())
      return;
    if ((!Actor.isValid(paramActor)) || (!paramActor.isNet()))
      return;
    if ((this.arms[paramInt1].enemy != null) && (this.arms[paramInt1].timeWhenFireS >= 0.0D))
      return;
    paramInt2 &= 255;
    FiringDevice.access$5402(this.arms[paramInt1], paramActor);
    FiringDevice.access$6602(this.arms[paramInt1], paramInt2);
    if (paramFloat < 0.0F)
      FiringDevice.access$6402(this.arms[paramInt1], -1.0D);
    else
      FiringDevice.access$6402(this.arms[paramInt1], paramFloat + NetServerParams.getServerTime() * 0.001D);
  }

  private void mirror_send_bufferized_Damage()
  {
    if (!isNetMirror())
      return;
    if ((this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel() instanceof NetChannelInStream))
      return;
    long l1 = NetServerParams.getServerTime();
    long l2 = Rnd(65, 115);
    if (Math.abs(l1 - this.netsendDmg_lasttimeMS) < l2)
      return;
    this.netsendDmg_lasttimeMS = l1;
    try
    {
      int i = 0;
      NetMsgFiltered localNetMsgFiltered = null;

      for (int j = 0; j < this.parts.length; j++)
      {
        int k = this.netsendDmg_partindex + j;
        if (k >= this.parts.length)
          k -= this.parts.length;
        if ((this.parts[k].state == 2) || (this.parts[k].damage < 0.0078125D))
          continue;
        int m = (int)(this.parts[k].damage * 128.0F);
        m--; if (m < 0) {
          m = 0;
        }
        else if (m > 127)
          m = 127;
        if (this.parts[k].damageIsFromRight)
          m |= 128;
        if (i <= 0)
        {
          localNetMsgFiltered = new NetMsgFiltered();
          localNetMsgFiltered.writeByte(80);
        }
        Actor localActor = this.parts[k].mirror_initiator;
        if ((!Actor.isValid(localActor)) || (!localActor.isNet()))
          localActor = null;
        Part.access$4702(this.parts[k], null);
        Part.access$4302(this.parts[k], 0.0F);
        localNetMsgFiltered.writeByte(k);
        localNetMsgFiltered.writeByte(m);
        localNetMsgFiltered.writeNetObj(localActor == null ? null : localActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
        i++; if (i >= 14) {
          break;
        }
      }
      for (this.netsendDmg_partindex += j; this.netsendDmg_partindex >= this.parts.length; this.netsendDmg_partindex -= this.parts.length);
      if (i > 0)
      {
        localNetMsgFiltered.setIncludeTime(false);
        this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(l1, this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel(), localNetMsgFiltered);
      }
    }
    catch (Exception localException)
    {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  public void createNetObject(NetChannel paramNetChannel, int paramInt)
  {
    if (paramNetChannel == null)
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new Master(this);
    else
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new Mirror(this, paramNetChannel, paramInt);
  }

  public void netFirstUpdate(NetChannel paramNetChannel)
    throws IOException
  {
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    localNetMsgGuaranted.writeByte(73);
    localNetMsgGuaranted.writeLong(-1L);
    this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(paramNetChannel, localNetMsgGuaranted);
    if (this.dying == 0)
      master_sendDrown(this.bodyDepth1, this.bodyPitch1, this.bodyRoll1, (float)(this.tmInterpoEnd - NetServerParams.getServerTime()) * 1000.0F);
    else
      send_DeathCommand(null, paramNetChannel);
    this.netsendPartsState_needtosend = true;
  }

  public float getReloadingTime(Aim paramAim)
  {
    return this.SLOWFIRE_K * GetGunProperties(paramAim).DELAY_AFTER_SHOOT;
  }

  public float chainFireTime(Aim paramAim)
  {
    float f = GetGunProperties(paramAim).CHAINFIRE_TIME;
    return f <= 0.0F ? 0.0F : f * Rnd(0.75F, 1.25F);
  }

  public float probabKeepSameEnemy(Actor paramActor)
  {
    return 0.75F;
  }

  public float minTimeRelaxAfterFight()
  {
    return 0.1F;
  }

  public void gunStartParking(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    ShipPartProperties localShipPartProperties = this.parts[localFiringDevice.part_idx].pro;
    paramAim.setRotationForParking(localFiringDevice.headYaw, localFiringDevice.gunPitch, localShipPartProperties.HEAD_STD_YAW, localShipPartProperties.GUN_STD_PITCH, localShipPartProperties.HEAD_YAW_RANGE, localShipPartProperties.HEAD_MAX_YAW_SPEED, localShipPartProperties.GUN_MAX_PITCH_SPEED);
  }

  public void gunInMove(boolean paramBoolean, Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    float f1 = paramAim.t();
    float f2 = paramAim.anglesYaw.getDeg(f1);
    float f3 = paramAim.anglesPitch.getDeg(f1);
    setGunAngles(localFiringDevice, f2, f3);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(false);
  }

  public Actor findEnemy(Aim paramAim)
  {
    if (isNetMirror())
      return null;
    ShipPartProperties localShipPartProperties = GetGunProperties(paramAim);
    Actor localActor = null;
    switch (localShipPartProperties.ATTACK_FAST_TARGETS)
    {
    case 0:
      NearestEnemies.set(localShipPartProperties.WEAPONS_MASK, -9999.9004F, KmHourToMSec(100.0F));
      break;
    case 1:
      NearestEnemies.set(localShipPartProperties.WEAPONS_MASK);
      break;
    default:
      NearestEnemies.set(localShipPartProperties.WEAPONS_MASK, KmHourToMSec(100.0F), 9999.9004F);
    }

    localActor = NearestEnemies.getAFoundEnemy(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), localShipPartProperties.ATTACK_MAX_RADIUS, getArmy());
    if (localActor == null)
      return null;
    if (!(localActor instanceof Prey))
    {
      System.out.println("bigship: nearest enemies: non-Prey");
      return null;
    }
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    BulletProperties localBulletProperties = null;
    if (localFiringDevice.gun.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties != null)
    {
      i = ((Prey)localActor).chooseBulletType(localFiringDevice.gun.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet);
      if (i < 0)
        return null;
      localBulletProperties = localFiringDevice.gun.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[i];
    }
    int i = ((Prey)localActor).chooseShotpoint(localBulletProperties);
    if (i < 0)
    {
      return null;
    }

    paramAim.shotpoint_idx = i;
    return localActor;
  }

  public boolean enterToFireMode(int paramInt, Actor paramActor, float paramFloat, Aim paramAim)
  {
    if (!isNetMirror())
    {
      FiringDevice localFiringDevice = GetFiringDevice(paramAim);
      bufferize_FireCommand(localFiringDevice.gun_idx, paramActor, paramAim.shotpoint_idx, paramInt == 0 ? -1.0F : paramFloat);
    }
    return true;
  }

  private void Track_Mirror(int paramInt1, Actor paramActor, int paramInt2)
  {
    if (paramActor == null)
      return;
    if ((this.arms == null) || (paramInt1 < 0) || (paramInt1 >= this.arms.length) || (this.arms[paramInt1].aime == null))
      return;
    if (this.parts[FiringDevice.access$1000(this.arms[paramInt1])].state != 0)
    {
      return;
    }

    this.arms[paramInt1].aime.passive_StartFiring(0, paramActor, paramInt2, 0.0F);
  }

  private void Fire_Mirror(int paramInt1, Actor paramActor, int paramInt2, float paramFloat)
  {
    if (paramActor == null)
      return;
    if ((this.arms == null) || (paramInt1 < 0) || (paramInt1 >= this.arms.length) || (this.arms[paramInt1].aime == null))
      return;
    if (this.parts[FiringDevice.access$1000(this.arms[paramInt1])].state != 0)
      return;
    if (paramFloat <= 0.15F)
      paramFloat = 0.15F;
    if (paramFloat >= 7.0F)
      paramFloat = 7.0F;
    this.arms[paramInt1].aime.passive_StartFiring(1, paramActor, paramInt2, paramFloat);
  }

  public int targetGun(Aim paramAim, Actor paramActor, float paramFloat, boolean paramBoolean)
  {
    if ((!Actor.isValid(paramActor)) || (!paramActor.isAlive()) || (paramActor.getArmy() == 0))
      return 0;
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    if ((localFiringDevice.gun instanceof CannonMidrangeGeneric))
    {
      int i = ((Prey)paramActor).chooseBulletType(localFiringDevice.gun.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet);
      if (i < 0)
        return 0;
      ((CannonMidrangeGeneric)localFiringDevice.gun).setBulletType(i);
    }
    boolean bool = ((Prey)paramActor).getShotpointOffset(paramAim.shotpoint_idx, p1);
    if (!bool)
      return 0;
    ShipPartProperties localShipPartProperties = this.parts[localFiringDevice.part_idx].pro;
    float f1 = paramFloat * Rnd(0.8F, 1.2F);
    if (!Aimer.Aim((BulletAimer)localFiringDevice.gun, paramActor, this, f1, p1, localShipPartProperties.fireOffset))
      return 0;
    Point3d localPoint3d1 = new Point3d();
    Aimer.GetPredictedTargetPosition(localPoint3d1);
    Point3d localPoint3d2 = Aimer.GetHunterFirePoint();
    float f2 = 0.05F;
    double d1 = localPoint3d1.distance(localPoint3d2);
    double d2 = localPoint3d1.jdField_z_of_type_Double;
    localPoint3d1.sub(localPoint3d2);
    localPoint3d1.scale(Rnd(0.995D, 1.005D));
    localPoint3d1.add(localPoint3d2);
    if (f1 > 0.001F)
    {
      Point3d localPoint3d3 = new Point3d();
      paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(localPoint3d3);
      tmpvd.sub(localPoint3d1, localPoint3d3);
      double d3 = tmpvd.length();
      if (d3 > 0.001D)
      {
        float f6 = (float)d3 / f1;
        if (f6 > 200.0F)
          f6 = 200.0F;
        float f7 = f6 * 0.01F;
        localPoint3d3.sub(localPoint3d2);
        double d4 = localPoint3d3.jdField_x_of_type_Double * localPoint3d3.jdField_x_of_type_Double + localPoint3d3.jdField_y_of_type_Double * localPoint3d3.jdField_y_of_type_Double + localPoint3d3.jdField_z_of_type_Double * localPoint3d3.jdField_z_of_type_Double;
        if (d4 > 0.01D)
        {
          float f8 = (float)tmpvd.dot(localPoint3d3);
          f8 /= (float)(d3 * Math.sqrt(d4));
          f8 = (float)Math.sqrt(1.0F - f8 * f8);
          f7 *= (0.4F + 0.6F * f8);
        }
        f7 *= 1.3F;
        f7 *= Aim.AngleErrorKoefForSkill[this.SKILL_IDX];
        int k = Mission.curCloudsType();
        if (k > 2)
        {
          float f9 = k > 4 ? 400.0F : 800.0F;
          float f10 = (float)(d1 / f9);
          if (f10 > 1.0F)
          {
            if (f10 > 10.0F)
              return 0;
            f10 = (f10 - 1.0F) / 9.0F;
            f7 *= (f10 + 1.0F);
          }
        }
        if ((k >= 3) && (d2 > Mission.curCloudsHeight()))
          f7 *= 1.25F;
        f2 += f7;
      }
    }
    if (World.Sun().ToSun.jdField_z_of_type_Float < -0.15F)
    {
      f3 = (-World.Sun().ToSun.jdField_z_of_type_Float - 0.15F) / 0.13F;
      if (f3 >= 1.0F)
        f3 = 1.0F;
      if (((paramActor instanceof Aircraft)) && (NetServerParams.getServerTime() - ((Aircraft)paramActor).tmSearchlighted < 1000L))
        f3 = 0.0F;
      f2 += 10.0F * f3;
    }
    float f3 = (float)paramActor.getSpeed(null) - 10.0F;
    if (f3 > 0.0F)
    {
      float f4 = 83.333344F;
      f3 = f3 >= f4 ? 1.0F : f3 / f4;
      f2 += f3 * localShipPartProperties.FAST_TARGETS_ANGLE_ERROR;
    }
    Vector3d localVector3d = new Vector3d();
    if (!((BulletAimer)localFiringDevice.gun).FireDirection(localPoint3d2, localPoint3d1, localVector3d))
      return 0;
    float f5;
    if (paramBoolean)
    {
      f5 = 99999.0F;
      d2 = 99999.0D;
    }
    else {
      f5 = localShipPartProperties.HEAD_MAX_YAW_SPEED;
      d2 = localShipPartProperties.GUN_MAX_PITCH_SPEED;
    }
    this.o.add(localShipPartProperties.fireOrient, this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().getOrient());
    int j = paramAim.setRotationForTargeting(this, this.o, localPoint3d2, localFiringDevice.headYaw, localFiringDevice.gunPitch, localVector3d, f2, f1, localShipPartProperties.HEAD_YAW_RANGE, localShipPartProperties.GUN_MIN_PITCH, localShipPartProperties.GUN_MAX_PITCH, f5, (float)d2, 0.0F);
    return j;
  }

  public void singleShot(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    if (!this.parts[localFiringDevice.part_idx].pro.TRACKING_ONLY)
      localFiringDevice.gun.shots(1);
  }

  public void startFire(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    if (!this.parts[localFiringDevice.part_idx].pro.TRACKING_ONLY)
      localFiringDevice.gun.shots(-1);
  }

  public void continueFire(Aim paramAim)
  {
  }

  public void stopFire(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    if (!this.parts[localFiringDevice.part_idx].pro.TRACKING_ONLY)
      localFiringDevice.gun.shots(0);
  }

  public boolean isVisibilityLong()
  {
    return true;
  }

  private void createAirport()
  {
    if (this.prop.propAirport != null)
    {
      this.prop.propAirport.firstInit(this);
      this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw = new TowStringMeshDraw(this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw);
      if (this.prop.propAirport.cellTO != null)
        this.cellTO = ((CellAirField)this.prop.propAirport.cellTO.getClone());
      if (this.prop.propAirport.cellLDG != null)
        this.cellLDG = ((CellAirField)this.prop.propAirport.cellLDG.getClone());
      this.airport = new AirportCarrier(this, this.prop.propAirport.rwy);
    }
  }

  public AirportCarrier getAirport()
  {
    return this.airport;
  }

  public CellAirField getCellTO()
  {
    return this.cellTO;
  }

  public CellAirField getCellLDR()
  {
    return this.cellLDG;
  }

  private void validateTowAircraft()
  {
    if (this.towPortNum < 0)
      return;
    if (!Actor.isValid(this.towAircraft))
    {
      requestDetowAircraft(this.towAircraft);
      return;
    }
    if (this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().distance(this.towAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint()) > hierMesh().visibilityR())
    {
      requestDetowAircraft(this.towAircraft);
      return;
    }
    if (!this.towAircraft.FM.CT.bHasArrestorControl)
    {
      requestDetowAircraft(this.towAircraft);
      return;
    }
  }

  public void forceTowAircraft(Aircraft paramAircraft, int paramInt)
  {
    if (this.towPortNum >= 0)
    {
      return;
    }

    this.towPortNum = paramInt;
    this.towAircraft = paramAircraft;
    this.towHook = new HookNamed(paramAircraft, "_ClipAGear");
  }

  public void requestTowAircraft(Aircraft paramAircraft)
  {
    if ((this.towPortNum >= 0) || (this.prop.propAirport.towPRel == null))
      return;
    HookNamed localHookNamed = new HookNamed(paramAircraft, "_ClipAGear");
    Point3d[] arrayOfPoint3d = this.prop.propAirport.towPRel;
    Point3d localPoint3d1 = new Point3d();
    Point3d localPoint3d2 = new Point3d();
    Point3d localPoint3d3 = new Point3d();
    Point3d localPoint3d4 = new Point3d();
    Loc localLoc1 = new Loc();
    Loc localLoc2 = new Loc();
    for (int i = 0; i < arrayOfPoint3d.length / 2; i++)
    {
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent(localLoc1);
      localPoint3d3.set(arrayOfPoint3d[(i + i)]);
      localLoc1.transform(localPoint3d3);
      localPoint3d4.set(arrayOfPoint3d[(i + i + 1)]);
      localLoc1.transform(localPoint3d4);
      paramAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrent(localLoc2);
      localLoc1.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      localHookNamed.computePos(paramAircraft, localLoc2, localLoc1);
      localPoint3d1.set(localLoc1.getPoint());
      paramAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getPrev(localLoc2);
      localLoc1.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
      localHookNamed.computePos(paramAircraft, localLoc2, localLoc1);
      localPoint3d2.set(localLoc1.getPoint());
      if (localPoint3d2.jdField_z_of_type_Double >= localPoint3d3.jdField_z_of_type_Double + 0.5D * (localPoint3d4.jdField_z_of_type_Double - localPoint3d3.jdField_z_of_type_Double) + 0.2D)
        continue;
      Line2d localLine2d1 = new Line2d(new Point2d(localPoint3d3.jdField_x_of_type_Double, localPoint3d3.jdField_y_of_type_Double), new Point2d(localPoint3d4.jdField_x_of_type_Double, localPoint3d4.jdField_y_of_type_Double));
      Line2d localLine2d2 = new Line2d(new Point2d(localPoint3d1.jdField_x_of_type_Double, localPoint3d1.jdField_y_of_type_Double), new Point2d(localPoint3d2.jdField_x_of_type_Double, localPoint3d2.jdField_y_of_type_Double));
      try
      {
        Point2d localPoint2d = localLine2d1.crossPRE(localLine2d2);
        double d1 = Math.min(localPoint3d3.jdField_x_of_type_Double, localPoint3d4.jdField_x_of_type_Double);
        double d2 = Math.max(localPoint3d3.jdField_x_of_type_Double, localPoint3d4.jdField_x_of_type_Double);
        double d3 = Math.min(localPoint3d3.jdField_y_of_type_Double, localPoint3d4.jdField_y_of_type_Double);
        double d4 = Math.max(localPoint3d3.jdField_y_of_type_Double, localPoint3d4.jdField_y_of_type_Double);
        if ((localPoint2d.jdField_x_of_type_Double > d1) && (localPoint2d.jdField_x_of_type_Double < d2) && (localPoint2d.jdField_y_of_type_Double > d3) && (localPoint2d.jdField_y_of_type_Double < d4))
        {
          double d5 = Math.min(localPoint3d1.jdField_x_of_type_Double, localPoint3d2.jdField_x_of_type_Double);
          double d6 = Math.max(localPoint3d1.jdField_x_of_type_Double, localPoint3d2.jdField_x_of_type_Double);
          double d7 = Math.min(localPoint3d1.jdField_y_of_type_Double, localPoint3d2.jdField_y_of_type_Double);
          double d8 = Math.max(localPoint3d1.jdField_y_of_type_Double, localPoint3d2.jdField_y_of_type_Double);
          if ((localPoint2d.jdField_x_of_type_Double > d5) && (localPoint2d.jdField_x_of_type_Double < d6) && (localPoint2d.jdField_y_of_type_Double > d7) && (localPoint2d.jdField_y_of_type_Double < d8))
          {
            this.towPortNum = i;
            this.towAircraft = paramAircraft;
            this.towHook = new HookNamed(paramAircraft, "_ClipAGear");
            return;
          }
        }
      }
      catch (Exception localException)
      {
      }
    }
  }

  public void requestDetowAircraft(Aircraft paramAircraft)
  {
    if (paramAircraft == this.towAircraft)
    {
      this.towAircraft = null;
      this.towPortNum = -1;
    }
  }

  public boolean isTowAircraft(Aircraft paramAircraft)
  {
    return this.towAircraft == paramAircraft;
  }

  public double getSpeed(Vector3d paramVector3d)
  {
    if (this.path == null)
      return super.getSpeed(paramVector3d);
    long l = NetServerParams.getServerTime();
    if (l > Time.tickLen() * 4)
      return super.getSpeed(paramVector3d);
    Segment localSegment = (Segment)this.path.get(0);
    tmpDir.sub(localSegment.posOut, localSegment.posIn);
    tmpDir.normalize();
    tmpDir.scale(localSegment.speedIn);
    if (paramVector3d != null)
      paramVector3d.set(tmpDir);
    return tmpDir.length();
  }

  static
  {
    netsendFire_tmpbuff = new TmpTrackOrFireInfo[31];
    for (int i = 0; i < netsendFire_tmpbuff.length; i++)
      netsendFire_tmpbuff[i] = new TmpTrackOrFireInfo();
  }

  private static class TowStringMeshDraw extends ActorMeshDraw
  {
    private static Loc lRender = new Loc();
    private static Loc l = new Loc();
    private static Vector3d tmpVector = new Vector3d();
    private static Point3d p0 = new Point3d();
    private static Point3d p1 = new Point3d();

    public void render(Actor paramActor)
    {
      super.render(paramActor);
      BigshipGeneric localBigshipGeneric = (BigshipGeneric)paramActor;
      if (localBigshipGeneric.prop.propAirport == null)
        return;
      Point3d[] arrayOfPoint3d = localBigshipGeneric.prop.propAirport.towPRel;
      if (arrayOfPoint3d == null)
        return;
      paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(lRender);
      int i = arrayOfPoint3d.length / 2;
      for (int j = 0; j < i; j++)
        if (j != localBigshipGeneric.towPortNum)
        {
          lRender.transform(arrayOfPoint3d[(j * 2)], p0);
          lRender.transform(arrayOfPoint3d[(j * 2 + 1)], p1);
          renderTow(localBigshipGeneric.prop.propAirport.towString);
        } else {
          if (!Actor.isValid(localBigshipGeneric.towAircraft))
            continue;
          lRender.transform(arrayOfPoint3d[(j * 2)], p0);
          l.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
          localBigshipGeneric.towHook.computePos(localBigshipGeneric.towAircraft, localBigshipGeneric.towAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(), l);
          p1.set(l.getPoint());
          renderTow(localBigshipGeneric.prop.propAirport.towString);
          lRender.transform(arrayOfPoint3d[(j * 2 + 1)], p0);
          l.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
          localBigshipGeneric.towHook.computePos(localBigshipGeneric.towAircraft, localBigshipGeneric.towAircraft.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(), l);
          p1.set(l.getPoint());
          renderTow(localBigshipGeneric.prop.propAirport.towString);
        }
    }

    private void renderTow(Mesh paramMesh)
    {
      tmpVector.sub(p1, p0);
      paramMesh.setScaleXYZ((float)tmpVector.length(), 1.0F, 1.0F);
      tmpVector.normalize();
      Orient localOrient = l.getOrient();
      localOrient.setAT0(tmpVector);
      l.set(p0);
      paramMesh.setPos(l);
      paramMesh.render();
    }

    public TowStringMeshDraw(ActorDraw paramActorDraw)
    {
      super();
    }
  }

  public static class AirportProperties
  {
    public Loc[] rwy = { new Loc(), new Loc() };
    public Mesh towString;
    public Point3d[] towPRel;
    public CellAirField cellTO;
    public CellAirField cellLDG;
    private boolean bInited;
    private static Loc loc = new Loc();
    private static Point3d p = new Point3d();
    private static Orient o = new Orient();
    private static Matrix4d m1 = new Matrix4d();
    private static double[] tmp = new double[3];

    public void firstInit(BigshipGeneric paramBigshipGeneric)
    {
      if (this.bInited)
        return;
      this.bInited = true;
      HierMesh localHierMesh = paramBigshipGeneric.hierMesh();
      findHook(localHierMesh, "_RWY_TO", this.rwy[0]);
      findHook(localHierMesh, "_RWY_LDG", this.rwy[1]);
      this.towString = new Mesh("3DO/Arms/ArrestorCable/mono.sim");
      ArrayList localArrayList = new ArrayList();
      int i = 0;
      while (true)
      {
        String str = "0" + i;
        if (!findHook(localHierMesh, "_TOW" + str + "A", loc))
          break;
        localArrayList.add(new Point3d(loc.getPoint()));
        findHook(localHierMesh, "_TOW" + str + "B", loc);
        localArrayList.add(new Point3d(loc.getPoint()));
        i++;
      }
      if (i > 0)
      {
        i *= 2;
        this.towPRel = new Point3d[i];
        for (int j = 0; j < i; j++) {
          this.towPRel[j] = ((Point3d)localArrayList.get(j));
        }
      }
      fillParks(paramBigshipGeneric, localHierMesh, "_Park", localArrayList);
      if (localArrayList.size() > 0)
        this.cellTO = new CellAirField(new CellObject[1][1], localArrayList, 1.0D);
      fillParks(paramBigshipGeneric, localHierMesh, "_LPark", localArrayList);
      if (localArrayList.size() > 0)
        this.cellLDG = new CellAirField(new CellObject[1][1], localArrayList, 1.0D);
    }

    private void fillParks(BigshipGeneric paramBigshipGeneric, HierMesh paramHierMesh, String paramString, ArrayList paramArrayList)
    {
      paramArrayList.clear();
      int i = 0;
      while (true)
      {
        String str = paramString + (i > 9 ? "" + i : new StringBuffer().append("0").append(i).toString());
        if (!findHook(paramHierMesh, str, loc))
          break;
        paramArrayList.add(new Point3d(-p.y, p.x, p.z));
        i++;
      }
    }

    private boolean findHook(HierMesh paramHierMesh, String paramString, Loc paramLoc)
    {
      int i = paramHierMesh.hookFind(paramString);
      if (i == -1)
      {
        return false;
      }

      paramHierMesh.hookMatrix(i, m1);
      m1.getEulers(tmp);
      o.setYPR(Geom.RAD2DEG((float)tmp[0]), 360.0F - Geom.RAD2DEG((float)tmp[1]), 360.0F - Geom.RAD2DEG((float)tmp[2]));
      p.set(m1.m03, m1.m13, m1.m23);
      paramLoc.set(p, o);
      return true;
    }

    public AirportProperties(Class paramClass)
    {
      this.bInited = false;
      Property.set(paramClass, "IsAirport", "true");
    }
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Class cls;
    public BigshipGeneric.ShipProperties proper;

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2)
    {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2))
      {
        if (f == -9865.3447F)
          System.out.println("Ship: Value of [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
        else
          System.out.println("Ship: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
        throw new RuntimeException("Can't set property");
      }

      return f;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2)
    {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0))
      {
        System.out.print("Ship: Value of [" + paramString1 + "]:<" + paramString2 + "> not found");
        throw new RuntimeException("Can't set property");
      }

      return new String(str);
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2, String paramString3)
    {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        return paramString3;
      }
      return new String(str);
    }

    private static void tryToReadGunProperties(SectFile paramSectFile, String paramString, BigshipGeneric.ShipPartProperties paramShipPartProperties)
    {
      if (paramSectFile.exist(paramString, "Gun"))
      {
        String str = "com.maddox.il2.objects.weapons." + getS(paramSectFile, paramString, "Gun");
        try
        {
          paramShipPartProperties.gunClass = Class.forName(str);
        }
        catch (Exception localException)
        {
          System.out.println("BigShip: Can't find gun class '" + str + "'");
          throw new RuntimeException("Can't register Ship object");
        }
      }
      if (paramSectFile.exist(paramString, "AttackMaxDistance"))
        paramShipPartProperties.ATTACK_MAX_DISTANCE = getF(paramSectFile, paramString, "AttackMaxDistance", 6.0F, 50000.0F);
      if (paramSectFile.exist(paramString, "AttackMaxRadius"))
        paramShipPartProperties.ATTACK_MAX_RADIUS = getF(paramSectFile, paramString, "AttackMaxRadius", 6.0F, 50000.0F);
      if (paramSectFile.exist(paramString, "AttackMaxHeight"))
        paramShipPartProperties.ATTACK_MAX_HEIGHT = getF(paramSectFile, paramString, "AttackMaxHeight", 6.0F, 15000.0F);
      if (paramSectFile.exist(paramString, "TrackingOnly"))
        paramShipPartProperties.TRACKING_ONLY = true;
      float f;
      if (paramSectFile.exist(paramString, "FireFastTargets"))
      {
        f = getF(paramSectFile, paramString, "FireFastTargets", 0.0F, 2.0F);
        paramShipPartProperties.ATTACK_FAST_TARGETS = (int)(f + 0.5F);
        if (paramShipPartProperties.ATTACK_FAST_TARGETS > 2)
          paramShipPartProperties.ATTACK_FAST_TARGETS = 2;
      }
      if (paramSectFile.exist(paramString, "FastTargetsAngleError"))
      {
        f = getF(paramSectFile, paramString, "FastTargetsAngleError", 0.0F, 45.0F);
        paramShipPartProperties.FAST_TARGETS_ANGLE_ERROR = f;
      }
      if (paramSectFile.exist(paramString, "HeadMinYaw"))
        paramShipPartProperties._HEAD_MIN_YAW = getF(paramSectFile, paramString, "HeadMinYaw", -360.0F, 360.0F);
      if (paramSectFile.exist(paramString, "HeadMaxYaw"))
        paramShipPartProperties._HEAD_MAX_YAW = getF(paramSectFile, paramString, "HeadMaxYaw", -360.0F, 360.0F);
      if (paramSectFile.exist(paramString, "GunMinPitch"))
        paramShipPartProperties.GUN_MIN_PITCH = getF(paramSectFile, paramString, "GunMinPitch", -15.0F, 85.0F);
      if (paramSectFile.exist(paramString, "GunMaxPitch"))
        paramShipPartProperties.GUN_MAX_PITCH = getF(paramSectFile, paramString, "GunMaxPitch", 0.0F, 89.900002F);
      if (paramSectFile.exist(paramString, "HeadMaxYawSpeed"))
        paramShipPartProperties.HEAD_MAX_YAW_SPEED = getF(paramSectFile, paramString, "HeadMaxYawSpeed", 0.1F, 999.0F);
      if (paramSectFile.exist(paramString, "GunMaxPitchSpeed"))
        paramShipPartProperties.GUN_MAX_PITCH_SPEED = getF(paramSectFile, paramString, "GunMaxPitchSpeed", 0.1F, 999.0F);
      if (paramSectFile.exist(paramString, "DelayAfterShoot"))
        paramShipPartProperties.DELAY_AFTER_SHOOT = getF(paramSectFile, paramString, "DelayAfterShoot", 0.0F, 999.0F);
      if (paramSectFile.exist(paramString, "ChainfireTime"))
        paramShipPartProperties.CHAINFIRE_TIME = getF(paramSectFile, paramString, "ChainfireTime", 0.0F, 600.0F);
      if (paramSectFile.exist(paramString, "GunHeadChunk"))
        paramShipPartProperties.headChunkName = getS(paramSectFile, paramString, "GunHeadChunk");
      if (paramSectFile.exist(paramString, "GunBarrelChunk"))
        paramShipPartProperties.gunChunkName = getS(paramSectFile, paramString, "GunBarrelChunk");
      if (paramSectFile.exist(paramString, "GunShellStartHook"))
        paramShipPartProperties.gunShellStartHookName = getS(paramSectFile, paramString, "GunShellStartHook");
    }

    private static BigshipGeneric.ShipProperties LoadShipProperties(SectFile paramSectFile, String paramString, Class paramClass)
    {
      BigshipGeneric.ShipProperties localShipProperties = new BigshipGeneric.ShipProperties();
      localShipProperties.meshName = getS(paramSectFile, paramString, "Mesh");
      localShipProperties.soundName = getS(paramSectFile, paramString, "SoundMove");
      if (localShipProperties.soundName.equalsIgnoreCase("none"))
        localShipProperties.soundName = null;
      localShipProperties.SLIDER_DIST = getF(paramSectFile, paramString, "SliderDistance", 5.0F, 1000.0F);
      localShipProperties.SPEED = BigshipGeneric.KmHourToMSec(getF(paramSectFile, paramString, "Speed", 0.5F, 200.0F));
      localShipProperties.DELAY_RESPAWN_MIN = 15.0F;
      localShipProperties.DELAY_RESPAWN_MAX = 30.0F;
      Property.set(paramClass, "iconName", "icons/" + getS(paramSectFile, paramString, "Icon") + ".mat");
      Property.set(paramClass, "meshName", localShipProperties.meshName);
      Property.set(paramClass, "speed", localShipProperties.SPEED);

      for (int i = 0; paramSectFile.sectionIndex(paramString + ":Part" + i) >= 0; i++);
      if (i <= 0)
      {
        System.out.println("BigShip: No part sections for '" + paramString + "'");
        throw new RuntimeException("Can't register BigShip object");
      }
      if (i >= 255)
      {
        System.out.println("BigShip: Too many parts in " + paramString + ".");
        throw new RuntimeException("Can't register BigShip object");
      }
      localShipProperties.propparts = new BigshipGeneric.ShipPartProperties[i];
      localShipProperties.nGuns = 0;
      for (int j = 0; j < i; j++)
      {
        String str1 = paramString + ":Part" + j;
        BigshipGeneric.ShipPartProperties localShipPartProperties = new BigshipGeneric.ShipPartProperties();
        localShipProperties.propparts[j] = localShipPartProperties;
        localShipPartProperties.baseChunkName = getS(paramSectFile, str1, "BaseChunk");

        for (int m = 0; paramSectFile.exist(str1, "AdditionalCollisionChunk" + m); m++);
        if (m > 4)
        {
          System.out.println("BigShip: Too many addcollischunks in '" + str1 + "'");
          throw new RuntimeException("Can't register BigShip object");
        }
        localShipPartProperties.additCollisChunkName = new String[m];
        for (int n = 0; n < m; n++) {
          localShipPartProperties.additCollisChunkName[n] = getS(paramSectFile, str1, "AdditionalCollisionChunk" + n);
        }
        String str2 = null;
        if (paramSectFile.exist(str1, "strengthBasedOnThisSection"))
          str2 = getS(paramSectFile, str1, "strengthBasedOnThisSection");
        if (!localShipPartProperties.stre.read("Bigship", paramSectFile, str2, str1))
          throw new RuntimeException("Can't register Bigship object");
        if (paramSectFile.exist(str1, "Vital"))
        {
          localShipPartProperties.dmgDepth = getF(paramSectFile, str1, "damageDepth", 0.0F, 99.0F);
          localShipPartProperties.dmgPitch = getF(paramSectFile, str1, "damagePitch", -89.0F, 89.0F);
          localShipPartProperties.dmgRoll = getF(paramSectFile, str1, "damageRoll", 0.0F, 89.0F);
          localShipPartProperties.dmgTime = getF(paramSectFile, str1, "damageTime", 1.0F, 1200.0F);
          localShipPartProperties.BLACK_DAMAGE = 0.6666667F;
        }
        else {
          localShipPartProperties.dmgDepth = -1.0F;
          localShipPartProperties.BLACK_DAMAGE = 1.0F;
        }
        if ((!paramSectFile.exist(str1, "Gun")) && (!paramSectFile.exist(str1, "gunBasedOnThisSection"))) {
          localShipPartProperties.gun_idx = -1;
        }
        else if (localShipPartProperties.isItLifeKeeper())
        {
          System.out.println("*** ERROR: bigship: vital with gun");
          localShipPartProperties.gun_idx = -1;
        }
        else {
          localShipPartProperties.gun_idx = (localShipProperties.nGuns++);
          if (localShipProperties.nGuns > 256)
          {
            System.out.println("BigShip: Too many guns in " + paramString + ".");
            throw new RuntimeException("Can't register BigShip object");
          }
          localShipPartProperties.gunClass = null;
          localShipPartProperties.ATTACK_MAX_DISTANCE = -1000.0F;
          localShipPartProperties.ATTACK_MAX_RADIUS = -1000.0F;
          localShipPartProperties.ATTACK_MAX_HEIGHT = -1000.0F;
          localShipPartProperties.TRACKING_ONLY = false;
          localShipPartProperties.ATTACK_FAST_TARGETS = 1;
          localShipPartProperties.FAST_TARGETS_ANGLE_ERROR = 0.0F;
          localShipPartProperties._HEAD_MIN_YAW = -1000.0F;
          localShipPartProperties._HEAD_MAX_YAW = -1000.0F;
          localShipPartProperties.GUN_MIN_PITCH = -1000.0F;
          localShipPartProperties.GUN_STD_PITCH = -1000.0F;
          localShipPartProperties.GUN_MAX_PITCH = -1000.0F;
          localShipPartProperties.HEAD_MAX_YAW_SPEED = -1000.0F;
          localShipPartProperties.GUN_MAX_PITCH_SPEED = -1000.0F;
          localShipPartProperties.DELAY_AFTER_SHOOT = -1000.0F;
          localShipPartProperties.CHAINFIRE_TIME = -1000.0F;
          localShipPartProperties.headChunkName = null;
          localShipPartProperties.gunChunkName = null;
          localShipPartProperties.gunShellStartHookName = null;
          if (paramSectFile.exist(str1, "gunBasedOnThisSection"))
          {
            String str3 = getS(paramSectFile, str1, "gunBasedOnThisSection");
            tryToReadGunProperties(paramSectFile, str3, localShipPartProperties);
          }
          tryToReadGunProperties(paramSectFile, str1, localShipPartProperties);
          if ((localShipPartProperties.gunClass == null) || (localShipPartProperties.ATTACK_MAX_DISTANCE <= -1000.0F) || (localShipPartProperties.ATTACK_MAX_RADIUS <= -1000.0F) || (localShipPartProperties.ATTACK_MAX_HEIGHT <= -1000.0F) || (localShipPartProperties._HEAD_MIN_YAW <= -1000.0F) || (localShipPartProperties._HEAD_MAX_YAW <= -1000.0F) || (localShipPartProperties.GUN_MIN_PITCH <= -1000.0F) || (localShipPartProperties.GUN_MAX_PITCH <= -1000.0F) || (localShipPartProperties.HEAD_MAX_YAW_SPEED <= -1000.0F) || (localShipPartProperties.GUN_MAX_PITCH_SPEED <= -1000.0F) || (localShipPartProperties.DELAY_AFTER_SHOOT <= -1000.0F) || (localShipPartProperties.CHAINFIRE_TIME <= -1000.0F) || (localShipPartProperties.headChunkName == null) || (localShipPartProperties.gunChunkName == null) || (localShipPartProperties.gunShellStartHookName == null))
          {
            System.out.println("BigShip: Not enough 'gun' data  in '" + str1 + "'");
            throw new RuntimeException("Can't register BigShip object");
          }
          localShipPartProperties.WEAPONS_MASK = Gun.getProperties(localShipPartProperties.gunClass).weaponType;
          if (localShipPartProperties.WEAPONS_MASK == 0)
          {
            System.out.println("BigShip: Undefined weapon type in gun class '" + localShipPartProperties.gunClass.getName() + "'");
            throw new RuntimeException("Can't register BigShip object");
          }
          if (localShipPartProperties._HEAD_MIN_YAW > localShipPartProperties._HEAD_MAX_YAW)
          {
            System.out.println("BigShip: Wrong yaw angles in gun " + str1 + ".");
            throw new RuntimeException("Can't register BigShip object");
          }
          localShipPartProperties.HEAD_STD_YAW = 0.0F;
          localShipPartProperties.HEAD_YAW_RANGE.set(localShipPartProperties._HEAD_MIN_YAW, localShipPartProperties._HEAD_MAX_YAW);
        }
      }

      localShipProperties.WEAPONS_MASK = 0;
      localShipProperties.ATTACK_MAX_DISTANCE = 1.0F;
      for (int k = 0; k < localShipProperties.propparts.length; k++) {
        if (!localShipProperties.propparts[k].haveGun())
          continue;
        localShipProperties.WEAPONS_MASK |= localShipProperties.propparts[k].WEAPONS_MASK;
        if (localShipProperties.ATTACK_MAX_DISTANCE < localShipProperties.propparts[k].ATTACK_MAX_DISTANCE) {
          localShipProperties.ATTACK_MAX_DISTANCE = localShipProperties.propparts[k].ATTACK_MAX_DISTANCE;
        }
      }
      if (paramSectFile.get(paramString, "IsAirport", false))
        localShipProperties.propAirport = new BigshipGeneric.AirportProperties(paramClass);
      return localShipProperties;
    }

    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      BigshipGeneric localBigshipGeneric = null;
      try
      {
        BigshipGeneric.access$5102(this.proper);
        BigshipGeneric.access$5202(paramActorSpawnArg);
        localBigshipGeneric = (BigshipGeneric)this.cls.newInstance();
        BigshipGeneric.access$5102(null);
        BigshipGeneric.access$5202(null);
      }
      catch (Exception localException)
      {
        BigshipGeneric.access$5102(null);
        BigshipGeneric.access$5202(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create Ship object [class:" + this.cls.getName() + "]");
        return null;
      }
      return localBigshipGeneric;
    }

    public SPAWN(Class paramClass)
    {
      try
      {
        String str1 = paramClass.getName();
        int i = str1.lastIndexOf('.');
        int j = str1.lastIndexOf('$');
        if (i < j)
          i = j;
        String str2 = str1.substring(i + 1);
        this.proper = LoadShipProperties(Statics.getShipsFile(), str2, paramClass);
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("Problem in spawn: " + paramClass.getName());
      }
      this.cls = paramClass;
      Spawn.add(this.cls, this);
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out;

    public boolean netInput(NetMsgInput paramNetMsgInput)
      throws IOException
    {
      int j;
      int k;
      int m;
      int i4;
      int i1;
      int i3;
      Object localObject1;
      if (paramNetMsgInput.isGuaranted())
      {
        NetMsgGuaranted localNetMsgGuaranted1;
        switch (paramNetMsgInput.readUnsignedByte())
        {
        case 73:
          if (isMirrored())
          {
            localNetMsgGuaranted1 = new NetMsgGuaranted(paramNetMsgInput, 0);
            post(localNetMsgGuaranted1);
          }
          BigshipGeneric.access$4202(BigshipGeneric.this, paramNetMsgInput.readLong());
          if (BigshipGeneric.this.timeOfDeath < 0L)
          {
            if (BigshipGeneric.this.dying == 0)
            {
              BigshipGeneric.this.makeLive();
              BigshipGeneric.this.setDefaultLivePose();
              BigshipGeneric.this.forgetAllAiming();
            }
          }
          else if (BigshipGeneric.this.dying == 0)
            BigshipGeneric.this.Die(null, BigshipGeneric.this.timeOfDeath, false, true);
          return true;
        case 82:
          if (isMirrored())
          {
            localNetMsgGuaranted1 = new NetMsgGuaranted(paramNetMsgInput, 0);
            post(localNetMsgGuaranted1);
          }
          BigshipGeneric.this.makeLive();
          BigshipGeneric.this.setDefaultLivePose();
          BigshipGeneric.this.forgetAllAiming();
          BigshipGeneric.this.setDiedFlag(false);
          BigshipGeneric.access$2402(BigshipGeneric.this, BigshipGeneric.access$2502(BigshipGeneric.this, 0L));
          BigshipGeneric.access$2602(BigshipGeneric.this, BigshipGeneric.access$2702(BigshipGeneric.this, BigshipGeneric.access$2802(BigshipGeneric.this, 0.0F)));
          BigshipGeneric.access$2902(BigshipGeneric.this, BigshipGeneric.access$3002(BigshipGeneric.this, BigshipGeneric.access$3102(BigshipGeneric.this, 0.0F)));
          BigshipGeneric.access$3202(BigshipGeneric.this, BigshipGeneric.access$3302(BigshipGeneric.this, BigshipGeneric.access$3402(BigshipGeneric.this, 0.0F)));
          BigshipGeneric.this.setPosition();
          BigshipGeneric.this.pos.reset();
          return true;
        case 83:
          if (isMirrored())
          {
            localNetMsgGuaranted1 = new NetMsgGuaranted(paramNetMsgInput, 0);
            post(localNetMsgGuaranted1);
          }
          i = paramNetMsgInput.available();
          j = (BigshipGeneric.this.parts.length + 3) / 4;
          if (i != j)
          {
            System.out.println("*** net bigship S");
            return true;
          }
          if (j <= 0)
          {
            System.out.println("*** net bigship S0");
            return true;
          }
          k = 0;
          for (m = 0; m < i; m++)
          {
            int n = paramNetMsgInput.readUnsignedByte();
            for (int i2 = 0; i2 < 4; i2++)
            {
              if (k >= BigshipGeneric.this.parts.length)
                break;
              i4 = n >>> i2 * 2 & 0x3;
              if (i4 <= BigshipGeneric.Part.access$1100(BigshipGeneric.this.parts[k]))
              {
                k++;
              }
              else {
                if (i4 == 2)
                {
                  BigshipGeneric.Part.access$4302(BigshipGeneric.this.parts[k], 0.0F);
                  BigshipGeneric.Part.access$4702(BigshipGeneric.this.parts[k], null);
                }
                BigshipGeneric.Part.access$1102(BigshipGeneric.this.parts[k], i4);
                BigshipGeneric.this.visualsInjurePart(k, true);
                k++;
              }
            }

          }

          return true;
        case 100:
          if (isMirrored())
          {
            NetMsgGuaranted localNetMsgGuaranted2 = new NetMsgGuaranted(paramNetMsgInput, 0);
            post(localNetMsgGuaranted2);
          }
          i1 = paramNetMsgInput.available();
          if (i1 != 8)
          {
            System.out.println("*** net bigship d");
            return true;
          }
          if (BigshipGeneric.this.dying != 0)
          {
            return true;
          }

          BigshipGeneric.this.computeInterpolatedDPR(NetServerParams.getServerTime());
          BigshipGeneric.access$2902(BigshipGeneric.this, BigshipGeneric.this.bodyDepth);
          BigshipGeneric.access$3002(BigshipGeneric.this, BigshipGeneric.this.bodyPitch);
          BigshipGeneric.access$3102(BigshipGeneric.this, BigshipGeneric.this.bodyRoll);
          BigshipGeneric.access$3202(BigshipGeneric.this, (float)(1000.0D * ((paramNetMsgInput.readUnsignedShort() & 0x7FFF) / 32767.0D)));
          BigshipGeneric.access$3302(BigshipGeneric.this, (float)(90.0D * (paramNetMsgInput.readShort() / 32767.0D)));
          BigshipGeneric.access$3402(BigshipGeneric.this, (float)(90.0D * (paramNetMsgInput.readShort() / 32767.0D)));
          BigshipGeneric.access$2402(BigshipGeneric.this, BigshipGeneric.access$2502(BigshipGeneric.this, NetServerParams.getServerTime()));
          BigshipGeneric.access$2514(BigshipGeneric.this, ()(1000.0D * (1200.0D * ((paramNetMsgInput.readUnsignedShort() & 0x7FFF) / 32767.0D))));
          BigshipGeneric.this.computeInterpolatedDPR(NetServerParams.getServerTime());
          return true;
        case 68:
          if (isMirrored())
          {
            NetMsgGuaranted localNetMsgGuaranted3 = new NetMsgGuaranted(paramNetMsgInput, 1);
            post(localNetMsgGuaranted3);
          }
          i3 = paramNetMsgInput.available();

          if (i3 == 8 + NetMsgInput.netObjReferenceLen() + 8 + 8) {
            i4 = 0;
          }
          else if (i3 == 8 + NetMsgInput.netObjReferenceLen() + 8 + 8 + 8)
          {
            i4 = 1;
          }
          else {
            System.out.println("*** net bigship D");
            return true;
          }
          if (BigshipGeneric.this.dying != 0)
            return true;
          BigshipGeneric.access$4202(BigshipGeneric.this, paramNetMsgInput.readLong());
          if (Mission.isDeathmatch())
            BigshipGeneric.access$4202(BigshipGeneric.this, NetServerParams.getServerTime());
          if (BigshipGeneric.this.timeOfDeath < 0L)
          {
            System.out.println("*** net bigship D tm");
            return true;
          }
          NetObj localNetObj = paramNetMsgInput.readNetObj();
          localObject1 = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
          BigshipGeneric.this.computeInterpolatedDPR(NetServerParams.getServerTime());
          BigshipGeneric.access$2902(BigshipGeneric.this, BigshipGeneric.this.bodyDepth);
          BigshipGeneric.access$3002(BigshipGeneric.this, BigshipGeneric.this.bodyPitch);
          BigshipGeneric.access$3102(BigshipGeneric.this, BigshipGeneric.this.bodyRoll);
          BigshipGeneric.access$3202(BigshipGeneric.this, (float)(1000.0D * ((paramNetMsgInput.readUnsignedShort() & 0x7FFF) / 32767.0D)));
          BigshipGeneric.access$3302(BigshipGeneric.this, (float)(90.0D * (paramNetMsgInput.readShort() / 32767.0D)));
          BigshipGeneric.access$3402(BigshipGeneric.this, (float)(90.0D * (paramNetMsgInput.readShort() / 32767.0D)));
          BigshipGeneric.access$2402(BigshipGeneric.this, BigshipGeneric.access$2502(BigshipGeneric.this, NetServerParams.getServerTime()));
          BigshipGeneric.access$2514(BigshipGeneric.this, ()(1000.0D * (1200.0D * ((paramNetMsgInput.readUnsignedShort() & 0x7FFF) / 32767.0D))));
          BigshipGeneric.this.computeInterpolatedDPR(NetServerParams.getServerTime());
          BigshipGeneric.access$3602(BigshipGeneric.this, (float)(1000.0D * ((paramNetMsgInput.readUnsignedShort() & 0x7FFF) / 32767.0D)));
          BigshipGeneric.access$3702(BigshipGeneric.this, (float)(90.0D * (paramNetMsgInput.readShort() / 32767.0D)));
          BigshipGeneric.access$3802(BigshipGeneric.this, (float)(90.0D * (paramNetMsgInput.readShort() / 32767.0D)));
          BigshipGeneric.access$3902(BigshipGeneric.this, BigshipGeneric.this.tmInterpoEnd);
          BigshipGeneric.access$3914(BigshipGeneric.this, ()(1000.0D * (1200.0D * ((paramNetMsgInput.readUnsignedShort() & 0x7FFF) / 32767.0D))));
          if (i4 != 0)
          {
            long l = paramNetMsgInput.readLong();
            if (l > 0L)
            {
              BigshipGeneric.access$2422(BigshipGeneric.this, l);
              BigshipGeneric.access$2522(BigshipGeneric.this, l);
              BigshipGeneric.access$3922(BigshipGeneric.this, l);
              BigshipGeneric.this.computeInterpolatedDPR(NetServerParams.getServerTime());
            }
          }
          BigshipGeneric.this.Die((Actor)localObject1, BigshipGeneric.this.timeOfDeath, true, false);
          return true;
        }
        System.out.println("**net bigship unknown cmd");
        return false;
      }
      int i = paramNetMsgInput.readUnsignedByte();
      if ((i & 0xE0) == 224)
      {
        j = 1 + NetMsgInput.netObjReferenceLen() + 1;
        k = 2 + NetMsgInput.netObjReferenceLen() + 1;
        m = paramNetMsgInput.available();
        i1 = i & 0x1F;
        i3 = m - i1 * j;
        i4 = i3 / k;
        if ((i4 < 0) || (i4 > 31) || (i1 > 31) || (i3 % k != 0))
        {
          System.out.println("*** net big0 code:" + i + " szT:" + j + " szF:" + k + " len:" + m + " nT:" + i1 + " lenF:" + i3 + " nF:" + i4);
          return true;
        }
        if (isMirrored())
        {
          this.out.unLockAndSet(paramNetMsgInput, i1 + i4);
          this.out.setIncludeTime(true);
          postReal(Message.currentRealTime(), this.out); } int i5;
        Object localObject2;
        while (true) { i5 = paramNetMsgInput.readUnsignedByte();
          localObject1 = paramNetMsgInput.readNetObj();
          localObject2 = localObject1 == null ? null : ((ActorNet)localObject1).actor();
          int i7 = paramNetMsgInput.readUnsignedByte();
          BigshipGeneric.this.Track_Mirror(i5, (Actor)localObject2, i7);

          i1--; if (i1 < 0)
          {
            break;
          } }
        do {
          i5 = paramNetMsgInput.readUnsignedByte();
          int i6 = paramNetMsgInput.readUnsignedByte();
          localObject2 = paramNetMsgInput.readNetObj();
          Actor localActor = localObject2 == null ? null : ((ActorNet)localObject2).actor();
          double d1 = -2.0D + i5 / 255.0D * 7000.0D / 1000.0D;
          double d2 = 0.001D * (Message.currentGameTime() - NetServerParams.getServerTime()) + d1;
          int i8 = paramNetMsgInput.readUnsignedByte();
          BigshipGeneric.this.Fire_Mirror(i6, localActor, i8, (float)d2);

          i4--; } while (i4 >= 0);

        return true;
      }
      if (i == 80)
      {
        j = 2 + NetMsgInput.netObjReferenceLen();
        k = paramNetMsgInput.available();
        m = k / j;
        if ((m <= 0) || (m > 256) || (k % j != 0))
        {
          System.out.println("*** net bigship2 n:" + m);
          return true;
        }

        this.out.unLockAndSet(paramNetMsgInput, m);
        this.out.setIncludeTime(false);
        postRealTo(Message.currentRealTime(), masterChannel(), this.out);
        return true;
      }

      System.out.println("**net bigship unknown ng cmd");
      return true;
    }

    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4)
    {
      super(paramInt, i);
      this.out = new NetMsgFiltered();
    }
  }

  class Master extends ActorNet
  {
    public boolean netInput(NetMsgInput paramNetMsgInput)
      throws IOException
    {
      if (paramNetMsgInput.isGuaranted())
        return true;
      if (paramNetMsgInput.readUnsignedByte() != 80)
        return false;
      if (BigshipGeneric.this.dying != 0)
        return true;
      int i = 2 + NetMsgInput.netObjReferenceLen();
      int j = paramNetMsgInput.available();
      int k = j / i;
      if ((k <= 0) || (k > 256) || (j % i != 0))
      {
        System.out.println("*** net bigship1 len:" + j);
        return true;
      }
      do
      {
        int m = paramNetMsgInput.readUnsignedByte();
        if ((m < 0) || (m >= BigshipGeneric.this.parts.length))
          return true;
        int n = paramNetMsgInput.readUnsignedByte();
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        Actor localActor = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
        if (BigshipGeneric.Part.access$1100(BigshipGeneric.this.parts[m]) != 2)
        {
          BigshipGeneric.Part.access$4316(BigshipGeneric.this.parts[m], ((n & 0x7F) + 1) / 128.0F);
          BigshipGeneric.Part.access$4402(BigshipGeneric.this.parts[m], (n & 0x80) != 0);
          BigshipGeneric.this.InjurePart(m, localActor, true);
        }
        k--; } while (k >= 0);

      return true;
    }

    public Master(Actor arg2)
    {
      super();
    }
  }

  class Move extends Interpolate
  {
    public boolean tick()
    {
      BigshipGeneric.this.validateTowAircraft();
      int i;
      if (BigshipGeneric.this.dying == 0)
      {
        l = Time.tickNext();
        if (Mission.isCoop())
          l = NetServerParams.getServerTime() + Time.tickLen();
        if (BigshipGeneric.this.path != null)
        {
          BigshipGeneric.this.computeInterpolatedDPR(l);
          BigshipGeneric.this.setMovablePosition(l);
        }
        else if (BigshipGeneric.this.computeInterpolatedDPR(l)) {
          BigshipGeneric.this.setPosition();
        }i = 0;
        int j;
        if (BigshipGeneric.this.wakeupTmr == 0L)
        {
          for (j = 0; j < BigshipGeneric.this.prop.nGuns; j++) {
            if (BigshipGeneric.Part.access$1100(BigshipGeneric.this.parts[BigshipGeneric.FiringDevice.access$1000(BigshipGeneric.this.arms[j])]) != 0)
              continue;
            BigshipGeneric.FiringDevice.access$1200(BigshipGeneric.this.arms[j]).tick_();
            i = 1;
          }
        }
        else
        {
          for (j = 0; j < BigshipGeneric.this.prop.nGuns; j++)
          {
            if (BigshipGeneric.Part.access$1100(BigshipGeneric.this.parts[BigshipGeneric.FiringDevice.access$1000(BigshipGeneric.this.arms[j])]) != 0)
              continue;
            i = 1;
            break;
          }

          if (BigshipGeneric.this.wakeupTmr > 0L) {
            BigshipGeneric.access$610(BigshipGeneric.this);
          }
          else if (BigshipGeneric.access$604(BigshipGeneric.this) == 0L)
            if (BigshipGeneric.this.isAnyEnemyNear())
              BigshipGeneric.access$602(BigshipGeneric.this, BigshipGeneric.access$1400(BigshipGeneric.Rnd(BigshipGeneric.this.DELAY_WAKEUP, BigshipGeneric.this.DELAY_WAKEUP * 1.2F)));
            else
              BigshipGeneric.access$602(BigshipGeneric.this, -BigshipGeneric.access$1400(BigshipGeneric.Rnd(4.0F, 7.0F)));
        }
        if (i != 0)
          BigshipGeneric.this.send_bufferized_FireCommand();
        if (BigshipGeneric.this.isNetMirror()) {
          BigshipGeneric.this.mirror_send_bufferized_Damage();
        }
        else if (BigshipGeneric.this.netsendPartsState_needtosend)
          BigshipGeneric.this.send_bufferized_PartsState();
        return true;
      }
      if (BigshipGeneric.this.dying == 3)
      {
        if ((BigshipGeneric.this.path != null) || (!Mission.isDeathmatch()))
        {
          BigshipGeneric.this.eraseGuns();
          return false;
        }
        if (BigshipGeneric.access$2010(BigshipGeneric.this) > 0L)
          return true;
        if (!BigshipGeneric.this.isNetMaster())
        {
          BigshipGeneric.access$2002(BigshipGeneric.this, 10000L);
          return true;
        }

        BigshipGeneric.access$602(BigshipGeneric.this, 0L);
        BigshipGeneric.this.makeLive();
        BigshipGeneric.this.forgetAllAiming();
        BigshipGeneric.this.setDefaultLivePose();
        BigshipGeneric.this.setDiedFlag(false);
        BigshipGeneric.access$2402(BigshipGeneric.this, BigshipGeneric.access$2502(BigshipGeneric.this, 0L));
        BigshipGeneric.access$2602(BigshipGeneric.this, BigshipGeneric.access$2702(BigshipGeneric.this, BigshipGeneric.access$2802(BigshipGeneric.this, 0.0F)));
        BigshipGeneric.access$2902(BigshipGeneric.this, BigshipGeneric.access$3002(BigshipGeneric.this, BigshipGeneric.access$3102(BigshipGeneric.this, 0.0F)));
        BigshipGeneric.access$3202(BigshipGeneric.this, BigshipGeneric.access$3302(BigshipGeneric.this, BigshipGeneric.access$3402(BigshipGeneric.this, 0.0F)));
        BigshipGeneric.this.setPosition();
        BigshipGeneric.this.pos.reset();
        BigshipGeneric.this.send_RespawnCommand();
        return true;
      }

      if (BigshipGeneric.this.netsendPartsState_needtosend)
        BigshipGeneric.this.send_bufferized_PartsState();
      long l = Time.tickNext();
      if (BigshipGeneric.this.dying == 1)
      {
        if (l >= BigshipGeneric.this.tmInterpoEnd)
        {
          BigshipGeneric.access$2902(BigshipGeneric.this, BigshipGeneric.this.bodyDepth1);
          BigshipGeneric.access$3002(BigshipGeneric.this, BigshipGeneric.this.bodyPitch1);
          BigshipGeneric.access$3102(BigshipGeneric.this, BigshipGeneric.this.bodyRoll1);
          BigshipGeneric.access$3202(BigshipGeneric.this, BigshipGeneric.this.sink2Depth);
          BigshipGeneric.access$3302(BigshipGeneric.this, BigshipGeneric.this.sink2Pitch);
          BigshipGeneric.access$3402(BigshipGeneric.this, BigshipGeneric.this.sink2Roll);
          BigshipGeneric.access$2402(BigshipGeneric.this, BigshipGeneric.this.tmInterpoEnd);
          BigshipGeneric.access$2502(BigshipGeneric.this, BigshipGeneric.this.sink2timeWhenStop);
          BigshipGeneric.access$102(BigshipGeneric.this, 2);
        }
      }
      else if (l >= BigshipGeneric.this.tmInterpoEnd)
      {
        BigshipGeneric.access$2902(BigshipGeneric.this, BigshipGeneric.access$3202(BigshipGeneric.this, BigshipGeneric.this.sink2Depth));
        BigshipGeneric.access$3002(BigshipGeneric.this, BigshipGeneric.access$3302(BigshipGeneric.this, BigshipGeneric.this.sink2Pitch));
        BigshipGeneric.access$3102(BigshipGeneric.this, BigshipGeneric.access$3402(BigshipGeneric.this, BigshipGeneric.this.sink2Roll));
        BigshipGeneric.access$2402(BigshipGeneric.this, BigshipGeneric.access$2502(BigshipGeneric.this, 0L));
        BigshipGeneric.access$102(BigshipGeneric.this, 3);
      }
      if (((Time.tickCounter() & 0x63) == 0) && (BigshipGeneric.this.dsmoks != null))
      {
        for (i = 0; i < BigshipGeneric.this.dsmoks.length; i++) {
          if ((BigshipGeneric.this.dsmoks[i] == null) || (BigshipGeneric.Pipe.access$4100(BigshipGeneric.this.dsmoks[i]) == null) || (BigshipGeneric.Pipe.access$4100(BigshipGeneric.this.dsmoks[i]).pos.getAbsPoint().z >= -4.891D))
            continue;
          Eff3DActor.finish(BigshipGeneric.Pipe.access$4100(BigshipGeneric.this.dsmoks[i]));
          BigshipGeneric.Pipe.access$4102(BigshipGeneric.this.dsmoks[i], null);
        }
      }

      BigshipGeneric.this.computeInterpolatedDPR(l);
      if (BigshipGeneric.this.path != null)
        BigshipGeneric.this.setMovablePosition(BigshipGeneric.this.timeOfDeath);
      else
        BigshipGeneric.this.setPosition();
      return true;
    }

    Move()
    {
    }
  }

  static class HookNamedZ0 extends HookNamed
  {
    public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2)
    {
      super.computePos(paramActor, paramLoc1, paramLoc2);
      paramLoc2.getPoint().z = 0.25D;
    }

    public HookNamedZ0(ActorMesh paramActorMesh, String paramString)
    {
      super(paramString);
    }

    public HookNamedZ0(Mesh paramMesh, String paramString)
    {
      super(paramString);
    }
  }

  public static class Pipe
  {
    private Eff3DActor pipe;
    private int part_idx;

    public Pipe()
    {
      this.pipe = null;
      this.part_idx = -1;
    }
  }

  private static class Segment
  {
    public Point3d posIn;
    public Point3d posOut;
    public float length;
    public long timeIn;
    public long timeOut;
    public float speedIn;
    public float speedOut;

    private Segment()
    {
    }

    Segment(BigshipGeneric.1 param1)
    {
      this();
    }
  }

  public static class Part
  {
    private float damage;
    private Actor mirror_initiator;
    private Point3d shotpointOffs;
    private boolean damageIsFromRight;
    private int state;
    BigshipGeneric.ShipPartProperties pro;

    public Part()
    {
      this.shotpointOffs = new Point3d();
      this.damageIsFromRight = false;
    }
  }

  public static class FiringDevice
  {
    private int gun_idx;
    private int part_idx;
    private Gun gun;
    private Aim aime;
    private float headYaw;
    private float gunPitch;
    private Actor enemy;
    private double timeWhenFireS;
    private int shotpointIdx;
  }

  public static class TmpTrackOrFireInfo
  {
    private int gun_idx;
    private Actor enemy;
    private double timeWhenFireS;
    private int shotpointIdx;
  }

  public static class ShipProperties
  {
    public String meshName;
    public String soundName;
    public int WEAPONS_MASK;
    public int HITBY_MASK;
    public float ATTACK_MAX_DISTANCE;
    public float SLIDER_DIST;
    public float SPEED;
    public float DELAY_RESPAWN_MIN;
    public float DELAY_RESPAWN_MAX;
    public BigshipGeneric.ShipPartProperties[] propparts;
    public int nGuns;
    public BigshipGeneric.AirportProperties propAirport;

    public ShipProperties()
    {
      this.meshName = null;
      this.soundName = null;
      this.WEAPONS_MASK = 4;
      this.HITBY_MASK = -2;
      this.ATTACK_MAX_DISTANCE = 1.0F;
      this.SLIDER_DIST = 1.0F;
      this.SPEED = 1.0F;
      this.DELAY_RESPAWN_MIN = 15.0F;
      this.DELAY_RESPAWN_MAX = 30.0F;
      this.propparts = null;
      this.propAirport = null;
    }
  }

  public static class ShipPartProperties
  {
    public String baseChunkName;
    public int baseChunkIdx;
    public Point3f partOffs;
    public float partR;
    public String[] additCollisChunkName;
    public int[] additCollisChunkIdx;
    public StrengthProperties stre;
    public float dmgDepth;
    public float dmgPitch;
    public float dmgRoll;
    public float dmgTime;
    public float BLACK_DAMAGE;
    public int gun_idx;
    public Class gunClass;
    public int WEAPONS_MASK;
    public boolean TRACKING_ONLY;
    public float ATTACK_MAX_DISTANCE;
    public float ATTACK_MAX_RADIUS;
    public float ATTACK_MAX_HEIGHT;
    public int ATTACK_FAST_TARGETS;
    public float FAST_TARGETS_ANGLE_ERROR;
    public AnglesRange HEAD_YAW_RANGE;
    public float HEAD_STD_YAW;
    public float _HEAD_MIN_YAW;
    public float _HEAD_MAX_YAW;
    public float GUN_MIN_PITCH;
    public float GUN_STD_PITCH;
    public float GUN_MAX_PITCH;
    public float HEAD_MAX_YAW_SPEED;
    public float GUN_MAX_PITCH_SPEED;
    public float DELAY_AFTER_SHOOT;
    public float CHAINFIRE_TIME;
    public String headChunkName;
    public String gunChunkName;
    public int headChunkIdx;
    public int gunChunkIdx;
    public Point3d fireOffset;
    public Orient fireOrient;
    public String gunShellStartHookName;

    public boolean isItLifeKeeper()
    {
      return this.dmgDepth >= 0.0F;
    }

    public boolean haveGun()
    {
      return this.gun_idx >= 0;
    }

    public ShipPartProperties()
    {
      this.baseChunkName = null;
      this.baseChunkIdx = -1;
      this.partOffs = null;
      this.partR = 1.0F;
      this.additCollisChunkName = null;
      this.additCollisChunkIdx = null;
      this.stre = new StrengthProperties();
      this.dmgDepth = -1.0F;
      this.dmgPitch = 0.0F;
      this.dmgRoll = 0.0F;
      this.dmgTime = 1.0F;
      this.BLACK_DAMAGE = 0.0F;
      this.gunClass = null;
      this.WEAPONS_MASK = 4;
      this.TRACKING_ONLY = false;
      this.ATTACK_MAX_DISTANCE = 1.0F;
      this.ATTACK_MAX_RADIUS = 1.0F;
      this.ATTACK_MAX_HEIGHT = 1.0F;
      this.ATTACK_FAST_TARGETS = 1;
      this.FAST_TARGETS_ANGLE_ERROR = 0.0F;
      this.HEAD_YAW_RANGE = new AnglesRange(-1.0F, 1.0F);
      this.HEAD_STD_YAW = 0.0F;
      this._HEAD_MIN_YAW = -1.0F;
      this._HEAD_MAX_YAW = -1.0F;
      this.GUN_MIN_PITCH = -20.0F;
      this.GUN_STD_PITCH = -18.0F;
      this.GUN_MAX_PITCH = -15.0F;
      this.HEAD_MAX_YAW_SPEED = 720.0F;
      this.GUN_MAX_PITCH_SPEED = 60.0F;
      this.DELAY_AFTER_SHOOT = 1.0F;
      this.CHAINFIRE_TIME = 0.0F;
      this.headChunkName = null;
      this.gunChunkName = null;
      this.headChunkIdx = -1;
      this.gunChunkIdx = -1;
      this.gunShellStartHookName = null;
    }
  }
}