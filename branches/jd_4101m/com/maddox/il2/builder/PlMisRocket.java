package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.gwindow.GWindowTabDialogClient.Tab;
import com.maddox.il2.ai.Army;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.I18N;
import com.maddox.il2.objects.ActorSimpleHMesh;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;
import java.util.HashMap;

public class PlMisRocket extends Plugin
{
  ArrayList allActors = new ArrayList();
  Item[] item;
  HashMap itemMap = new HashMap();

  private float[] lineNXYZ = new float[6];
  private Point2d p2d = new Point2d();
  private Point3d p3d = new Point3d();
  private Point2d p2d2 = new Point2d();
  private Mat targetMat;
  private PlMission pluginMission;
  private int startComboBox1;
  private GWindowMenuItem viewType;
  private String[] _actorInfo = new String[1];
  GWindowTabDialogClient.Tab tabTarget;
  GWindowLabel wName;
  GWindowComboControl wArmy;
  GWindowLabel wLTimeOutH;
  GWindowEditControl wTimeOutH;
  GWindowLabel wLTimeOutM;
  GWindowEditControl wTimeOutM;
  GWindowLabel wLCount;
  GWindowEditControl wCount;
  GWindowLabel wLPeriodH;
  GWindowEditControl wPeriodH;
  GWindowLabel wLPeriodM;
  GWindowEditControl wPeriodM;
  GWindowLabel wTarget;
  GWindowButton wTargetSet;
  GWindowButton wTargetClear;

