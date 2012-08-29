// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunADEN30s.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import java.security.SecureRandom;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunADEN30s extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunADEN30s()
    {
    }

    private void initRandom()
    {
        if(theRangeRandom != null)
        {
            return;
        } else
        {
            long lTime = java.lang.System.currentTimeMillis();
            java.security.SecureRandom secRandom = new SecureRandom();
            secRandom.setSeed(lTime);
            long lSeed1 = secRandom.nextInt();
            long lSeed2 = secRandom.nextInt();
            long lSeed = (lSeed1 << 32) + lSeed2;
            theRangeRandom = new RangeRandom(lSeed);
            return;
        }
    }

    private int nextRandomInt(int iMin, int iMax)
    {
        initRandom();
        return theRangeRandom.nextInt(iMin, iMax);
    }

    public void createdProperties()
    {
        super.createdProperties();
        initRandom();
        int iRandBullet = nextRandomInt(1, super.prop.bullet.length);
        for(int i = 0; i < iRandBullet; i++)
            nextIndexBulletType();

    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = "3DO/Effects/GunFire/20mm/mono.sim";
        gunproperties.fire = "3DO/Effects/GunFire/30mm/GunFire.eff";
        gunproperties.sprite = "3DO/Effects/GunFire/30mm/GunFlare.eff";
        gunproperties.smoke = "effects/smokes/MachineGun.eff";
        gunproperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
        gunproperties.sound = "weapon.MGun231";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = -1;
        gunproperties.maxDeltaAngle = 0.43F;
        gunproperties.shotFreq = 20F;
        gunproperties.traceFreq = 2;
        gunproperties.bullets = 50;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.247F;
        gunproperties.bullet[0].kalibr = 0.000667F;
        gunproperties.bullet[0].speed = 790F;
        gunproperties.bullet[0].power = 0.046F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 1.5F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd200ffff;
        gunproperties.bullet[0].timeLife = 3.3F;
        gunproperties.bullet[1].massa = 0.247F;
        gunproperties.bullet[1].kalibr = 0.000667F;
        gunproperties.bullet[1].speed = 790F;
        gunproperties.bullet[1].power = 0.054F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 1.5F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 3.3F;
        return gunproperties;
    }

    private static com.maddox.il2.ai.RangeRandom theRangeRandom;
}
