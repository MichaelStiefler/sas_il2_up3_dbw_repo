// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   USGS.java

package com.maddox.il2.net;

import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;

// Referenced classes of package com.maddox.il2.net:
//            NetServerParams

public class USGS
{

    public USGS()
    {
    }

    public static int mode()
    {
        return mode;
    }

    public static boolean isUsed()
    {
        return mode != 0 && mode != 4;
    }

    public static boolean isUsing()
    {
        return com.maddox.il2.net.USGS.isUsed() || prev_mode != 0;
    }

    public static void setMode(int i)
    {
        mode = i;
    }

    public static void serverReady(int i)
    {
        if(csp_state == 3 && version == 3)
        {
            int j = com.maddox.il2.net.USGS.cspPostMessage(512, i);
            int k;
            do
            {
                try
                {
                    java.lang.Thread.sleep(10L);
                }
                catch(java.lang.Exception exception) { }
                k = com.maddox.il2.net.USGS.cspGetMessage();
            } while(k <= 0);
        }
    }

    public static boolean tryStartCSP()
    {
        int i = 0;
        i = com.maddox.il2.net.USGS.cspInitialize();
        if(i != 0)
            return false;
        i = com.maddox.il2.net.USGS.cspConnect(5000);
        if(i != 0)
        {
            com.maddox.il2.net.USGS.cspUninitialize();
            return false;
        }
        csp_state = 1;
        do
        {
            int j;
            do
            {
                try
                {
                    java.lang.Thread.sleep(10L);
                }
                catch(java.lang.Exception exception) { }
                j = com.maddox.il2.net.USGS.cspGetMessage();
            } while(j <= 0);
            switch(csp_state)
            {
            case 1: // '\001'
                if(j == 0x10005)
                {
                    csp_state = 0;
                    return false;
                }
                if(j == 0x10003)
                {
                    name = com.maddox.il2.net.USGS.cspUserName();
                    room = com.maddox.il2.net.USGS.cspRoomName();
                    int k = com.maddox.il2.net.USGS.cspGamePort();
                    if(k == 0)
                        k = 21000;
                    serverIP = com.maddox.il2.net.USGS.cspPrimaryGameServerIP() + ":" + k;
                    csp_state = 4;
                    com.maddox.il2.net.USGS.cspPostMessage(3, 0);
                } else
                if(j == 0x10001)
                {
                    name = com.maddox.il2.net.USGS.cspUserName();
                    room = com.maddox.il2.net.USGS.cspRoomName();
                    java.lang.String s = com.maddox.il2.net.USGS.cspGameName();
                    if(s.indexOf("COOP") > 0)
                        bGameDfight = false;
                    else
                        bGameDfight = true;
                    csp_state = 2;
                    com.maddox.il2.net.USGS.cspPostMessage(1, 0);
                }
                break;

            case 2: // '\002'
                if(j == 0x10002)
                {
                    csp_state = 3;
                    version = 3;
                    com.maddox.il2.net.USGS.setMode(2);
                    maxclients = com.maddox.il2.net.USGS.cspMaxPlayer();
                    if(maxclients < 2)
                        maxclients = 2;
                    if(maxclients > 32)
                        maxclients = 32;
                    return true;
                }
                break;

            case 4: // '\004'
                if(j == 0x10004)
                {
                    csp_state = 5;
                    version = 3;
                    com.maddox.il2.net.USGS.setMode(1);
                    return true;
                }
                break;
            }
        } while(true);
    }

    public static boolean tryStart(java.lang.String s, java.lang.String s1, java.lang.String s2, java.lang.String s3, java.lang.String s4)
    {
        if(s != null)
        {
            if(s1 != null || s2 != null || s3 != null)
            {
                version = 2;
                com.maddox.il2.net.USGS.setMode(2);
                name = s;
                room = s1;
                if("coop".equals(s2))
                    bGameDfight = false;
                else
                    bGameDfight = true;
                try
                {
                    maxclients = java.lang.Integer.parseInt(s3);
                    if(maxclients < 2)
                        maxclients = 2;
                    if(maxclients > 32)
                        maxclients = 32;
                }
                catch(java.lang.Exception exception) { }
            } else
            if(s4 != null)
            {
                version = 2;
                com.maddox.il2.net.USGS.setMode(1);
                name = s;
                serverIP = s4;
            }
            return true;
        } else
        {
            return false;
        }
    }

