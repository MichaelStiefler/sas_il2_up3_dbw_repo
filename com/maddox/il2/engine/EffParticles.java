package com.maddox.il2.engine;

public class EffParticles extends Eff3D
{
  protected Eff3DActor NewActor(Loc paramLoc)
  {
    return new EffParticlesActor(this, paramLoc);
  }
  protected Eff3DActor NewActor(ActorPos paramActorPos) {
    return new EffParticlesActor(this, paramActorPos);
  }
  protected EffParticles() {
    this.cppObj = cNew(); } 
  private native int cNew();

  public EffParticles(int paramInt) { this.cppObj = paramInt; }

  static {
    GObj.loadNative();
  }
}