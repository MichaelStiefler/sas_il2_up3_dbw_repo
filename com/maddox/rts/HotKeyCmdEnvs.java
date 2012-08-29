package com.maddox.rts;

import com.maddox.util.HashMapExt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public final class HotKeyCmdEnvs
  implements MsgAddListenerListener, MsgRemoveListenerListener
{
  protected HashMap envs;
  private ArrayList listeners;
  private MsgHotKeyCmd msg = new MsgHotKeyCmd();
  protected ArrayList lst;
  protected HotKeyCmdEnv cur;

  public Object[] getListeners()
  {
    return this.listeners.toArray();
  }
  public void msgAddListener(Object paramObject1, Object paramObject2) {
    this.listeners.add(paramObject1);
  }

  public void msgRemoveListener(Object paramObject1, Object paramObject2) {
    int i = this.listeners.indexOf(paramObject1);
    if (i >= 0)
      this.listeners.remove(i);
  }

  protected void post(HotKeyCmd paramHotKeyCmd, boolean paramBoolean1, boolean paramBoolean2) {
    int i = this.listeners.size();
    if (i == 0) return;
    this.msg.bStart = paramBoolean1;
    this.msg.bBefore = paramBoolean2;
    for (int j = 0; j < i; j++)
      this.msg.send(this.listeners.get(j), paramHotKeyCmd);
  }

  public void endAllCmd() {
    for (int i = 0; i < this.lst.size(); i++) {
      HotKeyCmdEnv localHotKeyCmdEnv = (HotKeyCmdEnv)this.lst.get(i);
      if (localHotKeyCmdEnv.isEnabled()) {
        HashMapExt localHashMapExt = localHotKeyCmdEnv.all();
        Map.Entry localEntry = localHashMapExt.nextEntry(null);
        while (localEntry != null) {
          HotKeyCmd localHotKeyCmd = (HotKeyCmd)localEntry.getValue();
          if (localHotKeyCmd.isActive())
            localHotKeyCmd.stop();
          localEntry = localHashMapExt.nextEntry(localEntry);
        }
      }
    }
    RTSConf.cur.hotKeyEnvs.active.clear();
  }

  protected HotKeyCmdEnvs() {
    this.envs = new HashMap();
    this.lst = new ArrayList();
    this.listeners = new ArrayList();
  }
}