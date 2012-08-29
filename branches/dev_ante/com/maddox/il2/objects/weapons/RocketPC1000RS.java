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

public class RocketPC1000RS extends Rocket
{
  private long tEStart;
  private Orient tOrient = new Orient();

  public RocketPC1000RS() { this.flags &= -33;
  }

  public boolean interpolateStep()
  {
    if (this.tEStart > 0L) if (Time.current() > this.tEStart) { this.tEStart = -1L; setThrust(52500.0F);

        if (Config.isUSE_RENDER()) { newSound(this.soundName, true); Eff3DActor.setIntesity(this.smoke, 1.0F); Eff3DActor.setIntesity(this.sprite, 1.0F); this.flame.drawing(true); this.light.light.setEmit(2.0F, 100.0F); }  } else {
        Ballistics.update(this, this.M, 0.07068583F, 0.0F, true);

        this.pos.setAbs(this.tOrient); return false;
      } return super.interpolateStep();
  }

  public void start(float paramFloat)
  {
    super.start(-1.0F); FlightModel localFlightModel = ((Aircraft)getOwner()).FM; this.tOrient.set(localFlightModel.Or); this.speed.set(localFlightModel.Vwld); this.noGDelay = -1L; this.tEStart = (Time.current() + World.Rnd().nextLong(900L, 1100L)); if (Config.isUSE_RENDER()) { breakSounds(); Eff3DActor.setIntesity(this.smoke, 0.0F); Eff3DActor.setIntesity(this.sprite, 0.0F); this.flame.drawing(false);

      this.light.light.setEmit(0.0F, 0.0F);
    }
  }

  static
  {
    Class localClass = RocketPC1000RS.class; Property.set(localClass, "mesh", "3DO/Arms/PC1000RS/mono.sim"); Property.set(localClass, "sprite", "3DO/Effects/Rocket/firesprite.eff"); Property.set(localClass, "flame", "3DO/Effects/Rocket/mono.sim"); Property.set(localClass, "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff"); Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F)); Property.set(localClass, "emitLen", 150.0F); Property.set(localClass, "emitMax", 2.0F); Property.set(localClass, "sound", "weapon.rocket_132"); Property.set(localClass, "radius", 45.0F);

    Property.set(localClass, "timeLife", 1000000.0F); Property.set(localClass, "timeFire", 3.0F); Property.set(localClass, "force", 0.0F); Property.set(localClass, "power", 110.0F); Property.set(localClass, "powerType", 0); Property.set(localClass, "kalibr", 0.5333F); Property.set(localClass, "massa", 988.0F); Property.set(localClass, "massaEnd", 838.0F);
  }
}