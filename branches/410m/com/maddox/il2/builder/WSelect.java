// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   WSelect.java

package com.maddox.il2.builder;

import com.maddox.JGP.Color4f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTabDialogClient;
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
import com.maddox.il2.objects.ActorSimpleHMesh;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.builder:
//            Plugin, Builder

public class WSelect extends com.maddox.gwindow.GWindowFramed
{
    class _Render3D extends com.maddox.il2.engine.Render
    {

        public void preRender()
        {
            if(!com.maddox.il2.engine.Actor.isValid(actorMesh) && actorMesh != null && meshName != null)
                setMesh(meshName, bGround);
            if(com.maddox.il2.engine.Actor.isValid(actorMesh))
            {
                if(animateMeshA != 0.0F || animateMeshT != 0.0F)
                {
                    actorMesh.pos.getAbs(_orient);
                    _orient.set(_orient.azimut() + animateMeshA * root.deltaTimeSec, _orient.tangage() + animateMeshT * root.deltaTimeSec, 0.0F);
                    _orient.wrap360();
                    actorMesh.pos.setAbs(_orient);
                    actorMesh.pos.reset();
                }
                actorMesh.draw.preRender(actorMesh);
            }
        }

        public void render()
        {
            if(com.maddox.il2.engine.Actor.isValid(actorMesh))
            {
                com.maddox.il2.engine.Render.prepareStates();
                actorMesh.draw.render(actorMesh);
            }
        }

        public _Render3D(com.maddox.il2.engine.Renders renders1, float f)
        {
            super(renders1, f);
            setClearColor(new Color4f(0.5F, 0.78F, 0.92F, 1.0F));
            useClearStencil(true);
        }
    }


    public void clearExtendTabs()
    {
        for(int i = tabsClient.sizeTabs(); i-- > 1;)
            tabsClient.removeTab(i);

    }

    public boolean isMeshVisible()
    {
        return renders.bVisible;
    }

    public com.maddox.il2.engine.HierMesh getHierMesh()
    {
        if(!com.maddox.il2.engine.Actor.isValid(actorMesh))
            return null;
        if(!(actorMesh instanceof com.maddox.il2.engine.ActorHMesh))
            return null;
        else
            return ((com.maddox.il2.engine.ActorHMesh)actorMesh).hierMesh();
    }

    public void windowShown()
    {
        builder.mSelectItem.bChecked = true;
        super.windowShown();
        doUpdateMesh();
    }

    public void windowHidden()
    {
        builder.mSelectItem.bChecked = false;
        super.windowHidden();
    }

    private void doUpdateMesh()
    {
        com.maddox.il2.builder.Path path = builder.selectedPath();
        if(path == null)
        {
            return;
        } else
        {
            com.maddox.il2.builder.Plugin plugin = (com.maddox.il2.builder.Plugin)com.maddox.rts.Property.value(path, "builderPlugin");
            plugin.updateSelectorMesh();
            return;
        }
    }

