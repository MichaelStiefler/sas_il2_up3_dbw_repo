package com.maddox.il2.engine;

import com.maddox.rts.State;
import com.maddox.rts.States;

class EffAnimatedSpriteActor extends Eff3DActor
{
  public void _setIntesity(float paramFloat)
  {
    if (this.states.getState() == 0) {
      ((Eff3D)this.draw).setIntesity(paramFloat);
      if (this.bUseIntensityAsSwitchDraw)
        drawing(paramFloat != 0.0F);
    }
  }

  public EffAnimatedSpriteActor(Eff3D paramEff3D, Loc paramLoc)
  {
    this.draw = paramEff3D;
    if (_isStaticPos)
      this.pos = new ActorPosStaticEff3D(this, paramLoc);
    else
      this.pos = new ActorPosMove(this, paramLoc);
    this.states = new States(new Object[] { new Eff3DActor.Ready(this, this), new _Finish(this) });
    this.states.setState(0);
    drawing(true);
  }
  public EffAnimatedSpriteActor(Eff3D paramEff3D, ActorPos paramActorPos) {
    this.draw = paramEff3D;
    this.pos = paramActorPos;
    this.states = new States(new Object[] { new Eff3DActor.Ready(this, this), new _Finish(this) });
    this.states.setState(0);
    this.flags |= 3;
    paramActorPos.base().pos.addChildren(this);
  }

  class _Finish extends State
  {
    public _Finish(Object arg2)
    {
      super();
    }
    public void begin(int paramInt) {
      EffAnimatedSpriteActor.this.destroy();
    }
  }
}