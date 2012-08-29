// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunVz30syn.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunVz30s

public class MGunVz30syn extends com.maddox.il2.objects.weapons.MGunVz30s
{

    public MGunVz30syn()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.shotFreq = 13F;
        gunproperties.shotFreqDeviation = 0.04F;
        gunproperties.emitI = 2.0F;
        return gunproperties;
    }
}
