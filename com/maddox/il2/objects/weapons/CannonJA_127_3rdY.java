// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonJA_127_3rdY.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonLongrangeGeneric

public class CannonJA_127_3rdY extends com.maddox.il2.objects.weapons.CannonLongrangeGeneric
{

    public CannonJA_127_3rdY()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 18380F;
        gunproperties.fireMesh = null;
        gunproperties.fire = "Effects/BigShip/GunFire150mm/Fire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "Effects/BigShip/GunFire150mm/Burst.eff";
        gunproperties.shells = null;
        gunproperties.emitColor = new Color3f(1.0F, 0.5F, 0.2F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 15F;
        gunproperties.emitTime = 0.3F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 1.78F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 178F;
        bulletproperties.kalibr = 0.127F;
        bulletproperties.massa = 23F;
        bulletproperties.speed = 915F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 1.78F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 178F;
        bulletproperties.kalibr = 0.127F;
        bulletproperties.massa = 23F;
        bulletproperties.speed = 915F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        return 50F;
    }
}
