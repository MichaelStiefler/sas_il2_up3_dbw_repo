// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketROFS132.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket

public class RocketROFS132 extends com.maddox.il2.objects.weapons.Rocket
{

    public RocketROFS132()
    {
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        pos.getTime(com.maddox.rts.Time.current(), p);
        com.maddox.il2.ai.MsgExplosion.send(actor, s, p, getOwner(), 10.1F, 8.4F, 1, 1200F);
        super.doExplosion(actor, s);
    }

    protected void doExplosionAir()
    {
        pos.getTime(com.maddox.rts.Time.current(), p);
        com.maddox.il2.ai.MsgExplosion.send(null, null, p, getOwner(), 10.1F, 8.4F, 1, 1200F);
        super.doExplosionAir();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static com.maddox.JGP.Point3d p = new Point3d();

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketROFS132.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/RS-132-45/mono.sim");
        com.maddox.rts.Property.set(class1, "sprite", "3do/effects/rocket/firesprite.eff");
        com.maddox.rts.Property.set(class1, "flame", "3do/effects/rocket/mono.sim");
        com.maddox.rts.Property.set(class1, "smoke", "3do/effects/rocket/rocketsmokewhite.eff");
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
        com.maddox.rts.Property.set(class1, "emitLen", 75F);
        com.maddox.rts.Property.set(class1, "emitMax", 1.0F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(class1, "radius", 112F);
        com.maddox.rts.Property.set(class1, "timeLife", 2.12F);
        com.maddox.rts.Property.set(class1, "timeFire", 4F);
        com.maddox.rts.Property.set(class1, "force", 1300F);
        com.maddox.rts.Property.set(class1, "power", 8.4F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.132F);
        com.maddox.rts.Property.set(class1, "massa", 32F);
        com.maddox.rts.Property.set(class1, "massaEnd", 19F);
    }
}
