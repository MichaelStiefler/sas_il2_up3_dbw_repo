package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletGeneric;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.rts.Time;

public class MGunAircraftGeneric extends Gun
  implements BulletEmitter, BulletAimer
{
  private static final boolean DEBUG = false;
  protected BulletAircraftGeneric guardBullet;
  private static Bullet bullet;
  private int _index = -1;

  public String getDayProperties(String paramString)
  {
    if (paramString == null) return null;
    String str = "3DO/Effects/GunFire/";
    if (paramString.regionMatches(true, 0, str, 0, str.length())) {
      return "3DO/Effects/GunFireDay/" + paramString.substring(str.length());
    }
    return paramString;
  }

  public void createdProperties()
  {
    if ((this.prop.fireMesh != null) && (this.prop.fireMeshDay == null)) {
      this.prop.fireMeshDay = getDayProperties(this.prop.fireMesh);
    }
    if ((this.prop.fire != null) && (this.prop.fireDay == null)) {
      this.prop.fireDay = getDayProperties(this.prop.fire);
    }
    if ((this.prop.sprite != null) && (this.prop.spriteDay == null)) {
      this.prop.spriteDay = getDayProperties(this.prop.sprite);
    }
    super.createdProperties();
  }

  public String prop_fireMesh()
  {
    if (World.Sun().ToSun.z >= -0.22F) {
      return this.prop.fireMeshDay;
    }
    return this.prop.fireMesh;
  }
  public String prop_fire() {
    if (World.Sun().ToSun.z >= -0.22F) {
      return this.prop.fireDay;
    }
    return this.prop.fire;
  }
  public String prop_sprite() {
    if (World.Sun().ToSun.z >= -0.22F) {
      return this.prop.spriteDay;
    }
    return this.prop.sprite;
  }

  public void setConvDistance(float paramFloat1, float paramFloat2)
  {
    Point3d localPoint3d = this.pos.getRelPoint();
    Orient localOrient = new Orient();
    localOrient.set(this.pos.getRelOrient());

    float f1 = (float)Math.sqrt(localPoint3d.y * localPoint3d.y + paramFloat1 * paramFloat1);
    float f2 = (float)Math.toDegrees(Math.atan(-localPoint3d.y / paramFloat1));
    if (!this.prop.bUseHookAsRel) {
      f2 = 0.0F;
      f1 = paramFloat1;
    }

    float f3 = 0.0F; float f4 = 0.0F;
    float f5 = this.prop.bullet[0].speed; float f6 = 0.0F;

    while (f3 < f1) {
      f5 += this.bulletKV[0] * Time.tickConstLenFs() * 1.0F * BulletAircraftGeneric.fv(f5) / f5;
      f6 -= this.bulletAG[0] * Time.tickConstLenFs();
      f3 += f5 * Time.tickConstLenFs();
      f4 += f6 * Time.tickConstLenFs();
    }
    f4 += paramFloat2;
    f4 = (float)(f4 - localPoint3d.z);
    float f7 = (float)Math.toDegrees(Math.atan(f4 / f1));

    localOrient.setYPR(localOrient.azimut() + f2, localOrient.tangage() + f7, localOrient.kren());
    this.pos.setRel(localOrient);
  }

  public void init() {
    int i = this.prop.bullet.length;
    this.bulletAG = new float[i];
    this.bulletKV = new float[i];
    initRealisticGunnery();
  }

  public void initRealisticGunnery(boolean paramBoolean) {
    int i = this.prop.bullet.length;
    for (int j = 0; j < i; j++)
      if (paramBoolean) {
        this.bulletAG[j] = -10.0F;
        this.bulletKV[j] = (-(1000.0F * this.prop.bullet[j].kalibr / this.prop.bullet[j].massa));
      } else {
        this.bulletAG[j] = 0.0F;
        this.bulletKV[j] = 0.0F;
      }
  }

  public Bullet createNextBullet(Vector3d paramVector3d1, int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d2, long paramLong)
  {
    bullet = new BulletAircraftGeneric(paramVector3d1, paramInt, paramGunGeneric, paramLoc, paramVector3d2, paramLong);
    if ((!World.cur().diffCur.Realistic_Gunnery) && (isContainOwner(World.getPlayerAircraft())));
    return bullet;
  }

  public void loadBullets(int paramInt)
  {
    super.loadBullets(paramInt);
    resetGuard();
  }
  public void _loadBullets(int paramInt) {
    super._loadBullets(paramInt);
    resetGuard();
  }
  private void resetGuard() {
    this.guardBullet = null;
    BulletGeneric localBulletGeneric = Engine.cur.bulletList;
    while (localBulletGeneric != null) {
      if (((localBulletGeneric instanceof BulletAircraftGeneric)) && (localBulletGeneric.gun() == this))
        ((BulletAircraftGeneric)localBulletGeneric).bulletss = localBulletGeneric.hashCode();
      localBulletGeneric = localBulletGeneric.nextBullet;
    }
  }

  public int nextIndexBulletType()
  {
    this._index += 1;
    if (this._index == this.prop.bullet.length) this._index = 0;
    return this._index;
  }
}