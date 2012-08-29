package com.maddox.il2.objects.vehicles.artillery;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.StrengthProperties;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.Wreckage;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class RocketryGeneric extends ActorMesh
  implements MsgCollisionListener, MsgExplosionListener, MsgShotListener, Prey
{
  private static HashMap rocketryMap = new HashMap();

  RocketryProperties prop = null;

  MeshesNames meshNames = null;

  private Point3d targetPos = null;

  private Point3d begPos_wagon = new Point3d();
  private Point3d endPos_wagon = new Point3d();
  private Point3d begPos_rocket = new Point3d();
  private Point3d endPos_rocket = new Point3d();

  private int launchIntervalS = 0;

  private ArrayList rs = new ArrayList();
  private int nextFreeIdR;
  private float damage = 0.0F;

  private long actionTimeMS = 0L;
  private int countRockets;
  private static RangeRandom rndSeed = new RangeRandom();

  private final boolean Corpse()
  {
    return this.damage >= 1.0F;
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public boolean isStaticPos()
  {
    return true;
  }

  public static final float RndF(float paramFloat1, float paramFloat2) {
    return World.Rnd().nextFloat(paramFloat1, paramFloat2);
  }

  public static final int RndI(int paramInt1, int paramInt2) {
    return World.Rnd().nextInt(paramInt1, paramInt2);
  }

  private static final long SecsToTicks(float paramFloat) {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (Corpse()) {
      return;
    }
    if (!Actor.isValid(paramActor)) {
      return;
    }
    if ((paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet != null) && (paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirror())) {
      return;
    }
    if ((paramActor instanceof Wreckage)) {
      return;
    }

    if (!(paramActor instanceof Aircraft)) {
      return;
    }
    if (paramActor.getSpeed(null) < 28.0D) {
      return;
    }
    if ((paramString2 != null) && (
      (paramString2.startsWith("Wing")) || (paramString2.startsWith("Stab")) || (paramString2.startsWith("Arone")) || (paramString2.startsWith("Vator")) || (paramString2.startsWith("Keel")) || (paramString2.startsWith("Rudder")) || (paramString2.startsWith("Pilot"))))
    {
      return;
    }

    if (isNetMirror())
      sendRampDamage_Mirror(1.0F, paramActor);
    else
      handleDamageRamp_Both(1.0F, paramActor, true);
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = 2;

    if (Corpse()) {
      return;
    }

    if (paramShot.power <= 0.0F) {
      return;
    }

    if ((isNetMirror()) && 
      (paramShot.isMirage())) {
      return;
    }

    float f1 = paramShot.power;
    if (paramShot.powerType == 1) {
      f1 = paramShot.power / 2.4E-007F;
    }

    f1 *= RndF(1.0F, 1.1F);

    if (f1 < this.prop.stre.SHOT_MIN_ENERGY)
    {
      return;
    }

    float f2 = f1 / this.prop.stre.SHOT_MAX_ENERGY;
    if (isNetMirror())
      sendRampDamage_Mirror(f2, paramShot.initiator);
    else
      handleDamageRamp_Both(f2, paramShot.initiator, true);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (Corpse()) {
      return;
    }

    if ((isNetMirror()) && 
      (paramExplosion.isMirage())) {
      return;
    }

    float f1 = paramExplosion.power;

    if ((paramExplosion.powerType == 2) && (paramExplosion.chunkName != null)) {
      f1 *= 4.0F;
    }

    float f2 = -1.0F;
    float f3;
    if (paramExplosion.chunkName != null) {
      f3 = f1;

      f3 *= RndF(1.0F, 1.1F);

      if (f3 >= this.prop.stre.EXPLHIT_MIN_TNT)
      {
        f2 = f3 / this.prop.stre.EXPLHIT_MAX_TNT;
      }

    }

    if (f2 < 0.0F) {
      f3 = paramExplosion.receivedTNT_1meter(this);

      f3 *= RndF(1.0F, 1.1F);

      if (f3 >= this.prop.stre.EXPLNEAR_MIN_TNT)
      {
        f2 = f3 / this.prop.stre.EXPLNEAR_MAX_TNT;
      }
    }

    if (f2 > 0.0F)
      if (isNetMirror())
        sendRampDamage_Mirror(f2, paramExplosion.initiator);
      else
        handleDamageRamp_Both(f2, paramExplosion.initiator, true);
  }

  private static void getHookOffset(Mesh paramMesh, String paramString, boolean paramBoolean, Point3d paramPoint3d)
  {
    int i = paramMesh.hookFind(paramString);
    if (i == -1) {
      if (paramBoolean) {
        System.out.println("Rocketry: Hook [" + paramString + "] not found");
        throw new RuntimeException("Can't work");
      }
      paramPoint3d.set(0.0D, 0.0D, 0.0D);
    }
    Matrix4d localMatrix4d = new Matrix4d();
    paramMesh.hookMatrix(i, localMatrix4d);
    paramPoint3d.set(localMatrix4d.m03, localMatrix4d.m13, localMatrix4d.m23);
  }

  TrajSeg[] _computeFallTrajectory_(int paramInt, Point3d paramPoint3d, Vector3d paramVector3d)
  {
    TrajSeg[] arrayOfTrajSeg = new TrajSeg[1];

    for (int i = 0; i < arrayOfTrajSeg.length; i++) {
      arrayOfTrajSeg[i] = new TrajSeg();
    }

    arrayOfTrajSeg[0].pos0.set(paramPoint3d);
    arrayOfTrajSeg[0].v0.set(paramVector3d);
    arrayOfTrajSeg[0].a.set(0.0D, 0.0D, -3.0D);
    arrayOfTrajSeg[0].t0 = 0.0D;
    arrayOfTrajSeg[0].t = 500.0D;

    return arrayOfTrajSeg;
  }

  TrajSeg[] _computeWagonTrajectory_(int paramInt)
  {
    rndSeed.setSeed(paramInt);

    Vector2d localVector2d = new Vector2d();

    localVector2d.set(this.endPos_wagon.jdField_x_of_type_Double - this.begPos_wagon.jdField_x_of_type_Double, this.endPos_wagon.jdField_y_of_type_Double - this.begPos_wagon.jdField_y_of_type_Double);
    localVector2d.normalize();

    TrajSeg[] arrayOfTrajSeg = new TrajSeg[2];

    for (int i = 0; i < arrayOfTrajSeg.length; i++) {
      arrayOfTrajSeg[i] = new TrajSeg();
    }

    Vector3d localVector3d = new Vector3d();

    float f = this.prop.TAKEOFF_SPEED;

    localVector3d.sub(this.endPos_wagon, this.begPos_wagon);
    double d1 = localVector3d.length();
    localVector3d.scale(1.0D / d1);

    arrayOfTrajSeg[0].v0.set(0.0D, 0.0D, 0.0D);
    arrayOfTrajSeg[0].pos0.set(this.begPos_wagon);

    arrayOfTrajSeg[0].t = (2.0D * d1 / (0.0D + f));
    arrayOfTrajSeg[0].a.set(localVector3d);
    arrayOfTrajSeg[0].a.scale(f / arrayOfTrajSeg[0].t);

    f = this.prop.TAKEOFF_SPEED * rndSeed.nextFloat(0.85F, 0.97F);

    double d2 = localVector3d.jdField_z_of_type_Double * d1;
    double d3 = Math.sqrt(d1 * d1 - d2 * d2);
    arrayOfTrajSeg[1].v0.set(d3, 0.0D, d2);
    arrayOfTrajSeg[1].v0.normalize();
    arrayOfTrajSeg[1].v0.scale(f);

    arrayOfTrajSeg[1].pos0.set(0.0D, 0.0D, this.endPos_wagon.jdField_z_of_type_Double);

    arrayOfTrajSeg[1].t = (30.0D + 2.0D * (arrayOfTrajSeg[1].v0.jdField_z_of_type_Double / 9.800000000000001D));
    arrayOfTrajSeg[1].a.set(0.0D, 0.0D, -9.800000000000001D);

    for (int j = 0; j <= 1; j++) {
      if (arrayOfTrajSeg[j].t <= 0.0D) {
        return null;
      }

    }

    arrayOfTrajSeg[0].t0 = 0.0D;
    for (int k = 1; k <= 1; k++) {
      arrayOfTrajSeg[k].t0 = (arrayOfTrajSeg[(k - 1)].t0 + arrayOfTrajSeg[(k - 1)].t);
    }

    for (int m = 1; m <= 1; m++) {
      arrayOfTrajSeg[m].pos0.set(localVector2d.jdField_x_of_type_Double * arrayOfTrajSeg[m].pos0.jdField_x_of_type_Double, localVector2d.jdField_y_of_type_Double * arrayOfTrajSeg[m].pos0.jdField_x_of_type_Double, arrayOfTrajSeg[m].pos0.jdField_z_of_type_Double);
      arrayOfTrajSeg[m].pos0.add(this.endPos_wagon.jdField_x_of_type_Double, this.endPos_wagon.jdField_y_of_type_Double, 0.0D);
      arrayOfTrajSeg[m].v0.set(localVector2d.jdField_x_of_type_Double * arrayOfTrajSeg[m].v0.jdField_x_of_type_Double, localVector2d.jdField_y_of_type_Double * arrayOfTrajSeg[m].v0.jdField_x_of_type_Double, arrayOfTrajSeg[m].v0.jdField_z_of_type_Double);
      arrayOfTrajSeg[m].a.set(localVector2d.jdField_x_of_type_Double * arrayOfTrajSeg[m].a.jdField_x_of_type_Double, localVector2d.jdField_y_of_type_Double * arrayOfTrajSeg[m].a.jdField_x_of_type_Double, arrayOfTrajSeg[m].a.jdField_z_of_type_Double);
    }

    return arrayOfTrajSeg;
  }

  private TrajSeg[] _computeAirTrajectory_(int paramInt)
  {
    rndSeed.setSeed(paramInt);

    Point3d localPoint3d1 = new Point3d();
    localPoint3d1.set(this.targetPos);

    float f1 = rndSeed.nextFloat(0.0F, 359.98999F);
    float f2 = rndSeed.nextFloat(0.0F, this.prop.MAX_ERR_HIT_DISTANCE);
    localPoint3d1.add(Geom.cosDeg(f1) * f2, Geom.sinDeg(f1) * f2, 0.0D);

    double d1 = this.prop.FLY_HEIGHT + rndSeed.nextFloat(-this.prop.MAX_ERR_HEIGHT, this.prop.MAX_ERR_HEIGHT);

    Point3d localPoint3d2 = new Point3d();
    localPoint3d2.set(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
    localPoint3d2.jdField_z_of_type_Double = d1;

    Object localObject = new Point2d(localPoint3d1.jdField_x_of_type_Double, localPoint3d1.jdField_y_of_type_Double);
    Point2d localPoint2d = new Point2d(localPoint3d2.jdField_x_of_type_Double, localPoint3d2.jdField_y_of_type_Double);
    double d2 = ((Point2d)localObject).distance(localPoint2d);

    localObject = new Vector2d();

    ((Vector2d)localObject).set(localPoint3d1.jdField_x_of_type_Double - localPoint3d2.jdField_x_of_type_Double, localPoint3d1.jdField_y_of_type_Double - localPoint3d2.jdField_y_of_type_Double);
    ((Vector2d)localObject).normalize();

    float f3 = this.prop.MAX_SPEED;

    TrajSeg[] arrayOfTrajSeg = new TrajSeg[3];

    for (int i = 0; i < arrayOfTrajSeg.length; i++) {
      arrayOfTrajSeg[i] = new TrajSeg();
    }

    arrayOfTrajSeg[0].v0.set(f3, 0.0D, 0.0D);
    arrayOfTrajSeg[1].v0.set(f3, 0.0D, 0.0D);
    arrayOfTrajSeg[2].v0.set(f3 * Geom.cosDeg(this.prop.HIT_ANGLE), 0.0D, -f3 * Geom.sinDeg(this.prop.HIT_ANGLE));

    arrayOfTrajSeg[0].pos0.set(0.0D, 0.0D, d1);

    arrayOfTrajSeg[2].pos0.set(d2, 0.0D, localPoint3d1.jdField_z_of_type_Double);

    arrayOfTrajSeg[1].t = (2.0D * (arrayOfTrajSeg[2].pos0.jdField_z_of_type_Double - d1) / (arrayOfTrajSeg[1].v0.jdField_z_of_type_Double + arrayOfTrajSeg[2].v0.jdField_z_of_type_Double));

    arrayOfTrajSeg[1].pos0.set(arrayOfTrajSeg[2].pos0.jdField_x_of_type_Double - 0.5D * (arrayOfTrajSeg[1].v0.jdField_x_of_type_Double + arrayOfTrajSeg[2].v0.jdField_x_of_type_Double) * arrayOfTrajSeg[1].t, 0.0D, d1);

    arrayOfTrajSeg[2].t = 100.0D;

    arrayOfTrajSeg[0].t = (2.0D * (arrayOfTrajSeg[1].pos0.jdField_x_of_type_Double - arrayOfTrajSeg[0].pos0.jdField_x_of_type_Double) / (arrayOfTrajSeg[0].v0.jdField_x_of_type_Double + arrayOfTrajSeg[1].v0.jdField_x_of_type_Double));

    for (i = 0; i <= 2; i++) {
      if (arrayOfTrajSeg[i].t <= 0.0D) {
        return null;
      }

    }

    arrayOfTrajSeg[0].a.set(0.0D, 0.0D, 0.0D);
    for (int j = 1; j <= 1; j++) {
      arrayOfTrajSeg[j].a.sub(arrayOfTrajSeg[(j + 1)].v0, arrayOfTrajSeg[j].v0);
      arrayOfTrajSeg[j].a.scale(1.0D / arrayOfTrajSeg[j].t);
    }
    arrayOfTrajSeg[2].a.set(0.0D, 0.0D, 0.0D);

    arrayOfTrajSeg[0].t0 = 0.0D;
    for (int k = 1; k <= 2; k++) {
      arrayOfTrajSeg[k].t0 = (arrayOfTrajSeg[(k - 1)].t0 + arrayOfTrajSeg[(k - 1)].t);
    }

    for (int m = 0; m <= 2; m++) {
      arrayOfTrajSeg[m].pos0.set(((Vector2d)localObject).jdField_x_of_type_Double * arrayOfTrajSeg[m].pos0.jdField_x_of_type_Double, ((Vector2d)localObject).jdField_y_of_type_Double * arrayOfTrajSeg[m].pos0.jdField_x_of_type_Double, arrayOfTrajSeg[m].pos0.jdField_z_of_type_Double);
      arrayOfTrajSeg[m].pos0.add(localPoint3d2.jdField_x_of_type_Double, localPoint3d2.jdField_y_of_type_Double, 0.0D);
      arrayOfTrajSeg[m].v0.set(((Vector2d)localObject).jdField_x_of_type_Double * arrayOfTrajSeg[m].v0.jdField_x_of_type_Double, ((Vector2d)localObject).jdField_y_of_type_Double * arrayOfTrajSeg[m].v0.jdField_x_of_type_Double, arrayOfTrajSeg[m].v0.jdField_z_of_type_Double);
      arrayOfTrajSeg[m].a.set(((Vector2d)localObject).jdField_x_of_type_Double * arrayOfTrajSeg[m].a.jdField_x_of_type_Double, ((Vector2d)localObject).jdField_y_of_type_Double * arrayOfTrajSeg[m].a.jdField_x_of_type_Double, arrayOfTrajSeg[m].a.jdField_z_of_type_Double);
    }

    return (TrajSeg)arrayOfTrajSeg;
  }

  private TrajSeg[] _computeRampTrajectory_(int paramInt, boolean paramBoolean)
  {
    rndSeed.setSeed(paramInt);

    Point3d localPoint3d = new Point3d();
    localPoint3d.set(this.targetPos);

    float f1 = rndSeed.nextFloat(0.0F, 359.98999F);
    float f2 = rndSeed.nextFloat(0.0F, this.prop.MAX_ERR_HIT_DISTANCE);
    localPoint3d.add(Geom.cosDeg(f1) * f2, Geom.sinDeg(f1) * f2, 0.0D);

    Object localObject = new Point2d(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double);
    Point2d localPoint2d = new Point2d(this.endPos_rocket.jdField_x_of_type_Double, this.endPos_rocket.jdField_y_of_type_Double);
    double d1 = ((Point2d)localObject).distance(localPoint2d);

    localObject = new Vector2d();

    ((Vector2d)localObject).set(localPoint3d.jdField_x_of_type_Double - this.endPos_rocket.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double - this.endPos_rocket.jdField_y_of_type_Double);
    ((Vector2d)localObject).normalize();

    float f3 = paramBoolean ? 0.5F : 1.0F;
    double d2 = f3 * this.prop.FLY_HEIGHT + f3 * rndSeed.nextFloat(-this.prop.MAX_ERR_HEIGHT, this.prop.MAX_ERR_HEIGHT);

    float f4 = paramBoolean ? this.prop.TAKEOFF_SPEED + 1.0F : this.prop.MAX_SPEED;

    TrajSeg[] arrayOfTrajSeg = new TrajSeg[6];

    for (int i = 0; i < arrayOfTrajSeg.length; i++) {
      arrayOfTrajSeg[i] = new TrajSeg();
    }

    Vector3d localVector3d = new Vector3d();

    localVector3d.sub(this.endPos_rocket, this.begPos_rocket);
    double d3 = localVector3d.length();
    localVector3d.scale(1.0D / d3);

    arrayOfTrajSeg[0].v0.set(0.0D, 0.0D, 0.0D);
    arrayOfTrajSeg[0].pos0.set(this.begPos_rocket);

    arrayOfTrajSeg[0].t = (2.0D * d3 / (0.0D + this.prop.TAKEOFF_SPEED));
    arrayOfTrajSeg[0].a.set(localVector3d);
    arrayOfTrajSeg[0].a.scale(this.prop.TAKEOFF_SPEED / arrayOfTrajSeg[0].t);

    double d4 = localVector3d.jdField_z_of_type_Double * d3;
    double d5 = Math.sqrt(d3 * d3 - d4 * d4);
    arrayOfTrajSeg[1].v0.set(d5, 0.0D, d4);
    arrayOfTrajSeg[1].v0.normalize();
    arrayOfTrajSeg[1].v0.scale(this.prop.TAKEOFF_SPEED);

    arrayOfTrajSeg[2].v0.set(this.prop.TAKEOFF_SPEED, 0.0D, 0.0D);
    arrayOfTrajSeg[3].v0.set(f4, 0.0D, 0.0D);
    arrayOfTrajSeg[4].v0.set(f4, 0.0D, 0.0D);
    arrayOfTrajSeg[5].v0.set(f4 * Geom.cosDeg(this.prop.HIT_ANGLE), 0.0D, -f4 * Geom.sinDeg(this.prop.HIT_ANGLE));

    arrayOfTrajSeg[1].pos0.set(0.0D, 0.0D, this.endPos_rocket.jdField_z_of_type_Double);

    arrayOfTrajSeg[1].t = (2.0D * (d2 - arrayOfTrajSeg[1].pos0.jdField_z_of_type_Double) / (arrayOfTrajSeg[1].v0.jdField_z_of_type_Double + 0.0D));

    arrayOfTrajSeg[2].pos0.set(arrayOfTrajSeg[1].pos0.jdField_x_of_type_Double + 0.5D * (arrayOfTrajSeg[1].v0.jdField_x_of_type_Double + arrayOfTrajSeg[2].v0.jdField_x_of_type_Double) * arrayOfTrajSeg[1].t, 0.0D, d2);
    arrayOfTrajSeg[2].t = this.prop.SPEEDUP_TIME;

    arrayOfTrajSeg[3].pos0.set(arrayOfTrajSeg[2].pos0.jdField_x_of_type_Double + 0.5D * (arrayOfTrajSeg[2].v0.jdField_x_of_type_Double + arrayOfTrajSeg[3].v0.jdField_x_of_type_Double) * arrayOfTrajSeg[2].t, 0.0D, d2);

    arrayOfTrajSeg[5].pos0.set(d1, 0.0D, localPoint3d.jdField_z_of_type_Double);

    arrayOfTrajSeg[4].t = (2.0D * (arrayOfTrajSeg[5].pos0.jdField_z_of_type_Double - d2) / (arrayOfTrajSeg[4].v0.jdField_z_of_type_Double + arrayOfTrajSeg[5].v0.jdField_z_of_type_Double));

    arrayOfTrajSeg[4].pos0.set(arrayOfTrajSeg[5].pos0.jdField_x_of_type_Double - 0.5D * (arrayOfTrajSeg[4].v0.jdField_x_of_type_Double + arrayOfTrajSeg[5].v0.jdField_x_of_type_Double) * arrayOfTrajSeg[4].t, 0.0D, d2);

    arrayOfTrajSeg[5].t = 100.0D;

    arrayOfTrajSeg[3].t = (2.0D * (arrayOfTrajSeg[4].pos0.jdField_x_of_type_Double - arrayOfTrajSeg[3].pos0.jdField_x_of_type_Double) / (arrayOfTrajSeg[3].v0.jdField_x_of_type_Double + arrayOfTrajSeg[4].v0.jdField_x_of_type_Double));

    for (int j = 0; j <= 5; j++) {
      if (arrayOfTrajSeg[j].t <= 0.0D) {
        return null;
      }

    }

    for (int k = 1; k <= 4; k++) {
      arrayOfTrajSeg[k].a.sub(arrayOfTrajSeg[(k + 1)].v0, arrayOfTrajSeg[k].v0);
      arrayOfTrajSeg[k].a.scale(1.0D / arrayOfTrajSeg[k].t);
    }
    arrayOfTrajSeg[5].a.set(0.0D, 0.0D, 0.0D);

    arrayOfTrajSeg[0].t0 = 0.0D;
    for (int m = 1; m <= 5; m++) {
      arrayOfTrajSeg[m].t0 = (arrayOfTrajSeg[(m - 1)].t0 + arrayOfTrajSeg[(m - 1)].t);
    }

    for (int n = 1; n <= 5; n++) {
      arrayOfTrajSeg[n].pos0.set(((Vector2d)localObject).jdField_x_of_type_Double * arrayOfTrajSeg[n].pos0.jdField_x_of_type_Double, ((Vector2d)localObject).jdField_y_of_type_Double * arrayOfTrajSeg[n].pos0.jdField_x_of_type_Double, arrayOfTrajSeg[n].pos0.jdField_z_of_type_Double);
      arrayOfTrajSeg[n].pos0.add(this.endPos_rocket.jdField_x_of_type_Double, this.endPos_rocket.jdField_y_of_type_Double, 0.0D);
      arrayOfTrajSeg[n].v0.set(((Vector2d)localObject).jdField_x_of_type_Double * arrayOfTrajSeg[n].v0.jdField_x_of_type_Double, ((Vector2d)localObject).jdField_y_of_type_Double * arrayOfTrajSeg[n].v0.jdField_x_of_type_Double, arrayOfTrajSeg[n].v0.jdField_z_of_type_Double);
      arrayOfTrajSeg[n].a.set(((Vector2d)localObject).jdField_x_of_type_Double * arrayOfTrajSeg[n].a.jdField_x_of_type_Double, ((Vector2d)localObject).jdField_y_of_type_Double * arrayOfTrajSeg[n].a.jdField_x_of_type_Double, arrayOfTrajSeg[n].a.jdField_z_of_type_Double);
    }

    return (TrajSeg)arrayOfTrajSeg;
  }

  private TrajSeg[] computeNormalTrajectory(int paramInt)
  {
    TrajSeg[] arrayOfTrajSeg = null;

    if (this.prop.air) {
      arrayOfTrajSeg = _computeAirTrajectory_(paramInt);
    } else {
      arrayOfTrajSeg = _computeRampTrajectory_(paramInt, false);
      if (arrayOfTrajSeg == null) {
        arrayOfTrajSeg = _computeRampTrajectory_(paramInt, true);
      }
    }

    return arrayOfTrajSeg;
  }

  private RocketryGeneric(RocketryProperties paramRocketryProperties, MeshesNames paramMeshesNames, String paramString, int paramInt1, NetChannel paramNetChannel, int paramInt2, double paramDouble1, double paramDouble2, float paramFloat1, Point2d paramPoint2d, float paramFloat2, float paramFloat3, int paramInt3)
  {
    super(paramMeshesNames.ramp);
    this.prop = paramRocketryProperties;
    this.meshNames = paramMeshesNames;

    this.countRockets = paramInt3;
    if ((this.countRockets == 0) || (paramPoint2d == null)) {
      this.countRockets = 0;
      paramPoint2d = null;
      this.targetPos = null;
    }
    else {
      this.targetPos = new Point3d();
      this.targetPos.set(paramPoint2d.jdField_x_of_type_Double, paramPoint2d.jdField_y_of_type_Double, Engine.land().HQ(paramPoint2d.jdField_x_of_type_Double, paramPoint2d.jdField_y_of_type_Double));
    }

    setName(paramString);
    setArmy(paramInt1);

    Point3d localPoint3d = new Point3d();
    if (this.prop.air) {
      localPoint3d.set(paramDouble1, paramDouble2, this.prop.FLY_HEIGHT);
    } else {
      localPoint3d.set(paramDouble1, paramDouble2, Engine.land().HQ(paramDouble1, paramDouble2));
      localObject = new Point3d();
      getHookOffset(mesh(), "Ground_Level", false, (Point3d)localObject);
      localPoint3d.jdField_z_of_type_Double -= ((Point3d)localObject).jdField_z_of_type_Double;
    }

    Object localObject = new Orient();
    if (this.targetPos == null) {
      ((Orient)localObject).set(paramFloat1, 0.0F, 0.0F);
    }
    else {
      Vector3d localVector3d = new Vector3d();
      localVector3d.sub(this.targetPos, localPoint3d);
      localVector3d.jdField_z_of_type_Double = 0.0D;
      ((Orient)localObject).setAT0(localVector3d);
    }

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localPoint3d, (Orient)localObject);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

    if (this.prop.air) {
      collide(false);
      drawing(false);
    } else {
      collide(true);
      drawing(true);
    }

    if (this.prop.air) {
      this.begPos_wagon = null;
      this.endPos_wagon = null;
      this.begPos_rocket = null;
      this.endPos_rocket = null;
    } else {
      getHookOffset(mesh(), "_begWagon", true, this.begPos_wagon);
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().transform(this.begPos_wagon);
      getHookOffset(mesh(), "_endWagon", true, this.endPos_wagon);
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().transform(this.endPos_wagon);

      getHookOffset(mesh(), "_begRocket", true, this.begPos_rocket);
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().transform(this.begPos_rocket);
      getHookOffset(mesh(), "_endRocket", true, this.endPos_rocket);
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().transform(this.endPos_rocket);
    }

    int i = (int)(paramFloat2 * 60.0F + 0.5F);
    if (i < 0) {
      i = 0;
    }
    if (i > 14400) {
      i = 14400;
    }

    this.launchIntervalS = (int)(paramFloat3 * 60.0F + 0.5F);
    if (this.launchIntervalS < 180) {
      this.launchIntervalS = 180;
    }
    if (this.launchIntervalS > 14400) {
      this.launchIntervalS = 14400;
    }

    this.damage = 0.0F;
    this.actionTimeMS = 9223372036854775807L;

    createNetObject(paramNetChannel, paramInt2);

    if (this.targetPos != null) {
      if (isNetMaster()) {
        long l = 1000L * i - 1000L * this.launchIntervalS / 2L;
        if (l <= 0L)
        {
          prepareLaunch_Master(i);
        }
        else
          this.actionTimeMS = (Time.current() + l);
      }
      else
      {
        this.actionTimeMS = 9223372036854775807L;
      }

    }

    if (!interpEnd("move"))
      interpPut(new Move(), "move", Time.current(), null);
  }

  public int HitbyMask()
  {
    return -2;
  }

  public int chooseBulletType(BulletProperties[] paramArrayOfBulletProperties)
  {
    if (Corpse()) {
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

    if (paramArrayOfBulletProperties[0].powerType == 0)
    {
      return 1;
    }

    return 0;
  }

  public int chooseShotpoint(BulletProperties paramBulletProperties) {
    if (Corpse()) {
      return -1;
    }
    return 0;
  }

  public boolean getShotpointOffset(int paramInt, Point3d paramPoint3d) {
    if (Corpse()) {
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

  private RocketryRocket findMyRocket(int paramInt)
  {
    for (int i = 0; i < this.rs.size(); i++) {
      if (((RocketryRocket)this.rs.get(i)).idR == paramInt) {
        return (RocketryRocket)this.rs.get(i);
      }
    }
    return null;
  }

  void forgetRocket(RocketryRocket paramRocketryRocket)
  {
    for (int i = 0; i < this.rs.size(); i++)
      if ((RocketryRocket)this.rs.get(i) == paramRocketryRocket) {
        this.rs.remove(i);
        return;
      }
  }

  private void killWrongRockets(int paramInt)
  {
    for (int i = 0; i < this.rs.size(); i++) {
      int j = ((RocketryRocket)this.rs.get(i)).idR;
      int k = (byte)(j - paramInt);
      if ((k >= 0) && (k <= 20)) {
        ((RocketryRocket)this.rs.get(i)).silentDeath();
        this.rs.remove(i);
        i--;
      }
    }
  }

  private void sendRampDamage_Mirror(float paramFloat, Actor paramActor)
  {
    if (!isNetMirror()) {
      return;
    }

    if ((this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel() instanceof NetChannelInStream)) {
      return;
    }

    int i = (int)(paramFloat * 65000.0F);
    if (i <= 0) {
      return;
    }
    if (i > 65000) {
      i = 65000;
    }

    try
    {
      NetMsgFiltered localNetMsgFiltered = new NetMsgFiltered();
      localNetMsgFiltered.writeByte(45);
      localNetMsgFiltered.writeShort(i);
      localNetMsgFiltered.writeNetObj(paramActor == null ? null : paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
      localNetMsgFiltered.setIncludeTime(false);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(Time.current(), this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel(), localNetMsgFiltered);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void sendRespawn_Master()
  {
    if (!this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored()) {
      return;
    }

    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(83);
      localNetMsgGuaranted.writeByte(this.nextFreeIdR);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void prepareLaunch_Master(int paramInt)
  {
    if (this.countRockets <= 0) {
      this.countRockets = 0;
      this.actionTimeMS = 9223372036854775807L;
      return;
    }

    long l1 = Time.current();

    int i = RndI(0, 65535);
    int j = this.nextFreeIdR;
    this.nextFreeIdR = (this.nextFreeIdR + 1 & 0xFF);
    long l2 = l1 + 1000L * paramInt;

    this.actionTimeMS = (l2 + 1000L * this.launchIntervalS / 2L);

    killWrongRockets(j);

    TrajSeg[] arrayOfTrajSeg = computeNormalTrajectory(i);
    if (arrayOfTrajSeg == null)
    {
      return;
    }

    RocketryRocket localRocketryRocket = new RocketryRocket(this, this.meshNames.rocket, j, i, l2, l1, arrayOfTrajSeg);

    if (localRocketryRocket.isDamaged())
    {
      localRocketryRocket.silentDeath();
      return;
    }

    this.rs.add(localRocketryRocket);
    if (this.countRockets < 1000) {
      this.countRockets -= 1;
    }

    if (!this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored()) {
      return;
    }

    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(80);
      localNetMsgGuaranted.writeShort(paramInt);
      localNetMsgGuaranted.writeByte(j);
      localNetMsgGuaranted.writeShort(i);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void handleInitCommand_Mirror(boolean paramBoolean, int paramInt, RocketInGame[] paramArrayOfRocketInGame)
  {
    for (int i = 0; i < this.rs.size(); i++) {
      ((RocketryRocket)this.rs.get(i)).silentDeath();
    }
    this.rs.clear();

    this.damage = (paramBoolean ? 0.0F : 1.0F);

    setMesh(paramBoolean ? this.meshNames.ramp : this.meshNames.ramp_d);

    if (this.targetPos == null) {
      return;
    }

    long l = Time.current();
    for (int j = 0; j < paramArrayOfRocketInGame.length; j++) {
      TrajSeg[] arrayOfTrajSeg = computeNormalTrajectory(paramArrayOfRocketInGame[j].randseed);
      if (arrayOfTrajSeg == null)
      {
        continue;
      }
      RocketryRocket localRocketryRocket = new RocketryRocket(this, this.meshNames.rocket, paramArrayOfRocketInGame[j].idR, paramArrayOfRocketInGame[j].randseed, l - ()(1000.0D * paramArrayOfRocketInGame[j].timeAfterStartS), l, arrayOfTrajSeg);

      if (localRocketryRocket.isDamaged())
        localRocketryRocket.silentDeath();
      else
        this.rs.add(localRocketryRocket);
    }
  }

  private void handleRespawnCommand_Mirror(int paramInt)
  {
    killWrongRockets(paramInt);

    this.actionTimeMS = (Time.current() + 99999L);
    if (this.damage >= 1.0F) {
      this.damage = 0.0F;
      setMesh(this.meshNames.ramp);
      setDiedFlag(false);
    } else {
      this.damage = 0.0F;
    }
  }

  private void handlePrepareLaunchCommand_Mirror(int paramInt1, int paramInt2, int paramInt3)
  {
    killWrongRockets(paramInt2);

    if (this.targetPos == null) {
      return;
    }

    long l = Time.current();
    TrajSeg[] arrayOfTrajSeg = computeNormalTrajectory(paramInt3);
    if (arrayOfTrajSeg == null)
    {
      return;
    }

    RocketryRocket localRocketryRocket = new RocketryRocket(this, this.meshNames.rocket, paramInt2, paramInt3, l + paramInt1 * 1000, l, arrayOfTrajSeg);

    if (localRocketryRocket.isDamaged())
      localRocketryRocket.silentDeath();
    else
      this.rs.add(localRocketryRocket);
  }

  private void handleSyncLaunchCommand_Mirror(long paramLong, int paramInt)
  {
    killWrongRockets(paramInt + 1 & 0xFF);

    if (this.targetPos == null) {
      return;
    }

    RocketryRocket localRocketryRocket = findMyRocket(paramInt);
    if (localRocketryRocket != null)
      localRocketryRocket.changeLaunchTimeIfCan(paramLong);
  }

  public void sendRocketStateChange(RocketryRocket paramRocketryRocket, char paramChar, Actor paramActor)
  {
    boolean bool = isNetMaster();

    int i = RndI(0, 65535);

    if (bool) {
      handleRocketCommand_Both(paramChar, paramRocketryRocket.idR, i, paramActor, true);
      return;
    }

    if (!isNetMirror()) {
      return;
    }

    if ((this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel() instanceof NetChannelInStream)) {
      return;
    }

    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(Character.toLowerCase(paramChar));
      localNetMsgGuaranted.writeByte(paramRocketryRocket.idR);
      localNetMsgGuaranted.writeShort(i);
      localNetMsgGuaranted.writeNetObj(paramActor == null ? null : paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.masterChannel(), localNetMsgGuaranted);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void handleRocketCommand_Both(char paramChar, int paramInt1, int paramInt2, Actor paramActor, boolean paramBoolean)
  {
    if (this.targetPos == null) {
      return;
    }

    RocketryRocket localRocketryRocket = findMyRocket(paramInt1);
    if (localRocketryRocket == null) {
      return;
    }

    paramChar = localRocketryRocket.handleCommand(paramChar, paramInt2, paramActor);
    if ((paramChar == 0) || (!paramBoolean) || (!this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored())) {
      return;
    }

    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(Character.toUpperCase(paramChar));
      localNetMsgGuaranted.writeByte(paramInt1);
      localNetMsgGuaranted.writeShort(paramInt2);
      localNetMsgGuaranted.writeNetObj(paramActor == null ? null : paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post(localNetMsgGuaranted);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void handleDamageRamp_Both(float paramFloat, Actor paramActor, boolean paramBoolean)
  {
    if (this.prop.air) {
      return;
    }
    if (paramFloat <= 0.0F) {
      return;
    }

    if (this.damage >= 1.0F)
    {
      return;
    }

    this.damage += paramFloat;
    if (this.damage >= 1.0F)
      this.damage = 1.0F;
    else {
      return;
    }

    World.onActorDied(this, paramActor);

    setMesh(this.meshNames.ramp_d);
    this.actionTimeMS = Time.current();
    if (Mission.isDeathmatch()) {
      this.actionTimeMS += SecsToTicks(Mission.respawnTime("Artillery") * RndF(1.0F, 1.2F));
    }

    for (int i = 0; i < this.rs.size(); i++) {
      localObject = (RocketryRocket)this.rs.get(i);
      int j = ((RocketryRocket)localObject).idR;
      if (((RocketryRocket)localObject).isOnRamp()) {
        handleRocketCommand_Both('X', j, RndI(0, 65535), paramActor, paramBoolean);
        i = 0;
      }

    }

    Explosions.ExplodeBridge(this.begPos_wagon, this.endPos_wagon, 1.2F);

    if ((!paramBoolean) || (!this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.isMirrored())) {
      return;
    }

    Object localObject = new NetMsgGuaranted();
    try {
      ((NetMsgGuaranted)localObject).writeByte(68);
      ((NetMsgGuaranted)localObject).writeByte(this.nextFreeIdR);
      ((NetMsgGuaranted)localObject).writeShort(RndI(0, 65535));
      ((NetMsgGuaranted)localObject).writeNetObj(paramActor == null ? null : paramActor.jdField_net_of_type_ComMaddoxIl2EngineActorNet);
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.post((NetMsgGuaranted)localObject);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void createNetObject(NetChannel paramNetChannel, int paramInt)
  {
    if (paramNetChannel == null)
    {
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new Master(this);
    }
    else
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new Mirror(this, paramNetChannel, paramInt);
  }

  public void netFirstUpdate(NetChannel paramNetChannel)
    throws IOException
  {
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();

    localNetMsgGuaranted.writeByte(73);

    localNetMsgGuaranted.writeByte(this.damage >= 1.0F ? 0 : 1);
    localNetMsgGuaranted.writeByte(this.nextFreeIdR);

    int i = 0;
    for (int j = 0; j < this.rs.size(); j++) {
      RocketryRocket localRocketryRocket1 = (RocketryRocket)this.rs.get(j);
      if (!localRocketryRocket1.isDamaged()) {
        i++;
      }

    }

    if (i > 10) {
      i = 10;
    }
    localNetMsgGuaranted.writeByte(i);
    for (int k = this.rs.size() - 1; k >= 0; k--) {
      RocketryRocket localRocketryRocket2 = (RocketryRocket)this.rs.get(k);
      if (!localRocketryRocket2.isDamaged()) {
        localNetMsgGuaranted.writeByte(localRocketryRocket2.idR);
        float f = (float)((Time.current() - localRocketryRocket2.timeOfStartMS) * 0.001D);
        localNetMsgGuaranted.writeFloat(f);
        localNetMsgGuaranted.writeShort(localRocketryRocket2.randseed);
        i--; if (i <= 0) {
          break;
        }
      }
    }
    this.jdField_net_of_type_ComMaddoxIl2EngineActorNet.postTo(paramNetChannel, localNetMsgGuaranted);
  }

  public static RocketryGeneric New(String paramString1, String paramString2, NetChannel paramNetChannel, int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, float paramFloat1, float paramFloat2, int paramInt3, float paramFloat3, Point2d paramPoint2d)
  {
    RocketryProperties localRocketryProperties = (RocketryProperties)rocketryMap.get(paramString2);
    if (localRocketryProperties == null) {
      System.out.println("***** Rocketry: Unknown type [" + paramString2 + "]");
      return null;
    }

    MeshesNames localMeshesNames = null;
    switch (World.cur().camouflage) {
    case 1:
      localMeshesNames = localRocketryProperties.winterNames;
      break;
    case 2:
      localMeshesNames = localRocketryProperties.desertNames;
      break;
    default:
      localMeshesNames = localRocketryProperties.summerNames;
    }

    return new RocketryGeneric(localRocketryProperties, localMeshesNames, paramString1, paramInt2, paramNetChannel, paramInt1, paramDouble1, paramDouble2, paramFloat1, paramPoint2d, paramFloat2, paramFloat3, paramInt3);
  }

  public static final float KmHourToMSec(float paramFloat)
  {
    return paramFloat / 3.6F;
  }

  private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2)
  {
    float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
    if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
      if (f == -9865.3447F) {
        System.out.println("Rocketry: Parameter [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
      }
      else {
        System.out.println("Rocketry: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
      }

      throw new RuntimeException("Can't set property");
    }
    return f;
  }

  private static String getS(SectFile paramSectFile, String paramString1, String paramString2) {
    String str = paramSectFile.get(paramString1, paramString2);
    if ((str == null) || (str.length() <= 0)) {
      System.out.print("Rocketry: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");
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

  public static void PreLoad(String paramString)
  {
    Property.set(RocketryGeneric.class, "iconName", "icons/objV1.mat");

    rocketryMap = new HashMap();

    SectFile localSectFile = new SectFile(paramString, 0);
    int i = localSectFile.sections();

    for (int j = 0; j < i; j++) {
      RocketryProperties localRocketryProperties = new RocketryProperties();
      localRocketryProperties.name = localSectFile.sectionName(j);

      localRocketryProperties.summerNames.setNull();
      localRocketryProperties.desertNames.setNull();
      localRocketryProperties.winterNames.setNull();

      localRocketryProperties.summerNames.ramp = getS(localSectFile, localRocketryProperties.name, "MeshSummer_ramp", "");
      if (localRocketryProperties.summerNames.ramp == "")
      {
        localRocketryProperties.air = true;
        localRocketryProperties.summerNames.ramp = "3do/primitive/coord/mono.sim";
        localRocketryProperties.summerNames.ramp_d = localRocketryProperties.summerNames.ramp;
        localRocketryProperties.desertNames = localRocketryProperties.summerNames;
        localRocketryProperties.winterNames = localRocketryProperties.summerNames;
      }
      else {
        localRocketryProperties.air = false;
        localRocketryProperties.desertNames.ramp = getS(localSectFile, localRocketryProperties.name, "MeshDesert_ramp", localRocketryProperties.summerNames.ramp);
        localRocketryProperties.winterNames.ramp = getS(localSectFile, localRocketryProperties.name, "MeshWinter_ramp", localRocketryProperties.summerNames.ramp);
        localRocketryProperties.summerNames.ramp_d = getS(localSectFile, localRocketryProperties.name, "MeshSummerDamage_ramp");
        localRocketryProperties.desertNames.ramp_d = getS(localSectFile, localRocketryProperties.name, "MeshDesertDamage_ramp", localRocketryProperties.summerNames.ramp_d);
        localRocketryProperties.winterNames.ramp_d = getS(localSectFile, localRocketryProperties.name, "MeshWinterDamage_ramp", localRocketryProperties.summerNames.ramp_d);

        localRocketryProperties.summerNames.wagon = getS(localSectFile, localRocketryProperties.name, "MeshSummer_wagon");
        localRocketryProperties.desertNames.wagon = getS(localSectFile, localRocketryProperties.name, "MeshDesert_wagon", localRocketryProperties.summerNames.wagon);
        localRocketryProperties.winterNames.wagon = getS(localSectFile, localRocketryProperties.name, "MeshWinter_wagon", localRocketryProperties.summerNames.wagon);
      }

      localRocketryProperties.summerNames.rocket = getS(localSectFile, localRocketryProperties.name, "MeshSummer_rocket");
      localRocketryProperties.desertNames.rocket = getS(localSectFile, localRocketryProperties.name, "MeshDesert_rocket", localRocketryProperties.summerNames.rocket);
      localRocketryProperties.winterNames.rocket = getS(localSectFile, localRocketryProperties.name, "MeshWinter_rocket", localRocketryProperties.summerNames.rocket);

      localRocketryProperties.soundName = getS(localSectFile, localRocketryProperties.name, "SoundMove");

      localRocketryProperties.MASS_FULL = getF(localSectFile, localRocketryProperties.name, "MassFull", 0.5F, 100000.0F);
      localRocketryProperties.MASS_TNT = getF(localSectFile, localRocketryProperties.name, "MassTNT", 0.0F, 10000000.0F);
      localRocketryProperties.EXPLOSION_RADIUS = getF(localSectFile, localRocketryProperties.name, "ExplosionRadius", 0.01F, 100000.0F);

      if (!localRocketryProperties.air) {
        localRocketryProperties.TAKEOFF_SPEED = getF(localSectFile, localRocketryProperties.name, "TakeoffSpeed", 1.0F, 3000.0F);
        localRocketryProperties.TAKEOFF_SPEED = KmHourToMSec(localRocketryProperties.TAKEOFF_SPEED);
      }
      localRocketryProperties.MAX_SPEED = getF(localSectFile, localRocketryProperties.name, "MaxSpeed", localRocketryProperties.TAKEOFF_SPEED, 3000.0F);
      localRocketryProperties.MAX_SPEED = KmHourToMSec(localRocketryProperties.MAX_SPEED);
      if (!localRocketryProperties.air) {
        localRocketryProperties.SPEEDUP_TIME = getF(localSectFile, localRocketryProperties.name, "SpeedupTime", 1.0F, 10000.0F);
      }
      localRocketryProperties.FLY_HEIGHT = getF(localSectFile, localRocketryProperties.name, "FlyHeight", 100.0F, 15000.0F);
      localRocketryProperties.HIT_ANGLE = getF(localSectFile, localRocketryProperties.name, "HitAngle", 5.0F, 89.0F);

      localRocketryProperties.MAX_ERR_HEIGHT = getF(localSectFile, localRocketryProperties.name, "MaxErrHeight", 0.0F, 2000.0F);
      localRocketryProperties.MAX_ERR_HIT_DISTANCE = getF(localSectFile, localRocketryProperties.name, "MaxErrHitDistance", 0.0F, 10000.0F);

      if ((!localRocketryProperties.air) && 
        (!localRocketryProperties.stre.read("Rocketry", localSectFile, null, localRocketryProperties.name))) {
        throw new RuntimeException("Can't register Rocketry data");
      }

      localRocketryProperties.DMG_WARHEAD_MM = getF(localSectFile, localRocketryProperties.name, "DmgWarhead_mm", 0.0F, 2000.0F);
      localRocketryProperties.DMG_WARHEAD_PROB = getF(localSectFile, localRocketryProperties.name, "DmgWarhead_prob", 0.0F, 1.0F);

      localRocketryProperties.DMG_FUEL_MM = getF(localSectFile, localRocketryProperties.name, "DmgFuel_mm", 0.0F, 2000.0F);
      localRocketryProperties.DMG_FUEL_PROB = getF(localSectFile, localRocketryProperties.name, "DmgFuel_prob", 0.0F, 1.0F);

      localRocketryProperties.DMG_ENGINE_MM = getF(localSectFile, localRocketryProperties.name, "DmgEngine_mm", 0.0F, 2000.0F);
      localRocketryProperties.DMG_ENGINE_PROB = getF(localSectFile, localRocketryProperties.name, "DmgEngine_prob", 0.0F, 1.0F);

      localRocketryProperties.DMG_WING_MM = getF(localSectFile, localRocketryProperties.name, "DmgWing_mm", 0.0F, 2000.0F);
      localRocketryProperties.DMG_WING_PROB = getF(localSectFile, localRocketryProperties.name, "DmgWing_prob", 0.0F, 1.0F);

      localRocketryProperties.DMG_WARHEAD_TNT = getF(localSectFile, localRocketryProperties.name, "DmgWarhead_tnt", 0.0F, 1000000.0F);
      localRocketryProperties.DMG_WING_TNT = getF(localSectFile, localRocketryProperties.name, "DmgWing_tnt", 0.0F, 1000000.0F);

      rocketryMap.put(localRocketryProperties.name, localRocketryProperties);
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      char c = (char)paramNetMsgInput.readByte();
      int i1;
      if (paramNetMsgInput.isGuaranted())
      {
        NetMsgGuaranted localNetMsgGuaranted1;
        int m;
        Object localObject;
        int n;
        int k;
        switch (c)
        {
        case 'a':
        case 'b':
        case 'e':
        case 'f':
        case 'l':
        case 'r':
        case 'x':
          if (!isMaster()) {
            localNetMsgGuaranted1 = new NetMsgGuaranted(paramNetMsgInput, 1);
            postTo(masterChannel(), localNetMsgGuaranted1);
          }
          return true;
        case 'A':
        case 'B':
        case 'D':
        case 'E':
        case 'F':
        case 'L':
        case 'R':
        case 'X':
          if (isMirrored()) {
            localNetMsgGuaranted1 = new NetMsgGuaranted(paramNetMsgInput, 1);
            post(localNetMsgGuaranted1);
          }

          int i = paramNetMsgInput.readUnsignedByte();
          m = paramNetMsgInput.readUnsignedShort();
          NetObj localNetObj = paramNetMsgInput.readNetObj();
          localObject = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
          if (c != 'D')
            RocketryGeneric.this.handleRocketCommand_Both(c, i, m, (Actor)localObject, false);
          else {
            RocketryGeneric.this.handleDamageRamp_Both(1.0F, (Actor)localObject, false);
          }

          return true;
        case 'P':
          if (isMirrored()) {
            NetMsgGuaranted localNetMsgGuaranted2 = new NetMsgGuaranted(paramNetMsgInput, 0);
            post(localNetMsgGuaranted2);
          }

          int j = paramNetMsgInput.readUnsignedShort();
          m = paramNetMsgInput.readUnsignedByte();
          n = paramNetMsgInput.readUnsignedShort();
          RocketryGeneric.this.handlePrepareLaunchCommand_Mirror(j, m, n);

          return true;
        case 'S':
          if (isMirrored()) {
            NetMsgGuaranted localNetMsgGuaranted3 = new NetMsgGuaranted(paramNetMsgInput, 0);
            post(localNetMsgGuaranted3);
          }

          k = paramNetMsgInput.readUnsignedByte();
          RocketryGeneric.this.handleRespawnCommand_Mirror(k);

          return true;
        case 'I':
          k = paramNetMsgInput.readByte() != 0 ? 1 : 0;
          m = paramNetMsgInput.readUnsignedByte();
          n = paramNetMsgInput.readUnsignedByte();
          if (n > 10) {
            n = 10;
          }
          localObject = new RocketryGeneric.RocketInGame[n];
          for (i1 = 0; i1 < n; i1++) {
            localObject[i1] = new RocketryGeneric.RocketInGame();
            localObject[i1].idR = paramNetMsgInput.readUnsignedByte();
            localObject[i1].timeAfterStartS = paramNetMsgInput.readFloat();
            localObject[i1].randseed = paramNetMsgInput.readUnsignedShort();
          }
          RocketryGeneric.this.handleInitCommand_Mirror(k, m, localObject);

          return true;
        case 'C':
        case 'G':
        case 'H':
        case 'J':
        case 'K':
        case 'M':
        case 'N':
        case 'O':
        case 'Q':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'Y':
        case 'Z':
        case '[':
        case '\\':
        case ']':
        case '^':
        case '_':
        case '`':
        case 'c':
        case 'd':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w': } return false;
      }

      switch (c)
      {
      case '-':
        if (!NetMissionTrack.isPlaying()) {
          this.out.unLockAndSet(paramNetMsgInput, 1);
          this.out.setIncludeTime(false);
          postRealTo(Message.currentRealTime(), RocketryGeneric.this.net.masterChannel(), this.out);
        }
        return true;
      case 'Y':
        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 0);
          this.out.setIncludeTime(true);
          postReal(Message.currentRealTime(), this.out);
        }

        long l1 = ()(paramNetMsgInput.readFloat() * 1000.0D);
        long l2 = Message.currentGameTime() + l1;
        i1 = paramNetMsgInput.readUnsignedByte();
        RocketryGeneric.this.handleSyncLaunchCommand_Mirror(l2, i1);

        return true;
      }
      return false;
    }

    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4)
    {
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
      char c = (char)paramNetMsgInput.readByte();
      if (paramNetMsgInput.isGuaranted()) {
        switch (c)
        {
        case 'a':
        case 'b':
        case 'e':
        case 'f':
        case 'l':
        case 'r':
        case 'x':
          int i = paramNetMsgInput.readUnsignedByte();
          int j = paramNetMsgInput.readUnsignedShort();
          localObject = paramNetMsgInput.readNetObj();
          Actor localActor = localObject == null ? null : ((ActorNet)localObject).actor();
          RocketryGeneric.this.handleRocketCommand_Both(c, i, j, localActor, true);

          return true;
        case 'c':
        case 'd':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w': } return false;
      }

      if (c != '-') {
        return false;
      }
      float f = paramNetMsgInput.readUnsignedShort() / 65000.0F;
      NetObj localNetObj = paramNetMsgInput.readNetObj();
      Object localObject = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
      RocketryGeneric.this.handleDamageRamp_Both(f, (Actor)localObject, true);
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
      if (Time.current() < RocketryGeneric.this.actionTimeMS) {
        return true;
      }

      if (RocketryGeneric.this.damage >= 1.0F) {
        if (!Mission.isDeathmatch()) {
          return false;
        }
        if (!RocketryGeneric.this.isNetMaster()) {
          RocketryGeneric.access$002(RocketryGeneric.this, Time.current() + 99999L);
          return true;
        }

        RocketryGeneric.access$102(RocketryGeneric.this, 0.0F);
        RocketryGeneric.access$002(RocketryGeneric.this, Time.current() + 1000L * RocketryGeneric.this.launchIntervalS / 2L);
        RocketryGeneric.this.setMesh(RocketryGeneric.this.meshNames.ramp);
        RocketryGeneric.this.setDiedFlag(false);
        RocketryGeneric.this.sendRespawn_Master();
        return true;
      }

      if (RocketryGeneric.this.isNetMaster()) {
        RocketryGeneric.this.prepareLaunch_Master(RocketryGeneric.this.launchIntervalS / 2);
      }
      else {
        RocketryGeneric.access$002(RocketryGeneric.this, Time.current() + 99999L);
      }
      return true;
    }
  }

  public static class RocketInGame
  {
    public int idR;
    public float timeAfterStartS;
    public int randseed;
  }

  public static class TrajSeg
  {
    public double t0;
    public double t;
    public Point3d pos0 = new Point3d();
    public Vector3d v0 = new Vector3d();
    public Vector3d a = new Vector3d();
  }

  public static class RocketryProperties
  {
    public String name = null;

    public RocketryGeneric.MeshesNames summerNames = new RocketryGeneric.MeshesNames();
    public RocketryGeneric.MeshesNames desertNames = new RocketryGeneric.MeshesNames();
    public RocketryGeneric.MeshesNames winterNames = new RocketryGeneric.MeshesNames();

    public String soundName = null;

    public boolean air = false;

    public float MASS_FULL = 200.0F;
    public float MASS_TNT = 100.0F;
    public float EXPLOSION_RADIUS = 500.0F;

    public float TAKEOFF_SPEED = 1.0F;
    public float MAX_SPEED = 1.0F;
    public float SPEEDUP_TIME = 1.0F;
    public float FLY_HEIGHT = 1.0F;
    public float HIT_ANGLE = 30.0F;

    public float MAX_ERR_HEIGHT = 0.0F;
    public float MAX_ERR_HIT_DISTANCE = 0.0F;

    public StrengthProperties stre = new StrengthProperties();

    public float DMG_WARHEAD_MM = 0.0F;
    public float DMG_WARHEAD_PROB = 0.0F;

    public float DMG_FUEL_MM = 0.0F;
    public float DMG_FUEL_PROB = 0.0F;

    public float DMG_ENGINE_MM = 0.0F;
    public float DMG_ENGINE_PROB = 0.0F;

    public float DMG_WING_MM = 0.0F;
    public float DMG_WING_PROB = 0.0F;

    public float DMG_WARHEAD_TNT = 0.0F;
    public float DMG_WING_TNT = 0.0F;
  }

  public static class MeshesNames
  {
    public String ramp;
    public String ramp_d;
    public String wagon;
    public String rocket;

    public void setNull()
    {
      this.ramp = null;
      this.ramp_d = null;
      this.wagon = null;
      this.rocket = null;
    }
  }
}