  public void renderMap2DAfter()
  {
    if (builder.isFreeView()) return;
    if (!this.viewType.bChecked) return;
    Actor localActor = builder.selectedActor();
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      Rocket localRocket = (Rocket)this.allActors.get(j);

      boolean bool1 = builder.project2d(localRocket.pos.getAbsPoint(), this.p2d);
      if (localRocket.target != null) {
        this.p3d.x = localRocket.target.x;
        this.p3d.y = localRocket.target.y;
        this.p3d.z = Engine.land().HQ(this.p3d.x, this.p3d.y);
        boolean bool2 = builder.project2d(this.p3d, this.p2d2);
        if ((bool1) || (bool2)) {
          this.lineNXYZ[0] = (float)this.p2d.x;
          this.lineNXYZ[1] = (float)this.p2d.y;
          this.lineNXYZ[2] = 0.0F;
          this.lineNXYZ[3] = (float)this.p2d2.x;
          this.lineNXYZ[4] = (float)this.p2d2.y;
          this.lineNXYZ[5] = 0.0F;
          Render.drawBeginLines(-1);
          Render.drawLines(this.lineNXYZ, 2, 3.0F, -16711936, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE | Mat.BLEND, 3);

          Render.drawEnd();
        }
        if (bool2) {
          float f = builder.conf.iconSize;
          Render.drawTile((float)(this.p2d2.x - f / 2.0F), (float)(this.p2d2.y - f / 2.0F), f, f, 0.0F, this.targetMat, -1, 0.0F, 1.0F, 1.0F, -1.0F);
        }
      }
      if (bool1) {
        int k = Army.color(localRocket.getArmy());
        if (builder.isMiltiSelected(localRocket)) k = Builder.colorMultiSelected(k);
        else if (localRocket == localActor) k = Builder.colorSelected();
        IconDraw.setColor(k);
        IconDraw.render(localRocket, this.p2d.x, this.p2d.y);
      }
    }
  }

  public boolean save(SectFile paramSectFile)
  {
    int i = this.allActors.size();
    if (i == 0) return true;
    Orient localOrient1 = new Orient();
    int j = paramSectFile.sectionAdd("Rocket");
    for (int k = 0; k < i; k++) {
      Rocket localRocket = (Rocket)this.allActors.get(k);
      Point3d localPoint3d = localRocket.pos.getAbsPoint();
      Orient localOrient2 = localRocket.pos.getAbsOrient();
      localOrient1.set(localOrient2);
      localOrient1.wrap360();
      paramSectFile.lineAdd(j, "" + localRocket.name() + " " + localRocket.item.name + " " + localRocket.getArmy() + " " + formatPos(localPoint3d.x, localPoint3d.y, localOrient1.azimut()) + " " + localRocket.timeout + " " + localRocket.count + " " + localRocket.period + " " + (localRocket.target != null ? formatValue(localRocket.target.x) + " " + formatValue(localRocket.target.y) : ""));
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

  public void load(SectFile paramSectFile) {
    int i = paramSectFile.sectionIndex("Rocket");
    if (i >= 0) {
      Orient localOrient = new Orient();
      int j = paramSectFile.vars(i);
      Point3d localPoint3d = new Point3d();
      for (int k = 0; k < j; k++) {
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k)); if (localNumberTokenizer.hasMoreTokens()) {
          String str1 = localNumberTokenizer.next(""); if (localNumberTokenizer.hasMoreTokens()) {
            String str2 = localNumberTokenizer.next(""); if (localNumberTokenizer.hasMoreTokens()) {
              Item localItem = (Item)this.itemMap.get(str2); if (localItem != null) {
                int m = localNumberTokenizer.next(1, 1, 2);
                localPoint3d.x = localNumberTokenizer.next(0.0D); if (localNumberTokenizer.hasMoreTokens()) {
                  localPoint3d.y = localNumberTokenizer.next(0.0D); if (localNumberTokenizer.hasMoreTokens()) {
                    Rocket localRocket = insert(localItem, str1, localPoint3d, false); if (localRocket != null) {
                      localRocket.setArmy(m);
                      localOrient.set(localNumberTokenizer.next(0.0F), 0.0F, 0.0F);
                      localRocket.pos.setAbs(localOrient); localRocket.pos.reset();
                      localRocket.timeout = localNumberTokenizer.next(localRocket.timeout);
                      localRocket.count = localNumberTokenizer.next(localRocket.count);
                      localRocket.period = localNumberTokenizer.next(localRocket.period); if (localNumberTokenizer.hasMoreTokens())
                        localRocket.target = new Point2d(localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0D)); 
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public void deleteAll() {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      Rocket localRocket = (Rocket)this.allActors.get(j);
      localRocket.destroy();
    }
    this.allActors.clear();
  }

  public void delete(Actor paramActor) {
    this.allActors.remove(paramActor);
    paramActor.destroy();
  }

  private void makeName(Actor paramActor, String paramString) {
    if ((paramString != null) && (Actor.getByName(paramString) == null)) {
      paramActor.setName(paramString);
      return;
    }
    int i = 0;
    while (true) {
      paramString = i + "_Rocket";
      if (Actor.getByName(paramString) == null)
        break;
      i++;
    }
    paramActor.setName(paramString);
  }

  private Rocket insert(Item paramItem, String paramString, Point3d paramPoint3d, boolean paramBoolean) {
    try {
      Rocket localRocket = new Rocket(paramItem);
      localRocket.pos.setAbs(paramPoint3d);
      localRocket.pos.reset();
      Property.set(localRocket, "builderSpawn", "");
      Property.set(localRocket, "builderPlugin", this);
      makeName(localRocket, paramString);
      this.allActors.add(localRocket);
      if (paramBoolean)
        builder.setSelected(localRocket);
      PlMission.setChanged();
      return localRocket; } catch (Exception localException) {
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
    insert(this.item[j], null, paramLoc.getPoint(), paramBoolean);
  }

  public void changeType() {
    builder.setSelected(null);
  }

  private void updateView() {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      Rocket localRocket = (Rocket)this.allActors.get(j);
      localRocket.drawing(this.viewType.bChecked);
    }
  }

  public void configure()
  {
    if (getPlugin("Mission") == null)
      throw new RuntimeException("PlMisRocket: plugin 'Mission' not found");
    this.pluginMission = ((PlMission)getPlugin("Mission"));
    if (this.sectFile == null)
      throw new RuntimeException("PlMisRocket: field 'sectFile' not defined");
    SectFile localSectFile = new SectFile(this.sectFile, 0);
    int i = localSectFile.sections();
    if (i <= 0)
      throw new RuntimeException("PlMisRocket: file '" + this.sectFile + "' is empty");
    this.item = new Item[i];
    for (int j = 0; j < i; j++) {
      Item localItem = new Item();
      localItem.indx = j;
      localItem.name = localSectFile.sectionName(j);
      localItem.i18nName = I18N.technic(localItem.name);
      localItem.meshName = localSectFile.get(localItem.name, "MeshEditor");
      localItem.iconName = localSectFile.get(localItem.name, "IconEditor");
      localItem.army = localSectFile.get(localItem.name, "DefaultArmy", 1, 1, 2);
      this.item[j] = localItem;
      this.itemMap.put(localItem.name, localItem);
    }
    Property.set(Rocket.class, "i18nName", i18n("Rocket"));
    this.targetMat = Mat.New("icons/objV1target.mat");
  }

  private void fillComboBox2(int paramInt1, int paramInt2) {
    if (paramInt1 != this.startComboBox1)
      return;
    if (builder.wSelect.curFilledType != paramInt1) {
      builder.wSelect.curFilledType = paramInt1;
      builder.wSelect.comboBox2.clear(false);
      for (int i = 0; i < this.item.length; i++)
        builder.wSelect.comboBox2.add(this.item[i].i18nName);
      builder.wSelect.comboBox1.setSelected(paramInt1, true, false);
    }
    builder.wSelect.comboBox2.setSelected(paramInt2, true, false);

    fillComboBox2Render(paramInt1, paramInt2);
  }

  private void fillComboBox2Render(int paramInt1, int paramInt2) {
    try {
      String str = this.item[paramInt2].meshName;
      builder.wSelect.setMesh(str, true);
    } catch (Exception localException) {
      builder.wSelect.setMesh(null, true);
    }
  }

  public void viewTypeAll(boolean paramBoolean) {
    this.viewType.bChecked = paramBoolean;
    updateView();
  }

  public String[] actorInfo(Actor paramActor) {
    Rocket localRocket = (Rocket)paramActor;
    this._actorInfo[0] = localRocket.item.i18nName;
    return this._actorInfo;
  }

  public void syncSelector()
  {
    Rocket localRocket = (Rocket)builder.selectedActor();
    fillComboBox2(this.startComboBox1, localRocket.item.indx);
    builder.wSelect.tabsClient.addTab(1, this.tabTarget);
    this.wName.cap.set(localRocket.item.i18nName);
    this.wArmy.setSelected(localRocket.getArmy() - 1, true, false);
    this.wTimeOutH.setValue("" + (int)(localRocket.timeout / 60.0F % 24.0F), false);
    this.wTimeOutM.setValue("" + (int)(localRocket.timeout % 60.0F), false);
    this.wCount.setValue("" + localRocket.count, false);
    this.wPeriodH.setValue("" + (int)(localRocket.period / 60.0F % 24.0F), false);
    this.wPeriodM.setValue("" + (int)(localRocket.period % 60.0F), false);
  }

  public void createGUI() {
    this.startComboBox1 = builder.wSelect.comboBox1.size();
    builder.wSelect.comboBox1.add(i18n("Rocket"));
    builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMisRocket.this.fillComboBox2(i, 0);
        return false;
      }
    });
    builder.wSelect.comboBox2.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        if (paramInt1 != 2)
          return false;
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if (i != PlMisRocket.this.startComboBox1)
          return false;
        int j = Plugin.builder.wSelect.comboBox2.getSelected();
        PlMisRocket.this.fillComboBox2Render(i, j);
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
      this.viewType = builder.mDisplayFilter.subMenu.addItem(i, new GWindowMenuItem(builder.mDisplayFilter.subMenu, i18n("showRocket"), null)
      {
        public void execute() {
          this.bChecked = (!this.bChecked);
          PlMisRocket.this.updateView();
        }
      });
      this.viewType.bChecked = true;
    }

    GWindowDialogClient localGWindowDialogClient1 = (GWindowDialogClient)builder.wSelect.tabsClient.create(new GWindowDialogClient());
    this.tabTarget = builder.wSelect.tabsClient.createTab(i18n("Rocket"), localGWindowDialogClient1);

    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 1.0F, 1.0F, 7.0F, 1.3F, i18n("Name"), null));
    localGWindowDialogClient1.addLabel(this.wName = new GWindowLabel(localGWindowDialogClient1, 9.0F, 1.0F, 7.0F, 1.3F, "", null));
    localGWindowDialogClient1.addLabel(new GWindowLabel(localGWindowDialogClient1, 1.0F, 3.0F, 7.0F, 1.3F, i18n("Army"), null));
    localGWindowDialogClient1.addControl(this.wArmy = new GWindowComboControl(localGWindowDialogClient1, 9.0F, 3.0F, 7.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        for (int i = 1; i < Builder.armyAmount(); i++)
          add(I18N.army(Army.name(i))); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        Actor localActor = Plugin.builder.selectedActor();
        int i = getSelected() + 1;
        localActor.setArmy(i);
        PlMission.setChanged();
        PlMission.checkShowCurrentArmy();
        return false;
      }
    });
    GWindowDialogClient localGWindowDialogClient2 = localGWindowDialogClient1;
    localGWindowDialogClient2.addLabel(this.wLTimeOutH = new GWindowLabel(localGWindowDialogClient2, 1.0F, 5.0F, 7.0F, 1.3F, i18n("TimeOut"), null));
    localGWindowDialogClient2.addControl(this.wTimeOutH = new GWindowEditControl(localGWindowDialogClient2, 9.0F, 5.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisRocket.this.getTimeOut();
        return false;
      }
    });
    localGWindowDialogClient2.addLabel(this.wLTimeOutM = new GWindowLabel(localGWindowDialogClient2, 11.2F, 5.0F, 1.0F, 1.3F, ":", null));
    localGWindowDialogClient2.addControl(this.wTimeOutM = new GWindowEditControl(localGWindowDialogClient2, 11.5F, 5.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisRocket.this.getTimeOut();
        return false;
      }
    });
    localGWindowDialogClient2.addLabel(this.wLCount = new GWindowLabel(localGWindowDialogClient2, 1.0F, 7.0F, 7.0F, 1.3F, i18n("Count"), null));
    localGWindowDialogClient2.addControl(this.wCount = new GWindowEditControl(localGWindowDialogClient2, 9.0F, 7.0F, 5.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        String str = PlMisRocket.this.wCount.getValue();
        int i = 0;
        try { i = Integer.parseInt(str); } catch (Exception localException) {
        }
        if (i < 0) i = 0;
        PlMisRocket.Rocket localRocket = (PlMisRocket.Rocket)Plugin.builder.selectedActor();
        localRocket.count = i;
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient2.addLabel(this.wLPeriodH = new GWindowLabel(localGWindowDialogClient2, 1.0F, 9.0F, 7.0F, 1.3F, i18n("Period"), null));
    localGWindowDialogClient2.addControl(this.wPeriodH = new GWindowEditControl(localGWindowDialogClient2, 9.0F, 9.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisRocket.this.getPeriod();
        return false;
      }
    });
    localGWindowDialogClient2.addLabel(this.wLPeriodM = new GWindowLabel(localGWindowDialogClient2, 11.2F, 9.0F, 1.0F, 1.3F, ":", null));
    localGWindowDialogClient2.addControl(this.wPeriodM = new GWindowEditControl(localGWindowDialogClient2, 11.5F, 9.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisRocket.this.getPeriod();
        return false;
      }
    });
    localGWindowDialogClient2.addLabel(new GWindowLabel(localGWindowDialogClient2, 1.0F, 11.0F, 7.0F, 1.3F, i18n("Target"), null));

    localGWindowDialogClient2.addControl(this.wTargetSet = new GWindowButton(localGWindowDialogClient2, 1.0F, 13.0F, 5.0F, 1.6F, i18n("&Set"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2) {
          PlMisRocket.Rocket localRocket = (PlMisRocket.Rocket)Plugin.builder.selectedActor();
          localRocket.target = null;
          PlMission.setChanged();
          Plugin.builder.beginSelectTarget();
        }
        return false;
      }
    });
    localGWindowDialogClient2.addControl(this.wTargetClear = new GWindowButton(localGWindowDialogClient2, 9.0F, 13.0F, 5.0F, 1.6F, i18n("&Clear"), null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 == 2) {
          PlMisRocket.Rocket localRocket = (PlMisRocket.Rocket)Plugin.builder.selectedActor();
          localRocket.target = null;
          PlMission.setChanged();
        }
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
    Rocket localRocket = (Rocket)builder.selectedActor();
    localRocket.timeout = f;
    PlMission.setChanged();
  }
  private void getPeriod() {
    String str = this.wPeriodH.getValue();
    double d1 = 0.0D;
    try { d1 = Double.parseDouble(str); } catch (Exception localException1) {
    }
    if (d1 < 0.0D) d1 = 0.0D;
    if (d1 > 12.0D) d1 = 12.0D;
    str = this.wPeriodM.getValue();
    double d2 = 0.0D;
    try { d2 = Double.parseDouble(str); } catch (Exception localException2) {
    }
    if (d2 < 0.0D) d2 = 0.0D;
    if (d2 > 60.0D) d2 = 60.0D;
    float f = (float)(d1 * 60.0D + d2);
    Rocket localRocket = (Rocket)builder.selectedActor();
    localRocket.period = f;
    PlMission.setChanged();
  }

  public String mis_getProperties(Actor paramActor) {
    Orient localOrient1 = new Orient();
    String str = "";
    int i = builder.wSelect.comboBox1.getSelected();
    int j = builder.wSelect.comboBox2.getSelected();
    if (i != this.startComboBox1)
      return str;
    if ((j < 0) || (j >= this.item.length)) {
      return str;
    }
    Rocket localRocket = (Rocket)paramActor;
    Point3d localPoint3d = localRocket.pos.getAbsPoint();
    Orient localOrient2 = localRocket.pos.getAbsOrient();
    localOrient1.set(localOrient2);
    localOrient1.wrap360();
    str = " 1_" + localRocket.name() + " " + localRocket.item.name + " " + localRocket.getArmy() + " " + formatPos(localPoint3d.x, localPoint3d.y, localOrient1.azimut()) + " " + localRocket.timeout + " " + localRocket.count + " " + localRocket.period + " " + (localRocket.target == null ? "" : new StringBuffer().append(formatValue(localRocket.target.x)).append(" ").append(formatValue(localRocket.target.y)).toString());
    return str;
  }

  public Actor mis_insert(Loc paramLoc, String paramString) {
    int i = builder.wSelect.comboBox1.getSelected();
    int j = builder.wSelect.comboBox2.getSelected();
    if (i != this.startComboBox1)
      return null;
    if ((j < 0) || (j >= this.item.length)) {
      return null;
    }
    Orient localOrient = new Orient();
    Point3d localPoint3d = new Point3d();
    NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramString);
    if (localNumberTokenizer.hasMoreTokens()) {
      String str1 = localNumberTokenizer.next("");
      if (localNumberTokenizer.hasMoreTokens()) {
        String str2 = localNumberTokenizer.next("");
        if (localNumberTokenizer.hasMoreTokens()) {
          Item localItem = (Item)this.itemMap.get(str2);
          if (localItem != null) {
            int k = localNumberTokenizer.next(1, 1, 2);
            localPoint3d.x = localNumberTokenizer.next(0.0D);
            localPoint3d.x = paramLoc.getPoint().x;
            if (localNumberTokenizer.hasMoreTokens()) {
              localPoint3d.y = localNumberTokenizer.next(0.0D);
              localPoint3d.y = paramLoc.getPoint().y;
              if (localNumberTokenizer.hasMoreTokens()) {
                Rocket localRocket = insert(localItem, null, localPoint3d, false);
                if (localRocket != null) {
                  localRocket.setArmy(k);
                  localOrient.set(localNumberTokenizer.next(0.0F), 0.0F, 0.0F);
                  localRocket.pos.setAbs(localOrient);
                  localRocket.pos.reset();
                  localRocket.timeout = localNumberTokenizer.next(localRocket.timeout);
                  localRocket.count = localNumberTokenizer.next(localRocket.count);
                  localRocket.period = localNumberTokenizer.next(localRocket.period);
                  if (localNumberTokenizer.hasMoreTokens())
                    localRocket.target = new Point2d(localNumberTokenizer.next(0.0D), localNumberTokenizer.next(0.0D));
                }
                return localRocket;
              }
            }
          }
        }
      }
    }
    return null;
  }

  public boolean mis_validateSelected(int paramInt1, int paramInt2) {
    if (paramInt1 != this.startComboBox1) {
      return false;
    }
    return (paramInt2 >= 0) && (paramInt2 < this.item.length);
  }

  static
  {
    Property.set(PlMisRocket.class, "name", "MisRocket");
  }

  static class Item
  {
    public int indx;
    public String name;
    public String i18nName;
    public String meshName;
    public String iconName;
    public int army;
  }

  static class Rocket extends ActorSimpleHMesh
  {
    public PlMisRocket.Item item;
    public float timeout = 0.0F;
    public int count = 1;
    public float period = 20.0F;
    public Point2d target;

    public Rocket(PlMisRocket.Item paramItem)
    {
      super();
      this.item = paramItem;
      if (paramItem.iconName != null) try {
          this.icon = Mat.New(paramItem.iconName);
        } catch (Exception localException) {
        } setArmy(paramItem.army);
    }
  }
}