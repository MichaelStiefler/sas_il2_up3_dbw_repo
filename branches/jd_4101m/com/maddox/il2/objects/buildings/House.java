package com.maddox.il2.objects.buildings;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MeshShared;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Finger;
import com.maddox.rts.Message;
import com.maddox.rts.SectFile;
import com.maddox.rts.SoftClass;
import com.maddox.rts.Spawn;
import java.io.PrintStream;

public final class House extends ActorMesh
  implements MsgExplosionListener, MsgShotListener, ActorAlign, SoftClass
{
  private Properties prop;
  private static final int MAT_WOOD = 0;
  private static final int MAT_BRICK = 1;
  private static final int MAT_STEEL = 2;
  private static final int N_MAT_TYPES = 3;
  private static float[][][] PenetrateEnergyToKill = (float[][][])null;

  private static float[][] PenetrateThickness = (float[][])null;
  private static final float KinEnergy_4 = 511.22504F;
  private static final float KinEnergy_7_62 = 2453.8801F;
  private static final float KinEnergy_12_7 = 10140.0F;
  private static final float KinEnergy_20 = 23400.0F;
  private static final float KinEnergy_37 = 131400.0F;
  private static final float KinEnergy_45 = 252000.0F;
  private static final float KinEnergy_50 = 369000.0F;
  private static final float KinEnergy_75 = 1224000.0F;
  private static final float KinEnergy_100 = 3295500.0F;
  private static final float KinEnergy_203 = 5120000.0F;
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();
  private static float[] probab = new float[2];

  public House()
  {
    this.prop = null;
  }

  public static void registerSpawner(String paramString)
  {
    new SPAWN(paramString);
  }

  public String fullClassName() {
    return getClass().getName() + "$" + this.prop.SoftClassInnerName;
  }
  public int fingerOfFullClassName() {
    return this.prop.FingerOfFullClassName;
  }
  public String getMeshLiveName() {
    return this.prop.MESH0_NAME;
  }

  private static final boolean RndB(float paramFloat)
  {
    return World.Rnd().nextFloat(0.0F, 1.0F) < paramFloat;
  }

  private static final float RndF(float paramFloat1, float paramFloat2) {
    return World.Rnd().nextFloat(paramFloat1, paramFloat2);
  }

  private static final void internalerrror(int paramInt)
  {
    System.out.println("*** Internal error#" + paramInt + " in House ***");
    throw new RuntimeException("Can't initialize House");
  }

  private static final void InitTablesOfEnergyToKill()
  {
    PenetrateEnergyToKill = new float[3][][];

    PenetrateThickness = new float[3][];

    PenetrateThickness[0] = { 0.025F, 0.15F };

    PenetrateEnergyToKill[0] = { { 23400.0F, 131400.0F, 252000.0F }, { 131400.0F, 369000.0F, 1224000.0F } };

    PenetrateThickness[1] = { 0.12F, 0.24F };

    PenetrateEnergyToKill[1] = { { 131400.0F, 252000.0F, 3295500.0F }, { 369000.0F, 3295500.0F, 5120000.0F } };

    PenetrateThickness[2] = { 0.002F, 0.008F };

    PenetrateEnergyToKill[2] = { { 511.22504F, 2453.8801F, 23400.0F }, { 2453.8801F, 10140.0F, 23400.0F } };

    for (int i = 0; i < 3; i++)
    {
      if (Math.abs(PenetrateThickness[i][0] - PenetrateThickness[i][1]) < 0.001D) {
        internalerrror(1);
      }

      for (int j = 0; j <= 1; j++) {
        float[] arrayOfFloat = PenetrateEnergyToKill[i][j];
        if ((arrayOfFloat[1] - arrayOfFloat[0] < 0.001D) || (arrayOfFloat[2] - arrayOfFloat[1] < 0.001D)) {
          internalerrror(2);
        }

      }

      float f1 = Math.min(PenetrateEnergyToKill[i][0][0], PenetrateEnergyToKill[i][1][0]);

      float f2 = Math.max(PenetrateEnergyToKill[i][0][2], PenetrateEnergyToKill[i][1][2]);

      for (int k = 0; k <= 100; k++) {
        float f3 = f1 + (f2 - f1) * k / 100.0F;
        float f4 = ComputeProbabOfPenetrateKill(i, 0, f3);
        float f5 = ComputeProbabOfPenetrateKill(i, 1, f3);
        if (f4 < f5) {
          System.out.println(i + " i,e0,e1,e:" + k + " " + f1 + " " + f2 + " " + f3 + " prob0,1: " + f4 + " " + f5);
          internalerrror(3);
        }
      }
    }
  }

  private static final float ComputeProbabOfPenetrateKill(int paramInt1, int paramInt2, float paramFloat)
  {
    float[] arrayOfFloat = PenetrateEnergyToKill[paramInt1][paramInt2];
    float f1;
    float f2;
    if (paramFloat < arrayOfFloat[1]) {
      if (paramFloat < arrayOfFloat[0]) {
        return 0.0F;
      }
      f1 = 0.2F / (arrayOfFloat[1] - arrayOfFloat[0]);
      f2 = 0.1F - arrayOfFloat[0] * f1;
    } else {
      if (paramFloat >= arrayOfFloat[2]) {
        return 1.0F;
      }
      f1 = 0.7F / (arrayOfFloat[2] - arrayOfFloat[1]);
      f2 = 0.3F - arrayOfFloat[1] * f1;
    }
    return paramFloat * f1 + f2;
  }

  private final float ComputeProbabOfPenetrateKill(float paramFloat, int paramInt)
  {
    if (paramInt <= 0) {
      return 0.0F;
    }

    float f1 = ComputeProbabOfPenetrateKill(this.prop.MAT_TYPE, 0, paramFloat);
    float f2 = ComputeProbabOfPenetrateKill(this.prop.MAT_TYPE, 1, paramFloat);

    float[] arrayOfFloat = PenetrateThickness[this.prop.MAT_TYPE];
    float f3 = (f2 - f1) / (arrayOfFloat[1] - arrayOfFloat[0]);
    float f4 = f1 - arrayOfFloat[0] * f3;
    float f5 = this.prop.PANZER * f3 + f4;
    if (f5 < 0.1F) f5 = 0.0F;
    else if (f5 >= 1.0F) f5 = 1.0F;
    else if (paramInt > 1) {
      f5 = 1.0F - (float)Math.pow(1.0F - f5, paramInt);
    }

    return f5;
  }

  public void msgShot(Shot paramShot)
  {
    paramShot.bodyMaterial = this.prop.EFF_BODY_MATERIAL;

    if (!isAlive()) {
      return;
    }
    if (paramShot.power <= 0.0F) {
      return;
    }

    if (paramShot.powerType == 1) {
      if (this.prop.MAT_TYPE != 2) {
        return;
      }

      f1 = paramShot.power * RndF(0.75F, 1.15F);

      float f2 = 0.256F * (float)Math.sqrt(Math.sqrt(f1));

      if (this.prop.PANZER > f2) {
        return;
      }

      die(paramShot.initiator, true);
      return;
    }

    float f1 = ComputeProbabOfPenetrateKill(paramShot.power, 1);

    if (!RndB(f1)) {
      return;
    }

    die(paramShot.initiator, true);
  }

  public void msgExplosion(Explosion paramExplosion)
  {
    if (!isAlive()) {
      return;
    }
    if (paramExplosion.power <= 0.0F) {
      return;
    }

    if (paramExplosion.powerType == 1)
    {
      float[] arrayOfFloat1 = new float[6];
      mesh().getBoundBox(arrayOfFloat1);

      this.pos.getAbs(p);
      p.x = (p.x - arrayOfFloat1[0] + (arrayOfFloat1[3] - arrayOfFloat1[0]));
      p.y = (p.y - arrayOfFloat1[1] + (arrayOfFloat1[4] - arrayOfFloat1[1]));
      p.z = (p.z - arrayOfFloat1[2] + (arrayOfFloat1[5] - arrayOfFloat1[2]));

      float[] arrayOfFloat2 = new float[2];
      paramExplosion.computeSplintersHit(p, mesh().collisionR(), 0.7F, arrayOfFloat2);

      float f1 = 0.015F * arrayOfFloat2[1] * arrayOfFloat2[1] * 0.5F;

      float f2 = ComputeProbabOfPenetrateKill(f1, (int)(arrayOfFloat2[0] + 0.5F));

      if (RndB(f2)) {
        die(paramExplosion.initiator, true);
      }
      return;
    }

    if (paramExplosion.powerType == 0) {
      if (Explosion.killable(this, paramExplosion.receivedPower(this), this.prop.MIN_TNT, this.prop.MAX_TNT, this.prop.PROBAB_DEATH_WHEN_EXPLOSION))
      {
        die(paramExplosion.initiator, true);
      }
      return;
    }

    if (this.prop.MAT_TYPE == 1) {
      return;
    }
    die(paramExplosion.initiator, true);
  }

  protected void runDeathShow()
  {
    float[] arrayOfFloat = new float[6];
    mesh().getBoundBox(arrayOfFloat);

    Explosions.HouseExplode(this.prop.EXPL_TYPE, this.pos.getAbs(), arrayOfFloat);
  }

  private void die(Actor paramActor, boolean paramBoolean)
  {
    if (!isAlive()) {
      return;
    }

    if (paramBoolean) {
      runDeathShow();
    }

    World.onActorDied(this, paramActor);

    if ((getOwner() instanceof HouseManager)) {
      ((HouseManager)getOwner()).onHouseDie(this, paramActor);
    }
    else if (World.cur().statics != null)
      World.cur().statics.onHouseDied(this, paramActor);
  }

  public void doDieShow()
  {
    runDeathShow();
  }

  public void setDiedFlag(boolean paramBoolean) {
    super.setDiedFlag(paramBoolean);
    activateMesh();
  }

  private void activateMesh()
  {
    boolean bool = getDiedFlag();
    String str = !bool ? this.prop.MESH0_NAME : this.prop.MESH1_NAME;
    if (str == null) {
      collide(false);
      drawing(false);
      return;
    }
    setMesh(MeshShared.get(str));
    if (!bool ? !this.prop.bInitHeight0 : !this.prop.bInitHeight1) {
      int i = mesh().hookFind("Ground_Level");
      float f = 0.0F;
      Object localObject;
      if (i != -1) {
        localObject = new Matrix4d();
        mesh().hookMatrix(i, (Matrix4d)localObject);
        f = (float)(-((Matrix4d)localObject).m23);
      }
      else {
        localObject = new float[6];
        mesh().getBoundBox(localObject);
        f = -localObject[2];
      }
      if (!bool) {
        this.prop.bInitHeight0 = true;
        this.prop.heightAboveLandSurface0 = f;
      } else {
        this.prop.bInitHeight1 = true;
        this.prop.heightAboveLandSurface1 = f;
      }
    }
    if (this.prop.IGNORE_SHADOW_DATA)
      mesh().setFastShadowVisibility(2);
    else {
      mesh().setFastShadowVisibility(1);
    }
    align();
    collide(!bool);
    drawing(true);
  }

  public void align()
  {
    this.pos.getAbs(p);
    p.z = Engine.land().HQ(p.x, p.y);
    if (!getDiedFlag()) p.z += this.prop.ADD_HEIGHT_0 + this.prop.heightAboveLandSurface0; else
      p.z += this.prop.ADD_HEIGHT_1 + this.prop.heightAboveLandSurface1;
    o.setYPR(this.pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
    if (this.prop.ALIGN_TO_LAND_NORMAL) {
      Engine.land().N(p.x, p.y, n);
      o.orient(n);
    }
    this.pos.setAbs(p, o);
  }

  public Object getSwitchListener(Message paramMessage) {
    return this; } 
  public boolean isStaticPos() { return true; }

  public float futurePosition(float paramFloat, Point3d paramPoint3d)
  {
    this.pos.getAbs(paramPoint3d);
    if (paramFloat <= 0.0F) return 0.0F;
    return paramFloat;
  }

  static
  {
    InitTablesOfEnergyToKill();
  }

  public static class SPAWN
    implements ActorSpawn
  {
    public Class cls;
    public House.Properties prop;

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2)
    {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
        if (f == -9865.3447F) {
          System.out.println("House: Value of [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
        }
        else {
          System.out.println("House: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
        }

        throw new RuntimeException("Can't set property");
      }
      return f;
    }

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2, float paramFloat3)
    {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if (f == -9865.3447F) {
        return paramFloat1;
      }

      if ((f < paramFloat2) || (f > paramFloat3)) {
        System.out.println("House: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat2 + ";" + paramFloat3 + ")");

        throw new RuntimeException("Can't set property");
      }
      return f;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("House: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");
        System.out.println(str == null ? "not found" : "is empty");
        throw new RuntimeException("Can't set property");
      }
      return str;
    }

    private static House.Properties LoadHouseProperties(SectFile paramSectFile, String paramString1, String paramString2, String paramString3)
    {
      House.Properties localProperties = new House.Properties();

      localProperties.SoftClassInnerName = paramString3;
      localProperties.FingerOfFullClassName = Finger.Int(paramString2);

      String str1 = paramSectFile.get(paramString1, "equals");
      if ((str1 == null) || (str1.length() <= 0)) {
        str1 = paramString1;
      }

      localProperties.MESH0_NAME = getS(paramSectFile, paramString1, "MeshLive");
      localProperties.MESH1_NAME = paramSectFile.get(paramString1, "MeshDead");
      if (localProperties.MESH1_NAME == null) {
        getS(paramSectFile, paramString1, "MeshDead");
      }
      if (localProperties.MESH1_NAME.equals("")) {
        localProperties.MESH1_NAME = null;
      }

      localProperties.ADD_HEIGHT_0 = getF(paramSectFile, str1, "AddHeightLive", 0.0F, -100.0F, 100.0F);
      localProperties.ADD_HEIGHT_1 = getF(paramSectFile, str1, "AddHeightDead", 0.0F, -100.0F, 100.0F);

      localProperties.ALIGN_TO_LAND_NORMAL = (getF(paramSectFile, str1, "AlignToLand", 0.0F, 0.0F, 1.0F) > 0.0F);

      localProperties.ALIGN_TO_LAND_NORMAL = (getF(paramSectFile, str1, "AlignToLand", 0.0F, 0.0F, 1.0F) > 0.0F);

      localProperties.IGNORE_SHADOW_DATA = false;
      if (paramSectFile.get(paramString1, "IgnoreShadowData") != null) {
        localProperties.IGNORE_SHADOW_DATA = true;
      }

      localProperties.PANZER = getF(paramSectFile, str1, "Panzer", 1.0E-004F, 50.0F);

      String str2 = getS(paramSectFile, str1, "Body");
      if (str2.equalsIgnoreCase("WoodSmall")) {
        localProperties.MAT_TYPE = 0;
        localProperties.EFF_BODY_MATERIAL = 3;
        localProperties.EXPL_TYPE = 0;
        localProperties.MIN_TNT = (0.25F * (localProperties.PANZER / 0.025F));
        localProperties.MAX_TNT = (localProperties.MIN_TNT * 2.0F);
        localProperties.PROBAB_DEATH_WHEN_EXPLOSION = 0.01F;
      } else if (str2.equalsIgnoreCase("WoodMiddle")) {
        localProperties.MAT_TYPE = 0;
        localProperties.EFF_BODY_MATERIAL = 3;
        localProperties.EXPL_TYPE = 1;
        localProperties.MIN_TNT = (5.0F * (localProperties.PANZER / 0.15F));
        localProperties.MAX_TNT = (localProperties.MIN_TNT * 1.7F);
        localProperties.PROBAB_DEATH_WHEN_EXPLOSION = 0.01F;
      } else if (str2.equalsIgnoreCase("RockMiddle")) {
        localProperties.MAT_TYPE = 1;
        localProperties.EFF_BODY_MATERIAL = 4;
        localProperties.EXPL_TYPE = 2;
        localProperties.MIN_TNT = (6.0F * (localProperties.PANZER / 0.12F));
        localProperties.MAX_TNT = (localProperties.MIN_TNT * 1.7F);
        localProperties.PROBAB_DEATH_WHEN_EXPLOSION = 0.01F;
      } else if (str2.equalsIgnoreCase("RockBig")) {
        localProperties.MAT_TYPE = 1;
        localProperties.EFF_BODY_MATERIAL = 4;
        localProperties.EXPL_TYPE = 3;
        localProperties.MIN_TNT = (12.0F * (localProperties.PANZER / 0.24F));
        localProperties.MAX_TNT = (localProperties.MIN_TNT * 1.7F);
        localProperties.PROBAB_DEATH_WHEN_EXPLOSION = 0.05F;
      } else if (str2.equalsIgnoreCase("RockHuge")) {
        localProperties.MAT_TYPE = 1;
        localProperties.EFF_BODY_MATERIAL = 4;
        localProperties.EXPL_TYPE = 4;
        localProperties.MIN_TNT = (30.0F * (localProperties.PANZER / 0.48F));
        localProperties.MAX_TNT = (localProperties.MIN_TNT * 1.7F);
        localProperties.PROBAB_DEATH_WHEN_EXPLOSION = 0.05F;
      } else if (str2.equalsIgnoreCase("FuelSmall")) {
        localProperties.MAT_TYPE = 2;
        localProperties.EFF_BODY_MATERIAL = 2;
        localProperties.EXPL_TYPE = 5;
        localProperties.MIN_TNT = (0.2F * (localProperties.PANZER / 0.002F));
        localProperties.MAX_TNT = (localProperties.MIN_TNT * 1.7F);
        localProperties.PROBAB_DEATH_WHEN_EXPLOSION = 0.01F;
      } else if (str2.equalsIgnoreCase("FuelBig")) {
        localProperties.MAT_TYPE = 2;
        localProperties.EFF_BODY_MATERIAL = 2;
        localProperties.EXPL_TYPE = 6;
        localProperties.MIN_TNT = (0.8F * (localProperties.PANZER / 0.008F));
        localProperties.MAX_TNT = (localProperties.MIN_TNT * 1.7F);
        localProperties.PROBAB_DEATH_WHEN_EXPLOSION = 0.01F;
      } else {
        System.out.println("House: Undefined Body type in class '" + paramString2 + "'");

        System.out.println("Allowed body types are:WoodSmall,WoodMiddle,RockMiddle,RockBig,RockHuge,FuelSmall,FuelBig");

        throw new RuntimeException("Can't register house object");
      }

      return localProperties;
    }

    protected SPAWN(String paramString)
    {
      this.cls = getClass().getDeclaringClass();

      String str1 = this.cls.getName();
      String str2 = str1.substring(str1.lastIndexOf('.') + 1);
      String str3 = str2 + '$' + paramString;
      String str4 = str1 + '$' + paramString;

      SectFile localSectFile = Statics.getBuildingsFile();
      String str5 = null;
      int i = localSectFile.sections();
      for (int j = 0; j < i; j++) {
        if (localSectFile.sectionName(j).endsWith(str3)) {
          str5 = localSectFile.sectionName(j);
          break;
        }
      }
      if (str5 == null) {
        System.out.println("House: Section " + str3 + " not found");
        throw new RuntimeException("Can't register spawner");
      }
      try
      {
        this.prop = LoadHouseProperties(localSectFile, str5, str4, paramString);
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("Problem in house spawn registration: " + str4);
      }

      Spawn.add_SoftClass(str4, this);
    }

    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      House localHouse = null;
      try { localHouse = (House)this.cls.newInstance();
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        return null;
      }
      House.access$002(localHouse, this.prop);
      if (!paramActorSpawnArg.armyExist)
      {
        paramActorSpawnArg.armyExist = true;
        paramActorSpawnArg.army = 0;
      }

      paramActorSpawnArg.setStationaryNoIcon(localHouse);
      try {
        localHouse.activateMesh();
      } catch (RuntimeException localRuntimeException) {
        localHouse.destroy();
        throw localRuntimeException;
      }
      if ((!House.Properties.access$200(localHouse.prop)) && (localHouse.mesh().collisionR() <= 0.0F)) {
        System.out.println("##### House without collision (" + localHouse.prop.MESH0_NAME + ")");
      }

      House.Properties.access$202(localHouse.prop, true);
      return localHouse;
    }
  }

  public static class Properties
  {
    public String SoftClassInnerName = null;
    public int FingerOfFullClassName = 0;

    public String MESH0_NAME = "3do/BuildingsGeneral/NameNotSpecified.sim";
    public String MESH1_NAME = "3do/BuildingsGeneral/DMG/NameNotSpecified.sim";

    private boolean meshTested = false;

    public float ADD_HEIGHT_0 = 0.0F;
    public float ADD_HEIGHT_1 = 0.0F;

    public boolean ALIGN_TO_LAND_NORMAL = false;

    public boolean IGNORE_SHADOW_DATA = false;

    public int MAT_TYPE = -1;
    public int EFF_BODY_MATERIAL = -1;
    public int EXPL_TYPE = -1;

    public float PANZER = 0.001F;

    public float MIN_TNT = 0.07F;
    public float MAX_TNT = 0.071F;
    public float PROBAB_DEATH_WHEN_EXPLOSION = 0.4F;

    public boolean bInitHeight0 = false;
    public boolean bInitHeight1 = false;
    public float heightAboveLandSurface0;
    public float heightAboveLandSurface1;
  }
}