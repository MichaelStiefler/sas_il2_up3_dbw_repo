package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

public class MGunBrowning303Trak extends com.maddox.il2.objects.weapons.MGunBrowning303s
{

    public MGunBrowning303Trak()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = "3DO/Effects/GunShells/GunShells.eff";
        gunproperties.shotFreq = 16.66667F;
        gunproperties.traceFreq = 0;
        gunproperties.maxDeltaAngle = 0.44F;
        gunproperties.shotFreqDeviation = 0.04F;
        return gunproperties;
    }
}
