// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunSh37k.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunSh37s

public class MGunSh37k extends com.maddox.il2.objects.weapons.MGunSh37s
{

    public MGunSh37k()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
        gunproperties.shotFreq = 2.0F;
        gunproperties.maxDeltaAngle = 0.42F;
        gunproperties.shotFreqDeviation = 0.02F;
        return gunproperties;
    }
}