    public void setMesh(java.lang.String s, boolean flag)
    {
        if(meshName == s && com.maddox.il2.engine.Actor.isValid(actorMesh))
            return;
        if(com.maddox.il2.engine.Actor.isValid(actorMesh))
            actorMesh.destroy();
        actorMesh = null;
        meshName = s;
        bGround = flag;
        if(s == null)
        {
            wShow.bEnable = false;
            if(renders.bVisible)
            {
                renders.hideWindow();
                wShow.cap = new GCaption(com.maddox.il2.builder.Plugin.i18n("ButtonShow"));
            }
            return;
        }
        wShow.bEnable = true;
        if(!renders.bVisible)
            return;
        if(!renders.isVisible())
        {
            renders.hideWindow();
            wShow.cap = new GCaption(com.maddox.il2.builder.Plugin.i18n("ButtonShow"));
            return;
        }
        double d = 20D;
        if(s.toLowerCase().endsWith(".sim"))
        {
            actorMesh = new ActorSimpleMesh(s);
            d = ((com.maddox.il2.engine.ActorMesh)actorMesh).mesh().visibilityR();
        } else
        {
            actorMesh = new ActorSimpleHMesh(s);
            d = ((com.maddox.il2.engine.ActorHMesh)actorMesh).hierMesh().visibilityR();
            if(!flag)
                com.maddox.il2.objects.air.Aircraft.prepareMeshCamouflage(s, ((com.maddox.il2.engine.ActorHMesh)actorMesh).hierMesh());
        }
        if(flag)
        {
            actorMesh.pos.setAbs(new Orient(30F, 0.0F, 0.0F));
            d *= java.lang.Math.cos(0.78539816339744828D) / java.lang.Math.sin(((double)camera3D.FOV() * 3.1415926535897931D) / 180D / 2D);
            camera3D.pos.setAbs(new Point3d(d, 0.0D, d * 0.90000000000000002D), new Orient(180F, -45F, 0.0F));
        } else
        {
            actorMesh.pos.setAbs(new Orient(90F, 0.0F, 0.0F));
            d *= java.lang.Math.cos(0.26179938779914941D) / java.lang.Math.sin(((double)camera3D.FOV() * 3.1415926535897931D) / 180D / 2D);
            camera3D.pos.setAbs(new Point3d(d, 0.0D, 0.0D), new Orient(180F, 0.0F, 0.0F));
        }
        camera3D.pos.reset();
        if(flag)
            animateMeshT = 0.0F;
        doUpdateMesh();
    }

    public void created()
    {
        bAlwaysOnTop = true;
        super.created();
        title = com.maddox.il2.builder.Plugin.i18n("Object");
        clientWindow = create(new GWindowTabDialogClient());
        tabsClient = (com.maddox.gwindow.GWindowTabDialogClient)clientWindow;
        com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)tabsClient.create(new GWindowDialogClient());
        tabsClient.addTab(com.maddox.il2.builder.Plugin.i18n("Type"), gwindowdialogclient);
        gwindowdialogclient.addControl(comboBox1 = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 1.0F, 1.0F, 18F) {

            public boolean notify(int i, int j)
            {
                if(i == 2)
                    builder.setSelected(null);
                return super.notify(i, j);
            }

        }
);
        comboBox1.setEditable(false);
        gwindowdialogclient.addControl(comboBox2 = new com.maddox.gwindow.GWindowComboControl(gwindowdialogclient, 1.0F, 2.5F, 18F) {

            public boolean notify(int i, int j)
            {
                if(i == 2)
                    if(com.maddox.il2.engine.Actor.isValid(builder.selectedPath()))
                    {
                        com.maddox.il2.builder.Plugin plugin = (com.maddox.il2.builder.Plugin)com.maddox.rts.Property.value(builder.selectedPath(), "builderPlugin");
                        plugin.changeType();
                    } else
                    if(com.maddox.il2.engine.Actor.isValid(builder.selectedActor()))
                    {
                        com.maddox.il2.builder.Plugin plugin1 = (com.maddox.il2.builder.Plugin)com.maddox.rts.Property.value(builder.selectedActor(), "builderPlugin");
                        if(plugin1 != null)
                            plugin1.changeType();
                        else
                        if(builder.bMultiSelect)
                        {
                            com.maddox.il2.builder.Plugin plugin2 = com.maddox.il2.builder.Plugin.getPlugin("MapActors");
                            plugin2.changeType();
                        }
                    }
                return super.notify(i, j);
            }

        }
);
        comboBox2.setEditable(false);
        comboBox2.listCountLines = comboBox2.listVisibleLines = 16;
        gwindowdialogclient.addControl(wShow = new com.maddox.gwindow.GWindowButton(gwindowdialogclient, 1.0F, 4F, 18F, 1.4F, com.maddox.il2.builder.Plugin.i18n("ButtonShow"), null) {

            public boolean notify(int i, int j)
            {
                if(i == 2)
                    if(renders.bVisible)
                    {
                        renders.hideWindow();
                        cap = new GCaption(com.maddox.il2.builder.Plugin.i18n("ButtonShow"));
                    } else
                    {
                        com.maddox.il2.engine.Actor.destroy(actorMesh);
                        renders.showWindow();
                        cap = new GCaption(com.maddox.il2.builder.Plugin.i18n("ButtonHide"));
                        if(com.maddox.il2.engine.Actor.isValid(actorMesh))
                            actorMesh.destroy();
                        setMesh(meshName, bGround);
                        _resized();
                    }
                return super.notify(i, j);
            }

        }
);
        wShow.bEnable = false;
        renders = new com.maddox.il2.engine.GUIRenders(gwindowdialogclient, 1.0F, 6F, 18F, 12F, true) {

            public void mouseButton(int i, boolean flag, float f, float f1)
            {
                super.mouseButton(i, flag, f, f1);
                if(!flag)
                    return;
                if(i == 1)
                {
                    animateMeshA = animateMeshT = 0.0F;
                    if(com.maddox.il2.engine.Actor.isValid(actorMesh))
                        if(bGround)
                            actorMesh.pos.setAbs(new Orient(30F, 0.0F, 0.0F));
                        else
                            actorMesh.pos.setAbs(new Orient(90F, 0.0F, 0.0F));
                } else
                if(i == 0)
                {
                    f -= win.dx / 2.0F;
                    if(java.lang.Math.abs(f) < win.dx / 16F)
                        animateMeshA = 0.0F;
                    else
                        animateMeshA = (-128F * f) / win.dx;
                    if(!bGround)
                    {
                        f1 -= win.dy / 2.0F;
                        if(java.lang.Math.abs(f1) < win.dy / 16F)
                            animateMeshT = 0.0F;
                        else
                            animateMeshT = (-128F * f1) / win.dy;
                    }
                }
            }

            public void windowShown()
            {
                super.windowShown();
                doUpdateMesh();
            }

        }
