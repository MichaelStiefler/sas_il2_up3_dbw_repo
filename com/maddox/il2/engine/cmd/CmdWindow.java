// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CmdWindow.java

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

public class CmdWindow extends com.maddox.rts.Cmd
{

    public java.lang.Object exec(com.maddox.rts.CmdEnv cmdenv, java.util.Map map)
    {
        if(!(com.maddox.rts.RTSConf.cur instanceof com.maddox.rts.RTSConfWin))
        {
            ERR_HARD("This command is valid only on Win32 main window");
            return null;
        }
        java.lang.Object obj = com.maddox.rts.CmdEnv.RETURN_OK;
        if(map.containsKey("_$$") || map.containsKey("FULL") || map.containsKey("PROVIDER") || map.containsKey("NOFULL"))
        {
            boolean flag = false;
            java.lang.String s = null;
            java.lang.String s1 = com.maddox.opengl.Provider.isGLloaded() ? com.maddox.opengl.Provider.GLname() : "";
            if(com.maddox.il2.engine.cmd.CmdWindow.nargs(map, "PROVIDER") > 0)
            {
                s = com.maddox.il2.engine.cmd.CmdWindow.arg(map, "PROVIDER", 0);
                if(!com.maddox.opengl.Provider.isGLloaded() || s.compareToIgnoreCase(s1) != 0)
                    flag = true;
            }
            boolean flag1 = map.containsKey("FULL");
            int i;
            if(map.containsKey("_$$"))
            {
                int j1 = flag1 ? 2560 : com.maddox.rts.ScreenMode.startup().width();
                i = com.maddox.il2.engine.cmd.CmdWindow.arg(map, "_$$", 0, 640, 160, j1);
            } else
            {
                i = com.maddox.il2.engine.Config.cur.windowWidth;
            }
            int j;
            if(com.maddox.il2.engine.cmd.CmdWindow.nargs(map, "_$$") > 1)
            {
                int k1 = flag1 ? 1820 : com.maddox.rts.ScreenMode.startup().height();
                j = com.maddox.il2.engine.cmd.CmdWindow.arg(map, "_$$", 1, 480, 120, 1820);
            } else
            {
                j = (i * 3) / 4;
            }
            int k;
            if(com.maddox.il2.engine.cmd.CmdWindow.nargs(map, "_$$") > 2)
            {
                if(flag1)
                    k = com.maddox.il2.engine.cmd.CmdWindow.arg(map, "_$$", 2, 16, 16, 32);
                else
                    k = com.maddox.rts.ScreenMode.startup().colourBits();
            } else
            if(flag1)
                k = com.maddox.il2.engine.Config.cur.windowColourBits;
            else
                k = com.maddox.rts.ScreenMode.startup().colourBits();
            int l;
            if(com.maddox.il2.engine.cmd.CmdWindow.nargs(map, "_$$") > 3)
                l = com.maddox.il2.engine.cmd.CmdWindow.arg(map, "_$$", 3, 16, 16, 32);
            else
            if(k == com.maddox.il2.engine.Config.cur.windowColourBits)
                l = com.maddox.il2.engine.Config.cur.windowDepthBits;
            else
                l = k;
            int i1;
            if(com.maddox.il2.engine.cmd.CmdWindow.nargs(map, "_$$") > 4)
                i1 = com.maddox.il2.engine.cmd.CmdWindow.arg(map, "_$$", 4, 0, 0, 8);
            else
                i1 = com.maddox.il2.engine.Config.cur.windowStencilBits;
            if(flag1 == com.maddox.il2.engine.Config.cur.windowChangeScreenRes && !flag1 && k == com.maddox.il2.engine.Config.cur.windowColourBits && l == com.maddox.il2.engine.Config.cur.windowDepthBits && i1 == com.maddox.il2.engine.Config.cur.windowStencilBits && !flag)
            {
                if(i != com.maddox.il2.engine.Config.cur.windowWidth || j != com.maddox.il2.engine.Config.cur.windowHeight)
                {
                    if(flag1)
                    {
                        com.maddox.rts.ScreenMode.set(i, j, k);
                        com.maddox.rts.RTSConf.cur.mainWindow.setSize(com.maddox.rts.ScreenMode.current().width(), com.maddox.rts.ScreenMode.current().height());
                    } else
                    {
                        com.maddox.rts.RTSConf.cur.mainWindow.setSize(i, j);
                    }
                    com.maddox.il2.engine.Config.cur.windowWidth = ((com.maddox.rts.MainWin32)com.maddox.rts.RTSConf.cur.mainWindow).Width();
                    com.maddox.il2.engine.Config.cur.windowHeight = ((com.maddox.rts.MainWin32)com.maddox.rts.RTSConf.cur.mainWindow).Height();
                }
            } else
            {
                if(com.maddox.opengl.Provider.countContexts() > 1)
                {
                    ERR_HARD("Many (" + com.maddox.opengl.Provider.countContexts() + ") OpenGL is opened");
                    return null;
                }
                com.maddox.il2.engine.Config.cur.endSound();
                com.maddox.rts.MainWindow.adapter().setMessagesEnable(false);
                com.maddox.il2.engine.RendersMain.glContext().setMessagesEnable(false);
                com.maddox.il2.engine.RendersMain.glContext().destroy();
                ((com.maddox.rts.MainWin32)com.maddox.rts.RTSConf.cur.mainWindow).destroy();
                com.maddox.rts.RTSConf.cur.stop();
                com.maddox.il2.engine.RenderContext.deactivate();
                try
                {
                    if(flag)
                        com.maddox.opengl.Provider.GLload(s);
                    com.maddox.il2.engine.Config.cur.createGlContext(com.maddox.il2.engine.RendersMain.glContext(), flag1, flag1, i, j, k, l, i1);
                }
                catch(java.lang.Exception exception)
                {
                    ERR_HARD(exception.toString());
                    obj = null;
                    if(flag)
                    {
                        flag = false;
                        try
                        {
                            com.maddox.opengl.Provider.GLload(s1);
                        }
                        catch(java.lang.Exception exception1)
                        {
                            ERR_HARD(exception1.toString());
                            ERR_HARD("Error restore provider");
                            com.maddox.rts.RTSConf.setRequestExitApp(true);
                            return null;
                        }
                    }
                    try
                    {
                        com.maddox.il2.engine.Config.cur.createGlContext(com.maddox.il2.engine.RendersMain.glContext(), com.maddox.il2.engine.Config.cur.windowChangeScreenRes, com.maddox.il2.engine.Config.cur.windowFullScreen, com.maddox.il2.engine.Config.cur.windowWidth, com.maddox.il2.engine.Config.cur.windowHeight, com.maddox.il2.engine.Config.cur.windowColourBits, com.maddox.il2.engine.Config.cur.windowDepthBits, com.maddox.il2.engine.Config.cur.windowStencilBits);
                    }
                    catch(java.lang.Exception exception2)
                    {
                        ERR_HARD(exception2.toString());
                        ERR_HARD("Error restore window mode");
                        com.maddox.rts.RTSConf.setRequestExitApp(true);
                        return null;
                    }
                }
                if(flag)
                    com.maddox.il2.engine.Config.cur.glLib = s;
                com.maddox.rts.MainWindow.adapter().setMessagesEnable(true);
                com.maddox.il2.engine.RendersMain.glContext().setMessagesEnable(true);
                if(flag)
                    com.maddox.il2.engine.Config.cur.loadEngine(null);
                else
                    com.maddox.il2.engine.Config.cur.loadEngine();
                com.maddox.il2.engine.RenderContext.activate(com.maddox.il2.engine.RendersMain.glContext());
                com.maddox.il2.engine.Mat.enableDeleteTextureID(false);
                com.maddox.rts.CmdEnv.top().exec("fobj *.font RELOAD");
                com.maddox.il2.engine.GObj.DeleteCppObjects();
                com.maddox.rts.RTSConf.cur.mainWindow.SendAction(4);
                com.maddox.rts.RTSConf.cur.mainWindow.SendAction(8);
                boolean flag2 = com.maddox.il2.engine.ConsoleGL0.isActive();
                com.maddox.il2.engine.ConsoleGL0.activate(true);
                com.maddox.il2.engine.Render render = (com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderConsoleGL0");
                com.maddox.il2.engine.RendersMain.glContext().sendAction(8);
                java.lang.System.out.println("Reload all textures of landscape ...");
                if(com.maddox.il2.game.Main3D.cur3D().isUseStartLog())
                {
                    if(render != null)
                        com.maddox.il2.engine.Engine.rendersMain().paint(render);
                } else
                {
                    com.maddox.il2.engine.ConsoleGL0.exclusiveDraw("gui/background0.mat");
                    com.maddox.il2.engine.ConsoleGL0.exclusiveDrawStep(null, 0);
                }
                try
                {
                    com.maddox.il2.ai.World.land().ReLoadMap();
                }
                catch(java.lang.Exception exception3)
                {
                    ERR_HARD(exception3.toString());
                    ERR_HARD("Error reload landscape");
                    com.maddox.rts.RTSConf.setRequestExitApp(true);
                    return null;
                }
                com.maddox.il2.engine.GObj.DeleteCppObjects();
                java.lang.System.out.println("Reload all textures of objects ...");
                if(com.maddox.il2.game.Main3D.cur3D().isUseStartLog() && render != null)
                    com.maddox.il2.engine.Engine.rendersMain().paint(render);
                com.maddox.rts.CmdEnv.top().exec("fobj *.tga *.txa RELOAD");
                com.maddox.il2.engine.GObj.DeleteCppObjects();
                com.maddox.il2.engine.Mat.enableDeleteTextureID(true);
                com.maddox.il2.engine.ConsoleGL0.activate(flag2);
                com.maddox.rts.RTSConf.cur.start();
                com.maddox.il2.engine.Config.cur.beginSound();
            }
        }
        INFO_HARD("  " + com.maddox.il2.engine.Config.cur.windowWidth + "x" + com.maddox.il2.engine.Config.cur.windowHeight + "x" + com.maddox.il2.engine.Config.cur.windowColourBits + " " + com.maddox.il2.engine.Config.cur.windowDepthBits + " " + com.maddox.il2.engine.Config.cur.windowStencilBits + " " + (com.maddox.opengl.Provider.isGLloaded() ? com.maddox.opengl.Provider.GLname() : ""));
        return obj;
    }

    public CmdWindow()
    {
        param.put("FULL", null);
        param.put("NOFULL", null);
        param.put("PROVIDER", null);
        _properties.put("NAME", "window");
        _levelAccess = 1;
    }

    public static final java.lang.String FULL = "FULL";
    public static final java.lang.String NOFULL = "NOFULL";
    public static final java.lang.String PROVIDER = "PROVIDER";
}