    private static native java.lang.String cspUserName();

    private static native java.lang.String cspUserPassword();

    private static native java.lang.String cspGameName();

    private static native int cspLobbyServerID();

    private static native int cspGroupID();

    private static native java.lang.String cspRoomName();

    private static native java.lang.String cspRoomPassword();

    private static native int cspMaxPlayer();

    private static native java.lang.String cspPrimaryGameServerIP();

    private static native java.lang.String cspSecondaryGameServerIP();

    private static native int cspGamePort();

    private static native int cspErrorMsgIDSource();

    private static native int cspErrorCode();

    private static native int cspGetMessage();

    private static native int cspPostMessage(int i, int j);

    private static native int cspInitialize();

    private static native void cspUninitialize();

    private static native int cspConnect(int i);

    private static native void cspDisconnect();

    public static boolean tryStartDedicated(java.lang.String s, java.lang.String s1, java.lang.String s2, java.lang.String s3, java.lang.String s4, java.lang.String s5)
    {
        int i;
        try
        {
            i = java.lang.Integer.parseInt(s4);
        }
        catch(java.lang.Exception exception)
        {
            return false;
        }
        int j;
        try
        {
            j = java.lang.Integer.parseInt(s5);
        }
        catch(java.lang.Exception exception1)
        {
            return false;
        }
        if(!com.maddox.il2.net.USGS.rsInitialize())
            return false;
        if(!com.maddox.il2.net.USGS.rsLobbyServerConnection(s3, i))
            return false;
        com.maddox.il2.net.USGS.rsEngine();
        if(!com.maddox.il2.net.USGS.rsLobbyServerLogin(s, j))
            return false;
        do
        {
            int k = com.maddox.il2.net.USGS.rsResLobbyServerLogin();
            if(k != 0)
            {
                if(k == 1)
                    break;
                if(k == 2)
                {
                    com.maddox.il2.net.USGS.rsLobbyServerClose();
                    com.maddox.il2.net.USGS.rsUninitialize();
                    return false;
                }
            }
            com.maddox.il2.net.USGS.rsEngine();
            try
            {
                java.lang.Thread.sleep(10L);
            }
            catch(java.lang.Exception exception3) { }
        } while(true);
        name = s;
        room = s1;
        bGameDfight = true;
        try
        {
            maxclients = java.lang.Integer.parseInt(s2);
            if(maxclients < 2)
                maxclients = 2;
            if(maxclients > 32)
                maxclients = 32;
        }
        catch(java.lang.Exception exception2) { }
        version = 2;
        com.maddox.il2.net.USGS.setMode(3);
        return true;
    }

    public static void engine()
    {
        if(com.maddox.il2.net.USGS.mode() == 3)
            com.maddox.il2.net.USGS.rsEngine();
    }

    public static void stopDedicated()
    {
        if(com.maddox.il2.net.USGS.mode() == 3)
        {
            com.maddox.il2.net.USGS.rsLobbyServerClose();
            com.maddox.il2.net.USGS.rsUninitialize();
            com.maddox.il2.net.USGS.setMode(4);
        }
    }

