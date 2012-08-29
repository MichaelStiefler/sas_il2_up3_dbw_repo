// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BulletAntiAirBigGermany.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.effects.Explosions;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            BulletAntiAirGeneric

public class BulletAntiAirBigGermany extends com.maddox.il2.objects.weapons.BulletAntiAirGeneric
{

    public BulletAntiAirBigGermany(com.maddox.JGP.Vector3d vector3d, int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d1, long l, 
            float f)
    {
        super(vector3d, i, gungeneric, loc, vector3d1, l, f, false);
    }

    protected void explodeInAir_Effect(com.maddox.JGP.Point3d point3d)
    {
        com.maddox.il2.objects.effects.Explosions.AirFlak(point3d, 1);
    }
}
