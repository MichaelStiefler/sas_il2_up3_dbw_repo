// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonMortar_600mm.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonLongrangeGeneric

public class CannonMortar_600mm extends com.maddox.il2.objects.weapons.CannonLongrangeGeneric
{

    public CannonMortar_600mm()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 6640F;
        gunproperties.fireMesh = null;
        gunproperties.fire = "Effects/BigShip/GunFire350mm/Fire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "Effects/BigShip/GunFire350mm/Burst.eff";
        gunproperties.shells = null;
        gunproperties.emitColor = new Color3f(0.8F, 0.2F, 0.0F);
        gunproperties.emitI = 4F;
        gunproperties.emitR = 32F;
        gunproperties.emitTime = 0.6F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 280F;
        bulletproperties.powerType = 2;
        bulletproperties.powerRadius = 280F;
        bulletproperties.kalibr = 0.6F;
        bulletproperties.massa = 2170F;
        bulletproperties.speed = 220F;
        bulletproperties.traceMesh = "3do/Arms/600mmShell/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 220F;
        bulletproperties.powerType = 2;
        bulletproperties.powerRadius = 220F;
        bulletproperties.kalibr = 0.6F;
        bulletproperties.massa = 1700F;
        bulletproperties.speed = 283F;
        bulletproperties.traceMesh = "3do/Arms/600mmShell/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        return 8.45F;
    }
}
