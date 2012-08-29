// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetCScore.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.net.NetUserLeft;
import com.maddox.il2.net.NetUserStat;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUINetAircraft, GUIDialogClient, GUINetClientGuard, 
//            GUISeparate

public class GUINetCScore extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bPrev)
            {
                if(com.maddox.il2.game.Main.cur().netServerParams.isMaster())
                {
                    com.maddox.il2.game.Mission.cur().destroy();
                    com.maddox.il2.game.Main.stateStack().change(com.maddox.il2.game.Main.cur().netServerParams.bNGEN ? 69 : 38);
                } else
                {
                    com.maddox.il2.gui.GUINetClientGuard guinetclientguard = (com.maddox.il2.gui.GUINetClientGuard)com.maddox.il2.game.Main.cur().netChannelListener;
                    if(guinetclientguard != null)
                        guinetclientguard.dlgDestroy(new com.maddox.il2.gui.GUINetClientGuard.DestroyExec() {

                            public void destroy(com.maddox.il2.gui.GUINetClientGuard guinetclientguard1)
                            {
                                guinetclientguard1.destroy(true);
                            }

                        }
);
                }
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(960F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isMaster())
                draw(x1024(96F), y1024(656F), x1024(128F), y1024(48F), 0, i18n("netcs.New_mission"));
            else
                draw(x1024(96F), y1024(656F), x1024(128F), y1024(48F), 0, i18n("netcs.Disconnect"));
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            wTable.set1024PosSize(32F, 32F, 960F, 560F);
            bPrev.setPosC(x1024(56F), y1024(680F));
        }

        public DialogClient()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            if(items == null)
                return 0;
            else
                return items.size() + 1;
        }

        public void preRender()
        {
            checkListItems();
            super.preRender();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            if(flag && items != null && i != items.size())
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
            }
            if(flag)
                setCanvasColorWHITE();
            else
                setCanvasColorBLACK();
            com.maddox.il2.gui.Item item = null;
            if(i < items.size())
                item = (com.maddox.il2.gui.Item)items.get(i);
            else
                item = (com.maddox.il2.gui.Item)items.get(i - 1);
            java.lang.String s = null;
            byte byte0 = 1;
            switch(j)
            {
            case 0: // '\0'
                if(item.entry != null)
                {
                    s = "" + item.entry.indexInArmy;
                    setCanvasColor(com.maddox.il2.ai.Army.color(item.entry.reg.getArmy()));
                }
                break;

            case 1: // '\001'
                if(item.entry != null)
                {
                    float f2 = f1;
                    setCanvasColorWHITE();
                    draw(0.0F, 0.0F, f2, f2, item.entry.texture);
                    if(flag)
                        setCanvasColorWHITE();
                    else
                        setCanvasColorBLACK();
                    draw(1.5F * f2, 0.0F, f - 1.5F * f2, f1, 0, item.entry.reg.shortInfo());
                } else
                if(i == items.size() && com.maddox.il2.net.NetUser.getArmyCoopWinner() > 0)
                {
                    setCanvasColor(com.maddox.il2.ai.Army.color(com.maddox.il2.net.NetUser.getArmyCoopWinner()));
                    setCanvasFont(1);
                    if(com.maddox.il2.net.NetUser.getArmyCoopWinner() == 1)
                        s = com.maddox.il2.game.I18N.hud_log("RedWon");
                    else
                        s = com.maddox.il2.game.I18N.hud_log("BlueWon");
                    region.x = region.y = 0.0F;
                    region.dx = f;
                    region.dy = f1;
                    pushClipRegion(region, false, 0.0F);
                    draw(0.0F, 0.0F, f, f1, 0, s);
                    popClip();
                    return;
                }
                break;

            case 2: // '\002'
                if(item.entry != null)
                {
                    byte0 = 0;
                    s = com.maddox.il2.game.I18N.plane(item.entry.keyName);
                }
                break;

            case 3: // '\003'
                if(item.entry != null)
                    s = item.entry.number;
                break;

            case 4: // '\004'
                if(item.entry != null)
                    s = item.entry.cocName;
                break;

            case 5: // '\005'
                byte0 = 0;
                if(item.user != null)
                    s = item.user.uniqueName();
                break;

            case 6: // '\006'
                byte0 = 2;
                if(item.user != null)
                    s = "" + item.score + "  ";
                break;
            }
            if(s != null)
                draw(0.0F, 0.0F, f, f1, byte0, s);
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = true;
            bSelecting = true;
            bSelectRow = true;
            addColumn("N", null);
            addColumn(com.maddox.il2.game.I18N.gui("netcs.Regiment"), null);
            addColumn(com.maddox.il2.game.I18N.gui("netcs.Plane"), null);
            addColumn(com.maddox.il2.game.I18N.gui("netcs.Number"), null);
            addColumn(com.maddox.il2.game.I18N.gui("netcs.Position"), null);
            addColumn(com.maddox.il2.game.I18N.gui("netcs.Player"), null);
            addColumn(com.maddox.il2.game.I18N.gui("netcs.Score"), null);
            vSB.scroll = rowHeight(0);
            getColumn(0).setRelativeDx(1.0F);
            getColumn(1).setRelativeDx(4F);
            getColumn(2).setRelativeDx(4F);
            getColumn(3).setRelativeDx(4F);
            getColumn(4).setRelativeDx(4F);
            getColumn(5).setRelativeDx(4F);
            getColumn(6).setRelativeDx(2.0F);
            alignColumns();
            wClient.bNotify = true;
            resized();
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
        }

        public java.util.ArrayList items;
        private com.maddox.gwindow.GRegion region;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            items = new ArrayList();
            region = new GRegion();
        }
    }

    static class Item
    {

        public com.maddox.il2.gui.GUINetAircraft.Item entry;
        public com.maddox.il2.net.NetUser user;
        public double score;
        public int army;

        Item()
        {
        }
    }


    private void fillListItems()
    {
        java.util.ArrayList arraylist = wTable.items;
        arraylist.clear();
        int i = 0;
        int j = 0;
        double d = 0.0D;
        do
        {
            com.maddox.il2.gui.GUINetAircraft.Item item = com.maddox.il2.gui.GUINetAircraft.getItem(i);
            if(item == null)
            {
                if(j != 0)
                {
                    com.maddox.il2.gui.Item item1 = new Item();
                    item1.score = d;
                    item1.army = j;
                    arraylist.add(item1);
                }
                break;
            }
            if(item.reg.getArmy() != j && j != 0)
            {
                com.maddox.il2.gui.Item item2 = new Item();
                item2.score = d;
                item2.army = j;
                arraylist.add(item2);
                d = 0.0D;
            }
            com.maddox.il2.gui.Item item3 = new Item();
            item3.entry = item;
            item3.user = findPlayer(i);
            if(item3.user != null)
                item3.score = item3.user.stat().score;
            item3.army = item.reg.getArmy();
            arraylist.add(item3);
            d += item3.score;
            j = item3.army;
            i++;
        } while(true);
        wTable.setSelect(-1, -1);
    }

    private void checkListItems()
    {
        for(int i = 0; i < wTable.items.size(); i++)
        {
            com.maddox.il2.gui.Item item = (com.maddox.il2.gui.Item)wTable.items.get(i);
            if(item.entry != null && item.user != null)
                if(item.user.isDestroyed())
                    item.user = null;
                else
                if(item.user.stat().score != item.score)
                {
                    fillListItems();
                    return;
                }
        }

    }

    private com.maddox.il2.net.NetUser findPlayer(int i)
    {
        com.maddox.il2.net.NetUser netuser = (com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host();
        if(netuser.getPlace() == i)
            return netuser;
        java.util.List list = com.maddox.rts.NetEnv.hosts();
        for(int j = 0; j < list.size(); j++)
        {
            com.maddox.il2.net.NetUser netuser1 = (com.maddox.il2.net.NetUser)list.get(j);
            if(netuser1.getPlace() == i)
                return netuser1;
        }

        return null;
    }

    public void _enter()
    {
        fillListItems();
        wTable.resized();
        client.activateWindow();
    }

    public void _leave()
    {
        wTable.items.clear();
        com.maddox.rts.CmdEnv.top().exec("user STAT EVENTLOG");
        com.maddox.il2.net.NetUserLeft.all.clear();
        client.hideWindow();
    }

    protected void init(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("netcs.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wTable = new Table(dialogClient);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bPrev = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public GUINetCScore(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(51);
        init(gwindowroot);
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton bPrev;
    public com.maddox.il2.gui.Table wTable;

}
