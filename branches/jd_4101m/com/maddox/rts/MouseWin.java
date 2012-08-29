package com.maddox.rts;

public final class MouseWin
  implements MsgTimeOutListener, MouseCursor
{
  private static final int PRESS = 0;
  private static final int RELEASE = 1;
  private static final int ABSMOVE = 2;
  private boolean bOnlyAbsMove = false;
  private boolean bCreated;
  private int[] param;
  private MsgTimeOut ticker;

  public final boolean isCreated()
  {
    return this.bCreated;
  }

  public final void create()
    throws MouseWinException
  {
    if (this.bCreated) {
      return;
    }
    if (RTSConf.cur.mainWindow.hWnd() == 0)
      throw new MouseWinException("Mouse windows driver: main window not present");
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

  public void setCursor(int paramInt) {
    SetCursor(paramInt);
  }

  public void msgTimeOut(Object paramObject)
  {
    if (this.bCreated) {
      while (nGetMsg(this.param)) {
        long l = Time.realFromRawClamp(this.param[1]);
        switch (this.param[0]) { case 0:
          RTSConf.cur.mouse.setPress(l, this.param[2]); break;
        case 1:
          RTSConf.cur.mouse.setRelease(l, this.param[2]); break;
        case 2:
          int i = this.param[2];
          int j = RTSConf.cur.mainWindow.height() - this.param[3] - 1;
          int k = this.param[4];
          RTSConf.cur.mouse.setAbsMove(l, i, j, k);
        }
      }

      this.ticker.post();
    }
  }

  protected MouseWin(int paramInt, boolean paramBoolean) {
    this.param = new int[5];
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

  private static final native void SetCursor(int paramInt);

  static { RTS.loadNative();
  }
}