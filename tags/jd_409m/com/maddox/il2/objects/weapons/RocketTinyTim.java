package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class RocketTinyTim extends Rocket
{
  private long tEStart;
  private Orient tOrient = new Orient();

  public RocketTinyTim()
  {
    this.flags &= -33;
  }

  public boolean interpolateStep()
  {
    if (this.tEStart > 0L) {
      if (Time.current() > this.tEStart) {
        this.tEStart = -1L;
        setThrust(222000.0F);

        if (Config.isUSE_RENDER()) {
          newSound(this.soundName, true);
          Eff3DActor.setIntesity(this.jdField_smoke_of_type_ComMaddoxIl2EngineEff3DActor, 1.0F);
          Eff3DActor.setIntesity(this.jdField_sprite_of_type_ComMaddoxIl2EngineEff3DActor, 1.0F);
          this.jdField_flame_of_type_ComMaddoxIl2EngineActor.drawing(true);
          this.jdField_light_of_type_ComMaddoxIl2EngineLightPointActor.light.setEmit(2.0F, 100.0F);
        }
      } else {
        Ballistics.update(this, this.M, 0.07068583F, 0.0F, true);
        this.pos.setAbs(this.tOrient);
        return false;
      }
    }
    return super.interpolateStep();
  }

  public void start(float paramFloat)
  {
    super.start(-1.0F);
    FlightModel localFlightModel = ((Aircraft)getOwner()).FM;
    this.tOrient.set(localFlightModel.Or);
    this.speed.set(localFlightModel.Vwld);
    this.noGDelay = -1L;
    this.tEStart = (Time.current() + World.Rnd().nextLong(900L, 1100L));
    if (Config.isUSE_RENDER()) {
      breakSounds();
      Eff3DActor.setIntesity(this.jdField_smoke_of_type_ComMaddoxIl2EngineEff3DActor, 0.0F);
      Eff3DActor.setIntesity(this.jdField_sprite_of_type_ComMaddoxIl2EngineEff3DActor, 0.0F);

      this.jdField_flame_of_type_ComMaddoxIl2EngineActor.drawing(false);
      this.jdField_light_of_type_ComMaddoxIl2EngineLightPointActor.light.setEmit(0.0F, 0.0F);
    }
  }

  static
  {
    Class localClass = RocketTinyTim.class;
    Property.set(localClass, "mesh", "3DO/Arms/TinyTim/mono.sim");

    Property.set(localClass, "sprite", "3DO/Effects/Rocket/firesprite.eff");
    Property.set(localClass, "flame", "3DO/Effects/Rocket/mono.sim");
    Property.set(localClass, "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff");
    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 50.0F);
    Property.set(localClass, "emitMax", 2.0F);

    Property.set(localClass, "sound", "weapon.rocket_132");

    Property.set(localClass, "radius", 75.0F);
    Property.set(localClass, "timeLife", 1000000.0F);
    Property.set(localClass, "timeFire", 33.0F);
    Property.set(localClass, "force", 0.0F);

    Property.set(localClass, "power", 125.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.3F);
    Property.set(localClass, "massa", 582.0F);
    Property.set(localClass, "massaEnd", 270.0F);
  }
}