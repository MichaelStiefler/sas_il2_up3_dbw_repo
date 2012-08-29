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
    if ((this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.fireMesh != null) && (this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.fireMeshDay == null)) {
      this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.fireMeshDay = getDayProperties(this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.fireMesh);
    }
    if ((this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.fire != null) && (this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.fireDay == null)) {
      this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.fireDay = getDayProperties(this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.fire);
    }
    if ((this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.sprite != null) && (this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.spriteDay == null)) {
      this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.spriteDay = getDayProperties(this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.sprite);
    }
    super.createdProperties();
  }

  public String prop_fireMesh()
  {
    if (World.Sun().ToSun.jdField_z_of_type_Float >= -0.22F) {
      return this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.fireMeshDay;
    }
    return this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.fireMesh;
  }
  public String prop_fire() {
    if (World.Sun().ToSun.jdField_z_of_type_Float >= -0.22F) {
      return this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.fireDay;
    }
    return this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.fire;
  }
  public String prop_sprite() {
    if (World.Sun().ToSun.jdField_z_of_type_Float >= -0.22F) {
      return this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.spriteDay;
    }
    return this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.sprite;
  }

  public void setConvDistance(float paramFloat1, float paramFloat2)
  {
    Point3d localPoint3d = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRelPoint();
    Orient localOrient = new Orient();
    localOrient.set(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRelOrient());

    float f1 = (float)Math.sqrt(localPoint3d.jdField_y_of_type_Double * localPoint3d.jdField_y_of_type_Double + paramFloat1 * paramFloat1);
    float f2 = (float)Math.toDegrees(Math.atan(-localPoint3d.jdField_y_of_type_Double / paramFloat1));
    if (!this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bUseHookAsRel) {
      f2 = 0.0F;
      f1 = paramFloat1;
    }

    float f3 = 0.0F; float f4 = 0.0F;
    float f5 = this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[0].speed; float f6 = 0.0F;

    while (f3 < f1) {
      f5 += this.jdField_bulletKV_of_type_ArrayOfFloat[0] * Time.tickConstLenFs() * 1.0F * BulletAircraftGeneric.fv(f5) / f5;
      f6 -= this.jdField_bulletAG_of_type_ArrayOfFloat[0] * Time.tickConstLenFs();
      f3 += f5 * Time.tickConstLenFs();
      f4 += f6 * Time.tickConstLenFs();
    }
    f4 += paramFloat2;
    f4 = (float)(f4 - localPoint3d.z);
    float f7 = (float)Math.toDegrees(Math.atan(f4 / f1));

    localOrient.setYPR(localOrient.azimut() + f2, localOrient.tangage() + f7, localOrient.kren());
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setRel(localOrient);
  }

  public void init() {
    int i = this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet.length;
    this.jdField_bulletAG_of_type_ArrayOfFloat = new float[i];
    this.jdField_bulletKV_of_type_ArrayOfFloat = new float[i];
    initRealisticGunnery();
  }

  public void initRealisticGunnery(boolean paramBoolean) {
    int i = this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet.length;
    for (int j = 0; j < i; j++)
      if (paramBoolean) {
        this.jdField_bulletAG_of_type_ArrayOfFloat[j] = -10.0F;
        this.jdField_bulletKV_of_type_ArrayOfFloat[j] = (-(1000.0F * this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[j].kalibr / this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet[j].massa));
      } else {
        this.jdField_bulletAG_of_type_ArrayOfFloat[j] = 0.0F;
        this.jdField_bulletKV_of_type_ArrayOfFloat[j] = 0.0F;
      }
  }

  public Bullet createNextBullet(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    bullet = new BulletAircraftGeneric(paramInt, paramGunGeneric, paramLoc, paramVector3d, paramLong);
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
    if (this._index == this.jdField_prop_of_type_ComMaddoxIl2EngineGunProperties.bullet.length) this._index = 0;
    return this._index;
  }
}