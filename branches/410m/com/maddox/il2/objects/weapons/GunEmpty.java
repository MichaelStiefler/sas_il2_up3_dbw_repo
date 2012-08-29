// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GunEmpty.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Gun

public class GunEmpty extends com.maddox.il2.objects.weapons.Gun
{

    public com.maddox.il2.ai.BulletEmitter detach(com.maddox.il2.engine.HierMesh hiermesh, int i)
    {
        return this;
    }

    public void initRealisticGunnery(boolean flag)
    {
    }

    public boolean isEnablePause()
    {
        return false;
    }

    public float bulletMassa()
    {
        return 0.0F;
    }

    public float bulletSpeed()
    {
        return 0.0F;
    }

    public int countBullets()
    {
        return 0;
    }

    public boolean haveBullets()
    {
        return false;
    }

    public void loadBullets()
    {
    }

    public void loadBullets(int i)
    {
    }

    public java.lang.Class bulletClass()
    {
        return null;
    }

    public void setBulletClass(java.lang.Class class1)
    {
    }

    public boolean isShots()
    {
        return false;
    }

    public void shots(int i, float f)
    {
    }

    public void shots(int i)
    {
    }

    public java.lang.String getHookName()
    {
        return "Body";
    }

    public void set(com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.il2.engine.Loc loc)
    {
    }

    public void set(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
    }

    public void set(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
    }

    private GunEmpty()
    {
        flags |= 0x4004;
        pos = new ActorPosMove(this);
    }

    public static com.maddox.il2.objects.weapons.GunEmpty get()
    {
        return empty;
    }

    private static com.maddox.il2.objects.weapons.GunEmpty empty = new GunEmpty();

}
