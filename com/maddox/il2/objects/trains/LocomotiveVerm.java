// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LocomotiveVerm.java

package com.maddox.il2.objects.trains;

import com.maddox.JGP.Point3d;
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

public class LocomotiveVerm extends com.maddox.il2.objects.trains.Wagon
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener
{
    public static class SPAWN
        implements com.maddox.il2.objects.trains.WagonSpawn
    {

        public com.maddox.il2.objects.trains.Wagon wagonSpawn(com.maddox.il2.objects.trains.Train train)
        {
            return new LocomotiveVerm(train);
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
        new com.maddox.rts.MsgAction(1.0D, new Wagon.Pair(this, this, actor)) {

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

    protected LocomotiveVerm(com.maddox.il2.objects.trains.Train train, java.lang.String s, java.lang.String s1)
    {
        super(train, s, s1);
        life = 0.015F;
        ignoreTNT = 0.5F;
        killTNT = 3.5F;
        bodyMaterial = 2;
        pipe = com.maddox.il2.engine.Eff3DActor.New(this, new HookNamed(this, "Vapor"), null, 1.0F, "Effects/Smokes/SmokeBlack_Locomotive.eff", -1F);
    }

    public LocomotiveVerm(com.maddox.il2.objects.trains.Train train)
    {
        this(train, com.maddox.il2.objects.trains.LocomotiveVerm.getMeshName(0), com.maddox.il2.objects.trains.LocomotiveVerm.getMeshName(1));
    }

    private static java.lang.String getMeshName(int i)
    {
        switch(com.maddox.il2.ai.World.cur().camouflage)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
        default:
            return "3do/Trains/Prvz_B" + (i != 1 ? "" : "_Dmg") + "/hier.him";
        }
    }

    public static java.lang.String getMeshNameForEditor()
    {
        return com.maddox.il2.objects.trains.LocomotiveVerm.getMeshName(0);
    }

    void place(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, boolean flag, boolean flag1)
    {
        super.place(point3d, point3d1, flag, flag1);
        if(pipe == null)
            return;
        float f;
        if(!IsDead())
        {
            f = ((com.maddox.il2.objects.trains.Train)getOwner()).getEngineSmokeKoef();
            if(f == 0.0F && ((com.maddox.il2.objects.trains.Train)getOwner()).stoppedForever())
                f = -1F;
        } else
        {
            f = -1F;
        }
        if(f >= 0.0F)
        {
            pipe._setIntesity(f);
        } else
        {
            pipe._finish();
            pipe = null;
        }
    }

    protected void ActivateMesh()
    {
        if(IsDead() && pipe != null)
        {
            pipe._finish();
            pipe = null;
        }
        super.ActivateMesh();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static java.lang.Class cls;
    protected com.maddox.il2.engine.Eff3DActor pipe;

    static 
    {
        cls = com.maddox.il2.objects.trains.LocomotiveVerm.class;
        com.maddox.rts.Spawn.add(cls, new SPAWN());
    }
}
