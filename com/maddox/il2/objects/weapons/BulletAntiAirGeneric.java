// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BulletAntiAirGeneric.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bullet

public abstract class BulletAntiAirGeneric extends com.maddox.il2.objects.weapons.Bullet
{

    public BulletAntiAirGeneric(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l, float f, 
            boolean flag)
    {
        super(i, gungeneric, loc, vector3d, l);
        explodeAtHeight = f;
        time_explode = flag;
    }

    public void move(float f)
    {
        if(gun == null)
        {
            destroy();
            return;
        }
        p0.set(p1);
        p1.scaleAdd(f, speed, p0);
        if(explodeAtHeight > 0.0F)
        {
            if((float)p1.z >= explodeAtHeight)
            {
                timeOut();
                destroy();
                return;
            }
        } else
        {
            speed.z += gun.bulletAG[indx()] * f;
        }
    }

    public void timeOut()
    {
        if(explodeAtHeight <= 0.0F && !time_explode)
            return;
        if(gun == null)
        {
            return;
        } else
        {
            com.maddox.il2.engine.BulletProperties bulletproperties = properties();
            com.maddox.il2.objects.weapons.Bullet.tmpP.interpolate(p0, p1, com.maddox.rts.Time.tickOffset());
            com.maddox.il2.ai.MsgExplosion.send(null, null, com.maddox.il2.objects.weapons.Bullet.tmpP, owner, bulletproperties.massa, bulletproperties.power, bulletproperties.powerType, bulletproperties.powerRadius);
            explodeInAir_Effect(com.maddox.il2.objects.weapons.Bullet.tmpP);
            return;
        }
    }

    protected abstract void explodeInAir_Effect(com.maddox.JGP.Point3d point3d);

    public void showExplosion(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.BulletProperties bulletproperties, double d)
    {
        if(explodeAtHeight <= 0.0F && !time_explode)
            super.showExplosion(actor, point3d, bulletproperties, d);
        else
            explodeInAir_Effect(point3d);
    }

    protected float explodeAtHeight;
    protected boolean time_explode;
}
