// Source File Name: RocketGunAIM9B.java
// Author:           Storebror
// Last Modified by: Storebror 2011-06-01
package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeGuidedMissileCarrier;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketGun
public class RocketGunAIM9B extends RocketGun {

  public RocketGunAIM9B() {
  }

  public float bulletMassa() {
    return bulletMassa / 10F;
  }

  public void shots(int paramInt) {
    try {
      if (Actor.isValid(super.actor) && (super.actor instanceof Aircraft) && (super.actor instanceof TypeGuidedMissileCarrier) && (Aircraft) super.actor == World.getPlayerAircraft() && ((RealFlightModel) ((SndAircraft) ((Aircraft) super.actor)).FM).isRealMode() && ((TypeGuidedMissileCarrier) super.actor).hasMissiles() && ((TypeGuidedMissileCarrier) super.actor).getMissileLockState() == 0) {
        HUD.log("AIM-9B launch cancelled (disengaged)");
        return;
      }
    } catch (Exception exception) {
      HUD.log("AIM-9B launch cancelled (system error)");
    }
    super.shots(paramInt);
    if ((paramInt > 0)
            && Actor.isValid(super.actor)
            && (super.actor instanceof TypeGuidedMissileCarrier)
            && World.cur().diffCur.Limited_Ammo
            && (super.actor == World.getPlayerAircraft())) {
      ((TypeGuidedMissileCarrier) super.actor).shotMissile();
    }
  }

  static {
    Class class1 = com.maddox.il2.objects.weapons.RocketGunAIM9B.class;
    Property.set(class1, "bulletClass", (Object) com.maddox.il2.objects.weapons.MissileAIM9B.class);
    Property.set(class1, "bullets", 1);
    Property.set(class1, "shotFreq", 0.25F);
    Property.set(class1, "sound", "weapon.rocketgun_132");
  }
}