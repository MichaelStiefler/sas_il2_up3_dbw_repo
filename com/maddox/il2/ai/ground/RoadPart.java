package com.maddox.il2.ai.ground;

public class RoadPart
{
  public int begseg;
  public int endseg;
  public double begt;
  public double endt;
  public double occupLen;

  public RoadPart()
  {
    this.begseg = (this.endseg = 0);
    this.begt = (this.endt = 0.0D);
    this.occupLen = 0.0D;
  }
}