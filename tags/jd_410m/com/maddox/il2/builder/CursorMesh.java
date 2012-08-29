package com.maddox.il2.builder;

import com.maddox.il2.engine.ActorMesh;
import com.maddox.rts.Message;

class CursorMesh extends ActorMesh
{
  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }
  public CursorMesh(String paramString) { super(paramString);
    this.flags |= 8192;
    drawing(false); }

  protected void createActorHashCode() {
    makeActorRealHashCode();
  }
}