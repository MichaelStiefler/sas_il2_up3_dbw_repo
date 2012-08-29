package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
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
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;

public class PlMisStaticCamera extends Plugin
{
  private static final int hMIN = 2;
  private static final int hMAX = 10000;
  private static final int hDEF = 100;
  protected ArrayList allActors = new ArrayList();

  Item[] item = { new Item("StaticCamera") };

  private Point2d p2d = new Point2d();
  private PlMission pluginMission;
  private int startComboBox1;
  private GWindowMenuItem viewType;
  private String[] _actorInfo = new String[1];
  GWindowTabDialogClient.Tab tabTarget;
  GWindowEditControl wH;

  public void renderMap2DAfter()
  {
    if (Plugin.builder.isFreeView()) return;
    if (!this.viewType.bChecked) return;
    Actor localActor = Plugin.builder.selectedActor();
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorStaticCamera localActorStaticCamera = (ActorStaticCamera)this.allActors.get(j);
      if (Plugin.builder.project2d(localActorStaticCamera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), this.p2d)) {
        int k = Army.color(0);
        if (localActorStaticCamera == localActor)
          k = Builder.colorSelected();
        IconDraw.setColor(k);
        IconDraw.render(localActorStaticCamera, this.p2d.jdField_x_of_type_Double, this.p2d.jdField_y_of_type_Double);
      }
    }
  }

  public boolean save(SectFile paramSectFile)
  {
    int i = this.allActors.size();
    if (i == 0) return true;
    int j = paramSectFile.sectionAdd("StaticCamera");
    for (int k = 0; k < i; k++) {
      ActorStaticCamera localActorStaticCamera = (ActorStaticCamera)this.allActors.get(k);
      paramSectFile.lineAdd(j, "" + (int)localActorStaticCamera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_x_of_type_Double + " " + (int)localActorStaticCamera.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_y_of_type_Double + " " + localActorStaticCamera.h);
    }

    return true;
  }

  public void load(SectFile paramSectFile) {
    int i = paramSectFile.sectionIndex("StaticCamera");
    if (i >= 0) {
      int j = paramSectFile.vars(i);
      Point3d localPoint3d = new Point3d();
      for (int k = 0; k < j; k++) {
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
        localPoint3d.jdField_x_of_type_Double = localNumberTokenizer.next(0);
        localPoint3d.jdField_y_of_type_Double = localNumberTokenizer.next(0);
        int m = localNumberTokenizer.next(100, 2, 10000);
        ActorStaticCamera localActorStaticCamera = insert(localPoint3d, false);
        if (localActorStaticCamera != null)
          localActorStaticCamera.h = m;
      }
    }
  }

  public void deleteAll()
  {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorStaticCamera localActorStaticCamera = (ActorStaticCamera)this.allActors.get(j);
      localActorStaticCamera.destroy();
    }
    this.allActors.clear();
  }

  public void delete(Actor paramActor) {
    this.allActors.remove(paramActor);
    paramActor.destroy();
  }

  private ActorStaticCamera insert(Point3d paramPoint3d, boolean paramBoolean) {
    if (this.allActors.size() >= 20)
      return null;
    try {
      ActorStaticCamera localActorStaticCamera = new ActorStaticCamera(paramPoint3d);
      Property.set(localActorStaticCamera, "builderSpawn", "");
      Property.set(localActorStaticCamera, "builderPlugin", this);
      this.allActors.add(localActorStaticCamera);
      if (paramBoolean)
        Plugin.builder.setSelected(localActorStaticCamera);
      PlMission.setChanged();
      return localActorStaticCamera; } catch (Exception localException) {
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
      ActorStaticCamera localActorStaticCamera = (ActorStaticCamera)this.allActors.get(j);
      localActorStaticCamera.drawing(this.viewType.bChecked);
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
        Plugin.builder.wSelect.comboBox2.add(Plugin.i18n(this.item[i].name));
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
    ActorStaticCamera localActorStaticCamera = (ActorStaticCamera)paramActor;
    this._actorInfo[0] = (Plugin.i18n("StaticCamera") + " " + localActorStaticCamera.h);
    return this._actorInfo;
  }

  public void syncSelector()
  {
    ActorStaticCamera localActorStaticCamera = (ActorStaticCamera)Plugin.builder.selectedActor();
    fillComboBox2(this.startComboBox1);
    Plugin.builder.wSelect.comboBox2.setSelected(0, true, false);
    Plugin.builder.wSelect.tabsClient.addTab(1, this.tabTarget);
    this.wH.setValue("" + localActorStaticCamera.h, false);
  }

  public void createGUI() {
    this.startComboBox1 = Plugin.builder.wSelect.comboBox1.size();
    Plugin.builder.wSelect.comboBox1.add(Plugin.i18n("StaticCamera"));
    Plugin.builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMisStaticCamera.this.fillComboBox2(i);
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
      this.viewType = Plugin.builder.mDisplayFilter.subMenu.addItem(i, new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, Plugin.i18n("showStaticCamers"), null)
      {
        public void execute() {
          this.jdField_bChecked_of_type_Boolean = (!this.jdField_bChecked_of_type_Boolean);
          PlMisStaticCamera.this.updateView();
        }
      });
      this.viewType.bChecked = true;
    }

    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)Plugin.builder.wSelect.tabsClient.create(new GWindowDialogClient());
    this.tabTarget = Plugin.builder.wSelect.tabsClient.createTab(Plugin.i18n("StaticCamera"), localGWindowDialogClient);

    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 4.0F, 1.3F, Plugin.i18n("Height"), null));
    localGWindowDialogClient.addLabel(new GWindowLabel(localGWindowDialogClient, 11.0F, 1.0F, 2.0F, 1.3F, Plugin.i18n("[m]"), null));
    localGWindowDialogClient.addControl(this.wH = new GWindowEditControl(localGWindowDialogClient, 6.0F, 1.0F, 4.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorStaticCamera localActorStaticCamera = (ActorStaticCamera)Plugin.builder.selectedActor();
        int i = localActorStaticCamera.h;
        try { i = Integer.parseInt(getValue()); } catch (Exception localException) {
        }
        if (i < 2) i = 2;
        if (i > 10000) i = 10000;
        localActorStaticCamera.h = i;
        PlMission.setChanged();
        return false;
      } } );
  }

  static {
    Property.set(PlMisStaticCamera.class, "name", "MisStaticCamera");
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