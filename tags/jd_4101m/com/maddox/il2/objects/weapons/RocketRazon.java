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
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeGuidedBombCarrier;
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

public class RocketRazon extends RocketBomb
{
  private FlightModel fm;
  private Maneuver maneuver;
  private Actor target;
  private Eff3DActor fl1;
  private Eff3DActor fl2;
  private static Orient or = new Orient();
  private static Point3d p = new Point3d();
  private static Point3d pT = new Point3d();
  private static Vector3d v = new Vector3d();
  private static Vector3d pOld = new Vector3d();
  private static Vector3d pNew = new Vector3d();
  private static Actor hunted = null;
  private long tStart;
  private float prevd;
  private static double azimuthControlScaleFact = 0.9D;
  private static double tangageControlScaleFact = 0.9D;
  private boolean first = true;

  public static String lastBombAlive = "NONE";
  public boolean isMaster = false;

  void endMaster()
  {
    if (isMaster())
    {
      if ((getOwner() != null) && ((getOwner() instanceof TypeGuidedBombCarrier)))
      {
        TypeGuidedBombCarrier localTypeGuidedBombCarrier = (TypeGuidedBombCarrier)(TypeGuidedBombCarrier)getOwner();
        localTypeGuidedBombCarrier.typeGuidedBombCsetMasterAlive(false);
      }
    }
  }

  boolean isMaster()
  {
    return this.isMaster;
  }

