package com.maddox.il2.game;

import com.maddox.JGP.Color4f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.ConsoleGL0;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.engine.TTFont;
import com.maddox.il2.engine.TextScr;
import com.maddox.opengl.GLContext;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.Console.Exec;
import com.maddox.rts.IniFile;
import com.maddox.rts.MainWin32;
import com.maddox.rts.MainWindow;
import com.maddox.rts.RTSConf;
import com.maddox.rts.RTSConfWin;
import com.maddox.rts.ScreenMode;
import com.maddox.rts.Time;
import com.maddox.util.UnicodeTo8bit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Locale;

public class WConsole
{
  Socket socket = null;
  PrintWriter out = null;
  BufferedReader in = null;
  Thread pipe = null;
  int prompt = -1;
  boolean bReady = true;
  private ScreenMode saveMode;
  private int saveMouseMode;
  private boolean bChangedScreenMode = false;
  private boolean bChangeTimerPause = false;
  private static WConsole cur;

  boolean begin(String[] paramArrayOfString)
  {
    int i = 0;
    i = Config.cur.ini.get("Console", "IP", 0, 0, 65000);
    if (i == 0) {
      return false;
    }

    try
    {
      InetAddress localInetAddress1 = InetAddress.getLocalHost();
      if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
        localInetAddress1 = InetAddress.getByName(paramArrayOfString[0]);
      }
      InetAddress localInetAddress2 = InetAddress.getLocalHost();
      String str = Config.cur.ini.get("NET", "localHost", (String)null);
      if ((str != null) && (str.length() > 0))
        localInetAddress2 = InetAddress.getByName(str);
      this.socket = new Socket(localInetAddress1, i, localInetAddress2, 0);
      this.out = new PrintWriter(this.socket.getOutputStream(), true);
      this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }
    catch (Exception localException) {
      System.err.println("Couldn't get I/O for the connection to IL2");
      return false;
    }

    this.pipe = new Thread() {
      public void run() { String str1 = "<consoleN><";
        int i = str1.length();
        try {
          while (WConsole.this.bReady) {
            String str2 = WConsole.this.in.readLine();
            if (str2 == null) break;
            str2 = UnicodeTo8bit.load(str2);
            int j = str2.length();
            int k = 0;
            while (k + i <= j) {
              if (str2.regionMatches(k, str1, 0, i)) {
                int m = k + i;
                int n = str2.indexOf(">", m);
                if (n > 0) {
                  try {
                    WConsole.this.prompt = Integer.parseInt(str2.substring(m, n)); } catch (Exception localException2) {
                  }
                  if (n + 1 < j) str2 = str2.substring(0, k) + str2.substring(n + 1, j); else
                    str2 = str2.substring(0, k);
                  j = str2.length();
                  if ((j == 1) && (str2.charAt(0) == '\n')) {
                    j = 0;
                    str2 = null;
                  }
                } else {
                  k++;
                }
              } else {
                k++;
              }
            }
            if ((str2 != null) && (str2.length() > 0))
              System.out.print(str2);
          }
        }
        catch (Exception localException1) {
          WConsole.this.bReady = false;
        }
      }
    };
    this.pipe.start();

    System.out.println("IL2 remote console");
    System.out.println("For end console enter 'quit'");
    this.out.println("server");

