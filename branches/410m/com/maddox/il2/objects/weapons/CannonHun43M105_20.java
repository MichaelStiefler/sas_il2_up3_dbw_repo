// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonHun43M105_20.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonTankGeneric

public class CannonHun43M105_20 extends com.maddox.il2.objects.weapons.CannonTankGeneric
{

    public CannonHun43M105_20()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.kalibr = 0.105F;
        bulletproperties.massa = 17F;
        bulletproperties.speed = 560F;
        gunproperties.sound = "weapon.Cannon100t";
        return 20.5F;
    }
}
