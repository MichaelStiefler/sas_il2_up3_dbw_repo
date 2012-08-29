// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunTankGeneric.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonMidrangeGeneric

public abstract class MGunTankGeneric extends com.maddox.il2.objects.weapons.CannonMidrangeGeneric
    implements com.maddox.il2.ai.BulletAimer
{

    public MGunTankGeneric()
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
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.5F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.1F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 3200F;
        gunproperties.shotFreq = 1.0F;
        gunproperties.traceFreq = 2;
        gunproperties.bullets = -1;
        gunproperties.bulletsCluster = 3;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties()
        });
        gunproperties.bullet[0].traceMesh = null;
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0x1010101;
        gunproperties.bullet[0].massa = 0.001F;
        gunproperties.bullet[0].kalibr = 9F;
        gunproperties.bullet[0].speed = 10F;
        gunproperties.bullet[0].power = 0.0F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].timeLife = 3F;
        float f = Specify(gunproperties);
        com.maddox.il2.objects.weapons.CannonMidrangeGeneric.autocomputeSplintersRadiuses(gunproperties.bullet);
        for(int i = 0; i < gunproperties.bullet.length; i++)
        {
            float f1 = gunproperties.aimMaxDist / (gunproperties.bullet[i].speed * 0.707F);
            gunproperties.bullet[i].timeLife = f1 * 2.0F;
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
}
