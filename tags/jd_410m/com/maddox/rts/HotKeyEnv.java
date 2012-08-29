package com.maddox.rts;

import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapInt;
import com.maddox.util.HashMapIntEntry;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class HotKeyEnv
{
  public static final String DEFAULT = "default";
  private static ArrayList removed = new ArrayList();
  private String name;
  private boolean bEnabled = true;
  private HashMapInt keys;
  private HotKeyCmdEnv hotKeyCmdEnv;

  public final String name()
  {
    return this.name;
  }

  public final boolean isEnabled()
  {
    return this.bEnabled;
  }

  public static boolean isEnabled(String paramString)
  {
    HotKeyEnv localHotKeyEnv = (HotKeyEnv)RTSConf.cur.hotKeyEnvs.envs.get(paramString);
    if (localHotKeyEnv == null) return false;
    return localHotKeyEnv.bEnabled;
  }

  public final void enable(boolean paramBoolean)
  {
    this.bEnabled = paramBoolean;
  }

  public static void enable(String paramString, boolean paramBoolean)
  {
    HotKeyEnv localHotKeyEnv = (HotKeyEnv)RTSConf.cur.hotKeyEnvs.envs.get(paramString);
    if (localHotKeyEnv == null) return;
    localHotKeyEnv.bEnabled = paramBoolean;
  }
  private int hotKeys(int paramInt1, int paramInt2) {
    return (paramInt1 & 0xFFFF) << 16 | paramInt2 & 0xFFFF;
  }

  public void add(int paramInt1, int paramInt2, String paramString) {
    this.keys.put(hotKeys(paramInt1, paramInt2), paramString);
  }

  public static void addHotKey(int paramInt1, int paramInt2, String paramString)
  {
    RTSConf.cur.hotKeyEnvs.cur.add(paramInt1, paramInt2, paramString);
  }

  public static void addHotKey(String paramString1, int paramInt1, int paramInt2, String paramString2)
  {
    HotKeyEnv localHotKeyEnv = (HotKeyEnv)RTSConf.cur.hotKeyEnvs.envs.get(paramString1);
    if (localHotKeyEnv == null)
      localHotKeyEnv = new HotKeyEnv(paramString1);
    localHotKeyEnv.add(paramInt1, paramInt2, paramString2);
  }

  public void remove(int paramInt1, int paramInt2)
  {
    this.keys.remove(hotKeys(paramInt1, paramInt2));
  }

  public void remove(String paramString)
  {
    if (paramString == null) return;
    int i = 1;
    while (i != 0) {
      i = 0;
      HashMapIntEntry localHashMapIntEntry = this.keys.nextEntry(null);
      while (localHashMapIntEntry != null) {
        int j = localHashMapIntEntry.getKey();
        int k = j >> 16 & 0xFFFF;
        int m = j & 0xFFFF;
        String str = (String)localHashMapIntEntry.getValue();
        if (paramString.equals(str)) {
          remove(k, m);
          i = 1;
          break;
        }
        localHashMapIntEntry = this.keys.nextEntry(localHashMapIntEntry);
      }
    }
  }

  public int find(String paramString) {
    HashMapIntEntry localHashMapIntEntry = this.keys.nextEntry(null);
    while (localHashMapIntEntry != null) {
      String str = (String)localHashMapIntEntry.getValue();
      if (paramString.equals(str)) {
        int i = localHashMapIntEntry.getKey();
        return i;
      }
      localHashMapIntEntry = this.keys.nextEntry(localHashMapIntEntry);
    }
    return 0;
  }

  public String get(int paramInt1, int paramInt2)
  {
    return (String)this.keys.get(hotKeys(paramInt1, paramInt2));
  }

  public final HashMapInt all()
  {
    return this.keys;
  }

  public static final void setCurrentEnv(String paramString)
  {
    HotKeyEnv localHotKeyEnv = (HotKeyEnv)RTSConf.cur.hotKeyEnvs.envs.get(paramString);
    if (localHotKeyEnv == null)
      localHotKeyEnv = new HotKeyEnv(paramString);
    RTSConf.cur.hotKeyEnvs.cur = localHotKeyEnv;
  }

  public static final HotKeyEnv currentEnv()
  {
    return RTSConf.cur.hotKeyEnvs.cur;
  }

  public static final List allEnv()
  {
    return RTSConf.cur.hotKeyEnvs.lst;
  }

  public static final HotKeyEnv env(String paramString)
  {
    return (HotKeyEnv)RTSConf.cur.hotKeyEnvs.envs.get(paramString);
  }

  public static String key2Text(int paramInt) {
    if (paramInt >= 601) {
      return "User" + (paramInt - 601);
    }
    return VK.getKeyText(paramInt);
  }

  public static int text2Key(String paramString) {
    if (paramString.startsWith("User")) {
      String str = paramString.substring("User".length());
      return Integer.parseInt(str) + 601;
    }
    return VK.getKeyFromText(paramString);
  }

  public static void fromIni(String paramString1, IniFile paramIniFile, String paramString2)
  {
    String[] arrayOfString = paramIniFile.getVariables(paramString2);
    setCurrentEnv(paramString1);
    if (arrayOfString == null)
      return;
    for (int i = 0; i < arrayOfString.length; i++) {
      StringTokenizer localStringTokenizer = new StringTokenizer(arrayOfString[i]);
      String str1 = paramIniFile.getValue(paramString2, arrayOfString[i]);
      if ((str1.length() <= 0) || 
        (!localStringTokenizer.hasMoreTokens())) continue;
      String str2 = localStringTokenizer.nextToken();
      int j = text2Key(str2);
      if (j == 0) {
        System.err.println("INI: HotKey '" + arrayOfString[i] + "' is unknown");
      }
      else if (localStringTokenizer.hasMoreTokens()) {
        String str3 = localStringTokenizer.nextToken();
        int k = text2Key(str3);
        if (k == 0)
          System.err.println("INI: HotKey '" + arrayOfString[i] + "' is unknown");
        else
          addHotKey(j, k, str1);
      }
      else {
        addHotKey(0, j, str1);
      }
    }
  }

  public static void toIni(String paramString1, IniFile paramIniFile, String paramString2)
  {
    HotKeyEnv localHotKeyEnv = env(paramString1);
    if (localHotKeyEnv == null) {
      System.err.println("INI: HotKey environment '" + paramString1 + "' not present");
      return;
    }
    HashMapIntEntry localHashMapIntEntry = localHotKeyEnv.keys.nextEntry(null);
    while (localHashMapIntEntry != null) {
      int i = localHashMapIntEntry.getKey();
      int j = i >> 16 & 0xFFFF;
      int k = i & 0xFFFF;
      String str = (String)localHashMapIntEntry.getValue();
      if (k >= 601)
      {
        paramIniFile.setValue(paramString2, "User" + (k - 601), str);
      }
      else if (j != 0) paramIniFile.setValue(paramString2, key2Text(j) + " " + key2Text(k), str); else {
        paramIniFile.setValue(paramString2, key2Text(k), str);
      }
      localHashMapIntEntry = localHotKeyEnv.keys.nextEntry(localHashMapIntEntry);
    }
  }

  public static void tick(boolean paramBoolean)
  {
    HashMapExt localHashMapExt = RTSConf.cur.hotKeyEnvs.active;
    Map.Entry localEntry = localHashMapExt.nextEntry(null);
    while (localEntry != null) {
      HotKeyCmd localHotKeyCmd = (HotKeyCmd)localEntry.getKey();
      if (localHotKeyCmd.isTickInTime(paramBoolean))
        localHotKeyCmd.play();
      localEntry = localHashMapExt.nextEntry(localEntry);
    }
  }

  private final boolean startCmd(boolean paramBoolean, int paramInt1, int paramInt2) {
    HashMapExt localHashMapExt = RTSConf.cur.hotKeyEnvs.active;
    int i = hotKeys(paramInt1, paramInt2);
    String str = (String)this.keys.get(i);
    if (str != null) {
      HotKeyCmd localHotKeyCmd = this.hotKeyCmdEnv.get(str);
      if ((localHotKeyCmd != null) && (!localHotKeyCmd.isActive()) && (localHotKeyCmd.isEnabled()) && (localHotKeyCmd.isRealTime() == paramBoolean) && ((!Time.isPaused()) || (!localHotKeyCmd.isDisableIfTimePaused())))
      {
        localHashMapExt.put(localHotKeyCmd, null);
        RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, true, true);
        localHotKeyCmd.start(paramInt1, paramInt2);
        RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, true, false);
        return true;
      }
    }
    return false;
  }

  public static final void keyPress(boolean paramBoolean1, int paramInt, boolean paramBoolean2)
  {
    int i = paramBoolean1 ? 1 : 0;
    HashMapInt localHashMapInt = RTSConf.cur.hotKeyEnvs.keyState[i];
    HashMapExt localHashMapExt = RTSConf.cur.hotKeyEnvs.active;
    boolean bool = localHashMapInt.containsKey(paramInt);
    Object localObject;
    if ((paramBoolean2) && (!bool)) {
      localHashMapInt.put(paramInt, null);
      int j = 0;
      HashMapIntEntry localHashMapIntEntry1;
      for (int k = 0; k < RTSConf.cur.hotKeyEnvs.lst.size(); k++) {
        localObject = (HotKeyEnv)RTSConf.cur.hotKeyEnvs.lst.get(k);
        if ((((HotKeyEnv)localObject).bEnabled) && (((HotKeyEnv)localObject).hotKeyCmdEnv.isEnabled())) {
          localHashMapIntEntry1 = localHashMapInt.nextEntry(null);
          while (localHashMapIntEntry1 != null) {
            HashMapIntEntry localHashMapIntEntry2 = localHashMapInt.nextEntry(localHashMapIntEntry1);
            while (localHashMapIntEntry2 != null) {
              if ((((HotKeyEnv)localObject).startCmd(paramBoolean1, localHashMapIntEntry1.getKey(), localHashMapIntEntry2.getKey())) || (((HotKeyEnv)localObject).startCmd(paramBoolean1, localHashMapIntEntry2.getKey(), localHashMapIntEntry1.getKey())))
              {
                j = 1;
              }localHashMapIntEntry2 = localHashMapInt.nextEntry(localHashMapIntEntry2);
            }
            localHashMapIntEntry1 = localHashMapInt.nextEntry(localHashMapIntEntry1);
          }
        }
      }
      if (j == 0) {
        for (k = 0; k < RTSConf.cur.hotKeyEnvs.lst.size(); k++) {
          localObject = (HotKeyEnv)RTSConf.cur.hotKeyEnvs.lst.get(k);
          if ((((HotKeyEnv)localObject).bEnabled) && (((HotKeyEnv)localObject).hotKeyCmdEnv.isEnabled())) {
            localHashMapIntEntry1 = localHashMapInt.nextEntry(null);
            while (localHashMapIntEntry1 != null) {
              ((HotKeyEnv)localObject).startCmd(paramBoolean1, 0, localHashMapIntEntry1.getKey());
              localHashMapIntEntry1 = localHashMapInt.nextEntry(localHashMapIntEntry1);
            }
          }
        }
      }
    }
    else if ((!paramBoolean2) && (bool)) {
      Map.Entry localEntry = localHashMapExt.nextEntry(null);
      while (localEntry != null) {
        HotKeyCmd localHotKeyCmd = (HotKeyCmd)localEntry.getKey();
        if (((localHotKeyCmd.modifierKey == paramInt) || (localHotKeyCmd.key == paramInt)) && (localHotKeyCmd.isRealTime() == paramBoolean1))
          removed.add(localHotKeyCmd);
        localEntry = localHashMapExt.nextEntry(localEntry);
      }
      for (int m = 0; m < removed.size(); m++) {
        localObject = (HotKeyCmd)removed.get(m);
        localHashMapExt.remove(localObject);
        RTSConf.cur.hotKeyCmdEnvs.post((HotKeyCmd)localObject, false, true);
        ((HotKeyCmd)localObject).stop();
        RTSConf.cur.hotKeyCmdEnvs.post((HotKeyCmd)localObject, false, false);
      }
      removed.clear();
      localHashMapInt.remove(paramInt);
    }
  }

  public static final void keyPress(boolean paramBoolean1, int paramInt1, int paramInt2, boolean paramBoolean2) {
    int i = paramBoolean1 ? 1 : 0;
    HashMapInt localHashMapInt = RTSConf.cur.hotKeyEnvs.keyState[i];
    HashMapExt localHashMapExt = RTSConf.cur.hotKeyEnvs.active;
    int j = paramInt1 | paramInt2 << 16;
    boolean bool = localHashMapInt.containsKey(j);
    Object localObject;
    if ((paramBoolean2) && (!bool)) {
      localHashMapInt.put(j, null);
      for (int k = 0; k < RTSConf.cur.hotKeyEnvs.lst.size(); k++) {
        localObject = (HotKeyEnv)RTSConf.cur.hotKeyEnvs.lst.get(k);
        if ((((HotKeyEnv)localObject).bEnabled) && (((HotKeyEnv)localObject).hotKeyCmdEnv.isEnabled())) {
          ((HotKeyEnv)localObject).startCmd(paramBoolean1, paramInt1, paramInt2);
          ((HotKeyEnv)localObject).startCmd(paramBoolean1, paramInt2, paramInt1);
        }
      }
    }
    else if ((!paramBoolean2) && (bool)) {
      Map.Entry localEntry = localHashMapExt.nextEntry(null);
      while (localEntry != null) {
        localObject = (HotKeyCmd)localEntry.getKey();
        if (((((HotKeyCmd)localObject).modifierKey == paramInt1) && (((HotKeyCmd)localObject).key == paramInt2)) || ((((HotKeyCmd)localObject).modifierKey == paramInt2) && (((HotKeyCmd)localObject).key == paramInt1) && (((HotKeyCmd)localObject).isRealTime() == paramBoolean1)))
        {
          removed.add(localObject);
        }localEntry = localHashMapExt.nextEntry(localEntry);
      }
      for (int m = 0; m < removed.size(); m++) {
        HotKeyCmd localHotKeyCmd = (HotKeyCmd)removed.get(m);
        localHashMapExt.remove(localHotKeyCmd);
        RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, false, true);
        localHotKeyCmd.stop();
        RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, false, false);
      }
      removed.clear();
      localHashMapInt.remove(j);
    }
  }

  public static final void mouseMove(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    for (int i = 0; i < RTSConf.cur.hotKeyCmdEnvs.lst.size(); i++) {
      HotKeyCmdEnv localHotKeyCmdEnv = (HotKeyCmdEnv)RTSConf.cur.hotKeyCmdEnvs.lst.get(i);
      if ((localHotKeyCmdEnv.isEnabled()) && (localHotKeyCmdEnv.hotKeyEnv().bEnabled)) {
        HashMapExt localHashMapExt = localHotKeyCmdEnv.all();
        Map.Entry localEntry = localHashMapExt.nextEntry(null);
        while (localEntry != null) {
          HotKeyCmd localHotKeyCmd = (HotKeyCmd)localEntry.getValue();
          if (((localHotKeyCmd instanceof HotKeyCmdMouseMove)) && (localHotKeyCmd.isEnabled()) && (localHotKeyCmd.isRealTime() == paramBoolean) && ((!Time.isPaused()) || (!localHotKeyCmd.isDisableIfTimePaused())))
          {
            HotKeyCmdMouseMove localHotKeyCmdMouseMove = (HotKeyCmdMouseMove)localHotKeyCmd;
            localHotKeyCmdMouseMove.setMove(paramInt1, paramInt2, paramInt3);
            if (Mouse.adapter().isInvert())
              localHotKeyCmdMouseMove.prepareInvert();
            RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmdMouseMove, true, true);
            localHotKeyCmdMouseMove.doMove();
            RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmdMouseMove, true, false);
          }
          localEntry = localHashMapExt.nextEntry(localEntry);
        }
      }
    }
  }

  private final void doCmdMove(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3) {
    int i = hotKeys(paramInt1, paramInt2);
    String str = (String)this.keys.get(i);
    if (str != null) {
      HotKeyCmd localHotKeyCmd = this.hotKeyCmdEnv.get(str);
      if ((localHotKeyCmd != null) && ((localHotKeyCmd instanceof HotKeyCmdMove)) && (localHotKeyCmd.isEnabled()) && (localHotKeyCmd.isRealTime() == paramBoolean) && ((!Time.isPaused()) || (!localHotKeyCmd.isDisableIfTimePaused())))
      {
        ((HotKeyCmdMove)localHotKeyCmd).setMove(paramInt3);
        RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, true, true);
        localHotKeyCmd.start(paramInt1, paramInt2);
        RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, true, false);
        RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, false, true);
        localHotKeyCmd.stop();
        RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmd, false, false);
      }
    }
  }

  public static final void joyMove(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    for (int i = 0; i < RTSConf.cur.hotKeyEnvs.lst.size(); i++) {
      HotKeyEnv localHotKeyEnv = (HotKeyEnv)RTSConf.cur.hotKeyEnvs.lst.get(i);
      if ((localHotKeyEnv.bEnabled) && (localHotKeyEnv.hotKeyCmdEnv.isEnabled())) {
        localHotKeyEnv.doCmdMove(paramBoolean, paramInt1, paramInt2, paramInt3);
        localHotKeyEnv.doCmdMove(paramBoolean, paramInt2, paramInt1, paramInt3);
      }
    }
  }

  public static final void mouseAbsMove(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 == 0) return;
    for (int i = 0; i < RTSConf.cur.hotKeyEnvs.lst.size(); i++) {
      HotKeyEnv localHotKeyEnv = (HotKeyEnv)RTSConf.cur.hotKeyEnvs.lst.get(i);
      if ((localHotKeyEnv.bEnabled) && (localHotKeyEnv.hotKeyCmdEnv.isEnabled())) {
        localHotKeyEnv.doCmdMove(paramBoolean, 530, 0, paramInt3);
        localHotKeyEnv.doCmdMove(paramBoolean, 0, 530, paramInt3);
      }
    }
  }

  public static final void keyRedirect(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9) {
    for (int i = 0; i < RTSConf.cur.hotKeyCmdEnvs.lst.size(); i++) {
      HotKeyCmdEnv localHotKeyCmdEnv = (HotKeyCmdEnv)RTSConf.cur.hotKeyCmdEnvs.lst.get(i);

      HashMapExt localHashMapExt = localHotKeyCmdEnv.all();
      Map.Entry localEntry = localHashMapExt.nextEntry(null);
      while (localEntry != null) {
        HotKeyCmd localHotKeyCmd = (HotKeyCmd)localEntry.getValue();
        if (((localHotKeyCmd instanceof HotKeyCmdRedirect)) && (localHotKeyCmd.isEnabled()) && (localHotKeyCmd.isRealTime() == paramBoolean) && ((!Time.isPaused()) || (!localHotKeyCmd.isDisableIfTimePaused())))
        {
          HotKeyCmdRedirect localHotKeyCmdRedirect = (HotKeyCmdRedirect)localHotKeyCmd;
          if (paramInt1 == localHotKeyCmdRedirect.idRedirect()) {
            localHotKeyCmdRedirect.setRedirect(paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9);
            RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmdRedirect, true, true);
            localHotKeyCmdRedirect.doRedirect();
            RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmdRedirect, true, false);
          }
        }
        localEntry = localHashMapExt.nextEntry(localEntry);
      }
    }
  }

  public static final void trackIRAngles(boolean paramBoolean, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    for (int i = 0; i < RTSConf.cur.hotKeyCmdEnvs.lst.size(); i++) {
      HotKeyCmdEnv localHotKeyCmdEnv = (HotKeyCmdEnv)RTSConf.cur.hotKeyCmdEnvs.lst.get(i);
      if ((localHotKeyCmdEnv.isEnabled()) && (localHotKeyCmdEnv.hotKeyEnv().bEnabled)) {
        HashMapExt localHashMapExt = localHotKeyCmdEnv.all();
        Map.Entry localEntry = localHashMapExt.nextEntry(null);
        while (localEntry != null) {
          HotKeyCmd localHotKeyCmd = (HotKeyCmd)localEntry.getValue();
          if (((localHotKeyCmd instanceof HotKeyCmdTrackIRAngles)) && (localHotKeyCmd.isEnabled()) && (localHotKeyCmd.isRealTime() == paramBoolean) && ((!Time.isPaused()) || (!localHotKeyCmd.isDisableIfTimePaused())))
          {
            HotKeyCmdTrackIRAngles localHotKeyCmdTrackIRAngles = (HotKeyCmdTrackIRAngles)localHotKeyCmd;
            localHotKeyCmdTrackIRAngles.setAngles(paramFloat1, paramFloat2, paramFloat3);
            RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmdTrackIRAngles, true, true);
            localHotKeyCmdTrackIRAngles.doAngles();
            RTSConf.cur.hotKeyCmdEnvs.post(localHotKeyCmdTrackIRAngles, true, false);
          }
          localEntry = localHashMapExt.nextEntry(localEntry);
        }
      }
    }
  }

  public HotKeyCmdEnv hotKeyCmdEnv()
  {
    return this.hotKeyCmdEnv;
  }
  protected HotKeyEnv(String paramString) {
    this.name = paramString;
    this.keys = new HashMapInt();
    RTSConf.cur.hotKeyEnvs.envs.put(paramString, this);
    RTSConf.cur.hotKeyEnvs.lst.add(this);

    this.hotKeyCmdEnv = ((HotKeyCmdEnv)RTSConf.cur.hotKeyCmdEnvs.envs.get(paramString));
    if (this.hotKeyCmdEnv == null)
      this.hotKeyCmdEnv = new HotKeyCmdEnv(paramString);
  }
}