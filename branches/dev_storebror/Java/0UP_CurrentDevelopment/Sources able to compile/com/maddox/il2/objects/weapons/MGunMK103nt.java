package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunMK103nt extends com.maddox.il2.objects.weapons.MGunMK103s
{

    public MGunMK103nt()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.shotFreq = 7.083333F;
        gunproperties.maxDeltaAngle = 0.35F;
        gunproperties.shotFreqDeviation = 0.02F;
        gunproperties.traceFreq = 10000;
        gunproperties.bullet[0].traceMesh = null;
        gunproperties.bullet[0].traceTrail = null;
        gunproperties.bullet[0].traceColor = 0;
        return gunproperties;
    }
}
