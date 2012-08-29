package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.rts.Destroy;
import com.maddox.rts.ObjState;

public class LightPointActor
  implements Destroy
{
  public LightPoint light;
  public Point3d relPos = new Point3d();

  public boolean isDestroyed() { return (this.light == null) || (this.light.isDestroyed()); } 
  public void destroy() {
    ObjState.destroy(this.light);
  }

  public LightPointActor(LightPoint paramLightPoint) {
    this.light = paramLightPoint;
  }

  public LightPointActor(LightPoint paramLightPoint, Point3d paramPoint3d) {
    this.light = paramLightPoint;
    this.relPos.set(paramPoint3d);
  }
}