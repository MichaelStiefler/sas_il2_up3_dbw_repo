package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Color3f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.MsgExplosion;
import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeX4Carrier;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.ships.Ship.PilotBoatWater_US;
import com.maddox.il2.objects.ships.Ship.PilotWater_JA;
import com.maddox.il2.objects.ships.Ship.PilotWater_US;
import com.maddox.il2.objects.ships.Ship.RwyCon;
import com.maddox.il2.objects.ships.Ship.RwySteel;
import com.maddox.il2.objects.ships.Ship.RwySteelLow;
import com.maddox.il2.objects.ships.Ship.RwyTransp;
import com.maddox.il2.objects.ships.Ship.RwyTranspSqr;
import com.maddox.il2.objects.ships.Ship.RwyTranspWide;
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
import java.util.List;

public class RocketBat extends RocketBomb
{
  private FlightModel fm;
  private Actor target;
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
  private float targetRCSMax = 0.0F;

  public boolean interpolateStep()
  {
    float f1 = Time.tickLenFs();
    this.pos.getAbs(p, or);

    if (this.first)
    {
      this.first = false;
    }

    if (Actor.isValid(getOwner()))
    {
      ((TypeX4Carrier)this.fm.actor).typeX4CResetControls();

      if (this.target != null)
      {
        pT = this.target.pos.getAbsPoint();

        Point3d localPoint3d = new Point3d();
        localPoint3d.x = pT.x;
        localPoint3d.y = pT.y;

        localPoint3d.z = (pT.z + 2.5D);

        localPoint3d.sub(p);
        or.transformInv(localPoint3d);

        if (localPoint3d.x > -10.0D)
        {
          double d;
          if ((this.target instanceof TgtShip))
          {
            d = Aircraft.cvt(this.fm.Skill, 0.0F, 3.0F, 4.0F / this.targetRCSMax, 1.0F / this.targetRCSMax);
          }
          else
          {
            d = Aircraft.cvt(this.fm.Skill, 0.0F, 3.0F, 20.0F, 4.0F);
          }
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
      getSpeed(spd);
      float f2 = (float)spd.length();

      Vector3d localVector3d = new Vector3d(0.0D, 0.0D, 0.0D);
      localVector3d.y = (-azimuthControlScaleFact * f2 * ((TypeX4Carrier)this.fm.actor).typeX4CgetdeltaAzimuth());
      localVector3d.z = (tangageControlScaleFact * f2 * ((TypeX4Carrier)this.fm.actor).typeX4CgetdeltaTangage());

      if ((localVector3d.y != 0.0D) || (localVector3d.z != 0.0D))
      {
        f2 *= 0.9992F;
      }

      this.pos.getAbs(Or);

      Or.transform(localVector3d);

      localVector3d.z += f2 * 0.007D * f1 * Atmosphere.g();

      spd.add(localVector3d);

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

  public RocketBat()
  {
    this.fm = null;
    this.tStart = 0L;
    this.prevd = 1000.0F;
  }

  public RocketBat(Actor paramActor, NetChannel paramNetChannel, int paramInt, Point3d paramPoint3d, Orient paramOrient, float paramFloat)
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

    this.tStart = Time.current();
    this.pos.getAbs(p, or);
    or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
    this.pos.setAbs(p, or);

    this.fm = ((Aircraft)getOwner()).FM;

    List localList = Engine.targets();
    int i = localList.size();

    float f1 = 0.0F;
    Object localObject = null;

    for (int j = 0; j < i; j++)
    {
      Actor localActor = (Actor)localList.get(j);

      if ((!(localActor instanceof TgtShip)) && (!(localActor instanceof BridgeSegment)))
        continue;
      Point3d localPoint3d1 = new Point3d();
      Point3d localPoint3d2 = localActor.pos.getAbsPoint();

      localPoint3d1.x = localPoint3d2.x;
      localPoint3d1.y = localPoint3d2.y;
      localPoint3d1.z = localPoint3d2.z;

      this.pos.getAbs(p, or);

      localPoint3d1.sub(p);
      or.transformInv(localPoint3d1);

      float f2 = antennaPattern(localPoint3d1, localActor);

      if ((f2 <= f1) || (f2 <= 0.001D))
      {
        continue;
      }
      f1 = f2;
      localObject = localActor;
      this.targetRCSMax = estimateRCS(localObject);
    }

    this.target = localObject;
  }

  private float antennaPattern(Point3d paramPoint3d, Actor paramActor)
  {
    float f1 = (float)Math.sqrt(paramPoint3d.x * paramPoint3d.x + paramPoint3d.y * paramPoint3d.y + paramPoint3d.z * paramPoint3d.z);

    if (f1 > 32000.0F) {
      return 0.0F;
    }
    float f2 = (float)Math.atan2(paramPoint3d.y, paramPoint3d.x);

    float f3 = (float)Math.sqrt(paramPoint3d.x * paramPoint3d.x + paramPoint3d.y * paramPoint3d.y);

    float f4 = (float)Math.atan2(paramPoint3d.z, f3);

    f4 += 0.2617992F;

    f1 /= 1000.0F;
    double d;
    if ((Math.cos(f2) > 0.0D) && (Math.cos(f4) > 0.0D))
      d = Math.cos(f2) * Math.cos(f4) / (f1 * f1);
    else {
      d = 0.0D;
    }

    if ((d > 0.0D) && ((paramActor instanceof TgtShip)))
    {
      float f5 = estimateRCS(paramActor);

      d *= f5;
    }
    return (float)d;
  }

  private float estimateRCS(Actor paramActor)
  {
    if (((paramActor instanceof Ship.PilotWater_US)) || ((paramActor instanceof Ship.PilotBoatWater_US)) || ((paramActor instanceof Ship.PilotWater_JA)) || ((paramActor instanceof Ship.RwyCon)) || ((paramActor instanceof Ship.RwySteel)) || ((paramActor instanceof Ship.RwySteelLow)) || ((paramActor instanceof Ship.RwyTransp)) || ((paramActor instanceof Ship.RwyTranspWide)) || ((paramActor instanceof Ship.RwyTranspSqr)))
    {
      return 0.0F;
    }
    float f = 0.0F;
    f = paramActor.collisionR();
    if (f < 5.0F) {
      f = 5.0F;
    }

    return f / 40.0F;
  }

  public void destroy()
  {
    if ((isNet()) && (isNetMirror()))
      doExplosionAir();
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
    Class localClass = RocketBat.class;
    Property.set(localClass, "mesh", "3do/arms/Bat/mono.sim");
    Property.set(localClass, "sound", "weapon.bomb_std");
    Property.set(localClass, "emitColor", new Color3f(1.0F, 1.0F, 0.5F));
    Property.set(localClass, "emitLen", 0.0F);
    Property.set(localClass, "emitMax", 0.0F);
    Property.set(localClass, "radius", 100.0F);
    Property.set(localClass, "timeLife", 1000.0F);
    Property.set(localClass, "timeFire", 12.0F);
    Property.set(localClass, "force", 2.0F);
    Property.set(localClass, "power", 250.0F);
    Property.set(localClass, "powerType", 0);
    Property.set(localClass, "kalibr", 1.1F);
    Property.set(localClass, "massa", 853.0F);
    Property.set(localClass, "massaEnd", 853.0F);
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

        RocketBat.this.getSpeed(RocketBat.v);
        this.out.writeFloat((float)RocketBat.v.x);
        this.out.writeFloat((float)RocketBat.v.y);
        this.out.writeFloat((float)RocketBat.v.z);

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

      RocketBat.v.x = paramNetMsgInput.readFloat();
      RocketBat.v.y = paramNetMsgInput.readFloat();
      RocketBat.v.z = paramNetMsgInput.readFloat();

      RocketBat.this.setSpeed(RocketBat.v);
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
        RocketBat localRocketBat = new RocketBat(localActor, paramNetMsgInput.channel(), paramInt, localPoint3d, localOrient, f);
      }
      catch (Exception localException)
      {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
    }
  }
}