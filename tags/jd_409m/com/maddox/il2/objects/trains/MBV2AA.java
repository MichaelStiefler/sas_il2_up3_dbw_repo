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
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.weapons.CannonMidrangeGeneric;
import com.maddox.il2.objects.weapons.CannonZIS3;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.il2.objects.weapons.MGunDA762t;
import com.maddox.il2.objects.weapons.MMGunShKASt;
import com.maddox.rts.ObjState;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class MBV2AA extends Wagon
  implements MsgCollisionRequestListener, MsgExplosionListener, MsgShotListener, Predator, HunterInterface
{
  private static Class cls = MBV2AA.class;
  private static final int N_FIRING_DEVICES = 10;
  private FiringDevice[] arms;
  private static Vector3d tmpv = new Vector3d();
  private static Point3d p1 = new Point3d();

  public static final double Rnd(double paramDouble1, double paramDouble2)
  {
    return World.Rnd().nextDouble(paramDouble1, paramDouble2);
  }

  public static final float Rnd(float paramFloat1, float paramFloat2)
  {
    return World.Rnd().nextFloat(paramFloat1, paramFloat2);
  }

  public static final float KmHourToMSec(float paramFloat) {
    return paramFloat / 3.6F;
  }

  private static String getMeshName(int paramInt)
  {
    String str = "summer";
    switch (World.cur().camouflage)
    {
    case 0:
      break;
    case 1:
      str = "winter"; break;
    }

    return "3do/Trains/MBV2" + (paramInt != 1 ? "AA" : "_Dmg") + "/" + str + "/hier.him";
  }

  public static String getMeshNameForEditor()
  {
    return getMeshName(0);
  }

  public void destroy()
  {
    if (isDestroyed())
    {
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
    for (int i = 0; i < 10; i++) {
      if ((this.arms[i] != null) && (this.arms[i].aime == paramAim))
        return this.arms[i];
    }
    System.out.println("Internal error: Can't find train gun.");
    return null;
  }

  private void setGunAngles(FiringDevice paramFiringDevice, float paramFloat1, float paramFloat2)
  {
    FiringDevice.access$102(paramFiringDevice, paramFloat1);
    FiringDevice.access$202(paramFiringDevice, paramFloat2);
    hierMesh().chunkSetAngles("Head" + paramFiringDevice.id, paramFiringDevice.headYaw, 0.0F, 0.0F);
    hierMesh().chunkSetAngles("Gun" + paramFiringDevice.id, -paramFiringDevice.gunPitch, 0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.inValidate(false);
  }

  private void eraseGuns()
  {
    if (this.arms != null)
    {
      for (int i = 0; i < 10; i++) {
        if (this.arms[i] == null)
          continue;
        if (this.arms[i].aime != null)
        {
          this.arms[i].aime.forgetAll();
          FiringDevice.access$002(this.arms[i], null);
        }
        if (this.arms[i].gun != null)
        {
          ObjState.destroy(this.arms[i].gun);
          FiringDevice.access$402(this.arms[i], null);
        }
        FiringDevice.access$502(this.arms[i], null);
        this.arms[i] = null;
      }

      this.arms = null;
    }
  }

  protected void forgetAllAiming()
  {
    if (this.arms != null)
    {
      for (int i = 0; i < 10; i++)
        if ((this.arms[i] != null) && (this.arms[i].aime != null))
          this.arms[i].aime.forgetAiming();
    }
  }

  private void fillGunProperties(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt3, float paramFloat4, AnglesRange paramAnglesRange, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12)
  {
    this.arms[paramInt1].WEAPONS_MASK = paramInt2;
    this.arms[paramInt1].TRACKING_ONLY = paramBoolean;
    this.arms[paramInt1].ATTACK_MAX_DISTANCE = paramFloat1;
    this.arms[paramInt1].ATTACK_MAX_RADIUS = paramFloat2;
    this.arms[paramInt1].ATTACK_MAX_HEIGHT = paramFloat3;
    this.arms[paramInt1].ATTACK_FAST_TARGETS = paramInt3;
    this.arms[paramInt1].FAST_TARGETS_ANGLE_ERROR = paramFloat4;
    this.arms[paramInt1].HEAD_YAW_RANGE = paramAnglesRange;
    this.arms[paramInt1].HEAD_STD_YAW = paramFloat5;
    this.arms[paramInt1].GUN_MIN_PITCH = paramFloat6;
    this.arms[paramInt1].GUN_STD_PITCH = paramFloat7;
    this.arms[paramInt1].GUN_MAX_PITCH = paramFloat8;
    this.arms[paramInt1].HEAD_MAX_YAW_SPEED = paramFloat9;
    this.arms[paramInt1].GUN_MAX_PITCH_SPEED = paramFloat10;
    this.arms[paramInt1].DELAY_AFTER_SHOOT = paramFloat11;
    this.arms[paramInt1].CHAINFIRE_TIME = paramFloat12;
  }

  public MBV2AA(Train paramTrain)
  {
    super(paramTrain, getMeshName(0), getMeshName(1));
    this.arms = new FiringDevice[10];
    try
    {
      this.life = 0.036F;
      this.ignoreTNT = 16.0F;
      this.killTNT = 42.0F;
      this.bodyMaterial = 2;
      for (int i = 0; i < 3; i++) {
        this.arms[i] = new FiringDevice();
        FiringDevice.access$302(this.arms[i], i);
        FiringDevice.access$402(this.arms[i], new CannonZIS3());
        if (this.arms[i].gun == null)
        {
          System.out.println("Train: Gun is not created");
        }
        else {
          this.arms[i].gun.set(this, "ShellStart" + i);
          this.arms[i].gun.loadBullets(-1);
        }
        Loc localLoc1 = new Loc();
        hierMesh().setCurChunk("Head" + i);
        hierMesh().getChunkLocObj(localLoc1);
        FiringDevice.access$502(this.arms[i], new Point3d());
        localLoc1.get(this.arms[i].fireOffset);
        FiringDevice.access$602(this.arms[i], new Orient());
        localLoc1.get(this.arms[i].fireOrient);
        FiringDevice.access$002(this.arms[i], new Aim(this, isNetMirror()));
        try {
          this.arms[i].gunClass = Class.forName("com.maddox.il2.objects.weapons.CannonZIS3");
        } catch (Exception localException2) {
        }
      }
      for (int j = 3; j < 10; j++) {
        this.arms[j] = new FiringDevice();
        FiringDevice.access$302(this.arms[j], j);
        if ((j == 7) || (j == 9)) {
          FiringDevice.access$402(this.arms[j], new MGunDA762t());
        }
        else {
          FiringDevice.access$402(this.arms[j], new MMGunShKASt());
        }
        if (this.arms[j].gun == null)
        {
          System.out.println("Train: Gun is not created");
        }
        else {
          this.arms[j].gun.set(this, "ShellStart" + j);
          this.arms[j].gun.loadBullets(-1);
        }
        Loc localLoc2 = new Loc();
        hierMesh().setCurChunk("Head" + j);
        hierMesh().getChunkLocObj(localLoc2);
        FiringDevice.access$502(this.arms[j], new Point3d());
        localLoc2.get(this.arms[j].fireOffset);
        FiringDevice.access$602(this.arms[j], new Orient());
        localLoc2.get(this.arms[j].fireOrient);
        FiringDevice.access$002(this.arms[j], new Aim(this, isNetMirror()));
        try {
          if ((j == 7) || (j == 9)) {
            this.arms[j].gunClass = Class.forName("com.maddox.il2.objects.weapons.MGunDA762t");
          }
          else {
            this.arms[j].gunClass = Class.forName("com.maddox.il2.objects.weapons.MMGunShKASt");
          }
        }
        catch (Exception localException3)
        {
        }

      }

      fillGunProperties(0, Gun.getProperties(this.arms[0].gunClass).weaponType, false, 4000.0F, 4000.0F, 4000.0F, 0, 0.0F, new AnglesRange(-138.0F, 138.0F), 0.0F, -5.0F, 0.0F, 25.0F, 7.5F, 7.5F, 9.0F, 0.0F);
      fillGunProperties(1, Gun.getProperties(this.arms[1].gunClass).weaponType, false, 4000.0F, 4000.0F, 4000.0F, 0, 0.0F, new AnglesRange(-159.0F, 159.0F), 0.0F, -5.0F, 0.0F, 25.0F, 7.5F, 7.5F, 9.0F, 0.0F);
      fillGunProperties(2, Gun.getProperties(this.arms[2].gunClass).weaponType, false, 4000.0F, 4000.0F, 4000.0F, 0, 0.0F, new AnglesRange(-140.0F, 140.0F), 0.0F, -5.0F, 0.0F, 25.0F, 7.5F, 7.5F, 9.0F, 0.0F);
      fillGunProperties(3, Gun.getProperties(this.arms[3].gunClass).weaponType, false, 2200.0F, 2200.0F, 2200.0F, 1, 0.0F, new AnglesRange(-45.0F, 45.0F), 0.0F, -45.0F, 0.0F, 45.0F, 38.0F, 18.0F, 1.5F, 6.5F);
      fillGunProperties(4, Gun.getProperties(this.arms[4].gunClass).weaponType, false, 2200.0F, 2200.0F, 2200.0F, 1, 0.0F, new AnglesRange(-45.0F, 45.0F), 0.0F, -45.0F, 0.0F, 45.0F, 38.0F, 18.0F, 1.5F, 6.5F);
      fillGunProperties(5, Gun.getProperties(this.arms[5].gunClass).weaponType, false, 2200.0F, 2200.0F, 2200.0F, 1, 0.0F, new AnglesRange(-45.0F, 45.0F), 0.0F, -45.0F, 0.0F, 45.0F, 38.0F, 18.0F, 1.5F, 6.5F);
      fillGunProperties(6, Gun.getProperties(this.arms[6].gunClass).weaponType, false, 2200.0F, 2200.0F, 2200.0F, 1, 0.0F, new AnglesRange(-45.0F, 45.0F), 0.0F, -45.0F, 0.0F, 45.0F, 38.0F, 18.0F, 1.5F, 6.5F);
      fillGunProperties(7, Gun.getProperties(this.arms[7].gunClass).weaponType, false, 2200.0F, 2200.0F, 2200.0F, 1, 0.0F, new AnglesRange(-180.0F, 180.0F), 0.0F, -5.0F, 0.0F, 80.0F, 38.0F, 18.0F, 1.5F, 6.5F);
      fillGunProperties(8, Gun.getProperties(this.arms[8].gunClass).weaponType, false, 3000.0F, 3000.0F, 3000.0F, 1, 0.0F, new AnglesRange(-180.0F, 180.0F), 0.0F, 0.0F, 0.0F, 82.0F, 38.0F, 18.0F, 0.375F, 6.5F);
      fillGunProperties(9, Gun.getProperties(this.arms[9].gunClass).weaponType, false, 2200.0F, 2200.0F, 2200.0F, 1, 0.0F, new AnglesRange(-180.0F, 180.0F), 0.0F, -5.0F, 0.0F, 80.0F, 38.0F, 18.0F, 1.5F, 6.5F);
    }
    catch (Exception localException1)
    {
      System.out.println(localException1.getMessage());
      localException1.printStackTrace();
    }
  }

  void place(Point3d paramPoint3d1, Point3d paramPoint3d2, boolean paramBoolean1, boolean paramBoolean2)
  {
    super.place(paramPoint3d1, paramPoint3d2, paramBoolean1, paramBoolean2);
    if (this.arms == null)
      return;
    for (int i = 0; i < 10; i++)
      this.arms[i].aime.tick_();
  }

  private final FiringDevice GetGunProperties(Aim paramAim)
  {
    for (int i = 0; i < 10; i++) {
      if (this.arms[i].aime == paramAim)
        return this.arms[i];
    }
    System.out.println("Internal error 2: Can't find train gun.");
    return null;
  }

  public int WeaponsMask()
  {
    int i = 0;
    for (int j = 0; j < 10; j++) {
      i |= this.arms[j].WEAPONS_MASK;
    }
    return i;
  }

  public float AttackMaxDistance()
  {
    float f = 0.0F;
    for (int i = 0; i < 10; i++) {
      if (this.arms[i].ATTACK_MAX_DISTANCE <= f) continue; f = this.arms[i].ATTACK_MAX_DISTANCE;
    }
    return f;
  }

  public float getReloadingTime(Aim paramAim)
  {
    for (int i = 0; i < 10; i++) {
      if (this.arms[i].aime == paramAim) return this.arms[i].DELAY_AFTER_SHOOT;
    }
    return 0.0F;
  }

  public float chainFireTime(Aim paramAim)
  {
    float f = 0.0F;
    for (int i = 0; i < 10; i++) {
      if (this.arms[i].aime != paramAim) continue; f = this.arms[i].CHAINFIRE_TIME;
    }
    return f > 0.0F ? f * Rnd(0.75F, 1.25F) : 0.0F;
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
    int i = 0;
    for (int j = 0; j < 10; j++) {
      if (this.arms[j].aime != paramAim) continue; localFiringDevice = this.arms[j];
    }
    paramAim.setRotationForParking(localFiringDevice.headYaw, localFiringDevice.gunPitch, localFiringDevice.HEAD_STD_YAW, localFiringDevice.GUN_STD_PITCH, localFiringDevice.HEAD_YAW_RANGE, localFiringDevice.HEAD_MAX_YAW_SPEED, localFiringDevice.GUN_MAX_PITCH_SPEED);
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
    if (isNetMirror())
      return null;
    int i = 0;
    for (int j = 0; j < 10; j++) {
      if (this.arms[j].aime != paramAim) continue; i = this.arms[j].id;
    }
    Actor localActor = null;
    switch (this.arms[i].ATTACK_FAST_TARGETS)
    {
    case 0:
      NearestEnemies.set(this.arms[i].WEAPONS_MASK, -9999.9004F, KmHourToMSec(100.0F));
      break;
    case 1:
      NearestEnemies.set(this.arms[i].WEAPONS_MASK);
      break;
    default:
      NearestEnemies.set(this.arms[i].WEAPONS_MASK, KmHourToMSec(100.0F), 9999.9004F);
    }

    localActor = NearestEnemies.getAFoundEnemy(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), this.arms[i].ATTACK_MAX_RADIUS, getArmy());
    if (localActor == null)
      return null;
    if (!(localActor instanceof Prey))
    {
      System.out.println("trplatf4: nearest enemies: non-Prey");
      return null;
    }
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    BulletProperties localBulletProperties = null;
    if (localFiringDevice.gun.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties != null)
    {
      k = ((Prey)localActor).chooseBulletType(localFiringDevice.gun.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet);
      if (k < 0)
        return null;
      localBulletProperties = localFiringDevice.gun.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[k];
    }
    int k = ((Prey)localActor).chooseShotpoint(localBulletProperties);
    if (k < 0)
    {
      return null;
    }

    paramAim.shotpoint_idx = k;
    return localActor;
  }

  public boolean enterToFireMode(int paramInt, Actor paramActor, float paramFloat, Aim paramAim)
  {
    if (!isNetMirror())
    {
      FiringDevice localFiringDevice = GetFiringDevice(paramAim);
      send_FireCommand(localFiringDevice.id, paramActor, paramAim.shotpoint_idx, paramInt != 0 ? paramFloat : -1.0F);
    }
    return true;
  }

  protected void Track_Mirror(int paramInt1, Actor paramActor, int paramInt2)
  {
    if (IsDamaged())
      return;
    if (paramActor == null)
      return;
    if ((this.arms == null) || (this.arms[paramInt1].aime == null))
      return;
    if ((paramInt1 < 0) || (paramInt1 >= 10))
    {
      return;
    }

    this.arms[paramInt1].aime.passive_StartFiring(0, paramActor, paramInt2, 0.0F);
  }

  protected void Fire_Mirror(int paramInt1, Actor paramActor, int paramInt2, float paramFloat)
  {
    if (IsDamaged())
      return;
    if (paramActor == null)
      return;
    if ((this.arms == null) || (this.arms[paramInt1].aime == null))
      return;
    if ((paramInt1 < 0) || (paramInt1 >= 10))
      return;
    if (paramFloat <= 0.2F)
      paramFloat = 0.2F;
    if (paramFloat >= 15.0F)
      paramFloat = 15.0F;
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
    float f1 = paramFloat * Rnd(0.8F, 1.2F);
    if (!Aimer.Aim((BulletAimer)localFiringDevice.gun, paramActor, this, f1, p1, localFiringDevice.fireOffset))
      return 0;
    Point3d localPoint3d1 = new Point3d();
    Aimer.GetPredictedTargetPosition(localPoint3d1);
    Point3d localPoint3d2 = Aimer.GetHunterFirePoint();
    float f2 = 0.19F;
    double d1 = localPoint3d1.distance(localPoint3d2);
    double d2 = localPoint3d1.jdField_z_of_type_Double;
    localPoint3d1.sub(localPoint3d2);
    localPoint3d1.scale(Rnd(0.98D, 1.02D));
    localPoint3d1.add(localPoint3d2);
    float f5;
    if (f1 > 0.001F)
    {
      Point3d localPoint3d3 = new Point3d();
      paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(localPoint3d3);
      tmpv.sub(localPoint3d1, localPoint3d3);
      double d3 = tmpv.length();
      if (d3 > 0.001D)
      {
        f5 = (float)d3 / f1;
        if (f5 > 200.0F)
          f5 = 200.0F;
        float f6 = f5 * 0.01F;
        localPoint3d3.sub(localPoint3d2);
        double d4 = localPoint3d3.jdField_x_of_type_Double * localPoint3d3.jdField_x_of_type_Double + localPoint3d3.jdField_y_of_type_Double * localPoint3d3.jdField_y_of_type_Double + localPoint3d3.jdField_z_of_type_Double * localPoint3d3.jdField_z_of_type_Double;
        if (d4 > 0.01D)
        {
          float f7 = (float)tmpv.dot(localPoint3d3);
          f7 /= (float)(d3 * Math.sqrt(d4));
          f7 = (float)Math.sqrt(1.0F - f7 * f7);
          f6 *= (0.4F + 0.6F * f7);
        }
        f6 *= 0.5F;
        int k = Mission.curCloudsType();
        if (k > 2)
        {
          float f8 = k <= 4 ? 800.0F : 400.0F;
          float f9 = (float)(d1 / f8);
          if (f9 > 1.0F)
          {
            if (f9 > 10.0F)
              return 0;
            f9 = (f9 - 1.0F) / 9.0F;
            f6 *= (f9 + 1.0F);
          }
        }
        if ((k >= 3) && (d2 > Mission.curCloudsHeight()))
          f6 *= 1.25F;
        f2 += f6;
      }
    }
    if (World.Sun().ToSun.jdField_z_of_type_Float < -0.15F)
    {
      f3 = (-World.Sun().ToSun.jdField_z_of_type_Float - 0.15F) / 0.13F;
      if (f3 >= 1.0F)
        f3 = 1.0F;
      if (((paramActor instanceof Aircraft)) && (Time.current() - ((Aircraft)paramActor).tmSearchlighted < 1000L))
        f3 = 0.0F;
      f2 += 10.0F * f3;
    }
    float f3 = (float)paramActor.getSpeed(null) - 10.0F;
    float f4 = 83.333344F;
    f3 = f3 < f4 ? f3 / f4 : 1.0F;
    f2 += f3 * 2.0F;
    Vector3d localVector3d = new Vector3d();
    if (!((BulletAimer)localFiringDevice.gun).FireDirection(localPoint3d2, localPoint3d1, localVector3d)) {
      return 0;
    }
    if (paramBoolean)
    {
      f5 = 99999.0F;
      d2 = 99999.0D;
    }
    else {
      f5 = localFiringDevice.HEAD_MAX_YAW_SPEED;
      d2 = localFiringDevice.GUN_MAX_PITCH_SPEED;
    }
    Orient localOrient = new Orient();
    Loc localLoc;
    if (localFiringDevice.id == 7) {
      localLoc = new Loc();
      hierMesh().setCurChunk("Head1");
      hierMesh().getChunkLocObj(localLoc);
      localOrient.add(localLoc.getOrient(), this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().getOrient());
    }
    else if (localFiringDevice.id == 9) {
      localLoc = new Loc();
      hierMesh().setCurChunk("Head2");
      hierMesh().getChunkLocObj(localLoc);
      localOrient.add(localLoc.getOrient(), this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().getOrient());
    }
    else {
      localOrient.add(this.arms[FiringDevice.access$300(localFiringDevice)].fireOrient, this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs().getOrient());
      localOrient.setYPR(localOrient.getYaw(), localOrient.getPitch(), localOrient.getRoll());
    }
    int j = paramAim.setRotationForTargeting(this, localOrient, localPoint3d2, this.arms[FiringDevice.access$300(localFiringDevice)].headYaw, this.arms[FiringDevice.access$300(localFiringDevice)].gunPitch, localVector3d, f2, f1, this.arms[localFiringDevice.id].HEAD_YAW_RANGE, this.arms[localFiringDevice.id].GUN_MIN_PITCH, this.arms[localFiringDevice.id].GUN_MAX_PITCH, f5, (float)d2, 0.0F);
    return j;
  }

  public void singleShot(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    if (!localFiringDevice.TRACKING_ONLY)
      localFiringDevice.gun.shots(1);
  }

  public void startFire(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    if ((!blockTrigger(localFiringDevice)) && 
      (!localFiringDevice.TRACKING_ONLY))
      localFiringDevice.gun.shots(-1);
  }

  private boolean blockTrigger(FiringDevice paramFiringDevice) {
    float f1 = paramFiringDevice.headYaw;
    float f2 = paramFiringDevice.gunPitch;
    switch (paramFiringDevice.id)
    {
    case 7:
      f1 += this.arms[1].headYaw;
      while (f1 >= 360.0F) {
        f1 -= 360.0F;
      }
      while (f1 < 0.0F) {
        f1 += 360.0F;
      }
      while (f2 >= 360.0F) {
        f2 -= 360.0F;
      }
      while (f2 < 0.0F) {
        f2 += 360.0F;
      }
      if ((f1 <= 175.0F) || (f1 >= 185.0F) || ((f2 >= 5.0F) && (f2 <= 355.0F))) break;
      return true;
    case 8:
      while (f1 >= 360.0F) {
        f1 -= 360.0F;
      }
      while (f1 < 0.0F) {
        f1 += 360.0F;
      }
      while (f2 >= 360.0F) {
        f2 -= 360.0F;
      }
      while (f2 < 0.0F) {
        f2 += 360.0F;
      }
      if (((f1 > 324.0F) || (f1 < 36.0F)) && (f2 < 32.0F)) {
        return true;
      }
      if ((f1 <= 145.0F) || (f1 >= 215.0F) || (f2 >= 35.0F)) break;
      return true;
    case 9:
      f1 += this.arms[2].headYaw;
      while (f1 >= 360.0F) {
        f1 -= 360.0F;
      }
      while (f1 < 0.0F) {
        f1 += 360.0F;
      }
      while (f2 >= 360.0F) {
        f2 -= 360.0F;
      }
      while (f2 < 0.0F) {
        f2 += 360.0F;
      }
      if ((f1 <= 170.0F) || (f1 >= 190.0F) || ((f2 >= 5.0F) && (f2 <= 355.0F))) break;
      return true;
    }

    return false;
  }

  public void continueFire(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    if (blockTrigger(localFiringDevice))
      stopFire(paramAim);
  }

  public void stopFire(Aim paramAim)
  {
    FiringDevice localFiringDevice = GetFiringDevice(paramAim);
    if (!localFiringDevice.TRACKING_ONLY)
      localFiringDevice.gun.shots(0);
  }

  static
  {
    cls = MBV2AA.class;
    Spawn.add(cls, new SPAWN());
  }

  public class FiringDevice
  {
    private int id;
    private Gun gun;
    private Aim aime;
    private float headYaw;
    private float gunPitch;
    private Point3d fireOffset;
    private Orient fireOrient;
    public int WEAPONS_MASK;
    public boolean TRACKING_ONLY;
    public float ATTACK_MAX_DISTANCE;
    public float ATTACK_MAX_RADIUS;
    public float ATTACK_MAX_HEIGHT;
    public int ATTACK_FAST_TARGETS;
    public float FAST_TARGETS_ANGLE_ERROR;
    public AnglesRange HEAD_YAW_RANGE;
    public float HEAD_STD_YAW;
    public float GUN_MIN_PITCH;
    public float GUN_STD_PITCH;
    public float GUN_MAX_PITCH;
    public float HEAD_MAX_YAW_SPEED;
    public float GUN_MAX_PITCH_SPEED;
    public float DELAY_AFTER_SHOOT;
    public float CHAINFIRE_TIME;
    public Class gunClass;

    public FiringDevice()
    {
    }
  }

  public static class SPAWN
    implements WagonSpawn
  {
    public Wagon wagonSpawn(Train paramTrain)
    {
      return new MBV2AA(paramTrain);
    }
  }
}