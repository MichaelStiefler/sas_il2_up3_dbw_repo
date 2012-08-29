// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIPlayerSelect.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCellEdit;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import com.maddox.util.UnicodeTo8bit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUIMainMenu, GUIPocket, 
//            GUISeparate

public class GUIPlayerSelect extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == wNew)
            {
                wTable.addUser();
                return true;
            }
            if(gwindow == wDel)
            {
                wTable.removeUser();
                return true;
            }
            if(gwindow == wSelect)
            {
                int k = wTable.selectRow;
                if(k >= 0)
                {
                    int l = (wTable.selectCol + 1) % 3;
                    wTable.setSelect(k, l);
                    com.maddox.il2.ai.UserCfg usercfg = (com.maddox.il2.ai.UserCfg)wTable.users.get(k);
                    com.maddox.il2.ai.World.cur().userCfg = usercfg;
                    com.maddox.il2.ai.World.cur().setUserCovers();
                }
                saveUsersList();
                com.maddox.il2.ai.World.cur().userCfg.loadConf();
                com.maddox.il2.gui.GUIMainMenu guimainmenu = (com.maddox.il2.gui.GUIMainMenu)com.maddox.il2.game.GameState.get(2);
                guimainmenu.pPilotName.cap = new GCaption(com.maddox.il2.ai.World.cur().userCfg.name + " '" + com.maddox.il2.ai.World.cur().userCfg.callsign + "' " + com.maddox.il2.ai.World.cur().userCfg.surname);
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(608F), x1024(464F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(32F), y1024(32F), x1024(464F), y1024(32F), 1, i18n("player.ListPlayers"));
            draw(x1024(96F), y1024(480F), x1024(400F), y1024(48F), 0, i18n("player.NewPlayer"));
            draw(x1024(96F), y1024(544F), x1024(400F), y1024(48F), 0, i18n("player.DeletePlayer"));
            draw(x1024(96F), y1024(624F), x1024(400F), y1024(48F), 0, i18n("player.Select"));
        }

        public void setPosSize()
        {
            set1024PosSize(254F, 48F, 528F, 704F);
            wNew.setPosC(x1024(56F), y1024(506F));
            wDel.setPosC(x1024(56F), y1024(568F));
            wSelect.setPosC(x1024(56F), y1024(648F));
            wTable.setPosSize(x1024(32F), y1024(80F), x1024(464F), y1024(368F));
        }

        public DialogClient()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public void addUser()
        {
            int i = selectRow + 1;
            users.add(i, new UserCfg(com.maddox.il2.ai.UserCfg.defName, com.maddox.il2.ai.UserCfg.defCallsign, com.maddox.il2.ai.UserCfg.defSurname));
            setSelect(i, 0);
            resized();
        }

        public void removeUser()
        {
            if(users.size() <= 1)
                return;
            int i = selectRow;
            if(i < 0)
                return;
            com.maddox.il2.ai.UserCfg usercfg = (com.maddox.il2.ai.UserCfg)users.get(i);
            if(usercfg.sId != null)
                usercfg.removeUserDir();
            users.remove(i);
            if(editor != null)
            {
                ((com.maddox.gwindow.GWindow)editor).hideWindow();
                editor = null;
            }
            if(--i < 0)
                i = 0;
            setSelect(i, 0);
            resized();
        }

        public int countRows()
        {
            return users == null ? 0 : users.size();
        }

        public void columnClicked(int i)
        {
            sort(i);
        }

        public boolean isCellEditable(int i, int j)
        {
            return true;
        }

        public com.maddox.gwindow.GWindowCellEdit getCellEdit(int i, int j)
        {
            com.maddox.gwindow.GWindowCellEdit gwindowcelledit = super.getCellEdit(i, j);
            if(gwindowcelledit != null && (gwindowcelledit instanceof com.maddox.gwindow.GWindowEditControl))
            {
                com.maddox.gwindow.GWindowEditControl gwindoweditcontrol = (com.maddox.gwindow.GWindowEditControl)gwindowcelledit;
                gwindoweditcontrol.maxLength = 32;
            }
            return gwindowcelledit;
        }

        public java.lang.Object getValueAt(int i, int j)
        {
            com.maddox.il2.ai.UserCfg usercfg = (com.maddox.il2.ai.UserCfg)users.get(i);
            switch(j)
            {
            case 0: // '\0'
                return usercfg.name;

            case 1: // '\001'
                return usercfg.callsign;

            case 2: // '\002'
                return usercfg.surname;
            }
            return null;
        }

        public void setValueAt(java.lang.Object obj, int i, int j)
        {
            if(i < 0 || j < 0)
                return;
            if(i >= users.size())
                return;
            com.maddox.il2.ai.UserCfg usercfg = (com.maddox.il2.ai.UserCfg)users.get(i);
            java.lang.String s = (java.lang.String)obj;
            if(s == null || s.length() == 0)
                s = " ";
            switch(j)
            {
            case 0: // '\0'
                usercfg.name = s;
                break;

            case 1: // '\001'
                usercfg.callsign = s;
                break;

            case 2: // '\002'
                usercfg.surname = s;
                break;
            }
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = false;
            addColumn(com.maddox.il2.game.I18N.gui("player.Name"), null);
            addColumn(com.maddox.il2.game.I18N.gui("player.Callsign"), null);
            addColumn(com.maddox.il2.game.I18N.gui("player.Surname"), null);
            vSB.scroll = rowHeight(0);
            resized();
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
        }

        public java.util.ArrayList users;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow, 2.0F, 4F, 20F, 16F);
            users = new ArrayList();
        }
    }


    public void enterPush(com.maddox.il2.game.GameState gamestate)
    {
        if(!loadUsersList())
        {
            com.maddox.il2.game.Main.stateStack().pop();
            return;
        } else
        {
            _enter();
            return;
        }
    }

    public void _enter()
    {
        client.showWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    private boolean loadUsersList()
    {
        wTable.users.clear();
        wTable.setSelect(-1, -1);
        com.maddox.rts.SectFile sectfile = new SectFile("users/all.ini", 0);
        int i = sectfile.sectionIndex("list");
        int j = sectfile.sectionIndex("current");
        if(i < 0 || j < 0)
            return false;
        int k = sectfile.vars(i);
        if(k == 0)
            return false;
        java.lang.String s = sectfile.var(j, 0);
        int l = 0;
        try
        {
            l = java.lang.Integer.parseInt(s);
        }
        catch(java.lang.Exception exception) { }
        for(int i1 = 0; i1 < k; i1++)
        {
            java.lang.String s1 = sectfile.var(i, i1);
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.value(i, i1));
            com.maddox.il2.ai.UserCfg usercfg = new UserCfg(com.maddox.util.UnicodeTo8bit.load(numbertokenizer.next(com.maddox.il2.ai.UserCfg.defName)), com.maddox.util.UnicodeTo8bit.load(numbertokenizer.next(com.maddox.il2.ai.UserCfg.defCallsign)), com.maddox.util.UnicodeTo8bit.load(numbertokenizer.next(com.maddox.il2.ai.UserCfg.defSurname)));
            usercfg.sId = s1;
            if(usercfg.existUserDir() && usercfg.existUserConf() && !existUserInList(usercfg))
                wTable.users.add(usercfg);
            else
                l--;
        }

        if(wTable.users.size() == 0)
            return false;
        if(l < 0)
            l = 0;
        if(l >= wTable.users.size())
            l = wTable.users.size() - 1;
        wTable.setSelect(l, 0);
        return true;
    }

    private boolean existUserInList(com.maddox.il2.ai.UserCfg usercfg)
    {
        for(int i = 0; i < wTable.users.size(); i++)
        {
            com.maddox.il2.ai.UserCfg usercfg1 = (com.maddox.il2.ai.UserCfg)wTable.users.get(i);
            if(usercfg.sId.compareToIgnoreCase(usercfg1.sId) == 0)
                return true;
        }

        return false;
    }

    private void saveUsersList()
    {
        com.maddox.rts.SectFile sectfile = new SectFile("users/all.ini", 1);
        sectfile.clear();
        int i = sectfile.sectionAdd("list");
        for(int j = 0; j < wTable.users.size(); j++)
        {
            com.maddox.il2.ai.UserCfg usercfg = (com.maddox.il2.ai.UserCfg)wTable.users.get(j);
            if(usercfg.sId == null)
            {
                usercfg.makeId();
                usercfg.createUserDir();
                usercfg.createUserConf();
            }
            sectfile.lineAdd(i, usercfg.sId, com.maddox.util.UnicodeTo8bit.save(usercfg.name, true) + " " + com.maddox.util.UnicodeTo8bit.save(usercfg.callsign, true) + " " + com.maddox.util.UnicodeTo8bit.save(usercfg.surname, true));
        }

        int k = sectfile.sectionAdd("current");
        int l = wTable.selectRow;
        if(l < 0)
            l = 0;
        if(l >= wTable.users.size())
            l = wTable.users.size() - 1;
        sectfile.lineAdd(k, "" + l);
        sectfile.saveFile();
    }

    private void sort(int i)
    {
        com.maddox.il2.ai.UserCfg usercfg = null;
        int j = wTable.selectRow;
        int l = wTable.selectCol;
        if(j >= 0)
            usercfg = (com.maddox.il2.ai.UserCfg)wTable.users.get(j);
        wTable.setSelect(-1, -1);
        for(int i1 = 0; i1 < wTable.users.size(); i1++)
        {
            com.maddox.il2.ai.UserCfg usercfg1 = (com.maddox.il2.ai.UserCfg)wTable.users.get(i1);
            java.lang.String s = usercfg1.name;
            switch(i)
            {
            case 1: // '\001'
                s = usercfg1.callsign;
                break;

            case 2: // '\002'
                s = usercfg1.surname;
                break;
            }
            java.lang.String s1 = s;
            int k1 = 0;
            for(; _sortMap.containsKey(s1); s1 = s + k1++);
            _sortMap.put(s1, usercfg1);
        }

        wTable.users.clear();
        java.util.Iterator iterator = _sortMap.keySet().iterator();
        int j1 = 0;
        for(int k = 0; iterator.hasNext(); k++)
        {
            com.maddox.il2.ai.UserCfg usercfg2 = (com.maddox.il2.ai.UserCfg)_sortMap.get(iterator.next());
            wTable.users.add(usercfg2);
            if(usercfg2 == usercfg)
                j1 = k;
        }

        if(usercfg != null)
            wTable.setSelect(j1, l);
        _sortMap.clear();
    }

    public GUIPlayerSelect(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(1);
        _sortMap = new TreeMap();
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("player.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wTable = new Table(dialogClient);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        wNew = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        wDel = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        wSelect = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton wNew;
    public com.maddox.il2.gui.GUIButton wDel;
    public com.maddox.il2.gui.GUIButton wSelect;
    public com.maddox.il2.gui.Table wTable;
    private java.util.TreeMap _sortMap;


}
