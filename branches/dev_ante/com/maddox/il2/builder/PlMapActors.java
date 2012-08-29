package com.maddox.il2.builder;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.TexImage.TexImage;
import com.maddox.gwindow.GFileFilter;
import com.maddox.gwindow.GFileFilterName;
import com.maddox.gwindow.GNotifyListener;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowFileOpen;
import com.maddox.gwindow.GWindowFileSaveAs;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowMenuPopUp;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorSpawn;
import com.maddox.il2.engine.ActorSpawnArg;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.DreamEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.LandConf;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.Statics;
import com.maddox.il2.objects.air.Runaway;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.buildings.Mountain;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Finger;
import com.maddox.rts.HomePath;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.MainWindow;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgMouseListener;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SFSReader;
import com.maddox.rts.SectFile;
import com.maddox.rts.SoftClass;
import com.maddox.rts.Spawn;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapXY16List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class PlMapActors extends Plugin
  implements MsgMouseListener
{
  protected HashMapExt allActors = new HashMapExt();
  protected TexImage landMapT;
  protected TexImage landMapH;
  protected Mat matLabel = null;
  private static final int EXT_TYPES = 4;
  private static final int OFS_BRIDGE = 4;
  private static final int OFS_ROAD = 3;
  private static final int OFS_HEIGHT = 2;
  private static final int OFS_TILE = 1;
  private boolean bTMapChanged;
  private boolean bHMapChanged;
  private boolean bBridgeChanged;
  Type[] type;
  private HashMapExt _saveCls0Map = new HashMapExt();
  private HashMapExt _saveClsMap = new HashMapExt();

  private Point2d p2d = new Point2d();
  private int newCurType;
  private int newCurItem;
  private ActorSpawnArg spawnArg = new ActorSpawnArg();

  private int fillHeight = 0;

  private Point3d _startFill = new Point3d();
  private Point3d _endFill = new Point3d();
  private Point3d _stepFill = new Point3d();
  private int squareTile = 1;

  private ArrayList _clipSpawns = new ArrayList();
  private ArrayList _clipAirdroms = new ArrayList();
  private ArrayList _clipLoc = new ArrayList();
  private Point3d _clipP0 = new Point3d();
  private GWindowFileOpen dlgPasteFrom;
  private int startComboBox1;
  ViewItem[] viewType;
  GWindowMenuItem viewBridge;
  GWindowMenuItem viewRunaway;
  HashMap viewClassFingers = new HashMap();
  GWindowMenuItem mSave;
  GWindowMenuItem mLoad;
  GWindowMenuItem mSaveSpawn;
  GWindowMenuItem mLoadSpawn;
  GWindowMenuItem mSaveAs;
  GWindowMenuItem mLoadAs;
  GWindowMenuItem mCreateBridges;
  private GWindowFileOpen dlgLoadAs;
  private GWindowFileOpen dlgLoadSpawn;
  private GWindowFileSaveAs dlgSaveAs;
  private GWindowFileSaveAs dlgSaveSpawn;
  private int newIndxBridge = 10000;
  private Bridge curBridge;
  private int indxBridge;
  private Point3d _clearRoadP0 = new Point3d();
  private Point3d _clearRoadP1 = new Point3d();
  private ArrayList _clearRoadArray = new ArrayList();
  private float[] _clearRoadBound = new float[6];
  private ClearRoadFilter _clearRoadFilter = new ClearRoadFilter(null);
  private GWindowFileOpen dlgLoadPattern;
  private GWindowFileOpen dlgRemovePattern;
  private double PIXEL = 200.0D;
  private double PIXELD2 = 100.0D;
  private int TILE = 8;
  private int TILEMASK = 7;
  private double QUAD = this.PIXEL * this.TILE;
  private int _changedPatternType;
  Point3f _changeOffsetXY = new Point3f();
  Loc _changeLoc = new Loc();

  private static final String getFullClassName(Actor paramActor)
  {
    return (paramActor instanceof SoftClass) ? ((SoftClass)paramActor).fullClassName() : paramActor.getClass().getName();
  }

  private static final int getFingerOfFullClassName(Actor paramActor)
  {
    return Finger.Int((paramActor instanceof SoftClass) ? ((SoftClass)paramActor).fullClassName() : paramActor.getClass().getName());
  }

  private String staticFileName()
  {
    String str1 = "maps/" + PlMapLoad.mapFileName();
    SectFile localSectFile = new SectFile(str1);
    int i = localSectFile.sectionIndex("static");
    if ((i >= 0) && (localSectFile.vars(i) > 0)) {
      String str2 = localSectFile.var(i, 0);
      return HomePath.concatNames(str1, str2);
    }
    return null;
  }

  public void mapLoaded() {
    deleteAll();
    Plugin.builder.deleteAll();
    Engine.drawEnv().resetGameClear();
    Engine.drawEnv().resetGameCreate();
    if (World.cur().statics != null)
      World.cur().statics.resetGame();
    if (!Plugin.builder.isLoadedLandscape()) return;
    load(staticFileName());

    Statics.trim();
    Engine.dreamEnv().resetGlobalListener(World.cur().statics);
  }

  public void deleteAll()
  {
    Map.Entry localEntry = this.allActors.nextEntry(null);
    while (localEntry != null) {
      Actor localActor = (Actor)localEntry.getKey();
      if (Actor.isValid(localActor))
        localActor.destroy();
      localEntry = this.allActors.nextEntry(localEntry);
    }
    this.allActors.clear();
  }

  public void loadAs(String paramString)
  {
    load(paramString, false, false);
  }

  public void load(String paramString) {
    load(paramString, true, false);
  }

  public void load(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    int i = 0;
    if (paramBoolean2) {
      this._clipSpawns.clear();
      this._clipAirdroms.clear();
      this._clipLoc.clear();
      this._clipP0.jdField_x_of_type_Double = (this._clipP0.jdField_y_of_type_Double = this._clipP0.jdField_z_of_type_Double = 0.0D);
    } else {
      this.landMapT = new TexImage();
      this.landMapH = new TexImage();
      try {
        this.landMapT.LoadTGA("maps/" + PlMapLoad.mapDirName() + "/" + World.land().config.typeMap);
        this.landMapH.LoadTGA("maps/" + PlMapLoad.mapDirName() + "/" + World.land().config.heightMap);
      }
      catch (Exception localException1)
      {
      }
      this.bTMapChanged = false;
      this.bBridgeChanged = false;
      if (paramString == null) {
        if ((!"Test".equals(PlMapLoad.mapKeyName())) && (!paramBoolean1))
          PlMapLoad.bridgesCreate(this.landMapT);
        return;
      }
    }
    Loc localLoc1 = new Loc();
    try {
      DataInputStream localDataInputStream = new DataInputStream(new SFSInputStream(paramString));
      int j = 1;
      int k = localDataInputStream.readInt();
      if (k == -65535) {
        j = 0;
        k = localDataInputStream.readInt();
      }
      int m = 0;
      float f5;
      for (int n = 0; n < k; n++) {
        int i1 = localDataInputStream.readInt();
        int i3 = localDataInputStream.readInt();
        int i5 = localDataInputStream.readInt();
        int i8 = localDataInputStream.readInt();
        int i10 = localDataInputStream.readInt();
        f5 = localDataInputStream.readFloat();

        if ((paramBoolean1) && (!paramBoolean2)) {
          Bridge localBridge = new Bridge(m++, i10, i1, i3, i5, i8, f5);
          Property.set(localBridge, "builderSpawn", "");
          PlMapLoad.bridgeActors.add(localBridge);
        }
      }
      float f3;
      float f4;
      Object localObject1;
      int i7;
      int i12;
      if (j != 0) {
        k = localDataInputStream.readInt();
        if (k > 0) {
          while (k-- > 0) {
            String str2 = localDataInputStream.readUTF();
            Spawn.get_WithSoftClass(str2);
          }
          k = localDataInputStream.readInt();
          while (k-- > 0) {
            int i2 = localDataInputStream.readInt();
            float f1 = localDataInputStream.readFloat();
            float f2 = localDataInputStream.readFloat();
            f3 = localDataInputStream.readFloat();
            f4 = localDataInputStream.readFloat();
            f5 = localDataInputStream.readFloat();
            float f6 = localDataInputStream.readFloat();
            localLoc1.set(f1, f2, f3, f4, f5, f6);
            ActorSpawn localActorSpawn = (ActorSpawn)Spawn.get(i2);
            if (localActorSpawn != null)
              if (paramBoolean2) {
                Loc localLoc3 = new Loc(localLoc1);
                this._clipSpawns.add(localActorSpawn);
                this._clipLoc.add(localLoc3);
                this._clipP0.add(localLoc3.getPoint());
                i++;
              } else {
                insert(localActorSpawn, localLoc1, false);
              }
          }
        }
      }
      else
      {
        k = localDataInputStream.readInt();
        localObject1 = null;
        int i4;
        if (k > 0) {
          localObject1 = new ActorSpawn[k];
          for (i4 = 0; i4 < k; i4++) {
            String str3 = localDataInputStream.readUTF();
            localObject1[i4] = ((ActorSpawn)Spawn.get_WithSoftClass(str3));
          }
          k = localDataInputStream.readInt();
          while (k-- > 0) {
            int i6 = localDataInputStream.readInt();
            f3 = localDataInputStream.readFloat();
            f4 = localDataInputStream.readFloat();
            f5 = localDataInputStream.readFloat();
            localLoc1.set(f3, f4, 0.0D, f5, 0.0F, 0.0F);
            if ((i6 < localObject1.length) && (localObject1[i6] != null)) {
              if (paramBoolean2) {
                Loc localLoc2 = new Loc(localLoc1);
                this._clipSpawns.add(localObject1[i6]);
                this._clipLoc.add(localLoc2);
                this._clipP0.add(localLoc2.getPoint());
                i++;
              } else {
                insert(localObject1[i6], localLoc1, false);
              }
            }
          }
        }

        k = localDataInputStream.readInt();
        if (k > 0) {
          localObject1 = new ActorSpawn[k];
          for (i4 = 0; i4 < k; i4++) {
            String str4 = localDataInputStream.readUTF();
            localObject1[i4] = ((ActorSpawn)Spawn.get_WithSoftClass(str4));
          }
          i7 = localDataInputStream.readInt();
          while (i7-- > 0) {
            int i9 = localDataInputStream.readInt();
            f4 = (i9 & 0xFFFF) * 200.0F;
            f5 = (i9 >> 16 & 0xFFFF) * 200.0F;
            i12 = localDataInputStream.readInt();
            while (i12-- > 0) {
              int i13 = localDataInputStream.readInt();
              int i14 = localDataInputStream.readInt();
              int i15 = i13 & 0x7FFF;
              if ((i15 < localObject1.length) && (localObject1[i15] != null)) {
                int i16 = (short)(i13 >> 16);
                int i17 = (short)(i14 & 0xFFFF);
                int i18 = (short)(i14 >> 16 & 0xFFFF);
                float f7 = i16 * 360.0F / 32000.0F;
                float f8 = i17 * 200.0F / 32000.0F + f4;
                float f9 = i18 * 200.0F / 32000.0F + f5;
                localLoc1.set(f8, f9, 0.0D, -f7, 0.0F, 0.0F);
                if (paramBoolean2) {
                  Loc localLoc4 = new Loc(localLoc1);
                  this._clipSpawns.add(localObject1[i15]);
                  this._clipLoc.add(localLoc4);
                  this._clipP0.add(localLoc4.getPoint());
                  i++;
                } else {
                  insert(localObject1[i15], localLoc1, false);
                }
              }
            }
          }
        }
      }

      if (localDataInputStream.available() > 0)
      {
        localObject1 = (PlMapAirdrome)Plugin.getPlugin("MapAirdrome");
        Point3d localPoint3d = new Point3d();
        for (i7 = 0; i7 < 3; i7++) {
          k = localDataInputStream.readInt();
          while (k-- > 0) {
            PathAirdrome localPathAirdrome = new PathAirdrome(Plugin.builder.pathes, i7);
            Property.set(localPathAirdrome, "builderPlugin", localObject1);
            localPathAirdrome.drawing(((PlMapAirdrome)localObject1).mView.jdField_bChecked_of_type_Boolean);
            Object localObject2 = null;
            int i11 = localDataInputStream.readInt();
            for (i12 = 0; i12 < i11; i12++) {
              localPoint3d.set(localDataInputStream.readFloat(), localDataInputStream.readFloat(), 0.0D);
              localPoint3d.jdField_z_of_type_Double = (Engine.land().HQ(localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double) + 0.2D);
              localObject2 = new PAirdrome(localPathAirdrome, (PPoint)localObject2, localPoint3d, i7);
              Property.set(localObject2, "builderPlugin", localObject1);
              Property.set(localObject2, "builderSpawn", "");
              if (paramBoolean2) {
                this._clipP0.add(localPoint3d);
                i++;
              }
            }
            if (paramBoolean2) {
              this._clipAirdroms.add(PathAirdrome.toSpawnString(localPathAirdrome));
              localPathAirdrome.destroy();
            }
          }
        }
      }
      localDataInputStream.close();
      if (!paramBoolean2) {
        viewUpdate();
        Engine.drawEnv().staticTrimToSize();
      }
    } catch (Exception localException2) {
      String str1 = "Actors load from '" + paramString + "' FAILED: " + localException2.getMessage();
      System.out.println(str1);
      localException2.printStackTrace();
      Plugin.builder.tip(str1);
    }
    if ((paramBoolean2) && (i > 0)) {
      this._clipP0.jdField_x_of_type_Double /= i; this._clipP0.jdField_y_of_type_Double /= i; this._clipP0.jdField_z_of_type_Double /= i;
      paste();
    }
  }

  public void save() {
    save(staticFileName());
    if ((this.bTMapChanged) || (this.bBridgeChanged)) {
      int i = PlMapLoad.bridgeActors.size();
      TexImage localTexImage = this.landMapT;
      for (int j = 0; j < i; j++) {
        localObject = (Bridge)PlMapLoad.bridgeActors.get(j);
        int k = ((Bridge)localObject).__x1;
        int m = ((Bridge)localObject).__y1;
        int n = ((Bridge)localObject).__x2;
        int i1 = ((Bridge)localObject).__y2;
        int i2 = n - k;
        if (i2 < 0) i2 = -1;
        if (i2 > 0) i2 = 1;
        int i3 = i1 - m;
        if (i3 < 0) i3 = -1;
        if (i3 > 0) i3 = 1;
        while (true)
        {
          localTexImage.I(k, m, localTexImage.intI(k, m) & 0xFFFFFF1F);
          if ((k == n) && (m == i1))
            break;
          k += i2;
          m += i3;
        }
      }
      Object localObject = "maps/" + PlMapLoad.mapDirName() + "/" + World.land().config.typeMap;
      try {
        localTexImage.SaveTGA((String)localObject);
        this.bTMapChanged = false;
      } catch (Exception localException2) {
        String str3 = "Type map '" + (String)localObject + "' save FAILED: " + localException2.getMessage();
        System.out.println(str3); Plugin.builder.tip(str3);
      }
    }
    if (this.bHMapChanged) {
      String str1 = "maps/" + PlMapLoad.mapDirName() + "/" + World.land().config.heightMap;
      try {
        this.landMapH.SaveTGA(str1);
        this.bHMapChanged = false;
      } catch (Exception localException1) {
        String str2 = "Height map '" + str1 + "' save FAILED: " + localException1.getMessage();
        System.out.println(str2); Plugin.builder.tip(str2);
      }
    }
    Plugin.doSave((SectFile)null);
  }

  public void saveAs(String paramString) {
    save(paramString, false);
  }
  public void save(String paramString) {
    save(paramString, true);
  }
  public void save(String paramString, boolean paramBoolean) {
    if (paramString == null)
      return;
    try {
      DataOutputStream localDataOutputStream = new DataOutputStream(new FileOutputStream(paramString));

      localDataOutputStream.writeInt(-65535);

      int i = PlMapLoad.bridgeActors.size();
      if ((i == 0) || (!paramBoolean)) {
        localDataOutputStream.writeInt(0);
      } else {
        localDataOutputStream.writeInt(i);
        for (int j = 0; j < i; j++) {
          Bridge localBridge = (Bridge)PlMapLoad.bridgeActors.get(j);
          localDataOutputStream.writeInt(localBridge.__x1);
          localDataOutputStream.writeInt(localBridge.__y1);
          localDataOutputStream.writeInt(localBridge.__x2);
          localDataOutputStream.writeInt(localBridge.__y2);
          localDataOutputStream.writeInt(localBridge.__type);
          localDataOutputStream.writeFloat(localBridge.__offsetK);
        }

      }

      this._saveCls0Map.clear();
      this._saveClsMap.clear();
      HashMapXY16List localHashMapXY16List = new HashMapXY16List(7);
      int k = 0;
      int m = 0;
      int n = 0;
      Map.Entry localEntry = this.allActors.nextEntry(null);
      String str2;
      Object localObject2;
      while (localEntry != null) {
        localObject1 = (Actor)localEntry.getKey();
        if (Actor.isValid((Actor)localObject1)) {
          str2 = getFullClassName((Actor)localObject1);
          if (((localObject1 instanceof Runaway)) || ((localObject1 instanceof Mountain)))
          {
            if (!this._saveCls0Map.containsKey(str2)) {
              this._saveCls0Map.put(str2, new Integer(k));
              k++;
            }
            m++;
          } else {
            if (!this._saveClsMap.containsKey(str2)) {
              this._saveClsMap.put(str2, new Integer(n));
              n++;
            }
            localObject2 = ((Actor)localObject1).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
            localHashMapXY16List.put((int)(((Point3d)localObject2).jdField_y_of_type_Double / 200.0D), (int)(((Point3d)localObject2).jdField_x_of_type_Double / 200.0D), localObject1);
          }
        }
        localEntry = this.allActors.nextEntry(localEntry);
      }
      Object localObject3;
      int i5;
      if (k > 0) {
        localObject1 = new String[k];
        localEntry = this._saveCls0Map.nextEntry(null);
        while (localEntry != null) {
          str2 = (String)localEntry.getKey();
          localObject2 = (Integer)localEntry.getValue();
          localObject1[localObject2.intValue()] = str2;
          localEntry = this._saveCls0Map.nextEntry(localEntry);
        }
        localDataOutputStream.writeInt(k);
        for (int i1 = 0; i1 < k; i1++)
          localDataOutputStream.writeUTF(localObject1[i1]);
        localDataOutputStream.writeInt(m);
        localEntry = this.allActors.nextEntry(null);
        while (localEntry != null) {
          localObject2 = (Actor)localEntry.getKey();
          if (Actor.isValid((Actor)localObject2)) {
            localObject3 = getFullClassName((Actor)localObject2);
            if (this._saveCls0Map.containsKey(localObject3)) {
              i5 = ((Integer)this._saveCls0Map.get(localObject3)).intValue();
              Point3d localPoint3d1 = ((Actor)localObject2).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
              Orient localOrient1 = ((Actor)localObject2).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient();
              localDataOutputStream.writeInt(i5);
              localDataOutputStream.writeFloat((float)localPoint3d1.jdField_x_of_type_Double);
              localDataOutputStream.writeFloat((float)localPoint3d1.jdField_y_of_type_Double);
              localDataOutputStream.writeFloat(localOrient1.azimut());
            }
          }
          localEntry = this.allActors.nextEntry(localEntry);
        }
      } else {
        localDataOutputStream.writeInt(0);
      }
      Object localObject4;
      if (n > 0) {
        localObject1 = new String[n];
        localEntry = this._saveClsMap.nextEntry(null);
        while (localEntry != null) {
          String str3 = (String)localEntry.getKey();
          localObject2 = (Integer)localEntry.getValue();
          localObject1[localObject2.intValue()] = str3;
          localEntry = this._saveClsMap.nextEntry(localEntry);
        }
        localDataOutputStream.writeInt(n);
        for (int i2 = 0; i2 < n; i2++) {
          localDataOutputStream.writeUTF(localObject1[i2]);
        }
        i3 = 0;
        localObject3 = localHashMapXY16List.allKeys();
        localDataOutputStream.writeInt(localObject3.length);
        for (i5 = 0; i5 < localObject3.length; i5++) {
          int i7 = localHashMapXY16List.key2x(localObject3[i5]);
          int i8 = localHashMapXY16List.key2y(localObject3[i5]);
          float f1 = i7 * 200.0F;
          float f2 = i8 * 200.0F;
          localDataOutputStream.writeInt(i8 << 16 | i7);
          localObject4 = localHashMapXY16List.get(i8, i7);
          int i11 = ((List)localObject4).size();
          i3 += i11;
          localDataOutputStream.writeInt(i11);
          for (int i12 = 0; i12 < i11; i12++) {
            Actor localActor2 = (Actor)((List)localObject4).get(i12);
            String str4 = getFullClassName(localActor2);
            int i13 = ((Integer)this._saveClsMap.get(str4)).intValue();
            Point3d localPoint3d3 = localActor2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
            Orient localOrient2 = localActor2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient();
            int i14 = i13 & 0x7FFF;
            float f3 = localOrient2.getYaw() % 360.0F;
            i14 |= (int)(f3 * 32000.0F / 360.0F) << 16;
            int i15 = (int)((localPoint3d3.jdField_x_of_type_Double - f1) * 32000.0D / 200.0D) & 0xFFFF | (int)((localPoint3d3.jdField_y_of_type_Double - f2) * 32000.0D / 200.0D) << 16;

            localDataOutputStream.writeInt(i14);
            localDataOutputStream.writeInt(i15);
          }
        }
        System.out.println("Saved actors: " + i3 + " blocks: " + localObject3.length);
      } else {
        localDataOutputStream.writeInt(0);
      }

      localHashMapXY16List.clear();
      this._saveCls0Map.clear();
      this._saveClsMap.clear();

      Object localObject1 = { 0, 0, 0 };
      Object[] arrayOfObject = Plugin.builder.pathes.getOwnerAttached();
      for (int i3 = 0; i3 < arrayOfObject.length; i3++) {
        localObject3 = (Actor)arrayOfObject[i3];
        if (localObject3 == null) break;
        if ((localObject3 instanceof PathAirdrome)) {
          PathAirdrome localPathAirdrome1 = (PathAirdrome)localObject3;
          localObject1[localPathAirdrome1._iType] += 1;
        }
      }
      for (int i4 = 0; i4 < 3; i4++) {
        localDataOutputStream.writeInt(localObject1[i4]);
        arrayOfObject = Plugin.builder.pathes.getOwnerAttached();
        for (int i6 = 0; i6 < arrayOfObject.length; i6++) {
          Actor localActor1 = (Actor)arrayOfObject[i6];
          if (localActor1 == null) break;
          if ((localActor1 instanceof PathAirdrome)) {
            PathAirdrome localPathAirdrome2 = (PathAirdrome)localActor1;
            if (localPathAirdrome2._iType == i4) {
              int i9 = localPathAirdrome2.points();
              localDataOutputStream.writeInt(i9);
              for (int i10 = 0; i10 < i9; i10++) {
                localObject4 = localPathAirdrome2.point(i10);
                Point3d localPoint3d2 = ((PPoint)localObject4).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
                localDataOutputStream.writeFloat((float)localPoint3d2.jdField_x_of_type_Double);
                localDataOutputStream.writeFloat((float)localPoint3d2.jdField_y_of_type_Double);
              }
            }
          }
        }
      }

      localDataOutputStream.close();
    } catch (Exception localException) {
      String str1 = "Actors save to '" + paramString + "' FAILED: " + localException.getMessage();
      System.out.println(str1); Plugin.builder.tip(str1);
    }
  }

  public void loadSpawn(String paramString)
  {
    loadSpawnFrom(paramString);

    viewUpdate();
    Engine.drawEnv().staticTrimToSize();
  }
  public void loadSpawnFrom(SectFile paramSectFile, String paramString) {
    int i = paramSectFile.sectionIndex(paramString);
    if (i < 0)
      return;
    int j = paramSectFile.vars(i);
    for (int k = 0; k < j; k++) {
      String str = paramSectFile.line(i, k);
      Actor localActor = (Actor)CmdEnv.top().exec(str);
      if (Actor.isValid(localActor)) {
        IconDraw.create(localActor);
        if (localActor.icon == null)
          localActor.icon = IconDraw.get("icons/unknown.mat");
        Property.set(localActor, "builderSpawn", "");
        Property.set(localActor, "builderPlugin", this);
        this.allActors.put(localActor, null);
      }
    }
  }

  public void loadSpawnFrom(String paramString) {
    try {
      BufferedReader localBufferedReader = new BufferedReader(new SFSReader(paramString));
      String str = "";
      while (true)
      {
        str = localBufferedReader.readLine();
        if (str == null)
          break;
        Actor localActor = (Actor)CmdEnv.top().exec(str);
        if ((Actor.isValid(localActor)) && (!(localActor instanceof PathAirdrome))) {
          IconDraw.create(localActor);
          if (localActor.icon == null)
            localActor.icon = IconDraw.get("icons/unknown.mat");
          Property.set(localActor, "builderSpawn", "");
          Property.set(localActor, "builderPlugin", this);
          this.allActors.put(localActor, null);
        }
      }
      localBufferedReader.close();
    } catch (Exception localException) {
      System.out.println("Load '" + paramString + "'failed: " + localException.getMessage());
    }
  }

  public void saveSpawn(String paramString)
  {
    saveSpawnTo(paramString);
  }

  public void saveSpawnTo(SectFile paramSectFile, String paramString)
  {
    int i = paramSectFile.sectionIndex(paramString);
    if (i < 0)
      i = paramSectFile.sectionAdd(paramString);
    else {
      paramSectFile.sectionClear(i);
    }
    Item[] arrayOfItem = null;
    for (int j = 0; j < this.type.length; j++) {
      if (paramString.equals(this.type[j].name)) {
        arrayOfItem = this.type[j].item;
        break;
      }
    }
    if (arrayOfItem == null)
      return;
    Map.Entry localEntry = this.allActors.nextEntry(null);
    while (localEntry != null) {
      Actor localActor = (Actor)localEntry.getKey();
      if (Actor.isValid(localActor)) {
        int k = getFingerOfFullClassName(localActor);
        for (int m = 0; m < arrayOfItem.length; m++) {
          if (k == arrayOfItem[m].fingerOfFullClassName) {
            Point3d localPoint3d = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
            Orient localOrient = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient();
            paramSectFile.lineAdd(i, "spawn", arrayOfItem[m].fullClassName + " POSP " + (float)localPoint3d.jdField_x_of_type_Double + " " + (float)localPoint3d.jdField_y_of_type_Double + " " + (float)localPoint3d.jdField_z_of_type_Double + " POSO " + localOrient.azimut() + " " + localOrient.tangage() + " " + localOrient.kren());

            break;
          }
        }
      }
      localEntry = this.allActors.nextEntry(localEntry);
    }
  }

  public void saveSpawnTo(String paramString) {
    try {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(paramString)));
      localObject1 = this.allActors.nextEntry(null);
      Object localObject3;
      while (localObject1 != null) {
        localObject2 = (Actor)((Map.Entry)localObject1).getKey();
        if (Actor.isValid((Actor)localObject2)) {
          Point3d localPoint3d = ((Actor)localObject2).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
          localObject3 = ((Actor)localObject2).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsOrient();
          localPrintWriter.println("spawn " + getFullClassName((Actor)localObject2) + " POSP " + (float)localPoint3d.jdField_x_of_type_Double + " " + (float)localPoint3d.jdField_y_of_type_Double + " " + (float)localPoint3d.jdField_z_of_type_Double + " POSO " + ((Orient)localObject3).azimut() + " " + ((Orient)localObject3).tangage() + " " + ((Orient)localObject3).kren());
        }

        localObject1 = this.allActors.nextEntry((Map.Entry)localObject1);
      }
      Object localObject2 = Plugin.builder.pathes.getOwnerAttached();
      for (int i = 0; i < localObject2.length; i++) {
        localObject3 = (Actor)localObject2[i];
        if (localObject3 == null) break;
        if ((localObject3 instanceof PathAirdrome)) {
          PathAirdrome localPathAirdrome = (PathAirdrome)localObject3;
          localPrintWriter.println(PathAirdrome.toSpawnString(localPathAirdrome));
        }
      }
      localPrintWriter.close();
    } catch (Exception localException) {
      Object localObject1 = "Actors save as spawns to '" + paramString + "' FAILED: " + localException.getMessage();
      System.out.println((String)localObject1); Plugin.builder.tip((String)localObject1);
    }
  }

  public void renderMap2D()
  {
    Actor localActor2;
    if (!Plugin.builder.isFreeView()) {
      Actor localActor1 = Plugin.builder.selectedActor();
      Actor[] arrayOfActor = Plugin.builder.selectedActors();
      IconDraw.setColor(255, 0, 0, 255);
      for (int k = 0; k < arrayOfActor.length; k++) {
        localActor2 = arrayOfActor[k];
        if (localActor2 == null) break;
        if (Plugin.builder.project2d(localActor2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint(), this.p2d)) {
          if (localActor2 == localActor1) {
            IconDraw.setColor(255, 255, 0, 255);
            IconDraw.render(localActor2, this.p2d.jdField_x_of_type_Double, this.p2d.jdField_y_of_type_Double);
            IconDraw.setColor(255, 0, 0, 255);
          } else {
            IconDraw.render(localActor2, this.p2d.jdField_x_of_type_Double, this.p2d.jdField_y_of_type_Double);
          }
        }
      }
    }
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    if ((i < 0) || (i >= this.type.length) || (j < 0) || (j >= this.type[i].item.length))
    {
      return;
    }if ((i == this.type.length - 2) && (this.matLabel != null)) {
      Point3d localPoint3d = null;
      if (Plugin.builder.isFreeView()) {
        localActor2 = Plugin.builder.selectedActor();
        if (!Actor.isValid(localActor2))
          return;
        localPoint3d = localActor2.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
      } else {
        localPoint3d = Plugin.builder.posScreenToLand(Plugin.builder.mousePosX, Plugin.builder.mousePosY, 0.0D, 0.1D);
      }
      int m = World.land().WORLD2PIXX(localPoint3d.jdField_x_of_type_Double);
      int n = World.land().WORLD2PIXY(localPoint3d.jdField_y_of_type_Double);
      int i1 = this.squareTile;
      i1 |= 1;
      int i2 = m - i1 / 2;
      int i3 = n - i1 / 2;
      World.land(); int i4 = Landscape.getSizeXpix();
      World.land(); int i5 = Landscape.getSizeYpix();
      IconDraw.setColor(255, 255, 255, 255);
      for (int i6 = i3; i6 < i3 + i1; i6++)
        for (int i7 = i2; i7 < i2 + i1; i7++)
          if ((i6 >= 0) && (i6 < i5) && (i7 >= 0) && (i7 < i4)) {
            double d1 = World.land().PIX2WORLDX(i7);
            double d2 = World.land().PIX2WORLDY(i6);
            double d3 = World.land().HQ(d1, d2);
            Plugin.builder.project2d(d1, d2, d3, this.p2d);
            IconDraw.render(this.matLabel, this.p2d.jdField_x_of_type_Double, this.p2d.jdField_y_of_type_Double);
          }
    }
  }

  public void changeType()
  {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    if (i == this.type.length - 4) {
      this.newCurType = i;
      if (!setCurBridge()) return;
      localObject = this.curBridge;
      int k = 0;
      if (j == 0) k = 64;
      if (j == 1) k = 32;
      if (j == 2) k = 128;
      changeCurBridge(((Bridge)localObject).__indx, k, ((Bridge)localObject).__x1, ((Bridge)localObject).__y1, ((Bridge)localObject).__x2, ((Bridge)localObject).__y2, ((Bridge)localObject).__offsetK);
      this.newCurType = i;
      this.newCurItem = j;
      return;
    }
    if ((i < 0) || (i >= this.type.length - 4))
      return;
    Object localObject = Plugin.builder.selectedActor();
    Loc localLoc = ((Actor)localObject).jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs();
    insert(this.type[i].item[j].spawn_, localLoc, true);
    if (Plugin.builder.selectedActor() != localObject) {
      this.allActors.remove(localObject);
      ((Actor)localObject).destroy();
    }
    this.newCurType = i;
    this.newCurItem = j;
  }

  public void changeType(boolean paramBoolean1, boolean paramBoolean2) {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    if (i == this.type.length - 4) {
      this.newCurType = i;
      if (paramBoolean2) bridgeType(paramBoolean1); else
        bridgeLen(paramBoolean1);
      return;
    }

    int k = getFingerOfFullClassName(Plugin.builder.selectedActor());
    if ((i < 0) || (i >= this.type.length - 4))
      i = 0;
    if ((j < 0) || (j >= this.type[i].item.length) || (this.type[i].item[j].fingerOfFullClassName != k))
    {
      int n = 0;
      for (int m = 0; m < this.type.length; m++) {
        for (n = 0; n < this.type[m].item.length; n++) {
          if (this.type[m].item[n].fingerOfFullClassName == k)
            break;
        }
        if (n != this.type[m].item.length)
          break;
      }
      if (m != this.type.length) {
        i = m;
        j = n;
      } else {
        return;
      }
    }
    if (paramBoolean2) {
      if (paramBoolean1) {
        i--;
        if (i < 0)
          i = 0;
      } else {
        i++;
        if (i == this.type.length - 4)
          i = this.type.length - 1 - 4;
      }
      j = 0;
    }
    else if (paramBoolean1) {
      j--;
      if (j < 0)
        j = 0;
    } else {
      j++;
      if (j == this.type[i].item.length) {
        j = this.type[i].item.length - 1;
      }
    }

    Actor localActor = Plugin.builder.selectedActor();
    Loc localLoc = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs();
    insert(this.type[i].item[j].spawn_, localLoc, true);
    if (Plugin.builder.selectedActor() != localActor) {
      localActor.destroy();
    }
    this.newCurType = i;
    this.newCurItem = j;

    fillComboBox2(this.newCurType, this.newCurItem);
  }

  private Actor insert(ActorSpawn paramActorSpawn, Loc paramLoc, boolean paramBoolean)
  {
    this.spawnArg.clear();
    this.spawnArg.point = paramLoc.getPoint();
    this.spawnArg.orient = paramLoc.getOrient();
    try {
      Actor localActor = paramActorSpawn.actorSpawn(this.spawnArg);
      IconDraw.create(localActor);
      if (localActor.icon == null)
        localActor.icon = IconDraw.get("icons/unknown.mat");
      Plugin.builder.align(localActor);
      Property.set(localActor, "builderSpawn", "");
      Property.set(localActor, "builderPlugin", this);
      this.allActors.put(localActor, null);
      if (paramBoolean)
        Plugin.builder.setSelected(localActor);
      return localActor;
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
    return null;
  }

  public void delete(Actor paramActor)
  {
    this.allActors.remove(paramActor);
    paramActor.destroy();
  }

  public void insert(Loc paramLoc, boolean paramBoolean) {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    if ((i < 0) || (i >= this.type.length) || (j < 0) || (j >= this.type[i].item.length))
    {
      return;
    }if (i == this.type.length - 4)
    {
      bridgeCreate(paramLoc);
    } else if (i == this.type.length - 3)
    {
      this.bTMapChanged = true;
      int k = World.land().WORLD2PIXX(paramLoc.getPoint().jdField_x_of_type_Double);
      int m = World.land().WORLD2PIXY(paramLoc.getPoint().jdField_y_of_type_Double);
      int n = 0;
      if (j == 0) n = 64;
      if (j == 1) n = 32;
      if (j == 2) n = 128;
      int i1 = this.landMapT.intI(k, m) | n;
      this.landMapT.I(k, m, i1);
      World.land(); Landscape.setPixelMapT(k, m, i1);
    }
    else if (i == this.type.length - 2)
    {
      if (j == 0) peekHeight(paramLoc.getPoint());
      else if (j == 1) setHeight(paramLoc.getPoint());
      else if (j == 2) blurHeight5(paramLoc.getPoint(), 1); 
    }
    else if (i == this.type.length - 1)
    {
      changeTile(paramLoc.getPoint(), j);
    } else {
      ActorSpawn localActorSpawn = this.type[i].item[j].spawn_;
      insert(localActorSpawn, paramLoc, paramBoolean);
    }
  }

  private void peekHeight(Point3d paramPoint3d)
  {
    int i = World.land().WORLD2PIXX(paramPoint3d.jdField_x_of_type_Double);
    int j = World.land().WORLD2PIXY(paramPoint3d.jdField_y_of_type_Double);
    this.fillHeight = this.landMapH.intI(i, j);
    Plugin.builder.tip("Peek Height code = " + this.fillHeight);
  }
  private void fillHeight(Point3d paramPoint3d, int paramInt) {
    int i = World.land().WORLD2PIXX(paramPoint3d.jdField_x_of_type_Double);
    int j = World.land().WORLD2PIXY(paramPoint3d.jdField_y_of_type_Double);
    paramInt |= 1;
    int k = i - paramInt / 2;
    int m = j - paramInt / 2;
    World.land(); int n = Landscape.getSizeXpix();
    World.land(); int i1 = Landscape.getSizeYpix();
    for (int i2 = m; i2 < m + paramInt; i2++)
      for (int i3 = k; i3 < k + paramInt; i3++)
        if ((i2 >= 0) && (i2 < i1) && (i3 >= 0) && (i3 < n))
          setHeight(i3, i2); 
  }

  private void setHeight(int paramInt1, int paramInt2) {
    int i = this.landMapT.intI(paramInt1, paramInt2);

    this.bHMapChanged = true;

    this.landMapH.I(paramInt1, paramInt2, this.fillHeight);
    World.land(); Landscape.setPixelMapH(paramInt1, paramInt2, this.fillHeight);
  }

  private void setHeight(Point3d paramPoint3d) {
    int i = World.land().WORLD2PIXX(paramPoint3d.jdField_x_of_type_Double);
    int j = World.land().WORLD2PIXY(paramPoint3d.jdField_y_of_type_Double);
    setHeight(i, j);
  }
  private void incHeight(int paramInt1, int paramInt2, int paramInt3) {
    int i = this.landMapT.intI(paramInt1, paramInt2);

    this.bHMapChanged = true;

    int j = this.landMapH.intI(paramInt1, paramInt2) + paramInt3;
    if (j < 0) j = 0;
    if (j > 255) j = 255;
    this.landMapH.I(paramInt1, paramInt2, j);
    World.land(); Landscape.setPixelMapH(paramInt1, paramInt2, j);
  }

  private void incHeight(Point3d paramPoint3d, int paramInt) {
    int i = World.land().WORLD2PIXX(paramPoint3d.jdField_x_of_type_Double);
    int j = World.land().WORLD2PIXY(paramPoint3d.jdField_y_of_type_Double);
    incHeight(i, j, paramInt);
  }
  private void incHeight(Point3d paramPoint3d, int paramInt1, int paramInt2) {
    int i = World.land().WORLD2PIXX(paramPoint3d.jdField_x_of_type_Double);
    int j = World.land().WORLD2PIXY(paramPoint3d.jdField_y_of_type_Double);
    paramInt1 |= 1;
    int k = i - paramInt1 / 2;
    int m = j - paramInt1 / 2;
    World.land(); int n = Landscape.getSizeXpix();
    World.land(); int i1 = Landscape.getSizeYpix();
    for (int i2 = m; i2 < m + paramInt1; i2++)
      for (int i3 = k; i3 < k + paramInt1; i3++)
        if ((i2 >= 0) && (i2 < i1) && (i3 >= 0) && (i3 < n))
          incHeight(i3, i2, paramInt2);
  }

  public void msgMouseMove(int paramInt1, int paramInt2, int paramInt3) {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    if ((i < 0) || (i >= this.type.length) || (j < 0) || (j >= this.type[i].item.length))
    {
      return;
    }if (i == this.type.length - 2)
    {
      Actor localActor;
      Point3d localPoint3d;
      if (paramInt3 == 0) {
        if (HotKeyCmdEnv.env(Builder.envName).get("fill").isActive()) {
          localActor = Plugin.builder.selectedActor();
          if (Actor.isValid(localActor)) {
            localPoint3d = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
            peekHeight(localPoint3d);
            fillHeight(localPoint3d, this.squareTile);
          }
        }
        return;
      }
      if (Plugin.builder.isFreeView()) {
        localActor = Plugin.builder.selectedActor();
        if (Actor.isValid(localActor)) {
          localPoint3d = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
          if (j == 0) {
            incHeight(localPoint3d, this.squareTile, paramInt3 > 0 ? 1 : -1);
          } else {
            peekHeight(localPoint3d);
            this.fillHeight += (paramInt3 > 0 ? 1 : -1);
            if (this.fillHeight < 0) this.fillHeight = 0;
            if (this.fillHeight > 255) this.fillHeight = 255;
            fillHeight(localPoint3d, this.squareTile);
            if (j == 2)
              blurHeight5(localPoint3d, this.squareTile); 
          }
        }
      }
    }
  }

  public void msgMouseButton(int paramInt, boolean paramBoolean) {
  }

  public void msgMouseAbsMove(int paramInt1, int paramInt2, int paramInt3) {
  }

  private int HCode2M(int paramInt) {
    if (paramInt < 64) return paramInt;
    if (paramInt < 96) return 64 + (paramInt - 64) * 2;
    if (paramInt < 128) return 128 + (paramInt - 96) * 4;
    if (paramInt < 160) return 256 + (paramInt - 128) * 8;
    if (paramInt < 192) return 512 + (paramInt - 160) * 16;
    if (paramInt < 224) return 1024 + (paramInt - 192) * 32;
    return 2048 + (paramInt - 224) * 64;
  }

  private int M2HCode(int paramInt) {
    if (paramInt < 63) return paramInt;
    if (paramInt < 126) return (paramInt - 64) / 2 + 64;
    if (paramInt < 252) return (paramInt - 128) / 4 + 96;
    if (paramInt < 504) return (paramInt - 256) / 8 + 128;
    if (paramInt < 1008) return (paramInt - 512) / 16 + 160;
    if (paramInt < 2016) return (paramInt - 1024) / 32 + 192;
    int i = (paramInt - 2048) / 64 + 224;
    return i < 256 ? i : 255;
  }

  private void blurHeight5(Point3d paramPoint3d, int paramInt) {
    int i = World.land().WORLD2PIXX(paramPoint3d.jdField_x_of_type_Double);
    int j = World.land().WORLD2PIXY(paramPoint3d.jdField_y_of_type_Double);
    paramInt |= 1;
    int k = i - paramInt / 2;
    int m = j - paramInt / 2;
    World.land(); int n = Landscape.getSizeXpix();
    World.land(); int i1 = Landscape.getSizeYpix();

    for (int i2 = m; i2 < m + paramInt; i2++)
      for (int i3 = k; i3 < k + paramInt; i3++)
        if ((i2 >= 0) && (i2 < i1) && (i3 >= 0) && (i3 < n)) {
          int i4 = HCode2M(this.landMapH.intI(i3, i2)) * 4;
          int i5 = 4;
          if (i3 > 0) { i4 += HCode2M(this.landMapH.intI(i3 - 1, i2)); i5++; }
          if (i3 + 1 < n) { i4 += HCode2M(this.landMapH.intI(i3 + 1, i2)); i5++; }
          if (i2 > 0) { i4 += HCode2M(this.landMapH.intI(i3, i2 - 1)); i5++; }
          if (i2 + 1 < i1) { i4 += HCode2M(this.landMapH.intI(i3, i2 + 1)); i5++; }
          if (i5 > 4) {
            int i6 = this.fillHeight;
            this.fillHeight = M2HCode(i4 / i5);
            setHeight(i3, i2);
            this.fillHeight = i6;
          }
        }
  }

  private void changeTiles(Point3d paramPoint3d, int paramInt1, int paramInt2)
  {
    int i = World.land().WORLD2PIXX(paramPoint3d.jdField_x_of_type_Double);
    int j = World.land().WORLD2PIXY(paramPoint3d.jdField_y_of_type_Double);
    paramInt2 |= 1;
    int k = i - paramInt2 / 2;
    int m = j - paramInt2 / 2;
    World.land(); int n = Landscape.getSizeXpix();
    World.land(); int i1 = Landscape.getSizeYpix();
    for (int i2 = m; i2 < m + paramInt2; i2++)
      for (int i3 = k; i3 < k + paramInt2; i3++)
        if ((i2 >= 0) && (i2 < i1) && (i3 >= 0) && (i3 < n))
          changeTile(paramPoint3d, i3, i2, paramInt1);
  }

  private void changeTile(Point3d paramPoint3d, int paramInt) {
    int i = World.land().WORLD2PIXX(paramPoint3d.jdField_x_of_type_Double);
    int j = World.land().WORLD2PIXY(paramPoint3d.jdField_y_of_type_Double);
    changeTile(paramPoint3d, i, j, paramInt);
  }
  private void changeTile(Point3d paramPoint3d, int paramInt1, int paramInt2, int paramInt3) {
    this.bTMapChanged = true;
    int i = tileType(paramInt3);
    if (i == -1) return;
    int j = this.landMapT.intI(paramInt1, paramInt2);

    j = j & 0xFFFFFFE0 | i;

    this.landMapT.I(paramInt1, paramInt2, j);
    World.land(); Landscape.setPixelMapT(paramInt1, paramInt2, j);
    if (!Engine.dreamEnv().isSleep(paramPoint3d)) {
      int[] arrayOfInt1 = { paramInt1 };
      int[] tmp85_83 = new int[1]; World.land(); tmp85_83[0] = (Landscape.getSizeYpix() - 1 - paramInt2); int[] arrayOfInt2 = tmp85_83;
      World.cur().statics.msgDreamGlobal(false, 1, arrayOfInt1, arrayOfInt2);
      World.cur().statics.msgDreamGlobal(true, 1, arrayOfInt1, arrayOfInt2);
    }
  }

  private int tileType(int paramInt)
  {
    int i = 0;
    switch (paramInt) { case 0:
    default:
      i = 28; break;
    case 1:
      i = 0; break;
    case 2:
      i = 1; break;
    case 3:
      i = 2; break;
    case 4:
      i = 3; break;
    case 5:
      i = 4; break;
    case 6:
      i = 5; break;
    case 7:
      i = 6; break;
    case 8:
      i = 7; break;
    case 9:
      i = 8; break;
    case 10:
      i = 9; break;
    case 11:
      i = 10; break;
    case 12:
      i = 11; break;
    case 13:
      i = 12; break;
    case 14:
      i = 13; break;
    case 15:
      i = 14; break;
    case 16:
      i = 15; break;
    case 17:
      i = 16; break;
    case 18:
      i = 17; break;
    case 19:
      i = 18; break;
    case 20:
      i = 19; break;
    case 21:
      i = 20; break;
    case 22:
      i = 21; break;
    case 23:
      i = 22; break;
    case 24:
      i = 23; break;
    case 25:
      i = 24; break;
    case 26:
      i = 30; break;
    case 27:
      i = 31;
    }
    return i;
  }

  private void _doFill(Point3d paramPoint3d) {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    if ((i < 0) || (i >= this.type.length) || (j < 0) || (j >= this.type[i].item.length))
    {
      return;
    }if (i != this.type.length - 1)
      return;
    changeTile(paramPoint3d, j);
  }
  private void _doFill() {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    if ((i < 0) || (i >= this.type.length) || (j < 0) || (j >= this.type[i].item.length))
    {
      return;
    }if ((i != this.type.length - 1) && (i != this.type.length - 2))
    {
      return;
    }this._startFill.jdField_z_of_type_Double = 0.0D;
    this._endFill.jdField_z_of_type_Double = 0.0D;
    double d = this._endFill.distance(this._startFill);
    int k = (int)Math.round(d / 200.0D) + 1;
    float f = 1.0F / k;
    for (int m = 0; m <= k; m++) {
      this._stepFill.interpolate(this._startFill, this._endFill, m * f);
      if (i == this.type.length - 1) {
        changeTiles(this._stepFill, j, this.squareTile);
      }
      else if (j == 1) { fillHeight(this._stepFill, this.squareTile); } else {
        if (j != 2) continue; blurHeight5(this._stepFill, this.squareTile);
      }
    }
  }

  public void beginFill(Point3d paramPoint3d)
  {
    this._startFill.set(paramPoint3d); } 
  public void fill(Point3d paramPoint3d) { this._endFill.set(paramPoint3d); _doFill(); this._startFill.set(paramPoint3d); } 
  public void endFill(Point3d paramPoint3d) {
  }
  public void fillPopUpMenu(GWindowMenuPopUp paramGWindowMenuPopUp, Point3d paramPoint3d) {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    if ((i < 0) || (i >= this.type.length) || (j < 0) || (j >= this.type[i].item.length) || ((i != this.type.length - 2) && (i != this.type.length - 1)))
    {
      paramGWindowMenuPopUp.addItem(new GWindowMenuItem(paramGWindowMenuPopUp, "&Paste From File...", null) {
        public void execute() { PlMapActors.this.pasteFromFile(this.root);
        }
      });
      return;
    }
    GWindowMenuItem localGWindowMenuItem1 = paramGWindowMenuPopUp.addItem(new GWindowMenuItem(paramGWindowMenuPopUp, "&Fill Tile Size", null));
    localGWindowMenuItem1.subMenu = ((GWindowMenu)localGWindowMenuItem1.create(new GWindowMenu()));
    localGWindowMenuItem1.subMenu.close(false);
    GWindowMenuItem localGWindowMenuItem2 = localGWindowMenuItem1.subMenu.addItem(new GWindowMenuItem(localGWindowMenuItem1.subMenu, "&1", null) {
      public void execute() { PlMapActors.access$102(PlMapActors.this, 1);
      }
    });
    localGWindowMenuItem2.jdField_bChecked_of_type_Boolean = (this.squareTile == 1);
    localGWindowMenuItem2 = localGWindowMenuItem1.subMenu.addItem(new GWindowMenuItem(localGWindowMenuItem1.subMenu, "&3", null) {
      public void execute() { PlMapActors.access$102(PlMapActors.this, 3);
      }
    });
    localGWindowMenuItem2.jdField_bChecked_of_type_Boolean = (this.squareTile == 3);
    localGWindowMenuItem2 = localGWindowMenuItem1.subMenu.addItem(new GWindowMenuItem(localGWindowMenuItem1.subMenu, "&5", null) {
      public void execute() { PlMapActors.access$102(PlMapActors.this, 5);
      }
    });
    localGWindowMenuItem2.jdField_bChecked_of_type_Boolean = (this.squareTile == 5);
    localGWindowMenuItem2 = localGWindowMenuItem1.subMenu.addItem(new GWindowMenuItem(localGWindowMenuItem1.subMenu, "&7", null) {
      public void execute() { PlMapActors.access$102(PlMapActors.this, 7);
      }
    });
    localGWindowMenuItem2.jdField_bChecked_of_type_Boolean = (this.squareTile == 7);
    localGWindowMenuItem2 = localGWindowMenuItem1.subMenu.addItem(new GWindowMenuItem(localGWindowMenuItem1.subMenu, "&9", null) {
      public void execute() { PlMapActors.access$102(PlMapActors.this, 9);
      }
    });
    localGWindowMenuItem2.jdField_bChecked_of_type_Boolean = (this.squareTile == 9);

    paramGWindowMenuPopUp.addItem("-", null);
    paramGWindowMenuPopUp.addItem(new GWindowMenuItem(paramGWindowMenuPopUp, "&Paste From File...", null) {
      public void execute() { PlMapActors.this.pasteFromFile(this.root);
      }
    });
    paramGWindowMenuPopUp.addItem("-", null);
    paramGWindowMenuPopUp.addItem(new GWindowMenuItem(paramGWindowMenuPopUp, "&Insert Pattern As Static...", null) {
      public void execute() { PlMapActors.this.doInsertPattern(this.root);
      }
    });
    paramGWindowMenuPopUp.addItem(new GWindowMenuItem(paramGWindowMenuPopUp, "&Remove Pattern From Static...", null) {
      public void execute() { PlMapActors.this.doRemovePattern(this.root); } } );
  }

  public void delete(Loc paramLoc) {
    int i = Plugin.builder.wSelect.comboBox1.getSelected();
    int j = Plugin.builder.wSelect.comboBox2.getSelected();
    if ((i < 0) || (i >= this.type.length) || (j < 0) || (j >= this.type[i].item.length))
    {
      return;
    }if (i != this.type.length - 4)
    {
      if (i == this.type.length - 3)
      {
        this.bTMapChanged = true;
        int k = World.land().WORLD2PIXX(paramLoc.getPoint().jdField_x_of_type_Double);
        int m = World.land().WORLD2PIXY(paramLoc.getPoint().jdField_y_of_type_Double);
        int n = 0;
        if (j == 0) n = 64;
        if (j == 1) n = 32;
        if (j == 2) n = 128;
        int i1 = this.landMapT.intI(k, m) & (n ^ 0xFFFFFFFF);
        this.landMapT.I(k, m, i1);
        World.land(); Landscape.setPixelMapT(k, m, i1);
      }
      else if (i != this.type.length - 1);
    }
  }

  public void cut() {
    if (Plugin.builder.isFreeView()) return;
    copy(false);
    Actor[] arrayOfActor = Plugin.builder.selectedActors();
    for (int i = 0; i < arrayOfActor.length; i++) {
      Actor localActor = arrayOfActor[i];
      if (localActor == null) break;
      if ((Actor.isValid(localActor)) && (Plugin.builder.isMiltiSelected(localActor))) {
        if ((localActor instanceof PAirdrome)) {
          PathAirdrome localPathAirdrome = (PathAirdrome)localActor.getOwner();
          if (localPathAirdrome.pointIndx((PAirdrome)localActor) == 0)
            localPathAirdrome.destroy();
        } else {
          localActor.destroy();
        }
      }
    }
    Plugin.builder.selectedActorsValidate();
    Plugin.builder.repaint();
  }
  public void copy(boolean paramBoolean) {
    if (Plugin.builder.isFreeView()) return;
    this._clipSpawns.clear();
    this._clipAirdroms.clear();
    this._clipLoc.clear();
    int i = 0;
    this._clipP0.jdField_x_of_type_Double = (this._clipP0.jdField_y_of_type_Double = this._clipP0.jdField_z_of_type_Double = 0.0D);
    Actor[] arrayOfActor = Plugin.builder.selectedActors();
    for (int j = 0; j < arrayOfActor.length; j++) {
      Actor localActor = arrayOfActor[j];
      if (localActor == null) break;
      if ((Actor.isValid(localActor)) && (Plugin.builder.isMiltiSelected(localActor)))
      {
        Object localObject;
        if ((localActor instanceof PAirdrome)) {
          localObject = (PAirdrome)localActor;
          this._clipP0.add(localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint());
          i++;
          PathAirdrome localPathAirdrome = (PathAirdrome)((PAirdrome)localObject).getOwner();
          if (localPathAirdrome.pointIndx((PPoint)localObject) == 0)
            this._clipAirdroms.add(PathAirdrome.toSpawnString(localPathAirdrome));
        }
        else {
          localObject = new Loc();
          localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs((Loc)localObject);
          this._clipSpawns.add(Spawn.get_WithSoftClass(getFullClassName(localActor)));
          this._clipLoc.add(localObject);
          this._clipP0.add(((Loc)localObject).getPoint());
          i++;
        }
      }
    }
    if (i > 1) { this._clipP0.jdField_x_of_type_Double /= i; this._clipP0.jdField_y_of_type_Double /= i; this._clipP0.jdField_z_of_type_Double /= i; }
    if (paramBoolean)
      Plugin.builder.selectActorsClear();
    Plugin.builder.repaint();
  }
  public void paste() {
    if (Plugin.builder.isFreeView()) return;
    Plugin.builder.selectActorsClear();
    int i = this._clipSpawns.size();
    if ((i == 0) && (this._clipAirdroms.size() == 0)) return;
    Point3d localPoint3d1 = Plugin.builder.mouseWorldPos();
    Loc localLoc1 = new Loc();
    Point3d localPoint3d2 = new Point3d();
    Object localObject;
    for (int j = 0; j < i; j++) {
      Loc localLoc2 = (Loc)this._clipLoc.get(j);
      localPoint3d2.sub(localLoc2.getPoint(), this._clipP0);
      localPoint3d2.add(localPoint3d1);
      localLoc1.set(localPoint3d2, localLoc2.getOrient());
      localObject = insert((ActorSpawn)this._clipSpawns.get(j), localLoc1, false);
      Plugin.builder.selectActorsAdd((Actor)localObject);
    }
    i = this._clipAirdroms.size();
    for (int k = 0; k < i; k++) {
      localObject = (String)this._clipAirdroms.get(k);
      PathAirdrome localPathAirdrome = (PathAirdrome)CmdEnv.top().exec((String)localObject);
      int m = localPathAirdrome.points();
      for (int n = 0; n < m; n++) {
        PPoint localPPoint = localPathAirdrome.point(n);
        localPPoint.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbs(localPoint3d2);
        localPoint3d2.sub(this._clipP0);
        localPoint3d2.add(localPoint3d1);
        localPPoint.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.setAbs(localPoint3d2); localPPoint.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.reset();
        Plugin.builder.selectActorsAdd(localPPoint);
      }
    }
    Plugin.builder.repaint();
  }

  private void pasteFromFile(GWindowRoot paramGWindowRoot)
  {
    if (this.dlgPasteFrom == null)
      this.dlgPasteFrom = new GWindowFileOpen(paramGWindowRoot, true, "Paste From File ...", "maps", new GFileFilter[] { new GFileFilterName("All files", new String[] { "*" }) })
      {
        public void result(String paramString)
        {
          if (paramString != null)
            PlMapActors.this.load("maps/" + paramString, false, true);
        }
      };
    else this.dlgPasteFrom.activateWindow();
  }

  private void fillComboBox1()
  {
    this.startComboBox1 = Plugin.builder.wSelect.comboBox1.size();
    for (int i = 0; i < this.type.length; i++) {
      Plugin.builder.wSelect.comboBox1.add(this.type[i].name);
    }
    Plugin.builder.wSelect.comboBox1.setSelected(0, true, false);
  }
  private void fillComboBox2(int paramInt1, int paramInt2) {
    if ((paramInt1 < this.startComboBox1) || (paramInt1 >= this.startComboBox1 + this.type.length)) {
      return;
    }
    if (Plugin.builder.wSelect.curFilledType != paramInt1) {
      Plugin.builder.wSelect.curFilledType = paramInt1;
      Plugin.builder.wSelect.comboBox2.clear(false);
      for (int i = 0; i < this.type[paramInt1].item.length; i++) {
        Plugin.builder.wSelect.comboBox2.add(this.type[paramInt1].item[i].name);
      }
      Plugin.builder.wSelect.comboBox1.setSelected(paramInt1, true, false);
    }
    Plugin.builder.wSelect.comboBox2.setSelected(paramInt2, true, false);
  }

  public void syncSelector() {
    Actor localActor = Plugin.builder.selectedActor();
    int i = getFingerOfFullClassName(localActor);
    for (int j = 0; j < this.type.length; j++) {
      for (int k = 0; k < this.type[j].item.length; k++) {
        if (i == this.type[j].item[k].fingerOfFullClassName) {
          fillComboBox2(j, k);
          return;
        }
      }
    }
    if ((localActor instanceof Bridge)) {
      Bridge localBridge = (Bridge)localActor;
      int m = localBridge.type();
      int n = 0;
      switch (m) { case 2:
        n = 0; break;
      case 1:
        n = 1; break;
      case 0:
        n = 2;
      }
      fillComboBox2(this.type.length - 4, n);
      return;
    }
  }

  public void configure() {
    this.matLabel = Mat.New("icons/label.mat");
    Plugin.builder.bMultiSelect = true;
    if (this.jdField_sectFile_of_type_JavaLangString == null)
      throw new RuntimeException("PlMapActors: field 'sectFile' not defined");
    SectFile localSectFile = new SectFile(this.jdField_sectFile_of_type_JavaLangString, 0);
    int i = localSectFile.sections();
    if (i <= 0) {
      throw new RuntimeException("PlMapActors: file '" + this.jdField_sectFile_of_type_JavaLangString + "' is empty");
    }

    int j = 0;
    for (int k = 0; k < i; k++) {
      if (localSectFile.sectionName(k).equals("***")) {
        j++;
      }
    }
    if (j <= 0) {
      throw new RuntimeException("PlMapActors: No type groups in file '" + this.jdField_sectFile_of_type_JavaLangString + "'");
    }

    this.type = new Type[j + 4];
    int m = localSectFile.sectionIndex("***");
    for (int n = 0; n < j; n++) {
      int i1 = (n >= j - 1 ? i : localSectFile.sectionIndex("***", m + 1)) - 1 - m;

      if (i1 <= 0) {
        throw new RuntimeException("PlMapActors: Empty group in file '" + this.jdField_sectFile_of_type_JavaLangString + "'");
      }

      int i2 = localSectFile.varIndex(m, "Title");
      if (i2 < 0) {
        throw new RuntimeException("PlMapActors: No 'Title' in file '" + this.jdField_sectFile_of_type_JavaLangString + "', section '***' (#" + m + ")");
      }

      String str1 = localSectFile.value(m, i2);

      Item[] arrayOfItem2 = new Item[i1];
      for (int i3 = 0; i3 < i1; i3++) {
        String str2 = localSectFile.sectionName(m + 1 + i3);
        int i4 = str2.indexOf(' ');
        if (i4 > 0) {
          str2 = str2.substring(0, i4);
        }

        String str3 = localSectFile.get(str2, "Title");
        if (str3 == null) {
          str3 = localSectFile.get(str2, "equals");
          if (str3 == null) {
            throw new RuntimeException("PlMapActors: No 'Title' in file '" + this.jdField_sectFile_of_type_JavaLangString + "', section '" + str2 + "'");
          }

          int i5 = localSectFile.sectionIndex(str3);
          if (i5 < 0) {
            throw new RuntimeException("PlMapActors: Unknown 'equals' in file '" + this.jdField_sectFile_of_type_JavaLangString + "', section '" + str2 + "'");
          }

          str5 = localSectFile.sectionName(i5);
          str3 = localSectFile.get(str5, "Title");
          if (str3 == null) {
            throw new RuntimeException("PlMapActors: No 'Title' in file '" + this.jdField_sectFile_of_type_JavaLangString + "', section '" + str5 + "'");
          }

        }

        String str4 = str2;
        String str5 = "";
        int i6 = str2.lastIndexOf('$');
        if (i6 >= 0) {
          str4 = str2.substring(0, i6);
          str5 = str2.substring(i6 + 1);
        }

        Class localClass = null;
        try {
          localClass = ObjIO.classForName(str4);
        } catch (Exception localException) {
          throw new RuntimeException("PlMapActors: class '" + str4 + "' not found");
        }

        if (i6 >= 0)
          str2 = localClass.getName() + "$" + str5;
        else {
          str2 = localClass.getName();
        }

        arrayOfItem2[i3] = new Item(str3, str2);
      }
      this.type[n] = new Type(str1, arrayOfItem2);
      m += 1 + i1;
    }

    Item[] arrayOfItem1 = new Item[3];
    arrayOfItem1[0] = new Item("Rail");
    arrayOfItem1[1] = new Item("Country");
    arrayOfItem1[2] = new Item("Highway");
    this.type[j] = new Type("Bridge", arrayOfItem1);

    arrayOfItem1 = new Item[3];
    arrayOfItem1[0] = new Item("Rail");
    arrayOfItem1[1] = new Item("Country");
    arrayOfItem1[2] = new Item("Highway");
    this.type[(j + 1)] = new Type("Road", arrayOfItem1);

    arrayOfItem1 = new Item[3];
    arrayOfItem1[0] = new Item("Peek");
    arrayOfItem1[1] = new Item("Fill");
    arrayOfItem1[2] = new Item("Blur");
    this.type[(j + 2)] = new Type("Height", arrayOfItem1);

    arrayOfItem1 = new Item[28];
    arrayOfItem1[0] = new Item("WATER");
    arrayOfItem1[1] = new Item("LowLand");
    arrayOfItem1[2] = new Item("LowLand1");
    arrayOfItem1[3] = new Item("LowLand2");
    arrayOfItem1[4] = new Item("LowLand3");
    arrayOfItem1[5] = new Item("MidLand");
    arrayOfItem1[6] = new Item("MidLand1");
    arrayOfItem1[7] = new Item("MidLand2");
    arrayOfItem1[8] = new Item("MidLand3");
    arrayOfItem1[9] = new Item("Mount");
    arrayOfItem1[10] = new Item("Mount1");
    arrayOfItem1[11] = new Item("Mount2");
    arrayOfItem1[12] = new Item("Mount3");
    arrayOfItem1[13] = new Item("Country");
    arrayOfItem1[14] = new Item("Country1");
    arrayOfItem1[15] = new Item("Country2");
    arrayOfItem1[16] = new Item("Country3");
    arrayOfItem1[17] = new Item("City");
    arrayOfItem1[18] = new Item("City1");
    arrayOfItem1[19] = new Item("City2");
    arrayOfItem1[20] = new Item("City3");
    arrayOfItem1[21] = new Item("Airfield");
    arrayOfItem1[22] = new Item("Airfield1");
    arrayOfItem1[23] = new Item("Airfield2");
    arrayOfItem1[24] = new Item("Airfield3");
    arrayOfItem1[25] = new Item("Wood");
    arrayOfItem1[26] = new Item("CoastRiver");
    arrayOfItem1[27] = new Item("CoastSea");
    this.type[(j + 3)] = new Type("Tile", arrayOfItem1);
  }

  public void selectAll()
  {
    Map.Entry localEntry = this.allActors.nextEntry(null);
    while (localEntry != null) {
      Actor localActor = (Actor)localEntry.getKey();
      if ((Actor.isValid(localActor)) && (localActor.isDrawing()))
        Plugin.builder.selectActorsAdd(localActor);
      localEntry = this.allActors.nextEntry(localEntry);
    }
  }

  void viewUpdate()
  {
    Map.Entry localEntry = this.allActors.nextEntry(null);
    while (localEntry != null) {
      Actor localActor = (Actor)localEntry.getKey();
      if (Actor.isValid(localActor)) {
        Integer localInteger = new Integer(getFingerOfFullClassName(localActor));
        localActor.drawing(this.viewClassFingers.containsKey(localInteger));
      }
      localEntry = this.allActors.nextEntry(localEntry);
    }
    if ((Actor.isValid(Plugin.builder.selectedActor())) && (!Plugin.builder.selectedActor().isDrawing()))
      Plugin.builder.setSelected(null);
    Plugin.builder.selectedActorsValidate();
    if (!Plugin.builder.isFreeView())
      Plugin.builder.repaint(); 
  }

  void viewType(int paramInt, boolean paramBoolean) {
    int i = this.type[paramInt].item.length;
    for (int j = 0; j < i; j++) {
      Integer localInteger = new Integer(this.type[paramInt].item[j].fingerOfFullClassName);
      if (paramBoolean) this.viewClassFingers.put(localInteger, null); else
        this.viewClassFingers.remove(localInteger);
    }
    viewUpdate();
  }
  void viewType(int paramInt) {
    if (paramInt >= this.type.length - 4 - 1) return;
    viewType(paramInt, this.viewType[paramInt].jdField_bChecked_of_type_Boolean);
  }

  public void viewTypeAll(boolean paramBoolean)
  {
    int i;
    if (paramBoolean)
      for (i = 0; i < this.type.length - 4; i++)
        if (!this.viewType[i].jdField_bChecked_of_type_Boolean) {
          this.viewType[i].jdField_bChecked_of_type_Boolean = true;
          viewType(i, true);
        }
    else {
      for (i = 0; i < this.type.length - 4; i++)
        if (this.viewType[i].jdField_bChecked_of_type_Boolean) {
          this.viewType[i].jdField_bChecked_of_type_Boolean = false;
          viewType(i, false);
        }
    }
    viewBridge(paramBoolean);
    viewRunaway(paramBoolean);
  }

  void viewBridge(boolean paramBoolean)
  {
    Plugin.builder.conf.bViewBridge = paramBoolean;
    this.viewBridge.jdField_bChecked_of_type_Boolean = Plugin.builder.conf.bViewBridge;
  }
  void viewBridge() { viewBridge(!Plugin.builder.conf.bViewBridge); }

  void viewRunaway(boolean paramBoolean) {
    Plugin.builder.conf.bViewRunaway = paramBoolean;
    this.viewRunaway.jdField_bChecked_of_type_Boolean = Plugin.builder.conf.bViewRunaway;
  }
  void viewRunaway() { viewRunaway(!Plugin.builder.conf.bViewRunaway);
  }

  public void createGUI()
  {
    fillComboBox1();
    fillComboBox2(0, 0);
    Plugin.builder.wSelect.comboBox1.addNotifyListener(new GNotifyListener() {
      public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
        int i = Plugin.builder.wSelect.comboBox1.getSelected();
        if ((i >= 0) && (paramInt1 == 2))
          PlMapActors.this.fillComboBox2(i, 0);
        return false;
      }
    });
    this.viewType = new ViewItem[this.type.length];
    for (int i = 0; i < this.type.length - 4; i++) {
      ViewItem localViewItem = (ViewItem)Plugin.builder.mDisplayFilter.subMenu.addItem(new ViewItem(i, Plugin.builder.mDisplayFilter.subMenu, "show " + this.type[i].name, null));

      localViewItem.jdField_bChecked_of_type_Boolean = true;
      this.viewType[i] = localViewItem;
      viewType(i, true);
    }
    Plugin.builder.mDisplayFilter.subMenu.addItem("-", null);
    this.viewBridge = Plugin.builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, "show Bridge", null) {
      public void execute() {
        PlMapActors.this.viewBridge();
      }
    });
    this.viewBridge.jdField_bChecked_of_type_Boolean = Plugin.builder.conf.bViewBridge;
    this.viewRunaway = Plugin.builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, "show Runway", null) {
      public void execute() {
        PlMapActors.this.viewRunaway();
      }
    });
    this.viewRunaway.jdField_bChecked_of_type_Boolean = Plugin.builder.conf.bViewRunaway;
    Plugin.builder.mDisplayFilter.subMenu.addItem("-", null);
    Plugin.builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, "&Show All", null) {
      public void execute() {
        PlMapActors.this.viewTypeAll(true);
      }
    });
    Plugin.builder.mDisplayFilter.subMenu.addItem(new GWindowMenuItem(Plugin.builder.mDisplayFilter.subMenu, "&Hide All", null) {
      public void execute() {
        PlMapActors.this.viewTypeAll(false);
      }
    });
    this.mLoad = Plugin.builder.mFile.subMenu.addItem(1, new GWindowMenuItem(Plugin.builder.mFile.subMenu, "Load", null)
    {
      public void execute()
      {
        if (PlMapLoad.mapFileName() != null) {
          PlMapLoad localPlMapLoad = (PlMapLoad)Plugin.getPlugin("MapLoad");
          PlMapLoad.Land localLand = PlMapLoad.getLandLoaded();
          localPlMapLoad.mapUnload();
          localPlMapLoad.guiMapLoad(localLand);
        }
      }
    });
    this.mSave = Plugin.builder.mFile.subMenu.addItem(2, new GWindowMenuItem(Plugin.builder.mFile.subMenu, "Save", null)
    {
      public void execute()
      {
        PlMapActors.this.save();
      }
    });
    Plugin.builder.mFile.subMenu.addItem(3, "-", null);
    this.mLoadAs = Plugin.builder.mFile.subMenu.addItem(4, new GWindowMenuItem(Plugin.builder.mFile.subMenu, "Load As ...", null)
    {
      public void execute()
      {
        if (PlMapActors.this.dlgLoadAs == null) {
          PlMapActors.access$502(PlMapActors.this, new PlMapActors.19(this, this.root, true, "Load As ...", "maps", new GFileFilter[] { new GFileFilterName("All files", new String[] { "*" }) }));
        }
        else
        {
          PlMapActors.this.dlgLoadAs.activateWindow();
        }
      }
    });
    this.mLoadSpawn = Plugin.builder.mFile.subMenu.addItem(5, new GWindowMenuItem(Plugin.builder.mFile.subMenu, "Load Spawn ...", null)
    {
      public void execute()
      {
        if (PlMapActors.this.dlgLoadSpawn == null) {
          PlMapActors.access$702(PlMapActors.this, new PlMapActors.21(this, this.root, true, "Load Spawn ...", "maps", new GFileFilter[] { new GFileFilterName("All files", new String[] { "*" }) }));
        }
        else
        {
          PlMapActors.this.dlgLoadSpawn.activateWindow();
        }
      }
    });
    this.mSaveAs = Plugin.builder.mFile.subMenu.addItem(6, new GWindowMenuItem(Plugin.builder.mFile.subMenu, "Save As ...", null)
    {
      public void execute()
      {
        if (PlMapActors.this.dlgSaveAs == null) {
          PlMapActors.access$902(PlMapActors.this, new PlMapActors.23(this, this.root, true, "Save As ...", "maps", new GFileFilter[] { new GFileFilterName("All files", new String[] { "*" }) }));
        }
        else
        {
          PlMapActors.this.dlgSaveAs.activateWindow();
        }
      }
    });
    this.mSaveSpawn = Plugin.builder.mFile.subMenu.addItem(7, new GWindowMenuItem(Plugin.builder.mFile.subMenu, "Save As Spawn ...", null)
    {
      public void execute()
      {
        if (PlMapActors.this.dlgSaveSpawn == null) {
          PlMapActors.access$1102(PlMapActors.this, new PlMapActors.25(this, this.root, true, "Save As Spawn ...", "maps", new GFileFilter[] { new GFileFilterName("All files", new String[] { "*" }) }));
        }
        else
        {
          PlMapActors.this.dlgSaveSpawn.activateWindow();
        }
      }
    });
    Plugin.builder.mFile.subMenu.addItem(8, "-", null);
    this.mCreateBridges = Plugin.builder.mFile.subMenu.addItem(9, new GWindowMenuItem(Plugin.builder.mFile.subMenu, "CreateBridges ...", null)
    {
      public void execute()
      {
        new PlMapActors.27(this, this.root, 20.0F, true, "CreateBridges ...", "Start process creating bridges ?", 1, 0.0F);
      }
    });
    Plugin.builder.mFile.subMenu.addItem(10, "-", null);
    Plugin.builder.mFile.subMenu.addItem(11, new GWindowMenuItem(Plugin.builder.mFile.subMenu, "Clear Roads ...", null)
    {
      public void execute() {
        new PlMapActors.29(this, this.root, 20.0F, true, "Clear Roads ...", "Start process clear roads ?", 1, 0.0F);
      }
    });
    Plugin.builder.mFile.subMenu.addItem(12, new GWindowMenuItem(Plugin.builder.mFile.subMenu, "Remove Roads ...", null)
    {
      public void execute() {
        new PlMapActors.31(this, this.root, 20.0F, true, "Remove Roads ...", "Start process remove roads ?", 1, 0.0F);
      }
    });
    Plugin.builder.mFile.subMenu.addItem(13, new GWindowMenuItem(Plugin.builder.mFile.subMenu, "Clear Water ...", null)
    {
      public void execute() {
        new PlMapActors.33(this, this.root, 20.0F, true, "Clear Water ...", "Start process clear water ?", 1, 0.0F);
      }
    });
  }

  public void start()
  {
    MsgAddListener.post(64, RTSConf.cur.mouse, this, null);

    HotKeyCmdEnv.setCurrentEnv(Builder.envName);
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "cut") {
      public void begin() { PlMapActors.this.cut();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "copy") {
      public void begin() { PlMapActors.this.copy(true);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "paste") {
      public void begin() { PlMapActors.this.paste();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "bridgeLeft") {
      public void begin() { PlMapActors.this.bridgeRotate(true);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "bridgeRight") {
      public void begin() { PlMapActors.this.bridgeRotate(false);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "bridgeOffset+") {
      public void begin() { PlMapActors.this.bridgeOffset(true);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "bridgeOffset-") {
      public void begin() { PlMapActors.this.bridgeOffset(false);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectLowLand") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 1);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectLowLand1") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 2);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectLowLand2") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 3);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectLowLand3") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 4);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectMidLand") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 5);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectMidLand1") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 6);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectMidLand2") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 7);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectMidLand3") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 8);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectMount") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 9);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectMount1") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 10);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectMount2") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 11);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectMount3") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 12);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectCountry") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 13);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectCountry1") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 14);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectCountry2") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 15);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectCountry3") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 16);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectCity") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 17);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectCity1") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 18);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectCity2") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 19);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectCity3") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 20);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectAirfield") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 21);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectAirfield1") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 22);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectAirfield2") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 23);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectAirfield3") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 24);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectWood") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 25);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectCoastRiver") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 26);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectCoastSea") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 1, 27);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectRoadRail") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 3, 0);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectRoadCountry") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 3, 1);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectRoadHighway") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 3, 2);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectBridgeRail") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 4, 0);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectBridgeCountry") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 4, 1);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectBridgeHighway") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 4, 2);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectHeightPeek") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 2, 0);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectHeightFill") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 2, 1);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "selectHeightBlur") {
      public void begin() { PlMapActors.this.setSelected(this.sName, PlMapActors.this.type.length - 2, 2);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "incHeight") {
      public void begin() { PlMapActors.access$2308(PlMapActors.this); if (PlMapActors.this.fillHeight > 255) PlMapActors.access$2302(PlMapActors.this, 255);
        Plugin.builder.tip("Peek Height code = " + PlMapActors.this.fillHeight);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "decHeight") {
      public void begin() { PlMapActors.access$2310(PlMapActors.this); if (PlMapActors.this.fillHeight < 0) PlMapActors.access$2302(PlMapActors.this, 0);
        Plugin.builder.tip("Peek Height code = " + PlMapActors.this.fillHeight);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "incHeight10") {
      public void begin() { PlMapActors.access$2312(PlMapActors.this, 10); if (PlMapActors.this.fillHeight > 255) PlMapActors.access$2302(PlMapActors.this, 255);
        Plugin.builder.tip("Peek Height code = " + PlMapActors.this.fillHeight);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "decHeight10") {
      public void begin() { PlMapActors.access$2320(PlMapActors.this, 10); if (PlMapActors.this.fillHeight < 0) PlMapActors.access$2302(PlMapActors.this, 0);
        Plugin.builder.tip("Peek Height code = " + PlMapActors.this.fillHeight);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "incCurHeight") {
      public void begin() { if (!Plugin.builder.isFreeView()) return;
        Actor localActor = Plugin.builder.selectedActor();
        if (!Actor.isValid(localActor)) return;
        Point3d localPoint3d = localActor.pos.getAbsPoint();
        PlMapActors.this.peekHeight(localPoint3d);
        PlMapActors.access$2308(PlMapActors.this); if (PlMapActors.this.fillHeight > 255) PlMapActors.access$2302(PlMapActors.this, 255);
        Plugin.builder.tip("Peek Cur Height code = " + PlMapActors.this.fillHeight);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "decCurHeight") {
      public void begin() { if (!Plugin.builder.isFreeView()) return;
        Actor localActor = Plugin.builder.selectedActor();
        if (!Actor.isValid(localActor)) return;
        Point3d localPoint3d = localActor.pos.getAbsPoint();
        PlMapActors.this.peekHeight(localPoint3d);
        PlMapActors.access$2310(PlMapActors.this); if (PlMapActors.this.fillHeight < 0) PlMapActors.access$2302(PlMapActors.this, 0);
        Plugin.builder.tip("Peek Cur Height code = " + PlMapActors.this.fillHeight);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "incCurHeight10") {
      public void begin() { if (!Plugin.builder.isFreeView()) return;
        Actor localActor = Plugin.builder.selectedActor();
        if (!Actor.isValid(localActor)) return;
        Point3d localPoint3d = localActor.pos.getAbsPoint();
        PlMapActors.this.peekHeight(localPoint3d);
        PlMapActors.access$2312(PlMapActors.this, 10); if (PlMapActors.this.fillHeight > 255) PlMapActors.access$2302(PlMapActors.this, 255);
        Plugin.builder.tip("Peek Cur Height code = " + PlMapActors.this.fillHeight);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "decCurHeight10") {
      public void begin() { if (!Plugin.builder.isFreeView()) return;
        Actor localActor = Plugin.builder.selectedActor();
        if (!Actor.isValid(localActor)) return;
        Point3d localPoint3d = localActor.pos.getAbsPoint();
        PlMapActors.this.peekHeight(localPoint3d);
        PlMapActors.access$2320(PlMapActors.this, 10); if (PlMapActors.this.fillHeight < 0) PlMapActors.access$2302(PlMapActors.this, 0);
        Plugin.builder.tip("Peek Cur Height code = " + PlMapActors.this.fillHeight);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "square1") {
      public void begin() { PlMapActors.this.setQuare(0);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "square3") {
      public void begin() { PlMapActors.this.setQuare(1);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "square5") {
      public void begin() { PlMapActors.this.setQuare(2);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "square7") {
      public void begin() { PlMapActors.this.setQuare(3);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "square9") {
      public void begin() { PlMapActors.this.setQuare(4);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "damage") {
      public void begin() { if (Actor.isValid(Plugin.builder.selectedActor())) {
          Actor localActor = Plugin.builder.selectedActor();
          localActor.setDiedFlag(!localActor.getDiedFlag());
        } } } );
  }

  private void setQuare(int paramInt) {
    this.squareTile = (1 + paramInt * 2);
    Plugin.builder.tip("Set Square size = " + this.squareTile);
  }

  private void setSelected(String paramString, int paramInt1, int paramInt2) {
    Plugin.builder.wSelect.comboBox1.setSelected(paramInt1, true, true);
    Plugin.builder.wSelect.comboBox2.setSelected(paramInt2, true, true);
    Plugin.builder.tip(paramString);
  }

  private void bridgeCreate(Loc paramLoc)
  {
    int i = Plugin.builder.wSelect.comboBox2.getSelected();
    int j = World.land().WORLD2PIXX(paramLoc.getPoint().jdField_x_of_type_Double);
    int k = World.land().WORLD2PIXY(paramLoc.getPoint().jdField_y_of_type_Double);
    int m = 0;
    if (i == 0) m = 64;
    if (i == 1) m = 32;
    if (i == 2) m = 128;

    Bridge localBridge = new Bridge(this.newIndxBridge, m, j, k, j + 1, k, 0.0F);
    Property.set(localBridge, "builderSpawn", "");
    PlMapLoad.bridgeActors.add(localBridge);
    this.newIndxBridge += 1;
    this.bBridgeChanged = true;
  }

  private boolean setCurBridge()
  {
    if ((Plugin.builder.selectedActor() == null) || (!(Plugin.builder.selectedActor() instanceof Bridge)))
      return false;
    this.curBridge = ((Bridge)Plugin.builder.selectedActor());
    this.indxBridge = PlMapLoad.bridgeActors.indexOf(this.curBridge);
    return (Actor.isValid(this.curBridge)) && (this.indxBridge >= 0);
  }

  private void changeCurBridge(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat) {
    Bridge localBridge = this.curBridge;
    localBridge.destroy();
    localBridge = new Bridge(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramFloat);
    Property.set(localBridge, "builderSpawn", "");
    PlMapLoad.bridgeActors.set(this.indxBridge, localBridge);
    Plugin.builder.setSelected(localBridge);
    Plugin.builder.repaint();
    this.bBridgeChanged = true;
  }

  private void bridgeType(boolean paramBoolean) {
    if (!setCurBridge()) return;
    Bridge localBridge = this.curBridge;
    int i = localBridge.__type;
    switch (localBridge.__type) {
    case 64:
      if (paramBoolean) { i = 128; this.newCurItem = 2; } else {
        i = 32; this.newCurItem = 1;
      }break;
    case 32:
      if (paramBoolean) { i = 64; this.newCurItem = 0; } else {
        i = 128; this.newCurItem = 2;
      }break;
    case 128:
      if (paramBoolean) { i = 32; this.newCurItem = 1; } else {
        i = 64; this.newCurItem = 0;
      }break;
    default:
      return;
    }

    changeCurBridge(localBridge.__indx, i, localBridge.__x1, localBridge.__y1, localBridge.__x2, localBridge.__y2, localBridge.__offsetK);

    fillComboBox2(this.newCurType, this.newCurItem);
  }

  private void bridgeOffset(boolean paramBoolean) {
    if (!setCurBridge()) return;
    Bridge localBridge = this.curBridge;
    float f = localBridge.__offsetK;
    if (paramBoolean) {
      f += 0.02F;
      if (f > 0.4F) return; 
    }
    else {
      f -= 0.02F;
      if (f < -0.4F) return;
    }
    changeCurBridge(localBridge.__indx, localBridge.__type, localBridge.__x1, localBridge.__y1, localBridge.__x2, localBridge.__y2, f);
  }

  private void bridgeLen(boolean paramBoolean) {
    if (!setCurBridge()) return;
    Bridge localBridge = this.curBridge;
    int i = localBridge.__x2 - localBridge.__x1;
    int j = localBridge.__y2 - localBridge.__y1;
    if (paramBoolean) {
      if (i < 0) i = -1;
      else if (i > 0) i = 1;
      if (j < 0) j = -1;
      else if (j > 0) j = 1; 
    }
    else {
      if (i < 0) i = 1;
      else if (i > 0) i = -1;
      if (j < 0) j = 1;
      else if (j > 0) j = -1;
    }
    int k = localBridge.__x2 + i;
    int m = localBridge.__y2 + j;
    if ((k == localBridge.__x1) && (m == localBridge.__y1))
      return;
    changeCurBridge(localBridge.__indx, localBridge.__type, localBridge.__x1, localBridge.__y1, k, m, localBridge.__offsetK);
  }

  private void bridgeRotate(boolean paramBoolean) {
    if (!setCurBridge()) return;
    Bridge localBridge = this.curBridge;
    int i = localBridge.__x2 - localBridge.__x1;
    int j = localBridge.__y2 - localBridge.__y1;
    if (paramBoolean) {
      if (i > 0) {
        if (j > 0)
          j = 0;
        else if (j < 0)
          i = 0;
        else
          j = -i;
      }
      else if (i < 0) {
        if (j > 0)
          i = 0;
        else if (j < 0)
          j = 0;
        else {
          j = -i;
        }
      }
      else if (j > 0)
        i = j;
      else if (j < 0) {
        i = j;
      }

    }
    else if (i > 0) {
      if (j > 0)
        i = 0;
      else if (j < 0)
        j = 0;
      else
        j = i;
    }
    else if (i < 0) {
      if (j > 0)
        j = 0;
      else if (j < 0)
        i = 0;
      else {
        j = i;
      }
    }
    else if (j > 0)
      i = -j;
    else if (j < 0) {
      i = -j;
    }

    int k = localBridge.__x1 + i;
    int m = localBridge.__y1 + j;
    if ((k == localBridge.__x1) && (m == localBridge.__y1))
      return;
    changeCurBridge(localBridge.__indx, localBridge.__type, localBridge.__x1, localBridge.__y1, k, m, localBridge.__offsetK);
  }

  private void doClearWater() {
    Plugin.builder.selectActorsClear();
    Plugin.builder.setSelected(null);
    Landscape localLandscape = World.land();
    ArrayList localArrayList = new ArrayList();
    Map.Entry localEntry = this.allActors.nextEntry(null);
    Object localObject;
    while (localEntry != null) {
      Actor localActor = (Actor)localEntry.getKey();
      if ((Actor.isValid(localActor)) && ((localActor instanceof ActorMesh))) {
        Point3d localPoint3d = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
        localObject = ((ActorMesh)localActor).mesh();
        double d1 = 0.0D;
        if ((localObject instanceof HierMesh)) {
          d1 = ((ActorMesh)localActor).mesh().visibilityR() + 2.0F;
        } else {
          ((Mesh)localObject).getBoundBox(this._clearRoadBound);
          double d2 = Math.max(Math.abs(this._clearRoadBound[0]), Math.abs(this._clearRoadBound[3])) + 2.0F;
          double d3 = Math.max(Math.abs(this._clearRoadBound[1]), Math.abs(this._clearRoadBound[4])) + 2.0F;
          d1 = Math.max(d2, d3);
        }
        d1 /= 2.0D;
        if ((localLandscape.isWater(localPoint3d.jdField_x_of_type_Double - d1, localPoint3d.jdField_y_of_type_Double - d1)) || (localLandscape.isWater(localPoint3d.jdField_x_of_type_Double - d1, localPoint3d.jdField_y_of_type_Double + d1)) || (localLandscape.isWater(localPoint3d.jdField_x_of_type_Double + d1, localPoint3d.jdField_y_of_type_Double + d1)) || (localLandscape.isWater(localPoint3d.jdField_x_of_type_Double + d1, localPoint3d.jdField_y_of_type_Double - d1)))
        {
          localArrayList.add(localActor);
        }
      }
      localEntry = this.allActors.nextEntry(localEntry);
    }
    int i = localArrayList.size();
    for (int j = 0; j < i; j++) {
      localObject = (Actor)localArrayList.get(j);
      this.allActors.remove(localObject);
      ((Actor)localObject).destroy();
    }
  }

  private void doRemoveRoads() {
    Plugin.builder.selectActorsClear();
    Plugin.builder.setSelected(null);
    this.bTMapChanged = true;
    Landscape localLandscape = World.land();
    int i = Landscape.getSizeXpix();
    int j = Landscape.getSizeYpix();
    for (int k = 1; k < j - 1; k++) {
      int m = j - 1 - k;
      int i1;
      for (int n = 1; n < i - 1; n++) {
        i1 = this.landMapT.intI(n, m);
        if ((i1 & 0xE0) == 0)
          continue;
        i1 &= -225;
        this.landMapT.I(n, m, i1);
        World.land(); Landscape.setPixelMapT(n, m, i1);
      }
      if (k % 5 == 0) {
        i1 = k * 100 / j;
        RTSConf.cur.mainWindow.setTitle("" + i1 + "%");
      }
    }
    RTSConf.cur.mainWindow.setTitle("100% ");
  }
  private void doClearRoads() {
    Plugin.builder.selectActorsClear();
    Plugin.builder.setSelected(null);
    Landscape localLandscape = World.land();
    int i = Landscape.getSizeXpix();
    int j = Landscape.getSizeYpix();
    for (int k = 1; k < j - 1; k++)
    {
      int n;
      for (int m = 1; m < i - 1; m++) {
        n = j - 1 - k;
        int i1 = this.landMapT.intI(m, n) & 0xE0;
        if (i1 != 0) {
          tryClearRoads(i1, m, k, -1, 1, j);
          tryClearRoads(i1, m, k, 0, 1, j);
          tryClearRoads(i1, m, k, 1, 1, j);
          tryClearRoads(i1, m, k, 1, 0, j);
        }
      }
      if (k % 5 == 0) {
        n = k * 100 / j;
        RTSConf.cur.mainWindow.setTitle("" + n + "%");
      }
    }
    RTSConf.cur.mainWindow.setTitle("100% ");
  }
  private void tryClearRoads(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    int i = paramInt6 - 1 - paramInt3 - paramInt5;
    if ((this.landMapT.intI(paramInt2 + paramInt4, i) & 0xE0 & paramInt1) == 0)
      return;
    this._clearRoadP0.jdField_x_of_type_Double = (paramInt2 * 200.0F + 100.0F);
    this._clearRoadP0.jdField_y_of_type_Double = ((paramInt3 - 1) * 200.0F + 100.0F);
    this._clearRoadP0.jdField_z_of_type_Double = (World.land().HQ(this._clearRoadP0.jdField_x_of_type_Double, this._clearRoadP0.jdField_y_of_type_Double) + 1.0D);
    this._clearRoadP1.jdField_x_of_type_Double = ((paramInt2 + paramInt4) * 200.0F + 100.0F);
    this._clearRoadP1.jdField_y_of_type_Double = ((paramInt3 - 1 + paramInt5) * 200.0F + 100.0F);
    this._clearRoadP1.jdField_z_of_type_Double = (World.land().HQ(this._clearRoadP1.jdField_x_of_type_Double, this._clearRoadP1.jdField_y_of_type_Double) + 1.0D);

    Engine.drawEnv().getFiltered(this._clearRoadArray, this._clearRoadP0.jdField_x_of_type_Double - 100.0D, this._clearRoadP0.jdField_y_of_type_Double - 100.0D, this._clearRoadP0.jdField_x_of_type_Double + 100.0D, this._clearRoadP0.jdField_y_of_type_Double + 100.0D, 15, this._clearRoadFilter);

    Engine.drawEnv().getFiltered(this._clearRoadArray, this._clearRoadP1.jdField_x_of_type_Double - 100.0D, this._clearRoadP1.jdField_y_of_type_Double - 100.0D, this._clearRoadP1.jdField_x_of_type_Double + 100.0D, this._clearRoadP1.jdField_y_of_type_Double + 100.0D, 15, this._clearRoadFilter);

    if (this._clearRoadArray.size() == 0) return;
    for (int j = 0; j < this._clearRoadArray.size(); j++) {
      Actor localActor = (Actor)this._clearRoadArray.get(j);
      if ((this.allActors.containsKey(localActor)) && ((localActor instanceof ActorMesh))) {
        Point3d localPoint3d = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint();
        Mesh localMesh = ((ActorMesh)localActor).mesh();
        double d1 = 0.0D;
        if ((localMesh instanceof HierMesh)) {
          d1 = ((ActorMesh)localActor).mesh().visibilityR() + 2.0F;
          d1 *= d1;
        } else {
          localMesh.getBoundBox(this._clearRoadBound);
          double d2 = Math.max(Math.abs(this._clearRoadBound[0]), Math.abs(this._clearRoadBound[3])) + 2.0F;
          double d3 = Math.max(Math.abs(this._clearRoadBound[1]), Math.abs(this._clearRoadBound[4])) + 2.0F;
          d1 = d2 * d2 + d3 * d3;
        }
        if (intersectLineSphere(this._clearRoadP0.jdField_x_of_type_Double, this._clearRoadP0.jdField_y_of_type_Double, this._clearRoadP1.jdField_x_of_type_Double, this._clearRoadP1.jdField_y_of_type_Double, localPoint3d.jdField_x_of_type_Double, localPoint3d.jdField_y_of_type_Double, d1) < 0.0D)
        {
          continue;
        }
        this.allActors.remove(localActor);
        localActor.destroy();
      }
    }

    this._clearRoadArray.clear();
  }

  private double intersectLineSphere(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7)
  {
    double d1 = paramDouble3 - paramDouble1;
    double d2 = paramDouble4 - paramDouble2;
    double d3 = d1 * d1 + d2 * d2;
    if (d3 < 1.0E-006D) {
      if (paramDouble7 >= (paramDouble1 - paramDouble5) * (paramDouble1 - paramDouble5) + (paramDouble2 - paramDouble6) * (paramDouble2 - paramDouble6)) return 0.0D;
      return -1.0D;
    }
    double d4 = ((paramDouble5 - paramDouble1) * d1 + (paramDouble6 - paramDouble2) * d2) / d3;
    if ((d4 >= 0.0D) && (d4 <= 1.0D)) {
      d5 = paramDouble1 + d4 * d1;
      d6 = paramDouble2 + d4 * d2;
      double d7 = (d5 - paramDouble5) * (d5 - paramDouble5) + (d6 - paramDouble6) * (d6 - paramDouble6);
      double d8 = paramDouble7 - d7;
      if (d8 < 0.0D) {
        return -1.0D;
      }
      if (d4 < 0.0D) d4 = 0.0D;
      return d4;
    }
    double d5 = (paramDouble3 - paramDouble5) * (paramDouble3 - paramDouble5) + (paramDouble4 - paramDouble6) * (paramDouble4 - paramDouble6);
    double d6 = (paramDouble1 - paramDouble5) * (paramDouble1 - paramDouble5) + (paramDouble2 - paramDouble6) * (paramDouble2 - paramDouble6);
    if ((d5 <= paramDouble7) || (d6 <= paramDouble7)) {
      if (d5 < d6) return 1.0D;
      return 0.0D;
    }
    return -1.0D;
  }

  private void doInsertPattern(GWindow paramGWindow)
  {
    int i = Plugin.builder.wSelect.comboBox2.getSelected();
    int j = tileType(i);
    if (j == -1) {
      System.out.println("Unknown type of ladscape");
      return;
    }
    this._changedPatternType = j;
    if (this.dlgLoadPattern == null)
      this.dlgLoadPattern = new GWindowFileOpen(paramGWindow, true, "Load Pattern ...", "maps", new GFileFilter[] { new GFileFilterName("All files", new String[] { "*" }) })
      {
        public void result(String paramString)
        {
          if (paramString != null)
            PlMapActors.this.changePattern(true, "maps/" + paramString);
        }
      };
    else this.dlgLoadPattern.activateWindow();
  }

  private void doRemovePattern(GWindow paramGWindow)
  {
    int i = Plugin.builder.wSelect.comboBox2.getSelected();
    int j = tileType(i);
    if (j == -1) {
      System.out.println("Unknown type of ladscape");
      return;
    }
    this._changedPatternType = j;
    if (this.dlgRemovePattern == null)
      this.dlgRemovePattern = new GWindowFileOpen(paramGWindow, true, "Load Pattern ...", "maps", new GFileFilter[] { new GFileFilterName("All files", new String[] { "*" }) })
      {
        public void result(String paramString)
        {
          if (paramString != null)
            PlMapActors.this.changePattern(false, "maps/" + paramString);
        }
      };
    else this.dlgRemovePattern.activateWindow();
  }

  private int changeOnePattern(ArrayList paramArrayList, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    int j = paramArrayList.size();
    this._changeOffsetXY.jdField_x_of_type_Float = ((float)this.PIXEL * paramInt1);
    this._changeOffsetXY.jdField_y_of_type_Float = ((float)this.PIXEL * (paramInt2 - 1));
    for (int k = 0; k < j; k++) {
      ItemPattern localItemPattern = (ItemPattern)paramArrayList.get(k);
      if (paramInt3 == 0 ? 
        (localItemPattern.jdField_x_of_type_Float < this.PIXELD2) && 
        (localItemPattern.jdField_y_of_type_Float < this.PIXELD2) : 
        paramInt3 == 1 ? 
        (localItemPattern.jdField_x_of_type_Float > this.PIXELD2) && 
        (localItemPattern.jdField_y_of_type_Float < this.PIXELD2) : 
        paramInt3 == 2 ? 
        (localItemPattern.jdField_x_of_type_Float < this.PIXELD2) && 
        (localItemPattern.jdField_y_of_type_Float > this.PIXELD2) : 
        (paramInt3 == 3) && (
        (localItemPattern.jdField_x_of_type_Float > this.PIXELD2) || 
        (localItemPattern.jdField_y_of_type_Float > this.PIXELD2)))
        continue;
      if (paramBoolean)
      {
        this._changeLoc.set(localItemPattern.jdField_x_of_type_Float + this._changeOffsetXY.jdField_x_of_type_Float, localItemPattern.jdField_y_of_type_Float + this._changeOffsetXY.jdField_y_of_type_Float, 0.0D, localItemPattern.getAzimut(), 0.0F, 0.0F);

        insert(localItemPattern.spawn, this._changeLoc, false);
        i++;
      }
      else
      {
        Actor localActor = findParrtenActor(localItemPattern, this._changeOffsetXY);
        if (localActor != null) {
          this.allActors.remove(localActor);
          localActor.destroy();
          i++;
        }
      }
    }
    return i;
  }

  private void changePattern(boolean paramBoolean, String paramString)
  {
    int i = 0;
    Plugin.builder.selectActorsClear();
    Plugin.builder.setSelected(null);
    ArrayList[][] arrayOfArrayList = new ArrayList[this.TILE][this.TILE];
    loadPattern(paramString, arrayOfArrayList);
    Landscape localLandscape = World.land();
    int j = Landscape.getSizeXpix();
    int k = Landscape.getSizeYpix();
    for (int m = 1; m < k - 1; m++)
    {
      int i1;
      for (int n = 1; n < j - 1; n++) {
        i1 = k - 1 - m;
        int i2 = Landscape.getPixelMapT(n, i1) & 0x1F;
        if (i2 != this._changedPatternType)
        {
          continue;
        }

        ArrayList localArrayList = arrayOfArrayList[(m & this.TILEMASK)][(n + this.TILE - 1 & this.TILEMASK)];
        if ((localArrayList != null) && (localArrayList.size() > 0))
          i += changeOnePattern(localArrayList, paramBoolean, n - 1, m, 0);
        localArrayList = arrayOfArrayList[(m & this.TILEMASK)][(n & this.TILEMASK)];
        if ((localArrayList != null) && (localArrayList.size() > 0))
          i += changeOnePattern(localArrayList, paramBoolean, n, m, 1);
        localArrayList = arrayOfArrayList[(m + 1 & this.TILEMASK)][(n + this.TILE - 1 & this.TILEMASK)];
        if ((localArrayList != null) && (localArrayList.size() > 0))
          i += changeOnePattern(localArrayList, paramBoolean, n - 1, m + 1, 2);
        localArrayList = arrayOfArrayList[(m + 1 & this.TILEMASK)][(n & this.TILEMASK)];
        if ((localArrayList != null) && (localArrayList.size() > 0))
          i += changeOnePattern(localArrayList, paramBoolean, n, m + 1, 3);
      }
      if (m % 5 == 0) {
        i1 = m * 100 / k;
        RTSConf.cur.mainWindow.setTitle("" + i1 + "% " + i);
      }
    }
    RTSConf.cur.mainWindow.setTitle("100% " + i);
    String str = null;
    if (paramBoolean)
      str = " " + i + " pattern objects inserted as static";
    else
      str = " " + i + " pattern objects removed from static";
    System.out.println(str); Plugin.builder.tip(str);
  }

  public Actor findParrtenActor(ItemPattern paramItemPattern, Point3f paramPoint3f) {
    double d1 = paramItemPattern.jdField_x_of_type_Float + paramPoint3f.jdField_x_of_type_Float;
    double d2 = paramItemPattern.jdField_y_of_type_Float + paramPoint3f.jdField_y_of_type_Float;
    Map.Entry localEntry = this.allActors.nextEntry(null);
    while (localEntry != null) {
      Actor localActor = (Actor)localEntry.getKey();
      if (Actor.isValid(localActor)) {
        double d3 = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_x_of_type_Double;
        double d4 = localActor.jdField_pos_of_type_ComMaddoxIl2EngineActorPos.getAbsPoint().jdField_y_of_type_Double;
        double d5 = (d1 - d3) * (d1 - d3) + (d2 - d4) * (d2 - d4);
        if ((d5 < 1.0D) && 
          (getFingerOfFullClassName(localActor) == paramItemPattern.finger)) {
          return localActor;
        }
      }

      localEntry = this.allActors.nextEntry(localEntry);
    }
    return null;
  }

  private void loadPattern(String paramString, ArrayList[][] paramArrayOfArrayList) {
    try {
      DataInputStream localDataInputStream = new DataInputStream(new SFSInputStream(paramString));

      System.out.println("Read pattern objects ...");
      int i = 1;
      int j = localDataInputStream.readInt();
      if (j == -65535) {
        i = 0;
        j = localDataInputStream.readInt();
      }
      int k = 0;
      int m = 0;
      int i1;
      float f2;
      float f3;
      float f4;
      if (i != 0) {
        for (int n = 0; n < j; n++) {
          localDataInputStream.readInt(); localDataInputStream.readInt();
          localDataInputStream.readInt(); localDataInputStream.readInt();
          localDataInputStream.readInt(); localDataInputStream.readFloat();
        }
        j = localDataInputStream.readInt();
        k = j;
        if (j > 0) {
          while (j-- > 0) {
            String str2 = localDataInputStream.readUTF();
            Spawn.get_WithSoftClass(str2);
          }
          j = localDataInputStream.readInt();
          m = j;
          while (j-- > 0) {
            i1 = localDataInputStream.readInt();
            float f1 = localDataInputStream.readFloat();
            f2 = localDataInputStream.readFloat();
            f3 = localDataInputStream.readFloat();
            f4 = localDataInputStream.readFloat();
            float f5 = localDataInputStream.readFloat();
            float f6 = localDataInputStream.readFloat();
            addPattern(i1, f1, f2, f4, paramArrayOfArrayList);
          }
        }
      }
      else {
        j = localDataInputStream.readInt();
        k = j;
        int[] arrayOfInt = null;
        if (j > 0) {
          arrayOfInt = new int[j];
          for (i1 = 0; i1 < j; i1++) {
            String str3 = localDataInputStream.readUTF();
            arrayOfInt[i1] = Finger.Int(str3);
            Spawn.get_WithSoftClass(str3);
          }
          j = localDataInputStream.readInt();
          m = j;
          while (j-- > 0) {
            int i2 = localDataInputStream.readInt();
            f2 = localDataInputStream.readFloat();
            f3 = localDataInputStream.readFloat();
            f4 = localDataInputStream.readFloat();
            addPattern(arrayOfInt[i2], f2, f3, f4, paramArrayOfArrayList);
          }
        }

        j = localDataInputStream.readInt();
        k += j;
        if (j > 0) {
          arrayOfInt = new int[j];
          for (i1 = 0; i1 < j; i1++) {
            String str4 = localDataInputStream.readUTF();
            arrayOfInt[i1] = Finger.Int(str4);
            Spawn.get_WithSoftClass(str4);
          }
          int i3 = localDataInputStream.readInt();
          while (i3-- > 0) {
            int i4 = localDataInputStream.readInt();
            f3 = (i4 & 0xFFFF) * 200.0F;
            f4 = (i4 >> 16 & 0xFFFF) * 200.0F;
            int i5 = localDataInputStream.readInt();
            m += j;
            while (i5-- > 0) {
              int i6 = localDataInputStream.readInt();
              int i7 = localDataInputStream.readInt();
              int i8 = i6 & 0x7FFF;
              if ((i8 < arrayOfInt.length) && (arrayOfInt[i8] != 0)) {
                int i9 = (short)(i6 >> 16);
                int i10 = (short)(i7 & 0xFFFF);
                int i11 = (short)(i7 >> 16 & 0xFFFF);
                float f7 = i9 * 360.0F / 32000.0F;
                float f8 = i10 * 200.0F / 32000.0F + f3;
                float f9 = i11 * 200.0F / 32000.0F + f4;
                addPattern(arrayOfInt[i8], f8, f9, -f7, paramArrayOfArrayList);
              }
            }
          }
        }
      }
      localDataInputStream.close();
      System.out.println("" + k + " class " + m + " actors in pattern");
    } catch (Exception localException) {
      String str1 = "Pattern actors load from '" + paramString + "' FAILED: " + localException.getMessage();
      System.out.println(str1);
      localException.printStackTrace();
    }
  }

  private void addPattern(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, ArrayList[][] paramArrayOfArrayList) {
    paramFloat2 = (float)(paramFloat2 + this.PIXEL);
    double d1 = paramFloat1 % this.QUAD;
    double d2 = paramFloat2 % this.QUAD;
    int i = (int)(d1 / this.PIXEL);
    int j = (int)(d2 / this.PIXEL);
    if (paramArrayOfArrayList[j][i] == null)
      paramArrayOfArrayList[j][i] = new ArrayList();
    paramArrayOfArrayList[j][i].add(new ItemPattern(paramInt, (float)(d1 % this.PIXEL), (float)(d2 % this.PIXEL), paramFloat3));
  }

  public void freeResources() {
    this.dlgPasteFrom = null;
    this.dlgLoadAs = null;
    this.dlgLoadSpawn = null;
    this.dlgLoadPattern = null;
    this.dlgRemovePattern = null;
    this.dlgSaveAs = null;
    this.dlgSaveSpawn = null;
  }
  static {
    Property.set(PlMapActors.class, "name", "MapActors");
  }

  static class ItemPattern
  {
    public int finger;
    public int flags;
    public float x;
    public float y;
    public ActorSpawn spawn;

    public float getAzimut()
    {
      return (this.flags >> 16) + (this.flags >> 8 & 0xFF) / 256.0F;
    }
    public ItemPattern(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
      this.finger = paramInt;
      this.x = paramFloat1;
      this.y = paramFloat2;
      this.flags = ((int)paramFloat3 << 16 | ((int)(paramFloat3 * 256.0F) & 0xFF) << 8);

      this.spawn = ((ActorSpawn)Spawn.get(paramInt));
    }
  }

  private static class ClearRoadFilter
    implements ActorFilter
  {
    private ClearRoadFilter()
    {
    }

    public boolean isUse(Actor paramActor, double paramDouble)
    {
      return paramActor instanceof ActorMesh;
    }

    ClearRoadFilter(PlMapActors.1 param1)
    {
      this();
    }
  }

  class ViewItem extends GWindowMenuItem
  {
    int indx;

    public void execute()
    {
      this.jdField_bChecked_of_type_Boolean = (!this.jdField_bChecked_of_type_Boolean);
      PlMapActors.this.viewType(this.indx);
    }
    public ViewItem(int paramGWindowMenu, GWindowMenu paramString1, String paramString2, String arg5) {
      super(paramString2, str);
      this.indx = paramGWindowMenu;
    }
  }

  static class Type
  {
    public String name;
    public PlMapActors.Item[] item;

    public Type(String paramString, PlMapActors.Item[] paramArrayOfItem)
    {
      this.name = paramString;
      this.item = paramArrayOfItem;
    }
  }

  static class Item
  {
    public String name;
    ActorSpawn spawn_;
    String fullClassName;
    int fingerOfFullClassName;

    public Item(String paramString1, String paramString2)
    {
      this.name = paramString1;
      this.fullClassName = paramString2;
      this.fingerOfFullClassName = Finger.Int(this.fullClassName);
      this.spawn_ = ((ActorSpawn)Spawn.get_WithSoftClass(this.fullClassName));
    }

    public Item(String paramString)
    {
      this.name = paramString;
      this.fullClassName = null;
      this.spawn_ = null;
      this.fingerOfFullClassName = Finger.Int("* an impossible name *");
    }
  }
}