package com.maddox.rts;

import com.maddox.il2.engine.Config;

public final class MouseDX
  implements MsgTimeOutListener
{
  public static final int NONEXCLUSIVE_BACKGROUND = 0;
  public static final int NONEXCLUSIVE_FOREGROUND = 1;
  public static final int EXCLUSIVE_FOREGROUND = 2;
  private static final int BUTTON0 = 0;
  private static final int BUTTON1 = 1;
  private static final int BUTTON2 = 2;
  private static final int BUTTON3 = 3;
  private static final int MOVE_X = 4;
  private static final int MOVE_Y = 5;
  private static final int MOVE_Z = 6;
  private static final boolean bR_I18N = false;
  private static boolean bR_RU = "RU".equals(Config.LOCALE);
  private boolean bCreated;
  private int level;
  private int[] param;
  private MsgTimeOut ticker;

  public final boolean isCreated()
  {
    return this.bCreated;
  }

  public final void create(int paramInt)
    throws MouseDXException
  {
    if (this.bCreated) {
      setCooperativeLevel(paramInt);
      return;
    }
    checkCoopLevel(paramInt);
    if (RTSConf.cur.mainWindow.hWnd() == 0)
      throw new MouseDXException("DirectX mouse driver: window not present");
    nCreate(paramInt);
    this.level = paramInt;
    this.ticker.post();
    this.bCreated = true;
  }

  public final void create()
    throws MouseDXException
  {
    create(this.level);
  }
  public final int cooperativeLevel() {
    return this.level;
  }

  public final void setCooperativeLevel(int paramInt) throws MouseDXException
  {
    checkCoopLevel(paramInt);
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
      if (bR_RU)
      {
        int tmp24_23 = 0; this.param[1] = tmp24_23; this.param[0] = tmp24_23;
      }while (nGetMsg(this.param)) {
        long l = Time.realFromRawClamp(this.param[2]);
        switch (this.param[0]) { case 0:
        case 1:
        case 2:
        case 3:
          if (this.param[1] == 1) RTSConf.cur.mouse.setPress(l, this.param[0] - 0); else
            RTSConf.cur.mouse.setRelease(l, this.param[0] - 0);
          break;
        case 4:
          RTSConf.cur.mouse.setMove(l, this.param[1], 0, 0); break;
        case 5:
          RTSConf.cur.mouse.setMove(l, 0, -this.param[1], 0); break;
        case 6:
          RTSConf.cur.mouse.setMove(l, 0, 0, this.param[1]);
        }
        if (bR_RU)
        {
          int tmp218_217 = 0; this.param[1] = tmp218_217; this.param[0] = tmp218_217;
        }
      }
      RTSConf.cur.mouse.flushMove();
      this.ticker.post();
    }
  }

  private static final void checkCoopLevel(int paramInt)
    throws MouseDXException
  {
    if ((paramInt < 0) || (paramInt > 2)) throw new MouseDXException("DirectX mouse driver: unknown cooperative level = " + paramInt); 
  }

  protected MouseDX(int paramInt1, int paramInt2, boolean paramBoolean)
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
      create();  } 
  private static final native void nCreate(int paramInt) throws MouseDXException;

  private static final native void nDestroy();

  private static final native void nSetCoopLevel(int paramInt);

  private static final native boolean nGetMsg(int[] paramArrayOfInt);

  static { RTS.loadNative();
  }
}