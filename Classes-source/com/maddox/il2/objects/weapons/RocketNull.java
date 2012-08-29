// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketNull.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.Actor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket

public class RocketNull extends com.maddox.il2.objects.weapons.Rocket
{

    public void start(float f, int i)
    {
        super.start(f, i);
        drawing(false);
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor1, java.lang.String s1)
    {
    }

    protected void doExplosionAir()
    {
    }

    public RocketNull()
    {
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketNull.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Null/mono.sim");
        com.maddox.rts.Property.set(class1, "sprite", (java.lang.Object)null);
        com.maddox.rts.Property.set(class1, "flame", (java.lang.Object)null);
        com.maddox.rts.Property.set(class1, "smoke", (java.lang.Object)null);
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(0.0F, 0.0F, 0.0F));
        com.maddox.rts.Property.set(class1, "emitLen", 0.0F);
        com.maddox.rts.Property.set(class1, "emitMax", 0.0F);
        com.maddox.rts.Property.set(class1, "sound", (java.lang.Object)null);
        com.maddox.rts.Property.set(class1, "radius", 0.1F);
        com.maddox.rts.Property.set(class1, "timeLife", 999.999F);
        com.maddox.rts.Property.set(class1, "timeFire", 0.0F);
        com.maddox.rts.Property.set(class1, "force", 0.0F);
        com.maddox.rts.Property.set(class1, "power", 0.01485F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.001F);
        com.maddox.rts.Property.set(class1, "massa", 0.01485F);
        com.maddox.rts.Property.set(class1, "massaEnd", 0.01485F);
    }
}
