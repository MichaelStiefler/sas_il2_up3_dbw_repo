package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.rts.Message;

public class ActorSoundListener extends Actor
{
  private boolean bUseBaseSpeed = true;

  public Point3d absPos = new Point3d();

  public void setUseBaseSpeed(boolean paramBoolean)
  {
    this.bUseBaseSpeed = paramBoolean;
  }
  public boolean isUseBaseSpeed() { return this.bUseBaseSpeed; } 
  public boolean isDrawing() {
    return true;
  }
  public Object getSwitchListener(Message paramMessage) {
    return this;
  }

  public ActorSoundListener() {
    this.draw = new SoundListenerDraw();
    this.pos = new ActorPosMove(this);
    drawing(true);
    setName("ActorSoundListener");
  }

  protected void createActorHashCode()
  {
    makeActorRealHashCode();
  }

  public double getRelRhoSqr(Point3d paramPoint3d)
  {
    double d1 = paramPoint3d.x - this.absPos.x;
    double d2 = paramPoint3d.y - this.absPos.y;
    double d3 = paramPoint3d.z - this.absPos.z;
    return d1 * d1 + d2 * d2 + d3 * d3;
  }

  public void initDraw()
  {
    ((SoundListenerDraw)this.draw).init();
  }
}