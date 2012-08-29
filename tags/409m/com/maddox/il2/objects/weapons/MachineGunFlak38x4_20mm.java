// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MachineGunFlak38x4_20mm.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MachineGunFlak38_20mm, CannonMidrangeGeneric

public class MachineGunFlak38x4_20mm extends com.maddox.il2.objects.weapons.MachineGunFlak38_20mm
{

    public MachineGunFlak38x4_20mm()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        super.Specify(gunproperties);
        gunproperties.shotFreq = 32F;
        gunproperties.bulletsCluster = 2;
        gunproperties.traceFreq = 1;
        com.maddox.il2.objects.weapons.CannonMidrangeGeneric.autocomputeSplintersRadiuses(gunproperties.bullet);
        return 55F;
    }
}
