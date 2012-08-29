package com.maddox.il2.objects.buildings;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorLandMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.rts.Finger;
import com.maddox.rts.Message;
import com.maddox.rts.SectFile;
import com.maddox.rts.SoftClass;
import com.maddox.rts.Spawn;
import java.io.PrintStream;

public final class Mountain extends ActorLandMesh
  implements ActorAlign, SoftClass
{
  private Properties prop;
  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();

  public Mountain()
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

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public void setDiedFlag(boolean paramBoolean) {
    super.setDiedFlag(paramBoolean);
    align();
    drawing(true);
  }

  public void align()
  {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(p);
    p.z = 0.0D;
    o.setYPR(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient().getYaw(), 0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(p, o);
  }

  public float futurePosition(float paramFloat, Point3d paramPoint3d)
  {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(paramPoint3d);
    if (paramFloat <= 0.0F) return 0.0F;
    return paramFloat;
  }
  private static class SPAWN implements ActorSpawn {
    public Class cls;
    public Mountain.Properties prop;

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2) { String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("Mountain: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");
        System.out.println(str == null ? "not found" : "is empty");
        throw new RuntimeException("Can't set property");
      }
      return str;
    }

    private static Mountain.Properties LoadProperties(SectFile paramSectFile, String paramString1, String paramString2, String paramString3)
    {
      Mountain.Properties localProperties = new Mountain.Properties();

      localProperties.SoftClassInnerName = paramString3;
      localProperties.FingerOfFullClassName = Finger.Int(paramString2);

      localProperties.MeshName = getS(paramSectFile, paramString1, "Mesh");

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
        System.out.println("Mountain: Section " + str3 + " not found");
        throw new RuntimeException("Can't register spawner");
      }
      try
      {
        this.prop = LoadProperties(localSectFile, str5, str4, paramString);
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("Problem in mountain spawn registration: " + str4);
      }

      Spawn.add_SoftClass(str4, this);
    }

    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg)
    {
      Mountain localMountain = null;
      try {
        localMountain = (Mountain)this.cls.newInstance();
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        return null;
      }

      Mountain.access$002(localMountain, this.prop);
      try {
        localMountain.setMesh(new Mesh(this.prop.MeshName));
      } catch (RuntimeException localRuntimeException) {
        localMountain.destroy();
        throw localRuntimeException;
      }

      if (!this.prop.bInitHeight) {
        int i = localMountain.mesh().hookFind("Ground_Level");
        float f = 0.0F;
        Object localObject;
        if (i != -1) {
          localObject = new Matrix4d();
          localMountain.mesh().hookMatrix(i, (Matrix4d)localObject);
          f = (float)(-((Matrix4d)localObject).m23);
        }
        else {
          localObject = new float[6];
          localMountain.mesh().getBoundBox(localObject);
          f = -localObject[2];
        }
        this.prop.heightAboveLandSurface = f;
        this.prop.bInitHeight = true;
      }

      paramActorSpawnArg.set(localMountain);
      localMountain.align();
      localMountain.drawing(true);
      return (Actor)localMountain;
    }
  }

  public static class Properties
  {
    public String SoftClassInnerName = null;
    public int FingerOfFullClassName = 0;

    public String MeshName = null;

    public boolean bInitHeight = false;
    public float heightAboveLandSurface;
  }
}