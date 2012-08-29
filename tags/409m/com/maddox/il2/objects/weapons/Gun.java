// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Gun.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetGunner;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Bullet, GunEmpty

public class Gun extends com.maddox.il2.engine.GunGeneric
    implements com.maddox.il2.ai.BulletEmitter
{

    public Gun()
    {
    }

    public void initRealisticGunnery()
    {
        boolean flag = !isContainOwner(com.maddox.il2.ai.World.getPlayerAircraft()) || com.maddox.il2.ai.World.cur().diffCur.Realistic_Gunnery;
        initRealisticGunnery(flag);
    }

    public int nextIndexBulletType()
    {
        return 0;
    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc1, com.maddox.JGP.Vector3d vector3d, long l)
    {
        return new Bullet(i, gungeneric, loc1, vector3d, l);
    }

    public void doStartBullet(double d)
    {
        int i = nextIndexBulletType();
        long l = com.maddox.rts.Time.tick() + (long)((double)com.maddox.rts.Time.tickLenFms() * d);
        pos.getTime(l, loc);
        com.maddox.il2.engine.Loc loc1 = loc;
        com.maddox.il2.engine.Orient orient;
        if(prop.maxDeltaAngle > 0.0F)
        {
            orient = loc.getOrient();
            float f = com.maddox.il2.ai.World.Rnd().nextFloat(-prop.maxDeltaAngle, prop.maxDeltaAngle);
            float f1 = com.maddox.il2.ai.World.Rnd().nextFloat(-prop.maxDeltaAngle, prop.maxDeltaAngle);
            orient.increment(f, f1, 0.0F);
        } else
        {
            orient = loc1.getOrient();
        }
        v1.set(1.0D, 0.0D, 0.0D);
        orient.transform(v1);
        v1.scale(prop.bullet[i].speed);
        com.maddox.il2.engine.Actor actor = getOwner();
        if(actor instanceof com.maddox.il2.objects.air.Aircraft)
        {
            v2.set(v1);
            v2.scale(prop.bullet[i].massa * (float)prop.bulletsCluster);
            ((com.maddox.il2.objects.air.Aircraft)actor).FM.gunPulse(v2);
            actor.getSpeed(v);
            v1.add(v);
            if(actor == com.maddox.il2.ai.World.getPlayerAircraft())
            {
                com.maddox.il2.ai.World.cur().scoreCounter.bulletsFire += prop.bulletsCluster;
                if(com.maddox.il2.ai.World.cur().diffCur.Realistic_Gunnery && (((com.maddox.il2.objects.air.Aircraft)actor).FM instanceof com.maddox.il2.fm.RealFlightModel))
                {
                    com.maddox.il2.engine.Loc loc2 = pos.getRel();
                    if(java.lang.Math.abs(loc2.getPoint().y) < 0.5D)
                    {
                        double d1 = prop.bullet[i].massa * prop.bullet[i].speed;
                        v.x = com.maddox.il2.ai.World.Rnd().nextDouble(-20D, 20D) * d1;
                        v.y = com.maddox.il2.ai.World.Rnd().nextDouble(-100D, 200D) * d1;
                        v.z = com.maddox.il2.ai.World.Rnd().nextDouble(-200D, 200D) * d1;
                        v.scale(0.29999999999999999D);
                        ((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.air.Aircraft)actor).FM).gunMomentum(v, false);
                    } else
                    {
                        double d2 = prop.bullet[i].massa * (float)prop.bulletsCluster * prop.shotFreq;
                        v2.set(-1D, 0.0D, 0.0D);
                        loc2.transform(v2);
                        double d4 = 0.45000000000000001D * java.lang.Math.sqrt(java.lang.Math.sqrt(prop.bullet[i].massa));
                        d4 = 64D * com.maddox.il2.ai.World.Rnd().nextDouble(1.0D - d4, 1.0D + d4);
                        v2.scale(d4 * v1.length() * d2);
                        v.cross(loc2.getPoint(), v2);
                        v.y *= 0.10000000149011612D;
                        v.z *= 0.5D;
                        v.scale(0.29999999999999999D);
                        ((com.maddox.il2.fm.RealFlightModel)((com.maddox.il2.objects.air.Aircraft)actor).FM).gunMomentum(v, true);
                    }
                }
            }
        } else
        if(getSpeed(v) > 0.0D)
            v1.add(v);
        if((actor instanceof com.maddox.il2.objects.air.NetGunner) && com.maddox.il2.ai.World.isPlayerGunner())
            com.maddox.il2.ai.World.cur().scoreCounter.bulletsFire += prop.bulletsCluster;
        com.maddox.il2.objects.weapons.Bullet bullet = createNextBullet(i, this, loc1, v1, com.maddox.rts.Time.current() + (long)(int)(prop.bullet[i].timeLife * 1000F));
        bullet.move((float)((1.0D - d) * (double)com.maddox.rts.Time.tickLenFs()));
        bullet.bMoved = true;
        bullet.flags |= 0x1000;
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && bulletNum % prop.traceFreq == 0)
        {
            bullet.flags |= 0x80000000;
            if(prop.bullet[i].traceTrail != null)
            {
                com.maddox.il2.engine.Camera3D camera3d = com.maddox.il2.game.Main3D.cur3D().camera3D;
                if(com.maddox.il2.engine.Actor.isValid(camera3d))
                {
                    double d3 = 1000000D;
                    com.maddox.JGP.Point3d point3d = loc1.getPoint();
                    v1.scale(prop.bullet[i].timeLife);
                    p1.add(point3d, v1);
                    com.maddox.JGP.Point3d point3d1 = ((com.maddox.il2.engine.Actor) (camera3d)).pos.getAbsPoint();
                    double d5 = p1.x - point3d.x;
                    double d6 = p1.y - point3d.y;
                    double d7 = p1.z - point3d.z;
                    double d8 = d5 * d5 + d6 * d6 + d7 * d7;
                    double d9 = ((point3d1.x - point3d.x) * d5 + (point3d1.y - point3d.y) * d6 + (point3d1.z - point3d.z) * d7) / d8;
                    if(d9 > 0.0D && d9 < 1.0D)
                    {
                        double d10 = point3d.x + d9 * d5;
                        double d12 = point3d.y + d9 * d6;
                        double d14 = point3d.z + d9 * d7;
                        double d15 = (d10 - point3d1.x) * (d10 - point3d1.x) + (d12 - point3d1.y) * (d12 - point3d1.y) + (d14 - point3d1.z) * (d14 - point3d1.z);
                        if(d15 > d3)
                            return;
                    } else
                    {
                        double d11 = (p1.x - point3d1.x) * (p1.x - point3d1.x) + (p1.y - point3d1.y) * (p1.y - point3d1.y) + (p1.z - point3d1.z) * (p1.z - point3d1.z);
                        double d13 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y) + (point3d.z - point3d1.z) * (point3d.z - point3d1.z);
                        if(d13 > d3 && d11 > d3)
                            return;
                    }
                    bullet.effTrail = com.maddox.il2.engine.Eff3DActor.NewPosMove(pos.getAbs(), 1.0F, prop.bullet[i].traceTrail, -1F);
                }
            }
        }
    }

    private boolean nameEQ(com.maddox.il2.engine.HierMesh hiermesh, int i, int j)
    {
        if(hiermesh == null)
            return false;
        hiermesh.setCurChunk(i);
        java.lang.String s = hiermesh.chunkName();
        hiermesh.setCurChunk(j);
        java.lang.String s1 = hiermesh.chunkName();
        int l = java.lang.Math.min(s.length(), s1.length());
        for(int k = 0; k < l; k++)
        {
            char c = s.charAt(k);
            if(c == '_')
                return true;
            if(c != s1.charAt(k))
                return false;
        }

        return true;
    }

    public com.maddox.il2.ai.BulletEmitter detach(com.maddox.il2.engine.HierMesh hiermesh, int i)
    {
        if(isDestroyed())
            return com.maddox.il2.objects.weapons.GunEmpty.get();
        if(i == -1 || nameEQ(hiermesh, i, chunkIndx))
        {
            destroy();
            return com.maddox.il2.objects.weapons.GunEmpty.get();
        } else
        {
            return this;
        }
    }

    public float TravelTime(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        float f = (float)point3d.distance(point3d1);
        if(f < prop.aimMinDist || f > prop.aimMaxDist)
            return -1F;
        else
            return f / prop.bullet[0].speed;
    }

    public boolean FireDirection(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Vector3d vector3d)
    {
        float f = (float)point3d.distance(point3d1);
        if(f < prop.aimMinDist || f > prop.aimMaxDist)
        {
            return false;
        } else
        {
            vector3d.set(point3d1);
            vector3d.sub(point3d);
            vector3d.scale(1.0F / f);
            return true;
        }
    }

    private static com.maddox.il2.engine.Loc loc = new Loc();
    private static com.maddox.JGP.Vector3d v = new Vector3d();
    private static com.maddox.JGP.Vector3d v1 = new Vector3d();
    private static com.maddox.JGP.Vector3d v2 = new Vector3d();
    private static com.maddox.JGP.Point3d p1 = new Point3d();

}
