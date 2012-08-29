package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class CannonM5_75mmbatt extends com.maddox.il2.objects.weapons.CannonMidrangeGeneric
{

    public CannonM5_75mmbatt()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.maxDeltaAngle = 5F;
        gunproperties.aimMaxDist = 4000F;
        gunproperties.sound = "weapon.Cannon75";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 0.0F;
        bulletproperties.timeLife = 6F;
        bulletproperties.kalibr = 0.075F;
        bulletproperties.massa = 6.3F;
        bulletproperties.speed = 760F;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.0F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 110F;
        bulletproperties.timeLife = 7F;
        bulletproperties.kalibr = 0.0762F;
        bulletproperties.massa = 6.2F;
        bulletproperties.speed = 780F;
        return 40F;
    }
}
