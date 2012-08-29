// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonHowitzer_150mm.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonMidrangeGeneric

public class CannonHowitzer_150mm extends com.maddox.il2.objects.weapons.CannonMidrangeGeneric
{

    public CannonHowitzer_150mm()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 6000F;
        gunproperties.sound = "weapon.Cannon100";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 0.0F;
        bulletproperties.timeLife = 8.5F;
        bulletproperties.kalibr = 0.15F;
        bulletproperties.massa = 48.6F;
        bulletproperties.speed = 500F;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 6F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 170F;
        bulletproperties.timeLife = 8.5F;
        bulletproperties.kalibr = 0.15F;
        bulletproperties.massa = 43.5F;
        bulletproperties.speed = 520F;
        return 29.5F;
    }
}
