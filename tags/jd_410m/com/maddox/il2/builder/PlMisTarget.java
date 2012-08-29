package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GCaption;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowCheckBox;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowHSliderInt;
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
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.game.I18N;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;

public class PlMisTarget extends Plugin
{
  protected ArrayList allActors = new ArrayList();

  Item[] item = { new Item("Destroy", 0), new Item("DestroyGround", 1), new Item("DestroyBridge", 2), new Item("Inspect", 3), new Item("Escort", 4), new Item("Defence", 5), new Item("DefenceGround", 6), new Item("DefenceBridge", 7) };

  private float[] line2XYZ = new float[6];
  private Point2d p2d = new Point2d();
  private Point2d p2dt = new Point2d();
  private Point3d p3d = new Point3d();
  private static final int NCIRCLESEGMENTS = 48;
  private static float[] _circleXYZ = new float[''];
  private PlMission pluginMission;
  private int startComboBox1;
  private GWindowMenuItem viewType;
  private String[] _actorInfo = new String[2];
  GWindowTabDialogClient.Tab tabTarget;
  GWindowLabel wType;
  GWindowLabel wTarget;
  GWindowCheckBox wBTimeout;
  GWindowLabel wLTimeout;
  GWindowEditControl wTimeoutH;
  GWindowEditControl wTimeoutM;
  GWindowHSliderInt wR;
  GWindowCheckBox wBLanding;
  GWindowLabel wLLanding;
  GWindowComboControl wImportance;
  GWindowLabel wLDestruct;
  GWindowComboControl wDestruct;
  GWindowLabel wLArmy;
  GWindowComboControl wArmy;

