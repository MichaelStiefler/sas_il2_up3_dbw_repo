// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMisStatic.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.game.I18N;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric;
import com.maddox.il2.objects.vehicles.planes.PlaneGeneric;
import com.maddox.il2.objects.vehicles.stationary.SirenGeneric;
import com.maddox.il2.objects.vehicles.stationary.SmokeGeneric;
import com.maddox.rts.LDRres;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, PlMission, Builder, BldConfig, 
//            WSelect

public class PlMisStatic extends com.maddox.il2.builder.Plugin
{
    class ViewItem extends com.maddox.gwindow.GWindowMenuItem
    {

        public void execute()
        {
            bChecked = !bChecked;
            viewType(indx);
        }

        int indx;

        public ViewItem(int i, com.maddox.gwindow.GWindowMenu gwindowmenu, java.lang.String s, java.lang.String s1)
        {
            super(gwindowmenu, s, s1);
            indx = i;
        }
    }

    static class Type
    {

        public java.lang.String name;
        public com.maddox.il2.builder.Item item[];

        public Type(java.lang.String s, com.maddox.il2.builder.Item aitem[])
        {
            name = s;
            item = aitem;
        }
    }

    static class Item
    {

        public java.lang.String name;
        public java.lang.Class clazz;
        public int army;
        public com.maddox.il2.engine.ActorSpawn spawn;
        public java.lang.String country;

        public Item(java.lang.String s, java.lang.Class class1, int i)
        {
            name = s;
            clazz = class1;
            army = i;
            if(class1 != null)
            {
                spawn = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get(class1.getName());
                java.lang.String s1 = com.maddox.il2.game.I18N.technic(s);
                if(s1.equals(s))
                {
                    s1 = com.maddox.rts.Property.stringValue(class1, "i18nName", s1);
                    if("Plane".equals(s1))
                    {
                        java.lang.Class class2 = (java.lang.Class)com.maddox.rts.Property.value(class1, "airClass", null);
                        if(class2 != null)
                        {
                            java.lang.String s2 = com.maddox.rts.Property.stringValue(class2, "keyName", null);
                            if(s2 != null)
                                s1 = com.maddox.il2.game.I18N.plane(s2);
                        }
                    }
                }
                com.maddox.rts.Property.set(class1, "i18nName", s1);
            }
        }
    }

    static class Country
    {

        public java.lang.String name;
        public java.lang.String i18nName;

        Country()
        {
        }
    }


    public PlMisStatic()
    {
        allActors = new ArrayList();
        p3d = new Point3d();
        p2d = new Point2d();
        p = new Point3d();
        o = new Orient();
        spawnArg = new ActorSpawnArg();
        viewClasses = new HashMap();
        _actorInfo = new java.lang.String[2];
        pathes = new java.lang.Object[1];
        points = new java.lang.Object[1];
    }

