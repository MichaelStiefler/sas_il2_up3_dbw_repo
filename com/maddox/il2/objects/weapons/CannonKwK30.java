// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonKwK30.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunTankGeneric

public class CannonKwK30 extends com.maddox.il2.objects.weapons.MGunTankGeneric
{

    public CannonKwK30()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.shotFreq = 6.666667F;
        gunproperties.sound = "weapon.mgun_tank_20";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.kalibr = 0.02F;
        bulletproperties.massa = 0.148F;
        bulletproperties.speed = 801F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmGreenBlue/mono.sim";
        bulletproperties.traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        bulletproperties.traceColor = 0xd494c476;
        return 97.1F;
    }
}