  public boolean interpolateStep()
  {
    float f1 = Time.tickLenFs();
    this.pos.getAbs(p, or);

    if ((World.Rnd().nextFloat() > 0.8F) && 
      (this.fl1 != null))
    {
      mydebug("setIntesity() fl1");
      Eff3DActor.setIntesity(this.fl1, World.Rnd().nextFloat(0.1F, 2.5F));
    }

    mydebug("****************** BOMB NAME |" + name() + "|");
    if (this.first)
    {
      mydebug("INTERPOLATE ");
      mydebug("V = " + this.speed);
      mydebug("POS = " + p);
      mydebug("TICKLEN = " + f1);
      this.first = false;
    }
    mydebug(p.x + "| " + p.y + "| " + p.z);

    if (Actor.isValid(getOwner()))
    {
      if (((getOwner() != World.getPlayerAircraft()) || (!((RealFlightModel)this.fm).isRealMode())) && ((this.fm instanceof Maneuver)))
      {
        if (this.target != null)
        {
          mydebug("Target =  " + this.target.name());
          pT = this.target.pos.getAbsPoint();

          Point3d localPoint3d = new Point3d();
          localPoint3d.x = pT.x;
          localPoint3d.y = pT.y;

          localPoint3d.z = (pT.z + 2.5D);

          mydebug("Target pos =  " + localPoint3d);
          mydebug("Bomb pos =  " + p);

          localPoint3d.sub(p);
          or.transformInv(localPoint3d);

          mydebug("Target error =  " + localPoint3d);

          localObject = (Aircraft)getOwner();
          int i = (((Aircraft)localObject).FM.isCapableOfACM()) && (!((Aircraft)localObject).FM.isReadyToDie()) && (!((Aircraft)localObject).FM.isTakenMortalDamage()) && (!((Aircraft)localObject).FM.AS.bIsAboutToBailout) && (!((Aircraft)localObject).FM.AS.isPilotDead(1)) ? 1 : 0;

          if ((localPoint3d.x > -10.0D) && (i != 0))
          {
            double d;
            if (isMaster())
              d = Aircraft.cvt(this.fm.Skill * ((Aircraft)localObject).FM.AS.getPilotHealth(1), 0.0F, 3.0F, 25.0F, 0.0F);
            else {
              d = Aircraft.cvt(this.fm.Skill * ((Aircraft)localObject).FM.AS.getPilotHealth(1), 0.0F, 3.0F, 100.0F, 20.0F);
            }

            ((TypeX4Carrier)this.fm.actor).typeX4CResetControls();

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

      if ((getOwner() != null) && ((getOwner() instanceof TypeGuidedBombCarrier)))
      {
        TypeGuidedBombCarrier localTypeGuidedBombCarrier = (TypeGuidedBombCarrier)(TypeGuidedBombCarrier)getOwner();
        mydebug("isMasterAlive " + ((Actor)localTypeGuidedBombCarrier).name() + " " + localTypeGuidedBombCarrier.typeGuidedBombCisMasterAlive());
        mydebug("isMaster() = " + isMaster());
        mydebug("last bomb alive = " + lastBombAlive);
      }

      if (lastBombAlive.equals(name()))
      {
        ((TypeX4Carrier)this.fm.actor).typeX4CResetControls();
      }

      mydebug("correction: " + localObject);

      if ((((Vector3d)localObject).y != 0.0D) || (((Vector3d)localObject).z != 0.0D))
      {
        this.pos.getAbs(Or);

        Or.transform((Tuple3d)localObject);
        spd.add((Tuple3d)localObject);
        float f3 = (float)spd.length();
        float f4 = f2 / f3;
        spd.scale(f4);

        setSpeed(spd);
      }

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

  public RocketRazon()
  {
    this.fm = null;
    this.tStart = 0L;
    this.prevd = 1000.0F;
  }

  public RocketRazon(Actor paramActor, NetChannel paramNetChannel, int paramInt, Point3d paramPoint3d, Orient paramOrient, float paramFloat)
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
      this.fl1 = Eff3DActor.New(this, findHook("_NavLightR"), null, 1.0F, "3DO/Effects/Fireworks/FlareWhite1.eff", -1.0F);

      if (this.fl1 != null)
      {
        mydebug("setIntesity()");
        Eff3DActor.setIntesity(this.fl1, 0.1F);
      }
    }
    this.pos.getAbs(p, or);
    or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
    this.pos.setAbs(p, or);

    if ((getOwner() != null) && ((getOwner() instanceof TypeGuidedBombCarrier)))
    {
      localObject = (TypeGuidedBombCarrier)(TypeGuidedBombCarrier)getOwner();
      if (!((TypeGuidedBombCarrier)localObject).typeGuidedBombCisMasterAlive())
      {
        this.isMaster = true;
        ((TypeGuidedBombCarrier)localObject).typeGuidedBombCsetMasterAlive(true);
      }
    }

    if (getOwner() == World.getPlayerAircraft())
    {
      lastBombAlive = name();
    }

    getSpeed(spd);
    this.pos.getAbs(p, or);
    Object localObject = new Vector3d(0.0D, 0.0D, 0.0D);

    mydebug("BombDispersion!");

    mydebug("Speed BEFORE = " + spd.x + " " + spd.y + " " + spd.z + " ");
    localObject.x += World.Rnd().nextFloat_Dome(-5.0F, 5.0F);
    localObject.y += World.Rnd().nextFloat_Dome(-2.2F, 2.2F);

    mydebug("error: " + localObject);

    or.transform((Tuple3d)localObject);
    spd.add((Tuple3d)localObject);
    setSpeed(spd);

    getSpeed(spd);
    mydebug("Speed AFTER  = " + spd.x + " " + spd.y + " " + spd.z + " ");
  }

  public void destroy()
  {
    if ((isNet()) && (isNetMirror()))
      doExplosionAir();
    if (Config.isUSE_RENDER())
    {
      Eff3DActor.finish(this.fl1);
    }
    endMaster();
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
    Class localClass = RocketRazon.class;

    Property.set(localClass, "mesh", "3do/arms/Razon/mono.sim");

    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 50.0F);
    Property.set(localClass, "emitMax", 0.5F);
    Property.set(localClass, "timeLife", 1000.0F);
    Property.set(localClass, "timeFire", 12.0F);
    Property.set(localClass, "force", 2.0F);
    Property.set(localClass, "massaEnd", 250.0F);
    Property.set(localClass, "radius", 50.0F);
    Property.set(localClass, "power", 125.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 0.32F);
    Property.set(localClass, "massa", 250.0F);
    Property.set(localClass, "sound", "weapon.bomb_mid");

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

        RocketRazon.this.getSpeed(RocketRazon.v);
        this.out.writeFloat((float)RocketRazon.v.x);
        this.out.writeFloat((float)RocketRazon.v.y);
        this.out.writeFloat((float)RocketRazon.v.z);

        RocketRazon.this.mydebug("WRITE DATA: " + RocketRazon.v);

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

      RocketRazon.v.x = paramNetMsgInput.readFloat();
      RocketRazon.v.y = paramNetMsgInput.readFloat();
      RocketRazon.v.z = paramNetMsgInput.readFloat();

      RocketRazon.this.mydebug("READ DATA: " + RocketRazon.v);

      RocketRazon.this.setSpeed(RocketRazon.v);
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
        RocketRazon localRocketRazon = new RocketRazon(localActor, paramNetMsgInput.channel(), paramInt, localPoint3d, localOrient, f);
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
    }
  }
}