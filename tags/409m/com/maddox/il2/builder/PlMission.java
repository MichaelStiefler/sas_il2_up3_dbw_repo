// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMission.java

package com.maddox.il2.builder;

import com.maddox.TexImage.TexImage;
import com.maddox.gwindow.GFileFilter;
import com.maddox.gwindow.GFileFilterName;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFileBox;
import com.maddox.gwindow.GWindowFileBoxExec;
import com.maddox.gwindow.GWindowFileOpen;
import com.maddox.gwindow.GWindowFileSaveAs;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRootMenu;
import com.maddox.gwindow.GWindowStatusBar;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.Land2DText;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.effects.SpritesFog;
import com.maddox.rts.HomePath;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.io.File;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, Path, PathAir, PlMapLoad, 
//            Builder, Pathes, PathFind, BldConfig

public class PlMission extends com.maddox.il2.builder.Plugin
{
    class WConditions extends com.maddox.gwindow.GWindowFramed
    {

        public void windowShown()
        {
            mConditions.bChecked = true;
            super.windowShown();
        }

        public void windowHidden()
        {
            mConditions.bChecked = false;
            super.windowHidden();
        }

        public void created()
        {
            bAlwaysOnTop = true;
            super.created();
            title = com.maddox.il2.builder.Plugin.i18n("MissionConditions");
            clientWindow = create(new GWindowTabDialogClient());
            com.maddox.gwindow.GWindowTabDialogClient gwindowtabdialogclient = (com.maddox.gwindow.GWindowTabDialogClient)clientWindow;
            com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = null;
            gwindowtabdialogclient.addTab(com.maddox.il2.builder.Plugin.i18n("weather"), gwindowtabdialogclient.create(gwindowdialogclient = new GWindowDialogClient()));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 4F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Time"), null));
            gwindowdialogclient.addControl(wTimeH = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 6F, 1.0F, 2.0F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        getTime();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 8.2F, 1.0F, 1.0F, 1.3F, ":", null));
            gwindowdialogclient.addControl(wTimeM = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 8.5F, 1.0F, 2.0F, 1.3F, "") {

                public void afterCreated()
                {
                    super.afterCreated();
                    bNumericOnly = true;
                    bDelayedNotify = true;
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        getTime();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 6F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Weather"), null));
            gwindowdialogclient.addControl(wCloudType = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 8F, 3F, 8F) {

                public boolean notify(int i, int j)
                {
                    if(i == 2)
                        getCloudType();
                    return super.notify(i, j);
                }

            }
);
            wCloudType.setEditable(false);
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Clear"));
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Good"));
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Hazy"));
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Poor"));
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Blind"));
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Rain/Snow"));
            wCloudType.add(com.maddox.il2.builder.Plugin.i18n("Thunder"));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 9F, 1.3F, com.maddox.il2.builder.Plugin.i18n("CloudHeight"), null));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 16.5F, 5F, 2.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("[m]"), null));
            gwindowdialogclient.addControl(wCloudHeight = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 11F, 5F, 5F) {

                public boolean notify(int i, int j)
                {
                    if(i == 2)
                        getCloudHeight();
                    return super.notify(i, j);
                }

            }
);
            wCloudHeight.setEditable(false);
            wCloudHeight.add("500");
            wCloudHeight.add("600");
            wCloudHeight.add("700");
            wCloudHeight.add("800");
            wCloudHeight.add("900");
            wCloudHeight.add("1000");
            wCloudHeight.add("1100");
            wCloudHeight.add("1200");
            wCloudHeight.add("1300");
            wCloudHeight.add("1400");
            wCloudHeight.add("1500");
            gwindowtabdialogclient.addTab(com.maddox.il2.builder.Plugin.i18n("misc"), gwindowtabdialogclient.create(gwindowdialogclient = new GWindowDialogClient()));
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 12F, 1.3F, com.maddox.il2.builder.Plugin.i18n("timeLocked"), null));
            gwindowdialogclient.addControl(wTimeFix = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 14F, 1.0F, null) {

                public void preRender()
                {
                    super.preRender();
                    setChecked(com.maddox.il2.ai.World.cur().isTimeOfDayConstant(), false);
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        com.maddox.il2.ai.World.cur().setTimeOfDayConstant(isChecked());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
            gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 12F, 1.3F, com.maddox.il2.builder.Plugin.i18n("weaponsLocked"), null));
            gwindowdialogclient.addControl(wWeaponFix = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 14F, 3F, null) {

                public void preRender()
                {
                    super.preRender();
                    setChecked(com.maddox.il2.ai.World.cur().isWeaponsConstant(), false);
                }

                public boolean notify(int i, int j)
                {
                    if(i != 2)
                    {
                        return false;
                    } else
                    {
                        com.maddox.il2.ai.World.cur().setWeaponsConstant(isChecked());
                        com.maddox.il2.builder.PlMission.setChanged();
                        return false;
                    }
                }

            }
);
        }

        public void update()
        {
            float f = com.maddox.il2.ai.World.getTimeofDay();
            int i = (int)f % 24;
            int j = (int)(60F * (f - (float)(int)f));
            wTimeH.setValue("" + i, false);
            wTimeM.setValue("" + j, false);
            wCloudType.setSelected(cloudType, true, false);
            int k = (int)(((cloudHeight - 500F) + 50F) / 100F);
            if(k < 0)
                k = 0;
            if(k > 10)
                k = 10;
            wCloudHeight.setSelected(k, true, false);
            wTimeFix.setChecked(com.maddox.il2.ai.World.cur().isTimeOfDayConstant(), false);
            wWeaponFix.setChecked(com.maddox.il2.ai.World.cur().isWeaponsConstant(), false);
        }

        public void getTime()
        {
            java.lang.String s = wTimeH.getValue();
            double d = 0.0D;
            try
            {
                d = java.lang.Double.parseDouble(s);
            }
            catch(java.lang.Exception exception) { }
            if(d < 0.0D)
                d = 0.0D;
            if(d > 23D)
                d = 23D;
            s = wTimeM.getValue();
            double d1 = 0.0D;
            try
            {
                d1 = java.lang.Double.parseDouble(s);
            }
            catch(java.lang.Exception exception1) { }
            if(d1 < 0.0D)
                d1 = 0.0D;
            if(d1 >= 60D)
                d1 = 59D;
            float f = (float)(d + d1 / 60D);
            if((int)(f * 60F) != (int)(com.maddox.il2.ai.World.getTimeofDay() * 60F))
            {
                com.maddox.il2.ai.World.setTimeofDay(f);
                if(com.maddox.il2.builder.Plugin.builder.isLoadedLandscape())
                    com.maddox.il2.ai.World.land().cubeFullUpdate();
            }
            com.maddox.il2.builder.PlMission.setChanged();
            update();
        }

        public void getCloudType()
        {
            cloudType = wCloudType.getSelected();
            com.maddox.il2.game.Mission.setCloudsType(cloudType);
            com.maddox.il2.builder.PlMission.setChanged();
        }

        public void getCloudHeight()
        {
            cloudHeight = (float)(500 + 100 * wCloudHeight.getSelected());
            com.maddox.il2.game.Mission.setCloudsHeight(cloudHeight);
            com.maddox.il2.builder.PlMission.setChanged();
        }

        public void afterCreated()
        {
            super.afterCreated();
            resized();
            close(false);
        }

        com.maddox.gwindow.GWindowEditControl wTimeH;
        com.maddox.gwindow.GWindowEditControl wTimeM;
        com.maddox.gwindow.GWindowComboControl wCloudType;
        com.maddox.gwindow.GWindowComboControl wCloudHeight;
        com.maddox.gwindow.GWindowCheckBox wTimeFix;
        com.maddox.gwindow.GWindowCheckBox wWeaponFix;

        public WConditions()
        {
            doNew(com.maddox.il2.builder.Plugin.builder.clientWindow, 2.0F, 2.0F, 20F, 12F, true);
            bSizable = false;
        }
    }

    class DlgFileConfirmSave extends com.maddox.gwindow.GWindowFileBoxExec
    {

        public boolean isCloseBox()
        {
            return bClose;
        }

        public void exec(com.maddox.gwindow.GWindowFileBox gwindowfilebox, java.lang.String s)
        {
            box = gwindowfilebox;
            bClose = true;
            if(s == null || box.files.size() == 0)
            {
                box.endExec();
                return;
            }
            int i = s.lastIndexOf("/");
            if(i >= 0)
                s = s.substring(i + 1);
            for(int j = 0; j < box.files.size(); j++)
            {
                java.lang.String s1 = ((java.io.File)box.files.get(j)).getName();
                if(s.compareToIgnoreCase(s1) == 0)
                {
                    com.maddox.gwindow.GWindowMessageBox gwindowmessagebox = new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.builder.Plugin.builder.clientWindow.root, 20F, true, com.maddox.il2.game.I18N.gui("warning.Warning"), com.maddox.il2.game.I18N.gui("warning.ReplaceFile"), 1, 0.0F) {

                        public void result(int k)
                        {
                            if(k != 3)
                                bClose = false;
                            box.endExec();
                        }

                    }
;
                    return;
                }
            }

            box.endExec();
        }

        com.maddox.gwindow.GWindowFileBox box;
        boolean bClose;

        DlgFileConfirmSave()
        {
            bClose = true;
        }
    }

    static class GWindowMenuItemArmy extends com.maddox.gwindow.GWindowMenuItem
    {

        int army;

        public GWindowMenuItemArmy(com.maddox.gwindow.GWindowMenu gwindowmenu, java.lang.String s, java.lang.String s1, int i)
        {
            super(gwindowmenu, s, s1);
            army = i;
        }
    }


    public static void setChanged()
    {
        if(cur != null)
            cur.bChanged = true;
    }

    public static boolean isChanged()
    {
        if(cur != null)
            return cur.bChanged;
        else
            return false;
    }

    public static java.lang.String missionFileName()
    {
        if(cur == null)
            return null;
        else
            return cur.missionFileName;
    }

    public static void doMissionReload()
    {
        if(cur == null)
            return;
        if(!cur.bReload)
        {
            return;
        } else
        {
            cur.bReload = false;
            cur.doLoadMission("missions/" + cur.missionFileName);
            return;
        }
    }

    public boolean load(java.lang.String s)
    {
        com.maddox.il2.builder.Plugin.builder.deleteAll();
        com.maddox.rts.SectFile sectfile = new SectFile(s, 0);
        int i = sectfile.sectionIndex("MAIN");
        if(i < 0)
        {
            com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: '" + s + "' - section MAIN not found");
            return false;
        }
        int j = sectfile.varIndex(i, "MAP");
        if(j < 0)
        {
            com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: '" + s + "' - in section MAIN line MAP not found");
            return false;
        }
        java.lang.String s1 = sectfile.value(i, j);
        com.maddox.il2.builder.PlMapLoad.Land land = com.maddox.il2.builder.PlMapLoad.getLandForFileName(s1);
        if(land == com.maddox.il2.builder.PlMapLoad.getLandLoaded())
        {
            com.maddox.il2.ai.World.cur().statics.restoreAllBridges();
            com.maddox.il2.ai.World.cur().statics.restoreAllHouses();
        } else
        if(!pluginMapLoad.mapLoad(land))
        {
            com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: '" + s + "' - tirrain '" + s1 + "' not loaded");
            return false;
        }
        int k = sectfile.get("MAIN", "TIMECONSTANT", 0, 0, 1);
        com.maddox.il2.ai.World.cur().setTimeOfDayConstant(k == 1);
        com.maddox.il2.ai.World.setTimeofDay(sectfile.get("MAIN", "TIME", 12F, 0.0F, 23.99F));
        int l = sectfile.get("MAIN", "WEAPONSCONSTANT", 0, 0, 1);
        com.maddox.il2.ai.World.cur().setWeaponsConstant(l == 1);
        java.lang.String s2 = sectfile.get("MAIN", "player", (java.lang.String)null);
        com.maddox.il2.builder.Path.playerNum = sectfile.get("MAIN", "playerNum", 0, 0, 3);
        missionArmy = sectfile.get("MAIN", "army", 1, 1, 2);
        cloudType = sectfile.get("MAIN", "CloudType", 0, 0, 6);
        cloudHeight = sectfile.get("MAIN", "CloudHeight", 1000F, 500F, 1500F);
        com.maddox.il2.game.Mission.createClouds(cloudType, cloudHeight);
        if(com.maddox.il2.game.Main3D.cur3D().clouds != null)
            com.maddox.il2.game.Main3D.cur3D().clouds.setShow(false);
        com.maddox.il2.game.Main3D.cur3D().spritesFog.setShow(false);
        wConditions.update();
        com.maddox.il2.builder.Plugin.doLoad(sectfile);
        if(s2 != null)
        {
            java.lang.Object aobj[] = com.maddox.il2.builder.Plugin.builder.pathes.getOwnerAttached();
            for(int i1 = 0; i1 < aobj.length; i1++)
            {
                com.maddox.il2.builder.Path path = (com.maddox.il2.builder.Path)aobj[i1];
                if(!s2.equals(path.name()))
                    continue;
                if(!((com.maddox.il2.builder.PathAir)path).bOnlyAI)
                {
                    com.maddox.il2.builder.Path.player = path;
                    missionArmy = path.getArmy();
                }
                break;
            }

        }
        com.maddox.il2.builder.Plugin.builder.repaint();
        bChanged = false;
        return true;
    }

    public boolean save(java.lang.String s)
    {
        if(com.maddox.il2.builder.PlMapLoad.getLandLoaded() == null)
        {
            com.maddox.il2.builder.Plugin.builder.tipErr("MissionSave: tirrain not selected");
            return false;
        }
        com.maddox.rts.SectFile sectfile = new SectFile();
        sectfile.setFileName(s);
        int i = sectfile.sectionAdd("MAIN");
        sectfile.lineAdd(i, "MAP", com.maddox.il2.builder.PlMapLoad.mapFileName());
        sectfile.lineAdd(i, "TIME", "" + com.maddox.il2.ai.World.getTimeofDay());
        if(com.maddox.il2.ai.World.cur().isTimeOfDayConstant())
            sectfile.lineAdd(i, "TIMECONSTANT", "1");
        if(com.maddox.il2.ai.World.cur().isWeaponsConstant())
            sectfile.lineAdd(i, "WEAPONSCONSTANT", "1");
        sectfile.lineAdd(i, "CloudType", "" + cloudType);
        sectfile.lineAdd(i, "CloudHeight", "" + cloudHeight);
        if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.builder.Path.player))
        {
            sectfile.lineAdd(i, "player", com.maddox.il2.builder.Path.player.name());
            if(com.maddox.il2.builder.Path.playerNum >= ((com.maddox.il2.builder.PathAir)com.maddox.il2.builder.Path.player).planes)
                com.maddox.il2.builder.Path.playerNum = 0;
        } else
        {
            com.maddox.il2.builder.Path.playerNum = 0;
        }
        sectfile.lineAdd(i, "army", "" + missionArmy);
        sectfile.lineAdd(i, "playerNum", "" + com.maddox.il2.builder.Path.playerNum);
        if(!com.maddox.il2.builder.Plugin.doSave(sectfile))
        {
            return false;
        } else
        {
            sectfile.saveFile(s);
            bChanged = false;
            return true;
        }
    }

    public void mapLoaded()
    {
        if(!com.maddox.il2.builder.Plugin.builder.isLoadedLandscape())
            return;
        java.lang.String s = "maps/" + com.maddox.il2.builder.PlMapLoad.mapFileName();
        com.maddox.rts.SectFile sectfile = new SectFile(s);
        int i = sectfile.sectionIndex("static");
        if(i >= 0 && sectfile.vars(i) > 0)
        {
            java.lang.String s1 = sectfile.var(i, 0);
            com.maddox.il2.objects.Statics.load(com.maddox.rts.HomePath.concatNames(s, s1), com.maddox.il2.builder.PlMapLoad.bridgeActors);
        }
        int j = sectfile.sectionIndex("text");
        if(j >= 0 && sectfile.vars(j) > 0)
        {
            java.lang.String s2 = sectfile.var(j, 0);
            if(com.maddox.il2.game.Main3D.cur3D().land2DText == null)
                com.maddox.il2.game.Main3D.cur3D().land2DText = new Land2DText();
            else
                com.maddox.il2.game.Main3D.cur3D().land2DText.clear();
            com.maddox.il2.game.Main3D.cur3D().land2DText.load(com.maddox.rts.HomePath.concatNames(s, s2));
        }
        com.maddox.il2.objects.Statics.trim();
        com.maddox.il2.engine.Landscape landscape = com.maddox.il2.ai.World.land();
        com.maddox.il2.engine.Landscape _tmp = landscape;
        if(com.maddox.il2.engine.Landscape.isExistMeshs())
        {
            for(int k = 0; k < com.maddox.il2.builder.PathFind.tShip.sy; k++)
            {
                for(int l = 0; l < com.maddox.il2.builder.PathFind.tShip.sx; l++)
                {
                    com.maddox.il2.engine.Landscape _tmp1 = landscape;
                    if(com.maddox.il2.engine.Landscape.isExistMesh(l, com.maddox.il2.builder.PathFind.tShip.sy - k - 1))
                    {
                        com.maddox.il2.builder.PathFind.tShip.I(l, k, com.maddox.il2.builder.PathFind.tShip.intI(l, k) | 8);
                        com.maddox.il2.builder.PathFind.tNoShip.I(l, k, com.maddox.il2.builder.PathFind.tNoShip.intI(l, k) | 8);
                    }
                }

            }

        }
        com.maddox.il2.game.Mission.createClouds(cloudType, cloudHeight);
        if(com.maddox.il2.game.Main3D.cur3D().clouds != null)
            com.maddox.il2.game.Main3D.cur3D().clouds.setShow(false);
        com.maddox.il2.game.Main3D.cur3D().spritesFog.setShow(false);
        wConditions.update();
    }

    public void configure()
    {
        com.maddox.il2.builder.Plugin.builder.bMultiSelect = false;
        if(com.maddox.il2.builder.Plugin.getPlugin("MapLoad") == null)
        {
            throw new RuntimeException("PlMission: plugin 'MapLoad' not found");
        } else
        {
            pluginMapLoad = (com.maddox.il2.builder.PlMapLoad)com.maddox.il2.builder.Plugin.getPlugin("MapLoad");
            return;
        }
    }

    void _viewTypeAll(boolean flag)
    {
        com.maddox.il2.builder.Plugin.doViewTypeAll(flag);
        viewBridge(flag);
        viewRunaway(flag);
        viewName.bChecked = com.maddox.il2.builder.Plugin.builder.conf.bShowName = flag;
        viewTime.bChecked = com.maddox.il2.builder.Plugin.builder.conf.bShowTime = flag;
        for(int i = 0; i < com.maddox.il2.builder.Plugin.builder.conf.bShowArmy.length; i++)
            viewArmy[i].bChecked = com.maddox.il2.builder.Plugin.builder.conf.bShowArmy[i] = flag;

        if(!flag)
            com.maddox.il2.builder.Plugin.builder.setSelected(null);
    }

    void viewBridge(boolean flag)
    {
        com.maddox.il2.builder.Plugin.builder.conf.bViewBridge = flag;
        viewBridge.bChecked = com.maddox.il2.builder.Plugin.builder.conf.bViewBridge;
    }

    void viewBridge()
    {
        viewBridge(!com.maddox.il2.builder.Plugin.builder.conf.bViewBridge);
    }

    void viewRunaway(boolean flag)
    {
        com.maddox.il2.builder.Plugin.builder.conf.bViewRunaway = flag;
        viewRunaway.bChecked = com.maddox.il2.builder.Plugin.builder.conf.bViewRunaway;
    }

    void viewRunaway()
    {
        viewRunaway(!com.maddox.il2.builder.Plugin.builder.conf.bViewRunaway);
    }

    public static void checkShowCurrentArmy()
    {
        java.lang.Object obj = com.maddox.il2.builder.Plugin.builder.selectedPath();
        if(obj == null)
            obj = com.maddox.il2.builder.Plugin.builder.selectedActor();
        if(obj == null)
            return;
        int i = ((com.maddox.il2.engine.Actor) (obj)).getArmy();
        if(!com.maddox.il2.builder.Plugin.builder.conf.bShowArmy[i])
            com.maddox.il2.builder.Plugin.builder.setSelected(null);
    }

    private java.lang.String checkMisExtension(java.lang.String s)
    {
        if(!s.toLowerCase().endsWith(".mis"))
            return s + ".mis";
        else
            return s;
    }

    public void createGUI()
    {
        com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem("-", null);
        viewBridge = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("showBridge"), com.maddox.il2.builder.Plugin.i18n("TIPshowBridge")) {

            public void execute()
            {
                viewBridge();
            }

        }
);
        viewBridge.bChecked = com.maddox.il2.builder.Plugin.builder.conf.bViewBridge;
        viewRunaway = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("showRunway"), com.maddox.il2.builder.Plugin.i18n("TIPshowRunway")) {

            public void execute()
            {
                viewRunaway();
            }

        }
);
        viewRunaway.bChecked = com.maddox.il2.builder.Plugin.builder.conf.bViewRunaway;
        com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem("-", null);
        viewName = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("showName"), com.maddox.il2.builder.Plugin.i18n("TIPshowName")) {

            public void execute()
            {
                bChecked = com.maddox.il2.builder.Plugin.builder.conf.bShowName = !com.maddox.il2.builder.Plugin.builder.conf.bShowName;
            }

        }
);
        viewName.bChecked = com.maddox.il2.builder.Plugin.builder.conf.bShowName;
        viewTime = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("showTime"), com.maddox.il2.builder.Plugin.i18n("TIPshowTime")) {

            public void execute()
            {
                bChecked = com.maddox.il2.builder.Plugin.builder.conf.bShowTime = !com.maddox.il2.builder.Plugin.builder.conf.bShowTime;
            }

        }
);
        viewTime.bChecked = com.maddox.il2.builder.Plugin.builder.conf.bShowTime;
        viewArmy = new com.maddox.il2.builder.GWindowMenuItemArmy[com.maddox.il2.builder.Builder.armyAmount()];
        for(int i = 0; i < com.maddox.il2.builder.Builder.armyAmount(); i++)
        {
            viewArmy[i] = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(new com.maddox.il2.builder.GWindowMenuItemArmy(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("showArmy") + " " + com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(i)), com.maddox.il2.builder.Plugin.i18n("TIPshowArmy") + " " + com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(i)), i) {

                public void execute()
                {
                    bChecked = com.maddox.il2.builder.Plugin.builder.conf.bShowArmy[army] = !com.maddox.il2.builder.Plugin.builder.conf.bShowArmy[army];
                    com.maddox.il2.builder.PlMission.checkShowCurrentArmy();
                }

            }
);
            viewArmy[i].bChecked = com.maddox.il2.builder.Plugin.builder.conf.bShowArmy[i];
        }

        com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem("-", null);
        com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("&ShowAll"), com.maddox.il2.builder.Plugin.i18n("TIPShowAll")) {

            public void execute()
            {
                _viewTypeAll(true);
            }

        }
);
        com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("&HideAll"), com.maddox.il2.builder.Plugin.i18n("TIPHideAll")) {

            public void execute()
            {
                _viewTypeAll(false);
            }

        }
);
        com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(1, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, com.maddox.il2.builder.Plugin.i18n("Load"), com.maddox.il2.builder.Plugin.i18n("TIPLoad")) {

            public void execute()
            {
                doDlgLoadMission();
            }

        }
);
        com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(2, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, com.maddox.il2.builder.Plugin.i18n("Save"), com.maddox.il2.builder.Plugin.i18n("TIPSaveAs")) {

            public void execute()
            {
                if(missionFileName != null)
                {
                    save("missions/" + missionFileName);
                } else
                {
                    com.maddox.gwindow.GWindowFileSaveAs gwindowfilesaveas = new com.maddox.gwindow.GWindowFileSaveAs(root, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
                        new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                            "*.mis"
                        })
                    }) {

                        public void result(java.lang.String s)
                        {
                            if(s != null)
                            {
                                s = checkMisExtension(s);
                                missionFileName = s;
                                ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                                lastOpenFile = s;
                                save("missions/" + s);
                            }
                        }

                    }
