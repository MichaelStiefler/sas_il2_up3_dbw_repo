// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GameWin3D.java

package com.maddox.il2.game;

import com.maddox.gwindow.GCaption;
import com.maddox.il2.engine.Config;
import com.maddox.il2.gui.GUIInfoName;
import com.maddox.il2.gui.GUIMainMenu;
import com.maddox.il2.gui.GUIPocket;
import com.maddox.il2.net.GameSpy;
import com.maddox.il2.net.USGS;
import com.maddox.rts.IniFile;

// Referenced classes of package com.maddox.il2.game:
//            MainWin3D, Main, GameStateStack, GameState

public class GameWin3D extends com.maddox.il2.game.MainWin3D
{

    public GameWin3D()
    {
    }

    public void onBeginApp()
    {
        boolean flag = true;
        boolean flag1 = false;
        if(com.maddox.il2.net.USGS.mode() == 1)
            com.maddox.il2.game.Main.stateStack().push(34);
        else
        if(com.maddox.il2.net.USGS.mode() == 2)
            com.maddox.il2.game.Main.stateStack().push(35);
        else
        if(netGameSpy != null)
        {
            if(netGameSpy.isServer())
                com.maddox.il2.game.Main.stateStack().push(35);
            else
                com.maddox.il2.game.Main.stateStack().push(34);
            flag = false;
            com.maddox.il2.gui.GUIMainMenu guimainmenu = (com.maddox.il2.gui.GUIMainMenu)com.maddox.il2.game.GameState.get(2);
            guimainmenu.pPilotName.cap = new GCaption(netGameSpy.userName);
            com.maddox.il2.gui.GUIInfoName.nickName = netGameSpy.userName;
        } else
        {
            int i = com.maddox.il2.engine.Config.cur.ini.get("game", "Intro", 0);
            if(i == 1)
            {
                com.maddox.il2.game.Main.stateStack().push(58);
                flag1 = true;
            }
            flag = false;
        }
        if(netGameSpy == null)
        {
            netGameSpyListener = new GameSpy();
            if(com.maddox.il2.net.USGS.isUsed())
                netGameSpyListener.setListenerOnly(com.maddox.il2.net.USGS.room);
            else
                netGameSpyListener.setListenerOnly(null);
        }
        if(flag)
        {
            com.maddox.il2.gui.GUIMainMenu guimainmenu1 = (com.maddox.il2.gui.GUIMainMenu)com.maddox.il2.game.GameState.get(2);
            guimainmenu1.pPilotName.cap = new GCaption(com.maddox.il2.net.USGS.name);
            com.maddox.il2.gui.GUIInfoName.nickName = com.maddox.il2.net.USGS.name;
        }
        if(!flag1)
            com.maddox.il2.game.GameWin3D.menuMusicPlay();
    }

    private static boolean tryStartGS()
    {
        if(args == null)
            return true;
        if(args.length > 0 && "/GS:StartedByGS".equals(args[0]))
            return com.maddox.il2.net.USGS.tryStartCSP();
        int i = -1;
        int j = 0;
        do
        {
            if(j >= args.length)
                break;
            if("GS".equals(args[j]))
            {
                i = j;
                break;
            }
            j++;
        } while(true);
        if(i == -1)
            return true;
        java.lang.String s = null;
        java.lang.String s1 = null;
        java.lang.String s2 = null;
        java.lang.String s3 = null;
        java.lang.String s4 = null;
        for(int k = i + 1; k < args.length - 1; k++)
        {
            if("-name".equals(args[k]))
            {
                s = args[k + 1];
                k++;
                continue;
            }
            if("-room".equals(args[k]))
            {
                s1 = args[k + 1];
                k++;
                continue;
            }
            if("-type".equals(args[k]))
            {
                s2 = args[k + 1];
                k++;
                continue;
            }
            if("-maxclients".equals(args[k]))
            {
                s3 = args[k + 1];
                k++;
            }
        }

        if(i + 1 < args.length && !args[i + 1].startsWith("-"))
            s4 = args[i + 1];
        return com.maddox.il2.net.USGS.tryStart(s, s1, s2, s3, s4);
    }

    private void startServerGameSpy()
    {
        com.maddox.il2.net.GameSpy gamespy = new GameSpy();
        for(int i = 0; i < args.length; i++)
        {
            if("-room".equals(args[i]) && i + 1 < args.length)
                gamespy.roomName = args[i + 1];
            if("-name".equals(args[i]) && i + 1 < args.length)
                gamespy.userName = args[i + 1];
            if("-type".equals(args[i]) && i + 1 < args.length)
                gamespy.gameType = args[i + 1];
            if("-maxclients".equals(args[i]) && i + 1 < args.length)
                try
                {
                    gamespy.maxClients = java.lang.Integer.parseInt(args[i + 1]);
                    if(gamespy.maxClients < 2)
                        gamespy.maxClients = 2;
                    if(gamespy.maxClients > 32)
                        gamespy.maxClients = 32;
                }
                catch(java.lang.Exception exception)
                {
                    gamespy.maxClients = 16;
                }
            if(!"-master".equals(args[i]) || i + 1 >= args.length)
                continue;
            java.lang.String s = args[i + 1];
            int j = s.indexOf(":");
            if(j >= 0)
            {
                if(j > 0)
                    com.maddox.il2.net.GameSpy.MASTER_ADDR = s.substring(0, j);
                try
                {
                    com.maddox.il2.net.GameSpy.MASTER_PORT = java.lang.Integer.parseInt(s.substring(j + 1));
                }
                catch(java.lang.Exception exception1) { }
            } else
            {
                com.maddox.il2.net.GameSpy.MASTER_ADDR = s;
            }
        }

        if(gamespy.userName == null)
            gamespy.userName = args[0];
        netGameSpy = gamespy;
    }

    private void startClientGameSpy()
    {
        com.maddox.il2.net.GameSpy gamespy = new GameSpy();
        for(int i = 0; i < args.length; i++)
        {
            if("-connect".equals(args[i]) && i + 1 < args.length)
                gamespy.serverIP = args[i + 1];
            if("-name".equals(args[i]) && i + 1 < args.length)
                gamespy.userName = args[i + 1];
        }

        if(gamespy.serverIP == null)
            gamespy.serverIP = args[0];
        netGameSpy = gamespy;
    }

    public static void main(java.lang.String args1[])
    {
        args = args1;
        if(!com.maddox.il2.game.GameWin3D.tryStartGS())
            return;
        com.maddox.il2.game.GameWin3D gamewin3d = new GameWin3D();
        if(!com.maddox.il2.net.USGS.isUsed() && args != null)
        {
            boolean flag = false;
            boolean flag1 = false;
            for(int i = 0; i < args.length; i++)
            {
                if("-room".equals(args[i]))
                    flag1 = true;
                if("-name".equals(args[i]))
                    flag = true;
            }

            if(flag)
                ((com.maddox.il2.game.GameWin3D)gamewin3d).startClientGameSpy();
            else
            if(flag1)
                ((com.maddox.il2.game.GameWin3D)gamewin3d).startServerGameSpy();
        }
        com.maddox.il2.game.GameWin3D _tmp = gamewin3d;
        com.maddox.il2.game.Main.exec(gamewin3d, "conf.ini", "il2", 1);
    }

    private static java.lang.String args[];
}
