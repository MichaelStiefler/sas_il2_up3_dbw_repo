// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunHo5ki.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunHo5s

public class MGunHo5ki extends com.maddox.il2.objects.weapons.MGunHo5s
{

    public MGunHo5ki()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.shotFreq = 14.16667F;
        gunproperties.maxDeltaAngle = 0.24F;
        gunproperties.shotFreqDeviation = 0.02F;
        return gunproperties;
    }
}
