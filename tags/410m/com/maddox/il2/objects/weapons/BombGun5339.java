// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombGun5339.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            TorpedoApparatus

public class BombGun5339 extends com.maddox.il2.objects.weapons.TorpedoApparatus
{

    public BombGun5339()
    {
    }

    public com.maddox.il2.ai.BulletEmitter detach(int i)
    {
        return null;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombGun5339.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.Bomb5339.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.01F);
        com.maddox.rts.Property.set(class1, "external", 0);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bombgun_torpedo");
    }
}
