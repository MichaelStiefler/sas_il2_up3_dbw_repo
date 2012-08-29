// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   BombCargo70.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.gb;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Chute;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombCargo70 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombCargo70()
    {
        chute = null;
        bOnChute = false;
    }

    protected boolean haveSound()
    {
        return false;
    }

    public void start()
    {
        super.start();
        ttcurTM = com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 1.75F);
    }

    public void interpolateTick()
    {
label0:
        {
            super.interpolateTick();
            ((com.maddox.il2.objects.weapons.Bomb)this).getSpeed(v3d);
            or.setAT0(v3d);
            ((com.maddox.il2.engine.Actor)this).pos.setAbs(or);
            if(bOnChute)
            {
                ((com.maddox.JGP.Tuple3d) (v3d)).scale(0.98999999999999999D);
                if(((com.maddox.JGP.Tuple3d) (v3d)).z < -5D)
                    v3d.z += 1.1F * com.maddox.rts.Time.tickConstLenFs();
                ((com.maddox.il2.objects.weapons.Bomb)this).setSpeed(v3d);
                if(com.maddox.il2.objects.weapons.Bomb.a == 0)
                    break label0;
            }
            if(super.curTm > ttcurTM)
            {
                bOnChute = true;
                chute = new Chute(((com.maddox.il2.engine.Actor) (this)));
                ((com.maddox.il2.engine.Actor) (chute)).collide(false);
                ((com.maddox.il2.engine.ActorMesh) (chute)).mesh().setScale(1.5F);
                ((com.maddox.il2.engine.Actor) (chute)).pos.setRel(new Point3d(2D, 0.0D, 0.0D), new Orient(0.0F, 90F, 0.0F));
            }
        }
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
label0:
        {
            if(actor instanceof com.maddox.il2.objects.ActorLand)
            {
                if(chute != null)
                    chute.landing();
                com.maddox.il2.engine.Loc loc = new Loc();
                ((com.maddox.il2.engine.Actor)this).pos.getAbs(loc);
                loc.getPoint().z = com.maddox.il2.engine.Engine.land().HQ(((com.maddox.JGP.Tuple3d) (loc.getPoint())).x, ((com.maddox.JGP.Tuple3d) (loc.getPoint())).y);
                if(!com.maddox.il2.engine.Engine.land().isWater(((com.maddox.JGP.Tuple3d) (loc.getPoint())).x, ((com.maddox.JGP.Tuple3d) (loc.getPoint())).y))
                {
                    loc.getOrient().set(loc.getOrient().getAzimut(), -90F, 0.0F);
                    com.maddox.il2.objects.ActorSimpleMesh actorsimplemesh = new ActorSimpleMesh("3DO/Arms/Cargo-TypeA/mono.sim", loc);
                    ((com.maddox.il2.engine.Actor) (actorsimplemesh)).collide(false);
                    ((com.maddox.il2.engine.Actor) (actorsimplemesh)).postDestroy(0x249f0L);
                }
                if(!com.maddox.il2.game.Mission.isServer() && !((com.maddox.rts.NetObj) (com.maddox.il2.game.Main.cur().netServerParams)).isMaster())
                    break label0;
                com.maddox.il2.game.gb.a(((com.maddox.il2.engine.Actor)this).pos.getAbsPoint());
                if(com.maddox.il2.objects.weapons.Bomb.a == 0)
                    break label0;
            }
            if(chute != null)
                ((com.maddox.il2.engine.ActorMesh) (chute)).destroy();
        }
        ((com.maddox.il2.engine.ActorMesh)this).destroy();
    }

    static java.lang.Class class$ZutiCargo2(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
        return class1;
    }

    private com.maddox.il2.objects.air.Chute chute;
    private boolean bOnChute;
    private static com.maddox.il2.engine.Orient or = new Orient();
    private static com.maddox.JGP.Vector3d v3d = new Vector3d();
    private float ttcurTM;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombCargo70.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3DO/Arms/Cargo-TypeA/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 6F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 70F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", (java.lang.String)null);
    }
}
