package com.maddox.il2.objects.buildings;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
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
import com.maddox.rts.Finger;
import com.maddox.rts.Message;
import com.maddox.rts.SectFile;
import com.maddox.rts.SoftClass;
import com.maddox.rts.Spawn;
import java.io.PrintStream;

public final class Furniture extends ActorMesh
  implements ActorAlign, SoftClass
{
  private Properties prop;
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();

  public Furniture()
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

  public void setDiedFlag(boolean paramBoolean)
  {
    super.setDiedFlag(paramBoolean);
    activateMesh();
  }

  private void activateMesh()
  {
    setMesh(MeshShared.get(this.prop.MESH_NAME));
    if (!this.prop.bInitHeight) {
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
      this.prop.heightAboveLandSurface = f;
      this.prop.bInitHeight = true;
    }
    mesh().setFastShadowVisibility(1);
    align();
    drawing(true);
  }

  public void align()
  {
    this.pos.getAbs(p);
    p.z = Engine.land().HQ(p.x, p.y);
    p.z += this.prop.ADD_HEIGHT + this.prop.heightAboveLandSurface;
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

  private static class SPAWN implements ActorSpawn
  {
    public Class cls;
    public Furniture.Properties prop;

    private static float getF(SectFile paramSectFile, String paramString1, String paramString2, float paramFloat1, float paramFloat2)
    {
      float f = paramSectFile.get(paramString1, paramString2, -9865.3447F);
      if ((f == -9865.3447F) || (f < paramFloat1) || (f > paramFloat2)) {
        if (f == -9865.3447F) {
          System.out.println("Furniture: Value of [" + paramString1 + "]:<" + paramString2 + "> " + "not found");
        }
        else {
          System.out.println("Furniture: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat1 + ";" + paramFloat2 + ")");
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
        System.out.println("Furniture: Value of [" + paramString1 + "]:<" + paramString2 + "> (" + f + ")" + " is out of range (" + paramFloat2 + ";" + paramFloat3 + ")");

        throw new RuntimeException("Can't set property");
      }
      return f;
    }

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("Furniture: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");
        System.out.println(str == null ? "not found" : "is empty");
        throw new RuntimeException("Can't set property");
      }
      return str;
    }

    private static Furniture.Properties LoadProperties(SectFile paramSectFile, String paramString1, String paramString2, String paramString3)
    {
      Furniture.Properties localProperties = new Furniture.Properties();

      localProperties.SoftClassInnerName = paramString3;
      localProperties.FingerOfFullClassName = Finger.Int(paramString2);

      String str = paramSectFile.get(paramString1, "equals");
      if ((str == null) || (str.length() <= 0)) {
        str = paramString1;
      }

      localProperties.MESH_NAME = getS(paramSectFile, paramString1, "Mesh");

      localProperties.ADD_HEIGHT = getF(paramSectFile, str, "AddHeight", 0.0F, -100.0F, 100.0F);

      localProperties.ALIGN_TO_LAND_NORMAL = (getF(paramSectFile, str, "AlignToLand", 0.0F, 0.0F, 1.0F) > 0.0F);

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
        System.out.println("Furniture: Section " + str3 + " not found");
        throw new RuntimeException("Can't register spawner");
      }
      try
      {
        this.prop = LoadProperties(localSectFile, str5, str4, paramString);
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("Problem in Furniture spawn registration: " + str4);
      }

      Spawn.add_SoftClass(str4, this);
    }

    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      Furniture localFurniture = null;
      try {
        localFurniture = (Furniture)this.cls.newInstance();
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        return null;
      }
      Furniture.access$002(localFurniture, this.prop);
      if (!paramActorSpawnArg.armyExist)
      {
        paramActorSpawnArg.armyExist = true;
        paramActorSpawnArg.army = 0;
      }

      paramActorSpawnArg.setStationaryNoIcon(localFurniture);
      try {
        localFurniture.activateMesh();
      } catch (RuntimeException localRuntimeException) {
        localFurniture.destroy();
        throw localRuntimeException;
      }
      return localFurniture;
    }
  }

  public static class Properties
  {
    public String SoftClassInnerName = null;
    public int FingerOfFullClassName = 0;

    public String MESH_NAME = "3do/BuildingsGeneral/NameNotSpecified.sim";

    public boolean ALIGN_TO_LAND_NORMAL = false;
    public float ADD_HEIGHT = 0.0F;

    public boolean bInitHeight = false;
    public float heightAboveLandSurface;
  }
}