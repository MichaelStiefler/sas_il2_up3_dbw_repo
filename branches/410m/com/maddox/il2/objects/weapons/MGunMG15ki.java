// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunMG15ki.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunMG15s

public class MGunMG15ki extends com.maddox.il2.objects.weapons.MGunMG15s
{

    public MGunMG15ki()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.shotFreq = 15.83333F;
        gunproperties.maxDeltaAngle = 0.5F;
        gunproperties.shotFreqDeviation = 0.04F;
        return gunproperties;
    }
}
