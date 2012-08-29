package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapXY16Hash;
import com.maddox.util.HashMapXY16List;
import java.io.PrintStream;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

public class CollideEnvXY extends CollideEnv
{
  public static final int SMALL_STEP = 32;
  public static final int STEP = 96;
  public static final float STEPF = 96.0F;
  public static final float STEPF_2 = 48.0F;
  public static float STATIC_HMAX = 50.0F;
  private static final float MAX_ENEMY_DIST = 6000.0F;
  private boolean bDoCollision = false;

  private int[] indexLineX = new int[100];
  private int[] indexLineY = new int[100];
  private double boundXmin;
  private double boundXmax;
  private double boundYmin;
  private double boundYmax;
  private double boundZmin;
  private double boundZmax;
  private HashMapExt moved = new HashMapExt();
  private HashMapExt current = new HashMapExt();
  private Actor _bulletActor;
  private String _bulletChunk;
  private double _bulletTickOffset;
  private boolean _bulletArcade;
  private AbstractCollection _getSphereLst;
  private Point3d _getSphereCenter;
  private double _getSphereR;
  private Point3d _getLineP0;
  private Point3d _getLineP1 = new Point3d();
  private double _getLineR2;
  private double _getLineLen2;
  private double _getLineDx;
  private double _getLineDy;
  private double _getLineDz;
  private boolean _getLineBOnlySphere;
  private double _getLineU;
  private Actor _getLineA;
  private Point3d _getLineRayHit = new Point3d();
  private Point3d _getLineLandHit = new Point3d();
  private Point3d _getFilteredCenter;
  private ActorFilter _getFilteredFilter;
  private double _getFilteredR;
  private Actor _current;
  private Point3d _currentP;
  private double _currentCollisionR;
  private Point3d _p;
  private Point3d p0 = new Point3d();
  private Vector3d normal = new Vector3d();
  private HashMapXY16Hash mapXY = new HashMapXY16Hash(7);
  private HashMapXY16List lstXY = new HashMapXY16List(7);
  private CollideEnvXYIndex index = new CollideEnvXYIndex();

  public boolean isDoCollision()
  {
    return this.bDoCollision;
  }

  public static final double intersectPointSphere(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7)
  {
    double d = paramDouble7 * paramDouble7;
    if (d >= (paramDouble1 - paramDouble4) * (paramDouble1 - paramDouble4) + (paramDouble2 - paramDouble5) * (paramDouble2 - paramDouble5) + (paramDouble3 - paramDouble6) * (paramDouble3 - paramDouble6)) return 0.0D;
    return -1.0D;
  }

  public static final double intersectLineSphere(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10)
  {
    double d1 = paramDouble10 * paramDouble10;
    double d2 = paramDouble4 - paramDouble1;
    double d3 = paramDouble5 - paramDouble2;
    double d4 = paramDouble6 - paramDouble3;
    double d5 = d2 * d2 + d3 * d3 + d4 * d4;
    if (d5 < 1.0E-006D) {
      if (d1 >= (paramDouble1 - paramDouble7) * (paramDouble1 - paramDouble7) + (paramDouble2 - paramDouble8) * (paramDouble2 - paramDouble8) + (paramDouble3 - paramDouble9) * (paramDouble3 - paramDouble9)) return 0.0D;
      return -1.0D;
    }
    double d6 = ((paramDouble7 - paramDouble1) * d2 + (paramDouble8 - paramDouble2) * d3 + (paramDouble9 - paramDouble3) * d4) / d5;
    if ((d6 >= 0.0D) && (d6 <= 1.0D)) {
      d7 = paramDouble1 + d6 * d2;
      d8 = paramDouble2 + d6 * d3;
      double d9 = paramDouble3 + d6 * d4;
      double d10 = (d7 - paramDouble7) * (d7 - paramDouble7) + (d8 - paramDouble8) * (d8 - paramDouble8) + (d9 - paramDouble9) * (d9 - paramDouble9);
      double d11 = d1 - d10;
      if (d11 < 0.0D)
        return -1.0D;
      d6 -= Math.sqrt(d11 / d5);
      if (d6 < 0.0D) d6 = 0.0D;
      return d6;
    }
    double d7 = (paramDouble4 - paramDouble7) * (paramDouble4 - paramDouble7) + (paramDouble5 - paramDouble8) * (paramDouble5 - paramDouble8) + (paramDouble6 - paramDouble9) * (paramDouble6 - paramDouble9);
    double d8 = (paramDouble1 - paramDouble7) * (paramDouble1 - paramDouble7) + (paramDouble2 - paramDouble8) * (paramDouble2 - paramDouble8) + (paramDouble3 - paramDouble9) * (paramDouble3 - paramDouble9);
    if ((d7 <= d1) || (d8 <= d1)) {
      if (d7 < d8) return 1.0D;
      return 0.0D;
    }
    return -1.0D;
  }

  private void collidePoint()
  {
    makeBoundBox(this._p.x, this._p.y, this._p.z);
    int i = makeIndexLine(this._p);
    Object localObject;
    for (int j = 0; j < i; j++) {
      HashMapExt localHashMapExt = this.mapXY.get(this.indexLineY[j], this.indexLineX[j]);
      if (localHashMapExt != null) {
        Map.Entry localEntry = localHashMapExt.nextEntry(null);
        while (localEntry != null) {
          localObject = (Actor)localEntry.getKey();
          if ((this._current.getOwner() != localObject) && (Actor.isValid((Actor)localObject)) && (!this.current.containsKey(localObject)))
            _collidePoint((Actor)localObject);
          localEntry = localHashMapExt.nextEntry(localEntry);
        }
      }
    }
    double d1 = Engine.cur.land.Hmax(this._p.x, this._p.y);
    if (this.boundZmin < d1 + STATIC_HMAX) {
      for (int k = 0; k < i; k++) {
        localObject = this.lstXY.get(this.indexLineY[k], this.indexLineX[k]);
        if (localObject != null) {
          int m = ((List)localObject).size();
          for (int n = 0; n < m; n++) {
            Actor localActor = (Actor)((List)localObject).get(n);
            if ((this._current.getOwner() != localActor) && (Actor.isValid(localActor)) && (!this.current.containsKey(localActor))) {
              _collidePoint(localActor);
            }
          }
        }
      }
    }
    if ((this._current.isCollideOnLand()) && (this._p.z - d1 <= 0.0D)) {
      double d2 = this._p.z - Engine.cur.land.HQ(this._p.x, this._p.y);
      if (d2 <= 0.0D) {
        long l = Time.tick();
        MsgCollision.post(l, this._current, Engine.actorLand(), "<edge>", "Body");
      }
    }
  }

  private void _collidePoint(Actor paramActor) {
    double d1 = paramActor.collisionR();
    if (d1 > 0.0D) {
      Point3d localPoint3d = paramActor.pos.getAbsPoint();
      double d2 = -1.0D;
      int i = 1;
      this.p0.set(this._p);
      Object localObject;
      if (paramActor.pos.isChanged()) {
        localObject = paramActor.pos.getCurrentPoint();
        if (collideBoundBox(((Point3d)localObject).x, ((Point3d)localObject).y, ((Point3d)localObject).z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1))
          this.p0.x += localPoint3d.x - ((Point3d)localObject).x;
        this.p0.y += localPoint3d.y - ((Point3d)localObject).y;
        this.p0.z += localPoint3d.z - ((Point3d)localObject).z;
        i = (this.p0.x - this._p.x) * (this.p0.x - this._p.x) + (this.p0.y - this._p.y) * (this.p0.y - this._p.y) + (this.p0.z - this._p.z) * (this.p0.z - this._p.z) < 1.0E-006D ? 1 : 0;

        if (i != 0) {
          d2 = intersectPointSphere(this._p.x, this._p.y, this._p.z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1);
        }
        else {
          d2 = intersectLineSphere(this.p0.x, this.p0.y, this.p0.z, this._p.x, this._p.y, this._p.z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1);
        }

      }
      else if (collideBoundBox(localPoint3d.x, localPoint3d.y, localPoint3d.z, d1)) {
        d2 = intersectPointSphere(this._p.x, this._p.y, this._p.z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1);
      }

      if ((d2 >= 0.0D) && (MsgCollisionRequest.on(this._current, paramActor))) {
        localObject = "Body";
        if ((paramActor instanceof ActorMesh)) {
          Mesh localMesh = ((ActorMesh)paramActor).mesh();
          Loc localLoc = paramActor.pos.getAbs();
          if (i != 0) d2 = localMesh.detectCollisionPoint(localLoc, this._p) != 0 ? 0.0D : -1.0D; else
            d2 = localMesh.detectCollisionLine(localLoc, this.p0, this._p);
          if (d2 >= 0.0D) localObject = Mesh.collisionChunk(0);
        }
        if (d2 >= 0.0D) {
          long l = Time.tick() + ()(d2 * Time.tickLenFms());
          if (l >= Time.tickNext()) l = Time.tickNext() - 1L;
          MsgCollision.post2(l, this._current, paramActor, "<edge>", (String)localObject);
        }
      }
    }
    this.current.put(paramActor, null);
  }

