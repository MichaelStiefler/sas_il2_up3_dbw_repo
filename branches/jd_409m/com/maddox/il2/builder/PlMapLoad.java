package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.TexImage.TexImage;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBar;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMessageBox;
import com.maddox.gwindow.GWindowRootMenu;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Land2D;
import com.maddox.il2.engine.Land2Dn;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.engine.TextScr;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Runaway;
import com.maddox.il2.tools.BridgesGenerator;
import com.maddox.rts.HomePath;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import java.io.PrintStream;
import java.util.ArrayList;

public class PlMapLoad extends Plugin
{
  private static ArrayList lands = new ArrayList();

  private static int landLoaded = -1;

  public static ArrayList bridgeActors = new ArrayList();
  public static boolean bDrawNumberBridge = false;

  private static Point3d _p3d = new Point3d();
  private Point2d p2d;
  MenuItem[] menuItem;
  private GWindowMessageBox loadMessageBox;
  private Land _guiLand;

  public PlMapLoad()
  {
    this.p2d = new Point2d();
  }

  public static Land getLandLoaded()
  {
    if (landLoaded < 0) return null;
    return (Land)lands.get(landLoaded);
  }

  public static Land getLandForKeyName(String paramString) {
    for (int i = 0; i < lands.size(); i++) {
      Land localLand = (Land)lands.get(i);
      if (localLand.keyName.equals(paramString))
        return localLand;
    }
    return null;
  }

  public static Land getLandForFileName(String paramString) {
    for (int i = 0; i < lands.size(); i++) {
      Land localLand = (Land)lands.get(i);
      if (localLand.fileName.equals(paramString))
        return localLand;
    }
    return null;
  }

  public static String mapKeyName() {
    Land localLand = getLandLoaded();
    if (localLand == null) return null;
    return localLand.keyName;
  }
  public static String mapI18nName() {
    Land localLand = getLandLoaded();
    if (localLand == null) return null;
    return localLand.i18nName;
  }
  public static String mapFileName() {
    Land localLand = getLandLoaded();
    if (localLand == null) return null;
    return localLand.fileName;
  }
  public static String mapDirName() {
    Land localLand = getLandLoaded();
    if (localLand == null) return null;
    return localLand.dirName;
  }

  private static void bridgesClear()
  {
    for (int i = 0; i < bridgeActors.size(); i++) {
      Actor localActor = (Actor)bridgeActors.get(i);
      localActor.destroy();
    }
    bridgeActors.clear();
  }

  public static void bridgesCreate(TexImage paramTexImage) {
    bridgesClear();
    if (paramTexImage != null) {
      com.maddox.il2.tools.Bridge[] arrayOfBridge = BridgesGenerator.getBridgesArray(paramTexImage);
      for (int i = 0; i < arrayOfBridge.length; i++) {
        com.maddox.il2.objects.bridges.Bridge localBridge = new com.maddox.il2.objects.bridges.Bridge(i, arrayOfBridge[i].type, arrayOfBridge[i].x1, arrayOfBridge[i].y1, arrayOfBridge[i].x2, arrayOfBridge[i].y2, 0.0F);

        Property.set(localBridge, "builderSpawn", "");
        bridgeActors.add(localBridge);

        _p3d.jdField_x_of_type_Double = World.land().PIX2WORLDX((arrayOfBridge[i].x1 + arrayOfBridge[i].x2) / 2);
        _p3d.jdField_y_of_type_Double = World.land().PIX2WORLDY((arrayOfBridge[i].y1 + arrayOfBridge[i].y2) / 2);
        _p3d.z = 0.0D;
        PlMapLabel.insert(_p3d);
      }
      System.out.println("" + arrayOfBridge.length + " bridges created");
    }
  }

  public void mapUnload()
  {
    landLoaded = -1;
    clearMenuItems();
    bridgesClear();
    Plugin.doMapLoaded();
    PathFind.unloadMap();
  }

