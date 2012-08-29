// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetAircraft.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelTrack;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetSquadron;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetWing;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgNet;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.Property;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.IntHashtable;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.objects.air:
//            CockpitGunner, NetGunner, Aircraft

public abstract class NetAircraft extends com.maddox.il2.objects.sounds.SndAircraft
{
    static class DamagerItem
    {

        public com.maddox.il2.engine.Actor damager;
        public int damage;
        public long lastTime;

        public DamagerItem(com.maddox.il2.engine.Actor actor, int i)
        {
            damager = actor;
            damage = i;
            lastTime = com.maddox.rts.Time.current();
        }
    }

    public static class SPAWN
        implements com.maddox.il2.engine.ActorSpawn, com.maddox.rts.NetSpawn
    {

        private com.maddox.il2.engine.Actor actorSpawnCoop(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            if(actorspawnarg.name == null)
                return null;
            java.lang.String s = actorspawnarg.name.substring(3);
            boolean flag = false;
            com.maddox.il2.objects.air.NetAircraft netaircraft = (com.maddox.il2.objects.air.NetAircraft)com.maddox.il2.engine.Actor.getByName(s);
            if(netaircraft == null)
            {
                netaircraft = (com.maddox.il2.objects.air.NetAircraft)com.maddox.il2.engine.Actor.getByName(" " + s);
                if(netaircraft != null)
                    flag = true;
            }
            if(netaircraft == null)
                return null;
            actorspawnarg.name = null;
            com.maddox.il2.ai.Wing wing = netaircraft.getWing();
            com.maddox.il2.objects.air.NetAircraft netaircraft1 = null;
            com.maddox.il2.objects.air.NetAircraft.loadingCountry = wing.regiment().country();
            try
            {
                netaircraft1 = (com.maddox.il2.objects.air.NetAircraft)cls.newInstance();
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.air.NetAircraft.loadingCountry = null;
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
                return null;
            }
            netaircraft1.bCoopPlane = true;
            int i = netaircraft.aircIndex();
            if(!flag)
            {
                netaircraft.setName(" " + s);
                netaircraft.collide(false);
            }
            netaircraft1.setName(s);
            if(actorspawnarg.bPlayer && actorspawnarg.netChannel == null)
            {
                com.maddox.il2.ai.World.setPlayerAircraft((com.maddox.il2.objects.air.Aircraft)netaircraft1);
                netaircraft1.setFM(1, true);
                com.maddox.il2.ai.World.setPlayerFM();
            } else
            if(com.maddox.il2.game.Mission.isServer())
                netaircraft1.setFM(1, actorspawnarg.netChannel == null);
            else
            if(_netUser != null && _netUser.isTrackWriter())
            {
                com.maddox.il2.ai.World.setPlayerAircraft((com.maddox.il2.objects.air.Aircraft)netaircraft1);
                netaircraft1.setFM(1, false);
                com.maddox.il2.ai.World.setPlayerFM();
            } else
            {
                netaircraft1.setFM(2, actorspawnarg.netChannel == null);
            }
            netaircraft1.FM.M.fuel = actorspawnarg.fuel * netaircraft1.FM.M.maxFuel;
            netaircraft1.bPaintShemeNumberOn = actorspawnarg.bNumberOn;
            netaircraft1.FM.AS.bIsEnableToBailout = netaircraft.FM.AS.bIsEnableToBailout;
            netaircraft1.createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
            ((com.maddox.il2.objects.air.AircraftNet)netaircraft1.net).netUser = _netUser;
            ((com.maddox.il2.objects.air.AircraftNet)netaircraft1.net).netName = s;
            netaircraft1.FM.setSkill(netaircraft.FM.Skill);
            try
            {
                netaircraft1.weaponsLoad(actorspawnarg.weapons);
                netaircraft1.thisWeaponsName = actorspawnarg.weapons;
            }
            catch(java.lang.Exception exception1)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception1);
            }
            if(_netUser != null && (netaircraft1.net.isMaster() || _netUser.isTrackWriter()))
                netaircraft1.createCockpits();
            netaircraft1.FM.AP.way = new Way(netaircraft.FM.AP.way);
            netaircraft1.onAircraftLoaded();
            wing.airc[i] = (com.maddox.il2.objects.air.Aircraft)netaircraft1;
            netaircraft1.setArmy(netaircraft.getArmy());
            netaircraft1.setOwner(wing);
            if(_netUser != null && (netaircraft1.net.isMaster() || _netUser.isTrackWriter()))
                com.maddox.il2.ai.World.setPlayerRegiment();
            if(com.maddox.il2.game.Mission.isServer())
                ((com.maddox.il2.ai.air.Maneuver)netaircraft.FM).Group.changeAircraft((com.maddox.il2.objects.air.Aircraft)netaircraft, (com.maddox.il2.objects.air.Aircraft)netaircraft1);
            netaircraft1.FM.CT.set(netaircraft.FM.CT);
            netaircraft1.FM.CT.forceGear(netaircraft1.FM.CT.GearControl);
            com.maddox.il2.objects.air.Aircraft _tmp = (com.maddox.il2.objects.air.Aircraft)netaircraft1;
            com.maddox.il2.objects.air.Aircraft.forceGear(netaircraft1.getClass(), netaircraft1.hierMesh(), netaircraft1.FM.CT.getGear());
            netaircraft1.pos.setAbs(netaircraft.pos.getAbs());
            netaircraft1.pos.reset();
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            netaircraft.getSpeed(vector3d);
            netaircraft1.setSpeed(vector3d);
            if(netaircraft.FM.brakeShoe)
            {
                ((com.maddox.il2.objects.air.Aircraft)netaircraft1).FM.AP.way.takeoffAirport = netaircraft.FM.AP.way.takeoffAirport;
                ((com.maddox.il2.objects.air.Aircraft)netaircraft1).FM.brakeShoe = true;
                ((com.maddox.il2.objects.air.Aircraft)netaircraft1).FM.turnOffCollisions = true;
                ((com.maddox.il2.objects.air.Aircraft)netaircraft1).FM.brakeShoeLoc.set(netaircraft.FM.brakeShoeLoc);
                ((com.maddox.il2.objects.air.Aircraft)netaircraft1).FM.brakeShoeLastCarrier = netaircraft.FM.brakeShoeLastCarrier;
                ((com.maddox.il2.objects.air.Aircraft)netaircraft1).FM.Gears.bFlatTopGearCheck = true;
                ((com.maddox.il2.objects.air.Aircraft)netaircraft1).makeMirrorCarrierRelPos();
            }
            if(netaircraft.FM.CT.bHasWingControl)
            {
                ((com.maddox.il2.objects.air.Aircraft)netaircraft1).FM.CT.wingControl = netaircraft.FM.CT.wingControl;
                ((com.maddox.il2.objects.air.Aircraft)netaircraft1).FM.CT.forceWing(netaircraft.FM.CT.wingControl);
            }
            netaircraft1.preparePaintScheme();
            netaircraft1.prepareCamouflage();
            com.maddox.il2.objects.air.NetAircraft.loadingCountry = null;
            if(_netUser != null)
            {
                _netUser.tryPrepareSkin(netaircraft1);
                _netUser.tryPrepareNoseart(netaircraft1);
                _netUser.tryPreparePilot(netaircraft1);
                _netUser.setArmy(netaircraft1.getArmy());
            } else
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
                com.maddox.il2.game.Mission.cur().prepareSkinAI((com.maddox.il2.objects.air.Aircraft)netaircraft1);
            netaircraft1.restoreLinksInCoopWing();
            if(netaircraft1.net.isMaster() && (!com.maddox.il2.ai.World.cur().diffCur.Takeoff_N_Landing || netaircraft.FM.AP.way.get(0).Action != 1 || !netaircraft.FM.isStationedOnGround()))
            {
                netaircraft1.FM.EI.setCurControlAll(true);
                netaircraft1.FM.EI.setEngineRunning();
                netaircraft1.FM.CT.setPowerControl(0.75F);
                netaircraft1.FM.setStationedOnGround(false);
                netaircraft1.FM.setWasAirborne(true);
            }
            return netaircraft1;
        }

        private void netSpawnCoop(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            try
            {
                com.maddox.il2.engine.ActorSpawnArg actorspawnarg = new ActorSpawnArg();
                actorspawnarg.fuel = netmsginput.readFloat();
                actorspawnarg.bNumberOn = netmsginput.readBoolean();
                actorspawnarg.name = "net" + netmsginput.read255();
                actorspawnarg.weapons = netmsginput.read255();
                _netUser = (com.maddox.il2.net.NetUser)netmsginput.readNetObj();
                actorspawnarg.netChannel = netmsginput.channel();
                actorspawnarg.netIdRemote = i;
                com.maddox.il2.objects.air.NetAircraft netaircraft = (com.maddox.il2.objects.air.NetAircraft)actorSpawnCoop(actorspawnarg);
                com.maddox.il2.objects.air.NetAircraft.netSpawnCommon(netmsginput, actorspawnarg);
                netaircraft.pos.setAbs(actorspawnarg.point, actorspawnarg.orient);
                netaircraft.pos.reset();
                netaircraft.setSpeed(actorspawnarg.speed);
                com.maddox.il2.objects.air.NetAircraft.netSpawnCommon(netmsginput, actorspawnarg, netaircraft);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
            }
        }

        private com.maddox.il2.engine.Actor _actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            Object obj = null;
            com.maddox.il2.net.NetSquadron netsquadron = null;
            com.maddox.il2.net.NetWing netwing = null;
            int i = 0;
            com.maddox.il2.objects.air.NetAircraft netaircraft = null;
            try
            {
                int j = actorspawnarg.name.length();
                if(_netUser != null)
                {
                    i = java.lang.Integer.parseInt(actorspawnarg.name.substring(j - 2, j));
                    int k = actorspawnarg.name.charAt(j - 3) - 48;
                    int i1 = actorspawnarg.name.charAt(j - 4) - 48;
                    java.lang.Object obj1;
                    if(j == 4)
                    {
                        obj1 = _netUser.netUserRegiment;
                    } else
                    {
                        java.lang.String s = actorspawnarg.name.substring(0, j - 4);
                        obj1 = (com.maddox.il2.ai.Regiment)com.maddox.il2.engine.Actor.getByName(s);
                    }
                    netsquadron = new NetSquadron(((com.maddox.il2.ai.Regiment) (obj1)), i1);
                    netwing = new NetWing(netsquadron, k);
                } else
                {
                    i = java.lang.Integer.parseInt(actorspawnarg.name.substring(j - 1, j)) + 1;
                    int l = actorspawnarg.name.charAt(j - 2) - 48;
                    int j1 = actorspawnarg.name.charAt(j - 3) - 48;
                    i += j1 * 16 + l * 4;
                    java.lang.String s1 = actorspawnarg.name.substring(0, j - 3);
                    com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)com.maddox.il2.engine.Actor.getByName(s1);
                    netsquadron = new NetSquadron(regiment, j1);
                    netwing = new NetWing(netsquadron, l);
                }
                com.maddox.il2.objects.air.NetAircraft.loadingCountry = netsquadron.regiment().country();
                netaircraft = (com.maddox.il2.objects.air.NetAircraft)cls.newInstance();
            }
            catch(java.lang.Exception exception)
            {
                if(netsquadron != null)
                    netsquadron.destroy();
                if(netwing != null)
                    netwing.destroy();
                com.maddox.il2.objects.air.NetAircraft.loadingCountry = null;
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
                return null;
            }
            netaircraft.bCoopPlane = false;
            netaircraft.createNetObject(actorspawnarg.netChannel, actorspawnarg.netIdRemote);
            ((com.maddox.il2.objects.air.AircraftNet)netaircraft.net).netUser = _netUser;
            ((com.maddox.il2.objects.air.AircraftNet)netaircraft.net).netName = actorspawnarg.name;
            if(_netUser != null)
            {
                actorspawnarg.name = null;
                makeName(netaircraft);
            }
            if(actorspawnarg.bPlayer && actorspawnarg.netChannel == null || _netUser != null && _netUser.isTrackWriter())
            {
                com.maddox.il2.ai.World.cur().resetUser();
                com.maddox.il2.ai.World.setPlayerAircraft((com.maddox.il2.objects.air.Aircraft)netaircraft);
                netaircraft.setFM(1, actorspawnarg.netChannel == null);
                com.maddox.il2.ai.World.setPlayerFM();
                actorspawnarg.bPlayer = false;
            } else
            {
                netaircraft.setFM(2, actorspawnarg.netChannel == null);
            }
            netaircraft.FM.setSkill(3);
            netaircraft.FM.M.fuel = actorspawnarg.fuel * netaircraft.FM.M.maxFuel;
            netaircraft.bPaintShemeNumberOn = actorspawnarg.bNumberOn;
            try
            {
                netaircraft.weaponsLoad(actorspawnarg.weapons);
                netaircraft.thisWeaponsName = actorspawnarg.weapons;
            }
            catch(java.lang.Exception exception1)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception1);
            }
            if(netaircraft.net.isMaster() || _netUser != null && _netUser.isTrackWriter())
                netaircraft.createCockpits();
            netaircraft.onAircraftLoaded();
            netwing.setPlane(netaircraft, i);
            com.maddox.il2.objects.air.NetAircraft.loadingCountry = null;
            boolean flag = false;
            if(actorspawnarg.bornPlaceExist)
            {
                com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)com.maddox.il2.ai.World.cur().bornPlaces.get(actorspawnarg.bornPlace);
                com.maddox.il2.engine.Loc loc = bornplace.getAircraftPlace((com.maddox.il2.objects.air.Aircraft)netaircraft, actorspawnarg.stayPlace);
                actorspawnarg.point = loc.getPoint();
                actorspawnarg.orient = loc.getOrient();
                actorspawnarg.armyExist = true;
                actorspawnarg.army = bornplace.army;
                actorspawnarg.speed = new Vector3d();
                if(!com.maddox.il2.ai.World.cur().diffCur.Takeoff_N_Landing || actorspawnarg.stayPlace >= com.maddox.il2.ai.World.cur().airdrome.stayHold.length && !netaircraft.FM.brakeShoe)
                {
                    actorspawnarg.point.z += 1000D;
                    actorspawnarg.speed.x = 100D;
                    actorspawnarg.orient.transform(actorspawnarg.speed);
                    flag = true;
                } else
                {
                    netaircraft.FM.CT.setLanded();
                    com.maddox.il2.objects.air.Aircraft _tmp = (com.maddox.il2.objects.air.Aircraft)netaircraft;
                    com.maddox.il2.objects.air.Aircraft.forceGear(netaircraft.getClass(), netaircraft.hierMesh(), netaircraft.FM.CT.getGear());
                }
                netaircraft.FM.AS.bIsEnableToBailout = bornplace.bParachute;
            }
            actorspawnarg.set(netaircraft);
            if(flag)
            {
                netaircraft.FM.EI.setCurControlAll(true);
                netaircraft.FM.EI.setEngineRunning();
                netaircraft.FM.CT.setPowerControl(0.75F);
                netaircraft.FM.setStationedOnGround(false);
                netaircraft.FM.setWasAirborne(true);
            }
            if(actorspawnarg.speed == null)
                netaircraft.setSpeed(new Vector3d());
            if(_netUser != null)
            {
                _netUser.tryPrepareSkin(netaircraft);
                _netUser.tryPrepareNoseart(netaircraft);
                _netUser.tryPreparePilot(netaircraft);
                _netUser.setArmy(netaircraft.getArmy());
            } else
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
                com.maddox.il2.game.Mission.cur().prepareSkinAI((com.maddox.il2.objects.air.Aircraft)netaircraft);
            if(netaircraft.net.isMaster() || _netUser != null && _netUser.isTrackWriter())
                com.maddox.il2.ai.World.setPlayerRegiment();
            return netaircraft;
        }

        private void makeName(com.maddox.il2.objects.air.NetAircraft netaircraft)
        {
            java.lang.String s = ((com.maddox.il2.objects.air.AircraftNet)netaircraft.net).netUser.uniqueName();
            int i;
            for(i = 0; com.maddox.il2.engine.Actor.getByName(s + "_" + i) != null; i++);
            netaircraft.setName(s + "_" + i);
        }

        private void _netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            try
            {
                com.maddox.il2.engine.ActorSpawnArg actorspawnarg = new ActorSpawnArg();
                actorspawnarg.army = netmsginput.readByte();
                actorspawnarg.armyExist = true;
                actorspawnarg.fuel = netmsginput.readFloat();
                actorspawnarg.bNumberOn = netmsginput.readBoolean();
                actorspawnarg.name = netmsginput.read255();
                actorspawnarg.weapons = netmsginput.read255();
                _netUser = (com.maddox.il2.net.NetUser)netmsginput.readNetObj();
                actorspawnarg.netChannel = netmsginput.channel();
                actorspawnarg.netIdRemote = i;
                com.maddox.il2.objects.air.NetAircraft.netSpawnCommon(netmsginput, actorspawnarg);
                com.maddox.il2.objects.air.NetAircraft netaircraft = (com.maddox.il2.objects.air.NetAircraft)_actorSpawn(actorspawnarg);
                if(netaircraft != null)
                    com.maddox.il2.objects.air.NetAircraft.netSpawnCommon(netmsginput, actorspawnarg, netaircraft);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
            }
        }

        public com.maddox.il2.engine.Actor actorSpawn(com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        {
            if(!com.maddox.il2.game.Mission.isNet())
                return null;
            if(com.maddox.il2.game.Main.cur().netServerParams == null)
                return null;
            if(actorspawnarg.netChannel == null && actorspawnarg.bPlayer)
                _netUser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            com.maddox.il2.engine.Actor actor = null;
            if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
                actor = _actorSpawn(actorspawnarg);
            else
            if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
                actor = actorSpawnCoop(actorspawnarg);
            _netUser = null;
            if(actor != null && actor == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.net.NetMissionTrack.isRecording() && com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
                try
                {
                    com.maddox.rts.NetMsgSpawn netmsgspawn = actor.netReplicate(com.maddox.il2.net.NetMissionTrack.netChannelOut());
                    actor.net.postTo(com.maddox.il2.net.NetMissionTrack.netChannelOut(), netmsgspawn);
                }
                catch(java.lang.Exception exception)
                {
                    com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
                }
            return actor;
        }

        public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            if(com.maddox.il2.game.Main.cur().netServerParams == null)
                return;
            if((netmsginput.channel() instanceof com.maddox.rts.NetChannelInStream) && com.maddox.il2.net.NetMissionTrack.playingVersion() == 100)
            {
                if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
                    netSpawnCoop(i, netmsginput);
                else
                    _netSpawn(i, netmsginput);
            } else
            {
                try
                {
                    byte byte0 = netmsginput.readByte();
                    if((byte0 & 1) == 1)
                        netSpawnCoop(i, netmsginput);
                    else
                        _netSpawn(i, netmsginput);
                }
                catch(java.lang.Exception exception)
                {
                    com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
                    return;
                }
            }
            _netUser = null;
        }

        public java.lang.Class cls;
        private com.maddox.il2.net.NetUser _netUser;

        public SPAWN(java.lang.Class class1)
        {
            cls = class1;
            com.maddox.rts.Spawn.add(cls, this);
        }
    }

    public class Mirror extends com.maddox.il2.objects.air.AircraftNet
    {

        public void makeFirstUnderDeck()
        {
            if(FM.brakeShoe)
            {
                com.maddox.il2.objects.air.NetAircraft.corn.set(pos.getAbsPoint());
                com.maddox.il2.objects.air.NetAircraft.corn1.set(pos.getAbsPoint());
                com.maddox.il2.objects.air.NetAircraft.corn1.z -= 20D;
                com.maddox.il2.engine.Actor actor = com.maddox.il2.engine.Engine.collideEnv().getLine(com.maddox.il2.objects.air.NetAircraft.corn, com.maddox.il2.objects.air.NetAircraft.corn1, false, com.maddox.il2.objects.air.NetAircraft.clipFilter, com.maddox.il2.objects.air.NetAircraft.pship);
                if(!(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric) && com.maddox.il2.game.Mission.isCoop() && com.maddox.rts.Time.current() < 60000L)
                    actor = FM.brakeShoeLastCarrier;
                if(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric)
                {
                    bUnderDeck = true;
                    _lRel.set(pos.getAbs());
                    _lRel.sub(actor.pos.getAbs());
                }
            }
        }

        public void netFirstUpdate(float f, float f1, float f2, float f3, float f4, float f5, float f6, 
                float f7, float f8)
        {
            FM.Vwld.set(f6, f7, f8);
            FM.getAccel().set(0.0D, 0.0D, 0.0D);
            _t = tcur = tupdate = com.maddox.rts.Time.current();
            _p.set(f, f1, f2);
            _v.set(FM.Vwld);
            _o.set(f3, f4, f5);
            _w.set(0.0F, 0.0F, 0.0F);
            FM.Loc.set(f, f1, f2);
            FM.Or.set(f3, f4, f5);
            tint = tcur;
            tlag = 0L;
        }

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
                return netGetGMsg(netmsginput, false);
            if(com.maddox.rts.Time.isPaused() && !com.maddox.il2.net.NetMissionTrack.isPlaying())
                return true;
            if(netmsginput.channel() != masterChannel())
            {
                postRealTo(com.maddox.rts.Message.currentTime(true), masterChannel(), new NetMsgFiltered(netmsginput, 0));
                return true;
            }
            if(isMirrored())
            {
                out.unLockAndSet(netmsginput, 0);
                postReal(com.maddox.rts.Message.currentTime(true), out);
            }
            byte byte0 = netmsginput.readByte();
            int i = netmsginput.readUnsignedByte();
            bGround = (byte0 & 0x20) != 0;
            bUnderDeck = (byte0 & 0x40) != 0;
            if(isFMTrackMirror())
            {
                netmsginput.readUnsignedByte();
                netmsginput.readUnsignedByte();
            } else
            {
                netControls(netmsginput.readUnsignedByte());
                netWeaponControl(netmsginput.readUnsignedByte());
                if(bFirstUpdate)
                {
                    com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)superObj();
                    aircraft.FM.CT.forceGear(aircraft.FM.CT.GearControl);
                    com.maddox.il2.objects.air.Aircraft _tmp = aircraft;
                    com.maddox.il2.objects.air.Aircraft.forceGear(aircraft.getClass(), aircraft.hierMesh(), aircraft.FM.CT.getGear());
                }
            }
            float f = netmsginput.readFloat();
            float f1 = netmsginput.readFloat();
            float f2 = netmsginput.readFloat();
            int j = netmsginput.readShort();
            int k = netmsginput.readShort();
            int l = netmsginput.readShort();
            float f3 = -(((float)j * 180F) / 32000F);
            float f4 = ((float)k * 90F) / 32000F;
            float f5 = ((float)l * 180F) / 32000F;
            int i1 = netmsginput.readShort();
            int j1 = netmsginput.readShort();
            int k1 = netmsginput.readShort();
            float f6 = ((float)i1 * 50F) / 32000F;
            float f7 = ((float)j1 * 50F) / 32000F;
            float f8 = ((float)k1 * 50F) / 32000F;
            if(bUnderDeck)
                f6 = f7 = f8 = 0.0F;
            int l1 = netmsginput.readShort();
            int i2 = netmsginput.readShort();
            int j2 = netmsginput.readShort();
            float f9 = ((float)l1 * 400F) / 32000F;
            float f10 = ((float)i2 * 400F) / 32000F;
            float f11 = ((float)j2 * 400F) / 32000F;
            if(bGround && !bUnderDeck)
                f11 = 0.0F;
            int k2 = netmsginput.readShort();
            int l2 = netmsginput.readShort();
            int i3 = netmsginput.readShort();
            float f12 = ((float)k2 * 2000F) / 32000F;
            float f13 = ((float)l2 * 2000F) / 32000F;
            float f14 = ((float)i3 * 2000F) / 32000F;
            if(bGround || bUnderDeck)
            {
                f12 = 0.0F;
                f13 = 0.0F;
                f14 = 0.0F;
            }
            if(bUnderDeck)
                _lRel.set(((double)k2 * 200D) / 32000D, ((double)l2 * 200D) / 32000D, ((double)i3 * 200D) / 32000D, -(((float)i1 * 180F) / 32000F), ((float)j1 * 90F) / 32000F, ((float)k1 * 180F) / 32000F);
            long l3 = com.maddox.rts.Message.currentTime(false) + (long)i;
            _t = l3;
            if(com.maddox.rts.NetEnv.testLag)
            {
                long l4 = com.maddox.rts.Time.tickNext() - l3;
                if(l4 < 0L)
                    l4 = 0L;
                if(bFirstUpdate || tlag >= l4)
                {
                    bFirstUpdate = false;
                    tlag = l4;
                } else
                if(l3 > tupdate)
                {
                    double d = (double)(l4 - (tcur - tint)) / (double)(l3 - tupdate);
                    if(d > 0.014999999999999999D)
                        d = 0.014999999999999999D;
                    long l5 = (long)((double)(l3 - tupdate) * d);
                    if(l5 > (long)(com.maddox.rts.Time.tickConstLen() / 2))
                        l5 = com.maddox.rts.Time.tickConstLen() / 2;
                    tlag = (tcur - tint) + l5;
                    if(tlag >= l4)
                        tlag = l4;
                }
            } else
            {
                bFirstUpdate = false;
            }
            tupdate = _t;
            FM.Vwld.set(f9, f10, f11);
            FM.getAccel().set(f12, f13, f14);
            _p.set(f, f1, f2);
            _v.set(FM.Vwld);
            _o.set(f3, f4, f5);
            _o.transformInv(FM.Vwld, FM.getVflow());
            _w.set(f6, f7, f8);
            FM.getW().set(f6, f7, f8);
            int j3 = byte0 & 0xf;
            if(j3 == 1)
            {
                float f15 = ((float)netmsginput.readUnsignedByte() / 255F) * 640F;
                float f16 = ((float)netmsginput.readUnsignedByte() / 255F) * 1.6F;
                j3 = FM.EI.getNum();
                for(int k4 = 0; k4 < j3; k4++)
                    if(!isFMTrackMirror())
                    {
                        FM.EI.engines[k4].setw(f15);
                        FM.EI.engines[k4].setPropPhi(f16);
                    }

            } else
            {
                for(int k3 = 0; k3 < j3; k3++)
                {
                    float f17 = ((float)netmsginput.readUnsignedByte() / 255F) * 640F;
                    float f18 = ((float)netmsginput.readUnsignedByte() / 255F) * 1.6F;
                    if(!isFMTrackMirror())
                    {
                        FM.EI.engines[k3].setw(f17);
                        FM.EI.engines[k3].setPropPhi(f18);
                    }
                }

            }
            if((byte0 & 0x10) != 0 && netCockpitTuretNum >= 0)
            {
                int i4 = netmsginput.readUnsignedShort();
                int j4 = netmsginput.readUnsignedShort();
                float f19 = unpackSY(i4);
                float f20 = unpackSP(j4 & 0x7fff);
                FM.CT.WeaponControl[netCockpitWeaponControlNum] = (j4 & 0x8000) != 0;
                if(superObj() == com.maddox.il2.ai.World.getPlayerAircraft())
                {
                    com.maddox.il2.engine.Actor._tmpOrient.set(f19, f20, 0.0F);
                    ((com.maddox.il2.objects.air.CockpitGunner)com.maddox.il2.game.Main3D.cur3D().cockpits[netCockpitIndxPilot]).moveGun(com.maddox.il2.engine.Actor._tmpOrient);
                } else
                {
                    com.maddox.il2.fm.Turret turret = FM.turret[netCockpitTuretNum];
                    turret.tu[0] = f19;
                    turret.tu[1] = f20;
                }
            }
            return true;
        }

        float unpackSY(int i)
        {
            return (float)(((double)i * 360D) / 65000D - 180D);
        }

        float unpackSP(int i)
        {
            return (float)(((double)i * 360D) / 32000D - 180D);
        }

        public void fmUpdate(float f)
        {
            if(tupdate < 0L)
                netFirstUpdate((float)FM.Loc.x, (float)FM.Loc.y, (float)FM.Loc.z, FM.Or.getAzimut(), FM.Or.getTangage(), FM.Or.getKren(), (float)FM.Vwld.x, (float)FM.Vwld.y, (float)FM.Vwld.z);
            f = (float)(com.maddox.rts.Time.tickNext() - tcur) * 0.001F;
            if(f < 0.001F)
                return;
            tcur = com.maddox.rts.Time.tickNext();
            FM.CT.update(f, 50F, FM.EI, false, isFMTrackMirror());
            FM.Gears.ground(FM, false, bGround);
            FM.Gears.bFlatTopGearCheck = false;
            for(int i = 0; i < 3; i++)
            {
                FM.Gears.gWheelAngles[i] = (FM.Gears.gWheelAngles[i] + (float)java.lang.Math.toDegrees(java.lang.Math.atan((FM.Gears.gVelocity[i] * (double)f) / 0.375D))) % 360F;
                FM.Gears.gVelocity[i] *= 0.94999998807907104D;
            }

            hierMesh().chunkSetAngles("GearL1_D0", 0.0F, -FM.Gears.gWheelAngles[0], 0.0F);
            hierMesh().chunkSetAngles("GearR1_D0", 0.0F, -FM.Gears.gWheelAngles[1], 0.0F);
            hierMesh().chunkSetAngles("GearC1_D0", 0.0F, -FM.Gears.gWheelAngles[2], 0.0F);
            float f2 = FM.Gears.getSteeringAngle();
            moveSteering(f2);
            if(FM.Gears.nearGround())
                moveWheelSink();
            FM.EI.netupdate(f, isFMTrackMirror());
            FM.FMupdate(f);
            long l;
            for(tint = tcur - tlag; tint > _t; _t += l)
            {
                l = tint - _t;
                if(l > (long)com.maddox.rts.Time.tickConstLen())
                    l = com.maddox.rts.Time.tickConstLen();
                float f3 = (float)l * 0.001F;
                _p.x += _v.x * f3;
                _p.y += _v.y * f3;
                _p.z += _v.z * f3;
                _v.x += FM.getAccel().x * (double)f3;
                _v.y += FM.getAccel().y * (double)f3;
                _v.z += FM.getAccel().z * (double)f3;
                TmpV.scale(f3, _w);
                _o.increment((float)(-java.lang.Math.toDegrees(TmpV.z)), (float)(-java.lang.Math.toDegrees(TmpV.y)), (float)java.lang.Math.toDegrees(TmpV.x));
            }

            com.maddox.il2.ai.World.land();
            float f1 = com.maddox.il2.engine.Landscape.HQ(_p.x, _p.y);
            if(com.maddox.il2.ai.World.land().isWater(_p.x, _p.y))
            {
                if(_p.z < f1 - 20F)
                    _p.z = f1 - 20F;
            } else
            if(_p.z < f1 + 1.0F)
                _p.z = f1 + 1.0F;
            TmpVd.set(_p);
            save_dt = 0.98F * save_dt + 0.02F * ((float)(tint - tupdate) * 0.001F);
            f2 = 0.03F;
            if(_v.length() > 0.0F)
            {
                f2 = 1.08F - save_dt * 2.0F;
                if(f2 > 1.0F)
                    f2 = 1.0F;
                if(f2 < 0.03F)
                    f2 = 0.03F;
            }
            saveCoeff = 0.98F * saveCoeff + 0.02F * f2;
            FM.Loc.interpolate(TmpVd, saveCoeff);
            float f4 = saveCoeff * 2.0F;
            if(com.maddox.il2.net.NetMissionTrack.isPlaying())
                f4 = saveCoeff / 4F;
            if(f4 > 1.0F)
                f4 = 1.0F;
            FM.Or.interpolate(_o, f4);
            if(bUnderDeck)
            {
                com.maddox.il2.objects.air.NetAircraft.corn.set(FM.Loc);
                com.maddox.il2.objects.air.NetAircraft.corn1.set(FM.Loc);
                com.maddox.il2.objects.air.NetAircraft.corn1.z -= 20D;
                com.maddox.il2.engine.Actor actor = com.maddox.il2.engine.Engine.collideEnv().getLine(com.maddox.il2.objects.air.NetAircraft.corn, com.maddox.il2.objects.air.NetAircraft.corn1, false, com.maddox.il2.objects.air.NetAircraft.clipFilter, com.maddox.il2.objects.air.NetAircraft.pship);
                if(!(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric) && com.maddox.il2.game.Mission.isCoop() && com.maddox.rts.Time.current() < 60000L)
                    actor = FM.brakeShoeLastCarrier;
                if(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric)
                {
                    com.maddox.il2.objects.air.NetAircraft.lCorn.set(_lRel);
                    com.maddox.il2.objects.air.NetAircraft.lCorn.add(actor.pos.getAbs());
                    FM.Loc.set(com.maddox.il2.objects.air.NetAircraft.lCorn.getPoint());
                    FM.Or.set(com.maddox.il2.objects.air.NetAircraft.lCorn.getOrient());
                    saveCoeff = 1.0F;
                    _p.set(FM.Loc);
                    _o.set(FM.Or);
                    actor.getSpeed(FM.Vwld);
                    _v.x = (float)FM.Vwld.x;
                    _v.y = (float)FM.Vwld.y;
                    _v.z = (float)FM.Vwld.z;
                }
            }
            if(isFMTrackMirror())
                fmTrack.FMupdate(FM);
            if(FM.isTick(44, 0))
            {
                FM.AS.update(f * 44F);
                ((com.maddox.il2.objects.air.Aircraft)superObj()).rareAction(f * 44F, false);
                if(FM.Loc.z - com.maddox.il2.engine.Engine.land().HQ_Air(FM.Loc.x, FM.Loc.y) > 40D)
                {
                    FM.setWasAirborne(true);
                    FM.setStationedOnGround(false);
                } else
                if(FM.Vwld.length() < 1.0D)
                    FM.setStationedOnGround(true);
            }
        }

        public void netControls(int i)
        {
            FM.CT.GearControl = (i & 1) == 0 ? 0.0F : 1.0F;
            FM.CT.FlapsControl = (i & 2) == 0 ? 0.0F : 1.0F;
            FM.CT.BrakeControl = (i & 4) == 0 ? 0.0F : 1.0F;
            FM.CT.setRadiatorControl((i & 8) == 0 ? 0.0F : 1.0F);
            FM.CT.BayDoorControl = (i & 0x10) == 0 ? 0.0F : 1.0F;
            FM.CT.AirBrakeControl = (i & 0x20) == 0 ? 0.0F : 1.0F;
        }

        public void netWeaponControl(int i)
        {
            int l = FM.CT.WeaponControl.length;
            int j = 0;
            for(int k = 1; j < l && k < 256; k <<= 1)
            {
                FM.CT.WeaponControl[j] = (i & k) != 0;
                j++;
            }

        }

        com.maddox.rts.NetMsgFiltered out;
        private long tupdate;
        private long _t;
        private long tcur;
        private com.maddox.JGP.Point3f _p;
        private com.maddox.JGP.Vector3f _v;
        private com.maddox.il2.engine.Orient _o;
        private com.maddox.JGP.Vector3f _w;
        private com.maddox.JGP.Vector3f TmpV;
        private com.maddox.JGP.Vector3d TmpVd;
        private float save_dt;
        private float saveCoeff;
        private boolean bGround;
        private boolean bUnderDeck;
        private long tint;
        private long tlag;
        private boolean bFirstUpdate;
        private com.maddox.il2.engine.Loc _lRel;


        public Mirror(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(actor, netchannel, i);
            out = new NetMsgFiltered();
            tupdate = -1L;
            _p = new Point3f();
            _v = new Vector3f();
            _o = new Orient();
            _w = new Vector3f();
            TmpV = new Vector3f();
            TmpVd = new Vector3d();
            save_dt = 0.001F;
            saveCoeff = 1.0F;
            bGround = false;
            bUnderDeck = false;
            bFirstUpdate = true;
            _lRel = new Loc();
            try
            {
                out.setIncludeTime(true);
                out.setFilterArg(actor);
            }
            catch(java.lang.Exception exception) { }
        }
    }

    static class ClipFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            return actor instanceof com.maddox.il2.objects.ships.BigshipGeneric;
        }

        ClipFilter()
        {
        }
    }

    class Master extends com.maddox.il2.objects.air.AircraftNet
        implements com.maddox.rts.NetUpdate
    {

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
                return netGetGMsg(netmsginput, true);
            if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                return true;
            } else
            {
                int i = netmsginput.readByte();
                int j = netmsginput.readByte();
                int k = (i & 1) << 1 | j & 1;
                i &= -2;
                j &= -2;
                msgSndShot((float)k * 0.05F + 0.01F, (double)i * 0.25D, (double)j * 0.25D, 0.0D);
                return true;
            }
        }

        public void netUpdate()
        {
            if(com.maddox.rts.Time.tickCounter() <= 2)
                return;
            if(netUser == null && FM.brakeShoe)
            {
                int i = FM.actor.hashCode() & 0xf;
                if((countUpdates++ & 0xf) != i)
                    return;
            } else
            {
                countUpdates = 0;
            }
            if(weaponsIsEmpty)
                FM.CT.WCT = 0;
            boolean flag = (FM.CT.WCT & 0xf) != 0;
            try
            {
                out.unLockAndClear();
                int j = 0;
                boolean flag1 = false;
                boolean flag2 = false;
                int j1 = 0;
                for(int l1 = 0; l1 < FM.EI.getNum(); l1++)
                {
                    int k2 = (int)((FM.EI.engines[l1].getw() / 640F) * 255F);
                    if(l1 == 0)
                        j1 = k2;
                    else
                    if(j1 != k2)
                        flag2 = true;
                    if(k2 != FM.EI.engines[l1].wNetPrev)
                    {
                        flag1 = true;
                        FM.EI.engines[l1].wNetPrev = k2;
                    }
                }

                if(flag1)
                    if(flag2)
                    {
                        j = 1;
                    } else
                    {
                        j = FM.EI.getNum();
                        if(j > 15)
                            j = 15;
                    }
                if(netCockpitValid && netCockpitTuretNum >= 0)
                    j |= 0x10;
                if(FM.Gears.onGround())
                    j |= 0x20;
                if(FM.Gears.isUnderDeck() && FM.Vrel.lengthSquared() < 2D)
                {
                    com.maddox.il2.objects.air.NetAircraft.corn.set(FM.Loc);
                    com.maddox.il2.objects.air.NetAircraft.corn1.set(FM.Loc);
                    com.maddox.il2.objects.air.NetAircraft.corn1.z -= 20D;
                    com.maddox.il2.engine.Actor actor = com.maddox.il2.engine.Engine.collideEnv().getLine(com.maddox.il2.objects.air.NetAircraft.corn, com.maddox.il2.objects.air.NetAircraft.corn1, false, com.maddox.il2.objects.air.NetAircraft.clipFilter, com.maddox.il2.objects.air.NetAircraft.pship);
                    if(!(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric) && com.maddox.il2.game.Mission.isCoop() && com.maddox.rts.Time.current() < 60000L)
                        actor = FM.brakeShoeLastCarrier;
                    if(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric)
                    {
                        com.maddox.il2.objects.air.NetAircraft.lCorn.set(pos.getAbs());
                        com.maddox.il2.objects.air.NetAircraft.lCorn.sub(actor.pos.getAbs());
                        if(java.lang.Math.abs(com.maddox.il2.objects.air.NetAircraft.lCorn.getX()) < 200D && java.lang.Math.abs(com.maddox.il2.objects.air.NetAircraft.lCorn.getY()) < 200D && java.lang.Math.abs(com.maddox.il2.objects.air.NetAircraft.lCorn.getZ()) < 200D)
                            j |= 0x40;
                    }
                }
                out.writeByte(j);
                int k = (int)(com.maddox.rts.Time.tickNext() - com.maddox.rts.Time.current());
                if(k > 255)
                    k = 255;
                out.writeByte(k);
                out.writeByte(FM.CT.CTL);
                out.writeByte(FM.CT.WCT);
                FM.CT.WCT &= 3;
                pos.getAbs(p, o);
                out.writeFloat((float)p.x);
                out.writeFloat((float)p.y);
                out.writeFloat((float)p.z);
                o.wrap();
                int l = (int)((o.getYaw() * 32000F) / 180F);
                j1 = (int)((o.tangage() * 32000F) / 90F);
                int i2 = (int)((o.kren() * 32000F) / 180F);
                out.writeShort(l);
                out.writeShort(j1);
                out.writeShort(i2);
                if((j & 0x40) == 0)
                {
                    vec3f.set(FM.getW());
                    int l2 = (int)((vec3f.x * 32000F) / 50F);
                    int j3 = (int)((vec3f.y * 32000F) / 50F);
                    int l3 = (int)((vec3f.z * 32000F) / 50F);
                    out.writeShort(l2);
                    out.writeShort(j3);
                    out.writeShort(l3);
                } else
                {
                    com.maddox.il2.objects.air.NetAircraft.lCorn.get(o);
                    o.wrap();
                    int i1 = (int)((o.getYaw() * 32000F) / 180F);
                    int k1 = (int)((o.tangage() * 32000F) / 90F);
                    int j2 = (int)((o.kren() * 32000F) / 180F);
                    out.writeShort(i1);
                    out.writeShort(k1);
                    out.writeShort(j2);
                }
                vec3f.set(FM.Vwld);
                int i3 = (int)((vec3f.x * 32000F) / 400F);
                int k3 = (int)((vec3f.y * 32000F) / 400F);
                int i4 = (int)((vec3f.z * 32000F) / 400F);
                out.writeShort(i3);
                out.writeShort(k3);
                out.writeShort(i4);
                if((j & 0x40) == 0)
                {
                    vec3f.set(FM.getAccel());
                    int j4 = (int)((vec3f.x * 32000F) / 2000F);
                    int i5 = (int)((vec3f.y * 32000F) / 2000F);
                    int k5 = (int)((vec3f.z * 32000F) / 2000F);
                    out.writeShort(j4);
                    out.writeShort(i5);
                    out.writeShort(k5);
                } else
                {
                    int k4 = (int)((com.maddox.il2.objects.air.NetAircraft.lCorn.getX() * 32000D) / 200D);
                    int j5 = (int)((com.maddox.il2.objects.air.NetAircraft.lCorn.getY() * 32000D) / 200D);
                    int l5 = (int)((com.maddox.il2.objects.air.NetAircraft.lCorn.getZ() * 32000D) / 200D);
                    out.writeShort(k4);
                    out.writeShort(j5);
                    out.writeShort(l5);
                }
                for(int l4 = 0; l4 < (j & 0xf); l4++)
                {
                    out.writeByte((byte)(int)((FM.EI.engines[l4].getw() / 640F) * 255F));
                    out.writeByte((byte)(int)((FM.EI.engines[l4].getPropPhi() / 1.6F) * 255F));
                }

                if(netCockpitValid && netCockpitTuretNum >= 0)
                {
                    com.maddox.il2.fm.Turret turret = FM.turret[netCockpitTuretNum];
                    boolean flag3 = FM.CT.WeaponControl[netCockpitWeaponControlNum];
                    out.writeShort(packSY(turret.tu[0]));
                    out.writeShort(packSP(turret.tu[1]) | (flag3 ? 0x8000 : 0));
                }
                post(com.maddox.rts.Time.current(), out);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
            if(weaponsCheck && com.maddox.rts.Time.current() > weaponsSyncTime)
            {
                weaponsSyncTime = com.maddox.rts.Time.current() + 5000L;
                weaponsCheck = false;
                if(isWeaponsChanged(weaponsBitStates))
                {
                    weaponsBitStates = getWeaponsBitStates(weaponsBitStates);
                    netPutWeaponsBitStates(weaponsBitStates);
                    weaponsIsEmpty = isWeaponsAllEmpty();
                }
            }
            if(flag)
                weaponsCheck = true;
        }

        int packSY(float f)
        {
            return 0xffff & (int)((((double)f % 360D + 180D) * 65000D) / 360D);
        }

        int packSP(float f)
        {
            return 0x7fff & (int)((((double)f % 360D + 180D) * 32000D) / 360D);
        }

        com.maddox.rts.NetMsgFiltered out;
        public byte weaponsBitStates[];
        public boolean weaponsIsEmpty;
        public boolean weaponsCheck;
        public long weaponsSyncTime;
        public int curWayPoint;
        private com.maddox.JGP.Vector3f vec3f;
        private com.maddox.JGP.Point3d p;
        private com.maddox.il2.engine.Orient o;
        private int countUpdates;

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
            out = new NetMsgFiltered();
            weaponsIsEmpty = false;
            weaponsCheck = false;
            weaponsSyncTime = 0L;
            curWayPoint = 0;
            vec3f = new Vector3f();
            p = new Point3d();
            o = new Orient();
            countUpdates = 0;
            try
            {
                out.setIncludeTime(true);
                out.setFilterArg(actor);
            }
            catch(java.lang.Exception exception) { }
        }
    }

    public class AircraftNet extends com.maddox.il2.engine.ActorNet
    {

        private void createFilterTable()
        {
            if(com.maddox.il2.game.Main.cur().netServerParams == null)
                return;
            if(com.maddox.il2.game.Main.cur().netServerParams.isMirror())
            {
                return;
            } else
            {
                filterTable = new IntHashtable();
                return;
            }
        }

        public com.maddox.il2.net.NetUser netUser;
        public java.lang.String netName;
        public com.maddox.util.IntHashtable filterTable;

        public AircraftNet(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
            createFilterTable();
        }

        public AircraftNet(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(actor, netchannel, i);
            createFilterTable();
        }
    }


    private boolean isCoopPlane()
    {
        return bCoopPlane;
    }

    protected static java.lang.String[] partNames()
    {
        return com.maddox.il2.objects.air.Aircraft.partNames();
    }

    protected int curDMGLevel(int i)
    {
        return 0;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
    }

    protected void netHits(int i, int j, int k, com.maddox.il2.engine.Actor actor)
    {
    }

    protected void doExplosion()
    {
    }

    public int curDMGProp(int i)
    {
        return 0;
    }

    protected void weaponsLoad(java.lang.String s)
        throws java.lang.Exception
    {
    }

    protected void createCockpits()
    {
    }

    public void setFM(int i, boolean flag)
    {
    }

    public void preparePaintScheme()
    {
    }

    public void prepareCamouflage()
    {
    }

    public int aircIndex()
    {
        return -1;
    }

    public com.maddox.il2.ai.Wing getWing()
    {
        return null;
    }

    public void onAircraftLoaded()
    {
    }

    public com.maddox.il2.net.NetUser netUser()
    {
        if(!isNet())
            return null;
        else
            return ((com.maddox.il2.objects.air.AircraftNet)net).netUser;
    }

    public java.lang.String netName()
    {
        if(!isNet())
            return null;
        else
            return ((com.maddox.il2.objects.air.AircraftNet)net).netName;
    }

    public boolean isNetPlayer()
    {
        if(!isNet())
            return false;
        else
            return ((com.maddox.il2.objects.air.AircraftNet)net).netUser != null;
    }

    public void moveSteering(float f)
    {
    }

    public void moveWheelSink()
    {
    }

    public void setFMTrack(com.maddox.il2.fm.FlightModelTrack flightmodeltrack)
    {
        fmTrack = flightmodeltrack;
    }

    public com.maddox.il2.fm.FlightModelTrack fmTrack()
    {
        return fmTrack;
    }

    public boolean isFMTrackMirror()
    {
        return fmTrack != null && fmTrack.isMirror();
    }

    public boolean netNewAState_isEnable(boolean flag)
    {
        if(!isNet())
            return false;
        if(flag && net.isMaster())
            return false;
        if(!flag && !net.isMirrored())
            return false;
        return !flag || !(net.masterChannel() instanceof com.maddox.rts.NetChannelInStream);
    }

    public com.maddox.rts.NetMsgGuaranted netNewAStateMsg(boolean flag)
        throws java.io.IOException
    {
        if(!isNet())
            return null;
        if(flag && net.isMaster())
            return null;
        if(!flag && !net.isMirrored())
            return null;
        if(flag && (net.masterChannel() instanceof com.maddox.rts.NetChannelInStream))
            return null;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        if(flag)
            netmsgguaranted.writeByte(128);
        else
            netmsgguaranted.writeByte(0);
        return netmsgguaranted;
    }

    public void netSendAStateMsg(boolean flag, com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        if(flag)
            net.postTo(net.masterChannel(), netmsgguaranted);
        else
            net.post(netmsgguaranted);
    }

    public void netUpdateWayPoint()
    {
        if(net == null || com.maddox.il2.game.Main.cur().netServerParams == null || !com.maddox.il2.game.Main.cur().netServerParams.isCoop() || com.maddox.il2.game.Main.cur().netServerParams.isMaster() || !net.isMaster() || !net.isMirrored())
            return;
        com.maddox.il2.objects.air.Master master = (com.maddox.il2.objects.air.Master)net;
        if(master.curWayPoint != FM.AP.way.Cur())
        {
            master.curWayPoint = FM.AP.way.Cur();
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(1);
                netmsgguaranted.writeShort(master.curWayPoint);
                master.postTo(com.maddox.il2.game.Main.cur().netServerParams.masterChannel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
            }
        }
    }

    private boolean netGetUpdateWayPoint(com.maddox.rts.NetMsgInput netmsginput, boolean flag, boolean flag1)
        throws java.io.IOException
    {
        if(flag)
            return false;
        int i = netmsginput.readUnsignedShort();
        if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
        {
            FM.AP.way.setCur(i);
            if(i == FM.AP.way.size() - 1)
                FM.AP.way.next();
        } else
        {
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(1);
                netmsgguaranted.writeShort(i);
                net.postTo(com.maddox.il2.game.Main.cur().netServerParams.masterChannel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
            }
        }
        return true;
    }

    private int getWeaponsAmount()
    {
        int i = FM.CT.Weapons.length;
        if(FM.CT.Weapons.length == 0)
            return 0;
        int j = 0;
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[k];
            if(abulletemitter != null)
            {
                for(int l = 0; l < abulletemitter.length; l++)
                    if(abulletemitter[l] != null)
                        j++;

            }
        }

        return j;
    }

    private boolean isWeaponsAllEmpty()
    {
        int i = FM.CT.Weapons.length;
        if(FM.CT.Weapons.length == 0)
            return true;
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[j];
            if(abulletemitter != null)
            {
                for(int k = 0; k < abulletemitter.length; k++)
                    if(abulletemitter[k] != null && abulletemitter[k].countBullets() != 0)
                        return false;

            }
        }

        return true;
    }

    private byte[] getWeaponsBitStatesBuf(byte abyte0[])
    {
        int i = getWeaponsAmount();
        if(i == 0)
            return null;
        int j = (i + 7) / 8;
        if(abyte0 == null || abyte0.length != j)
            abyte0 = new byte[j];
        for(int k = 0; k < j; k++)
            abyte0[k] = 0;

        return abyte0;
    }

    private byte[] getWeaponsBitStates(byte abyte0[])
    {
        abyte0 = getWeaponsBitStatesBuf(abyte0);
        if(abyte0 == null)
            return null;
        int i = 0;
        int j = FM.CT.Weapons.length;
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[k];
            if(abulletemitter != null)
            {
                for(int l = 0; l < abulletemitter.length; l++)
                    if(abulletemitter[l] != null)
                    {
                        if(abulletemitter[l].countBullets() != 0)
                            abyte0[i / 8] |= 1 << i % 8;
                        i++;
                    }

            }
        }

        return abyte0;
    }

    private void setWeaponsBitStates(byte abyte0[])
    {
        int i = 0;
        int j = FM.CT.Weapons.length;
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[k];
            if(abulletemitter != null)
            {
                for(int l = 0; l < abulletemitter.length; l++)
                    if(abulletemitter[l] != null)
                    {
                        if((abyte0[i / 8] & 1 << i % 8) == 0)
                            abulletemitter[l]._loadBullets(0);
                        i++;
                    }

            }
        }

    }

    private boolean isWeaponsChanged(byte abyte0[])
    {
        if(getWeaponsAmount() == 0)
            return false;
        if(abyte0 == null)
            return true;
        int i = 0;
        int j = FM.CT.Weapons.length;
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[k];
            if(abulletemitter != null)
            {
                for(int l = 0; l < abulletemitter.length; l++)
                    if(abulletemitter[l] != null)
                    {
                        if(((abyte0[i / 8] & 1 << i % 8) == 0) != (abulletemitter[l].countBullets() == 0))
                            return true;
                        i++;
                    }

            }
        }

        return false;
    }

    private void netPutWeaponsBitStates(byte abyte0[])
    {
        if(!isNet() || net.countMirrors() == 0)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(2);
            netmsgguaranted.write(abyte0);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
        }
    }

    private boolean netGetWeaponsBitStates(com.maddox.rts.NetMsgInput netmsginput, boolean flag, boolean flag1)
        throws java.io.IOException
    {
        if(flag || flag1)
            return false;
        byte abyte0[] = getWeaponsBitStatesBuf(null);
        for(int i = 0; i < abyte0.length; i++)
            abyte0[i] = (byte)netmsginput.readUnsignedByte();

        setWeaponsBitStates(abyte0);
        netPutWeaponsBitStates(abyte0);
        return true;
    }

    public boolean isGunPodsExist()
    {
        return bGunPodsExist;
    }

    public boolean isGunPodsOn()
    {
        return bGunPodsOn;
    }

    public void setGunPodsOn(boolean flag)
    {
        if(bGunPodsOn == flag)
            return;
        for(int i = 0; i < FM.CT.Weapons.length; i++)
        {
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[i];
            if(abulletemitter != null)
            {
                for(int j = 0; j < abulletemitter.length; j++)
                    if(abulletemitter[j] != null)
                        abulletemitter[j].setPause(!flag);

            }
        }

        bGunPodsOn = flag;
        if(!isNet() || net.countMirrors() == 0)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            if(flag)
                netmsgguaranted.writeByte(3);
            else
                netmsgguaranted.writeByte(4);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
        }
    }

    public void replicateDropFuelTanks()
    {
        if(!isNet() || net.countMirrors() == 0)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(5);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
        }
    }

    protected void netPutHits(boolean flag, com.maddox.rts.NetChannel netchannel, int i, int j, int k, com.maddox.il2.engine.Actor actor)
    {
        if(!flag && net.countMirrors() == 0)
            return;
        if(!com.maddox.il2.engine.Actor.isValid(actor) || !actor.isNet())
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            if(flag)
                netmsgguaranted.writeByte(134);
            else
                netmsgguaranted.writeByte(6);
            netmsgguaranted.writeByte(i & 0xf | j << 4 & 0xf0);
            netmsgguaranted.writeByte(k);
            netmsgguaranted.writeNetObj(actor.net);
            if(flag)
                net.postTo(net.masterChannel(), netmsgguaranted);
            else
                net.postExclude(netchannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
        }
    }

    private boolean netGetHits(com.maddox.rts.NetMsgInput netmsginput, boolean flag, boolean flag1)
        throws java.io.IOException
    {
        if(flag && !flag1)
            return false;
        int i = netmsginput.readUnsignedByte();
        int j = i >> 4 & 0xf;
        i &= 0xf;
        int k = netmsginput.readUnsignedByte();
        if(k >= 44)
            return false;
        com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
        if(netobj == null)
            return true;
        com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)netobj.superObj();
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return true;
        if(!flag && flag1)
            netPutHits(true, null, i, j, k, actor);
        if(net.countMirrors() > 1)
            netPutHits(false, netmsginput.channel(), i, j, k, actor);
        netHits(i, j, k, actor);
        return true;
    }

    public void hitProp(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        if(isNet() && net.isMirrored())
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(7);
                netmsgguaranted.writeByte(i);
                netmsgguaranted.writeByte(j);
                netmsgguaranted.writeNetObj(actor == null ? null : ((com.maddox.rts.NetObj) (actor.net)));
                net.post(netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
            }
    }

    private boolean netGetHitProp(com.maddox.rts.NetMsgInput netmsginput, boolean flag, boolean flag1)
        throws java.io.IOException
    {
        if(flag || flag1)
        {
            return false;
        } else
        {
            int i = netmsginput.readUnsignedByte();
            int j = netmsginput.readUnsignedByte();
            com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
            hitProp(i, j, netobj == null ? null : (com.maddox.il2.engine.Actor)netobj.superObj());
            return true;
        }
    }

    protected void netPutCut(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        if(!isNet() || net.countMirrors() == 0)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(8);
            netmsgguaranted.writeByte(i);
            netmsgguaranted.writeByte(j);
            netmsgguaranted.writeNetObj(actor == null ? null : ((com.maddox.rts.NetObj) (actor.net)));
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
        }
    }

    private boolean netGetCut(com.maddox.rts.NetMsgInput netmsginput, boolean flag, boolean flag1)
        throws java.io.IOException
    {
        if(flag || flag1)
            return false;
        int i = netmsginput.readUnsignedByte();
        if(i >= 44)
        {
            return false;
        } else
        {
            int j = netmsginput.readUnsignedByte();
            com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
            nextCUTLevel(com.maddox.il2.objects.air.NetAircraft.partNames()[i] + "_D0", j, netobj == null ? null : (com.maddox.il2.engine.Actor)netobj.superObj());
            return true;
        }
    }

    public void netExplode()
    {
        if(!isNet() || net.countMirrors() == 0)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(9);
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
        }
    }

    private boolean netGetExplode(com.maddox.rts.NetMsgInput netmsginput, boolean flag, boolean flag1)
        throws java.io.IOException
    {
        if(flag || flag1)
        {
            return false;
        } else
        {
            netExplode();
            doExplosion();
            return true;
        }
    }

    public void setDiedFlag(boolean flag)
    {
        if(isAlive() && flag && isNet() && com.maddox.il2.engine.Actor.isValid(getDamager()) && getDamager().isNet() && net.countMirrors() > 0)
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(10);
                netmsgguaranted.writeNetObj(getDamager().net);
                net.post(netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
            }
        super.setDiedFlag(flag);
    }

    private boolean netGetDead(com.maddox.rts.NetMsgInput netmsginput, boolean flag, boolean flag1)
        throws java.io.IOException
    {
        com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
        if(netobj != null)
        {
            if(isAlive())
                com.maddox.il2.ai.World.onActorDied(this, (com.maddox.il2.engine.Actor)netobj.superObj());
            if(net.countMirrors() > 0)
                try
                {
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                    netmsgguaranted.writeByte(10);
                    netmsgguaranted.writeNetObj(netobj);
                    net.post(netmsgguaranted);
                }
                catch(java.lang.Exception exception)
                {
                    com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
                }
        }
        return true;
    }

    public void netFirstUpdate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        if(!com.maddox.il2.game.Mission.isDogfight() && (!com.maddox.il2.game.Mission.isCoop() || !isNetPlayer()) && (netchannel instanceof com.maddox.rts.NetChannelOutStream))
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(11);
            netReplicateFirstUpdate(netchannel, netmsgguaranted);
            net.postTo(netchannel, netmsgguaranted);
            if(com.maddox.il2.game.Mission.isSingle() && com.maddox.il2.ai.World.getPlayerAircraft() == this)
                if(fmTrack() == null)
                {
                    if(isNetMaster())
                        new com.maddox.rts.MsgAction(true, this) {

                            public void doAction(java.lang.Object obj)
                            {
                                new FlightModelTrack((com.maddox.il2.objects.air.Aircraft)obj);
                            }

                        }
;
                } else
                {
                    com.maddox.rts.MsgNet.postRealNewChannel(fmTrack(), netchannel);
                }
        }
        netCockpitFirstUpdate(this, netchannel);
    }

    private boolean netGetFirstUpdate(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        com.maddox.il2.engine.ActorSpawnArg actorspawnarg = new ActorSpawnArg();
        try
        {
            com.maddox.il2.objects.air.NetAircraft.netSpawnCommon(netmsginput, actorspawnarg);
            pos.setAbs(actorspawnarg.point, actorspawnarg.orient);
            pos.reset();
            setSpeed(actorspawnarg.speed);
            com.maddox.il2.objects.air.NetAircraft.netSpawnCommon(netmsginput, actorspawnarg, this);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
        }
        return true;
    }

    public int netCockpitAstatePilotIndx(int i)
    {
        return com.maddox.il2.objects.air.NetAircraft.netCockpitAstatePilotIndx(getClass(), i);
    }

    public static int netCockpitAstatePilotIndx(java.lang.Class class1, int i)
    {
        if(i < 0)
            return -1;
        java.lang.Object obj = com.maddox.rts.Property.value(class1, "cockpitClass");
        if(obj == null)
            return -1;
        if(obj instanceof java.lang.Class)
            if(i > 0)
                return -1;
            else
                return com.maddox.rts.Property.intValue((java.lang.Class)obj, "astatePilotIndx", 0);
        java.lang.Class aclass[] = (java.lang.Class[])obj;
        if(i >= aclass.length)
            return -1;
        else
            return com.maddox.rts.Property.intValue(aclass[i], "astatePilotIndx", 0);
    }

    public void netCockpitAuto(com.maddox.il2.engine.Actor actor, int i, boolean flag)
    {
        short aword0[] = null;
        int j = 0;
        java.lang.Object obj = com.maddox.rts.Property.value(getClass(), "cockpitClass");
        if(obj == null)
            return;
        if(obj instanceof java.lang.Class)
        {
            if(i > 0)
                return;
            j = com.maddox.rts.Property.intValue((java.lang.Class)obj, "weaponControlNum", 10);
        } else
        {
            java.lang.Class aclass[] = (java.lang.Class[])obj;
            if(i >= aclass.length)
                return;
            j = com.maddox.rts.Property.intValue(aclass[i], "weaponControlNum", 10);
        }
        if(com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo)
        {
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[j];
            if(abulletemitter != null)
            {
                aword0 = new short[abulletemitter.length];
                for(int k = 0; k < abulletemitter.length; k++)
                {
                    int l = abulletemitter[k].countBullets();
                    if(l < 0)
                        aword0[k] = -1;
                    else
                        aword0[k] = (short)l;
                }

            }
        }
        netCockpitAuto(actor, i, flag, aword0, null);
    }

    private void netCockpitAuto(com.maddox.il2.engine.Actor actor, int i, boolean flag, short aword0[], com.maddox.rts.NetChannel netchannel)
    {
        java.lang.Object obj = com.maddox.rts.Property.value(getClass(), "cockpitClass");
        if(obj == null)
            return;
        java.lang.Class class1 = null;
        if(obj instanceof java.lang.Class)
        {
            if(i > 0)
                return;
            class1 = (java.lang.Class)obj;
        } else
        {
            java.lang.Class aclass[] = (java.lang.Class[])obj;
            if(i >= aclass.length)
                return;
            class1 = aclass[i];
        }
        if(!(com.maddox.il2.objects.air.CockpitPilot.class).isAssignableFrom(class1))
        {
            int j = com.maddox.rts.Property.intValue(class1, "weaponControlNum", 10);
            int l = com.maddox.rts.Property.intValue(class1, "aiTuretNum", 0);
            if(this == com.maddox.il2.ai.World.getPlayerAircraft())
            {
                com.maddox.il2.objects.air.CockpitGunner cockpitgunner = (com.maddox.il2.objects.air.CockpitGunner)com.maddox.il2.game.Main3D.cur3D().cockpits[i];
                cockpitgunner.setRealMode(!flag);
            } else
            {
                com.maddox.il2.fm.Turret turret = FM.turret[l];
                turret.bIsAIControlled = flag;
            }
            com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[j];
            if(abulletemitter != null)
            {
                boolean flag1 = !actor.net.isMaster() || com.maddox.il2.ai.World.cur().diffCur.Realistic_Gunnery;
                if(flag)
                    flag1 = true;
                for(int j1 = 0; j1 < abulletemitter.length; j1++)
                {
                    if(abulletemitter[j1] instanceof com.maddox.il2.engine.Actor)
                    {
                        ((com.maddox.il2.engine.Actor)abulletemitter[j1]).setOwner(((com.maddox.il2.engine.Actor) (flag ? ((com.maddox.il2.engine.Actor) (this)) : actor)));
                        if(abulletemitter[j1] instanceof com.maddox.il2.objects.weapons.Gun)
                            ((com.maddox.il2.objects.weapons.Gun)abulletemitter[j1]).initRealisticGunnery(flag1);
                    }
                    if(aword0 != null)
                    {
                        short word0 = aword0[j1];
                        if(word0 == 65535)
                            word0 = -1;
                        abulletemitter[j1]._loadBullets(word0);
                    } else
                    if(!com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo)
                        abulletemitter[j1].loadBullets(-1);
                }

            }
            if(actor instanceof com.maddox.il2.objects.air.NetGunner)
            {
                ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitTuretNum = flag ? -1 : l;
                ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitWeaponControlNum = j;
            } else
            {
                netCockpitTuretNum = flag ? -1 : l;
                netCockpitWeaponControlNum = j;
            }
        } else
        if(actor instanceof com.maddox.il2.objects.air.NetGunner)
            ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitTuretNum = -1;
        else
            netCockpitTuretNum = -1;
        int k = net.countMirrors();
        if(net.isMirror())
            k++;
        if(netchannel != null)
            k--;
        if(k > 0)
        {
            if(actor instanceof com.maddox.il2.objects.air.NetGunner)
                ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitValid = false;
            else
                netCockpitValid = false;
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new com.maddox.rts.NetMsgGuaranted() {

                    public void unLocking()
                    {
                        try
                        {
                            com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)((com.maddox.rts.NetObj)objects().get(0)).superObj();
                            if(actor1 instanceof com.maddox.il2.objects.air.NetGunner)
                            {
                                if(((com.maddox.il2.objects.air.NetGunner)actor1).netCockpitMsg == this)
                                    ((com.maddox.il2.objects.air.NetGunner)actor1).netCockpitValid = true;
                            } else
                            if(netCockpitMsg == this)
                                netCockpitValid = true;
                        }
                        catch(java.lang.Exception exception1)
                        {
                            com.maddox.il2.objects.air.NetAircraft.printDebug(exception1);
                        }
                    }

                }
;
                if(actor instanceof com.maddox.il2.objects.air.NetGunner)
                    ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitMsg = netmsgguaranted;
                else
                    netCockpitMsg = netmsgguaranted;
                netmsgguaranted.writeByte(13);
                netmsgguaranted.writeNetObj(actor.net);
                if(flag)
                    i |= 0x80;
                netmsgguaranted.writeByte(i);
                if(aword0 != null)
                {
                    for(int i1 = 0; i1 < aword0.length; i1++)
                        netmsgguaranted.writeShort(aword0[i1]);

                }
                net.postExclude(netchannel, netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
            }
        } else
        if(actor instanceof com.maddox.il2.objects.air.NetGunner)
            ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitValid = true;
        else
            netCockpitValid = true;
    }

    private boolean netGetCockpitAuto(com.maddox.rts.NetMsgInput netmsginput, boolean flag, boolean flag1)
        throws java.io.IOException
    {
        com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
        if(netobj == null)
            return false;
        com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)netobj.superObj();
        int i = netmsginput.readUnsignedByte();
        boolean flag2 = (i & 0x80) != 0;
        i &= 0xffffff7f;
        short aword0[] = null;
        int j = netmsginput.available() / 2;
        if(j > 0)
        {
            aword0 = new short[j];
            for(int k = 0; k < aword0.length; k++)
                aword0[k] = (short)netmsginput.readUnsignedShort();

        }
        netCockpitAuto(actor, i, flag2, aword0, netmsginput.channel());
        return true;
    }

    public void netCockpitEnter(com.maddox.il2.engine.Actor actor, int i)
    {
        netCockpitEnter(actor, i, true);
    }

    public void netCockpitEnter(com.maddox.il2.engine.Actor actor, int i, boolean flag)
    {
        if(flag)
            if(actor instanceof com.maddox.il2.objects.air.NetGunner)
            {
                com.maddox.il2.ai.EventLog.onOccupied((com.maddox.il2.objects.air.Aircraft)this, ((com.maddox.il2.objects.air.NetGunner)actor).getUser(), netCockpitAstatePilotIndx(i));
            } else
            {
                com.maddox.il2.ai.EventLog.onOccupied((com.maddox.il2.objects.air.Aircraft)this, ((com.maddox.il2.objects.air.Aircraft)actor).netUser(), netCockpitAstatePilotIndx(i));
                if(actor == com.maddox.il2.ai.World.getPlayerAircraft() && actor.isNetMaster() && i == 0 && !bWeaponsEventLog)
                {
                    bWeaponsEventLog = true;
                    com.maddox.il2.ai.EventLog.onWeaponsLoad(actor, thisWeaponsName, (int)((FM.M.fuel * 100F) / FM.M.maxFuel));
                }
            }
        netCockpitEnter(actor, i, ((com.maddox.rts.NetChannel) (null)));
    }

    private void netCockpitEnter(com.maddox.il2.engine.Actor actor, int i, com.maddox.rts.NetChannel netchannel)
    {
        int j = netCockpitIndxPilot;
        if(actor instanceof com.maddox.il2.objects.air.NetGunner)
            j = ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitIndxPilot;
        java.lang.Class class1 = null;
        java.lang.Class class2 = null;
        java.lang.Object obj = com.maddox.rts.Property.value(getClass(), "cockpitClass");
        if(obj == null)
            return;
        if(obj instanceof java.lang.Class)
        {
            if(j > 0)
                return;
            if(i > 0)
                return;
            class1 = class2 = (java.lang.Class)obj;
        } else
        {
            java.lang.Class aclass[] = (java.lang.Class[])obj;
            if(j >= aclass.length)
                return;
            if(i >= aclass.length)
                return;
            class1 = aclass[j];
            class2 = aclass[i];
        }
        if(!(com.maddox.il2.objects.air.CockpitPilot.class).isAssignableFrom(class1))
        {
            int k = com.maddox.rts.Property.intValue(class1, "aiTuretNum", 0);
            com.maddox.il2.fm.Turret turret = FM.turret[k];
            turret.bIsNetMirror = false;
        }
        if(!(com.maddox.il2.objects.air.CockpitPilot.class).isAssignableFrom(class2))
        {
            int l = com.maddox.rts.Property.intValue(class2, "aiTuretNum", 0);
            com.maddox.il2.fm.Turret turret1 = FM.turret[l];
            turret1.bIsNetMirror = actor.net.isMirror();
        }
        if(actor instanceof com.maddox.il2.objects.air.NetGunner)
            ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitIndxPilot = i;
        else
            netCockpitIndxPilot = i;
        netCockpitDriverSet(actor, i);
        int i1 = 0;
        int j1 = -1;
        if(!(com.maddox.il2.objects.air.CockpitPilot.class).isAssignableFrom(class2))
        {
            j1 = com.maddox.rts.Property.intValue(class2, "aiTuretNum", 0);
            com.maddox.il2.fm.Turret turret2 = FM.turret[j1];
            if(turret2.bIsAIControlled)
                j1 = -1;
            else
                i1 = com.maddox.rts.Property.intValue(class2, "weaponControlNum", 10);
        }
        if(actor instanceof com.maddox.il2.objects.air.NetGunner)
        {
            ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitTuretNum = j1;
            ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitWeaponControlNum = i1;
        } else
        {
            netCockpitTuretNum = j1;
            netCockpitWeaponControlNum = i1;
        }
        int k1 = net.countMirrors();
        if(net.isMirror())
            k1++;
        if(netchannel != null)
            k1--;
        if(k1 > 0)
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(12);
                netmsgguaranted.writeNetObj(actor.net);
                netmsgguaranted.writeByte(i);
                net.postExclude(netchannel, netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
            }
    }

    private boolean netGetCockpitEnter(com.maddox.rts.NetMsgInput netmsginput, boolean flag, boolean flag1)
        throws java.io.IOException
    {
        com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
        if(netobj == null)
        {
            return false;
        } else
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)netobj.superObj();
            int i = netmsginput.readUnsignedByte();
            netCockpitEnter(actor, i, netmsginput.channel());
            return true;
        }
    }

    protected void netCockpitFirstUpdate(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        int i = netCockpitIndxPilot;
        int j = netCockpitTuretNum;
        if(actor instanceof com.maddox.il2.objects.air.NetGunner)
        {
            i = ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitIndxPilot;
            j = ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitTuretNum;
        }
        if(i != 0)
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(12);
            netmsgguaranted.writeNetObj(actor.net);
            netmsgguaranted.writeByte(i);
            net.postTo(netchannel, netmsgguaranted);
        }
        if(j >= 0)
        {
            short aword0[] = null;
            java.lang.Object obj = com.maddox.rts.Property.value(getClass(), "cockpitClass");
            if(obj == null)
                return;
            java.lang.Class class1 = null;
            if(obj instanceof java.lang.Class)
            {
                if(i > 0)
                    return;
                class1 = (java.lang.Class)obj;
            } else
            {
                java.lang.Class aclass[] = (java.lang.Class[])obj;
                if(i >= aclass.length)
                    return;
                class1 = aclass[i];
            }
            int k = com.maddox.rts.Property.intValue(class1, "weaponControlNum", 10);
            if(com.maddox.il2.ai.World.cur().diffCur.Limited_Ammo)
            {
                com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[k];
                if(abulletemitter != null)
                {
                    aword0 = new short[abulletemitter.length];
                    for(int l = 0; l < abulletemitter.length; l++)
                    {
                        int j1 = abulletemitter[l].countBullets();
                        if(j1 < 0)
                            aword0[l] = -1;
                        else
                            aword0[l] = (short)j1;
                    }

                }
            }
            com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new com.maddox.rts.NetMsgGuaranted() {

                public void unLocking()
                {
                    com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)((com.maddox.rts.NetObj)objects().get(0)).superObj();
                    if(actor1 instanceof com.maddox.il2.objects.air.NetGunner)
                    {
                        if(((com.maddox.il2.objects.air.NetGunner)actor1).netCockpitMsg == this)
                            ((com.maddox.il2.objects.air.NetGunner)actor1).netCockpitValid = true;
                    } else
                    if(netCockpitMsg == this)
                        netCockpitValid = true;
                }

            }
