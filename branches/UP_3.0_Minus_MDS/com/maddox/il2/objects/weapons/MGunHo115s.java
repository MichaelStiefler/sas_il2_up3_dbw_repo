// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MGunHo115s.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunAircraftGeneric

public class MGunHo115s extends com.maddox.il2.objects.weapons.MGunAircraftGeneric
{

    public MGunHo115s()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = true;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = null;
        gunproperties.fire = "3DO/Effects/GunFire/37mm/GunFire.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "effects/smokes/CannonTank.eff";
        gunproperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
        gunproperties.sound = "weapon.air_cannon_37";
        gunproperties.emitColor = new Color3f(1.0F, 1.0F, 0.0F);
        gunproperties.emitI = 2.5F;
        gunproperties.emitR = 1.5F;
        gunproperties.emitTime = 0.03F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.weaponType = -1;
        gunproperties.maxDeltaAngle = 0.43F;
        gunproperties.shotFreq = 6.66F;
        gunproperties.traceFreq = 2;
        gunproperties.bullets = 50;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.27F;
        gunproperties.bullet[0].kalibr = 0.000567F;
        gunproperties.bullet[0].speed = 600F;
        gunproperties.bullet[0].power = 0.018F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 1.5F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd200ffff;
        gunproperties.bullet[0].timeLife = 2.3F;
        gunproperties.bullet[1].massa = 0.235F;
        gunproperties.bullet[1].kalibr = 0.000567F;
        gunproperties.bullet[1].speed = 700F;
        gunproperties.bullet[1].power = 0.054F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 1.5F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 3F;
        return gunproperties;
    }
}
