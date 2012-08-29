// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MGunBredaSAFATt77st.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBredaSAFATt77s

public class MGunBredaSAFATt77st extends com.maddox.il2.objects.weapons.MGunBredaSAFATt77s
{

    public MGunBredaSAFATt77st()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = false;
        gunproperties.shells = null;
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.maxDeltaAngle = 0.42F;
        gunproperties.shotFreq = 14.66667F;
        return gunproperties;
    }
}
