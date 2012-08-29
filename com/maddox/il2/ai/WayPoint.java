// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   WayPoint.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.objects.bridges.Bridge;

// Referenced classes of package com.maddox.il2.ai:
//            Chief, World, RangeRandom

public class WayPoint
{

    public WayPoint()
    {
        p = new Point3d();
        bHadTarget = false;
        bTargetFinished = false;
        bRadioSilence = false;
    }

    public WayPoint(com.maddox.JGP.Point3f point3f)
    {
        this(point3f.x, point3f.y, point3f.z);
    }

    public WayPoint(com.maddox.JGP.Point3d point3d)
    {
        this((float)point3d.x, (float)point3d.y, (float)point3d.z);
    }

    public WayPoint(float f, float f1, float f2)
    {
        p = new Point3d();
        bHadTarget = false;
        bTargetFinished = false;
        bRadioSilence = false;
        set(f, f1, f2, 83F);
    }

    public WayPoint(float f, float f1, float f2, float f3)
    {
        p = new Point3d();
        bHadTarget = false;
        bTargetFinished = false;
        bRadioSilence = false;
        set(f, f1, f2, f3);
    }

    public float x()
    {
        return (float)p.x;
    }

    public float y()
    {
        return (float)p.y;
    }

    public float z()
    {
        return (float)p.z;
    }

    public void setTarget(java.lang.String s)
    {
        if(s == null)
            bHadTarget = false;
        else
            bHadTarget = true;
        sTarget = s;
        target = null;
    }

    public boolean isTargetFinished()
    {
        getTarget();
        return bTargetFinished;
    }

    public java.lang.String getTargetName()
    {
        return sTarget;
    }

    public com.maddox.il2.engine.Actor getTarget()
    {
        if(sTarget != null)
        {
            com.maddox.il2.engine.Actor actor = com.maddox.il2.engine.Actor.getByName(sTarget);
            if(actor != null)
            {
                target = actor;
                sTarget = null;
            }
        }
        if(target == null || target != null && !target.isAlive())
        {
            bTargetFinished = true;
            target = null;
        }
        if(!bHadTarget)
            bTargetFinished = false;
        return target;
    }

    public com.maddox.il2.engine.Actor getTargetActorRandom()
    {
        com.maddox.il2.engine.Actor actor = getTarget();
        if(!com.maddox.il2.engine.Actor.isValid(actor) || !actor.isAlive())
            return null;
        if((actor instanceof com.maddox.il2.ai.Chief) || (actor instanceof com.maddox.il2.objects.bridges.Bridge))
        {
            int i = actor.getOwnerAttachedCount();
            if(i < 1)
                return null;
            for(int j = 0; j < i; j++)
            {
                com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)actor.getOwnerAttached(com.maddox.il2.ai.World.Rnd().nextInt(0, i - 1));
                if(com.maddox.il2.engine.Actor.isValid(actor1) && actor1.isAlive())
                    return actor1;
            }

            for(int k = 0; k < i; k++)
            {
                com.maddox.il2.engine.Actor actor2 = (com.maddox.il2.engine.Actor)actor.getOwnerAttached(k);
                if(com.maddox.il2.engine.Actor.isValid(actor2) && actor2.isAlive())
                    return actor2;
            }

            return null;
        } else
        {
            return actor;
        }
    }

    public void set(float f, float f1, float f2)
    {
        p.set(f, f1, f2);
    }

    public void set(com.maddox.JGP.Point3f point3f)
    {
        p.set(point3f);
    }

    public void set(com.maddox.JGP.Point3d point3d)
    {
        p.set(point3d);
    }

    public void set(float f, float f1, float f2, float f3)
    {
        p.set(f, f1, f2);
        Speed = f3;
    }

    public void set(float f)
    {
        Speed = f;
    }

    public void setTimeout(int i)
    {
        timeout = i;
    }

    public void set(com.maddox.il2.ai.WayPoint waypoint)
    {
        set(waypoint.getP());
        set(waypoint.getV());
        sTarget = waypoint.sTarget;
        target = waypoint.target;
        Action = waypoint.Action;
        timeout = waypoint.timeout;
        bTargetFinished = waypoint.bTargetFinished;
        bHadTarget = waypoint.bHadTarget;
        bRadioSilence = waypoint.bRadioSilence;
    }

    public com.maddox.JGP.Point3d getP()
    {
        return p;
    }

    public void getP(com.maddox.JGP.Point3f point3f)
    {
        point3f.set(p);
    }

    public void getP(com.maddox.JGP.Point3d point3d)
    {
        point3d.set(p);
    }

    public float getV()
    {
        return Speed;
    }

    public static com.maddox.JGP.Vector3f vector(com.maddox.il2.ai.WayPoint waypoint, com.maddox.il2.ai.WayPoint waypoint1)
    {
        V1.x = (float)(waypoint1.p.x - waypoint.p.x);
        V1.y = (float)(waypoint1.p.y - waypoint.p.y);
        V1.z = (float)(waypoint1.p.z - waypoint.p.z);
        return V1;
    }

    public boolean isRadioSilence()
    {
        return bRadioSilence;
    }

    public static final int NORMFLY = 0;
    public static final int TAKEOFF = 1;
    public static final int LANDING = 2;
    public static final int GATTACK = 3;
    private com.maddox.JGP.Point3d p;
    public float Speed;
    public int Action;
    public int timeout;
    private boolean bHadTarget;
    protected java.lang.String sTarget;
    protected com.maddox.il2.engine.Actor target;
    protected boolean bTargetFinished;
    protected boolean bRadioSilence;
    private static com.maddox.JGP.Vector3f V1 = new Vector3f();

}
