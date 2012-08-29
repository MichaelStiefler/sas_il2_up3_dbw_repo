// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMisFront.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Front;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.I18N;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, ActorFrontMarker, PlMission, Builder, 
//            BldConfig, WSelect

public class PlMisFront extends com.maddox.il2.builder.Plugin
{
    static class Item
    {

        public int army;

        public Item(int i)
        {
            army = i;
        }
    }


    public PlMisFront()
    {
        allActors = new ArrayList();
        item = new com.maddox.il2.builder.Item[com.maddox.il2.ai.Army.amountNet() - 1];
        for(int i = 0; i < item.length; i++)
            item[i] = new Item(i + 1);

        markers = new ArrayList();
        buf = new byte[16384];
        _mask = new byte[4356];
        p2d = new Point2d();
        _actorInfo = new java.lang.String[1];
    }

    private void tilesUpdate()
    {
    }

    public void preRenderMap2D()
    {
        if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            return;
        if(!viewType.bChecked)
            return;
        com.maddox.il2.ai.Front.preRender(true);
        if(com.maddox.il2.ai.Front.isMarkersUpdated())
            tilesUpdate();
    }

    public void renderMap2DAfter()
    {
        if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            return;
        if(!viewType.bChecked)
            return;
        com.maddox.il2.ai.Front.render(com.maddox.il2.builder.Plugin.builder.isView3D());
        com.maddox.il2.engine.IconDraw.setScrSize(com.maddox.il2.builder.Plugin.builder.conf.iconSize * 2, com.maddox.il2.builder.Plugin.builder.conf.iconSize * 2);
        com.maddox.il2.engine.Actor actor = com.maddox.il2.builder.Plugin.builder.selectedActor();
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorFrontMarker actorfrontmarker = (com.maddox.il2.builder.ActorFrontMarker)allActors.get(j);
            if(com.maddox.il2.builder.Plugin.builder.project2d(actorfrontmarker.pos.getAbsPoint(), p2d))
            {
                int k = com.maddox.il2.ai.Army.color(actorfrontmarker.getArmy());
                if(actorfrontmarker == actor)
                    k = com.maddox.il2.builder.Builder.colorSelected();
                com.maddox.il2.engine.IconDraw.setColor(k);
                com.maddox.il2.engine.IconDraw.render(actorfrontmarker, p2d.x + (double)((com.maddox.il2.builder.Plugin.builder.conf.iconSize * 2) / 3), p2d.y + (double)((com.maddox.il2.builder.Plugin.builder.conf.iconSize * 2) / 3));
            }
        }