;
            if(actor instanceof com.maddox.il2.objects.air.NetGunner)
            {
                if(!((com.maddox.il2.objects.air.NetGunner)actor).netCockpitValid)
                    ((com.maddox.il2.objects.air.NetGunner)actor).netCockpitMsg = netmsgguaranted1;
            } else
            if(!netCockpitValid)
                netCockpitMsg = netmsgguaranted1;
            netmsgguaranted1.writeByte(13);
            netmsgguaranted1.writeNetObj(actor.net);
            netmsgguaranted1.writeByte(i);
            if(aword0 != null)
            {
                for(int i1 = 0; i1 < aword0.length; i1++)
                    netmsgguaranted1.writeShort(aword0[i1]);

            }
            net.postTo(netchannel, netmsgguaranted1);
        }
    }

    private boolean netCockpitCheckDrivers()
    {
        if(netCockpitDrivers != null)
            return true;
        java.lang.Object obj = com.maddox.rts.Property.value(getClass(), "cockpitClass");
        if(obj == null)
            return false;
        if(obj instanceof java.lang.Class)
        {
            netCockpitDrivers = new com.maddox.il2.engine.Actor[1];
        } else
        {
            java.lang.Class aclass[] = (java.lang.Class[])obj;
            netCockpitDrivers = new com.maddox.il2.engine.Actor[aclass.length];
        }
        return true;
    }

    public com.maddox.il2.engine.Actor netCockpitGetDriver(int i)
    {
        if(!netCockpitCheckDrivers())
            return null;
        if(i < 0 || i >= netCockpitDrivers.length)
            return null;
        else
            return netCockpitDrivers[i];
    }

    private void netCockpitDriverSet(com.maddox.il2.engine.Actor actor, int i)
    {
        if(!netCockpitCheckDrivers())
            return;
        com.maddox.il2.net.NetUser netuser = netUser();
        if(actor instanceof com.maddox.il2.objects.air.NetGunner)
            netuser = ((com.maddox.il2.objects.air.NetGunner)actor).getUser();
        if(netuser == null)
            netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        for(int j = 0; j < netCockpitDrivers.length; j++)
            if(netCockpitDrivers[j] == actor)
            {
                netCockpitDrivers[j] = null;
                netuser.tryPreparePilotDefaultSkin((com.maddox.il2.objects.air.Aircraft)this, netCockpitAstatePilotIndx(j));
            }

        netCockpitDrivers[i] = actor;
        netuser.tryPreparePilotSkin(this, netCockpitAstatePilotIndx(i));
    }

    public void netCockpitDriverRequest(com.maddox.il2.engine.Actor actor, int i)
    {
        if(!netCockpitCheckDrivers())
            return;
        if(i < 0 || i >= netCockpitDrivers.length)
            return;
        if(net.isMaster())
        {
            if(netCockpitDrivers[i] != null)
                return;
            netCockpitDriverSet(actor, i);
            com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.netSwitchToCockpit(i);
        } else
        {
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(14);
                netmsgguaranted.writeNetObj(actor.net);
                netmsgguaranted.writeByte(i);
                net.postTo(net.masterChannel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
            }
        }
    }

    private boolean netGetCockpitDriver(com.maddox.rts.NetMsgInput netmsginput, boolean flag, boolean flag1)
        throws java.io.IOException
    {
        com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
        if(netobj == null)
            return false;
        com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)netobj.superObj();
        int i = netmsginput.readUnsignedByte();
        if(!netCockpitCheckDrivers())
            return false;
        if(i < 0 || i >= netCockpitDrivers.length)
            return true;
        if(flag)
        {
            if(netCockpitDrivers[i] != null)
                return true;
            netCockpitDriverSet(actor, i);
        } else
        if(flag1)
        {
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(14);
                netmsgguaranted.writeNetObj(actor.net);
                netmsgguaranted.writeByte(i);
                net.postTo(net.masterChannel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception);
            }
        } else
        {
            netCockpitDriverSet(actor, i);
            if(actor.net.isMaster())
                com.maddox.il2.game.Main3D.cur3D().aircraftHotKeys.netSwitchToCockpit(i);
        }
        if(net.countMirrors() > 0)
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
                netmsgguaranted1.writeByte(14);
                netmsgguaranted1.writeNetObj(actor.net);
                netmsgguaranted1.writeByte(i);
                net.post(netmsgguaranted1);
            }
            catch(java.lang.Exception exception1)
            {
                com.maddox.il2.objects.air.NetAircraft.printDebug(exception1);
            }
        return true;
    }

    boolean netGetGMsg(com.maddox.rts.NetMsgInput netmsginput, boolean flag)
        throws java.io.IOException
    {
        int i = netmsginput.readUnsignedByte();
        boolean flag1 = (i & 0x80) == 128;
        i &= 0xffffff7f;
        switch(i)
        {
        case 0: // '\0'
            FM.AS.netUpdate(flag, flag1, netmsginput);
            return true;

        case 1: // '\001'
            return netGetUpdateWayPoint(netmsginput, flag, flag1);

        case 2: // '\002'
            return netGetWeaponsBitStates(netmsginput, flag, flag1);

        case 3: // '\003'
            setGunPodsOn(true);
            return true;

        case 4: // '\004'
            setGunPodsOn(false);
            return true;

        case 5: // '\005'
            FM.CT.dropFuelTanks();
            return true;

        case 6: // '\006'
            return netGetHits(netmsginput, flag, flag1);

        case 7: // '\007'
            return netGetHitProp(netmsginput, flag, flag1);

        case 8: // '\b'
            return netGetCut(netmsginput, flag, flag1);

        case 9: // '\t'
            return netGetExplode(netmsginput, flag, flag1);

        case 10: // '\n'
            return netGetDead(netmsginput, flag, flag1);

        case 11: // '\013'
            return netGetFirstUpdate(netmsginput);

        case 12: // '\f'
            return netGetCockpitEnter(netmsginput, flag, flag1);

        case 13: // '\r'
            return netGetCockpitAuto(netmsginput, flag, flag1);

        case 14: // '\016'
            return netGetCockpitDriver(netmsginput, flag, flag1);
        }
        return false;
    }

    protected void sendMsgSndShot(com.maddox.il2.ai.Shot shot)
    {
        int i = shot.mass <= 0.05F ? 0 : 1;
        com.maddox.il2.engine.Actor._tmpPoint.set(pos.getAbsPoint());
        com.maddox.il2.engine.Actor._tmpPoint.sub(shot.p);
        int j = (int)(com.maddox.il2.engine.Actor._tmpPoint.x / 0.25D) & 0xfe;
        int k = (int)(com.maddox.il2.engine.Actor._tmpPoint.y / 0.25D) & 0xfe;
        i &= 3;
        try
        {
            com.maddox.rts.NetMsgFiltered netmsgfiltered = new NetMsgFiltered();
            netmsgfiltered.writeByte(j | i >> 1);
            netmsgfiltered.writeByte(k | i & 1);
            net.postTo(com.maddox.rts.Time.current(), net.masterChannel(), netmsgfiltered);
        }
        catch(java.lang.Exception exception) { }
    }

    protected void msgSndShot(float f, double d, double d1, double d2)
    {
    }

    public void makeMirrorCarrierRelPos()
    {
        if(net == null || net.isMaster())
        {
            return;
        } else
        {
            ((com.maddox.il2.objects.air.Mirror)net).makeFirstUnderDeck();
            return;
        }
    }

    public boolean isMirrorUnderDeck()
    {
        if(net == null || net.isMaster())
            return false;
        else
            return ((com.maddox.il2.objects.air.Mirror)net).bUnderDeck;
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        if(isNetMaster() && fmTrack != null && !fmTrack.isDestroyed())
            fmTrack.destroy();
        fmTrack = null;
        super.destroy();
        damagers.clear();
    }

    public void createNetObject(com.maddox.rts.NetChannel netchannel, int i)
    {
        if(netchannel == null)
            net = new Master(this);
        else
            net = new Mirror(this, netchannel, i);
    }

    public void restoreLinksInCoopWing()
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null || !com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            return;
        com.maddox.il2.ai.Wing wing = getWing();
        com.maddox.il2.objects.air.Aircraft aaircraft[] = wing.airc;
        int i;
        for(i = 0; i < aaircraft.length; i++)
            if(com.maddox.il2.engine.Actor.isValid(aaircraft[i]))
                break;

        if(i == aaircraft.length)
            return;
        aaircraft[i].FM.Leader = null;
        for(int j = i + 1; j < aaircraft.length; j++)
            if(com.maddox.il2.engine.Actor.isValid(aaircraft[j]))
            {
                aaircraft[i].FM.Wingman = aaircraft[j].FM;
                aaircraft[j].FM.Leader = aaircraft[i].FM;
                i = j;
            }

    }

    private static void netSpawnCommon(com.maddox.rts.NetMsgInput netmsginput, com.maddox.il2.engine.ActorSpawnArg actorspawnarg)
        throws java.lang.Exception
    {
        actorspawnarg.point = new Point3d(netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat());
        actorspawnarg.orient = new Orient(netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat());
        actorspawnarg.speed = new Vector3d(netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat());
    }

    private static void netSpawnCommon(com.maddox.rts.NetMsgInput netmsginput, com.maddox.il2.engine.ActorSpawnArg actorspawnarg, com.maddox.il2.objects.air.NetAircraft netaircraft)
        throws java.lang.Exception
    {
        com.maddox.il2.objects.air.Mirror mirror = (com.maddox.il2.objects.air.Mirror)netaircraft.net;
        mirror.netFirstUpdate((float)actorspawnarg.point.x, (float)actorspawnarg.point.y, (float)actorspawnarg.point.z, actorspawnarg.orient.azimut(), actorspawnarg.orient.tangage(), actorspawnarg.orient.kren(), (float)actorspawnarg.speed.x, (float)actorspawnarg.speed.y, (float)actorspawnarg.speed.z);
        int i = 0;
        for(int j = 0; j < 44; j++)
        {
            int k = 0;
            if((j & 1) == 0)
            {
                i = netmsginput.readUnsignedByte();
                k = i & 0xff;
            } else
            {
                k = i >> 8 & 0xff;
            }
            while(k-- > 0) 
            {
                com.maddox.il2.objects.air.NetAircraft _tmp = netaircraft;
                netaircraft.nextDMGLevel(com.maddox.il2.objects.air.NetAircraft.partNames()[j] + "_D0", 0, null);
            }
        }

        long l = netmsginput.readLong();
        if(l != netaircraft.FM.Operate)
        {
            int i1 = 0;
            for(long l1 = 1L; i1 < 44; l1 <<= 1)
            {
                if((l & l1) == 0L && (netaircraft.FM.Operate & l1) != 0L)
                {
                    com.maddox.il2.objects.air.NetAircraft _tmp1 = netaircraft;
                    netaircraft.nextCUTLevel(com.maddox.il2.objects.air.NetAircraft.partNames()[i1] + "_D0", 0, null);
                }
                i1++;
            }

        }
        byte byte0 = netmsginput.readByte();
        for(int j1 = 0; j1 < 4; j1++)
            if((byte0 & 1 << j1) != 0)
                netaircraft.hitProp(j1, 0, null);

        if((byte0 & 0x10) != 0)
            netaircraft.setGunPodsOn(false);
        byte abyte0[] = netaircraft.getWeaponsBitStatesBuf(null);
        if(abyte0 != null)
        {
            for(int k1 = 0; k1 < abyte0.length; k1++)
                abyte0[k1] = (byte)netmsginput.readUnsignedByte();

            netaircraft.setWeaponsBitStates(abyte0);
        }
        netaircraft.FM.AS.netFirstUpdate(netmsginput);
    }

    private void netReplicateCommon(com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeFloat(FM.M.fuel / FM.M.maxFuel);
        netmsgguaranted.writeBoolean(bPaintShemeNumberOn);
        netmsgguaranted.write255(((com.maddox.il2.objects.air.AircraftNet)net).netName);
        netmsgguaranted.write255(thisWeaponsName);
        netmsgguaranted.writeNetObj(((com.maddox.il2.objects.air.AircraftNet)net).netUser);
    }

    private void netReplicateFirstUpdate(com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
        netmsgguaranted.writeFloat((float)point3d.x);
        netmsgguaranted.writeFloat((float)point3d.y);
        netmsgguaranted.writeFloat((float)point3d.z);
        com.maddox.il2.engine.Orient orient = pos.getAbsOrient();
        netmsgguaranted.writeFloat(orient.azimut());
        netmsgguaranted.writeFloat(orient.tangage());
        netmsgguaranted.writeFloat(orient.kren());
        com.maddox.JGP.Vector3d vector3d = new Vector3d();
        getSpeed(vector3d);
        netmsgguaranted.writeFloat((float)vector3d.x);
        netmsgguaranted.writeFloat((float)vector3d.y);
        netmsgguaranted.writeFloat((float)vector3d.z);
        int i = 0;
        int j;
        for(j = 0; j < 44; j++)
            if((j & 1) == 0)
            {
                i = curDMGLevel(j) & 0xff;
            } else
            {
                i |= (curDMGLevel(j) & 0xff) << 8;
                netmsgguaranted.writeByte(i);
            }

        if((j & 1) == 1)
            netmsgguaranted.writeByte(i);
        netmsgguaranted.writeLong(FM.Operate);
        int k = curDMGProp(0) | curDMGProp(1) << 1 | curDMGProp(2) << 2 | curDMGProp(3) << 3;
        if(!isGunPodsOn())
            k |= 0x10;
        netmsgguaranted.writeByte(k);
        byte abyte0[] = getWeaponsBitStates(null);
        if(abyte0 != null)
            netmsgguaranted.write(abyte0);
        FM.AS.netReplicate(netmsgguaranted);
    }

    private com.maddox.rts.NetMsgSpawn netReplicateCoop(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        com.maddox.rts.NetMsgSpawn netmsgspawn = super.netReplicate(netchannel);
        netmsgspawn.writeByte(1);
        netReplicateCommon(netchannel, netmsgspawn);
        netReplicateFirstUpdate(netchannel, netmsgspawn);
        return netmsgspawn;
    }

    private com.maddox.rts.NetMsgSpawn _netReplicate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        com.maddox.rts.NetMsgSpawn netmsgspawn = super.netReplicate(netchannel);
        netmsgspawn.writeByte(0);
        netmsgspawn.writeByte(getArmy());
        netReplicateCommon(netchannel, netmsgspawn);
        netReplicateFirstUpdate(netchannel, netmsgspawn);
        return netmsgspawn;
    }

    public com.maddox.rts.NetMsgSpawn netReplicate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return null;
        if(netchannel.isMirrored(net))
            return null;
        com.maddox.rts.NetMsgSpawn netmsgspawn = null;
        if(isCoopPlane())
            netmsgspawn = netReplicateCoop(netchannel);
        else
            netmsgspawn = _netReplicate(netchannel);
        if(com.maddox.il2.ai.World.getPlayerAircraft() == this && (netchannel instanceof com.maddox.rts.NetChannelOutStream))
            if(fmTrack() == null)
            {
                if(isNetMaster())
                    new com.maddox.rts.MsgAction(true, this) {

                        public void doAction(java.lang.Object obj)
                        {
                            new FlightModelTrack((com.maddox.il2.objects.air.Aircraft)obj);
                        }

                    }
;
            } else
            {
                com.maddox.rts.MsgNet.postRealNewChannel(fmTrack(), netchannel);
            }
        return netmsgspawn;
    }

    public NetAircraft()
    {
        thisWeaponsName = null;
        bPaintShemeNumberOn = true;
        bGunPodsExist = false;
        bGunPodsOn = true;
        netCockpitIndxPilot = 0;
        netCockpitWeaponControlNum = 0;
        netCockpitTuretNum = -1;
        netCockpitValid = false;
        netCockpitMsg = null;
        bWeaponsEventLog = false;
        netCockpitDrivers = null;
        damagers = new ArrayList();
        damagerExclude = null;
        damager_ = null;
        bCoopPlane = loadingCoopPlane;
    }

    public void setDamagerExclude(com.maddox.il2.engine.Actor actor)
    {
        damagerExclude = actor;
        if(damager_ == actor)
            damager_ = null;
    }

    public void setDamager(com.maddox.il2.engine.Actor actor)
    {
        setDamager(actor, 1);
    }

    public void setDamager(com.maddox.il2.engine.Actor actor, int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor) || this == actor)
            return;
        if(i <= 0)
            return;
        if(i > 4)
            i = 4;
        damager_ = null;
        int j = damagers.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.objects.air.DamagerItem damageritem = (com.maddox.il2.objects.air.DamagerItem)damagers.get(k);
            if(damageritem.damager == actor)
            {
                damageritem.damage += i;
                damageritem.lastTime = com.maddox.rts.Time.current();
                return;
            }
        }

        damagers.add(new DamagerItem(actor, i));
        if(com.maddox.il2.ai.World.cur().isDebugFM())
        {
            com.maddox.il2.objects.air.Aircraft.debugprintln(this, "Printing Registered Damagers: *****");
            for(int l = 0; l < j; l++)
            {
                com.maddox.il2.objects.air.DamagerItem damageritem1 = (com.maddox.il2.objects.air.DamagerItem)damagers.get(l);
                if(com.maddox.il2.engine.Actor.isValid(damageritem1.damager))
                    com.maddox.il2.objects.air.Aircraft.debugprintln(damageritem1.damager, "inflicted " + damageritem1.damage + " puntos..");
            }

        }
    }

    public com.maddox.il2.engine.Actor getDamager()
    {
        if(com.maddox.il2.engine.Actor.isValid(damager_))
            return damager_;
        damager_ = null;
        long l = 0L;
        com.maddox.il2.engine.Actor actor = null;
        long l1 = 0L;
        com.maddox.il2.engine.Actor actor1 = null;
        com.maddox.il2.engine.Actor actor2 = null;
        int i = damagers.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.objects.air.DamagerItem damageritem = (com.maddox.il2.objects.air.DamagerItem)damagers.get(j);
            if(damageritem.damager != damagerExclude && com.maddox.il2.engine.Actor.isValid(damageritem.damager))
                if(damageritem.damager instanceof com.maddox.il2.objects.air.Aircraft)
                {
                    if(damageritem.lastTime > l1)
                    {
                        l1 = damageritem.lastTime;
                        actor1 = damageritem.damager;
                    }
                } else
                if(damageritem.damager == com.maddox.il2.engine.Engine.actorLand())
                    actor2 = damageritem.damager;
                else
                if(damageritem.lastTime > l)
                {
                    l = damageritem.lastTime;
                    actor = damageritem.damager;
                }
        }

        if(actor1 != null)
            damager_ = actor1;
        else
        if(actor != null)
            damager_ = actor;
        else
        if(actor2 != null)
            damager_ = actor2;
        return damager_;
    }

    public boolean isDamagerExclusive()
    {
        int i = 0;
        for(int j = 0; j < damagers.size(); j++)
            if(damagerExclude != damagers.get(j))
                i++;

        return i == 1;
    }

    protected static void printDebug(java.lang.Exception exception)
    {
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
    }

    public int getPilotsCount()
    {
        return FM.crew;
    }

    public static final int FM_AI = 0;
    public static final int FM_REAL = 1;
    public static final int FM_NET_CLIENT = 2;
    public static java.lang.String loadingCountry;
    public static boolean loadingCoopPlane;
    protected java.lang.String thisWeaponsName;
    protected boolean bPaintShemeNumberOn;
    private boolean bCoopPlane;
    private com.maddox.il2.fm.FlightModelTrack fmTrack;
    public static final int NETG_ID_TOMASTER = 128;
    public static final int NETG_ID_CODE_ASTATE = 0;
    public static final int NETG_ID_CODE_UPDATE_WAY = 1;
    public static final int NETG_ID_CODE_UPDATE_WEAPONS = 2;
    public static final int NETG_ID_CODE_GUNPODS_ON = 3;
    public static final int NETG_ID_CODE_GUNPODS_OFF = 4;
    public static final int NETG_ID_CODE_DROP_FUEL_TANKS = 5;
    public static final int NETG_ID_CODE_HIT = 6;
    public static final int NETG_ID_CODE_HIT_PROP = 7;
    public static final int NETG_ID_CODE_CUT = 8;
    public static final int NETG_ID_CODE_EXPLODE = 9;
    public static final int NETG_ID_CODE_DEAD = 10;
    public static final int NETG_ID_CODE_FIRST_UPDATE = 11;
    public static final int NETG_ID_CODE_COCKPIT_ENTER = 12;
    public static final int NETG_ID_CODE_COCKPIT_AUTO = 13;
    public static final int NETG_ID_CODE_COCKPIT_DRIVER = 14;
    protected boolean bGunPodsExist;
    protected boolean bGunPodsOn;
    private int netCockpitIndxPilot;
    private int netCockpitWeaponControlNum;
    private int netCockpitTuretNum;
    private boolean netCockpitValid;
    private com.maddox.rts.NetMsgGuaranted netCockpitMsg;
    private boolean bWeaponsEventLog;
    private com.maddox.il2.engine.Actor netCockpitDrivers[];
    private static com.maddox.JGP.Point3d corn = new Point3d();
    private static com.maddox.JGP.Point3d corn1 = new Point3d();
    private static com.maddox.JGP.Point3d pship = new Point3d();
    private static com.maddox.il2.engine.Loc lCorn = new Loc();
    static com.maddox.il2.objects.air.ClipFilter clipFilter = new ClipFilter();
    private java.util.ArrayList damagers;
    private com.maddox.il2.engine.Actor damagerExclude;
    private com.maddox.il2.engine.Actor damager_;



















}
