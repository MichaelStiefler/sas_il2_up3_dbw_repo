// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUINetServerMisSelect.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
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
import com.maddox.il2.net.USGS;
import com.maddox.rts.BackgroundTask;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.HomePath;
import com.maddox.rts.MsgAction;
import com.maddox.rts.MsgBackgroundTaskListener;
import com.maddox.rts.NetConnect;
import com.maddox.rts.NetEnv;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUINetServer, GUISeparate, 
//            GUINetAircraft

public class GUINetServerMisSelect extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2 || loadMessageBox != null)
                return super.notify(gwindow, i, j);
            if(gwindow == wPrev)
            {
                com.maddox.il2.gui.GUINetServer.exitServer(true);
                return true;
            }
            if(gwindow == wDirs)
            {
                fillFiles();
                return true;
            }
            if(gwindow == wDifficulty)
            {
                com.maddox.il2.game.Main.stateStack().push(41);
                return true;
            }
            if(gwindow == wNext)
            {
                if(wDirs.getValue() == null)
                    return true;
                int k = wTable.selectRow;
                if(k < 0 || k >= wTable.files.size())
                {
                    return true;
                } else
                {
                    com.maddox.il2.gui.FileMission filemission = (com.maddox.il2.gui.FileMission)wTable.files.get(k);
                    com.maddox.il2.game.Main.cur().currentMissionFile = new SectFile(HOME_DIR() + "/" + wDirs.getValue() + "/" + filemission.fileName, 0);
                    doLoadMission();
                    return true;
                }
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(432F), y1024(546F), x1024(384F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(416F), y1024(32F), 2.0F, y1024(608F));
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(64F), y1024(156F), x1024(240F), y1024(32F), 0, i18n("netsms.MissionType"));
            draw(x1024(64F), y1024(264F), x1024(240F), y1024(32F), 0, i18n("netsms.Missions"));
            draw(x1024(464F), y1024(264F), x1024(248F), y1024(32F), 0, i18n("netsms.Description"));
            draw(x1024(104F), y1024(592F), x1024(192F), y1024(48F), 0, !com.maddox.il2.net.USGS.isUsed() && com.maddox.il2.game.Main.cur().netGameSpy == null ? i18n("netsms.MainMenu") : i18n("main.Quit"));
            draw(x1024(496F), y1024(592F), x1024(128F), y1024(48F), 0, i18n("brief.Difficulty"));
            draw(x1024(528F), y1024(592F), x1024(216F), y1024(48F), 2, i18n("netsms.Load"));
        }

        public void setPosSize()
        {
            set1024PosSize(80F, 64F, 848F, 672F);
            wPrev.setPosC(x1024(56F), y1024(616F));
            wDifficulty.setPosC(x1024(456F), y1024(616F));
            wNext.setPosC(x1024(792F), y1024(616F));
            wDirs.setPosSize(x1024(48F), y1024(192F), x1024(336F), M(2.0F));
            wTable.setPosSize(x1024(48F), y1024(304F), x1024(336F), y1024(256F));
            wDescript.setPosSize(x1024(448F), y1024(312F), x1024(354F), y1024(212F));
        }

        public DialogClient()
        {
        }
    }

    public class WDescript extends com.maddox.gwindow.GWindow
    {

        public void render()
        {
            java.lang.String s = null;
            if(wTable.selectRow >= 0)
            {
                s = ((com.maddox.il2.gui.FileMission)wTable.files.get(wTable.selectRow)).description;
                if(s != null && s.length() == 0)
                    s = null;
            }
            if(s != null)
            {
                setCanvasFont(0);
                setCanvasColorBLACK();
                drawLines(0.0F, -root.C.font.descender, s, 0, s.length(), win.dx, root.C.font.height);
            }
        }

        public WDescript()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return files == null ? 0 : files.size();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            java.lang.String s = ((com.maddox.il2.gui.FileMission)files.get(i)).name;
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
                setCanvasColorWHITE();
                draw(0.0F, 0.0F, f, f1, 0, s);
            } else
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, 0, s);
            }
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = false;
            addColumn(com.maddox.il2.game.I18N.gui("netsms.Mission_files"), null);
            vSB.scroll = rowHeight(0);
            resized();
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
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

        public java.util.ArrayList files;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow, 2.0F, 4F, 20F, 16F);
            files = new ArrayList();
            bNotify = true;
            wClient.bNotify = true;
        }
    }

    static class FileMission
    {

        public java.lang.String fileName;
        public java.lang.String name;
        public java.lang.String description;

        public FileMission(java.lang.String s, java.lang.String s1)
        {
            fileName = s1;
            try
            {
                java.lang.String s2 = s1;
                int i = s2.lastIndexOf(".");
                if(i >= 0)
                    s2 = s2.substring(0, i);
                java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle(s + "/" + s2, com.maddox.rts.RTSConf.cur.locale);
                name = resourcebundle.getString("Name");
                description = resourcebundle.getString("Short");
            }
            catch(java.lang.Exception exception)
            {
                name = s1;
                description = null;
            }
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
        loadMessageBox = new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.game.Main3D.cur3D().guiManager.root, 20F, true, i18n("netsms.StandBy"), i18n("netsms.Loading_simulation"), 5, 0.0F) {

            public void result(int i)
            {
                if(i == 1)
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
                if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
                {
                    ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).setBornPlace(-1);
                    com.maddox.rts.CmdEnv.top().exec("mission BEGIN");
                    com.maddox.il2.game.Main.stateStack().change(39);
                } else
                if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
                {
                    ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).resetAllPlaces();
                    com.maddox.rts.CmdEnv.top().exec("mission BEGIN");
                    int i = com.maddox.il2.gui.GUINetAircraft.serverPlace();
                    if(i != -1)
                        ((com.maddox.il2.net.NetUser)com.maddox.rts.NetEnv.host()).requestPlace(i);
                    com.maddox.il2.game.Main.stateStack().change(45);
                }
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

    public java.lang.String HOME_DIR()
    {
        if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            return "missions/net/coop";
        if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
            return "missions/net/dogfight";
        else
            return "missions/net";
    }

    public void enter(com.maddox.il2.game.GameState gamestate)
    {
        com.maddox.il2.ai.World.cur().diffCur.set(com.maddox.il2.ai.World.cur().userCfg.netDifficulty);
        com.maddox.il2.game.Main.cur().netServerParams.setDifficulty(com.maddox.il2.ai.World.cur().diffCur.get());
        if(gamestate.id() == 35)
        {
            _enter();
        } else
        {
            if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
            {
                com.maddox.rts.NetEnv.cur().connect.bindEnable(true);
                com.maddox.il2.game.Main.cur().netServerParams.USGSupdate();
            }
            client.activateWindow();
        }
    }

    public void enterPop(com.maddox.il2.game.GameState gamestate)
    {
        if(gamestate.id() == 41)
        {
            com.maddox.il2.ai.World.cur().userCfg.netDifficulty = com.maddox.il2.ai.World.cur().diffCur.get();
            com.maddox.il2.ai.World.cur().userCfg.saveConf();
            com.maddox.il2.game.Main.cur().netServerParams.setDifficulty(com.maddox.il2.ai.World.cur().diffCur.get());
        }
        client.activateWindow();
    }

    public void _enter()
    {
        fillDirs();
        if(com.maddox.il2.game.Main.cur().netServerParams.isCoop())
        {
            infoMenu.info = i18n("netsms.infoC");
            com.maddox.rts.NetEnv.cur().connect.bindEnable(true);
            com.maddox.il2.game.Main.cur().netServerParams.USGSupdate();
        } else
        if(com.maddox.il2.game.Main.cur().netServerParams.isDogfight())
            infoMenu.info = i18n("netsms.infoD");
        else
            infoMenu.info = i18n("netsms.infoM");
        client.activateWindow();
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public void fillDirs()
    {
        java.io.File file = new File(com.maddox.rts.HomePath.get(0), HOME_DIR());
        java.io.File afile[] = file.listFiles();
        wDirs.clear(false);
        if(afile == null || afile.length == 0)
        {
            wTable.files.clear();
            wTable.setSelect(-1, 0);
            return;
        }
        for(int i = 0; i < afile.length; i++)
            if(afile[i].isDirectory() && !afile[i].isHidden() && !".".equals(afile[i].getName()) && !"..".equals(afile[i].getName()))
                _scanMap.put(afile[i].getName(), null);

        for(java.util.Iterator iterator = _scanMap.keySet().iterator(); iterator.hasNext(); wDirs.add((java.lang.String)iterator.next()));
        if(_scanMap.size() > 0)
            wDirs.setSelected(0, true, false);
        _scanMap.clear();
        fillFiles();
    }

    public void fillFiles()
    {
        wTable.files.clear();
        java.lang.String s = wDirs.getValue();
        if(s != null)
        {
            java.lang.String s1 = HOME_DIR() + "/" + s;
            java.io.File file = new File(com.maddox.rts.HomePath.get(0), s1);
            java.io.File afile[] = file.listFiles();
            if(afile != null && afile.length > 0)
            {
                for(int i = 0; i < afile.length; i++)
                    if(!afile[i].isDirectory() && !afile[i].isHidden() && afile[i].getName().toLowerCase().lastIndexOf(".properties") < 0)
                    {
                        com.maddox.il2.gui.FileMission filemission = new FileMission(s1, afile[i].getName());
                        _scanMap.put(filemission.fileName, filemission);
                    }

                for(java.util.Iterator iterator = _scanMap.keySet().iterator(); iterator.hasNext(); wTable.files.add(_scanMap.get(iterator.next())));
                if(_scanMap.size() > 0)
                    wTable.setSelect(0, 0);
                else
                    wTable.setSelect(-1, 0);
                _scanMap.clear();
            } else
            {
                wTable.setSelect(-1, 0);
            }
        } else
        {
            wTable.setSelect(-1, 0);
        }
        wTable.resized();
    }

    public GUINetServerMisSelect(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(38);
        _scanMap = new TreeMap();
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("netsms.infoM");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wDirs = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wDirs.setEditable(false);
        wTable = new Table(dialogClient);
        dialogClient.create(wDescript = new WDescript());
        wDescript.bNotify = true;
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        wPrev = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        wDifficulty = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        wNext = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public static final java.lang.String HOME_DIR = "missions/net";
    public static final java.lang.String HOME_DIR_DOGFIGHT = "missions/net/dogfight";
    public static final java.lang.String HOME_DIR_COOP = "missions/net/coop";
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton wPrev;
    public com.maddox.il2.gui.GUIButton wDifficulty;
    public com.maddox.il2.gui.GUIButton wNext;
    public com.maddox.gwindow.GWindowComboControl wDirs;
    public com.maddox.il2.gui.Table wTable;
    public com.maddox.il2.gui.WDescript wDescript;
    private com.maddox.gwindow.GWindowMessageBox loadMessageBox;
    public java.util.TreeMap _scanMap;




}
