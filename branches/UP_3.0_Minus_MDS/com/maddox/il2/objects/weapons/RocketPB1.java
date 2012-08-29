// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   RocketPB1.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket

public class RocketPB1 extends com.maddox.il2.objects.weapons.Rocket
{

    public RocketPB1()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketPB1.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/rs-82/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sprite", "3do/effects/rocket/firesprite.eff");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "flame", "3do/effects/rocket/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "smoke", "3do/effects/rocket/rocketsmokewhite.eff");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitColor", ((java.lang.Object) (new Color3f(1.0F, 1.0F, 0.5F))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitLen", 50F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitMax", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 0.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeLife", 999.999F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeFire", 4F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "force", 1300F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 0.36F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.082F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 6.7F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massaEnd", 2.9F);
    }
}
