package com.maddox.il2.ai.ground;

public class UnitInPackedForm extends UnitInPackedFormGeneric
{
  public float SPEED_AVERAGE;
  public float BEST_SPACE;
  public int WEAPONS_MASK;
  public int HITBY_MASK;

  public UnitInPackedForm(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, int paramInt4, int paramInt5)
  {
    super(paramInt1, paramInt2, paramInt3);
    this.SPEED_AVERAGE = paramFloat1;
    this.BEST_SPACE = paramFloat2;
    this.WEAPONS_MASK = paramInt4;
    this.HITBY_MASK = paramInt5;
  }

  public UnitInPackedForm(int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInt1, paramInt2, paramInt3);
    this.SPEED_AVERAGE = 0.0F;
    this.BEST_SPACE = 0.0F;
    this.WEAPONS_MASK = 0;
    this.HITBY_MASK = 0;
  }
}