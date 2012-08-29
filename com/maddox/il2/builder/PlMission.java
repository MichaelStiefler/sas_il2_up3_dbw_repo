package com.maddox.il2.builder;

import com.maddox.TexImage.TexImage;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GFileFilter;
import com.maddox.gwindow.GFileFilterName;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowBoxSeparate;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFileBox;
import com.maddox.gwindow.GWindowFileBoxExec;
import com.maddox.gwindow.GWindowFileOpen;
import com.maddox.gwindow.GWindowFileSaveAs;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowHSeparate;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRootMenu;
import com.maddox.gwindow.GWindowStatusBar;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.EffClouds;
import com.maddox.il2.engine.Land2DText;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.effects.SpritesFog;
import com.maddox.rts.HomePath;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PlMission extends Plugin
{
  protected static PlMission cur;
  protected int missionArmy = 1;

  private int cloudType = 0;
  private float cloudHeight = 1000.0F;

  private float windDirection = 0.0F;
  private float windVelocity = 0.0F;
  private int gust = 0;
  private int turbulence = 0;
  private int day = 15;
  private int month = World.land().config.month;
  private int year = 1940;
  private String[] _yearKey = { "1930", "1931", "1932", "1933", "1934", "1935", "1936", "1937", "1938", "1939", "1940", "1941", "1942", "1943", "1944", "1945", "1946", "1947", "1948", "1949", "1950", "1951", "1952", "1953", "1954", "1955", "1956", "1957", "1958", "1959", "1960" };

  private String[] _dayKey = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

  private String[] _monthKey = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
  private static final int RADAR_SHIPRADAR_MAXRANGE_MIN = 1;
  private static final int RADAR_SHIPRADAR_MAXRANGE_MAX = 99999;
  private static final int RADAR_SHIPRADAR_MINHEIGHT_MIN = 0;
  private static final int RADAR_SHIPRADAR_MINHEIGHT_MAX = 99999;
  private static final int RADAR_SHIPRADAR_MAXHEIGHT_MIN = 1000;
  private static final int RADAR_SHIPRADAR_MAXHEIGHT_MAX = 99999;
  private static final int RADAR_SHIPSMALLRADAR_MAXRANGE_MIN = 1;
  private static final int RADAR_SHIPSMALLRADAR_MAXRANGE_MAX = 99999;
  private static final int RADAR_SHIPSMALLRADAR_MINHEIGHT_MIN = 0;
  private static final int RADAR_SHIPSMALLRADAR_MINHEIGHT_MAX = 99999;
  private static final int RADAR_SHIPSMALLRADAR_MAXHEIGHT_MIN = 1000;
  private static final int RADAR_SHIPSMALLRADAR_MAXHEIGHT_MAX = 99999;
  private static final int RADAR_SCOUTRADAR_MAXRANGE_MIN = 1;
  private static final int RADAR_SCOUTRADAR_MAXRANGE_MAX = 99999;
  private static final int RADAR_SCOUTRADAR_DELTAHEIGHT_MIN = 100;
  private static final int RADAR_SCOUTRADAR_DELTAHEIGHT_MAX = 99999;
  private static final int RADAR_SCOUTGROUNDOBJECT_ALPHA_MIN = 1;
  private static final int RADAR_SCOUTGROUNDOBJECT_ALPHA_MAX = 11;
  private static final int RADAR_REFRESHINTERVAL_MIN = 0;
  private static final int RADAR_REFRESHINTERVAL_MAX = 99999;
  private static final float MISC_BOMBSCAT1_CRATERSVISIBILITYMULTIPLIER_MIN = 1.0F;
  private static final float MISC_BOMBSCAT1_CRATERSVISIBILITYMULTIPLIER_MAX = 99999.0F;
  private static final float MISC_BOMBSCAT2_CRATERSVISIBILITYMULTIPLIER_MIN = 1.0F;
  private static final float MISC_BOMBSCAT2_CRATERSVISIBILITYMULTIPLIER_MAX = 99999.0F;
  private static final float MISC_BOMBSCAT3_CRATERSVISIBILITYMULTIPLIER_MIN = 1.0F;
  private static final float MISC_BOMBSCAT3_CRATERSVISIBILITYMULTIPLIER_MAX = 99999.0F;
  private static final int RT_BIGSHIP_MIN = 0;
  private static final int RT_BIGSHIP_MAX = 1200000;
  private static final int RT_SHIP_MIN = 0;
  private static final int RT_SHIP_MAX = 1200000;
  private static final int RT_AEROANCHORED_MIN = 0;
  private static final int RT_AEROANCHORED_MAX = 1200000;
  private static final int RT_ARTILLERY_MIN = 0;
  private static final int RT_ARTILLERY_MAX = 1200000;
  private static final int RT_SEARCHLIGHT_MIN = 0;
  private static final int RT_SEARCHLIGHT_MAX = 1200000;
  private boolean bChanged = false;
  private String missionFileName;
  private boolean bReload = false;
  PlMapLoad pluginMapLoad;
  WConditions wConditions;
  GWindowMenuItem mConditions;
  WFoW wFoW;
  GWindowMenuItem mFoW;
  WCraters wCraters;
  GWindowMenuItem mCraters;
  WRespawnTime wRespawnTime;
  GWindowMenuItem mRespawnTime;
  WMisc wMisc;
  GWindowMenuItem mMisc;
  GWindowMenuItem viewBridge;
  GWindowMenuItem viewRunaway;
  GWindowMenuItem viewName;
  GWindowMenuItem viewTime;
  GWindowMenuItem[] viewArmy;
  private String lastOpenFile;
  private GWindowMessageBox _loadMessageBox;
  private String _loadFileName;
  private int zutiRadar_RefreshInterval;
  private boolean zutiRadar_DisableVectoring;
  private boolean zutiRadar_EnableTowerCommunications;
  private boolean zutiRadar_HideUnpopulatedAirstripsFromMinimap;
  private boolean zutiRadar_ShipsAsRadar;
  private int zutiRadar_ShipRadar_MaxRange;
  private int zutiRadar_ShipRadar_MinHeight;
  private int zutiRadar_ShipRadar_MaxHeight;
  private int zutiRadar_ShipSmallRadar_MaxRange;
  private int zutiRadar_ShipSmallRadar_MinHeight;
  private int zutiRadar_ShipSmallRadar_MaxHeight;
  private boolean zutiRadar_ScoutsAsRadar;
  private int zutiRadar_ScoutRadar_MaxRange;
  private int zutiRadar_ScoutRadar_DeltaHeight;
  private String zutiRadar_ScoutRadarType_Red;
  private String zutiRadar_ScoutRadarType_Blue;
  private int zutiRadar_ScoutGroundObjects_Alpha;
  private boolean zutiRadar_ScoutCompleteRecon;
  private boolean zutiMisc_DisableAIRadioChatter;
  private boolean zutiMisc_DespawnAIPlanesAfterLanding;
  private boolean zutiMisc_HidePlayersCountOnHomeBase;
  private float zutiMisc_BombsCat1_CratersVisibilityMultiplier;
  private float zutiMisc_BombsCat2_CratersVisibilityMultiplier;
  private float zutiMisc_BombsCat3_CratersVisibilityMultiplier;
  private int respawnTime_Bigship;
  private int respawnTime_Ship;
  private int respawnTime_Aeroanchored;
  private int respawnTime_Artillery;
  private int respawnTime_Searchlight;
  private Zuti_WManageAircrafts zuti_manageAircrafts;

  public static void setChanged()
  {
    if (cur != null)
      cur.bChanged = true; 
  }

  public static boolean isChanged() {
    if (cur != null)
      return cur.bChanged;
    return false;
  }

  public static String missionFileName()
  {
    if (cur == null) return null;
    return cur.missionFileName;
  }
  public static void doMissionReload() {
    if (cur == null) return;
    if (!cur.bReload) return;
    cur.bReload = false;
    cur.doLoadMission("missions/" + cur.missionFileName);
  }

  public boolean load(String paramString) {
    builder.deleteAll();
    SectFile localSectFile = new SectFile(paramString, 0);
    int i = localSectFile.sectionIndex("MAIN");
    if (i < 0) {
      builder.tipErr("MissionLoad: '" + paramString + "' - section MAIN not found");
      return false;
    }
    int j = localSectFile.varIndex(i, "MAP");
    if (j < 0) {
      builder.tipErr("MissionLoad: '" + paramString + "' - in section MAIN line MAP not found");
      return false;
    }
    String str1 = localSectFile.value(i, j);
    PlMapLoad.Land localLand = PlMapLoad.getLandForFileName(str1);
    if (localLand == PlMapLoad.getLandLoaded()) {
      World.cur().statics.restoreAllBridges();
      World.cur().statics.restoreAllHouses();
    }
    else if (!this.pluginMapLoad.mapLoad(localLand)) {
      builder.tipErr("MissionLoad: '" + paramString + "' - tirrain '" + str1 + "' not loaded");
      return false;
    }

    int k = localSectFile.get("MAIN", "TIMECONSTANT", 0, 0, 1);
    World.cur().setTimeOfDayConstant(k == 1);
    World.setTimeofDay(localSectFile.get("MAIN", "TIME", 12.0F, 0.0F, 23.99F));

    int m = localSectFile.get("MAIN", "WEAPONSCONSTANT", 0, 0, 1);
    World.cur().setWeaponsConstant(m == 1);

    String str2 = localSectFile.get("MAIN", "player", (String)null);
    Path.playerNum = localSectFile.get("MAIN", "playerNum", 0, 0, 3);
    this.missionArmy = localSectFile.get("MAIN", "army", 1, 1, 2);

    this.year = localSectFile.get("SEASON", "Year", 1940, 1930, 1960);
    this.month = localSectFile.get("SEASON", "Month", World.land().config.month, 1, 12);
    this.day = localSectFile.get("SEASON", "Day", 15, 1, 31);
    this.windDirection = localSectFile.get("WEATHER", "WindDirection", 0.0F, 0.0F, 359.98999F);
    this.windVelocity = localSectFile.get("WEATHER", "WindSpeed", 0.0F, 0.0F, 15.0F);
    this.gust = localSectFile.get("WEATHER", "Gust", 0, 0, 12);
    this.turbulence = localSectFile.get("WEATHER", "Turbulence", 0, 0, 6);

    this.cloudType = localSectFile.get("MAIN", "CloudType", 0, 0, 6);
    this.cloudHeight = localSectFile.get("MAIN", "CloudHeight", 1000.0F, 300.0F, 5000.0F);
    Mission.createClouds(this.cloudType, this.cloudHeight);
    if (Main3D.cur3D().clouds != null) {
      Main3D.cur3D().clouds.setShow(false);
    }
    Main3D.cur3D().spritesFog.setShow(false);

    this.wConditions.update();

    zutiInitMDSVariables();

    zutiLoadMDSVariables(localSectFile);

    this.wFoW.update();
    this.wCraters.update();
    this.wRespawnTime.update();
    this.wMisc.update();

    Plugin.doLoad(localSectFile);

    if (str2 != null) {
      Object[] arrayOfObject = builder.pathes.getOwnerAttached();
      for (int n = 0; n < arrayOfObject.length; n++) {
        Path localPath = (Path)arrayOfObject[n];
        if (str2.equals(localPath.name())) {
          if (((PathAir)localPath).bOnlyAI) break;
          Path.player = localPath;
          this.missionArmy = localPath.getArmy(); break;
        }
      }

    }

    builder.repaint();
    this.bChanged = false;
    return true;
  }

  public boolean save(String paramString) {
    if (PlMapLoad.getLandLoaded() == null) {
      builder.tipErr("MissionSave: tirrain not selected");
      return false;
    }
    SectFile localSectFile = new SectFile();
    localSectFile.setFileName(paramString);
    int i = localSectFile.sectionAdd("MAIN");
    localSectFile.lineAdd(i, "MAP", PlMapLoad.mapFileName());
    localSectFile.lineAdd(i, "TIME", "" + World.getTimeofDay());
    if (World.cur().isTimeOfDayConstant())
      localSectFile.lineAdd(i, "TIMECONSTANT", "1");
    if (World.cur().isWeaponsConstant())
      localSectFile.lineAdd(i, "WEAPONSCONSTANT", "1");
    localSectFile.lineAdd(i, "CloudType", "" + this.cloudType);
    localSectFile.lineAdd(i, "CloudHeight", "" + this.cloudHeight);

    if (Actor.isValid(Path.player)) {
      localSectFile.lineAdd(i, "player", Path.player.name());
      if (Path.playerNum >= ((PathAir)Path.player).planes)
        Path.playerNum = 0;
    } else {
      Path.playerNum = 0;
    }
    localSectFile.lineAdd(i, "army", "" + this.missionArmy);
    localSectFile.lineAdd(i, "playerNum", "" + Path.playerNum);

    int j = localSectFile.sectionAdd("SEASON");
    localSectFile.lineAdd(j, "Year", "" + this.year);
    localSectFile.lineAdd(j, "Month", "" + this.month);
    localSectFile.lineAdd(j, "Day", "" + this.day);

    int k = localSectFile.sectionAdd("WEATHER");
    localSectFile.lineAdd(k, "WindDirection", "" + this.windDirection);
    localSectFile.lineAdd(k, "WindSpeed", "" + this.windVelocity);
    localSectFile.lineAdd(k, "Gust", "" + this.gust);
    localSectFile.lineAdd(k, "Turbulence", "" + this.turbulence);

    zutiSaveMDSVariables(localSectFile);

    if (!Plugin.doSave(localSectFile)) {
      return false;
    }
    localSectFile.saveFile(paramString);
    this.bChanged = false;
    return true;
  }

  public void mapLoaded()
  {
    zutiInitMDSVariables();

    if (!builder.isLoadedLandscape()) return;

    String str1 = "maps/" + PlMapLoad.mapFileName();
    SectFile localSectFile = new SectFile(str1);
    int i = localSectFile.sectionIndex("static");
    if ((i >= 0) && (localSectFile.vars(i) > 0)) {
      String str2 = localSectFile.var(i, 0);
      Statics.load(HomePath.concatNames(str1, str2), PlMapLoad.bridgeActors);
    }

    int j = localSectFile.sectionIndex("text");
    if ((j >= 0) && (localSectFile.vars(j) > 0)) {
      localObject = localSectFile.var(j, 0);
      if (Main3D.cur3D().land2DText == null)
        Main3D.cur3D().land2DText = new Land2DText();
      else
        Main3D.cur3D().land2DText.clear();
      Main3D.cur3D().land2DText.load(HomePath.concatNames(str1, (String)localObject));
    }

    Statics.trim();

    Object localObject = World.land();
    if (Landscape.isExistMeshs()) {
      for (int k = 0; k < PathFind.tShip.sy; k++) {
        for (int m = 0; m < PathFind.tShip.sx; m++) {
          if (Landscape.isExistMesh(m, PathFind.tShip.sy - k - 1)) {
            PathFind.tShip.I(m, k, PathFind.tShip.intI(m, k) | 0x8);
            PathFind.tNoShip.I(m, k, PathFind.tNoShip.intI(m, k) | 0x8);
          }
        }
      }
    }

    Mission.createClouds(this.cloudType, this.cloudHeight);
    if (Main3D.cur3D().clouds != null) {
      Main3D.cur3D().clouds.setShow(false);
    }
    Main3D.cur3D().spritesFog.setShow(false);

    this.wConditions.update();

    this.wFoW.update();
    this.wCraters.update();
    this.wRespawnTime.update();
    this.wMisc.update();
  }

  public void configure() {
    builder.bMultiSelect = false;
    if (getPlugin("MapLoad") == null)
      throw new RuntimeException("PlMission: plugin 'MapLoad' not found");
    this.pluginMapLoad = ((PlMapLoad)getPlugin("MapLoad"));
  }

  void _viewTypeAll(boolean paramBoolean)
  {
    Plugin.doViewTypeAll(paramBoolean);
    viewBridge(paramBoolean);
    viewRunaway(paramBoolean);
    this.viewName.bChecked = (builder.conf.bShowName = paramBoolean);
    this.viewTime.bChecked = (builder.conf.bShowTime = paramBoolean);
    for (int i = 0; i < builder.conf.bShowArmy.length; i++)
      this.viewArmy[i].bChecked = (builder.conf.bShowArmy[i] = paramBoolean);
    if (!paramBoolean)
      builder.setSelected(null);
  }

  void viewBridge(boolean paramBoolean) {
    builder.conf.bViewBridge = paramBoolean;
    this.viewBridge.bChecked = builder.conf.bViewBridge;
  }
  void viewBridge() { viewBridge(!builder.conf.bViewBridge); }

  void viewRunaway(boolean paramBoolean) {
    builder.conf.bViewRunaway = paramBoolean;
    this.viewRunaway.bChecked = builder.conf.bViewRunaway;
  }
  void viewRunaway() { viewRunaway(!builder.conf.bViewRunaway); }

  public static void checkShowCurrentArmy() {
    Object localObject = builder.selectedPath();
    if (localObject == null)
      localObject = builder.selectedActor();
    if (localObject == null) return;
    int i = ((Actor)localObject).getArmy();
    if (builder.conf.bShowArmy[i] == 0)
      builder.setSelected(null);
  }

  private String checkMisExtension(String paramString)
  {
    if (!paramString.toLowerCase().endsWith(".mis"))
      return paramString + ".mis";
    return paramString;
  }

  public void createGUI()
  {
    zutiInitMDSVariables();

    builder.mDisplayFilter.subMenu.addItem("-", null);
    this.viewBridge = builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(builder.mDisplayFilter.subMenu, i18n("showBridge"), i18n("TIPshowBridge")) {
      public void execute() {
        PlMission.this.viewBridge();
      }
    });
    this.viewBridge.bChecked = builder.conf.bViewBridge;
    this.viewRunaway = builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(builder.mDisplayFilter.subMenu, i18n("showRunway"), i18n("TIPshowRunway")) {
      public void execute() {
        PlMission.this.viewRunaway();
      }
    });
    this.viewRunaway.bChecked = builder.conf.bViewRunaway;
    builder.mDisplayFilter.subMenu.addItem("-", null);
    this.viewName = builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(builder.mDisplayFilter.subMenu, i18n("showName"), i18n("TIPshowName")) {
      public void execute() {
        this.bChecked = (Plugin.builder.conf.bShowName = !Plugin.builder.conf.bShowName ? 1 : 0);
      }
    });
    this.viewName.bChecked = builder.conf.bShowName;
    this.viewTime = builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(builder.mDisplayFilter.subMenu, i18n("showTime"), i18n("TIPshowTime")) {
      public void execute() {
        this.bChecked = (Plugin.builder.conf.bShowTime = !Plugin.builder.conf.bShowTime ? 1 : 0);
      }
    });
    this.viewTime.bChecked = builder.conf.bShowTime;

    this.viewArmy = new GWindowMenuItemArmy[Builder.armyAmount()];
    for (int i = 0; i < Builder.armyAmount(); i++) {
      this.viewArmy[i] = builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItemArmy(builder.mDisplayFilter.subMenu, i18n("showArmy") + " " + I18N.army(Army.name(i)), i18n("TIPshowArmy") + " " + I18N.army(Army.name(i)), i)
      {
        public void execute() {
          this.bChecked = (Plugin.builder.conf.bShowArmy[this.army] = Plugin.builder.conf.bShowArmy[this.army] == 0 ? 1 : 0);
          PlMission.checkShowCurrentArmy();
        }
      });
      this.viewArmy[i].bChecked = builder.conf.bShowArmy[i];
    }
    builder.mDisplayFilter.subMenu.addItem("-", null);
    builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(builder.mDisplayFilter.subMenu, i18n("&ShowAll"), i18n("TIPShowAll")) {
      public void execute() {
        PlMission.this._viewTypeAll(true);
      }
    });
    builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(builder.mDisplayFilter.subMenu, i18n("&HideAll"), i18n("TIPHideAll")) {
      public void execute() {
        PlMission.this._viewTypeAll(false);
      }
    });
    builder.mFile.subMenu.addItem(1, new GWindowMenuItem(builder.mFile.subMenu, i18n("Load"), i18n("TIPLoad"))
    {
      public void execute() {
        PlMission.this.doDlgLoadMission();
      }
    });
    builder.mFile.subMenu.addItem(2, new GWindowMenuItem(builder.mFile.subMenu, i18n("Save"), i18n("TIPSaveAs"))
    {
      public void execute() {
        if (PlMission.this.missionFileName != null) {
          PlMission.this.save("missions/" + PlMission.this.missionFileName);
        } else {
          1 local1 = new GWindowFileSaveAs(this.root, true, Plugin.i18n("SaveMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) })
          {
            public void result(String paramString)
            {
              if (paramString != null) {
                paramString = PlMission.this.checkMisExtension(paramString);
                PlMission.access$102(PlMission.this, paramString);
                ((GWindowRootMenu)Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(PlMission.this.missionFileName);
                PlMission.access$402(PlMission.this, paramString);
                PlMission.this.save("missions/" + paramString);
              }
            }
          };
          local1.exec = new PlMission.DlgFileConfirmSave(PlMission.this);
          if (PlMission.this.lastOpenFile != null)
            local1.setSelectFile(PlMission.this.lastOpenFile);
        }
      }
    });
    builder.mFile.subMenu.addItem(3, new GWindowMenuItem(builder.mFile.subMenu, i18n("SaveAs"), i18n("TIPSaveAs"))
    {
      public void execute() {
        1 local1 = new GWindowFileSaveAs(this.root, true, Plugin.i18n("SaveMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) })
        {
          public void result(String paramString)
          {
            if (paramString != null) {
              paramString = PlMission.this.checkMisExtension(paramString);
              PlMission.access$102(PlMission.this, paramString);
              ((GWindowRootMenu)Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(PlMission.this.missionFileName);
              PlMission.access$402(PlMission.this, paramString);
              PlMission.this.save("missions/" + paramString);
            }
          }
        };
        local1.exec = new PlMission.DlgFileConfirmSave(PlMission.this);
        if (PlMission.this.lastOpenFile != null)
          local1.setSelectFile(PlMission.this.lastOpenFile);
      }
    });
    builder.mFile.subMenu.addItem(4, new GWindowMenuItem(builder.mFile.subMenu, i18n("Play"), i18n("TIPPlay")) {
      public void execute() {
        if (!Plugin.builder.isLoadedLandscape()) return;
        if ((PlMission.isChanged()) || (PlMission.this.missionFileName == null)) {
          if (PlMission.this.missionFileName != null) {
            if (PlMission.this.save("missions/" + PlMission.this.missionFileName))
              PlMission.this.playMission();
          } else {
            1 local1 = new GWindowFileSaveAs(this.root, true, Plugin.i18n("SaveMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) })
            {
              public void result(String paramString)
              {
                if (paramString != null) {
                  paramString = PlMission.this.checkMisExtension(paramString);
                  PlMission.access$102(PlMission.this, paramString);
                  ((GWindowRootMenu)Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(PlMission.this.missionFileName);
                  PlMission.access$402(PlMission.this, paramString);
                  if (PlMission.this.save("missions/" + paramString))
                    PlMission.this.playMission();
                }
              }
            };
            local1.exec = new PlMission.DlgFileConfirmSave(PlMission.this);
            if (PlMission.this.lastOpenFile != null)
              local1.setSelectFile(PlMission.this.lastOpenFile);
          }
        }
        else PlMission.this.playMission();
      }
    });
    builder.mFile.subMenu.bNotify = true;
    builder.mFile.subMenu.addNotifyListener(new GNotifyListener()
    {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        if (paramInt1 != 13) return false;
        Plugin.builder.mFile.subMenu.getItem(2).bEnable = Plugin.builder.isLoadedLandscape();
        Plugin.builder.mFile.subMenu.getItem(3).bEnable = Plugin.builder.isLoadedLandscape();
        Plugin.builder.mFile.subMenu.getItem(4).bEnable = Plugin.builder.isLoadedLandscape();
        return false;
      }
    });
    this.mConditions = builder.mConfigure.subMenu.addItem(0, new GWindowMenuItem(builder.mConfigure.subMenu, i18n("&Conditions"), i18n("TIPConditions"))
    {
      public void execute() {
        if (PlMission.this.wConditions.isVisible()) PlMission.this.wConditions.hideWindow(); else
          PlMission.this.wConditions.showWindow();
      }
    });
    this.mFoW = builder.mConfigure.subMenu.addItem(1, new GWindowMenuItem(builder.mConfigure.subMenu, i18n("&FogOfWar"), i18n("TIPFoW"))
    {
      public void execute() {
        if (PlMission.this.wFoW.isVisible()) PlMission.this.wFoW.hideWindow(); else
          PlMission.this.wFoW.showWindow();
      }
    });
    builder.mConfigure.subMenu.addItem(2, "-", null);
    this.mCraters = builder.mConfigure.subMenu.addItem(3, new GWindowMenuItem(builder.mConfigure.subMenu, i18n("Cra&ters"), i18n("TIPCraters"))
    {
      public void execute() {
        if (PlMission.this.wCraters.isVisible()) PlMission.this.wCraters.hideWindow(); else
          PlMission.this.wCraters.showWindow();
      }
    });
    this.mRespawnTime = builder.mConfigure.subMenu.addItem(4, new GWindowMenuItem(builder.mConfigure.subMenu, i18n("&Respawn"), i18n("TIPRespawnTime"))
    {
      public void execute() {
        if (PlMission.this.wRespawnTime.isVisible()) PlMission.this.wRespawnTime.hideWindow(); else
          PlMission.this.wRespawnTime.showWindow();
      }
    });
    this.mMisc = builder.mConfigure.subMenu.addItem(5, new GWindowMenuItem(builder.mConfigure.subMenu, i18n("&Misc"), i18n("TIPMisc"))
    {
      public void execute() {
        if (PlMission.this.wMisc.isVisible()) PlMission.this.wMisc.hideWindow(); else
          PlMission.this.wMisc.showWindow();
      }
    });
    builder.mEdit.subMenu.addItem(0, "-", null);

    this.wConditions = new WConditions();
    this.wConditions.update();

    this.wFoW = new WFoW();
    this.wFoW.update();
    this.wCraters = new WCraters();
    this.wCraters.update();
    this.wRespawnTime = new WRespawnTime();
    this.wRespawnTime.update();
    this.wMisc = new WMisc();
    this.wMisc.update();
  }

  private void doLoadMission(String paramString)
  {
    this._loadFileName = paramString;
    this._loadMessageBox = new GWindowMessageBox(builder.clientWindow.root, 20.0F, true, i18n("StandBy"), i18n("LoadingMission"), 4, 0.0F);

    new MsgAction(72, 0.0D) {
      public void doAction() { PlMission.this.load(PlMission.this._loadFileName);
        PlMission.this._loadMessageBox.close(false); }
    };
  }

  private void playMission()
  {
    Main.cur().currentMissionFile = new SectFile("missions/" + this.missionFileName, 0);
    this.bReload = true;

    Main.stateStack().push(4);
  }

  private void doDlgLoadMission()
  {
    if (!isChanged()) {
      _doDlgLoadMission();
      return;
    }
    new GWindowMessageBox(builder.clientWindow.root, 20.0F, true, i18n("LoadMission"), i18n("ConfirmExitMsg"), 1, 0.0F)
    {
      public void result(int paramInt)
      {
        if (paramInt == 3) {
          if (PlMission.this.missionFileName != null) {
            PlMission.this.save("missions/" + PlMission.this.missionFileName);
            PlMission.this._doDlgLoadMission();
          } else {
            1 local1 = new GWindowFileSaveAs(this.root, true, Plugin.i18n("SaveMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) })
            {
              public void result(String paramString)
              {
                if (paramString != null) {
                  paramString = PlMission.this.checkMisExtension(paramString);
                  PlMission.access$102(PlMission.this, paramString);
                  ((GWindowRootMenu)Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(PlMission.this.missionFileName);
                  PlMission.access$402(PlMission.this, paramString);
                  PlMission.this.save("missions/" + paramString);
                }
                PlMission.this._doDlgLoadMission();
              }
            };
            local1.exec = new PlMission.DlgFileConfirmSave(PlMission.this);
            if (PlMission.this.lastOpenFile != null)
              local1.setSelectFile(PlMission.this.lastOpenFile);
          }
        }
        else
          PlMission.this._doDlgLoadMission();
      }
    };
  }

  private void _doDlgLoadMission() {
    20 local20 = new GWindowFileOpen(builder.clientWindow.root, true, i18n("LoadMission"), "missions", new GFileFilter[] { new GFileFilterName(i18n("MissionFiles"), new String[] { "*.mis" }) })
    {
      public void result(String paramString)
      {
        if (paramString != null) {
          PlMission.access$102(PlMission.this, paramString);
          ((GWindowRootMenu)Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(PlMission.this.missionFileName);
          PlMission.access$402(PlMission.this, paramString);
          PlMission.this.doLoadMission("missions/" + paramString);
        }
      }
    };
    if (this.lastOpenFile != null)
      local20.setSelectFile(this.lastOpenFile);
  }

  public boolean exitBuilder() {
    if (!isChanged())
      return true;
    new GWindowMessageBox(builder.clientWindow.root, 20.0F, true, i18n("ConfirmExit"), i18n("ConfirmExitMsg"), 1, 0.0F)
    {
      public void result(int paramInt)
      {
        if (paramInt == 3) {
          if (PlMission.this.missionFileName != null) {
            PlMission.this.save("missions/" + PlMission.this.missionFileName);
            Plugin.builder.doMenu_FileExit();
          } else {
            1 local1 = new GWindowFileSaveAs(this.root, true, Plugin.i18n("SaveMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) })
            {
              public void result(String paramString)
              {
                if (paramString != null) {
                  paramString = PlMission.this.checkMisExtension(paramString);
                  PlMission.access$102(PlMission.this, paramString);
                  ((GWindowRootMenu)Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(PlMission.this.missionFileName);
                  PlMission.access$402(PlMission.this, paramString);
                  PlMission.this.save("missions/" + paramString);
                }
                PlMission.access$1402(PlMission.this, false);
                Plugin.builder.doMenu_FileExit();
              }
            };
            local1.exec = new PlMission.DlgFileConfirmSave(PlMission.this);
            if (PlMission.this.lastOpenFile != null)
              local1.setSelectFile(PlMission.this.lastOpenFile);
          }
        }
        else {
          PlMission.access$1402(PlMission.this, false);
          Plugin.builder.doMenu_FileExit();
        }
      }
    };
    return false;
  }

  public void loadNewMap() {
    if (!this.bChanged) {
      this.missionFileName = null;
      ((GWindowRootMenu)builder.clientWindow.root).statusBar.setDefaultHelp(this.missionFileName);
      ((PlMapLoad)Plugin.getPlugin("MapLoad")).guiMapLoad();
      return;
    }
    new GWindowMessageBox(builder.clientWindow.root, 20.0F, true, i18n("SaveMission"), i18n("ConfirmExitMsg"), 1, 0.0F)
    {
      public void result(int paramInt)
      {
        if (paramInt == 3) {
          if (PlMission.this.missionFileName != null) {
            PlMission.this.save("missions/" + PlMission.this.missionFileName);
            ((PlMapLoad)Plugin.getPlugin("MapLoad")).guiMapLoad();
          } else {
            1 local1 = new GWindowFileSaveAs(this.root, true, Plugin.i18n("SaveMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) })
            {
              public void result(String paramString)
              {
                if (paramString != null) {
                  paramString = PlMission.this.checkMisExtension(paramString);
                  PlMission.access$102(PlMission.this, paramString);
                  ((GWindowRootMenu)Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(PlMission.this.missionFileName);
                  PlMission.access$402(PlMission.this, paramString);
                  PlMission.this.save("missions/" + paramString);
                }
                PlMission.access$1402(PlMission.this, false);
                ((PlMapLoad)Plugin.getPlugin("MapLoad")).guiMapLoad();
              }
            };
            local1.exec = new PlMission.DlgFileConfirmSave(PlMission.this);
            if (PlMission.this.lastOpenFile != null)
              local1.setSelectFile(PlMission.this.lastOpenFile);
          }
        }
        else {
          PlMission.access$1402(PlMission.this, false);
          ((PlMapLoad)Plugin.getPlugin("MapLoad")).guiMapLoad();
        }
        PlMission.access$102(PlMission.this, null);
        ((GWindowRootMenu)Plugin.builder.clientWindow.root).statusBar.setDefaultHelp(PlMission.this.missionFileName);
      } } ;
  }

  public void freeResources() {
    if (this.wConditions.isVisible()) this.wConditions.hideWindow();

    if (!this.bReload) {
      this.missionFileName = null;
      ((GWindowRootMenu)builder.clientWindow.root).statusBar.setDefaultHelp(this.missionFileName);
    }
  }

  public PlMission()
  {
    cur = this;
  }

  private String checkValidRange(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 < paramInt2) {
      paramInt1 = paramInt2;
    }
    else if (paramInt1 > paramInt3)
      paramInt1 = paramInt3;
    return Integer.toString(paramInt1);
  }
  private String checkValidRange(float paramFloat1, float paramFloat2, float paramFloat3) {
    if (paramFloat1 < paramFloat2) {
      paramFloat1 = paramFloat2;
    }
    else if (paramFloat1 > paramFloat3)
      paramFloat1 = paramFloat3;
    return Float.toString(paramFloat1);
  }
  private String checkValidRange(int paramInt1, int paramInt2, GWindowLabel paramGWindowLabel) {
    int i = Integer.parseInt(paramGWindowLabel.cap.caption);
    if (paramInt1 < paramInt2) {
      paramInt1 = paramInt2;
    }
    else if (paramInt1 > i)
      paramInt1 = i;
    return Integer.toString(paramInt1);
  }

  private void zutiLoadMDSVariables(SectFile paramSectFile)
  {
    try
    {
      Mission.ZUTI_RADAR_IN_ADV_MODE = false;
      if (paramSectFile.get("MDS", "MDS_Radar_SetRadarToAdvanceMode", 0, 0, 1) == 1) {
        Mission.ZUTI_RADAR_IN_ADV_MODE = true;
      }
      this.zutiRadar_ShipsAsRadar = false;
      if (paramSectFile.get("MDS", "MDS_Radar_ShipsAsRadar", 0, 0, 1) == 1)
        this.zutiRadar_ShipsAsRadar = true;
      this.zutiRadar_ShipRadar_MaxRange = paramSectFile.get("MDS", "MDS_Radar_ShipRadar_MaxRange", 100, 1, 99999);
      this.zutiRadar_ShipRadar_MinHeight = paramSectFile.get("MDS", "MDS_Radar_ShipRadar_MinHeight", 100, 0, 99999);
      this.zutiRadar_ShipRadar_MaxHeight = paramSectFile.get("MDS", "MDS_Radar_ShipRadar_MaxHeight", 5000, 1000, 99999);
      this.zutiRadar_ShipSmallRadar_MaxRange = paramSectFile.get("MDS", "MDS_Radar_ShipSmallRadar_MaxRange", 25, 1, 99999);
      this.zutiRadar_ShipSmallRadar_MinHeight = paramSectFile.get("MDS", "MDS_Radar_ShipSmallRadar_MinHeight", 0, 0, 99999);
      this.zutiRadar_ShipSmallRadar_MaxHeight = paramSectFile.get("MDS", "MDS_Radar_ShipSmallRadar_MaxHeight", 2000, 1000, 99999);

      this.zutiRadar_ScoutsAsRadar = false;
      if (paramSectFile.get("MDS", "MDS_Radar_ScoutsAsRadar", 0, 0, 1) == 1)
        this.zutiRadar_ScoutsAsRadar = true;
      this.zutiRadar_ScoutRadar_MaxRange = paramSectFile.get("MDS", "MDS_Radar_ScoutRadar_MaxRange", 2, 1, 99999);
      this.zutiRadar_ScoutRadar_DeltaHeight = paramSectFile.get("MDS", "MDS_Radar_ScoutRadar_DeltaHeight", 1500, 100, 99999);

      this.zutiRadar_ScoutCompleteRecon = false;
      if (paramSectFile.get("MDS", "MDS_Radar_ScoutCompleteRecon", 0, 0, 1) == 1) {
        this.zutiRadar_ScoutCompleteRecon = true;
      }
      zutiLoadScouts_Red(paramSectFile);
      zutiLoadScouts_Blue(paramSectFile);

      this.zutiRadar_ScoutGroundObjects_Alpha = paramSectFile.get("MDS", "MDS_Radar_ScoutGroundObjects_Alpha", 5, 1, 11);

      this.zutiRadar_RefreshInterval = paramSectFile.get("MDS", "MDS_Radar_RefreshInterval", 0, 0, 99999);

      this.zutiRadar_DisableVectoring = false;
      if (paramSectFile.get("MDS", "MDS_Radar_DisableVectoring", 0, 0, 1) == 1) {
        this.zutiRadar_DisableVectoring = true;
      }
      this.zutiRadar_EnableTowerCommunications = true;
      if (paramSectFile.get("MDS", "MDS_Radar_EnableTowerCommunications", 1, 0, 1) == 0) {
        this.zutiRadar_EnableTowerCommunications = false;
      }
      this.zutiRadar_HideUnpopulatedAirstripsFromMinimap = false;
      if (paramSectFile.get("MDS", "MDS_Radar_HideUnpopulatedAirstripsFromMinimap", 0, 0, 1) == 1) {
        this.zutiRadar_HideUnpopulatedAirstripsFromMinimap = true;
      }
      this.zutiMisc_DisableAIRadioChatter = false;
      if (paramSectFile.get("MDS", "MDS_Misc_DisableAIRadioChatter", 0, 0, 1) == 1)
        this.zutiMisc_DisableAIRadioChatter = true;
      this.zutiMisc_DespawnAIPlanesAfterLanding = true;
      if (paramSectFile.get("MDS", "MDS_Misc_DespawnAIPlanesAfterLanding", 1, 0, 1) == 0)
        this.zutiMisc_DespawnAIPlanesAfterLanding = false;
      this.zutiMisc_HidePlayersCountOnHomeBase = false;
      if (paramSectFile.get("MDS", "MDS_Misc_HidePlayersCountOnHomeBase", 0, 0, 1) == 1) {
        this.zutiMisc_HidePlayersCountOnHomeBase = true;
      }

      this.zutiMisc_BombsCat1_CratersVisibilityMultiplier = paramSectFile.get("MDS", "MDS_Misc_BombsCat1_CratersVisibilityMultiplier", 1.0F, 1.0F, 99999.0F);
      this.zutiMisc_BombsCat2_CratersVisibilityMultiplier = paramSectFile.get("MDS", "MDS_Misc_BombsCat2_CratersVisibilityMultiplier", 1.0F, 1.0F, 99999.0F);
      this.zutiMisc_BombsCat3_CratersVisibilityMultiplier = paramSectFile.get("MDS", "MDS_Misc_BombsCat3_CratersVisibilityMultiplier", 1.0F, 1.0F, 99999.0F);

      this.respawnTime_Bigship = paramSectFile.get("RespawnTime", "Bigship", 1800, 0, 1200000);
      this.respawnTime_Ship = paramSectFile.get("RespawnTime", "Ship", 1800, 0, 1200000);
      this.respawnTime_Aeroanchored = paramSectFile.get("RespawnTime", "Aeroanchored", 1800, 0, 1200000);
      this.respawnTime_Artillery = paramSectFile.get("RespawnTime", "Artillery", 1800, 0, 1200000);
      this.respawnTime_Searchlight = paramSectFile.get("RespawnTime", "Searchlight", 1800, 0, 1200000);
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }

  private void zutiSaveMDSVariables(SectFile paramSectFile)
  {
    try
    {
      int i = paramSectFile.sectionIndex("MDS");

      if (i < 0) {
        i = paramSectFile.sectionAdd("MDS");
      }

      paramSectFile.lineAdd(i, "MDS_Radar_SetRadarToAdvanceMode", BoolToInt(Mission.ZUTI_RADAR_IN_ADV_MODE));
      paramSectFile.lineAdd(i, "MDS_Radar_RefreshInterval", new Integer(this.zutiRadar_RefreshInterval).toString());
      paramSectFile.lineAdd(i, "MDS_Radar_DisableVectoring", BoolToInt(this.zutiRadar_DisableVectoring));
      paramSectFile.lineAdd(i, "MDS_Radar_EnableTowerCommunications", BoolToInt(this.zutiRadar_EnableTowerCommunications));
      paramSectFile.lineAdd(i, "MDS_Radar_ShipsAsRadar", BoolToInt(this.zutiRadar_ShipsAsRadar));
      paramSectFile.lineAdd(i, "MDS_Radar_ShipRadar_MaxRange", new Integer(this.zutiRadar_ShipRadar_MaxRange).toString());
      paramSectFile.lineAdd(i, "MDS_Radar_ShipRadar_MinHeight", new Integer(this.zutiRadar_ShipRadar_MinHeight).toString());
      paramSectFile.lineAdd(i, "MDS_Radar_ShipRadar_MaxHeight", new Integer(this.zutiRadar_ShipRadar_MaxHeight).toString());
      paramSectFile.lineAdd(i, "MDS_Radar_ShipSmallRadar_MaxRange", new Integer(this.zutiRadar_ShipSmallRadar_MaxRange).toString());
      paramSectFile.lineAdd(i, "MDS_Radar_ShipSmallRadar_MinHeight", new Integer(this.zutiRadar_ShipSmallRadar_MinHeight).toString());
      paramSectFile.lineAdd(i, "MDS_Radar_ShipSmallRadar_MaxHeight", new Integer(this.zutiRadar_ShipSmallRadar_MaxHeight).toString());
      paramSectFile.lineAdd(i, "MDS_Radar_ScoutsAsRadar", BoolToInt(this.zutiRadar_ScoutsAsRadar));
      paramSectFile.lineAdd(i, "MDS_Radar_ScoutRadar_MaxRange", new Integer(this.zutiRadar_ScoutRadar_MaxRange).toString());
      paramSectFile.lineAdd(i, "MDS_Radar_ScoutRadar_DeltaHeight", new Integer(this.zutiRadar_ScoutRadar_DeltaHeight).toString());
      paramSectFile.lineAdd(i, "MDS_Radar_HideUnpopulatedAirstripsFromMinimap", BoolToInt(this.zutiRadar_HideUnpopulatedAirstripsFromMinimap));
      paramSectFile.lineAdd(i, "MDS_Radar_ScoutGroundObjects_Alpha", new Integer(this.zutiRadar_ScoutGroundObjects_Alpha).toString());
      paramSectFile.lineAdd(i, "MDS_Radar_ScoutCompleteRecon", BoolToInt(this.zutiRadar_ScoutCompleteRecon));

      zutiSaveScouts_Red(paramSectFile);
      zutiSaveScouts_Blue(paramSectFile);

      paramSectFile.lineAdd(i, "MDS_Misc_DisableAIRadioChatter", BoolToInt(this.zutiMisc_DisableAIRadioChatter));
      paramSectFile.lineAdd(i, "MDS_Misc_DespawnAIPlanesAfterLanding", BoolToInt(this.zutiMisc_DespawnAIPlanesAfterLanding));
      paramSectFile.lineAdd(i, "MDS_Misc_HidePlayersCountOnHomeBase", BoolToInt(this.zutiMisc_HidePlayersCountOnHomeBase));

      paramSectFile.lineAdd(i, "MDS_Misc_BombsCat1_CratersVisibilityMultiplier", new Float(this.zutiMisc_BombsCat1_CratersVisibilityMultiplier).toString());
      paramSectFile.lineAdd(i, "MDS_Misc_BombsCat2_CratersVisibilityMultiplier", new Float(this.zutiMisc_BombsCat2_CratersVisibilityMultiplier).toString());
      paramSectFile.lineAdd(i, "MDS_Misc_BombsCat3_CratersVisibilityMultiplier", new Float(this.zutiMisc_BombsCat3_CratersVisibilityMultiplier).toString());

      i = paramSectFile.sectionAdd("RespawnTime");

      paramSectFile.lineAdd(i, "Bigship", new Integer(this.respawnTime_Bigship).toString());
      paramSectFile.lineAdd(i, "Ship", new Integer(this.respawnTime_Ship).toString());
      paramSectFile.lineAdd(i, "Aeroanchored", new Integer(this.respawnTime_Aeroanchored).toString());
      paramSectFile.lineAdd(i, "Artillery", new Integer(this.respawnTime_Artillery).toString());
      paramSectFile.lineAdd(i, "Searchlight", new Integer(this.respawnTime_Searchlight).toString());
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }

  private void zutiInitMDSVariables() {
    if (GUI.pad != null) {
      GUI.pad.zutiColorAirfields = true;
    }

    this.zutiRadar_ShipsAsRadar = false;
    this.zutiRadar_ShipRadar_MaxRange = 100;
    this.zutiRadar_ShipRadar_MinHeight = 100;
    this.zutiRadar_ShipRadar_MaxHeight = 5000;
    this.zutiRadar_ShipSmallRadar_MaxRange = 25;
    this.zutiRadar_ShipSmallRadar_MinHeight = 0;
    this.zutiRadar_ShipSmallRadar_MaxHeight = 2000;
    this.zutiRadar_ScoutsAsRadar = false;
    this.zutiRadar_ScoutRadar_MaxRange = 2;
    this.zutiRadar_ScoutRadar_DeltaHeight = 1500;
    this.zutiRadar_ScoutRadarType_Red = "";
    this.zutiRadar_ScoutRadarType_Blue = "";
    this.zutiRadar_RefreshInterval = 0;
    this.zutiRadar_DisableVectoring = false;
    this.zutiRadar_EnableTowerCommunications = true;
    this.zutiRadar_HideUnpopulatedAirstripsFromMinimap = false;
    Mission.ZUTI_RADAR_IN_ADV_MODE = false;
    this.zutiRadar_ScoutGroundObjects_Alpha = 5;
    this.zutiRadar_ScoutCompleteRecon = false;
    this.zutiMisc_DisableAIRadioChatter = false;
    this.zutiMisc_DespawnAIPlanesAfterLanding = true;
    this.zutiMisc_HidePlayersCountOnHomeBase = false;

    this.zutiMisc_BombsCat1_CratersVisibilityMultiplier = 1.0F;
    this.zutiMisc_BombsCat2_CratersVisibilityMultiplier = 1.0F;
    this.zutiMisc_BombsCat3_CratersVisibilityMultiplier = 1.0F;

    this.respawnTime_Bigship = 1800;
    this.respawnTime_Ship = 1800;
    this.respawnTime_Aeroanchored = 1800;
    this.respawnTime_Artillery = 1800;
    this.respawnTime_Searchlight = 1800;
  }

  private String BoolToInt(boolean paramBoolean)
  {
    if (paramBoolean) {
      return "1";
    }
    return "0";
  }

  private void zutiSaveScouts_Red(SectFile paramSectFile)
  {
    if ((this.zutiRadar_ScoutRadarType_Red != null) && (this.zutiRadar_ScoutRadarType_Red.trim().length() > 0))
    {
      int i = paramSectFile.sectionAdd("MDS_Scouts_Red");

      StringTokenizer localStringTokenizer = new StringTokenizer(this.zutiRadar_ScoutRadarType_Red);

      while (localStringTokenizer.hasMoreTokens())
      {
        paramSectFile.lineAdd(i, localStringTokenizer.nextToken());
      }
    }
  }

  private void zutiSaveScouts_Blue(SectFile paramSectFile)
  {
    if ((this.zutiRadar_ScoutRadarType_Blue != null) && (this.zutiRadar_ScoutRadarType_Blue.trim().length() > 0))
    {
      int i = paramSectFile.sectionAdd("MDS_Scouts_Blue");

      StringTokenizer localStringTokenizer = new StringTokenizer(this.zutiRadar_ScoutRadarType_Blue);

      while (localStringTokenizer.hasMoreTokens())
      {
        paramSectFile.lineAdd(i, localStringTokenizer.nextToken());
      }
    }
  }

  private void zutiLoadScouts_Red(SectFile paramSectFile)
  {
    int i = paramSectFile.sectionIndex("MDS_Scouts_Red");
    if (i > -1)
    {
      this.zutiRadar_ScoutRadarType_Red = "";

      int j = paramSectFile.vars(i);
      for (int k = 0; k < j; k++)
      {
        String str1 = paramSectFile.line(i, k);
        StringTokenizer localStringTokenizer = new StringTokenizer(str1);
        String str2 = null;

        if (localStringTokenizer.hasMoreTokens())
        {
          str2 = localStringTokenizer.nextToken();
        }

        if (str2 == null)
          continue;
        str2 = str2.intern();
        Class localClass = (Class)Property.value(str2, "airClass", null);

        if (localClass == null) {
          continue;
        }
        this.zutiRadar_ScoutRadarType_Red = (this.zutiRadar_ScoutRadarType_Red + str2 + " ");
      }
    }
  }

  private void zutiLoadScouts_Blue(SectFile paramSectFile)
  {
    int i = paramSectFile.sectionIndex("MDS_Scouts_Blue");
    if (i > -1)
    {
      this.zutiRadar_ScoutRadarType_Blue = "";

      int j = paramSectFile.vars(i);
      for (int k = 0; k < j; k++)
      {
        String str1 = paramSectFile.line(i, k);
        StringTokenizer localStringTokenizer = new StringTokenizer(str1);
        String str2 = null;

        if (localStringTokenizer.hasMoreTokens())
        {
          str2 = localStringTokenizer.nextToken();
        }

        if (str2 == null)
          continue;
        str2 = str2.intern();
        Class localClass = (Class)Property.value(str2, "airClass", null);

        if (localClass == null) {
          continue;
        }
        this.zutiRadar_ScoutRadarType_Blue = (this.zutiRadar_ScoutRadarType_Blue + str2 + " ");
      }
    }
  }

  static
  {
    Property.set(PlMission.class, "name", "Mission");
  }

  class WConditions extends GWindowFramed
  {
    GWindowEditControl wTimeH;
    GWindowEditControl wTimeM;
    GWindowComboControl wCloudType;
    GWindowEditControl wCloudHeight;
    GWindowCheckBox wTimeFix;
    GWindowCheckBox wWeaponFix;
    GWindowComboControl wYear;
    GWindowComboControl wDay;
    GWindowComboControl wMonth;
    GWindowEditControl wWindDirection;
    GWindowEditControl wWindVelocity;
    GWindowComboControl wGust;
    GWindowComboControl wTurbulence;
    GWindowBoxSeparate boxWindTable;
    GWindowLabel wLabel0;
    GWindowLabel wLabel1;
    GWindowLabel wLabel2;
    GWindowLabel wLabel3;
    GWindowLabel wLabel4;
    GWindowLabel wLabel5;
    GWindowLabel wLabel6;
    GWindowLabel wLabel7;
    GWindowLabel wLabel8;
    GWindowLabel wLabel9;
    GWindowLabel wLabel10;
    GWindowLabel wLabel00;
    GWindowLabel wLabel11;
    GWindowLabel wLabel22;
    GWindowLabel wLabel33;
    GWindowLabel wLabel44;
    GWindowLabel wLabel55;
    GWindowLabel wLabel66;
    GWindowLabel wLabel77;
    GWindowLabel wLabel88;
    GWindowLabel wLabel99;
    GWindowLabel wLabel1010;

    public void windowShown()
    {
      PlMission.this.mConditions.bChecked = true;
      super.windowShown();
    }
    public void windowHidden() {
      PlMission.this.mConditions.bChecked = false;
      super.windowHidden();
    }
    public void created() {
      this.bAlwaysOnTop = true;
      super.created();
      this.title = Plugin.i18n("MissionConditions");
      float f = 13.0F;
      this.clientWindow = create(new GWindowTabDialogClient());
      GWindowTabDialogClient localGWindowTabDialogClient = (GWindowTabDialogClient)this.clientWindow;
      GWindowDialogClient localGWindowDialogClient = null;
      localGWindowTabDialogClient.addTab(Plugin.i18n("weather"), localGWindowTabDialogClient.create(localGWindowDialogClient = new GWindowDialogClient()));

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, f - 1.0F, 1.3F, Plugin.i18n("Weather"), null));
      localGWindowDialogClient.addControl(this.wCloudType = new GWindowComboControl(localGWindowDialogClient, f, 1.0F, 8.0F) {
        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 == 2) {
            PlMission.WConditions.this.getCloudType();
          }
          return super.notify(paramInt1, paramInt2);
        }
      });
      this.wCloudType.setEditable(false);
      this.wCloudType.add(Plugin.i18n("Clear"));
      this.wCloudType.add(Plugin.i18n("Good"));
      this.wCloudType.add(Plugin.i18n("Hazy"));
      this.wCloudType.add(Plugin.i18n("Poor"));
      this.wCloudType.add(Plugin.i18n("Blind"));
      this.wCloudType.add(Plugin.i18n("Rain/Snow"));
      this.wCloudType.add(Plugin.i18n("Thunder"));

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, f - 1.0F, 1.3F, Plugin.i18n("CloudHeight"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, f + 5.5F, 3.0F, 2.0F, 1.3F, Plugin.i18n("[m]"), null));

      localGWindowDialogClient.addControl(this.wCloudHeight = new GWindowEditControl(localGWindowDialogClient, f, 3.0F, 5.0F, 1.3F, "") {
        public void afterCreated() { super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true; }

        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 == 2) {
            PlMission.WConditions.this.getCloudHeight();
          }
          return super.notify(paramInt1, paramInt2);
        }
      });
      this.wCloudHeight.setEditable(true);

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 13.0F, 11.0F, 1.6F, Plugin.i18n("WindTable"), null));
      this.boxWindTable = new GWindowBoxSeparate(localGWindowDialogClient, 1.0F, 15.0F, 20.0F, 25.0F);
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 3.0F, 16.0F, 9.0F, 1.3F, Plugin.i18n("Altitude[m]"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 10.0F, 16.0F, 9.0F, 1.3F, Plugin.i18n("WindSpeed[m/s]"), null));

      localGWindowDialogClient.addLabel(this.wLabel0 = new GWindowLabel(localGWindowDialogClient, 1.0F, 18.0F, 5.0F, 1.3F, "10", null));
      this.wLabel0.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel1 = new GWindowLabel(localGWindowDialogClient, 1.0F, 20.0F, 5.0F, 1.3F, "1000", null));
      this.wLabel1.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel2 = new GWindowLabel(localGWindowDialogClient, 1.0F, 22.0F, 5.0F, 1.3F, "2000", null));
      this.wLabel2.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel3 = new GWindowLabel(localGWindowDialogClient, 1.0F, 24.0F, 5.0F, 1.3F, "3000", null));
      this.wLabel3.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel4 = new GWindowLabel(localGWindowDialogClient, 1.0F, 26.0F, 5.0F, 1.3F, "4000", null));
      this.wLabel4.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel5 = new GWindowLabel(localGWindowDialogClient, 1.0F, 28.0F, 5.0F, 1.3F, "5000", null));
      this.wLabel5.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel6 = new GWindowLabel(localGWindowDialogClient, 1.0F, 30.0F, 5.0F, 1.3F, "6000", null));
      this.wLabel6.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel7 = new GWindowLabel(localGWindowDialogClient, 1.0F, 32.0F, 5.0F, 1.3F, "7000", null));
      this.wLabel7.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel8 = new GWindowLabel(localGWindowDialogClient, 1.0F, 34.0F, 5.0F, 1.3F, "8000", null));
      this.wLabel8.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel9 = new GWindowLabel(localGWindowDialogClient, 1.0F, 36.0F, 5.0F, 1.3F, "9000", null));
      this.wLabel9.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel10 = new GWindowLabel(localGWindowDialogClient, 1.0F, 38.0F, 5.0F, 1.3F, "10000", null));
      this.wLabel10.align = 2;

      localGWindowDialogClient.addLabel(this.wLabel00 = new GWindowLabel(localGWindowDialogClient, 10.0F, 18.0F, 5.0F, 1.3F, "", null));
      this.wLabel00.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel11 = new GWindowLabel(localGWindowDialogClient, 10.0F, 20.0F, 5.0F, 1.3F, "", null));
      this.wLabel11.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel22 = new GWindowLabel(localGWindowDialogClient, 10.0F, 22.0F, 5.0F, 1.3F, "", null));
      this.wLabel22.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel33 = new GWindowLabel(localGWindowDialogClient, 10.0F, 24.0F, 5.0F, 1.3F, "", null));
      this.wLabel33.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel44 = new GWindowLabel(localGWindowDialogClient, 10.0F, 26.0F, 5.0F, 1.3F, "", null));
      this.wLabel44.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel55 = new GWindowLabel(localGWindowDialogClient, 10.0F, 28.0F, 5.0F, 1.3F, "", null));
      this.wLabel55.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel66 = new GWindowLabel(localGWindowDialogClient, 10.0F, 30.0F, 5.0F, 1.3F, "", null));
      this.wLabel66.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel77 = new GWindowLabel(localGWindowDialogClient, 10.0F, 32.0F, 5.0F, 1.3F, "", null));
      this.wLabel77.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel88 = new GWindowLabel(localGWindowDialogClient, 10.0F, 34.0F, 5.0F, 1.3F, "", null));
      this.wLabel88.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel99 = new GWindowLabel(localGWindowDialogClient, 10.0F, 36.0F, 5.0F, 1.3F, "", null));
      this.wLabel99.align = 2;
      localGWindowDialogClient.addLabel(this.wLabel1010 = new GWindowLabel(localGWindowDialogClient, 10.0F, 38.0F, 5.0F, 1.3F, "", null));
      this.wLabel1010.align = 2;

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, f - 1.0F, 1.3F, Plugin.i18n("WindDirection"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, f + 5.5F, 5.0F, 7.0F, 1.3F, Plugin.i18n("[deg]"), null));
      localGWindowDialogClient.addControl(this.wWindDirection = new GWindowEditControl(localGWindowDialogClient, f, 5.0F, 5.0F, 1.3F, "") {
        public void afterCreated() { super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true; }

        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 == 2) {
            PlMission.WConditions.this.getWindDirection();
          }
          return super.notify(paramInt1, paramInt2);
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 7.0F, f - 1.0F, 1.3F, Plugin.i18n("WindVelocity"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, f + 5.5F, 7.0F, 7.0F, 1.3F, Plugin.i18n("[m/s]"), null));
      localGWindowDialogClient.addControl(this.wWindVelocity = new GWindowEditControl(localGWindowDialogClient, f, 7.0F, 5.0F, 1.3F, "") {
        public void afterCreated() { super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true; }

        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 == 2) {
            PlMission.WConditions.this.getWindVelocity();
          }
          return super.notify(paramInt1, paramInt2);
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 9.0F, f - 1.0F, 1.3F, Plugin.i18n("Gust"), null));
      localGWindowDialogClient.addControl(this.wGust = new GWindowComboControl(localGWindowDialogClient, f, 9.0F, 8.0F) {
        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 == 2) {
            PlMission.WConditions.this.getGust();
          }
          return super.notify(paramInt1, paramInt2);
        }
      });
      this.wGust.setEditable(false);
      this.wGust.add(Plugin.i18n("None"));
      this.wGust.add(Plugin.i18n("Low"));
      this.wGust.add(Plugin.i18n("Moderate"));
      this.wGust.add(Plugin.i18n("Strong"));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 11.0F, f - 1.0F, 1.3F, Plugin.i18n("Turbulence"), null));
      localGWindowDialogClient.addControl(this.wTurbulence = new GWindowComboControl(localGWindowDialogClient, f, 11.0F, 8.0F) {
        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 == 2) {
            PlMission.WConditions.this.getTurbulence();
          }
          return super.notify(paramInt1, paramInt2);
        }
      });
      this.wTurbulence.setEditable(false);
      this.wTurbulence.add(Plugin.i18n("None"));
      this.wTurbulence.add(Plugin.i18n("Low"));
      this.wTurbulence.add(Plugin.i18n("Moderate"));
      this.wTurbulence.add(Plugin.i18n("Strong"));
      this.wTurbulence.add(Plugin.i18n("VeryStrong"));

      localGWindowTabDialogClient.addTab(Plugin.i18n("Season"), localGWindowTabDialogClient.create(localGWindowDialogClient = new GWindowDialogClient()));

      f = 10.0F;

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, f - 1.0F, 1.3F, Plugin.i18n("Time"), null));
      localGWindowDialogClient.addControl(this.wTimeH = new GWindowEditControl(localGWindowDialogClient, f, 1.0F, 2.0F, 1.3F, "") {
        public void afterCreated() { super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true; }

        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 != 2) return false;
          PlMission.WConditions.this.getTime();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, f + 2.15F, 1.0F, 1.0F, 1.3F, ":", null));
      localGWindowDialogClient.addControl(this.wTimeM = new GWindowEditControl(localGWindowDialogClient, f + 2.5F, 1.0F, 2.0F, 1.3F, "") {
        public void afterCreated() { super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true; }

        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 != 2) return false;
          PlMission.WConditions.this.getTime();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, f - 1.0F, 1.3F, Plugin.i18n("Day"), null));
      localGWindowDialogClient.addControl(this.wDay = new GWindowComboControl(localGWindowDialogClient, f, 3.0F, 5.0F) {
        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 == 2) {
            PlMission.WConditions.this.getDay();
          }
          return super.notify(paramInt1, paramInt2);
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, f - 1.0F, 1.3F, Plugin.i18n("Month"), null));
      localGWindowDialogClient.addControl(this.wMonth = new GWindowComboControl(localGWindowDialogClient, f, 5.0F, 5.0F) {
        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 == 2) {
            PlMission.WConditions.this.getMonth();
          }
          return super.notify(paramInt1, paramInt2);
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 7.0F, f - 1.0F, 1.3F, Plugin.i18n("Year"), null));
      localGWindowDialogClient.addControl(this.wYear = new GWindowComboControl(localGWindowDialogClient, f, 7.0F, 5.0F) {
        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 == 2) {
            PlMission.WConditions.this.getYear();
          }
          return super.notify(paramInt1, paramInt2);
        }
      });
      this.wDay.setEditable(false);
      this.wYear.setEditable(false);
      this.wMonth.setEditable(false);

      for (int i = 0; i < PlMission.this._dayKey.length; i++)
      {
        this.wDay.add(PlMission.this._dayKey[i]);
      }
      for (i = 0; i < PlMission.this._monthKey.length; i++)
      {
        this.wMonth.add(PlMission.this._monthKey[i]);
      }

      for (i = 0; i < PlMission.this._yearKey.length; i++)
      {
        this.wYear.add(PlMission.this._yearKey[i]);
      }

      localGWindowTabDialogClient.addTab(Plugin.i18n("misc"), localGWindowTabDialogClient.create(localGWindowDialogClient = new GWindowDialogClient()));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 12.0F, 1.3F, Plugin.i18n("timeLocked"), null));
      localGWindowDialogClient.addControl(this.wTimeFix = new GWindowCheckBox(localGWindowDialogClient, 14.0F, 1.0F, null) {
        public void preRender() {
          super.preRender();
          setChecked(World.cur().isTimeOfDayConstant(), false);
        }
        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 != 2) return false;
          World.cur().setTimeOfDayConstant(isChecked());
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 12.0F, 1.3F, Plugin.i18n("weaponsLocked"), null));
      localGWindowDialogClient.addControl(this.wWeaponFix = new GWindowCheckBox(localGWindowDialogClient, 14.0F, 3.0F, null) {
        public void preRender() {
          super.preRender();
          setChecked(World.cur().isWeaponsConstant(), false);
        }
        public boolean notify(int paramInt1, int paramInt2) {
          if (paramInt1 != 2) return false;
          World.cur().setWeaponsConstant(isChecked());
          PlMission.setChanged();
          return false;
        }
      });
    }

    public void update()
    {
      float f = World.getTimeofDay();
      int i = (int)f % 24;
      int j = (int)(60.0F * (f - (int)f));
      this.wTimeH.setValue("" + i, false);
      this.wTimeM.setValue("" + j, false);
      this.wCloudType.setSelected(PlMission.this.cloudType, true, false);
      int k = (int)PlMission.this.cloudHeight;

      this.wCloudHeight.setValue("" + k, false);
      this.wTimeFix.setChecked(World.cur().isTimeOfDayConstant(), false);
      this.wWeaponFix.setChecked(World.cur().isWeaponsConstant(), false);

      this.wDay.setValue("" + PlMission.this.day, false);

      this.wMonth.setValue("" + PlMission.this.month, false);
      this.wYear.setValue("" + PlMission.this.year, false);
      this.wWindDirection.setValue("" + PlMission.this.windDirection, false);
      this.wWindVelocity.setValue("" + PlMission.this.windVelocity, false);

      int m = PlMission.this.gust;
      int n = PlMission.this.turbulence;
      if (PlMission.this.gust > 0) {
        m = (PlMission.this.gust - 6) / 2;
      }
      this.wGust.setSelected(m, true, false);

      if (PlMission.this.turbulence > 0) {
        n = PlMission.this.turbulence - 2;
      }
      this.wTurbulence.setSelected(n, true, false);

      calcWindTable(PlMission.this.cloudType, PlMission.this.cloudHeight, PlMission.this.windVelocity);
    }

    public void calcWindTable(int paramInt, float paramFloat1, float paramFloat2)
    {
      float f1 = paramFloat2;
      if (f1 == 0.0F)
        f1 = 0.25F + this.wCloudType.getSelected() * this.wCloudType.getSelected() * 0.12F;
      float f2 = paramFloat1 + 300.0F;
      float f3 = f2 / 2.0F;
      float f4 = f1 * f3 / 3000.0F + f1;
      float f5 = f1 * (f2 - f3) / 9000.0F + f4;
      int[] arrayOfInt = new int[11];

      for (int i = 0; i <= 10; i++) {
        int j = i * 1000;
        if (j > f2)
          f1 = f5 + (j - f2) * f1 / 18000.0F;
        else if (j > f3)
          f1 = f4 + (j - f3) * f1 / 9000.0F;
        else if (j > 10.0F) {
          f1 += f1 * j / 3000.0F;
        }
        if (j <= 10.0F);
        arrayOfInt[i] = (int)f1;
      }

      this.wLabel00.cap.set("" + arrayOfInt[0]);
      this.wLabel11.cap.set("" + arrayOfInt[1]);
      this.wLabel22.cap.set("" + arrayOfInt[2]);
      this.wLabel33.cap.set("" + arrayOfInt[3]);
      this.wLabel44.cap.set("" + arrayOfInt[4]);
      this.wLabel55.cap.set("" + arrayOfInt[5]);
      this.wLabel66.cap.set("" + arrayOfInt[6]);
      this.wLabel77.cap.set("" + arrayOfInt[7]);
      this.wLabel88.cap.set("" + arrayOfInt[8]);
      this.wLabel99.cap.set("" + arrayOfInt[9]);
      this.wLabel1010.cap.set("" + arrayOfInt[10]);
    }

    public void getTime() {
      String str = this.wTimeH.getValue();
      double d1 = 0.0D;
      try { d1 = Double.parseDouble(str); } catch (Exception localException1) {
      }
      if (d1 < 0.0D) d1 = 0.0D;
      if (d1 > 23.0D) d1 = 23.0D;
      str = this.wTimeM.getValue();
      double d2 = 0.0D;
      try { d2 = Double.parseDouble(str); } catch (Exception localException2) {
      }
      if (d2 < 0.0D) d2 = 0.0D;
      if (d2 >= 60.0D) d2 = 59.0D;
      float f = (float)(d1 + d2 / 60.0D);
      if ((int)(f * 60.0F) != (int)(World.getTimeofDay() * 60.0F)) {
        World.setTimeofDay(f);
        if (Plugin.builder.isLoadedLandscape())
          World.land().cubeFullUpdate();
      }
      PlMission.setChanged();
      update();
    }

    public void getCloudType() {
      PlMission.access$5502(PlMission.this, this.wCloudType.getSelected());
      Mission.setCloudsType(PlMission.this.cloudType);
      PlMission.setChanged();
      update();
    }

    public void getCloudHeight()
    {
      try
      {
        PlMission.access$5602(PlMission.this, Float.parseFloat(this.wCloudHeight.getValue())); } catch (Exception localException) {
      }
      if (PlMission.this.cloudHeight < 300.0F) PlMission.access$5602(PlMission.this, 300.0F);
      if (PlMission.this.cloudHeight > 5000.0F) PlMission.access$5602(PlMission.this, 5000.0F);

      Mission.setCloudsHeight(PlMission.this.cloudHeight);
      PlMission.setChanged();
      update();
    }

    public void getYear()
    {
      PlMission.access$5902(PlMission.this, Integer.parseInt(this.wYear.getValue()));
      Mission.setYear(PlMission.this.year);
      PlMission.setChanged();
    }

    public void getDay()
    {
      PlMission.access$5702(PlMission.this, Integer.parseInt(this.wDay.getValue()));
      Mission.setDay(PlMission.this.day);
      PlMission.setChanged();
    }

    public void getMonth()
    {
      PlMission.access$5802(PlMission.this, Integer.parseInt(this.wMonth.getValue()));
      Mission.setMonth(PlMission.this.month);
      PlMission.setChanged();
    }

    public void getWindDirection() {
      try {
        PlMission.access$6002(PlMission.this, Float.parseFloat(this.wWindDirection.getValue())); } catch (Exception localException) {
      }
      if (PlMission.this.windDirection < 0.0F) PlMission.access$6002(PlMission.this, 0.0F);
      if (PlMission.this.windDirection >= 360.0F) PlMission.access$6002(PlMission.this, 0.0F);
      Mission.setWindDirection(PlMission.this.windDirection);
      PlMission.setChanged();
      update();
    }
    public void getWindVelocity() {
      try {
        PlMission.access$6102(PlMission.this, Float.parseFloat(this.wWindVelocity.getValue())); } catch (Exception localException) {
      }
      if (PlMission.this.windVelocity > 15.0F) PlMission.access$6102(PlMission.this, 15.0F);
      if (PlMission.this.windVelocity < 0.0F) PlMission.access$6102(PlMission.this, 0.0F);
      Mission.setWindVelocity(PlMission.this.windVelocity);
      PlMission.setChanged();
      update();
    }

    public void getGust() {
      PlMission.access$6202(PlMission.this, this.wGust.getSelected());
      if (PlMission.this.gust > 0)
        PlMission.access$6202(PlMission.this, PlMission.this.gust * 2 + 6);
      float f = PlMission.this.gust * 1.0F;
      Mission.setGust(f);
      PlMission.setChanged();
    }
    public void getTurbulence() {
      PlMission.access$6302(PlMission.this, this.wTurbulence.getSelected());

      if (PlMission.this.turbulence > 0)
        PlMission.access$6312(PlMission.this, 2);
      float f = PlMission.this.turbulence * 1.0F;
      Mission.setTurbulence(f);
      PlMission.setChanged();
    }

    public void afterCreated() {
      super.afterCreated();
      resized();
      close(false);
    }

    public WConditions() {
      doNew(Plugin.builder.clientWindow, 2.0F, 2.0F, 23.0F, 45.0F, true);

      this.bSizable = true;
    }
  }

  class WFoW extends GWindowFramed
  {
    GWindowCheckBox wZutiRadar_IsRadarInAdvancedMode;
    GWindowCheckBox wZutiRadar_ShipsAsRadar;
    GWindowCheckBox wZutiRadar_ScoutsAsRadar;
    GWindowCheckBox wZutiRadar_ScoutCompleteRecon;
    GWindowEditControl wZutiRadar_ShipRadar_MaxRange;
    GWindowEditControl wZutiRadar_ShipRadar_MinHeight;
    GWindowEditControl wZutiRadar_ShipRadar_MaxHeight;
    GWindowEditControl wZutiRadar_ShipSmallRadar_MaxRange;
    GWindowEditControl wZutiRadar_ShipSmallRadar_MinHeight;
    GWindowEditControl wZutiRadar_ShipSmallRadar_MaxHeight;
    GWindowEditControl wZutiRadar_ScoutRadar_MaxRange;
    GWindowEditControl wZutiRadar_ScoutRadar_DeltaHeight;
    GWindowEditControl wZutiRadar_RefreshInterval;
    GWindowEditControl wZutiRadar_ScoutRadarType_Red;
    GWindowEditControl wZutiRadar_ScoutRadarType_Blue;
    GWindowComboControl wZutiRadar_ScoutGroundObjects_Alpha;
    GWindowButton bZutiRadar_ScoutRadarType_Red;
    GWindowButton bZutiRadar_ScoutRadarType_Blue;
    final float separateWidth = 52.0F;
    final float fowStartOf2ndCol = 21.0F;
    final float fowTextBoxWidth = 3.5F;
    final float fowStartOf3rdCol = 26.0F;
    final float fowStartOf4thCol = 46.5F;

    final float fowTitleTextW = 16.0F;
    final float fowTextW = 30.0F;
    final float fowStartCol1 = 20.0F;
    final float fowStartCol2 = 32.0F;
    final float fowStartCol3 = 44.0F;

    public void windowShown() {
      PlMission.this.mFoW.bChecked = true;
      super.windowShown();
    }
    public void windowHidden() {
      PlMission.this.mFoW.bChecked = false;
      super.windowHidden();
    }

    public void created() {
      this.bAlwaysOnTop = true;
      super.created();
      this.title = Plugin.i18n("mds.tabRadar");
      this.clientWindow = create(new GWindowDialogClient());
      GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.clientWindow;
      new GWindowBoxSeparate(localGWindowDialogClient, 1.0F, 1.0F, 52.0F, 23.0F);

      new GWindowHSeparate(localGWindowDialogClient, 4.0F, 3.5F, 48.0F);
      new GWindowHSeparate(localGWindowDialogClient, 4.0F, 11.5F, 48.0F);
      new GWindowHSeparate(localGWindowDialogClient, 2.0F, 21.5F, 50.0F);
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 4.0F, 2.0F, 20.0F, 1.3F, Plugin.i18n("mds.radar.advanced1"), null));

      localGWindowDialogClient.addControl(this.wZutiRadar_IsRadarInAdvancedMode = new GWindowCheckBox(localGWindowDialogClient, 2.0F, 2.0F, null)
      {
        public void preRender()
        {
          super.preRender();
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (PlMission.WFoW.this.wZutiRadar_ShipsAsRadar.isChecked())
          {
            PlMission.WFoW.this.wZutiRadar_ShipRadar_MaxRange.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ShipRadar_MinHeight.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ShipRadar_MaxHeight.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ShipSmallRadar_MaxRange.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ShipSmallRadar_MinHeight.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ShipSmallRadar_MaxHeight.setEnable(isChecked());
          }

          if (PlMission.WFoW.this.wZutiRadar_ScoutsAsRadar.isChecked())
          {
            PlMission.WFoW.this.wZutiRadar_ScoutRadar_MaxRange.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ScoutRadar_DeltaHeight.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ScoutGroundObjects_Alpha.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ScoutCompleteRecon.setEnable(isChecked());
          }

          if (paramInt1 != 2) {
            return false;
          }
          Mission.ZUTI_RADAR_IN_ADV_MODE = isChecked();
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 2.0F, 22.0F, 20.0F, 1.3F, Plugin.i18n("mds.radar.refresh"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 23.5F, 22.0F, 6.0F, 1.3F, Plugin.i18n("mds.radar.SEC"), null));
      localGWindowDialogClient.addControl(this.wZutiRadar_RefreshInterval = new GWindowEditControl(localGWindowDialogClient, 20.0F, 22.0F, 3.5F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2)
            return false;
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 0, 99999));
          PlMission.access$3602(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 4.0F, 4.0F, 20.0F, 1.3F, Plugin.i18n("mds.radar.shipsAsRadars"), null));
      localGWindowDialogClient.addControl(this.wZutiRadar_ShipsAsRadar = new GWindowCheckBox(localGWindowDialogClient, 2.0F, 4.0F, null)
      {
        public void preRender()
        {
          super.preRender();
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (PlMission.WFoW.this.wZutiRadar_IsRadarInAdvancedMode.isChecked())
          {
            PlMission.WFoW.this.wZutiRadar_ShipRadar_MaxRange.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ShipRadar_MinHeight.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ShipRadar_MaxHeight.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ShipSmallRadar_MaxRange.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ShipSmallRadar_MinHeight.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ShipSmallRadar_MaxHeight.setEnable(isChecked());
          }

          if (paramInt1 != 2) {
            return false;
          }
          PlMission.access$3702(PlMission.this, isChecked());
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 5.0F, 8.0F, 16.0F, 1.3F, Plugin.i18n("mds.radar.BigShips"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 5.0F, 10.0F, 16.0F, 1.3F, Plugin.i18n("mds.radar.SmallShips"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 23.5F, 8.0F, 20.0F, 1.3F, Plugin.i18n("mds.radar.KM"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 35.5F, 8.0F, 20.0F, 1.3F, Plugin.i18n("mds.radar.M"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 47.5F, 8.0F, 20.0F, 1.3F, Plugin.i18n("mds.radar.M"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 23.5F, 10.0F, 20.0F, 1.3F, Plugin.i18n("mds.radar.KM"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 35.5F, 10.0F, 20.0F, 1.3F, Plugin.i18n("mds.radar.M"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 47.5F, 10.0F, 20.0F, 1.3F, Plugin.i18n("mds.radar.M"), null));

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 13.75F, 6.0F, 16.0F, 1.3F, Plugin.i18n("mds.radar.bigShipMax"), null, 1));
      localGWindowDialogClient.addControl(this.wZutiRadar_ShipRadar_MaxRange = new GWindowEditControl(localGWindowDialogClient, 20.0F, 8.0F, 3.5F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
          setEnable(false);
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2)
            return false;
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 1, 99999));
          PlMission.access$3802(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 25.75F, 6.0F, 16.0F, 1.3F, Plugin.i18n("mds.radar.bigShipMin"), null, 1));
      localGWindowDialogClient.addControl(this.wZutiRadar_ShipRadar_MinHeight = new GWindowEditControl(localGWindowDialogClient, 32.0F, 8.0F, 3.5F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
          setEnable(false);
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2)
            return false;
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 0, 99999));
          PlMission.access$3902(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 37.75F, 6.0F, 16.0F, 1.3F, Plugin.i18n("mds.radar.bigShipMaxH"), null, 1));
      localGWindowDialogClient.addControl(this.wZutiRadar_ShipRadar_MaxHeight = new GWindowEditControl(localGWindowDialogClient, 44.0F, 8.0F, 3.5F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
          setEnable(false);
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 1000, 99999));
          PlMission.access$4002(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addControl(this.wZutiRadar_ShipSmallRadar_MaxRange = new GWindowEditControl(localGWindowDialogClient, 20.0F, 10.0F, 3.5F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
          setEnable(false);
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 1, 99999));
          PlMission.access$4102(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addControl(this.wZutiRadar_ShipSmallRadar_MinHeight = new GWindowEditControl(localGWindowDialogClient, 32.0F, 10.0F, 3.5F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
          setEnable(false);
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 0, 99999));
          PlMission.access$4202(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addControl(this.wZutiRadar_ShipSmallRadar_MaxHeight = new GWindowEditControl(localGWindowDialogClient, 44.0F, 10.0F, 3.5F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
          setEnable(false);
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 1000, 99999));
          PlMission.access$4302(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 4.0F, 12.0F, 30.0F, 1.3F, Plugin.i18n("mds.radar.scoutsAsRadars"), null));
      localGWindowDialogClient.addControl(this.wZutiRadar_ScoutsAsRadar = new GWindowCheckBox(localGWindowDialogClient, 2.0F, 12.0F, null)
      {
        public void preRender()
        {
          super.preRender();
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (PlMission.WFoW.this.wZutiRadar_IsRadarInAdvancedMode.isChecked())
          {
            PlMission.WFoW.this.wZutiRadar_ScoutRadar_MaxRange.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ScoutRadar_DeltaHeight.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ScoutGroundObjects_Alpha.setEnable(isChecked());
            PlMission.WFoW.this.wZutiRadar_ScoutCompleteRecon.setEnable(isChecked());
          }

          PlMission.WFoW.this.bZutiRadar_ScoutRadarType_Red.setEnable(isChecked());
          PlMission.WFoW.this.wZutiRadar_ScoutRadarType_Red.setEnable(isChecked());
          PlMission.WFoW.this.bZutiRadar_ScoutRadarType_Blue.setEnable(isChecked());
          PlMission.WFoW.this.wZutiRadar_ScoutRadarType_Blue.setEnable(isChecked());
          PlMission.WFoW.this.wZutiRadar_ScoutCompleteRecon.setEnable(isChecked());

          if (paramInt1 != 2) {
            return false;
          }
          PlMission.access$4402(PlMission.this, isChecked());
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 5.0F, 14.0F, 30.0F, 1.3F, Plugin.i18n("mds.radar.scoutAcScanMax"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 29.5F, 14.0F, 6.0F, 1.3F, Plugin.i18n("mds.radar.KM"), null));
      localGWindowDialogClient.addControl(this.wZutiRadar_ScoutRadar_MaxRange = new GWindowEditControl(localGWindowDialogClient, 26.0F, 14.0F, 3.5F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
          setEnable(false);
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 1, 99999));
          PlMission.access$4502(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 5.0F, 16.0F, 30.0F, 1.3F, Plugin.i18n("mds.radar.scoutAcDelta"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 29.5F, 16.0F, 6.0F, 1.3F, Plugin.i18n("mds.radar.M"), null));
      localGWindowDialogClient.addControl(this.wZutiRadar_ScoutRadar_DeltaHeight = new GWindowEditControl(localGWindowDialogClient, 26.0F, 16.0F, 3.5F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
          setEnable(false);
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 100, 99999));
          PlMission.access$4602(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 5.0F, 18.0F, 30.0F, 1.3F, Plugin.i18n("mds.radar.scoutGroundAlpha"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 29.5F, 18.0F, 6.0F, 1.3F, Plugin.i18n("mds.radar.DEG"), null));
      localGWindowDialogClient.addControl(this.wZutiRadar_ScoutGroundObjects_Alpha = new GWindowComboControl(localGWindowDialogClient, 26.0F, 18.0F, 3.5F)
      {
        public void afterCreated()
        {
          super.afterCreated();
          setEnable(false);
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          PlMission.access$4702(PlMission.this, getSelected() + 1);
          PlMission.setChanged();
          return false;
        }
      });
      this.wZutiRadar_ScoutGroundObjects_Alpha.setEditable(false);
      this.wZutiRadar_ScoutGroundObjects_Alpha.add("30");
      this.wZutiRadar_ScoutGroundObjects_Alpha.add("35");
      this.wZutiRadar_ScoutGroundObjects_Alpha.add("40");
      this.wZutiRadar_ScoutGroundObjects_Alpha.add("45");
      this.wZutiRadar_ScoutGroundObjects_Alpha.add("50");
      this.wZutiRadar_ScoutGroundObjects_Alpha.add("55");
      this.wZutiRadar_ScoutGroundObjects_Alpha.add("60");
      this.wZutiRadar_ScoutGroundObjects_Alpha.add("65");
      this.wZutiRadar_ScoutGroundObjects_Alpha.add("70");
      this.wZutiRadar_ScoutGroundObjects_Alpha.add("75");
      this.wZutiRadar_ScoutGroundObjects_Alpha.add("80");

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 5.0F, 20.0F, 30.0F, 1.3F, Plugin.i18n("mds.radar.scoutCmplRecon"), null));
      localGWindowDialogClient.addControl(this.wZutiRadar_ScoutCompleteRecon = new GWindowCheckBox(localGWindowDialogClient, 2.0F, 20.0F, null)
      {
        public void afterCreated()
        {
          super.afterCreated();
          setEnable(false);
        }

        public void preRender()
        {
          super.preRender();
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          PlMission.access$4802(PlMission.this, isChecked());
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addControl(this.bZutiRadar_ScoutRadarType_Red = new GWindowButton(localGWindowDialogClient, 32.5F, 14.0F, 20.0F, 2.0F, Plugin.i18n("mds.radar.scoutRed"), null)
      {
        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          if (PlMission.this.zuti_manageAircrafts == null)
          {
            PlMission.access$4902(PlMission.this, new Zuti_WManageAircrafts());
          }

          PlMission.this.zuti_manageAircrafts.setShowAIPlanes(true);

          if (PlMission.this.zuti_manageAircrafts.isVisible())
          {
            PlMission.this.zuti_manageAircrafts.hideWindow();
            PlMission.this.zuti_manageAircrafts.clearAirNames();
          }
          else
          {
            PlMission.this.zuti_manageAircrafts.setTitle(Plugin.i18n("mds.radar.scoutRedTitle"));
            PlMission.this.zuti_manageAircrafts.setParentEditControl(PlMission.WFoW.this.wZutiRadar_ScoutRadarType_Red);
            PlMission.this.zuti_manageAircrafts.showWindow();
          }
          return true;
        }
      });
      localGWindowDialogClient.addControl(this.wZutiRadar_ScoutRadarType_Red = new GWindowEditControl(localGWindowDialogClient, 32.0F, 17.0F, 22.5F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = false;
          this.bDelayedNotify = true;
          this.bCanEdit = false;
          hideWindow();
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          PlMission.access$5002(PlMission.this, getValue());
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addControl(this.bZutiRadar_ScoutRadarType_Blue = new GWindowButton(localGWindowDialogClient, 32.5F, 17.0F, 20.0F, 2.0F, Plugin.i18n("mds.radar.scoutBlue"), null)
      {
        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          if (PlMission.this.zuti_manageAircrafts == null)
          {
            PlMission.access$4902(PlMission.this, new Zuti_WManageAircrafts());
          }

          PlMission.this.zuti_manageAircrafts.setShowAIPlanes(true);

          if (PlMission.this.zuti_manageAircrafts.isVisible())
          {
            PlMission.this.zuti_manageAircrafts.hideWindow();
            PlMission.this.zuti_manageAircrafts.clearAirNames();
          }
          else
          {
            PlMission.this.zuti_manageAircrafts.setTitle(Plugin.i18n("mds.radar.scoutBlueTitle"));
            PlMission.this.zuti_manageAircrafts.setParentEditControl(PlMission.WFoW.this.wZutiRadar_ScoutRadarType_Blue);
            PlMission.this.zuti_manageAircrafts.showWindow();
          }
          return true;
        }
      });
      localGWindowDialogClient.addControl(this.wZutiRadar_ScoutRadarType_Blue = new GWindowEditControl(localGWindowDialogClient, 21.0F, 31.0F, 31.0F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = false;
          this.bDelayedNotify = true;
          this.bCanEdit = false;
          hideWindow();
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          PlMission.access$5102(PlMission.this, getValue());
          PlMission.setChanged();
          return false;
        }
      });
    }

    public void update()
    {
      try
      {
        this.wZutiRadar_IsRadarInAdvancedMode.setChecked(Mission.ZUTI_RADAR_IN_ADV_MODE, false);
        this.wZutiRadar_RefreshInterval.setValue(new Integer(PlMission.this.zutiRadar_RefreshInterval).toString(), false);

        this.wZutiRadar_ShipsAsRadar.setChecked(PlMission.this.zutiRadar_ShipsAsRadar, false);
        this.wZutiRadar_ShipRadar_MaxRange.setValue(new Integer(PlMission.this.zutiRadar_ShipRadar_MaxRange).toString(), false);
        this.wZutiRadar_ShipRadar_MinHeight.setValue(new Integer(PlMission.this.zutiRadar_ShipRadar_MinHeight).toString(), false);
        this.wZutiRadar_ShipRadar_MaxHeight.setValue(new Integer(PlMission.this.zutiRadar_ShipRadar_MaxHeight).toString(), false);
        this.wZutiRadar_ShipSmallRadar_MaxRange.setValue(new Integer(PlMission.this.zutiRadar_ShipSmallRadar_MaxRange).toString(), false);
        this.wZutiRadar_ShipSmallRadar_MinHeight.setValue(new Integer(PlMission.this.zutiRadar_ShipSmallRadar_MinHeight).toString(), false);
        this.wZutiRadar_ShipSmallRadar_MaxHeight.setValue(new Integer(PlMission.this.zutiRadar_ShipSmallRadar_MaxHeight).toString(), false);
        this.wZutiRadar_ScoutsAsRadar.setChecked(PlMission.this.zutiRadar_ScoutsAsRadar, false);
        this.wZutiRadar_ScoutRadar_MaxRange.setValue(new Integer(PlMission.this.zutiRadar_ScoutRadar_MaxRange).toString(), false);
        this.wZutiRadar_ScoutRadar_DeltaHeight.setValue(new Integer(PlMission.this.zutiRadar_ScoutRadar_DeltaHeight).toString(), false);
        this.wZutiRadar_ScoutRadarType_Red.setValue(PlMission.this.zutiRadar_ScoutRadarType_Red, false);
        this.wZutiRadar_ScoutRadarType_Blue.setValue(PlMission.this.zutiRadar_ScoutRadarType_Blue, false);
        this.wZutiRadar_ScoutGroundObjects_Alpha.setSelected(PlMission.this.zutiRadar_ScoutGroundObjects_Alpha - 1, true, false);
        this.wZutiRadar_ScoutCompleteRecon.setChecked(PlMission.this.zutiRadar_ScoutCompleteRecon, false);
      } catch (Exception localException) {
      }
    }

    public void afterCreated() {
      super.afterCreated();
      resized();
      close(false);
    }

    public WFoW() {
      doNew(Plugin.builder.clientWindow, 2.0F, 2.0F, 55.0F, 27.0F, true);
      this.bSizable = false;
    }
  }

  class WCraters extends GWindowFramed
  {
    GWindowLabel lSeparate_Craters;
    GWindowEditControl wZutiMisc_BombsCat1_CratersVisibilityMultiplier;
    GWindowEditControl wZutiMisc_BombsCat2_CratersVisibilityMultiplier;
    GWindowEditControl wZutiMisc_BombsCat3_CratersVisibilityMultiplier;
    GWindowBoxSeparate bSeparate_Craters;

    public void windowShown()
    {
      PlMission.this.mCraters.bChecked = true;
      super.windowShown();
    }
    public void windowHidden() {
      PlMission.this.mCraters.bChecked = false;
      super.windowHidden();
    }

    public void created() {
      this.bAlwaysOnTop = true;
      super.created();
      this.title = Plugin.i18n("mds.tabCraters");
      this.clientWindow = create(new GWindowDialogClient());
      GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.clientWindow;

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 28.0F, 1.3F, Plugin.i18n("mds.craters.cat11"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 35.0F, 1.0F, 5.0F, 1.3F, Plugin.i18n("mds.craters.cat12"), null));
      localGWindowDialogClient.addControl(this.wZutiMisc_BombsCat1_CratersVisibilityMultiplier = new GWindowEditControl(localGWindowDialogClient, 32.0F, 1.0F, 3.0F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = (this.bNumericFloat = 1);
          this.bDelayedNotify = true;
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Float.parseFloat(getValue()), 1.0F, 99999.0F));
          PlMission.access$3202(PlMission.this, Float.parseFloat(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 28.0F, 1.3F, Plugin.i18n("mds.craters.cat21"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 35.0F, 3.0F, 5.0F, 1.3F, Plugin.i18n("mds.craters.cat22"), null));
      localGWindowDialogClient.addControl(this.wZutiMisc_BombsCat2_CratersVisibilityMultiplier = new GWindowEditControl(localGWindowDialogClient, 32.0F, 3.0F, 3.0F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = (this.bNumericFloat = 1);
          this.bDelayedNotify = true;
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Float.parseFloat(getValue()), 1.0F, 99999.0F));
          PlMission.access$3302(PlMission.this, Float.parseFloat(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 28.0F, 1.3F, Plugin.i18n("mds.craters.cat31"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 35.0F, 5.0F, 5.0F, 1.3F, Plugin.i18n("mds.craters.cat32"), null));
      localGWindowDialogClient.addControl(this.wZutiMisc_BombsCat3_CratersVisibilityMultiplier = new GWindowEditControl(localGWindowDialogClient, 32.0F, 5.0F, 3.0F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = (this.bNumericFloat = 1);
          this.bDelayedNotify = true;
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Float.parseFloat(getValue()), 1.0F, 99999.0F));
          PlMission.access$3402(PlMission.this, Float.parseFloat(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
    }

    public void update() {
      try {
        this.wZutiMisc_BombsCat1_CratersVisibilityMultiplier.setValue(new Float(PlMission.this.zutiMisc_BombsCat1_CratersVisibilityMultiplier).toString(), false);
        this.wZutiMisc_BombsCat2_CratersVisibilityMultiplier.setValue(new Float(PlMission.this.zutiMisc_BombsCat2_CratersVisibilityMultiplier).toString(), false);
        this.wZutiMisc_BombsCat3_CratersVisibilityMultiplier.setValue(new Float(PlMission.this.zutiMisc_BombsCat3_CratersVisibilityMultiplier).toString(), false); } catch (Exception localException) {
      }
    }

    public void afterCreated() {
      super.afterCreated();
      resized();
      close(false);
    }

    public WCraters() {
      doNew(Plugin.builder.clientWindow, 2.0F, 2.0F, 40.0F, 9.0F, true);
      this.bSizable = true;
    }
  }

  class WRespawnTime extends GWindowFramed
  {
    GWindowBoxSeparate bSeparate_Respawn;
    GWindowEditControl wRespawnTime_Bigship;
    GWindowEditControl wRespawnTime_Ship;
    GWindowEditControl wRespawnTime_Aeroanchored;
    GWindowEditControl wRespawnTime_Artillery;
    GWindowEditControl wRespawnTime_Searchlight;
    final float separateWidth = 27.0F;
    final float respawnTextFieldStart = 20.0F;

    public void windowShown() {
      PlMission.this.mRespawnTime.bChecked = true;
      super.windowShown();
    }
    public void windowHidden() {
      PlMission.this.mRespawnTime.bChecked = false;
      super.windowHidden();
    }

    public void created() {
      this.bAlwaysOnTop = true;
      super.created();
      this.title = Plugin.i18n("mds.tabRespawn");
      this.clientWindow = create(new GWindowDialogClient());
      GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.clientWindow;

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 18.0F, 1.3F, Plugin.i18n("mds.respawn.bigship"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 24.0F, 1.0F, 18.0F, 1.3F, Plugin.i18n("mds.respawn.seconds"), null));
      localGWindowDialogClient.addControl(this.wRespawnTime_Bigship = new GWindowEditControl(localGWindowDialogClient, 18.0F, 1.0F, 5.0F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 0, 1200000));
          PlMission.access$2502(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 18.0F, 1.3F, Plugin.i18n("mds.respawn.ship"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 24.0F, 3.0F, 18.0F, 1.3F, Plugin.i18n("mds.respawn.seconds"), null));
      localGWindowDialogClient.addControl(this.wRespawnTime_Ship = new GWindowEditControl(localGWindowDialogClient, 18.0F, 3.0F, 5.0F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 0, 1200000));
          PlMission.access$2602(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 18.0F, 1.3F, Plugin.i18n("mds.respawn.aeroanchored"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 24.0F, 5.0F, 18.0F, 1.3F, Plugin.i18n("mds.respawn.seconds"), null));
      localGWindowDialogClient.addControl(this.wRespawnTime_Aeroanchored = new GWindowEditControl(localGWindowDialogClient, 18.0F, 5.0F, 5.0F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 0, 1200000));
          PlMission.access$2702(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 7.0F, 18.0F, 1.3F, Plugin.i18n("mds.respawn.artillery"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 24.0F, 7.0F, 18.0F, 1.3F, Plugin.i18n("mds.respawn.seconds"), null));
      localGWindowDialogClient.addControl(this.wRespawnTime_Artillery = new GWindowEditControl(localGWindowDialogClient, 18.0F, 7.0F, 5.0F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 0, 1200000));
          PlMission.access$2802(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 9.0F, 18.0F, 1.3F, Plugin.i18n("mds.respawn.searchlight"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 24.0F, 9.0F, 18.0F, 1.3F, Plugin.i18n("mds.respawn.seconds"), null));
      localGWindowDialogClient.addControl(this.wRespawnTime_Searchlight = new GWindowEditControl(localGWindowDialogClient, 18.0F, 9.0F, 5.0F, 1.3F, "")
      {
        public void afterCreated()
        {
          super.afterCreated();
          this.bNumericOnly = true;
          this.bDelayedNotify = true;
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          setValue(PlMission.this.checkValidRange(Integer.parseInt(getValue()), 0, 1200000));
          PlMission.access$2902(PlMission.this, Integer.parseInt(getValue()));
          PlMission.setChanged();
          return false;
        }
      });
    }

    public void update()
    {
      try {
        this.wRespawnTime_Bigship.setValue(new Integer(PlMission.this.respawnTime_Bigship).toString(), false);
        this.wRespawnTime_Ship.setValue(new Integer(PlMission.this.respawnTime_Ship).toString(), false);
        this.wRespawnTime_Aeroanchored.setValue(new Integer(PlMission.this.respawnTime_Aeroanchored).toString(), false);
        this.wRespawnTime_Artillery.setValue(new Integer(PlMission.this.respawnTime_Artillery).toString(), false);
        this.wRespawnTime_Searchlight.setValue(new Integer(PlMission.this.respawnTime_Searchlight).toString(), false);
      }
      catch (Exception localException) {
      }
    }

    public void afterCreated() {
      super.afterCreated();
      resized();
      close(false);
    }

    public WRespawnTime() {
      doNew(Plugin.builder.clientWindow, 2.0F, 2.0F, 28.0F, 13.0F, true);
      this.bSizable = true;
    }
  }

  class WMisc extends GWindowFramed
  {
    GWindowLabel lSeparate_Misc;
    GWindowBoxSeparate bSeparate_Misc;
    GWindowCheckBox wZutiRadar_EnableTowerCommunications;
    GWindowCheckBox wZutiMisc_DisableAIRadioChatter;
    GWindowCheckBox wZutiMisc_DespawnAIPlanesAfterLanding;
    GWindowCheckBox wZutiMisc_HidePlayersCountOnHomeBase;
    GWindowCheckBox wZutiRadar_HideUnpopulatedAirstripsFromMinimap;
    GWindowCheckBox wZutiRadar_DisableVectoring;
    final float separateWidth = 35.0F;
    final float miscBoxStart = 32.0F;

    public void windowShown() {
      PlMission.this.mMisc.bChecked = true;
      super.windowShown();
    }
    public void windowHidden() {
      PlMission.this.mMisc.bChecked = false;
      super.windowHidden();
    }

    public void created() {
      this.bAlwaysOnTop = true;
      super.created();
      this.title = Plugin.i18n("mds.tabMisc");
      this.clientWindow = create(new GWindowDialogClient());
      GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)this.clientWindow;

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 30.0F, 1.3F, Plugin.i18n("mds.misc.towerComms"), null));
      localGWindowDialogClient.addControl(this.wZutiRadar_EnableTowerCommunications = new GWindowCheckBox(localGWindowDialogClient, 32.0F, 1.0F, null)
      {
        public void preRender()
        {
          super.preRender();
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          PlMission.access$1702(PlMission.this, isChecked());
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 30.0F, 1.3F, Plugin.i18n("mds.misc.disableAI"), null));
      localGWindowDialogClient.addControl(this.wZutiMisc_DisableAIRadioChatter = new GWindowCheckBox(localGWindowDialogClient, 32.0F, 3.0F, null)
      {
        public void pcarrierSpawnPointsComboStarter()
        {
          super.preRender();
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          PlMission.access$1802(PlMission.this, isChecked());
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 30.0F, 1.3F, Plugin.i18n("mds.misc.despawn"), null));
      localGWindowDialogClient.addControl(this.wZutiMisc_DespawnAIPlanesAfterLanding = new GWindowCheckBox(localGWindowDialogClient, 32.0F, 5.0F, null)
      {
        public void preRender()
        {
          super.preRender();
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          PlMission.access$1902(PlMission.this, isChecked());
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 7.0F, 30.0F, 1.3F, Plugin.i18n("mds.misc.hideAirfields"), null));
      localGWindowDialogClient.addControl(this.wZutiRadar_HideUnpopulatedAirstripsFromMinimap = new GWindowCheckBox(localGWindowDialogClient, 32.0F, 7.0F, null)
      {
        public void preRender()
        {
          super.preRender();
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          PlMission.access$2002(PlMission.this, isChecked());
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 9.0F, 30.0F, 1.3F, Plugin.i18n("mds.misc.hideHBNumbers"), null));
      localGWindowDialogClient.addControl(this.wZutiMisc_HidePlayersCountOnHomeBase = new GWindowCheckBox(localGWindowDialogClient, 32.0F, 9.0F, null)
      {
        public void preRender()
        {
          super.preRender();
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          PlMission.access$2102(PlMission.this, isChecked());
          PlMission.setChanged();
          return false;
        }
      });
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 11.0F, 30.0F, 1.3F, Plugin.i18n("mds.misc.disableVectoring"), null));
      localGWindowDialogClient.addControl(this.wZutiRadar_DisableVectoring = new GWindowCheckBox(localGWindowDialogClient, 32.0F, 11.0F, null)
      {
        public void preRender()
        {
          super.preRender();
        }

        public boolean notify(int paramInt1, int paramInt2)
        {
          if (paramInt1 != 2) {
            return false;
          }
          PlMission.access$2202(PlMission.this, isChecked());
          PlMission.setChanged();
          return false;
        }
      });
    }

    public void update()
    {
      try
      {
        this.wZutiRadar_HideUnpopulatedAirstripsFromMinimap.setChecked(PlMission.this.zutiRadar_HideUnpopulatedAirstripsFromMinimap, false);
        this.wZutiRadar_DisableVectoring.setChecked(PlMission.this.zutiRadar_DisableVectoring, false);
        this.wZutiRadar_EnableTowerCommunications.setChecked(PlMission.this.zutiRadar_EnableTowerCommunications, false);
        this.wZutiMisc_DisableAIRadioChatter.setChecked(PlMission.this.zutiMisc_DisableAIRadioChatter, false);
        this.wZutiMisc_DespawnAIPlanesAfterLanding.setChecked(PlMission.this.zutiMisc_DespawnAIPlanesAfterLanding, false);
        this.wZutiMisc_HidePlayersCountOnHomeBase.setChecked(PlMission.this.zutiMisc_HidePlayersCountOnHomeBase, false);
      }
      catch (Exception localException) {
      }
    }

    public void afterCreated() {
      super.afterCreated();
      resized();
      close(false);
    }

    public WMisc() {
      doNew(Plugin.builder.clientWindow, 2.0F, 2.0F, 36.0F, 15.0F, true);
      this.bSizable = true;
    }
  }

  class DlgFileConfirmSave extends GWindowFileBoxExec
  {
    GWindowFileBox box;
    boolean bClose = true;

    DlgFileConfirmSave() {  } 
    public boolean isCloseBox() { return this.bClose; } 
    public void exec(GWindowFileBox paramGWindowFileBox, String paramString) {
      this.box = paramGWindowFileBox;
      this.bClose = true;
      if ((paramString == null) || (this.box.files.size() == 0)) {
        this.box.endExec();
        return;
      }
      int i = paramString.lastIndexOf("/");
      if (i >= 0)
        paramString = paramString.substring(i + 1);
      for (int j = 0; j < this.box.files.size(); j++) {
        String str = ((File)this.box.files.get(j)).getName();
        if (paramString.compareToIgnoreCase(str) == 0) {
          1 local1 = new GWindowMessageBox(Plugin.builder.clientWindow.root, 20.0F, true, I18N.gui("warning.Warning"), I18N.gui("warning.ReplaceFile"), 1, 0.0F)
          {
            public void result(int paramInt)
            {
              if (paramInt != 3)
                PlMission.DlgFileConfirmSave.this.bClose = false;
              PlMission.DlgFileConfirmSave.this.box.endExec();
            }
          };
          return;
        }
      }
      this.box.endExec();
    }
  }

  static class GWindowMenuItemArmy extends GWindowMenuItem
  {
    int army;

    public GWindowMenuItemArmy(GWindowMenu paramGWindowMenu, String paramString1, String paramString2, int paramInt)
    {
      super(paramString1, paramString2);
      this.army = paramInt;
    }
  }
}