package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunShVAKsiNS23 extends com.maddox.il2.objects.weapons.MGunShVAKs
{

    public MGunShVAKsiNS23()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = false;
        gunproperties.shells = null;
        gunproperties.shotFreq = 13.33333F;
        gunproperties.maxDeltaAngle = 0.12F;
        return gunproperties;
    }
}
