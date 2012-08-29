// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetNewServer.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.GameSpy;
import com.maddox.il2.net.NetLocalControl;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUSGSControl;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.USGS;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HomePath;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.NetSocket;
import java.io.File;
import java.util.List;

// Referenced classes of package com.maddox.il2.gui:
//            GUIMainMenu, GUIClient, GUIInfoMenu, GUIInfoName, 
//            GUILookAndFeel, GUIButton, GUI, GUIChatDialog, 
//            GUIPocket, GUIDialogClient, GUISeparate

public class GUINetNewServer extends com.maddox.il2.game.GameState
{
    public class DlgPassword extends com.maddox.gwindow.GWindowFramed
    {

        public boolean doOk()
        {
            java.lang.String s = pw0.getValue();
            java.lang.String s1 = pw1.getValue();
            if(s.equals(s1))
            {
                if("".equals(s))
                    password = null;
                else
                    password = s;
                return true;
            } else
            {
                new GWindowMessageBox(root, 22F, true, i18n("netns.Pwd"), i18n("netns.PwdIncorrect"), 3, 0.0F);
                return false;
            }
        }

        public void doCancel()
        {
        }

        public void afterCreated()
        {
            clientWindow = create(new com.maddox.gwindow.GWindowDialogClient() {

                public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
                {
                    if(i != 2)
                        return super.notify(gwindow, i, j);
                    if(gwindow == bOk)
                    {
                        if(doOk())
                            close(false);
                        return true;
                    }
                    if(gwindow == bCancel)
                    {
                        doCancel();
                        close(false);
                        return true;
                    } else
                    {
                        return super.notify(gwindow, i, j);
                    }
                }

            }
);
            com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)clientWindow;
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 10F, 1.5F, i18n("netns.Password_") + " ", null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 10F, 1.5F, i18n("netns.ConfirmPassword") + " ", null));
            gwindowdialogclient.addControl(pw0 = new GWindowEditControl(gwindowdialogclient, 12F, 1.0F, 8F, 1.5F, null));
            gwindowdialogclient.addControl(pw1 = new GWindowEditControl(gwindowdialogclient, 12F, 3F, 8F, 1.5F, null));
            pw0.bPassword = pw1.bPassword = true;
            gwindowdialogclient.addDefault(bOk = new GWindowButton(gwindowdialogclient, 4F, 6F, 6F, 2.0F, i18n("netns.Ok"), null));
            gwindowdialogclient.addEscape(bCancel = new GWindowButton(gwindowdialogclient, 12F, 6F, 6F, 2.0F, i18n("netns.Cancel"), null));
            if(password != null)
            {
                pw0.setValue(password, false);
                pw1.setValue(password, false);
            }
            super.afterCreated();
            resized();
            showModal();
        }

        com.maddox.gwindow.GWindowEditControl pw0;
        com.maddox.gwindow.GWindowEditControl pw1;
        com.maddox.gwindow.GWindowButton bOk;
        com.maddox.gwindow.GWindowButton bCancel;

        public DlgPassword(com.maddox.gwindow.GWindow gwindow)
        {
            bSizable = false;
            title = i18n("netns.EnterPassword");
            float f = 22F;
            float f1 = 12F;
            float f2 = gwindow.win.dx / gwindow.lookAndFeel().metric();
            float f3 = gwindow.win.dy / gwindow.lookAndFeel().metric();
            float f4 = (f2 - f) / 2.0F;
            float f5 = (f3 - f1) / 2.0F;
            doNew(gwindow, f4, f5, f, f1, true);
        }
    }

    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bStart)
            {
                doNewLocal();
                return true;
            }
            if(gwindow == cGameType)
            {
                fillPlayers();
                return true;
            }
            if(gwindow == bPassword)
            {
                new DlgPassword(root);
                return true;
            }
            if(gwindow == bExit)
            {
                doExit();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(88F), x1024(800F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(248F), x1024(800F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(32F), y1024(32F), x1024(160F), y1024(32F), 2, i18n("netns.Name"));
            draw(x1024(576F), y1024(32F), x1024(256F), y1024(32F), 0, i18n("netns.Password"));
            draw(x1024(144F), y1024(120F), x1024(400F), y1024(32F), 2, i18n("netns.GameType"));
            draw(x1024(144F), y1024(184F), x1024(400F), y1024(32F), 2, i18n("netns.Max.Players"));
            if(com.maddox.il2.game.Main.cur().netGameSpy != null)
                draw(x1024(96F), y1024(280F), x1024(256F), y1024(48F), 0, i18n("main.Quit"));
            else
                draw(x1024(96F), y1024(280F), x1024(256F), y1024(48F), 0, i18n("netns.MainMenu"));
            draw(x1024(464F), y1024(280F), x1024(304F), y1024(48F), 2, i18n("netns.Create"));
        }

        public void setPosSize()
        {
            set1024PosSize(80F, 216F, 864F, 360F);
            wName.setPosSize(x1024(208F), y1024(32F), x1024(288F), y1024(32F));
            bPassword.setPosC(x1024(536F), y1024(48F));
            cGameType.setPosSize(x1024(560F), y1024(120F), x1024(272F), y1024(32F));
            cPlayers.setPosSize(x1024(560F), y1024(184F), x1024(272F), y1024(32F));
            bExit.setPosC(x1024(56F), y1024(304F));
            bStart.setPosC(x1024(808F), y1024(304F));
        }

        public DialogClient()
        {
        }
    }


    public void _enter()
    {
        if(com.maddox.il2.net.USGS.isUsed())
        {
            ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).reset();
            com.maddox.rts.CmdEnv.top().exec("socket LISTENER 1");
            int i = 31;
            com.maddox.rts.NetEnv.cur();
            com.maddox.rts.NetEnv.host().setShortName(com.maddox.il2.net.USGS.name);
            i = com.maddox.il2.net.USGS.maxclients - 1;
            java.lang.String s = "socket udp CREATE LOCALPORT " + com.maddox.il2.engine.Config.cur.netLocalPort + " SPEED " + com.maddox.il2.engine.Config.cur.netSpeed + " CHANNELS " + i;
            if(com.maddox.il2.engine.Config.cur.netLocalHost != null && com.maddox.il2.engine.Config.cur.netLocalHost.length() > 0)
                s = s + " LOCALHOST " + com.maddox.il2.engine.Config.cur.netLocalHost;
            com.maddox.rts.CmdEnv.top().exec(s);
            new NetServerParams();
            com.maddox.il2.game.Main.cur().netServerParams.setType(48);
            com.maddox.il2.game.Main.cur().netServerParams.setServerName(com.maddox.il2.net.USGS.room);
            if("".equals(com.maddox.il2.game.Main.cur().netServerParams.serverName()))
                com.maddox.il2.game.Main.cur().netServerParams.setServerName(com.maddox.rts.NetEnv.host().shortName());
            com.maddox.il2.game.Main.cur().netServerParams.serverDescription = com.maddox.il2.engine.Config.cur.netServerDescription;
            com.maddox.il2.game.Main.cur().netServerParams.setPassword(null);
            new NetUSGSControl();
            int k = 0;
            if(!com.maddox.il2.net.USGS.bGameDfight)
                k = 1;
            com.maddox.il2.game.Main.cur().netServerParams.setMode(k);
            com.maddox.il2.game.Main.cur().netServerParams.setDifficulty(com.maddox.il2.ai.World.cur().diffUser.get());
            com.maddox.il2.game.Main.cur().netServerParams.setMaxUsers(i + 1);
            new Chat();
            com.maddox.il2.net.USGS.serverReady(com.maddox.il2.engine.Config.cur.netLocalPort);
            com.maddox.il2.game.Main.cur().netServerParams.bNGEN = false;
            com.maddox.il2.game.Main.stateStack().change(38);
            com.maddox.il2.gui.GUI.chatDlg.showWindow();
            return;
        }
        if(com.maddox.il2.game.Main.cur().netGameSpy != null)
        {
            com.maddox.il2.engine.Config.cur.netServerChannels = com.maddox.il2.game.Main.cur().netGameSpy.maxClients - 1;
            if("coop".equals(com.maddox.il2.game.Main.cur().netGameSpy.gameType))
                cGameType.setSelected(1, true, false);
            else
                cGameType.setSelected(0, true, false);
        }
        if(com.maddox.il2.engine.Config.cur.netServerChannels < 1)
            com.maddox.il2.engine.Config.cur.netServerChannels = 1;
        if(com.maddox.il2.engine.Config.cur.netServerChannels > 31)
            com.maddox.il2.engine.Config.cur.netServerChannels = 31;
        fillPlayers();
        int j = com.maddox.il2.engine.Config.cur.netServerChannels - 1;
        cPlayers.setSelected(j, true, false);
        if(com.maddox.il2.game.Main.cur().netGameSpy != null)
        {
            wName.setValue(com.maddox.il2.game.Main.cur().netGameSpy.roomName, false);
            wName.setEditable(false);
        } else
        if("".equals(wName.getValue()))
            if("".equals(com.maddox.il2.engine.Config.cur.netServerName))
                wName.setValue(com.maddox.rts.NetEnv.host().shortName(), false);
            else
                wName.setValue(com.maddox.il2.engine.Config.cur.netServerName, false);
        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).reset();
        client.activateWindow();
    }

    public void _leave()
    {
        if(com.maddox.il2.net.USGS.isUsing())
            return;
        if(com.maddox.il2.game.Main.cur().netGameSpy == null)
            com.maddox.il2.engine.Config.cur.netServerName = wName.getValue();
        client.hideWindow();
    }

    private void fillPlayers()
    {
        int i = cPlayers.getSelected();
        if(i < 0)
            i = 0;
        cPlayers.clear(false);
        byte byte0 = 32;
        for(int j = 2; j <= byte0; j++)
            cPlayers.add("" + j);

        cPlayers.setSelected(i, true, false);
    }

    private void doNewServer(int i)
    {
        com.maddox.il2.engine.Config.cur.netServerChannels = cPlayers.getSelected() + 1;
        com.maddox.rts.CmdEnv.top().exec("socket LISTENER 1");
        com.maddox.rts.NetEnv.cur();
        com.maddox.rts.NetEnv.host().setShortName(com.maddox.il2.ai.World.cur().userCfg.callsign);
        java.lang.String s = "socket udp CREATE LOCALPORT " + com.maddox.il2.engine.Config.cur.netLocalPort + " SPEED " + com.maddox.il2.engine.Config.cur.netSpeed + " CHANNELS " + com.maddox.il2.engine.Config.cur.netServerChannels;
        if(com.maddox.il2.engine.Config.cur.netLocalHost != null && com.maddox.il2.engine.Config.cur.netLocalHost.length() > 0)
            s = s + " LOCALHOST " + com.maddox.il2.engine.Config.cur.netLocalHost;
        com.maddox.rts.CmdEnv.top().exec(s);
        new NetServerParams();
        if(com.maddox.il2.game.Main.cur().netGameSpy != null)
        {
            java.util.List list = com.maddox.rts.NetEnv.socketsBlock();
            if(list != null && list.size() > 0)
                com.maddox.il2.game.Main.cur().netGameSpy.set(com.maddox.il2.game.Main.cur().netGameSpy.roomName, (com.maddox.rts.NetSocket)list.get(0), com.maddox.il2.engine.Config.cur.netLocalPort);
            com.maddox.il2.game.Main.cur().netServerParams.setType(32);
            com.maddox.il2.game.Main.cur().netServerParams.setServerName(com.maddox.il2.game.Main.cur().netGameSpy.roomName);
            com.maddox.rts.NetEnv.cur();
            com.maddox.rts.NetEnv.host().setShortName(com.maddox.il2.game.Main.cur().netGameSpy.userName);
        } else
        {
            com.maddox.il2.game.Main.cur().netServerParams.setType(i);
            com.maddox.il2.game.Main.cur().netServerParams.setServerName(wName.getValue());
            if("".equals(com.maddox.il2.game.Main.cur().netServerParams.serverName()))
                com.maddox.il2.game.Main.cur().netServerParams.setServerName(com.maddox.rts.NetEnv.host().shortName());
        }
        com.maddox.il2.game.Main.cur().netServerParams.setPassword(password);
        new NetLocalControl();
        int j = 0;
        if(cGameType.getSelected() == 1 || cGameType.getSelected() == 2)
            j = 1;
        com.maddox.il2.game.Main.cur().netServerParams.setMode(j);
        com.maddox.il2.game.Main.cur().netServerParams.setDifficulty(com.maddox.il2.ai.World.cur().diffUser.get());
        com.maddox.il2.game.Main.cur().netServerParams.setMaxUsers(com.maddox.il2.engine.Config.cur.netServerChannels + 1);
        new Chat();
        if(cGameType.getSelected() == 2)
        {
            com.maddox.il2.game.Main.cur().netServerParams.bNGEN = true;
            com.maddox.il2.game.Main.stateStack().change(68);
        } else
        {
            com.maddox.il2.game.Main.cur().netServerParams.bNGEN = false;
            com.maddox.il2.game.Main.stateStack().change(38);
        }
        com.maddox.il2.gui.GUI.chatDlg.showWindow();
    }

    private void doNewLocal()
    {
        doNewServer(0);
    }

    private void doExit()
    {
        com.maddox.il2.gui.GUIMainMenu guimainmenu = (com.maddox.il2.gui.GUIMainMenu)com.maddox.il2.game.GameState.get(2);
        guimainmenu.pPilotName.cap = new GCaption(com.maddox.il2.ai.World.cur().userCfg.name + " '" + com.maddox.il2.ai.World.cur().userCfg.callsign + "' " + com.maddox.il2.ai.World.cur().userCfg.surname);
        com.maddox.il2.gui.GUIInfoName.nickName = null;
        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).reset();
        com.maddox.il2.game.Main.stateStack().pop();
    }

    private java.lang.Object THIS()
    {
        return this;
    }

    private boolean isExistNGEN()
    {
        java.io.File file = new File(com.maddox.rts.HomePath.get(0), "ngen");
        if(!file.isDirectory())
            return false;
        file = new File(com.maddox.rts.HomePath.get(0), "ngen.exe");
        java.lang.Exception exception;
        return file.exists();
        exception;
        return false;
    }

    public GUINetNewServer(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(35);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("netns.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wName = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null));
        wName.maxLength = 64;
        cGameType = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cGameType.setEditable(false);
        cGameType.add(i18n("netns.Dogfight"));
        cGameType.add(i18n("netns.Cooperative"));
        if(isExistNGEN())
            cGameType.add(i18n("netns.NGEN"));
        cGameType.setSelected(0, true, false);
        cPlayers = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 0.0F, 0.0F, 1.0F));
        cPlayers.setEditable(false);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bPassword = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bStart = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.gwindow.GWindowEditControl wName;
    public com.maddox.il2.gui.GUIButton bPassword;
    public com.maddox.gwindow.GWindowComboControl cGameType;
    public com.maddox.gwindow.GWindowComboControl cPlayers;
    public com.maddox.il2.gui.GUIButton bStart;
    public com.maddox.il2.gui.GUIButton bExit;
    private java.lang.String password;





}
