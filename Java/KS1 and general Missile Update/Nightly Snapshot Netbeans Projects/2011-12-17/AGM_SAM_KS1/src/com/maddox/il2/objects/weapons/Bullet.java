// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) deadcode 
// Source File Name:   Bullet.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.*;
import com.maddox.il2.ai.*;
import com.maddox.il2.engine.*;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Time;

public class Bullet extends BulletGeneric
{

    public void move(float f)
    {
        super.move(f);
    }

    public void timeOut()
    {
    }

    public void showExplosion(Actor actor, Point3d point3d, BulletProperties bulletproperties, double d)
    {
        Explosions.generateExplosion(actor, point3d, bulletproperties.power, bulletproperties.powerType, bulletproperties.powerRadius, d);
    }

    public boolean collided(Actor actor, String s, double d)
    {
      com.maddox.il2.ai.EventLog.type("Bullet collided actor=" + actor.getClass().getName() + " s=" + s + " d=" + d + " isNet=" + owner.isNet() + " isNetMirror=" + owner.isNetMirror());
      Exception testExc = new Exception();
      testExc.printStackTrace();
      
        tmpP.interpolate(p0, p1, d);
        if((flags & 0x2000) != 0 && Actor.isValid(owner) && Actor.isValid(actor) && owner.getArmy() != actor.getArmy())
        {
            if(actor instanceof ActorMesh)
            {
                Mesh mesh = ((ActorMesh)actor).mesh();
                l1.set(tmpP);
                actor.pos.getTime(Time.current(), l2);
                l3.sub(l2, l1);
                l3.getMatrix(m2);
                int i = mesh.hookFaceCollisFind(m2, resChunk, m1);
                if(i != -1)
                {
                    mesh.hookFaceMatrix(i, resChunk[0], m1);
                    l2.getMatrix(m2);
                    m2.mul(m1);
                    tmpP.set(m2.m03, m2.m13, m2.m23);
                    if(mesh instanceof HierMesh)
                    {
                        ((HierMesh)mesh).setCurChunk(resChunk[0]);
                        s = ((HierMesh)mesh).chunkName();
                    }
                }
            }
            speed.scale(2D);
        }
        vf.set(speed);
        BulletProperties bulletproperties = properties();
        if(owner != null && (owner == World.getPlayerAircraft() || owner == World.getPlayerGunner()) && !(actor instanceof ActorLand))
        {
            World.cur().scoreCounter.bulletsHit += gun.prop.bulletsCluster;
            if(actor instanceof Aircraft)
                World.cur().scoreCounter.bulletsHitAir += gun.prop.bulletsCluster;
        }
        float f = bulletproperties.massa;
        com.maddox.il2.ai.Shot shot = null;
        if(bulletproperties.cumulativePower > 0.0F)
        {
            for(int j = 0; j < gun.prop.bulletsCluster; j++)
            {
                com.maddox.il2.ai.Shot shot1 = MsgShot.send(actor, s, tmpP, vf, f, owner, bulletproperties.cumulativePower, 1, d);
                if(!Actor.isValid(actor))
                    return true;
                if(j == 0)
                    shot = shot1;
            }

            Explosions.generateShot(actor, shot);
        } else
        {
            double d1 = speed.length();
            float f1 = (float)(((double)f * d1 * d1) / 2D);
            for(int k = 0; k < gun.prop.bulletsCluster; k++)
            {
                com.maddox.il2.ai.Shot shot2;
                if(bulletproperties.powerRadius == 0.0F)
                    shot2 = MsgShot.send(actor, s, tmpP, vf, f, owner, f1, bulletproperties.power != 0.0F ? 3 : 2, d);
                else
                    shot2 = MsgShot.send(actor, s, tmpP, vf, f, owner, f1, 0, d);
                if(!Actor.isValid(actor))
                    return true;
                if(k == 0)
                    shot = shot2;
                if(bulletproperties.power > 0.0F && bulletproperties.powerRadius > 0.0F)
                    MsgExplosion.send(actor, s, tmpP, owner, f, bulletproperties.power + 0.03F * f, bulletproperties.powerType, bulletproperties.powerRadius);
            }

            Explosions.generateShot(actor, shot);
            if(bulletproperties.power > 0.0F)
                showExplosion(actor, tmpP, bulletproperties, d);
        }
        return true;
    }

    public Bullet(Vector3d vector3d, int i, GunGeneric gungeneric, Loc loc, Vector3d vector3d1, long l)
    {
        super(i, gungeneric, loc, vector3d1, l);
        if(owner != null && (owner.equals(World.getPlayerAircraft()) || owner.isContainOwner(World.getPlayerAircraft())) && !World.cur().diffCur.Realistic_Gunnery)
            flags |= 0x40000000;
    }

    protected static Point3d tmpP = new Point3d();
    protected static Vector3f vf = new Vector3f();
    private static Loc l1 = new Loc();
    private static Loc l2 = new Loc();
    private static Loc l3 = new Loc();
    private static Matrix4d m1 = new Matrix4d();
    private static Matrix4d m2 = new Matrix4d();
    private static int resChunk[] = new int[1];

}
