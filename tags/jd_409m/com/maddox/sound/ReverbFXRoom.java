package com.maddox.sound;

public class ReverbFXRoom extends BaseObject
{
  protected float minAttn;
  protected float attn;
  protected float prevAttn;

  public ReverbFXRoom(float paramFloat)
  {
    this.minAttn = paramFloat;
    this.attn = 1.0F;
    this.prevAttn = 1.0F;
  }

  public void set(float paramFloat)
  {
    this.attn = (this.minAttn + (1.0F - this.minAttn) * paramFloat);
  }

  protected void tick(Reverb paramReverb)
  {
    if (this.prevAttn != this.attn) {
      paramReverb.set(100, this.attn);
      this.prevAttn = this.attn;
    }
  }
}