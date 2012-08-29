// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   RoadPath.java

package com.maddox.il2.ai.ground;

import com.maddox.JGP.Vector2d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.bridges.LongBridge;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.ai.ground:
//            RoadSegment, RoadPart

public class RoadPath
{

    public RoadPath(com.maddox.il2.ai.ground.RoadPath roadpath)
    {
        segments = new ArrayList();
        segments = new ArrayList();
        for(int i = 0; i < roadpath.segments.size(); i++)
        {
            com.maddox.il2.ai.ground.RoadSegment roadsegment = (com.maddox.il2.ai.ground.RoadSegment)roadpath.segments.get(i);
            segments.add(new RoadSegment(roadsegment));
        }

    }

    public com.maddox.il2.ai.ground.RoadSegment get(int i)
    {
        if(i < 0 || i >= segments.size())
            return null;
        else
            return (com.maddox.il2.ai.ground.RoadSegment)segments.get(i);
    }

    public int nsegments()
    {
        return segments.size();
    }

    public float computeCosToNextSegment(int i)
    {
        com.maddox.JGP.Vector2d vector2d = ((com.maddox.il2.ai.ground.RoadSegment)segments.get(i)).dir2D;
        com.maddox.JGP.Vector2d vector2d1 = ((com.maddox.il2.ai.ground.RoadSegment)segments.get(i + 1)).dir2D;
        return (float)vector2d.dot(vector2d1);
    }

    public RoadPath(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        segments = new ArrayList();
        int i = sectfile.sectionIndex(s);
        if(i < 0)
            throw new ActorException("RoadPath: Section [" + s + "] not found");
        int j = sectfile.vars(i);
        if(j < 1)
            throw new ActorException("RoadPath must contain at least 2 segments");
        segments.clear();
        boolean flag = false;
        for(int k = 0; k < j; k++)
        {
            java.util.StringTokenizer stringtokenizer = new StringTokenizer(sectfile.line(i, k));
            float f = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
            float f1 = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
            float f2 = java.lang.Float.valueOf(stringtokenizer.nextToken()).floatValue();
            if(f2 > 100F)
                f2 = 60F;
            double d = 0.0D;
            if(stringtokenizer.hasMoreTokens())
                d = java.lang.Double.valueOf(stringtokenizer.nextToken()).doubleValue();
            int l = -(int)f2 - 1;
            if(l < 0)
            {
                if(flag)
                    throw new ActorException("Road: one-element bridge");
                if(f2 <= 0.0F)
                    throw new ActorException("Road: wrong width");
                segments.add(new RoadSegment(f, f1, -1D, f2 * 0.5F, d, -1, -1));
                continue;
            }
            if(flag)
            {
                flag = false;
                continue;
            }
            flag = true;
            if(f2 != (float)(int)f2)
                throw new ActorException("Road: bad bridge index");
            com.maddox.il2.objects.bridges.LongBridge.AddSegmentsToRoadPath(l, segments, f, f1);
        }

        if(flag)
        {
            throw new ActorException("Road: one-element bridge");
        } else
        {
            ComputeDerivedData();
            return;
        }
    }

    private boolean ComputeDerivedData()
    {
        if(segments.size() <= 0)
            return false;
        long l = -1L;
        for(int i = 0; i < segments.size(); i++)
        {
            com.maddox.il2.ai.ground.RoadSegment roadsegment = (com.maddox.il2.ai.ground.RoadSegment)segments.get(i);
            roadsegment.ComputeDerivedData(i != 0 ? (com.maddox.il2.ai.ground.RoadSegment)segments.get(i - 1) : null, i < segments.size() - 1 ? (com.maddox.il2.ai.ground.RoadSegment)segments.get(i + 1) : null);
            if(roadsegment.waitTime <= 0L)
                continue;
            if(roadsegment.waitTime > l)
                l = roadsegment.waitTime;
            else
                throw new ActorException("RoadPath: unordered WaitTime detected");
        }

        return true;
        java.lang.Exception exception;
        exception;
        return false;
    }

    public boolean segIsWrongOrDamaged(int i)
    {
        return i < 0 || i >= segments.size() || ((com.maddox.il2.ai.ground.RoadSegment)segments.get(i)).IsDamaged();
    }

    public boolean segIsPassableBy(int i, com.maddox.il2.engine.Actor actor)
    {
        return i >= 0 && i < segments.size() && ((com.maddox.il2.ai.ground.RoadSegment)segments.get(i)).IsPassableBy(actor);
    }

    public void RegisterTravellerToBridges(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.objects.bridges.LongBridge longbridge = null;
        for(int i = 0; i < segments.size(); i++)
        {
            com.maddox.il2.ai.ground.RoadSegment roadsegment = (com.maddox.il2.ai.ground.RoadSegment)segments.get(i);
            if(roadsegment.br != null && roadsegment.br != longbridge)
            {
                longbridge = roadsegment.br;
                com.maddox.il2.objects.bridges.LongBridge.AddTraveller(roadsegment.brSg.Code(), actor);
            }
        }

    }

    public void UnregisterTravellerFromBridges(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.objects.bridges.LongBridge longbridge = null;
        for(int i = 0; i < segments.size(); i++)
        {
            com.maddox.il2.ai.ground.RoadSegment roadsegment = (com.maddox.il2.ai.ground.RoadSegment)segments.get(i);
            if(roadsegment.br != null && roadsegment.br != longbridge)
            {
                longbridge = roadsegment.br;
                com.maddox.il2.objects.bridges.LongBridge.DelTraveller(roadsegment.brSg.Code(), actor);
            }
        }

    }

