// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PlMisDestruction.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMenuPopUp;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.bridges.LongBridge;
import com.maddox.il2.objects.buildings.House;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.HashMapInt;
import java.util.AbstractCollection;
import java.util.BitSet;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, PlMission, Builder, BldConfig, 
//            PlMapLoad

public class PlMisDestruction extends com.maddox.il2.builder.Plugin
{
    class WDialog extends com.maddox.gwindow.GWindowFramed
    {

        public void windowShown()
        {
            mDialog.bChecked = true;
            if(tiles == null)
                tilesNew();
            super.windowShown();
        }

        public void windowHidden()
        {
            mDialog.bChecked = false;
            super.windowHidden();
        }

        public void created()
        {
            bAlwaysOnTop = true;
            super.created();
            title = com.maddox.il2.builder.Plugin.i18n("Destruction");
            com.maddox.gwindow.GWindowDialogClient gwindowdialogclient;
            clientWindow = create(gwindowdialogclient = new GWindowDialogClient());
            com.maddox.gwindow.GWindowLabel gwindowlabel;
            gwindowdialogclient.addLabel(gwindowlabel = new GWindowLabel(gwindowdialogclient, 1.0F, 1.0F, 11F, 1.3F, com.maddox.il2.builder.Plugin.i18n("DestLight"), null));
            gwindowlabel.align = 2;
            gwindowdialogclient.addLabel(gwindowlabel = new GWindowLabel(gwindowdialogclient, 1.0F, 3F, 11F, 1.3F, com.maddox.il2.builder.Plugin.i18n("DestSize"), null));
            gwindowlabel.align = 2;
            gwindowdialogclient.addLabel(gwindowlabel = new GWindowLabel(gwindowdialogclient, 1.0F, 5F, 11F, 1.3F, com.maddox.il2.builder.Plugin.i18n("DestValue"), null));
            gwindowlabel.align = 2;
            if(com.maddox.il2.builder.Plugin.builder.conf.iLightDestruction < 0)
                com.maddox.il2.builder.Plugin.builder.conf.iLightDestruction = 0;
            if(com.maddox.il2.builder.Plugin.builder.conf.iLightDestruction > 255)
                com.maddox.il2.builder.Plugin.builder.conf.iLightDestruction = 255;
            gwindowdialogclient.addControl(wLight = new com.maddox.gwindow.GWindowHSliderInt(gwindowdialogclient, 0, 256, com.maddox.il2.builder.Plugin.builder.conf.iLightDestruction, 13F, 1.0F, 10F) {

                public boolean notify(int i, int j)
                {
                    com.maddox.il2.builder.Plugin.builder.conf.iLightDestruction = pos();
                    return super.notify(i, j);
                }

                public void created()
                {
                    bSlidingNotify = true;
                }

            }
);
            wLight.toolTip = com.maddox.il2.builder.Plugin.i18n("TIPDestLight");
            wLight.resized();
            gwindowdialogclient.addControl(wSize = new com.maddox.gwindow.GWindowHSliderInt(gwindowdialogclient, 0, 7, 0, 13F, 3F, 10F) {

                public boolean notify(int i, int j)
                {
                    fillSize = (int)java.lang.Math.pow(2D, pos());
                    return super.notify(i, j);
                }

                public void created()
                {
                    bSlidingNotify = true;
                }

            }
);
            wSize.toolTip = com.maddox.il2.builder.Plugin.i18n("TIPDestSize");
            wSize.resized();
            gwindowdialogclient.addControl(wValue = new com.maddox.gwindow.GWindowHSliderInt(gwindowdialogclient, 0, 256, fillValue, 13F, 5F, 10F) {

                public boolean notify(int i, int j)
                {
                    fillValue = pos();
                    return super.notify(i, j);
                }

                public void created()
                {
                    bSlidingNotify = true;
                }

            }
);
            wValue.toolTip = com.maddox.il2.builder.Plugin.i18n("TIPDestValue");
            wValue.resized();
        }

