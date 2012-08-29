// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonAntiAirGeneric.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonMidrangeGeneric

public abstract class CannonAntiAirGeneric extends com.maddox.il2.objects.weapons.CannonMidrangeGeneric
    implements com.maddox.il2.ai.BulletAimer
{

    public CannonAntiAirGeneric()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.weaponType = 4;
        gunproperties.bCannon = true;
        gunproperties.bUseHookAsRel = false;
        gunproperties.fireMesh = null;
        gunproperties.fire = "3DO/Effects/GunFire/88mm/CannonTank.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "effects/smokes/CannonTank.eff";
        gunproperties.shells = null;
        gunproperties.sound = "weapon.zenitka_85";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.6F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 8F;
        gunproperties.emitTime = 0.3F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 11000F;
        gunproperties.shotFreq = 999F;
        gunproperties.traceFreq = 0;
        gunproperties.bullets = -1;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties()
        });
        for(int i = 0; i < 2; i++)
        {
            gunproperties.bullet[i].massa = 0.001F;
            gunproperties.bullet[i].kalibr = 9F;
            gunproperties.bullet[i].speed = 10F;
            gunproperties.bullet[i].power = i != 0 ? 0.0F : 0.7F;
            gunproperties.bullet[i].powerType = 1;
            gunproperties.bullet[i].powerRadius = 140F;
            gunproperties.bullet[i].timeLife = i != 0 ? 6F : 20F;
            gunproperties.bullet[i].traceMesh = null;
            gunproperties.bullet[i].traceTrail = null;
            gunproperties.bullet[i].traceColor = 0;
        }

        float f = Specify(gunproperties);
        com.maddox.il2.objects.weapons.CannonMidrangeGeneric.autocomputeSplintersRadiuses(gunproperties.bullet);
        if(f > 0.0F)
        {
            if(f <= 20F)
                f = 20F;
            if(f >= 70F)
                f = 70F;
            f = (f - 20F) / 50F;
            gunproperties.maxDeltaAngle = 0.3F - f * 0.2F;
        } else
        {
            gunproperties.maxDeltaAngle = 0.2F;
        }
        return gunproperties;
    }

    protected abstract float Specify(com.maddox.il2.engine.GunProperties gunproperties);

    private float straightTravelTime(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        float f = (float)point3d.distance(point3d1);
        if(f < prop.aimMinDist || f > prop.aimMaxDist)
            return -1F;
        else
            return f / prop.bullet[0].speed;
    }

    private boolean straightFireDirection(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Vector3d vector3d)
    {
        float f = (float)point3d.distance(point3d1);
        if(f < prop.aimMinDist || f > prop.aimMaxDist)
        {
            return false;
        } else
        {
            vector3d.set(point3d1);
            vector3d.sub(point3d);
            vector3d.scale(1.0F / f);
            return true;
        }
    }

    public float TravelTime(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        if(bulletTypeIdx > 0 || (float)(point3d1.z - point3d.z) < 250F)
            return super.TravelTime(point3d, point3d1);
        else
            return straightTravelTime(point3d, point3d1);
    }

    public boolean FireDirection(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Vector3d vector3d)
    {
        boolean flag;
        if(bulletTypeIdx > 0 || (float)(point3d1.z - point3d.z) < 250F)
        {
            flag = super.FireDirection(point3d, point3d1, vector3d);
            explodeAtHeight = -1F;
        } else
        {
            flag = straightFireDirection(point3d, point3d1, vector3d);
            explodeAtHeight = (float)point3d1.z;
        }
        return flag;
    }

    static final float MIN_AT_HEIGHT_EXPLODE = 250F;
    protected float explodeAtHeight;
}
