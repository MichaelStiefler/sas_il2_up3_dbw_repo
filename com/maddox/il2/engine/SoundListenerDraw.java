package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundListener;
import java.util.HashMap;

class SoundListenerDraw extends ActorDraw
{
  private Acoustics ownerAcoustics = null;
  private static Vector3d Ahead = new Vector3d();
  private static Vector3d Up = new Vector3d();
  private boolean bInited = false;
  private static final int numBullets = 6;
  public static final double maxBulletDist = 30.0D;
  private static final double maxBulletDist2 = 900.0D;
  private BulletInfo[] bulletInfo;
  private BulletInfo[] biFree;
  private HashMap biMap;
  private final double bsRho = 0.0D;
  private final double rMax = 40.0D;

  public static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3d v = new Vector3d();
  private static Point3d dp = new Point3d();

  SoundListenerDraw()
  {
    this.bulletInfo = new BulletInfo[6];
    this.biFree = new BulletInfo[6];
    this.biMap = new HashMap();
  }

  public void init()
  {
    if (Config.isUSE_RENDER())
      for (int i = 0; i < 6; i++) {
        this.bulletInfo[i] = new BulletInfo();
        this.bulletInfo[i].sound = new SoundFX("objects.bullet");
        this.bulletInfo[i].sound.setAcoustics(Engine.worldAcoustics());
        this.bulletInfo[i].bullet = null;
        this.bulletInfo[i].rho = 900.0D;
      }
  }

  private void setBulletPos(BulletInfo paramBulletInfo)
  {
    Point3d localPoint3d = paramBulletInfo.bullet.p1;
    double d = localPoint3d.distance(p);
    if (d > 40.0D) {
      dp.sub(localPoint3d, p);
      dp.scale(40.0D / d);
      dp.add(p);
      paramBulletInfo.sound.setPosition(dp.jdField_x_of_type_Double, dp.jdField_y_of_type_Double, dp.jdField_z_of_type_Double);
    } else {
      paramBulletInfo.sound.setPosition(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double, localPoint3d.jdField_z_of_type_Double);
    }
    paramBulletInfo.sound.setControl(202, paramBulletInfo.km);
  }

  private void renderBullets()
  {
    BulletGeneric localBulletGeneric1 = Engine.cur.bulletList;
    int i = 0;

    for (int j = 0; j < 6; j++) {
      BulletInfo localBulletInfo1 = this.bulletInfo[j];
      if ((localBulletInfo1.bullet != null) && 
        (localBulletInfo1.bullet.owner == null) && (localBulletInfo1.bullet.gun == null)) localBulletInfo1.bullet = null;

      if ((localBulletInfo1.bullet == null) || (!localBulletInfo1.sound.isPlaying())) {
        localBulletInfo1.rho = 900.0D;
        this.biFree[(i++)] = localBulletInfo1;
        localBulletInfo1.bullet = null;
      }
      if (localBulletInfo1.bullet != null) {
        this.biMap.put(localBulletInfo1.bullet, localBulletInfo1);
      }
    }

    if (!Main3D.cur3D().isViewOutside()) return;

    if (i > 0) {
      while (localBulletGeneric1 != null) {
        if ((localBulletGeneric1.owner != null) || (localBulletGeneric1.gun != null)) {
          double d = localBulletGeneric1.p1.distanceSquared(p);
          if ((d < 900.0D) && 
            (!this.biMap.containsKey(localBulletGeneric1))) {
            for (int n = 0; n < i; n++) {
              if (this.biFree[n].rho > d) {
                BulletGeneric localBulletGeneric2 = this.biFree[(i - 1)].bullet;
                if (localBulletGeneric2 != null) {
                  if (this.biMap.containsKey(localBulletGeneric2)) this.biMap.remove(localBulletGeneric2);
                  for (int i1 = i - 1; i1 > n; i1--) {
                    this.biFree[i1].bullet = this.biFree[(i1 - 1)].bullet;
                    this.biFree[i1].rho = this.biFree[(i1 - 1)].rho;
                  }
                }
                this.biFree[n].rho = d;
                this.biFree[n].bullet = localBulletGeneric1;
                this.biMap.put(localBulletGeneric1, this.biFree[n]);
                break;
              }
            }
          }
        }

        localBulletGeneric1 = localBulletGeneric1.nextBullet;
      }
    }

    for (int k = 0; k < 6; k++) {
      BulletInfo localBulletInfo2 = this.bulletInfo[k];
      if (localBulletInfo2.bullet == null) continue; setBulletPos(localBulletInfo2);
    }

    for (int m = 0; m < i; m++) {
      BulletInfo localBulletInfo3 = this.biFree[m];
      if (localBulletInfo3.bullet != null) {
        float f1 = localBulletInfo3.bullet.properties().massa; float f2 = 0.2F;

        if (f1 > 0.09F) {
          f2 = (float)Math.log(f1) / (float)Math.log(10.0D) + 1.0F;
          if (f2 < 0.0F) f2 = 0.0F;
          else if (f2 > 3.0F) f2 = 3.0F;
          f2 = 4.0F - f2;
        }
        localBulletInfo3.km = f2;
        localBulletInfo3.sound.setUsrFlag(f2 <= 0.4F ? 0 : 1);
        localBulletInfo3.sound.play();
        setBulletPos(localBulletInfo3);
      }
      this.biFree[m] = null;
    }

    this.biMap.clear();
  }

