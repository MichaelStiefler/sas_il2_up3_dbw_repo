// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Paratrooper.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.MsgExplosionListener;
import com.maddox.il2.ai.MsgShotListener;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.UnitInterface;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorMeshDraw;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.FObj;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MsgCollisionListener;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            Aircraft, Chute, NetGunner, NetAircraft

public class Paratrooper extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener, com.maddox.il2.engine.MsgCollisionListener, com.maddox.il2.ai.MsgExplosionListener, com.maddox.il2.ai.MsgShotListener
{
    public static class SPAWN
        implements com.maddox.rts.NetSpawn
    {

        public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            try
            {
                com.maddox.il2.engine.Loc loc = new Loc(netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat());
                com.maddox.JGP.Vector3d vector3d = new Vector3d(netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat());
                com.maddox.il2.engine.Actor actor = null;
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                if(netobj != null)
                    actor = (com.maddox.il2.engine.Actor)netobj.superObj();
                com.maddox.il2.objects.air.Paratrooper paratrooper = new Paratrooper(actor, netmsginput.readUnsignedByte(), netmsginput.readUnsignedByte(), loc, vector3d, netmsginput, i);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }

        public SPAWN()
        {
        }
    }

    class Mirror extends com.maddox.il2.objects.air.ParaNet
    {

        public Mirror(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetMsgInput netmsginput, int i)
        {
            super(actor, netmsginput, i);
            try
            {
                turn_para_on_height = netmsginput.readFloat();
                nRunCycles = netmsginput.readByte();
                driver = (com.maddox.il2.net.NetUser)netmsginput.readNetObj();
                testDriver();
            }
            catch(java.lang.Exception exception) { }
        }
    }

