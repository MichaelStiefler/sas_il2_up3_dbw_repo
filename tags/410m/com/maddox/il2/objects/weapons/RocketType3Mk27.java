// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketType3Mk27.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket, RocketPhBall

public class RocketType3Mk27 extends com.maddox.il2.objects.weapons.Rocket
{

    public RocketType3Mk27()
    {
    }

    protected void doExplosion(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        doFireContaineds();
    }

    protected void doExplosionAir()
    {
        doFireContaineds();
    }

    private final void doFireContaineds()
    {
        com.maddox.il2.objects.effects.Explosions.AirFlak(pos.getAbsPoint(), 1);
        com.maddox.il2.engine.Actor actor = null;
        if(com.maddox.il2.engine.Actor.isValid(getOwner()))
            actor = getOwner();
        com.maddox.JGP.Point3d point3d = new Point3d(pos.getAbsPoint());
        com.maddox.il2.engine.Orient orient = new Orient();
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        for(int i = 0; i < 145; i++)
        {
            orient.set(com.maddox.il2.ai.World.Rnd().nextFloat(-180F, 180F), com.maddox.il2.ai.World.Rnd().nextFloat(-180F, 180F), com.maddox.il2.ai.World.Rnd().nextFloat(-180F, 180F));
            getSpeed(vector3d);
            vector3d.scale(0.20000000000000001D);
            vector3d.add(com.maddox.il2.ai.World.Rnd().nextDouble(-75D, 75D), com.maddox.il2.ai.World.Rnd().nextDouble(-75D, 75D), com.maddox.il2.ai.World.Rnd().nextDouble(-75D, 75D));
            com.maddox.il2.objects.weapons.RocketPhBall rocketphball = new RocketPhBall();
            rocketphball.start(-1F);
            ((com.maddox.il2.objects.weapons.Rocket) (rocketphball)).pos.setUpdateEnable(true);
            ((com.maddox.il2.objects.weapons.Rocket) (rocketphball)).pos.setAbs(point3d, orient);
            ((com.maddox.il2.objects.weapons.Rocket) (rocketphball)).pos.reset();
            rocketphball.setOwner(actor, false, false, false);
            rocketphball.setSpeed(vector3d);
        }

        postDestroy();
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketType3Mk27.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Type3No6Mk27B(R)Model1/mono.sim");
        com.maddox.rts.Property.set(class1, "sprite", "3DO/Effects/Tracers/GuidedRocket/Black.eff");
        com.maddox.rts.Property.set(class1, "flame", "3DO/Effects/Rocket/mono.sim");
        com.maddox.rts.Property.set(class1, "smoke", "3DO/Effects/Tracers/GuidedRocket/White.eff");
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
        com.maddox.rts.Property.set(class1, "emitLen", 50F);
        com.maddox.rts.Property.set(class1, "emitMax", 1.0F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(class1, "radius", 40F);
        com.maddox.rts.Property.set(class1, "timeLife", 10F);
        com.maddox.rts.Property.set(class1, "timeFire", 10F);
        com.maddox.rts.Property.set(class1, "force", 10000F);
        com.maddox.rts.Property.set(class1, "power", 2.2275F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.3F);
        com.maddox.rts.Property.set(class1, "massa", 58.806F);
        com.maddox.rts.Property.set(class1, "massaEnd", 49.896F);
    }
}