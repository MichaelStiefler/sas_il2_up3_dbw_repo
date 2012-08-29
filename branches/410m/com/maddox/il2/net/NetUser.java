// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   NetUser.java

package com.maddox.il2.net;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.TargetsGuard;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Airdrome;
import com.maddox.il2.ai.air.Point_Stay;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.BmpUtils;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.DotRange;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.Selector;
import com.maddox.il2.game.ZutiNetReceiveMethods;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.gui.GUIAirArming;
import com.maddox.il2.gui.GUINetAircraft;
import com.maddox.il2.gui.GUINetClientCBrief;
import com.maddox.il2.gui.GUINetClientDBrief;
import com.maddox.il2.net.client.ProfileUser;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.NetGunner;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgInvokeMethod_Object;
import com.maddox.rts.MsgNet;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetChannelOutStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetFilter;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.rts.net.NetFileClient;
import com.maddox.rts.net.NetFileRequest;
import com.maddox.sound.RadioChannelSpawn;
import com.maddox.util.HashMapExt;
import com.maddox.util.IntHashtable;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// Referenced classes of package com.maddox.il2.net:
//            BornPlace, Connect, NetUserLeft, NetUserStat, 
//            NetUserRegiment, NetServerParams, NetFilesTrack, NetFileServerMissProp, 
//            NetMissionTrack, NetFileServerSkin, NetFileServerPilot, NetFileServerNoseart, 
//            Chat, NetBanned, NetMaxLag

public class NetUser extends com.maddox.rts.NetHost
    implements com.maddox.rts.net.NetFileClient, com.maddox.rts.NetUpdate
{
    class AircraftNetFilter
        implements com.maddox.rts.NetFilter
    {

        public float filterNetMessage(com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetMsgFiltered netmsgfiltered)
        {
            java.lang.Object obj = netmsgfiltered.filterArg();
            if(obj == null)
                return -1F;
            com.maddox.util.IntHashtable inthashtable = null;
            com.maddox.il2.engine.ActorPos actorpos = null;
            if(obj instanceof com.maddox.il2.objects.air.NetAircraft)
            {
                com.maddox.il2.objects.air.NetAircraft netaircraft = (com.maddox.il2.objects.air.NetAircraft)obj;
                inthashtable = ((com.maddox.il2.objects.air.NetAircraft.AircraftNet)netaircraft.net).filterTable;
                actorpos = netaircraft.pos;
            } else
            if(obj instanceof com.maddox.il2.objects.air.NetGunner)
            {
                com.maddox.il2.objects.air.NetGunner netgunner = (com.maddox.il2.objects.air.NetGunner)obj;
                inthashtable = netgunner.getFilterTable();
                actorpos = netgunner.pos;
            } else
            {
                return -1F;
            }
            if(com.maddox.rts.Time.isPaused())
                return 0.0F;
            if(!com.maddox.il2.engine.Actor.isValid(viewActor))
                return 0.5F;
            int i = inthashtable.get(netchannel.id());
            if(i == -1)
            {
                inthashtable.put(netchannel.id(), (int)(com.maddox.rts.Time.current() & 0x7ffffffL));
                return 1.0F;
            }
            double d = (int)(com.maddox.rts.Time.current() & 0x7ffffffL) - i;
            if(d < 0.0D)
            {
                inthashtable.put(netchannel.id(), (int)(com.maddox.rts.Time.current() & 0x7ffffffL));
                return 1.0F;
            }
            double d1 = viewActor.pos.getAbsPoint().distance(actorpos.getAbsPoint());
            if(d1 > 10000D)
                d1 = 10000D;
            if(d1 < 1.0D)
                d1 = 1.0D;
            double d2 = (d1 * 5000D) / 10000D;
            float f = (float)(d / d2);
            if(f >= 1.0F)
                return 1.0F;
            else
                return f * f;
        }

        public void filterNetMessagePosting(com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetMsgFiltered netmsgfiltered)
        {
            java.lang.Object obj = netmsgfiltered.filterArg();
            if(obj == null)
                return;
            com.maddox.util.IntHashtable inthashtable = null;
            if(obj instanceof com.maddox.il2.objects.air.NetAircraft)
            {
                com.maddox.il2.objects.air.NetAircraft netaircraft = (com.maddox.il2.objects.air.NetAircraft)obj;
                inthashtable = ((com.maddox.il2.objects.air.NetAircraft.AircraftNet)netaircraft.net).filterTable;
            } else
            if(obj instanceof com.maddox.il2.objects.air.NetGunner)
            {
                com.maddox.il2.objects.air.NetGunner netgunner = (com.maddox.il2.objects.air.NetGunner)obj;
                inthashtable = netgunner.getFilterTable();
            } else
            {
                return;
            }
            inthashtable.put(netchannel.id(), (int)(com.maddox.rts.Time.current() & 0x7ffffffL));
        }

        public boolean filterEnableAdd(com.maddox.rts.NetChannel netchannel, com.maddox.rts.NetFilter netfilter)
        {
            return true;
        }

        AircraftNetFilter()
        {
        }
    }

    static class SPAWN
        implements com.maddox.rts.NetSpawn
    {

        public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            try
            {
                java.lang.String s = netmsginput.read255();
                int j = netmsginput.readUnsignedByte();
                int k = netmsginput.readUnsignedByte();
                if(k == 255)
                    k = -1;
                int l = netmsginput.readUnsignedShort();
                boolean flag = netmsginput.readBoolean();
                com.maddox.rts.NetHost anethost[] = null;
                int i1 = netmsginput.available() / netmsginput.netObjReferenceLen();
                if(i1 > 0)
                {
                    anethost = new com.maddox.rts.NetHost[i1];
                    for(int j1 = 0; j1 < i1; j1++)
                        anethost[j1] = (com.maddox.rts.NetHost)netmsginput.readNetObj();

                } else
                {
                    l = netmsginput.channel().id();
                }
                com.maddox.il2.net.NetUser netuser = new NetUser(netmsginput.channel(), i, s, anethost);
                netuser.bornPlace = j;
                netuser.place = k;
                netuser.idChannelFirst = l;
                netuser.bTrackWriter = flag;
                netuser.bWaitStartCoopMission = false;
                if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isMaster() && com.maddox.il2.game.Main.cur().netServerParams.isCoop())
                    netuser.requestPlace(-1);
                if(i1 == 0 && (netmsginput.channel() instanceof com.maddox.rts.NetChannelInStream))
                    netuser.bTrackWriter = true;
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.net.NetUser.printDebug(exception);
            }
        }

        SPAWN()
        {
        }
    }


    public com.maddox.il2.net.NetUserStat stat()
    {
        return stat;
    }

    public com.maddox.il2.net.NetUserStat curstat()
    {
        return curstat;
    }

    public void reset()
    {
        army = 0;
        stat.clear();
        curstat.clear();
        netMaxLag = null;
    }

    public java.lang.String uniqueName()
    {
        return uniqueName;
    }

    public boolean isTrackWriter()
    {
        return bTrackWriter;
    }

    public static com.maddox.il2.net.NetUser findTrackWriter()
    {
        java.util.List list = com.maddox.rts.NetEnv.hosts();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)list.get(j);
            if(netuser.isTrackWriter())
                return netuser;
        }

        return null;
    }

    public void setShortName(java.lang.String s)
    {
        if(s == null)
            s = "";
        super.setShortName(s);
        if(isMaster() && !isMirrored())
            makeUniqueName();
    }

    private void makeUniqueName()
    {
        java.lang.String s = shortName();
        java.util.ArrayList arraylist = new ArrayList(com.maddox.rts.NetEnv.hosts());
        arraylist.add(com.maddox.rts.NetEnv.host());
        int i = arraylist.size();
        int j = 0;
        do
        {
            boolean flag = false;
            int k = 0;
            do
            {
                if(k >= i)
                    break;
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)arraylist.get(k);
                java.lang.String s1 = netuser.uniqueName();
                if(s.equals(s1) && netuser != this)
                {
                    flag = true;
                    break;
                }
                k++;
            } while(true);
            if(flag)
            {
                s = shortName() + j;
                j++;
            } else
            {
                uniqueName = s;
                return;
            }
        } while(true);
    }

    private void pingUpdateInc()
    {
        new com.maddox.rts.MsgAction(64, 10D, this) {

            public void doAction(java.lang.Object obj)
            {
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)obj;
                if(netuser.isDestroyed())
                    return;
                if(com.maddox.il2.game.Main.cur().netServerParams != null && !com.maddox.il2.game.Main.cur().netServerParams.isDestroyed() && !com.maddox.il2.game.Main.cur().netServerParams.isMaster() && !(com.maddox.il2.game.Main.cur().netServerParams.masterChannel() instanceof com.maddox.rts.NetChannelInStream))
                    try
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(1);
                        netmsgguaranted.writeByte(9);
                        postTo(com.maddox.il2.game.Main.cur().netServerParams.masterChannel(), netmsgguaranted);
                    }
                    catch(java.lang.Exception exception)
                    {
                        com.maddox.il2.net.NetUser.printDebug(exception);
                    }
                pingUpdateInc();
            }

        }
