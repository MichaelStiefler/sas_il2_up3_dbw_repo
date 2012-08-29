package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
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

public class RocketHS_293 extends RocketBomb
{
  private FlightModel fm;
  private Maneuver maneuver;
  private Actor target;
  private Eff3DActor fl1;
  private Eff3DActor fl2;
  private Eff3DActor smoke01;
  private static Orient or = new Orient();
  private static Point3d p = new Point3d();
  private static Point3d pT = new Point3d();
  private static Vector3d v = new Vector3d();
  private static Actor hunted = null;
  private long tStart;
  private long tEStart;
  private float prevd;
  private static double azimuthControlScaleFact = 1.0D;
  private static double tangageControlScaleFact = 1.0D;
  private boolean first = true;

  public boolean interpolateStep()
  {
    if (this.tEStart > 0L)
    {
      if (Time.current() > this.tEStart)
      {
        this.tEStart = -1L;
        setThrust(17500.0F);
        if (Config.isUSE_RENDER())
        {
          if (this.smoke != null)
            Eff3DActor.setIntesity(this.smoke, 1.0F);
          if (this.sprite != null)
            Eff3DActor.setIntesity(this.sprite, 1.0F);
          if (this.flame != null)
            this.flame.drawing(true);
          this.light.light.setEmit(2.0F, 100.0F);
        }
      }

    }

    if ((World.Rnd().nextFloat() > 0.8F) && 
      (this.fl1 != null))
    {
      Eff3DActor.setIntesity(this.fl1, World.Rnd().nextFloat(0.1F, 2.5F));
    }

    if ((World.Rnd().nextFloat() > 0.8F) && 
      (this.fl2 != null))
    {
      Eff3DActor.setIntesity(this.fl2, World.Rnd().nextFloat(0.1F, 2.5F));
    }

    float f1 = Time.tickLenFs();
    this.pos.getAbs(p, or);

    if (this.first)
    {
      this.first = false;
    }

    if (Actor.isValid(getOwner()))
    {
      if (((getOwner() != World.getPlayerAircraft()) || (!((RealFlightModel)this.fm).isRealMode())) && ((this.fm instanceof Maneuver)))
      {
        if (this.target != null)
        {
          pT = this.target.pos.getAbsPoint();

          Point3d localPoint3d = new Point3d();
          localPoint3d.x = pT.x;
          localPoint3d.y = pT.y;
          localPoint3d.z = (pT.z + 2.5D);

          localPoint3d.sub(p);
          or.transformInv(localPoint3d);

          localObject = (Aircraft)getOwner();
          int i = (((Aircraft)localObject).FM.isCapableOfACM()) && (!((Aircraft)localObject).FM.isReadyToDie()) && (!((Aircraft)localObject).FM.isTakenMortalDamage()) && (!((Aircraft)localObject).FM.AS.bIsAboutToBailout) && (!((Aircraft)localObject).FM.AS.isPilotDead(1)) ? 1 : 0;

          if ((localPoint3d.x > -10.0D) && (i != 0))
          {
            double d = Aircraft.cvt(this.fm.Skill * ((Aircraft)localObject).FM.AS.getPilotHealth(1), 0.0F, 3.0F, 10.0F, 2.0F);
            if (localPoint3d.y > d)
              ((TypeX4Carrier)this.fm.actor).typeX4CAdjSideMinus();
            if (localPoint3d.y < -d)
              ((TypeX4Carrier)this.fm.actor).typeX4CAdjSidePlus();
            if (localPoint3d.z < -d)
              ((TypeX4Carrier)this.fm.actor).typeX4CAdjAttitudeMinus();
            if (localPoint3d.z > d) {
              ((TypeX4Carrier)this.fm.actor).typeX4CAdjAttitudePlus();
            }
          }
        }
      }
      getSpeed(spd);
      float f2 = (float)spd.length();

      Object localObject = new Vector3d(0.0D, 0.0D, 0.0D);
      ((Vector3d)localObject).y = (-azimuthControlScaleFact * f2 * ((TypeX4Carrier)this.fm.actor).typeX4CgetdeltaAzimuth());
      ((Vector3d)localObject).z = (tangageControlScaleFact * f2 * ((TypeX4Carrier)this.fm.actor).typeX4CgetdeltaTangage());

      if ((((Vector3d)localObject).y != 0.0D) || (((Vector3d)localObject).z != 0.0D))
      {
        f2 *= 0.9992F;
      }

      this.pos.getAbs(Or);

      Or.transform((Tuple3d)localObject);

      localObject.z += f2 * 0.0018D * f1 * Atmosphere.g() * this.Minit / this.M;

      if (this.isThrust) {
        localObject.z += 0.032D * f1 * Atmosphere.g() * this.Minit / this.M;
      }

      spd.add((Tuple3d)localObject);

      float f3 = (float)spd.length();
      float f4 = f2 / f3;
      spd.scale(f4);

      setSpeed(spd);

      ((TypeX4Carrier)this.fm.actor).typeX4CResetControls();
    }

    if ((!Actor.isValid(getOwner())) || (!(getOwner() instanceof Aircraft)))
    {
      doExplosionAir();
      postDestroy();
      collide(false);
      drawing(false);
      return true;
    }

    return true;
  }

