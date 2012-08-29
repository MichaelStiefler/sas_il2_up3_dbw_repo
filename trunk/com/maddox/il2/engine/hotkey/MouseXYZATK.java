package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Mouse;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgMouseListener;

public class MouseXYZATK
  implements MsgMouseListener
{
  private boolean bRealTime = false;
  private static final int UNKNOWN = -1;
  private static final int XY = 0;
  private static final int Z = 1;
  private static final int AT = 2;
  private static final int KT = 3;
  private static final int XYmove = 4;
  private static final int Zmove = 5;
  private static final int Amove = 6;
  private static final int Tmove = 7;
  private static final int Kmove = 8;
  private float koofAngle = 0.1F;
  private float[] koof = { 0.02F, 1.0F, 10.0F };
  private int iKoof = 1;
  private int iMode = -1;
  private Actor target = null;
  private Actor camera = null;
  private boolean bUse = true;

  private Orient o = new Orient();
  private Point3d p = new Point3d();
  private Orient cameraO = new Orient();
  private boolean bSaveHookUseMouse;

  public void resetGame()
  {
    this.target = null;
  }

  public void setTarget(Actor paramActor)
  {
    this.target = paramActor; } 
  public void setCamera(Actor paramActor) { this.camera = paramActor; } 
  public boolean use(boolean paramBoolean) {
    boolean bool = this.bUse;
    this.bUse = paramBoolean;
    if (this.target != null)
      this.target.pos.inValidate(true);
    return bool;
  }
  float DEG2RAD(float paramFloat) {
    return paramFloat * 0.01745329F;
  }
  public void msgMouseButton(int paramInt, boolean paramBoolean) {
  }
  public void msgMouseAbsMove(int paramInt1, int paramInt2, int paramInt3) {  }

  public void msgMouseMove(int paramInt1, int paramInt2, int paramInt3) { if ((this.bUse) && (Actor.isValid(this.target)) && (Actor.isValid(this.camera)) && (this.iMode != -1)) {
      this.camera.pos.getAbs(this.cameraO);
      this.target.pos.getRel(this.p, this.o);
      switch (this.iMode) {
      case 0:
      case 4:
        double d1 = Math.sin(DEG2RAD(this.cameraO.azimut()));
        double d2 = Math.cos(DEG2RAD(this.cameraO.azimut()));
        this.p.x += -d1 * paramInt1 * this.koof[this.iKoof] + d2 * paramInt2 * this.koof[this.iKoof];
        this.p.y += -d2 * paramInt1 * this.koof[this.iKoof] - d1 * paramInt2 * this.koof[this.iKoof];
        break;
      case 1:
      case 5:
        this.p.z += paramInt2 * this.koof[this.iKoof];
        break;
      case 2:
        this.o.set(this.o.azimut() + paramInt1 * this.koofAngle, this.o.tangage() + paramInt2 * this.koofAngle, this.o.kren());
        break;
      case 3:
        this.o.set(this.o.azimut(), this.o.tangage() + paramInt2 * this.koofAngle, this.o.kren() + paramInt1 * this.koofAngle);
        break;
      case 6:
        this.o.set(this.o.azimut() + paramInt1 * this.koofAngle, this.o.tangage(), this.o.kren());
        break;
      case 7:
        this.o.set(this.o.azimut(), this.o.tangage() + paramInt1 * this.koofAngle, this.o.kren());
        break;
      case 8:
        this.o.set(this.o.azimut(), this.o.tangage(), this.o.kren() + paramInt1 * this.koofAngle);
      }

      this.target.pos.setRel(this.p, this.o);
    }
  }

  private void initHotKeys(String paramString)
  {
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(this.bRealTime, "SpeedSlow") {
      public void begin() { if (MouseXYZATK.this.bUse) MouseXYZATK.access$102(MouseXYZATK.this, 0);
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(this.bRealTime, "SpeedNormal") {
      public void begin() { if (MouseXYZATK.this.bUse) MouseXYZATK.access$102(MouseXYZATK.this, 1);
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(this.bRealTime, "SpeedFast") {
      public void begin() { if (MouseXYZATK.this.bUse) MouseXYZATK.access$102(MouseXYZATK.this, 2);
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(this.bRealTime, "XY") {
      public void begin() { if (MouseXYZATK.this.bUse) MouseXYZATK.access$202(MouseXYZATK.this, 0);
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(this.bRealTime, "Z") {
      public void begin() { if (MouseXYZATK.this.bUse) MouseXYZATK.access$202(MouseXYZATK.this, 1);
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(this.bRealTime, "AT") {
      public void begin() { if (MouseXYZATK.this.bUse) MouseXYZATK.access$202(MouseXYZATK.this, 2);
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(this.bRealTime, "KT") {
      public void begin() { if (MouseXYZATK.this.bUse) MouseXYZATK.access$202(MouseXYZATK.this, 3);
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(this.bRealTime, "XYmove") {
      public void begin() { MouseXYZATK.this.saveHookUseMouse(4); } 
      public void end() { MouseXYZATK.this.restoreHookUseMouse();
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(this.bRealTime, "Zmove") {
      public void begin() { MouseXYZATK.this.saveHookUseMouse(5); } 
      public void end() { MouseXYZATK.this.restoreHookUseMouse();
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(this.bRealTime, "Amove") {
      public void begin() { MouseXYZATK.this.saveHookUseMouse(6); } 
      public void end() { MouseXYZATK.this.restoreHookUseMouse();
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(this.bRealTime, "Tmove") {
      public void begin() { MouseXYZATK.this.saveHookUseMouse(7); } 
      public void end() { MouseXYZATK.this.restoreHookUseMouse();
      }
    });
    HotKeyCmdEnv.addCmd(paramString, new HotKeyCmd(this.bRealTime, "Kmove") {
      public void begin() { MouseXYZATK.this.saveHookUseMouse(8); } 
      public void end() { MouseXYZATK.this.restoreHookUseMouse(); }
    });
  }

  private void saveHookUseMouse(int paramInt) {
    if ((!this.bUse) || (this.iMode != -1)) return;
    this.iMode = paramInt;
    if (HookView.current == null) return;
    this.bSaveHookUseMouse = HookView.isUseMouse();
    HookView.useMouse(false);
  }
  private void restoreHookUseMouse() {
    if ((!this.bUse) || (this.iMode == -1)) return;
    this.iMode = -1;
    if (HookView.current == null) return;
    HookView.useMouse(this.bSaveHookUseMouse);
  }

  public MouseXYZATK(String paramString) {
    String str = paramString + " Config";
    this.bRealTime = Config.cur.ini.get(str, "RealTime", this.bRealTime);
    HotKeyEnv.fromIni(paramString, Config.cur.ini, paramString);
    MsgAddListener.post(this.bRealTime ? 64 : 0, Mouse.adapter(), this, null);
    initHotKeys(paramString);
  }
}