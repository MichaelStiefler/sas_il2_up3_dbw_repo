// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIPad.java

package com.maddox.il2.gui;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GMesh;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.AirportCarrier;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Front;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Accumulator;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Land2D;
import com.maddox.il2.engine.Land2DText;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.LightEnvXY;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Renders;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.vehicles.artillery.AAA;
import com.maddox.rts.IniFile;
import com.maddox.util.HashMapExt;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUI, GUIBriefing, SquareLabels

public class GUIPad
{
    public class RenderMap2D extends com.maddox.il2.engine.Render
    {

        public void preRender()
        {
            com.maddox.il2.ai.Front.preRender(false);
        }

        public void render()
        {
            if(main.land2D != null)
                main.land2D.render(0.9F, 0.9F, 0.9F, 1.0F);
            if(main.land2DText != null)
                main.land2DText.render();
            drawGrid2D();
            com.maddox.il2.ai.Front.render(false);
            int i = (int)java.lang.Math.round((32D * (double)client.root.win.dx) / 1024D);
            com.maddox.il2.engine.IconDraw.setScrSize(i, i);
            drawAirports();
            if(!com.maddox.il2.ai.World.cur().diffCur.No_Map_Icons)
            {
                drawChiefs();
                drawAAAandFillAir();
                drawAir();
            }
            com.maddox.il2.gui.GUIBriefing.drawTargets(renders, gridFont, emptyMat, cameraMap2D, targets);
            if(!com.maddox.il2.ai.World.cur().diffCur.NoMinimapPath)
            {
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(com.maddox.il2.engine.Actor.isValid(aircraft))
                {
                    com.maddox.il2.engine.IconDraw.setColor(0xff00ffff);
                    drawPlayerPath();
                }
            }
            if(!com.maddox.il2.ai.World.cur().diffCur.NoMinimapPath || !com.maddox.il2.ai.World.cur().diffCur.No_Map_Icons)
            {
                com.maddox.il2.objects.air.Aircraft aircraft1 = com.maddox.il2.ai.World.getPlayerAircraft();
                if(com.maddox.il2.engine.Actor.isValid(aircraft1))
                {
                    com.maddox.JGP.Point3d point3d = ((com.maddox.il2.engine.Actor) (aircraft1)).pos.getAbsPoint();
                    float f = (float)((point3d.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
                    float f1 = (float)((point3d.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
                    com.maddox.il2.engine.IconDraw.setColor(-1);
                    com.maddox.il2.engine.IconDraw.render(_iconAir, f, f1, ((com.maddox.il2.engine.Actor) (aircraft1)).pos.getAbsOrient().azimut());
                }
            }
            com.maddox.il2.gui.SquareLabels.draw(cameraMap2D, com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX(), com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX(), com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeX());
        }

        public RenderMap2D(com.maddox.il2.engine.Renders renders2, float f)
        {
            super(renders2, f);
            useClearDepth(false);
            useClearColor(false);
        }
    }

    public class RenderMap2D1 extends com.maddox.il2.engine.Render
    {

        public void preRender()
        {
        }

        public void render()
        {
            renders1.draw(0.0F, 0.0F, renders1.win.dx, renders1.win.dy, mesh);
        }

        public RenderMap2D1(com.maddox.il2.engine.Renders renders2, float f)
        {
            super(renders2, f);
            useClearDepth(false);
            useClearColor(false);
        }
    }

    class ArmyAccum
        implements com.maddox.il2.engine.Accumulator
    {

        public void clear()
        {
        }

        public boolean add(com.maddox.il2.engine.Actor actor, double d)
        {
            _army[actor.getArmy()]++;
            return true;
        }

        ArmyAccum()
        {
        }
    }

    class AirDrome
    {

        com.maddox.il2.ai.Airport airport;
        int color;
        int army;

        AirDrome()
        {
        }
    }


    public boolean isActive()
    {
        return bActive;
    }

    public void enter()
    {
        if(bActive)
            return;
        bActive = true;
        com.maddox.il2.gui.GUI.activate(true, false);
        client.showWindow();
        float f = client.root.win.dx;
        float f1 = client.root.win.dy;
        frameRegion.x = com.maddox.il2.engine.Config.cur.ini.get("game", "mapPadX", frameRegion.x);
        frameRegion.y = com.maddox.il2.engine.Config.cur.ini.get("game", "mapPadY", frameRegion.y);
        frame.setPosSize(frameRegion.x * f, frameRegion.y * f1, frameRegion.dx * f, frameRegion.dy * f1);
        cameraMap2D.set(0.0F, renderMap2D.getViewPortWidth(), 0.0F, renderMap2D.getViewPortHeight());
        cameraMap2D1.set(0.0F, renderMap2D1.getViewPortWidth(), 0.0F, renderMap2D1.getViewPortHeight());
        computeScales();
        scaleCamera();
        if(bStartMission)
        {
            targets.clear();
            if(com.maddox.il2.game.Mission.isSingle() || com.maddox.il2.game.Mission.isCoop())
                com.maddox.il2.gui.GUIBriefing.fillTargets(com.maddox.il2.game.Mission.cur().sectFile(), targets);
        }
        if(!com.maddox.il2.ai.World.cur().diffCur.No_Map_Icons || bStartMission)
        {
            com.maddox.il2.engine.Actor actor = com.maddox.il2.engine.Actor.getByName("camera");
            float f2;
            float f3;
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                f2 = (float)point3d.x;
                f3 = (float)point3d.y;
            } else
            {
                f2 = com.maddox.il2.ai.World.land().getSizeX() / 2.0F;
                f3 = com.maddox.il2.ai.World.land().getSizeY() / 2.0F;
            }
            setPosCamera(f2, f3);
            bStartMission = false;
        }
        frame.activateWindow();
        savedUseMesh = main.guiManager.isUseGMeshs();
        saveIconDx = com.maddox.il2.engine.IconDraw.scrSizeX();
        saveIconDy = com.maddox.il2.engine.IconDraw.scrSizeY();
        main.guiManager.setUseGMeshs(false);
    }

    public void leave(boolean flag)
    {
        if(!bActive)
            return;
        bActive = false;
        float f = client.root.win.dx;
        float f1 = client.root.win.dy;
        frameRegion.x = frame.win.x / f;
        frameRegion.y = frame.win.y / f1;
        frameRegion.dx = frame.win.dx / f;
        frameRegion.dy = frame.win.dy / f1;
        com.maddox.il2.engine.Config.cur.ini.set("game", "mapPadX", frameRegion.x);
        com.maddox.il2.engine.Config.cur.ini.set("game", "mapPadY", frameRegion.y);
        client.hideWindow();
        com.maddox.il2.game.Main3D.cur3D().guiManager.setUseGMeshs(savedUseMesh);
        com.maddox.il2.engine.IconDraw.setScrSize(saveIconDx, saveIconDy);
        if(!flag)
            com.maddox.il2.gui.GUI.unActivate();
    }

    private void setPosCamera(float f, float f1)
    {
        float f2 = (float)((double)(cameraMap2D.right - cameraMap2D.left) / cameraMap2D.worldScale);
        cameraMap2D.worldXOffset = f - f2 / 2.0F;
        float f3 = (float)((double)(cameraMap2D.top - cameraMap2D.bottom) / cameraMap2D.worldScale);
        cameraMap2D.worldYOffset = f1 - f3 / 2.0F;
        clipCamera();
    }

    private void scaleCamera()
    {
        cameraMap2D.worldScale = (scale[curScale] * client.root.win.dx) / 1024F;
    }

    private void clipCamera()
    {
        if(cameraMap2D.worldXOffset < -com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX())
            cameraMap2D.worldXOffset = -com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX();
        float f = (float)((double)(cameraMap2D.right - cameraMap2D.left) / cameraMap2D.worldScale);
        if(cameraMap2D.worldXOffset > com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeX() - com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() - (double)f)
            cameraMap2D.worldXOffset = com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeX() - com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX() - (double)f;
        if(cameraMap2D.worldYOffset < -com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY())
            cameraMap2D.worldYOffset = -com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY();
        float f1 = (float)((double)(cameraMap2D.top - cameraMap2D.bottom) / cameraMap2D.worldScale);
        if(cameraMap2D.worldYOffset > com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeY() - com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() - (double)f1)
            cameraMap2D.worldYOffset = com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeY() - com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY() - (double)f1;
    }

    private void computeScales()
    {
        float f = (renders.win.dx * 1024F) / client.root.win.dx;
        float f1 = (renders.win.dy * 768F) / client.root.win.dy;
        int i = 0;
        float f2 = 0.064F;
        for(; i < scale.length; i++)
        {
            scale[i] = f2;
            float f3 = (float)(com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeX() * (double)f2);
            if(f3 < f)
                break;
            float f5 = (float)(com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeY() * (double)f2);
            if(f5 < f1)
                break;
            f2 /= 2.0F;
        }

        scales = i;
        if(scales < scale.length)
        {
            float f4 = f / (float)com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeX();
            float f6 = f1 / (float)com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeY();
            scale[i] = f4;
            if(f6 > f4)
                scale[i] = f6;
            scales = i + 1;
        }
        if(curScale >= scales)
            curScale = scales - 1;
        if(curScale < 0)
            curScale = 0;
    }

    private void drawGrid2D()
    {
        int i = gridStep();
        int j = (int)((cameraMap2D.worldXOffset + com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX()) / (double)i);
        int k = (int)((cameraMap2D.worldYOffset + com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY()) / (double)i);
        double d = (double)(cameraMap2D.right - cameraMap2D.left) / cameraMap2D.worldScale;
        double d1 = (double)(cameraMap2D.top - cameraMap2D.bottom) / cameraMap2D.worldScale;
        int l = (int)(d / (double)i) + 2;
        int i1 = (int)(d1 / (double)i) + 2;
        float f = (float)(((double)(j * i) - cameraMap2D.worldXOffset - com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX()) * cameraMap2D.worldScale + 0.5D);
        float f1 = (float)(((double)(k * i) - cameraMap2D.worldYOffset - com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsY()) * cameraMap2D.worldScale + 0.5D);
        float f2 = (float)((double)(l * i) * cameraMap2D.worldScale);
        float f3 = (float)((double)(i1 * i) * cameraMap2D.worldScale);
        float f4 = (float)((double)i * cameraMap2D.worldScale);
        _gridCount = 0;
        com.maddox.il2.engine.Render.drawBeginLines(-1);
        for(int j1 = 0; j1 <= i1; j1++)
        {
            float f5 = f1 + (float)j1 * f4;
            char c = (j1 + k) % 10 != 0 ? '\177' : '\300';
            line2XYZ[0] = f;
            line2XYZ[1] = f5;
            line2XYZ[2] = 0.0F;
            line2XYZ[3] = f + f2;
            line2XYZ[4] = f5;
            line2XYZ[5] = 0.0F;
            com.maddox.il2.engine.Render.drawLines(line2XYZ, 2, 1.0F, 0xff000000 | c << 16 | c << 8 | c, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE, 0);
            if(c == '\300')
                drawGridText(0, (int)f5, (k + j1) * i);
        }

        for(int k1 = 0; k1 <= l; k1++)
        {
            float f6 = f + (float)k1 * f4;
            char c1 = (k1 + j) % 10 != 0 ? '\177' : '\300';
            line2XYZ[0] = f6;
            line2XYZ[1] = f1;
            line2XYZ[2] = 0.0F;
            line2XYZ[3] = f6;
            line2XYZ[4] = f1 + f3;
            line2XYZ[5] = 0.0F;
            com.maddox.il2.engine.Render.drawLines(line2XYZ, 2, 1.0F, 0xff000000 | c1 << 16 | c1 << 8 | c1, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE, 0);
            if(c1 == '\300')
                drawGridText((int)f6, 0, (j + k1) * i);
        }

        com.maddox.il2.engine.Render.drawEnd();
        drawGridText();
    }

    private int gridStep()
    {
        float f = cameraMap2D.right - cameraMap2D.left;
        float f1 = cameraMap2D.top - cameraMap2D.bottom;
        double d = f;
        if(f1 < f)
            d = f1;
        d /= cameraMap2D.worldScale;
        int i = 0x186a0;
        for(int j = 0; j < 5; j++)
        {
            if((double)(i * 3) <= d)
                break;
            i /= 10;
        }

        return i;
    }

    private void drawGridText(int i, int j, int k)
    {
        if(i < 0 || j < 0 || k <= 0 || _gridCount == 20)
        {
            return;
        } else
        {
            _gridX[_gridCount] = i;
            _gridY[_gridCount] = j;
            _gridVal[_gridCount] = k;
            _gridCount++;
            return;
        }
    }

    private void drawGridText()
    {
        for(int i = 0; i < _gridCount; i++)
            gridFont.output(0xffc0c0c0, _gridX[i] + 2, _gridY[i] + 2, 0.0F, _gridVal[i] / 1000 + "." + (_gridVal[i] % 1000) / 100);

        _gridCount = 0;
    }

    private void drawAirports()
    {
        com.maddox.il2.engine.Mat mat = com.maddox.il2.engine.IconDraw.get("icons/runaway.mat");
        for(int i = 0; i < airdrome.size(); i++)
        {
            com.maddox.il2.gui.AirDrome airdrome1 = (com.maddox.il2.gui.AirDrome)airdrome.get(i);
            if(com.maddox.il2.engine.Actor.isValid(airdrome1.airport) && com.maddox.il2.engine.Actor.isAlive(airdrome1.airport) && (airdrome1.army == 0 || airdrome1.army == com.maddox.il2.ai.World.getPlayerArmy()))
            {
                com.maddox.JGP.Point3d point3d = airdrome1.airport.pos.getAbsPoint();
                float f = (float)((point3d.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
                float f1 = (float)((point3d.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
                com.maddox.il2.engine.IconDraw.setColor(airdrome1.color);
                com.maddox.il2.engine.IconDraw.render(mat, f, f1);
            }
        }

    }

    public void fillAirports()
    {
        airdrome.clear();
        java.util.ArrayList arraylist = new ArrayList();
        com.maddox.il2.ai.World.cur();
        com.maddox.il2.ai.World.getAirports(arraylist);
        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.il2.ai.Airport airport = (com.maddox.il2.ai.Airport)arraylist.get(i);
            com.maddox.il2.gui.AirDrome airdrome1 = new AirDrome();
            airdrome1.airport = airport;
            if((airport instanceof com.maddox.il2.ai.AirportCarrier) && com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.ai.AirportCarrier)airport).ship()))
                airdrome1.army = ((com.maddox.il2.ai.AirportCarrier)airport).ship().getArmy();
            airdrome.add(airdrome1);
        }

        com.maddox.JGP.Point3d point3d = new Point3d();
        for(int j = 0; j < airdrome.size(); j++)
        {
            com.maddox.il2.gui.AirDrome airdrome2 = (com.maddox.il2.gui.AirDrome)airdrome.get(j);
            com.maddox.JGP.Point3d point3d1 = airdrome2.airport.pos.getAbsPoint();
            com.maddox.il2.ai.World.land();
            point3d.set(point3d1.x, point3d1.y, com.maddox.il2.engine.Landscape.HQ((float)point3d1.x, (float)point3d1.y));
            int k = 0;
            if(airdrome2.army == 0)
            {
                com.maddox.il2.engine.Engine.collideEnv().getNearestEnemies(point3d, 2000D, 0, armyAccum);
                int l = 0;
                for(int i1 = 1; i1 < _army.length; i1++)
                {
                    if(l < _army[i1])
                    {
                        l = _army[i1];
                        k = i1;
                    }
                    _army[i1] = 0;
                }

                airdrome2.army = k;
            }
            airdrome2.color = com.maddox.il2.ai.Army.color(airdrome2.army);
        }

    }

    private void drawChiefs()
    {
        com.maddox.util.HashMapExt hashmapext = com.maddox.il2.engine.Engine.name2Actor();
        for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getValue();
            if(actor instanceof com.maddox.il2.ai.Chief)
            {
                com.maddox.il2.engine.Mat mat = actor.icon;
                if(mat != null)
                {
                    com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                    float f = (float)((point3d.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
                    float f1 = (float)((point3d.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
                    com.maddox.il2.engine.IconDraw.setColor(com.maddox.il2.ai.Army.color(actor.getArmy()));
                    com.maddox.il2.engine.IconDraw.render(mat, f, f1);
                }
            }
        }

    }

    private void drawAAAandFillAir()
    {
        double d = cameraMap2D.worldXOffset;
        double d1 = cameraMap2D.worldYOffset;
        double d2 = cameraMap2D.worldXOffset + (double)(cameraMap2D.right - cameraMap2D.left) / cameraMap2D.worldScale;
        double d3 = cameraMap2D.worldYOffset + (double)(cameraMap2D.top - cameraMap2D.bottom) / cameraMap2D.worldScale;
        java.util.List list = com.maddox.il2.engine.Engine.targets();
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
            if(actor instanceof com.maddox.il2.objects.air.Aircraft)
            {
                if(actor != com.maddox.il2.ai.World.getPlayerAircraft())
                {
                    com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
                    if(point3d.x >= d && point3d.x <= d2 && point3d.y >= d1 && point3d.y <= d3)
                        airs.add(actor);
                }
            } else
            if(actor instanceof com.maddox.il2.objects.vehicles.artillery.AAA)
            {
                com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
                if(point3d1.x >= d && point3d1.x <= d2 && point3d1.y >= d1 && point3d1.y <= d3)
                {
                    com.maddox.il2.engine.Mat mat = com.maddox.il2.engine.IconDraw.create(actor);
                    if(mat != null)
                    {
                        float f = (float)((point3d1.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
                        float f1 = (float)((point3d1.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
                        com.maddox.il2.engine.IconDraw.setColor(com.maddox.il2.ai.Army.color(actor.getArmy()));
                        com.maddox.il2.engine.IconDraw.render(mat, f, f1);
                    }
                }
            }
        }

    }

    private void drawAir()
    {
        int i = airs.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)airs.get(j);
            com.maddox.JGP.Point3d point3d = actor.pos.getAbsPoint();
            float f = (float)((point3d.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
            float f1 = (float)((point3d.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
            com.maddox.il2.engine.IconDraw.setColor(com.maddox.il2.ai.Army.color(actor.getArmy()));
            com.maddox.il2.engine.IconDraw.render(_iconAir, f, f1, actor.pos.getAbsOrient().azimut());
        }

        airs.clear();
    }

    private com.maddox.il2.engine.Mat getIconAir(int i)
    {
        java.lang.String s = null;
        switch(i)
        {
        case 0: // '\0'
            s = "normfly";
            break;

        case 1: // '\001'
            s = "takeoff";
            break;

        case 2: // '\002'
            s = "landing";
            break;

        case 3: // '\003'
            s = "gattack";
            break;

        default:
            return null;
        }
        return com.maddox.il2.engine.IconDraw.get("icons/" + s + ".mat");
    }

    private void drawPlayerPath()
    {
        com.maddox.il2.fm.Autopilotage autopilotage = com.maddox.il2.ai.World.getPlayerFM().AP;
        if(autopilotage == null)
            return;
        com.maddox.il2.ai.Way way = autopilotage.way;
        if(way == null)
            return;
        int i = way.Cur();
        int j = way.size();
        if(j <= 0 || i >= j)
            return;
        if(lineNXYZ.length / 3 <= j)
            lineNXYZ = new float[(j + 1) * 3];
        lineNCounter = 0;
        int k = 0;
        com.maddox.il2.engine.Render.drawBeginLines(-1);
        while(k < j) 
        {
            com.maddox.il2.ai.WayPoint waypoint = way.get(k++);
            waypoint.getP(_wayP);
            lineNXYZ[lineNCounter * 3 + 0] = (float)(((double)_wayP.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
            lineNXYZ[lineNCounter * 3 + 1] = (float)(((double)_wayP.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
            lineNXYZ[lineNCounter * 3 + 2] = 0.0F;
            lineNCounter++;
        }
        com.maddox.il2.engine.Render.drawLines(lineNXYZ, lineNCounter, 2.0F, 0xff000000, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE | com.maddox.il2.engine.Mat.BLEND, 3);
        if(!com.maddox.il2.ai.World.cur().diffCur.No_Map_Icons)
        {
            com.maddox.JGP.Point3d point3d = com.maddox.il2.ai.World.getPlayerAircraft().pos.getAbsPoint();
            lineNXYZ[0] = (float)((point3d.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
            lineNXYZ[1] = (float)((point3d.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
            lineNXYZ[2] = 0.0F;
            com.maddox.il2.ai.WayPoint waypoint2 = way.get(i);
            waypoint2.getP(_wayP);
            lineNXYZ[3] = (float)(((double)_wayP.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
            lineNXYZ[4] = (float)(((double)_wayP.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
            lineNXYZ[5] = 0.0F;
            com.maddox.il2.engine.Render.drawLines(lineNXYZ, 2, 2.0F, 0xff000000, com.maddox.il2.engine.Mat.NOWRITEZ | com.maddox.il2.engine.Mat.MODULATE | com.maddox.il2.engine.Mat.NOTEXTURE | com.maddox.il2.engine.Mat.BLEND, 3);
        }
        com.maddox.il2.engine.Render.drawEnd();
        for(int l = 0; l < j;)
        {
            com.maddox.il2.ai.WayPoint waypoint1 = way.get(l++);
            waypoint1.getP(_wayP);
            float f = (float)(((double)_wayP.x - cameraMap2D.worldXOffset) * cameraMap2D.worldScale);
            float f1 = (float)(((double)_wayP.y - cameraMap2D.worldYOffset) * cameraMap2D.worldScale);
            com.maddox.il2.engine.IconDraw.render(getIconAir(waypoint1.Action), f, f1);
        }

    }

    public com.maddox.il2.gui.GUIPad THIS()
    {
        return this;
    }

    public GUIPad(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        frameRegion = new GRegion(0.05F, 0.1F, 0.35F, 0.6F);
        targets = new ArrayList();
        scales = scale.length;
        curScale = 0;
        curScaleDirect = 1;
        bActive = false;
        line2XYZ = new float[6];
        _gridX = new int[20];
        _gridY = new int[20];
        _gridVal = new int[20];
        airdrome = new ArrayList();
        _army = new int[com.maddox.il2.ai.Army.amountNet()];
        armyAccum = new ArmyAccum();
        airs = new ArrayList();
        _wayP = new Point3f();
        lineNXYZ = new float[6];
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new com.maddox.il2.gui.GUIClient() {

            public void render()
            {
                int i = client.root.C.alpha;
                client.root.C.alpha = 255;
                super.render();
                client.root.C.alpha = i;
            }

        }
);
        frame = (com.maddox.gwindow.GWindowFramed)client.create(0.0F, 0.0F, 1.0F, 1.0F, false, new com.maddox.gwindow.GWindowFramed() {

            public void resized()
            {
                super.resized();
                if(renders != null)
                    renders.setPosSize(win.dx * mapView[0], win.dy * mapView[1], win.dx * mapView[2], win.dy * mapView[3]);
                if(renders1 != null)
                    renders1.setPosSize(0.0F, 0.0F, win.dx, win.dy);
            }

            public void render()
            {
            }

        }
);
        frame.bSizable = false;
        renders = new GUIRenders(frame, 0.0F, 0.0F, 1.0F, 1.0F, false);
        renders1 = new com.maddox.il2.engine.GUIRenders(frame, 0.0F, 0.0F, 1.0F, 1.0F, false) {

            public void mouseButton(int i, boolean flag, float f, float f1)
            {
                if(i == 0)
                {
                    bLPressed = flag;
                    mouseCursor = bLPressed ? 3 : 1;
                } else
                if(i == 1 && scales > 1)
                {
                    bRPressed = flag;
                    if(bRPressed && !bLPressed)
                    {
                        f -= THIS().renders.win.x;
                        f1 -= THIS().renders.win.y;
                        float f2 = (float)(cameraMap2D.worldXOffset + (double)f / cameraMap2D.worldScale);
                        float f3 = (float)(cameraMap2D.worldYOffset + (double)(THIS().renders.win.dy - f1 - 1.0F) / cameraMap2D.worldScale);
                        curScale+= = curScaleDirect;
                        if(curScaleDirect < 0)
                        {
                            if(curScale < 0)
                            {
                                curScale = 1;
                                curScaleDirect = 1;
                            }
                        } else
                        if(curScale >= scales)
                        {
                            curScale = scales - 2;
                            curScaleDirect = -1;
                        }
                        scaleCamera();
                        f2 = (float)((double)f2 - (double)(f - THIS().renders.win.dx / 2.0F) / cameraMap2D.worldScale);
                        f3 = (float)((double)f3 + (double)(f1 - THIS().renders.win.dy / 2.0F) / cameraMap2D.worldScale);
                        setPosCamera(f2, f3);
                    }
                }
            }

            public void mouseMove(float f, float f1)
            {
                if(bLPressed && bRPressed)
                    frame.setPos(frame.win.x + root.mouseStep.dx, frame.win.y + root.mouseStep.dy);
                else
                if(bLPressed)
                {
                    cameraMap2D.worldXOffset -= (double)root.mouseStep.dx / cameraMap2D.worldScale;
                    cameraMap2D.worldYOffset += (double)root.mouseStep.dy / cameraMap2D.worldScale;
                    clipCamera();
                }
            }

            boolean bLPressed;
            boolean bRPressed;

            
            {
                bLPressed = false;
                bRPressed = false;
            }
        }
;
        cameraMap2D = new CameraOrtho2D();
        cameraMap2D.worldScale = scale[curScale];
        renderMap2D = new RenderMap2D(renders.renders, 1.0F);
        renderMap2D.setCamera(cameraMap2D);
        renderMap2D.setShow(true);
        cameraMap2D1 = new CameraOrtho2D();
        renderMap2D1 = new RenderMap2D1(renders1.renders, 1.0F);
        renderMap2D1.setCamera(cameraMap2D1);
        renderMap2D1.setShow(true);
        com.maddox.il2.engine.LightEnvXY lightenvxy = new LightEnvXY();
        renderMap2D.setLightEnv(lightenvxy);
        renderMap2D1.setLightEnv(lightenvxy);
        lightenvxy.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
        com.maddox.JGP.Vector3f vector3f = new Vector3f(1.0F, -2F, -1F);
        vector3f.normalize();
        lightenvxy.sun().set(vector3f);
        gridFont = com.maddox.il2.engine.TTFont.font[1];
        mesh = com.maddox.gwindow.GMesh.New("gui/game/pad/mono.sim");
        _iconAir = com.maddox.il2.engine.Mat.New("icons/plane.mat");
        emptyMat = com.maddox.il2.engine.Mat.New("icons/empty.mat");
        main = com.maddox.il2.game.Main3D.cur3D();
        client.hideWindow();
    }

    public static boolean bStartMission = true;
    private com.maddox.il2.game.Main3D main;
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.gwindow.GWindowFramed frame;
    public com.maddox.gwindow.GRegion frameRegion;
    public float mapView[] = {
        0.09F, 0.09F, 0.82F, 0.82F
    };
    public com.maddox.il2.engine.GUIRenders renders;
    public com.maddox.il2.engine.GUIRenders renders1;
    public com.maddox.il2.gui.RenderMap2D renderMap2D;
    public com.maddox.il2.engine.CameraOrtho2D cameraMap2D;
    public com.maddox.il2.gui.RenderMap2D1 renderMap2D1;
    public com.maddox.il2.engine.CameraOrtho2D cameraMap2D1;
    public boolean savedUseMesh;
    public int saveIconDx;
    public int saveIconDy;
    protected java.util.ArrayList targets;
    public com.maddox.gwindow.GMesh mesh;
    public com.maddox.il2.engine.TTFont gridFont;
    public com.maddox.il2.engine.Mat emptyMat;
    public com.maddox.il2.engine.Mat _iconAir;
    private float scale[] = {
        0.064F, 0.032F, 0.016F, 0.008F, 0.004F, 0.002F, 0.001F, 0.0005F, 0.00025F
    };
    private int scales;
    private int curScale;
    private int curScaleDirect;
    public boolean bActive;
    private float line2XYZ[];
    private int _gridCount;
    private int _gridX[];
    private int _gridY[];
    private int _gridVal[];
    private java.util.ArrayList airdrome;
    int _army[];
    com.maddox.il2.gui.ArmyAccum armyAccum;
    private java.util.ArrayList airs;
    private com.maddox.JGP.Point3f _wayP;
    private float lineNXYZ[];
    private int lineNCounter;

















}
