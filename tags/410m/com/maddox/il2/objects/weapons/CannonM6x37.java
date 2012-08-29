// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonM6x37.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonTankGeneric

public class CannonM6x37 extends com.maddox.il2.objects.weapons.CannonTankGeneric
{

    public CannonM6x37()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.kalibr = 0.037F;
        bulletproperties.massa = 0.87F;
        bulletproperties.speed = 884F;
        gunproperties.sound = "weapon.Cannon45t";
        return 53.5F;
    }
}
