// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 21/6/2011 4:23:13 μμ
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: fullnames 
// Source File Name:   MGunMG15120NAG2.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunMG15120s

public class MGunMG15120NAG2 extends com.maddox.il2.objects.weapons.MGunMG15120s
{

    public MGunMG15120NAG2()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = true;
        gunproperties.weaponType = 3;
        gunproperties.traceFreq = 3;
        return gunproperties;
    }
}