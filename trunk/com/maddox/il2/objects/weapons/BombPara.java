package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.rts.Property;

public class BombPara extends Bomb
{
  public void start()
  {
    Loc localLoc = new Loc();
    Orient localOrient = new Orient();

    Class localClass = getClass();
    init(Property.floatValue(localClass, "kalibr", 0.082F), Property.floatValue(localClass, "massa", 6.8F));

    setOwner(this.pos.base(), false, false, false);
    this.pos.setBase(null, null, true);
    this.pos.setAbs(this.pos.getCurrent());

    this.pos.getAbs(localLoc);

    Paratrooper localParatrooper = new Paratrooper(getOwner(), getOwner().getArmy(), 255, localLoc, ((Aircraft)getOwner()).FM.Vwld);
    destroy();
  }

  static
  {
    Class localClass = BombPara.class;
    Property.set(localClass, "mesh", "3DO/Arms/Null/mono.sim");
    Property.set(localClass, "radius", 0.0F);
    Property.set(localClass, "power", 0.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.5F);
    Property.set(localClass, "massa", 100.0F);
  }
}