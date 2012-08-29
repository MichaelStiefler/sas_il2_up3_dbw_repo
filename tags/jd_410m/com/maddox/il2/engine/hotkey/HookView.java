package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HookRender;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.objects.ActorViewPoint;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyCmdMouseMove;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Time;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class HookView extends HookRender
{
  protected static Actor lastBaseActor = null;
  protected Actor camera = null;
  protected boolean bUse = false;
  protected boolean bClipOnLand = true;
  protected static final int EXT = 1;
  protected static boolean bUseMouse = true;
  protected static boolean bChangeLen = false;
  protected static int useFlags = 0;

  private static float _minLen = 2.0F;
  private static float _defaultLen = 20.0F;
  private static float _maxLen = 500.0F;
  protected static float _visibleR = -1.0F;
  protected static float len = _defaultLen;

  protected static float stepAzimut = 45.0F;
  protected static float stepTangage = 30.0F;
  protected static float maxAzimut = 179.0F;
  protected static float maxTangage = 89.0F;
  protected static float minTangage = -89.0F;
  protected static float Azimut = 0.0F;
  protected static float Tangage = 0.0F;
  protected static float _Azimut = 0.0F;
  protected static float _Tangage = 0.0F;
  protected static long prevTime = 0L;

  protected static float koofAzimut = 0.1F;
  protected static float koofTangage = 0.1F;
  protected static float koofLen = 1.0F;
  protected static float koofSpeed = 6.0F;

  protected Orient o = new Orientation();
  protected Point3d p = new Point3d();
  protected Point3d pAbs = new Point3d();
  protected Orient oAbs = new Orient();

  private boolean bFollow = false;

  private static Point3d pClipZ1 = new Point3d();
  private static Point3d pClipZ2 = new Point3d();
  private static Point3d pClipRes = new Point3d();
  static ClipFilter clipFilter = new ClipFilter();
  private static final float Circ = 360.0F;
  private long timeViewSet = -2000L;
  private static String sectConf;
  public static HookView current = null;

  public boolean getFollow()
  {
    return this.bFollow; } 
  public void setFollow(boolean paramBoolean) { this.bFollow = paramBoolean; } 
  protected boolean isUseCommon() {
    return useFlags != 0;
  }
  protected void useCommon(int paramInt, boolean paramBoolean) { if (this.bUse) useFlags |= paramInt; else
      useFlags &= (paramInt ^ 0xFFFFFFFF); }

  protected static float minLen()
  {
    if (_visibleR > 0.0F)
      return _visibleR + 1.5F;
    return _minLen;
  }
  public static float defaultLen() {
    if (_visibleR > 0.0F)
      return _visibleR * 3.0F;
    return _defaultLen;
  }
  protected static float maxLen() {
    return _maxLen;
  }
  public float len() { return len; }

  public void resetGame() {
    lastBaseActor = null;
    _visibleR = -1.0F;
    _Azimut = HookView.Azimut = 0.0F;
    _Tangage = HookView.Tangage = 0.0F;
    this.timeViewSet = -2000L;
  }

  public void saveRecordedStates(PrintWriter paramPrintWriter) throws Exception {
    paramPrintWriter.println(len);
    paramPrintWriter.println(Azimut);
    paramPrintWriter.println(_Azimut);
    paramPrintWriter.println(Tangage);
    paramPrintWriter.println(_Tangage);
    paramPrintWriter.println(this.o.azimut());
    paramPrintWriter.println(this.o.tangage());
    paramPrintWriter.println(koofAzimut);
    paramPrintWriter.println(koofTangage);
    paramPrintWriter.println(koofLen);
    paramPrintWriter.println(_minLen);
    paramPrintWriter.println(_defaultLen);
    paramPrintWriter.println(_maxLen);
    paramPrintWriter.println(koofSpeed);
  }
  public void loadRecordedStates(BufferedReader paramBufferedReader) throws Exception {
    len = Float.parseFloat(paramBufferedReader.readLine());
    Azimut = Float.parseFloat(paramBufferedReader.readLine());
    _Azimut = Float.parseFloat(paramBufferedReader.readLine());
    Tangage = Float.parseFloat(paramBufferedReader.readLine());
    _Tangage = Float.parseFloat(paramBufferedReader.readLine());
    this.o.set(Float.parseFloat(paramBufferedReader.readLine()), Float.parseFloat(paramBufferedReader.readLine()), 0.0F);

    koofAzimut = Float.parseFloat(paramBufferedReader.readLine());
    koofTangage = Float.parseFloat(paramBufferedReader.readLine());
    koofLen = Float.parseFloat(paramBufferedReader.readLine());
    _minLen = Float.parseFloat(paramBufferedReader.readLine());
    _defaultLen = Float.parseFloat(paramBufferedReader.readLine());
    _maxLen = Float.parseFloat(paramBufferedReader.readLine());
    koofSpeed = Float.parseFloat(paramBufferedReader.readLine());
  }

  public void reset() {
    this.timeViewSet = -2000L;
    if (AircraftHotKeys.bFirstHotCmd) return;
    set(lastBaseActor, defaultLen(), 0.0F, 0.0F);
  }

  public void set(Actor paramActor, float paramFloat1, float paramFloat2) {
    set(paramActor, defaultLen(), paramFloat1, paramFloat2);
  }

  public void set(Actor paramActor, float paramFloat1, float paramFloat2, float paramFloat3) {
    lastBaseActor = paramActor;
    _visibleR = -1.0F;
    if ((Actor.isValid(paramActor)) && ((paramActor instanceof ActorMesh))) {
      _visibleR = ((ActorMesh)paramActor).mesh().visibilityR();
    }
    this.o.set(paramFloat2, paramFloat3, 0.0F);

    _Azimut = HookView.Azimut = 0.0F;
    _Tangage = HookView.Tangage = 0.0F;
    prevTime = Time.real();

    len = paramFloat1;
    if (this.camera != null) {
      Actor localActor = this.camera.pos.base();
      if (localActor != null) {
        localActor.pos.getAbs(this.o);
        this.o.increment(paramFloat2, paramFloat3, 0.0F);
        this.o.set(this.o.azimut(), this.o.tangage(), 0.0F);
      }
      if (this.bUse)
        this.camera.pos.inValidate(true);
    }
    this.o.wrap();
  }

  private float bvalue(float paramFloat1, float paramFloat2, long paramLong) {
    float f = koofSpeed * (float)paramLong / 30.0F;
    if (paramFloat1 == paramFloat2) return paramFloat1;
    if (paramFloat1 > paramFloat2) {
      if (paramFloat1 < paramFloat2 + f) return paramFloat1;
      return paramFloat2 + f;
    }
    if (paramFloat1 > paramFloat2 - f) return paramFloat1;
    return paramFloat2 - f;
  }

  public boolean computeRenderPos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
    long l1 = Time.currentReal();
    if (l1 != prevTime) {
      long l2 = l1 - prevTime;
      prevTime = l1;
      if ((_Azimut != Azimut) || (_Tangage != Tangage)) {
        Azimut = bvalue(_Azimut, Azimut, l2);
        Tangage = bvalue(_Tangage, Tangage, l2);
        paramLoc1.get(this.o);
        float f = this.o.azimut() + Azimut;
        this.o.set(f, Tangage, 0.0F);
        this.o.wrap360();
      }
      if ((_Azimut == Azimut) && ((Azimut > 180.0F) || (Azimut < -180.0F))) {
        Azimut %= 360.0F;
        if (Azimut > 180.0F) Azimut -= 360.0F;
        else if (Azimut < -180.0F) Azimut += 360.0F;
        _Azimut = Azimut;
      }
      if ((_Tangage == Tangage) && ((Tangage > 180.0F) || (Tangage < -180.0F))) {
        Tangage %= 360.0F;
        if (Tangage > 180.0F) Tangage -= 360.0F;
        else if (Tangage < -180.0F) Tangage += 360.0F;
        _Tangage = Tangage;
      }
    }
    computePos(paramActor, paramLoc1, paramLoc2);
    return true;
  }
  public void computePos(Actor paramActor, Loc paramLoc1, Loc paramLoc2) {
    paramLoc1.get(this.pAbs, this.oAbs);
    if (this.bUse) {
      if (lastBaseActor != paramActor) {
        lastBaseActor = paramActor;
        _visibleR = -1.0F;
        if ((Actor.isValid(paramActor)) && ((paramActor instanceof ActorMesh)))
          _visibleR = ((ActorMesh)paramActor).mesh().visibilityR();
      }
      this.p.set(-len, 0.0D, 0.0D);
      if (this.bFollow) {
        this.o.set(this.oAbs.getAzimut(), this.o.getTangage(), this.o.getKren());
      }
      this.o.transform(this.p);
      this.pAbs.add(this.p);
      if (this.bClipOnLand) {
        double d = Engine.land().HQ_Air(this.pAbs.x, this.pAbs.y) + 2.0D;
        if (this.pAbs.z < d)
          this.pAbs.z = d;
        pClipZ1.set(this.pAbs);
        pClipZ2.set(this.pAbs);
        pClipZ1.z -= 2.0D;
        pClipZ2.z += 42.0D;
        Actor localActor = Engine.collideEnv().getLine(pClipZ2, pClipZ1, false, clipFilter, pClipRes);
        if ((Actor.isValid(localActor)) && 
          (this.pAbs.z < pClipRes.z + 2.0D)) {
          this.pAbs.z = (pClipRes.z + 2.0D);
        }
      }
      paramLoc2.set(this.pAbs, this.o);
    } else {
      paramLoc2.set(this.pAbs, this.oAbs);
    }
  }

  public void setCamera(Actor paramActor)
  {
    this.camera = paramActor;
  }
  public void use(boolean paramBoolean) {
    this.bUse = paramBoolean;
    if (this.camera != null)
      this.camera.pos.inValidate(true);
    useCommon(1, this.bUse);
  }
  public boolean isUse() { return this.bUse; } 
  public static void useMouse(boolean paramBoolean) {
    bUseMouse = paramBoolean; } 
  public static boolean isUseMouse() { return bUseMouse; }

  public boolean clipOnLand(boolean paramBoolean) {
    boolean bool = this.bClipOnLand;
    this.bClipOnLand = paramBoolean;
    return bool;
  }

  protected void clipLen(Actor paramActor) {
    if (len < minLen()) {
      if ((Actor.isValid(paramActor)) && ((paramActor instanceof ActorViewPoint))) {
        if (len < -maxLen())
          len = -maxLen();
      }
      else len = minLen();
    }

    if (len > maxLen()) len = maxLen();
  }

  protected void mouseMove(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((bUseMouse) && (isUseCommon())) {
      if (bChangeLen) {
        len += paramInt2 * koofLen;
        clipLen(lastBaseActor);
      } else {
        if (Time.real() < this.timeViewSet + 1000L) return;
        if (this.bFollow) paramInt1 = 0;
        if (this.bUse) {
          this.o.set(this.o.azimut() + paramInt1 * koofAzimut, this.o.tangage() + paramInt2 * koofTangage, 0.0F);
          this.o.wrap360();
        }
      }
      if (Actor.isValid(this.camera))
        this.camera.pos.inValidate(true);
      Azimut = _Azimut;
      Tangage = _Tangage;
    }
  }

  public void viewSet(float paramFloat1, float paramFloat2) {
    if ((!bUseMouse) && (!isUseCommon())) return;
    this.timeViewSet = Time.real();

    paramFloat1 %= 360.0F;
    if (paramFloat1 > 180.0F) paramFloat1 -= 360.0F;
    else if (paramFloat1 < -180.0F) paramFloat1 += 360.0F;
    paramFloat2 %= 360.0F;
    if (paramFloat2 > 180.0F) paramFloat2 -= 360.0F;
    else if (paramFloat2 < -180.0F) paramFloat2 += 360.0F;

    if (this.bFollow) {
      this.o.set(this.o.azimut(), paramFloat2, 0.0F);
      this.o.wrap360();
    } else {
      Azimut = HookView._Azimut = paramFloat1;
      Tangage = HookView._Tangage = paramFloat2;
      this.o.set(Azimut, Tangage, 0.0F);
    }

    if (Actor.isValid(this.camera))
      this.camera.pos.inValidate(true);
  }

  public void snapSet(float paramFloat1, float paramFloat2) {
    if ((!this.bUse) || (!bUseMouse) || (!isUseCommon())) return;
    if (this.bFollow) paramFloat1 = 0.0F;
    _Azimut = 45.0F * paramFloat1;
    _Tangage = 44.0F * paramFloat2;
    if (this.camera != null) {
      Actor localActor = this.camera.pos.base();
      if (localActor != null)
      {
        Azimut = (this.o.azimut() - localActor.pos.getAbsOrient().azimut()) % 360.0F;
        if (Azimut > _Azimut)
          while (Math.abs(Azimut - _Azimut) > 180.0F)
            Azimut -= 360.0F;
        if (Azimut < _Azimut) {
          while (Math.abs(Azimut - _Azimut) > 180.0F) {
            Azimut += 360.0F;
          }
        }
        Tangage = this.o.tangage() % 360.0F;
        if (Tangage > _Tangage)
          while (Math.abs(Tangage - _Tangage) > 180.0F)
            Tangage -= 360.0F;
        if (Tangage < _Tangage) {
          while (Math.abs(Tangage - _Tangage) > 180.0F) {
            Tangage += 360.0F;
          }

        }

        this.camera.pos.inValidate(true);
      }
    }
  }

  public void panSet(int paramInt1, int paramInt2) {
    if ((!this.bUse) || (!bUseMouse) || (!isUseCommon())) return;
    if ((paramInt1 == 0) && (paramInt2 == 0)) {
      _Azimut = 0.0F;
      _Tangage = 0.0F;
    }
    _Azimut = paramInt1 * stepAzimut + _Azimut;
    if (this.bFollow) _Azimut = 0.0F;

    _Tangage = paramInt2 * stepTangage + _Tangage;

    if (this.camera != null) {
      Actor localActor = this.camera.pos.base();
      if (localActor != null)
      {
        Azimut = (this.o.azimut() - localActor.pos.getAbsOrient().azimut()) % 360.0F;
        if (Azimut > _Azimut)
          while (Math.abs(Azimut - _Azimut) > 180.0F)
            Azimut -= 360.0F;
        if (Azimut < _Azimut) {
          while (Math.abs(Azimut - _Azimut) > 180.0F) {
            Azimut += 360.0F;
          }
        }
        Tangage = this.o.tangage() % 360.0F;
        if (Tangage > _Tangage)
          while (Math.abs(Tangage - _Tangage) > 180.0F)
            Tangage -= 360.0F;
        if (Tangage < _Tangage) {
          while (Math.abs(Tangage - _Tangage) > 180.0F) {
            Tangage += 360.0F;
          }

        }

        this.camera.pos.inValidate(true);
      }
    }
  }

  private void initHotKeys(String paramString) {
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmdMouseMove(true, "Move") {
      public void move(int paramInt1, int paramInt2, int paramInt3) {
        HookView.this.mouseMove(paramInt1, paramInt2, paramInt3);
      }
      public void created() { setRecordId(27);
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(true, "Reset") {
      public void begin() { if (HookView.this.isUseCommon()) HookView.this.reset();  } 
      public void created() {
        setRecordId(28);
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(true, "Len") {
      public void begin() { if (HookView.this.isUseCommon()) HookView.bChangeLen = true;  } 
      public void end() {
        if (HookView.this.isUseCommon()) HookView.bChangeLen = false; 
      }
      public void created() { setRecordId(29); }
    });
  }

  public static void loadConfig()
  {
    koofAzimut = Config.cur.ini.get(sectConf, "AzimutSpeed", koofAzimut, 0.01F, 1.0F);
    koofTangage = Config.cur.ini.get(sectConf, "TangageSpeed", koofTangage, 0.01F, 1.0F);
    koofLen = Config.cur.ini.get(sectConf, "LenSpeed", koofLen, 0.1F, 10.0F);
    _minLen = Config.cur.ini.get(sectConf, "MinLen", _minLen, 1.0F, 20.0F);
    _defaultLen = Config.cur.ini.get(sectConf, "DefaultLen", _defaultLen, 1.0F, 100.0F);
    _maxLen = Config.cur.ini.get(sectConf, "MaxLen", _maxLen, 1.0F, 50000.0F);
    if (_defaultLen < _minLen) _defaultLen = _minLen;
    if (_maxLen < _defaultLen) _maxLen = _defaultLen;
    koofSpeed = Config.cur.ini.get(sectConf, "Speed", koofSpeed, 0.1F, 100.0F);
  }

  public HookView(String paramString) {
    HotKeyEnv.fromIni(paramString, Config.cur.ini, paramString);
    sectConf = paramString + " Config";
    loadConfig();
    initHotKeys(paramString);
    current = this;
  }
  protected HookView() {
  }
  public static HookView cur() {
    return current;
  }

  static class ClipFilter
    implements ActorFilter
  {
    public boolean isUse(Actor paramActor, double paramDouble)
    {
      return paramActor instanceof BigshipGeneric;
    }
  }
}