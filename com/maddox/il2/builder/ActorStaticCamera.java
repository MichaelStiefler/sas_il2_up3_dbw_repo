package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;

public class ActorStaticCamera extends Actor
  implements ActorAlign
{
  public int h = 100;

  public void align() {
    alignPosToLand(0.0D, true);
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public ActorStaticCamera(Point3d paramPoint3d) {
    this.flags |= 8192;
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(paramPoint3d);
    align();
    drawing(true);
    this.icon = IconDraw.get("icons/camera.mat");
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }
}