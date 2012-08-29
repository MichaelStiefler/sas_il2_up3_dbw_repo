// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombCargoA.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Chute;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bomb

public class BombCargoA extends com.maddox.il2.objects.weapons.Bomb
{

    public BombCargoA()
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
        super.interpolateTick();
        getSpeed(v3d);
        or.setAT0(v3d);
        pos.setAbs(or);
        if(bOnChute)
        {
            v3d.scale(0.98999999999999999D);
            if(v3d.z < -5D)
                v3d.z += 1.1F * com.maddox.rts.Time.tickConstLenFs();
            setSpeed(v3d);
        } else
        if(curTm > ttcurTM)
        {
            bOnChute = true;
            chute = new Chute(this);
            chute.collide(false);
            chute.mesh().setScale(1.5F);
            chute.pos.setRel(new Point3d(2D, 0.0D, 0.0D), new Orient(0.0F, 90F, 0.0F));
        }
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(actor instanceof com.maddox.il2.objects.ActorLand)
        {
            if(chute != null)
                chute.landing();
            com.maddox.il2.engine.Loc loc = new Loc();
            pos.getAbs(loc);
            loc.getPoint().z = com.maddox.il2.engine.Engine.land().HQ(loc.getPoint().x, loc.getPoint().y);
            if(!com.maddox.il2.engine.Engine.land().isWater(loc.getPoint().x, loc.getPoint().y))
            {
                loc.getOrient().set(loc.getOrient().getAzimut(), -90F, 0.0F);
                com.maddox.il2.objects.ActorSimpleMesh actorsimplemesh = new ActorSimpleMesh("3DO/Arms/Cargo-TypeA/mono.sim", loc);
                actorsimplemesh.collide(false);
                actorsimplemesh.postDestroy(0x249f0L);
            }
        } else
        if(chute != null)
            chute.destroy();
        destroy();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private com.maddox.il2.objects.air.Chute chute;
    private boolean bOnChute;
    private static com.maddox.il2.engine.Orient or = new Orient();
    private static com.maddox.JGP.Vector3d v3d = new Vector3d();
    private float ttcurTM;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombCargoA.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Cargo-TypeA/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 1.0F);
        com.maddox.rts.Property.set(class1, "power", 6F);
        com.maddox.rts.Property.set(class1, "powerType", 1);
        com.maddox.rts.Property.set(class1, "kalibr", 1.0F);
        com.maddox.rts.Property.set(class1, "massa", 500F);
        com.maddox.rts.Property.set(class1, "sound", (java.lang.String)null);
    }
}
