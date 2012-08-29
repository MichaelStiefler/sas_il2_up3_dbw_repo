package com.maddox.rts;

public final class JoyWin
  implements MsgTimeOutListener
{
  private static final int BUTTON0 = 1;
  private static final int BUTTON1 = 2;
  private static final int BUTTON2 = 4;
  private static final int BUTTON3 = 8;
  private static final int MOVE_X = 16;
  private static final int MOVE_Y = 32;
  private static boolean[][] bButtons = new boolean[2][4];
  private boolean bCreated;
  private int amount;
  private int[] param;
  private MsgTimeOut ticker;
  private long timePool = 100L;

  public final boolean isCreated()
  {
    return this.bCreated;
  }

  public final void create()
    throws JoyWinException
  {
    if (RTSConf.cur.mainWindow.hWnd() == 0)
      throw new JoyWinException("Windows joystick driver: window not present");
    this.amount = nCreate();
    if (this.amount == 0)
      throw new JoyWinException("Windows joystick not found");
    this.ticker.setTime(Time.currentReal() + this.timePool);
    this.ticker.post();
    this.bCreated = true;
    RTSConf.cur.joy.setAttached(true);
    for (int i = 0; i < this.amount; i++)
      RTSConf.cur.joy.setCaps(i, 4, 2, 0, 3);
  }

  public final void destroy()
  {
    if (this.bCreated) {
      RTSConf.cur.queueRealTime.remove(this.ticker);
      RTSConf.cur.queueRealTimeNextTick.remove(this.ticker);
      this.bCreated = false;
      this.amount = 0;
      RTSConf.cur.joy.setAttached(false);
    }
  }

  private void checkButton(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (bButtons[paramInt1][paramInt2] != paramBoolean) {
      bButtons[paramInt1][paramInt2] = paramBoolean;
      if (paramBoolean) RTSConf.cur.joy.setPress(paramLong, paramInt1, paramInt2); else
        RTSConf.cur.joy.setRelease(paramLong, paramInt1, paramInt2); 
    }
  }

  public void msgTimeOut(Object paramObject) {
    if (this.bCreated) {
      long l = Time.currentReal();
      for (int i = 0; i < this.amount; i++) {
        if (nGetMsg(i, this.param)) {
          checkButton(l, i, 0, (this.param[0] & 0x1) != 0);
          checkButton(l, i, 1, (this.param[0] & 0x2) != 0);
          checkButton(l, i, 2, (this.param[0] & 0x4) != 0);
          checkButton(l, i, 3, (this.param[0] & 0x8) != 0);
          if ((this.param[0] & 0x10) != 0)
            RTSConf.cur.joy.setMove(l, i, 0, this.param[1]);
          if ((this.param[0] & 0x20) != 0)
            RTSConf.cur.joy.setMove(l, i, 1, this.param[2]);
        }
      }
      RTSConf.cur.joy.poll(l);
      this.ticker.setTime(Time.currentReal() + this.timePool);
      this.ticker.post();
    }
  }

  protected JoyWin(long paramLong, boolean paramBoolean) {
    this.param = new int[3];
    this.bCreated = false;
    this.amount = 0;
    this.ticker = new MsgTimeOut(null);
    this.timePool = paramLong;
    this.ticker.setTime(Time.currentReal() + paramLong);
    this.ticker.setNotCleanAfterSend();
    this.ticker.setFlags(64);
    this.ticker.setListener(this);
    if (paramBoolean)
      create();
  }

  private static final native int nCreate();

  private static final native boolean nGetMsg(int paramInt, int[] paramArrayOfInt);

  static
  {
    RTS.loadNative();
  }
}