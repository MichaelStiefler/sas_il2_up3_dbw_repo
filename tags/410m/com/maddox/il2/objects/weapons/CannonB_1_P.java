// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonB_1_P.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonLongrangeGeneric

public class CannonB_1_P extends com.maddox.il2.objects.weapons.CannonLongrangeGeneric
{

    public CannonB_1_P()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 36000F;
        gunproperties.fireMesh = null;
        gunproperties.fire = "Effects/Bigship/GunFire250mm/Fire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "Effects/BigShip/GunFire250mm/Burst.eff";
        gunproperties.shells = null;
        gunproperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 18F;
        gunproperties.emitTime = 0.4F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 1.82F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 200F;
        bulletproperties.kalibr = 0.18F;
        bulletproperties.massa = 97.5F;
        bulletproperties.speed = 920F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0x1010101;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 7.52F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 200F;
        bulletproperties.kalibr = 0.18F;
        bulletproperties.massa = 97.5F;
        bulletproperties.speed = 920F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0x1010101;
        return 56F;
    }
}
