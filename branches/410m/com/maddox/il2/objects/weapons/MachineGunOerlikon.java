// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGunOerlikon.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunTankGeneric

public class MachineGunOerlikon extends com.maddox.il2.objects.weapons.MGunTankGeneric
{

    public MachineGunOerlikon()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 2300F;
        gunproperties.shotFreq = 8.666667F;
        gunproperties.sound = "weapon.mgun_tank_20";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 7F;
        bulletproperties.kalibr = 0.02F;
        bulletproperties.massa = 0.124F;
        bulletproperties.speed = 570F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmBlue/mono.sim";
        bulletproperties.traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
        bulletproperties.traceColor = 0xd2ff0000;
        return 115F;
    }
}
