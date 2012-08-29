// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunNull.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunNullGeneric

public class MGunNull extends com.maddox.il2.objects.weapons.MGunNullGeneric
{

    public MGunNull()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bCannon = false;
        gunproperties.bUseHookAsRel = true;
        gunproperties.fireMesh = null;
        gunproperties.fire = null;
        gunproperties.sprite = null;
        gunproperties.smoke = null;
        gunproperties.shells = null;
        gunproperties.sound = null;
        gunproperties.emitColor = null;
        gunproperties.emitI = 0.0F;
        gunproperties.emitR = 0.0F;
        gunproperties.emitTime = 0.0F;
        gunproperties.aimMinDist = 0.0F;
        gunproperties.aimMaxDist = 0.0F;
        gunproperties.weaponType = 3;
        gunproperties.maxDeltaAngle = 0.0F;
        gunproperties.shotFreq = 0.0F;
        gunproperties.traceFreq = 0;
        gunproperties.bullets = 0x7fffffff;
        gunproperties.bulletsCluster = 1;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.0F;
        gunproperties.bullet[0].kalibr = 0.0F;
        gunproperties.bullet[0].speed = 0.0F;
        gunproperties.bullet[0].power = 0.0F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = null;
        gunproperties.bullet[0].traceTrail = null;
        gunproperties.bullet[0].traceColor = 0;
        gunproperties.bullet[0].timeLife = 0.0F;
        return gunproperties;
    }
}
