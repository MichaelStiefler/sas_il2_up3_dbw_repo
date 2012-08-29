package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Mouse;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgMouseListener;
import com.maddox.rts.Time;

public class FreeView
  implements MsgMouseListener
{
  private FreeInterpolate interpolator = null;
  private String envName;
  private float koofAzimut = 0.25F;
  private float koofTangage = 0.25F;
  private float koofLen = 0.25F;
  private float minLen = 2.0F;
  private float defaultLen = 10.0F;
  private float maxLen = 500.0F;

  private float len = this.defaultLen;
  private Orient o = new Orientation();
  private Point3d p = new Point3d();
  private Actor target = null;
  private boolean bUseMouse = true;

  private Point3d pAbs = new Point3d();
  private Orient oAbs = new Orient();

  private boolean bChangeLen = false;

  private static FreeView adapter = null;

  public void resetGame()
  {
    this.target = null;
  }

  private void reset()
  {
    if (Actor.isValid(this.target)) {
      this.target.pos.getAbs(this.oAbs);
      this.o.set(this.oAbs.azimut(), this.oAbs.tangage(), 0.0F);
    } else {
      this.o.set(0.0F, 0.0F, 0.0F);
    }
    this.len = this.defaultLen;
  }

  public void msgMouseButton(int paramInt, boolean paramBoolean)
  {
  }

  public void msgMouseAbsMove(int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void msgMouseMove(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.bUseMouse)
      if (this.bChangeLen) {
        this.len += paramInt2 * this.koofLen;
        if (this.len < this.minLen) this.len = this.minLen;
        if (this.len > this.maxLen) this.len = this.maxLen; 
      }
      else {
        this.o.set(this.o.azimut() + paramInt1 * this.koofAzimut, this.o.tangage() + paramInt2 * this.koofTangage, 0.0F);
      }
  }

  private void initHotKeys()
  {
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(false, "Reset") {
      public void begin() { FreeView.this.reset();
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(false, "Len") {
      public void begin() { FreeView.access$602(FreeView.this, true); } 
      public void end() { FreeView.access$602(FreeView.this, false); }  } );
  }

  public static float len() {
    return adapter._len(); } 
  private float _len() { return this.len; } 
  public static void setTarget(Actor paramActor) {
    adapter._setTarget(paramActor);
  }
  private void _setTarget(Actor paramActor) { this.target = paramActor; }

  public static Actor getTarget() {
    return adapter._getTarget(); } 
  private Actor _getTarget() { return this.target; } 
  public static void setActor(Actor paramActor) {
    adapter._setActor(paramActor);
  }
  private void _setActor(Actor paramActor) { if (this.interpolator != null) {
      if (this.interpolator.jdField_actor_of_type_ComMaddoxIl2EngineActor == paramActor) {
        this.interpolator.bActive = true;
        return;
      }
      this.interpolator.bActive = false;
      this.interpolator = null;
    }
    if (Actor.isValid(paramActor)) {
      this.interpolator = new FreeInterpolate();
      paramActor.interpPut(this.interpolator, null, Time.current(), null);
    } }

  public static Actor getActor() {
    return adapter._getActor();
  }
  private Actor _getActor() { if ((this.interpolator != null) && 
      (this.interpolator.bActive)) {
      return this.interpolator.jdField_actor_of_type_ComMaddoxIl2EngineActor;
    }
    return null; }

  public static boolean useMouse(boolean paramBoolean) {
    return adapter._useMouse(paramBoolean);
  }
  private boolean _useMouse(boolean paramBoolean) { boolean bool = this.bUseMouse;
    this.bUseMouse = paramBoolean;
    return bool; }

  public static String environment() {
    return adapter._environment(); } 
  private String _environment() { return this.envName; }

  private FreeView(String paramString) {
    adapter = this;

    MsgAddListener.post(0, Mouse.adapter(), this, null);
    this.envName = paramString;
    HotKeyEnv.fromIni(this.envName, Config.cur.ini, this.envName);
    String str = this.envName + " Config";
    this.koofAzimut = Config.cur.ini.get(str, "AzimutSpeed", this.koofAzimut);
    this.koofTangage = Config.cur.ini.get(str, "TangageSpeed", this.koofTangage);
    this.koofLen = Config.cur.ini.get(str, "LenSpeed", this.koofLen);
    this.minLen = Config.cur.ini.get(str, "MinLen", this.minLen);
    this.defaultLen = Config.cur.ini.get(str, "DefaultLen", this.defaultLen);
    this.maxLen = Config.cur.ini.get(str, "MaxLen", this.maxLen);
    initHotKeys();
  }
  public static void initSave() {
    adapter._initSave();
  }
  private void _initSave() { String str = this.envName + " Config";
    Config.cur.ini.setValue(str, "AzimutSpeed", Float.toString(this.koofAzimut));
    Config.cur.ini.setValue(str, "TangageSpeed", Float.toString(this.koofTangage));
    Config.cur.ini.setValue(str, "LenSpeed", Float.toString(this.koofLen));
    Config.cur.ini.setValue(str, "MinLen", Float.toString(this.minLen));
    Config.cur.ini.setValue(str, "DefaultLen", Float.toString(this.defaultLen));
    Config.cur.ini.setValue(str, "MaxLen", Float.toString(this.maxLen)); }

  public static FreeView adapter()
  {
    return adapter;
  }
  public static void init(String paramString) {
    if (adapter == null)
      new FreeView(paramString);
  }

  class FreeInterpolate extends Interpolate
  {
    public boolean bActive = true;

    FreeInterpolate() {  } 
    public void begin() { FreeView.this.reset(); } 
    public boolean tick() {
      if (!this.bActive)
        return false;
      if (Actor.isValid(FreeView.this.target)) {
        FreeView.this.target.pos.getAbs(FreeView.this.pAbs);
        FreeView.this.p.set(-FreeView.this.len, 0.0D, 0.0D);
        FreeView.this.o.transform(FreeView.this.p);
        FreeView.this.pAbs.add(FreeView.this.p);
        double d = Engine.land().HQ(FreeView.this.pAbs.x, FreeView.this.pAbs.y) + 1.0D;
        if (FreeView.this.pAbs.jdField_z_of_type_Double < d)
          FreeView.this.pAbs.jdField_z_of_type_Double = d;
        this.jdField_actor_of_type_ComMaddoxIl2EngineActor.pos.setAbs(FreeView.this.pAbs, FreeView.this.o);
      } else {
        this.jdField_actor_of_type_ComMaddoxIl2EngineActor.pos.setAbs(FreeView.this.o);
      }
      return true;
    }
  }
}