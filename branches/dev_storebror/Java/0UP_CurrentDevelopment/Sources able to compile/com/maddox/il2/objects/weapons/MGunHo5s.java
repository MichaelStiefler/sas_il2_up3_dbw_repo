package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

public class MGunHo5s extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunHo5s()
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
        gunproperties.sound = "weapon.MGunHo5s";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 3000F;
        gunproperties.weaponType = 3;
        gunproperties.maxDeltaAngle = 0.12F;
        gunproperties.shotFreq = 12.75F;
        gunproperties.traceFreq = 2;
        gunproperties.bullets = 150;
        gunproperties.bulletsCluster = 2;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.085F;
        gunproperties.bullet[0].kalibr = 0.000252F;
        gunproperties.bullet[0].speed = 730F;
        gunproperties.bullet[0].power = 0.004F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.15F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd200ffff;
        gunproperties.bullet[0].timeLife = 3F;
        gunproperties.bullet[1].massa = 0.085F;
        gunproperties.bullet[1].kalibr = 0.000252F;
        gunproperties.bullet[1].speed = 730F;
        gunproperties.bullet[1].power = 0.004F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.0F;
        gunproperties.bullet[1].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        gunproperties.bullet[1].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[1].traceColor = 0xd200ffff;
        gunproperties.bullet[1].timeLife = 3F;
        gunproperties.bullet[2].massa = 0.085F;
        gunproperties.bullet[2].kalibr = 0.000252F;
        gunproperties.bullet[2].speed = 730F;
        gunproperties.bullet[2].power = 0.004F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.15F;
        gunproperties.bullet[2].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        gunproperties.bullet[2].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[2].traceColor = 0xd200ffff;
        gunproperties.bullet[2].timeLife = 3F;
        gunproperties.bullet[3].massa = 0.085F;
        gunproperties.bullet[3].kalibr = 0.000252F;
        gunproperties.bullet[3].speed = 730F;
        gunproperties.bullet[3].power = 0.004F;
        gunproperties.bullet[3].powerType = 0;
        gunproperties.bullet[3].powerRadius = 0.15F;
        gunproperties.bullet[3].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        gunproperties.bullet[3].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[3].traceColor = 0xd200ffff;
        gunproperties.bullet[3].timeLife = 3F;
        return gunproperties;
    }
}