        public void afterCreated()
        {
            super.afterCreated();
            resized();
            close(false);
        }

        public com.maddox.gwindow.GWindowHSliderInt wLight;
        public com.maddox.gwindow.GWindowHSliderInt wSize;
        public com.maddox.gwindow.GWindowHSliderInt wValue;


        public WDialog()
        {
            doNew(com.maddox.il2.builder.Plugin.builder.clientWindow, 2.0F, 2.0F, 25F, 9F, true);
            bSizable = false;
        }
    }

    class SelectFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public void reset(double d)
        {
            _Actor = null;
            _maxLen2 = d;
        }

        public com.maddox.il2.engine.Actor get()
        {
            return _Actor;
        }

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            if(d <= _maxLen2)
            {
                if(actor instanceof com.maddox.il2.objects.bridges.BridgeSegment)
                    if(com.maddox.il2.builder.Plugin.builder.conf.bViewBridge)
                        actor = actor.getOwner();
                    else
                        return true;
                if(actor instanceof com.maddox.il2.objects.bridges.Bridge)
                {
                    if(!com.maddox.il2.builder.Plugin.builder.conf.bViewBridge)
                        return true;
                } else
                if(!(actor instanceof com.maddox.il2.objects.buildings.House))
                    return true;
                if(_Actor == null)
                {
                    _Actor = actor;
                    _Len2 = d;
                } else
                if(d < _Len2)
                {
                    _Actor = actor;
                    _Len2 = d;
                }
            }
            return true;
        }

        private com.maddox.il2.engine.Actor _Actor;
        private double _Len2;
        private double _maxLen2;

        SelectFilter()
        {
            _Actor = null;
        }
    }

    class Tile
    {

        public void setPix(int i, int j, int k)
        {
            int l = (j * 64 + i) * 4;
            k &= 0xff;
            buf[l + 0] = (byte)k;
            buf[l + 1] = (byte)(255 - k);
            buf[l + 3] = -1;
            bufEmpty = false;
            bChanged = true;
            tilesChanged = true;
        }

        public boolean fillFromStatic(boolean flag)
        {
            com.maddox.il2.objects.Statics statics = com.maddox.il2.ai.World.cur().statics;
            com.maddox.util.HashMapInt hashmapint = statics.allBlocks();
            if(!bufEmpty && !flag)
            {
                for(int i = 0; i < buf.length; i++)
                    buf[i] = 0;

                bufEmpty = true;
            }
            for(int j = 0; j < 64; j++)
            {
                for(int k = 0; k < 64; k++)
                {
                    int l = statics.key(j + y0, k + x0);
                    com.maddox.il2.objects.Statics.Block block = (com.maddox.il2.objects.Statics.Block)hashmapint.get(l);
                    if(block != null)
                    {
                        if(flag)
                            return true;
                        float f = block.getDestruction();
                        setPix(k, j, (int)(f * 255F));
                    }
                }

            }

            return false;
        }

        public void updateImage()
        {
            if(mat == null)
            {
                mat = (com.maddox.il2.engine.Mat)baseTileMat.Clone();
                mat.Rename(null);
                mat.setLayer(0);
            }
            fillFromStatic(false);
            mat.updateImage(64, 64, 0x380004, buf);
            bChanged = false;
        }

        public boolean bChanged;
        public int x0;
        public int y0;
        public com.maddox.il2.engine.Mat mat;

        public Tile(int i, int j)
        {
            bChanged = true;
            x0 = i;
            y0 = j;
        }
    }


    public PlMisDestruction()
    {
        fillSize = 1;
        fillValue = 127;
        tilesChanged = false;
        buf = new byte[16384];
        bufEmpty = true;
        _startFill = new Point3d();
        _endFill = new Point3d();
        _stepFill = new Point3d();
        _selectFilter = new SelectFilter();
        findedActor = null;
    }

    public boolean isActive()
    {
        if(com.maddox.il2.builder.Plugin.builder.isFreeView())
            return false;
        if(tiles == null)
            return false;
        else
            return mDialog.bChecked;
    }

    private void tilesDel()
    {
        if(tiles == null)
        {
            return;
        } else
        {
            tiles = null;
            return;
        }
    }

    private void tilesNew()
    {
        tilesDel();
        int i = com.maddox.il2.engine.Landscape.getSizeXpix();
        int j = com.maddox.il2.engine.Landscape.getSizeYpix();
        int k = ((i + 64) - 1) / 64;
        int l = ((j + 64) - 1) / 64;
        tiles = new com.maddox.il2.builder.Tile[l][k];
        com.maddox.il2.builder.Tile tile = null;
        for(int i1 = 0; i1 < l; i1++)
        {
            for(int j1 = 0; j1 < k; j1++)
            {
                if(tile == null)
                {
                    tile = new Tile(j1 * 64, i1 * 64);
                } else
                {
                    tile.x0 = j1 * 64;
                    tile.y0 = i1 * 64;
                }
                if(tile.fillFromStatic(true))
                {
                    tiles[i1][j1] = tile;
                    tile = null;
                    tilesChanged = true;
                }
            }

        }

    }

    public void preRenderMap2D()
    {
        if(!isActive())
            return;
        if(!tilesChanged)
            return;
        int i = tiles[0].length;
        int j = tiles.length;
        for(int k = 0; k < j; k++)
        {
            for(int l = 0; l < i; l++)
            {
                com.maddox.il2.builder.Tile tile = tiles[k][l];
                if(tile != null && tile.bChanged)
                    tile.updateImage();
            }

        }

        tilesChanged = false;
    }

    public void renderMap2D()
    {
        if(!isActive())
            return;
        com.maddox.il2.engine.CameraOrtho2D cameraortho2d = (com.maddox.il2.engine.CameraOrtho2D)com.maddox.il2.engine.Render.currentCamera();
        int i = tiles[0].length;
        int j = tiles.length;
        float f = 12800F;
        float f1 = (float)((double)f * cameraortho2d.worldScale);
        int k = com.maddox.il2.builder.Plugin.builder.conf.iLightDestruction << 24 | 0xffffff;
        for(int l = 0; l < j; l++)
        {
            float f2 = (float)(((double)((float)l * f) - cameraortho2d.worldYOffset) * cameraortho2d.worldScale);
            if(f2 > cameraortho2d.top)
                break;
            if(f2 + f1 >= cameraortho2d.bottom)
            {
                for(int i1 = 0; i1 < i; i1++)
                {
                    float f3 = (float)(((double)((float)i1 * f) - cameraortho2d.worldXOffset) * cameraortho2d.worldScale);
                    if(f3 > cameraortho2d.right)
                        break;
                    if(f3 + f1 >= cameraortho2d.left)
                    {
                        com.maddox.il2.builder.Tile tile = tiles[l][i1];
                        if(tile != null && tile.mat != null)
                            com.maddox.il2.engine.Render.drawTile(f3, f2, f1, f1, 0.0F, tile.mat, k, 0.0F, 0.0F, 1.0F, 1.0F);
                    }
                }

            }
        }

    }

    public void mapLoaded()
    {
        tilesDel();
        if(mDialog.bChecked)
            wDialog.hideWindow();
    }

    public void load(com.maddox.rts.SectFile sectfile)
    {
        com.maddox.il2.ai.World.cur().statics.restoreAllBridges();
        com.maddox.il2.ai.World.cur().statics.restoreAllHouses();
        com.maddox.il2.ai.World.cur().statics.loadStateBridges(sectfile, false);
        com.maddox.il2.ai.World.cur().statics.loadStateHouses(sectfile, false);
        if(tiles == null)
            return;
        int i = tiles[0].length;
        int j = tiles.length;
        for(int k = 0; k < j; k++)
        {
            for(int l = 0; l < i; l++)
            {
                com.maddox.il2.builder.Tile tile = tiles[k][l];
                if(tile != null)
                    tile.bChanged = true;
            }

        }

        tilesChanged = true;
    }

    public boolean save(com.maddox.rts.SectFile sectfile)
    {
        int i = sectfile.sectionAdd("Bridge");
        com.maddox.il2.ai.World.cur().statics.saveStateBridges(sectfile, i);
        i = sectfile.sectionAdd("House");
        com.maddox.il2.ai.World.cur().statics.saveStateHouses(sectfile, i);
        return true;
    }

    private void fill(com.maddox.JGP.Point3d point3d, int i)
    {
        com.maddox.il2.objects.Statics statics = com.maddox.il2.ai.World.cur().statics;
        com.maddox.util.HashMapInt hashmapint = statics.allBlocks();
        int j = (int)(point3d.x / 200D);
        int k = (int)(point3d.y / 200D);
        i |= 1;
        int l = j - i / 2;
        int i1 = k - i / 2;
        for(int j1 = i1; j1 < i1 + i; j1++)
        {
            for(int k1 = l; k1 < l + i; k1++)
            {
                int l1 = statics.key(j1, k1);
                com.maddox.il2.objects.Statics.Block block = (com.maddox.il2.objects.Statics.Block)hashmapint.get(l1);
                if(block != null)
                {
                    int i2 = k1 / 64;
                    int j2 = j1 / 64;
                    tiles[j2][i2].bChanged = true;
                    tilesChanged = true;
                    block.setDestruction((float)fillValue / 255F);
                    com.maddox.il2.builder.PlMission.setChanged();
                }
            }

        }

    }

    private void _doFill()
    {
        double d = _endFill.distance(_startFill);
        int i = (int)java.lang.Math.round(d / 200D) + 1;
        float f = 1.0F / (float)i;
        for(int j = 0; j <= i; j++)
        {
            _stepFill.interpolate(_startFill, _endFill, (float)j * f);
            fill(_stepFill, fillSize);
        }

    }

    public void beginFill(com.maddox.JGP.Point3d point3d)
    {
        if(!isActive())
        {
            return;
        } else
        {
            _startFill.set(point3d);
            return;
        }
    }

    public void fill(com.maddox.JGP.Point3d point3d)
    {
        if(!isActive())
        {
            return;
        } else
        {
            _endFill.set(point3d);
            _doFill();
            _startFill.set(point3d);
            return;
        }
    }

    public void endFill(com.maddox.JGP.Point3d point3d)
    {
    }

    public void configure()
    {
        if(com.maddox.il2.builder.Plugin.getPlugin("Mission") == null)
        {
            throw new RuntimeException("PlMisDestruction: plugin 'Mission' not found");
        } else
        {
            pluginMission = (com.maddox.il2.builder.PlMission)com.maddox.il2.builder.Plugin.getPlugin("Mission");
            return;
        }
    }

    public com.maddox.il2.engine.Actor selectNear(com.maddox.JGP.Point3d point3d, double d)
    {
        _selectFilter.reset(d * d);
        com.maddox.il2.engine.Engine.drawEnv().getFiltered((java.util.AbstractCollection)null, point3d.x - d, point3d.y - d, point3d.x + d, point3d.y + d, 15, _selectFilter);
        return _selectFilter.get();
    }

    public void fillPopUpMenu(com.maddox.gwindow.GWindowMenuPopUp gwindowmenupopup, com.maddox.JGP.Point3d point3d)
    {
        if(!isActive())
            return;
        findedActor = selectNear(point3d, 100D);
        if(findedActor == null)
            return;
        if(findedActor instanceof com.maddox.il2.objects.bridges.Bridge)
        {
            if(com.maddox.il2.engine.Actor.isAlive(findedActor))
                gwindowmenupopup.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenupopup, com.maddox.il2.builder.Plugin.i18n("De&stroyBridge"), com.maddox.il2.builder.Plugin.i18n("TIPDestroyBridge")) {

                    public void execute()
                    {
                        doBridge(true);
                    }

                }
);
            else
                gwindowmenupopup.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenupopup, com.maddox.il2.builder.Plugin.i18n("Re&storeBridge"), com.maddox.il2.builder.Plugin.i18n("TIPRestoreBridge")) {

                    public void execute()
                    {
                        doBridge(false);
                    }

                }
);
        } else
        if(com.maddox.il2.engine.Actor.isAlive(findedActor))
            gwindowmenupopup.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenupopup, com.maddox.il2.builder.Plugin.i18n("De&stroyObject"), com.maddox.il2.builder.Plugin.i18n("TIPDestroyObject")) {

                public void execute()
                {
                    doHouse(true);
                }

            }
);
        else
            gwindowmenupopup.addItem(new com.maddox.gwindow.GWindowMenuItem(gwindowmenupopup, com.maddox.il2.builder.Plugin.i18n("Re&storeObject"), com.maddox.il2.builder.Plugin.i18n("TIPRestoreObject")) {

                public void execute()
                {
                    doHouse(false);
                }

            }
);
    }

    private void doHouse(boolean flag)
    {
        com.maddox.JGP.Point3d point3d = findedActor.pos.getAbsPoint();
        int i = (int)(point3d.x / 64D / 200D);
        int j = (int)(point3d.y / 64D / 200D);
        com.maddox.il2.builder.Tile tile = tiles[j][i];
        if(tile == null)
        {
            return;
        } else
        {
            tile.bChanged = true;
            tilesChanged = true;
            findedActor.setDiedFlag(flag);
            com.maddox.il2.builder.PlMission.setChanged();
            return;
        }
    }

    private void doBridge(boolean flag)
    {
        com.maddox.il2.objects.bridges.LongBridge longbridge = (com.maddox.il2.objects.bridges.LongBridge)findedActor;
        if(flag)
        {
            int i = longbridge.NumStateBits();
            java.util.BitSet bitset = new BitSet(i);
            int j = (int)((float)(fillValue * i) / 255F);
            if(j == 0)
                j = 1;
            if(j > i)
                j = i;
            for(int k = j; k > 0;)
            {
                int l = (int)(java.lang.Math.random() * (double)j);
                if(!bitset.get(l))
                {
                    bitset.set(l);
                    k--;
                }
            }

            longbridge.SetStateOfSegments(bitset);
        } else
        {
            longbridge.BeLive();
        }
        com.maddox.il2.builder.PlMission.setChanged();
    }

    public void createGUI()
    {
        baseTileMat = com.maddox.il2.engine.Mat.New("3do/builder/tile.mat");
        mDialog = com.maddox.il2.builder.Plugin.builder.mView.subMenu.addItem(2, new com.maddox.gwindow.GWindowMenuItem(com.maddox.il2.builder.Plugin.builder.mView.subMenu, com.maddox.il2.builder.Plugin.i18n("De&struction"), com.maddox.il2.builder.Plugin.i18n("TIPDestruction")) {

            public void execute()
            {
                if(wDialog.isVisible())
                    wDialog.hideWindow();
                else
                if(com.maddox.il2.builder.PlMapLoad.getLandLoaded() != null)
                    wDialog.showWindow();
            }

        }
);
        wDialog = new WDialog();
    }

    public void freeResources()
    {
        findedActor = null;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static final int TILE_SIZE = 64;
    com.maddox.gwindow.GWindowMenuItem mDialog;
    com.maddox.il2.builder.WDialog wDialog;
    private int fillSize;
    private int fillValue;
    com.maddox.il2.builder.Tile tiles[][];
    boolean tilesChanged;
    com.maddox.il2.engine.Mat baseTileMat;
    byte buf[];
    boolean bufEmpty;
    private com.maddox.JGP.Point3d _startFill;
    private com.maddox.JGP.Point3d _endFill;
    private com.maddox.JGP.Point3d _stepFill;
    private com.maddox.il2.builder.PlMission pluginMission;
    private com.maddox.il2.builder.SelectFilter _selectFilter;
    com.maddox.il2.engine.Actor findedActor;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PlMisDestruction.class, "name", "MisDestruction");
    }






}
