// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MissileK13A.java

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

public class MissileK13A extends com.maddox.il2.objects.weapons.Missile
{
    static class SPAWN extends com.maddox.il2.objects.weapons.Missile.SPAWN
    {

        public void doSpawn(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f)
        {
            new MissileK13A(actor, netchannel, i, point3d, orient, f);
        }

        SPAWN()
        {
        }
    }


    public MissileK13A(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f)
    {
        ((com.maddox.il2.objects.weapons.Missile)this).MissileInit(actor, netchannel, i, point3d, orient, f);
    }

    public MissileK13A()
    {
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.MissileK13A.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3do/arms/K-13A/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sprite", "3do/effects/RocketSidewinder/RocketSidewinderSpriteBlack.eff");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "flame", "3do/Effects/RocketSidewinder/RocketSidewinderFlame.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "smoke", "3do/Effects/RocketSidewinder/RocketSidewinderSmoke.eff");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitColor", ((java.lang.Object) (new Color3f(1.0F, 1.0F, 0.5F))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitLen", 50F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitMax", 0.4F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeLife", 35F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeFire", 2.4F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "force", 12000F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "forceT1", 0.7F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "forceP1", 0.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "forceT2", 0.2F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "forceP2", 50F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "dragCoefficient", 0.3F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 0.42F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 8.96F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.2F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 72F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massaEnd", 47F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "stepMode", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "launchType", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "detectorType", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sunRayAngle", 22F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "groundTrackFactor", 9F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "flareLockTime", 1000L);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "trackDelay", 1000L);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "failureRate", 50F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "maxLockGForce", 3F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "maxFOVfrom", 24F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "maxFOVto", 60F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PkMaxFOVfrom", 28F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PkMaxFOVto", 70F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PkDistMin", 400F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PkDistOpt", 1500F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PkDistMax", 5000F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "maxSpeed", 2012.214F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "leadPercent", 100F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "maxGForce", 11F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "stepsForFullTurn", 10);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "fxLock", "weapon.AIM9.lock");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "fxNoLock", "weapon.AIM9.nolock");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "smplLock", "AIM9_lock.wav");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "smplNoLock", "AIM9_no_lock.wav");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "friendlyName", "K-13A");
        com.maddox.rts.Spawn.add(class1, ((java.lang.Object) (new SPAWN())));
    }
}
