// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TorpedoApparatus.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletAimer;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Gun, Bomb

public abstract class TorpedoApparatus extends com.maddox.il2.objects.weapons.Gun
    implements com.maddox.il2.ai.BulletAimer
{

    public void loadBullets(int i)
    {
        bullets(i);
    }

    public void setBulletClass(java.lang.Class class1)
    {
        bulletClass = class1;
    }

    public void doStartBullet(double d)
    {
        newBomb();
        if(bomb == null)
            return;
        bomb.pos.setUpdateEnable(true);
        bomb.pos.resetAsBase();
        if(bomb.pos.getAbsPoint().z > 0.0D)
        {
            com.maddox.il2.engine.Eff3DActor.New(getOwner(), hook, null, 1.0F, "3DO/Effects/Fireworks/20_SmokeBoiling.eff", -1F);
            com.maddox.il2.engine.Eff3DActor.New(getOwner(), hook, null, 1.0F, "3DO/Effects/Fireworks/20_SparksP.eff", -1F);
        }
        bomb.start();
    }

    public void shots(int i)
    {
        doStartBullet(0.0D);
    }

    public void doEffects(boolean flag)
    {
    }

    private void newBomb()
    {
        try
        {
            bomb = (com.maddox.il2.objects.weapons.Bomb)bulletClass.newInstance();
            bomb.pos.setBase(getOwner(), hook, false);
            bomb.pos.resetAsBase();
            bomb.pos.setUpdateEnable(false);
        }
        catch(java.lang.Exception exception) { }
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
        setOwner(actor);
        java.lang.Class class1 = getClass();
        bulletClass = (java.lang.Class)com.maddox.rts.Property.value(class1, "bulletClass", null);
        setBulletClass(bulletClass);
        hook = (com.maddox.il2.engine.HookNamed)actor.findHook(s);
        actor.interpPut(interpolater, null, -1L, null);
    }

    public float TravelTime(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        float f = (float)point3d.distance(point3d1);
        java.lang.Class class1 = getClass();
        java.lang.Class class2 = (java.lang.Class)com.maddox.rts.Property.value(class1, "bulletClass", null);
        float f1 = com.maddox.rts.Property.floatValue(class2, "velocity", 1.0F);
        float f2 = com.maddox.rts.Property.floatValue(class2, "traveltime", 1.0F);
        if(f > f1 * f2)
            return -1F;
        else
            return f / f1;
    }

    public boolean FireDirection(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Vector3d vector3d)
    {
        float f = (float)point3d.distance(point3d1);
        java.lang.Class class1 = getClass();
        java.lang.Class class2 = (java.lang.Class)com.maddox.rts.Property.value(class1, "bulletClass", null);
        float f1 = com.maddox.rts.Property.floatValue(class2, "velocity", 1.0F);
        float f2 = com.maddox.rts.Property.floatValue(class2, "traveltime", 1.0F);
        if(f > f1 * f2)
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

    public com.maddox.il2.engine.GunProperties createProperties()
    {
        com.maddox.il2.engine.GunProperties gunproperties = new GunProperties();
        gunproperties.weaponType = 16;
        return gunproperties;
    }

    public TorpedoApparatus()
    {
        bulletClass = null;
    }

    protected com.maddox.il2.objects.weapons.Bomb bomb;
    protected com.maddox.il2.engine.HookNamed hook;
    protected java.lang.Class bulletClass;
}
