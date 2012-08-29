package com.maddox.rts;

public class TrackIR
  implements MsgAddListenerListener, MsgRemoveListenerListener
{
  public static final int ANGLES = 0;
  public static final int UNKNOWN = -1;
  private boolean bExist;
  private Listeners listeners;
  private Listeners realListeners;
  private MessageCache cache;
  private float yaw;
  private float pitch;
  private float roll;

  public static TrackIR adapter()
  {
    return RTSConf.cur.trackIR;
  }

  public boolean isExist()
  {
    return this.bExist;
  }
  public void getAngles(float[] paramArrayOfFloat) {
    paramArrayOfFloat[0] = this.yaw;
    paramArrayOfFloat[1] = this.pitch;
    paramArrayOfFloat[2] = this.roll;
  }

  public Object[] getListeners()
  {
    return this.listeners.get();
  }

  public Object[] getRealListeners()
  {
    return this.realListeners.get();
  }
  public void msgAddListener(Object paramObject1, Object paramObject2) {
    if (Message.current().isRealTime()) this.realListeners.insListener(paramObject1); else
      this.listeners.insListener(paramObject1);
  }

  public void msgRemoveListener(Object paramObject1, Object paramObject2) {
    if (Message.current().isRealTime()) this.realListeners.removeListener(paramObject1); else
      this.listeners.removeListener(paramObject1);
  }

  protected void setExist(boolean paramBoolean) {
    this.bExist = paramBoolean;
  }
  protected void clear() { this.yaw = (this.pitch = this.roll = 0.0F); }

  protected void setAngles(float paramFloat1, float paramFloat2, float paramFloat3) {
    if ((this.yaw == paramFloat1) && (this.pitch == paramFloat2) && (this.roll == paramFloat3)) {
      return;
    }
    this.yaw = paramFloat1;
    this.pitch = paramFloat2;
    this.roll = paramFloat3;

    Object[] arrayOfObject = this.realListeners.get();
    MsgTrackIR localMsgTrackIR;
    if (arrayOfObject != null) {
      localMsgTrackIR = (MsgTrackIR)this.cache.get();
      localMsgTrackIR.id = 0;
      localMsgTrackIR.yaw = this.yaw;
      localMsgTrackIR.pitch = this.pitch;
      localMsgTrackIR.roll = this.roll;
      localMsgTrackIR.post(64, arrayOfObject, Time.currentReal(), this);
    }
    if (!Time.isPaused()) {
      arrayOfObject = this.listeners.get();
      if (arrayOfObject != null) {
        localMsgTrackIR = (MsgTrackIR)this.cache.get();
        localMsgTrackIR.id = 0;
        localMsgTrackIR.yaw = this.yaw;
        localMsgTrackIR.pitch = this.pitch;
        localMsgTrackIR.roll = this.roll;
        localMsgTrackIR.post(0, arrayOfObject, Time.current(), this);
      }
    }
  }

  protected TrackIR(IniFile paramIniFile, String paramString) {
    this.bExist = false;
    this.listeners = new Listeners();
    this.realListeners = new Listeners();
    this.cache = new MessageCache(MsgTrackIR.class);
    clear();
  }
}