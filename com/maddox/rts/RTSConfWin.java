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
    this.jdField_mouse_of_type_ComMaddoxRtsMouse.setMouseCursorAdapter(null);
    if (paramInt == 0) {
      this.mouseDX.destroy();
      this.mouseWin.destroy();
      this.jdField_mouse_of_type_ComMaddoxRtsMouse.setComputePos(false, false);
    } else if (paramInt == 1) {
      if (this.jdField_mainWindow_of_type_ComMaddoxRtsMainWindow.hWnd() != 0) {
        this.mouseDX.destroy();
        this.mouseWin.create();
        this.jdField_mouse_of_type_ComMaddoxRtsMouse.setComputePos(false, true);
        this.jdField_mouse_of_type_ComMaddoxRtsMouse.setMouseCursorAdapter(this.mouseWin);
      }
    }
    else if (this.jdField_mainWindow_of_type_ComMaddoxRtsMainWindow.hWnd() != 0) {
      this.mouseDX.create(2);
      this.mouseWin.destroy();
      this.jdField_mouse_of_type_ComMaddoxRtsMouse.setComputePos(true, false);
    }

    this.jdField_mouse_of_type_ComMaddoxRtsMouse._clear();
    return paramInt;
  }

  public void setUseMouse(int paramInt) {
    if (this.jdField_useMouse_of_type_Int == paramInt) return;
    super.setUseMouse(doUseMouse(paramInt));
  }

  private boolean doUseJoy(boolean paramBoolean) {
    if (paramBoolean) {
      if (this.jdField_mainWindow_of_type_ComMaddoxRtsMainWindow.hWnd() != 0)
        try {
          this.joyDX.create();
        } catch (Exception localException1) {
          System.out.println("DirectX Joystick NOT created: " + localException1.getMessage());
          try {
            this.joyWin.create();
          } catch (Exception localException2) {
          }
        }
      this.jdField_joy_of_type_ComMaddoxRtsJoy._clear();
      return true;
    }
    this.joyDX.destroy();
    this.joyWin.destroy();
    this.jdField_joy_of_type_ComMaddoxRtsJoy._clear();
    return false;
  }

  public void useJoy(boolean paramBoolean)
  {
    if (this.jdField_bUseJoy_of_type_Boolean == paramBoolean) return;
    super.useJoy(doUseJoy(paramBoolean));
  }

  public void start() {
    if (!this.jdField_bStarted_of_type_Boolean) {
      this.jdField_useMouse_of_type_Int = doUseMouse(this.jdField_useMouse_of_type_Int);

      if (this.jdField_mainWindow_of_type_ComMaddoxRtsMainWindow.hWnd() != 0) {
        this.keyboardDX.create();
        this.keyboardWin.create();
      }
      this.jdField_keyboard_of_type_ComMaddoxRtsKeyboard._clear();

      this.jdField_bUseJoy_of_type_Boolean = doUseJoy(this.jdField_bUseJoy_of_type_Boolean);

      if (this.jdField_bUseTrackIR_of_type_Boolean) {
        this.trackIRWin.create();
        this.jdField_trackIR_of_type_ComMaddoxRtsTrackIR.setExist(this.trackIRWin.isCreated());
      }

      RTSConf.cur.hotKeyEnvs.resetGameCreate();
      super.start();
    }
  }

  public void stop() {
    if (this.jdField_bStarted_of_type_Boolean) {
      doUseMouse(0);

      this.keyboardDX.destroy();
      this.keyboardWin.destroy();
      this.jdField_keyboard_of_type_ComMaddoxRtsKeyboard._clear();

      doUseJoy(false);

      if (this.jdField_bUseTrackIR_of_type_Boolean) {
        this.trackIRWin.destroy();
        this.jdField_trackIR_of_type_ComMaddoxRtsTrackIR.setExist(false);
      }

      RTSConf.cur.hotKeyEnvs.resetGameClear();
      super.stop();
    }
  }

  public RTSConfWin()
  {
    super(null);
    this.jdField_mainWindow_of_type_ComMaddoxRtsMainWindow = new MainWin32(-10005, true);
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
    this.jdField_mainWindow_of_type_ComMaddoxRtsMainWindow = new MainWin32(-10005, true);
    this.jdField_useMouse_of_type_Int = paramIniFile.get(paramString, "mouseUse", this.jdField_useMouse_of_type_Int, 0, 2);
    this.mouseDX = new MouseDX(-10004, 2, false);
    this.mouseWin = new MouseWin(-10003, false);
    this.keyboardDX = new KeyboardDX(-10002, 1, false);
    this.keyboardWin = new KeyboardWin(-10001, false);
    this.keyboardWin.setOnlyChars(true);
    this.jdField_bUseJoy_of_type_Boolean = paramIniFile.get(paramString, "joyUse", this.jdField_bUseJoy_of_type_Boolean);

    this.joyDX = new JoyDX(100L, 3, false);
    this.joyWin = new JoyWin(100L, false);

    this.jdField_bUseTrackIR_of_type_Boolean = paramIniFile.get(paramString, "trackIRUse", this.jdField_bUseTrackIR_of_type_Boolean);
    this.trackIRWin = new TrackIRWin(-10010, false);
  }
}