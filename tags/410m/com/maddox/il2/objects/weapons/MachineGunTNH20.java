// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGunTNH20.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunTankGeneric

public class MachineGunTNH20 extends com.maddox.il2.objects.weapons.MGunTankGeneric
{

    public MachineGunTNH20()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.shotFreq = 13.33333F;
        gunproperties.bulletsCluster = 2;
        gunproperties.traceFreq = 2;
        gunproperties.sound = "weapon.mgun_tank_20x2";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.kalibr = 0.02F;
        bulletproperties.massa = 0.096F;
        bulletproperties.speed = 817F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xd9002eff;
        return 1.4F / bulletproperties.kalibr;
    }
}
