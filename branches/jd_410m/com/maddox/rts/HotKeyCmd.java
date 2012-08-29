package com.maddox.rts;

import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import java.util.List;

public abstract class HotKeyCmd
{
  protected HotKeyCmdEnv hotKeyCmdEnv;
  protected String sName;
  public String sortingName;
  protected boolean bActive = false;

  protected boolean bEnabled = true;

  protected boolean bRealTime = true;
  public int modifierKey;
  public int key;
  private int recordId = 0;

  static HashMapInt mapRecorded = new HashMapInt();

  public HotKeyCmdEnv hotKeyCmdEnv()
  {
    return this.hotKeyCmdEnv;
  }

  public int recordId()
  {
    return this.recordId;
  }
  protected void setRecordId(int paramInt) {
    this.recordId = paramInt;
    mapRecorded.put(this.recordId, this);
  }

  public String name()
  {
    return this.sName;
  }
  public boolean isDisableIfTimePaused() {
    return false;
  }
  public boolean isRealTime() {
    return this.bRealTime;
  }

  public boolean isTickInTime(boolean paramBoolean) {
    return paramBoolean == this.bRealTime;
  }

  public boolean isActive()
  {
    return this.bActive;
  }

  public boolean isEnabled()
  {
    return this.bEnabled;
  }

  public void enable(boolean paramBoolean)
  {
    this.bEnabled = paramBoolean;
  }

  public void begin()
  {
  }

  public void tick()
  {
  }

  public void end() {
  }

  public final void start(int paramInt1, int paramInt2) {
    this.modifierKey = paramInt1;
    this.key = paramInt2;
    this.bActive = true;
    try {
      begin();
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }

  public final void play()
  {
    if (this.bActive)
      try {
        tick();
      } catch (Exception localException) {
        localException.printStackTrace();
      }
  }

  public final void stop()
  {
    this.bActive = false;
    try {
      end();
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }

  protected final void _cancel() {
    this.bActive = false;
  }

  public void created() {
  }

  public HotKeyCmd(boolean paramBoolean, String paramString) {
    this(paramBoolean, paramString, paramString);
  }

  public HotKeyCmd(boolean paramBoolean, String paramString1, String paramString2) {
    this.bRealTime = paramBoolean;
    this.sName = paramString1;
    this.sortingName = paramString2;
    created();
  }

  public static HotKeyCmd getByRecordedId(int paramInt) {
    return (HotKeyCmd)mapRecorded.get(paramInt);
  }

  public static int exec(double paramDouble, String paramString1, String paramString2)
  {
    return exec(false, false, paramDouble, paramString1, paramString2);
  }

  public static int exec(String paramString1, String paramString2)
  {
    int i = exec(true, true, 0.0D, paramString1, paramString2);
    if (i > 0)
      exec(true, false, 0.0D, paramString1, paramString2);
    return i;
  }

  public static int exec(boolean paramBoolean, String paramString1, String paramString2)
  {
    return exec(true, paramBoolean, 0.0D, paramString1, paramString2);
  }

  private static int exec(boolean paramBoolean1, boolean paramBoolean2, double paramDouble, String paramString1, String paramString2) {
    int i = 0;
    boolean bool = Time.isPaused();
    HashMapExt localHashMapExt = RTSConf.cur.hotKeyEnvs.active;
    List localList = HotKeyCmdEnv.allEnv();
    for (int j = 0; j < localList.size(); j++) {
      HotKeyCmdEnv localHotKeyCmdEnv = (HotKeyCmdEnv)localList.get(j);
      if ((!localHotKeyCmdEnv.isEnabled()) || ((paramString1 != null) && (!paramString1.equals(localHotKeyCmdEnv.name()))))
        continue;
      HotKeyCmd localHotKeyCmd = localHotKeyCmdEnv.get(paramString2);
      if ((localHotKeyCmd == null) || (!localHotKeyCmd.isEnabled()) || ((bool) && (!localHotKeyCmd.isRealTime())))
      {
        continue;
      }
      if (paramBoolean1) {
        if (paramBoolean2) {
          if (!localHotKeyCmd.isActive()) {
            localHashMapExt.put(localHotKeyCmd, null);
            RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, true, true);
            localHotKeyCmd.start(0, 0);
            RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, true, false);
            i++;
          }
        }
        else if (localHotKeyCmd.isActive()) {
          localHashMapExt.remove(localHotKeyCmd);
          RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, false, true);
          localHotKeyCmd.stop();
          RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, false, false);
          i++;
        }

      }
      else if (!localHotKeyCmd.isActive()) {
        localHashMapExt.put(localHotKeyCmd, null);
        RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, true, true);
        localHotKeyCmd.start(0, 0);
        RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, true, false);
        if (paramDouble > 0.0D) {
          new MsgAction(localHotKeyCmd.isRealTime() ? 64 : 0, paramDouble, localHotKeyCmd) {
            public void doAction(Object paramObject) { HotKeyCmd localHotKeyCmd = (HotKeyCmd)paramObject;
              if (localHotKeyCmd.isActive()) {
                RTSConf.cur.hotKeyEnvs.active.remove(localHotKeyCmd);
                RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, false, true);
                localHotKeyCmd.stop();
                RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, false, false);
              } } } ;
        } else {
          localHashMapExt.remove(localHotKeyCmd);
          RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, false, true);
          localHotKeyCmd.stop();
          RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, false, false);
        }
        i++;
      }

    }

    return i;
  }

  public void _exec(boolean paramBoolean) {
    if ((Time.isPaused()) && (!isRealTime())) return;
    if (!this.hotKeyCmdEnv.isEnabled()) return;
    HashMapExt localHashMapExt = RTSConf.cur.hotKeyEnvs.active;
    if (paramBoolean) {
      if (!isActive()) {
        localHashMapExt.put(this, null);
        RTSConf.cur.hotKeyCmdEnvs.post(this, true, true);
        start(0, 0);
        RTSConf.cur.hotKeyCmdEnvs.post(this, true, false);
      }
    }
    else if (isActive()) {
      localHashMapExt.remove(this);
      RTSConf.cur.hotKeyCmdEnvs.post(this, false, true);
      stop();
      RTSConf.cur.hotKeyCmdEnvs.post(this, false, false);
    }
  }
}