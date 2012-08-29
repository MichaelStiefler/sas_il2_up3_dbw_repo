// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GUIObjectView.java

package com.maddox.il2.gui;

import com.maddox.JGP.Color4f;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.LightEnvXY;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.Renders;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.GameState;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.ActorSimpleHMesh;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.rts.LDRres;
import com.maddox.rts.Mouse;
import com.maddox.rts.ObjIO;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.ResourceBundle;

// Referenced classes of package com.maddox.il2.gui:
//            GUIClient, GUIInfoMenu, GUIInfoName, GUILookAndFeel, 
//            GUIButton, GUIObjectInspector, GUIDialogClient, GUISeparate

public class GUIObjectView extends com.maddox.il2.game.GameState
{
    public class DialogClient extends com.maddox.il2.gui.GUIDialogClient
    {

        public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
        {
            if(i != 2)
                return super.notify(gwindow, i, j);
            if(isMouseCaptured())
                return true;
            if(gwindow == wCountry)
            {
                fillObjects();
                int k = wCountry.getSelected();
                if(k >= 0)
                {
                    com.maddox.il2.game.Main3D.menuMusicPlay(k != 0 ? "de" : "ru");
                    com.maddox.il2.gui.GUIObjectInspector.s_country = wCountry.getSelected();
                    if(wTable.countRows() > 0)
                    {
                        com.maddox.il2.gui.GUIObjectInspector.s_object = 0;
                        com.maddox.il2.gui.GUIObjectInspector.s_scroll = 0.0F;
                        wTable.setSelect(com.maddox.il2.gui.GUIObjectInspector.s_object, 0);
                        if(wTable.vSB.isVisible())
                            wTable.vSB.setPos(com.maddox.il2.gui.GUIObjectInspector.s_scroll, true);
                    }
                }
                return true;
            }
            if(gwindow == wText)
            {
                com.maddox.il2.gui.GUIObjectInspector.s_object = wTable.selectRow;
                com.maddox.il2.gui.GUIObjectInspector.s_scroll = wTable.vSB.pos();
                com.maddox.il2.game.Main.stateStack().change(22);
                return true;
            }
            if(gwindow == wPrev)
            {
                com.maddox.il2.gui.GUIObjectInspector.s_object = wTable.selectRow;
                com.maddox.il2.gui.GUIObjectInspector.s_scroll = wTable.vSB.pos();
                com.maddox.il2.game.Main.stateStack().change(22);
                com.maddox.il2.game.Main.stateStack().pop();
                return true;
            } else
            {
                return super.notify(gwindow, i, j);
            }
        }

