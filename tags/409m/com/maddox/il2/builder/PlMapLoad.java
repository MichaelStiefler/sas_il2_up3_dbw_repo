// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMapLoad.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.TexImage.TexImage;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBar;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRootMenu;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Land2D;
import com.maddox.il2.engine.Land2Dn;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.engine.TextScr;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Runaway;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.tools.BridgesGenerator;
import com.maddox.rts.HomePath;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.io.PrintStream;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, PlMapLabel, PathFind, Builder, 
//            BldConfig, PlMission

public class PlMapLoad extends com.maddox.il2.builder.Plugin
{
    class MenuItem extends com.maddox.gwindow.GWindowMenuItem
    {

        public void execute()
        {
            com.maddox.il2.builder.Land land = (com.maddox.il2.builder.Land)com.maddox.il2.builder.PlMapLoad.lands.get(indx);
            if(land == com.maddox.il2.builder.PlMapLoad.getLandLoaded())
                return;
            if(!com.maddox.il2.builder.Plugin.builder.bMultiSelect)
            {
                _guiLand = land;
                ((com.maddox.il2.builder.PlMission)com.maddox.il2.builder.Plugin.getPlugin("Mission")).loadNewMap();
            } else
            {
                guiMapLoad(land);
            }
        }

        int indx;

        public MenuItem(com.maddox.gwindow.GWindowMenu gwindowmenu, java.lang.String s, java.lang.String s1, int i)
        {
            super(gwindowmenu, s, s1);
            indx = i;
        }
    }

    public static class Land
    {

        public int indx;
        public java.lang.String keyName;
        public java.lang.String i18nName;
        public java.lang.String fileName;
        public java.lang.String dirName;

        public Land()
        {
        }
    }


    public PlMapLoad()
    {
        p2d = new Point2d();
    }

    public static com.maddox.il2.builder.Land getLandLoaded()
    {
        if(landLoaded < 0)
            return null;
        else
            return (com.maddox.il2.builder.Land)lands.get(landLoaded);
    }

    public static com.maddox.il2.builder.Land getLandForKeyName(java.lang.String s)
    {
        for(int i = 0; i < lands.size(); i++)
        {
            com.maddox.il2.builder.Land land = (com.maddox.il2.builder.Land)lands.get(i);
            if(land.keyName.equals(s))
                return land;
        }

        return null;
    }

    public static com.maddox.il2.builder.Land getLandForFileName(java.lang.String s)
    {
        for(int i = 0; i < lands.size(); i++)
        {
            com.maddox.il2.builder.Land land = (com.maddox.il2.builder.Land)lands.get(i);
            if(land.fileName.equals(s))
                return land;
        }

        return null;
    }

    public static java.lang.String mapKeyName()
    {
        com.maddox.il2.builder.Land land = com.maddox.il2.builder.PlMapLoad.getLandLoaded();
        if(land == null)
            return null;
        else
            return land.keyName;
    }

    public static java.lang.String mapI18nName()
    {
        com.maddox.il2.builder.Land land = com.maddox.il2.builder.PlMapLoad.getLandLoaded();
        if(land == null)
            return null;
        else
            return land.i18nName;
    }

    public static java.lang.String mapFileName()
    {
        com.maddox.il2.builder.Land land = com.maddox.il2.builder.PlMapLoad.getLandLoaded();
        if(land == null)
            return null;
        else
            return land.fileName;
    }

    public static java.lang.String mapDirName()
    {
        com.maddox.il2.builder.Land land = com.maddox.il2.builder.PlMapLoad.getLandLoaded();
        if(land == null)
            return null;
        else
            return land.dirName;
    }

    private static void bridgesClear()
    {
        for(int i = 0; i < bridgeActors.size(); i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)bridgeActors.get(i);
            actor.destroy();
        }

