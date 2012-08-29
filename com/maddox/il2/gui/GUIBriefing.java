package com.maddox.il2.gui;

import com.maddox.JGP.Point2d;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GWindow;
import com.maddox.il2.ai.Army;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.GUIRenders;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.NetEnv;
import com.maddox.rts.ObjIO;
import com.maddox.rts.Property;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class GUIBriefing extends GUIBriefingGeneric
{
  protected String playerName;
  protected ArrayList playerPath = new ArrayList();
  protected ArrayList targets = new ArrayList();
  protected Mat iconBornPlace;
  protected Mat iconPlayer;
  private static String[] tip = new String[3];

  private float[] lineNXYZ = new float[6];
  private int lineNCounter;
  protected boolean bSelectBorn = false;

  public void _enter()
  {
    this.playerPath.clear();
    this.playerName = null;
    super._enter();
  }

  private void drawBornPlaces() {
    if (this.iconBornPlace == null)
      return;
    ArrayList localArrayList = World.cur().bornPlaces;
    if ((localArrayList == null) || (localArrayList.size() == 0))
      return;
    int i = localArrayList.size();

    for (int j = 0; j < i; j++) {
      localObject1 = (BornPlace)localArrayList.get(j);
      ((BornPlace)localObject1).tmpForBrief = 0;
    }

    Object localObject1 = (NetUser)NetEnv.host();
    int k = ((NetUser)localObject1).getBornPlace();
    if ((k >= 0) && (k < i)) {
      localObject2 = (BornPlace)localArrayList.get(k);
      ((BornPlace)localObject2).tmpForBrief = 1;
    }
    Object localObject2 = NetEnv.hosts();
    for (int m = 0; m < ((List)localObject2).size(); m++) {
      NetUser localNetUser = (NetUser)((List)localObject2).get(m);
      int i1 = localNetUser.getBornPlace();
      if ((i1 >= 0) && (i1 < i)) {
        BornPlace localBornPlace2 = (BornPlace)localArrayList.get(i1);
        localBornPlace2.tmpForBrief += 1;
      }
    }

    for (int n = 0; n < i; n++) {
      BornPlace localBornPlace1 = (BornPlace)localArrayList.get(n);
      IconDraw.setColor(Army.color(localBornPlace1.army));
      float f1 = (float)((localBornPlace1.place.jdField_x_of_type_Double - this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldXOffset) * this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldScale);
      float f2 = (float)((localBornPlace1.place.jdField_y_of_type_Double - this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldYOffset) * this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldScale);
      IconDraw.render(this.iconBornPlace, f1, f2);
      if ((n == k) && (this.iconPlayer != null)) {
        Render.drawTile(f1, f2, IconDraw.scrSizeX(), IconDraw.scrSizeY(), 0.0F, this.iconPlayer, Army.color(localBornPlace1.army), 0.0F, 1.0F, 1.0F, -1.0F);
      }

      if (localBornPlace1.tmpForBrief > 0)
        this.jdField_gridFont_of_type_ComMaddoxIl2EngineTTFont.output(Army.color(localBornPlace1.army), (int)f1 + IconDraw.scrSizeX() / 2 + 2, (int)f2 - IconDraw.scrSizeY() / 2 - 2, 0.0F, "" + localBornPlace1.tmpForBrief);
    }
  }

  public static void fillTargets(SectFile paramSectFile, ArrayList paramArrayList)
  {
    paramArrayList.clear();
    int i = paramSectFile.sectionIndex("Target");
    if (i >= 0) {
      int j = paramSectFile.vars(i);
      for (int k = 0; k < j; k++) {
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
        int m = localNumberTokenizer.next(0, 0, 7);
        int n = localNumberTokenizer.next(0, 0, 2);
        if (n == 2)
          continue;
        TargetPoint localTargetPoint = new TargetPoint();
        localTargetPoint.type = m;
        localTargetPoint.importance = n;
        int i1 = localNumberTokenizer.next(0) == 1 ? 1 : 0;
        int i2 = localNumberTokenizer.next(0, 0, 720);
        int i3 = localNumberTokenizer.next(0) == 1 ? 1 : 0;
        localTargetPoint.x = localNumberTokenizer.next(0);
        localTargetPoint.y = localNumberTokenizer.next(0);
        int i4 = localNumberTokenizer.next(0);
        if ((localTargetPoint.type == 3) || (localTargetPoint.type == 6) || (localTargetPoint.type == 1))
        {
          if (i4 < 50) i4 = 50;
          if (i4 > 3000) i4 = 3000;
        }
        localTargetPoint.r = i4;
        int i5 = localNumberTokenizer.next(0);
        localTargetPoint.nameTarget = localNumberTokenizer.next(null);
        if ((localTargetPoint.nameTarget != null) && (localTargetPoint.nameTarget.startsWith("Bridge")))
          localTargetPoint.nameTarget = null;
        int i6 = localNumberTokenizer.next(0);
        int i7 = localNumberTokenizer.next(0);
        if ((i6 != 0) && (i7 != 0)) {
          localTargetPoint.x = i6; localTargetPoint.y = i7;
        }
        switch (localTargetPoint.type) { case 0:
          localTargetPoint.icon = IconDraw.get("icons/tdestroyair.mat");
          if ((localTargetPoint.nameTarget == null) || 
            (!paramSectFile.exist("Chiefs", localTargetPoint.nameTarget))) break;
          localTargetPoint.icon = IconDraw.get("icons/tdestroychief.mat"); break;
        case 1:
          localTargetPoint.icon = IconDraw.get("icons/tdestroyground.mat"); break;
        case 2:
          localTargetPoint.icon = IconDraw.get("icons/tdestroybridge.mat");
          localTargetPoint.nameTarget = null;
          break;
        case 3:
          localTargetPoint.icon = IconDraw.get("icons/tinspect.mat"); break;
        case 4:
          localTargetPoint.icon = IconDraw.get("icons/tescort.mat"); break;
        case 5:
          localTargetPoint.icon = IconDraw.get("icons/tdefence.mat"); break;
        case 6:
          localTargetPoint.icon = IconDraw.get("icons/tdefenceground.mat"); break;
        case 7:
          localTargetPoint.icon = IconDraw.get("icons/tdefencebridge.mat");
          localTargetPoint.nameTarget = null;
        }

        if (localTargetPoint.nameTarget != null)
        {
          Object localObject;
          if (paramSectFile.exist("Chiefs", localTargetPoint.nameTarget)) {
            try {
              StringTokenizer localStringTokenizer = new StringTokenizer(paramSectFile.get("Chiefs", localTargetPoint.nameTarget, (String)null));
              localObject = localStringTokenizer.nextToken();
              int i8 = ((String)localObject).indexOf(".");
              localTargetPoint.nameTarget = (I18N.technic(((String)localObject).substring(0, i8)) + " " + I18N.technic(((String)localObject).substring(i8 + 1)));
            }
            catch (Exception localException1)
            {
              localTargetPoint.nameTarget = null;
            }
          }
          else if (paramSectFile.sectionIndex(localTargetPoint.nameTarget) >= 0)
            try {
              String str = paramSectFile.get(localTargetPoint.nameTarget, "Class", (String)null);
              localObject = ObjIO.classForName(str);
              localTargetPoint.nameTarget = Property.stringValue(localObject, "iconFar_shortClassName", null);
            } catch (Exception localException2) {
              localTargetPoint.nameTarget = null;
            }
          else {
            localTargetPoint.nameTarget = null;
          }
        }
        paramArrayList.add(localTargetPoint);
      }
    }
  }

  public static void drawTargets(GUIRenders paramGUIRenders, TTFont paramTTFont, Mat paramMat, CameraOrtho2D paramCameraOrtho2D, ArrayList paramArrayList)
  {
    int i = paramArrayList.size();
    if (i == 0) return;
    GPoint localGPoint = paramGUIRenders.getMouseXY();
    int j = -1;
    float f1 = localGPoint.x;
    float f2 = paramGUIRenders.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - 1.0F - localGPoint.y;
    float f3 = IconDraw.scrSizeX() / 2;
    float f4 = f1; float f5 = f2;
    IconDraw.setColor(-16711681);
    TargetPoint localTargetPoint;
    float f7;
    for (int k = 0; k < i; k++) {
      localTargetPoint = (TargetPoint)paramArrayList.get(k);
      if (localTargetPoint.icon != null) {
        float f6 = (float)((localTargetPoint.x - paramCameraOrtho2D.worldXOffset) * paramCameraOrtho2D.worldScale);
        f7 = (float)((localTargetPoint.y - paramCameraOrtho2D.worldYOffset) * paramCameraOrtho2D.worldScale);
        IconDraw.render(localTargetPoint.icon, f6, f7);
        if ((f6 >= f1 - f3) && (f6 <= f1 + f3) && (f7 >= f2 - f3) && (f7 <= f2 + f3)) {
          j = k;
          f4 = f6; f5 = f7;
        }
      }
    }
    if (j != -1) {
      localTargetPoint = (TargetPoint)paramArrayList.get(j);
      for (int m = 0; m < 3; m++) tip[m] = null;
      if (localTargetPoint.importance == 0) tip[0] = I18N.gui("brief.Primary"); else
        tip[0] = I18N.gui("brief.Secondary");
      switch (localTargetPoint.type) { case 0:
        tip[1] = I18N.gui("brief.Destroy"); break;
      case 1:
        tip[1] = I18N.gui("brief.DestroyGround"); break;
      case 2:
        tip[1] = I18N.gui("brief.DestroyBridge"); break;
      case 3:
        tip[1] = I18N.gui("brief.Inspect"); break;
      case 4:
        tip[1] = I18N.gui("brief.Escort"); break;
      case 5:
        tip[1] = I18N.gui("brief.Defence"); break;
      case 6:
        tip[1] = I18N.gui("brief.DefenceGround"); break;
      case 7:
        tip[1] = I18N.gui("brief.DefenceBridge");
      }
      if (localTargetPoint.nameTarget != null) {
        tip[2] = localTargetPoint.nameTarget;
      }

      f7 = paramTTFont.width(tip[0]);
      int n = 1;
      for (int i1 = 1; i1 < 3; i1++) {
        if (tip[i1] == null) {
          break;
        }
        n = i1;
        f8 = paramTTFont.width(tip[i1]);
        if (f7 >= f8) continue; f7 = f8;
      }
      float f8 = -paramTTFont.descender();
      float f9 = paramTTFont.height() + f8;
      f7 += 2.0F * f8;
      float f10 = f9 * (n + 1) + 2.0F * f8;

      float f11 = f4 - f7 / 2.0F;
      float f12 = f5 + f3;
      if (f11 + f7 > paramGUIRenders.jdField_win_of_type_ComMaddoxGwindowGRegion.dx) f11 = paramGUIRenders.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - f7;
      if (f12 + f10 > paramGUIRenders.jdField_win_of_type_ComMaddoxGwindowGRegion.dy) f12 = paramGUIRenders.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - f10;
      if (f11 < 0.0F) f11 = 0.0F;
      if (f12 < 0.0F) f12 = 0.0F;

      Render.drawTile(f11, f12, f7, f10, 0.0F, paramMat, -813694977, 0.0F, 0.0F, 1.0F, 1.0F);
      Render.drawEnd();
      for (int i2 = 0; i2 <= n; i2++)
        paramTTFont.output(-16777216, f11 + f8, f12 + f8 + (n - i2) * f9 + f8, 0.0F, tip[i2]);
    }
  }

  private void drawTargets()
  {
    drawTargets(this.jdField_renders_of_type_ComMaddoxIl2EngineGUIRenders, this.jdField_gridFont_of_type_ComMaddoxIl2EngineTTFont, this.emptyMat, this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D, this.targets);
  }

  private Mat getIconAir(int paramInt) {
    String str = null;
    switch (paramInt) { case 0:
      str = "normfly"; break;
    case 1:
      str = "takeoff"; break;
    case 2:
      str = "landing"; break;
    case 3:
      str = "gattack"; break;
    default:
      return null;
    }
    return IconDraw.get("icons/" + str + ".mat");
  }

  private void drawPlayerPath() {
    checkPlayerPath();
    int i = this.playerPath.size();
    if (i == 0) return;
    if (this.lineNXYZ.length / 3 <= i)
      this.lineNXYZ = new float[(i + 1) * 3];
    this.lineNCounter = 0;
    for (int j = 0; j < i; j++) {
      PathPoint localPathPoint1 = (PathPoint)this.playerPath.get(j);
      this.lineNXYZ[(this.lineNCounter * 3 + 0)] = (float)((localPathPoint1.x - this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldXOffset) * this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldScale);
      this.lineNXYZ[(this.lineNCounter * 3 + 1)] = (float)((localPathPoint1.y - this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldYOffset) * this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldScale);
      this.lineNXYZ[(this.lineNCounter * 3 + 2)] = 0.0F;
      this.lineNCounter += 1;
    }
    Render.drawBeginLines(-1);
    Render.drawLines(this.lineNXYZ, this.lineNCounter, 2.0F, -16777216, Mat.NOWRITEZ | Mat.MODULATE | Mat.NOTEXTURE | Mat.BLEND, 3);

    Render.drawEnd();
    IconDraw.setColor(-16711681);
    for (int k = 0; k < i; k++) {
      PathPoint localPathPoint2 = (PathPoint)this.playerPath.get(k);
      float f1 = (float)((localPathPoint2.x - this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldXOffset) * this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldScale);
      float f2 = (float)((localPathPoint2.y - this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldYOffset) * this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldScale);
      IconDraw.render(getIconAir(localPathPoint2.type), f1, f2);
      this.jdField_gridFont_of_type_ComMaddoxIl2EngineTTFont.output(-16777216, (int)f1 + IconDraw.scrSizeX() / 2 + 2, (int)f2 - IconDraw.scrSizeY() / 2 - 2, 0.0F, "" + (k + 1));
    }
  }

  private void checkPlayerPath()
  {
    SectFile localSectFile = Main.cur().currentMissionFile;
    String str1 = null;
    if (Mission.isCoop()) {
      str1 = GUINetAircraft.selectedWingName();
      if (str1 == null)
        str1 = localSectFile.get("MAIN", "player", (String)null);
    } else {
      str1 = localSectFile.get("MAIN", "player", (String)null);
    }
    if (str1 == null) {
      if (this.playerName == null) return;
      this.playerPath.clear();
      this.playerName = null;
      return;
    }
    if (str1.equals(this.playerName))
      return;
    this.playerName = str1;
    this.playerPath.clear();
    if (this.playerName != null) {
      int i = localSectFile.sectionIndex(this.playerName + "_WAY");
      if (i >= 0) {
        int j = localSectFile.vars(i);
        for (int k = 0; k < j; k++) {
          PathPoint localPathPoint = new PathPoint(null);
          String str2 = localSectFile.var(i, k);
          if ("NORMFLY".equals(str2)) localPathPoint.type = 0;
          else if ("TAKEOFF".equals(str2)) localPathPoint.type = 1;
          else if ("LANDING".equals(str2)) localPathPoint.type = 2;
          else if ("GATTACK".equals(str2)) localPathPoint.type = 3; else
            localPathPoint.type = 0;
          String str3 = localSectFile.value(i, k);
          if ((str3 == null) || (str3.length() <= 0)) {
            localPathPoint.x = (localPathPoint.y = 0.0F);
          } else {
            NumberTokenizer localNumberTokenizer = new NumberTokenizer(str3);
            localPathPoint.x = localNumberTokenizer.next(-1.0E+030F, -1.0E+030F, 1.0E+030F);
            localPathPoint.y = localNumberTokenizer.next(-1.0E+030F, -1.0E+030F, 1.0E+030F);
            double d1 = localNumberTokenizer.next(0.0D, 0.0D, 10000.0D);
            double d2 = localNumberTokenizer.next(0.0D, 0.0D, 1000.0D);
          }
          this.playerPath.add(localPathPoint);
        }
      }
    }
  }

  protected void doRenderMap2D()
  {
    if ((id() == 40) || (id() == 39))
    {
      drawBornPlaces();
    }
    else {
      drawPlayerPath();
      drawTargets();
    }
  }

  protected int findBornPlace(float paramFloat1, float paramFloat2) {
    if ((id() != 40) && (id() != 39))
      return -1;
    ArrayList localArrayList = World.cur().bornPlaces;
    if ((localArrayList == null) || (localArrayList.size() == 0))
      return -1;
    int i = localArrayList.size();
    double d = IconDraw.scrSizeX() / 2 / this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldScale;
    d = 2.0D * d * d;
    for (int j = 0; j < i; j++) {
      BornPlace localBornPlace = (BornPlace)localArrayList.get(j);
      if (((localBornPlace.place.jdField_x_of_type_Double - paramFloat1) * (localBornPlace.place.jdField_x_of_type_Double - paramFloat1) + (localBornPlace.place.jdField_y_of_type_Double - paramFloat2) * (localBornPlace.place.jdField_y_of_type_Double - paramFloat2) < d) && (localBornPlace.army != 0))
      {
        return j;
      }
    }
    return -1;
  }

  protected boolean isBornPlace(float paramFloat1, float paramFloat2) {
    return findBornPlace(paramFloat1, paramFloat2) >= 0;
  }
  protected void setBornPlace(float paramFloat1, float paramFloat2) {
    int i = findBornPlace(paramFloat1, paramFloat2);
    if (i < 0) return;
    NetUser localNetUser = (NetUser)NetEnv.host();
    int j = localNetUser.getArmy();
    localNetUser.setBornPlace(i);
    if ((j != localNetUser.getArmy()) && (this.jdField_briefSound_of_type_JavaLangString != null)) {
      String str = Main.cur().currentMissionFile.get("MAIN", "briefSound" + localNetUser.getArmy());
      if (str != null) {
        this.jdField_briefSound_of_type_JavaLangString = str;
        CmdEnv.top().exec("music LIST " + this.jdField_briefSound_of_type_JavaLangString);
      }
    }
  }

  protected void doMouseButton(int paramInt, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    if (paramInt == 0) {
      this.jdField_bLPressed_of_type_Boolean = paramBoolean;
      if (this.bSelectBorn) {
        if (this.jdField_bLPressed_of_type_Boolean) {
          float f1 = (float)(this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldXOffset + paramFloat1 / this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldScale);
          float f2 = (float)(this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldYOffset + (this.jdField_renders_of_type_ComMaddoxIl2EngineGUIRenders.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - paramFloat2 - 1.0F) / this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldScale);

          setBornPlace(f1, f2);
        }
        return;
      }
    }
    super.doMouseButton(paramInt, paramBoolean, paramFloat1, paramFloat2);
  }
  protected void doMouseMove(float paramFloat1, float paramFloat2) {
    if ((this.jdField_bLPressed_of_type_Boolean) && (!this.bSelectBorn)) {
      super.doMouseMove(paramFloat1, paramFloat2);
    } else {
      float f1 = (float)(this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldXOffset + paramFloat1 / this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldScale);
      float f2 = (float)(this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldYOffset + (this.jdField_renders_of_type_ComMaddoxIl2EngineGUIRenders.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - paramFloat2 - 1.0F) / this.jdField_cameraMap2D_of_type_ComMaddoxIl2EngineCameraOrtho2D.worldScale);

      this.bSelectBorn = isBornPlace(f1, f2);
      this.jdField_renders_of_type_ComMaddoxIl2EngineGUIRenders.mouseCursor = (this.bSelectBorn ? 2 : 3);
    }
  }

  protected void fillMap() throws Exception
  {
    super.fillMap();
    SectFile localSectFile = Main.cur().currentMissionFile;
    if ((id() == 40) || (id() == 39))
    {
      this.iconBornPlace = IconDraw.get("icons/born.mat");
      this.iconPlayer = IconDraw.get("icons/player.mat");
    }
    else {
      fillTargets(localSectFile, this.targets);
    }
  }

  protected void clientRender() {
    GUIBriefingGeneric.DialogClient localDialogClient = this.dialogClient;
    localDialogClient.draw(localDialogClient.x1024(768.0F), localDialogClient.y1024(656.0F), localDialogClient.x1024(160.0F), localDialogClient.y1024(48.0F), 2, i18n("brief.Fly"));
  }
  protected String infoMenuInfo() {
    return i18n("brief.info");
  }

  public GUIBriefing(int paramInt) {
    super(paramInt);
  }

  public static class TargetPoint
  {
    public float x;
    public float y;
    public int r;
    public int type;
    public int importance;
    public Mat icon;
    public String nameTarget;
  }

  private static class PathPoint
  {
    public int type;
    public float x;
    public float y;

    private PathPoint()
    {
    }

    PathPoint(GUIBriefing.1 param1)
    {
      this();
    }
  }
}