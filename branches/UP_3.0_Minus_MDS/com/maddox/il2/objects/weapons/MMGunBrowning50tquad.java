// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MMGunBrowning50tquad.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBrowning50s

public class MMGunBrowning50tquad extends com.maddox.il2.objects.weapons.MGunBrowning50s
{

    public MMGunBrowning50tquad()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = false;
        gunproperties.shells = null;
        gunproperties.shotFreq = 45F;
        gunproperties.bulletsCluster *= 4;
        gunproperties.weaponType = 1;
        gunproperties.sound = "weapon.mgun_15_dual_t";
        gunproperties.maxDeltaAngle = 0.229F;
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 1.0F);
        return gunproperties;
    }
}
