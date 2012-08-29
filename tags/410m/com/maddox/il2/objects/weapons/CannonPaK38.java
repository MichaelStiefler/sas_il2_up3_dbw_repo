// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonPaK38.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonMidrangeGeneric

public class CannonPaK38 extends com.maddox.il2.objects.weapons.CannonMidrangeGeneric
{

    public CannonPaK38()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 3000F;
        gunproperties.sound = "weapon.Cannon45";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 0.0F;
        bulletproperties.timeLife = 7F;
        bulletproperties.kalibr = 0.05F;
        bulletproperties.massa = 2.05F;
        bulletproperties.speed = 835F;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.21F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 90F;
        bulletproperties.timeLife = 6F;
        bulletproperties.kalibr = 0.05F;
        bulletproperties.massa = 1.81F;
        bulletproperties.speed = 550F;
        return 60F;
    }
}
