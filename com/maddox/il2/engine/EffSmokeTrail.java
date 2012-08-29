package com.maddox.il2.engine;

public class EffSmokeTrail extends Eff3D
{
  protected Eff3DActor NewActor(Loc paramLoc)
  {
    return new EffSmokeTrailActor(this, paramLoc);
  }
  protected EffSmokeTrail() {
    this.jdField_cppObj_of_type_Int = cNew(); } 
  private native int cNew();

  public EffSmokeTrail(int paramInt) { this.jdField_cppObj_of_type_Int = paramInt; }

  static {
    GObj.loadNative();
  }
}