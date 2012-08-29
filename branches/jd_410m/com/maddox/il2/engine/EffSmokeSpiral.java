package com.maddox.il2.engine;

public class EffSmokeSpiral extends Eff3D
{
  protected Eff3DActor NewActor(Loc paramLoc)
  {
    return new EffSmokeSpiralActor(this, paramLoc);
  }
  protected Eff3DActor NewActor(ActorPos paramActorPos) {
    return new EffSmokeSpiralActor(this, paramActorPos);
  }
  protected EffSmokeSpiral() {
    this.cppObj = cNew(); } 
  private native int cNew();

  public EffSmokeSpiral(int paramInt) { this.cppObj = paramInt; }

  static {
    GObj.loadNative();
  }
}