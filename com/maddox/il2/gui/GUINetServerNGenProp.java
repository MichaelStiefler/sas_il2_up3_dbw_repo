// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetServerNGenProp.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCellEdit;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgBackgroundTaskListener;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.io.PrintStream;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUISwitchBox3, 
//            GUILookAndFeel, GUIButton, GUINetServerNGenSelect, GUIDialogClient, 
//            GUISeparate, GUINetAircraft

public class GUINetServerNGenProp extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bList)
            {
                getChanges();
                com.maddox.il2.game.Main.stateStack().change(68);
                return true;
            }
            if(gwindow == bDiff)
            {
                com.maddox.il2.game.Main.stateStack().push(41);
                return true;
            }
            if(gwindow == bLast)
            {
                com.maddox.rts.SectFile sectfile = new SectFile(com.maddox.il2.gui.GUINetServerNGenSelect.cur.fileName, 4, true, null, com.maddox.rts.RTSConf.charEncoding, true);
                java.lang.String s1 = sectfile.line(sectfile.sectionIndex("$missions"), com.maddox.il2.gui.GUINetServerNGenSelect.cur.missions - 1);
                java.lang.String s3 = com.maddox.il2.gui.GUINetServerNGenSelect.cur.fileName;
                int l = s3.lastIndexOf("conf.dat");
                if(l >= 0)
                    s3 = s3.substring(0, l);
                com.maddox.il2.game.Main.cur().currentMissionFile = new SectFile(s3 + s1, 0);
                doLoadMission();
                return true;
            }
            if(gwindow == bNew)
            {
                getChanges();
                java.lang.String s = "NGen.exe";
                try
                {
                    java.lang.String s2 = com.maddox.il2.gui.GUINetServerNGenSelect.cur.fileName;
                    java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
                    java.lang.Process process = runtime.exec(s + " " + s2);
                    process.waitFor();
                }
                catch(java.lang.Throwable throwable)
                {
                    java.lang.System.out.println(throwable.getMessage());
                    throwable.printStackTrace();
                    return true;
                }
                com.maddox.rts.SectFile sectfile1 = new SectFile(com.maddox.il2.gui.GUINetServerNGenSelect.cur.fileName, 4, true, null, com.maddox.rts.RTSConf.charEncoding, true);
                int k = sectfile1.sectionIndex("$missions");
                if(k < 0)
                    return true;
                com.maddox.il2.gui.GUINetServerNGenSelect.cur.missions = sectfile1.vars(k);
                java.lang.String s4 = sectfile1.line(k, com.maddox.il2.gui.GUINetServerNGenSelect.cur.missions - 1);
                java.lang.String s5 = com.maddox.il2.gui.GUINetServerNGenSelect.cur.fileName;
                int i1 = s5.lastIndexOf("conf.dat");
                if(i1 >= 0)
                    s5 = s5.substring(0, i1);
                com.maddox.il2.game.Main.cur().currentMissionFile = new SectFile(s5 + s4, 0);
                doLoadMission();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(432F), x1024(672F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(672F), 2.0F);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(32F), y1024(32F), x1024(672F), y1024(32F), 1, com.maddox.il2.gui.GUINetServerNGenSelect.cur.name);
            draw(x1024(32F), y1024(80F), x1024(320F), y1024(32F), 0, i18n("ngenp.note"));
            draw(x1024(32F), y1024(160F), x1024(320F), y1024(32F), 0, i18n("ngenp.properties"));
            draw(x1024(96F), y1024(656F), x1024(96F), y1024(48F), 0, i18n("ngenp.list"));
            draw(x1024(176F), y1024(656F), x1024(112F), y1024(48F), 2, i18n("ngenp.difficulty"));
            if(com.maddox.il2.gui.GUINetServerNGenSelect.cur.missions > 0 && (loadMessageBox == null || com.maddox.il2.gui.GUINetServerNGenSelect.cur.missions > 1))
                draw(x1024(352F), y1024(656F), x1024(112F), y1024(48F), 2, i18n("ngenp.last"));
            draw(x1024(528F), y1024(656F), x1024(112F), y1024(48F), 2, i18n("ngenp.new"));
            draw(x1024(32F), y1024(448F), x1024(672F), y1024(32F), 0, i18n("ngenp.end_mission"));
            draw(x1024(384F), y1024(496F), x1024(226F), y1024(32F), 0, i18n("ngenp.timeout"));
            draw(x1024(264F), y1024(544F), x1024(346F), y1024(48F), 0, i18n("ngenp.landed"));
        }

        public void setPosSize()
        {
            set1024PosSize(144F, 32F, 736F, 736F);
            wNote.set1024PosSize(48F, 112F, 640F, 32F);
            wMenu.set1024PosSize(48F, 192F, 640F, 224F);
            wTimeHour.set1024PosSize(176F, 496F, 80F, 32F);
            wTimeMins.set1024PosSize(272F, 496F, 80F, 32F);
            sLand.setPosC(x1024(216F), y1024(568F));
            bList.setPosC(x1024(56F), y1024(680F));
            bDiff.setPosC(x1024(328F), y1024(680F));
            bLast.setPosC(x1024(504F), y1024(680F));
            bNew.setPosC(x1024(680F), y1024(680F));
        }

        public DialogClient()
        {
        }
    }

    public class Menu extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return lst == null ? 0 : lst.size();
        }

        public boolean isCellEditable(int i, int j)
        {
            return j == 1;
        }

        public float rowHeight(int i)
        {
            return (float)(int)(root.textFonts[0].height * 1.6F);
        }

        public com.maddox.gwindow.GWindowCellEdit getCellEdit(int i, int j)
        {
            if(!isCellEditable(i, j))
                return null;
            indxMenu = i;
            com.maddox.gwindow.GWindowComboControl gwindowcombocontrol;
            com.maddox.gwindow.GWindowCellEdit gwindowcelledit = (com.maddox.gwindow.GWindowCellEdit)wClient.create(gwindowcombocontrol = new com.maddox.gwindow.GWindowComboControl() {

                public boolean notify(int l, int i1)
                {
                    boolean flag = super.notify(l, i1);
                    if(l == 2)
                        item.select = i1;
                    return flag;
                }

                com.maddox.il2.gui.MenuItem item;

                
                {
                    item = (com.maddox.il2.gui.MenuItem)lst.get(indxMenu);
                }
            }
);
            gwindowcombocontrol.setEditable(false);
            com.maddox.il2.gui.MenuItem menuitem = (com.maddox.il2.gui.MenuItem)lst.get(indxMenu);
            for(int k = 0; k < menuitem.keys.size(); k++)
                gwindowcombocontrol.add((java.lang.String)menuitem.names.get(k));

            gwindowcombocontrol.setSelected(menuitem.select, true, false);
            return gwindowcelledit;
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
            }
            com.maddox.il2.gui.MenuItem menuitem = (com.maddox.il2.gui.MenuItem)lst.get(i);
            java.lang.String s = null;
            int k = 0;
            switch(j)
            {
            case 0: // '\0'
                s = menuitem.name;
                k = 0;
                break;

            case 1: // '\001'
                s = (java.lang.String)menuitem.names.get(menuitem.select);
                k = 0;
                break;
            }
            if(flag)
            {
                setCanvasColorWHITE();
                draw(0.0F, 0.0F, f, f1, k, s);
            } else
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, k, s);
            }
        }

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(super.notify(gwindow, i, j))
            {
                return true;
            } else
            {
                notify(i, j);
                return false;
            }
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = true;
            bSelectRow = true;
            addColumn(com.maddox.il2.game.I18N.gui("ngenp.name"), null);
            addColumn(com.maddox.il2.game.I18N.gui("ngenp.state"), null);
            vSB.scroll = rowHeight(0);
            getColumn(0).setRelativeDx(10F);
            getColumn(1).setRelativeDx(12F);
            alignColumns();
            bNotify = true;
            wClient.bNotify = true;
            resized();
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
        }

        public java.util.ArrayList lst;
        int indxMenu;

        public Menu(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
            lst = new ArrayList();
        }
    }

    static class MenuItem
    {

        public void select(java.lang.String s)
        {
            for(select = 0; select < keys.size(); select++)
                if(s.equals(keys.get(select)))
                    return;

            select = 0;
        }

        java.lang.String key;
        java.lang.String name;
        java.util.ArrayList keys;
        java.util.ArrayList names;
        int select;

        MenuItem()
        {
            keys = new ArrayList();
            names = new ArrayList();
            select = 0;
        }
    }

    class MissionListener
        implements com.maddox.rts.MsgBackgroundTaskListener
    {

        public void msgBackgroundTaskStarted(com.maddox.rts.BackgroundTask backgroundtask)
        {
        }

        public void msgBackgroundTaskStep(com.maddox.rts.BackgroundTask backgroundtask)
        {
            loadMessageBox.message = (int)backgroundtask.percentComplete() + "% " + com.maddox.il2.game.I18N.gui(backgroundtask.messageComplete());
        }

        public void msgBackgroundTaskStoped(com.maddox.rts.BackgroundTask backgroundtask)
        {
            com.maddox.rts.BackgroundTask.removeListener(this);
            if(backgroundtask.isComplete())
                missionLoaded();
            else
                missionBad(com.maddox.il2.game.I18N.gui("miss.LoadBad") + " " + backgroundtask.messageCancel());
        }

        public MissionListener()
        {
            com.maddox.rts.BackgroundTask.addListener(this);
        }
    }


    private void doLoadMission()
    {
        int i = wTimeHour.getSelected();
        int j = wTimeMins.getSelected() * 15;
        long l = (long)i * 60L * 60L * 1000L + (long)j * 60L * 1000L;
        com.maddox.il2.game.Main.cur().netServerParams.timeoutNGEN = l;
        com.maddox.il2.game.Main.cur().netServerParams.bLandedNGEN = sLand.isChecked();
        loadMessageBox = new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("netsms.StandBy"), i18n("netsms.Loading_simulation"), 5, 0.0F) {

            public void result(int k)
            {
                if(k == 1)
                    com.maddox.rts.BackgroundTask.cancel(com.maddox.il2.game.I18N.gui("miss.UserCancel"));
            }

        }