;
                    gwindowfilesaveas.exec = new DlgFileConfirmSave();
                    if(lastOpenFile != null)
                        gwindowfilesaveas.setSelectFile(lastOpenFile);
                }
            }


        }
);
        com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(3, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, com.maddox.il2.builder.Plugin.i18n("SaveAs"), com.maddox.il2.builder.Plugin.i18n("TIPSaveAs")) {

            public void execute()
            {
                com.maddox.gwindow.GWindowFileSaveAs gwindowfilesaveas = new com.maddox.gwindow.GWindowFileSaveAs(root, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
                    new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                        "*.mis"
                    })
                }) {

                    public void result(java.lang.String s)
                    {
                        if(s != null)
                        {
                            s = checkMisExtension(s);
                            missionFileName = s;
                            ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                            lastOpenFile = s;
                            save("missions/" + s);
                        }
                    }

                }
;
                gwindowfilesaveas.exec = new DlgFileConfirmSave();
                if(lastOpenFile != null)
                    gwindowfilesaveas.setSelectFile(lastOpenFile);
            }


        }
);
        com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addItem(4, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mFile.subMenu, com.maddox.il2.builder.Plugin.i18n("Play"), com.maddox.il2.builder.Plugin.i18n("TIPPlay")) {

            public void execute()
            {
                if(!com.maddox.il2.builder.Plugin.builder.isLoadedLandscape())
                    return;
                if(com.maddox.il2.builder.PlMission.isChanged() || missionFileName == null)
                {
                    if(missionFileName != null)
                    {
                        if(save("missions/" + missionFileName))
                            playMission();
                    } else
                    {
                        com.maddox.gwindow.GWindowFileSaveAs gwindowfilesaveas = new com.maddox.gwindow.GWindowFileSaveAs(root, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
                            new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                                "*.mis"
                            })
                        }) {

                            public void result(java.lang.String s)
                            {
                                if(s != null)
                                {
                                    s = checkMisExtension(s);
                                    missionFileName = s;
                                    ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                                    lastOpenFile = s;
                                    if(save("missions/" + s))
                                        playMission();
                                }
                            }

                        }
;
                        gwindowfilesaveas.exec = new DlgFileConfirmSave();
                        if(lastOpenFile != null)
                            gwindowfilesaveas.setSelectFile(lastOpenFile);
                    }
                } else
                {
                    playMission();
                }
            }


        }
);
        com.maddox.il2.builder.Plugin.builder.mFile.subMenu.bNotify = true;
        com.maddox.il2.builder.Plugin.builder.mFile.subMenu.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int j, int k)
            {
                if(j != 13)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.Plugin.builder.mFile.subMenu.getItem(2).bEnable = com.maddox.il2.builder.Plugin.builder.isLoadedLandscape();
                    com.maddox.il2.builder.Plugin.builder.mFile.subMenu.getItem(3).bEnable = com.maddox.il2.builder.Plugin.builder.isLoadedLandscape();
                    com.maddox.il2.builder.Plugin.builder.mFile.subMenu.getItem(4).bEnable = com.maddox.il2.builder.Plugin.builder.isLoadedLandscape();
                    return false;
                }
            }

        }
);
        mConditions = com.maddox.il2.builder.Plugin.builder.mEdit.subMenu.addItem(0, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mEdit.subMenu, com.maddox.il2.builder.Plugin.i18n("&Conditions"), com.maddox.il2.builder.Plugin.i18n("TIPConditions")) {

            public void execute()
            {
                if(wConditions.isVisible())
                    wConditions.hideWindow();
                else
                    wConditions.showWindow();
            }

        }
);
        com.maddox.il2.builder.Plugin.builder.mEdit.subMenu.addItem(1, "-", null);
        wConditions = new WConditions();
        wConditions.update();
    }

    private void doLoadMission(java.lang.String s)
    {
        _loadFileName = s;
        _loadMessageBox = new GWindowMessageBox(com.maddox.il2.builder.Plugin.builder.clientWindow.root, 20F, true, com.maddox.il2.builder.Plugin.i18n("StandBy"), com.maddox.il2.builder.Plugin.i18n("LoadingMission"), 4, 0.0F);
        new com.maddox.rts.MsgAction(72, 0.0D) {

            public void doAction()
            {
                load(_loadFileName);
                _loadMessageBox.close(false);
            }

        }
;
    }

    private void playMission()
    {
        com.maddox.il2.game.Main.cur().currentMissionFile = new SectFile("missions/" + missionFileName, 0);
        bReload = true;
        com.maddox.il2.game.Main.stateStack().push(4);
    }

    private void doDlgLoadMission()
    {
        if(!com.maddox.il2.builder.PlMission.isChanged())
        {
            _doDlgLoadMission();
            return;
        } else
        {
            new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.builder.Plugin.builder.clientWindow.root, 20F, true, com.maddox.il2.builder.Plugin.i18n("LoadMission"), com.maddox.il2.builder.Plugin.i18n("ConfirmExitMsg"), 1, 0.0F) {

                public void result(int i)
                {
                    if(i == 3)
                    {
                        if(missionFileName != null)
                        {
                            save("missions/" + missionFileName);
                            _doDlgLoadMission();
                        } else
                        {
                            com.maddox.gwindow.GWindowFileSaveAs gwindowfilesaveas = new com.maddox.gwindow.GWindowFileSaveAs(root, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
                                new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                                    "*.mis"
                                })
                            }) {

                                public void result(java.lang.String s)
                                {
                                    if(s != null)
                                    {
                                        s = checkMisExtension(s);
                                        missionFileName = s;
                                        ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                                        lastOpenFile = s;
                                        save("missions/" + s);
                                    }
                                    _doDlgLoadMission();
                                }

                            }
;
                            gwindowfilesaveas.exec = new DlgFileConfirmSave();
                            if(lastOpenFile != null)
                                gwindowfilesaveas.setSelectFile(lastOpenFile);
                        }
                    } else
                    {
                        _doDlgLoadMission();
                    }
                }


            }
