package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CameraOrtho2D;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.Chat;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.rts.NetEnv;
import com.maddox.rts.SectFile;
import com.maddox.util.NumberTokenizer;
import java.util.ArrayList;
import java.util.List;

public class Front
{
  private ArrayList allMarkers = new ArrayList();
  private static final double dMin = 1000.0D;
  private static final double dMax = 50000.0D;
  private static final double d2Min = 1000000.0D;
  private static final double d2Max = 2500000000.0D;
  public static final int M = 16;
  private ArrayList markers = new ArrayList();
  private int tNx;
  private int tNy;
  private double camWorldXOffset;
  private double camWorldYOffset;
  private float camLeft;
  private float camBottom;
  private byte[] mask;
  private byte[] mask2;
  private boolean bExistFront = false;
  private Mat[] frontMat = new Mat[4];

  private boolean tilesChanged = false;
  private int prevNCountMarkers = 0;
  private float prevCamLeft = 0.0F;
  private float prevCamBottom = 0.0F;
  private double prevCamWorldScale = 1.0D;
  private double prevCamWorldXOffset = 0.0D;
  private double prevCamWorldYOffset = 0.0D;
  private boolean bTilesUpdated = false;
  private Main3D main;
  private double tStep;
  private Point3d pointLand0 = new Point3d();
  private Point3d pointLand1 = new Point3d();

