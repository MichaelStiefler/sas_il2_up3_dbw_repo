package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Chute;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombMk24Flare extends Bomb
{
  private Chute chute = null;
  private boolean bOnChute = false;
  private static Vector3d v3d = new Vector3d();
  private float ttcurTM;
  private float ttexpTM;

  protected boolean haveSound()
  {
    return false;
  }

  public void start() {
    super.start();
    this.ttcurTM = World.Rnd().nextFloat(0.5F, 1.75F);
    this.ttexpTM = World.Rnd().nextFloat(11.2F, 17.75F);
  }

  public void destroy() {
    if (this.chute != null)
      this.chute.destroy();
    super.destroy();
  }

  public void interpolateTick() {
    super.interpolateTick();
    if (this.bOnChute) {
      getSpeed(v3d);
      v3d.scale(0.97D);
      if (v3d.z < -2.0D)
        v3d.z += 1.1F * Time.tickConstLenFs();
      setSpeed(v3d);
    } else if (this.curTm > this.ttcurTM) {
      this.bOnChute = true;
      this.chute = new Chute(this);
      this.chute.collide(false);
      this.chute.mesh().setScale(0.5F);
      this.chute.pos.setRel(new Point3d(0.5D, 0.0D, 0.0D), new Orient(0.0F, 90.0F, 0.0F));
    }
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (((paramActor instanceof ActorLand)) && (this.chute != null))
      this.chute.landing();
    super.msgCollision(paramActor, paramString1, paramString2);
  }

  static
  {
    Class localClass = BombMk24Flare.class;

    Property.set(localClass, "mesh", "3DO/Arms/Mk24_Flare/mono.sim");
    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 1.0F));
    Property.set(localClass, "emitLen", 250.0F);
    Property.set(localClass, "emitMax", 10.0F);
    Property.set(localClass, "radius", 75.0F);
    Property.set(localClass, "power", 0.0F);
    Property.set(localClass, "powerType", 1);
    Property.set(localClass, "kalibr", 0.2F);
    Property.set(localClass, "massa", 8.0F);
    Property.set(localClass, "sound", "weapon.bomb_phball");
  }
}