// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMisBorn.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowBoxSeparate;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.Army;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.ZutiAircraft;
import com.maddox.rts.LDRres;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, ActorBorn, Zuti_WAircraftLoadout, PlMission, 
//            Zuti_WManageAircrafts, Zuti_WHomeBaseCountries, Builder, BldConfig, 
//            WSelect

public class PlMisBorn extends com.maddox.il2.builder.Plugin
{
    class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return lst == null ? 0 : lst.size();
        }

        public java.lang.Object getValueAt(int i, int j)
        {
            if(lst == null)
                return null;
            if(i < 0 || i >= lst.size())
            {
                return null;
            } else
            {
                java.lang.String s = (java.lang.String)lst.get(i);
                return com.maddox.il2.game.I18N.plane(s);
            }
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
        }

        public java.util.ArrayList lst;
        public boolean zutiShowAcWeaponsWindow;

        public Table(com.maddox.gwindow.GWindow gwindow, java.lang.String s, float f, float f1, float f2, float f3)
        {
            super(gwindow, f, f1, f2, f3);
            lst = new ArrayList();
            zutiShowAcWeaponsWindow = false;
            bColumnsSizable = false;
            addColumn(s, null);
            vSB.scroll = rowHeight(0);
            resized();
        }
    }

    static class Item
    {

        public java.lang.String name;

        public Item(java.lang.String s)
        {
            name = s;
        }
    }


    public PlMisBorn()
    {
        allActors = new ArrayList();
        p2d = new Point2d();
        p2dt = new Point2d();
        p3d = new Point3d();
        _actorInfo = new java.lang.String[1];
    }

    public void renderMap2DAfter()
    {
        if(builder.isFreeView())
            return;
        if(!viewType.bChecked)
            return;
        com.maddox.il2.engine.Actor actor = builder.selectedActor();
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)allActors.get(j);
            if(!builder.project2d(actorborn.pos.getAbsPoint(), p2d))
                continue;
            int k = com.maddox.il2.ai.Army.color(actorborn.getArmy());
            if(actorborn == actor)
                k = com.maddox.il2.builder.Builder.colorSelected();
            com.maddox.il2.engine.IconDraw.setColor(k);
            com.maddox.il2.engine.IconDraw.render(actorborn, p2d.x, p2d.y);
            actorborn.pos.getAbs(p3d);
            p3d.x += actorborn.r;
            if(!builder.project2d(p3d, p2dt))
                continue;
            double d = p2dt.x - p2d.x;
            if(d > (double)(builder.conf.iconSize / 3))
                drawCircle(p2d.x, p2d.y, d, k);
        }

    }

    private void drawCircle(double d, double d1, double d2, int i)
    {
        int j = 48;
        double d3 = 6.2831853071795862D / (double)j;
        double d4 = 0.0D;
        for(int k = 0; k < j; k++)
        {
            _circleXYZ[k * 3 + 0] = (float)(d + d2 * java.lang.Math.cos(d4));
            _circleXYZ[k * 3 + 1] = (float)(d1 + d2 * java.lang.Math.sin(d4));
            _circleXYZ[k * 3 + 2] = 0.0F;
            d4 += d3;
        }

        com.maddox.il2.engine.Render.drawBeginLines(-1);
        com.maddox.il2.engine.Render.drawLines(_circleXYZ, j, 1.0F, i, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE, 4);
        com.maddox.il2.engine.Render.drawEnd();
    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("MDS");
        if(i < 0)
            i = sectfile.sectionAdd("MDS");
        int j = allActors.size();
        if(j == 0)
            return true;
        int k = countAllAircraft();
        int l = sectfile.sectionAdd("BornPlace");
        for(int i1 = 0; i1 < j; i1++)
        {
            com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)allActors.get(i1);
            sectfile.lineAdd(l, "" + actorborn.getArmy() + " " + actorborn.r + " " + (int)actorborn.pos.getAbsPoint().x + " " + (int)actorborn.pos.getAbsPoint().y + " " + (actorborn.bParachute ? "1" : "0") + " " + actorborn.zutiSpawnHeight + " " + actorborn.zutiSpawnSpeed + " " + actorborn.zutiSpawnOrient + " " + actorborn.zutiMaxBasePilots + " " + actorborn.zutiRadarHeight_MIN + " " + actorborn.zutiRadarHeight_MAX + " " + actorborn.zutiRadarRange + " " + BoolToInt(actorborn.zutiAirspawnOnly) + " " + BoolToInt(actorborn.zutiEnablePlaneLimits) + " " + BoolToInt(actorborn.zutiDecreasingNumberOfPlanes) + " " + BoolToInt(actorborn.zutiDisableSpawning) + " " + BoolToInt(actorborn.zutiEnableFriction) + " " + actorborn.zutiFriction + " " + BoolToInt(actorborn.zutiIncludeStaticPlanes) + " " + BoolToInt(actorborn.zutiStaticPositionOnly) + " " + BoolToInt(actorborn.zutiAirspawnIfCarrierFull));
            java.util.ArrayList arraylist = null;
            if(actorborn.airNames != null && actorborn.zutiAcLoadouts != null && actorborn.zutiAcLoadouts.modifiedAircrafts != null)
                arraylist = zutiSyncLists(actorborn.airNames, actorborn.zutiAcLoadouts.modifiedAircrafts);
            else
            if(actorborn.airNames != null && (actorborn.zutiAcLoadouts == null || actorborn.zutiAcLoadouts.modifiedAircrafts != null) && actorborn.airNames.size() != ZUTI_ALL_AC_LIST_SIZE)
            {
                actorborn.zutiAcLoadouts = zutiCreateAndLoadLoadoutsObject(actorborn);
                arraylist = zutiSyncLists(actorborn.airNames, actorborn.zutiAcLoadouts.modifiedAircrafts);
            }
            if(arraylist != null)
            {
                int j1 = sectfile.sectionAdd("BornPlace" + i1);
                for(int k1 = 0; k1 < arraylist.size(); k1++)
                {
                    com.maddox.il2.game.ZutiAircraft zutiaircraft = (com.maddox.il2.game.ZutiAircraft)arraylist.get(k1);
                    if(actorborn.zutiEnablePlaneLimits)
                        sectfile.lineAdd(j1, zutiaircraft.getMissionLine(actorborn.zutiDecreasingNumberOfPlanes));
                    else
                        sectfile.lineAdd(j1, zutiaircraft.getMissionLineNoLoadouts());
                }

            }
            zutiSaveBornPlaceCountries(actorborn, sectfile, i1);
        }

        return true;
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("BornPlace");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            com.maddox.JGP.Point3d point3d = new Point3d();
            for(int k = 0; k < j; k++)
            {
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
                int l = numbertokenizer.next(0, 0, com.maddox.il2.ai.Army.amountNet() - 1);
                int i1 = numbertokenizer.next(1000, 500, 10000);
                point3d.x = numbertokenizer.next(0);
                point3d.y = numbertokenizer.next(0);
                boolean flag = numbertokenizer.next(1) == 1;
                com.maddox.il2.builder.ActorBorn actorborn = insert(point3d, false);
                try
                {
                    actorborn.zutiSpawnHeight = numbertokenizer.next(1000, 0, 10000);
                    actorborn.zutiSpawnSpeed = numbertokenizer.next(200, 0, 500);
                    actorborn.zutiSpawnOrient = numbertokenizer.next(0, 0, 360);
                    actorborn.zutiMaxBasePilots = numbertokenizer.next(0, 0, 0x1869f);
                    actorborn.zutiRadarHeight_MIN = numbertokenizer.next(0, 0, 0x1869f);
                    actorborn.zutiRadarHeight_MAX = numbertokenizer.next(5000, 0, 0x1869f);
                    actorborn.zutiRadarRange = numbertokenizer.next(50, 1, 0x1869f);
                    if(numbertokenizer.next(0, 0, 1) == 1)
                        actorborn.zutiAirspawnOnly = true;
                    if(numbertokenizer.next(0, 0, 1) == 1)
                        actorborn.zutiEnablePlaneLimits = true;
                    if(numbertokenizer.next(0, 0, 1) == 1)
                        actorborn.zutiDecreasingNumberOfPlanes = true;
                    if(numbertokenizer.next(0, 0, 1) == 1)
                        actorborn.zutiDisableSpawning = true;
                    if(numbertokenizer.next(0, 0, 1) == 1)
                        actorborn.zutiEnableFriction = true;
                    actorborn.zutiFriction = numbertokenizer.next(3.7999999999999998D, 0.0D, 10D);
                    if(numbertokenizer.next(0, 0, 1) == 1)
                        actorborn.zutiIncludeStaticPlanes = true;
                    if(numbertokenizer.next(0, 0, 1) == 1)
                        actorborn.zutiStaticPositionOnly = true;
                    if(numbertokenizer.next(0, 0, 1) == 1)
                        actorborn.zutiAirspawnIfCarrierFull = true;
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println("PlMisBorn: no air spawn entries defined for HomeBase at " + point3d);
                }
                if(actorborn == null)
                    continue;
                actorborn.airNames.clear();
                if(actorborn == null)
                    continue;
                actorborn.setArmy(l);
                actorborn.r = i1;
                actorborn.bParachute = flag;
                int j1 = sectfile.sectionIndex("BornPlace" + k);
                if(j1 >= 0)
                {
                    int k1 = sectfile.vars(j1);
                    for(int l1 = 0; l1 < k1; l1++)
                    {
                        java.lang.String s = sectfile.line(j1, l1);
                        java.util.StringTokenizer stringtokenizer = new StringTokenizer(s);
                        com.maddox.il2.game.ZutiAircraft zutiaircraft = new ZutiAircraft();
                        java.lang.String s1 = "";
                        for(int i2 = 0; stringtokenizer.hasMoreTokens(); i2++)
                            switch(i2)
                            {
                            case 0: // '\0'
                                zutiaircraft.setAcName(stringtokenizer.nextToken());
                                break;

                            case 1: // '\001'
                                zutiaircraft.setMaxAllowed(java.lang.Integer.valueOf(stringtokenizer.nextToken()).intValue());
                                break;

                            default:
                                s1 = s1 + " " + stringtokenizer.nextToken();
                                break;
                            }

                        zutiaircraft.setLoadedWeapons(s1, true);
                        java.lang.String s2 = zutiaircraft.getAcName();
                        if(s2 == null)
                            continue;
                        s2 = s2.intern();
                        java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(s2, "airClass", null);
                        if(class1 == null || !com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                            continue;
                        if(zutiaircraft.wasModified())
                        {
                            if(actorborn.zutiAcLoadouts == null)
                            {
                                actorborn.zutiAcLoadouts = new Zuti_WAircraftLoadout();
                                actorborn.zutiAcLoadouts.modifiedAircrafts = new ArrayList();
                            } else
                            if(actorborn.zutiAcLoadouts.modifiedAircrafts == null)
                                actorborn.zutiAcLoadouts.modifiedAircrafts = new ArrayList();
                            actorborn.zutiAcLoadouts.modifiedAircrafts.add(zutiaircraft);
                        }
                        actorborn.airNames.add(s2);
                    }

                }
                if(actorborn.airNames.size() == 0)
                    addAllAircraft(actorborn.airNames);
                zutiLoadBornPlaceCountries(actorborn, sectfile);
            }

        }
    }

    public void deleteAll()
    {
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)allActors.get(j);
            actorborn.destroy();
        }

        allActors.clear();
    }

    public void delete(com.maddox.il2.engine.Actor actor)
    {
        allActors.remove(actor);
        actor.destroy();
    }

    private boolean findAirport(com.maddox.JGP.Point3d point3d)
    {
        point3d.z = 0.0D;
        com.maddox.il2.ai.Airport airport = com.maddox.il2.ai.Airport.nearest(point3d, -1, 7);
        if(airport == null)
            return false;
        point3d.set(airport.pos.getAbsPoint());
        for(int i = 0; i < allActors.size(); i++)
        {
            com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)allActors.get(i);
            com.maddox.JGP.Point3d point3d1 = actorborn.pos.getAbsPoint();
            double d = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y);
            if(d < 100D)
                return false;
        }

        return true;
    }

    private com.maddox.il2.builder.ActorBorn insert(com.maddox.JGP.Point3d point3d, boolean flag)
    {
        if(allActors.size() >= 255)
            return null;
        if(!zutiFindAirport(point3d))
            return null;
        com.maddox.il2.builder.ActorBorn actorborn;
        actorborn = new ActorBorn(point3d);
        addAllAircraft(actorborn.airNames);
        builder.align(actorborn);
        com.maddox.rts.Property.set(actorborn, "builderSpawn", "");
        com.maddox.rts.Property.set(actorborn, "builderPlugin", this);
        allActors.add(actorborn);
        if(flag)
            builder.setSelected(actorborn);
        com.maddox.il2.builder.PlMission.setChanged();
        return actorborn;
        java.lang.Exception exception;
        exception;
        return null;
    }

    public void insert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        int i = builder.wSelect.comboBox1.getSelected();
        int j = builder.wSelect.comboBox2.getSelected();
        if(i != startComboBox1)
            return;
        if(j < 0 || j >= item.length)
        {
            return;
        } else
        {
            insert(loc.getPoint(), flag);
            return;
        }
    }

    public void changeType()
    {
        builder.setSelected(null);
    }

    private void updateView()
    {
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)allActors.get(j);
            actorborn.drawing(viewType.bChecked);
        }

    }

    public void configure()
    {
        if(com.maddox.il2.builder.PlMisBorn.getPlugin("Mission") == null)
        {
            throw new RuntimeException("PlMisBorn: plugin 'Mission' not found");
        } else
        {
            pluginMission = (com.maddox.il2.builder.PlMission)com.maddox.il2.builder.PlMisBorn.getPlugin("Mission");
            return;
        }
    }

    private void fillComboBox2(int i)
    {
        if(i != startComboBox1)
            return;
        if(builder.wSelect.curFilledType != i)
        {
            builder.wSelect.curFilledType = i;
            builder.wSelect.comboBox2.clear(false);
            for(int j = 0; j < item.length; j++)
                builder.wSelect.comboBox2.add(item[j].name);

            builder.wSelect.comboBox1.setSelected(i, true, false);
        }
        builder.wSelect.comboBox2.setSelected(0, true, false);
        builder.wSelect.setMesh(null, true);
    }

    public void viewTypeAll(boolean flag)
    {
        viewType.bChecked = flag;
        updateView();
    }

    public java.lang.String[] actorInfo(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)actor;
        _actorInfo[0] = com.maddox.il2.builder.PlMisBorn.i18n("BornPlace");
        return _actorInfo;
    }

    public void syncSelector()
    {
        com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)builder.selectedActor();
        fillComboBox2(startComboBox1);
        builder.wSelect.comboBox2.setSelected(0, true, false);
        builder.wSelect.tabsClient.addTab(1, tabTarget);
        builder.wSelect.tabsClient.addTab(2, tabAircraft);
        com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.addTab(3, tabSpawn);
        com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.addTab(4, tabFow);
        fillTabAircraft();
        zutiResetValues();
        wR.setPos((actorborn.r - 500) / 50, false);
        wArmy.setSelected(actorborn.getArmy(), true, false);
    }

    public void updateSelector()
    {
        fillTabAircraft();
    }

    public void createGUI()
    {
        com.maddox.il2.builder.Plugin.builder.wSelect.metricWin = new GRegion(2.0F, 2.0F, 40F, 36F);
        startComboBox1 = builder.wSelect.comboBox1.size();
        builder.wSelect.comboBox1.add(com.maddox.il2.builder.PlMisBorn.i18n("BornPlace"));
        builder.wSelect.comboBox1.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int j, int k)
            {
                int l = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
                if(l >= 0 && j == 2)
                    fillComboBox2(l);
                return false;
            }

        }
);
        int i;
        for(i = builder.mDisplayFilter.subMenu.size() - 1; i >= 0 && pluginMission.viewBridge != builder.mDisplayFilter.subMenu.getItem(i); i--);
        if(--i >= 0)
        {
            viewType = builder.mDisplayFilter.subMenu.addItem(i, new com.maddox.gwindow.GWindowMenuItem(builder.mDisplayFilter.subMenu, com.maddox.il2.builder.PlMisBorn.i18n("showBornPlaces"), null) {

                public void execute()
                {
                    bChecked = !bChecked;
                    updateView();
                }

            }
);
            viewType.bChecked = true;
        }
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)builder.wSelect.tabsClient.create(new GWindowDialogClient());
        tabTarget = builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.PlMisBorn.i18n("BornPlaceActor"), gwindowdialogclient);
        gwindowdialogclient.addLabel(lProperties = new GWindowLabel(gwindowdialogclient, 3F, 0.5F, 6F, 1.6F, com.maddox.il2.builder.Plugin.i18n("mds.properties"), null));
        bSeparate_Properties = new GWindowBoxSeparate(gwindowdialogclient, 1.0F, 1.0F, 37F, 18F);
        bSeparate_Properties.exclude = lProperties;
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 2.0F, 6F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.properties.radius"), null));
        gwindowdialogclient.addLabel(lRadius = new GWindowLabel(gwindowdialogclient, 17F, 2.0F, 6F, 1.3F, " [500m]", null));
        gwindowdialogclient.addControl(wR = new com.maddox.gwindow.GWindowHSliderInt(gwindowdialogclient, 0, 190, 20, 7F, 2.0F, 10F) {

            public void afterCreated()
            {
                super.afterCreated();
                bSlidingNotify = true;
            }

            public boolean notify(int j, int k)
            {
                com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                if(actorborn != null)
                    lRadius.cap = new GCaption(" [" + actorborn.r + "m]");
                zutiCountriesWindowRefresh(actorborn);
                if(j != 2)
                {
                    return false;
                } else
                {
                    actorborn.r = pos() * 50 + 500;
                    lRadius.cap = new GCaption(" [" + actorborn.r + "m]");
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 4F, 6F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.properties.army"), null));
        gwindowdialogclient.addControl(wArmy = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 8F, 4F, 10F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                for(int j = 0; j < com.maddox.il2.ai.Army.amountNet(); j++)
                    add(com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(j)));

            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.setArmy(getSelected());
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 6F, 8F, 1.3F, com.maddox.il2.builder.PlMisBorn.i18n("Parachute"), null));
        gwindowdialogclient.addControl(wParachute = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 25F, 6F, null) {

            public void preRender()
            {
                super.preRender();
                com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                setChecked(actorborn.bParachute, false);
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.bParachute = isChecked();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 8F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.properties.friction1"), null));
        gwindowdialogclient.addControl(wZutiEnableFriction = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 25F, 8F, null) {

            public void preRender()
            {
                super.preRender();
                com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                setChecked(actorborn.zutiEnableFriction, false);
                wFriction.setEnable(actorborn.zutiEnableFriction);
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiEnableFriction = isChecked();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 28F, 8F, 6F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.properties.friction2"), null));
        gwindowdialogclient.addControl(wFriction = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 34F, 8F, 2.5F, 1.3F, "Tralala") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bNumericFloat = true;
                bDelayedNotify = true;
            }

            public void preRender()
            {
                if(getValue().trim().length() > 0)
                {
                    return;
                } else
                {
                    super.preRender();
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    setValue((new Double(actorborn.zutiFriction)).toString(), false);
                    return;
                }
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiFriction = java.lang.Double.parseDouble(getValue());
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 10F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.properties.spawn"), null));
        gwindowdialogclient.addControl(wZutiDisableSpawning = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 25F, 10F, null) {

            public void preRender()
            {
                super.preRender();
                com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                setChecked(actorborn.zutiDisableSpawning, false);
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiDisableSpawning = isChecked();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 12F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.properties.staticPosition"), null));
        gwindowdialogclient.addControl(wZutiStaticPositionOnly = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 25F, 12F, null) {

            public void preRender()
            {
                super.preRender();
                com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                setChecked(actorborn.zutiStaticPositionOnly, false);
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiStaticPositionOnly = isChecked();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 14F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.spawn.maxPilots"), null));
        gwindowdialogclient.addControl(wMaxPilots = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 25F, 14F, 3F, 1.3F, "Tralala") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public void preRender()
            {
                if(getValue().trim().length() > 0)
                {
                    return;
                } else
                {
                    super.preRender();
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    setValue((new Integer(actorborn.zutiMaxBasePilots)).toString(), false);
                    return;
                }
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiMaxBasePilots = java.lang.Integer.parseInt(getValue());
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addControl(bModifyCountries = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 2.0F, 16F, 10F, 2.0F, com.maddox.il2.builder.Plugin.i18n("mds.properties.countries"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    zutiCountriesWindowShow(actorborn, 0);
                    return true;
                }
            }

        }
);
        gwindowdialogclient.addLabel(lLimitations = new GWindowLabel(gwindowdialogclient, 3F, 19.5F, 9F, 1.6F, com.maddox.il2.builder.Plugin.i18n("mds.limitations"), null));
        bSeparate_Limitations = new GWindowBoxSeparate(gwindowdialogclient, 1.0F, 20F, 37F, 7F);
        bSeparate_Limitations.exclude = lLimitations;
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 21F, 22F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.limitations.enable"), null));
        gwindowdialogclient.addControl(wEnablePlaneLimits = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 25F, 21F, null) {

            public void preRender()
            {
                super.preRender();
                com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                setChecked(actorborn.zutiEnablePlaneLimits, false);
            }

            public boolean notify(int j, int k)
            {
                wDecreasingNumberOfPlanes.setEnable(isChecked());
                wIncludeStaticPlanes.setEnable(isChecked());
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiEnablePlaneLimits = isChecked();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 2.0F, 23F, 23F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.limitations.decAc"), null));
        gwindowdialogclient.addControl(wDecreasingNumberOfPlanes = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 25F, 23F, null) {

            public void preRender()
            {
                super.preRender();
                setEnable(wEnablePlaneLimits.isChecked());
                com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                setChecked(actorborn.zutiDecreasingNumberOfPlanes, false);
            }

            public boolean notify(int j, int k)
            {
                wIncludeStaticPlanes.setEnable(isChecked() && wEnablePlaneLimits.isChecked());
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiDecreasingNumberOfPlanes = isChecked();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 3F, 25F, 22F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.limitations.countStatic"), null));
        gwindowdialogclient.addControl(wIncludeStaticPlanes = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 25F, 25F, null) {

            public void preRender()
            {
                super.preRender();
                setEnable(wDecreasingNumberOfPlanes.isChecked() && wEnablePlaneLimits.isChecked());
                com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                setChecked(actorborn.zutiIncludeStaticPlanes, false);
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiIncludeStaticPlanes = isChecked();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient1 = (com.maddox.gwindow.GWindowDialogClient)builder.wSelect.tabsClient.create(new com.maddox.gwindow.GWindowDialogClient() {

            public void resized()
            {
                super.resized();
                setAircraftSizes(this);
            }

        }
);
        tabAircraft = builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.PlMisBorn.i18n("bplace_aircraft"), gwindowdialogclient1);
        lstAvailable = new Table(gwindowdialogclient1, com.maddox.il2.builder.PlMisBorn.i18n("bplace_planes"), 1.0F, 1.0F, 6F, 10F);
        lstAvailable.zutiShowAcWeaponsWindow = true;
        lstInReserve = new Table(gwindowdialogclient1, com.maddox.il2.builder.PlMisBorn.i18n("bplace_list"), 14F, 1.0F, 6F, 10F);
        gwindowdialogclient1.addControl(bAddAll = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 8F, 1.0F, 5F, 2.0F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_addall"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    lstAvailable.lst.clear();
                    addAllAircraft(lstAvailable.lst);
                    fillTabAircraft();
                    return true;
                }
            }

        }
);
        gwindowdialogclient1.addControl(bAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 8F, 3F, 5F, 2.0F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_add"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                int l = lstInReserve.selectRow;
                if(l < 0 || l >= lstInReserve.lst.size())
                {
                    return true;
                } else
                {
                    lstAvailable.lst.add(lstInReserve.lst.get(l));
                    fillTabAircraft();
                    return true;
                }
            }

        }
);
        gwindowdialogclient1.addControl(bRemAll = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 8F, 6F, 5F, 2.0F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_delall"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    lstAvailable.lst.clear();
                    fillTabAircraft();
                    return true;
                }
            }

        }
);
        gwindowdialogclient1.addControl(bRem = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 8F, 8F, 5F, 2.0F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_del"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                int l = lstAvailable.selectRow;
                if(l < 0 || l >= lstAvailable.lst.size())
                {
                    return true;
                } else
                {
                    lstAvailable.lst.remove(l);
                    fillTabAircraft();
                    return true;
                }
            }

        }
);
        gwindowdialogclient1.addControl(bModifyPlane = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 8F, 12F, 5F, 2.0F, com.maddox.il2.builder.PlMisBorn.i18n("mds.aircraft.modify"), null) {

            public boolean notify(int j, int k)
            {
                com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                bModifyPlane.setEnable(actorborn.zutiEnablePlaneLimits);
                if(j != 2)
                    return false;
                if(actorborn.zutiAcLoadouts == null)
                    actorborn.zutiAcLoadouts = new Zuti_WAircraftLoadout();
                if(!actorborn.zutiAcLoadouts.isVisible())
                {
                    if(lstAvailable.selectRow < 0 || lstAvailable.selectRow >= lstAvailable.lst.size())
                    {
                        return true;
                    } else
                    {
                        actorborn.zutiAcLoadouts.setSelectedAircraft((java.lang.String)lstAvailable.lst.get(lstAvailable.selectRow));
                        actorborn.zutiAcLoadouts.setTitle((java.lang.String)lstAvailable.lst.get(lstAvailable.selectRow));
                        actorborn.zutiAcLoadouts.showWindow();
                        return true;
                    }
                } else
                {
                    return true;
                }
            }

        }
);
        gwindowdialogclient1.addLabel(lSeparate = new GWindowLabel(gwindowdialogclient1, 3F, 12F, 12F, 1.6F, " " + com.maddox.il2.builder.PlMisBorn.i18n("bplace_cats") + " ", null));
        bSeparate = new GWindowBoxSeparate(gwindowdialogclient1, 1.0F, 12.5F, 27F, 8F);
        bSeparate.exclude = lSeparate;
        gwindowdialogclient1.addLabel(lCountry = new GWindowLabel(gwindowdialogclient1, 2.0F, 14F, 7F, 1.6F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_country"), null));
        gwindowdialogclient1.addControl(cCountry = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient1, 9F, 14F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
                java.util.TreeMap treemap = new TreeMap();
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int j = 0; j < arraylist.size(); j++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(j);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                        continue;
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "originCountry", null);
                    if(s1 == null)
                        continue;
                    java.lang.String s3 = null;
                    try
                    {
                        s3 = resourcebundle.getString(s1);
                    }
                    catch(java.lang.Exception exception)
                    {
                        continue;
                    }
                    treemap.put(s3, s1);
                }

                java.lang.String s;
                for(java.util.Iterator iterator = treemap.keySet().iterator(); iterator.hasNext(); add(s))
                {
                    s = (java.lang.String)iterator.next();
                    java.lang.String s2 = (java.lang.String)treemap.get(s);
                    com.maddox.il2.builder.PlMisBorn.lstCountry.add(s2);
                }

                if(com.maddox.il2.builder.PlMisBorn.lstCountry.size() > 0)
                    setSelected(0, true, false);
            }

        }
);
        gwindowdialogclient1.addControl(bCountryAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 17F, 14F, 5F, 1.6F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_add"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.String s = (java.lang.String)com.maddox.il2.builder.PlMisBorn.lstCountry.get(cCountry.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int l = 0; l < arraylist.size(); l++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(l);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass") || !s.equals(com.maddox.rts.Property.stringValue(class1, "originCountry", null)))
                        continue;
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName");
                    if(!lstAvailable.lst.contains(s1))
                        lstAvailable.lst.add(s1);
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient1.addControl(bCountryRem = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 22F, 14F, 5F, 1.6F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_del"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.String s = (java.lang.String)com.maddox.il2.builder.PlMisBorn.lstCountry.get(cCountry.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int l = 0; l < arraylist.size(); l++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(l);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass") || !s.equals(com.maddox.rts.Property.stringValue(class1, "originCountry", null)))
                        continue;
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName");
                    int i1 = lstAvailable.lst.indexOf(s1);
                    if(i1 >= 0)
                        lstAvailable.lst.remove(i1);
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient1.addLabel(lYear = new GWindowLabel(gwindowdialogclient1, 2.0F, 16F, 7F, 1.6F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_year"), null));
        gwindowdialogclient1.addControl(cYear = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient1, 9F, 16F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                java.util.TreeMap treemap = new TreeMap();
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int j = 0; j < arraylist.size(); j++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(j);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                        continue;
                    float f = com.maddox.rts.Property.floatValue(class1, "yearService", 0.0F);
                    if(f != 0.0F)
                        treemap.put("" + (int)f, null);
                }

                java.lang.String s;
                for(java.util.Iterator iterator = treemap.keySet().iterator(); iterator.hasNext(); add(s))
                {
                    s = (java.lang.String)iterator.next();
                    com.maddox.il2.builder.PlMisBorn.lstYear.add(s);
                }

                if(com.maddox.il2.builder.PlMisBorn.lstYear.size() > 0)
                    setSelected(0, true, false);
            }

        }
);
        gwindowdialogclient1.addControl(bYearAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 17F, 16F, 5F, 1.6F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_add"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.String s = (java.lang.String)com.maddox.il2.builder.PlMisBorn.lstYear.get(cYear.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int l = 0; l < arraylist.size(); l++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(l);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass") || !s.equals("" + (int)com.maddox.rts.Property.floatValue(class1, "yearService", 0.0F)))
                        continue;
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName");
                    if(!lstAvailable.lst.contains(s1))
                        lstAvailable.lst.add(s1);
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient1.addControl(bYearRem = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 22F, 16F, 5F, 1.6F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_del"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.String s = (java.lang.String)com.maddox.il2.builder.PlMisBorn.lstYear.get(cYear.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int l = 0; l < arraylist.size(); l++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(l);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass") || !s.equals("" + (int)com.maddox.rts.Property.floatValue(class1, "yearService", 0.0F)))
                        continue;
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName");
                    int i1 = lstAvailable.lst.indexOf(s1);
                    if(i1 >= 0)
                        lstAvailable.lst.remove(i1);
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient1.addLabel(lType = new GWindowLabel(gwindowdialogclient1, 2.0F, 18F, 7F, 1.6F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_category"), null));
        gwindowdialogclient1.addControl(cType = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient1, 9F, 18F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                java.util.TreeMap treemap = new TreeMap();
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int j = 0; j < arraylist.size(); j++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(j);
                    if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                        continue;
                    if((com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeStormovik != null ? com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeStormovik : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeStormovik = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.TypeStormovik"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_sturm"), com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeStormovik != null ? ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeStormovik)) : ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeStormovik = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.TypeStormovik"))));
                    if((com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeFighter != null ? com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeFighter : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeFighter = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.TypeFighter"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_fiter"), com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeFighter != null ? ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeFighter)) : ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeFighter = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.TypeFighter"))));
                    if((com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeBomber != null ? com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeBomber : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeBomber = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.TypeBomber"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_bomber"), com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeBomber != null ? ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeBomber)) : ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeBomber = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.TypeBomber"))));
                    if((com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeScout != null ? com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeScout : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeScout = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.TypeScout"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_recon"), com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeScout != null ? ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeScout)) : ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeScout = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.TypeScout"))));
                    if((com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeDiveBomber != null ? com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeDiveBomber : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeDiveBomber = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.TypeDiveBomber"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_diver"), com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeDiveBomber != null ? ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeDiveBomber)) : ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeDiveBomber = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.TypeDiveBomber"))));
                    if((com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeSailPlane != null ? com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeSailPlane : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeSailPlane = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.TypeSailPlane"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_sailer"), com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeSailPlane != null ? ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeSailPlane)) : ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$TypeSailPlane = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.TypeSailPlane"))));
                    if((com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 != null ? com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.Scheme1"))).isAssignableFrom(class1))
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_single"), com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 != null ? ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1)) : ((java.lang.Object) (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.Scheme1"))));
                    else
                        treemap.put(com.maddox.il2.builder.Plugin.i18n("bplace_multi"), null);
                }

                java.lang.String s;
                for(java.util.Iterator iterator = treemap.keySet().iterator(); iterator.hasNext(); add(s))
                {
                    s = (java.lang.String)iterator.next();
                    java.lang.Class class2 = (java.lang.Class)treemap.get(s);
                    com.maddox.il2.builder.PlMisBorn.lstType.add(class2);
                }

                if(com.maddox.il2.builder.PlMisBorn.lstType.size() > 0)
                    setSelected(0, true, false);
            }

        }
);
        gwindowdialogclient1.addControl(bTypeAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 17F, 18F, 5F, 1.6F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_add"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.Class class1 = (java.lang.Class)com.maddox.il2.builder.PlMisBorn.lstType.get(cType.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int l = 0; l < arraylist.size(); l++)
                {
                    java.lang.Class class2 = (java.lang.Class)arraylist.get(l);
                    if(!com.maddox.rts.Property.containsValue(class2, "cockpitClass") || (class1 != null ? !class1.isAssignableFrom(class2) : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 != null ? com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.Scheme1"))).isAssignableFrom(class2)))
                        continue;
                    java.lang.String s = com.maddox.rts.Property.stringValue(class2, "keyName");
                    if(!lstAvailable.lst.contains(s))
                        lstAvailable.lst.add(s);
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient1.addControl(bTypeRem = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 22F, 18F, 5F, 1.6F, com.maddox.il2.builder.PlMisBorn.i18n("bplace_del"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.Class class1 = (java.lang.Class)com.maddox.il2.builder.PlMisBorn.lstType.get(cType.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int l = 0; l < arraylist.size(); l++)
                {
                    java.lang.Class class2 = (java.lang.Class)arraylist.get(l);
                    if(!com.maddox.rts.Property.containsValue(class2, "cockpitClass") || (class1 != null ? !class1.isAssignableFrom(class2) : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 != null ? com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.Scheme1"))).isAssignableFrom(class2)))
                        continue;
                    java.lang.String s = com.maddox.rts.Property.stringValue(class2, "keyName");
                    int i1 = lstAvailable.lst.indexOf(s);
                    if(i1 >= 0)
                        lstAvailable.lst.remove(i1);
                }

                fillTabAircraft();
                return true;
            }

        }
);
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient2 = (com.maddox.gwindow.GWindowDialogClient)com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
        tabSpawn = com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.Plugin.i18n("mds.tabSpawn"), gwindowdialogclient2);
        gwindowdialogclient2.addLabel(lAirSpawn = new GWindowLabel(gwindowdialogclient2, 3F, 0.5F, 11F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.spawn"), null));
        bSeparate_AirSpawn = new GWindowBoxSeparate(gwindowdialogclient2, 1.0F, 1.0F, 37F, 11F);
        bSeparate_AirSpawn.exclude = lAirSpawn;
        gwindowdialogclient2.addLabel(new GWindowLabel(gwindowdialogclient2, 2.0F, 2.0F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.spawn.height"), null));
        gwindowdialogclient2.addControl(wHeight = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient2, 20F, 2.0F, 5F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public void preRender()
            {
                if(getValue().trim().length() > 0)
                {
                    return;
                } else
                {
                    super.preRender();
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    setValue((new Integer(actorborn.zutiSpawnHeight)).toString(), false);
                    return;
                }
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiSpawnHeight = java.lang.Integer.parseInt(getValue());
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient2.addLabel(new GWindowLabel(gwindowdialogclient2, 2.0F, 4F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.spawn.speed"), null));
        gwindowdialogclient2.addControl(wSpeed = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient2, 20F, 4F, 5F, 1.3F, "Tralala") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public void preRender()
            {
                if(getValue().trim().length() > 0)
                {
                    return;
                } else
                {
                    super.preRender();
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    setValue((new Integer(actorborn.zutiSpawnSpeed)).toString(), false);
                    return;
                }
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiSpawnSpeed = java.lang.Integer.parseInt(getValue());
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient2.addLabel(new GWindowLabel(gwindowdialogclient2, 2.0F, 6F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.spawn.orient"), null));
        gwindowdialogclient2.addControl(wOrient = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient2, 20F, 6F, 5F, 1.3F, "Tralala") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public void preRender()
            {
                if(getValue().trim().length() > 0)
                {
                    return;
                } else
                {
                    super.preRender();
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    setValue((new Integer(actorborn.zutiSpawnOrient)).toString(), false);
                    return;
                }
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiSpawnOrient = java.lang.Integer.parseInt(getValue());
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient2.addLabel(new GWindowLabel(gwindowdialogclient2, 2.0F, 8F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.spawn.spawnIfCarrierFull"), null));
        gwindowdialogclient2.addControl(wAirSpawnIfCarrierFull = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient2, 20F, 8F, null) {

            public void preRender()
            {
                super.preRender();
                com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                setChecked(actorborn.zutiAirspawnIfCarrierFull, false);
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiAirspawnIfCarrierFull = isChecked();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient2.addLabel(new GWindowLabel(gwindowdialogclient2, 2.0F, 10F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.spawn.alwaysAirSpawn"), null));
        gwindowdialogclient2.addControl(wAlwaysAirSpawn = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient2, 20F, 10F, null) {

            public void preRender()
            {
                super.preRender();
                com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                setChecked(actorborn.zutiAirspawnOnly, false);
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiAirspawnOnly = isChecked();
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient3 = (com.maddox.gwindow.GWindowDialogClient)com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
        tabFow = com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.Plugin.i18n("mds.tabFow"), gwindowdialogclient3);
        gwindowdialogclient3.addLabel(lRadar = new GWindowLabel(gwindowdialogclient3, 3F, 0.5F, 13F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar"), null));
        bSeparate_Radar = new GWindowBoxSeparate(gwindowdialogclient3, 1.0F, 1.0F, 37F, 10F);
        bSeparate_Radar.exclude = lRadar;
        gwindowdialogclient3.addLabel(new GWindowLabel(gwindowdialogclient3, 2.0F, 2.0F, 39F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.description1"), null));
        gwindowdialogclient3.addLabel(new GWindowLabel(gwindowdialogclient3, 2.0F, 3F, 39F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.description2"), null));
        gwindowdialogclient3.addLabel(new GWindowLabel(gwindowdialogclient3, 2.0F, 5F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.range"), null));
        gwindowdialogclient3.addControl(wZutiRadar_Range = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient3, 20F, 5F, 5F, 1.3F, "Tralala") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public void preRender()
            {
                if(getValue().trim().length() > 0)
                {
                    return;
                } else
                {
                    super.preRender();
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    setValue((new Integer(actorborn.zutiRadarRange)).toString(), false);
                    return;
                }
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiRadarRange = java.lang.Integer.parseInt(getValue());
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient3.addLabel(new GWindowLabel(gwindowdialogclient3, 2.0F, 7F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.minHeight"), null));
        gwindowdialogclient3.addControl(wZutiRadar_Min = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient3, 20F, 7F, 5F, 1.3F, "Tralala") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public void preRender()
            {
                if(getValue().trim().length() > 0)
                {
                    return;
                } else
                {
                    super.preRender();
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    setValue((new Integer(actorborn.zutiRadarHeight_MIN)).toString(), false);
                    return;
                }
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiRadarHeight_MIN = java.lang.Integer.parseInt(getValue());
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient3.addLabel(new GWindowLabel(gwindowdialogclient3, 2.0F, 9F, 18F, 1.3F, com.maddox.il2.builder.Plugin.i18n("mds.radar.maxHeight"), null));
        gwindowdialogclient3.addControl(wZutiRadar_Max = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient3, 20F, 9F, 5F, 1.3F, "Tralala") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public void preRender()
            {
                if(getValue().trim().length() > 0)
                {
                    return;
                } else
                {
                    super.preRender();
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    setValue((new Integer(actorborn.zutiRadarHeight_MAX)).toString(), false);
                    return;
                }
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.zutiRadarHeight_MAX = java.lang.Integer.parseInt(getValue());
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        zuti_manageAircrafts = new Zuti_WManageAircrafts();
    }

    private void fillTabAircraft()
    {
        com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)builder.selectedActor();
        int i = lstAvailable.selectRow;
        int j = lstInReserve.selectRow;
        lstAvailable.lst = actorborn.airNames;
        lstInReserve.lst.clear();
        java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
        for(int k = 0; k < arraylist.size(); k++)
        {
            java.lang.Class class1 = (java.lang.Class)arraylist.get(k);
            if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                continue;
            java.lang.String s = com.maddox.rts.Property.stringValue(class1, "keyName");
            if(!lstAvailable.lst.contains(s))
                lstInReserve.lst.add(s);
        }

        if(lstAvailable.lst.size() > 0)
        {
            lstAvailable.lst.clear();
            for(int l = 0; l < arraylist.size(); l++)
            {
                java.lang.Class class2 = (java.lang.Class)arraylist.get(l);
                if(!com.maddox.rts.Property.containsValue(class2, "cockpitClass"))
                    continue;
                java.lang.String s1 = com.maddox.rts.Property.stringValue(class2, "keyName");
                if(!lstInReserve.lst.contains(s1))
                    lstAvailable.lst.add(s1);
            }

        }
        if(i >= 0)
            if(lstAvailable.lst.size() > 0)
            {
                if(i >= lstAvailable.lst.size())
                    i = lstAvailable.lst.size() - 1;
            } else
            {
                i = -1;
            }
        lstAvailable.setSelect(i, 0);
        if(j >= 0)
            if(lstInReserve.lst.size() > 0)
            {
                if(j >= lstInReserve.lst.size())
                    j = lstInReserve.lst.size() - 1;
            } else
            {
                j = -1;
            }
        lstInReserve.setSelect(j, 0);
        lstAvailable.resized();
        lstInReserve.resized();
    }

    private void addAllAircraft(java.util.ArrayList arraylist)
    {
        java.util.ArrayList arraylist1 = com.maddox.il2.game.Main.cur().airClasses;
        for(int i = 0; i < arraylist1.size(); i++)
        {
            java.lang.Class class1 = (java.lang.Class)arraylist1.get(i);
            if(!com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                continue;
            java.lang.String s = com.maddox.rts.Property.stringValue(class1, "keyName");
            if(!arraylist.contains(s))
                arraylist.add(s);
        }

        if(ZUTI_ALL_AC_LIST_SIZE < 0)
            ZUTI_ALL_AC_LIST_SIZE = arraylist.size();
    }

    private int countAllAircraft()
    {
        int i = 0;
        java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
        for(int j = 0; j < arraylist.size(); j++)
        {
            java.lang.Class class1 = (java.lang.Class)arraylist.get(j);
            if(com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                i++;
        }

        return i;
    }

    private void setAircraftSizes(com.maddox.gwindow.GWindow gwindow)
    {
        float f = gwindow.win.dx;
        float f1 = gwindow.win.dy;
        com.maddox.gwindow.GFont gfont = gwindow.root.textFonts[0];
        float f2 = gwindow.lAF().metric();
        com.maddox.gwindow.GSize gsize = new GSize();
        gfont.size(com.maddox.il2.builder.PlMisBorn.i18n("bplace_addall"), gsize);
        float f3 = gsize.dx;
        gfont.size(com.maddox.il2.builder.PlMisBorn.i18n("bplace_add"), gsize);
        float f4 = gsize.dx;
        gfont.size(com.maddox.il2.builder.PlMisBorn.i18n("bplace_delall"), gsize);
        float f5 = gsize.dx;
        gfont.size(com.maddox.il2.builder.PlMisBorn.i18n("bplace_del"), gsize);
        float f6 = gsize.dx;
        gfont.size(com.maddox.il2.builder.PlMisBorn.i18n("bplace_planes"), gsize);
        float f7 = gsize.dx;
        gfont.size(com.maddox.il2.builder.PlMisBorn.i18n("bplace_list"), gsize);
        float f8 = gsize.dx;
        gfont.size("Modify", gsize);
        float f9 = gsize.dx;
        float f10 = f3;
        if(f10 < f4)
            f10 = f4;
        if(f10 < f5)
            f10 = f5;
        if(f10 < f6)
            f10 = f6;
        if(f10 < f9)
            f10 = f9;
        float f11 = f2 + f10;
        f10 += f2 + 4F * f2 + f7 + 4F * f2 + f8 + 4F * f2;
        if(f < f10)
            f = f10;
        float f12 = 10F * f2 + 10F * f2 + 2.0F * f2;
        if(f1 < f12)
            f1 = f12;
        float f13 = (f - f11) / 2.0F;
        bAddAll.setPosSize(f13, f2, f11, 2.0F * f2);
        bAdd.setPosSize(f13, f2 + 2.0F * f2, f11, 2.0F * f2);
        bRemAll.setPosSize(f13, 2.0F * f2 + 4F * f2, f11, 2.0F * f2);
        bRem.setPosSize(f13, 2.0F * f2 + 6F * f2, f11, 2.0F * f2);
        bModifyPlane.setPosSize(f13, 2.0F * f2 + 10F * f2, f11, 2.0F * f2);
        float f14 = (f - f11 - 4F * f2) / 2.0F;
        float f15 = f1 - 6F * f2 - 2.0F * f2 - 3F * f2;
        lstAvailable.setPosSize(f2, f2, f14, f15);
        lstInReserve.setPosSize(f - f2 - f14, f2, f14, f15);
        gfont.size(" " + com.maddox.il2.builder.PlMisBorn.i18n("bplace_cats") + " ", gsize);
        f14 = gsize.dx;
        float f16 = f2 + f15;
        lSeparate.setPosSize(2.0F * f2, f16, f14, 2.0F * f2);
        bSeparate.setPosSize(f2, f16 + f2, f - 2.0F * f2, f1 - f16 - 2.0F * f2);
        gfont.size(com.maddox.il2.builder.PlMisBorn.i18n("bplace_country"), gsize);
        float f17 = gsize.dx;
        gfont.size(com.maddox.il2.builder.PlMisBorn.i18n("bplace_year"), gsize);
        if(f17 < gsize.dx)
            f17 = gsize.dx;
        gfont.size(com.maddox.il2.builder.PlMisBorn.i18n("bplace_category"), gsize);
        if(f17 < gsize.dx)
            f17 = gsize.dx;
        f11 = 2.0F * f2 + f4 + f6;
        f14 = f - f17 - f11 - 6F * f2;
        float f18 = gwindow.lAF().getComboH();
        lCountry.setPosSize(2.0F * f2, f16 + 2.0F * f2, f17, 2.0F * f2);
        cCountry.setPosSize(2.0F * f2 + f17 + f2, f16 + 2.0F * f2 + (2.0F * f2 - f18) / 2.0F, f14, f18);
        bCountryAdd.setPosSize(f - 4F * f2 - f6 - f4, f16 + 2.0F * f2, f2 + f4, 2.0F * f2);
        bCountryRem.setPosSize(f - 3F * f2 - f6, f16 + 2.0F * f2, f2 + f6, 2.0F * f2);
        lYear.setPosSize(2.0F * f2, f16 + 4F * f2, f17, 2.0F * f2);
        cYear.setPosSize(2.0F * f2 + f17 + f2, f16 + 4F * f2 + (2.0F * f2 - f18) / 2.0F, f14, f18);
        bYearAdd.setPosSize(f - 4F * f2 - f6 - f4, f16 + 4F * f2, f2 + f4, 2.0F * f2);
        bYearRem.setPosSize(f - 3F * f2 - f6, f16 + 4F * f2, f2 + f6, 2.0F * f2);
        lType.setPosSize(2.0F * f2, f16 + 6F * f2, f17, 2.0F * f2);
        cType.setPosSize(2.0F * f2 + f17 + f2, f16 + 6F * f2 + (2.0F * f2 - f18) / 2.0F, f14, f18);
        bTypeAdd.setPosSize(f - 4F * f2 - f6 - f4, f16 + 6F * f2, f2 + f4, 2.0F * f2);
        bTypeRem.setPosSize(f - 3F * f2 - f6, f16 + 6F * f2, f2 + f6, 2.0F * f2);
    }

    private boolean zutiFindAirport(com.maddox.JGP.Point3d point3d)
    {
        point3d.z = 0.0D;
        com.maddox.il2.ai.Airport airport = com.maddox.il2.ai.Airport.nearest(point3d, -1, 7);
        if(airport != null)
        {
            double d = 250000D;
            com.maddox.JGP.Point3d point3d1 = airport.pos.getAbsPoint();
            double d1 = (point3d1.x - point3d.x) * (point3d1.x - point3d.x) + (point3d1.y - point3d.y) * (point3d1.y - point3d.y);
            if(d1 > d)
                return true;
            point3d.set(airport.pos.getAbsPoint());
            for(int i = 0; i < allActors.size(); i++)
            {
                com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)allActors.get(i);
                com.maddox.JGP.Point3d point3d2 = actorborn.pos.getAbsPoint();
                double d2 = (point3d.x - point3d2.x) * (point3d.x - point3d2.x) + (point3d.y - point3d2.y) * (point3d.y - point3d2.y);
                if(d2 < 100D)
                    return false;
            }

            return true;
        } else
        {
            return true;
        }
    }

    private void zutiResetValues()
    {
        if(wHeight != null)
            wHeight.setValue("");
        if(wSpeed != null)
            wSpeed.setValue("");
        if(wOrient != null)
            wOrient.setValue("");
        if(wMaxPilots != null)
            wMaxPilots.setValue("");
        if(wFriction != null)
            wFriction.setValue("");
        if(wZutiRadar_Min != null)
            wZutiRadar_Min.setValue("");
        if(wZutiRadar_Max != null)
            wZutiRadar_Max.setValue("");
        if(wZutiRadar_Range != null)
            wZutiRadar_Range.setValue("");
    }

    private java.lang.String BoolToInt(boolean flag)
    {
        if(flag)
            return "1";
        else
            return "0";
    }

    private void zutiSaveBornPlaceCountries(com.maddox.il2.builder.ActorBorn actorborn, com.maddox.rts.SectFile sectfile, int i)
    {
        if(actorborn.zutiHomeBaseCountries != null && actorborn.zutiHomeBaseCountries.size() > 0)
        {
            java.util.Collections.sort(actorborn.zutiHomeBaseCountries);
            int j = sectfile.sectionAdd("BornPlaceCountries" + i);
            for(int k = 0; k < actorborn.zutiHomeBaseCountries.size(); k++)
            {
                java.lang.String s = (java.lang.String)actorborn.zutiHomeBaseCountries.get(k);
                java.lang.String s1 = com.maddox.il2.game.I18N.getCountryKey(s);
                if(s1 != null)
                    sectfile.lineAdd(j, s1);
            }

        }
    }

    private void zutiLoadBornPlaceCountries(com.maddox.il2.builder.ActorBorn actorborn, com.maddox.rts.SectFile sectfile)
    {
        java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
        int i = sectfile.sectionIndex("MDS_BornPlace_" + (int)actorborn.pos.getAbsPoint().x + "_" + (int)actorborn.pos.getAbsPoint().y + "_Countries");
        if(i >= 0)
        {
            if(actorborn.zutiHomeBaseCountries == null)
                actorborn.zutiHomeBaseCountries = new ArrayList();
            actorborn.zutiHomeBaseCountries.clear();
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
                try
                {
                    java.lang.String s = sectfile.var(i, k);
                    java.lang.String s1 = resourcebundle.getString(s);
                    if(!actorborn.zutiHomeBaseCountries.contains(s1))
                        actorborn.zutiHomeBaseCountries.add(s1);
                }
                catch(java.lang.Exception exception) { }

        }
    }

    private void zutiCountriesWindowRefresh(com.maddox.il2.builder.ActorBorn actorborn)
    {
        if(actorborn == null)
            return;
        if(zuti_homeBaseCountries == null)
            return;
        try
        {
            if(zuti_homeBaseCountries.isVisible())
            {
                zuti_homeBaseCountries.close(true);
                zuti_homeBaseCountries.setSelectedCountries(actorborn);
                zuti_homeBaseCountries.showWindow();
            }
        }
        catch(java.lang.Exception exception) { }
        return;
    }

    private void zutiCountriesWindowShow(com.maddox.il2.builder.ActorBorn actorborn, int i)
    {
        if(actorborn == null)
            return;
        try
        {
            if(zuti_homeBaseCountries == null)
                zuti_homeBaseCountries = new Zuti_WHomeBaseCountries();
            zuti_homeBaseCountries.setMode(i);
            if(zuti_homeBaseCountries.isVisible())
            {
                zuti_homeBaseCountries.close(true);
                zuti_homeBaseCountries.setSelectedCountries(actorborn);
                zuti_homeBaseCountries.showWindow();
            } else
            {
                zuti_homeBaseCountries.setSelectedCountries(actorborn);
                zuti_homeBaseCountries.showWindow();
            }
        }
        catch(java.lang.Exception exception) { }
    }

    private java.util.ArrayList zutiSyncLists(java.util.ArrayList arraylist, java.util.ArrayList arraylist1)
    {
        java.util.ArrayList arraylist2 = new ArrayList();
        for(int i = 0; i < arraylist.size(); i++)
        {
            java.lang.String s = (java.lang.String)arraylist.get(i);
            boolean flag = false;
            int i1 = 0;
            do
            {
                if(i1 >= arraylist1.size())
                    break;
                com.maddox.il2.game.ZutiAircraft zutiaircraft2 = (com.maddox.il2.game.ZutiAircraft)arraylist1.get(i1);
                if(zutiaircraft2.getAcName().equals(s))
                {
                    flag = true;
                    break;
                }
                i1++;
            } while(true);
            if(!flag)
            {
                com.maddox.il2.game.ZutiAircraft zutiaircraft1 = new ZutiAircraft();
                zutiaircraft1.setAcName(s);
                zutiaircraft1.setMaxAllowed(0);
                java.util.ArrayList arraylist3 = new ArrayList();
                arraylist3.add("Default");
                zutiaircraft1.setSelectedWeapons(arraylist3);
                arraylist2.add(zutiaircraft1);
            }
        }

        for(int j = 0; j < arraylist2.size(); j++)
            arraylist1.add(arraylist2.get(j));

        arraylist2.clear();
        for(int k = 0; k < arraylist1.size(); k++)
        {
            com.maddox.il2.game.ZutiAircraft zutiaircraft = (com.maddox.il2.game.ZutiAircraft)arraylist1.get(k);
            boolean flag1 = false;
            int j1 = 0;
            do
            {
                if(j1 >= arraylist.size())
                    break;
                java.lang.String s1 = (java.lang.String)arraylist.get(j1);
                if(zutiaircraft.getAcName().equals(s1))
                {
                    flag1 = true;
                    break;
                }
                j1++;
            } while(true);
            if(!flag1)
                arraylist2.add(zutiaircraft);
        }

        for(int l = 0; l < arraylist2.size(); l++)
            arraylist1.remove(arraylist2.get(l));

        return arraylist1;
    }

    private com.maddox.il2.builder.Zuti_WAircraftLoadout zutiCreateAndLoadLoadoutsObject(com.maddox.il2.builder.ActorBorn actorborn)
    {
        actorborn.zutiAcLoadouts = new Zuti_WAircraftLoadout();
        actorborn.zutiAcLoadouts.modifiedAircrafts = new ArrayList();
        for(int i = 0; i < actorborn.airNames.size(); i++)
        {
            java.lang.String s = (java.lang.String)actorborn.airNames.get(i);
            com.maddox.il2.game.ZutiAircraft zutiaircraft = new ZutiAircraft();
            zutiaircraft.setAcName(s);
            if(actorborn.zutiDecreasingNumberOfPlanes)
                zutiaircraft.setMaxAllowed(-1);
            else
                zutiaircraft.setMaxAllowed(0);
            java.util.ArrayList arraylist = new ArrayList();
            arraylist.add("Default");
            zutiaircraft.setSelectedWeapons(arraylist);
            actorborn.zutiAcLoadouts.modifiedAircrafts.add(zutiaircraft);
        }

        return actorborn.zutiAcLoadouts;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static final int rMIN = 500;
    private static final int rMAX = 10000;
    private static final int rDEF = 1000;
    private static final int rSTEP = 50;
    private final float checkBoxPosition = 25F;
    private final float spawnAndFowTextFieldPosition = 20F;
    com.maddox.gwindow.GWindowCheckBox wZutiEnableFriction;
    com.maddox.gwindow.GWindowEditControl wFriction;
    com.maddox.gwindow.GWindowCheckBox wZutiStaticPositionOnly;
    com.maddox.gwindow.GWindowCheckBox wEnablePlaneLimits;
    com.maddox.gwindow.GWindowCheckBox wDecreasingNumberOfPlanes;
    com.maddox.gwindow.GWindowCheckBox wIncludeStaticPlanes;
    com.maddox.gwindow.GWindowLabel lProperties;
    com.maddox.gwindow.GWindowLabel lLimitations;
    com.maddox.gwindow.GWindowBoxSeparate bSeparate_Limitations;
    com.maddox.gwindow.GWindowLabel lRadius;
    com.maddox.gwindow.GWindowLabel lAirSpawn;
    com.maddox.gwindow.GWindowLabel lRadar;
    com.maddox.gwindow.GWindowButton bModifyPlane;
    com.maddox.gwindow.GWindowButton bModifyCountries;
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabSpawn;
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabFow;
    com.maddox.gwindow.GWindowEditControl wHeight;
    com.maddox.gwindow.GWindowEditControl wSpeed;
    com.maddox.gwindow.GWindowEditControl wOrient;
    com.maddox.gwindow.GWindowEditControl wMaxPilots;
    com.maddox.gwindow.GWindowEditControl wZutiRadar_Min;
    com.maddox.gwindow.GWindowEditControl wZutiRadar_Max;
    com.maddox.gwindow.GWindowEditControl wZutiRadar_Range;
    com.maddox.gwindow.GWindowCheckBox wZutiDisableSpawning;
    com.maddox.gwindow.GWindowCheckBox wAlwaysAirSpawn;
    com.maddox.gwindow.GWindowCheckBox wAirSpawnIfCarrierFull;
    com.maddox.gwindow.GWindowBoxSeparate bSeparate_Properties;
    com.maddox.gwindow.GWindowBoxSeparate bSeparate_AirSpawn;
    com.maddox.gwindow.GWindowBoxSeparate bSeparate_Radar;
    com.maddox.gwindow.GWindowBoxSeparate bSeparate_Capture_Local;
    private com.maddox.il2.builder.Zuti_WManageAircrafts zuti_manageAircrafts;
    private com.maddox.il2.builder.Zuti_WHomeBaseCountries zuti_homeBaseCountries;
    private static int ZUTI_ALL_AC_LIST_SIZE = -1;
    protected java.util.ArrayList allActors;
    com.maddox.il2.builder.Item item[] = {
        new Item(com.maddox.il2.builder.PlMisBorn.i18n("BornPlace"))
    };
    private com.maddox.JGP.Point2d p2d;
    private com.maddox.JGP.Point2d p2dt;
    private com.maddox.JGP.Point3d p3d;
    private static final int NCIRCLESEGMENTS = 48;
    private static float _circleXYZ[] = new float[144];
    private com.maddox.il2.builder.PlMission pluginMission;
    private int startComboBox1;
    private com.maddox.gwindow.GWindowMenuItem viewType;
    private java.lang.String _actorInfo[];
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabTarget;
    com.maddox.gwindow.GWindowComboControl wArmy;
    com.maddox.gwindow.GWindowHSliderInt wR;
    com.maddox.gwindow.GWindowCheckBox wParachute;
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabAircraft;
    com.maddox.il2.builder.Table lstAvailable;
    com.maddox.il2.builder.Table lstInReserve;
    com.maddox.gwindow.GWindowButton bAddAll;
    com.maddox.gwindow.GWindowButton bAdd;
    com.maddox.gwindow.GWindowButton bRemAll;
    com.maddox.gwindow.GWindowButton bRem;
    com.maddox.gwindow.GWindowLabel lSeparate;
    com.maddox.gwindow.GWindowBoxSeparate bSeparate;
    com.maddox.gwindow.GWindowLabel lCountry;
    com.maddox.gwindow.GWindowComboControl cCountry;
    static java.util.ArrayList lstCountry = new ArrayList();
    com.maddox.gwindow.GWindowButton bCountryAdd;
    com.maddox.gwindow.GWindowButton bCountryRem;
    com.maddox.gwindow.GWindowLabel lYear;
    com.maddox.gwindow.GWindowComboControl cYear;
    static java.util.ArrayList lstYear = new ArrayList();
    com.maddox.gwindow.GWindowButton bYearAdd;
    com.maddox.gwindow.GWindowButton bYearRem;
    com.maddox.gwindow.GWindowLabel lType;
    com.maddox.gwindow.GWindowComboControl cType;
    static java.util.ArrayList lstType = new ArrayList();
    com.maddox.gwindow.GWindowButton bTypeAdd;
    com.maddox.gwindow.GWindowButton bTypeRem;
    static java.lang.Class class$com$maddox$il2$objects$air$TypeStormovik; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$air$TypeFighter; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$air$TypeBomber; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$air$TypeScout; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$air$TypeDiveBomber; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$air$TypeSailPlane; /* synthetic field */
    static java.lang.Class class$com$maddox$il2$objects$air$Scheme1; /* synthetic field */

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMisBorn.class, "name", "MisBorn");
    }







}