  public int preRender(Actor paramActor)
  {
    if (!this.bInited) {
      if (Config.cur.isSoundUse()) {
        SoundListener.setAcoustics(Engine.worldAcoustics());
      } else {
        paramActor.postDestroy();
        return 0;
      }
      this.bInited = true;
    }

    int i = 0;

    Actor localActor1 = paramActor.actorAcoustics();
    Acoustics localAcoustics = null;
    if (localActor1 != null) localAcoustics = localActor1.acoustics; else
      localAcoustics = Engine.worldAcoustics();
    if (this.ownerAcoustics != localAcoustics) {
      this.ownerAcoustics = localAcoustics;
      SoundListener.setAcoustics(localAcoustics);
      Main3D localMain3D = Main3D.cur3D();
      if ((localMain3D != null) && 
        (localMain3D.clouds != null) && 
        (localMain3D.clouds.sound != null)) localMain3D.clouds.sound.setAcoustics(localAcoustics);

    }

    if (localActor1 != null) {
      localActor1.pos.getRender(p, o);
      Ahead.set(1.0D, 0.0D, 0.0D);
      Up.set(0.0D, 0.0D, 1.0D);
      o.transform(Ahead);
      o.transform(Up);
      localAcoustics.setOrientation((float)Ahead.jdField_x_of_type_Double, (float)Ahead.jdField_y_of_type_Double, (float)Ahead.jdField_z_of_type_Double, (float)Up.jdField_x_of_type_Double, (float)Up.jdField_y_of_type_Double, (float)Up.jdField_z_of_type_Double);
      i = 1;
    }

    paramActor.pos.getRender(p, o);
    double d = 0.0D;
    d = Engine.land().HQ(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double);
    Engine.worldAcoustics().setPosition(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double, d);

    Ahead.set(1.0D, 0.0D, 0.0D);
    Up.set(0.0D, 0.0D, 1.0D);
    o.transform(Ahead);
    o.transform(Up);

    ((ActorSoundListener)paramActor).absPos.set(p);

    SoundListener.setOrientation((float)Ahead.jdField_x_of_type_Double, (float)Ahead.jdField_y_of_type_Double, (float)Ahead.jdField_z_of_type_Double, (float)Up.jdField_x_of_type_Double, (float)Up.jdField_y_of_type_Double, (float)Up.jdField_z_of_type_Double);
    SoundListener.setPosition(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double, p.jdField_z_of_type_Double);
    if (i != 0) localAcoustics.setPosition(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double, p.jdField_z_of_type_Double);

    if ((Config.isUSE_RENDER()) && 
      (Main3D.cur3D().clouds != null)) Main3D.cur3D().clouds.soundUpdate(p.jdField_z_of_type_Double);

    if (((ActorSoundListener)paramActor).isUseBaseSpeed()) {
      Actor localActor2 = paramActor.pos.base();
      if (localActor2 != null) localActor2 = localActor2.pos.base();
      if (localActor2 == null) paramActor.getSpeed(v); else
        localActor2.getSpeed(v);
    } else {
      paramActor.getSpeed(v);
    }

    SoundListener.setVelocity((float)v.jdField_x_of_type_Double, (float)v.jdField_y_of_type_Double, (float)v.jdField_z_of_type_Double);

    if ((Config.isUSE_RENDER()) && (!Time.isPaused())) renderBullets();

    return 0;
  }

  public void render(Actor paramActor)
  {
  }

  public void destroy()
  {
  }

  class BulletInfo
  {
    public SoundFX sound;
    public BulletGeneric bullet;
    public double rho;
    public float km;

    BulletInfo()
    {
    }
  }
}