;
        new com.maddox.rts.MsgAction(72, 0.0D) {

            public void doAction()
            {
                if(com.maddox.il2.game.Mission.cur() != null)
                    com.maddox.il2.game.Mission.cur().destroy();
                try
                {
                    new MissionListener();
                    com.maddox.il2.game.Mission.loadFromSect(com.maddox.il2.game.Main.cur().currentMissionFile, true);
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println(exception.getMessage());
                    exception.printStackTrace();
                    missionBad(com.maddox.il2.game.I18N.gui("miss.LoadBad"));
                }
            }

        }
;
    }

    public void missionLoaded()
    {
        new com.maddox.rts.MsgAction(72, 0.0D) {

            public void doAction()
            {
                com.maddox.il2.engine.GUIWindowManager guiwindowmanager = com.maddox.il2.game.Main3D.cur3D().guiManager;
                if(loadMessageBox != null)
                {
                    loadMessageBox.close(false);
                    loadMessageBox = null;
                }
                ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).resetAllPlaces();
                com.maddox.rts.CmdEnv.top().exec("mission BEGIN");
                int i = com.maddox.il2.gui.GUINetAircraft.serverPlace();
                if(i != -1)
                    ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).requestPlace(i);
                com.maddox.il2.game.Main.stateStack().change(45);
            }

        }