;
            return;
        }
    }

    private void _doDlgLoadMission()
    {
        com.maddox.gwindow.GWindowFileOpen gwindowfileopen = new com.maddox.gwindow.GWindowFileOpen(com.maddox.il2.builder.Plugin.builder.clientWindow.root, true, com.maddox.il2.builder.Plugin.i18n("LoadMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
            new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                "*.mis"
            })
        }) {

            public void result(java.lang.String s)
            {
                if(s != null)
                {
                    missionFileName = s;
                    ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                    lastOpenFile = s;
                    doLoadMission("missions/" + s);
                }
            }

        }
;
        if(lastOpenFile != null)
            gwindowfileopen.setSelectFile(lastOpenFile);
    }

    public boolean exitBuilder()
    {
        if(!com.maddox.il2.builder.PlMission.isChanged())
        {
            return true;
        } else
        {
            new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.builder.Plugin.builder.clientWindow.root, 20F, true, com.maddox.il2.builder.Plugin.i18n("ConfirmExit"), com.maddox.il2.builder.Plugin.i18n("ConfirmExitMsg"), 1, 0.0F) {

                public void result(int i)
                {
                    if(i == 3)
                    {
                        if(missionFileName != null)
                        {
                            save("missions/" + missionFileName);
                            com.maddox.il2.builder.Plugin.builder.doMenu_FileExit();
                        } else
                        {
                            com.maddox.gwindow.GWindowFileSaveAs gwindowfilesaveas = new com.maddox.gwindow.GWindowFileSaveAs(root, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
                                new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                                    "*.mis"
                                })
                            }) {

                                public void result(java.lang.String s)
                                {
                                    if(s != null)
                                    {
                                        s = checkMisExtension(s);
                                        missionFileName = s;
                                        ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                                        lastOpenFile = s;
                                        save("missions/" + s);
                                    }
                                    bChanged = false;
                                    com.maddox.il2.builder.Plugin.builder.doMenu_FileExit();
                                }

                            }
;
                            gwindowfilesaveas.exec = new DlgFileConfirmSave();
                            if(lastOpenFile != null)
                                gwindowfilesaveas.setSelectFile(lastOpenFile);
                        }
                    } else
                    {
                        bChanged = false;
                        com.maddox.il2.builder.Plugin.builder.doMenu_FileExit();
                    }
                }


            }
