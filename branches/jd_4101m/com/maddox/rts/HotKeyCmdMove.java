package com.maddox.rts;

public abstract class HotKeyCmdMove extends HotKeyCmd
{
  protected int _mov;

  public int move()
  {
    return this._mov;
  }
  public final void setMove(int paramInt) {
    this._mov = paramInt;
  }

  public HotKeyCmdMove(boolean paramBoolean, String paramString) {
    super(paramBoolean, paramString);
  }
  public HotKeyCmdMove(boolean paramBoolean, String paramString1, String paramString2) {
    super(paramBoolean, paramString1, paramString2);
  }

  public void _exec(int paramInt) {
    boolean bool = Time.isPaused();
    if ((Time.isPaused()) && (!isRealTime())) return;
    if (!this.hotKeyCmdEnv.isEnabled()) return;
    setMove(paramInt);
    RTSConf.cur.hotKeyCmdEnvs.post(this, true, true);
    start(0, 0);
    RTSConf.cur.hotKeyCmdEnvs.post(this, true, false);
    stop();
  }
}