// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombPhBall.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.ActorLand;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb, Ballistics

public class BombPhBall extends com.maddox.il2.objects.weapons.Bomb
{

    public BombPhBall()
    {
    }

    protected boolean haveSound()
    {
        return index % 8 == 0;
    }

    public void start()
    {
        super.start();
        drawing(false);
        t1 = com.maddox.rts.Time.current() + 1500L + com.maddox.il2.ai.World.Rnd().nextLong(0L, 850L);
        t2 = com.maddox.rts.Time.current() + 2200L + com.maddox.il2.ai.World.Rnd().nextLong(0L, 3800L);
        com.maddox.il2.engine.Eff3DActor.New(this, null, new Loc(), 1.0F, "3DO/Effects/Fireworks/PhosfourousBall.eff", (float)(t1 - com.maddox.rts.Time.current()) / 1000F);
    }

    public void interpolateTick()
    {
        curTm += com.maddox.rts.Time.tickLenFs();
        com.maddox.il2.objects.weapons.Ballistics.updateBomb(this, M, S, J, DistFromCMtoStab);
        updateSound();
        if(t1 < com.maddox.rts.Time.current())
        {
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                com.maddox.il2.engine.Eff3DActor eff3dactor = com.maddox.il2.engine.Eff3DActor.New(this, null, new Loc(), 1.0F, "3DO/Effects/Fireworks/PhosfourousFire.eff", (float)(t2 - t1) / 1000F);
                if(index % 30 == 0)
                {
                    com.maddox.il2.engine.LightPointActor lightpointactor = new LightPointActor(new LightPointWorld(), new Point3d());
                    lightpointactor.light.setColor(1.0F, 1.0F, 0.0F);
                    lightpointactor.light.setEmit(1.0F, 300F);
                    ((com.maddox.il2.engine.Actor) (eff3dactor)).draw.lightMap().put("light", lightpointactor);
                }
            }
            t1 = t2 + 1L;
        }
        if(t2 < com.maddox.rts.Time.current())
            postDestroy();
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(actor instanceof com.maddox.il2.objects.ActorLand)
        {
            if(com.maddox.rts.Time.current() > (t2 + t1) / 2L)
            {
                return;
            } else
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
                return;
            }
        } else
        {
            super.msgCollision(actor, s, s1);
            return;
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
    private long t2;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombPhBall.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Null/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 5.5F);
        com.maddox.rts.Property.set(class1, "power", 10F);
        com.maddox.rts.Property.set(class1, "powerType", 2);
        com.maddox.rts.Property.set(class1, "kalibr", 0.1F);
        com.maddox.rts.Property.set(class1, "massa", 0.5F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_phball");
    }
}
