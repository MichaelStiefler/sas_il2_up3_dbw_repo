// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonUS_3_50Mk10.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonLongrangeGeneric

public class CannonUS_3_50Mk10 extends com.maddox.il2.objects.weapons.CannonLongrangeGeneric
{

    public CannonUS_3_50Mk10()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 13350F;
        gunproperties.sound = "weapon.Cannon85t";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 0.58F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 70F;
        bulletproperties.kalibr = 0.0762F;
        bulletproperties.massa = 5.9F;
        bulletproperties.speed = 823F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.0F;
        bulletproperties.kalibr = 0.0762F;
        bulletproperties.massa = 5.8F;
        bulletproperties.speed = 823F;
        bulletproperties.traceMesh = null;
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0x1010101;
        return 50F;
    }
}
