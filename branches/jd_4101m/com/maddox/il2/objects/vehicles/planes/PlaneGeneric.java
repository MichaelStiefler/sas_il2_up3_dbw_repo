package com.maddox.il2.objects.vehicles.planes;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Line2f;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point2f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.TableFunctions;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.I18N;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.TableFunction2;
import java.io.IOException;
import java.io.PrintStream;

public abstract class PlaneGeneric extends ActorHMesh
  implements MsgExplosionListener, MsgShotListener, Prey, Obstacle, ActorAlign
{
  private PlaneProperties prop = null;

  public String country = null;

  private int dying = 0;
  static final int DYING_NONE = 0;
  static final int DYING_DEAD = 1;
  private static PlaneProperties constr_arg1 = null;
  private static ActorSpawnArg constr_arg2 = null;

  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();
  private static Vector3d tmpv = new Vector3d();

  private static int[] pnti = new int[3];
  private static Matrix4d M4 = new Matrix4d();

  public static final double Rnd(double paramDouble1, double paramDouble2)
  {
    return World.Rnd().nextDouble(paramDouble1, paramDouble2);
  }
  public static final float Rnd(float paramFloat1, float paramFloat2) {
    return World.Rnd().nextFloat(paramFloat1, paramFloat2);
  }
  private boolean RndB(float paramFloat) {
    return World.Rnd().nextFloat(0.0F, 1.0F) < paramFloat;
  }

  private static final long SecsToTicks(float paramFloat) {
    long l = ()(0.5D + paramFloat / Time.tickLenFs());
    return l < 1L ? 1L : l;
  }

  public boolean isStaticPos()
  {
    return true;
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = 2;

    if (this.dying != 0) {
      return;
    }

    if (paramShot.power <= 0.0F) {
      return;
    }

    if ((isNetMirror()) && (paramShot.isMirage())) {
      return;
    }

    if (paramShot.powerType == 1) {
      if (RndB(0.15F)) {
        return;
      }

      Die(paramShot.initiator, 0, true);
      return;
    }

    float f1 = this.prop.PANZER * Rnd(0.93F, 1.07F);

    float f2 = this.prop.fnShotPanzer.Value(paramShot.power, f1);

    if ((f2 < 1000.0F) && ((f2 <= 1.0F) || (RndB(1.0F / f2))))
      Die(paramShot.initiator, 0, true);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (this.dying != 0) {
      return;
    }

    if ((isNetMirror()) && (paramExplosion.isMirage())) {
      return;
    }

    if (paramExplosion.power <= 0.0F) {
      return;
    }

    if (paramExplosion.powerType == 1) {
      if (TankGeneric.splintersKill(paramExplosion, this.prop.fnShotPanzer, Rnd(0.0F, 1.0F), Rnd(0.0F, 1.0F), this, 0.6F, 0.0F, this.prop.PANZER, this.prop.PANZER, this.prop.PANZER, this.prop.PANZER, this.prop.PANZER, this.prop.PANZER))
      {
        Die(paramExplosion.initiator, 0, true);
      }
      return;
    }

    if ((paramExplosion.powerType == 2) && (paramExplosion.chunkName != null)) {
      Die(paramExplosion.initiator, 0, true);
      return;
    }
    float f1;
    if (paramExplosion.chunkName != null)
      f1 = 0.5F * paramExplosion.power;
    else {
      f1 = paramExplosion.receivedTNTpower(this);
    }
    f1 *= Rnd(0.95F, 1.05F);

    float f2 = this.prop.PANZER;

    float f3 = this.prop.fnExplodePanzer.Value(f1, f2);

    if ((f3 < 1000.0F) && ((f3 <= 1.0F) || (RndB(1.0F / f3))))
      Die(paramExplosion.initiator, 0, true);
  }

  private void ShowExplode(float paramFloat)
  {
    if (paramFloat > 0.0F) {
      paramFloat = Rnd(paramFloat, paramFloat * 1.6F);
    }
    Explosions.runByName(this.prop.explodeName, this, "", "", paramFloat);
  }

  private void Die(Actor paramActor, short paramShort, boolean paramBoolean)
  {
    if (this.dying != 0) {
      return;
    }

    if (paramShort <= 0) {
      if (isNetMirror()) {
        send_DeathRequest(paramActor);
        return;
      }

      paramShort = 1;
    }

    this.dying = 1;
    World.onActorDied(this, paramActor);

    activateMesh(false);
    Align(false, true);
    if (paramBoolean) {
      ShowExplode(17.0F);
    }

    if (paramBoolean)
      send_DeathCommand(paramActor);
  }

  public void destroy()
  {
    if (isDestroyed())
      return;
    super.destroy();
  }

  public Object getSwitchListener(Message paramMessage) {
    return this;
  }

  protected PlaneGeneric()
  {
    this(constr_arg1, constr_arg2);
  }

  private PlaneGeneric(PlaneProperties paramPlaneProperties, ActorSpawnArg paramActorSpawnArg)
  {
    this.prop = paramPlaneProperties;

    paramActorSpawnArg.setStationary(this);

    this.country = paramActorSpawnArg.country;
    try
    {
      activateMesh(true);
    } catch (RuntimeException localRuntimeException) {
      super.destroy();
      throw localRuntimeException;
    }

    collide(true);
    drawing(true);

    createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);

    if ((this.prop.height == 0.0F) && (this.prop.pitch == 0.0F)) {
      pnti[0] = hierMesh().hookFind("_ClipLGear");
      pnti[1] = hierMesh().hookFind("_ClipRGear");
      pnti[2] = hierMesh().hookFind("_ClipCGear");
      String str = Property.stringValue(this.prop.clazz, "FlightModel", null);
      SectFile localSectFile = FlightModelMain.sectFile(str);
      if ((pnti[0] >= 0) && (pnti[1] >= 0) && (pnti[2] >= 0) && (localSectFile.get("Gear", "FromIni", 0) == 0))
      {
        hierMesh().hookMatrix(pnti[2], M4);
        double d1 = M4.m03;
        double d2 = M4.m23;
        hierMesh().hookMatrix(pnti[0], M4);
        double d3 = M4.m03;
        double d4 = M4.m23;
        hierMesh().hookMatrix(pnti[1], M4);
        d3 = (d3 + M4.m03) * 0.5D;
        d4 = (d4 + M4.m23) * 0.5D;

        double d5 = d3 - d1;
        double d6 = d4 - d2;
        this.prop.pitch = (-Geom.RAD2DEG((float)Math.atan2(d6, d5)));
        if (d5 < 0.0D) this.prop.pitch += 180.0F;
        Line2f localLine2f = new Line2f();
        localLine2f.set(new Point2f((float)d3, (float)d4), new Point2f((float)d1, (float)d2));
        this.prop.height = localLine2f.distance(new Point2f(0.0F, 0.0F));
      } else {
        this.prop.height = localSectFile.get("Gear", "H", -0.5F);
        this.prop.pitch = localSectFile.get("Gear", "Pitch", -0.5F);
      }
    }

    Align(true, false);
  }

  public void activateMesh(boolean paramBoolean)
  {
    if (paramBoolean) {
      localObject1 = Regiment.findFirst(this.country, getArmy());
      localObject2 = Aircraft.getPropertyMesh(this.prop.clazz, ((Regiment)localObject1).country());
      setMesh((String)localObject2);
      Aircraft.prepareMeshCamouflage((String)localObject2, hierMesh());
      PaintScheme localPaintScheme = Aircraft.getPropertyPaintScheme(this.prop.clazz, ((Regiment)localObject1).country());
      localPaintScheme.prepareNum(this.prop.clazz, hierMesh(), (Regiment)localObject1, (int)(Math.random() * 3.0D), (int)(Math.random() * 3.0D), (int)(Math.random() * 98.0D + 1.0D));
    }

    Object localObject1 = Aircraft.partNames();
    Object localObject2 = hierMesh();
    for (int i = 1; (i < 10) && 
      (((HierMesh)localObject2).chunkFindCheck("Pilot" + i + "_D0") >= 0); i++)
    {
      ((HierMesh)localObject2).chunkVisible("Pilot" + i + "_D0", false);
      if (((HierMesh)localObject2).chunkFindCheck("Head" + i + "_D0") >= 0) {
        ((HierMesh)localObject2).chunkVisible("Head" + i + "_D0", false);
      }
      if (((HierMesh)localObject2).chunkFindCheck("HMask" + i + "_D0") >= 0) {
        ((HierMesh)localObject2).chunkVisible("HMask" + i + "_D0", false);
      }
      if (((HierMesh)localObject2).chunkFindCheck("Pilot" + i + "a_D0") >= 0) {
        ((HierMesh)localObject2).chunkVisible("Pilot" + i + "a_D0", false);
      }
      if (((HierMesh)localObject2).chunkFindCheck("Head" + i + "a_D0") >= 0) {
        ((HierMesh)localObject2).chunkVisible("Head" + i + "a_D0", false);
      }
      if (((HierMesh)localObject2).chunkFindCheck("Pilot" + i + "_FAK") >= 0) {
        ((HierMesh)localObject2).chunkVisible("Pilot" + i + "_FAK", false);
      }
      if (((HierMesh)localObject2).chunkFindCheck("Pilot" + i + "_FAL") >= 0) {
        ((HierMesh)localObject2).chunkVisible("Pilot" + i + "_FAL", false);
      }
      if (((HierMesh)localObject2).chunkFindCheck("Head" + i + "_FAK") >= 0) {
        ((HierMesh)localObject2).chunkVisible("Head" + i + "_FAK", false);
      }
      if (((HierMesh)localObject2).chunkFindCheck("Head" + i + "_FAL") >= 0) {
        ((HierMesh)localObject2).chunkVisible("Head" + i + "_FAL", false);
      }

    }

    if (!paramBoolean) {
      for (i = 0; i < localObject1.length; i++) {
        if (((HierMesh)localObject2).chunkFindCheck(localObject1[i] + "_D0") >= 0) {
          ((HierMesh)localObject2).chunkVisible(localObject1[i] + "_D0", false);
          for (int j = 3; j >= 0; j--) {
            if (((HierMesh)localObject2).chunkFindCheck(localObject1[i] + "_D" + j) >= 0) {
              ((HierMesh)localObject2).chunkVisible(localObject1[i] + "_D" + j, true);
              break;
            }
          }
        }
      }

    }

    Aircraft.forceGear(this.prop.clazz, hierMesh(), 1.0F);

    if (!paramBoolean)
    {
      mesh().makeAllMaterialsDarker(0.32F, 0.45F);
    }
  }

  private void Align(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.pos.getAbs(p, o);
    p.z = (Engine.land().HQ(p.x, p.y) + this.prop.height);
    if (!paramBoolean1) {
      o.increment(0.0F, -this.prop.pitch, 0.0F);
    }
    Engine.land().N(p.x, p.y, n);
    o.orient(n);
    o.increment(0.0F, this.prop.pitch, 0.0F);

    if (paramBoolean2)
    {
      long l = ()(p.x % 2.3D * 221.0D + p.y % 3.4D * 211.0D * 211.0D);
      RangeRandom localRangeRandom = new RangeRandom(l);

      p.z -= localRangeRandom.nextFloat(0.1F, 0.4F);

      float f1 = localRangeRandom.nextFloat(-2.0F, 2.0F);
      float f2 = (localRangeRandom.nextBoolean() ? 1.0F : -1.0F) * localRangeRandom.nextFloat(7.0F, 18.0F);
      o.increment(f1, 0.0F, f2);
    }

    this.pos.setAbs(p, o);
  }

  public void align()
  {
    Align(false, false);
  }

  public int HitbyMask()
  {
    return this.prop.HITBY_MASK;
  }

  public int chooseBulletType(BulletProperties[] paramArrayOfBulletProperties)
  {
    if (this.dying != 0) {
      return -1;
    }

    if (paramArrayOfBulletProperties.length == 1) {
      return 0;
    }

    if (paramArrayOfBulletProperties.length <= 0) {
      return -1;
    }

    if (paramArrayOfBulletProperties[0].power <= 0.0F)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].power <= 0.0F)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].cumulativePower > 0.0F)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].cumulativePower > 0.0F)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 1)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].powerType == 1)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 2)
    {
      return 1;
    }

    return 0;
  }

  public int chooseShotpoint(BulletProperties paramBulletProperties) {
    if (this.dying != 0) {
      return -1;
    }
    return 0;
  }

  public boolean getShotpointOffset(int paramInt, Point3d paramPoint3d) {
    if (this.dying != 0) {
      return false;
    }

    if (paramInt != 0) {
      return false;
    }

    if (paramPoint3d != null) {
      paramPoint3d.set(0.0D, 0.0D, 0.0D);
    }
    return true;
  }

  public boolean unmovableInFuture()
  {
    return true;
  }

  public void collisionDeath()
  {
    if (isNet()) {
      return;
    }

    ShowExplode(-1.0F);

    destroy();
  }

  public float futurePosition(float paramFloat, Point3d paramPoint3d)
  {
    this.pos.getAbs(paramPoint3d);
    return paramFloat <= 0.0F ? 0.0F : paramFloat;
  }

  private void send_DeathCommand(Actor paramActor)
  {
    if (!isNetMaster()) {
      return;
    }

    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    try {
      localNetMsgGuaranted.writeByte(68);
      localNetMsgGuaranted.writeNetObj(paramActor == null ? null : paramActor.net);
      this.net.post(localNetMsgGuaranted);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  private void send_DeathRequest(Actor paramActor)
  {
    if (!isNetMirror()) {
      return;
    }

    if ((this.net.masterChannel() instanceof NetChannelInStream))
      return;
    try
    {
      NetMsgFiltered localNetMsgFiltered = new NetMsgFiltered();
      localNetMsgFiltered.writeByte(68);
      localNetMsgFiltered.writeNetObj(paramActor == null ? null : paramActor.net);
      localNetMsgFiltered.setIncludeTime(false);
      this.net.postTo(Time.current(), this.net.masterChannel(), localNetMsgFiltered);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  public void createNetObject(NetChannel paramNetChannel, int paramInt)
  {
    if (paramNetChannel == null)
    {
      this.net = new Master(this);
    }
    else
      this.net = new Mirror(this, paramNetChannel, paramInt);
  }

  public void netFirstUpdate(NetChannel paramNetChannel)
    throws IOException
  {
    NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
    localNetMsgGuaranted.writeByte(73);
    if (this.dying == 0)
      localNetMsgGuaranted.writeShort(0);
    else {
      localNetMsgGuaranted.writeShort(1);
    }
    this.net.postTo(paramNetChannel, localNetMsgGuaranted);
  }

  public static class SPAWN implements ActorSpawn {
    public Class cls;
    public PlaneGeneric.PlaneProperties proper;

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2) {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
        if (f == -9865.3447F) {
          System.out.println("Plane: Parameter [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
        }
        else {
          System.out.println("Plane: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
        }

        throw new RuntimeException("Can't set property");
      }
      return f;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("Plane: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");
        System.out.println(str == null ? "not found" : "is empty");
        throw new RuntimeException("Can't set property");
      }
      return str;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2, String paramString3) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        return paramString3;
      }
      return str;
    }

    private static PlaneGeneric.PlaneProperties LoadPlaneProperties(SectFile paramSectFile, String paramString, Class paramClass)
    {
      PlaneGeneric.PlaneProperties localPlaneProperties = new PlaneGeneric.PlaneProperties();

      localPlaneProperties.fnShotPanzer = TableFunctions.GetFunc2("PlaneShotPanzer");
      localPlaneProperties.fnExplodePanzer = TableFunctions.GetFunc2("PlaneExplodePanzer");

      String str1 = getS(paramSectFile, paramString, "Class");
      localPlaneProperties.clazz = null;
      try {
        localPlaneProperties.clazz = ObjIO.classForName(str1);
      } catch (Exception localException) {
        System.out.println("*** Plane: class '" + str1 + "' not found");
        return null;
      }

      Property.set(paramClass, "airClass", localPlaneProperties.clazz);

      String str2 = Property.stringValue(localPlaneProperties.clazz, "keyName", null);
      if (str2 == null) Property.set(paramClass, "i18nName", "Plane"); else {
        Property.set(paramClass, "i18nName", I18N.plane(str2));
      }

      localPlaneProperties.explodeName = getS(paramSectFile, paramString, "Explode", "Aircraft");

      localPlaneProperties.PANZER = getF(paramSectFile, paramString, "PanzerBodyFront", 1.0E-004F, 50.0F);
      localPlaneProperties.HITBY_MASK = (localPlaneProperties.PANZER > 0.015F ? -2 : -1);

      Property.set(paramClass, "iconName", "icons/" + getS(paramSectFile, paramString, "Icon") + ".mat");

      return localPlaneProperties;
    }

    public SPAWN(Class paramClass)
    {
      try
      {
        String str1 = paramClass.getName();
        int i = str1.lastIndexOf('.');
        int j = str1.lastIndexOf('$');
        if (i < j) {
          i = j;
        }
        String str2 = str1.substring(i + 1);
        this.proper = LoadPlaneProperties(Statics.getTechnicsFile(), str2, paramClass);
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("Problem in spawn: " + paramClass.getName());
      }

      this.cls = paramClass;
      Spawn.add(this.cls, this);
    }

    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      PlaneGeneric localPlaneGeneric = null;
      try
      {
        PlaneGeneric.access$202(this.proper);
        PlaneGeneric.access$302(paramActorSpawnArg);
        localPlaneGeneric = (PlaneGeneric)this.cls.newInstance();
        PlaneGeneric.access$202(null);
        PlaneGeneric.access$302(null);
      } catch (Exception localException) {
        PlaneGeneric.access$202(null);
        PlaneGeneric.access$302(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create stationary Plane object [class:" + this.cls.getName() + "]");

        return null;
      }
      return localPlaneGeneric;
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted())
      {
        Object localObject;
        switch (paramNetMsgInput.readByte()) {
        case 73:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 0);
            post((NetMsgGuaranted)localObject);
          }
          int i = paramNetMsgInput.readShort();
          if (i > 0)
          {
            if (PlaneGeneric.this.dying != 1) {
              PlaneGeneric.this.Die(null, 1, false);
            }
          }
          return true;
        case 68:
          if (isMirrored()) {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 1);
            post((NetMsgGuaranted)localObject);
          }
          if (PlaneGeneric.this.dying != 1) {
            localObject = paramNetMsgInput.readNetObj();
            Actor localActor = localObject == null ? null : ((ActorNet)localObject).actor();
            PlaneGeneric.this.Die(localActor, 1, true);
          }
          return true;
        }
        return false;
      }

      switch (paramNetMsgInput.readByte()) {
      case 68:
        this.out.unLockAndSet(paramNetMsgInput, 1);
        this.out.setIncludeTime(false);
        postRealTo(Message.currentRealTime(), masterChannel(), this.out);
        return true;
      }
      return true;
    }

    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4) {
      super(paramInt, i);
    }
  }

  class Master extends ActorNet
  {
    public Master(Actor arg2)
    {
      super();
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted()) {
        return true;
      }
      if (paramNetMsgInput.readByte() != 68)
        return false;
      if (PlaneGeneric.this.dying == 1) {
        return true;
      }
      NetObj localNetObj = paramNetMsgInput.readNetObj();
      Actor localActor = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
      PlaneGeneric.this.Die(localActor, 0, true);
      return true;
    }
  }

  public static class PlaneProperties
  {
    public Class clazz = null;

    public float height = 0.0F;
    public float pitch = 0.0F;

    public TableFunction2 fnShotPanzer = null;
    public TableFunction2 fnExplodePanzer = null;

    public float PANZER = 0.001F;

    public int HITBY_MASK = -2;

    public String explodeName = null;
  }
}