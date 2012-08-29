// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MissileAIM9B.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.NetChannel;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Missile

public class MissileAIM9B extends com.maddox.il2.objects.weapons.Missile
{
    static class SPAWN extends com.maddox.il2.objects.weapons.Missile.SPAWN
    {

        public void doSpawn(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f)
        {
            new MissileAIM9B(actor, netchannel, i, point3d, orient, f);
        }

        SPAWN()
        {
        }
    }


    public MissileAIM9B(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f)
    {
        MissileInit(actor, netchannel, i, point3d, orient, f);
    }

    public MissileAIM9B()
    {
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.MissileAIM9B.class;
        com.maddox.rts.Property.set(class1, "mesh", "3do/arms/AIM9B/mono.sim");
        com.maddox.rts.Property.set(class1, "sprite", "3do/effects/RocketSidewinder/RocketSidewinderSpriteBlack.eff");
        com.maddox.rts.Property.set(class1, "flame", "3do/Effects/RocketSidewinder/RocketSidewinderFlame.sim");
        com.maddox.rts.Property.set(class1, "smoke", "3do/Effects/RocketSidewinder/RocketSidewinderSmoke.eff");
        com.maddox.rts.Property.set(class1, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
        com.maddox.rts.Property.set(class1, "emitLen", 50F);
        com.maddox.rts.Property.set(class1, "emitMax", 0.4F);
        com.maddox.rts.Property.set(class1, "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(class1, "timeLife", 30F);
        com.maddox.rts.Property.set(class1, "timeFire", 2.2F);
        com.maddox.rts.Property.set(class1, "force", 12000F);
        com.maddox.rts.Property.set(class1, "forceT1", 0.5F);
        com.maddox.rts.Property.set(class1, "forceP1", 0.0F);
        com.maddox.rts.Property.set(class1, "forceT2", 0.2F);
        com.maddox.rts.Property.set(class1, "forceP2", 50F);
        com.maddox.rts.Property.set(class1, "dragCoefficient", 0.3F);
        com.maddox.rts.Property.set(class1, "powerType", 0);
        com.maddox.rts.Property.set(class1, "power", 0.45F);
        com.maddox.rts.Property.set(class1, "radius", 9.14F);
        com.maddox.rts.Property.set(class1, "kalibr", 0.2F);
        com.maddox.rts.Property.set(class1, "massa", 70F);
        com.maddox.rts.Property.set(class1, "massaEnd", 45F);
        com.maddox.rts.Property.set(class1, "stepMode", 0);
        com.maddox.rts.Property.set(class1, "launchType", 1);
        com.maddox.rts.Property.set(class1, "detectorType", 1);
        com.maddox.rts.Property.set(class1, "sunRayAngle", 20F);
        com.maddox.rts.Property.set(class1, "groundTrackFactor", 10F);
        com.maddox.rts.Property.set(class1, "flareLockTime", 1000L);
        com.maddox.rts.Property.set(class1, "trackDelay", 1000L);
        com.maddox.rts.Property.set(class1, "failureRate", 50F);
        com.maddox.rts.Property.set(class1, "maxLockGForce", 2.0F);
        com.maddox.rts.Property.set(class1, "maxFOVfrom", 25F);
        com.maddox.rts.Property.set(class1, "maxFOVto", 60F);
        com.maddox.rts.Property.set(class1, "PkMaxFOVfrom", 30F);
        com.maddox.rts.Property.set(class1, "PkMaxFOVto", 70F);
        com.maddox.rts.Property.set(class1, "PkDistMin", 400F);
        com.maddox.rts.Property.set(class1, "PkDistOpt", 1500F);
        com.maddox.rts.Property.set(class1, "PkDistMax", 4500F);
        com.maddox.rts.Property.set(class1, "leadPercent", 100F);
        com.maddox.rts.Property.set(class1, "maxGForce", 12F);
        com.maddox.rts.Property.set(class1, "stepsForFullTurn", 12);
        com.maddox.rts.Property.set(class1, "fxLock", "weapon.AIM9.lock");
        com.maddox.rts.Property.set(class1, "fxNoLock", "weapon.AIM9.nolock");
        com.maddox.rts.Property.set(class1, "smplLock", "AIM9_lock.wav");
        com.maddox.rts.Property.set(class1, "smplNoLock", "AIM9_no_lock.wav");
        com.maddox.rts.Property.set(class1, "friendlyName", "AIM-9B");
        com.maddox.rts.Spawn.add(class1, new SPAWN());
    }
}
