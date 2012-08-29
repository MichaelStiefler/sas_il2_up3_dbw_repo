// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   RocketGunK13A.java

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

public class RocketGunK13A extends com.maddox.il2.objects.weapons.RocketGun
{

    public RocketGunK13A()
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
            if(com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Interpolate)this).actor) && (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.Aircraft) && (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeGuidedMissileCarrier) && (com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor == com.maddox.il2.ai.World.getPlayerAircraft() && ((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Interpolate)this).actor)).FM).isRealMode() && ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)((com.maddox.il2.engine.Interpolate)this).actor).hasMissiles() && ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)((com.maddox.il2.engine.Interpolate)this).actor).getMissileLockState() == 0)
            {
                com.maddox.il2.game.HUD.log("K-13A launch cancelled (disengaged)");
                return;
            }
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.game.HUD.log("K-13A launch cancelled (system error)");
        }
        super.shots(paramInt);
        if(paramInt > 0 && com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Interpolate)this).actor) && (((com.maddox.il2.engine.Interpolate)this).actor instanceof com.maddox.il2.objects.air.TypeGuidedMissileCarrier) && com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo && ((com.maddox.il2.engine.Interpolate)this).actor == com.maddox.il2.ai.World.getPlayerAircraft())
            ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)((com.maddox.il2.engine.Interpolate)this).actor).shotMissile();
    }

    static java.lang.Class _mthclass$(java.lang.String x0)
    {
        return java.lang.Class.forName(x0);
        java.lang.ClassNotFoundException x1;
        x1;
        throw new NoClassDefFoundError(((java.lang.Throwable) (x1)).getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.weapons.RocketGunK13A.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bulletClass", ((java.lang.Object) (((java.lang.Object) (com.maddox.il2.objects.weapons.MissileK13A.class)))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "bullets", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "shotFreq", 0.25F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.rocketgun_132");
    }
}
