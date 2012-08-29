package com.maddox.rts;

public abstract class HotKeyCmdMouseMove extends HotKeyCmd
{
  protected int _dx;
  protected int _dy;
  protected int _dz;

  public void move(int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void prepareInvert()
  {
    this._dy = (-this._dy);
  }

  public final void setMove(int paramInt1, int paramInt2, int paramInt3)
  {
    this._dx = paramInt1;
    this._dy = paramInt2;
    this._dz = paramInt3;
  }

  public final void doMove() {
    this.jdField_bActive_of_type_Boolean = true;
    move(this._dx, this._dy, this._dz);
    this.jdField_bActive_of_type_Boolean = false;
  }

  public HotKeyCmdMouseMove(boolean paramBoolean, String paramString) {
    super(paramBoolean, paramString);
  }

  public void _exec(int paramInt1, int paramInt2, int paramInt3) {
    boolean bool = Time.isPaused();
    if ((Time.isPaused()) && (!isRealTime())) return;
    if (!this.hotKeyCmdEnv.isEnabled()) return;
    setMove(paramInt1, paramInt2, paramInt3);
    RTSConf.cur.hotKeyCmdEnvs.post(this, true, true);
    doMove();
    RTSConf.cur.hotKeyCmdEnvs.post(this, true, false);
  }
}