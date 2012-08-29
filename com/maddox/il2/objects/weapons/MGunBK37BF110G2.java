// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunBK37BF110G2.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBK37

public class MGunBK37BF110G2 extends com.maddox.il2.objects.weapons.MGunBK37
{

    public MGunBK37BF110G2()
    {
    }

    public void setConvDistance(float f, float f1)
    {
        super.setConvDistance(f, f1 - 0.5F);
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bullet[0].speed = 1170F;
        return gunproperties;
    }
}
