// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 18-Jan-12 3:12:07 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RocketGunWPFAC.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketGun

public class RocketGunWPFAC extends RocketGun
{

    public RocketGunWPFAC()
    {
    }

    public void setRocketTimeLife(float f)
    {
        timeLife = -1F;
    }

    static 
    {
        Class class1 = com.maddox.il2.objects.weapons.RocketGunWPFAC.class;
        Property.set(class1, "bulletClass", (Object)com.maddox.il2.objects.weapons.RocketWPFAC.class);
        Property.set(class1, "bullets", 1);
        Property.set(class1, "shotFreq", 1.0F);
        Property.set(class1, "sound", "weapon.rocketgun_82");
    }
}