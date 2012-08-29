package com.maddox.il2.engine;

public class EffSmokeTrail extends Eff3D
{
  protected Eff3DActor NewActor(Loc paramLoc)
  {
    return new EffSmokeTrailActor(this, paramLoc);
  }
  protected EffSmokeTrail() {
    this.cppObj = cNew(); } 
  private native int cNew();

  public EffSmokeTrail(int paramInt) { this.cppObj = paramInt; }

  static {
    GObj.loadNative();
  }
}