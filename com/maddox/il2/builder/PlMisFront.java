package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.Front;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.I18N;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;

public class PlMisFront extends Plugin
{
  public static final int TILE_SIZE = 64;
  public static final int M = 16;
  private static final boolean USE_TILE_RENDER = false;
  protected ArrayList allActors;
  Item[] item;
  ArrayList markers;
  int tNx;
  int tNy;
  double camWorldXOffset;
  double camWorldYOffset;
  float camLeft;
  float camBottom;
  int _tNx;
  int _tNy;
  Mat[] mats;
  Mat baseTileMat;
  byte[] buf;
  byte[] _mask;
  private Point2d p2d;
  private PlMission pluginMission;
  private int startComboBox1;
  private GWindowMenuItem viewType;
  private String[] _actorInfo;

  public PlMisFront()
  {
    this.allActors = new ArrayList();

    this.item = new Item[Army.amountNet() - 1];
    for (int i = 0; i < this.item.length; i++) {
      this.item[i] = new Item(i + 1);
    }

    this.markers = new ArrayList();

    this.buf = new byte[16384];
    this._mask = new byte[4356];

    this.p2d = new Point2d();

    this._actorInfo = new String[1];
  }

  private void tilesUpdate()
  {
  }

  public void preRenderMap2D()
  {
    if (builder.isFreeView()) return;
    if (!this.viewType.bChecked) return;
    Front.preRender(true);
    if (Front.isMarkersUpdated())
      tilesUpdate();
  }

  public void renderMap2DAfter() {
    if (builder.isFreeView()) return;
    if (!this.viewType.bChecked) return;

    Front.render(builder.isView3D());

    IconDraw.setScrSize(builder.conf.iconSize * 2, builder.conf.iconSize * 2);
    Actor localActor = builder.selectedActor();
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorFrontMarker localActorFrontMarker = (ActorFrontMarker)this.allActors.get(j);
      if (builder.project2d(localActorFrontMarker.pos.getAbsPoint(), this.p2d)) {
        int k = Army.color(localActorFrontMarker.getArmy());
        if (localActorFrontMarker == localActor)
          k = Builder.colorSelected();
        IconDraw.setColor(k);
        IconDraw.render(localActorFrontMarker, this.p2d.x + builder.conf.iconSize * 2 / 3, this.p2d.y + builder.conf.iconSize * 2 / 3);
      }
    }
    IconDraw.setScrSize(builder.conf.iconSize, builder.conf.iconSize);
  }

  public boolean save(SectFile paramSectFile)
  {
    int i = this.allActors.size();
    if (i == 0) return true;
    int j = paramSectFile.sectionAdd("FrontMarker");
    for (int k = 0; k < i; k++) {
      Actor localActor = (Actor)this.allActors.get(k);
      paramSectFile.lineAdd(j, "FrontMarker" + k + " " + fmt(localActor.pos.getAbsPoint().x) + " " + fmt(localActor.pos.getAbsPoint().y) + " " + localActor.getArmy());
    }

    return true;
  }
  private String fmt(double paramDouble) {
    int i = paramDouble < 0.0D ? 1 : 0;
    if (i != 0) paramDouble = -paramDouble;
    double d = paramDouble + 0.005D - (int)paramDouble;
    if (d >= 0.1D) return (i != 0 ? "-" : "") + (int)paramDouble + "." + (int)(d * 100.0D);
    return (i != 0 ? "-" : "") + (int)paramDouble + ".0" + (int)(d * 100.0D);
  }

  public void load(SectFile paramSectFile) {
    int i = paramSectFile.sectionIndex("FrontMarker");
    if (i >= 0) {
      int j = paramSectFile.vars(i);
      Point3d localPoint3d = new Point3d();
      for (int k = 0; k < j; k++) {
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
        String str = localNumberTokenizer.next((String)null);
        localPoint3d.x = localNumberTokenizer.next(0.0D);
        localPoint3d.y = localNumberTokenizer.next(0.0D);
        int m = localNumberTokenizer.next(1, 1, Army.amountNet() - 1);
        if (m <= Army.amountSingle() - 1)
          insert(localPoint3d, false, m);
      }
    }
  }

  public void deleteAll() {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)this.allActors.get(j);
      localActor.destroy();
    }
    this.allActors.clear();
    Front.setMarkersChanged();
  }

  public void delete(Actor paramActor) {
    this.allActors.remove(paramActor);
    paramActor.destroy();
    Front.setMarkersChanged();
  }

  private ActorFrontMarker insert(Point3d paramPoint3d, boolean paramBoolean, int paramInt)
  {
    try
    {
      String str = i18n("FrontMarker") + " " + I18N.army(Army.name(paramInt));
      ActorFrontMarker localActorFrontMarker = new ActorFrontMarker(str, paramInt, paramPoint3d);
      Property.set(localActorFrontMarker, "builderSpawn", "");
      Property.set(localActorFrontMarker, "builderPlugin", this);
      this.allActors.add(localActorFrontMarker);
      if (paramBoolean)
        builder.setSelected(localActorFrontMarker);
      PlMission.setChanged();
      Front.setMarkersChanged();
      return localActorFrontMarker; } catch (Exception localException) {
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
    insert(paramLoc.getPoint(), paramBoolean, this.item[j].army);
  }

  private void updateView() {
    int i = this.allActors.size();
    for (int j = 0; j < i; j++) {
      ActorFrontMarker localActorFrontMarker = (ActorFrontMarker)this.allActors.get(j);
      localActorFrontMarker.drawing(this.viewType.bChecked);
    }
  }

  public void configure()
  {
    if (getPlugin("Mission") == null)
      throw new RuntimeException("PlMisFront: plugin 'Mission' not found");
    this.pluginMission = ((PlMission)getPlugin("Mission"));
  }

  private void fillComboBox2(int paramInt) {
    if (paramInt != this.startComboBox1)
      return;
    if (builder.wSelect.curFilledType != paramInt) {
      builder.wSelect.curFilledType = paramInt;
      builder.wSelect.comboBox2.clear(false);
      for (int i = 0; i < this.item.length; i++)
        builder.wSelect.comboBox2.add(i18n("FrontMarker") + " " + I18N.army(Army.name(this.item[i].army)));
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
    ActorFrontMarker localActorFrontMarker = (ActorFrontMarker)paramActor;
    this._actorInfo[0] = localActorFrontMarker.i18nKey;
    return this._actorInfo;
  }

  public void syncSelector()
  {
    ActorFrontMarker localActorFrontMarker = (ActorFrontMarker)builder.selectedActor();
    fillComboBox2(this.startComboBox1);
    builder.wSelect.comboBox2.setSelected(localActorFrontMarker.getArmy() - 1, true, false);
  }

  public void createGUI()
  {
    this.startComboBox1 = builder.wSelect.comboBox1.size();
    builder.wSelect.comboBox1.add(i18n("FrontMarker"));
    builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMisFront.this.fillComboBox2(i);
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
      this.viewType = builder.mDisplayFilter.subMenu.addItem(i, new GWindowMenuItem(builder.mDisplayFilter.subMenu, i18n("showFrontMarker"), null)
      {
        public void execute() {
          this.bChecked = (!this.bChecked);
          PlMisFront.this.updateView();
        }
      });
      this.viewType.bChecked = true;
    }
  }

  public void freeResources() {
  }

  static {
    Property.set(PlMisFront.class, "name", "MisFront");
  }

  static class Item
  {
    public int army;

    public Item(int paramInt)
    {
      this.army = paramInt;
    }
  }
}