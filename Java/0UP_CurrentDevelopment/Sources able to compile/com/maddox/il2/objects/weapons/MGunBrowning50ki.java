package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning50ki extends com.maddox.il2.objects.weapons.MGunBrowning50s
{

    public MGunBrowning50ki()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.shotFreq = 12.5F;
        gunproperties.maxDeltaAngle = 0.229F;
        return gunproperties;
    }
}