  public boolean mapLoad(Land paramLand) {
    if (getLandLoaded() == paramLand)
      return true;
    Plugin.builder.deleteAll();
    bridgesClear();
    landLoaded = -1;
    clearMenuItems();
    PathFind.unloadMap();
    Main3D.cur3D().resetGame();
    Plugin.builder.tip(Plugin.i18n("Loading") + " " + paramLand.i18nName + "...");
    SectFile localSectFile = new SectFile("maps/" + paramLand.fileName, 0);
    int i = localSectFile.sectionIndex("MAP2D");
    if (i < 0) {
      Plugin.builder.tipErr("section [MAP2D] not found in 'maps/" + paramLand.fileName);
      return false;
    }
    int j = localSectFile.vars(i);
    if (j == 0) {
      Plugin.builder.tipErr("section [MAP2D] in 'maps/" + paramLand.fileName + " is empty");
      return false;
    }
    try
    {
      if (Plugin.builder.bMultiSelect) {
        World.land().LoadMap(paramLand.fileName, null);
      } else {
        int[] arrayOfInt = null;
        m = localSectFile.sectionIndex("static");
        if ((m >= 0) && (localSectFile.vars(m) > 0)) {
          String str1 = localSectFile.var(m, 0);
          if ((str1 != null) && (str1.length() > 0)) {
            str1 = HomePath.concatNames("maps/" + paramLand.fileName, str1);
            arrayOfInt = Statics.readBridgesEndPoints(str1);
          }
        }
        World.land().LoadMap(paramLand.fileName, arrayOfInt);
      }
    } catch (Exception localException1) {
      Plugin.builder.tipErr("World.land().LoadMap() error: " + localException1);
      return false;
    }

    World.cur().setCamouflage(World.land().config.camouflage);

    if (Main3D.cur3D().land2D != null) {
      if (!Main3D.cur3D().land2D.isDestroyed())
        Main3D.cur3D().land2D.destroy();
      Main3D.cur3D().land2D = null;
    }
    Main3D.cur3D().land2D = new Land2Dn(paramLand.fileName, World.land().getSizeX(), World.land().getSizeY());
    Plugin.builder.computeViewMap2D(-1.0D, 0.0D, 0.0D);

    PathFind.tShip = new TexImage();
    PathFind.tNoShip = new TexImage();

    int k = 0;
    int m = localSectFile.sectionIndex("TMAPED");
    if (m >= 0) {
      int n = localSectFile.vars(m);
      if (n > 0) {
        String str2 = "maps/" + paramLand.dirName + "/" + localSectFile.var(m, 0);
        try {
          PathFind.tShip.LoadTGA(str2);
          PathFind.tNoShip.LoadTGA(str2);

          TexImage localTexImage = new TexImage();
          localTexImage.LoadTGA("maps/" + paramLand.dirName + "/" + World.land().config.typeMap);
          for (i4 = 0; i4 < localTexImage.sy; i4++) {
            for (i5 = 0; i5 < localTexImage.sx; i5++) {
              int i6 = localTexImage.I(i5, i4) & 0xE0;
              if (i6 != 0) {
                PathFind.tShip.I(i5, i4, PathFind.tShip.intI(i5, i4) & 0xFFFFFF1F | i6);
                PathFind.tNoShip.I(i5, i4, PathFind.tNoShip.intI(i5, i4) & 0xFFFFFF1F | i6);
              }
            }
          }
          k = 1; } catch (Exception localException3) {
        }
      }
    }
    if (k == 0) {
      try {
        PathFind.tShip.LoadTGA("maps/" + paramLand.dirName + "/" + World.land().config.typeMap);
        PathFind.tNoShip.LoadTGA("maps/" + paramLand.dirName + "/" + World.land().config.typeMap);
      }
      catch (Exception localException2)
      {
      }
    }
    for (int i1 = 0; i1 < PathFind.tShip.sy; i1++) {
      for (int i2 = 0; i2 < PathFind.tShip.sx; i2++)
      {
        if ((PathFind.tShip.I(i2, i1) & 0x1C) == 24) PathFind.tShip.I(i2, i1, PathFind.tShip.intI(i2, i1) & 0xFFFFFFE3);
        if ((PathFind.tNoShip.I(i2, i1) & 0x1C) != 24) continue; PathFind.tNoShip.I(i2, i1, PathFind.tNoShip.intI(i2, i1) & 0xFFFFFFE3);
      }
    }

    Landscape localLandscape = World.land();
    for (int i3 = 0; i3 < PathFind.tShip.sy; i3++) {
      for (i4 = 0; i4 < PathFind.tShip.sx; i4++) {
        if (((PathFind.tShip.intI(i4, i3) & 0x1C) != 28) || 
          (Landscape.estimateNoWater(i4, i3, 128) <= 255 - Plugin.builder.conf.iWaterLevel)) continue;
        PathFind.tShip.I(i4, i3, PathFind.tShip.intI(i4, i3) & 0xFFFFFFE3);
      }
    }

    for (int i4 = 0; i4 < PathFind.tNoShip.sy; i4++) {
      for (i5 = 0; i5 < PathFind.tNoShip.sx; i5++) {
        if (((PathFind.tNoShip.intI(i5, i4) & 0x1C) != 28) || 
          (Landscape.estimateNoWater(i5, i4, 128) <= 250)) continue;
        PathFind.tNoShip.I(i5, i4, PathFind.tNoShip.intI(i5, i4) & 0xFFFFFFE3);
      }

    }

    Plugin.builder.tip(paramLand.i18nName);
    landLoaded = paramLand.indx;
    if (this.menuItem != null) {
      for (i5 = 0; i5 < this.menuItem.length; i5++) {
        this.menuItem[i5].jdField_bChecked_of_type_Boolean = (i5 == landLoaded);
      }
    }
    Plugin.doMapLoaded();

    PathFind.b = new com.maddox.il2.tools.Bridge[bridgeActors.size()];
    for (int i5 = 0; i5 < bridgeActors.size(); i5++) {
      com.maddox.il2.objects.bridges.Bridge localBridge = (com.maddox.il2.objects.bridges.Bridge)bridgeActors.get(i5);
      int i7 = localBridge.__indx;
      PathFind.b[i7] = new com.maddox.il2.tools.Bridge();
      PathFind.b[i7].x1 = localBridge.__x1;
      PathFind.b[i7].y1 = localBridge.__y1;
      PathFind.b[i7].x2 = localBridge.__x2;
      PathFind.b[i7].y2 = localBridge.__y2;
      PathFind.b[i7].type = localBridge.__type;
    }

    PathFind.setMoverType(0);
    return true;
  }

