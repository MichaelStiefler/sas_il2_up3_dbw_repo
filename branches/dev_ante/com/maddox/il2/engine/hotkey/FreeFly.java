package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera;
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
import com.maddox.rts.Message;
import com.maddox.rts.Mouse;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgMouseListener;
import com.maddox.rts.Time;

public class FreeFly
  implements MsgMouseListener
{
  private ActorInterpolate aInterpolator;
  private Actor target;
  private String envName;
  private boolean bRealTime = false;

  private Point3d vO = new Point3d(0.0D, 0.0D, 0.0D);
  public Point3d speedO = new Point3d(10.0D, 10.0D, 10.0D);

  public double maxSpeed = 10.0D;
  public double aSpeed = 3.0D;
  private double doSpeed = 0.0D;
  private double speed = 0.0D;

  public float koofAzimutView = 0.25F;
  public float koofTangageView = 0.25F;
  public boolean bViewReset = false;
  private Point3f view = new Point3f();
  private boolean bClipOnLand = true;

  private Point3d aP = new Point3d();
  private Orient aO = new Orientation();
  private Orient o = new Orient();

  private static FreeFly adapter = null;

  float DEG2RAD(float paramFloat)
  {
    return paramFloat * 0.01745329F;
  }
  public void resetGame() {
    this.target = null;
  }

  public void msgMouseButton(int paramInt, boolean paramBoolean)
  {
  }

  public void msgMouseAbsMove(int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void msgMouseMove(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((Actor.isValid(this.target)) && ((this.target instanceof Camera))) {
      this.view.x += paramInt1;
      this.view.y += paramInt2;
    }
  }

  private void initHotKeys() {
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(this.bRealTime, "ResetView") {
      public void begin() { FreeFly.this.bViewReset = true;
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(this.bRealTime, "Azimut-") {
      public void begin() { FreeFly.this.vO.jdField_x_of_type_Double += -1.0D; } 
      public void end() { FreeFly.this.vO.jdField_x_of_type_Double -= -1.0D;
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(this.bRealTime, "Azimut+") {
      public void begin() { FreeFly.this.vO.jdField_x_of_type_Double += 1.0D; } 
      public void end() { FreeFly.this.vO.jdField_x_of_type_Double -= 1.0D;
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(this.bRealTime, "Tangage-") {
      public void begin() { FreeFly.this.vO.jdField_y_of_type_Double += -1.0D; } 
      public void end() { FreeFly.this.vO.jdField_y_of_type_Double -= -1.0D;
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(this.bRealTime, "Tangage+") {
      public void begin() { FreeFly.this.vO.jdField_y_of_type_Double += 1.0D; } 
      public void end() { FreeFly.this.vO.jdField_y_of_type_Double -= 1.0D;
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(this.bRealTime, "Kren-") {
      public void begin() { FreeFly.this.vO.jdField_z_of_type_Double += -1.0D; } 
      public void end() { FreeFly.this.vO.jdField_z_of_type_Double -= -1.0D;
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(this.bRealTime, "Kren+") {
      public void begin() { FreeFly.this.vO.jdField_z_of_type_Double += 1.0D; } 
      public void end() { FreeFly.this.vO.jdField_z_of_type_Double -= 1.0D;
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(this.bRealTime, "Speed-") {
      public void begin() { FreeFly.access$418(FreeFly.this, -1.0D); } 
      public void end() { FreeFly.access$426(FreeFly.this, -1.0D);
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(this.bRealTime, "Speed+") {
      public void begin() { FreeFly.access$418(FreeFly.this, 1.0D); } 
      public void end() { FreeFly.access$426(FreeFly.this, 1.0D); }  } );
  }

  public static double speed() {
    return adapter._speed(); } 
  private double _speed() { return this.speed; } 
  public static boolean clipOnLand(boolean paramBoolean) {
    return adapter._clipOnLand(paramBoolean);
  }
  private boolean _clipOnLand(boolean paramBoolean) { boolean bool = this.bClipOnLand;
    this.bClipOnLand = paramBoolean;
    return bool; }

  public static void setActor(Actor paramActor) {
    adapter._setActor(paramActor); } 
  private void _setActor(Actor paramActor) { this.target = paramActor; } 
  public static Actor getActor(Actor paramActor) {
    return adapter._getActor(); } 
  private Actor _getActor() { return this.target; } 
  public static String environment() {
    return adapter._environment(); } 
  private String _environment() { return this.envName; }

  private FreeFly(String paramString) {
    adapter = this;
    this.envName = paramString;
    HotKeyEnv.fromIni(this.envName, Config.cur.ini, this.envName);
    String str = this.envName + " Config";
    this.bRealTime = Config.cur.ini.get(str, "RealTime", this.bRealTime);
    this.koofAzimutView = Config.cur.ini.get(str, "AzimutView", this.koofAzimutView);
    this.koofTangageView = Config.cur.ini.get(str, "TangageView", this.koofTangageView);
    this.speedO.jdField_x_of_type_Double = Config.cur.ini.get(str, "SpeedAzimut", (float)this.speedO.jdField_x_of_type_Double);
    this.speedO.jdField_y_of_type_Double = Config.cur.ini.get(str, "SpeedTangage", (float)this.speedO.jdField_y_of_type_Double);
    this.speedO.jdField_z_of_type_Double = Config.cur.ini.get(str, "SpeedKren", (float)this.speedO.jdField_z_of_type_Double);
    this.maxSpeed = Config.cur.ini.get(str, "MaxSpeed", (float)this.maxSpeed);
    this.aSpeed = Config.cur.ini.get(str, "Acselerate", (float)this.aSpeed);
    MsgAddListener.post(this.bRealTime ? 64 : 0, Mouse.adapter(), this, null);
    initHotKeys();
    this.aInterpolator = new ActorInterpolate();
    this.aInterpolator.interpPut(new FreeInterpolate(), null, this.bRealTime ? Time.currentReal() : Time.current(), null);
  }
  public static void initSave() {
    adapter._initSave();
  }
  private void _initSave() { String str = this.envName + " Config";
    Config.cur.ini.setValue(str, "AzimutView", Float.toString(this.koofAzimutView));
    Config.cur.ini.setValue(str, "TangageView", Float.toString(this.koofTangageView));
    Config.cur.ini.setValue(str, "SpeedAzimut", Float.toString((float)this.speedO.jdField_x_of_type_Double));
    Config.cur.ini.setValue(str, "SpeedTangage", Float.toString((float)this.speedO.jdField_y_of_type_Double));
    Config.cur.ini.setValue(str, "SpeedKren", Float.toString((float)this.speedO.jdField_z_of_type_Double));
    Config.cur.ini.setValue(str, "MaxSpeed", Float.toString((float)this.maxSpeed));
    Config.cur.ini.setValue(str, "Acselerate", Float.toString((float)this.aSpeed)); }

  public static FreeFly adapter()
  {
    return adapter;
  }
  public static void init(String paramString) {
    if (adapter == null)
      new FreeFly(paramString);
  }

  class ActorInterpolate extends Actor
  {
    public Object getSwitchListener(Message paramMessage)
    {
      return this;
    }
    public ActorInterpolate() { if (FreeFly.this.bRealTime)
        this.jdField_flags_of_type_Int |= 8192;
      this.jdField_flags_of_type_Int |= 16384; }

    protected void createActorHashCode() {
      makeActorRealHashCode();
    }
  }

  class FreeInterpolate extends Interpolate
  {
    private long prevTime;

    FreeInterpolate()
    {
    }

    public void begin()
    {
      FreeFly.this.view.set(0.0F, 0.0F, 0.0F);
      FreeFly.access$102(FreeFly.this, 0.0D);
      this.prevTime = (FreeFly.this.bRealTime ? Time.currentReal() : Time.current());
    }
    public boolean tick() {
      long l = FreeFly.this.bRealTime ? Time.currentReal() : Time.current();
      if (l < this.prevTime)
        this.prevTime = l;
      if (l == this.prevTime)
        return true;
      float f = (float)(l - this.prevTime) * 0.001F;
      this.prevTime = l;

      if (!Actor.isValid(FreeFly.this.target)) {
        return true;
      }
      FreeFly.access$118(FreeFly.this, FreeFly.this.doSpeed * FreeFly.this.aSpeed * f);
      if (FreeFly.this.speed < 0.0D) FreeFly.access$102(FreeFly.this, 0.0D);
      if (FreeFly.this.speed > FreeFly.this.maxSpeed) FreeFly.access$102(FreeFly.this, FreeFly.this.maxSpeed);

      FreeFly.this.target.pos.getAbs(FreeFly.this.aP, FreeFly.this.aO);

      double d1 = FreeFly.this.speed * f;
      double d2 = Math.cos(FreeFly.this.DEG2RAD(FreeFly.this.aO.tangage())) * d1;
      FreeFly.this.aP.jdField_y_of_type_Double -= Math.sin(FreeFly.this.DEG2RAD(FreeFly.this.aO.azimut())) * d2;
      FreeFly.this.aP.jdField_x_of_type_Double += Math.cos(FreeFly.this.DEG2RAD(FreeFly.this.aO.azimut())) * d2;
      FreeFly.this.aP.jdField_z_of_type_Double += Math.sin(FreeFly.this.DEG2RAD(FreeFly.this.aO.tangage())) * d1;
      if (FreeFly.this.bClipOnLand) {
        double d3 = Engine.land().HQ(FreeFly.this.aP.jdField_x_of_type_Double, FreeFly.this.aP.jdField_y_of_type_Double) + 1.0D;
        if (FreeFly.this.aP.jdField_z_of_type_Double < d3) {
          FreeFly.this.aP.jdField_z_of_type_Double = d3;
        }
      }
      FreeFly.this.aO.increment((float)(FreeFly.this.vO.jdField_x_of_type_Double * FreeFly.this.speedO.jdField_x_of_type_Double * f), (float)(FreeFly.this.vO.jdField_y_of_type_Double * FreeFly.this.speedO.jdField_y_of_type_Double * f), (float)(FreeFly.this.vO.jdField_z_of_type_Double * FreeFly.this.speedO.jdField_z_of_type_Double * f));

      FreeFly.this.o.set(FreeFly.this.aO);

      if (FreeFly.this.bViewReset) {
        FreeFly.this.view.set(0.0F, 0.0F, 0.0F);
        FreeFly.this.bViewReset = false;
      }
      else if ((FreeFly.this.target instanceof Camera)) {
        FreeFly.this.o.set(FreeFly.this.view.x * FreeFly.this.koofAzimutView, FreeFly.this.view.y * FreeFly.this.koofTangageView, 0.0F);
      }
      FreeFly.this.target.pos.setAbs(FreeFly.this.aP, FreeFly.this.o);
      return true;
    }
  }
}