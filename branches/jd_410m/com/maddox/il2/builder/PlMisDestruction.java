package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowDialogClient;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMenuPopUp;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.Statics.Block;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.il2.objects.bridges.LongBridge;
import com.maddox.il2.objects.buildings.House;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.HashMapInt;
import java.util.AbstractCollection;
import java.util.BitSet;

public class PlMisDestruction extends Plugin
{
  public static final int TILE_SIZE = 64;
  GWindowMenuItem mDialog;
  WDialog wDialog;
  private int fillSize;
  private int fillValue;
  Tile[][] tiles;
  boolean tilesChanged;
  Mat baseTileMat;
  byte[] buf;
  boolean bufEmpty;
  private Point3d _startFill;
  private Point3d _endFill;
  private Point3d _stepFill;
  private PlMission pluginMission;
  private SelectFilter _selectFilter;
  Actor findedActor;

  public PlMisDestruction()
  {
    this.fillSize = 1;
    this.fillValue = 127;

    this.tilesChanged = false;

    this.buf = new byte[16384];
    this.bufEmpty = true;

    this._startFill = new Point3d();
    this._endFill = new Point3d();
    this._stepFill = new Point3d();

    this._selectFilter = new SelectFilter();

    this.findedActor = null;
  }

  public boolean isActive()
  {
    if (builder.isFreeView()) return false;
    if (this.tiles == null) return false;
    return this.mDialog.bChecked;
  }

  private void tilesDel()
  {
    if (this.tiles == null) return;
    this.tiles = ((Tile[][])null);
  }
  private void tilesNew() {
    tilesDel();
    int i = Landscape.getSizeXpix();
    int j = Landscape.getSizeYpix();
    int k = (i + 64 - 1) / 64;
    int m = (j + 64 - 1) / 64;
    this.tiles = new Tile[m][k];

    Tile localTile = null;
    for (int n = 0; n < m; n++)
      for (int i1 = 0; i1 < k; i1++) {
        if (localTile == null) {
          localTile = new Tile(i1 * 64, n * 64);
        } else {
          localTile.x0 = (i1 * 64);
          localTile.y0 = (n * 64);
        }
        if (localTile.fillFromStatic(true)) {
          this.tiles[n][i1] = localTile;
          localTile = null;
          this.tilesChanged = true;
        }
      }
  }

  public void preRenderMap2D()
  {
    if (!isActive()) return;
    if (!this.tilesChanged) return;
    int i = this.tiles[0].length;
    int j = this.tiles.length;
    for (int k = 0; k < j; k++) {
      for (int m = 0; m < i; m++) {
        Tile localTile = this.tiles[k][m];
        if ((localTile != null) && (localTile.bChanged)) {
          localTile.updateImage();
        }
      }
    }
    this.tilesChanged = false;
  }

