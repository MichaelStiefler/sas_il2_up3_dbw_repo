package com.maddox.il2.gui;

import com.maddox.JGP.Color4f;
import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexture;
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

public class GUIObjectView extends GameState
{
  public final float propDelta = 20.0F;
  public float propRot = 0.0F;

  public Orient _o = new Orient(0.0F, 0.0F, 0.0F);
  public int ROT_X = 0;
  public int ROT_Y = 0;
  public static double Z_GAP = 4.0D;
  public static double Z_DIST_BORN = 0.0D;
  public static double Z_DIST_NEAR = 0.0D;
  public static double Z_DIST_FAR = 100.0D;
  public static double SCALE_FACTOR = 0.0D;

  public boolean bGround = false;
  public GUIClient client;
  public DialogClient dialogClient;
  public GUIInfoMenu infoMenu;
  public GUIInfoName infoName;
  public GUIButton wPrev;
  public GUIButton wText;
  public GWindowComboControl wCountry;
  public Table wTable;
  public WRenders wRenders;
  public GFont helpFont;
  public static String[] cnt = { "", "" };

  private Orient _orient = new Orient();
  private Point3d _point = new Point3d();

  public void _enter()
  {
    this.wCountry.setSelected(GUIObjectInspector.s_country, true, false);

    Main3D.menuMusicPlay(GUIObjectInspector.s_country == 0 ? "ru" : "de");
    fillObjects();
    getDistanceProperties();
    this.client.activateWindow();
    this.wTable.resolutionChanged();

    if (this.wTable.countRows() > 0) {
      this.wTable.setSelect(GUIObjectInspector.s_object, 0);
      if (this.wTable.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.isVisible())
        this.wTable.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.setPos(GUIObjectInspector.s_scroll, true); 
    }
  }

  public void _leave() {
    this.client.hideWindow();
  }

  public void getDistanceProperties()
  {
    String str = "NoName";
    try
    {
      ResourceBundle localResourceBundle = ResourceBundle.getBundle("i18n/Distance", RTSConf.cur.locale, LDRres.loader());
      str = localResourceBundle.getString(GUIObjectInspector.type);
      NumberTokenizer localNumberTokenizer = new NumberTokenizer(str);
      Z_DIST_NEAR = localNumberTokenizer.next(1000.0F);
      Z_DIST_FAR = localNumberTokenizer.next(1000.0F);
      Z_DIST_BORN = localNumberTokenizer.next(1000.0F);
      Z_GAP = localNumberTokenizer.next(1000.0F);
    }
    catch (Exception localException) {
      Z_DIST_NEAR = 20.0D;
      Z_DIST_FAR = 100.0D;
      Z_DIST_BORN = 30.0D;
      Z_GAP = 6.0D;

      System.out.println(GUIObjectInspector.type + ": error occured");
    }

    if (!this.bGround);
  }

