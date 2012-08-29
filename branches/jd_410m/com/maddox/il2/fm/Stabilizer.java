package com.maddox.il2.fm;

public class Stabilizer
{
  float Hset;
  float Hcur;
  float Hold;
  float HDiff;
  float MaxSpeed;
  float Speed;
  float OldSpeed;
  float Accel;
  float OldAccel;
  float dAccel;
  float NeedSpeed;
  float NeedAccel;
  float A;

  public void set(float paramFloat1, float paramFloat2)
  {
    this.Hset = (this.Hcur = this.Hold = paramFloat1); this.A = paramFloat2;
    this.HDiff = (this.Speed = this.OldSpeed = this.Accel = this.OldAccel = 0.0F);
    this.MaxSpeed = Math.max(this.Hset * 1.0E-004F, 0.001F);
  }

  public float getOutput(float paramFloat) {
    this.Hold = this.Hcur;
    this.Hcur = paramFloat;
    this.OldAccel = this.Accel;
    this.OldSpeed = this.Speed;

    this.HDiff = (this.Hcur - this.Hset);
    this.Speed = (this.Hcur - this.Hold);
    this.Accel = (this.Speed - this.OldSpeed);
    this.NeedSpeed = (-this.HDiff * 0.01F);
    if (this.NeedSpeed > this.MaxSpeed) this.NeedSpeed = this.MaxSpeed;
    else if (this.NeedSpeed < -this.MaxSpeed) this.NeedSpeed = (-this.MaxSpeed);
    this.NeedAccel = ((this.NeedSpeed - this.Speed) * 0.01F);
    this.dAccel = (this.Accel - this.OldAccel);
    if (this.NeedAccel - this.Accel > this.dAccel) this.A += 0.001F; else this.A -= 0.001F;
    if (this.A < -1.0F) this.A = -1.0F; else if (this.A > 1.0F) this.A = 1.0F;
    return this.A;
  }
}