package com.maddox.il2.builder;

import com.maddox.il2.engine.Actor;
import com.maddox.rts.Message;
import java.util.List;

public class Path extends Actor
{
  public static Path player;
  public static int playerNum;
  public double startTime = 0.0D;

  public int renderPoints = 0;

  public int moveType = -1;

  private Object[] points = new Object[1];

  public boolean isDrawing()
  {
    if ((this.flags & 0x1) == 0) return false;
    int i = getArmy();
    return Plugin.builder.conf.bShowArmy[i];
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public Path(Pathes paramPathes) {
    this.flags |= 8192;
    setOwner(paramPathes);
    drawing(true);
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  public PPoint selectPrev(PPoint paramPPoint) {
    this.points = getOwnerAttached(this.points);
    for (int i = 0; i < this.points.length; i++) {
      PPoint localPPoint = (PPoint)this.points[i];
      if (localPPoint == paramPPoint) {
        if (i > 0) return (PPoint)this.points[(i - 1)];
        if (i + 1 < this.points.length) return (PPoint)this.points[(i + 1)];
        return null;
      }
    }
    return null;
  }

  public int pointIndx(PPoint paramPPoint) {
    this.points = getOwnerAttached(this.points);
    for (int i = 0; i < this.points.length; i++) {
      PPoint localPPoint = (PPoint)this.points[i];
      if (localPPoint == paramPPoint)
        return i;
    }
    return -1;
  }

  public int points() {
    return this.ownerAttached.size();
  }

  public PPoint point(int paramInt) {
    return (PPoint)this.ownerAttached.get(paramInt);
  }

  public void computeTimes() {
  }

  public void pointMoved(PPoint paramPPoint) {
    computeTimes();
  }

  public void destroy()
  {
    if (isDestroyed()) return;
    if (this == player) {
      player = null;
      playerNum = 0;
    }
    Object[] arrayOfObject = getOwnerAttached();
    for (int i = 0; i < arrayOfObject.length; i++) {
      Actor localActor = (Actor)arrayOfObject[i];
      if (localActor == null) break;
      localActor.destroy();
      arrayOfObject[i] = null;
    }
    super.destroy();
  }
}