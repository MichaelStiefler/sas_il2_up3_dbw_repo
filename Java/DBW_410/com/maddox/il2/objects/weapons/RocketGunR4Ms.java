// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 7/02/2011 12:03:06 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RocketGunR4M.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketGun

public class RocketGunR4Ms extends RocketGun
{

    public RocketGunR4Ms()
    {
    }

    public void setConvDistance(float f, float f1)
    {
        super.setConvDistance(f, f1 + 2.81F);
    }

    static 
    {
        Class class1 = com.maddox.il2.objects.weapons.RocketGunR4Ms.class;
        Property.set(class1, "bulletClass", (Object)com.maddox.il2.objects.weapons.RocketR4M.class);
        Property.set(class1, "bullets", 1);
        Property.set(class1, "shotFreq", 4F);
        Property.set(class1, "sound", "weapon.rocketgun_132");
    }
}