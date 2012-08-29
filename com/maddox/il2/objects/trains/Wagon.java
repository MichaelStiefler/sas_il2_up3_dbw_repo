// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Wagon.java

package com.maddox.il2.objects.trains;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.Prey;
import com.maddox.il2.ai.ground.TgtTrain;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorException;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookNamed;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.effects.Explosions;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.trains:
//            Train, LocomotiveVerm

public abstract class Wagon extends com.maddox.il2.engine.ActorHMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.engine.MsgCollisionListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener, com.maddox.il2.ai.ground.Prey, com.maddox.il2.ai.ground.TgtTrain
{
    public class MyMsgAction extends com.maddox.rts.MsgAction
    {

        public void doAction(java.lang.Object obj)
        {
        }

        java.lang.Object obj2;

        public MyMsgAction(double d, java.lang.Object obj, java.lang.Object obj1)
        {
            super(d, obj);
            obj2 = obj1;
        }
    }

    class Mirror extends com.maddox.il2.engine.ActorNet
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
            {
                byte byte2 = netmsginput.readByte();
                switch(byte2)
                {
                case 83: // 'S'
                case 115: // 's'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted);
                    }
                    float f = netmsginput.readFloat();
                    if(f <= 0.0F)
                        f = -1F;
                    com.maddox.il2.objects.trains.Train.TrainState trainstate = new Train.TrainState();
                    trainstate._headSeg = netmsginput.readInt();
                    trainstate._headAlong = netmsginput.readDouble();
                    trainstate._curSpeed = netmsginput.readFloat();
                    trainstate._milestoneDist = netmsginput.readDouble();
                    trainstate._requiredSpeed = netmsginput.readFloat();
                    trainstate._maxAcceler = netmsginput.readFloat();
                    LifeChanged(false, f, null, true);
                    if(getOwner() != null)
                    {
                        boolean flag = byte2 == 115;
                        ((com.maddox.il2.objects.trains.Train)getOwner()).setStateDataMirror(trainstate, flag);
                    }
                    forgetAllAiming();
                    return true;

                case 73: // 'I'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted(netmsginput, 0);
                        post(netmsgguaranted1);
                    }
                    float f1 = netmsginput.readFloat();
                    if(f1 <= 0.0F)
                        f1 = -1F;
                    LifeChanged(false, f1, null, true);
                    forgetAllAiming();
                    return true;

