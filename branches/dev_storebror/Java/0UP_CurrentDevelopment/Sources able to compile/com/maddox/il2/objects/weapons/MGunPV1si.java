package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunPV1si extends com.maddox.il2.objects.weapons.MGunPV1
{

    public MGunPV1si()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.shotFreq = 11.7F;
        gunproperties.maxDeltaAngle = 0.31F;
        return gunproperties;
    }
}
