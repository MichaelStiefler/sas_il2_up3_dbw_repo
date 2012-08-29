// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonHun41M40_51.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonTankGeneric

public class CannonHun41M40_51 extends com.maddox.il2.objects.weapons.CannonTankGeneric
{

    public CannonHun41M40_51()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.kalibr = 0.04F;
        bulletproperties.massa = 0.9F;
        bulletproperties.speed = 800F;
        gunproperties.sound = "weapon.Cannon45t";
        return 51F;
    }
}
