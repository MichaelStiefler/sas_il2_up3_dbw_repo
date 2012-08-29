package com.maddox.il2.objects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Message;

public class Wreck extends ActorHMesh
{
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();
  private static Vector3d tmpv = new Vector3d();

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public boolean isStaticPos() {
    return false;
  }

  public Wreck(HierMesh paramHierMesh, Loc paramLoc)
  {
    super(paramHierMesh, paramLoc);

    setArmy(0);

    collide(false);
    drawing(true);
  }
}