    public static void update()
    {
        if(com.maddox.il2.net.USGS.mode() != 3)
            return;
        java.lang.String s = "";
        if(com.maddox.il2.game.Mission.isPlaying())
            s = com.maddox.il2.game.Mission.cur().name();
        java.lang.String s1 = "dogfight";
        java.lang.String s2 = "openplaying";
        com.maddox.il2.ai.DifficultySettings difficultysettings = new DifficultySettings();
        difficultysettings.set(com.maddox.il2.game.Main.cur().netServerParams.getDifficulty());
        com.maddox.il2.net.USGS.rsUpdateGroup(s, s1, s2, difficultysettings.Wind_N_Turbulence, difficultysettings.Flutter_Effect, difficultysettings.Stalls_N_Spins, difficultysettings.Blackouts_N_Redouts, difficultysettings.Engine_Overheat, difficultysettings.Torque_N_Gyro_Effects, difficultysettings.Realistic_Landings, difficultysettings.Takeoff_N_Landing, difficultysettings.Cockpit_Always_On, difficultysettings.No_Outside_Views, difficultysettings.No_Padlock, difficultysettings.Head_Shake, difficultysettings.No_Icons, difficultysettings.No_Map_Icons, difficultysettings.Realistic_Gunnery, difficultysettings.Limited_Ammo, difficultysettings.Limited_Fuel, difficultysettings.Vulnerability, difficultysettings.Clouds, difficultysettings.NoMinimapPath, difficultysettings.NoSpeedBar, difficultysettings.NoInstantSuccess, difficultysettings.SeparateEStart, difficultysettings.ComplexEManagement);
    }

    private static native boolean rsInitialize();

    private static native boolean rsUninitialize();

    private static native void rsEngine();

    private static native boolean rsLobbyServerConnection(java.lang.String s, int i);

    private static native boolean rsLobbyServerLogin(java.lang.String s, int i);

    private static native int rsResLobbyServerLogin();

    private static native void rsLobbyServerClose();

    private static native void rsUpdateGroup(java.lang.String s, java.lang.String s1, java.lang.String s2, boolean flag, boolean flag1, boolean flag2, boolean flag3, boolean flag4, 
            boolean flag5, boolean flag6, boolean flag7, boolean flag8, boolean flag9, boolean flag10, boolean flag11, 
            boolean flag12, boolean flag13, boolean flag14, boolean flag15, boolean flag16, boolean flag17, boolean flag18, 
            boolean flag19, boolean flag20, boolean flag21, boolean flag22, boolean flag23);

    public static int version = 2;
    public static boolean bGameDfight = true;
    public static java.lang.String name = null;
    public static java.lang.String room = null;
    public static int maxclients = 16;
    public static java.lang.String serverIP = null;
    public static final int MODE_NONE = 0;
    public static final int MODE_CLIENT = 1;
    public static final int MODE_LOCAL_SERVER = 2;
    public static final int MODE_SERVER = 3;
    public static final int MODE_DISABLED = 4;
    private static int mode = 0;
    private static int prev_mode = 0;
    public static final int CSP_STATE_DEAD = 0;
    public static final int CSP_STATE_PRELOAD = 1;
    public static final int CSP_STATE_INITMASTER = 2;
    public static final int CSP_STATE_MASTER = 3;
    public static final int CSP_STATE_INITCLIENT = 4;
    public static final int CSP_STATE_CLIENT = 5;
    private static int csp_state = 0;
    private static final int MSGFGS_CHSTA_INITMASTERSESSION = 0x10001;
    private static final int MSGFGS_CHSTA_MASTERSESSION = 0x10002;
    private static final int MSGFGS_CHSTA_INITCLIENTSESSION = 0x10003;
    private static final int MSGFGS_CHSTA_CLIENTSESSION = 0x10004;
    private static final int MSGFGS_CHSTA_TERMINATE = 0x10005;
    private static final int MSGFGS_SWITCHTOGS_AK = 0x10100;
    private static final int MSGFGS_READYTORECEIVECONNECTIONS_AK = 0x10200;
    private static final int MSGFGS_ERROR = 0x11000;
    private static final int MSGFG_CHSTA_INITMASTERSESSION_AK = 1;
    private static final int MSGFG_CHSTA_MASTERSESSION_AK = 2;
    private static final int MSGFG_CHSTA_INITCLIENTSESSION_AK = 3;
    private static final int MSGFG_CHSTA_CLIENTSESSION_AK = 4;
    private static final int MSGFG_SWITCHTOGS = 256;
    private static final int MSGFG_READYTORECEIVECONNECTIONS = 512;
    private static final int MSGFG_LOGOUTANDKILLUBICLIENT = 768;

    static 
    {
        java.lang.System.loadLibrary("il2_usgs2");
    }
}
