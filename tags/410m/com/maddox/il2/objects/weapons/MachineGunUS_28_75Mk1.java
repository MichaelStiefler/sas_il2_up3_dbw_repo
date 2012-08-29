// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGunUS_28_75Mk1.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAntiAirGeneric

public class MachineGunUS_28_75Mk1 extends com.maddox.il2.objects.weapons.MGunAntiAirGeneric
{

    public MachineGunUS_28_75Mk1()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 6767F;
        gunproperties.shotFreq = 10F;
        gunproperties.bulletsCluster = 3;
        gunproperties.sound = "weapon.zenitka_20";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 7F;
        bulletproperties.addExplTime = 1.5F;
        bulletproperties.power = 0.017F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 32F;
        bulletproperties.kalibr = 0.028F;
        bulletproperties.massa = 0.416F;
        bulletproperties.speed = 823F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmMagenta/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xb3ff00ff;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.015F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 30F;
        bulletproperties.kalibr = 0.028F;
        bulletproperties.massa = 0.416F;
        bulletproperties.speed = 823F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmMagenta/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xb3ff00ff;
        return 75F;
    }
}
