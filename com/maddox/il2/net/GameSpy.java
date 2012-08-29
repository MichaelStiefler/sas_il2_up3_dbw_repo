// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GameSpy.java

package com.maddox.il2.net;

import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.rts.MsgInvokeMethod;
import com.maddox.rts.MsgNetExtListener;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.NetAddress;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetSocket;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import com.maddox.rts.net.IPAddress;
import java.io.PrintStream;
import java.util.List;

// Referenced classes of package com.maddox.il2.net:
//            NetUser, Connect, NetServerParams, NetUserStat, 
//            NetBanned

public class GameSpy
    implements com.maddox.rts.MsgNetExtListener, com.maddox.rts.MsgTimeOutListener
{

    public GameSpy()
    {
        userName = null;
        serverIP = null;
        roomName = null;
        gameType = "dogfight";
        maxClients = 16;
        queryid = 1;
        packetnumber = 0;
        qport = 0;
        no_query = 1;
        strBuf = new StringBuffer();
        _bGameModeWait = true;
        armyScore = new int[com.maddox.il2.ai.Army.amountNet()];
        _encrypted_val = new char[128];
        _encoded_val = new char[200];
        outBuf = new StringBuffer();
        _outBuf = new byte[1500];
        queryBuf = new StringBuffer();
        _trip = new int[3];
        _kwart = new int[4];
        _state = new char[256];
    }

    public boolean isServer()
    {
        return serverIP == null;
    }

    public void sendStatechanged()
    {
        send_heartbeat(1);
    }

    public void sendExiting()
    {
        send_heartbeat(2);
    }

    private void correctASCII()
    {
        int i = strBuf.length();
        for(int j = 0; j < i; j++)
        {
            char c = strBuf.charAt(j);
            if((c & 0x7f) != c)
                strBuf.setCharAt(j, '?');
        }

    }

    private void send_basic()
    {
        strBuf.append("\\gamename\\");
        strBuf.append("il2sturmovikfb");
        strBuf.append("\\gamever\\");
        strBuf.append("4.09m");
        strBuf.append("\\location\\");
        strBuf.append(1);
        buffer_send(strBuf);
        strBuf.delete(0, strBuf.length());
    }

    private void send_info()
    {
        strBuf.append("\\hostname\\");
        strBuf.append(com.maddox.il2.game.Main.cur().netServerParams.serverName());
        correctASCII();
        strBuf.append("\\hostport\\");
        if(isListenerOnly())
            strBuf.append(com.maddox.il2.engine.Config.cur.netLocalPort);
        else
            strBuf.append(qport);
        if(com.maddox.il2.game.Mission.cur() != null && com.maddox.il2.game.Mission.cur().name() != null)
        {
            strBuf.append("\\mapname\\");
            strBuf.append(com.maddox.il2.game.Mission.cur().name());
        }
        strBuf.append("\\gametype\\");
        if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            strBuf.append("coop");
        else
        if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
            strBuf.append("dogfight");
        else
        if(com.maddox.il2.game.Main.cur().netServerParams.isSingle())
            strBuf.append("single");
        int i = com.maddox.rts.NetEnv.hosts().size();
        if(!com.maddox.il2.game.Main.cur().netServerParams.isDedicated())
            i++;
        strBuf.append("\\numplayers\\");
        strBuf.append(i);
        strBuf.append("\\maxplayers\\");
        strBuf.append(com.maddox.il2.game.Main.cur().netServerParams.getMaxUsers());
        java.lang.String s = "unknown";
        if(com.maddox.rts.RTSConf.isRequestExitApp())
            s = "exiting";
        else
        if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
        {
            if(com.maddox.rts.NetEnv.cur().connect.isBindEnable())
            {
                s = "wait";
                _bGameModeWait = true;
            } else
            {
                s = "closedplaying";
                _bGameModeWait = false;
            }
        } else
        if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
        {
            if(com.maddox.il2.game.Mission.isPlaying())
            {
                s = "openplaying";
                _bGameModeWait = false;
            } else
            {
                s = "wait";
                _bGameModeWait = true;
            }
        } else
        if(com.maddox.il2.game.Main.cur().netServerParams.isSingle())
        {
            s = "closedplaying";
            _bGameModeWait = false;
        }
        strBuf.append("\\gamemode\\");
        strBuf.append(s);
        if(room != null)
        {
            strBuf.append("\\groupid\\");
            strBuf.append(room);
        }
        buffer_send(strBuf);
        strBuf.delete(0, strBuf.length());
    }

    private void send_rules()
    {
        com.maddox.il2.ai.DifficultySettings difficultysettings = com.maddox.il2.ai.World.cur().diffCur;
        strBuf.append("\\SeparateEStart\\");
        strBuf.append(difficultysettings.SeparateEStart ? "1" : "0");
        strBuf.append("\\ComplexEManagement\\");
        strBuf.append(difficultysettings.ComplexEManagement ? "1" : "0");
        strBuf.append("\\EngineOverheat\\");
        strBuf.append(difficultysettings.Engine_Overheat ? "1" : "0");
        strBuf.append("\\TorqueGyroEffects\\");
        strBuf.append(difficultysettings.Torque_N_Gyro_Effects ? "1" : "0");
        strBuf.append("\\FlutterEffect\\");
        strBuf.append(difficultysettings.Flutter_Effect ? "1" : "0");
        strBuf.append("\\WindTurbulence\\");
        strBuf.append(difficultysettings.Wind_N_Turbulence ? "1" : "0");
        strBuf.append("\\StallsSpins\\");
        strBuf.append(difficultysettings.Stalls_N_Spins ? "1" : "0");
        strBuf.append("\\Vulnerability\\");
        strBuf.append(difficultysettings.Vulnerability ? "1" : "0");
        strBuf.append("\\BlackoutsRedouts\\");
        strBuf.append(difficultysettings.Blackouts_N_Redouts ? "1" : "0");
        strBuf.append("\\RealisticGunnery\\");
        strBuf.append(difficultysettings.Realistic_Gunnery ? "1" : "0");
        strBuf.append("\\LimitedAmmo\\");
        strBuf.append(difficultysettings.Limited_Ammo ? "1" : "0");
        strBuf.append("\\LimitedFuel\\");
        strBuf.append(difficultysettings.Limited_Fuel ? "1" : "0");
        strBuf.append("\\CockpitAlwaysOn\\");
        strBuf.append(difficultysettings.Cockpit_Always_On ? "1" : "0");
        strBuf.append("\\NoOutsideViews\\");
        strBuf.append(difficultysettings.No_Outside_Views ? "1" : "0");
        strBuf.append("\\HeadShake\\");
        strBuf.append(difficultysettings.Head_Shake ? "1" : "0");
        strBuf.append("\\NoIcons\\");
        strBuf.append(difficultysettings.No_Icons ? "1" : "0");
        strBuf.append("\\NoPadlock\\");
        strBuf.append(difficultysettings.No_Padlock ? "1" : "0");
        strBuf.append("\\Clouds\\");
        strBuf.append(difficultysettings.Clouds ? "1" : "0");
        strBuf.append("\\NoInstantSuccess\\");
        strBuf.append(difficultysettings.NoInstantSuccess ? "1" : "0");
        strBuf.append("\\TakeoffLanding\\");
        strBuf.append(difficultysettings.Takeoff_N_Landing ? "1" : "0");
        strBuf.append("\\RealisticLandings\\");
        strBuf.append(difficultysettings.Realistic_Landings ? "1" : "0");
        strBuf.append("\\NoMapIcons\\");
        strBuf.append(difficultysettings.No_Map_Icons ? "1" : "0");
        strBuf.append("\\NoMinimapPath\\");
        strBuf.append(difficultysettings.NoMinimapPath ? "1" : "0");
        strBuf.append("\\NoSpeedBar\\");
        strBuf.append(difficultysettings.NoSpeedBar ? "1" : "0");
        if(com.maddox.il2.game.Main.cur().netServerParams.isProtected())
            strBuf.append("\\password\\1");
        buffer_send(strBuf);
        strBuf.delete(0, strBuf.length());
    }

    private void send_player(int i, com.maddox.il2.net.NetUser netuser)
    {
        strBuf.append("\\player_" + i + "\\");
        strBuf.append(netuser.shortName());
        strBuf.append("\\score_" + i + "\\");
        strBuf.append((int)netuser.stat().score);
        strBuf.append("\\ping_" + i + "\\");
        strBuf.append(netuser.ping);
        strBuf.append("\\team_" + i + "\\");
        strBuf.append(netuser.getArmy());
        int j = netuser.getArmy();
        if(armyScore[j] < 0)
            armyScore[j] = 0;
        armyScore[j] += (int)netuser.stat().score;
    }

    private void send_players()
    {
        for(int i = 0; i < armyScore.length; i++)
            armyScore[i] = -1;

        int j = 0;
        if(!com.maddox.il2.game.Main.cur().netServerParams.isDedicated())
            send_player(j++, (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host());
        java.util.List list = com.maddox.rts.NetEnv.hosts();
        int k = list.size();
        for(int l = 0; l < k; l++)
            send_player(j++, (com.maddox.il2.net.NetUser)list.get(l));

        for(int i1 = 0; i1 < armyScore.length; i1++)
            if(armyScore[i1] >= 0)
            {
                strBuf.append("\\team_t" + i1 + "\\" + com.maddox.il2.ai.Army.name(i1));
                strBuf.append("\\score_t" + i1 + "\\" + armyScore[i1]);
            }

        correctASCII();
        buffer_send(strBuf);
        strBuf.delete(0, strBuf.length());
    }

    private void send_echo(java.lang.String s)
    {
        if(s.length() > 1350)
        {
            return;
        } else
        {
            strBuf.append("\\echo\\");
            strBuf.append(s);
            buffer_send(strBuf);
            strBuf.delete(0, strBuf.length());
            return;
        }
    }

    private void send_final(java.lang.String s)
    {
        if(s != null)
        {
            int i = s.length();
            if(i > 128)
                return;
            s.getChars(0, i, _encrypted_val, 0);
            gs_encrypt("h53Ew8", _encrypted_val, i);
            int j = gs_encode(_encrypted_val, i, _encoded_val);
            strBuf.append("\\validate\\");
            strBuf.append(_encoded_val, 0, j);
            buffer_send(strBuf);
            strBuf.delete(0, strBuf.length());
        }
        strBuf.append("\\final\\");
        buffer_send(strBuf);
        strBuf.delete(0, strBuf.length());
        packet_send();
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        int i = 0x493e0;
        if(master_socket != null && master_address != null && MASTER_PORT != 0)
        {
            send_heartbeat(0);
            if(no_query > 0)
            {
                no_query++;
                if(no_query > 10)
                    no_query = 0;
                else
                    i = 30000;
            }
        } else
        {
            i = 30000;
        }
        ticker.post(com.maddox.rts.Time.currentReal() + (long)i);
    }

    public void set(java.lang.String s, com.maddox.rts.NetSocket netsocket, int i)
    {
        if(s != null)
            room = s;
        master_socket = netsocket;
        qport = i;
        if(master_socket != null && master_address == null)
        {
            try
            {
                com.maddox.rts.net.IPAddress ipaddress = new IPAddress();
                ipaddress.create(MASTER_ADDR);
                master_address = ipaddress;
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println("Unknown master address: " + MASTER_ADDR);
            }
            if(ticker == null)
            {
                ticker = new MsgTimeOut();
                ticker.setNotCleanAfterSend();
                ticker.setListener(this);
                ticker.setFlags(64);
            }
            if(!ticker.busy())
                ticker.post();
            com.maddox.rts.NetEnv.cur().msgAddListener(this, null);
            onMsgTimeout();
        }
    }

    public void setListenerOnly(java.lang.String s)
    {
        room = s;
        com.maddox.rts.NetEnv.cur().msgAddListener(this, null);
    }

    public boolean isListenerOnly()
    {
        return com.maddox.il2.game.Main.cur().netGameSpyListener == this;
    }

    public void onMsgTimeout()
    {
        (new MsgInvokeMethod("onMsgTimeout")).post(64, this, 5D);
        if(com.maddox.il2.game.Main.cur() != null && com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.rts.NetEnv.cur() != null && com.maddox.rts.NetEnv.cur().connect != null)
        {
            boolean flag;
            if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            {
                if(com.maddox.rts.NetEnv.cur().connect.isBindEnable())
                    flag = true;
                else
                    flag = false;
            } else
            if(com.maddox.il2.game.Mission.isPlaying())
                flag = false;
            else
                flag = true;
            if(flag != _bGameModeWait)
                sendStatechanged();
        }
    }

    private void send_heartbeat(int i)
    {
        if(master_socket == null || master_address == null || MASTER_PORT == 0)
            return;
        outBuf.append("\\heartbeat\\");
        outBuf.append(qport);
        outBuf.append("\\gamename\\");
        outBuf.append("il2sturmovikfb");
        if(i != 0)
        {
            outBuf.append("\\statechanged\\");
            outBuf.append(i);
        }
        int j = outBuf.length();
        for(int k = 0; k < j; k++)
            _outBuf[k] = (byte)(outBuf.charAt(k) & 0xff);

        outBuf.delete(0, j);
        com.maddox.rts.RTSConf.cur.netEnv.postExt(_outBuf, 0, j, master_socket, master_address, MASTER_PORT);
    }

    private void packet_send()
    {
        if(outBuf.length() == 0)
            return;
        packetnumber++;
        outBuf.append("\\queryid\\");
        outBuf.append(queryid);
        outBuf.append(".");
        outBuf.append(packetnumber);
        int i = outBuf.length();
        for(int j = 0; j < i; j++)
            _outBuf[j] = (byte)(outBuf.charAt(j) & 0xff);

        outBuf.delete(0, i);
        com.maddox.rts.RTSConf.cur.netEnv.postExt(_outBuf, 0, i, in_socket, in_address, in_port);
    }

    private void strcat(java.lang.StringBuffer stringbuffer, java.lang.StringBuffer stringbuffer1)
    {
        strcat(stringbuffer, stringbuffer1, 0, stringbuffer1.length());
    }

    private void strcat(java.lang.StringBuffer stringbuffer, java.lang.StringBuffer stringbuffer1, int i, int j)
    {
        for(int k = 0; k < j; k++)
            stringbuffer.append(stringbuffer1.charAt(i + k));

    }

    private void buffer_send(java.lang.StringBuffer stringbuffer)
    {
        buffer_send(stringbuffer, 0, stringbuffer.length());
    }

    private void buffer_send(java.lang.StringBuffer stringbuffer, int i, int j)
    {
        if(outBuf.length() + j < 1350)
            strcat(outBuf, stringbuffer, i, j);
        else
        if(j > 1350)
        {
            int k = 0;
            int l = 0;
            int i1 = 0;
            for(; k < 1350; k++)
                if('\\' == stringbuffer.charAt(i + k))
                {
                    if(i1 % 2 == 0)
                        l = k;
                    i1++;
                }

            if(l == 0)
                return;
            buffer_send(stringbuffer, 0, l);
            buffer_send(stringbuffer, l, j - l);
        } else
        {
            packet_send();
            strcat(outBuf, stringbuffer, i, j);
        }
    }

    private void setQuery(byte abyte0[])
    {
        queryBuf.delete(0, queryBuf.length());
        for(int i = 0; i < abyte0.length; i++)
            queryBuf.append((char)abyte0[i]);

    }

    private boolean isExistQueryKey(int i)
    {
        java.lang.String s = queries[i];
        int j = queryBuf.length();
        int k = s.length();
        int l = j - k;
        int i1 = 0;
        while(i1 <= l) 
        {
            char c = queryBuf.charAt(i1++);
            if(c != '\\')
                continue;
            int j1;
            for(j1 = 0; j1 < k; j1++)
            {
                char c2 = s.charAt(j1);
                c = queryBuf.charAt(i1++);
                if(c == '\\')
                {
                    i1--;
                    break;
                }
                if(c != c2)
                    break;
            }

            if(j1 != k)
                continue;
            queryVal_0 = queryVal_1 = 0;
            if(i1 == j)
                return true;
            c = queryBuf.charAt(i1++);
            if(c == '\\')
            {
                if(i1 == j)
                    return true;
                queryVal_0 = i1;
                while(i1 < j) 
                {
                    char c1 = queryBuf.charAt(i1++);
                    if(c1 == '\\')
                        break;
                }
                queryVal_1 = i1 - 1;
                return true;
            }
            break;
        }
        return false;
    }

    private java.lang.String getQueryValue()
    {
        return queryBuf.substring(queryVal_0, queryVal_1);
    }

    public void msgNetExt(byte abyte0[], com.maddox.rts.NetSocket netsocket, com.maddox.rts.NetAddress netaddress, int i)
    {
        if(abyte0 == null || abyte0.length < 1)
            return;
        if((char)abyte0[0] != '\\')
            return;
        if(com.maddox.il2.game.Main.cur().netServerParams == null)
            return;
        if(((com.maddox.il2.net.Connect)com.maddox.rts.NetEnv.cur().connect).banned.isExist(netaddress))
            return;
        in_socket = netsocket;
        in_address = netaddress;
        in_port = i;
        java.lang.String s = null;
        queryid++;
        packetnumber = 0;
        if(no_query > 0)
            no_query = 0;
        outBuf.delete(0, outBuf.length());
        setQuery(abyte0);
        for(int j = 0; j <= 7; j++)
            if(isExistQueryKey(j))
                switch(j)
                {
                case 0: // '\0'
                    send_basic();
                    break;

                case 1: // '\001'
                    send_info();
                    break;

                case 2: // '\002'
                    send_rules();
                    break;

                case 3: // '\003'
                    send_players();
                    break;

                case 4: // '\004'
                    send_basic();
                    send_info();
                    send_rules();
                    send_players();
                    break;

                case 5: // '\005'
                    send_basic();
                    packet_send();
                    send_info();
                    packet_send();
                    send_rules();
                    packet_send();
                    send_players();
                    break;

                case 6: // '\006'
                    send_echo(getQueryValue());
                    break;

                case 7: // '\007'
                    s = getQueryValue();
                    break;
                }

        send_final(s);
        in_socket = null;
        in_address = null;
    }

    private int encode_ct(int i)
    {
        if(i < 26)
            return 65 + i;
        if(i < 52)
            return (97 + i) - 26;
        if(i < 62)
            return (48 + i) - 52;
        if(i == 62)
            return 43;
        return i != 63 ? 0 : 47;
    }

    private int gs_encode(char ac[], int i, char ac1[])
    {
        int i1 = 0;
        int j1 = 0;
        for(int j = 0; j < i;)
        {
            for(int k = 0; k <= 2;)
            {
                if(j < i)
                    _trip[k] = ac[i1++];
                else
                    _trip[k] = 0;
                k++;
                j++;
            }

            _kwart[0] = _trip[0] >> 2;
            _kwart[1] = ((_trip[0] & 3) << 4) + (_trip[1] >> 4);
            _kwart[2] = ((_trip[1] & 0xf) << 2) + (_trip[2] >> 6);
            _kwart[3] = _trip[2] & 0x3f;
            for(int l = 0; l <= 3; l++)
                ac1[j1++] = (char)(encode_ct(_kwart[l]) & 0xff);

        }

        return j1;
    }

    private void gs_encrypt(java.lang.String s, char ac[], int i)
    {
        int j = s.length();
        for(int k = 0; k < 256; k++)
            _state[k] = (char)k;

        int j1 = 0;
        int k1 = 0;
        for(int l = 0; l < 256; l++)
        {
            k1 = (s.charAt(j1) + _state[l] + k1) % 256;
            j1 = (j1 + 1) % j;
            char c = _state[k1];
            _state[k1] = _state[l];
            _state[l] = c;
        }

        j1 = 0;
        k1 = 0;
        for(int i1 = 0; i1 < i; i1++)
        {
            j1 = (j1 + ac[i1] + 1) % 256;
            k1 = (_state[j1] + k1) % 256;
            char c1 = _state[k1];
            _state[k1] = _state[j1];
            _state[j1] = c1;
            int l1 = (_state[j1] + _state[k1]) % 256;
            ac[i1] ^= _state[l1];
            ac[i1] &= '\377';
        }

    }

    public java.lang.String userName;
    public java.lang.String serverIP;
    public java.lang.String roomName;
    public java.lang.String gameType;
    public int maxClients;
    private static final java.lang.String gamename = "il2sturmovikfb";
    private static final java.lang.String gamever = "4.09m";
    private static final java.lang.String secret_key = "h53Ew8";
    public static int MASTER_PORT = 27900;
    public static java.lang.String MASTER_ADDR = "master.gamespy.com";
    private static final int MAX_DATA_SIZE = 1400;
    private static final int FIRST_HB_TIME = 30000;
    private static final int HB_TIME = 0x493e0;
    private static final int MAX_FIRST_COUNT = 10;
    private int queryid;
    private int packetnumber;
    private int qport;
    private int no_query;
    private java.lang.String room;
    private java.lang.StringBuffer strBuf;
    private boolean _bGameModeWait;
    private int armyScore[];
    private char _encrypted_val[];
    private char _encoded_val[];
    private com.maddox.rts.MsgTimeOut ticker;
    private com.maddox.rts.NetSocket master_socket;
    private com.maddox.rts.NetAddress master_address;
    private com.maddox.rts.NetSocket in_socket;
    private com.maddox.rts.NetAddress in_address;
    private int in_port;
    private java.lang.StringBuffer outBuf;
    private byte _outBuf[];
    private static final java.lang.String queries[] = {
        "basic", "info", "rules", "players", "status", "packets", "echo", "secure"
    };
    private static final int qtbasic = 0;
    private static final int qtinfo = 1;
    private static final int qtrules = 2;
    private static final int qtplayers = 3;
    private static final int qtstatus = 4;
    private static final int qtpackets = 5;
    private static final int qtecho = 6;
    private static final int qtsecure = 7;
    private java.lang.StringBuffer queryBuf;
    private int queryVal_0;
    private int queryVal_1;
    int _trip[];
    int _kwart[];
    private char _state[];

}
