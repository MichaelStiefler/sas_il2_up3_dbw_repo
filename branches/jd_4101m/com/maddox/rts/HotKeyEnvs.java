package com.maddox.rts;

import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class HotKeyEnvs
{
  protected HashMapInt[] keyState = { new HashMapInt(), new HashMapInt() };

  protected HashMapExt active = new HashMapExt();
  protected HotKeyEnv cur;
  protected HashMap envs = new HashMap();

  protected ArrayList lst = new ArrayList();
  private HotKey hotKeyAdapter;

  public HotKey adapter()
  {
    return this.hotKeyAdapter;
  }

  public void endAllActiveCmd(boolean paramBoolean) {
    ArrayList localArrayList = new ArrayList();
    Map.Entry localEntry = this.active.nextEntry(null);
    HotKeyCmd localHotKeyCmd;
    while (localEntry != null) {
      localHotKeyCmd = (HotKeyCmd)localEntry.getKey();
      if ((localHotKeyCmd.bRealTime == paramBoolean) && (localHotKeyCmd.isActive()) && ((localHotKeyCmd.modifierKey != 0) || (localHotKeyCmd.key != 0)))
      {
        localArrayList.add(localHotKeyCmd);
      }localEntry = this.active.nextEntry(localEntry);
    }
    while (localArrayList.size() > 0) {
      localHotKeyCmd = (HotKeyCmd)localArrayList.get(localArrayList.size() - 1);
      this.active.remove(localHotKeyCmd);
      RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, false, true);
      localHotKeyCmd.stop();
      RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, false, false);
      localArrayList.remove(localArrayList.size() - 1);
    }
    int i = paramBoolean ? 1 : 0;
    RTSConf.cur.hotKeyEnvs.keyState[i].clear();
  }

  protected HotKeyEnvs() {
    this.hotKeyAdapter = new HotKey();
  }

  protected void resetGameClear() {
    ArrayList localArrayList = new ArrayList();
    Map.Entry localEntry = this.active.nextEntry(null);
    HotKeyCmd localHotKeyCmd;
    while (localEntry != null) {
      localHotKeyCmd = (HotKeyCmd)localEntry.getKey();
      localArrayList.add(localHotKeyCmd);
      localEntry = this.active.nextEntry(localEntry);
    }
    this.keyState[0].clear();
    this.keyState[1].clear();
    this.active.clear();
    while (localArrayList.size() > 0) {
      localHotKeyCmd = (HotKeyCmd)localArrayList.get(localArrayList.size() - 1);
      localHotKeyCmd._cancel();
      localArrayList.remove(localArrayList.size() - 1);
    }
    this.hotKeyAdapter.resetGameClear();
  }
  protected void resetGameCreate() {
    this.hotKeyAdapter.resetGameCreate();
  }
}