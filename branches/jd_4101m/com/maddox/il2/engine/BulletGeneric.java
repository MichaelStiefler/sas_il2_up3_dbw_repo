package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.rts.Destroy;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;

public class BulletGeneric
  implements Destroy
{
  public static final int TRACE_EXIST = -2147483648;
  public static final int ARCADE = 1073741824;
  public static final int INDEX_MASK = 255;
  public static final int TRACE_VISIBLE = 32768;
  public static final int TRACE_LINE = 16384;
  public static final int ARCADED = 8192;
  public static final int FIRST_TICK = 4096;
  public int flags;
  public BulletGeneric nextBullet;
  protected Actor owner;
  protected GunGeneric gun;
  public Eff3DActor effTrail;
  public long timeEnd;
  public boolean bMoved = false;

  public Point3d p0 = new Point3d();

  public Point3d p1 = new Point3d();

  public Vector3d speed = new Vector3d();

  protected static Vector3d dspeed = new Vector3d();

  private static Vector3d tmpVector = new Vector3d();
  private static Loc tmpLoc = new Loc();
  private static Point3d tmpP = new Point3d();

  private static HashMapInt mapLines = new HashMapInt();

  public int indx()
  {
    return this.flags & 0xFF;
  }

  public BulletProperties properties() {
    return this.gun.prop.bullet[indx()];
  }

  public Actor owner() {
    return this.owner;
  }
  public GunGeneric gun() { return this.gun; } 
  public Actor gunOwnerBody() {
    return this.gun.interpolater.actor;
  }

  public void destroy() {
    this.owner = (this.gun = null);
    Eff3DActor.finish(this.effTrail);
    this.effTrail = null;
  }
  public boolean isDestroyed() { return this.gun == null;
  }

  public void move(float paramFloat)
  {
    if (this.gun == null) return;
    Point3d localPoint3d = this.p1; this.p1 = this.p0; this.p0 = localPoint3d;
    dspeed.scale(this.gun.bulletKV[indx()] * paramFloat * this.speed.length(), this.speed);
    dspeed.z += this.gun.bulletAG[indx()] * paramFloat;
    this.speed.add(dspeed);
    this.p1.scaleAdd(paramFloat, this.speed, this.p0);
  }

  public boolean collided(Actor paramActor, String paramString, double paramDouble)
  {
    return true;
  }

  public void timeOut()
  {
  }

  public BulletGeneric(int paramInt, GunGeneric paramGunGeneric, Loc paramLoc, Vector3d paramVector3d, long paramLong)
  {
    this.gun = paramGunGeneric;
    this.owner = paramGunGeneric.getOwner();
    this.p0.set(paramLoc.getPoint());
    this.p1.set(this.p0);
    this.speed.set(paramVector3d);
    this.timeEnd = paramLong;
    this.nextBullet = Engine.cur.bulletList;
    Engine.cur.bulletList = this;
    this.flags = (paramInt & 0xFF);
  }

  public void preRender()
  {
    if (this.effTrail != null)
      this.effTrail.pos.setAbs(this.p0);
    this.flags &= -49153;
    if ((this.flags & 0x80000000) != 0) {
      Point3d localPoint3d = Render.currentCamera().pos.getAbsPoint();
      if ((this.gun._bulletTraceMesh[indx()] != null) && (localPoint3d.distance(this.p0) < 100.0D)) {
        if (this.gun._bulletTraceMesh[indx()].preRender(this.p0) != 0)
          this.flags |= 32768;
      }
      else if (this.gun.prop.bullet[indx()].traceColor != 0)
        this.flags |= 49152;
    }
  }

  public void render()
  {
    if ((this.flags & 0x1000) != 0) return;
    int i = indx();
    if ((this.flags & 0x4000) != 0) {
      tmpVector.sub(this.p1, this.p0);
      tmpVector.normalize();
      tmpVector.scale(this.gun._bulletTraceMeshLen[i]);
      tmpP.interpolate(this.p0, this.p1, Time.tickOffset());
      tmpVector.add(tmpP);
      drawLine(tmpP, tmpVector, this.gun.prop.bullet[i].traceColor);
    } else {
      tmpVector.sub(this.p1, this.p0);
      tmpVector.normalize();
      Orient localOrient = tmpLoc.getOrient();
      localOrient.setAT0(tmpVector);
      tmpP.interpolate(this.p0, this.p1, Time.tickOffset());
      tmpLoc.set(tmpP);
      if (!this.gun._bulletTraceMesh[i].putToRenderArray(tmpLoc)) {
        this.gun._bulletTraceMesh[i].setPos(tmpLoc);
        this.gun._bulletTraceMesh[i].render();
      }
    }
  }

  public static void preRenderAll()
  {
    BulletGeneric localBulletGeneric = Engine.cur.bulletList;
    while (localBulletGeneric != null) {
      if (((localBulletGeneric.flags & 0x80000000) != 0) || (localBulletGeneric.effTrail != null))
        localBulletGeneric.preRender();
      localBulletGeneric = localBulletGeneric.nextBullet;
    }
  }

  public static void renderAll() {
    BulletGeneric localBulletGeneric = Engine.cur.bulletList;
    while (localBulletGeneric != null) {
      if ((localBulletGeneric.flags & 0x8000) != 0)
        localBulletGeneric.render();
      localBulletGeneric = localBulletGeneric.nextBullet;
    }
    flushLines();
  }

  public static void drawLine(Tuple3d paramTuple3d1, Tuple3d paramTuple3d2, int paramInt)
  {
    Lines localLines = (Lines)mapLines.get(paramInt);
    if (localLines == null) {
      localLines = new Lines(null);
      localLines.color = paramInt;
      mapLines.put(paramInt, localLines);
    }
    if (localLines.n >= localLines.coord.length / 6) {
      float[] arrayOfFloat = new float[localLines.coord.length * 2];
      System.arraycopy(localLines.coord, 0, arrayOfFloat, 0, localLines.coord.length);
      localLines.coord = arrayOfFloat;
    }
    int i = localLines.n * 6;
    localLines.coord[(i + 0)] = (float)paramTuple3d1.x; localLines.coord[(i + 1)] = (float)paramTuple3d1.y; localLines.coord[(i + 2)] = (float)paramTuple3d1.z;
    localLines.coord[(i + 0 + 3)] = (float)paramTuple3d2.x; localLines.coord[(i + 1 + 3)] = (float)paramTuple3d2.y; localLines.coord[(i + 2 + 3)] = (float)paramTuple3d2.z;
    localLines.n += 1;
  }

  public static void flushLines() {
    HashMapIntEntry localHashMapIntEntry = mapLines.nextEntry(null);
    int i = 0;
    while (localHashMapIntEntry != null) {
      Lines localLines = (Lines)localHashMapIntEntry.getValue();
      if (localLines.n > 0) {
        if (i == 0) {
          Render.drawBeginLines(0);
          i = 1;
        }
        Render.drawLines(localLines.coord, localLines.n * 2, 1.0F, localLines.color, Render.LineFlags | Mat.BLENDADD, 0);
        localLines.n = 0;
      }
      localHashMapIntEntry = mapLines.nextEntry(localHashMapIntEntry);
    }
    if (i != 0)
      Render.drawEnd();
  }

  private static class Lines
  {
    public int color;
    public int n = 0;
    public float[] coord = new float[6];

    private Lines()
    {
    }

    Lines(BulletGeneric.1 param1)
    {
      this();
    }
  }
}