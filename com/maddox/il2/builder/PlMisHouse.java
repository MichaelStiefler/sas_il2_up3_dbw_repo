// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMisHouse.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.ai.Army;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.objects.buildings.House;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, PlMission, Builder, BldConfig, 
//            WSelect

public class PlMisHouse extends com.maddox.il2.builder.Plugin
{
    class ViewType extends com.maddox.gwindow.GWindowMenuItem
    {

        public void execute()
        {
            bChecked = !bChecked;
            viewTypeAll(bChecked);
        }

        public ViewType(com.maddox.gwindow.GWindowMenu gwindowmenu, java.lang.String s, java.lang.String s1)
        {
            super(gwindowmenu, s, s1);
        }
    }

    static class Type
    {

        public int indx;
        public java.lang.String name;
        public com.maddox.il2.engine.ActorSpawn spawn;
        public java.lang.String fullClassName;
        public java.lang.String shortClassName;

        public Type(int i, java.lang.String s, java.lang.String s1)
        {
            indx = i;
            name = s;
            fullClassName = s1;
            shortClassName = fullClassName.substring("com.maddox.il2.objects.buildings.".length());
            spawn = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get_WithSoftClass(fullClassName);
        }
    }


    public PlMisHouse()
    {
        allActors = new ArrayList();
        allTypes = new HashMap();
        houseIcon = null;
        p2d = new Point2d();
        p = new Point3d();
        o = new Orient();
        spawnArg = new ActorSpawnArg();
        bView = true;
        viewClasses = new HashMap();
        _actorInfo = new java.lang.String[1];
    }

    public void mapLoaded()
    {
        deleteAll();
    }

    public void deleteAll()
    {
        for(int i = 0; i < allActors.size(); i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)allActors.get(i);
            if(com.maddox.il2.engine.Actor.isValid(actor))
                actor.destroy();
        }