;
    }

    private void missionBad(java.lang.String s)
    {
        loadMessageBox.close(false);
        loadMessageBox = null;
        new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("netsms.Error"), s, 3, 0.0F) {

            public void result(int i)
            {
            }

        }
;
    }

    public void enter(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate != null && gamestate.id() == 51)
        {
            java.lang.String s = "NGen.exe";
            try
            {
                java.lang.String s1 = com.maddox.il2.gui.GUINetServerNGenSelect.cur.fileName;
                java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
                java.lang.Process process = runtime.exec(s + " -ended " + s1);
                process.waitFor();
            }
            catch(java.lang.Throwable throwable)
            {
                java.lang.System.out.println(throwable.getMessage());
                throwable.printStackTrace();
            }
        }
        com.maddox.rts.SectFile sectfile = new SectFile(com.maddox.il2.gui.GUINetServerNGenSelect.cur.fileName, 4, true, null, com.maddox.rts.RTSConf.charEncoding, true);
        int i = sectfile.get("$select", "difficulty", -1);
        if(i == -1)
            i = com.maddox.il2.ai.World.cur().userCfg.netDifficulty;
        else
            com.maddox.il2.ai.World.cur().userCfg.netDifficulty = i;
        com.maddox.il2.ai.World.cur().diffCur.set(i);
        com.maddox.il2.game.Main.cur().netServerParams.setDifficulty(com.maddox.il2.ai.World.cur().diffCur.get());
        com.maddox.rts.NetEnv.cur().connect.bindEnable(true);
        com.maddox.il2.game.Main.cur().netServerParams.USGSupdate();
        _enter();
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate.id() == 41)
        {
            com.maddox.il2.ai.World.cur().userCfg.netDifficulty = com.maddox.il2.ai.World.cur().diffCur.get();
            com.maddox.il2.ai.World.cur().userCfg.saveConf();
            com.maddox.il2.game.Main.cur().netServerParams.setDifficulty(com.maddox.il2.ai.World.cur().diffCur.get());
            com.maddox.rts.SectFile sectfile = new SectFile(com.maddox.il2.gui.GUINetServerNGenSelect.cur.fileName, 5, true, null, com.maddox.rts.RTSConf.charEncoding, true);
            sectfile.set("$select", "difficulty", com.maddox.il2.ai.World.cur().userCfg.netDifficulty);
            sectfile.saveFile();
        }
        client.activateWindow();
    }

    public void _enter()
    {
        fillNote();
        fillMenu();
        wMenu.resized();
        client.activateWindow();
        if(com.maddox.il2.gui.GUINetServerNGenSelect.cur.missions > 0)
            bLast.showWindow();
        else
            bLast.hideWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    private void fillNote()
    {
        if(com.maddox.il2.gui.GUINetServerNGenSelect.cur != null)
            wNote.setValue(com.maddox.il2.gui.GUINetServerNGenSelect.cur.note, false);
        else
            wNote.setValue("", false);
    }

    private void fillMenu()
    {
        wMenu.lst.clear();
        if(com.maddox.il2.gui.GUINetServerNGenSelect.cur == null)
            return;
        com.maddox.rts.SectFile sectfile = new SectFile(com.maddox.il2.gui.GUINetServerNGenSelect.cur.fileName, 4, true, null, com.maddox.rts.RTSConf.charEncoding, true);
        int i = sectfile.sections();
        for(int j = 0; j < i; j++)
        {
            java.lang.String s = sectfile.sectionName(j);
            if(!s.startsWith("$"))
            {
                int k = sectfile.vars(j);
                if(k != 0)
                {
                    com.maddox.il2.gui.MenuItem menuitem = new MenuItem();
                    menuitem.key = s;
                    menuitem.name = sectfile.get("$locale", s, s);
                    for(int l = 0; l < k; l++)
                    {
                        java.lang.String s1 = sectfile.var(j, l);
                        java.lang.String s3 = sectfile.value(j, l);
                        menuitem.keys.add(s1);
                        menuitem.names.add(s3);
                    }

                    java.lang.String s2 = sectfile.get("$select", s, (java.lang.String)null);
                    if(s2 != null)
                        menuitem.select(s2);
                    wMenu.lst.add(menuitem);
                }
            }
        }

        if(wMenu.lst.size() > 0)
            wMenu.setSelect(0, 0);
    }

    private void getChanges()
    {
        if(com.maddox.il2.gui.GUINetServerNGenSelect.cur == null)
            return;
        com.maddox.rts.SectFile sectfile = new SectFile(com.maddox.il2.gui.GUINetServerNGenSelect.cur.fileName, 5, true, null, com.maddox.rts.RTSConf.charEncoding, true);
        boolean flag = false;
        java.lang.String s = wNote.getValue();
        if(!s.equals(com.maddox.il2.gui.GUINetServerNGenSelect.cur.note))
        {
            com.maddox.il2.gui.GUINetServerNGenSelect.cur.note = s;
            sectfile.set("$locale", "note", s);
            flag = true;
        }
        for(int i = 0; i < wMenu.lst.size(); i++)
        {
            com.maddox.il2.gui.MenuItem menuitem = (com.maddox.il2.gui.MenuItem)wMenu.lst.get(i);
            java.lang.String s1 = (java.lang.String)menuitem.keys.get(menuitem.select);
            if(!s1.equals(sectfile.get("$select", menuitem.key, (java.lang.String)null)))
            {
                sectfile.set("$select", menuitem.key, s1);
                flag = true;
            }
        }

        if(flag)
            sectfile.saveFile();
    }

    public GUINetServerNGenProp(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(69);
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("ngenp.info");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wNote = (com.maddox.gwindow.GWindowEditControl)dialogClient.addControl(new GWindowEditControl(dialogClient, 0.0F, 0.0F, 1.0F, 2.0F, null));
        wMenu = new Menu(dialogClient);
        wTimeHour = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wTimeMins = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wTimeHour.add("00");
        wTimeHour.add("01");
        wTimeHour.add("02");
        wTimeHour.add("03");
        wTimeHour.add("04");
        wTimeHour.add("05");
        wTimeHour.add("06");
        wTimeHour.add("07");
        wTimeHour.add("08");
        wTimeHour.add("09");
        wTimeHour.add("10");
        wTimeHour.add("11");
        wTimeHour.add("12");
        wTimeHour.add("13");
        wTimeHour.add("14");
        wTimeHour.add("15");
        wTimeHour.add("16");
        wTimeHour.add("17");
        wTimeHour.add("18");
        wTimeHour.add("19");
        wTimeHour.add("20");
        wTimeHour.add("21");
        wTimeHour.add("22");
        wTimeHour.add("23");
        wTimeHour.setEditable(false);
        wTimeHour.setSelected(0, true, false);
        wTimeMins.add("00");
        wTimeMins.add("15");
        wTimeMins.add("30");
        wTimeMins.add("45");
        wTimeMins.setEditable(false);
        wTimeMins.setSelected(0, true, false);
        sLand = (com.maddox.il2.gui.GUISwitchBox3)dialogClient.addControl(new GUISwitchBox3(dialogClient));
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bList = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bDiff = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bLast = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bNew = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.gwindow.GWindowEditControl wNote;
    public com.maddox.il2.gui.Menu wMenu;
    public com.maddox.il2.gui.GUIButton bList;
    public com.maddox.il2.gui.GUIButton bDiff;
    public com.maddox.il2.gui.GUIButton bLast;
    public com.maddox.il2.gui.GUIButton bNew;
    public com.maddox.gwindow.GWindowComboControl wTimeHour;
    public com.maddox.gwindow.GWindowComboControl wTimeMins;
    public com.maddox.il2.gui.GUISwitchBox3 sLand;
    private com.maddox.gwindow.GWindowMessageBox loadMessageBox;





}
