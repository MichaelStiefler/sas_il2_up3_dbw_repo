// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

public class BombType3AntiAir extends com.maddox.il2.objects.weapons.Bomb
{

    public BombType3AntiAir()
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
        for(int i = 0; i < 145; i++)
        {
            orient.set(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 360F), com.maddox.il2.ai.World.Rnd().nextFloat(-90F, 90F), com.maddox.il2.ai.World.Rnd().nextFloat(-180F, 180F));
            getSpeed(vector3d);
            vector3d.add(com.maddox.il2.ai.World.Rnd().nextDouble(-15D, 15D), com.maddox.il2.ai.World.Rnd().nextDouble(-15D, 15D), com.maddox.il2.ai.World.Rnd().nextDouble(-15D, 15D));
            com.maddox.il2.objects.weapons.RocketPhBall2 rocketphball2 = new RocketPhBall2();
            rocketphball2.pos.setUpdateEnable(true);
            rocketphball2.pos.setAbs(point3d, orient);
            rocketphball2.pos.reset();
            rocketphball2.start(-1F);
            rocketphball2.setOwner(actor, false, false, false);
            rocketphball2.setSpeed(vector3d);
            if(i % 4 == 0)
                com.maddox.il2.engine.Eff3DActor.New(rocketphball2, null, null, 3F, "effects/Smokes/SmokeBlack_BuletteTrail.eff", 30F);
        }

        postDestroy();
    }

    private long t1;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombType3AntiAir.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/No3AntiAirBombJ/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 10F);
        com.maddox.rts.Property.set(class1, "power", 0.15F);
        com.maddox.rts.Property.set(class1, "powerType", 1);
        com.maddox.rts.Property.set(class1, "kalibr", 0.18F);
        com.maddox.rts.Property.set(class1, "massa", 30F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
