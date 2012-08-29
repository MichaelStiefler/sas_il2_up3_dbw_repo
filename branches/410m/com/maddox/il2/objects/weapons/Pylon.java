// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Pylon.java

package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.MeshShared;
import com.maddox.rts.Message;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            GunEmpty

public class Pylon extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.ai.BulletEmitter
{

    public com.maddox.il2.ai.BulletEmitter detach(com.maddox.il2.engine.HierMesh hiermesh, int i)
    {
        if(isDestroyed())
            return com.maddox.il2.objects.weapons.GunEmpty.get();
        if(i == -1 || i == chunkIndx)
        {
            destroy();
            return com.maddox.il2.objects.weapons.GunEmpty.get();
        } else
        {
            return this;
        }
    }

    public boolean isEnablePause()
    {
        return false;
    }

    public boolean isPause()
    {
        return false;
    }

    public void setPause(boolean flag)
    {
    }

    public float bulletMassa()
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

    public void _loadBullets(int i)
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
        return hookName;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Pylon()
    {
        setMesh(com.maddox.il2.engine.MeshShared.get(com.maddox.rts.Property.stringValue(getClass(), "mesh", null)));
        collide(false);
        drawing(true);
    }

    public void set(com.maddox.il2.engine.Actor actor, java.lang.String s, com.maddox.il2.engine.Loc loc)
    {
        set(actor, s);
    }

    public void set(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        set(actor, s);
    }

    public void set(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        hookName = s;
        setOwner(actor);
        if(s != null)
        {
            com.maddox.il2.engine.Hook hook = actor.findHook(s);
            pos.setBase(actor, hook, false);
            pos.changeHookToRel();
            chunkIndx = hook.chunkNum();
        } else
        {
            pos.setBase(actor, null, false);
            chunkIndx = -1;
        }
        visibilityAsBase(true);
        pos.setUpdateEnable(false);
        pos.reset();
    }

    private java.lang.String hookName;
    private int chunkIndx;
}
