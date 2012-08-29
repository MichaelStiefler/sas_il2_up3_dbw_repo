// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunAntiAirGeneric.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonMidrangeGeneric

public abstract class MGunAntiAirGeneric extends com.maddox.il2.objects.weapons.CannonMidrangeGeneric
    implements com.maddox.il2.ai.BulletAimer
{

    public MGunAntiAirGeneric()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.weaponType = 2;
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = false;
        gunproperties.fireMesh = "3DO/Effects/GunFire/20mm/mono.sim";
        gunproperties.fire = null;
        gunproperties.sprite = "3DO/Effects/GunFire/20mm/GunFlare.eff";
        gunproperties.smoke = "effects/smokes/MachineGun.eff";
        gunproperties.shells = null;
        gunproperties.sound = "weapon.zenitka_20";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.5F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.1F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 2000F;
        gunproperties.shotFreq = 1.0F;
        gunproperties.traceFreq = 2;
        gunproperties.bullets = -1;
        gunproperties.bulletsCluster = 2;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties()
        });
        for(int i = 0; i < 2; i++)
        {
            gunproperties.bullet[i].traceMesh = null;
            gunproperties.bullet[i].traceTrail = "3DO/Effects/Tracers/TrailCurved.eff";
            gunproperties.bullet[i].traceColor = 0x1010101;
            gunproperties.bullet[i].massa = 0.001F;
            gunproperties.bullet[i].kalibr = 9F;
            gunproperties.bullet[i].speed = 10F;
            gunproperties.bullet[i].power = i != 0 ? 0.0F : 0.001F;
            gunproperties.bullet[i].powerType = 1;
            gunproperties.bullet[i].powerRadius = 10F;
            gunproperties.bullet[i].timeLife = i != 0 ? 5F : 9F;
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
        float f = super.TravelTime(point3d, point3d1);
        if(bulletTypeIdx > 0)
            return f;
        if(f > prop.bullet[0].timeLife + prop.bullet[0].addExplTime)
            return -1F;
        else
            return f;
    }

    public static final float Rnd(float f, float f1)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(f, f1);
    }

    private static final long SecsToTicks(float f)
    {
        long l = (long)(0.5D + (double)(f / com.maddox.rts.Time.tickLenFs()));
        return l >= 1L ? l : 1L;
    }

    public boolean FireDirection(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Vector3d vector3d)
    {
        explAddTimeT = 0L;
        if(bulletTypeIdx == 0)
        {
            float f = prop.bullet[0].addExplTime;
            if(f > 0.0F)
            {
                explAddTimeT = com.maddox.il2.objects.weapons.MGunAntiAirGeneric.SecsToTicks(com.maddox.il2.objects.weapons.MGunAntiAirGeneric.Rnd(-f, f));
                if(explAddTimeT == 0L)
                    explAddTimeT = 1L;
            }
        }
        return super.FireDirection(point3d, point3d1, vector3d);
    }

    protected long explAddTimeT;
}
