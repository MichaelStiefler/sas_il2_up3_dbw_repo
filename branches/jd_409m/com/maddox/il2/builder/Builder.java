package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowHScrollBar;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowManager;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBar;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMenuPopUp;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowRootMenu;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.gwindow.GWindowVSliderInt;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.ActorPosStatic;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.GUIWindowManager._Render;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Land2D;
import com.maddox.il2.engine.Land2DText;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.engine.TextScr;
import com.maddox.il2.engine.hotkey.HookView;
import com.maddox.il2.engine.hotkey.MouseXYZATK;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Main3D.Render2D;
import com.maddox.il2.game.Main3D.Render3D0;
import com.maddox.il2.game.Main3D.Render3D1;
import com.maddox.il2.game.Main3D.RenderMap2D;
import com.maddox.il2.gui.SquareLabels;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.effects.SpritesFog;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.MsgTimerListener;
import com.maddox.rts.MsgTimerParam;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.SoftClass;
import com.maddox.rts.Time;
import com.maddox.rts.Timer;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Map.Entry;

public class Builder
{
  public static String PLUGINS_SECTION = "builder_plugins";
  public static String envName;
  public static float defaultAzimut = 0.0F;
  public static final int colorTargetPrimary = -1;
  public static final int colorTargetSecondary = -16711936;
  public static final int colorTargetSecret = -8454144;
  public static float MaxVisualDistance = 16000.0F;

  public static float saveMaxVisualDistance = 5000.0F;
  public static float saveMaxStaticVisualDistance = 3000.0F;
  public BldConfig conf;
  public TTFont smallFont;
  public static final double viewHLandMin = 50.0D;
  private Actor selectedActor;
  private HashMapExt selectedActors = new HashMapExt();
  public Camera3D camera;
  public CameraOrtho2D camera2D;
  private MouseXYZATK mouseXYZATK;
  private CursorMesh cursor;
  public boolean bMultiSelect;
  public boolean bSnap = false;
  public double snapStep = 10.0D;
  public Pathes pathes;
  private boolean bFreeView = false;

  private double viewDistance = 100000.0D;

  private double viewHMax = -1.0D;
  private double viewH = -1.0D;
  private double viewHLand = -1.0D;
  private double viewX = 1.0D;
  private double viewY = 1.0D;
  private boolean bView3D = false;

  private Point3d _camPoint = new Point3d();
  private Orient _camOrient = new Orient(-90.0F, -90.0F, 0.0F);

  private Point3d __posScreenToLand = new Point3d();

  private SelectFilter _selectFilter = new SelectFilter();

  private Point3d __pi = new Point3d();
  private Orient __oi = new Orient();

  ArrayList _deleted = new ArrayList();
  private Actor[] _selectedActors;
  private boolean _bSelect;
  private double _selectX0;
  private double _selectY0;
  private double _selectX1;
  private double _selectY1;
  private FilterSelect filterSelect = new FilterSelect();

  private int saveMouseMode = 2;

  private Loc _savCameraNoFreeLoc = new Loc();
  private Loc _savCameraFreeLoc = new Loc();
  public static final int MOUSE_NONE = 0;
  public static final int MOUSE_OBJECT_MOVE = 1;
  public static final int MOUSE_WORLD_ZOOM = 2;
  public static final int MOUSE_MULTISELECT = 3;
  public static final int MOUSE_SELECT_TARGET = 4;
  public static final int MOUSE_FILL = 5;
  public int mouseState = 0;

  int mouseFirstPosX = 0;
  int mouseFirstPosY = 0;
  int mousePosX = 0;
  int mousePosY = 0;
  boolean bMouseRenderRect = false;
  Actor movedActor = null;
  Point3d movedActorPosSnap = new Point3d();
  Point3d movedActorPosStepSave = new Point3d();

  private Point3d _objectMoveP = new Point3d();

  protected Point3d projectPos3d = new Point3d();

  private float[] line5XYZ = new float[15];
  private Mat emptyMat;
  private float[] line2XYZ = new float[6];
  protected TTFont _gridFont;
  private int _gridCount;
  private int[] _gridX = new int[20];
  private int[] _gridY = new int[20];
  private int[] _gridVal = new int[20];

  protected Point2d projectPos2d = new Point2d();
  private float[] lineNXYZ = new float[6];
  private int lineNCounter;
  private Actor overActor = null;
  private Mat selectTargetMat;
  private GWindowMenuPopUp popUpMenu;
  private GWindowMessageBox deletingMessageBox;
  private boolean bDeletingChangeSelection;
  private Actor deletingActor;
  private boolean bRotateObjects = false;
  public ClientWindow clientWindow;
  public ViewWindow viewWindow;
  public XScrollBar mapXscrollBar;
  public YScrollBar mapYscrollBar;
  public ZSlider mapZSlider;
  public WSelect wSelect;
  public WViewLand wViewLand;
  public WSnap wSnap;
  public GWindowMenuBarItem mFile;
  public GWindowMenuBarItem mEdit;
  public GWindowMenuBarItem mView;
  public GWindowMenuItem mSelectItem;
  public GWindowMenuItem mViewLand;
  public GWindowMenuItem mSnap;
  public GWindowMenuItem mAlignItem;
  public GWindowMenuItem mDisplayFilter;
  public GWindowMenuItem mGrayScaleMap;
  public GWindowMenuItem mIcon8;
  public GWindowMenuItem mIcon16;
  public GWindowMenuItem mIcon32;
  public GWindowMenuItem mIcon64;
  private static int[] _viewPort = new int[4];

  public static int armyAmount()
  {
    return Army.amountSingle();
  }

  public static int colorSelected()
  {
    long l1 = Time.currentReal();
    long l2 = 1000L;
    double d = 2.0D * (l1 % l2) / l2;
    if (d >= 1.0D)
      d = 2.0D - d;
    int i = (int)(255.0D * d);
    return 0xFF000000 | i << 16 | i << 8 | i;
  }

  public boolean isLoadedLandscape()
  {
    return PlMapLoad.getLandLoaded() != null;
  }
  public boolean isFreeView() {
    return this.bFreeView; } 
  public boolean isView3D() { return this.bView3D; } 
  public double viewDistance() {
    return this.viewDistance;
  }

  public void computeViewMap2D(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (!isLoadedLandscape()) return;
    int i = (int)this.viewWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
    int j = (int)this.viewWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
    double d1 = this.camera.FOV() * 3.141592653589793D / 180.0D / 2.0D;
    double d2 = i / j;
    if (paramDouble1 < 0.0D)
    {
      this.viewHMax = (Main3D.cur3D().land2D.mapSizeX() / 2.0D / Math.tan(d1));
      d3 = Main3D.cur3D().land2D.mapSizeY() / 2.0D / Math.tan(d1) * d2;
      if (d3 < this.viewHMax)
        this.viewHMax = d3;
      k = (int)(Math.sqrt(1.0D) * 100.0D);
      m = (int)(Math.sqrt(this.viewHMax) * 100.0D);
      this.mapZSlider.setRange(k, m - k + 1, k);
      paramDouble1 = this.viewHMax;
      paramDouble2 = Main3D.cur3D().land2D.mapSizeX() / 2.0D - Main3D.cur3D().land2D.worldOfsX();
      paramDouble3 = Main3D.cur3D().land2D.mapSizeY() / 2.0D - Main3D.cur3D().land2D.worldOfsY();
    }
    double d3 = Engine.land().HQ(paramDouble2, paramDouble3);
    if (paramDouble1 < 50.0D + d3) paramDouble1 = 50.0D + d3;
    if (paramDouble1 > this.viewHMax) paramDouble1 = this.viewHMax;
    if (this.viewH != paramDouble1) {
      this.viewH = paramDouble1;
      this.mapZSlider.setPos((int)(Math.sqrt(this.viewH) * 100.0D), false);
    }
    this.viewHLand = (this.viewH - d3);

    this.camera2D.worldScale = (i / (2.0D * this.viewH * Math.tan(d1)));
    int k = (int)(Main3D.cur3D().land2D.mapSizeX() * this.camera2D.worldScale + 0.5D) > i ? 1 : 0;
    int m = (int)(Main3D.cur3D().land2D.mapSizeY() * this.camera2D.worldScale + 0.5D) > j ? 1 : 0;

    this.viewX = (i / this.camera2D.worldScale);
    if (k != 0) {
      this.camera2D.worldXOffset = (paramDouble2 - this.viewX / 2.0D);
      if (this.camera2D.worldXOffset < -Main3D.cur3D().land2D.worldOfsX())
        this.camera2D.worldXOffset = (-Main3D.cur3D().land2D.worldOfsX());
      if (this.camera2D.worldXOffset > Main3D.cur3D().land2D.mapSizeX() - Main3D.cur3D().land2D.worldOfsX() - this.viewX)
        this.camera2D.worldXOffset = (Main3D.cur3D().land2D.mapSizeX() - Main3D.cur3D().land2D.worldOfsX() - this.viewX);
      this.mapXscrollBar.setRange(0.0F, (int)(Main3D.cur3D().land2D.mapSizeX() * 100.0D), (int)(this.viewX * 100.0D), (int)(this.viewX * 100.0D / 64.0D), (int)((this.camera2D.worldXOffset + Main3D.cur3D().land2D.worldOfsX()) * 100.0D));
    }
    else
    {
      this.camera2D.worldXOffset = (-(this.viewX - Main3D.cur3D().land2D.mapSizeX()) / 2.0D - Main3D.cur3D().land2D.worldOfsX());
      this.mapXscrollBar.setRange(0.0F, 0.0F, 1.0F, 1.0F, 0.0F);
    }

    this.viewY = (j / this.camera2D.worldScale);
    if (m != 0) {
      this.camera2D.worldYOffset = (paramDouble3 - this.viewY / 2.0D);
      if (this.camera2D.worldYOffset < -Main3D.cur3D().land2D.worldOfsY())
        this.camera2D.worldYOffset = (-Main3D.cur3D().land2D.worldOfsY());
      if (this.camera2D.worldYOffset > Main3D.cur3D().land2D.mapSizeY() - Main3D.cur3D().land2D.worldOfsY() - this.viewY)
        this.camera2D.worldYOffset = (Main3D.cur3D().land2D.mapSizeY() - Main3D.cur3D().land2D.worldOfsY() - this.viewY);
      this.mapYscrollBar.setRange(0.0F, (int)(Main3D.cur3D().land2D.mapSizeY() * 100.0D), (int)(this.viewY * 100.0D), (int)(this.viewY * 100.0D / 64.0D), (int)((Main3D.cur3D().land2D.mapSizeY() - this.viewY - this.camera2D.worldYOffset - Main3D.cur3D().land2D.worldOfsY()) * 100.0D));
    }
    else
    {
      this.camera2D.worldYOffset = (-(this.viewY - Main3D.cur3D().land2D.mapSizeY()) / 2.0D - Main3D.cur3D().land2D.worldOfsY());
      this.mapYscrollBar.setRange(0.0F, 0.0F, 1.0F, 1.0F, 0.0F);
    }
    double d4 = Math.tan(d1) * this.viewH;
    double d5 = d4 / d2;
    double d6 = Math.sqrt(d4 * d4 + d5 * d5);
    this.viewDistance = Math.sqrt(this.viewH * this.viewH + d6 * d6);
    if (this.viewDistance > MaxVisualDistance) {
      this.bView3D = false;
      Main3D.cur3D().land2D.show(this.conf.bShowLandscape);
      Main3D.cur3D().renderMap2D.useClearColor(true);
      Main3D.cur3D().render3D0.setShow(false);
      Main3D.cur3D().render3D1.setShow(false);
      Main3D.cur3D().render2D.setShow(false);
    } else {
      this.bView3D = true;
      Main3D.cur3D().land2D.show(false);
      Main3D.cur3D().renderMap2D.useClearColor(false);
      Main3D.cur3D().render3D0.setShow(true);
      Main3D.cur3D().render3D1.setShow(true);
      Main3D.cur3D().render2D.setShow(true);
    }
    setPosCamera3D();
    repaint();
  }

