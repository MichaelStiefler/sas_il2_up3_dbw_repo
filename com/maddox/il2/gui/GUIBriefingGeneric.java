// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIBriefingGeneric.java

package com.maddox.il2.gui;

import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowScrollingDialogClient;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Front;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Land2D;
import com.maddox.il2.engine.Land2DText;
import com.maddox.il2.engine.Land2Dn;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.LightEnvXY;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Renders;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.HomePath;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIDialogClient, GUISeparate, SquareLabels

public class GUIBriefingGeneric extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(gwindow == bPrev)
            {
                doBack();
                return true;
            }
            if(gwindow == bNext)
            {
                doNext();
                return true;
            }
            if(gwindow == bDifficulty)
            {
                doDiff();
                return true;
            }
            if(gwindow == bLoodout)
            {
                doLoodout();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(32F), y1024(624F), x1024(960F), 2.0F);
            setCanvasColorWHITE();
            com.maddox.il2.gui.GUILookAndFeel guilookandfeel = (com.maddox.il2.gui.GUILookAndFeel)lookAndFeel();
            guilookandfeel.drawBevel(this, x1024(32F), y1024(32F), x1024(528F), y1024(560F), guilookandfeel.bevelComboDown, guilookandfeel.basicelements);
            setCanvasFont(0);
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            clientRender();
        }

        public void resized()
        {
            super.resized();
            if(renders != null)
            {
                com.maddox.il2.gui.GUILookAndFeel guilookandfeel = (com.maddox.il2.gui.GUILookAndFeel)lookAndFeel();
                com.maddox.gwindow.GBevel gbevel = guilookandfeel.bevelComboDown;
                renders.setPosSize(x1024(32F) + gbevel.L.dx, y1024(32F) + gbevel.T.dy, x1024(528F) - gbevel.L.dx - gbevel.R.dx, y1024(560F) - gbevel.T.dy - gbevel.B.dy);
            }
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            bPrev.setPosC(x1024(104F), y1024(680F));
            bDifficulty.setPosC(x1024(502F), y1024(680F));
            bLoodout.setPosC(x1024(744F), y1024(680F));
            bNext.setPosC(x1024(968F), y1024(680F));
            wScrollDescription.setPosSize(x1024(592F), y1024(32F), x1024(400F), y1024(560F));
            clientSetPosSize();
        }

        public DialogClient()
        {
        }
    }

    public class ScrollDescript extends com.maddox.gwindow.GWindowScrollingDialogClient
    {

        public void created()
        {
            fixed = wDescript = createDescript(this);
            fixed.bNotify = true;
            bNotify = true;
        }

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(super.notify(gwindow, i, j))
            {
                return true;
            } else
            {
                notify(i, j);
                return false;
            }
        }

        public void resized()
        {
            if(wDescript != null)
                wDescript.computeSize();
            super.resized();
            if(vScroll.isVisible())
            {
                com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
                vScroll.setPos(win.dx - lookAndFeel().getVScrollBarW() - gbevel.R.dx, gbevel.T.dy);
                vScroll.setSize(lookAndFeel().getVScrollBarW(), win.dy - gbevel.T.dy - gbevel.B.dy);
            }
        }

        public void render()
        {
            setCanvasColorWHITE();
            com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
            lookAndFeel().drawBevel(this, 0.0F, 0.0F, win.dx, win.dy, gbevel, ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).basicelements, true);
        }

        public ScrollDescript()
        {
        }
    }

    public class Descript extends com.maddox.gwindow.GWindowDialogClient
    {

        public void render()
        {
            java.lang.String s = textDescription();
            if(s != null)
            {
                com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
                setCanvasFont(0);
                setCanvasColorBLACK();
                root.C.clip.y += gbevel.T.dy;
                root.C.clip.dy -= gbevel.T.dy + gbevel.B.dy;
                drawLines(gbevel.L.dx + 2.0F, gbevel.T.dy + 2.0F, s, 0, s.length(), win.dx - gbevel.L.dx - gbevel.R.dx - 4F, root.C.font.height);
            }
        }

        public void computeSize()
        {
            java.lang.String s = textDescription();
            if(s != null)
            {
                win.dx = parentWindow.win.dx;
                com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
                setCanvasFont(0);
                int i = computeLines(s, 0, s.length(), win.dx - gbevel.L.dx - gbevel.R.dx - 4F);
                win.dy = root.C.font.height * (float)i + gbevel.T.dy + gbevel.B.dy + 4F;
                if(win.dy > parentWindow.win.dy)
                {
                    win.dx = parentWindow.win.dx - lookAndFeel().getVScrollBarW();
                    int j = computeLines(s, 0, s.length(), win.dx - gbevel.L.dx - gbevel.R.dx - 4F);
                    win.dy = root.C.font.height * (float)j + gbevel.T.dy + gbevel.B.dy + 4F;
                }
            } else
            {
                win.dx = parentWindow.win.dx;
                win.dy = parentWindow.win.dy;
            }
        }

        public Descript()
        {
        }
    }

    public class RenderMap2D extends com.maddox.il2.engine.Render
    {

        public void preRender()
        {
            if(main.land2D == null)
            {
                return;
            } else
            {
                com.maddox.il2.ai.Front.preRender(false);
                return;
            }
        }

        public void render()
        {
            if(main.land2D == null)
                return;
            main.land2D.render();
            if(main.land2DText != null)
                main.land2DText.render();
            drawGrid2D();
            com.maddox.il2.ai.Front.render(false);
            int i = (int)java.lang.Math.round((32D * (double)renders.root.win.dx) / 1024D);
            com.maddox.il2.engine.IconDraw.setScrSize(i, i);
            doRenderMap2D();
            com.maddox.il2.gui.SquareLabels.draw(cameraMap2D, com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX(), com.maddox.il2.game.Main3D.cur3D().land2D.worldOfsX(), com.maddox.il2.game.Main3D.cur3D().land2D.mapSizeX());
        }

        public RenderMap2D(com.maddox.il2.engine.Renders renders1, float f)
        {
            super(renders1, f);
            useClearDepth(false);
            useClearColor(false);
        }
    }


    public void _enter()
    {
        client.activateWindow();
        try
        {
            com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Main.cur().currentMissionFile;
            briefSound = sectfile.get("MAIN", "briefSound");
            java.lang.String s = com.maddox.il2.game.Main.cur().currentMissionFile.fileName();
            java.lang.String s1 = sectfile.get("MAIN", "MAP");
            if(!s.equals(curMissionName) || !s1.equals(curMapName) || curMissionNum != com.maddox.il2.game.Main.cur().missionCounter || main.land2D == null)
            {
                dialogClient.resized();
                fillTextDescription();
                fillMap();
                com.maddox.il2.ai.Front.loadMission(sectfile);
                curMissionName = s;
                curMapName = s1;
                curMissionNum = com.maddox.il2.game.Main.cur().missionCounter;
            }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        com.maddox.il2.ai.Front.setMarkersChanged();
        wScrollDescription.resized();
        if(wScrollDescription.vScroll.isVisible())
            wScrollDescription.vScroll.setPos(0.0F, true);
    }

    public void _leave()
    {
        client.hideWindow();
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
        cameraMap2D.worldScale = (scale[curScale] * renders.root.win.dx) / 1024F;
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
        float f = (renders.win.dx * 1024F) / renders.root.win.dx;
        float f1 = (renders.win.dy * 768F) / renders.root.win.dy;
        int i = 0;
        float f2 = 0.064F;
        for(; i < scale.length; i++)
        {
            scale[i] = f2;
            float f3 = landDX * f2;
            if(f3 < f)
                break;
            float f5 = landDY * f2;
            if(f5 < f1)
                break;
            f2 /= 2.0F;
        }

        scales = i;
        if(scales < scale.length)
        {
            float f4 = f / landDX;
            float f6 = f1 / landDY;
            scale[i] = f4;
            if(f6 > f4)
                scale[i] = f6;
            scales = i + 1;
        }
        curScale = scales - 1;
        curScaleDirect = -1;
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

    protected void doRenderMap2D()
    {
    }

    protected void doMouseButton(int i, boolean flag, float f, float f1)
    {
        renders;
        if(i != 0)
            break MISSING_BLOCK_LABEL_48;
        bLPressed = flag;
        renders;
        if(!bLPressed) goto _L2; else goto _L1
_L1:
        renders;
        7;
          goto _L3
_L2:
        renders;
        3;
_L3:
        mouseCursor;
        break MISSING_BLOCK_LABEL_280;
        renders;
        if(i == 1 && scales > 1)
        {
            bRPressed = flag;
            if(bRPressed && !bLPressed)
            {
                float f2 = (float)(cameraMap2D.worldXOffset + (double)f / cameraMap2D.worldScale);
                float f3 = (float)(cameraMap2D.worldYOffset + (double)(renders.win.dy - f1 - 1.0F) / cameraMap2D.worldScale);
                curScale += curScaleDirect;
                if(curScaleDirect < 0)
                {
                    if(curScale < 0)
                    {
                        curScale = 1;
                        curScaleDirect = 1;
                    }
                } else
                if(curScale == scales)
                {
                    curScale = scales - 2;
                    curScaleDirect = -1;
                }
                scaleCamera();
                f2 = (float)((double)f2 - (double)(f - renders.win.dx / 2.0F) / cameraMap2D.worldScale);
                f3 = (float)((double)f3 + (double)(f1 - renders.win.dy / 2.0F) / cameraMap2D.worldScale);
                setPosCamera(f2, f3);
            }
        }
    }

    protected void doMouseMove(float f, float f1)
    {
        if(bLPressed && renders.mouseCursor == 7)
        {
            cameraMap2D.worldXOffset -= (double)renders.root.mouseStep.dx / cameraMap2D.worldScale;
            cameraMap2D.worldYOffset += (double)renders.root.mouseStep.dy / cameraMap2D.worldScale;
            clipCamera();
        }
    }

    protected void createRenderWindow(com.maddox.gwindow.GWindow gwindow)
    {
        renders = new com.maddox.il2.engine.GUIRenders(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false) {

            public void mouseButton(int i, boolean flag, float f, float f1)
            {
                doMouseButton(i, flag, f, f1);
            }

            public void mouseMove(float f, float f1)
            {
                doMouseMove(f, f1);
            }

        }
;
        renders.mouseCursor = 3;
        renders.bNotify = true;
        cameraMap2D = new CameraOrtho2D();
        cameraMap2D.worldScale = scale[curScale];
        renderMap2D = new RenderMap2D(renders.renders, 1.0F);
        renderMap2D.setCamera(cameraMap2D);
        renderMap2D.setShow(true);
        com.maddox.il2.engine.LightEnvXY lightenvxy = new LightEnvXY();
        renderMap2D.setLightEnv(lightenvxy);
        lightenvxy.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
        com.maddox.JGP.Vector3f vector3f = new Vector3f(1.0F, -2F, -1F);
        vector3f.normalize();
        lightenvxy.sun().set(vector3f);
        gridFont = com.maddox.il2.engine.TTFont.font[1];
        emptyMat = com.maddox.il2.engine.Mat.New("icons/empty.mat");
        main = com.maddox.il2.game.Main3D.cur3D();
    }

    protected void fillMap()
        throws java.lang.Exception
    {
        com.maddox.rts.SectFile sectfile = com.maddox.il2.game.Main.cur().currentMissionFile;
        java.lang.String s = sectfile.get("MAIN", "MAP");
        if(s == null)
            throw new Exception("No MAP in mission file ");
        com.maddox.rts.SectFile sectfile1 = new SectFile("maps/" + s);
        java.lang.String s1 = sectfile1.get("MAP", "TypeMap", (java.lang.String)null);
        if(s1 == null)
            throw new Exception("Bad MAP description in mission file ");
        com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s1);
        if(numbertokenizer.hasMoreTokens())
        {
            numbertokenizer.next();
            if(numbertokenizer.hasMoreTokens())
                s1 = numbertokenizer.next();
        }
        s1 = com.maddox.rts.HomePath.concatNames("maps/" + s, s1);
        int ai[] = new int[3];
        if(!com.maddox.il2.engine.Mat.tgaInfo(s1, ai))
            throw new Exception("Bad MAP description in mission file ");
        landDX = (float)ai[0] * 200F;
        landDY = (float)ai[1] * 200F;
        if(main.land2D != null)
        {
            if(!main.land2D.isDestroyed())
                main.land2D.destroy();
            main.land2D = null;
        }
        s1 = null;
        int i = sectfile1.sectionIndex("MAP2D");
        if(i >= 0)
        {
            int j = sectfile1.vars(i);
            if(j > 0)
            {
                main.land2D = new Land2Dn(s, landDX, landDY);
                landDX = (float)main.land2D.mapSizeX();
                landDY = (float)main.land2D.mapSizeY();
            }
        }
        if(main.land2DText == null)
            main.land2DText = new Land2DText();
        else
            main.land2DText.clear();
        int k = sectfile1.sectionIndex("text");
        if(k >= 0 && sectfile1.vars(k) > 0)
        {
            java.lang.String s2 = sectfile1.var(k, 0);
            main.land2DText.load(com.maddox.rts.HomePath.concatNames("maps/" + s, s2));
        }
        computeScales();
        scaleCamera();
        setPosCamera(landDX / 2.0F, landDY / 2.0F);
    }

    protected void prepareTextDescription(int i)
    {
        if(textDescription == null)
            return;
        if(textArmyDescription == null || textArmyDescription.length != i)
            textArmyDescription = new java.lang.String[i];
        for(int j = 0; j < i; j++)
        {
            textArmyDescription[j] = null;
            prepareTextDescriptionArmy(j);
        }

    }

    private void prepareTextDescriptionArmy(int i)
    {
        java.lang.String s = (com.maddox.il2.ai.Army.name(i) + ">").toUpperCase();
        int j = 0;
        int k = textDescription.length();
        java.lang.StringBuffer stringbuffer = new StringBuffer();
        while(j < k) 
        {
            int l = textDescription.indexOf("<ARMY", j);
            if(l >= j)
            {
                if(l > j)
                    subString(stringbuffer, textDescription, j, l);
                int i1 = textDescription.indexOf("</ARMY>", l);
                if(i1 == -1)
                    i1 = k;
                for(l += "<ARMY".length(); l < k && java.lang.Character.isSpaceChar(textDescription.charAt(l)); l++);
                if(l == k)
                {
                    j = k;
                    break;
                }
                if(textDescription.startsWith(s, l))
                {
                    l += s.length();
                    if(l < i1 && textDescription.charAt(l) == '\n')
                        l++;
                    subString(stringbuffer, textDescription, l, i1);
                }
                j = i1 + "</ARMY>".length();
                if(j < k && textDescription.charAt(j) == '\n')
                    j++;
            } else
            {
                subString(stringbuffer, textDescription, j, k);
                j = k;
            }
        }
        textArmyDescription[i] = new String(stringbuffer);
    }

    private void subString(java.lang.StringBuffer stringbuffer, java.lang.String s, int i, int j)
    {
        while(i < j) 
            stringbuffer.append(s.charAt(i++));
    }

    protected void fillTextDescription()
    {
        try
        {
            java.lang.String s = com.maddox.il2.game.Main.cur().currentMissionFile.fileName();
            for(int i = s.length() - 1; i > 0; i--)
            {
                char c = s.charAt(i);
                if(c == '\\' || c == '/')
                    break;
                if(c != '.')
                    continue;
                s = s.substring(0, i);
                break;
            }

            java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle(s, com.maddox.rts.RTSConf.cur.locale);
            textDescription = resourcebundle.getString("Description");
        }
        catch(java.lang.Exception exception)
        {
            textDescription = null;
            textArmyDescription = null;
        }
    }

    protected java.lang.String textDescription()
    {
        return textDescription;
    }

    protected com.maddox.il2.gui.Descript createDescript(com.maddox.gwindow.GWindow gwindow)
    {
        return (com.maddox.il2.gui.Descript)gwindow.create(new Descript());
    }

    protected void doNext()
    {
    }

    protected void doDiff()
    {
    }

    protected void doBack()
    {
    }

    protected void doLoodout()
    {
    }

    protected void clientRender()
    {
    }

    protected void clientSetPosSize()
    {
    }

    protected void clientInit(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
    }

    protected java.lang.String infoMenuInfo()
    {
        return "????????";
    }

    protected void init(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = infoMenuInfo();
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        createRenderWindow(dialogClient);
        dialogClient.create(wScrollDescription = new ScrollDescript());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        bPrev = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        bDifficulty = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bLoodout = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        bNext = (com.maddox.il2.gui.GUIButton)dialogClient.addDefault(new GUIButton(dialogClient, gtexture, 0.0F, 192F, 48F, 48F));
        clientInit(gwindowroot);
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public GUIBriefingGeneric(int i)
    {
        super(i);
        curMissionNum = -1;
        briefSound = null;
        scales = scale.length;
        curScale = scales - 1;
        curScaleDirect = -1;
        line2XYZ = new float[6];
        _gridX = new int[20];
        _gridY = new int[20];
        _gridVal = new int[20];
        bLPressed = false;
        bRPressed = false;
    }

    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.ScrollDescript wScrollDescription;
    public com.maddox.il2.gui.Descript wDescript;
    public com.maddox.il2.gui.GUIButton bLoodout;
    public com.maddox.il2.gui.GUIButton bDifficulty;
    public com.maddox.il2.gui.GUIButton bPrev;
    public com.maddox.il2.gui.GUIButton bNext;
    public java.lang.String textDescription;
    public java.lang.String textArmyDescription[];
    public java.lang.String curMissionName;
    public int curMissionNum;
    public java.lang.String curMapName;
    protected java.lang.String briefSound;
    protected com.maddox.il2.game.Main3D main;
    protected com.maddox.il2.engine.GUIRenders renders;
    protected com.maddox.il2.gui.RenderMap2D renderMap2D;
    protected com.maddox.il2.engine.CameraOrtho2D cameraMap2D;
    protected com.maddox.il2.engine.TTFont gridFont;
    protected com.maddox.il2.engine.Mat emptyMat;
    protected float scale[] = {
        0.064F, 0.032F, 0.016F, 0.008F, 0.004F, 0.002F, 0.001F, 0.0005F, 0.00025F
    };
    protected int scales;
    protected int curScale;
    protected int curScaleDirect;
    protected float landDX;
    protected float landDY;
    private float line2XYZ[];
    private int _gridCount;
    private int _gridX[];
    private int _gridY[];
    private int _gridVal[];
    protected boolean bLPressed;
    protected boolean bRPressed;

}