  public void renderMap2D() {
    if (!isActive()) return;
    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)Render.currentCamera();
    int i = this.tiles[0].length;
    int j = this.tiles.length;
    float f1 = 12800.0F;
    float f2 = (float)(f1 * localCameraOrtho2D.worldScale);
    int k = builder.conf.iLightDestruction << 24 | 0xFFFFFF;
    for (int m = 0; m < j; m++) {
      float f3 = (float)((m * f1 - localCameraOrtho2D.worldYOffset) * localCameraOrtho2D.worldScale);
      if (f3 > localCameraOrtho2D.top) break;
      if (f3 + f2 >= localCameraOrtho2D.bottom)
        for (int n = 0; n < i; n++) {
          float f4 = (float)((n * f1 - localCameraOrtho2D.worldXOffset) * localCameraOrtho2D.worldScale);
          if (f4 > localCameraOrtho2D.right) break;
          if (f4 + f2 >= localCameraOrtho2D.left) {
            Tile localTile = this.tiles[m][n];
            if ((localTile != null) && (localTile.mat != null))
              Render.drawTile(f4, f3, f2, f2, 0.0F, localTile.mat, k, 0.0F, 0.0F, 1.0F, 1.0F); 
          }
        }
    }
  }

  public void mapLoaded() {
    tilesDel();
    if (this.mDialog.bChecked)
      this.wDialog.hideWindow();
  }

  public void load(SectFile paramSectFile) {
    World.cur().statics.restoreAllBridges();
    World.cur().statics.restoreAllHouses();
    World.cur().statics.loadStateBridges(paramSectFile, false);
    World.cur().statics.loadStateHouses(paramSectFile, false);
    if (this.tiles == null) return;
    int i = this.tiles[0].length;
    int j = this.tiles.length;
    for (int k = 0; k < j; k++) {
      for (int m = 0; m < i; m++) {
        Tile localTile = this.tiles[k][m];
        if (localTile != null)
          localTile.bChanged = true;
      }
    }
    this.tilesChanged = true;
  }

  public boolean save(SectFile paramSectFile) {
    int i = paramSectFile.sectionAdd("Bridge");
    World.cur().statics.saveStateBridges(paramSectFile, i);
    i = paramSectFile.sectionAdd("House");
    World.cur().statics.saveStateHouses(paramSectFile, i);
    return true;
  }

  private void fill(Point3d paramPoint3d, int paramInt) {
    Statics localStatics = World.cur().statics;
    HashMapInt localHashMapInt = localStatics.allBlocks();
    int i = (int)(paramPoint3d.x / 200.0D);
    int j = (int)(paramPoint3d.y / 200.0D);
    paramInt |= 1;
    int k = i - paramInt / 2;
    int m = j - paramInt / 2;
    for (int n = m; n < m + paramInt; n++)
      for (int i1 = k; i1 < k + paramInt; i1++) {
        int i2 = localStatics.key(n, i1);
        Statics.Block localBlock = (Statics.Block)localHashMapInt.get(i2);
        if (localBlock != null) {
          int i3 = i1 / 64;
          int i4 = n / 64;
          this.tiles[i4][i3].bChanged = true;
          this.tilesChanged = true;
          localBlock.setDestruction(this.fillValue / 255.0F);
          PlMission.setChanged();
        }
      }
  }

  private void _doFill()
  {
    double d = this._endFill.distance(this._startFill);
    int i = (int)Math.round(d / 200.0D) + 1;
    float f = 1.0F / i;
    for (int j = 0; j <= i; j++) {
      this._stepFill.interpolate(this._startFill, this._endFill, j * f);
      fill(this._stepFill, this.fillSize);
    }
  }

  public void beginFill(Point3d paramPoint3d)
  {
    if (!isActive()) return;
    this._startFill.set(paramPoint3d);
  }
  public void fill(Point3d paramPoint3d) {
    if (!isActive()) return;
    this._endFill.set(paramPoint3d);
    _doFill();
    this._startFill.set(paramPoint3d);
  }
  public void endFill(Point3d paramPoint3d) {
  }
  public void configure() {
    if (getPlugin("Mission") == null)
      throw new RuntimeException("PlMisDestruction: plugin 'Mission' not found");
    this.pluginMission = ((PlMission)getPlugin("Mission"));
  }

  public Actor selectNear(Point3d paramPoint3d, double paramDouble)
  {
    this._selectFilter.reset(paramDouble * paramDouble);
    Engine.drawEnv().getFiltered((AbstractCollection)null, paramPoint3d.x - paramDouble, paramPoint3d.y - paramDouble, paramPoint3d.x + paramDouble, paramPoint3d.y + paramDouble, 15, this._selectFilter);

    return this._selectFilter.get();
  }

  public void fillPopUpMenu(GWindowMenuPopUp paramGWindowMenuPopUp, Point3d paramPoint3d)
  {
    if (!isActive()) return;
    this.findedActor = selectNear(paramPoint3d, 100.0D);
    if (this.findedActor == null) return;
    if ((this.findedActor instanceof Bridge)) {
      if (Actor.isAlive(this.findedActor))
        paramGWindowMenuPopUp.addItem(new GWindowMenuItem(paramGWindowMenuPopUp, i18n("De&stroyBridge"), i18n("TIPDestroyBridge")) {
          public void execute() { PlMisDestruction.this.doBridge(true); }
        });
      else paramGWindowMenuPopUp.addItem(new GWindowMenuItem(paramGWindowMenuPopUp, i18n("Re&storeBridge"), i18n("TIPRestoreBridge")) {
          public void execute() { PlMisDestruction.this.doBridge(false); }
        });
    }
    else if (Actor.isAlive(this.findedActor))
      paramGWindowMenuPopUp.addItem(new GWindowMenuItem(paramGWindowMenuPopUp, i18n("De&stroyObject"), i18n("TIPDestroyObject")) {
        public void execute() { PlMisDestruction.this.doHouse(true); }
      });
    else paramGWindowMenuPopUp.addItem(new GWindowMenuItem(paramGWindowMenuPopUp, i18n("Re&storeObject"), i18n("TIPRestoreObject")) {
        public void execute() { PlMisDestruction.this.doHouse(false); }
      });
  }

  private void doHouse(boolean paramBoolean)
  {
    Point3d localPoint3d = this.findedActor.pos.getAbsPoint();
    int i = (int)(localPoint3d.x / 64.0D / 200.0D);
    int j = (int)(localPoint3d.y / 64.0D / 200.0D);
    Tile localTile = this.tiles[j][i];
    if (localTile == null) return;
    localTile.bChanged = true;
    this.tilesChanged = true;
    this.findedActor.setDiedFlag(paramBoolean);
    PlMission.setChanged();
  }
  private void doBridge(boolean paramBoolean) {
    LongBridge localLongBridge = (LongBridge)this.findedActor;
    if (paramBoolean) {
      int i = localLongBridge.NumStateBits();
      BitSet localBitSet = new BitSet(i);
      int j = (int)(this.fillValue * i / 255.0F);
      if (j == 0) j = 1;
      if (j > i) j = i;
      int k = j;
      while (k > 0) {
        int m = (int)(Math.random() * j);
        if (!localBitSet.get(m)) {
          localBitSet.set(m);
          k--;
        }
      }
      localLongBridge.SetStateOfSegments(localBitSet);
    }
    else {
      localLongBridge.BeLive();
    }
    PlMission.setChanged();
  }

  public void createGUI()
  {
    this.baseTileMat = Mat.New("3do/builder/tile.mat");

    this.mDialog = builder.mView.subMenu.addItem(2, new GWindowMenuItem(builder.mView.subMenu, i18n("De&struction"), i18n("TIPDestruction"))
    {
      public void execute() {
        if (PlMisDestruction.this.wDialog.isVisible()) PlMisDestruction.this.wDialog.hideWindow();
        else if (PlMapLoad.getLandLoaded() != null) PlMisDestruction.this.wDialog.showWindow();
      }
    });
    this.wDialog = new WDialog();
  }

  public void freeResources() {
    this.findedActor = null;
  }
  static {
    Property.set(PlMisDestruction.class, "name", "MisDestruction");
  }

  class WDialog extends GWindowFramed
  {
    public GWindowHSliderInt wLight;
    public GWindowHSliderInt wSize;
    public GWindowHSliderInt wValue;

    public void windowShown()
    {
      PlMisDestruction.this.mDialog.bChecked = true;
      if (PlMisDestruction.this.tiles == null)
        PlMisDestruction.this.tilesNew();
      super.windowShown();
    }
    public void windowHidden() {
      PlMisDestruction.this.mDialog.bChecked = false;
      super.windowHidden();
    }
    public void created() {
      this.bAlwaysOnTop = true;
      super.created();
      this.title = Plugin.i18n("Destruction");
      GWindowDialogClient localGWindowDialogClient;
      this.clientWindow = create(localGWindowDialogClient = new GWindowDialogClient());
      GWindowLabel localGWindowLabel;
      localGWindowDialogClient.addLabel(localGWindowLabel = new GWindowLabel(localGWindowDialogClient, 1.0F, 1.0F, 11.0F, 1.3F, Plugin.i18n("DestLight"), null));
      localGWindowLabel.align = 2;
      localGWindowDialogClient.addLabel(localGWindowLabel = new GWindowLabel(localGWindowDialogClient, 1.0F, 3.0F, 11.0F, 1.3F, Plugin.i18n("DestSize"), null));
      localGWindowLabel.align = 2;
      localGWindowDialogClient.addLabel(localGWindowLabel = new GWindowLabel(localGWindowDialogClient, 1.0F, 5.0F, 11.0F, 1.3F, Plugin.i18n("DestValue"), null));
      localGWindowLabel.align = 2;
      if (Plugin.builder.conf.iLightDestruction < 0) Plugin.builder.conf.iLightDestruction = 0;
      if (Plugin.builder.conf.iLightDestruction > 255) Plugin.builder.conf.iLightDestruction = 255;
      localGWindowDialogClient.addControl(this.wLight = new GWindowHSliderInt(localGWindowDialogClient, 0, 256, Plugin.builder.conf.iLightDestruction, 13.0F, 1.0F, 10.0F) {
        public boolean notify(int paramInt1, int paramInt2) {
          Plugin.builder.conf.iLightDestruction = pos();
          return super.notify(paramInt1, paramInt2);
        }
        public void created() { this.bSlidingNotify = true;
        }
      });
      this.wLight.toolTip = Plugin.i18n("TIPDestLight");
      this.wLight.resized();

      localGWindowDialogClient.addControl(this.wSize = new GWindowHSliderInt(localGWindowDialogClient, 0, 7, 0, 13.0F, 3.0F, 10.0F) {
        public boolean notify(int paramInt1, int paramInt2) {
          PlMisDestruction.access$402(PlMisDestruction.this, (int)Math.pow(2.0D, pos()));
          return super.notify(paramInt1, paramInt2);
        }
        public void created() { this.bSlidingNotify = true;
        }
      });
      this.wSize.toolTip = Plugin.i18n("TIPDestSize");
      this.wSize.resized();

      localGWindowDialogClient.addControl(this.wValue = new GWindowHSliderInt(localGWindowDialogClient, 0, 256, PlMisDestruction.this.fillValue, 13.0F, 5.0F, 10.0F) {
        public boolean notify(int paramInt1, int paramInt2) {
          PlMisDestruction.access$502(PlMisDestruction.this, pos());
          return super.notify(paramInt1, paramInt2);
        }
        public void created() { this.bSlidingNotify = true;
        }
      });
      this.wValue.toolTip = Plugin.i18n("TIPDestValue");
      this.wValue.resized();
    }
    public void afterCreated() {
      super.afterCreated();
      resized();
      close(false);
    }
    public WDialog() {
      doNew(Plugin.builder.clientWindow, 2.0F, 2.0F, 25.0F, 9.0F, true);
      this.bSizable = false;
    }
  }

  class SelectFilter
    implements ActorFilter
  {
    private Actor _Actor = null;
    private double _Len2;
    private double _maxLen2;

    SelectFilter()
    {
    }

    public void reset(double paramDouble)
    {
      this._Actor = null; this._maxLen2 = paramDouble; } 
    public Actor get() { return this._Actor; } 
    public boolean isUse(Actor paramActor, double paramDouble) {
      if (paramDouble <= this._maxLen2) {
        if ((paramActor instanceof BridgeSegment)) {
          if (Plugin.builder.conf.bViewBridge)
            paramActor = paramActor.getOwner();
          else
            return true;
        }
        if ((paramActor instanceof Bridge)) {
          if (!Plugin.builder.conf.bViewBridge)
            return true;
        } else if (!(paramActor instanceof House)) {
          return true;
        }
        if (this._Actor == null) {
          this._Actor = paramActor;
          this._Len2 = paramDouble;
        }
        else if (paramDouble < this._Len2) {
          this._Actor = paramActor;
          this._Len2 = paramDouble;
        }
      }

      return true;
    }
  }

  class Tile
  {
    public boolean bChanged = true;
    public int x0;
    public int y0;
    public Mat mat;

    public void setPix(int paramInt1, int paramInt2, int paramInt3)
    {
      int i = (paramInt2 * 64 + paramInt1) * 4;
      paramInt3 &= 255;
      PlMisDestruction.this.buf[(i + 0)] = (byte)paramInt3;
      PlMisDestruction.this.buf[(i + 1)] = (byte)(255 - paramInt3);
      PlMisDestruction.this.buf[(i + 3)] = -1;
      PlMisDestruction.this.bufEmpty = false;
      this.bChanged = true;
      PlMisDestruction.this.tilesChanged = true;
    }

    public boolean fillFromStatic(boolean paramBoolean) {
      Statics localStatics = World.cur().statics;
      HashMapInt localHashMapInt = localStatics.allBlocks();
      if ((!PlMisDestruction.this.bufEmpty) && (!paramBoolean)) {
        for (i = 0; i < PlMisDestruction.this.buf.length; i++)
          PlMisDestruction.this.buf[i] = 0;
        PlMisDestruction.this.bufEmpty = true;
      }
      for (int i = 0; i < 64; i++) {
        for (int j = 0; j < 64; j++) {
          int k = localStatics.key(i + this.y0, j + this.x0);
          Statics.Block localBlock = (Statics.Block)localHashMapInt.get(k);
          if (localBlock != null) {
            if (paramBoolean) return true;
            float f = localBlock.getDestruction();
            setPix(j, i, (int)(f * 255.0F));
          }
        }
      }
      return false;
    }

    public void updateImage() {
      if (this.mat == null) {
        this.mat = ((Mat)PlMisDestruction.this.baseTileMat.Clone());
        this.mat.Rename(null);
        this.mat.setLayer(0);
      }
      fillFromStatic(false);
      this.mat.updateImage(64, 64, 3670020, PlMisDestruction.this.buf);

      this.bChanged = false;
    }

    public Tile(int paramInt1, int arg3) {
      this.x0 = paramInt1;
      int i;
      this.y0 = i;
    }
  }
}