  public RocketHS_293()
  {
    this.fm = null;
    this.tStart = 0L;
    this.prevd = 1000.0F;
  }

  public RocketHS_293(Actor paramActor, NetChannel paramNetChannel, int paramInt, Point3d paramPoint3d, Orient paramOrient, float paramFloat)
  {
    this.fm = null;
    this.tStart = 0L;
    this.prevd = 1000.0F;
    this.net = new Mirror(this, paramNetChannel, paramInt);
    this.pos.setAbs(paramPoint3d, paramOrient);
    this.pos.reset();
    this.pos.setBase(paramActor, null, true);
    doStart(-1.0F);
    v.set(1.0D, 0.0D, 0.0D);
    paramOrient.transform(v);
    v.scale(paramFloat);
    setSpeed(v);
    collide(false);
  }

  public void start(float paramFloat)
  {
    Actor localActor = this.pos.base();
    if ((Actor.isValid(localActor)) && ((localActor instanceof Aircraft)))
    {
      if (localActor.isNetMirror())
      {
        destroy();
        return;
      }
      this.net = new Master(this);
    }
    doStart(paramFloat);
  }

  private void doStart(float paramFloat)
  {
    super.start(-1.0F);
    this.fm = ((Aircraft)getOwner()).FM;
    if ((this.fm instanceof Maneuver))
    {
      this.maneuver = ((Maneuver)this.fm);
      this.target = this.maneuver.target_ground;
    }

    this.tStart = Time.current();
    if (Config.isUSE_RENDER())
    {
      this.fl1 = Eff3DActor.New(this, findHook("_NavLightR"), null, 1.0F, "3DO/Effects/Fireworks/FlareBlue1.eff", -1.0F);
      this.fl2 = Eff3DActor.New(this, findHook("_NavLightG"), null, 1.0F, "3DO/Effects/Fireworks/FlareBlue1.eff", -1.0F);
      if (this.flame != null)
        this.flame.drawing(false);
      if (this.fl1 != null)
      {
        Eff3DActor.setIntesity(this.fl1, 10.0F);
      }
      Hook localHook = null;
      localHook = findHook("_SMOKE01");
      this.smoke01 = Eff3DActor.New(this, localHook, null, 1.0F, "Effects/Smokes/SmokeBlack_planeTrail.eff", -1.0F);
      if (this.smoke01 != null) {
        this.smoke01.pos.changeHookToRel();
      }

    }

    this.noGDelay = -1L;
    this.tEStart = (Time.current() + 1000L);
    if (Config.isUSE_RENDER()) {
      if (this.smoke != null)
        Eff3DActor.setIntesity(this.smoke, 0.0F);
      if (this.sprite != null)
        Eff3DActor.setIntesity(this.sprite, 0.0F);
      if (this.flame != null)
        this.flame.drawing(false);
      this.light.light.setEmit(0.0F, 0.0F);
    }

    this.pos.getAbs(p, or);
    or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
    this.pos.setAbs(p, or);
  }

  public void destroy()
  {
    if ((isNet()) && (isNetMirror()))
      doExplosionAir();
    if (Config.isUSE_RENDER())
    {
      Eff3DActor.finish(this.fl1);
      Eff3DActor.finish(this.fl2);
      if (this.smoke01 != null) {
        Eff3DActor.finish(this.smoke01);
      }
    }
    super.destroy();
  }

  protected void doExplosionAir()
  {
    this.pos.getTime(Time.current(), p);
    MsgExplosion.send(null, null, p, getOwner(), 45.0F, 2.0F, 1, 550.0F);
    super.doExplosionAir();
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel)
    throws IOException
  {
    NetMsgSpawn localNetMsgSpawn = super.netReplicate(paramNetChannel);
    localNetMsgSpawn.writeNetObj(getOwner().net);
    Point3d localPoint3d = this.pos.getAbsPoint();
    localNetMsgSpawn.writeFloat((float)localPoint3d.x);
    localNetMsgSpawn.writeFloat((float)localPoint3d.y);
    localNetMsgSpawn.writeFloat((float)localPoint3d.z);
    Orient localOrient = this.pos.getAbsOrient();
    localNetMsgSpawn.writeFloat(localOrient.azimut());
    localNetMsgSpawn.writeFloat(localOrient.tangage());
    float f = (float)getSpeed(null);
    localNetMsgSpawn.writeFloat(f);
    return localNetMsgSpawn;
  }