    class Master extends com.maddox.il2.objects.air.ParaNet
    {

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
            actor.pos.getAbs(com.maddox.il2.objects.air.Paratrooper.p);
            float f = (float)com.maddox.il2.objects.air.Paratrooper.p.z - com.maddox.il2.engine.Engine.land().HQ((float)com.maddox.il2.objects.air.Paratrooper.p.x, (float)com.maddox.il2.objects.air.Paratrooper.p.y);
            if(f <= 500F)
                turn_para_on_height = 500F;
            else
            if(f >= 4000F)
                turn_para_on_height = 2000F;
            else
                turn_para_on_height = 500F + 1500F * ((f - 500F) / 3500F);
            turn_para_on_height*= = com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 1.2F);
            nRunCycles = com.maddox.il2.ai.World.Rnd().nextInt(6, 12);
            java.lang.Class class1 = actor.getOwner().getClass();
            java.lang.Object obj = com.maddox.rts.Property.value(class1, "cockpitClass");
            if(obj != null)
            {
                java.lang.Class aclass[] = null;
                if(obj instanceof java.lang.Class)
                {
                    aclass = new java.lang.Class[1];
                    aclass[0] = (java.lang.Class)obj;
                } else
                {
                    aclass = (java.lang.Class[])(java.lang.Class[])obj;
                }
                for(int i = 0; i < aclass.length; i++)
                {
                    int j = com.maddox.rts.Property.intValue(aclass[i], "astatePilotIndx", 0);
                    if(j != idxOfPilotPlace)
                        continue;
                    com.maddox.il2.engine.Actor actor1 = ((com.maddox.il2.objects.air.Aircraft)actor.getOwner()).netCockpitGetDriver(i);
                    if(actor1 == null)
                        continue;
                    if(com.maddox.il2.game.Mission.isSingle())
                    {
                        driver = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
                        break;
                    }
                    if(actor1 instanceof com.maddox.il2.objects.air.NetGunner)
                        driver = ((com.maddox.il2.objects.air.NetGunner)actor1).getUser();
                    else
                        driver = ((com.maddox.il2.objects.air.NetAircraft)actor1).netUser();
                    break;
                }

            }
            testDriver();
        }
    }

    class ParaNet extends com.maddox.il2.engine.ActorNet
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(!netmsginput.isGuaranted())
                return false;
            byte byte0 = netmsginput.readByte();
            byte byte1 = -1;
            switch(byte0)
            {
            case 68: // 'D'
                byte1 = 1;
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor = null;
                if(netobj != null)
                    actor = (com.maddox.il2.engine.Actor)netobj.superObj();
                Die(actor, false);
                break;

            case 83: // 'S'
                byte1 = 1;
                com.maddox.rts.NetObj netobj1 = netmsginput.readNetObj();
                com.maddox.il2.engine.Actor actor1 = null;
                if(netobj1 != null)
                    actor1 = (com.maddox.il2.engine.Actor)netobj1.superObj();
                java.lang.Object aobj[] = getOwnerAttached();
                for(int i = 0; i < aobj.length; i++)
                {
                    com.maddox.il2.objects.air.Chute chute = (com.maddox.il2.objects.air.Chute)aobj[i];
                    if(com.maddox.il2.engine.Actor.isValid(chute))
                        chute.tangleChute(actor1);
                }

                break;
            }
            if(byte1 >= 0)
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(netmsginput, byte1);
                postExclude(netmsginput.channel(), netmsgguaranted);
                return true;
            } else
            {
                return false;
            }
        }

        public ParaNet(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
        }

        public ParaNet(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetMsgInput netmsginput, int i)
        {
            super(actor, netmsginput.channel(), i);
        }
    }

    class Move extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            if(st == 9)
            {
                if(dying == 0)
                    checkCaptured();
                postDestroy();
                return false;
            }
            if((st == 6 || st == 7 || st == 8) && com.maddox.rts.Time.current() >= disappearTime)
            {
                postDestroy();
                return false;
            }
            if(dying != 0)
                switch(st)
                {
                case 4: // '\004'
                    st = 5;
                    animStartTime = com.maddox.rts.Time.current();
                    break;

                case 6: // '\006'
                    st = 7;
                    idxOfDeadPose = com.maddox.il2.ai.World.Rnd().nextInt(0, 3);
                    break;
                }
            long l = com.maddox.rts.Time.tickNext() - animStartTime;
            switch(st)
            {
            default:
                break;

            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 3: // '\003'
                pos.getAbs(com.maddox.il2.objects.air.Paratrooper.p);
                com.maddox.il2.engine.Engine.land();
                float f = com.maddox.il2.engine.Landscape.HQ((float)com.maddox.il2.objects.air.Paratrooper.p.x, (float)com.maddox.il2.objects.air.Paratrooper.p.y);
                if(st == 0)
                    if(l >= 2500L)
                    {
                        pos.setAbs(faceOrient);
                        if(dying == 0 && (float)com.maddox.il2.objects.air.Paratrooper.p.z - f <= turn_para_on_height && speed.z < -5D)
                        {
                            st = 1;
                            animStartTime = com.maddox.rts.Time.current();
                            l = com.maddox.rts.Time.tickNext() - animStartTime;
                            new Chute(actor);
                        }
                    } else
                    {
                        pos.getAbs(com.maddox.il2.objects.air.Paratrooper.o);
                        float f1 = (float)l / 2500F;
                        if(f1 <= 0.0F)
                            f1 = 0.0F;
                        if(f1 >= 1.0F)
                            f1 = 1.0F;
                        com.maddox.il2.objects.air.Paratrooper.o.interpolate(startOrient, faceOrient, f1);
                        pos.setAbs(com.maddox.il2.objects.air.Paratrooper.o);
                    }
                if(st == 1 && l >= 500L)
                {
                    st = 2;
                    animStartTime = com.maddox.rts.Time.current();
                    l = com.maddox.rts.Time.tickNext() - animStartTime;
                }
                com.maddox.il2.objects.air.Paratrooper.p.scaleAdd(com.maddox.rts.Time.tickLenFs(), speed, com.maddox.il2.objects.air.Paratrooper.p);
                speed.z -= com.maddox.rts.Time.tickLenFs() * com.maddox.il2.ai.World.g();
                if(st == 2)
                {
                    if(speed.x != 0.0D)
                        speed.x -= (java.lang.Math.abs(speed.x) / speed.x) * 0.0099999997764825821D * (speed.x * speed.x) * (double)com.maddox.rts.Time.tickLenFs();
                    if(speed.y != 0.0D)
                        speed.y -= (java.lang.Math.abs(speed.y) / speed.y) * 0.0099999997764825821D * (speed.y * speed.y) * (double)com.maddox.rts.Time.tickLenFs();
                } else
                {
                    if(speed.x != 0.0D)
                        speed.x -= (java.lang.Math.abs(speed.x) / speed.x) * 0.0010000000474974513D * (speed.x * speed.x) * (double)com.maddox.rts.Time.tickLenFs();
                    if(speed.y != 0.0D)
                        speed.y -= (java.lang.Math.abs(speed.y) / speed.y) * 0.0010000000474974513D * (speed.y * speed.y) * (double)com.maddox.rts.Time.tickLenFs();
                }
                double d = st != 2 ? 50F : 5F;
                if(-speed.z > d)
                {
                    double d1 = -speed.z - d;
                    if(d1 > (double)(com.maddox.rts.Time.tickLenFs() * 20F))
                        d1 = com.maddox.rts.Time.tickLenFs() * 20F;
                    speed.z += d1;
                }
                if(com.maddox.il2.objects.air.Paratrooper.p.z <= (double)f)
                {
                    boolean flag = speed.length() > 10D;
                    com.maddox.JGP.Vector3d vector3d = new Vector3d();
                    vector3d.set(1.0D, 0.0D, 0.0D);
                    faceOrient.transform(vector3d);
                    speed.set(vector3d);
                    speed.z = 0.0D;
                    speed.normalize();
                    speed.scale(6.5454545021057129D);
                    com.maddox.il2.objects.air.Paratrooper.p.z = f;
                    if(flag || dying != 0)
                    {
                        st = 7;
                        animStartTime = com.maddox.rts.Time.current();
                        disappearTime = com.maddox.rts.Time.tickNext() + (long)(1000 * com.maddox.il2.ai.World.Rnd().nextInt(25, 35));
                        idxOfDeadPose = com.maddox.il2.ai.World.Rnd().nextInt(0, 3);
                        new com.maddox.rts.MsgAction(0.0D, actor) {

                            public void doAction(java.lang.Object obj)
                            {
                                com.maddox.il2.objects.air.Paratrooper paratrooper = (com.maddox.il2.objects.air.Paratrooper)obj;
                                paratrooper.Die(com.maddox.il2.engine.Engine.actorLand());
                            }

                        }
;
                    } else
                    {
                        st = 4;
                        animStartTime = com.maddox.rts.Time.current();
                        if(name().equals("_paraplayer_") && com.maddox.il2.game.Mission.isNet() && com.maddox.il2.ai.World.getPlayerFM() != null && com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()) && com.maddox.il2.ai.World.getPlayerAircraft().isNetPlayer())
                        {
                            com.maddox.il2.fm.FlightModel flightmodel = com.maddox.il2.ai.World.getPlayerFM();
                            if(flightmodel.isWasAirborne() && flightmodel.isStationedOnGround() && !flightmodel.isNearAirdrome())
                                com.maddox.il2.net.Chat.sendLogRnd(2, "gore_walkaway", com.maddox.il2.ai.World.getPlayerAircraft(), null);
                        }
                    }
                    pos.setAbs(faceOrient);
                    java.lang.Object aobj[] = getOwnerAttached();
                    for(int i = 0; i < aobj.length; i++)
                    {
                        com.maddox.il2.objects.air.Chute chute = (com.maddox.il2.objects.air.Chute)aobj[i];
                        if(com.maddox.il2.engine.Actor.isValid(chute))
                            chute.landing();
                    }

                }
                pos.setAbs(com.maddox.il2.objects.air.Paratrooper.p);
                break;

            case 4: // '\004'
                pos.getAbs(com.maddox.il2.objects.air.Paratrooper.p);
                com.maddox.il2.objects.air.Paratrooper.p.scaleAdd(com.maddox.rts.Time.tickLenFs(), speed, com.maddox.il2.objects.air.Paratrooper.p);
                com.maddox.il2.objects.air.Paratrooper.p.z = com.maddox.il2.engine.Engine.land().HQ(com.maddox.il2.objects.air.Paratrooper.p.x, com.maddox.il2.objects.air.Paratrooper.p.y);
                pos.setAbs(com.maddox.il2.objects.air.Paratrooper.p);
                if(com.maddox.il2.ai.World.land().isWater(com.maddox.il2.objects.air.Paratrooper.p.x, com.maddox.il2.objects.air.Paratrooper.p.y))
                {
                    if(swimMeshCode < 0)
                    {
                        st = 5;
                        animStartTime = com.maddox.rts.Time.current();
                    } else
                    {
                        setMesh(com.maddox.il2.objects.air.Paratrooper.GetMeshName_Water(swimMeshCode));
                        pos.getAbs(com.maddox.il2.objects.air.Paratrooper.p);
                        com.maddox.il2.objects.air.Paratrooper.p.z = com.maddox.il2.engine.Engine.land().HQ(com.maddox.il2.objects.air.Paratrooper.p.x, com.maddox.il2.objects.air.Paratrooper.p.y);
                        pos.setAbs(com.maddox.il2.objects.air.Paratrooper.p);
                        st = 8;
                        animStartTime = com.maddox.rts.Time.current();
                        disappearTime = com.maddox.rts.Time.tickNext() + (long)(1000 * com.maddox.il2.ai.World.Rnd().nextInt(25, 35));
                        checkCaptured();
                    }
                    break;
                }
                if(l / 733L >= (long)nRunCycles)
                {
                    st = 5;
                    animStartTime = com.maddox.rts.Time.current();
                }
                break;

            case 5: // '\005'
                pos.getAbs(com.maddox.il2.objects.air.Paratrooper.p);
                com.maddox.il2.objects.air.Paratrooper.p.scaleAdd(com.maddox.rts.Time.tickLenFs(), speed, com.maddox.il2.objects.air.Paratrooper.p);
                com.maddox.il2.objects.air.Paratrooper.p.z = com.maddox.il2.engine.Engine.land().HQ(com.maddox.il2.objects.air.Paratrooper.p.x, com.maddox.il2.objects.air.Paratrooper.p.y);
                if(com.maddox.il2.ai.World.land().isWater(com.maddox.il2.objects.air.Paratrooper.p.x, com.maddox.il2.objects.air.Paratrooper.p.y))
                    if(swimMeshCode < 0)
                    {
                        com.maddox.il2.objects.air.Paratrooper.p.z -= 0.5D;
                    } else
                    {
                        setMesh(com.maddox.il2.objects.air.Paratrooper.GetMeshName_Water(swimMeshCode));
                        pos.getAbs(com.maddox.il2.objects.air.Paratrooper.p);
                        com.maddox.il2.objects.air.Paratrooper.p.z = com.maddox.il2.engine.Engine.land().HQ(com.maddox.il2.objects.air.Paratrooper.p.x, com.maddox.il2.objects.air.Paratrooper.p.y);
                        pos.setAbs(com.maddox.il2.objects.air.Paratrooper.p);
                        st = 8;
                        animStartTime = com.maddox.rts.Time.current();
                        disappearTime = com.maddox.rts.Time.tickNext() + (long)(1000 * com.maddox.il2.ai.World.Rnd().nextInt(25, 35));
                        checkCaptured();
                        break;
                    }
                pos.setAbs(com.maddox.il2.objects.air.Paratrooper.p);
                if(l >= 1066L)
                {
                    st = 6;
                    animStartTime = com.maddox.rts.Time.current();
                    disappearTime = com.maddox.rts.Time.tickNext() + (long)(1000 * com.maddox.il2.ai.World.Rnd().nextInt(25, 35));
                    checkCaptured();
                }
                break;

            case 6: // '\006'
            case 7: // '\007'
                pos.getAbs(com.maddox.il2.objects.air.Paratrooper.p);
                com.maddox.il2.objects.air.Paratrooper.p.z = com.maddox.il2.engine.Engine.land().HQ(com.maddox.il2.objects.air.Paratrooper.p.x, com.maddox.il2.objects.air.Paratrooper.p.y);
                if(com.maddox.il2.ai.World.land().isWater(com.maddox.il2.objects.air.Paratrooper.p.x, com.maddox.il2.objects.air.Paratrooper.p.y))
                    com.maddox.il2.objects.air.Paratrooper.p.z -= 3D;
                pos.setAbs(com.maddox.il2.objects.air.Paratrooper.p);
                break;
            }
            setAnimFrame(com.maddox.rts.Time.tickNext());
            return true;
        }

        Move()
        {
        }
    }

    private class SoldDraw extends com.maddox.il2.engine.ActorMeshDraw
    {

        public int preRender(com.maddox.il2.engine.Actor actor)
        {
            setAnimFrame(com.maddox.rts.Time.current());
            return super.preRender(actor);
        }

        private SoldDraw()
        {
        }

    }


    public boolean isChuteSafelyOpened()
    {
        return st == 2 || st == 6 || st == 8 || st == 9;
    }

    public static void resetGame()
    {
        _counter = 0;
        preload1 = preload2 = preload3 = null;
    }

    public static void PRELOAD()
    {
        preload1 = new Mesh(com.maddox.il2.objects.air.Paratrooper.GetMeshName(1));
        preload2 = new Mesh(com.maddox.il2.objects.air.Paratrooper.GetMeshName(2));
        preload3 = new Mesh(com.maddox.il2.objects.air.Chute.GetMeshName());
        preload4 = new Mesh(com.maddox.il2.objects.air.Paratrooper.GetMeshName_Water(0));
        preload5 = new Mesh(com.maddox.il2.objects.air.Paratrooper.GetMeshName_Water(1));
        preload6 = new Mesh(com.maddox.il2.objects.air.Paratrooper.GetMeshName_Water(2));
    }

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if((actor instanceof com.maddox.il2.objects.air.Aircraft) && actor.isNet() && actor.isNetMirror())
            aflag[0] = false;
        if((actor == getOwner() || getOwner() == null) && com.maddox.rts.Time.current() - animStartTime < 2800L)
            aflag[0] = false;
        if(dying != 0 && (actor == null || !(actor instanceof com.maddox.il2.objects.ships.ShipGeneric) && !(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric)))
            aflag[0] = false;
    }

    public void msgCollision(com.maddox.il2.engine.Actor actor, java.lang.String s, java.lang.String s1)
    {
        if(st == 9)
            return;
        if(dying != 0)
        {
            if(actor != null && ((actor instanceof com.maddox.il2.objects.ships.ShipGeneric) || (actor instanceof com.maddox.il2.objects.ships.BigshipGeneric)))
                st = 9;
            return;
        }
        if(actor != null && ((actor instanceof com.maddox.il2.objects.ships.ShipGeneric) || (actor instanceof com.maddox.il2.objects.ships.BigshipGeneric)))
        {
            boolean flag = java.lang.Math.abs(speed.z) > 10D;
            if(flag)
                Die(actor);
            st = 9;
            return;
        }
        com.maddox.JGP.Point3d point3d = p;
        pos.getAbs(p);
        com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        vector3d.set(point3d.x - point3d1.x, point3d.y - point3d1.y, 0.0D);
        if(vector3d.length() < 0.001D)
        {
            float f = com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 359.99F);
            vector3d.set(com.maddox.JGP.Geom.sinDeg(f), com.maddox.JGP.Geom.cosDeg(f), 0.0D);
        }
        vector3d.normalize();
        float f1 = 0.2F;
        vector3d.add(com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1), com.maddox.il2.ai.World.Rnd().nextFloat(-f1, f1));
        vector3d.normalize();
        float f2 = 13.09091F * com.maddox.rts.Time.tickLenFs();
        vector3d.scale(f2);
        speed.z *= 0.5D;
        point3d.add(vector3d);
        pos.setAbs(point3d);
        if(st == 4)
        {
            st = 5;
            animStartTime = com.maddox.rts.Time.current();
        }
        if(st == 6 && dying == 0 && (actor instanceof com.maddox.il2.ai.ground.UnitInterface) && actor.getSpeed(null) > 0.5D)
            Die(actor);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        if(st == 9)
            return;
        shot.bodyMaterial = 3;
        if(dying != 0)
            return;
        if(shot.power <= 0.0F)
            return;
        if(shot.powerType == 1)
        {
            Die(shot.initiator);
            return;
        }
        if(shot.v.length() < 20D)
        {
            return;
        } else
        {
            Die(shot.initiator);
            return;
        }
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        if(st == 9)
            return;
        if(dying != 0)
            return;
        float f = 0.005F;
        float f1 = 0.1F;
        com.maddox.il2.ai.Explosion _tmp = explosion;
        if(com.maddox.il2.ai.Explosion.killable(this, explosion.receivedTNT_1meter(this), f, f1, 0.0F))
            Die(explosion.initiator);
    }

    public void checkCaptured()
    {
        bCheksCaptured = true;
        if(logAircraftName != null && (driver == null && isNetMaster() || driver != null && driver.isMaster()))
            com.maddox.il2.ai.EventLog.onParaLanded(this, logAircraftName, idxOfPilotPlace);
        if(com.maddox.il2.ai.Front.isCaptured(this))
        {
            if(name().equals("_paraplayer_"))
            {
                com.maddox.il2.ai.World.setPlayerCaptured();
                if(com.maddox.il2.engine.Config.isUSE_RENDER())
                    com.maddox.il2.game.HUD.log("PlayerCAPT");
                if(com.maddox.il2.game.Mission.isNet())
                    com.maddox.il2.net.Chat.sendLog(1, "gore_captured", (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host(), null);
            }
            if(logAircraftName != null && (driver == null && isNetMaster() || driver != null && driver.isMaster()))
                com.maddox.il2.ai.EventLog.onCaptured(this, logAircraftName, idxOfPilotPlace);
        }
    }

    public boolean isChecksCaptured()
    {
        if(dying != 0)
            return true;
        else
            return bCheksCaptured;
    }

    private void Die(com.maddox.il2.engine.Actor actor)
    {
        Die(actor, true);
    }

    private void Die(com.maddox.il2.engine.Actor actor, boolean flag)
    {
        if(dying != 0)
            return;
        com.maddox.il2.ai.World.onActorDied(this, actor);
        if(actor != this)
        {
            if(name().equals("_paraplayer_"))
            {
                com.maddox.il2.ai.World.setPlayerDead();
                if(com.maddox.il2.engine.Config.isUSE_RENDER())
                    com.maddox.il2.game.HUD.log("Player_Killed");
                if(com.maddox.il2.game.Mission.isNet())
                {
                    if((actor instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)actor).isNetPlayer() && com.maddox.il2.engine.Actor.isAlive(com.maddox.il2.ai.World.getPlayerAircraft()))
                        com.maddox.il2.net.Chat.sendLogRnd(1, "gore_pkonchute", (com.maddox.il2.objects.air.Aircraft)actor, com.maddox.il2.ai.World.getPlayerAircraft());
                    com.maddox.il2.net.Chat.sendLog(0, "gore_killed", (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host(), (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host());
                }
            }
            if(logAircraftName != null && (driver == null && isNetMaster() || driver != null && driver.isMaster()))
                if(com.maddox.il2.engine.Actor.isValid(actor) && actor != com.maddox.il2.engine.Engine.actorLand())
                    com.maddox.il2.ai.EventLog.onParaKilled(this, logAircraftName, idxOfPilotPlace, actor);
                else
                    com.maddox.il2.ai.EventLog.onPilotKilled(this, logAircraftName, idxOfPilotPlace);
        }
        dying = 1;
        if(isNet() && flag)
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(68);
                if(actor != null)
                    netmsgguaranted.writeNetObj(actor.net);
                else
                    netmsgguaranted.writeNetObj(null);
                net.postExclude(null, netmsgguaranted);
            }
            catch(java.lang.Exception exception) { }
    }

    public void destroy()
    {
        java.lang.Object aobj[] = getOwnerAttached();
        for(int i = 0; i < aobj.length; i++)
        {
            com.maddox.il2.objects.air.Chute chute = (com.maddox.il2.objects.air.Chute)aobj[i];
            if(com.maddox.il2.engine.Actor.isValid(chute))
                chute.destroy();
        }

        if(com.maddox.il2.game.Mission.isPlaying() && com.maddox.il2.ai.World.cur() != null && driver != null && (driver.isMaster() || driver.isTrackWriter()))
            com.maddox.il2.ai.World.cur().checkViewOnPlayerDied(this);
        super.destroy();
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    void chuteTangled(com.maddox.il2.engine.Actor actor, boolean flag)
    {
        if(st == 1 || st == 2)
        {
            st = 3;
            animStartTime = com.maddox.rts.Time.current();
            pos.setAbs(faceOrient);
            if(logAircraftName != null && (driver == null && isNetMaster() || driver != null && driver.isMaster()))
                com.maddox.il2.ai.EventLog.onChuteKilled(this, logAircraftName, idxOfPilotPlace, actor);
            if(isNet() && flag)
                try
                {
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                    netmsgguaranted.writeByte(83);
                    if(actor != null)
                        netmsgguaranted.writeNetObj(actor.net);
                    else
                        netmsgguaranted.writeNetObj(null);
                    net.postExclude(null, netmsgguaranted);
                }
                catch(java.lang.Exception exception) { }
        }
    }

    void setAnimFrame(double d)
    {
        int i;
        int j;
        float f;
        switch(st)
        {
        case 0: // '\0'
            i = 0;
            j = 19;
            int k = 633;
            double d1 = d - (double)animStartTime;
            if(d1 <= 0.0D)
                f = 0.0F;
            else
            if(d1 >= (double)k)
                f = 1.0F;
            else
                f = (float)(d1 / (double)k);
            if(f >= 1.0F && dying != 0)
            {
                i = j = 134;
                f = 0.0F;
            }
            break;

        case 1: // '\001'
            i = 19;
            j = 34;
            int l = 500;
            double d2 = d - (double)animStartTime;
            if(d2 <= 0.0D)
            {
                f = 0.0F;
                break;
            }
            if(d2 >= (double)l)
                f = 1.0F;
            else
                f = (float)(d2 / (double)l);
            break;

        case 2: // '\002'
        case 3: // '\003'
            i = 34;
            j = 54;
            int i1 = 666;
            double d3 = d - (double)animStartTime;
            if(d3 <= 0.0D)
                f = 0.0F;
            else
            if(d3 >= (double)i1)
                f = 1.0F;
            else
                f = (float)(d3 / (double)i1);
            if(f >= 1.0F && dying != 0)
            {
                i = j = 133;
                f = 0.0F;
            }
            break;

        case 4: // '\004'
            i = 55;
            j = 77;
            int j1 = 733;
            double d4 = d - (double)animStartTime;
            d4 %= j1;
            if(d4 < 0.0D)
                d4 += j1;
            f = (float)(d4 / (double)j1);
            break;

        case 5: // '\005'
            i = 77;
            j = 109;
            int k1 = 1066;
            double d5 = d - (double)animStartTime;
            if(d5 <= 0.0D)
            {
                f = 0.0F;
                break;
            }
            if(d5 >= (double)k1)
                f = 1.0F;
            else
                f = (float)(d5 / (double)k1);
            break;

        case 6: // '\006'
            i = 109;
            j = 128;
            int l1 = 633;
            double d6 = d - (double)animStartTime;
            if(d6 <= 0.0D)
            {
                f = 0.0F;
                break;
            }
            if(d6 >= (double)l1)
                f = 1.0F;
            else
                f = (float)(d6 / (double)l1);
            break;

        case 8: // '\b'
            return;

        case 9: // '\t'
            return;

        case 7: // '\007'
        default:
            i = j = 129 + idxOfDeadPose;
            f = 0.0F;
            break;
        }
        mesh().setFrameFromRange(i, j, f);
    }

    public int HitbyMask()
    {
        return -25;
    }

    public int chooseBulletType(com.maddox.il2.engine.BulletProperties abulletproperties[])
    {
        if(dying != 0)
            return -1;
        if(abulletproperties.length == 1)
            return 0;
        if(abulletproperties.length <= 0)
            return -1;
        if(abulletproperties[0].power <= 0.0F)
            return 1;
        if(abulletproperties[0].powerType == 1)
            return 0;
        if(abulletproperties[1].powerType == 1)
            return 1;
        if(abulletproperties[0].cumulativePower > 0.0F)
            return 1;
        return abulletproperties[0].powerType != 2 ? 0 : 1;
    }

    public int chooseShotpoint(com.maddox.il2.engine.BulletProperties bulletproperties)
    {
        return dying == 0 ? 0 : -1;
    }

    public boolean getShotpointOffset(int i, com.maddox.JGP.Point3d point3d)
    {
        if(dying != 0)
            return false;
        if(i != 0)
            return false;
        if(point3d != null)
            point3d.set(0.0D, 0.0D, 0.0D);
        return true;
    }

    private static java.lang.String GetMeshName(int i)
    {
        return "3do/humans/Paratroopers/" + (i != 2 ? "Russia" : "Germany") + "/mono.sim";
    }

    private static java.lang.String GetMeshName_Water(int i)
    {
        return "3do/humans/Paratroopers/Water/" + (i != 0 ? i != 1 ? "US_Dinghy" : "US_Jacket" : "JN_Jacket") + "/live.sim";
    }

    public void prepareSkin(java.lang.String s, java.lang.String s1, com.maddox.il2.engine.Mat amat[])
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        java.lang.String s2 = "Pilot";
        int i = mesh().materialFind(s2);
        if(i < 0)
            return;
        com.maddox.il2.engine.Mat mat;
        if(com.maddox.il2.engine.FObj.Exist(s))
        {
            mat = (com.maddox.il2.engine.Mat)com.maddox.il2.engine.FObj.Get(s);
        } else
        {
            com.maddox.il2.engine.Mat mat1 = mesh().material(i);
            mat = (com.maddox.il2.engine.Mat)mat1.Clone();
            mat.Rename(s);
            mat.setLayer(0);
            mat.set('\0', s1);
        }
        if(amat != null)
            amat[0] = mat;
        mesh().materialReplace(s2, mat);
    }

    public Paratrooper(com.maddox.il2.engine.Actor actor, int i, int j, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, com.maddox.rts.NetMsgInput netmsginput, int k)
    {
        super(com.maddox.il2.objects.air.Paratrooper.GetMeshName(i));
        logAircraftName = null;
        swimMeshCode = -1;
        st = 0;
        dying = 0;
        bCheksCaptured = false;
        startOrient = new Orient();
        loc.get(startOrient);
        faceOrient = new Orient();
        faceOrient.set(startOrient);
        faceOrient.setYPR(faceOrient.getYaw(), 0.0F, 0.0F);
        com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
        vector3d1.set(1.0D, 0.0D, 0.0D);
        faceOrient.transform(vector3d1);
        speed = new Vector3d();
        speed.set(vector3d);
        setOwner(actor);
        idxOfPilotPlace = j;
        setArmy(i);
        swimMeshCode = -1;
        if(com.maddox.il2.engine.Actor.isValid(actor) && (actor instanceof com.maddox.il2.objects.air.Aircraft))
        {
            java.lang.String s = ((com.maddox.il2.objects.air.Aircraft)actor).getRegiment().country();
            if("us".equals(s) || "gb".equals(s))
                swimMeshCode = j != 0 ? 1 : 2;
            else
            if("ja".equals(s))
                swimMeshCode = 0;
        }
        o.setAT0(speed);
        o.set(o.azimut(), 0.0F, 0.0F);
        pos.setAbs(loc);
        pos.reset();
        st = 0;
        animStartTime = com.maddox.rts.Time.tick();
        dying = 0;
        setName("_para_" + _counter++);
        collide(true);
        draw = new SoldDraw();
        dreamFire(true);
        drawing(true);
        if(!interpEnd("move"))
            interpPut(new Move(), "move", com.maddox.rts.Time.current(), null);
        if(com.maddox.il2.engine.Actor.isValid(actor))
            logAircraftName = com.maddox.il2.ai.EventLog.name(actor);
        if(netmsginput == null)
            net = new Master(this);
        else
            net = new Mirror(this, netmsginput, k);
    }

    private void testDriver()
    {
        if(driver != null && (driver.isMaster() || driver.isTrackWriter()) && com.maddox.il2.engine.Actor.isValid(getOwner()))
        {
            if(com.maddox.il2.ai.World.isPlayerGunner())
                com.maddox.il2.ai.World.doGunnerParatrooper(this);
            else
                com.maddox.il2.ai.World.doPlayerParatrooper(this);
            setName("_paraplayer_");
            if(com.maddox.il2.game.Mission.isNet())
                com.maddox.il2.net.Chat.sendLog(1, "gore_bailedout", (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host(), (com.maddox.il2.net.NetUser)null);
        }
        if(driver != null)
            driver.tryPreparePilot(this);
    }

    public Paratrooper(com.maddox.il2.engine.Actor actor, int i, int j, com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d)
    {
        this(actor, i, j, loc, vector3d, null, 0);
    }

    public com.maddox.rts.NetMsgSpawn netReplicate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        com.maddox.rts.NetMsgSpawn netmsgspawn = new NetMsgSpawn(net);
        com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
        netmsgspawn.writeFloat((float)point3d.x);
        netmsgspawn.writeFloat((float)point3d.y);
        netmsgspawn.writeFloat((float)point3d.z);
        com.maddox.il2.engine.Orient orient = pos.getAbsOrient();
        netmsgspawn.writeFloat(orient.getAzimut());
        netmsgspawn.writeFloat(orient.getTangage());
        netmsgspawn.writeFloat(orient.getKren());
        netmsgspawn.writeFloat((float)speed.x);
        netmsgspawn.writeFloat((float)speed.y);
        netmsgspawn.writeFloat((float)speed.z);
        netmsgspawn.writeByte(getArmy());
        if(getOwner() != null && netchannel != null && netchannel.isMirrored(getOwner().net))
            netmsgspawn.writeNetObj(getOwner().net);
        else
            netmsgspawn.writeNetObj(null);
        netmsgspawn.writeByte(idxOfPilotPlace);
        netmsgspawn.writeFloat(turn_para_on_height);
        netmsgspawn.writeByte(nRunCycles);
        netmsgspawn.writeNetObj(driver);
        return netmsgspawn;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static final int FPS = 30;
    private static final int FREEFLY_START_FRAME = 0;
    private static final int FREEFLY_LAST_FRAME = 19;
    private static final int FREEFLY_N_FRAMES = 20;
    private static final int FREEFLY_CYCLE_TIME = 633;
    private static final int FREEFLY_ROT_TIME = 2500;
    private static final int PARAUP1_START_FRAME = 19;
    private static final int PARAUP1_LAST_FRAME = 34;
    private static final int PARAUP1_N_FRAMES = 16;
    private static final int PARAUP1_CYCLE_TIME = 500;
    private static final int PARAUP2_START_FRAME = 34;
    private static final int PARAUP2_LAST_FRAME = 54;
    private static final int PARAUP2_N_FRAMES = 21;
    private static final int PARAUP2_CYCLE_TIME = 666;
    private static final int RUN_START_FRAME = 55;
    private static final int RUN_LAST_FRAME = 77;
    private static final int RUN_N_FRAMES = 23;
    private static final int RUN_CYCLE_TIME = 733;
    private static final int FALL_START_FRAME = 77;
    private static final int FALL_LAST_FRAME = 109;
    private static final int FALL_N_FRAMES = 33;
    private static final int FALL_CYCLE_TIME = 1066;
    private static final int LIE_START_FRAME = 109;
    private static final int LIE_LAST_FRAME = 128;
    private static final int LIE_N_FRAMES = 20;
    private static final int LIE_CYCLE_TIME = 633;
    private static final int LIEDEAD_START_FRAME = 129;
    private static final int LIEDEAD_N_FRAMES = 4;
    private static final int PARADEAD_FRAME = 133;
    private static final int FREEFLYDEAD_FRAME = 134;
    private static final float FREE_SPEED = 50F;
    private static final float PARA_SPEED = 5F;
    private static final float RUN_SPEED = 6.545455F;
    public static final java.lang.String playerParaName = "_paraplayer_";
    private java.lang.String logAircraftName;
    private int idxOfPilotPlace;
    private com.maddox.il2.net.NetUser driver;
    private int swimMeshCode;
    private com.maddox.JGP.Vector3d speed;
    private com.maddox.il2.engine.Orient startOrient;
    private com.maddox.il2.engine.Orient faceOrient;
    private static final int ST_FREEFLY = 0;
    private static final int ST_PARAUP1 = 1;
    private static final int ST_PARAUP2 = 2;
    private static final int ST_PARATANGLED = 3;
    private static final int ST_RUN = 4;
    private static final int ST_FALL = 5;
    private static final int ST_LIE = 6;
    private static final int ST_LIEDEAD = 7;
    private static final int ST_SWIM = 8;
    private static final int ST_DISAPPEAR = 9;
    private int st;
    private int dying;
    static final int DYING_NONE = 0;
    static final int DYING_DEAD = 1;
    private int idxOfDeadPose;
    private long animStartTime;
    private long disappearTime;
    private int nRunCycles;
    private float turn_para_on_height;
    private static int _counter = 0;
    private static com.maddox.il2.engine.Mesh preload1 = null;
    private static com.maddox.il2.engine.Mesh preload2 = null;
    private static com.maddox.il2.engine.Mesh preload3 = null;
    private static com.maddox.il2.engine.Mesh preload4 = null;
    private static com.maddox.il2.engine.Mesh preload5 = null;
    private static com.maddox.il2.engine.Mesh preload6 = null;
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static com.maddox.JGP.Vector3f n = new Vector3f();
    private boolean bCheksCaptured;

    static 
    {
        com.maddox.rts.Spawn.add(com.maddox.il2.objects.air.Paratrooper.class, new SPAWN());
    }

























}
