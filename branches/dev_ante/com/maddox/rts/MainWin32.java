package com.maddox.rts;

public class MainWin32 extends MainWindow
  implements MsgTimeOutListener
{
  private MsgTimeOut ticker;
  private boolean bLoop = false;

  public boolean isLoop()
  {
    return this.bLoop;
  }

  public void loop(boolean paramBoolean)
  {
    if (paramBoolean != this.bLoop)
      if (paramBoolean) {
        this.ticker.post();
        this.bLoop = true;
      } else {
        RTSConf.cur.queueRealTime.remove(this.ticker);
        RTSConf.cur.queueRealTimeNextTick.remove(this.ticker);
        this.bLoop = false;
      }
  }

  public void msgTimeOut(Object paramObject)
  {
    if (this.bLoop) {
      this.ticker.post();
      loopMsgs();
    }
  }

  public void loopMsgs()
  {
    int i = LoopMsgs();
    if (i != 0) {
      if (((i & 0x2) != 0) && (this.jdField_hWnd_of_type_Int != 0)) {
        this.jdField_hWnd_of_type_Int = 0;
        this.jdField_bCreated_of_type_Boolean = false;
        SendAction(2);
      }
      if ((i & 0x4) != 0) {
        this.jdField_cx_of_type_Int = Width();
        this.jdField_cy_of_type_Int = Height();
        this.jdField_cxFull_of_type_Int = WidthFull();
        this.jdField_cyFull_of_type_Int = HeightFull();
        SendAction(4);
      }
      if ((i & 0x8) != 0) {
        this.jdField_posX_of_type_Int = PosX();
        this.jdField_posY_of_type_Int = PosY();
        SendAction(8);
      }
      if ((i & 0x10) != 0) {
        this.jdField_bFocused_of_type_Boolean = IsFocused();
        SendAction(16);
      }
    }
  }

  public boolean create(String paramString, int paramInt1, int paramInt2)
  {
    if (this.jdField_hWnd_of_type_Int != 0) destroy();
    this.jdField_hWnd_of_type_Int = Create(paramString, paramInt1, paramInt2);
    if (this.jdField_hWnd_of_type_Int == 0) return false;
    this.jdField_bCreated_of_type_Boolean = true;
    this.jdField_bFullScreen_of_type_Boolean = true;
    this.jdField_cx_of_type_Int = Width();
    this.jdField_cy_of_type_Int = Height();
    this.jdField_cxFull_of_type_Int = WidthFull();
    this.jdField_cyFull_of_type_Int = HeightFull();
    this.jdField_posX_of_type_Int = PosX();
    this.jdField_posY_of_type_Int = PosY();
    this.jdField_bFocused_of_type_Boolean = IsFocused();
    SendAction(1);
    return true;
  }

  public boolean create(String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
  {
    if (this.jdField_hWnd_of_type_Int != 0) destroy();
    this.jdField_hWnd_of_type_Int = Create(paramString, paramBoolean1, paramBoolean2, paramInt1, paramInt2);
    if (this.jdField_hWnd_of_type_Int == 0) return false;
    this.jdField_bCreated_of_type_Boolean = true;
    this.jdField_bFullScreen_of_type_Boolean = false;
    this.jdField_cx_of_type_Int = Width();
    this.jdField_cy_of_type_Int = Height();
    this.jdField_cxFull_of_type_Int = WidthFull();
    this.jdField_cyFull_of_type_Int = HeightFull();
    this.jdField_posX_of_type_Int = PosX();
    this.jdField_posY_of_type_Int = PosY();
    this.jdField_bFocused_of_type_Boolean = IsFocused();
    SendAction(1);
    return true;
  }

  public void destroy()
  {
    if (this.jdField_hWnd_of_type_Int == 0) return;
    Destroy();
    this.jdField_hWnd_of_type_Int = 0;
    this.jdField_bCreated_of_type_Boolean = false;
    SendAction(2);
  }

  public void setTitle(String paramString) {
    if (this.jdField_hWnd_of_type_Int == 0) return;
    SetTitle(paramString); } 
  public native void setFocus();

  public native void setSize(int paramInt1, int paramInt2);

  public native void setPosSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public native void SetTitle(String paramString);

  public boolean isIconic() { return IsIconic(); } 
  public void showIconic() { ShowIconic(); } 
  public void showNormal() { ShowNormal(); } 
  private native int Create(String paramString, int paramInt1, int paramInt2);

  private native int Create(String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2);

  private native void Destroy();

  private native int LoopMsgs();

  public native int Width();

  public native int Height();

  private native int WidthFull();

  private native int HeightFull();

  private native int PosX();

  private native int PosY();

  public native boolean IsFocused();

  private native boolean IsIconic();

  private native void ShowIconic();

  private native void ShowNormal();

  public static native String RegistryGetAppPath(String paramString);

  public static native String RegistryGetStringLM(String paramString1, String paramString2);

  public static native String GetAppPath();

  public static native String GetCDDrive(String paramString);

  public static native void SetAppPath(String paramString);

  public static native void ImmDisableIME();

  protected MainWin32(int paramInt, boolean paramBoolean) { this.ticker = new MsgTimeOut(null);
    this.ticker.setTickPos(paramInt);
    this.ticker.setNotCleanAfterSend();
    this.ticker.setFlags(88);
    this.ticker.setListener(this);
    loop(paramBoolean);
  }

  static
  {
    RTS.loadNative();
  }
}