    return true;
  }

  void end() {
    this.bReady = false;
    if (this.socket == null) return; try
    {
      this.out.close();
      this.in.close();
      this.socket.close(); } catch (Exception localException) {
    }
    this.socket = null;
    this.pipe.interrupt();
  }

  private void checkFocus()
  {
    if (((MainWin32)RTSConf.cur.mainWindow).IsFocused()) {
      if (!RendersMain.isShow()) {
        if ((Time.isEnableChangePause()) && 
          (this.bChangeTimerPause)) {
          Time.setPause(false);
          this.bChangeTimerPause = false;
        }

        if (this.bChangedScreenMode) {
          if (MainWindow.adapter().isIconic())
            MainWindow.adapter().showNormal();
          ScreenMode.set(this.saveMode);
          MainWindow.adapter().setFocus();
          RTSConf.cur.setUseMouse(this.saveMouseMode);
          this.bChangedScreenMode = false;
        }
        RendersMain.setShow(true);
        RendersMain.bSwapBuffersResult = true;
      } else if ((!RendersMain.bSwapBuffersResult) && (!RTSConf.isRequestExitApp()) && (Config.cur.windowChangeScreenRes) && (Config.cur.windowFullScreen))
      {
        CmdEnv.top().exec("window " + Config.cur.windowWidth + " " + Config.cur.windowHeight + " " + Config.cur.windowColourBits + " " + Config.cur.windowDepthBits + " " + Config.cur.windowStencilBits + " PROVIDER " + Config.cur.glLib + " FULL");

        RendersMain.bSwapBuffersResult = true;
      }

    }
    else if (RendersMain.isShow()) {
      if ((Time.isEnableChangePause()) && 
        (!Time.isPaused())) {
        Time.setPause(true);
        this.bChangeTimerPause = true;
      }
      RendersMain.setShow(false);
      if ((!this.bChangedScreenMode) && (Config.cur.windowChangeScreenRes)) {
        this.saveMouseMode = RTSConf.cur.getUseMouse();
        RTSConf.cur.setUseMouse(0);
        this.saveMode = ScreenMode.current();
        if (!MainWindow.adapter().isIconic())
          MainWindow.adapter().showIconic();
        ScreenMode.restore();
        this.bChangedScreenMode = true;
      }
      RendersMain.bSwapBuffersResult = true;
    }
  }

  public void loopApp()
  {
    ConsoleGL0.exclusiveDraw(false);

    while (!RTSConf.isRequestExitApp())
      synchronized (RTSConf.lockObject()) {
        checkFocus();
        RTSConf.cur.loopMsgs();
      }
  }

  public boolean beginApp(String paramString1, String paramString2, int paramInt, String[] paramArrayOfString)
  {
    IniFile localIniFile = new IniFile(paramString1);

    localIniFile.set("window", "ChangeScreenRes", 0);
    localIniFile.set("window", "FullScreen", 0);
    localIniFile.set("window", "DrawIfNotFocused", 0);
    localIniFile.set("window", "EnableResize", 0);
    localIniFile.set("Render_OpenGL", "TexFlags.PointSampling", 1);
    localIniFile.set("Render_OpenGL", "TexFlags.UseDither", 1);
    localIniFile.set("Render_OpenGL", "TexMipFilter", 0);
    localIniFile.set("Render_OpenGL", "UseDither", 0);
    localIniFile.set("rts", "mouseUse", 1);
    localIniFile.set("rts", "joyUse", 0);

    RTSConf.cur = new RTSConfWin(localIniFile, "rts", paramInt);
    RTSConf.cur.console.exec = new ConsoleExec();
    Config.cur = new Config(localIniFile, true);
    Config.cur.mainSection = paramString2;
    Engine.cur = new Engine();

    Config.typeProvider();

    String str = Config.cur.ini.get(paramString2, "title", "il2 console");
    GLContext localGLContext = Config.cur.createGlContext(str);
    Config.typeGlStrings();

    Config.cur.typeContextSettings(localGLContext);
    RTSConf.cur.start();

    RenderContext.activate(localGLContext);
    RendersMain.setGlContext(localGLContext);
    RendersMain.setSaveAspect(false);
    RendersMain.setTickPainting(true);

    TTFont.font[3] = TTFont.get("courSmall");

    ConsoleGL0.init("Console", paramInt);
    ConsoleGL0.exclusiveDraw(true);
    ConsoleGL0.activate(true);
    Render localRender = (Render)Actor.getByName("renderConsoleGL0");
    localRender.useClearColor(true);

    TextScr.font();
    TextScr.setColor(new Color4f(1.0F, 0.0F, 0.0F, 1.0F));
    Time.setPause(false);
    RTSConf.cur.loopMsgs();
    Time.setPause(true);
    RTSConf.cur.console.clear();
    return begin(paramArrayOfString);
  }

  public void endApp() {
    end();
    GLContext localGLContext = RendersMain.glContext();
    if (GLContext.isValid(localGLContext)) {
      localGLContext.destroy();
    }
    if (ScreenMode.current() != ScreenMode.startup())
      ScreenMode.restore();
    if (RTSConf.cur != null) {
      RTSConf.cur.stop();
      if ((RTSConf.cur.mainWindow.isCreated()) && ((RTSConf.cur instanceof RTSConfWin)))
        ((MainWin32)RTSConf.cur.mainWindow).destroy();
    }
  }

  public void exec(String paramString1, String paramString2, int paramInt, String[] paramArrayOfString)
  {
    Locale.setDefault(Locale.US);
    if (cur != null) {
      throw new RuntimeException("Traying recurse execute main method");
    }
    Runtime.getRuntime().traceInstructions(false);
    Runtime.getRuntime().traceMethodCalls(false);
    cur = this;
    try {
      if (beginApp(paramString1, paramString2, paramInt, paramArrayOfString))
        try {
          loopApp();
        } catch (Exception localException1) {
          System.out.println("Main loop: " + localException1.getMessage());
          localException1.printStackTrace();
        }
    }
    catch (Exception localException2) {
      System.out.println("Main begin: " + localException2.getMessage());
      localException2.printStackTrace();
    }
    try {
      endApp();
    } catch (Exception localException3) {
      System.out.println("Main end: " + localException3.getMessage());
      localException3.printStackTrace();
    }
    if ((RTSConf.cur != null) && (RTSConf.cur.console != null))
      RTSConf.cur.console.log(false);
    if ((RTSConf.cur != null) && (RTSConf.cur.execPostProcessCmd != null)) {
      try {
        Runtime.getRuntime().exec(RTSConf.cur.execPostProcessCmd);
      } catch (Exception localException4) {
        System.out.println("Exec cmd (" + RTSConf.cur.execPostProcessCmd + ") error: " + localException4.getMessage());
        localException4.printStackTrace();
      }
    }
    System.exit(0);
  }

  public static void main(String[] paramArrayOfString)
  {
    WConsole localWConsole = new WConsole();
    localWConsole.exec("confc.ini", "il2_console", 1, paramArrayOfString);
  }

  class ConsoleExec extends Console.Exec
  {
    ConsoleExec()
    {
    }

    public void doExec(String paramString)
    {
      if ("quit".equals(paramString)) {
        WConsole.this.out.println("<QUIT QUIT>");
        WConsole.this.out.flush();
        WConsole.this.bReady = false;
        RTSConf.setRequestExitApp(true);
        return;
      }
      WConsole.this.out.println(UnicodeTo8bit.save(paramString, false));
      WConsole.this.out.flush();
      if ("exit".equals(paramString)) {
        WConsole.this.bReady = false;
        RTSConf.setRequestExitApp(true);
      }
    }

    public String getPrompt() {
      if (WConsole.this.prompt >= 0) return WConsole.this.prompt + ">";
      return ">";
    }
  }
}