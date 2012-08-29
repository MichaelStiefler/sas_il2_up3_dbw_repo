// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MissileR55f.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.NetChannel;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Missile, MissileR55

public class MissileR55f extends com.maddox.il2.objects.weapons.Missile
{
    static class SPAWN extends com.maddox.il2.objects.weapons.Missile.SPAWN
    {

        public void doSpawn(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f)
        {
            new MissileR55(actor, netchannel, i, point3d, orient, f);
        }

        SPAWN()
        {
        }
    }


    public MissileR55f(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f)
    {
        ((com.maddox.il2.objects.weapons.Missile)this).MissileInit(actor, netchannel, i, point3d, orient, f);
    }

    public MissileR55f()
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
        java.lang.Class class1 = com.maddox.il2.objects.weapons.MissileR55f.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "mesh", "3do/arms/R-55f/mono.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sprite", "3do/effects/RocketSidewinder/RocketSidewinderSpriteBlack.eff");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "flame", "3do/Effects/RocketSidewinder/RocketSidewinderFlame.sim");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "smoke", "3do/Effects/RocketSidewinder/RocketSidewinderSmoke.eff");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitColor", ((java.lang.Object) (new Color3f(1.0F, 1.0F, 0.5F))));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitLen", 50F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "emitMax", 0.4F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sound", "weapon.rocket_132");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "radius", 10F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeLife", 12F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "timeFire", 7F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "force", 8500F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "forceT1", 1.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "forceP1", 0.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "forceT2", 0.2F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "forceP2", 50F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "dragCoefficient", 0.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "power", 1.135F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "powerType", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "kalibr", 0.2F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massa", 74.25F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "massaEnd", 68F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "stepMode", 0);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "launchType", 2);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "detectorType", 1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "sunRayAngle", 22F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "groundTrackFactor", 9F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "flareLockTime", 1000L);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "trackDelay", 1000L);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "failureRate", 50F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "maxLockGForce", 99.9F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "maxFOVfrom", 24F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "maxFOVto", 60F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PkMaxFOVfrom", 28F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PkMaxFOVto", 70F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PkDistMin", 800F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PkDistOpt", 1500F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PkDistMax", 4500F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "leadPercent", 0.0F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "maxGForce", 12F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "stepsForFullTurn", 12F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "fxLock", "weapon.K5.lock");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "fxNoLock", "weapon.K5.nolock");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "smplLock", "K5_lock.wav");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "smplNoLock", "K5_no_lock.wav");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "friendlyName", "R-55");
        com.maddox.rts.Spawn.add(class1, ((java.lang.Object) (new SPAWN())));
    }
}
