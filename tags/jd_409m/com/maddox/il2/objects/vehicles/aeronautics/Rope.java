package com.maddox.il2.objects.vehicles.aeronautics;

import com.maddox.JGP.Plane3d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.CollideEnvXY;
import com.maddox.il2.engine.CollisionInterface;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollision;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Message;
import com.maddox.rts.Time;
import java.io.PrintStream;

public class Rope extends Actor
  implements CollisionInterface, MsgCollisionListener
{
  private AeroanchoredGeneric anchor = null;
  private static final int ST_STAY = 0;
  private static final int ST_FALL = 1;
  private static final int ST_DEAD = 2;
  private int st = 0;

  private Mesh ropeMesh = null;
  private float ropeSegLen = 0.0F;

  private Point3d curPos = new Point3d();
  private double height_stay;
  private double height_down;
  private double speedOfFall;
  private String lastColliedChunkName = null;

  private static Loc locStraightUp = new Loc();

  private static Orient o = new Orient();
  private static Point3d p = new Point3d();

  private static Point3d p0 = new Point3d();
  private static Point3d p1 = new Point3d();
  private static Point3d p2 = new Point3d();
  private static Point3d p3 = new Point3d();

  private static Vector3d v = new Vector3d();

  private static Vector3d norm = new Vector3d();
  private static Vector3d e1 = new Vector3d();
  private static Vector3d e2 = new Vector3d();

  private static Plane3d qplane = new Plane3d();
  private static Plane3d bplane = new Plane3d();

  private static float[] lineXYZ = new float[6];

  public boolean collision_isEnabled()
  {
    return this.st == 0;
  }

  public double collision_getCylinderR() {
    return 32.0D;
  }

  public void collision_processing(Actor paramActor)
  {
    if (!(paramActor instanceof Aircraft)) {
      return;
    }

    if (!(paramActor instanceof ActorHMesh)) {
      return;
    }

    Point3d localPoint3d = paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    float f = paramActor.collisionR();

    double d1 = collision_getCylinderR() + f;

    if ((localPoint3d.jdField_z_of_type_Double - d1 > this.height_stay) || (localPoint3d.jdField_z_of_type_Double + d1 < this.height_down))
    {
      return;
    }

    v.sub(localPoint3d, paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getCurrentPoint());

    if (v.lengthSquared() <= 0.0004D)
    {
      return;
    }

    p3.set(this.curPos);
    p2.set(this.curPos);
    p3.jdField_z_of_type_Double = this.height_stay;
    p2.jdField_z_of_type_Double = this.height_down;

    p0.set(p3);
    p1.set(p2);
    p0.add(v);
    p1.add(v);

    if (v.jdField_x_of_type_Double * v.jdField_x_of_type_Double + v.jdField_y_of_type_Double * v.jdField_y_of_type_Double <= 0.0004D)
    {
      if (v.jdField_z_of_type_Double >= 0.0D)
      {
        p1.set(p2);
      } else {
        p0.set(p1);
        p1.set(p3);
      }

      if (CollideEnvXY.intersectLineSphere(p0.jdField_x_of_type_Double, p0.jdField_y_of_type_Double, p0.jdField_z_of_type_Double, p1.jdField_x_of_type_Double, p1.jdField_y_of_type_Double, p1.jdField_z_of_type_Double, localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double, localPoint3d.jdField_z_of_type_Double, f) < 0.0D)
      {
        return;
      }

      Loc localLoc1 = paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs();

      localObject = ((ActorMesh)paramActor).mesh();

      double d3 = ((Mesh)localObject).detectCollisionLine(paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(), p0, p1);

      if ((d3 >= 0.0D) && (d3 <= 1.0D)) {
        long l1 = Time.tick() + ()(d3 * Time.tickLenFms());
        if (l1 >= Time.tickNext()) {
          l1 = Time.tickNext() - 1L;
        }

        MsgCollision.post2(l1, this, paramActor, "<edge>", Mesh.collisionChunk(0));
      }
      return;
    }

    e1.sub(p2, p1);
    e2.sub(p0, p1);
    norm.cross(e1, e2);
    double d2 = norm.length();
    if (d2 < 0.001D)
    {
      System.out.println("***rope: normal");
      return;
    }
    norm.scale(1.0D / d2);
    qplane.set(norm, p1);

    d2 = qplane.deviation(localPoint3d);
    if ((d2 >= f) || (d2 <= -f)) {
      return;
    }

    e2.sub(p1, p0);
    bplane.N.cross(e2, qplane.N);
    bplane.N.normalize();
    bplane.D = (-bplane.N.dot(p0));

    if (bplane.deviation(localPoint3d) > f) {
      return;
    }

    e2.sub(p3, p2);
    bplane.N.cross(e2, qplane.N);
    bplane.N.normalize();
    bplane.D = (-bplane.N.dot(p2));

    if (bplane.deviation(localPoint3d) > f) {
      return;
    }

    Loc localLoc2 = paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs();

    Object localObject = ((ActorHMesh)paramActor).hierMesh();

    int i = ((HierMesh)localObject).detectCollision_Quad_Multi(paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(), p0, p1, p2, p3);

    for (int j = 0; j < i; j++) {
      double d4 = HierMesh.collisionDistMulti(j);
      if ((d4 >= 0.0D) && (d4 <= 1.0D)) {
        String str = HierMesh.collisionNameMulti(j, 0);

        long l2 = Time.tick() + ()(d4 * Time.tickLenFms());
        if (l2 >= Time.tickNext()) {
          l2 = Time.tickNext() - 1L;
        }

        MsgCollision.post2(l2, this, paramActor, "<edge>", str);
      }
    }
  }

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if ((this.st == 0) && (this.anchor != null))
      this.anchor.ropeCollision(paramActor);
  }

  void somebodyKilled(int paramInt)
  {
    if (this.st != 0) {
      return;
    }
    this.st = 1;
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public Rope(AeroanchoredGeneric paramAeroanchoredGeneric, float paramFloat1, float paramFloat2, String paramString)
  {
    this.ropeMesh = new Mesh(paramString);
    this.ropeSegLen = paramFloat2;

    this.anchor = paramAeroanchoredGeneric;

    this.anchor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.curPos);

    this.height_down = Engine.land().HQ(this.curPos.jdField_x_of_type_Double, this.curPos.jdField_y_of_type_Double);
    this.height_stay = (this.height_down + paramFloat1);

    this.st = 0;
    this.curPos.jdField_z_of_type_Double = this.height_stay;

    o.setYPR(this.anchor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient().getYaw(), 0.0F, 0.0F);

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(this);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.curPos, o);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

    locStraightUp.set(this.curPos, o);

    setArmy(0);

    collide(false);
    drawing(true);

    this.draw = new MyDrawer();

    this.speedOfFall = (25.0D * Time.tickLenFs());

    if (interpGet("move") == null)
      interpPut(new Move(), "move", Time.current(), null);
  }

  class MyDrawer extends ActorDraw
  {
    MyDrawer()
    {
    }

    public int preRender(Actor paramActor)
    {
      if (Rope.this.st == 2) {
        return 0;
      }

      Rope.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(Rope.p0);

      double d1 = Rope.p0.jdField_z_of_type_Double - Rope.this.height_down - 0.01D;
      if (d1 <= 0.0D) {
        return 0;
      }

      Rope.p1.jdField_x_of_type_Double = Rope.p0.jdField_x_of_type_Double;
      Rope.p1.jdField_y_of_type_Double = Rope.p0.jdField_y_of_type_Double;
      Rope.p1.jdField_z_of_type_Double = (Rope.p0.jdField_z_of_type_Double - d1 * 0.5D);
      if (!Render.currentCamera().isSphereVisible(Rope.p1, (float)(d1 * 0.5D))) {
        return 0;
      }

      int i = 1 + (int)(d1 / Rope.this.ropeSegLen);

      Rope.p2.jdField_x_of_type_Double = Rope.p0.jdField_x_of_type_Double;
      Rope.p2.jdField_y_of_type_Double = Rope.p0.jdField_y_of_type_Double;
      double d2 = Rope.this.ropeSegLen * 0.5D;

      int j = 0;

      for (int k = 0; k < i; k++) {
        Rope.p2.jdField_z_of_type_Double = (Rope.p0.jdField_z_of_type_Double - k * Rope.this.ropeSegLen - d2);
        Rope.locStraightUp.set(Rope.p2);

        Rope.this.ropeMesh.setPos(Rope.locStraightUp);

        lightUpdate(Rope.locStraightUp, true);
        soundUpdate(paramActor, Rope.locStraightUp);

        j |= Rope.this.ropeMesh.preRender();
      }

      return j;
    }

    public void render(Actor paramActor)
    {
      super.render(paramActor);

      Rope.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getRender(Rope.p0);

      double d1 = Rope.p0.jdField_z_of_type_Double - Rope.this.height_down - 0.01D;
      if (d1 <= 0.0D) {
        return;
      }

      Rope.p1.jdField_x_of_type_Double = Rope.p0.jdField_x_of_type_Double;
      Rope.p1.jdField_y_of_type_Double = Rope.p0.jdField_y_of_type_Double;
      Rope.p1.jdField_z_of_type_Double = (Rope.p0.jdField_z_of_type_Double - d1 * 0.5D);
      if (!Render.currentCamera().isSphereVisible(Rope.p1, (float)(d1 * 0.5D))) {
        return;
      }

      Render.currentCamera().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(Rope.p3);

      int i = 1 + (int)(d1 / Rope.this.ropeSegLen);

      Rope.p2.jdField_x_of_type_Double = Rope.p0.jdField_x_of_type_Double;
      Rope.p2.jdField_y_of_type_Double = Rope.p0.jdField_y_of_type_Double;
      double d2 = Rope.this.ropeSegLen * 0.5D;

      for (int j = 0; j < i; j++) {
        Rope.p2.jdField_z_of_type_Double = (Rope.p0.jdField_z_of_type_Double - j * Rope.this.ropeSegLen - d2);

        if (!Render.currentCamera().isSphereVisible(Rope.p2, (float)d2))
        {
          continue;
        }

        double d3 = Rope.p3.distanceSquared(Rope.p2);
        int k;
        if (d3 < 2500.0D) {
          k = 0; } else {
          if (d3 >= 562500.0D) {
            continue;
          }
          k = 1;
        }

        if (k == 0) {
          Rope.locStraightUp.set(Rope.p2);
          Rope.this.ropeMesh.setPos(Rope.locStraightUp);

          lightUpdate(Rope.locStraightUp, false);
          Render.currentLightEnv().prepareForRender(Rope.locStraightUp.getPoint(), (float)d2);

          Rope.this.ropeMesh.render();
        }
        else {
          d3 = Math.sqrt(d3);
          float f;
          int m;
          if (d3 > 100.0D) {
            f = ((float)d3 - 100.0F) / 650.0F;
            m = (int)(129.0F * (1.0F - f) + 0.0F * f);
          } else {
            f = ((float)d3 - 50.0F) / 50.0F;
            m = (int)(255.0F * (1.0F - f) + 129.0F * f);
          }

          if (m <= 0) {
            continue;
          }
          if (m > 255) {
            m = 255;
          }

          Render.drawBeginLines(0);
          float tmp439_438 = (float)Rope.p2.jdField_x_of_type_Double; Rope.lineXYZ[3] = tmp439_438; Rope.lineXYZ[0] = tmp439_438;
          float tmp457_456 = (float)Rope.p2.jdField_y_of_type_Double; Rope.lineXYZ[4] = tmp457_456; Rope.lineXYZ[1] = tmp457_456;
          Rope.lineXYZ[2] = (float)(Rope.access$800().jdField_z_of_type_Double - Rope.access$700(Rope.this) * 0.5F);
          Rope.lineXYZ[5] = (float)(Rope.access$800().jdField_z_of_type_Double + Rope.access$700(Rope.this) * 0.5F);
          Render.drawLines(Rope.lineXYZ, 2, 1.0F, (tmp457_456 << 24) + 328965, Render.LineFlags, 0);
          Render.drawEnd();
        }
      }
    }
  }

  class Move extends Interpolate
  {
    Move()
    {
    }

    public boolean tick()
    {
      if (Rope.this.st == 1) {
        Rope.this.curPos.jdField_z_of_type_Double -= Rope.this.speedOfFall;
        if (Rope.this.curPos.jdField_z_of_type_Double <= Rope.this.height_down) {
          Rope.this.anchor.ropeDisappeared();
          Rope.access$402(Rope.this, null);
          Rope.access$002(Rope.this, 2);
          Rope.this.drawing(false);
          Rope.this.postDestroy();
          return false;
        }
        Rope.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(Rope.this.curPos);
        return true;
      }

      if (Rope.this.st == 0)
      {
        Rope.this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(Rope.this.curPos);
        return true;
      }

      System.out.println("***anchor: unexpected dead");
      return true;
    }
  }
}