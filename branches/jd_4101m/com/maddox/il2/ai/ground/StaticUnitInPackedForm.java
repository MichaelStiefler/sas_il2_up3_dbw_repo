package com.maddox.il2.ai.ground;

public class StaticUnitInPackedForm extends UnitInPackedFormGeneric
{
  private float x;
  private float y;
  private float yaw;

  public float X()
  {
    return this.x; } 
  public float Y() { return this.y; } 
  public float Yaw() { return this.yaw; }

  public StaticUnitInPackedForm(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, float paramFloat3) {
    super(paramInt1, paramInt2, paramInt3);
    this.x = paramFloat1;
    this.y = paramFloat2;
    this.yaw = paramFloat3;
  }
}