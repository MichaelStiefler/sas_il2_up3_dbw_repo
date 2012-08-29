// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   GunNull.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.objects.air.TypeGuidedMissileCarrier;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Gun

public class GunNull extends com.maddox.il2.objects.weapons.Gun
{

    public GunNull()
    {
        hasBullets = true;
        ((com.maddox.il2.engine.Actor)this).flags |= 0x4004;
        ((com.maddox.il2.engine.Actor)this).pos = ((com.maddox.il2.engine.ActorPos) (new ActorPosMove(((com.maddox.il2.engine.Actor) (this)))));
    }

    public void emptyGun()
    {
        hasBullets = false;
    }

    public com.maddox.il2.ai.BulletEmitter detach(com.maddox.il2.engine.HierMesh hiermesh, int i)
    {
        return ((com.maddox.il2.ai.BulletEmitter) (this));
    }

    public void initRealisticGunnery(boolean flag1)
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
        if(((com.maddox.il2.engine.Actor)this).getOwner() != null && (((com.maddox.il2.engine.Actor)this).getOwner() instanceof com.maddox.il2.objects.air.TypeGuidedMissileCarrier))
            return ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)((com.maddox.il2.engine.Actor)this).getOwner()).hasMissiles() ? 0x7fffffff : 0;
        else
            return hasBullets ? 0x7fffffff : 0;
    }

    public boolean haveBullets()
    {
        if(((com.maddox.il2.engine.Actor)this).getOwner() != null && (((com.maddox.il2.engine.Actor)this).getOwner() instanceof com.maddox.il2.objects.air.TypeGuidedMissileCarrier))
            return ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)((com.maddox.il2.engine.Actor)this).getOwner()).hasMissiles();
        else
            return hasBullets;
    }

    public void loadBullets()
    {
        com.maddox.il2.ai.EventLog.type("loadBullets");
        hasBullets = true;
    }

    public void loadBullets(int i)
    {
        com.maddox.il2.ai.EventLog.type("loadBullets " + i);
        hasBullets = true;
    }

    public java.lang.Class bulletClass()
    {
        return null;
    }

    public void setBulletClass(java.lang.Class class2)
    {
    }

    public boolean isShots()
    {
        return false;
    }

    public void shots(int j, float f1)
    {
    }

    public void shots(int j)
    {
    }

    public java.lang.String getHookName()
    {
        return "Body";
    }

    public void set(com.maddox.il2.engine.Actor actor1, java.lang.String s1, com.maddox.il2.engine.Loc loc1)
    {
    }

    public void set(com.maddox.il2.engine.Actor actor1, java.lang.String s2, java.lang.String s3)
    {
    }

    public void set(com.maddox.il2.engine.Actor actor1, java.lang.String s1)
    {
    }

    private boolean hasBullets;
}
