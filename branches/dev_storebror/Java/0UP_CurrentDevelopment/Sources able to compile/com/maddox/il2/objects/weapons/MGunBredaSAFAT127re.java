package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunBredaSAFAT127re extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunBredaSAFAT127re()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = false;
        gunproperties.fireMesh = "3DO/Effects/GunFire/12mm/mono.sim";
        gunproperties.fire = null;
        gunproperties.sprite = "3DO/Effects/GunFire/12mm/GunFlare.eff";
        gunproperties.smoke = null;
        gunproperties.shells = null;
        gunproperties.sound = "weapon.MGunBredaSAFAT127s";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = -1;
        gunproperties.maxDeltaAngle = 0.17F;
        gunproperties.shotFreq = 10F;
        gunproperties.bulletsCluster = 2;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.0364F;
        gunproperties.bullet[0].kalibr = 0.000128F;
        gunproperties.bullet[0].speed = 760F;
        gunproperties.bullet[0].power = 0.0F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = "3DO/Effects/Tracers/20mmWhite/mono.sim";
        gunproperties.bullet[0].traceTrail = "3DO/Effects/TEXTURES/fumeefine.eff";
        gunproperties.bullet[0].traceColor = 0;
        gunproperties.bullet[0].timeLife = 6.5F;
        gunproperties.bullet[1].massa = 0.0364F;
        gunproperties.bullet[1].kalibr = 0.000128F;
        gunproperties.bullet[1].speed = 760F;
        gunproperties.bullet[1].power = 0.0F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.0F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 6.5F;
        gunproperties.bullet[2].massa = 0.0367F;
        gunproperties.bullet[2].kalibr = 0.000128F;
        gunproperties.bullet[2].speed = 750F;
        gunproperties.bullet[2].power = 0.001F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.0F;
        gunproperties.bullet[2].traceMesh = "3DO/Effects/Tracers/20mmWhite/mono.sim";
        gunproperties.bullet[2].traceTrail = "3DO/Effects/TEXTURES/fumeefine.eff";
        gunproperties.bullet[2].traceColor = 0xd2ffffff;
        gunproperties.bullet[2].timeLife = 6.25F;
        gunproperties.bullet[3].massa = 0.037F;
        gunproperties.bullet[3].kalibr = 0.000128F;
        gunproperties.bullet[3].speed = 760F;
        gunproperties.bullet[3].power = 0.0017F;
        gunproperties.bullet[3].powerType = 0;
        gunproperties.bullet[3].powerRadius = 0.0F;
        gunproperties.bullet[3].traceMesh = null;
        gunproperties.bullet[3].traceTrail = null;
        gunproperties.bullet[3].traceColor = 0;
        gunproperties.bullet[3].timeLife = 6.5F;
        gunproperties.bullet[4].massa = 0.03275F;
        gunproperties.bullet[4].kalibr = 0.000132F;
        gunproperties.bullet[4].speed = 770F;
        gunproperties.bullet[4].power = 0.0008F;
        gunproperties.bullet[4].powerType = 0;
        gunproperties.bullet[4].powerRadius = 0.02F;
        gunproperties.bullet[4].traceMesh = "3DO/Effects/Tracers/20mmWhite/mono.sim";
        gunproperties.bullet[4].traceTrail = "3DO/Effects/TEXTURES/fumeefine.eff";
        gunproperties.bullet[4].traceColor = 0xd2ffffff;
        gunproperties.bullet[4].timeLife = 6.3F;
        return gunproperties;
    }
}
