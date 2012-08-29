// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunBrowning303k_jap.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBrowning303s_jap

public class MGunBrowning303k_jap extends com.maddox.il2.objects.weapons.MGunBrowning303s_jap
{

    public MGunBrowning303k_jap()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = "3DO/Effects/GunShells/GunShells.eff";
        gunproperties.shotFreq = 16.66667F;
        gunproperties.maxDeltaAngle = 0.44F;
        gunproperties.shotFreqDeviation = 0.04F;
        return gunproperties;
    }
}
