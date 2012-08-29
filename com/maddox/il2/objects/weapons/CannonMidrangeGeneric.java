// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonMidrangeGeneric.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.fm.Atmosphere;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Gun, BulletParabolaGeneric, Bullet

public abstract class CannonMidrangeGeneric extends com.maddox.il2.objects.weapons.Gun
    implements com.maddox.il2.ai.BulletAimer
{

    public CannonMidrangeGeneric()
    {
        bulletTypeIdx = 0;
    }

    public final void setBulletType(int i)
    {
        bulletTypeIdx = i;
    }

    public int nextIndexBulletType()
    {
        return bulletTypeIdx;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return new BulletParabolaGeneric(i, gungeneric, loc, vector3d, l);
    }

    public static void autocomputeSplintersRadiuses(com.maddox.il2.engine.BulletProperties abulletproperties[])
    {
        for(int i = 0; i < abulletproperties.length; i++)
            if(abulletproperties[i].power > 0.0F && abulletproperties[i].powerType == 1)
            {
                float f = 110F;
                float f1 = 280F;
                float f2 = (abulletproperties[i].kalibr - 0.037F) / 0.045F;
                if(f2 <= 0.0F)
                    f2 = 0.0F;
                if(f2 >= 1.0F)
                    f2 = 1.0F;
                abulletproperties[i].powerRadius = f + f2 * (f1 - f);
            }

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
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.6F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 10F;
        gunproperties.emitTime = 0.3F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 5000F;
        gunproperties.shotFreq = 999F;
        gunproperties.traceFreq = 0;
        gunproperties.bullets = -1;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties()
        });
        for(int i = 0; i < gunproperties.bullet.length; i++)
        {
            gunproperties.bullet[i].massa = 0.001F;
            gunproperties.bullet[i].kalibr = 9F;
            gunproperties.bullet[i].speed = 10F;
            gunproperties.bullet[i].power = 0.0F;
            if(i == 0)
                gunproperties.bullet[i].powerType = 0;
            else
                gunproperties.bullet[i].powerType = 1;
            gunproperties.bullet[i].powerRadius = 140F;
            gunproperties.bullet[i].timeLife = 10F;
            gunproperties.bullet[i].traceMesh = null;
            gunproperties.bullet[i].traceTrail = null;
            gunproperties.bullet[i].traceColor = 0;
        }

        float f = Specify(gunproperties);
        com.maddox.il2.objects.weapons.CannonMidrangeGeneric.autocomputeSplintersRadiuses(gunproperties.bullet);
        for(int j = 0; j < gunproperties.bullet.length; j++)
        {
            float f1 = gunproperties.aimMaxDist / (gunproperties.bullet[j].speed * 0.707F);
            gunproperties.bullet[j].timeLife = f1 * 2.0F;
        }

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

    public float TravelTime(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        float f = (float)(point3d1.z - point3d.z);
        if(java.lang.Math.abs(f) > prop.aimMaxDist)
            return -1F;
        float f3 = (float)(point3d1.x - point3d.x);
        float f4 = (float)(point3d1.y - point3d.y);
        float f5 = (float)java.lang.Math.sqrt(f3 * f3 + f4 * f4);
        if(f5 > prop.aimMaxDist)
            return -1F;
        if(f5 < prop.aimMinDist && java.lang.Math.abs(f) < prop.aimMinDist)
            return -1F;
        float f1 = f5;
        float f2 = f;
        f3 = -com.maddox.il2.fm.Atmosphere.g() / 2.0F;
        f4 = prop.bullet[bulletTypeIdx].speed;
        f5 = f3 * f3;
        float f6 = -(2.0F * f2 * f3 + f4 * f4);
        float f7 = f1 * f1 + f2 * f2;
        float f8 = f6 * f6 - 4F * f5 * f7;
        if(f8 < 0.0F)
            return -1F;
        f8 = (float)java.lang.Math.sqrt(f8);
        float f9 = (-f6 + f8) / (2.0F * f5);
        float f10 = (-f6 - f8) / (2.0F * f5);
        if(f9 >= 0.0F)
        {
            if(f10 >= 0.0F && f10 < f9)
                f9 = f10;
        } else
        if(f10 >= 0.0F)
            f9 = f10;
        else
            return -1F;
        timeToFly = (float)java.lang.Math.sqrt(f9);
        return timeToFly;
    }

    public boolean FireDirection(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Vector3d vector3d)
    {
        float f = (float)(point3d1.x - point3d.x);
        float f1 = (float)(point3d1.y - point3d.y);
        float f2 = (float)(point3d1.z - point3d.z);
        float f3 = (float)java.lang.Math.sqrt(f * f + f1 * f1);
        float f4 = (float)(point3d1.z - point3d.z);
        if((double)java.lang.Math.abs(f3) < 0.01D)
        {
            vector3d.set(0.0D, 0.0D, 1.0D);
            if(f4 < 0.0F)
                vector3d.negate();
            return true;
        }
        vector3d.set(f, f1, 0.0D);
        vector3d.scale(1.0F / f3);
        float f5 = prop.bullet[bulletTypeIdx].speed;
        if(timeToFly >= 0.0001F)
        {
            float f8 = -com.maddox.il2.fm.Atmosphere.g() / 2.0F;
            float f7 = (f4 - f8 * timeToFly * timeToFly) / timeToFly;
            float f6 = f3 / timeToFly;
            vector3d.x *= f6;
            vector3d.y *= f6;
            vector3d.z = f7;
            vector3d.normalize();
        } else
        {
            return false;
        }
        return true;
    }

    private float timeToFly;
    protected int bulletTypeIdx;
}
