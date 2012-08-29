// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunBrowning50kAPIT.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBrowning50APIT

public class MGunBrowning50kAPIT extends com.maddox.il2.objects.weapons.MGunBrowning50APIT
{

    public MGunBrowning50kAPIT()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = "3DO/Effects/GunShells/GunShells.eff";
        gunproperties.shotFreq = 12.5F;
        gunproperties.maxDeltaAngle = 0.229F;
        gunproperties.shotFreqDeviation = 0.08F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmPink/mono.sim";
        gunproperties.bullet[0].traceColor = 0xd29e6bed;
        return gunproperties;
    }
}
