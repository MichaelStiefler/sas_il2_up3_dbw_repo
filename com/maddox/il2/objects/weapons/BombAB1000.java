// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombAB1000.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb, BombB22EZ

public class BombAB1000 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombAB1000()
    {
    }

    public void start()
    {
        super.start();
        t1 = com.maddox.rts.Time.current() + 1000L * (long)java.lang.Math.max(delayExplosion, 2.21F) + com.maddox.il2.ai.World.Rnd().nextLong(-250L, 250L);
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
        if(charge < 6)
        {
            com.maddox.il2.objects.effects.Explosions.generateExplosion(this, pos.getCurrentPoint(), 0.01F, 0, 10F, 0.0D);
            com.maddox.il2.engine.Actor actor = getOwner();
            if(!com.maddox.il2.engine.Actor.isValid(actor))
                return;
            com.maddox.JGP.Point3d point3d = new Point3d();
            com.maddox.il2.engine.Orient orient = new Orient();
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
            com.maddox.il2.engine.Loc loc = new Loc();
            com.maddox.il2.engine.Loc loc1 = new Loc(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
            pos.getCurrent(loc);
            findHook("_Spawn0" + charge).computePos(this, loc, loc1);
            getSpeed(vector3d1);
            for(int i = 0; i < 74; i++)
            {
                loc1.get(point3d, orient);
                orient.increment(com.maddox.il2.ai.World.Rnd().nextFloat(-135F, 135F), com.maddox.il2.ai.World.Rnd().nextFloat(-17.5F, 17.5F), com.maddox.il2.ai.World.Rnd().nextFloat(-0.5F, 0.5F));
                vector3d.set(1.0D, 0.0D, 0.0D);
                orient.transform(vector3d);
                vector3d.scale(com.maddox.il2.ai.World.Rnd().nextDouble(5D, 38.200000000000003D));
                vector3d.add(vector3d1);
                com.maddox.il2.objects.weapons.BombB22EZ bombb22ez = new BombB22EZ();
                ((com.maddox.il2.objects.weapons.Bomb) (bombb22ez)).pos.setUpdateEnable(true);
                ((com.maddox.il2.objects.weapons.Bomb) (bombb22ez)).pos.setAbs(point3d, orient);
                ((com.maddox.il2.objects.weapons.Bomb) (bombb22ez)).pos.reset();
                bombb22ez.start();
                bombb22ez.setOwner(actor, false, false, false);
                bombb22ez.setSpeed(vector3d);
                if(i % 4 == 0)
                    com.maddox.il2.engine.Eff3DActor.New(bombb22ez, null, null, 3F, "effects/Smokes/SmokeBlack_BuletteTrail.eff", 30F);
            }

            t1 = com.maddox.rts.Time.current() + 1000L;
        } else
        {
            com.maddox.il2.objects.effects.Explosions.AirFlak(pos.getAbsPoint(), 1);
            postDestroy();
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private long t1;
    private int charge;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombAB1000.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/AB-1000/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 10F);
        com.maddox.rts.Property.set(class1, "power", 0.15F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.6604F);
        com.maddox.rts.Property.set(class1, "massa", 905.3F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_big");
    }
}
