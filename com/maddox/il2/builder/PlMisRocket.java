// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMisRocket.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.ai.Army;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.I18N;
import com.maddox.il2.objects.ActorSimpleHMesh;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;
import java.util.HashMap;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, PlMission, Builder, BldConfig, 
//            WSelect

public class PlMisRocket extends com.maddox.il2.builder.Plugin
{
    static class Item
    {

        public int indx;
        public java.lang.String name;
        public java.lang.String i18nName;
        public java.lang.String meshName;
        public java.lang.String iconName;
        public int army;

        Item()
        {
        }
    }

    static class Rocket extends com.maddox.il2.objects.ActorSimpleHMesh
    {

        public com.maddox.il2.builder.Item item;
        public float timeout;
        public int count;
        public float period;
        public com.maddox.JGP.Point2d target;

        public Rocket(com.maddox.il2.builder.Item item1)
        {
            super(item1.meshName);
            timeout = 0.0F;
            count = 1;
            period = 20F;
            item = item1;
            if(item1.iconName != null)
                try
                {
                    icon = com.maddox.il2.engine.Mat.New(item1.iconName);
                }
                catch(java.lang.Exception exception) { }
            setArmy(item1.army);
        }
    }


    public PlMisRocket()
    {
        allActors = new ArrayList();
        itemMap = new HashMap();
        lineNXYZ = new float[6];
        p2d = new Point2d();
        p3d = new Point3d();
        p2d2 = new Point2d();
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
            com.maddox.il2.builder.Rocket rocket = (com.maddox.il2.builder.Rocket)allActors.get(j);
            boolean flag = com.maddox.il2.builder.Plugin.builder.project2d(rocket.pos.getAbsPoint(), p2d);
            if(rocket.target != null)
            {
                p3d.x = rocket.target.x;
                p3d.y = rocket.target.y;
                p3d.z = com.maddox.il2.engine.Engine.land().HQ(p3d.x, p3d.y);
                boolean flag1 = com.maddox.il2.builder.Plugin.builder.project2d(p3d, p2d2);
                if(flag || flag1)
                {
                    lineNXYZ[0] = (float)p2d.x;
                    lineNXYZ[1] = (float)p2d.y;
                    lineNXYZ[2] = 0.0F;
                    lineNXYZ[3] = (float)p2d2.x;
                    lineNXYZ[4] = (float)p2d2.y;
                    lineNXYZ[5] = 0.0F;
                    com.maddox.il2.engine.Render.drawBeginLines(-1);
                    com.maddox.il2.engine.Render.drawLines(lineNXYZ, 2, 3F, 0xff00ff00, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE | com.maddox.il2.engine.Mat.BLEND, 3);
                    com.maddox.il2.engine.Render.drawEnd();
                }
                if(flag1)
                {
                    float f = com.maddox.il2.builder.Plugin.builder.conf.iconSize;
                    com.maddox.il2.engine.Render.drawTile((float)(p2d2.x - (double)(f / 2.0F)), (float)(p2d2.y - (double)(f / 2.0F)), f, f, 0.0F, targetMat, -1, 0.0F, 1.0F, 1.0F, -1F);
                }
            }
            if(flag)
            {
                int k = com.maddox.il2.ai.Army.color(rocket.getArmy());
                if(rocket == actor)
                    k = com.maddox.il2.builder.Builder.colorSelected();
                com.maddox.il2.engine.IconDraw.setColor(k);
                com.maddox.il2.engine.IconDraw.render(rocket, p2d.x, p2d.y);
            }
        }

    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        int i = allActors.size();
        if(i == 0)
            return true;
        com.maddox.il2.engine.Orient orient = new Orient();
        int j = sectfile.sectionAdd("Rocket");
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.builder.Rocket rocket = (com.maddox.il2.builder.Rocket)allActors.get(k);
            com.maddox.JGP.Point3d point3d = rocket.pos.getAbsPoint();
            com.maddox.il2.engine.Orient orient1 = rocket.pos.getAbsOrient();
            orient.set(orient1);
            orient.wrap360();
            sectfile.lineAdd(j, "" + rocket.name() + " " + rocket.item.name + " " + rocket.getArmy() + " " + formatPos(point3d.x, point3d.y, orient.azimut()) + " " + rocket.timeout + " " + rocket.count + " " + rocket.period + " " + (rocket.target == null ? "" : formatValue(rocket.target.x) + " " + formatValue(rocket.target.y)));
        }

        return true;
    }

    private java.lang.String formatPos(double d, double d1, float f)
    {
        return formatValue(d) + " " + formatValue(d1) + " " + formatValue(f);
    }

    private java.lang.String formatValue(double d)
    {
        boolean flag = d < 0.0D;
        if(flag)
            d = -d;
        double d1 = (d + 0.0050000000000000001D) - (double)(int)d;
        if(d1 >= 0.10000000000000001D)
            return (flag ? "-" : "") + (int)d + "." + (int)(d1 * 100D);
        else
            return (flag ? "-" : "") + (int)d + ".0" + (int)(d1 * 100D);
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("Rocket");
        if(i >= 0)
        {
            com.maddox.il2.engine.Orient orient = new Orient();
            int j = sectfile.vars(i);
            com.maddox.JGP.Point3d point3d = new Point3d();
            for(int k = 0; k < j; k++)
            {
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
                if(numbertokenizer.hasMoreTokens())
                {
                    java.lang.String s = numbertokenizer.next("");
                    if(numbertokenizer.hasMoreTokens())
                    {
                        java.lang.String s1 = numbertokenizer.next("");
                        if(numbertokenizer.hasMoreTokens())
                        {
                            com.maddox.il2.builder.Item item1 = (com.maddox.il2.builder.Item)itemMap.get(s1);
                            if(item1 != null)
                            {
                                int l = numbertokenizer.next(1, 1, 2);
                                point3d.x = numbertokenizer.next(0.0D);
                                if(numbertokenizer.hasMoreTokens())
                                {
                                    point3d.y = numbertokenizer.next(0.0D);
                                    if(numbertokenizer.hasMoreTokens())
                                    {
                                        com.maddox.il2.builder.Rocket rocket = insert(item1, s, point3d, false);
                                        if(rocket != null)
                                        {
                                            rocket.setArmy(l);
                                            orient.set(numbertokenizer.next(0.0F), 0.0F, 0.0F);
                                            rocket.pos.setAbs(orient);
                                            rocket.pos.reset();
                                            rocket.timeout = numbertokenizer.next(rocket.timeout);
                                            rocket.count = numbertokenizer.next(rocket.count);
                                            rocket.period = numbertokenizer.next(rocket.period);
                                            if(numbertokenizer.hasMoreTokens())
                                                rocket.target = new Point2d(numbertokenizer.next(0.0D), numbertokenizer.next(0.0D));
                                        }
                                    }
                                }
                            }
                        }
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
            com.maddox.il2.builder.Rocket rocket = (com.maddox.il2.builder.Rocket)allActors.get(j);
            rocket.destroy();
        }

        allActors.clear();
    }

    public void delete(com.maddox.il2.engine.Actor actor)
    {
        allActors.remove(actor);
        actor.destroy();
    }

    private void makeName(com.maddox.il2.engine.Actor actor, java.lang.String s)
    {
        if(s != null && com.maddox.il2.engine.Actor.getByName(s) == null)
        {
            actor.setName(s);
            return;
        }
        int i = 0;
        do
        {
            s = i + "_Rocket";
            if(com.maddox.il2.engine.Actor.getByName(s) != null)
            {
                i++;
            } else
            {
                actor.setName(s);
                return;
            }
        } while(true);
    }

    private com.maddox.il2.builder.Rocket insert(com.maddox.il2.builder.Item item1, java.lang.String s, com.maddox.JGP.Point3d point3d, boolean flag)
    {
        com.maddox.il2.builder.Rocket rocket;
        rocket = new Rocket(item1);
        rocket.pos.setAbs(point3d);
        rocket.pos.reset();
        com.maddox.rts.Property.set(rocket, "builderSpawn", "");
        com.maddox.rts.Property.set(rocket, "builderPlugin", this);
        makeName(rocket, s);
        allActors.add(rocket);
        if(flag)
            com.maddox.il2.builder.Plugin.builder.setSelected(rocket);
        com.maddox.il2.builder.PlMission.setChanged();
        return rocket;
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
            insert(item[j], null, loc.getPoint(), flag);
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
            com.maddox.il2.builder.Rocket rocket = (com.maddox.il2.builder.Rocket)allActors.get(j);
            rocket.drawing(viewType.bChecked);
        }

    }

    public void configure()
    {
        if(com.maddox.il2.builder.Plugin.getPlugin("Mission") == null)
            throw new RuntimeException("PlMisRocket: plugin 'Mission' not found");
        pluginMission = (com.maddox.il2.builder.PlMission)com.maddox.il2.builder.Plugin.getPlugin("Mission");
        if(sectFile == null)
            throw new RuntimeException("PlMisRocket: field 'sectFile' not defined");
        com.maddox.rts.SectFile sectfile = new SectFile(sectFile, 0);
        int i = sectfile.sections();
        if(i <= 0)
            throw new RuntimeException("PlMisRocket: file '" + sectFile + "' is empty");
        item = new com.maddox.il2.builder.Item[i];
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.Item item1 = new Item();
            item1.indx = j;
            item1.name = sectfile.sectionName(j);
            item1.i18nName = com.maddox.il2.game.I18N.technic(item1.name);
            item1.meshName = sectfile.get(item1.name, "MeshEditor");
            item1.iconName = sectfile.get(item1.name, "IconEditor");
            item1.army = sectfile.get(item1.name, "DefaultArmy", 1, 1, 2);
            item[j] = item1;
            itemMap.put(item1.name, item1);
        }

        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMisRocket$Rocket.class, "i18nName", com.maddox.il2.builder.Plugin.i18n("Rocket"));
        targetMat = com.maddox.il2.engine.Mat.New("icons/objV1target.mat");
    }

    private void fillComboBox2(int i, int j)
    {
        if(i != startComboBox1)
            return;
        if(com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType != i)
        {
            com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType = i;
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.clear(false);
            for(int k = 0; k < item.length; k++)
                com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.add(item[k].i18nName);

            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(i, true, false);
        }
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(j, true, false);
        fillComboBox2Render(i, j);
    }

    private void fillComboBox2Render(int i, int j)
    {
        try
        {
            java.lang.String s = item[j].meshName;
            com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(s, true);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(null, true);
        }
    }

    public void viewTypeAll(boolean flag)
    {
        viewType.bChecked = flag;
        updateView();
    }

    public java.lang.String[] actorInfo(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.builder.Rocket rocket = (com.maddox.il2.builder.Rocket)actor;
        _actorInfo[0] = rocket.item.i18nName;
        return _actorInfo;
    }

    public void syncSelector()
    {
        com.maddox.il2.builder.Rocket rocket = (com.maddox.il2.builder.Rocket)com.maddox.il2.builder.Plugin.builder.selectedActor();
        fillComboBox2(startComboBox1, rocket.item.indx);
        com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.addTab(1, tabTarget);
        wName.cap.set(rocket.item.i18nName);
        wArmy.setSelected(rocket.getArmy() - 1, true, false);
        wTimeOutH.setValue("" + (int)((rocket.timeout / 60F) % 24F), false);
        wTimeOutM.setValue("" + (int)(rocket.timeout % 60F), false);
        wCount.setValue("" + rocket.count, false);
        wPeriodH.setValue("" + (int)((rocket.period / 60F) % 24F), false);
        wPeriodM.setValue("" + (int)(rocket.period % 60F), false);
    }

    public void createGUI()
    {
        startComboBox1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.size();
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.add(com.maddox.il2.builder.Plugin.i18n("Rocket"));
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int j, int k)
            {
                int l = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
                if(l >= 0 && j == 2)
                    fillComboBox2(l, 0);
                return false;
            }

        }
);
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int j, int k)
            {
                if(j != 2)
                    return false;
                int l = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
                if(l != startComboBox1)
                {
                    return false;
                } else
                {
                    int i1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
                    fillComboBox2Render(l, i1);
                    return false;
                }
            }

        }
);
        int i;
        for(i = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.size() - 1; i >= 0; i--)
            if(pluginMission.viewBridge == com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.getItem(i))
                break;

        if(--i >= 0)
        {
            viewType = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(i, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("showRocket"), null) {

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
        tabTarget = com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.Plugin.i18n("Rocket"), gwindowdialogclient);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Name"), null));
        gwindowdialogclient.addLabel(wName = new GWindowLabel(gwindowdialogclient, 9F, 1.0F, 7F, 1.3F, "", null));
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Army"), null));
        gwindowdialogclient.addControl(wArmy = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 3F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                for(int j = 1; j < com.maddox.il2.builder.Builder.armyAmount(); j++)
                    add(com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(j)));

            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
                    int l = getSelected() + 1;
                    actor.setArmy(l);
                    com.maddox.il2.builder.PlMission.setChanged();
                    com.maddox.il2.builder.PlMission.checkShowCurrentArmy();
                    return false;
                }
            }

        }
);
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient1 = gwindowdialogclient;
        gwindowdialogclient1.addLabel(wLTimeOutH = new GWindowLabel(gwindowdialogclient1, 1.0F, 5F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("TimeOut"), null));
        gwindowdialogclient1.addControl(wTimeOutH = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient1, 9F, 5F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    getTimeOut();
                    return false;
                }
            }

        }
);
        gwindowdialogclient1.addLabel(wLTimeOutM = new GWindowLabel(gwindowdialogclient1, 11.2F, 5F, 1.0F, 1.3F, ":", null));
        gwindowdialogclient1.addControl(wTimeOutM = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient1, 11.5F, 5F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    getTimeOut();
                    return false;
                }
            }

        }
);
        gwindowdialogclient1.addLabel(wLCount = new GWindowLabel(gwindowdialogclient1, 1.0F, 7F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Count"), null));
        gwindowdialogclient1.addControl(wCount = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient1, 9F, 7F, 5F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                    return false;
                java.lang.String s = wCount.getValue();
                int l = 0;
                try
                {
                    l = java.lang.Integer.parseInt(s);
                }
                catch(java.lang.Exception exception) { }
                if(l < 0)
                    l = 0;
                com.maddox.il2.builder.Rocket rocket = (com.maddox.il2.builder.Rocket)com.maddox.il2.builder.Plugin.builder.selectedActor();
                rocket.count = l;
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
        gwindowdialogclient1.addLabel(wLPeriodH = new GWindowLabel(gwindowdialogclient1, 1.0F, 9F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Period"), null));
        gwindowdialogclient1.addControl(wPeriodH = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient1, 9F, 9F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    getPeriod();
                    return false;
                }
            }

        }
);
        gwindowdialogclient1.addLabel(wLPeriodM = new GWindowLabel(gwindowdialogclient1, 11.2F, 9F, 1.0F, 1.3F, ":", null));
        gwindowdialogclient1.addControl(wPeriodM = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient1, 11.5F, 9F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int j, int k)
            {
                if(j != 2)
                {
                    return false;
                } else
                {
                    getPeriod();
                    return false;
                }
            }

        }
);
        gwindowdialogclient1.addLabel(new GWindowLabel(gwindowdialogclient1, 1.0F, 11F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Target"), null));
        gwindowdialogclient1.addControl(wTargetSet = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 1.0F, 13F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("&Set"), null) {

            public boolean notify(int j, int k)
            {
                if(j == 2)
                {
                    com.maddox.il2.builder.Rocket rocket = (com.maddox.il2.builder.Rocket)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    rocket.target = null;
                    com.maddox.il2.builder.PlMission.setChanged();
                    com.maddox.il2.builder.Plugin.builder.beginSelectTarget();
                }
                return false;
            }

        }
);
        gwindowdialogclient1.addControl(wTargetClear = new com.maddox.gwindow.GWindowButton(gwindowdialogclient1, 9F, 13F, 5F, 1.6F, com.maddox.il2.builder.Plugin.i18n("&Clear"), null) {

            public boolean notify(int j, int k)
            {
                if(j == 2)
                {
                    com.maddox.il2.builder.Rocket rocket = (com.maddox.il2.builder.Rocket)com.maddox.il2.builder.Plugin.builder.selectedActor();
                    rocket.target = null;
                    com.maddox.il2.builder.PlMission.setChanged();
                }
                return false;
            }

        }
);
    }

    private void getTimeOut()
    {
        java.lang.String s = wTimeOutH.getValue();
        double d = 0.0D;
        try
        {
            d = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception) { }
        if(d < 0.0D)
            d = 0.0D;
        if(d > 12D)
            d = 12D;
        s = wTimeOutM.getValue();
        double d1 = 0.0D;
        try
        {
            d1 = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception1) { }
        if(d1 < 0.0D)
            d1 = 0.0D;
        if(d1 > 60D)
            d1 = 60D;
        float f = (float)(d * 60D + d1);
        com.maddox.il2.builder.Rocket rocket = (com.maddox.il2.builder.Rocket)com.maddox.il2.builder.Plugin.builder.selectedActor();
        rocket.timeout = f;
        com.maddox.il2.builder.PlMission.setChanged();
    }

    private void getPeriod()
    {
        java.lang.String s = wPeriodH.getValue();
        double d = 0.0D;
        try
        {
            d = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception) { }
        if(d < 0.0D)
            d = 0.0D;
        if(d > 12D)
            d = 12D;
        s = wPeriodM.getValue();
        double d1 = 0.0D;
        try
        {
            d1 = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception1) { }
        if(d1 < 0.0D)
            d1 = 0.0D;
        if(d1 > 60D)
            d1 = 60D;
        float f = (float)(d * 60D + d1);
        com.maddox.il2.builder.Rocket rocket = (com.maddox.il2.builder.Rocket)com.maddox.il2.builder.Plugin.builder.selectedActor();
        rocket.period = f;
        com.maddox.il2.builder.PlMission.setChanged();
    }

    java.util.ArrayList allActors;
    com.maddox.il2.builder.Item item[];
    java.util.HashMap itemMap;
    private float lineNXYZ[];
    private com.maddox.JGP.Point2d p2d;
    private com.maddox.JGP.Point3d p3d;
    private com.maddox.JGP.Point2d p2d2;
    private com.maddox.il2.engine.Mat targetMat;
    private com.maddox.il2.builder.PlMission pluginMission;
    private int startComboBox1;
    private com.maddox.gwindow.GWindowMenuItem viewType;
    private java.lang.String _actorInfo[];
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabTarget;
    com.maddox.gwindow.GWindowLabel wName;
    com.maddox.gwindow.GWindowComboControl wArmy;
    com.maddox.gwindow.GWindowLabel wLTimeOutH;
    com.maddox.gwindow.GWindowEditControl wTimeOutH;
    com.maddox.gwindow.GWindowLabel wLTimeOutM;
    com.maddox.gwindow.GWindowEditControl wTimeOutM;
    com.maddox.gwindow.GWindowLabel wLCount;
    com.maddox.gwindow.GWindowEditControl wCount;
    com.maddox.gwindow.GWindowLabel wLPeriodH;
    com.maddox.gwindow.GWindowEditControl wPeriodH;
    com.maddox.gwindow.GWindowLabel wLPeriodM;
    com.maddox.gwindow.GWindowEditControl wPeriodM;
    com.maddox.gwindow.GWindowLabel wTarget;
    com.maddox.gwindow.GWindowButton wTargetSet;
    com.maddox.gwindow.GWindowButton wTargetClear;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMisRocket.class, "name", "MisRocket");
    }






}