        bridgeActors.clear();
    }

    public static void bridgesCreate(com.maddox.TexImage.TexImage teximage)
    {
        com.maddox.il2.builder.PlMapLoad.bridgesClear();
        if(teximage != null)
        {
            com.maddox.il2.tools.Bridge abridge[] = com.maddox.il2.tools.BridgesGenerator.getBridgesArray(teximage);
            for(int i = 0; i < abridge.length; i++)
            {
                com.maddox.il2.objects.bridges.Bridge bridge = new Bridge(i, abridge[i].type, abridge[i].x1, abridge[i].y1, abridge[i].x2, abridge[i].y2, 0.0F);
                com.maddox.rts.Property.set(bridge, "builderSpawn", "");
                bridgeActors.add(bridge);
                _p3d.x = com.maddox.il2.ai.World.land().PIX2WORLDX((abridge[i].x1 + abridge[i].x2) / 2);
                _p3d.y = com.maddox.il2.ai.World.land().PIX2WORLDY((abridge[i].y1 + abridge[i].y2) / 2);
                _p3d.z = 0.0D;
                com.maddox.il2.builder.PlMapLabel.insert(_p3d);
            }

            java.lang.System.out.println("" + abridge.length + " bridges created");
        }
    }

    public void mapUnload()
    {
        landLoaded = -1;
        clearMenuItems();
        com.maddox.il2.builder.PlMapLoad.bridgesClear();
        com.maddox.il2.builder.Plugin.doMapLoaded();
        com.maddox.il2.builder.PathFind.unloadMap();
    }

    public boolean mapLoad(com.maddox.il2.builder.Land land)
    {
        if(com.maddox.il2.builder.PlMapLoad.getLandLoaded() == land)
            return true;
        com.maddox.il2.builder.Plugin.builder.deleteAll();
        com.maddox.il2.builder.PlMapLoad.bridgesClear();
        landLoaded = -1;
        clearMenuItems();
        com.maddox.il2.builder.PathFind.unloadMap();
        com.maddox.il2.game.Main3D.cur3D().resetGame();
        com.maddox.il2.builder.Plugin.builder.tip(com.maddox.il2.builder.Plugin.i18n("Loading") + " " + land.i18nName + "...");
        com.maddox.rts.SectFile sectfile = new SectFile("maps/" + land.fileName, 0);
        int i = sectfile.sectionIndex("MAP2D");
        if(i < 0)
        {
            com.maddox.il2.builder.Plugin.builder.tipErr("section [MAP2D] not found in 'maps/" + land.fileName);
            return false;
        }
        int j = sectfile.vars(i);
        if(j == 0)
        {
            com.maddox.il2.builder.Plugin.builder.tipErr("section [MAP2D] in 'maps/" + land.fileName + " is empty");
            return false;
        }
        try
        {
            if(com.maddox.il2.builder.Plugin.builder.bMultiSelect)
            {
                com.maddox.il2.ai.World.land().LoadMap(land.fileName, null);
            } else
            {
                int ai[] = null;
                int k = sectfile.sectionIndex("static");
                if(k >= 0 && sectfile.vars(k) > 0)
                {
                    java.lang.String s = sectfile.var(k, 0);
                    if(s != null && s.length() > 0)
                    {
                        s = com.maddox.rts.HomePath.concatNames("maps/" + land.fileName, s);
                        ai = com.maddox.il2.objects.Statics.readBridgesEndPoints(s);
                    }
                }
                com.maddox.il2.ai.World.land().LoadMap(land.fileName, ai);
            }
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.builder.Plugin.builder.tipErr("World.land().LoadMap() error: " + exception);
            return false;
        }
        com.maddox.il2.ai.World.cur().setCamouflage(com.maddox.il2.ai.World.land().config.camouflage);
        if(com.maddox.il2.game.Main3D.cur3D().land2D != null)
        {
            if(!com.maddox.il2.game.Main3D.cur3D().land2D.isDestroyed())
                com.maddox.il2.game.Main3D.cur3D().land2D.destroy();
            com.maddox.il2.game.Main3D.cur3D().land2D = null;
        }
        com.maddox.il2.game.Main3D.cur3D().land2D = new Land2Dn(land.fileName, com.maddox.il2.ai.World.land().getSizeX(), com.maddox.il2.ai.World.land().getSizeY());
        com.maddox.il2.builder.Plugin.builder.computeViewMap2D(-1D, 0.0D, 0.0D);
        com.maddox.il2.builder.PathFind.tShip = new TexImage();
        com.maddox.il2.builder.PathFind.tNoShip = new TexImage();
        boolean flag = false;
        int l = sectfile.sectionIndex("TMAPED");
        if(l >= 0)
        {
            int i1 = sectfile.vars(l);
            if(i1 > 0)
            {
                java.lang.String s1 = "maps/" + land.dirName + "/" + sectfile.var(l, 0);
                try
                {
                    com.maddox.il2.builder.PathFind.tShip.LoadTGA(s1);
                    com.maddox.il2.builder.PathFind.tNoShip.LoadTGA(s1);
                    com.maddox.TexImage.TexImage teximage = new TexImage();
                    teximage.LoadTGA("maps/" + land.dirName + "/" + com.maddox.il2.ai.World.land().config.typeMap);
                    for(int i2 = 0; i2 < teximage.sy; i2++)
                    {
                        for(int l2 = 0; l2 < teximage.sx; l2++)
                        {
                            int l3 = teximage.I(l2, i2) & 0xe0;
                            if(l3 != 0)
                            {
                                com.maddox.il2.builder.PathFind.tShip.I(l2, i2, com.maddox.il2.builder.PathFind.tShip.intI(l2, i2) & 0xffffff1f | l3);
                                com.maddox.il2.builder.PathFind.tNoShip.I(l2, i2, com.maddox.il2.builder.PathFind.tNoShip.intI(l2, i2) & 0xffffff1f | l3);
                            }
                        }

                    }

                    flag = true;
                }
                catch(java.lang.Exception exception2) { }
            }
        }
        if(!flag)
            try
            {
                com.maddox.il2.builder.PathFind.tShip.LoadTGA("maps/" + land.dirName + "/" + com.maddox.il2.ai.World.land().config.typeMap);
                com.maddox.il2.builder.PathFind.tNoShip.LoadTGA("maps/" + land.dirName + "/" + com.maddox.il2.ai.World.land().config.typeMap);
            }
            catch(java.lang.Exception exception1) { }
        for(int j1 = 0; j1 < com.maddox.il2.builder.PathFind.tShip.sy; j1++)
        {
            for(int k1 = 0; k1 < com.maddox.il2.builder.PathFind.tShip.sx; k1++)
            {
                if((com.maddox.il2.builder.PathFind.tShip.I(k1, j1) & 0x1c) == 24)
                    com.maddox.il2.builder.PathFind.tShip.I(k1, j1, com.maddox.il2.builder.PathFind.tShip.intI(k1, j1) & 0xffffffe3);
                if((com.maddox.il2.builder.PathFind.tNoShip.I(k1, j1) & 0x1c) == 24)
                    com.maddox.il2.builder.PathFind.tNoShip.I(k1, j1, com.maddox.il2.builder.PathFind.tNoShip.intI(k1, j1) & 0xffffffe3);
            }

        }

        com.maddox.il2.engine.Landscape landscape = com.maddox.il2.ai.World.land();
        for(int l1 = 0; l1 < com.maddox.il2.builder.PathFind.tShip.sy; l1++)
        {
            for(int j2 = 0; j2 < com.maddox.il2.builder.PathFind.tShip.sx; j2++)
                if((com.maddox.il2.builder.PathFind.tShip.intI(j2, l1) & 0x1c) == 28)
                {
                    com.maddox.il2.engine.Landscape _tmp = landscape;
                    if(com.maddox.il2.engine.Landscape.estimateNoWater(j2, l1, 128) > 255 - com.maddox.il2.builder.Plugin.builder.conf.iWaterLevel)
                        com.maddox.il2.builder.PathFind.tShip.I(j2, l1, com.maddox.il2.builder.PathFind.tShip.intI(j2, l1) & 0xffffffe3);
                }

        }

        for(int k2 = 0; k2 < com.maddox.il2.builder.PathFind.tNoShip.sy; k2++)
        {
            for(int i3 = 0; i3 < com.maddox.il2.builder.PathFind.tNoShip.sx; i3++)
                if((com.maddox.il2.builder.PathFind.tNoShip.intI(i3, k2) & 0x1c) == 28)
                {
                    com.maddox.il2.engine.Landscape _tmp1 = landscape;
                    if(com.maddox.il2.engine.Landscape.estimateNoWater(i3, k2, 128) > 250)
                        com.maddox.il2.builder.PathFind.tNoShip.I(i3, k2, com.maddox.il2.builder.PathFind.tNoShip.intI(i3, k2) & 0xffffffe3);
                }

        }

        com.maddox.il2.builder.Plugin.builder.tip(land.i18nName);
        landLoaded = land.indx;
        if(menuItem != null)
        {
            for(int j3 = 0; j3 < menuItem.length; j3++)
                menuItem[j3].bChecked = j3 == landLoaded;

        }
        com.maddox.il2.builder.Plugin.doMapLoaded();
        com.maddox.il2.builder.PathFind.b = new com.maddox.il2.tools.Bridge[bridgeActors.size()];
        for(int k3 = 0; k3 < bridgeActors.size(); k3++)
        {
            com.maddox.il2.objects.bridges.Bridge bridge = (com.maddox.il2.objects.bridges.Bridge)bridgeActors.get(k3);
            int i4 = bridge.__indx;
            com.maddox.il2.builder.PathFind.b[i4] = new com.maddox.il2.tools.Bridge();
            com.maddox.il2.builder.PathFind.b[i4].x1 = bridge.__x1;
            com.maddox.il2.builder.PathFind.b[i4].y1 = bridge.__y1;
            com.maddox.il2.builder.PathFind.b[i4].x2 = bridge.__x2;
            com.maddox.il2.builder.PathFind.b[i4].y2 = bridge.__y2;
            com.maddox.il2.builder.PathFind.b[i4].type = bridge.__type;
        }

        com.maddox.il2.builder.PathFind.setMoverType(0);
        return true;
    }

    public void renderMap2D()
    {
        if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            return;
        if(com.maddox.il2.builder.PlMapLoad.getLandLoaded() == null)
            return;
        if(com.maddox.il2.builder.Plugin.builder.conf.bViewBridge)
        {
            com.maddox.il2.engine.Render.prepareStates();
            com.maddox.il2.engine.IconDraw.setColor(255, 255, 255, 255);
            for(int i = 0; i < bridgeActors.size(); i++)
            {
                com.maddox.il2.objects.bridges.Bridge bridge = (com.maddox.il2.objects.bridges.Bridge)bridgeActors.get(i);
                if(com.maddox.il2.builder.Plugin.builder.project2d(bridge.pos.getAbsPoint(), p2d))
                    com.maddox.il2.engine.IconDraw.render(bridge, p2d.x, p2d.y);
            }

            if(bDrawNumberBridge || com.maddox.il2.builder.Plugin.builder.bMultiSelect)
            {
                for(int j = 0; j < bridgeActors.size(); j++)
                {
                    com.maddox.il2.objects.bridges.Bridge bridge1 = (com.maddox.il2.objects.bridges.Bridge)bridgeActors.get(j);
                    if(com.maddox.il2.builder.Plugin.builder.project2d(bridge1.pos.getAbsPoint(), p2d))
                        com.maddox.il2.engine.TextScr.font().output(0xff00ff00, (int)p2d.x + com.maddox.il2.engine.IconDraw.scrSizeX() / 2 + 2, (int)p2d.y - com.maddox.il2.engine.IconDraw.scrSizeY() / 2 - 2, 0.0F, "" + bridge1.__indx);
                }

            }
        }
        if(com.maddox.il2.builder.Plugin.builder.conf.bViewRunaway)
        {
            com.maddox.il2.engine.IconDraw.setColor(255, 255, 255, 255);
            for(com.maddox.il2.objects.air.Runaway runaway = com.maddox.il2.ai.World.cur().runawayList; runaway != null; runaway = runaway.next())
                if(com.maddox.il2.builder.Plugin.builder.project2d(runaway.pos.getAbsPoint(), p2d))
                    com.maddox.il2.engine.IconDraw.render(runaway, p2d.x, p2d.y);

        }
    }

    private void clearMenuItems()
    {
        if(menuItem != null)
        {
            for(int i = 0; i < menuItem.length; i++)
                menuItem[i].bChecked = false;

        }
    }

    public void createGUI()
    {
        com.maddox.gwindow.GWindowRootMenu gwindowrootmenu = (com.maddox.gwindow.GWindowRootMenu)com.maddox.il2.builder.Plugin.builder.clientWindow.root;
        com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem = gwindowrootmenu.menuBar.getItem(0);
        com.maddox.gwindow.GWindowMenuItem gwindowmenuitem = gwindowmenubaritem.subMenu.addItem(0, new GWindowMenuItem(gwindowmenubaritem.subMenu, com.maddox.il2.builder.Plugin.i18n("&MapLoad"), com.maddox.il2.builder.Plugin.i18n("TIPLoadLandscape")));
        gwindowmenuitem.subMenu = (com.maddox.gwindow.GWindowMenu)gwindowmenuitem.create(new GWindowMenu());
        gwindowmenuitem.subMenu.close(false);
        int i = lands.size();
        menuItem = new com.maddox.il2.builder.MenuItem[i];
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.builder.Land land = (com.maddox.il2.builder.Land)lands.get(j);
            gwindowmenuitem.subMenu.addItem(menuItem[j] = new MenuItem(gwindowmenuitem.subMenu, land.i18nName, null, j));
            menuItem[j].bChecked = false;
        }

    }

    public void guiMapLoad()
    {
        guiMapLoad(_guiLand);
    }

    public void guiMapLoad(com.maddox.il2.builder.Land land)
    {
        _guiLand = land;
        loadMessageBox = new GWindowMessageBox(com.maddox.il2.builder.Plugin.builder.clientWindow.root, 20F, true, com.maddox.il2.builder.Plugin.i18n("StandBy"), com.maddox.il2.builder.Plugin.i18n("LoadingLandscape") + " " + land.i18nName, 4, 0.0F);
        new com.maddox.rts.MsgAction(72, 0.0D) {

            public void doAction()
            {
                mapLoad(_guiLand);
                loadMessageBox.close(false);
                loadMessageBox = null;
            }

        }
;
    }

    public void configure()
    {
        com.maddox.rts.SectFile sectfile = new SectFile("maps/all.ini", 0);
        int i = sectfile.sectionIndex("all");
        if(i < 0)
            return;
        int j = sectfile.vars(i);
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.builder.Land land = new Land();
            land.indx = k;
            land.keyName = sectfile.var(i, k);
            land.fileName = sectfile.value(i, k);
            land.dirName = land.fileName.substring(0, land.fileName.lastIndexOf("/"));
            land.i18nName = com.maddox.il2.game.I18N.map(land.keyName);
            lands.add(land);
        }

    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static java.util.ArrayList lands = new ArrayList();
    private static int landLoaded = -1;
    public static java.util.ArrayList bridgeActors = new ArrayList();
    public static boolean bDrawNumberBridge = false;
    private static com.maddox.JGP.Point3d _p3d = new Point3d();
    private com.maddox.JGP.Point2d p2d;
    com.maddox.il2.builder.MenuItem menuItem[];
    private com.maddox.gwindow.GWindowMessageBox loadMessageBox;
    private com.maddox.il2.builder.Land _guiLand;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMapLoad.class, "name", "MapLoad");
    }





}