  private int targetColor(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean) return Builder.colorSelected();
    switch (paramInt) { case 0:
      return -1;
    case 1:
      return -16711936;
    case 2:
      return -8454144;
    }
    return 0;
  }

  public void renderMap2DBefore() {
    if (builder.isFreeView()) return;
    if (!this.viewType.bChecked) return;
    Actor localActor = builder.selectedActor();
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorTarget localActorTarget = (ActorTarget)this.allActors.get(j);
      if ((!Actor.isValid(localActorTarget.getTarget())) || 
        (!builder.project2d(localActorTarget.pos.getAbsPoint(), this.p2d)) || (!builder.project2d(localActorTarget.getTarget().pos.getAbsPoint(), this.p2dt)))
        continue;
      if (this.p2d.distance(this.p2dt) > 4.0D) {
        int k = targetColor(localActorTarget.importance, localActorTarget == localActor);
        this.line2XYZ[0] = (float)this.p2d.x; this.line2XYZ[1] = (float)this.p2d.y; this.line2XYZ[2] = 0.0F;
        this.line2XYZ[3] = (float)this.p2dt.x; this.line2XYZ[4] = (float)this.p2dt.y; this.line2XYZ[5] = 0.0F;
        Render.drawBeginLines(-1);
        Render.drawLines(this.line2XYZ, 2, 1.0F, k, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE | Mat.BLEND, 3);

        Render.drawEnd();
      }
    }
  }

  public void renderMap2DAfter()
  {
    if (builder.isFreeView()) return;
    if (!this.viewType.bChecked) return;
    Actor localActor = builder.selectedActor();
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorTarget localActorTarget = (ActorTarget)this.allActors.get(j);
      if (builder.project2d(localActorTarget.pos.getAbsPoint(), this.p2d)) {
        int k = targetColor(localActorTarget.importance, localActorTarget == localActor);
        IconDraw.setColor(k);
        if ((Actor.isValid(localActorTarget.getTarget())) && 
          (builder.project2d(localActorTarget.getTarget().pos.getAbsPoint(), this.p2dt)) && 
          (this.p2d.distance(this.p2dt) > 4.0D)) {
          Render.drawTile((float)(this.p2dt.x - builder.conf.iconSize / 2), (float)(this.p2dt.y - builder.conf.iconSize / 2), builder.conf.iconSize, builder.conf.iconSize, 0.0F, Plugin.targetIcon, k, 0.0F, 1.0F, 1.0F, -1.0F);
        }

        IconDraw.render(localActorTarget, this.p2d.x, this.p2d.y);
        if ((localActorTarget.type != 3) && (localActorTarget.type != 6) && (localActorTarget.type != 1)) {
          continue;
        }
        localActorTarget.pos.getAbs(this.p3d);
        this.p3d.x += localActorTarget.r;
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
    int i = this.allActors.size();
    if (i == 0) return true;
    int j = paramSectFile.sectionAdd("Target");
    for (int k = 0; k < i; k++) {
      ActorTarget localActorTarget = (ActorTarget)this.allActors.get(k);
      String str = "";
      int m = 0;
      int n = 0;
      int i1 = 0;
      if (Actor.isValid(localActorTarget.target)) {
        if ((localActorTarget.target instanceof PPoint)) {
          str = localActorTarget.target.getOwner().name();
          m = ((Path)localActorTarget.target.getOwner()).pointIndx((PPoint)localActorTarget.target);
        } else {
          str = localActorTarget.target.name();
        }
        Point3d localPoint3d = localActorTarget.target.pos.getAbsPoint();
        n = (int)localPoint3d.x;
        i1 = (int)localPoint3d.y;
      }
      paramSectFile.lineAdd(j, "" + localActorTarget.type + " " + localActorTarget.importance + " " + (localActorTarget.bTimeout ? "1 " : "0 ") + localActorTarget.timeout + " " + localActorTarget.destructLevel + (localActorTarget.bLanding ? 1 : 0) + " " + (int)localActorTarget.pos.getAbsPoint().x + " " + (int)localActorTarget.pos.getAbsPoint().y + " " + localActorTarget.r + (str.length() > 0 ? " " + m + " " + str + " " + n + " " + i1 : ""));
    }

    return true;
  }

  public void load(SectFile paramSectFile) {
    int i = paramSectFile.sectionIndex("Target");
    if (i >= 0) {
      int j = paramSectFile.vars(i);
      Point3d localPoint3d = new Point3d();
      for (int k = 0; k < j; k++) {
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
        int m = localNumberTokenizer.next(0, 0, 7);
        int n = localNumberTokenizer.next(0, 0, 2);
        boolean bool1 = localNumberTokenizer.next(0) == 1;
        int i1 = localNumberTokenizer.next(0, 0, 720);
        int i2 = localNumberTokenizer.next(0);
        boolean bool2 = (i2 & 0x1) == 1;
        i2 /= 10;
        if (i2 < 0) i2 = 0;
        if (i2 > 100) i2 = 100;
        localPoint3d.x = localNumberTokenizer.next(0);
        localPoint3d.y = localNumberTokenizer.next(0);
        int i3 = localNumberTokenizer.next(0);
        if ((m == 3) || (m == 6) || (m == 1))
        {
          if (i3 < 2) i3 = 2;
          if (i3 > 3000) i3 = 3000;
        }
        int i4 = localNumberTokenizer.next(0);
        String str = localNumberTokenizer.next(null);
        if ((str != null) && (str.startsWith("Bridge")))
          str = " " + str;
        ActorTarget localActorTarget = insert(localPoint3d, m, str, i4, false);
        if (localActorTarget != null) {
          localActorTarget.importance = n;
          localActorTarget.bTimeout = bool1;
          localActorTarget.timeout = i1;
          localActorTarget.r = i3;
          localActorTarget.bLanding = bool2;
          localActorTarget.destructLevel = i2;
        }
      }
    }
  }

  public void deleteAll() {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorTarget localActorTarget = (ActorTarget)this.allActors.get(j);
      localActorTarget.destroy();
    }
    this.allActors.clear();
  }

  public void delete(Actor paramActor) {
    this.allActors.remove(paramActor);
    paramActor.destroy();
  }

  public void afterDelete() {
    for (int i = 0; i < this.allActors.size(); ) {
      ActorTarget localActorTarget = (ActorTarget)this.allActors.get(i);
      if ((localActorTarget.target != null) && (!Actor.isValid(localActorTarget.target))) {
        localActorTarget.destroy();
        this.allActors.remove(i);
      } else {
        i++;
      }
    }
  }

  private ActorTarget insert(Point3d paramPoint3d, int paramInt1, String paramString, int paramInt2, boolean paramBoolean)
  {
    try {
      ActorTarget localActorTarget1 = new ActorTarget(paramPoint3d, paramInt1, paramString, paramInt2);
      if (Actor.isValid(localActorTarget1.target)) {
        for (int i = 0; i < this.allActors.size(); i++) {
          ActorTarget localActorTarget2 = (ActorTarget)this.allActors.get(i);
          if ((localActorTarget1.type != localActorTarget2.type) || (!Actor.isValid(localActorTarget2.target)) || (
            (localActorTarget2.target != localActorTarget1.target) && ((!(localActorTarget1.target instanceof PPoint)) || (localActorTarget1.target.getOwner() != localActorTarget2.target.getOwner()))))
            continue;
          localActorTarget1.destroy();
          return null;
        }

      }

      builder.align(localActorTarget1);
      Property.set(localActorTarget1, "builderSpawn", "");
      Property.set(localActorTarget1, "builderPlugin", this);
      this.allActors.add(localActorTarget1);
      if (paramBoolean)
        builder.setSelected(localActorTarget1);
      PlMission.setChanged();
      return localActorTarget1; } catch (Exception localException) {
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
    insert(paramLoc.getPoint(), this.item[j].indx, null, 0, paramBoolean);
  }

  public void changeType() {
    builder.setSelected(null);
  }

  private void updateView() {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorTarget localActorTarget = (ActorTarget)this.allActors.get(j);
      localActorTarget.drawing(this.viewType.bChecked);
    }
  }

  public void configure()
  {
    if (getPlugin("Mission") == null)
      throw new RuntimeException("PlMisTarget: plugin 'Mission' not found");
    this.pluginMission = ((PlMission)getPlugin("Mission"));
  }

  private void fillComboBox2(int paramInt) {
    if (paramInt != this.startComboBox1)
      return;
    if (builder.wSelect.curFilledType != paramInt) {
      builder.wSelect.curFilledType = paramInt;
      builder.wSelect.comboBox2.clear(false);
      for (int i = 0; i < this.item.length; i++)
        builder.wSelect.comboBox2.add(i18n(this.item[i].name));
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
    ActorTarget localActorTarget = (ActorTarget)paramActor;
    switch (localActorTarget.importance) { case 0:
      this._actorInfo[0] = (i18n("Primary") + " " + i18n(this.item[localActorTarget.type].name)); break;
    case 1:
      this._actorInfo[0] = (i18n("Secondary") + " " + i18n(this.item[localActorTarget.type].name)); break;
    case 2:
      this._actorInfo[0] = (i18n("Secret") + " " + i18n(this.item[localActorTarget.type].name));
    }
    if ((Actor.isValid(localActorTarget.getTarget())) && ((localActorTarget.getTarget() instanceof PPoint))) {
      Path localPath = (Path)localActorTarget.getTarget().getOwner();
      if ((localPath instanceof PathAir))
        this._actorInfo[1] = ((PathAir)localPath).typedName;
      else if ((localPath instanceof PathChief))
        this._actorInfo[1] = Property.stringValue(localPath, "i18nName", "");
      else
        this._actorInfo[1] = localPath.name();
    } else {
      this._actorInfo[1] = null;
    }
    return this._actorInfo;
  }

  public void syncSelector()
  {
    ActorTarget localActorTarget = (ActorTarget)builder.selectedActor();
    fillComboBox2(this.startComboBox1);
    builder.wSelect.comboBox2.setSelected(localActorTarget.type, true, false);
    builder.wSelect.tabsClient.addTab(1, this.tabTarget);
    this.wType.cap.set(i18n(this.item[localActorTarget.type].name));
    float f = 3.0F;
    if ((Actor.isValid(localActorTarget.getTarget())) && ((localActorTarget.getTarget() instanceof PPoint))) {
      this.wTarget.showWindow();
      Path localPath = (Path)localActorTarget.getTarget().getOwner();
      if ((localPath instanceof PathAir))
        this.wTarget.cap.set(((PathAir)localPath).typedName);
      else if ((localPath instanceof PathChief))
        this.wTarget.cap.set(Property.stringValue(localPath, "i18nName", ""));
      else
        this.wTarget.cap.set(localPath.name());
      f += 2.0F;
    } else {
      this.wTarget.hideWindow();
    }
    if ((localActorTarget.type == 3) || (localActorTarget.type == 6) || (localActorTarget.type == 7))
    {
      this.wBTimeout.hideWindow();
    } else {
      this.wBTimeout.showWindow();
      this.wBTimeout.setMetricPos(this.wBTimeout.metricWin.x, f);
    }

    this.wBTimeout.setChecked(localActorTarget.bTimeout, false);
    this.wLTimeout.setMetricPos(this.wLTimeout.metricWin.x, f);
    this.wTimeoutH.setEnable(localActorTarget.bTimeout);
    this.wTimeoutM.setEnable(localActorTarget.bTimeout);
    this.wTimeoutH.setMetricPos(this.wTimeoutH.metricWin.x, f);
    this.wTimeoutM.setMetricPos(this.wTimeoutM.metricWin.x, f);
    this.wTimeoutH.setValue("" + localActorTarget.timeout / 60 % 24, false);
    this.wTimeoutM.setValue("" + localActorTarget.timeout % 60, false);
    f += 2.0F;
    if ((localActorTarget.type == 3) || (localActorTarget.type == 6) || (localActorTarget.type == 1))
    {
      this.wR.setPos(localActorTarget.r / 50, false);
      this.wR.showWindow();
      this.wR.setMetricPos(this.wR.metricWin.x, f);
      f += 2.0F;
    } else {
      this.wR.hideWindow();
    }
    if (localActorTarget.type == 3) {
      this.wBLanding.showWindow();
      this.wLLanding.showWindow();
      this.wBLanding.setMetricPos(this.wBLanding.metricWin.x, f);
      this.wLLanding.setMetricPos(this.wLLanding.metricWin.x, f);
      this.wBLanding.setChecked(localActorTarget.bLanding, false);
      f += 2.0F;
    } else {
      this.wBLanding.hideWindow();
      this.wLLanding.hideWindow();
    }
    this.wImportance.setMetricPos(this.wImportance.metricWin.x, f);
    this.wImportance.setSelected(localActorTarget.importance, true, false);
    f += 2.0F;

    if ((localActorTarget.type == 3) || (localActorTarget.type == 2) || (localActorTarget.type == 7))
    {
      this.wLDestruct.hideWindow();
      this.wDestruct.hideWindow();
    } else {
      this.wLDestruct.showWindow();
      this.wDestruct.showWindow();
      this.wLDestruct.setMetricPos(this.wLDestruct.metricWin.x, f);
      f += 2.0F;
      this.wDestruct.setMetricPos(this.wDestruct.metricWin.x, f);
      f += 2.0F;
      int i;
      if (localActorTarget.destructLevel < 12) i = 0;
      else if (localActorTarget.destructLevel < 37) i = 1;
      else if (localActorTarget.destructLevel < 62) i = 2;
      else if (localActorTarget.destructLevel < 87) i = 3; else
        i = 4;
      this.wDestruct.setSelected(i, true, false);
      if ((localActorTarget.type == 0) || (localActorTarget.type == 1))
      {
        this.wDestruct.posEnable[0] = false;
        this.wDestruct.posEnable[4] = true;
      } else {
        this.wDestruct.posEnable[0] = true;
        this.wDestruct.posEnable[4] = false;
      }
    }

    this.wLArmy.setMetricPos(this.wLArmy.metricWin.x, f);
    f += 2.0F;
    this.wArmy.setMetricPos(this.wArmy.metricWin.x, f);
    if (Actor.isValid(Path.player)) {
      this.wArmy.setSelected(Path.player.getArmy() - 1, true, false);
      this.wArmy.bEnable = false;
    } else {
      this.wArmy.setSelected(PlMission.cur.missionArmy - 1, true, false);
      this.wArmy.bEnable = true;
    }
  }

  public void createGUI()
  {
    this.startComboBox1 = builder.wSelect.comboBox1.size();
    builder.wSelect.comboBox1.add(i18n("tTarget"));
    builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMisTarget.this.fillComboBox2(i);
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
      this.viewType = builder.mDisplayFilter.subMenu.addItem(i, new GWindowMenuItem(builder.mDisplayFilter.subMenu, i18n("showTarget"), null)
      {
        public void execute() {
          this.bChecked = (!this.bChecked);
          PlMisTarget.this.updateView();
        }
      });
      this.viewType.bChecked = true;
    }

    GWindowDialogClient localGWindowDialogClient = (GWindowDialogClient)builder.wSelect.tabsClient.create(new GWindowDialogClient());
    this.tabTarget = builder.wSelect.tabsClient.createTab(i18n("tTarget"), localGWindowDialogClient);
    localGWindowDialogClient.addLabel(this.wType = new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 15.0F, 1.3F, i18n("lType"), null));
    localGWindowDialogClient.addLabel(this.wTarget = new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 15.0F, 1.3F, i18n("tTarget"), null));
    localGWindowDialogClient.addControl(this.wBTimeout = new GWindowCheckBox(localGWindowDialogClient, 1.0F, 5.0F, null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorTarget localActorTarget = (ActorTarget)Plugin.builder.selectedActor();
        localActorTarget.bTimeout = isChecked();
        PlMisTarget.this.wTimeoutH.setEnable(localActorTarget.bTimeout);
        PlMisTarget.this.wTimeoutM.setEnable(localActorTarget.bTimeout);
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLTimeout = new GWindowLabel(localGWindowDialogClient, 3.0F, 5.0F, 5.0F, 1.3F, i18n("TimeOut"), null));
    localGWindowDialogClient.addControl(this.wTimeoutH = new GWindowEditControl(localGWindowDialogClient, 9.0F, 5.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisTarget.this.getTimeOut();
        return false;
      }
    });
    localGWindowDialogClient.addControl(this.wTimeoutM = new GWindowEditControl(localGWindowDialogClient, 12.0F, 5.0F, 2.0F, 1.3F, "") {
      public void afterCreated() { super.afterCreated();
        this.bNumericOnly = true;
        this.bDelayedNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMisTarget.this.getTimeOut();
        return false;
      }
    });
    localGWindowDialogClient.addControl(this.wR = new GWindowHSliderInt(localGWindowDialogClient, 0, 61, 11, 1.0F, 7.0F, 10.0F) {
      public void afterCreated() { super.afterCreated();
        this.bSlidingNotify = true; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorTarget localActorTarget = (ActorTarget)Plugin.builder.selectedActor();
        localActorTarget.r = (pos() * 50);
        if (localActorTarget.r < 2) localActorTarget.r = 2;
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addControl(this.wBLanding = new GWindowCheckBox(localGWindowDialogClient, 1.0F, 9.0F, null) {
      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorTarget localActorTarget = (ActorTarget)Plugin.builder.selectedActor();
        localActorTarget.bLanding = isChecked();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLLanding = new GWindowLabel(localGWindowDialogClient, 3.0F, 9.0F, 7.0F, 1.3F, i18n("landing"), null));
    localGWindowDialogClient.addControl(this.wImportance = new GWindowComboControl(localGWindowDialogClient, 1.0F, 11.0F, 10.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        add(Plugin.i18n("Primary"));
        add(Plugin.i18n("Secondary"));
        add(Plugin.i18n("Secret")); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorTarget localActorTarget = (ActorTarget)Plugin.builder.selectedActor();
        localActorTarget.importance = getSelected();
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLDestruct = new GWindowLabel(localGWindowDialogClient, 1.0F, 13.0F, 12.0F, 1.3F, i18n("DestructLevel"), null));
    localGWindowDialogClient.addControl(this.wDestruct = new GWindowComboControl(localGWindowDialogClient, 1.0F, 15.0F, 10.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        add("0 %");
        add("25 %");
        add("50 %");
        add("75 %");
        add("100 %");
        boolean[] arrayOfBoolean = new boolean[5];
        for (int i = 0; i < 5; i++)
          arrayOfBoolean[i] = true;
        this.posEnable = arrayOfBoolean; }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        ActorTarget localActorTarget = (ActorTarget)Plugin.builder.selectedActor();
        localActorTarget.destructLevel = (getSelected() * 25);
        PlMission.setChanged();
        return false;
      }
    });
    localGWindowDialogClient.addLabel(this.wLArmy = new GWindowLabel(localGWindowDialogClient, 1.0F, 15.0F, 12.0F, 1.3F, i18n("AppliesArmy"), null));
    localGWindowDialogClient.addControl(this.wArmy = new GWindowComboControl(localGWindowDialogClient, 1.0F, 17.0F, 10.0F) {
      public void afterCreated() { super.afterCreated();
        setEditable(false);
        add(I18N.army(Army.name(1)));
        add(I18N.army(Army.name(2))); }

      public boolean notify(int paramInt1, int paramInt2) {
        if (paramInt1 != 2) return false;
        PlMission.cur.missionArmy = (getSelected() + 1);
        PlMission.setChanged();
        return false;
      } } );
  }

  private void getTimeOut() {
    ActorTarget localActorTarget = (ActorTarget)builder.selectedActor();
    String str = this.wTimeoutH.getValue();
    double d1 = 0.0D;
    try { d1 = Double.parseDouble(str); } catch (Exception localException1) {
    }
    if (d1 < 0.0D) d1 = 0.0D;
    if (d1 > 12.0D) d1 = 12.0D;
    str = this.wTimeoutM.getValue();
    double d2 = 0.0D;
    try { d2 = Double.parseDouble(str); } catch (Exception localException2) {
    }
    if (d2 < 0.0D) d2 = 0.0D;
    if (d2 > 59.0D) d2 = 59.0D;
    localActorTarget.timeout = (int)(d1 * 60.0D + d2);

    this.wTimeoutH.setValue("" + localActorTarget.timeout / 60 % 24, false);
    this.wTimeoutM.setValue("" + localActorTarget.timeout % 60, false);
    PlMission.setChanged();
  }
  static {
    Property.set(PlMisTarget.class, "name", "MisTarget");
  }

  static class Item
  {
    public String name;
    public int indx;

    public Item(String paramString, int paramInt)
    {
      this.name = paramString;
      this.indx = paramInt;
    }
  }
}