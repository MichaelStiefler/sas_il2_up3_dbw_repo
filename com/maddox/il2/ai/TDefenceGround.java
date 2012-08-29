// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Target.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosStatic;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;

// Referenced classes of package com.maddox.il2.ai:
//            Target, World

class TDefenceGround extends com.maddox.il2.ai.Target
{

    protected boolean checkActorDied(com.maddox.il2.engine.Actor actor)
    {
        if(actor.pos == null)
            return false;
        actor.pos.getAbs(p);
        p.z = pos.getAbsPoint().z;
        if(pos.getAbsPoint().distance(p) <= r)
        {
            if(!com.maddox.il2.ai.TDefenceGround.isStaticActor(actor))
                return false;
            countActors--;
            if(countActors <= 0)
            {
                setDiedFlag(true);
                return true;
            }
        }
        return false;
    }

    protected boolean checkTimeoutOff()
    {
        setTaskCompleteFlag(true);
        setDiedFlag(true);
        return true;
    }

    public TDefenceGround(int i, int j, int k, int l, int i1, int j1)
    {
        super(i, j);
        countActors = 0;
        r = i1;
        destructLevel = j1;
        com.maddox.il2.ai.World.land();
        pos = new ActorPosStatic(this, new Point3d(k, l, com.maddox.il2.engine.Landscape.HQ(k, l)), new Orient());
        countActors = com.maddox.il2.ai.TDefenceGround.countStaticActors(pos.getAbsPoint(), r);
        if(countActors == 0)
        {
            setTaskCompleteFlag(true);
        } else
        {
            countActors = java.lang.Math.round((float)(countActors * destructLevel) / 100F);
            if(countActors == 0)
                countActors = 1;
        }
    }

    public boolean zutiIsOverTarged(double d, double d1)
    {
        double d2 = r * r;
        double d3 = (d - p.x) * (d - p.x) + (d1 - p.y) * (d1 - p.y);
        return d3 < d2;
    }

    double r;
    int countActors;
    int destructLevel;
    public static com.maddox.JGP.Point3d p = new Point3d();

}
