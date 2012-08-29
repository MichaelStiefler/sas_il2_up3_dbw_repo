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

public class RoadPath
{
  public ArrayList segments = new ArrayList();

  public RoadPath(RoadPath paramRoadPath) {
    this.segments = new ArrayList();
    for (int i = 0; i < paramRoadPath.segments.size(); i++) {
      RoadSegment localRoadSegment = (RoadSegment)paramRoadPath.segments.get(i);
      this.segments.add(new RoadSegment(localRoadSegment));
    }
  }

  public RoadSegment get(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.segments.size())) return null;
    return (RoadSegment)this.segments.get(paramInt);
  }

  public int nsegments()
  {
    return this.segments.size();
  }

  public float computeCosToNextSegment(int paramInt) {
    Vector2d localVector2d1 = ((RoadSegment)this.segments.get(paramInt)).dir2D;
    Vector2d localVector2d2 = ((RoadSegment)this.segments.get(paramInt + 1)).dir2D;
    return (float)localVector2d1.dot(localVector2d2);
  }

  public RoadPath(SectFile paramSectFile, String paramString)
  {
    int i = paramSectFile.sectionIndex(paramString);
    if (i < 0) {
      throw new ActorException("RoadPath: Section [" + paramString + "] not found");
    }
    int j = paramSectFile.vars(i);
    if (j < 1) {
      throw new ActorException("RoadPath must contain at least 2 segments");
    }
    this.segments.clear();
    int k = 0;
    for (int m = 0; m < j; m++) {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramSectFile.line(i, m));
      float f1 = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
      float f2 = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
      float f3 = Float.valueOf(localStringTokenizer.nextToken()).floatValue();
      if (f3 > 100.0F) {
        f3 = 60.0F;
      }

      double d = 0.0D;
      if (localStringTokenizer.hasMoreTokens()) {
        d = Double.valueOf(localStringTokenizer.nextToken()).doubleValue();
      }

      int n = -(int)f3 - 1;
      if (n < 0) {
        if (k != 0)
          throw new ActorException("Road: one-element bridge");
        if (f3 <= 0.0F)
          throw new ActorException("Road: wrong width");
        this.segments.add(new RoadSegment(f1, f2, -1.0D, f3 * 0.5F, d, -1, -1));
      }
      else if (k != 0) {
        k = 0;
      }
      else {
        k = 1;
        if (f3 != (int)f3)
          throw new ActorException("Road: bad bridge index");
        LongBridge.AddSegmentsToRoadPath(n, this.segments, f1, f2);
      }
    }
    if (k != 0) {
      throw new ActorException("Road: one-element bridge");
    }
    ComputeDerivedData();
  }

  private boolean ComputeDerivedData() {
    if (this.segments.size() <= 0) return false; try
    {
      long l = -1L;
      for (int i = 0; i < this.segments.size(); i++) {
        RoadSegment localRoadSegment = (RoadSegment)this.segments.get(i);
        localRoadSegment.ComputeDerivedData(i == 0 ? null : (RoadSegment)this.segments.get(i - 1), i >= this.segments.size() - 1 ? null : (RoadSegment)this.segments.get(i + 1));

        if (localRoadSegment.waitTime > 0L) {
          if (localRoadSegment.waitTime > l) {
            l = localRoadSegment.waitTime;
          }
          else {
            throw new ActorException("RoadPath: unordered WaitTime detected");
          }
        }
      }
      return true; } catch (Exception localException) {
    }
    return false;
  }

  public boolean segIsWrongOrDamaged(int paramInt)
  {
    return (paramInt < 0) || (paramInt >= this.segments.size()) || (((RoadSegment)this.segments.get(paramInt)).IsDamaged());
  }

  public boolean segIsPassableBy(int paramInt, Actor paramActor)
  {
    return (paramInt >= 0) && (paramInt < this.segments.size()) && (((RoadSegment)this.segments.get(paramInt)).IsPassableBy(paramActor));
  }

  public void RegisterTravellerToBridges(Actor paramActor)
  {
    LongBridge localLongBridge = null;
    for (int i = 0; i < this.segments.size(); i++) {
      RoadSegment localRoadSegment = (RoadSegment)this.segments.get(i);
      if ((localRoadSegment.br != null) && (localRoadSegment.br != localLongBridge)) {
        localLongBridge = localRoadSegment.br;
        LongBridge.AddTraveller(localRoadSegment.brSg.Code(), paramActor);
      }
    }
  }

  public void UnregisterTravellerFromBridges(Actor paramActor)
  {
    LongBridge localLongBridge = null;
    for (int i = 0; i < this.segments.size(); i++) {
      RoadSegment localRoadSegment = (RoadSegment)this.segments.get(i);
      if ((localRoadSegment.br != null) && (localRoadSegment.br != localLongBridge)) {
        localLongBridge = localRoadSegment.br;
        LongBridge.DelTraveller(localRoadSegment.brSg.Code(), paramActor);
      }
    }
  }

  public boolean MarkDestroyedSegments(int paramInt1, int paramInt2)
  {
    BridgeSegment localBridgeSegment = BridgeSegment.getByIdx(paramInt1, paramInt2);
    int i = 0;
    for (int j = 0; j < this.segments.size(); j++) {
      RoadSegment localRoadSegment = (RoadSegment)this.segments.get(j);
      if (localRoadSegment.brSg == localBridgeSegment) {
        i = 1;
      }
    }
    return i;
  }

  int getCodeOfBridgeSegment(int paramInt)
  {
    RoadSegment localRoadSegment = (RoadSegment)this.segments.get(paramInt);
    return localRoadSegment.br == null ? -1 : localRoadSegment.brSg.Code();
  }

  public double ComputeSignedDistAlong(int paramInt1, double paramDouble1, int paramInt2, double paramDouble2)
  {
    double d1 = ((RoadSegment)this.segments.get(paramInt1)).length2Dallprev;
    double d2 = ((RoadSegment)this.segments.get(paramInt2)).length2Dallprev;
    return d2 - d1 + paramDouble2 - paramDouble1;
  }

  public void unlockBridges(Actor paramActor, int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0)
      paramInt1 = 0;
    if (paramInt2 >= this.segments.size()) {
      paramInt2 = this.segments.size() - 1;
    }
    while (paramInt1 <= paramInt2) {
      RoadSegment localRoadSegment = (RoadSegment)this.segments.get(paramInt1++);
      if (localRoadSegment.br != null)
        localRoadSegment.br.resetSupervisor(paramActor);
    }
  }

  public void lockBridges(Actor paramActor, int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt2 >= this.segments.size()))
      return;
    while (paramInt1 <= paramInt2) {
      RoadSegment localRoadSegment = (RoadSegment)this.segments.get(paramInt1++);
      if (localRoadSegment.br != null)
        localRoadSegment.br.setSupervisor(paramActor);
    }
  }

  public double ComputeMinRoadWidth(int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2) { int i = paramInt1; paramInt1 = paramInt2; paramInt2 = i; }
    double d1 = ((RoadSegment)this.segments.get(paramInt1)).begR;
    while (paramInt1 <= paramInt2) {
      double d2 = ((RoadSegment)this.segments.get(paramInt1)).begR;
      double d3 = ((RoadSegment)this.segments.get(paramInt1)).endR;
      if (d3 < d1) d1 = d3;
      if (d2 < d1) d1 = d2;
      paramInt1++;
    }
    return 2.0D * d1;
  }

  public long getMaxWaitTime(int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2) { int i = paramInt1; paramInt1 = paramInt2; paramInt2 = i; }
    if (paramInt1 < 0) {
      paramInt1 = 0;
      if (paramInt2 < 0) {
        System.out.println("Road: unexpected both segments < 0");
        return 0L;
      }
    }
    long l1 = 0L;
    while (paramInt1 <= paramInt2) {
      long l2 = ((RoadSegment)this.segments.get(paramInt1)).waitTime;
      if (l2 < 0L) {
        l2 = -l2 + Time.tick();
        ((RoadSegment)this.segments.get(paramInt1)).waitTime = l2;
      }
      if (l2 > l1) {
        l1 = l2;
      }
      paramInt1++;
    }
    return l1;
  }

  public boolean FindFreeSpace(double paramDouble1, int paramInt, double paramDouble2, RoadPart paramRoadPart)
  {
    paramRoadPart.begseg = paramInt;
    paramRoadPart.begt = paramDouble2;
    paramRoadPart.endseg = paramRoadPart.begseg;
    paramRoadPart.endt = paramRoadPart.begt;
    paramRoadPart.occupLen = 0.0D;
    if (paramRoadPart.occupLen >= paramDouble1) {
      return true;
    }

    double d = paramRoadPart.begt;
    if (paramRoadPart.occupLen + d >= paramDouble1)
    {
      paramRoadPart.endt = (paramRoadPart.begt - (paramDouble1 - paramRoadPart.occupLen));
      paramRoadPart.occupLen = paramDouble1;
      return true;
    }

    paramRoadPart.endt = 0.0D;
    paramRoadPart.occupLen = d;

    while (!segIsWrongOrDamaged(paramRoadPart.endseg - 1)) {
      paramRoadPart.endseg -= 1;
      d = get(paramRoadPart.endseg).length2D;

      if (paramRoadPart.occupLen + d >= paramDouble1)
      {
        paramRoadPart.endt = (d - (paramDouble1 - paramRoadPart.occupLen));
        paramRoadPart.occupLen = paramDouble1;
        return true;
      }

      paramRoadPart.endt = 0.0D;
      paramRoadPart.occupLen += d;
    }

    d = get(paramRoadPart.begseg).length2D - paramRoadPart.begt;
    if (paramRoadPart.occupLen + d >= paramDouble1)
    {
      paramRoadPart.begt += paramDouble1 - paramRoadPart.occupLen;
      paramRoadPart.occupLen = paramDouble1;
      return true;
    }

    paramRoadPart.begt += d;
    paramRoadPart.occupLen += d;

    while (!segIsWrongOrDamaged(paramRoadPart.begseg + 1)) {
      paramRoadPart.begseg += 1;
      d = get(paramRoadPart.begseg).length2D;
      if (paramRoadPart.occupLen + d >= paramDouble1)
      {
        paramRoadPart.begt = (paramDouble1 - paramRoadPart.occupLen);
        paramRoadPart.occupLen = paramDouble1;
        return true;
      }

      paramRoadPart.begt = d;
      paramRoadPart.occupLen += d;
    }

    return false;
  }
}