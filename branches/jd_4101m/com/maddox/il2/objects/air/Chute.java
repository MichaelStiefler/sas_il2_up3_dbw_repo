package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Message;
import com.maddox.rts.Time;

public class Chute extends ActorMesh
  implements MsgCollisionRequestListener, MsgCollisionListener, MsgExplosionListener, MsgShotListener
{
  private static final int FPS = 30;
  private static final int PARAUP_START_FRAME = 0;
  private static final int PARAUP_LAST_FRAME = 35;
  private static final int PARAUP_N_FRAMES = 36;
  private static final int PARAUP_CYCLE_TIME = 1166;
  private static final int FALL_START_FRAME = 35;
  private static final int FALL_LAST_FRAME = 76;
  private static final int FALL_N_FRAMES = 42;
  private static final int FALL_CYCLE_TIME = 1366;
  private static final int TANGLE_START_FRAME = 77;
  private static final int TANGLE_LAST_FRAME = 122;
  private static final int TANGLE_N_FRAMES = 46;
  private static final int TANGLE_CYCLE_TIME = 1500;
  private static final int FALLTANGLE_START_FRAME = 45;
  private static final int FALLTANGLE_LAST_FRAME = 76;
  private static final int FALLTANGLE_N_FRAMES = 32;
  private static final int FALLTANGLE_CYCLE_TIME = 1033;
  private static final int ST_PARAUP = 0;
  private static final int ST_FALL = 1;
  private static final int ST_TANGLE = 2;
  private static final int ST_FALLTANGLE = 3;
  private int st = 0;
  private long animStartTime;
  private long disappearTime;
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();

  public void msgCollisionRequest(Actor paramActor, boolean[] paramArrayOfBoolean)
  {
    if (((paramActor instanceof Aircraft)) && (paramActor.isNet()) && (paramActor.isNetMirror()))
    {
      paramArrayOfBoolean[0] = false;
    }

    if (paramActor == getOwner()) {
      paramArrayOfBoolean[0] = false;
    }

    if ((paramActor != null) && ((paramActor instanceof Paratrooper)) && ((this.st == 1) || (this.st == 3)))
    {
      paramArrayOfBoolean[0] = false;
    }
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (this.st != 0) {
      return;
    }
    TangleChute(paramActor);
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = 3;

    if (this.st != 0) {
      return;
    }

    if (paramShot.power <= 0.0F) {
      return;
    }

    if (paramShot.powerType == 1)
    {
      DieChute(paramShot.initiator);
      return;
    }

    if (paramShot.v.length() < 40.0D) {
      return;
    }

    DieChute(paramShot.initiator);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (this.st != 0) {
      return;
    }

    float f1 = 0.01F;
    float f2 = 0.09F;

    if (Explosion.killable(this, paramExplosion.receivedTNT_1meter(this), f1, f2, 0.0F))
    {
      DieChute(paramExplosion.initiator);
    }
  }

  private void DieChute(Actor paramActor)
  {
    TangleChute(paramActor);
  }

  private void TangleChute(Actor paramActor)
  {
    TangleChute(paramActor, true);
  }

  private void TangleChute(Actor paramActor, boolean paramBoolean) {
    tangling();
    if ((getOwner() instanceof Paratrooper)) {
      Paratrooper localParatrooper = (Paratrooper)getOwner();
      if (Actor.isValid(localParatrooper))
        localParatrooper.chuteTangled(paramActor, paramBoolean);
    }
  }

  void tangleChute(Actor paramActor)
  {
    TangleChute(paramActor, false);
  }

  public void landing()
  {
    if ((this.st == 1) || (this.st == 3)) {
      return;
    }

    this.pos.getAbs(p);
    Engine.land(); float f = Landscape.HQ((float)p.x, (float)p.y);

    p.z = f;
    this.st = (this.st == 2 ? 3 : 1);
    this.animStartTime = Time.current();

    this.pos.getAbs(o);

    Vector3f localVector3f = new Vector3f();
    Engine.land().N((float)p.x, (float)p.y, localVector3f);
    o.orient(localVector3f);

    this.pos.setAbs(p, o);

    setOwner(null);
    this.pos.setBase(null, null, true);

    this.disappearTime = (Time.tickNext() + World.Rnd().nextInt(25000, 35000));
  }

  private void tangling()
  {
    if (this.st != 0) {
      return;
    }

    this.st = 2;
    this.animStartTime = (Time.current() - World.Rnd().nextInt(0, 5000));
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  void setAnimFrame(double paramDouble)
  {
    int i;
    int j;
    int k;
    double d;
    float f;
    switch (this.st) {
    case 0:
      i = 0;
      j = 35;
      k = 1166;
      d = paramDouble - this.animStartTime;
      if (d <= 0.0D)
        f = 0.0F;
      else if (d >= k)
        f = 1.0F;
      else {
        f = (float)(d / k);
      }
      break;
    case 1:
      i = 35;
      j = 76;
      k = 1366;
      d = paramDouble - this.animStartTime;
      if (d <= 0.0D)
        f = 0.0F;
      else if (d >= k)
        f = 1.0F;
      else {
        f = (float)(d / k);
      }
      break;
    case 2:
      i = 77;
      j = 122;
      k = 1500;
      d = paramDouble - this.animStartTime;
      d %= k;
      if (d < 0.0D) {
        d += k;
      }
      f = (float)(d / k);
      break;
    default:
      i = 45;
      j = 76;
      k = 1033;
      d = paramDouble - this.animStartTime;
      if (d <= 0.0D)
        f = 0.0F;
      else if (d >= k)
        f = 1.0F;
      else {
        f = (float)(d / k);
      }

    }

    mesh().setFrameFromRange(i, j, f);
  }

  static String GetMeshName()
  {
    return "3do/humans/Paratroopers/Chute/mono.sim";
  }

  public Chute(Actor paramActor) {
    super(GetMeshName());

    setOwner(paramActor);

    this.pos.setBase(paramActor, null, false);
    this.pos.resetAsBase();
    setArmy(0);

    this.st = 0;
    this.animStartTime = Time.tick();

    collide(true);
    this.draw = new ChuteDraw(null);
    drawing(true);

    if (!interpEnd("move"))
      interpPut(new Move(), "move", Time.current(), null);
  }

  class Move extends Interpolate
  {
    Move()
    {
    }

    public boolean tick()
    {
      if ((Chute.this.st == 1) || (Chute.this.st == 3)) {
        long l = Time.tickNext() - Chute.this.animStartTime;
        if (Time.current() >= Chute.this.disappearTime) {
          Chute.this.postDestroy();
          return false;
        }
      }
      Chute.this.setAnimFrame(Time.tickNext());

      return true;
    }
  }

  private class ChuteDraw extends ActorMeshDraw
  {
    private final Chute this$0;

    private ChuteDraw()
    {
      this.this$0 = this$1;
    }
    public int preRender(Actor paramActor) { this.this$0.setAnimFrame(Time.current());
      return super.preRender(paramActor);
    }

    ChuteDraw(Chute.1 arg2)
    {
      this();
    }
  }
}