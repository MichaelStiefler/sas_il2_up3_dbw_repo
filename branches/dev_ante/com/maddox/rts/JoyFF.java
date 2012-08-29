package com.maddox.rts;

import java.util.ArrayList;

public class JoyFF
{
  public static final int NONEXCLUSIVE_BACKGROUND = 0;
  public static final int NONEXCLUSIVE_FOREGROUND = 1;
  public static final int EXCLUSIVE_BACKGROUND = 2;
  public static final int EXCLUSIVE_FOREGROUND = 3;
  private static JoyFF cur;
  private static ArrayList lst = new ArrayList();

  private static boolean bAttached = false;
  private static boolean bEnable = false;
  private static boolean bStarted = false;
  private static boolean bAutoCenter = true;

  public static boolean isAttached() { return bAttached; } 
  public static boolean isEnable() { return bEnable; } 
  public static void setEnable(boolean paramBoolean) { bEnable = paramBoolean; } 
  public static boolean isStarted() { return bStarted; } 
  public static void start() {
    if (!bAttached) return;
    if (bStarted) return;
    bStarted = Start(3);
    if (bStarted) {
      setAutoCenter(bAutoCenter);
      int i = lst.size();
      for (int j = 0; j < i; j++) {
        Effect localEffect = (Effect)lst.get(j);
        localEffect.cppObj = NewEffect(HomePath.toFileSystemName(HomePath.get(0), localEffect.fileName, 0));
        if (localEffect.cppObj == 0)
          return;
        localEffect.duration = GetEffectDuration(localEffect.cppObj);
      }
    }
  }

  public static void stop() {
    if (!bAttached) return;
    if (!bStarted) return;
    int i = lst.size();
    for (int j = 0; j < i; j++) {
      Effect localEffect = (Effect)lst.get(j);
      if (localEffect.cppObj != 0) {
        DelEffect(localEffect.cppObj);
        localEffect.cppObj = 0;
        localEffect.iterations = 0;
        localEffect.lock = null;
      }
    }
    bStarted = false;
    Stop();
  }

  public static void setAutoCenter(boolean paramBoolean) {
    if (!bAttached) return;
    bAutoCenter = paramBoolean;
    if (!bStarted) return;
    SetAutoCenter(paramBoolean);
  }

  private JoyFF() {
    bAttached = IsAttached(); } 
  private static final native boolean IsAttached();

  private static final native boolean Start(int paramInt);

  private static final native void Stop();

  private static final native void SetAutoCenter(boolean paramBoolean);

  private static final native int NewEffect(String paramString);

  private static final native void DelEffect(int paramInt);

  private static final native int GetEffectDuration(int paramInt);

  private static final native void StartEffect(int paramInt);

  private static final native void StopEffect(int paramInt);

  private static final native boolean EffectIsPlaying(int paramInt);

  private static final native void SetEffectDuration(int paramInt1, int paramInt2);

  private static final native void SetEffectSamplePeriod(int paramInt1, int paramInt2);

  private static final native void SetEffectGain(int paramInt1, int paramInt2);

  private static final native void SetEffectDirection(int paramInt1, int paramInt2, int paramInt3);

  private static final native void SetEffectOffset(int paramInt1, int paramInt2, int paramInt3);

  private static final native void SetEffectCoefficient(int paramInt1, int paramInt2, int paramInt3);

  static { RTS.loadNative();
    cur = new JoyFF();
  }

