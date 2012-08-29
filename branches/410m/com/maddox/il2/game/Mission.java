// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Mission.java

package com.maddox.il2.game;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.AirportGround;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Target;
import com.maddox.il2.ai.TargetsGuard;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.ai.ground.ChiefGround;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Land2D;
import com.maddox.il2.engine.Land2DText;
import com.maddox.il2.engine.Land2Dn;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Wind;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIAirArming;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetFileServerNoseart;
import com.maddox.il2.net.NetFileServerPilot;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.il2.net.NetFilesTrack;
import com.maddox.il2.net.NetMissionListener;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.ActorViewPoint;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.bridges.LongBridge;
import com.maddox.il2.objects.buildings.HouseManager;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.effects.LightsGlare;
import com.maddox.il2.objects.effects.SpritesFog;
import com.maddox.il2.objects.effects.SunGlare;
import com.maddox.il2.objects.effects.Zip;
import com.maddox.il2.objects.humans.Soldier;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.Ship;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric;
import com.maddox.il2.objects.vehicles.artillery.RocketryGeneric;
import com.maddox.il2.objects.vehicles.radios.TypeHasBeacon;
import com.maddox.il2.objects.vehicles.radios.TypeHasHayRake;
import com.maddox.il2.objects.vehicles.radios.TypeHasLorenzBlindLanding;
import com.maddox.il2.objects.vehicles.radios.TypeHasMeacon;
import com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation;
import com.maddox.il2.objects.vehicles.stationary.Smoke;
import com.maddox.il2.objects.vehicles.stationary.SmokeGeneric;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CfgInt;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Destroy;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.IniFile;
import com.maddox.rts.Joy;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.LDRres;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgInvokeMethod_Object;
import com.maddox.rts.MsgNet;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelCallbackStream;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetChannelStream;
import com.maddox.rts.NetControl;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgDestroy;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.rts.net.NetFileServerDef;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.CmdMusic;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.SharedTokenizer;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.game:
//            Main3D, ZutiAircraft, GameTrack, Main, 
//            ZutiSupportMethods, ZutiRadarRefresh, DotRange

