package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.MsgShot;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletGeneric;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Time;

public class Bullet extends BulletGeneric
{
  protected static Point3d tmpP = new Point3d();
  protected static Vector3f vf = new Vector3f();

  private static Loc l1 = new Loc();
  private static Loc l2 = new Loc();
  private static Loc l3 = new Loc();
  private static Matrix4d m1 = new Matrix4d();
  private static Matrix4d m2 = new Matrix4d();
  private static int[] resChunk = new int[1];

  public void move(float paramFloat)
  {
    super.move(paramFloat);
  }

  public void timeOut()
  {
  }

  public void showExplosion(Actor paramActor, Point3d paramPoint3d, BulletProperties paramBulletProperties, double paramDouble)
  {
    Explosions.generateExplosion(paramActor, paramPoint3d, paramBulletProperties.power, paramBulletProperties.powerType, paramBulletProperties.powerRadius, paramDouble);
  }

  public boolean collided(Actor paramActor, String paramString, double paramDouble) {
    tmpP.interpolate(this.p0, this.p1, paramDouble);

    if (((this.flags & 0x2000) != 0) && (Actor.isValid(this.owner)) && (Actor.isValid(paramActor)) && (this.owner.getArmy() != paramActor.getArmy()))
    {
      if ((paramActor instanceof ActorMesh)) {
        localObject1 = ((ActorMesh)paramActor).mesh();
        l1.set(tmpP);
        paramActor.pos.getTime(Time.current(), l2);
        l3.sub(l2, l1);
        l3.getMatrix(m2);
        int i = ((Mesh)localObject1).hookFaceCollisFind(m2, resChunk, m1);
        if (i != -1) {
          ((Mesh)localObject1).hookFaceMatrix(i, resChunk[0], m1);
          l2.getMatrix(m2);
          m2.mul(m1);
          tmpP.set(m2.m03, m2.m13, m2.m23);
          if ((localObject1 instanceof HierMesh)) {
            ((HierMesh)localObject1).setCurChunk(resChunk[0]);
            paramString = ((HierMesh)localObject1).chunkName();
          }
        }
      }
      this.speed.scale(2.0D);
    }

    vf.set(this.speed);
    Object localObject1 = properties();
    if ((this.owner != null) && ((this.owner == World.getPlayerAircraft()) || (this.owner == World.getPlayerGunner())) && (!(paramActor instanceof ActorLand)))
    {
      World.cur().scoreCounter.bulletsHit += this.gun.prop.bulletsCluster;
      if ((paramActor instanceof Aircraft))
        World.cur().scoreCounter.bulletsHitAir += this.gun.prop.bulletsCluster;
    }
    float f1 = ((BulletProperties)localObject1).massa;
    Object localObject2 = null;
    Shot localShot;
    if (((BulletProperties)localObject1).cumulativePower > 0.0F) {
      for (int j = 0; j < this.gun.prop.bulletsCluster; j++) {
        localShot = MsgShot.send(paramActor, paramString, tmpP, vf, f1, this.owner, ((BulletProperties)localObject1).cumulativePower, 1, paramDouble);

        if (!Actor.isValid(paramActor)) return true;
        if (j != 0) continue; localObject2 = localShot;
      }
      Explosions.generateShot(paramActor, localObject2);
    } else {
      double d = this.speed.length();
      float f2 = (float)(f1 * d * d / 2.0D);
      for (int k = 0; k < this.gun.prop.bulletsCluster; k++) {
        if (((BulletProperties)localObject1).powerRadius == 0.0F) {
          localShot = MsgShot.send(paramActor, paramString, tmpP, vf, f1, this.owner, f2, ((BulletProperties)localObject1).power == 0.0F ? 2 : 3, paramDouble);
        }
        else {
          localShot = MsgShot.send(paramActor, paramString, tmpP, vf, f1, this.owner, f2, 0, paramDouble);
        }

        if (!Actor.isValid(paramActor)) return true;
        if (k == 0) localObject2 = localShot;
        if ((((BulletProperties)localObject1).power > 0.0F) && (((BulletProperties)localObject1).powerRadius > 0.0F)) {
          MsgExplosion.send(paramActor, paramString, tmpP, this.owner, f1, ((BulletProperties)localObject1).power + 0.03F * f1, ((BulletProperties)localObject1).powerType, ((BulletProperties)localObject1).powerRadius);
        }
      }

      Explosions.generateShot(paramActor, localObject2);
      if (((BulletProperties)localObject1).power > 0.0F) {
        showExplosion(paramActor, tmpP, (BulletProperties)localObject1, paramDouble);
      }
    }
    return true;
  }

  public Bullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    super(paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong);
    if ((this.owner != null) && ((this.owner.equals(World.getPlayerAircraft())) || (this.owner.isContainOwner(World.getPlayerAircraft()))) && (!World.cur().diffCur.Realistic_Gunnery))
    {
      this.flags |= 1073741824;
    }
  }
}