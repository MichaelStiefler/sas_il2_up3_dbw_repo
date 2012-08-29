// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonTankGeneric.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonMidrangeGeneric

public abstract class CannonTankGeneric extends com.maddox.il2.objects.weapons.CannonMidrangeGeneric
    implements com.maddox.il2.ai.BulletAimer
{

    public CannonTankGeneric()
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
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.6F);
        gunproperties.emitI = 5F;
        gunproperties.emitR = 4F;
        gunproperties.emitTime = 0.3F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 3000F;
        gunproperties.shotFreq = 999F;
        gunproperties.traceFreq = 1;
        gunproperties.bullets = -1;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties()
        });
        for(int i = 0; i < gunproperties.bullet.length; i++)
        {
            gunproperties.bullet[i].massa = 0.001F;
            gunproperties.bullet[i].kalibr = 9F;
            gunproperties.bullet[i].speed = 10F;
            gunproperties.bullet[i].power = 0.0F;
            gunproperties.bullet[i].powerType = 0;
            gunproperties.bullet[i].powerRadius = 0.0F;
            gunproperties.bullet[i].timeLife = 5F;
            gunproperties.bullet[i].traceMesh = null;
            gunproperties.bullet[i].traceTrail = "effects/Smokes/ShellTrail.eff";
            gunproperties.bullet[i].traceColor = 0x1010101;
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
}