public class Mission
    implements com.maddox.rts.Destroy
{
    static class SPAWN
        implements com.maddox.rts.NetSpawn
    {

        public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            try
            {
                if(com.maddox.il2.game.Main.cur().mission != null)
                    com.maddox.il2.game.Main.cur().mission.destroy();
                com.maddox.il2.game.Mission mission = new Mission();
                if(com.maddox.il2.game.Mission.cur() != null)
                    com.maddox.il2.game.Mission.cur().destroy();
                mission.clear();
                com.maddox.il2.game.Main.cur().mission = mission;
                mission.createNetObject(netmsginput.channel(), i);
                com.maddox.il2.game.Main.cur().missionLoading = mission;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.game.Mission.printDebug(exception);
            }
        }

        SPAWN()
        {
        }
    }

    class Mirror extends com.maddox.il2.game.NetMissionObj
    {

        public Mirror(com.maddox.il2.game.Mission mission1, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(mission1, netchannel, i);
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(0);
                postTo(netchannel, netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.game.Mission _tmp = mission1;
                com.maddox.il2.game.Mission.printDebug(exception);
            }
        }
    }

    class Master extends com.maddox.il2.game.NetMissionObj
    {

        public Master(com.maddox.il2.game.Mission mission1)
        {
            super(mission1);
            mission1.sectFinger = mission1.sectFile.fingerExcludeSectPrefix("$$$");
        }
    }

    class NetMissionObj extends com.maddox.rts.NetObj
        implements com.maddox.rts.NetChannelCallbackStream
    {

        private void msgCallback(com.maddox.rts.NetChannel netchannel, int i)
        {
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(1);
                netmsgguaranted.writeByte(i);
                com.maddox.rts.NetMsgInput netmsginput = new NetMsgInput();
                netmsginput.setData(netchannel, true, netmsgguaranted.data(), 0, netmsgguaranted.size());
                com.maddox.rts.MsgNet.postReal(com.maddox.rts.Time.currentReal(), this, netmsginput);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.game.NetMissionObj _tmp = this;
                com.maddox.il2.game.NetMissionObj.printDebug(exception);
            }
        }

        public boolean netChannelCallback(com.maddox.rts.NetChannelOutStream netchanneloutstream, com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        {
            if(!(netmsgguaranted instanceof com.maddox.rts.NetMsgSpawn)) goto _L2; else goto _L1
_L1:
            msgCallback(netchanneloutstream, 0);
              goto _L3
_L2:
            if(!(netmsgguaranted instanceof com.maddox.rts.NetMsgDestroy)) goto _L4; else goto _L3
_L4:
            int i;
            com.maddox.rts.NetMsgInput netmsginput = new NetMsgInput();
            netmsginput.setData(netchanneloutstream, true, netmsgguaranted.data(), 0, netmsgguaranted.size());
            i = netmsginput.readUnsignedByte();
            i;
            JVM INSTR lookupswitch 4: default 139
        //                       0: 100
        //                       2: 109
        //                       4: 118
        //                       12: 127;
               goto _L5 _L6 _L7 _L8 _L9
_L5:
            break; /* Loop/switch isn't completed */
_L6:
            msgCallback(netchanneloutstream, 1);
            break; /* Loop/switch isn't completed */
_L7:
            msgCallback(netchanneloutstream, 3);
            break; /* Loop/switch isn't completed */
_L8:
            msgCallback(netchanneloutstream, 4);
            break; /* Loop/switch isn't completed */
_L9:
            com.maddox.il2.game.Main3D.cur3D().gameTrackRecord().startKeyRecord(netmsgguaranted);
            return false;
            java.lang.Exception exception;
            exception;
            this;
            com.maddox.il2.game.NetMissionObj.printDebug(exception);
_L3:
            return true;
        }

        public boolean netChannelCallback(com.maddox.rts.NetChannelInStream netchannelinstream, com.maddox.rts.NetMsgInput netmsginput)
        {
            try
            {
                int i = netmsginput.readUnsignedByte();
                if(i == 4)
                    netchannelinstream.setPause(true);
                else
                if(i == 12)
                {
                    netchannelinstream.setGameTime();
                    if(com.maddox.il2.game.Mission.isCoop() || com.maddox.il2.game.Mission.isDogfight())
                    {
                        com.maddox.il2.game.Main.cur().netServerParams.prepareHidenAircraft();
                        com.maddox.il2.game.Mission.doMissionStarting();
                        com.maddox.rts.Time.setPause(false);
                    }
                    com.maddox.il2.game.Main3D.cur3D().gameTrackPlay().startKeyPlay();
                }
                netmsginput.reset();
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.game.NetMissionObj _tmp = this;
                com.maddox.il2.game.NetMissionObj.printDebug(exception);
            }
            return true;
        }

        public void netChannelCallback(com.maddox.rts.NetChannelInStream netchannelinstream, com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        {
            if(!(netmsgguaranted instanceof com.maddox.rts.NetMsgSpawn) && !(netmsgguaranted instanceof com.maddox.rts.NetMsgDestroy))
                try
                {
                    com.maddox.rts.NetMsgInput netmsginput = new NetMsgInput();
                    netmsginput.setData(netchannelinstream, true, netmsgguaranted.data(), 0, netmsgguaranted.size());
                    int i = netmsginput.readUnsignedByte();
                    if(i == 4)
                        netchannelinstream.setPause(false);
                }
                catch(java.lang.Exception exception)
                {
                    com.maddox.il2.game.NetMissionObj _tmp = this;
                    com.maddox.il2.game.NetMissionObj.printDebug(exception);
                }
        }

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            com.maddox.il2.game.Mission mission = (com.maddox.il2.game.Mission)superObj();
            mission.netInput(netmsginput);
            return true;
        }

        public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
        {
            if(com.maddox.il2.game.Main.cur().missionLoading != null)
            {
                return;
            } else
            {
                tryReplicate(netchannel);
                return;
            }
        }

        private void tryReplicate(com.maddox.rts.NetChannel netchannel)
        {
            if(netchannel.isReady() && netchannel.isPublic() && netchannel != masterChannel && !netchannel.isMirrored(this) && netchannel.userState == 1)
                try
                {
                    postTo(netchannel, new NetMsgSpawn(this));
                }
                catch(java.lang.Exception exception)
                {
                    com.maddox.il2.game.NetMissionObj _tmp = this;
                    com.maddox.il2.game.NetMissionObj.printDebug(exception);
                }
        }

        public NetMissionObj(java.lang.Object obj)
        {
            super(obj);
        }

        public NetMissionObj(java.lang.Object obj, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(obj, netchannel, i);
        }
    }

    static class WingTakeoffPos
    {

        public boolean equals(java.lang.Object obj)
        {
            if(obj == null)
                return false;
            if(!(obj instanceof com.maddox.il2.game.WingTakeoffPos))
            {
                return false;
            } else
            {
                com.maddox.il2.game.WingTakeoffPos wingtakeoffpos = (com.maddox.il2.game.WingTakeoffPos)obj;
                return x == wingtakeoffpos.x && y == wingtakeoffpos.y;
            }
        }

        public int hashCode()
        {
            return x + y;
        }

        public int x;
        public int y;

        public WingTakeoffPos(double d, double d1)
        {
            x = (int)(d / 100D) * 100;
            y = (int)(d1 / 100D) * 100;
        }
    }

    class TimeOutWing
    {

        public void start()
        {
            if(isDestroyed())
                return;
            try
            {
                com.maddox.il2.objects.air.NetAircraft.loadingCoopPlane = false;
                com.maddox.il2.ai.Wing wing = new Wing();
                wing.load(sectFile, wingName, null);
                prepareSkinInWing(sectFile, wing);
                wing.setOnAirport();
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.game.Mission.printDebug(exception);
            }
        }

        java.lang.String wingName;

        public TimeOutWing(java.lang.String s)
        {
            wingName = s;
        }
    }

    public class BackgroundLoader extends com.maddox.rts.BackgroundTask
    {

        public void run()
            throws java.lang.Exception
        {
            _load(_name, _in, true);
        }

        private java.lang.String _name;
        private com.maddox.rts.SectFile _in;

        public BackgroundLoader(java.lang.String s, com.maddox.rts.SectFile sectfile)
        {
            _name = s;
            _in = sectfile;
        }
    }


    public Mission()
    {
        name = null;
        sectFinger = 0L;
        actors = new ArrayList();
        curActor = 0;
        bPlaying = false;
        curCloudsType = 0;
        curCloudsHeight = 1000F;
        curYear = 0;
        curMonth = 0;
        curDay = 0;
        curWindDirection = 0.0F;
        curWindVelocity = 0.0F;
        curGust = 0.0F;
        curTurbulence = 0.0F;
        bigShipHpDiv = 1.0F;
        _loadPlayer = false;
        playerNum = 0;
    }

    public static float respawnTime(java.lang.String s)
    {
        java.lang.Object obj = respawnMap.get(s);
        if(obj == null)
            return 1800F;
        else
            return ((java.lang.Float)obj).floatValue();
    }

    public static boolean isPlaying()
    {
        if(com.maddox.il2.game.Main.cur() == null)
            return false;
        if(com.maddox.il2.game.Main.cur().mission == null)
            return false;
        if(com.maddox.il2.game.Main.cur().mission.isDestroyed())
            return false;
        else
            return com.maddox.il2.game.Main.cur().mission.bPlaying;
    }

    public static boolean isSingle()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return false;
        if(com.maddox.il2.game.Main.cur().mission.isDestroyed())
            return false;
        if(com.maddox.il2.game.Main.cur().mission.net == null)
            return true;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return true;
        else
            return com.maddox.il2.game.Main.cur().netServerParams.isSingle();
    }

    public static boolean isNet()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return false;
        if(com.maddox.il2.game.Main.cur().mission.isDestroyed())
            return false;
        if(com.maddox.il2.game.Main.cur().mission.net == null)
            return false;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return false;
        else
            return !com.maddox.il2.game.Main.cur().netServerParams.isSingle();
    }

    public com.maddox.rts.NetChannel getNetMasterChannel()
    {
        if(net == null)
            return null;
        else
            return net.masterChannel();
    }

    public static boolean isServer()
    {
        return com.maddox.rts.NetEnv.isServer();
    }

    public static boolean isDeathmatch()
    {
        return com.maddox.il2.game.Mission.isDogfight();
    }

    public static boolean isDogfight()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return false;
        if(com.maddox.il2.game.Main.cur().mission.isDestroyed())
            return false;
        if(com.maddox.il2.game.Main.cur().mission.net == null)
            return false;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return false;
        else
            return com.maddox.il2.game.Main.cur().netServerParams.isDogfight();
    }

    public static boolean isCoop()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return false;
        if(com.maddox.il2.game.Main.cur().mission.isDestroyed())
            return false;
        if(com.maddox.il2.game.Main.cur().mission.net == null)
            return false;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return false;
        else
            return com.maddox.il2.game.Main.cur().netServerParams.isCoop();
    }

    public static int curCloudsType()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return 0;
        else
            return com.maddox.il2.game.Main.cur().mission.curCloudsType;
    }

    public static float curCloudsHeight()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return 1000F;
        else
            return com.maddox.il2.game.Main.cur().mission.curCloudsHeight;
    }

    public static int curYear()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return 0;
        else
            return com.maddox.il2.game.Main.cur().mission.curYear;
    }

    public static int curMonth()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return 0;
        else
            return com.maddox.il2.game.Main.cur().mission.curMonth;
    }

    public static int curDay()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return 0;
        else
            return com.maddox.il2.game.Main.cur().mission.curDay;
    }

    public static float curWindDirection()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return 0.0F;
        else
            return com.maddox.il2.game.Main.cur().mission.curWindDirection;
    }

    public static float curWindVelocity()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return 0.0F;
        else
            return com.maddox.il2.game.Main.cur().mission.curWindVelocity;
    }

    public static float curGust()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return 0.0F;
        else
            return com.maddox.il2.game.Main.cur().mission.curGust;
    }

    public static float curTurbulence()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return 0.0F;
        else
            return com.maddox.il2.game.Main.cur().mission.curTurbulence;
    }

    public static com.maddox.il2.game.Mission cur()
    {
        return com.maddox.il2.game.Main.cur().mission;
    }

    public static void BreakP()
    {
        java.lang.System.out.print("");
    }

    public static void load(java.lang.String s)
        throws java.lang.Exception
    {
        com.maddox.il2.game.Mission.load(s, false);
    }

    public static void load(java.lang.String s, boolean flag)
        throws java.lang.Exception
    {
        com.maddox.il2.game.Mission.load("missions/", s, flag);
    }

    public static void load(java.lang.String s, java.lang.String s1)
        throws java.lang.Exception
    {
        com.maddox.il2.game.Mission.load(s, s1, false);
    }

    public static void load(java.lang.String s, java.lang.String s1, boolean flag)
        throws java.lang.Exception
    {
        com.maddox.il2.game.Mission mission = new Mission();
        if(com.maddox.il2.game.Mission.cur() != null)
            com.maddox.il2.game.Mission.cur().destroy();
        else
            mission.clear();
        mission.sectFile = new SectFile(s + s1);
        mission.load(s1, mission.sectFile, flag);
    }

    public static void loadFromSect(com.maddox.rts.SectFile sectfile)
        throws java.lang.Exception
    {
        com.maddox.il2.game.Mission.loadFromSect(sectfile, false);
    }

    public static void loadFromSect(com.maddox.rts.SectFile sectfile, boolean flag)
        throws java.lang.Exception
    {
        com.maddox.il2.game.Mission mission = new Mission();
        java.lang.String s = sectfile.fileName();
        if(s != null && s.toLowerCase().startsWith("missions/"))
            s = s.substring("missions/".length());
        if(com.maddox.il2.game.Mission.cur() != null)
            com.maddox.il2.game.Mission.cur().destroy();
        else
            mission.clear();
        mission.sectFile = sectfile;
        mission.load(s, mission.sectFile, flag);
    }

    public java.lang.String name()
    {
        return name;
    }

    public com.maddox.rts.SectFile sectFile()
    {
        return sectFile;
    }

    public long finger()
    {
        return sectFinger;
    }

    public boolean isDestroyed()
    {
        return name == null;
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        if(bPlaying)
            doEnd();
        bPlaying = false;
        clear();
        name = null;
        com.maddox.il2.game.Main.cur().mission = null;
        if(com.maddox.il2.game.Main.cur().netMissionListener != null)
            com.maddox.il2.game.Main.cur().netMissionListener.netMissionState(8, 0.0F, null);
        if(net != null && !net.isDestroyed())
            net.destroy();
        net = null;
    }

    private void clear()
    {
        if(net != null)
        {
            doReplicateNotMissionActors(false);
            if(net.masterChannel() != null)
                doReplicateNotMissionActors(net.masterChannel(), false);
        }
        actors.clear();
        beaconsRed.clear();
        beaconsBlue.clear();
        meaconsRed.clear();
        meaconsBlue.clear();
        hayrakeMap.clear();
        curActor = 0;
        com.maddox.il2.game.Main.cur().resetGame();
        com.maddox.il2.game.ZutiSupportMethods.clear();
        com.maddox.il2.game.ZutiRadarRefresh.reset();
        if(com.maddox.il2.gui.GUI.pad != null)
            com.maddox.il2.gui.GUI.pad.zutiPadObjects.clear();
    }

    private void Mission()
    {
    }

    private void load(java.lang.String s, com.maddox.rts.SectFile sectfile, boolean flag)
        throws java.lang.Exception
    {
        if(flag)
            com.maddox.rts.BackgroundTask.execute(new BackgroundLoader(s, sectfile));
        else
            _load(s, sectfile, flag);
    }

    private void LOADING_STEP(float f, java.lang.String s)
    {
        if(net != null && com.maddox.il2.game.Main.cur().netMissionListener != null)
            com.maddox.il2.game.Main.cur().netMissionListener.netMissionState(3, f, s);
        if(!com.maddox.rts.BackgroundTask.step(f, s))
            throw new RuntimeException(com.maddox.rts.BackgroundTask.executed().messageCancel());
        else
            return;
    }

    private void _load(java.lang.String s, com.maddox.rts.SectFile sectfile, boolean flag)
        throws java.lang.Exception
    {
        if(com.maddox.il2.gui.GUI.pad != null)
            com.maddox.il2.gui.GUI.pad.zutiPadObjects.clear();
        zutiResetMissionVariables();
        com.maddox.sound.AudioDevice.soundsOff();
        if(s != null)
            java.lang.System.out.println("Loading mission " + s + "...");
        else
            java.lang.System.out.println("Loading mission ...");
        com.maddox.il2.ai.EventLog.checkState();
        com.maddox.il2.game.Main.cur().missionLoading = this;
        com.maddox.rts.RTSConf.cur.time.setEnableChangePause1(false);
        com.maddox.il2.engine.Actor.setSpawnFromMission(true);
        try
        {
            com.maddox.il2.game.Main.cur().mission = this;
            name = s;
            if(net == null)
                createNetObject(null, 0);
            loadMain(sectfile);
            loadRespawnTime(sectfile);
            com.maddox.il2.ai.Front.loadMission(sectfile);
            java.util.List list = null;
            if(com.maddox.il2.game.Main.cur().netServerParams.isCoop() || com.maddox.il2.game.Main.cur().netServerParams.isDogfight() || com.maddox.il2.game.Main.cur().netServerParams.isSingle())
            {
                try
                {
                    list = loadWings(sectfile);
                }
                catch(java.lang.Exception exception2)
                {
                    java.lang.System.out.println("Mission error, ID_04: " + exception2.toString());
                    exception2.printStackTrace();
                }
                try
                {
                    loadChiefs(sectfile);
                }
                catch(java.lang.Exception exception3)
                {
                    java.lang.System.out.println("Mission error, ID_05: " + exception3.toString());
                }
            }
            try
            {
                loadHouses(sectfile);
            }
            catch(java.lang.Exception exception4)
            {
                java.lang.System.out.println("Mission error, ID_06.1: " + exception4.toString());
            }
            try
            {
                loadNStationary(sectfile);
            }
            catch(java.lang.Exception exception5)
            {
                java.lang.System.out.println("Mission error, ID_06.2: " + exception5.toString());
            }
            try
            {
                loadStationary(sectfile);
            }
            catch(java.lang.Exception exception6)
            {
                java.lang.System.out.println("Mission error, ID_06.3: " + exception6.toString());
            }
            try
            {
                loadRocketry(sectfile);
            }
            catch(java.lang.Exception exception7)
            {
                java.lang.System.out.println("Mission error, ID_06.4: " + exception7.toString());
            }
            try
            {
                loadViewPoint(sectfile);
            }
            catch(java.lang.Exception exception8)
            {
                java.lang.System.out.println("Mission error, ID_06.5: " + exception8.toString());
            }
            try
            {
                if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
                    loadBornPlaces(sectfile);
            }
            catch(java.lang.Exception exception9)
            {
                java.lang.System.out.println("Mission error, ID_07: " + exception9.toString());
            }
            if(com.maddox.il2.game.Main.cur().netServerParams.isCoop() || com.maddox.il2.game.Main.cur().netServerParams.isSingle() || com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
                try
                {
                    loadTargets(sectfile);
                }
                catch(java.lang.Exception exception10)
                {
                    java.lang.System.out.println("Mission error, ID_08: " + exception10.toString());
                }
            try
            {
                populateBeacons();
            }
            catch(java.lang.Exception exception11)
            {
                java.lang.System.out.println("Mission error, ID_09: " + exception11.toString());
            }
            try
            {
                populateRunwayLights();
            }
            catch(java.lang.Exception exception12)
            {
                java.lang.System.out.println("Mission error, ID_10: " + exception12.toString());
            }
            if(list != null)
            {
                int i = list.size();
                for(int j = 0; j < i; j++)
                {
                    com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)list.get(j);
                    try
                    {
                        if(com.maddox.il2.engine.Actor.isValid(wing))
                            wing.setOnAirport();
                    }
                    catch(java.lang.Exception exception13) { }
                }

            }
        }
        catch(java.lang.Exception exception)
        {
            if(net != null && com.maddox.il2.game.Main.cur().netMissionListener != null)
                com.maddox.il2.game.Main.cur().netMissionListener.netMissionState(4, 0.0F, exception.getMessage());
            com.maddox.il2.game.Mission.printDebug(exception);
            clear();
            if(net != null && !net.isDestroyed())
                net.destroy();
            net = null;
            com.maddox.il2.game.Main.cur().mission = null;
            name = null;
            com.maddox.il2.engine.Actor.setSpawnFromMission(false);
            com.maddox.il2.game.Main.cur().missionLoading = null;
            setTime(false);
            throw exception;
        }
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.getPlayerAircraft()))
                com.maddox.il2.ai.World.land().cubeFullUpdate((float)com.maddox.il2.ai.World.getPlayerAircraft().pos.getAbsPoint().z);
            else
                com.maddox.il2.ai.World.land().cubeFullUpdate(1000F);
            com.maddox.il2.gui.GUI.pad.fillAirports();
        }
        com.maddox.il2.engine.Actor.setSpawnFromMission(false);
        com.maddox.il2.game.Main.cur().missionLoading = null;
        com.maddox.il2.game.Main.cur().missionCounter++;
        setTime(!com.maddox.il2.game.Main.cur().netServerParams.isSingle());
        LOADING_STEP(90F, "task.Load_humans");
        com.maddox.il2.objects.air.Paratrooper.PRELOAD();
        LOADING_STEP(95F, "task.Load_humans");
        if(com.maddox.il2.game.Main.cur().netServerParams.isSingle() || com.maddox.il2.game.Main.cur().netServerParams.isCoop() || com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
            com.maddox.il2.objects.humans.Soldier.PRELOAD();
        LOADING_STEP(100F, "");
        if(com.maddox.il2.game.Main.cur().netMissionListener != null)
            com.maddox.il2.game.Main.cur().netMissionListener.netMissionState(5, 0.0F, null);
        if(net.isMirror())
        {
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(4);
                net.masterChannel().userState = 4;
                net.postTo(net.masterChannel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception1)
            {
                com.maddox.il2.game.Mission.printDebug(exception1);
            }
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).missionLoaded();
        } else
        if(com.maddox.il2.game.Main.cur().netServerParams.isSingle())
        {
            if(com.maddox.il2.game.Main.cur() instanceof com.maddox.il2.game.Main3D)
                ((com.maddox.il2.game.Main3D)com.maddox.il2.game.Main.cur()).ordersTree.missionLoaded();
            com.maddox.il2.game.Main.cur().dotRangeFriendly.setDefault();
            com.maddox.il2.game.Main.cur().dotRangeFoe.setDefault();
            com.maddox.il2.game.Main.cur().dotRangeFoe.set(-1D, -1D, -1D, 5D, -1D, -1D);
        } else
        {
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).replicateDotRange();
        }
        com.maddox.rts.NetObj.tryReplicate(net, false);
        com.maddox.il2.ai.War.cur().missionLoaded();
        if(flag)
            com.maddox.il2.game.Main.cur().mission = this;
    }

    private void setTime(boolean flag)
    {
        com.maddox.rts.Time _tmp = com.maddox.rts.RTSConf.cur.time;
        com.maddox.rts.Time.setSpeed(1.0F);
        com.maddox.rts.Time _tmp1 = com.maddox.rts.RTSConf.cur.time;
        com.maddox.rts.Time.setSpeedReal(1.0F);
        if(flag)
        {
            com.maddox.rts.RTSConf.cur.time.setEnableChangePause1(false);
            com.maddox.rts.RTSConf.cur.time.setEnableChangeSpeed(false);
            com.maddox.rts.RTSConf.cur.time.setEnableChangeTickLen(true);
        } else
        {
            com.maddox.rts.RTSConf.cur.time.setEnableChangePause1(true);
            com.maddox.rts.RTSConf.cur.time.setEnableChangeSpeed(true);
            com.maddox.rts.RTSConf.cur.time.setEnableChangeTickLen(false);
        }
    }

    private void loadZutis(com.maddox.rts.SectFile sectfile)
    {
        try
        {
            com.maddox.il2.game.Main.cur().mission.zutiRadar_ShipsAsRadar = false;
            if(sectfile.get("MDS", "MDS_Radar_ShipsAsRadar", 0, 0, 1) == 1)
                com.maddox.il2.game.Main.cur().mission.zutiRadar_ShipsAsRadar = true;
            com.maddox.il2.game.Main.cur().mission.zutiRadar_ShipRadar_MaxRange = sectfile.get("MDS", "MDS_Radar_ShipRadar_MaxRange", 100, 1, 0x1869f);
            com.maddox.il2.game.Main.cur().mission.zutiRadar_ShipRadar_MinHeight = sectfile.get("MDS", "MDS_Radar_ShipRadar_MinHeight", 100, 0, 0x1869f);
            com.maddox.il2.game.Main.cur().mission.zutiRadar_ShipRadar_MaxHeight = sectfile.get("MDS", "MDS_Radar_ShipRadar_MaxHeight", 5000, 1000, 0x1869f);
            com.maddox.il2.game.Main.cur().mission.zutiRadar_ShipSmallRadar_MaxRange = sectfile.get("MDS", "MDS_Radar_ShipSmallRadar_MaxRange", 25, 1, 0x1869f);
            com.maddox.il2.game.Main.cur().mission.zutiRadar_ShipSmallRadar_MinHeight = sectfile.get("MDS", "MDS_Radar_ShipSmallRadar_MinHeight", 0, 0, 0x1869f);
            com.maddox.il2.game.Main.cur().mission.zutiRadar_ShipSmallRadar_MaxHeight = sectfile.get("MDS", "MDS_Radar_ShipSmallRadar_MaxHeight", 2000, 1000, 0x1869f);
            com.maddox.il2.game.Main.cur().mission.zutiRadar_ScoutsAsRadar = false;
            if(sectfile.get("MDS", "MDS_Radar_ScoutsAsRadar", 0, 0, 1) == 1)
                com.maddox.il2.game.Main.cur().mission.zutiRadar_ScoutsAsRadar = true;
            com.maddox.il2.game.Main.cur().mission.zutiRadar_ScoutRadar_MaxRange = sectfile.get("MDS", "MDS_Radar_ScoutRadar_MaxRange", 2, 1, 0x1869f);
            com.maddox.il2.game.Main.cur().mission.zutiRadar_ScoutRadar_DeltaHeight = sectfile.get("MDS", "MDS_Radar_ScoutRadar_DeltaHeight", 1500, 100, 0x1869f);
            com.maddox.il2.game.Main.cur().mission.zutiRadar_ScoutCompleteRecon = false;
            if(sectfile.get("MDS", "MDS_Radar_ScoutCompleteRecon", 0, 0, 1) == 1)
                com.maddox.il2.game.Main.cur().mission.zutiRadar_ScoutCompleteRecon = true;
            zutiLoadScouts_Red(sectfile);
            zutiLoadScouts_Blue(sectfile);
            com.maddox.il2.game.Main.cur().mission.zutiRadar_RefreshInterval = sectfile.get("MDS", "MDS_Radar_RefreshInterval", 0, 0, 0x1869f) * 1000;
            com.maddox.il2.game.Main.cur().mission.zutiRadar_DisableVectoring = false;
            if(sectfile.get("MDS", "MDS_Radar_DisableVectoring", 0, 0, 1) == 1)
                com.maddox.il2.game.Main.cur().mission.zutiRadar_DisableVectoring = true;
            com.maddox.il2.game.Main.cur().mission.zutiRadar_EnableTowerCommunications = true;
            if(sectfile.get("MDS", "MDS_Radar_EnableTowerCommunications", 1, 0, 1) == 0)
                com.maddox.il2.game.Main.cur().mission.zutiRadar_EnableTowerCommunications = false;
            ZUTI_RADAR_IN_ADV_MODE = false;
            if(sectfile.get("MDS", "MDS_Radar_SetRadarToAdvanceMode", 0, 0, 1) == 1)
                ZUTI_RADAR_IN_ADV_MODE = true;
            com.maddox.il2.game.Main.cur().mission.zutiRadar_HideUnpopulatedAirstripsFromMinimap = false;
            if(sectfile.get("MDS", "MDS_Radar_HideUnpopulatedAirstripsFromMinimap", 0, 0, 1) == 1)
                com.maddox.il2.game.Main.cur().mission.zutiRadar_HideUnpopulatedAirstripsFromMinimap = true;
            com.maddox.il2.game.Main.cur().mission.zutiRadar_ScoutGroundObjects_Alpha = sectfile.get("MDS", "MDS_Radar_ScoutGroundObjects_Alpha", 5, 1, 11);
            com.maddox.il2.game.Main.cur().mission.zutiMisc_DisableAIRadioChatter = false;
            if(sectfile.get("MDS", "MDS_Misc_DisableAIRadioChatter", 0, 0, 1) == 1)
                com.maddox.il2.game.Main.cur().mission.zutiMisc_DisableAIRadioChatter = true;
            com.maddox.il2.game.Main.cur().mission.zutiMisc_DespawnAIPlanesAfterLanding = true;
            if(sectfile.get("MDS", "MDS_Misc_DespawnAIPlanesAfterLanding", 1, 0, 1) == 0)
                com.maddox.il2.game.Main.cur().mission.zutiMisc_DespawnAIPlanesAfterLanding = false;
            com.maddox.il2.game.Main.cur().mission.zutiMisc_HidePlayersCountOnHomeBase = false;
            if(sectfile.get("MDS", "MDS_Misc_HidePlayersCountOnHomeBase", 0, 0, 1) == 1)
                com.maddox.il2.game.Main.cur().mission.zutiMisc_HidePlayersCountOnHomeBase = true;
            com.maddox.il2.game.Main.cur().mission.zutiMisc_BombsCat1_CratersVisibilityMultiplier = sectfile.get("MDS", "MDS_Misc_BombsCat1_CratersVisibilityMultiplier", 1.0F, 1.0F, 99999F);
            com.maddox.il2.game.Main.cur().mission.zutiMisc_BombsCat2_CratersVisibilityMultiplier = sectfile.get("MDS", "MDS_Misc_BombsCat2_CratersVisibilityMultiplier", 1.0F, 1.0F, 99999F);
            com.maddox.il2.game.Main.cur().mission.zutiMisc_BombsCat3_CratersVisibilityMultiplier = sectfile.get("MDS", "MDS_Misc_BombsCat3_CratersVisibilityMultiplier", 1.0F, 1.0F, 99999F);
            zutiSetShipRadars();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Mission error, ID_11: " + exception.toString());
        }
    }

    private void loadMain(com.maddox.rts.SectFile sectfile)
        throws java.lang.Exception
    {
        int i = sectfile.get("MAIN", "TIMECONSTANT", 0, 0, 1);
        com.maddox.il2.ai.World.cur().setTimeOfDayConstant(i == 1);
        com.maddox.il2.ai.World.setTimeofDay(sectfile.get("MAIN", "TIME", 12F, 0.0F, 23.99F));
        loadZutis(sectfile);
        int j = sectfile.get("MAIN", "WEAPONSCONSTANT", 0, 0, 1);
        com.maddox.il2.ai.World.cur().setWeaponsConstant(j == 1);
        bigShipHpDiv = 1.0F / sectfile.get("MAIN", "ShipHP", 1.0F, 0.001F, 100F);
        java.lang.String s = sectfile.get("MAIN", "MAP");
        if(s == null)
            throw new Exception("No MAP in mission file ");
        java.lang.String s1 = null;
        int ai[] = null;
        com.maddox.rts.SectFile sectfile1 = new SectFile("maps/" + s);
        int k = sectfile1.sectionIndex("static");
        if(k >= 0 && sectfile1.vars(k) > 0)
        {
            s1 = sectfile1.var(k, 0);
            if(s1 == null || s1.length() <= 0)
            {
                s1 = null;
            } else
            {
                s1 = com.maddox.rts.HomePath.concatNames("maps/" + s, s1);
                ai = com.maddox.il2.objects.Statics.readBridgesEndPoints(s1);
            }
        }
        LOADING_STEP(0.0F, "task.Load_landscape");
        int l = sectfile.get("SEASON", "Year", 1940, 1930, 1960);
        int i1 = sectfile.get("SEASON", "Month", com.maddox.il2.ai.World.land().config.getDefaultMonth("maps/" + s), 1, 12);
        int j1 = sectfile.get("SEASON", "Day", 15, 1, 31);
        com.maddox.il2.game.Mission.setDate(l, i1, j1);
        com.maddox.il2.ai.World.land().LoadMap(s, ai, i1, j1);
        com.maddox.il2.ai.World.cur().setCamouflage(com.maddox.il2.ai.World.land().config.camouflage);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().land2D != null)
            {
                if(!com.maddox.il2.game.Main3D.cur3D().land2D.isDestroyed())
                    com.maddox.il2.game.Main3D.cur3D().land2D.destroy();
                com.maddox.il2.game.Main3D.cur3D().land2D = null;
            }
            Object obj = null;
            int k1 = sectfile1.sectionIndex("MAP2D");
            if(k1 >= 0)
            {
                int i2 = sectfile1.vars(k1);
                if(i2 > 0)
                {
                    LOADING_STEP(20F, "task.Load_map");
                    com.maddox.il2.game.Main3D.cur3D().land2D = new Land2Dn(s, com.maddox.il2.ai.World.land().getSizeX(), com.maddox.il2.ai.World.land().getSizeY());
                }
            }
            if(com.maddox.il2.game.Main3D.cur3D().land2DText == null)
                com.maddox.il2.game.Main3D.cur3D().land2DText = new Land2DText();
            else
                com.maddox.il2.game.Main3D.cur3D().land2DText.clear();
            int j2 = sectfile1.sectionIndex("text");
            if(j2 >= 0 && sectfile1.vars(j2) > 0)
            {
                LOADING_STEP(22F, "task.Load_landscape_texts");
                java.lang.String s2 = sectfile1.var(j2, 0);
                com.maddox.il2.game.Main3D.cur3D().land2DText.load(com.maddox.rts.HomePath.concatNames("maps/" + s, s2));
            }
        }
        if(s1 != null)
        {
            LOADING_STEP(23F, "task.Load_static_objects");
            com.maddox.il2.objects.Statics.load(s1, com.maddox.il2.ai.World.cur().statics.bridges);
            com.maddox.il2.engine.Engine.drawEnv().staticTrimToSize();
        }
        com.maddox.il2.objects.Statics.trim();
        if(com.maddox.il2.game.Main.cur().netServerParams.isSingle() || com.maddox.il2.game.Main.cur().netServerParams.isCoop() || com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
            try
            {
                com.maddox.il2.ai.World.cur().statics.loadStateBridges(sectfile, false);
                com.maddox.il2.ai.World.cur().statics.loadStateHouses(sectfile, false);
                com.maddox.il2.ai.World.cur().statics.loadStateBridges(sectfile, true);
                com.maddox.il2.ai.World.cur().statics.loadStateHouses(sectfile, true);
                checkBridgesAndHouses(sectfile);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println("Mission error, ID_12: " + exception.toString());
            }
        if(com.maddox.il2.game.Main.cur().netServerParams.isSingle())
        {
            player = sectfile.get("MAIN", "player");
            playerNum = sectfile.get("MAIN", "playerNum", 0, 0, 3);
        } else
        {
            player = null;
        }
        com.maddox.il2.ai.World.setMissionArmy(sectfile.get("MAIN", "army", 1, 1, 2));
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            com.maddox.il2.game.Main3D.cur3D().ordersTree.setFrequency(new Boolean(true));
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            LOADING_STEP(29F, "task.Load_landscape_effects");
            com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
            int l1 = sectfile.get("MAIN", "CloudType", 0, 0, 6);
            float f = sectfile.get("MAIN", "CloudHeight", 1000F, 300F, 5000F);
            com.maddox.il2.game.Mission.createClouds(l1, f);
            if(!com.maddox.il2.engine.Config.cur.ini.get("game", "NoLensFlare", false))
            {
                main3d.sunFlareCreate();
                main3d.sunFlareShow(true);
            } else
            {
                main3d.sunFlareShow(false);
            }
            float f1 = (float)(s.charAt(0) - 64) * 3.141593F;
            f1 = sectfile.get("WEATHER", "WindDirection", f1, 0.0F, 359.99F);
            float f2 = 0.25F + (float)(l1 * l1) * 0.12F;
            f2 = sectfile.get("WEATHER", "WindSpeed", f2, 0.0F, 15F);
            float f3 = l1 > 3 ? (float)l1 * 2.0F : 0.0F;
            f3 = sectfile.get("WEATHER", "Gust", f3, 0.0F, 12F);
            float f4 = l1 > 2 ? l1 : 0.0F;
            f4 = sectfile.get("WEATHER", "Turbulence", f4, 0.0F, 6F);
            com.maddox.il2.ai.World.wind().set(f, f1, f2, f3, f4);
            for(int k2 = 0; k2 < 3; k2++)
            {
                com.maddox.il2.game.Main3D.cur3D()._lightsGlare[k2].setShow(true);
                com.maddox.il2.game.Main3D.cur3D()._sunGlare[k2].setShow(true);
            }

        }
    }

    public static void setDate(int i, int j, int k)
    {
        com.maddox.il2.game.Mission.setYear(i);
        com.maddox.il2.game.Mission.setMonth(j);
        com.maddox.il2.game.Mission.setDay(k);
    }

    public static void setYear(int i)
    {
        if(i < 1930)
            i = 1930;
        if(i > 1960)
            i = 1960;
        if(com.maddox.il2.game.Mission.cur() != null)
            com.maddox.il2.game.Mission.cur().curYear = i;
    }

    public static void setMonth(int i)
    {
        if(i < 1)
            i = 1;
        if(i > 12)
            i = 12;
        if(com.maddox.il2.game.Mission.cur() != null)
            com.maddox.il2.game.Mission.cur().curMonth = i;
    }

    public static void setDay(int i)
    {
        if(i < 1)
            i = 1;
        if(i > 31)
            i = 31;
        if(com.maddox.il2.game.Mission.cur() != null)
            com.maddox.il2.game.Mission.cur().curDay = i;
    }

    public static void setWindDirection(float f)
    {
        if(f < 0.0F)
            f = 0.0F;
        if(f > 359.99F)
            f = 0.0F;
        if(com.maddox.il2.game.Mission.cur() != null)
            com.maddox.il2.game.Mission.cur().curWindDirection = f;
    }

    public static void setWindVelocity(float f)
    {
        if(f < 0.0F)
            f = 0.0F;
        if(f > 15F)
            f = 15F;
        if(com.maddox.il2.game.Mission.cur() != null)
            com.maddox.il2.game.Mission.cur().curWindVelocity = f;
    }

    public static void setGust(float f)
    {
        if(f < 0.0F)
            f = 0.0F;
        if(f > 12F)
            f = 12F;
        if(com.maddox.il2.game.Mission.cur() != null)
            com.maddox.il2.game.Mission.cur().curGust = f;
    }

    public static void setTurbulence(float f)
    {
        if(f < 0.0F)
            f = 0.0F;
        if(f > 6F)
            f = 6F;
        if(com.maddox.il2.game.Mission.cur() != null)
            com.maddox.il2.game.Mission.cur().curTurbulence = f;
    }

    public static void createClouds(int i, float f)
    {
        if(i < 0)
            i = 0;
        if(i > 6)
            i = 6;
        if(com.maddox.il2.game.Mission.cur() != null)
        {
            com.maddox.il2.game.Mission.cur().curCloudsType = i;
            com.maddox.il2.game.Mission.cur().curCloudsHeight = f;
        }
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
        com.maddox.il2.engine.Camera3D camera3d = (com.maddox.il2.engine.Camera3D)com.maddox.il2.engine.Actor.getByName("camera");
        if(main3d.clouds != null)
            main3d.clouds.destroy();
        main3d.clouds = new EffClouds(com.maddox.il2.ai.World.cur().diffCur.NewCloudsRender, i, f);
        if(i > 5)
            try
            {
                if(main3d.zip != null)
                    main3d.zip.destroy();
                main3d.zip = new Zip(f);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println("Zip load error: " + exception);
            }
        int j = 5 - i;
        if(i == 6)
            j = 1;
        if(j > 4)
            j = 4;
        com.maddox.il2.engine.RenderContext.cfgLandFogHaze.set(j);
        com.maddox.il2.engine.RenderContext.cfgLandFogHaze.apply();
        com.maddox.il2.engine.RenderContext.cfgLandFogHaze.reset();
        com.maddox.il2.engine.RenderContext.cfgLandFogLow.set(0);
        com.maddox.il2.engine.RenderContext.cfgLandFogLow.apply();
        com.maddox.il2.engine.RenderContext.cfgLandFogLow.reset();
        if(com.maddox.il2.engine.Actor.isValid(main3d.spritesFog))
            main3d.spritesFog.destroy();
        main3d.spritesFog = new SpritesFog(camera3d, 0.7F, 7000F, 7500F);
    }

    public static void setCloudsType(int i)
    {
        if(i < 0)
            i = 0;
        if(i > 6)
            i = 6;
        if(com.maddox.il2.game.Mission.cur() != null)
            com.maddox.il2.game.Mission.cur().curCloudsType = i;
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
        if(main3d.clouds != null)
            main3d.clouds.setType(i);
        int j = 5 - i;
        if(i == 6)
            j = 1;
        if(j > 4)
            j = 4;
        com.maddox.il2.engine.RenderContext.cfgLandFogHaze.set(j);
        com.maddox.il2.engine.RenderContext.cfgLandFogHaze.apply();
        com.maddox.il2.engine.RenderContext.cfgLandFogHaze.reset();
        com.maddox.il2.engine.RenderContext.cfgLandFogLow.set(0);
        com.maddox.il2.engine.RenderContext.cfgLandFogLow.apply();
        com.maddox.il2.engine.RenderContext.cfgLandFogLow.reset();
    }

    public static void setCloudsHeight(float f)
    {
        if(com.maddox.il2.game.Mission.cur() != null)
            com.maddox.il2.game.Mission.cur().curCloudsHeight = f;
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        com.maddox.il2.game.Main3D main3d = com.maddox.il2.game.Main3D.cur3D();
        if(main3d.clouds != null)
            main3d.clouds.setHeight(f);
    }

    private void loadRespawnTime(com.maddox.rts.SectFile sectfile)
    {
        respawnMap.clear();
        int i = sectfile.sectionIndex("RespawnTime");
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        for(int k = 0; k < j; k++)
        {
            java.lang.String s = sectfile.var(i, k);
            float f = sectfile.get("RespawnTime", s, 1800F, 20F, 1000000F);
            respawnMap.put(s, new Float(f));
        }

    }

    private java.util.List loadWings(com.maddox.rts.SectFile sectfile)
        throws java.lang.Exception
    {
        int i = sectfile.sectionIndex("Wing");
        if(i < 0)
            return null;
        if(!com.maddox.il2.ai.World.cur().diffCur.Takeoff_N_Landing)
            prepareTakeoff(sectfile, !com.maddox.il2.game.Main.cur().netServerParams.isSingle());
        com.maddox.rts.NetChannel netchannel = null;
        if(!com.maddox.il2.game.Mission.isServer())
            netchannel = net.masterChannel();
        int j = sectfile.vars(i);
        java.util.ArrayList arraylist = null;
        if(j > 0)
            arraylist = new ArrayList(j);
        for(int k = 0; k < j; k++)
        {
            LOADING_STEP(30 + java.lang.Math.round(((float)k / (float)j) * 30F), "task.Load_aircraft");
            java.lang.String s = sectfile.var(i, k);
            _loadPlayer = s.equals(player);
            int l = sectfile.get(s, "StartTime", 0);
            if(l > 0 && !_loadPlayer)
            {
                if(netchannel == null)
                {
                    double d = (long)l * 60L;
                    new com.maddox.rts.MsgAction(0, d, new TimeOutWing(s)) {

                        public void doAction(java.lang.Object obj)
                        {
                            com.maddox.il2.game.TimeOutWing timeoutwing = (com.maddox.il2.game.TimeOutWing)obj;
                            timeoutwing.start();
                        }

                    }
;
                }
                continue;
            }
            com.maddox.il2.objects.air.NetAircraft.loadingCoopPlane = com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isCoop();
            com.maddox.il2.ai.Wing wing = new Wing();
            wing.load(sectfile, s, netchannel);
            if(netchannel != null && !com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            {
                com.maddox.il2.objects.air.Aircraft aaircraft[] = wing.airc;
                for(int i1 = 0; i1 < aaircraft.length; i1++)
                    if(com.maddox.il2.engine.Actor.isValid(aaircraft[i1]) && aaircraft[i1].net == null)
                    {
                        aaircraft[i1].destroy();
                        aaircraft[i1] = null;
                    }

            }
            arraylist.add(wing);
            prepareSkinInWing(sectfile, wing);
        }

        LOADING_STEP(60F, "task.Load_aircraft");
        return arraylist;
    }

    private void prepareSkinInWing(com.maddox.rts.SectFile sectfile, com.maddox.il2.ai.Wing wing)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        com.maddox.il2.objects.air.Aircraft aaircraft[] = wing.airc;
        for(int i = 0; i < aaircraft.length; i++)
            if(com.maddox.il2.engine.Actor.isValid(aaircraft[i]))
            {
                com.maddox.il2.objects.air.Aircraft aircraft = aaircraft[i];
                prepareSkinInWing(sectfile, aircraft, wing.name(), i);
            }

    }

    private void prepareSkinInWing(com.maddox.rts.SectFile sectfile, com.maddox.il2.objects.air.Aircraft aircraft, java.lang.String s, int i)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(com.maddox.il2.ai.World.getPlayerAircraft() == aircraft)
        {
            if(com.maddox.il2.game.Mission.isSingle())
                if(com.maddox.il2.net.NetMissionTrack.isPlaying())
                {
                    ((com.maddox.il2.net.NetUser)com.maddox.il2.game.Main.cur().netServerParams.host()).tryPrepareSkin(aircraft);
                    ((com.maddox.il2.net.NetUser)com.maddox.il2.game.Main.cur().netServerParams.host()).tryPrepareNoseart(aircraft);
                    ((com.maddox.il2.net.NetUser)com.maddox.il2.game.Main.cur().netServerParams.host()).tryPreparePilot(aircraft);
                } else
                {
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(aircraft.getClass(), "keyName", null);
                    java.lang.String s3 = com.maddox.il2.ai.World.cur().userCfg.getSkin(s1);
                    if(s3 != null)
                    {
                        java.lang.String s6 = com.maddox.il2.gui.GUIAirArming.validateFileName(s1);
                        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setSkin(s6 + "/" + s3);
                        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).tryPrepareSkin(aircraft);
                    } else
                    {
                        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setSkin(null);
                    }
                    java.lang.String s7 = com.maddox.il2.ai.World.cur().userCfg.getNoseart(s1);
                    if(s7 != null)
                    {
                        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setNoseart(s7);
                        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).tryPrepareNoseart(aircraft);
                    } else
                    {
                        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setNoseart(null);
                    }
                    java.lang.String s11 = com.maddox.il2.ai.World.cur().userCfg.netPilot;
                    ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setPilot(s11);
                    if(s11 != null)
                        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).tryPreparePilot(aircraft);
                }
        } else
        {
            java.lang.String s2 = sectfile.get(s, "skin" + i, (java.lang.String)null);
            if(s2 != null)
            {
                java.lang.String s4 = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(aircraft.getClass(), aircraft.getRegiment().country());
                s2 = com.maddox.il2.gui.GUIAirArming.validateFileName(com.maddox.rts.Property.stringValue(aircraft.getClass(), "keyName", null)) + "/" + s2;
                if(com.maddox.il2.net.NetMissionTrack.isPlaying())
                {
                    s2 = com.maddox.il2.net.NetFilesTrack.getLocalFileName(com.maddox.il2.game.Main.cur().netFileServerSkin, com.maddox.il2.game.Main.cur().netServerParams.host(), s2);
                    if(s2 != null)
                        s2 = com.maddox.il2.game.Main.cur().netFileServerSkin.alternativePath() + "/" + s2;
                } else
                {
                    s2 = com.maddox.il2.game.Main.cur().netFileServerSkin.primaryPath() + "/" + s2;
                }
                if(s2 != null)
                {
                    java.lang.String s8 = "PaintSchemes/Cache/" + com.maddox.rts.Finger.file(0L, s2, -1);
                    com.maddox.il2.objects.air.Aircraft.prepareMeshSkin(s4, aircraft.hierMesh(), s2, s8);
                }
            }
            java.lang.String s5 = sectfile.get(s, "noseart" + i, (java.lang.String)null);
            if(s5 != null)
            {
                java.lang.String s9 = com.maddox.il2.game.Main.cur().netFileServerNoseart.primaryPath() + "/" + s5;
                java.lang.String s12 = s5.substring(0, s5.length() - 4);
                if(com.maddox.il2.net.NetMissionTrack.isPlaying())
                {
                    s9 = com.maddox.il2.net.NetFilesTrack.getLocalFileName(com.maddox.il2.game.Main.cur().netFileServerNoseart, com.maddox.il2.game.Main.cur().netServerParams.host(), s5);
                    if(s9 != null)
                    {
                        s12 = s9.substring(0, s9.length() - 4);
                        s9 = com.maddox.il2.game.Main.cur().netFileServerNoseart.alternativePath() + "/" + s9;
                    }
                }
                if(s9 != null)
                {
                    java.lang.String s14 = "PaintSchemes/Cache/Noseart0" + s12 + ".tga";
                    java.lang.String s16 = "PaintSchemes/Cache/Noseart0" + s12 + ".mat";
                    java.lang.String s18 = "PaintSchemes/Cache/Noseart1" + s12 + ".tga";
                    java.lang.String s20 = "PaintSchemes/Cache/Noseart1" + s12 + ".mat";
                    if(com.maddox.il2.engine.BmpUtils.bmp8PalTo2TGA4(s9, s14, s18))
                        com.maddox.il2.objects.air.Aircraft.prepareMeshNoseart(aircraft.hierMesh(), s16, s20, s14, s18, null);
                }
            }
            java.lang.String s10 = sectfile.get(s, "pilot" + i, (java.lang.String)null);
            if(s10 != null)
            {
                java.lang.String s13 = com.maddox.il2.game.Main.cur().netFileServerPilot.primaryPath() + "/" + s10;
                java.lang.String s15 = s10.substring(0, s10.length() - 4);
                if(com.maddox.il2.net.NetMissionTrack.isPlaying())
                {
                    s13 = com.maddox.il2.net.NetFilesTrack.getLocalFileName(com.maddox.il2.game.Main.cur().netFileServerPilot, com.maddox.il2.game.Main.cur().netServerParams.host(), s10);
                    if(s13 != null)
                    {
                        s15 = s13.substring(0, s13.length() - 4);
                        s13 = com.maddox.il2.game.Main.cur().netFileServerPilot.alternativePath() + "/" + s13;
                    }
                }
                if(s13 != null)
                {
                    java.lang.String s17 = "PaintSchemes/Cache/Pilot" + s15 + ".tga";
                    java.lang.String s19 = "PaintSchemes/Cache/Pilot" + s15 + ".mat";
                    if(com.maddox.il2.engine.BmpUtils.bmp8PalToTGA3(s13, s17))
                        com.maddox.il2.objects.air.Aircraft.prepareMeshPilot(aircraft.hierMesh(), 0, s19, s17, null);
                }
            }
        }
    }

    public void prepareSkinAI(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        java.lang.String s = aircraft.name();
        if(s.length() < 4)
            return;
        java.lang.String s1 = s.substring(0, s.length() - 1);
        int i = 0;
        try
        {
            i = java.lang.Integer.parseInt(s.substring(s.length() - 1, s.length()));
        }
        catch(java.lang.Exception exception)
        {
            return;
        }
        prepareSkinInWing(sectFile, aircraft, s1, i);
    }

    public void recordNetFiles()
    {
        if(com.maddox.il2.game.Mission.isDogfight())
            return;
        int i = sectFile.sectionIndex("Wing");
        if(i < 0)
            return;
        int j = sectFile.vars(i);
label0:
        for(int k = 0; k < j; k++)
            try
            {
                java.lang.String s = sectFile.var(i, k);
                java.lang.String s1 = sectFile.get(s, "Class", (java.lang.String)null);
                java.lang.Class class1 = com.maddox.rts.ObjIO.classForName(s1);
                java.lang.String s2 = com.maddox.il2.gui.GUIAirArming.validateFileName(com.maddox.rts.Property.stringValue(class1, "keyName", null));
                int l = 0;
                do
                {
                    if(l >= 4)
                        continue label0;
                    java.lang.String s3 = sectFile.get(s, "skin" + l, (java.lang.String)null);
                    if(s3 != null)
                        recordNetFile(com.maddox.il2.game.Main.cur().netFileServerSkin, s2 + "/" + s3);
                    recordNetFile(com.maddox.il2.game.Main.cur().netFileServerNoseart, sectFile.get(s, "noseart" + l, (java.lang.String)null));
                    recordNetFile(com.maddox.il2.game.Main.cur().netFileServerPilot, sectFile.get(s, "pilot" + l, (java.lang.String)null));
                    l++;
                } while(true);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.game.Mission.printDebug(exception);
            }

    }

    private void recordNetFile(com.maddox.rts.net.NetFileServerDef netfileserverdef, java.lang.String s)
    {
        if(s == null)
            return;
        java.lang.String s1 = s;
        if(com.maddox.il2.net.NetMissionTrack.isPlaying())
        {
            s1 = com.maddox.il2.net.NetFilesTrack.getLocalFileName(netfileserverdef, com.maddox.il2.game.Main.cur().netServerParams.host(), s);
            if(s1 == null)
                return;
        }
        com.maddox.il2.net.NetFilesTrack.recordFile(netfileserverdef, (com.maddox.il2.net.NetUser)com.maddox.il2.game.Main.cur().netServerParams.host(), s, s1);
    }

    public com.maddox.il2.objects.air.Aircraft loadAir(com.maddox.rts.SectFile sectfile, java.lang.String s, java.lang.String s1, java.lang.String s2, int i)
        throws java.lang.Exception
    {
        boolean flag = !com.maddox.il2.game.Mission.isServer();
        java.lang.Class class1 = com.maddox.rts.ObjIO.classForName(s);
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)class1.newInstance();
        if(com.maddox.il2.game.Main.cur().netServerParams.isSingle() && _loadPlayer)
        {
            if(com.maddox.rts.Property.value(class1, "cockpitClass", null) == null)
                throw new Exception("One of selected aircraft has no cockpit.");
            if(playerNum == 0)
            {
                com.maddox.il2.ai.World.setPlayerAircraft(aircraft);
                _loadPlayer = false;
            } else
            {
                playerNum--;
            }
        }
        aircraft.setName(s2);
        int j = 0;
        if(flag)
        {
            j = ((java.lang.Integer)(java.lang.Integer)actors.get(curActor)).intValue();
            if(j == 0)
                aircraft.load(sectfile, s1, i, null, 0);
            else
                aircraft.load(sectfile, s1, i, net.masterChannel(), j);
        } else
        {
            aircraft.load(sectfile, s1, i, null, 0);
        }
        if(aircraft.isSpawnFromMission())
            if(net.isMirror())
            {
                if(j == 0)
                    actors.set(curActor++, null);
                else
                    actors.set(curActor++, aircraft);
            } else
            {
                actors.add(aircraft);
            }
        aircraft.pos.reset();
        return aircraft;
    }

    public static void preparePlayerNumberOn(com.maddox.rts.SectFile sectfile)
    {
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        java.lang.String s = sectfile.get("MAIN", "player", "");
        if("".equals(s))
        {
            return;
        } else
        {
            java.lang.String s1 = sectfile.get("MAIN", "playerNum", "");
            sectfile.set(s, "numberOn" + s1, usercfg.netNumberOn ? "1" : "0");
            return;
        }
    }

    private void prepareTakeoff(com.maddox.rts.SectFile sectfile, boolean flag)
    {
        if(flag)
        {
            int i = sectfile.sectionIndex("Wing");
            if(i < 0)
                return;
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
                prepareWingTakeoff(sectfile, sectfile.var(i, k));

        } else
        {
            java.lang.String s = sectfile.get("MAIN", "player", (java.lang.String)null);
            if(s == null)
                return;
            prepareWingTakeoff(sectfile, s);
        }
        sectFinger = sectfile.fingerExcludeSectPrefix("$$$");
    }

    private void prepareWingTakeoff(com.maddox.rts.SectFile sectfile, java.lang.String s)
    {
        int i;
        int j;
        java.util.ArrayList arraylist;
label0:
        {
            java.lang.String s1 = s + "_Way";
            i = sectfile.sectionIndex(s1);
            if(i < 0)
                return;
            j = sectfile.vars(i);
            if(j == 0)
                return;
            arraylist = new ArrayList(j);
            for(int k = 0; k < j; k++)
                arraylist.add(sectfile.line(i, k));

            java.lang.String s2 = (java.lang.String)arraylist.get(0);
            if(!s2.startsWith("TAKEOFF"))
                return;
            java.lang.StringBuffer stringbuffer = new StringBuffer("NORMFLY");
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s2);
            numbertokenizer.next((java.lang.String)null);
            double d = numbertokenizer.next(1000D);
            double d1 = numbertokenizer.next(1000D);
            com.maddox.il2.game.WingTakeoffPos wingtakeoffpos = new WingTakeoffPos(d, d1);
            if(mapWingTakeoff == null)
            {
                mapWingTakeoff = new HashMap();
                mapWingTakeoff.put(wingtakeoffpos, wingtakeoffpos);
            } else
            {
                do
                {
                    com.maddox.il2.game.WingTakeoffPos wingtakeoffpos1 = (com.maddox.il2.game.WingTakeoffPos)mapWingTakeoff.get(wingtakeoffpos);
                    if(wingtakeoffpos1 == null)
                    {
                        mapWingTakeoff.put(wingtakeoffpos, wingtakeoffpos);
                        break;
                    }
                    wingtakeoffpos.x += 200;
                } while(true);
            }
            d = wingtakeoffpos.x;
            d1 = wingtakeoffpos.y;
            stringbuffer.append(" ");
            stringbuffer.append(d);
            stringbuffer.append(" ");
            stringbuffer.append(d1);
            if(j > 1)
            {
                java.lang.String s3 = (java.lang.String)arraylist.get(1);
                if(!s3.startsWith("TAKEOFF") && !s3.startsWith("LANDING"))
                {
                    com.maddox.util.NumberTokenizer numbertokenizer1 = new NumberTokenizer(s3);
                    numbertokenizer1.next((java.lang.String)null);
                    numbertokenizer1.next((java.lang.String)null);
                    numbertokenizer1.next((java.lang.String)null);
                    stringbuffer.append(" ");
                    stringbuffer.append(numbertokenizer1.next("1000.0"));
                    stringbuffer.append(" ");
                    stringbuffer.append(numbertokenizer1.next("300.0"));
                    arraylist.set(0, stringbuffer.toString());
                    break label0;
                }
            }
            stringbuffer.append(" 1000 300");
            arraylist.set(0, stringbuffer.toString());
        }
        sectfile.sectionClear(i);
        for(int l = 0; l < j; l++)
            sectfile.lineAdd(i, (java.lang.String)arraylist.get(l));

    }

    private void loadChiefs(com.maddox.rts.SectFile sectfile)
        throws java.lang.Exception
    {
        int i = sectfile.sectionIndex("Chiefs");
        if(i < 0)
            return;
        if(chiefsIni == null)
            chiefsIni = new SectFile("com/maddox/il2/objects/chief.ini");
        int j = sectfile.vars(i);
        for(int k = 0; k < j; k++)
        {
            LOADING_STEP(60 + java.lang.Math.round(((float)k / (float)j) * 20F), "task.Load_tanks");
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
            java.lang.String s = numbertokenizer.next();
            java.lang.String s1 = numbertokenizer.next();
            int l = numbertokenizer.next(-1);
            if(l < 0)
            {
                java.lang.System.out.println("Mission: Wrong chief's army [" + l + "]");
                continue;
            }
            com.maddox.il2.ai.Chief.new_DELAY_WAKEUP = numbertokenizer.next(0.0F);
            com.maddox.il2.ai.Chief.new_SKILL_IDX = numbertokenizer.next(2);
            if(com.maddox.il2.ai.Chief.new_SKILL_IDX < 0 || com.maddox.il2.ai.Chief.new_SKILL_IDX > 3)
            {
                java.lang.System.out.println("Mission: Wrong chief's skill [" + com.maddox.il2.ai.Chief.new_SKILL_IDX + "]");
                continue;
            }
            com.maddox.il2.ai.Chief.new_SLOWFIRE_K = numbertokenizer.next(1.0F);
            if(com.maddox.il2.ai.Chief.new_SLOWFIRE_K < 0.5F || com.maddox.il2.ai.Chief.new_SLOWFIRE_K > 100F)
            {
                java.lang.System.out.println("Mission: Wrong chief's slowfire [" + com.maddox.il2.ai.Chief.new_SLOWFIRE_K + "]");
                continue;
            }
            if(chiefsIni.sectionIndex(s1) < 0)
            {
                java.lang.System.out.println("Mission: Wrong chief's type [" + s1 + "]");
                continue;
            }
            int i1 = s1.indexOf('.');
            if(i1 <= 0)
            {
                java.lang.System.out.println("Mission: Wrong chief's type [" + s1 + "]");
                continue;
            }
            java.lang.String s2 = s1.substring(0, i1);
            java.lang.String s3 = s1.substring(i1 + 1);
            java.lang.String s4 = chiefsIni.get(s2, s3);
            if(s4 == null)
            {
                java.lang.System.out.println("Mission: Wrong chief's type [" + s1 + "]");
                continue;
            }
            numbertokenizer = new NumberTokenizer(s4);
            s4 = numbertokenizer.nextToken();
            numbertokenizer.nextToken();
            java.lang.String s5 = null;
            if(numbertokenizer.hasMoreTokens())
                s5 = numbertokenizer.nextToken();
            java.lang.Class class1 = com.maddox.rts.ObjIO.classForName(s4);
            if(class1 == null)
            {
                java.lang.System.out.println("Mission: Unknown chief's class [" + s4 + "]");
                continue;
            }
            java.lang.reflect.Constructor constructor;
            try
            {
                java.lang.Class aclass[] = new java.lang.Class[6];
                aclass[0] = java.lang.String.class;
                aclass[1] = java.lang.Integer.TYPE;
                aclass[2] = com.maddox.rts.SectFile.class;
                aclass[3] = java.lang.String.class;
                aclass[4] = com.maddox.rts.SectFile.class;
                aclass[5] = java.lang.String.class;
                constructor = class1.getConstructor(aclass);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println("Mission: No required constructor in chief's class [" + s4 + "]");
                continue;
            }
            int j1 = curActor;
            java.lang.Object obj;
            try
            {
                java.lang.Object aobj[] = new java.lang.Object[6];
                aobj[0] = s;
                aobj[1] = new Integer(l);
                aobj[2] = chiefsIni;
                aobj[3] = s1;
                aobj[4] = sectfile;
                aobj[5] = s + "_Road";
                obj = constructor.newInstance(aobj);
            }
            catch(java.lang.Exception exception1)
            {
                java.lang.System.out.println("Mission: Can't create chief '" + s + "' [class:" + s4 + "]");
                continue;
            }
            if(s5 != null)
                ((com.maddox.il2.engine.Actor)obj).icon = com.maddox.il2.engine.IconDraw.get(s5);
            if(j1 != curActor && net != null && net.isMirror())
            {
                for(int k1 = j1; k1 < curActor; k1++)
                {
                    com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)actors.get(k1);
                    if(actor.net != null && !actor.net.isMaster())
                        continue;
                    if(com.maddox.il2.engine.Actor.isValid(actor))
                    {
                        if(obj instanceof com.maddox.il2.ai.ground.ChiefGround)
                            ((com.maddox.il2.ai.ground.ChiefGround)obj).Detach(actor, actor);
                        actor.destroy();
                    }
                    actors.set(k1, null);
                }

            }
            if(obj instanceof com.maddox.il2.ai.ground.ChiefGround)
                ((com.maddox.il2.ai.ground.ChiefGround)obj).dreamFire(true);
        }

    }

    public int getUnitNetIdRemote(com.maddox.il2.engine.Actor actor)
    {
        if(net.isMaster())
        {
            actors.add(actor);
            return 0;
        } else
        {
            java.lang.Integer integer = (java.lang.Integer)actors.get(curActor);
            actors.set(curActor, actor);
            curActor++;
            return integer.intValue();
        }
    }

    private com.maddox.il2.engine.Actor loadStationaryActor(java.lang.String s, java.lang.String s1, int i, double d, double d1, 
            float f, float f1, java.lang.String s2, java.lang.String s3, java.lang.String s4)
    {
        java.lang.Class class1 = null;
        try
        {
            class1 = com.maddox.rts.ObjIO.classForName(s1);
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Mission: class '" + s1 + "' not found");
            return null;
        }
        com.maddox.il2.engine.ActorSpawn actorspawn = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get(class1.getName(), false);
        if(actorspawn == null)
        {
            java.lang.System.out.println("Mission: ActorSpawn for '" + s1 + "' not found");
            return null;
        }
        spawnArg.clear();
        if(s != null)
        {
            if("NONAME".equals(s))
            {
                java.lang.System.out.println("Mission: 'NONAME' - not valid actor name");
                return null;
            }
            if(com.maddox.il2.engine.Actor.getByName(s) != null)
            {
                java.lang.System.out.println("Mission: actor '" + s + "' alredy exist");
                return null;
            }
            spawnArg.name = s;
        }
        spawnArg.army = i;
        spawnArg.armyExist = true;
        spawnArg.country = s2;
        com.maddox.il2.ai.Chief.new_DELAY_WAKEUP = 0.0F;
        com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.new_RADIUS_HIDE = 0.0F;
        if(s2 != null)
            try
            {
                com.maddox.il2.ai.Chief.new_DELAY_WAKEUP = java.lang.Integer.parseInt(s2);
                com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.new_RADIUS_HIDE = com.maddox.il2.ai.Chief.new_DELAY_WAKEUP;
            }
            catch(java.lang.Exception exception1) { }
        com.maddox.il2.ai.Chief.new_SKILL_IDX = 2;
        if(s3 != null)
        {
            try
            {
                com.maddox.il2.ai.Chief.new_SKILL_IDX = java.lang.Integer.parseInt(s3);
            }
            catch(java.lang.Exception exception2) { }
            if(com.maddox.il2.ai.Chief.new_SKILL_IDX < 0 || com.maddox.il2.ai.Chief.new_SKILL_IDX > 3)
            {
                java.lang.System.out.println("Mission: Wrong actor skill '" + com.maddox.il2.ai.Chief.new_SKILL_IDX + "'");
                return null;
            }
        }
        com.maddox.il2.ai.Chief.new_SLOWFIRE_K = 1.0F;
        if(s4 != null)
        {
            try
            {
                com.maddox.il2.ai.Chief.new_SLOWFIRE_K = java.lang.Float.parseFloat(s4);
            }
            catch(java.lang.Exception exception3) { }
            if(com.maddox.il2.ai.Chief.new_SLOWFIRE_K < 0.5F || com.maddox.il2.ai.Chief.new_SLOWFIRE_K > 100F)
            {
                java.lang.System.out.println("Mission: Wrong actor slowfire '" + com.maddox.il2.ai.Chief.new_SLOWFIRE_K + "'");
                return null;
            }
        }
        p.set(d, d1, 0.0D);
        spawnArg.point = p;
        o.set(f, 0.0F, 0.0F);
        spawnArg.orient = o;
        if(f1 > 0.0F)
        {
            spawnArg.timeLenExist = true;
            spawnArg.timeLen = f1;
        }
        spawnArg.netChannel = null;
        spawnArg.netIdRemote = 0;
        if(net.isMirror())
        {
            spawnArg.netChannel = net.masterChannel();
            spawnArg.netIdRemote = ((java.lang.Integer)(java.lang.Integer)actors.get(curActor)).intValue();
            if(spawnArg.netIdRemote == 0)
            {
                actors.set(curActor++, null);
                return null;
            }
        }
        com.maddox.il2.engine.Actor actor = null;
        try
        {
            actor = actorspawn.actorSpawn(spawnArg);
        }
        catch(java.lang.Exception exception4)
        {
            java.lang.System.out.println(exception4.getMessage());
            exception4.printStackTrace();
        }
        if(net.isMirror())
            actors.set(curActor++, actor);
        else
            actors.add(actor);
        return actor;
    }

    private void loadStationary(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("Stationary");
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        for(int k = 0; k < j; k++)
        {
            LOADING_STEP(80 + java.lang.Math.round(((float)k / (float)j) * 5F), "task.Load_stationary_objects");
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
            loadStationaryActor(null, numbertokenizer.next(""), numbertokenizer.next(0), numbertokenizer.next(0.0D), numbertokenizer.next(0.0D), numbertokenizer.next(0.0F), numbertokenizer.next(0.0F), numbertokenizer.next((java.lang.String)null), numbertokenizer.next((java.lang.String)null), numbertokenizer.next((java.lang.String)null));
        }

    }

    private void loadNStationary(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("NStationary");
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        for(int k = 0; k < j; k++)
        {
            LOADING_STEP(85 + java.lang.Math.round(((float)k / (float)j) * 5F), "task.Load_stationary_objects");
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
            loadStationaryActor(numbertokenizer.next(""), numbertokenizer.next(""), numbertokenizer.next(0), numbertokenizer.next(0.0D), numbertokenizer.next(0.0D), numbertokenizer.next(0.0F), numbertokenizer.next(0.0F), numbertokenizer.next((java.lang.String)null), numbertokenizer.next((java.lang.String)null), numbertokenizer.next((java.lang.String)null));
        }

    }

    private void loadRocketry(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("Rocket");
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        for(int k = 0; k < j; k++)
        {
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
            if(!numbertokenizer.hasMoreTokens())
                continue;
            java.lang.String s = numbertokenizer.next("");
            if(!numbertokenizer.hasMoreTokens())
                continue;
            java.lang.String s1 = numbertokenizer.next("");
            if(!numbertokenizer.hasMoreTokens())
                continue;
            int l = numbertokenizer.next(1, 1, 2);
            double d = numbertokenizer.next(0.0D);
            if(!numbertokenizer.hasMoreTokens())
                continue;
            double d1 = numbertokenizer.next(0.0D);
            if(!numbertokenizer.hasMoreTokens())
                continue;
            float f = numbertokenizer.next(0.0F);
            if(!numbertokenizer.hasMoreTokens())
                continue;
            float f1 = numbertokenizer.next(0.0F);
            int i1 = numbertokenizer.next(1);
            float f2 = numbertokenizer.next(20F);
            com.maddox.JGP.Point2d point2d = null;
            if(numbertokenizer.hasMoreTokens())
                point2d = new Point2d(numbertokenizer.next(0.0D), numbertokenizer.next(0.0D));
            com.maddox.rts.NetChannel netchannel = null;
            int j1 = 0;
            if(net.isMirror())
            {
                netchannel = net.masterChannel();
                j1 = ((java.lang.Integer)(java.lang.Integer)actors.get(curActor)).intValue();
                if(j1 == 0)
                {
                    actors.set(curActor++, null);
                    continue;
                }
            }
            com.maddox.il2.objects.vehicles.artillery.RocketryGeneric rocketrygeneric = null;
            try
            {
                rocketrygeneric = com.maddox.il2.objects.vehicles.artillery.RocketryGeneric.New(s, s1, netchannel, j1, l, d, d1, f, f1, i1, f2, point2d);
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
            if(net.isMirror())
                actors.set(curActor++, rocketrygeneric);
            else
                actors.add(rocketrygeneric);
        }

    }

    private void loadHouses(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("Buildings");
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        if(j == 0)
            return;
        if(net.isMirror())
        {
            spawnArg.netChannel = net.masterChannel();
            spawnArg.netIdRemote = ((java.lang.Integer)(java.lang.Integer)actors.get(curActor)).intValue();
            com.maddox.il2.objects.buildings.HouseManager housemanager = new HouseManager(sectfile, "Buildings", net.masterChannel(), ((java.lang.Integer)(java.lang.Integer)actors.get(curActor)).intValue());
            actors.set(curActor++, housemanager);
        } else
        {
            com.maddox.il2.objects.buildings.HouseManager housemanager1 = new HouseManager(sectfile, "Buildings", null, 0);
            actors.add(housemanager1);
        }
    }

    private void loadBornPlaces(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("BornPlace");
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        if(j == 0)
            return;
        if(com.maddox.il2.ai.World.cur().airdrome == null)
            return;
        if(com.maddox.il2.ai.World.cur().airdrome.stay == null)
            return;
        com.maddox.il2.ai.World.cur().bornPlaces = new ArrayList(j);
        for(int k = 0; k < j; k++)
        {
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
            int l = numbertokenizer.next(0, 0, com.maddox.il2.ai.Army.amountNet() - 1);
            float f = numbertokenizer.next(1000, 500, 10000);
            double d = f * f;
            float f1 = numbertokenizer.next(0);
            float f2 = numbertokenizer.next(0);
            boolean flag = numbertokenizer.next(1) == 1;
            int i1 = 1000;
            int j1 = 200;
            int k1 = 0;
            int l1 = 0;
            int i2 = 0;
            int j2 = 5000;
            int k2 = 50;
            boolean flag1 = false;
            boolean flag2 = false;
            boolean flag3 = false;
            boolean flag4 = false;
            boolean flag5 = false;
            boolean flag6 = false;
            boolean flag7 = false;
            double d1 = 3.7999999999999998D;
            boolean flag8 = false;
            int l2 = 0;
            try
            {
                i1 = numbertokenizer.next(1000, 0, 10000);
                l2++;
                j1 = numbertokenizer.next(200, 0, 500);
                l2++;
                k1 = numbertokenizer.next(0, 0, 360);
                l2++;
                l1 = numbertokenizer.next(0, 0, 0x1869f);
                l2++;
                i2 = numbertokenizer.next(0, 0, 0x1869f);
                l2++;
                j2 = numbertokenizer.next(5000, 0, 0x1869f);
                l2++;
                k2 = numbertokenizer.next(50, 1, 0x1869f);
                l2++;
                if(numbertokenizer.next(0, 0, 1) == 1)
                {
                    flag2 = true;
                    l2++;
                }
                if(numbertokenizer.next(0, 0, 1) == 1)
                {
                    flag3 = true;
                    l2++;
                }
                if(numbertokenizer.next(0, 0, 1) == 1)
                {
                    flag4 = true;
                    l2++;
                }
                if(numbertokenizer.next(0, 0, 1) == 1)
                {
                    flag6 = true;
                    l2++;
                }
                if(numbertokenizer.next(0, 0, 1) == 1)
                {
                    flag7 = true;
                    l2++;
                }
                d1 = numbertokenizer.next(3.7999999999999998D, 0.0D, 10D);
                if(numbertokenizer.next(0, 0, 1) == 1)
                {
                    flag5 = true;
                    l2++;
                }
                if(numbertokenizer.next(0, 0, 1) == 1)
                {
                    flag8 = true;
                    l2++;
                }
                if(numbertokenizer.next(0, 0, 1) == 1)
                {
                    flag1 = true;
                    l2++;
                }
            }
            catch(java.lang.Exception exception1)
            {
                java.lang.System.out.println("Mission: no air spawn entries defined for HomeBase nr. " + k + ". value index: " + l2);
            }
            boolean flag9 = false;
            com.maddox.il2.ai.air.Point_Stay apoint_stay[][] = com.maddox.il2.ai.World.cur().airdrome.stay;
            if(!com.maddox.il2.game.Mission.isDogfight())
            {
                flag9 = true;
            } else
            {
                for(int i3 = 0; i3 < apoint_stay.length; i3++)
                {
                    if(apoint_stay[i3] == null)
                        continue;
                    com.maddox.il2.ai.air.Point_Stay point_stay = apoint_stay[i3][apoint_stay[i3].length - 1];
                    if((double)((point_stay.x - f1) * (point_stay.x - f1) + (point_stay.y - f2) * (point_stay.y - f2)) > d)
                        continue;
                    flag9 = true;
                    break;
                }

            }
            if(!flag9)
                continue;
            com.maddox.il2.net.BornPlace bornplace = new BornPlace(f1, f2, l, f);
            bornplace.zutiRadarHeight_MIN = i2;
            bornplace.zutiRadarHeight_MAX = j2;
            bornplace.zutiRadarRange = k2;
            bornplace.zutiSpawnHeight = i1;
            bornplace.zutiSpawnSpeed = j1;
            bornplace.zutiSpawnOrient = k1;
            bornplace.zutiMaxBasePilots = l1;
            bornplace.zutiAirspawnIfCarrierFull = flag1;
            bornplace.zutiAirspawnOnly = flag2;
            bornplace.zutiDisableSpawning = flag6;
            bornplace.zutiEnableFriction = flag7;
            bornplace.zutiFriction = d1;
            bornplace.zutiEnablePlaneLimits = flag3;
            bornplace.zutiDecreasingNumberOfPlanes = flag4;
            bornplace.zutiIncludeStaticPlanes = flag5;
            bornplace.zutiBpIndex = k;
            bornplace.zutiStaticPositionOnly = flag8;
            bornplace.bParachute = flag;
            com.maddox.il2.ai.World.cur().bornPlaces.add(bornplace);
            bornplace.zutiCountBornPlaceStayPoints();
            if(actors != null)
            {
                int j3 = actors.size();
                for(int l3 = 0; l3 < j3; l3++)
                {
                    com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)actors.get(l3);
                    if(!com.maddox.il2.engine.Actor.isValid(actor) || actor.pos == null || !com.maddox.il2.game.ZutiSupportMethods.isStaticActor(actor))
                        continue;
                    com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                    double d2 = (point3d.x - (double)f1) * (point3d.x - (double)f1) + (point3d.y - (double)f2) * (point3d.y - (double)f2);
                    if(d2 <= d)
                        actor.setArmy(bornplace.army);
                }

            }
            int k3 = sectfile.sectionIndex("BornPlace" + k);
            if(k3 >= 0)
            {
                int i4 = sectfile.vars(k3);
                for(int j4 = 0; j4 < i4; j4++)
                {
                    java.lang.String s = sectfile.line(k3, j4);
                    java.util.StringTokenizer stringtokenizer = new StringTokenizer(s);
                    com.maddox.il2.game.ZutiAircraft zutiaircraft = new ZutiAircraft();
                    java.lang.String s1 = "";
                    for(int k4 = 0; stringtokenizer.hasMoreTokens(); k4++)
                        switch(k4)
                        {
                        case 0: // '\0'
                            zutiaircraft.setAcName(stringtokenizer.nextToken());
                            break;

                        case 1: // '\001'
                            zutiaircraft.setMaxAllowed(java.lang.Integer.valueOf(stringtokenizer.nextToken()).intValue());
                            break;

                        default:
                            s1 = s1 + " " + stringtokenizer.nextToken();
                            break;
                        }

                    zutiaircraft.setLoadedWeapons(s1, false);
                    java.lang.String s2 = zutiaircraft.getAcName();
                    if(s2 == null)
                        continue;
                    s2 = s2.intern();
                    java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(s2, "airClass", null);
                    if(class1 == null || !com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                        continue;
                    if(bornplace.zutiAircrafts == null)
                        bornplace.zutiAircrafts = new ArrayList();
                    bornplace.zutiAircrafts.add(zutiaircraft);
                }

            }
            bornplace.zutiFillAirNames();
            zutiLoadBornPlaceCountries(bornplace, sectfile, k);
        }

        try
        {
            zutiAssignBpToMovingCarrier();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Mission error, ID_15: " + exception.toString());
        }
    }

    private void loadTargets(com.maddox.rts.SectFile sectfile)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams.isSingle() || com.maddox.il2.game.Main.cur().netServerParams.isCoop() || com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
        {
            int i = sectfile.sectionIndex("Target");
            if(i < 0)
                return;
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
                com.maddox.il2.ai.Target.create(sectfile.line(i, k));

        }
    }

    private void loadViewPoint(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("StaticCamera");
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        for(int k = 0; k < j; k++)
        {
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
            float f = numbertokenizer.next(0);
            float f1 = numbertokenizer.next(0);
            float f2 = numbertokenizer.next(100, 2, 10000);
            com.maddox.il2.objects.ActorViewPoint actorviewpoint = new ActorViewPoint();
            com.maddox.JGP.Point3d point3d = new Point3d(f, f1, f2 + com.maddox.il2.ai.World.land().HQ_Air(f, f1));
            actorviewpoint.pos.setAbs(point3d);
            actorviewpoint.pos.reset();
            actorviewpoint.dreamFire(true);
            actorviewpoint.setName("StaticCamera_" + k);
            if(net.isMirror())
            {
                actorviewpoint.createNetObject(net.masterChannel(), ((java.lang.Integer)(java.lang.Integer)actors.get(curActor)).intValue());
                actors.set(curActor++, actorviewpoint);
            } else
            {
                actorviewpoint.createNetObject(null, 0);
                actors.add(actorviewpoint);
            }
        }

    }

    private void checkBridgesAndHouses(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sections();
label0:
        for(int j = 0; j < i; j++)
        {
            java.lang.String s = sectfile.sectionName(j);
            if(s.endsWith("_Way"))
            {
                int i1 = sectfile.vars(j);
                int l1 = 0;
                do
                {
                    if(l1 >= i1)
                        continue label0;
                    java.lang.String s1 = sectfile.var(j, l1);
                    if(s1.equals("GATTACK"))
                    {
                        com.maddox.util.SharedTokenizer.set(sectfile.value(j, l1));
                        com.maddox.util.SharedTokenizer.next((java.lang.String)null);
                        com.maddox.util.SharedTokenizer.next((java.lang.String)null);
                        com.maddox.util.SharedTokenizer.next((java.lang.String)null);
                        com.maddox.util.SharedTokenizer.next((java.lang.String)null);
                        java.lang.String s2 = com.maddox.util.SharedTokenizer.next((java.lang.String)null);
                        if(s2 != null && s2.startsWith("Bridge"))
                        {
                            com.maddox.il2.objects.bridges.LongBridge longbridge1 = (com.maddox.il2.objects.bridges.LongBridge)com.maddox.il2.engine.Actor.getByName(" " + s2);
                            if(longbridge1 != null && !longbridge1.isAlive())
                                longbridge1.BeLive();
                        }
                    }
                    l1++;
                } while(true);
            }
            if(!s.endsWith("_Road"))
                continue;
            int j1 = sectfile.vars(j);
            for(int i2 = 0; i2 < j1; i2++)
            {
                com.maddox.util.SharedTokenizer.set(sectfile.value(j, i2));
                com.maddox.util.SharedTokenizer.next((java.lang.String)null);
                int k2 = (int)com.maddox.util.SharedTokenizer.next(1.0D);
                if(k2 >= 0)
                    continue;
                k2 = -k2 - 1;
                com.maddox.il2.objects.bridges.LongBridge longbridge = com.maddox.il2.objects.bridges.LongBridge.getByIdx(k2);
                if(longbridge != null && !longbridge.isAlive())
                    longbridge.BeLive();
            }

        }

        int k = sectfile.sectionIndex("Target");
        if(k < 0)
            return;
        int l = sectfile.vars(k);
        for(int k1 = 0; k1 < l; k1++)
        {
            com.maddox.util.SharedTokenizer.set(sectfile.line(k, k1));
            int j2 = com.maddox.util.SharedTokenizer.next(0, 0, 7);
            if(j2 == 1 || j2 == 2 || j2 == 6 || j2 == 7)
            {
                com.maddox.util.SharedTokenizer.next(0);
                com.maddox.util.SharedTokenizer.next(0);
                com.maddox.util.SharedTokenizer.next(0);
                com.maddox.util.SharedTokenizer.next(0);
                int l2 = com.maddox.util.SharedTokenizer.next(0);
                int i3 = com.maddox.util.SharedTokenizer.next(0);
                int j3 = com.maddox.util.SharedTokenizer.next(1000, 50, 3000);
                int k3 = com.maddox.util.SharedTokenizer.next(0);
                java.lang.String s3 = com.maddox.util.SharedTokenizer.next(null);
                if(s3 != null && s3.startsWith("Bridge"))
                    s3 = " " + s3;
                switch(j2)
                {
                case 3: // '\003'
                case 4: // '\004'
                case 5: // '\005'
                default:
                    break;

                case 1: // '\001'
                case 6: // '\006'
                    com.maddox.il2.ai.World.cur().statics.restoreAllHouses(l2, i3, j3);
                    break;

                case 2: // '\002'
                case 7: // '\007'
                    if(s3 == null)
                        break;
                    com.maddox.il2.objects.bridges.LongBridge longbridge2 = (com.maddox.il2.objects.bridges.LongBridge)com.maddox.il2.engine.Actor.getByName(s3);
                    if(longbridge2 != null && !longbridge2.isAlive())
                        longbridge2.BeLive();
                    break;
                }
            }
        }

    }

    public static void doMissionStarting()
    {
        java.util.ArrayList arraylist = new ArrayList(com.maddox.il2.engine.Engine.targets());
        int i = arraylist.size();
        for(int j = 0; j < i;)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)arraylist.get(j);
            if(!com.maddox.il2.engine.Actor.isValid(actor))
                continue;
            try
            {
                actor.missionStarting();
                continue;
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception.getMessage());
                exception.printStackTrace();
                j++;
            }
        }

    }

    public static void initRadioSounds()
    {
        if(radioStationsLoaded)
            return;
        if(hasRadioStations)
        {
            com.maddox.sound.CmdMusic.setCurrentVolume(0.0F);
            radioStationsLoaded = true;
        }
        com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
        if(aircraft != null)
        {
            java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().mission.getBeacons(aircraft.FM.actor.getArmy());
            if(arraylist != null)
            {
                for(int i = 0; i < arraylist.size(); i++)
                {
                    com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)arraylist.get(i);
                    if(actor instanceof com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation)
                    {
                        hasRadioStations = true;
                        aircraft.FM.AS.preLoadRadioStation(actor);
                    }
                }

            }
            if(!hasRadioStations)
                radioStationsLoaded = true;
        }
    }

    public void doBegin()
    {
        if(bPlaying)
            return;
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            com.maddox.il2.game.Main3D.cur3D().setDrawLand(true);
            if(com.maddox.il2.ai.World.cur().diffCur.Clouds)
            {
                com.maddox.il2.game.Main3D.cur3D().bDrawClouds = true;
                if(com.maddox.il2.engine.RenderContext.cfgSky.get() == 0)
                {
                    com.maddox.il2.engine.RenderContext.cfgSky.set(1);
                    com.maddox.il2.engine.RenderContext.cfgSky.apply();
                    com.maddox.il2.engine.RenderContext.cfgSky.reset();
                }
            } else
            {
                com.maddox.il2.game.Main3D.cur3D().bDrawClouds = false;
            }
            com.maddox.rts.CmdEnv.top().exec("fov 70");
            if(com.maddox.il2.game.Main3D.cur3D().keyRecord != null)
            {
                com.maddox.il2.game.Main3D.cur3D().keyRecord.clearPrevStates();
                com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
                com.maddox.il2.game.Main3D.cur3D().keyRecord.stopRecording(false);
                if(com.maddox.il2.game.Main.cur().netServerParams.isSingle())
                    com.maddox.il2.game.Main3D.cur3D().keyRecord.startRecording();
            }
            com.maddox.il2.net.NetMissionTrack.countRecorded = 0;
            if(com.maddox.il2.game.Main3D.cur3D().guiManager != null)
            {
                com.maddox.il2.game.Main3D.cur3D().guiManager.setTimeGameActive(true);
                com.maddox.il2.gui.GUIPad.bStartMission = true;
            }
            if(!com.maddox.il2.game.Main.cur().netServerParams.isCoop())
                com.maddox.il2.game.Mission.doMissionStarting();
            if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
            {
                com.maddox.rts.Time.setPause(false);
                com.maddox.rts.RTSConf.cur.time.setEnableChangePause1(false);
            }
            com.maddox.rts.CmdEnv.top().exec("music PUSH");
            com.maddox.rts.CmdEnv.top().exec("music STOP");
            if(!com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
                com.maddox.il2.objects.effects.ForceFeedback.startMission();
            com.maddox.rts.Joy.adapter().rePostMoves();
            viewSet = com.maddox.il2.game.Main3D.cur3D().viewSet_Get();
            iconTypes = com.maddox.il2.game.Main3D.cur3D().iconTypes();
        } else
        {
            com.maddox.il2.game.Mission.doMissionStarting();
            com.maddox.rts.Time.setPause(false);
        }
        if(net.isMaster())
        {
            sendCmd(10);
            doReplicateNotMissionActors(true);
        }
        if(com.maddox.il2.game.Main.cur().netServerParams.isSingle())
        {
            com.maddox.il2.game.Main.cur().netServerParams.setExtraOcclusion(false);
            com.maddox.sound.AudioDevice.soundsOn();
        }
        if(com.maddox.il2.game.Main.cur().netServerParams.isMaster() && (com.maddox.il2.game.Main.cur().netServerParams.isCoop() || com.maddox.il2.game.Main.cur().netServerParams.isSingle() || com.maddox.il2.game.Main.cur().netServerParams.isDogfight()))
            com.maddox.il2.ai.World.cur().targetsGuard.activate();
        com.maddox.il2.ai.EventLog.type(true, "Mission: " + name() + " is Playing");
        com.maddox.il2.ai.EventLog.type("Mission BEGIN");
        if(com.maddox.il2.game.Main.cur().netServerParams != null)
            com.maddox.il2.game.Main.cur().netServerParams.zutiResetServerTime();
        bPlaying = true;
        if(com.maddox.il2.game.Main.cur().netServerParams != null)
            com.maddox.il2.game.Main.cur().netServerParams.USGSupdate();
    }

    public void doEnd()
    {
        if(!bPlaying)
            return;
        try
        {
            com.maddox.il2.ai.EventLog.type("Mission END");
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                com.maddox.il2.objects.effects.ForceFeedback.stopMission();
                if(com.maddox.il2.game.Main3D.cur3D().guiManager != null)
                    com.maddox.il2.game.Main3D.cur3D().guiManager.setTimeGameActive(false);
                com.maddox.il2.net.NetMissionTrack.stopRecording();
                if(com.maddox.il2.game.Main3D.cur3D().keyRecord != null)
                {
                    if(com.maddox.il2.game.Main3D.cur3D().keyRecord.isPlaying())
                    {
                        com.maddox.il2.game.Main3D.cur3D().keyRecord.stopPlay();
                        com.maddox.il2.game.Main3D.cur3D().guiManager.setKeyboardGameActive(true);
                        com.maddox.il2.game.Main3D.cur3D().guiManager.setMouseGameActive(true);
                        com.maddox.il2.game.Main3D.cur3D().guiManager.setJoyGameActive(true);
                    }
                    com.maddox.il2.game.Main3D.cur3D().keyRecord.stopRecording(true);
                }
                com.maddox.rts.CmdEnv.top().exec("music POP");
                com.maddox.rts.CmdEnv.top().exec("music PLAY");
            }
            com.maddox.rts.RTSConf.cur.time.setEnableChangePause1(true);
            com.maddox.rts.Time.setPause(true);
            if(net.isMaster())
                sendCmd(20);
            com.maddox.sound.AudioDevice.soundsOff();
            com.maddox.il2.objects.sounds.Voice.endSession();
            bPlaying = false;
            if(com.maddox.il2.game.Main.cur().netServerParams != null)
                com.maddox.il2.game.Main.cur().netServerParams.USGSupdate();
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Mission error, ID_16: " + exception.toString());
        }
        return;
    }

    public com.maddox.rts.NetObj netObj()
    {
        return net;
    }

    private void sendCmd(int i)
    {
        if(net.isMirrored())
            try
            {
                java.util.List list = com.maddox.rts.NetEnv.channels();
                int j = list.size();
                for(int k = 0; k < j; k++)
                {
                    com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)list.get(k);
                    if(netchannel != net.masterChannel() && netchannel.isReady() && netchannel.isMirrored(net) && (netchannel.userState == 4 || netchannel.userState == 0))
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                        netmsgguaranted.writeByte(i);
                        net.postTo(netchannel, netmsgguaranted);
                    }
                }

            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.game.Mission.printDebug(exception);
            }
    }

    private void doReplicateNotMissionActors(boolean flag)
    {
        if(net.isMirrored())
        {
            java.util.List list = com.maddox.rts.NetEnv.channels();
            int i = list.size();
            for(int j = 0; j < i; j++)
            {
                com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)list.get(j);
                if(netchannel == net.masterChannel() || !netchannel.isReady() || !netchannel.isMirrored(net))
                    continue;
                if(flag)
                {
                    if(netchannel.userState == 4)
                        doReplicateNotMissionActors(netchannel, true);
                } else
                {
                    netchannel.userState = 1;
                }
            }

        }
    }

    private void doReplicateNotMissionActors(com.maddox.rts.NetChannel netchannel, boolean flag)
    {
        if(flag)
        {
            netchannel.userState = 0;
            com.maddox.util.HashMapInt hashmapint = com.maddox.rts.NetEnv.cur().objects;
            for(com.maddox.util.HashMapIntEntry hashmapintentry = hashmapint.nextEntry(null); hashmapintentry != null; hashmapintentry = hashmapint.nextEntry(hashmapintentry))
            {
                com.maddox.rts.NetObj netobj = (com.maddox.rts.NetObj)hashmapintentry.getValue();
                if((netobj instanceof com.maddox.il2.engine.ActorNet) && !netchannel.isMirrored(netobj))
                {
                    com.maddox.il2.engine.ActorNet actornet = (com.maddox.il2.engine.ActorNet)netobj;
                    if(com.maddox.il2.engine.Actor.isValid(actornet.actor()) && !actornet.actor().isSpawnFromMission())
                        com.maddox.rts.MsgNet.postRealNewChannel(netobj, netchannel);
                }
            }

        } else
        {
            netchannel.userState = 1;
        }
    }

    private void doResvMission(com.maddox.rts.NetMsgInput netmsginput)
    {
        try
        {
            while(netmsginput.available() > 0) 
            {
                int i = netmsginput.readInt();
                if(i < 0)
                {
                    java.lang.String s = netmsginput.read255();
                    sectFile.sectionAdd(s);
                } else
                {
                    sectFile.lineAdd(i, netmsginput.read255(), netmsginput.read255());
                }
            }
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.game.Mission.printDebug(exception);
            java.lang.System.out.println("Bad format reseived missiion");
        }
    }

    private void doSendMission(com.maddox.rts.NetChannel netchannel, int i)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(i);
            int j = sectFile.sections();
