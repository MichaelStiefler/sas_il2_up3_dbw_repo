package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning50kiAPI extends com.maddox.il2.objects.weapons.MGunBrowning50APIT
{

    public MGunBrowning50kiAPI()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.shotFreq = 12.5F;
        gunproperties.maxDeltaAngle = 0.229F;
        return gunproperties;
    }
}
