// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RoadSegment.java

package com.maddox.il2.ai.ground;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector2d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.bridges.LongBridge;

public class RoadSegment
{

    RoadSegment(com.maddox.il2.ai.ground.RoadSegment roadsegment)
    {
        waitTime = roadsegment.waitTime;
        br = roadsegment.br;
        brSg = roadsegment.brSg;
        start = new Point3d(roadsegment.start);
        begR = roadsegment.begR;
        endR = roadsegment.endR;
        dHeight = roadsegment.dHeight;
        length2D = roadsegment.length2D;
        length2Dallprev = roadsegment.length2Dallprev;
        normal = new Vector3f(roadsegment.normal);
        yaw = roadsegment.yaw;
        dir2D = new Vector2d(roadsegment.dir2D);
    }

    private static final long convertWTime(double d)
    {
        if(java.lang.Math.abs(d) < 0.10000000000000001D)
            return 0L;
        else
            return (long)(d * 60D * 1000D + (d <= 0.0D ? -0.5D : 0.5D));
    }

    public RoadSegment(double d, double d1, double d2, double d3, double d4, int i, int j)
    {
        start = new Point3d();
        normal = new Vector3f();
        dir2D = new Vector2d();
        set(d, d1, d2, d3, d4, i, j);
    }

    public void setZ(double d)
    {
        start.z = d;
    }

    public void set(double d, double d1, double d2, double d3, double d4, int i, int j)
    {
        waitTime = com.maddox.il2.ai.ground.RoadSegment.convertWTime(d4);
        start.set(d, d1, d2);
        begR = d3;
        if(i < 0)
        {
            br = null;
            brSg = null;
        } else
        {
            br = com.maddox.il2.objects.bridges.LongBridge.getByIdx(i);
            brSg = com.maddox.il2.objects.bridges.BridgeSegment.getByIdx(i, j);
        }
    }

    public boolean IsLandAligned()
    {
        return start.z < 0.0D;
    }

    public boolean IsPassableBy(com.maddox.il2.engine.Actor actor)
    {
        return brSg == null || br.isUsableFor(actor) && !brSg.IsDamaged();
    }

    public boolean IsDamaged()
    {
        return brSg != null && brSg.IsDamaged();
    }

    public void ComputeDerivedData(com.maddox.il2.ai.ground.RoadSegment roadsegment, com.maddox.il2.ai.ground.RoadSegment roadsegment1)
        throws java.lang.Exception
    {
        if(roadsegment == null)
            length2Dallprev = 0.0D;
        else
            length2Dallprev = roadsegment.length2Dallprev + roadsegment.length2D;
        if(roadsegment1 == null)
        {
            endR = begR;
            dHeight = 0.0D;
            length2D = 0.0D;
            normal.set(0.0F, 0.0F, 1.0F);
            yaw = 0.0F;
            dir2D.set(1.0D, 0.0D);
        } else
        {
            endR = roadsegment1.begR;
            P.sub(roadsegment1.start, start);
            if(roadsegment1.IsLandAligned())
                dHeight = com.maddox.il2.engine.Engine.land().HQ(roadsegment1.start.x, roadsegment1.start.y) - start.z;
            else
                dHeight = roadsegment1.start.z - start.z;
            length2D = java.lang.Math.sqrt(P.x * P.x + P.y * P.y);
            if(length2D <= 0.0D)
                throw new Exception("RS: too close points");
            normal.set((float)(-P.x * P.z), (float)(-P.y * P.z), (float)(length2D * length2D));
            normal.normalize();
            yaw = (float)(java.lang.Math.atan2(P.y, P.x) * 57.295779513082323D);
            dir2D.set(P.x, P.y);
            dir2D.normalize();
            if(br != null)
            {
                if(roadsegment != null && roadsegment.br == null)
                {
                    roadsegment.br = br;
                    roadsegment.brSg = brSg;
                }
                if(roadsegment1.br == null && (roadsegment == null || roadsegment.brSg != brSg))
                {
                    roadsegment1.br = br;
                    roadsegment1.brSg = brSg;
                }
            }
        }
    }

    public com.maddox.JGP.Point3d getEndP()
    {
        return new Point3d(start.x + dir2D.x * length2D, start.y + dir2D.y * length2D, start.z + dHeight);
    }

    double computePosAlong(com.maddox.JGP.Point3d point3d)
    {
        if(length2D == 0.0D)
        {
            return 0.0D;
        } else
        {
            double d = (point3d.x - start.x) * dir2D.x + (point3d.y - start.y) * dir2D.y;
            return d;
        }
    }

    public double computePosAlong_Fit(com.maddox.JGP.Point3d point3d)
    {
        if(length2D == 0.0D)
        {
            return 0.0D;
        } else
        {
            double d = (point3d.x - start.x) * dir2D.x + (point3d.y - start.y) * dir2D.y;
            return d > 0.0D ? d < length2D ? d : length2D : 0.0D;
        }
    }

