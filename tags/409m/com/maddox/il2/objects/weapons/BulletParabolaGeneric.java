// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BulletParabolaGeneric.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bullet

public class BulletParabolaGeneric extends com.maddox.il2.objects.weapons.Bullet
{

    public BulletParabolaGeneric(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        super(i, gungeneric, loc, vector3d, l);
    }

    public void move(float f)
    {
        if(gun == null)
        {
            return;
        } else
        {
            p0.set(p1);
            p1.scaleAdd(f, speed, p0);
            speed.z += gun.bulletAG[indx()] * f;
            return;
        }
    }

    public void timeOut()
    {
    }
}
