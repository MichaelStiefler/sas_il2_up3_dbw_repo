// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunNS45k.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunNS45s

public class MGunNS45k extends com.maddox.il2.objects.weapons.MGunNS45s
{

    public MGunNS45k()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
        gunproperties.shotFreq = 4.5F;
        gunproperties.shotFreqDeviation = 0.02F;
        return gunproperties;
    }
}
