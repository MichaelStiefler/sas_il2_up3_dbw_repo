// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LocomotiveTrailorUSSR.java

package com.maddox.il2.objects.trains;

import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Spawn;

// Referenced classes of package com.maddox.il2.objects.trains:
//            Wagon, Train, WagonSpawn

public class LocomotiveTrailorUSSR extends com.maddox.il2.objects.trains.Wagon
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener
{
    public static class SPAWN
        implements com.maddox.il2.objects.trains.WagonSpawn
    {

        public com.maddox.il2.objects.trains.Wagon wagonSpawn(com.maddox.il2.objects.trains.Train train)
        {
            return new LocomotiveTrailorUSSR(train);
        }

        public SPAWN()
        {
        }
    }


    protected void explode(com.maddox.il2.engine.Actor actor)
    {
        new com.maddox.rts.MsgAction(0.0D, this) {

            public void doAction(java.lang.Object obj)
            {
                com.maddox.il2.objects.trains.Wagon wagon = (com.maddox.il2.objects.trains.Wagon)obj;
                com.maddox.il2.engine.Eff3DActor.New(wagon, new HookNamed(wagon, "Damage"), null, 1.0F, "Effects/Smokes/SmokeTrailorFC.eff", 32F);
            }

        }
;
        new com.maddox.rts.MsgAction(7D, new Wagon.Pair(this, this, actor)) {

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
    }

    public LocomotiveTrailorUSSR(com.maddox.il2.objects.trains.Train train)
    {
        super(train, com.maddox.il2.objects.trains.LocomotiveTrailorUSSR.getMeshName(0), com.maddox.il2.objects.trains.LocomotiveTrailorUSSR.getMeshName(1));
        life = 0.015F;
        ignoreTNT = 0.29F;
        killTNT = 1.9F;
        bodyMaterial = 2;
    }

    private static java.lang.String getMeshName(int i)
    {
        java.lang.String s;
        switch(com.maddox.il2.ai.World.cur().camouflage)
        {
        case 0: // '\0'
            s = "summer";
            break;

        case 1: // '\001'
            s = "winter";
            break;

        case 2: // '\002'
            s = "desert";
            break;

        default:
            s = "summer";
            break;
        }
        return "3do/Trains/PrvzB" + (i == 1 ? "_Dmg" : "") + "/" + s + "/hier.him";
    }

    public static java.lang.String getMeshNameForEditor()
    {
        return com.maddox.il2.objects.trains.LocomotiveTrailorUSSR.getMeshName(0);
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
        cls = com.maddox.il2.objects.trains.LocomotiveTrailorUSSR.class;
        com.maddox.rts.Spawn.add(cls, new SPAWN());
    }
}
