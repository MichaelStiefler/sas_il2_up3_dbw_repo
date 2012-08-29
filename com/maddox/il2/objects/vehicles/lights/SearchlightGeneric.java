package com.maddox.il2.objects.vehicles.lights;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.AnglesForkExtended;
import com.maddox.il2.ai.AnglesRange;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.TableFunctions;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Aim;
import com.maddox.il2.ai.ground.HunterInterface;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.effects.LightsGlare;
import com.maddox.il2.objects.vehicles.aeronautics.Balloon;
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
import com.maddox.util.HashMapExt;
import com.maddox.util.TableFunction2;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map.Entry;

public abstract class SearchlightGeneric extends ActorHMesh
  implements MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener, Predator, Obstacle, ActorAlign, HunterInterface
{
  private static final int TM_LOOSE_IN_DARK = 1000;
  private static final int TM_TO_REMEMBER_BAD_TARGET = 22000;
  private static final float SUNZ_MIN_TO_WORK = -0.22F;
  private SearchlightProperties prop = null;
  private float heightAboveLandSurface;
  private Aim aime;
  private float headYaw;
  private float gunPitch;
  private long startDelay;
  private long lastTimeWhenFound;
  private boolean smoothMove;
  private boolean lightWantedState;
  private boolean lightIsOn;
  private boolean nightTime;
  private LightPointActor landLight;
  private LightPoint cloudLight = new LightPoint();

  private int dying = 0;
  static final int DYING_NONE = 0;
  static final int DYING_DEAD = 1;
  private short deathSeed;
  private long respawnDelay = 0L;

  private Point3d B = new Point3d();
  private Vector3d L = new Vector3d();

  private static SearchlightProperties constr_arg1 = null;
  private static ActorSpawnArg constr_arg2 = null;

  private static Point3d p3d = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();
  private static Vector3d tmpv = new Vector3d();

  private static Loc locLloc = new Loc();
  private static Point3d locLpos = new Point3d();
  private static Matrix4d locLmatr = new Matrix4d();

  private static boolean someObjectWasLightedInPreviousCall = true;

  private static HashMapExt hashmap_ON = new HashMapExt();

  private static HashMapExt hashmap_ALL = new HashMapExt();
  private static boolean hashmap_ALL_is_changed = true;
  private static Map.Entry next_entry_get = null;

  private static Point3f accumColor = new Point3f();
  private static Vector3d accumDir = new Vector3d();
  private static Point3d planeP = new Point3d();

  public static Point3d nosePos = new Point3d();
  private static Vector3d noseDir = new Vector3d();
  private static Vector3d targRayDir = new Vector3d();
  private static Point3d targetPos = new Point3d();
  private static Loc Cam2WorldLoc = new Loc();

  private NetMsgFiltered outCommand = new NetMsgFiltered();

  public static void resetGame()
  {
    hashmap_ON.clear();
    hashmap_ALL.clear();
    hashmap_ALL_is_changed = true;
    someObjectWasLightedInPreviousCall = true;
    next_entry_get = null;
  }

  private static void register_ONOFF(SearchlightGeneric paramSearchlightGeneric, boolean paramBoolean) {
    if (paramBoolean)
      hashmap_ON.put(paramSearchlightGeneric, paramSearchlightGeneric);
    else {
      hashmap_ON.remove(paramSearchlightGeneric);
    }
    if (!hashmap_ALL.containsKey(paramSearchlightGeneric)) {
      hashmap_ALL.put(paramSearchlightGeneric, paramSearchlightGeneric);
      hashmap_ALL_is_changed = true;
    }
  }

  public static int possibleGlare()
  {
    if ((!hashmap_ALL.isEmpty()) && (World.Sun().ToSun.z <= -0.22F)) {
      if (hashmap_ALL_is_changed) {
        hashmap_ALL_is_changed = false;
        return 2;
      }
      return 1;
    }
    return 0;
  }

  public static int numlightsGlare() {
    return hashmap_ALL.size();
  }

  private static float computeAngleFromCam(Point3d paramPoint3d)
  {
    targRayDir.sub(paramPoint3d, nosePos);
    double d1 = targRayDir.length();
    if (d1 < 0.001D) {
      return -1.0F;
    }
    targRayDir.scale(1.0D / d1);

    double d2 = noseDir.dot(targRayDir);
    if (d2 < 0.01D) {
      return -1.0F;
    }
    return Geom.RAD2DEG((float)Math.acos(d2));
  }

  public static boolean computeGlare(LightsGlare paramLightsGlare, Point3d paramPoint3d)
  {
    com.maddox.il2.game.Main3D.cur3D()._camera3D[com.maddox.il2.game.Main3D.cur3D().getRenderIndx()].pos.getRender(Cam2WorldLoc);
    Cam2WorldLoc.get(nosePos);
    noseDir.set(1.0D, 0.0D, 0.0D);
    Cam2WorldLoc.transform(noseDir);

    Map.Entry localEntry = hashmap_ALL.nextEntry(null);
    int i = hashmap_ALL.size() * 3;

    for (int j = 0; j < i; j += 3) {
      SearchlightGeneric localSearchlightGeneric = (SearchlightGeneric)localEntry.getKey();
      localEntry = hashmap_ALL.nextEntry(localEntry);

      if (!localSearchlightGeneric.lightIsOn) {
        float f1 = computeAngleFromCam(localSearchlightGeneric.B);
        paramLightsGlare.glareData[j] = (f1 >= 0.0F ? 0.0F : -1.0F);
        paramLightsGlare.glareData[(j + 1)] = f1;
      }
      else
      {
        double d1 = localSearchlightGeneric.L.dot(nosePos) - localSearchlightGeneric.L.dot(localSearchlightGeneric.B);
        if ((d1 <= 0.0D) || (d1 >= localSearchlightGeneric.prop.H))
        {
          float f2 = computeAngleFromCam(localSearchlightGeneric.B);
          paramLightsGlare.glareData[j] = (f2 >= 0.0F ? 0.0F : -1.0F);
          paramLightsGlare.glareData[(j + 1)] = f2;
        }
        else
        {
          locLpos.scaleAdd(d1, localSearchlightGeneric.L, localSearchlightGeneric.B);

          d1 /= localSearchlightGeneric.prop.H;
          double d2 = localSearchlightGeneric.prop.R0 + (localSearchlightGeneric.prop.R1 - localSearchlightGeneric.prop.R0) * d1;

          tmpv.sub(nosePos, locLpos);
          if (tmpv.lengthSquared() >= d2 * d2)
          {
            float f3 = computeAngleFromCam(localSearchlightGeneric.B);
            paramLightsGlare.glareData[j] = (f3 >= 0.0F ? 0.0F : -1.0F);
            paramLightsGlare.glareData[(j + 1)] = f3;
          }
          else
          {
            double d3 = (1.0D - d1) * localSearchlightGeneric.prop.LIGHT_LAND_I;
            float f4;
            if (d3 <= 0.0D) {
              f4 = computeAngleFromCam(localSearchlightGeneric.B);
              paramLightsGlare.glareData[j] = (f4 >= 0.0F ? 0.0F : -1.0F);
              paramLightsGlare.glareData[(j + 1)] = f4;
            }
            else
            {
              f4 = paramLightsGlare.computeFlash(localSearchlightGeneric, localSearchlightGeneric.B, accumColor);
              if (f4 > 0.0F) {
                f4 = (float)(f4 * d3);
              }
              paramLightsGlare.glareData[j] = f4;
              paramLightsGlare.glareData[(j + 1)] = accumColor.x;
            }
          }
        }
      }
    }
    return true;
  }

  public static void getnextposandcolorGlare(Point3d paramPoint3d, Point3f paramPoint3f) {
    if (paramPoint3d == null) {
      next_entry_get = hashmap_ALL.nextEntry(null);
      return;
    }
    SearchlightGeneric localSearchlightGeneric = (SearchlightGeneric)next_entry_get.getKey();
    next_entry_get = hashmap_ALL.nextEntry(next_entry_get);
    paramPoint3d.set(localSearchlightGeneric.B);
    paramPoint3f.set(localSearchlightGeneric.prop.LIGHT_COLOR);
  }

  private static void clearSearchlightSourcesInObjects()
  {
    List localList = Engine.targets();
    int i = localList.size();

    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)localList.get(j);

      if ((!(localActor instanceof Aircraft)) && (!(localActor instanceof Balloon)))
      {
        continue;
      }
      if (localActor.draw == null)
      {
        continue;
      }
      HashMapExt localHashMapExt = localActor.draw.lightMap();
      if (localHashMapExt == null)
      {
        continue;
      }
      LightPointActor localLightPointActor = (LightPointActor)localHashMapExt.remove("SL");
      if (localLightPointActor != null)
        localLightPointActor.destroy();
    }
  }

  public static void lightPlanesBySearchlights()
  {
    if (hashmap_ON.isEmpty()) {
      if (someObjectWasLightedInPreviousCall) {
        clearSearchlightSourcesInObjects();
        someObjectWasLightedInPreviousCall = false;
      }
      return;
    }

    List localList = Engine.targets();
    int i = localList.size();

    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)localList.get(j);

      if ((!(localActor instanceof Aircraft)) && (!(localActor instanceof Balloon)))
      {
        continue;
      }
      localActor.pos.getRender(planeP);

      double d1 = 0.0D;

      if (planeP.z - World.land().HQ(planeP.x, planeP.y) > 10.0D)
      {
        int k = 0;
        accumColor.set(0.0F, 0.0F, 0.0F);
        accumDir.set(0.0D, 0.0D, 0.0D);

        localObject = hashmap_ON.nextEntry(null);
        while (localObject != null) {
          SearchlightGeneric localSearchlightGeneric = (SearchlightGeneric)((Map.Entry)localObject).getKey();

          double d2 = localSearchlightGeneric.L.dot(planeP) - localSearchlightGeneric.L.dot(localSearchlightGeneric.B);
          if ((d2 <= 0.0D) || (d2 >= localSearchlightGeneric.prop.H))
          {
            localObject = hashmap_ON.nextEntry((Map.Entry)localObject);
            continue;
          }

          locLpos.scaleAdd(d2, localSearchlightGeneric.L, localSearchlightGeneric.B);

          d2 /= localSearchlightGeneric.prop.H;
          double d3 = localSearchlightGeneric.prop.R0 + (localSearchlightGeneric.prop.R1 - localSearchlightGeneric.prop.R0) * d2;
          double d4 = d3 + localActor.collisionR() * 0.75D;

          tmpv.sub(planeP, locLpos);
          double d5 = tmpv.lengthSquared();
          if (d5 >= d4 * d4)
          {
            localObject = hashmap_ON.nextEntry((Map.Entry)localObject);
            continue;
          }

          if (d4 - d3 <= 0.0D) {
            localObject = hashmap_ON.nextEntry((Map.Entry)localObject);
            continue;
          }
          d5 = Math.sqrt(d5);
          double d6 = 1.0D - (d5 - d3) / (d4 - d3);
          if (d6 >= 1.0D) {
            d6 = 1.0D;
          }

          double d7 = (1.0D - d2) * localSearchlightGeneric.prop.LIGHT_LAND_I;
          d7 *= d6;

          if (d7 <= 0.0D) {
            localObject = hashmap_ON.nextEntry((Map.Entry)localObject);
            continue;
          }

          d1 += d7;
          accumColor.scaleAdd((float)d7, localSearchlightGeneric.prop.LIGHT_COLOR);
          accumDir.scaleAdd(d7, localSearchlightGeneric.L);

          localObject = hashmap_ON.nextEntry((Map.Entry)localObject);
        }
      }

      if (localActor.draw == null)
      {
        continue;
      }
      HashMapExt localHashMapExt = localActor.draw.lightMap();

      if (localHashMapExt == null)
      {
        continue;
      }
      Object localObject = (LightPointActor)localHashMapExt.get("SL");

      if (d1 <= 0.0D) {
        if (localObject != null) {
          ((LightPointActor)localObject).light.setEmit(0.0F, 0.0F);
        }
      }
      else
      {
        someObjectWasLightedInPreviousCall = true;

        if (localObject == null) {
          localObject = new LightPointActor(new LightPoint());
          localHashMapExt.put("SL", localObject);
        }

        accumDir.normalize();
        accumDir.negate();
        float f1 = 100123.0F;
        accumDir.scale(1000.0D);
        localActor.pos.getRender(locLloc);
        locLloc.transformInv(accumDir);

        float f2 = Math.max(accumColor.x, Math.max(accumColor.y, accumColor.z));
        accumColor.scale(1.0F / f2);

        if (d1 >= 1.350000023841858D) {
          d1 = 1.350000023841858D;
        }

        ((LightPointActor)localObject).light.setEmit((float)d1, f1);
        ((LightPointActor)localObject).light.setColor(accumColor.x, accumColor.y, accumColor.z);
        ((LightPointActor)localObject).relPos.set(accumDir);
      }
    }
  }

  public static void lightCloudsBySearchlights() {
    if (hashmap_ON.isEmpty()) {
      return;
    }

    int i = Mission.curCloudsType();
    if (i <= 0)
    {
      return;
    }

    double d1 = Mission.curCloudsHeight();

    Map.Entry localEntry = hashmap_ON.nextEntry(null);
    while (localEntry != null) {
      SearchlightGeneric localSearchlightGeneric = (SearchlightGeneric)localEntry.getKey();
      localEntry = hashmap_ON.nextEntry(localEntry);

      if (localSearchlightGeneric.L.z <= 0.01D)
      {
        continue;
      }
      double d2 = (d1 - localSearchlightGeneric.B.z) / localSearchlightGeneric.L.z;
      if ((d2 < 0.0D) || 
        (d2 >= localSearchlightGeneric.prop.H))
      {
        continue;
      }

      p3d.scaleAdd(d2, localSearchlightGeneric.L, localSearchlightGeneric.B);

      d2 /= localSearchlightGeneric.prop.H;

      double d3 = localSearchlightGeneric.prop.R0 + (localSearchlightGeneric.prop.R1 - localSearchlightGeneric.prop.R0) * d2;
      d3 *= 5.0D;
      if (d3 <= 100.0D) {
        d3 = 100.0D;
      }
      if (d3 >= 400.0D) {
        d3 = 400.0D;
      }

      double d4 = 3.0F * localSearchlightGeneric.prop.LIGHT_LAND_I;
      d4 *= (1.0D - d2);
      if (d4 <= 0.0D)
      {
        continue;
      }
      localSearchlightGeneric.cloudLight.setPos(p3d);
      localSearchlightGeneric.cloudLight.setEmit((float)d4, (float)d3);
      localSearchlightGeneric.cloudLight.addToRender();
    }
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

  protected final boolean Head360()
  {
    return this.prop.HEAD_YAW_RANGE.fullcircle();
  }

  private void activateMesh(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (!paramBoolean1) {
      hierMesh().chunkVisible("Body", false);
      hierMesh().chunkVisible("Head", false);
      hierMesh().chunkVisible("Gun", false);
      hierMesh().chunkVisible("Ray_ON", false);
      hierMesh().chunkVisible("Ray_OFF", false);

      hierMesh().chunkVisible("Dead", true);

      this.lightIsOn = false;
      this.lightWantedState = false;

      if (this.landLight != null) {
        this.landLight.destroy();
        this.landLight = null;
      }
      ((MyDrawer)this.draw).killLightMap();

      register_ONOFF(this, false);

      return;
    }

    hierMesh().chunkVisible("Body", true);
    hierMesh().chunkVisible("Head", true);
    hierMesh().chunkVisible("Gun", true);
    hierMesh().chunkVisible("Ray_ON", paramBoolean2);
    hierMesh().chunkVisible("Ray_OFF", !paramBoolean2);

    hierMesh().chunkVisible("Dead", false);

    this.lightIsOn = paramBoolean2;
    this.lightWantedState = paramBoolean2;

    if (this.landLight != null) {
      this.landLight.destroy();
      this.landLight = null;
    }
    ((MyDrawer)this.draw).killLightMap();

    if (paramBoolean2) {
      this.landLight = new LightPointActor(new LightPointWorld(), new Point3d(0.0D, 0.0D, 0.0D));
      this.landLight.light.setColor(this.prop.LIGHT_COLOR.x, this.prop.LIGHT_COLOR.y, this.prop.LIGHT_COLOR.z);
      this.landLight.light.setEmit(this.prop.LIGHT_LAND_I, this.prop.LIGHT_LAND_R);
      this.draw.lightMap().put("light", this.landLight);

      this.cloudLight.setColor(this.prop.LIGHT_COLOR.x, this.prop.LIGHT_COLOR.y, this.prop.LIGHT_COLOR.z);
    }

    register_ONOFF(this, paramBoolean2);
  }

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if (((paramActor instanceof ActorMesh)) && (((ActorMesh)paramActor).isStaticPos())) {
      paramArrayOfBoolean[0] = false;
      return;
    }
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = 2;

    if (this.dying != 0) {
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

      Die(paramShot.initiator, 0, true);
      return;
    }

    float f1 = this.prop.PANZER;
    f1 *= Rnd(0.93F, 1.07F);

    float f2 = this.prop.fnShotPanzer.Value(paramShot.power, f1);

    if ((f2 < 1000.0F) && ((f2 <= 1.0F) || (RndB(1.0F / f2))))
      Die(paramShot.initiator, 0, true);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (this.dying != 0) {
      return;
    }

    if ((isNetMirror()) && (paramExplosion.isMirage())) {
      return;
    }

    if (paramExplosion.power <= 0.0F) {
      return;
    }

    if (paramExplosion.powerType == 1) {
      if (TankGeneric.splintersKill(paramExplosion, this.prop.fnShotPanzer, 0.5F, 0.5F, this, 0.7F, 0.25F, this.prop.PANZER, this.prop.PANZER, this.prop.PANZER, this.prop.PANZER, this.prop.PANZER, this.prop.PANZER))
      {
        Die(paramExplosion.initiator, 0, true);
      }
      return;
    }

    if ((paramExplosion.powerType == 2) && (paramExplosion.chunkName != null)) {
      Die(paramExplosion.initiator, 0, true);
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
      Die(paramExplosion.initiator, 0, true);
  }

  private void ShowExplode(float paramFloat)
  {
    if (paramFloat > 0.0F) {
      paramFloat = Rnd(paramFloat, paramFloat * 1.6F);
    }
    Explosions.runByName(this.prop.explodeName, this, "", "", paramFloat);
  }

  private float[] computeDeathPose(short paramShort)
  {
    RangeRandom localRangeRandom = new RangeRandom(paramShort);

    float[] arrayOfFloat = new float[10];

    arrayOfFloat[0] = (this.headYaw + localRangeRandom.nextFloat(-15.0F, 15.0F));
    arrayOfFloat[1] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(4.0F, 9.0F));
    arrayOfFloat[2] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(4.0F, 9.0F));

    arrayOfFloat[3] = (-this.gunPitch + localRangeRandom.nextFloat(-15.0F, 15.0F));
    arrayOfFloat[4] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(2.0F, 5.0F));
    arrayOfFloat[5] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(5.0F, 9.0F));

    arrayOfFloat[6] = 0.0F;
    arrayOfFloat[7] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(4.0F, 8.0F));
    arrayOfFloat[8] = ((localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(7.0F, 12.0F));

    arrayOfFloat[9] = (this.heightAboveLandSurface - localRangeRandom.nextFloat(0.0F, 0.25F));

    return arrayOfFloat;
  }

  private void Die(Actor paramActor, short paramShort, boolean paramBoolean) {
    if (this.dying != 0) {
      return;
    }

    if (paramShort <= 0) {
      if (isNetMirror()) {
        send_DeathRequest(paramActor);
        return;
      }

      paramShort = (short)(int)Rnd(1.0F, 30000.0F);
    }
    this.deathSeed = paramShort;

    this.dying = 1;

    World.onActorDied(this, paramActor);

    if (this.aime != null) {
      this.aime.forgetAiming();
    }

    float[] arrayOfFloat = computeDeathPose(paramShort);

    hierMesh().chunkSetAngles("Head", arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2]);
    hierMesh().chunkSetAngles("Gun", arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
    hierMesh().chunkSetAngles("Body", arrayOfFloat[6], arrayOfFloat[7], arrayOfFloat[8]);
    this.heightAboveLandSurface = arrayOfFloat[9];
    ((MyDrawer)this.draw).interpolateAngles = false;

    Align();

    activateMesh(false, false);
    if (paramBoolean) {
      ShowExplode(14.0F);
    }

    if (paramBoolean)
      send_DeathCommand(paramActor);
  }

  private void setGunAngles(float paramFloat1, float paramFloat2)
  {
    this.headYaw = paramFloat1;
    this.gunPitch = paramFloat2;
    hierMesh().chunkSetAngles("Head", this.headYaw, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("Gun", -this.gunPitch, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("Body", 0.0F, 0.0F, 0.0F);
    ((MyDrawer)this.draw).pushAngles(this.headYaw, -this.gunPitch);

    hierMesh().setCurChunk("Ray_ON");
    hierMesh().getChunkLocObj(locLloc);
    locLloc.add(this.pos.getAbs());
    this.L.set(1.0D, 0.0D, 0.0D);
    locLloc.transform(this.L);
    this.L.normalize();
    locLloc.get(this.B);
  }

  public void destroy() {
    if (isDestroyed())
      return;
    if (this.aime != null) {
      this.aime.forgetAll();
      this.aime = null;
    }
    super.destroy();
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public boolean isStaticPos() {
    return false;
  }

  private void setDefaultLivePose()
  {
    Matrix4d localMatrix4d = new Matrix4d();

    this.heightAboveLandSurface = 0.0F;
    int i = hierMesh().hookFind("Ground_Level");
    if (i != -1) {
      hierMesh().hookMatrix(i, localMatrix4d);
      this.heightAboveLandSurface = (float)(-localMatrix4d.m23);
    }

    Point3d localPoint3d1 = new Point3d();
    hierMesh().hookMatrix(hierMesh().hookFind("ConeNear"), localMatrix4d);
    localPoint3d1.x = localMatrix4d.m03;
    localPoint3d1.y = localMatrix4d.m13;
    localPoint3d1.z = localMatrix4d.m23;

    Point3d localPoint3d2 = new Point3d();
    hierMesh().hookMatrix(hierMesh().hookFind("ConeFar"), localMatrix4d);
    localPoint3d2.x = localMatrix4d.m03;
    localPoint3d2.y = localMatrix4d.m13;
    localPoint3d2.z = localMatrix4d.m23;

    hierMesh().setCurChunk("Ray_ON");
    hierMesh().getChunkLocObj(locLloc);
    locLloc.transformInv(localPoint3d1);
    locLloc.transformInv(localPoint3d2);

    if (Math.abs(localPoint3d1.x) > 0.1D) {
      System.out.println("**** Wrong position or orientation of ConeNear");
    }

    this.prop.R0 = Math.sqrt(localPoint3d1.y * localPoint3d1.y + localPoint3d1.z * localPoint3d1.z);
    this.prop.R1 = Math.sqrt(localPoint3d2.y * localPoint3d2.y + localPoint3d2.z * localPoint3d2.z);
    if ((localPoint3d2.x - localPoint3d1.x < 0.1D) || (this.prop.R1 < this.prop.R0)) {
      System.out.println("**** Wrong position or orientation of ConeFar");
    }

    this.prop.TANGA = ((this.prop.R1 - this.prop.R0) / (localPoint3d2.x - localPoint3d1.x));

    float f = 1.0F;

    int j = Mission.curCloudsType();
    switch (j) { case 1:
      f = 1.0F; break;
    case 2:
      f = 0.6F; break;
    case 3:
      f = 0.3F; break;
    case 4:
      f = 0.12F; break;
    case 5:
      f = 0.12F; break;
    case 6:
      f = 0.12F;
    }

    this.prop.H = (this.prop.Hclear * f);
    this.prop.R1 = (this.prop.R0 + this.prop.H * this.prop.TANGA);

    setGunAngles(0.0F, this.prop.GUN_STD_PITCH);
    ((MyDrawer)this.draw).interpolateAngles = false;

    Align();
  }

  protected SearchlightGeneric()
  {
    this(constr_arg1, constr_arg2);
  }

  private SearchlightGeneric(SearchlightProperties paramSearchlightProperties, ActorSpawnArg paramActorSpawnArg)
  {
    super(paramSearchlightProperties.meshName);
    this.prop = paramSearchlightProperties;

    paramActorSpawnArg.setStationary(this);

    collide(true);
    drawing(true);

    this.draw = new MyDrawer();

    this.lastTimeWhenFound = 0L;
    this.smoothMove = false;

    createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);

    this.startDelay = 0L;
    if (paramActorSpawnArg.timeLenExist) {
      this.startDelay = ()(paramActorSpawnArg.timeLen * 60.0F * 1000.0F + 0.5F);
      if (this.startDelay < 0L) this.startDelay = 0L;
    }

    this.headYaw = 0.0F;
    this.gunPitch = 0.0F;

    activateMesh(true, false);

    setDefaultLivePose();

    if (Time.isRealOnly()) {
      this.flags |= 8192;
      if (!interpEnd("tick_builder")) {
        this.aime = null;
        interpPut(new TickBuilder(), "tick_builder", Time.currentReal(), null);
      }
    } else {
      startMove();
    }
  }

  private void Align()
  {
    this.pos.getAbs(p3d);
    p3d.z = (Engine.land().HQ(p3d.x, p3d.y) + this.heightAboveLandSurface);
    o.setYPR(this.pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
    this.pos.setAbs(p3d, o);
  }

  public void align()
  {
    Align();
  }

  public void startMove()
  {
    if (!interpEnd("tick_game")) {
      if (this.aime != null) {
        this.aime.forgetAll();
        this.aime = null;
      }
      this.aime = new Aim(this, isNetMirror());
      interpPut(new TickGame(), "tick_game", Time.current(), null);
    }
  }

  public int WeaponsMask()
  {
    return -1;
  }

  public int HitbyMask() {
    return this.prop.HITBY_MASK;
  }

  public int chooseBulletType(BulletProperties[] paramArrayOfBulletProperties)
  {
    if (this.dying != 0) {
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
    if (this.dying != 0) {
      return -1;
    }
    return 0;
  }

  public boolean getShotpointOffset(int paramInt, Point3d paramPoint3d) {
    if (this.dying != 0) {
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

  public float AttackMaxDistance()
  {
    return (float)this.prop.H;
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

  private void send_DeathCommand(Actor paramActor)
  {
    if (!isNetMaster()) {
      return;
    }

    if (Mission.isDeathmatch()) {
      float f = Mission.respawnTime("Searchlight");
      this.respawnDelay = SecsToTicks(Rnd(f, f * 1.2F));
    } else {
      this.respawnDelay = 0L;
    }

    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(68);
      localNetMsgGuaranted.writeShort(this.deathSeed);
      localNetMsgGuaranted.writeFloat(this.headYaw);
      localNetMsgGuaranted.writeFloat(this.gunPitch);
      localNetMsgGuaranted.writeNetObj(paramActor == null ? null : paramActor.net);
      this.net.post(localNetMsgGuaranted);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void send_RespawnCommand()
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

  private void send_FireCommand(Actor paramActor, float paramFloat)
  {
    if (!isNetMaster()) {
      return;
    }
    if (!this.net.isMirrored()) {
      return;
    }
    if ((!Actor.isValid(paramActor)) || (!paramActor.isNet())) {
      return;
    }

    if (paramFloat < 0.0F)
      try {
        this.outCommand.unLockAndClear();
        this.outCommand.writeByte(84);
        this.outCommand.writeNetObj(paramActor.net);
        this.outCommand.setIncludeTime(false);
        this.net.post(Time.current(), this.outCommand);
      } catch (Exception localException1) {
        System.out.println(localException1.getMessage());
        localException1.printStackTrace();
      }
    else
      try {
        this.outCommand.unLockAndClear();
        this.outCommand.writeByte(70);
        this.outCommand.writeFloat(paramFloat);
        this.outCommand.writeNetObj(paramActor.net);
        this.outCommand.setIncludeTime(true);
        this.net.post(Time.current(), this.outCommand);
      } catch (Exception localException2) {
        System.out.println(localException2.getMessage());
        localException2.printStackTrace();
      }
  }

  private void send_DeathRequest(Actor paramActor)
  {
    if (!isNetMirror()) {
      return;
    }

    if ((this.net.masterChannel() instanceof NetChannelInStream))
      return;
    try
    {
      NetMsgFiltered localNetMsgFiltered = new NetMsgFiltered();
      localNetMsgFiltered.writeByte(68);
      localNetMsgFiltered.writeNetObj(paramActor == null ? null : paramActor.net);
      localNetMsgFiltered.setIncludeTime(false);
      this.net.postTo(Time.current(), this.net.masterChannel(), localNetMsgFiltered);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
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

  public void netFirstUpdate(NetChannel paramNetChannel)
    throws IOException
  {
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    localNetMsgGuaranted.writeByte(73);
    if (this.dying == 0)
      localNetMsgGuaranted.writeShort(0);
    else {
      localNetMsgGuaranted.writeShort(this.deathSeed);
    }
    localNetMsgGuaranted.writeFloat(this.headYaw);
    localNetMsgGuaranted.writeFloat(this.gunPitch);
    this.net.postTo(paramNetChannel, localNetMsgGuaranted);
  }

  public float getReloadingTime(Aim paramAim)
  {
    return 0.0F;
  }

  public float chainFireTime(Aim paramAim)
  {
    return 3.0F;
  }

  public float probabKeepSameEnemy(Actor paramActor)
  {
    return Time.current() - this.lastTimeWhenFound <= 22000L ? 1.0F : 0.0F;
  }

  public float minTimeRelaxAfterFight()
  {
    return 0.0F;
  }

  public void gunStartParking(Aim paramAim)
  {
    this.lightWantedState = false;

    paramAim.setRotationForParking(this.headYaw, this.gunPitch, 0.0F, this.prop.GUN_STD_PITCH, this.prop.HEAD_YAW_RANGE, this.prop.HEAD_MAX_YAW_SPEED, this.prop.GUN_MAX_PITCH_SPEED);
  }

  public void gunInMove(boolean paramBoolean, Aim paramAim)
  {
    float f1 = paramAim.t();

    if ((!paramBoolean) && (this.smoothMove))
    {
      f1 = 0.5F * (1.0F + Geom.sinDeg(f1 * 180.0F - 90.0F));
    }

    this.lightWantedState = (!paramBoolean);

    float f2 = paramAim.anglesYaw.getDeg(f1);
    float f3 = paramAim.anglesPitch.getDeg(f1);

    setGunAngles(f2, f3);
    this.pos.inValidate(false);

    Actor localActor = paramAim.getEnemy();

    if ((Actor.isValid(localActor)) && (localActor.isAlive()) && (localActor.getArmy() != 0))
    {
      Point3d localPoint3d1 = new Point3d();
      localActor.pos.getAbs(localPoint3d1);

      Point3d localPoint3d2 = new Point3d();
      this.pos.getAbs(localPoint3d2);

      Vector3d localVector3d1 = new Vector3d();
      localVector3d1.sub(localPoint3d1, localPoint3d2);

      Orient localOrient = new Orient();
      localOrient.setYPR(this.pos.getAbsOrient().getYaw() + f2, f3, 0.0F);

      Vector3d localVector3d2 = new Vector3d(1.0D, 0.0D, 0.0D);
      localOrient.transform(localVector3d2);

      double d1 = localVector3d1.length();

      double d2 = localVector3d2.dot(localVector3d1);
      if (d2 > 0.0D) {
        double d3 = Math.sqrt(d1 * d1 - d2 * d2);

        float f4 = (float)(this.prop.R0 + this.prop.TANGA * d1);

        float f5 = localActor.collisionR();
        float f6 = (float)(d3 - f5 - f4);

        if (f6 <= 0.0F)
        {
          this.lastTimeWhenFound = Time.current();
          ((Aircraft)localActor).tmSearchlighted = this.lastTimeWhenFound;
        }
      }
    }
  }

  public Actor findEnemy(Aim paramAim)
  {
    if ((!this.nightTime) || (this.dying == 1)) {
      return null;
    }

    if (isNetMirror()) {
      return null;
    }

    if (Time.current() < this.startDelay) {
      return null;
    }

    this.lastTimeWhenFound = Time.current();

    Actor localActor = null;

    NearestEnemies.set(WeaponsMask());
    localActor = NearestEnemies.getAFoundFlyingPlane(this.pos.getAbsPoint(), AttackMaxDistance(), getArmy(), 250.0F);

    return localActor;
  }

  public boolean enterToFireMode(int paramInt, Actor paramActor, float paramFloat, Aim paramAim)
  {
    if (!isNetMirror()) {
      send_FireCommand(paramActor, paramInt == 0 ? -1.0F : paramFloat);
    }

    this.lightWantedState = true;
    return true;
  }

  private void Track_Mirror(Actor paramActor)
  {
    if (this.dying == 1) {
      return;
    }

    if (paramActor == null) {
      return;
    }

    if (this.aime == null) {
      return;
    }

    this.lastTimeWhenFound = Time.current();

    this.lightWantedState = true;
    this.aime.passive_StartFiring(0, paramActor, 0, 0.0F);
  }

  private void Fire_Mirror(Actor paramActor, float paramFloat)
  {
    if (this.dying == 1) {
      return;
    }

    if (paramActor == null) {
      return;
    }

    if (this.aime == null) {
      return;
    }

    if (paramFloat <= 0.2F) {
      paramFloat = 0.2F;
    }

    if (paramFloat >= 15.0F) {
      paramFloat = 15.0F;
    }

    this.lastTimeWhenFound = Time.current();

    this.lightWantedState = true;
    this.aime.passive_StartFiring(1, paramActor, 0, paramFloat);
  }

  public int targetGun(Aim paramAim, Actor paramActor, float paramFloat, boolean paramBoolean)
  {
    paramBoolean = false;

    this.smoothMove = false;

    if ((!this.nightTime) || (this.dying == 1)) {
      return 0;
    }

    if ((!Actor.isValid(paramActor)) || (!paramActor.isAlive()) || (paramActor.getArmy() == 0)) {
      return 0;
    }

    this.lightWantedState = true;

    float f1 = paramFloat * Rnd(0.8F, 1.2F);
    Point3d localPoint3d1 = new Point3d();
    paramActor.futurePosition(f1, localPoint3d1);

    Point3d localPoint3d2 = new Point3d();
    this.pos.getAbs(localPoint3d2);
    localPoint3d2.z += 2.0D;

    Vector3d localVector3d = new Vector3d();
    localVector3d.sub(localPoint3d1, localPoint3d2);
    localVector3d.normalize();

    Orient localOrient = new Orient();
    localOrient.setAT0(localVector3d);

    long l = ((Aircraft)paramActor).tmSearchlighted;
    float f2;
    if (l == 0L) {
      f2 = this.prop.SEARCH_MAX_CONE_ANGLE / 2.0F;
      this.smoothMove = true;
    } else if (Time.current() - l > 1000L) {
      float f5 = (float)(Time.current() - l) / 1000.0F;
      if (f5 >= 2.0F) {
        f5 = 2.0F;
        this.smoothMove = true;
      }
      f2 = this.prop.SEARCH_MAX_CONE_ANGLE / 4.0F * f5;
    } else {
      f2 = this.prop.FOUND_MAX_CONE_ANGLE / 2.0F;
    }

    localOrient.increment(Rnd(-1.0F, 1.0F) * f2, Rnd(-1.0F, 1.0F) * f2, 0.0F);

    localVector3d.set(1.0D, 0.0D, 0.0D);
    localOrient.transform(localVector3d);
    float f3;
    float f4;
    if (paramBoolean) {
      f3 = 99999.0F;
      f4 = 99999.0F;
    } else {
      f3 = this.prop.HEAD_MAX_YAW_SPEED;
      f4 = this.prop.GUN_MAX_PITCH_SPEED;
    }

    int i = paramAim.setRotationForTargeting(this, this.pos.getAbs().getOrient(), localPoint3d2, this.headYaw, this.gunPitch, localVector3d, 0.0F, f1, this.prop.HEAD_YAW_RANGE, this.prop.GUN_MIN_PITCH, this.prop.GUN_MAX_PITCH, f3, f4, 0.0F);

    if ((i == 2) && 
      (Time.current() - this.lastTimeWhenFound > 22000L)) {
      paramAim.anglesYaw.setDeg(this.headYaw);
      paramAim.anglesPitch.setDeg(this.gunPitch);
      return 0;
    }

    return i;
  }

  public void singleShot(Aim paramAim)
  {
  }

  public void startFire(Aim paramAim)
  {
  }

  public void continueFire(Aim paramAim)
  {
  }

  public void stopFire(Aim paramAim)
  {
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Class cls;
    public SearchlightGeneric.SearchlightProperties proper;

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2)
    {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
        if (f == -9865.3447F) {
          System.out.println("Searchlight: Parameter [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
        }
        else {
          System.out.println("Searchlight: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
        }

        throw new RuntimeException("Can't set property");
      }
      return f;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("Searchlight: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");
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

    private static SearchlightGeneric.SearchlightProperties LoadSearchlightProperties(SectFile paramSectFile, String paramString, Class paramClass)
    {
      SearchlightGeneric.SearchlightProperties localSearchlightProperties = new SearchlightGeneric.SearchlightProperties();

      String str = getS(paramSectFile, paramString, "PanzerType", null);
      if (str == null) {
        str = "Car";
      }
      localSearchlightProperties.fnShotPanzer = TableFunctions.GetFunc2(str + "ShotPanzer");
      localSearchlightProperties.fnExplodePanzer = TableFunctions.GetFunc2(str + "ExplodePanzer");

      localSearchlightProperties.PANZER_TNT_TYPE = getF(paramSectFile, paramString, "PanzerSubtype", 0.0F, 100.0F);

      localSearchlightProperties.meshSummer = getS(paramSectFile, paramString, "MeshSummer");
      localSearchlightProperties.meshDesert = getS(paramSectFile, paramString, "MeshDesert", localSearchlightProperties.meshSummer);
      localSearchlightProperties.meshWinter = getS(paramSectFile, paramString, "MeshWinter", localSearchlightProperties.meshSummer);

      localSearchlightProperties.PANZER = getF(paramSectFile, paramString, "PanzerBody", 0.001F, 9.999F);
      localSearchlightProperties.HITBY_MASK = (localSearchlightProperties.PANZER > 0.015F ? -2 : -1);

      localSearchlightProperties.explodeName = getS(paramSectFile, paramString, "Explode", "Searchlight");

      localSearchlightProperties.Hclear = getF(paramSectFile, paramString, "MaxDistance", 6.0F, 12000.0F);

      float f = getF(paramSectFile, paramString, "HeadYawHalfRange", 0.0F, 180.0F);
      localSearchlightProperties.HEAD_YAW_RANGE.set(-f, f);
      localSearchlightProperties.GUN_MIN_PITCH = getF(paramSectFile, paramString, "GunMinPitch", -15.0F, 85.0F);
      localSearchlightProperties.GUN_STD_PITCH = getF(paramSectFile, paramString, "GunStdPitch", -15.0F, 90.0F);
      localSearchlightProperties.GUN_MAX_PITCH = getF(paramSectFile, paramString, "GunMaxPitch", 0.0F, 90.0F);
      localSearchlightProperties.HEAD_MAX_YAW_SPEED = getF(paramSectFile, paramString, "HeadMaxYawSpeed", 0.1F, 999.0F);
      localSearchlightProperties.GUN_MAX_PITCH_SPEED = getF(paramSectFile, paramString, "GunMaxPitchSpeed", 0.1F, 999.0F);

      localSearchlightProperties.SEARCH_MAX_CONE_ANGLE = getF(paramSectFile, paramString, "SearchMaxConeAngle", 0.01F, 60.0F);
      localSearchlightProperties.FOUND_MAX_CONE_ANGLE = getF(paramSectFile, paramString, "FoundMaxConeAngle", 0.0F, 20.0F);

      localSearchlightProperties.LIGHT_COLOR.x = getF(paramSectFile, paramString, "ColorR", 0.0F, 1.0F);
      localSearchlightProperties.LIGHT_COLOR.y = getF(paramSectFile, paramString, "ColorG", 0.0F, 1.0F);
      localSearchlightProperties.LIGHT_COLOR.z = getF(paramSectFile, paramString, "ColorB", 0.0F, 1.0F);

      localSearchlightProperties.LIGHT_LAND_I = getF(paramSectFile, paramString, "LandIntensity", 0.1F, 100.0F);
      localSearchlightProperties.LIGHT_LAND_R = getF(paramSectFile, paramString, "LandRadius", 1.0F, 300.0F);

      Property.set(paramClass, "iconName", "icons/" + getS(paramSectFile, paramString, "Icon") + ".mat");
      Property.set(paramClass, "meshName", localSearchlightProperties.meshSummer);

      return localSearchlightProperties;
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
        this.proper = LoadSearchlightProperties(Statics.getTechnicsFile(), str2, paramClass);
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
        this.proper.meshName = this.proper.meshWinter;
        break;
      case 2:
        this.proper.meshName = this.proper.meshDesert;
        break;
      default:
        this.proper.meshName = this.proper.meshSummer;
      }

      SearchlightGeneric localSearchlightGeneric = null;
      try
      {
        SearchlightGeneric.access$1702(this.proper);
        SearchlightGeneric.access$1802(paramActorSpawnArg);
        localSearchlightGeneric = (SearchlightGeneric)this.cls.newInstance();
        SearchlightGeneric.access$1702(null);
        SearchlightGeneric.access$1802(null);
      } catch (Exception localException) {
        SearchlightGeneric.access$1702(null);
        SearchlightGeneric.access$1802(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create Searchlight object [class:" + this.cls.getName() + "]");

        return null;
      }
      return localSearchlightGeneric;
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public boolean netInput(NetMsgInput paramNetMsgInput)
      throws IOException
    {
      float f2;
      if (paramNetMsgInput.isGuaranted())
      {
        Object localObject;
        short s;
        float f1;
        switch (paramNetMsgInput.readByte()) {
        case 73:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 0);
            post((NetMsgGuaranted)localObject);
          }
          s = paramNetMsgInput.readShort();
          f1 = paramNetMsgInput.readFloat();
          f2 = paramNetMsgInput.readFloat();
          if (s <= 0)
          {
            if (SearchlightGeneric.this.dying != 1) {
              SearchlightGeneric.this.aime.forgetAiming();
              SearchlightGeneric.this.setGunAngles(f1, f2);
            }
          }
          else if (SearchlightGeneric.this.dying != 1) {
            SearchlightGeneric.this.setGunAngles(f1, f2);
            SearchlightGeneric.this.Die(null, s, false);
          }

          return true;
        case 82:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 0);
            post((NetMsgGuaranted)localObject);
          }
          SearchlightGeneric.access$602(SearchlightGeneric.this, 0);

          SearchlightGeneric.this.setDiedFlag(false);

          SearchlightGeneric.this.aime.forgetAiming();

          SearchlightGeneric.this.activateMesh(true, false);
          SearchlightGeneric.this.setDefaultLivePose();

          SearchlightGeneric.access$1102(SearchlightGeneric.this, 0L);

          return true;
        case 68:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 1);
            post((NetMsgGuaranted)localObject);
          }
          s = paramNetMsgInput.readShort();
          f1 = paramNetMsgInput.readFloat();
          f2 = paramNetMsgInput.readFloat();
          if ((s > 0) && (SearchlightGeneric.this.dying != 1)) {
            SearchlightGeneric.this.setGunAngles(f1, f2);
            localObject = paramNetMsgInput.readNetObj();
            Actor localActor2 = localObject == null ? null : ((ActorNet)localObject).actor();
            SearchlightGeneric.this.Die(localActor2, s, true);
          }
          return true;
        }
        return false;
      }
      NetObj localNetObj;
      Actor localActor1;
      switch (paramNetMsgInput.readByte())
      {
      case 84:
        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 1);
          this.out.setIncludeTime(false);
          postReal(Message.currentRealTime(), this.out);
        }

        localNetObj = paramNetMsgInput.readNetObj();
        localActor1 = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
        SearchlightGeneric.this.Track_Mirror(localActor1);

        break;
      case 70:
        if (isMirrored()) {
          this.out.unLockAndSet(paramNetMsgInput, 1);
          this.out.setIncludeTime(true);
          postReal(Message.currentRealTime(), this.out);
        }

        localNetObj = paramNetMsgInput.readNetObj();
        localActor1 = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
        f2 = paramNetMsgInput.readFloat();
        float f3 = 0.001F * (float)(Message.currentGameTime() - Time.current()) + f2;

        SearchlightGeneric.this.Fire_Mirror(localActor1, f3);

        break;
      case 68:
        this.out.unLockAndSet(paramNetMsgInput, 1);
        this.out.setIncludeTime(false);
        postRealTo(Message.currentRealTime(), masterChannel(), this.out);
        return true;
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
      if (paramNetMsgInput.readByte() != 68)
        return false;
      if (SearchlightGeneric.this.dying == 1) {
        return true;
      }
      NetObj localNetObj = paramNetMsgInput.readNetObj();
      Actor localActor = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
      SearchlightGeneric.this.Die(localActor, 0, true);
      return true;
    }
  }

  class TickGame extends Interpolate
  {
    TickGame()
    {
    }

    public boolean tick()
    {
      if (SearchlightGeneric.this.dying == 1) {
        if (SearchlightGeneric.access$710(SearchlightGeneric.this) <= 0L) {
          if (!Mission.isDeathmatch())
          {
            if (SearchlightGeneric.this.aime != null) {
              SearchlightGeneric.this.aime.forgetAll();
              SearchlightGeneric.access$802(SearchlightGeneric.this, null);
            }
            return false;
          }
          if (!SearchlightGeneric.this.isNetMaster()) {
            SearchlightGeneric.access$702(SearchlightGeneric.this, 10000L);
            return true;
          }

          SearchlightGeneric.access$602(SearchlightGeneric.this, 0);

          SearchlightGeneric.this.setDiedFlag(false);

          SearchlightGeneric.this.aime.forgetAiming();

          SearchlightGeneric.this.activateMesh(true, false);

          SearchlightGeneric.this.setDefaultLivePose();
          SearchlightGeneric.this.send_RespawnCommand();

          SearchlightGeneric.access$1102(SearchlightGeneric.this, 0L);

          return true;
        }
        return true;
      }

      SearchlightGeneric.this.aime.tick_();

      SearchlightGeneric.access$402(SearchlightGeneric.this, World.Sun().ToSun.z <= -0.22F);

      if (SearchlightGeneric.this.lightWantedState == true) {
        if ((SearchlightGeneric.this.nightTime) && 
          (!SearchlightGeneric.this.lightIsOn)) {
          SearchlightGeneric.this.activateMesh(true, true);
        }

      }
      else if (SearchlightGeneric.this.lightIsOn) {
        SearchlightGeneric.this.activateMesh(true, false);
      }

      return true;
    }
  }

  class TickBuilder extends Interpolate
  {
    TickBuilder()
    {
    }

    public boolean tick()
    {
      SearchlightGeneric.access$402(SearchlightGeneric.this, World.Sun().ToSun.z <= -0.22F);
      if (SearchlightGeneric.this.lightIsOn) {
        if (!SearchlightGeneric.this.nightTime) {
          SearchlightGeneric.this.activateMesh(true, false);
        }
      }
      else if (SearchlightGeneric.this.nightTime) {
        SearchlightGeneric.this.activateMesh(true, true);
      }

      return true;
    }
  }

  class MyDrawer extends ActorMeshDraw
  {
    long t0 = 0L;
    long t1 = 0L;
    AnglesFork fa = new AnglesFork();
    AnglesFork fb = new AnglesFork();
    public boolean interpolateAngles = false;

    MyDrawer() {  }

    public void killLightMap() { if (this.lightMap != null) {
        this.lightMap.clear();
        this.lightMap = null;
      } }

    public void pushAngles(float paramFloat1, float paramFloat2)
    {
      long l = Time.tickNext();

      if ((this.interpolateAngles) || (this.t1 <= l)) {
        if (this.t1 == l) {
          this.fa.setDstDeg(paramFloat1);
          this.fb.setDstDeg(paramFloat2);
          this.t1 = l;
        } else {
          this.fa.setDeg(this.fa.getDstDeg(), paramFloat1);
          this.fb.setDeg(this.fb.getDstDeg(), paramFloat2);
          this.t0 = this.t1;
          this.t1 = l;
        }
        this.interpolateAngles = true;
      } else {
        this.t0 = (this.t1 = l);
        this.fa.setDeg(paramFloat1, paramFloat1);
        this.fb.setDeg(paramFloat2, paramFloat2);
        this.interpolateAngles = true;
      }
    }

    public int preRender(Actor paramActor) {
      if (this.interpolateAngles) {
        long l = Time.current();
        float f1;
        float f2;
        if ((this.t1 <= this.t0) || (l >= this.t1)) {
          f1 = this.fa.getDstDeg();
          f2 = this.fb.getDstDeg();
        } else if (l <= this.t0) {
          f1 = this.fa.getSrcDeg();
          f2 = this.fb.getSrcDeg();
        } else {
          float f3 = (float)(l - this.t0) / (float)(this.t1 - this.t0);
          f1 = this.fa.getDeg(f3);
          f2 = this.fb.getDeg(f3);
        }

        ((ActorHMesh)paramActor).hierMesh().chunkSetAngles("Head", f1, 0.0F, 0.0F);
        ((ActorHMesh)paramActor).hierMesh().chunkSetAngles("Gun", f2, 0.0F, 0.0F);
      }

      if (SearchlightGeneric.this.lightIsOn) {
        ((ActorHMesh)paramActor).hierMesh().setCurChunk("Ray_ON");
        ((ActorHMesh)paramActor).hierMesh().getChunkLTM(SearchlightGeneric.locLmatr);
        SearchlightGeneric.locLpos.set(10.0D, 0.0D, 0.0D);
        SearchlightGeneric.locLmatr.transform(SearchlightGeneric.locLpos);

        SearchlightGeneric.this.landLight.relPos.set(SearchlightGeneric.locLpos);
      }

      return super.preRender(paramActor);
    }
  }

  public static class SearchlightProperties
  {
    public String meshName = null;
    public String meshDeadName = null;

    public String meshSummer = null;
    public String meshDesert = null;
    public String meshWinter = null;
    public String meshDeadSummer = null;
    public String meshDeadDesert = null;
    public String meshDeadWinter = null;

    public TableFunction2 fnShotPanzer = null;
    public TableFunction2 fnExplodePanzer = null;

    public float PANZER = 0.001F;

    public float PANZER_TNT_TYPE = 1.0F;

    public String explodeName = null;

    public int HITBY_MASK = -1;

    public AnglesRange HEAD_YAW_RANGE = new AnglesRange(-1.0F, 1.0F);
    public float GUN_MIN_PITCH = 30.0F;
    public float GUN_STD_PITCH = 90.0F;
    public float GUN_MAX_PITCH = 90.0F;
    public float HEAD_MAX_YAW_SPEED = 720.0F;
    public float GUN_MAX_PITCH_SPEED = 60.0F;
    public float SEARCH_MAX_CONE_ANGLE = 30.0F;
    public float FOUND_MAX_CONE_ANGLE = 0.2F;
    public Point3f LIGHT_COLOR = new Point3f(1.0F, 1.0F, 1.0F);
    public float LIGHT_LAND_I = 3.0F;
    public float LIGHT_LAND_R = 3.0F;

    public double Hclear = 0.0D;

    public double H = 0.0D;
    public double R0 = 1.0D;
    public double R1 = 2.0D;
    public double TANGA = 1.0D;
  }
}