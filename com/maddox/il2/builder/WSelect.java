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

public class WSelect extends GWindowFramed
{
  public GWindowTabDialogClient tabsClient;
  public GWindowComboControl comboBox1;
  public GWindowComboControl comboBox2;
  public int curFilledType = -1;
  public GWindowButton wShow;
  GUIRenders renders;
  Camera3D camera3D;
  _Render3D render3D;
  String meshName;
  Actor actorMesh;
  boolean bGround;
  float animateMeshA = 0.0F;
  float animateMeshT = 0.0F;
  Builder builder;
  private Orient _orient = new Orient();

  public void clearExtendTabs()
  {
    int i = this.tabsClient.sizeTabs();
    while (i-- > 1)
      this.tabsClient.removeTab(i); 
  }

  public boolean isMeshVisible() {
    return this.renders.bVisible;
  }
  public HierMesh getHierMesh() {
    if (!Actor.isValid(this.actorMesh)) return null;
    if (!(this.actorMesh instanceof ActorHMesh)) return null;
    return ((ActorHMesh)this.actorMesh).hierMesh();
  }

  public void windowShown() {
    this.builder.mSelectItem.bChecked = true;
    super.windowShown();
    doUpdateMesh();
  }
  public void windowHidden() {
    this.builder.mSelectItem.bChecked = false;
    super.windowHidden();
  }

  private void doUpdateMesh() {
    Path localPath = this.builder.selectedPath();
    if (localPath == null) return;
    Plugin localPlugin = (Plugin)Property.value(localPath, "builderPlugin");
    localPlugin.updateSelectorMesh();
  }

  public void setMesh(String paramString, boolean paramBoolean)
  {
    if ((this.meshName == paramString) && (Actor.isValid(this.actorMesh))) return;
    if (Actor.isValid(this.actorMesh))
      this.actorMesh.destroy();
    this.actorMesh = null;
    this.meshName = paramString;
    this.bGround = paramBoolean;
    if (paramString == null) {
      this.wShow.bEnable = false;
      if (this.renders.bVisible) {
        this.renders.hideWindow();
        this.wShow.cap = new GCaption(Plugin.i18n("ButtonShow"));
      }
      return;
    }
    this.wShow.bEnable = true;
    if (!this.renders.bVisible) return;
    if (!this.renders.isVisible()) {
      this.renders.hideWindow();
      this.wShow.cap = new GCaption(Plugin.i18n("ButtonShow"));
      return;
    }
    double d = 20.0D;
    if (paramString.toLowerCase().endsWith(".sim")) {
      this.actorMesh = new ActorSimpleMesh(paramString);
      d = ((ActorMesh)this.actorMesh).mesh().visibilityR();
    } else {
      this.actorMesh = new ActorSimpleHMesh(paramString);
      d = ((ActorHMesh)this.actorMesh).hierMesh().visibilityR();
      if (!paramBoolean)
        Aircraft.prepareMeshCamouflage(paramString, ((ActorHMesh)this.actorMesh).hierMesh());
    }
    if (paramBoolean) {
      this.actorMesh.pos.setAbs(new Orient(30.0F, 0.0F, 0.0F));
      d *= Math.cos(0.7853981633974483D) / Math.sin(this.camera3D.FOV() * 3.141592653589793D / 180.0D / 2.0D);
      this.camera3D.pos.setAbs(new Point3d(d, 0.0D, d * 0.9D), new Orient(180.0F, -45.0F, 0.0F));
    }
    else
    {
      this.actorMesh.pos.setAbs(new Orient(90.0F, 0.0F, 0.0F));
      d *= Math.cos(0.2617993877991494D) / Math.sin(this.camera3D.FOV() * 3.141592653589793D / 180.0D / 2.0D);
      this.camera3D.pos.setAbs(new Point3d(d, 0.0D, 0.0D), new Orient(180.0F, 0.0F, 0.0F));
    }

    this.camera3D.pos.reset();
    if (paramBoolean)
      this.animateMeshT = 0.0F;
    doUpdateMesh();
  }