;
    }

    public int getArmy()
    {
        return army;
    }

    public void setArmy(int i)
    {
        army = i;
        radio_onArmyChanged();
    }

    public void sendStatInc()
    {
        if(!isMaster())
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return;
        _st.clear();
        _st.fillFromScoreCounter(true);
        if(_st.isEmpty())
        {
            return;
        } else
        {
            _sendStatInc(false);
            return;
        }
    }

    private void sendCurStatInc()
    {
        if(!isMaster())
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return;
        _st.clear();
        _st.fillFromScoreCounter(false);
        if(_st.isEmpty())
            return;
        __st.set(stat);
        __st.inc(_st);
        if(__st.isEqualsCurrent(curstat))
        {
            return;
        } else
        {
            _sendStatInc(true);
            return;
        }
    }

    public void netUpdate()
    {
        if(!isMaster())
            return;
        checkCameraBaseChanged();
        long l = com.maddox.rts.Time.real();
        if(lastTimeUpdate + 20000L > l)
            return;
        lastTimeUpdate = l;
        if(!com.maddox.il2.game.Mission.isNet())
            return;
        if(!com.maddox.il2.game.Mission.isPlaying())
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams.masterChannel() instanceof com.maddox.rts.NetChannelInStream)
        {
            return;
        } else
        {
            sendCurStatInc();
            return;
        }
    }

    private void _sendStatInc(boolean flag)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
        {
            if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            {
                if(flag)
                {
                    curstat.set(_st);
                } else
                {
                    stat.set(_st);
                    curstat.set(_st);
                }
            } else
            if(flag)
            {
                curstat.set(stat);
                curstat.inc(_st);
                _st.set(curstat);
            } else
            {
                stat.inc(_st);
                curstat.set(stat);
                _st.set(stat);
            }
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).replicateStat(this, flag);
        } else
        {
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(flag ? 7 : 5);
                _st.write(netmsgguaranted);
                postTo(com.maddox.il2.game.Main.cur().netServerParams.masterChannel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.net.NetUser.printDebug(exception);
            }
        }
    }

    private void replicateStat(com.maddox.il2.net.NetUser netuser, boolean flag)
    {
        replicateStat(netuser, flag, null);
    }

    private void replicateStat(com.maddox.il2.net.NetUser netuser, boolean flag, com.maddox.rts.NetChannel netchannel)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(flag ? 6 : 4);
            netmsgguaranted.writeNetObj(netuser);
            _st.write(netmsgguaranted);
            if(netchannel != null)
                postTo(netchannel, netmsgguaranted);
            else
                post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
    }

    private void getIncStat(com.maddox.rts.NetMsgInput netmsginput, boolean flag)
        throws java.io.IOException
    {
        _st.read(netmsginput);
        _sendStatInc(flag);
    }

    private void getStat(com.maddox.rts.NetMsgInput netmsginput, boolean flag)
        throws java.io.IOException
    {
        _st.read(netmsginput);
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)netmsginput.readNetObj();
        if(netuser == null)
            return;
        if(flag)
        {
            netuser.curstat.set(_st);
        } else
        {
            netuser.stat.set(_st);
            netuser.curstat.set(_st);
        }
        replicateStat(netuser, flag);
    }

    public int getAirdromeStay()
    {
        return airdromeStay;
    }

    public int getBornPlace()
    {
        return bornPlace;
    }

    public void setBornPlace(int i)
    {
        if(bornPlace == i)
            return;
        bornPlace = i;
        airdromeStay = -1;
        if(isMirrored())
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(2);
                netmsgguaranted.writeByte(2);
                netmsgguaranted.writeByte(bornPlace);
                post(netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.net.NetUser.printDebug(exception);
            }
        if(bornPlace >= 0 && com.maddox.il2.ai.World.cur().bornPlaces != null && bornPlace < com.maddox.il2.ai.World.cur().bornPlaces.size() && com.maddox.il2.game.Main.cur().netServerParams != null)
        {
            com.maddox.il2.net.BornPlace bornplace = (com.maddox.il2.net.BornPlace)com.maddox.il2.ai.World.cur().bornPlaces.get(bornPlace);
            setArmy(bornplace.army);
            if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
            {
                double d = bornplace.r * bornplace.r;
                com.maddox.il2.ai.air.Point_Stay apoint_stay[][] = com.maddox.il2.ai.World.cur().airdrome.stay;
                for(int j = 0; j < apoint_stay.length; j++)
                {
                    if(apoint_stay[j] == null)
                        continue;
                    com.maddox.il2.ai.air.Point_Stay point_stay = apoint_stay[j][apoint_stay[j].length - 1];
                    double d1 = ((double)point_stay.x - bornplace.place.x) * ((double)point_stay.x - bornplace.place.x) + ((double)point_stay.y - bornplace.place.y) * ((double)point_stay.y - bornplace.place.y);
                    if(d1 > d || ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).airdromeStay == j)
                        continue;
                    java.util.List list = com.maddox.rts.NetEnv.hosts();
                    boolean flag = false;
                    int k = 0;
                    do
                    {
                        if(k >= list.size())
                            break;
                        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)list.get(k);
                        if(netuser.airdromeStay == j)
                        {
                            flag = true;
                            break;
                        }
                        k++;
                    } while(true);
                    if(!flag)
                    {
                        airdromeStay = j;
                        d = d1;
                    }
                }

                if(isMirror())
                    sendAirdromeStay(bornPlace, airdromeStay);
            }
        }
    }

    private void sendAirdromeStay(int i, int j)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(6);
            netmsgguaranted.writeByte(3);
            netmsgguaranted.writeByte(i);
            netmsgguaranted.writeInt(j);
            postTo(masterChannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
    }

    public void onConnectReady(com.maddox.rts.NetChannel netchannel)
    {
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(1);
            netmsgguaranted.writeByte(1);
            postTo(netchannel, netmsgguaranted);
            netchannel.userState = 1;
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
    }

    public int getPlace()
    {
        if(isMaster() && localRequestPlace != place)
            return -1;
        else
            return place;
    }

    private int _getPlace()
    {
        return place;
    }

    public void requestPlace(int i)
    {
        armyCoopWinner = 0;
        if(isMaster())
        {
            if(localRequestPlace == i)
                return;
            localRequestPlace = i;
            place = -1;
        }
        com.maddox.il2.net.NetServerParams netserverparams = com.maddox.il2.game.Main.cur().netServerParams;
        if(netserverparams.isMaster())
        {
            if(i != -1)
            {
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
                if(netuser._getPlace() == i)
                {
                    i = -1;
                } else
                {
                    java.util.List list1 = com.maddox.rts.NetEnv.hosts();
                    int k = 0;
                    do
                    {
                        if(k >= list1.size())
                            break;
                        com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)list1.get(k);
                        if(netuser1._getPlace() == i)
                        {
                            i = -1;
                            break;
                        }
                        k++;
                    } while(true);
                }
            }
            place = i;
            if(place >= 0)
                setArmy(com.maddox.il2.gui.GUINetAircraft.getItem(place).reg.getArmy());
            bWaitStartCoopMission = false;
            if(com.maddox.rts.NetEnv.host().isMirrored())
            {
                java.util.List list = com.maddox.rts.NetEnv.channels();
                for(int j = 0; j < list.size(); j++)
                {
                    com.maddox.rts.NetChannel netchannel = (com.maddox.rts.NetChannel)list.get(j);
                    if(netchannel.isMirrored(this) || netchannel == masterChannel())
                        try
                        {
                            com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
                            netmsgguaranted1.writeByte(14);
                            netmsgguaranted1.writeByte(place);
                            netmsgguaranted1.writeNetObj(this);
                            com.maddox.rts.NetEnv.host().postTo(netchannel, netmsgguaranted1);
                        }
                        catch(java.lang.Exception exception1)
                        {
                            com.maddox.il2.net.NetUser.printDebug(exception1);
                        }
                }

            }
        } else
        {
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(13);
                netmsgguaranted.writeByte(i);
                postTo(netserverparams.masterChannel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.net.NetUser.printDebug(exception);
            }
        }
    }

    public void resetAllPlaces()
    {
        com.maddox.il2.net.NetServerParams netserverparams = com.maddox.il2.game.Main.cur().netServerParams;
        if(netserverparams == null || !netserverparams.isMaster())
            return;
        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).requestPlace(-1);
        java.util.List list = com.maddox.rts.NetEnv.hosts();
        if(list == null)
            return;
        for(int i = 0; i < list.size(); i++)
        {
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)list.get(i);
            netuser.requestPlace(-1);
        }

    }

    public void missionLoaded()
    {
        if(!com.maddox.il2.game.Mission.isCoop())
            return;
        if(!(com.maddox.il2.game.Mission.cur().netObj().masterChannel() instanceof com.maddox.rts.NetChannelInStream))
        {
            java.util.List list = com.maddox.rts.NetEnv.hosts();
            if(list == null)
                return;
            for(int i = 0; i < list.size(); i++)
            {
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)list.get(i);
                if(netuser.place >= 0)
                    netuser.setArmy(com.maddox.il2.gui.GUINetAircraft.getItem(netuser.place).reg.getArmy());
            }

        }
    }

    public boolean isWaitStartCoopMission()
    {
        return bWaitStartCoopMission && getPlace() >= 0;
    }

    public void doWaitStartCoopMission()
    {
        com.maddox.il2.net.NetServerParams netserverparams = com.maddox.il2.game.Main.cur().netServerParams;
        if(netserverparams.isMaster())
        {
            bWaitStartCoopMission = true;
            if(com.maddox.rts.NetEnv.host().isMirrored())
                try
                {
                    com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                    netmsgguaranted.writeByte(16);
                    netmsgguaranted.writeNetObj(this);
                    com.maddox.rts.NetEnv.host().post(netmsgguaranted);
                }
                catch(java.lang.Exception exception)
                {
                    com.maddox.il2.net.NetUser.printDebug(exception);
                }
        } else
        {
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
                netmsgguaranted1.writeByte(15);
                postTo(netserverparams.masterChannel(), netmsgguaranted1);
            }
            catch(java.lang.Exception exception1)
            {
                com.maddox.il2.net.NetUser.printDebug(exception1);
            }
        }
    }

    public void kick(com.maddox.il2.net.NetUser netuser)
    {
        if(netuser == null || netuser.isDestroyed())
            return;
        com.maddox.il2.net.NetServerParams netserverparams = com.maddox.il2.game.Main.cur().netServerParams;
        if(!netserverparams.isMaster())
            return;
        if(netuser.isMaster())
        {
            return;
        } else
        {
            _kick(netuser);
            return;
        }
    }

    private void _kick(com.maddox.il2.net.NetUser netuser)
    {
        if(netuser == null || netuser.isDestroyed())
        {
            return;
        } else
        {
            new com.maddox.rts.MsgAction(72, 0.0D, netuser) {

                public void doAction(java.lang.Object obj)
                {
                    com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)obj;
                    if(netuser1 == null || netuser1.isDestroyed())
                        return;
                    if(netuser1.path() == null)
                        netuser1.masterChannel().destroy("You have been kicked from the server.");
                    else
                        try
                        {
                            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                            netmsgguaranted.writeByte(17);
                            netmsgguaranted.writeNetObj(netuser1);
                            com.maddox.rts.NetEnv.host().postTo(netuser1.masterChannel(), netmsgguaranted);
                        }
                        catch(java.lang.Exception exception)
                        {
                            com.maddox.il2.net.NetUser.printDebug(exception);
                        }
                }

            }
;
            return;
        }
    }

    public void coopMissionComplete(boolean flag)
    {
        if(isMirrored())
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(18);
                netmsgguaranted.writeByte(flag ? 1 : 0);
                post(netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.net.NetUser.printDebug(exception);
            }
        setArmyCoopWinner(flag);
    }

    private void setClientMissionComplete(boolean flag)
    {
        coopMissionComplete(flag);
        com.maddox.il2.ai.World.cur().targetsGuard.doMissionComplete();
    }

    private void setArmyCoopWinner(boolean flag)
    {
        armyCoopWinner = com.maddox.il2.ai.World.getMissionArmy();
        if(!flag)
            armyCoopWinner = armyCoopWinner % 2 + 1;
    }

    public static int getArmyCoopWinner()
    {
        return armyCoopWinner;
    }

    public void speekVoice(int i, int j, int k, java.lang.String s, int ai[], int l, boolean flag)
    {
        if(isMirrored())
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(22);
                netmsgguaranted.writeShort(i);
                netmsgguaranted.writeShort(j);
                if(s != null && s.length() == 2)
                    k |= 0x8000;
                netmsgguaranted.writeShort(k);
                if(s != null && s.length() == 2)
                    netmsgguaranted.write255(s);
                netmsgguaranted.writeBoolean(flag);
                int i1 = ai.length;
                int j1 = 0;
                do
                {
                    if(j1 >= i1)
                        break;
                    int k1 = ai[j1];
                    if(k1 == 0)
                        break;
                    netmsgguaranted.writeShort(k1);
                    j1++;
                } while(true);
                post(netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.net.NetUser.printDebug(exception);
            }
    }

    private void getVoice(com.maddox.rts.NetMsgInput netmsginput)
    {
        int i;
        int j;
        int k;
        java.lang.String s;
        boolean flag;
        int l;
        i = netmsginput.readUnsignedShort();
        j = netmsginput.readUnsignedShort();
        k = netmsginput.readUnsignedShort();
        s = null;
        if((k & 0x8000) != 0)
        {
            k &= 0xffff7fff;
            s = netmsginput.read255();
        }
        flag = netmsginput.readBoolean();
        l = netmsginput.available() / 2;
        if(l == 0)
            return;
        try
        {
            int ai[] = new int[l + 1];
            for(int i1 = 0; i1 < l; i1++)
                ai[i1] = netmsginput.readUnsignedShort();

            ai[l] = 0;
            speekVoice(i, j, k, s, ai, 1, flag);
            com.maddox.il2.objects.sounds.Voice.setSyncMode(i);
            com.maddox.il2.objects.sounds.Voice.speak(j, k, s, ai, 1, false, flag);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
        return;
    }

    private void checkCameraBaseChanged()
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(!com.maddox.il2.game.Mission.isNet())
            return;
        com.maddox.il2.net.NetServerParams netserverparams = com.maddox.il2.game.Main.cur().netServerParams;
        if(netserverparams == null || netserverparams.isMaster())
            return;
        com.maddox.il2.engine.Actor actor = com.maddox.il2.game.Main3D.cur3D().viewActor();
        if(viewActor == actor)
            return;
        viewActor = actor;
        com.maddox.il2.engine.ActorNet actornet = null;
        if(com.maddox.il2.engine.Actor.isValid(actor))
            actornet = actor.net;
        replicateCameraBaseChanged(actornet);
    }

    private void replicateCameraBaseChanged(com.maddox.rts.NetObj netobj)
    {
        com.maddox.il2.net.NetServerParams netserverparams = com.maddox.il2.game.Main.cur().netServerParams;
        if(netserverparams.isMaster())
        {
            if(netobj != null)
            {
                java.lang.Object obj = netobj.superObj();
                if(obj != null && (obj instanceof com.maddox.il2.engine.Actor))
                {
                    viewActor = (com.maddox.il2.engine.Actor)obj;
                    return;
                }
            }
            viewActor = null;
        } else
        {
            doReplicateCameraBaseChanged(netobj);
        }
    }

    public void doReplicateCameraBaseChanged(java.lang.Object obj)
    {
        if(isDestroyed())
            return;
        com.maddox.il2.net.NetServerParams netserverparams = com.maddox.il2.game.Main.cur().netServerParams;
        if(netserverparams == null)
            return;
        com.maddox.rts.NetObj netobj = null;
        if(obj != null)
        {
            netobj = (com.maddox.rts.NetObj)obj;
            if(netobj.isDestroyed())
                netobj = null;
            else
            if(netobj.masterChannel() != netserverparams.masterChannel() && !netserverparams.masterChannel().isMirrored(netobj))
            {
                (new MsgInvokeMethod_Object("doReplicateCameraBaseChanged", netobj)).post(72, this);
                return;
            }
        }
        if(netserverparams.masterChannel() instanceof com.maddox.rts.NetChannelInStream)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(19);
            netmsgguaranted.writeNetObj(netobj);
            postTo(netserverparams.masterChannel(), netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
    }

    public void orderCmd(int i)
    {
        com.maddox.il2.net.NetServerParams netserverparams = com.maddox.il2.game.Main.cur().netServerParams;
        if(netserverparams.isMaster())
        {
            if(i == -1)
                ordersTree.activate();
            else
            if(i == -2)
                ordersTree.unactivate();
            else
                ordersTree.execCmd(i);
        } else
        {
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(20);
                netmsgguaranted.writeByte(i);
                postTo(netserverparams.masterChannel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.net.NetUser.printDebug(exception);
            }
        }
    }

    public void orderCmd(int i, com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.net.NetServerParams netserverparams = com.maddox.il2.game.Main.cur().netServerParams;
        if(netserverparams.isMaster())
        {
            if(i == -1)
                ordersTree.activate();
            else
            if(i == -2)
            {
                ordersTree.unactivate();
            } else
            {
                com.maddox.il2.engine.Actor actor1 = com.maddox.il2.game.Selector.getTarget();
                com.maddox.il2.game.Selector.setTarget(actor);
                ordersTree.execCmd(i);
                com.maddox.il2.game.Selector.setTarget(actor1);
            }
        } else
        {
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(20);
                netmsgguaranted.writeByte(i);
                netmsgguaranted.writeNetObj(actor.net);
                postTo(netserverparams.masterChannel(), netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.net.NetUser.printDebug(exception);
            }
        }
    }

    public void postTaskComplete(com.maddox.il2.engine.Actor actor)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return;
        com.maddox.il2.ai.World.onTaskComplete(actor);
        if(actor.net.countMirrors() == 0)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(23);
            netmsgguaranted.writeNetObj(actor.net);
            post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
    }

    public void replicateDotRange()
    {
        replicateDotRange(true);
        replicateDotRange(false);
    }

    public void replicateDotRange(boolean flag)
    {
        replicateDotRange(flag, null);
    }

    private void replicateDotRange(com.maddox.rts.NetChannel netchannel)
    {
        replicateDotRange(true, netchannel);
        replicateDotRange(false, netchannel);
    }

    private void replicateDotRange(boolean flag, com.maddox.rts.NetChannel netchannel)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return;
        if(this != com.maddox.il2.game.Main.cur().netServerParams.host())
            return;
        if(isMirrored() || netchannel != null)
            try
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                netmsgguaranted.writeByte(flag ? 29 : 30);
                if(flag)
                    com.maddox.il2.game.Main.cur().dotRangeFriendly.netOutput(netmsgguaranted);
                else
                    com.maddox.il2.game.Main.cur().dotRangeFoe.netOutput(netmsgguaranted);
                if(netchannel == null)
                    post(netmsgguaranted);
                else
                    postTo(netchannel, netmsgguaranted);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.net.NetUser.printDebug(exception);
            }
    }

    public void replicateEventLog(int i, float f, java.lang.String s, java.lang.String s1, int j, float f1, float f2)
    {
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return;
        if(!isMirrored())
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(31);
            netmsgguaranted.writeByte(i);
            netmsgguaranted.writeFloat(f);
            netmsgguaranted.write255(s);
            netmsgguaranted.write255(s1);
            netmsgguaranted.writeByte(j);
            netmsgguaranted.writeFloat(f1);
            netmsgguaranted.writeFloat(f2);
            post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
    }

    private void getEventLog(com.maddox.rts.NetMsgInput netmsginput)
    {
        try
        {
            byte byte0 = netmsginput.readByte();
            float f = netmsginput.readFloat();
            java.lang.String s = netmsginput.read255();
            java.lang.String s1 = netmsginput.read255();
            byte byte1 = netmsginput.readByte();
            float f1 = netmsginput.readFloat();
            float f2 = netmsginput.readFloat();
            com.maddox.il2.ai.EventLog.type(byte0, f, s, s1, byte1, f1, f2, false);
            replicateEventLog(byte0, f, s, s1, byte1, f1, f2);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
    }

    public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        if(super.netInput(netmsginput))
            return true;
        netmsginput.reset();
        int i = netmsginput.readByte();
        if(com.maddox.il2.game.ZutiNetReceiveMethods.processReceivedMessage(this, netmsginput, (byte)i))
            return true;
        if(isMirror() && netmsginput.channel() == masterChannel)
            switch(i)
            {
            case 1: // '\001'
                if(netmsginput.channel().userState == -1)
                {
                    netmsginput.channel().userState = 1;
                    if(com.maddox.il2.game.Mission.cur() != null && com.maddox.il2.game.Mission.cur().netObj() != null)
                        com.maddox.rts.MsgNet.postRealNewChannel(com.maddox.il2.game.Mission.cur().netObj(), masterChannel);
                }
                return true;

            case 14: // '\016'
                int j = netmsginput.readUnsignedByte();
                if(j == 255)
                    j = -1;
                com.maddox.il2.net.NetUser netuser2 = (com.maddox.il2.net.NetUser)netmsginput.readNetObj();
                if(netuser2 == null)
                    return true;
                netuser2.place = j;
                if(j >= 0 && com.maddox.il2.game.Mission.cur() != null && com.maddox.il2.game.Main.cur().missionLoading == null)
                    netuser2.setArmy(com.maddox.il2.gui.GUINetAircraft.getItem(j).reg.getArmy());
                netuser2.bWaitStartCoopMission = false;
                if(isMirrored())
                    try
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted4 = new NetMsgGuaranted();
                        netmsgguaranted4.writeByte(14);
                        netmsgguaranted4.writeByte(j);
                        netmsgguaranted4.writeNetObj(netuser2);
                        post(netmsgguaranted4);
                    }
                    catch(java.lang.Exception exception2)
                    {
                        com.maddox.il2.net.NetUser.printDebug(exception2);
                    }
                return true;

            case 16: // '\020'
                com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)netmsginput.readNetObj();
                if(netuser == null)
                    return true;
                netuser.bWaitStartCoopMission = true;
                if(isMirrored())
                    try
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
                        netmsgguaranted.writeByte(16);
                        netmsgguaranted.writeNetObj(netuser);
                        post(netmsgguaranted);
                    }
                    catch(java.lang.Exception exception)
                    {
                        com.maddox.il2.net.NetUser.printDebug(exception);
                    }
                return true;

            case 17: // '\021'
                com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)netmsginput.readNetObj();
                if(netuser1 == null)
                {
                    return true;
                } else
                {
                    _kick(netuser1);
                    return true;
                }

            case 20: // '\024'
                byte byte0 = netmsginput.readByte();
                if(netmsginput.available() > 0)
                {
                    com.maddox.il2.engine.Actor actor = null;
                    com.maddox.rts.NetObj netobj1 = netmsginput.readNetObj();
                    if(netobj1 != null)
                        actor = (com.maddox.il2.engine.Actor)netobj1.superObj();
                    orderCmd(byte0, actor);
                } else
                {
                    orderCmd(byte0);
                }
                return true;

            case 18: // '\022'
                boolean flag = netmsginput.readByte() != 0;
                if(isMirrored())
                    try
                    {
                        com.maddox.rts.NetMsgGuaranted netmsgguaranted1 = new NetMsgGuaranted();
                        netmsgguaranted1.writeByte(18);
                        netmsgguaranted1.writeByte(flag ? 1 : 0);
                        post(netmsgguaranted1);
                    }
                    catch(java.lang.Exception exception1)
                    {
                        com.maddox.il2.net.NetUser.printDebug(exception1);
                    }
                setClientMissionComplete(flag);
                return true;

            case 2: // '\002'
                int k = netmsginput.readUnsignedByte();
                if(k == 255)
                    k = -1;
                setBornPlace(k);
                return true;

            case 10: // '\n'
                java.lang.String s = netmsginput.read255();
                char ac[] = new char[2];
                ac[0] = netmsginput.readChar();
                ac[1] = netmsginput.readChar();
                int j2 = netmsginput.readUnsignedByte();
                java.lang.String s5 = netmsginput.read255();
                setUserRegiment(s, s5, ac, j2);
                replicateNetUserRegiment();
                return true;

            case 11: // '\013'
                java.lang.String s1 = netmsginput.read255();
                setSkin(s1);
                replicateSkin();
                return true;

            case 12: // '\f'
                java.lang.String s2 = netmsginput.read255();
                setPilot(s2);
                replicatePilot();
                return true;

            case 32: // ' '
                java.lang.String s3 = netmsginput.read255();
                setNoseart(s3);
                replicateNoseart();
                return true;

            case 21: // '\025'
                java.lang.String s4 = null;
                int l1 = 0;
                if(netmsginput.available() > 0)
                {
                    s4 = netmsginput.read255();
                    if(netmsginput.available() > 0)
                    {
                        l1 = netmsginput.readInt();
                    } else
                    {
                        l1 = -1;
                        java.lang.System.out.println("ERROR: Radio channel message has old format");
                    }
                }
                if(l1 != -1)
                    replicateRadio(s4, l1);
                return true;

            case 22: // '\026'
                getVoice(netmsginput);
                return true;

            case 19: // '\023'
                replicateCameraBaseChanged(netmsginput.readNetObj());
                return true;

            case 23: // '\027'
                com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
                if(netobj == null)
                {
                    return true;
                } else
                {
                    postTaskComplete((com.maddox.il2.engine.Actor)netobj.superObj());
                    return true;
                }

            case 29: // '\035'
                com.maddox.il2.game.Main.cur().dotRangeFriendly.netInput(netmsginput);
                replicateDotRange(true);
                return true;

            case 30: // '\036'
                com.maddox.il2.game.Main.cur().dotRangeFoe.netInput(netmsginput);
                replicateDotRange(false);
                return true;
            }
        switch(i)
        {
        case 13: // '\r'
            int l = netmsginput.readUnsignedByte();
            if(l == 255)
                l = -1;
            requestPlace(l);
            return true;

        case 15: // '\017'
            doWaitStartCoopMission();
            return true;

        case 3: // '\003'
            int i1 = netmsginput.readUnsignedByte();
            if(i1 == 255)
                i1 = -1;
            int i2 = netmsginput.readInt();
            if(i1 == bornPlace)
                if(isMirror())
                    sendAirdromeStay(i1, i2);
                else
                    airdromeStay = i2;
            return true;

        case 4: // '\004'
        case 6: // '\006'
            getStat(netmsginput, i == 6);
            return true;

        case 5: // '\005'
        case 7: // '\007'
            getIncStat(netmsginput, i == 7);
            return true;

        case 9: // '\t'
            int j1 = 0;
            if(netmsginput.available() == 4)
                j1 = netmsginput.readInt();
            j1 += netmsginput.channel().ping();
            if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
            {
                ping = j1;
                com.maddox.rts.NetMsgGuaranted netmsgguaranted2 = new NetMsgGuaranted();
                netmsgguaranted2.writeByte(8);
                netmsgguaranted2.writeInt(j1);
                netmsgguaranted2.writeNetObj(this);
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).post(netmsgguaranted2);
            } else
            {
                com.maddox.rts.NetMsgGuaranted netmsgguaranted3 = new NetMsgGuaranted();
                netmsgguaranted3.writeByte(9);
                netmsgguaranted3.writeInt(j1);
                postTo(com.maddox.il2.game.Main.cur().netServerParams.masterChannel(), netmsgguaranted3);
            }
            return true;

        case 8: // '\b'
            int k1 = netmsginput.readInt();
            com.maddox.il2.net.NetUser netuser3 = (com.maddox.il2.net.NetUser)netmsginput.readNetObj();
            if(netuser3 != null)
            {
                netuser3.ping = k1;
                com.maddox.rts.NetMsgGuaranted netmsgguaranted5 = new NetMsgGuaranted();
                netmsgguaranted5.writeByte(8);
                netmsgguaranted5.writeInt(k1);
                netmsgguaranted5.writeNetObj(netuser3);
                post(netmsgguaranted5);
            }
            return true;

        case 24: // '\030'
            if(com.maddox.il2.ai.World.cur().statics != null)
                com.maddox.il2.ai.World.cur().statics.netMsgHouseDie(this, netmsginput);
            return true;

        case 25: // '\031'
            if(com.maddox.il2.ai.World.cur().statics != null)
                com.maddox.il2.ai.World.cur().statics.netMsgHouseSync(netmsginput);
            return true;

        case 26: // '\032'
            if(com.maddox.il2.ai.World.cur().statics != null)
                com.maddox.il2.ai.World.cur().statics.netMsgBridgeRDie(netmsginput);
            return true;

        case 27: // '\033'
            if(com.maddox.il2.ai.World.cur().statics != null)
                com.maddox.il2.ai.World.cur().statics.netMsgBridgeDie(this, netmsginput);
            return true;

        case 28: // '\034'
            if(com.maddox.il2.ai.World.cur().statics != null)
                com.maddox.il2.ai.World.cur().statics.netMsgBridgeSync(netmsginput);
            return true;

        case 31: // '\037'
            getEventLog(netmsginput);
            return true;

        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        case 14: // '\016'
        case 16: // '\020'
        case 17: // '\021'
        case 18: // '\022'
        case 19: // '\023'
        case 20: // '\024'
        case 21: // '\025'
        case 22: // '\026'
        case 23: // '\027'
        case 29: // '\035'
        case 30: // '\036'
        default:
            return false;
        }
    }

    public void netFileAnswer(com.maddox.rts.net.NetFileRequest netfilerequest)
    {
        if(netUserRegiment.netFileRequest == netfilerequest)
        {
            netUserRegiment.netFileRequest = null;
            if(netfilerequest.state() != 0 && !com.maddox.il2.net.NetFilesTrack.existFile(netfilerequest))
                return;
            netUserRegiment.setLocalFileNameBmp(netfilerequest.localFileName());
            if(netUserRegiment.localFileNameBmp != null)
                com.maddox.il2.net.NetFilesTrack.recordFile(com.maddox.il2.game.Main.cur().netFileServerReg, this, netUserRegiment.ownerFileNameBmp, netUserRegiment.localFileNameBmp);
            if(!netUserRegiment.isEmpty())
            {
                com.maddox.il2.objects.air.Aircraft aircraft = findAircraft();
                if(aircraft != null)
                {
                    java.lang.String s2 = aircraft.netName();
                    int k = s2.length();
                    int l = 0;
                    try
                    {
                        l = java.lang.Integer.parseInt(s2.substring(k - 2, k));
                    }
                    catch(java.lang.Exception exception) { }
                    aircraft.preparePaintScheme(l);
                }
            }
        } else
        if(netSkinRequest == netfilerequest)
        {
            netSkinRequest = null;
            if(netfilerequest.state() != 0 && !com.maddox.il2.net.NetFilesTrack.existFile(netfilerequest))
                return;
            localSkinBmp = netfilerequest.localFileName();
            if(localSkinBmp.length() == 0)
            {
                localSkinBmp = null;
            } else
            {
                tryPrepareSkin(findAircraft());
                com.maddox.il2.net.NetFilesTrack.recordFile(com.maddox.il2.game.Main.cur().netFileServerSkin, this, ownerSkinBmp, localSkinBmp);
            }
        } else
        if(netNoseartRequest == netfilerequest)
        {
            netNoseartRequest = null;
            if(netfilerequest.state() != 0 && !com.maddox.il2.net.NetFilesTrack.existFile(netfilerequest))
                return;
            localNoseartBmp = netfilerequest.localFileName();
            if(localNoseartBmp.length() == 0)
            {
                localNoseartBmp = null;
            } else
            {
                tryPrepareNoseart(findAircraft());
                com.maddox.il2.net.NetFilesTrack.recordFile(com.maddox.il2.game.Main.cur().netFileServerNoseart, this, ownerNoseartBmp, localNoseartBmp);
            }
        } else
        if(netPilotRequest == netfilerequest)
        {
            netPilotRequest = null;
            if(netfilerequest.state() != 0 && !com.maddox.il2.net.NetFilesTrack.existFile(netfilerequest))
                return;
            localPilotBmp = netfilerequest.localFileName();
            if(localPilotBmp.length() == 0)
            {
                localPilotBmp = null;
            } else
            {
                com.maddox.il2.objects.air.NetGunner netgunner = findGunner();
                com.maddox.il2.objects.air.Aircraft aircraft1 = findAircraft();
                if(netgunner == null)
                    tryPreparePilot(aircraft1);
                else
                if(com.maddox.il2.engine.Actor.isValid(aircraft1))
                    tryPreparePilot(aircraft1, aircraft1.netCockpitAstatePilotIndx(netgunner.getCockpitNum()));
                com.maddox.il2.net.NetFilesTrack.recordFile(com.maddox.il2.game.Main.cur().netFileServerPilot, this, ownerPilotBmp, localPilotBmp);
            }
        } else
        if(netFileRequestMissProp == netfilerequest)
        {
            if(netfilerequest.state() != 0)
            {
                netFileRequestMissProp = null;
                return;
            }
            java.lang.String s = netfilerequest.localFileName();
            if(s.equals(netfilerequest.ownerFileName()))
                s = com.maddox.il2.game.Main.cur().netFileServerMissProp.primaryPath() + "/" + s;
            else
                s = com.maddox.il2.game.Main.cur().netFileServerMissProp.alternativePath() + "/" + s;
            netFileRequestMissProp = null;
            int i = s.lastIndexOf(".properties");
            if(i < 0)
                return;
            s = s.substring(0, i);
            if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            {
                com.maddox.il2.gui.GUINetClientCBrief guinetclientcbrief = (com.maddox.il2.gui.GUINetClientCBrief)com.maddox.il2.game.GameState.get(46);
                if(!guinetclientcbrief.isExistTextDescription())
                    guinetclientcbrief.setTextDescription(s);
            } else
            if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
            {
                com.maddox.il2.gui.GUINetClientDBrief guinetclientdbrief = (com.maddox.il2.gui.GUINetClientDBrief)com.maddox.il2.game.GameState.get(40);
                if(!guinetclientdbrief.isExistTextDescription())
                    guinetclientdbrief.setTextDescription(s);
            }
        } else
        if(netFileRequestMissPropLocale == netfilerequest)
        {
            if(netfilerequest.state() != 0)
            {
                netFileRequestMissPropLocale = null;
                return;
            }
            java.lang.String s1 = netfilerequest.localFileName();
            if(s1.equals(netfilerequest.ownerFileName()))
                s1 = com.maddox.il2.game.Main.cur().netFileServerMissProp.primaryPath() + "/" + s1;
            else
                s1 = com.maddox.il2.game.Main.cur().netFileServerMissProp.alternativePath() + "/" + s1;
            netFileRequestMissPropLocale = null;
            int j = s1.lastIndexOf(".properties");
            if(j < 0)
                return;
            s1 = s1.substring(0, j);
            j = s1.lastIndexOf("_" + com.maddox.rts.RTSConf.cur.locale.getLanguage());
            if(j > 0)
                s1 = s1.substring(0, j);
            if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            {
                com.maddox.il2.gui.GUINetClientCBrief guinetclientcbrief1 = (com.maddox.il2.gui.GUINetClientCBrief)com.maddox.il2.game.GameState.get(46);
                guinetclientcbrief1.setTextDescription(s1);
            } else
            if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
            {
                com.maddox.il2.gui.GUINetClientDBrief guinetclientdbrief1 = (com.maddox.il2.gui.GUINetClientDBrief)com.maddox.il2.game.GameState.get(40);
                guinetclientdbrief1.setTextDescription(s1);
            }
        }
    }

    public void recordNetFiles()
    {
        if(netUserRegiment.localFileNameBmp != null)
            com.maddox.il2.net.NetFilesTrack.recordFile(com.maddox.il2.game.Main.cur().netFileServerReg, this, netUserRegiment.ownerFileNameBmp, netUserRegiment.localFileNameBmp);
        if(localSkinBmp != null)
            com.maddox.il2.net.NetFilesTrack.recordFile(com.maddox.il2.game.Main.cur().netFileServerSkin, this, ownerSkinBmp, localSkinBmp);
        if(localPilotBmp != null)
            com.maddox.il2.net.NetFilesTrack.recordFile(com.maddox.il2.game.Main.cur().netFileServerPilot, this, ownerPilotBmp, localPilotBmp);
        if(localNoseartBmp != null)
            com.maddox.il2.net.NetFilesTrack.recordFile(com.maddox.il2.game.Main.cur().netFileServerNoseart, this, ownerNoseartBmp, localNoseartBmp);
    }

    public void setMissProp(java.lang.String s)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
        {
            com.maddox.il2.gui.GUINetClientCBrief guinetclientcbrief = (com.maddox.il2.gui.GUINetClientCBrief)com.maddox.il2.game.GameState.get(46);
            guinetclientcbrief.clearTextDescription();
        } else
        if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
        {
            com.maddox.il2.gui.GUINetClientDBrief guinetclientdbrief = (com.maddox.il2.gui.GUINetClientDBrief)com.maddox.il2.game.GameState.get(40);
            guinetclientdbrief.clearTextDescription();
        }
        if(!s.startsWith("missions/"))
            return;
        s = s.substring("missions/".length());
        int i = s.length() - 1;
        do
        {
            if(i <= 0)
                break;
            char c = s.charAt(i);
            if(c == '\\' || c == '/')
                break;
            if(c == '.')
            {
                s = s.substring(0, i);
                break;
            }
            i--;
        } while(true);
        if(netFileRequestMissProp != null)
        {
            netFileRequestMissProp.doCancel();
            netFileRequestMissProp = null;
        }
        if(netFileRequestMissPropLocale != null)
        {
            netFileRequestMissPropLocale.doCancel();
            netFileRequestMissPropLocale = null;
        }
        if(!com.maddox.rts.RTSConf.cur.locale.equals(java.util.Locale.US))
        {
            netFileRequestMissPropLocale = new NetFileRequest(this, com.maddox.il2.game.Main.cur().netFileServerMissProp, 220, com.maddox.il2.game.Main.cur().netServerParams, s + "_" + com.maddox.rts.RTSConf.cur.locale.getLanguage() + ".properties");
            netFileRequestMissPropLocale.doRequest();
        }
        netFileRequestMissProp = new NetFileRequest(this, com.maddox.il2.game.Main.cur().netFileServerMissProp, 210, com.maddox.il2.game.Main.cur().netServerParams, s + ".properties");
        netFileRequestMissProp.doRequest();
    }

    public void setUserRegiment(java.lang.String s, java.lang.String s1, char ac[], int i)
    {
        if(netUserRegiment.equals(s, s1, ac, i))
            return;
        netUserRegiment.set(s, s1, ac, i);
        if(netUserRegiment.netFileRequest != null)
        {
            netUserRegiment.netFileRequest.doCancel();
            netUserRegiment.netFileRequest = null;
        }
        if(isMaster())
        {
            netUserRegiment.setLocalFileNameBmp(netUserRegiment.ownerFileNameBmp());
            if(com.maddox.il2.net.NetMissionTrack.isRecording())
                com.maddox.il2.net.NetFilesTrack.recordFile(com.maddox.il2.game.Main.cur().netFileServerReg, this, netUserRegiment.ownerFileNameBmp, netUserRegiment.localFileNameBmp);
        } else
        if(netUserRegiment.ownerFileNameBmp().length() > 0 && (com.maddox.il2.engine.Config.cur.netSkinDownload || (masterChannel() instanceof com.maddox.rts.NetChannelInStream)))
        {
            netUserRegiment.netFileRequest = new NetFileRequest(this, com.maddox.il2.game.Main.cur().netFileServerReg, 200, this, netUserRegiment.ownerFileNameBmp());
            netUserRegiment.netFileRequest.doRequest();
        }
    }

    public void replicateNetUserRegiment()
    {
        replicateNetUserRegiment(null);
    }

    private void replicateNetUserRegiment(com.maddox.rts.NetChannel netchannel)
    {
        if(!isMirrored() && netchannel == null)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted(1);
            netmsgguaranted.writeByte(10);
            netmsgguaranted.write255(netUserRegiment.branch());
            netmsgguaranted.writeChar(netUserRegiment.aid()[0]);
            netmsgguaranted.writeChar(netUserRegiment.aid()[1]);
            netmsgguaranted.writeByte(netUserRegiment.gruppeNumber());
            netmsgguaranted.write255(netUserRegiment.ownerFileNameBmp);
            if(netchannel == null)
                post(netmsgguaranted);
            else
                postTo(netchannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
    }

    public void tryPrepareSkin(com.maddox.il2.objects.air.NetAircraft netaircraft)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(netaircraft))
            return;
        if(localSkinBmp == null)
            return;
        com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)netaircraft;
        java.lang.Class class1 = aircraft.getClass();
        com.maddox.il2.ai.Regiment regiment = aircraft.getRegiment();
        java.lang.String s = regiment.country();
        java.lang.String s1 = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class1, s);
        if(skinDir == null)
        {
            java.lang.String s2 = s1;
            int i = s2.lastIndexOf('/');
            if(i >= 0)
                s2 = s2.substring(0, i + 1) + "summer";
            else
                s2 = s2 + "summer";
            com.maddox.il2.net.NetFileServerSkin netfileserverskin = com.maddox.il2.game.Main.cur().netFileServerSkin;
            java.lang.String s3;
            if(ownerSkinBmp.equals(localSkinBmp))
            {
                s3 = netfileserverskin.primaryPath() + "/" + localSkinBmp;
                skinDir = "" + com.maddox.rts.Finger.file(0L, s3, -1);
            } else
            {
                s3 = netfileserverskin.alternativePath() + "/" + localSkinBmp;
                int j = localSkinBmp.lastIndexOf('.');
                if(j >= 0)
                    skinDir = localSkinBmp.substring(0, j);
                else
                    skinDir = localSkinBmp;
            }
            skinDir = "PaintSchemes/Cache/" + skinDir;
            try
            {
                java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(skinDir, 0));
                if(!file.isDirectory())
                    file.mkdir();
            }
            catch(java.lang.Exception exception)
            {
                skinDir = null;
            }
            if(!com.maddox.il2.engine.BmpUtils.bmp8PalTo4TGA4(s3, s2, skinDir))
                skinDir = null;
        }
        if(skinDir == null)
        {
            return;
        } else
        {
            com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(s1, aircraft.hierMesh(), skinDir, cacheSkinMat);
            return;
        }
    }

    public void setSkin(java.lang.String s)
    {
        if(s == null)
            s = "";
        if(s.equals(ownerSkinBmp))
            return;
        ownerSkinBmp = s;
        localSkinBmp = null;
        skinDir = null;
        if(netSkinRequest != null)
        {
            netSkinRequest.doCancel();
            netSkinRequest = null;
        }
        if(isMaster())
        {
            if(s.length() > 0)
            {
                localSkinBmp = ownerSkinBmp;
                if(com.maddox.il2.net.NetMissionTrack.isRecording())
                    com.maddox.il2.net.NetFilesTrack.recordFile(com.maddox.il2.game.Main.cur().netFileServerSkin, this, ownerSkinBmp, localSkinBmp);
            } else
            {
                localSkinBmp = null;
            }
        } else
        if(s.length() > 0 && (com.maddox.il2.engine.Config.cur.netSkinDownload || (masterChannel() instanceof com.maddox.rts.NetChannelInStream)))
        {
            netSkinRequest = new NetFileRequest(this, com.maddox.il2.game.Main.cur().netFileServerSkin, 100, this, ownerSkinBmp);
            netSkinRequest.doRequest();
        }
    }

    public void replicateSkin()
    {
        replicateSkin(null);
    }

    private void replicateSkin(com.maddox.rts.NetChannel netchannel)
    {
        if(!isMirrored() && netchannel == null)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(11);
            netmsgguaranted.write255(ownerSkinBmp);
            if(netchannel == null)
                post(netmsgguaranted);
            else
                postTo(netchannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
    }

    public void checkReplicateSkin(java.lang.String s)
    {
        if(!isMaster())
            return;
        if(!"".equals(ownerSkinBmp))
            return;
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        java.lang.String s1 = usercfg.getSkin(s);
        if(s1 == null)
        {
            return;
        } else
        {
            setSkin(com.maddox.il2.gui.GUIAirArming.validateFileName(s) + "/" + s1);
            replicateSkin();
            return;
        }
    }

    public void tryPreparePilot(com.maddox.il2.objects.air.NetAircraft netaircraft)
    {
        tryPreparePilotSkin(netaircraft, 0);
    }

    public void tryPreparePilot(com.maddox.il2.objects.air.NetAircraft netaircraft, int i)
    {
        tryPreparePilotSkin(netaircraft, i);
    }

    public void tryPreparePilot(com.maddox.il2.objects.air.Paratrooper paratrooper)
    {
        tryPreparePilotSkin(paratrooper, 0);
    }

    public void tryPreparePilotSkin(com.maddox.il2.engine.Actor actor, int i)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return;
        if(i < 0)
            return;
        if(localPilotBmp == null)
            return;
        if(localPilotTga == null)
        {
            com.maddox.il2.net.NetFileServerPilot netfileserverpilot = com.maddox.il2.game.Main.cur().netFileServerPilot;
            java.lang.String s1;
            if(ownerPilotBmp.equals(localPilotBmp))
                s1 = netfileserverpilot.primaryPath() + "/" + localPilotBmp;
            else
                s1 = netfileserverpilot.alternativePath() + "/" + localPilotBmp;
            localPilotTga = localPilotBmp.substring(0, localPilotBmp.length() - 4);
            if(!com.maddox.il2.engine.BmpUtils.bmp8PalToTGA3(s1, "PaintSchemes/Cache/Pilot" + localPilotTga + ".tga"))
            {
                localPilotTga = null;
                return;
            }
        }
        if(localPilotTga == null)
            return;
        java.lang.String s = "PaintSchemes/Cache/Pilot" + localPilotTga + ".mat";
        java.lang.String s2 = "PaintSchemes/Cache/Pilot" + localPilotTga + ".tga";
        if(actor instanceof com.maddox.il2.objects.air.NetAircraft)
            com.maddox.il2.objects.air.Aircraft.prepareMeshPilot(((com.maddox.il2.objects.air.NetAircraft)actor).hierMesh(), i, s, s2, cachePilotMat);
        else
        if(actor instanceof com.maddox.il2.objects.air.Paratrooper)
            ((com.maddox.il2.objects.air.Paratrooper)actor).prepareSkin(s, s2, cachePilotMat);
    }

    public void tryPreparePilotDefaultSkin(com.maddox.il2.objects.air.Aircraft aircraft, int i)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(aircraft))
            return;
        if(i < 0)
        {
            return;
        } else
        {
            java.lang.String s = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(aircraft.getClass(), aircraft.getRegiment().country());
            java.lang.String s1 = com.maddox.rts.HomePath.concatNames(s, "pilot" + (1 + i) + ".mat");
            com.maddox.il2.objects.air.Aircraft.prepareMeshPilot(aircraft.hierMesh(), i, s1, "3do/plane/textures/pilot" + (1 + i) + ".tga");
            return;
        }
    }

    public void setPilot(java.lang.String s)
    {
        if(s == null)
            s = "";
        if(s.equals(ownerPilotBmp))
            return;
        ownerPilotBmp = s;
        localPilotBmp = null;
        localPilotTga = null;
        if(netPilotRequest != null)
        {
            netPilotRequest.doCancel();
            netPilotRequest = null;
        }
        if(isMaster())
        {
            if(s.length() > 0)
            {
                localPilotBmp = ownerPilotBmp;
                if(com.maddox.il2.net.NetMissionTrack.isRecording())
                    com.maddox.il2.net.NetFilesTrack.recordFile(com.maddox.il2.game.Main.cur().netFileServerPilot, this, ownerPilotBmp, localPilotBmp);
            } else
            {
                localPilotBmp = null;
            }
        } else
        if(s.length() > 0 && (com.maddox.il2.engine.Config.cur.netSkinDownload || (masterChannel() instanceof com.maddox.rts.NetChannelInStream)))
        {
            netPilotRequest = new NetFileRequest(this, com.maddox.il2.game.Main.cur().netFileServerPilot, 150, this, ownerPilotBmp);
            netPilotRequest.doRequest();
        }
    }

    public void replicatePilot()
    {
        replicatePilot(null);
    }

    private void replicatePilot(com.maddox.rts.NetChannel netchannel)
    {
        if(!isMirrored() && netchannel == null)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(12);
            netmsgguaranted.write255(ownerPilotBmp);
            if(netchannel == null)
                post(netmsgguaranted);
            else
                postTo(netchannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
    }

    public void checkReplicatePilot()
    {
        if(!isMaster())
            return;
        if(!"".equals(ownerPilotBmp))
            return;
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        java.lang.String s = usercfg.netPilot;
        if(s == null)
        {
            return;
        } else
        {
            setPilot(s);
            replicatePilot();
            return;
        }
    }

    public void tryPrepareNoseart(com.maddox.il2.objects.air.NetAircraft netaircraft)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(!com.maddox.il2.engine.Actor.isValid(netaircraft))
            return;
        if(localNoseartBmp == null)
            return;
        if(localNoseartTga == null)
        {
            com.maddox.il2.net.NetFileServerNoseart netfileservernoseart = com.maddox.il2.game.Main.cur().netFileServerNoseart;
            java.lang.String s;
            if(ownerNoseartBmp.equals(localNoseartBmp))
                s = netfileservernoseart.primaryPath() + "/" + localNoseartBmp;
            else
                s = netfileservernoseart.alternativePath() + "/" + localNoseartBmp;
            localNoseartTga = localNoseartBmp.substring(0, localNoseartBmp.length() - 4);
            if(!com.maddox.il2.engine.BmpUtils.bmp8PalTo2TGA4(s, "PaintSchemes/Cache/Noseart0" + localNoseartTga + ".tga", "PaintSchemes/Cache/Noseart1" + localNoseartTga + ".tga"))
            {
                localNoseartTga = null;
                return;
            }
        }
        if(localNoseartTga == null)
        {
            return;
        } else
        {
            com.maddox.il2.objects.air.Aircraft.prepareMeshNoseart(netaircraft.hierMesh(), "PaintSchemes/Cache/Noseart0" + localNoseartTga + ".mat", "PaintSchemes/Cache/Noseart1" + localNoseartTga + ".mat", "PaintSchemes/Cache/Noseart0" + localNoseartTga + ".tga", "PaintSchemes/Cache/Noseart1" + localNoseartTga + ".tga", cacheNoseartMat);
            return;
        }
    }

    public void setNoseart(java.lang.String s)
    {
        if(s == null)
            s = "";
        if(s.equals(ownerNoseartBmp))
            return;
        ownerNoseartBmp = s;
        localNoseartBmp = null;
        localNoseartTga = null;
        if(netNoseartRequest != null)
        {
            netNoseartRequest.doCancel();
            netNoseartRequest = null;
        }
        if(isMaster())
        {
            if(s.length() > 0)
            {
                localNoseartBmp = ownerNoseartBmp;
                if(com.maddox.il2.net.NetMissionTrack.isRecording())
                    com.maddox.il2.net.NetFilesTrack.recordFile(com.maddox.il2.game.Main.cur().netFileServerNoseart, this, ownerNoseartBmp, localNoseartBmp);
            } else
            {
                localNoseartBmp = null;
            }
        } else
        if(s.length() > 0 && (com.maddox.il2.engine.Config.cur.netSkinDownload || (masterChannel() instanceof com.maddox.rts.NetChannelInStream)))
        {
            netNoseartRequest = new NetFileRequest(this, com.maddox.il2.game.Main.cur().netFileServerNoseart, 175, this, ownerNoseartBmp);
            netNoseartRequest.doRequest();
        }
    }

    public void replicateNoseart()
    {
        replicateNoseart(null);
    }

    private void replicateNoseart(com.maddox.rts.NetChannel netchannel)
    {
        if(!isMirrored() && netchannel == null)
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(32);
            netmsgguaranted.write255(ownerNoseartBmp);
            if(netchannel == null)
                post(netmsgguaranted);
            else
                postTo(netchannel, netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
    }

    public void checkReplicateNoseart(java.lang.String s)
    {
        if(!isMaster())
            return;
        if(!"".equals(ownerNoseartBmp))
            return;
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        java.lang.String s1 = usercfg.getNoseart(s);
        if(s1 == null)
        {
            return;
        } else
        {
            setNoseart(s1);
            replicateNoseart();
            return;
        }
    }

    public java.lang.String radio()
    {
        return radio;
    }

    public int curCodec()
    {
        return curCodec;
    }

    public boolean isRadioNone()
    {
        return radio == null;
    }

    public boolean isRadioCommon()
    {
        return " 0".equals(radio);
    }

    public boolean isRadioArmy()
    {
        if(radio == null)
            return false;
        if(radio.length() < 2)
            return false;
        if(radio.charAt(0) != ' ')
            return false;
        return radio.charAt(1) != '0';
    }

    public boolean isRadioPrivate()
    {
        return !isRadioNone() && !isRadioCommon() && !isRadioArmy();
    }

    public void setRadio(java.lang.String s, int i)
    {
        replicateRadio(s, i);
    }

    public void radio_onCreated(java.lang.String s)
    {
        if(!com.maddox.il2.net.Chat.USE_NET_PHONE)
            return;
        if(radio != null && radio.equals(s))
            com.maddox.il2.net.Chat.radioSpawn.set(radio);
    }

    private void radio_onArmyChanged()
    {
        if(isMirror())
            return;
        if(!isRadioArmy())
        {
            return;
        } else
        {
            replicateRadio(" " + getArmy(), 1);
            return;
        }
    }

    private void replicateRadio(java.lang.String s, int i)
    {
        if(radio == s)
            return;
        if(s != null && s.equals(radio))
            return;
        radio = s;
        curCodec = i;
        if(!com.maddox.il2.net.Chat.USE_NET_PHONE)
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams.isMaster() && radio != null && !com.maddox.il2.net.Chat.radioSpawn.isExistChannel(radio))
            com.maddox.il2.net.Chat.radioSpawn.create(radio, curCodec);
        if(isMaster())
            if(radio == null)
                com.maddox.il2.net.Chat.radioSpawn.set(null);
            else
            if(com.maddox.il2.net.Chat.radioSpawn.isExistChannel(radio))
                com.maddox.il2.net.Chat.radioSpawn.set(radio);
        if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
        {
            java.util.ArrayList arraylist = null;
            int j = com.maddox.il2.net.Chat.radioSpawn.getNumChannels();
            for(int k = 0; k < j; k++)
            {
                java.lang.String s1 = com.maddox.il2.net.Chat.radioSpawn.getChannelName(k);
                if(s1.equals(((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).radio()))
                    continue;
                boolean flag = false;
                java.util.List list = com.maddox.rts.NetEnv.hosts();
                int i1 = 0;
                do
                {
                    if(i1 >= list.size())
                        break;
                    com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)list.get(i1);
                    if(s1.equals(netuser.radio))
                    {
                        flag = true;
                        break;
                    }
                    i1++;
                } while(true);
                if(flag)
                    continue;
                if(arraylist == null)
                    arraylist = new ArrayList();
                arraylist.add(s1);
            }

            if(arraylist != null)
            {
                for(int l = 0; l < arraylist.size(); l++)
                    com.maddox.il2.net.Chat.radioSpawn.kill((java.lang.String)arraylist.get(l));

            }
        }
        if(!isMirrored())
            return;
        try
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(21);
            if(radio != null)
            {
                netmsgguaranted.write255(radio);
                netmsgguaranted.writeInt(curCodec);
            }
            post(netmsgguaranted);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
    }

    private void tryPostChatTimeSpeed()
    {
        if(!com.maddox.rts.NetChannel.bCheckServerTimeSpeed)
            return;
        if(isDestroyed())
            return;
        if(com.maddox.il2.game.Main.cur().chat == null)
            return;
        if(masterChannel() == null)
            return;
        try
        {
            if(masterChannel().isMirrored(com.maddox.il2.game.Main.cur().chat))
            {
                java.util.ArrayList arraylist = new ArrayList(1);
                arraylist.add(this);
                com.maddox.il2.game.Main.cur().chat.send(null, "checkTimeSpeed " + com.maddox.rts.NetChannel.checkTimeSpeedInterval + "sec" + " " + (int)java.lang.Math.round(com.maddox.rts.NetChannel.checkTimeSpeedDifferense * 100D) + "%", arraylist, (byte)0, false);
            } else
            {
                new com.maddox.rts.MsgAction(64, 1.0D, this) {

                    public void doAction(java.lang.Object obj)
                    {
                        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)obj;
                        netuser.tryPostChatTimeSpeed();
                    }

                }
;
            }
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.net.NetUser.printDebug(exception);
        }
        return;
    }

    public com.maddox.il2.objects.air.Aircraft findAircraft()
    {
        for(java.util.Map.Entry entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(null); entry != null; entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getValue();
            if((actor instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)actor).netUser() == this)
                return (com.maddox.il2.objects.air.Aircraft)actor;
        }

        return null;
    }

    public com.maddox.il2.objects.air.NetGunner findGunner()
    {
        for(java.util.Map.Entry entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(null); entry != null; entry = com.maddox.il2.engine.Engine.name2Actor().nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getValue();
            if((actor instanceof com.maddox.il2.objects.air.NetGunner) && ((com.maddox.il2.objects.air.NetGunner)actor).getUser() == this)
                return (com.maddox.il2.objects.air.NetGunner)actor;
        }

        return null;
    }

    public void destroy()
    {
        super.destroy();
        if(isMirror() && com.maddox.rts.NetEnv.isServer() && !((com.maddox.il2.net.Connect)com.maddox.rts.NetEnv.cur().connect).banned.isExist(shortName))
            com.maddox.il2.net.Chat.sendLog(0, "user_leaves", shortName(), null);
        if(com.maddox.il2.engine.Actor.isValid(netUserRegiment))
            netUserRegiment.destroy();
        if(airNetFilter != null)
        {
            com.maddox.rts.NetChannel netchannel = masterChannel();
            if(netchannel != null && !netchannel.isDestroying())
                netchannel.filterRemove(airNetFilter);
            airNetFilter = null;
        }
        if(com.maddox.il2.game.Mission.isPlaying())
        {
            if(com.maddox.il2.game.Mission.isCoop() && com.maddox.rts.Time.current() > 1L)
                new NetUserLeft(uniqueName(), army, curstat);
            com.maddox.il2.ai.EventLog.onDisconnected(uniqueName());
        }
    }

    public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
    {
        if(netchannel.isMirrored(this))
            return;
        boolean flag;
        flag = false;
        if(!(com.maddox.il2.game.Main.cur() instanceof com.maddox.il2.game.Main3D) || !com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
            break MISSING_BLOCK_LABEL_45;
        if(isMaster())
        {
            if(com.maddox.il2.net.NetMissionTrack.isPlaying())
                return;
            break MISSING_BLOCK_LABEL_45;
        }
        flag = true;
        com.maddox.rts.NetMsgSpawn netmsgspawn = new NetMsgSpawn(this);
        netmsgspawn.write255(shortName);
        netmsgspawn.writeByte(bornPlace);
        netmsgspawn.writeByte(place);
        netmsgspawn.writeShort(idChannelFirst);
        if(isMirror())
        {
            if(path != null)
            {
                for(int i = 0; i < path.length; i++)
                    netmsgspawn.writeNetObj(path[i]);

            }
            if(!flag)
                netmsgspawn.writeNetObj(com.maddox.rts.NetEnv.host());
        } else
        if(!com.maddox.rts.NetEnv.isServer() && !bPingUpdateStarted)
        {
            bPingUpdateStarted = true;
            pingUpdateInc();
        }
        if((netchannel instanceof com.maddox.rts.NetChannelOutStream) && (com.maddox.il2.game.Main.cur() instanceof com.maddox.il2.game.Main3D) && com.maddox.il2.game.Main3D.cur3D().isDemoPlaying() && bTrackWriter)
            netmsgspawn.writeBoolean(true);
        else
            netmsgspawn.writeBoolean(false);
        postTo(netchannel, netmsgspawn);
        if(!"".equals(netUserRegiment.ownerFileNameBmp))
            replicateNetUserRegiment(netchannel);
        if(!"".equals(ownerSkinBmp))
            replicateSkin(netchannel);
        if(!"".equals(ownerPilotBmp))
            replicatePilot(netchannel);
        if(!"".equals(ownerNoseartBmp))
            replicateNoseart(netchannel);
        if(radio != null)
        {
            com.maddox.rts.NetMsgGuaranted netmsgguaranted = new NetMsgGuaranted();
            netmsgguaranted.writeByte(21);
            netmsgguaranted.write255(radio);
            netmsgguaranted.writeInt(curCodec);
            postTo(netchannel, netmsgguaranted);
        }
        replicateDotRange(netchannel);
        break MISSING_BLOCK_LABEL_349;
        java.lang.Exception exception;
        exception;
        com.maddox.il2.net.NetUser.printDebug(exception);
    }

    public NetUser(java.lang.String s)
    {
        super(s);
        stat = new NetUserStat();
        curstat = new NetUserStat();
        ping = 0;
        army = 0;
        bTrackWriter = false;
        bornPlace = -1;
        airdromeStay = -1;
        uniqueName = null;
        place = -1;
        bWaitStartCoopMission = false;
        localRequestPlace = -1;
        ownerSkinBmp = "";
        cacheSkinMat = new com.maddox.il2.engine.Mat[3];
        ownerPilotBmp = "";
        cachePilotMat = new com.maddox.il2.engine.Mat[1];
        ownerNoseartBmp = "";
        cacheNoseartMat = new com.maddox.il2.engine.Mat[2];
        radio = null;
        curCodec = 0;
        lastTimeUpdate = 0L;
        bPingUpdateStarted = false;
        makeUniqueName();
        netUserRegiment = new NetUserRegiment();
    }

    public NetUser(com.maddox.rts.NetChannel netchannel, int i, java.lang.String s, com.maddox.rts.NetHost anethost[])
    {
        super(netchannel, i, s, anethost);
        stat = new NetUserStat();
        curstat = new NetUserStat();
        ping = 0;
        army = 0;
        bTrackWriter = false;
        bornPlace = -1;
        airdromeStay = -1;
        uniqueName = null;
        place = -1;
        bWaitStartCoopMission = false;
        localRequestPlace = -1;
        ownerSkinBmp = "";
        cacheSkinMat = new com.maddox.il2.engine.Mat[3];
        ownerPilotBmp = "";
        cachePilotMat = new com.maddox.il2.engine.Mat[1];
        ownerNoseartBmp = "";
        cacheNoseartMat = new com.maddox.il2.engine.Mat[2];
        radio = null;
        curCodec = 0;
        lastTimeUpdate = 0L;
        bPingUpdateStarted = false;
        if(com.maddox.rts.NetEnv.isServer())
        {
            if(((com.maddox.il2.net.Connect)com.maddox.rts.NetEnv.cur().connect).banned.isExist(s))
            {
                kick(this);
                java.lang.System.out.println("User '" + s + "' [" + netchannel.remoteAddress().getHostAddress() + "] banned");
            } else
            {
                com.maddox.il2.net.Chat.sendLog(0, "user_joins", shortName(), null);
            }
            airNetFilter = new AircraftNetFilter();
            netchannel.filterAdd(airNetFilter);
            _st.clear();
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).replicateStat(this, false, netchannel);
        }
        makeUniqueName();
        netUserRegiment = new NetUserRegiment();
        if(com.maddox.rts.NetEnv.isServer())
        {
            java.lang.System.out.println("socket channel '" + netchannel.id() + "', ip " + netchannel.remoteAddress().getHostAddress() + ":" + netchannel.remotePort() + ", " + uniqueName() + ", is complete created");
            new com.maddox.rts.MsgAction(64, 1.0D, this) {

                public void doAction(java.lang.Object obj)
                {
                    com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)obj;
                    netuser.tryPostChatTimeSpeed();
                }

            }
;
        }
        com.maddox.il2.ai.EventLog.onConnected(uniqueName());
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static final int MSG_READY = 1;
    public static final int MSG_BORNPLACE = 2;
    public static final int MSG_AIRDROMESTAY = 3;
    public static final int MSG_STAT = 4;
    public static final int MSG_STAT_INC = 5;
    public static final int MSG_CURSTAT = 6;
    public static final int MSG_CURSTAT_INC = 7;
    public static final int MSG_PING = 8;
    public static final int MSG_PING_INC = 9;
    public static final int MSG_REGIMENT = 10;
    public static final int MSG_SKIN = 11;
    public static final int MSG_PILOT = 12;
    public static final int MSG_REQUEST_PLACE = 13;
    public static final int MSG_PLACE = 14;
    public static final int MSG_REQUEST_WAIT_START = 15;
    public static final int MSG_WAIT_START = 16;
    public static final int MSG_KICK = 17;
    public static final int MSG_MISSION_COMPLETE = 18;
    public static final int MSG_CAMERA = 19;
    public static final int MSG_ORDER_CMD = 20;
    public static final int MSG_RADIO = 21;
    public static final int MSG_VOICE = 22;
    public static final int MSG_TASK_COMPLETE = 23;
    public static final int MSG_HOUSE_DIE = 24;
    public static final int MSG_HOUSE_SYNC = 25;
    public static final int MSG_BRIDGE_RDIE = 26;
    public static final int MSG_BRIDGE_DIE = 27;
    public static final int MSG_BRIDGE_SYNC = 28;
    public static final int MSG_DOT_RANGE_FRIENDLY = 29;
    public static final int MSG_DOT_RANGE_FOE = 30;
    public static final int MSG_EVENTLOG = 31;
    public static final int MSG_NOISEART = 32;
    private com.maddox.il2.net.NetUserStat stat;
    private com.maddox.il2.net.NetUserStat curstat;
    private static com.maddox.il2.net.NetUserStat _st = new NetUserStat();
    private static com.maddox.il2.net.NetUserStat __st = new NetUserStat();
    private com.maddox.il2.net.NetUserStat fullStat;
    private com.maddox.il2.net.client.ProfileUser profile;
    private java.lang.String address;
    private int port;
    private java.lang.String sessionID;
    private int profileID;
    private int idChannelFirst;
    public int ping;
    private int army;
    private boolean bTrackWriter;
    private int bornPlace;
    private int airdromeStay;
    private java.lang.String uniqueName;
    private int place;
    private boolean bWaitStartCoopMission;
    public int syncCoopStart;
    private int localRequestPlace;
    private static int armyCoopWinner = 0;
    public com.maddox.il2.net.NetUserRegiment netUserRegiment;
    protected com.maddox.il2.net.NetMaxLag netMaxLag;
    public com.maddox.il2.game.order.OrdersTree ordersTree;
    private com.maddox.rts.net.NetFileRequest netFileRequestMissProp;
    private com.maddox.rts.net.NetFileRequest netFileRequestMissPropLocale;
    private java.lang.String ownerSkinBmp;
    private java.lang.String localSkinBmp;
    private java.lang.String skinDir;
    private com.maddox.rts.net.NetFileRequest netSkinRequest;
    private com.maddox.il2.engine.Mat cacheSkinMat[];
    private java.lang.String ownerPilotBmp;
    private java.lang.String localPilotBmp;
    private java.lang.String localPilotTga;
    private com.maddox.rts.net.NetFileRequest netPilotRequest;
    private com.maddox.il2.engine.Mat cachePilotMat[];
    private java.lang.String ownerNoseartBmp;
    private java.lang.String localNoseartBmp;
    private java.lang.String localNoseartTga;
    private com.maddox.rts.net.NetFileRequest netNoseartRequest;
    private com.maddox.il2.engine.Mat cacheNoseartMat[];
    private java.lang.String radio;
    private int curCodec;
    private com.maddox.il2.engine.Actor viewActor;
    private com.maddox.il2.net.AircraftNetFilter airNetFilter;
    private long lastTimeUpdate;
    private boolean bPingUpdateStarted;

    static 
    {
        com.maddox.rts.Spawn.add(com.maddox.il2.net.NetUser.class, new SPAWN());
    }











}
