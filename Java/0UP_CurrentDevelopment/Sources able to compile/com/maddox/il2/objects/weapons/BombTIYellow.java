package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombTIYellow extends com.maddox.il2.objects.weapons.Bomb
{

    public BombTIYellow()
    {
    }

    public void start()
    {
        super.start();
        t1 = com.maddox.rts.Time.current() + 1000L * (long)java.lang.Math.max(delayExplosion, 4.21F) + com.maddox.il2.ai.World.Rnd().nextLong(-250L, 250L);
        charge = 0;
        setName("qqq");
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
        charge++;
        if((double)charge < 1.01D)
        {
            com.maddox.il2.engine.Actor actor = getOwner();
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                com.maddox.JGP.Point3d point3d = new Point3d();
                com.maddox.il2.engine.Orient orient = new Orient();
                com.maddox.JGP.Vector3d vector3d = new Vector3d();
                com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
                com.maddox.il2.engine.Loc loc = new Loc();
                com.maddox.il2.engine.Loc loc1 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                pos.getCurrent(loc);
                findHook("_Spawn0" + charge).computePos(this, loc, loc1);
                getSpeed(vector3d1);
                for(int i = 0; i < 60; i++)
                {
                    loc1.get(point3d, orient);
                    orient.increment(com.maddox.il2.ai.World.Rnd().nextFloat(-85F, 85F), com.maddox.il2.ai.World.Rnd().nextFloat(-85.5F, 85.5F), com.maddox.il2.ai.World.Rnd().nextFloat(-0.5F, 0.5F));
                    vector3d.set(1.0D, 0.0D, 0.0D);
                    orient.transform(vector3d);
                    vector3d.scale(com.maddox.il2.ai.World.Rnd().nextDouble(5D, 12.199999999999999D));
                    vector3d.add(vector3d1);
                    com.maddox.il2.objects.weapons.BombTiYw bombtiyw = new BombTiYw();
                    bombtiyw.pos.setUpdateEnable(true);
                    bombtiyw.pos.setAbs(point3d, orient);
                    bombtiyw.pos.reset();
                    bombtiyw.start();
                    bombtiyw.setOwner(actor, false, false, false);
                    bombtiyw.setSpeed(vector3d);
                }

                t1 = com.maddox.rts.Time.current() + 1000L;
            }
        }
    }

    private long t1;
    private int charge;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTIYellow.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Flare/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 0.0F);
        com.maddox.rts.Property.set(class1, "power", 0.0F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 125F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_big");
    }
}
