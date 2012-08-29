package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunType99No2t extends com.maddox.il2.objects.weapons.MGunType99No2s
{

    public MGunType99No2t()
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
