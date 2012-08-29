// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Cargo.java

package com.maddox.il2.objects.trains;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Spawn;

// Referenced classes of package com.maddox.il2.objects.trains:
//            Wagon, Train, WagonSpawn

public class Cargo extends com.maddox.il2.objects.trains.Wagon
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener
{
    public static class SPAWN
        implements com.maddox.il2.objects.trains.WagonSpawn
    {

        public com.maddox.il2.objects.trains.Wagon wagonSpawn(com.maddox.il2.objects.trains.Train train)
        {
            return new Cargo(train);
        }

        public SPAWN()
        {
        }
    }


    protected void explode(com.maddox.il2.engine.Actor actor)
    {
        new com.maddox.rts.MsgAction(0.0D) {

            public void doAction()
            {
                com.maddox.JGP.Point3d point3d = new Point3d();
                pos.getAbs(point3d);
                com.maddox.il2.objects.effects.Explosions.ExplodeVagonArmor(point3d, point3d, 2.0F);
            }

        }
;
        if((crushSeed & 1) == 0)
            new com.maddox.rts.MsgAction(0.80000000000000004D) {

                public void doAction()
                {
                    com.maddox.JGP.Point3d point3d = new Point3d();
                    pos.getAbs(point3d);
                    com.maddox.il2.objects.effects.Explosions.ExplodeVagonArmor(point3d, point3d, 2.0F);
                }

            }
;
        new com.maddox.il2.objects.trains.Wagon.MyMsgAction(0.42999999999999999D, this, actor) {

            public void doAction(java.lang.Object obj)
            {
                com.maddox.JGP.Point3d point3d = new Point3d();
                pos.getAbs(point3d);
                float f = 180F;
                int i = 0;
                float f1 = 140F;
                com.maddox.il2.ai.MsgExplosion.send((com.maddox.il2.engine.Actor)obj, "Body", point3d, (com.maddox.il2.engine.Actor)obj2, 0.0F, f, i, f1);
            }

        }
;
        new com.maddox.rts.MsgAction(0.69999999999999996D, new Wagon.Pair(this, this, actor)) {

            public void doAction(java.lang.Object obj)
            {
                com.maddox.il2.engine.Actor actor1 = getOwner();
                if(actor1 != null)
                    ((com.maddox.il2.objects.trains.Train)actor1).wagonDied(((com.maddox.il2.objects.trains.Wagon.Pair)obj).victim, ((com.maddox.il2.objects.trains.Wagon.Pair)obj).initiator);
                life = -1F;
                ActivateMesh();
            }

        }
;
        new com.maddox.rts.MsgAction(1.2D, this) {

            public void doAction(java.lang.Object obj)
            {
                com.maddox.il2.objects.trains.Wagon wagon = (com.maddox.il2.objects.trains.Wagon)obj;
                com.maddox.il2.engine.Eff3DActor.New(wagon, new HookNamed(wagon, "Select1"), null, 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", 6F);
            }

        }
;
    }

    public Cargo(com.maddox.il2.objects.trains.Train train)
    {
        super(train, com.maddox.il2.objects.trains.Cargo.getMeshName(0), com.maddox.il2.objects.trains.Cargo.getMeshName(1));
        life = 0.009F;
        ignoreTNT = 0.12F;
        killTNT = 0.7F;
        bodyMaterial = 3;
    }

    private static java.lang.String getMeshName(int i)
    {
        switch(com.maddox.il2.ai.World.cur().camouflage)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
        default:
            return "3do/Trains/Cargo" + (i != 1 ? "" : "_Dmg") + "/hier.him";
        }
    }

    public static java.lang.String getMeshNameForEditor()
    {
        return com.maddox.il2.objects.trains.Cargo.getMeshName(0);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static java.lang.Class cls;

    static 
    {
        cls = com.maddox.il2.objects.trains.Cargo.class;
        com.maddox.rts.Spawn.add(cls, new SPAWN());
    }
}
