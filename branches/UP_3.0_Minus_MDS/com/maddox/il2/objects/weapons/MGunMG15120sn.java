// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MGunMG15120sn.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunMG15120s

public class MGunMG15120sn extends com.maddox.il2.objects.weapons.MGunMG15120s
{

    public MGunMG15120sn()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = null;
        gunproperties.sprite = null;
        gunproperties.shells = null;
        gunproperties.emitColor = new Color3f(0.0F, 0.0F, 0.0F);
        gunproperties.shotFreq = 11.5F;
        gunproperties.maxDeltaAngle = 0.25F;
        return gunproperties;
    }
}
