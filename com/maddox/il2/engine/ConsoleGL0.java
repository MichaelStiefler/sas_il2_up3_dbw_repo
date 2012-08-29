package com.maddox.il2.engine;

import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConsoleGL0
{
  public static float typeOffset = 5.0F;
  protected static ConsoleGL0Listener consoleListener;
  protected static Mat backgroundMat;
  protected static boolean bActive = false;
  protected static TTFont font;
  private static ArrayList pausedEnv = new ArrayList();
  private static String envName;
  private static ConsoleGL0Render render;
  private static boolean bInit = false;

  public static void exclusiveDraw(boolean paramBoolean)
  {
    if (paramBoolean)
      consoleListener = new ConsoleGL0Listener(render);
    else {
      consoleListener = null;
    }
    RTSConf.cur.console.setListenerChanges(consoleListener);
  }

  public static void exclusiveDraw(String paramString, long paramLong)
  {
    try {
      backgroundMat = Mat.New(paramString);
      if (backgroundMat != null) {
        render.exlusiveDraw();
        long l1 = Time.real() + paramLong;
        while (true) {
          long l2 = Time.real();
          if (l2 >= l1)
            break; 
        }
      }
    } catch (Exception localException) {
    }
  }

  public static void exclusiveDraw(String paramString) {
    try {
      String str1 = RTSConf.cur.locale.getLanguage();
      if ((!"us".equalsIgnoreCase(str1)) && (!"en".equalsIgnoreCase(str1))) {
        String str2 = paramString.substring(0, paramString.length() - ".mat".length()) + "_" + str1 + ".mat";

        backgroundMat = Mat.New(str2);
        if (backgroundMat != null) {
          render.exlusiveDraw();
          return;
        }
      }
    } catch (Exception localException1) {
    }
    try {
      backgroundMat = Mat.New(paramString);
      render.exlusiveDraw(); } catch (Exception localException2) {
    }
  }

  public static void exclusiveDrawStep(String paramString, int paramInt) {
    if (backgroundMat == null) return;
    if (paramString == null) {
      backgroundMat = null;
      render.sstep = null;
    } else {
      render.exlusiveDrawStep(paramString, paramInt);
    }
  }

  public static boolean isActive() {
    return bActive;
  }
  public static void activate(boolean paramBoolean) { if (bActive != paramBoolean) {
      RTSConf.cur.console.activate(paramBoolean);
      render.setShow(paramBoolean);
      bActive = paramBoolean;
      int j;
      if (bActive) {
        List localList = HotKeyEnv.allEnv();
        j = localList.size();
        for (int k = 0; k < j; k++) {
          HotKeyEnv localHotKeyEnv2 = (HotKeyEnv)localList.get(k);
          if ((localHotKeyEnv2.isEnabled()) && (!envName.equals(localHotKeyEnv2.name()))) {
            localHotKeyEnv2.enable(false);
            pausedEnv.add(localHotKeyEnv2);
          }
        }
      } else {
        int i = pausedEnv.size();
        for (j = 0; j < i; j++) {
          HotKeyEnv localHotKeyEnv1 = (HotKeyEnv)pausedEnv.get(j);
          localHotKeyEnv1.enable(true);
        }
        pausedEnv.clear();
      }
    } }

  private static void initHotKeys()
  {
    HotKeyCmdEnv.addCmd(envName, new HotKeyCmd(true, "Activate") {
      public void end() {
        ConsoleGL0.activate(!ConsoleGL0.bActive);
      }
    });
  }

  public static void init(String paramString, int paramInt)
  {
    if (!bInit) {
      CmdEnv localCmdEnv = RTSConf.cur.console.getEnv();
      localCmdEnv.setLevelAccess(paramInt);
      envName = paramString;
      HotKeyEnv.fromIni(envName, Config.cur.ini, "HotKey " + envName);
      font = TTFont.font[3];

      CameraOrtho2D localCameraOrtho2D = new CameraOrtho2D();
      localCameraOrtho2D.setName("cameraConsoleGL0");
      localCameraOrtho2D.set(0.0F, RendersMain.getViewPortWidth(), 0.0F, RendersMain.getViewPortHeight());
      render = new ConsoleGL0Render(0.0F);
      render.setName("renderConsoleGL0");
      render.setCamera(localCameraOrtho2D);
      render.setShow(false);
      initHotKeys();
      bInit = true;
    }
  }
}