// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombTorp45_36AV_A.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Chute;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            BombParaTorp

public class BombTorp45_36AV_A extends com.maddox.il2.objects.weapons.BombParaTorp
{

    public BombTorp45_36AV_A()
    {
        chute = null;
        bOnChute = false;
    }

    public void start()
    {
        super.start();
        ttcurTM = com.maddox.il2.ai.World.Rnd().nextFloat(0.5F, 1.75F);
        ttexpTM = com.maddox.il2.ai.World.Rnd().nextFloat(11.2F, 17.75F);
        openHeight = 10000F;
    }

    public void destroy()
    {
        if(chute != null)
            chute.destroy();
        super.destroy();
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if((actor instanceof com.maddox.il2.objects.ActorLand) && chute != null)
            bOnChute = false;
        ttcurTM = 100000F;
        if(chute != null)
            chute.landing();
        super.msgCollision(actor, s, s1);
    }

    public void interpolateTick()
    {
        curTm += com.maddox.rts.Time.tickLenFs();
        super.interpolateTick();
        if(bOnChute)
        {
            getSpeed(v3d);
            v3d.scale(0.98999999999999999D);
            if(v3d.z < -10D)
                v3d.z += 1.1F * com.maddox.rts.Time.tickConstLenFs();
            setSpeed(v3d);
            pos.getAbs(P, Or);
        } else
        if(curTm > ttcurTM && P.z < (double)openHeight)
        {
            bOnChute = true;
            chute = new Chute(this);
            chute.collide(false);
            chute.mesh().setScale(2.5F);
            chute.pos.setRel(new Point3d(1.0D, 0.0D, 0.0D), new Orient(0.0F, 90F, 0.0F));
        }
    }

    protected void mydebug(java.lang.String s)
    {
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
    private static com.maddox.JGP.Vector3d v3d = new Vector3d();
    private float ttcurTM;
    private float ttexpTM;
    private float openHeight;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorp45_36AV_A.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/45-12/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 20F);
        com.maddox.rts.Property.set(class1, "power", 220F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.45F);
        com.maddox.rts.Property.set(class1, "massa", 960F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 20F);
        com.maddox.rts.Property.set(class1, "traveltime", 200F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMin", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 90.5F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 115F);
        com.maddox.rts.Property.set(class1, "armingTime", 3.5F);
    }
}
