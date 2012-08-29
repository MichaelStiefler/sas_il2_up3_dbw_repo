package com.maddox.il2.builder;

import com.maddox.TexImage.TexImage;
import com.maddox.gwindow.GFileFilter;
import com.maddox.gwindow.GFileFilterName;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFileBox;
import com.maddox.gwindow.GWindowFileBoxExec;
import com.maddox.gwindow.GWindowFileOpen;
import com.maddox.gwindow.GWindowFileSaveAs;
import com.maddox.gwindow.GWindowFramed;
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
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.game.GameStateStack;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.effects.SpritesFog;
import com.maddox.rts.HomePath;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.io.File;
import java.util.ArrayList;

public class PlMission extends Plugin
{
  protected static PlMission cur;
  protected int missionArmy = 1;

  private int cloudType = 0;
  private float cloudHeight = 1000.0F;

  private boolean bChanged = false;
  private String missionFileName;
  private boolean bReload = false;
  PlMapLoad pluginMapLoad;
  WConditions wConditions;
  GWindowMenuItem mConditions;
  GWindowMenuItem viewBridge;
  GWindowMenuItem viewRunaway;
  GWindowMenuItem viewName;
  GWindowMenuItem viewTime;
  GWindowMenuItem[] viewArmy;
  private String lastOpenFile;
  private GWindowMessageBox _loadMessageBox;
  private String _loadFileName;

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
    Plugin.builder.deleteAll();
    SectFile localSectFile = new SectFile(paramString, 0);
    int i = localSectFile.sectionIndex("MAIN");
    if (i < 0) {
      Plugin.builder.tipErr("MissionLoad: '" + paramString + "' - section MAIN not found");
      return false;
    }
    int j = localSectFile.varIndex(i, "MAP");
    if (j < 0) {
      Plugin.builder.tipErr("MissionLoad: '" + paramString + "' - in section MAIN line MAP not found");
      return false;
    }
    String str1 = localSectFile.value(i, j);
    PlMapLoad.Land localLand = PlMapLoad.getLandForFileName(str1);
    if (localLand == PlMapLoad.getLandLoaded()) {
      World.cur().statics.restoreAllBridges();
      World.cur().statics.restoreAllHouses();
    }
    else if (!this.pluginMapLoad.mapLoad(localLand)) {
      Plugin.builder.tipErr("MissionLoad: '" + paramString + "' - tirrain '" + str1 + "' not loaded");
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

    this.cloudType = localSectFile.get("MAIN", "CloudType", 0, 0, 6);
    this.cloudHeight = localSectFile.get("MAIN", "CloudHeight", 1000.0F, 500.0F, 1500.0F);
    Mission.createClouds(this.cloudType, this.cloudHeight);
    if (Main3D.cur3D().clouds != null) {
      Main3D.cur3D().clouds.setShow(false);
    }
    Main3D.cur3D().spritesFog.setShow(false);

    this.wConditions.update();

    Plugin.doLoad(localSectFile);

    if (str2 != null) {
      Object[] arrayOfObject = Plugin.builder.pathes.getOwnerAttached();
      for (int n = 0; n < arrayOfObject.length; n++) {
        Path localPath = (Path)arrayOfObject[n];
        if (str2.equals(localPath.name())) {
          if (((PathAir)localPath).bOnlyAI) break;
          Path.player = localPath;
          this.missionArmy = localPath.getArmy(); break;
        }
      }

    }

    Plugin.builder.repaint();
    this.bChanged = false;
    return true;
  }

