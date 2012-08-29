package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class PlMisBorn extends Plugin
{
  private static final int rMIN = 500;
  private static final int rMAX = 10000;
  private static final int rDEF = 1000;
  private static final int rSTEP = 50;
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
    this.allActors = new ArrayList();

    this.item = new Item[] { new Item(Plugin.i18n("BornPlace")) };

    this.p2d = new Point2d();
    this.p2dt = new Point2d();
    this.p3d = new Point3d();

    this._actorInfo = new String[1];
  }

  public void renderMap2DAfter()
  {
    if (Plugin.builder.isFreeView()) return;
    if (!this.viewType.bChecked) return;
    Actor localActor = Plugin.builder.selectedActor();
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorBorn localActorBorn = (ActorBorn)this.allActors.get(j);
      if (Plugin.builder.project2d(localActorBorn.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), this.p2d)) {
        int k = Army.color(localActorBorn.getArmy());
        if (localActorBorn == localActor)
          k = Builder.colorSelected();
        IconDraw.setColor(k);
        IconDraw.render(localActorBorn, this.p2d.jdField_x_of_type_Double, this.p2d.jdField_y_of_type_Double);
        localActorBorn.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(this.p3d);
        this.p3d.jdField_x_of_type_Double += localActorBorn.r;
        if (Plugin.builder.project2d(this.p3d, this.p2dt)) {
          double d = this.p2dt.jdField_x_of_type_Double - this.p2d.jdField_x_of_type_Double;
          if (d > Plugin.builder.conf.iconSize / 3)
            drawCircle(this.p2d.jdField_x_of_type_Double, this.p2d.jdField_y_of_type_Double, d, k);
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
    int i = this.allActors.size();
    if (i == 0) return true;
    int j = countAllAircraft();
    int k = paramSectFile.sectionAdd("BornPlace");
    for (int m = 0; m < i; m++) {
      ActorBorn localActorBorn = (ActorBorn)this.allActors.get(m);
      paramSectFile.lineAdd(k, "" + localActorBorn.getArmy() + " " + localActorBorn.r + " " + (int)localActorBorn.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_x_of_type_Double + " " + (int)localActorBorn.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_y_of_type_Double + " " + (localActorBorn.bParachute ? "" : "0"));

      int n = localActorBorn.airNames.size();
      if ((n > 0) && (n < j)) {
        int i1 = paramSectFile.sectionAdd("BornPlace" + m);
        for (int i2 = 0; i2 < n; i2++) {
          String str = (String)localActorBorn.airNames.get(i2);
          paramSectFile.lineAdd(i1, str);
        }
      }
    }
    return true;
  }

  public void load(SectFile paramSectFile) {
    int i = paramSectFile.sectionIndex("BornPlace");
    if (i >= 0) {
      int j = paramSectFile.vars(i);
      Point3d localPoint3d = new Point3d();
      for (int k = 0; k < j; k++) {
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
        int m = localNumberTokenizer.next(0, 0, Army.amountNet() - 1);
        int n = localNumberTokenizer.next(1000, 500, 10000);
        localPoint3d.jdField_x_of_type_Double = localNumberTokenizer.next(0);
        localPoint3d.jdField_y_of_type_Double = localNumberTokenizer.next(0);
        boolean bool = localNumberTokenizer.next(1) == 1;
        ActorBorn localActorBorn = insert(localPoint3d, false);
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
            for (int i3 = 0; i3 < i2; i3++) {
              String str = paramSectFile.var(i1, i3);
              if (str != null) {
                str = str.intern();
                Class localClass = (Class)Property.value(str, "airClass", null);
                if ((localClass == null) || 
                  (!Property.containsValue(localClass, "cockpitClass"))) continue;
                localActorBorn.airNames.add(str);
              }
            }
          }
          if (localActorBorn.airNames.size() == 0)
            addAllAircraft(localActorBorn.airNames);
        }
      }
    }
  }

  public void deleteAll() {
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
      return false;
    paramPoint3d.set(localAirport.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
    for (int i = 0; i < this.allActors.size(); i++) {
      ActorBorn localActorBorn = (ActorBorn)this.allActors.get(i);
      Point3d localPoint3d = localActorBorn.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
      double d = (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) * (paramPoint3d.jdField_x_of_type_Double - localPoint3d.jdField_x_of_type_Double) + (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double) * (paramPoint3d.jdField_y_of_type_Double - localPoint3d.jdField_y_of_type_Double);
      if (d < 100.0D)
        return false;
    }
    return true;
  }

  private ActorBorn insert(Point3d paramPoint3d, boolean paramBoolean) {
    if (this.allActors.size() >= 255) {
      return null;
    }
    if (!findAirport(paramPoint3d))
      return null;
    try {
      ActorBorn localActorBorn = new ActorBorn(paramPoint3d);
      addAllAircraft(localActorBorn.airNames);

      Plugin.builder.align(localActorBorn);
      Property.set(localActorBorn, "builderSpawn", "");
      Property.set(localActorBorn, "builderPlugin", this);
      this.allActors.add(localActorBorn);
      if (paramBoolean)
        Plugin.builder.setSelected(localActorBorn);
      PlMission.setChanged();
      return localActorBorn; } catch (Exception localException) {
    }
    return null;
  }

  public void insert(Loc paramLoc, boolean paramBoolean) {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    if (i != this.startComboBox1)
      return;
    if ((j < 0) || (j >= this.item.length))
      return;
    insert(paramLoc.getPoint(), paramBoolean);
  }

  public void changeType() {
    Plugin.builder.setSelected(null);
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
    if (Plugin.getPlugin("Mission") == null)
      throw new RuntimeException("PlMisBorn: plugin 'Mission' not found");
    this.pluginMission = ((PlMission)Plugin.getPlugin("Mission"));
  }

  private void fillComboBox2(int paramInt) {
    if (paramInt != this.startComboBox1)
      return;
    if (Plugin.builder.wSelect.curFilledType != paramInt) {
      Plugin.builder.wSelect.curFilledType = paramInt;
      Plugin.builder.wSelect.comboBox2.clear(false);
      for (int i = 0; i < this.item.length; i++)
        Plugin.builder.wSelect.comboBox2.add(this.item[i].name);
      Plugin.builder.wSelect.comboBox1.setSelected(paramInt, true, false);
    }
    Plugin.builder.wSelect.comboBox2.setSelected(0, true, false);
    Plugin.builder.wSelect.setMesh(null, true);
  }

  public void viewTypeAll(boolean paramBoolean) {
    this.viewType.bChecked = paramBoolean;
    updateView();
  }

  public String[] actorInfo(Actor paramActor) {
    ActorBorn localActorBorn = (ActorBorn)paramActor;
    this._actorInfo[0] = Plugin.i18n("BornPlace");
    return this._actorInfo;
  }

  public void syncSelector()
  {
    ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
    fillComboBox2(this.startComboBox1);
    Plugin.builder.wSelect.comboBox2.setSelected(0, true, false);
    Plugin.builder.wSelect.tabsClient.addTab(1, this.tabTarget);
    Plugin.builder.wSelect.tabsClient.addTab(2, this.tabAircraft);
    fillTabAircraft();

    this.wR.setPos((localActorBorn.r - 500) / 50, false);
    this.wArmy.setSelected(localActorBorn.getArmy(), true, false);
  }

  public void updateSelector() {
    fillTabAircraft();
  }

  public void createGUI()
  {
    this.startComboBox1 = Plugin.builder.wSelect.comboBox1.size();
    Plugin.builder.wSelect.comboBox1.add(Plugin.i18n("BornPlace"));
    Plugin.builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMisBorn.this.fillComboBox2(i);
        return false;
      }
    });
    int i = Plugin.builder.mDisplayFilter.subMenu.size() - 1;
    while (i >= 0) {
      if (this.pluginMission.viewBridge == Plugin.builder.mDisplayFilter.subMenu.getItem(i))
        break;
      i--;
    }
    i--;
    if (i >= 0) {
      this.viewType = Plugin.builder.mDisplayFilter.subMenu.addItem(i, new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, Plugin.i18n("showBornPlaces"), null)
      {
        public void execute() {
          this.jdField_bChecked_of_type_Boolean = (!this.jdField_bChecked_of_type_Boolean);
          PlMisBorn.this.updateView();
        }
      });
      this.viewType.bChecked = true;
    }

    GWindowDialogClient localGWindowDialogClient1 = (GWindowDialogClient)Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
    this.tabTarget = Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("BornPlaceActor"), localGWindowDialogClient1);
    localGWindowDialogClient1.addControl(this.wR = new GWindowHSliderInt(localGWindowDialogClient1, 0, 190, 20, 1.0F, 1.0F, 10.0F) {
      public void afterCreated() { super.afterCreated();
        this.bSlidingNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
        localActorBorn.r = (pos() * 50 + 500);
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient1.addControl(this.wArmy = new GWindowComboControl(localGWindowDialogClient1, 1.0F, 3.0F, 10.0F) {
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
    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 1.0F, 5.0F, 8.0F, 1.3F, Plugin.i18n("Parachute"), null));
    localGWindowDialogClient1.addControl(this.wParachute = new GWindowCheckBox(localGWindowDialogClient1, 10.0F, 5.0F, null) {
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
    GWindowDialogClient localGWindowDialogClient2 = (GWindowDialogClient)Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient() {
      public void resized() {
        super.resized();
        PlMisBorn.this.setAircraftSizes(this);
      }
    });
    this.tabAircraft = Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("bplace_aircraft"), localGWindowDialogClient2);
    this.lstAvailable = new Table(localGWindowDialogClient2, Plugin.i18n("bplace_planes"), 1.0F, 1.0F, 6.0F, 10.0F);
    this.lstInReserve = new Table(localGWindowDialogClient2, Plugin.i18n("bplace_list"), 14.0F, 1.0F, 6.0F, 10.0F);
    localGWindowDialogClient2.addControl(this.bAddAll = new GWindowButton(localGWindowDialogClient2, 8.0F, 1.0F, 5.0F, 2.0F, Plugin.i18n("bplace_addall"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisBorn.this.lstAvailable.lst.clear();
        PlMisBorn.this.addAllAircraft(PlMisBorn.this.lstAvailable.lst);
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addControl(this.bAdd = new GWindowButton(localGWindowDialogClient2, 8.0F, 3.0F, 5.0F, 2.0F, Plugin.i18n("bplace_add"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        int i = PlMisBorn.this.lstInReserve.selectRow;
        if ((i < 0) || (i >= PlMisBorn.this.lstInReserve.lst.size())) return true;
        PlMisBorn.this.lstAvailable.lst.add(PlMisBorn.this.lstInReserve.lst.get(i));
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addControl(this.bRemAll = new GWindowButton(localGWindowDialogClient2, 8.0F, 6.0F, 5.0F, 2.0F, Plugin.i18n("bplace_delall"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisBorn.this.lstAvailable.lst.clear();
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addControl(this.bRem = new GWindowButton(localGWindowDialogClient2, 8.0F, 8.0F, 5.0F, 2.0F, Plugin.i18n("bplace_del"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        int i = PlMisBorn.this.lstAvailable.selectRow;
        if ((i < 0) || (i >= PlMisBorn.this.lstAvailable.lst.size())) return true;
        PlMisBorn.this.lstAvailable.lst.remove(i);
        PlMisBorn.this.fillTabAircraft();
        return true;
      }
    });
    localGWindowDialogClient2.addLabel(this.lSeparate = new GWindowLabel(localGWindowDialogClient2, 3.0F, 12.0F, 12.0F, 1.6F, " " + Plugin.i18n("bplace_cats") + " ", null));
    this.bSeparate = new GWindowBoxSeparate(localGWindowDialogClient2, 1.0F, 12.5F, 27.0F, 8.0F);
    this.bSeparate.exclude = this.lSeparate;

    localGWindowDialogClient2.addLabel(this.lCountry = new GWindowLabel(localGWindowDialogClient2, 2.0F, 14.0F, 7.0F, 1.6F, Plugin.i18n("bplace_country"), null));
    localGWindowDialogClient2.addControl(this.cCountry = new GWindowComboControl(localGWindowDialogClient2, 9.0F, 14.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        ResourceBundle localResourceBundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());
        TreeMap localTreeMap = new TreeMap();
        ArrayList localArrayList = Main.cur().airClasses;
        String str1;
        String str2;
        for (int i = 0; i < localArrayList.size(); i++) {
          localObject = (Class)localArrayList.get(i);
          if (Property.containsValue((Class)localObject, "cockpitClass")) {
            str1 = Property.stringValue((Class)localObject, "originCountry", null);
            if (str1 != null) {
              str2 = null;
              try {
                str2 = localResourceBundle.getString(str1);
              } catch (Exception localException) {
                continue;
              }
              localTreeMap.put(str2, str1);
            }
          }
        }
        Object localObject = localTreeMap.keySet().iterator();
        while (((Iterator)localObject).hasNext()) {
          str1 = (String)((Iterator)localObject).next();
          str2 = (String)localTreeMap.get(str1);
          PlMisBorn.lstCountry.add(str2);
          add(str1);
        }
        if (PlMisBorn.lstCountry.size() > 0)
          setSelected(0, true, false);
      }
    });
    localGWindowDialogClient2.addControl(this.bCountryAdd = new GWindowButton(localGWindowDialogClient2, 17.0F, 14.0F, 5.0F, 1.6F, Plugin.i18n("bplace_add"), null) {
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
    localGWindowDialogClient2.addControl(this.bCountryRem = new GWindowButton(localGWindowDialogClient2, 22.0F, 14.0F, 5.0F, 1.6F, Plugin.i18n("bplace_del"), null) {
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
    localGWindowDialogClient2.addLabel(this.lYear = new GWindowLabel(localGWindowDialogClient2, 2.0F, 16.0F, 7.0F, 1.6F, Plugin.i18n("bplace_year"), null));
    localGWindowDialogClient2.addControl(this.cYear = new GWindowComboControl(localGWindowDialogClient2, 9.0F, 16.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        TreeMap localTreeMap = new TreeMap();
        ArrayList localArrayList = Main.cur().airClasses;
        for (int i = 0; i < localArrayList.size(); i++) {
          localObject = (Class)localArrayList.get(i);
          if (Property.containsValue((Class)localObject, "cockpitClass")) {
            float f = Property.floatValue((Class)localObject, "yearService", 0.0F);
            if (f != 0.0F)
              localTreeMap.put("" + (int)f, null);
          }
        }
        Object localObject = localTreeMap.keySet().iterator();
        while (((Iterator)localObject).hasNext()) {
          String str = (String)((Iterator)localObject).next();
          PlMisBorn.lstYear.add(str);
          add(str);
        }
        if (PlMisBorn.lstYear.size() > 0)
          setSelected(0, true, false);
      }
    });
    localGWindowDialogClient2.addControl(this.bYearAdd = new GWindowButton(localGWindowDialogClient2, 17.0F, 16.0F, 5.0F, 1.6F, Plugin.i18n("bplace_add"), null) {
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
    localGWindowDialogClient2.addControl(this.bYearRem = new GWindowButton(localGWindowDialogClient2, 22.0F, 16.0F, 5.0F, 1.6F, Plugin.i18n("bplace_del"), null) {
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
    localGWindowDialogClient2.addLabel(this.lType = new GWindowLabel(localGWindowDialogClient2, 2.0F, 18.0F, 7.0F, 1.6F, Plugin.i18n("bplace_category"), null));
    localGWindowDialogClient2.addControl(this.cType = new GWindowComboControl(localGWindowDialogClient2, 9.0F, 18.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        TreeMap localTreeMap = new TreeMap();
        ArrayList localArrayList = Main.cur().airClasses;
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
        Object localObject = localTreeMap.keySet().iterator();
        while (((Iterator)localObject).hasNext()) {
          String str = (String)((Iterator)localObject).next();
          Class localClass = (Class)localTreeMap.get(str);
          PlMisBorn.lstType.add(localClass);
          add(str);
        }
        if (PlMisBorn.lstType.size() > 0)
          setSelected(0, true, false);
      }
    });
    localGWindowDialogClient2.addControl(this.bTypeAdd = new GWindowButton(localGWindowDialogClient2, 17.0F, 18.0F, 5.0F, 1.6F, Plugin.i18n("bplace_add"), null) {
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
    localGWindowDialogClient2.addControl(this.bTypeRem = new GWindowButton(localGWindowDialogClient2, 22.0F, 18.0F, 5.0F, 1.6F, Plugin.i18n("bplace_del"), null) {
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
  }

  private void fillTabAircraft()
  {
    ActorBorn localActorBorn = (ActorBorn)Plugin.builder.selectedActor();
    int i = this.lstAvailable.jdField_selectRow_of_type_Int;
    int j = this.lstInReserve.jdField_selectRow_of_type_Int;
    this.lstAvailable.lst = localActorBorn.airNames;
    this.lstInReserve.lst.clear();
    ArrayList localArrayList = Main.cur().airClasses;
    Object localObject;
    for (int k = 0; k < localArrayList.size(); k++) {
      Class localClass = (Class)localArrayList.get(k);
      if (Property.containsValue(localClass, "cockpitClass")) {
        localObject = Property.stringValue(localClass, "keyName");
        if (!this.lstAvailable.lst.contains(localObject))
          this.lstInReserve.lst.add(localObject); 
      }
    }
    if (this.lstAvailable.lst.size() > 0) {
      this.lstAvailable.lst.clear();
      for (int m = 0; m < localArrayList.size(); m++) {
        localObject = (Class)localArrayList.get(m);
        if (Property.containsValue((Class)localObject, "cockpitClass")) {
          String str = Property.stringValue((Class)localObject, "keyName");
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
        if (!paramArrayList.contains(str))
          paramArrayList.add(str); 
      }
    }
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
    localGFont.size(Plugin.i18n("bplace_addall"), localGSize);
    float f4 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_add"), localGSize);
    float f5 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_delall"), localGSize);
    float f6 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_del"), localGSize);
    float f7 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_planes"), localGSize);
    float f8 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_list"), localGSize);
    float f9 = localGSize.dx;
    float f10 = f4;
    if (f10 < f5) f10 = f5;
    if (f10 < f6) f10 = f6;
    if (f10 < f7) f10 = f7;
    float f11 = f3 + f10;
    f10 += f3 + 4.0F * f3 + f8 + 4.0F * f3 + f9 + 4.0F * f3;
    if (f1 < f10) f1 = f10;

    float f12 = 10.0F * f3 + 10.0F * f3 + 2.0F * f3;
    if (f2 < f12) f2 = f12;

    float f13 = (f1 - f11) / 2.0F;
    this.bAddAll.setPosSize(f13, f3, f11, 2.0F * f3);
    this.bAdd.setPosSize(f13, f3 + 2.0F * f3, f11, 2.0F * f3);
    this.bRemAll.setPosSize(f13, 2.0F * f3 + 4.0F * f3, f11, 2.0F * f3);
    this.bRem.setPosSize(f13, 2.0F * f3 + 6.0F * f3, f11, 2.0F * f3);

    float f14 = (f1 - f11 - 4.0F * f3) / 2.0F;
    float f15 = f2 - 6.0F * f3 - 2.0F * f3 - 3.0F * f3;
    this.lstAvailable.setPosSize(f3, f3, f14, f15);
    this.lstInReserve.setPosSize(f1 - f3 - f14, f3, f14, f15);

    localGFont.size(" " + Plugin.i18n("bplace_cats") + " ", localGSize);
    f14 = localGSize.dx;
    float f16 = f3 + f15;
    this.lSeparate.setPosSize(2.0F * f3, f16, f14, 2.0F * f3);
    this.bSeparate.setPosSize(f3, f16 + f3, f1 - 2.0F * f3, f2 - f16 - 2.0F * f3);

    localGFont.size(Plugin.i18n("bplace_country"), localGSize);
    float f17 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_year"), localGSize);
    if (f17 < localGSize.dx) f17 = localGSize.dx;
    localGFont.size(Plugin.i18n("bplace_category"), localGSize);
    if (f17 < localGSize.dx) f17 = localGSize.dx;
    f11 = 2.0F * f3 + f5 + f7;
    f14 = f1 - f17 - f11 - 6.0F * f3;

    float f18 = paramGWindow.lAF().getComboH();
    this.lCountry.setPosSize(2.0F * f3, f16 + 2.0F * f3, f17, 2.0F * f3);
    this.cCountry.setPosSize(2.0F * f3 + f17 + f3, f16 + 2.0F * f3 + (2.0F * f3 - f18) / 2.0F, f14, f18);
    this.bCountryAdd.setPosSize(f1 - 4.0F * f3 - f7 - f5, f16 + 2.0F * f3, f3 + f5, 2.0F * f3);
    this.bCountryRem.setPosSize(f1 - 3.0F * f3 - f7, f16 + 2.0F * f3, f3 + f7, 2.0F * f3);
    this.lYear.setPosSize(2.0F * f3, f16 + 4.0F * f3, f17, 2.0F * f3);
    this.cYear.setPosSize(2.0F * f3 + f17 + f3, f16 + 4.0F * f3 + (2.0F * f3 - f18) / 2.0F, f14, f18);
    this.bYearAdd.setPosSize(f1 - 4.0F * f3 - f7 - f5, f16 + 4.0F * f3, f3 + f5, 2.0F * f3);
    this.bYearRem.setPosSize(f1 - 3.0F * f3 - f7, f16 + 4.0F * f3, f3 + f7, 2.0F * f3);
    this.lType.setPosSize(2.0F * f3, f16 + 6.0F * f3, f17, 2.0F * f3);
    this.cType.setPosSize(2.0F * f3 + f17 + f3, f16 + 6.0F * f3 + (2.0F * f3 - f18) / 2.0F, f14, f18);
    this.bTypeAdd.setPosSize(f1 - 4.0F * f3 - f7 - f5, f16 + 6.0F * f3, f3 + f5, 2.0F * f3);
    this.bTypeRem.setPosSize(f1 - 3.0F * f3 - f7, f16 + 6.0F * f3, f3 + f7, 2.0F * f3);
  }

  static {
    Property.set(PlMisBorn.class, "name", "MisBorn");
  }

  class Table extends GWindowTable
  {
    public ArrayList lst = new ArrayList();

    public int countRows() { return this.lst != null ? this.lst.size() : 0; } 
    public Object getValueAt(int paramInt1, int paramInt2) {
      if (this.lst == null) return null;
      if ((paramInt1 < 0) || (paramInt1 >= this.lst.size())) return null;
      String str = (String)this.lst.get(paramInt1);
      return I18N.plane(str);
    }
    public void resolutionChanged() {
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
      super.resolutionChanged();
    }
    public Table(GWindow paramString, String paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float arg7) {
      super(paramFloat2, paramFloat3, paramFloat4, localObject);
      this.bColumnsSizable = false;
      addColumn(paramFloat1, null);
      this.jdField_vSB_of_type_ComMaddoxGwindowGWindowVScrollBar.scroll = rowHeight(0);
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