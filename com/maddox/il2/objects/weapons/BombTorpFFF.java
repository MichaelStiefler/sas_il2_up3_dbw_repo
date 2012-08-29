// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombTorpFFF.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Chute;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            BombParaTorp, BombTorpFFF1

public class BombTorpFFF extends com.maddox.il2.objects.weapons.BombParaTorp
{

    public BombTorpFFF()
    {
        chute1 = null;
        chute2 = null;
        bOnChute1 = false;
        bOnChute2 = false;
    }

    public void start()
    {
        super.start();
        ttcurTM = 1.5F;
        ttexpTM = 15F;
        if(!(this instanceof com.maddox.il2.objects.weapons.BombTorpFFF1))
        {
            ttcurTM += 1.2F;
            openHeight = 210F;
        } else
        {
            openHeight = 180F;
        }
    }

    public void destroy()
    {
        if(chute1 != null)
            chute1.destroy();
        if(chute2 != null)
            chute2.destroy();
        super.destroy();
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if((actor instanceof com.maddox.il2.objects.ActorLand) && (chute1 != null || chute2 != null))
            bOnChute1 = false;
        ttcurTM = 100000F;
        if(chute1 != null)
            chute1.landing();
        if(chute2 != null)
            chute2.landing();
        super.msgCollision(actor, s, s1);
    }

    public void interpolateTick()
    {
        curTm += com.maddox.rts.Time.tickLenFs();
        super.interpolateTick();
        if(bOnChute2)
        {
            getSpeed(v3d);
            v3d.scale(0.98999999999999999D);
            if(v3d.z < -40D)
                v3d.z += 8F * com.maddox.rts.Time.tickConstLenFs();
            setSpeed(v3d);
            pos.getAbs(P, Or);
        } else
        if(bOnChute1)
        {
            getSpeed(v3d);
            v3d.scale(0.98999999999999999D);
            if(v3d.z < -90D)
                v3d.z += 1.1F * com.maddox.rts.Time.tickConstLenFs();
            setSpeed(v3d);
            pos.getAbs(P, Or);
            if(P.z < (double)openHeight)
            {
                bOnChute2 = true;
                bOnChute1 = false;
                chute2 = new Chute(this);
                chute2.collide(false);
                chute2.mesh().setScale(2.5F);
                chute2.pos.setRel(new Point3d(3D, 0.0D, 0.0D), new Orient(0.0F, 90F, 0.0F));
                if(chute1 != null)
                    chute1.destroy();
            }
        } else
        if(curTm > ttcurTM)
        {
            bOnChute1 = true;
            chute1 = new Chute(this);
            chute1.collide(false);
            chute1.mesh().setScale(0.4F);
            chute1.pos.setRel(new Point3d(0.5D, 0.0D, 0.0D), new Orient(0.0F, 90F, 0.0F));
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private com.maddox.il2.objects.air.Chute chute1;
    private com.maddox.il2.objects.air.Chute chute2;
    public boolean bOnChute1;
    public boolean bOnChute2;
    private static com.maddox.JGP.Vector3d v3d = new Vector3d();
    private float ttcurTM;
    private float ttexpTM;
    private float openHeight;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombTorpFFF.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/MotobombaFFF/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 70F);
        com.maddox.rts.Property.set(class1, "power", 120F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "kalibr", 0.5F);
        com.maddox.rts.Property.set(class1, "massa", 360F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.torpedo");
        com.maddox.rts.Property.set(class1, "velocity", 6.1F);
        com.maddox.rts.Property.set(class1, "traveltime", 2100F);
        com.maddox.rts.Property.set(class1, "startingspeed", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMin", 0.0F);
        com.maddox.rts.Property.set(class1, "impactAngleMax", 90.5F);
        com.maddox.rts.Property.set(class1, "impactSpeed", 115F);
        com.maddox.rts.Property.set(class1, "armingTime", 3.5F);
    }
}
