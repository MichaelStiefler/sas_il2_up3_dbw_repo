package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.TableFunctions;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ZutiTargetsSupportMethods;
import com.maddox.il2.ai.ground.Obstacle;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtTank;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.TableFunction2;
import java.io.IOException;
import java.io.PrintStream;

public abstract class StationaryGeneric extends ActorHMesh
  implements MsgExplosionListener, MsgShotListener, Prey, Obstacle, ActorAlign
{
  private StationaryProperties prop = null;
  private float heightAboveLandSurface;
  private int dying = 0;
  static final int DYING_NONE = 0;
  static final int DYING_DEAD = 1;
  private static StationaryProperties constr_arg1 = null;
  private static ActorSpawnArg constr_arg2 = null;

  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();
  private static Vector3d tmpv = new Vector3d();

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

    if ((isNetMirror()) && 
      (paramShot.isMirage())) {
      return;
    }

    if (paramShot.powerType == 1) {
      if (RndB(0.15F)) {
        return;
      }

      Die(paramShot.initiator, 0, true);
      return;
    }

    float f1 = Shot.panzerThickness(this.pos.getAbsOrient(), paramShot.v, paramShot.chunkName.equalsIgnoreCase("Head"), this.prop.PANZER_BODY_FRONT, this.prop.PANZER_BODY_SIDE, this.prop.PANZER_BODY_BACK, this.prop.PANZER_BODY_TOP, this.prop.PANZER_HEAD, this.prop.PANZER_HEAD_TOP);

    f1 *= Rnd(0.93F, 1.07F);

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
      if (TankGeneric.splintersKill(paramExplosion, this.prop.fnShotPanzer, Rnd(0.0F, 1.0F), Rnd(0.0F, 1.0F), this, 0.7F, 0.25F, this.prop.PANZER_BODY_FRONT, this.prop.PANZER_BODY_SIDE, this.prop.PANZER_BODY_BACK, this.prop.PANZER_BODY_TOP, this.prop.PANZER_HEAD, this.prop.PANZER_HEAD_TOP))
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

    float f2 = this.prop.fnExplodePanzer.Value(f1, this.prop.PANZER_TNT_TYPE);

    if ((f2 < 1000.0F) && ((f2 <= 1.0F) || (RndB(1.0F / f2))))
      Die(paramExplosion.initiator, 0, true);
  }

  private void ShowExplode(float paramFloat, Actor paramActor)
  {
    if (paramFloat > 0.0F) {
      paramFloat = Rnd(paramFloat, paramFloat * 1.6F);
    }
    Explosions.runByName(this.prop.explodeName, this, "Smoke", "SmokeHead", paramFloat, paramActor);
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

    if (this.prop.meshName1 == null)
    {
      mesh().makeAllMaterialsDarker(0.22F, 0.35F);
    }
    else setMesh(this.prop.meshName1);

    int i = mesh().hookFind("Ground_Level");
    if (i != -1) {
      Matrix4d localMatrix4d = new Matrix4d();
      mesh().hookMatrix(i, localMatrix4d);
      this.heightAboveLandSurface = (float)(-localMatrix4d.m23);
    }

    Align();

    if (paramBoolean) {
      ShowExplode(15.0F, paramActor);
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

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  protected StationaryGeneric()
  {
    this(constr_arg1, constr_arg2);
  }

  private StationaryGeneric(StationaryProperties paramStationaryProperties, ActorSpawnArg paramActorSpawnArg)
  {
    super(paramStationaryProperties.meshName);
    this.prop = paramStationaryProperties;

    paramActorSpawnArg.setStationary(this);

    collide(true);
    drawing(true);

    createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);

    this.heightAboveLandSurface = 0.0F;
    int i = mesh().hookFind("Ground_Level");
    if (i != -1) {
      Matrix4d localMatrix4d = new Matrix4d();
      mesh().hookMatrix(i, localMatrix4d);
      this.heightAboveLandSurface = (float)(-localMatrix4d.m23);
    } else {
      System.out.println("Stationary " + getClass().getName() + ": hook Ground_Level not found");
    }

    Align();
  }

  private void Align()
  {
    this.pos.getAbs(p);
    p.z = (Engine.land().HQ(p.x, p.y) + this.heightAboveLandSurface);
    o.setYPR(this.pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
    Engine.land().N(p.x, p.y, n);
    o.orient(n);
    this.pos.setAbs(p, o);
  }

  public void align()
  {
    Align();
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

    if ((this instanceof TgtTank)) {
      if (paramArrayOfBulletProperties[0].cumulativePower > 0.0F)
      {
        return 0;
      }
      if (paramArrayOfBulletProperties[1].cumulativePower > 0.0F)
      {
        return 1;
      }

      if (paramArrayOfBulletProperties[0].power <= 0.0F)
      {
        return 0;
      }
      if (paramArrayOfBulletProperties[1].power <= 0.0F)
      {
        return 1;
      }
    } else {
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
    }

    if (paramArrayOfBulletProperties[0].powerType == 1)
    {
      return 0;
    }
    if (paramArrayOfBulletProperties[1].powerType == 1)
    {
      return 1;
    }

    if (paramArrayOfBulletProperties[0].powerType == 0)
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

    ShowExplode(-1.0F, null);

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
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(100);
      localNetMsgGuaranted.writeNetObj(paramActor == null ? null : paramActor.net);
      this.net.postTo(this.net.masterChannel(), localNetMsgGuaranted);
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
    public StationaryGeneric.StationaryProperties proper;

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2) {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
        if (f == -9865.3447F) {
          System.out.println("Stationary: Parameter [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
        }
        else {
          System.out.println("Stationary: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
        }

        throw new RuntimeException("Can't set property");
      }
      return f;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("Stationary: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");
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

    private static StationaryGeneric.StationaryProperties LoadStationaryProperties(SectFile paramSectFile, String paramString, Class paramClass)
    {
      StationaryGeneric.StationaryProperties localStationaryProperties = new StationaryGeneric.StationaryProperties();

      String str = getS(paramSectFile, paramString, "PanzerType", null);
      if (str == null) {
        str = "Tank";
      }
      localStationaryProperties.fnShotPanzer = TableFunctions.GetFunc2(str + "ShotPanzer");
      localStationaryProperties.fnExplodePanzer = TableFunctions.GetFunc2(str + "ExplodePanzer");

      localStationaryProperties.PANZER_TNT_TYPE = getF(paramSectFile, paramString, "PanzerSubtype", 0.0F, 100.0F);

      localStationaryProperties.meshSummer = getS(paramSectFile, paramString, "MeshSummer");
      localStationaryProperties.meshDesert = getS(paramSectFile, paramString, "MeshDesert", localStationaryProperties.meshSummer);
      localStationaryProperties.meshWinter = getS(paramSectFile, paramString, "MeshWinter", localStationaryProperties.meshSummer);
      localStationaryProperties.meshSummer1 = getS(paramSectFile, paramString, "MeshSummerDamage", null);
      localStationaryProperties.meshDesert1 = getS(paramSectFile, paramString, "MeshDesertDamage", localStationaryProperties.meshSummer1);
      localStationaryProperties.meshWinter1 = getS(paramSectFile, paramString, "MeshWinterDamage", localStationaryProperties.meshSummer1);

      int i = (localStationaryProperties.meshSummer1 == null ? 1 : 0) + (localStationaryProperties.meshDesert1 == null ? 1 : 0) + (localStationaryProperties.meshWinter1 == null ? 1 : 0);

      if ((i != 0) && (i != 3)) {
        System.out.println("Stationary: Uncomplete set of damage meshes for '" + paramString + "'");

        throw new RuntimeException("Can't register stationary object");
      }

      localStationaryProperties.explodeName = getS(paramSectFile, paramString, "Explode", "Stationary");

      localStationaryProperties.PANZER_BODY_FRONT = getF(paramSectFile, paramString, "PanzerBodyFront", 0.001F, 9.999F);

      if (paramSectFile.get(paramString, "PanzerBodyBack", -9865.3447F) == -9865.3447F) {
        localStationaryProperties.PANZER_BODY_BACK = localStationaryProperties.PANZER_BODY_FRONT;
        localStationaryProperties.PANZER_BODY_SIDE = localStationaryProperties.PANZER_BODY_FRONT;
        localStationaryProperties.PANZER_BODY_TOP = localStationaryProperties.PANZER_BODY_FRONT;
      } else {
        localStationaryProperties.PANZER_BODY_BACK = getF(paramSectFile, paramString, "PanzerBodyBack", 0.001F, 9.999F);
        localStationaryProperties.PANZER_BODY_SIDE = getF(paramSectFile, paramString, "PanzerBodySide", 0.001F, 9.999F);
        localStationaryProperties.PANZER_BODY_TOP = getF(paramSectFile, paramString, "PanzerBodyTop", 0.001F, 9.999F);
      }

      if (paramSectFile.get(paramString, "PanzerHead", -9865.3447F) == -9865.3447F)
        localStationaryProperties.PANZER_HEAD = localStationaryProperties.PANZER_BODY_FRONT;
      else {
        localStationaryProperties.PANZER_HEAD = getF(paramSectFile, paramString, "PanzerHead", 0.001F, 9.999F);
      }

      if (paramSectFile.get(paramString, "PanzerHeadTop", -9865.3447F) == -9865.3447F)
        localStationaryProperties.PANZER_HEAD_TOP = localStationaryProperties.PANZER_BODY_TOP;
      else {
        localStationaryProperties.PANZER_HEAD_TOP = getF(paramSectFile, paramString, "PanzerHeadTop", 0.001F, 9.999F);
      }

      float f = Math.min(Math.min(localStationaryProperties.PANZER_BODY_BACK, localStationaryProperties.PANZER_BODY_TOP), Math.min(localStationaryProperties.PANZER_BODY_SIDE, localStationaryProperties.PANZER_HEAD_TOP));

      localStationaryProperties.HITBY_MASK = (f > 0.015F ? -2 : -1);

      Property.set(paramClass, "iconName", "icons/" + getS(paramSectFile, paramString, "Icon") + ".mat");
      Property.set(paramClass, "meshName", localStationaryProperties.meshSummer);

      return localStationaryProperties;
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
        this.proper = LoadStationaryProperties(Statics.getTechnicsFile(), str2, paramClass);
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
      switch (World.cur().camouflage) {
      case 1:
        this.proper.meshName = this.proper.meshWinter;
        this.proper.meshName1 = this.proper.meshWinter1;
        break;
      case 2:
        this.proper.meshName = this.proper.meshDesert;
        this.proper.meshName1 = this.proper.meshDesert1;
        break;
      default:
        this.proper.meshName = this.proper.meshSummer;
        this.proper.meshName1 = this.proper.meshSummer1;
      }

      StationaryGeneric localStationaryGeneric = null;
      try
      {
        StationaryGeneric.access$202(this.proper);
        StationaryGeneric.access$302(paramActorSpawnArg);
        localStationaryGeneric = (StationaryGeneric)this.cls.newInstance();
        StationaryGeneric.access$202(null);
        StationaryGeneric.access$302(null);
      } catch (Exception localException) {
        StationaryGeneric.access$202(null);
        StationaryGeneric.access$302(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create Stationary object [class:" + this.cls.getName() + "]");

        return null;
      }
      return localStationaryGeneric;
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted())
      {
        Object localObject;
        switch (paramNetMsgInput.readByte())
        {
        case 73:
          if (isMirrored()) {
            NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted(paramNetMsgInput, 0);
            post(localNetMsgGuaranted);
          }
          int i = paramNetMsgInput.readShort();
          if (i > 0)
          {
            if (StationaryGeneric.this.dying != 1) {
              StationaryGeneric.this.Die(null, 1, false);
              try
              {
                ZutiTargetsSupportMethods.staticActorDied(StationaryGeneric.this);
              }
              catch (Exception localException1)
              {
                System.out.println("StationaryGeneric error, ID_01: " + localException1.toString());
              }
            }
          }

          return true;
        case 68:
          if (isMirrored())
          {
            localObject = new NetMsgGuaranted(paramNetMsgInput, 1);
            post((NetMsgGuaranted)localObject);
          }
          if (StationaryGeneric.this.dying != 1) {
            localObject = paramNetMsgInput.readNetObj();
            Actor localActor = localObject == null ? null : ((ActorNet)localObject).actor();
            StationaryGeneric.this.Die(localActor, 1, true);
            try
            {
              ZutiTargetsSupportMethods.staticActorDied(StationaryGeneric.this);
            }
            catch (Exception localException2)
            {
              System.out.println("StationaryGeneric error, ID_02: " + localException2.toString());
            }
          }

          return true;
        case 100:
          localObject = new NetMsgGuaranted(paramNetMsgInput, 1);
          postTo(masterChannel(), (NetMsgGuaranted)localObject);
          return true;
        }
        return false;
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
        if (paramNetMsgInput.readByte() != 100)
          return false;
      }
      else return false;

      if (StationaryGeneric.this.dying == 1) {
        return true;
      }
      NetObj localNetObj = paramNetMsgInput.readNetObj();
      Actor localActor = localNetObj == null ? null : ((ActorNet)localNetObj).actor();
      StationaryGeneric.this.Die(localActor, 0, true);
      return true;
    }
  }

  public static class StationaryProperties
  {
    public String meshName = null;
    public String meshName1 = null;

    public String meshSummer = null;
    public String meshDesert = null;
    public String meshWinter = null;

    public String meshSummer1 = null;
    public String meshDesert1 = null;
    public String meshWinter1 = null;

    public TableFunction2 fnShotPanzer = null;
    public TableFunction2 fnExplodePanzer = null;

    public float PANZER_BODY_FRONT = 0.001F;
    public float PANZER_BODY_BACK = 0.001F;
    public float PANZER_BODY_SIDE = 0.001F;
    public float PANZER_BODY_TOP = 0.001F;
    public float PANZER_HEAD = 0.001F;
    public float PANZER_HEAD_TOP = 0.001F;

    public float PANZER_TNT_TYPE = 1.0F;

    public int HITBY_MASK = -2;

    public String explodeName = null;
  }
}