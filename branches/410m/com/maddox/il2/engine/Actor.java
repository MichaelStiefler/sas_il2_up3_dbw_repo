// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Actor.java

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

// Referenced classes of package com.maddox.il2.engine:
//            ActorException, Interpolators, MsgDreamGlobalListener, Orient, 
//            Loc, Engine, MsgOwner, ActorNet, 
//            ActorPos, Landscape, InterpolateAdapter, ActorDraw, 
//            DreamEnv, Mat, Hook, Interpolate

public abstract class Actor extends com.maddox.rts.ObjState
{

    public static boolean isValid(com.maddox.il2.engine.Actor actor)
    {
        return actor != null && !actor.isDestroyed();
    }

    public static boolean isAlive(com.maddox.il2.engine.Actor actor)
    {
        return actor != null && actor.isAlive();
    }

    public boolean isAlive()
    {
        return (flags & 0x8004) == 0;
    }

    public void setDiedFlag(boolean flag)
    {
        if(flag)
        {
            if((flags & 4) == 0)
            {
                flags |= 4;
                if(this instanceof com.maddox.il2.ai.ground.Prey)
                {
                    int i = com.maddox.il2.engine.Engine.targets().indexOf(this);
                    if(i >= 0)
                        com.maddox.il2.engine.Engine.targets().remove(i);
                }
                if(com.maddox.il2.engine.Actor.isValid(owner))
                    com.maddox.il2.engine.MsgOwner.died(owner, this);
            }
        } else
        if((flags & 4) != 0)
        {
            flags &= -5;
            if(this instanceof com.maddox.il2.ai.ground.Prey)
                com.maddox.il2.engine.Engine.targets().add(this);
        }
    }

    public boolean getDiedFlag()
    {
        return (flags & 4) != 0;
    }

    public boolean isTaskComplete()
    {
        return (flags & 8) != 0;
    }

    public void setTaskCompleteFlag(boolean flag)
    {
        if(flag)
        {
            if((flags & 8) == 0)
            {
                flags |= 8;
                if(com.maddox.il2.engine.Actor.isValid(owner))
                    com.maddox.il2.engine.MsgOwner.taskComplete(owner, this);
            }
        } else
        {
            flags &= -9;
        }
    }

    public int getArmy()
    {
        return flags >>> 16;
    }

    public void setArmy(int i)
    {
        flags = i << 16 | flags & 0xffff;
    }

    public boolean isRealTime()
    {
        return (flags & 0x2000) != 0 || com.maddox.rts.Time.isRealOnly();
    }

    public boolean isRealTimeFlag()
    {
        return (flags & 0x2000) != 0;
    }

    public boolean isNet()
    {
        return net != null;
    }

    public boolean isNetMaster()
    {
        return net != null && net.isMaster();
    }

    public boolean isNetMirror()
    {
        return net != null && net.isMirror();
    }

    public boolean isSpawnFromMission()
    {
        return (flags & 0x1000) != 0;
    }

    public void missionStarting()
    {
    }

    public void setName(java.lang.String s)
    {
        if(name != null)
            com.maddox.il2.engine.Engine.cur.name2Actor.remove(name);
        name = s;
        if(s != null)
            com.maddox.il2.engine.Engine.cur.name2Actor.put(name, this);
    }

    public java.lang.String name()
    {
        return name != null ? name : "NONAME";
    }

    public boolean isNamed()
    {
        return name != null;
    }

    public static com.maddox.il2.engine.Actor getByName(java.lang.String s)
    {
        return (com.maddox.il2.engine.Actor)com.maddox.il2.engine.Engine.cur.name2Actor.get(s);
    }

