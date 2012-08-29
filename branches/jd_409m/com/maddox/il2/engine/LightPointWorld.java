package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

public class LightPointWorld extends LightPoint
{
  public LightPointWorld()
  {
    Engine.cur.lightEnv.add(this);
  }

  public void setPos(double paramDouble1, double paramDouble2, double paramDouble3) {
    Engine.cur.lightEnv.changedPos(this, paramDouble1, paramDouble2, paramDouble3);
    super.setPos(paramDouble1, paramDouble2, paramDouble3);
  }
  public void setPos(double[] paramArrayOfDouble) {
    setPos(paramArrayOfDouble[0], paramArrayOfDouble[1], paramArrayOfDouble[2]);
  }
  public void setPos(Point3d paramPoint3d) {
    setPos(paramPoint3d.x, paramPoint3d.y, paramPoint3d.z);
  }
  public void setEmit(float paramFloat1, float paramFloat2) {
    Engine.cur.lightEnv.changedEmit(this, paramFloat1, paramFloat2);
    super.setEmit(paramFloat1, paramFloat2);
  }
  public void destroy() {
    if (isDestroyed()) return;
    Engine.cur.lightEnv.remove(this);
    super.destroy();
  }
}