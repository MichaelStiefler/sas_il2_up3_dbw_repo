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
      paramBulletInfo.sound.setPosition(dp.x, dp.y, dp.z);
    } else {
      paramBulletInfo.sound.setPosition(localPoint3d.x, localPoint3d.y, localPoint3d.z);
    }
    paramBulletInfo.sound.setControl(202, paramBulletInfo.km);
  }

  private void renderBullets()
  {
    BulletGeneric localBulletGeneric1 = Engine.cur.bulletList;
    int i = 0;
    BulletInfo localBulletInfo;
    for (int j = 0; j < 6; j++) {
      localBulletInfo = this.bulletInfo[j];
      if ((localBulletInfo.bullet != null) && 
        (localBulletInfo.bullet.owner == null) && (localBulletInfo.bullet.gun == null)) localBulletInfo.bullet = null;

      if ((localBulletInfo.bullet == null) || (!localBulletInfo.sound.isPlaying())) {
        localBulletInfo.rho = 900.0D;
        this.biFree[(i++)] = localBulletInfo;
        localBulletInfo.bullet = null;
      }
      if (localBulletInfo.bullet != null) {
        this.biMap.put(localBulletInfo.bullet, localBulletInfo);
      }
    }

    if (!Main3D.cur3D().isViewOutside()) return;

    if (i > 0) {
      while (localBulletGeneric1 != null) {
        if ((localBulletGeneric1.owner != null) || (localBulletGeneric1.gun != null)) {
          double d = localBulletGeneric1.p1.distanceSquared(p);
          if ((d < 900.0D) && 
            (!this.biMap.containsKey(localBulletGeneric1))) {
            for (int m = 0; m < i; m++) {
              if (this.biFree[m].rho > d) {
                BulletGeneric localBulletGeneric2 = this.biFree[(i - 1)].bullet;
                if (localBulletGeneric2 != null) {
                  if (this.biMap.containsKey(localBulletGeneric2)) this.biMap.remove(localBulletGeneric2);
                  for (int n = i - 1; n > m; n--) {
                    this.biFree[n].bullet = this.biFree[(n - 1)].bullet;
                    this.biFree[n].rho = this.biFree[(n - 1)].rho;
                  }
                }
                this.biFree[m].rho = d;
                this.biFree[m].bullet = localBulletGeneric1;
                this.biMap.put(localBulletGeneric1, this.biFree[m]);
                break;
              }
            }
          }
        }

        localBulletGeneric1 = localBulletGeneric1.nextBullet;
      }
    }

    for (int k = 0; k < 6; k++) {
      localBulletInfo = this.bulletInfo[k];
      if (localBulletInfo.bullet == null) continue; setBulletPos(localBulletInfo);
    }

    for (k = 0; k < i; k++) {
      localBulletInfo = this.biFree[k];
      if (localBulletInfo.bullet != null) {
        float f1 = localBulletInfo.bullet.properties().massa; float f2 = 0.2F;

        if (f1 > 0.09F) {
          f2 = (float)Math.log(f1) / (float)Math.log(10.0D) + 1.0F;
          if (f2 < 0.0F) f2 = 0.0F;
          else if (f2 > 3.0F) f2 = 3.0F;
          f2 = 4.0F - f2;
        }
        localBulletInfo.km = f2;
        localBulletInfo.sound.setUsrFlag(f2 <= 0.4F ? 0 : 1);
        localBulletInfo.sound.play();
        setBulletPos(localBulletInfo);
      }
      this.biFree[k] = null;
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
      localAcoustics.setOrientation((float)Ahead.x, (float)Ahead.y, (float)Ahead.z, (float)Up.x, (float)Up.y, (float)Up.z);
      i = 1;
    }

    paramActor.pos.getRender(p, o);
    double d = 0.0D;
    d = Engine.land().HQ(p.x, p.y);
    Engine.worldAcoustics().setPosition(p.x, p.y, d);

    Ahead.set(1.0D, 0.0D, 0.0D);
    Up.set(0.0D, 0.0D, 1.0D);
    o.transform(Ahead);
    o.transform(Up);

    ((ActorSoundListener)paramActor).absPos.set(p);

    SoundListener.setOrientation((float)Ahead.x, (float)Ahead.y, (float)Ahead.z, (float)Up.x, (float)Up.y, (float)Up.z);
    SoundListener.setPosition(p.x, p.y, p.z);
    if (i != 0) localAcoustics.setPosition(p.x, p.y, p.z);

    if ((Config.isUSE_RENDER()) && 
      (Main3D.cur3D().clouds != null)) Main3D.cur3D().clouds.soundUpdate(p.z);

    if (((ActorSoundListener)paramActor).isUseBaseSpeed()) {
      Actor localActor2 = paramActor.pos.base();
      if (localActor2 != null) localActor2 = localActor2.pos.base();
      if (localActor2 == null) paramActor.getSpeed(v); else
        localActor2.getSpeed(v);
    } else {
      paramActor.getSpeed(v);
    }

    SoundListener.setVelocity((float)v.x, (float)v.y, (float)v.z);

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