// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunMK103ki.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunMK103s

public class MGunMK103ki extends com.maddox.il2.objects.weapons.MGunMK103s
{

    public MGunMK103ki()
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
        return gunproperties;
    }
}
