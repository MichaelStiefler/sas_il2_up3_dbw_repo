package com.maddox.rts;

public abstract class HotKeyCmdTrackIRAngles extends HotKeyCmd
{
  protected float _yaw;
  protected float _pitch;
  protected float _roll;

  public void angles(float paramFloat1, float paramFloat2, float paramFloat3)
  {
  }

  public final void setAngles(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this._yaw = paramFloat1;
    this._pitch = paramFloat2;
    this._roll = paramFloat3;
  }

  public final void doAngles() {
    this.jdField_bActive_of_type_Boolean = true;
    angles(this._yaw, this._pitch, this._roll);
    this.jdField_bActive_of_type_Boolean = false;
  }

  public HotKeyCmdTrackIRAngles(boolean paramBoolean, String paramString) {
    super(paramBoolean, paramString);
  }

  public void _exec(float paramFloat1, float paramFloat2, float paramFloat3) {
    boolean bool = Time.isPaused();
    if ((Time.isPaused()) && (!isRealTime())) return;
    if (!this.hotKeyCmdEnv.isEnabled()) return;
    setAngles(paramFloat1, paramFloat2, paramFloat3);
    RTSConf.cur.hotKeyCmdEnvs.post(this, true, true);
    doAngles();
    RTSConf.cur.hotKeyCmdEnvs.post(this, true, false);
  }
}