package com.maddox.il2.objects.trains;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Aimer;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.ai.AnglesForkExtended;
import com.maddox.il2.ai.AnglesRange;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Aim;
import com.maddox.il2.ai.ground.HunterInterface;
import com.maddox.il2.ai.ground.NearestEnemies;
import com.maddox.il2.ai.ground.Predator;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.MachineGunFlak30_20mm;
import com.maddox.rts.ObjState;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class Platform4 extends Wagon
  implements MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener, Predator, HunterInterface
{
  private static Class cls = Platform4.class;
  private static final int N_FIRING_DEVICES = 2;
  private FiringDevice[] arms = new FiringDevice[2];
  private static final float ATTACK_MAX_DISTANCE = 2200.0F;
  private static final float GUN_MIN_PITCH = -10.0F;
  private static final float GUN_MAX_PITCH = 89.0F;
  private static final float HEAD_MAX_YAW_SPEED = 38.0F;
  private static final float GUN_MAX_PITCH_SPEED = 18.0F;
  private AnglesRange HEAD_YAW_RANGE = new AnglesRange(-180.0F, 180.0F);
  private static final float DELAY_AFTER_SHOOT = 1.2F;
  private static final float CHAINFIRE_TIME = 6.5F;
  private static Vector3d tmpv = new Vector3d();
  private static Point3d p1 = new Point3d();

  public static final double Rnd(double paramDouble1, double paramDouble2)
  {
    return World.Rnd().nextDouble(paramDouble1, paramDouble2);
  }
  public static final float Rnd(float paramFloat1, float paramFloat2) {
    return World.Rnd().nextFloat(paramFloat1, paramFloat2);
  }

  private static String getMeshName(int paramInt)
  {
    switch (World.cur().camouflage) {
    case 0:
    case 1:
    case 2:
    }
    return "3do/Trains/Platform4" + (paramInt == 1 ? "_Dmg" : "") + "/hier.him";
  }

  public static String getMeshNameForEditor()
  {
    return getMeshName(0);
  }

  public void destroy() {
    if (isDestroyed()) {
      return;
    }
    eraseGuns();
    super.destroy();
  }

  protected void hiddenexplode()
  {
    eraseGuns();
    super.hiddenexplode();
  }

  protected void explode(Actor paramActor)
  {
    eraseGuns();
    super.explode(paramActor);
  }

  private final FiringDevice GetFiringDevice(Aim paramAim)
  {
    for (int i = 0; i < 2; i++) {
      if ((this.arms[i] != null) && (this.arms[i].aime == paramAim)) {
        return this.arms[i];
      }
    }
    System.out.println("Internal error: Can't find train gun.");
    return null;
  }

  private void setGunAngles(FiringDevice paramFiringDevice, float paramFloat1, float paramFloat2) {
    FiringDevice.access$102(paramFiringDevice, paramFloat1);
    FiringDevice.access$202(paramFiringDevice, paramFloat2);
    hierMesh().chunkSetAngles("Head" + paramFiringDevice.id, paramFiringDevice.headYaw, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("Gun" + paramFiringDevice.id, -paramFiringDevice.gunPitch, 0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(false);
  }

  private void eraseGuns()
  {
    if (this.arms != null) {
      for (int i = 0; i < 2; i++) {
        if (this.arms[i] != null) {
          if (this.arms[i].aime != null) {
            this.arms[i].aime.forgetAll();
            FiringDevice.access$002(this.arms[i], null);
          }
          if (this.arms[i].gun != null) {
            ObjState.destroy(this.arms[i].gun);
            FiringDevice.access$402(this.arms[i], null);
          }
          FiringDevice.access$502(this.arms[i], null);
          this.arms[i] = null;
        }
      }
      this.arms = null;
    }
  }

  protected void forgetAllAiming() {
    if (this.arms != null)
      for (int i = 0; i < 2; i++)
        if ((this.arms[i] != null) && (this.arms[i].aime != null))
          this.arms[i].aime.forgetAiming();
  }

  public Platform4(Train paramTrain)
  {
    super(paramTrain, getMeshName(0), getMeshName(1));
    try {
      this.life = 0.012F;
      this.ignoreTNT = 0.4F;
      this.killTNT = 3.0F;
      this.bodyMaterial = 2;

      for (int i = 0; i < 2; i++) {
        this.arms[i] = new FiringDevice();

        FiringDevice.access$302(this.arms[i], i);

        FiringDevice.access$402(this.arms[i], new MachineGunFlak30_20mm());
        if (this.arms[i].gun == null) {
          System.out.println("Train: Gun is not created");
        } else {
          this.arms[i].gun.set(this, "ShellStart" + i);
          this.arms[i].gun.loadBullets(-1);
        }

        setGunAngles(this.arms[i], 0.0F, 39.5F);

        Loc localLoc = new Loc();
        hierMesh().setCurChunk("Gun" + i);
        hierMesh().getChunkLocObj(localLoc);

        FiringDevice.access$502(this.arms[i], new Point3d());
        localLoc.get(this.arms[i].fireOffset);

        FiringDevice.access$002(this.arms[i], new Aim(this, isNetMirror()));
      }
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  void place(Point3d paramPoint3d1, Point3d paramPoint3d2, boolean paramBoolean1, boolean paramBoolean2)
  {
    super.place(paramPoint3d1, paramPoint3d2, paramBoolean1, paramBoolean2);

    if (this.arms == null) {
      return;
    }

    for (int i = 0; i < 2; i++)
      this.arms[i].aime.tick_();
  }

  public int WeaponsMask()
  {
    return 2;
  }

  public float AttackMaxDistance()
  {
    return 2200.0F;
  }

  public float getReloadingTime(Aim paramAim)
  {
    return 1.2F;
  }

  public float chainFireTime(Aim paramAim)
  {
    return 6.5F * Rnd(0.75F, 1.25F);
  }

  public float probabKeepSameEnemy(Actor paramActor)
  {
    return 0.75F;
  }

  public float minTimeRelaxAfterFight()
  {
    return 0.0F;
  }

  public void gunStartParking(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    paramAim.setRotationForParking(localFiringDevice.headYaw, localFiringDevice.gunPitch, 0.0F, 39.5F, this.HEAD_YAW_RANGE, 38.0F, 18.0F);
  }

  public void gunInMove(boolean paramBoolean, Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    float f1 = paramAim.t();
    float f2 = paramAim.anglesYaw.getDeg(f1);
    float f3 = paramAim.anglesPitch.getDeg(f1);
    setGunAngles(localFiringDevice, f2, f3);
  }

  public Actor findEnemy(Aim paramAim)
  {
    if (isNetMirror()) {
      return null;
    }

    Actor localActor = null;

    NearestEnemies.set(WeaponsMask());

    localActor = NearestEnemies.getAFoundEnemy(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), AttackMaxDistance(), getArmy());

    if (localActor == null) {
      return null;
    }

    if (!(localActor instanceof Prey)) {
      System.out.println("trplatf4: nearest enemies: non-Prey");
      return null;
    }

    FiringDevice localFiringDevice = GetFiringDevice(paramAim);

    BulletProperties localBulletProperties = null;

    if (localFiringDevice.gun.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties != null) {
      i = ((Prey)localActor).chooseBulletType(localFiringDevice.gun.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet);

      if (i < 0)
      {
        return null;
      }

      localBulletProperties = localFiringDevice.gun.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[i];
    }

    int i = ((Prey)localActor).chooseShotpoint(localBulletProperties);

    if (i < 0) {
      return null;
    }

    paramAim.shotpoint_idx = i;

    return localActor;
  }

  public boolean enterToFireMode(int paramInt, Actor paramActor, float paramFloat, Aim paramAim)
  {
    if (!isNetMirror()) {
      FiringDevice localFiringDevice = GetFiringDevice(paramAim);
      send_FireCommand(localFiringDevice.id, paramActor, paramAim.shotpoint_idx, paramInt == 0 ? -1.0F : paramFloat);
    }

    return true;
  }

  protected void Track_Mirror(int paramInt1, Actor paramActor, int paramInt2)
  {
    if (IsDamaged()) {
      return;
    }

    if (paramActor == null) {
      return;
    }

    if ((this.arms == null) || (this.arms[paramInt1].aime == null))
    {
      return;
    }

    if ((paramInt1 < 0) || (paramInt1 >= 2)) {
      return;
    }

    this.arms[paramInt1].aime.passive_StartFiring(0, paramActor, paramInt2, 0.0F);
  }

  protected void Fire_Mirror(int paramInt1, Actor paramActor, int paramInt2, float paramFloat)
  {
    if (IsDamaged()) {
      return;
    }

    if (paramActor == null) {
      return;
    }

    if ((this.arms == null) || (this.arms[paramInt1].aime == null))
    {
      return;
    }

    if ((paramInt1 < 0) || (paramInt1 >= 2)) {
      return;
    }

    if (paramFloat <= 0.2F) {
      paramFloat = 0.2F;
    }

    if (paramFloat >= 15.0F) {
      paramFloat = 15.0F;
    }

    this.arms[paramInt1].aime.passive_StartFiring(1, paramActor, paramInt2, paramFloat);
  }

  public int targetGun(Aim paramAim, Actor paramActor, float paramFloat, boolean paramBoolean)
  {
    if ((!Actor.isValid(paramActor)) || (!paramActor.isAlive()) || (paramActor.getArmy() == 0)) {
      return 0;
    }

    FiringDevice localFiringDevice = GetFiringDevice(paramAim);

    if ((localFiringDevice.gun instanceof CannonMidrangeGeneric)) {
      int i = ((Prey)paramActor).chooseBulletType(localFiringDevice.gun.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet);
      if (i < 0) {
        return 0;
      }
      ((CannonMidrangeGeneric)localFiringDevice.gun).setBulletType(i);
    }

    boolean bool = ((Prey)paramActor).getShotpointOffset(paramAim.shotpoint_idx, p1);
    if (!bool) {
      return 0;
    }

    float f1 = paramFloat * Rnd(0.8F, 1.2F);

    if (!Aimer.Aim((BulletAimer)localFiringDevice.gun, paramActor, this, f1, p1, localFiringDevice.fireOffset)) {
      return 0;
    }

    Point3d localPoint3d1 = new Point3d();
    Aimer.GetPredictedTargetPosition(localPoint3d1);

    Point3d localPoint3d2 = Aimer.GetHunterFirePoint();

    float f2 = 0.19F;

    double d1 = localPoint3d1.distance(localPoint3d2);
    double d2 = localPoint3d1.jdField_z_of_type_Double;

    localPoint3d1.sub(localPoint3d2);
    localPoint3d1.scale(Rnd(0.98D, 1.02D));
    localPoint3d1.add(localPoint3d2);

    if (f1 > 0.001F) {
      Point3d localPoint3d3 = new Point3d();
      paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(localPoint3d3);

      tmpv.sub(localPoint3d1, localPoint3d3);
      double d3 = tmpv.length();

      if (d3 > 0.001D) {
        float f7 = (float)d3 / f1;
        if (f7 > 200.0F) {
          f7 = 200.0F;
        }
        float f8 = f7 * 0.01F;

        localPoint3d3.sub(localPoint3d2);
        double d4 = localPoint3d3.jdField_x_of_type_Double * localPoint3d3.jdField_x_of_type_Double + localPoint3d3.jdField_y_of_type_Double * localPoint3d3.jdField_y_of_type_Double + localPoint3d3.jdField_z_of_type_Double * localPoint3d3.jdField_z_of_type_Double;

        if (d4 > 0.01D) {
          float f9 = (float)tmpv.dot(localPoint3d3);
          f9 /= (float)(d3 * Math.sqrt(d4));

          f9 = (float)Math.sqrt(1.0F - f9 * f9);

          f8 *= (0.4F + 0.6F * f9);
        }
        f8 *= 0.5F;

        int k = Mission.curCloudsType();
        if (k > 2) {
          float f10 = k > 4 ? 400.0F : 800.0F;
          float f11 = (float)(d1 / f10);
          if (f11 > 1.0F) {
            if (f11 > 10.0F) {
              return 0;
            }
            f11 = (f11 - 1.0F) / 9.0F;
            f8 *= (f11 + 1.0F);
          }
        }

        if ((k >= 3) && (d2 > Mission.curCloudsHeight())) {
          f8 *= 1.25F;
        }

        f2 += f8;
      }

    }

    if (World.Sun().ToSun.jdField_z_of_type_Float < -0.15F) {
      f5 = (-World.Sun().ToSun.jdField_z_of_type_Float - 0.15F) / 0.13F;
      if (f5 >= 1.0F) {
        f5 = 1.0F;
      }

      if (((paramActor instanceof Aircraft)) && (Time.current() - ((Aircraft)paramActor).tmSearchlighted < 1000L))
      {
        f5 = 0.0F;
      }
      f2 += 10.0F * f5;
    }

    float f5 = (float)paramActor.getSpeed(null) - 10.0F;
    float f6 = 83.333336F;
    f5 = f5 >= f6 ? 1.0F : f5 / f6;
    f2 += f5 * 2.0F;

    Vector3d localVector3d = new Vector3d();
    if (!((BulletAimer)localFiringDevice.gun).FireDirection(localPoint3d2, localPoint3d1, localVector3d))
    {
      return 0;
    }
    float f3;
    float f4;
    if (paramBoolean) {
      f3 = 99999.0F;
      f4 = 99999.0F;
    } else {
      f3 = 38.0F;
      f4 = 18.0F;
    }

    int j = paramAim.setRotationForTargeting(this, this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().getOrient(), localPoint3d2, localFiringDevice.headYaw, localFiringDevice.gunPitch, localVector3d, f2, f1, this.HEAD_YAW_RANGE, -10.0F, 89.0F, f3, f4, 0.0F);

    return j;
  }

  public void singleShot(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    localFiringDevice.gun.shots(1);
  }

  public void startFire(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    localFiringDevice.gun.shots(-1);
  }

  public void continueFire(Aim paramAim)
  {
  }

  public void stopFire(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    localFiringDevice.gun.shots(0);
  }

  static
  {
    Spawn.add(cls, new SPAWN());
  }

  public static class SPAWN implements WagonSpawn {
    public Wagon wagonSpawn(Train paramTrain) {
      return new Platform4(paramTrain);
    }
  }

  public class FiringDevice
  {
    private int id;
    private Gun gun;
    private Aim aime;
    private float headYaw;
    private float gunPitch;
    private Point3d fireOffset;

    public FiringDevice()
    {
    }
  }
}