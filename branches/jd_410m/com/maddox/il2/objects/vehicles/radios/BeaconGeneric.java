package com.maddox.il2.objects.vehicles.radios;

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
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
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

public abstract class BeaconGeneric extends ActorHMesh
  implements MsgExplosionListener, MsgShotListener, Obstacle, ActorAlign
{
  private BeaconProperties prop = null;
  private float heightAboveLandSurface;
  private int dying = 0;
  static final int DYING_NONE = 0;
  static final int DYING_DEAD = 1;
  public static BeaconProperties constr_arg1 = null;
  private static ActorSpawnArg constr_arg2 = null;
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  protected static Vector3d V = new Vector3d();
  private static final int numberOfSamplePoints = 500;
  private static final int attSamplesPerCycle = 20;
  private static float[] attenuationSamples = new float[500];
  private static int currentAttSampleSlot = 1;
  private static final int mountainSamplesPerRow = 50;
  private static final int mountainSamplesPerCycle = 10;
  private static int currentMntSampleCol = 0;
  private static int currentMntSampleRow = 0;
  private static float[][] mountainErrorSamples = new float[50][50];
  private static final float mntSampleRadius = 20000.0F;
  private static final float mntSingleSampleLen = 800.0F;
  private static final int EARTH_RADIUS = 6371000;
  private static float nightError = 0.0F;
  private static int terErrDirection = 1;
  private static int ngtErrDirection = -1;
  private static float mntNE = 0.0F;
  private static float mntSE = 0.0F;
  private static float mntSW = 0.0F;
  private static float mntNW = 0.0F;

  private static final float[] signalPropagationScale = { 0.0F, 0.4F, 0.6F, 0.77F, 0.89F, 0.97F, 1.0F };

  public static float getConeOfSilenceMultiplier(double paramDouble1, double paramDouble2)
  {
    float f = 57.324841F * (float)Math.atan2(paramDouble1 - paramDouble2, paramDouble2);
    return cvt(f, 20.0F, 40.0F, 0.0F, 1.0F);
  }

  public static float getTerrainAndNightError(Aircraft paramAircraft)
  {
    float f1 = paramAircraft.FM.Or.getYaw();
    float f2 = -45.0F;
    float f3 = mntNE;
    if (mntSE > f3)
    {
      f3 = mntSE;
      f2 = 45.0F;
    }
    if (mntSW > f3)
    {
      f3 = mntSW;
      f2 = 135.0F;
    }
    if (mntNW > f3)
    {
      f3 = mntNW;
      f2 = -135.0F;
    }
    float f4 = f1 - f2;
    f3 = cvt(f3, 15.0F, 1400.0F, 0.0F, 1.0F);
    float f5 = cvt((float)paramAircraft.FM.Loc.z, 3000.0F, 15000.0F, 1.0F, 0.0F);
    f3 *= f5;
    float f6 = (float)Math.random();
    if (f4 > 0.0F)
      terErrDirection = -1;
    else
      terErrDirection = 1;
    if (f6 < 0.01D) {
      terErrDirection = 0;
    }
    if (f6 < 0.007D)
      ngtErrDirection *= -1;
    else if (f6 < 0.13D)
      ngtErrDirection = 1;
    else if (f6 > 0.97D) {
      ngtErrDirection = 0;
    }
    float f7 = terErrDirection * (f3 * 30.0F + World.Rnd().nextFloat(-f3 * 8.0F, f3 * 8.0F));
    float f8 = ngtErrDirection * (nightError * 30.0F + World.Rnd().nextFloat(-nightError * 5.0F, nightError * 5.0F));

    return f7 + f8;
  }

  private static void sampleMountains(Aircraft paramAircraft)
  {
    float f1 = Math.abs(currentMntSampleCol - 25);
    for (int i = 0; i < 10; i++)
    {
      float f2 = -20000.0F + currentMntSampleRow * 800.0F;
      float f3 = -20000.0F + currentMntSampleCol * 800.0F;

      float f4 = Landscape.HQ_Air((float)paramAircraft.FM.Loc.x + f2, (float)paramAircraft.FM.Loc.y + f3);
      float f5 = Math.abs(currentMntSampleRow - 25);
      float f6 = Math.max(f5, f1);
      f4 *= cvt(f6, 0.0F, 25.0F, 1.0F, 0.5F);
      mountainErrorSamples[currentMntSampleRow][currentMntSampleCol] = f4;
      currentMntSampleRow += 1;
      if (currentMntSampleRow != 50)
        continue;
      currentMntSampleRow = 0;
      currentMntSampleCol += 1;
      f1 = Math.abs(currentMntSampleCol - 25);
      if (currentMntSampleCol == 50) {
        currentMntSampleCol = 0;
      }
    }

    i = 625;
    int k;
    for (int j = 0; j < 25; j++)
    {
      for (k = 0; k < 25; k++)
      {
        mntSW += mountainErrorSamples[j][k];
      }
    }
    mntSW /= i;

    for (j = 0; j < 25; j++)
    {
      for (k = 25; k < 50; k++)
      {
        mntNW += mountainErrorSamples[j][k];
      }
    }
    mntNW /= i;

    for (j = 25; j < 50; j++)
    {
      for (k = 25; k < 50; k++)
      {
        mntNE += mountainErrorSamples[j][k];
      }
    }
    mntNE /= i;

    for (j = 25; j < 50; j++)
    {
      for (k = 0; k < 25; k++)
      {
        mntSE += mountainErrorSamples[j][k];
      }
    }
    mntSE /= i;
  }

  public static float getSignalAttenuation(Point3d paramPoint3d, Aircraft paramAircraft, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    V.sub(paramAircraft.FM.Loc, paramPoint3d);
    double d1 = V.length();

    double d2 = 0.0D;

    double d3 = d1 / 500.0D;
    for (int i = 0; i < 20; i++)
    {
      double d4 = d3 * currentAttSampleSlot;
      V.normalize();
      V.scale(d4);
      float f4 = Landscape.HQ_Air((float)(paramPoint3d.x + V.x), (float)(paramPoint3d.y + V.y));
      double d6 = getCurvatureCorrectedHeight((float)(d4 / d1), d1, paramPoint3d.z, paramAircraft.FM.getAltitude());
      float f6 = (float)(d6 - f4);
      if (f6 < 0.0F)
        attenuationSamples[(currentAttSampleSlot - 1)] = (-f6);
      else {
        attenuationSamples[(currentAttSampleSlot - 1)] = 0.0F;
      }
      currentAttSampleSlot += 1;
      if (currentAttSampleSlot <= 500)
        continue;
      currentAttSampleSlot = 1;
    }

    float f1 = 0.0F;

    for (int j = 0; j < 500; j++)
    {
      if ((attenuationSamples[j] > f1) && (f1 > 0.0F))
      {
        f3 = attenuationSamples[j] - f1;
        d2 += attenuationSamples[j] * d3 + d3 * f3;
      }
      f1 = attenuationSamples[j];
    }

    d2 *= 0.166666D;

    if (paramBoolean4) {
      return 0.0F;
    }
    if (!paramBoolean3) {
      sampleMountains(paramAircraft);
    }
    float f2 = 0.0F;
    float f3 = 0.0F;
    double d5 = lineOfSightDelta(paramPoint3d.z, paramAircraft.FM.getAltitude(), d1);
    float f5;
    double d7;
    float f7;
    if (paramBoolean1)
    {
      f5 = 0.0F;
      if (d5 < 0.0D)
        f5 = (float)(-2.0D * d5);
      d7 = paramAircraft.FM.getAltitude() - paramPoint3d.z;
      f3 = cvt(World.Sun().ToSun.z, -0.2F, 0.1F, 0.75F, 1.0F);
      if ((World.Sun().ToSun.z > -0.1F) && (World.Sun().ToSun.z < 0.1F) && (Math.random() < 0.1D))
        f3 += (float)Math.random() * 0.2F;
      f7 = 1.0F - getConeOfSilenceMultiplier(d1, d7);
      f2 = cvt((float)(d2 + f5) * f3, 0.0F, 7000000.0F, 0.0F, 1.0F);
      f2 = f2 + f7 + floatindex(cvt((float)d1 * f3, 0.0F, 270000.0F, 0.0F, 6.0F), signalPropagationScale);
      if (f3 < 1.0F)
        nightError = cvt(f2, 0.65F, 1.0F, 0.0F, 1.0F);
      else {
        nightError = 0.0F;
      }
    }
    else if (paramBoolean3)
    {
      f2 = cvt((float)d2, 0.0F, 500000.0F, 0.0F, 1.0F);
      f5 = cvt((float)d5, -10000.0F, 0.0F, 1.0F, 0.0F);
      f2 = f2 + f5 + floatindex(cvt((float)d1, 0.0F, 190000.0F, 0.0F, 6.0F), signalPropagationScale);
    }
    else if (paramBoolean2)
    {
      f5 = 0.0F;
      if (d5 < 0.0D)
        f5 = (float)(-3.0D * d5);
      d7 = paramAircraft.FM.getAltitude() - paramPoint3d.z;
      f3 = cvt(World.Sun().ToSun.z, -0.2F, 0.2F, 0.4F, 1.0F);
      if ((World.Sun().ToSun.z > -0.1F) && (World.Sun().ToSun.z < 0.1F) && (Math.random() < 0.2D))
        f3 += (float)Math.random() * 0.3F;
      f7 = 1.0F - getConeOfSilenceMultiplier(d1, d7);
      f2 = cvt((float)(d2 + f5) * f3, 0.0F, 6000000.0F, 0.0F, 1.0F);
      f2 = f2 + f7 + floatindex(cvt((float)d1 * f3, 0.0F, 300000.0F, 0.0F, 6.0F), signalPropagationScale);
      if (f3 < 1.0F)
        nightError = cvt(f2, 0.65F, 1.0F, 0.0F, 1.0F);
      else {
        nightError = 0.0F;
      }
    }

    if (f2 > 1.0F)
      f2 = 1.0F;
    return f2;
  }

  private static double getCurvatureCorrectedHeight(float paramFloat1, double paramDouble1, double paramDouble2, float paramFloat2)
  {
    double d1 = paramFloat1 * Math.sin(paramDouble1 / 6371000.0D) * (6371000.0F + paramFloat2);
    double d2 = paramDouble2 + 6371000.0D + paramFloat1 * (Math.cos(paramDouble1 / 6371000.0D) * (6371000.0F + paramFloat2) - paramDouble2 - 6371000.0D);
    double d3 = Math.sqrt(d1 * d1 + d2 * d2) - 6371000.0D;
    if (d3 > 0.0D)
      return d3;
    return 0.0D;
  }

  public static double lineOfSightDelta(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    return (Math.sqrt(12.47599983215332D * paramDouble1) + Math.sqrt(12.47599983215332D * paramDouble2)) * 1000.0D - paramDouble3;
  }

  protected static float floatindex(float paramFloat, float[] paramArrayOfFloat)
  {
    int i = (int)paramFloat;
    if (i >= paramArrayOfFloat.length - 1) {
      return paramArrayOfFloat[(paramArrayOfFloat.length - 1)];
    }
    if (i < 0) {
      return paramArrayOfFloat[0];
    }
    if (i == 0)
    {
      if (paramFloat > 0.0F) {
        return paramArrayOfFloat[0] + paramFloat * (paramArrayOfFloat[1] - paramArrayOfFloat[0]);
      }
      return paramArrayOfFloat[0];
    }

    return paramArrayOfFloat[i] + paramFloat % i * (paramArrayOfFloat[(i + 1)] - paramArrayOfFloat[i]);
  }

  public static final double Rnd(double paramDouble1, double paramDouble2)
  {
    return World.Rnd().nextDouble(paramDouble1, paramDouble2);
  }

  public static final float Rnd(float paramFloat1, float paramFloat2) {
    return World.Rnd().nextFloat(paramFloat1, paramFloat2);
  }

  private boolean RndB(float paramFloat) {
    return World.Rnd().nextFloat(0.0F, 1.0F) < paramFloat;
  }

  private static final long SecsToTicks(float paramFloat) {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }

  public boolean isStaticPos() {
    return true;
  }

  public void msgShot(Shot paramShot) {
    paramShot.bodyMaterial = 2;
    if ((this.dying == 0) && (paramShot.power > 0.0F) && ((!isNetMirror()) || (!paramShot.isMirage())))
    {
      if (paramShot.powerType == 1) {
        if (!RndB(0.15F))
          Die(paramShot.initiator, 0, true);
      } else {
        float f1 = Shot.panzerThickness(this.pos.getAbsOrient(), paramShot.v, paramShot.chunkName.equalsIgnoreCase("Head"), this.prop.PANZER_BODY_FRONT, this.prop.PANZER_BODY_SIDE, this.prop.PANZER_BODY_BACK, this.prop.PANZER_BODY_TOP, this.prop.PANZER_HEAD, this.prop.PANZER_HEAD_TOP);

        f1 *= Rnd(0.93F, 1.07F);
        float f2 = this.prop.fnShotPanzer.Value(paramShot.power, f1);
        if ((f2 < 1000.0F) && ((f2 <= 1.0F) || (RndB(1.0F / f2))))
          Die(paramShot.initiator, 0, true);
      }
    }
  }

  public void msgExplosion(Explosion paramExplosion) {
    if ((this.dying == 0) && ((!isNetMirror()) || (!paramExplosion.isMirage())) && (paramExplosion.power > 0.0F))
    {
      int i = paramExplosion.powerType;
      if ((paramExplosion == null) || 
        (i == 1)) {
        if (TankGeneric.splintersKill(paramExplosion, this.prop.fnShotPanzer, Rnd(0.0F, 1.0F), Rnd(0.0F, 1.0F), this, 0.7F, 0.25F, this.prop.PANZER_BODY_FRONT, this.prop.PANZER_BODY_SIDE, this.prop.PANZER_BODY_BACK, this.prop.PANZER_BODY_TOP, this.prop.PANZER_HEAD, this.prop.PANZER_HEAD_TOP))
        {
          Die(paramExplosion.initiator, 0, true);
        }
      } else {
        int j = paramExplosion.powerType;
        if ((paramExplosion == null) || (
          (j == 2) && (paramExplosion.chunkName != null))) {
          Die(paramExplosion.initiator, 0, true);
        }
        else
        {
          float f1;
          if (paramExplosion.chunkName != null)
            f1 = 0.5F * paramExplosion.power;
          else
            f1 = paramExplosion.receivedTNTpower(this);
          f1 *= Rnd(0.95F, 1.05F);
          float f2 = this.prop.fnExplodePanzer.Value(f1, this.prop.PANZER_TNT_TYPE);

          if ((f2 < 1000.0F) && ((f2 <= 1.0F) || (RndB(1.0F / f2))))
          {
            Die(paramExplosion.initiator, 0, true);
          }
        }
      }
    }
  }

  private void ShowExplode(float paramFloat, Actor paramActor) {
    if (paramFloat > 0.0F)
      paramFloat = Rnd(paramFloat, paramFloat * 1.6F);
    Explosions.runByName(this.prop.explodeName, this, "Smoke", "SmokeHead", paramFloat, paramActor);
  }

  private void Die(Actor paramActor, short paramShort, boolean paramBoolean)
  {
    if (this.dying == 0) {
      if (paramShort <= 0) {
        if (isNetMirror()) {
          send_DeathRequest(paramActor);
          return;
        }
        i = 1;
      }
      this.dying = 1;
      World.onActorDied(this, paramActor);
      if (this.prop.meshName1 == null)
        mesh().makeAllMaterialsDarker(0.22F, 0.35F);
      else
        setMesh(this.prop.meshName1);
      int i = mesh().hookFind("Ground_Level");
      if (i != -1) {
        Matrix4d localMatrix4d = new Matrix4d();
        mesh().hookMatrix(i, localMatrix4d);
        this.heightAboveLandSurface = (float)(-localMatrix4d.m23);
      }
      Align();
      if (paramBoolean)
        ShowExplode(15.0F, paramActor);
      if (paramBoolean)
        send_DeathCommand(paramActor);
    }
  }

  public void destroy() {
    if (!isDestroyed())
      super.destroy();
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }

  protected BeaconGeneric() {
    this(constr_arg1, constr_arg2);
  }

  private BeaconGeneric(BeaconProperties paramBeaconProperties, ActorSpawnArg paramActorSpawnArg)
  {
    super(paramBeaconProperties.meshName);
    this.prop = paramBeaconProperties;
    paramActorSpawnArg.setStationary(this);
    collide(true);
    drawing(true);
    createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);
    this.heightAboveLandSurface = 0.0F;
    int i = mesh().hookFind("Ground_Level");
    if (i != -1) {
      Matrix4d localMatrix4d = new Matrix4d();
      mesh().hookMatrix(i, localMatrix4d);
      this.heightAboveLandSurface = (float)(-localMatrix4d.m23);
    } else {
      System.out.println("Stationary " + getClass().getName() + ": hook Ground_Level not found");
    }
    Align();
  }

  private void Align()
  {
    this.pos.getAbs(p);
    p.z = (Engine.land().HQ(p.x, p.y) + this.heightAboveLandSurface);
    o.setYPR(this.pos.getAbsOrient().getYaw(), 0.0F, 0.0F);

    this.pos.setAbs(p, o);
  }

  public void align() {
    Align();
  }

  public boolean unmovableInFuture()
  {
    return true;
  }

  public void collisionDeath() {
    if (!isNet()) {
      ShowExplode(-1.0F, null);
      destroy();
    }
  }

  public float futurePosition(float paramFloat, Point3d paramPoint3d) {
    this.pos.getAbs(paramPoint3d);
    return paramFloat <= 0.0F ? 0.0F : paramFloat;
  }

  private void send_DeathCommand(Actor paramActor) {
    if (isNetMaster()) {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      try {
        localNetMsgGuaranted.writeByte(68);
        localNetMsgGuaranted.writeNetObj(paramActor == null ? (ActorNet)null : paramActor.net);

        this.net.post(localNetMsgGuaranted);
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
    }
  }

  private void send_DeathRequest(Actor paramActor) {
    if ((isNetMirror()) && (!(this.net.masterChannel() instanceof NetChannelInStream)))
      try
      {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(100);
        localNetMsgGuaranted.writeNetObj(paramActor == null ? (ActorNet)null : paramActor.net);

        this.net.postTo(this.net.masterChannel(), localNetMsgGuaranted);
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
  }

  public void createNetObject(NetChannel paramNetChannel, int paramInt)
  {
    if (paramNetChannel == null)
      this.net = new Master(this);
    else
      this.net = new Mirror(this, paramNetChannel, paramInt);
  }

  public void netFirstUpdate(NetChannel paramNetChannel) throws IOException {
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    localNetMsgGuaranted.writeByte(73);
    if (this.dying == 0)
      localNetMsgGuaranted.writeShort(0);
    else
      localNetMsgGuaranted.writeShort(1);
    this.net.postTo(paramNetChannel, localNetMsgGuaranted);
  }

  protected static float cvt(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    paramFloat1 = Math.min(Math.max(paramFloat1, paramFloat2), paramFloat3);
    return paramFloat4 + (paramFloat5 - paramFloat4) * (paramFloat1 - paramFloat2) / (paramFloat3 - paramFloat2);
  }

  public static class BeaconProperties
  {
    public String meshName = null;
    public String meshName1 = null;
    public String meshSummer = null;
    public String meshDesert = null;
    public String meshWinter = null;
    public String meshSummer1 = null;
    public String meshDesert1 = null;
    public String meshWinter1 = null;
    public TableFunction2 fnShotPanzer = null;
    public TableFunction2 fnExplodePanzer = null;
    public float PANZER_BODY_FRONT = 0.001F;
    public float PANZER_BODY_BACK = 0.001F;
    public float PANZER_BODY_SIDE = 0.001F;
    public float PANZER_BODY_TOP = 0.001F;
    public float PANZER_HEAD = 0.001F;
    public float PANZER_HEAD_TOP = 0.001F;
    public float PANZER_TNT_TYPE = 1.0F;
    public int HITBY_MASK = -2;
    public String explodeName = null;
    public float innerMarkerDist = 0.0F;
    public float outerMarkerDist = 0.0F;
  }

  class Master extends ActorNet
  {
    public Master(Actor arg2)
    {
      super();
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted()) {
        if (paramNetMsgInput.readByte() != 100)
          return false;
      }
      else return false;
      if (BeaconGeneric.this.dying == 1)
        return true;
      NetObj localNetObj = paramNetMsgInput.readNetObj();
      Actor localActor = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
      BeaconGeneric.this.Die(localActor, 0, true);
      return true;
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted())
      {
        Object localObject;
        switch (paramNetMsgInput.readByte()) {
        case 73:
          if (isMirrored()) {
            NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted(paramNetMsgInput, 0);

            post(localNetMsgGuaranted);
          }
          int i = paramNetMsgInput.readShort();
          if ((i > 0) && (BeaconGeneric.this.dying != 1))
            BeaconGeneric.this.Die(null, 1, false);
          return true;
        case 68:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 1);

            post((NetMsgGuaranted)localObject);
          }
          if (BeaconGeneric.this.dying != 1) {
            localObject = paramNetMsgInput.readNetObj();

            Actor localActor = localObject == null ? null : ((ActorNet)localObject).actor();

            BeaconGeneric.this.Die(localActor, 1, true);
          }
          return true;
        case 100:
          localObject = new NetMsgGuaranted(paramNetMsgInput, 1);

          postTo(masterChannel(), (NetMsgGuaranted)localObject);
          return true;
        }

        return false;
      }

      return true;
    }

    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4) {
      super(paramInt, i);
    }
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Class cls;
    public BeaconGeneric.BeaconProperties proper;

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2)
    {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
        if (f == -9865.3447F) {
          System.out.println("Stationary: Parameter [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
        }
        else
        {
          System.out.println("Stationary: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
        }

        throw new RuntimeException("Can't set property");
      }
      return f;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2)
    {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("Stationary: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");

        System.out.println(str == null ? "not found" : "is empty");

        throw new RuntimeException("Can't set property");
      }
      return str;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2, String paramString3)
    {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0))
        return paramString3;
      return str;
    }

    public static BeaconGeneric.BeaconProperties LoadStationaryProperties(SectFile paramSectFile, String paramString, Class paramClass)
    {
      BeaconGeneric.BeaconProperties localBeaconProperties = new BeaconGeneric.BeaconProperties();

      String str = getS(paramSectFile, paramString, "PanzerType", null);
      if (str == null)
        str = "Tank";
      localBeaconProperties.fnShotPanzer = TableFunctions.GetFunc2(str + "ShotPanzer");

      localBeaconProperties.fnExplodePanzer = TableFunctions.GetFunc2(str + "ExplodePanzer");

      localBeaconProperties.PANZER_TNT_TYPE = getF(paramSectFile, paramString, "PanzerSubtype", 0.0F, 100.0F);

      localBeaconProperties.meshSummer = getS(paramSectFile, paramString, "MeshSummer");

      localBeaconProperties.meshDesert = getS(paramSectFile, paramString, "MeshDesert", localBeaconProperties.meshSummer);

      localBeaconProperties.meshWinter = getS(paramSectFile, paramString, "MeshWinter", localBeaconProperties.meshSummer);

      localBeaconProperties.meshSummer1 = getS(paramSectFile, paramString, "MeshSummerDamage", null);

      localBeaconProperties.meshDesert1 = getS(paramSectFile, paramString, "MeshDesertDamage", localBeaconProperties.meshSummer1);

      localBeaconProperties.meshWinter1 = getS(paramSectFile, paramString, "MeshWinterDamage", localBeaconProperties.meshSummer1);

      int i = (localBeaconProperties.meshSummer1 == null ? 1 : 0) + (localBeaconProperties.meshDesert1 == null ? 1 : 0) + (localBeaconProperties.meshWinter1 == null ? 1 : 0);

      if ((i != 0) && (i != 3)) {
        System.out.println("Stationary: Uncomplete set of damage meshes for '" + paramString + "'");

        throw new RuntimeException("Can't register beacon object");
      }
      localBeaconProperties.explodeName = getS(paramSectFile, paramString, "Explode", "Stationary");

      localBeaconProperties.PANZER_BODY_FRONT = getF(paramSectFile, paramString, "PanzerBodyFront", 0.001F, 9.999F);

      if (paramSectFile.get(paramString, "PanzerBodyBack", -9865.3447F) == -9865.3447F)
      {
        localBeaconProperties.PANZER_BODY_BACK = localBeaconProperties.PANZER_BODY_FRONT;

        localBeaconProperties.PANZER_BODY_SIDE = localBeaconProperties.PANZER_BODY_FRONT;

        localBeaconProperties.PANZER_BODY_TOP = localBeaconProperties.PANZER_BODY_FRONT;
      }
      else {
        localBeaconProperties.PANZER_BODY_BACK = getF(paramSectFile, paramString, "PanzerBodyBack", 0.001F, 9.999F);

        localBeaconProperties.PANZER_BODY_SIDE = getF(paramSectFile, paramString, "PanzerBodySide", 0.001F, 9.999F);

        localBeaconProperties.PANZER_BODY_TOP = getF(paramSectFile, paramString, "PanzerBodyTop", 0.001F, 9.999F);
      }

      if (paramSectFile.get(paramString, "PanzerHead", -9865.3447F) == -9865.3447F) {
        localBeaconProperties.PANZER_HEAD = localBeaconProperties.PANZER_BODY_FRONT;
      }
      else {
        localBeaconProperties.PANZER_HEAD = getF(paramSectFile, paramString, "PanzerHead", 0.001F, 9.999F);
      }
      if (paramSectFile.get(paramString, "PanzerHeadTop", -9865.3447F) == -9865.3447F)
      {
        localBeaconProperties.PANZER_HEAD_TOP = localBeaconProperties.PANZER_BODY_TOP;
      }
      else {
        localBeaconProperties.PANZER_HEAD_TOP = getF(paramSectFile, paramString, "PanzerHeadTop", 0.001F, 9.999F);
      }
      float f = Math.min(Math.min(localBeaconProperties.PANZER_BODY_BACK, localBeaconProperties.PANZER_BODY_TOP), Math.min(localBeaconProperties.PANZER_BODY_SIDE, localBeaconProperties.PANZER_HEAD_TOP));

      localBeaconProperties.HITBY_MASK = (f > 0.015F ? -2 : -1);
      Property.set(paramClass, "iconName", "icons/" + getS(paramSectFile, paramString, "Icon") + ".mat");

      Property.set(paramClass, "meshName", localBeaconProperties.meshSummer);
      try
      {
        if ((paramClass == Beacon.LorenzBLBeacon.class) || (paramClass == Beacon.LorenzBLBeacon_LongRunway.class) || (paramClass == Beacon.LorenzBLBeacon_AAIAS.class))
        {
          localBeaconProperties.innerMarkerDist = getF(paramSectFile, paramString, "InnerMarkerDist", 1.0F, 3000.0F);
          localBeaconProperties.outerMarkerDist = getF(paramSectFile, paramString, "OuterMarkerDist", 3000.0F, 30000.0F);
        }
      }
      catch (Exception localException)
      {
      }

      return localBeaconProperties;
    }

    public SPAWN(Class paramClass) {
      try {
        String str1 = paramClass.getName();
        int i = str1.lastIndexOf('.');
        int j = str1.lastIndexOf('$');
        if (i < j)
          i = j;
        String str2 = str1.substring(i + 1);
        this.proper = LoadStationaryProperties(Statics.getTechnicsFile(), str2, paramClass);
      }
      catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("Problem in spawn: " + paramClass.getName());
      }
      this.cls = paramClass;
      Spawn.add(this.cls, this);
    }

    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg) {
      switch (World.cur().camouflage) {
      case 1:
        this.proper.meshName = this.proper.meshWinter;
        this.proper.meshName1 = this.proper.meshWinter1;
        break;
      case 2:
        this.proper.meshName = this.proper.meshDesert;
        this.proper.meshName1 = this.proper.meshDesert1;
        break;
      default:
        this.proper.meshName = this.proper.meshSummer;
        this.proper.meshName1 = this.proper.meshSummer1;
      }Object localObject = null;
      BeaconGeneric localBeaconGeneric;
      try { BeaconGeneric.constr_arg1 = this.proper;
        BeaconGeneric.access$002(paramActorSpawnArg);
        localBeaconGeneric = (BeaconGeneric)this.cls.newInstance();
        BeaconGeneric.constr_arg1 = null;
        BeaconGeneric.access$002(null);
      } catch (Exception localException) {
        BeaconGeneric.constr_arg1 = null;
        BeaconGeneric.access$002(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create Stationary object [class:" + this.cls.getName() + "]");

        return null;
      }
      return localBeaconGeneric;
    }
  }
}