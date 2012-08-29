// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonUS_4_50Mk7.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonLongrangeGeneric

public class CannonUS_4_50Mk7 extends com.maddox.il2.objects.weapons.CannonLongrangeGeneric
{

    public CannonUS_4_50Mk7()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 14560F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.power = 1.88F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 168F;
        bulletproperties.kalibr = 0.102F;
        bulletproperties.massa = 17.5F;
        bulletproperties.speed = 884F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xff00ffff;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 2.1F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 180F;
        bulletproperties.kalibr = 0.102F;
        bulletproperties.massa = 17.5F;
        bulletproperties.speed = 884F;
        bulletproperties.traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
        bulletproperties.traceTrail = null;
        bulletproperties.traceColor = 0xff0080ff;
        return 50F;
    }
}
