package com.maddox.il2.objects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.Message;
import com.maddox.rts.Time;

public class ActorSnapToLand extends ActorMesh
{
  private float scale = 1.0F;
  private float dScale = 0.0F;
  private float ascale = 1.0F;
  private float adScale = 0.0F;
  private Mat mat;
  private static Vector3f normal = new Vector3f();
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();

  public boolean isStaticPos()
  {
    return true;
  }

  public ActorSnapToLand(String paramString, boolean paramBoolean, Loc paramLoc, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    this(paramString, paramBoolean, paramLoc, 1.0F, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
  }
  protected void createActorHashCode() {
    makeActorRealHashCode();
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }
  public ActorSnapToLand(String paramString, boolean paramBoolean, Loc paramLoc, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    super(paramString, paramLoc);
    Object localObject;
    if (Config.isUSE_RENDER()) {
      this.mat = mesh().material(0);

      if (paramBoolean) {
        localObject = (Mat)this.mat.Clone();
        if (localObject != null) {
          this.mat = ((Mat)localObject);
          mesh().materialReplace(0, this.mat);
        }
      }

      this.mat.setLayer(0);
    }

    if (paramFloat6 > 0.0F) {
      postDestroy(Time.current() + ()(paramFloat6 * 1000.0F));
      localObject = null;
      int i;
      if (paramFloat2 != paramFloat3) {
        i = (int)(paramFloat6 / Time.tickLenFs());
        this.scale = paramFloat2;
        if (i > 0) {
          this.dScale = ((paramFloat3 - paramFloat2) / i);
          localObject = new Scaler();
          interpPut((Interpolate)localObject, "scaler", Time.current(), null);
        }
      }
      if (paramFloat4 != paramFloat5) {
        i = (int)(paramFloat6 / Time.tickLenFs());
        this.ascale = paramFloat4;
        if (i > 0) {
          this.adScale = ((paramFloat5 - paramFloat4) / i);
          if (localObject == null) {
            localObject = new Scaler();
            interpPut((Interpolate)localObject, "scaler", Time.current(), null);
          }
        }
      }
    } else {
      this.scale = paramFloat2;
      this.ascale = paramFloat4;
    }
    if (Config.isUSE_RENDER()) {
      if (this.scale != 1.0F) mesh().setScale(this.scale);
      if (this.ascale != 1.0F) this.mat.set(10, this.ascale);

    }

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(p, o);
    p.z = (Engine.land().HQ(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double) + paramFloat1);
    Engine.land().N(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double, normal);
    o.orient(normal);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(p, o);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

    drawing(true);
  }
  class Scaler extends Interpolate {
    Scaler() {
    }
    public boolean tick() { if (Config.isUSE_RENDER()) {
        if (ActorSnapToLand.this.dScale != 0.0F) {
          ActorSnapToLand.access$116(ActorSnapToLand.this, ActorSnapToLand.this.dScale);
          ActorSnapToLand.this.mesh().setScale(ActorSnapToLand.this.scale);
        }
        if (ActorSnapToLand.this.adScale != 0.0F) {
          ActorSnapToLand.access$316(ActorSnapToLand.this, ActorSnapToLand.this.adScale);
          ActorSnapToLand.this.mat.set(10, ActorSnapToLand.this.ascale);
        }
      }
      return true;
    }
  }
}