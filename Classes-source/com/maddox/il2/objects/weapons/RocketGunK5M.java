// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RocketGunK5M.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeGuidedMissileCarrier;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            RocketGun

public class RocketGunK5M extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunK5M()
    {
    }

    public float bulletMassa()
    {
        return super.bulletMassa / 10F;
    }

    public void shots(int paramInt)
    {
        try
        {
            if(com.maddox.il2.engine.Actor.isValid(super.actor) && (super.actor instanceof com.maddox.il2.objects.air.Aircraft) && (super.actor instanceof com.maddox.il2.objects.air.TypeGuidedMissileCarrier) && (com.maddox.il2.objects.air.Aircraft)super.actor == com.maddox.il2.ai.World.getPlayerAircraft() && ((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)super.actor)).FM).isRealMode() && ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)super.actor).hasMissiles() && ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)super.actor).getMissileLockState() == 0)
            {
                com.maddox.il2.game.HUD.log("K-5M launch cancelled (disengaged)");
                return;
            }
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.game.HUD.log("K-5M launch cancelled (system error)");
        }
        super.shots(paramInt);
        if(paramInt > 0 && com.maddox.il2.engine.Actor.isValid(super.actor) && (super.actor instanceof com.maddox.il2.objects.air.TypeGuidedMissileCarrier) && com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo && super.actor == com.maddox.il2.ai.World.getPlayerAircraft())
            ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)super.actor).shotMissile();
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        return java.lang.Class.forName(x0);
        java.lang.ClassNotFoundException x1;
        x1;
        throw new NoClassDefFoundError(x1.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunK5M.class;
        com.maddox.rts.Property.set(class1, "bulletClass", com.maddox.il2.objects.weapons.MissileK5M.class);
        com.maddox.rts.Property.set(class1, "bullets", 1);
        com.maddox.rts.Property.set(class1, "shotFreq", 0.25F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocketgun_132");
    }
}
