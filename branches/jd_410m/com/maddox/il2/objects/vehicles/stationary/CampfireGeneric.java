package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.RandomVector;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.LightPointWorld;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;

public abstract class CampfireGeneric extends ActorHMesh
  implements ActorAlign
{
  private static String mesh_name = "3do/buildings/furniture/campfire/mono.sim";
  private float heightAboveLandSurface;
  private LightPointActor light;
  private int hashCode;
  private static RangeRandom rnd = new RangeRandom();

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

  protected CampfireGeneric()
  {
    this(constr_arg2);
  }

  private CampfireGeneric(ActorSpawnArg paramActorSpawnArg)
  {
    super(mesh_name);

    paramActorSpawnArg.setStationary(this);

    setArmy(0);

    collide(false);
    drawing(true);
    this.draw = new MyDraw();

    this.light = new LightPointActor(new LightPointWorld(), new Point3d(0.0D, 0.0D, 1.5D));
    this.light.light.setColor(new Color3f(1.0F, 0.95F, 0.66F));
    this.light.light.setEmit(1.0F, 22.0F);
    this.draw.lightMap().put("light", this.light);

    createNetObject(paramActorSpawnArg.netChannel, paramActorSpawnArg.netIdRemote);

    this.heightAboveLandSurface = 0.0F;
    Align();

    this.hashCode = rnd.nextInt(0, 4095);
  }

  private void Align() {
    this.pos.getAbs(p);
    p.z = (Engine.land().HQ(p.x, p.y) + this.heightAboveLandSurface);
    o.setYPR(this.pos.getAbsOrient().getYaw(), 0.0F, 0.0F);
    this.pos.setAbs(p, o);
  }

  public void align()
  {
    Align();
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

  public void netFirstUpdate(NetChannel paramNetChannel) throws IOException
  {
  }

  public static class SPAWN implements ActorSpawn
  {
    public Class cls;

    public SPAWN(Class paramClass) {
      Property.set(paramClass, "iconName", "icons/unknown.mat");
      Property.set(paramClass, "meshName", CampfireGeneric.mesh_name);

      this.cls = paramClass;
      Spawn.add(this.cls, this);
    }

    public Actor actorSpawn(ActorSpawnArg paramActorSpawnArg) {
      CampfireGeneric localCampfireGeneric = null;
      try
      {
        CampfireGeneric.access$402(paramActorSpawnArg);
        localCampfireGeneric = (CampfireGeneric)this.cls.newInstance();
        CampfireGeneric.access$402(null);
      } catch (Exception localException) {
        CampfireGeneric.access$402(null);
        System.out.println(localException.getMessage());
        localException.printStackTrace();
        System.out.println("SPAWN: Can't create CampfireGeneric object [class:" + this.cls.getName() + "]");

        return null;
      }
      return localCampfireGeneric;
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

  class MyDraw extends ActorMeshDraw
  {
    MyDraw()
    {
    }

    public void lightUpdate(Loc paramLoc, boolean paramBoolean)
    {
      if (paramBoolean)
      {
        RandomVector.getTimed(Time.currentReal() * 24L, CampfireGeneric.tmpv, CampfireGeneric.this.hashCode);

        float f = Math.abs((float)CampfireGeneric.tmpv.x);
        CampfireGeneric.this.light.light.setEmit(0.3F + f * 0.9F, 27.0F + f * 8.0F);
      }
      super.lightUpdate(paramLoc, paramBoolean);
    }
  }
}