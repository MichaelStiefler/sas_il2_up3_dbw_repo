// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetServerParams.java

package com.maddox.il2.net;

import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.gui.GUINetServerCMission;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetGunner;
import com.maddox.rts.CLASS;
import com.maddox.rts.CfgInt;
import com.maddox.rts.Finger;
import com.maddox.rts.IniFile;
import com.maddox.rts.MainWin32;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.Property;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

// Referenced classes of package com.maddox.il2.net:
//            NetUser, NetMaxLag, NetMissionTrack, USGS, 
//            GameSpy, NetMissionListener

public class NetServerParams extends com.maddox.rts.NetObj
    implements com.maddox.rts.NetUpdate
{
    private class CheckUser
    {

        public boolean checkInput(int i, com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            boolean flag = false;
            switch(i)
            {
            case 8: // '\b'
                if(checkKey == 0)
                    checkKey = checkFirst(checkPublicKey);
                flag = checkKey == netmsginput.readInt();
                if(flag)
                    state++;
                break;

            case 9: // '\t'
                int j = 0;
                if(checkRuntime == 2)
                    j = publicKey;
                flag = netmsginput.readInt() == checkSecond(publicKey, j);
                if(flag)
                    flag = netmsginput.readInt() == checkSecond2;
                if(flag)
                    state++;
                break;

            case 10: // '\n'
                com.maddox.il2.objects.air.Aircraft aircraft = user.findAircraft();
                if(com.maddox.il2.engine.Actor.isValid(aircraft))
                {
                    int k = com.maddox.rts.Finger.incInt(publicKey, diff);
                    flag = netmsginput.readInt() == (int)aircraft.finger(k) + com.maddox.rts.SFSInputStream.oo;
                    if(flag)
                        classAircraft = aircraft.getClass();
                    else
                        classAircraft = null;
                } else
                {
                    classAircraft = null;
                    flag = true;
                }
                break;

            default:
                return false;
            }
            timeSended = 0L;
            if(!flag)
            {
                com.maddox.rts.NetChannel netchannel = netmsginput.channel();
                if(!netchannel.isDestroying())
                {
                    java.lang.String s = "Timeout ";
                    s = s + (i - 8);
                    netchannel.destroy(s);
                }
            }
            return true;
        }

        public void checkUpdate(long l)
        {
            if(state > 10)
                return;
            if(timeSended != 0L)
            {
                if(l < timeSended + 0x249f0L)
                    return;
                com.maddox.rts.NetChannel netchannel = user.masterChannel();
                if(!netchannel.isDestroying())
                {
                    java.lang.String s = "Timeout .";
                    s = s + (state - 8);
                    netchannel.destroy(s);
                }
                return;
            }
            try
            {
                int i = 0;
                switch(state)
                {
                case 8: // '\b'
                    i = checkPublicKey;
                    break;

                case 9: // '\t'
                    i = publicKey = (int)(java.lang.Math.random() * 4294967295D);
                    break;

                case 10: // '\n'
                    com.maddox.il2.objects.air.Aircraft aircraft = user.findAircraft();
                    if(com.maddox.il2.engine.Actor.isValid(aircraft) && !aircraft.getClass().equals(classAircraft))
                    {
                        i = publicKey = (int)(java.lang.Math.random() * 4294967295D);
                        diff = com.maddox.il2.ai.World.cur().diffCur.get();
                    }
                    break;
                }
                if(i != 0)
                {
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                    netmsgguaranted.writeByte(state);
                    netmsgguaranted.writeNetObj(user);
                    netmsgguaranted.writeInt(i);
                    if(state == 9)
                        if(checkRuntime == 2)
                            netmsgguaranted.writeInt(i);
                        else
                            netmsgguaranted.writeInt(0);
                    postTo(user.masterChannel(), netmsgguaranted);
                    timeSended = l;
                }
            }
            catch(java.lang.Exception exception) { }
        }

        public com.maddox.il2.net.NetUser user;
        public int state;
        public long timeSended;
        public java.lang.Class classAircraft;
        public int publicKey;
        public int diff;

        public CheckUser(com.maddox.il2.net.NetUser netuser)
        {
            state = 8;
            timeSended = 0L;
            classAircraft = null;
            publicKey = 0;
            user = netuser;
        }
    }

    static class SPAWN
        implements com.maddox.rts.NetSpawn
    {

        public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            try
            {
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                int j = netmsginput.readInt();
                int k = netmsginput.readInt();
                byte byte0 = netmsginput.readByte();
                java.lang.String s = netmsginput.read255();
                com.maddox.il2.net.NetServerParams netserverparams = new NetServerParams(netmsginput.channel(), i, (com.maddox.rts.NetHost)netobj);
                netserverparams.flags = j;
                netserverparams.difficulty = k;
                netserverparams.maxUsers = byte0;
                netserverparams.serverName = s;
                netserverparams.autoLogDetail = netmsginput.readByte();
                netserverparams.farMaxLagTime = netmsginput.readFloat();
                netserverparams.nearMaxLagTime = netmsginput.readFloat();
                netserverparams.cheaterWarningDelay = netmsginput.readFloat();
                netserverparams.cheaterWarningNum = netmsginput.readInt();
                if(netmsginput.channel() instanceof com.maddox.rts.NetChannelInStream)
                {
                    netserverparams.difficulty = com.maddox.il2.ai.World.cur().diffCur.get();
                    if(netmsginput.available() >= 8)
                        netserverparams.serverDeltaTime = netmsginput.readLong();
                    else
                        netserverparams.serverDeltaTime = 0L;
                } else
                {
                    com.maddox.il2.ai.World.cur().diffCur.set(k);
                }
                netserverparams.synkExtraOcclusion();
                if(netmsginput.available() >= 4)
                    netserverparams.eventlogClient = netmsginput.readInt();
                else
                    netserverparams.eventlogClient = -1;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }

        SPAWN()
        {
        }
    }


    public static long getServerTime()
    {
        if(com.maddox.il2.net.NetMissionTrack.isPlaying())
            if(com.maddox.il2.game.Main.cur() != null && com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            {
                long l = com.maddox.rts.Time.current() - com.maddox.il2.game.Main.cur().netServerParams.serverDeltaTime;
                if(l < 0L)
                    l = 0L;
                if(l > com.maddox.il2.game.Main.cur().netServerParams.lastServerTime)
                    com.maddox.il2.game.Main.cur().netServerParams.lastServerTime = l;
                return com.maddox.il2.game.Main.cur().netServerParams.lastServerTime;
            } else
            {
                return com.maddox.rts.Time.current();
            }
        if(com.maddox.il2.game.Main.cur() != null && com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isCoop() && com.maddox.il2.game.Main.cur().netServerParams.isMirror() && !com.maddox.rts.Time.isPaused() && com.maddox.il2.game.Main.cur().netServerParams.serverClockOffset0 != 0L)
        {
            long l1 = com.maddox.il2.game.Main.cur().netServerParams.masterChannel().remoteClockOffset();
            long l2 = com.maddox.rts.Time.current() - (l1 - com.maddox.il2.game.Main.cur().netServerParams.serverClockOffset0);
            if(l2 < 0L)
                l2 = 0L;
            if(l2 > com.maddox.il2.game.Main.cur().netServerParams.lastServerTime)
                com.maddox.il2.game.Main.cur().netServerParams.lastServerTime = l2;
            return com.maddox.il2.game.Main.cur().netServerParams.lastServerTime;
        } else
        {
            return com.maddox.rts.Time.current();
        }
    }

    public com.maddox.rts.NetHost host()
    {
        return host;
    }

    public boolean isDedicated()
    {
        return (flags & 8) != 0;
    }

    public boolean isBBGC()
    {
        return (flags & 0x30) == 16;
    }

    public boolean isGAMESPY()
    {
        return (flags & 0x30) == 32;
    }

    public boolean isUSGS()
    {
        return (flags & 0x30) == 48;
    }

    public int getType()
    {
        return flags & 0x30;
    }

    public void setType(int i)
    {
        flags = flags & 0xffffffcf | i & 0x30;
    }

    public boolean isDogfight()
    {
        return (flags & 7) == 0;
    }

    public boolean isCoop()
    {
        return (flags & 7) == 1;
    }

    public boolean isSingle()
    {
        return (flags & 7) == 2;
    }

    public void setMode(int i)
    {
        if(!isMaster())
        {
            return;
        } else
        {
            flags = flags & -8 | i & 7;
            mirrorsUpdate();
            return;
        }
    }

    public boolean isShowSpeedBar()
    {
        return (flags & 0x1000) != 0;
    }

    public void setShowSpeedBar(boolean flag)
    {
        if(!isMaster())
            return;
        if(flag == isShowSpeedBar())
            return;
        if(flag)
            flags |= 0x1000;
        else
            flags &= 0xffffefff;
        mirrorsUpdate();
    }

    public boolean isExtraOcclusion()
    {
        return (flags & 0x2000) != 0;
    }

    public void setExtraOcclusion(boolean flag)
    {
        if(!isMaster())
            return;
        if(flag == isExtraOcclusion())
            return;
        if(flag)
            flags |= 0x2000;
        else
            flags &= 0xffffdfff;
        synkExtraOcclusion();
        mirrorsUpdate();
    }

    public void synkExtraOcclusion()
    {
        if(isDedicated())
        {
            return;
        } else
        {
            com.maddox.sound.AudioDevice.setExtraOcclusion(isExtraOcclusion());
            return;
        }
    }

    public int autoLogDetail()
    {
        return autoLogDetail;
    }

    public boolean eventlogHouse()
    {
        return eventlogHouse && isMaster();
    }

    public int eventlogClient()
    {
        return eventlogClient;
    }

    public float farMaxLagTime()
    {
        return farMaxLagTime;
    }

    public float nearMaxLagTime()
    {
        return nearMaxLagTime;
    }

    public float cheaterWarningDelay()
    {
        return cheaterWarningDelay;
    }

    public int cheaterWarningNum()
    {
        return cheaterWarningNum;
    }

    public int getDifficulty()
    {
        return difficulty;
    }

    public void setDifficulty(int i)
    {
        if(!isMaster())
        {
            return;
        } else
        {
            difficulty = i;
            com.maddox.il2.ai.World.cur().diffCur.set(difficulty);
            setClouds();
            mirrorsUpdate();
            return;
        }
    }

    public java.lang.String serverName()
    {
        return serverName;
    }

    public void setServerName(java.lang.String s)
    {
        if(com.maddox.il2.net.USGS.isUsed() && isMaster())
        {
            serverName = com.maddox.il2.net.USGS.room;
            if(serverName == null)
                serverName = "Server";
            return;
        }
        if(com.maddox.il2.game.Main.cur().netGameSpy != null)
        {
            serverName = com.maddox.il2.game.Main.cur().netGameSpy.roomName;
            return;
        } else
        {
            serverName = s;
            mirrorsUpdate();
            return;
        }
    }

    public boolean isProtected()
    {
        return (flags & 0x80) != 0;
    }

    public java.lang.String getPassword()
    {
        return serverPassword;
    }

    public void setPassword(java.lang.String s)
    {
        serverPassword = s;
        if(serverPassword != null)
            flags |= 0x80;
        else
            flags &= 0xffffff7f;
        mirrorsUpdate();
    }

    private void setClouds()
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
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
    }

    public int getMaxUsers()
    {
        return maxUsers;
    }

    public void setMaxUsers(int i)
    {
        if(!isMaster())
        {
            return;
        } else
        {
            maxUsers = i;
            mirrorsUpdate();
            return;
        }
    }

    private void mirrorsUpdate()
    {
        USGSupdate();
        if(com.maddox.il2.game.Main.cur().netGameSpy != null)
            com.maddox.il2.game.Main.cur().netGameSpy.sendStatechanged();
        if(!isMirrored())
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(0);
            netmsgguaranted.writeInt(flags);
            netmsgguaranted.writeInt(difficulty);
            netmsgguaranted.writeByte(maxUsers);
            netmsgguaranted.write255(serverName);
            post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.NetObj.printDebug(exception);
        }
    }

    public void USGSupdate()
    {
        if(!isMaster())
            return;
        if(!com.maddox.il2.net.USGS.isUsed())
        {
            return;
        } else
        {
            com.maddox.il2.net.USGS.update();
            return;
        }
    }

    public void doMissionCoopEnter()
    {
        if(isMaster())
        {
            java.util.List list = com.maddox.rts.NetEnv.hosts();
            if(list.size() == 0)
            {
                prepareHidenAircraft();
                startCoopGame();
                return;
            }
            for(int i = 0; i < list.size(); i++)
                ((com.maddox.il2.net.NetUser)list.get(i)).syncCoopStart = -1;

            bCheckStartSync = true;
            syncTime = com.maddox.rts.Time.currentReal() + 32000L;
        } else
        if(com.maddox.il2.game.Main.cur().netMissionListener != null)
            com.maddox.il2.game.Main.cur().netMissionListener.netMissionCoopEnter();
        if(isMirrored())
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(1);
                netmsgguaranted.writeByte(syncStamp);
                post(netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        if(!isMaster())
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
                netmsgguaranted1.writeByte(2);
                netmsgguaranted1.writeNetObj(com.maddox.rts.NetEnv.host());
                postTo(masterChannel(), netmsgguaranted1);
            }
            catch(java.lang.Exception exception1)
            {
                com.maddox.rts.NetObj.printDebug(exception1);
            }
    }

    public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        netmsginput.reset();
        byte byte0 = netmsginput.readByte();
        switch(byte0)
        {
        case 0: // '\0'
            int i = netmsginput.readInt();
            int j = netmsginput.readInt();
            int k = netmsginput.readByte();
            serverName = netmsginput.read255();
            flags = i;
            difficulty = j;
            maxUsers = k;
            com.maddox.il2.ai.World.cur().diffCur.set(difficulty);
            setClouds();
            synkExtraOcclusion();
            if(isMirrored())
                post(new NetMsgGuaranted(netmsginput, 0));
            break;

        case 1: // '\001'
            syncStamp = netmsginput.readUnsignedByte();
            doMissionCoopEnter();
            break;

        case 2: // '\002'
            if(isMaster())
            {
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)netmsginput.readNetObj();
                if(netuser != null)
                    netuser.syncCoopStart = syncStamp;
            } else
            {
                postTo(masterChannel(), new NetMsgGuaranted(netmsginput, 1));
            }
            break;

        case 3: // '\003'
            int l = netmsginput.readUnsignedByte();
            int j1 = netmsginput.readInt();
            if(syncStamp != l)
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
                netmsgguaranted1.writeByte(4);
                netmsgguaranted1.writeByte(l);
                netmsgguaranted1.writeNetObj(com.maddox.rts.NetEnv.host());
                postTo(masterChannel(), netmsgguaranted1);
                syncStamp = l;
                syncTime = (long)j1 + com.maddox.rts.Message.currentRealTime();
            } else
            {
                long l1 = (long)j1 + com.maddox.rts.Message.currentRealTime();
                if(syncTime > l1)
                    syncTime = l1;
            }
            if(isMirrored())
            {
                outMsgF.unLockAndClear();
                outMsgF.writeByte(3);
                outMsgF.writeByte(syncStamp);
                outMsgF.writeInt((int)(syncTime - com.maddox.rts.Time.currentReal()));
                postReal(com.maddox.rts.Time.currentReal(), outMsgF);
            }
            break;

        case 4: // '\004'
            if(isMaster())
            {
                int i1 = netmsginput.readUnsignedByte();
                com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)netmsginput.readNetObj();
                if(netuser1 == null || i1 != syncStamp)
                    break;
                netuser1.syncCoopStart = syncStamp;
                java.util.List list = com.maddox.rts.NetEnv.hosts();
                for(int k1 = 0; k1 < list.size(); k1++)
                    if(((com.maddox.il2.net.NetUser)list.get(k1)).syncCoopStart != syncStamp)
                        return true;

                bDoSync = false;
                doStartCoopGame();
            } else
            {
                postTo(masterChannel(), new NetMsgGuaranted(netmsginput, 1));
            }
            break;

        case 5: // '\005'
            doStartCoopGame();
            break;

        case 6: // '\006'
            serverDeltaTime = netmsginput.readLong();
            if(!com.maddox.il2.net.NetMissionTrack.isRecording())
                break;
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(6);
                netmsgguaranted.writeLong(serverDeltaTime);
                postTo(com.maddox.il2.net.NetMissionTrack.netChannelOut(), netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
            break;

        default:
            return checkInput(byte0, netmsginput);
        }
        return true;
    }

    public void netUpdate()
    {
        if(!com.maddox.il2.net.NetMissionTrack.isPlaying())
        {
            doCheckMaxLag();
            if(isMaster())
                checkUpdate();
        }
        if(isMirror() && isCoop() && !com.maddox.rts.Time.isPaused() && com.maddox.il2.net.NetMissionTrack.isRecording() && !com.maddox.il2.net.NetMissionTrack.isPlaying())
        {
            long l = com.maddox.rts.Time.current();
            if(l > serverDeltaTime_lastUpdate + 3000L)
            {
                serverDeltaTime_lastUpdate = l;
                try
                {
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                    netmsgguaranted.writeByte(6);
                    long l1 = com.maddox.il2.game.Main.cur().netServerParams.masterChannel().remoteClockOffset();
                    long l2 = l1 - com.maddox.il2.game.Main.cur().netServerParams.serverClockOffset0;
                    netmsgguaranted.writeLong(l2);
                    postTo(com.maddox.il2.net.NetMissionTrack.netChannelOut(), netmsgguaranted);
                }
                catch(java.lang.Exception exception1)
                {
                    com.maddox.rts.NetObj.printDebug(exception1);
                }
            }
        }
        if(!bDoSync && !bCheckStartSync)
            return;
        if(isMaster())
        {
            if(bCheckStartSync)
            {
                java.util.List list = com.maddox.rts.NetEnv.hosts();
                if(list.size() == 0)
                {
                    prepareHidenAircraft();
                    startCoopGame();
                    bCheckStartSync = false;
                    return;
                }
                if(com.maddox.rts.Time.currentReal() > syncTime)
                {
                    for(int i = 0; i < list.size(); i++)
                    {
                        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)list.get(i);
                        if(netuser.syncCoopStart != syncStamp)
                            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).kick(netuser);
                    }

                } else
                {
                    for(int j = 0; j < list.size(); j++)
                    {
                        com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)list.get(j);
                        if(netuser1.syncCoopStart != syncStamp)
                            return;
                    }

                }
                syncStamp = syncStamp + 1 & 0xff;
                syncDelta = 4000L;
                syncTime = com.maddox.rts.Time.currentReal() + syncDelta;
                bCheckStartSync = false;
                bDoSync = true;
            }
            if(com.maddox.rts.NetEnv.hosts().size() == 0)
            {
                prepareHidenAircraft();
                startCoopGame();
                bDoSync = false;
                return;
            }
            if(com.maddox.rts.Time.currentReal() > syncTime - syncDelta / 2L)
                if(syncDelta < 32000L)
                {
                    syncStamp = syncStamp + 1 & 0xff;
                    syncDelta *= 2L;
                    syncTime = com.maddox.rts.Time.currentReal() + syncDelta;
                } else
                {
                    java.util.List list1 = com.maddox.rts.NetEnv.hosts();
                    for(int k = 0; k < list1.size(); k++)
                    {
                        com.maddox.il2.net.NetUser netuser2 = (com.maddox.il2.net.NetUser)list1.get(k);
                        if(netuser2.syncCoopStart != syncStamp)
                            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).kick(netuser2);
                    }

                    bDoSync = false;
                    doStartCoopGame();
                    return;
                }
            try
            {
                outMsgF.unLockAndClear();
                outMsgF.writeByte(3);
                outMsgF.writeByte(syncStamp);
                outMsgF.writeInt((int)(syncTime - com.maddox.rts.Time.currentReal()));
                postReal(com.maddox.rts.Time.currentReal(), outMsgF);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }
    }

    public void msgNetDelChannel(com.maddox.rts.NetChannel netchannel)
    {
        netUpdate();
    }

    private void doStartCoopGame()
    {
        if(isMirrored())
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(5);
                post(netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        com.maddox.il2.game.HUD.logCoopTimeStart(syncTime);
        new com.maddox.rts.MsgAction(64, syncTime, this) {

            public void doAction(java.lang.Object obj)
            {
                if(obj != com.maddox.il2.game.Main.cur().netServerParams)
                {
                    return;
                } else
                {
                    prepareHidenAircraft();
                    startCoopGame();
                    return;
                }
            }

        }
;
    }

    private void startCoopGame()
    {
        prepareOrdersTree();
        com.maddox.il2.game.Mission.doMissionStarting();
        com.maddox.rts.Time.setPause(false);
        com.maddox.sound.AudioDevice.soundsOn();
        if(isMaster() && bNGEN)
        {
            if(timeoutNGEN > 0L)
                startTimeoutNGEN(timeoutNGEN);
            if(bLandedNGEN)
                startLandedNGEN(2000L);
        } else
        {
            if(masterChannel() != null)
                serverClockOffset0 = masterChannel().remoteClockOffset();
            else
                serverClockOffset0 = 0L;
            lastServerTime = 0L;
            serverDeltaTime_lastUpdate = 0xfffffffffffe7960L;
        }
    }

    private void startTimeoutNGEN(long l)
    {
        new com.maddox.rts.MsgAction(0, com.maddox.rts.Time.current() + l, com.maddox.il2.game.Mission.cur()) {

            public void doAction(java.lang.Object obj)
            {
                if(com.maddox.il2.game.Mission.cur() != obj)
                    return;
                if(!com.maddox.il2.game.Mission.isPlaying())
                    return;
                if(com.maddox.il2.game.Main.state().id() != 49)
                    startTimeoutNGEN(500L);
                else
                    ((com.maddox.il2.gui.GUINetServerCMission)com.maddox.il2.game.Main.state()).tryExit();
            }

        }
;
    }

    private void startLandedNGEN(long l)
    {
        new com.maddox.rts.MsgAction(0, com.maddox.rts.Time.current() + l, com.maddox.il2.game.Mission.cur()) {

            public void doAction(java.lang.Object obj)
            {
                if(com.maddox.il2.game.Mission.cur() != obj)
                    return;
                if(!com.maddox.il2.game.Mission.isPlaying())
                    return;
                if(com.maddox.il2.game.Main.state().id() != 49)
                {
                    startLandedNGEN(2000L);
                } else
                {
                    boolean flag = true;
                    for(java.util.Map.Entry entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(null); entry != null; entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(entry))
                    {
                        com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getValue();
                        if(!(actor instanceof com.maddox.il2.objects.air.Aircraft) || !com.maddox.il2.engine.Actor.isAlive(actor))
                            continue;
                        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
                        if(!aircraft.isNetPlayer())
                            continue;
                        if(!aircraft.FM.isWasAirborne())
                        {
                            flag = false;
                            break;
                        }
                        if(aircraft.FM.isStationedOnGround())
                            continue;
                        flag = false;
                        break;
                    }

                    if(flag)
                        ((com.maddox.il2.gui.GUINetServerCMission)com.maddox.il2.game.Main.state()).tryExit();
                    else
                        startLandedNGEN(2000L);
                }
            }

        }
;
    }

    public void prepareHidenAircraft()
    {
        java.util.ArrayList arraylist = new ArrayList();
        for(java.util.Map.Entry entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(null); entry != null; entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getValue();
            if((actor instanceof com.maddox.il2.objects.air.Aircraft) && actor.name().charAt(0) == ' ')
                arraylist.add(actor);
        }

        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)arraylist.get(i);
            java.lang.String s = aircraft.name().substring(1);
            if(com.maddox.il2.engine.Actor.getByName(s) != null)
            {
                aircraft.destroy();
            } else
            {
                aircraft.setName(s);
                aircraft.collide(true);
                aircraft.restoreLinksInCoopWing();
            }
        }

        if(com.maddox.il2.ai.World.isPlayerGunner())
            com.maddox.il2.ai.World.getPlayerGunner().getAircraft();
        if(isMaster())
            return;
        for(java.util.Map.Entry entry1 = com.maddox.il2.engine.Engine.name2Actor().nextEntry(null); entry1 != null; entry1 = com.maddox.il2.engine.Engine.name2Actor().nextEntry(entry1))
        {
            com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)entry1.getValue();
            if(actor1 instanceof com.maddox.il2.objects.air.Aircraft)
            {
                com.maddox.il2.objects.air.Aircraft aircraft1 = (com.maddox.il2.objects.air.Aircraft)actor1;
                if(!aircraft1.isNetPlayer() && !aircraft1.isNet())
                    arraylist.add(actor1);
            }
        }

        for(int j = 0; j < arraylist.size(); j++)
        {
            com.maddox.il2.objects.air.Aircraft aircraft2 = (com.maddox.il2.objects.air.Aircraft)arraylist.get(j);
            aircraft2.destroy();
        }

    }

    private void prepareOrdersTree()
    {
        if(com.maddox.il2.ai.World.isPlayerGunner())
            com.maddox.il2.ai.World.getPlayerGunner().getAircraft();
        else
            ((com.maddox.il2.game.Main3D)com.maddox.il2.game.Main.cur()).ordersTree.netMissionLoaded(com.maddox.il2.ai.World.getPlayerAircraft());
        if(isMirror())
            return;
        java.util.List list = com.maddox.rts.NetEnv.hosts();
        for(int i = 0; i < list.size(); i++)
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)list.get(i);
            netuser.ordersTree = new OrdersTree(false);
            netuser.ordersTree.netMissionLoaded(netuser.findAircraft());
        }

    }

    private void doCheckMaxLag()
    {
        long l = com.maddox.rts.Time.real();
        if(_lastCheckMaxLag > 0L && l - _lastCheckMaxLag < 1000L)
            return;
        _lastCheckMaxLag = l;
        if(!com.maddox.il2.game.Mission.isPlaying())
            return;
        if(isMaster())
        {
            java.util.List list = com.maddox.il2.engine.Engine.targets();
            int i = list.size();
            for(int j = 0; j < i; j++)
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
                if((actor instanceof com.maddox.il2.objects.air.Aircraft) && com.maddox.il2.engine.Actor.isAlive(actor) && !actor.net.isMaster())
                {
                    com.maddox.il2.net.NetUser netuser1 = ((com.maddox.il2.objects.air.Aircraft)actor).netUser();
                    if(netuser1 != null)
                    {
                        if(netuser1.netMaxLag == null)
                            netuser1.netMaxLag = new NetMaxLag();
                        netuser1.netMaxLag.doServerCheck((com.maddox.il2.objects.air.Aircraft)actor);
                    }
                }
            }

        } else
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            if(netuser.netMaxLag == null)
                netuser.netMaxLag = new NetMaxLag();
            netuser.netMaxLag.doClientCheck();
        }
    }

    public void destroy()
    {
        super.destroy();
        bCheckStartSync = false;
        bDoSync = false;
        com.maddox.il2.game.Main.cur().netServerParams = null;
    }

    public NetServerParams()
    {
        super(null);
        flags = 4096;
        autoLogDetail = 3;
        eventlogHouse = false;
        eventlogClient = -1;
        bNGEN = false;
        timeoutNGEN = 0L;
        bLandedNGEN = false;
        farMaxLagTime = 10F;
        nearMaxLagTime = 2.0F;
        cheaterWarningDelay = 10F;
        cheaterWarningNum = 3;
        syncStamp = 0;
        bCheckStartSync = false;
        bDoSync = false;
        serverDeltaTime = 0L;
        serverDeltaTime_lastUpdate = 0L;
        serverClockOffset0 = 0L;
        lastServerTime = 0L;
        _lastCheckMaxLag = -1L;
        checkRuntime = 0;
        checkTimeUpdate = 0L;
        checkUsers = new HashMapExt();
        checkPublicKey = 0;
        checkKey = 0;
        checkSecond2 = 0;
        host = com.maddox.rts.NetEnv.host();
        serverName = host.shortName();
        com.maddox.il2.game.Main.cur().netServerParams = this;
        outMsgF = new NetMsgFiltered();
        try
        {
            outMsgF.setIncludeTime(true);
        }
        catch(java.lang.Exception exception) { }
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            flags |= 8;
        synkExtraOcclusion();
        autoLogDetail = com.maddox.il2.engine.Config.cur.ini.get("chat", "autoLogDetail", autoLogDetail, 0, 3);
        nearMaxLagTime = com.maddox.il2.engine.Config.cur.ini.get("MaxLag", "nearMaxLagTime", nearMaxLagTime, 0.1F, 30F);
        farMaxLagTime = com.maddox.il2.engine.Config.cur.ini.get("MaxLag", "farMaxLagTime", farMaxLagTime, nearMaxLagTime, 30F);
        cheaterWarningDelay = com.maddox.il2.engine.Config.cur.ini.get("MaxLag", "cheaterWarningDelay", cheaterWarningDelay, 1.0F, 30F);
        cheaterWarningNum = com.maddox.il2.engine.Config.cur.ini.get("MaxLag", "cheaterWarningNum", cheaterWarningNum);
        checkRuntime = com.maddox.il2.engine.Config.cur.ini.get("NET", "checkRuntime", 0, 0, 2);
        eventlogHouse = com.maddox.il2.engine.Config.cur.ini.get("game", "eventlogHouse", false);
        eventlogClient = com.maddox.il2.engine.Config.cur.ini.get("game", "eventlogClient", -1);
    }

    public NetServerParams(com.maddox.rts.NetChannel netchannel, int i, com.maddox.rts.NetHost nethost)
    {
        super(null, netchannel, i);
        flags = 4096;
        autoLogDetail = 3;
        eventlogHouse = false;
        eventlogClient = -1;
        bNGEN = false;
        timeoutNGEN = 0L;
        bLandedNGEN = false;
        farMaxLagTime = 10F;
        nearMaxLagTime = 2.0F;
        cheaterWarningDelay = 10F;
        cheaterWarningNum = 3;
        syncStamp = 0;
        bCheckStartSync = false;
        bDoSync = false;
        serverDeltaTime = 0L;
        serverDeltaTime_lastUpdate = 0L;
        serverClockOffset0 = 0L;
        lastServerTime = 0L;
        _lastCheckMaxLag = -1L;
        checkRuntime = 0;
        checkTimeUpdate = 0L;
        checkUsers = new HashMapExt();
        checkPublicKey = 0;
        checkKey = 0;
        checkSecond2 = 0;
        host = nethost;
        com.maddox.il2.game.Main.cur().netServerParams = this;
        outMsgF = new NetMsgFiltered();
        try
        {
            outMsgF.setIncludeTime(true);
        }
        catch(java.lang.Exception exception) { }
    }

    public com.maddox.rts.NetMsgSpawn netReplicate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        com.maddox.rts.NetMsgSpawn netmsgspawn = new NetMsgSpawn(this);
        netmsgspawn.writeNetObj(host);
        netmsgspawn.writeInt(flags);
        netmsgspawn.writeInt(difficulty);
        netmsgspawn.writeByte(maxUsers);
        netmsgspawn.write255(serverName);
        netmsgspawn.writeByte(autoLogDetail);
        netmsgspawn.writeFloat(farMaxLagTime);
        netmsgspawn.writeFloat(nearMaxLagTime);
        netmsgspawn.writeFloat(cheaterWarningDelay);
        netmsgspawn.writeInt(cheaterWarningNum);
        if((netchannel instanceof com.maddox.rts.NetChannelOutStream) && isCoop())
            if(com.maddox.il2.net.NetMissionTrack.isPlaying())
            {
                netmsgspawn.writeLong(serverDeltaTime);
            } else
            {
                long l = 0L;
                if(isMirror())
                {
                    long l1 = com.maddox.il2.game.Main.cur().netServerParams.masterChannel().remoteClockOffset();
                    l = l1 - com.maddox.il2.game.Main.cur().netServerParams.serverClockOffset0;
                }
                netmsgspawn.writeLong(l);
            }
        netmsgspawn.writeInt(eventlogClient);
        return netmsgspawn;
    }

    private int crcSFSFile(java.lang.String s, int i)
    {
        try
        {
            com.maddox.rts.SFSInputStream sfsinputstream = new SFSInputStream(com.maddox.rts.Finger.LongFN(0L, s));
            i = sfsinputstream.crc(i);
            sfsinputstream.close();
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.rts.NetObj.printDebug(exception);
            return 0;
        }
        return i;
    }

    private int checkFirst(int i)
    {
        if(i != 0)
        {
            long l = com.maddox.rts.Finger.file(i, com.maddox.rts.MainWin32.GetCDDrive("jvm.dll"), -1);
            l = com.maddox.rts.Finger.file(l, com.maddox.rts.MainWin32.GetCDDrive("java.dll"), -1);
            l = com.maddox.rts.Finger.file(l, com.maddox.rts.MainWin32.GetCDDrive("net.dll"), -1);
            l = com.maddox.rts.Finger.file(l, com.maddox.rts.MainWin32.GetCDDrive("verify.dll"), -1);
            l = com.maddox.rts.Finger.file(l, com.maddox.rts.MainWin32.GetCDDrive("zip.dll"), -1);
            l = com.maddox.rts.Finger.file(l, "lib/rt.jar", -1);
            java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
            for(int j = 0; j < arraylist.size(); j++)
            {
                java.lang.Class class1 = (java.lang.Class)arraylist.get(j);
                l = com.maddox.il2.fm.FlightModelMain.finger(l, com.maddox.rts.Property.stringValue(class1, "FlightModel", null));
            }

            l = com.maddox.il2.objects.Statics.getShipsFile().finger(l);
            l = com.maddox.il2.objects.Statics.getTechnicsFile().finger(l);
            l = com.maddox.il2.objects.Statics.getBuildingsFile().finger(l);
            i = (int)l;
        }
        return i;
    }

    private int checkSecond(int i, int j)
    {
        checkSecond2 = j;
        try
        {
            java.lang.ClassLoader classloader = getClass().getClassLoader();
            java.lang.reflect.Field afield[] = (java.lang.ClassLoader.class).getDeclaredFields();
            java.lang.reflect.Field field = null;
            for(int k = 0; k < afield.length; k++)
            {
                if(!"classes".equals(afield[k].getName()))
                    continue;
                field = afield[k];
                break;
            }

            java.util.Vector vector = (java.util.Vector)com.maddox.rts.CLASS.field(classloader, field);
            for(int l = 0; l < vector.size(); l++)
            {
                java.lang.Class class1 = (java.lang.Class)vector.get(l);
                java.lang.reflect.Field afield1[] = class1.getDeclaredFields();
                if(afield1 != null)
                {
                    for(int i1 = 0; i1 < afield1.length; i1++)
                        i = com.maddox.rts.Finger.incInt(i, afield1[i1].getName());

                }
                java.lang.reflect.Method amethod[] = class1.getDeclaredMethods();
                if(amethod != null)
                {
                    for(int j1 = 0; j1 < amethod.length; j1++)
                        i = com.maddox.rts.Finger.incInt(i, amethod[j1].getName());

                }
                if(checkSecond2 != 0)
                    j = com.maddox.rts.CLASS.method(class1, j);
            }

        }
        catch(java.lang.Exception exception) { }
        checkSecond2 = j;
        return i;
    }

    private boolean CheckUserInput(int i, com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int j;
        switch(i)
        {
        case 8: // '\b'
            j = checkFirst(netmsginput.readInt());
            break;

        case 9: // '\t'
            j = checkSecond(netmsginput.readInt(), netmsginput.readInt());
            break;

        case 10: // '\n'
            j = netmsginput.readInt();
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
            com.maddox.il2.objects.air.Aircraft aircraft = netuser.findAircraft();
            if(com.maddox.il2.engine.Actor.isValid(aircraft))
            {
                j = com.maddox.rts.Finger.incInt(j, com.maddox.il2.ai.World.cur().diffCur.get());
                j = (int)aircraft.finger(j) + com.maddox.rts.SFSInputStream.oo;
            }
            break;

        default:
            return false;
        }
        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
        netmsgguaranted.writeByte(i);
        netmsgguaranted.writeNetObj(com.maddox.rts.NetEnv.host());
        netmsgguaranted.writeInt(j);
        if(i == 9)
            netmsgguaranted.writeInt(checkSecond2);
        postTo(netmsginput.channel(), netmsgguaranted);
        return true;
    }

    private boolean checkInput(int i, com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)netmsginput.readNetObj();
        if(isMaster())
        {
            java.lang.Object obj = (com.maddox.il2.net.CheckUser)checkUsers.get(netuser);
            if(obj != null)
                return ((com.maddox.il2.net.CheckUser) (obj)).checkInput(i, netmsginput);
            else
                return false;
        }
        if(com.maddox.rts.NetEnv.host() == netuser)
            return CheckUserInput(i, netmsginput);
        netmsginput.reset();
        obj = new NetMsgGuaranted();
        ((com.maddox.rts.NetMsgGuaranted) (obj)).writeMsg(netmsginput, 1);
        if(netmsginput.channel() == masterChannel())
            postTo(netuser.masterChannel(), ((com.maddox.rts.NetMsgGuaranted) (obj)));
        else
            postTo(masterChannel(), ((com.maddox.rts.NetMsgGuaranted) (obj)));
        return true;
    }

    private void checkUpdate()
    {
        if(isSingle())
            return;
        long l = com.maddox.rts.Time.currentReal();
        if(l < checkTimeUpdate)
            return;
        checkTimeUpdate = l + 1000L;
        java.util.List list = com.maddox.rts.NetEnv.hosts();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)list.get(j);
            if(!checkUsers.containsKey(netuser))
                checkUsers.put(netuser, new CheckUser(netuser));
        }

        boolean flag;
        if(i != checkUsers.size())
            do
            {
                flag = false;
                for(java.util.Map.Entry entry1 = checkUsers.nextEntry(null); entry1 != null; entry1 = checkUsers.nextEntry(entry1))
                {
                    com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)entry1.getKey();
                    if(!netuser1.isDestroyed())
                        continue;
                    checkUsers.remove(netuser1);
                    flag = true;
                    break;
                }

            } while(flag);
        for(java.util.Map.Entry entry = checkUsers.nextEntry(null); entry != null; entry = checkUsers.nextEntry(entry))
        {
            if(checkPublicKey == 0 && checkRuntime >= 1)
                checkPublicKey = (int)(java.lang.Math.random() * 4294967295D);
            com.maddox.il2.net.CheckUser checkuser = (com.maddox.il2.net.CheckUser)entry.getValue();
            checkuser.checkUpdate(l);
        }

    }

    public static final int MIN_SYNC_DELTA = 4000;
    public static final int MAX_SYNC_DELTA = 32000;
    public static final int MODE_DOGFIGHT = 0;
    public static final int MODE_COOP = 1;
    public static final int MODE_SINGLE = 2;
    public static final int MODE_MASK = 7;
    public static final int TYPE_LOCAL = 0;
    public static final int TYPE_BBGC = 16;
    public static final int TYPE_BBGC_DEMO = 32;
    public static final int TYPE_GAMESPY = 32;
    public static final int TYPE_USGS = 48;
    public static final int TYPE_MASK = 48;
    public static final int TYPE_SHIFT = 4;
    public static final int PROTECTED = 128;
    public static final int DEDICATED = 8;
    public static final int SHOW_SPEED_BAR = 4096;
    public static final int EXTRA_OCCLUSION = 8192;
    public static final int MSG_UPDATE = 0;
    public static final int MSG_COOP_ENTER = 1;
    public static final int MSG_COOP_ENTER_ASK = 2;
    public static final int MSG_SYNC = 3;
    public static final int MSG_SYNC_ASK = 4;
    public static final int MSG_SYNC_START = 5;
    public static final int MSG_TIME = 6;
    public static final int MSG_CHECK_BEGIN = 8;
    public static final int MSG_CHECK_FIRST = 8;
    public static final int MSG_CHECK_SECOND = 9;
    public static final int MSG_CHECK_STEP = 10;
    public static final int MSG_CHECK_END = 10;
    private java.lang.String serverName;
    public java.lang.String serverDescription;
    private java.lang.String serverPassword;
    private com.maddox.rts.NetHost host;
    private int flags;
    private int difficulty;
    private int maxUsers;
    private int autoLogDetail;
    private boolean eventlogHouse;
    private int eventlogClient;
    public boolean bNGEN;
    public long timeoutNGEN;
    public boolean bLandedNGEN;
    private float farMaxLagTime;
    private float nearMaxLagTime;
    private float cheaterWarningDelay;
    private int cheaterWarningNum;
    private com.maddox.rts.NetMsgFiltered outMsgF;
    private int syncStamp;
    private long syncTime;
    private long syncDelta;
    private boolean bCheckStartSync;
    private boolean bDoSync;
    private long serverDeltaTime;
    private long serverDeltaTime_lastUpdate;
    private long serverClockOffset0;
    private long lastServerTime;
    long _lastCheckMaxLag;
    private int checkRuntime;
    private long checkTimeUpdate;
    private com.maddox.util.HashMapExt checkUsers;
    private int checkPublicKey;
    private int checkKey;
    private int checkSecond2;

    static 
    {
        com.maddox.rts.Spawn.add(com.maddox.il2.net.NetServerParams.class, new SPAWN());
    }






















}
