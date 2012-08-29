// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CannonRocketSimpleRS132.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Orient;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            CannonRocketSimple, RocketSimpleRS132

public class CannonRocketSimpleRS132 extends com.maddox.il2.objects.weapons.CannonRocketSimple
{

    public CannonRocketSimpleRS132()
    {
    }

    protected void Specify(com.maddox.il2.engine.GunProperties gunproperties)
    {
        gunproperties.shotFreq = 0.33F;
        gunproperties.aimMinDist = 20F;
        gunproperties.aimMaxDist = 8000F;
        com.maddox.il2.engine.BulletProperties bulletproperties = gunproperties.bullet[0];
        bulletproperties.speed = 400F;
    }

    public void launch(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f, com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.objects.weapons.RocketSimpleRS132 rocketsimplers132 = new RocketSimpleRS132(point3d, orient, actor);
        rocketsimplers132.start(f);
    }
}
