// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Bullet.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.MsgShot;
import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletGeneric;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Time;

public class Bullet extends com.maddox.il2.engine.BulletGeneric
{

    public void move(float f)
    {
        super.move(f);
    }

    public void timeOut()
    {
    }

    public void showExplosion(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.BulletProperties bulletproperties, double d)
    {
        com.maddox.il2.objects.effects.Explosions.generateExplosion(actor, point3d, bulletproperties.power, bulletproperties.powerType, bulletproperties.powerRadius, d);
    }

    public boolean collided(com.maddox.il2.engine.Actor actor, java.lang.String s, double d)
    {
        tmpP.interpolate(p0, p1, d);
        if((flags & 0x2000) != 0 && com.maddox.il2.engine.Actor.isValid(owner) && com.maddox.il2.engine.Actor.isValid(actor) && owner.getArmy() != actor.getArmy())
        {
            if(actor instanceof com.maddox.il2.engine.ActorMesh)
            {
                com.maddox.il2.engine.Mesh mesh = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
                l1.set(tmpP);
                actor.pos.getTime(com.maddox.rts.Time.current(), l2);
                l3.sub(l2, l1);
                l3.getMatrix(m2);
                int i = mesh.hookFaceCollisFind(m2, resChunk, m1);
                if(i != -1)
                {
                    mesh.hookFaceMatrix(i, resChunk[0], m1);
                    l2.getMatrix(m2);
                    m2.mul(m1);
                    tmpP.set(m2.m03, m2.m13, m2.m23);
                    if(mesh instanceof com.maddox.il2.engine.HierMesh)
                    {
                        ((com.maddox.il2.engine.HierMesh)mesh).setCurChunk(resChunk[0]);
                        s = ((com.maddox.il2.engine.HierMesh)mesh).chunkName();
                    }
                }
            }
            speed.scale(2D);
        }
        vf.set(speed);
        com.maddox.il2.engine.BulletProperties bulletproperties = properties();
        if(owner != null && (owner == com.maddox.il2.ai.World.getPlayerAircraft() || owner == com.maddox.il2.ai.World.getPlayerGunner()) && !(actor instanceof com.maddox.il2.objects.ActorLand))
        {
            com.maddox.il2.ai.World.cur().scoreCounter.bulletsHit += gun.prop.bulletsCluster;
            if(actor instanceof com.maddox.il2.objects.air.Aircraft)
                com.maddox.il2.ai.World.cur().scoreCounter.bulletsHitAir += gun.prop.bulletsCluster;
        }
        float f = bulletproperties.massa;
        com.maddox.il2.ai.Shot shot = null;
        if(bulletproperties.cumulativePower > 0.0F)
        {
            for(int j = 0; j < gun.prop.bulletsCluster; j++)
            {
                com.maddox.il2.ai.Shot shot1 = com.maddox.il2.ai.MsgShot.send(actor, s, tmpP, vf, f, owner, bulletproperties.cumulativePower, 1, d);
                if(!com.maddox.il2.engine.Actor.isValid(actor))
                    return true;
                if(j == 0)
                    shot = shot1;
            }

            com.maddox.il2.objects.effects.Explosions.generateShot(actor, shot);
        } else
        {
            double d1 = speed.length();
            float f1 = (float)(((double)f * d1 * d1) / 2D);
            for(int k = 0; k < gun.prop.bulletsCluster; k++)
            {
                com.maddox.il2.ai.Shot shot2;
                if(bulletproperties.powerRadius == 0.0F)
                    shot2 = com.maddox.il2.ai.MsgShot.send(actor, s, tmpP, vf, f, owner, f1, bulletproperties.power != 0.0F ? 3 : 2, d);
                else
                    shot2 = com.maddox.il2.ai.MsgShot.send(actor, s, tmpP, vf, f, owner, f1, 0, d);
                if(!com.maddox.il2.engine.Actor.isValid(actor))
                    return true;
                if(k == 0)
                    shot = shot2;
                if(bulletproperties.power > 0.0F && bulletproperties.powerRadius > 0.0F)
                    com.maddox.il2.ai.MsgExplosion.send(actor, s, tmpP, owner, f, bulletproperties.power + 0.03F * f, bulletproperties.powerType, bulletproperties.powerRadius);
            }

            com.maddox.il2.objects.effects.Explosions.generateShot(actor, shot);
            if(bulletproperties.power > 0.0F)
                showExplosion(actor, tmpP, bulletproperties, d);
        }
        return true;
    }

    public Bullet(com.maddox.JGP.Vector3d vector3d, int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d1, long l)
    {
        super(i, gungeneric, loc, vector3d1, l);
        if(owner != null && (owner.equals(com.maddox.il2.ai.World.getPlayerAircraft()) || owner.isContainOwner(com.maddox.il2.ai.World.getPlayerAircraft())) && !com.maddox.il2.ai.World.cur().diffCur.Realistic_Gunnery)
            flags |= 0x40000000;
    }

    protected static com.maddox.JGP.Point3d tmpP = new Point3d();
    protected static com.maddox.JGP.Vector3f vf = new Vector3f();
    private static com.maddox.il2.engine.Loc l1 = new Loc();
    private static com.maddox.il2.engine.Loc l2 = new Loc();
    private static com.maddox.il2.engine.Loc l3 = new Loc();
    private static com.maddox.JGP.Matrix4d m1 = new Matrix4d();
    private static com.maddox.JGP.Matrix4d m2 = new Matrix4d();
    private static int resChunk[] = new int[1];

}
