package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowBoxSeparate;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.gwindow.GWindowTabDialogClient.Tab;
import com.maddox.gwindow.GWindowTable;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.Army;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.ZutiAircraft;
import com.maddox.il2.objects.air.Scheme1;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeSailPlane;
import com.maddox.il2.objects.air.TypeScout;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.rts.LDRres;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class PlMisBorn extends Plugin
{
  private static final int rMIN = 500;
  private static final int rMAX = 10000;
  private static final int rDEF = 1000;
  private static final int rSTEP = 50;
  private final float checkBoxPosition = 25.0F;
  private final float spawnAndFowTextFieldPosition = 20.0F;
  GWindowCheckBox wZutiEnableFriction;
  GWindowEditControl wFriction;
  GWindowCheckBox wZutiStaticPositionOnly;
  GWindowCheckBox wEnablePlaneLimits;
  GWindowCheckBox wDecreasingNumberOfPlanes;
  GWindowCheckBox wIncludeStaticPlanes;
  GWindowLabel lProperties;
  GWindowLabel lLimitations;
  GWindowBoxSeparate bSeparate_Limitations;
  GWindowLabel lRadius;
  GWindowLabel lAirSpawn;
  GWindowLabel lRadar;
  GWindowButton bModifyPlane;
  GWindowButton bModifyCountries;
  GWindowTabDialogClient.Tab tabSpawn;
  GWindowTabDialogClient.Tab tabFow;
  GWindowEditControl wHeight;
  GWindowEditControl wSpeed;
  GWindowEditControl wOrient;
  GWindowEditControl wMaxPilots;
  GWindowEditControl wZutiRadar_Min;
  GWindowEditControl wZutiRadar_Max;
  GWindowEditControl wZutiRadar_Range;
  GWindowCheckBox wZutiDisableSpawning;
  GWindowCheckBox wAlwaysAirSpawn;
  GWindowCheckBox wAirSpawnIfCarrierFull;
  GWindowBoxSeparate bSeparate_Properties;
  GWindowBoxSeparate bSeparate_AirSpawn;
  GWindowBoxSeparate bSeparate_Radar;
  GWindowBoxSeparate bSeparate_Capture_Local;
  private Zuti_WManageAircrafts zuti_manageAircrafts;
  private Zuti_WHomeBaseCountries zuti_homeBaseCountries;
  private static int ZUTI_ALL_AC_LIST_SIZE = -1;
  protected ArrayList allActors;
  Item[] item;
  private Point2d p2d;
  private Point2d p2dt;
  private Point3d p3d;
  private static final int NCIRCLESEGMENTS = 48;
  private static float[] _circleXYZ = new float[''];
  private PlMission pluginMission;
  private int startComboBox1;
  private GWindowMenuItem viewType;
  private String[] _actorInfo;
  GWindowTabDialogClient.Tab tabTarget;
  GWindowComboControl wArmy;
  GWindowHSliderInt wR;
  GWindowCheckBox wParachute;
  GWindowTabDialogClient.Tab tabAircraft;
  Table lstAvailable;
  Table lstInReserve;
  GWindowButton bAddAll;
  GWindowButton bAdd;
  GWindowButton bRemAll;
  GWindowButton bRem;
  GWindowLabel lSeparate;
  GWindowBoxSeparate bSeparate;
  GWindowLabel lCountry;
  GWindowComboControl cCountry;
  static ArrayList lstCountry = new ArrayList();
  GWindowButton bCountryAdd;
  GWindowButton bCountryRem;
  GWindowLabel lYear;
  GWindowComboControl cYear;
  static ArrayList lstYear = new ArrayList();
  GWindowButton bYearAdd;
  GWindowButton bYearRem;
  GWindowLabel lType;
  GWindowComboControl cType;
  static ArrayList lstType = new ArrayList();
  GWindowButton bTypeAdd;
  GWindowButton bTypeRem;

  public PlMisBorn()
  {
    this.checkBoxPosition = 25.0F;
    this.spawnAndFowTextFieldPosition = 20.0F;

    this.allActors = new ArrayList();

    this.item = new Item[] { new Item(i18n("BornPlace")) };

    this.p2d = new Point2d();
    this.p2dt = new Point2d();
    this.p3d = new Point3d();

    this._actorInfo = new String[1];
  }

  public void renderMap2DAfter()
  {
    if (builder.isFreeView()) return;
    if (!this.viewType.bChecked) return;
    Actor localActor = builder.selectedActor();
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorBorn localActorBorn = (ActorBorn)this.allActors.get(j);
      if (builder.project2d(localActorBorn.pos.getAbsPoint(), this.p2d)) {
        int k = Army.color(localActorBorn.getArmy());
        if (localActorBorn == localActor)
          k = Builder.colorSelected();
        IconDraw.setColor(k);
        IconDraw.render(localActorBorn, this.p2d.x, this.p2d.y);
        localActorBorn.pos.getAbs(this.p3d);
        this.p3d.x += localActorBorn.r;
        if (builder.project2d(this.p3d, this.p2dt)) {
          double d = this.p2dt.x - this.p2d.x;
          if (d > builder.conf.iconSize / 3)
            drawCircle(this.p2d.x, this.p2d.y, d, k);
        }
      }
    }
  }

  private void drawCircle(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt)
  {
    int i = 48;
    double d1 = 6.283185307179586D / i;
    double d2 = 0.0D;
    for (int j = 0; j < i; j++) {
      _circleXYZ[(j * 3 + 0)] = (float)(paramDouble1 + paramDouble3 * Math.cos(d2));
      _circleXYZ[(j * 3 + 1)] = (float)(paramDouble2 + paramDouble3 * Math.sin(d2));
      _circleXYZ[(j * 3 + 2)] = 0.0F;
      d2 += d1;
    }
    Render.drawBeginLines(-1);
    Render.drawLines(_circleXYZ, i, 1.0F, paramInt, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE, 4);

    Render.drawEnd();
  }

  public boolean save(SectFile paramSectFile)
  {
    int i = paramSectFile.sectionIndex("MDS");

    if (i < 0) {
      i = paramSectFile.sectionAdd("MDS");
    }
    int j = this.allActors.size();
    if (j == 0) return true;
    int k = countAllAircraft();
    int m = paramSectFile.sectionAdd("BornPlace");
    for (int n = 0; n < j; n++) {
      ActorBorn localActorBorn = (ActorBorn)this.allActors.get(n);
      paramSectFile.lineAdd(m, "" + localActorBorn.getArmy() + " " + localActorBorn.r + " " + (int)localActorBorn.pos.getAbsPoint().x + " " + (int)localActorBorn.pos.getAbsPoint().y + " " + (localActorBorn.bParachute ? "1" : "0") + " " + localActorBorn.zutiSpawnHeight + " " + localActorBorn.zutiSpawnSpeed + " " + localActorBorn.zutiSpawnOrient + " " + localActorBorn.zutiMaxBasePilots + " " + localActorBorn.zutiRadarHeight_MIN + " " + localActorBorn.zutiRadarHeight_MAX + " " + localActorBorn.zutiRadarRange + " " + BoolToInt(localActorBorn.zutiAirspawnOnly) + " " + BoolToInt(localActorBorn.zutiEnablePlaneLimits) + " " + BoolToInt(localActorBorn.zutiDecreasingNumberOfPlanes) + " " + BoolToInt(localActorBorn.zutiDisableSpawning) + " " + BoolToInt(localActorBorn.zutiEnableFriction) + " " + localActorBorn.zutiFriction + " " + BoolToInt(localActorBorn.zutiIncludeStaticPlanes) + " " + BoolToInt(localActorBorn.zutiStaticPositionOnly) + " " + BoolToInt(localActorBorn.zutiAirspawnIfCarrierFull));

      ArrayList localArrayList = null;

      if ((localActorBorn.airNames != null) && (localActorBorn.zutiAcLoadouts != null) && (localActorBorn.zutiAcLoadouts.modifiedAircrafts != null))
        localArrayList = zutiSyncLists(localActorBorn.airNames, localActorBorn.zutiAcLoadouts.modifiedAircrafts);
      else if ((localActorBorn.airNames != null) && ((localActorBorn.zutiAcLoadouts == null) || (localActorBorn.zutiAcLoadouts.modifiedAircrafts != null)))
      {
        if (localActorBorn.airNames.size() != ZUTI_ALL_AC_LIST_SIZE)
        {
          localActorBorn.zutiAcLoadouts = zutiCreateAndLoadLoadoutsObject(localActorBorn);
          localArrayList = zutiSyncLists(localActorBorn.airNames, localActorBorn.zutiAcLoadouts.modifiedAircrafts);
        }

      }

      if (localArrayList != null)
      {
        int i1 = paramSectFile.sectionAdd("BornPlace" + n);

        for (int i2 = 0; i2 < localArrayList.size(); i2++)
        {
          ZutiAircraft localZutiAircraft = (ZutiAircraft)localArrayList.get(i2);

          if (localActorBorn.zutiEnablePlaneLimits)
            paramSectFile.lineAdd(i1, localZutiAircraft.getMissionLine(localActorBorn.zutiDecreasingNumberOfPlanes));
          else {
            paramSectFile.lineAdd(i1, localZutiAircraft.getMissionLineNoLoadouts());
          }
        }
      }

      zutiSaveBornPlaceCountries(localActorBorn, paramSectFile, n);
    }

    return true;
  }

  public void load(SectFile paramSectFile)
  {
    int i = paramSectFile.sectionIndex("BornPlace");
    if (i >= 0) {
      int j = paramSectFile.vars(i);
      Point3d localPoint3d = new Point3d();
      for (int k = 0; k < j; k++) {
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
        int m = localNumberTokenizer.next(0, 0, Army.amountNet() - 1);
        int n = localNumberTokenizer.next(1000, 500, 10000);
        localPoint3d.x = localNumberTokenizer.next(0);
        localPoint3d.y = localNumberTokenizer.next(0);
        boolean bool = localNumberTokenizer.next(1) == 1;
        ActorBorn localActorBorn = insert(localPoint3d, false);
        try
        {
          localActorBorn.zutiSpawnHeight = localNumberTokenizer.next(1000, 0, 10000);
          localActorBorn.zutiSpawnSpeed = localNumberTokenizer.next(200, 0, 500);
          localActorBorn.zutiSpawnOrient = localNumberTokenizer.next(0, 0, 360);
          localActorBorn.zutiMaxBasePilots = localNumberTokenizer.next(0, 0, 99999);

          localActorBorn.zutiRadarHeight_MIN = localNumberTokenizer.next(0, 0, 99999);
          localActorBorn.zutiRadarHeight_MAX = localNumberTokenizer.next(5000, 0, 99999);
          localActorBorn.zutiRadarRange = localNumberTokenizer.next(50, 1, 99999);
          if (localNumberTokenizer.next(0, 0, 1) == 1)
            localActorBorn.zutiAirspawnOnly = true;
          if (localNumberTokenizer.next(0, 0, 1) == 1)
            localActorBorn.zutiEnablePlaneLimits = true;
          if (localNumberTokenizer.next(0, 0, 1) == 1)
            localActorBorn.zutiDecreasingNumberOfPlanes = true;
          if (localNumberTokenizer.next(0, 0, 1) == 1)
            localActorBorn.zutiDisableSpawning = true;
          if (localNumberTokenizer.next(0, 0, 1) == 1)
            localActorBorn.zutiEnableFriction = true;
          localActorBorn.zutiFriction = localNumberTokenizer.next(3.8D, 0.0D, 10.0D);
          if (localNumberTokenizer.next(0, 0, 1) == 1)
            localActorBorn.zutiIncludeStaticPlanes = true;
          if (localNumberTokenizer.next(0, 0, 1) == 1)
            localActorBorn.zutiStaticPositionOnly = true;
          if (localNumberTokenizer.next(0, 0, 1) == 1)
            localActorBorn.zutiAirspawnIfCarrierFull = true;
        }
        catch (Exception localException)
        {
          System.out.println("PlMisBorn: no air spawn entries defined for HomeBase at " + localPoint3d);
        }

        if (localActorBorn == null)
          continue;
        localActorBorn.airNames.clear();
        if (localActorBorn != null) {
          localActorBorn.setArmy(m);
          localActorBorn.r = n;
          localActorBorn.bParachute = bool;

          int i1 = paramSectFile.sectionIndex("BornPlace" + k);
          if (i1 >= 0) {
            int i2 = paramSectFile.vars(i1);
            for (int i3 = 0; i3 < i2; i3++)
            {
              String str1 = paramSectFile.line(i1, i3);
              StringTokenizer localStringTokenizer = new StringTokenizer(str1);

              ZutiAircraft localZutiAircraft = new ZutiAircraft();
              String str2 = "";
              int i4 = 0;
              while (localStringTokenizer.hasMoreTokens())
              {
                switch (i4)
                {
                case 0:
                  localZutiAircraft.setAcName(localStringTokenizer.nextToken());
                  break;
                case 1:
                  localZutiAircraft.setMaxAllowed(Integer.valueOf(localStringTokenizer.nextToken()).intValue());
                  break;
                default:
                  str2 = str2 + " " + localStringTokenizer.nextToken();
                }

                i4++;
              }
              localZutiAircraft.setLoadedWeapons(str2, true);
              String str3 = localZutiAircraft.getAcName();
              if (str3 == null)
                continue;
              str3 = str3.intern();
              Class localClass = (Class)Property.value(str3, "airClass", null);
              if ((localClass == null) || (!Property.containsValue(localClass, "cockpitClass")))
                continue;
              if (localZutiAircraft.wasModified())
              {
                if (localActorBorn.zutiAcLoadouts == null)
                {
                  localActorBorn.zutiAcLoadouts = new Zuti_WAircraftLoadout();
                  localActorBorn.zutiAcLoadouts.modifiedAircrafts = new ArrayList();
                }
                else if (localActorBorn.zutiAcLoadouts.modifiedAircrafts == null) {
                  localActorBorn.zutiAcLoadouts.modifiedAircrafts = new ArrayList();
                }
                localActorBorn.zutiAcLoadouts.modifiedAircrafts.add(localZutiAircraft);
              }

              localActorBorn.airNames.add(str3);
            }

          }

          if (localActorBorn.airNames.size() == 0) {
            addAllAircraft(localActorBorn.airNames);
          }

          zutiLoadBornPlaceCountries(localActorBorn, paramSectFile);
        }
      }
    }
  }

  public void deleteAll()
  {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorBorn localActorBorn = (ActorBorn)this.allActors.get(j);
      localActorBorn.destroy();
    }
    this.allActors.clear();
  }

  public void delete(Actor paramActor) {
    this.allActors.remove(paramActor);
    paramActor.destroy();
  }

  private boolean findAirport(Point3d paramPoint3d) {
    paramPoint3d.z = 0.0D;
    Airport localAirport = Airport.nearest(paramPoint3d, -1, 7);
    if (localAirport == null)
    {
      return false;
    }
    paramPoint3d.set(localAirport.pos.getAbsPoint());
    for (int i = 0; i < this.allActors.size(); i++) {
      ActorBorn localActorBorn = (ActorBorn)this.allActors.get(i);
      Point3d localPoint3d = localActorBorn.pos.getAbsPoint();
      double d = (paramPoint3d.x - localPoint3d.x) * (paramPoint3d.x - localPoint3d.x) + (paramPoint3d.y - localPoint3d.y) * (paramPoint3d.y - localPoint3d.y);
      if (d < 100.0D)
        return false;
    }
    return true;
  }

  private ActorBorn insert(Point3d paramPoint3d, boolean paramBoolean) {
    if (this.allActors.size() >= 255) {
      return null;
    }

    if (!zutiFindAirport(paramPoint3d))
      return null;
    try {
      ActorBorn localActorBorn = new ActorBorn(paramPoint3d);
      addAllAircraft(localActorBorn.airNames);

      builder.align(localActorBorn);
      Property.set(localActorBorn, "builderSpawn", "");
      Property.set(localActorBorn, "builderPlugin", this);
      this.allActors.add(localActorBorn);
      if (paramBoolean)
        builder.setSelected(localActorBorn);
      PlMission.setChanged();
      return localActorBorn; } catch (Exception localException) {
    }
    return null;
  }

  public void insert(Loc paramLoc, boolean paramBoolean) {
    int i = builder.wSelect.comboBox1.getSelected();
    int j = builder.wSelect.comboBox2.getSelected();
    if (i != this.startComboBox1)
      return;
    if ((j < 0) || (j >= this.item.length))
      return;
    insert(paramLoc.getPoint(), paramBoolean);
  }

  public void changeType() {
    builder.setSelected(null);
  }

  private void updateView() {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorBorn localActorBorn = (ActorBorn)this.allActors.get(j);
      localActorBorn.drawing(this.viewType.bChecked);
    }
  }

  public void configure()
  {
    if (getPlugin("Mission") == null)
      throw new RuntimeException("PlMisBorn: plugin 'Mission' not found");
    this.pluginMission = ((PlMission)getPlugin("Mission"));
  }

  private void fillComboBox2(int paramInt) {
    if (paramInt != this.startComboBox1)
      return;
    if (builder.wSelect.curFilledType != paramInt) {
      builder.wSelect.curFilledType = paramInt;
      builder.wSelect.comboBox2.clear(false);
      for (int i = 0; i < this.item.length; i++)
        builder.wSelect.comboBox2.add(this.item[i].name);
      builder.wSelect.comboBox1.setSelected(paramInt, true, false);
    }
    builder.wSelect.comboBox2.setSelected(0, true, false);
    builder.wSelect.setMesh(null, true);
  }

  public void viewTypeAll(boolean paramBoolean) {
    this.viewType.bChecked = paramBoolean;
    updateView();
  }

  public String[] actorInfo(Actor paramActor) {
    ActorBorn localActorBorn = (ActorBorn)paramActor;
    this._actorInfo[0] = i18n("BornPlace");
    return this._actorInfo;
  }

  public void syncSelector()
  {
    ActorBorn localActorBorn = (ActorBorn)builder.selectedActor();
    fillComboBox2(this.startComboBox1);
    builder.wSelect.comboBox2.setSelected(0, true, false);
    builder.wSelect.tabsClient.addTab(1, this.tabTarget);
    builder.wSelect.tabsClient.addTab(2, this.tabAircraft);

    Plugin.builder.wSelect.tabsClient.addTab(3, this.tabSpawn);
    Plugin.builder.wSelect.tabsClient.addTab(4, this.tabFow);

    fillTabAircraft();

    zutiResetValues();

    this.wR.setPos((localActorBorn.r - 500) / 50, false);
    this.wArmy.setSelected(localActorBorn.getArmy(), true, false);
  }

  public void updateSelector() {
    fillTabAircraft();
  }

  public void createGUI()
  {
    Plugin.builder.wSelect.metricWin = new GRegion(2.0F, 2.0F, 40.0F, 36.0F);

    this.startComboBox1 = builder.wSelect.comboBox1.size();
    builder.wSelect.comboBox1.add(i18n("BornPlace"));
    builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMisBorn.this.fillComboBox2(i);
        return false;
      }
    });
    int i = builder.mDisplayFilter.subMenu.size() - 1;
    while ((i >= 0) && 
      (this.pluginMission.viewBridge != builder.mDisplayFilter.subMenu.getItem(i)))
    {
      i--;
    }
    i--;
    if (i >= 0) {
      this.viewType = builder.mDisplayFilter.subMenu.addItem(i, new GWindowMenuItem(builder.mDisplayFilter.subMenu, i18n("showBornPlaces"), null)
      {
        public void execute() {
          this.bChecked = (!this.bChecked);
          PlMisBorn.this.updateView();
        }
      });
      this.viewType.bChecked = true;
    }

    GWindowDialogClient localGWindowDialogClient1 = (GWindowDialogClient)builder.wSelect.tabsClient.create(new GWindowDialogClient());
    this.tabTarget = builder.wSelect.tabsClient.createTab(i18n("BornPlaceActor"), localGWindowDialogClient1);

    localGWindowDialogClient1.addLabel(this.lProperties = new GWindowLabel(localGWindowDialogClient1, 3.0F, 0.5F, 6.0F, 1.6F, Plugin.i18n("mds.properties"), null));
    this.bSeparate_Properties = new GWindowBoxSeparate(localGWindowDialogClient1, 1.0F, 1.0F, 37.0F, 18.0F);
    this.bSeparate_Properties.exclude = this.lProperties;
    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 2.0F, 2.0F, 6.0F, 1.3F, Plugin.i18n("mds.properties.radius"), null));
    localGWindowDialogClient1.addLabel(this.lRadius = new GWindowLabel(localGWindowDialogClient1, 17.0F, 2.0F, 6.0F, 1.3F, " [500m]", null));

    localGWindowDialogClient1.addControl(this.wR = new GWindowHSliderInt(localGWindowDialogClient1, 0, 190, 20, 7.0F, 2.0F, 10.0F) {
      public void afterCreated() { super.afterCreated();
        this.bSlidingNotify = true;
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        if (localActorBorn != null) {
          PlMisBorn.this.lRadius.cap = new GCaption(" [" + localActorBorn.r + "m]");
        }
        PlMisBorn.this.zutiCountriesWindowRefresh(localActorBorn);

        if (paramInt1 != 2) return false;

        localActorBorn.r = (pos() * 50 + 500);

        PlMisBorn.this.lRadius.cap = new GCaption(" [" + localActorBorn.r + "m]");

        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 2.0F, 4.0F, 6.0F, 1.3F, Plugin.i18n("mds.properties.army"), null));

    localGWindowDialogClient1.addControl(this.wArmy = new GWindowComboControl(localGWindowDialogClient1, 8.0F, 4.0F, 10.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        for (int i = 0; i < Army.amountNet(); i++)
          add(I18N.army(Army.name(i))); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.setArmy(getSelected());
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 2.0F, 6.0F, 8.0F, 1.3F, i18n("Parachute"), null));
    localGWindowDialogClient1.addControl(this.wParachute = new GWindowCheckBox(localGWindowDialogClient1, 25.0F, 6.0F, null) {
      public void preRender() {
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setChecked(localActorBorn.bParachute, false);
      }
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.bParachute = isChecked();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 2.0F, 8.0F, 18.0F, 1.3F, Plugin.i18n("mds.properties.friction1"), null));
    localGWindowDialogClient1.addControl(this.wZutiEnableFriction = new GWindowCheckBox(localGWindowDialogClient1, 25.0F, 8.0F, null)
    {
      public void preRender()
      {
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setChecked(localActorBorn.zutiEnableFriction, false);
        PlMisBorn.this.wFriction.setEnable(localActorBorn.zutiEnableFriction);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiEnableFriction = isChecked();

        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 28.0F, 8.0F, 6.0F, 1.3F, Plugin.i18n("mds.properties.friction2"), null));
    localGWindowDialogClient1.addControl(this.wFriction = new GWindowEditControl(localGWindowDialogClient1, 34.0F, 8.0F, 2.5F, 1.3F, "Tralala")
    {
      public void afterCreated()
      {
        super.afterCreated();
        this.bNumericOnly = true;
        this.bNumericFloat = true;
        this.bDelayedNotify = true;
      }

      public void preRender()
      {
        if (getValue().trim().length() > 0) {
          return;
        }
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setValue(new Double(localActorBorn.zutiFriction).toString(), false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiFriction = Double.parseDouble(getValue());
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 2.0F, 10.0F, 18.0F, 1.3F, Plugin.i18n("mds.properties.spawn"), null));
    localGWindowDialogClient1.addControl(this.wZutiDisableSpawning = new GWindowCheckBox(localGWindowDialogClient1, 25.0F, 10.0F, null)
    {
      public void preRender()
      {
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setChecked(localActorBorn.zutiDisableSpawning, false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiDisableSpawning = isChecked();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 2.0F, 12.0F, 18.0F, 1.3F, Plugin.i18n("mds.properties.staticPosition"), null));
    localGWindowDialogClient1.addControl(this.wZutiStaticPositionOnly = new GWindowCheckBox(localGWindowDialogClient1, 25.0F, 12.0F, null)
    {
      public void preRender()
      {
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setChecked(localActorBorn.zutiStaticPositionOnly, false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiStaticPositionOnly = isChecked();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 2.0F, 14.0F, 18.0F, 1.3F, Plugin.i18n("mds.spawn.maxPilots"), null));
    localGWindowDialogClient1.addControl(this.wMaxPilots = new GWindowEditControl(localGWindowDialogClient1, 25.0F, 14.0F, 3.0F, 1.3F, "Tralala")
    {
      public void afterCreated()
      {
        super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true;
      }

      public void preRender()
      {
        if (getValue().trim().length() > 0) {
          return;
        }
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setValue(new Integer(localActorBorn.zutiMaxBasePilots).toString(), false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiMaxBasePilots = Integer.parseInt(getValue());
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient1.addControl(this.bModifyCountries = new GWindowButton(localGWindowDialogClient1, 2.0F, 16.0F, 10.0F, 2.0F, Plugin.i18n("mds.properties.countries"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        PlMisBorn.this.zutiCountriesWindowShow(localActorBorn, 0);

        return true;
      }
    });
    localGWindowDialogClient1.addLabel(this.lLimitations = new GWindowLabel(localGWindowDialogClient1, 3.0F, 19.5F, 9.0F, 1.6F, Plugin.i18n("mds.limitations"), null));
    this.bSeparate_Limitations = new GWindowBoxSeparate(localGWindowDialogClient1, 1.0F, 20.0F, 37.0F, 7.0F);
    this.bSeparate_Limitations.exclude = this.lLimitations;

    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 2.0F, 21.0F, 22.0F, 1.3F, Plugin.i18n("mds.limitations.enable"), null));
    localGWindowDialogClient1.addControl(this.wEnablePlaneLimits = new GWindowCheckBox(localGWindowDialogClient1, 25.0F, 21.0F, null)
    {
      public void preRender()
      {
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setChecked(localActorBorn.zutiEnablePlaneLimits, false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        PlMisBorn.this.wDecreasingNumberOfPlanes.setEnable(isChecked());
        PlMisBorn.this.wIncludeStaticPlanes.setEnable(isChecked());

        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiEnablePlaneLimits = isChecked();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 2.0F, 23.0F, 23.0F, 1.3F, Plugin.i18n("mds.limitations.decAc"), null));
    localGWindowDialogClient1.addControl(this.wDecreasingNumberOfPlanes = new GWindowCheckBox(localGWindowDialogClient1, 25.0F, 23.0F, null)
    {
      public void preRender()
      {
        super.preRender();
        setEnable(PlMisBorn.this.wEnablePlaneLimits.isChecked());
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setChecked(localActorBorn.zutiDecreasingNumberOfPlanes, false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        PlMisBorn.this.wIncludeStaticPlanes.setEnable((isChecked()) && (PlMisBorn.this.wEnablePlaneLimits.isChecked()));

        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiDecreasingNumberOfPlanes = isChecked();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 3.0F, 25.0F, 22.0F, 1.3F, Plugin.i18n("mds.limitations.countStatic"), null));
    localGWindowDialogClient1.addControl(this.wIncludeStaticPlanes = new GWindowCheckBox(localGWindowDialogClient1, 25.0F, 25.0F, null)
    {
      public void preRender()
      {
        super.preRender();
        setEnable((PlMisBorn.this.wDecreasingNumberOfPlanes.isChecked()) && (PlMisBorn.this.wEnablePlaneLimits.isChecked()));
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setChecked(localActorBorn.zutiIncludeStaticPlanes, false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiIncludeStaticPlanes = isChecked();
        PlMission.setChanged();
        return false;
      }
    });
    GWindowDialogClient localGWindowDialogClient2 = (GWindowDialogClient)builder.wSelect.tabsClient.create(new GWindowDialogClient() {
      public void resized() {
        super.resized();
        PlMisBorn.this.setAircraftSizes(this);
      }
    });
    this.tabAircraft = builder.wSelect.tabsClient.createTab(i18n("bplace_aircraft"), localGWindowDialogClient2);
    this.lstAvailable = new Table(localGWindowDialogClient2, i18n("bplace_planes"), 1.0F, 1.0F, 6.0F, 10.0F);

    this.lstAvailable.zutiShowAcWeaponsWindow = true;

    this.lstInReserve = new Table(localGWindowDialogClient2, i18n("bplace_list"), 14.0F, 1.0F, 6.0F, 10.0F);
    localGWindowDialogClient2.addControl(this.bAddAll = new GWindowButton(localGWindowDialogClient2, 8.0F, 1.0F, 5.0F, 2.0F, i18n("bplace_addall"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisBorn.this.lstAvailable.lst.clear();
        PlMisBorn.this.addAllAircraft(PlMisBorn.this.lstAvailable.lst);
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addControl(this.bAdd = new GWindowButton(localGWindowDialogClient2, 8.0F, 3.0F, 5.0F, 2.0F, i18n("bplace_add"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        int i = PlMisBorn.this.lstInReserve.selectRow;
        if ((i < 0) || (i >= PlMisBorn.this.lstInReserve.lst.size())) return true;
        PlMisBorn.this.lstAvailable.lst.add(PlMisBorn.this.lstInReserve.lst.get(i));
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addControl(this.bRemAll = new GWindowButton(localGWindowDialogClient2, 8.0F, 6.0F, 5.0F, 2.0F, i18n("bplace_delall"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisBorn.this.lstAvailable.lst.clear();
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addControl(this.bRem = new GWindowButton(localGWindowDialogClient2, 8.0F, 8.0F, 5.0F, 2.0F, i18n("bplace_del"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        int i = PlMisBorn.this.lstAvailable.selectRow;
        if ((i < 0) || (i >= PlMisBorn.this.lstAvailable.lst.size())) return true;
        PlMisBorn.this.lstAvailable.lst.remove(i);
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addControl(this.bModifyPlane = new GWindowButton(localGWindowDialogClient2, 8.0F, 12.0F, 5.0F, 2.0F, i18n("mds.aircraft.modify"), null)
    {
      public boolean notify(int paramInt1, int paramInt2)
      {
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        PlMisBorn.this.bModifyPlane.setEnable(localActorBorn.zutiEnablePlaneLimits);
        if (paramInt1 != 2) {
          return false;
        }
        if (localActorBorn.zutiAcLoadouts == null) {
          localActorBorn.zutiAcLoadouts = new Zuti_WAircraftLoadout();
        }
        if (!localActorBorn.zutiAcLoadouts.isVisible())
        {
          if ((PlMisBorn.this.lstAvailable.selectRow < 0) || (PlMisBorn.this.lstAvailable.selectRow >= PlMisBorn.this.lstAvailable.lst.size())) {
            return true;
          }

          localActorBorn.zutiAcLoadouts.setSelectedAircraft((String)PlMisBorn.this.lstAvailable.lst.get(PlMisBorn.this.lstAvailable.selectRow));
          localActorBorn.zutiAcLoadouts.setTitle((String)PlMisBorn.this.lstAvailable.lst.get(PlMisBorn.this.lstAvailable.selectRow));
          localActorBorn.zutiAcLoadouts.showWindow();
          return true;
        }

        return true;
      }
    });
    localGWindowDialogClient2.addLabel(this.lSeparate = new GWindowLabel(localGWindowDialogClient2, 3.0F, 12.0F, 12.0F, 1.6F, " " + i18n("bplace_cats") + " ", null));
    this.bSeparate = new GWindowBoxSeparate(localGWindowDialogClient2, 1.0F, 12.5F, 27.0F, 8.0F);
    this.bSeparate.exclude = this.lSeparate;

    localGWindowDialogClient2.addLabel(this.lCountry = new GWindowLabel(localGWindowDialogClient2, 2.0F, 14.0F, 7.0F, 1.6F, i18n("bplace_country"), null));
    localGWindowDialogClient2.addControl(this.cCountry = new GWindowComboControl(localGWindowDialogClient2, 9.0F, 14.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        ResourceBundle localResourceBundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());
        TreeMap localTreeMap = new TreeMap();
        ArrayList localArrayList = Main.cur().airClasses;
        Object localObject;
        String str1;
        for (int i = 0; i < localArrayList.size(); i++) {
          localObject = (Class)localArrayList.get(i);
          if (Property.containsValue((Class)localObject, "cockpitClass")) {
            str1 = Property.stringValue((Class)localObject, "originCountry", null);
            if (str1 != null) {
              String str2 = null;
              try {
                str2 = localResourceBundle.getString(str1);
              } catch (Exception localException) {
                continue;
              }
              localTreeMap.put(str2, str1);
            }
          }
        }
        Iterator localIterator = localTreeMap.keySet().iterator();
        while (localIterator.hasNext()) {
          localObject = (String)localIterator.next();
          str1 = (String)localTreeMap.get(localObject);
          PlMisBorn.lstCountry.add(str1);
          add((String)localObject);
        }
        if (PlMisBorn.lstCountry.size() > 0)
          setSelected(0, true, false);
      }
    });
    localGWindowDialogClient2.addControl(this.bCountryAdd = new GWindowButton(localGWindowDialogClient2, 17.0F, 14.0F, 5.0F, 1.6F, i18n("bplace_add"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        String str1 = (String)PlMisBorn.lstCountry.get(PlMisBorn.this.cCountry.getSelected());
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++) {
          Class localClass = (Class)localArrayList.get(i);
          if ((!Property.containsValue(localClass, "cockpitClass")) || 
            (!str1.equals(Property.stringValue(localClass, "originCountry", null)))) continue;
          String str2 = Property.stringValue(localClass, "keyName");
          if (!PlMisBorn.this.lstAvailable.lst.contains(str2)) {
            PlMisBorn.this.lstAvailable.lst.add(str2);
          }
        }
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addControl(this.bCountryRem = new GWindowButton(localGWindowDialogClient2, 22.0F, 14.0F, 5.0F, 1.6F, i18n("bplace_del"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        String str1 = (String)PlMisBorn.lstCountry.get(PlMisBorn.this.cCountry.getSelected());
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++) {
          Class localClass = (Class)localArrayList.get(i);
          if ((!Property.containsValue(localClass, "cockpitClass")) || 
            (!str1.equals(Property.stringValue(localClass, "originCountry", null)))) continue;
          String str2 = Property.stringValue(localClass, "keyName");
          int j = PlMisBorn.this.lstAvailable.lst.indexOf(str2);
          if (j >= 0) {
            PlMisBorn.this.lstAvailable.lst.remove(j);
          }
        }
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addLabel(this.lYear = new GWindowLabel(localGWindowDialogClient2, 2.0F, 16.0F, 7.0F, 1.6F, i18n("bplace_year"), null));
    localGWindowDialogClient2.addControl(this.cYear = new GWindowComboControl(localGWindowDialogClient2, 9.0F, 16.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        TreeMap localTreeMap = new TreeMap();
        ArrayList localArrayList = Main.cur().airClasses;
        Object localObject;
        for (int i = 0; i < localArrayList.size(); i++) {
          localObject = (Class)localArrayList.get(i);
          if (Property.containsValue((Class)localObject, "cockpitClass")) {
            float f = Property.floatValue((Class)localObject, "yearService", 0.0F);
            if (f != 0.0F)
              localTreeMap.put("" + (int)f, null);
          }
        }
        Iterator localIterator = localTreeMap.keySet().iterator();
        while (localIterator.hasNext()) {
          localObject = (String)localIterator.next();
          PlMisBorn.lstYear.add(localObject);
          add((String)localObject);
        }
        if (PlMisBorn.lstYear.size() > 0)
          setSelected(0, true, false);
      }
    });
    localGWindowDialogClient2.addControl(this.bYearAdd = new GWindowButton(localGWindowDialogClient2, 17.0F, 16.0F, 5.0F, 1.6F, i18n("bplace_add"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        String str1 = (String)PlMisBorn.lstYear.get(PlMisBorn.this.cYear.getSelected());
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++) {
          Class localClass = (Class)localArrayList.get(i);
          if ((!Property.containsValue(localClass, "cockpitClass")) || 
            (!str1.equals("" + (int)Property.floatValue(localClass, "yearService", 0.0F)))) continue;
          String str2 = Property.stringValue(localClass, "keyName");
          if (!PlMisBorn.this.lstAvailable.lst.contains(str2)) {
            PlMisBorn.this.lstAvailable.lst.add(str2);
          }
        }
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addControl(this.bYearRem = new GWindowButton(localGWindowDialogClient2, 22.0F, 16.0F, 5.0F, 1.6F, i18n("bplace_del"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        String str1 = (String)PlMisBorn.lstYear.get(PlMisBorn.this.cYear.getSelected());
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++) {
          Class localClass = (Class)localArrayList.get(i);
          if ((!Property.containsValue(localClass, "cockpitClass")) || 
            (!str1.equals("" + (int)Property.floatValue(localClass, "yearService", 0.0F)))) continue;
          String str2 = Property.stringValue(localClass, "keyName");
          int j = PlMisBorn.this.lstAvailable.lst.indexOf(str2);
          if (j >= 0) {
            PlMisBorn.this.lstAvailable.lst.remove(j);
          }
        }
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addLabel(this.lType = new GWindowLabel(localGWindowDialogClient2, 2.0F, 18.0F, 7.0F, 1.6F, i18n("bplace_category"), null));
    localGWindowDialogClient2.addControl(this.cType = new GWindowComboControl(localGWindowDialogClient2, 9.0F, 18.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        TreeMap localTreeMap = new TreeMap();
        ArrayList localArrayList = Main.cur().airClasses;
        Object localObject;
        for (int i = 0; i < localArrayList.size(); i++) {
          localObject = (Class)localArrayList.get(i);
          if (Property.containsValue((Class)localObject, "cockpitClass")) {
            if (TypeStormovik.class.isAssignableFrom((Class)localObject))
              localTreeMap.put(Plugin.i18n("bplace_sturm"), TypeStormovik.class);
            if (TypeFighter.class.isAssignableFrom((Class)localObject)) {
              localTreeMap.put(Plugin.i18n("bplace_fiter"), TypeFighter.class);
            }

            if (TypeBomber.class.isAssignableFrom((Class)localObject))
              localTreeMap.put(Plugin.i18n("bplace_bomber"), TypeBomber.class);
            if (TypeScout.class.isAssignableFrom((Class)localObject))
              localTreeMap.put(Plugin.i18n("bplace_recon"), TypeScout.class);
            if (TypeDiveBomber.class.isAssignableFrom((Class)localObject))
              localTreeMap.put(Plugin.i18n("bplace_diver"), TypeDiveBomber.class);
            if (TypeSailPlane.class.isAssignableFrom((Class)localObject))
              localTreeMap.put(Plugin.i18n("bplace_sailer"), TypeSailPlane.class);
            if (Scheme1.class.isAssignableFrom((Class)localObject))
              localTreeMap.put(Plugin.i18n("bplace_single"), Scheme1.class);
            else
              localTreeMap.put(Plugin.i18n("bplace_multi"), null);
          }
        }
        Iterator localIterator = localTreeMap.keySet().iterator();
        while (localIterator.hasNext()) {
          localObject = (String)localIterator.next();
          Class localClass = (Class)localTreeMap.get(localObject);
          PlMisBorn.lstType.add(localClass);
          add((String)localObject);
        }
        if (PlMisBorn.lstType.size() > 0)
          setSelected(0, true, false);
      }
    });
    localGWindowDialogClient2.addControl(this.bTypeAdd = new GWindowButton(localGWindowDialogClient2, 17.0F, 18.0F, 5.0F, 1.6F, i18n("bplace_add"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        Class localClass1 = (Class)PlMisBorn.lstType.get(PlMisBorn.this.cType.getSelected());
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++) {
          Class localClass2 = (Class)localArrayList.get(i);
          if ((!Property.containsValue(localClass2, "cockpitClass")) || 
            (localClass1 == null ? 
            Scheme1.class.isAssignableFrom(localClass2) : 
            !localClass1.isAssignableFrom(localClass2)))
            continue;
          String str = Property.stringValue(localClass2, "keyName");
          if (!PlMisBorn.this.lstAvailable.lst.contains(str))
            PlMisBorn.this.lstAvailable.lst.add(str);
        }
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addControl(this.bTypeRem = new GWindowButton(localGWindowDialogClient2, 22.0F, 18.0F, 5.0F, 1.6F, i18n("bplace_del"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        Class localClass1 = (Class)PlMisBorn.lstType.get(PlMisBorn.this.cType.getSelected());
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++) {
          Class localClass2 = (Class)localArrayList.get(i);
          if ((!Property.containsValue(localClass2, "cockpitClass")) || 
            (localClass1 == null ? 
            Scheme1.class.isAssignableFrom(localClass2) : 
            !localClass1.isAssignableFrom(localClass2)))
            continue;
          String str = Property.stringValue(localClass2, "keyName");
          int j = PlMisBorn.this.lstAvailable.lst.indexOf(str);
          if (j >= 0)
            PlMisBorn.this.lstAvailable.lst.remove(j);
        }
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    GWindowDialogClient localGWindowDialogClient3 = (GWindowDialogClient)Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
    this.tabSpawn = Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("mds.tabSpawn"), localGWindowDialogClient3);

    localGWindowDialogClient3.addLabel(this.lAirSpawn = new GWindowLabel(localGWindowDialogClient3, 3.0F, 0.5F, 11.0F, 1.3F, Plugin.i18n("mds.spawn"), null));
    this.bSeparate_AirSpawn = new GWindowBoxSeparate(localGWindowDialogClient3, 1.0F, 1.0F, 37.0F, 11.0F);
    this.bSeparate_AirSpawn.exclude = this.lAirSpawn;

    localGWindowDialogClient3.addLabel(new GWindowLabel(localGWindowDialogClient3, 2.0F, 2.0F, 18.0F, 1.3F, Plugin.i18n("mds.spawn.height"), null));
    localGWindowDialogClient3.addControl(this.wHeight = new GWindowEditControl(localGWindowDialogClient3, 20.0F, 2.0F, 5.0F, 1.3F, "")
    {
      public void afterCreated()
      {
        super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true;
      }

      public void preRender()
      {
        if (getValue().trim().length() > 0) {
          return;
        }
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setValue(new Integer(localActorBorn.zutiSpawnHeight).toString(), false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiSpawnHeight = Integer.parseInt(getValue());
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient3.addLabel(new GWindowLabel(localGWindowDialogClient3, 2.0F, 4.0F, 18.0F, 1.3F, Plugin.i18n("mds.spawn.speed"), null));
    localGWindowDialogClient3.addControl(this.wSpeed = new GWindowEditControl(localGWindowDialogClient3, 20.0F, 4.0F, 5.0F, 1.3F, "Tralala")
    {
      public void afterCreated()
      {
        super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true;
      }

      public void preRender()
      {
        if (getValue().trim().length() > 0) {
          return;
        }
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setValue(new Integer(localActorBorn.zutiSpawnSpeed).toString(), false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiSpawnSpeed = Integer.parseInt(getValue());
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient3.addLabel(new GWindowLabel(localGWindowDialogClient3, 2.0F, 6.0F, 18.0F, 1.3F, Plugin.i18n("mds.spawn.orient"), null));
    localGWindowDialogClient3.addControl(this.wOrient = new GWindowEditControl(localGWindowDialogClient3, 20.0F, 6.0F, 5.0F, 1.3F, "Tralala")
    {
      public void afterCreated()
      {
        super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true;
      }

      public void preRender()
      {
        if (getValue().trim().length() > 0) {
          return;
        }
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setValue(new Integer(localActorBorn.zutiSpawnOrient).toString(), false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiSpawnOrient = Integer.parseInt(getValue());
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient3.addLabel(new GWindowLabel(localGWindowDialogClient3, 2.0F, 8.0F, 18.0F, 1.3F, Plugin.i18n("mds.spawn.spawnIfCarrierFull"), null));
    localGWindowDialogClient3.addControl(this.wAirSpawnIfCarrierFull = new GWindowCheckBox(localGWindowDialogClient3, 20.0F, 8.0F, null)
    {
      public void preRender()
      {
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setChecked(localActorBorn.zutiAirspawnIfCarrierFull, false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiAirspawnIfCarrierFull = isChecked();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient3.addLabel(new GWindowLabel(localGWindowDialogClient3, 2.0F, 10.0F, 18.0F, 1.3F, Plugin.i18n("mds.spawn.alwaysAirSpawn"), null));
    localGWindowDialogClient3.addControl(this.wAlwaysAirSpawn = new GWindowCheckBox(localGWindowDialogClient3, 20.0F, 10.0F, null)
    {
      public void preRender()
      {
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setChecked(localActorBorn.zutiAirspawnOnly, false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiAirspawnOnly = isChecked();
        PlMission.setChanged();
        return false;
      }
    });
    GWindowDialogClient localGWindowDialogClient4 = (GWindowDialogClient)Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
    this.tabFow = Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("mds.tabFow"), localGWindowDialogClient4);

    localGWindowDialogClient4.addLabel(this.lRadar = new GWindowLabel(localGWindowDialogClient4, 3.0F, 0.5F, 13.0F, 1.3F, Plugin.i18n("mds.radar"), null));
    this.bSeparate_Radar = new GWindowBoxSeparate(localGWindowDialogClient4, 1.0F, 1.0F, 37.0F, 10.0F);
    this.bSeparate_Radar.exclude = this.lRadar;

    localGWindowDialogClient4.addLabel(new GWindowLabel(localGWindowDialogClient4, 2.0F, 2.0F, 39.0F, 1.3F, Plugin.i18n("mds.radar.description1"), null));
    localGWindowDialogClient4.addLabel(new GWindowLabel(localGWindowDialogClient4, 2.0F, 3.0F, 39.0F, 1.3F, Plugin.i18n("mds.radar.description2"), null));

    localGWindowDialogClient4.addLabel(new GWindowLabel(localGWindowDialogClient4, 2.0F, 5.0F, 18.0F, 1.3F, Plugin.i18n("mds.radar.range"), null));
    localGWindowDialogClient4.addControl(this.wZutiRadar_Range = new GWindowEditControl(localGWindowDialogClient4, 20.0F, 5.0F, 5.0F, 1.3F, "Tralala")
    {
      public void afterCreated()
      {
        super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true;
      }

      public void preRender()
      {
        if (getValue().trim().length() > 0) {
          return;
        }
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setValue(new Integer(localActorBorn.zutiRadarRange).toString(), false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiRadarRange = Integer.parseInt(getValue());
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient4.addLabel(new GWindowLabel(localGWindowDialogClient4, 2.0F, 7.0F, 18.0F, 1.3F, Plugin.i18n("mds.radar.minHeight"), null));
    localGWindowDialogClient4.addControl(this.wZutiRadar_Min = new GWindowEditControl(localGWindowDialogClient4, 20.0F, 7.0F, 5.0F, 1.3F, "Tralala")
    {
      public void afterCreated()
      {
        super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true;
      }

      public void preRender()
      {
        if (getValue().trim().length() > 0) {
          return;
        }
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setValue(new Integer(localActorBorn.zutiRadarHeight_MIN).toString(), false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiRadarHeight_MIN = Integer.parseInt(getValue());
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient4.addLabel(new GWindowLabel(localGWindowDialogClient4, 2.0F, 9.0F, 18.0F, 1.3F, Plugin.i18n("mds.radar.maxHeight"), null));
    localGWindowDialogClient4.addControl(this.wZutiRadar_Max = new GWindowEditControl(localGWindowDialogClient4, 20.0F, 9.0F, 5.0F, 1.3F, "Tralala")
    {
      public void afterCreated()
      {
        super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true;
      }

      public void preRender()
      {
        if (getValue().trim().length() > 0) {
          return;
        }
        super.preRender();
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        setValue(new Integer(localActorBorn.zutiRadarHeight_MAX).toString(), false);
      }

      public boolean notify(int paramInt1, int paramInt2)
      {
        if (paramInt1 != 2) {
          return false;
        }
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.zutiRadarHeight_MAX = Integer.parseInt(getValue());
        PlMission.setChanged();
        return false;
      }
    });
    this.zuti_manageAircrafts = new Zuti_WManageAircrafts();
  }

  private void fillTabAircraft()
  {
    ActorBorn localActorBorn = (ActorBorn)builder.selectedActor();
    int i = this.lstAvailable.selectRow;
    int j = this.lstInReserve.selectRow;
    this.lstAvailable.lst = localActorBorn.airNames;
    this.lstInReserve.lst.clear();
    ArrayList localArrayList = Main.cur().airClasses;
    Class localClass;
    String str;
    for (int k = 0; k < localArrayList.size(); k++) {
      localClass = (Class)localArrayList.get(k);
      if (Property.containsValue(localClass, "cockpitClass")) {
        str = Property.stringValue(localClass, "keyName");
        if (!this.lstAvailable.lst.contains(str))
          this.lstInReserve.lst.add(str); 
      }
    }
    if (this.lstAvailable.lst.size() > 0) {
      this.lstAvailable.lst.clear();
      for (k = 0; k < localArrayList.size(); k++) {
        localClass = (Class)localArrayList.get(k);
        if (Property.containsValue(localClass, "cockpitClass")) {
          str = Property.stringValue(localClass, "keyName");
          if (!this.lstInReserve.lst.contains(str))
            this.lstAvailable.lst.add(str);
        }
      }
    }
    if (i >= 0) {
      if (this.lstAvailable.lst.size() > 0) {
        if (i >= this.lstAvailable.lst.size())
          i = this.lstAvailable.lst.size() - 1;
      }
      else i = -1;
    }

    this.lstAvailable.setSelect(i, 0);

    if (j >= 0) {
      if (this.lstInReserve.lst.size() > 0) {
        if (j >= this.lstInReserve.lst.size())
          j = this.lstInReserve.lst.size() - 1;
      }
      else j = -1;
    }

    this.lstInReserve.setSelect(j, 0);

    this.lstAvailable.resized();
    this.lstInReserve.resized();
  }

  private void addAllAircraft(ArrayList paramArrayList) {
    ArrayList localArrayList = Main.cur().airClasses;
    for (int i = 0; i < localArrayList.size(); i++) {
      Class localClass = (Class)localArrayList.get(i);
      if (Property.containsValue(localClass, "cockpitClass")) {
        String str = Property.stringValue(localClass, "keyName");
        if (!paramArrayList.contains(str)) {
          paramArrayList.add(str);
        }
      }
    }

    if (ZUTI_ALL_AC_LIST_SIZE < 0)
      ZUTI_ALL_AC_LIST_SIZE = paramArrayList.size();
  }

  private int countAllAircraft() {
    int i = 0;
    ArrayList localArrayList = Main.cur().airClasses;
    for (int j = 0; j < localArrayList.size(); j++) {
      Class localClass = (Class)localArrayList.get(j);
      if (Property.containsValue(localClass, "cockpitClass"))
        i++;
    }
    return i;
  }
  private void setAircraftSizes(GWindow paramGWindow) {
    float f1 = paramGWindow.win.dx;
    float f2 = paramGWindow.win.dy;
    GFont localGFont = paramGWindow.root.textFonts[0];
    float f3 = paramGWindow.lAF().metric();
    GSize localGSize = new GSize();
    localGFont.size(i18n("bplace_addall"), localGSize);
    float f4 = localGSize.dx;
    localGFont.size(i18n("bplace_add"), localGSize);
    float f5 = localGSize.dx;
    localGFont.size(i18n("bplace_delall"), localGSize);
    float f6 = localGSize.dx;
    localGFont.size(i18n("bplace_del"), localGSize);
    float f7 = localGSize.dx;
    localGFont.size(i18n("bplace_planes"), localGSize);
    float f8 = localGSize.dx;
    localGFont.size(i18n("bplace_list"), localGSize);
    float f9 = localGSize.dx;

    localGFont.size("Modify", localGSize);
    float f10 = localGSize.dx;

    float f11 = f4;
    if (f11 < f5) f11 = f5;
    if (f11 < f6) f11 = f6;
    if (f11 < f7) f11 = f7;

    if (f11 < f10) {
      f11 = f10;
    }

    float f12 = f3 + f11;
    f11 += f3 + 4.0F * f3 + f8 + 4.0F * f3 + f9 + 4.0F * f3;
    if (f1 < f11) f1 = f11;

    float f13 = 10.0F * f3 + 10.0F * f3 + 2.0F * f3;
    if (f2 < f13) f2 = f13;

    float f14 = (f1 - f12) / 2.0F;
    this.bAddAll.setPosSize(f14, f3, f12, 2.0F * f3);
    this.bAdd.setPosSize(f14, f3 + 2.0F * f3, f12, 2.0F * f3);
    this.bRemAll.setPosSize(f14, 2.0F * f3 + 4.0F * f3, f12, 2.0F * f3);
    this.bRem.setPosSize(f14, 2.0F * f3 + 6.0F * f3, f12, 2.0F * f3);
    this.bModifyPlane.setPosSize(f14, 2.0F * f3 + 10.0F * f3, f12, 2.0F * f3);

    float f15 = (f1 - f12 - 4.0F * f3) / 2.0F;
    float f16 = f2 - 6.0F * f3 - 2.0F * f3 - 3.0F * f3;
    this.lstAvailable.setPosSize(f3, f3, f15, f16);
    this.lstInReserve.setPosSize(f1 - f3 - f15, f3, f15, f16);

    localGFont.size(" " + i18n("bplace_cats") + " ", localGSize);
    f15 = localGSize.dx;
    float f17 = f3 + f16;
    this.lSeparate.setPosSize(2.0F * f3, f17, f15, 2.0F * f3);
    this.bSeparate.setPosSize(f3, f17 + f3, f1 - 2.0F * f3, f2 - f17 - 2.0F * f3);

    localGFont.size(i18n("bplace_country"), localGSize);
    float f18 = localGSize.dx;
    localGFont.size(i18n("bplace_year"), localGSize);
    if (f18 < localGSize.dx) f18 = localGSize.dx;
    localGFont.size(i18n("bplace_category"), localGSize);
    if (f18 < localGSize.dx) f18 = localGSize.dx;
    f12 = 2.0F * f3 + f5 + f7;
    f15 = f1 - f18 - f12 - 6.0F * f3;

    float f19 = paramGWindow.lAF().getComboH();
    this.lCountry.setPosSize(2.0F * f3, f17 + 2.0F * f3, f18, 2.0F * f3);
    this.cCountry.setPosSize(2.0F * f3 + f18 + f3, f17 + 2.0F * f3 + (2.0F * f3 - f19) / 2.0F, f15, f19);
    this.bCountryAdd.setPosSize(f1 - 4.0F * f3 - f7 - f5, f17 + 2.0F * f3, f3 + f5, 2.0F * f3);
    this.bCountryRem.setPosSize(f1 - 3.0F * f3 - f7, f17 + 2.0F * f3, f3 + f7, 2.0F * f3);
    this.lYear.setPosSize(2.0F * f3, f17 + 4.0F * f3, f18, 2.0F * f3);
    this.cYear.setPosSize(2.0F * f3 + f18 + f3, f17 + 4.0F * f3 + (2.0F * f3 - f19) / 2.0F, f15, f19);
    this.bYearAdd.setPosSize(f1 - 4.0F * f3 - f7 - f5, f17 + 4.0F * f3, f3 + f5, 2.0F * f3);
    this.bYearRem.setPosSize(f1 - 3.0F * f3 - f7, f17 + 4.0F * f3, f3 + f7, 2.0F * f3);
    this.lType.setPosSize(2.0F * f3, f17 + 6.0F * f3, f18, 2.0F * f3);
    this.cType.setPosSize(2.0F * f3 + f18 + f3, f17 + 6.0F * f3 + (2.0F * f3 - f19) / 2.0F, f15, f19);
    this.bTypeAdd.setPosSize(f1 - 4.0F * f3 - f7 - f5, f17 + 6.0F * f3, f3 + f5, 2.0F * f3);
    this.bTypeRem.setPosSize(f1 - 3.0F * f3 - f7, f17 + 6.0F * f3, f3 + f7, 2.0F * f3);
  }

  private boolean zutiFindAirport(Point3d paramPoint3d)
  {
    paramPoint3d.z = 0.0D;
    Airport localAirport = Airport.nearest(paramPoint3d, -1, 7);

    if (localAirport != null)
    {
      double d1 = 250000.0D;
      Point3d localPoint3d1 = localAirport.pos.getAbsPoint();

      double d2 = (localPoint3d1.x - paramPoint3d.x) * (localPoint3d1.x - paramPoint3d.x) + (localPoint3d1.y - paramPoint3d.y) * (localPoint3d1.y - paramPoint3d.y);
      if (d2 > d1)
      {
        return true;
      }

      paramPoint3d.set(localAirport.pos.getAbsPoint());
      for (int i = 0; i < this.allActors.size(); i++)
      {
        ActorBorn localActorBorn = (ActorBorn)this.allActors.get(i);
        Point3d localPoint3d2 = localActorBorn.pos.getAbsPoint();
        d2 = (paramPoint3d.x - localPoint3d2.x) * (paramPoint3d.x - localPoint3d2.x) + (paramPoint3d.y - localPoint3d2.y) * (paramPoint3d.y - localPoint3d2.y);
        if (d2 < 100.0D) {
          return false;
        }
      }
      return true;
    }

    return true;
  }

  private void zutiResetValues()
  {
    if (this.wHeight != null)
      this.wHeight.setValue("");
    if (this.wSpeed != null)
      this.wSpeed.setValue("");
    if (this.wOrient != null)
      this.wOrient.setValue("");
    if (this.wMaxPilots != null)
      this.wMaxPilots.setValue("");
    if (this.wFriction != null) {
      this.wFriction.setValue("");
    }
    if (this.wZutiRadar_Min != null)
      this.wZutiRadar_Min.setValue("");
    if (this.wZutiRadar_Max != null)
      this.wZutiRadar_Max.setValue("");
    if (this.wZutiRadar_Range != null)
      this.wZutiRadar_Range.setValue("");
  }

  private String BoolToInt(boolean paramBoolean)
  {
    if (paramBoolean) {
      return "1";
    }
    return "0";
  }

  private void zutiSaveBornPlaceCountries(ActorBorn paramActorBorn, SectFile paramSectFile, int paramInt)
  {
    if ((paramActorBorn.zutiHomeBaseCountries != null) && (paramActorBorn.zutiHomeBaseCountries.size() > 0))
    {
      Collections.sort(paramActorBorn.zutiHomeBaseCountries);

      int i = paramSectFile.sectionAdd("BornPlaceCountries" + paramInt);
      for (int j = 0; j < paramActorBorn.zutiHomeBaseCountries.size(); j++)
      {
        String str1 = (String)paramActorBorn.zutiHomeBaseCountries.get(j);
        String str2 = I18N.getCountryKey(str1);
        if (str2 != null)
          paramSectFile.lineAdd(i, str2);
      }
    }
  }

  private void zutiLoadBornPlaceCountries(ActorBorn paramActorBorn, SectFile paramSectFile)
  {
    ResourceBundle localResourceBundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());
    int i = paramSectFile.sectionIndex("MDS_BornPlace_" + (int)paramActorBorn.pos.getAbsPoint().x + "_" + (int)paramActorBorn.pos.getAbsPoint().y + "_Countries");
    if (i >= 0)
    {
      if (paramActorBorn.zutiHomeBaseCountries == null) {
        paramActorBorn.zutiHomeBaseCountries = new ArrayList();
      }
      paramActorBorn.zutiHomeBaseCountries.clear();

      int j = paramSectFile.vars(i);
      for (int k = 0; k < j; k++)
      {
        try
        {
          String str1 = paramSectFile.var(i, k);
          String str2 = localResourceBundle.getString(str1);
          if (!paramActorBorn.zutiHomeBaseCountries.contains(str2))
            paramActorBorn.zutiHomeBaseCountries.add(str2);
        }
        catch (Exception localException)
        {
        }
      }
    }
  }

  private void zutiCountriesWindowRefresh(ActorBorn paramActorBorn)
  {
    if (paramActorBorn == null) {
      return;
    }
    try
    {
      if (this.zuti_homeBaseCountries == null) {
        return;
      }
      if (this.zuti_homeBaseCountries.isVisible())
      {
        this.zuti_homeBaseCountries.close(true);

        this.zuti_homeBaseCountries.setSelectedCountries(paramActorBorn);
        this.zuti_homeBaseCountries.showWindow();
      }
    }
    catch (Exception localException) {
    }
  }

  private void zutiCountriesWindowShow(ActorBorn paramActorBorn, int paramInt) {
    if (paramActorBorn == null) {
      return;
    }
    try
    {
      if (this.zuti_homeBaseCountries == null) {
        this.zuti_homeBaseCountries = new Zuti_WHomeBaseCountries();
      }
      this.zuti_homeBaseCountries.setMode(paramInt);

      if (this.zuti_homeBaseCountries.isVisible())
      {
        this.zuti_homeBaseCountries.close(true);

        this.zuti_homeBaseCountries.setSelectedCountries(paramActorBorn);
        this.zuti_homeBaseCountries.showWindow();
      }
      else
      {
        this.zuti_homeBaseCountries.setSelectedCountries(paramActorBorn);
        this.zuti_homeBaseCountries.showWindow();
      }
    }
    catch (Exception localException)
    {
    }
  }

  private ArrayList zutiSyncLists(ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject1;
    int j;
    Object localObject2;
    for (int i = 0; i < paramArrayList1.size(); i++)
    {
      localObject1 = (String)paramArrayList1.get(i);
      j = 0;
      for (int k = 0; k < paramArrayList2.size(); k++)
      {
        localObject2 = (ZutiAircraft)paramArrayList2.get(k);

        if (!((ZutiAircraft)localObject2).getAcName().equals(localObject1))
          continue;
        j = 1;
        break;
      }

      if (j != 0) {
        continue;
      }
      ZutiAircraft localZutiAircraft = new ZutiAircraft();
      localZutiAircraft.setAcName((String)localObject1);
      localZutiAircraft.setMaxAllowed(0);
      localObject2 = new ArrayList();
      ((ArrayList)localObject2).add("Default");
      localZutiAircraft.setSelectedWeapons((ArrayList)localObject2);
      localArrayList.add(localZutiAircraft);
    }

    for (i = 0; i < localArrayList.size(); i++) {
      paramArrayList2.add(localArrayList.get(i));
    }

    localArrayList.clear();
    for (i = 0; i < paramArrayList2.size(); i++)
    {
      localObject1 = (ZutiAircraft)paramArrayList2.get(i);
      j = 0;
      for (int m = 0; m < paramArrayList1.size(); m++)
      {
        localObject2 = (String)paramArrayList1.get(m);
        if (!((ZutiAircraft)localObject1).getAcName().equals(localObject2))
          continue;
        j = 1;
        break;
      }

      if (j == 0) {
        localArrayList.add(localObject1);
      }
    }
    for (i = 0; i < localArrayList.size(); i++) {
      paramArrayList2.remove(localArrayList.get(i));
    }
    return (ArrayList)(ArrayList)paramArrayList2;
  }

  private Zuti_WAircraftLoadout zutiCreateAndLoadLoadoutsObject(ActorBorn paramActorBorn) {
    paramActorBorn.zutiAcLoadouts = new Zuti_WAircraftLoadout();
    paramActorBorn.zutiAcLoadouts.modifiedAircrafts = new ArrayList();

    for (int i = 0; i < paramActorBorn.airNames.size(); i++)
    {
      String str = (String)paramActorBorn.airNames.get(i);
      ZutiAircraft localZutiAircraft = new ZutiAircraft();
      localZutiAircraft.setAcName(str);
      if (paramActorBorn.zutiDecreasingNumberOfPlanes)
        localZutiAircraft.setMaxAllowed(-1);
      else
        localZutiAircraft.setMaxAllowed(0);
      ArrayList localArrayList = new ArrayList();
      localArrayList.add("Default");
      localZutiAircraft.setSelectedWeapons(localArrayList);
      paramActorBorn.zutiAcLoadouts.modifiedAircrafts.add(localZutiAircraft);
    }

    return paramActorBorn.zutiAcLoadouts;
  }

  static
  {
    Property.set(PlMisBorn.class, "name", "MisBorn");
  }

  class Table extends GWindowTable
  {
    public ArrayList lst = new ArrayList();

    public boolean zutiShowAcWeaponsWindow = false;

    public int countRows() { return this.lst != null ? this.lst.size() : 0; } 
    public Object getValueAt(int paramInt1, int paramInt2) {
      if (this.lst == null) return null;
      if ((paramInt1 < 0) || (paramInt1 >= this.lst.size())) return null;
      String str = (String)this.lst.get(paramInt1);
      return I18N.plane(str);
    }
    public void resolutionChanged() {
      this.vSB.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Table(GWindow paramString, String paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float arg7) {
      super(paramFloat2, paramFloat3, paramFloat4, localObject);
      this.bColumnsSizable = false;
      addColumn(paramFloat1, null);
      this.vSB.scroll = rowHeight(0);
      resized();
    }
  }

  static class Item
  {
    public String name;

    public Item(String paramString)
    {
      this.name = paramString;
    }
  }
}