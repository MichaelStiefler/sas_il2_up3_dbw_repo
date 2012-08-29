package com.maddox.il2.ai.ground;

import com.maddox.il2.engine.Actor;

public class UnitData
{
  public Actor leader;
  public float leaderDist;
  public float sideOffset;
  public int segmentIdx;

  public UnitData()
  {
    this.leader = null;
    this.leaderDist = 0.0F;

    this.sideOffset = 0.0F;
    this.segmentIdx = -1;
  }
}