  private void setPosCamera3D() {
    this._camPoint.jdField_z_of_type_Double = this.viewH;
    this._camPoint.jdField_x_of_type_Double = (this.camera2D.worldXOffset + (this.camera2D.right - this.camera2D.left) / this.camera2D.worldScale / 2.0D);
    this._camPoint.jdField_y_of_type_Double = (this.camera2D.worldYOffset + (this.camera2D.top - this.camera2D.bottom) / this.camera2D.worldScale / 2.0D);
    this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this._camPoint, this._camOrient); this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
  }

  public double posX2DtoWorld(int paramInt)
  {
    return this.camera2D.worldXOffset + paramInt / this.camera2D.worldScale;
  }
  public double posY2DtoWorld(int paramInt) {
    return this.camera2D.worldYOffset + paramInt / this.camera2D.worldScale;
  }
  public Point3d posScreenToLand(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2) {
    Point3d localPoint3d = this.__posScreenToLand;
    if (this.bView3D) {
      double d1 = this.camera2D.worldXOffset + (this.camera2D.right - this.camera2D.left) / this.camera2D.worldScale / 2.0D;
      double d2 = this.camera2D.worldYOffset + (this.camera2D.top - this.camera2D.bottom) / this.camera2D.worldScale / 2.0D;
      localPoint3d.jdField_x_of_type_Double = posX2DtoWorld(paramInt1);
      localPoint3d.jdField_y_of_type_Double = posY2DtoWorld(paramInt2);
      localPoint3d.jdField_z_of_type_Double = (Engine.land().HQ(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double) + paramDouble1); if (localPoint3d.jdField_z_of_type_Double > this.viewH) localPoint3d.jdField_z_of_type_Double = this.viewH;
      double d3 = (localPoint3d.jdField_x_of_type_Double - d1) / this.viewH;
      double d4 = (localPoint3d.jdField_y_of_type_Double - d2) / this.viewH;
      double d5 = 0.0D;
      double d6 = (localPoint3d.jdField_z_of_type_Double - d5) * (localPoint3d.jdField_z_of_type_Double - d5);
      for (int i = 0; (i < 8) && (d6 > paramDouble2); i++) {
        d5 = localPoint3d.jdField_z_of_type_Double;
        localPoint3d.jdField_x_of_type_Double = ((this.viewH - d5) * d3 + d1);
        localPoint3d.jdField_y_of_type_Double = ((this.viewH - d5) * d4 + d2);
        localPoint3d.jdField_z_of_type_Double = (Engine.land().HQ(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double) + paramDouble1); if (localPoint3d.jdField_z_of_type_Double > this.viewH) localPoint3d.jdField_z_of_type_Double = this.viewH;
        d6 = (localPoint3d.jdField_z_of_type_Double - d5) * (localPoint3d.jdField_z_of_type_Double - d5);
      }
      int j = 0;
      do { d5 = localPoint3d.jdField_z_of_type_Double;
        localPoint3d.jdField_x_of_type_Double = (((this.viewH - d5) * d3 + d1 + localPoint3d.jdField_x_of_type_Double) / 2.0D);
        localPoint3d.jdField_y_of_type_Double = (((this.viewH - d5) * d4 + d2 + localPoint3d.jdField_y_of_type_Double) / 2.0D);
        localPoint3d.jdField_z_of_type_Double = (Engine.land().HQ(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double) + paramDouble1); if (localPoint3d.jdField_z_of_type_Double > this.viewH) localPoint3d.jdField_z_of_type_Double = this.viewH;
        d6 = (localPoint3d.jdField_z_of_type_Double - d5) * (localPoint3d.jdField_z_of_type_Double - d5);

        j++; if (j >= 8) break;  }
      while (d6 > paramDouble2);
    }
    else
    {
      localPoint3d.jdField_x_of_type_Double = posX2DtoWorld(paramInt1);
      localPoint3d.jdField_y_of_type_Double = posY2DtoWorld(paramInt2);
      localPoint3d.jdField_z_of_type_Double = Engine.land().HQ(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double);
    }
    return localPoint3d;
  }

  public Point3d mouseWorldPos()
  {
    return posScreenToLand(this.mousePosX, this.mousePosY, 0.0D, 0.1D);
  }

  public Actor selectNear(Point3d paramPoint3d) {
    Actor localActor = selectNear(paramPoint3d, 100.0D);
    if (localActor != null)
      return localActor;
    return selectNear(paramPoint3d, 10000.0D);
  }

  public Actor selectNearFull(int paramInt1, int paramInt2) {
    if ((paramInt1 < 0) || (paramInt2 < 0)) return null;
    Point3d localPoint3d = posScreenToLand(paramInt1, paramInt2, 0.0D, 0.1D);
    return selectNear(localPoint3d);
  }

  public Actor selectNear(int paramInt1, int paramInt2) {
    if ((paramInt1 < 0) || (paramInt2 < 0)) return null;
    Point3d localPoint3d = posScreenToLand(paramInt1, paramInt2, 0.0D, 0.1D);
    double d1 = this.viewH - localPoint3d.jdField_z_of_type_Double;
    if (d1 < 0.001D) d1 = 0.001D;
    double d2 = this.conf.iconSize * d1 / this.viewH / this.camera2D.worldScale / 2.0D;
    return selectNear(localPoint3d, d2);
  }

  public Actor selectNear(Point3d paramPoint3d, double paramDouble) {
    this._selectFilter.reset(paramDouble * paramDouble);
    Engine.drawEnv().getFiltered((AbstractCollection)null, paramPoint3d.jdField_x_of_type_Double - paramDouble, paramPoint3d.jdField_y_of_type_Double - paramDouble, paramPoint3d.jdField_x_of_type_Double + paramDouble, paramPoint3d.jdField_y_of_type_Double + paramDouble, 15, this._selectFilter);

    return this._selectFilter.get();
  }

  private void worldScrool(double paramDouble1, double paramDouble2)
  {
    double d1 = this.camera2D.worldXOffset + this.viewX / 2.0D + paramDouble1;
    double d2 = this.camera2D.worldYOffset + this.viewY / 2.0D + paramDouble2;
    double d3 = Engine.land().HQ(d1, d2);
    double d4 = this.viewH;
    if (this.conf.bSaveViewHLand)
      d4 = d3 + this.viewHLand;
    computeViewMap2D(d4, d1, d2);
  }

  public void align(Actor paramActor)
  {
    if ((paramActor instanceof ActorAlign)) {
      ((ActorAlign)paramActor).align();
    } else {
      paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.__pi);
      double d = Engine.land().HQ(this.__pi.jdField_x_of_type_Double, this.__pi.jdField_y_of_type_Double) + 0.2D;
      this.__pi.jdField_z_of_type_Double = d;
      paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.__pi);
    }
  }

  public void deleteAll() {
    setSelected(null);
    Plugin.doDeleteAll();
    Plugin.doAfterDelete();
    selectedActorsValidate();
    this.pathes.clear();
  }

  public int countSelectedActors()
  {
    if (this.bMultiSelect) {
      if (Actor.isValid(this.selectedActor)) {
        if (this.selectedActors.containsKey(this.selectedActor)) return this.selectedActors.size();
        return this.selectedActors.size() + 1;
      }
      return this.selectedActors.size();
    }

    return Actor.isValid(this.selectedActor) ? 1 : 0;
  }

  public void selectActorsClear()
  {
    if (this.bMultiSelect)
      this.selectedActors.clear(); 
  }

  public void selectActorsAdd(Actor paramActor) {
    if (this.bMultiSelect)
      this.selectedActors.put(paramActor, null);
  }

  public Actor[] selectedActors()
  {
    if (this.bMultiSelect) {
      int i = countSelectedActors();
      Actor[] arrayOfActor2 = _selectedActors(i > 0 ? i : 1);
      int j = 0;
      if (Actor.isValid(this.selectedActor)) {
        arrayOfActor2[(j++)] = this.selectedActor;
      }
      if (this.selectedActors.size() > 0) {
        Map.Entry localEntry = this.selectedActors.nextEntry(null);
        while (localEntry != null) {
          Actor localActor = (Actor)localEntry.getKey();
          if ((Actor.isValid(localActor)) && (localActor != this.selectedActor))
            arrayOfActor2[(j++)] = localActor;
          localEntry = this.selectedActors.nextEntry(localEntry);
        }
      }
      if (j == 0) {
        arrayOfActor2[0] = null;
      }
      else if (arrayOfActor2.length > j) {
        arrayOfActor2[j] = null;
      }
      return arrayOfActor2;
    }

    Actor[] arrayOfActor1 = _selectedActors(1);
    if (Actor.isValid(this.selectedActor)) {
      arrayOfActor1[0] = this.selectedActor;
      if (arrayOfActor1.length > 1)
        arrayOfActor1[1] = null;
    } else {
      arrayOfActor1[0] = null;
    }
    return arrayOfActor1;
  }

  private Actor[] _selectedActors(int paramInt) {
    if ((this._selectedActors == null) || (this._selectedActors.length < paramInt)) {
      this._selectedActors = new Actor[paramInt];
    }
    return this._selectedActors;
  }

  public void selectedActorsValidate()
  {
    Actor[] arrayOfActor = selectedActors();
    for (int i = 0; i < arrayOfActor.length; i++) {
      Actor localActor = arrayOfActor[i];
      if (localActor == null) break;
      if ((!Actor.isValid(localActor)) || (!localActor.isDrawing()))
        this.selectedActors.remove(localActor);
    }
  }

  public void select(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean) {
    if (paramDouble3 > paramDouble1) { this._selectX0 = paramDouble1; this._selectX1 = paramDouble3; } else {
      this._selectX0 = paramDouble3; this._selectX1 = paramDouble1;
    }if (paramDouble4 > paramDouble2) { this._selectY0 = paramDouble2; this._selectY1 = paramDouble4; } else {
      this._selectY0 = paramDouble4; this._selectY1 = paramDouble2;
    }this._bSelect = paramBoolean;
    Engine.drawEnv().getFiltered((AbstractCollection)null, this._selectX0, this._selectY0, this._selectX1, this._selectY1, 15, this.filterSelect);
  }

  public boolean isMiltiSelected(Actor paramActor)
  {
    if (this.bMultiSelect)
      return this.selectedActors.containsKey(paramActor);
    return false;
  }

  public boolean isSelected(Actor paramActor) {
    if (this.bMultiSelect) {
      if (paramActor == this.selectedActor) return true;
      return this.selectedActors.containsKey(paramActor);
    }
    return paramActor == this.selectedActor;
  }

  public Actor selectedActor()
  {
    return this.selectedActor;
  }
  public PPoint selectedPoint() {
    if (this.pathes == null) return null;
    return this.pathes.currentPPoint;
  }
  public Path selectedPath() {
    if (this.pathes == null) return null;
    if (!Actor.isValid(this.pathes.currentPPoint))
      return null;
    return (Path)selectedPoint().getOwner();
  }

  public void setSelected(Actor paramActor) {
    if (!this.conf.bEnableSelect) return;
    if (Actor.isValid(this.selectedActor)) {
      Plugin localPlugin1 = (Plugin)Property.value(this.selectedActor, "builderPlugin");
      if ((localPlugin1 instanceof PlMisStatic)) {
        defaultAzimut = this.selectedActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient().azimut();
      }
    }
    int i = this.wSelect.tabsClient.getCurrent();
    this.wSelect.clearExtendTabs();
    Plugin localPlugin2;
    if ((paramActor != null) && ((paramActor instanceof PPoint))) {
      this.pathes.currentPPoint = ((PPoint)paramActor);
      this.selectedActor = null;
      localPlugin2 = (Plugin)Property.value(paramActor.getOwner(), "builderPlugin");
      localPlugin2.syncSelector();

      if ((paramActor instanceof PAir))
        tip(Plugin.i18n("Selected") + " " + ((PathAir)paramActor.getOwner()).typedName);
      else if ((paramActor instanceof PNodes))
        tip(Plugin.i18n("Selected") + " " + Property.stringValue(paramActor.getOwner(), "i18nName", ""));
    }
    else {
      if (this.pathes != null)
        this.pathes.currentPPoint = null;
      this.selectedActor = paramActor;
      if (isFreeView())
        setActorView(paramActor);
      if (paramActor != null) {
        localPlugin2 = (Plugin)Property.value(paramActor, "builderPlugin");
        if (localPlugin2 != null) {
          localPlugin2.syncSelector();
          if ((localPlugin2 instanceof PlMisStatic))
            defaultAzimut = paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient().azimut();
        } else if (this.bMultiSelect) {
          localPlugin2 = Plugin.getPlugin("MapActors");
          localPlugin2.syncSelector();
        }
        String str = null;
        if (this.bMultiSelect) {
          str = (paramActor instanceof SoftClass) ? ((SoftClass)paramActor).fullClassName() : paramActor.getClass().getName();

          int j = str.lastIndexOf('.');
          str = str.substring(j + 1);
        } else {
          str = Property.stringValue(paramActor.getClass(), "i18nName", "");
        }
        tip(Plugin.i18n("Selected") + " " + str);
      } else {
        tip("");
      }
    }
    if ((i > 0) && (i < this.wSelect.tabsClient.sizeTabs()))
      this.wSelect.tabsClient.setCurrent(i);
  }

  public void doUpdateSelector() {
    if (Actor.isValid(this.pathes.currentPPoint)) {
      Plugin localPlugin = (Plugin)Property.value(this.pathes.currentPPoint.getOwner(), "builderPlugin");
      localPlugin.updateSelector();
    }
  }

  private void setActorView(Actor paramActor) {
    if (!Actor.isValid(paramActor))
      return;
    if (actorView() != paramActor) {
      if ((paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos instanceof ActorPosStatic)) {
        boolean bool = paramActor.isCollide();
        paramActor.collide(false);
        paramActor.drawing(false);
        paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos = new ActorPosMove(paramActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos);
        paramActor.drawing(true);
        if (bool)
          paramActor.collide(true);
      }
      this.mouseXYZATK.setTarget(paramActor);
      this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(paramActor, Main3D.cur3D().hookView, false);
      this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
      this.cursor.drawing(paramActor == this.cursor);
    }
  }

  private void setActorView()
  {
    this.pathes.currentPPoint = null;
    this.bFreeView = true;
    this.saveMouseMode = RTSConf.cur.getUseMouse();
    if (this.saveMouseMode != 2)
      RTSConf.cur.setUseMouse(2);
    this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this._savCameraNoFreeLoc);
    Object localObject;
    if (Actor.isValid(selectedActor())) {
      localObject = selectedActor();
    } else {
      localObject = this.cursor;
      this.selectedActor = ((Actor)localObject);
      Point3d localPoint3d = new Point3d();
      this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(localPoint3d);
      localPoint3d.jdField_z_of_type_Double = (Engine.land().HQ(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double) + 0.2D);
      ((Actor)localObject).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localPoint3d);
    }
    setActorView((Actor)localObject);
    this.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.manager.activateMouse(false);
    this.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.manager.activateKeyboard(false);
    HotKeyCmdEnv.enable("HookView", true);
    HotKeyCmdEnv.enable("MouseXYZ", true);
    this.viewWindow.jdField_mouseCursor_of_type_Int = 0;
    if (!this.bMultiSelect) {
      Main3D.cur3D().spritesFog.setShow(true);

      if (Main3D.cur3D().clouds != null) {
        Main3D.cur3D().bDrawClouds = true;
        Main3D.cur3D().clouds.setShow(true);
      }
      Main3D.cur3D().bEnableFog = true;
    }
    if (this.conf.bAnimateCamera) {
      this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this._savCameraFreeLoc);
      new AnimateView((Actor)localObject, this._savCameraNoFreeLoc, this._savCameraFreeLoc);
    }
  }

  private void clearActorView() {
    this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this._savCameraFreeLoc);
    this.mouseXYZATK.setTarget(null);
    this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(null, null, false);

    computeViewMap2D(this._savCameraNoFreeLoc.getZ(), this._savCameraFreeLoc.getX(), this._savCameraFreeLoc.getY());
    this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this._savCameraNoFreeLoc);

    if (this.conf.bAnimateCamera)
      new AnimateView(null, this._savCameraFreeLoc, this._savCameraNoFreeLoc);
    else
      endClearActorView();
  }

  private void endClearActorView()
  {
    this.bFreeView = false;
    if (this.saveMouseMode != 2) {
      RTSConf.cur.setUseMouse(this.saveMouseMode);
    }

    this.cursor.drawing(false);
    if (selectedActor() == this.cursor)
      setSelected(null);
    selectedActorsValidate();
    this.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.manager.activateMouse(true);
    this.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.manager.activateKeyboard(true);
    HotKeyCmdEnv.enable("HookView", false);
    HotKeyCmdEnv.enable("MouseXYZ", false);
    this.viewWindow.jdField_mouseCursor_of_type_Int = 1;
    PlMission.setChanged();
    if (!this.bMultiSelect) {
      Main3D.cur3D().spritesFog.setShow(false);

      if (Main3D.cur3D().clouds != null)
        Main3D.cur3D().clouds.setShow(false);
      Main3D.cur3D().bEnableFog = false;
    }
    repaint();
  }

  private Actor actorView()
  {
    return this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.base();
  }

  public void doMouseAbsMove(int paramInt1, int paramInt2)
  {
    if (!isLoadedLandscape()) return;
    if (isFreeView()) return;
    if (this.mousePosX == -1) {
      this.mousePosX = paramInt1;
      this.mousePosY = paramInt2;
      return;
    }
    Point3d localPoint3d = posScreenToLand(this.mousePosX, this.mousePosY, 0.0D, 0.1D);

    switch (this.mouseState)
    {
    case 1:
      double d1 = this.camera2D.worldScale;
      double d2;
      if (this.bView3D) {
        d2 = this.viewH - localPoint3d.jdField_z_of_type_Double;
        if (d2 < 0.001D) d2 = 0.001D;
        d1 *= this.viewH / d2;
      }
      if (this.movedActor == null) {
        worldScrool((this.mousePosX - paramInt1) / d1, (this.mousePosY - paramInt2) / d1);
      } else {
        d2 = (paramInt1 - this.mousePosX) / d1;
        double d3 = (paramInt2 - this.mousePosY) / d1;
        if (this.bSnap) {
          this.movedActorPosSnap.jdField_x_of_type_Double += d2;
          this.movedActorPosSnap.jdField_y_of_type_Double += d3;
          this.movedActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.movedActorPosStepSave);
          this._objectMoveP.set(this.movedActorPosSnap);
          this._objectMoveP.jdField_x_of_type_Double = (Math.round(this.movedActorPosSnap.jdField_x_of_type_Double / this.snapStep) * this.snapStep);
          this._objectMoveP.jdField_y_of_type_Double = (Math.round(this.movedActorPosSnap.jdField_y_of_type_Double / this.snapStep) * this.snapStep);
          this._objectMoveP.jdField_z_of_type_Double = this.movedActorPosSnap.jdField_z_of_type_Double;
        } else {
          this.movedActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this._objectMoveP);
          this._objectMoveP.jdField_x_of_type_Double += d2;
          this._objectMoveP.jdField_y_of_type_Double += d3;
        }
        try
        {
          Object localObject;
          if ((this.movedActor instanceof PPoint)) {
            localObject = (PPoint)this.movedActor;
            ((PPoint)localObject).moveTo(this._objectMoveP);
            ((Path)((PPoint)localObject).getOwner()).pointMoved((PPoint)localObject);
            PlMission.setChanged();
          } else if (!(this.movedActor instanceof Bridge))
          {
            if (!(this.movedActor instanceof ActorLabel))
            {
              if (!(this.movedActor instanceof ActorBorn))
              {
                this.movedActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this._objectMoveP); this.movedActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
                align(this.movedActor);
                PlMission.setChanged();
              }
            }
          }
          if ((this.bMultiSelect) && (this.selectedActors.containsKey(this.movedActor))) {
            localObject = selectedActors();
            for (int i = 0; i < localObject.length; i++) {
              Actor localActor2 = localObject[i];
              if (localActor2 == null) break;
              if ((Actor.isValid(localActor2)) && (localActor2 != this.movedActor)) {
                if ((localActor2 instanceof ActorAlign)) {
                  localActor2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.__pi);
                  if (this.bSnap) {
                    this.__pi.jdField_x_of_type_Double += this._objectMoveP.jdField_x_of_type_Double - this.movedActorPosStepSave.jdField_x_of_type_Double;
                    this.__pi.jdField_y_of_type_Double += this._objectMoveP.jdField_y_of_type_Double - this.movedActorPosStepSave.jdField_y_of_type_Double;
                  } else {
                    this.__pi.jdField_x_of_type_Double += d2; this.__pi.jdField_y_of_type_Double += d3;
                  }
                  localActor2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.__pi);
                  ((ActorAlign)localActor2).align();
                } else {
                  localActor2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.__pi);
                  this.__pi.jdField_x_of_type_Double += d2; this.__pi.jdField_y_of_type_Double += d3;
                  double d4 = Engine.land().HQ(this.__pi.jdField_x_of_type_Double, this.__pi.jdField_y_of_type_Double) + 0.2D;
                  this.__pi.jdField_z_of_type_Double = d4;
                  localActor2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.__pi);
                }
                localActor2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
              }
            }
          }
        } catch (Exception localException) {
          this.mouseState = 0;
          this.viewWindow.jdField_mouseCursor_of_type_Int = 1;
        }
        repaint();
      }

      break;
    case 0:
      Actor localActor1 = selectNear(paramInt1, paramInt2);
      if ((!this.bMultiSelect) && (localActor1 != null) && ((localActor1 instanceof Bridge))) {
        if (this.movedActor != null)
          this.viewWindow.jdField_mouseCursor_of_type_Int = 1;
        this.movedActor = null;
        setOverActor(localActor1);
      }
      else {
        if (localActor1 != null) {
          if (this.movedActor == null)
            this.viewWindow.jdField_mouseCursor_of_type_Int = 7;
          this.movedActor = localActor1;
          this.movedActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.movedActorPosSnap);
        } else {
          if (this.movedActor != null)
            this.viewWindow.jdField_mouseCursor_of_type_Int = 1;
          this.movedActor = null;
        }
        setOverActor(this.movedActor);
      }
      break;
    case 4:
      this.movedActor = null;
      setOverActor(selectNear(paramInt1, paramInt2));
      if ((Actor.isValid(selectedPoint())) || 
        ((selectedActor() instanceof PlMisRocket.Rocket))) break;
      breakSelectTarget(); break;
    case 2:
    case 3:
    }

    this.mousePosX = paramInt1;
    this.mousePosY = paramInt2;
    if (this.mouseState == 5)
      Plugin.doFill(localPoint3d);
    if (this.bMouseRenderRect)
      repaint();
  }

  public void render3D()
  {
    if (!isLoadedLandscape()) return;
    Plugin.doRender3D();
    if (isFreeView())
      return;
    if ((this.conf.bShowGrid) && (this.bView3D))
      drawGrid3D();
  }

  public boolean project2d(Point3d paramPoint3d, Point2d paramPoint2d) {
    return project2d(paramPoint3d.jdField_x_of_type_Double, paramPoint3d.jdField_y_of_type_Double, paramPoint3d.jdField_z_of_type_Double, paramPoint2d);
  }
  public boolean project2d(double paramDouble1, double paramDouble2, double paramDouble3, Point2d paramPoint2d) {
    if (this.bView3D) {
      Main3D.cur3D().project2d(paramDouble1, paramDouble2, paramDouble3, this.projectPos3d);
      paramPoint2d.jdField_x_of_type_Double = (this.projectPos3d.jdField_x_of_type_Double - _viewPort[0]);
      paramPoint2d.jdField_y_of_type_Double = (this.projectPos3d.jdField_y_of_type_Double - _viewPort[1]);
    } else {
      paramPoint2d.jdField_x_of_type_Double = ((paramDouble1 - this.camera2D.worldXOffset) * this.camera2D.worldScale);
      paramPoint2d.jdField_y_of_type_Double = ((paramDouble2 - this.camera2D.worldYOffset) * this.camera2D.worldScale);
    }
    if (paramPoint2d.jdField_x_of_type_Double + this.conf.iconSize < 0.0D) return false;
    if (paramPoint2d.jdField_y_of_type_Double + this.conf.iconSize < 0.0D) return false;
    if (paramPoint2d.jdField_x_of_type_Double - this.conf.iconSize > this.camera2D.right - this.camera2D.left) return false;
    return paramPoint2d.jdField_y_of_type_Double - this.conf.iconSize <= this.camera2D.top - this.camera2D.bottom;
  }

  public void preRenderMap2D()
  {
    if (!isLoadedLandscape()) return;
    Plugin.doPreRenderMap2D();
  }

  public void renderMap2D() {
    if (!isLoadedLandscape()) return;
    if ((!isFreeView()) && (this.conf.iLightLand != 255)) {
      int i = 255 - this.conf.iLightLand << 24 | 0x3F3F3F;
      Render.drawTile(0.0F, 0.0F, this.viewWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.viewWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, 0.0F, this.emptyMat, i, 0.0F, 0.0F, 1.0F, 1.0F);
      Render.drawEnd();
    }
    Plugin.doRenderMap2DBefore();
    if (!isFreeView())
      this.pathes.renderMap2DTargetLines();
    Plugin.doRenderMap2D();
    if (isFreeView())
      return;
    if (this.conf.bShowGrid) {
      if (!this.bView3D)
        drawGrid2D();
      drawGridText();
    }
    this.pathes.renderMap2D(this.bView3D, this.conf.iconSize);
    Plugin.doRenderMap2DAfter();
    if ((this.bMouseRenderRect) && ((this.mouseFirstPosX != this.mousePosX) || (this.mouseFirstPosY != this.mousePosY))) {
      this.line5XYZ[0] = this.mouseFirstPosX; this.line5XYZ[1] = this.mouseFirstPosY; this.line5XYZ[2] = 0.0F;
      this.line5XYZ[3] = this.mousePosX; this.line5XYZ[4] = this.mouseFirstPosY; this.line5XYZ[5] = 0.0F;
      this.line5XYZ[6] = this.mousePosX; this.line5XYZ[7] = this.mousePosY; this.line5XYZ[8] = 0.0F;
      this.line5XYZ[9] = this.mouseFirstPosX; this.line5XYZ[10] = this.mousePosY; this.line5XYZ[11] = 0.0F;

      Render.drawBeginLines(-1);
      Render.drawLines(this.line5XYZ, 4, 1.0F, colorSelected(), Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE, 4);

      Render.drawEnd();
    }
    drawInfoOverActor();
    drawSelectTarget();
    if (Main3D.cur3D().land2DText != null)
      Main3D.cur3D().land2DText.render();
    if ((this.conf.bShowGrid) && (!this.bView3D))
      SquareLabels.draw(this.camera2D, Main3D.cur3D().land2D.worldOfsX(), Main3D.cur3D().land2D.worldOfsX(), Main3D.cur3D().land2D.mapSizeX());
  }

  private int gridStep()
  {
    double d = this.viewX;
    if (this.viewY < this.viewX) d = this.viewY;
    d *= this.viewHLand / this.viewH;
    int i = 100000;
    for (int j = 0; j < 5; j++) {
      if (i * 3 <= d)
        break;
      i /= 10;
    }
    return i;
  }
  private void drawGrid2D() {
    int i = gridStep();
    int j = (int)((this.camera2D.worldXOffset + Main3D.cur3D().land2D.worldOfsX()) / i);
    int k = (int)((this.camera2D.worldYOffset + Main3D.cur3D().land2D.worldOfsY()) / i);
    int m = (int)(this.viewX / i) + 2;
    int n = (int)(this.viewY / i) + 2;
    float f1 = (float)((j * i - this.camera2D.worldXOffset - Main3D.cur3D().land2D.worldOfsX()) * this.camera2D.worldScale + 0.5D);
    float f2 = (float)((k * i - this.camera2D.worldYOffset - Main3D.cur3D().land2D.worldOfsY()) * this.camera2D.worldScale + 0.5D);
    float f3 = (float)(m * i * this.camera2D.worldScale);
    float f4 = (float)(n * i * this.camera2D.worldScale);
    float f5 = (float)(i * this.camera2D.worldScale);
    this._gridCount = 0;
    Render.drawBeginLines(-1);
    for (int i1 = 0; i1 <= n; i1++) {
      float f6 = f2 + i1 * f5;
      int i3 = (i1 + k) % 10 == 0 ? 192 : 127;
      this.line2XYZ[0] = f1; this.line2XYZ[1] = f6; this.line2XYZ[2] = 0.0F;
      this.line2XYZ[3] = (f1 + f3); this.line2XYZ[4] = f6; this.line2XYZ[5] = 0.0F;
      Render.drawLines(this.line2XYZ, 2, 1.0F, 0xFF000000 | i3 << 16 | i3 << 8 | i3, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE, 0);

      if (i3 == 192)
        drawGridText(0, (int)f6, (k + i1) * i);
    }
    for (int i2 = 0; i2 <= m; i2++) {
      float f7 = f1 + i2 * f5;
      int i4 = (i2 + j) % 10 == 0 ? 192 : 127;
      this.line2XYZ[0] = f7; this.line2XYZ[1] = f2; this.line2XYZ[2] = 0.0F;
      this.line2XYZ[3] = f7; this.line2XYZ[4] = (f2 + f4); this.line2XYZ[5] = 0.0F;
      Render.drawLines(this.line2XYZ, 2, 1.0F, 0xFF000000 | i4 << 16 | i4 << 8 | i4, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE, 0);

      if (i4 == 192)
        drawGridText((int)f7, 0, (j + i2) * i);
    }
    Render.drawEnd();
  }

  private void drawGridText(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt3 <= 0) || (this._gridCount == 20)) return;
    this._gridX[this._gridCount] = paramInt1;
    this._gridY[this._gridCount] = paramInt2;
    this._gridVal[this._gridCount] = paramInt3;
    this._gridCount += 1;
  }
  private void drawGridText() {
    for (int i = 0; i < this._gridCount; i++)
      this._gridFont.output(-4144960, this._gridX[i] + 2, this._gridY[i] + 2, 0.0F, this._gridVal[i] / 1000 + "." + this._gridVal[i] % 1000 / 100);
    this._gridCount = 0;
  }

  private void drawGrid3D() {
    int i = gridStep();
    int j = (int)((this.camera2D.worldXOffset + Main3D.cur3D().land2D.worldOfsX()) / i);
    int k = (int)((this.camera2D.worldYOffset + Main3D.cur3D().land2D.worldOfsY()) / i);
    int m = (int)(this.viewX / i) + 2;
    int n = (int)(this.viewY / i) + 2;
    Render.drawBeginLines(0);
    int i4;
    float f4;
    for (int i1 = 0; i1 <= n; i1++) {
      float f1 = (i1 + k) * i;
      int i3 = 64;
      i4 = 1;
      if ((i1 + k) % 2 == 0) {
        i3 = 150;
        if ((i1 + k) % 10 == 0) {
          i4 = 0;
          i3 = 240;
        }
      }
      double d1 = -1.0D;
      double d3 = -1.0D;
      if (this.lineNXYZ.length / 3 <= m) this.lineNXYZ = new float[(m + 1) * 3];
      this.lineNCounter = 0;
      for (int i6 = 0; i6 <= m; i6++) {
        float f3 = (i6 + j) * i;
        Engine.land(); f4 = Landscape.HQ(f3 - (float)Main3D.cur3D().land2D.worldOfsX(), f1 - (float)Main3D.cur3D().land2D.worldOfsY());
        this.lineNXYZ[(this.lineNCounter * 3 + 0)] = (f3 - (float)Main3D.cur3D().land2D.worldOfsX());
        this.lineNXYZ[(this.lineNCounter * 3 + 1)] = (f1 - (float)Main3D.cur3D().land2D.worldOfsY());
        this.lineNXYZ[(this.lineNCounter * 3 + 2)] = f4;
        this.lineNCounter += 1;
        if (i4 == 0) {
          project2d(f3 - (float)Main3D.cur3D().land2D.worldOfsX(), f1 - (float)Main3D.cur3D().land2D.worldOfsY(), f4, this.projectPos2d);
          if (this.projectPos2d.jdField_x_of_type_Double > 0.0D) {
            if (i6 > 0) drawGridText(0, (int)(d3 - d1 / (this.projectPos2d.jdField_x_of_type_Double - d1) * (this.projectPos2d.jdField_y_of_type_Double - d3)), (int)f1); else
              drawGridText(0, (int)this.projectPos2d.jdField_y_of_type_Double, (int)f1);
            i4 = 1;
          }
          d1 = this.projectPos2d.jdField_x_of_type_Double;
          d3 = this.projectPos2d.jdField_y_of_type_Double;
        }
      }
      Render.drawLines(this.lineNXYZ, this.lineNCounter, 1.0F, 0xFF000000 | i3 << 16 | i3 << 8 | i3, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE, 2);
    }

    for (int i2 = 0; i2 <= m; i2++) {
      float f2 = (i2 + j) * i;
      i4 = 64;
      int i5 = 1;
      if ((i2 + j) % 2 == 0) {
        i4 = 150;
        if ((i2 + j) % 10 == 0) {
          i5 = 0;
          i4 = 240;
        }
      }
      double d2 = -1.0D;
      double d4 = -1.0D;
      if (this.lineNXYZ.length / 3 <= n) this.lineNXYZ = new float[(n + 1) * 3];
      this.lineNCounter = 0;
      for (int i7 = 0; i7 <= n; i7++) {
        f4 = (i7 + k) * i;
        Engine.land(); float f5 = Landscape.HQ(f2 - (float)Main3D.cur3D().land2D.worldOfsX(), f4 - (float)Main3D.cur3D().land2D.worldOfsY());
        this.lineNXYZ[(this.lineNCounter * 3 + 0)] = (f2 - (float)Main3D.cur3D().land2D.worldOfsX());
        this.lineNXYZ[(this.lineNCounter * 3 + 1)] = (f4 - (float)Main3D.cur3D().land2D.worldOfsY());
        this.lineNXYZ[(this.lineNCounter * 3 + 2)] = f5;
        this.lineNCounter += 1;
        if (i5 == 0) {
          project2d(f2 - (float)Main3D.cur3D().land2D.worldOfsX(), f4 - (float)Main3D.cur3D().land2D.worldOfsY(), f5, this.projectPos2d);
          if (this.projectPos2d.jdField_y_of_type_Double > 0.0D) {
            if (i7 > 0) drawGridText((int)(d2 - d4 / (this.projectPos2d.jdField_y_of_type_Double - d4) * (this.projectPos2d.jdField_x_of_type_Double - d2)), 0, (int)f2); else
              drawGridText((int)this.projectPos2d.jdField_x_of_type_Double, 0, (int)f2);
            i5 = 1;
          }
          d2 = this.projectPos2d.jdField_x_of_type_Double;
          d4 = this.projectPos2d.jdField_y_of_type_Double;
        }
      }
      Render.drawLines(this.lineNXYZ, this.lineNCounter, 1.0F, 0xFF000000 | i4 << 16 | i4 << 8 | i4, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE, 2);
    }

    Render.drawEnd();
  }

  public Actor getOverActor()
  {
    return this.overActor; } 
  public void setOverActor(Actor paramActor) { this.overActor = paramActor; } 
  private void drawInfoOverActor() {
    if (!Actor.isValid(this.overActor)) return;
    Plugin localPlugin = (Plugin)Property.value((this.overActor instanceof PPoint) ? this.overActor.getOwner() : this.overActor, "builderPlugin");
    if (localPlugin == null) return;
    String[] arrayOfString = localPlugin.actorInfo(this.overActor);
    if (arrayOfString == null) return;
    Point3d localPoint3d = this.overActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    if (!project2d(localPoint3d, this.projectPos2d)) return;
    float f1 = 0.0F;
    int i = 0;
    for (int j = 0; j < arrayOfString.length; j++) {
      String str1 = arrayOfString[j];
      if (str1 == null) break;
      f3 = this.smallFont.width(str1);
      if (f3 > f1) f1 = f3;
      i++;
    }
    if (f1 == 0.0F) return;
    float f2 = -this.smallFont.descender();
    float f3 = this.smallFont.height();
    float f4 = i * (f3 + f2) + f2;
    int k = Army.color((this.overActor instanceof PPoint) ? this.overActor.getOwner().getArmy() : this.overActor.getArmy());
    Render.drawTile((float)this.projectPos2d.jdField_x_of_type_Double, (float)(this.projectPos2d.jdField_y_of_type_Double + this.conf.iconSize / 2 + f2), f1 + 2.0F * f2, f4, 0.0F, this.emptyMat, k & 0x7FFFFFFF, 0.0F, 0.0F, 1.0F, 1.0F);

    Render.drawEnd();
    for (int m = 0; m < arrayOfString.length; m++) {
      String str2 = arrayOfString[m];
      if (str2 == null) break;
      this.smallFont.output(0xFF000000 | k ^ 0xFFFFFFFF, (float)(this.projectPos2d.jdField_x_of_type_Double + f2), (float)(this.projectPos2d.jdField_y_of_type_Double + this.conf.iconSize / 2 + f2 + (i - m - 1) * (f2 + f3) + f2), 0.0F, str2);
    }
  }

  private void drawSelectTarget()
  {
    if (this.mouseState != 4) return;
    if (!this.viewWindow.isMouseOver()) return;
    PAir localPAir = (PAir)selectedPoint();
    Point3d localPoint3d;
    if (Actor.isValid(localPAir)) {
      localPoint3d = localPAir.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    } else {
      Actor localActor = selectedActor();
      if (!(localActor instanceof PlMisRocket.Rocket))
        return;
      localPoint3d = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
    }
    project2d(localPoint3d, this.projectPos2d);
    this.lineNXYZ[0] = (float)this.projectPos2d.jdField_x_of_type_Double;
    this.lineNXYZ[1] = (float)this.projectPos2d.jdField_y_of_type_Double;
    this.lineNXYZ[2] = 0.0F;
    this.lineNXYZ[3] = this.mousePosX;
    this.lineNXYZ[4] = this.mousePosY;
    this.lineNXYZ[5] = 0.0F;
    Render.drawBeginLines(-1);
    Render.drawLines(this.lineNXYZ, 2, 1.0F, -16711936, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE, 2);
    Render.drawEnd();
    float f = this.conf.iconSize * 2;
    Render.drawTile(this.mousePosX - f / 2.0F, this.mousePosY - f / 2.0F, f, f, 0.0F, this.selectTargetMat, -1, 0.0F, 0.0F, 1.0F, 1.0F);
  }
  public void beginSelectTarget() {
    Path localPath = selectedPath();
    if ((localPath == null) || (!(localPath instanceof PathAir))) {
      Actor localActor = selectedActor();
      if (!(localActor instanceof PlMisRocket.Rocket)) {
        return;
      }

    }

    this.mouseState = 4;
    this.viewWindow.jdField_mouseCursor_of_type_Int = 0;
  }
  public void endSelectTarget() {
    if (this.mouseState != 4) return;
    this.mouseState = 0;
    this.viewWindow.jdField_mouseCursor_of_type_Int = 1;
    Path localPath = selectedPath();
    Object localObject1;
    Object localObject2;
    if ((localPath == null) || (!(localPath instanceof PathAir))) {
      localObject1 = (PlMisRocket.Rocket)selectedActor();
      localObject2 = mouseWorldPos();
      ((PlMisRocket.Rocket)localObject1).target = new Point2d(((Point3d)localObject2).jdField_x_of_type_Double, ((Point3d)localObject2).jdField_y_of_type_Double);
    } else {
      localObject1 = getOverActor();
      if (!Actor.isValid((Actor)localObject1)) return;
      if (((localObject1 instanceof PPoint)) && 
        (((Actor)localObject1).getOwner() == selectedPath())) return;

      localObject2 = (PAir)selectedPoint();
      ((PAir)localObject2).setTarget((Actor)localObject1);
      Plugin localPlugin = (Plugin)Property.value(((PAir)localObject2).getOwner(), "builderPlugin");
      localPlugin.updateSelector();
    }
    PlMission.setChanged();
  }
  public void breakSelectTarget() {
    if (this.mouseState != 4) return;
    this.mouseState = 0;
    this.viewWindow.jdField_mouseCursor_of_type_Int = 1;
  }

  private void initHotKeys()
  {
    HotKeyCmdEnv.setCurrentEnv(envName);
    HotKeyEnv.fromIni(envName, Config.cur.ini, "HotKey " + envName);

    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "toLand") {
      public void begin() { if (Actor.isValid(Builder.this.selectedActor())) {
          Builder.this.align(Builder.this.selectedActor());
          PlMission.setChanged();
          if (!Builder.this.isFreeView())
            Builder.this.repaint();
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "normalLand") {
      public void begin() { if (Actor.isValid(Builder.this.selectedActor())) {
          Point3d localPoint3d = new Point3d();
          Orient localOrient = new Orient();
          Builder.this.selectedActor().pos.getAbs(localPoint3d, localOrient);
          Vector3f localVector3f = new Vector3f();
          Engine.land().N(localPoint3d.x, localPoint3d.y, localVector3f);
          localOrient.orient(localVector3f);
          Builder.this.selectedActor().pos.setAbs(localOrient);
          Builder.defaultAzimut = localOrient.azimut();
          PlMission.setChanged();
          if (!Builder.this.isFreeView())
            Builder.this.repaint();
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "land") {
      public void begin() { Builder.this.changeViewLand();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "stepAzimut-5") {
      public void begin() { Builder.this.stepAzimut(-5);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "stepAzimut-15") {
      public void begin() { Builder.this.stepAzimut(-15);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "stepAzimut-30") {
      public void begin() { Builder.this.stepAzimut(-30);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "stepAzimut5") {
      public void begin() { Builder.this.stepAzimut(5);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "stepAzimut15") {
      public void begin() { Builder.this.stepAzimut(15);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "stepAzimut30") {
      public void begin() { Builder.this.stepAzimut(30);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "resetAngles") {
      public void begin() { if (!Actor.isValid(Builder.this.selectedActor())) return;
        Orient localOrient = new Orient();
        localOrient.set(0.0F, 0.0F, 0.0F);
        Builder.defaultAzimut = 0.0F;
        Builder.this.selectedActor().pos.setAbs(localOrient);
        PlMission.setChanged();
        if (!Builder.this.isFreeView())
          Builder.this.repaint();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "resetTangage90") {
      public void begin() { if (!Actor.isValid(Builder.this.selectedActor())) return;
        Orient localOrient = new Orient();
        localOrient.set(0.0F, 90.0F, 0.0F);
        Builder.defaultAzimut = 0.0F;
        Builder.this.selectedActor().pos.setAbs(localOrient);
        PlMission.setChanged();
        if (!Builder.this.isFreeView())
          Builder.this.repaint();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "change+") {
      public void begin() { Builder.this.changeType(false, false);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "change++") {
      public void begin() { Builder.this.changeType(false, true);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "change-") {
      public void begin() { Builder.this.changeType(true, false);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "change--") {
      public void begin() { Builder.this.changeType(true, true);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "insert") {
      public void begin() { Builder.this.insert(false);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "insert+") {
      public void begin() { Builder.this.insert(true);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "delete") {
      public void begin() { Builder.this.delete(false);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "delete+") {
      public void begin() { Builder.this.delete(true);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "fill") {
      public void begin() {
        if (!Builder.this.isLoadedLandscape()) return;
        if (Builder.this.isFreeView()) return;
        if ((Builder.this.bMultiSelect) && (!Builder.this.bView3D)) return;
        if (Builder.this.mouseState != 0) return;
        Builder.this.mouseState = 5;
        Point3d localPoint3d = Builder.this.posScreenToLand(Builder.this.mousePosX, Builder.this.mousePosY, 0.0D, 0.1D);
        Plugin.doBeginFill(localPoint3d);
      }
      public void end() {
        if (Builder.this.mouseState != 5) return;
        Builder.this.mouseState = 0;
        Point3d localPoint3d = Builder.this.posScreenToLand(Builder.this.mousePosX, Builder.this.mousePosY, 0.0D, 0.1D);
        Plugin.doEndFill(localPoint3d);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "cursor") {
      public void begin() { if (!Builder.this.isLoadedLandscape()) return;
        if (!Builder.this.isFreeView()) return;
        Object localObject;
        if (Builder.this.actorView() == Builder.this.cursor) {
          localObject = Builder.this.selectNear(Builder.this.actorView().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
          if (localObject != null) {
            Builder.this.cursor.drawing(false);
            Builder.this.setSelected((Actor)localObject);
          }
        } else if (Actor.isValid(Builder.this.actorView())) {
          localObject = Builder.this.actorView().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs();
          Builder.this.cursor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs((Loc)localObject);
          Builder.this.cursor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
          Builder.this.cursor.drawing(true);
          Builder.this.setSelected(Builder.this.cursor);
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "objectMove") {
      public void begin() {
        if (!Builder.this.isLoadedLandscape()) return;
        if (Builder.this.isFreeView()) return;
        if (Builder.this.mouseState == 4) {
          Builder.this.endSelectTarget();
          return;
        }
        if (Builder.this.mouseState != 0) return;
        Builder.this.mouseState = 1;
        Builder.this.viewWindow.jdField_mouseCursor_of_type_Int = 7;
        Actor localActor = Builder.this.selectNear(Builder.this.mousePosX, Builder.this.mousePosY);
        if (localActor != null) {
          Builder.this.setSelected(localActor);
          Builder.this.repaint();
        }
        Builder.this.setOverActor(null);
      }
      public void end() {
        if (!Builder.this.isLoadedLandscape()) return;
        if (Builder.this.isFreeView()) return;
        if (Builder.this.mouseState != 1) return;
        Builder.this.mouseState = 0;
        Builder.this.viewWindow.jdField_mouseCursor_of_type_Int = 1;
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "worldZoom") {
      public void begin() {
        if (!Builder.this.isLoadedLandscape()) return;
        if (Builder.this.isFreeView()) return;
        if (Builder.this.mouseState != 0) return;
        Builder.this.mouseState = 2;
        Builder.this.mouseFirstPosX = Builder.this.mousePosX;
        Builder.this.mouseFirstPosY = Builder.this.mousePosY;
        Builder.this.bMouseRenderRect = true;
        Builder.this.viewWindow.jdField_mouseCursor_of_type_Int = 2;
        Builder.this.setOverActor(null);
      }
      public void end() {
        if (!Builder.this.isLoadedLandscape()) return;
        if (Builder.this.isFreeView()) return;
        if (Builder.this.mouseState != 2) return;
        Builder.this.mouseState = 0;
        Builder.this.bMouseRenderRect = false;
        if (Builder.this.mouseFirstPosX == Builder.this.mousePosX) {
          Builder.this.repaint();
          return;
        }
        Point3d localPoint3d = Builder.this.posScreenToLand(Builder.this.mouseFirstPosX, Builder.this.mouseFirstPosY, 0.0D, 0.1D);
        double d1 = localPoint3d.jdField_x_of_type_Double;
        double d2 = localPoint3d.jdField_y_of_type_Double;
        localPoint3d = Builder.this.posScreenToLand(Builder.this.mousePosX, Builder.this.mousePosY, 0.0D, 0.1D);
        double d3 = Builder.this.camera.FOV() * 3.141592653589793D / 180.0D / 2.0D;
        double d4 = localPoint3d.jdField_x_of_type_Double - d1; if (d4 < 0.0D) d4 = -d4;
        double d5 = d4 / 2.0D / Math.tan(d3);
        Builder.this.computeViewMap2D(d5, (d1 + localPoint3d.jdField_x_of_type_Double) / 2.0D, (d2 + localPoint3d.jdField_y_of_type_Double) / 2.0D);
        Builder.this.viewWindow.jdField_mouseCursor_of_type_Int = 1;
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "unselect") {
      public void begin() {
        if (!Builder.this.isLoadedLandscape()) return;
        if (Builder.this.isFreeView()) return;
        if (Builder.this.mouseState != 0) return;
        Builder.this.setSelected(null);
        Builder.this.repaint();
      }
    });
    if (this.bMultiSelect) {
      HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "select+") {
        public void begin() {
          if (!Builder.this.isLoadedLandscape()) return;
          if (Builder.this.isFreeView()) return;
          if (Builder.this.mouseState != 0) return;
          Builder.this.mouseState = 3;
          Builder.this.mouseFirstPosX = Builder.this.mousePosX;
          Builder.this.mouseFirstPosY = Builder.this.mousePosY;
          Builder.this.bMouseRenderRect = true;
        }
        public void end() {
          if (!Builder.this.isLoadedLandscape()) return;
          if (Builder.this.isFreeView()) return;
          if (Builder.this.mouseState != 3) return;
          Builder.this.mouseState = 0;
          Builder.this.bMouseRenderRect = false;
          if (Builder.this.mouseFirstPosX != Builder.this.mousePosX) {
            Point3d localPoint3d = Builder.this.posScreenToLand(Builder.this.mouseFirstPosX, Builder.this.mouseFirstPosY, 0.0D, 0.1D);
            double d1 = localPoint3d.jdField_x_of_type_Double;
            double d2 = localPoint3d.jdField_y_of_type_Double;
            localPoint3d = Builder.this.posScreenToLand(Builder.this.mousePosX, Builder.this.mousePosY, 0.0D, 0.1D);
            Builder.this.select(d1, d2, localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double, true);
          }
          Builder.this.repaint();
        }
      });
      HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "select-") {
        public void begin() {
          if (!Builder.this.isLoadedLandscape()) return;
          if (Builder.this.isFreeView()) return;
          if (Builder.this.mouseState != 0) return;
          Builder.this.mouseState = 3;
          Builder.this.mouseFirstPosX = Builder.this.mousePosX;
          Builder.this.mouseFirstPosY = Builder.this.mousePosY;
          Builder.this.bMouseRenderRect = true;
        }
        public void end() {
          if (!Builder.this.isLoadedLandscape()) return;
          if (Builder.this.isFreeView()) return;
          if (Builder.this.mouseState != 3) return;
          Builder.this.mouseState = 0;
          Builder.this.bMouseRenderRect = false;
          if (Builder.this.mouseFirstPosX != Builder.this.mousePosX) {
            Point3d localPoint3d = Builder.this.posScreenToLand(Builder.this.mouseFirstPosX, Builder.this.mouseFirstPosY, 0.0D, 0.1D);
            double d1 = localPoint3d.jdField_x_of_type_Double;
            double d2 = localPoint3d.jdField_y_of_type_Double;
            localPoint3d = Builder.this.posScreenToLand(Builder.this.mousePosX, Builder.this.mousePosY, 0.0D, 0.1D);
            Builder.this.select(d1, d2, localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double, false);
          }
          Builder.this.repaint();
        } } );
    } else {
      HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "beginSelectTarget") {
        public void end() {
          if (!Builder.this.isLoadedLandscape()) return;
          if (Builder.this.mouseState != 0) return;
          if (Builder.this.isFreeView()) return;
          Builder.this.beginSelectTarget();
        }
      });
    }
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "freeView") {
      public void end() {
        if (!Builder.this.isLoadedLandscape()) return;
        if (Builder.this.mouseState != 0) return;
        if (Builder.this.isFreeView()) Builder.this.clearActorView();
        else if (Builder.this.bView3D) Builder.this.setActorView();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "break") {
      public void end() {
        if (!Builder.this.isLoadedLandscape()) return;
        if (Builder.this.isFreeView()) {
          Builder.this.clearActorView();
        } else {
          Builder.this.setOverActor(null);
          Builder.this.breakSelectTarget();
          Builder.this.mouseState = 0;
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "popupmenu") {
      public void begin() { if (!Builder.this.isLoadedLandscape()) return;
        if (Builder.this.mouseState != 0) return;
        if (Builder.this.isFreeView()) return;
        Builder.this.doPopUpMenu(); }
    });
  }

  public void doPopUpMenu()
  {
    if (this.mousePosX == -1) return;
    if (this.popUpMenu == null) {
      this.popUpMenu = ((GWindowMenuPopUp)this.viewWindow.create(new GWindowMenuPopUp()));
    } else {
      if (this.popUpMenu.isVisible())
        return;
      this.popUpMenu.clearItems();
    }
    Point3d localPoint3d = posScreenToLand(this.mousePosX, this.mousePosY, 0.0D, 0.1D);
    float f = this.viewWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.mousePosY - 1.0F;

    if ((Actor.isValid(selectedPoint())) || (Actor.isValid(selectedActor()))) {
      this.popUpMenu.addItem(new GWindowMenuItem(this.popUpMenu, Plugin.i18n("&Unselect"), Plugin.i18n("TIPUnselect")) {
        public void execute() {
          Builder.this.setSelected(null);
        }
      });
      this.popUpMenu.addItem(new GWindowMenuItem(this.popUpMenu, Plugin.i18n("&Delete"), Plugin.i18n("TIPDelete")) {
        public void execute() {
          Builder.this.delete(true);
        }
      });
    }
    Plugin.doFillPopUpMenu(this.popUpMenu, localPoint3d);
    if (this.popUpMenu.size() > 0) {
      this.popUpMenu.setPos(this.mousePosX, f);
      this.popUpMenu.showModal();
      this.mousePosX = (this.mousePosY = -1);
      this.movedActor = null;
    } else {
      this.popUpMenu.hideWindow();
    }
  }

  public void setViewLand() {
    Main3D.cur3D().setDrawLand(this.conf.bShowLandscape);
    if (Main3D.cur3D().land2D != null)
      Main3D.cur3D().land2D.show(isView3D() ? false : this.conf.bShowLandscape); 
  }

  public void changeViewLand() {
    this.conf.bShowLandscape = (!this.conf.bShowLandscape);
    Main3D.cur3D().setDrawLand(this.conf.bShowLandscape);
    if (Main3D.cur3D().land2D != null)
      Main3D.cur3D().land2D.show(isView3D() ? false : this.conf.bShowLandscape);
    this.wViewLand.wShow.setChecked(this.conf.bShowLandscape, false);
    if (!isFreeView())
      repaint();
  }

  public void changeType(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (!isLoadedLandscape()) return;
    if (this.mouseState != 0) return;
    if ((!Actor.isValid(selectedActor())) || (selectedActor() == this.cursor)) return;
    Plugin.doChangeType(paramBoolean1, paramBoolean2);
    if (!isFreeView())
      repaint();
  }

  private void insert(boolean paramBoolean) {
    if (!isLoadedLandscape()) return;
    if (this.mouseState != 0) return;
    Loc localLoc = new Loc();
    if (isFreeView()) {
      if (!Actor.isValid(actorView()))
        return;
      actorView().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(localLoc);
      if (actorView() != this.cursor)
        localLoc.add(new Point3d(1.0D, 1.0D, 0.0D));
    } else {
      localLoc.set(posScreenToLand(this.mousePosX, this.mousePosY, 0.0D, 0.1D), new Orient(defaultAzimut, 0.0F, 0.0F));
    }
    Plugin.doInsert(localLoc, paramBoolean);
    if (!isFreeView())
      repaint();
  }

  private void delete(boolean paramBoolean)
  {
    if (!isLoadedLandscape()) return;
    if (this.mouseState != 0) return;
    Object localObject1;
    Object localObject2;
    if (isFreeView()) {
      if ((!Actor.isValid(selectedActor())) || (selectedActor() == this.cursor) || ((selectedActor() instanceof Bridge)))
      {
        return;
      }localObject1 = selectedActor().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs();
      selectedActor().destroy();
      Plugin.doAfterDelete();
      PlMission.setChanged();
      localObject2 = null;
      if (paramBoolean) {
        localObject2 = selectNear(((Loc)localObject1).getPoint());
      }
      if (localObject2 == null) {
        localObject2 = this.cursor;
        this.cursor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs((Loc)localObject1); this.cursor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
        this.cursor.drawing(true);
      }
      setSelected((Actor)localObject2);
    } else {
      if (Actor.isValid(selectedPoint())) {
        localObject1 = selectedPath();
        try {
          localObject2 = ((Path)localObject1).selectPrev(this.pathes.currentPPoint);
          this.pathes.currentPPoint.destroy();
          Plugin.doAfterDelete();
          PlMission.setChanged();
          if (localObject2 == null) {
            ((Path)localObject1).destroy();
            Plugin.doAfterDelete();
            setSelected(null);
          } else {
            setSelected(paramBoolean ? localObject2 : null);
          }
        } catch (Exception localException) {
        }
      } else {
        int i = 0;
        Object localObject3;
        if ((Actor.isValid(selectedActor())) && (!this.selectedActors.containsKey(selectedActor()))) {
          if ((this.bMultiSelect) && ((selectedActor() instanceof Bridge))) {
            if (this.deletingMessageBox == null) {
              this.deletingActor = selectedActor();
              this.bDeletingChangeSelection = paramBoolean;
              this.deletingMessageBox = new GWindowMessageBox(this.clientWindow, 20.0F, true, "Confirm DELETE", "Delete Bridge ?", 1, 0.0F)
              {
                public void result(int paramInt)
                {
                  if (paramInt == 3) {
                    if (Builder.this.deletingActor == Builder.this.selectedActor())
                      Builder.this.delete(Builder.this.bDeletingChangeSelection);
                  } else {
                    Builder.access$1902(Builder.this, null);
                    Builder.access$1702(Builder.this, null);
                    Builder.this.setSelected(null);
                  }
                }
              };
              return;
            }
            this.deletingMessageBox = null;
            PlMapLoad.bridgeActors.remove(selectedActor());
            selectedActor().destroy();
            this.deletingActor = null;
          }
          else {
            localObject3 = (Plugin)Property.value(selectedActor(), "builderPlugin");
            if (localObject3 != null)
              ((Plugin)localObject3).delete(selectedActor());
          }
          i = 1;
        } else {
          localObject3 = selectedActors();
          for (int j = 0; j < localObject3.length; j++) {
            Actor localActor = localObject3[j];
            if (localActor == null) break;
            if (Actor.isValid(localActor)) {
              Plugin localPlugin = (Plugin)Property.value(localActor, "builderPlugin");
              localPlugin.delete(localActor);
              i = 1;
            }
          }
        }
        if (i != 0) {
          Plugin.doAfterDelete();
          PlMission.setChanged();
          selectedActorsValidate();
          if (paramBoolean)
            setSelected(selectNearFull(this.mousePosX, this.mousePosY));
          else
            setSelected(null);
        }
        else {
          localObject3 = new Loc();
          ((Loc)localObject3).set(posScreenToLand(this.mousePosX, this.mousePosY, 0.0D, 0.1D));
          Plugin.doDelete((Loc)localObject3);
        }
      }
      repaint();
    }
  }

  private void stepAzimut(int paramInt) {
    if (!isLoadedLandscape()) return;
    Object localObject;
    if (Actor.isValid(selectedActor())) {
      if ((selectedActor() instanceof Bridge)) return;
      localObject = new Orient();
      selectedActor().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs((Orient)localObject);
      ((Orient)localObject).wrap();
      ((Orient)localObject).set(((Orient)localObject).azimut() + paramInt, ((Orient)localObject).tangage(), ((Orient)localObject).kren());
      selectedActor().jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs((Orient)localObject);
      defaultAzimut = ((Orient)localObject).azimut();
      align(selectedActor());
      PlMission.setChanged();
      if (!isFreeView())
        repaint();
    } else if ((this.bMultiSelect) && (!isFreeView()) && (countSelectedActors() > 0)) {
      localObject = posScreenToLand(this.mousePosX, this.mousePosY, 0.0D, 0.1D);
      Point3d localPoint3d1 = new Point3d();
      Orient localOrient = new Orient();
      Actor[] arrayOfActor = selectedActors();
      double d1 = Math.sin(paramInt * 3.141592653589793D / 180.0D);
      double d2 = Math.cos(paramInt * 3.141592653589793D / 180.0D);
      for (int i = 0; i < arrayOfActor.length; i++) {
        Actor localActor = arrayOfActor[i];
        if (localActor == null) break;
        if (Actor.isValid(localActor)) {
          Point3d localPoint3d2 = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
          localPoint3d1.jdField_x_of_type_Double = (localPoint3d2.jdField_x_of_type_Double - ((Point3d)localObject).jdField_x_of_type_Double);
          localPoint3d1.jdField_y_of_type_Double = (localPoint3d2.jdField_y_of_type_Double - ((Point3d)localObject).jdField_y_of_type_Double);
          if (localPoint3d1.jdField_x_of_type_Double * localPoint3d1.jdField_x_of_type_Double + localPoint3d1.jdField_y_of_type_Double * localPoint3d1.jdField_y_of_type_Double > 1.0E-006D) {
            double d3 = localPoint3d1.jdField_x_of_type_Double * d2 + localPoint3d1.jdField_y_of_type_Double * d1;
            double d4 = localPoint3d1.jdField_y_of_type_Double * d2 - localPoint3d1.jdField_x_of_type_Double * d1;
            localPoint3d1.jdField_x_of_type_Double = (d3 + ((Point3d)localObject).jdField_x_of_type_Double);
            localPoint3d1.jdField_y_of_type_Double = (d4 + ((Point3d)localObject).jdField_y_of_type_Double);
          }
          localPoint3d1.jdField_z_of_type_Double = localPoint3d2.jdField_z_of_type_Double;
          if (this.bRotateObjects) {
            localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(localOrient);
            localOrient.wrap();
            localOrient.set(localOrient.azimut() + paramInt, localOrient.tangage(), localOrient.kren());
            localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localPoint3d1, localOrient);
          } else {
            localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localPoint3d1);
          }
          align(localActor);
        }
      }
      repaint();
    }
  }

  public void tipErr(String paramString)
  {
    System.err.println(paramString);
    this.clientWindow.toolTip(paramString);
  }
  public void tip(String paramString) {
    this.clientWindow.toolTip(paramString);
  }

  protected void doMenu_FileExit()
  {
    if (Plugin.doExitBuilder())
      Main.stateStack().pop();
  }

  public void repaint() {
  }

  public void enterRenders() {
    this.bView3D = false;
    Main3D.cur3D().renderMap2D.setShow(true);
    Main3D.cur3D().renderMap2D.useClearColor(true);
    Main3D.cur3D().render3D0.setShow(false);
    Main3D.cur3D().render3D1.setShow(false);
    Main3D.cur3D().render2D.setShow(false);
    _viewPort[2] = (int)this.viewWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
    _viewPort[3] = (int)this.viewWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
    GPoint localGPoint = this.viewWindow.windowToGlobal(0.0F, 0.0F);
    _viewPort[0] = ((int)localGPoint.x + ((GUIWindowManager)this.viewWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.manager).render.getViewPortX0());
    _viewPort[1] = ((int)(this.viewWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGPoint.y - this.viewWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy) + ((GUIWindowManager)this.viewWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.manager).render.getViewPortY0());

    Main3D.cur3D().renderMap2D.setViewPort(_viewPort);
    Main3D.cur3D().render3D0.setViewPort(_viewPort);
    Main3D.cur3D().render3D1.setViewPort(_viewPort);
    this.camera2D.set(0.0F, _viewPort[2], 0.0F, _viewPort[3]);

    Render localRender = (Render)Actor.getByName("renderConsoleGL0");
    CameraOrtho2D localCameraOrtho2D;
    if (localRender != null) {
      localCameraOrtho2D = (CameraOrtho2D)localRender.getCamera();
      localRender.setViewPort(_viewPort);
      localCameraOrtho2D.set(0.0F, _viewPort[2], 0.0F, _viewPort[3]);
    }

    localRender = (Render)Actor.getByName("renderTextScr");
    if (localRender != null) {
      localCameraOrtho2D = (CameraOrtho2D)localRender.getCamera();
      localRender.setViewPort(_viewPort);
      localCameraOrtho2D.set(0.0F, _viewPort[2], 0.0F, _viewPort[3]);
    }
  }

  public void leaveRenders()
  {
    Main3D.cur3D().renderMap2D.setShow(false);
    Main3D.cur3D().renderMap2D.useClearColor(false);
    Main3D.cur3D().render3D0.setShow(true);
    Main3D.cur3D().render3D1.setShow(true);
    Main3D.cur3D().render2D.setShow(true);

    leaveRender(Main3D.cur3D().renderMap2D);
    leaveRender(Main3D.cur3D().render3D0);
    leaveRender(Main3D.cur3D().render3D1);
    leaveRender(Main3D.cur3D().render2D);
    leaveRender((Render)Actor.getByName("renderConsoleGL0"));
    leaveRender((Render)Actor.getByName("renderTextScr"));
  }
  private void leaveRender(Render paramRender) {
    if (paramRender == null) return;
    paramRender.contextResized();
  }

  public void mapLoaded() {
    enterRenders();
    if (!isLoadedLandscape()) {
      this.bView3D = false;
      Main3D.cur3D().renderMap2D.setShow(true);
      Main3D.cur3D().render3D0.setShow(false);
      Main3D.cur3D().render3D1.setShow(false);
      Main3D.cur3D().render2D.setShow(false);
      this.mapXscrollBar.setRange(0.0F, 0.0F, 1.0F, 1.0F, 0.0F);
      this.mapYscrollBar.setRange(0.0F, 0.0F, 1.0F, 1.0F, 0.0F);
      this.mapZSlider.setRange(0, 2, 0);
    } else {
      computeViewMap2D(-1.0D, 0.0D, 0.0D);
      if (Main3D.cur3D().land2D != null)
        Main3D.cur3D().land2D.show(this.conf.bShowLandscape); 
    }
  }

  public void enter() {
    Main3D.cur3D().resetGame();
    saveMaxVisualDistance = World.MaxVisualDistance;
    saveMaxStaticVisualDistance = World.MaxStaticVisualDistance;
    World.MaxVisualDistance = MaxVisualDistance;
    World.MaxStaticVisualDistance = MaxVisualDistance;
    enterRenders();
    setViewLand();
    Main3D.cur3D().camera3D.dreamFire(true);
    Main3D.cur3D().hookView.use(true);
    Main3D.cur3D().hookView.reset();
    Main3D.cur3D().bEnableFog = false;
    com.maddox.il2.objects.air.Runaway.bDrawing = this.bMultiSelect;
    this.camera.interpPut(new InterpolateOnLand(), "onLand", Time.currentReal(), null);

    this.viewWindow.jdField_mouseCursor_of_type_Int = 1;
    this.pathes = new Pathes();
    PlMission.doMissionReload();
    this.cursor = new CursorMesh("3do/primitive/coord/mono.sim");
  }
  public void leave() {
    this.camera.interpEnd("onLand");
    PlMapLoad localPlMapLoad = (PlMapLoad)Plugin.getPlugin("MapLoad");
    localPlMapLoad.mapUnload();

    this.mouseState = 0;
    setSelected(null);

    if (this.wSelect.isVisible()) this.wSelect.hideWindow();
    if (this.wViewLand.isVisible()) this.wViewLand.hideWindow();
    if ((this.bMultiSelect) && 
      (this.wSnap.isVisible())) this.wSnap.hideWindow();
    Plugin.doFreeResources();

    leaveRenders();
    Main3D.cur3D().bEnableFog = true;
    Main3D.cur3D().camera3D.dreamFire(false);
    com.maddox.il2.objects.air.Runaway.bDrawing = false;
    this.pathes.destroy();
    this.pathes = null;
    this.cursor.destroy();
    this.cursor = null;
    this.mouseXYZATK.resetGame();
    Main3D.cur3D().resetGame();
    this.conf.save();
    World.MaxVisualDistance = saveMaxVisualDistance;
    World.MaxStaticVisualDistance = saveMaxVisualDistance;
  }

  public Builder(GWindowRootMenu paramGWindowRootMenu, String paramString) {
    envName = paramString;
    ((GUIWindowManager)paramGWindowRootMenu.manager).setUseGMeshs(true);

    this.camera = ((Camera3D)Actor.getByName("camera"));
    this.camera2D = ((CameraOrtho2D)Actor.getByName("cameraMap2D"));
    this.mouseXYZATK = new MouseXYZATK("MouseXYZ");
    this.mouseXYZATK.setCamera(this.camera);

    this.conf = new BldConfig();
    this.conf.load(new SectFile("bldconf.ini", 1), "builder config");

    Plugin.loadAll(new SectFile("bldconf.ini", 0), PLUGINS_SECTION, this);

    paramGWindowRootMenu.clientWindow = paramGWindowRootMenu.create(this.clientWindow = new ClientWindow());
    this.mapXscrollBar = new XScrollBar(this.clientWindow);
    this.mapYscrollBar = new YScrollBar(this.clientWindow);
    this.mapZSlider = new ZSlider(this.clientWindow);
    this.mapXscrollBar.setRange(0.0F, 0.0F, 1.0F, 1.0F, 0.0F);
    this.mapYscrollBar.setRange(0.0F, 0.0F, 1.0F, 1.0F, 0.0F);
    this.mapZSlider.setRange(0, 2, 0);
    this.mapZSlider.bSlidingNotify = true;
    this.clientWindow.create(this.viewWindow = new ViewWindow());
    this.clientWindow.resized();

    paramGWindowRootMenu.statusBar.defaultHelp = null;
    this.mFile = paramGWindowRootMenu.menuBar.addItem(Plugin.i18n("&File"), Plugin.i18n("Load/SaveMissionFiles"));
    this.mFile.subMenu = ((GWindowMenu)this.mFile.create(new GWindowMenu()));
    this.mFile.subMenu.close(false);

    this.mFile.subMenu.addItem("-", null);
    this.mFile.subMenu.addItem(new GWindowMenuItem(this.mFile.subMenu, Plugin.i18n("&Exit"), Plugin.i18n("ExitBuilder")) {
      public void execute() { Builder.this.doMenu_FileExit();
      }
    });
    this.mEdit = paramGWindowRootMenu.menuBar.addItem(Plugin.i18n("&Edit"), Plugin.i18n("TIPEdit"));
    this.mEdit.subMenu = ((GWindowMenu)this.mEdit.create(new GWindowMenu()));
    this.mEdit.subMenu.close(false);
    if (this.bMultiSelect) {
      this.mEdit.subMenu.addItem(new GWindowMenuItem(this.mEdit.subMenu, "&Select All", null) {
        public void execute() {
          Plugin.doSelectAll();
        }
      });
      this.mEdit.subMenu.addItem(new GWindowMenuItem(this.mEdit.subMenu, "&Unselect All", null) {
        public void execute() {
          Builder.this.setSelected(null);
          Builder.this.selectedActors.clear();
        }
      });
      localGWindowMenuItem = this.mEdit.subMenu.addItem(new GWindowMenuItem(this.mEdit.subMenu, "&Enable Select", null) {
        public void execute() {
          Builder.this.setSelected(null);
          Builder.this.selectedActors.clear();
          Builder.this.conf.bEnableSelect = (!Builder.this.conf.bEnableSelect);
          this.bChecked = Builder.this.conf.bEnableSelect;
        }
      });
      localGWindowMenuItem.bChecked = this.conf.bEnableSelect;
      this.mEdit.subMenu.addItem("-", null);
    }
    this.mEdit.subMenu.addItem(new GWindowMenuItem(this.mEdit.subMenu, Plugin.i18n("&DeleteAll"), Plugin.i18n("TIPDeleteAll")) {
      public void execute() { Builder.this.deleteAll();
      }
    });
    if (this.bMultiSelect) {
      this.mEdit.subMenu.addItem("-", null);
      localGWindowMenuItem = this.mEdit.subMenu.addItem(new GWindowMenuItem(this.mEdit.subMenu, "&Rotate Objects", null) {
        public void execute() {
          Builder.access$2402(Builder.this, !Builder.this.bRotateObjects);
          this.bChecked = Builder.this.bRotateObjects;
        }
      });
      localGWindowMenuItem.bChecked = this.bRotateObjects;
    }

    this.mView = paramGWindowRootMenu.menuBar.addItem(Plugin.i18n("&View"), Plugin.i18n("TIPView"));
    this.mView.subMenu = ((GWindowMenu)this.mView.create(new GWindowMenu()));
    this.mView.subMenu.close(false);
    this.mSelectItem = this.mView.subMenu.addItem(new GWindowMenuItem(this.mView.subMenu, Plugin.i18n("&Object"), Plugin.i18n("TIPObject")) {
      public void execute() {
        if (Builder.this.wSelect.isVisible()) Builder.this.wSelect.hideWindow(); else
          Builder.this.wSelect.showWindow();
      }
    });
    this.mViewLand = this.mView.subMenu.addItem(new GWindowMenuItem(this.mView.subMenu, Plugin.i18n("&Landscape"), Plugin.i18n("TIPLandscape")) {
      public void execute() {
        if (Builder.this.wViewLand.isVisible()) Builder.this.wViewLand.hideWindow(); else
          Builder.this.wViewLand.showWindow();
      }
    });
    if (this.bMultiSelect)
      this.mSnap = this.mView.subMenu.addItem(new GWindowMenuItem(this.mView.subMenu, "&Snap", null) {
        public void execute() {
          if (Builder.this.wSnap.isVisible()) Builder.this.wSnap.hideWindow(); else
            Builder.this.wSnap.showWindow();
        } } );
    this.mView.subMenu.addItem("-", null);
    this.mDisplayFilter = this.mView.subMenu.addItem(new GWindowMenuItem(this.mView.subMenu, Plugin.i18n("&DisplayFilter"), Plugin.i18n("TIPDisplayFilter")));
    this.mDisplayFilter.subMenu = ((GWindowMenu)this.mDisplayFilter.create(new GWindowMenu()));
    this.mDisplayFilter.subMenu.close(false);
    this.mView.subMenu.addItem("-", null);
    GWindowMenuItem localGWindowMenuItem = this.mView.subMenu.addItem(new GWindowMenuItem(this.mView.subMenu, Plugin.i18n("&IconSize"), Plugin.i18n("TIPIconSize")));
    localGWindowMenuItem.subMenu = ((GWindowMenu)localGWindowMenuItem.create(new GWindowMenu()));
    localGWindowMenuItem.subMenu.close(false);
    this.mIcon8 = localGWindowMenuItem.subMenu.addItem(new GWindowMenuItem(localGWindowMenuItem.subMenu, "&8", null) {
      public void execute() {
        Builder.this.conf.iconSize = 8;
        IconDraw.setScrSize(Builder.this.conf.iconSize, Builder.this.conf.iconSize);
        Builder.this.mIcon8.bChecked = true;
        Builder.this.mIcon16.bChecked = (Builder.this.mIcon32.bChecked = Builder.this.mIcon64.bChecked = 0);
      }
    });
    this.mIcon16 = localGWindowMenuItem.subMenu.addItem(new GWindowMenuItem(localGWindowMenuItem.subMenu, "&16", null) {
      public void execute() {
        Builder.this.conf.iconSize = 16;
        IconDraw.setScrSize(Builder.this.conf.iconSize, Builder.this.conf.iconSize);
        Builder.this.mIcon16.bChecked = true;
        Builder.this.mIcon8.bChecked = (Builder.this.mIcon32.bChecked = Builder.this.mIcon64.bChecked = 0);
      }
    });
    this.mIcon32 = localGWindowMenuItem.subMenu.addItem(new GWindowMenuItem(localGWindowMenuItem.subMenu, "&32", null) {
      public void execute() {
        Builder.this.conf.iconSize = 32;
        IconDraw.setScrSize(Builder.this.conf.iconSize, Builder.this.conf.iconSize);
        Builder.this.mIcon32.bChecked = true;
        Builder.this.mIcon8.bChecked = (Builder.this.mIcon16.bChecked = Builder.this.mIcon64.bChecked = 0);
      }
    });
    this.mIcon64 = localGWindowMenuItem.subMenu.addItem(new GWindowMenuItem(localGWindowMenuItem.subMenu, "&64", null) {
      public void execute() {
        Builder.this.conf.iconSize = 64;
        IconDraw.setScrSize(Builder.this.conf.iconSize, Builder.this.conf.iconSize);
        Builder.this.mIcon64.bChecked = true;
        Builder.this.mIcon8.bChecked = (Builder.this.mIcon16.bChecked = Builder.this.mIcon32.bChecked = 0);
      }
    });
    switch (this.conf.iconSize) { case 8:
      this.mIcon8.bChecked = true; break;
    case 16:
      this.mIcon16.bChecked = true; break;
    case 32:
      this.mIcon32.bChecked = true; break;
    case 64:
      this.mIcon64.bChecked = true; break;
    default:
      this.conf.iconSize = 16; this.mIcon16.bChecked = true;
    }
    IconDraw.setScrSize(this.conf.iconSize, this.conf.iconSize);
    localGWindowMenuItem = this.mView.subMenu.addItem(new GWindowMenuItem(this.mView.subMenu, Plugin.i18n("Save&ViewHLand"), Plugin.i18n("TIPSaveViewHLand")) {
      public void execute() {
        Builder.this.conf.bSaveViewHLand = (!Builder.this.conf.bSaveViewHLand);
        this.bChecked = Builder.this.conf.bSaveViewHLand;
      }
    });
    localGWindowMenuItem.bChecked = this.conf.bSaveViewHLand;
    localGWindowMenuItem = this.mView.subMenu.addItem(new GWindowMenuItem(this.mView.subMenu, Plugin.i18n("Show&Grid"), Plugin.i18n("TIPShowGrid")) {
      public void execute() {
        Builder.this.conf.bShowGrid = (!Builder.this.conf.bShowGrid);
        this.bChecked = Builder.this.conf.bShowGrid;
      }
    });
    localGWindowMenuItem.bChecked = this.conf.bShowGrid;
    localGWindowMenuItem = this.mView.subMenu.addItem(new GWindowMenuItem(this.mView.subMenu, Plugin.i18n("&AnimateCamera"), Plugin.i18n("TIPAnimateCamera")) {
      public void execute() {
        Builder.this.conf.bAnimateCamera = (!Builder.this.conf.bAnimateCamera);
        this.bChecked = Builder.this.conf.bAnimateCamera;
      }
    });
    localGWindowMenuItem.bChecked = this.conf.bAnimateCamera;

    this.wSelect = new WSelect(this, this.clientWindow);
    this.wViewLand = new WViewLand(this, this.clientWindow);
    if (this.bMultiSelect) {
      this.wSnap = new WSnap(this, this.clientWindow);
    }
    Plugin.doCreateGUI();

    this._gridFont = TTFont.font[1];
    this.smallFont = TTFont.font[0];
    this.emptyMat = Mat.New("icons/empty.mat");
    this.selectTargetMat = Mat.New("icons/selecttarget.mat");

    initHotKeys();

    Plugin.doStart();
    HotKeyCmdEnv.enable(envName, false);
    HotKeyCmdEnv.enable("MouseXYZ", false);
  }

  public class ClientWindow extends GWindow
  {
    public ClientWindow()
    {
    }

    public void resized()
    {
      GRegion localGRegion = this.parentWindow.getClientRegion();
      this.jdField_win_of_type_ComMaddoxGwindowGRegion.set(localGRegion);
      Builder.this.mapXscrollBar.setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, lookAndFeel().getHScrollBarH());
      Builder.this.mapXscrollBar.setPos(0.0F, this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - lookAndFeel().getHScrollBarH());
      Builder.this.mapYscrollBar.setSize(lookAndFeel().getVScrollBarW(), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - lookAndFeel().getHScrollBarH());
      Builder.this.mapYscrollBar.setPos(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - lookAndFeel().getVScrollBarW(), 0.0F);
      Builder.this.mapZSlider.setSize(lookAndFeel().getVSliderIntW(), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - lookAndFeel().getHScrollBarH());
      Builder.this.mapZSlider.setPos(0.0F, 0.0F);
      Builder.this.viewWindow.setPos(lookAndFeel().getVSliderIntW(), 0.0F);
      Builder.this.viewWindow.setSize(this.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 2.0F * lookAndFeel().getVScrollBarW(), this.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - lookAndFeel().getHScrollBarH());

      if (Builder.this.isLoadedLandscape())
        Builder.this.computeViewMap2D(Builder.this.viewH, Builder.this.camera2D.worldXOffset + Builder.this.viewX / 2.0D, Builder.this.camera2D.worldYOffset + Builder.this.viewY / 2.0D);
    }

    public void resolutionChanged() {
      resized();
    }
  }

  public class ViewWindow extends GWindow
  {
    public ViewWindow()
    {
    }

    public void mouseMove(float paramFloat1, float paramFloat2)
    {
      paramFloat2 = this.win.dy - paramFloat2 - 1.0F;
      Builder.this.doMouseAbsMove((int)paramFloat1, (int)paramFloat2);
    }
    public void keyFocusEnter() {
      HotKeyCmdEnv.enable(Builder.envName, true);
    }
    public void keyFocusExit() {
      HotKeyCmdEnv.enable(Builder.envName, false);
    }
  }

  public class ZSlider extends GWindowVSliderInt
  {
    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2) {
        if (Builder.this.isLoadedLandscape()) {
          double d1 = pos() / 100.0D;
          double d2 = d1 * d1;
          Builder.this.computeViewMap2D(d2, Builder.this.camera2D.worldXOffset + Builder.this.viewX / 2.0D, Builder.this.camera2D.worldYOffset + Builder.this.viewY / 2.0D);
        }
        return true;
      }
      return false;
    }
    public void setScrollPos(boolean paramBoolean1, boolean paramBoolean2) {
      int i = this.posCount / 64;
      if (i <= 0) i = 1;
      setPos(this.pos + (paramBoolean1 ? i : -i), paramBoolean2);
    }
    public ZSlider(GWindow arg2) { super();
    }
  }

  public class YScrollBar extends GWindowVScrollBar
  {
    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2) {
        if (Builder.this.isLoadedLandscape()) {
          Builder.this.worldScrool(0.0D, Main3D.cur3D().land2D.mapSizeY() - Builder.this.viewY - pos() / 100.0F - Builder.this.camera2D.worldYOffset - Main3D.cur3D().land2D.worldOfsY());
        }
        return true;
      }if (paramInt1 == 17) {
        return super.notify(paramInt1, paramInt2);
      }
      return false;
    }
    public YScrollBar(GWindow arg2) { super();
    }
  }

  public class XScrollBar extends GWindowHScrollBar
  {
    public boolean notify(int paramInt1, int paramInt2)
    {
      if (paramInt1 == 2) {
        if (Builder.this.isLoadedLandscape()) {
          Builder.this.worldScrool(pos() / 100.0F - Builder.this.camera2D.worldXOffset - Main3D.cur3D().land2D.worldOfsX(), 0.0D);
        }
        return true;
      }
      return false;
    }
    public XScrollBar(GWindow arg2) { super();
    }
  }

  class AnimateView
    implements MsgTimerListener
  {
    Actor aView;
    Loc from;
    Loc to;
    Loc cur = new Loc();

    public void msgTimer(MsgTimerParam paramMsgTimerParam, int paramInt, boolean paramBoolean1, boolean paramBoolean2) { float f = (paramInt + 1) / 100.0F;
      this.cur.interpolate(this.from, this.to, f);
      Builder.this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(this.cur);
      if (paramBoolean2) {
        if (this.aView != null) Builder.this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(this.aView, Main3D.cur3D().hookView, false); else
          Builder.this.endClearActorView();
        HotKeyCmdEnv.enable(Builder.envName, true);
      } }

    public AnimateView(Actor paramLoc1, Loc paramLoc2, Loc arg4) {
      this.aView = paramLoc1;
      this.from = paramLoc2;
      Object localObject;
      this.to = localObject;
      if (paramLoc1 != null)
        Builder.this.camera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setBase(null, null, false);
      HotKeyCmdEnv.enable(Builder.envName, false);
      RTSConf.cur.realTimer.msgAddListener(this, new MsgTimerParam(0, 0, Time.currentReal(), 100, 10, true, true));
    }
  }

  class FilterSelect
    implements ActorFilter
  {
    FilterSelect()
    {
    }

    public boolean isUse(Actor paramActor, double paramDouble)
    {
      if (!Builder.this.conf.bEnableSelect) return false;
      if (!Property.containsValue(paramActor, "builderSpawn")) return false;
      if ((paramActor instanceof ActorLabel)) return false;
      if ((paramActor instanceof BridgeSegment)) return false;
      if ((paramActor instanceof Bridge)) return false;
      Point3d localPoint3d = paramActor.pos.getAbsPoint();
      if ((localPoint3d.jdField_x_of_type_Double < Builder.this._selectX0) || (localPoint3d.jdField_x_of_type_Double > Builder.this._selectX1)) return false;
      if ((localPoint3d.jdField_y_of_type_Double < Builder.this._selectY0) || (localPoint3d.jdField_y_of_type_Double > Builder.this._selectY1)) return false;
      if (Builder.this._bSelect) Builder.this.selectedActors.put(paramActor, null); else
        Builder.this.selectedActors.remove(paramActor);
      return true;
    }
  }

  class InterpolateOnLand extends Interpolate
  {
    InterpolateOnLand()
    {
    }

    public boolean tick()
    {
      Actor localActor = Builder.this.selectedActor();
      if (!Actor.isValid(localActor))
        return true;
      if ((Builder.this.isFreeView()) && (Builder.this.bMultiSelect)) {
        localActor.pos.getAbs(Builder.this.__pi, Builder.this.__oi);
        double d1 = Engine.land().HQ(Builder.this.__pi.jdField_x_of_type_Double, Builder.this.__pi.jdField_y_of_type_Double);
        double d2 = Builder.this.__pi.z - d1;
        boolean bool = Engine.land().isWater(Builder.this.__pi.jdField_x_of_type_Double, Builder.this.__pi.jdField_y_of_type_Double);
        int i = (int)d2;
        if (d2 < 0.0D) d2 = -d2;
        int j = (int)(d2 * 100.0D) % 100;
        int k = TextScr.font().height() - TextScr.font().descender();
        Engine.land(); TextScr.output(5, 5, "curPos: " + (int)Builder.this.__pi.jdField_x_of_type_Double + " " + (int)Builder.this.__pi.jdField_y_of_type_Double + " H= " + i + "." + j + (bool ? " water HLand:" : " land HLand:") + (float)d1 + " type=" + Landscape.getPixelMapT(Engine.land().WORLD2PIXX(Builder.this.__pi.jdField_x_of_type_Double), Engine.land().WORLD2PIXY(Builder.this.__pi.jdField_y_of_type_Double)));

        TextScr.output(5, 5 + k, "Orient: " + (int)Builder.this.__oi.azimut() + " " + (int)Builder.this.__oi.tangage() + " " + (int)Builder.this.__oi.kren());
        Point3d localPoint3d = this.actor.pos.getAbsPoint();
        TextScr.output(5, 5 + 2 * k, "Distance: " + (int)localPoint3d.distance(Builder.this.__pi));
      }
      Builder.this.align(localActor);
      return true;
    }
  }

  class SelectFilter
    implements ActorFilter
  {
    private Actor _Actor = null;
    private double _Len2;
    private double _maxLen2;

    SelectFilter()
    {
    }

    public void reset(double paramDouble)
    {
      this._Actor = null; this._maxLen2 = paramDouble; } 
    public Actor get() { return this._Actor; } 
    public boolean isUse(Actor paramActor, double paramDouble) {
      if (!Builder.this.conf.bEnableSelect) return true;
      if (paramDouble <= this._maxLen2) {
        if ((paramActor instanceof BridgeSegment))
        {
          if (Builder.this.conf.bViewBridge)
            paramActor = paramActor.getOwner();
          else
            return true;
        }
        if ((paramActor instanceof Bridge))
        {
          if (!Builder.this.conf.bViewBridge) {
            return true;
          }
        }
        else if ((paramActor instanceof PPoint)) {
          if (Builder.this.isFreeView())
            return true;
          Path localPath = (Path)paramActor.getOwner();
          if (!localPath.isDrawing())
            return true;
        } else if (!Property.containsValue(paramActor, "builderSpawn")) {
          return true;
        }
        if (this._Actor == null) {
          this._Actor = paramActor;
          this._Len2 = paramDouble;
        }
        else if (paramDouble < this._Len2) {
          this._Actor = paramActor;
          this._Len2 = paramDouble;
        }
      }

      return true;
    }
  }
}