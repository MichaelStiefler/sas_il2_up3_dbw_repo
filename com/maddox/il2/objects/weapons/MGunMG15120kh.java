// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunMG15120kh.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunMG15120k

public class MGunMG15120kh extends com.maddox.il2.objects.weapons.MGunMG15120k
{

    public MGunMG15120kh()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bEnablePause = true;
        gunproperties.maxDeltaAngle = 0.28F;
        return gunproperties;
    }
}
