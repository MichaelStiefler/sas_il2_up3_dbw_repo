package com.maddox.il2.objects.bridges;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosStatic;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.LandPlate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.rts.Message;

public class BridgeRoad extends Actor
  implements LandPlate
{
  private Mat mat;
  private int tp;
  private int begX;
  private int begY;
  private int incX;
  private int incY;
  private float offsetKoef;
  private float R;
  private static Point3d p = new Point3d();

  public boolean isStaticPos()
  {
    return true;
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }

  public BridgeRoad(Point3d paramPoint3d, float paramFloat1, Mat paramMat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float paramFloat2)
  {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosStatic(this);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(paramPoint3d);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

    this.draw = new BridgeRoadDraw(null);
    drawing(true);

    this.R = paramFloat1;

    this.mat = paramMat;

    switch (paramInt1) {
    case 0:
      this.tp = 128;
      break;
    case 1:
      this.tp = 32;
      break;
    default:
      this.tp = 64;
    }

    this.begX = paramInt2;
    this.begY = paramInt3;
    this.incX = paramInt4;
    this.incY = paramInt5;

    this.offsetKoef = paramFloat2;
  }

  private class BridgeRoadDraw extends ActorDraw
  {
    private final BridgeRoad this$0;

    private BridgeRoadDraw()
    {
      this.this$0 = this$1;
    }
    public int preRender(Actor paramActor) { this.this$0.pos.getRender(BridgeRoad.p);
      if (!Render.currentCamera().isSphereVisible(BridgeRoad.p, this.this$0.R)) {
        return 0;
      }
      return this.this$0.mat.preRender(); }

    public void render(Actor paramActor)
    {
      World.cur(); World.land().renderBridgeRoad(this.this$0.mat, this.this$0.tp, this.this$0.begX, this.this$0.begY, this.this$0.incX, this.this$0.incY, this.this$0.offsetKoef);
    }

    BridgeRoadDraw(BridgeRoad.1 arg2)
    {
      this();
    }
  }
}