        public void render()
        {
            super.render();
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(40F), y1024(620F), x1024(250F), 2.0F);
            com.maddox.il2.gui.GUISeparate.draw(this, com.maddox.gwindow.GColor.Gray, x1024(320F), y1024(32F), 2.0F, y1024(650F));
            setCanvasColor(com.maddox.gwindow.GColor.Gray);
            setCanvasFont(0);
            draw(x1024(40F), y1024(40F), x1024(240F), y1024(32F), 0, i18n("obj.SelectCountry"));
            draw(x1024(104F), y1024(652F), x1024(192F), y1024(48F), 0, i18n("obj.Back"));
            draw(x1024(730F), y1024(652F), x1024(192F), y1024(48F), 2, i18n("obj.Text"));
            root.C.font = helpFont;
            draw(x1024(360F), y1024(606F), x1024(560F), y1024(16F), 0, i18n("obj.Help0"));
            draw(x1024(360F), y1024(622F), x1024(470F), y1024(16F), 0, i18n("obj.Help1"));
            setCanvasColorWHITE();
            com.maddox.gwindow.GBevel gbevel = ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).bevelComboDown;
            lookAndFeel().drawBevel(this, x1024(360F) - gbevel.L.dx, y1024(50F) - gbevel.T.dy, x1024(625F) + gbevel.R.dx * 2.0F, y1024(540F) + gbevel.B.dy * 2.0F, gbevel, ((com.maddox.il2.gui.GUILookAndFeel)lookAndFeel()).basicelements, false);
        }

        public void setPosSize()
        {
            set1024PosSize(0.0F, 32F, 1024F, 736F);
            wPrev.setPosC(x1024(64F), y1024(676F));
            wText.setPosC(x1024(960F), y1024(676F));
            wCountry.setPosSize(x1024(40F), y1024(82F), x1024(246F), M(2.0F));
            wTable.setPosSize(x1024(40F), y1024(194F), x1024(246F), y1024(400F));
            wRenders.setPosSize(x1024(360F), y1024(50F), x1024(625F), y1024(540F));
        }

        public DialogClient()
        {
        }
    }

    public class Table extends com.maddox.gwindow.GWindowTable
    {

        public int countRows()
        {
            return objects == null ? 0 : objects.size();
        }

        public void renderCell(int i, int j, boolean flag, float f, float f1)
        {
            setCanvasFont(0);
            java.lang.String s = ((com.maddox.il2.gui.ObjectInfo)objects.get(i)).name;
            if(flag)
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, lookAndFeel().regionWhite);
                setCanvasColorWHITE();
                draw(0.0F, 0.0F, f, f1, 0, s);
            } else
            {
                setCanvasColorBLACK();
                draw(0.0F, 0.0F, f, f1, 0, s);
            }
        }

        public void setSelect(int i, int j)
        {
            super.setSelect(i, j);
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

        public void afterCreated()
        {
            super.afterCreated();
            bColumnsSizable = false;
            addColumn(com.maddox.il2.game.I18N.gui("obj.ObjectTypesList"), null);
            vSB.scroll = rowHeight(0);
            bNotify = true;
            wClient.bNotify = true;
            resized();
        }

        public void resolutionChanged()
        {
            vSB.scroll = rowHeight(0);
            super.resolutionChanged();
        }

        public java.util.ArrayList objects;

        public Table(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow, 2.0F, 4F, 20F, 16F);
            objects = new ArrayList();
        }
    }

    public class WRenders extends com.maddox.il2.engine.GUIRenders
    {

        public void mouseMove(float f, float f1)
        {
            float f2 = root.mouseStep.dx;
            float f3 = root.mouseStep.dy;
            if(com.maddox.rts.Mouse.adapter().isInvert())
                f3 = -f3;
            if(isMouseCaptured() && MODE_ROTATE)
            {
                ROT_X += f2 / 2.0F;
                ROT_Y -= f3 / 2.0F;
                if(bGround)
                {
                    if(ROT_Y > 20)
                        ROT_Y = 20;
                    if(ROT_Y < -50)
                        ROT_Y = -50;
                }
            }
            if(isMouseCaptured() && MODE_SCALE)
            {
                com.maddox.il2.gui.GUIObjectView.SCALE_FACTOR -= f3 / 2.0F;
                if(com.maddox.il2.gui.GUIObjectView.SCALE_FACTOR > com.maddox.il2.gui.GUIObjectView.Z_DIST_FAR)
                    com.maddox.il2.gui.GUIObjectView.SCALE_FACTOR = com.maddox.il2.gui.GUIObjectView.Z_DIST_FAR;
                if(com.maddox.il2.gui.GUIObjectView.SCALE_FACTOR < com.maddox.il2.gui.GUIObjectView.Z_DIST_NEAR)
                    com.maddox.il2.gui.GUIObjectView.SCALE_FACTOR = com.maddox.il2.gui.GUIObjectView.Z_DIST_NEAR;
            }
        }

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            if(i == 1 && flag)
            {
                mouseCursor = 0;
                mouseCapture(true);
                MODE_SCALE = true;
                MODE_ROTATE = false;
            }
            if(i == 0 && flag)
            {
                mouseCursor = 0;
                mouseCapture(true);
                MODE_ROTATE = true;
                MODE_SCALE = false;
            }
            if(!isMouseCaptured())
            {
                super.mouseButton(i, flag, f, f1);
                return;
            }
            if(!flag)
            {
                mouseCursor = 1;
                mouseCapture(false);
            }
        }

        public void created()
        {
            render3D = new _Render3D(renders, 1.0F);
            render3D.camera3D = new Camera3D();
            render3D.camera3D.set(50F, 1.0F, 20000F);
            render3D.setCamera(render3D.camera3D);
            com.maddox.il2.engine.LightEnvXY lightenvxy = new LightEnvXY();
            render3D.setLightEnv(lightenvxy);
            lightenvxy.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
            com.maddox.JGP.Vector3f vector3f = new Vector3f(-1F, 1.0F, -1F);
            vector3f.normalize();
            lightenvxy.sun().set(vector3f);
            bNotify = true;
        }

        public com.maddox.il2.gui._Render3D render3D;
        public boolean MODE_SCALE;
        public boolean MODE_ROTATE;

        public WRenders()
        {
            MODE_SCALE = false;
            MODE_ROTATE = false;
        }
    }

    public class _Render3D extends com.maddox.il2.engine.Render
    {

        public void preRender()
        {
            checkMesh();
            if(com.maddox.il2.engine.Actor.isValid(actorMesh))
            {
                if(animateMeshA != 0.0F || animateMeshT != 0.0F)
                {
                    actorMesh.pos.getAbs(_orient);
                    _orient.set(_orient.azimut() + animateMeshA * wRenders.root.deltaTimeSec, _orient.tangage() + animateMeshT * wRenders.root.deltaTimeSec, 0.0F);
                    actorMesh.pos.setAbs(_orient);
                    actorMesh.pos.reset();
                }
                worldMesh.draw.preRender(worldMesh);
                isShadow = (actorMesh.draw.preRender(actorMesh) & 4) != 0;
            }
        }

        public void render()
        {
            if(com.maddox.il2.engine.Actor.isValid(actorMesh))
            {
                com.maddox.il2.engine.Render.prepareStates();
                worldMesh.draw.render(worldMesh);
                if(isShadow && bGround)
                    actorMesh.draw.renderShadowProjective(actorMesh);
                actorMesh.draw.render(actorMesh);
            }
        }

        public void checkMesh()
        {
            int i = wTable.selectRow;
            if(i < 0)
            {
                if(com.maddox.il2.engine.Actor.isValid(actorMesh))
                    actorMesh.destroy();
                actorMesh = null;
            }
            com.maddox.il2.gui.ObjectInfo objectinfo = (com.maddox.il2.gui.ObjectInfo)wTable.objects.get(i);
            if(meshName == objectinfo.meshName && com.maddox.il2.engine.Actor.isValid(actorMesh))
            {
                double d = ((com.maddox.il2.engine.ActorMesh)actorMesh).mesh().visibilityR();
                if(bGround)
                {
                    d *= java.lang.Math.cos(0.78539816339744828D) / java.lang.Math.sin(((double)camera3D.FOV() * 3.1415926535897931D) / 180D / 2D);
                    d -= com.maddox.il2.gui.GUIObjectView.Z_GAP;
                    if(d < com.maddox.il2.gui.GUIObjectView.Z_DIST_NEAR)
                        com.maddox.il2.gui.GUIObjectView.Z_DIST_NEAR = d;
                    d = com.maddox.il2.gui.GUIObjectView.SCALE_FACTOR;
                    _point.set(-d, 0.0D, 0.0D);
                    _o.set(ROT_X, ROT_Y - 45, 0.0F);
                } else
                {
                    d *= java.lang.Math.cos(0.26179938779914941D) / java.lang.Math.sin(((double)camera3D.FOV() * 3.1415926535897931D) / 180D / 2D);
                    d -= com.maddox.il2.gui.GUIObjectView.Z_GAP;
                    if(d < com.maddox.il2.gui.GUIObjectView.Z_DIST_NEAR)
                        com.maddox.il2.gui.GUIObjectView.Z_DIST_NEAR = d;
                    d = com.maddox.il2.gui.GUIObjectView.SCALE_FACTOR;
                    _point.set(-d, 0.0D, 0.0D);
                    _o.set(ROT_X, ROT_Y, 0.0F);
                    for(int j = 1; j <= 6; j++)
                        if(((com.maddox.il2.objects.ActorSimpleHMesh)actorMesh).hierMesh().chunkFindCheck("Prop" + j + "_D0") != -1)
                            ((com.maddox.il2.objects.ActorSimpleHMesh)actorMesh).hierMesh().chunkSetAngles("Prop" + j + "_D0", 0.0F, -propRot + (float)(j * 50), 0.0F);

                    propRot = (propRot + 20F) % 360F;
                }
                _o.transform(_point);
                camera3D.pos.setAbs(_point, _o);
                return;
            }
            if(com.maddox.il2.engine.Actor.isValid(actorMesh))
                actorMesh.destroy();
            actorMesh = null;
            meshName = objectinfo.meshName;
            bGround = objectinfo._bGround;
            if(meshName == null)
                return;
            double d1 = 20D;
            com.maddox.il2.gui.GUIObjectView.SCALE_FACTOR = com.maddox.il2.gui.GUIObjectView.Z_DIST_BORN;
            ROT_Y = 20;
            ROT_X = 220;
            worldMesh = new ActorSimpleMesh("3do/GUI/ObjectInspector/" + com.maddox.il2.gui.GUIObjectInspector.type + "/mono.sim");
            if(meshName.toLowerCase().endsWith(".sim"))
            {
                try
                {
                    actorMesh = new ActorSimpleMesh(meshName);
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println(exception.getMessage());
                    actorMesh = null;
                    return;
                }
                d1 = ((com.maddox.il2.engine.ActorMesh)actorMesh).mesh().visibilityR();
                double d2 = 0.0D;
                int k = ((com.maddox.il2.objects.ActorSimpleMesh)actorMesh).mesh().hookFind("Ground_Level");
                if(k != -1)
                {
                    com.maddox.JGP.Matrix4d matrix4d = new Matrix4d();
                    ((com.maddox.il2.objects.ActorSimpleMesh)actorMesh).mesh().hookMatrix(k, matrix4d);
                    double d3 = -matrix4d.m23;
                    ((com.maddox.il2.objects.ActorSimpleMesh)actorMesh).pos.setAbs(new Point3d(0.0D, 0.0D, d3));
                }
            } else
            {
                try
                {
                    actorMesh = new ActorSimpleHMesh(meshName);
                }
                catch(java.lang.Exception exception1)
                {
                    java.lang.System.out.println(exception1.getMessage());
                    actorMesh = null;
                    return;
                }
                d1 = ((com.maddox.il2.engine.ActorHMesh)actorMesh).hierMesh().visibilityR();
                double d4 = 0.0D;
                int l = ((com.maddox.il2.objects.ActorSimpleHMesh)actorMesh).mesh().hookFind("Ground_Level");
                if(l != -1)
                {
                    com.maddox.JGP.Matrix4d matrix4d1 = new Matrix4d();
                    ((com.maddox.il2.objects.ActorSimpleHMesh)actorMesh).mesh().hookMatrix(l, matrix4d1);
                    double d5 = -matrix4d1.m23;
                    ((com.maddox.il2.objects.ActorSimpleHMesh)actorMesh).pos.setAbs(new Point3d(0.0D, 0.0D, d5));
                }
                if(!bGround)
                {
                    if(objectinfo.camouflage != null)
                        com.maddox.il2.ai.World.cur().setCamouflage(objectinfo.camouflage);
                    com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(meshName, ((com.maddox.il2.engine.ActorHMesh)actorMesh).hierMesh());
                    if(objectinfo.reg != null)
                    {
                        com.maddox.il2.objects.air.PaintScheme paintscheme = com.maddox.il2.objects.air.Aircraft.getPropertyPaintScheme(objectinfo.airClass, objectinfo.reg.country());
                        if(paintscheme != null)
                        {
                            int j1 = 0;
                            int k1 = 0;
                            int l1 = 0;
                            paintscheme.prepare(objectinfo.airClass, ((com.maddox.il2.engine.ActorHMesh)actorMesh).hierMesh(), objectinfo.reg, j1, k1, l1, true);
                        }
                    }
                }
                for(int i1 = 1; i1 <= 6; i1++)
                    if(((com.maddox.il2.objects.ActorSimpleHMesh)actorMesh).hierMesh().chunkFindCheck("Prop" + i1 + "_D0") != -1)
                    {
                        ((com.maddox.il2.objects.ActorSimpleHMesh)actorMesh).hierMesh().chunkVisible("Prop" + i1 + "_D0", false);
                        ((com.maddox.il2.objects.ActorSimpleHMesh)actorMesh).hierMesh().chunkVisible("PropRot" + i1 + "_D0", true);
                    }

            }
            getDistanceProperties();
            if(bGround)
            {
                d1 *= java.lang.Math.cos(0.78539816339744828D) / java.lang.Math.sin(((double)camera3D.FOV() * 3.1415926535897931D) / 180D / 2D);
                d1 -= com.maddox.il2.gui.GUIObjectView.Z_GAP;
                if(d1 < com.maddox.il2.gui.GUIObjectView.Z_DIST_NEAR)
                    com.maddox.il2.gui.GUIObjectView.Z_DIST_NEAR = d1;
                d1 = com.maddox.il2.gui.GUIObjectView.SCALE_FACTOR;
                _point.set(-d1, 0.0D, 0.0D);
                _o.set(ROT_X, ROT_Y - 45, 0.0F);
            } else
            {
                d1 *= java.lang.Math.cos(0.26179938779914941D) / java.lang.Math.sin(((double)camera3D.FOV() * 3.1415926535897931D) / 180D / 2D);
                d1 -= com.maddox.il2.gui.GUIObjectView.Z_GAP;
                if(d1 < com.maddox.il2.gui.GUIObjectView.Z_DIST_NEAR)
                    com.maddox.il2.gui.GUIObjectView.Z_DIST_NEAR = d1;
                d1 = com.maddox.il2.gui.GUIObjectView.SCALE_FACTOR;
                _point.set(-d1, 0.0D, 0.0D);
                _o.set(ROT_X, ROT_Y, 0.0F);
            }
            _o.transform(_point);
            camera3D.pos.setAbs(_point, _o);
            camera3D.pos.reset();
            if(bGround)
                animateMeshT = 0.0F;
        }

        public com.maddox.il2.engine.Camera3D camera3D;
        public java.lang.String meshName;
        public com.maddox.il2.engine.Actor actorMesh;
        public com.maddox.il2.engine.Actor worldMesh;
        public float animateMeshA;
        public float animateMeshT;
        public boolean isShadow;

        public _Render3D(com.maddox.il2.engine.Renders renders, float f)
        {
            super(renders, f);
            meshName = null;
            actorMesh = null;
            worldMesh = null;
            animateMeshA = 0.0F;
            animateMeshT = 0.0F;
            isShadow = false;
            setClearColor(new Color4f(0.39F, 0.35F, 0.23F, 1.0F));
            useClearStencil(true);
        }
    }

    static class ObjectInfo
    {

        public java.lang.String key;
        public java.lang.String name;
        public java.lang.String meshName;
        public boolean _bGround;
        public java.lang.Class airClass;
        public com.maddox.il2.ai.Regiment reg;
        public java.lang.String camouflage;

        public ObjectInfo(java.lang.String s, java.lang.String s1, java.lang.String s2, boolean flag, java.lang.Class class1, java.lang.String s3, java.lang.String s4)
        {
            key = s1;
            meshName = s2;
            _bGround = flag;
            camouflage = s4;
            if(!flag)
            {
                name = com.maddox.il2.game.I18N.plane(s1);
                airClass = class1;
                if(s3 != null)
                {
                    reg = (com.maddox.il2.ai.Regiment)com.maddox.il2.engine.Actor.getByName(s3);
                    meshName = com.maddox.il2.objects.air.Aircraft.getPropertyMesh(airClass, reg.country());
                }
            } else
            {
                try
                {
                    java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle(s, com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
                    name = resourcebundle.getString(s1 + "_NAME");
                }
                catch(java.lang.Exception exception)
                {
                    name = s1;
                }
            }
        }
    }


    public void _enter()
    {
        wCountry.setSelected(com.maddox.il2.gui.GUIObjectInspector.s_country, true, false);
        com.maddox.il2.game.Main3D.menuMusicPlay(com.maddox.il2.gui.GUIObjectInspector.s_country != 0 ? "de" : "ru");
        fillObjects();
        getDistanceProperties();
        client.activateWindow();
        wTable.resolutionChanged();
        if(wTable.countRows() > 0)
        {
            wTable.setSelect(com.maddox.il2.gui.GUIObjectInspector.s_object, 0);
            if(wTable.vSB.isVisible())
                wTable.vSB.setPos(com.maddox.il2.gui.GUIObjectInspector.s_scroll, true);
        }
    }

    public void _leave()
    {
        client.hideWindow();
    }

    public void getDistanceProperties()
    {
        java.lang.String s = "NoName";
        try
        {
            java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("i18n/Distance", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
            java.lang.String s1 = resourcebundle.getString(com.maddox.il2.gui.GUIObjectInspector.type);
            com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(s1);
            Z_DIST_NEAR = numbertokenizer.next(1000F);
            Z_DIST_FAR = numbertokenizer.next(1000F);
            Z_DIST_BORN = numbertokenizer.next(1000F);
            Z_GAP = numbertokenizer.next(1000F);
        }
        catch(java.lang.Exception exception)
        {
            Z_DIST_NEAR = 20D;
            Z_DIST_FAR = 100D;
            Z_DIST_BORN = 30D;
            Z_GAP = 6D;
            java.lang.System.out.println(com.maddox.il2.gui.GUIObjectInspector.type + ": error occured");
        }
        if(bGround);
    }

    public void fillCountries()
    {
        wCountry.clear();
        java.lang.String s = "NoName";
        for(int i = 0; i < cnt.length; i++)
        {
            java.lang.String s1;
            try
            {
                java.util.ResourceBundle resourcebundle = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
                s1 = resourcebundle.getString(cnt[i]);
            }
            catch(java.lang.Exception exception)
            {
                s1 = cnt[i];
            }
            wCountry.add(s1);
        }

    }

    public void fillObjects()
    {
        wTable.objects.clear();
        int i = wCountry.getSelected() + 1;
        if("air".equals(com.maddox.il2.gui.GUIObjectInspector.type))
        {
            java.lang.String s = "com/maddox/il2/objects/air.ini";
            com.maddox.rts.SectFile sectfile = new SectFile(s, 0);
            int j = sectfile.sectionIndex("AIR");
            int k = sectfile.vars(j);
            for(int i1 = 0; i1 < k; i1++)
            {
                java.lang.String s3 = sectfile.var(j, i1);
                com.maddox.util.NumberTokenizer numbertokenizer = new NumberTokenizer(sectfile.value(j, i1));
                java.lang.String s5 = numbertokenizer.next();
                int l1 = numbertokenizer.next(0);
                boolean flag = true;
                java.lang.String s7 = null;
                java.lang.String s8 = null;
                while(numbertokenizer.hasMoreTokens()) 
                {
                    java.lang.String s9 = numbertokenizer.next();
                    if("NOINFO".equals(s9))
                    {
                        flag = false;
                        break;
                    }
                    if(!"NOQUICK".equals(s9))
                        if("SUMMER".equals(s9))
                            s8 = s9;
                        else
                        if("WINTER".equals(s9))
                            s8 = s9;
                        else
                        if("DESERT".equals(s9))
                            s8 = s9;
                        else
                            s7 = s9;
                }
                if(flag && l1 == i)
                    try
                    {
                        java.lang.Class class1 = com.maddox.rts.ObjIO.classForName(s5);
                        java.lang.String s10 = com.maddox.il2.objects.air.Aircraft.getPropertyMeshDemo(class1, cnt[wCountry.getSelected()]);
                        com.maddox.il2.gui.ObjectInfo objectinfo1 = new ObjectInfo(null, s3, s10, false, class1, s7, s8);
                        wTable.objects.add(objectinfo1);
                    }
                    catch(java.lang.Exception exception) { }
            }

        } else
        {
            java.lang.String s1 = "i18n/" + com.maddox.il2.gui.GUIObjectInspector.type + ".ini";
            com.maddox.rts.SectFile sectfile1 = new SectFile(s1, 0);
            java.lang.String s2 = "i18n/" + com.maddox.il2.gui.GUIObjectInspector.type;
            int l = sectfile1.sectionIndex("ALL");
            int j1 = sectfile1.vars(l);
            for(int k1 = 0; k1 < j1; k1++)
            {
                java.lang.String s4 = sectfile1.var(l, k1);
                com.maddox.util.NumberTokenizer numbertokenizer1 = new NumberTokenizer(sectfile1.value(l, k1));
                java.lang.String s6 = numbertokenizer1.next();
                int i2 = numbertokenizer1.next(0);
                if(i2 == i)
                {
                    com.maddox.il2.gui.ObjectInfo objectinfo = new ObjectInfo(s2, s4, s6, true, null, null, null);
                    wTable.objects.add(objectinfo);
                }
            }

        }
        wTable.resized();
    }

    public GUIObjectView(com.maddox.gwindow.GWindowRoot gwindowroot)
    {
        super(23);
        propRot = 0.0F;
        _o = new Orient(0.0F, 0.0F, 0.0F);
        ROT_X = 0;
        ROT_Y = 0;
        bGround = false;
        _orient = new Orient();
        _point = new Point3d();
        client = (com.maddox.il2.gui.GUIClient)gwindowroot.create(new GUIClient());
        dialogClient = (com.maddox.il2.gui.DialogClient)client.create(new DialogClient());
        infoMenu = (com.maddox.il2.gui.GUIInfoMenu)client.create(new GUIInfoMenu());
        infoMenu.info = i18n("obj.infoV");
        infoName = (com.maddox.il2.gui.GUIInfoName)client.create(new GUIInfoName());
        wCountry = (com.maddox.gwindow.GWindowComboControl)dialogClient.addControl(new GWindowComboControl(dialogClient, 2.0F, 2.0F, 20F + gwindowroot.lookAndFeel().getHScrollBarW() / gwindowroot.lookAndFeel().metric()));
        wCountry.setEditable(false);
        fillCountries();
        wCountry.setSelected(com.maddox.il2.gui.GUIObjectInspector.s_country, true, false);
        wTable = new Table(dialogClient);
        dialogClient.create(wRenders = new WRenders());
        com.maddox.gwindow.GTexture gtexture = ((com.maddox.il2.gui.GUILookAndFeel)gwindowroot.lookAndFeel()).buttons2;
        wPrev = (com.maddox.il2.gui.GUIButton)dialogClient.addEscape(new GUIButton(dialogClient, gtexture, 0.0F, 96F, 48F, 48F));
        wText = (com.maddox.il2.gui.GUIButton)dialogClient.addControl(new GUIButton(dialogClient, gtexture, 0.0F, 48F, 48F, 48F));
        helpFont = com.maddox.gwindow.GFont.New("arial8");
        dialogClient.activateWindow();
        client.hideWindow();
    }

    public final float propDelta = 20F;
    public float propRot;
    public com.maddox.il2.engine.Orient _o;
    public int ROT_X;
    public int ROT_Y;
    public static double Z_GAP = 4D;
    public static double Z_DIST_BORN = 0.0D;
    public static double Z_DIST_NEAR = 0.0D;
    public static double Z_DIST_FAR = 100D;
    public static double SCALE_FACTOR = 0.0D;
    public boolean bGround;
    public com.maddox.il2.gui.GUIClient client;
    public com.maddox.il2.gui.DialogClient dialogClient;
    public com.maddox.il2.gui.GUIInfoMenu infoMenu;
    public com.maddox.il2.gui.GUIInfoName infoName;
    public com.maddox.il2.gui.GUIButton wPrev;
    public com.maddox.il2.gui.GUIButton wText;
    public com.maddox.gwindow.GWindowComboControl wCountry;
    public com.maddox.il2.gui.Table wTable;
    public com.maddox.il2.gui.WRenders wRenders;
    public com.maddox.gwindow.GFont helpFont;
    public static java.lang.String cnt[] = {
        "", ""
    };
    private com.maddox.il2.engine.Orient _orient;
    private com.maddox.JGP.Point3d _point;

    static 
    {
        cnt[0] = "allies";
        cnt[1] = "axis";
    }


}
