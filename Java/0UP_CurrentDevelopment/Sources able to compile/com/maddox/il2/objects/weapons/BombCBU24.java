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

public class BombCBU24 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombCBU24()
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
        if(t1 > com.maddox.rts.Time.current())
            doFireContaineds();
        super.msgCollision(actor, s, s1);
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
        for(int i = 0; i < 150; i++)
        {
            orient.set(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 360F), com.maddox.il2.ai.World.Rnd().nextFloat(-90F, 90F), com.maddox.il2.ai.World.Rnd().nextFloat(-180F, 180F));
            getSpeed(vector3d);
            vector3d.add(com.maddox.il2.ai.World.Rnd().nextDouble(-15D, 15D), com.maddox.il2.ai.World.Rnd().nextDouble(-15D, 15D), com.maddox.il2.ai.World.Rnd().nextDouble(-15D, 15D));
            com.maddox.il2.objects.weapons.Bomblet2Kg bomblet2kg = new Bomblet2Kg();
            ((com.maddox.il2.objects.weapons.Bomb) (bomblet2kg)).pos.setUpdateEnable(true);
            ((com.maddox.il2.objects.weapons.Bomb) (bomblet2kg)).pos.setAbs(point3d, orient);
            ((com.maddox.il2.objects.weapons.Bomb) (bomblet2kg)).pos.reset();
            bomblet2kg.start();
            bomblet2kg.setOwner(actor, false, false, false);
            bomblet2kg.setSpeed(vector3d);
        }

        postDestroy();
    }

    private long t1;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombCBU24.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/CBU24/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 1.0F);
        com.maddox.rts.Property.set(class1, "power", 0.15F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.18F);
        com.maddox.rts.Property.set(class1, "massa", 362.336F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_std");
    }
}
