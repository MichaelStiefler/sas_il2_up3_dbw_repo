// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunMiniGun3000.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBrowning50s

public class MGunMiniGun3000 extends com.maddox.il2.objects.weapons.MGunBrowning50s
{

    public MGunMiniGun3000()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fire = null;
        gunproperties.shells = null;
        gunproperties.sound = "weapon.MiniGun";
        gunproperties.shotFreq = 50F;
        gunproperties.bullets = 8000;
        return gunproperties;
    }
}
