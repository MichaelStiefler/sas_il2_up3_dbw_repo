package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunB20t extends com.maddox.il2.objects.weapons.MGunB20s
{

    public MGunB20t()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = false;
        gunproperties.shells = null;
        gunproperties.shotFreq = 13.33333F;
        gunproperties.maxDeltaAngle = 0.14F;
        return gunproperties;
    }
}
