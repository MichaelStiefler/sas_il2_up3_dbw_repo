// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MGunADEN30ki.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunADEN30s

public class MGunADEN30ki extends com.maddox.il2.objects.weapons.MGunADEN30s
{

    public MGunADEN30ki()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.shotFreq = 20F;
        gunproperties.maxDeltaAngle = 0.43F;
        gunproperties.shotFreqDeviation = 0.02F;
        return gunproperties;
    }
}
