package com.maddox.il2.fm;

public class PolaresJet extends FMMath
{
  float[] Cx_Min = new float[3];
  float[] AOA_0 = new float[3];
  float[] AOA_C = new float[3];
  float[] Cy_Crit = new float[3];
  float[] Cx_Crit = new float[3];
  float CxA;
  float CxCurvature;
  float CxStraightness;
  public boolean JET = false;
  public boolean CANARD = false;

  protected float lastAOA = -1.0E+012F;
  protected float lastCx;
  protected float lastCy;
  protected float Flaps;

  public float get_Cy(float paramFloat)
  {
    if (paramFloat == this.lastAOA) return this.lastCy;
    polares2(paramFloat);
    return this.lastCy;
  }

  public float get_AOA_CRYT() {
    return this.AOA_C[2];
  }

  public float get_Cx(float paramFloat)
  {
    if (paramFloat == this.lastAOA) return this.lastCx;
    polares2(paramFloat);
    return this.lastCx;
  }

  protected void polares2(float paramFloat)
  {
    float f1 = get_Cxa(paramFloat);
    float f2 = get_Cya(paramFloat);
    float f3 = (float)Math.cos(DEG2RAD(paramFloat));
    float f4 = (float)Math.sin(DEG2RAD(paramFloat));
    this.lastCx = (f1 * f3 - f2 * f4);
    this.lastCy = (f2 * f3 + f1 * f4);
    this.lastAOA = paramFloat;
  }

  public float get_Cya(float paramFloat)
  {
    paramFloat -= this.AOA_0[2];
    if ((paramFloat > -90.0F) && (paramFloat < 90.0F)) {
      paramFloat *= 0.0111111F;
      if (paramFloat >= 0.0F) paramFloat = (float)Math.sqrt(paramFloat); else
        paramFloat = -(float)Math.sqrt(-paramFloat);
      paramFloat *= 90.0F;
    }
    float f = DEG2RAD(paramFloat);
    return (float)Math.sin(f * 2.0F) * this.Cy_Crit[2];
  }

  public float get_Cxa(float paramFloat)
  {
    float f = DEG2RAD(paramFloat);
    return (1.0F - (float)Math.cos(f * 2.0F)) * 0.5F + this.Cx_Min[2];
  }

  public float get_Cz(float paramFloat)
  {
    return 0.3F * (float)Math.sin(DEG2RAD(paramFloat) * 2.0F);
  }
}