  public static List markers()
  {
    return World.cur().front.allMarkers;
  }
  public static void checkAllCaptured() {
    List localList = Engine.targets();
    int i = localList.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)localList.get(j);
      Object localObject;
      if ((localActor instanceof Paratrooper)) {
        localObject = (Paratrooper)localActor;
        if (!((Paratrooper)localObject).isChecksCaptured())
          ((Paratrooper)localObject).checkCaptured(); 
      } else {
        if ((!(localActor instanceof Aircraft)) || 
          (localActor.isNetMirror()))
          continue;
        localObject = (Aircraft)localActor;
        checkAircraftCaptured((Aircraft)localObject);
      }
    }
  }

  public static void checkAircraftCaptured(Aircraft paramAircraft) {
    if (Actor.isAlive(paramAircraft)) {
      if (paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isOk()) return;
      if (!isCaptured(paramAircraft)) return;
      for (int i = 0; i < paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.crew; i++) {
        if ((paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotDead(i)) || (paramAircraft.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotParatrooper(i)))
          continue;
        EventLog.onCaptured(paramAircraft, i);
        if ((!Config.isUSE_RENDER()) || 
          (paramAircraft != World.getPlayerAircraft()) || (i != Main3D.cur3D().cockpitCur.astatePilotIndx())) {
          continue;
        }
        World.setPlayerCaptured();
        if (Config.isUSE_RENDER()) HUD.log("PlayerCAPT");
        if (Mission.isNet())
          Chat.sendLog(1, "gore_captured", (NetUser)NetEnv.host(), null);
      }
    }
  }

  public static boolean isCaptured(Actor paramActor)
  {
    if ((paramActor == null) || (paramActor.pos == null)) return false;
    return isCaptured(paramActor.getArmy(), paramActor.pos.getAbsPoint().jdField_x_of_type_Double, paramActor.pos.getAbsPoint().jdField_y_of_type_Double);
  }

  public static boolean isCaptured(int paramInt, double paramDouble1, double paramDouble2)
  {
    if (paramInt == 0) return false;
    int i = army(paramDouble1, paramDouble2);
    if (i == 0) return false;

    if (paramInt == i) return false;

    List localList1 = markers();
    int j = localList1.size();
    Object localObject = null;
    double d1 = 90000000000.0D;
    for (int k = 0; k < j; k++) {
      Marker localMarker = (Marker)localList1.get(k);
      if (paramInt == localMarker.army) {
        double d3 = (localMarker.jdField_x_of_type_Double - paramDouble1) * (localMarker.jdField_x_of_type_Double - paramDouble1) + (localMarker.jdField_y_of_type_Double - paramDouble2) * (localMarker.jdField_y_of_type_Double - paramDouble2);
        if (d1 > d3) {
          d1 = d3;
          localObject = localMarker;
        }
      }
    }
    if (localObject == null) {
      return true;
    }
    double d2 = localObject.jdField_x_of_type_Double;
    double d4 = localObject.jdField_y_of_type_Double;
    double d5 = paramDouble1;
    double d6 = paramDouble2;
    while (true) {
      double d7 = (d2 - d5) * (d2 - d5) + (d4 - d6) * (d4 - d6);
      if (d7 <= 1000000.0D)
        break;
      double d8 = (d2 + d5) * 0.5D;
      d10 = (d4 + d6) * 0.5D;
      i = army(d8, d10);
      if (i == paramInt) {
        d2 = d8;
        d4 = d10; continue;
      }
      d5 = d8;
      d6 = d10;
    }

    d2 = (d2 + d5) * 0.5D;
    d4 = (d4 + d6) * 0.5D;
    d1 = (d2 - paramDouble1) * (d2 - paramDouble1) + (d4 - paramDouble2) * (d4 - paramDouble2);

    if (d1 >= 2500000000.0D) {
      return true;
    }
    List localList2 = Engine.targets();
    j = localList2.size();
    for (int m = 0; m < j; m++) {
      Actor localActor = (Actor)localList2.get(m);
      if (((localActor instanceof Aircraft)) || 
        (paramInt == localActor.getArmy())) continue;
      Point3d localPoint3d = localActor.pos.getAbsPoint();
      d10 = (localPoint3d.jdField_x_of_type_Double - paramDouble1) * (localPoint3d.jdField_x_of_type_Double - paramDouble1) + (localPoint3d.jdField_y_of_type_Double - paramDouble2) * (localPoint3d.jdField_y_of_type_Double - paramDouble2);
      if (d10 < 1000000.0D) {
        return true;
      }
    }

    double d9 = Math.sqrt(d1) / 50000.0D;
    double d10 = paramDouble1 + paramDouble2;
    if (d10 < 0.0D) d10 = -d10;
    d10 -= (int)d10;
    return 0.6D + d9 * 0.5D + (d10 - 0.5D) >= 0.5D;
  }

  public static int army(double paramDouble1, double paramDouble2) {
    return World.cur().front._army(paramDouble1, paramDouble2);
  }
  public int _army(double paramDouble1, double paramDouble2) {
    int i = this.allMarkers.size();
    if (i == 0)
      return 0;
    int j = 0;
    double d1 = 90000000000.0D;
    for (int k = 0; k < i; k++) {
      Marker localMarker = (Marker)this.allMarkers.get(k);
      double d2 = (localMarker.jdField_x_of_type_Double - paramDouble1) * (localMarker.jdField_x_of_type_Double - paramDouble1) + (localMarker.jdField_y_of_type_Double - paramDouble2) * (localMarker.jdField_y_of_type_Double - paramDouble2);
      if (d1 > d2) {
        j = localMarker.army;
        d1 = d2;
      }
    }
    return j;
  }

  public void resetGameClear() {
    this.allMarkers.clear();
    this.tilesChanged = true;
  }
  public void resetGameCreate() {
  }

  public static void loadMission(SectFile paramSectFile) {
    World.cur().front._loadMission(paramSectFile);
  }
  public void _loadMission(SectFile paramSectFile) {
    this.allMarkers.clear();
    int i = paramSectFile.sectionIndex("FrontMarker");
    if (i >= 0) {
      int j = paramSectFile.vars(i);
      for (int k = 0; k < j; k++) {
        NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramSectFile.line(i, k));
        String str = localNumberTokenizer.next((String)null);
        if ((str == null) || (!str.startsWith("FrontMarker")))
          continue;
        double d1 = localNumberTokenizer.next(0.0D);
        double d2 = localNumberTokenizer.next(0.0D);
        int m = localNumberTokenizer.next(1, 1, Army.amountNet() - 1);
        if ((m <= Army.amountSingle() - 1) || (Mission.isDogfight())) {
          Marker localMarker = new Marker();
          localMarker.jdField_x_of_type_Double = d1;
          localMarker.jdField_y_of_type_Double = d2;
          localMarker.army = m;
          localMarker._armyMask = (1 << m - 1);
          this.allMarkers.add(localMarker);
        }
      }
    }
    this.tilesChanged = true;
  }

  public static void preRender(boolean paramBoolean)
  {
    World.cur().front._preRender(paramBoolean);
  }
  public static void render(boolean paramBoolean) {
    World.cur().front._render(paramBoolean);
  }
  public static void setMarkersChanged() {
    World.cur().front._setMarkersChanged();
  }
  public void _setMarkersChanged() {
    this.tilesChanged = true;
  }

  private boolean isOneArmy(int paramInt)
  {
    int i = Army.amountNet() - 1;
    int j = 0;
    for (int k = 0; k < i; k++)
      if ((paramInt & 1 << k) != 0) {
        j++;
        if (j > 1)
          return false;
      }
    return true;
  }

  private void tilesUpdate() {
    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)Render.currentCamera();
    int i = (int)(localCameraOrtho2D.right - localCameraOrtho2D.left) / 16 + 5;
    int j = (int)(localCameraOrtho2D.top - localCameraOrtho2D.bottom) / 16 + 5;
    if ((i != this.tNx) || (j != this.tNy)) {
      this.tNx = i;
      this.tNy = j;
      this.mask = new byte[i * j];
      this.mask2 = new byte[i * j];
    }

    int k = this.allMarkers.size();
    if (k < 2) {
      this.bExistFront = false;
      return;
    }
    int m = 0;
    for (int n = 0; n < k; n++) {
      Marker localMarker1 = (Marker)this.allMarkers.get(n);
      m |= localMarker1._armyMask;
    }
    if (isOneArmy(m)) {
      this.bExistFront = false;
      return;
    }

    double d1 = 16.0D / localCameraOrtho2D.worldScale;
    long l = ()(localCameraOrtho2D.worldXOffset / d1) - 1L;
    this.camWorldXOffset = (l * d1);
    this.camLeft = (float)(localCameraOrtho2D.left - (localCameraOrtho2D.worldXOffset - this.camWorldXOffset) * localCameraOrtho2D.worldScale);
    l = ()(localCameraOrtho2D.worldYOffset / d1) - 1L;
    this.camWorldYOffset = (l * d1);
    this.camBottom = (float)(localCameraOrtho2D.bottom - (localCameraOrtho2D.worldYOffset - this.camWorldYOffset) * localCameraOrtho2D.worldScale);

    double d2 = localCameraOrtho2D.worldYOffset + (localCameraOrtho2D.right - localCameraOrtho2D.left) / localCameraOrtho2D.worldScale * 0.5D;
    double d3 = localCameraOrtho2D.worldXOffset + (localCameraOrtho2D.top - localCameraOrtho2D.bottom) / localCameraOrtho2D.worldScale * 0.5D;
    double d4 = 90000000000.0D;

    for (int i1 = 0; i1 < k; i1++) {
      Marker localMarker2 = (Marker)this.allMarkers.get(i1);
      double d5 = localMarker2.jdField_x_of_type_Double - d3;
      d7 = localMarker2.jdField_y_of_type_Double - d2;
      localMarker2._d2 = (d5 * d5 + d7 * d7);
      if (d4 > localMarker2._d2) {
        d4 = localMarker2._d2;
      }
    }

    d4 = Math.sqrt(d4);
    d4 += (localCameraOrtho2D.right - localCameraOrtho2D.left + (localCameraOrtho2D.top - localCameraOrtho2D.bottom)) / localCameraOrtho2D.worldScale;
    d4 *= d4;
    m = 0;
    for (int i2 = 0; i2 < k; i2++) {
      Marker localMarker3 = (Marker)this.allMarkers.get(i2);
      if (d4 > localMarker3._d2) {
        this.markers.add(localMarker3);
        m |= localMarker3._armyMask;
      }
    }

    if ((this.markers.size() < 2) || (isOneArmy(m))) {
      this.markers.clear();
      this.bExistFront = false;
      return;
    }

    k = this.markers.size();
    double d6 = this.camWorldYOffset - 0.5D * d1;
    double d7 = this.camWorldXOffset - 0.5D * d1;
    int i3 = 0;
    int i4 = 0;
    m = 0;
    int i8;
    for (int i5 = 0; i5 < j; i5++) {
      double d8 = d6 + i5 * d1;
      for (i8 = 0; i8 < i; i8++) {
        double d9 = d7 + i8 * d1;
        double d10 = d4;
        for (int i12 = 0; i12 < k; i12++) {
          Marker localMarker4 = (Marker)this.markers.get(i12);
          double d11 = (localMarker4.jdField_x_of_type_Double - d9) * (localMarker4.jdField_x_of_type_Double - d9) + (localMarker4.jdField_y_of_type_Double - d8) * (localMarker4.jdField_y_of_type_Double - d8);
          if (d10 > d11) {
            d10 = d11;
            i3 = localMarker4.army;
            i4 = localMarker4._armyMask;
          }
        }
        this.mask[(i * i5 + i8)] = (byte)i3;
        m |= i4;
      }
    }
    this.markers.clear();

    if (isOneArmy(m)) {
      this.bExistFront = false;
      return;
    }

    for (int i6 = 1; i6 < j - 1; i6++) {
      for (i7 = 1; i7 < i - 1; i7++) {
        i8 = i6 * i + i7;
        m = this.mask[i8];
        if ((m == this.mask[(i8 - 1)]) && (m == this.mask[(i8 + 1)]) && (m == this.mask[(i8 - i)]) && (m == this.mask[(i8 + i)]))
        {
          this.mask2[i8] = 0;
        }
        else this.mask2[i8] = (byte)m;
      }

    }

    for (int i7 = 1; i7 < j - 1; i7++) {
      for (i8 = 1; i8 < i - 1; i8++) {
        int i9 = i7 * i + i8;
        int i10 = this.mask2[i9];
        if (i10 == 0) {
          this.mask[i9] = 0;
        } else {
          int i11 = 0;
          if (this.mask2[(i9 + 1)] == i10) {
            if ((this.mask2[(i9 + i)] != this.mask2[(i9 - i + 1)]) && (this.mask2[(i9 - i)] != this.mask2[(i9 + i + 1)]))
            {
              i11 |= 1;
            }
          } else if ((this.mask2[(i9 + i + 1)] == i10) && 
            (this.mask2[(i9 + i)] != i10) && 
            (this.mask2[(i9 + i)] != this.mask2[(i9 + 1)])) {
            i11 |= 2;
          }

          if (this.mask2[(i9 + i)] == i10) {
            if ((this.mask2[(i9 - 1)] != this.mask2[(i9 + i + 1)]) && (this.mask2[(i9 + i - 1)] != this.mask2[(i9 + 1)]))
            {
              i11 |= 4;
            }
          } else if ((this.mask2[(i9 + i - 1)] == i10) && 
            (this.mask2[(i9 - 1)] != i10) && 
            (this.mask2[(i9 - 1)] != this.mask2[(i9 + i)])) {
            i11 |= 8;
          }

          this.mask[i9] = (byte)i11;
        }
      }
    }
    this.bExistFront = true;
  }

  public void _preRender(boolean paramBoolean)
  {
    CameraOrtho2D localCameraOrtho2D = (CameraOrtho2D)Render.currentCamera();
    if (!this.tilesChanged) {
      this.tilesChanged = ((this.prevNCountMarkers != this.allMarkers.size()) || (this.prevCamLeft != localCameraOrtho2D.left) || (this.prevCamBottom != localCameraOrtho2D.bottom) || (this.prevCamWorldScale != localCameraOrtho2D.worldScale) || (this.prevCamWorldXOffset != localCameraOrtho2D.worldXOffset) || (this.prevCamWorldYOffset != localCameraOrtho2D.worldYOffset));
    }

    if ((!this.tilesChanged) && (paramBoolean)) {
      for (int i = 0; i < this.prevNCountMarkers; i++) {
        Marker localMarker1 = (Marker)this.allMarkers.get(i);
        if ((localMarker1.jdField_x_of_type_Double != localMarker1._x) || (localMarker1.jdField_y_of_type_Double != localMarker1._y)) {
          this.tilesChanged = true;
          break;
        }
      }
    }
    if (this.tilesChanged) {
      tilesUpdate();
      this.prevNCountMarkers = this.allMarkers.size();
      this.prevCamLeft = localCameraOrtho2D.left;
      this.prevCamBottom = localCameraOrtho2D.bottom;
      this.prevCamWorldScale = localCameraOrtho2D.worldScale;
      this.prevCamWorldXOffset = localCameraOrtho2D.worldXOffset;
      this.prevCamWorldYOffset = localCameraOrtho2D.worldYOffset;
      for (int j = 0; j < this.prevNCountMarkers; j++) {
        Marker localMarker2 = (Marker)this.allMarkers.get(j);
        Marker.access$002(localMarker2, localMarker2.jdField_x_of_type_Double);
        Marker.access$102(localMarker2, localMarker2.jdField_y_of_type_Double);
      }
      this.tilesChanged = false;
      this.bTilesUpdated = true;
    } else {
      this.bTilesUpdated = false;
    }
    if ((this.bExistFront) && (this.frontMat[0] == null)) {
      this.frontMat[0] = Mat.New("icons/front1.mat");
      this.frontMat[1] = Mat.New("icons/front2.mat");
      this.frontMat[2] = Mat.New("icons/front3.mat");
      this.frontMat[3] = Mat.New("icons/front4.mat");
    }
  }

  public static boolean isMarkersUpdated() {
    return World.cur().front.bTilesUpdated;
  }

  private void drawTile3D(float paramFloat1, float paramFloat2, Mat paramMat, int paramInt) {
    double d1 = this.camWorldXOffset + paramFloat1 * this.tStep;
    double d2 = this.camWorldYOffset + paramFloat2 * this.tStep;
    double d3 = Landscape.HQ((float)d1, (float)d2);
    this.main.project2d(d1, d2, d3, this.pointLand0);
    d1 += 32.0D * this.tStep;
    d2 += 32.0D * this.tStep;
    d3 = Landscape.HQ((float)d1, (float)d2);
    this.main.project2d(d1, d2, d3, this.pointLand1);
    Render.drawTile((float)this.pointLand0.jdField_x_of_type_Double, (float)this.pointLand0.jdField_y_of_type_Double, (float)(this.pointLand1.jdField_x_of_type_Double - this.pointLand0.jdField_x_of_type_Double), (float)(this.pointLand1.jdField_y_of_type_Double - this.pointLand0.jdField_y_of_type_Double), 0.0F, paramMat, paramInt, 0.0F, 0.0F, 1.0F, 1.0F);
  }

  public void _render(boolean paramBoolean)
  {
    if (this.bExistFront) {
      int i = this.tNx;
      int j = this.tNy;
      int k;
      int i1;
      int i2;
      int i3;
      int i4;
      if (paramBoolean) {
        this.main = Main3D.cur3D();
        this.tStep = (1.0D / this.prevCamWorldScale);
        for (k = 1; k < j - 1; k++) {
          int m = 16 * (k - 1);
          for (i1 = 1; i1 < i - 1; i1++) {
            i2 = k * i + i1;
            i3 = this.mask[i2];
            if (i3 != 0) {
              i4 = Army.color(this.mask2[i2]) | 0xFF000000;
              int i5 = 16 * (i1 - 1);
              if ((i3 & 0x1) != 0)
                drawTile3D(i5, m, this.frontMat[0], i4);
              else if ((i3 & 0x2) != 0) {
                drawTile3D(i5, m, this.frontMat[1], i4);
              }
              if ((i3 & 0x4) != 0)
                drawTile3D(i5, m, this.frontMat[2], i4);
              else if ((i3 & 0x8) != 0)
                drawTile3D(i5 - 16, m, this.frontMat[3], i4);
            }
          }
        }
      } else {
        for (k = 1; k < j - 1; k++) {
          int n = 16 * (k - 1);
          for (i1 = 1; i1 < i - 1; i1++) {
            i2 = k * i + i1;
            i3 = this.mask[i2];
            if (i3 != 0) {
              i4 = Army.color(this.mask2[i2]) | 0xFF000000;

              int i6 = 16 * (i1 - 1);
              if ((i3 & 0x1) != 0)
                Render.drawTile(this.camLeft + i6, this.camBottom + n, 32.0F, 32.0F, 0.0F, this.frontMat[0], i4, 0.0F, 0.0F, 1.0F, 1.0F);
              else if ((i3 & 0x2) != 0) {
                Render.drawTile(this.camLeft + i6, this.camBottom + n, 32.0F, 32.0F, 0.0F, this.frontMat[1], i4, 0.0F, 0.0F, 1.0F, 1.0F);
              }
              if ((i3 & 0x4) != 0)
                Render.drawTile(this.camLeft + i6, this.camBottom + n, 32.0F, 32.0F, 0.0F, this.frontMat[2], i4, 0.0F, 0.0F, 1.0F, 1.0F);
              else if ((i3 & 0x8) != 0)
                Render.drawTile(this.camLeft + i6 - 16.0F, this.camBottom + n, 32.0F, 32.0F, 0.0F, this.frontMat[3], i4, 0.0F, 0.0F, 1.0F, 1.0F);
            }
          }
        }
      }
    }
  }

  public static class Marker
  {
    public double x;
    public double y;
    public int army;
    public int _armyMask;
    public double _d2;
    private double _x = 0.0D;
    private double _y = 0.0D;
  }
}