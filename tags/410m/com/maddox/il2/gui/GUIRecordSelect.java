// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIRecordSelect.java

package com.maddox.il2.gui;

import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.rts.HomePath;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.KeyRecord;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUISwitchBox2, GUIDialogClient, GUISeparate

public class GUIRecordSelect extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == wPrev)
            {
                sCycle.setChecked(bCycle, false);
                sTimeCompression.setChecked(bManualTimeCompression, false);
                sViewControls.setChecked(bManualViewControls, false);
                com.maddox.il2.game.Main3D.cur3D().viewSet_Load();
                com.maddox.il2.game.Main.stateStack().pop();
            } else
            {
                if(gwindow == wPlay)
                {
                    bCycle = sCycle.isChecked();
                    bManualTimeCompression = sTimeCompression.isChecked();
                    bManualViewControls = sViewControls.isChecked();
                    int k = wTable.selectRow;
                    if(k < 0 || k >= wTable.files.size())
                    {
                        return true;
                    } else
                    {
                        selectedFile = (java.lang.String)wTable.files.get(k);
                        com.maddox.il2.game.Main.stateStack().push(8);
                        return true;
                    }
                }
                if(gwindow == wDelete)
                {
                    int l = wTable.selectRow;
                    if(l < 0 || l >= wTable.files.size())
                    {
                        return true;
                    } else
                    {
                        new com.maddox.gwindow.GWindowMessageBox(root, 20F, true, i18n("warning.Warning"), i18n("warning.DeleteFile"), 1, 0.0F) {

                            public void result(int i1)
                            {
                                if(i1 != 3)
                                    return;
                                int j1 = wTable.selectRow;
                                java.lang.String s = (java.lang.String)wTable.files.get(j1);
                                try
                                {
                                    java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName("Records/" + s, 0));
                                    file.delete();
                                }
                                catch(java.lang.Exception exception) { }
                                fillFiles();
                                if(j1 >= wTable.files.size())
                                    j1 = wTable.files.size() - 1;
                                if(j1 < 0)
                                {
                                    return;
                                } else
                                {
                                    wTable.setSelect(j1, 0);
                                    return;
                                }
                            }

                        }
;
                        return true;
                    }
                }
                if(gwindow == sViewMessages)
                {
                    com.maddox.il2.game.Main3D.cur3D().hud.bDrawAllMessages = sViewMessages.isChecked();
                    bDrawAllMessages = sViewMessages.isChecked();
                }
            }
            return super.notify(gwindow, i, j);
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(464F), x1024(720F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(448F), y1024(352F), x1024(305F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(432F), y1024(32F), 2.0F, y1024(400F));
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(528F), y1024(48F), x1024(224F), y1024(48F), i18n("record.Cycle"), 2);
            draw(x1024(528F), y1024(128F), x1024(224F), y1024(48F), i18n("record.ManualTime"), 2);
            draw(x1024(528F), y1024(208F), x1024(224F), y1024(48F), i18n("record.ManualView"), 2);
            draw(x1024(528F), y1024(288F), x1024(224F), y1024(48F), i18n("record.InflightMessages"), 2);
            draw(x1024(528F), y1024(384F), x1024(224F), y1024(48F), i18n("record.Delete"), 2);
            draw(x1024(96F), y1024(496F), x1024(208F), y1024(48F), 0, i18n("record.MainMenu"));
            draw(x1024(448F), y1024(496F), x1024(240F), y1024(48F), 2, i18n("record.Play"));
        }

        public void setPosSize()
        {
            set1024PosSize(128F, 112F, 784F, 576F);
            wPrev.setPosC(x1024(56F), y1024(520F));
            wPlay.setPosC(x1024(728F), y1024(520F));
            wDelete.setPosC(x1024(488F), y1024(408F));
            sCycle.setPosC(x1024(496F), y1024(72F));
            sTimeCompression.setPosC(x1024(496F), y1024(152F));
            sViewControls.setPosC(x1024(496F), y1024(232F));
            sViewMessages.setPosC(x1024(496F), y1024(312F));
            wTable.setPosSize(x1024(32F), y1024(32F), x1024(384F), y1024(400F));
        }


        public DialogClient()
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
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
                setCanvasColorWHITE();
                draw(0.0F, 0.0F, f, f1, 0, (java.lang.String)files.get(i));
            } else
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, 0, (java.lang.String)files.get(i));
            }
        }

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = false;
            addColumn(com.maddox.il2.game.I18N.gui("record.TrackFiles"), null);
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
            super(gwindow);
            files = new ArrayList();
            bNotify = true;
            wClient.bNotify = true;
        }
    }


    public void _enter()
    {
        if(com.maddox.il2.game.Mission.cur() != null && !com.maddox.il2.game.Mission.cur().isDestroyed())
            com.maddox.il2.game.Mission.cur().destroy();
        if(com.maddox.il2.game.Main3D.cur3D().keyRecord != null)
            com.maddox.il2.game.Main3D.cur3D().keyRecord.clearRecorded();
        bSaveManualTimeCompression = com.maddox.rts.HotKeyEnv.isEnabled("timeCompression");
        bSaveManualViewControls = com.maddox.rts.HotKeyEnv.isEnabled("aircraftView");
        sCycle.setChecked(bCycle, false);
        sTimeCompression.setChecked(bManualTimeCompression, false);
        sViewControls.setChecked(bManualViewControls, false);
        sViewMessages.setChecked(bDrawAllMessages, false);
        com.maddox.il2.game.Main3D.cur3D().hud.bDrawAllMessages = bDrawAllMessages;
        fillFiles();
        client.activateWindow();
    }

    public void leavePop(com.maddox.il2.game.GameState gamestate)
    {
        com.maddox.il2.game.Main3D.cur3D().hud.bDrawAllMessages = true;
        com.maddox.il2.ai.World.cur().setUserCovers();
        super.leavePop(gamestate);
    }

    public void _leave()
    {
        com.maddox.rts.HotKeyEnv.enable("timeCompression", bSaveManualTimeCompression);
        com.maddox.rts.HotKeyEnv.enable("aircraftView", bSaveManualViewControls);
        com.maddox.rts.HotKeyEnv.enable("HookView", bSaveManualViewControls);
        com.maddox.rts.HotKeyEnv.enable("PanView", bSaveManualViewControls);
        com.maddox.rts.HotKeyEnv.enable("SnapView", bSaveManualViewControls);
        client.hideWindow();
    }

    public void fillFiles()
    {
        wTable.files.clear();
        java.io.File file = new File(com.maddox.rts.HomePath.get(0), "Records");
        java.io.File afile[] = file.listFiles();
        if(afile != null && afile.length > 0)
        {
            for(int i = 0; i < afile.length; i++)
                if(!afile[i].isDirectory() && !afile[i].isHidden())
                    _scanMap.put(afile[i].getName(), null);

            for(java.util.Iterator iterator = _scanMap.keySet().iterator(); iterator.hasNext(); wTable.files.add(iterator.next()));
            if(_scanMap.size() > 0)
                wTable.setSelect(0, 0);
            _scanMap.clear();
        }
        wTable.resized();
    }

    public GUIRecordSelect(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(7);
        bCycle = true;
        bManualTimeCompression = false;
        bManualViewControls = false;
        bDrawAllMessages = true;
        bSaveManualTimeCompression = false;
        bSaveManualViewControls = false;
        _scanMap = new TreeMap();
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("record.infoSelect");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wTable = new Table(dialogClient);
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        wPrev = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        wPlay = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        wDelete = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        sCycle = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient));
        sTimeCompression = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient));
        sViewControls = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient));
        sViewMessages = (com.maddox.il2.gui.GUISwitchBox2)dialogClient.addControl(new GUISwitchBox2(dialogClient));
        sCycle.setChecked(bCycle, false);
        sTimeCompression.setChecked(bManualTimeCompression, false);
        sViewControls.setChecked(bManualViewControls, false);
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public java.lang.String selectedFile;
    public boolean bCycle;
    public boolean bManualTimeCompression;
    public boolean bManualViewControls;
    public boolean bDrawAllMessages;
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton wPrev;
    public com.maddox.il2.gui.GUIButton wPlay;
    public com.maddox.il2.gui.GUIButton wDelete;
    public com.maddox.il2.gui.Table wTable;
    public com.maddox.il2.gui.GUISwitchBox2 sCycle;
    public com.maddox.il2.gui.GUISwitchBox2 sTimeCompression;
    public com.maddox.il2.gui.GUISwitchBox2 sViewControls;
    public com.maddox.il2.gui.GUISwitchBox2 sViewMessages;
    public boolean bSaveManualTimeCompression;
    public boolean bSaveManualViewControls;
    public java.util.TreeMap _scanMap;
}