;
            return false;
        }
    }

    public void loadNewMap()
    {
        if(!bChanged)
        {
            missionFileName = null;
            ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
            ((com.maddox.il2.builder.PlMapLoad)com.maddox.il2.builder.Plugin.getPlugin("MapLoad")).guiMapLoad();
            return;
        } else
        {
            new com.maddox.gwindow.GWindowMessageBox(com.maddox.il2.builder.Plugin.builder.clientWindow.root, 20F, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), com.maddox.il2.builder.Plugin.i18n("ConfirmExitMsg"), 1, 0.0F) {

                public void result(int i)
                {
                    if(i == 3)
                    {
                        if(missionFileName != null)
                        {
                            save("missions/" + missionFileName);
                            ((com.maddox.il2.builder.PlMapLoad)com.maddox.il2.builder.Plugin.getPlugin("MapLoad")).guiMapLoad();
                        } else
                        {
                            com.maddox.gwindow.GWindowFileSaveAs gwindowfilesaveas = new com.maddox.gwindow.GWindowFileSaveAs(root, true, com.maddox.il2.builder.Plugin.i18n("SaveMission"), "missions", new com.maddox.gwindow.GFileFilter[] {
                                new GFileFilterName(com.maddox.il2.builder.Plugin.i18n("MissionFiles"), new java.lang.String[] {
                                    "*.mis"
                                })
                            }) {

                                public void result(java.lang.String s)
                                {
                                    if(s != null)
                                    {
                                        s = checkMisExtension(s);
                                        missionFileName = s;
                                        ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                                        lastOpenFile = s;
                                        save("missions/" + s);
                                    }
                                    bChanged = false;
                                    ((com.maddox.il2.builder.PlMapLoad)com.maddox.il2.builder.Plugin.getPlugin("MapLoad")).guiMapLoad();
                                }

                            }
;
                            gwindowfilesaveas.exec = new DlgFileConfirmSave();
                            if(lastOpenFile != null)
                                gwindowfilesaveas.setSelectFile(lastOpenFile);
                        }
                    } else
                    {
                        bChanged = false;
                        ((com.maddox.il2.builder.PlMapLoad)com.maddox.il2.builder.Plugin.getPlugin("MapLoad")).guiMapLoad();
                    }
                    missionFileName = null;
                    ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
                }


            }
;
            return;
        }
    }

    public void freeResources()
    {
        if(wConditions.isVisible())
            wConditions.hideWindow();
        if(!bReload)
        {
            missionFileName = null;
            ((com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(missionFileName);
        }
    }

    public PlMission()
    {
        missionArmy = 1;
        cloudType = 0;
        cloudHeight = 1000F;
        bChanged = false;
        bReload = false;
        cur = this;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    protected static com.maddox.il2.builder.PlMission cur;
    protected int missionArmy;
    private int cloudType;
    private float cloudHeight;
    private boolean bChanged;
    private java.lang.String missionFileName;
    private boolean bReload;
    com.maddox.il2.builder.PlMapLoad pluginMapLoad;
    com.maddox.il2.builder.WConditions wConditions;
    com.maddox.gwindow.GWindowMenuItem mConditions;
    com.maddox.gwindow.GWindowMenuItem viewBridge;
    com.maddox.gwindow.GWindowMenuItem viewRunaway;
    com.maddox.gwindow.GWindowMenuItem viewName;
    com.maddox.gwindow.GWindowMenuItem viewTime;
    com.maddox.gwindow.GWindowMenuItem viewArmy[];
    private java.lang.String lastOpenFile;
    private com.maddox.gwindow.GWindowMessageBox _loadMessageBox;
    private java.lang.String _loadFileName;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMission.class, "name", "Mission");
    }
















}
