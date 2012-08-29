// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketWfrGr21.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket

public class RocketWfrGr21 extends com.maddox.il2.objects.weapons.Rocket
{

    public RocketWfrGr21()
    {
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketWfrGr21.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/WfrGr21Rocket/mono.sim");
        com.maddox.rts.Property.set(class1, "sprite", "3do/effects/rocket/firesprite.eff");
        com.maddox.rts.Property.set(class1, "flame", "3do/effects/rocket/mono.sim");
        com.maddox.rts.Property.set(class1, "smoke", "3do/effects/rocket/rocketsmokewhite.eff");
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
        com.maddox.rts.Property.set(class1, "emitLen", 50F);
        com.maddox.rts.Property.set(class1, "emitMax", 1.0F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(class1, "radius", 790F);
        com.maddox.rts.Property.set(class1, "timeLife", 20F);
        com.maddox.rts.Property.set(class1, "timeFire", 1.8F);
        com.maddox.rts.Property.set(class1, "force", 1720F);
        com.maddox.rts.Property.set(class1, "power", 40.8F);
        com.maddox.rts.Property.set(class1, "powerType", 1);
        com.maddox.rts.Property.set(class1, "kalibr", 0.21F);
        com.maddox.rts.Property.set(class1, "massa", 110F);
        com.maddox.rts.Property.set(class1, "massaEnd", 91.6F);
    }
}
