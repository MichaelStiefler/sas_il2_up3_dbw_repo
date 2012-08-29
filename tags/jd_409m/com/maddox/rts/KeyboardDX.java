package com.maddox.rts;

public final class KeyboardDX
  implements MsgTimeOutListener
{
  public static final int NONEXCLUSIVE_BACKGROUND = 0;
  public static final int NONEXCLUSIVE_FOREGROUND = 1;
  private static final int PRESS = 0;
  private static final int RELEASE = 1;
  private boolean bCreated;
  private int level;
  private int[] param;
  private MsgTimeOut ticker;

  public final boolean isCreated()
  {
    return this.bCreated;
  }

  public final void create(int paramInt)
    throws KeyboardDXException
  {
    if (this.bCreated) {
      setCooperativeLevel(paramInt);
      return;
    }
    checkCoopLevel(paramInt);
    if (RTSConf.cur.mainWindow.hWnd() == 0)
      throw new KeyboardDXException("Keyboard DirectX driver: main window not present");
    nCreate(paramInt);
    this.level = paramInt;
    this.ticker.post();
    this.bCreated = true;
  }

  public final void create()
    throws KeyboardDXException
  {
    create(this.level);
  }

  public final void setCooperativeLevel(int paramInt)
    throws KeyboardDXException
  {
    checkCoopLevel(paramInt);
    if (this.bCreated)
      nSetCoopLevel(paramInt);
    this.level = paramInt;
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
          RTSConf.cur.keyboard.setPress(l, this.param[1]); break;
        case 1:
          RTSConf.cur.keyboard.setRelease(l, this.param[1]);
        }
      }
      this.ticker.post();
    }
  }

  private static final void checkCoopLevel(int paramInt)
    throws KeyboardDXException
  {
    if ((paramInt < 0) || (paramInt > 1)) throw new KeyboardDXException("Keyboard DirectX driver: unknown cooperative level = " + paramInt); 
  }

  protected KeyboardDX(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.param = new int[3];
    this.bCreated = false;
    checkCoopLevel(paramInt2);
    this.level = paramInt2;
    this.ticker = new MsgTimeOut(null);
    this.ticker.setTickPos(paramInt1);
    this.ticker.setNotCleanAfterSend();
    this.ticker.setFlags(88);
    this.ticker.setListener(this);
    if (paramBoolean)
      create(); 
  }
  private static final native void nCreate(int paramInt) throws KeyboardDXException;

  private static final native void nDestroy();

  private static final native void nSetCoopLevel(int paramInt);

  private static final native boolean nGetMsg(int[] paramArrayOfInt);

  static { RTS.loadNative();
  }
}