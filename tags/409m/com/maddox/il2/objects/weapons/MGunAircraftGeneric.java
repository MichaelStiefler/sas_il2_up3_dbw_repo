// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MGunAircraftGeneric.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletGeneric;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Gun, BulletAircraftGeneric, Bullet

public class MGunAircraftGeneric extends com.maddox.il2.objects.weapons.Gun
    implements com.maddox.il2.ai.BulletEmitter, com.maddox.il2.ai.BulletAimer
{

    public MGunAircraftGeneric()
    {
        _index = -1;
    }

    public java.lang.String getDayProperties(java.lang.String s)
    {
        if(s == null)
            return null;
        java.lang.String s1 = "3DO/Effects/GunFire/";
        if(s.regionMatches(true, 0, s1, 0, s1.length()))
            return "3DO/Effects/GunFireDay/" + s.substring(s1.length());
        else
            return s;
    }

    public void createdProperties()
    {
        if(prop.fireMesh != null && prop.fireMeshDay == null)
            prop.fireMeshDay = getDayProperties(prop.fireMesh);
        if(prop.fire != null && prop.fireDay == null)
            prop.fireDay = getDayProperties(prop.fire);
        if(prop.sprite != null && prop.spriteDay == null)
            prop.spriteDay = getDayProperties(prop.sprite);
        super.createdProperties();
    }

    public java.lang.String prop_fireMesh()
    {
        if(com.maddox.il2.ai.World.Sun().ToSun.z >= -0.22F)
            return prop.fireMeshDay;
        else
            return prop.fireMesh;
    }

    public java.lang.String prop_fire()
    {
        if(com.maddox.il2.ai.World.Sun().ToSun.z >= -0.22F)
            return prop.fireDay;
        else
            return prop.fire;
    }

    public java.lang.String prop_sprite()
    {
        if(com.maddox.il2.ai.World.Sun().ToSun.z >= -0.22F)
            return prop.spriteDay;
        else
            return prop.sprite;
    }

    public void setConvDistance(float f, float f1)
    {
        com.maddox.JGP.Point3d point3d = pos.getRelPoint();
        com.maddox.il2.engine.Orient orient = new Orient();
        orient.set(pos.getRelOrient());
        float f2 = (float)java.lang.Math.sqrt(point3d.y * point3d.y + (double)(f * f));
        float f3 = (float)java.lang.Math.toDegrees(java.lang.Math.atan(-point3d.y / (double)f));
        if(!prop.bUseHookAsRel)
        {
            f3 = 0.0F;
            f2 = f;
        }
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = prop.bullet[0].speed;
        float f7 = 0.0F;
        while(f4 < f2) 
        {
            f6 += (bulletKV[0] * com.maddox.rts.Time.tickConstLenFs() * 1.0F * com.maddox.il2.objects.weapons.BulletAircraftGeneric.fv(f6)) / f6;
            f7 -= bulletAG[0] * com.maddox.rts.Time.tickConstLenFs();
            f4 += f6 * com.maddox.rts.Time.tickConstLenFs();
            f5 += f7 * com.maddox.rts.Time.tickConstLenFs();
        }
        f5 += f1;
        f5 = (float)((double)f5 - point3d.z);
        float f8 = (float)java.lang.Math.toDegrees(java.lang.Math.atan(f5 / f2));
        orient.setYPR(orient.azimut() + f3, orient.tangage() + f8, orient.kren());
        pos.setRel(orient);
    }

    public void init()
    {
        int i = prop.bullet.length;
        bulletAG = new float[i];
        bulletKV = new float[i];
        initRealisticGunnery();
    }

    public void initRealisticGunnery(boolean flag)
    {
        int i = prop.bullet.length;
        for(int j = 0; j < i; j++)
            if(flag)
            {
                bulletAG[j] = -10F;
                bulletKV[j] = -((1000F * prop.bullet[j].kalibr) / prop.bullet[j].massa);
            } else
            {
                bulletAG[j] = 0.0F;
                bulletKV[j] = 0.0F;
            }

    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, long l)
    {
        bullet = new BulletAircraftGeneric(i, gungeneric, loc, vector3d, l);
        if(!com.maddox.il2.ai.World.cur().diffCur.Realistic_Gunnery)
            if(!isContainOwner(com.maddox.il2.ai.World.getPlayerAircraft()));
        return bullet;
    }

    public void loadBullets(int i)
    {
        super.loadBullets(i);
        resetGuard();
    }

    public void _loadBullets(int i)
    {
        super._loadBullets(i);
        resetGuard();
    }

    private void resetGuard()
    {
        guardBullet = null;
        for(com.maddox.il2.engine.BulletGeneric bulletgeneric = com.maddox.il2.engine.Engine.cur.bulletList; bulletgeneric != null; bulletgeneric = bulletgeneric.nextBullet)
            if((bulletgeneric instanceof com.maddox.il2.objects.weapons.BulletAircraftGeneric) && bulletgeneric.gun() == this)
                ((com.maddox.il2.objects.weapons.BulletAircraftGeneric)bulletgeneric).bulletss = bulletgeneric.hashCode();

    }

    public int nextIndexBulletType()
    {
        _index++;
        if(_index == prop.bullet.length)
            _index = 0;
        return _index;
    }

    private static final boolean DEBUG = false;
    protected com.maddox.il2.objects.weapons.BulletAircraftGeneric guardBullet;
    private static com.maddox.il2.objects.weapons.Bullet bullet;
    private int _index;
}
