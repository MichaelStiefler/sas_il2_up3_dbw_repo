// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunMG151ki.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunMG151s

public class MGunMG151ki extends com.maddox.il2.objects.weapons.MGunMG151s
{

    public MGunMG151ki()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.shotFreq = 11.66667F;
        gunproperties.maxDeltaAngle = 0.25F;
        gunproperties.shotFreqDeviation = 0.02F;
        return gunproperties;
    }
}
