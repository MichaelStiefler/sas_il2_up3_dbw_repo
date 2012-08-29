// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonJA_100_Type98.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonAntiAirGeneric, BulletAntiAirBigGermany, Bullet

public class CannonJA_100_Type98 extends com.maddox.il2.objects.weapons.CannonAntiAirGeneric
{

    public CannonJA_100_Type98()
    {
    }

    protected float Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.aimMaxDist = 19500F;
        gunproperties.sound = "weapon.Cannon100";
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.timeLife = 13F;
        bulletproperties.power = 0.95F;
        bulletproperties.powerType = 1;
        bulletproperties.powerRadius = 156F;
        bulletproperties.kalibr = 0.1F;
        bulletproperties.massa = 13F;
        bulletproperties.speed = 1000F;
        bulletproperties = gunproperties.bullet[1];
        bulletproperties.power = 0.95F;
        bulletproperties.powerType = 0;
        bulletproperties.powerRadius = 156F;
        bulletproperties.kalibr = 0.1F;
        bulletproperties.massa = 13F;
        bulletproperties.speed = 1000F;
        return 65F;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return new BulletAntiAirBigGermany(i, gungeneric, loc, vector3d, l, explodeAtHeight);
    }
}
