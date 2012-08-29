package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;
import com.maddox.rts.Property;

public class PPoint extends Actor
  implements ActorAlign
{
  public double time = 0.0D;
  public double screenX;
  public double screenY;
  public int screenQuad;

  public Actor getTarget()
  {
    return null;
  }
  public void moveTo(Point3d paramPoint3d) {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(paramPoint3d);
    align();
  }

  public void align() {
    alignPosToLand(0.0D, true);
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public PPoint(Path paramPath, PPoint paramPPoint, Mat paramMat, Point3d paramPoint3d) {
    this.flags |= 8192;
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this);
    if (paramMat != null)
      this.icon = paramMat;
    else {
      IconDraw.create(this);
    }
    setOwnerAfter(paramPath, paramPPoint, true, true, true);
    if (paramPoint3d != null) {
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(paramPoint3d);
      align();
    }
    drawing(true);
  }

  public PPoint(Path paramPath, PPoint paramPPoint, Point3d paramPoint3d) {
    this(paramPath, paramPPoint, (Mat)null, paramPoint3d);
  }

  public PPoint(Path paramPath, PPoint paramPPoint, String paramString, Point3d paramPoint3d) {
    this(paramPath, paramPPoint, IconDraw.get(paramString), paramPoint3d);
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  static
  {
    Property.set(PPoint.class, "iconName", "icons/SelectIcon.mat");
  }
}