  public static class Effect
    implements MsgTimeOutListener
  {
    public int cppObj;
    String fileName;
    int duration;
    int iterations;
    Object lock;

    public void msgTimeOut(Object paramObject)
    {
      if (this.cppObj == 0) return;
      if (paramObject != this.lock) return;
      this.iterations -= 1;
      if (this.iterations == 0) return;
      JoyFF.access$000(this.cppObj);
      MsgTimeOut.post(64, Time.currentReal() + this.duration, this, this.lock);
    }

    public void start(int paramInt) {
      if (this.cppObj == 0) return;
      if (paramInt < 0) paramInt = -1;
      if (paramInt == 0) return;
      this.iterations = paramInt;
      this.lock = null;
      JoyFF.access$000(this.cppObj);
      if ((this.iterations != 1) && (this.duration > 0)) {
        this.lock = new Object();
        MsgTimeOut.post(64, Time.currentReal() + this.duration, this, this.lock);
      }
    }

    public void stop() {
      this.iterations = 0;
      this.lock = null;
      if (this.cppObj == 0) return;
      JoyFF.access$100(this.cppObj);
    }

    public boolean isPlaying() {
      if (this.cppObj == 0) return false;
      if (this.iterations == 0) return false;
      if ((this.iterations == 1) && (this.lock == null))
        return JoyFF.access$200(this.cppObj);
      return true;
    }

    public void setDuration(int paramInt) {
      if (this.cppObj == 0) return;
      this.duration = paramInt;
      JoyFF.access$300(this.cppObj, paramInt);
    }
    public void setSamplePeriod(int paramInt) {
      if (this.cppObj == 0) return;
      JoyFF.access$400(this.cppObj, paramInt);
    }
    public void setGain(float paramFloat) {
      if (this.cppObj == 0) return;
      if (paramFloat < 0.0F) paramFloat = 0.0F;
      if (paramFloat > 1.0F) paramFloat = 1.0F;
      JoyFF.access$500(this.cppObj, (int)(paramFloat * 10000.0F));
    }
    public void setDirection(float paramFloat1, float paramFloat2) {
      if (this.cppObj == 0) return;
      if (paramFloat1 < -1.0F) paramFloat1 = -1.0F;
      if (paramFloat1 > 1.0F) paramFloat1 = 1.0F;
      if (paramFloat2 < -1.0F) paramFloat2 = -1.0F;
      if (paramFloat2 > 1.0F) paramFloat2 = 1.0F;
      JoyFF.access$600(this.cppObj, (int)(paramFloat1 * 10000.0F), (int)(paramFloat2 * 10000.0F));
    }
    public void setOffset(float paramFloat1, float paramFloat2) {
      if (this.cppObj == 0) return;
      if (paramFloat1 < -1.0F) paramFloat1 = -1.0F;
      if (paramFloat1 > 1.0F) paramFloat1 = 1.0F;
      if (paramFloat2 < -1.0F) paramFloat2 = -1.0F;
      if (paramFloat2 > 1.0F) paramFloat2 = 1.0F;
      JoyFF.access$700(this.cppObj, (int)(paramFloat1 * 10000.0F), (int)(paramFloat2 * 10000.0F));
    }
    public void setCoefficient(float paramFloat1, float paramFloat2) {
      if (this.cppObj == 0) return;
      if (paramFloat1 < -1.0F) paramFloat1 = -1.0F;
      if (paramFloat1 > 1.0F) paramFloat1 = 1.0F;
      if (paramFloat2 < -1.0F) paramFloat2 = -1.0F;
      if (paramFloat2 > 1.0F) paramFloat2 = 1.0F;
      JoyFF.access$800(this.cppObj, (int)(paramFloat1 * 10000.0F), (int)(paramFloat2 * 10000.0F));
    }

    public void destroy()
    {
      if (this.cppObj == 0)
        return;
      JoyFF.access$900(this.cppObj);
      this.cppObj = 0;
      JoyFF.lst.remove(this);
    }

    public Effect(String paramString) {
      this.fileName = paramString;
      if (!JoyFF.bAttached) return;
      if (!JoyFF.bStarted) return;
      this.cppObj = JoyFF.access$1300(HomePath.toFileSystemName(this.fileName, 0));
      if (this.cppObj == 0)
        return;
      this.duration = JoyFF.access$1400(this.cppObj);
      JoyFF.lst.add(this);
    }
  }
}