;
        camera3D = new Camera3D();
        camera3D.set(50F, 1.0F, 800F);
        render3D = new _Render3D(renders.renders, 1.0F);
        render3D.setCamera(camera3D);
        renders.hideWindow();
        com.maddox.il2.engine.LightEnvXY lightenvxy = new LightEnvXY();
        render3D.setLightEnv(lightenvxy);
        lightenvxy.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
        com.maddox.JGP.Vector3f vector3f = new Vector3f(-2F, 1.0F, -1F);
        vector3f.normalize();
        lightenvxy.sun().set(vector3f);
        resized();
    }

    public void _resized()
    {
        if(comboBox1 == null)
            return;
        comboBox1.setSize(comboBox1.parentWindow.win.dx - lookAndFeel().metric(2.0F), comboBox1.win.dy);
        comboBox2.setSize(comboBox2.parentWindow.win.dx - lookAndFeel().metric(2.0F), comboBox2.win.dy);
        wShow.setSize(wShow.parentWindow.win.dx - lookAndFeel().metric(2.0F), wShow.win.dy);
        float f = renders.parentWindow.win.dy - lookAndFeel().metric(7F);
        if(f <= 10F)
            f = 10F;
        renders.setSize(renders.parentWindow.win.dx - lookAndFeel().metric(2.0F), f);
    }

    public void resized()
    {
        super.resized();
        _resized();
    }

    public void afterCreated()
    {
        super.afterCreated();
        close(false);
    }

    public WSelect(com.maddox.il2.builder.Builder builder1, com.maddox.gwindow.GWindow gwindow)
    {
        curFilledType = -1;
        animateMeshA = 0.0F;
        animateMeshT = 0.0F;
        _orient = new Orient();
        builder = builder1;
        doNew(gwindow, 2.0F, 2.0F, 20F, 25F, true);
    }

    public com.maddox.gwindow.GWindowTabDialogClient tabsClient;
    public com.maddox.gwindow.GWindowComboControl comboBox1;
    public com.maddox.gwindow.GWindowComboControl comboBox2;
    public int curFilledType;
    public com.maddox.gwindow.GWindowButton wShow;
    com.maddox.il2.engine.GUIRenders renders;
    com.maddox.il2.engine.Camera3D camera3D;
    com.maddox.il2.builder._Render3D render3D;
    java.lang.String meshName;
    com.maddox.il2.engine.Actor actorMesh;
    boolean bGround;
    float animateMeshA;
    float animateMeshT;
    com.maddox.il2.builder.Builder builder;
    private com.maddox.il2.engine.Orient _orient;


}