    public double computePosSide(com.maddox.JGP.Point3d point3d)
    {
        if(length2D == 0.0D)
            return 0.0D;
        com.maddox.JGP.Vector2d vector2d = new Vector2d(point3d.x - start.x, point3d.y - start.y);
        double d = vector2d.dot(dir2D);
        if(d <= 0.0D)
            d = 0.0D;
        else
        if(d >= length2D)
            d = length2D;
        double d1 = vector2d.lengthSquared() - d * d;
        if(d1 <= 0.0D)
        {
            return 0.0D;
        } else
        {
            d1 = java.lang.Math.sqrt(d1);
            return dir2D.x * vector2d.y - dir2D.y * vector2d.x <= 0.0D ? d1 : -d1;
        }
    }

    public double computePosSide_Fit(com.maddox.JGP.Point3d point3d, float f)
    {
        if(length2D == 0.0D)
            return 0.0D;
        com.maddox.JGP.Vector2d vector2d = new Vector2d(point3d.x - start.x, point3d.y - start.y);
        double d = vector2d.dot(dir2D);
        if(d <= 0.0D)
            d = 0.0D;
        else
        if(d >= length2D)
            d = length2D;
        double d1 = vector2d.lengthSquared() - d * d;
        if(d1 <= 0.0D)
            return 0.0D;
        d1 = java.lang.Math.sqrt(d1);
        if(dir2D.x * vector2d.y - dir2D.y * vector2d.x > 0.0D)
            d1 = -d1;
        double d2 = d / length2D;
        double d3 = begR * (1.0D - d2) + endR * d2;
        if(f > 0.0F)
        {
            d3 -= f;
            if(d3 <= 0.0D)
                d3 = 0.0D;
        }
        if(d1 <= -d3)
            d1 = -d3;
        else
        if(d1 >= d3)
            d1 = d3;
        return d1;
    }

    public com.maddox.JGP.Point3d computePos_Fit(double d, double d1, float f)
    {
        if(length2D == 0.0D)
            return new Point3d(start);
        if(d <= 0.0D)
            d = 0.0D;
        else
        if(d >= length2D)
            d = length2D;
        double d2 = d / length2D;
        double d3 = begR * (1.0D - d2) + endR * d2;
        if(f > 0.0F)
        {
            d3 -= f;
            if(d3 <= 0.0D)
                d3 = 0.0D;
        }
        if(d1 <= -d3)
            d1 = -d3;
        else
        if(d1 >= d3)
            d1 = d3;
        com.maddox.JGP.Point3d point3d = new Point3d(start);
        point3d.x += dir2D.x * d + dir2D.y * d1;
        point3d.y += dir2D.y * d - dir2D.x * d1;
        if(point3d.z < 0.0D)
            point3d.z = com.maddox.il2.engine.Engine.land().HQ(point3d.x, point3d.y);
        else
            point3d.z += d2 * dHeight;
        return point3d;
    }

    public com.maddox.JGP.Point3d computePos_FitBegCirc(double d, double d1, float f)
    {
        if(length2D == 0.0D)
            return new Point3d(start);
        double d2;
        if(d <= 0.0D)
        {
            double d3 = java.lang.Math.sqrt(d * d + d1 * d1);
            double d5;
            if(f > 0.0F)
            {
                d5 = begR - (double)f;
                if(d5 <= 0.0D)
                    d5 = 0.0D;
            } else
            {
                d5 = begR;
            }
            if(d3 > d5)
            {
                double d6 = d5 / d3;
                d *= d6;
                d1 *= d6;
            }
            d2 = d / length2D;
        } else
        {
            if(d >= length2D)
                d = length2D;
            d2 = d / length2D;
            double d4 = begR * (1.0D - d2) + endR * d2;
            if(f > 0.0F)
            {
                d4 -= f;
                if(d4 <= 0.0D)
                    d4 = 0.0D;
            }
            if(d1 <= -d4)
                d1 = -d4;
            else
            if(d1 >= d4)
                d1 = d4;
        }
        com.maddox.JGP.Point3d point3d = new Point3d(start);
        point3d.x += dir2D.x * d + dir2D.y * d1;
        point3d.y += dir2D.y * d - dir2D.x * d1;
        if(point3d.z < 0.0D)
            point3d.z = com.maddox.il2.engine.Engine.land().HQ(point3d.x, point3d.y);
        else
            point3d.z += d2 * dHeight;
        return point3d;
    }

    private static com.maddox.JGP.Point3d P = new Point3d();
    public long waitTime;
    public com.maddox.il2.objects.bridges.LongBridge br;
    public com.maddox.il2.objects.bridges.BridgeSegment brSg;
    public com.maddox.JGP.Point3d start;
    public double begR;
    public double endR;
    public double dHeight;
    public double length2D;
    public double length2Dallprev;
    public com.maddox.JGP.Vector3f normal;
    public float yaw;
    public com.maddox.JGP.Vector2d dir2D;

}
