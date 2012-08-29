package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.Front.Marker;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;
import java.util.List;

public class ActorFrontMarker extends Actor
  implements ActorAlign
{
  public Front.Marker m;
  public String i18nKey;

  public void align()
  {
    alignPosToLand(0.0D, true);
    this.m.x = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().x;
    this.m.y = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().y;
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public void destroy() {
    if (this.m == null) return;
    super.destroy();
    int i = Front.markers().indexOf(this.m);
    if (i >= 0) {
      Front.markers().remove(i);
      Front.setMarkersChanged();
    }
    this.m = null;
  }

  public ActorFrontMarker(String paramString, int paramInt, Point3d paramPoint3d) {
    this.m = new Front.Marker();
    this.i18nKey = paramString;
    setArmy(paramInt);
    this.m.army = paramInt;
    this.m._armyMask = (1 << paramInt - 1);
    this.flags |= 8192;
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this);
    IconDraw.create(this);
    if (paramPoint3d != null) {
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(paramPoint3d);
      align();
    }
    this.icon = IconDraw.get("icons/front0.mat");
    drawing(true);
    Front.markers().add(this.m);
    Front.setMarkersChanged();
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }
}