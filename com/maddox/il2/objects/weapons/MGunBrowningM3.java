// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MGunBrowningM3.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBrowning50APIT

public class MGunBrowningM3 extends com.maddox.il2.objects.weapons.MGunBrowning50APIT
{

    public MGunBrowningM3()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.shells = null;
        gunproperties.shotFreq = 20F;
        gunproperties.traceFreq = 2;
        gunproperties.maxDeltaAngle = 0.229F;
        gunproperties.bulletsCluster = 2;
        gunproperties.emitI = 8F;
        gunproperties.emitR = 1.5F;
        return gunproperties;
    }
}
