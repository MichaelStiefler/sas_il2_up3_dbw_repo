// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonKwK37.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonTankGeneric

public class CannonKwK37 extends com.maddox.il2.objects.weapons.CannonTankGeneric
{

    public CannonKwK37()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.kalibr = 0.075F;
        bulletproperties.massa = 6.8F;
        bulletproperties.speed = 385F;
        gunproperties.sound = "weapon.Cannon75t";
        return 24F;
    }
}
