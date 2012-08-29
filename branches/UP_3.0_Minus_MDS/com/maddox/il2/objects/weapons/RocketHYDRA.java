// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   RocketHYDRA.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketHVAR2

public class RocketHYDRA extends com.maddox.il2.objects.weapons.RocketHVAR2
{

    public RocketHYDRA()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketHYDRA.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/2-75inch/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sprite", "3DO/Effects/Rocket/firesprite.eff");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "flame", "3DO/Effects/Rocket/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitColor", ((java.lang.Object) (new Color3f(1.0F, 1.0F, 0.5F))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitLen", 25F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitMax", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.rocket_82");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 20F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeLife", 999.999F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeFire", 4F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "force", 800F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 9.6F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.06985F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 11F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massaEnd", 6F);
    }
}
