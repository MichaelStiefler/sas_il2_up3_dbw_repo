// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonML20.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonMidrangeGeneric

public class CannonML20 extends com.maddox.il2.objects.weapons.CannonMidrangeGeneric
{

    public CannonML20()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 6000F;
        gunproperties.sound = "weapon.Cannon100";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 0.0F;
        bulletproperties.timeLife = 7F;
        bulletproperties.kalibr = 0.152F;
        bulletproperties.massa = 48.8F;
        bulletproperties.speed = 600F;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 6.25F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 170F;
        bulletproperties.timeLife = 7F;
        bulletproperties.kalibr = 0.152F;
        bulletproperties.massa = 43.6F;
        bulletproperties.speed = 630F;
        return 29F;
    }
}
