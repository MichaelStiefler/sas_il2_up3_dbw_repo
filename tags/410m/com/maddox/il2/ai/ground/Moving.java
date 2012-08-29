// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Moving.java

package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AnglesFork;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.ai.ground:
//            UnitInterface, UnitMove

public class Moving
{

    private static final long SecsToTicks(float f)
    {
        long l = (long)(0.5D + (double)(f / com.maddox.rts.Time.tickLenFs()));
        return l >= 1L ? l : 1L;
    }

    public boolean IsLandAligned()
    {
        return normal.z < 0.0F;
    }

    public static final float distance2D(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        return (float)java.lang.Math.sqrt((point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y));
    }

    public Moving()
    {
        moveCurTime = rotatCurTime = -1L;
        srcPos = new Point3d();
        dstPos = new Point3d();
        normal = null;
        angles = new AnglesFork();
        rotatingInPlace = false;
        movingForward = false;
    }

    public void switchToStay(float f)
    {
        dstPos = null;
        moveTotTime = moveCurTime = com.maddox.il2.ai.ground.Moving.SecsToTicks(f);
        rotatCurTime = -1L;
        rotatingInPlace = false;
        movingForward = false;
    }

    public void switchToAsk()
    {
        switchToStay(0.0F);
        moveCurTime = rotatCurTime = -1L;
    }

    public void set(com.maddox.il2.ai.ground.UnitMove unitmove, com.maddox.il2.engine.Actor actor, float f, float f1, float f2, float f3)
    {
        dstPos = new Point3d();
        normal = new Vector3f();
        normal.set(unitmove.normal);
        srcPos.set(actor.pos.getAbsPoint());
        moveTotTime = moveCurTime = com.maddox.il2.ai.ground.Moving.SecsToTicks(unitmove.totalTime);
        if(unitmove.pos == null)
        {
            switchToStay(unitmove.totalTime);
            return;
        }
        dstPos.set(unitmove.pos);
        movingForward = true;
        angles.setDeg(actor.pos.getAbsOrient().getYaw());
        double d = dstPos.x - srcPos.x;
        double d1 = dstPos.y - srcPos.y;
        if(java.lang.Math.abs(d) + java.lang.Math.abs(d1) > 1.0000000000000001E-005D)
            angles.setDstRad((float)java.lang.Math.atan2(d1, d));
        boolean flag = false;
        if(f1 > 0.0F && angles.getAbsDiffDeg() > 90F)
        {
            flag = true;
            angles.reverseDst();
            movingForward = false;
        }
        rotatingInPlace = false;
        float f4 = 0.0F;
        d1 = 0.0F;
        float f5 = unitmove.totalTime;
        if(angles.getAbsDiffDeg() > 0.02F)
            if(angles.getAbsDiffDeg() <= f3)
            {
                f4 = angles.getAbsDiffDeg() / (f2 * 0.2F);
                if(f4 > f5)
                {
                    float f6 = f4 * 0.2F;
                    if(unitmove.dontrun)
                        f6 *= 0.6F;
                    if(f6 > f5)
                        f4 = f5 = f6;
                    else
                        f4 = f5;
                }
            } else
            {
                rotatingInPlace = true;
                f4 = angles.getAbsDiffDeg() / f2;
                if(f4 > f5)
                    f5 = f4;
                f5 -= f4;
            }
        d1 = f5;
        float f7 = com.maddox.il2.ai.ground.Moving.distance2D(srcPos, dstPos);
        float f8 = flag ? f1 : unitmove.dontrun ? unitmove.walkSpeed : f;
        if(f7 / f8 > d1)
            d1 = f7 / f8;
        rotatTotTime = com.maddox.il2.ai.ground.Moving.SecsToTicks(f4);
        moveTotTime = com.maddox.il2.ai.ground.Moving.SecsToTicks(d1);
        moveCurTime = d1 <= 0.0F ? -1L : moveTotTime;
        rotatCurTime = f4 <= 0.0F ? -1L : rotatTotTime;
        if(rotatCurTime < 0L && moveCurTime <= 1L || com.maddox.il2.ai.ground.Moving.distance2D(srcPos, dstPos) < 0.05F)
            switchToStay(((com.maddox.il2.ai.ground.UnitInterface)actor).StayInterval());
    }

    public void switchToRotate(com.maddox.il2.engine.Actor actor, float f, float f1)
    {
        if(normal == null)
        {
            movingForward = false;
            moveTotTime = moveCurTime = -1L;
            rotatTotTime = rotatCurTime = -1L;
            return;
        } else
        {
            dstPos = new Point3d();
            srcPos.set(actor.pos.getAbsPoint());
            dstPos.set(srcPos);
            rotatingInPlace = true;
            angles.setDeg(actor.pos.getAbsOrient().getYaw());
            angles.setDstDeg(angles.getSrcDeg() + f);
            float f2 = angles.getAbsDiffDeg() / f1;
            rotatTotTime = com.maddox.il2.ai.ground.Moving.SecsToTicks(f2);
            rotatCurTime = rotatTotTime <= 0L ? -1L : rotatTotTime;
            movingForward = true;
            moveTotTime = -1L;
            moveCurTime = -1L;
            return;
        }
    }

    public com.maddox.JGP.Point3d srcPos;
    public com.maddox.JGP.Point3d dstPos;
    public com.maddox.il2.ai.AnglesFork angles;
    public long moveTotTime;
    public long moveCurTime;
    public long rotatTotTime;
    public long rotatCurTime;
    public com.maddox.JGP.Vector3f normal;
    public boolean rotatingInPlace;
    public boolean movingForward;
}
