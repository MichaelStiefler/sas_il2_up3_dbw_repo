package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Property;

public abstract class TorpedoApparatus extends Gun
  implements BulletAimer
{
  protected Bomb bomb;
  protected HookNamed hook;
  protected Class bulletClass = null;

  public void loadBullets(int paramInt)
  {
    bullets(paramInt);
  }

  public void setBulletClass(Class paramClass)
  {
    this.bulletClass = paramClass;
  }

  public void doStartBullet(double paramDouble)
  {
    newBomb();
    if (this.bomb == null) return;
    this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setUpdateEnable(true);
    this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.resetAsBase();

    if (this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().z > 0.0D)
    {
      Eff3DActor.New(getOwner(), this.hook, null, 1.0F, "3DO/Effects/Fireworks/20_SmokeBoiling.eff", -1.0F);

      Eff3DActor.New(getOwner(), this.hook, null, 1.0F, "3DO/Effects/Fireworks/20_SparksP.eff", -1.0F);
    }
    this.bomb.start();
  }

  public void shots(int paramInt)
  {
    doStartBullet(0.0D);
  }

  public void doEffects(boolean paramBoolean)
  {
  }

  private void newBomb()
  {
    try {
      this.bomb = ((Bomb)this.bulletClass.newInstance());
      this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(getOwner(), this.hook, false);

      this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.resetAsBase();
      this.bomb.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setUpdateEnable(false);
    } catch (Exception localException) {
    }
  }

  public void set(Actor paramActor, String paramString, Loc paramLoc) {
    set(paramActor, paramString);
  }

  public void set(Actor paramActor, String paramString1, String paramString2)
  {
    set(paramActor, paramString1);
  }

  public void set(Actor paramActor, String paramString)
  {
    setOwner(paramActor);
    Class localClass = getClass();

    this.bulletClass = ((Class)Property.value(localClass, "bulletClass", null));

    setBulletClass(this.bulletClass);

    this.hook = ((HookNamed)paramActor.findHook(paramString));

    paramActor.interpPut(this.interpolater, null, -1L, null);
  }

  public float TravelTime(Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    float f1 = (float)paramPoint3d1.distance(paramPoint3d2);
    Class localClass1 = getClass();
    Class localClass2 = (Class)Property.value(localClass1, "bulletClass", null);
    float f2 = Property.floatValue(localClass2, "velocity", 1.0F);
    float f3 = Property.floatValue(localClass2, "traveltime", 1.0F);
    if (f1 > f2 * f3) {
      return -1.0F;
    }
    return f1 / f2;
  }

  public boolean FireDirection(Point3d paramPoint3d1, Point3d paramPoint3d2, Vector3d paramVector3d)
  {
    float f1 = (float)paramPoint3d1.distance(paramPoint3d2);
    Class localClass1 = getClass();
    Class localClass2 = (Class)Property.value(localClass1, "bulletClass", null);
    float f2 = Property.floatValue(localClass2, "velocity", 1.0F);
    float f3 = Property.floatValue(localClass2, "traveltime", 1.0F);
    if (f1 > f2 * f3) {
      return false;
    }
    paramVector3d.set(paramPoint3d2);
    paramVector3d.sub(paramPoint3d1);
    paramVector3d.scale(1.0F / f1);
    return true;
  }

  public GunProperties createProperties()
  {
    GunProperties localGunProperties = new GunProperties();
    localGunProperties.weaponType = 16;

    return localGunProperties;
  }
}