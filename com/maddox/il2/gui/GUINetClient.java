// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetClient.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.USGS;

// Referenced classes of package com.maddox.il2.gui:
//            GUINetClientGuard, GUIClient, GUIInfoMenu, GUIInfoName, 
//            GUILookAndFeel, GUIButton, GUIDialogClient, GUISeparate

public class GUINetClient extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bExit)
            {
                com.maddox.il2.gui.GUINetClientGuard guinetclientguard = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
                guinetclientguard.dlgDestroy(new com.maddox.il2.gui.GUINetClientGuard.DestroyExec() {

                    public void destroy(com.maddox.il2.gui.GUINetClientGuard guinetclientguard1)
                    {
                        if(com.maddox.il2.net.USGS.isUsed() || com.maddox.il2.game.Main.cur().netGameSpy != null)
                            com.maddox.il2.game.Main.doGameExit();
                        else
                            guinetclientguard1.destroy(true);
                    }

                }
);
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(208F), x1024(480F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(32F), y1024(32F), x1024(480F), y1024(48F), 1, i18n("netc.WaitLoading"));
            draw(x1024(32F), y1024(96F), x1024(480F), y1024(96F), guardMessage(), 3);
            draw(x1024(96F), y1024(240F), x1024(136F), y1024(48F), 0, !com.maddox.il2.net.USGS.isUsed() && com.maddox.il2.game.Main.cur().netGameSpy == null ? i18n("netc.Disconnect") : i18n("main.Quit"));
        }

        private java.lang.String guardMessage()
        {
            com.maddox.il2.gui.GUINetClientGuard guinetclientguard = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
            if(guinetclientguard == null || guinetclientguard.lastNetMissionInfo == null)
                return "";
            else
                return guinetclientguard.lastNetMissionInfo;
        }

        public void setPosSize()
        {
            set1024PosSize(240F, 240F, 544F, 320F);
            bExit.setPosC(x1024(56F), y1024(264F));
        }

        public DialogClient()
        {
        }
    }


    public void _enter()
    {
        if(com.maddox.il2.game.Main.cur().netChannelListener == null)
            new GUINetClientGuard();
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public GUINetClient(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(36);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("netc.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bExit = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bExit;
}