    private void initCountry()
    {
        if(listCountry != null)
            return;
        listCountry = new java.util.ArrayList[3];
        mapCountry = new java.util.HashMap[3];
        for(int i = 0; i < 3; i++)
        {
            listCountry[i] = new ArrayList();
            mapCountry[i] = new HashMap();
        }

        java.util.ResourceBundle resourcebundle;
        try
        {
            resourcebundle = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
        }
        catch(java.lang.Exception exception)
        {
            resourcebundle = null;
        }
        java.util.HashMap hashmap = new HashMap();
        java.util.List list = com.maddox.il2.ai.Regiment.getAll();
        for(int j = 0; j < list.size(); j++)
        {
            com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)list.get(j);
            if(!hashmap.containsKey(regiment.country()))
            {
                int k = regiment.getArmy();
                if(k >= 0 && k <= 2)
                {
                    hashmap.put(regiment.country(), null);
                    com.maddox.il2.builder.Country country = new Country();
                    country.name = regiment.country();
                    if(resourcebundle != null)
                        country.i18nName = resourcebundle.getString(country.name);
                    else
                        country.i18nName = country.name;
                    listCountry[k].add(country);
                    mapCountry[k].put(country.name, new Integer(listCountry[k].size() - 1));
                }
            }
        }

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
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        com.maddox.il2.engine.Render.prepareStates();
        for(int j = 0; j < allActors.size(); j++)
        {
            com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)allActors.get(j);
            if(com.maddox.il2.engine.Actor.isValid(actor1) && actor1.icon != null && viewClasses.containsKey(actor1.getClass()))
            {
                p3d.set(actor1.pos.getAbsPoint());
                if(actor1 instanceof com.maddox.il2.objects.vehicles.stationary.SmokeGeneric)
                    p3d.z = com.maddox.il2.engine.Engine.land().HQ(p3d.x, p3d.y);
                if(com.maddox.il2.builder.Plugin.builder.project2d(p3d, p2d))
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
                        if(com.maddox.il2.builder.Plugin.builder.conf.bShowName)
                        {
                            java.lang.String s = com.maddox.rts.Property.stringValue(actor1.getClass(), "i18nName", "");
                            com.maddox.il2.builder.Plugin.builder.smallFont.output(i, (int)p2d.x + com.maddox.il2.engine.IconDraw.scrSizeX() / 2 + 2, ((int)p2d.y + com.maddox.il2.builder.Plugin.builder.smallFont.height()) - com.maddox.il2.builder.Plugin.builder.smallFont.descender() - com.maddox.il2.engine.IconDraw.scrSizeY() / 2 - 2, 0.0F, s);
                        }
                    }
                }
            }
        }

    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        initCountry();
        int i = sectfile.sectionIndex("Stationary");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            for(int l = 0; l < j; l++)
            {
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, l));
                insert(null, numbertokenizer.next(""), numbertokenizer.next(0), numbertokenizer.next(0.0D), numbertokenizer.next(0.0D), numbertokenizer.next(0.0F), numbertokenizer.next(0.0F), numbertokenizer.next((java.lang.String)null), numbertokenizer.next((java.lang.String)null), numbertokenizer.next((java.lang.String)null));
            }

        }
        i = sectfile.sectionIndex("NStationary");
        if(i >= 0)
        {
            int k = sectfile.vars(i);
            for(int i1 = 0; i1 < k; i1++)
            {
                com.maddox.util.NumberTokenizer numbertokenizer1 = new NumberTokenizer(sectfile.line(i, i1));
                insert(numbertokenizer1.next(""), numbertokenizer1.next(""), numbertokenizer1.next(0), numbertokenizer1.next(0.0D), numbertokenizer1.next(0.0D), numbertokenizer1.next(0.0F), numbertokenizer1.next(0.0F), numbertokenizer1.next((java.lang.String)null), numbertokenizer1.next((java.lang.String)null), numbertokenizer1.next((java.lang.String)null));
            }

        }
        com.maddox.il2.engine.Engine.drawEnv().staticTrimToSize();
    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        com.maddox.il2.engine.Orient orient = new Orient();
        int i = sectfile.sectionAdd("NStationary");
        for(int j = 0; j < allActors.size(); j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)allActors.get(j);
            if(com.maddox.il2.engine.Actor.isValid(actor) && com.maddox.rts.Property.containsValue(actor, "builderSpawn"))
            {
                com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                com.maddox.il2.engine.Orient orient1 = actor.pos.getAbsOrient();
                orient.set(orient1);
                orient.wrap360();
                float f = com.maddox.rts.Property.floatValue(actor, "timeout", 0.0F);
                if(actor instanceof com.maddox.il2.objects.vehicles.planes.PlaneGeneric)
                {
                    java.lang.String s = ((com.maddox.il2.objects.vehicles.planes.PlaneGeneric)actor).country;
                    sectfile.lineAdd(i, actor.name(), com.maddox.rts.ObjIO.classGetName(actor.getClass()) + " " + actor.getArmy() + " " + formatPos(point3d.x, point3d.y, orient.azimut()) + " " + f + " " + s);
                } else
                if((actor instanceof com.maddox.il2.objects.ships.ShipGeneric) || (actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                {
                    int k = com.maddox.rts.Property.intValue(actor, "sleep", 0);
                    int i1 = com.maddox.rts.Property.intValue(actor, "skill", 2);
                    float f1 = com.maddox.rts.Property.floatValue(actor, "slowfire", 1.0F);
                    sectfile.lineAdd(i, actor.name(), com.maddox.rts.ObjIO.classGetName(actor.getClass()) + " " + actor.getArmy() + " " + formatPos(point3d.x, point3d.y, orient.azimut()) + " " + f + " " + k + " " + i1 + " " + f1);
                } else
                if(actor instanceof com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric)
                {
                    int l = com.maddox.rts.Property.intValue(actor, "radius_hide", 0);
                    sectfile.lineAdd(i, actor.name(), com.maddox.rts.ObjIO.classGetName(actor.getClass()) + " " + actor.getArmy() + " " + formatPos(point3d.x, point3d.y, orient.azimut()) + " " + f + " " + l);
                } else
                if(actor instanceof com.maddox.il2.objects.vehicles.stationary.SmokeGeneric)
                    sectfile.lineAdd(i, actor.name(), com.maddox.rts.ObjIO.classGetName(actor.getClass()) + " " + actor.getArmy() + " " + formatPos(point3d.x, point3d.y, orient.azimut()) + " " + formatValue(point3d.z));
                else
                    sectfile.lineAdd(i, actor.name(), com.maddox.rts.ObjIO.classGetName(actor.getClass()) + " " + actor.getArmy() + " " + formatPos(point3d.x, point3d.y, orient.azimut()) + " " + f);
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
            s = i + "_Static";
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

    private com.maddox.il2.engine.Actor insert(java.lang.String s, java.lang.String s1, int i, double d, double d1, 
            float f, float f1, java.lang.String s2, java.lang.String s3, java.lang.String s4)
    {
        com.maddox.il2.engine.ActorSpawn actorspawn;
        java.lang.Class class1 = null;
        try
        {
            class1 = com.maddox.rts.ObjIO.classForName(s1);
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: class '" + s1 + "' not found");
            return null;
        }
        actorspawn = (com.maddox.il2.engine.ActorSpawn)com.maddox.rts.Spawn.get(class1.getName(), false);
        if(actorspawn == null)
        {
            com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: ActorSpawn for '" + s1 + "' not found");
            return null;
        }
        spawnArg.clear();
        if(s != null)
        {
            if("NONAME".equals(s))
                s = null;
            if(com.maddox.il2.engine.Actor.getByName(s) != null)
                s = null;
        }
        if(i < 0 && i >= com.maddox.il2.builder.Builder.armyAmount())
        {
            com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: Wrong actor army '" + i + "'");
            return null;
        }
        spawnArg.army = i;
        spawnArg.armyExist = true;
        spawnArg.country = s2;
        com.maddox.il2.ai.Chief.new_DELAY_WAKEUP = 0.0F;
        com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.new_RADIUS_HIDE = 0.0F;
        if(s2 != null)
            try
            {
                com.maddox.il2.ai.Chief.new_DELAY_WAKEUP = java.lang.Integer.parseInt(s2);
                com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.new_RADIUS_HIDE = com.maddox.il2.ai.Chief.new_DELAY_WAKEUP;
            }
            catch(java.lang.Exception exception1) { }
        com.maddox.il2.ai.Chief.new_SKILL_IDX = 2;
        if(s3 != null)
        {
            try
            {
                com.maddox.il2.ai.Chief.new_SKILL_IDX = java.lang.Integer.parseInt(s3);
            }
            catch(java.lang.Exception exception2) { }
            if(com.maddox.il2.ai.Chief.new_SKILL_IDX < 0 || com.maddox.il2.ai.Chief.new_SKILL_IDX > 3)
            {
                com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: Wrong actor skill '" + com.maddox.il2.ai.Chief.new_SKILL_IDX + "'");
                return null;
            }
        }
        com.maddox.il2.ai.Chief.new_SLOWFIRE_K = 1.0F;
        if(s4 != null)
        {
            try
            {
                com.maddox.il2.ai.Chief.new_SLOWFIRE_K = java.lang.Float.parseFloat(s4);
            }
            catch(java.lang.Exception exception3) { }
            if(com.maddox.il2.ai.Chief.new_SLOWFIRE_K < 0.5F || com.maddox.il2.ai.Chief.new_SLOWFIRE_K > 100F)
            {
                com.maddox.il2.builder.Plugin.builder.tipErr("MissionLoad: Wrong actor slowfire '" + com.maddox.il2.ai.Chief.new_SLOWFIRE_K + "'");
                return null;
            }
        }
        p.set(d, d1, 0.0D);
        spawnArg.point = p;
        o.set(f, 0.0F, 0.0F);
        spawnArg.orient = o;
        com.maddox.il2.engine.Actor actor;
        actor = actorspawn.actorSpawn(spawnArg);
        if(actor instanceof com.maddox.il2.objects.vehicles.stationary.SirenGeneric)
            actor.setArmy(i);
        if(actor instanceof com.maddox.il2.objects.vehicles.stationary.SmokeGeneric)
        {
            p.z = f1;
            actor.pos.setAbs(p);
            actor.pos.reset();
        }
        com.maddox.il2.builder.Plugin.builder.align(actor);
        com.maddox.rts.Property.set(actor, "builderSpawn", "");
        com.maddox.rts.Property.set(actor, "builderPlugin", this);
        if(!actor.isRealTimeFlag())
            actor.interpCancelAll();
        makeName(actor, s);
        allActors.add(actor);
        if(actor instanceof com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric)
        {
            com.maddox.rts.Property.set(actor, "timeout", f1);
            com.maddox.rts.Property.set(actor, "radius_hide", (int)com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric.new_RADIUS_HIDE);
        }
        if((actor instanceof com.maddox.il2.objects.ships.ShipGeneric) || (actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
        {
            com.maddox.rts.Property.set(actor, "sleep", (int)com.maddox.il2.ai.Chief.new_DELAY_WAKEUP);
            com.maddox.rts.Property.set(actor, "skill", com.maddox.il2.ai.Chief.new_SKILL_IDX);
            com.maddox.rts.Property.set(actor, "slowfire", com.maddox.il2.ai.Chief.new_SLOWFIRE_K);
        }
        return actor;
        java.lang.Exception exception4;
        exception4;
        java.lang.System.out.println(exception4.getMessage());
        exception4.printStackTrace();
        return null;
    }

    private com.maddox.il2.engine.Actor insert(com.maddox.il2.engine.ActorSpawn actorspawn, com.maddox.il2.engine.Loc loc, int i, boolean flag, java.lang.String s)
    {
        spawnArg.clear();
        spawnArg.point = loc.getPoint();
        spawnArg.orient = loc.getOrient();
        spawnArg.army = i;
        spawnArg.armyExist = true;
        spawnArg.country = s;
        com.maddox.il2.engine.Actor actor;
        actor = actorspawn.actorSpawn(spawnArg);
        if(actor instanceof com.maddox.il2.objects.vehicles.stationary.SirenGeneric)
            actor.setArmy(i);
        com.maddox.il2.builder.Plugin.builder.align(actor);
        com.maddox.rts.Property.set(actor, "builderSpawn", "");
        com.maddox.rts.Property.set(actor, "builderPlugin", this);
        if(!actor.isRealTimeFlag())
            actor.interpCancelAll();
        if(actor instanceof com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric)
        {
            com.maddox.rts.Property.set(actor, "timeout", 0.0F);
            com.maddox.rts.Property.set(actor, "radius_hide", 0);
        }
        if((actor instanceof com.maddox.il2.objects.ships.ShipGeneric) || (actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
        {
            com.maddox.rts.Property.set(actor, "sleep", 0);
            com.maddox.rts.Property.set(actor, "skill", 2);
            com.maddox.rts.Property.set(actor, "slowfire", 1.0F);
        }
        makeName(actor, null);
        allActors.add(actor);
        if(flag)
            com.maddox.il2.builder.Plugin.builder.setSelected(actor);
        com.maddox.il2.builder.PlMission.setChanged();
        return actor;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
        return null;
    }

    public void insert(com.maddox.il2.engine.Loc loc, boolean flag)
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected();
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        if(i < startComboBox1 || i >= startComboBox1 + type.length)
            return;
        i -= startComboBox1;
        if(j < 0 || j >= type[i].item.length)
        {
            return;
        } else
        {
            com.maddox.il2.engine.ActorSpawn actorspawn = type[i].item[j].spawn;
            insert(actorspawn, loc, type[i].item[j].army, flag, type[i].item[j].country);
            return;
        }
    }

    public void changeType()
    {
        int i = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.getSelected() - startComboBox1;
        int j = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.getSelected();
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        com.maddox.il2.engine.Loc loc = actor.pos.getAbs();
        com.maddox.il2.engine.Actor actor1 = insert(type[i].item[j].spawn, loc, type[i].item[j].army, true, type[i].item[j].country);
        if(com.maddox.il2.builder.Plugin.builder.selectedActor() != actor)
        {
            allActors.remove(actor);
            java.lang.String s = actor.name();
            actor.destroy();
            actor1.setName(s);
        }
    }

    public void configure()
    {
        if(com.maddox.il2.builder.Plugin.getPlugin("Mission") == null)
            throw new RuntimeException("PlMisStatic: plugin 'Mission' not found");
        pluginMission = (com.maddox.il2.builder.PlMission)com.maddox.il2.builder.Plugin.getPlugin("Mission");
        if(sectFile == null)
            throw new RuntimeException("PlMisStatic: field 'sectFile' not defined");
        com.maddox.rts.SectFile sectfile = new SectFile(sectFile, 0);
        int i = sectfile.sections();
        if(i <= 0)
            throw new RuntimeException("PlMisStatic: file '" + sectFile + "' is empty");
        type = new com.maddox.il2.builder.Type[i];
        for(int j = 0; j < i; j++)
        {
            java.lang.String s = sectfile.sectionName(j);
            int k = sectfile.vars(j);
            com.maddox.il2.builder.Item aitem[] = new com.maddox.il2.builder.Item[k];
            for(int l = 0; l < k; l++)
            {
                java.lang.String s1 = sectfile.var(j, l);
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.value(j, l));
                java.lang.String s2 = numbertokenizer.next((java.lang.String)null);
                int i1 = numbertokenizer.next(0, 0, com.maddox.il2.builder.Builder.armyAmount() - 1);
                java.lang.Class class1 = null;
                int j1 = s2.indexOf(' ');
                if(j1 > 0)
                    s2 = s2.substring(0, j1);
                try
                {
                    class1 = com.maddox.rts.ObjIO.classForName(s2);
                }
                catch(java.lang.Exception exception)
                {
                    throw new RuntimeException("PlMisStatic: class '" + s2 + "' not found");
                }
                int k1 = s2.lastIndexOf('$');
                if(k1 >= 0)
                {
                    java.lang.String s3 = s2.substring(0, k1);
                    try
                    {
                        com.maddox.rts.ObjIO.classForName(s3);
                    }
                    catch(java.lang.Exception exception1)
                    {
                        throw new RuntimeException("PlMisStatic: Outer class '" + s3 + "' not found");
                    }
                }
                aitem[l] = new Item(s1, class1, i1);
            }

            type[j] = new Type(s, aitem);
        }

    }

    void viewUpdate()
    {
        for(int i = 0; i < allActors.size(); i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)allActors.get(i);
            if(com.maddox.il2.engine.Actor.isValid(actor) && com.maddox.rts.Property.containsValue(actor, "builderSpawn"))
                actor.drawing(viewClasses.containsKey(actor.getClass()));
        }

        if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.builder.Plugin.builder.selectedActor()) && !com.maddox.il2.builder.Plugin.builder.selectedActor().isDrawing())
            com.maddox.il2.builder.Plugin.builder.setSelected(null);
        if(!com.maddox.il2.builder.Plugin.builder.isFreeView())
            com.maddox.il2.builder.Plugin.builder.repaint();
    }

    void viewType(int i, boolean flag)
    {
        int j = type[i].item.length;
        for(int k = 0; k < j; k++)
            if(flag)
                viewClasses.put(type[i].item[k].clazz, type[i].item[k]);
            else
                viewClasses.remove(type[i].item[k].clazz);

        viewUpdate();
    }

    void viewType(int i)
    {
        viewType(i, viewType[i].bChecked);
    }

    public void viewTypeAll(boolean flag)
    {
        for(int i = 0; i < type.length; i++)
            if(viewType[i].bChecked != flag)
            {
                viewType[i].bChecked = flag;
                viewType(i, flag);
            }

    }

    private void fillComboBox1()
    {
        startComboBox1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.size();
        for(int i = 0; i < type.length; i++)
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.add(com.maddox.il2.game.I18N.technic(type[i].name));

        if(startComboBox1 == 0)
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(0, true, false);
    }

    private void fillComboBox2(int i, int j)
    {
        if(i < startComboBox1 || i >= startComboBox1 + type.length)
            return;
        if(com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType != i)
        {
            com.maddox.il2.builder.Plugin.builder.wSelect.curFilledType = i;
            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.clear(false);
            for(int k = 0; k < type[i - startComboBox1].item.length; k++)
                com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.add(com.maddox.rts.Property.stringValue(type[i - startComboBox1].item[k].clazz, "i18nName", ""));

            com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.setSelected(i, true, false);
        }
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(j, true, false);
        fillComboBox2Render(i, j);
    }

    private void fillComboBox2Render(int i, int j)
    {
        try
        {
            java.lang.Class class1 = type[i - startComboBox1].item[j].clazz;
            if((com.maddox.il2.objects.vehicles.planes.PlaneGeneric.class).isAssignableFrom(class1))
            {
                java.lang.Class class2 = (java.lang.Class)com.maddox.rts.Property.value(class1, "airClass", null);
                int k = type[i - startComboBox1].item[j].army;
                java.lang.String s1 = null;
                if(com.maddox.il2.engine.Actor.isValid(com.maddox.il2.builder.Plugin.builder.selectedActor()))
                {
                    k = com.maddox.il2.builder.Plugin.builder.selectedActor().getArmy();
                    s1 = ((com.maddox.il2.objects.vehicles.planes.PlaneGeneric)com.maddox.il2.builder.Plugin.builder.selectedActor()).country;
                    type[i - startComboBox1].item[j].country = s1;
                    type[i - startComboBox1].item[j].army = k;
                }
                com.maddox.il2.ai.Regiment regiment = com.maddox.il2.ai.Regiment.findFirst(s1, k);
                java.lang.String s2 = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(class2, regiment.country());
                com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(s2, false);
                if(com.maddox.il2.builder.Plugin.builder.wSelect.getHierMesh() != null)
                {
                    com.maddox.il2.objects.air.PaintScheme paintscheme = com.maddox.il2.objects.air.Aircraft.getPropertyPaintScheme(class2, regiment.country());
                    paintscheme.prepareNum(class2, com.maddox.il2.builder.Plugin.builder.wSelect.getHierMesh(), regiment, (int)(java.lang.Math.random() * 3D), (int)(java.lang.Math.random() * 3D), (int)(java.lang.Math.random() * 98D + 1.0D));
                }
            } else
            {
                java.lang.String s = com.maddox.rts.Property.stringValue(class1, "meshName", null);
                if(s == null)
                {
                    java.lang.reflect.Method method = class1.getMethod("getMeshNameForEditor", null);
                    s = (java.lang.String)method.invoke(class1, null);
                }
                com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(s, true);
            }
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.builder.Plugin.builder.wSelect.setMesh(null, true);
        }
    }

    public java.lang.String[] actorInfo(com.maddox.il2.engine.Actor actor)
    {
        java.lang.Class class1 = actor.getClass();
        for(int i = 0; i < type.length; i++)
        {
            for(int j = 0; j < type[i].item.length; j++)
                if(class1 == type[i].item[j].clazz)
                {
                    _actorInfo[0] = com.maddox.il2.game.I18N.technic(type[i].name) + "." + com.maddox.rts.Property.stringValue(type[i].item[j].clazz, "i18nName", "");
                    float f = com.maddox.rts.Property.floatValue(actor, "timeout", 0.0F);
                    if(f > 0.0F)
                        _actorInfo[1] = com.maddox.il2.builder.Plugin.timeSecToString(f * 60F + (float)(int)(com.maddox.il2.ai.World.getTimeofDay() * 60F * 60F));
                    else
                        _actorInfo[1] = null;
                    return _actorInfo;
                }

        }

        return null;
    }

    public void syncSelector()
    {
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        java.lang.Class class1 = actor.getClass();
        for(int i = 0; i < type.length; i++)
        {
            for(int j = 0; j < type[i].item.length; j++)
                if(class1 == type[i].item[j].clazz)
                {
                    fillComboBox2(i + startComboBox1, j);
                    com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.addTab(1, tabActor);
                    wName.cap.set(com.maddox.rts.Property.stringValue(type[i].item[j].clazz, "i18nName", ""));
                    wArmy.setSelected(actor.getArmy(), true, false);
                    if(actor instanceof com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric)
                    {
                        float f = com.maddox.rts.Property.floatValue(actor, "timeout", 0.0F);
                        wTimeOutH.setValue("" + (int)((f / 60F) % 24F), false);
                        wTimeOutM.setValue("" + (int)(f % 60F), false);
                        wLTimeOutH.showWindow();
                        wTimeOutH.showWindow();
                        wLTimeOutM.showWindow();
                        wTimeOutM.showWindow();
                        int l = com.maddox.rts.Property.intValue(actor, "radius_hide", 0);
                        wL1RHide.showWindow();
                        wL2RHide.showWindow();
                        wRHide.showWindow();
                        wRHide.setValue("" + l, false);
                    } else
                    {
                        wLTimeOutH.hideWindow();
                        wTimeOutH.hideWindow();
                        wLTimeOutM.hideWindow();
                        wTimeOutM.hideWindow();
                        wL1RHide.hideWindow();
                        wL2RHide.hideWindow();
                        wRHide.hideWindow();
                    }
                    if((com.maddox.il2.objects.vehicles.planes.PlaneGeneric.class).isAssignableFrom(class1))
                    {
                        com.maddox.il2.objects.vehicles.planes.PlaneGeneric planegeneric = (com.maddox.il2.objects.vehicles.planes.PlaneGeneric)actor;
                        fillCountry(actor.getArmy(), planegeneric.country);
                        wLCountry.showWindow();
                        wCountry.showWindow();
                    } else
                    {
                        wLCountry.hideWindow();
                        wCountry.hideWindow();
                    }
                    if((actor instanceof com.maddox.il2.objects.ships.ShipGeneric) || (actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                    {
                        wLSleepM.showWindow();
                        wSleepM.showWindow();
                        wLSleepS.showWindow();
                        wSleepS.showWindow();
                        int k = com.maddox.rts.Property.intValue(actor, "sleep", 0);
                        wSleepM.setValue("" + (k / 60) % 99, false);
                        wSleepS.setValue("" + k % 60, false);
                        wLSkill.showWindow();
                        wSkill.showWindow();
                        int i1 = com.maddox.rts.Property.intValue(actor, "skill", 2);
                        wSkill.setSelected(i1, true, false);
                        wLSlowfire.showWindow();
                        wSlowfire.showWindow();
                        float f1 = com.maddox.rts.Property.floatValue(actor, "slowfire", 1.0F);
                        wSlowfire.setValue("" + f1);
                    } else
                    {
                        wLSleepM.hideWindow();
                        wSleepM.hideWindow();
                        wLSleepS.hideWindow();
                        wSleepS.hideWindow();
                        wLSkill.hideWindow();
                        wSkill.hideWindow();
                        wLSlowfire.hideWindow();
                        wSlowfire.hideWindow();
                    }
                    return;
                }

        }

    }

    private java.lang.String fillCountry(int i, java.lang.String s)
    {
        initCountry();
        wCountry.clear(false);
        java.util.ArrayList arraylist = listCountry[i];
        for(int j = 0; j < arraylist.size(); j++)
        {
            com.maddox.il2.builder.Country country = (com.maddox.il2.builder.Country)arraylist.get(j);
            wCountry.add(country.i18nName);
        }

        if(s != null && !mapCountry[i].containsKey(s))
            s = null;
        if(s == null)
            switch(i)
            {
            case 0: // '\0'
                s = "nn";
                break;

            case 1: // '\001'
                s = "ru";
                break;

            case 2: // '\002'
                s = "de";
                break;
            }
        java.lang.Integer integer = (java.lang.Integer)mapCountry[i].get(s);
        wCountry.setSelected(integer.intValue(), true, false);
        return s;
    }

    private void controlResized(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient, com.maddox.gwindow.GWindow gwindow)
    {
        if(gwindow == null)
        {
            return;
        } else
        {
            gwindow.setSize(gwindowdialogclient.win.dx - gwindow.win.x - gwindowdialogclient.lAF().metric(1.0F), gwindow.win.dy);
            return;
        }
    }

    private void editResized(com.maddox.gwindow.GWindowDialogClient gwindowdialogclient)
    {
        controlResized(gwindowdialogclient, wName);
        controlResized(gwindowdialogclient, wArmy);
        controlResized(gwindowdialogclient, wCountry);
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
                if(i1 < startComboBox1 || i1 >= startComboBox1 + type.length)
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
            i = type.length - 1;
            viewType = new com.maddox.il2.builder.ViewItem[type.length];
            for(; i >= 0; i--)
            {
                com.maddox.il2.builder.ViewItem viewitem = null;
                if("de".equals(com.maddox.rts.RTSConf.cur.locale.getLanguage()))
                    viewitem = (com.maddox.il2.builder.ViewItem)com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(j, new ViewItem(i, com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.game.I18N.technic(type[i].name) + " " + com.maddox.il2.builder.Plugin.i18n("show"), null));
                else
                    viewitem = (com.maddox.il2.builder.ViewItem)com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(j, new ViewItem(i, com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("show") + " " + com.maddox.il2.game.I18N.technic(type[i].name), null));
                viewitem.bChecked = true;
                viewType[i] = viewitem;
                viewType(i, true);
            }

        }
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.create(new com.maddox.gwindow.GWindowDialogClient() {

            public void resized()
            {
                super.resized();
                editResized(this);
            }

        }
);
        tabActor = com.maddox.il2.builder.Plugin.builder.wSelect.tabsClient.createTab(com.maddox.il2.builder.Plugin.i18n("StaticActor"), gwindowdialogclient);
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Name"), null));
        gwindowdialogclient.addLabel(wName = new GWindowLabel(gwindowdialogclient, 9F, 1.0F, 7F, 1.3F, "", null));
        gwindowdialogclient.addLabel(new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Army"), null));
        gwindowdialogclient.addControl(wArmy = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 3F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                for(int k = 0; k < com.maddox.il2.builder.Builder.armyAmount(); k++)
                    add(com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(k)));

            }

            public boolean notify(int k, int l)
            {
                if(k != 2)
                    return false;
                com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
                int i1 = getSelected();
                actor.setArmy(i1);
                com.maddox.il2.builder.PlMission.setChanged();
                com.maddox.il2.builder.PlMission.checkShowCurrentArmy();
                java.lang.Class class1 = actor.getClass();
                if((com.maddox.il2.builder.PlMisStatic.class$com$maddox$il2$objects$vehicles$planes$PlaneGeneric != null ? com.maddox.il2.builder.PlMisStatic.class$com$maddox$il2$objects$vehicles$planes$PlaneGeneric : (com.maddox.il2.builder.PlMisStatic.class$com$maddox$il2$objects$vehicles$planes$PlaneGeneric = com.maddox.il2.builder.PlMisStatic._mthclass$("com.maddox.il2.objects.vehicles.planes.PlaneGeneric"))).isAssignableFrom(class1))
                {
                    com.maddox.il2.objects.vehicles.planes.PlaneGeneric planegeneric = (com.maddox.il2.objects.vehicles.planes.PlaneGeneric)actor;
                    java.lang.String s = fillCountry(i1, planegeneric.country);
                    planegeneric.country = s;
                    planegeneric.activateMesh(true);
                    for(int j1 = 0; j1 < type.length; j1++)
                    {
                        for(int k1 = 0; k1 < type[j1].item.length; k1++)
                        {
                            if(class1 != type[j1].item[k1].clazz)
                                continue;
                            fillComboBox2Render(j1 + startComboBox1, k1);
                            break;
                        }

                    }

                }
                return false;
            }

        }
);
        gwindowdialogclient.addLabel(wLCountry = new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 7F, 1.3F, com.maddox.il2.game.I18N.gui("neta.Country"), null));
        gwindowdialogclient.addControl(wCountry = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 5F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
            }

            public boolean notify(int k, int l)
            {
                if(k != 2)
                    return false;
                com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
                java.lang.Class class1 = actor.getClass();
                if(!(com.maddox.il2.builder.PlMisStatic.class$com$maddox$il2$objects$vehicles$planes$PlaneGeneric != null ? com.maddox.il2.builder.PlMisStatic.class$com$maddox$il2$objects$vehicles$planes$PlaneGeneric : (com.maddox.il2.builder.PlMisStatic.class$com$maddox$il2$objects$vehicles$planes$PlaneGeneric = com.maddox.il2.builder.PlMisStatic._mthclass$("com.maddox.il2.objects.vehicles.planes.PlaneGeneric"))).isAssignableFrom(class1))
                    return false;
                int i1 = getSelected();
                com.maddox.il2.builder.Country country = (com.maddox.il2.builder.Country)listCountry[actor.getArmy()].get(i1);
                com.maddox.il2.objects.vehicles.planes.PlaneGeneric planegeneric = (com.maddox.il2.objects.vehicles.planes.PlaneGeneric)actor;
                planegeneric.country = country.name;
                planegeneric.activateMesh(true);
                for(int j1 = 0; j1 < type.length; j1++)
                {
                    for(int k1 = 0; k1 < type[j1].item.length; k1++)
                    {
                        if(class1 != type[j1].item[k1].clazz)
                            continue;
                        fillComboBox2Render(j1 + startComboBox1, k1);
                        break;
                    }

                }

                return false;
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

            public boolean notify(int k, int l)
            {
                if(k != 2)
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

            public boolean notify(int k, int l)
            {
                if(k != 2)
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
        gwindowdialogclient1.addLabel(wL1RHide = new GWindowLabel(gwindowdialogclient1, 1.0F, 7F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("RHide"), null));
        gwindowdialogclient1.addLabel(wL2RHide = new GWindowLabel(gwindowdialogclient1, 14F, 7F, 4F, 1.3F, com.maddox.il2.builder.Plugin.i18n("[M]"), null));
        gwindowdialogclient1.addControl(wRHide = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient1, 9F, 7F, 4F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int k, int l)
            {
                if(k != 2)
                    return false;
                com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
                int i1 = com.maddox.rts.Property.intValue(actor, "radius_hide", 0);
                java.lang.String s = getValue();
                try
                {
                    i1 = (int)java.lang.Double.parseDouble(s);
                    if(i1 < 0)
                    {
                        i1 = 0;
                        setValue("" + i1, false);
                    }
                }
                catch(java.lang.Exception exception)
                {
                    setValue("" + i1, false);
                    return false;
                }
                com.maddox.rts.Property.set(actor, "radius_hide", i1);
                com.maddox.il2.builder.PlMission.setChanged();
                return false;
            }

        }
);
        gwindowdialogclient.addLabel(wLSleepM = new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Sleep"), null));
        gwindowdialogclient.addControl(wSleepM = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 9F, 5F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int k, int l)
            {
                if(k != 2)
                {
                    return false;
                } else
                {
                    getSleep();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wLSleepS = new GWindowLabel(gwindowdialogclient, 11.2F, 5F, 1.0F, 1.3F, ":", null));
        gwindowdialogclient.addControl(wSleepS = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 11.5F, 5F, 2.0F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = true;
                bDelayedNotify = true;
            }

            public boolean notify(int k, int l)
            {
                if(k != 2)
                {
                    return false;
                } else
                {
                    getSleep();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wLSkill = new GWindowLabel(gwindowdialogclient, 1.0F, 7F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Skill"), null));
        gwindowdialogclient.addControl(wSkill = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 9F, 7F, 7F) {

            public void afterCreated()
            {
                super.afterCreated();
                setEditable(false);
                add(com.maddox.il2.builder.Plugin.i18n("Rookie"));
                add(com.maddox.il2.builder.Plugin.i18n("Average"));
                add(com.maddox.il2.builder.Plugin.i18n("Veteran"));
                add(com.maddox.il2.builder.Plugin.i18n("Ace"));
            }

            public boolean notify(int k, int l)
            {
                if(k != 2)
                {
                    return false;
                } else
                {
                    com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
                    com.maddox.rts.Property.set(actor, "skill", getSelected());
                    com.maddox.il2.builder.PlMission.setChanged();
                    return false;
                }
            }

        }
);
        gwindowdialogclient.addLabel(wLSlowfire = new GWindowLabel(gwindowdialogclient, 1.0F, 9F, 7F, 1.3F, com.maddox.il2.builder.Plugin.i18n("Slowfire"), null));
        gwindowdialogclient.addControl(wSlowfire = new com.maddox.gwindow.GWindowEditControl(gwindowdialogclient, 9F, 9F, 3F, 1.3F, "") {

            public void afterCreated()
            {
                super.afterCreated();
                bNumericOnly = bNumericFloat = true;
                bDelayedNotify = true;
            }

            public boolean notify(int k, int l)
            {
                if(k != 2)
                    return false;
                java.lang.String s = getValue();
                float f = 1.0F;
                try
                {
                    f = java.lang.Float.parseFloat(s);
                }
                catch(java.lang.Exception exception) { }
                if(f < 0.5F)
                    f = 0.5F;
                if(f > 100F)
                    f = 100F;
                setValue("" + f, false);
                com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
                com.maddox.rts.Property.set(actor, "slowfire", f);
                com.maddox.il2.builder.PlMission.setChanged();
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
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        com.maddox.rts.Property.set(actor, "timeout", f);
        com.maddox.il2.builder.PlMission.setChanged();
    }

    private void getSleep()
    {
        java.lang.String s = wSleepM.getValue();
        double d = 0.0D;
        try
        {
            d = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception) { }
        if(d < 0.0D)
            d = 0.0D;
        if(d > 99D)
            d = 99D;
        s = wSleepS.getValue();
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
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        com.maddox.rts.Property.set(actor, "sleep", (int)(d * 60D + d1));
        com.maddox.il2.builder.PlMission.setChanged();
    }

    private java.util.ArrayList listCountry[];
    private java.util.HashMap mapCountry[];
    protected java.util.ArrayList allActors;
    com.maddox.il2.builder.Type type[];
    private com.maddox.JGP.Point3d p3d;
    private com.maddox.JGP.Point2d p2d;
    private com.maddox.JGP.Point3d p;
    private com.maddox.il2.engine.Orient o;
    private com.maddox.il2.engine.ActorSpawnArg spawnArg;
    private com.maddox.il2.builder.PlMission pluginMission;
    private int startComboBox1;
    com.maddox.il2.builder.ViewItem viewType[];
    java.util.HashMap viewClasses;
    private java.lang.String _actorInfo[];
    com.maddox.gwindow.GWindowTabDialogClient.Tab tabActor;
    com.maddox.gwindow.GWindowLabel wName;
    com.maddox.gwindow.GWindowComboControl wArmy;
    com.maddox.gwindow.GWindowLabel wLTimeOutH;
    com.maddox.gwindow.GWindowEditControl wTimeOutH;
    com.maddox.gwindow.GWindowLabel wLTimeOutM;
    com.maddox.gwindow.GWindowEditControl wTimeOutM;
    com.maddox.gwindow.GWindowLabel wLCountry;
    com.maddox.gwindow.GWindowComboControl wCountry;
    com.maddox.gwindow.GWindowLabel wLSleepM;
    com.maddox.gwindow.GWindowLabel wLSleepS;
    com.maddox.gwindow.GWindowEditControl wSleepM;
    com.maddox.gwindow.GWindowEditControl wRHide;
    com.maddox.gwindow.GWindowLabel wL1RHide;
    com.maddox.gwindow.GWindowLabel wL2RHide;
    com.maddox.gwindow.GWindowEditControl wSleepS;
    com.maddox.gwindow.GWindowLabel wLSkill;
    com.maddox.gwindow.GWindowComboControl wSkill;
    com.maddox.gwindow.GWindowLabel wLSlowfire;
    com.maddox.gwindow.GWindowEditControl wSlowfire;
    private java.lang.Object pathes[];
    private java.lang.Object points[];

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMisStatic.class, "name", "MisStatic");
    }








}