  public void renderMap2D() {
    if (Plugin.builder.isFreeView()) return;
    if (getLandLoaded() == null) return;
    if (Plugin.builder.conf.bViewBridge) {
      Render.prepareStates();
      IconDraw.setColor(255, 255, 255, 255);
      for (int i = 0; i < bridgeActors.size(); i++) {
        com.maddox.il2.objects.bridges.Bridge localBridge1 = (com.maddox.il2.objects.bridges.Bridge)bridgeActors.get(i);
        if (Plugin.builder.project2d(localBridge1.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), this.p2d)) {
          IconDraw.render(localBridge1, this.p2d.jdField_x_of_type_Double, this.p2d.jdField_y_of_type_Double);
        }
      }
      if ((bDrawNumberBridge) || (Plugin.builder.bMultiSelect)) {
        for (int j = 0; j < bridgeActors.size(); j++) {
          com.maddox.il2.objects.bridges.Bridge localBridge2 = (com.maddox.il2.objects.bridges.Bridge)bridgeActors.get(j);
          if (Plugin.builder.project2d(localBridge2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), this.p2d)) {
            TextScr.font().output(-16711936, (int)this.p2d.jdField_x_of_type_Double + IconDraw.scrSizeX() / 2 + 2, (int)this.p2d.jdField_y_of_type_Double - IconDraw.scrSizeY() / 2 - 2, 0.0F, "" + localBridge2.__indx);
          }
        }

      }

    }

    if (Plugin.builder.conf.bViewRunaway) {
      IconDraw.setColor(255, 255, 255, 255);
      Runaway localRunaway = World.cur().runawayList;
      while (localRunaway != null) {
        if (Plugin.builder.project2d(localRunaway.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), this.p2d))
          IconDraw.render(localRunaway, this.p2d.jdField_x_of_type_Double, this.p2d.jdField_y_of_type_Double);
        localRunaway = localRunaway.next();
      }
    }
  }

  private void clearMenuItems()
  {
    if (this.menuItem != null)
      for (int i = 0; i < this.menuItem.length; i++)
        this.menuItem[i].jdField_bChecked_of_type_Boolean = false;
  }

  public void createGUI()
  {
    GWindowRootMenu localGWindowRootMenu = (GWindowRootMenu)Plugin.builder.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot;
    GWindowMenuBarItem localGWindowMenuBarItem = localGWindowRootMenu.menuBar.getItem(0);
    GWindowMenuItem localGWindowMenuItem = localGWindowMenuBarItem.subMenu.addItem(0, new GWindowMenuItem(localGWindowMenuBarItem.subMenu, Plugin.i18n("&MapLoad"), Plugin.i18n("TIPLoadLandscape")));
    localGWindowMenuItem.subMenu = ((GWindowMenu)localGWindowMenuItem.create(new GWindowMenu()));
    localGWindowMenuItem.subMenu.close(false);
    int i = lands.size();
    this.menuItem = new MenuItem[i];
    for (int j = 0; j < i; j++) {
      Land localLand = (Land)lands.get(j);
      localGWindowMenuItem.subMenu.addItem(this.menuItem[j] =  = new MenuItem(localGWindowMenuItem.subMenu, localLand.i18nName, null, j));
      this.menuItem[j].jdField_bChecked_of_type_Boolean = false;
    }
  }

  public void guiMapLoad()
  {
    guiMapLoad(this._guiLand);
  }

  public void guiMapLoad(Land paramLand) {
    this._guiLand = paramLand;
    this.loadMessageBox = new GWindowMessageBox(Plugin.builder.clientWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot, 20.0F, true, Plugin.i18n("StandBy"), Plugin.i18n("LoadingLandscape") + " " + paramLand.i18nName, 4, 0.0F);

    new MsgAction(72, 0.0D) {
      public void doAction() { PlMapLoad.this.mapLoad(PlMapLoad.this._guiLand);
        PlMapLoad.this.loadMessageBox.close(false); PlMapLoad.access$202(PlMapLoad.this, null);
      }
    };
  }

  public void configure()
  {
    SectFile localSectFile = new SectFile("maps/all.ini", 0);

    int i = localSectFile.sectionIndex("all");
    if (i < 0) return;
    int j = localSectFile.vars(i);
    for (int k = 0; k < j; k++) {
      Land localLand = new Land();
      localLand.indx = k;
      localLand.keyName = localSectFile.var(i, k);
      localLand.fileName = localSectFile.value(i, k);
      localLand.dirName = localLand.fileName.substring(0, localLand.fileName.lastIndexOf("/"));
      localLand.i18nName = I18N.map(localLand.keyName);
      lands.add(localLand);
    }
  }

  static {
    Property.set(PlMapLoad.class, "name", "MapLoad");
  }

  class MenuItem extends GWindowMenuItem
  {
    int indx;

    public void execute()
    {
      PlMapLoad.Land localLand = (PlMapLoad.Land)PlMapLoad.lands.get(this.indx);
      if (localLand == PlMapLoad.getLandLoaded())
        return;
      if (!Plugin.builder.bMultiSelect) {
        PlMapLoad.access$102(PlMapLoad.this, localLand);
        ((PlMission)Plugin.getPlugin("Mission")).loadNewMap();
      } else {
        PlMapLoad.this.guiMapLoad(localLand);
      }
    }

    public MenuItem(GWindowMenu paramString1, String paramString2, String paramInt, int arg5) {
      super(paramString2, paramInt);
      int i;
      this.indx = i;
    }
  }

  public static class Land
  {
    public int indx;
    public String keyName;
    public String i18nName;
    public String fileName;
    public String dirName;
  }
}