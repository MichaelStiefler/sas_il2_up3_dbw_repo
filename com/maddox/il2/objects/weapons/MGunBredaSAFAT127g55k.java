// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunBredaSAFAT127g55k.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBredaSAFAT127g55

public class MGunBredaSAFAT127g55k extends com.maddox.il2.objects.weapons.MGunBredaSAFAT127g55
{

    public MGunBredaSAFAT127g55k()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.shells = "3DO/Effects/GunShells/GunShells.eff";
        return gunproperties;
    }
}
