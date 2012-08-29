// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMisStaticCamera.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
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
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, ActorStaticCamera, PlMission, Builder, 
//            WSelect

public class PlMisStaticCamera extends com.maddox.il2.builder.Plugin
{
    static class Item
    {

        public java.lang.String name;

        public Item(java.lang.String s)
        {
            name = s;
        }
    }


    public PlMisStaticCamera()
    {
        allActors = new ArrayList();
        p2d = new Point2d();
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
            com.maddox.il2.builder.ActorStaticCamera actorstaticcamera = (com.maddox.il2.builder.ActorStaticCamera)allActors.get(j);
            if(com.maddox.il2.builder.Plugin.builder.project2d(actorstaticcamera.pos.getAbsPoint(), p2d))
            {
                int k = com.maddox.il2.ai.Army.color(0);
                if(actorstaticcamera == actor)
                    k = com.maddox.il2.builder.Builder.colorSelected();
                com.maddox.il2.engine.IconDraw.setColor(k);
                com.maddox.il2.engine.IconDraw.render(actorstaticcamera, p2d.x, p2d.y);
            }
        }

    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        int i = allActors.size();
        if(i == 0)
            return true;
        int j = sectfile.sectionAdd("StaticCamera");
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.builder.ActorStaticCamera actorstaticcamera = (com.maddox.il2.builder.ActorStaticCamera)allActors.get(k);
            sectfile.lineAdd(j, "" + (int)actorstaticcamera.pos.getAbsPoint().x + " " + (int)actorstaticcamera.pos.getAbsPoint().y + " " + actorstaticcamera.h);
        }

        return true;
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("StaticCamera");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            com.maddox.JGP.Point3d point3d = new Point3d();
            for(int k = 0; k < j; k++)
            {
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
                point3d.x = numbertokenizer.next(0);
                point3d.y = numbertokenizer.next(0);
                int l = numbertokenizer.next(100, 2, 10000);
                com.maddox.il2.builder.ActorStaticCamera actorstaticcamera = insert(point3d, false);
                if(actorstaticcamera != null)
                    actorstaticcamera.h = l;
            }

        }
    }

    public void deleteAll()
    {
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorStaticCamera actorstaticcamera = (com.maddox.il2.builder.ActorStaticCamera)allActors.get(j);
            actorstaticcamera.destroy();
        }

        allActors.clear();
    }

    public void delete(com.maddox.il2.engine.Actor actor)
    {
        allActors.remove(actor);
        actor.destroy();
    }

    private com.maddox.il2.builder.ActorStaticCamera insert(com.maddox.JGP.Point3d point3d, boolean flag)
    {
        if(allActors.size() >= 20)
            return null;
        com.maddox.il2.builder.ActorStaticCamera actorstaticcamera;
        actorstaticcamera = new ActorStaticCamera(point3d);
        com.maddox.rts.Property.set(actorstaticcamera, "builderSpawn", "");
        com.maddox.rts.Property.set(actorstaticcamera, "builderPlugin", this);
        allActors.add(actorstaticcamera);
        if(flag)
            com.maddox.il2.builder.Plugin.builder.setSelected(actorstaticcamera);
        com.maddox.il2.builder.PlMission.setChanged();
        return actorstaticcamera;
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
            com.maddox.il2.builder.ActorStaticCamera actorstaticcamera = (com.maddox.il2.builder.ActorStaticCamera)allActors.get(j);
            actorstaticcamera.drawing(viewType.bChecked);
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
                com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.add(com.maddox.il2.builder.Plugin.i18n(item[j].name));

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
        com.maddox.il2.builder.ActorStaticCamera actorstaticcamera = (com.maddox.il2.builder.ActorStaticCamera)actor;
        _actorInfo[0] = com.maddox.il2.builder.Plugin.i18n("StaticCamera") + " " + actorstaticcamera.h;
        return _actorInfo;
    }

    public void syncSelector()
    {
        com.maddox.il2.builder.ActorStaticCamera actorstaticcamera = (com.maddox.il2.builder.ActorStaticCamera)com.maddox.il2.builder.Plugin.builder.selectedActor();
        fillComboBox2(startComboBox1);
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(0, true, false);
        com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.addTab(1, tabTarget);
        wH.setValue("" + actorstaticcamera.h, false);
    }

    public void createGUI()
    {
        startComboBox1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.size();
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.add(com.maddox.il2.builder.Plugin.i18n("StaticCamera"));
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
            viewType = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(i, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("showStaticCamers"), null) {

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
        tabTarget = com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.Plugin.i18n("StaticCamera"), gwindowdialogclient);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 4F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Height"), null));
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 11F, 1.0F, 2.0F, 1.3F, com.maddox.il2.builder.Plugin.i18n("[m]"), null));
        gwindowdialogclient.addControl(wH = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 6F, 1.0F, 4F, 1.3F, "") {

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
                com.maddox.il2.builder.ActorStaticCamera actorstaticcamera = (com.maddox.il2.builder.ActorStaticCamera)com.maddox.il2.builder.Plugin.builder.selectedActor();
                int l = actorstaticcamera.h;
                try
                {
                    l = java.lang.Integer.parseInt(getValue());
                }
                catch(java.lang.Exception exception) { }
                if(l < 2)
                    l = 2;
                if(l > 10000)
                    l = 10000;
                actorstaticcamera.h = l;
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static final int hMIN = 2;
    private static final int hMAX = 10000;
    private static final int hDEF = 100;
    protected java.util.ArrayList allActors;
    com.maddox.il2.builder.Item item[] = {
        new Item("StaticCamera")
    };
    private com.maddox.JGP.Point2d p2d;
    private com.maddox.il2.builder.PlMission pluginMission;
    private int startComboBox1;
    private com.maddox.gwindow.GWindowMenuItem viewType;
    private java.lang.String _actorInfo[];
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabTarget;
    com.maddox.gwindow.GWindowEditControl wH;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMisStaticCamera.class, "name", "MisStaticCamera");
    }


}