  private void collideLine() {
    this._currentP = this._current.pos.getCurrentPoint();
    this._p = this._current.pos.getAbsPoint();
    if ((this._currentP.x - this._p.x) * (this._currentP.x - this._p.x) + (this._currentP.y - this._p.y) * (this._currentP.y - this._p.y) + (this._currentP.z - this._p.z) * (this._currentP.z - this._p.z) < 1.0E-006D)
    {
      collidePoint();
      return;
    }

    makeBoundBox(this._currentP.x, this._currentP.y, this._currentP.z, this._p.x, this._p.y, this._p.z);
    int i = makeIndexLine(this._currentP, this._p);
    if (i == 0)
      System.out.println("CollideEnvXY.collideLine: " + this._current + " very big step moved actor - IGNORED !!!");
    Object localObject;
    for (int j = 0; j < i; j++) {
      HashMapExt localHashMapExt = this.mapXY.get(this.indexLineY[j], this.indexLineX[j]);
      if (localHashMapExt != null) {
        Map.Entry localEntry = localHashMapExt.nextEntry(null);
        while (localEntry != null) {
          localObject = (Actor)localEntry.getKey();
          if ((this._current.getOwner() != localObject) && (Actor.isValid((Actor)localObject)) && (!this.current.containsKey(localObject)))
            _collideLine((Actor)localObject);
          localEntry = localHashMapExt.nextEntry(localEntry);
        }
      }
    }
    double d1 = Engine.cur.land.Hmax(this._p.x, this._p.y);
    if (this.boundZmin < d1 + STATIC_HMAX) {
      for (int k = 0; k < i; k++) {
        localObject = this.lstXY.get(this.indexLineY[k], this.indexLineX[k]);
        if (localObject != null) {
          int m = ((List)localObject).size();
          for (int n = 0; n < m; n++) {
            Actor localActor = (Actor)((List)localObject).get(n);
            if ((this._current.getOwner() != localActor) && (Actor.isValid(localActor)) && (!this.current.containsKey(localActor))) {
              _collideLine(localActor);
            }
          }
        }
      }
    }
    if ((this._current.isCollideOnLand()) && (this._p.z - d1 <= 0.0D)) {
      double d2 = this._p.z - Engine.cur.land.HQ(this._p.x, this._p.y);
      if (d2 <= 0.0D) {
        long l = Time.tick();
        double d3 = this._currentP.z - Engine.cur.land.HQ(this._currentP.x, this._currentP.y);
        if (d3 > 0.0D) {
          double d4 = 1.0D + d2 / (d3 - d2);
          l += ()(d4 * Time.tickLenFms());
          if (l >= Time.tickNext()) l = Time.tickNext() - 1L;
        }
        MsgCollision.post(l, this._current, Engine.actorLand(), "<edge>", "Body");
      }
    }
  }

  private void _collideLine(Actor paramActor) {
    Engine.cur.profile.collideLineAll += 1;
    double d1 = paramActor.collisionR();
    if (d1 > 0.0D) {
      Point3d localPoint3d = paramActor.pos.getAbsPoint();
      double d2 = -1.0D;
      int i = 0;
      this.p0.set(this._currentP);
      Object localObject;
      if (paramActor.pos.isChanged()) {
        localObject = paramActor.pos.getCurrentPoint();
        if (collideBoundBox(((Point3d)localObject).x, ((Point3d)localObject).y, ((Point3d)localObject).z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1)) {
          this.p0.x += localPoint3d.x - ((Point3d)localObject).x;
          this.p0.y += localPoint3d.y - ((Point3d)localObject).y;
          this.p0.z += localPoint3d.z - ((Point3d)localObject).z;
          i = (this.p0.x - this._p.x) * (this.p0.x - this._p.x) + (this.p0.y - this._p.y) * (this.p0.y - this._p.y) + (this.p0.z - this._p.z) * (this.p0.z - this._p.z) < 1.0E-006D ? 1 : 0;

          if (i != 0) {
            d2 = intersectPointSphere(this._p.x, this._p.y, this._p.z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1);
          }
          else {
            d2 = intersectLineSphere(this.p0.x, this.p0.y, this.p0.z, this._p.x, this._p.y, this._p.z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1);
          }
        }

      }
      else if (collideBoundBox(localPoint3d.x, localPoint3d.y, localPoint3d.z, d1)) {
        d2 = intersectLineSphere(this.p0.x, this.p0.y, this.p0.z, this._p.x, this._p.y, this._p.z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1);
      }

      if (d2 >= 0.0D) {
        Engine.cur.profile.collideLineSphere += 1;
        if (MsgCollisionRequest.on(this._current, paramActor)) {
          localObject = "Body";
          if ((paramActor instanceof ActorMesh)) {
            Mesh localMesh = ((ActorMesh)paramActor).mesh();
            Loc localLoc = paramActor.pos.getAbs();
            if (i != 0) d2 = localMesh.detectCollisionPoint(localLoc, this._p) != 0 ? 0.0D : -1.0D; else
              d2 = localMesh.detectCollisionLine(localLoc, this.p0, this._p);
            if (d2 >= 0.0D) localObject = Mesh.collisionChunk(0);
          }
          if (d2 >= 0.0D) {
            Engine.cur.profile.collideLine += 1;
            long l = Time.tick() + ()(d2 * Time.tickLenFms());
            if (l >= Time.tickNext()) l = Time.tickNext() - 1L;
            MsgCollision.post2(l, this._current, paramActor, "<edge>", (String)localObject);
          }
        }
      }
    }
    this.current.put(paramActor, null);
  }

  private int makeIndexLine(Point3d paramPoint3d)
  {
    int i = (int)paramPoint3d.x / 96;
    int j = (int)paramPoint3d.y / 96;
    int k = 1;
    if ((this.indexLineX == null) || (k > this.indexLineX.length)) {
      this.indexLineX = new int[2 * k];
      this.indexLineY = new int[2 * k];
    }
    this.indexLineX[0] = i;
    this.indexLineY[0] = j;
    return k;
  }
  private int makeIndexLine(Point3d paramPoint3d1, Point3d paramPoint3d2) {
    int i = (int)paramPoint3d1.x / 96;
    int j = (int)paramPoint3d1.y / 96;
    int k = Math.abs((int)paramPoint3d2.x / 96 - i) + Math.abs((int)paramPoint3d2.y / 96 - j) + 1;

    if (k > 100) {
      return 0;
    }
    this.indexLineX[0] = i;
    this.indexLineY[0] = j;
    if (k > 1) {
      int m = 1; if (paramPoint3d2.x < paramPoint3d1.x) m = -1;
      int n = 1; if (paramPoint3d2.y < paramPoint3d1.y) n = -1;
      double d1;
      double d2;
      int i1;
      if (Math.abs(paramPoint3d2.x - paramPoint3d1.x) >= Math.abs(paramPoint3d2.y - paramPoint3d1.y)) {
        d1 = Math.abs(paramPoint3d1.y % 96.0D);
        d2 = 96.0D * (paramPoint3d2.y - paramPoint3d1.y) / Math.abs(paramPoint3d2.x - paramPoint3d1.x);
        if (d2 >= 0.0D)
          for (i1 = 1; i1 < k; i1++) {
            if (d1 < 96.0D) { i += m; d1 += d2; } else {
              j += n; d1 -= 96.0D;
            }this.indexLineX[i1] = i; this.indexLineY[i1] = j;
          }
        else
          for (i1 = 1; i1 < k; i1++) {
            if (d1 > 0.0D) { i += m; d1 += d2; } else {
              j += n; d1 += 96.0D;
            }this.indexLineX[i1] = i; this.indexLineY[i1] = j;
          }
      }
      else
      {
        d1 = Math.abs(paramPoint3d1.x % 96.0D);
        d2 = 96.0D * (paramPoint3d2.x - paramPoint3d1.x) / Math.abs(paramPoint3d2.y - paramPoint3d1.y);
        if (d2 >= 0.0D)
          for (i1 = 1; i1 < k; i1++) {
            if (d1 < 96.0D) { j += n; d1 += d2; } else {
              i += m; d1 -= 96.0D;
            }this.indexLineX[i1] = i; this.indexLineY[i1] = j;
          }
        else {
          for (i1 = 1; i1 < k; i1++) {
            if (d1 > 0.0D) { j += n; d1 += d2; } else {
              i += m; d1 += 96.0D;
            }this.indexLineX[i1] = i; this.indexLineY[i1] = j;
          }
        }
      }
    }

    return k;
  }

