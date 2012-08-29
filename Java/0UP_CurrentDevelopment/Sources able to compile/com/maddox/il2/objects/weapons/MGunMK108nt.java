package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunMK108nt extends com.maddox.il2.objects.weapons.MGunMK108k
{

    public MGunMK108nt()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bEnablePause = true;
        gunproperties.maxDeltaAngle = 0.43F;
        gunproperties.traceFreq = 10000;
        gunproperties.bullet[0].traceMesh = null;
        gunproperties.bullet[0].traceTrail = null;
        gunproperties.bullet[0].traceColor = 0;
        return gunproperties;
    }
}
