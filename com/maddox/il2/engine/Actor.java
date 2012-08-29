package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.rts.Finger;
import com.maddox.rts.Message;
import com.maddox.rts.MsgDestroy;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.ObjState;
import com.maddox.rts.Time;
import com.maddox.sound.Acoustics;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundList;
import com.maddox.sound.SoundPreset;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Actor extends ObjState
{
  public static final int DRAW = 1;
  public static final int VISIBILITY_AS_BASE = 2;
  public static final int COLLIDE = 16;
  public static final int COLLIDE_AS_POINT = 32;
  public static final int COLLIDE_ON_LAND = 64;
  public static final int COLLIDE_ONLY_THIS = 128;
  public static final int DREAM_FIRE = 256;
  public static final int DREAM_LISTENER = 512;
  public static final int MISSION_SPAWN = 4096;
  public static final int REAL_TIME = 8192;
  public static final int SERVICE = 16384;
  public static final int DESTROYED = 32768;
  public static final int _DEAD = 4;
  public static final int _TASK_COMPLETE = 8;
  protected int flags = 0;
  private String name;
  public ActorNet net;
  private Actor owner;
  protected List ownerAttached;
  private static Object[] emptyArrayOwners = new Object[0];
  public ActorPos pos;
  public Interpolators interp;
  public Mat icon;
  public ActorDraw draw;
  public Acoustics acoustics = null;

  private static boolean bSpawnFromMission = false;

  private static Vector3d _V1 = new Vector3d();
  public static Point3d _tmpPoint = new Point3d();
  public static Orient _tmpOrient = new Orient();
  public static Loc _tmpLoc = new Loc();
  public static double[] _d3 = new double[3];
  public static float[] _f3 = new float[3];
  private int _hash;
  private static int _hashNext = 1;
  private static int _countActors = 0;

  public static boolean isValid(Actor paramActor)
  {
    return (paramActor != null) && (!paramActor.isDestroyed());
  }

  public static boolean isAlive(Actor paramActor)
  {
    return (paramActor != null) && (paramActor.isAlive());
  }

  public boolean isAlive()
  {
    return (this.flags & 0x8004) == 0;
  }

  public void setDiedFlag(boolean paramBoolean)
  {
    if (paramBoolean) {
      if ((this.flags & 0x4) == 0) {
        this.flags |= 4;
        if ((this instanceof Prey)) {
          int i = Engine.targets().indexOf(this);
          if (i >= 0)
            Engine.targets().remove(i);
        }
        if (isValid(this.owner))
          MsgOwner.died(this.owner, this);
      }
    }
    else if ((this.flags & 0x4) != 0) {
      this.flags &= -5;
      if ((this instanceof Prey))
        Engine.targets().add(this);
    }
  }

  public boolean getDiedFlag() {
    return (this.flags & 0x4) != 0;
  }

  public boolean isTaskComplete()
  {
    return (this.flags & 0x8) != 0;
  }

  public void setTaskCompleteFlag(boolean paramBoolean)
  {
    if (paramBoolean) {
      if ((this.flags & 0x8) == 0) {
        this.flags |= 8;
        if (isValid(this.owner))
          MsgOwner.taskComplete(this.owner, this);
      }
    }
    else this.flags &= -9;
  }

  public int getArmy()
  {
    return this.flags >>> 16;
  }
  public void setArmy(int paramInt) {
    this.flags = (paramInt << 16 | this.flags & 0xFFFF);
  }
  public boolean isRealTime() {
    return ((this.flags & 0x2000) != 0) || (Time.isRealOnly());
  }
  public boolean isRealTimeFlag() { return (this.flags & 0x2000) != 0; }

  public boolean isNet() {
    return this.net != null;
  }
  public boolean isNetMaster() {
    return (this.net != null) && (this.net.isMaster());
  }
  public boolean isNetMirror() {
    return (this.net != null) && (this.net.isMirror());
  }
  public boolean isSpawnFromMission() {
    return (this.flags & 0x1000) != 0;
  }

  public void missionStarting()
  {
  }

  public void setName(String paramString)
  {
    if (this.name != null) Engine.cur.name2Actor.remove(this.name);
    this.name = paramString;
    if (paramString != null) Engine.cur.name2Actor.put(this.name, this); 
  }

  public String name()
  {
    return this.name == null ? "NONAME" : this.name;
  }
  public boolean isNamed() {
    return this.name != null;
  }

  public static Actor getByName(String paramString) {
    return (Actor)Engine.cur.name2Actor.get(paramString);
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel)
    throws IOException
  {
    return new NetMsgSpawn(this.net);
  }

  public void netFirstUpdate(NetChannel paramNetChannel)
    throws IOException
  {
  }

  public Actor getOwner()
  {
    return this.owner;
  }

  public boolean isContainOwner(Object paramObject) {
    if (paramObject == null) return false;
    if (this.owner == null) return false;
    if (this.owner.equals(paramObject)) return true;
    return this.owner.isContainOwner(paramObject);
  }

  public Object[] getOwnerAttached()
  {
    if (this.ownerAttached != null) return this.ownerAttached.toArray();
    return emptyArrayOwners;
  }

  public Object[] getOwnerAttached(Object[] paramArrayOfObject)
  {
    if (this.ownerAttached != null) return this.ownerAttached.toArray(paramArrayOfObject);
    return emptyArrayOwners;
  }

  public int getOwnerAttachedCount()
  {
    if (this.ownerAttached != null) return this.ownerAttached.size();
    return 0;
  }

  public int getOwnerAttachedIndex(Object paramObject)
  {
    if (this.ownerAttached != null)
      return this.ownerAttached.indexOf(paramObject);
    return -1;
  }

  public Object getOwnerAttached(int paramInt)
  {
    return this.ownerAttached.get(paramInt);
  }

  public void setOwner(Actor paramActor, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramActor != this.owner) {
      if ((isValid(this.owner)) && (this.owner.ownerAttached != null)) {
        int i = this.owner.ownerAttached.indexOf(this);
        if (i >= 0) {
          this.owner.ownerAttached.remove(i);
          if (paramBoolean2)
            MsgOwner.detach(this.owner, this);
        }
      }
      Actor localActor = this.owner;
      if (isValid(paramActor)) {
        this.owner = paramActor;
        if (paramBoolean1) {
          if (this.owner.ownerAttached == null)
            this.owner.ownerAttached = new ArrayList();
          this.owner.ownerAttached.add(this);
          if (paramBoolean2)
            MsgOwner.attach(this.owner, this);
          if (paramBoolean3)
            MsgOwner.change(this, paramActor, localActor);
        }
      } else {
        this.owner = null;
        if (paramActor != null)
          throw new ActorException("new owner is destroyed");
        if (paramBoolean3)
          MsgOwner.change(this, paramActor, localActor);
      }
    }
  }

  public void setOwnerAfter(Actor paramActor1, Actor paramActor2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramActor1 != this.owner) {
      if ((isValid(this.owner)) && (this.owner.ownerAttached != null)) {
        int i = this.owner.ownerAttached.indexOf(this);
        if (i >= 0) {
          this.owner.ownerAttached.remove(i);
          if (paramBoolean2)
            MsgOwner.detach(this.owner, this);
        }
      }
      Actor localActor = this.owner;
      if (isValid(paramActor1)) {
        this.owner = paramActor1;
        if (paramBoolean1) {
          if (this.owner.ownerAttached == null)
            this.owner.ownerAttached = new ArrayList();
          if (paramActor2 == null) {
            this.owner.ownerAttached.add(0, this);
          } else {
            int j = this.owner.ownerAttached.indexOf(paramActor2);
            if (j < 0)
              throw new ActorException("beforeChildren not found");
            this.owner.ownerAttached.add(j + 1, this);
          }
          if (paramBoolean2)
            MsgOwner.attach(this.owner, this);
          if (paramBoolean3)
            MsgOwner.change(this, paramActor1, localActor);
        }
      } else {
        this.owner = null;
        if (paramActor1 != null)
          throw new ActorException("new owner is destroyed");
        if (paramBoolean3)
          MsgOwner.change(this, paramActor1, localActor);
      }
    }
  }

  public void setOwner(Actor paramActor)
  {
    setOwner(paramActor, true, true, false);
  }

  public void changeOwner(Actor paramActor)
  {
    setOwner(paramActor, true, true, true);
  }

  public Hook findHook(Object paramObject)
  {
    return null;
  }

  public float futurePosition(float paramFloat, Point3d paramPoint3d)
  {
    if (this.pos == null) return 0.0F;
    long l = ()(paramFloat * 1000.0F + 0.5F);
    this.pos.getTime(Time.current() + l, paramPoint3d);
    return paramFloat;
  }

  public float futurePosition(float paramFloat, Loc paramLoc)
  {
    if (this.pos == null) return 0.0F;
    long l = ()(paramFloat * 1000.0F + 0.5F);
    this.pos.getTime(Time.current() + l, paramLoc);
    return paramFloat;
  }

  public void alignPosToLand(double paramDouble, boolean paramBoolean)
  {
    if (this.pos == null) return;
    if (Engine.land() == null) return;
    this.pos.getAbs(_tmpPoint);
    _tmpPoint.jdField_z_of_type_Double = (Engine.land().HQ(_tmpPoint.x, _tmpPoint.y) + paramDouble);
    this.pos.setAbs(_tmpPoint);
    if (paramBoolean)
      this.pos.reset();
  }

  protected void interpolateTick()
  {
    if ((this.interp != null) && (this.interp.size() > 0)) {
      try {
        this.interp.tick((this.flags & 0x2000) != 0 ? Time.currentReal() : Time.current());
      } catch (Exception localException) {
        localException.printStackTrace();
      }
      return;
    }
    InterpolateAdapter.adapter().removeListener(this);
  }

  public boolean interpIsSleep()
  {
    if (this.interp != null) return this.interp.isSleep();
    return false;
  }

  public boolean interpSleep()
  {
    if (this.interp != null) return this.interp.sleep();
    return false;
  }

  public boolean interpWakeup()
  {
    if (this.interp != null) return this.interp.wakeup();
    return false;
  }

  public int interpSize()
  {
    if (this.interp != null) return this.interp.size();
    return 0;
  }

  public Interpolate interpGet(Object paramObject)
  {
    if (this.interp != null) return this.interp.get(paramObject);
    return null;
  }

  public void interpPut(Interpolate paramInterpolate, Object paramObject, long paramLong, Message paramMessage)
  {
    if (this.interp == null)
      this.interp = new Interpolators();
    this.interp.put(paramInterpolate, paramObject, paramLong, paramMessage, this);
    if (this.interp.size() == 1)
      InterpolateAdapter.adapter().addListener(this);
  }

  public boolean interpEnd(Object paramObject)
  {
    if (this.interp != null) return this.interp.end(paramObject);
    return false;
  }

  public void interpEndAll()
  {
    if (this.interp != null)
      this.interp.endAll();
  }

  public boolean interpCancel(Object paramObject)
  {
    if (this.interp != null) return this.interp.cancel(paramObject);
    return false;
  }

  public void interpCancelAll()
  {
    if (this.interp != null)
      this.interp.cancelAll();
  }

  public boolean isDrawing()
  {
    return ((this.flags & 0x1) != 0) && ((this.draw != null) || (this.icon != null));
  }
  public boolean isIconDrawing() { return ((this.flags & 0x1) != 0) && (this.icon != null); }

  public void drawing(boolean paramBoolean)
  {
    if (paramBoolean != ((this.flags & 0x1) != 0)) {
      if (paramBoolean) this.flags |= 1; else
        this.flags &= -2;
      if ((this.pos != null) && (this.pos.actor() == this))
        this.pos.drawingChange(paramBoolean); 
    }
  }

  public boolean isVisibilityAsBase() {
    return (this.flags & 0x2) != 0;
  }
  public void visibilityAsBase(boolean paramBoolean) {
    if (((this.flags & 0x2) != 0) == paramBoolean) return;
    if (paramBoolean) this.flags |= 2; else
      this.flags &= -3;
    if ((this.pos != null) && ((this.flags & 0x1) != 0) && (this.pos.actor() == this))
      this.pos.drawingChange(true);
  }

  public boolean isCollide() {
    return (this.flags & 0x10) != 0;
  }
  public boolean isCollideAsPoint() {
    return (this.flags & 0x20) != 0;
  }

  public boolean isCollideAndNotAsPoint() {
    return (this.flags & 0x30) == 16;
  }

  public boolean isCollideOnLand() {
    return (this.flags & 0x40) != 0;
  }

  public void collide(boolean paramBoolean) {
    if (paramBoolean != ((this.flags & 0x10) != 0)) {
      if (paramBoolean) this.flags |= 16; else
        this.flags &= -17;
      if ((this.pos != null) && ((this.flags & 0x20) == 0) && (this.pos.actor() == this))
        this.pos.collideChange(paramBoolean);
    }
  }

  public boolean isDreamListener() {
    return (this.flags & 0x200) != 0;
  }
  public boolean isDreamFire() {
    return (this.flags & 0x100) != 0;
  }

  public void dreamFire(boolean paramBoolean) {
    if (paramBoolean != ((this.flags & 0x100) != 0)) {
      if (paramBoolean) this.flags |= 256; else
        this.flags &= -257;
      if ((this.pos != null) && (this.pos.actor() == this))
        this.pos.dreamFireChange(paramBoolean);
    }
  }

  public float collisionR() {
    return 10.0F;
  }

  public Acoustics acoustics()
  {
    Actor localActor = this;
    while (localActor != null) {
      if (localActor.acoustics != null)
        break;
      if (localActor.pos != null)
        localActor = localActor.pos.base();
      else {
        localActor = null;
      }
    }
    if (localActor != null) return localActor.acoustics;
    return Engine.worldAcoustics();
  }

  public Actor actorAcoustics()
  {
    Actor localActor = this;
    while (localActor != null) {
      if (localActor.acoustics != null)
        break;
      if (localActor.pos != null)
        localActor = localActor.pos.base();
      else {
        localActor = null;
      }
    }
    return localActor;
  }

  public Acoustics findParentAcoustics()
  {
    Actor localActor = this;
    while (localActor != null) {
      if (localActor.acoustics != null) return localActor.acoustics;
      if (localActor.pos == null) break;
      localActor = localActor.pos.base();
    }
    return null;
  }

  public void setAcoustics(Acoustics paramAcoustics)
  {
    if (paramAcoustics == null) paramAcoustics = Engine.worldAcoustics();
    this.acoustics = paramAcoustics;
    if ((this.draw != null) && (this.draw.sounds != null)) {
      SoundFX localSoundFX = this.draw.sounds.get();
      while (localSoundFX != null) {
        localSoundFX.setAcoustics(this.acoustics);
        localSoundFX = localSoundFX.next();
      }
    }
    if (this.ownerAttached != null)
      for (int i = 0; i < this.ownerAttached.size(); i++) {
        Actor localActor = (Actor)this.ownerAttached.get(i);
        localActor.setAcoustics(paramAcoustics);
      }
  }

  public SoundFX newSound(String paramString, boolean paramBoolean)
  {
    if ((this.draw == null) || (paramString == null)) return null;
    if (paramString.equals("")) {
      System.out.println("Empty sound in " + toString());
      return null;
    }
    SoundFX localSoundFX = new SoundFX(paramString);
    if (localSoundFX.isInitialized()) {
      localSoundFX.setAcoustics(this.acoustics);
      localSoundFX.insert(this.draw.sounds(), false);
      if (paramBoolean) localSoundFX.play(); 
    }
    else {
      localSoundFX = null;
    }return localSoundFX;
  }

  public SoundFX newSound(SoundPreset paramSoundPreset, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((this.draw == null) || (paramSoundPreset == null)) return null;
    SoundFX localSoundFX = new SoundFX(paramSoundPreset);
    if (localSoundFX.isInitialized()) {
      localSoundFX.setAcoustics(this.acoustics);
      localSoundFX.insert(this.draw.sounds(), paramBoolean2);
      if (paramBoolean1) localSoundFX.play(); 
    }
    else {
      localSoundFX = null;
    }return localSoundFX;
  }

  public void playSound(String paramString, boolean paramBoolean)
  {
    if ((this.draw == null) || (paramString == null)) return;
    if (paramString.equals("")) {
      System.out.println("Empty sound in " + toString());
      return;
    }
    SoundFX localSoundFX = new SoundFX(paramString);
    if ((paramBoolean) && (localSoundFX.isInitialized())) {
      localSoundFX.setAcoustics(this.acoustics);
      localSoundFX.insert(this.draw.sounds(), true);
      localSoundFX.play();
    } else {
      localSoundFX.play(this.pos.getAbsPoint());
    }
  }

  public void playSound(SoundPreset paramSoundPreset, boolean paramBoolean)
  {
    if ((this.draw == null) || (paramSoundPreset == null)) return;
    SoundFX localSoundFX = new SoundFX(paramSoundPreset);
    if ((paramBoolean) && (localSoundFX.isInitialized())) {
      localSoundFX.setAcoustics(this.acoustics);
      localSoundFX.insert(this.draw.sounds(), true);
      localSoundFX.play();
    }
    localSoundFX.play(this.pos.getAbsPoint());
  }

  public void stopSounds()
  {
    if ((this.draw != null) && (this.draw.sounds != null)) {
      SoundFX localSoundFX = this.draw.sounds.get();
      while (localSoundFX != null) {
        localSoundFX.stop();
        localSoundFX = localSoundFX.next();
      }
    }
  }

  public void breakSounds()
  {
    if ((this.draw != null) && (this.draw.sounds != null)) {
      SoundFX localSoundFX = this.draw.sounds.get();
      while (localSoundFX != null) {
        localSoundFX.cancel();
        localSoundFX = localSoundFX.next();
      }
    }
  }

  public SoundFX getRootFX()
  {
    return null;
  }

  public boolean hasInternalSounds()
  {
    return false;
  }

  public boolean isDestroyed()
  {
    return (this.flags & 0x8000) != 0;
  }

  public void destroy()
  {
    if (isDestroyed()) return;
    breakSounds();
    if (this.pos != null) {
      if (this.pos.actor() == this) {
        this.pos.reset();
        this.pos.destroy();
      } else if (isValid(this.pos.base())) {
        this.pos.base().pos.removeChildren(this);
      }
    }
    if ((this instanceof MsgDreamGlobalListener)) {
      Engine.dreamEnv().removeGlobalListener(this);
    }
    if (this.ownerAttached != null) {
      while (this.ownerAttached.size() > 0) {
        Actor localActor = (Actor)this.ownerAttached.get(0);
        localActor.changeOwner(null);
      }
    }
    setOwner(null);

    ObjState.destroy(this.net);

    if (this.interp != null) {
      this.interp.destroy();
      this.interp = null;
      InterpolateAdapter.adapter().removeListener(this);
    }

    ObjState.destroy(this.draw);

    if (this.name != null)
      Engine.cur.name2Actor.remove(this.name);
    if ((this instanceof Prey)) {
      int i = Engine.targets().indexOf(this);
      if (i >= 0)
        Engine.targets().remove(i);
    }
    this.flags |= 32768;
    super.destroy();

    _countActors -= 1;
    if (Engine.cur != null)
      Engine.cur.actorDestroyed(this);
  }

  public void postDestroy()
  {
    Engine.postDestroyActor(this);
  }

  public void postDestroy(long paramLong)
  {
    MsgDestroy.Post(paramLong, this);
  }

  public double distance(Actor paramActor)
  {
    return this.pos.getAbsPoint().distance(paramActor.pos.getAbsPoint());
  }

  public int target_O_Clock(Actor paramActor)
  {
    _V1.sub(paramActor.pos.getAbsPoint(), this.pos.getAbsPoint());
    this.pos.getAbsOrient().transformInv(_V1);
    float f1 = 57.324841F * (float)Math.atan2(_V1.y, -_V1.x);
    int i = (int)f1;
    i = ((i + 180) % 360 + 15) / 30;
    if (i == 0) i = 12;
    float f2 = (float)_V1.length() + 0.1F;
    float f3 = (float)(paramActor.pos.getAbsPoint().jdField_z_of_type_Double - this.pos.getAbsPoint().jdField_z_of_type_Double) / f2;
    if (f3 > 0.4F) i += 12;
    else if (f3 < -0.4F) i += 24;
    return i;
  }

  public double getSpeed(Vector3d paramVector3d)
  {
    return this.pos.speed(paramVector3d);
  }
  public void setSpeed(Vector3d paramVector3d) {
  }

  public static void setSpawnFromMission(boolean paramBoolean) {
    bSpawnFromMission = paramBoolean;
  }

  public static int countAll()
  {
    return _countActors;
  }

  public boolean isGameActor() {
    return this._hash > 0;
  }

  protected Actor()
  {
    createActorHashCode();

    if (bSpawnFromMission) {
      this.flags |= 4096;
    }

    _countActors += 1;
    if ((this instanceof MsgDreamGlobalListener))
      Engine.dreamEnv().addGlobalListener(this);
    if ((this instanceof Prey))
      Engine.targets().add(this);
  }

  protected void createActorHashCode() {
    makeActorGameHashCode();
  }

  protected void makeActorRealHashCode() {
    this._hash = (-Math.abs(super.hashCode()));
  }

  protected void makeActorGameHashCode() {
    this._hash = (_hashNext++);
  }

  protected static void resetActorGameHashCodes()
  {
    _hashNext = 1;
  }
  public static int _getCurHashNextCode() {
    return _hashNext;
  }

  public int hashCode()
  {
    return this._hash;
  }

  public long getCRC(long paramLong)
  {
    if (this.pos == null) return paramLong;
    this.pos.getAbs(_tmpPoint, _tmpOrient);
    _tmpPoint.get(_d3); long l = Finger.incLong(paramLong, _d3);
    _tmpOrient.get(_f3); l = Finger.incLong(l, _f3);
    return l;
  }

  public int getCRC(int paramInt)
  {
    if (this.pos == null) return paramInt;
    this.pos.getAbs(_tmpPoint, _tmpOrient);
    _tmpPoint.get(_d3); int i = Finger.incInt(paramInt, _d3);
    _tmpOrient.get(_f3); i = Finger.incInt(i, _f3);
    return i;
  }
}