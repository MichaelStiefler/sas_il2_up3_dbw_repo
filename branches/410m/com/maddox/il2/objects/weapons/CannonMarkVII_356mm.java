// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonMarkVII_356mm.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonLongrangeGeneric

public class CannonMarkVII_356mm extends com.maddox.il2.objects.weapons.CannonLongrangeGeneric
{

    public CannonMarkVII_356mm()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 46630F;
        gunproperties.fireMesh = null;
        gunproperties.fire = "Effects/BigShip/GunFire350mm/Fire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "Effects/BigShip/GunFire350mm/Burst.eff";
        gunproperties.shells = null;
        gunproperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 38F;
        gunproperties.emitTime = 0.6F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 20.38F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 200F;
        bulletproperties.kalibr = 0.356F;
        bulletproperties.massa = 747.8F;
        bulletproperties.speed = 732F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 75.85F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 220F;
        bulletproperties.kalibr = 0.356F;
        bulletproperties.massa = 747.8F;
        bulletproperties.speed = 732F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        return 45F;
    }
}
