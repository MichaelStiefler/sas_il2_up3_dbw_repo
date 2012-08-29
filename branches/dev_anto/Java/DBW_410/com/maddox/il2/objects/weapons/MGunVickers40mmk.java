package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunVickers40mmk extends MGunVickers40mms
{

    public MGunVickers40mmk()
    {
    }

    public GunProperties createProperties()
    {
        GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.shotFreq = 1.6F;
        gunproperties.maxDeltaAngle = 0.01F;
        gunproperties.shotFreqDeviation = 0.03F;
        return gunproperties;
    }
}