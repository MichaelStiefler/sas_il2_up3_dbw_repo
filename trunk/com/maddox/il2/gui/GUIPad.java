package com.maddox.il2.gui;

import com.maddox.JGP.Point2d;
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
import com.maddox.il2.ai.ZutiTargetsSupportMethods;
import com.maddox.il2.ai.ground.TgtShip;
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
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiPadObject;
import com.maddox.il2.game.ZutiRadarRefresh;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.vehicles.artillery.AAA;
import com.maddox.il2.objects.vehicles.radios.Beacon;
import com.maddox.il2.objects.vehicles.radios.TypeHasBeacon;
import com.maddox.il2.objects.vehicles.radios.TypeHasLorenzBlindLanding;
import com.maddox.il2.objects.vehicles.radios.TypeHasRadioStation;
import com.maddox.rts.IniFile;
import com.maddox.util.HashMapExt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class GUIPad
{
  public static boolean bStartMission = true;
  private Main3D main;
  public GUIClient client;
  public GWindowFramed frame;
  public GRegion frameRegion = new GRegion(0.05F, 0.1F, 0.35F, 0.6F);
  public float[] mapView = { 0.09F, 0.09F, 0.82F, 0.82F };
  public GUIRenders renders;
  public GUIRenders renders1;
  public RenderMap2D renderMap2D;
  public CameraOrtho2D cameraMap2D;
  public RenderMap2D1 renderMap2D1;
  public CameraOrtho2D cameraMap2D1;
  public boolean savedUseMesh;
  public int saveIconDx;
  public int saveIconDy;
  protected ArrayList targets = new ArrayList();
  public GMesh mesh;
  public TTFont gridFont;
  public TTFont waypointFont;
  public Mat emptyMat;
  public Mat _iconAir;
  public Mat _iconILS;
  private float bigFontMultip = 1.0F;
  private int lastScale;
  private float[] scale = { 0.064F, 0.032F, 0.016F, 0.008F, 0.004F, 0.002F, 0.001F, 0.0005F, 0.00025F };
  private int scales = this.scale.length;
  private int curScale = 0;
  private int curScaleDirect = 1;

  public boolean bActive = false;

  private float[] line2XYZ = new float[6];
  private int _gridCount;
  private int[] _gridX = new int[20];
  private int[] _gridY = new int[20];
  private int[] _gridVal = new int[20];

  private ArrayList airdrome = new ArrayList();

  int[] _army = new int[Army.amountNet()];
  ArmyAccum armyAccum = new ArmyAccum();

  private ArrayList airs = new ArrayList();

  private Point3f _wayP = new Point3f();
  private Point3f _wayP2 = new Point3f();
  private float[] lineNXYZ = new float[6];
  private int lineNCounter;
  public boolean zutiColorAirfields = true;

  public Map zutiPadObjects = new HashMap();
  private boolean zutiPlayeAcDrawn = false;
  private ArrayList zutiNeutralHomeBases = null;
  protected Mat iconBornPlace = null;

  public boolean isActive()
  {
    return this.bActive;
  }

  public void enter() {
    if (this.bActive) return;
    this.bActive = true;
    GUI.activate(true, false);
    this.client.showWindow();
    float f1 = this.client.root.win.dx;
    float f2 = this.client.root.win.dy;
    this.frameRegion.x = Config.cur.ini.get("game", "mapPadX", this.frameRegion.x);
    this.frameRegion.y = Config.cur.ini.get("game", "mapPadY", this.frameRegion.y);
    this.frame.setPosSize(this.frameRegion.x * f1, this.frameRegion.y * f2, this.frameRegion.dx * f1, this.frameRegion.dy * f2);

    this.cameraMap2D.set(0.0F, this.renderMap2D.getViewPortWidth(), 0.0F, this.renderMap2D.getViewPortHeight());
    this.cameraMap2D1.set(0.0F, this.renderMap2D1.getViewPortWidth(), 0.0F, this.renderMap2D1.getViewPortHeight());

    computeScales();
    scaleCamera();

    if (bStartMission) {
      this.targets.clear();

      ZutiRadarRefresh.update(this.lastScale < this.curScale);
      this.lastScale = this.curScale;

      ZutiSupportMethods.fillNeutralHomeBases(this.zutiNeutralHomeBases);

      ZutiSupportMethods.fillTargets(Mission.cur().sectFile());

      ZutiTargetsSupportMethods.checkForDeactivatedTargets();

      ZutiSupportMethods.fillGroundChiefsArray(this);
    }

    if ((!World.cur().diffCur.No_Player_Icon) || (bStartMission))
    {
      Actor localActor = Actor.getByName("camera");
      float f3;
      float f4;
      if ((Actor.isValid(localActor)) && (!World.cur().diffCur.No_Player_Icon)) {
        Point3d localPoint3d = localActor.pos.getAbsPoint();
        f3 = (float)localPoint3d.x;
        f4 = (float)localPoint3d.y;
      } else {
        f3 = World.land().getSizeX() / 2.0F;
        f4 = World.land().getSizeY() / 2.0F;
      }
      setPosCamera(f3, f4);
      bStartMission = false;
    }

    this.frame.activateWindow();
    this.savedUseMesh = this.main.guiManager.isUseGMeshs();
    this.saveIconDx = IconDraw.scrSizeX();
    this.saveIconDy = IconDraw.scrSizeY();
    this.main.guiManager.setUseGMeshs(false);
  }
  public void leave(boolean paramBoolean) {
    if (!this.bActive) return;
    this.bActive = false;
    float f1 = this.client.root.win.dx;
    float f2 = this.client.root.win.dy;
    this.frameRegion.x = (this.frame.win.x / f1);
    this.frameRegion.y = (this.frame.win.y / f2);
    this.frameRegion.dx = (this.frame.win.dx / f1);
    this.frameRegion.dy = (this.frame.win.dy / f2);
    Config.cur.ini.set("game", "mapPadX", this.frameRegion.x);
    Config.cur.ini.set("game", "mapPadY", this.frameRegion.y);
    this.client.hideWindow();
    Main3D.cur3D().guiManager.setUseGMeshs(this.savedUseMesh);
    IconDraw.setScrSize(this.saveIconDx, this.saveIconDy);
    if (!paramBoolean)
      GUI.unActivate();
  }

  private void setPosCamera(float paramFloat1, float paramFloat2) {
    float f1 = (float)((this.cameraMap2D.right - this.cameraMap2D.left) / this.cameraMap2D.worldScale);
    this.cameraMap2D.worldXOffset = (paramFloat1 - f1 / 2.0F);
    float f2 = (float)((this.cameraMap2D.top - this.cameraMap2D.bottom) / this.cameraMap2D.worldScale);
    this.cameraMap2D.worldYOffset = (paramFloat2 - f2 / 2.0F);
    clipCamera();
  }

  private void scaleCamera() {
    this.cameraMap2D.worldScale = (this.scale[this.curScale] * this.client.root.win.dx / 1024.0F);
  }

  private void clipCamera() {
    if (this.cameraMap2D.worldXOffset < -Main3D.cur3D().land2D.worldOfsX())
      this.cameraMap2D.worldXOffset = (-Main3D.cur3D().land2D.worldOfsX());
    float f1 = (float)((this.cameraMap2D.right - this.cameraMap2D.left) / this.cameraMap2D.worldScale);
    if (this.cameraMap2D.worldXOffset > Main3D.cur3D().land2D.mapSizeX() - Main3D.cur3D().land2D.worldOfsX() - f1) {
      this.cameraMap2D.worldXOffset = (Main3D.cur3D().land2D.mapSizeX() - Main3D.cur3D().land2D.worldOfsX() - f1);
    }
    if (this.cameraMap2D.worldYOffset < -Main3D.cur3D().land2D.worldOfsY())
      this.cameraMap2D.worldYOffset = (-Main3D.cur3D().land2D.worldOfsY());
    float f2 = (float)((this.cameraMap2D.top - this.cameraMap2D.bottom) / this.cameraMap2D.worldScale);
    if (this.cameraMap2D.worldYOffset > Main3D.cur3D().land2D.mapSizeY() - Main3D.cur3D().land2D.worldOfsY() - f2)
      this.cameraMap2D.worldYOffset = (Main3D.cur3D().land2D.mapSizeY() - Main3D.cur3D().land2D.worldOfsY() - f2);
  }

  private void computeScales() {
    float f1 = this.renders.win.dx * 1024.0F / this.client.root.win.dx;
    float f2 = this.renders.win.dy * 768.0F / this.client.root.win.dy;
    int i = 0;
    float f3 = 0.064F;
    float f4;
    float f5;
    while (i < this.scale.length) {
      this.scale[i] = f3;
      f4 = (float)(Main3D.cur3D().land2D.mapSizeX() * f3);
      if (f4 < f1) break;
      f5 = (float)(Main3D.cur3D().land2D.mapSizeY() * f3);
      if (f5 < f2) break;
      f3 /= 2.0F;
      i++;
    }
    this.scales = i;
    if (this.scales < this.scale.length) {
      f4 = f1 / (float)Main3D.cur3D().land2D.mapSizeX();
      f5 = f2 / (float)Main3D.cur3D().land2D.mapSizeY();
      this.scale[i] = f4;
      if (f5 > f4)
        this.scale[i] = f5;
      this.scales = (i + 1);
    }
    if (this.curScale >= this.scales)
      this.curScale = (this.scales - 1);
    if (this.curScale < 0)
      this.curScale = 0;
  }

  private void drawGrid2D() {
    int i = gridStep();
    int j = (int)((this.cameraMap2D.worldXOffset + Main3D.cur3D().land2D.worldOfsX()) / i);
    int k = (int)((this.cameraMap2D.worldYOffset + Main3D.cur3D().land2D.worldOfsY()) / i);
    double d1 = (this.cameraMap2D.right - this.cameraMap2D.left) / this.cameraMap2D.worldScale;
    double d2 = (this.cameraMap2D.top - this.cameraMap2D.bottom) / this.cameraMap2D.worldScale;
    int m = (int)(d1 / i) + 2;
    int n = (int)(d2 / i) + 2;
    float f1 = (float)((j * i - this.cameraMap2D.worldXOffset - Main3D.cur3D().land2D.worldOfsX()) * this.cameraMap2D.worldScale + 0.5D);
    float f2 = (float)((k * i - this.cameraMap2D.worldYOffset - Main3D.cur3D().land2D.worldOfsY()) * this.cameraMap2D.worldScale + 0.5D);
    float f3 = (float)(m * i * this.cameraMap2D.worldScale);
    float f4 = (float)(n * i * this.cameraMap2D.worldScale);
    float f5 = (float)(i * this.cameraMap2D.worldScale);
    this._gridCount = 0;
    Render.drawBeginLines(-1);
    float f6;
    int i2;
    for (int i1 = 0; i1 <= n; i1++) {
      f6 = f2 + i1 * f5;
      i2 = (i1 + k) % 10 == 0 ? 192 : 127;
      this.line2XYZ[0] = f1; this.line2XYZ[1] = f6; this.line2XYZ[2] = 0.0F;
      this.line2XYZ[3] = (f1 + f3); this.line2XYZ[4] = f6; this.line2XYZ[5] = 0.0F;
      Render.drawLines(this.line2XYZ, 2, 1.0F, 0xFF000000 | i2 << 16 | i2 << 8 | i2, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE, 0);

      if (i2 == 192)
        drawGridText(0, (int)f6, (k + i1) * i);
    }
    for (i1 = 0; i1 <= m; i1++) {
      f6 = f1 + i1 * f5;
      i2 = (i1 + j) % 10 == 0 ? 192 : 127;
      this.line2XYZ[0] = f6; this.line2XYZ[1] = f2; this.line2XYZ[2] = 0.0F;
      this.line2XYZ[3] = f6; this.line2XYZ[4] = (f2 + f4); this.line2XYZ[5] = 0.0F;
      Render.drawLines(this.line2XYZ, 2, 1.0F, 0xFF000000 | i2 << 16 | i2 << 8 | i2, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE, 0);

      if (i2 == 192)
        drawGridText((int)f6, 0, (j + i1) * i);
    }
    Render.drawEnd();
    drawGridText();
  }
  private int gridStep() {
    float f1 = this.cameraMap2D.right - this.cameraMap2D.left;
    float f2 = this.cameraMap2D.top - this.cameraMap2D.bottom;
    double d = f1;
    if (f2 < f1) d = f2;
    d /= this.cameraMap2D.worldScale;
    int i = 100000;
    for (int j = 0; (j < 5) && 
      (i * 3 > d); j++)
    {
      i /= 10;
    }
    return i;
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
      this.gridFont.output(-4144960, this._gridX[i] + 2, this._gridY[i] + 2, 0.0F, this._gridVal[i] / 1000 + "." + this._gridVal[i] % 1000 / 100);
    this._gridCount = 0;
  }

  private void drawAirports()
  {
    Mission localMission = Main.cur().mission;

    if (localMission == null) {
      return;
    }
    int i = World.getPlayerArmy();

    Mat localMat = IconDraw.get("icons/runaway.mat");
    AirDrome localAirDrome = null;
    Point3d localPoint3d = null;

    for (int j = 0; j < this.airdrome.size(); j++)
    {
      localAirDrome = (AirDrome)this.airdrome.get(j);

      if ((World.cur().diffCur.RealisticNavigationInstruments) && ((localAirDrome.airport instanceof AirportCarrier)))
      {
        continue;
      }
      if (this.zutiColorAirfields) {
        zutiChangeAirportArmyAndColor(localAirDrome);
      }

      if ((localAirDrome.army != i) && (localMission.zutiRadar_HideUnpopulatedAirstripsFromMinimap))
      {
        continue;
      }
      if ((!Actor.isValid(localAirDrome.airport)) || (!Actor.isAlive(localAirDrome.airport)))
        continue;
      localPoint3d = localAirDrome.airport.pos.getAbsPoint();
      float f1 = (float)((localPoint3d.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
      float f2 = (float)((localPoint3d.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);

      if (localAirDrome.army != i)
      {
        if (localAirDrome.zutiIsOnShip) {
          continue;
        }
        IconDraw.setColor(-1);
      }
      else {
        IconDraw.setColor(localAirDrome.color);
      }
      IconDraw.render(localMat, f1, f2);
    }

    this.zutiColorAirfields = false;
  }

  public int getAirportArmy(Airport paramAirport)
  {
    for (int i = 0; i < this.airdrome.size(); i++)
    {
      AirDrome localAirDrome = (AirDrome)this.airdrome.get(i);
      if (localAirDrome.airport == paramAirport)
        return localAirDrome.army;
    }
    return 0;
  }

  public void fillAirports() {
    this.airdrome.clear();
    ArrayList localArrayList = new ArrayList();
    World.cur(); World.getAirports(localArrayList);
    AirDrome localAirDrome;
    for (int i = 0; i < localArrayList.size(); i++) {
      Airport localAirport = (Airport)localArrayList.get(i);
      localAirDrome = new AirDrome();
      localAirDrome.airport = localAirport;
      if ((localAirport instanceof AirportCarrier)) {
        if (World.cur().diffCur.RealisticNavigationInstruments)
          continue;
        if (Actor.isValid(((AirportCarrier)localAirport).ship())) {
          localAirDrome.army = ((AirportCarrier)localAirport).ship().getArmy();
        }
        localAirDrome.zutiIsOnShip = true;
      }
      this.airdrome.add(localAirDrome);
    }

    Point3d localPoint3d1 = new Point3d();
    for (int j = 0; j < this.airdrome.size(); j++) {
      localAirDrome = (AirDrome)this.airdrome.get(j);
      Point3d localPoint3d2 = localAirDrome.airport.pos.getAbsPoint();
      World.land(); localPoint3d1.set(localPoint3d2.x, localPoint3d2.y, Landscape.HQ((float)localPoint3d2.x, (float)localPoint3d2.y));
      int k = 0;
      if (localAirDrome.army == 0) {
        Engine.collideEnv().getNearestEnemies(localPoint3d1, 2000.0D, 0, this.armyAccum);
        int m = 0;
        for (int n = 1; n < this._army.length; n++) {
          if (m < this._army[n]) {
            m = this._army[n];
            k = n;
          }
          this._army[n] = 0;
        }
        localAirDrome.army = k;
      }
      localAirDrome.color = Army.color(localAirDrome.army);
    }
  }

  private void drawChiefs()
  {
    HashMapExt localHashMapExt = Engine.name2Actor();
    Map.Entry localEntry = localHashMapExt.nextEntry(null);
    while (localEntry != null) {
      Actor localActor = (Actor)localEntry.getValue();
      if ((localActor instanceof Chief)) {
        Mat localMat = localActor.icon;
        if (localMat != null) {
          Point3d localPoint3d = localActor.pos.getAbsPoint();
          float f1 = (float)((localPoint3d.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
          float f2 = (float)((localPoint3d.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
          IconDraw.setColor(Army.color(localActor.getArmy()));
          IconDraw.render(localMat, f1, f2);
        }
      }
      localEntry = localHashMapExt.nextEntry(localEntry);
    }
  }

  private void drawAAAandFillAir() {
    double d1 = this.cameraMap2D.worldXOffset;
    double d2 = this.cameraMap2D.worldYOffset;
    double d3 = this.cameraMap2D.worldXOffset + (this.cameraMap2D.right - this.cameraMap2D.left) / this.cameraMap2D.worldScale;
    double d4 = this.cameraMap2D.worldYOffset + (this.cameraMap2D.top - this.cameraMap2D.bottom) / this.cameraMap2D.worldScale;
    List localList = Engine.targets();
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)localList.get(j);
      Point3d localPoint3d;
      if ((localActor instanceof Aircraft)) {
        if (localActor != World.getPlayerAircraft()) {
          localPoint3d = localActor.pos.getAbsPoint();
          if ((localPoint3d.x >= d1) && (localPoint3d.x <= d3) && (localPoint3d.y >= d2) && (localPoint3d.y <= d4))
            this.airs.add(localActor); 
        }
      } else if ((localActor instanceof AAA)) {
        localPoint3d = localActor.pos.getAbsPoint();
        if ((localPoint3d.x >= d1) && (localPoint3d.x <= d3) && (localPoint3d.y >= d2) && (localPoint3d.y <= d4)) {
          Mat localMat = IconDraw.create(localActor);
          if (localMat != null) {
            float f1 = (float)((localPoint3d.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
            float f2 = (float)((localPoint3d.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
            IconDraw.setColor(Army.color(localActor.getArmy()));
            IconDraw.render(localMat, f1, f2);
          }
        }
      }
    }
  }

  private void drawRadioBeacons()
  {
    double d1 = this.cameraMap2D.worldXOffset;
    double d2 = this.cameraMap2D.worldYOffset;
    double d3 = this.cameraMap2D.worldXOffset + (this.cameraMap2D.right - this.cameraMap2D.left) / this.cameraMap2D.worldScale;
    double d4 = this.cameraMap2D.worldYOffset + (this.cameraMap2D.top - this.cameraMap2D.bottom) / this.cameraMap2D.worldScale;
    Aircraft localAircraft = World.getPlayerAircraft();
    if (!Actor.isValid(localAircraft))
      return;
    ArrayList localArrayList = Main.cur().mission.getBeacons(localAircraft.getArmy());
    if (localArrayList == null)
      return;
    int i = localArrayList.size();
    for (int j = 0; j < i; j++)
    {
      Actor localActor = (Actor)localArrayList.get(j);

      if ((!(localActor instanceof TypeHasBeacon)) || ((localActor instanceof TgtShip)) || ((localActor instanceof TypeHasRadioStation)))
        continue;
      if (localActor.getArmy() == World.getPlayerAircraft().getArmy()) {
        Point3d localPoint3d = localActor.pos.getAbsPoint();
        if ((localPoint3d.x < d1) || (localPoint3d.x > d3) || (localPoint3d.y < d2) || (localPoint3d.y > d4))
          continue;
        float f1 = (float)((localPoint3d.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
        float f2 = (float)((localPoint3d.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
        int k = Army.color(localActor.getArmy());
        float f4;
        if ((localActor instanceof TypeHasLorenzBlindLanding))
        {
          float f3 = localActor.pos.getAbsOrient().azimut() + 90.0F;
          for (f3 = (f3 + 180.0F) % 360.0F; f3 < 0.0F; f3 += 360.0F);
          while (f3 >= 360.0F) f3 -= 360.0F;

          IconDraw.setColor(-1);
          IconDraw.render(this._iconILS, f1, f2, f3 - 90.0F);
          f4 = 20.0F;
          String str1 = Beacon.getBeaconID(j);
          this.gridFont.output(k, f1 + f4, f2 - f4, 0.0F, str1);
          if (this.curScale < 2)
          {
            int m = (int)localActor.pos.getAbsPoint().z;
            str1 = (int)f3 + "°";
            this.gridFont.output(k, f1 + f4, f2 - f4 - 20.0F, 0.0F, str1);
            str1 = m + "m";
            this.gridFont.output(k, f1 + f4, f2 - f4 - 40.0F, 0.0F, str1);
          }
        }
        else
        {
          Mat localMat = IconDraw.create(localActor);
          if (localMat == null)
            continue;
          IconDraw.setColor(k);
          IconDraw.render(localMat, f1, f2);

          f4 = 20.0F;
          float f5 = 15.0F;
          String str2 = Beacon.getBeaconID(j);
          this.gridFont.output(k, f1 + f4, f2 - f5, 0.0F, str2);
        }
      }
    }
  }

  private void drawAir()
  {
    int i = this.airs.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)this.airs.get(j);
      Point3d localPoint3d = localActor.pos.getAbsPoint();
      float f1 = (float)((localPoint3d.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
      float f2 = (float)((localPoint3d.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
      IconDraw.setColor(Army.color(localActor.getArmy()));
      IconDraw.render(this._iconAir, f1, f2, localActor.pos.getAbsOrient().azimut());
    }
    this.airs.clear();
  }

  private Mat getIconAir(int paramInt) {
    String str = null;
    switch (paramInt) { case 0:
      str = "normfly"; break;
    case 1:
      str = "takeoff"; break;
    case 2:
      str = "landing"; break;
    case 3:
      str = "gattack"; break;
    default:
      return null;
    }
    return IconDraw.get("icons/" + str + ".mat");
  }

  private void drawPlayerPath() {
    Autopilotage localAutopilotage = World.getPlayerFM().AP;
    if (localAutopilotage == null) return;
    Way localWay = localAutopilotage.way;
    if (localWay == null) return;
    int i = localWay.Cur();
    int j = localWay.size();
    if ((j <= 0) || (i >= j)) return;
    if (this.lineNXYZ.length / 3 <= j)
      this.lineNXYZ = new float[(j + 1) * 3];
    this.lineNCounter = 0;
    int k = 0;
    Render.drawBeginLines(-1);
    Object localObject;
    while (k < j) {
      localObject = localWay.get(k++);
      ((WayPoint)localObject).getP(this._wayP);
      this.lineNXYZ[(this.lineNCounter * 3 + 0)] = (float)((this._wayP.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
      this.lineNXYZ[(this.lineNCounter * 3 + 1)] = (float)((this._wayP.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
      this.lineNXYZ[(this.lineNCounter * 3 + 2)] = 0.0F;
      this.lineNCounter += 1;
    }
    Render.drawLines(this.lineNXYZ, this.lineNCounter, 2.0F, -16777216, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE | Mat.BLEND, 3);
    WayPoint localWayPoint1;
    if ((!World.cur().diffCur.No_Player_Icon) && (!World.cur().diffCur.RealisticNavigationInstruments))
    {
      localObject = World.getPlayerAircraft().pos.getAbsPoint();
      this.lineNXYZ[0] = (float)((((Point3d)localObject).x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
      this.lineNXYZ[1] = (float)((((Point3d)localObject).y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
      this.lineNXYZ[2] = 0.0F;
      localWayPoint1 = localWay.get(i);
      localWayPoint1.getP(this._wayP);
      this.lineNXYZ[3] = (float)((this._wayP.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
      this.lineNXYZ[4] = (float)((this._wayP.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
      this.lineNXYZ[5] = 0.0F;
      Render.drawLines(this.lineNXYZ, 2, 2.0F, -16777216, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE | Mat.BLEND, 3);
    }

    float f1 = 0.0F;
    Render.drawEnd();
    k = 0;
    while (k < j) {
      localWayPoint1 = localWay.get(k++);
      localWayPoint1.getP(this._wayP);
      float f2 = (float)((this._wayP.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
      float f3 = (float)((this._wayP.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);
      IconDraw.render(getIconAir(localWayPoint1.Action), f2, f3);

      if ((this.curScale < (int)(4.0F - this.bigFontMultip)) && (k < j))
      {
        WayPoint localWayPoint2 = localWay.get(k);
        localWayPoint2.getP(this._wayP2);
        this._wayP.sub(this._wayP2);
        float f4 = 57.324841F * (float)Math.atan2(this._wayP.x, this._wayP.y);

        for (f4 = (f4 + 180.0F) % 360.0F; f4 < 0.0F; f4 += 360.0F);
        while (f4 >= 360.0F) f4 -= 360.0F;

        f4 = Math.round(f4);

        double d = Math.sqrt(this._wayP.y * this._wayP.y + this._wayP.x * this._wayP.x) / 1000.0D;

        if (d < 1.0D)
          continue;
        String str = "km";

        if ((HUD.drawSpeed() == 2) || (HUD.drawSpeed() == 3))
        {
          d *= 0.5399569869041443D;

          str = "nm";
        }

        d = Math.round(d);

        float f5 = 0.0F;
        float f6 = 0.0F;

        if ((f4 >= 0.0F) && (f4 < 90.0F))
        {
          f5 = 15.0F;
          f6 = -20.0F;
          if ((f1 >= 270.0F) && (f1 <= 360.0F))
          {
            f5 = -35.0F * this.bigFontMultip;
            f6 = 30.0F * this.bigFontMultip;
          }
        }
        else if ((f4 >= 90.0F) && (f4 < 180.0F))
        {
          f5 = 15.0F;
          f6 = 30.0F * this.bigFontMultip;
          if ((f1 >= 180.0F) && (f1 < 270.0F))
          {
            f5 = -35.0F * this.bigFontMultip;
            f6 = -20.0F;
          }
        }
        else if ((f4 >= 180.0F) && (f4 < 270.0F))
        {
          f5 = -35.0F * this.bigFontMultip;
          f6 = 30.0F * this.bigFontMultip;
          if ((f1 >= 90.0F) && (f1 < 180.0F))
          {
            f5 = 15.0F;
            f6 = 30.0F * this.bigFontMultip;
          }
        }
        else if ((f4 >= 270.0F) && (f4 <= 360.0F))
        {
          f5 = -35.0F * this.bigFontMultip;
          f6 = -20.0F;
          if ((f1 >= 0.0F) && (f1 < 90.0F))
          {
            f5 = 15.0F;
            f6 = -20.0F;
          }
        }
        f1 = f4;

        this.waypointFont.output(-16777216, f2 + f5, f3 + f6 - 0.0F, 0.0F, "" + k);
        this.waypointFont.output(-16777216, f2 + f5, f3 + f6 - 12.0F * this.bigFontMultip, 0.0F, (int)f4 + "°");
        this.waypointFont.output(-16777216, f2 + f5, f3 + f6 - 24.0F * this.bigFontMultip, 0.0F, (int)d + str);
      }
    }
  }

  public GUIPad THIS()
  {
    return this;
  }

  public GUIPad(GWindowRoot paramGWindowRoot)
  {
    this.iconBornPlace = IconDraw.get("icons/born.mat");

    this.client = ((GUIClient)paramGWindowRoot.create(new GUIClient() {
      public void render() {
        int i = GUIPad.this.client.root.C.alpha;
        GUIPad.this.client.root.C.alpha = 255;
        super.render();
        GUIPad.this.client.root.C.alpha = i;
      }
    }));
    this.frame = ((GWindowFramed)this.client.create(0.0F, 0.0F, 1.0F, 1.0F, false, new GWindowFramed() {
      public void resized() {
        super.resized();
        if (GUIPad.this.renders != null) {
          GUIPad.this.renders.setPosSize(this.win.dx * GUIPad.this.mapView[0], this.win.dy * GUIPad.this.mapView[1], this.win.dx * GUIPad.this.mapView[2], this.win.dy * GUIPad.this.mapView[3]);
        }
        if (GUIPad.this.renders1 != null)
          GUIPad.this.renders1.setPosSize(0.0F, 0.0F, this.win.dx, this.win.dy);
      }

      public void render()
      {
      }
    }));
    this.frame.bSizable = false;
    this.renders = new GUIRenders(this.frame, 0.0F, 0.0F, 1.0F, 1.0F, false);
    this.renders1 = new GUIRenders(this.frame, 0.0F, 0.0F, 1.0F, 1.0F, false) {
      boolean bLPressed = false;
      boolean bRPressed = false;

      public void mouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2) { if (paramInt == 0) {
          this.bLPressed = paramBoolean;
          this.mouseCursor = (this.bLPressed ? 3 : 1);
        } else if ((paramInt == 1) && (GUIPad.this.scales > 1)) {
          this.bRPressed = paramBoolean;
          if ((this.bRPressed) && (!this.bLPressed)) {
            paramFloat1 -= GUIPad.this.THIS().renders.win.x;
            paramFloat2 -= GUIPad.this.THIS().renders.win.y;
            float f1 = (float)(GUIPad.this.cameraMap2D.worldXOffset + paramFloat1 / GUIPad.this.cameraMap2D.worldScale);
            float f2 = (float)(GUIPad.this.cameraMap2D.worldYOffset + (GUIPad.this.THIS().renders.win.dy - paramFloat2 - 1.0F) / GUIPad.this.cameraMap2D.worldScale);

            GUIPad.access$112(GUIPad.this, GUIPad.this.curScaleDirect);
            if (GUIPad.this.curScaleDirect < 0) {
              if (GUIPad.this.curScale < 0) {
                GUIPad.access$102(GUIPad.this, 1);
                GUIPad.access$1402(GUIPad.this, 1);
              }
            }
            else if (GUIPad.this.curScale >= GUIPad.this.scales) {
              GUIPad.access$102(GUIPad.this, GUIPad.this.scales - 2);
              GUIPad.access$1402(GUIPad.this, -1);
            }

            GUIPad.this.scaleCamera();
            f1 = (float)(f1 - (paramFloat1 - GUIPad.this.THIS().renders.win.dx / 2.0F) / GUIPad.this.cameraMap2D.worldScale);
            f2 = (float)(f2 + (paramFloat2 - GUIPad.this.THIS().renders.win.dy / 2.0F) / GUIPad.this.cameraMap2D.worldScale);
            GUIPad.this.setPosCamera(f1, f2);
          }
        } }

      public void mouseMove(float paramFloat1, float paramFloat2) {
        if ((this.bLPressed) && (this.bRPressed)) {
          GUIPad.this.frame.setPos(GUIPad.this.frame.win.x + this.root.mouseStep.dx, GUIPad.this.frame.win.y + this.root.mouseStep.dy);
        } else if (this.bLPressed) {
          GUIPad.this.cameraMap2D.worldXOffset -= this.root.mouseStep.dx / GUIPad.this.cameraMap2D.worldScale;
          GUIPad.this.cameraMap2D.worldYOffset += this.root.mouseStep.dy / GUIPad.this.cameraMap2D.worldScale;
          GUIPad.this.clipCamera();
        }
      }
    };
    this.cameraMap2D = new CameraOrtho2D();
    this.cameraMap2D.worldScale = this.scale[this.curScale];
    this.renderMap2D = new RenderMap2D(this.renders.renders, 1.0F);
    this.renderMap2D.setCamera(this.cameraMap2D);
    this.renderMap2D.setShow(true);
    this.cameraMap2D1 = new CameraOrtho2D();
    this.renderMap2D1 = new RenderMap2D1(this.renders1.renders, 1.0F);
    this.renderMap2D1.setCamera(this.cameraMap2D1);
    this.renderMap2D1.setShow(true);

    LightEnvXY localLightEnvXY = new LightEnvXY();
    this.renderMap2D.setLightEnv(localLightEnvXY);
    this.renderMap2D1.setLightEnv(localLightEnvXY);

    localLightEnvXY.sun().setLight(0.5F, 0.5F, 1.0F, 1.0F, 1.0F, 0.8F);
    Vector3f localVector3f = new Vector3f(1.0F, -2.0F, -1.0F); localVector3f.normalize();
    localLightEnvXY.sun().set(localVector3f);

    this.gridFont = TTFont.font[1];
    if (World.cur().smallMapWPLabels)
    {
      this.waypointFont = TTFont.font[0];
      this.bigFontMultip = 1.0F;
    }
    else
    {
      this.waypointFont = TTFont.font[1];
      this.bigFontMultip = 2.0F;
    }
    this.mesh = GMesh.New("gui/game/pad/mono.sim");
    this._iconAir = Mat.New("icons/plane.mat");
    this.emptyMat = Mat.New("icons/empty.mat");
    this._iconILS = Mat.New("icons/ILS.mat");
    this.main = Main3D.cur3D();

    this.client.hideWindow();
  }

  public void zutiChangeAirportArmyAndColor(BornPlace paramBornPlace)
  {
    if (paramBornPlace == null) {
      return;
    }
    double d1 = paramBornPlace.r * paramBornPlace.r;
    int i = this.airdrome.size();

    for (int j = 0; j < i; j++)
    {
      AirDrome localAirDrome = (AirDrome)this.airdrome.get(j);
      if (localAirDrome.airport == null)
        continue;
      Point3d localPoint3d = localAirDrome.airport.pos.getAbsPoint();
      double d2 = (localPoint3d.x - paramBornPlace.place.x) * (localPoint3d.x - paramBornPlace.place.x) + (localPoint3d.y - paramBornPlace.place.y) * (localPoint3d.y - paramBornPlace.place.y);
      if (d2 > d1) {
        continue;
      }
      localAirDrome.army = paramBornPlace.army;
      localAirDrome.color = Army.color(localAirDrome.army);
    }
  }

  private void zutiDrawAirInterval()
  {
    boolean bool = World.cur().diffCur.NoMinimapPath;
    Main.cur().mission.getClass(); int i = 0;
    try
    {
      Iterator localIterator = this.zutiPadObjects.keySet().iterator();
      ZutiPadObject localZutiPadObject = null;
      Point3d localPoint3d = null;

      while (localIterator.hasNext())
      {
        localZutiPadObject = (ZutiPadObject)this.zutiPadObjects.get(localIterator.next());

        if ((localZutiPadObject == null) || (localZutiPadObject.getOwner() == null) || (!localZutiPadObject.isAlive())) {
          continue;
        }
        if ((!bool) && (localZutiPadObject.isPlayerPlane()))
        {
          localZutiPadObject.setVisibleForPlayerArmy(true);
        }

        if (!localZutiPadObject.isVisibleForPlayerArmy())
          continue;
        localPoint3d = localZutiPadObject.getPosition();
        float f1 = (float)((localPoint3d.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
        float f2 = (float)((localPoint3d.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);

        if (i != 0)
          IconDraw.setColor(-1);
        else {
          IconDraw.setColor(Army.color(localZutiPadObject.getArmy()));
        }
        if (localZutiPadObject.isPlayerPlane())
        {
          this.zutiPlayeAcDrawn = true;
          IconDraw.setColor(-1);
        }

        switch (localZutiPadObject.type)
        {
        case 0:
        case 3:
          IconDraw.render(this._iconAir, f1, f2, localZutiPadObject.getAzimut());
          break;
        case 1:
        case 2:
        case 4:
        case 5:
          Mat localMat = localZutiPadObject.getIcon();
          if (localMat == null) break;
          IconDraw.render(localMat, f1, f2);
        }

      }

    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void zutiChangeAirportArmyAndColor(AirDrome paramAirDrome) {
    if ((paramAirDrome == null) || (World.cur().bornPlaces == null)) {
      return;
    }
    ArrayList localArrayList = World.cur().bornPlaces;
    int i = localArrayList.size();
    for (int j = 0; j < i; j++)
    {
      BornPlace localBornPlace = (BornPlace)localArrayList.get(j);
      if (localBornPlace == null)
        continue;
      double d1 = localBornPlace.r * localBornPlace.r;

      Point3d localPoint3d = paramAirDrome.airport.pos.getAbsPoint();
      double d2 = (localPoint3d.x - localBornPlace.place.x) * (localPoint3d.x - localBornPlace.place.x) + (localPoint3d.y - localBornPlace.place.y) * (localPoint3d.y - localBornPlace.place.y);
      if (d2 > d1)
        continue;
      paramAirDrome.army = localBornPlace.army;
      paramAirDrome.color = Army.color(paramAirDrome.army);
    }
  }

  private void zutiDrawBornPlaces()
  {
    Mission localMission = Main.cur().mission;

    if (localMission != null) { localMission.getClass(); if (this.iconBornPlace != null); } else { return;
    }
    if ((this.zutiNeutralHomeBases != null) && (this.zutiNeutralHomeBases.size() != 0))
    {
      int i = this.zutiNeutralHomeBases.size();
      BornPlace localBornPlace = null;

      for (int j = 0; j < i; j++)
      {
        localBornPlace = (BornPlace)this.zutiNeutralHomeBases.get(j);

        if ((localBornPlace.army == 1) || (localBornPlace.army == 2))
        {
          this.zutiNeutralHomeBases.remove(localBornPlace);

          break;
        }

        IconDraw.setColor(Army.color(localBornPlace.army));

        float f1 = (float)((localBornPlace.place.x - this.cameraMap2D.worldXOffset) * this.cameraMap2D.worldScale);
        float f2 = (float)((localBornPlace.place.y - this.cameraMap2D.worldYOffset) * this.cameraMap2D.worldScale);

        IconDraw.render(this.iconBornPlace, f1, f2);
      }
    }
  }

  public class RenderMap2D extends Render
  {
    public void preRender()
    {
      Front.preRender(false);
    }

    public void render()
    {
      ZutiRadarRefresh.update(GUIPad.this.lastScale < GUIPad.this.curScale);
      GUIPad.access$002(GUIPad.this, GUIPad.this.curScale);
      GUIPad.access$202(GUIPad.this, false);
      try
      {
        if (GUIPad.this.main.land2D != null)
          GUIPad.this.main.land2D.render(0.9F, 0.9F, 0.9F, 1.0F);
        if (GUIPad.this.main.land2DText != null)
          GUIPad.this.main.land2DText.render();
        GUIPad.this.drawGrid2D();
        Front.render(false);
        int i = (int)Math.round(Mission.ZUTI_ICON_SIZE * GUIPad.this.client.root.win.dx / 1024.0D);

        IconDraw.setScrSize(i, i);

        Mission localMission = Main.cur().mission;

        ZutiSupportMethods.drawTargets(GUIPad.this.renders, GUIPad.this.gridFont, GUIPad.this.emptyMat, GUIPad.this.cameraMap2D);

        GUIPad.this.drawAirports();

        GUIPad.this.zutiDrawBornPlaces();

        if (!World.cur().diffCur.No_Map_Icons)
        {
          GUIPad.this.drawChiefs();
          GUIPad.this.drawAAAandFillAir();
          GUIPad.this.drawAir();
        }

        GUIPad.this.drawRadioBeacons();
        Aircraft localAircraft;
        if (!World.cur().diffCur.NoMinimapPath)
        {
          localAircraft = World.getPlayerAircraft();
          if (Actor.isValid(localAircraft))
          {
            IconDraw.setColor(-16711681);
            GUIPad.this.drawPlayerPath();
          }

        }

        if ((World.cur().diffCur.No_Map_Icons) && (localMission != null) && (localMission.zutiRadar_PlayerSideHasRadars))
        {
          GUIPad.this.zutiDrawAirInterval();
        }

        if (!World.cur().diffCur.No_Player_Icon)
        {
          localAircraft = World.getPlayerAircraft();
          if (Actor.isValid(localAircraft))
          {
            Point3d localPoint3d = localAircraft.pos.getAbsPoint();
            float f1 = (float)((localPoint3d.x - GUIPad.this.cameraMap2D.worldXOffset) * GUIPad.this.cameraMap2D.worldScale);
            float f2 = (float)((localPoint3d.y - GUIPad.this.cameraMap2D.worldYOffset) * GUIPad.this.cameraMap2D.worldScale);
            IconDraw.setColor(-1);
            IconDraw.render(GUIPad.this._iconAir, f1, f2, localAircraft.pos.getAbsOrient().azimut());
          }
        }

        SquareLabels.draw(GUIPad.this.cameraMap2D, Main3D.cur3D().land2D.worldOfsX(), Main3D.cur3D().land2D.worldOfsX(), Main3D.cur3D().land2D.mapSizeX());
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }

    public RenderMap2D(Renders paramFloat, float arg3) {
      super(localObject);
      useClearDepth(false);
      useClearColor(false);
    }
  }

  public class RenderMap2D1 extends Render
  {
    public void preRender()
    {
    }

    public void render()
    {
      GUIPad.this.renders1.draw(0.0F, 0.0F, GUIPad.this.renders1.win.dx, GUIPad.this.renders1.win.dy, GUIPad.this.mesh);
    }

    public RenderMap2D1(Renders paramFloat, float arg3) {
      super(localObject);
      useClearDepth(false);
      useClearColor(false);
    }
  }

  class ArmyAccum
    implements Accumulator
  {
    ArmyAccum()
    {
    }

    public void clear()
    {
    }

    public boolean add(Actor paramActor, double paramDouble)
    {
      GUIPad.this._army[paramActor.getArmy()] += 1;
      return true;
    }
  }

  class AirDrome
  {
    Airport airport;
    int color;
    int army;
    boolean zutiIsOnShip = false;

    AirDrome()
    {
    }
  }
}