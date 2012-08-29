package com.maddox.il2.objects.vehicles.aeronautics;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.TableFunctions;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.TableFunction2;
import java.io.IOException;
import java.io.PrintStream;

public abstract class AeroanchoredGeneric extends ActorMesh
  implements MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener, Prey, Obstacle, ActorAlign
{
  AeroanchoredProperties prop = null;

  PartState anchor = new PartState();
  PartState rope = new PartState();
  PartState balloon = new PartState();

  private Balloon balloonActor = null;
  private Rope ropeActor = null;

  private long respawnDelay = 0L;
  private int mirrorSendDmgDelay = 0;
  private float heightAboveLandSurface;
  private static AeroanchoredProperties constr_arg1 = null;
  private static ActorSpawnArg constr_arg2 = null;

  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();
  private static Vector3d tmpv = new Vector3d();

  public static final float Rnd(float paramFloat1, float paramFloat2)
  {
    return World.Rnd().nextFloat(paramFloat1, paramFloat2);
  }

  private boolean RndB(float paramFloat) {
    return World.Rnd().nextFloat(0.0F, 1.0F) < paramFloat;
  }

  private static final long SecsToTicks(float paramFloat) {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if (((paramActor instanceof ActorMesh)) && (((ActorMesh)paramActor).isStaticPos())) {
      paramArrayOfBoolean[0] = false;
      return;
    }
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2) {
    anchorDamaged(paramActor, 1.0F);
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = 2;

    if (this.anchor.dead) {
      return;
    }

    if (paramShot.power <= 0.0F) {
      return;
    }

    if ((isNetMirror()) && 
      (paramShot.isMirage())) {
      return;
    }

    if (paramShot.powerType == 1) {
      if (RndB(0.15F)) {
        return;
      }

      anchorDamaged(paramShot.initiator, 1.0F);
      return;
    }

    float f1 = Shot.panzerThickness(this.pos.getAbsOrient(), paramShot.v, false, this.prop.PANZER_BODY, this.prop.PANZER_BODY, this.prop.PANZER_BODY, this.prop.PANZER_BODY, this.prop.PANZER_BODY, this.prop.PANZER_BODY);

    f1 *= Rnd(0.93F, 1.07F);

    float f2 = this.prop.fnShotPanzer.Value(paramShot.power, f1);

    if ((f2 < 1000.0F) && ((f2 <= 1.0F) || (RndB(1.0F / f2))))
      anchorDamaged(paramShot.initiator, 1.0F);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (this.anchor.dead) {
      return;
    }

    if ((isNetMirror()) && (paramExplosion.isMirage())) {
      return;
    }

    if (paramExplosion.power <= 0.0F) {
      return;
    }

    if (paramExplosion.powerType == 1) {
      if (TankGeneric.splintersKill(paramExplosion, this.prop.fnShotPanzer, Rnd(0.0F, 1.0F), Rnd(0.0F, 1.0F), this, 0.7F, 0.0F, this.prop.PANZER_BODY, this.prop.PANZER_BODY, this.prop.PANZER_BODY, this.prop.PANZER_BODY, this.prop.PANZER_BODY, this.prop.PANZER_BODY))
      {
        anchorDamaged(paramExplosion.initiator, 1.0F);
      }
      return;
    }

    if ((paramExplosion.powerType == 2) && (paramExplosion.chunkName != null)) {
      anchorDamaged(paramExplosion.initiator, 1.0F);
      return;
    }
    float f1;
    if (paramExplosion.chunkName != null)
      f1 = 0.5F * paramExplosion.power;
    else {
      f1 = paramExplosion.receivedTNTpower(this);
    }
    f1 *= Rnd(0.95F, 1.05F);

    float f2 = this.prop.fnExplodePanzer.Value(f1, this.prop.PANZER_TNT_TYPE);

    if ((f2 < 1000.0F) && ((f2 <= 1.0F) || (RndB(1.0F / f2))))
      anchorDamaged(paramExplosion.initiator, 1.0F);
  }

  private void ShowExplode(float paramFloat)
  {
    if (paramFloat > 0.0F)
      paramFloat = Rnd(paramFloat, paramFloat * 1.6F);
  }

  private void anchorDamaged(Actor paramActor, float paramFloat)
  {
    if (this.anchor.dead) {
      return;
    }

    if (isNetMirror()) {
      PartState.access$116(this.anchor, paramFloat);
      PartState.access$202(this.anchor, paramActor);
    } else {
      applyDamage(97, paramActor, paramFloat, true);
    }
  }

  public void destroy()
  {
    if (isDestroyed()) {
      return;
    }

    if (this.balloonActor != null) {
      this.balloonActor.destroy();
      this.balloonActor = null;
    }
    if (this.ropeActor != null) {
      this.ropeActor.destroy();
      this.ropeActor = null;
    }

    super.destroy();
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public boolean isStaticPos() {
    return true;
  }

  private void setDefaultPose()
  {
    this.heightAboveLandSurface = 0.0F;
    int i = mesh().hookFind("Ground_Level");
    if (i != -1) {
      Matrix4d localMatrix4d = new Matrix4d();
      mesh().hookMatrix(i, localMatrix4d);
      this.heightAboveLandSurface = (float)(-localMatrix4d.m23);
    }
    Align();
  }

  protected AeroanchoredGeneric()
  {
    this(constr_arg1, constr_arg2);
  }

  private AeroanchoredGeneric(AeroanchoredProperties paramAeroanchoredProperties, ActorSpawnArg paramActorSpawnArg)
  {
    super(paramAeroanchoredProperties.meshAName);
    this.prop = paramAeroanchoredProperties;

    paramActorSpawnArg.setStationary(this);

    collide(true);
    drawing(true);

    this.anchor.resetToLive();
    this.balloon.resetToLive();
    this.rope.resetToLive();

    createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);

    setDefaultPose();
    recreateBalloon();
    recreateRope();

    setDiedFlag(false);
    if (this.balloonActor != null) {
      this.balloonActor.setDiedFlag(false);
    }
    if (this.ropeActor != null) {
      this.ropeActor.setDiedFlag(false);
    }

    startMove();
  }

  private void Align() {
    this.pos.getAbs(p);
    p.z = (Engine.land().HQ(p.x, p.y) + this.heightAboveLandSurface);
    o.setYPR(this.pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
    Engine.land().N(p.x, p.y, n);
    o.orient(n);
    this.pos.setAbs(p, o);
    this.pos.reset();
  }

  public void align()
  {
    Align();
    recreateBalloon();
    recreateRope();
  }

  private float computeTopPossibleHeight(double paramDouble1, double paramDouble2)
  {
    paramDouble1 = paramDouble1 * 1.17D + paramDouble2 * 3.71D;
    paramDouble1 -= (int)paramDouble1;
    int i = (short)(int)(16001.0D + paramDouble1 * 16000.0D);

    RangeRandom localRangeRandom = new RangeRandom(i);

    return localRangeRandom.nextFloat(9050.0F, 10900.0F);
  }

  private void recreateBalloon()
  {
    if (this.balloonActor != null) {
      this.balloonActor.destroy();
      this.balloonActor = null;
    }

    if (!this.balloon.dead) {
      this.pos.getAbs(p);
      float f1 = this.prop.HEIGHT;
      float f2 = computeTopPossibleHeight(p.x, p.y);
      this.balloonActor = new Balloon(this, f1, f2, this.rope.dead);
    }
  }

  private void recreateRope() {
    if (this.ropeActor != null) {
      this.ropeActor.destroy();
      this.ropeActor = null;
    }

    if (!this.rope.dead) {
      float f = this.prop.HEIGHT;
      this.ropeActor = new Rope(this, f, this.prop.ROPE_SEG_LENGTH, this.prop.meshRName);
    }
  }

  private void _balloon_Trouble(Actor paramActor, float paramFloat)
  {
    if (this.balloonActor == null) {
      return;
    }
    if (this.balloon.dead) {
      System.out.println("***Ballon: strange trouble");
    }

    if (isNetMirror()) {
      PartState.access$116(this.balloon, paramFloat);
      PartState.access$202(this.balloon, paramActor);
    } else {
      applyDamage(98, paramActor, paramFloat, true);
    }
  }

  void balloonCollision(Actor paramActor) {
    _balloon_Trouble(paramActor, 1.0F);
  }

  void balloonShot(Actor paramActor) {
    _balloon_Trouble(paramActor, 1.0F);
  }

  void balloonExplosion(Actor paramActor) {
    _balloon_Trouble(paramActor, 1.0F);
  }

  void balloonDisappeared() {
    if (!this.balloon.dead) {
      System.out.println("***balloon disappeared strangely");
    }
    this.balloonActor = null;
  }

  private void _rope_Trouble(Actor paramActor, float paramFloat)
  {
    if (this.ropeActor == null) {
      return;
    }
    if (this.rope.dead) {
      System.out.println("***Rope: strange trouble");
    }

    if (isNetMirror()) {
      PartState.access$116(this.rope, paramFloat);
      PartState.access$202(this.rope, paramActor);
    } else {
      applyDamage(114, paramActor, paramFloat, true);
    }
  }

  void ropeCollision(Actor paramActor) {
    _rope_Trouble(paramActor, 1.0F);
  }

  void ropeDisappeared() {
    if (!this.rope.dead) {
      System.out.println("***rope disappeared strangely");
    }
    this.ropeActor = null;
  }

  public void startMove()
  {
    if (!interpEnd("move"))
      interpPut(new Move(), "move", Time.current(), null);
  }

  public int HitbyMask()
  {
    return this.prop.HITBY_MASK;
  }

  public int chooseBulletType(BulletProperties[] paramArrayOfBulletProperties)
  {
    if (this.anchor.dead) {
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
      return 0;
    }
    if (paramArrayOfBulletProperties[1].power <= 0.0F)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].cumulativePower > 0.0F)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].cumulativePower > 0.0F)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 1)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].powerType == 1)
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
    if (this.anchor.dead) {
      return -1;
    }
    return 0;
  }

  public boolean getShotpointOffset(int paramInt, Point3d paramPoint3d) {
    if (this.anchor.dead) {
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

  public boolean unmovableInFuture()
  {
    return true;
  }

  public void collisionDeath()
  {
    if (isNet()) {
      return;
    }

    ShowExplode(-1.0F);

    destroy();
  }

  public float futurePosition(float paramFloat, Point3d paramPoint3d)
  {
    this.pos.getAbs(paramPoint3d);
    return paramFloat <= 0.0F ? 0.0F : paramFloat;
  }

  private void mastersend_AKillCommand(int paramInt, Actor paramActor)
  {
    if (!isNetMaster()) {
      return;
    }

    if (Mission.isDeathmatch()) {
      float f = Mission.respawnTime("Aeroanchored");
      this.respawnDelay = SecsToTicks(Rnd(f, f * 1.2F));
    } else {
      this.respawnDelay = 0L;
    }

    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(paramInt);
      localNetMsgGuaranted.writeNetObj(paramActor == null ? null : paramActor.net);
      this.net.post(localNetMsgGuaranted);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void mastersend_RespawnCommand()
  {
    if ((!isNetMaster()) || (!Mission.isDeathmatch())) {
      return;
    }
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(82);
      this.net.post(localNetMsgGuaranted);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  public void netFirstUpdate(NetChannel paramNetChannel) throws IOException
  {
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();

    localNetMsgGuaranted.writeByte(73);
    int i = (this.anchor.dead ? 0 : 1) + (this.rope.dead ? 0 : 2) + (this.balloon.dead ? 0 : 4);
    localNetMsgGuaranted.writeByte(i);

    this.net.postTo(paramNetChannel, localNetMsgGuaranted);
  }

  private void mirrorsend_DamageIfNeed()
  {
    if ((!this.anchor.dead) && (this.anchor.damage >= 0.0039063F)) {
      _mirrorsend_ADamageRequest(97, this.anchor);
      this.mirrorSendDmgDelay = 7;
    }

    if ((!this.rope.dead) && (this.rope.damage >= 0.0039063F)) {
      _mirrorsend_ADamageRequest(114, this.rope);
      this.mirrorSendDmgDelay = 7;
    }

    if ((!this.balloon.dead) && (this.balloon.damage >= 0.0039063F)) {
      _mirrorsend_ADamageRequest(98, this.balloon);
      this.mirrorSendDmgDelay = 7;
    }
  }

  private void _mirrorsend_ADamageRequest(int paramInt, PartState paramPartState)
  {
    float f = paramPartState.damage;
    Actor localActor = paramPartState.mirror_initiator;

    PartState.access$102(paramPartState, 0.0F);
    PartState.access$202(paramPartState, null);

    if ((this.net.masterChannel() instanceof NetChannelInStream)) {
      return;
    }

    int i = (int)(f * 256.0F) - 1;
    if (i < 0)
      i = 0;
    else if (i > 255) {
      i = 255;
    }
    try
    {
      NetMsgFiltered localNetMsgFiltered = new NetMsgFiltered();
      localNetMsgFiltered.writeByte(paramInt);
      localNetMsgFiltered.writeByte(i);
      localNetMsgFiltered.writeNetObj(localActor == null ? null : localActor.net);
      localNetMsgFiltered.setIncludeTime(false);
      this.net.postTo(Time.current(), this.net.masterChannel(), localNetMsgFiltered);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void applyDamage(int paramInt, Actor paramActor, float paramFloat, boolean paramBoolean)
  {
    switch (paramInt) {
    case 97:
      if (this.anchor.dead) {
        return;
      }
      PartState.access$116(this.anchor, paramFloat);
      if (this.anchor.damage < 1.0F) {
        return;
      }
      if (paramBoolean) {
        mastersend_AKillCommand(97, paramActor);
      }

      if (this.balloonActor != null) {
        this.balloonActor.somebodyKilled(97);
      }

      if (this.ropeActor != null) {
        this.ropeActor.somebodyKilled(97);
      }

      applyDamage(114, paramActor, 1.0F, false);

      this.anchor.resetToDead();
      ShowExplode(14.0F);
      setMesh(this.prop.meshANameDead);
      setDefaultPose();
      break;
    case 114:
      if (this.rope.dead) {
        return;
      }
      PartState.access$116(this.rope, paramFloat);
      if (this.rope.damage < 1.0F) {
        return;
      }
      if (paramBoolean) {
        mastersend_AKillCommand(114, paramActor);
      }

      if (this.ropeActor == null) {
        System.out.println("***balloon: strange kill r");
        return;
      }

      if (this.balloonActor != null) {
        this.balloonActor.somebodyKilled(114);
      }

      this.rope.resetToDead();
      if (this.ropeActor == null) break;
      this.ropeActor.somebodyKilled(114); break;
    case 98:
      if (this.balloon.dead) {
        return;
      }
      PartState.access$116(this.balloon, paramFloat);
      if (this.balloon.damage < 1.0F) {
        return;
      }
      if (paramBoolean) {
        mastersend_AKillCommand(98, paramActor);
      }

      if (this.balloonActor == null) {
        System.out.println("***balloon: strange kill b");
        return;
      }

      if (this.ropeActor != null) {
        this.ropeActor.somebodyKilled(98);
      }

      applyDamage(114, paramActor, 1.0F, false);

      this.balloon.resetToDead();
      if (this.balloonActor == null) break;
      this.balloonActor.somebodyKilled(98);
    }

    if (!getDiedFlag()) {
      World.onActorDied(this, paramActor);
      if (this.balloonActor != null) {
        World.onActorDied(this.balloonActor, paramActor);
      }
      if (this.ropeActor != null)
        World.onActorDied(this.ropeActor, paramActor);
    }
  }

  private void handleStartCommandFromMaster(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if ((paramBoolean1) || (paramBoolean3)) {
      paramBoolean2 = true;
    }

    if (paramBoolean1)
      this.anchor.resetToDead();
    else {
      this.anchor.resetToLive();
    }

    if (paramBoolean2)
      this.rope.resetToDead();
    else {
      this.rope.resetToLive();
    }

    if (paramBoolean3)
      this.balloon.resetToDead();
    else {
      this.balloon.resetToLive();
    }

    setMesh(paramBoolean1 ? this.prop.meshANameDead : this.prop.meshAName);
    setDefaultPose();
    recreateBalloon();
    recreateRope();

    boolean bool = (this.anchor.dead) || (this.rope.dead) || (this.balloon.dead);
    setDiedFlag(bool);
    if (this.balloonActor != null) {
      this.balloonActor.setDiedFlag(bool);
    }
    if (this.ropeActor != null)
      this.ropeActor.setDiedFlag(bool);
  }

  public void createNetObject(NetChannel paramNetChannel, int paramInt)
  {
    if (paramNetChannel == null)
    {
      this.net = new Master(this);
    }
    else
      this.net = new Mirror(this, paramNetChannel, paramInt);
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Class cls;
    public AeroanchoredGeneric.AeroanchoredProperties proper;

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2)
    {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
        if (f == -9865.3447F) {
          System.out.println("Aeroanchored: Parameter [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
        }
        else {
          System.out.println("Aeroanchored: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
        }

        throw new RuntimeException("Can't set property");
      }
      return f;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("Aeroanchored: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");
        System.out.println(str == null ? "not found" : "is empty");
        throw new RuntimeException("Can't set property");
      }
      return str;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2, String paramString3) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        return paramString3;
      }
      return str;
    }

    private static AeroanchoredGeneric.AeroanchoredProperties LoadAeroanchoredProperties(SectFile paramSectFile, String paramString, Class paramClass)
    {
      AeroanchoredGeneric.AeroanchoredProperties localAeroanchoredProperties = new AeroanchoredGeneric.AeroanchoredProperties();

      localAeroanchoredProperties.meshASummer = getS(paramSectFile, paramString, "MeshAnchorSummer");
      localAeroanchoredProperties.meshADesert = getS(paramSectFile, paramString, "MeshAnchorDesert", localAeroanchoredProperties.meshASummer);
      localAeroanchoredProperties.meshAWinter = getS(paramSectFile, paramString, "MeshAnchorWinter", localAeroanchoredProperties.meshASummer);
      localAeroanchoredProperties.meshASummerDead = getS(paramSectFile, paramString, "MeshAnchorSummerDamage", localAeroanchoredProperties.meshASummer);
      localAeroanchoredProperties.meshADesertDead = getS(paramSectFile, paramString, "MeshAnchorDesertDamage", localAeroanchoredProperties.meshASummerDead);
      localAeroanchoredProperties.meshAWinterDead = getS(paramSectFile, paramString, "MeshAnchorWinterDamage", localAeroanchoredProperties.meshASummerDead);

      localAeroanchoredProperties.meshBSummer = getS(paramSectFile, paramString, "MeshBalloonSummer");
      localAeroanchoredProperties.meshBDesert = getS(paramSectFile, paramString, "MeshBalloonDesert", localAeroanchoredProperties.meshBSummer);
      localAeroanchoredProperties.meshBWinter = getS(paramSectFile, paramString, "MeshBalloonWinter", localAeroanchoredProperties.meshBSummer);

      localAeroanchoredProperties.meshRSummer = getS(paramSectFile, paramString, "MeshRopeSummer");
      localAeroanchoredProperties.meshRDesert = getS(paramSectFile, paramString, "MeshRopeDesert", localAeroanchoredProperties.meshRSummer);
      localAeroanchoredProperties.meshRWinter = getS(paramSectFile, paramString, "MeshRopeWinter", localAeroanchoredProperties.meshRSummer);

      String str = getS(paramSectFile, paramString, "PanzerType", null);
      if (str == null) {
        str = "Car";
      }
      localAeroanchoredProperties.fnShotPanzer = TableFunctions.GetFunc2(str + "ShotPanzer");
      localAeroanchoredProperties.fnExplodePanzer = TableFunctions.GetFunc2(str + "ExplodePanzer");

      localAeroanchoredProperties.HEIGHT = getF(paramSectFile, paramString, "Height", 25.0F, 4001.0F);

      localAeroanchoredProperties.ROPE_SEG_LENGTH = getF(paramSectFile, paramString, "RopeSegLength", 10.0F, 1000.0F);

      localAeroanchoredProperties.PANZER_TNT_TYPE = getF(paramSectFile, paramString, "PanzerSubtype", 0.0F, 100.0F);
      localAeroanchoredProperties.PANZER_BODY = getF(paramSectFile, paramString, "PanzerBody", 0.001F, 9.999F);

      localAeroanchoredProperties.HITBY_MASK = (localAeroanchoredProperties.PANZER_BODY > 0.015F ? -2 : -1);

      localAeroanchoredProperties.explodeName = getS(paramSectFile, paramString, "Explode", "Aeroanchor");

      Property.set(paramClass, "iconName", "icons/" + getS(paramSectFile, paramString, "Icon") + ".mat");
      Property.set(paramClass, "meshName", localAeroanchoredProperties.meshASummer);

      return localAeroanchoredProperties;
    }

    public SPAWN(Class paramClass)
    {
      try
      {
        String str1 = paramClass.getName();
        int i = str1.lastIndexOf('.');
        int j = str1.lastIndexOf('$');
        if (i < j) {
          i = j;
        }
        String str2 = str1.substring(i + 1);
        this.proper = LoadAeroanchoredProperties(Statics.getTechnicsFile(), str2, paramClass);
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

    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      switch (World.cur().camouflage) {
      case 1:
        this.proper.meshAName = this.proper.meshAWinter;
        this.proper.meshANameDead = this.proper.meshAWinterDead;
        this.proper.meshRName = this.proper.meshRWinter;
        this.proper.meshBName = this.proper.meshBWinter;
        break;
      case 2:
        this.proper.meshAName = this.proper.meshADesert;
        this.proper.meshANameDead = this.proper.meshADesertDead;
        this.proper.meshRName = this.proper.meshRDesert;
        this.proper.meshBName = this.proper.meshBDesert;
        break;
      default:
        this.proper.meshAName = this.proper.meshASummer;
        this.proper.meshANameDead = this.proper.meshASummerDead;
        this.proper.meshRName = this.proper.meshRSummer;
        this.proper.meshBName = this.proper.meshBSummer;
      }

      AeroanchoredGeneric localAeroanchoredGeneric = null;
      try
      {
        AeroanchoredGeneric.access$1402(this.proper);
        AeroanchoredGeneric.access$1502(paramActorSpawnArg);
        localAeroanchoredGeneric = (AeroanchoredGeneric)this.cls.newInstance();
        AeroanchoredGeneric.access$1402(null);
        AeroanchoredGeneric.access$1502(null);
      } catch (Exception localException) {
        AeroanchoredGeneric.access$1402(null);
        AeroanchoredGeneric.access$1502(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create Aeroanchored object [class:" + this.cls.getName() + "]");

        return null;
      }
      return localAeroanchoredGeneric;
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted())
      {
        int j = paramNetMsgInput.readByte();
        NetMsgGuaranted localNetMsgGuaranted;
        switch (j) {
        case 73:
          if (isMirrored()) {
            localNetMsgGuaranted = new NetMsgGuaranted(paramNetMsgInput, 0);
            post(localNetMsgGuaranted);
          }
          int i = paramNetMsgInput.readUnsignedByte();
          AeroanchoredGeneric.this.handleStartCommandFromMaster((i & 0x1) == 0, (i & 0x2) == 0, (i & 0x4) == 0);

          return true;
        case 82:
          if (isMirrored()) {
            localNetMsgGuaranted = new NetMsgGuaranted(paramNetMsgInput, 0);
            post(localNetMsgGuaranted);
          }
          AeroanchoredGeneric.this.handleStartCommandFromMaster(false, false, false);

          return true;
        case 97:
        case 98:
        case 114:
          if (isMirrored()) {
            localNetMsgGuaranted = new NetMsgGuaranted(paramNetMsgInput, 1);
            post(localNetMsgGuaranted);
          }
          NetObj localNetObj = paramNetMsgInput.readNetObj();
          Actor localActor = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
          AeroanchoredGeneric.this.applyDamage(j, localActor, 1.0F, false);
          return true;
        }

        return true;
      }

      switch (paramNetMsgInput.readByte())
      {
      case 97:
      case 98:
      case 114:
        this.out.unLockAndSet(paramNetMsgInput, 1);
        this.out.setIncludeTime(false);
        postRealTo(Message.currentRealTime(), masterChannel(), this.out);
        break;
      }

      return true;
    }

    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4) {
      super(paramInt, i);
    }
  }

  class Master extends ActorNet
  {
    public Master(Actor arg2)
    {
      super();
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted()) {
        return true;
      }

      int i = paramNetMsgInput.readByte();

      switch (i)
      {
      case 97:
      case 98:
      case 114:
        int j = paramNetMsgInput.readUnsignedByte();
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        Actor localActor = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
        float f = (j + 1) / 256.0F;
        AeroanchoredGeneric.this.applyDamage(i, localActor, f, true);

        break;
      }

      return true;
    }
  }

  class Move extends Interpolate
  {
    Move()
    {
    }

    public boolean tick()
    {
      if ((AeroanchoredGeneric.this.isNetMirror()) && 
        (AeroanchoredGeneric.access$310(AeroanchoredGeneric.this) <= 0)) {
        AeroanchoredGeneric.access$302(AeroanchoredGeneric.this, 1);
        AeroanchoredGeneric.this.mirrorsend_DamageIfNeed();
      }

      if (!AeroanchoredGeneric.PartState.access$000(AeroanchoredGeneric.this.rope))
      {
        return true;
      }

      if (AeroanchoredGeneric.access$510(AeroanchoredGeneric.this) > 0L)
      {
        return true;
      }

      if (!Mission.isDeathmatch()) {
        return false;
      }

      if (!AeroanchoredGeneric.this.isNetMaster()) {
        AeroanchoredGeneric.access$502(AeroanchoredGeneric.this, 10000L);
        return true;
      }

      AeroanchoredGeneric.this.anchor.resetToLive();
      AeroanchoredGeneric.this.rope.resetToLive();
      AeroanchoredGeneric.this.balloon.resetToLive();
      AeroanchoredGeneric.this.balloon.resetToLive();

      AeroanchoredGeneric.this.setMesh(AeroanchoredGeneric.this.prop.meshAName);
      AeroanchoredGeneric.this.setDefaultPose();
      AeroanchoredGeneric.this.recreateBalloon();
      AeroanchoredGeneric.this.recreateRope();

      AeroanchoredGeneric.this.setDiedFlag(false);
      if (AeroanchoredGeneric.this.balloonActor != null) {
        AeroanchoredGeneric.this.balloonActor.setDiedFlag(false);
      }
      if (AeroanchoredGeneric.this.ropeActor != null) {
        AeroanchoredGeneric.this.ropeActor.setDiedFlag(false);
      }

      AeroanchoredGeneric.this.mastersend_RespawnCommand();

      return true;
    }
  }

  public static class PartState
  {
    private float damage;
    private Actor mirror_initiator;
    private boolean dead;

    void resetToLive()
    {
      this.damage = 0.0F;
      this.mirror_initiator = null;
      this.dead = false;
    }

    void resetToDead() {
      this.damage = 0.0F;
      this.mirror_initiator = null;
      this.dead = true;
    }
  }

  public static class AeroanchoredProperties
  {
    public String meshAName = null;
    public String meshANameDead = null;
    public String meshRName = null;
    public String meshBName = null;

    public String meshASummer = null;
    public String meshADesert = null;
    public String meshAWinter = null;
    public String meshASummerDead = null;
    public String meshADesertDead = null;
    public String meshAWinterDead = null;

    public String meshRSummer = null;
    public String meshRDesert = null;
    public String meshRWinter = null;

    public String meshBSummer = null;
    public String meshBDesert = null;
    public String meshBWinter = null;

    public float HEIGHT = 0.001F;
    public float ROPE_SEG_LENGTH = 0.001F;

    public TableFunction2 fnShotPanzer = null;
    public TableFunction2 fnExplodePanzer = null;

    public float PANZER_BODY = 0.001F;
    public float PANZER_TNT_TYPE = 1.0F;
    public int HITBY_MASK = -1;

    public String explodeName = null;
  }
}