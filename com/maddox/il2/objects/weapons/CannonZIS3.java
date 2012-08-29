// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonZIS3.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonMidrangeGeneric

public class CannonZIS3 extends com.maddox.il2.objects.weapons.CannonMidrangeGeneric
{

    public CannonZIS3()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 4000F;
        gunproperties.sound = "weapon.Cannon75";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 0.0F;
        bulletproperties.timeLife = 6F;
        bulletproperties.kalibr = 0.0762F;
        bulletproperties.massa = 6.3F;
        bulletproperties.speed = 662F;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.71F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 120F;
        bulletproperties.timeLife = 7F;
        bulletproperties.kalibr = 0.0762F;
        bulletproperties.massa = 6.2F;
        bulletproperties.speed = 680F;
        return 40F;
    }
}
