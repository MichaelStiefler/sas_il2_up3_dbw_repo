package com.maddox.rts;

public final class TrackIRWin
  implements MsgTimeOutListener
{
  private static int ProgramID = 1001;
  private boolean bCreated;
  private float[] angles;
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
      throw new RuntimeException("TrackIR windows driver: main window not present");
    if (!nCreate(ProgramID))
      return;
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

  public void msgTimeOut(Object paramObject) {
    if (this.bCreated) {
      if (nGetAngles(this.angles))
        RTSConf.cur.trackIR.setAngles(this.angles[0], this.angles[1], this.angles[2]);
      this.ticker.post();
    }
  }

  protected TrackIRWin(int paramInt, boolean paramBoolean) {
    this.angles = new float[3];
    this.bCreated = false;
    this.ticker = new MsgTimeOut(null);
    this.ticker.setTickPos(paramInt);
    this.ticker.setNotCleanAfterSend();
    this.ticker.setFlags(88);
    this.ticker.setListener(this);
    if (paramBoolean)
      create(); 
  }
  private static final native boolean nCreate(int paramInt);

  private static final native void nDestroy();

  private static final native boolean nGetAngles(float[] paramArrayOfFloat);

  static {
    RTS.loadNative();
  }
}