        com.maddox.il2.engine.IconDraw.setScrSize(com.maddox.il2.builder.Plugin.builder.conf.iconSize, com.maddox.il2.builder.Plugin.builder.conf.iconSize);
    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        int i = allActors.size();
        if(i == 0)
            return true;
        int j = sectfile.sectionAdd("FrontMarker");
        for(int k = 0; k < i; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)allActors.get(k);
            sectfile.lineAdd(j, "FrontMarker" + k + " " + fmt(actor.pos.getAbsPoint().x) + " " + fmt(actor.pos.getAbsPoint().y) + " " + actor.getArmy());
        }

        return true;
    }

    private java.lang.String fmt(double d)
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
        int i = sectfile.sectionIndex("FrontMarker");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            com.maddox.JGP.Point3d point3d = new Point3d();
            for(int k = 0; k < j; k++)
            {
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.line(i, k));
                java.lang.String s = numbertokenizer.next((java.lang.String)null);
                point3d.x = numbertokenizer.next(0.0D);
                point3d.y = numbertokenizer.next(0.0D);
                int l = numbertokenizer.next(1, 1, com.maddox.il2.ai.Army.amountNet() - 1);
                if(l <= com.maddox.il2.ai.Army.amountSingle() - 1)
                    insert(point3d, false, l);
            }

        }
    }

    public void deleteAll()
    {
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)allActors.get(j);
            actor.destroy();
        }

        allActors.clear();
        com.maddox.il2.ai.Front.setMarkersChanged();
    }

    public void delete(com.maddox.il2.engine.Actor actor)
    {
        allActors.remove(actor);
        actor.destroy();
        com.maddox.il2.ai.Front.setMarkersChanged();
    }

    private com.maddox.il2.builder.ActorFrontMarker insert(com.maddox.JGP.Point3d point3d, boolean flag, int i)
    {
        com.maddox.il2.builder.ActorFrontMarker actorfrontmarker;
        java.lang.String s = com.maddox.il2.builder.Plugin.i18n("FrontMarker") + " " + com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(i));
        actorfrontmarker = new ActorFrontMarker(s, i, point3d);
        com.maddox.rts.Property.set(actorfrontmarker, "builderSpawn", "");
        com.maddox.rts.Property.set(actorfrontmarker, "builderPlugin", this);
        allActors.add(actorfrontmarker);
        if(flag)
            com.maddox.il2.builder.Plugin.builder.setSelected(actorfrontmarker);
        com.maddox.il2.builder.PlMission.setChanged();
        com.maddox.il2.ai.Front.setMarkersChanged();
        return actorfrontmarker;
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
            insert(loc.getPoint(), flag, item[j].army);
            return;
        }
    }

    private void updateView()
    {
        int i = allActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.ActorFrontMarker actorfrontmarker = (com.maddox.il2.builder.ActorFrontMarker)allActors.get(j);
            actorfrontmarker.drawing(viewType.bChecked);
        }

    }

    public void configure()
    {
        if(com.maddox.il2.builder.Plugin.getPlugin("Mission") == null)
        {
            throw new RuntimeException("PlMisFront: plugin 'Mission' not found");
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
                com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.add(com.maddox.il2.builder.Plugin.i18n("FrontMarker") + " " + com.maddox.il2.game.I18N.army(com.maddox.il2.ai.Army.name(item[j].army)));

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
        com.maddox.il2.builder.ActorFrontMarker actorfrontmarker = (com.maddox.il2.builder.ActorFrontMarker)actor;
        _actorInfo[0] = actorfrontmarker.i18nKey;
        return _actorInfo;
    }

    public void syncSelector()
    {
        com.maddox.il2.builder.ActorFrontMarker actorfrontmarker = (com.maddox.il2.builder.ActorFrontMarker)com.maddox.il2.builder.Plugin.builder.selectedActor();
        fillComboBox2(startComboBox1);
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox2.setSelected(actorfrontmarker.getArmy() - 1, true, false);
    }

    public void createGUI()
    {
        startComboBox1 = com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.size();
        com.maddox.il2.builder.Plugin.builder.wSelect.comboBox1.add(com.maddox.il2.builder.Plugin.i18n("FrontMarker"));
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
            viewType = com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu.addItem(i, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mDisplayFilter.subMenu, com.maddox.il2.builder.Plugin.i18n("showFrontMarker"), null) {

                public void execute()
                {
                    bChecked = !bChecked;
                    updateView();
                }

            }
);
            viewType.bChecked = true;
        }
    }

    public void freeResources()
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static final int TILE_SIZE = 64;
    public static final int M = 16;
    private static final boolean USE_TILE_RENDER = false;
    protected java.util.ArrayList allActors;
    com.maddox.il2.builder.Item item[];
    java.util.ArrayList markers;
    int tNx;
    int tNy;
    double camWorldXOffset;
    double camWorldYOffset;
    float camLeft;
    float camBottom;
    int _tNx;
    int _tNy;
    com.maddox.il2.engine.Mat mats[];
    com.maddox.il2.engine.Mat baseTileMat;
    byte buf[];
    byte _mask[];
    private com.maddox.JGP.Point2d p2d;
    private com.maddox.il2.builder.PlMission pluginMission;
    private int startComboBox1;
    private com.maddox.gwindow.GWindowMenuItem viewType;
    private java.lang.String _actorInfo[];

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMisFront.class, "name", "MisFront");
    }


}
