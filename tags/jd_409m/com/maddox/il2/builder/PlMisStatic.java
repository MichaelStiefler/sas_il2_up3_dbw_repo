package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowLookAndFeel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.gwindow.GWindowTabDialogClient.Tab;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Chief;
import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.game.I18N;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.PaintScheme;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric;
import com.maddox.il2.objects.vehicles.planes.PlaneGeneric;
import com.maddox.il2.objects.vehicles.stationary.SirenGeneric;
import com.maddox.il2.objects.vehicles.stationary.SmokeGeneric;
import com.maddox.rts.LDRres;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class PlMisStatic extends Plugin
{
  private ArrayList[] listCountry;
  private HashMap[] mapCountry;
  protected ArrayList allActors = new ArrayList();
  Type[] type;
  private Point3d p3d = new Point3d();
  private Point2d p2d = new Point2d();

  private Point3d p = new Point3d();
  private Orient o = new Orient();

  private ActorSpawnArg spawnArg = new ActorSpawnArg();
  private PlMission pluginMission;
  private int startComboBox1;
  ViewItem[] viewType;
  HashMap viewClasses = new HashMap();

  private String[] _actorInfo = new String[2];
  GWindowTabDialogClient.Tab tabActor;
  GWindowLabel wName;
  GWindowComboControl wArmy;
  GWindowLabel wLTimeOutH;
  GWindowEditControl wTimeOutH;
  GWindowLabel wLTimeOutM;
  GWindowEditControl wTimeOutM;
  GWindowLabel wLCountry;
  GWindowComboControl wCountry;
  GWindowLabel wLSleepM;
  GWindowLabel wLSleepS;
  GWindowEditControl wSleepM;
  GWindowEditControl wRHide;
  GWindowLabel wL1RHide;
  GWindowLabel wL2RHide;
  GWindowEditControl wSleepS;
  GWindowLabel wLSkill;
  GWindowComboControl wSkill;
  GWindowLabel wLSlowfire;
  GWindowEditControl wSlowfire;
  private Object[] pathes = new Object[1];
  private Object[] points = new Object[1];

  private void initCountry()
  {
    if (this.listCountry != null) return;
    this.listCountry = new ArrayList[3];
    this.mapCountry = new HashMap[3];
    for (int i = 0; i < 3; i++) {
      this.listCountry[i] = new ArrayList();
      this.mapCountry[i] = new HashMap();
    }ResourceBundle localResourceBundle;
    try {
      localResourceBundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());
    } catch (Exception localException) {
      localResourceBundle = null;
    }
    HashMap localHashMap = new HashMap();
    List localList = Regiment.getAll();
    for (int j = 0; j < localList.size(); j++) {
      Regiment localRegiment = (Regiment)localList.get(j);
      if (localHashMap.containsKey(localRegiment.country()))
        continue;
      int k = localRegiment.getArmy();
      if ((k < 0) || (k > 2))
        continue;
      localHashMap.put(localRegiment.country(), null);
      Country localCountry = new Country();
      localCountry.name = localRegiment.country();
      if (localResourceBundle != null)
        localCountry.i18nName = localResourceBundle.getString(localCountry.name);
      else
        localCountry.i18nName = localCountry.name;
      this.listCountry[k].add(localCountry);
      this.mapCountry[k].put(localCountry.name, new Integer(this.listCountry[k].size() - 1));
    }
  }

  public void mapLoaded()
  {
    deleteAll();
  }

  public void deleteAll() {
    for (int i = 0; i < this.allActors.size(); i++) {
      Actor localActor = (Actor)this.allActors.get(i);
      if (Actor.isValid(localActor))
        localActor.destroy();
    }
    this.allActors.clear();
  }

  public void delete(Actor paramActor) {
    this.allActors.remove(paramActor);
    paramActor.destroy();
  }

  public void renderMap2D() {
    if (Plugin.builder.isFreeView()) return;
    Actor localActor1 = Plugin.builder.selectedActor();

    Render.prepareStates();

    for (int j = 0; j < this.allActors.size(); j++) {
      Actor localActor2 = (Actor)this.allActors.get(j);
      if ((!Actor.isValid(localActor2)) || (localActor2.icon == null) || (!this.viewClasses.containsKey(localActor2.getClass())))
        continue;
      this.p3d.set(localActor2.pos.getAbsPoint());
      if ((localActor2 instanceof SmokeGeneric))
        this.p3d.jdField_z_of_type_Double = Engine.land().HQ(this.p3d.jdField_x_of_type_Double, this.p3d.jdField_y_of_type_Double);
      if (Plugin.builder.project2d(this.p3d, this.p2d)) {
        int k = localActor2.getArmy();
        int m = Plugin.builder.conf.bShowArmy[k];
        if (m != 0)
        {
          int i;
          if (localActor2 == localActor1) i = Builder.colorSelected(); else
            i = Army.color(localActor2.getArmy());
          IconDraw.setColor(i);
          IconDraw.render(localActor2, this.p2d.jdField_x_of_type_Double, this.p2d.jdField_y_of_type_Double);
          if (Plugin.builder.conf.bShowName) {
            String str = Property.stringValue(localActor2.getClass(), "i18nName", "");
            Plugin.builder.smallFont.output(i, (int)this.p2d.jdField_x_of_type_Double + IconDraw.scrSizeX() / 2 + 2, (int)this.p2d.jdField_y_of_type_Double + Plugin.builder.smallFont.height() - Plugin.builder.smallFont.descender() - IconDraw.scrSizeY() / 2 - 2, 0.0F, str);
          }
        }
      }
    }
  }

  public void load(SectFile paramSectFile)
  {
    initCountry();
    int i = paramSectFile.sectionIndex("Stationary");
    int j;
    int k;
    NumberTokenizer localNumberTokenizer;
    if (i >= 0) {
      j = paramSectFile.vars(i);
      for (k = 0; k < j; k++) {
        localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
        insert(null, localNumberTokenizer.next(""), localNumberTokenizer.next(0), localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0F), localNumberTokenizer.next(0.0F), localNumberTokenizer.next((String)null), localNumberTokenizer.next((String)null), localNumberTokenizer.next((String)null));
      }
    }
    i = paramSectFile.sectionIndex("NStationary");
    if (i >= 0) {
      j = paramSectFile.vars(i);
      for (k = 0; k < j; k++) {
        localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
        insert(localNumberTokenizer.next(""), localNumberTokenizer.next(""), localNumberTokenizer.next(0), localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0F), localNumberTokenizer.next(0.0F), localNumberTokenizer.next((String)null), localNumberTokenizer.next((String)null), localNumberTokenizer.next((String)null));
      }
    }
    Engine.drawEnv().staticTrimToSize();
  }

  public boolean save(SectFile paramSectFile) {
    Orient localOrient1 = new Orient();
    int i = paramSectFile.sectionAdd("NStationary");
    for (int j = 0; j < this.allActors.size(); j++) {
      Actor localActor = (Actor)this.allActors.get(j);
      if ((Actor.isValid(localActor)) && (Property.containsValue(localActor, "builderSpawn"))) {
        Point3d localPoint3d = localActor.pos.getAbsPoint();
        Orient localOrient2 = localActor.pos.getAbsOrient();
        localOrient1.set(localOrient2);
        localOrient1.wrap360();
        float f1 = Property.floatValue(localActor, "timeout", 0.0F);
        if ((localActor instanceof PlaneGeneric)) {
          String str = ((PlaneGeneric)localActor).country;
          paramSectFile.lineAdd(i, localActor.name(), ObjIO.classGetName(localActor.getClass()) + " " + localActor.getArmy() + " " + formatPos(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double, localOrient1.azimut()) + " " + f1 + " " + str);
        }
        else
        {
          int k;
          if (((localActor instanceof ShipGeneric)) || ((localActor instanceof BigshipGeneric))) {
            k = Property.intValue(localActor, "sleep", 0);
            int m = Property.intValue(localActor, "skill", 2);
            float f2 = Property.floatValue(localActor, "slowfire", 1.0F);
            paramSectFile.lineAdd(i, localActor.name(), ObjIO.classGetName(localActor.getClass()) + " " + localActor.getArmy() + " " + formatPos(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double, localOrient1.azimut()) + " " + f1 + " " + k + " " + m + " " + f2);
          }
          else if ((localActor instanceof ArtilleryGeneric)) {
            k = Property.intValue(localActor, "radius_hide", 0);
            paramSectFile.lineAdd(i, localActor.name(), ObjIO.classGetName(localActor.getClass()) + " " + localActor.getArmy() + " " + formatPos(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double, localOrient1.azimut()) + " " + f1 + " " + k);
          }
          else if ((localActor instanceof SmokeGeneric)) {
            paramSectFile.lineAdd(i, localActor.name(), ObjIO.classGetName(localActor.getClass()) + " " + localActor.getArmy() + " " + formatPos(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double, localOrient1.azimut()) + " " + formatValue(localPoint3d.jdField_z_of_type_Double));
          }
          else
          {
            paramSectFile.lineAdd(i, localActor.name(), ObjIO.classGetName(localActor.getClass()) + " " + localActor.getArmy() + " " + formatPos(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double, localOrient1.azimut()) + " " + f1);
          }
        }

      }

    }

    return true;
  }

  private String formatPos(double paramDouble1, double paramDouble2, float paramFloat) {
    return formatValue(paramDouble1) + " " + formatValue(paramDouble2) + " " + formatValue(paramFloat);
  }

  private String formatValue(double paramDouble)
  {
    int i = paramDouble < 0.0D ? 1 : 0;
    if (i != 0) paramDouble = -paramDouble;
    double d = paramDouble + 0.005D - (int)paramDouble;
    if (d >= 0.1D) return (i != 0 ? "-" : "") + (int)paramDouble + "." + (int)(d * 100.0D);
    return (i != 0 ? "-" : "") + (int)paramDouble + ".0" + (int)(d * 100.0D);
  }

  private void makeName(Actor paramActor, String paramString) {
    if ((paramString != null) && (Actor.getByName(paramString) == null)) {
      paramActor.setName(paramString);
      return;
    }
    int i = 0;
    while (true) {
      paramString = i + "_Static";
      if (Actor.getByName(paramString) == null)
        break;
      i++;
    }
    paramActor.setName(paramString);
  }

  private Actor insert(String paramString1, String paramString2, int paramInt, double paramDouble1, double paramDouble2, float paramFloat1, float paramFloat2, String paramString3, String paramString4, String paramString5)
  {
    Class localClass = null;
    try { localClass = ObjIO.classForName(paramString2);
    } catch (Exception localException1) {
      Plugin.builder.tipErr("MissionLoad: class '" + paramString2 + "' not found");
      return null;
    }
    ActorSpawn localActorSpawn = (ActorSpawn)Spawn.get(localClass.getName(), false);
    if (localActorSpawn == null) {
      Plugin.builder.tipErr("MissionLoad: ActorSpawn for '" + paramString2 + "' not found");
      return null;
    }
    this.spawnArg.clear();
    if (paramString1 != null) {
      if ("NONAME".equals(paramString1))
        paramString1 = null;
      if (Actor.getByName(paramString1) != null)
        paramString1 = null;
    }
    if ((paramInt < 0) && (paramInt >= Builder.armyAmount())) {
      Plugin.builder.tipErr("MissionLoad: Wrong actor army '" + paramInt + "'");
      return null;
    }
    this.spawnArg.army = paramInt;
    this.spawnArg.armyExist = true;
    this.spawnArg.country = paramString3;

    Chief.new_DELAY_WAKEUP = 0.0F;
    ArtilleryGeneric.new_RADIUS_HIDE = 0.0F;
    if (paramString3 != null)
      try {
        Chief.new_DELAY_WAKEUP = Integer.parseInt(paramString3);
        ArtilleryGeneric.new_RADIUS_HIDE = Chief.new_DELAY_WAKEUP;
      }
      catch (Exception localException2) {
      }
    Chief.new_SKILL_IDX = 2;
    if (paramString4 != null) {
      try {
        Chief.new_SKILL_IDX = Integer.parseInt(paramString4); } catch (Exception localException3) {
      }
      if ((Chief.new_SKILL_IDX < 0) || (Chief.new_SKILL_IDX > 3)) {
        Plugin.builder.tipErr("MissionLoad: Wrong actor skill '" + Chief.new_SKILL_IDX + "'");
        return null;
      }
    }

    Chief.new_SLOWFIRE_K = 1.0F;
    if (paramString5 != null) {
      try {
        Chief.new_SLOWFIRE_K = Float.parseFloat(paramString5); } catch (Exception localException4) {
      }
      if ((Chief.new_SLOWFIRE_K < 0.5F) || (Chief.new_SLOWFIRE_K > 100.0F)) {
        Plugin.builder.tipErr("MissionLoad: Wrong actor slowfire '" + Chief.new_SLOWFIRE_K + "'");
        return null;
      }
    }

    this.p.set(paramDouble1, paramDouble2, 0.0D);
    this.spawnArg.point = this.p;
    this.o.set(paramFloat1, 0.0F, 0.0F);
    this.spawnArg.orient = this.o;
    try {
      Actor localActor = localActorSpawn.actorSpawn(this.spawnArg);
      if ((localActor instanceof SirenGeneric))
        localActor.setArmy(paramInt);
      if ((localActor instanceof SmokeGeneric)) {
        this.p.jdField_z_of_type_Double = paramFloat2;
        localActor.pos.setAbs(this.p);
        localActor.pos.reset();
      }
      Plugin.builder.align(localActor);
      Property.set(localActor, "builderSpawn", "");
      Property.set(localActor, "builderPlugin", this);
      if (!localActor.isRealTimeFlag())
        localActor.interpCancelAll();
      makeName(localActor, paramString1);
      this.allActors.add(localActor);
      if ((localActor instanceof ArtilleryGeneric)) {
        Property.set(localActor, "timeout", paramFloat2);
        Property.set(localActor, "radius_hide", (int)ArtilleryGeneric.new_RADIUS_HIDE);
      }
      if (((localActor instanceof ShipGeneric)) || ((localActor instanceof BigshipGeneric))) {
        Property.set(localActor, "sleep", (int)Chief.new_DELAY_WAKEUP);
        Property.set(localActor, "skill", Chief.new_SKILL_IDX);
        Property.set(localActor, "slowfire", Chief.new_SLOWFIRE_K);
      }
      return localActor;
    } catch (Exception localException5) {
      System.out.println(localException5.getMessage());
      localException5.printStackTrace();
    }
    return null;
  }

  private Actor insert(ActorSpawn paramActorSpawn, Loc paramLoc, int paramInt, boolean paramBoolean, String paramString)
  {
    this.spawnArg.clear();
    this.spawnArg.point = paramLoc.getPoint();
    this.spawnArg.orient = paramLoc.getOrient();
    this.spawnArg.army = paramInt; this.spawnArg.armyExist = true;
    this.spawnArg.country = paramString;
    try {
      Actor localActor = paramActorSpawn.actorSpawn(this.spawnArg);
      if ((localActor instanceof SirenGeneric))
        localActor.setArmy(paramInt);
      Plugin.builder.align(localActor);
      Property.set(localActor, "builderSpawn", "");
      Property.set(localActor, "builderPlugin", this);
      if (!localActor.isRealTimeFlag())
        localActor.interpCancelAll();
      if ((localActor instanceof ArtilleryGeneric)) {
        Property.set(localActor, "timeout", 0.0F);
        Property.set(localActor, "radius_hide", 0);
      }
      if (((localActor instanceof ShipGeneric)) || ((localActor instanceof BigshipGeneric))) {
        Property.set(localActor, "sleep", 0);
        Property.set(localActor, "skill", 2);
        Property.set(localActor, "slowfire", 1.0F);
      }
      makeName(localActor, null);
      this.allActors.add(localActor);
      if (paramBoolean)
        Plugin.builder.setSelected(localActor);
      PlMission.setChanged();
      return localActor;
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    return null;
  }

  public void insert(Loc paramLoc, boolean paramBoolean)
  {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    if ((i < this.startComboBox1) || (i >= this.startComboBox1 + this.type.length))
    {
      return;
    }
    i -= this.startComboBox1;
    if ((j < 0) || (j >= this.type[i].item.length))
      return;
    ActorSpawn localActorSpawn = this.type[i].item[j].spawn;
    insert(localActorSpawn, paramLoc, this.type[i].item[j].army, paramBoolean, this.type[i].item[j].country);
  }

  public void changeType() {
    int i = Plugin.builder.wSelect.comboBox1.getSelected() - this.startComboBox1;
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    Actor localActor1 = Plugin.builder.selectedActor();
    Loc localLoc = localActor1.pos.getAbs();
    Actor localActor2 = insert(this.type[i].item[j].spawn, localLoc, this.type[i].item[j].army, true, this.type[i].item[j].country);
    if (Plugin.builder.selectedActor() != localActor1) {
      this.allActors.remove(localActor1);
      String str = localActor1.name();
      localActor1.destroy();
      localActor2.setName(str);
    }
  }

  public void configure() {
    if (Plugin.getPlugin("Mission") == null)
      throw new RuntimeException("PlMisStatic: plugin 'Mission' not found");
    this.pluginMission = ((PlMission)Plugin.getPlugin("Mission"));
    if (this.jdField_sectFile_of_type_JavaLangString == null)
      throw new RuntimeException("PlMisStatic: field 'sectFile' not defined");
    SectFile localSectFile = new SectFile(this.jdField_sectFile_of_type_JavaLangString, 0);
    int i = localSectFile.sections();
    if (i <= 0)
      throw new RuntimeException("PlMisStatic: file '" + this.jdField_sectFile_of_type_JavaLangString + "' is empty");
    this.type = new Type[i];
    for (int j = 0; j < i; j++) {
      String str1 = localSectFile.sectionName(j);
      int k = localSectFile.vars(j);
      Item[] arrayOfItem = new Item[k];
      for (int m = 0; m < k; m++) {
        String str2 = localSectFile.var(j, m);
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(localSectFile.value(j, m));
        String str3 = localNumberTokenizer.next((String)null);
        int n = localNumberTokenizer.next(0, 0, Builder.armyAmount() - 1);

        Class localClass = null;
        int i1 = str3.indexOf(' ');
        if (i1 > 0)
          str3 = str3.substring(0, i1);
        try {
          localClass = ObjIO.classForName(str3);
        } catch (Exception localException1) {
          throw new RuntimeException("PlMisStatic: class '" + str3 + "' not found");
        }

        int i2 = str3.lastIndexOf('$');
        if (i2 >= 0) {
          String str4 = str3.substring(0, i2);
          try {
            ObjIO.classForName(str4);
          } catch (Exception localException2) {
            throw new RuntimeException("PlMisStatic: Outer class '" + str4 + "' not found");
          }

        }

        arrayOfItem[m] = new Item(str2, localClass, n);
      }
      this.type[j] = new Type(str1, arrayOfItem);
    }
  }

  void viewUpdate()
  {
    for (int i = 0; i < this.allActors.size(); i++) {
      Actor localActor = (Actor)this.allActors.get(i);
      if ((Actor.isValid(localActor)) && (Property.containsValue(localActor, "builderSpawn")))
        localActor.drawing(this.viewClasses.containsKey(localActor.getClass()));
    }
    if ((Actor.isValid(Plugin.builder.selectedActor())) && (!Plugin.builder.selectedActor().isDrawing()))
      Plugin.builder.setSelected(null);
    if (!Plugin.builder.isFreeView())
      Plugin.builder.repaint(); 
  }

  void viewType(int paramInt, boolean paramBoolean) {
    int i = this.type[paramInt].item.length;
    for (int j = 0; j < i; j++) {
      if (paramBoolean) this.viewClasses.put(this.type[paramInt].item[j].clazz, this.type[paramInt].item[j]); else
        this.viewClasses.remove(this.type[paramInt].item[j].clazz);
    }
    viewUpdate();
  }
  void viewType(int paramInt) {
    viewType(paramInt, this.viewType[paramInt].jdField_bChecked_of_type_Boolean);
  }
  public void viewTypeAll(boolean paramBoolean) {
    for (int i = 0; i < this.type.length; i++)
      if (this.viewType[i].jdField_bChecked_of_type_Boolean != paramBoolean) {
        this.viewType[i].jdField_bChecked_of_type_Boolean = paramBoolean;
        viewType(i, paramBoolean);
      }
  }

  private void fillComboBox1()
  {
    this.startComboBox1 = Plugin.builder.wSelect.comboBox1.size();
    for (int i = 0; i < this.type.length; i++) {
      Plugin.builder.wSelect.comboBox1.add(I18N.technic(this.type[i].name));
    }
    if (this.startComboBox1 == 0)
      Plugin.builder.wSelect.comboBox1.setSelected(0, true, false); 
  }

  private void fillComboBox2(int paramInt1, int paramInt2) {
    if ((paramInt1 < this.startComboBox1) || (paramInt1 >= this.startComboBox1 + this.type.length)) {
      return;
    }
    if (Plugin.builder.wSelect.curFilledType != paramInt1) {
      Plugin.builder.wSelect.curFilledType = paramInt1;
      Plugin.builder.wSelect.comboBox2.clear(false);
      for (int i = 0; i < this.type[(paramInt1 - this.startComboBox1)].item.length; i++) {
        Plugin.builder.wSelect.comboBox2.add(Property.stringValue(this.type[(paramInt1 - this.startComboBox1)].item[i].clazz, "i18nName", ""));
      }
      Plugin.builder.wSelect.comboBox1.setSelected(paramInt1, true, false);
    }
    Plugin.builder.wSelect.comboBox2.setSelected(paramInt2, true, false);

    fillComboBox2Render(paramInt1, paramInt2);
  }

  private void fillComboBox2Render(int paramInt1, int paramInt2) {
    try {
      Class localClass = this.type[(paramInt1 - this.startComboBox1)].item[paramInt2].clazz;
      Object localObject;
      if (PlaneGeneric.class.isAssignableFrom(localClass)) {
        localObject = (Class)Property.value(localClass, "airClass", null);
        int i = this.type[(paramInt1 - this.startComboBox1)].item[paramInt2].army;
        String str1 = null;
        if (Actor.isValid(Plugin.builder.selectedActor())) {
          i = Plugin.builder.selectedActor().getArmy();
          str1 = ((PlaneGeneric)Plugin.builder.selectedActor()).country;
          this.type[(paramInt1 - this.startComboBox1)].item[paramInt2].country = str1;
          this.type[(paramInt1 - this.startComboBox1)].item[paramInt2].army = i;
        }
        Regiment localRegiment = Regiment.findFirst(str1, i);
        String str2 = Aircraft.getPropertyMesh((Class)localObject, localRegiment.country());

        Plugin.builder.wSelect.setMesh(str2, false);

        if (Plugin.builder.wSelect.getHierMesh() != null) {
          PaintScheme localPaintScheme = Aircraft.getPropertyPaintScheme((Class)localObject, localRegiment.country());
          localPaintScheme.prepareNum((Class)localObject, Plugin.builder.wSelect.getHierMesh(), localRegiment, (int)(Math.random() * 3.0D), (int)(Math.random() * 3.0D), (int)(Math.random() * 98.0D + 1.0D));
        }

      }
      else
      {
        localObject = Property.stringValue(localClass, "meshName", null);
        if (localObject == null) {
          Method localMethod = localClass.getMethod("getMeshNameForEditor", null);
          localObject = (String)localMethod.invoke(localClass, null);
        }
        Plugin.builder.wSelect.setMesh((String)localObject, true);
      }
    } catch (Exception localException) {
      Plugin.builder.wSelect.setMesh(null, true);
    }
  }

  public String[] actorInfo(Actor paramActor) {
    Class localClass = paramActor.getClass();
    for (int i = 0; i < this.type.length; i++) {
      for (int j = 0; j < this.type[i].item.length; j++) {
        if (localClass == this.type[i].item[j].clazz) {
          this._actorInfo[0] = (I18N.technic(this.type[i].name) + "." + Property.stringValue(this.type[i].item[j].clazz, "i18nName", ""));

          float f = Property.floatValue(paramActor, "timeout", 0.0F);
          if (f > 0.0F)
            this._actorInfo[1] = Plugin.timeSecToString(f * 60.0F + (int)(World.getTimeofDay() * 60.0F * 60.0F));
          else {
            this._actorInfo[1] = null;
          }
          return this._actorInfo;
        }
      }
    }
    return null;
  }

  public void syncSelector()
  {
    Actor localActor = Plugin.builder.selectedActor();
    Class localClass = localActor.getClass();
    for (int i = 0; i < this.type.length; i++)
      for (int j = 0; j < this.type[i].item.length; j++)
        if (localClass == this.type[i].item[j].clazz) {
          fillComboBox2(i + this.startComboBox1, j);
          Plugin.builder.wSelect.tabsClient.addTab(1, this.tabActor);
          this.wName.cap.set(Property.stringValue(this.type[i].item[j].clazz, "i18nName", ""));
          this.wArmy.setSelected(localActor.getArmy(), true, false);
          int m;
          if ((localActor instanceof ArtilleryGeneric)) {
            float f1 = Property.floatValue(localActor, "timeout", 0.0F);
            this.wTimeOutH.setValue("" + (int)(f1 / 60.0F % 24.0F), false);
            this.wTimeOutM.setValue("" + (int)(f1 % 60.0F), false);
            this.wLTimeOutH.showWindow();
            this.wTimeOutH.showWindow();
            this.wLTimeOutM.showWindow();
            this.wTimeOutM.showWindow();
            m = Property.intValue(localActor, "radius_hide", 0);
            this.wL1RHide.showWindow();
            this.wL2RHide.showWindow();
            this.wRHide.showWindow();
            this.wRHide.setValue("" + m, false);
          } else {
            this.wLTimeOutH.hideWindow();
            this.wTimeOutH.hideWindow();
            this.wLTimeOutM.hideWindow();
            this.wTimeOutM.hideWindow();
            this.wL1RHide.hideWindow();
            this.wL2RHide.hideWindow();
            this.wRHide.hideWindow();
          }
          if (PlaneGeneric.class.isAssignableFrom(localClass)) {
            PlaneGeneric localPlaneGeneric = (PlaneGeneric)localActor;
            fillCountry(localActor.getArmy(), localPlaneGeneric.country);
            this.wLCountry.showWindow();
            this.wCountry.showWindow();
          } else {
            this.wLCountry.hideWindow();
            this.wCountry.hideWindow();
          }
          if (((localActor instanceof ShipGeneric)) || ((localActor instanceof BigshipGeneric))) {
            this.wLSleepM.showWindow();
            this.wSleepM.showWindow();
            this.wLSleepS.showWindow();
            this.wSleepS.showWindow();
            int k = Property.intValue(localActor, "sleep", 0);
            this.wSleepM.setValue("" + k / 60 % 99, false);
            this.wSleepS.setValue("" + k % 60, false);
            this.wLSkill.showWindow();
            this.wSkill.showWindow();
            m = Property.intValue(localActor, "skill", 2);
            this.wSkill.setSelected(m, true, false);
            this.wLSlowfire.showWindow();
            this.wSlowfire.showWindow();
            float f2 = Property.floatValue(localActor, "slowfire", 1.0F);
            this.wSlowfire.setValue("" + f2);
          } else {
            this.wLSleepM.hideWindow();
            this.wSleepM.hideWindow();
            this.wLSleepS.hideWindow();
            this.wSleepS.hideWindow();
            this.wLSkill.hideWindow();
            this.wSkill.hideWindow();
            this.wLSlowfire.hideWindow();
            this.wSlowfire.hideWindow();
          }
          return;
        }
  }

  private String fillCountry(int paramInt, String paramString)
  {
    initCountry();
    this.wCountry.clear(false);
    ArrayList localArrayList = this.listCountry[paramInt];
    for (int i = 0; i < localArrayList.size(); i++) {
      localObject = (Country)localArrayList.get(i);
      this.wCountry.add(((Country)localObject).i18nName);
    }
    if ((paramString != null) && 
      (!this.mapCountry[paramInt].containsKey(paramString)))
      paramString = null;
    if (paramString == null) {
      switch (paramInt) { case 0:
        paramString = "nn"; break;
      case 1:
        paramString = "ru"; break;
      case 2:
        paramString = "de";
      }
    }
    Object localObject = (Integer)this.mapCountry[paramInt].get(paramString);
    this.wCountry.setSelected(((Integer)localObject).intValue(), true, false);
    return (String)paramString;
  }

  private void controlResized(GWindowDialogClient paramGWindowDialogClient, GWindow paramGWindow) {
    if (paramGWindow == null) return;
    paramGWindow.setSize(paramGWindowDialogClient.win.dx - paramGWindow.win.x - paramGWindowDialogClient.lAF().metric(1.0F), paramGWindow.win.dy);
  }
  private void editResized(GWindowDialogClient paramGWindowDialogClient) {
    controlResized(paramGWindowDialogClient, this.wName);
    controlResized(paramGWindowDialogClient, this.wArmy);
    controlResized(paramGWindowDialogClient, this.wCountry);
  }

  public void createGUI() {
    fillComboBox1();
    fillComboBox2(0, 0);
    Plugin.builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMisStatic.this.fillComboBox2(i, 0);
        return false;
      }
    });
    Plugin.builder.wSelect.comboBox2.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        if (paramInt1 != 2)
          return false;
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i < PlMisStatic.this.startComboBox1) || (i >= PlMisStatic.this.startComboBox1 + PlMisStatic.this.type.length))
          return false;
        int j = Plugin.builder.wSelect.comboBox2.getSelected();
        PlMisStatic.this.fillComboBox2Render(i, j);

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
      int j = i;
      i = this.type.length - 1;
      this.viewType = new ViewItem[this.type.length];
      while (i >= 0) {
        localObject = null;
        if ("de".equals(RTSConf.cur.locale.getLanguage())) {
          localObject = (ViewItem)Plugin.builder.mDisplayFilter.subMenu.addItem(j, new ViewItem(i, Plugin.builder.mDisplayFilter.subMenu, I18N.technic(this.type[i].name) + " " + Plugin.i18n("show"), null));
        }
        else {
          localObject = (ViewItem)Plugin.builder.mDisplayFilter.subMenu.addItem(j, new ViewItem(i, Plugin.builder.mDisplayFilter.subMenu, Plugin.i18n("show") + " " + I18N.technic(this.type[i].name), null));
        }
        ((ViewItem)localObject).jdField_bChecked_of_type_Boolean = true;
        this.viewType[i] = localObject;
        viewType(i, true);
        i--;
      }
    }

    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient() {
      public void resized() { super.resized(); PlMisStatic.this.editResized(this);
      }
    });
    this.tabActor = Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("StaticActor"), localGWindowDialogClient);

    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 7.0F, 1.3F, Plugin.i18n("Name"), null));
    localGWindowDialogClient.addLabel(this.wName = new GWindowLabel(localGWindowDialogClient, 9.0F, 1.0F, 7.0F, 1.3F, "", null));
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 7.0F, 1.3F, Plugin.i18n("Army"), null));
    localGWindowDialogClient.addControl(this.wArmy = new GWindowComboControl(localGWindowDialogClient, 9.0F, 3.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        for (int i = 0; i < Builder.armyAmount(); i++)
          add(I18N.army(Army.name(i))); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        Actor localActor = Plugin.builder.selectedActor();
        int i = getSelected();
        localActor.setArmy(i);
        PlMission.setChanged();
        PlMission.checkShowCurrentArmy();

        Class localClass = localActor.getClass();
        if (PlaneGeneric.class.isAssignableFrom(localClass)) {
          PlaneGeneric localPlaneGeneric = (PlaneGeneric)localActor;
          String str = PlMisStatic.this.fillCountry(i, localPlaneGeneric.country);
          localPlaneGeneric.country = str;
          localPlaneGeneric.activateMesh(true);
          for (int j = 0; j < PlMisStatic.this.type.length; j++) {
            for (int k = 0; k < PlMisStatic.this.type[j].item.length; k++) {
              if (localClass == PlMisStatic.this.type[j].item[k].clazz) {
                PlMisStatic.this.fillComboBox2Render(j + PlMisStatic.this.startComboBox1, k);
                break;
              }
            }
          }
        }
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLCountry = new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 7.0F, 1.3F, I18N.gui("neta.Country"), null));
    localGWindowDialogClient.addControl(this.wCountry = new GWindowComboControl(localGWindowDialogClient, 9.0F, 5.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        Actor localActor = Plugin.builder.selectedActor();
        Class localClass = localActor.getClass();
        if (!PlaneGeneric.class.isAssignableFrom(localClass))
          return false;
        int i = getSelected();
        PlMisStatic.Country localCountry = (PlMisStatic.Country)PlMisStatic.this.listCountry[localActor.getArmy()].get(i);
        PlaneGeneric localPlaneGeneric = (PlaneGeneric)localActor;
        localPlaneGeneric.country = localCountry.name;
        localPlaneGeneric.activateMesh(true);
        for (int j = 0; j < PlMisStatic.this.type.length; j++) {
          for (int k = 0; k < PlMisStatic.this.type[j].item.length; k++) {
            if (localClass == PlMisStatic.this.type[j].item[k].clazz) {
              PlMisStatic.this.fillComboBox2Render(j + PlMisStatic.this.startComboBox1, k);
              break;
            }
          }
        }
        return false;
      }
    });
    Object localObject = localGWindowDialogClient;
    ((GWindowDialogClient)localObject).addLabel(this.wLTimeOutH = new GWindowLabel((GWindow)localObject, 1.0F, 5.0F, 7.0F, 1.3F, Plugin.i18n("TimeOut"), null));
    ((GWindowDialogClient)localObject).addControl(this.wTimeOutH = new GWindowEditControl((GWindow)localObject, 9.0F, 5.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisStatic.this.getTimeOut();
        return false;
      }
    });
    ((GWindowDialogClient)localObject).addLabel(this.wLTimeOutM = new GWindowLabel((GWindow)localObject, 11.2F, 5.0F, 1.0F, 1.3F, ":", null));
    ((GWindowDialogClient)localObject).addControl(this.wTimeOutM = new GWindowEditControl((GWindow)localObject, 11.5F, 5.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisStatic.this.getTimeOut();
        return false;
      }
    });
    ((GWindowDialogClient)localObject).addLabel(this.wL1RHide = new GWindowLabel((GWindow)localObject, 1.0F, 7.0F, 7.0F, 1.3F, Plugin.i18n("RHide"), null));
    ((GWindowDialogClient)localObject).addLabel(this.wL2RHide = new GWindowLabel((GWindow)localObject, 14.0F, 7.0F, 4.0F, 1.3F, Plugin.i18n("[M]"), null));
    ((GWindowDialogClient)localObject).addControl(this.wRHide = new GWindowEditControl((GWindow)localObject, 9.0F, 7.0F, 4.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        Actor localActor = Plugin.builder.selectedActor();
        int i = Property.intValue(localActor, "radius_hide", 0);
        String str = getValue();
        try {
          i = (int)Double.parseDouble(str);
          if (i < 0) {
            i = 0;
            setValue("" + i, false);
          }
        } catch (Exception localException) {
          setValue("" + i, false);
          return false;
        }
        Property.set(localActor, "radius_hide", i);
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLSleepM = new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 7.0F, 1.3F, Plugin.i18n("Sleep"), null));
    localGWindowDialogClient.addControl(this.wSleepM = new GWindowEditControl(localGWindowDialogClient, 9.0F, 5.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisStatic.this.getSleep();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLSleepS = new GWindowLabel(localGWindowDialogClient, 11.2F, 5.0F, 1.0F, 1.3F, ":", null));
    localGWindowDialogClient.addControl(this.wSleepS = new GWindowEditControl(localGWindowDialogClient, 11.5F, 5.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisStatic.this.getSleep();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLSkill = new GWindowLabel(localGWindowDialogClient, 1.0F, 7.0F, 7.0F, 1.3F, Plugin.i18n("Skill"), null));
    localGWindowDialogClient.addControl(this.wSkill = new GWindowComboControl(localGWindowDialogClient, 9.0F, 7.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        add(Plugin.i18n("Rookie"));
        add(Plugin.i18n("Average"));
        add(Plugin.i18n("Veteran"));
        add(Plugin.i18n("Ace")); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        Actor localActor = Plugin.builder.selectedActor();
        Property.set(localActor, "skill", getSelected());
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLSlowfire = new GWindowLabel(localGWindowDialogClient, 1.0F, 9.0F, 7.0F, 1.3F, Plugin.i18n("Slowfire"), null));
    localGWindowDialogClient.addControl(this.wSlowfire = new GWindowEditControl(localGWindowDialogClient, 9.0F, 9.0F, 3.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = (this.bNumericFloat = 1);
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        String str = getValue();
        float f = 1.0F;
        try {
          f = Float.parseFloat(str); } catch (Exception localException) {
        }
        if (f < 0.5F) f = 0.5F;
        if (f > 100.0F) f = 100.0F;
        setValue("" + f, false);
        Actor localActor = Plugin.builder.selectedActor();
        Property.set(localActor, "slowfire", f);
        PlMission.setChanged();
        return false;
      } } );
  }

  private void getTimeOut() {
    String str = this.wTimeOutH.getValue();
    double d1 = 0.0D;
    try { d1 = Double.parseDouble(str); } catch (Exception localException1) {
    }
    if (d1 < 0.0D) d1 = 0.0D;
    if (d1 > 12.0D) d1 = 12.0D;
    str = this.wTimeOutM.getValue();
    double d2 = 0.0D;
    try { d2 = Double.parseDouble(str); } catch (Exception localException2) {
    }
    if (d2 < 0.0D) d2 = 0.0D;
    if (d2 > 60.0D) d2 = 60.0D;
    float f = (float)(d1 * 60.0D + d2);
    Actor localActor = Plugin.builder.selectedActor();
    Property.set(localActor, "timeout", f);
    PlMission.setChanged();
  }
  private void getSleep() {
    String str = this.wSleepM.getValue();
    double d1 = 0.0D;
    try { d1 = Double.parseDouble(str); } catch (Exception localException1) {
    }
    if (d1 < 0.0D) d1 = 0.0D;
    if (d1 > 99.0D) d1 = 99.0D;
    str = this.wSleepS.getValue();
    double d2 = 0.0D;
    try { d2 = Double.parseDouble(str); } catch (Exception localException2) {
    }
    if (d2 < 0.0D) d2 = 0.0D;
    if (d2 > 60.0D) d2 = 60.0D;
    Actor localActor = Plugin.builder.selectedActor();
    Property.set(localActor, "sleep", (int)(d1 * 60.0D + d2));
    PlMission.setChanged();
  }

  static
  {
    Property.set(PlMisStatic.class, "name", "MisStatic");
  }

  class ViewItem extends GWindowMenuItem
  {
    int indx;

    public void execute()
    {
      this.jdField_bChecked_of_type_Boolean = (!this.jdField_bChecked_of_type_Boolean);
      PlMisStatic.this.viewType(this.indx);
    }
    public ViewItem(int paramGWindowMenu, GWindowMenu paramString1, String paramString2, String arg5) {
      super(paramString2, str);
      this.indx = paramGWindowMenu;
    }
  }

  static class Type
  {
    public String name;
    public PlMisStatic.Item[] item;

    public Type(String paramString, PlMisStatic.Item[] paramArrayOfItem)
    {
      this.name = paramString; this.item = paramArrayOfItem;
    }
  }

  static class Item
  {
    public String name;
    public Class clazz;
    public int army;
    public ActorSpawn spawn;
    public String country;

    public Item(String paramString, Class paramClass, int paramInt)
    {
      this.name = paramString; this.clazz = paramClass; this.army = paramInt;
      if (paramClass != null) {
        this.spawn = ((ActorSpawn)Spawn.get(paramClass.getName()));
        String str1 = I18N.technic(paramString);
        if (str1.equals(paramString)) {
          str1 = Property.stringValue(paramClass, "i18nName", str1);
          if ("Plane".equals(str1)) {
            Class localClass = (Class)Property.value(paramClass, "airClass", null);
            if (localClass != null) {
              String str2 = Property.stringValue(localClass, "keyName", null);
              if (str2 != null)
                str1 = I18N.plane(str2);
            }
          }
        }
        Property.set(paramClass, "i18nName", str1);
      }
    }
  }

  static class Country
  {
    public String name;
    public String i18nName;
  }
}