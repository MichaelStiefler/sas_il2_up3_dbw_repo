package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunType99No2s extends com.maddox.il2.objects.weapons.MGunMGFFk
{

    public MGunType99No2s()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = "3DO/Effects/GunFire/20mm/mono.sim";
        gunproperties.fire = null;
        gunproperties.sprite = "3DO/Effects/GunFire/20mm/GunFlare.eff";
        gunproperties.smoke = "effects/smokes/MachineGun.eff";
        gunproperties.shells = "3DO/Effects/GunShells/GunShells.eff";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 10F;
        gunproperties.emitR = 3F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = -1;
        gunproperties.maxDeltaAngle = 0.28F;
        gunproperties.shotFreq = 8F;
        gunproperties.traceFreq = 3;
        gunproperties.bullets = 250;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.128F;
        gunproperties.bullet[0].kalibr = 0.00036F;
        gunproperties.bullet[0].speed = 750F;
        gunproperties.bullet[0].power = 0.0035F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.15F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmOrange/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd2ff0000;
        gunproperties.bullet[0].timeLife = 3.3F;
        return gunproperties;
    }
}
