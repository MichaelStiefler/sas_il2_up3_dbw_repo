// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonMarkVI_254mm.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonLongrangeGeneric

public class CannonMarkVI_254mm extends com.maddox.il2.objects.weapons.CannonLongrangeGeneric
{

    public CannonMarkVI_254mm()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 13530F;
        gunproperties.fireMesh = null;
        gunproperties.fire = "Effects/Bigship/GunFire250mm/Fire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "Effects/BigShip/GunFire250mm/Burst.eff";
        gunproperties.shells = null;
        gunproperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 25.4F;
        gunproperties.emitTime = 0.4F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 3.89F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 200F;
        bulletproperties.kalibr = 0.254F;
        bulletproperties.massa = 227F;
        bulletproperties.speed = 810F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0x1010101;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 28.3F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 200F;
        bulletproperties.kalibr = 0.254F;
        bulletproperties.massa = 227F;
        bulletproperties.speed = 810F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0x1010101;
        return 46.76F;
    }
}
