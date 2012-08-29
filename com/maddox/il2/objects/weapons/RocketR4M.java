// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketR4M.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket

public class RocketR4M extends com.maddox.il2.objects.weapons.Rocket
{

    public RocketR4M()
    {
    }

    public void start(float f, int i)
    {
        setMesh("3DO/Arms/R4M-OPEN/mono.sim");
        super.start(f, i);
        speed.normalize();
        speed.scale(525D);
        noGDelay = -1L;
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketR4M.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/R4M-CLOSED/mono.sim");
        com.maddox.rts.Property.set(class1, "sprite", "3DO/Effects/Tracers/GuidedRocket/Black.eff");
        com.maddox.rts.Property.set(class1, "flame", "3DO/Effects/Rocket/mono.sim");
        com.maddox.rts.Property.set(class1, "smoke", "3DO/Effects/Tracers/GuidedRocket/White.eff");
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
        com.maddox.rts.Property.set(class1, "emitLen", 50F);
        com.maddox.rts.Property.set(class1, "emitMax", 1.0F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(class1, "radius", 5F);
        com.maddox.rts.Property.set(class1, "timeLife", 60F);
        com.maddox.rts.Property.set(class1, "timeFire", 60F);
        com.maddox.rts.Property.set(class1, "force", 0.0F);
        com.maddox.rts.Property.set(class1, "power", 0.52F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.0825F);
        com.maddox.rts.Property.set(class1, "massa", 4.4F);
        com.maddox.rts.Property.set(class1, "massaEnd", 4.4F);
        com.maddox.rts.Property.set(class1, "maxDeltaAngle", 1.75F);
    }
}
