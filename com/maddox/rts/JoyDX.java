package com.maddox.rts;

import com.maddox.il2.engine.Config;

public final class JoyDX
  implements MsgTimeOutListener
{
  public static final int NONEXCLUSIVE_BACKGROUND = 0;
  public static final int NONEXCLUSIVE_FOREGROUND = 1;
  public static final int EXCLUSIVE_BACKGROUND = 2;
  public static final int EXCLUSIVE_FOREGROUND = 3;
  private static final int BUTTON0 = 0;
  private static final int BUTTON31 = 31;
  private static final int MOVE_X = 32;
  private static final int MOVE_Y = 33;
  private static final int MOVE_Z = 34;
  private static final int MOVE_RX = 35;
  private static final int MOVE_RY = 36;
  private static final int MOVE_RZ = 37;
  private static final int MOVE_U = 38;
  private static final int MOVE_V = 39;
  private static final int POV0 = 40;
  private static final int POV1 = 41;
  private static final int POV2 = 42;
  private static final int POV3 = 43;
  private static final boolean bR_I18N = false;
  private static boolean bR_RU = "RU".equals(Config.LOCALE);
  private boolean bCreated;
  private int amount;
  private int level;
  private int[] param;
  private MsgTimeOut ticker;
  private long timePool = 100L;

  public final boolean isCreated()
  {
    return this.bCreated;
  }

  public final void create(int paramInt)
    throws JoyDXException
  {
    if (this.bCreated) {
      setCooperativeLevel(paramInt);
      return;
    }
    checkCoopLevel(paramInt);
    if (RTSConf.cur.mainWindow.hWnd() == 0)
      throw new JoyDXException("DirectX joystick driver: window not present");
    this.amount = nCreate(paramInt);
    this.level = paramInt;
    this.ticker.setTime(Time.currentReal() + this.timePool);
    this.ticker.post();
    this.bCreated = true;
    RTSConf.cur.joy.setAttached(true);
    RTSConf.cur.joy.setAmount(this.amount);
    int[] arrayOfInt = new int[4];
    for (int i = 0; i < this.amount; i++) {
      nCaps(i, arrayOfInt);
      RTSConf.cur.joy.setCaps(i, arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]);
    }
  }

  public final void create()
    throws JoyDXException
  {
    create(this.level);
  }
  public final int cooperativeLevel() {
    return this.level;
  }

  public final void setCooperativeLevel(int paramInt) throws JoyDXException
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
      this.amount = 0;
      RTSConf.cur.joy.setAttached(false);
    }
  }

  public void msgTimeOut(Object paramObject)
  {
    if (this.bCreated) {
      long l = Time.currentReal();
      for (int i = 0; i < this.amount; tmp40_39++) {
        if (bR_RU)
        {
          int tmp40_39 = 0; this.param[1] = tmp40_39; this.param[0] = tmp40_39;
        }while (nGetMsg(tmp40_39, this.param))
        {
          if ((this.param[0] >= 0) && (this.param[0] <= 31)) {
            if (this.param[1] == 1) RTSConf.cur.joy.setPress(l, tmp40_39, this.param[0] - 0); else
              RTSConf.cur.joy.setRelease(l, tmp40_39, this.param[0] - 0);
          }
          else
          {
            if (bR_RU) {
              this.param[0] -= 6;
            }
            switch (this.param[0]) { case 32:
              RTSConf.cur.joy.setMove(l, tmp40_39, 0, this.param[1]); break;
            case 33:
              RTSConf.cur.joy.setMove(l, tmp40_39, 1, this.param[1]); break;
            case 34:
              RTSConf.cur.joy.setMove(l, tmp40_39, 2, this.param[1]); break;
            case 35:
              RTSConf.cur.joy.setMove(l, tmp40_39, 3, this.param[1]); break;
            case 36:
              RTSConf.cur.joy.setMove(l, tmp40_39, 4, this.param[1]); break;
            case 37:
              RTSConf.cur.joy.setMove(l, tmp40_39, 5, this.param[1]); break;
            case 38:
              RTSConf.cur.joy.setMove(l, tmp40_39, 6, this.param[1]); break;
            case 39:
              RTSConf.cur.joy.setMove(l, tmp40_39, 7, this.param[1]); break;
            case 40:
              RTSConf.cur.joy.setPov(l, tmp40_39, 0, this.param[1]); break;
            case 41:
              RTSConf.cur.joy.setPov(l, tmp40_39, 1, this.param[1]); break;
            case 42:
              RTSConf.cur.joy.setPov(l, tmp40_39, 2, this.param[1]); break;
            case 43:
              RTSConf.cur.joy.setPov(l, tmp40_39, 3, this.param[1]); break;
            }
          }

          if (bR_RU)
          {
            int tmp499_498 = 0; this.param[1] = tmp499_498; this.param[0] = tmp499_498;
          }
        }
      }
      RTSConf.cur.joy.poll(l);
      this.ticker.setTime(Time.currentReal() + this.timePool);
      this.ticker.post();
    }
  }

  private static final void checkCoopLevel(int paramInt)
    throws JoyDXException
  {
    if ((paramInt < 0) || (paramInt > 3)) throw new JoyDXException("DirectX joystick driver: unknown cooperative level = " + paramInt); 
  }

  protected JoyDX(long paramLong, int paramInt, boolean paramBoolean)
  {
    this.param = new int[3];
    this.bCreated = false;
    this.amount = 0;
    checkCoopLevel(paramInt);
    this.level = paramInt;
    this.ticker = new MsgTimeOut(null);
    this.timePool = paramLong;
    this.ticker.setTime(Time.currentReal() + paramLong);
    this.ticker.setNotCleanAfterSend();
    this.ticker.setFlags(64);
    this.ticker.setListener(this);
    if (paramBoolean)
      create();  } 
  public static final native void doControlPanel();

  private static final native int nCreate(int paramInt) throws JoyDXException;

  private static final native void nDestroy();

  private static final native void nSetCoopLevel(int paramInt);

  private static final native boolean nGetMsg(int paramInt, int[] paramArrayOfInt);

  private static final native boolean nCaps(int paramInt, int[] paramArrayOfInt);

  static { RTS.loadNative();
  }
}