label0:
            for(int k = 0; k < j; k++)
            {
                java.lang.String s = sectFile.sectionName(k);
                if(s.startsWith("$$$"))
                    continue;
                if(netmsgguaranted.size() >= 128)
                {
                    net.postTo(netchannel, netmsgguaranted);
                    netmsgguaranted = new NetMsgGuaranted();
                    netmsgguaranted.writeByte(i);
                }
                netmsgguaranted.writeInt(-1);
                netmsgguaranted.write255(s);
                int l = sectFile.vars(k);
                int i1 = 0;
                do
                {
                    if(i1 >= l)
                        continue label0;
                    if(netmsgguaranted.size() >= 128)
                    {
                        net.postTo(netchannel, netmsgguaranted);
                        netmsgguaranted = new NetMsgGuaranted();
                        netmsgguaranted.writeByte(i);
                    }
                    netmsgguaranted.writeInt(k);
                    netmsgguaranted.write255(sectFile.var(k, i1));
                    netmsgguaranted.write255(sectFile.value(k, i1));
                    i1++;
                } while(true);
            }

            if(netmsgguaranted.size() > 1)
                net.postTo(netchannel, netmsgguaranted);
            netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(i + 1);
            net.postTo(netchannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.game.Mission.printDebug(exception);
        }
    }

    public void replicateTimeofDay()
    {
        if(!com.maddox.il2.game.Mission.isServer())
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(11);
            netmsgguaranted.writeFloat(com.maddox.il2.ai.World.getTimeofDay());
            net.post(netmsgguaranted);
        }
        catch(java.lang.Exception exception) { }
    }

    private boolean isExistFile(java.lang.String s)
    {
        boolean flag = false;
        try
        {
            com.maddox.rts.SFSInputStream sfsinputstream = new SFSInputStream(s);
            sfsinputstream.close();
            flag = true;
        }
        catch(java.lang.Exception exception) { }
        return flag;
    }

    private void netInput(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        boolean flag = false;
        if((net instanceof com.maddox.il2.game.Master) || netmsginput.channel() != net.masterChannel())
            flag = true;
        boolean flag1 = netmsginput.channel() instanceof com.maddox.rts.NetChannelStream;
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = null;
        int i = netmsginput.readUnsignedByte();
        switch(i)
        {
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
        case 9: // '\t'
        case 14: // '\016'
        case 15: // '\017'
        case 16: // '\020'
        case 17: // '\021'
        case 18: // '\022'
        case 19: // '\023'
        default:
            break;

        case 0: // '\0'
            netmsginput.channel().userState = 2;
            netmsgguaranted = new NetMsgGuaranted();
            if(flag)
            {
                if(flag1)
                {
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
                    netmsgguaranted1.writeByte(13);
                    netmsgguaranted1.writeLong(com.maddox.rts.Time.current());
                    net.postTo(netmsginput.channel(), netmsgguaranted1);
                }
                netmsgguaranted.writeByte(0);
                netmsgguaranted.write255(name);
                netmsgguaranted.writeLong(sectFinger);
                break;
            }
            name = netmsginput.read255();
            sectFinger = netmsginput.readLong();
            com.maddox.il2.game.Main.cur().netMissionListener.netMissionState(0, 0.0F, name);
            if(!flag1)
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setMissProp("missions/" + name);
            java.lang.String s = "missions/" + name;
            if(!flag1 && isExistFile(s))
            {
                sectFile = new SectFile(s, 0, false);
                if(sectFinger == sectFile.fingerExcludeSectPrefix("$$$"))
                {
                    netmsgguaranted.writeByte(3);
                    break;
                }
            }
            s = "missions/Net/Cache/" + sectFinger + ".mis";
            int ai[] = getSwTbl(s, sectFinger);
            sectFile = new SectFile(s, 0, false, ai);
            if(!flag1 && sectFinger == sectFile.fingerExcludeSectPrefix("$$$"))
            {
                netmsgguaranted.writeByte(3);
            } else
            {
                sectFile = new SectFile(s, 1, false, ai);
                sectFile.clear();
                netmsgguaranted.writeByte(1);
            }
            break;

        case 13: // '\r'
            if(!flag)
            {
                long l = netmsginput.readLong();
                com.maddox.rts.RTSConf.cur.time.setCurrent(l);
                com.maddox.il2.net.NetMissionTrack.playingStartTime = l;
            }
            break;

        case 1: // '\001'
            if(flag)
            {
                doSendMission(netmsginput.channel(), 1);
            } else
            {
                com.maddox.il2.game.Main.cur().netMissionListener.netMissionState(1, 0.0F, null);
                doResvMission(netmsginput);
            }
            break;

        case 2: // '\002'
            if(!flag)
            {
                sectFile.saveFile();
                netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(3);
            }
            break;

        case 3: // '\003'
            if(flag)
            {
                int j = actors.size();
                for(int i1 = 0; i1 < j;)
                {
                    netmsgguaranted = new NetMsgGuaranted();
                    netmsgguaranted.writeByte(3);
                    for(int j1 = 64; j1-- > 0 && i1 < j;)
                    {
                        com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)actors.get(i1++);
                        if(com.maddox.il2.engine.Actor.isValid(actor1))
                            netmsgguaranted.writeShort(actor1.net.idLocal());
                        else
                            netmsgguaranted.writeShort(0);
                    }

                    net.postTo(netmsginput.channel(), netmsgguaranted);
                }

                netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(4);
                netmsginput.channel().userState = 3;
            } else
            {
                com.maddox.il2.game.Main.cur().netMissionListener.netMissionState(2, 0.0F, null);
                for(; netmsginput.available() > 0; actors.add(new Integer(netmsginput.readUnsignedShort())));
            }
            break;

        case 4: // '\004'
            if(flag)
            {
                if(com.maddox.il2.game.Mission.isDogfight() || (netmsginput.channel() instanceof com.maddox.rts.NetChannelOutStream))
                {
                    com.maddox.il2.ai.World.cur().statics.netBridgeSync(netmsginput.channel());
                    com.maddox.il2.ai.World.cur().statics.netHouseSync(netmsginput.channel());
                }
                for(int k = 0; k < actors.size(); k++)
                {
                    com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)actors.get(k);
                    if(!com.maddox.il2.engine.Actor.isValid(actor))
                        continue;
                    try
                    {
                        com.maddox.rts.NetChannel netchannel = netmsginput.channel();
                        netchannel.setMirrored(actor.net);
                        actor.netFirstUpdate(netmsginput.channel());
                    }
                    catch(java.lang.Exception exception1)
                    {
                        com.maddox.il2.game.Mission.printDebug(exception1);
                    }
                }

                if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.ai.World.cur().houseManager))
                    com.maddox.il2.ai.World.cur().houseManager.fullUpdateChannel(netmsginput.channel());
                netmsgguaranted = new NetMsgGuaranted();
                if(com.maddox.il2.game.Mission.isPlaying())
                {
                    netmsgguaranted.writeByte(10);
                    net.postTo(netmsginput.channel(), netmsgguaranted);
                    netmsgguaranted = new NetMsgGuaranted();
                    netmsgguaranted.writeByte(11);
                    netmsgguaranted.writeFloat(com.maddox.il2.ai.World.getTimeofDay());
                    net.postTo(netmsginput.channel(), netmsgguaranted);
                    netmsgguaranted = null;
                    doReplicateNotMissionActors(netmsginput.channel(), true);
                    trySendMsgStart(netmsginput.channel());
                } else
                {
                    netmsgguaranted.writeByte(5);
                    netmsginput.channel().userState = 4;
                }
            } else
            {
                netmsginput.channel().userState = 3;
                try
                {
                    load(name, sectFile, true);
                }
                catch(java.lang.Exception exception)
                {
                    com.maddox.il2.game.Mission.printDebug(exception);
                    com.maddox.il2.game.Main.cur().netMissionListener.netMissionState(4, 0.0F, exception.getMessage());
                }
            }
            break;

        case 5: // '\005'
            if(!flag);
            break;

        case 10: // '\n'
            if(!(net instanceof com.maddox.il2.game.Master) && netmsginput.channel() == net.masterChannel())
            {
                if(net.isMirrored())
                {
                    netmsgguaranted = new NetMsgGuaranted();
                    netmsgguaranted.writeByte(10);
                    net.post(netmsgguaranted);
                    netmsgguaranted = null;
                }
                doReplicateNotMissionActors(true);
                doReplicateNotMissionActors(netmsginput.channel(), true);
                doBegin();
                com.maddox.il2.game.Main.cur().netMissionListener.netMissionState(6, 0.0F, null);
            }
            break;

        case 11: // '\013'
            if(!(net instanceof com.maddox.il2.game.Master) && netmsginput.channel() == net.masterChannel())
            {
                float f = netmsginput.readFloat();
                com.maddox.il2.ai.World.setTimeofDay(f);
                com.maddox.il2.ai.World.land().cubeFullUpdate();
            }
            break;

        case 20: // '\024'
            if(!(net instanceof com.maddox.il2.game.Master) && netmsginput.channel() == net.masterChannel())
            {
                com.maddox.il2.game.Main.cur().netMissionListener.netMissionState(7, 0.0F, null);
                doReplicateNotMissionActors(false);
                doReplicateNotMissionActors(netmsginput.channel(), false);
                doEnd();
                if(net.isMirrored())
                {
                    netmsgguaranted = new NetMsgGuaranted();
                    netmsgguaranted.writeByte(20);
                    net.post(netmsgguaranted);
                    netmsgguaranted = null;
                }
            }
            break;

        case 12: // '\f'
            if(!(net instanceof com.maddox.il2.game.Master) && netmsginput.channel() == net.masterChannel())
                com.maddox.il2.game.Main.cur().netMissionListener.netMissionState(9, 0.0F, null);
            break;
        }
        if(netmsgguaranted != null && netmsgguaranted.size() > 0)
            net.postTo(netmsginput.channel(), netmsgguaranted);
    }

    public void trySendMsgStart(java.lang.Object obj)
    {
        if(isDestroyed())
            return;
        com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)obj;
        if(netchannel.isDestroyed())
            return;
        com.maddox.util.HashMapInt hashmapint = com.maddox.rts.RTSConf.cur.netEnv.objects;
        for(com.maddox.util.HashMapIntEntry hashmapintentry = null; (hashmapintentry = hashmapint.nextEntry(hashmapintentry)) != null;)
        {
            com.maddox.rts.NetObj netobj = (com.maddox.rts.NetObj)hashmapintentry.getValue();
            if(netobj != null && !netobj.isDestroyed() && !netobj.isCommon() && !netchannel.isMirrored(netobj) && netobj.masterChannel() != netchannel && ((!(netchannel instanceof com.maddox.rts.NetChannelOutStream) || !(netobj instanceof com.maddox.rts.NetControl) && (!(netobj instanceof com.maddox.il2.net.NetUser) || !netobj.isMaster() || !com.maddox.il2.net.NetMissionTrack.isPlaying())) && (!(netobj instanceof com.maddox.il2.game.GameTrack) || !netobj.isMirror())))
            {
                java.lang.Object obj1 = netobj.superObj();
                if(!(obj1 instanceof com.maddox.rts.Destroy) || !((com.maddox.rts.Destroy)obj1).isDestroyed())
                {
                    (new MsgInvokeMethod_Object("trySendMsgStart", netchannel)).post(72, this, 0.0D);
                    return;
                }
            }
        }

        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(12);
            net.postTo(netchannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.game.Mission.printDebug(exception);
        }
    }

    private void createNetObject(com.maddox.rts.NetChannel netchannel, int i)
    {
        setTime(true);
        if(netchannel == null)
        {
            net = new Master(this);
            doReplicateNotMissionActors(false);
        } else
        {
            net = new Mirror(this, netchannel, i);
            doReplicateNotMissionActors(netchannel, false);
        }
    }

    protected static void printDebug(java.lang.Exception exception)
    {
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
    }

    private int[] getSwTbl(java.lang.String s, long l)
    {
        int i = (int)l;
        int j = com.maddox.rts.Finger.Int(s);
        if(i < 0)
            i = -i;
        if(j < 0)
            j = -j;
        int k = (j + i / 7) % 16 + 15;
        int i1 = (j + i / 21) % com.maddox.rts.Finger.kTable.length;
        if(k < 0)
            k = -k % 16;
        if(k < 10)
            k = 10;
        if(i1 < 0)
            i1 = -i1 % com.maddox.rts.Finger.kTable.length;
        int ai[] = new int[k];
        for(int j1 = 0; j1 < k; j1++)
            ai[j1] = com.maddox.rts.Finger.kTable[(i1 + j1) % com.maddox.rts.Finger.kTable.length];

        return ai;
    }

    public java.util.ArrayList getAllActors()
    {
        return actors;
    }

    private java.lang.String generateHayrakeCode(com.maddox.JGP.Point3d point3d)
    {
        double d = point3d.x;
        double d1 = point3d.y;
        long l = (long)(d + d1);
        java.util.Random random = new Random(l);
        byte abyte0[] = new byte[12];
label0:
        for(int i = 0; i < abyte0.length; i++)
        {
            boolean flag = false;
            do
            {
                if(flag)
                    continue label0;
                byte byte0 = (byte)(random.nextInt(26) + 65);
                if(byte0 != 74 && byte0 != 81 && byte0 != 89 && byte0 <= 90)
                {
                    int j = 0;
                    while(j < abyte0.length && byte0 != abyte0[j]) 
                    {
                        if(j == abyte0.length - 1)
                        {
                            flag = true;
                            abyte0[i] = byte0;
                        }
                        j++;
                    }
                }
            } while(true);
        }

        java.lang.String s = new String(abyte0);
        return s;
    }

    private void populateRunwayLights()
    {
        java.util.ArrayList arraylist = new ArrayList();
        com.maddox.il2.ai.World.getAirports(arraylist);
        for(int i = 0; i < arraylist.size(); i++)
        {
            if(!(arraylist.get(i) instanceof com.maddox.il2.ai.AirportGround))
                continue;
            for(int k = 0; k < actors.size(); k++)
            {
                if(!(actors.get(k) instanceof com.maddox.il2.objects.vehicles.stationary.SmokeGeneric) || !(actors.get(k) instanceof com.maddox.il2.objects.vehicles.stationary.Smoke.Smoke15) && !(actors.get(k) instanceof com.maddox.il2.objects.vehicles.stationary.Smoke.Smoke14) && !(actors.get(k) instanceof com.maddox.il2.objects.vehicles.stationary.Smoke.Smoke13) && !(actors.get(k) instanceof com.maddox.il2.objects.vehicles.stationary.Smoke.Smoke12))
                    continue;
                com.maddox.il2.ai.AirportGround airportground = (com.maddox.il2.ai.AirportGround)arraylist.get(i);
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)actors.get(k);
                double d = airportground.pos.getAbsPoint().x - actor.pos.getAbsPoint().x;
                double d1 = airportground.pos.getAbsPoint().y - actor.pos.getAbsPoint().y;
                if(java.lang.Math.abs(d) < 2000D && java.lang.Math.abs(d1) < 2000D && (actor.getArmy() == 1 || actor.getArmy() == 2))
                {
                    com.maddox.il2.objects.vehicles.stationary.SmokeGeneric smokegeneric = (com.maddox.il2.objects.vehicles.stationary.SmokeGeneric)actor;
                    smokegeneric.setVisible(false);
                    airportground.addLights(smokegeneric);
                }
            }

        }

        for(int j = 0; j < actors.size(); j++)
        {
            if(actors.get(j) instanceof com.maddox.il2.objects.vehicles.stationary.SmokeGeneric)
            {
                ((com.maddox.il2.objects.vehicles.stationary.SmokeGeneric)actors.get(j)).setArmy(0);
                continue;
            }
            if((actors.get(j) instanceof com.maddox.il2.objects.ships.Ship.RwyTransp) || (actors.get(j) instanceof com.maddox.il2.objects.ships.Ship.RwyTranspWide) || (actors.get(j) instanceof com.maddox.il2.objects.ships.Ship.RwyTranspSqr))
                ((com.maddox.il2.objects.ships.BigshipGeneric)actors.get(j)).hideTransparentRunwayRed();
        }

    }

    private void populateBeacons()
    {
        if(!com.maddox.il2.ai.World.cur().diffCur.RealisticNavigationInstruments)
        {
            for(int i = 0; i < actors.size(); i++)
                if(actors.get(i) instanceof com.maddox.il2.objects.vehicles.radios.TypeHasLorenzBlindLanding)
                    ((com.maddox.il2.engine.Actor)actors.get(i)).missionStarting();

            return;
        }
        java.util.ArrayList arraylist = new ArrayList();
        java.util.ArrayList arraylist1 = new ArrayList();
        for(int j = 0; j < actors.size(); j++)
        {
            if(actors.get(j) instanceof com.maddox.il2.objects.vehicles.radios.TypeHasBeacon)
            {
                com.maddox.JGP.Point3d point3d = ((com.maddox.il2.engine.Actor)actors.get(j)).pos.getAbsPoint();
                arraylist.add(((java.lang.Object) (new java.lang.Object[] {
                    actors.get(j), point3d
                })));
                if(actors.get(j) instanceof com.maddox.il2.objects.vehicles.radios.TypeHasLorenzBlindLanding)
                    ((com.maddox.il2.engine.Actor)actors.get(j)).missionStarting();
                if(actors.get(j) instanceof com.maddox.il2.objects.ships.BigshipGeneric)
                    hayrakeMap.put((com.maddox.il2.engine.Actor)actors.get(j), "NDB");
                continue;
            }
            if(actors.get(j) instanceof com.maddox.il2.objects.vehicles.radios.TypeHasMeacon)
            {
                com.maddox.JGP.Point3d point3d1 = ((com.maddox.il2.engine.Actor)actors.get(j)).pos.getAbsPoint();
                arraylist1.add(((java.lang.Object) (new java.lang.Object[] {
                    actors.get(j), point3d1
                })));
                continue;
            }
            if(actors.get(j) instanceof com.maddox.il2.objects.vehicles.radios.TypeHasHayRake)
            {
                com.maddox.JGP.Point3d point3d2 = ((com.maddox.il2.engine.Actor)actors.get(j)).pos.getAbsPoint();
                java.lang.String s = generateHayrakeCode(point3d2);
                arraylist.add(((java.lang.Object) (new java.lang.Object[] {
                    actors.get(j), point3d2
                })));
                hayrakeMap.put((com.maddox.il2.engine.Actor)actors.get(j), s);
            }
        }

        if(arraylist.size() == 0)
            return;
        sortBeaconsList(arraylist);
        for(int k = 0; k < arraylist.size(); k++)
        {
            java.lang.Object aobj[] = (java.lang.Object[])(java.lang.Object[])arraylist.get(k);
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)aobj[0];
            if(((actor instanceof com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation) || actor.getArmy() == 1) && beaconsRed.size() < 32)
                beaconsRed.add(aobj[0]);
            if(((actor instanceof com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation) || actor.getArmy() == 2) && beaconsBlue.size() < 32)
                beaconsBlue.add(aobj[0]);
        }

        for(int l = 0; l < arraylist1.size(); l++)
        {
            java.lang.Object aobj1[] = (java.lang.Object[])(java.lang.Object[])arraylist1.get(l);
            com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)aobj1[0];
            if(actor1.getArmy() == 1 && meaconsRed.size() < 32)
            {
                meaconsRed.add(aobj1[0]);
                continue;
            }
            if(actor1.getArmy() == 2 && meaconsBlue.size() < 32)
                meaconsBlue.add(aobj1[0]);
        }

        arraylist.clear();
        arraylist = null;
        arraylist1.clear();
        arraylist1 = null;
    }

    public static void addHayrakesToOrdersTree()
    {
        for(int i = 0; i < 10; i++)
            com.maddox.il2.game.Main3D.cur3D().ordersTree.addShipIDs(i, -1, null, "", "");

        if(!com.maddox.il2.ai.World.cur().diffCur.RealisticNavigationInstruments)
            return;
        int j = 0;
        java.util.Iterator iterator = hayrakeMap.entrySet().iterator();
        do
        {
            if(!iterator.hasNext() || j >= 10)
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
            if(actor.getArmy() != com.maddox.il2.ai.World.getPlayerArmy())
                continue;
            java.lang.String s = (java.lang.String)entry.getValue();
            java.lang.String s1 = com.maddox.rts.Property.stringValue(actor.getClass(), "i18nName", "");
            int k = -1;
            if(beaconsRed.contains(actor))
                k = beaconsRed.indexOf(actor);
            else
            if(beaconsBlue.contains(actor))
                k = beaconsBlue.indexOf(actor);
            if(s.equals("NDB"))
            {
                com.maddox.il2.game.Main3D.cur3D().ordersTree.addShipIDs(j, k, actor, s1, "");
            } else
            {
                boolean flag = com.maddox.il2.objects.air.Aircraft.hasPlaneZBReceiver(com.maddox.il2.ai.World.getPlayerAircraft());
                if(!flag)
                    continue;
                java.lang.String s2 = s;
                if(s.length() == 12)
                    s2 = s.substring(0, 3) + " / " + s.substring(3, 6) + " / " + s.substring(6, 9) + " / " + s.substring(9, 12);
                else
                if(s.length() == 24)
                    s2 = s.substring(0, 2) + "-" + s.substring(2, 4) + "-" + s.substring(4, 6) + " / " + s.substring(6, 8) + "-" + s.substring(8, 10) + "-" + s.substring(10, 12) + " / " + s.substring(12, 14) + "-" + s.substring(14, 16) + "-" + s.substring(16, 18) + " / " + s.substring(18, 20) + "-" + s.substring(20, 22) + "-" + s.substring(22, 24);
                com.maddox.il2.game.Main3D.cur3D().ordersTree.addShipIDs(j, k, actor, s1, "( " + s2 + " )");
            }
            j++;
        } while(true);
    }

    private void sortBeaconsList(java.util.List list)
    {
        boolean flag = false;
        do
        {
            for(int i = 0; i < list.size() - 1; i++)
            {
                flag = false;
                java.lang.Object aobj[] = (java.lang.Object[])(java.lang.Object[])list.get(i);
                java.lang.Object aobj1[] = (java.lang.Object[])(java.lang.Object[])list.get(i + 1);
                if((aobj[0] instanceof com.maddox.il2.objects.vehicles.radios.TypeHasHayRake) && !(aobj1[0] instanceof com.maddox.il2.objects.vehicles.radios.TypeHasHayRake) || (aobj[0] instanceof com.maddox.il2.objects.ships.BigshipGeneric) && !(aobj1[0] instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                {
                    java.lang.Object aobj2[] = aobj;
                    list.set(i, ((java.lang.Object) (aobj1)));
                    list.set(i + 1, ((java.lang.Object) (aobj2)));
                    flag = true;
                }
            }

        } while(flag);
    }

    public boolean hasBeacons(int i)
    {
        if(i == 1)
            return beaconsRed.size() > 0;
        if(i == 2)
            return beaconsBlue.size() > 0;
        else
            return false;
    }

    public java.util.ArrayList getBeacons(int i)
    {
        if(i == 1)
            return beaconsRed;
        if(i == 2)
            return beaconsBlue;
        else
            return null;
    }

    public java.util.ArrayList getMeacons(int i)
    {
        if(i == 1)
            return meaconsBlue;
        if(i == 2)
            return meaconsRed;
        else
            return null;
    }

    public java.lang.String getHayrakeCodeOfCarrier(com.maddox.il2.engine.Actor actor)
    {
        if(hayrakeMap.containsKey(actor))
            return (java.lang.String)hayrakeMap.get(actor);
        else
            return null;
    }

    private void zutiAssignBpToMovingCarrier()
    {
        for(int i = 0; i < actors.size(); i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)com.maddox.il2.game.Main.cur().mission.actors.get(i);
            if(!(actor instanceof com.maddox.il2.objects.ships.BigshipGeneric) || actor.name().indexOf("_Chief") <= -1 || actor.toString().indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_STRING[0]) <= -1 && actor.toString().indexOf(com.maddox.il2.objects.ships.BigshipGeneric.ZUTI_CARRIER_STRING[1]) <= -1)
                continue;
            com.maddox.il2.objects.ships.BigshipGeneric bigshipgeneric = (com.maddox.il2.objects.ships.BigshipGeneric)actor;
            if(actor.icon != null || com.maddox.il2.game.Main.cur().netServerParams.isMaster())
                bigshipgeneric.zutiAssignBornPlace();
        }

    }

    private void zutiResetMissionVariables()
    {
        if(com.maddox.il2.game.ZutiSupportMethods.ZUTI_BANNED_PILOTS == null)
            com.maddox.il2.game.ZutiSupportMethods.ZUTI_BANNED_PILOTS = new ArrayList();
        com.maddox.il2.game.ZutiSupportMethods.ZUTI_BANNED_PILOTS.clear();
        if(com.maddox.il2.game.ZutiSupportMethods.ZUTI_DEAD_TARGETS == null)
            com.maddox.il2.game.ZutiSupportMethods.ZUTI_DEAD_TARGETS = new ArrayList();
        com.maddox.il2.game.ZutiSupportMethods.ZUTI_DEAD_TARGETS.clear();
        if(com.maddox.il2.gui.GUI.pad != null)
            com.maddox.il2.gui.GUI.pad.zutiColorAirfields = true;
        com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_COUNTER = 0;
        com.maddox.il2.game.ZutiSupportMethods.ZUTI_KIA_DELAY_CLEARED = false;
        zutiCarrierSpawnPoints_CV2 = 6;
        zutiCarrierSpawnPoints_CV9 = 5;
        zutiCarrierSpawnPoints_CVE = 2;
        zutiCarrierSpawnPoints_CVL = 7;
        zutiCarrierSpawnPoints_Akagi = 8;
        zutiCarrierSpawnPoints_IJN = 6;
        zutiCarrierSpawnPoints_HMS = 5;
        zutiRadar_PlayerSideHasRadars = false;
        zutiRadar_ShipsAsRadar = false;
        zutiRadar_ShipRadar_MaxRange = 100;
        zutiRadar_ShipRadar_MinHeight = 100;
        zutiRadar_ShipRadar_MaxHeight = 5000;
        zutiRadar_ShipSmallRadar_MaxRange = 25;
        zutiRadar_ShipSmallRadar_MinHeight = 0;
        zutiRadar_ShipSmallRadar_MaxHeight = 2000;
        zutiRadar_ScoutsAsRadar = false;
        zutiRadar_ScoutRadar_MaxRange = 2;
        zutiRadar_ScoutRadar_DeltaHeight = 1500;
        zutiRadar_RefreshInterval = 0;
        zutiRadar_EnableTowerCommunications = true;
        zutiRadar_HideUnpopulatedAirstripsFromMinimap = false;
        ZUTI_RADAR_IN_ADV_MODE = false;
        zutiRadar_ScoutGroundObjects_Alpha = 5;
        ScoutsRed = new ArrayList();
        ScoutsBlue = new ArrayList();
        zutiRadar_ScoutCompleteRecon = false;
        zutiRadar_EnableBigShip_Radar = true;
        zutiRadar_EnableSmallShip_Radar = true;
        zutiMisc_DisableAIRadioChatter = false;
        zutiMisc_DespawnAIPlanesAfterLanding = true;
        zutiMisc_HidePlayersCountOnHomeBase = false;
        zutiMisc_EnableReflyOnlyIfBailedOrDied = false;
        zutiMisc_DisableReflyForMissionDuration = false;
        zutiMisc_ReflyKIADelay = 0;
        zutiMisc_MaxAllowedKIA = 0x7fffffff;
        zutiMisc_ReflyKIADelayMultiplier = 0.0F;
        if(com.maddox.il2.game.Main.cur().netServerParams.reflyKIADelay > 0)
        {
            zutiMisc_EnableReflyOnlyIfBailedOrDied = true;
            zutiMisc_ReflyKIADelay = com.maddox.il2.game.Main.cur().netServerParams.reflyKIADelay;
        }
        if(com.maddox.il2.game.Main.cur().netServerParams.reflyDisabled)
            zutiMisc_DisableReflyForMissionDuration = true;
        else
        if(com.maddox.il2.game.Main.cur().netServerParams.maxAllowedKIA >= 0)
        {
            zutiMisc_EnableReflyOnlyIfBailedOrDied = true;
            zutiMisc_MaxAllowedKIA = com.maddox.il2.game.Main.cur().netServerParams.maxAllowedKIA;
        }
        if(com.maddox.il2.game.Main.cur().netServerParams.reflyKIADelayMultiplier > 0.0F && zutiMisc_ReflyKIADelay != 0)
        {
            zutiMisc_EnableReflyOnlyIfBailedOrDied = true;
            zutiMisc_ReflyKIADelayMultiplier = com.maddox.il2.game.Main.cur().netServerParams.reflyKIADelayMultiplier;
        }
        zutiMisc_BombsCat1_CratersVisibilityMultiplier = 1.0F;
        zutiMisc_BombsCat2_CratersVisibilityMultiplier = 1.0F;
        zutiMisc_BombsCat3_CratersVisibilityMultiplier = 1.0F;
    }

    private void zutiLoadBornPlaceCountries(com.maddox.il2.net.BornPlace bornplace, com.maddox.rts.SectFile sectfile, int i)
    {
        if(bornplace == null)
            return;
        if(bornplace != null && bornplace.zutiHomeBaseCountries == null)
            bornplace.zutiHomeBaseCountries = new ArrayList();
        bornplace.zutiLoadAllCountries();
        int j = sectfile.sectionIndex("BornPlaceCountries" + i);
        if(j >= 0)
        {
            bornplace.zutiHomeBaseCountries.clear();
            java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
            int k = sectfile.vars(j);
            for(int l = 0; l < k; l++)
                try
                {
                    java.lang.String s = sectfile.var(j, l);
                    java.lang.String s1 = resourcebundle.getString(s);
                    if(!bornplace.zutiHomeBaseCountries.contains(s1))
                        bornplace.zutiHomeBaseCountries.add(s1);
                }
                catch(java.lang.Exception exception) { }

        }
    }

    private void zutiLoadScouts_Red(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("MDS_Scouts_Red");
        if(i > -1)
        {
            if(com.maddox.il2.game.Main.cur().mission.ScoutsRed == null)
                com.maddox.il2.game.Main.cur().mission.ScoutsRed = new ArrayList();
            com.maddox.il2.game.Main.cur().mission.ScoutsRed.clear();
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
            {
                java.lang.String s = sectfile.line(i, k);
                java.util.StringTokenizer stringtokenizer = new StringTokenizer(s);
                java.lang.String s1 = null;
                if(stringtokenizer.hasMoreTokens())
                    s1 = stringtokenizer.nextToken();
                if(s1 == null)
                    continue;
                s1 = s1.intern();
                java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(s1, "airClass", null);
                if(class1 != null && com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                    com.maddox.il2.game.Main.cur().mission.ScoutsRed.add(s1);
            }

        }
    }

    private void zutiLoadScouts_Blue(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("MDS_Scouts_Blue");
        if(i > -1)
        {
            if(com.maddox.il2.game.Main.cur().mission.ScoutsBlue == null)
                com.maddox.il2.game.Main.cur().mission.ScoutsBlue = new ArrayList();
            com.maddox.il2.game.Main.cur().mission.ScoutsBlue.clear();
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
            {
                java.lang.String s = sectfile.line(i, k);
                java.util.StringTokenizer stringtokenizer = new StringTokenizer(s);
                java.lang.String s1 = null;
                if(stringtokenizer.hasMoreTokens())
                    s1 = stringtokenizer.nextToken();
                if(s1 == null)
                    continue;
                s1 = s1.intern();
                java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(s1, "airClass", null);
                if(class1 != null && com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                    com.maddox.il2.game.Main.cur().mission.ScoutsBlue.add(s1);
            }

        }
    }

    private void zutiSetShipRadars()
    {
        if(zutiRadar_ShipRadar_MaxHeight == 0 && zutiRadar_ShipRadar_MaxRange == 0 && zutiRadar_ShipRadar_MinHeight == 0)
            zutiRadar_EnableBigShip_Radar = false;
        if(zutiRadar_ShipSmallRadar_MaxHeight == 0 && zutiRadar_ShipSmallRadar_MaxRange == 0 && zutiRadar_ShipSmallRadar_MinHeight == 0)
            zutiRadar_EnableSmallShip_Radar = false;
    }

    public static int getMissionDate(boolean flag)
    {
        int i = 0;
        if(com.maddox.il2.game.Main.cur().mission == null)
        {
            com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Main.cur().currentMissionFile;
            if(sectfile == null)
                return 0;
            java.lang.String s = sectfile.get("MAIN", "MAP");
            int l = com.maddox.il2.ai.World.land().config.getDefaultMonth("maps/" + s);
            int j1 = sectfile.get("SEASON", "Year", 1940, 1930, 1960);
            int k1 = sectfile.get("SEASON", "Month", l, 1, 12);
            int l1 = sectfile.get("SEASON", "Day", 15, 1, 31);
            i = j1 * 10000 + k1 * 100 + l1;
            int j2 = 0x1280540 + l * 100 + 15;
            if(flag && i == j2)
                i = 0;
        } else
        {
            int j = com.maddox.il2.game.Mission.curYear();
            int k = com.maddox.il2.game.Mission.curMonth();
            int i1 = com.maddox.il2.game.Mission.curDay();
            i = j * 10000 + k * 100 + i1;
            if(flag)
            {
                com.maddox.rts.SectFile sectfile1 = com.maddox.il2.game.Main.cur().currentMissionFile;
                if(sectfile1 == null)
                    return 0;
                java.lang.String s1 = sectfile1.get("MAIN", "MAP");
                int i2 = com.maddox.il2.ai.World.land().config.getDefaultMonth("maps/" + s1);
                int k2 = 0x1280540 + i2 * 100 + 15;
                if(i == k2)
                    i = 0;
            }
        }
        return i;
    }

    public static float BigShipHpDiv()
    {
        if(com.maddox.il2.game.Main.cur().mission == null)
            return 1.0F;
        else
            return com.maddox.il2.game.Main.cur().mission.bigShipHpDiv;
    }

    public static final java.lang.String DIR = "missions/";
    public static final java.lang.String DIRNET = "missions/Net/Cache/";
    public static final float CLOUD_HEIGHT = 8000F;
    private java.lang.String name;
    private com.maddox.rts.SectFile sectFile;
    private long sectFinger;
    private java.util.ArrayList actors;
    private int curActor;
    private boolean bPlaying;
    private int curCloudsType;
    private float curCloudsHeight;
    protected static int viewSet = 0;
    protected static int iconTypes = 0;
    private static java.util.HashMap respawnMap = new HashMap();
    private int curYear;
    private int curMonth;
    private int curDay;
    private float curWindDirection;
    private float curWindVelocity;
    private float curGust;
    private float curTurbulence;
    private static java.util.ArrayList beaconsRed = new ArrayList();
    private static java.util.ArrayList beaconsBlue = new ArrayList();
    private static java.util.ArrayList meaconsRed = new ArrayList();
    private static java.util.ArrayList meaconsBlue = new ArrayList();
    private static java.util.Map hayrakeMap = new HashMap();
    private final int HAYRAKE_CODE_LENGTH = 12;
    public static boolean hasRadioStations = false;
    private static boolean radioStationsLoaded = false;
    private float bigShipHpDiv;
    private java.lang.String player;
    private boolean _loadPlayer;
    private int playerNum;
    private java.util.HashMap mapWingTakeoff;
    private static com.maddox.rts.SectFile chiefsIni;
    private static com.maddox.JGP.Point3d Loc = new Point3d();
    private static com.maddox.il2.engine.Orient Or = new Orient();
    private static com.maddox.JGP.Vector3f Spd = new Vector3f();
    private static com.maddox.JGP.Vector3d Spdd = new Vector3d();
    private static com.maddox.il2.engine.ActorSpawnArg spawnArg = new ActorSpawnArg();
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    public static final int NET_MSG_ID_NAME = 0;
    public static final int NET_MSG_ID_BODY = 1;
    public static final int NET_MSG_ID_BODY_END = 2;
    public static final int NET_MSG_ID_ACTORS = 3;
    public static final int NET_MSG_ID_ACTORS_END = 4;
    public static final int NET_MSG_ID_LOADED = 5;
    public static final int NET_MSG_ID_BEGIN = 10;
    public static final int NET_MSG_ID_TOD = 11;
    public static final int NET_MSG_ID_START = 12;
    public static final int NET_MSG_ID_TIME = 13;
    public static final int NET_MSG_ID_END = 20;
    protected com.maddox.rts.NetObj net;
    public int zutiCarrierSpawnPoints_CV2;
    public int zutiCarrierSpawnPoints_CV9;
    public int zutiCarrierSpawnPoints_CVE;
    public int zutiCarrierSpawnPoints_CVL;
    public int zutiCarrierSpawnPoints_Akagi;
    public int zutiCarrierSpawnPoints_IJN;
    public int zutiCarrierSpawnPoints_HMS;
    public final boolean zutiTargets_MovingIcons = true;
    public final boolean zutiTargets_ShowTargets = true;
    public final boolean zutiIcons_ShowNeutralHB = true;
    public final boolean zutiRadar_ShowAircraft = true;
    public final boolean zutiRadar_ShowGroundUnits = true;
    public final boolean zutiRadar_StaticIconsIfNoRadar = true;
    public boolean zutiRadar_PlayerSideHasRadars;
    public int zutiRadar_RefreshInterval;
    public final boolean zutiRadar_AircraftIconsWhite = false;
    public boolean zutiRadar_HideUnpopulatedAirstripsFromMinimap;
    public boolean zutiRadar_EnableTowerCommunications;
    public boolean zutiRadar_ShipsAsRadar;
    public int zutiRadar_ShipRadar_MaxRange;
    public int zutiRadar_ShipRadar_MinHeight;
    public int zutiRadar_ShipRadar_MaxHeight;
    public int zutiRadar_ShipSmallRadar_MaxRange;
    public int zutiRadar_ShipSmallRadar_MinHeight;
    public int zutiRadar_ShipSmallRadar_MaxHeight;
    public boolean zutiRadar_ScoutsAsRadar;
    public int zutiRadar_ScoutRadar_MaxRange;
    public int zutiRadar_ScoutRadar_DeltaHeight;
    public java.util.ArrayList ScoutsRed;
    public java.util.ArrayList ScoutsBlue;
    public int zutiRadar_ScoutGroundObjects_Alpha;
    public final boolean zutiRadar_ShowRockets = true;
    public boolean zutiRadar_EnableBigShip_Radar;
    public boolean zutiRadar_EnableSmallShip_Radar;
    public boolean zutiRadar_ScoutCompleteRecon;
    public boolean zutiRadar_DisableVectoring;
    public static boolean ZUTI_RADAR_IN_ADV_MODE = false;
    private static int ZUTI_ICON_SIZES[] = {
        8, 12, 16, 20, 24, 28, 32
    };
    public static int ZUTI_ICON_SIZE;
    public boolean zutiMisc_DisableAIRadioChatter;
    public boolean zutiMisc_DespawnAIPlanesAfterLanding;
    public boolean zutiMisc_HidePlayersCountOnHomeBase;
    public boolean zutiMisc_EnableReflyOnlyIfBailedOrDied;
    public boolean zutiMisc_DisableReflyForMissionDuration;
    public int zutiMisc_ReflyKIADelay;
    public int zutiMisc_MaxAllowedKIA;
    public float zutiMisc_ReflyKIADelayMultiplier;
    public float zutiMisc_BombsCat1_CratersVisibilityMultiplier;
    public float zutiMisc_BombsCat2_CratersVisibilityMultiplier;
    public float zutiMisc_BombsCat3_CratersVisibilityMultiplier;

    static 
    {
        com.maddox.rts.Spawn.add(com.maddox.il2.game.Mission.class, new SPAWN());
        ZUTI_ICON_SIZE = ZUTI_ICON_SIZES[4];
    }







}