    public boolean MarkDestroyedSegments(int i, int j)
    {
        com.maddox.il2.objects.bridges.BridgeSegment bridgesegment = com.maddox.il2.objects.bridges.BridgeSegment.getByIdx(i, j);
        boolean flag = false;
        for(int k = 0; k < segments.size(); k++)
        {
            com.maddox.il2.ai.ground.RoadSegment roadsegment = (com.maddox.il2.ai.ground.RoadSegment)segments.get(k);
            if(roadsegment.brSg == bridgesegment)
                flag = true;
        }

        return flag;
    }

    int getCodeOfBridgeSegment(int i)
    {
        com.maddox.il2.ai.ground.RoadSegment roadsegment = (com.maddox.il2.ai.ground.RoadSegment)segments.get(i);
        return roadsegment.br != null ? roadsegment.brSg.Code() : -1;
    }

    public double ComputeSignedDistAlong(int i, double d, int j, double d1)
    {
        double d2 = ((com.maddox.il2.ai.ground.RoadSegment)segments.get(i)).length2Dallprev;
        double d3 = ((com.maddox.il2.ai.ground.RoadSegment)segments.get(j)).length2Dallprev;
        return ((d3 - d2) + d1) - d;
    }

    public void unlockBridges(com.maddox.il2.engine.Actor actor, int i, int j)
    {
        if(i < 0)
            i = 0;
        if(j >= segments.size())
            j = segments.size() - 1;
        do
        {
            if(i > j)
                break;
            com.maddox.il2.ai.ground.RoadSegment roadsegment = (com.maddox.il2.ai.ground.RoadSegment)segments.get(i++);
            if(roadsegment.br != null)
                roadsegment.br.resetSupervisor(actor);
        } while(true);
    }

    public void lockBridges(com.maddox.il2.engine.Actor actor, int i, int j)
    {
        if(i < 0 || j >= segments.size())
            return;
        do
        {
            if(i > j)
                break;
            com.maddox.il2.ai.ground.RoadSegment roadsegment = (com.maddox.il2.ai.ground.RoadSegment)segments.get(i++);
            if(roadsegment.br != null)
                roadsegment.br.setSupervisor(actor);
        } while(true);
    }

    public double ComputeMinRoadWidth(int i, int j)
    {
        if(i > j)
        {
            int k = i;
            i = j;
            j = k;
        }
        double d = ((com.maddox.il2.ai.ground.RoadSegment)segments.get(i)).begR;
        for(; i <= j; i++)
        {
            double d1 = ((com.maddox.il2.ai.ground.RoadSegment)segments.get(i)).begR;
            double d2 = ((com.maddox.il2.ai.ground.RoadSegment)segments.get(i)).endR;
            if(d2 < d)
                d = d2;
            if(d1 < d)
                d = d1;
        }

        return 2D * d;
    }

    public long getMaxWaitTime(int i, int j)
    {
        if(i > j)
        {
            int k = i;
            i = j;
            j = k;
        }
        if(i < 0)
        {
            i = 0;
            if(j < 0)
            {
                java.lang.System.out.println("Road: unexpected both segments < 0");
                return 0L;
            }
        }
        long l = 0L;
        for(; i <= j; i++)
        {
            long l1 = ((com.maddox.il2.ai.ground.RoadSegment)segments.get(i)).waitTime;
            if(l1 < 0L)
            {
                l1 = -l1 + com.maddox.rts.Time.tick();
                ((com.maddox.il2.ai.ground.RoadSegment)segments.get(i)).waitTime = l1;
            }
            if(l1 > l)
                l = l1;
        }

        return l;
    }

    public boolean FindFreeSpace(double d, int i, double d1, com.maddox.il2.ai.ground.RoadPart roadpart)
    {
        roadpart.begseg = i;
        roadpart.begt = d1;
        roadpart.endseg = roadpart.begseg;
        roadpart.endt = roadpart.begt;
        roadpart.occupLen = 0.0D;
        if(roadpart.occupLen >= d)
            return true;
        double d2 = roadpart.begt;
        if(roadpart.occupLen + d2 >= d)
        {
            roadpart.endt = roadpart.begt - (d - roadpart.occupLen);
            roadpart.occupLen = d;
            return true;
        }
        roadpart.endt = 0.0D;
        for(roadpart.occupLen = d2; !segIsWrongOrDamaged(roadpart.endseg - 1); roadpart.occupLen += d2)
        {
            roadpart.endseg--;
            d2 = get(roadpart.endseg).length2D;
            if(roadpart.occupLen + d2 >= d)
            {
                roadpart.endt = d2 - (d - roadpart.occupLen);
                roadpart.occupLen = d;
                return true;
            }
            roadpart.endt = 0.0D;
        }

        d2 = get(roadpart.begseg).length2D - roadpart.begt;
        if(roadpart.occupLen + d2 >= d)
        {
            roadpart.begt += d - roadpart.occupLen;
            roadpart.occupLen = d;
            return true;
        }
        roadpart.begt += d2;
        for(roadpart.occupLen += d2; !segIsWrongOrDamaged(roadpart.begseg + 1); roadpart.occupLen += d2)
        {
            roadpart.begseg++;
            d2 = get(roadpart.begseg).length2D;
            if(roadpart.occupLen + d2 >= d)
            {
                roadpart.begt = d - roadpart.occupLen;
                roadpart.occupLen = d;
                return true;
            }
            roadpart.begt = d2;
        }

        return false;
    }

    public java.util.ArrayList segments;
}
