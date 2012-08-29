package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3D;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.Main;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.Statics;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import java.io.IOException;
import java.io.PrintStream;

public abstract class SmokeGeneric extends ActorHMesh
  implements ActorAlign
{
  private SmokeProperties prop = null;
  private float heightAboveLandSurface;
  private static SmokeProperties constr_arg1 = null;
  private static ActorSpawnArg constr_arg2 = null;

  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();
  private static Vector3d tmpv = new Vector3d();

  private NetMsgFiltered outCommand = new NetMsgFiltered();

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public boolean isStaticPos() {
    return true;
  }

  protected SmokeGeneric()
  {
    this(constr_arg1, constr_arg2);
  }

  private SmokeGeneric(SmokeProperties paramSmokeProperties, ActorSpawnArg paramActorSpawnArg)
  {
    super(paramSmokeProperties.meshName);
    this.prop = paramSmokeProperties;

    double d = 0.0D;
    if (paramActorSpawnArg.timeLenExist) {
      d = paramActorSpawnArg.point.jdField_z_of_type_Double;
      paramActorSpawnArg.point.jdField_z_of_type_Double = paramActorSpawnArg.timeLen;
    }
    paramActorSpawnArg.setStationary(this);
    if (paramActorSpawnArg.timeLenExist) {
      paramActorSpawnArg.point.jdField_z_of_type_Double = d;
    }

    setArmy(0);

    collide(false);
    drawing(true);

    createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);

    this.heightAboveLandSurface = 0.0F;
    Align();

    if (!Config.isUSE_RENDER()) return;
    if ((Main.state() != null) && (Main.state().id() == 18))
      Eff3D.initSetTypeTimer(true);
    Eff3DActor.New(this, null, new Loc(0.0D, 0.0D, 0.0D, 0.0F, 90.0F, 0.0F), 1.0F, this.prop.effectName, -1.0F);
  }

  private void Align() {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(p);

    if (p.jdField_z_of_type_Double < Engine.land().HQ(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double) + this.heightAboveLandSurface)
      p.jdField_z_of_type_Double = (Engine.land().HQ(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double) + this.heightAboveLandSurface);
    o.setYPR(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient().getYaw(), 0.0F, 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(p, o);
  }

  public void align()
  {
    Align();
  }

  public void createNetObject(NetChannel paramNetChannel, int paramInt)
  {
    if (paramNetChannel == null)
    {
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new Master(this);
    }
    else
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new Mirror(this, paramNetChannel, paramInt);
  }

  public void netFirstUpdate(NetChannel paramNetChannel) throws IOException {
  }

  public static class SPAWN implements ActorSpawn {
    public Class cls;
    public SmokeGeneric.SmokeProperties proper;

    private static String getS(SectFile paramSectFile, String paramString1, String paramString2) {
      String str = paramSectFile.get(paramString1, paramString2);
      if ((str == null) || (str.length() <= 0)) {
        System.out.print("Smoke: Parameter [" + paramString1 + "]:<" + paramString2 + "> ");
        System.out.println(str == null ? "not found" : "is empty");
        throw new RuntimeException("Can't set property");
      }
      return str;
    }

    private static SmokeGeneric.SmokeProperties LoadSmokeProperties(SectFile paramSectFile, String paramString, Class paramClass)
    {
      SmokeGeneric.SmokeProperties localSmokeProperties = new SmokeGeneric.SmokeProperties();

      localSmokeProperties.meshName = getS(paramSectFile, "Smokes", paramString + ":Mesh");
      localSmokeProperties.effectName = getS(paramSectFile, "Smokes", paramString + ":Effect");

      Property.set(paramClass, "iconName", "icons/unknown.mat");
      Property.set(paramClass, "meshName", localSmokeProperties.meshName);

      return localSmokeProperties;
    }

    public SPAWN(Class paramClass)
    {
      try {
        String str1 = paramClass.getName();
        int i = str1.lastIndexOf('.');
        int j = str1.lastIndexOf('$');
        if (i < j) {
          i = j;
        }
        String str2 = str1.substring(i + 1);
        this.proper = LoadSmokeProperties(Statics.getTechnicsFile(), str2, paramClass);
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

    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg) {
      SmokeGeneric localSmokeGeneric = null;
      try
      {
        SmokeGeneric.access$002(this.proper);
        SmokeGeneric.access$102(paramActorSpawnArg);
        localSmokeGeneric = (SmokeGeneric)this.cls.newInstance();
        SmokeGeneric.access$002(null);
        SmokeGeneric.access$102(null);
      } catch (Exception localException) {
        SmokeGeneric.access$002(null);
        SmokeGeneric.access$102(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create SmokeGeneric object [class:" + this.cls.getName() + "]");

        return null;
      }
      return localSmokeGeneric;
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
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

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException
    {
      return true;
    }
  }

  public static class SmokeProperties
  {
    public String meshName = null;
    public String effectName = null;
  }
}