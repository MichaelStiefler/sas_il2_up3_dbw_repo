// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombGun750lbsM117.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            BombGun

public class BombGun750lbsM117 extends com.maddox.il2.objects.weapons.BombGun
{

    public BombGun750lbsM117()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGun750lbsM117.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bulletClass", ((java.lang.Object) (com.maddox.il2.objects.weapons.Bomb750lbsM117.class)));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bullets", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "shotFreq", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "external", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.bombgun");
    }
}
