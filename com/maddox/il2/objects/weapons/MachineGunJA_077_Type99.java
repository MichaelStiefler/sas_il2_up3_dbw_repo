// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGunJA_077_Type99.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunTankGeneric

public class MachineGunJA_077_Type99 extends com.maddox.il2.objects.weapons.MGunTankGeneric
{

    public MachineGunJA_077_Type99()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.shotFreq = 10F;
        gunproperties.bulletsCluster = 2;
        gunproperties.sound = "weapon.mgun_tank_13";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.kalibr = 0.0077F;
        bulletproperties.massa = 0.011F;
        bulletproperties.speed = 850F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmWhite/mono.sim";
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0xd2ffffff;
        return 1.07F / bulletproperties.kalibr;
    }
}
