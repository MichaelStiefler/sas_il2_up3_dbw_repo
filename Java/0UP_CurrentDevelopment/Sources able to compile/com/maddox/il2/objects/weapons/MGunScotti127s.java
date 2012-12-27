package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunScotti127s extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunScotti127s()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = "3DO/Effects/GunFire/12mm/mono.sim";
        gunproperties.fire = null;
        gunproperties.sprite = "3DO/Effects/GunFire/12mm/GunFlare.eff";
        gunproperties.smoke = null;
        gunproperties.shells = null;
        gunproperties.sound = "weapon.mgun_20_500";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = -1;
        gunproperties.maxDeltaAngle = 0.209F;
        gunproperties.shotFreqDeviation = 0.02F;
        gunproperties.shotFreq = 13.3333F;
        gunproperties.traceFreq = 2;
        gunproperties.bullets = 500;
        gunproperties.bulletsCluster = 2;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.03275F;
        gunproperties.bullet[0].kalibr = 0.000106001F;
        gunproperties.bullet[0].speed = 770F;
        gunproperties.bullet[0].power = 0.0008F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.03F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmRed/mono.sim";
        gunproperties.bullet[0].traceTrail = "3DO/Effects/TEXTURES/fumeefine.eff";
        gunproperties.bullet[0].traceColor = 0xd90000ff;
        gunproperties.bullet[0].timeLife = 6.5F;
        return gunproperties;
    }
}