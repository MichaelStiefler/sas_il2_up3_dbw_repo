// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombTiRd.java

package com.maddox.il2.objects.weapons;

import java.util.Random;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombFOFlare extends com.maddox.il2.objects.weapons.Bomb
{

    public BombFOFlare()
    {
    }

    protected boolean haveSound()
    {
        return false;
    }

    public void start()
    {
        super.start();
        ttcurTM = World.Rnd().nextFloat(0.1F, 0.2F);
    	t1 = com.maddox.rts.Time.current() + 0x68dbcL + com.maddox.il2.ai.World.Rnd().nextLong(0L, 850L);
        t2 = com.maddox.rts.Time.current() + 0x68c90L + com.maddox.il2.ai.World.Rnd().nextLong(0L, 3800L);
  	    drawing(false);
        com.maddox.il2.engine.Eff3DActor.New(this, null, new Loc(), 1.0F, "Effects/Smokes/WPsmoke.eff", (float)(t1 - Time.current()) / 1000F);
    }

    public void interpolateTick()
    {
        super.interpolateTick();
        if (!bFinsDeployed && (curTm > ttcurTM))
        {
            bFinsDeployed = true;
            this.S *= 300F; // Here the front square is changed. Apply some offset factor of your choice, the higher the factor, the more the bomb will decelerate.
        }
    }
    
    public void msgCollision(Actor actor, String s, String s1)
    {
        if(actor instanceof ActorLand)
        {
            if(Time.current() <= (t2 + t1) / 2L)
            {
                Point3d point3d = new Point3d();
                pos.getTime(Time.current(), point3d);
                Class class1 = getClass();
                float f = Property.floatValue(class1, "power", 0.0F);
                int i = Property.intValue(class1, "powerType", 0);
                float f1 = Property.floatValue(class1, "radius", 10.0F);
                MsgExplosion.send(actor, s1, point3d, getOwner(), M, f, i, f1);
                Vector3d vector3d = new Vector3d();
                getSpeed(vector3d);
                vector3d.x *= 0.5D;
                vector3d.y *= 0.5D;
                vector3d.z = 1.0D;
                setSpeed(vector3d);
                if(counter > 200 && !marked)
                {
                    marked = true;
                    HUD.logCenter("                                                                              Artillery Firing!");
                    counter = 0;
                }
                Random random = new Random();
                int j = random.nextInt(10);
                j -= 5;
                if(marked && counter > 25 + j)
                {
                    int k = random.nextInt(150);
                    int l = k - 75;
                    point3d.x += l;
                    k = random.nextInt(150);
                    l = k - 75;
                    point3d.y += l;
                    Explosions.generate(actor, point3d, 25F, 0, 136F, !Mission.isNet());
                    MsgExplosion.send(actor, s, point3d, getOwner(), 0.0F, 25F, 0, 136F);
                    counter = 0;
                }
                counter++;
            }
        } else
        {
            super.msgCollision(actor, s, s1);
        }
    }

    private long t1;
    private long t2;
    private int counter;
    private boolean marked;
    private boolean bFinsDeployed;
    private float ttcurTM;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombFOFlare.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Null/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 0.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 0.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 2);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.0508F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 1.2F);
    }
}