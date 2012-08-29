// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunBrowning50s_jap.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunBrowning50s

public class MGunBrowning50s_jap extends com.maddox.il2.objects.weapons.MGunBrowning50s
{

    public MGunBrowning50s_jap()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.0485F;
        gunproperties.bullet[0].kalibr = 0.0001209675F;
        gunproperties.bullet[0].speed = 870F;
        gunproperties.bullet[0].power = 0.002F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = "3do/effects/tracers/20mmYellow/mono.sim";
        gunproperties.bullet[0].traceTrail = null;
        gunproperties.bullet[0].traceColor = 0xf900ffff;
        gunproperties.bullet[0].timeLife = 6.5F;
        gunproperties.bullet[1].massa = 0.0485F;
        gunproperties.bullet[1].kalibr = 0.0001209675F;
        gunproperties.bullet[1].speed = 870F;
        gunproperties.bullet[1].power = 0.0F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.0F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 6.52F;
        gunproperties.bullet[2].massa = 0.0485F;
        gunproperties.bullet[2].kalibr = 0.0001258062F;
        gunproperties.bullet[2].speed = 870F;
        gunproperties.bullet[2].power = 0.0009768F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.15F;
        gunproperties.bullet[2].traceMesh = null;
        gunproperties.bullet[2].traceTrail = null;
        gunproperties.bullet[2].traceColor = 0;
        gunproperties.bullet[2].timeLife = 6.5F;
        gunproperties.bullet[3].massa = 0.0485F;
        gunproperties.bullet[3].kalibr = 0.0001209675F;
        gunproperties.bullet[3].speed = 870F;
        gunproperties.bullet[3].power = 0.0F;
        gunproperties.bullet[3].powerType = 0;
        gunproperties.bullet[3].powerRadius = 0.0F;
        gunproperties.bullet[3].traceMesh = null;
        gunproperties.bullet[3].traceTrail = null;
        gunproperties.bullet[3].traceColor = 0;
        gunproperties.bullet[3].timeLife = 6.25F;
        return gunproperties;
    }
}
