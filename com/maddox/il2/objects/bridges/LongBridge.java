package com.maddox.il2.objects.bridges;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.ai.ground.RoadSegment;
import com.maddox.il2.ai.ground.UnitInterface;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosStatic;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.LandPlate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MeshShared;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.trains.Train;
import com.maddox.rts.Message;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.BitSet;

public class LongBridge extends Actor
  implements LandPlate
{
  public static final int BRIDGE_HIGHWAY = 0;
  public static final int BRIDGE_COUNTRY = 1;
  public static final int BRIDGE_RAIL = 2;
  private static final int N_BRIDGE_TYPES = 3;
  private static final float BRIDGE_SEGM_MAX_LIFE = 24.0F;
  private static final float BRIDGE_WOODSEGM_MAX_LIFE = 2.1F;
  private static final float BRIDGE_SEGM_IGN_TNT = 0.05F;
  private static final float BRIDGE_WOODSEGM_IGN_TNT = 0.033F;
  private int type;
  public int bodyMaterial;
  private int bridgeIdx;
  private int begX;
  private int begY;
  private int endX;
  private int endY;
  private int nMidCells;
  private float offsetKoef;
  private int incX;
  private int incY;
  private int dirOct;
  private float width;
  private float height;
  private float heightEnd;
  private float lengthEnd;
  private Mat mat;
  private BridgeRoad[] bridgeRoad = new BridgeRoad[2];
  private ArrayList travellers;
  private Actor supervisor;

  public int type()
  {
    return this.type;
  }

  public boolean isStaticPos()
  {
    return true;
  }
  public int bridgeIdx() { return this.bridgeIdx; }

  private static String nameOfBridgeByIdx(int paramInt) {
    return " Bridge" + paramInt;
  }

  public static LongBridge getByIdx(int paramInt) {
    return (LongBridge)Actor.getByName(nameOfBridgeByIdx(paramInt));
  }

  public boolean isUsableFor(Actor paramActor)
  {
    return (this.supervisor == null) || (this.supervisor == paramActor);
  }

  public void setSupervisor(Actor paramActor) {
    this.supervisor = paramActor;
  }
  public void resetSupervisor(Actor paramActor) {
    if (this.supervisor == paramActor)
      this.supervisor = null;
  }

  public float getWidth()
  {
    return this.width;
  }

  private String SegmentMeshName(boolean paramBoolean, int paramInt) {
    return new String("3do/bridges/" + (this.type == 1 ? "country/" : this.type == 0 ? "highway/" : "rail/") + ((this.dirOct & 0x1) == 0 ? "short/" : "long/") + (paramBoolean ? "mid/" : "end/") + (paramInt == 3 ? "mono3" : paramInt == 0 ? "mono1" : "mono2") + ".sim");
  }

  private static int ComputeOctDirection(int paramInt1, int paramInt2)
  {
    int i;
    if (paramInt1 > 0)
      i = paramInt2 < 0 ? 1 : paramInt2 > 0 ? 7 : 0;
    else if (paramInt1 < 0)
      i = paramInt2 < 0 ? 3 : paramInt2 > 0 ? 5 : 4;
    else {
      i = paramInt2 > 0 ? 6 : 2;
    }
    return i;
  }

  private Orient ComputeSegmentOrient(boolean paramBoolean) {
    float f = this.dirOct * 45.0F;
    if (!paramBoolean) {
      f += 180.0F;
      if (f >= 360.0F) {
        f -= 360.0F;
      }
    }
    Orient localOrient = new Orient();
    localOrient.setYPR(f, 0.0F, 0.0F);
    return localOrient;
  }

  private Point3d ComputeSegmentPos3d(int paramInt)
  {
    float f1;
    float f2;
    int i;
    if (paramInt == 0) {
      f1 = this.begX;
      f2 = this.begY;
      i = 1;
    } else if (paramInt == 1 + 2 * this.nMidCells) {
      f1 = this.begX + this.incX * (1 + this.nMidCells);
      f2 = this.begY + this.incY * (1 + this.nMidCells);
      i = 0;
    } else {
      f1 = this.begX + this.incX * (paramInt + 1 >> 1);
      f2 = this.begY + this.incY * (paramInt + 1 >> 1);
      i = (paramInt & 0x1) == 0 ? 1 : 0;
    }
    float f3 = this.offsetKoef + (i != 0 ? 0.25F : -0.25F);
    float f4 = f1 + 0.5F + this.incX * f3;
    float f5 = f2 + 0.5F + this.incY * f3;
    Point3d localPoint3d = new Point3d();
    localPoint3d.jdField_x_of_type_Double = World.land().PIX2WORLDX(f4);
    localPoint3d.jdField_y_of_type_Double = World.land().PIX2WORLDY(f5);
    localPoint3d.jdField_z_of_type_Double = 0.0D;
    return localPoint3d;
  }

  private float SegmentLength() {
    return 200.0F * ((this.dirOct & 0x1) == 0 ? 0.5F : 0.7071F);
  }

  void ComputeSegmentKeyPoints(int paramInt, Point3d paramPoint3d1, Point3d paramPoint3d2, Point3d paramPoint3d3, Point3d paramPoint3d4)
  {
    int i = paramInt == 0 ? 1 : 0;
    int j = paramInt == 1 + 2 * this.nMidCells ? 1 : 0;
    int k = (i == 0) && (j == 0) ? 1 : 0;

    paramPoint3d2.set(ComputeSegmentPos3d(0));
    paramPoint3d4.set(ComputeSegmentPos3d(1));

    float f1 = (float)(paramPoint3d4.jdField_x_of_type_Double - paramPoint3d2.jdField_x_of_type_Double);
    float f2 = (float)(paramPoint3d4.jdField_y_of_type_Double - paramPoint3d2.jdField_y_of_type_Double);
    float f3 = 1.0F / (float)Math.sqrt(f1 * f1 + f2 * f2);

    paramPoint3d1.set(ComputeSegmentPos3d(paramInt));
    paramPoint3d2.set(paramPoint3d1);
    paramPoint3d4.set(paramPoint3d1);
    paramPoint3d2.sub(0.5D * f1, 0.5D * f2, 0.0D);
    paramPoint3d4.add(0.5D * f1, 0.5D * f2, 0.0D);

    if (i != 0) {
      paramPoint3d3.set(paramPoint3d2);
      paramPoint3d3.add(f1 * f3 * this.lengthEnd, f2 * f3 * this.lengthEnd, 0.0D);
      paramPoint3d2.jdField_z_of_type_Double = 0.0D;
      paramPoint3d3.jdField_z_of_type_Double = this.heightEnd;
      paramPoint3d4.jdField_z_of_type_Double = this.height;
    } else if (j != 0) {
      paramPoint3d3.set(paramPoint3d4);
      paramPoint3d3.sub(f1 * f3 * this.lengthEnd, f2 * f3 * this.lengthEnd, 0.0D);
      paramPoint3d2.jdField_z_of_type_Double = this.height;
      paramPoint3d3.jdField_z_of_type_Double = this.heightEnd;
      paramPoint3d4.jdField_z_of_type_Double = 0.0D;
    } else {
      paramPoint3d3.set(paramPoint3d1);
      paramPoint3d2.jdField_z_of_type_Double = this.height;
      paramPoint3d3.jdField_z_of_type_Double = this.height;
      paramPoint3d4.jdField_z_of_type_Double = this.height;
    }
  }

  void ShowSegmentExplosion(BridgeSegment paramBridgeSegment, int paramInt1, int paramInt2)
  {
    Point3d localPoint3d1 = new Point3d();
    Point3d localPoint3d2 = new Point3d();
    Point3d localPoint3d3 = new Point3d();
    Point3d localPoint3d4 = new Point3d();
    ComputeSegmentKeyPoints(paramInt1, localPoint3d1, localPoint3d2, localPoint3d3, localPoint3d4);
    Point3d localPoint3d5;
    Point3d localPoint3d6;
    if (paramInt2 == 0) {
      localPoint3d5 = localPoint3d2;
      localPoint3d6 = localPoint3d3;
    } else {
      localPoint3d5 = localPoint3d3;
      localPoint3d6 = localPoint3d4;
    }

    Explosions.ExplodeBridge(localPoint3d5, localPoint3d6, this.width);
  }

  public int NumStateBits()
  {
    return 2 * (1 + this.nMidCells + 1);
  }

  public BitSet GetStateOfSegments()
  {
    Object[] arrayOfObject = getOwnerAttached();
    BitSet localBitSet = new BitSet(2 * arrayOfObject.length);
    for (int i = 0; i < arrayOfObject.length; i++) {
      boolean bool1 = ((BridgeSegment)arrayOfObject[i]).IsDead(0);
      boolean bool2 = ((BridgeSegment)arrayOfObject[i]).IsDead(1);
      if (bool1) {
        localBitSet.set(i * 2 + 0);
      }
      if (bool2) {
        localBitSet.set(i * 2 + 1);
      }
    }
    return localBitSet;
  }

  public void SetStateOfSegments(BitSet paramBitSet)
  {
    Object[] arrayOfObject = getOwnerAttached();

    for (int i = 0; i < arrayOfObject.length; i++) {
      bool1 = paramBitSet.get(i * 2 + 0);
      j = paramBitSet.get(i * 2 + 1);
      if ((bool1) && (i > 0)) {
        paramBitSet.set((i - 1) * 2 + 1);
      }
      if ((j != 0) && (i < arrayOfObject.length - 1)) {
        paramBitSet.set((i + 1) * 2 + 0);
      }

    }

    boolean bool1 = false;
    for (int j = 0; j < arrayOfObject.length; j++) {
      boolean bool2 = paramBitSet.get(j * 2 + 0);
      boolean bool3 = paramBitSet.get(j * 2 + 1);
      if ((bool2) || (bool3)) bool1 = true;
      ((BridgeSegment)arrayOfObject[j]).ForcePartState(0, !bool2);
      ((BridgeSegment)arrayOfObject[j]).ForcePartState(1, !bool3);
    }
    setDiedFlag(bool1);
  }

  public void BeLive()
  {
    BitSet localBitSet = new BitSet(NumStateBits());
    SetStateOfSegments(localBitSet);
  }

  void SetSegmentDamageState(boolean paramBoolean, BridgeSegment paramBridgeSegment, int paramInt, float paramFloat1, float paramFloat2, Actor paramActor)
  {
    int i = (paramFloat2 <= 0.0F ? 2 : 0) + (paramFloat1 <= 0.0F ? 1 : 0);

    int j = paramInt == 0 ? 1 : 0;
    boolean bool1 = paramInt == 1 + 2 * this.nMidCells;
    boolean bool2 = (j == 0) && (!bool1);

    boolean bool3 = bool1;
    if ((bool2) && (i == 2)) {
      bool3 = true;
    }
    Orient localOrient = ComputeSegmentOrient(bool3);
    paramBridgeSegment.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localOrient);
    paramBridgeSegment.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

    paramBridgeSegment.setMesh(MeshShared.get(SegmentMeshName(bool2, i)));

    paramBridgeSegment.collide(true);
    paramBridgeSegment.drawing(true);

    if ((i != 0) && (isAlive())) {
      World.onActorDied(this, paramActor);
    }

    if ((i != 0) && 
      (this.travellers.size() > 0))
    {
      arrayOfObject = this.travellers.toArray();
      for (k = 0; k < arrayOfObject.length; k++) {
        if ((arrayOfObject[k] instanceof ChiefGround))
          ((ChiefGround)arrayOfObject[k]).BridgeSegmentDestroyed(this.bridgeIdx, paramInt, paramActor);
        else if ((arrayOfObject[k] instanceof Train))
          ((Train)arrayOfObject[k]).BridgeSegmentDestroyed(this.bridgeIdx, paramInt, paramActor);
        else {
          ((UnitInterface)arrayOfObject[k]).absoluteDeath(paramActor);
        }

      }

    }

    if (!paramBoolean) {
      return;
    }

    if (i == 0) {
      return;
    }

    Object[] arrayOfObject = getOwnerAttached();
    int k = 1 + 2 * this.nMidCells + 1;

    Actor localActor = paramActor;
    if (((i & 0x1) != 0) && (paramInt > 0)) {
      ((BridgeSegment)arrayOfObject[(paramInt - 1)]).ForcePartDestroing(1, localActor);
    }
    if (((i & 0x2) != 0) && (paramInt < k - 1)) {
      ((BridgeSegment)arrayOfObject[(paramInt + 1)]).ForcePartDestroing(0, localActor);
    }

    if ((j != 0) && ((i & 0x1) != 0)) {
      ((BridgeSegment)arrayOfObject[paramInt]).ForcePartDestroing(1, localActor);
    }
    if ((bool1) && ((i & 0x2) != 0))
      ((BridgeSegment)arrayOfObject[paramInt]).ForcePartDestroing(0, localActor);
  }

  public void destroy()
  {
    Object[] arrayOfObject = getOwnerAttached();

    for (int i = 0; i < arrayOfObject.length; i++) {
      ((BridgeSegment)arrayOfObject[i]).destroy();
    }

    for (int j = 0; j < 2; j++) {
      if (Actor.isValid(this.bridgeRoad[j])) {
        this.bridgeRoad[j].destroy();
        this.bridgeRoad[j] = null;
      }

    }

    super.destroy();
  }

  public static void AddTraveller(int paramInt, Actor paramActor)
  {
    if ((!(paramActor instanceof UnitInterface)) && (!(paramActor instanceof ChiefGround)) && (!(paramActor instanceof Train)))
    {
      System.out.println("Error: traveller type");
      return;
    }
    LongBridge localLongBridge = getByIdx(paramInt >> 16);
    if (localLongBridge == null) {
      return;
    }
    if (localLongBridge.travellers.indexOf(paramActor) < 0)
      localLongBridge.travellers.add(paramActor);
  }

  public static void DelTraveller(int paramInt, Actor paramActor)
  {
    LongBridge localLongBridge = getByIdx(paramInt >> 16);
    if (localLongBridge == null) {
      return;
    }
    int i = localLongBridge.travellers.indexOf(paramActor);
    if (i >= 0)
      localLongBridge.travellers.remove(i);
  }

  public static void AddSegmentsToRoadPath(int paramInt, ArrayList paramArrayList, float paramFloat1, float paramFloat2)
  {
    getByIdx(paramInt).AddSegmentsToRoadPath(paramArrayList, paramFloat1, paramFloat2);
  }

  private void AddSegmentsToRoadPath(ArrayList paramArrayList, float paramFloat1, float paramFloat2)
  {
    Point3d localPoint3d1 = new Point3d();
    Point3d localPoint3d2 = new Point3d();
    Point3d localPoint3d3 = new Point3d();
    Point3d localPoint3d4 = new Point3d();
    int i = 1 + 2 * this.nMidCells;

    int j = paramArrayList.size();

    ComputeSegmentKeyPoints(0, localPoint3d1, localPoint3d2, localPoint3d3, localPoint3d4);
    paramArrayList.add(new RoadSegment((float)localPoint3d2.jdField_x_of_type_Double, (float)localPoint3d2.jdField_y_of_type_Double, (float)localPoint3d2.jdField_z_of_type_Double, this.width * 0.5F, 0.0D, this.bridgeIdx, 0));

    paramArrayList.add(new RoadSegment((float)localPoint3d3.jdField_x_of_type_Double, (float)localPoint3d3.jdField_y_of_type_Double, (float)localPoint3d3.jdField_z_of_type_Double, this.width * 0.5F, 0.0D, this.bridgeIdx, 0));

    for (int k = 1; k < 1 + 2 * this.nMidCells; k++) {
      ComputeSegmentKeyPoints(k, localPoint3d1, localPoint3d2, localPoint3d3, localPoint3d4);
      paramArrayList.add(new RoadSegment((float)localPoint3d2.jdField_x_of_type_Double, (float)localPoint3d2.jdField_y_of_type_Double, (float)localPoint3d2.jdField_z_of_type_Double, this.width * 0.5F, 0.0D, this.bridgeIdx, k));
    }

    ComputeSegmentKeyPoints(i, localPoint3d1, localPoint3d2, localPoint3d3, localPoint3d4);
    paramArrayList.add(new RoadSegment((float)localPoint3d2.jdField_x_of_type_Double, (float)localPoint3d2.jdField_y_of_type_Double, (float)localPoint3d2.jdField_z_of_type_Double, this.width * 0.5F, 0.0D, this.bridgeIdx, i));

    paramArrayList.add(new RoadSegment((float)localPoint3d3.jdField_x_of_type_Double, (float)localPoint3d3.jdField_y_of_type_Double, (float)localPoint3d3.jdField_z_of_type_Double, this.width * 0.5F, 0.0D, this.bridgeIdx, i));

    paramArrayList.add(new RoadSegment((float)localPoint3d4.jdField_x_of_type_Double, (float)localPoint3d4.jdField_y_of_type_Double, (float)localPoint3d4.jdField_z_of_type_Double, this.width * 0.5F, 0.0D, -1, -1));

    Point3d localPoint3d6 = new Point3d(paramFloat1, paramFloat2, 0.0D);

    ComputeSegmentKeyPoints(i, localPoint3d1, localPoint3d2, localPoint3d3, localPoint3d4);
    Point3d localPoint3d5 = new Point3d(localPoint3d4);
    localPoint3d5.jdField_z_of_type_Double = 0.0D;

    ComputeSegmentKeyPoints(0, localPoint3d1, localPoint3d2, localPoint3d3, localPoint3d4);
    localPoint3d2.jdField_z_of_type_Double = 0.0D;

    int m = localPoint3d6.distanceSquared(localPoint3d5) < localPoint3d6.distanceSquared(localPoint3d2) ? 1 : 0;

    if (m != 0) {
      int n = 2 + this.nMidCells * 2 + 1 + 2;
      Object localObject3;
      for (int i1 = 0; i1 < n / 2; i1++) {
        Object localObject2 = paramArrayList.get(j + i1);
        localObject3 = paramArrayList.get(j + n - 1 - i1);
        paramArrayList.set(j + i1, localObject3);
        paramArrayList.set(j + n - 1 - i1, localObject2);
      }
      for (int i2 = 0; i2 < n - 1; i2++) {
        localObject3 = (RoadSegment)paramArrayList.get(j + i2);
        RoadSegment localRoadSegment = (RoadSegment)paramArrayList.get(j + i2 + 1);
        ((RoadSegment)localObject3).br = localRoadSegment.br;
        ((RoadSegment)localObject3).brSg = localRoadSegment.brSg;
        if (i2 == n - 2) {
          localRoadSegment.br = null;
          localRoadSegment.brSg = null;
        }

      }

    }

    Object localObject1 = paramArrayList.get(paramArrayList.size() - 1);
    ((RoadSegment)localObject1).setZ(-1.0D);
    paramArrayList.set(paramArrayList.size() - 1, localObject1);
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public LongBridge(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat)
  {
    this.bridgeIdx = paramInt1;

    setName(nameOfBridgeByIdx(this.bridgeIdx));

    this.supervisor = null;

    if (Math.abs(paramFloat) > 0.4F) {
      System.out.println("LongBridge: too big offset");
      paramFloat = 0.0F;
    }
    this.offsetKoef = paramFloat;

    switch (paramInt2) {
    case 128:
      this.type = 0;
      this.bodyMaterial = 0;
      break;
    case 32:
      this.type = 1;
      this.bodyMaterial = 3;
      break;
    case 64:
      this.type = 2;
      this.bodyMaterial = 2;
      break;
    default:
      System.out.println("LongBridge: wrong type " + this.type);
    }

    if (Config.isUSE_RENDER())
    {
      World.cur(); LandConf localLandConf = World.land().config;
      this.mat = Mat.New("maps/_Tex/" + (this.type == 1 ? localLandConf.road : this.type == 0 ? localLandConf.highway : localLandConf.rail) + ".mat");
    }

    this.begX = paramInt3;
    this.begY = paramInt4;
    this.endX = paramInt5;
    this.endY = paramInt6;

    int i = this.endX - this.begX;
    int j = this.endY - this.begY;
    if (j == 0) {
      this.nMidCells = (Math.abs(i) - 1);
      this.incY = 0;
      this.incX = (i > 0 ? 1 : -1);
    } else if (i == 0) {
      this.nMidCells = (Math.abs(j) - 1);
      this.incX = 0;
      this.incY = (j > 0 ? 1 : -1);
    } else {
      if (Math.abs(i) != Math.abs(j)) {
        System.out.println("LongBridge: wrong direction");
      }
      this.nMidCells = (Math.abs(j) - 1);
      this.incX = (i > 0 ? 1 : -1);
      this.incY = (j > 0 ? 1 : -1);
    }
    this.dirOct = ComputeOctDirection(this.incX, this.incY);

    if (this.nMidCells < 0) {
      System.out.println("LongBridge: zero length");
    }

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosStatic(this);

    Object localObject1 = ComputeSegmentOrient(false);

    Point3d localPoint3d1 = ComputeSegmentPos3d(0);
    Point3d localPoint3d2 = ComputeSegmentPos3d(1 + 2 * this.nMidCells);
    localPoint3d1.add(localPoint3d2);
    localPoint3d1.scale(0.5D);

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localPoint3d1, (Orient)localObject1);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();

    this.draw = new LongBridgeDraw(null);
    drawing(false);
    float f1;
    float f2;
    if (this.type != 1) {
      f1 = 24.0F;
      f2 = 0.05F;
    } else {
      f1 = 2.1F;
      f2 = 0.033F;
    }

    int k = 0;

    Point3d localPoint3d3 = ComputeSegmentPos3d(k);
    localObject1 = new BridgeSegment(this, this.bridgeIdx, k++, f1, f2, localPoint3d3, this.dirOct);

    for (int m = 0; m < this.nMidCells; m++) {
      localPoint3d3 = ComputeSegmentPos3d(k);
      new BridgeSegment(this, this.bridgeIdx, k++, f1, f2, localPoint3d3, this.dirOct);

      localPoint3d3 = ComputeSegmentPos3d(k);
      new BridgeSegment(this, this.bridgeIdx, k++, f1, f2, localPoint3d3, this.dirOct);
    }

    localPoint3d3 = ComputeSegmentPos3d(k);
    new BridgeSegment(this, this.bridgeIdx, k++, f1, f2, localPoint3d3, this.dirOct);

    Object localObject2 = ((BridgeSegment)localObject1).mesh();

    Object localObject3 = new Matrix4d();

    int n = ((Mesh)localObject2).hookFind("Top");
    ((Mesh)localObject2).hookMatrix(n, (Matrix4d)localObject3);
    this.height = (float)((Matrix4d)localObject3).m23;
    this.width = (2.0F * Math.abs((float)((Matrix4d)localObject3).m13));

    n = ((Mesh)localObject2).hookFind("Mid");
    ((Mesh)localObject2).hookMatrix(n, (Matrix4d)localObject3);
    this.heightEnd = (float)((Matrix4d)localObject3).m23;
    this.lengthEnd = (-(float)((Matrix4d)localObject3).m03);
    this.lengthEnd += 0.5F * SegmentLength();

    this.travellers = new ArrayList();

    localObject2 = ComputeSegmentPos3d(0);
    Point3d localPoint3d4 = ComputeSegmentPos3d(1);
    localObject3 = new Vector3d(localPoint3d4);
    ((Vector3d)localObject3).sub((Tuple3d)localObject2);
    localPoint3d4 = ComputeSegmentPos3d(1 + 2 * this.nMidCells);

    float f3 = 2.0F * (float)((Vector3d)localObject3).length();
    float f4 = f3 * 0.25F;
    f3 *= 1.4F;
    f3 *= 0.5F;
    ((Vector3d)localObject3).normalize();
    ((Vector3d)localObject3).scale(f3);

    ((Point3d)localObject2).sub((Tuple3d)localObject3);

    localPoint3d4.add((Tuple3d)localObject3);

    float f5 = (float)Math.sqrt(f3 * f3 + f4 * f4);

    this.bridgeRoad[0] = new BridgeRoad((Point3d)localObject2, f5, this.mat, this.type, this.begX, this.begY, this.incX, this.incY, this.offsetKoef);
    this.bridgeRoad[1] = new BridgeRoad(localPoint3d4, f5, this.mat, this.type, this.endX, this.endY, -this.incX, -this.incY, -this.offsetKoef);
  }

  private class LongBridgeDraw extends ActorDraw
  {
    private final LongBridge this$0;

    private LongBridgeDraw()
    {
      this.this$0 = this$1;
    }
    public int preRender(Actor paramActor) {
      Point3d localPoint3d = new Point3d();
      this.this$0.pos.getRender(localPoint3d);
      float f = (float)Math.sqrt((this.this$0.endX - this.this$0.begX) * (this.this$0.endX - this.this$0.begX) + (this.this$0.endY - this.this$0.begY) * (this.this$0.endY - this.this$0.begY));

      f *= 0.5F;
      f += 1.988F;
      f *= 200.0F;
      f += 500.0F;

      if (!Render.currentCamera().isSphereVisible(localPoint3d, f)) {
        return 0;
      }

      return this.this$0.mat.preRender();
    }

    public void render(Actor paramActor)
    {
      int i;
      switch (this.this$0.type) {
      case 0:
        i = 128;
        break;
      case 1:
        i = 32;
        break;
      default:
        i = 64;
      }

      Point3d localPoint3d1 = this.this$0.ComputeSegmentPos3d(0);
      Point3d localPoint3d2 = this.this$0.ComputeSegmentPos3d(1 + 2 * this.this$0.nMidCells);
      float f1 = 284.0F;
      float f2 = f1 * 0.25F + f1 * 1.4F;

      f2 += 500.0F;

      if (Render.currentCamera().isSphereVisible(localPoint3d1, f2)) {
        World.cur(); World.land().renderBridgeRoad(this.this$0.mat, i, this.this$0.begX, this.this$0.begY, this.this$0.incX, this.this$0.incY, this.this$0.offsetKoef);
      }

      if (Render.currentCamera().isSphereVisible(localPoint3d2, f2)) {
        World.cur(); World.land().renderBridgeRoad(this.this$0.mat, i, this.this$0.endX, this.this$0.endY, -this.this$0.incX, -this.this$0.incY, -this.this$0.offsetKoef);
      }
    }

    LongBridgeDraw(LongBridge.1 arg2)
    {
      this();
    }
  }
}