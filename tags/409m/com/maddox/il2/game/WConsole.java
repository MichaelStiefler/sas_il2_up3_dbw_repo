// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   WConsole.java

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
    class ConsoleExec extends com.maddox.rts.Console.Exec
    {

        public void doExec(java.lang.String s)
        {
            if("quit".equals(s))
            {
                out.println("<QUIT QUIT>");
                out.flush();
                bReady = false;
                com.maddox.rts.RTSConf.setRequestExitApp(true);
                return;
            }
            out.println(com.maddox.util.UnicodeTo8bit.save(s, false));
            out.flush();
            if("exit".equals(s))
            {
                bReady = false;
                com.maddox.rts.RTSConf.setRequestExitApp(true);
            }
        }

        public java.lang.String getPrompt()
        {
            if(prompt >= 0)
                return prompt + ">";
            else
                return ">";
        }

        ConsoleExec()
        {
        }
    }


    public WConsole()
    {
        socket = null;
        out = null;
        in = null;
        pipe = null;
        prompt = -1;
        bReady = true;
        bChangedScreenMode = false;
        bChangeTimerPause = false;
    }

    boolean begin(java.lang.String as[])
    {
        int i = 0;
        i = com.maddox.il2.engine.Config.cur.ini.get("Console", "IP", 0, 0, 65000);
        if(i == 0)
            return false;
        try
        {
            java.net.InetAddress inetaddress = java.net.InetAddress.getLocalHost();
            if(as != null && as.length > 0)
                inetaddress = java.net.InetAddress.getByName(as[0]);
            java.net.InetAddress inetaddress1 = java.net.InetAddress.getLocalHost();
            java.lang.String s = com.maddox.il2.engine.Config.cur.ini.get("NET", "localHost", (java.lang.String)null);
            if(s != null && s.length() > 0)
                inetaddress1 = java.net.InetAddress.getByName(s);
            socket = new Socket(inetaddress, i, inetaddress1, 0);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.err.println("Couldn't get I/O for the connection to IL2");
            return false;
        }
        pipe = new java.lang.Thread() {

            public void run()
            {
                java.lang.String s1 = "<consoleN><";
                int j = s1.length();
                try
                {
                    while(bReady) 
                    {
                        java.lang.String s2 = in.readLine();
                        if(s2 == null)
                            break;
                        s2 = com.maddox.util.UnicodeTo8bit.load(s2);
                        int k = s2.length();
                        for(int l = 0; l + j <= k;)
                            if(s2.regionMatches(l, s1, 0, j))
                            {
                                int i1 = l + j;
                                int j1 = s2.indexOf(">", i1);
                                if(j1 > 0)
                                {
                                    try
                                    {
                                        prompt = java.lang.Integer.parseInt(s2.substring(i1, j1));
                                    }
                                    catch(java.lang.Exception exception2) { }
                                    if(j1 + 1 < k)
                                        s2 = s2.substring(0, l) + s2.substring(j1 + 1, k);
                                    else
                                        s2 = s2.substring(0, l);
                                    k = s2.length();
                                    if(k == 1 && s2.charAt(0) == '\n')
                                    {
                                        k = 0;
                                        s2 = null;
                                    }
                                } else
                                {
                                    l++;
                                }
                            } else
                            {
                                l++;
                            }

                        if(s2 != null && s2.length() > 0)
                            java.lang.System.out.print(s2);
                    }
                }
                catch(java.lang.Exception exception1)
                {
                    bReady = false;
                }
            }

        }
;
        pipe.start();
        java.lang.System.out.println("IL2 remote console");
        java.lang.System.out.println("For end console enter 'quit'");
        out.println("server");
        return true;
    }

    void end()
    {
        bReady = false;
        if(socket == null)
            return;
        try
        {
            out.close();
            in.close();
            socket.close();
        }
        catch(java.lang.Exception exception) { }
        socket = null;
        pipe.interrupt();
    }

    private void checkFocus()
    {
        if(((com.maddox.rts.MainWin32)com.maddox.rts.RTSConf.cur.mainWindow).IsFocused())
        {
            if(!com.maddox.il2.engine.RendersMain.isShow())
            {
                if(com.maddox.rts.Time.isEnableChangePause() && bChangeTimerPause)
                {
                    com.maddox.rts.Time.setPause(false);
                    bChangeTimerPause = false;
                }
                if(bChangedScreenMode)
                {
                    if(com.maddox.rts.MainWindow.adapter().isIconic())
                        com.maddox.rts.MainWindow.adapter().showNormal();
                    com.maddox.rts.ScreenMode.set(saveMode);
                    com.maddox.rts.MainWindow.adapter().setFocus();
                    com.maddox.rts.RTSConf.cur.setUseMouse(saveMouseMode);
                    bChangedScreenMode = false;
                }
                com.maddox.il2.engine.RendersMain.setShow(true);
                com.maddox.il2.engine.RendersMain.bSwapBuffersResult = true;
            } else
            if(!com.maddox.il2.engine.RendersMain.bSwapBuffersResult && !com.maddox.rts.RTSConf.isRequestExitApp() && com.maddox.il2.engine.Config.cur.windowChangeScreenRes && com.maddox.il2.engine.Config.cur.windowFullScreen)
            {
                com.maddox.rts.CmdEnv.top().exec("window " + com.maddox.il2.engine.Config.cur.windowWidth + " " + com.maddox.il2.engine.Config.cur.windowHeight + " " + com.maddox.il2.engine.Config.cur.windowColourBits + " " + com.maddox.il2.engine.Config.cur.windowDepthBits + " " + com.maddox.il2.engine.Config.cur.windowStencilBits + " PROVIDER " + com.maddox.il2.engine.Config.cur.glLib + " FULL");
                com.maddox.il2.engine.RendersMain.bSwapBuffersResult = true;
            }
        } else
        if(com.maddox.il2.engine.RendersMain.isShow())
        {
            if(com.maddox.rts.Time.isEnableChangePause() && !com.maddox.rts.Time.isPaused())
            {
                com.maddox.rts.Time.setPause(true);
                bChangeTimerPause = true;
            }
            com.maddox.il2.engine.RendersMain.setShow(false);
            if(!bChangedScreenMode && com.maddox.il2.engine.Config.cur.windowChangeScreenRes)
            {
                saveMouseMode = com.maddox.rts.RTSConf.cur.getUseMouse();
                com.maddox.rts.RTSConf.cur.setUseMouse(0);
                saveMode = com.maddox.rts.ScreenMode.current();
                if(!com.maddox.rts.MainWindow.adapter().isIconic())
                    com.maddox.rts.MainWindow.adapter().showIconic();
                com.maddox.rts.ScreenMode.restore();
                bChangedScreenMode = true;
            }
            com.maddox.il2.engine.RendersMain.bSwapBuffersResult = true;
        }
    }

    public void loopApp()
    {
        com.maddox.il2.engine.ConsoleGL0.exclusiveDraw(false);
        while(!com.maddox.rts.RTSConf.isRequestExitApp()) 
            synchronized(com.maddox.rts.RTSConf.lockObject())
            {
                checkFocus();
                com.maddox.rts.RTSConf.cur.loopMsgs();
            }
    }

    public boolean beginApp(java.lang.String s, java.lang.String s1, int i, java.lang.String as[])
    {
        com.maddox.rts.IniFile inifile = new IniFile(s);
        inifile.set("window", "ChangeScreenRes", 0);
        inifile.set("window", "FullScreen", 0);
        inifile.set("window", "DrawIfNotFocused", 0);
        inifile.set("window", "EnableResize", 0);
        inifile.set("Render_OpenGL", "TexFlags.PointSampling", 1);
        inifile.set("Render_OpenGL", "TexFlags.UseDither", 1);
        inifile.set("Render_OpenGL", "TexMipFilter", 0);
        inifile.set("Render_OpenGL", "UseDither", 0);
        inifile.set("rts", "mouseUse", 1);
        inifile.set("rts", "joyUse", 0);
        com.maddox.rts.RTSConf.cur = new RTSConfWin(inifile, "rts", i);
        com.maddox.rts.RTSConf.cur.console.exec = new ConsoleExec();
        com.maddox.il2.engine.Config.cur = new Config(inifile, true);
        com.maddox.il2.engine.Config.cur.mainSection = s1;
        com.maddox.il2.engine.Engine.cur = new Engine();
        com.maddox.il2.engine.Config.typeProvider();
        java.lang.String s2 = com.maddox.il2.engine.Config.cur.ini.get(s1, "title", "il2 console");
        com.maddox.opengl.GLContext glcontext = com.maddox.il2.engine.Config.cur.createGlContext(s2);
        com.maddox.il2.engine.Config.typeGlStrings();
        com.maddox.il2.engine.Config.cur.typeContextSettings(glcontext);
        com.maddox.rts.RTSConf.cur.start();
        com.maddox.il2.engine.RenderContext.activate(glcontext);
        com.maddox.il2.engine.RendersMain.setGlContext(glcontext);
        com.maddox.il2.engine.RendersMain.setSaveAspect(false);
        com.maddox.il2.engine.RendersMain.setTickPainting(true);
        com.maddox.il2.engine.TTFont.font[3] = com.maddox.il2.engine.TTFont.get("courSmall");
        com.maddox.il2.engine.ConsoleGL0.init("Console", i);
        com.maddox.il2.engine.ConsoleGL0.exclusiveDraw(true);
        com.maddox.il2.engine.ConsoleGL0.activate(true);
        com.maddox.il2.engine.Render render = (com.maddox.il2.engine.Render)com.maddox.il2.engine.Actor.getByName("renderConsoleGL0");
        render.useClearColor(true);
        com.maddox.il2.engine.TextScr.font();
        com.maddox.il2.engine.TextScr.setColor(new Color4f(1.0F, 0.0F, 0.0F, 1.0F));
        com.maddox.rts.Time.setPause(false);
        com.maddox.rts.RTSConf.cur.loopMsgs();
        com.maddox.rts.Time.setPause(true);
        com.maddox.rts.RTSConf.cur.console.clear();
        return begin(as);
    }

    public void endApp()
    {
        end();
        com.maddox.opengl.GLContext glcontext = com.maddox.il2.engine.RendersMain.glContext();
        if(com.maddox.opengl.GLContext.isValid(glcontext))
            glcontext.destroy();
        if(com.maddox.rts.ScreenMode.current() != com.maddox.rts.ScreenMode.startup())
            com.maddox.rts.ScreenMode.restore();
        if(com.maddox.rts.RTSConf.cur != null)
        {
            com.maddox.rts.RTSConf.cur.stop();
            if(com.maddox.rts.RTSConf.cur.mainWindow.isCreated() && (com.maddox.rts.RTSConf.cur instanceof com.maddox.rts.RTSConfWin))
                ((com.maddox.rts.MainWin32)com.maddox.rts.RTSConf.cur.mainWindow).destroy();
        }
    }

    public void exec(java.lang.String s, java.lang.String s1, int i, java.lang.String as[])
    {
        java.util.Locale.setDefault(java.util.Locale.US);
        if(cur != null)
            throw new RuntimeException("Traying recurse execute main method");
        java.lang.Runtime.getRuntime().traceInstructions(false);
        java.lang.Runtime.getRuntime().traceMethodCalls(false);
        cur = this;
        try
        {
            if(beginApp(s, s1, i, as))
                try
                {
                    loopApp();
                }
                catch(java.lang.Exception exception)
                {
                    java.lang.System.out.println("Main loop: " + exception.getMessage());
                    exception.printStackTrace();
                }
        }
        catch(java.lang.Exception exception1)
        {
            java.lang.System.out.println("Main begin: " + exception1.getMessage());
            exception1.printStackTrace();
        }
        try
        {
            endApp();
        }
        catch(java.lang.Exception exception2)
        {
            java.lang.System.out.println("Main end: " + exception2.getMessage());
            exception2.printStackTrace();
        }
        if(com.maddox.rts.RTSConf.cur != null && com.maddox.rts.RTSConf.cur.console != null)
            com.maddox.rts.RTSConf.cur.console.log(false);
        if(com.maddox.rts.RTSConf.cur != null && com.maddox.rts.RTSConf.cur.execPostProcessCmd != null)
            try
            {
                java.lang.Runtime.getRuntime().exec(com.maddox.rts.RTSConf.cur.execPostProcessCmd);
            }
            catch(java.lang.Exception exception3)
            {
                java.lang.System.out.println("Exec cmd (" + com.maddox.rts.RTSConf.cur.execPostProcessCmd + ") error: " + exception3.getMessage());
                exception3.printStackTrace();
            }
        java.lang.System.exit(0);
    }

    public static void main(java.lang.String args[])
    {
        com.maddox.il2.game.WConsole wconsole = new WConsole();
        wconsole.exec("confc.ini", "il2_console", 1, args);
    }

    java.net.Socket socket;
    java.io.PrintWriter out;
    java.io.BufferedReader in;
    java.lang.Thread pipe;
    int prompt;
    boolean bReady;
    private com.maddox.rts.ScreenMode saveMode;
    private int saveMouseMode;
    private boolean bChangedScreenMode;
    private boolean bChangeTimerPause;
    private static com.maddox.il2.game.WConsole cur;
}
