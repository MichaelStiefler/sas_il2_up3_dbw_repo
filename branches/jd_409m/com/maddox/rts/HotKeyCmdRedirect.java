package com.maddox.rts;

public abstract class HotKeyCmdRedirect extends HotKeyCmd
{
  private int idRedirect;
  protected int[] _r = new int[8];

  public int idRedirect() { return this.idRedirect; } 
  public void redirect(int[] paramArrayOfInt) {
  }

  public final void setRedirect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
    this._r[0] = paramInt1;
    this._r[1] = paramInt2;
    this._r[2] = paramInt3;
    this._r[3] = paramInt4;
    this._r[4] = paramInt5;
    this._r[5] = paramInt6;
    this._r[6] = paramInt7;
    this._r[7] = paramInt8;
  }

  public final void doRedirect() {
    this.jdField_bActive_of_type_Boolean = true;
    redirect(this._r);
    this.jdField_bActive_of_type_Boolean = false;
  }

  public HotKeyCmdRedirect(boolean paramBoolean, String paramString, int paramInt) {
    super(paramBoolean, paramString);
    this.idRedirect = paramInt;
  }

  protected void _exec(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
    boolean bool = Time.isPaused();
    if ((Time.isPaused()) && (!isRealTime())) return;
    if (!this.hotKeyCmdEnv.isEnabled()) return;
    setRedirect(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
    RTSConf.cur.hotKeyCmdEnvs.post(this, true, true);
    doRedirect();
    RTSConf.cur.hotKeyCmdEnvs.post(this, true, false);
  }
}