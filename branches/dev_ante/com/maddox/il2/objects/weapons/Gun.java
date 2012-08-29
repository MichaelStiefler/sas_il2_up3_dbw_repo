package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetGunner;
import com.maddox.rts.Time;

public class Gun extends GunGeneric
  implements BulletEmitter
{
  private static Loc loc = new Loc();
  private static Vector3d v = new Vector3d();
  private static Vector3d v1 = new Vector3d();
  private static Vector3d v2 = new Vector3d();
  private static Point3d p1 = new Point3d();

  public void initRealisticGunnery()
  {
    boolean bool = (!isContainOwner(World.getPlayerAircraft())) || (World.cur().diffCur.Realistic_Gunnery);

    initRealisticGunnery(bool);
  }
  public int nextIndexBulletType() {
    return 0;
  }
  public Bullet createNextBullet(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong) {
    return new Bullet(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong);
  }

  public void doStartBullet(double paramDouble) {
    int i = nextIndexBulletType();

    long l = Time.tick() + ()(Time.tickLenFms() * paramDouble);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getTime(l, loc);
    Loc localLoc = loc;
    Orient localOrient;
    if (this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.maxDeltaAngle > 0.0F) {
      localOrient = loc.getOrient();
      float f1 = World.Rnd().nextFloat(-this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.maxDeltaAngle, this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.maxDeltaAngle);
      float f2 = World.Rnd().nextFloat(-this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.maxDeltaAngle, this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.maxDeltaAngle);
      localOrient.increment(f1, f2, 0.0F);
    } else {
      localOrient = localLoc.getOrient();
    }
    v1.set(1.0D, 0.0D, 0.0D);
    localOrient.transform(v1);
    v1.scale(this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[i].speed);
    Actor localActor = getOwner();
    if ((localActor instanceof Aircraft)) {
      v2.set(v1);

      v2.scale(this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[i].massa * this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bulletsCluster);
      ((Aircraft)localActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel.gunPulse(v2);

      localActor.getSpeed(v);
      v1.add(v);
      if (localActor == World.getPlayerAircraft()) {
        World.cur().scoreCounter.bulletsFire += this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bulletsCluster;

        if ((World.cur().diffCur.Realistic_Gunnery) && ((((Aircraft)localActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel instanceof RealFlightModel)))
        {
          localObject = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRel();
          double d1;
          if (Math.abs(((Loc)localObject).getPoint().jdField_y_of_type_Double) < 0.5D) {
            d1 = this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[i].massa * this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[i].speed;
            v.jdField_x_of_type_Double = (World.Rnd().nextDouble(-20.0D, 20.0D) * d1);
            v.jdField_y_of_type_Double = (World.Rnd().nextDouble(-100.0D, 200.0D) * d1);
            v.jdField_z_of_type_Double = (World.Rnd().nextDouble(-200.0D, 200.0D) * d1);
            v.scale(0.3D);
            ((RealFlightModel)((Aircraft)localActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).gunMomentum(v, false);
          } else {
            d1 = this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[i].massa * this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bulletsCluster * this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.shotFreq;
            v2.set(-1.0D, 0.0D, 0.0D);
            ((Loc)localObject).transform(v2);
            double d3 = 0.45D * Math.sqrt(Math.sqrt(this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[i].massa));
            d3 = 64.0D * World.Rnd().nextDouble(1.0D - d3, 1.0D + d3);
            v2.scale(d3 * v1.length() * d1);
            v.cross(((Loc)localObject).getPoint(), v2);
            v.jdField_y_of_type_Double *= 0.1000000014901161D;
            v.jdField_z_of_type_Double *= 0.5D;
            v.scale(0.3D);
            ((RealFlightModel)((Aircraft)localActor).jdField_FM_of_type_ComMaddoxIl2FmFlightModel).gunMomentum(v, true);
          }
        }
      }
    }
    else if (getSpeed(v) > 0.0D) {
      v1.add(v);
    }
    if (((localActor instanceof NetGunner)) && (World.isPlayerGunner())) {
      World.cur().scoreCounter.bulletsFire += this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bulletsCluster;
    }
    Object localObject = createNextBullet(i, this, localLoc, v1, Time.current() + (int)(this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[i].timeLife * 1000.0F));
    ((Bullet)localObject).move((float)((1.0D - paramDouble) * Time.tickLenFs()));
    ((Bullet)localObject).bMoved = true;
    localObject.jdField_flags_of_type_Int |= 4096;
    if ((Config.isUSE_RENDER()) && 
      (this.bulletNum % this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.traceFreq == 0)) {
      localObject.jdField_flags_of_type_Int |= -2147483648;
      if (this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[i].traceTrail != null) {
        Camera3D localCamera3D = Main3D.cur3D().camera3D;
        if (Actor.isValid(localCamera3D)) {
          double d2 = 1000000.0D;
          Point3d localPoint3d1 = localLoc.getPoint();
          v1.scale(this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[i].timeLife);
          p1.add(localPoint3d1, v1);
          Point3d localPoint3d2 = localCamera3D.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
          double d4 = p1.jdField_x_of_type_Double - localPoint3d1.jdField_x_of_type_Double;
          double d5 = p1.jdField_y_of_type_Double - localPoint3d1.jdField_y_of_type_Double;
          double d6 = p1.jdField_z_of_type_Double - localPoint3d1.jdField_z_of_type_Double;
          double d7 = d4 * d4 + d5 * d5 + d6 * d6;
          double d8 = ((localPoint3d2.jdField_x_of_type_Double - localPoint3d1.jdField_x_of_type_Double) * d4 + (localPoint3d2.jdField_y_of_type_Double - localPoint3d1.jdField_y_of_type_Double) * d5 + (localPoint3d2.jdField_z_of_type_Double - localPoint3d1.jdField_z_of_type_Double) * d6) / d7;
          double d9;
          double d10;
          if ((d8 > 0.0D) && (d8 < 1.0D)) {
            d9 = localPoint3d1.jdField_x_of_type_Double + d8 * d4;
            d10 = localPoint3d1.jdField_y_of_type_Double + d8 * d5;
            double d11 = localPoint3d1.jdField_z_of_type_Double + d8 * d6;
            double d12 = (d9 - localPoint3d2.jdField_x_of_type_Double) * (d9 - localPoint3d2.jdField_x_of_type_Double) + (d10 - localPoint3d2.jdField_y_of_type_Double) * (d10 - localPoint3d2.jdField_y_of_type_Double) + (d11 - localPoint3d2.jdField_z_of_type_Double) * (d11 - localPoint3d2.jdField_z_of_type_Double);
            if (d12 > d2)
              return;
          } else {
            d9 = (p1.jdField_x_of_type_Double - localPoint3d2.jdField_x_of_type_Double) * (p1.jdField_x_of_type_Double - localPoint3d2.jdField_x_of_type_Double) + (p1.jdField_y_of_type_Double - localPoint3d2.jdField_y_of_type_Double) * (p1.jdField_y_of_type_Double - localPoint3d2.jdField_y_of_type_Double) + (p1.jdField_z_of_type_Double - localPoint3d2.jdField_z_of_type_Double) * (p1.jdField_z_of_type_Double - localPoint3d2.jdField_z_of_type_Double);
            d10 = (localPoint3d1.jdField_x_of_type_Double - localPoint3d2.jdField_x_of_type_Double) * (localPoint3d1.jdField_x_of_type_Double - localPoint3d2.jdField_x_of_type_Double) + (localPoint3d1.jdField_y_of_type_Double - localPoint3d2.jdField_y_of_type_Double) * (localPoint3d1.jdField_y_of_type_Double - localPoint3d2.jdField_y_of_type_Double) + (localPoint3d1.jdField_z_of_type_Double - localPoint3d2.jdField_z_of_type_Double) * (localPoint3d1.jdField_z_of_type_Double - localPoint3d2.jdField_z_of_type_Double);
            if ((d10 > d2) && (d9 > d2))
              return;
          }
          ((Bullet)localObject).effTrail = Eff3DActor.NewPosMove(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(), 1.0F, this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[i].traceTrail, -1.0F);
        }
      }
    }
  }

  private boolean nameEQ(HierMesh paramHierMesh, int paramInt1, int paramInt2)
  {
    if (paramHierMesh == null) return false;

    paramHierMesh.setCurChunk(paramInt1); String str1 = paramHierMesh.chunkName();
    paramHierMesh.setCurChunk(paramInt2); String str2 = paramHierMesh.chunkName();

    int j = Math.min(str1.length(), str2.length());
    for (int i = 0; i < j; i++) {
      int k = str1.charAt(i);
      if (k == 95) return true;
      if (k != str2.charAt(i)) return false;
    }
    return true;
  }

  public BulletEmitter detach(HierMesh paramHierMesh, int paramInt) {
    if (isDestroyed())
      return GunEmpty.get();
    if ((paramInt == -1) || (nameEQ(paramHierMesh, paramInt, this.chunkIndx))) {
      destroy();
      return GunEmpty.get();
    }
    return this;
  }

  public float TravelTime(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    float f = (float)paramPoint3d1.distance(paramPoint3d2);
    if ((f < this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.aimMinDist) || (f > this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.aimMaxDist)) {
      return -1.0F;
    }
    return f / this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[0].speed;
  }

  public boolean FireDirection(Point3d paramPoint3d1, Point3d paramPoint3d2, Vector3d paramVector3d)
  {
    float f = (float)paramPoint3d1.distance(paramPoint3d2);
    if ((f < this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.aimMinDist) || (f > this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.aimMaxDist)) {
      return false;
    }
    paramVector3d.set(paramPoint3d2);
    paramVector3d.sub(paramPoint3d1);
    paramVector3d.scale(1.0F / f);
    return true;
  }
}