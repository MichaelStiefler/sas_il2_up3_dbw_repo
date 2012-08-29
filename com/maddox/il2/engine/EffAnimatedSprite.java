package com.maddox.il2.engine;

public class EffAnimatedSprite extends Eff3D
{
  protected Eff3DActor NewActor(Loc paramLoc)
  {
    return new EffAnimatedSpriteActor(this, paramLoc);
  }
  protected Eff3DActor NewActor(ActorPos paramActorPos) {
    return new EffAnimatedSpriteActor(this, paramActorPos);
  }
  protected EffAnimatedSprite() {
    this.cppObj = cNew(); } 
  private native int cNew();

  public EffAnimatedSprite(int paramInt) { this.cppObj = paramInt; }

  static {
    GObj.loadNative();
  }
}