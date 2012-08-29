package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class Cannon0305_1907x3 extends com.maddox.il2.objects.weapons.CannonLongrangeGeneric
{

    public Cannon0305_1907x3()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 24620F;
        gunproperties.bulletsCluster = 3;
        gunproperties.fireMesh = null;
        gunproperties.fire = "Effects/Bigship/GunFire350mm/Fire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "Effects/BigShip/GunFire350mm/Burst.eff";
        gunproperties.shells = null;
        gunproperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 30.5F;
        gunproperties.emitTime = 0.6F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 12.96F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 200F;
        bulletproperties.kalibr = 0.305F;
        bulletproperties.massa = 470.9F;
        bulletproperties.speed = 762F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0x1010101;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 61.5F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 200F;
        bulletproperties.kalibr = 0.305F;
        bulletproperties.massa = 470.9F;
        bulletproperties.speed = 762F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0x1010101;
        return 52F;
    }
}
