// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorSoundListener.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundListener;
import java.util.HashMap;

// Referenced classes of package com.maddox.il2.engine:
//            ActorDraw, ActorSoundListener, Orient, Config, 
//            Engine, BulletGeneric, BulletProperties, Actor, 
//            EffClouds, ActorPos, Landscape

class SoundListenerDraw extends com.maddox.il2.engine.ActorDraw
{
    class BulletInfo
    {

        public com.maddox.sound.SoundFX sound;
        public com.maddox.il2.engine.BulletGeneric bullet;
        public double rho;
        public float km;

        BulletInfo()
        {
        }
    }


    SoundListenerDraw()
    {
        ownerAcoustics = null;
        bInited = false;
        bulletInfo = new com.maddox.il2.engine.BulletInfo[6];
        biFree = new com.maddox.il2.engine.BulletInfo[6];
        biMap = new HashMap();
    }

    public void init()
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            for(int i = 0; i < 6; i++)
            {
                bulletInfo[i] = new BulletInfo();
                bulletInfo[i].sound = new SoundFX("objects.bullet");
                bulletInfo[i].sound.setAcoustics(com.maddox.il2.engine.Engine.worldAcoustics());
                bulletInfo[i].bullet = null;
                bulletInfo[i].rho = 900D;
            }

        }
    }

    private void setBulletPos(com.maddox.il2.engine.BulletInfo bulletinfo)
    {
        com.maddox.JGP.Point3d point3d = bulletinfo.bullet.p1;
        double d = point3d.distance(p);
        if(d > 40D)
        {
            dp.sub(point3d, p);
            dp.scale(40D / d);
            dp.add(p);
            bulletinfo.sound.setPosition(dp.x, dp.y, dp.z);
        } else
        {
            bulletinfo.sound.setPosition(point3d.x, point3d.y, point3d.z);
        }
        bulletinfo.sound.setControl(202, bulletinfo.km);
    }

    private void renderBullets()
    {
        com.maddox.il2.engine.BulletGeneric bulletgeneric = com.maddox.il2.engine.Engine.cur.bulletList;
        int i = 0;
        for(int j = 0; j < 6; j++)
        {
            com.maddox.il2.engine.BulletInfo bulletinfo = bulletInfo[j];
            if(bulletinfo.bullet != null && bulletinfo.bullet.owner == null && bulletinfo.bullet.gun == null)
                bulletinfo.bullet = null;
            if(bulletinfo.bullet == null || !bulletinfo.sound.isPlaying())
            {
                bulletinfo.rho = 900D;
                biFree[i++] = bulletinfo;
                bulletinfo.bullet = null;
            }
            if(bulletinfo.bullet != null)
                biMap.put(bulletinfo.bullet, bulletinfo);
        }

        if(!com.maddox.il2.game.Main3D.cur3D().isViewOutside())
            return;
        if(i > 0)
            for(; bulletgeneric != null; bulletgeneric = bulletgeneric.nextBullet)
                if(bulletgeneric.owner != null || bulletgeneric.gun != null)
                {
                    double d = bulletgeneric.p1.distanceSquared(p);
                    if(d < 900D && !biMap.containsKey(bulletgeneric))
                    {
                        for(int i1 = 0; i1 < i; i1++)
                        {
                            if(biFree[i1].rho <= d)
                                continue;
                            com.maddox.il2.engine.BulletGeneric bulletgeneric1 = biFree[i - 1].bullet;
                            if(bulletgeneric1 != null)
                            {
                                if(biMap.containsKey(bulletgeneric1))
                                    biMap.remove(bulletgeneric1);
                                for(int j1 = i - 1; j1 > i1; j1--)
                                {
                                    biFree[j1].bullet = biFree[j1 - 1].bullet;
                                    biFree[j1].rho = biFree[j1 - 1].rho;
                                }

                            }
                            biFree[i1].rho = d;
                            biFree[i1].bullet = bulletgeneric;
                            biMap.put(bulletgeneric, biFree[i1]);
                            break;
                        }

                    }
                }

        for(int k = 0; k < 6; k++)
        {
            com.maddox.il2.engine.BulletInfo bulletinfo1 = bulletInfo[k];
            if(bulletinfo1.bullet != null)
                setBulletPos(bulletinfo1);
        }

        for(int l = 0; l < i; l++)
        {
            com.maddox.il2.engine.BulletInfo bulletinfo2 = biFree[l];
            if(bulletinfo2.bullet != null)
            {
                float f = bulletinfo2.bullet.properties().massa;
                float f1 = 0.2F;
                if(f > 0.09F)
                {
                    f1 = (float)java.lang.Math.log(f) / (float)java.lang.Math.log(10D) + 1.0F;
                    if(f1 < 0.0F)
                        f1 = 0.0F;
                    else
                    if(f1 > 3F)
                        f1 = 3F;
                    f1 = 4F - f1;
                }
                bulletinfo2.km = f1;
                bulletinfo2.sound.setUsrFlag(f1 > 0.4F ? 1 : 0);
                bulletinfo2.sound.play();
                setBulletPos(bulletinfo2);
            }
            biFree[l] = null;
        }

        biMap.clear();
    }

    public int preRender(com.maddox.il2.engine.Actor actor)
    {
        if(!bInited)
        {
            if(com.maddox.il2.engine.Config.cur.isSoundUse())
            {
                com.maddox.sound.SoundListener.setAcoustics(com.maddox.il2.engine.Engine.worldAcoustics());
            } else
            {
                actor.postDestroy();
                return 0;
            }
            bInited = true;
        }
        boolean flag = false;
        com.maddox.il2.engine.Actor actor1 = actor.actorAcoustics();
        com.maddox.sound.Acoustics acoustics = null;
        if(actor1 != null)
            acoustics = actor1.acoustics;
        else
            acoustics = com.maddox.il2.engine.Engine.worldAcoustics();
        if(ownerAcoustics != acoustics)
        {
            ownerAcoustics = acoustics;
            com.maddox.sound.SoundListener.setAcoustics(acoustics);
            com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
            if(main3d != null && main3d.clouds != null && main3d.clouds.sound != null)
                main3d.clouds.sound.setAcoustics(acoustics);
        }
        if(actor1 != null)
        {
            actor1.pos.getRender(p, o);
            Ahead.set(1.0D, 0.0D, 0.0D);
            Up.set(0.0D, 0.0D, 1.0D);
            o.transform(Ahead);
            o.transform(Up);
            acoustics.setOrientation((float)Ahead.x, (float)Ahead.y, (float)Ahead.z, (float)Up.x, (float)Up.y, (float)Up.z);
            flag = true;
        }
        actor.pos.getRender(p, o);
        double d = 0.0D;
        d = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y);
        com.maddox.il2.engine.Engine.worldAcoustics().setPosition(p.x, p.y, d);
        Ahead.set(1.0D, 0.0D, 0.0D);
        Up.set(0.0D, 0.0D, 1.0D);
        o.transform(Ahead);
        o.transform(Up);
        ((com.maddox.il2.engine.ActorSoundListener)actor).absPos.set(p);
        com.maddox.sound.SoundListener.setOrientation((float)Ahead.x, (float)Ahead.y, (float)Ahead.z, (float)Up.x, (float)Up.y, (float)Up.z);
        com.maddox.sound.SoundListener.setPosition(p.x, p.y, p.z);
        if(flag)
            acoustics.setPosition(p.x, p.y, p.z);
        com.maddox.il2.engine.Config _tmp = com.maddox.il2.engine.Config.cur;
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.game.Main3D.cur3D().clouds != null)
            com.maddox.il2.game.Main3D.cur3D().clouds.soundUpdate(p.z);
        if(((com.maddox.il2.engine.ActorSoundListener)actor).isUseBaseSpeed())
        {
            com.maddox.il2.engine.Actor actor2 = actor.pos.base();
            if(actor2 != null)
                actor2 = actor2.pos.base();
            if(actor2 == null)
                actor.getSpeed(v);
            else
                actor2.getSpeed(v);
        } else
        {
            actor.getSpeed(v);
        }
        com.maddox.sound.SoundListener.setVelocity((float)v.x, (float)v.y, (float)v.z);
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && !com.maddox.rts.Time.isPaused())
            renderBullets();
        return 0;
    }

    public void render(com.maddox.il2.engine.Actor actor)
    {
    }

    public void destroy()
    {
    }

    private com.maddox.sound.Acoustics ownerAcoustics;
    private static com.maddox.JGP.Vector3d Ahead = new Vector3d();
    private static com.maddox.JGP.Vector3d Up = new Vector3d();
    private boolean bInited;
    private static final int numBullets = 6;
    public static final double maxBulletDist = 30D;
    private static final double maxBulletDist2 = 900D;
    private com.maddox.il2.engine.BulletInfo bulletInfo[];
    private com.maddox.il2.engine.BulletInfo biFree[];
    private java.util.HashMap biMap;
    private final double bsRho = 0.0D;
    private final double rMax = 40D;
    public static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3d v = new Vector3d();
    private static com.maddox.JGP.Point3d dp = new Point3d();

}
