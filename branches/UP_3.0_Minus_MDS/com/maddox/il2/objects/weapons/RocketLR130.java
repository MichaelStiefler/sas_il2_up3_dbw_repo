// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   RocketLR130.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket

public class RocketLR130 extends com.maddox.il2.objects.weapons.Rocket
{

    public RocketLR130()
    {
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketLR130.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/LR130/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sprite", "3DO/Effects/Rocket/firesprite.eff");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "flame", "3DO/Effects/Rocket/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitColor", ((java.lang.Object) (new Color3f(1.0F, 1.0F, 0.5F))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitLen", 50F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitMax", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 60F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeLife", 999.999F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeFire", 1.2F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "force", 1620F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 35F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.13F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 48.3F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massaEnd", 8.4F);
    }
}
