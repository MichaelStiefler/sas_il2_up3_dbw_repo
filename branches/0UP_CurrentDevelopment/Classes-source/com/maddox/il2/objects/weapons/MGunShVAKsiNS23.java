// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunShVAKsiNS23.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunShVAKs

public class MGunShVAKsiNS23 extends com.maddox.il2.objects.weapons.MGunShVAKs
{

    public MGunShVAKsiNS23()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = false;
        gunproperties.shells = null;
        gunproperties.shotFreq = 13.33333F;
        gunproperties.maxDeltaAngle = 0.12F;
        return gunproperties;
    }
}
