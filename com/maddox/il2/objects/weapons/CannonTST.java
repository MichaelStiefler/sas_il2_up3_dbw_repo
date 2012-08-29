// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonTST.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Gun

public class CannonTST extends com.maddox.il2.objects.weapons.Gun
{

    public CannonTST()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = true;
        gunproperties.bUseHookAsRel = false;
        gunproperties.fireMesh = null;
        gunproperties.fire = "3DO/Effects/GunFire/88mm/CannonTank.eff";
        gunproperties.sprite = null;
        gunproperties.smoke = "effects/smokes/CannonTank.eff";
        gunproperties.shells = "3DO/Effects/GunShells/CannonShells.eff";
        gunproperties.sound = "weapon.Cannon75";
        gunproperties.emitColor = new Color3f(1.0F, 0.5F, 0.0F);
        gunproperties.emitI = 10F;
        gunproperties.emitR = 4F;
        gunproperties.emitTime = 0.1F;
        gunproperties.aimMinDist = 10F;
        gunproperties.aimMaxDist = 1000F;
        gunproperties.maxDeltaAngle = 0.0F;
        gunproperties.shotFreq = 5F;
        gunproperties.traceFreq = 1;
        gunproperties.bullets = 100;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties()
        });
        gunproperties.bullet[0].massa = 5.2F;
        gunproperties.bullet[0].kalibr = 0.075F;
        gunproperties.bullet[0].speed = 800F;
        gunproperties.bullet[0].power = 0.0F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].timeLife = 5F;
        gunproperties.bullet[0].traceMesh = "3DO/Effects/Tracers/7mmYellow/mono.sim";
        gunproperties.bullet[0].traceTrail = "effects/Smokes/SmokeBlack_BuletteTrail.eff";
        gunproperties.bullet[0].traceColor = 0xd200ffff;
        return gunproperties;
    }
}