  protected void mydebug(String paramString)
  {
  }

  static
  {
    Class localClass = RocketHS_293.class;
    Property.set(localClass, "mesh", "3do/arms/HS-293/mono.sim");

    Property.set(localClass, "smoke", "3DO/Effects/Rocket/rocketsmokewhite.eff");

    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 50.0F);
    Property.set(localClass, "emitMax", 0.5F);
    Property.set(localClass, "sound", "weapon.bomb_std");
    Property.set(localClass, "radius", 100.0F);
    Property.set(localClass, "timeLife", 1000.0F);
    Property.set(localClass, "timeFire", 12.0F);
    Property.set(localClass, "force", 0.0F);
    Property.set(localClass, "power", 210.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 1.1F);
    Property.set(localClass, "massa", 1045.0F);
    Property.set(localClass, "massaEnd", 700.0F);
    Spawn.add(localClass, new SPAWN());
  }

  class Master extends ActorNet
    implements NetUpdate
  {
    NetMsgFiltered out;

    public void msgNetNewChannel(NetChannel paramNetChannel)
    {
      if (!Actor.isValid(actor()))
        return;
      if (paramNetChannel.isMirrored(this))
        return;
      try
      {
        if (paramNetChannel.userState == 0)
        {
          NetMsgSpawn localNetMsgSpawn = actor().netReplicate(paramNetChannel);
          if (localNetMsgSpawn != null)
          {
            postTo(paramNetChannel, localNetMsgSpawn);
            actor().netFirstUpdate(paramNetChannel);
          }
        }
      }
      catch (Exception localException)
      {
        NetObj.printDebug(localException);
      }
    }

    public boolean netInput(NetMsgInput paramNetMsgInput)
      throws IOException
    {
      return false;
    }

    public void netUpdate()
    {
      try
      {
        this.out.unLockAndClear();

        RocketHS_293.this.getSpeed(RocketHS_293.v);
        this.out.writeFloat((float)RocketHS_293.v.x);
        this.out.writeFloat((float)RocketHS_293.v.y);
        this.out.writeFloat((float)RocketHS_293.v.z);

        post(Time.current(), this.out);
      }
      catch (Exception localException)
      {
        NetObj.printDebug(localException);
      }
    }

    public Master(Actor arg2)
    {
      super();
      this.out = new NetMsgFiltered();
    }
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out;

    public void msgNetNewChannel(NetChannel paramNetChannel)
    {
      if (!Actor.isValid(actor()))
        return;
      if (paramNetChannel.isMirrored(this))
        return;
      try
      {
        if (paramNetChannel.userState == 0)
        {
          NetMsgSpawn localNetMsgSpawn = actor().netReplicate(paramNetChannel);
          if (localNetMsgSpawn != null)
          {
            postTo(paramNetChannel, localNetMsgSpawn);
            actor().netFirstUpdate(paramNetChannel);
          }
        }
      }
      catch (Exception localException)
      {
        NetObj.printDebug(localException);
      }
    }

    public boolean netInput(NetMsgInput paramNetMsgInput)
      throws IOException
    {
      if (paramNetMsgInput.isGuaranted())
        return false;
      if (isMirrored())
      {
        this.out.unLockAndSet(paramNetMsgInput, 0);
        postReal(Message.currentTime(true), this.out);
      }

      RocketHS_293.v.x = paramNetMsgInput.readFloat();
      RocketHS_293.v.y = paramNetMsgInput.readFloat();
      RocketHS_293.v.z = paramNetMsgInput.readFloat();

      RocketHS_293.this.setSpeed(RocketHS_293.v);
      return true;
    }

    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4)
    {
      super(paramInt, i);
      this.out = new NetMsgFiltered();
    }
  }

  static class SPAWN
    implements NetSpawn
  {
    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput)
    {
      NetObj localNetObj = paramNetMsgInput.readNetObj();
      if (localNetObj == null)
        return;
      try
      {
        Actor localActor = (Actor)localNetObj.superObj();
        Point3d localPoint3d = new Point3d(paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat());
        Orient localOrient = new Orient(paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), 0.0F);
        float f = paramNetMsgInput.readFloat();
        RocketHS_293 localRocketHS_293 = new RocketHS_293(localActor, paramNetMsgInput.channel(), paramInt, localPoint3d, localOrient, f);
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
    }
  }
}