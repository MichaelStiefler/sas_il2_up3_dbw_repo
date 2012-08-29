package com.maddox.il2.engine;

import com.maddox.rts.MsgAction;
import com.maddox.rts.States;

class Eff3DActor$2 extends MsgAction
{
  private final Eff3DActor.Ready this$1;

  public void doAction(Object paramObject)
  {
    if ((Eff3DActor.Ready.access$000(this.this$1).jdField_states_of_type_ComMaddoxRtsStates != null) && (Eff3DActor.Ready.access$000(this.this$1).jdField_states_of_type_ComMaddoxRtsStates.getState() == 0))
      Eff3DActor.Ready.access$000(this.this$1).jdField_states_of_type_ComMaddoxRtsStates.setState(1);
  }
}