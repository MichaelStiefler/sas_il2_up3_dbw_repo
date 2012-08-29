// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   StaticObstacle.java

package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;

// Referenced classes of package com.maddox.il2.ai.ground:
//            Obstacle, UnitData, ChiefGround

public class StaticObstacle
{

    public StaticObstacle()
    {
        clear();
    }

    public void clear()
    {
        obj = null;
        nCollisions = 0;
    }

    public boolean isActive()
    {
        return obj != null;
    }

    public boolean updateState()
    {
        if(!com.maddox.il2.engine.Actor.isValid(obj))
            clear();
        return isActive();
    }

    public void collision(com.maddox.il2.engine.Actor actor, com.maddox.il2.ai.ground.ChiefGround chiefground, com.maddox.il2.ai.ground.UnitData unitdata)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return;
        if(!(actor instanceof com.maddox.il2.ai.ground.Obstacle))
            return;
        if(!((com.maddox.il2.ai.ground.Obstacle)actor).unmovableInFuture())
            return;
        if(updateState())
        {
            if(obj == actor)
            {
                nCollisions++;
                if(nCollisions > 3)
                {
                    ((com.maddox.il2.ai.ground.Obstacle)actor).collisionDeath();
                    clear();
                }
                return;
            }
            int i = unitdata.segmentIdx;
            double d = chiefground.computePosAlong(i, actor.pos.getAbsPoint());
            if(i < segIdx || i == segIdx && d <= along)
            {
                nCollisions++;
                if(nCollisions > 3)
                {
                    ((com.maddox.il2.ai.ground.Obstacle)actor).collisionDeath();
                    clear();
                }
                return;
            }
        }
        nCollisions = 1;
        obj = actor;
        pos = new Point3d(actor.pos.getAbsPoint());
        segIdx = unitdata.segmentIdx;
        along = chiefground.computePosAlong(segIdx, pos);
        side = chiefground.computePosSide(segIdx, pos);
        R = actor.collisionR();
        if(R <= 0.0F)
            R = 0.0F;
    }

    private static final int MAX_COLLISIONS = 3;
    private com.maddox.il2.engine.Actor obj;
    com.maddox.JGP.Point3d pos;
    float R;
    int segIdx;
    double along;
    double side;
    private int nCollisions;
}
