// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HookViewFly.java

package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.Render;
import com.maddox.rts.IniFile;

public class HookViewFly extends com.maddox.il2.engine.Hook
{

    private boolean computePos(com.maddox.il2.engine.Actor actor)
    {
        double d = actor.getSpeed(vect);
        if(d < 0.0099999997764825821D)
        {
            if(actor.pos == null)
                return false;
            com.maddox.il2.engine.Loc loc = actor.pos.getAbs();
            vect.set(1.0D, 0.0D, 0.0D);
            loc.transform(vect);
            d = 20D;
        }
        vect.normalize();
        vect.scaleAdd(d * (double)timeFirstStep, pAbs);
        p0.set(vect);
        float f = oAbs.getTangage();
        if(f > 0.0F)
            p0.z -= deltaZ;
        else
            p0.z += deltaZ;
        if(java.lang.Math.abs(p0.x - pAbs.x) > java.lang.Math.abs(p0.y - pAbs.y))
        {
            p0.y += deltaZ;
            p0.x += deltaZ / 4F;
        } else
        {
            p0.x += deltaZ;
            p0.y += deltaZ / 4F;
        }
        double d1 = com.maddox.il2.engine.Engine.land().HQ_Air(p0.x, p0.y) + 25D;
        if(p0.z < d1)
            p0.z = d1;
        double d2 = pAbs.distance(p0);
        maxLen = 2D * d2;
        if(d2 < 10D)
            maxLen = 20D;
        if(com.maddox.il2.engine.Actor.isValid(camera))
        {
            camera.pos.inValidate(true);
            camera.pos.resetAsBase();
        }
        return true;
    }

    public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        if(bUse)
        {
            loc.get(pAbs, oAbs);
            if(!bView)
                bView = computePos(actor);
            if(pAbs.distance(p0) > maxLen && com.maddox.il2.engine.Render.current() == null)
                bView = computePos(actor);
            if(!bView)
                return;
            v.set(pAbs);
            v.sub(p0);
            o.setAT0(v);
            loc1.set(p0, o);
        }
    }

    public boolean use(boolean flag)
    {
        boolean flag1 = bUse;
        bUse = flag;
        bView = false;
        return flag1;
    }

    public void reset()
    {
        bView = false;
    }

    public void setCamera(com.maddox.il2.engine.Actor actor)
    {
        camera = actor;
    }

    public HookViewFly(java.lang.String s)
    {
        timeFirstStep = 2.0F;
        deltaZ = 10F;
        p0 = new Point3d();
        pAbs = new Point3d();
        oAbs = new Orient();
        vect = new Vector3d();
        bUse = false;
        bView = false;
        o = new Orientation();
        v = new Vector3d();
        java.lang.String s1 = s + " Config";
        timeFirstStep = com.maddox.il2.engine.Config.cur.ini.get(s1, "timeFirstStep", timeFirstStep);
        deltaZ = com.maddox.il2.engine.Config.cur.ini.get(s1, "deltaZ", deltaZ);
        current = this;
    }

    private float timeFirstStep;
    private float deltaZ;
    private double maxLen;
    private com.maddox.JGP.Point3d p0;
    private com.maddox.JGP.Point3d pAbs;
    private com.maddox.il2.engine.Orient oAbs;
    private com.maddox.JGP.Vector3d vect;
    private boolean bUse;
    private boolean bView;
    private com.maddox.il2.engine.Actor camera;
    private com.maddox.il2.engine.Orient o;
    private com.maddox.JGP.Vector3d v;
    public static com.maddox.il2.engine.hotkey.HookViewFly current = null;

}
