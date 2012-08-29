package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Time;

public class FreeFlyXYZ
{
  private FreeInterpolate interpolator = null;
  private String envName;
  public double speedXYZ = 10.0D;
  public float speedAzimut = 10.0F;
  public float speedMul = 2.0F;

  private Point3d v = new Point3d();
  private float vAzimut = 0.0F;
  private float vMul = 1.0F;
  private Point3d P = new Point3d();
  private Orient O = new Orient();

  private static FreeFlyXYZ adapter = null;

  float DEG2RAD(float paramFloat)
  {
    return paramFloat * 0.01745329F;
  }

  private void initHotKeys()
  {
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(false, "SpeedMul") {
      public void begin() { FreeFlyXYZ.access$202(FreeFlyXYZ.this, FreeFlyXYZ.this.speedMul); } 
      public void end() { FreeFlyXYZ.access$202(FreeFlyXYZ.this, 1.0F);
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(false, "A-") {
      public void begin() { FreeFlyXYZ.access$316(FreeFlyXYZ.this, -1.0F); } 
      public void end() { FreeFlyXYZ.access$324(FreeFlyXYZ.this, -1.0F);
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(false, "A+") {
      public void begin() { FreeFlyXYZ.access$316(FreeFlyXYZ.this, 1.0F); } 
      public void end() { FreeFlyXYZ.access$324(FreeFlyXYZ.this, 1.0F);
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(false, "X-") {
      public void begin() { FreeFlyXYZ.this.v.x += -1.0D; } 
      public void end() { FreeFlyXYZ.this.v.x -= -1.0D;
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(false, "X+") {
      public void begin() { FreeFlyXYZ.this.v.x += 1.0D; } 
      public void end() { FreeFlyXYZ.this.v.x -= 1.0D;
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(false, "Y-") {
      public void begin() { FreeFlyXYZ.this.v.y += -1.0D; } 
      public void end() { FreeFlyXYZ.this.v.y -= -1.0D;
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(false, "Y+") {
      public void begin() { FreeFlyXYZ.this.v.y += 1.0D; } 
      public void end() { FreeFlyXYZ.this.v.y -= 1.0D;
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(false, "Z-") {
      public void begin() { FreeFlyXYZ.this.v.z += -1.0D; } 
      public void end() { FreeFlyXYZ.this.v.z -= -1.0D;
      }
    });
    HotKeyCmdEnv.addCmd(this.envName, new HotKeyCmd(false, "Z+") {
      public void begin() { FreeFlyXYZ.this.v.z += 1.0D; } 
      public void end() { FreeFlyXYZ.this.v.z -= 1.0D; }  } );
  }

  public static void setActor(Actor paramActor) {
    adapter._setActor(paramActor);
  }
  private void _setActor(Actor paramActor) { if (this.interpolator != null) {
      if (this.interpolator.actor == paramActor) {
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

  public static Actor getActor(Actor paramActor) {
    return adapter._getActor();
  }
  private Actor _getActor() { if ((this.interpolator != null) && 
      (this.interpolator.bActive)) {
      return this.interpolator.actor;
    }
    return null; }

  public static String environment() {
    return adapter._environment(); } 
  private String _environment() { return this.envName; }

  private FreeFlyXYZ(String paramString) {
    adapter = this;
    this.envName = paramString;
    HotKeyEnv.fromIni(this.envName, Config.cur.ini, this.envName);
    String str = this.envName + " Config";
    this.speedXYZ = Config.cur.ini.get(str, "SpeedXYZ", (float)this.speedXYZ);
    this.speedAzimut = Config.cur.ini.get(str, "SpeedAzimut", this.speedAzimut);
    this.speedMul = Config.cur.ini.get(str, "SpeedMul", this.speedMul);

    initHotKeys();
  }
  public static void initSave() {
    adapter._initSave();
  }
  private void _initSave() { String str = this.envName + " Config";
    Config.cur.ini.setValue(str, "SpeedXYZ", Float.toString((float)this.speedXYZ));
    Config.cur.ini.setValue(str, "SpeedAzimut", Float.toString(this.speedAzimut));
    Config.cur.ini.setValue(str, "SpeedMul", Float.toString(this.speedMul)); }

  public static FreeFlyXYZ adapter()
  {
    return adapter;
  }
  public static void init(String paramString) {
    if (adapter == null)
      new FreeFlyXYZ(paramString);
  }

  class FreeInterpolate extends Interpolate
  {
    public boolean bActive = true;

    FreeInterpolate() {  } 
    public void begin() {  }

    public boolean tick() { if (!this.bActive)
        return false;
      float f1 = Time.tickLenFs();

      this.actor.pos.getAbs(FreeFlyXYZ.this.P, FreeFlyXYZ.this.O);
      float f2 = FreeFlyXYZ.this.O.azimut() + FreeFlyXYZ.this.vMul * FreeFlyXYZ.this.vAzimut * FreeFlyXYZ.this.speedAzimut * f1;

      FreeFlyXYZ.this.P.x += FreeFlyXYZ.this.vMul * FreeFlyXYZ.this.v.x * FreeFlyXYZ.this.speedXYZ * f1;
      FreeFlyXYZ.this.P.y += FreeFlyXYZ.this.vMul * FreeFlyXYZ.this.v.y * FreeFlyXYZ.this.speedXYZ * f1;
      FreeFlyXYZ.this.P.z += FreeFlyXYZ.this.vMul * FreeFlyXYZ.this.v.z * FreeFlyXYZ.this.speedXYZ * f1;
      FreeFlyXYZ.this.O.set(f2, FreeFlyXYZ.this.O.tangage(), FreeFlyXYZ.this.O.kren());
      FreeFlyXYZ.this.O.wrap();
      this.actor.pos.setAbs(FreeFlyXYZ.this.P, FreeFlyXYZ.this.O);
      return true;
    }
  }
}