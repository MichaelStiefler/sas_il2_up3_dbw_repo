// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketFFARMk4.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket

public class RocketFFARMk4 extends com.maddox.il2.objects.weapons.Rocket
{

    public RocketFFARMk4()
    {
    }

    public void start(float f, int i)
    {
        setMesh("3DO/Arms/FFAR_275inch/mono_launched.sim");
        super.start(f, i);
        super.speed.normalize();
        super.speed.scale(525D);
        super.noGDelay = -1L;
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        return java.lang.Class.forName(x0);
        java.lang.ClassNotFoundException x1;
        x1;
        throw new NoClassDefFoundError(x1.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketFFARMk4.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/FFAR_275inch/mono.sim");
        com.maddox.rts.Property.set(class1, "sprite", "3DO/Effects/Tracers/GuidedRocket/Black.eff");
        com.maddox.rts.Property.set(class1, "flame", "3DO/Effects/Rocket/mono.sim");
        com.maddox.rts.Property.set(class1, "smoke", "3DO/Effects/Tracers/GuidedRocket/White.eff");
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
        com.maddox.rts.Property.set(class1, "emitLen", 50F);
        com.maddox.rts.Property.set(class1, "emitMax", 1.0F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(class1, "radius", 12F);
        com.maddox.rts.Property.set(class1, "timeLife", 60F);
        com.maddox.rts.Property.set(class1, "timeFire", 1.1F);
        com.maddox.rts.Property.set(class1, "force", 1300F);
        com.maddox.rts.Property.set(class1, "power", 5F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.055F);
        com.maddox.rts.Property.set(class1, "massa", 3.99F);
        com.maddox.rts.Property.set(class1, "massaEnd", 3F);
    }
}
