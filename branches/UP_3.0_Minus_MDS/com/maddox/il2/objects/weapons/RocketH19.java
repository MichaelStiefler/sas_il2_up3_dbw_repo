// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   RocketH19.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket

public class RocketH19 extends com.maddox.il2.objects.weapons.Rocket
{

    public RocketH19()
    {
    }

    public void start(float f)
    {
        setMesh("3DO/Arms/RocketH19-OPEN/mono.sim");
        super.start(f);
        speed.normalize();
        speed.scale(525D);
        noGDelay = -1L;
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketH19.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/RocketH19-CLOSED/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sprite", "3DO/Effects/Rocket/firesprite.eff");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "flame", "3DO/Effects/Rocket/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitColor", ((java.lang.Object) (new Color3f(1.0F, 1.0F, 0.5F))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitLen", 25F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitMax", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 20F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeLife", 999.999F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeFire", 4F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "force", 1300F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 3.6F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.07F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 8.4F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massaEnd", 5.5F);
    }
}