  public void fillCountries()
  {
    this.wCountry.clear();
    String str = "NoName";
    for (int i = 0; i < cnt.length; i++) {
      try {
        ResourceBundle localResourceBundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());
        str = localResourceBundle.getString(cnt[i]);
      } catch (Exception localException) {
        str = cnt[i];
      }
      this.wCountry.add(str);
    }
  }

  public void fillObjects() {
    this.wTable.objects.clear();
    int i = this.wCountry.getSelected() + 1;
    String str1;
    SectFile localSectFile;
    int k;
    int m;
    Object localObject1;
    Object localObject2;
    int i2;
    Object localObject3;
    if ("air".equals(GUIObjectInspector.type)) {
      str1 = "com/maddox/il2/objects/air.ini";
      localSectFile = new SectFile(str1, 0);
      int j = localSectFile.sectionIndex("AIR");
      k = localSectFile.vars(j);
      for (m = 0; m < k; m++) {
        String str3 = localSectFile.var(j, m);
        localObject1 = new NumberTokenizer(localSectFile.value(j, m));
        localObject2 = ((NumberTokenizer)localObject1).next();
        int i1 = ((NumberTokenizer)localObject1).next(0);
        i2 = 1;
        localObject3 = null;
        Object localObject4 = null;
        Object localObject5;
        while (((NumberTokenizer)localObject1).hasMoreTokens()) {
          localObject5 = ((NumberTokenizer)localObject1).next();
          if ("NOINFO".equals(localObject5)) {
            i2 = 0;
            break;
          }if (!"NOQUICK".equals(localObject5)) {
            if ("SUMMER".equals(localObject5))
              localObject4 = localObject5;
            else if ("WINTER".equals(localObject5))
              localObject4 = localObject5;
            else if ("DESERT".equals(localObject5))
              localObject4 = localObject5;
            else
              localObject3 = localObject5;
          }
        }
        if ((i2 == 0) || (i1 != i)) continue;
        try {
          localObject5 = ObjIO.classForName((String)localObject2);
          String str5 = Aircraft.getPropertyMeshDemo((Class)localObject5, cnt[this.wCountry.getSelected()]);
          ObjectInfo localObjectInfo = new ObjectInfo(null, str3, str5, false, (Class)localObject5, (String)localObject3, localObject4);
          this.wTable.objects.add(localObjectInfo);
        }
        catch (Exception localException)
        {
        }
      }

    }
    else
    {
      str1 = "i18n/" + GUIObjectInspector.type + ".ini";
      localSectFile = new SectFile(str1, 0);
      String str2 = "i18n/" + GUIObjectInspector.type;
      k = localSectFile.sectionIndex("ALL");
      m = localSectFile.vars(k);
      for (int n = 0; n < m; n++) {
        localObject1 = localSectFile.var(k, n);
        localObject2 = new NumberTokenizer(localSectFile.value(k, n));
        String str4 = ((NumberTokenizer)localObject2).next();
        i2 = ((NumberTokenizer)localObject2).next(0);
        if (i2 == i) {
          localObject3 = new ObjectInfo(str2, (String)localObject1, str4, true, null, null, null);
          this.wTable.objects.add(localObject3);
        }
      }
    }
    this.wTable.resized();
  }

  public GUIObjectView(GWindowRoot paramGWindowRoot)
  {
    super(23);
    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient()));
    this.dialogClient = ((DialogClient)this.client.create(new DialogClient()));
    this.infoMenu = ((GUIInfoMenu)this.client.create(new GUIInfoMenu()));
    this.infoMenu.info = i18n("obj.infoV");
    this.infoName = ((GUIInfoName)this.client.create(new GUIInfoName()));

    this.wCountry = ((GWindowComboControl)this.dialogClient.addControl(new GWindowComboControl(this.dialogClient, 2.0F, 2.0F, 20.0F + paramGWindowRoot.lookAndFeel().getHScrollBarW() / paramGWindowRoot.lookAndFeel().metric())));

    this.wCountry.setEditable(false);
    fillCountries();
    this.wCountry.setSelected(GUIObjectInspector.s_country, true, false);

    this.wTable = new Table(this.dialogClient);

    this.dialogClient.create(this.wRenders = new WRenders());

    GTexture localGTexture = ((GUILookAndFeel)paramGWindowRoot.lookAndFeel()).buttons2;
    this.wPrev = ((GUIButton)this.dialogClient.addEscape(new GUIButton(this.dialogClient, localGTexture, 0.0F, 96.0F, 48.0F, 48.0F)));
    this.wText = ((GUIButton)this.dialogClient.addControl(new GUIButton(this.dialogClient, localGTexture, 0.0F, 48.0F, 48.0F, 48.0F)));
    this.helpFont = GFont.New("arial8");

    this.dialogClient.activateWindow();
    this.client.hideWindow();
  }

  static
  {
    cnt[0] = "allies";
    cnt[1] = "axis";
  }

  public class DialogClient extends GUIDialogClient
  {
    public DialogClient()
    {
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
    {
      if (paramInt1 != 2) return super.notify(paramGWindow, paramInt1, paramInt2);

      if (isMouseCaptured()) return true;

      if (paramGWindow == GUIObjectView.this.wCountry) {
        GUIObjectView.this.fillObjects();
        int i = GUIObjectView.this.wCountry.getSelected();
        if (i >= 0)
        {
          Main3D.menuMusicPlay(i == 0 ? "ru" : "de");
          GUIObjectInspector.s_country = GUIObjectView.this.wCountry.getSelected();
          if (GUIObjectView.this.wTable.countRows() > 0) {
            GUIObjectInspector.s_object = 0;
            GUIObjectInspector.s_scroll = 0.0F;
            GUIObjectView.this.wTable.setSelect(GUIObjectInspector.s_object, 0);
            if (GUIObjectView.this.wTable.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.isVisible())
              GUIObjectView.this.wTable.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.setPos(GUIObjectInspector.s_scroll, true);
          }
        }
        return true;
      }

      if (paramGWindow == GUIObjectView.this.wText) {
        GUIObjectInspector.s_object = GUIObjectView.this.wTable.jdField_selectRow_of_type_Int;
        GUIObjectInspector.s_scroll = GUIObjectView.this.wTable.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.pos();
        Main.stateStack().change(22);
        return true;
      }

      if (paramGWindow == GUIObjectView.this.wPrev) {
        GUIObjectInspector.s_object = GUIObjectView.this.wTable.jdField_selectRow_of_type_Int;
        GUIObjectInspector.s_scroll = GUIObjectView.this.wTable.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.pos();
        Main.stateStack().change(22);
        Main.stateStack().pop();
        return true;
      }

      return super.notify(paramGWindow, paramInt1, paramInt2);
    }

    public void render() {
      super.render();
      GUISeparate.draw(this, GColor.Gray, x1024(40.0F), y1024(620.0F), x1024(250.0F), 2.0F);
      GUISeparate.draw(this, GColor.Gray, x1024(320.0F), y1024(32.0F), 2.0F, y1024(650.0F));
      setCanvasColor(GColor.Gray);
      setCanvasFont(0);
      draw(x1024(40.0F), y1024(40.0F), x1024(240.0F), y1024(32.0F), 0, GUIObjectView.this.i18n("obj.SelectCountry"));

      draw(x1024(104.0F), y1024(652.0F), x1024(192.0F), y1024(48.0F), 0, GUIObjectView.this.i18n("obj.Back"));
      draw(x1024(730.0F), y1024(652.0F), x1024(192.0F), y1024(48.0F), 2, GUIObjectView.this.i18n("obj.Text"));

      this.root.C.font = GUIObjectView.this.helpFont;
      draw(x1024(360.0F), y1024(606.0F), x1024(560.0F), y1024(16.0F), 0, GUIObjectView.this.i18n("obj.Help0"));
      draw(x1024(360.0F), y1024(622.0F), x1024(470.0F), y1024(16.0F), 0, GUIObjectView.this.i18n("obj.Help1"));

      setCanvasColorWHITE();
      GBevel localGBevel = ((GUILookAndFeel)lookAndFeel()).bevelComboDown;
      lookAndFeel().drawBevel(this, x1024(360.0F) - localGBevel.L.dx, y1024(50.0F) - localGBevel.T.dy, x1024(625.0F) + localGBevel.R.dx * 2.0F, y1024(540.0F) + localGBevel.B.dy * 2.0F, localGBevel, ((GUILookAndFeel)lookAndFeel()).basicelements, false);
    }

    public void setPosSize()
    {
      set1024PosSize(0.0F, 32.0F, 1024.0F, 736.0F);
      GUIObjectView.this.wPrev.setPosC(x1024(64.0F), y1024(676.0F));
      GUIObjectView.this.wText.setPosC(x1024(960.0F), y1024(676.0F));
      GUIObjectView.this.wCountry.setPosSize(x1024(40.0F), y1024(82.0F), x1024(246.0F), M(2.0F));
      GUIObjectView.this.wTable.setPosSize(x1024(40.0F), y1024(194.0F), x1024(246.0F), y1024(400.0F));

      GUIObjectView.this.wRenders.setPosSize(x1024(360.0F), y1024(50.0F), x1024(625.0F), y1024(540.0F));
    }
  }

  public class Table extends GWindowTable
  {
    public ArrayList objects = new ArrayList();

    public int countRows() { return this.objects != null ? this.objects.size() : 0; }

    public void renderCell(int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2) {
      setCanvasFont(0);
      String str = ((GUIObjectView.ObjectInfo)this.objects.get(paramInt1)).name;
      if (paramBoolean) {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, lookAndFeel().regionWhite);
        setCanvasColorWHITE();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, str);
      }
      else
      {
        setCanvasColorBLACK();
        draw(0.0F, 0.0F, paramFloat1, paramFloat2, 0, str);
      }
    }

    public void setSelect(int paramInt1, int paramInt2) {
      super.setSelect(paramInt1, paramInt2);
    }

    public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
      if (super.notify(paramGWindow, paramInt1, paramInt2))
        return true;
      notify(paramInt1, paramInt2);
      return false;
    }
    public void afterCreated() {
      super.afterCreated();
      this.bColumnsSizable = false;
      addColumn(I18N.gui("obj.ObjectTypesList"), null);
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      this.bNotify = true;
      this.wClient.bNotify = true;
      resized();
    }
    public void resolutionChanged() {
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Table(GWindow arg2) {
      super(2.0F, 4.0F, 20.0F, 16.0F);
    }
  }

  public class WRenders extends GUIRenders
  {
    public GUIObjectView._Render3D render3D;
    public boolean MODE_SCALE = false;
    public boolean MODE_ROTATE = false;

    public WRenders() {
    }
    public void mouseMove(float paramFloat1, float paramFloat2) {
      float f1 = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dx;
      float f2 = this.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.mouseStep.dy;
      if (Mouse.adapter().isInvert())
      {
        f2 = -f2;
      }
      if ((isMouseCaptured()) && (this.MODE_ROTATE))
      {
        GUIObjectView tmp55_52 = GUIObjectView.this; tmp55_52.ROT_X = (int)(tmp55_52.ROT_X + f1 / 2.0F);
        GUIObjectView tmp72_69 = GUIObjectView.this; tmp72_69.ROT_Y = (int)(tmp72_69.ROT_Y - f2 / 2.0F);
        if (GUIObjectView.this.bGround) {
          if (GUIObjectView.this.ROT_Y > 20) GUIObjectView.this.ROT_Y = 20;
          if (GUIObjectView.this.ROT_Y < -50) GUIObjectView.this.ROT_Y = -50;
        }
      }

      if ((isMouseCaptured()) && (this.MODE_SCALE))
      {
        GUIObjectView.SCALE_FACTOR -= f2 / 2.0F;
        if (GUIObjectView.SCALE_FACTOR > GUIObjectView.Z_DIST_FAR) GUIObjectView.SCALE_FACTOR = GUIObjectView.Z_DIST_FAR;
        if (GUIObjectView.SCALE_FACTOR < GUIObjectView.Z_DIST_NEAR) GUIObjectView.SCALE_FACTOR = GUIObjectView.Z_DIST_NEAR;
      }
    }

    public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
    {
      if ((paramInt == 1) && (paramBoolean)) {
        this.jdField_mouseCursor_of_type_Int = 0;
        mouseCapture(true);
        this.MODE_SCALE = true;
        this.MODE_ROTATE = false;
      }
      if ((paramInt == 0) && (paramBoolean)) {
        this.jdField_mouseCursor_of_type_Int = 0;
        mouseCapture(true);
        this.MODE_ROTATE = true;
        this.MODE_SCALE = false;
      }

      if (!isMouseCaptured()) {
        super.mouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
        return;
      }

      if (!paramBoolean)
      {
        this.jdField_mouseCursor_of_type_Int = 1;
        mouseCapture(false);
      }
    }

    public void created()
    {
      this.render3D = new GUIObjectView._Render3D(GUIObjectView.this, this.renders, 1.0F);
      this.render3D.camera3D = new Camera3D();
      this.render3D.camera3D.set(50.0F, 1.0F, 20000.0F);
      this.render3D.setCamera(this.render3D.camera3D);
      LightEnvXY localLightEnvXY = new LightEnvXY();
      this.render3D.setLightEnv(localLightEnvXY);

      localLightEnvXY.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
      Vector3f localVector3f = new Vector3f(-1.0F, 1.0F, -1.0F); localVector3f.normalize();
      localLightEnvXY.sun().set(localVector3f);
      this.bNotify = true;
    }
  }

  public class _Render3D extends Render
  {
    public Camera3D camera3D;
    public String meshName = null;
    public Actor actorMesh = null;
    public Actor worldMesh = null;
    public float animateMeshA = 0.0F;
    public float animateMeshT = 0.0F;
    public boolean isShadow = false;

    public void preRender()
    {
      checkMesh();
      if (Actor.isValid(this.actorMesh)) {
        if ((this.animateMeshA != 0.0F) || (this.animateMeshT != 0.0F)) {
          this.actorMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(GUIObjectView.this._orient);
          GUIObjectView.this._orient.set(GUIObjectView.this._orient.azimut() + this.animateMeshA * GUIObjectView.this.wRenders.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.deltaTimeSec, GUIObjectView.this._orient.tangage() + this.animateMeshT * GUIObjectView.this.wRenders.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.deltaTimeSec, 0.0F);

          this.actorMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(GUIObjectView.this._orient);

          this.actorMesh.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
        }
        this.worldMesh.draw.preRender(this.worldMesh);
        this.isShadow = ((this.actorMesh.draw.preRender(this.actorMesh) & 0x4) != 0);
      }
    }

    public void render() {
      if (Actor.isValid(this.actorMesh)) {
        Render.prepareStates();
        this.worldMesh.draw.render(this.worldMesh);
        if ((this.isShadow) && (GUIObjectView.this.bGround)) this.actorMesh.draw.renderShadowProjective(this.actorMesh);
        this.actorMesh.draw.render(this.actorMesh);
      }
    }

    public void checkMesh() {
      int i = GUIObjectView.this.wTable.selectRow;
      if (i < 0) {
        if (Actor.isValid(this.actorMesh))
          this.actorMesh.destroy();
        this.actorMesh = null;
      }
      GUIObjectView.ObjectInfo localObjectInfo = (GUIObjectView.ObjectInfo)GUIObjectView.this.wTable.objects.get(i);
      if ((this.meshName == localObjectInfo.meshName) && (Actor.isValid(this.actorMesh))) {
        d1 = ((ActorMesh)this.actorMesh).mesh().visibilityR();

        if (GUIObjectView.this.bGround) {
          d1 *= Math.cos(0.7853981633974483D) / Math.sin(this.camera3D.FOV() * 3.141592653589793D / 180.0D / 2.0D);
          d1 -= GUIObjectView.Z_GAP;
          if (d1 < GUIObjectView.Z_DIST_NEAR) GUIObjectView.Z_DIST_NEAR = d1;
          d1 = GUIObjectView.SCALE_FACTOR;
          GUIObjectView.this._point.set(-d1, 0.0D, 0.0D);
          GUIObjectView.this._o.set(GUIObjectView.this.ROT_X, GUIObjectView.this.ROT_Y - 45, 0.0F);
        } else {
          d1 *= Math.cos(0.2617993877991494D) / Math.sin(this.camera3D.FOV() * 3.141592653589793D / 180.0D / 2.0D);
          d1 -= GUIObjectView.Z_GAP;
          if (d1 < GUIObjectView.Z_DIST_NEAR)
          {
            GUIObjectView.Z_DIST_NEAR = d1;
          }
          d1 = GUIObjectView.SCALE_FACTOR;
          GUIObjectView.this._point.set(-d1, 0.0D, 0.0D);
          GUIObjectView.this._o.set(GUIObjectView.this.ROT_X, GUIObjectView.this.ROT_Y, 0.0F);

          for (int j = 1; j <= 6; j++)
          {
            if (((ActorSimpleHMesh)this.actorMesh).hierMesh().chunkFindCheck("Prop" + j + "_D0") == -1)
              continue;
            ((ActorSimpleHMesh)this.actorMesh).hierMesh().chunkSetAngles("Prop" + j + "_D0", 0.0F, -GUIObjectView.this.propRot + j * 50, 0.0F);
          }

          GUIObjectView.this.propRot = ((GUIObjectView.this.propRot + 20.0F) % 360.0F);
        }

        GUIObjectView.this._o.transform(GUIObjectView.this._point);
        this.camera3D.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(GUIObjectView.this._point, GUIObjectView.this._o);
        return;
      }

      if (Actor.isValid(this.actorMesh))
        this.actorMesh.destroy();
      this.actorMesh = null;
      this.meshName = localObjectInfo.meshName;
      GUIObjectView.this.bGround = localObjectInfo._bGround;
      if (this.meshName == null) {
        return;
      }
      double d1 = 20.0D;

      GUIObjectView.SCALE_FACTOR = GUIObjectView.Z_DIST_BORN;
      GUIObjectView.this.ROT_Y = 20;
      GUIObjectView.this.ROT_X = 220;

      this.worldMesh = new ActorSimpleMesh("3do/GUI/ObjectInspector/" + GUIObjectInspector.type + "/mono.sim");
      int k;
      Object localObject;
      if (this.meshName.toLowerCase().endsWith(".sim")) {
        try {
          this.actorMesh = new ActorSimpleMesh(this.meshName);
        } catch (Exception localException1) {
          System.out.println(localException1.getMessage());
          this.actorMesh = null;
          return;
        }
        d1 = ((ActorMesh)this.actorMesh).mesh().visibilityR();

        double d2 = 0.0D;
        k = ((ActorSimpleMesh)this.actorMesh).mesh().hookFind("Ground_Level");
        if (k != -1) {
          localObject = new Matrix4d();
          ((ActorSimpleMesh)this.actorMesh).mesh().hookMatrix(k, (Matrix4d)localObject);
          d2 = -((Matrix4d)localObject).m23;
          ((ActorSimpleMesh)this.actorMesh).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(new Point3d(0.0D, 0.0D, d2));
        }
      }
      else
      {
        try {
          this.actorMesh = new ActorSimpleHMesh(this.meshName);
        } catch (Exception localException2) {
          System.out.println(localException2.getMessage());
          this.actorMesh = null;
          return;
        }
        d1 = ((ActorHMesh)this.actorMesh).hierMesh().visibilityR();

        double d3 = 0.0D;
        k = ((ActorSimpleHMesh)this.actorMesh).mesh().hookFind("Ground_Level");
        if (k != -1) {
          localObject = new Matrix4d();
          ((ActorSimpleHMesh)this.actorMesh).mesh().hookMatrix(k, (Matrix4d)localObject);
          d3 = -((Matrix4d)localObject).m23;
          ((ActorSimpleHMesh)this.actorMesh).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(new Point3d(0.0D, 0.0D, d3));
        }

        if (!GUIObjectView.this.bGround) {
          if (localObjectInfo.camouflage != null)
            World.cur().setCamouflage(localObjectInfo.camouflage);
          Aircraft.prepareMeshCamouflage(this.meshName, ((ActorHMesh)this.actorMesh).hierMesh());
          if (localObjectInfo.reg != null) {
            localObject = Aircraft.getPropertyPaintScheme(localObjectInfo.airClass, localObjectInfo.reg.country());
            if (localObject != null) {
              int n = 0;
              int i1 = 0;
              int i2 = 0;
              ((PaintScheme)localObject).prepare(localObjectInfo.airClass, ((ActorHMesh)this.actorMesh).hierMesh(), localObjectInfo.reg, n, i1, i2, true);
            }
          }

        }

        for (int m = 1; m <= 6; m++)
        {
          if (((ActorSimpleHMesh)this.actorMesh).hierMesh().chunkFindCheck("Prop" + m + "_D0") == -1)
            continue;
          ((ActorSimpleHMesh)this.actorMesh).hierMesh().chunkVisible("Prop" + m + "_D0", false);
          ((ActorSimpleHMesh)this.actorMesh).hierMesh().chunkVisible("PropRot" + m + "_D0", true);
        }

      }

      GUIObjectView.this.getDistanceProperties();

      if (GUIObjectView.this.bGround) {
        d1 *= Math.cos(0.7853981633974483D) / Math.sin(this.camera3D.FOV() * 3.141592653589793D / 180.0D / 2.0D);
        d1 -= GUIObjectView.Z_GAP;
        if (d1 < GUIObjectView.Z_DIST_NEAR) GUIObjectView.Z_DIST_NEAR = d1;
        d1 = GUIObjectView.SCALE_FACTOR;
        GUIObjectView.this._point.set(-d1, 0.0D, 0.0D);
        GUIObjectView.this._o.set(GUIObjectView.this.ROT_X, GUIObjectView.this.ROT_Y - 45, 0.0F);
      } else {
        d1 *= Math.cos(0.2617993877991494D) / Math.sin(this.camera3D.FOV() * 3.141592653589793D / 180.0D / 2.0D);
        d1 -= GUIObjectView.Z_GAP;
        if (d1 < GUIObjectView.Z_DIST_NEAR)
        {
          GUIObjectView.Z_DIST_NEAR = d1;
        }
        d1 = GUIObjectView.SCALE_FACTOR;
        GUIObjectView.this._point.set(-d1, 0.0D, 0.0D);
        GUIObjectView.this._o.set(GUIObjectView.this.ROT_X, GUIObjectView.this.ROT_Y, 0.0F);
      }

      GUIObjectView.this._o.transform(GUIObjectView.this._point);
      this.camera3D.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(GUIObjectView.this._point, GUIObjectView.this._o);

      this.camera3D.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
      if (GUIObjectView.this.bGround)
        this.animateMeshT = 0.0F;
    }

    public _Render3D(Renders paramFloat, float arg3)
    {
      super(localObject);
      setClearColor(new Color4f(0.39F, 0.35F, 0.23F, 1.0F));
      useClearStencil(true);
    }
  }

  static class ObjectInfo
  {
    public String key;
    public String name;
    public String meshName;
    public boolean _bGround;
    public Class airClass;
    public Regiment reg;
    public String camouflage;

    public ObjectInfo(String paramString1, String paramString2, String paramString3, boolean paramBoolean, Class paramClass, String paramString4, String paramString5)
    {
      this.key = paramString2;
      this.meshName = paramString3;
      this._bGround = paramBoolean;
      this.camouflage = paramString5;
      if (!paramBoolean) {
        this.name = I18N.plane(paramString2);
        this.airClass = paramClass;
        if (paramString4 != null) {
          this.reg = ((Regiment)Actor.getByName(paramString4));
          this.meshName = Aircraft.getPropertyMesh(this.airClass, this.reg.country());
        }
      } else {
        try {
          ResourceBundle localResourceBundle = ResourceBundle.getBundle(paramString1, RTSConf.cur.locale, LDRres.loader());
          this.name = localResourceBundle.getString(paramString2 + "_NAME");
        } catch (Exception localException) {
          this.name = paramString2;
        }
      }
    }
  }
}