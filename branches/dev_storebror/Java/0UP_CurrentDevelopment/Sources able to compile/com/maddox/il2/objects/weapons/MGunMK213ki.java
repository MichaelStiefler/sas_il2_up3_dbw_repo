package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunMK213ki extends com.maddox.il2.objects.weapons.MGunMK213s
{

    public MGunMK213ki()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.shotFreq = 18.33F;
        gunproperties.maxDeltaAngle = 0.43F;
        gunproperties.shotFreqDeviation = 0.02F;
        return gunproperties;
    }
}
