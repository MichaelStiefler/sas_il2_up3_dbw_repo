package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunMK103s extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunMK103s()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = null;
        gunproperties.fire = "3DO/Effects/GunFire/30mm/GunFire.eff";
        gunproperties.sprite = "3DO/Effects/GunFire/30mm/GunFlare.eff";
        gunproperties.smoke = "effects/smokes/MachineGun.eff";
        gunproperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
        gunproperties.sound = "weapon.MGunMK103s";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = -1;
        gunproperties.maxDeltaAngle = 0.35F;
        gunproperties.shotFreq = 7.083333F;
        gunproperties.traceFreq = 2;
        gunproperties.bullets = 50;
        gunproperties.bulletsCluster = 2;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.355F;
        gunproperties.bullet[0].kalibr = 0.000387F;
        gunproperties.bullet[0].speed = 940F;
        gunproperties.bullet[0].power = 0.0F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = null;
        gunproperties.bullet[0].traceTrail = null;
        gunproperties.bullet[0].traceColor = 0xd200ffff;
        gunproperties.bullet[0].timeLife = 3F;
        gunproperties.bullet[1].massa = 0.33F;
        gunproperties.bullet[1].kalibr = 0.000387F;
        gunproperties.bullet[1].speed = 860F;
        gunproperties.bullet[1].power = 0.046F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 1.5F;
        gunproperties.bullet[1].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        gunproperties.bullet[1].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[1].traceColor = 0xd200ffff;
        gunproperties.bullet[1].timeLife = 3F;
        gunproperties.bullet[2].massa = 0.5F;
        gunproperties.bullet[2].kalibr = 0.000387F;
        gunproperties.bullet[2].speed = 690F;
        gunproperties.bullet[2].power = 0.018F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 1.0F;
        gunproperties.bullet[2].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        gunproperties.bullet[2].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[2].traceColor = 0xd200ffff;
        gunproperties.bullet[2].timeLife = 2.5F;
        gunproperties.bullet[3].massa = 0.45F;
        gunproperties.bullet[3].kalibr = 0.000387F;
        gunproperties.bullet[3].speed = 800F;
        gunproperties.bullet[3].power = 0.026F;
        gunproperties.bullet[3].powerType = 0;
        gunproperties.bullet[3].powerRadius = 0.0F;
        gunproperties.bullet[3].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        gunproperties.bullet[3].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[3].traceColor = 0xd200ffff;
        gunproperties.bullet[3].timeLife = 2.0F;
        return gunproperties;
    }
}
