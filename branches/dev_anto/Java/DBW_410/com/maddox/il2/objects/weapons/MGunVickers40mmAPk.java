package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunVickers40mmAPk extends MGunVickers40mmAPs
{

    public MGunVickers40mmAPk()
    {
    }

    public GunProperties createProperties()
    {
        GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.shotFreq = 1.6F;
        gunproperties.maxDeltaAngle = 0.09F;
        gunproperties.shotFreqDeviation = 0.04F;
        return gunproperties;
    }
}