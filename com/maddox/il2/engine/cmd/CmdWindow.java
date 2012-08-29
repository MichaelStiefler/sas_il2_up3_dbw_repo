package com.maddox.il2.engine.cmd;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.ConsoleGL0;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GObj;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Render;
import com.maddox.il2.engine.RenderContext;
import com.maddox.il2.engine.Renders;
import com.maddox.il2.engine.RendersMain;
import com.maddox.il2.game.Main3D;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.Provider;
import com.maddox.rts.Cmd;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.MainWin32;
import com.maddox.rts.MainWindow;
import com.maddox.rts.RTSConf;
import com.maddox.rts.RTSConfWin;
import com.maddox.rts.ScreenMode;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CmdWindow extends Cmd
{
  public static final String FULL = "FULL";
  public static final String NOFULL = "NOFULL";
  public static final String PROVIDER = "PROVIDER";

  public Object exec(CmdEnv paramCmdEnv, Map paramMap)
  {
    if (!(RTSConf.cur instanceof RTSConfWin)) {
      ERR_HARD("This command is valid only on Win32 main window");
      return null;
    }
    Object localObject = CmdEnv.RETURN_OK;
    if ((paramMap.containsKey("_$$")) || (paramMap.containsKey("FULL")) || (paramMap.containsKey("PROVIDER")) || (paramMap.containsKey("NOFULL")))
    {
      int i = 0;
      String str1 = null;
      String str2 = Provider.isGLloaded() ? Provider.GLname() : "";
      if (Cmd.nargs(paramMap, "PROVIDER") > 0) {
        str1 = Cmd.arg(paramMap, "PROVIDER", 0);
        if ((!Provider.isGLloaded()) || (str1.compareToIgnoreCase(str2) != 0)) {
          i = 1;
        }
      }
      boolean bool1 = paramMap.containsKey("FULL");
      int i2;
      int j;
      if (paramMap.containsKey("_$$")) {
        i2 = bool1 ? 2560 : ScreenMode.startup().width();
        j = Cmd.arg(paramMap, "_$$", 0, 640, 160, i2);
      } else {
        j = Config.cur.windowWidth;
      }
      int k;
      if (Cmd.nargs(paramMap, "_$$") > 1) {
        i2 = bool1 ? 1820 : ScreenMode.startup().height();
        k = Cmd.arg(paramMap, "_$$", 1, 480, 120, 1820);
      } else {
        k = j * 3 / 4;
      }
      int m;
      if (Cmd.nargs(paramMap, "_$$") > 2) {
        if (bool1) m = Cmd.arg(paramMap, "_$$", 2, 16, 16, 32); else
          m = ScreenMode.startup().colourBits();
      }
      else if (bool1) m = Config.cur.windowColourBits; else
        m = ScreenMode.startup().colourBits();
      int n;
      if (Cmd.nargs(paramMap, "_$$") > 3) {
        n = Cmd.arg(paramMap, "_$$", 3, 16, 16, 32);
      }
      else if (m == Config.cur.windowColourBits)
        n = Config.cur.windowDepthBits;
      else
        n = m;
      int i1;
      if (Cmd.nargs(paramMap, "_$$") > 4)
        i1 = Cmd.arg(paramMap, "_$$", 4, 0, 0, 8);
      else {
        i1 = Config.cur.windowStencilBits;
      }

      if ((bool1 == Config.cur.windowChangeScreenRes) && (!bool1) && (m == Config.cur.windowColourBits) && (n == Config.cur.windowDepthBits) && (i1 == Config.cur.windowStencilBits) && (i == 0))
      {
        if ((j != Config.cur.windowWidth) || (k != Config.cur.windowHeight)) {
          if (bool1) {
            ScreenMode.set(j, k, m);
            RTSConf.cur.mainWindow.setSize(ScreenMode.current().width(), ScreenMode.current().height());
          } else {
            RTSConf.cur.mainWindow.setSize(j, k);
          }
          Config.cur.windowWidth = ((MainWin32)RTSConf.cur.mainWindow).Width();
          Config.cur.windowHeight = ((MainWin32)RTSConf.cur.mainWindow).Height();
        }
      } else {
        if (Provider.countContexts() > 1) {
          ERR_HARD("Many (" + Provider.countContexts() + ") OpenGL is opened");
          return null;
        }

        Config.cur.endSound();
        MainWindow.adapter().setMessagesEnable(false);
        RendersMain.glContext().setMessagesEnable(false);
        RendersMain.glContext().destroy();
        ((MainWin32)RTSConf.cur.mainWindow).destroy();
        RTSConf.cur.stop();
        RenderContext.deactivate();
        try {
          if (i != 0)
            Provider.GLload(str1);
          Config.cur.createGlContext(RendersMain.glContext(), bool1, bool1, j, k, m, n, i1);
        } catch (Exception localException1) {
          ERR_HARD(localException1.toString());
          localObject = null;
          if (i != 0) {
            i = 0;
            try {
              Provider.GLload(str2);
            } catch (Exception localException2) {
              ERR_HARD(localException2.toString());
              ERR_HARD("Error restore provider");
              RTSConf.setRequestExitApp(true);
              return null;
            }
          }
          try {
            Config.cur.createGlContext(RendersMain.glContext(), Config.cur.windowChangeScreenRes, Config.cur.windowFullScreen, Config.cur.windowWidth, Config.cur.windowHeight, Config.cur.windowColourBits, Config.cur.windowDepthBits, Config.cur.windowStencilBits);
          }
          catch (Exception localException3)
          {
            ERR_HARD(localException3.toString());
            ERR_HARD("Error restore window mode");
            RTSConf.setRequestExitApp(true);
            return null;
          }
        }
        if (i != 0) {
          Config.cur.glLib = str1;
        }
        MainWindow.adapter().setMessagesEnable(true);
        RendersMain.glContext().setMessagesEnable(true);
        if (i != 0)
          Config.cur.loadEngine(null);
        else
          Config.cur.loadEngine();
        RenderContext.activate(RendersMain.glContext());

        Mat.enableDeleteTextureID(false);
        CmdEnv.top().exec("fobj *.font RELOAD");
        GObj.DeleteCppObjects();
        RTSConf.cur.mainWindow.SendAction(4);
        RTSConf.cur.mainWindow.SendAction(8);

        boolean bool2 = ConsoleGL0.isActive();
        ConsoleGL0.activate(true);
        Render localRender = (Render)Actor.getByName("renderConsoleGL0");

        RendersMain.glContext().sendAction(8);

        System.out.println("Reload all textures of landscape ...");
        if (Main3D.cur3D().isUseStartLog()) {
          if (localRender != null) Engine.rendersMain().paint(localRender); 
        }
        else {
          ConsoleGL0.exclusiveDraw("gui/background0.mat");
          ConsoleGL0.exclusiveDrawStep(null, 0);
        }
        try {
          World.land().ReLoadMap();
        } catch (Exception localException4) {
          ERR_HARD(localException4.toString());
          ERR_HARD("Error reload landscape");
          RTSConf.setRequestExitApp(true);
          return null;
        }
        GObj.DeleteCppObjects();

        System.out.println("Reload all textures of objects ...");
        if ((Main3D.cur3D().isUseStartLog()) && 
          (localRender != null)) Engine.rendersMain().paint(localRender);
        CmdEnv.top().exec("fobj *.tga *.txa RELOAD");
        GObj.DeleteCppObjects();

        Mat.enableDeleteTextureID(true);
        ConsoleGL0.activate(bool2);
        RTSConf.cur.start();

        Config.cur.beginSound();
      }
    }
    INFO_HARD("  " + Config.cur.windowWidth + "x" + Config.cur.windowHeight + "x" + Config.cur.windowColourBits + " " + Config.cur.windowDepthBits + " " + Config.cur.windowStencilBits + " " + (Provider.isGLloaded() ? Provider.GLname() : ""));

    return localObject;
  }

  public CmdWindow() {
    this.jdField_param_of_type_JavaUtilTreeMap.put("FULL", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("NOFULL", null);
    this.jdField_param_of_type_JavaUtilTreeMap.put("PROVIDER", null);
    this._properties.put("NAME", "window");
    this._levelAccess = 1;
  }
}