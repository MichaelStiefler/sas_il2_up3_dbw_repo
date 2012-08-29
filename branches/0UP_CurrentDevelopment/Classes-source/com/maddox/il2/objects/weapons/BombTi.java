// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombTi.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.ActorLand;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombTi extends com.maddox.il2.objects.weapons.Bomb
{

    public BombTi()
    {
    }

    public void start()
    {
        super.start();
        drawing(false);
        t1 = com.maddox.rts.Time.current() + 0x1f9dcL + com.maddox.il2.ai.World.Rnd().nextLong(0L, 850L);
        t2 = com.maddox.rts.Time.current() + 0x1f8b0L + com.maddox.il2.ai.World.Rnd().nextLong(0L, 3800L);
        com.maddox.il2.engine.Eff3DActor.New(this, null, new Loc(), 1.0F, "3DO/Effects/Fireworks/TIRed.eff", (float)(t1 - com.maddox.rts.Time.current()) / 1000F);
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(actor instanceof com.maddox.il2.objects.ActorLand)
        {
            if(com.maddox.rts.Time.current() <= (t2 + t1) / 2L)
            {
                com.maddox.JGP.Point3d point3d = new Point3d();
                pos.getTime(com.maddox.rts.Time.current(), point3d);
                java.lang.Class class1 = getClass();
                float f = com.maddox.rts.Property.floatValue(class1, "power", 0.0F);
                int i = com.maddox.rts.Property.intValue(class1, "powerType", 0);
                float f1 = com.maddox.rts.Property.floatValue(class1, "radius", 1.0F);
                com.maddox.il2.ai.MsgExplosion.send(actor, s1, point3d, getOwner(), M, f, i, f1);
                com.maddox.JGP.Vector3d vector3d = new Vector3d();
                getSpeed(vector3d);
                vector3d.x *= 0.5D;
                vector3d.y *= 0.5D;
                vector3d.z = 1.0D;
                setSpeed(vector3d);
            }
        } else
        {
            super.msgCollision(actor, s, s1);
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    private long t1;
    private long t2;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTi.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/15kgFragJ/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 30F);
        com.maddox.rts.Property.set(class1, "power", 7.5F);
        com.maddox.rts.Property.set(class1, "powerType", 1);
        com.maddox.rts.Property.set(class1, "kalibr", 0.32F);
        com.maddox.rts.Property.set(class1, "massa", 15F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_mid");
    }
}
