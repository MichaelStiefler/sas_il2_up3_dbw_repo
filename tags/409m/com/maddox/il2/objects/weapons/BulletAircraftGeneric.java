// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BulletAircraftGeneric.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.BulletGeneric;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.MsgAction;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bullet, MGunAircraftGeneric

public class BulletAircraftGeneric extends com.maddox.il2.objects.weapons.Bullet
{

    public void move(float f)
    {
        if(gun == null)
        {
            return;
        } else
        {
            com.maddox.JGP.Point3d point3d = p1;
            p1 = p0;
            p0 = point3d;
            com.maddox.il2.engine.BulletGeneric.dspeed.scale((double)(gun.bulletKV[indx()] * f * 1.0F * com.maddox.il2.objects.weapons.BulletAircraftGeneric.fv(speed.length())) / speed.length(), speed);
            com.maddox.il2.engine.BulletGeneric.dspeed.z += gun.bulletAG[indx()] * f;
            speed.add(com.maddox.il2.engine.BulletGeneric.dspeed);
            p1.scaleAdd(f, speed, p0);
            return;
        }
    }

    public void timeOut()
    {
        if(gun() != null)
            com.maddox.il2.objects.effects.Explosions.generateExplosion(null, p1, gun().prop.bullet[indx()].power, gun().prop.bullet[indx()].powerType, gun().prop.bullet[indx()].powerRadius, 0.0D);
    }

    public void destroy()
    {
        if(com.maddox.il2.game.Mission.isPlaying() && !com.maddox.il2.net.NetMissionTrack.isPlaying() && com.maddox.il2.engine.Actor.isValid(owner()) && com.maddox.il2.engine.Actor.isValid(gun()) && (gun() instanceof com.maddox.il2.objects.weapons.MGunAircraftGeneric) && owner() == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo)
        {
            int i = bulletss - hashCode();
            if(i != 0 && i <= gun().countBullets() && (i != -1 || !com.maddox.il2.ai.World.isPlayerGunner()))
                postRemove(owner());
        }
        super.destroy();
    }

    private void postRemove(com.maddox.il2.engine.Actor actor)
    {
        new com.maddox.rts.MsgAction(false, actor) {

            public void doAction(java.lang.Object obj)
            {
                if(obj instanceof com.maddox.il2.objects.air.Aircraft)
                {
                    com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)obj;
                    if(com.maddox.il2.engine.Actor.isValid(aircraft) && com.maddox.il2.game.Mission.isPlaying())
                        aircraft.detachGun(-1);
                }
            }

        }
;
    }

    public BulletAircraftGeneric(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        super(i, gungeneric, loc, vector3d, l);
        if(com.maddox.il2.game.Mission.isPlaying() && !com.maddox.il2.net.NetMissionTrack.isPlaying() && com.maddox.il2.engine.Actor.isValid(owner()) && com.maddox.il2.engine.Actor.isValid(gun()) && (gun() instanceof com.maddox.il2.objects.weapons.MGunAircraftGeneric) && owner() == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo)
        {
            int j = gun().countBullets();
            bulletss = j + hashCode();
            com.maddox.il2.objects.weapons.MGunAircraftGeneric mgunaircraftgeneric = (com.maddox.il2.objects.weapons.MGunAircraftGeneric)gun();
            if(mgunaircraftgeneric.guardBullet != null && j >= mgunaircraftgeneric.guardBullet.bulletss - mgunaircraftgeneric.guardBullet.hashCode() && (j != -1 || !com.maddox.il2.ai.World.isPlayerGunner()))
                postRemove(owner());
            mgunaircraftgeneric.guardBullet = this;
        }
    }

    static float fv(double d)
    {
        return d <= 1090D ? (float)(fv[(int)d / 100] + fv[(int)d / 100 + 1]) / 2.0F : 333F;
    }

    protected int bulletss;
    private static final int fv[] = {
        1, 1, 5, 15, 52, 87, 123, 160, 196, 233, 
        269, 333
    };

}
