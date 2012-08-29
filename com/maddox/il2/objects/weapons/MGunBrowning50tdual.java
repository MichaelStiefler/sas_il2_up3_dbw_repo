// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunBrowning50tdual.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBrowning50s

public class MGunBrowning50tdual extends com.maddox.il2.objects.weapons.MGunBrowning50s
{

    public MGunBrowning50tdual()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = false;
        gunproperties.shells = null;
        gunproperties.shotFreq = 25F;
        gunproperties.bulletsCluster *= 2;
        gunproperties.weaponType = 1;
        gunproperties.sound = "weapon.mgun_15_dual";
        gunproperties.maxDeltaAngle = 0.229F;
        return gunproperties;
    }
}
