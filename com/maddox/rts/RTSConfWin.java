package com.maddox.rts;

import java.io.PrintStream;

public class RTSConfWin extends RTSConf
{
  public MouseDX mouseDX;
  public MouseWin mouseWin;
  public KeyboardDX keyboardDX;
  public KeyboardWin keyboardWin;
  public JoyDX joyDX;
  public JoyWin joyWin;
  public TrackIRWin trackIRWin;

  private int doUseMouse(int paramInt)
  {
    this.mouse.setMouseCursorAdapter(null);
    if (paramInt == 0) {
      this.mouseDX.destroy();
      this.mouseWin.destroy();
      this.mouse.setComputePos(false, false);
    } else if (paramInt == 1) {
      if (this.mainWindow.hWnd() != 0) {
        this.mouseDX.destroy();
        this.mouseWin.create();
        this.mouse.setComputePos(false, true);
        this.mouse.setMouseCursorAdapter(this.mouseWin);
      }
    }
    else if (this.mainWindow.hWnd() != 0) {
      this.mouseDX.create(2);
      this.mouseWin.destroy();
      this.mouse.setComputePos(true, false);
    }

    this.mouse._clear();
    return paramInt;
  }

  public void setUseMouse(int paramInt) {
    if (this.useMouse == paramInt) return;
    super.setUseMouse(doUseMouse(paramInt));
  }

  private boolean doUseJoy(boolean paramBoolean) {
    if (paramBoolean) {
      if (this.mainWindow.hWnd() != 0)
        try {
          this.joyDX.create();
        } catch (Exception localException1) {
          System.out.println("DirectX Joystick NOT created: " + localException1.getMessage());
          try {
            this.joyWin.create();
          } catch (Exception localException2) {
          }
        }
      this.joy._clear();
      return true;
    }
    this.joyDX.destroy();
    this.joyWin.destroy();
    this.joy._clear();
    return false;
  }

  public void useJoy(boolean paramBoolean)
  {
    if (this.bUseJoy == paramBoolean) return;
    super.useJoy(doUseJoy(paramBoolean));
  }

  public void start() {
    if (!this.bStarted) {
      this.useMouse = doUseMouse(this.useMouse);

      if (this.mainWindow.hWnd() != 0) {
        this.keyboardDX.create();
        this.keyboardWin.create();
      }
      this.keyboard._clear();

      this.bUseJoy = doUseJoy(this.bUseJoy);

      if (this.bUseTrackIR) {
        this.trackIRWin.create();
        this.trackIR.setExist(this.trackIRWin.isCreated());
      }

      cur.hotKeyEnvs.resetGameCreate();
      super.start();
    }
  }

  public void stop() {
    if (this.bStarted) {
      doUseMouse(0);

      this.keyboardDX.destroy();
      this.keyboardWin.destroy();
      this.keyboard._clear();

      doUseJoy(false);

      if (this.bUseTrackIR) {
        this.trackIRWin.destroy();
        this.trackIR.setExist(false);
      }

      cur.hotKeyEnvs.resetGameClear();
      super.stop();
    }
  }

  public RTSConfWin()
  {
    super(null);
    this.mainWindow = new MainWin32(-10005, true);
    this.mouseDX = new MouseDX(-10004, 2, false);
    this.mouseWin = new MouseWin(-10003, false);
    this.keyboardDX = new KeyboardDX(-10002, 1, false);
    this.keyboardWin = new KeyboardWin(-10001, false);
    this.keyboardWin.setOnlyChars(true);
    this.joyDX = new JoyDX(100L, 3, false);
    this.joyWin = new JoyWin(100L, false);
    this.trackIRWin = new TrackIRWin(-10010, false);
  }
  public RTSConfWin(IniFile paramIniFile, String paramString, int paramInt) {
    super(null, paramIniFile, paramString, paramInt);
    this.mainWindow = new MainWin32(-10005, true);
    this.useMouse = paramIniFile.get(paramString, "mouseUse", this.useMouse, 0, 2);
    this.mouseDX = new MouseDX(-10004, 2, false);
    this.mouseWin = new MouseWin(-10003, false);
    this.keyboardDX = new KeyboardDX(-10002, 1, false);
    this.keyboardWin = new KeyboardWin(-10001, false);
    this.keyboardWin.setOnlyChars(true);
    this.bUseJoy = paramIniFile.get(paramString, "joyUse", this.bUseJoy);

    this.joyDX = new JoyDX(100L, 3, false);
    this.joyWin = new JoyWin(100L, false);

    this.bUseTrackIR = paramIniFile.get(paramString, "trackIRUse", this.bUseTrackIR);
    this.trackIRWin = new TrackIRWin(-10010, false);
  }
}