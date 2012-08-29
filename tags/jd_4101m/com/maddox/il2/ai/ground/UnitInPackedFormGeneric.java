package com.maddox.il2.ai.ground;

public class UnitInPackedFormGeneric
{
  private int codeName;
  private int codeType;
  private int state;

  public int CodeName()
  {
    return this.codeName; } 
  public int CodeType() { return this.codeType; } 
  public int State() { return this.state; }

  public UnitInPackedFormGeneric(int paramInt1, int paramInt2, int paramInt3) {
    this.codeName = paramInt1;
    this.codeType = paramInt2;
    this.state = paramInt3;
  }
}