// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonUS_8_55Mk9.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonLongrangeGeneric

public class CannonUS_8_55Mk9 extends com.maddox.il2.objects.weapons.CannonLongrangeGeneric
{

    public CannonUS_8_55Mk9()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 29131F;
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
        bulletproperties.power = 2.1F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 210F;
        bulletproperties.kalibr = 0.203F;
        bulletproperties.massa = 118F;
        bulletproperties.speed = 853F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 9.7F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 210F;
        bulletproperties.kalibr = 0.203F;
        bulletproperties.massa = 118F;
        bulletproperties.speed = 914F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        return 55F;
    }
}
