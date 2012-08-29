package com.maddox.il2.game;

import com.maddox.JGP.Color4f;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.builder.Builder;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorDraw;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorLandMesh;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSoundListener;
import com.maddox.il2.engine.BulletGeneric;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.ConsoleGL0;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.HookRender;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Land2D;
import com.maddox.il2.engine.Land2DText;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightEnv;
import com.maddox.il2.engine.LightEnvXY;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.MeshShared;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.engine.Renders;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.engine.TTFontTransform;
import com.maddox.il2.engine.TextScr;
import com.maddox.il2.engine.hotkey.FreeFly;
import com.maddox.il2.engine.hotkey.FreeFlyXYZ;
import com.maddox.il2.engine.hotkey.HookGunner;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.engine.hotkey.HookView;
import com.maddox.il2.engine.hotkey.HookViewEnemy;
import com.maddox.il2.engine.hotkey.HookViewFly;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.order.OrdersTree;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIBWDemoPlay;
import com.maddox.il2.gui.GUIBuilder;
import com.maddox.il2.gui.GUIRecordPlay;
import com.maddox.il2.gui.GUITrainingPlay;
import com.maddox.il2.net.Connect;
import com.maddox.il2.net.NetLocalControl;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.ActorViewPoint;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.il2.objects.air.Cockpit.Camera3DMirror;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.il2.objects.effects.Cinema;
import com.maddox.il2.objects.effects.DarkerNight;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.effects.LightsGlare;
import com.maddox.il2.objects.effects.OverLoad;
import com.maddox.il2.objects.effects.SpritesFog;
import com.maddox.il2.objects.effects.SunFlare;
import com.maddox.il2.objects.effects.SunGlare;
import com.maddox.il2.objects.effects.Zip;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.TestRunway;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.lights.SearchlightGeneric;
import com.maddox.il2.objects.weapons.Bomb;
import com.maddox.il2.objects.weapons.Rocket;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.gl;
import com.maddox.opengl.util.ScrShot;
import com.maddox.rts.CfgInt;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.Finger;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.InOutStreams;
import com.maddox.rts.IniFile;
import com.maddox.rts.KeyRecord;
import com.maddox.rts.KeyRecordCallback;
import com.maddox.rts.Keyboard;
import com.maddox.rts.Message;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetHost;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.rts.cmd.CmdSFS;
import com.maddox.sound.AudioDevice;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main3D extends Main
{
  public static float FOVX = 70.0F;
  public static final float ZNEAR = 1.2F;
  public static final float ZFAR = 48000.0F;
  protected boolean bDrawIfNotFocused;
  protected boolean bUseStartLog;
  protected boolean bUseGUI;
  private boolean bShowStartIntro;
  public GUIWindowManager guiManager;
  public KeyRecord keyRecord;
  public OrdersTree ordersTree;
  public TimeSkip timeSkip;
  public HookView hookView;
  public HookViewFly hookViewFly;
  public HookViewEnemy hookViewEnemy;
  public AircraftHotKeys aircraftHotKeys;
  public Cockpit[] cockpits;
  public Cockpit cockpitCur;
  public OverLoad overLoad;
  public OverLoad[] _overLoad;
  public SunGlare sunGlare;
  public SunGlare[] _sunGlare;
  public LightsGlare lightsGlare;
  public LightsGlare[] _lightsGlare;
  private SunFlare[] _sunFlare;
  public Render[] _sunFlareRender;
  private boolean bViewFly;
  private boolean bViewEnemy;
  public boolean bEnableFog;
  private boolean bDrawLand;
  public EffClouds clouds;
  public Zip zip;
  public boolean bDrawClouds;
  public SpritesFog spritesFog;
  public Cinema[] _cinema;
  public Render3D0R render3D0R;
  public Camera3DR camera3DR;
  public Render3D0 render3D0;
  public Render3D1 render3D1;
  public Camera3D camera3D;
  public Render3D0[] _render3D0;
  public Render3D1[] _render3D1;
  public Camera3D[] _camera3D;
  public Render2D render2D;
  public CameraOrtho2D camera2D;
  public Render2D[] _render2D;
  public CameraOrtho2D[] _camera2D;
  public Render3D0Mirror render3D0Mirror;
  public Render3D1Mirror render3D1Mirror;
  public Camera3D camera3DMirror;
  public Render2DMirror render2DMirror;
  public CameraOrtho2D camera2DMirror;
  public RenderCockpit renderCockpit;
  public Camera3D cameraCockpit;
  public RenderCockpit[] _renderCockpit;
  public Camera3D[] _cameraCockpit;
  public RenderCockpitMirror renderCockpitMirror;
  public Camera3D cameraCockpitMirror;
  public RenderHUD renderHUD;
  public CameraOrtho2D cameraHUD;
  public HUD hud;
  public RenderMap2D renderMap2D;
  public CameraOrtho2D cameraMap2D;
  public Land2D land2D;
  public Land2DText land2DText;
  public DarkerNight darkerNight;
  private static String _sLastMusic = "ru";
  protected int viewMirror;
  private int iconTypes;
  public static final String[] gameHotKeyCmdEnvs = { "Console", "hotkeys", "HookView", "PanView", "SnapView", "pilot", "move", "gunner", "misc", "orders", "aircraftView", "timeCompression", "gui" };

  public static final String[] builderHotKeyCmdEnvs = { "Console", "builder", "hotkeys" };
  KeyRecordCallback playRecordedMissionCallback;
  String playRecordedFile;
  int playBatchCurRecord;
  boolean playEndBatch;
  SectFile playRecordedSect;
  int playRecorderIndx;
  String playRecordedPlayFile;
  InOutStreams playRecordedStreams;
  NetChannelInStream playRecordedNetChannelIn;
  GameTrack gameTrackRecord;
  GameTrack gameTrackPlay;
  private boolean bLoadRecordedStates1Before;
  private boolean bRenderMirror;
  private int iRenderIndx;
  protected double[][] _modelMatrix3D;
  protected double[][] _projMatrix3D;
  protected int[][] _viewport;
  protected double[] _modelMatrix3DMirror;
  protected double[] _projMatrix3DMirror;
  protected int[] _viewportMirror;
  private double[] _dIn;
  private double[] _dOut;
  private static double shadowPairsR = 1000.0D;
  private static double shadowPairsR2 = shadowPairsR * shadowPairsR;
  private ArrayList shadowPairsList1;
  private HashMap shadowPairsMap1;
  private BigshipGeneric shadowPairsCur1;
  private ArrayList shadowPairsList2;
  private ActorFilter shadowPairsFilter;
  private DrwArray[] drwMaster;
  private DrwArray drwMirror;
  private Loc __l;
  private Point3d __p;
  private Orient __o;
  private Vector3d __v;
  private boolean bShowTime;
  public static final String ICONFAR_PROPERTY = "iconFar_shortClassName";
  public static final float iconFarActorSizeX = 2.0F;
  public static final float iconFarActorSizeY = 1.0F;
  public static final float iconFarSmallActorSize = 1.0F;
  public static int iconFarColor = 8355711;
  protected double iconGroundDrawMin;
  protected double iconSmallDrawMin;
  protected double iconAirDrawMin;
  protected double iconDrawMax;
  private Mat iconFarMat;
  private TTFont iconFarFont;
  private int iconFarFinger;
  private float iconFarFontHeight;
  private double iconClipX0;
  private double iconClipX1;
  private double iconClipY0;
  private double iconClipY1;
  private Actor iconFarPlayerActor;
  private Actor iconFarViewActor;
  private Actor iconFarPadlockActor;
  private FarActorItem iconFarPadlockItem;
  private ArrayList[] iconFarList;
  private int[] iconFarListLen;
  private FarActorFilter farActorFilter;
  private float[] line3XYZ;
  private Point3d _lineP;
  private Orient _lineO;
  private TransformMirror transformMirror;
  private long lastTimeScreenShot;
  private ScrShot scrShot;
  private ScreenSequence screenSequence;

  public Main3D()
  {
    this.bDrawIfNotFocused = false;
    this.bUseStartLog = false;
    this.bUseGUI = true;
    this.bShowStartIntro = false;

    this._overLoad = new OverLoad[3];

    this._sunGlare = new SunGlare[3];

    this._lightsGlare = new LightsGlare[3];

    this._sunFlare = new SunFlare[3];
    this._sunFlareRender = new Render[3];

    this.bViewFly = false;
    this.bViewEnemy = false;

    this.bEnableFog = true;
    this.bDrawLand = false;

    this.bDrawClouds = false;

    this._cinema = new Cinema[3];

    this._render3D0 = new Render3D0[3];
    this._render3D1 = new Render3D1[3];
    this._camera3D = new Camera3D[3];

    this._render2D = new Render2D[3];
    this._camera2D = new CameraOrtho2D[3];

    this._renderCockpit = new RenderCockpit[3];
    this._cameraCockpit = new Camera3D[3];

    this.viewMirror = 2;

    this.iconTypes = 3;

    this.bLoadRecordedStates1Before = false;

    this.bRenderMirror = false;
    this.iRenderIndx = 0;

    this._modelMatrix3D = new double[3][16];
    this._projMatrix3D = new double[3][16];
    this._viewport = new int[3][4];

    this._modelMatrix3DMirror = new double[16];
    this._projMatrix3DMirror = new double[16];
    this._viewportMirror = new int[4];

    this._dIn = new double[4];
    this._dOut = new double[4];

    this.shadowPairsList1 = new ArrayList();
    this.shadowPairsMap1 = new HashMap();
    this.shadowPairsCur1 = null;
    this.shadowPairsList2 = new ArrayList();
    this.shadowPairsFilter = new ShadowPairsFilter();

    this.drwMaster = new DrwArray[] { new DrwArray(), new DrwArray(), new DrwArray() };
    this.drwMirror = new DrwArray();

    this.__l = new Loc();
    this.__p = new Point3d();
    this.__o = new Orient();
    this.__v = new Vector3d();

    this.bShowTime = false;

    this.iconGroundDrawMin = 200.0D;
    this.iconSmallDrawMin = 100.0D;

    this.iconAirDrawMin = 1000.0D;
    this.iconDrawMax = 10000.0D;

    this.iconFarPadlockItem = new FarActorItem(0, 0, 0, 0, 0.0F, null);

    this.iconFarList = new ArrayList[] { new ArrayList(), new ArrayList(), new ArrayList() };
    this.iconFarListLen = new int[] { 0, 0, 0 };

    this.farActorFilter = new FarActorFilter();

    this.line3XYZ = new float[9];
    this._lineP = new Point3d();
    this._lineO = new Orient();

    this.transformMirror = new TransformMirror(null);

    this.lastTimeScreenShot = 0L;

    this.scrShot = null;
  }

  public static Main3D cur3D()
  {
    return (Main3D)cur();
  }

  public boolean isUseStartLog()
  {
    return this.bUseStartLog; } 
  public boolean isShowStartIntro() { return this.bShowStartIntro; } 
  public boolean isDrawLand() {
    return this.bDrawLand; } 
  public void setDrawLand(boolean paramBoolean) { this.bDrawLand = paramBoolean;
  }

  public boolean isDemoPlaying()
  {
    if (this.playRecordedStreams != null) return true;
    if (this.keyRecord == null) return false;
    return this.keyRecord.isPlaying();
  }
  public Actor viewActor() {
    return this.camera3D.pos.base();
  }
  public boolean isViewInsideShow() {
    if (!Actor.isValid(this.cockpitCur))
      return true;
    return !this.cockpitCur.isNullShow();
  }

  public boolean isEnableRenderingCockpit() {
    if (!Actor.isValid(this.cockpitCur))
      return true;
    return this.cockpitCur.isEnableRendering();
  }

  public boolean isViewOutside() {
    if (!Actor.isValid(this.cockpitCur))
      return true;
    return !this.cockpitCur.isFocused();
  }

  public boolean isViewPadlock() {
    if (!Actor.isValid(this.cockpitCur))
      return false;
    return this.cockpitCur.isPadlock();
  }

  public Actor getViewPadlockEnemy() {
    if (!Actor.isValid(this.cockpitCur))
      return null;
    return this.cockpitCur.getPadlockEnemy();
  }

  public void setViewInsideShow(boolean paramBoolean) {
    if ((isViewOutside()) || (isViewInsideShow() == paramBoolean))
      return;
    if (!Actor.isValid(this.cockpitCur))
      return;
    this.cockpitCur.setNullShow(!paramBoolean);
  }

  public void setEnableRenderingCockpit(boolean paramBoolean) {
    if ((isViewOutside()) || (isEnableRenderingCockpit() == paramBoolean))
      return;
    if (!Actor.isValid(this.cockpitCur))
      return;
    this.cockpitCur.setEnableRendering(paramBoolean);
  }

  private void endViewFly() {
    if (!this.bViewFly) return;
    this.hookViewFly.use(false);
    Engine.soundListener().setUseBaseSpeed(true);
    this.bViewFly = false;
  }
  private void endViewEnemy() {
    if (!this.bViewEnemy) return;
    this.hookViewEnemy.stop();
    this.bViewEnemy = false;
  }
  private void endView() {
    if (!isViewOutside()) return;
    if ((this.bViewFly) || (this.bViewEnemy)) return;
    this.hookView.use(false);
  }
  private void endViewInside() {
    if (isViewOutside()) return;
    this.cockpitCur.focusLeave();
  }

  public void setViewFly(Actor paramActor) {
    endView();
    endViewEnemy();
    endViewInside();

    Selector.resetGame();
    this.hookViewFly.use(true); this.bViewFly = true;
    this.camera3D.pos.setRel(new Point3d(), new Orient());
    this.camera3D.pos.changeBase(paramActor, this.hookViewFly, false);
    this.camera3D.pos.resetAsBase();
    Engine.soundListener().setUseBaseSpeed(false);
  }

  private void setViewSomebody(Actor paramActor) {
    endView();
    endViewFly();
    endViewInside();

    this.bViewEnemy = true;
    this.camera3D.pos.setRel(new Point3d(), new Orient());
    this.camera3D.pos.changeBase(paramActor, this.hookViewEnemy, false);
    this.camera3D.pos.resetAsBase();
    if ((paramActor instanceof ActorViewPoint))
      ((ActorViewPoint)paramActor).setViewActor(this.hookViewEnemy.enemy());
  }

  public void setViewEnemy(Actor paramActor, boolean paramBoolean1, boolean paramBoolean2) {
    Actor localActor = Selector.look(paramBoolean1, paramBoolean2, this.camera3D, paramActor.getArmy(), -1, paramActor, true);

    if (!this.hookViewEnemy.start(paramActor, localActor, paramBoolean2, true)) {
      if (this.bViewEnemy)
        setView(paramActor);
      return;
    }
    setViewSomebody(paramActor);
  }

  public void setViewFriend(Actor paramActor, boolean paramBoolean1, boolean paramBoolean2) {
    Actor localActor = Selector.look(paramBoolean1, paramBoolean2, this.camera3D, -1, paramActor.getArmy(), paramActor, true);

    if (!this.hookViewEnemy.start(paramActor, localActor, paramBoolean2, false)) {
      if (this.bViewEnemy)
        setView(paramActor);
      return;
    }
    setViewSomebody(paramActor);
  }

  public void setViewPadlock(boolean paramBoolean1, boolean paramBoolean2) {
    if (isViewOutside()) return;
    if (!this.cockpitCur.existPadlock()) return;
    Aircraft localAircraft = World.getPlayerAircraft();
    Actor localActor;
    if (World.cur().diffCur.No_Icons) {
      localActor = Selector.look(true, paramBoolean2, this.camera3D, -1, -1, localAircraft, false);
    }
    else if (paramBoolean1)
      localActor = Selector.look(true, paramBoolean2, this.camera3D, -1, localAircraft.getArmy(), localAircraft, false);
    else {
      localActor = Selector.look(true, paramBoolean2, this.camera3D, localAircraft.getArmy(), -1, localAircraft, false);
    }
    if ((localActor == null) || (localActor == localAircraft)) {
      return;
    }
    if (!this.cockpitCur.startPadlock(localActor))
      return; 
  }

  public void setViewEndPadlock() {
    if (isViewOutside()) return;
    if (!this.cockpitCur.existPadlock()) return;
    this.cockpitCur.endPadlock();
  }
  public void setViewNextPadlock(boolean paramBoolean) {
    if (isViewOutside()) return;
    if (!this.cockpitCur.existPadlock()) return;
    Actor localActor = Selector.next(paramBoolean);
    if (localActor == null)
      return;
    if (!this.cockpitCur.startPadlock(localActor))
      return;
  }

  public void setViewPadlockForward(boolean paramBoolean) {
    if (isViewPadlock())
      this.cockpitCur.setPadlockForward(paramBoolean);
  }

  public void setViewInside() {
    if ((!isViewOutside()) && (!isViewPadlock())) return;
    if (!Actor.isValid(this.cockpitCur)) return;
    if (!this.cockpitCur.isEnableFocusing()) return;
    endView();
    endViewFly();
    endViewEnemy();
    endViewInside();
    this.cockpitCur.focusEnter();
  }

  public void setViewFlow30(Actor paramActor) {
    setView(paramActor, true);
    this.hookView.set(paramActor, 30.0F, -30.0F); this.camera3D.pos.resetAsBase();
  }
  public void setViewFlow10(Actor paramActor, boolean paramBoolean) {
    this.hookView.setFollow(paramBoolean);
    setView(paramActor, true);
    this.hookView.set(paramActor, 10.0F, -10.0F); this.camera3D.pos.resetAsBase();
  }
  public void setView(Actor paramActor) { setView(paramActor, false); } 
  public void setView(Actor paramActor, boolean paramBoolean) {
    if ((viewActor() != paramActor) || (paramBoolean) || (this.bViewFly) || (this.bViewEnemy)) {
      endViewFly();
      endViewEnemy();
      endViewInside();

      Selector.resetGame();
      this.hookView.use(true);
      this.camera3D.pos.setRel(new Point3d(), new Orient());
      this.camera3D.pos.changeBase(paramActor, this.hookView, false);
      this.camera3D.pos.resetAsBase();
    }
  }

  public int cockpitCurIndx() {
    if ((this.cockpits == null) || (this.cockpitCur == null))
      return -1;
    for (int i = 0; i < this.cockpits.length; i++)
      if (this.cockpitCur == this.cockpits[i])
        return i;
    return -1;
  }

  public void beginStep(int paramInt) {
    if (!this.bUseStartLog)
      if (paramInt >= 0)
        ConsoleGL0.exclusiveDrawStep(I18N.gui("main.loading") + " " + paramInt + "%", paramInt);
      else
        ConsoleGL0.exclusiveDrawStep(null, -1);
  }

  public boolean beginApp(String paramString1, String paramString2, int paramInt) {
    Config.cur.mainSection = paramString2;
    Engine.cur = new Engine();

    Config.typeProvider();

    GLContext localGLContext = Config.cur.createGlContext(Config.cur.ini.get(Config.cur.mainSection, "title", "il2"));
    return beginApp(localGLContext, paramInt);
  }

  public boolean beginApp(GLContext paramGLContext, int paramInt)
  {
    Config.typeGlStrings();

    Config.cur.typeContextSettings(paramGLContext);
    this.bDrawIfNotFocused = Config.cur.ini.get("window", "DrawIfNotFocused", this.bDrawIfNotFocused);
    this.bShowStartIntro = Config.cur.ini.get("game", "intro", this.bShowStartIntro);

    RTSConf.cur.start();
    PaintScheme.init();
    NetEnv.cur().connect = new Connect();

    NetEnv.cur(); NetEnv.host().destroy(); new NetUser("No Name");
    NetEnv.active(true);
    Config.cur.beginSound();

    RenderContext.activate(paramGLContext);
    RendersMain.setSaveAspect(true);
    RendersMain.setGlContext(paramGLContext);
    RendersMain.setTickPainting(true);

    TTFont.font[0] = TTFont.get("arialSmall");
    TTFont.font[1] = TTFont.get("arial10");
    TTFont.font[2] = TTFont.get("arialb12");
    TTFont.font[3] = TTFont.get("courSmall");

    ConsoleGL0.init("Console", paramInt);

    this.bUseStartLog = Config.cur.ini.get("Console", "UseStartLog", this.bUseStartLog);
    if (this.bUseStartLog)
      ConsoleGL0.exclusiveDraw(true);
    else {
      ConsoleGL0.exclusiveDraw("gui/background0.mat");
    }
    beginStep(5);

    CmdEnv.top().exec("CmdLoad com.maddox.rts.cmd.CmdLoad");
    CmdEnv.top().exec("load com.maddox.rts.cmd.CmdFile PARAM CURENV on");
    CmdSFS.bMountError = false;

    CmdEnv.top().exec("file .rc");
    if (CmdSFS.bMountError) {
      return false;
    }
    beginStep(10);
    try {
      Engine.setWorldAcoustics("Landscape");
    } catch (Exception localException) {
      System.out.println("World Acoustics NOT initialized: " + localException.getMessage());
      return false;
    }
    Engine.soundListener().initDraw();

    beginStep(15);

    Regiment.loadAll();
    preloadNetClasses();
    beginStep(20);
    preloadAirClasses();
    beginStep(25);
    preloadChiefClasses();
    beginStep(30);
    preloadStationaryClasses();

    preload();

    beginStep(35);

    this.camera3D = new NetCamera3D();
    this.camera3D.setName("camera");
    this.camera3D.set(FOVX, 1.2F, 48000.0F);

    this.render3D0 = new Render3D0(0, 1.0F);
    this.render3D0.setSaveAspect(Config.cur.windowSaveAspect);
    this.render3D0.setName("render3D0");
    this.render3D0.setCamera(this.camera3D);
    Engine.lightEnv().sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
    Vector3f localVector3f = new Vector3f(0.0F, 1.0F, -1.0F); localVector3f.normalize();
    Engine.lightEnv().sun().set(localVector3f);

    this._camera3D[0] = this.camera3D;
    this._render3D0[0] = this.render3D0;
    for (int i = 1; i < 3; i++) {
      this._camera3D[i] = new Camera3D();
      this._camera3D[i].set(FOVX, 1.2F, 48000.0F);
      this._camera3D[i].pos.setBase(this.camera3D, null, false);
      this._render3D0[i] = new Render3D0(i, 1.0F - i * 0.001F);
      this._render3D0[i].setSaveAspect(true);
      this._render3D0[i].setCamera(this._camera3D[i]);
    }
    this._camera3D[1].pos.setRel(new Orient(-FOVX, 0.0F, 0.0F));
    this._camera3D[2].pos.setRel(new Orient(FOVX, 0.0F, 0.0F));

    this.render3D1 = new Render3D1(0, 0.9F);
    this.render3D1.setSaveAspect(Config.cur.windowSaveAspect);
    this.render3D1.setName("render3D1");
    this.render3D1.setCamera(this.camera3D);
    for (i = 1; i < 3; i++) {
      this._render3D1[i] = new Render3D1(i, 0.9F - i * 0.001F);
      this._render3D1[i].setSaveAspect(true);
      this._render3D1[i].setCamera(this._camera3D[i]);
    }

    this.camera3DR = new Camera3DR();
    this.camera3DR.setName("cameraR");
    this.camera3DR.set(FOVX, 1.2F, 48000.0F);
    this.camera3DR.pos.setBase(this.camera3D, new HookReflection(), false);
    this.render3D0R = new Render3D0R(1.1F);
    this.render3D0R.setSaveAspect(Config.cur.windowSaveAspect);
    this.render3D0R.setName("render3D0R");
    this.render3D0R.setCamera(this.camera3DR);

    Engine.soundListener().pos.setBase(this.camera3D, null, false);

    TextScr.font();

    this.camera2D = new CameraOrtho2D();
    this.camera2D.setName("camera2D");
    this.render2D = new Render2D(0, 0.95F);
    this.render2D.setSaveAspect(Config.cur.windowSaveAspect);
    this.render2D.setName("render2D");
    this.render2D.setCamera(this.camera2D);
    this.camera2D.set(0.0F, this.render2D.getViewPortWidth(), 0.0F, this.render2D.getViewPortHeight());
    this.camera2D.set(0.0F, 1.0F);
    this.render2D.setShow(true);
    this._camera2D[0] = this.camera2D;
    this._render2D[0] = this.render2D;
    for (i = 1; i < 3; i++) {
      this._camera2D[i] = new CameraOrtho2D();
      this._render2D[i] = new Render2D(i, 0.95F - i * 0.001F);
      this._render2D[i].setSaveAspect(true);
      this._render2D[i].setCamera(this._camera2D[i]);
      this._camera2D[i].set(0.0F, this._render2D[i].getViewPortWidth(), 0.0F, this._render2D[i].getViewPortHeight());
      this._camera2D[i].set(0.0F, 1.0F);
    }

    this.camera3DMirror = new Cockpit.Camera3DMirror();
    this.camera3DMirror.setName("cameraMirror");
    this.camera3DMirror.set(FOVX, 1.2F, 48000.0F);
    this.camera3DMirror.pos.setBase(this.camera3D, Cockpit.getHookCamera3DMirror(false), false);
    this.render3D0Mirror = new Render3D0Mirror(1.8F);
    this.render3D0Mirror.setName("render3D0Mirror");
    this.render3D0Mirror.setCamera(this.camera3DMirror);
    this.render3D1Mirror = new Render3D1Mirror(1.78F);
    this.render3D1Mirror.setName("render3D1Mirror");
    this.render3D1Mirror.setCamera(this.camera3DMirror);
    this.camera2DMirror = new CameraOrtho2D();
    this.camera2DMirror.setName("camera2DMirror");
    this.render2DMirror = new Render2DMirror(1.79F);
    this.render2DMirror.setName("render2DMirror");
    this.render2DMirror.setCamera(this.camera2DMirror);
    this.camera2DMirror.set(0.0F, this.render2DMirror.getViewPortWidth(), 0.0F, this.render2DMirror.getViewPortHeight());
    this.camera2DMirror.set(0.0F, 1.0F);

    this.cameraCockpit = new Camera3D();
    this.cameraCockpit.setName("cameraCockpit");
    this.cameraCockpit.set(FOVX, 0.05F, 12.5F);
    this.renderCockpit = new RenderCockpit(0, 0.5F);
    this.renderCockpit.setSaveAspect(Config.cur.windowSaveAspect);
    this.renderCockpit.setName("renderCockpit");
    this.renderCockpit.setCamera(this.cameraCockpit);
    this.renderCockpit.setShow(false);
    this._cameraCockpit[0] = this.cameraCockpit;
    this._renderCockpit[0] = this.renderCockpit;
    for (i = 1; i < 3; i++) {
      this._cameraCockpit[i] = new Camera3D();
      this._cameraCockpit[i].set(FOVX, 0.05F, 12.5F);
      this._cameraCockpit[i].pos.setBase(this.cameraCockpit, null, false);
      this._renderCockpit[i] = new RenderCockpit(i, 0.5F - i * 0.001F);
      this._renderCockpit[i].setSaveAspect(true);
      this._renderCockpit[i].setCamera(this._cameraCockpit[i]);
    }
    this._cameraCockpit[1].pos.setRel(new Orient(-FOVX, 0.0F, 0.0F));
    this._cameraCockpit[2].pos.setRel(new Orient(FOVX, 0.0F, 0.0F));

    this.cameraCockpitMirror = new Cockpit.Camera3DMirror();
    this.cameraCockpitMirror.pos.setBase(this.cameraCockpit, Cockpit.getHookCamera3DMirror(true), false);
    this.cameraCockpitMirror.setName("cameraCockpitMirror");
    this.cameraCockpitMirror.set(FOVX, 0.05F, 12.5F);
    this.renderCockpitMirror = new RenderCockpitMirror(1.77F);
    this.renderCockpitMirror.setName("renderCockpitMirror");
    this.renderCockpitMirror.setCamera(this.cameraCockpitMirror);

    this.cameraHUD = new CameraOrtho2D();
    this.cameraHUD.setName("cameraHUD");
    this.renderHUD = new RenderHUD(0.3F);
    this.renderHUD.setName("renderHUD");
    this.renderHUD.setCamera(this.cameraHUD);
    this.cameraHUD.set(0.0F, this.renderHUD.getViewPortWidth(), 0.0F, this.renderHUD.getViewPortHeight());
    this.cameraHUD.set(-1000.0F, 1000.0F);
    this.renderHUD.setShow(true);
    LightEnvXY localLightEnvXY = new LightEnvXY();
    this.renderHUD.setLightEnv(localLightEnvXY);
    localLightEnvXY.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
    localVector3f = new Vector3f(0.0F, 1.0F, -1.0F); localVector3f.normalize();
    localLightEnvXY.sun().set(localVector3f);

    this.hud = new HUD();
    this.renderHUD.contextResized();

    drawFarActorsInit();

    this.cameraMap2D = new CameraOrtho2D();
    this.cameraMap2D.setName("cameraMap2D");
    this.renderMap2D = new RenderMap2D(0.2F);
    this.renderMap2D.setName("renderMap2D");
    this.renderMap2D.setCamera(this.cameraMap2D);
    this.cameraMap2D.set(0.0F, this.renderMap2D.getViewPortWidth(), 0.0F, this.renderMap2D.getViewPortHeight());
    this.renderMap2D.setShow(false);

    beginStep(40);

    this._sunFlareRender[0] = SunFlare.newRender(0, 0.19F, this._camera3D[0]);
    this._sunFlareRender[0].setName("renderSunFlare");
    this._sunFlareRender[0].setSaveAspect(Config.cur.windowSaveAspect);
    this._sunFlareRender[0].setShow(false);
    for (int j = 1; j < 3; j++) {
      this._sunFlareRender[j] = SunFlare.newRender(j, 0.19F, this._camera3D[j]);
      this._sunFlareRender[j].setSaveAspect(true);
      this._sunFlareRender[j].setShow(false);
    }

    this.lightsGlare = new LightsGlare(0, 0.17F);
    this.lightsGlare.setSaveAspect(Config.cur.windowSaveAspect);
    this.lightsGlare.setCamera(new CameraOrtho2D());
    this.lightsGlare.setShow(false);
    this._lightsGlare[0] = this.lightsGlare;
    for (j = 1; j < 3; j++) {
      this._lightsGlare[j] = new LightsGlare(j, 0.17F - j * 0.001F);
      this._lightsGlare[j].setSaveAspect(true);
      this._lightsGlare[j].setCamera(new CameraOrtho2D());
    }

    this.sunGlare = new SunGlare(0, 0.15F);
    this.sunGlare.setSaveAspect(Config.cur.windowSaveAspect);
    this.sunGlare.setCamera(new CameraOrtho2D());
    this.sunGlare.setShow(false);
    this._sunGlare[0] = this.sunGlare;
    for (j = 1; j < 3; j++) {
      this._sunGlare[j] = new SunGlare(j, 0.15F - j * 0.001F);
      this._sunGlare[j].setSaveAspect(true);
      this._sunGlare[j].setCamera(new CameraOrtho2D());
    }

    this.overLoad = new OverLoad(0, 0.1F);
    this.overLoad.setSaveAspect(Config.cur.windowSaveAspect);
    this.overLoad.setCamera(new CameraOrtho2D());
    this.overLoad.setShow(false);
    this._overLoad[0] = this.overLoad;
    for (j = 1; j < 3; j++) {
      this._overLoad[j] = new OverLoad(j, 0.1F - j * 0.001F);
      this._overLoad[j].setSaveAspect(true);
      this._overLoad[j].setCamera(new CameraOrtho2D());
    }

    this.darkerNight = new DarkerNight(0, 0.5F);
    this.darkerNight.setSaveAspect(Config.cur.windowSaveAspect);
    this.darkerNight.setCamera(new CameraOrtho2D());
    this.darkerNight.setShow(true);

    this._cinema[0] = new Cinema(0, 0.09F);
    this._cinema[0].setSaveAspect(Config.cur.windowSaveAspect);
    this._cinema[0].setCamera(new CameraOrtho2D());
    this._cinema[0].setShow(false);
    for (j = 1; j < 3; j++) {
      this._cinema[j] = new Cinema(j, 0.09F - j * 0.001F);
      this._cinema[j].setSaveAspect(true);
      this._cinema[j].setCamera(new CameraOrtho2D());
      this._cinema[j].setShow(false);
    }

    this.timeSkip = new TimeSkip(-1.1F);

    HotKeyEnv.fromIni("hotkeys", Config.cur.ini, Config.cur.ini.get(Config.cur.mainSection, "hotkeys", "hotkeys"));

    FreeFly.init("FreeFly");
    FreeFlyXYZ.init("FreeFlyXYZ");
    this.hookView = new HookView("HookView");
    this.hookView.setCamera(this.camera3D);
    this.hookViewFly = new HookViewFly("HookViewFly");
    this.hookViewEnemy = new HookViewEnemy();
    this.hookViewEnemy.setCamera(this.camera3D);
    HookPilot.New();
    HookPilot.current.setTarget(this.cameraCockpit);
    HookPilot.current.setTarget2(this.camera3D);
    HookKeys.New();
    this.aircraftHotKeys = new AircraftHotKeys();

    beginStep(45);

    HotKeyCmdEnv.enable("default", false);

    HotKeyCmdEnv.enable("Console", false);
    HotKeyCmdEnv.enable("hotkeys", false);
    HotKeyCmdEnv.enable("HookView", false);
    HotKeyCmdEnv.enable("PanView", false);
    HotKeyCmdEnv.enable("SnapView", false);
    HotKeyCmdEnv.enable("pilot", false);
    HotKeyCmdEnv.enable("move", false);
    HotKeyCmdEnv.enable("gunner", false);
    HotKeyEnv.enable("pilot", false);
    HotKeyEnv.enable("move", false);
    HotKeyEnv.enable("gunner", false);
    HotKeyCmdEnv.enable("misc", false);
    HotKeyCmdEnv.enable("$$$misc", true);
    HotKeyEnv.enable("$$$misc", true);
    HotKeyCmdEnv.enable("orders", false);
    HotKeyCmdEnv.enable("aircraftView", false);
    HotKeyCmdEnv.enable("timeCompression", false);

    HotKeyCmdEnv.enable("gui", false);

    HotKeyCmdEnv.enable("builder", false);
    HotKeyCmdEnv.enable("MouseXYZ", false);

    HotKeyCmdEnv.enable("FreeFly", false);
    HotKeyCmdEnv.enable("FreeFlyXYZ", false);

    World.cur().userCfg = UserCfg.loadCurrent();
    World.cur().setUserCovers();

    this.ordersTree = new OrdersTree(true);

    beginStep(50);

    if (this.bUseGUI) {
      this.guiManager = GUI.create("gui");
      this.keyRecord = new KeyRecord();
      this.keyRecord.addExcludePrevCmd(278);
      Keyboard.adapter().setKeyEnable(27);
    }

    beginStep(90);

    initHotKeys();

    Voice.setEnableVoices(!Config.cur.ini.get("game", "NoChatter", false));

    beginStep(95);
    viewSet_Load();

    DeviceLink.start();

    onBeginApp();

    Time.setPause(false);
    RTSConf.cur.loopMsgs();
    Time.setPause(true);

    new MsgAction(64, 1.0D + Math.random() * 10.0D) {
      public void doAction() {
        try { Class.forName("fbapi");
          Main.doGameExit();
        }
        catch (Throwable localThrowable)
        {
        }
      }
    };
    this.bDrawClouds = true;
    TextScr.setColor(new Color4f(1.0F, 0.0F, 0.0F, 1.0F));

    RTSConf.cur.console.getEnv().exec("file rcu");
    beginStep(-1);
    createConsoleServer();
    return true;
  }

  public void setSaveAspect(boolean paramBoolean) {
    if (Config.cur.windowSaveAspect == paramBoolean) return;
    this.render3D0.setSaveAspect(paramBoolean);
    this.render3D1.setSaveAspect(paramBoolean);
    this.render2D.setSaveAspect(paramBoolean);
    this.renderCockpit.setSaveAspect(paramBoolean);
    this._sunFlareRender[0].setSaveAspect(paramBoolean);
    this.lightsGlare.setSaveAspect(paramBoolean);
    this.sunGlare.setSaveAspect(paramBoolean);
    this.overLoad.setSaveAspect(paramBoolean);
    this.darkerNight.setSaveAspect(paramBoolean);
    this._cinema[0].setSaveAspect(paramBoolean);
    Config.cur.windowSaveAspect = paramBoolean;
  }

  public static void menuMusicPlay() {
    menuMusicPlay(_sLastMusic);
  }

  public static void menuMusicPlay(String paramString) {
    paramString = Regiment.getCountryFromBranch(paramString);
    _sLastMusic = paramString;

    CmdEnv.top().exec("music FILE music/menu/" + _sLastMusic);
  }

  public void viewSet_Load() {
    int i = Config.cur.ini.get("game", "viewSet", 0);
    viewSet_Set(i);
    this.iconTypes = Config.cur.ini.get("game", "iconTypes", 3, 0, 3);
  }
  public void viewSet_Save() {
    if (this.aircraftHotKeys != null) {
      Config.cur.ini.set("game", "viewSet", viewSet_Get());
      Config.cur.ini.set("game", "iconTypes", iconTypes());
    }
  }

  protected int viewSet_Get() {
    int i = 0;
    if ((HookKeys.current != null) && 
      (HookKeys.current.isPanView())) i |= 1;
    if ((HookPilot.current != null) && 
      (HookPilot.current.isAim())) i |= 2;
    i |= (this.viewMirror & 0x3) << 2;
    if (!this.aircraftHotKeys.isAutoAutopilot()) i |= 16;
    i |= (HUD.drawSpeed() & 0x3) << 5;
    return i;
  }
  private void viewSet_Set(int paramInt) {
    HookKeys.current.setMode((paramInt & 0x1) != 0);
    HookPilot.current.doAim((paramInt & 0x2) != 0);
    this.viewMirror = (paramInt >> 2 & 0x3);
    this.aircraftHotKeys.setAutoAutopilot((paramInt & 0x10) == 0);
    HUD.setDrawSpeed(paramInt >> 5 & 0x3);
  }

  public boolean isViewMirror()
  {
    return this.viewMirror > 0;
  }

  public int iconTypes() {
    return this.iconTypes;
  }
  protected void changeIconTypes() { this.iconTypes = ((this.iconTypes + 1) % 4);
  }

  public void disableAllHotKeyCmdEnv()
  {
    List localList = HotKeyCmdEnv.allEnv();
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      HotKeyCmdEnv localHotKeyCmdEnv = (HotKeyCmdEnv)localList.get(j);
      localHotKeyCmdEnv.enable(false);
    }

    HotKeyCmdEnv.enable("hotkeys", true);
    HotKeyCmdEnv.enable("$$$misc", true);
  }
  public void enableHotKeyCmdEnvs(String[] paramArrayOfString) {
    for (int i = 0; i < paramArrayOfString.length; i++)
      HotKeyCmdEnv.enable(paramArrayOfString[i], true); 
  }

  public void enableOnlyHotKeyCmdEnvs(String[] paramArrayOfString) {
    disableAllHotKeyCmdEnv();
    enableHotKeyCmdEnvs(paramArrayOfString);
  }
  public void enableOnlyHotKeyCmdEnv(String paramString) {
    disableAllHotKeyCmdEnv();
    HotKeyCmdEnv.enable(paramString, true);
  }
  public void enableGameHotKeyCmdEnvs() {
    enableHotKeyCmdEnvs(gameHotKeyCmdEnvs);
  }
  public void enableOnlyGameHotKeyCmdEnvs() {
    enableOnlyHotKeyCmdEnvs(gameHotKeyCmdEnvs);
  }

  public void enableCockpitHotKeys() {
    if (isDemoPlaying()) return;
    if (!Actor.isValid(this.cockpitCur)) return;
    String[] arrayOfString = this.cockpitCur.getHotKeyEnvs();
    if (arrayOfString == null) return;
    for (int i = 0; i < arrayOfString.length; i++)
      if (arrayOfString[i] != null)
        HotKeyEnv.enable(arrayOfString[i], true); 
  }

  public void disableCockpitHotKeys() {
    if (isDemoPlaying()) return;
    if (!Actor.isValid(this.cockpitCur)) return;
    String[] arrayOfString = this.cockpitCur.getHotKeyEnvs();
    if (arrayOfString == null) return;
    for (int i = 0; i < arrayOfString.length; i++)
      if (arrayOfString[i] != null)
        HotKeyEnv.enable(arrayOfString[i], false);
  }

  private void _disableCockpitsHotKeys() {
    HotKeyEnv.enable("pilot", false);
    HotKeyEnv.enable("move", false);
    HotKeyEnv.enable("gunner", false);
  }

  public void disableCockpitsHotKeys() {
    if (isDemoPlaying()) return;
    _disableCockpitsHotKeys();
  }

  public void resetGameClear()
  {
    SearchlightGeneric.resetGame();

    disableCockpitsHotKeys();
    this.camera3D.pos.changeBase(null, null, false);
    this.camera3D.pos.setAbs(new Point3d(), new Orient());
    FreeFly.adapter().resetGame();
    if (HookPilot.current != null) {
      HookPilot.current.use(false);
      HookPilot.current.resetGame();
    }
    HookGunner.resetGame();
    if (HookKeys.current != null) {
      HookKeys.current.resetGame();
    }
    this.hookViewFly.reset();
    this.hookViewEnemy.reset();
    this.hookView.reset();
    this.hookView.resetGame();
    this.hookViewEnemy.resetGame();

    this.overLoad.setShow(false);

    for (int i = 0; i < 3; i++) {
      this._lightsGlare[i].setShow(false);
      this._lightsGlare[i].resetGame();

      this._sunGlare[i].setShow(false);
      this._sunGlare[i].resetGame();
    }

    Selector.resetGame();
    this.hud.resetGame();
    this.aircraftHotKeys.resetGame();
    this.bViewFly = false;
    this.bViewEnemy = false;
    this.ordersTree.resetGameClear();
    if (this.clouds != null) {
      this.clouds.destroy();
      this.clouds = null;
    }
    if (this.zip != null) {
      this.zip.destroy();
      this.zip = null;
    }
    sunFlareDestroy();

    if (Actor.isValid(this.spritesFog))
      this.spritesFog.destroy();
    this.spritesFog = null;
    if (this.land2D != null) {
      if (!this.land2D.isDestroyed())
        this.land2D.destroy();
      this.land2D = null;
    }
    if (this.land2DText != null) {
      if (!this.land2DText.isDestroyed())
        this.land2DText.destroy();
      this.land2DText = null;
    }

    if (this.cockpits != null) {
      for (i = 0; i < this.cockpits.length; i++) {
        if (Actor.isValid(this.cockpits[i]))
          this.cockpits[i].destroy();
        this.cockpits[i] = null;
      }
      this.cockpits = null;
    }
    this.cockpitCur = null;
    super.resetGameClear();
  }

  public void resetGameCreate() {
    super.resetGameCreate();
    Engine.soundListener().pos.setBase(this.camera3D, null, false);
    Engine.soundListener().setUseBaseSpeed(true);
  }

  public void resetUserClear() {
    World.cur().resetUser();
    this.aircraftHotKeys.resetUser();
    if (this.cockpits != null) {
      for (int i = 0; i < this.cockpits.length; i++) {
        if (Actor.isValid(this.cockpits[i]))
          this.cockpits[i].destroy();
        this.cockpits[i] = null;
      }
      this.cockpits = null;
    }
    this.cockpitCur = null;
    super.resetUserClear();
  }

  public void sunFlareCreate() {
    sunFlareDestroy();
    for (int i = 0; i < 3; i++)
      this._sunFlare[i] = new SunFlare(this._sunFlareRender[i]); 
  }

  public void sunFlareDestroy() {
    for (int i = 0; i < 3; i++) {
      if (Actor.isValid(this._sunFlare[i]))
        this._sunFlare[i].destroy();
      this._sunFlare[i] = null;
    }
  }

  public void sunFlareShow(boolean paramBoolean) {
    for (int i = 0; i < 3; i++)
      this._sunFlareRender[i].setShow(paramBoolean);
  }

  public KeyRecordCallback playRecordedMissionCallback()
  {
    return this.playRecordedMissionCallback;
  }
  public InOutStreams playRecordedStreams() {
    return this.playRecordedStreams;
  }
  NetChannelInStream playRecordedNetChannelIn() {
    return this.playRecordedNetChannelIn;
  }
  public GameTrack gameTrackRecord() { return this.gameTrackRecord; } 
  public void setGameTrackRecord(GameTrack paramGameTrack) { this.gameTrackRecord = paramGameTrack; } 
  public GameTrack gameTrackPlay() { return this.gameTrackPlay; } 
  public void setGameTrackPlay(GameTrack paramGameTrack) { this.gameTrackPlay = paramGameTrack; } 
  public void clearGameTrack(GameTrack paramGameTrack) {
    if (paramGameTrack == this.gameTrackRecord) this.gameTrackRecord = null;
    if (paramGameTrack == this.gameTrackPlay) this.gameTrackPlay = null; 
  }

  public String playRecordedMission(String paramString)
  {
    this.playBatchCurRecord = -1;
    this.playEndBatch = true;
    this.playRecordedStreams = null;
    return playRecordedMission(paramString, true);
  }

  public String playRecordedMission(String paramString, boolean paramBoolean) {
    this.playRecordedFile = paramString;
    if (this.playRecordedMissionCallback == null) {
      this.playRecordedMissionCallback = new KeyRecordCallback() {
        public void playRecordedEnded() {
          if (this != Main3D.this.playRecordedMissionCallback) return;
          GameState localGameState = Main.state();
          Object localObject;
          if ((localGameState instanceof GUIRecordPlay)) {
            localObject = (GUIRecordPlay)localGameState;
            ((GUIRecordPlay)localObject).doReplayMission(Main3D.this.playRecordedFile, Main3D.this.playEndBatch);
          } else if ((localGameState instanceof GUITrainingPlay)) {
            localObject = (GUITrainingPlay)localGameState;
            ((GUITrainingPlay)localObject).doQuitMission();
            ((GUITrainingPlay)localObject).doExit();
          } else if ((localGameState instanceof GUIBWDemoPlay)) {
            localObject = (GUIBWDemoPlay)localGameState;
            ((GUIBWDemoPlay)localObject).doQuitMission();
          }
        }

        public void doFirstHotCmd(boolean paramBoolean) {
          if (Main3D.this.playRecordedStreams != null) {
            AircraftHotKeys.bFirstHotCmd = paramBoolean;
            Main3D.this.loadRecordedStates1(paramBoolean);
            if (!paramBoolean)
              Main3D.this.loadRecordedStates2();
          }
        }
      };
    }
    if (this.playRecordedStreams != null) {
      try { this.playRecordedStreams.close(); } catch (Exception localException1) {
      }this.playRecordedStreams = null;
      NetMissionTrack.stopPlaying();
    }
    if (this.playRecordedNetChannelIn != null)
      this.playRecordedNetChannelIn.destroy();
    this.playRecordedNetChannelIn = null;

    if (InOutStreams.isExistAndValid(new File(paramString))) {
      return playNetRecordedMission(paramString, paramBoolean);
    }
    String str = paramString;
    SectFile localSectFile = new SectFile(paramString, 0, false);
    int i = localSectFile.sectionIndex("batch");
    if (i >= 0) {
      j = localSectFile.vars(i);
      if (j <= 0)
        return "Track file '" + paramString + "' is empty";
      this.playEndBatch = ((this.playBatchCurRecord != -1) && (this.playBatchCurRecord == j - 2));
      if (j == 1) this.playEndBatch = true;
      this.playBatchCurRecord += 1;
      if (this.playBatchCurRecord >= j)
        this.playBatchCurRecord = 0;
      str = "Records/" + localSectFile.line(i, this.playBatchCurRecord);
      if (InOutStreams.isExistAndValid(new File(str))) {
        return playNetRecordedMission(str, paramBoolean);
      }
      localSectFile = new SectFile(str, 0, false);
    } else {
      this.playEndBatch = true;
    }
    i = localSectFile.sectionIndex("$$$record");
    if (i < 0)
      return "Track file '" + str + "' not included section [$$$record]";
    if (localSectFile.vars(i) <= 10)
      return "Track file '" + str + "' is empty";
    int j = Integer.parseInt(localSectFile.var(i, 0));
    if (j != 130)
      return "Track file '" + str + "' version is not supported";
    int k = Integer.parseInt(localSectFile.var(i, 1));
    float f1 = Float.parseFloat(localSectFile.var(i, 2));
    float f2 = Float.parseFloat(localSectFile.var(i, 3));
    float f3 = Float.parseFloat(localSectFile.var(i, 4));
    float f4 = Float.parseFloat(localSectFile.var(i, 5));
    float f5 = Float.parseFloat(localSectFile.var(i, 6));
    int m = Integer.parseInt(localSectFile.var(i, 7));
    int n = Integer.parseInt(localSectFile.var(i, 8));

    long l1 = Long.parseLong(localSectFile.var(i, 9));
    long l2 = localSectFile.fingerExcludeSectPrefix("$$$");
    l2 = Finger.incLong(l2, k);
    l2 = Finger.incLong(l2, f1);
    l2 = Finger.incLong(l2, f2);
    l2 = Finger.incLong(l2, f3);
    l2 = Finger.incLong(l2, f4);
    l2 = Finger.incLong(l2, f5);
    l2 = Finger.incLong(l2, m);
    l2 = Finger.incLong(l2, n);
    if (l1 != l2) {
      return "Track file '" + str + "' is changed";
    }
    World.cur().diffCur.set(k);
    World.cur().diffCur.Cockpit_Always_On = false;
    World.cur().diffCur.No_Outside_Views = false;
    World.cur().diffCur.No_Padlock = false;
    World.cur().userCoverMashineGun = f1;
    World.cur().userCoverCannon = f2;
    World.cur().userCoverRocket = f3;
    World.cur().userRocketDelay = f4;
    World.cur().userBombDelay = f5;
    viewSet_Set(m);
    this.iconTypes = n;

    if (Main.cur().netServerParams == null) {
      new NetServerParams();
      Main.cur().netServerParams.setMode(2);
      new NetLocalControl();
    }
    try
    {
      Mission.loadFromSect(localSectFile, true);
    } catch (Exception localException2) {
      System.out.println(localException2.getMessage());
      localException2.printStackTrace();
      return "Track file '" + str + "' load failed: " + localException2.getMessage();
    }
    this.playRecordedSect = localSectFile;
    this.playRecorderIndx = i;
    this.playRecordedPlayFile = str;
    if (paramBoolean) doRecordedPlayFirst();
    return null;
  }

  private String playNetRecordedMission(String paramString, boolean paramBoolean) {
    try {
      this.playRecordedStreams = new InOutStreams();
      this.playRecordedStreams.open(new File(paramString), false);

      InputStream localInputStream1 = this.playRecordedStreams.openStream("version");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream1));
      int i = Integer.parseInt(localBufferedReader.readLine());
      int j = i;
      if (i >= 103)
        j = Integer.parseInt(localBufferedReader.readLine());
      localBufferedReader.close();

      if ((i != 100) && (i != 101) && (i != 102) && (i != 103)) {
        try { this.playRecordedStreams.close(); } catch (Exception localException3) {
        }this.playRecordedStreams = null;
        return "Track file '" + paramString + "' version is not supported";
      }

      loadRecordedStates0();
      InputStream localInputStream2 = this.playRecordedStreams.openStream("traffic");
      if (localInputStream2 == null)
        throw new Exception("Stream 'traffic' not found.");
      this.playRecordedNetChannelIn = new NetChannelInStream(localInputStream2, 1);
      RTSConf.cur.netEnv.addChannel(this.playRecordedNetChannelIn);
      this.playRecordedNetChannelIn.setStateInit(0);
      this.playRecordedNetChannelIn.userState = 1;
      NetMissionTrack.startPlaying(this.playRecordedStreams, i, j);
      if (paramBoolean) doRecordedPlayFirst(); 
    }
    catch (Exception localException1) {
      localException1.printStackTrace();
      if (this.playRecordedStreams != null) try {
          this.playRecordedStreams.close(); } catch (Exception localException2) {
        } this.playRecordedStreams = null;
      return "Track file '" + paramString + "' load failed: " + localException1.getMessage();
    }
    return null;
  }

  private void doRecordedPlayFirst() {
    _disableCockpitsHotKeys();
    HotKeyEnv.enable("misc", false);
    HotKeyEnv.enable("orders", false);
    HotKeyEnv.enable("timeCompression", false);
    HotKeyEnv.enable("aircraftView", false);
    HotKeyEnv.enable("HookView", false);
    HotKeyEnv.enable("PanView", false);
    HotKeyEnv.enable("SnapView", false);
  }

  public String startPlayRecordedMission()
  {
    if (this.playRecordedStreams != null) {
      this.keyRecord.startPlay(this.playRecordedMissionCallback);
    }
    else if (!this.keyRecord.startPlay(this.playRecordedSect, this.playRecorderIndx, 10, this.playRecordedMissionCallback)) {
      return "Track file '" + this.playRecordedPlayFile + "' load failed";
    }
    return null;
  }

  public void stopPlayRecordedMission() {
    this.playRecordedSect = null;
    if (this.keyRecord.isPlaying()) {
      this.keyRecord.stopPlay();
    }
    if (this.playRecordedStreams != null) {
      try { this.playRecordedStreams.close(); } catch (Exception localException) {
      }this.playRecordedStreams = null;
      NetMissionTrack.stopPlaying();
    }
    if (this.playRecordedNetChannelIn != null)
      this.playRecordedNetChannelIn.destroy();
    this.playRecordedNetChannelIn = null;

    _disableCockpitsHotKeys();
    HotKeyEnv.enable("misc", true);
    HotKeyEnv.enable("orders", true);
    HotKeyEnv.enable("timeCompression", true);
    HotKeyEnv.enable("aircraftView", true);
    HotKeyEnv.enable("HookView", true);
    HotKeyEnv.enable("PanView", true);
    HotKeyEnv.enable("SnapView", true);
  }

  public void flyRecordedMission() {
    if (this.keyRecord.isPlaying()) {
      this.keyRecord.stopPlay();
      this.playRecordedMissionCallback = null;
      if (Actor.isValid(this.cockpitCur))
        HotKeyCmd.exec("misc", "cockpitEnter" + cockpitCurIndx());
      enableCockpitHotKeys();
      HotKeyEnv.enable("misc", true);
      HotKeyEnv.enable("orders", true);
      HotKeyEnv.enable("timeCompression", true);
      HotKeyEnv.enable("aircraftView", true);
      HotKeyEnv.enable("HookView", true);
      HotKeyEnv.enable("PanView", true);
      HotKeyEnv.enable("SnapView", true);

      ForceFeedback.startMission();
    }
  }

  public boolean saveRecordedMission(String paramString) {
    if (this.mission == null) return false;
    if (this.mission.isDestroyed()) return false;
    if (!this.keyRecord.isContainRecorded()) return false; try
    {
      SectFile localSectFile = this.mission.sectFile();
      int i = localSectFile.sectionIndex("$$$record");
      if (i >= 0)
        localSectFile.sectionClear(i);
      else {
        i = localSectFile.sectionAdd("$$$record");
      }
      localSectFile.lineAdd(i, "130", "");
      long l = Finger.incLong(this.mission.finger(), 130);
      int j = World.cur().diffCur.get();
      localSectFile.lineAdd(i, "" + j, "");
      l = Finger.incLong(this.mission.finger(), j);
      localSectFile.lineAdd(i, "" + World.cur().userCoverMashineGun, "");
      l = Finger.incLong(l, World.cur().userCoverMashineGun);
      localSectFile.lineAdd(i, "" + World.cur().userCoverCannon, "");
      l = Finger.incLong(l, World.cur().userCoverCannon);
      localSectFile.lineAdd(i, "" + World.cur().userCoverRocket, "");
      l = Finger.incLong(l, World.cur().userCoverRocket);
      localSectFile.lineAdd(i, "" + World.cur().userRocketDelay, "");
      l = Finger.incLong(l, World.cur().userRocketDelay);
      localSectFile.lineAdd(i, "" + World.cur().userBombDelay, "");
      l = Finger.incLong(l, World.cur().userBombDelay);
      localSectFile.lineAdd(i, "" + Mission.viewSet, "");
      l = Finger.incLong(l, Mission.viewSet);
      localSectFile.lineAdd(i, "" + Mission.iconTypes, "");
      l = Finger.incLong(l, Mission.iconTypes);

      localSectFile.lineAdd(i, "" + l, "");
      this.keyRecord.saveRecorded(localSectFile, i);
      return localSectFile.saveFile(paramString);
    } catch (Exception localException) {
    }
    return false;
  }

  public boolean saveRecordedStates0(InOutStreams paramInOutStreams)
  {
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(paramInOutStreams.createStream("states0"));
      localPrintWriter.println(World.cur().diffCur.get());
      localPrintWriter.println(World.cur().userCoverMashineGun);
      localPrintWriter.println(World.cur().userCoverCannon);
      localPrintWriter.println(World.cur().userCoverRocket);
      localPrintWriter.println(World.cur().userRocketDelay);
      localPrintWriter.println(World.cur().userBombDelay);
      localPrintWriter.println(viewSet_Get());
      localPrintWriter.println(this.iconTypes);
      localPrintWriter.println(isViewOutside() ? "0" : "1");
      localPrintWriter.println(FOVX);
      localPrintWriter.flush(); localPrintWriter.close();
      return true;
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }return false;
  }

  public boolean saveRecordedStates1(InOutStreams paramInOutStreams) {
    try {
      PrintWriter localPrintWriter = new PrintWriter(paramInOutStreams.createStream("states1"));
      HookView.cur().saveRecordedStates(localPrintWriter);
      HookPilot.cur().saveRecordedStates(localPrintWriter);
      HookGunner.saveRecordedStates(localPrintWriter);
      localPrintWriter.flush(); localPrintWriter.close();
      return true;
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }return false;
  }

  public boolean saveRecordedStates2(InOutStreams paramInOutStreams) {
    try {
      PrintWriter localPrintWriter = new PrintWriter(paramInOutStreams.createStream("states2"));
      localPrintWriter.println(FOVX);
      int i = 0;
      if (this.hud.bDrawDashBoard) i |= 1;
      if (isViewInsideShow()) i |= 2;
      if ((Actor.isValid(this.cockpitCur)) && (this.cockpitCur.isToggleAim()))
        i |= 4;
      FlightModel localFlightModel = World.getPlayerFM();
      if ((localFlightModel != null) && (localFlightModel.AS.bShowSmokesOn))
        i |= 8;
      if (isEnableRenderingCockpit()) i |= 16;
      if ((Actor.isValid(this.cockpitCur)) && (this.cockpitCur.isToggleUp()))
        i |= 32;
      if ((Actor.isValid(this.cockpitCur)) && (this.cockpitCur.isToggleDim()))
        i |= 64;
      if ((Actor.isValid(this.cockpitCur)) && (this.cockpitCur.isToggleLight()))
        i |= 128;
      if ((Actor.isValid(this.cockpitCur)) && (!this.cockpitCur.isEnableRenderingBall()))
        i |= 256;
      localPrintWriter.println(i);
      localPrintWriter.flush(); localPrintWriter.close();
      return true;
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }return false;
  }

  public void loadRecordedStates0()
  {
    try {
      InputStream localInputStream = this.playRecordedStreams.openStream("states0");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
      World.cur().diffCur.set(Integer.parseInt(localBufferedReader.readLine()));
      World.cur().diffCur.Cockpit_Always_On = false;
      World.cur().diffCur.No_Outside_Views = false;
      World.cur().diffCur.No_Padlock = false;
      World.cur().userCoverMashineGun = Float.parseFloat(localBufferedReader.readLine());
      World.cur().userCoverCannon = Float.parseFloat(localBufferedReader.readLine());
      World.cur().userCoverRocket = Float.parseFloat(localBufferedReader.readLine());
      World.cur().userRocketDelay = Float.parseFloat(localBufferedReader.readLine());
      World.cur().userBombDelay = Float.parseFloat(localBufferedReader.readLine());
      viewSet_Set(Integer.parseInt(localBufferedReader.readLine()));
      this.iconTypes = Integer.parseInt(localBufferedReader.readLine());
      this.bLoadRecordedStates1Before = (Integer.parseInt(localBufferedReader.readLine()) == 1);
      float f = Float.parseFloat(localBufferedReader.readLine());
      if (f != FOVX)
        CmdEnv.top().exec("fov " + f);
      localInputStream.close();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  public void loadRecordedStates1(boolean paramBoolean) {
    if (paramBoolean == this.bLoadRecordedStates1Before)
      try {
        InputStream localInputStream = this.playRecordedStreams.openStream("states1");
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
        HookView.cur().loadRecordedStates(localBufferedReader);
        HookPilot.cur().loadRecordedStates(localBufferedReader);
        HookGunner.loadRecordedStates(localBufferedReader);
        localInputStream.close();
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
  }

  public void loadRecordedStates2() {
    try {
      InputStream localInputStream = this.playRecordedStreams.openStream("states2");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
      float f = Float.parseFloat(localBufferedReader.readLine());
      if (f != FOVX)
        CmdEnv.top().exec("fov " + f);
      int i = Integer.parseInt(localBufferedReader.readLine());
      this.hud.bDrawDashBoard = ((i & 0x1) != 0);
      setViewInsideShow((i & 0x2) != 0);
      if (Actor.isValid(this.cockpitCur))
        this.cockpitCur.doToggleAim((i & 0x4) != 0);
      FlightModel localFlightModel = World.getPlayerFM();
      if (localFlightModel != null)
        localFlightModel.AS.setAirShowState((i & 0x8) != 0);
      if (Actor.isValid(this.cockpitCur)) {
        setEnableRenderingCockpit((i & 0x10) != 0);
        this.cockpitCur.doToggleUp((i & 0x20) != 0);
        if (((i & 0x40) != 0) && 
          (!this.cockpitCur.isToggleDim()))
          this.cockpitCur.doToggleDim();
        if (((i & 0x80) != 0) && 
          (!this.cockpitCur.isToggleLight()))
          this.cockpitCur.doToggleLight();
        this.cockpitCur.setEnableRenderingBall((i & 0x100) == 0);
      }
      localInputStream.close();
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  public void setRenderIndx(int paramInt)
  {
    this.iRenderIndx = paramInt;
  }
  public int getRenderIndx() {
    return this.iRenderIndx;
  }

  private void transform_point(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3)
  {
    paramArrayOfDouble1[0] = (paramArrayOfDouble2[0] * paramArrayOfDouble3[0] + paramArrayOfDouble2[4] * paramArrayOfDouble3[1] + paramArrayOfDouble2[8] * paramArrayOfDouble3[2] + paramArrayOfDouble2[12] * paramArrayOfDouble3[3]);
    paramArrayOfDouble1[1] = (paramArrayOfDouble2[1] * paramArrayOfDouble3[0] + paramArrayOfDouble2[5] * paramArrayOfDouble3[1] + paramArrayOfDouble2[9] * paramArrayOfDouble3[2] + paramArrayOfDouble2[13] * paramArrayOfDouble3[3]);
    paramArrayOfDouble1[2] = (paramArrayOfDouble2[2] * paramArrayOfDouble3[0] + paramArrayOfDouble2[6] * paramArrayOfDouble3[1] + paramArrayOfDouble2[10] * paramArrayOfDouble3[2] + paramArrayOfDouble2[14] * paramArrayOfDouble3[3]);
    paramArrayOfDouble1[3] = (paramArrayOfDouble2[3] * paramArrayOfDouble3[0] + paramArrayOfDouble2[7] * paramArrayOfDouble3[1] + paramArrayOfDouble2[11] * paramArrayOfDouble3[2] + paramArrayOfDouble2[15] * paramArrayOfDouble3[3]);
  }

  public boolean project2d(double paramDouble1, double paramDouble2, double paramDouble3, Point3d paramPoint3d) {
    this._dIn[0] = paramDouble1; this._dIn[1] = paramDouble2; this._dIn[2] = paramDouble3; this._dIn[3] = 1.0D;
    if (this.bRenderMirror) {
      transform_point(this._dOut, this._modelMatrix3DMirror, this._dIn);
      transform_point(this._dIn, this._projMatrix3DMirror, this._dOut);
    } else {
      transform_point(this._dOut, this._modelMatrix3D[this.iRenderIndx], this._dIn);
      transform_point(this._dIn, this._projMatrix3D[this.iRenderIndx], this._dOut);
    }

    if (this._dIn[3] == 0.0D) {
      System.out.println("BAD glu.Project: " + paramDouble1 + " " + paramDouble2 + " " + paramDouble3);
      return false;
    }

    this._dIn[0] /= this._dIn[3]; this._dIn[1] /= this._dIn[3]; this._dIn[2] /= this._dIn[3];

    if (this.bRenderMirror) {
      paramPoint3d.x = (this._viewportMirror[0] + (1.0D + this._dIn[0]) * this._viewportMirror[2] / 2.0D);
      paramPoint3d.y = (this._viewportMirror[1] + (1.0D + this._dIn[1]) * this._viewportMirror[3] / 2.0D);
    } else {
      paramPoint3d.x = (this._viewport[this.iRenderIndx][0] + (1.0D + this._dIn[0]) * this._viewport[this.iRenderIndx][2] / 2.0D);
      paramPoint3d.y = (this._viewport[this.iRenderIndx][1] + (1.0D + this._dIn[1]) * this._viewport[this.iRenderIndx][3] / 2.0D);
    }
    paramPoint3d.z = ((1.0D + this._dIn[2]) / 2.0D);
    return true;
  }

  public boolean project2d_cam(double paramDouble1, double paramDouble2, double paramDouble3, Point3d paramPoint3d) {
    if (!project2d(paramDouble1, paramDouble2, paramDouble3, paramPoint3d))
      return false;
    if (this.bRenderMirror) {
      paramPoint3d.x -= this._viewportMirror[0];
      paramPoint3d.y -= this._viewportMirror[1];
    } else {
      paramPoint3d.x -= this._viewport[this.iRenderIndx][0];
      paramPoint3d.y -= this._viewport[this.iRenderIndx][1];
    }
    return true;
  }

  public boolean project2d_norm(double paramDouble1, double paramDouble2, double paramDouble3, Point3d paramPoint3d) {
    this._dIn[0] = paramDouble1; this._dIn[1] = paramDouble2; this._dIn[2] = paramDouble3; this._dIn[3] = 1.0D;
    if (this.bRenderMirror) {
      transform_point(this._dOut, this._modelMatrix3DMirror, this._dIn);
      transform_point(this._dIn, this._projMatrix3DMirror, this._dOut);
    } else {
      transform_point(this._dOut, this._modelMatrix3D[this.iRenderIndx], this._dIn);
      transform_point(this._dIn, this._projMatrix3D[this.iRenderIndx], this._dOut);
    }

    if (this._dIn[3] == 0.0D) {
      System.out.println("BAD glu.Project2: " + paramDouble1 + " " + paramDouble2 + " " + paramDouble3);
      return false;
    }

    double d = 1.0D / this._dIn[3];
    paramPoint3d.x = (this._dIn[0] * d);
    paramPoint3d.y = (this._dIn[1] * d);
    paramPoint3d.z = (this._dIn[2] * d);

    return true;
  }

  public boolean project2d(Point3d paramPoint3d1, Point3d paramPoint3d2) {
    return project2d(paramPoint3d1.x, paramPoint3d1.y, paramPoint3d1.z, paramPoint3d2);
  }
  public boolean project2d_cam(Point3d paramPoint3d1, Point3d paramPoint3d2) {
    return project2d_cam(paramPoint3d1.x, paramPoint3d1.y, paramPoint3d1.z, paramPoint3d2);
  }

  private void shadowPairsClear()
  {
    this.shadowPairsList1.clear();
    this.shadowPairsMap1.clear();
    this.shadowPairsList2.clear();
    this.shadowPairsCur1 = null;
  }
  private void shadowPairsAdd(ArrayList paramArrayList) {
    int i = paramArrayList.size();
    for (int j = 0; j < i; j++) {
      Object localObject = paramArrayList.get(j);
      if (((localObject instanceof BigshipGeneric)) && (!(localObject instanceof TestRunway))) {
        BigshipGeneric localBigshipGeneric = (BigshipGeneric)localObject;
        if ((Actor.isValid(localBigshipGeneric.getAirport())) && (!this.shadowPairsMap1.containsKey(localBigshipGeneric))) {
          this.shadowPairsList1.add(localBigshipGeneric);
          this.shadowPairsMap1.put(localBigshipGeneric, null);
        }
      }
    }
  }

  private void shadowPairsRender() {
    int i = this.shadowPairsList1.size();
    if (i == 0) return;
    for (int j = 0; j < i; j++) {
      this.shadowPairsCur1 = ((BigshipGeneric)this.shadowPairsList1.get(j));
      Point3d localPoint3d = this.shadowPairsCur1.pos.getAbsPoint();
      double d1 = localPoint3d.x - shadowPairsR;
      double d2 = localPoint3d.y - shadowPairsR;
      double d3 = localPoint3d.x + shadowPairsR;
      double d4 = localPoint3d.y + shadowPairsR;
      Engine.drawEnv().getFiltered((AbstractCollection)null, d1, d2, d3, d4, 14, this.shadowPairsFilter);
    }
    if (this.shadowPairsList2.size() == 0) return;
    HierMesh.renderShadowPairs(this.shadowPairsList2);
  }

  private void doPreRender3D(Render paramRender)
  {
    paramRender.useClearColor((!this.bDrawLand) || ((RenderContext.texGetFlags() & 0x20) != 0));

    paramRender.getCamera().pos.getRender(this.__p, this.__o);

    if ((!this.bRenderMirror) && (this.iRenderIndx == 0))
    {
      SearchlightGeneric.lightPlanesBySearchlights();

      localObject = paramRender.getCamera().pos.base();
      if (Actor.isValid((Actor)localObject)) {
        ((Actor)localObject).getSpeed(this.__v);
        Camera.SetTargetSpeed((float)this.__v.x, (float)this.__v.y, (float)this.__v.z);
      } else {
        Camera.SetTargetSpeed(0.0F, 0.0F, 0.0F);
      }
    }
    Render.enableFog(this.bEnableFog);

    if ((this.bDrawClouds) && (this.clouds != null)) {
      this.clouds.preRender();
    }
    if (this.bDrawLand) {
      Engine.land().preRender((float)this.__p.z, false);
    }
    this.darkerNight.preRender();

    Object localObject = this.bRenderMirror ? this.drwMirror : this.drwMaster[this.iRenderIndx];
    Engine.drawEnv().preRender(this.__p.x, this.__p.y, this.__p.z, World.MaxVisualDistance, 4, ((DrwArray)localObject).drwSolid, ((DrwArray)localObject).drwTransp, ((DrwArray)localObject).drwShadow, true);

    Engine.drawEnv().preRender(this.__p.x, this.__p.y, this.__p.z, World.MaxLongVisualDistance, 8, ((DrwArray)localObject).drwSolid, ((DrwArray)localObject).drwTransp, ((DrwArray)localObject).drwShadow, false);

    if (!this.bRenderMirror) {
      shadowPairsAdd(((DrwArray)localObject).drwSolid);
      shadowPairsAdd(((DrwArray)localObject).drwTransp);
    }
    if ((!this.bRenderMirror) || (this.viewMirror > 1)) {
      Engine.drawEnv().preRender(this.__p.x, this.__p.y, this.__p.z, World.MaxStaticVisualDistance, 2, ((DrwArray)localObject).drwSolid, ((DrwArray)localObject).drwTransp, ((DrwArray)localObject).drwShadow, false);

      Engine.drawEnv().preRender(this.__p.x, this.__p.y, this.__p.z, World.MaxPlateVisualDistance, 1, ((DrwArray)localObject).drwSolidPlate, ((DrwArray)localObject).drwTranspPlate, ((DrwArray)localObject).drwShadowPlate, true);
    }

    BulletGeneric.preRenderAll();
    if (this.bEnableFog) Render.enableFog(false); 
  }

  private void doRender3D0(Render paramRender) {
    int i = 0;
    Render.enableFog(this.bEnableFog);

    if (this.bDrawLand) {
      Engine.lightEnv().prepareForRender(this.camera3D.pos.getAbsPoint(), 8000.0F);
      i = Engine.land().render0(this.bRenderMirror) != 2 ? 1 : 0;
      LightPoint.clearRender();
    }

    if ((i != 0) && (this.bEnableFog)) Render.enableFog(false);

    DrwArray localDrwArray = this.bRenderMirror ? this.drwMirror : this.drwMaster[this.iRenderIndx];

    plateToRenderArray(localDrwArray.drwSolidPlate, localDrwArray.drwSolid);
    plateToRenderArray(localDrwArray.drwTranspPlate, localDrwArray.drwTransp);
    plateToRenderArray(localDrwArray.drwShadowPlate, localDrwArray.drwShadow);
    MeshShared.renderArray(true);

    paramRender.drawShadow(localDrwArray.drwShadow);

    if ((i != 0) && (this.bEnableFog)) Render.enableFog(true);

    if (this.bDrawLand) {
      Engine.land().render1(this.bRenderMirror);
    }
    int j = gl.GetError();
    if (j != 0)
      System.out.println("***( GL error: " + j + " (render3d0)");
  }

  private void doRender3D1(Render paramRender)
  {
    if ((this.bDrawClouds) && (this.clouds != null) && (RenderContext.cfgSky.get() > 0)) {
      Engine.lightEnv().prepareForRender(this.camera3D.pos.getAbsPoint(), RenderContext.cfgSky.get() * 4000.0F);

      SearchlightGeneric.lightCloudsBySearchlights();
      this.clouds.render();
      LightPoint.clearRender();
    }
    DrwArray localDrwArray = this.bRenderMirror ? this.drwMirror : this.drwMaster[this.iRenderIndx];
    paramRender.draw(localDrwArray.drwSolid, localDrwArray.drwTransp);

    if (!this.bRenderMirror) shadowPairsRender();

    BulletGeneric.renderAll();

    if (this.bEnableFog) {
      Render.flush();
      Render.enableFog(false);
    }

    this.darkerNight.render();
  }

  private void plateToRenderArray(ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    int i = paramArrayList1.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)paramArrayList1.get(j);
      if ((localActor instanceof ActorLandMesh)) {
        paramArrayList2.add(localActor);
      }
      else if (((localActor instanceof ActorMesh)) && ((((ActorMesh)localActor).mesh() instanceof MeshShared))) {
        localActor.pos.getRender(this.__l);
        if (!((MeshShared)((ActorMesh)localActor).mesh()).putToRenderArray(this.__l))
          localActor.draw.render(localActor);
      } else {
        localActor.draw.render(localActor);
      }
    }

    paramArrayList1.clear();
  }

  public void _getAspectViewPort(int paramInt, float[] paramArrayOfFloat)
  {
    paramArrayOfFloat[0] = (paramInt == 1 ? 0.0F : 0.6666667F);
    paramArrayOfFloat[1] = 0.0F;
    paramArrayOfFloat[2] = 0.3333333F;
    paramArrayOfFloat[3] = 1.0F;
  }

  public void _getAspectViewPort(int paramInt, int[] paramArrayOfInt) {
    paramArrayOfInt[0] = (paramInt == 1 ? 0 : 2 * RendersMain.width() / 3);
    paramArrayOfInt[1] = 0;
    paramArrayOfInt[2] = (RendersMain.width() / 3);
    paramArrayOfInt[3] = RendersMain.height();
  }

  private void drawTime()
  {
    if (!this.hud.bDrawAllMessages) return;
    if ((this.bShowTime) || (isDemoPlaying())) {
      int i = TextScr.This().getViewPortWidth();
      long l = Time.current();
      if (NetMissionTrack.isPlaying())
        l -= NetMissionTrack.playingStartTime;
      int j = (int)(l / 1000L % 60L);
      int k = (int)(l / 1000L / 60L);
      if (j > 9) TextScr.output(i - TextScr.font().height() * 3, 5, "" + k + ":" + j); else
        TextScr.output(i - TextScr.font().height() * 3, 5, "" + k + ":0" + j);
    }
  }

  public int mirrorX0()
  {
    return this.render3D0.getViewPortX0(); } 
  public int mirrorY0() { return 0; } 
  public int mirrorWidth() { return 256; } 
  public int mirrorHeight() { return 256;
  }

  public void preRenderHUD()
  {
  }

  public void renderHUD()
  {
  }

  public void renderHUDcontextResize(int paramInt1, int paramInt2)
  {
  }

  public void preRenderMap2D()
  {
  }

  public void renderMap2D()
  {
  }

  public void renderMap2DcontextResize(int paramInt1, int paramInt2)
  {
  }

  private void insertFarActorItem(int paramInt1, int paramInt2, int paramInt3, float paramFloat, String paramString)
  {
    int i = (int)this.iconFarFont.width(paramString);
    FarActorItem localFarActorItem;
    for (int j = 0; j < this.iconFarListLen[this.iRenderIndx]; j++) {
      localFarActorItem = (FarActorItem)this.iconFarList[this.iRenderIndx].get(j);
      if (paramFloat > localFarActorItem.z) {
        if (this.iconFarList[this.iRenderIndx].size() == this.iconFarListLen[this.iRenderIndx]) {
          localFarActorItem = new FarActorItem(paramInt1, paramInt2, paramInt3, i, paramFloat, paramString);
          this.iconFarList[this.iRenderIndx].add(localFarActorItem);
        } else {
          localFarActorItem = (FarActorItem)this.iconFarList[this.iRenderIndx].get(this.iconFarListLen[this.iRenderIndx]);
          localFarActorItem.set(paramInt1, paramInt2, paramInt3, i, paramFloat, paramString);
          this.iconFarList[this.iRenderIndx].remove(this.iconFarListLen[this.iRenderIndx]);
          this.iconFarList[this.iRenderIndx].add(j, localFarActorItem);
        }
        this.iconFarListLen[this.iRenderIndx] += 1;
        return;
      }
    }
    if (this.iconFarList[this.iRenderIndx].size() == this.iconFarListLen[this.iRenderIndx]) {
      localFarActorItem = new FarActorItem(paramInt1, paramInt2, paramInt3, i, paramFloat, paramString);
      this.iconFarList[this.iRenderIndx].add(localFarActorItem);
    } else {
      localFarActorItem = (FarActorItem)this.iconFarList[this.iRenderIndx].get(this.iconFarListLen[this.iRenderIndx]);
      localFarActorItem.set(paramInt1, paramInt2, paramInt3, i, paramFloat, paramString);
    }
    this.iconFarListLen[this.iRenderIndx] += 1;
  }
  private void clipFarActorItems() {
    int i = this.iconFarFont.height();
    for (int j = 0; j < this.iconFarListLen[this.iRenderIndx]; j++) {
      FarActorItem localFarActorItem1 = (FarActorItem)this.iconFarList[this.iRenderIndx].get(j);
      for (int k = j + 1; k < this.iconFarListLen[this.iRenderIndx]; k++) {
        FarActorItem localFarActorItem2 = (FarActorItem)this.iconFarList[this.iRenderIndx].get(k);
        if ((localFarActorItem2.x + localFarActorItem2.dx < localFarActorItem1.x) || 
          (localFarActorItem2.x > localFarActorItem1.x + localFarActorItem1.dx) || 
          (localFarActorItem2.y + i < localFarActorItem1.y) || 
          (localFarActorItem2.y > localFarActorItem1.y + i)) continue;
        this.iconFarList[this.iRenderIndx].remove(k);
        this.iconFarList[this.iRenderIndx].add(localFarActorItem2);
        k--;
        this.iconFarListLen[this.iRenderIndx] -= 1;
      }
    }
  }

  private void clearFarActorItems() {
    this.iconFarListLen[this.iRenderIndx] = 0;
  }

  private boolean isBomb(Actor paramActor)
  {
    return ((paramActor instanceof Bomb)) || ((paramActor instanceof Rocket));
  }

  protected void drawFarActors() {
    if ((Main.state() != null) && (Main.state().id() == 18)) return;
    if (this.iconFarMat == null)
      return;
    this.iconFarFontHeight = this.iconFarFont.height();
    this.iconClipX0 = -2.0D;
    this.iconClipY0 = -1.0D;
    if (this.bRenderMirror) {
      this.iconClipX1 = (this.render2DMirror.getViewPortWidth() + 2.0F);
      this.iconClipY1 = (this.render2DMirror.getViewPortHeight() + 1.0F);
    } else {
      this.iconClipX1 = (this._render2D[this.iRenderIndx].getViewPortWidth() + 2.0F);
      this.iconClipY1 = (this._render2D[this.iRenderIndx].getViewPortHeight() + 1.0F);
    }
    this.iconFarPlayerActor = World.getPlayerAircraft();
    this.iconFarViewActor = viewActor();
    this.iconFarPadlockItem.str = null;
    this.iconFarPadlockActor = getViewPadlockEnemy();
    this._camera3D[this.iRenderIndx].pos.getRender(this.farActorFilter.camp);
    Point3d localPoint3d1 = this.farActorFilter.camp;

    float f1 = Engine.lightEnv().sun().ToLight.z; if (f1 < 0.0F) f1 = 0.0F;
    float f3 = Engine.lightEnv().sun().Ambient + Engine.lightEnv().sun().Diffuze * (0.25F + 0.4F * f1);
    if (RenderContext.cfgHardwareShaders.get() > 0) {
      f1 = Engine.lightEnv().sun().Ambient + f1 * Engine.lightEnv().sun().Diffuze;
      if (f1 > 1.0F) f3 *= f1;
    }
    int j = (int)(127.0F * f3);
    if (j > 255) j = 255;

    iconFarColor = j | j << 8 | j << 16;
    List localList = Engine.targets();
    int k = localList.size();
    for (int m = 0; m < k; m++) {
      Actor localActor = (Actor)localList.get(m);
      Point3d localPoint3d2 = localActor.pos.getAbsPoint();
      double d4 = localPoint3d1.distance(localPoint3d2);
      if (d4 < 25000.0D) {
        this.farActorFilter.isUse(localActor, d4);
      }
    }

    this.iconFarPlayerActor = null;
    this.iconFarViewActor = null;
    this.iconFarPadlockActor = null;
    if (this.iconFarListLen[this.iRenderIndx] != 0) {
      clipFarActorItems();
      for (int i = 0; i < this.iconFarListLen[this.iRenderIndx]; i++) {
        FarActorItem localFarActorItem = (FarActorItem)this.iconFarList[this.iRenderIndx].get(i);
        if (this.bRenderMirror) {
          this.transformMirror.set(localFarActorItem.x - localFarActorItem.dx, localFarActorItem.y, localFarActorItem.z, localFarActorItem.dx);
          this.iconFarFont.transform(this.transformMirror, localFarActorItem.color, localFarActorItem.str);
        } else {
          this.iconFarFont.output(localFarActorItem.color, localFarActorItem.x, localFarActorItem.y, localFarActorItem.z, localFarActorItem.str);
        }
      }
    }
    if (this.iconFarPadlockItem.str != null) {
      if (!this.iconFarPadlockItem.bGround)
      {
        FarActorItem tmp590_587 = this.iconFarPadlockItem; tmp590_587.x = (int)(tmp590_587.x + 1.0F);
        FarActorItem tmp605_602 = this.iconFarPadlockItem; tmp605_602.y = (int)(tmp605_602.y + -7.5F);

        float f2 = 16.0F;

        this.line3XYZ[0] = (float)(this.iconFarPadlockItem.x - f2 * 0.866D);
        this.line3XYZ[1] = (float)(this.iconFarPadlockItem.y - f2 * 0.5D);
        this.line3XYZ[2] = this.iconFarPadlockItem.z;

        this.line3XYZ[3] = (float)(this.iconFarPadlockItem.x + f2 * 0.866D);
        this.line3XYZ[4] = (float)(this.iconFarPadlockItem.y - f2 * 0.5D);
        this.line3XYZ[5] = this.iconFarPadlockItem.z;

        this.line3XYZ[6] = this.iconFarPadlockItem.x;
        this.line3XYZ[7] = (this.iconFarPadlockItem.y + f2);
        this.line3XYZ[8] = this.iconFarPadlockItem.z;
      } else {
        this.camera3D.pos.getRender(this._lineP, this._lineO);
        double d1 = -this._lineO.getKren() * 3.141592653589793D / 180.0D;
        double d2 = Math.sin(d1);
        double d3 = Math.cos(d1);
        FarActorItem tmp837_834 = this.iconFarPadlockItem; tmp837_834.x = (int)(tmp837_834.x + 1.0F);
        FarActorItem tmp852_849 = this.iconFarPadlockItem; tmp852_849.y = (int)(tmp852_849.y + -7.5F);

        float f4 = 16.0F;

        this.line3XYZ[0] = this.iconFarPadlockItem.x;
        this.line3XYZ[1] = this.iconFarPadlockItem.y;
        this.line3XYZ[2] = this.iconFarPadlockItem.z;

        this.line3XYZ[3] = (float)(this.iconFarPadlockItem.x + d3 * f4 * 0.25D + d2 * 1.5D * f4);

        this.line3XYZ[4] = (float)(this.iconFarPadlockItem.y - d2 * f4 * 0.25D + d3 * 1.5D * f4);

        this.line3XYZ[5] = this.iconFarPadlockItem.z;

        this.line3XYZ[6] = (float)(this.iconFarPadlockItem.x - d3 * f4 * 0.25D + d2 * 1.5D * f4);

        this.line3XYZ[7] = (float)(this.iconFarPadlockItem.y + d2 * f4 * 0.25D + d3 * 1.5D * f4);

        this.line3XYZ[8] = this.iconFarPadlockItem.z;
      }

      Render.drawBeginLines(-1);
      Render.drawLines(this.line3XYZ, 3, 1.0F, this.iconFarPadlockItem.color, Mat.TESTZ | Mat.MODULATE | Mat.NOTEXTURE | Mat.BLEND, 5);

      Render.drawEnd();
    }
    clearFarActorItems();
  }

  protected void drawFarActorsInit()
  {
    this.iconFarMat = Mat.New("icons/faractor.mat");
    this.iconFarFont = TTFont.get("arialSmallZ");
    this.iconFarFinger = Finger.Int("iconFar_shortClassName");
  }

  public void initHotKeys()
  {
    CmdEnv.top().setCommand(new CmdExit(), "exit", "exit game");

    HotKeyCmdEnv.setCurrentEnv("hotkeys");

    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "exit") {
      public void begin() { Main.doGameExit();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "ScreenShot") {
      public void begin() { if (Main3D.this.scrShot == null) Main3D.access$2502(Main3D.this, new ScrShot("grab"));

        if (Mission.isNet()) {
          long l = Time.real();
          if (Main3D.this.lastTimeScreenShot + 10000L < l)
            Main3D.access$2602(Main3D.this, l);
          else {
            return;
          }
        }
        Main3D.this.scrShot.grab();
      }
    });
    CmdEnv.top().setCommand(new CmdScreenSequence(), "avi", "start/stop save screen shot sequence");
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "ScreenSequence") {
      public void begin() { if (Main3D.this.screenSequence == null) Main3D.access$2702(Main3D.this, new Main3D.ScreenSequence(Main3D.this));
        Main3D.this.screenSequence.doSave();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "land") {
      public void begin() { if (RTSConf.cur.console.getEnv().levelAccess() == 0)
          Main3D.this.setDrawLand(!Main3D.this.isDrawLand());
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "clouds") {
      public void begin() { if ((Mission.isSingle()) && 
          (RTSConf.cur.console.getEnv().levelAccess() == 0))
          Main3D.this.bDrawClouds = (!Main3D.this.bDrawClouds);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "showTime") {
      public void begin() { if (RTSConf.cur.console.getEnv().levelAccess() == 0)
          Main3D.access$2802(Main3D.this, !Main3D.this.bShowTime);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "pause") {
      public void begin() { if (TimeSkip.isDo()) return;
        if (Time.isEnableChangePause()) {
          Time.setPause(!Time.isPaused());
          if (Config.cur.isSoundUse())
            if (Time.isPaused())
              AudioDevice.soundsOff();
            else
              AudioDevice.soundsOn();
        }
      }
    });
  }

  class CmdExit extends Cmd
  {
    CmdExit()
    {
    }

    public Object exec(CmdEnv paramCmdEnv, Map paramMap)
    {
      Main.doGameExit();
      return null;
    }
  }

  class CmdScreenSequence extends Cmd
  {
    CmdScreenSequence()
    {
    }

    public Object exec(CmdEnv paramCmdEnv, Map paramMap)
    {
      if (Main3D.this.screenSequence == null) Main3D.access$2702(Main3D.this, new Main3D.ScreenSequence(Main3D.this));
      Main3D.this.screenSequence.doSave();
      return null;
    }
  }

  class ScreenSequence extends Actor
  {
    boolean bSave = false;
    ScrShot shot = new ScrShot("s");

    public void doSave()
    {
      this.bSave = (!this.bSave);
    }

    public Object getSwitchListener(Message paramMessage) {
      return this;
    }
    public ScreenSequence() {
      this.flags |= 16384;
      interpPut(new Interpolater(), "grabber", Time.current(), null);
    }
    protected void createActorHashCode() {
      makeActorRealHashCode();
    }

    class Interpolater extends Interpolate
    {
      Interpolater()
      {
      }

      public boolean tick()
      {
        if (Main3D.ScreenSequence.this.bSave)
          Main3D.ScreenSequence.this.shot.grab();
        return true;
      }
    }
  }

  private static class TransformMirror extends TTFontTransform
  {
    private float x0;
    private float y0;
    private float dx;
    private float z0;

    private TransformMirror()
    {
    }

    public void get(float paramFloat1, float paramFloat2, float[] paramArrayOfFloat)
    {
      paramArrayOfFloat[0] = (this.x0 + this.dx - paramFloat1);
      paramArrayOfFloat[1] = (this.y0 + paramFloat2);
      paramArrayOfFloat[2] = this.z0;
    }
    public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
      this.x0 = paramFloat1;
      this.y0 = paramFloat2;
      this.z0 = paramFloat3;
      this.dx = paramFloat4;
    }

    TransformMirror(Main3D.1 param1)
    {
      this();
    }
  }

  class FarActorFilter
    implements ActorFilter
  {
    Point3d p3d = new Point3d();
    Point3d p2d = new Point3d();
    Point3d camp = new Point3d();

    FarActorFilter()
    {
    }

    private String lenToString(int paramInt)
    {
      String str;
      if (paramInt >= 1000) str = paramInt / 1000 + "."; else str = ".";
      paramInt %= 1000;
      if (paramInt < 10) return str + "00";
      if (paramInt < 100) return str + "0" + paramInt / 10;
      return str + paramInt / 10;
    }

    private void drawPointer(int paramInt1, double paramDouble, int paramInt2, int paramInt3) {
      double d1 = Math.atan2(paramInt3, paramInt2);
      double d2 = Math.atan2(this.p2d.y - paramInt3, this.p2d.x - paramInt2);
      if (this.p2d.z > 1.0D)
        if (d2 > 0.0D) d2 = -3.141592653589793D + d2; else
          d2 = 3.141592653589793D + d2;
      double d3;
      double d4;
      if (d2 >= 0.0D) {
        if (d2 <= d1) {
          d3 = paramInt2;
          d4 = Math.tan(d2) * paramInt2;
        } else if (d2 <= 3.141592653589793D - d1) {
          d4 = paramInt3;
          d3 = Math.tan(1.570796326794897D - d2) * paramInt3;
        } else {
          d3 = -paramInt2;
          d4 = -Math.tan(d2) * paramInt2;
        }
      }
      else if (d2 >= -d1) {
        d3 = paramInt2;
        d4 = Math.tan(d2) * paramInt2;
      } else if (d2 >= -3.141592653589793D + d1) {
        d4 = -paramInt3;
        d3 = -Math.tan(1.570796326794897D - d2) * paramInt3;
      } else {
        d3 = -paramInt2;
        d4 = -Math.tan(d2) * paramInt2;
      }

      d3 += paramInt2;
      d4 += paramInt3;
      HUD.addPointer((float)d3, (float)d4, Army.color(paramInt1), (float)((1.0D - paramDouble / Main3D.this.iconDrawMax) * 0.8D + 0.2D), (float)d2);
    }

    public boolean isUse(Actor paramActor, double paramDouble)
    {
      if (paramActor == Main3D.this.iconFarViewActor) return false;
      if (paramDouble <= 5.0D) return false;
      int i = paramActor.getArmy();
      if ((i == 0) && (!Main3D.this.isBomb(paramActor))) return false;
      DotRange localDotRange = i == World.getPlayerArmy() ? Main3D.this.dotRangeFriendly : Main3D.this.dotRangeFoe;
      double d1 = 1.0D;
      double d2 = 0.078D + 1.2F / Main3D.FOVX;
      if (Main3D.FOVX < 24.0F) d2 = 0.16D;
      if (((paramActor instanceof ActorMesh)) && (((ActorMesh)paramActor).mesh() != null)) {
        float f1 = ((ActorMesh)paramActor).mesh().visibilityR();
        if (f1 > 0.0F) {
          if (f1 > 100.0F) {
            float f2 = ((ActorMesh)paramActor).collisionR();
            if (f2 > 0.0F)
              d1 = f2 * d2;
          } else {
            d1 = f1 * d2;
          }
        }
      }
      if ((paramActor instanceof Aircraft)) {
        if (d1 < 0.65D) d1 = 0.65D;
        if (d1 > 2.2D) d1 = 2.2D; 
      }
      else {
        d1 *= 1.2D;
      }
      Main3D.this.iconDrawMax = localDotRange.dot(d1);
      if (paramDouble > Main3D.this.iconDrawMax) return false;

      paramActor.pos.getRender(this.p3d);
      if (!Main3D.this.project2d_cam(this.p3d, this.p2d)) return false;
      if ((this.p2d.z > 1.0D) || (this.p2d.x < Main3D.this.iconClipX0) || (this.p2d.x > Main3D.this.iconClipX1) || (this.p2d.y < Main3D.this.iconClipY0) || (this.p2d.y > Main3D.this.iconClipY1))
      {
        if (Main3D.this.bRenderMirror) return false;
        if (!(paramActor instanceof Aircraft)) return false;
        if (Main3D.this.isViewInsideShow()) return false;
        if (World.cur().diffCur.No_Icons) return false;
        if (Main3D.this.iRenderIndx == 0)
          drawPointer(i, paramDouble, Main3D.this.render2D.getViewPortWidth() / 2, Main3D.this.render2D.getViewPortHeight() / 2);
        return false;
      }
      int j = (int)(this.p2d.x - 1.0D);
      int k = (int)(this.p2d.y - 0.5D);
      int m = 8355711;
      int n = 255;
      int i1 = 0;
      if (Main3D.this.bEnableFog) {
        Render.enableFog(true);
        m = Landscape.getFogRGBA((float)this.p3d.x, (float)this.p3d.y, (float)this.p3d.z);
        i1 = m >>> 24;
        n -= i1;
        Render.enableFog(false);
      }
      int i2 = ((int)(localDotRange.alphaDot(paramDouble * 2.2D, d1) * 255.0D) & 0xFF) << 24;
      int i3 = ((int)(localDotRange.alphaDot(paramDouble, d1) * 255.0D) & 0xFF) << 24;
      int i4 = i2 | (Main3D.iconFarColor & 0xFF) * n + (m & 0xFF) * i1 >>> 8 | (Main3D.iconFarColor >>> 8 & 0xFF) * n + (m >>> 8 & 0xFF) * i1 >>> 8 << 8 | (Main3D.iconFarColor >>> 16 & 0xFF) * n + (m >>> 16 & 0xFF) * i1 >>> 8 << 16;

      int i5 = i3 | (Main3D.iconFarColor >>> 1 & 0x3F) * n + (m >>> 0 & 0xFF) * i1 >>> 8 | (Main3D.iconFarColor >>> 9 & 0x3F) * n + (m >>> 8 & 0xFF) * i1 >>> 8 << 8 | (Main3D.iconFarColor >>> 17 & 0x3F) * n + (m >>> 16 & 0xFF) * i1 >>> 8 << 16;

      if ((paramActor instanceof Aircraft)) {
        if (paramDouble > Main3D.this.iconAirDrawMin) {
          Render.drawTile(j, k + 1.0F, 2.0F, 1.0F, (float)(-this.p2d.z), Main3D.this.iconFarMat, i4, 0.0F, 1.0F, 1.0F, -1.0F);

          Render.drawTile(j, k, 2.0F, 1.0F, (float)(-this.p2d.z), Main3D.this.iconFarMat, i5, 0.0F, 1.0F, 1.0F, -1.0F);
        }

      }
      else if (Main3D.this.isBomb(paramActor)) {
        if (paramDouble > Main3D.this.iconSmallDrawMin) {
          Render.drawTile(j, k + 1.0F, 1.0F, 1.0F, (float)(-this.p2d.z), Main3D.this.iconFarMat, i4, 0.0F, 1.0F, 1.0F, -1.0F);

          Render.drawTile(j, k, 1.0F, 1.0F, (float)(-this.p2d.z), Main3D.this.iconFarMat, i5, 0.0F, 1.0F, 1.0F, -1.0F);
        }

      }
      else if (paramDouble > Main3D.this.iconGroundDrawMin) {
        Render.drawTile(j, k + 1.0F, 2.0F, 1.0F, (float)(-this.p2d.z), Main3D.this.iconFarMat, i4, 0.0F, 1.0F, 1.0F, -1.0F);

        Render.drawTile(j, k, 2.0F, 1.0F, (float)(-this.p2d.z), Main3D.this.iconFarMat, i5, 0.0F, 1.0F, 1.0F, -1.0F);
      }

      k += 8;

      if ((paramActor == Main3D.this.iconFarPadlockActor) && (Main3D.this.iconTypes() != 0)) {
        Main3D.this.iconFarPadlockItem.set(localDotRange.colorIcon(paramDouble, i, n), j, k, 0, (float)(-this.p2d.z), "");
        Main3D.this.iconFarPadlockItem.bGround = (!(paramActor instanceof Aircraft));
        if (World.cur().diffCur.No_Icons) {
          int i6 = ((int)(localDotRange.alphaIcon(paramDouble) * n) & 0xFF) << 24;
          Main3D.this.iconFarPadlockItem.color = (i6 | 0xFF00);
        }
      }

      if (!(paramActor instanceof Aircraft)) return false;
      if (World.cur().diffCur.No_Icons) return false;
      if (Main3D.this.iconTypes() == 0) return false;
      if (paramDouble >= localDotRange.icon()) return false;

      String str1 = null;
      String str2 = null;
      String str3 = null;
      switch (Main3D.this.iconTypes()) {
      case 3:
      default:
        if (paramDouble > localDotRange.type()) break;
        str1 = Property.stringValue(paramActor.getClass(), Main3D.this.iconFarFinger, null);
        if (str1 != null) break;
        str1 = paramActor.getClass().getName();
        int i7 = str1.lastIndexOf('.');
        str1 = str1.substring(i7 + 1);
        Property.set(paramActor.getClass(), "iconFar_shortClassName", str1);
      case 2:
        if (paramDouble <= localDotRange.id())
          str2 = ((Aircraft)paramActor).typedName();
        if ((Mission.isNet()) && (((NetAircraft)paramActor).isNetPlayer()) && (i == World.getPlayerArmy()) && (paramDouble <= localDotRange.name()))
        {
          localObject = ((NetAircraft)paramActor).netUser();
          if (str2 != null)
            str2 = str2 + " " + ((NetUser)localObject).uniqueName();
          else
            str2 = ((NetUser)localObject).uniqueName(); 
        }
      case 1:
      }
      if (paramDouble <= localDotRange.range()) {
        str3 = lenToString((int)paramDouble);
      }
      Object localObject = null;
      if (str3 != null)
        localObject = str3;
      if (str1 != null) {
        if (localObject != null) localObject = (String)localObject + " " + str1; else
          localObject = str1;
      }
      if (str2 != null) {
        if (localObject != null) localObject = (String)localObject + ":" + str2; else {
          localObject = str2;
        }
      }
      if (localObject != null)
        Main3D.this.insertFarActorItem(localDotRange.colorIcon(paramDouble, i, n), j, k, (float)(-this.p2d.z), (String)localObject);
      return false;
    }
  }

  static class FarActorItem
  {
    public int color;
    public int x;
    public int y;
    public int dx;
    public float z;
    public String str;
    public boolean bGround;

    public void set(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, String paramString)
    {
      this.color = paramInt1;
      this.x = paramInt2;
      this.y = paramInt3;
      this.dx = paramInt4;
      this.z = paramFloat;
      this.str = paramString;
    }
    public FarActorItem(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, String paramString) {
      set(paramInt1, paramInt2, paramInt3, paramInt4, paramFloat, paramString);
    }
  }

  public class RenderMap2D extends Render
  {
    protected void contextResize(int paramInt1, int paramInt2)
    {
      super.contextResize(paramInt1, paramInt2);
      Main3D.this.renderMap2DcontextResize(paramInt1, paramInt2);
      if (Main3D.this.land2DText != null)
        Main3D.this.land2DText.contextResized(); 
    }

    public void preRender() {
      Main3D.this.preRenderMap2D();
      if ((Main.state() != null) && (Main.state().id() == 18)) {
        GUIBuilder localGUIBuilder = (GUIBuilder)Main.state();
        localGUIBuilder.builder.preRenderMap2D();
      }
    }

    public void render() {
      if (Main3D.this.land2D != null)
        Main3D.this.land2D.render();
      if ((Main.state() != null) && (Main.state().id() == 18)) {
        GUIBuilder localGUIBuilder = (GUIBuilder)Main.state();
        localGUIBuilder.builder.renderMap2D();
      } else if (Main3D.this.land2DText != null) {
        Main3D.this.land2DText.render();
      }

      Main3D.this.renderMap2D();
    }

    public RenderMap2D(float arg2) {
      this(Engine.rendersMain(), localObject);
    }

    public RenderMap2D(Renders paramFloat, float arg3) {
      super(localObject);
      useClearDepth(false);
      setClearColor(new Color4f(0.5F, 0.78F, 0.92F, 1.0F));
    }
  }

  public class RenderHUD extends Render
  {
    protected void contextResize(int paramInt1, int paramInt2)
    {
      super.contextResize(paramInt1, paramInt2);
      Main3D.this.renderHUDcontextResize(paramInt1, paramInt2);
      Main3D.this.hud.contextResize(paramInt1, paramInt2);
      renders().setCommonClearColor((this.viewPort[0] != 0.0F) || (this.viewPort[1] != 0.0F));
    }
    public void preRender() {
      Main3D.this.hud.preRender();
      Main3D.this.preRenderHUD();
    }

    public void render() {
      Main3D.this.hud.render();
      Main3D.this.renderHUD();
    }

    public RenderHUD(float arg2) {
      this(Engine.rendersMain(), localObject);
    }

    public RenderHUD(Renders paramFloat, float arg3) {
      super(localObject);
      useClearDepth(false);
      useClearColor(false);
    }
  }

  public class RenderCockpitMirror extends Render
  {
    protected void contextResize(int paramInt1, int paramInt2)
    {
      setViewPort(new int[] { Main3D.this.mirrorX0(), Main3D.this.mirrorY0(), Main3D.this.mirrorWidth(), Main3D.this.mirrorHeight() });
      if (this.camera != null)
        ((Camera3D)this.camera).set(((Camera3D)this.camera).FOV(), Main3D.this.mirrorWidth() / Main3D.this.mirrorHeight()); 
    }

    public boolean isShow() {
      if ((Main3D.this.viewMirror > 0) && (Main3D.this.renderCockpit.isShow()))
        return Main3D.this.cockpitCur.isExistMirror();
      return false;
    }
    public void preRender() {
      if ((Actor.isValid(Main3D.this.cockpitCur)) && (Main3D.this.cockpitCur.isFocused()))
        Main3D.this.cockpitCur.preRender(true); 
    }

    public void render() {
      if ((Actor.isValid(Main3D.this.cockpitCur)) && (Main3D.this.cockpitCur.isFocused())) {
        Main3D.this.cockpitCur.render(true);
        Render.flush();
        Main3D.this.cockpitCur.grabMirrorFromScreen(Main3D.this.mirrorX0(), Main3D.this.mirrorY0(), Main3D.this.mirrorWidth(), Main3D.this.mirrorHeight());
      }
    }

    public RenderCockpitMirror(float arg2) {
      super(localObject);
      useClearDepth(true);
      useClearColor(false);
      contextResized();
    }
  }

  public class RenderCockpit extends Render
  {
    int _indx = 0;

    public void preRender() { Main3D.access$602(Main3D.this, this._indx);
      if ((Actor.isValid(Main3D.this.cockpitCur)) && (Main3D.this.cockpitCur.isFocused()))
        Main3D.this.cockpitCur.preRender(false);
      Main3D.access$602(Main3D.this, 0); }

    public void render() {
      Main3D.access$602(Main3D.this, this._indx);
      if ((Actor.isValid(Main3D.this.cockpitCur)) && (Main3D.this.cockpitCur.isFocused())) {
        Main3D.this.cockpitCur.render(false);
      }
      Main3D.access$602(Main3D.this, 0);
    }
    public void getAspectViewPort(float[] paramArrayOfFloat) {
      if (this._indx == 0) { super.getAspectViewPort(paramArrayOfFloat); return; }
      Main3D.this._getAspectViewPort(this._indx, paramArrayOfFloat);
    }
    public void getAspectViewPort(int[] paramArrayOfInt) {
      if (this._indx == 0) { super.getAspectViewPort(paramArrayOfInt); return; }
      Main3D.this._getAspectViewPort(this._indx, paramArrayOfInt);
    }
    public boolean isShow() {
      if (this._indx == 0) return super.isShow();
      return (Config.cur.isUse3Renders()) && (Main3D.this.renderCockpit.isShow());
    }
    public RenderCockpit(int paramFloat, float arg3) {
      this(paramFloat, Engine.rendersMain(), localObject);
    }
    public RenderCockpit(int paramRenders, Renders paramFloat, float arg4) {
      super(localObject);
      this._indx = paramRenders;
      useClearDepth(true);
      useClearColor(false);
      contextResized();
    }
  }

  public class Render2DMirror extends Render
  {
    protected void contextResize(int paramInt1, int paramInt2)
    {
      setViewPort(new int[] { Main3D.this.mirrorX0(), Main3D.this.mirrorY0(), Main3D.this.mirrorWidth(), Main3D.this.mirrorHeight() });
      if (this.camera != null)
        ((CameraOrtho2D)this.camera).set(0.0F, Main3D.this.mirrorWidth(), 0.0F, Main3D.this.mirrorHeight()); 
    }

    public boolean isShow() {
      return (Main3D.this.viewMirror > 0) && (Main3D.this.render3D0.isShow()) && (!Main3D.this.isViewOutside()) && (Main3D.this.cockpitCur.isExistMirror());
    }

    public void render()
    {
      Main3D.access$1202(Main3D.this, true);
      if (Main3D.this.bEnableFog) Render.enableFog(false);
      Main3D.this.drawFarActors();
      if (Main3D.this.bEnableFog) Render.enableFog(true);
      Main3D.access$1202(Main3D.this, false);
    }
    public Render2DMirror(float arg2) {
      super(localObject);
      useClearDepth(false);
      useClearColor(false);
      contextResized();
    }
  }

  public class Render3D1Mirror extends Main3D.Render3DMirror
  {
    public void render()
    {
      Main3D.access$1202(Main3D.this, true);
      Main3D.this.doRender3D1(this);
      Main3D.access$1202(Main3D.this, false);
    }
    public Render3D1Mirror(float arg2) {
      super(localObject);
      useClearColor(false);
      useClearDepth(false);
      useClearStencil(false);
    }
  }

  public class Render3D0Mirror extends Main3D.Render3DMirror
  {
    public void preRender()
    {
      Main3D.access$1202(Main3D.this, true);
      Main3D.this.doPreRender3D(this);
      Main3D.access$1202(Main3D.this, false);
    }
    public void render() {
      this.camera.activateWorldMode(0);
      gl.GetDoublev(2982, Main3D.this._modelMatrix3DMirror);
      gl.GetDoublev(2983, Main3D.this._projMatrix3DMirror);
      gl.GetIntegerv(2978, Main3D.this._viewportMirror);
      this.camera.deactivateWorldMode();
      Main3D.access$1202(Main3D.this, true);
      Main3D.this.doRender3D0(this);
      Main3D.access$1202(Main3D.this, false);
    }
    public Render3D0Mirror(float arg2) {
      super(localObject);
      setClearColor(new Color4f(0.5F, 0.78F, 0.92F, 1.0F));
      useClearStencil(true);
    }
  }

  public class Render3DMirror extends Render
  {
    protected void contextResize(int paramInt1, int paramInt2)
    {
      setViewPort(new int[] { Main3D.this.mirrorX0(), Main3D.this.mirrorY0(), Main3D.this.mirrorWidth(), Main3D.this.mirrorHeight() });
      if (this.camera != null)
        ((Camera3D)this.camera).set(((Camera3D)this.camera).FOV(), Main3D.this.mirrorWidth() / Main3D.this.mirrorHeight()); 
    }

    public boolean isShow() {
      return (Main3D.this.viewMirror > 0) && (Main3D.this.render3D0.isShow()) && (!Main3D.this.isViewOutside()) && (Main3D.this.cockpitCur.isExistMirror());
    }

    public Render3DMirror(float arg2)
    {
      super(localObject);
      contextResized();
    }
  }

  public class Render2D extends Render
  {
    int _indx = 0;

    public void render() { Main3D.access$602(Main3D.this, this._indx);
      if (Main3D.this.bEnableFog) Render.enableFog(false);
      Main3D.this.drawFarActors();
      if (Main3D.this.bEnableFog) Render.enableFog(true);
      Main3D.access$602(Main3D.this, 0); }

    public void getAspectViewPort(float[] paramArrayOfFloat) {
      if (this._indx == 0) { super.getAspectViewPort(paramArrayOfFloat); return; }
      Main3D.this._getAspectViewPort(this._indx, paramArrayOfFloat);
    }
    public void getAspectViewPort(int[] paramArrayOfInt) {
      if (this._indx == 0) { super.getAspectViewPort(paramArrayOfInt); return; }
      Main3D.this._getAspectViewPort(this._indx, paramArrayOfInt);
    }
    public boolean isShow() {
      if (this._indx == 0) return super.isShow();
      if ((Main.state() != null) && (Main.state().id() == 18)) return false;
      return (Config.cur.isUse3Renders()) && (Main3D.this.render2D.isShow());
    }
    public Render2D(int paramFloat, float arg3) {
      this(paramFloat, Engine.rendersMain(), localObject);
    }
    public Render2D(int paramRenders, Renders paramFloat, float arg4) {
      super(localObject);
      this._indx = paramRenders;
      useClearDepth(false);
      useClearColor(false);
      contextResized();
    }
  }

  public class Render3D1 extends Render
  {
    int _indx = 0;

    public void preRender() { if (this._indx == 0)
        Main3D.this.drawTime(); }

    public void render() {
      Main3D.access$602(Main3D.this, this._indx);
      Main3D.this.doRender3D1(this);
      if ((Main.state() != null) && (Main.state().id() == 18) && (Main3D.this.iRenderIndx == 0)) {
        GUIBuilder localGUIBuilder = (GUIBuilder)Main.state();
        localGUIBuilder.builder.render3D();
      }
      Main3D.access$602(Main3D.this, 0);
    }
    public void getAspectViewPort(float[] paramArrayOfFloat) {
      if (this._indx == 0) { super.getAspectViewPort(paramArrayOfFloat); return; }
      Main3D.this._getAspectViewPort(this._indx, paramArrayOfFloat);
    }
    public void getAspectViewPort(int[] paramArrayOfInt) {
      if (this._indx == 0) { super.getAspectViewPort(paramArrayOfInt); return; }
      Main3D.this._getAspectViewPort(this._indx, paramArrayOfInt);
    }
    public boolean isShow() {
      if (this._indx == 0) return super.isShow();
      if ((Main.state() != null) && (Main.state().id() == 18)) return false;
      return (Config.cur.isUse3Renders()) && (Main3D.this.render3D1.isShow());
    }
    public Render3D1(int paramFloat, float arg3) {
      this(paramFloat, Engine.rendersMain(), localObject);
    }
    public Render3D1(int paramRenders, Renders paramFloat, float arg4) {
      super(localObject);
      this._indx = paramRenders;
      useClearColor(false);
      useClearDepth(false);
      useClearStencil(false);
      contextResized();
    }
  }

  public class Render3D0 extends Render
  {
    int _indx = 0;

    public void preRender() { Main3D.access$602(Main3D.this, this._indx);
      if (this._indx == 0)
        Main3D.this.shadowPairsClear();
      Main3D.this.doPreRender3D(this);
      Main3D.access$602(Main3D.this, 0); }

    public void render() {
      Main3D.access$602(Main3D.this, this._indx);
      this.camera.activateWorldMode(0);
      gl.GetDoublev(2982, Main3D.this._modelMatrix3D[Main3D.this.iRenderIndx]);
      gl.GetDoublev(2983, Main3D.this._projMatrix3D[Main3D.this.iRenderIndx]);
      gl.GetIntegerv(2978, Main3D.this._viewport[Main3D.this.iRenderIndx]);
      this.camera.deactivateWorldMode();
      Main3D.this.doRender3D0(this);
      Main3D.access$602(Main3D.this, 0);
    }
    public void getAspectViewPort(float[] paramArrayOfFloat) {
      if (this._indx == 0) { super.getAspectViewPort(paramArrayOfFloat); return; }
      Main3D.this._getAspectViewPort(this._indx, paramArrayOfFloat);
    }
    public void getAspectViewPort(int[] paramArrayOfInt) {
      if (this._indx == 0) { super.getAspectViewPort(paramArrayOfInt); return; }
      Main3D.this._getAspectViewPort(this._indx, paramArrayOfInt);
    }
    public boolean isShow() {
      if (this._indx == 0) return super.isShow();
      if ((Main.state() != null) && (Main.state().id() == 18)) return false;
      return (Config.cur.isUse3Renders()) && (Main3D.this.render3D0.isShow());
    }
    public Render3D0(int paramFloat, float arg3) {
      this(paramFloat, Engine.rendersMain(), localObject);
    }
    public Render3D0(int paramRenders, Renders paramFloat, float arg4) {
      super(localObject);
      this._indx = paramRenders;
      setClearColor(new Color4f(0.5F, 0.78F, 0.92F, 1.0F));
      useClearStencil(true);
      contextResized();
    }
  }

  class DrwArray
  {
    ArrayList drwSolidPlate = new ArrayList();
    ArrayList drwTranspPlate = new ArrayList();
    ArrayList drwShadowPlate = new ArrayList();
    ArrayList drwSolid = new ArrayList();
    ArrayList drwTransp = new ArrayList();
    ArrayList drwShadow = new ArrayList();

    DrwArray()
    {
    }
  }

  class ShadowPairsFilter
    implements ActorFilter
  {
    ShadowPairsFilter()
    {
    }

    public boolean isUse(Actor paramActor, double paramDouble)
    {
      if ((paramActor != Main3D.this.shadowPairsCur1) && ((paramActor instanceof ActorHMesh)) && (paramDouble <= Main3D.shadowPairsR2) && 
        (((ActorHMesh)paramActor).hierMesh() != null)) {
        Main3D.this.shadowPairsList2.add(Main3D.this.shadowPairsCur1.hierMesh());
        Main3D.this.shadowPairsList2.add(((ActorHMesh)paramActor).hierMesh());
      }

      return false;
    }
  }

  public class Render3D0R extends Render
  {
    ArrayList drwSolidL = new ArrayList();
    ArrayList drwTranspL = new ArrayList();
    ArrayList drwSolid = new ArrayList();
    ArrayList drwTransp = new ArrayList();

    public void preRender() { if (Engine.land().ObjectsReflections_Begin(0) == 1) {
        getCamera().pos.getRender(Main3D.this.__p, Main3D.this.__o);
        boolean bool = true;
        if (Landscape.isExistMeshs()) {
          this.drwSolid.clear();
          this.drwTransp.clear();
          Engine.drawEnv().preRender(Main3D.this.__p.x, Main3D.this.__p.y, Main3D.this.__p.z, World.MaxVisualDistance * 0.5F, 1, this.drwSolidL, this.drwTranspL, null, bool);

          int i = this.drwSolidL.size();
          Actor localActor;
          for (int j = 0; j < i; j++) {
            localActor = (Actor)this.drwSolidL.get(j);
            if ((localActor instanceof ActorLandMesh))
              this.drwSolid.add(localActor);
          }
          i = this.drwTranspL.size();
          for (j = 0; j < i; j++) {
            localActor = (Actor)this.drwTranspL.get(j);
            if ((localActor instanceof ActorLandMesh))
              this.drwTransp.add(localActor);
          }
          bool = false;
        }
        Engine.drawEnv().preRender(Main3D.this.__p.x, Main3D.this.__p.y, Main3D.this.__p.z, World.MaxVisualDistance * 0.5F, 14, this.drwSolid, this.drwTransp, null, bool);

        Engine.land().ObjectsReflections_End();
      } }

    public void render() {
      if (Engine.land().ObjectsReflections_Begin(1) == 1)
      {
        draw(this.drwSolid, this.drwTransp);

        Engine.land().ObjectsReflections_End();
      }
    }

    public boolean isShow()
    {
      return (Main3D.this.bDrawLand) && (Main3D.this.render3D0.isShow());
    }
    public Render3D0R(float arg2) {
      super(localObject);
    }
  }

  public static class HookReflection extends HookRender
  {
    public boolean computeRenderPos(Actor paramActor, Loc paramLoc1, Loc paramLoc2)
    {
      computePos(paramActor, paramLoc1, paramLoc2);
      return true;
    }
    public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
      Point3d localPoint3d = paramLoc1.getPoint();
      Orient localOrient = paramLoc1.getOrient();

      paramLoc2.set(paramLoc1);
    }
  }

  public static class Camera3DR extends Camera3D
  {
    public boolean activate(float paramFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
    {
      Engine.land().ObjectsReflections_Begin(0);
      return super.activate(paramFloat, paramInt1 / 2, paramInt2 / 2, paramInt3, paramInt4, paramInt5 / 2, paramInt6 / 2, paramInt7, paramInt8, paramInt9 / 2, paramInt10 / 2);
    }
  }

  public class NetCamera3D extends Camera3D
  {
    public NetCamera3D()
    {
    }

    public void set(float paramFloat)
    {
      super.set(paramFloat);
      Main3D.this._camera3D[1].set(paramFloat);
      Main3D.this._camera3D[1].pos.setRel(new Orient(-paramFloat, 0.0F, 0.0F));
      Main3D.this._camera3D[2].set(paramFloat);
      Main3D.this._camera3D[2].pos.setRel(new Orient(paramFloat, 0.0F, 0.0F));
      Main3D.this._cameraCockpit[1].set(paramFloat);
      Main3D.this._cameraCockpit[1].pos.setRel(new Orient(-paramFloat, 0.0F, 0.0F));
      Main3D.this._cameraCockpit[2].set(paramFloat);
      Main3D.this._cameraCockpit[2].pos.setRel(new Orient(paramFloat, 0.0F, 0.0F));
      Main3D.this.camera3DR.set(paramFloat);
    }
  }
}