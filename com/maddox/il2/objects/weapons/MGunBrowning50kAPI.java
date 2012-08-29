// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MGunBrowning50kAPI.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBrowning50APIT

public class MGunBrowning50kAPI extends com.maddox.il2.objects.weapons.MGunBrowning50APIT
{

    public MGunBrowning50kAPI()
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
        return gunproperties;
    }
}