        allActors.clear();
    }

    public void delete(com.maddox.il2.engine.Actor actor)
    {
        allActors.remove(actor);
        actor.destroy();
    }

    public void renderMap2D()
    {
        if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            return;
        if(!bView)
            return;
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        com.maddox.il2.engine.Render.prepareStates();
        for(int j = 0; j < allActors.size(); j++)
        {
            com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)allActors.get(j);
            if(com.maddox.il2.engine.Actor.isValid(actor1) && actor1.icon != null && com.maddox.il2.builder.Plugin.builder.project2d(actor1.pos.getAbsPoint(), p2d))
            {
                int k = actor1.getArmy();
                boolean flag = com.maddox.il2.builder.Plugin.builder.conf.bShowArmy[k];
                if(flag)
                {
                    int i;
                    if(actor1 == actor)
                        i = com.maddox.il2.builder.Builder.colorSelected();
                    else
                        i = com.maddox.il2.ai.Army.color(actor1.getArmy());
                    com.maddox.il2.engine.IconDraw.setColor(i);
                    com.maddox.il2.engine.IconDraw.render(actor1, p2d.x, p2d.y);
                }
            }
        }

    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionIndex("Buildings");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            for(int k = 0; k < j; k++)
            {
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
                numbertokenizer.next("");
                insert("com.maddox.il2.objects.buildings." + numbertokenizer.next(""), numbertokenizer.next(1) == 1, numbertokenizer.next(0.0D), numbertokenizer.next(0.0D), numbertokenizer.next(0.0F), false);
            }

        }
    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        com.maddox.il2.engine.Orient orient = new Orient();
        int i = sectfile.sectionAdd("Buildings");
        for(int j = 0; j < allActors.size(); j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)allActors.get(j);
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                com.maddox.il2.engine.Orient orient1 = actor.pos.getAbsOrient();
                orient.set(orient1);
                orient.wrap360();
                com.maddox.il2.builder.Type type1 = (com.maddox.il2.builder.Type)com.maddox.rts.Property.value(actor, "builderType", null);
                sectfile.lineAdd(i, j + "_bld", type1.shortClassName + " " + (actor.isAlive() ? "1 " : "0 ") + formatPos(point3d.x, point3d.y, orient.azimut()));
            }
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

    private com.maddox.il2.engine.Actor insert(java.lang.String s, boolean flag, double d, double d1, float f, 
            boolean flag1)
    {
        com.maddox.il2.engine.ActorSpawn actorspawn;
        actorspawn = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get_WithSoftClass(s, false);
        if(actorspawn == null)
        {
            com.maddox.il2.builder.Plugin.builder.tipErr("PlMisHouse: ActorSpawn for '" + s + "' not found");
            return null;
        }
        spawnArg.clear();
        p.set(d, d1, 0.0D);
        spawnArg.point = p;
        o.set(f, 0.0F, 0.0F);
        spawnArg.orient = o;
        com.maddox.il2.engine.Actor actor;
        actor = actorspawn.actorSpawn(spawnArg);
        if(!flag)
            actor.setDiedFlag(true);
        com.maddox.rts.Property.set(actor, "builderSpawn", "");
        com.maddox.rts.Property.set(actor, "builderPlugin", this);
        com.maddox.il2.builder.Type type1 = (com.maddox.il2.builder.Type)allTypes.get(s);
        com.maddox.rts.Property.set(actor, "builderType", type1);
        actor.icon = houseIcon;
        allActors.add(actor);
        com.maddox.il2.builder.Plugin.builder.align(actor);
        if(flag1)
            com.maddox.il2.builder.Plugin.builder.setSelected(actor);
        com.maddox.il2.builder.PlMission.setChanged();
        return actor;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
        return null;
    }

    private com.maddox.il2.engine.Actor insert(com.maddox.il2.builder.Type type1, com.maddox.il2.engine.Loc loc, boolean flag)
    {
        spawnArg.clear();
        spawnArg.point = loc.getPoint();
        spawnArg.orient = loc.getOrient();
        return insert(type1.fullClassName, true, loc.getPoint().x, loc.getPoint().y, loc.getOrient().getAzimut(), flag);
    }

    public void insert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(i != startComboBox1)
            return;
        if(j < 0 || j >= type.length)
        {
            return;
        } else
        {
            insert(type[j], loc, flag);
            return;
        }
    }

    public void changeType()
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected() - startComboBox1;
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        com.maddox.il2.engine.Loc loc = actor.pos.getAbs();
        com.maddox.il2.engine.Actor actor1 = insert(type[j], loc, true);
        if(com.maddox.il2.builder.Plugin.builder.selectedActor() != actor)
        {
            allActors.remove(actor);
            actor.destroy();
            com.maddox.il2.builder.PlMission.setChanged();
        }
    }

    public void changeType(boolean flag, boolean flag1)
    {
        if(flag1)
            return;
        if(com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected() != startComboBox1)
            return;
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(flag)
        {
            if(++i >= type.length)
                i = 0;
        } else
        if(--i < 0)
            i = type.length - 1;
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        com.maddox.il2.engine.Loc loc = actor.pos.getAbs();
        com.maddox.il2.engine.Actor actor1 = insert(type[i], loc, true);
        if(com.maddox.il2.builder.Plugin.builder.selectedActor() != actor)
        {
            allActors.remove(actor);
            actor.destroy();
            com.maddox.il2.builder.PlMission.setChanged();
        }
        fillComboBox2(startComboBox1, i);
    }

    public void configure()
    {
        if(com.maddox.il2.builder.Plugin.getPlugin("Mission") == null)
            throw new RuntimeException("PlMisHouse: plugin 'Mission' not found");
        pluginMission = (com.maddox.il2.builder.PlMission)com.maddox.il2.builder.Plugin.getPlugin("Mission");
        if(sectFile == null)
            throw new RuntimeException("PlMisHouse: field 'sectFile' not defined");
        com.maddox.rts.SectFile sectfile = new SectFile(sectFile, 0);
        int i = sectfile.sections();
        if(i <= 0)
            throw new RuntimeException("PlMisHouse: file '" + sectFile + "' is empty");
        com.maddox.il2.builder.Type atype[] = new com.maddox.il2.builder.Type[i];
        int j = 0;
        for(int k = 0; k < i; k++)
        {
            java.lang.String s = sectfile.sectionName(k);
            if(s.indexOf("House$") >= 0)
            {
                java.lang.String s1 = s;
                java.lang.String s2 = "";
                int i1 = s.lastIndexOf('$');
                if(i1 >= 0)
                {
                    s1 = s.substring(0, i1);
                    s2 = s.substring(i1 + 1);
                }
                java.lang.Class class1 = null;
                try
                {
                    class1 = com.maddox.rts.ObjIO.classForName(s1);
                }
                catch(java.lang.Exception exception)
                {
                    throw new RuntimeException("PlMisHouse: class '" + s1 + "' not found");
                }
                if(i1 >= 0)
                    s = class1.getName() + "$" + s2;
                else
                    s = class1.getName();
                atype[j] = new Type(j, com.maddox.il2.builder.Plugin.i18n("building") + " " + j, s);
                allTypes.put(s, atype[j]);
                j++;
            }
        }

        type = new com.maddox.il2.builder.Type[j];
        for(int l = 0; l < j; l++)
        {
            type[l] = atype[l];
            atype[l] = null;
        }

        atype = null;
        houseIcon = com.maddox.il2.engine.IconDraw.get("icons/objHouse.mat");
    }

    void viewUpdate()
    {
        for(int i = 0; i < allActors.size(); i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)allActors.get(i);
            if(com.maddox.il2.engine.Actor.isValid(actor))
                actor.drawing(bView);
        }

        if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.builder.Plugin.builder.selectedActor()) && !com.maddox.il2.builder.Plugin.builder.selectedActor().isDrawing())
            com.maddox.il2.builder.Plugin.builder.setSelected(null);
        if(!com.maddox.il2.builder.Plugin.builder.isFreeView())
            com.maddox.il2.builder.Plugin.builder.repaint();
    }

    public void viewTypeAll(boolean flag)
    {
        bView = flag;
        viewCheckBox.bChecked = bView;
        viewUpdate();
    }

    private void fillComboBox1()
    {
        startComboBox1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.size();
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.add(com.maddox.il2.builder.Plugin.i18n("buildings"));
        if(startComboBox1 == 0)
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(0, true, false);
    }

    private void fillComboBox2(int i, int j)
    {
        if(i != startComboBox1)
            return;
        if(com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType != i)
        {
            com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType = i;
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.clear(false);
            for(int k = 0; k < type.length; k++)
                com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.add(type[k].name);

            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(i, true, false);
        }
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(j, true, false);
        fillComboBox2Render(i, j);
    }

    private void fillComboBox2Render(int i, int j)
    {
        try
        {
            com.maddox.il2.builder.Type type1 = type[j];
            com.maddox.il2.objects.buildings.House.SPAWN spawn = (com.maddox.il2.objects.buildings.House.SPAWN)type1.spawn;
            com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(spawn.prop.MESH0_NAME, true);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(null, true);
        }
    }

    public java.lang.String[] actorInfo(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.builder.Type type1 = (com.maddox.il2.builder.Type)com.maddox.rts.Property.value(actor, "builderType", null);
        if(type1 != null)
        {
            _actorInfo[0] = type1.name;
            return _actorInfo;
        } else
        {
            return null;
        }
    }

    public void syncSelector()
    {
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        com.maddox.il2.builder.Type type1 = (com.maddox.il2.builder.Type)com.maddox.rts.Property.value(actor, "builderType", null);
        if(type1 == null)
        {
            return;
        } else
        {
            fillComboBox2(startComboBox1, type1.indx);
            return;
        }
    }

    public void createGUI()
    {
        fillComboBox1();
        fillComboBox2(0, 0);
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int k, int l)
            {
                int i1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
                if(i1 >= 0 && k == 2)
                    fillComboBox2(i1, 0);
                return false;
            }

        }
);
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.addNotifyListener(new com.maddox.gwindow.GNotifyListener() {

            public boolean notify(com.maddox.gwindow.GWindow gwindow, int k, int l)
            {
                if(k != 2)
                    return false;
                int i1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
                if(i1 != startComboBox1)
                {
                    return false;
                } else
                {
                    int j1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
                    fillComboBox2Render(i1, j1);
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
            int j = i;
            if("de".equals(com.maddox.rts.RTSConf.cur.locale.getLanguage()))
                viewCheckBox = (com.maddox.il2.builder.ViewType)com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(i, new ViewType(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("buildings") + " " + com.maddox.il2.builder.Plugin.i18n("show"), null));
            else
                viewCheckBox = (com.maddox.il2.builder.ViewType)com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(i, new ViewType(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("show") + " " + com.maddox.il2.builder.Plugin.i18n("buildings"), null));
            viewTypeAll(true);
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private java.util.ArrayList allActors;
    private java.util.HashMap allTypes;
    private com.maddox.il2.engine.Mat houseIcon;
    com.maddox.il2.builder.Type type[];
    private com.maddox.JGP.Point2d p2d;
    private com.maddox.JGP.Point3d p;
    private com.maddox.il2.engine.Orient o;
    private com.maddox.il2.engine.ActorSpawnArg spawnArg;
    private com.maddox.il2.builder.PlMission pluginMission;
    private int startComboBox1;
    private boolean bView;
    private com.maddox.il2.builder.ViewType viewCheckBox;
    java.util.HashMap viewClasses;
    private java.lang.String _actorInfo[];
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabActor;
    com.maddox.gwindow.GWindowLabel wName;
    com.maddox.gwindow.GWindowComboControl wArmy;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMisHouse.class, "name", "MisHouse");
    }



}