                case 68: // 'D'
                    if(isMirrored())
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted2 = new NetMsgGuaranted(netmsginput, 1);
                        post(netmsgguaranted2);
                    }
                    if(life > 0.0F)
                    {
                        com.maddox.rts.NetObj netobj2 = netmsginput.readNetObj();
                        com.maddox.il2.engine.Actor actor2 = netobj2 != null ? ((com.maddox.il2.engine.ActorNet)netobj2).actor() : null;
                        LifeChanged(false, 0.0F, actor2, false);
                    }
                    return true;
                }
                return false;
            }
            switch(netmsginput.readByte())
            {
            default:
                break;

            case 84: // 'T'
                if(isMirrored())
                {
                    out.unLockAndSet(netmsginput, 1);
                    out.setIncludeTime(false);
                    postReal(com.maddox.rts.Message.currentRealTime(), out);
                }
                byte byte0 = netmsginput.readByte();
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                int i = netmsginput.readUnsignedByte();
                Track_Mirror(byte0, actor, i);
                break;

            case 70: // 'F'
                if(isMirrored())
                {
                    out.unLockAndSet(netmsginput, 1);
                    out.setIncludeTime(true);
                    postReal(com.maddox.rts.Message.currentRealTime(), out);
                }
                byte byte1 = netmsginput.readByte();
                com.maddox.rts.NetObj netobj1 = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor1 = netobj1 != null ? ((com.maddox.il2.engine.ActorNet)netobj1).actor() : null;
                float f2 = netmsginput.readFloat();
                float f3 = 0.001F * (float)(com.maddox.rts.Message.currentGameTime() - com.maddox.rts.Time.current()) + f2;
                int j = netmsginput.readUnsignedByte();
                Fire_Mirror(byte1, actor1, j, f3);
                break;

            case 68: // 'D'
                out.unLockAndSet(netmsginput, 1);
                out.setIncludeTime(false);
                postRealTo(com.maddox.rts.Message.currentRealTime(), masterChannel(), out);
                break;
            }
            return true;
        }

        com.maddox.rts.NetMsgFiltered out;

        public Mirror(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(actor, netchannel, i);
            out = new NetMsgFiltered();
        }
    }

    class Master extends com.maddox.il2.engine.ActorNet
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
                return true;
            if(netmsginput.readByte() != 68)
                return false;
            if(life <= 0.0F)
            {
                return true;
            } else
            {
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor = netobj != null ? ((com.maddox.il2.engine.ActorNet)netobj).actor() : null;
                LifeChanged(false, 0.0F, actor, false);
                return true;
            }
        }

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
        }
    }

    public class Pair
    {

        com.maddox.il2.objects.trains.Wagon victim;
        com.maddox.il2.engine.Actor initiator;

        Pair(com.maddox.il2.objects.trains.Wagon wagon1, com.maddox.il2.engine.Actor actor)
        {
            victim = wagon1;
            initiator = actor;
        }
    }


    protected void forgetAllAiming()
    {
    }

    public int HitbyMask()
    {
        return -1;
    }

    public int chooseBulletType(com.maddox.il2.engine.BulletProperties abulletproperties[])
    {
        if(IsDamaged())
            return -1;
        if(abulletproperties.length == 1)
            return 0;
        if(abulletproperties.length <= 0)
            return -1;
        if(abulletproperties[0].power <= 0.0F)
            return 0;
        if(abulletproperties[1].power <= 0.0F)
            return 1;
        if(abulletproperties[0].cumulativePower > 0.0F)
            return 0;
        if(abulletproperties[1].cumulativePower > 0.0F)
            return 1;
        if(abulletproperties[0].powerType == 1)
            return 0;
        if(abulletproperties[1].powerType == 1)
            return 1;
        return abulletproperties[0].powerType != 0 ? 0 : 1;
    }

    public int chooseShotpoint(com.maddox.il2.engine.BulletProperties bulletproperties)
    {
        return !IsDamaged() ? 0 : -1;
    }

    public boolean getShotpointOffset(int i, com.maddox.JGP.Point3d point3d)
    {
        if(IsDamaged())
            return false;
        if(i != 0)
            return false;
        if(point3d != null)
            point3d.set(0.0D, 0.0D, 0.0D);
        return true;
    }

    public final boolean isStaticPos()
    {
        return false;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    private final boolean RndB(float f)
    {
        return com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < f;
    }

    final boolean IsDamaged()
    {
        return life <= 0.0F;
    }

    final boolean IsDead()
    {
        return life < 0.0F;
    }

    final boolean IsDeadOrDying()
    {
        return life <= 0.0F;
    }

    final float getLength()
    {
        return hook1 - hook2;
    }

    private void changePoseAsCrushed(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        tmp_rnd.setSeed(211 * crushSeed);
        orient.get(tmp_atk);
        tmp_atk[0] += tmp_rnd.nextFloat(-13F, 13F);
        tmp_atk[1] += tmp_rnd.nextFloat(-2F, 2.0F);
        tmp_atk[2] += tmp_rnd.nextFloat(-8F, 8F);
        orient.set(tmp_atk[0], tmp_atk[1], tmp_atk[2]);
        point3d.x += tmp_rnd.nextDouble(-0.80000000000000004D, 0.80000000000000004D);
        point3d.y += tmp_rnd.nextDouble(-0.90000000000000002D, 0.90000000000000002D);
        point3d.z += tmp_rnd.nextDouble(-0.25D, 0.0D);
    }

    void place(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, boolean flag, boolean flag1)
    {
        if(flag1)
            return;
        com.maddox.il2.engine.Orient orient = new Orient();
        com.maddox.JGP.Point3d point3d2 = new Point3d();
        point3d2.interpolate(point3d, point3d1, hook1 / (hook1 - hook2));
        point3d2.z += heightAboveLandSurface;
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        vector3d.sub(point3d, point3d1);
        orient.setAT0(vector3d);
        if(life < 0.0F)
            changePoseAsCrushed(point3d2, orient);
        pos.setAbs(point3d2, orient);
        if(flag)
            pos.reset();
    }

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if(actor instanceof com.maddox.il2.objects.trains.Wagon)
        {
            com.maddox.il2.engine.Actor actor1 = getOwner();
            com.maddox.il2.engine.Actor actor2 = actor.getOwner();
            if(actor1 == actor2)
            {
                aflag[0] = false;
                return;
            }
            if(((com.maddox.il2.objects.trains.Train)actor1).stoppedForever() && ((com.maddox.il2.objects.trains.Train)actor2).stoppedForever())
            {
                aflag[0] = false;
                return;
            } else
            {
                return;
            }
        }
        if(((com.maddox.il2.objects.trains.Train)getOwner()).stoppedForever() && (actor instanceof com.maddox.il2.engine.ActorMesh) && ((com.maddox.il2.engine.ActorMesh)actor).isStaticPos())
        {
            aflag[0] = false;
            return;
        }
        if(actor instanceof com.maddox.il2.objects.bridges.BridgeSegment)
        {
            aflag[0] = false;
            return;
        } else
        {
            return;
        }
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(life < 0.0F)
            return;
        if(isNetMirror())
            return;
        if(actor instanceof com.maddox.il2.objects.trains.Wagon)
            LifeChanged(false, 0.0F, actor, false);
    }

    protected float killProbab(float f)
    {
        float f1 = life;
        float f2 = 3.9E+009F * (f1 * f1 * f1);
        float f3 = f / f2;
        float f4;
        if(f3 <= 1.0F)
        {
            f3 = f3 * 2.0F - 1.0F;
            if(f3 <= 0.0F)
                return 0.0F;
            f4 = f3 * 0.04F;
        } else
        {
            if(f3 >= 10F)
                return 1.0F;
            f3 = (f3 - 1.0F) / 9F;
            f4 = 0.04F + 0.96F * f3;
        }
        return f4;
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        if(shot.chunkName.startsWith("Armor") && shot.power <= 20450F)
            return;
        shot.bodyMaterial = bodyMaterial;
        if(IsDamaged())
            return;
        if(shot.power <= 0.0F)
            return;
        if(isNetMirror() && shot.isMirage())
            return;
        if(shot.powerType == 1)
            if(RndB(0.125F))
            {
                return;
            } else
            {
                LifeChanged(isNetMirror(), 0.0F, shot.initiator, false);
                return;
            }
        if(!RndB(killProbab(shot.power)))
        {
            return;
        } else
        {
            LifeChanged(isNetMirror(), 0.0F, shot.initiator, false);
            return;
        }
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(IsDamaged())
            return;
        if(isNetMirror() && explosion.isMirage())
            return;
        com.maddox.il2.ai.Explosion _tmp = explosion;
        if(com.maddox.il2.ai.Explosion.killable(this, explosion.receivedTNT_1meter(this), ignoreTNT, killTNT, 0.0F))
            LifeChanged(isNetMirror(), 0.0F, explosion.initiator, false);
    }

    protected void hiddenexplode()
    {
    }

    protected void explode(com.maddox.il2.engine.Actor actor)
    {
        new com.maddox.rts.MsgAction(0.0D, this) {

            public void doAction(java.lang.Object obj)
            {
                com.maddox.il2.objects.trains.Wagon wagon = (com.maddox.il2.objects.trains.Wagon)obj;
                com.maddox.il2.engine.Eff3DActor.New(wagon, new HookNamed(wagon, "Damage"), null, 1.0F, "Effects/Smokes/WagonFC.eff", 60F);
            }

        }
;
        new com.maddox.rts.MsgAction(2.5D) {

            public void doAction()
            {
                com.maddox.JGP.Point3d point3d = new Point3d();
                pos.getAbs(point3d);
                com.maddox.il2.objects.effects.Explosions.ExplodeVagonArmor(point3d, point3d, 2.0F);
            }

        }
;
        new com.maddox.rts.MsgAction(4.5D, new Pair(this, actor)) {

            public void doAction(java.lang.Object obj)
            {
                com.maddox.il2.engine.Actor actor1 = getOwner();
                if(actor1 != null)
                    ((com.maddox.il2.objects.trains.Train)actor1).wagonDied(((com.maddox.il2.objects.trains.Pair)obj).victim, ((com.maddox.il2.objects.trains.Pair)obj).initiator);
                life = -1F;
                ActivateMesh();
            }

        }
;
    }

    private final void LifeChanged(boolean flag, float f, com.maddox.il2.engine.Actor actor, boolean flag1)
    {
        if(flag1)
        {
            if(f > 0.0F)
                life = f;
            else
                life = -1F;
            if(life < 0.0F)
            {
                crushSeed = (byte)com.maddox.il2.ai.World.Rnd().nextInt(1, 127);
                com.maddox.il2.ai.World.onActorDied(this, actor);
                hiddenexplode();
            }
            ActivateMesh();
            return;
        }
        if(f <= 0.0F)
        {
            if(life <= 0.0F)
                return;
            if(flag)
            {
                life = 0.001F;
                send_DeathRequest(actor);
                return;
            }
            life = 0.0F;
        } else
        {
            life = f;
            return;
        }
        crushSeed = (byte)com.maddox.il2.ai.World.Rnd().nextInt(1, 127);
        explode(actor);
        com.maddox.il2.ai.World.onActorDied(this, actor);
        if(!isNetMirror())
            send_DeathCommand(actor);
    }

    public void absoluteDeath(com.maddox.il2.engine.Actor actor)
    {
        if(!getDiedFlag())
            com.maddox.il2.ai.World.onActorDied(this, actor);
        destroy();
    }

    public void destroy()
    {
        super.destroy();
    }

    protected void ActivateMesh()
    {
        boolean flag = IsDead();
        setMesh(flag ? meshDead : meshLive);
        if(!flag)
        {
            heightAboveLandSurface = 0.0F;
            int i = hierMesh().hookFind("Ground_Level");
            if(i != -1)
            {
                com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
                hierMesh().hookMatrix(i, matrix4d);
                heightAboveLandSurface = (float)(-matrix4d.m23);
            }
            i = hierMesh().hookFind("Select1");
            if(i != -1)
            {
                com.maddox.JGP.Matrix4d matrix4d1 = new Matrix4d();
                hierMesh().hookMatrix(i, matrix4d1);
                hook1 = (float)matrix4d1.m03;
            } else
            {
                throw new ActorException("Wagon: hook Select1 not found in " + meshLive);
            }
            i = hierMesh().hookFind("Select2");
            if(i != -1)
            {
                com.maddox.JGP.Matrix4d matrix4d2 = new Matrix4d();
                hierMesh().hookMatrix(i, matrix4d2);
                hook2 = (float)matrix4d2.m03;
            } else
            {
                throw new ActorException("Wagon: hook Select2 not found in " + meshLive);
            }
            if(hook1 <= hook2)
                throw new ActorException("Wagon: hooks SelectX placed incorrectly in " + meshLive);
        }
    }

    public Wagon(com.maddox.il2.objects.trains.Train train, java.lang.String s, java.lang.String s1)
    {
        super(s);
        hook1 = 1.0F;
        hook2 = -1F;
        heightAboveLandSurface = 0.0F;
        life = 0.017F;
        ignoreTNT = 0.35F;
        killTNT = 1.9F;
        bodyMaterial = 2;
        outCommand = new NetMsgFiltered();
        collide(true);
        drawing(true);
        setOwner(train);
        setArmy(train.getArmy());
        meshLive = new String(s);
        meshDead = new String(s1);
        life = 1E-005F;
        ActivateMesh();
        int i = com.maddox.il2.game.Mission.cur().getUnitNetIdRemote(this);
        com.maddox.rts.NetChannel netchannel = com.maddox.il2.game.Mission.cur().getNetMasterChannel();
        if(netchannel == null)
            net = new Master(this);
        else
        if(i != 0)
            net = new Mirror(this, netchannel, i);
    }

    private void send_DeathCommand(com.maddox.il2.engine.Actor actor)
    {
        if(!isNetMaster())
            return;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        try
        {
            netmsgguaranted.writeByte(68);
            netmsgguaranted.writeNetObj(actor.net);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    protected void send_FireCommand(int i, com.maddox.il2.engine.Actor actor, int j, float f)
    {
        if(!isNetMaster())
            return;
        if(!net.isMirrored())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(actor) || !actor.isNet())
            return;
        j &= 0xff;
        if(f < 0.0F)
            try
            {
                outCommand.unLockAndClear();
                outCommand.writeByte(84);
                outCommand.writeByte(i);
                outCommand.writeNetObj(actor.net);
                outCommand.writeByte(j);
                outCommand.setIncludeTime(false);
                net.post(com.maddox.rts.Time.current(), outCommand);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        else
            try
            {
                outCommand.unLockAndClear();
                outCommand.writeByte(70);
                outCommand.writeByte(i);
                outCommand.writeFloat(f);
                outCommand.writeNetObj(actor.net);
                outCommand.writeByte(j);
                outCommand.setIncludeTime(true);
                net.post(com.maddox.rts.Time.current(), outCommand);
            }
            catch(java.lang.Exception exception1)
            {
                java.lang.System.out.println(exception1.getMessage());
                exception1.printStackTrace();
            }
    }

    private void send_DeathRequest(com.maddox.il2.engine.Actor actor)
    {
        if(!isNetMirror())
            return;
        if(net.masterChannel() instanceof com.maddox.rts.NetChannelInStream)
            return;
        try
        {
            com.maddox.rts.NetMsgFiltered netmsgfiltered = new NetMsgFiltered();
            netmsgfiltered.writeByte(68);
            netmsgfiltered.writeNetObj(actor.net);
            netmsgfiltered.setIncludeTime(false);
            net.postTo(com.maddox.rts.Time.current(), net.masterChannel(), netmsgfiltered);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    protected void Track_Mirror(int i, com.maddox.il2.engine.Actor actor, int j)
    {
        if(IsDamaged())
            return;
        else
            return;
    }

    protected void Fire_Mirror(int i, com.maddox.il2.engine.Actor actor, int j, float f)
    {
        if(IsDamaged())
            return;
        else
            return;
    }

    public void netFirstUpdate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        if(this instanceof com.maddox.il2.objects.trains.LocomotiveVerm)
        {
            com.maddox.il2.objects.trains.Train train = (com.maddox.il2.objects.trains.Train)getOwner();
            netmsgguaranted.writeByte(train.isAnybodyDead() ? 115 : 83);
            netmsgguaranted.writeFloat(life);
            com.maddox.il2.objects.trains.Train.TrainState trainstate = new Train.TrainState();
            train.getStateData(trainstate);
            netmsgguaranted.writeInt(trainstate._headSeg);
            netmsgguaranted.writeDouble(trainstate._headAlong);
            netmsgguaranted.writeFloat(trainstate._curSpeed);
            netmsgguaranted.writeDouble(trainstate._milestoneDist);
            netmsgguaranted.writeFloat(trainstate._requiredSpeed);
            netmsgguaranted.writeFloat(trainstate._maxAcceler);
        } else
        {
            netmsgguaranted.writeByte(73);
            netmsgguaranted.writeFloat(life);
        }
        net.postTo(netchannel, netmsgguaranted);
    }

    private static final float PROBAB_DEATH_WHEN_SHOT = 0.04F;
    private float hook1;
    private float hook2;
    private float heightAboveLandSurface;
    private java.lang.String meshLive;
    private java.lang.String meshDead;
    protected byte crushSeed;
    protected float life;
    protected float ignoreTNT;
    protected float killTNT;
    protected int bodyMaterial;
    private static float tmp_atk[] = new float[3];
    private static com.maddox.il2.ai.RangeRandom tmp_rnd = new RangeRandom(0L);
    private com.maddox.rts.NetMsgFiltered outCommand;


}
