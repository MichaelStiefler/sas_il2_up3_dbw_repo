package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMG213MGki extends com.maddox.il2.objects.weapons.MGunMG213MGs
{

    public MGunMG213MGki()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.shotFreq = 20F;
        gunproperties.maxDeltaAngle = 0.28F;
        gunproperties.shotFreqDeviation = 0.02F;
        return gunproperties;
    }
}
