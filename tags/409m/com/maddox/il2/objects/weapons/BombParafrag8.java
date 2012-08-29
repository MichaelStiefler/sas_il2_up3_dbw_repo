// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BombParafrag8.java

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
//            Bomb

public class BombParafrag8 extends com.maddox.il2.objects.weapons.Bomb
{

    public BombParafrag8()
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
        ttexpTM = com.maddox.il2.ai.World.Rnd().nextFloat(11.2F, 17.75F);
    }

    public void destroy()
    {
        if(chute != null)
            chute.destroy();
        super.destroy();
    }

    public void interpolateTick()
    {
        super.interpolateTick();
        if(bOnChute)
        {
            getSpeed(v3d);
            v3d.scale(0.96999999999999997D);
            if(v3d.z < -2D)
                v3d.z += 1.1F * com.maddox.rts.Time.tickConstLenFs();
            setSpeed(v3d);
        } else
        if(curTm > ttcurTM)
        {
            bOnChute = true;
            chute = new Chute(this);
            chute.collide(false);
            chute.mesh().setScale(0.5F);
            chute.pos.setRel(new Point3d(0.5D, 0.0D, 0.0D), new Orient(0.0F, 90F, 0.0F));
        }
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if((actor instanceof com.maddox.il2.objects.ActorLand) && chute != null)
            chute.landing();
        super.msgCollision(actor, s, s1);
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

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.BombParafrag8.class;
        com.maddox.rts.Property.set(class1, "mesh", "3DO/Arms/Parafrag/mono.sim");
        com.maddox.rts.Property.set(class1, "radius", 75F);
        com.maddox.rts.Property.set(class1, "power", 4F);
        com.maddox.rts.Property.set(class1, "powerType", 1);
        com.maddox.rts.Property.set(class1, "kalibr", 0.2F);
        com.maddox.rts.Property.set(class1, "massa", 8F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.bomb_phball");
    }
}