  public void created() {
    this.bAlwaysOnTop = true;
    super.created();
    this.title = Plugin.i18n("Object");
    this.clientWindow = create(new GWindowTabDialogClient());
    this.tabsClient = ((GWindowTabDialogClient)this.clientWindow);
    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.tabsClient.create(new GWindowDialogClient());
    this.tabsClient.addTab(Plugin.i18n("Type"), localGWindowDialogClient);
    localGWindowDialogClient.addControl(this.comboBox1 = new GWindowComboControl(localGWindowDialogClient, 1.0F, 1.0F, 18.0F) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2) {
          WSelect.this.builder.setSelected(null);
        }
        return super.notify(paramInt1, paramInt2);
      }
    });
    this.comboBox1.setEditable(false);
    localGWindowDialogClient.addControl(this.comboBox2 = new GWindowComboControl(localGWindowDialogClient, 1.0F, 2.5F, 18.0F) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2)
        {
          Plugin localPlugin;
          if (Actor.isValid(WSelect.this.builder.selectedPath())) {
            localPlugin = (Plugin)Property.value(WSelect.this.builder.selectedPath(), "builderPlugin");
            localPlugin.changeType();
          } else if (Actor.isValid(WSelect.this.builder.selectedActor())) {
            localPlugin = (Plugin)Property.value(WSelect.this.builder.selectedActor(), "builderPlugin");
            if (localPlugin != null) {
              localPlugin.changeType();
            } else if (WSelect.this.builder.bMultiSelect) {
              localPlugin = Plugin.getPlugin("MapActors");
              localPlugin.changeType();
            }
          }
        }
        return super.notify(paramInt1, paramInt2);
      }
    });
    this.comboBox2.setEditable(false);
    this.comboBox2.listCountLines = (this.comboBox2.listVisibleLines = 16);
    localGWindowDialogClient.addControl(this.wShow = new GWindowButton(localGWindowDialogClient, 1.0F, 4.0F, 18.0F, 1.4F, Plugin.i18n("ButtonShow"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2) {
          if (WSelect.this.renders.bVisible) {
            WSelect.this.renders.hideWindow();
            this.cap = new GCaption(Plugin.i18n("ButtonShow"));
          } else {
            Actor.destroy(WSelect.this.actorMesh);
            WSelect.this.renders.showWindow();
            this.cap = new GCaption(Plugin.i18n("ButtonHide"));
            if (Actor.isValid(WSelect.this.actorMesh))
              WSelect.this.actorMesh.destroy();
            WSelect.this.setMesh(WSelect.this.meshName, WSelect.this.bGround);
            WSelect.this._resized();
          }
        }
        return super.notify(paramInt1, paramInt2);
      }
    });
    this.wShow.bEnable = false;

    this.renders = new GUIRenders(localGWindowDialogClient, 1.0F, 6.0F, 18.0F, 12.0F, true) {
      public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) {
        super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
        if (!paramBoolean) return;
        if (paramInt == 1) {
          WSelect.this.animateMeshA = (WSelect.this.animateMeshT = 0.0F);
          if (Actor.isValid(WSelect.this.actorMesh))
            if (WSelect.this.bGround) WSelect.this.actorMesh.pos.setAbs(new Orient(30.0F, 0.0F, 0.0F)); else
              WSelect.this.actorMesh.pos.setAbs(new Orient(90.0F, 0.0F, 0.0F));
        }
        else if (paramInt == 0) {
          paramFloat1 -= this.win.dx / 2.0F;
          if (Math.abs(paramFloat1) < this.win.dx / 16.0F) WSelect.this.animateMeshA = 0.0F; else
            WSelect.this.animateMeshA = (-128.0F * paramFloat1 / this.win.dx);
          if (!WSelect.this.bGround) {
            paramFloat2 -= this.win.dy / 2.0F;
            if (Math.abs(paramFloat2) < this.win.dy / 16.0F) WSelect.this.animateMeshT = 0.0F; else
              WSelect.this.animateMeshT = (-128.0F * paramFloat2 / this.win.dy); 
          }
        }
      }

      public void windowShown() {
        super.windowShown();
        WSelect.this.doUpdateMesh();
      }
    };
    this.camera3D = new Camera3D();
    this.camera3D.set(50.0F, 1.0F, 800.0F);
    this.render3D = new _Render3D(this.renders.renders, 1.0F);
    this.render3D.setCamera(this.camera3D);
    this.renders.hideWindow();
    LightEnvXY localLightEnvXY = new LightEnvXY();
    this.render3D.setLightEnv(localLightEnvXY);

    localLightEnvXY.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
    Vector3f localVector3f = new Vector3f(-2.0F, 1.0F, -1.0F); localVector3f.normalize();
    localLightEnvXY.sun().set(localVector3f);

    resized();
  }

  public void _resized() {
    if (this.comboBox1 == null) return;
    this.comboBox1.setSize(this.comboBox1.parentWindow.win.dx - lookAndFeel().metric(2.0F), this.comboBox1.win.dy);
    this.comboBox2.setSize(this.comboBox2.parentWindow.win.dx - lookAndFeel().metric(2.0F), this.comboBox2.win.dy);
    this.wShow.setSize(this.wShow.parentWindow.win.dx - lookAndFeel().metric(2.0F), this.wShow.win.dy);
    float f = this.renders.parentWindow.win.dy - lookAndFeel().metric(7.0F);
    if (f <= 10.0F) f = 10.0F;
    this.renders.setSize(this.renders.parentWindow.win.dx - lookAndFeel().metric(2.0F), f);
  }

  public void resized() {
    super.resized();
    _resized();
  }

  public void afterCreated() {
    super.afterCreated();
    close(false);
  }

  public WSelect(Builder paramBuilder, GWindow paramGWindow) {
    this.builder = paramBuilder;
    doNew(paramGWindow, 2.0F, 2.0F, 20.0F, 25.0F, true);
  }

  class _Render3D extends Render
  {
    public void preRender()
    {
      if ((!Actor.isValid(WSelect.this.actorMesh)) && (WSelect.this.actorMesh != null) && (WSelect.this.meshName != null)) {
        WSelect.this.setMesh(WSelect.this.meshName, WSelect.this.bGround);
      }
      if (Actor.isValid(WSelect.this.actorMesh)) {
        if ((WSelect.this.animateMeshA != 0.0F) || (WSelect.this.animateMeshT != 0.0F)) {
          WSelect.this.actorMesh.pos.getAbs(WSelect.this._orient);
          WSelect.this._orient.set(WSelect.this._orient.azimut() + WSelect.this.animateMeshA * WSelect.this.root.deltaTimeSec, WSelect.this._orient.tangage() + WSelect.this.animateMeshT * WSelect.this.root.deltaTimeSec, 0.0F);

          WSelect.this._orient.wrap360();
          WSelect.this.actorMesh.pos.setAbs(WSelect.this._orient);
          WSelect.this.actorMesh.pos.reset();
        }
        WSelect.this.actorMesh.draw.preRender(WSelect.this.actorMesh);
      }
    }

    public void render() {
      if (Actor.isValid(WSelect.this.actorMesh)) {
        Render.prepareStates();
        WSelect.this.actorMesh.draw.render(WSelect.this.actorMesh);
      }
    }

    public _Render3D(Renders paramFloat, float arg3) {
      super(localObject);
      setClearColor(new Color4f(0.5F, 0.78F, 0.92F, 1.0F));
      useClearStencil(true);
    }
  }
}