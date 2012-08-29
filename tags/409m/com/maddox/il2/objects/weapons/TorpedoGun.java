// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TorpedoGun.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.GunProperties;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            BombGun

public class TorpedoGun extends com.maddox.il2.objects.weapons.BombGun
{

    public TorpedoGun()
    {
    }

    public float TravelTime(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        float f = (float)point3d.distance(point3d1);
        java.lang.Class class1 = getClass();
        java.lang.Class class2 = (java.lang.Class)com.maddox.rts.Property.value(class1, "bulletClass", null);
        float f1 = com.maddox.rts.Property.floatValue(class2, "velocity", 1.0F);
        float f2 = com.maddox.rts.Property.floatValue(class2, "traveltime", 1.0F);
        if(f > f1 * f2)
            return -1F;
        else
            return f / f1;
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = new GunProperties();
        gunproperties.weaponType = 16;
        return gunproperties;
    }
}
