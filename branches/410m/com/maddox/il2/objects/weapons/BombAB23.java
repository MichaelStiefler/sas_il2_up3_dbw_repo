// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombAB23.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb, BombSD2A

public class BombAB23 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombAB23()
    {
    }

    public void start()
    {
        super.start();
        t1 = com.maddox.rts.Time.current() + 1000L * (long)java.lang.Math.max(delayExplosion, 3F) + com.maddox.il2.ai.World.Rnd().nextLong(-250L, 250L);
    }

    public void interpolateTick()
    {
        super.interpolateTick();
        if(t1 < com.maddox.rts.Time.current())
            doFireContaineds();
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        super.msgCollision(actor, s, s1);
        if(t1 > com.maddox.rts.Time.current() && isFuseArmed())
            doFireContaineds();
    }

    private void doFireContaineds()
    {
        com.maddox.il2.objects.effects.Explosions.AirFlak(pos.getAbsPoint(), 1);
        com.maddox.il2.engine.Actor actor = null;
        if(com.maddox.il2.engine.Actor.isValid(getOwner()))
            actor = getOwner();
        com.maddox.JGP.Point3d point3d = new Point3d(pos.getAbsPoint());
        com.maddox.il2.engine.Orient orient = new Orient();
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        for(int i = 0; i < 23; i++)
        {
            orient.set(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 360F), com.maddox.il2.ai.World.Rnd().nextFloat(-90F, 90F), com.maddox.il2.ai.World.Rnd().nextFloat(-180F, 180F));
            getSpeed(vector3d);
            vector3d.add(com.maddox.il2.ai.World.Rnd().nextDouble(-20D, 20D), com.maddox.il2.ai.World.Rnd().nextDouble(-20D, 20D), com.maddox.il2.ai.World.Rnd().nextDouble(-20D, 20D));
            com.maddox.il2.objects.weapons.BombSD2A bombsd2a = new BombSD2A();
            bombsd2a.pos.setUpdateEnable(true);
            bombsd2a.pos.setAbs(point3d, orient);
            bombsd2a.pos.reset();
            bombsd2a.start();
            bombsd2a.setOwner(actor, false, false, false);
            bombsd2a.setSpeed(vector3d);
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

    private long t1;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombAB23.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/AB-23/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 1.0F);
        com.maddox.rts.Property.set(class1, "power", 0.15F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.18F);
        com.maddox.rts.Property.set(class1, "massa", 46F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
    }
}
