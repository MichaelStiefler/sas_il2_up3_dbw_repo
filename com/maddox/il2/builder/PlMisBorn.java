// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMisBorn.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
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
import com.maddox.rts.LDRres;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, ActorBorn, PlMission, Builder, 
//            BldConfig, WSelect

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

        public Table(com.maddox.gwindow.GWindow gwindow, java.lang.String s, float f, float f1, float f2, float f3)
        {
            super(gwindow, f, f1, f2, f3);
            lst = new ArrayList();
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
        if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            return;
        if(!viewType.bChecked)
            return;
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)allActors.get(j);
            if(com.maddox.il2.builder.Plugin.builder.project2d(actorborn.pos.getAbsPoint(), p2d))
            {
                int k = com.maddox.il2.ai.Army.color(actorborn.getArmy());
                if(actorborn == actor)
                    k = com.maddox.il2.builder.Builder.colorSelected();
                com.maddox.il2.engine.IconDraw.setColor(k);
                com.maddox.il2.engine.IconDraw.render(actorborn, p2d.x, p2d.y);
                actorborn.pos.getAbs(p3d);
                p3d.x += actorborn.r;
                if(com.maddox.il2.builder.Plugin.builder.project2d(p3d, p2dt))
                {
                    double d = p2dt.x - p2d.x;
                    if(d > (double)(com.maddox.il2.builder.Plugin.builder.conf.iconSize / 3))
                        drawCircle(p2d.x, p2d.y, d, k);
                }
            }
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
        int i = allActors.size();
        if(i == 0)
            return true;
        int j = countAllAircraft();
        int k = sectfile.sectionAdd("BornPlace");
        for(int l = 0; l < i; l++)
        {
            com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)allActors.get(l);
            sectfile.lineAdd(k, "" + actorborn.getArmy() + " " + actorborn.r + " " + (int)actorborn.pos.getAbsPoint().x + " " + (int)actorborn.pos.getAbsPoint().y + " " + (actorborn.bParachute ? "" : "0"));
            int i1 = actorborn.airNames.size();
            if(i1 > 0 && i1 < j)
            {
                int j1 = sectfile.sectionAdd("BornPlace" + l);
                for(int k1 = 0; k1 < i1; k1++)
                {
                    java.lang.String s = (java.lang.String)actorborn.airNames.get(k1);
                    sectfile.lineAdd(j1, s);
                }

            }
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
                if(actorborn != null)
                {
                    actorborn.airNames.clear();
                    if(actorborn != null)
                    {
                        actorborn.setArmy(l);
                        actorborn.r = i1;
                        actorborn.bParachute = flag;
                        int j1 = sectfile.sectionIndex("BornPlace" + k);
                        if(j1 >= 0)
                        {
                            int k1 = sectfile.vars(j1);
                            for(int l1 = 0; l1 < k1; l1++)
                            {
                                java.lang.String s = sectfile.var(j1, l1);
                                if(s != null)
                                {
                                    s = s.intern();
                                    java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(s, "airClass", null);
                                    if(class1 != null && com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                                        actorborn.airNames.add(s);
                                }
                            }

                        }
                        if(actorborn.airNames.size() == 0)
                            addAllAircraft(actorborn.airNames);
                    }
                }
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
        if(!findAirport(point3d))
            return null;
        com.maddox.il2.builder.ActorBorn actorborn;
        actorborn = new ActorBorn(point3d);
        addAllAircraft(actorborn.airNames);
        com.maddox.il2.builder.Plugin.builder.align(actorborn);
        com.maddox.rts.Property.set(actorborn, "builderSpawn", "");
        com.maddox.rts.Property.set(actorborn, "builderPlugin", this);
        allActors.add(actorborn);
        if(flag)
            com.maddox.il2.builder.Plugin.builder.setSelected(actorborn);
        com.maddox.il2.builder.PlMission.setChanged();
        return actorborn;
        java.lang.Exception exception;
        exception;
        return null;
    }

    public void insert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
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
        com.maddox.il2.builder.Plugin.builder.setSelected(null);
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
        if(com.maddox.il2.builder.Plugin.getPlugin("Mission") == null)
        {
            throw new RuntimeException("PlMisBorn: plugin 'Mission' not found");
        } else
        {
            pluginMission = (com.maddox.il2.builder.PlMission)com.maddox.il2.builder.Plugin.getPlugin("Mission");
            return;
        }
    }

    private void fillComboBox2(int i)
    {
        if(i != startComboBox1)
            return;
        if(com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType != i)
        {
            com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType = i;
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.clear(false);
            for(int j = 0; j < item.length; j++)
                com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.add(item[j].name);

            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(i, true, false);
        }
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(0, true, false);
        com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(null, true);
    }

    public void viewTypeAll(boolean flag)
    {
        viewType.bChecked = flag;
        updateView();
    }

    public java.lang.String[] actorInfo(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)actor;
        _actorInfo[0] = com.maddox.il2.builder.Plugin.i18n("BornPlace");
        return _actorInfo;
    }

    public void syncSelector()
    {
        com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
        fillComboBox2(startComboBox1);
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(0, true, false);
        com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.addTab(1, tabTarget);
        com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.addTab(2, tabAircraft);
        fillTabAircraft();
        wR.setPos((actorborn.r - 500) / 50, false);
        wArmy.setSelected(actorborn.getArmy(), true, false);
    }

    public void updateSelector()
    {
        fillTabAircraft();
    }

    public void createGUI()
    {
        startComboBox1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.size();
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.add(com.maddox.il2.builder.Plugin.i18n("BornPlace"));
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

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
        for(i = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.size() - 1; i >= 0; i--)
            if(pluginMission.viewBridge == com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.getItem(i))
                break;

        if(--i >= 0)
        {
            viewType = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(i, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("showBornPlaces"), null) {

                public void execute()
                {
                    bChecked = !bChecked;
                    updateView();
                }

            }
);
            viewType.bChecked = true;
        }
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
        tabTarget = com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.Plugin.i18n("BornPlaceActor"), gwindowdialogclient);
        gwindowdialogclient.addControl(wR = new com.maddox.gwindow.GWindowHSliderInt(gwindowdialogclient, 0, 190, 20, 1.0F, 1.0F, 10F) {

            public void afterCreated()
            {
                super.afterCreated();
                bSlidingNotify = true;
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    actorborn.r = pos() * 50 + 500;
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addControl(wArmy = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 1.0F, 3F, 10F) {

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
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 8F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Parachute"), null));
        gwindowdialogclient.addControl(wParachute = new com.maddox.gwindow.GWindowCheckBox(gwindowdialogclient, 10F, 5F, null) {

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
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient1 = (com.maddox.gwindow.GWindowDialogClient)com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.create(new com.maddox.gwindow.GWindowDialogClient() {

            public void resized()
            {
                super.resized();
                setAircraftSizes(this);
            }

        }
);
        tabAircraft = com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.Plugin.i18n("bplace_aircraft"), gwindowdialogclient1);
        lstAvailable = new Table(gwindowdialogclient1, com.maddox.il2.builder.Plugin.i18n("bplace_planes"), 1.0F, 1.0F, 6F, 10F);
        lstInReserve = new Table(gwindowdialogclient1, com.maddox.il2.builder.Plugin.i18n("bplace_list"), 14F, 1.0F, 6F, 10F);
        gwindowdialogclient1.addControl(bAddAll = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 8F, 1.0F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_addall"), null) {

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
        gwindowdialogclient1.addControl(bAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 8F, 3F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_add"), null) {

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
        gwindowdialogclient1.addControl(bRemAll = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 8F, 6F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_delall"), null) {

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
        gwindowdialogclient1.addControl(bRem = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 8F, 8F, 5F, 2.0F, com.maddox.il2.builder.Plugin.i18n("bplace_del"), null) {

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
        gwindowdialogclient1.addLabel(lSeparate = new GWindowLabel(gwindowdialogclient1, 3F, 12F, 12F, 1.6F, " " + com.maddox.il2.builder.Plugin.i18n("bplace_cats") + " ", null));
        bSeparate = new GWindowBoxSeparate(gwindowdialogclient1, 1.0F, 12.5F, 27F, 8F);
        bSeparate.exclude = lSeparate;
        gwindowdialogclient1.addLabel(lCountry = new GWindowLabel(gwindowdialogclient1, 2.0F, 14F, 7F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_country"), null));
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
                    java.lang.String s = com.maddox.rts.Property.stringValue(class1, "originCountry", null);
                    if(s == null)
                        continue;
                    java.lang.String s2 = null;
                    try
                    {
                        s2 = resourcebundle.getString(s);
                    }
                    catch(java.lang.Exception exception)
                    {
                        continue;
                    }
                    treemap.put(s2, s);
                }

                java.lang.String s1;
                for(java.util.Iterator iterator = treemap.keySet().iterator(); iterator.hasNext(); add(s1))
                {
                    s1 = (java.lang.String)iterator.next();
                    java.lang.String s3 = (java.lang.String)treemap.get(s1);
                    com.maddox.il2.builder.PlMisBorn.lstCountry.add(s3);
                }

                if(com.maddox.il2.builder.PlMisBorn.lstCountry.size() > 0)
                    setSelected(0, true, false);
            }

        }
);
        gwindowdialogclient1.addControl(bCountryAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 17F, 14F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_add"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.String s = (java.lang.String)com.maddox.il2.builder.PlMisBorn.lstCountry.get(cCountry.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int l = 0; l < arraylist.size(); l++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(l);
                    if(com.maddox.rts.Property.containsValue(class1, "cockpitClass") && s.equals(com.maddox.rts.Property.stringValue(class1, "originCountry", null)))
                    {
                        java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName");
                        if(!lstAvailable.lst.contains(s1))
                            lstAvailable.lst.add(s1);
                    }
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient1.addControl(bCountryRem = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 22F, 14F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_del"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.String s = (java.lang.String)com.maddox.il2.builder.PlMisBorn.lstCountry.get(cCountry.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int l = 0; l < arraylist.size(); l++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(l);
                    if(com.maddox.rts.Property.containsValue(class1, "cockpitClass") && s.equals(com.maddox.rts.Property.stringValue(class1, "originCountry", null)))
                    {
                        java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName");
                        int i1 = lstAvailable.lst.indexOf(s1);
                        if(i1 >= 0)
                            lstAvailable.lst.remove(i1);
                    }
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient1.addLabel(lYear = new GWindowLabel(gwindowdialogclient1, 2.0F, 16F, 7F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_year"), null));
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
                    if(com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                    {
                        float f = com.maddox.rts.Property.floatValue(class1, "yearService", 0.0F);
                        if(f != 0.0F)
                            treemap.put("" + (int)f, null);
                    }
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
        gwindowdialogclient1.addControl(bYearAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 17F, 16F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_add"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.String s = (java.lang.String)com.maddox.il2.builder.PlMisBorn.lstYear.get(cYear.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int l = 0; l < arraylist.size(); l++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(l);
                    if(com.maddox.rts.Property.containsValue(class1, "cockpitClass") && s.equals("" + (int)com.maddox.rts.Property.floatValue(class1, "yearService", 0.0F)))
                    {
                        java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName");
                        if(!lstAvailable.lst.contains(s1))
                            lstAvailable.lst.add(s1);
                    }
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient1.addControl(bYearRem = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 22F, 16F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_del"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.String s = (java.lang.String)com.maddox.il2.builder.PlMisBorn.lstYear.get(cYear.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int l = 0; l < arraylist.size(); l++)
                {
                    java.lang.Class class1 = (java.lang.Class)arraylist.get(l);
                    if(com.maddox.rts.Property.containsValue(class1, "cockpitClass") && s.equals("" + (int)com.maddox.rts.Property.floatValue(class1, "yearService", 0.0F)))
                    {
                        java.lang.String s1 = com.maddox.rts.Property.stringValue(class1, "keyName");
                        int i1 = lstAvailable.lst.indexOf(s1);
                        if(i1 >= 0)
                            lstAvailable.lst.remove(i1);
                    }
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient1.addLabel(lType = new GWindowLabel(gwindowdialogclient1, 2.0F, 18F, 7F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_category"), null));
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
                    if(com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
                    {
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
        gwindowdialogclient1.addControl(bTypeAdd = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 17F, 18F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_add"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.Class class1 = (java.lang.Class)com.maddox.il2.builder.PlMisBorn.lstType.get(cType.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int l = 0; l < arraylist.size(); l++)
                {
                    java.lang.Class class2 = (java.lang.Class)arraylist.get(l);
                    if(com.maddox.rts.Property.containsValue(class2, "cockpitClass") && (class1 != null ? class1.isAssignableFrom(class2) : !(com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 != null ? com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.Scheme1"))).isAssignableFrom(class2)))
                    {
                        java.lang.String s = com.maddox.rts.Property.stringValue(class2, "keyName");
                        if(!lstAvailable.lst.contains(s))
                            lstAvailable.lst.add(s);
                    }
                }

                fillTabAircraft();
                return true;
            }

        }
);
        gwindowdialogclient1.addControl(bTypeRem = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 22F, 18F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("bplace_del"), null) {

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.Class class1 = (java.lang.Class)com.maddox.il2.builder.PlMisBorn.lstType.get(cType.getSelected());
                java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
                for(int l = 0; l < arraylist.size(); l++)
                {
                    java.lang.Class class2 = (java.lang.Class)arraylist.get(l);
                    if(com.maddox.rts.Property.containsValue(class2, "cockpitClass") && (class1 != null ? class1.isAssignableFrom(class2) : !(com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 != null ? com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 : (com.maddox.il2.builder.PlMisBorn.class$com$maddox$il2$objects$air$Scheme1 = com.maddox.il2.builder.PlMisBorn._mthclass$("com.maddox.il2.objects.air.Scheme1"))).isAssignableFrom(class2)))
                    {
                        java.lang.String s = com.maddox.rts.Property.stringValue(class2, "keyName");
                        int i1 = lstAvailable.lst.indexOf(s);
                        if(i1 >= 0)
                            lstAvailable.lst.remove(i1);
                    }
                }

                fillTabAircraft();
                return true;
            }

        }
);
    }

    private void fillTabAircraft()
    {
        com.maddox.il2.builder.ActorBorn actorborn = (com.maddox.il2.builder.ActorBorn)com.maddox.il2.builder.Plugin.builder.selectedActor();
        int i = lstAvailable.selectRow;
        int j = lstInReserve.selectRow;
        lstAvailable.lst = actorborn.airNames;
        lstInReserve.lst.clear();
        java.util.ArrayList arraylist = com.maddox.il2.game.Main.cur().airClasses;
        for(int k = 0; k < arraylist.size(); k++)
        {
            java.lang.Class class1 = (java.lang.Class)arraylist.get(k);
            if(com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
            {
                java.lang.String s = com.maddox.rts.Property.stringValue(class1, "keyName");
                if(!lstAvailable.lst.contains(s))
                    lstInReserve.lst.add(s);
            }
        }

        if(lstAvailable.lst.size() > 0)
        {
            lstAvailable.lst.clear();
            for(int l = 0; l < arraylist.size(); l++)
            {
                java.lang.Class class2 = (java.lang.Class)arraylist.get(l);
                if(com.maddox.rts.Property.containsValue(class2, "cockpitClass"))
                {
                    java.lang.String s1 = com.maddox.rts.Property.stringValue(class2, "keyName");
                    if(!lstInReserve.lst.contains(s1))
                        lstAvailable.lst.add(s1);
                }
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
            if(com.maddox.rts.Property.containsValue(class1, "cockpitClass"))
            {
                java.lang.String s = com.maddox.rts.Property.stringValue(class1, "keyName");
                if(!arraylist.contains(s))
                    arraylist.add(s);
            }
        }

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
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_addall"), gsize);
        float f3 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_add"), gsize);
        float f4 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_delall"), gsize);
        float f5 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_del"), gsize);
        float f6 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_planes"), gsize);
        float f7 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_list"), gsize);
        float f8 = gsize.dx;
        float f9 = f3;
        if(f9 < f4)
            f9 = f4;
        if(f9 < f5)
            f9 = f5;
        if(f9 < f6)
            f9 = f6;
        float f10 = f2 + f9;
        f9 += f2 + 4F * f2 + f7 + 4F * f2 + f8 + 4F * f2;
        if(f < f9)
            f = f9;
        float f11 = 10F * f2 + 10F * f2 + 2.0F * f2;
        if(f1 < f11)
            f1 = f11;
        float f12 = (f - f10) / 2.0F;
        bAddAll.setPosSize(f12, f2, f10, 2.0F * f2);
        bAdd.setPosSize(f12, f2 + 2.0F * f2, f10, 2.0F * f2);
        bRemAll.setPosSize(f12, 2.0F * f2 + 4F * f2, f10, 2.0F * f2);
        bRem.setPosSize(f12, 2.0F * f2 + 6F * f2, f10, 2.0F * f2);
        float f13 = (f - f10 - 4F * f2) / 2.0F;
        float f14 = f1 - 6F * f2 - 2.0F * f2 - 3F * f2;
        lstAvailable.setPosSize(f2, f2, f13, f14);
        lstInReserve.setPosSize(f - f2 - f13, f2, f13, f14);
        gfont.size(" " + com.maddox.il2.builder.Plugin.i18n("bplace_cats") + " ", gsize);
        f13 = gsize.dx;
        float f15 = f2 + f14;
        lSeparate.setPosSize(2.0F * f2, f15, f13, 2.0F * f2);
        bSeparate.setPosSize(f2, f15 + f2, f - 2.0F * f2, f1 - f15 - 2.0F * f2);
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_country"), gsize);
        float f16 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_year"), gsize);
        if(f16 < gsize.dx)
            f16 = gsize.dx;
        gfont.size(com.maddox.il2.builder.Plugin.i18n("bplace_category"), gsize);
        if(f16 < gsize.dx)
            f16 = gsize.dx;
        f10 = 2.0F * f2 + f4 + f6;
        f13 = f - f16 - f10 - 6F * f2;
        float f17 = gwindow.lAF().getComboH();
        lCountry.setPosSize(2.0F * f2, f15 + 2.0F * f2, f16, 2.0F * f2);
        cCountry.setPosSize(2.0F * f2 + f16 + f2, f15 + 2.0F * f2 + (2.0F * f2 - f17) / 2.0F, f13, f17);
        bCountryAdd.setPosSize(f - 4F * f2 - f6 - f4, f15 + 2.0F * f2, f2 + f4, 2.0F * f2);
        bCountryRem.setPosSize(f - 3F * f2 - f6, f15 + 2.0F * f2, f2 + f6, 2.0F * f2);
        lYear.setPosSize(2.0F * f2, f15 + 4F * f2, f16, 2.0F * f2);
        cYear.setPosSize(2.0F * f2 + f16 + f2, f15 + 4F * f2 + (2.0F * f2 - f17) / 2.0F, f13, f17);
        bYearAdd.setPosSize(f - 4F * f2 - f6 - f4, f15 + 4F * f2, f2 + f4, 2.0F * f2);
        bYearRem.setPosSize(f - 3F * f2 - f6, f15 + 4F * f2, f2 + f6, 2.0F * f2);
        lType.setPosSize(2.0F * f2, f15 + 6F * f2, f16, 2.0F * f2);
        cType.setPosSize(2.0F * f2 + f16 + f2, f15 + 6F * f2 + (2.0F * f2 - f17) / 2.0F, f13, f17);
        bTypeAdd.setPosSize(f - 4F * f2 - f6 - f4, f15 + 6F * f2, f2 + f4, 2.0F * f2);
        bTypeRem.setPosSize(f - 3F * f2 - f6, f15 + 6F * f2, f2 + f6, 2.0F * f2);
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
    protected java.util.ArrayList allActors;
    com.maddox.il2.builder.Item item[] = {
        new Item(com.maddox.il2.builder.Plugin.i18n("BornPlace"))
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
