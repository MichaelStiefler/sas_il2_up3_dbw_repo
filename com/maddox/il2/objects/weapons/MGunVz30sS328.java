// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunVz30sS328.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            MGunVz30s

public class MGunVz30sS328 extends com.maddox.il2.objects.weapons.MGunVz30s
{

    public MGunVz30sS328()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties(), new BulletProperties()
        });
        gunproperties.bullet[0].massa = 0.01F;
        gunproperties.bullet[0].kalibr = 4.35483E-005F;
        gunproperties.bullet[0].speed = 860F;
        gunproperties.bullet[0].power = 0.0F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = null;
        gunproperties.bullet[0].traceTrail = null;
        gunproperties.bullet[0].traceColor = 0;
        gunproperties.bullet[0].timeLife = 3F;
        gunproperties.bullet[1].massa = 0.01F;
        gunproperties.bullet[1].kalibr = 8E-005F;
        gunproperties.bullet[1].speed = 860F;
        gunproperties.bullet[1].power = 0.0F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.0F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 3F;
        gunproperties.bullet[0].massa = 0.01F;
        gunproperties.bullet[0].kalibr = 4.35483E-005F;
        gunproperties.bullet[0].speed = 860F;
        gunproperties.bullet[0].power = 0.0F;
        gunproperties.bullet[0].powerType = 0;
        gunproperties.bullet[0].powerRadius = 0.0F;
        gunproperties.bullet[0].traceMesh = null;
        gunproperties.bullet[0].traceTrail = null;
        gunproperties.bullet[0].traceColor = 0;
        gunproperties.bullet[0].timeLife = 3F;
        gunproperties.bullet[1].massa = 0.01F;
        gunproperties.bullet[1].kalibr = 8E-005F;
        gunproperties.bullet[1].speed = 860F;
        gunproperties.bullet[1].power = 0.0F;
        gunproperties.bullet[1].powerType = 0;
        gunproperties.bullet[1].powerRadius = 0.0F;
        gunproperties.bullet[1].traceMesh = null;
        gunproperties.bullet[1].traceTrail = null;
        gunproperties.bullet[1].traceColor = 0;
        gunproperties.bullet[1].timeLife = 3F;
        gunproperties.bullet[2].massa = 0.01F;
        gunproperties.bullet[2].kalibr = 4.35483E-005F;
        gunproperties.bullet[2].speed = 860F;
        gunproperties.bullet[2].power = 0.002F;
        gunproperties.bullet[2].powerType = 0;
        gunproperties.bullet[2].powerRadius = 0.0F;
        gunproperties.bullet[2].traceMesh = "3DO/Effects/Tracers/20mmRed/mono.sim";
        gunproperties.bullet[2].traceTrail = null;
        gunproperties.bullet[2].traceColor = 0xd2002eff;
        gunproperties.bullet[2].timeLife = 3F;
        return gunproperties;
    }
}
