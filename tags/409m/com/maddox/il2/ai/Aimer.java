// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Aimer.java

package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;

// Referenced classes of package com.maddox.il2.ai:
//            BulletAimer

public class Aimer
{

    public Aimer()
    {
    }

    private static final boolean DT(float f, com.maddox.il2.ai.BulletAimer bulletaimer, com.maddox.il2.engine.Actor actor)
    {
        actor.futurePosition(f, targPos);
        targPos.add(targShotOffs);
        float f1 = bulletaimer.TravelTime(hunterPos, targPos);
        if(f1 < 0.0F)
        {
            return false;
        } else
        {
            DTresult = (idleT + f1) - f;
            return true;
        }
    }

    public static final boolean Aim(com.maddox.il2.ai.BulletAimer bulletaimer, com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1, float f, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        if(f < 0.0F)
            return false;
        idleT = f;
        if(point3d == null)
            targShotOffs.set(0.0D, 0.0D, 0.0D);
        else
            actor.pos.getAbsOrient().transform(point3d, targShotOffs);
        actor1.pos.getAbs(hunterLoc);
        actor1.futurePosition(idleT, hunterLoc.getPoint());
        if(point3d1 == null)
            hunterPos.set(hunterLoc.getPoint());
        else
            hunterLoc.transform(point3d1, hunterPos);
        float f1 = idleT;
        if(!com.maddox.il2.ai.Aimer.DT(f1, bulletaimer, actor))
            return false;
        float f3 = DTresult;
        if(f3 < 0.001F)
            return true;
        float f2 = f1 + f3;
        if(!com.maddox.il2.ai.Aimer.DT(f2, bulletaimer, actor))
        {
            f2 = f1 + f3 * 0.5F;
            if(!com.maddox.il2.ai.Aimer.DT(f2, bulletaimer, actor))
                return false;
        }
        float f4 = DTresult;
        if(f4 > 0.0F)
        {
            if(com.maddox.il2.ai.Aimer.DT((f1 + f2) / 2.0F, bulletaimer, actor) && DTresult <= 0.0F)
            {
                f2 = (f1 + f2) / 2.0F;
            } else
            {
                if(f2 - f1 < 0.0001F)
                    f2 = f1 + 0.3F;
                else
                if(java.lang.Math.abs(f4 - f3) < 0.0001F)
                {
                    f2 = f1 + 2.0F * (f2 - f1);
                } else
                {
                    f2 -= (f4 * (f2 - f1)) / (f4 - f3);
                    if(f2 <= f1)
                        f2 = f1 + 2.0F * (f2 - f1);
                }
                boolean flag = com.maddox.il2.ai.Aimer.DT(f2, bulletaimer, actor);
                if(!flag || DTresult > 0.0F)
                {
                    if(!flag)
                        return false;
                    f2 += DTresult * 2.0F;
                    flag = com.maddox.il2.ai.Aimer.DT(f2, bulletaimer, actor);
                    if(!flag || DTresult > 0.0F)
                        return false;
                }
            }
            f4 = DTresult;
        }
        float f5 = 0.0F;
        for(int i = 5; i > 0; i--)
        {
            if(f4 > -0.001F || f2 - f1 < 0.001F)
                return true;
            if(f3 - f4 < 0.0001F)
            {
                f5 = (f1 + f2) / 2.0F;
            } else
            {
                f5 = f2 - (f4 * (f2 - f1)) / (f4 - f3);
                if(f5 <= f1 || f5 >= f2)
                    f5 = (f1 + f2) / 2.0F;
            }
            if(!com.maddox.il2.ai.Aimer.DT(f5, bulletaimer, actor))
                return false;
            if(DTresult <= 0.0F)
            {
                f2 = f5;
                f4 = DTresult;
            } else
            {
                f1 = f5;
                f3 = DTresult;
            }
        }

        if(!com.maddox.il2.ai.Aimer.DT((f1 + f2) / 2.0F, bulletaimer, actor))
            return com.maddox.il2.ai.Aimer.DT(f5, bulletaimer, actor);
        else
            return true;
    }

    public static final void GetPredictedTargetPosition(com.maddox.JGP.Point3d point3d)
    {
        point3d.set(targPos);
    }

    public static final com.maddox.JGP.Point3d GetHunterFirePoint()
    {
        return hunterPos;
    }

    private static float idleT;
    private static com.maddox.JGP.Point3d hunterPos = new Point3d();
    private static float DTresult;
    private static com.maddox.JGP.Point3d targPos = new Point3d();
    private static com.maddox.JGP.Point3d targShotOffs = new Point3d();
    private static com.maddox.il2.engine.Loc hunterLoc = new Loc();

}
