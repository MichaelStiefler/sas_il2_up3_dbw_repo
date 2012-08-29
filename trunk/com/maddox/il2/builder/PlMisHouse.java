package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient.Tab;
import com.maddox.il2.ai.Army;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.objects.buildings.House.Properties;
import com.maddox.il2.objects.buildings.House.SPAWN;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Spawn;
import com.maddox.util.NumberTokenizer;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class PlMisHouse extends Plugin
{
  protected ArrayList allActors = new ArrayList();
  private HashMap allTypes = new HashMap();
  private Mat houseIcon = null;
  Type[] type;
  private Point2d p2d = new Point2d();

  private Point3d p = new Point3d();
  private Orient o = new Orient();
  private ActorSpawnArg spawnArg = new ActorSpawnArg();
  private PlMission pluginMission;
  private int startComboBox1;
  private boolean bView = true;
  private ViewType viewCheckBox;
  HashMap viewClasses = new HashMap();

  private String[] _actorInfo = new String[1];
  GWindowTabDialogClient.Tab tabActor;
  GWindowLabel wName;
  GWindowComboControl wArmy;

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
    if (builder.isFreeView()) return;
    if (!this.bView) return;
    Actor localActor1 = builder.selectedActor();

    Render.prepareStates();

    for (int j = 0; j < this.allActors.size(); j++) {
      Actor localActor2 = (Actor)this.allActors.get(j);
      if ((!Actor.isValid(localActor2)) || (localActor2.icon == null) || 
        (!builder.project2d(localActor2.pos.getAbsPoint(), this.p2d))) continue;
      int k = localActor2.getArmy();
      int m = builder.conf.bShowArmy[k];
      if (m != 0)
      {
        int i;
        if (builder.isMiltiSelected(localActor2)) i = Builder.colorMultiSelected(Army.color(localActor2.getArmy()));
        else if (localActor2 == localActor1) i = Builder.colorSelected(); else {
          i = Army.color(localActor2.getArmy());
        }
        IconDraw.setColor(i);
        IconDraw.render(localActor2, this.p2d.x, this.p2d.y);
      }
    }
  }

  public void load(SectFile paramSectFile)
  {
    int i = paramSectFile.sectionIndex("Buildings");
    if (i >= 0) {
      int j = paramSectFile.vars(i);
      for (int k = 0; k < j; k++) {
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
        localNumberTokenizer.next("");
        insert("com.maddox.il2.objects.buildings." + localNumberTokenizer.next(""), localNumberTokenizer.next(1) == 1, localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0F), false);
      }
    }
  }

  public boolean save(SectFile paramSectFile) {
    Orient localOrient1 = new Orient();
    int i = paramSectFile.sectionAdd("Buildings");
    for (int j = 0; j < this.allActors.size(); j++) {
      Actor localActor = (Actor)this.allActors.get(j);
      if (Actor.isValid(localActor)) {
        Point3d localPoint3d = localActor.pos.getAbsPoint();
        Orient localOrient2 = localActor.pos.getAbsOrient();
        localOrient1.set(localOrient2);
        localOrient1.wrap360();
        Type localType = (Type)Property.value(localActor, "builderType", null);
        paramSectFile.lineAdd(i, j + "_bld", localType.shortClassName + " " + (localActor.isAlive() ? "1 " : "0 ") + formatPos(localPoint3d.x, localPoint3d.y, localOrient1.azimut()));
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

  private Actor insert(String paramString, boolean paramBoolean1, double paramDouble1, double paramDouble2, float paramFloat, boolean paramBoolean2) {
    ActorSpawn localActorSpawn = (ActorSpawn)Spawn.get_WithSoftClass(paramString, false);
    if (localActorSpawn == null) {
      builder.tipErr("PlMisHouse: ActorSpawn for '" + paramString + "' not found");
      return null;
    }
    this.spawnArg.clear();
    this.p.set(paramDouble1, paramDouble2, 0.0D);
    this.spawnArg.point = this.p;
    this.o.set(paramFloat, 0.0F, 0.0F);
    this.spawnArg.orient = this.o;
    try {
      Actor localActor = localActorSpawn.actorSpawn(this.spawnArg);
      if (!paramBoolean1) localActor.setDiedFlag(true);
      Property.set(localActor, "builderSpawn", "");
      Property.set(localActor, "builderPlugin", this);
      Type localType = (Type)this.allTypes.get(paramString);
      Property.set(localActor, "builderType", localType);
      localActor.icon = this.houseIcon;
      this.allActors.add(localActor);
      builder.align(localActor);
      if (paramBoolean2)
        builder.setSelected(localActor);
      PlMission.setChanged();
      return localActor;
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    return null;
  }

  private Actor insert(Type paramType, Loc paramLoc, boolean paramBoolean)
  {
    this.spawnArg.clear();
    this.spawnArg.point = paramLoc.getPoint();
    this.spawnArg.orient = paramLoc.getOrient();
    return insert(paramType.fullClassName, true, paramLoc.getPoint().x, paramLoc.getPoint().y, paramLoc.getOrient().getAzimut(), paramBoolean);
  }

  public void insert(Loc paramLoc, boolean paramBoolean) {
    int i = builder.wSelect.comboBox1.getSelected();
    int j = builder.wSelect.comboBox2.getSelected();
    if (i != this.startComboBox1)
      return;
    if ((j < 0) || (j >= this.type.length))
      return;
    insert(this.type[j], paramLoc, paramBoolean);
  }

  public void changeType() {
    int i = builder.wSelect.comboBox1.getSelected() - this.startComboBox1;
    int j = builder.wSelect.comboBox2.getSelected();
    Actor localActor1 = builder.selectedActor();
    Loc localLoc = localActor1.pos.getAbs();
    Actor localActor2 = insert(this.type[j], localLoc, true);
    if (builder.selectedActor() != localActor1) {
      this.allActors.remove(localActor1);
      localActor1.destroy();
      PlMission.setChanged();
    }
  }

  public void changeType(boolean paramBoolean1, boolean paramBoolean2) {
    if (paramBoolean2) return;
    if (builder.wSelect.comboBox1.getSelected() != this.startComboBox1) return;
    int i = builder.wSelect.comboBox2.getSelected();
    if (paramBoolean1) {
      i++;
      if (i >= this.type.length)
        i = 0;
    } else {
      i--;
      if (i < 0)
        i = this.type.length - 1;
    }
    Actor localActor1 = builder.selectedActor();
    Loc localLoc = localActor1.pos.getAbs();
    Actor localActor2 = insert(this.type[i], localLoc, true);
    if (builder.selectedActor() != localActor1) {
      this.allActors.remove(localActor1);
      localActor1.destroy();
      PlMission.setChanged();
    }
    fillComboBox2(this.startComboBox1, i);
  }

  public void configure() {
    if (getPlugin("Mission") == null)
      throw new RuntimeException("PlMisHouse: plugin 'Mission' not found");
    this.pluginMission = ((PlMission)getPlugin("Mission"));
    if (this.sectFile == null)
      throw new RuntimeException("PlMisHouse: field 'sectFile' not defined");
    SectFile localSectFile = new SectFile(this.sectFile, 0);
    int i = localSectFile.sections();
    if (i <= 0)
      throw new RuntimeException("PlMisHouse: file '" + this.sectFile + "' is empty");
    Type[] arrayOfType = new Type[i];
    int j = 0;
    for (int k = 0; k < i; k++) {
      String str1 = localSectFile.sectionName(k);
      if (str1.indexOf("House$") < 0)
        continue;
      String str2 = str1;
      String str3 = "";
      int m = str1.lastIndexOf('$');
      if (m >= 0) {
        str2 = str1.substring(0, m);
        str3 = str1.substring(m + 1);
      }
      Class localClass = null;
      try {
        localClass = ObjIO.classForName(str2);
      } catch (Exception localException) {
        throw new RuntimeException("PlMisHouse: class '" + str2 + "' not found");
      }
      if (m >= 0)
        str1 = localClass.getName() + "$" + str3;
      else {
        str1 = localClass.getName();
      }
      arrayOfType[j] = new Type(j, i18n("building") + " " + j, str1);
      this.allTypes.put(str1, arrayOfType[j]);
      j++;
    }
    this.type = new Type[j];
    for (k = 0; k < j; k++) {
      this.type[k] = arrayOfType[k];
      arrayOfType[k] = null;
    }
    arrayOfType = null;
    this.houseIcon = IconDraw.get("icons/objHouse.mat");
  }

  void viewUpdate()
  {
    for (int i = 0; i < this.allActors.size(); i++) {
      Actor localActor = (Actor)this.allActors.get(i);
      if (Actor.isValid(localActor))
        localActor.drawing(this.bView);
    }
    if ((Actor.isValid(builder.selectedActor())) && (!builder.selectedActor().isDrawing()))
      builder.setSelected(null);
    if (!builder.isFreeView())
      builder.repaint(); 
  }

  public void viewTypeAll(boolean paramBoolean) {
    this.bView = paramBoolean;
    this.viewCheckBox.bChecked = this.bView;
    viewUpdate();
  }

  private void fillComboBox1()
  {
    this.startComboBox1 = builder.wSelect.comboBox1.size();
    builder.wSelect.comboBox1.add(i18n("buildings"));
    if (this.startComboBox1 == 0)
      builder.wSelect.comboBox1.setSelected(0, true, false); 
  }

  private void fillComboBox2(int paramInt1, int paramInt2) {
    if (paramInt1 != this.startComboBox1) {
      return;
    }
    if (builder.wSelect.curFilledType != paramInt1) {
      builder.wSelect.curFilledType = paramInt1;
      builder.wSelect.comboBox2.clear(false);
      for (int i = 0; i < this.type.length; i++)
        builder.wSelect.comboBox2.add(this.type[i].name);
      builder.wSelect.comboBox1.setSelected(paramInt1, true, false);
    }
    builder.wSelect.comboBox2.setSelected(paramInt2, true, false);

    fillComboBox2Render(paramInt1, paramInt2);
  }

  private void fillComboBox2Render(int paramInt1, int paramInt2) {
    try {
      Type localType = this.type[paramInt2];
      House.SPAWN localSPAWN = (House.SPAWN)localType.spawn;
      builder.wSelect.setMesh(localSPAWN.prop.MESH0_NAME, true);
    } catch (Exception localException) {
      builder.wSelect.setMesh(null, true);
    }
  }

  public String[] actorInfo(Actor paramActor) {
    Type localType = (Type)Property.value(paramActor, "builderType", null);
    if (localType != null) {
      this._actorInfo[0] = localType.name;
      return this._actorInfo;
    }
    return null;
  }

  public void syncSelector()
  {
    Actor localActor = builder.selectedActor();
    Type localType = (Type)Property.value(localActor, "builderType", null);
    if (localType == null) return;
    fillComboBox2(this.startComboBox1, localType.indx);
  }

  public void createGUI() {
    fillComboBox1();
    fillComboBox2(0, 0);
    builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMisHouse.this.fillComboBox2(i, 0);
        return false;
      }
    });
    builder.wSelect.comboBox2.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        if (paramInt1 != 2)
          return false;
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if (i != PlMisHouse.this.startComboBox1)
          return false;
        int j = Plugin.builder.wSelect.comboBox2.getSelected();
        PlMisHouse.this.fillComboBox2Render(i, j);
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
      int j = i;
      if ("de".equals(RTSConf.cur.locale.getLanguage())) {
        this.viewCheckBox = ((ViewType)builder.mDisplayFilter.subMenu.addItem(i, new ViewType(builder.mDisplayFilter.subMenu, i18n("buildings") + " " + i18n("show"), null)));
      }
      else {
        this.viewCheckBox = ((ViewType)builder.mDisplayFilter.subMenu.addItem(i, new ViewType(builder.mDisplayFilter.subMenu, i18n("show") + " " + i18n("buildings"), null)));
      }
      viewTypeAll(true);
    }
  }

  public String mis_getProperties(Actor paramActor) {
    String str = "";
    int i = builder.wSelect.comboBox1.getSelected();
    int j = builder.wSelect.comboBox2.getSelected();
    if (i != this.startComboBox1)
      return str;
    if ((j < 0) || (j >= this.type.length)) {
      return str;
    }
    Orient localOrient1 = new Orient();
    Point3d localPoint3d = paramActor.pos.getAbsPoint();
    Orient localOrient2 = paramActor.pos.getAbsOrient();
    localOrient1.set(localOrient2);
    localOrient1.wrap360();
    Type localType = (Type)Property.value(paramActor, "builderType", null);
    str = "1_bld " + localType.shortClassName + " " + (paramActor.isAlive() ? "1 " : "0 ") + formatPos(localPoint3d.x, localPoint3d.y, localOrient1.azimut());
    return str;
  }

  public Actor mis_insert(Loc paramLoc, String paramString) {
    int i = builder.wSelect.comboBox1.getSelected();
    int j = builder.wSelect.comboBox2.getSelected();
    if (i != this.startComboBox1)
      return null;
    if ((j < 0) || (j >= this.type.length)) {
      return null;
    }
    NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramString);
    localNumberTokenizer.next("");
    String str = localNumberTokenizer.next("");
    i = localNumberTokenizer.next(1);
    double d = localNumberTokenizer.next(0.0D);
    d = localNumberTokenizer.next(0.0D);

    Actor localActor = insert("com.maddox.il2.objects.buildings." + str, i == 1, paramLoc.getPoint().x, paramLoc.getPoint().y, localNumberTokenizer.next(0.0F), false);
    return localActor;
  }

  public boolean mis_validateSelected(int paramInt1, int paramInt2) {
    if (paramInt1 != this.startComboBox1) {
      return false;
    }
    return (paramInt2 >= 0) && (paramInt2 < this.type.length);
  }

  static
  {
    Property.set(PlMisHouse.class, "name", "MisHouse");
  }

  class ViewType extends GWindowMenuItem
  {
    public void execute()
    {
      this.bChecked = (!this.bChecked);
      PlMisHouse.this.viewTypeAll(this.bChecked);
    }
    public ViewType(GWindowMenu paramString1, String paramString2, String arg4) {
      super(paramString2, str);
    }
  }

  static class Type
  {
    public int indx;
    public String name;
    public ActorSpawn spawn;
    public String fullClassName;
    public String shortClassName;

    public Type(int paramInt, String paramString1, String paramString2)
    {
      this.indx = paramInt;
      this.name = paramString1;
      this.fullClassName = paramString2;
      this.shortClassName = this.fullClassName.substring("com.maddox.il2.objects.buildings.".length());
      this.spawn = ((ActorSpawn)Spawn.get_WithSoftClass(this.fullClassName));
    }
  }
}