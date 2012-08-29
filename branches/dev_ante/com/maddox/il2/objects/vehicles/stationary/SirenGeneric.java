package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.sound.SoundFX;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map.Entry;

public abstract class SirenGeneric extends ActorHMesh
  implements ActorAlign
{
  private static String mesh_name = "3do/primitive/siren/mono.sim";
  private float heightAboveLandSurface;
  protected SoundFX engineSFX = null;
  protected int engineSTimer = 9999999;
  private int myArmy;
  private static ActorSpawnArg constr_arg2 = null;

  private static Point3d p = new Point3d();
  private static Orient o = new Orient();
  private static Vector3f n = new Vector3f();
  private static Vector3d tmpv = new Vector3d();

  private NetMsgFiltered outCommand = new NetMsgFiltered();

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

  public void destroy()
  {
    if (isDestroyed()) {
      return;
    }
    this.engineSFX = null;
    this.engineSTimer = -99999999;
    breakSounds();

    super.destroy();
  }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public boolean isStaticPos() {
    return true;
  }

  protected SirenGeneric()
  {
    this(constr_arg2);
  }

  private SirenGeneric(ActorSpawnArg paramActorSpawnArg)
  {
    super(mesh_name);

    paramActorSpawnArg.setStationary(this);

    this.myArmy = getArmy();
    setArmy(0);

    collide(false);
    drawing(true);

    createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);

    this.heightAboveLandSurface = 0.0F;
    Align();

    startMove();
  }

  public void startMove()
  {
    if (!interpEnd("move")) {
      interpPut(new Move(), "move", Time.current(), null);
      this.engineSFX = newSound("objects.siren", false);
      this.engineSTimer = (-(int)SecsToTicks(Rnd(3.0F, 10.0F)));
    }
  }

  private void Align()
  {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(p);
    p.z = (Engine.land().HQ(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double) + this.heightAboveLandSurface);
    o.setYPR(this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient().getYaw(), 0.0F, 0.0F);
    Engine.land().N(p.jdField_x_of_type_Double, p.jdField_y_of_type_Double, n);
    o.orient(n);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(p, o);
  }

  public void align()
  {
    Align();
  }

  private boolean danger()
  {
    Point3d localPoint3d = new Point3d();
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(localPoint3d);
    Vector3d localVector3d = new Vector3d();

    Map.Entry localEntry = Engine.name2Actor().nextEntry(null);
    while (localEntry != null) {
      Actor localActor = (Actor)localEntry.getValue();
      if ((localActor instanceof Aircraft))
      {
        if ((localActor.getArmy() != this.myArmy) && 
          (localActor.getSpeed(localVector3d) > 20.0D) && 
          (localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().distance(localPoint3d) < 18000.0D))
        {
          return true;
        }

      }

      localEntry = Engine.name2Actor().nextEntry(localEntry);
    }
    return false;
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

  public void netFirstUpdate(NetChannel paramNetChannel) throws IOException
  {
  }

  public static class SPAWN implements ActorSpawn
  {
    public Class cls;

    public SPAWN(Class paramClass) {
      Property.set(paramClass, "iconName", "icons/unknown.mat");
      Property.set(paramClass, "meshName", SirenGeneric.mesh_name);

      this.cls = paramClass;
      Spawn.add(this.cls, this);
    }

    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg) {
      SirenGeneric localSirenGeneric = null;
      try
      {
        SirenGeneric.access$302(paramActorSpawnArg);
        localSirenGeneric = (SirenGeneric)this.cls.newInstance();
        SirenGeneric.access$302(null);
      } catch (Exception localException) {
        SirenGeneric.access$302(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create SirenGeneric object [class:" + this.cls.getName() + "]");

        return null;
      }
      return localSirenGeneric;
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

  class Move extends Interpolate
  {
    Move()
    {
    }

    public boolean tick()
    {
      if (SirenGeneric.this.engineSFX != null) {
        if (SirenGeneric.this.engineSTimer >= 0) {
          if (--SirenGeneric.this.engineSTimer <= 0) {
            SirenGeneric.this.engineSTimer = (int)SirenGeneric.access$000(SirenGeneric.Rnd(30.0F, 50.0F));
            if (!SirenGeneric.this.danger()) {
              SirenGeneric.this.engineSFX.stop();
              SirenGeneric.this.engineSTimer = (-SirenGeneric.this.engineSTimer);
            }
          }
        }
        else if (++SirenGeneric.this.engineSTimer >= 0) {
          SirenGeneric.this.engineSTimer = (-(int)SirenGeneric.access$000(SirenGeneric.Rnd(30.0F, 50.0F)));
          if (SirenGeneric.this.danger()) {
            SirenGeneric.this.engineSFX.play();
            SirenGeneric.this.engineSTimer = (-SirenGeneric.this.engineSTimer);
          }
        }
      }

      return true;
    }
  }
}