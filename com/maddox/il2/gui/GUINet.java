// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINet.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.rts.MainWin32;
import com.maddox.rts.RTSConf;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUISeparate

public class GUINet extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bClient)
            {
                com.maddox.il2.game.Main.stateStack().change(34);
                return true;
            }
            if(gwindow == bServer)
            {
                com.maddox.il2.game.Main.stateStack().change(35);
                return true;
            }
            if(gwindow == bExit)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            }
            if(gwindow == bChannel)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                java.lang.String s = com.maddox.rts.MainWin32.RegistryGetAppPath("BBGChan.exe");
                if(s != null)
                    com.maddox.rts.RTSConf.cur.execPostProcessCmd = s + "\\BBGChan.exe LobbyIL2.ini";
                com.maddox.il2.game.Main.doGameExit();
                return true;
            }
            if(gwindow == bUSGS)
            {
                com.maddox.il2.game.Main.stateStack().pop();
                com.maddox.rts.RTSConf.cur.execPostProcessCmd = pathUSGSExe + " -l +sg \"IL2FB\"";
                com.maddox.il2.game.Main.doGameExit();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            float f = bChannel != null ? 80 : 0;
            if(bUSGS != null)
                f += 80F;
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(192F + f), x1024(448F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            if(bChannel != null)
                draw(x1024(96F), y1024(32F), x1024(384F), y1024(48F), 0, i18n("net.BBGC"));
            if(bUSGS != null)
                draw(x1024(96F), y1024(32F), x1024(384F), y1024(48F), 0, i18n("net.USGS"));
            draw(x1024(96F), y1024(32F + f), x1024(384F), y1024(48F), 0, i18n("net.Client"));
            draw(x1024(96F), y1024(112F + f), x1024(384F), y1024(48F), 0, i18n("net.Server"));
            draw(x1024(96F), y1024(224F + f), x1024(384F), y1024(48F), 0, i18n("net.MainMenu"));
        }

        public void setPosSize()
        {
            float f = bChannel != null ? 80 : 0;
            if(bUSGS != null)
                f += 80F;
            set1024PosSize(272F, 256F - f / 2.0F, 512F, 304F + f);
            if(bChannel != null)
                bChannel.setPosC(x1024(56F), y1024(56F));
            if(bUSGS != null)
                bUSGS.setPosC(x1024(56F), y1024(56F));
            bClient.setPosC(x1024(56F), y1024(56F + f));
            bServer.setPosC(x1024(56F), y1024(136F + f));
            bExit.setPosC(x1024(56F), y1024(248F + f));
        }

        public DialogClient()
        {
        }
    }


    public void _enter()
    {
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public GUINet(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(33);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("net.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        pathUSGSExe = com.maddox.rts.MainWin32.RegistryGetStringLM("SOFTWARE\\Ubi Soft\\Game Service\\", "Directory");
        if(pathUSGSExe != null)
        {
            if(!pathUSGSExe.endsWith("\\"))
                pathUSGSExe += "\\";
            java.lang.String s = com.maddox.rts.MainWin32.RegistryGetStringLM("SOFTWARE\\Ubi Soft\\Game Service\\", "Exe");
            if(s != null)
                pathUSGSExe += s;
            else
                pathUSGSExe = null;
        }
        if(pathUSGSExe != null)
            bUSGS = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bClient = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bServer = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bChannel;
    public com.maddox.il2.gui.GUIButton bUSGS;
    public com.maddox.il2.gui.GUIButton bClient;
    public com.maddox.il2.gui.GUIButton bServer;
    public com.maddox.il2.gui.GUIButton bExit;
    public java.lang.String pathUSGSExe;
}