    public com.maddox.rts.NetMsgSpawn netReplicate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        return new NetMsgSpawn(net);
    }

    public void netFirstUpdate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
    }

    public com.maddox.il2.engine.Actor getOwner()
    {
        return owner;
    }

    public boolean isContainOwner(java.lang.Object obj)
    {
        if(obj == null)
            return false;
        if(owner == null)
            return false;
        if(owner.equals(obj))
            return true;
        else
            return owner.isContainOwner(obj);
    }

    public java.lang.Object[] getOwnerAttached()
    {
        if(ownerAttached != null)
            return ownerAttached.toArray();
        else
            return emptyArrayOwners;
    }

    public java.lang.Object[] getOwnerAttached(java.lang.Object aobj[])
    {
        if(ownerAttached != null)
            return ownerAttached.toArray(aobj);
        else
            return emptyArrayOwners;
    }

    public int getOwnerAttachedCount()
    {
        if(ownerAttached != null)
            return ownerAttached.size();
        else
            return 0;
    }

    public int getOwnerAttachedIndex(java.lang.Object obj)
    {
        if(ownerAttached != null)
            return ownerAttached.indexOf(obj);
        else
            return -1;
    }

    public java.lang.Object getOwnerAttached(int i)
    {
        return ownerAttached.get(i);
    }

    public void setOwner(com.maddox.il2.engine.Actor actor, boolean flag, boolean flag1, boolean flag2)
    {
        if(actor != owner)
        {
            if(com.maddox.il2.engine.Actor.isValid(owner) && owner.ownerAttached != null)
            {
                int i = owner.ownerAttached.indexOf(this);
                if(i >= 0)
                {
                    owner.ownerAttached.remove(i);
                    if(flag1)
                        com.maddox.il2.engine.MsgOwner.detach(owner, this);
                }
            }
            com.maddox.il2.engine.Actor actor1 = owner;
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                owner = actor;
                if(flag)
                {
                    if(owner.ownerAttached == null)
                        owner.ownerAttached = new ArrayList();
                    owner.ownerAttached.add(this);
                    if(flag1)
                        com.maddox.il2.engine.MsgOwner.attach(owner, this);
                    if(flag2)
                        com.maddox.il2.engine.MsgOwner.change(this, actor, actor1);
                }
            } else
            {
                owner = null;
                if(actor != null)
                    throw new ActorException("new owner is destroyed");
                if(flag2)
                    com.maddox.il2.engine.MsgOwner.change(this, actor, actor1);
            }
        }
    }

    public void setOwnerAfter(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1, boolean flag, boolean flag1, boolean flag2)
    {
        if(actor != owner)
        {
            if(com.maddox.il2.engine.Actor.isValid(owner) && owner.ownerAttached != null)
            {
                int i = owner.ownerAttached.indexOf(this);
                if(i >= 0)
                {
                    owner.ownerAttached.remove(i);
                    if(flag1)
                        com.maddox.il2.engine.MsgOwner.detach(owner, this);
                }
            }
            com.maddox.il2.engine.Actor actor2 = owner;
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                owner = actor;
                if(flag)
                {
                    if(owner.ownerAttached == null)
                        owner.ownerAttached = new ArrayList();
                    if(actor1 == null)
                    {
                        owner.ownerAttached.add(0, this);
                    } else
                    {
                        int j = owner.ownerAttached.indexOf(actor1);
                        if(j < 0)
                            throw new ActorException("beforeChildren not found");
                        owner.ownerAttached.add(j + 1, this);
                    }
                    if(flag1)
                        com.maddox.il2.engine.MsgOwner.attach(owner, this);
                    if(flag2)
                        com.maddox.il2.engine.MsgOwner.change(this, actor, actor2);
                }
            } else
            {
                owner = null;
                if(actor != null)
                    throw new ActorException("new owner is destroyed");
                if(flag2)
                    com.maddox.il2.engine.MsgOwner.change(this, actor, actor2);
            }
        }
    }

    public void setOwner(com.maddox.il2.engine.Actor actor)
    {
        setOwner(actor, true, true, false);
    }

    public void changeOwner(com.maddox.il2.engine.Actor actor)
    {
        setOwner(actor, true, true, true);
    }

    public com.maddox.il2.engine.Hook findHook(java.lang.Object obj)
    {
        return null;
    }

    public float futurePosition(float f, com.maddox.JGP.Point3d point3d)
    {
        if(pos == null)
        {
            return 0.0F;
        } else
        {
            long l = (long)(f * 1000F + 0.5F);
            pos.getTime(com.maddox.rts.Time.current() + l, point3d);
            return f;
        }
    }

    public float futurePosition(float f, com.maddox.il2.engine.Loc loc)
    {
        if(pos == null)
        {
            return 0.0F;
        } else
        {
            long l = (long)(f * 1000F + 0.5F);
            pos.getTime(com.maddox.rts.Time.current() + l, loc);
            return f;
        }
    }

    public void alignPosToLand(double d, boolean flag)
    {
        if(pos == null)
            return;
        if(com.maddox.il2.engine.Engine.land() == null)
            return;
        pos.getAbs(_tmpPoint);
        _tmpPoint.z = com.maddox.il2.engine.Engine.land().HQ(_tmpPoint.x, _tmpPoint.y) + d;
        pos.setAbs(_tmpPoint);
        if(flag)
            pos.reset();
    }

    protected void interpolateTick()
    {
        if(interp != null && interp.size() > 0)
        {
            try
            {
                interp.tick((flags & 0x2000) == 0 ? com.maddox.rts.Time.current() : com.maddox.rts.Time.currentReal());
            }
            catch(java.lang.Exception exception)
            {
                exception.printStackTrace();
            }
            return;
        } else
        {
            com.maddox.il2.engine.InterpolateAdapter.adapter().removeListener(this);
            return;
        }
    }

    public boolean interpIsSleep()
    {
        if(interp != null)
            return interp.isSleep();
        else
            return false;
    }

    public boolean interpSleep()
    {
        if(interp != null)
            return interp.sleep();
        else
            return false;
    }

    public boolean interpWakeup()
    {
        if(interp != null)
            return interp.wakeup();
        else
            return false;
    }

    public int interpSize()
    {
        if(interp != null)
            return interp.size();
        else
            return 0;
    }

    public com.maddox.il2.engine.Interpolate interpGet(java.lang.Object obj)
    {
        if(interp != null)
            return interp.get(obj);
        else
            return null;
    }

    public void interpPut(com.maddox.il2.engine.Interpolate interpolate, java.lang.Object obj, long l, com.maddox.rts.Message message)
    {
        if(interp == null)
            interp = new Interpolators();
        interp.put(interpolate, obj, l, message, this);
        if(interp.size() == 1)
            com.maddox.il2.engine.InterpolateAdapter.adapter().addListener(this);
    }

    public boolean interpEnd(java.lang.Object obj)
    {
        if(interp != null)
            return interp.end(obj);
        else
            return false;
    }

    public void interpEndAll()
    {
        if(interp != null)
            interp.endAll();
    }

    public boolean interpCancel(java.lang.Object obj)
    {
        if(interp != null)
            return interp.cancel(obj);
        else
            return false;
    }

    public void interpCancelAll()
    {
        if(interp != null)
            interp.cancelAll();
    }

    public boolean isDrawing()
    {
        return (flags & 1) != 0 && (draw != null || icon != null);
    }

    public boolean isIconDrawing()
    {
        return (flags & 1) != 0 && icon != null;
    }

    public void drawing(boolean flag)
    {
        if(flag != ((flags & 1) != 0))
        {
            if(flag)
                flags |= 1;
            else
                flags &= -2;
            if(pos != null && pos.actor() == this)
                pos.drawingChange(flag);
        }
    }

    public boolean isVisibilityAsBase()
    {
        return (flags & 2) != 0;
    }

    public void visibilityAsBase(boolean flag)
    {
        if(((flags & 2) != 0) == flag)
            return;
        if(flag)
            flags |= 2;
        else
            flags &= -3;
        if(pos != null && (flags & 1) != 0 && pos.actor() == this)
            pos.drawingChange(true);
    }

    public boolean isCollide()
    {
        return (flags & 0x10) != 0;
    }

    public boolean isCollideAsPoint()
    {
        return (flags & 0x20) != 0;
    }

    public boolean isCollideAndNotAsPoint()
    {
        return (flags & 0x30) == 16;
    }

    public boolean isCollideOnLand()
    {
        return (flags & 0x40) != 0;
    }

    public void collide(boolean flag)
    {
        if(flag != ((flags & 0x10) != 0))
        {
            if(flag)
                flags |= 0x10;
            else
                flags &= 0xffffffef;
            if(pos != null && (flags & 0x20) == 0 && pos.actor() == this)
                pos.collideChange(flag);
        }
    }

    public boolean isDreamListener()
    {
        return (flags & 0x200) != 0;
    }

    public boolean isDreamFire()
    {
        return (flags & 0x100) != 0;
    }

    public void dreamFire(boolean flag)
    {
        if(flag != ((flags & 0x100) != 0))
        {
            if(flag)
                flags |= 0x100;
            else
                flags &= 0xfffffeff;
            if(pos != null && pos.actor() == this)
                pos.dreamFireChange(flag);
        }
    }

    public float collisionR()
    {
        return 10F;
    }

    public com.maddox.sound.Acoustics acoustics()
    {
        com.maddox.il2.engine.Actor actor;
        for(actor = this; actor != null && actor.acoustics == null;)
            if(actor.pos != null)
                actor = actor.pos.base();
            else
                actor = null;

        if(actor != null)
            return actor.acoustics;
        else
            return com.maddox.il2.engine.Engine.worldAcoustics();
    }

    public com.maddox.il2.engine.Actor actorAcoustics()
    {
        com.maddox.il2.engine.Actor actor;
        for(actor = this; actor != null && actor.acoustics == null;)
            if(actor.pos != null)
                actor = actor.pos.base();
            else
                actor = null;

        return actor;
    }

    public com.maddox.sound.Acoustics findParentAcoustics()
    {
        com.maddox.il2.engine.Actor actor = this;
        do
        {
            if(actor == null)
                break;
            if(actor.acoustics != null)
                return actor.acoustics;
            if(actor.pos == null)
                break;
            actor = actor.pos.base();
        } while(true);
        return null;
    }

    public void setAcoustics(com.maddox.sound.Acoustics acoustics1)
    {
        if(acoustics1 == null)
            acoustics1 = com.maddox.il2.engine.Engine.worldAcoustics();
        acoustics = acoustics1;
        if(draw != null && draw.sounds != null)
        {
            for(com.maddox.sound.SoundFX soundfx = draw.sounds.get(); soundfx != null; soundfx = soundfx.next())
                soundfx.setAcoustics(acoustics);

        }
        if(ownerAttached != null)
        {
            for(int i = 0; i < ownerAttached.size(); i++)
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)ownerAttached.get(i);
                actor.setAcoustics(acoustics1);
            }

        }
    }

    public com.maddox.sound.SoundFX newSound(java.lang.String s, boolean flag)
    {
        if(draw == null || s == null)
            return null;
        if(s.equals(""))
        {
            java.lang.System.out.println("Empty sound in " + toString());
            return null;
        }
        com.maddox.sound.SoundFX soundfx = new SoundFX(s);
        if(soundfx.isInitialized())
        {
            soundfx.setAcoustics(acoustics);
            soundfx.insert(draw.sounds(), false);
            if(flag)
                soundfx.play();
        } else
        {
            soundfx = null;
        }
        return soundfx;
    }

    public com.maddox.sound.SoundFX newSound(com.maddox.sound.SoundPreset soundpreset, boolean flag, boolean flag1)
    {
        if(draw == null || soundpreset == null)
            return null;
        com.maddox.sound.SoundFX soundfx = new SoundFX(soundpreset);
        if(soundfx.isInitialized())
        {
            soundfx.setAcoustics(acoustics);
            soundfx.insert(draw.sounds(), flag1);
            if(flag)
                soundfx.play();
        } else
        {
            soundfx = null;
        }
        return soundfx;
    }

    public void playSound(java.lang.String s, boolean flag)
    {
        if(draw == null || s == null)
            return;
        if(s.equals(""))
        {
            java.lang.System.out.println("Empty sound in " + toString());
            return;
        }
        com.maddox.sound.SoundFX soundfx = new SoundFX(s);
        if(flag && soundfx.isInitialized())
        {
            soundfx.setAcoustics(acoustics);
            soundfx.insert(draw.sounds(), true);
            soundfx.play();
        } else
        {
            soundfx.play(pos.getAbsPoint());
        }
    }

    public void playSound(com.maddox.sound.SoundPreset soundpreset, boolean flag)
    {
        if(draw == null || soundpreset == null)
            return;
        com.maddox.sound.SoundFX soundfx = new SoundFX(soundpreset);
        if(flag && soundfx.isInitialized())
        {
            soundfx.setAcoustics(acoustics);
            soundfx.insert(draw.sounds(), true);
            soundfx.play();
        }
        soundfx.play(pos.getAbsPoint());
    }

    public void stopSounds()
    {
        if(draw != null && draw.sounds != null)
        {
            for(com.maddox.sound.SoundFX soundfx = draw.sounds.get(); soundfx != null; soundfx = soundfx.next())
                soundfx.stop();

        }
    }

    public void breakSounds()
    {
        if(draw != null && draw.sounds != null)
        {
            for(com.maddox.sound.SoundFX soundfx = draw.sounds.get(); soundfx != null; soundfx = soundfx.next())
                soundfx.cancel();

        }
    }

    public com.maddox.sound.SoundFX getRootFX()
    {
        return null;
    }

    public boolean hasInternalSounds()
    {
        return false;
    }

    public boolean isDestroyed()
    {
        return (flags & 0x8000) != 0;
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        breakSounds();
        if(pos != null)
            if(pos.actor() == this)
            {
                pos.reset();
                pos.destroy();
            } else
            if(com.maddox.il2.engine.Actor.isValid(pos.base()))
                pos.base().pos.removeChildren(this);
        if(this instanceof com.maddox.il2.engine.MsgDreamGlobalListener)
            com.maddox.il2.engine.Engine.dreamEnv().removeGlobalListener(this);
        if(ownerAttached != null)
        {
            com.maddox.il2.engine.Actor actor;
            for(; ownerAttached.size() > 0; actor.changeOwner(null))
                actor = (com.maddox.il2.engine.Actor)ownerAttached.get(0);

        }
        setOwner(null);
        com.maddox.il2.engine.Actor.destroy(((com.maddox.rts.Destroy) (net)));
        if(interp != null)
        {
            interp.destroy();
            interp = null;
            com.maddox.il2.engine.InterpolateAdapter.adapter().removeListener(this);
        }
        com.maddox.il2.engine.Actor.destroy(((com.maddox.rts.Destroy) (draw)));
        if(name != null)
            com.maddox.il2.engine.Engine.cur.name2Actor.remove(name);
        if(this instanceof com.maddox.il2.ai.ground.Prey)
        {
            int i = com.maddox.il2.engine.Engine.targets().indexOf(this);
            if(i >= 0)
                com.maddox.il2.engine.Engine.targets().remove(i);
        }
        flags |= 0x8000;
        super.destroy();
        _countActors--;
        if(com.maddox.il2.engine.Engine.cur != null)
            com.maddox.il2.engine.Engine.cur.actorDestroyed(this);
    }

    public void postDestroy()
    {
        com.maddox.il2.engine.Engine.postDestroyActor(this);
    }

    public void postDestroy(long l)
    {
        com.maddox.rts.MsgDestroy.Post(l, this);
    }

    public double distance(com.maddox.il2.engine.Actor actor)
    {
        return pos.getAbsPoint().distance(actor.pos.getAbsPoint());
    }

    public int target_O_Clock(com.maddox.il2.engine.Actor actor)
    {
        _V1.sub(actor.pos.getAbsPoint(), pos.getAbsPoint());
        pos.getAbsOrient().transformInv(_V1);
        float f = 57.32484F * (float)java.lang.Math.atan2(_V1.y, -_V1.x);
        int i = (int)f;
        i = ((i + 180) % 360 + 15) / 30;
        if(i == 0)
            i = 12;
        float f1 = (float)_V1.length() + 0.1F;
        float f2 = (float)(actor.pos.getAbsPoint().z - pos.getAbsPoint().z) / f1;
        if(f2 > 0.4F)
            i += 12;
        else
        if(f2 < -0.4F)
            i += 24;
        return i;
    }

    public double getSpeed(com.maddox.JGP.Vector3d vector3d)
    {
        return pos.speed(vector3d);
    }

    public void setSpeed(com.maddox.JGP.Vector3d vector3d)
    {
    }

    public static void setSpawnFromMission(boolean flag)
    {
        bSpawnFromMission = flag;
    }

    public static int countAll()
    {
        return _countActors;
    }

    public boolean isGameActor()
    {
        return _hash > 0;
    }

    protected Actor()
    {
        flags = 0;
        acoustics = null;
        createActorHashCode();
        if(bSpawnFromMission)
            flags |= 0x1000;
        _countActors++;
        if(this instanceof com.maddox.il2.engine.MsgDreamGlobalListener)
            com.maddox.il2.engine.Engine.dreamEnv().addGlobalListener(this);
        if(this instanceof com.maddox.il2.ai.ground.Prey)
            com.maddox.il2.engine.Engine.targets().add(this);
    }

    protected void createActorHashCode()
    {
        makeActorGameHashCode();
    }

    protected void makeActorRealHashCode()
    {
        _hash = -java.lang.Math.abs(super.hashCode());
    }

    protected void makeActorGameHashCode()
    {
        _hash = _hashNext++;
    }

    protected static void resetActorGameHashCodes()
    {
        _hashNext = 1;
    }

    public static int _getCurHashNextCode()
    {
        return _hashNext;
    }

    public int hashCode()
    {
        return _hash;
    }

    public long getCRC(long l)
    {
        if(pos == null)
        {
            return l;
        } else
        {
            pos.getAbs(_tmpPoint, _tmpOrient);
            _tmpPoint.get(_d3);
            long l1 = com.maddox.rts.Finger.incLong(l, _d3);
            _tmpOrient.get(_f3);
            l1 = com.maddox.rts.Finger.incLong(l1, _f3);
            return l1;
        }
    }

    public int getCRC(int i)
    {
        if(pos == null)
        {
            return i;
        } else
        {
            pos.getAbs(_tmpPoint, _tmpOrient);
            _tmpPoint.get(_d3);
            int j = com.maddox.rts.Finger.incInt(i, _d3);
            _tmpOrient.get(_f3);
            j = com.maddox.rts.Finger.incInt(j, _f3);
            return j;
        }
    }

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
    protected int flags;
    private java.lang.String name;
    public com.maddox.il2.engine.ActorNet net;
    private com.maddox.il2.engine.Actor owner;
    protected java.util.List ownerAttached;
    private static java.lang.Object emptyArrayOwners[] = new java.lang.Object[0];
    public com.maddox.il2.engine.ActorPos pos;
    public com.maddox.il2.engine.Interpolators interp;
    public com.maddox.il2.engine.Mat icon;
    public com.maddox.il2.engine.ActorDraw draw;
    public com.maddox.sound.Acoustics acoustics;
    private static boolean bSpawnFromMission = false;
    private static com.maddox.JGP.Vector3d _V1 = new Vector3d();
    public static com.maddox.JGP.Point3d _tmpPoint = new Point3d();
    public static com.maddox.il2.engine.Orient _tmpOrient = new Orient();
    public static com.maddox.il2.engine.Loc _tmpLoc = new Loc();
    public static double _d3[] = new double[3];
    public static float _f3[] = new float[3];
    private int _hash;
    private static int _hashNext = 1;
    private static int _countActors = 0;

}
