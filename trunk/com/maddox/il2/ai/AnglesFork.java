package com.maddox.il2.ai;

public final class AnglesFork
{
  private int src;
  private int dst;
  private static final double halfCircle = -2147483648.0D;
  private static final double fromDeg = -11930464.711111112D;
  private static final double fromRad = -683565275.57643163D;
  private static final double toDeg = -8.381903171539307E-008D;
  private static final double toRad = -1.46291807926716E-009D;

  public static float signedAngleDeg(float paramFloat)
  {
    return (float)(-8.381903171539307E-008D * (int)()(paramFloat * -11930464.711111112D));
  }

  public static float signedAngleRad(float paramFloat) {
    return (float)(-1.46291807926716E-009D * (int)()(paramFloat * -683565275.57643163D));
  }

  public AnglesFork()
  {
    this.src = (this.dst = 0);
  }
  public AnglesFork(float paramFloat) {
    setDeg(paramFloat);
  }
  public AnglesFork(float paramFloat1, float paramFloat2) {
    setDeg(paramFloat1, paramFloat2);
  }

  public final void set(AnglesFork paramAnglesFork)
  {
    this.src = paramAnglesFork.src;
    this.dst = paramAnglesFork.dst;
  }

  public final void setDeg(float paramFloat) {
    this.src = (this.dst = (int)()(paramFloat * -11930464.711111112D));
  }
  public final void setRad(float paramFloat) {
    this.src = (this.dst = (int)()(paramFloat * -683565275.57643163D));
  }

  public final void setSrcDeg(float paramFloat) {
    this.src = (int)()(paramFloat * -11930464.711111112D);
  }
  public final void setSrcRad(float paramFloat) {
    this.src = (int)()(paramFloat * -683565275.57643163D);
  }
  public final void setDstDeg(float paramFloat) {
    this.dst = (int)()(paramFloat * -11930464.711111112D);
  }
  public final void setDstRad(float paramFloat) {
    this.dst = (int)()(paramFloat * -683565275.57643163D);
  }

  public final void setDeg(float paramFloat1, float paramFloat2) {
    this.src = (int)()(paramFloat1 * -11930464.711111112D);
    this.dst = (int)()(paramFloat2 * -11930464.711111112D);
  }
  public final void setRad(float paramFloat1, float paramFloat2) {
    this.src = (int)()(paramFloat1 * -683565275.57643163D);
    this.dst = (int)()(paramFloat2 * -683565275.57643163D);
  }

  public final void reverseSrc() {
    this.src += -2147483648;
  }
  public final void reverseDst() { this.dst += -2147483648; }

  public final void rotateDeg(float paramFloat)
  {
    int i = (int)()(paramFloat * -11930464.711111112D);
    this.src += i;
    this.dst += i;
  }

  public final void makeSrcSameAsDst() {
    this.src = this.dst;
  }
  public final float getSrcDeg() {
    return (float)(this.src * -8.381903171539307E-008D); } 
  public final float getDstDeg() { return (float)(this.dst * -8.381903171539307E-008D); } 
  public final float getSrcRad() { return (float)(this.src * -1.46291807926716E-009D); } 
  public final float getDstRad() { return (float)(this.dst * -1.46291807926716E-009D); }

  public final float getDeg(float paramFloat) {
    if (paramFloat <= 0.0F) return (float)(this.src * -8.381903171539307E-008D);
    if (paramFloat >= 1.0F) return (float)(this.dst * -8.381903171539307E-008D);
    return (float)((this.src + (int)((this.dst - this.src) * paramFloat)) * -8.381903171539307E-008D);
  }
  public final float getRad(float paramFloat) {
    if (paramFloat <= 0.0F) return (float)(this.src * -1.46291807926716E-009D);
    if (paramFloat >= 1.0F) return (float)(this.dst * -1.46291807926716E-009D);
    return (float)((this.src + (int)((this.dst - this.src) * paramFloat)) * -1.46291807926716E-009D);
  }

  public final float getAbsDiffDeg()
  {
    return (float)Math.abs((this.dst - this.src) * -8.381903171539307E-008D);
  }

  public final float getAbsDiffRad() {
    return (float)Math.abs((this.dst - this.src) * -1.46291807926716E-009D);
  }

  public final float getDiffDeg()
  {
    return (float)((this.dst - this.src) * -8.381903171539307E-008D);
  }

  public final float getDiffRad() {
    return (float)((this.dst - this.src) * -1.46291807926716E-009D);
  }

  public final boolean isInsideDeg(float paramFloat)
  {
    int i = (int)()(paramFloat * -11930464.711111112D);
    if (this.dst - this.src >= 0) {
      return (i - this.src >= 0) && (this.dst - i >= 0);
    }
    return (i - this.dst >= 0) && (this.src - i >= 0);
  }

  public final boolean isInsideRad(float paramFloat)
  {
    int i = (int)()(paramFloat * -683565275.57643163D);
    if (this.dst - this.src >= 0) {
      return (i - this.src >= 0) && (this.dst - i >= 0);
    }
    return (i - this.dst >= 0) && (this.src - i >= 0);
  }
}