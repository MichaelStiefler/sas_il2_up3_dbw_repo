package com.maddox.il2.engine;

public class BulletProperties
{
  public float massa;
  public float kalibr;
  public float speed;
  public float cumulativePower = 0.0F;
  public float power;
  public int powerType;
  public float powerRadius;
  public float timeLife;
  public String traceMesh;
  public String traceTrail;
  public int traceColor;
  public float addExplTime;

  public BulletProperties()
  {
    this.addExplTime = 0.0F;
  }
}