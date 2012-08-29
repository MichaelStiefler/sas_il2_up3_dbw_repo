package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMiniGun6000 extends com.maddox.il2.objects.weapons.MGunBrowning50s
{

    public MGunMiniGun6000()
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
        gunproperties.shotFreq = 100F;
        gunproperties.bullets = 8000;
        return gunproperties;
    }
}
