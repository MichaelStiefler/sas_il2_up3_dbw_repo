package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.NearestTargets;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeX4Carrier;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;

public class RocketX4 extends Rocket
{
  private FlightModel fm = null;
  private Eff3DActor fl1;
  private Eff3DActor fl2;
  private static Orient or = new Orient();
  private static Point3d p = new Point3d();
  private static Point3d pT = new Point3d();
  private static Vector3d v = new Vector3d();
  private static Actor hunted = null;
  private long tStart = 0L;

  private float prevd = 1000.0F;

  public boolean interpolateStep()
  {
    float f1 = Time.tickLenFs();
    float f2 = (float)getSpeed(null);
    f2 += (320.0F - f2) * 0.1F * f1;
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(p, or);
    v.set(1.0D, 0.0D, 0.0D); or.transform(v);
    v.scale(f2);
    setSpeed(v);
    p.jdField_x_of_type_Double += v.jdField_x_of_type_Double * f1;
    p.jdField_y_of_type_Double += v.jdField_y_of_type_Double * f1;
    p.jdField_z_of_type_Double += v.jdField_z_of_type_Double * f1;

    if ((isNet()) && (isNetMirror())) {
      this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(p, or);
      return false;
    }

    if (Actor.isValid(getOwner())) {
      if ((getOwner() != World.getPlayerAircraft()) || (!((RealFlightModel)this.fm).isRealMode()))
      {
        if ((this.fm instanceof Pilot)) {
          Pilot localPilot = (Pilot)this.fm;
          if (localPilot.jdField_target_of_type_ComMaddoxIl2FmFlightModel != null) {
            localPilot.jdField_target_of_type_ComMaddoxIl2FmFlightModel.Loc.get(pT);
            pT.sub(p);
            or.transformInv(pT);
            if (pT.jdField_x_of_type_Double > -10.0D) {
              double d = Aircraft.cvt(this.fm.Skill, 0.0F, 3.0F, 15.0F, 0.0F);
              if (pT.jdField_y_of_type_Double > d) {
                ((TypeX4Carrier)this.fm.jdField_actor_of_type_ComMaddoxIl2EngineActor).typeX4CAdjSideMinus();
              }
              if (pT.jdField_y_of_type_Double < -d) {
                ((TypeX4Carrier)this.fm.jdField_actor_of_type_ComMaddoxIl2EngineActor).typeX4CAdjSidePlus();
              }
              if (pT.jdField_z_of_type_Double < -d) {
                ((TypeX4Carrier)this.fm.jdField_actor_of_type_ComMaddoxIl2EngineActor).typeX4CAdjAttitudeMinus();
              }
              if (pT.jdField_z_of_type_Double > d) {
                ((TypeX4Carrier)this.fm.jdField_actor_of_type_ComMaddoxIl2EngineActor).typeX4CAdjAttitudePlus();
              }
            }
          }
        }
      }

      or.increment(50.0F * f1 * ((TypeX4Carrier)this.fm.jdField_actor_of_type_ComMaddoxIl2EngineActor).typeX4CgetdeltaAzimuth(), 50.0F * f1 * ((TypeX4Carrier)this.fm.jdField_actor_of_type_ComMaddoxIl2EngineActor).typeX4CgetdeltaTangage(), 0.0F);

      or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
      ((TypeX4Carrier)this.fm.jdField_actor_of_type_ComMaddoxIl2EngineActor).typeX4CResetControls();
    }

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(p, or);

    if (Time.current() > this.tStart + 500L) {
      hunted = NearestTargets.getEnemy(0, -1, p, 800.0D, 0);
      if (Actor.isValid(hunted)) {
        float f3 = (float)p.distance(hunted.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
        if (((hunted instanceof Aircraft)) && ((f3 < 20.0F) || ((f3 < 40.0F) && (f3 > this.prevd) && (this.prevd != 1000.0F)))) {
          doExplosionAir();
          postDestroy();
          collide(false);
          drawing(false);
        }
        this.prevd = f3;
      } else {
        this.prevd = 1000.0F;
      }

    }

    if ((!Actor.isValid(getOwner())) || (!(getOwner() instanceof Aircraft))) {
      doExplosionAir();
      postDestroy();
      collide(false);
      drawing(false);
      return false;
    }
    return false;
  }

  public RocketX4()
  {
  }

  public RocketX4(Actor paramActor, NetChannel paramNetChannel, int paramInt, Point3d paramPoint3d, Orient paramOrient, float paramFloat)
  {
    this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new Mirror(this, paramNetChannel, paramInt);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(paramPoint3d, paramOrient);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(paramActor, null, true);
    doStart(-1.0F);
    v.set(1.0D, 0.0D, 0.0D); paramOrient.transform(v);
    v.scale(paramFloat);
    setSpeed(v);
    collide(false);
  }

  public void start(float paramFloat)
  {
    Actor localActor = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base();
    if ((Actor.isValid(localActor)) && ((localActor instanceof Aircraft))) {
      if (localActor.isNetMirror()) {
        destroy();
        return;
      }
      this.jdField_net_of_type_ComMaddoxIl2EngineActorNet = new Master(this);
    }

    doStart(paramFloat);
  }

  private void doStart(float paramFloat) {
    super.start(-1.0F);
    this.fm = ((Aircraft)getOwner()).FM;
    this.tStart = Time.current();
    if (Config.isUSE_RENDER()) {
      this.fl1 = Eff3DActor.New(this, findHook("_NavLightR"), null, 1.0F, "3DO/Effects/Fireworks/FlareRed.eff", -1.0F);
      this.fl2 = Eff3DActor.New(this, findHook("_NavLightG"), null, 1.0F, "3DO/Effects/Fireworks/FlareGreen.eff", -1.0F);
      this.flame.drawing(false);
    }

    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(p, or);
    or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(p, or);
  }
  public void destroy() {
    if ((isNet()) && (isNetMirror()))
      doExplosionAir();
    if (Config.isUSE_RENDER()) {
      Eff3DActor.finish(this.fl1);
      Eff3DActor.finish(this.fl2);
    }
    super.destroy();
  }

  protected void doExplosion(Actor paramActor, String paramString)
  {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getTime(Time.current(), p);
    MsgExplosion.send(paramActor, paramString, p, getOwner(), 45.0F, 2.0F, 1, 550.0F);
    super.doExplosion(paramActor, paramString);
  }
  protected void doExplosionAir() {
    this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getTime(Time.current(), p);
    MsgExplosion.send(null, null, p, getOwner(), 45.0F, 2.0F, 1, 550.0F);
    super.doExplosionAir();
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel)
    throws IOException
  {
    NetMsgSpawn localNetMsgSpawn = super.netReplicate(paramNetChannel);
    localNetMsgSpawn.writeNetObj(getOwner().jdField_net_of_type_ComMaddoxIl2EngineActorNet);
    Point3d localPoint3d = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    localNetMsgSpawn.writeFloat((float)localPoint3d.jdField_x_of_type_Double); localNetMsgSpawn.writeFloat((float)localPoint3d.jdField_y_of_type_Double); localNetMsgSpawn.writeFloat((float)localPoint3d.jdField_z_of_type_Double);
    Orient localOrient = this.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient();
    localNetMsgSpawn.writeFloat(localOrient.azimut()); localNetMsgSpawn.writeFloat(localOrient.tangage());
    float f = (float)getSpeed(null);
    localNetMsgSpawn.writeFloat(f);
    return localNetMsgSpawn;
  }

  static
  {
    Class localClass = RocketX4.class;
    Property.set(localClass, "mesh", "3do/arms/X-4/mono.sim");

    Property.set(localClass, "sprite", "3DO/Effects/Tracers/GuidedRocket/Black.eff");
    Property.set(localClass, "flame", "3do/effects/rocket/mono.sim");
    Property.set(localClass, "smoke", "3DO/Effects/Tracers/GuidedRocket/White.eff");
    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 50.0F);
    Property.set(localClass, "emitMax", 1.0F);

    Property.set(localClass, "sound", "weapon.rocket_132");

    Property.set(localClass, "radius", 40.0F);
    Property.set(localClass, "timeLife", 30.0F);
    Property.set(localClass, "timeFire", 33.0F);
    Property.set(localClass, "force", 15712.0F);

    Property.set(localClass, "power", 2.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 1.049F);
    Property.set(localClass, "massa", 60.0F);
    Property.set(localClass, "massaEnd", 45.0F);

    Spawn.add(localClass, new SPAWN());
  }

