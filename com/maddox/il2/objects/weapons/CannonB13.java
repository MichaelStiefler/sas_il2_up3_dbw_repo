// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonB13.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonLongrangeGeneric

public class CannonB13 extends com.maddox.il2.objects.weapons.CannonLongrangeGeneric
{

    public CannonB13()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 25000F;
        gunproperties.fireMesh = null;
        gunproperties.fire = "Effects/Bigship/GunFire150mm/Fire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "Effects/BigShip/GunFire150mm/Burst.eff";
        gunproperties.shells = null;
        gunproperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 13F;
        gunproperties.emitTime = 0.3F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 3.65F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 120F;
        bulletproperties.kalibr = 0.13F;
        bulletproperties.massa = 33.4F;
        bulletproperties.speed = 870F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0x1010101;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 2.71F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 120F;
        bulletproperties.kalibr = 0.13F;
        bulletproperties.massa = 33.4F;
        bulletproperties.speed = 870F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0x1010101;
        return 51.7F;
    }
}
