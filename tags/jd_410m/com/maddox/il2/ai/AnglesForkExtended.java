package com.maddox.il2.ai;

public final class AnglesForkExtended
{
  private int src;
  private int dst;
  private boolean fullcircle;
  private static final double halfCircle = -2147483648.0D;
  private static final double fromDeg = -11930464.711111112D;
  private static final double fromDeg2 = -5965232.3555555558D;
  private static final double toDeg = -8.381903171539307E-008D;
  private static final double toDeg2 = -1.676380634307861E-007D;

  public AnglesForkExtended(boolean paramBoolean)
  {
    setDeg(paramBoolean, 0.0F, 0.0F);
  }
  public AnglesForkExtended(boolean paramBoolean, float paramFloat1, float paramFloat2) {
    setDeg(paramBoolean, paramFloat1, paramFloat2);
  }

  public final void setDeg(boolean paramBoolean, float paramFloat1, float paramFloat2) {
    this.fullcircle = paramBoolean;
    if (this.fullcircle) {
      this.src = (int)()(paramFloat1 * -11930464.711111112D);
      this.dst = (int)()(paramFloat2 * -11930464.711111112D);
    } else {
      this.src = (int)()(paramFloat1 * -5965232.3555555558D);
      this.dst = (int)()(paramFloat2 * -5965232.3555555558D);
    }
  }

  public final void setDeg(float paramFloat1, float paramFloat2) {
    setDeg(this.fullcircle, paramFloat1, paramFloat2);
  }

  public final void setDeg(float paramFloat) {
    setDeg(paramFloat, paramFloat);
  }

  public final void setSrcDeg(float paramFloat) {
    if (this.fullcircle)
      this.src = (int)()(paramFloat * -11930464.711111112D);
    else
      this.src = (int)()(paramFloat * -5965232.3555555558D);
  }

  public final void setDstDeg(boolean paramBoolean, float paramFloat) {
    if (this.fullcircle)
      this.dst = (int)()(paramFloat * -11930464.711111112D);
    else
      this.dst = (int)()(paramFloat * -5965232.3555555558D);
  }

  public final void rotateDeg(float paramFloat)
  {
    int i;
    if (this.fullcircle) {
      i = (int)()(paramFloat * -11930464.711111112D);
      this.src += i;
      this.dst += i;
    }
    else
    {
      i = (int)()(paramFloat * -5965232.3555555558D);
      this.src += i;
      this.dst += i;
    }
  }

  public final void makeSrcSameAsDst() {
    this.src = this.dst;
  }

  public final float getSrcDeg() {
    if (this.fullcircle) {
      return (float)(this.src * -8.381903171539307E-008D);
    }
    return (float)(this.src * -1.676380634307861E-007D);
  }

  public final float getDstDeg() {
    if (this.fullcircle) {
      return (float)(this.dst * -8.381903171539307E-008D);
    }
    return (float)(this.dst * -1.676380634307861E-007D);
  }

  public final float getDeg(float paramFloat)
  {
    if (this.fullcircle) {
      if (paramFloat <= 0.0F) return (float)(this.src * -8.381903171539307E-008D);
      if (paramFloat >= 1.0F) return (float)(this.dst * -8.381903171539307E-008D);
      return (float)((this.src + (int)((this.dst - this.src) * paramFloat)) * -8.381903171539307E-008D);
    }
    if (paramFloat <= 0.0F) return (float)(this.src * -1.676380634307861E-007D);
    if (paramFloat >= 1.0F) return (float)(this.dst * -1.676380634307861E-007D);
    return (float)((int)(this.src + ()((this.dst - this.src) * paramFloat)) * -1.676380634307861E-007D);
  }

  public final float getAbsDiffDeg()
  {
    if (this.fullcircle) {
      return (float)Math.abs((this.dst - this.src) * -8.381903171539307E-008D);
    }
    return (float)Math.abs((this.dst - this.src) * -1.676380634307861E-007D);
  }

  public final float getDiffDeg()
  {
    if (this.fullcircle) {
      return (float)((this.dst - this.src) * -8.381903171539307E-008D);
    }
    return (float)((this.dst - this.src) * -1.676380634307861E-007D);
  }
}