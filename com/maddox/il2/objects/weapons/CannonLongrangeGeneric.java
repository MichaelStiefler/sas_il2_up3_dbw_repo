// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonLongrangeGeneric.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonMidrangeGeneric

public abstract class CannonLongrangeGeneric extends com.maddox.il2.objects.weapons.CannonMidrangeGeneric
    implements com.maddox.il2.ai.BulletAimer
{

    public CannonLongrangeGeneric()
    {
    }

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = super.createProperties();
        gunproperties.sound = "weapon.Cannon100";
        gunproperties.aimMinDist = 20F;
        gunproperties.aimMaxDist = 25000F;
        gunproperties.bullet = (new com.maddox.il2.engine.BulletProperties[] {
            new BulletProperties(), new BulletProperties()
        });
        for(int i = 0; i < gunproperties.bullet.length; i++)
        {
            gunproperties.bullet[i].massa = 0.001F;
            gunproperties.bullet[i].kalibr = 9F;
            gunproperties.bullet[i].speed = 10F;
            gunproperties.bullet[i].power = 0.0F;
            if(i == 0)
                gunproperties.bullet[i].powerType = 1;
            else
                gunproperties.bullet[i].powerType = 0;
            gunproperties.bullet[i].powerRadius = 140F;
            gunproperties.bullet[i].timeLife = 60F;
            gunproperties.bullet[i].traceMesh = null;
            gunproperties.bullet[i].traceTrail = null;
            gunproperties.bullet[i].traceColor = 0;
        }

        float f = Specify(gunproperties);
        com.maddox.il2.objects.weapons.CannonMidrangeGeneric.autocomputeSplintersRadiuses(gunproperties.bullet);
        for(int j = 0; j < gunproperties.bullet.length; j++)
        {
            float f1 = gunproperties.aimMaxDist / (gunproperties.bullet[j].speed * 0.707F);
            gunproperties.bullet[j].timeLife = f1 * 2.0F;
        }

        if(f > 0.0F)
        {
            if(f <= 20F)
                f = 20F;
            if(f >= 70F)
                f = 70F;
            f = (f - 20F) / 50F;
            gunproperties.maxDeltaAngle = 0.3F - f * 0.2F;
        } else
        {
            gunproperties.maxDeltaAngle = 0.2F;
        }
        return gunproperties;
    }

    protected abstract float Specify(com.maddox.il2.engine.GunProperties gunproperties);
}
