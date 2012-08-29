// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   MGunNullGeneric.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            GunNull, Gun, BulletAircraftGeneric, Bullet

public class MGunNullGeneric extends com.maddox.il2.objects.weapons.GunNull
    implements com.maddox.il2.ai.BulletEmitter, com.maddox.il2.ai.BulletAimer
{

    public MGunNullGeneric()
    {
        _index = -1;
    }

    public java.lang.String getDayProperties(java.lang.String s)
    {
        return null;
    }

    public void createdProperties()
    {
        ((com.maddox.il2.engine.GunGeneric)this).createdProperties();
    }

    public java.lang.String prop_fireMesh()
    {
        return null;
    }

    public java.lang.String prop_fire()
    {
        return null;
    }

    public java.lang.String prop_sprite()
    {
        return null;
    }

    public void setConvDistance(float f2, float f3)
    {
    }

    public void init()
    {
        int i = ((com.maddox.il2.engine.GunGeneric)this).prop.bullet.length;
        ((com.maddox.il2.engine.GunGeneric)this).bulletAG = new float[i];
        ((com.maddox.il2.engine.GunGeneric)this).bulletKV = new float[i];
        ((com.maddox.il2.objects.weapons.Gun)this).initRealisticGunnery();
    }

    public void initRealisticGunnery(boolean flag)
    {
        int i = ((com.maddox.il2.engine.GunGeneric)this).prop.bullet.length;
        for(int j = 0; j < i; j++)
        {
            ((com.maddox.il2.engine.GunGeneric)this).bulletAG[j] = 0.0F;
            ((com.maddox.il2.engine.GunGeneric)this).bulletKV[j] = 0.0F;
        }

    }

    public com.maddox.il2.objects.weapons.Bullet createNextBullet(com.maddox.JGP.Vector3d vector3d, int i, com.maddox.il2.engine.GunGeneric gungeneric, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d1, long l)
    {
        bullet = ((com.maddox.il2.objects.weapons.Bullet) (new BulletAircraftGeneric(vector3d, i, gungeneric, loc, vector3d1, l)));
        if(!com.maddox.il2.ai.World.cur().diffCur.Realistic_Gunnery)
            if(((com.maddox.il2.engine.Actor)this).isContainOwner(((java.lang.Object) (com.maddox.il2.ai.World.getPlayerAircraft()))));
        return bullet;
    }

    public void loadBullets(int i)
    {
        super.loadBullets(i);
        resetGuard();
    }

    public void _loadBullets(int i)
    {
        ((com.maddox.il2.engine.GunGeneric)this)._loadBullets(i);
        resetGuard();
    }

    private void resetGuard()
    {
        guardBullet = null;
    }

    public int nextIndexBulletType()
    {
        _index++;
        if(_index == ((com.maddox.il2.engine.GunGeneric)this).prop.bullet.length)
            _index = 0;
        return _index;
    }

    private static final boolean DEBUG = false;
    protected com.maddox.il2.objects.weapons.BulletAircraftGeneric guardBullet;
    private static com.maddox.il2.objects.weapons.Bullet bullet;
    private int _index;
}