  private void collideInterface() {
    CollisionInterface localCollisionInterface = (CollisionInterface)this._current;
    if (!localCollisionInterface.collision_isEnabled()) return;
    this._currentP = this._current.pos.getCurrentPoint();
    this._p = this._current.pos.getAbsPoint();
    int i = (int)this._p.x / 96;
    int j = (int)this._p.y / 96;
    this._currentCollisionR = localCollisionInterface.collision_getCylinderR();
    makeBoundBox2D(this._currentP.x, this._currentP.y, this._p.x, this._p.y, this._currentCollisionR);
    HashMapExt localHashMapExt = this.mapXY.get(j, i);
    if (localHashMapExt != null) {
      Map.Entry localEntry = localHashMapExt.nextEntry(null);
      while (localEntry != null) {
        Actor localActor = (Actor)localEntry.getKey();
        if ((Actor.isValid(localActor)) && (!this.current.containsKey(localActor))) {
          double d1 = localActor.collisionR();
          if (d1 > 0.0D) {
            Point3d localPoint3d1 = localActor.pos.getAbsPoint();
            double d2 = -1.0D;
            if (localActor.pos.isChanged()) {
              Point3d localPoint3d2 = localActor.pos.getCurrentPoint();
              if ((collideBoundBox2D(localPoint3d2.x, localPoint3d2.y, localPoint3d1.x, localPoint3d1.y, d1)) && 
                (MsgCollisionRequest.on(this._current, localActor))) {
                localCollisionInterface.collision_processing(localActor);
              }
            }
            else if ((collideBoundBox2D(localPoint3d1.x, localPoint3d1.y, d1)) && 
              (MsgCollisionRequest.on(this._current, localActor))) {
              localCollisionInterface.collision_processing(localActor);
            }

            this.current.put(localActor, null);
          }
        }
        localEntry = localHashMapExt.nextEntry(localEntry);
      }
    }
    this.current.clear();
  }

  private void collideSphere() {
    this._currentP = this._current.pos.getCurrentPoint();
    this._p = this._current.pos.getAbsPoint();
    int i = (int)this._p.x / 96;
    int j = (int)this._p.y / 96;
    this._currentCollisionR = this._current.collisionR();
    if (this._currentCollisionR <= 0.0D) return;
    makeBoundBox(this._p.x, this._p.y, this._p.z, this._currentCollisionR);
    Object localObject1;
    Object localObject2;
    if (this._currentCollisionR <= 32.0D) {
      HashMapExt localHashMapExt = this.mapXY.get(j, i);
      if (localHashMapExt != null) {
        localObject1 = localHashMapExt.nextEntry(null);
        while (localObject1 != null) {
          localObject2 = (Actor)((Map.Entry)localObject1).getKey();
          if ((Actor.isValid((Actor)localObject2)) && (!this.current.containsKey(localObject2)) && (!this.moved.containsKey(localObject2)))
          {
            _collideSphere((Actor)localObject2);
          }localObject1 = localHashMapExt.nextEntry((Map.Entry)localObject1);
        }
      }
    } else {
      this.index.make(this._p, (float)this._currentCollisionR);
      for (int k = 0; k < this.index.count; k++) {
        localObject1 = this.mapXY.get(this.index.y[k], this.index.x[k]);
        if (localObject1 != null) {
          localObject2 = ((HashMapExt)localObject1).nextEntry(null);
          while (localObject2 != null) {
            Actor localActor1 = (Actor)((Map.Entry)localObject2).getKey();
            if ((Actor.isValid(localActor1)) && (!this.current.containsKey(localActor1)) && (!this.moved.containsKey(localActor1)))
            {
              _collideSphere(localActor1);
            }localObject2 = ((HashMapExt)localObject1).nextEntry((Map.Entry)localObject2);
          }
        }
      }
    }
    double d1 = Engine.cur.land.Hmax(this._p.x, this._p.y);
    if (this.boundZmin < d1 + STATIC_HMAX) {
      localObject2 = this.lstXY.get(j, i);
      if (localObject2 != null) {
        int m = ((List)localObject2).size();
        for (int n = 0; n < m; n++) {
          Actor localActor2 = (Actor)((List)localObject2).get(n);
          if ((Actor.isValid(localActor2)) && (!this.current.containsKey(localActor2)) && (!this.moved.containsKey(localActor2))) {
            _collideSphere(localActor2);
          }
        }
      }
    }
    if ((this._current.isCollideOnLand()) && (this._p.z - this._currentCollisionR - d1 <= 0.0D)) {
      double d2 = this._p.z - this._currentCollisionR - Engine.cur.land.HQ(this._p.x, this._p.y);
      if (d2 <= 0.0D) {
        long l = Time.tick();
        double d4;
        if ((this._current instanceof ActorMesh)) {
          Mesh localMesh = ((ActorMesh)this._current).mesh();
          Loc localLoc = this._current.pos.getAbs();
          d4 = Engine.cur.land.EQN(this._p.x, this._p.y, this.normal);
          if (localMesh.detectCollisionPlane(localLoc, this.normal, d4) >= 0.0F)
            MsgCollision.post(l, this._current, Engine.actorLand(), Mesh.collisionChunk(0), "Body");
        } else {
          double d3 = this._currentP.z - this._currentCollisionR - Engine.cur.land.HQ(this._currentP.x, this._currentP.y);
          if (d3 > 0.0D) {
            d4 = 1.0D + d2 / (d3 - d2);
            l += ()(d4 * Time.tickLenFms());
            if (l >= Time.tickNext()) l = Time.tickNext() - 1L;
          }
          MsgCollision.post(l, this._current, Engine.actorLand(), "Body", "Body");
        }
      }
    }
  }

  private void _collideSphere(Actor paramActor) {
    Engine.cur.profile.collideSphereAll += 1;
    double d1 = paramActor.collisionR();
    if (d1 > 0.0D) {
      Point3d localPoint3d = paramActor.pos.getAbsPoint();
      if (collideBoundBox(localPoint3d.x, localPoint3d.y, localPoint3d.z, d1)) {
        double d2 = (this._currentCollisionR + d1) * (this._currentCollisionR + d1);
        double d3 = (this._p.x - localPoint3d.x) * (this._p.x - localPoint3d.x) + (this._p.y - localPoint3d.y) * (this._p.y - localPoint3d.y) + (this._p.z - localPoint3d.z) * (this._p.z - localPoint3d.z);

        if (d3 <= d2) {
          Engine.cur.profile.collideSphereSphere += 1;
          if (MsgCollisionRequest.on(this._current, paramActor)) {
            long l = Time.tick();
            if (((this._current instanceof ActorMesh)) && ((paramActor instanceof ActorMesh))) {
              Loc localLoc1 = this._current.pos.getAbs();
              Loc localLoc2 = paramActor.pos.getAbs();
              Mesh localMesh1 = ((ActorMesh)this._current).mesh();
              Mesh localMesh2 = ((ActorMesh)paramActor).mesh();
              if ((localMesh1 instanceof HierMesh)) {
                if ((localMesh2 instanceof HierMesh)) {
                  if (0 != ((HierMesh)localMesh1).detectCollision(localLoc1, (HierMesh)localMesh2, localLoc2)) {
                    Engine.cur.profile.collideSphere += 1;
                    MsgCollision.post2(l, this._current, paramActor, Mesh.collisionChunk(0), Mesh.collisionChunk(1));
                  }
                }
                else if (0 != ((HierMesh)localMesh1).detectCollision(localLoc1, localMesh2, localLoc2)) {
                  Engine.cur.profile.collideSphere += 1;
                  MsgCollision.post2(l, this._current, paramActor, Mesh.collisionChunk(0), Mesh.collisionChunk(1));
                }
              }
              else if ((localMesh2 instanceof HierMesh)) {
                if (0 != ((HierMesh)localMesh2).detectCollision(localLoc2, localMesh1, localLoc1)) {
                  Engine.cur.profile.collideSphere += 1;
                  MsgCollision.post2(l, paramActor, this._current, Mesh.collisionChunk(0), Mesh.collisionChunk(1));
                }
              }
              else if (0 != localMesh1.detectCollision(localLoc1, localMesh2, localLoc2)) {
                Engine.cur.profile.collideSphere += 1;
                MsgCollision.post2(l, this._current, paramActor, Mesh.collisionChunk(0), Mesh.collisionChunk(1));
              }
            }
            else {
              if (paramActor.pos.isChanged()) {
                localPoint3d = paramActor.pos.getCurrentPoint();
              }
              double d4 = (this._currentP.x - localPoint3d.x) * (this._currentP.x - localPoint3d.x) + (this._currentP.y - localPoint3d.y) * (this._currentP.y - localPoint3d.y) + (this._currentP.z - localPoint3d.z) * (this._currentP.z - localPoint3d.z);

              if (d4 > d2) {
                d2 = Math.sqrt(d2);
                d3 = Math.sqrt(d3);
                d4 = Math.sqrt(d4);

                double d5 = 1.0D - (d2 - d3) / (d4 - d3);
                l += ()(d5 * Time.tickLenFms());
                if (l >= Time.tickNext()) l = Time.tickNext() - 1L;
              }
              Engine.cur.profile.collideSphere += 1;
              MsgCollision.post2(l, this._current, paramActor, "Body", "Body");
            }
          }
        }
      }
    }
    this.current.put(paramActor, null);
  }

