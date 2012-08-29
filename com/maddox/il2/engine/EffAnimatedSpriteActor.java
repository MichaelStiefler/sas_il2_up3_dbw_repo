package com.maddox.il2.engine;

import com.maddox.rts.State;
import com.maddox.rts.States;

class EffAnimatedSpriteActor extends Eff3DActor
{
  public void _setIntesity(float paramFloat)
  {
    if (this.jdField_states_of_type_ComMaddoxRtsStates.getState() == 0) {
      ((Eff3D)this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw).setIntesity(paramFloat);
      if (this.bUseIntensityAsSwitchDraw)
        drawing(paramFloat != 0.0F);
    }
  }

  public EffAnimatedSpriteActor(Eff3D paramEff3D, Loc paramLoc)
  {
    this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw = paramEff3D;
    if (Eff3DActor._isStaticPos)
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosStaticEff3D(this, paramLoc);
    else
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this, paramLoc);
    this.jdField_states_of_type_ComMaddoxRtsStates = new States(new Object[] { new Eff3DActor.Ready(this, this), new _Finish(this) });
    this.jdField_states_of_type_ComMaddoxRtsStates.setState(0);
    drawing(true);
  }
  public EffAnimatedSpriteActor(Eff3D paramEff3D, ActorPos paramActorPos) {
    this.jdField_draw_of_type_ComMaddoxIl2EngineActorDraw = paramEff3D;
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = paramActorPos;
    this.jdField_states_of_type_ComMaddoxRtsStates = new States(new Object[] { new Eff3DActor.Ready(this, this), new _Finish(this) });
    this.jdField_states_of_type_ComMaddoxRtsStates.setState(0);
    this.flags |= 3;
    paramActorPos.base().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.addChildren(this);
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