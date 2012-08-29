package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class FuelTank extends Bomb
{
  private float Fuel;
  private static Point3d p = new Point3d();

  public FuelTank()
  {
    Class localClass = getClass();
    fill(Property.floatValue(localClass, "massa", 0.0F));
  }

  protected void doExplosion(Actor paramActor, String paramString) {
    MsgDestroy.Post(Time.current(), this);
    this.pos.getTime(Time.current(), p);
    if (World.land().isWater(p.x, p.y))
      Explosions.WreckageDrop_Water(p);
  }

  protected void fill(float paramFloat)
  {
    setName("_fueltank_");
    this.jdField_M_of_type_Float = paramFloat;
    this.Fuel = (paramFloat * 0.9F);
  }

  public float getFuel(float paramFloat) {
    if (paramFloat > this.Fuel) paramFloat = this.Fuel;
    this.Fuel -= paramFloat;
    this.jdField_M_of_type_Float -= paramFloat;
    return paramFloat;
  }
}