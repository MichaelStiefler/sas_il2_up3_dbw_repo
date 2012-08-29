// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonRocketSimple.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Atmosphere;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Gun

public abstract class CannonRocketSimple extends com.maddox.il2.objects.weapons.Gun
    implements com.maddox.il2.ai.BulletAimer
{

    public CannonRocketSimple()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.weaponType = 8;
        gunproperties.bCannon = true;
        gunproperties.bUseHookAsRel = false;
        gunproperties.fireMesh = null;
        gunproperties.fire = null;
        gunproperties.sprite = null;
        gunproperties.smoke = null;
        gunproperties.shells = null;
        gunproperties.sound = "weapon.Cannon75";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.6F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 7F;
        gunproperties.emitTime = 0.35F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 8000F;
        gunproperties.shotFreq = 999F;
        gunproperties.traceFreq = 0;
        gunproperties.bullets = -1;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties()
        });
        for(int i = 0; i < 1; i++)
        {
            gunproperties.bullet[i].massa = 1.0F;
            gunproperties.bullet[i].kalibr = 9F;
            gunproperties.bullet[i].speed = 220F;
            gunproperties.bullet[i].power = 0.0F;
            gunproperties.bullet[i].powerRadius = 140F;
            gunproperties.bullet[i].timeLife = 10F;
            gunproperties.bullet[i].traceMesh = null;
            gunproperties.bullet[i].traceTrail = null;
            gunproperties.bullet[i].traceColor = 0;
        }

        gunproperties.maxDeltaAngle = 0.4F;
        Specify(gunproperties);
        return gunproperties;
    }

    protected abstract void Specify(com.maddox.il2.engine.GunProperties gunproperties);

    public float TravelTime(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        float f = (float)(point3d1.z - point3d.z);
        if(java.lang.Math.abs(f) > prop.aimMaxDist)
            return -1F;
        float f3 = (float)(point3d1.x - point3d.x);
        float f4 = (float)(point3d1.y - point3d.y);
        float f5 = (float)java.lang.Math.sqrt(f3 * f3 + f4 * f4);
        if(f5 < prop.aimMinDist || f5 > prop.aimMaxDist)
            return -1F;
        float f1 = f5;
        float f2 = f;
        f3 = -com.maddox.il2.fm.Atmosphere.g() / 2.0F;
        f4 = prop.bullet[0].speed;
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
        float f2 = (float)(point3d1.x - point3d.x);
        float f3 = (float)(point3d1.y - point3d.y);
        float f4 = (float)(point3d1.z - point3d.z);
        float f5 = (float)java.lang.Math.sqrt(f2 * f2 + f3 * f3);
        float f = f5;
        float f1 = (float)(point3d1.z - point3d.z);
        if((double)java.lang.Math.abs(f) < 0.01D)
        {
            vector3d.set(0.0D, 0.0D, 1.0D);
            if(f1 < 0.0F)
                vector3d.negate();
            return true;
        }
        vector3d.set(f2, f3, 0.0D);
        vector3d.scale(1.0F / f);
        float f6 = prop.bullet[0].speed;
        if(timeToFly >= 0.0001F)
        {
            float f9 = -com.maddox.il2.fm.Atmosphere.g() / 2.0F;
            float f8 = (f1 - f9 * timeToFly * timeToFly) / timeToFly;
            float f7 = f / timeToFly;
            vector3d.x *= f7;
            vector3d.y *= f7;
            vector3d.z = f8;
            vector3d.normalize();
        } else
        {
            return false;
        }
        return true;
    }

    public void doStartBullet(double d)
    {
        pos.getAbs(tmpP);
        pos.getAbs(tmpO);
        if(prop.maxDeltaAngle > 0.0F)
        {
            float f = com.maddox.il2.ai.World.Rnd().nextFloat(-prop.maxDeltaAngle, prop.maxDeltaAngle);
            float f1 = com.maddox.il2.ai.World.Rnd().nextFloat(-prop.maxDeltaAngle, prop.maxDeltaAngle);
            tmpO.increment(f, f1, 0.0F);
        }
        launch(tmpP, tmpO, prop.bullet[0].speed, getOwner());
    }

    public abstract void launch(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f, com.maddox.il2.engine.Actor actor);

    private float timeToFly;
    private static com.maddox.JGP.Point3d tmpP = new Point3d();
    private static com.maddox.il2.engine.Orient tmpO = new Orient();

}