  private void makeBoundBox(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
  {
    if (paramDouble1 < paramDouble4) { this.boundXmin = paramDouble1; this.boundXmax = paramDouble4; } else {
      this.boundXmin = paramDouble4; this.boundXmax = paramDouble1;
    }if (paramDouble2 < paramDouble5) { this.boundYmin = paramDouble2; this.boundYmax = paramDouble5; } else {
      this.boundYmin = paramDouble5; this.boundYmax = paramDouble2;
    }if (paramDouble3 < paramDouble6) { this.boundZmin = paramDouble3; this.boundZmax = paramDouble6; } else {
      this.boundZmin = paramDouble6; this.boundZmax = paramDouble3;
    }
  }
  private void makeBoundBox2D(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5) {
    if (paramDouble1 < paramDouble3) { this.boundXmin = (paramDouble1 - paramDouble5); this.boundXmax = (paramDouble3 + paramDouble5); } else {
      this.boundXmin = (paramDouble3 - paramDouble5); this.boundXmax = (paramDouble1 + paramDouble5);
    }if (paramDouble2 < paramDouble4) { this.boundYmin = (paramDouble2 - paramDouble5); this.boundYmax = (paramDouble4 + paramDouble5); } else {
      this.boundYmin = (paramDouble4 - paramDouble5); this.boundYmax = (paramDouble2 + paramDouble5);
    }
  }
  private void makeBoundBox(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    this.boundXmin = (paramDouble1 - paramDouble4);
    this.boundXmax = (paramDouble1 + paramDouble4);
    this.boundYmin = (paramDouble2 - paramDouble4);
    this.boundYmax = (paramDouble2 + paramDouble4);
    this.boundZmin = (paramDouble3 - paramDouble4);
    this.boundZmax = (paramDouble3 + paramDouble4);
  }
  private void makeBoundBox(double paramDouble1, double paramDouble2, double paramDouble3) {
    this.boundXmin = (this.boundXmax = paramDouble1);

    this.boundYmin = (this.boundYmax = paramDouble2);

    this.boundZmin = (this.boundZmax = paramDouble3);
  }

  private boolean collideBoundBox(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    if (paramDouble3 + paramDouble4 < this.boundZmin) return false;
    if (paramDouble3 - paramDouble4 > this.boundZmax) return false;
    if (paramDouble1 + paramDouble4 < this.boundXmin) return false;
    if (paramDouble1 - paramDouble4 > this.boundXmax) return false;
    if (paramDouble2 + paramDouble4 < this.boundYmin) return false;
    return paramDouble2 - paramDouble4 <= this.boundYmax;
  }

  private boolean collideBoundBox2D(double paramDouble1, double paramDouble2, double paramDouble3) {
    if (paramDouble1 + paramDouble3 < this.boundXmin) return false;
    if (paramDouble1 - paramDouble3 > this.boundXmax) return false;
    if (paramDouble2 + paramDouble3 < this.boundYmin) return false;
    return paramDouble2 - paramDouble3 <= this.boundYmax;
  }

  private boolean collideBoundBox(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7) {
    if (paramDouble3 < paramDouble6) {
      if (paramDouble6 + paramDouble7 < this.boundZmin) return false;
      if (paramDouble3 - paramDouble7 > this.boundZmax) return false; 
    }
    else {
      if (paramDouble3 + paramDouble7 < this.boundZmin) return false;
      if (paramDouble6 - paramDouble7 > this.boundZmax) return false;
    }
    if (paramDouble1 < paramDouble4) {
      if (paramDouble4 + paramDouble7 < this.boundXmin) return false;
      if (paramDouble1 - paramDouble7 > this.boundXmax) return false; 
    }
    else {
      if (paramDouble1 + paramDouble7 < this.boundXmin) return false;
      if (paramDouble4 - paramDouble7 > this.boundXmax) return false;
    }
    if (paramDouble2 < paramDouble5) {
      if (paramDouble5 + paramDouble7 < this.boundYmin) return false;
      if (paramDouble2 - paramDouble7 > this.boundYmax) return false; 
    }
    else {
      if (paramDouble2 + paramDouble7 < this.boundYmin) return false;
      if (paramDouble5 - paramDouble7 > this.boundYmax) return false;
    }
    return true;
  }
  private boolean collideBoundBox2D(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5) {
    if (paramDouble1 < paramDouble3) {
      if (paramDouble3 + paramDouble5 < this.boundXmin) return false;
      if (paramDouble1 - paramDouble5 > this.boundXmax) return false; 
    }
    else {
      if (paramDouble1 + paramDouble5 < this.boundXmin) return false;
      if (paramDouble3 - paramDouble5 > this.boundXmax) return false;
    }
    if (paramDouble2 < paramDouble4) {
      if (paramDouble4 + paramDouble5 < this.boundYmin) return false;
      if (paramDouble2 - paramDouble5 > this.boundYmax) return false; 
    }
    else {
      if (paramDouble2 + paramDouble5 < this.boundYmin) return false;
      if (paramDouble4 - paramDouble5 > this.boundYmax) return false;
    }
    return true;
  }

  protected void doCollision(List paramList)
  {
    this.bDoCollision = true;
    int i = paramList.size();
    for (int j = 0; j < i; j++) {
      this._current = ((Actor)paramList.get(j));
      if (Actor.isValid(this._current)) {
        if (this._current.isCollide()) {
          if (this._current.isCollideAsPoint()) {
            collideLine();
          } else {
            this.moved.put(this._current, null);
            collideSphere();
          }
          this.current.clear();
        } else if ((this._current instanceof CollisionInterface)) {
          collideInterface();
        }
      }
    }
    this.moved.clear();
    this._current = null;
    this.bDoCollision = false;
  }

  private void _bulletCollide(Actor paramActor1, Actor paramActor2)
  {
    Engine.cur.profile.collideLineAll += 1;
    double d1 = paramActor1.collisionR();
    if (d1 > 0.0D) {
      Point3d localPoint3d = paramActor1.pos.getAbsPoint();
      double d2 = -1.0D;
      int i = 0;
      this.p0.set(this._currentP);
      Object localObject;
      if (paramActor1.pos.isChanged()) {
        localObject = paramActor1.pos.getCurrentPoint();
        if (collideBoundBox(((Point3d)localObject).x, ((Point3d)localObject).y, ((Point3d)localObject).z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1)) {
          this.p0.x += localPoint3d.x - ((Point3d)localObject).x;
          this.p0.y += localPoint3d.y - ((Point3d)localObject).y;
          this.p0.z += localPoint3d.z - ((Point3d)localObject).z;
          i = (this.p0.x - this._p.x) * (this.p0.x - this._p.x) + (this.p0.y - this._p.y) * (this.p0.y - this._p.y) + (this.p0.z - this._p.z) * (this.p0.z - this._p.z) < 1.0E-006D ? 1 : 0;

          if (i != 0) {
            d2 = intersectPointSphere(this._p.x, this._p.y, this._p.z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1);
          }
          else {
            d2 = intersectLineSphere(this.p0.x, this.p0.y, this.p0.z, this._p.x, this._p.y, this._p.z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1);
          }
        }

      }
      else if (collideBoundBox(localPoint3d.x, localPoint3d.y, localPoint3d.z, d1)) {
        d2 = intersectLineSphere(this.p0.x, this.p0.y, this.p0.z, this._p.x, this._p.y, this._p.z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1);
      }

      if (d2 >= 0.0D) {
        Engine.cur.profile.collideLineSphere += 1;
        localObject = "Body";
        if ((paramActor1 instanceof ActorMesh)) {
          Mesh localMesh = ((ActorMesh)paramActor1).mesh();
          Loc localLoc = paramActor1.pos.getAbs();
          if (i != 0) d2 = localMesh.detectCollisionPoint(localLoc, this._p) != 0 ? 0.0D : -1.0D; else
            d2 = localMesh.detectCollisionLine(localLoc, this.p0, this._p);
          if (d2 >= 0.0D) localObject = Mesh.collisionChunk(0);
        }
        if ((d2 >= 0.0D) && (d2 <= 1.0D)) {
          Engine.cur.profile.collideLine += 1;
          if ((this._bulletActor == null) || (d2 < this._bulletTickOffset)) {
            this._bulletActor = paramActor1;
            this._bulletTickOffset = d2;
            this._bulletChunk = ((String)localObject);
            this._bulletArcade = false;
          }
        }
        else if ((this._bulletArcade) && (paramActor1.getArmy() != paramActor2.getArmy())) {
          if (paramActor1.pos.isChanged()) {
            if (i != 0) {
              d2 = intersectPointSphere(this._p.x, this._p.y, this._p.z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1 / 2.0D);
            }
            else {
              d2 = intersectLineSphere(this.p0.x, this.p0.y, this.p0.z, this._p.x, this._p.y, this._p.z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1 / 2.0D);
            }
          }
          else {
            d2 = intersectLineSphere(this.p0.x, this.p0.y, this.p0.z, this._p.x, this._p.y, this._p.z, localPoint3d.x, localPoint3d.y, localPoint3d.z, d1 / 2.0D);
          }

          if ((d2 >= 0.0D) && (d2 <= 1.0D)) {
            Engine.cur.profile.collideLine += 1;
            if ((this._bulletActor == null) || (d2 < this._bulletTickOffset)) {
              this._bulletActor = paramActor1;
              this._bulletTickOffset = d2;
              this._bulletChunk = "Body";
            }
          }
        }
      }
    }
  }

  private boolean bulletCollide(BulletGeneric paramBulletGeneric)
  {
    this._bulletArcade = ((paramBulletGeneric.flags & 0x40000000) != 0);
    this._currentP = paramBulletGeneric.p0;
    this._p = paramBulletGeneric.p1;
    if ((this._currentP.x - this._p.x) * (this._currentP.x - this._p.x) + (this._currentP.y - this._p.y) * (this._currentP.y - this._p.y) + (this._currentP.z - this._p.z) * (this._currentP.z - this._p.z) < 1.0E-006D)
    {
      return false;
    }
    Actor localActor1 = paramBulletGeneric.gunOwnerBody();

    makeBoundBox(this._currentP.x, this._currentP.y, this._currentP.z, this._p.x, this._p.y, this._p.z);
    int i = makeIndexLine(this._currentP, this._p);
    if (i == 0) {
      System.out.println("CollideEnvXY.doBulletMoveAndCollision: " + paramBulletGeneric + " very big step moved bullet - IGNORED !!!");
    }
    double d1 = Engine.cur.land.Hmax(this._p.x, this._p.y);
    this._bulletActor = null;
    for (int j = 0; j < i; j++) {
      HashMapExt localHashMapExt = this.mapXY.get(this.indexLineY[j], this.indexLineX[j]);
      Object localObject;
      if (localHashMapExt != null) {
        localObject = localHashMapExt.nextEntry(null);
        while (localObject != null) {
          Actor localActor2 = (Actor)((Map.Entry)localObject).getKey();
          if ((localActor1 != null) && (localActor1 != localActor2) && (Actor.isValid(localActor2)))
            _bulletCollide(localActor2, localActor1);
          localObject = localHashMapExt.nextEntry((Map.Entry)localObject);
        }
      }
      if (this.boundZmin < d1 + STATIC_HMAX) {
        localObject = this.lstXY.get(this.indexLineY[j], this.indexLineX[j]);
        if (localObject != null) {
          int k = ((List)localObject).size();
          for (int m = 0; m < k; m++) {
            Actor localActor3 = (Actor)((List)localObject).get(m);
            if ((localActor1 != null) && (localActor1 != localActor3) && (Actor.isValid(localActor3)))
              _bulletCollide(localActor3, localActor1);
          }
        }
      }
      if (this._bulletActor != null) {
        break;
      }
    }
    if (this._p.z - d1 <= 0.0D) {
      double d2 = this._p.z - Engine.cur.land.HQ(this._p.x, this._p.y);
      if (d2 <= 0.0D) {
        double d3 = 0.0D;
        double d4 = this._currentP.z - Engine.cur.land.HQ(this._currentP.x, this._currentP.y);
        if (d4 > 0.0D) {
          d3 = 1.0D + d2 / (d4 - d2);
          if (d3 < 0.0D) d3 = 0.0D;
          if (d3 > 1.0D) d3 = 1.0D;
        }
        if ((this._bulletActor == null) || (d3 < this._bulletTickOffset)) {
          this._bulletActor = Engine.actorLand();
          this._bulletTickOffset = d3;
          this._bulletChunk = "Body";
          this._bulletArcade = false;
        }
      }
    }

    if (this._bulletActor != null) {
      if (((paramBulletGeneric.flags & 0x40000000) != 0) && (this._bulletArcade)) {
        paramBulletGeneric.flags |= 8192;
      }
      long l = Time.tick() + ()(this._bulletTickOffset * Time.tickLenFms());
      if (l >= Time.tickNext()) l = Time.tickNext() - 1L;
      MsgBulletCollision.post(l, this._bulletTickOffset, this._bulletActor, this._bulletChunk, paramBulletGeneric);
      return true;
    }
    return false;
  }

  protected void doBulletMoveAndCollision()
  {
    Object localObject = null;
    BulletGeneric localBulletGeneric1 = Engine.cur.bulletList;
    long l = Time.current();
    float f = Time.tickLenFs();
    while (localBulletGeneric1 != null) {
      if (l < localBulletGeneric1.timeEnd) {
        if (!localBulletGeneric1.bMoved) {
          localBulletGeneric1.move(f);
          localBulletGeneric1.flags &= -4097;
        }
        localBulletGeneric1.bMoved = false;
      } else {
        localBulletGeneric1.timeOut();
        localBulletGeneric1.destroy();
      }
      if ((localBulletGeneric1.isDestroyed()) || (bulletCollide(localBulletGeneric1))) {
        BulletGeneric localBulletGeneric2 = localBulletGeneric1;
        localBulletGeneric1 = localBulletGeneric1.nextBullet;
        if (localObject == null)
          Engine.cur.bulletList = localBulletGeneric1;
        else {
          localObject.nextBullet = localBulletGeneric1;
        }
        localBulletGeneric2.nextBullet = null;
        continue;
      }localObject = localBulletGeneric1;
      localBulletGeneric1 = localBulletGeneric1.nextBullet;
    }

    this._bulletActor = null;
  }

  public void getSphere(AbstractCollection paramAbstractCollection, Point3d paramPoint3d, double paramDouble)
  {
    int i = (int)(paramPoint3d.x - paramDouble) / 96;
    int j = (int)(paramPoint3d.y - paramDouble) / 96;
    int k = (int)(paramPoint3d.x + paramDouble) / 96;
    int m = (int)(paramPoint3d.y + paramDouble) / 96;
    this._getSphereLst = paramAbstractCollection;
    this._getSphereCenter = paramPoint3d;
    this._getSphereR = paramDouble;
    for (int n = j; n <= m; n++) {
      for (int i1 = i; i1 <= k; i1++) {
        HashMapExt localHashMapExt = this.mapXY.get(n, i1);
        if (localHashMapExt != null) {
          localObject = localHashMapExt.nextEntry(null);
          while (localObject != null) {
            Actor localActor1 = (Actor)((Map.Entry)localObject).getKey();
            if ((Actor.isValid(localActor1)) && (!this.current.containsKey(localActor1)))
              _getSphere(localActor1);
            localObject = localHashMapExt.nextEntry((Map.Entry)localObject);
          }
        }
        Object localObject = this.lstXY.get(n, i1);
        if (localObject != null) {
          int i2 = ((List)localObject).size();
          for (int i3 = 0; i3 < i2; i3++) {
            Actor localActor2 = (Actor)((List)localObject).get(i3);
            if ((Actor.isValid(localActor2)) && (!this.current.containsKey(localActor2)))
              _getSphere(localActor2);
          }
        }
      }
    }
    this.current.clear();
  }

  private void _getSphere(Actor paramActor)
  {
    this.current.put(paramActor, null);
    double d1 = paramActor.collisionR();
    Point3d localPoint3d = paramActor.pos.getAbsPoint();
    double d2 = (this._getSphereR + d1) * (this._getSphereR + d1);
    double d3 = (this._getSphereCenter.x - localPoint3d.x) * (this._getSphereCenter.x - localPoint3d.x) + (this._getSphereCenter.y - localPoint3d.y) * (this._getSphereCenter.y - localPoint3d.y) + (this._getSphereCenter.z - localPoint3d.z) * (this._getSphereCenter.z - localPoint3d.z);

    if (d3 <= d2)
      this._getSphereLst.add(paramActor);
  }

  public Actor getLine(Point3d paramPoint3d1, Point3d paramPoint3d2, boolean paramBoolean, Actor paramActor, Point3d paramPoint3d3)
  {
    if ((paramPoint3d1.x - paramPoint3d2.x) * (paramPoint3d1.x - paramPoint3d2.x) + (paramPoint3d1.y - paramPoint3d2.y) * (paramPoint3d1.y - paramPoint3d2.y) + (paramPoint3d1.z - paramPoint3d2.z) * (paramPoint3d1.z - paramPoint3d2.z) < 1.0E-006D)
    {
      return null;
    }
    this._getLineP1.set(paramPoint3d2); paramPoint3d2 = this._getLineP1;
    this._getLineP0 = paramPoint3d1;
    int i = 0;
    while (true) {
      i = makeIndexLine(paramPoint3d1, paramPoint3d2);
      if (i > 0) break;
      paramPoint3d2.interpolate(paramPoint3d1, paramPoint3d2, 0.5D);
    }
    if (Engine.cur.land.HQ(paramPoint3d1.x, paramPoint3d1.y) > paramPoint3d1.z) {
      return null;
    }
    double d1 = paramPoint3d1.z;
    double d2 = paramPoint3d2.z;
    if (d1 > paramPoint3d2.z) { d1 = paramPoint3d2.z; d2 = paramPoint3d1.z; }
    this._getLineDx = (paramPoint3d2.x - paramPoint3d1.x);
    this._getLineDy = (paramPoint3d2.y - paramPoint3d1.y);
    this._getLineDz = (paramPoint3d2.z - paramPoint3d1.z);
    this._getLineLen2 = (this._getLineDx * this._getLineDx + this._getLineDy * this._getLineDy + this._getLineDz * this._getLineDz);

    this._getLineBOnlySphere = paramBoolean;

    for (int j = 0; j < i; j++) {
      int k = this.indexLineX[j];
      int m = this.indexLineY[j];
      HashMapExt localHashMapExt = this.mapXY.get(m, k);
      if (localHashMapExt != null) {
        Map.Entry localEntry = localHashMapExt.nextEntry(null);
        while (localEntry != null) {
          Actor localActor1 = (Actor)localEntry.getKey();
          if ((Actor.isValid(localActor1)) && (localActor1 != paramActor))
            _getLineIntersect(localActor1);
          localEntry = localHashMapExt.nextEntry(localEntry);
        }
      }
      double d3 = Landscape.Hmax(k * 96 + 48.0F, m * 96 + 48.0F);
      Object localObject;
      if (d1 < d3 + STATIC_HMAX) {
        localObject = this.lstXY.get(m, k);
        if (localObject != null) {
          int n = ((List)localObject).size();
          for (int i1 = 0; i1 < n; i1++) {
            Actor localActor2 = (Actor)((List)localObject).get(i1);
            if ((Actor.isValid(localActor2)) && (localActor2 != paramActor)) {
              _getLineIntersect(localActor2);
            }
          }
        }
      }
      if (this._getLineA != null) {
        localObject = this._getLineA; this._getLineA = null;
        this._getLineP1.set(this._getLineRayHit);
        Engine.land(); if (Landscape.rayHitHQ(paramPoint3d1, this._getLineP1, this._getLineLandHit)) break;
        if (paramPoint3d3 != null)
          paramPoint3d3.set(this._getLineRayHit);
        return localObject;
      }

    }

    Engine.land(); if (Landscape.rayHitHQ(paramPoint3d1, this._getLineP1, this._getLineLandHit)) {
      if (paramPoint3d3 != null)
        paramPoint3d3.set(this._getLineLandHit);
      return Engine.actorLand();
    }
    return (Actor)null;
  }
  public Actor getLine(Point3d paramPoint3d1, Point3d paramPoint3d2, boolean paramBoolean, ActorFilter paramActorFilter, Point3d paramPoint3d3) {
    if ((paramPoint3d1.x - paramPoint3d2.x) * (paramPoint3d1.x - paramPoint3d2.x) + (paramPoint3d1.y - paramPoint3d2.y) * (paramPoint3d1.y - paramPoint3d2.y) + (paramPoint3d1.z - paramPoint3d2.z) * (paramPoint3d1.z - paramPoint3d2.z) < 1.0E-006D)
    {
      return null;
    }
    this._getLineP1.set(paramPoint3d2); paramPoint3d2 = this._getLineP1;
    this._getLineP0 = paramPoint3d1;
    int i = 0;
    while (true) {
      i = makeIndexLine(paramPoint3d1, paramPoint3d2);
      if (i > 0) break;
      paramPoint3d2.interpolate(paramPoint3d1, paramPoint3d2, 0.5D);
    }
    if (Engine.cur.land.HQ(paramPoint3d1.x, paramPoint3d1.y) > paramPoint3d1.z) {
      return null;
    }
    double d1 = paramPoint3d1.z;
    double d2 = paramPoint3d2.z;
    if (d1 > paramPoint3d2.z) { d1 = paramPoint3d2.z; d2 = paramPoint3d1.z; }
    this._getLineDx = (paramPoint3d2.x - paramPoint3d1.x);
    this._getLineDy = (paramPoint3d2.y - paramPoint3d1.y);
    this._getLineDz = (paramPoint3d2.z - paramPoint3d1.z);
    this._getLineLen2 = (this._getLineDx * this._getLineDx + this._getLineDy * this._getLineDy + this._getLineDz * this._getLineDz);

    this._getLineBOnlySphere = paramBoolean;

    for (int j = 0; j < i; j++) {
      int k = this.indexLineX[j];
      int m = this.indexLineY[j];
      HashMapExt localHashMapExt = this.mapXY.get(m, k);
      if (localHashMapExt != null) {
        Map.Entry localEntry = localHashMapExt.nextEntry(null);
        while (localEntry != null) {
          Actor localActor1 = (Actor)localEntry.getKey();
          if ((Actor.isValid(localActor1)) && (paramActorFilter.isUse(localActor1, 0.0D)))
            _getLineIntersect(localActor1);
          localEntry = localHashMapExt.nextEntry(localEntry);
        }
      }
      double d3 = Landscape.Hmax(k * 96 + 48.0F, m * 96 + 48.0F);
      Object localObject;
      if (d1 < d3 + STATIC_HMAX) {
        localObject = this.lstXY.get(m, k);
        if (localObject != null) {
          int n = ((List)localObject).size();
          for (int i1 = 0; i1 < n; i1++) {
            Actor localActor2 = (Actor)((List)localObject).get(i1);
            if ((Actor.isValid(localActor2)) && (paramActorFilter.isUse(localActor2, 0.0D))) {
              _getLineIntersect(localActor2);
            }
          }
        }
      }
      if (this._getLineA != null) {
        localObject = this._getLineA; this._getLineA = null;
        this._getLineP1.set(this._getLineRayHit);
        if (paramActorFilter.isUse(Engine.actorLand(), 0.0D)) { Engine.land(); if (Landscape.rayHitHQ(paramPoint3d1, this._getLineP1, this._getLineLandHit)) break; }
        if (paramPoint3d3 != null)
          paramPoint3d3.set(this._getLineRayHit);
        return localObject;
      }

    }

    if (paramActorFilter.isUse(Engine.actorLand(), 0.0D)) { Engine.land(); if (Landscape.rayHitHQ(paramPoint3d1, this._getLineP1, this._getLineLandHit)) {
        if (paramPoint3d3 != null)
          paramPoint3d3.set(this._getLineLandHit);
        return Engine.actorLand();
      } }
    return (Actor)null;
  }

  private void _getLineIntersect(Actor paramActor)
  {
    Point3d localPoint3d = paramActor.pos.getAbsPoint();
    double d1 = paramActor.collisionR();
    double d2 = d1 * d1;
    double d3 = ((localPoint3d.x - this._getLineP0.x) * this._getLineDx + (localPoint3d.y - this._getLineP0.y) * this._getLineDy + (localPoint3d.z - this._getLineP0.z) * this._getLineDz) / this._getLineLen2;
    double d4;
    double d5;
    if ((d3 >= 0.0D) && (d3 <= 1.0D)) {
      d4 = this._getLineP0.x + d3 * this._getLineDx;
      d5 = this._getLineP0.y + d3 * this._getLineDy;
      double d6 = this._getLineP0.z + d3 * this._getLineDz;
      double d7 = (d4 - localPoint3d.x) * (d4 - localPoint3d.x) + (d5 - localPoint3d.y) * (d5 - localPoint3d.y) + (d6 - localPoint3d.z) * (d6 - localPoint3d.z);

      double d8 = d2 - d7;
      if (d8 < 0.0D) {
        d3 = -1.0D;
      } else {
        d3 -= Math.sqrt(d8 / this._getLineLen2);
        if (d3 < 0.0D) d3 = 0.0D; 
      }
    }
    else {
      d4 = (this._getLineP1.x - localPoint3d.x) * (this._getLineP1.x - localPoint3d.x) + (this._getLineP1.y - localPoint3d.y) * (this._getLineP1.y - localPoint3d.y) + (this._getLineP1.z - localPoint3d.z) * (this._getLineP1.z - localPoint3d.z);

      d5 = (this._getLineP0.x - localPoint3d.x) * (this._getLineP0.x - localPoint3d.x) + (this._getLineP0.y - localPoint3d.y) * (this._getLineP0.y - localPoint3d.y) + (this._getLineP0.z - localPoint3d.z) * (this._getLineP0.z - localPoint3d.z);

      if ((d4 <= d2) || (d5 <= d2)) {
        if (d4 < d5) d3 = 1.0D; else
          d3 = 0.0D;
      }
      else d3 = -1.0D;
    }

    if (d3 < 0.0D) return;
    if ((!this._getLineBOnlySphere) && ((paramActor instanceof ActorMesh))) {
      Mesh localMesh = ((ActorMesh)paramActor).mesh();
      Loc localLoc = paramActor.pos.getAbs();
      d3 = localMesh.detectCollisionLine(localLoc, this._getLineP0, this._getLineP1);
      if (d3 < 0.0D)
        return;
    }
    if ((this._getLineA != null) && (d3 > this._getLineU)) return;
    this._getLineA = paramActor;
    this._getLineU = d3;
    this._getLineRayHit.interpolate(this._getLineP0, this._getLineP1, d3);
  }

  public void getFiltered(AbstractCollection paramAbstractCollection, Point3d paramPoint3d, double paramDouble, ActorFilter paramActorFilter)
  {
    int i = (int)(paramPoint3d.x - paramDouble) / 96;
    int j = (int)(paramPoint3d.y - paramDouble) / 96;
    int k = (int)(paramPoint3d.x + paramDouble) / 96;
    int m = (int)(paramPoint3d.y + paramDouble) / 96;
    this._getFilteredCenter = paramPoint3d;
    this._getFilteredFilter = paramActorFilter;
    this._getFilteredR = paramDouble;
    for (int n = j; n <= m; n++) {
      for (int i1 = i; i1 <= k; i1++) {
        HashMapExt localHashMapExt = this.mapXY.get(n, i1);
        if (localHashMapExt != null) {
          localObject = localHashMapExt.nextEntry(null);
          while (localObject != null) {
            Actor localActor1 = (Actor)((Map.Entry)localObject).getKey();
            if ((Actor.isValid(localActor1)) && (!this.current.containsKey(localActor1)) && (!this.moved.containsKey(localActor1)))
              _getFiltered(localActor1);
            localObject = localHashMapExt.nextEntry((Map.Entry)localObject);
          }
        }
        Object localObject = this.lstXY.get(n, i1);
        if (localObject != null) {
          int i2 = ((List)localObject).size();
          for (int i3 = 0; i3 < i2; i3++) {
            Actor localActor2 = (Actor)((List)localObject).get(i3);
            if ((Actor.isValid(localActor2)) && (!this.current.containsKey(localActor2)) && (!this.moved.containsKey(localActor2)))
              _getFiltered(localActor2);
          }
        }
      }
    }
    if (paramAbstractCollection != null) {
      Map.Entry localEntry = this.current.nextEntry(null);
      while (localEntry != null) {
        paramAbstractCollection.add(localEntry.getKey());
        localEntry = this.current.nextEntry(localEntry);
      }
    }
    this.current.clear();
    this.moved.clear();
  }

  private void _getFiltered(Actor paramActor)
  {
    double d1 = paramActor.collisionR();
    Point3d localPoint3d = paramActor.pos.getAbsPoint();
    double d2 = (this._getFilteredR + d1) * (this._getFilteredR + d1);
    double d3 = (this._getFilteredCenter.x - localPoint3d.x) * (this._getFilteredCenter.x - localPoint3d.x) + (this._getFilteredCenter.y - localPoint3d.y) * (this._getFilteredCenter.y - localPoint3d.y) + (this._getFilteredCenter.z - localPoint3d.z) * (this._getFilteredCenter.z - localPoint3d.z);

    if ((d3 <= d2) && (this._getFilteredFilter.isUse(paramActor, d3)))
      this.current.put(paramActor, null);
    else
      this.moved.put(paramActor, null);
  }

  public void getNearestEnemies(Point3d paramPoint3d, double paramDouble, int paramInt, Accumulator paramAccumulator)
  {
    if (paramDouble >= 6000.0D) {
      paramDouble = 6000.0D;
    }

    int i = (int)(paramPoint3d.x - paramDouble) / 96;
    int j = (int)(paramPoint3d.y - paramDouble) / 96;
    int k = (int)(paramPoint3d.x + paramDouble) / 96;
    int m = (int)(paramPoint3d.y + paramDouble) / 96;
    for (int n = j; n <= m; n++) {
      for (int i1 = i; i1 <= k; i1++) {
        HashMapExt localHashMapExt = this.mapXY.get(n, i1);
        int i3;
        double d4;
        if (localHashMapExt != null) {
          localObject = localHashMapExt.nextEntry(null);
          while (localObject != null) {
            Actor localActor1 = (Actor)((Map.Entry)localObject).getKey();
            i3 = localActor1.getArmy();
            if ((i3 != 0) && (i3 != paramInt) && (localActor1.isAlive()) && (!this.current.containsKey(localActor1)))
            {
              this.current.put(localActor1, null);
              double d1 = localActor1.collisionR();
              Point3d localPoint3d1 = localActor1.pos.getAbsPoint();
              double d3 = (paramDouble + d1) * (paramDouble + d1);
              d4 = (paramPoint3d.x - localPoint3d1.x) * (paramPoint3d.x - localPoint3d1.x) + (paramPoint3d.y - localPoint3d1.y) * (paramPoint3d.y - localPoint3d1.y) + (paramPoint3d.z - localPoint3d1.z) * (paramPoint3d.z - localPoint3d1.z);

              if ((d4 <= d3) && 
                (!paramAccumulator.add(localActor1, d4))) {
                this.current.clear();
                return;
              }
            }

            localObject = localHashMapExt.nextEntry((Map.Entry)localObject);
          }
        }

        Object localObject = this.lstXY.get(n, i1);
        if (localObject != null) {
          int i2 = ((List)localObject).size();
          for (i3 = 0; i3 < i2; i3++) {
            Actor localActor2 = (Actor)((List)localObject).get(i3);
            int i4 = localActor2.getArmy();
            if ((i4 == 0) || (i4 == paramInt) || (!localActor2.isAlive()) || (this.current.containsKey(localActor2)))
            {
              continue;
            }
            this.current.put(localActor2, null);
            double d2 = localActor2.collisionR();
            Point3d localPoint3d2 = localActor2.pos.getAbsPoint();
            d4 = (paramDouble + d2) * (paramDouble + d2);
            double d5 = (paramPoint3d.x - localPoint3d2.x) * (paramPoint3d.x - localPoint3d2.x) + (paramPoint3d.y - localPoint3d2.y) * (paramPoint3d.y - localPoint3d2.y) + (paramPoint3d.z - localPoint3d2.z) * (paramPoint3d.z - localPoint3d2.z);

            if ((d5 > d4) || 
              (paramAccumulator.add(localActor2, d5))) continue;
            this.current.clear();
            return;
          }
        }

      }

    }

    this.current.clear();
  }

  public void getNearestEnemiesCyl(Point3d paramPoint3d, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, Accumulator paramAccumulator)
  {
    if (paramDouble1 >= 6000.0D) {
      paramDouble1 = 6000.0D;
    }

    int i = (int)(paramPoint3d.x - paramDouble1) / 96;
    int j = (int)(paramPoint3d.y - paramDouble1) / 96;
    int k = (int)(paramPoint3d.x + paramDouble1) / 96;
    int m = (int)(paramPoint3d.y + paramDouble1) / 96;
    for (int n = j; n <= m; n++) {
      for (int i1 = i; i1 <= k; i1++) {
        HashMapExt localHashMapExt = this.mapXY.get(n, i1);
        int i3;
        double d4;
        double d5;
        if (localHashMapExt != null) {
          localObject = localHashMapExt.nextEntry(null);
          while (localObject != null) {
            Actor localActor1 = (Actor)((Map.Entry)localObject).getKey();
            i3 = localActor1.getArmy();
            if ((i3 != 0) && (i3 != paramInt) && (localActor1.isAlive()) && (!this.current.containsKey(localActor1)))
            {
              this.current.put(localActor1, null);
              double d1 = localActor1.collisionR();
              Point3d localPoint3d1 = localActor1.pos.getAbsPoint();
              double d3 = (paramDouble1 + d1) * (paramDouble1 + d1);
              d4 = (paramPoint3d.x - localPoint3d1.x) * (paramPoint3d.x - localPoint3d1.x) + (paramPoint3d.y - localPoint3d1.y) * (paramPoint3d.y - localPoint3d1.y);

              if (d4 <= d3) {
                d5 = localPoint3d1.z - paramPoint3d.z;
                if ((d5 - d1 <= paramDouble3) && (d5 + d1 >= paramDouble2))
                {
                  if (!paramAccumulator.add(localActor1, d4 + d5 * d5)) {
                    this.current.clear();
                    return;
                  }
                }
              }
            }
            localObject = localHashMapExt.nextEntry((Map.Entry)localObject);
          }
        }

        Object localObject = this.lstXY.get(n, i1);
        if (localObject != null) {
          int i2 = ((List)localObject).size();
          for (i3 = 0; i3 < i2; i3++) {
            Actor localActor2 = (Actor)((List)localObject).get(i3);
            int i4 = localActor2.getArmy();
            if ((i4 == 0) || (i4 == paramInt) || (!localActor2.isAlive()) || (this.current.containsKey(localActor2)))
            {
              continue;
            }
            this.current.put(localActor2, null);
            double d2 = localActor2.collisionR();
            Point3d localPoint3d2 = localActor2.pos.getAbsPoint();
            d4 = (paramDouble1 + d2) * (paramDouble1 + d2);
            d5 = (paramPoint3d.x - localPoint3d2.x) * (paramPoint3d.x - localPoint3d2.x) + (paramPoint3d.y - localPoint3d2.y) * (paramPoint3d.y - localPoint3d2.y);

            if (d5 <= d4) {
              double d6 = localPoint3d2.z - paramPoint3d.z;
              if ((d6 - d2 > paramDouble3) || (d6 + d2 < paramDouble2))
                continue;
              if (!paramAccumulator.add(localActor2, d5 + d6 * d6)) {
                this.current.clear();
                return;
              }
            }
          }
        }
      }

    }

    this.current.clear();
  }

  protected void changedPos(Actor paramActor, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    int i;
    int j;
    int k;
    int m;
    if (paramActor.collisionR() <= 32.0F) {
      i = (int)paramPoint3d1.x / 32;
      j = (int)paramPoint3d1.y / 32;
      k = (int)paramPoint3d2.x / 32;
      m = (int)paramPoint3d2.y / 32;
      if ((i != k) || (j != m)) {
        remove(paramActor, i, j);
        add(paramActor, k, m);
      }
    } else {
      i = (int)(paramPoint3d1.x / 96.0D);
      j = (int)(paramPoint3d1.y / 96.0D);
      k = (int)(paramPoint3d2.x / 96.0D);
      m = (int)(paramPoint3d2.y / 96.0D);
      if ((i != k) || (j != m)) {
        this.index.make(paramPoint3d1, paramActor.collisionR());
        for (int n = 0; n < this.index.count; n++)
          this.mapXY.remove(this.index.y[n], this.index.x[n], paramActor);
        this.index.make(paramPoint3d2, paramActor.collisionR());
        for (n = 0; n < this.index.count; n++)
          this.mapXY.put(this.index.y[n], this.index.x[n], paramActor, null);
      }
    }
  }

  protected void add(Actor paramActor)
  {
    if (paramActor.pos != null) {
      Point3d localPoint3d = paramActor.pos.getCurrentPoint();
      if (paramActor.collisionR() <= 32.0F) {
        add(paramActor, (int)localPoint3d.x / 32, (int)localPoint3d.y / 32);
      } else {
        this.index.make(localPoint3d, paramActor.collisionR());
        for (int i = 0; i < this.index.count; i++)
          this.mapXY.put(this.index.y[i], this.index.x[i], paramActor, null); 
      }
    }
  }

  protected void add(Actor paramActor, int paramInt1, int paramInt2) {
    this.index.make(paramInt1, paramInt2);
    for (int i = 0; i < this.index.count; i++)
      this.mapXY.put(this.index.y[i], this.index.x[i], paramActor, null);
  }

  protected void remove(Actor paramActor)
  {
    if (paramActor.pos != null) {
      Point3d localPoint3d = paramActor.pos.getCurrentPoint();
      if (paramActor.collisionR() <= 32.0F) {
        remove(paramActor, (int)localPoint3d.x / 32, (int)localPoint3d.y / 32);
      } else {
        this.index.make(localPoint3d, paramActor.collisionR());
        for (int i = 0; i < this.index.count; i++)
          this.mapXY.remove(this.index.y[i], this.index.x[i], paramActor); 
      }
    }
  }

  private void remove(Actor paramActor, int paramInt1, int paramInt2) {
    this.index.make(paramInt1, paramInt2);
    for (int i = 0; i < this.index.count; i++)
      this.mapXY.remove(this.index.y[i], this.index.x[i], paramActor);
  }

  protected void changedPosStatic(Actor paramActor, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    removeStatic(paramActor);
    addStatic(paramActor);
  }

  protected void addStatic(Actor paramActor) {
    if (paramActor.pos != null) {
      Point3d localPoint3d = paramActor.pos.getCurrentPoint();
      this.index.make(localPoint3d, paramActor.collisionR());
      for (int i = 0; i < this.index.count; i++)
        this.lstXY.put(this.index.y[i], this.index.x[i], paramActor);
    }
  }

  protected void removeStatic(Actor paramActor) {
    if (paramActor.pos != null) {
      Point3d localPoint3d = paramActor.pos.getCurrentPoint();
      this.index.make(localPoint3d, paramActor.collisionR());
      for (int i = 0; i < this.index.count; i++)
        this.lstXY.remove(this.index.y[i], this.index.x[i], paramActor);
    }
  }

  protected void resetGameClear() {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    this.mapXY.allValues(localArrayList1);
    Object localObject;
    for (int i = 0; i < localArrayList1.size(); i++) {
      localObject = (HashMapExt)localArrayList1.get(i);
      localArrayList2.addAll(((HashMapExt)localObject).keySet());
      Engine.destroyListGameActors(localArrayList2);
    }
    localArrayList1.clear();
    this.lstXY.allValues(localArrayList1);
    for (i = 0; i < localArrayList1.size(); i++) {
      localObject = (ArrayList)localArrayList1.get(i);
      localArrayList2.addAll((Collection)localObject);
      Engine.destroyListGameActors(localArrayList2);
    }
    localArrayList1.clear();
  }

  protected void resetGameCreate() {
    clear();
  }

  protected void clear() {
    this.mapXY.clear();
  }

  class CollideEnvXYIndex
  {
    public int count;
    public int[] x = new int[4];
    public int[] y = new int[4];

    CollideEnvXYIndex() {  }

    public void make(int paramInt1, int paramInt2) { int i = paramInt1 % 3;
      int j = paramInt1 / 3;
      int k = paramInt2 % 3;
      int m = paramInt2 / 3;
      this.x[0] = j;
      this.y[0] = m;
      switch (k) { case 0:
        switch (i) { case 0:
          this.x[1] = (j - 1); this.y[1] = m; this.x[2] = (j - 1); this.y[2] = (m - 1); this.x[3] = j; this.y[3] = (m - 1); this.count = 4; break;
        case 1:
          this.x[1] = j; this.y[1] = (m - 1); this.count = 2; break;
        case 2:
          this.x[1] = (j + 1); this.y[1] = m; this.x[2] = (j + 1); this.y[2] = (m - 1); this.x[3] = j; this.y[3] = (m - 1); this.count = 4;
        }
        break;
      case 1:
        switch (i) { case 0:
          this.x[1] = (j - 1); this.y[1] = m; this.count = 2; break;
        case 1:
          this.count = 1; break;
        case 2:
          this.x[1] = (j + 1); this.y[1] = m; this.count = 2;
        }
        break;
      case 2:
        switch (i) { case 0:
          this.x[1] = (j - 1); this.y[1] = m; this.x[2] = (j - 1); this.y[2] = (m + 1); this.x[3] = j; this.y[3] = (m + 1); this.count = 4; break;
        case 1:
          this.x[1] = j; this.y[1] = (m + 1); this.count = 2; break;
        case 2:
          this.x[1] = (j + 1); this.y[1] = m; this.x[2] = (j + 1); this.y[2] = (m + 1); this.x[3] = j; this.y[3] = (m + 1); this.count = 4;
        }
      }
    }

    public void make(Point3d paramPoint3d, float paramFloat)
    {
      int i = (int)((paramPoint3d.x - paramFloat - 96.0D) / 96.0D);
      int j = (int)((paramPoint3d.x + paramFloat + 96.0D) / 96.0D);
      int k = (int)((paramPoint3d.y - paramFloat - 96.0D) / 96.0D);
      int m = (int)((paramPoint3d.y + paramFloat + 96.0D) / 96.0D);
      int n = j - i + 1;
      int i1 = m - k + 1;
      this.count = (n * i1);
      if (this.count > this.x.length) {
        this.x = new int[this.count];
        this.y = new int[this.count];
      }
      int i2 = 0;
      while (i1-- > 0) {
        int i3 = n;
        int i4 = i;
        while (i3-- > 0) {
          this.x[i2] = (i4++);
          this.y[(i2++)] = k;
        }
        k++;
      }
    }
  }
}