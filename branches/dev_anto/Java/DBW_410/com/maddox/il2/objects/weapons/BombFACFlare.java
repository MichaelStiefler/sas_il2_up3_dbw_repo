// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombTiRd.java

package com.maddox.il2.objects.weapons;

import java.util.List;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeScout;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombFACFlare extends com.maddox.il2.objects.weapons.Bomb
{

    public BombFACFlare()
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
                 counter++;
                 if(counter == 1000)
                     pilot.Group.setGroupTask(1);
                 Point3d point3d = new Point3d();
                 pos.getTime(Time.current(), point3d);
                 Class class1 = getClass();
                 float f = Property.floatValue(class1, "power", 0.0F);
                 int i = Property.intValue(class1, "powerType", 0);
                 float f1 = Property.floatValue(class1, "radius", 1.0F);
                 MsgExplosion.send(actor, s1, point3d, getOwner(), M, f, i, f1);
                 Vector3d vector3d = new Vector3d();
                 getSpeed(vector3d);
                 vector3d.x *= 0.5D;
                 vector3d.y *= 0.5D;
                 vector3d.z = 1.0D;
                 setSpeed(vector3d);
                 if(!marked)
                 {
                     List list = Engine.targets();
                     int j = list.size();
                     for(int k = 0; k < j; k++)
                     {
                         Actor actor1 = (Actor)list.get(k);
                         Aircraft aircraft = (Aircraft)actor1;
                         if(((actor1 instanceof TypeStormovik) || (actor1 instanceof TypeFighter)) && !(actor1 instanceof TypeScout) && actor1.pos.getAbsPoint().distance(point3d) < 15000D)
                         {
                             airgroup = ((Maneuver)((Aircraft)actor1).FM).Group;
                             pilot = (Pilot)((Aircraft)actor1).FM;
                             pilot.Group.setGroupTask(4);
                             pilot.Group.setGTargMode(0);
                             pilot.Group.setGTargMode(point3d, 100F);
                             Voice.speakOk(aircraft);
                         }
                         marked = true;
                     }

                 }
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
    private static AirGroup airgroup = null;
    private static Pilot pilot = null;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombFACFlare.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Null/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 0.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 0.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 2);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.0508F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 1.2F);
    }
}