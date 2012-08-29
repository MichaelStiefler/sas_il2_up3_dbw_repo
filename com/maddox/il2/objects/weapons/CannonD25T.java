// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonD25T.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonTankGeneric

public class CannonD25T extends com.maddox.il2.objects.weapons.CannonTankGeneric
{

    public CannonD25T()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.kalibr = 0.122F;
        bulletproperties.massa = 25F;
        bulletproperties.speed = 781F;
        gunproperties.sound = "weapon.Cannon100t";
        return 48F;
    }
}
