// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MMGunBrowning50tdual.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBrowning50tdual

public class MMGunBrowning50tdual extends com.maddox.il2.objects.weapons.MGunBrowning50tdual
{

    public MMGunBrowning50tdual()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.sound = "weapon.mgun_15_dual_t";
        return gunproperties;
    }
}
