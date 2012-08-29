package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombAB500 extends Bomb
{
  private long t1;

  public void start()
  {
    super.start();
    this.t1 = (Time.current() + 1000L * ()Math.max(this.delayExplosion, 3.0F) + World.Rnd().nextLong(-250L, 250L));
  }

  public void interpolateTick()
  {
    super.interpolateTick();
    if (this.t1 < Time.current()) doFireContaineds();
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (this.t1 > Time.current()) doFireContaineds();
    super.msgCollision(paramActor, paramString1, paramString2);
  }

  private void doFireContaineds()
  {
    Explosions.AirFlak(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), 1);
    Actor localActor = null;
    if (Actor.isValid(getOwner())) {
      localActor = getOwner();
    }
    Point3d localPoint3d = new Point3d(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
    Orient localOrient = new Orient();
    Vector3d localVector3d = new Vector3d();
    for (int i = 0; i < 74; i++) {
      localOrient.set(World.Rnd().nextFloat(0.0F, 360.0F), World.Rnd().nextFloat(-90.0F, 90.0F), World.Rnd().nextFloat(-180.0F, 180.0F));
      getSpeed(localVector3d);
      localVector3d.add(World.Rnd().nextDouble(-15.0D, 15.0D), World.Rnd().nextDouble(-15.0D, 15.0D), World.Rnd().nextDouble(-15.0D, 15.0D));
      BombSD4HL localBombSD4HL = new BombSD4HL();
      localBombSD4HL.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setUpdateEnable(true);
      localBombSD4HL.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localPoint3d, localOrient);
      localBombSD4HL.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
      localBombSD4HL.start();
      localBombSD4HL.setOwner(localActor, false, false, false);
      localBombSD4HL.setSpeed(localVector3d);
    }
    postDestroy();
  }

  static
  {
    Class localClass = BombAB500.class;
    Property.set(localClass, "mesh", "3DO/Arms/AB-500/mono.sim");
    Property.set(localClass, "radius", 1.0F);
    Property.set(localClass, "power", 0.15F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.18F);

    Property.set(localClass, "massa", 335.336F);
    Property.set(localClass, "sound", "weapon.bomb_std");
  }
}