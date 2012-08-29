// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetCStart.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.NetEnv;
import java.util.List;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUINetClientGuard, GUI, 
//            GUISeparate

public class GUINetCStart extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bPrev)
            {
                if(bKick == null)
                {
                    com.maddox.il2.gui.GUINetClientGuard guinetclientguard = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
                    guinetclientguard.dlgDestroy(new com.maddox.il2.gui.GUINetClientGuard.DestroyExec() {

                        public void destroy(com.maddox.il2.gui.GUINetClientGuard guinetclientguard1)
                        {
                            guinetclientguard1.destroy(true);
                        }

                    }
);
                } else
                {
                    com.maddox.il2.game.Mission.cur().destroy();
                    com.maddox.il2.game.Main.stateStack().change(com.maddox.il2.game.Main.cur().netServerParams.bNGEN ? 69 : 38);
                }
                return true;
            }
            if(gwindow == bKick)
            {
                int k = wTable.selectRow;
                if(k >= 0 && k < com.maddox.rts.NetEnv.hosts().size())
                {
                    com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(k);
                    ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).kick(netuser);
                }
                return true;
            }
            if(gwindow == bFly)
            {
                com.maddox.il2.game.Main.cur().netServerParams.doMissionCoopEnter();
                com.maddox.il2.gui.GUI.unActivate();
                com.maddox.rts.HotKeyCmd.exec("aircraftView", "CockpitView");
                com.maddox.il2.game.Main.stateStack().change(49);
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void preRender()
        {
            if(bFly != null && !bFly.isVisible())
            {
                boolean flag = true;
                if(com.maddox.rts.NetEnv.hosts() != null)
                {
                    java.util.List list = com.maddox.rts.NetEnv.hosts();
                    int i = 0;
                    do
                    {
                        if(i >= list.size())
                            break;
                        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)list.get(i);
                        if(!netuser.isWaitStartCoopMission())
                        {
                            flag = false;
                            break;
                        }
                        i++;
                    } while(true);
                }
                if(flag)
                    bFly.showWindow();
            }
            super.preRender();
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(544F), x1024(512F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            if(bKick != null)
            {
                draw(x1024(96F), y1024(656F), x1024(192F), y1024(48F), 0, i18n("netcstart.EndMission"));
                draw(x1024(96F), y1024(576F), x1024(240F), y1024(48F), 0, i18n("netcstart.KickPlayer"));
                if(bFly.isVisible())
                    draw(x1024(304F), y1024(656F), x1024(176F), y1024(48F), 2, i18n("netcstart.Fly"));
                else
                    draw(x1024(304F), y1024(656F), x1024(208F), y1024(48F), 2, i18n("netcstart.Wait"));
            } else
            {
                draw(x1024(96F), y1024(656F), x1024(192F), y1024(48F), 0, i18n("netcstart.Disconnect"));
                draw(x1024(304F), y1024(656F), x1024(208F), y1024(48F), 2, i18n("netcstart.Wait"));
            }
        }

        public void setPosSize()
        {
            set1024PosSize(224F, 32F, 576F, 736F);
            wTable.set1024PosSize(32F, 32F, 512F, 480F);
            bPrev.setPosC(x1024(56F), y1024(680F));
            if(bKick != null)
            {
                bKick.setPosC(x1024(56F), y1024(600F));
                bFly.setPosC(x1024(520F), y1024(680F));
            }
        }

        public DialogClient()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return com.maddox.rts.NetEnv.hosts() != null ? com.maddox.rts.NetEnv.hosts().size() : 0;
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
            }
            if(flag)
                setCanvasColorWHITE();
            else
                setCanvasColorBLACK();
            com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.hosts().get(i);
            java.lang.String s = null;
            int k = 0;
            switch(j)
            {
            case 0: // '\0'
                s = netuser.uniqueName();
                break;

            case 1: // '\001'
                k = 1;
                s = "" + netuser.ping;
                break;

            case 2: // '\002'
                if(netuser.isWaitStartCoopMission())
                    s = com.maddox.il2.game.I18N.gui("netcstart.Ready");
                break;
            }
            if(s != null)
                draw(0.0F, 0.0F, f, f1, k, s);
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = true;
            bSelecting = true;
            bSelectRow = true;
            addColumn(com.maddox.il2.game.I18N.gui("netcstart.Player"), null);
            addColumn(com.maddox.il2.game.I18N.gui("netcstart.Ping"), null);
            addColumn(com.maddox.il2.game.I18N.gui("netcstart.State"), null);
            vSB.scroll = rowHeight(0);
            getColumn(0).setRelativeDx(5F);
            getColumn(1).setRelativeDx(2.0F);
            getColumn(2).setRelativeDx(3F);
            alignColumns();
            resized();
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
        }

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
        }
    }


    public void _enter()
    {
        if(bFly != null)
            bFly.hideWindow();
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public GUINetCStart(int i, com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(i);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("netcstart.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wTable = new Table(dialogClient);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bPrev = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        if(i == 47)
        {
            bKick = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
            bFly = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        }
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bPrev;
    public com.maddox.il2.gui.GUIButton bKick;
    public com.maddox.il2.gui.GUIButton bFly;
    public com.maddox.il2.gui.Table wTable;
}