  static class SPAWN
    implements NetSpawn
  {
    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput)
    {
      try
      {
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        if (localNetObj == null)
          return;
        Actor localActor = (Actor)localNetObj.superObj();
        Point3d localPoint3d = new Point3d(paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat());
        Orient localOrient = new Orient(paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), 0.0F);
        float f = paramNetMsgInput.readFloat();
        RocketX4 localRocketX4 = new RocketX4(localActor, paramNetMsgInput.channel(), paramInt, localPoint3d, localOrient, f);
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public void msgNetNewChannel(NetChannel paramNetChannel) {
      if (!Actor.isValid(actor()))
        return;
      try {
        if (paramNetChannel.isMirrored(this))
          return;
        if (paramNetChannel.userState == 0) {
          NetMsgSpawn localNetMsgSpawn = actor().netReplicate(paramNetChannel);
          if (localNetMsgSpawn != null) {
            postTo(paramNetChannel, localNetMsgSpawn);
            actor().netFirstUpdate(paramNetChannel);
          }
        }
      } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted())
        return false;
      if (isMirrored()) {
        this.out.unLockAndSet(paramNetMsgInput, 0);
        postReal(Message.currentTime(true), this.out);
      }
      RocketX4.p.x = paramNetMsgInput.readFloat();
      RocketX4.p.y = paramNetMsgInput.readFloat();
      RocketX4.p.z = paramNetMsgInput.readFloat();

      int i = paramNetMsgInput.readShort();
      int j = paramNetMsgInput.readShort();
      float f1 = -(i * 180.0F / 32000.0F);
      float f2 = j * 90.0F / 32000.0F;
      RocketX4.or.set(f1, f2, 0.0F);
      RocketX4.this.pos.setAbs(RocketX4.p, RocketX4.or);

      return true;
    }
    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4) {
      super(paramInt, i);
    }
  }

  class Master extends ActorNet
    implements NetUpdate
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public void msgNetNewChannel(NetChannel paramNetChannel) {
      if (!Actor.isValid(actor()))
        return;
      try {
        if (paramNetChannel.isMirrored(this))
          return;
        if (paramNetChannel.userState == 0) {
          NetMsgSpawn localNetMsgSpawn = actor().netReplicate(paramNetChannel);
          if (localNetMsgSpawn != null) {
            postTo(paramNetChannel, localNetMsgSpawn);
            actor().netFirstUpdate(paramNetChannel);
          }
        }
      } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      return false;
    }

    public void netUpdate() {
      try {
        this.out.unLockAndClear();
        RocketX4.this.pos.getAbs(RocketX4.p, RocketX4.or);
        this.out.writeFloat((float)RocketX4.p.x); this.out.writeFloat((float)RocketX4.p.y); this.out.writeFloat((float)RocketX4.p.z);
        RocketX4.or.wrap();
        int i = (int)(RocketX4.or.getYaw() * 32000.0F / 180.0F);
        int j = (int)(RocketX4.or.tangage() * 32000.0F / 90.0F);
        this.out.writeShort(i); this.out.writeShort(j);
        post(Time.current(), this.out); } catch (Exception localException) {
        NetObj.printDebug(localException);
      }
    }

    public Master(Actor arg2) {
      super();
    }
  }
}