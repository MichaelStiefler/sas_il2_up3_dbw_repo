package com.maddox.rts;

public final class KeyboardWin
  implements MsgTimeOutListener
{
  private static final int PRESS = 0;
  private static final int RELEASE = 1;
  private static final int CHAR = 2;
  private boolean bOnlyChars = false;
  private boolean bCreated;
  private int[] param;
  private MsgTimeOut ticker;

  public final boolean isCreated()
  {
    return this.bCreated;
  }
  public final boolean isOnlyChars() { return this.bOnlyChars; } 
  public final void setOnlyChars(boolean paramBoolean) { this.bOnlyChars = paramBoolean;
  }

  public final void create()
    throws KeyboardWinException
  {
    if (this.bCreated) {
      return;
    }
    if (RTSConf.cur.mainWindow.hWnd() == 0)
      throw new KeyboardWinException("Keyboard windows driver: main window not present");
    nCreate();
    this.ticker.post();
    this.bCreated = true;
  }

  public final void destroy()
  {
    if (this.bCreated) {
      nDestroy();
      RTSConf.cur.queueRealTime.remove(this.ticker);
      RTSConf.cur.queueRealTimeNextTick.remove(this.ticker);
      this.bCreated = false;
    }
  }

  public void msgTimeOut(Object paramObject)
  {
    if (this.bCreated) {
      while (nGetMsg(this.param)) {
        long l = Time.realFromRawClamp(this.param[2]);
        switch (this.param[0]) { case 0:
          if (this.bOnlyChars) continue; RTSConf.cur.keyboard.setPress(l, this.param[1]); break;
        case 1:
          if (this.bOnlyChars) continue; RTSConf.cur.keyboard.setRelease(l, this.param[1]); break;
        case 2:
          RTSConf.cur.keyboard.setChar(l, this.param[1]);
        }
      }
      this.ticker.post();
    }
  }

  protected KeyboardWin(int paramInt, boolean paramBoolean) {
    this.param = new int[3];
    this.bCreated = false;
    this.ticker = new MsgTimeOut(null);
    this.ticker.setTickPos(paramInt);
    this.ticker.setNotCleanAfterSend();
    this.ticker.setFlags(88);
    this.ticker.setListener(this);
    if (paramBoolean)
      create(); 
  }

  private static final native void nCreate();

  private static final native void nDestroy();

  private static final native boolean nGetMsg(int[] paramArrayOfInt);

  static {
    RTS.loadNative();
  }
}