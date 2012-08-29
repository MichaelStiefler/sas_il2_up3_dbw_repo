// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunBrowning303ki.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBrowning303s

public class MGunBrowning303ki extends com.maddox.il2.objects.weapons.MGunBrowning303s
{

    public MGunBrowning303ki()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.shotFreq = 16.66667F;
        gunproperties.maxDeltaAngle = 0.44F;
        gunproperties.shotFreqDeviation = 0.04F;
        return gunproperties;
    }
}
