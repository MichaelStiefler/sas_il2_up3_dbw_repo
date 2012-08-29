package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunType99No1t extends com.maddox.il2.objects.weapons.MGunType99No1s
{

    public MGunType99No1t()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = false;
        gunproperties.shells = null;
        gunproperties.maxDeltaAngle = 0.24F;
        return gunproperties;
    }
}
