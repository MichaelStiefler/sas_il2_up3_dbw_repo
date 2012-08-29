package com.maddox.il2.objects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Message;
import com.maddox.rts.NetObj;
import com.maddox.rts.Time;

public class ActorCrater extends ActorMesh
  implements MsgCollisionRequestListener
{
  public NetObj netOwner;
  public static Actor initOwner = null;

  private static Vector3f normal = new Vector3f();
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if ((paramActor instanceof Aircraft))
      paramArrayOfBoolean[0] = true;
    else
      paramArrayOfBoolean[0] = false;
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }
  public ActorCrater(String paramString, Loc paramLoc, float paramFloat) {
    super(paramString, paramLoc);

    this.pos.getAbs(p, o);
    p.z = Engine.land().HQ(p.x, p.y);
    Engine.land().N(p.x, p.y, normal);
    o.orient(normal);
    this.pos.setAbs(p, o);
    this.pos.reset();

    drawing(true);
    collide(true);
    postDestroy(Time.current() + ()(paramFloat * 1000.0F));
    if ((Actor.isAlive(initOwner)) && 
      ((initOwner instanceof Aircraft)))
      this.netOwner = ((Aircraft)initOwner).netUser();
  }
}