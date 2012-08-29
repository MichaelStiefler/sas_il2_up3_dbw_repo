// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombGunTorpMK13_1.java

package com.maddox.il2.objects.weapons;

import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            TorpedoGun, Bomb

public class BombGunTorpMK13_1 extends com.maddox.il2.objects.weapons.TorpedoGun
{

    public BombGunTorpMK13_1()
    {
    }

    public void setBombDelay(float f)
    {
        bombDelay = 0.0F;
        if(bomb != null)
            bomb.delayExplosion = bombDelay;
    }

    static java.lang.Class _mthclass$(java.lang.String string)
    {
        java.lang.Class var_class;
        try
        {
            var_class = java.lang.Class.forName(string);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return var_class;
    }

    static 
    {
        java.lang.Class var_class = com.maddox.il2.objects.weapons.BombGunTorpMK13_1.class;
        com.maddox.rts.Property.set(var_class, "bulletClass", com.maddox.il2.objects.weapons.BombTorpMk13_1.class);
        com.maddox.rts.Property.set(var_class, "bullets", 1);
        com.maddox.rts.Property.set(var_class, "shotFreq", 0.1F);
        com.maddox.rts.Property.set(var_class, "external", 1);
        com.maddox.rts.Property.set(var_class, "sound", "weapon.bombgun_torpedo");
    }
}