  public boolean save(String paramString) {
    if (PlMapLoad.getLandLoaded() == null) {
      Plugin.builder.tipErr("MissionSave: tirrain not selected");
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

    if (!Plugin.doSave(localSectFile)) {
      return false;
    }
    localSectFile.saveFile(paramString);
    this.bChanged = false;
    return true;
  }

  public void mapLoaded() {
    if (!Plugin.builder.isLoadedLandscape()) return;

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
  }

  public void configure() {
    Plugin.builder.bMultiSelect = false;
    if (Plugin.getPlugin("MapLoad") == null)
      throw new RuntimeException("PlMission: plugin 'MapLoad' not found");
    this.pluginMapLoad = ((PlMapLoad)Plugin.getPlugin("MapLoad"));
  }

  void _viewTypeAll(boolean paramBoolean)
  {
    Plugin.doViewTypeAll(paramBoolean);
    viewBridge(paramBoolean);
    viewRunaway(paramBoolean);
    this.viewName.bChecked = (Plugin.builder.conf.bShowName = paramBoolean);
    this.viewTime.bChecked = (Plugin.builder.conf.bShowTime = paramBoolean);
    for (int i = 0; i < Plugin.builder.conf.bShowArmy.length; i++)
      this.viewArmy[i].bChecked = (Plugin.builder.conf.bShowArmy[i] = paramBoolean);
    if (!paramBoolean)
      Plugin.builder.setSelected(null);
  }

  void viewBridge(boolean paramBoolean) {
    Plugin.builder.conf.bViewBridge = paramBoolean;
    this.viewBridge.bChecked = Plugin.builder.conf.bViewBridge;
  }
  void viewBridge() { viewBridge(!Plugin.builder.conf.bViewBridge); }

  void viewRunaway(boolean paramBoolean) {
    Plugin.builder.conf.bViewRunaway = paramBoolean;
    this.viewRunaway.bChecked = Plugin.builder.conf.bViewRunaway;
  }
  void viewRunaway() { viewRunaway(!Plugin.builder.conf.bViewRunaway); }

  public static void checkShowCurrentArmy() {
    Object localObject = Plugin.builder.selectedPath();
    if (localObject == null)
      localObject = Plugin.builder.selectedActor();
    if (localObject == null) return;
    int i = ((Actor)localObject).getArmy();
    if (Plugin.builder.conf.bShowArmy[i] == 0)
      Plugin.builder.setSelected(null);
  }

  private String checkMisExtension(String paramString)
  {
    if (!paramString.toLowerCase().endsWith(".mis"))
      return paramString + ".mis";
    return paramString;
  }

  public void createGUI() {
    Plugin.builder.mDisplayFilter.subMenu.addItem("-", null);
    this.viewBridge = Plugin.builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, Plugin.i18n("showBridge"), Plugin.i18n("TIPshowBridge")) {
      public void execute() {
        PlMission.this.viewBridge();
      }
    });
    this.viewBridge.bChecked = Plugin.builder.conf.bViewBridge;
    this.viewRunaway = Plugin.builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, Plugin.i18n("showRunway"), Plugin.i18n("TIPshowRunway")) {
      public void execute() {
        PlMission.this.viewRunaway();
      }
    });
    this.viewRunaway.bChecked = Plugin.builder.conf.bViewRunaway;
    Plugin.builder.mDisplayFilter.subMenu.addItem("-", null);
    this.viewName = Plugin.builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, Plugin.i18n("showName"), Plugin.i18n("TIPshowName")) {
      public void execute() {
        this.bChecked = (Plugin.builder.conf.bShowName = !Plugin.builder.conf.bShowName ? 1 : 0);
      }
    });
    this.viewName.bChecked = Plugin.builder.conf.bShowName;
    this.viewTime = Plugin.builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, Plugin.i18n("showTime"), Plugin.i18n("TIPshowTime")) {
      public void execute() {
        this.bChecked = (Plugin.builder.conf.bShowTime = !Plugin.builder.conf.bShowTime ? 1 : 0);
      }
    });
    this.viewTime.bChecked = Plugin.builder.conf.bShowTime;

    this.viewArmy = new GWindowMenuItemArmy[Builder.armyAmount()];
    for (int i = 0; i < Builder.armyAmount(); i++) {
      this.viewArmy[i] = Plugin.builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItemArmy(Plugin.builder.mDisplayFilter.subMenu, Plugin.i18n("showArmy") + " " + I18N.army(Army.name(i)), Plugin.i18n("TIPshowArmy") + " " + I18N.army(Army.name(i)), i)
      {
        public void execute() {
          this.bChecked = (Plugin.builder.conf.bShowArmy[this.jdField_army_of_type_Int] = Plugin.builder.conf.bShowArmy[this.jdField_army_of_type_Int] == 0 ? 1 : 0);
          PlMission.checkShowCurrentArmy();
        }
      });
      this.viewArmy[i].bChecked = Plugin.builder.conf.bShowArmy[i];
    }
    Plugin.builder.mDisplayFilter.subMenu.addItem("-", null);
    Plugin.builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, Plugin.i18n("&ShowAll"), Plugin.i18n("TIPShowAll")) {
      public void execute() {
        PlMission.this._viewTypeAll(true);
      }
    });
    Plugin.builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, Plugin.i18n("&HideAll"), Plugin.i18n("TIPHideAll")) {
      public void execute() {
        PlMission.this._viewTypeAll(false);
      }
    });
    Plugin.builder.mFile.subMenu.addItem(1, new GWindowMenuItem(Plugin.builder.mFile.subMenu, Plugin.i18n("Load"), Plugin.i18n("TIPLoad"))
    {
      public void execute() {
        PlMission.this.doDlgLoadMission();
      }
    });
    Plugin.builder.mFile.subMenu.addItem(2, new GWindowMenuItem(Plugin.builder.mFile.subMenu, Plugin.i18n("Save"), Plugin.i18n("TIPSaveAs"))
    {
      public void execute() {
        if (PlMission.this.missionFileName != null) {
          PlMission.this.save("missions/" + PlMission.this.missionFileName);
        } else {
          PlMission.10 local10 = new PlMission.10(this, this.root, true, Plugin.i18n("SaveMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) });

          local10.exec = new PlMission.DlgFileConfirmSave(PlMission.this);
          if (PlMission.this.lastOpenFile != null)
            local10.setSelectFile(PlMission.this.lastOpenFile);
        }
      }
    });
    Plugin.builder.mFile.subMenu.addItem(3, new GWindowMenuItem(Plugin.builder.mFile.subMenu, Plugin.i18n("SaveAs"), Plugin.i18n("TIPSaveAs"))
    {
      public void execute() {
        PlMission.12 local12 = new PlMission.12(this, this.root, true, Plugin.i18n("SaveMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) });

        local12.exec = new PlMission.DlgFileConfirmSave(PlMission.this);
        if (PlMission.this.lastOpenFile != null)
          local12.setSelectFile(PlMission.this.lastOpenFile);
      }
    });
    Plugin.builder.mFile.subMenu.addItem(4, new GWindowMenuItem(Plugin.builder.mFile.subMenu, Plugin.i18n("Play"), Plugin.i18n("TIPPlay")) {
      public void execute() {
        if (!Plugin.builder.isLoadedLandscape()) return;
        if ((PlMission.isChanged()) || (PlMission.this.missionFileName == null)) {
          if (PlMission.this.missionFileName != null) {
            if (PlMission.this.save("missions/" + PlMission.this.missionFileName))
              PlMission.this.playMission();
          } else {
            PlMission.14 local14 = new PlMission.14(this, this.root, true, Plugin.i18n("SaveMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) });

            local14.exec = new PlMission.DlgFileConfirmSave(PlMission.this);
            if (PlMission.this.lastOpenFile != null)
              local14.setSelectFile(PlMission.this.lastOpenFile);
          }
        }
        else PlMission.this.playMission();
      }
    });
    Plugin.builder.mFile.subMenu.bNotify = true;
    Plugin.builder.mFile.subMenu.addNotifyListener(new GNotifyListener()
    {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        if (paramInt1 != 13) return false;
        Plugin.builder.mFile.subMenu.getItem(2).bEnable = Plugin.builder.isLoadedLandscape();
        Plugin.builder.mFile.subMenu.getItem(3).bEnable = Plugin.builder.isLoadedLandscape();
        Plugin.builder.mFile.subMenu.getItem(4).bEnable = Plugin.builder.isLoadedLandscape();
        return false;
      }
    });
    this.mConditions = Plugin.builder.mEdit.subMenu.addItem(0, new GWindowMenuItem(Plugin.builder.mEdit.subMenu, Plugin.i18n("&Conditions"), Plugin.i18n("TIPConditions"))
    {
      public void execute() {
        if (PlMission.this.wConditions.isVisible()) PlMission.this.wConditions.hideWindow(); else
          PlMission.this.wConditions.showWindow();
      }
    });
    Plugin.builder.mEdit.subMenu.addItem(1, "-", null);
    this.wConditions = new WConditions();
    this.wConditions.update();
  }

  private void doLoadMission(String paramString)
  {
    this._loadFileName = paramString;
    this._loadMessageBox = new GWindowMessageBox(Plugin.builder.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, Plugin.i18n("StandBy"), Plugin.i18n("LoadingMission"), 4, 0.0F);

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
    new GWindowMessageBox(Plugin.builder.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, Plugin.i18n("LoadMission"), Plugin.i18n("ConfirmExitMsg"), 1, 0.0F)
    {
      public void result(int paramInt)
      {
        if (paramInt == 3) {
          if (PlMission.this.missionFileName != null) {
            PlMission.this.save("missions/" + PlMission.this.missionFileName);
            PlMission.this._doDlgLoadMission();
          } else {
            PlMission.20 local20 = new PlMission.20(this, this.root, true, Plugin.i18n("SaveMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) });

            local20.exec = new PlMission.DlgFileConfirmSave(PlMission.this);
            if (PlMission.this.lastOpenFile != null)
              local20.setSelectFile(PlMission.this.lastOpenFile);
          }
        }
        else
          PlMission.this._doDlgLoadMission();
      }
    };
  }

  private void _doDlgLoadMission() {
    21 local21 = new GWindowFileOpen(Plugin.builder.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, true, Plugin.i18n("LoadMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) })
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
      local21.setSelectFile(this.lastOpenFile);
  }

  public boolean exitBuilder() {
    if (!isChanged())
      return true;
    new GWindowMessageBox(Plugin.builder.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, Plugin.i18n("ConfirmExit"), Plugin.i18n("ConfirmExitMsg"), 1, 0.0F)
    {
      public void result(int paramInt)
      {
        if (paramInt == 3) {
          if (PlMission.this.missionFileName != null) {
            PlMission.this.save("missions/" + PlMission.this.missionFileName);
            Plugin.builder.doMenu_FileExit();
          } else {
            PlMission.23 local23 = new PlMission.23(this, this.root, true, Plugin.i18n("SaveMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) });

            local23.exec = new PlMission.DlgFileConfirmSave(PlMission.this);
            if (PlMission.this.lastOpenFile != null)
              local23.setSelectFile(PlMission.this.lastOpenFile);
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
      ((GWindowRootMenu)Plugin.builder.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot).statusBar.setDefaultHelp(this.missionFileName);
      ((PlMapLoad)Plugin.getPlugin("MapLoad")).guiMapLoad();
      return;
    }
    new GWindowMessageBox(Plugin.builder.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, Plugin.i18n("SaveMission"), Plugin.i18n("ConfirmExitMsg"), 1, 0.0F)
    {
      public void result(int paramInt)
      {
        if (paramInt == 3) {
          if (PlMission.this.missionFileName != null) {
            PlMission.this.save("missions/" + PlMission.this.missionFileName);
            ((PlMapLoad)Plugin.getPlugin("MapLoad")).guiMapLoad();
          } else {
            PlMission.25 local25 = new PlMission.25(this, this.root, true, Plugin.i18n("SaveMission"), "missions", new GFileFilter[] { new GFileFilterName(Plugin.i18n("MissionFiles"), new String[] { "*.mis" }) });

            local25.exec = new PlMission.DlgFileConfirmSave(PlMission.this);
            if (PlMission.this.lastOpenFile != null)
              local25.setSelectFile(PlMission.this.lastOpenFile);
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
      ((GWindowRootMenu)Plugin.builder.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot).statusBar.setDefaultHelp(this.missionFileName);
    }
  }

  public PlMission()
  {
    cur = this;
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
    GWindowComboControl wCloudHeight;
    GWindowCheckBox wTimeFix;
    GWindowCheckBox wWeaponFix;

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
      this.jdField_clientWindow_of_type_ComMaddoxGwindowGWindow = create(new GWindowTabDialogClient());
      GWindowTabDialogClient localGWindowTabDialogClient = (GWindowTabDialogClient)this.jdField_clientWindow_of_type_ComMaddoxGwindowGWindow;
      GWindowDialogClient localGWindowDialogClient = null;
      localGWindowTabDialogClient.addTab(Plugin.i18n("weather"), localGWindowTabDialogClient.create(localGWindowDialogClient = new GWindowDialogClient()));

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 4.0F, 1.3F, Plugin.i18n("Time"), null));
      localGWindowDialogClient.addControl(this.wTimeH = new PlMission.26(this, localGWindowDialogClient, 6.0F, 1.0F, 2.0F, 1.3F, ""));

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 8.2F, 1.0F, 1.0F, 1.3F, ":", null));
      localGWindowDialogClient.addControl(this.wTimeM = new PlMission.27(this, localGWindowDialogClient, 8.5F, 1.0F, 2.0F, 1.3F, ""));

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 6.0F, 1.3F, Plugin.i18n("Weather"), null));
      localGWindowDialogClient.addControl(this.wCloudType = new PlMission.28(this, localGWindowDialogClient, 8.0F, 3.0F, 8.0F));

      this.wCloudType.setEditable(false);
      this.wCloudType.add(Plugin.i18n("Clear"));
      this.wCloudType.add(Plugin.i18n("Good"));
      this.wCloudType.add(Plugin.i18n("Hazy"));
      this.wCloudType.add(Plugin.i18n("Poor"));
      this.wCloudType.add(Plugin.i18n("Blind"));
      this.wCloudType.add(Plugin.i18n("Rain/Snow"));
      this.wCloudType.add(Plugin.i18n("Thunder"));

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 9.0F, 1.3F, Plugin.i18n("CloudHeight"), null));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 16.5F, 5.0F, 2.0F, 1.3F, Plugin.i18n("[m]"), null));
      localGWindowDialogClient.addControl(this.wCloudHeight = new PlMission.29(this, localGWindowDialogClient, 11.0F, 5.0F, 5.0F));

      this.wCloudHeight.setEditable(false);
      this.wCloudHeight.add("500");
      this.wCloudHeight.add("600");
      this.wCloudHeight.add("700");
      this.wCloudHeight.add("800");
      this.wCloudHeight.add("900");
      this.wCloudHeight.add("1000");
      this.wCloudHeight.add("1100");
      this.wCloudHeight.add("1200");
      this.wCloudHeight.add("1300");
      this.wCloudHeight.add("1400");
      this.wCloudHeight.add("1500");

      localGWindowTabDialogClient.addTab(Plugin.i18n("misc"), localGWindowTabDialogClient.create(localGWindowDialogClient = new GWindowDialogClient()));
      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 12.0F, 1.3F, Plugin.i18n("timeLocked"), null));
      localGWindowDialogClient.addControl(this.wTimeFix = new PlMission.30(this, localGWindowDialogClient, 14.0F, 1.0F, null));

      localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 12.0F, 1.3F, Plugin.i18n("weaponsLocked"), null));
      localGWindowDialogClient.addControl(this.wWeaponFix = new PlMission.31(this, localGWindowDialogClient, 14.0F, 3.0F, null));
    }

    public void update()
    {
      float f = World.getTimeofDay();
      int i = (int)f % 24;
      int j = (int)(60.0F * (f - (int)f));
      this.wTimeH.setValue("" + i, false);
      this.wTimeM.setValue("" + j, false);
      this.wCloudType.setSelected(PlMission.this.cloudType, true, false);
      int k = (int)((PlMission.this.cloudHeight - 500.0F + 50.0F) / 100.0F);
      if (k < 0) k = 0;
      if (k > 10) k = 10;
      this.wCloudHeight.setSelected(k, true, false);
      this.wTimeFix.setChecked(World.cur().isTimeOfDayConstant(), false);
      this.wWeaponFix.setChecked(World.cur().isWeaponsConstant(), false);
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
      PlMission.access$1602(PlMission.this, this.wCloudType.getSelected());
      Mission.setCloudsType(PlMission.this.cloudType);
      PlMission.setChanged();
    }
    public void getCloudHeight() {
      PlMission.access$1702(PlMission.this, 500 + 100 * this.wCloudHeight.getSelected());
      Mission.setCloudsHeight(PlMission.this.cloudHeight);
      PlMission.setChanged();
    }

    public void afterCreated() {
      super.afterCreated();
      resized();
      close(false);
    }

    public WConditions() {
      doNew(Plugin.builder.clientWindow, 2.0F, 2.0F, 20.0F, 12.0F, true);
      this.bSizable = false;
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
          PlMission.18 local18 = new PlMission.18(this, Plugin.builder.clientWindow.root, 20.0F, true, I18N.gui("warning.Warning"), I18N.gui("warning.ReplaceFile"), 1, 0.0F);

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