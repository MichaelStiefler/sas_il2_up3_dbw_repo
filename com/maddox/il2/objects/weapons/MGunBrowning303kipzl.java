// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunBrowning303kipzl.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBrowning303ki

public class MGunBrowning303kipzl extends com.maddox.il2.objects.weapons.MGunBrowning303ki
{

    public MGunBrowning303kipzl()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 0.0F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.03F;
        return gunproperties;
    }
}
